package com.wsz.common.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 字串工具类
 * 
 * @author kingapex 2010-1-6下午01:52:58
 */
public class StringUtil {
	private StringUtil(){}

	/**
	 * 字符串是否为空
	 * @return true
	 */
	public static boolean isEmpty(Object str) {
		return str == null || "".equals(str);
	}

	/**
	 * MD5加密方法
	 * @param str String字符串
	 * @return String
	 */
	public static String myMd5(String str) {
		return myMd5(str, true);
	}

	public static String myMd5(String str, boolean zero) {
		MessageDigest messageDigest = null;
		try {
			// 指定加密的方式为MD5
			messageDigest = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException ex) {
			ex.printStackTrace();
			return null;
		}
		// 进行加密运算
		byte[] resultByte = messageDigest.digest(str.getBytes());
		StringBuffer result = new StringBuffer();
		for (int i = 0; i < resultByte.length; ++i) {
			// 将整数转换成十六进制形式的字符串 这里与0xff进行与运算的原因是保证转换结果为32位
			int v = 0xFF & resultByte[i];
			if(v<16 && zero)
				result.append("0");
			result.append(Integer.toHexString(v));
		}
		return result.toString();
	}
}

