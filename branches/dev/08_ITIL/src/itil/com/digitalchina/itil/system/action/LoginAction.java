package com.digitalchina.itil.system.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.acegisecurity.context.SecurityContextHolder;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionRedirect;

import com.digitalchina.info.framework.context.UserContext;
import com.digitalchina.info.framework.security.entity.Role;
import com.digitalchina.info.framework.security.entity.SecurityMessageInfo;
import com.digitalchina.info.framework.security.entity.UserInfo;
import com.digitalchina.info.framework.security.service.SecurityManageService;
import com.digitalchina.info.framework.util.PropertiesUtil;
import com.digitalchina.info.framework.web.adapter.struts.BaseDispatchAction;

/**
 * @Class Name LoginAction
 * @Author zhangpeng
 * @Create In 2008-6-18
 */
public class LoginAction extends BaseDispatchAction {

	private SecurityManageService sms = (SecurityManageService) getBean("securityManageService");

	public ActionForward firstInto(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		request.getSession().getServletContext().getInitParameter("");
		if (UserContext.getUserInfo() == null) {
			//modify by zhangpengf  for  SSO in 2009-12-15 begin
			String username = request.getHeader("iv-user");
			if (username != null) {
				request.getSession().setAttribute("SSO", true);
				return new ActionRedirect("/j_login.do?j_username=" + username
						+ "&j_password=SP_SSO");
			} else {
				return mapping.findForward("login");
			}
			//modify by zhangpengf  for  SSO in 2009-12-15 end
		} else {
			return mapping.findForward("success");
		}
	}

	public ActionForward logined(ActionMapping mapping, ActionForm actionForm,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		UserInfo loginUser = UserContext.getUserInfo();
		if (loginUser == null) {
			return mapping.findForward("login");
		}
		// add by lee for 为无角色用户添加默认角色 in 20090818 begin
		Set roles = loginUser.getRoles();// 获取当前用户角色
		if (roles.isEmpty()) {
			String defalutRoleId = PropertiesUtil
					.getProperties("system.security.defaultRole");// 获取系统设置自动分配角色ID
			Role defalutRole = (Role) this.getService().find(Role.class,
					defalutRoleId);// 取得系统设置默认角色
			roles.add(defalutRole);
			loginUser.setRoles(roles);
			loginUser = sms.saveUserInfoWithRoles(loginUser);// 保存角色并初始化角色对应菜单
			// 重新登录
			SecurityContextHolder.clearContext();
			request.getSession().removeAttribute("userInfo");
			return mapping.findForward("login");
		}
		// add by lee for 为无角色用户添加默认角色 in 20090818 end
		System.out.println(request.getSession().getAttribute(
				"ACEGI_SAVED_REQUEST_KEY"));

		UserContext.setOnlineUser(loginUser);
		if (request.getSession().getAttribute("ACEGI_SAVED_REQUEST_KEY") != null) {
			String redir = String.valueOf(request.getSession().getAttribute(
					"ACEGI_SAVED_REQUEST_KEY"));
			redir = (String) redir.substring(redir.indexOf("[") + 1, redir
					.indexOf("]"));
			String realUrl = "";
			if(redir.indexOf(this.getProperties("realmurl", "http://itss.digitalcina.com"))>=0){
				realUrl = this.getProperties("realmurl", "http://itss.digitalcina.com");
			}else{
				realUrl = this.getProperties("weburl", "http://10.1.120.53");
			}
			redir = redir.substring(realUrl.length() + 1);
			return new ActionRedirect(redir);
		}

		return mapping.findForward("success");

	}

	public ActionForward toLoginSuccessed(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		Boolean sso=(Boolean)request.getSession().getAttribute("SSO") ;
		//request.getSession().removeAttribute("SSO");
		boolean isSSO = (sso!= null ?sso : false);
		if (isSSO ) {
			return new ActionRedirect("/login.do?methodCall=logined");
		} else {
			PrintWriter pw;
			try {
				pw = response.getWriter();
				pw.println("{success:true,errors:{}}");

				pw.flush();

				pw.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			return null;
		}
		//modify by zhangpengf  for  SSO in 2009-12-15 end
	}

	public ActionForward toLoginFailed(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		PrintWriter pw;
		try {
			response.setCharacterEncoding("gbk");
			pw = response.getWriter();
			SecurityMessageInfo smi = UserContext.getLoginMessage();
			pw.println("{success:false,errors:{title:'" + smi.getTitle()
					+ "',j_password:'" + smi.getMessage() + "'}}");

			pw.flush();

			pw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * 这个方法被安全框架拦截了，一点作用也没有
	 */
	public ActionForward logout(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		SecurityContextHolder.clearContext();
		// UserContext.clearContext();
		request.getSession().removeAttribute("userInfo");
		return mapping.findForward("login");
	}

	public ActionForward loadOnLineUser(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		Set online = UserContext.getOnlineUserList();
		String online_s = "";
		Iterator it = online.iterator();
		while (it.hasNext()) {
			UserInfo item = (UserInfo) it.next();
			online_s += "{" + "id:'" + item.getUserName() + "'," + "text:'"
					+ item.getRealName() + "',"
					+ "uiProvider : com.dc.ui.OnLineUserTreeNodeUI,"
					+ "leaf:true" + "},";

		}
		if (online_s.length() > 0) {
			online_s = online_s.substring(0, online_s.length() - 1);
			online_s = "[{id:'root', text:'在线用户', expanded: true, children:["
					+ online_s + "]}]";
			response.setCharacterEncoding(this.getProperties(
					"system.characterEncoding", "gbk"));
			PrintWriter pw = response.getWriter();
			//System.out.println(online_s);
			pw.println(online_s);
			pw.flush();
			pw.close();
		}
		return null;
	}
}
