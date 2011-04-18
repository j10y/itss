/**
 * @Probject Name: 10_InfoFramework
 * @Path: com.digitalchina.info.framework.security.providerLogoutFilter.java
 * @Create By ’≈≈Ù
 * @Create In Nov 10, 2008 3:46:18 PM
 * TODO
 */
package com.digitalchina.info.framework.security.provider;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.acegisecurity.context.SecurityContextHolder;
import org.acegisecurity.ui.logout.LogoutHandler;

import com.digitalchina.info.framework.context.UserContext;
import com.digitalchina.info.framework.security.entity.UserInfo;
import com.digitalchina.info.framework.security.service.AuthenticationCust;

/**
 * @Class Name LogoutFilter
 * @Author ’≈≈Ù
 * @Create In Nov 10, 2008
 */
public class LogoutFilter extends org.acegisecurity.ui.logout.LogoutFilter {

	public LogoutFilter(String logoutSuccessUrl, LogoutHandler[] handlers) {
		super(logoutSuccessUrl, handlers);
		// TODO Auto-generated constructor stub
	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {

		UserInfo user = null;
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;

		if (this.requiresLogout(httpRequest, httpResponse)) {
			AuthenticationCust authen = (AuthenticationCust) SecurityContextHolder
					.getContext().getAuthentication();
			if (authen != null) {
				user = authen.getCurrentUserInfo();
			}
			Boolean SSO = (Boolean) httpRequest.getSession().getAttribute("SSO");
			if(Boolean.TRUE.equals(SSO)){
				UserContext.removeOnlineUser(user);
				httpResponse.sendRedirect("http://dcone.digitalchina.com/pkmslogout");
			}else{
				super.doFilter(request, response, chain);
			}
		}else{
			super.doFilter(request, response, chain);
		}
		if (user != null) {
			UserContext.removeOnlineUser(user);
			System.out.println("remove Online User: " + user.getUserName());
		}
	}
}
