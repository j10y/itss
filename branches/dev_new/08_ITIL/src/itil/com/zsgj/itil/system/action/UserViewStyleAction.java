package com.zsgj.itil.system.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.zsgj.info.framework.security.entity.Role;
import com.zsgj.info.framework.security.entity.UserInfo;
import com.zsgj.info.framework.security.service.SecurityManageService;
import com.zsgj.info.framework.util.HttpUtil;
import com.zsgj.info.framework.web.adapter.struts.BaseDispatchAction;

public class UserViewStyleAction extends BaseDispatchAction{
	private SecurityManageService sms = (SecurityManageService) getBean("securityManageService");
	/**
	 * 保存用户选择的页面样式
	 * @Methods Name saveStyle
	 * @Create In Jul 3, 2008 By itnova
	 * @param mapping
	 * @param actionForm
	 * @param request
	 * @param httpServletResponse
	 * @return
	 * @throws Exception ActionForward
	 */
	public ActionForward saveStyle(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse httpServletResponse) throws Exception {
				
		String userId = request.getParameter("userId");
		String userViewStyle = request.getParameter("style");
		UserInfo user = sms.findUserById(userId);
		
		Set roles = user.getRoles();
		user.setUserViewStyle(userViewStyle);
		user.setRoles(roles);
		user = sms.saveUserInfoStyleWithRoles(user);
				
		request.getSession().setAttribute("userInfo", user);
		PrintWriter out = null;
		try {
			out = httpServletResponse.getWriter();
			out.write("{success:" +true+ "}");
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			if(out != null)
				out.close();
				
		}
		return null;
	}
}