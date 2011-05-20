package com.zsgj.info.framework.web.adapter.struts;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.zsgj.info.framework.context.ContextHolder;
import com.zsgj.info.framework.context.UserContext;
import com.zsgj.info.framework.security.entity.UserInfo;
import com.zsgj.info.framework.security.service.LogUserActionService;
import com.zsgj.info.framework.util.PropertiesUtil;

/**
 * 核心控制器，覆盖struts的ActionServlet。
 * @Class Name BaseController
 * @Author peixf
 * @Create In 2008-3-3
 */

@SuppressWarnings("serial")
public class BaseController extends org.apache.struts.action.ActionServlet {
	/**
	 * 服务层日志记录器
	 */
	protected final Log logger = LogFactory.getLog("servicelog");
	
	protected void process(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
		
		String encoding = getServletContext().getInitParameter("characterEncoding");
		
		request.setCharacterEncoding((encoding == null || encoding.equals("") ? PropertiesUtil.getProperties("system.characterEncoding","GBK") : encoding));
		
		UserInfo user = UserContext.getUserInfo();
		LogUserActionService lua = (LogUserActionService)ContextHolder.getBean("logUserActionService");
		
		lua.saveUserActionLog(request, user);
		
		if(user != null){
			logger.info(user.getRealName() + "(" + user.getUserName()+ ")访问了:" + request.getRequestURI());
		}
		
		
		response.setHeader("Pragma","No-cache"); 
		response.setHeader("Cache-Control","no-cache"); 
		response.setDateHeader("Expires", 0); 

		super.process(request, response);

	}

}
