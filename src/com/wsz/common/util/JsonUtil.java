package com.wsz.common.util;

import com.google.gson.Gson;

public class JsonUtil {
	private JsonUtil(){}
	/**
	 * 转换为 json格式
	 * @param 
	 * @return String
	 */
	public static String ObjectToJson(Object obj) {
		Gson gson = new Gson();
		return gson.toJson(obj);
	} 
}
