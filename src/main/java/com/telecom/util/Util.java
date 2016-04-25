package com.telecom.util;

import java.lang.reflect.Field;
import java.nio.file.Files;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Util {

	static DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	public static String dateFormatString(Date date){
		return dateFormat.format(date);
	}
	
	public static Date dateFormatDate(String str) throws Exception{
		return sdf.parse(str);
	}
	
	public static void copy(Object newObj, Object oldObj) throws Exception{
		Field[] newFields = newObj.getClass().getDeclaredFields();
		for (Field field : newFields) {
			field.setAccessible(true);
			if(field.get(newObj) == null){
				field.set(newObj, field.get(oldObj));
			}
		}
	}
	
}
