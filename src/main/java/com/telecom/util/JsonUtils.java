package com.telecom.util;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonUtils {

	private static Log log = LogFactory.getLog(JsonUtils.class);
	
	//私有的默认构造子  
	private JsonUtils() {}  
	private static JsonUtils single=null;  
	private static ObjectMapper mapper = null;
	//静态工厂方法   
	public static JsonUtils newInstance() {  
		 if (single == null) {    
			 single = new JsonUtils();  
		 }
		return single;  
	}  
	
	public static ObjectMapper singleObjectMapper(){
		if(mapper == null){
			mapper = new ObjectMapper();
		}
		return mapper;
	}
	
	@SuppressWarnings("unchecked")
	public static Map<String, Object> jsonForm2Map(String json) throws Exception{
		if (StringUtils.isEmpty(json)) {  
            return null;  
        }  
        try {  
        	ObjectMapper mapper = singleObjectMapper();
        	Map<String, Object> result = mapper.readValue(json, Map.class);  
        	return result;
        } catch (IOException e) {  
        	log.warn("parse json string error:" + json, e);  
            return null;  
        }  
	}
	
	public static <T> T jsonForm2Bean(String json, Class<T> object) throws Exception{
		if (StringUtils.isEmpty(json)) {  
            return null;  
        }  
        try {  
        	ObjectMapper mapper = singleObjectMapper();
        	Map<String, Object> map = jsonForm2Map(json);
        	Map<String, Object> resultMap = new HashMap<String, Object>();
        	for (Entry<String, Object> entry : map.entrySet()) {
				String key = entry.getKey();
        		Object value = entry.getValue();
				if(value == null || ("password".equals(key) && "".equals(value))){
					continue;
				}
				else{
					resultMap.put(key, value);
				}
			}
        	json = map2json(resultMap);
        	T result = mapper.readValue(json, object);
        	return result;
        } catch (IOException e) {  
        	log.warn("parse json string error:" + json, e);  
            return null;  
        }  
	}
	
	public static String bean2json(Object object) throws Exception{
		try {  
			ObjectMapper mapper = singleObjectMapper();
			String json = mapper.writeValueAsString(object);
			return json;
        } catch (IOException e) {  
            log.warn("write to json string error:" + object, e);  
            return null;  
        }  
	}
	
	public static <T> String map2json(Map<String, Object> map) throws Exception{
		try {  
			ObjectMapper mapper = singleObjectMapper();
			String json = mapper.writeValueAsString(map);
			return json;
        } catch (IOException e) {  
            log.warn("write to json string error:" + map, e);  
            return null;  
        }  
	}
	
	public static <T> T map2bean(Map<String, Object> map, Class<T> object) throws Exception{
		try {  
			String json = map2json(map);
			return  jsonForm2Bean(json, object);
        } catch (IOException e) {  
            log.warn("write to json string error:" + map, e);  
            return null;  
        }  
	}
	
}
