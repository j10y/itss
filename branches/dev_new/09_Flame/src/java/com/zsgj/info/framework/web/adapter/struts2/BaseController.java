/**
 * @Probject Name: 10_InfoFramework_1
 * @Path: com.digitalchina.info.framework.web.adapter.struts2BaseController.java
 * @Create By ’≈≈Ù
 * @Create In 2009-7-27 œ¬ŒÁ03:46:10
 * TODO
 */
package com.zsgj.info.framework.web.adapter.struts2;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.StrutsStatics;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.zsgj.info.framework.context.ContextHolder;
import com.zsgj.info.framework.context.UserContext;
import com.zsgj.info.framework.security.entity.UserInfo;
import com.zsgj.info.framework.security.service.LogUserActionService;

/**
 * @Class Name BaseController
 * @Author ’≈≈Ù
 * @Create In 2009-7-27
 */
@SuppressWarnings("serial")
public class BaseController extends BaseAroundInterceptor {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.digitalchina.info.framework.web.adapter.struts2.BaseAroundInterceptor
	 * #after(com.opensymphony.xwork2.ActionInvocation, java.lang.String)
	 */
	@Override
	protected void after(ActionInvocation dispatcher, String result)
			throws Exception {
		// TODO Auto-generated method stub
		ActionContext actionContext = dispatcher.getInvocationContext();
		HttpServletRequest request = (HttpServletRequest) actionContext
				.get(StrutsStatics.HTTP_REQUEST);

		UserInfo user = UserContext.getUserInfo();
		LogUserActionService lua = (LogUserActionService) ContextHolder
				.getBean("logUserActionService");
		lua.saveUserActionLog(request, user);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.digitalchina.info.framework.web.adapter.struts2.BaseAroundInterceptor
	 * #before(com.opensymphony.xwork2.ActionInvocation)
	 */
	@Override
	protected void before(ActionInvocation invocation) throws Exception {
		// TODO Auto-generated method stub
		ActionContext actionContext = invocation.getInvocationContext();
		HttpServletRequest request = (HttpServletRequest) actionContext
				.get(StrutsStatics.HTTP_REQUEST);

		UserInfo user = UserContext.getUserInfo();
		LogUserActionService lua = (LogUserActionService) ContextHolder
				.getBean("logUserActionService");
		lua.saveUserActionLog(request, user);
	}

}
