package com.ihakula.journey.utils;

public class TextUtil {
	public static boolean isChinese(char c) {
		Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
		if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
				|| ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
				|| ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
				|| ub == Character.UnicodeBlock.GENERAL_PUNCTUATION
				|| ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
				|| ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS) {
			return true;
		}
		return false;

	}
	
	public static int getCNLength(String str) {
		char[] ch = str.toCharArray();
		int enLength = 0;
		int cnLength = 0;
		for (int i = 0; i < ch.length; i++) {
			char c = ch[i];
			if (isChinese(c) == true) {
				enLength += 2;
			} else {
				enLength += 1;
			}
		}
		cnLength = enLength%2==0?enLength/2:enLength/2+1;
		return cnLength;
	}
	
	public static String getStringByCNLength(String str,int maxSize) {
		char[] ch = str.toCharArray();
		int enSize = 0;
		int cnSize = 0;
		int index = ch.length;
		for (int i = 0; i < ch.length; i++) {
			char c = ch[i];
			if (isChinese(c) == true) {
				enSize += 2;
			} else {
				enSize += 1;
			}
			cnSize = enSize%2==0?enSize/2:enSize/2+1;

			if (cnSize == maxSize) {
				index = i;
				break;
			}
		}
		return str.substring(0, index);
	}
}
