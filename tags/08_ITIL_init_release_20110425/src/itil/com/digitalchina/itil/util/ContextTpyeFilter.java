package com.digitalchina.itil.util;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * ����response��дJSON������
 * �����򵥵��¼webseal��ɵ�json�����쳣����
 * @Class Name ContextTpyeFilter
 * @Author lee
 * @Create In Apr 7, 2010
 */
public class ContextTpyeFilter implements Filter {

	public void destroy() {
		// TODO Auto-generated method stub

	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		// TODO Auto-generated method stub
	    response.setContentType("text/plain");
		chain.doFilter(request, response);
	}

	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub

	}

}
