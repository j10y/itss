package com.xpsoft.framework.web.filter.struts2;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.struts2.dispatcher.FilterDispatcher;
/**
 * 此Filter继承struts2的FilterDispatcher，用于以后扩展
 * @Class Name FrameFilterDispatcher
 * @Author likang
 * @Create In Jul 22, 2010
 */
public class FrameFilterDispatcher extends FilterDispatcher {

	@Override
	public void doFilter(ServletRequest arg0, ServletResponse arg1,
			FilterChain arg2) throws IOException, ServletException {
		// TODO Auto-generated method stub
			super.doFilter(arg0, arg1, arg2);
	}
	
	
}
