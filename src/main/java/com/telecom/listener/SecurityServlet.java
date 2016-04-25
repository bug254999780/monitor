package com.telecom.listener;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.Logger;

public class SecurityServlet extends HttpServlet implements Filter {

	 Log log = LogFactory.getLog(SecurityServlet.class);
	
	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doFilter(ServletRequest servletRequest , ServletResponse servletResponse,
			FilterChain chain) throws IOException, ServletException {
		//Get code request Object, response Object, session Object
		HttpServletRequest request = (HttpServletRequest) servletRequest;
		HttpServletResponse response =  (HttpServletResponse) servletResponse;
		HttpSession session = request.getSession();
		
		chain.doFilter(servletRequest, servletResponse);
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		log.info("拦截器初始化");
	}

}
