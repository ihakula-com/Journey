package com.ihakula.journey.network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.HashMap;
import java.util.concurrent.TimeoutException;

import org.apache.http.HttpException;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import com.ihakula.journey.utils.SharedPreferencesUtil;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

public class NutsPlayRequest {
	// android地址
	private final String url = "http://54.251.119.236:8084/rest";
//	private final String url = "http://222.189.237.168:8084/rest";
	private final static String TOKEN_URL = "/get_token";
	private final static String FOCUS_URL = "/get_focus";
	private final static String APPLIST_URL = "/get_applist";
	private final static String UPDATE_APPLIST_URL = "/get_updatelist";
	private final static String APPDETAIL_URL = "/get_single_details";
	private final static String PICDES_URL = "/get_single_imgs";
	private final static String PERMISSION_URL = "/get_single_permission";
	private final static String POSTRATING_URL = "/grade";
	private final static String POSTCOLLECT_URL = "/collect";
	private final static String GROUPLIST_URL = "/get_cla_list";
	private final static String GROUPSUBLIST_URL = "/get_sub_list";
	private final static String CLA_APPLIST_URL = "/get_group";
	private final static String SUB_APPLIST_URL = "/get_group_subject";
	private final static String COLLECTION_APPLIST_URL = "/get_collection";
	private final static String COLLECTION_SIZE_URL = "/get_coll_size";
	private final static String UPDATE_SIZE_URL = "/get_updatelist_size";
	private final static String POST_REGIST_URL = "/regist";
	private final static String POST_LOGIN_URL = "/login";
	private final static String  SIMPLE_SEARCH_URL = "/simple_search";
	private final static String  SEARCH_URL = "/search";
	private final static String  CHECKUP_URL = "/checkup";
	
	private final String login_url = "http://api.weibo.cn/2/account/login";

	private Context mContext;
	private BaseRequest baseRequest;

	protected SharedPreferencesUtil spfu;

	public NutsPlayRequest(Context context) {
		baseRequest = new BaseRequest();
		spfu = SharedPreferencesUtil.getInstance(context);
		mContext = context;
	}

	private void addShareParams(ArrayList<NameValuePair> strParams) {
//		String authcode = "d6712b498d9815f23cf1d5df43afd242";
//		strParams.add(new BasicNameValuePair("authcode", authcode));
//		String from_client = "android";
//		strParams.add(new BasicNameValuePair("from_client", from_client));
	}
	
	public String getToken(HashMap<String, String> map) throws IOException, TimeoutException{
		ArrayList<NameValuePair> strParams = new ArrayList<NameValuePair>();
		strParams.clear();
		addShareParams(strParams);

		strParams.add(new BasicNameValuePair("version", map.get("version")));
		strParams.add(new BasicNameValuePair("imei", map.get("imei")));
		strParams.add(new BasicNameValuePair("sys_version", map.get("sys_version")));
		strParams.add(new BasicNameValuePair("resolution", map.get("resolution")));
		strParams.add(new BasicNameValuePair("type", "type"));

		StringBuffer sb = new StringBuffer();
		sb.append(url).append(TOKEN_URL);
		return baseRequest.getRequest(strParams, sb.toString());
	}
	
	/**
	 * 首页焦点图片
	 * @return
	 * @throws TimeoutException
	 * @throws IOException
	 */
	public String getFocus() throws TimeoutException, IOException{
		StringBuffer sb = new StringBuffer();
		sb.append(url).append(FOCUS_URL);
		return baseRequest.getRequest(null , sb.toString());
	}
	
	/**
	 * 应用列表
	 * @param category
	 * @param sort
	 * @param page
	 * @return
	 * @throws IOException
	 * @throws TimeoutException
	 */
	public String getAppList(String category , String sort , String page) throws IOException, TimeoutException{
		ArrayList<NameValuePair> strParams = new ArrayList<NameValuePair>();
		strParams.clear();
		addShareParams(strParams);
		if (null != category && category.length() > 0) {
			strParams.add(new BasicNameValuePair("category", category));
		}
		if (null != sort && sort.length() > 0) {
			strParams.add(new BasicNameValuePair("sort", sort));
		}
		if (null != page && page.length() > 0) {
			strParams.add(new BasicNameValuePair("page", page));
		}
		StringBuffer sb = new StringBuffer();
		sb.append(url).append(APPLIST_URL);
		return baseRequest.getRequest(strParams, sb.toString());
	}
	
	public String getUpdateAppList(String install_str) throws IOException, TimeoutException{
		ArrayList<NameValuePair> strParams = new ArrayList<NameValuePair>();
		strParams.clear();
		addShareParams(strParams);
		if (null != install_str && install_str.length() > 0) {
			strParams.add(new BasicNameValuePair("installed_list_info", install_str));
		}
		StringBuffer sb = new StringBuffer();
		sb.append(url).append(UPDATE_APPLIST_URL);
		return baseRequest.postRequest(strParams, sb.toString());
	}
	
	public String getApp(String appId) throws IOException, TimeoutException{
		ArrayList<NameValuePair> strParams = new ArrayList<NameValuePair>();
		strParams.clear();
		addShareParams(strParams);
		if (null != appId && appId.length() > 0) {
			strParams.add(new BasicNameValuePair("appid", appId));
		}
		StringBuffer sb = new StringBuffer();
		sb.append(url).append(APPDETAIL_URL);
		return baseRequest.getRequest(strParams, sb.toString());
	}
	
	public String getAppUpdateInfo(String versionName) throws IOException, TimeoutException{
		ArrayList<NameValuePair> strParams = new ArrayList<NameValuePair>();
		strParams.clear();
		addShareParams(strParams);
		if (null != versionName && versionName.length() > 0) {
			strParams.add(new BasicNameValuePair("versionname", versionName));
		}
		StringBuffer sb = new StringBuffer();
		sb.append(url).append(CHECKUP_URL);
		return baseRequest.getRequest(strParams, sb.toString());
	}
	
	public String getPicDes(String appId) throws IOException, TimeoutException{
		ArrayList<NameValuePair> strParams = new ArrayList<NameValuePair>();
		strParams.clear();
		addShareParams(strParams);
		if (null != appId && appId.length() > 0) {
			strParams.add(new BasicNameValuePair("appid", appId));
		}
		StringBuffer sb = new StringBuffer();
		sb.append(url).append(PICDES_URL);
		return baseRequest.getRequest(strParams, sb.toString());
	}
	
	public String getPermission(String appId) throws IOException, TimeoutException{
		ArrayList<NameValuePair> strParams = new ArrayList<NameValuePair>();
		strParams.clear();
		addShareParams(strParams);
		if (null != appId && appId.length() > 0) {
			strParams.add(new BasicNameValuePair("appid", appId));
		}
		StringBuffer sb = new StringBuffer();
		sb.append(url).append(PERMISSION_URL);
		return baseRequest.getRequest(strParams, sb.toString());
	}
	
	public String getSearchResult(String input) throws IOException, TimeoutException{
		ArrayList<NameValuePair> strParams = new ArrayList<NameValuePair>();
		strParams.clear();
		addShareParams(strParams);
		if (null != input && input.length() > 0) {
			strParams.add(new BasicNameValuePair("key", input));
		}
		StringBuffer sb = new StringBuffer();
		sb.append(url).append(SIMPLE_SEARCH_URL);
		return baseRequest.getRequest(strParams, sb.toString());
	}
	
	public String getSearchResultList(String key) throws IOException, TimeoutException{
		ArrayList<NameValuePair> strParams = new ArrayList<NameValuePair>();
		strParams.clear();
		addShareParams(strParams);
		if (null != key && key.length() > 0) {
			strParams.add(new BasicNameValuePair("key", key));
		}
		StringBuffer sb = new StringBuffer();
		sb.append(url).append(SEARCH_URL);
		return baseRequest.getRequest(strParams, sb.toString());
	}
	
	public String postRating(String appId , String rating , String token) throws IOException, TimeoutException{
		ArrayList<NameValuePair> strParams = new ArrayList<NameValuePair>();
		strParams.clear();
		addShareParams(strParams);
		if (null != appId && appId.length() > 0) {
			strParams.add(new BasicNameValuePair("appid", appId));
		}
		if (null != rating && rating.length() > 0) {
			strParams.add(new BasicNameValuePair("rating", rating));
		}

		StringBuffer sb = new StringBuffer();
		sb.append(url).append(POSTRATING_URL);
		return baseRequest.postRequest(strParams, sb.toString());
	}
	
	public String getGroupList(String category) throws IOException, TimeoutException{
		ArrayList<NameValuePair> strParams = new ArrayList<NameValuePair>();
		strParams.clear();
		addShareParams(strParams);
		if (null != category && category.length() > 0) {
			strParams.add(new BasicNameValuePair("category", category));
		}
		StringBuffer sb = new StringBuffer();
		sb.append(url).append(GROUPLIST_URL);
		return baseRequest.getRequest(strParams, sb.toString());
	}
	
	public String getGroupSubList() throws IOException, TimeoutException{
		ArrayList<NameValuePair> strParams = new ArrayList<NameValuePair>();
		strParams.clear();
		addShareParams(strParams);

		StringBuffer sb = new StringBuffer();
		sb.append(url).append(GROUPSUBLIST_URL);
		return baseRequest.getRequest(strParams, sb.toString());
	}
	
	//------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	
	/**
	 * 首页
	 * 
	 * @return
	 * @throws HttpException
	 * @throws IOException
	 */
	public String getRecommendRequest() throws TimeoutException, IOException {
		ArrayList<NameValuePair> strParams = new ArrayList<NameValuePair>();
		strParams.clear();
		addShareParams(strParams);
		StringBuffer sb = new StringBuffer();
		sb.append(url).append("index.php");
		return baseRequest.getRequest(strParams, sb.toString());
	}

	/**
	 * 搜索
	 * 
	 * @param key
	 * @return
	 * @throws HttpException
	 * @throws IOException
	 */
	public String getSearchRequest(String key, String page, String pagesize) throws TimeoutException, IOException {
		ArrayList<NameValuePair> strParams = new ArrayList<NameValuePair>();
		strParams.clear();
		addShareParams(strParams);
		if (null != key && key.length() > 0) {
			strParams.add(new BasicNameValuePair("keys", key));
		}
		if (null != page && page.length() > 0) {
			strParams.add(new BasicNameValuePair("page", page));
		}
		if (null != pagesize && pagesize.length() > 0) {
			strParams.add(new BasicNameValuePair("pagesize", pagesize));
		}

		StringBuffer sb = new StringBuffer();
		sb.append(url).append("search.php");
		return baseRequest.getRequest(strParams, sb.toString());
	}

	/**
	 * 热门搜索词
	 * 
	 * @return
	 * @throws HttpException
	 * @throws IOException
	 */
	public String getSearchWordRequest() throws TimeoutException, IOException {
		ArrayList<NameValuePair> strParams = new ArrayList<NameValuePair>();
		strParams.clear();
		addShareParams(strParams);
		strParams.add(new BasicNameValuePair("perpage", "10"));

		StringBuffer sb = new StringBuffer();
		sb.append(url).append("search_word.php");
		return baseRequest.getRequest(strParams, sb.toString());
	}

	/**
	 * 用户购买记录
	 * 
	 * @param gsid
	 * @return
	 * @throws HttpException
	 * @throws IOException
	 */
	public String getPayList(String gsid) throws TimeoutException, IOException {
		ArrayList<NameValuePair> strParams = new ArrayList<NameValuePair>();
		strParams.clear();
		addShareParams(strParams);
		strParams.add(new BasicNameValuePair("gsid", gsid));
		StringBuffer sb = new StringBuffer();
		sb.append(url).append("paylist.php");
		return baseRequest.getRequest(strParams, sb.toString());
	}

	/**
	 * 收藏图书至书包
	 * 
	 * @param bid
	 * @return
	 * @throws HttpException
	 * @throws IOException
	 */
	public String addCollect(String bid, String gsid) throws TimeoutException, IOException {
		ArrayList<NameValuePair> strParams = new ArrayList<NameValuePair>();
		strParams.clear();
		addShareParams(strParams);
		strParams.add(new BasicNameValuePair("bid", bid));
		strParams.add(new BasicNameValuePair("gsid", gsid));
		StringBuffer sb = new StringBuffer();
		sb.append(url).append("collect.php");
		return baseRequest.getRequest(strParams, sb.toString());
	}

	/**
	 * 收藏列表
	 * 
	 * @param gsid
	 * @return
	 * @throws HttpException
	 * @throws IOException
	 */
	public String getCollectRequest(String gsid) throws TimeoutException, IOException {
		ArrayList<NameValuePair> strParams = new ArrayList<NameValuePair>();
		strParams.clear();
		addShareParams(strParams);
		strParams.add(new BasicNameValuePair("gsid", gsid));

		StringBuffer sb = new StringBuffer();
		sb.append(url).append("collectlist.php");
		return baseRequest.getRequest(strParams, sb.toString());
	}

	/**
	 * 帐号余额
	 * 
	 * @param gsid
	 * @return
	 * @throws ConcurrentModificationException
	 * @throws HttpException
	 * @throws IOException
	 */
	public String getAccountBalance(String gsid) throws TimeoutException, IOException {
		ArrayList<NameValuePair> strParams = new ArrayList<NameValuePair>();
		strParams.clear();
		addShareParams(strParams);
		strParams.add(new BasicNameValuePair("gsid", gsid));
		StringBuffer sb = new StringBuffer();
		sb.append(url).append("userinfo.php");
		return baseRequest.getRequest(strParams, sb.toString());
	}

	/**
	 * 提交意见反馈
	 * 
	 * @param content
	 * @return
	 * @throws HttpException
	 * @throws IOException
	 */
	public String postFeedbackRequest(String content) throws TimeoutException, IOException {
		ArrayList<NameValuePair> strParams = new ArrayList<NameValuePair>();
		strParams.clear();
		addShareParams(strParams);
		if (content != null && content.length() > 0) {
			strParams.add(new BasicNameValuePair("mesg", content));
		}

		StringBuffer sb = new StringBuffer();
		sb.append(url).append("complain.php");
		return baseRequest.getRequest(strParams, sb.toString());
	}

	/**
	 * 版本升级
	 * 
	 * @param name
	 * @param password
	 * @return
	 * @throws HttpException
	 * @throws IOException
	 */
	public String getUpdateInfoRequest(String version) throws TimeoutException, IOException {
		ArrayList<NameValuePair> strParams = new ArrayList<NameValuePair>();
		strParams.clear();
		addShareParams(strParams);
		if (version != null && version.length() > 0) {
			strParams.add(new BasicNameValuePair("client", "Android"));
			strParams.add(new BasicNameValuePair("version", version));
		}
		StringBuffer sb = new StringBuffer();
		sb.append(url).append("getversion.php");
		return baseRequest.postRequest(strParams, sb.toString());
	}

	/**
	 * 排行
	 * 
	 * @param date_type
	 * @param page
	 * @param perpage
	 * @return
	 * @throws HttpException
	 * @throws IOException
	 */
	public String getTopListRequest(String date_type, String page, String perpage) throws TimeoutException, IOException {
		ArrayList<NameValuePair> strParams = new ArrayList<NameValuePair>();
		strParams.clear();
		addShareParams(strParams);

		if (null != date_type && date_type.length() > 0) {
			strParams.add(new BasicNameValuePair("date_type", date_type));
		}
		if (null != page && page.length() > 0) {
			strParams.add(new BasicNameValuePair("page", page));
		}
		if (null != perpage && perpage.length() > 0) {
			strParams.add(new BasicNameValuePair("perpage", perpage));
		}
		StringBuffer sb = new StringBuffer();
		sb.append(url).append("toplist.php");
		return baseRequest.getRequest(strParams, sb.toString());
	}

	public String getBookIntroRequest(String bid) throws TimeoutException, IOException {
		ArrayList<NameValuePair> strParams = new ArrayList<NameValuePair>();
		strParams.clear();
		addShareParams(strParams);
		if (null != bid && bid.length() > 0) {
			strParams.add(new BasicNameValuePair("bid", bid));
		}
		StringBuffer sb = new StringBuffer();
		sb.append(url).append("bookinfo.php");
		return baseRequest.getRequest(strParams, sb.toString());
	}

	public String getChaptersRequest(String bid, String page, String perpage, String gsid) throws TimeoutException,
			IOException {
		ArrayList<NameValuePair> strParams = new ArrayList<NameValuePair>();
		strParams.clear();
		addShareParams(strParams);
		if (!TextUtils.isEmpty(bid)) {
			strParams.add(new BasicNameValuePair("bid", bid));
		}
		if (!TextUtils.isEmpty(page)) {
			strParams.add(new BasicNameValuePair("page", page));
		}
		if (!TextUtils.isEmpty(perpage)) {
			strParams.add(new BasicNameValuePair("perpage", perpage));
		}
		if (!TextUtils.isEmpty(gsid)) {
			strParams.add(new BasicNameValuePair("gsid", gsid));
		}

		StringBuffer sb = new StringBuffer();
		sb.append(url).append("chapterlist.php");
		return baseRequest.getRequest(strParams, sb.toString());
	}

	/**
	 * 获取评论
	 * 
	 * @param bid
	 * @param page
	 * @param perpage
	 * @return
	 * @throws HttpException
	 * @throws IOException
	 */
	public String getCommentsRequest(String bid, String page, String perpage) throws TimeoutException, IOException {
		ArrayList<NameValuePair> strParams = new ArrayList<NameValuePair>();
		strParams.clear();
		addShareParams(strParams);
		if (null != bid && bid.length() > 0) {
			strParams.add(new BasicNameValuePair("bid", bid));
		}
		if (null != page && page.length() > 0) {
			strParams.add(new BasicNameValuePair("page", page));
		}
		if (null != perpage && perpage.length() > 0) {
			strParams.add(new BasicNameValuePair("perpage", perpage));
		}
		StringBuffer sb = new StringBuffer();
		sb.append(url).append("comment.php");
		return baseRequest.getRequest(strParams, sb.toString());
	}

	/**
	 * 分类
	 * 
	 * @param cate
	 * @param page
	 * @param perpage
	 * @return
	 * @throws HttpException
	 * @throws IOException
	 */
	public String getItemsRequest(String cate, String page, String perpage, String type) throws TimeoutException,
			IOException {
		ArrayList<NameValuePair> strParams = new ArrayList<NameValuePair>();
		strParams.clear();
		addShareParams(strParams);
		if (null != cate && cate.length() > 0) {
			strParams.add(new BasicNameValuePair("cate", cate));
		}
		if (null != page && page.length() > 0) {
			strParams.add(new BasicNameValuePair("page", page));
		}
		if (null != perpage && perpage.length() > 0) {
			strParams.add(new BasicNameValuePair("perpage", perpage));
		}
		if (!TextUtils.isEmpty(type)) {
			strParams.add(new BasicNameValuePair("type", type));
		}
		StringBuffer sb = new StringBuffer();
		sb.append(url).append("category.php");
		return baseRequest.getRequest(strParams, sb.toString());
	}

	/**
	 * 获取一个章节的文章内容
	 * 
	 * @param bid
	 * @param cid
	 * @return
	 * @throws HttpException
	 * @throws IOException
	 */
	public String getChapterContent(String bid, String cid, String gsid) throws TimeoutException, IOException {
		ArrayList<NameValuePair> strParams = new ArrayList<NameValuePair>();
		strParams.clear();
		addShareParams(strParams);
		if (!TextUtils.isEmpty(bid)) {
			strParams.add(new BasicNameValuePair("bid", bid));
		}
		if (!TextUtils.isEmpty(cid)) {
			strParams.add(new BasicNameValuePair("cid", cid));
		}
		if (!TextUtils.isEmpty(gsid)) {
			strParams.add(new BasicNameValuePair("gsid", gsid));
		}
		StringBuffer sb = new StringBuffer();
		sb.append(url).append("chapter.php");
		return baseRequest.getRequest(strParams, sb.toString());
	}

	public String sendCommentRequest(String bid, String message, String gsid) throws TimeoutException, IOException {
		ArrayList<NameValuePair> strParams = new ArrayList<NameValuePair>();
		strParams.clear();
		addShareParams(strParams);
		if (!TextUtils.isEmpty(bid)) {
			strParams.add(new BasicNameValuePair("bid", bid));
		}
		if (!TextUtils.isEmpty(message)) {
			strParams.add(new BasicNameValuePair("message", message));
		}
		if (!TextUtils.isEmpty(gsid)) {
			strParams.add(new BasicNameValuePair("gsid", gsid));
		}
		StringBuffer sb = new StringBuffer();
		sb.append(url).append("comment_post.php");
		return baseRequest.getRequest(strParams, sb.toString());
	}

	public String tongjiRequest(String imei) throws IOException, TimeoutException {
		ArrayList<NameValuePair> strParams = new ArrayList<NameValuePair>();
		strParams.clear();
		// addShareParams();

		strParams.add(new BasicNameValuePair("wm", "3333"));
		strParams.add(new BasicNameValuePair("from", "6710095010"));
		if (!TextUtils.isEmpty(imei)) {
			strParams.add(new BasicNameValuePair("IMEI", imei));
		}
		// wm=3333&from=6710095010&IMEI=XXX
		String tongjiUrl = "http://3g.sina.com.cn/3g/site/proc/admin/inner/empty.php";
		return baseRequest.getRequest(strParams, tongjiUrl);
	}
}
