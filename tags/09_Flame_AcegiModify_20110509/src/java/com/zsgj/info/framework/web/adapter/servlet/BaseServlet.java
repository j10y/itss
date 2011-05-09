package com.zsgj.info.framework.web.adapter.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zsgj.info.framework.context.ContextHolder;
import com.zsgj.info.framework.exception.ServiceException;

/**
 * Servlet基类，提供获取服务等方法
 * @Class Name BaseServlet
 * @Author sa
 * @Create In 2008-8-31
 */
public class BaseServlet extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("gbk");
		response.setCharacterEncoding("gbk");
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("gbk");
		response.setCharacterEncoding("gbk");
	}

	/**
	 * 返回spring管理的服务service
	 * @param name
	 * @return
	 */
	protected Object getBean(String name) {
		Object serviceBean = ContextHolder.getBean(name);
		if(serviceBean==null) {
			throw new ServiceException("没有名字为：" + name + "的服务！！");
		}
		return serviceBean;
	}
}
