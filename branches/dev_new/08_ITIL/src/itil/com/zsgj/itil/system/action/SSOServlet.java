package com.zsgj.itil.system.action;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionRedirect;

import com.zsgj.info.framework.context.UserContext;

public class SSOServlet extends HttpServlet {

	private static final long serialVersionUID = 2175223370000494306L;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		if (UserContext.getUserInfo() == null) {
			String username = request.getHeader("iv-user");
			if (username != null) {
				request.getSession().setAttribute("SSO", true);
				request.setAttribute("username",username);
				request.setAttribute("password", "SP_SSO");
				request.getRequestDispatcher("/itilsso.jsp").forward(request, response);
			} else {
				response.sendRedirect("/login.jsp");
			}
		} else {
			request.getRequestDispatcher("/index.jsp").forward(request, response);
		}
		
	}
}
