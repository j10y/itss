package com.digitalchina.info.framework.web.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * HttpFilter����servlet��Filter������չ��
 * Ӧ�����е�filter���̳�HttpFilter����ʵ�ֻ���HttpЭ���
 * ����������
 * @Class Name HttpFilter
 * @author xiaofeng
 * @Create In Oct 24, 2007
 */
public abstract class HttpFilter implements Filter {
	
	private FilterConfig config;

	public void destroy() {
	}
	
	public HttpFilter(){
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) //����Filter�ķ���
								throws IOException, ServletException {
		doFilter((HttpServletRequest)request, (HttpServletResponse)response,chain);
	}

	public abstract void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) 
				throws IOException, ServletException ;
	
	public void init(FilterConfig config) throws ServletException { 
		this.config=config;
		init();					
	}					
	
	public void init() throws ServletException {
		
		
	}
	
	public FilterConfig getFilterConfig(){
		return this.config;
	}
	
	public String getInitParameter(String name){
		return getInitParameter(name);
	}
	
	public ServletContext getServletContext(){
		return config.getServletContext();
	}

	
	
}
