package com.ihakula.journey.network;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.ConcurrentModificationException;
import java.util.List;
import java.util.concurrent.TimeoutException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpException;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.params.ConnManagerParams;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import android.util.Log;

import com.ihakula.journey.utils.PublicUtils;

public class BaseRequest {
	private DefaultHttpClient client;

	// private final String RESPONSE_HEADER_NAME_COOKIE = "Set-Cookie";
	private final String REQUEST_HEADER_NAME_COOKIE = "Cookie";

	private String cookie_value = null;

	public BaseRequest() {
		HttpParams params = new BasicHttpParams();
		// 设置一些基本参数
		// HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
		// HttpProtocolParams.setContentCharset(params, HTTP.UTF_8);
		// HttpProtocolParams.setUseExpectContinue(params, true);
		// HttpProtocolParams.setUserAgent(params,
		// "Mozilla/5.0(Linux;U;Android 2.2.1;en-us;Nexus One Build.FRG83) "
		// +
		// "AppleWebKit/553.1(KHTML,like Gecko) Version/4.0 Mobile Safari/533.1");
		// 超时设置
		/* 从连接池中取连接的超时时间 */
		ConnManagerParams.setTimeout(params, 1000);
		/* 连接超时 */
		HttpConnectionParams.setConnectionTimeout(params, 50000);
		/* 请求超时 */
		HttpConnectionParams.setSoTimeout(params, 20000);

		// 设置我们的HttpClient支持HTTP和HTTPS两种模式
		SchemeRegistry schReg = new SchemeRegistry();
		schReg.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
		schReg.register(new Scheme("https", SSLSocketFactory.getSocketFactory(), 443));

		// 使用线程安全的连接管理来创建HttpClient
		ClientConnectionManager conMgr = new ThreadSafeClientConnManager(params, schReg);
		client = new DefaultHttpClient(conMgr, params);
	}

	public void getHttpClient() {

	}

	/**
	 * 多参数的get请求
	 * 
	 * @param params
	 * @param url
	 * @return
	 * @throws HttpException
	 * @throws IOException
	 * @throws TimeoutException 
	 */
	public String getRequest(List<NameValuePair> params, String url) throws IOException, TimeoutException {
		String result = null;

		String strURL;

		StringBuffer sb = new StringBuffer();
		if (params != null && params.size() > 0) {
			try {
				for (NameValuePair nvp : params) {
					String value = URLEncoder.encode(nvp.getValue(), HTTP.UTF_8);
					if (nvp.getName().equals("oauth_verifier")) {
						value = nvp.getValue();
					}
					sb.append(nvp.getName()).append('=').append(value).append('&');
				}
				sb.deleteCharAt(sb.length() - 1);
			} catch (ConcurrentModificationException e) {
				e.printStackTrace();
			}
		}
		String paramsStr = sb.toString();
		if (paramsStr != null && !paramsStr.equals("")) {
			strURL = url + "?" + paramsStr;
		} else {
			strURL = url;
		}
		Log.i("jerome","url"+strURL);

		HttpGet request = new HttpGet(strURL);
		Log.i("jerome", "strURL = " + strURL);
		if (cookie_value != null) {
			request.addHeader(REQUEST_HEADER_NAME_COOKIE, cookie_value);
		}

		HttpResponse response = client.execute(request);
		int httpStatusCode = response.getStatusLine().getStatusCode();

		if (httpStatusCode == HttpStatus.SC_OK) {
			result = EntityUtils.toString(response.getEntity());
		} else if(httpStatusCode == HttpStatus.SC_REQUEST_TIMEOUT){
			throw new TimeoutException();
		}else{
			Log.e("jerome","http error code:"+httpStatusCode);
			throw new IOException("Error Response:" + response.getStatusLine().toString());
		}

		return result;
	}

	/**
	 * get请求，无参数，return InputStream
	 * 
	 * @param url
	 * @return
	 * @throws HttpException
	 * @throws IOException
	 */
	public InputStream getRequest(String url) throws TimeoutException, IOException {
		InputStream result = null;
		HttpGet request = new HttpGet(url);
		if (cookie_value != null) {
			request.addHeader(REQUEST_HEADER_NAME_COOKIE, cookie_value);
		}
		HttpResponse response = client.execute(request);
		int httpStatusCode = response.getStatusLine().getStatusCode();
		if (httpStatusCode == 200) {
			result = response.getEntity().getContent();
		} else if(httpStatusCode == HttpStatus.SC_REQUEST_TIMEOUT){
			throw new TimeoutException();
		}else{
			throw new IOException("Error Response:" + response.getStatusLine().toString());
		}
		return result;
	}

	/**
	 * 多参数的post请求
	 */
	public String postRequest(List<NameValuePair> params, String url) throws TimeoutException, IOException {
		String result = null;
		HttpPost request = new HttpPost(url);
		if (cookie_value != null) {
			request.addHeader(REQUEST_HEADER_NAME_COOKIE, cookie_value);
		}
		HttpEntity entity = new UrlEncodedFormEntity(params, HTTP.UTF_8);
		request.setEntity(entity);
		HttpResponse response = client.execute(request);
		int httpStatusCode = response.getStatusLine().getStatusCode();
		if (httpStatusCode == HttpStatus.SC_OK) {
			result = EntityUtils.toString(response.getEntity());
		} else if(httpStatusCode == HttpStatus.SC_REQUEST_TIMEOUT){
			throw new TimeoutException();
		}else{
			throw new IOException("Error Response:" + response.getStatusLine().toString());
		}

		return result;
	}

	/**
	 * 多参数，多文件的post请求
	 * 
	 * @param stringParams
	 * @param fileParams
	 * @param url
	 * @return
	 * @throws HttpException
	 * @throws IOException
	 */
	public String postRequest(List<NameValuePair> stringParams, List<NameValuePair> fileParams, String url)
			throws TimeoutException, IOException {
		String result = null;
		HttpPost request = new HttpPost(url);
		if (cookie_value != null) {
			request.addHeader(REQUEST_HEADER_NAME_COOKIE, cookie_value);
		}

		MultipartEntity entity = new MultipartEntity();
		for (NameValuePair snv : stringParams) {
			entity.addPart(snv.getName(), new StringBody(snv.getValue(), Charset.forName("UTF-8")));
		}

		for (NameValuePair fnv : fileParams) {
			File file = new File(fnv.getValue());
			if (file.isFile() && !file.isDirectory()) {
				entity.addPart(fnv.getName(), new FileBody(file));
			}
		}

		request.setEntity(entity);

		HttpResponse response = client.execute(request);

		int httpStatusCode = response.getStatusLine().getStatusCode();
		if (httpStatusCode == HttpStatus.SC_OK) {
			result = EntityUtils.toString(response.getEntity());
		} else if(httpStatusCode == HttpStatus.SC_REQUEST_TIMEOUT){
			throw new TimeoutException();
		}else{
			throw new IOException("Error Response:" + response.getStatusLine().toString());
		}

		return result;
	}

	public void setCookie(String value) {
		cookie_value = value;
	}

	public String downloadImage(String bookID, String url) {

		File folder = new File(PublicUtils.getAppFolder() + "/" + bookID);

		if (!folder.exists()) {
			folder.mkdirs();
		}

		InputStream in = null;
		FileOutputStream fileOut = null;
		HttpResponse response = null;
		String localSavePath = folder.getAbsolutePath() + "/cover_image.jpg";

		try {

			HttpGet request = new HttpGet(url);

			if (cookie_value != null) {
				request.addHeader(REQUEST_HEADER_NAME_COOKIE, cookie_value);
			}

			response = client.execute(request);
			in = response.getEntity().getContent();
			fileOut = new FileOutputStream(new File(localSavePath));

			byte[] buff = new byte[1024];
			int flag = in.read(buff);
			while (flag != -1) {
				fileOut.write(buff, 0, flag);
				flag = in.read(buff);
			}

		} catch (Exception e) {
			e.printStackTrace();
			return "";
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

			if (fileOut != null) {
				try {
					fileOut.close();
				} catch (IOException e) {
					e.printStackTrace();
				}

			}

		}

		return localSavePath;
	}

}
