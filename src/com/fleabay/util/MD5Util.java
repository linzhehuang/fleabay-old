package com.fleabay.util;

import java.security.MessageDigest;

public class MD5Util {
	// 计算MD5的值
	public static String sum(String... args) {
		String str = "";
		for(String arg : args) {
			str += arg;
		}
		try {
	        MessageDigest md = MessageDigest.getInstance("MD5");
	        byte[] bytes = md.digest(str.getBytes("utf-8"));
	        return toHex(bytes);
	    }
	    catch (Exception e) {
	        throw new RuntimeException(e);
	    }
	}
	private static String toHex(byte[] bytes) {
	    final char[] HEX_DIGITS = "0123456789ABCDEF".toCharArray();
	    StringBuilder ret = new StringBuilder(bytes.length * 2);
	    for (int i=0; i<bytes.length; i++) {
	        ret.append(HEX_DIGITS[(bytes[i] >> 4) & 0x0f]);
	        ret.append(HEX_DIGITS[bytes[i] & 0x0f]);
	    }
	    return ret.toString();
	}
}
