package com.telecom.util;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class ParseURL {

 	public static Map<String, Object> parseURL(String url){
 		Map<String, Object> map = new HashMap<String, Object>();
 		String params = url.split("\\?")[1];
 		String[] paramArray = params.split("&");
 		for (String param : paramArray) {
 			String key = param.split("=")[0];
 			String value = param.split("=")[1];
			map.put(key, value);
		}
 		return map;
 	}
 	
 	public static Map<String, Object> parseURLByResquestMap(Map<String, String[]> url) throws UnsupportedEncodingException{
 		Map<String, Object> map = new HashMap<String, Object>();
 		for (Entry<String, String[]> entry : url.entrySet()) {
			String key = entry.getKey();
			String[] valueArray = entry.getValue();
			String value = new String(valueArray[0].getBytes("iso8859-1"),"utf-8");
			Map<String, Object> valueMap = new HashMap<String, Object>();
			if(value.indexOf("?") > -1){
				valueMap = parseURL(valueArray[0]);
				codyMap(map, valueMap);
			}
			else if(valueMap != null){
				map.put(key, value);
			}
		}
 		return map;
 	}
 	
 	public static void codyMap(Map<String, Object> newMap, Map<String, Object> oldMap){
 		for (Entry<String, Object> oldEntry : oldMap.entrySet()) {
 			newMap.put(oldEntry.getKey(), oldEntry.getValue());
		}
 	}
 	 	
	public static void main(String[] args) {
		String url = "http://news.com?username=xxx&role=xxx&module=xxx";
		System.out.println(parseURL(url));
	}
	
}
