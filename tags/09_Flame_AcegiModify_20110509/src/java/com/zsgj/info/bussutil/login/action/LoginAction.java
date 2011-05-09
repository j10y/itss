/**
 * @Probject Name: IBMB2B
 * @Path: com.digitalchina.ibmb2b.product.actionLoginAction.java
 * @Create By zhangpeng
 * @Create In 2008-6-18 上午09:44:44
 * TODO
 */
package com.zsgj.info.bussutil.login.action;

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

import com.zsgj.info.framework.context.UserContext;
import com.zsgj.info.framework.security.entity.SecurityMessageInfo;
import com.zsgj.info.framework.security.entity.UserInfo;
import com.zsgj.info.framework.web.adapter.struts.BaseDispatchAction;

/**
 * @Class Name LoginAction
 * @Author zhangpeng
 * @Create In 2008-6-18
 */
public class LoginAction extends BaseDispatchAction {

	public ActionForward firstInto(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		if (UserContext.getUserInfo() == null) {
			return mapping.findForward("login");
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

		UserContext.setOnlineUser(loginUser);
		System.out.println(request.getSession().getAttribute("ACEGI_SAVED_REQUEST_KEY"));
		
		if(request.getSession().getAttribute("ACEGI_SAVED_REQUEST_KEY") != null){
			String redir = String.valueOf(request.getSession().getAttribute("ACEGI_SAVED_REQUEST_KEY"));
			redir = (String) redir.substring(redir.indexOf("["), redir.indexOf("]"));
			redir = redir.substring(redir.lastIndexOf(this.getProperties("webContext", "b2b")) + this.getProperties("webContext", "b2b").length() + 1);
			System.out.print(redir);
			return new ActionRedirect(redir);
		}
		
		return mapping.findForward("success");
	}

	public ActionForward toLoginSuccessed(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

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

	public ActionForward toLoginFailed(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		PrintWriter pw;
		try {
			response.setCharacterEncoding(this.getProperties(
					"system.characterEncoding", "gbk"));
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

	public ActionForward logout(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		SecurityContextHolder.clearContext();
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
			online_s += "{" 
					+ "id:'"
					+ item.getUserName()
					+ "',"
					+ "text: '" + item.getRealName() + "',"
					+ "uiProvider : com.dc.ui.OnLineUserTreeNodeUI,"
					+ "icon: 'images/cls/user.gif'," 
					+ "leaf:true" 
					+ "},";

		}
		if (online_s.length() > 0) {
			online_s = online_s.substring(0, online_s.length() - 1);
			online_s = "[{id:'root', text:'在线用户', expanded: true, children:["
					+ online_s + "]}]";
			response.setCharacterEncoding(this.getProperties(
					"system.characterEncoding", "gbk"));
			PrintWriter pw = response.getWriter();
			System.out.println(online_s);
			pw.println(online_s);
			pw.flush();
			pw.close();
		}
		return null;
	}
}
