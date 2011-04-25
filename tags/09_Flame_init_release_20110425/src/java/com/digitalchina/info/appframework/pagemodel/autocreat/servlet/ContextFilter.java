package com.digitalchina.info.appframework.pagemodel.autocreat.servlet;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

public class ContextFilter implements Filter {
	private static String webContext = "";

	public void destroy() {

	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest)request;
		webContext=req.getContextPath();
		chain.doFilter(request,response);

	}

	public void init(FilterConfig arg0) throws ServletException {

	}

	public static String getWebContext() {

		return webContext;
	}

}
