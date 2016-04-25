package com.telecom.job;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CookieJob {

	/**
	 * return user visit count
	 * @param request
	 * @param cName
	 * @param value
	 * @return
	 */
	public static String getValue(HttpServletRequest request,String cName,String value) {
		Cookie[] cookies = request.getCookies();
		if(cookies != null) {
			for(int i = 0; i < cookies.length; i++) {
				Cookie cookie = cookies[i];
				if(cName.equals(cookie.getName())) {
					return cookie.getValue();
				}
			}
		}
		return value;
	}

	/**
	 * get cookie value
	 * @param request
	 * @param cName
	 * @return
	 */
	public static Cookie getCookie(HttpServletRequest request,String cName) {
		Cookie[] cookies = request.getCookies();
		if(cookies != null) {
			for(int i = 0; i < cookies.length; i++) {
				Cookie cookie = cookies[i];
				if(cookie != null && cName.equals(cookie.getName())) {
					return cookie;
				}
			}
		}
		return null;
	}
	/**
	* add cookie
	* @param response
	* @param namecookie key value
	* @param valuecookie value
	* @param pathcookie path
	* @param domaincookie 
	* @param timeoutcookie 
	* 
	* @author： licy
	*/
	public static void addCookie(HttpServletResponse response,String name,String value,String path,String domain,int timeout) {
		Cookie cookie = new Cookie(name, value);
		if(domain == null) {
			domain = "admin.hotzz.cn";//Constant.PASSPORTDOMAIN;
		}
		if(path == null) {
			path = "/";
		}
//		cookie.setDomain(domain);
		cookie.setPath(path);
		cookie.setMaxAge(timeout);
		response.addCookie(cookie);
	}
	/**
	* delete cookie
	* @param request
	* @param response
	* @param namecookie的名称
	* 
	* @author： licy
	*/
	public static void delCookie(HttpServletRequest request,HttpServletResponse response,String name) {
		Cookie[] cookies = request.getCookies();
		for(Cookie cookie : cookies) {
			if(cookies != null && (name).equals(cookie.getName())) {
				addCookie(response,name,null,null,null,0);
				return;
			}
		}
	}
	/**
	* update cookie value
	* @param request
	* @param response
	* @param name
	* @param value
	* 
	* @author： licy
	*/
	public static void updateCookie(HttpServletRequest request,HttpServletResponse response,String name,String value) {
		Cookie[] cookies = request.getCookies();
		for(Cookie cookie : cookies) {
			if(cookies != null && (name).equals(cookie.getName())) {
				addCookie(response,name,value,cookie.getPath(),cookie.getDomain(),cookie.getMaxAge());
				return;
			}
		}
	}

	/**
	 * get hotzz login_name
	 * @param request
	 * @return
	 */
	public static String getLoginName(HttpServletRequest request){
		Cookie cookie = getCookie(request, "hotzz_login_name");
		if(cookie != null){
			return cookie.getValue();
		}else{
			return null;
		}
	}
	
	/**
	 * get hotzz password
	 * @param request
	 * @return
	 */
	public static String getPassword(HttpServletRequest request){
		Cookie cookie = getCookie(request, "hotzz_password");
		if(cookie != null){
			return cookie.getValue();
		}else{
			return null;
		}
	}
	
	public static void addUserCookie(HttpServletRequest request, HttpServletResponse response){
		
	}
	
}
