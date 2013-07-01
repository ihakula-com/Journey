package com.ihakula.journey.utils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import android.util.Log;

public class LoginMD5Util {
	
	public static final String KEY = "UnFcwo8slFwjgRMG9ohy3NSkbCQLwjbW";
	
	public static final String C = "iread";

	public static String md5(String str){
//		Log.e("MyLog", "md5 preStr:" + str);
		String md5 = getMD5Str(str);
//		Log.e("MyLog", "md5 str:" + md5);
		StringBuffer buffer = new StringBuffer();
		buffer.append(md5.substring(1, 2));
		buffer.append(md5.substring(5, 6));
		buffer.append(md5.substring(2, 3));
		buffer.append(md5.substring(10, 11));
		buffer.append(md5.substring(17, 18));
		buffer.append(md5.substring(9, 10));
		buffer.append(md5.substring(25, 26));
		buffer.append(md5.substring(27, 28));
		String s = buffer.toString().toLowerCase();
//		Log.e("MyLog", "md5 s:" + s);
		return s;
	}
	
	/**
     * md5加密
     * @param str 需要md5加密的信息
     * @return 加密后的md5字符串
     */
    public static String getMD5Str(String str) {
        MessageDigest messageDigest = null;

        try {
            messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.reset();
            messageDigest.update(str.getBytes("UTF-8"));
        } catch (NoSuchAlgorithmException e) {
            Log.e("MyLog", e.getMessage());
        } catch (UnsupportedEncodingException e) {
        	Log.e("MyLog", e.getMessage());
        }

        byte[] byteArray = messageDigest.digest();
        return byte2hex(byteArray);
    }
    
    /**
     *  二行制转字符串
     * @param b
     * @return
     */
    public static String byte2hex(byte[] b)
    {
        String hs = "";
        String stmp = "";
        for (int n = 0; n < b.length; n++) {
            stmp = Integer.toHexString(b[n] & 0XFF);
            if (stmp.length() == 1) {
                hs = hs + "0" + stmp;
            } else {
                hs = hs + stmp;
            }
        }
        return hs.toLowerCase();
    }
}
