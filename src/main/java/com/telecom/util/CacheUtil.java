package com.telecom.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 创建时间：2015年11月5日 下午8:48:19
 * 项目名称：monitor
 * @author zhangk
 * @version 1.0
 * @since JDK 1.6.0_21
 * 文件名称：CacheUtil.java
 * 类说明：
 */
public class CacheUtil {
	public static Map exceptionInfoMap=new HashMap();//异常缓存信息
	public static List exceptionInfoList=new ArrayList();//异常缓存信息
	public static Map dataInforMap=new HashMap();//机器信息缓存
	public static List dataInforList=new ArrayList();//机器信息缓存
	public static Map detailInfo=new HashMap<>(); //实施监测页面数据
	
}
