package com.telecom.util;
/**
 * 创建时间：2015年11月14日 上午3:31:57
 * 项目名称：monitor
 * @author zhangk
 * @version 1.0
 * @since JDK 1.6.0_21
 * 文件名称：PageParam.java
 * 类说明：
 */
public class PageParam {
	public static int pagesize=10; //每页显示条数
	public static int pagecount=0; // 总条数
	//起始位置
	public static int minvalue(int pagesize,int pageNb ){
		return (pageNb-1)*pagesize+1;
	}
	//结束位置
	public static int maxvalue(int pagesize,int pageNb ){
		return (pageNb-1)*pagesize+1+pagesize;
	}
}
