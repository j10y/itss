package com.digitalchina.info.framework.security.web.adapter.struts.user;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.digitalchina.info.framework.security.entity.Right;
import com.digitalchina.info.framework.security.service.SecurityManageService;
import com.digitalchina.info.framework.util.BeanUtil;
import com.digitalchina.info.framework.web.adapter.struts.BaseDispatchAction;

/**
 * 资源管理action
 * @Class Name ResourceAction
 * @Author peixf
 * @Create In 2008-3-14
 */
public class RightAction extends BaseDispatchAction{
	private SecurityManageService sms = (SecurityManageService) getBean("securityManageService");
	
	/**
	 * 显示所有资源
	 * @Methods Name list
	 * @Create In 2008-3-14 By peixf
	 * @param map
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 * @throws Exception ActionForward
	 */
	public ActionForward listRights(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse httpServletResponse) throws Exception {
	
		//List rights = sms.findRightsAll();
		List rights = sms.findRightsAllForPage();
		request.setAttribute("rights", rights);
		
		JSONArray jsonObject = JSONArray.fromObject(rights);
		//System.out.println(jsonObject.toString());
		
		try {
			httpServletResponse.getWriter().write("{success:" + true + ",rights:"+ jsonObject.toString() + "}");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return null;
		//return mapping.findForward("listRights");
	}
	
	/**
	 * 修改指定资源
	 * @Methods Name toEdit
	 * @Create In 2008-3-14 By peixf
	 * @param map
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception ActionForward
	 */
	public ActionForward toEditRight(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse httpServletResponse) throws Exception {
		
		List rights = sms.findRightsAll();
		request.setAttribute("rights", rights);
		
		/*List modules = sms.findModulesAll();
		request.setAttribute("modules", modules);*/
		
		String rightId = request.getParameter("id");
		Right right = sms.findRightById(rightId);
		request.setAttribute("right", right);
		
		return mapping.findForward("listRights"); //本页快速修改
	}
	
	/**
	 * 去添加一个资源
	 * @Methods Name toAddRight
	 * @Create In 2008-3-14 By peixf
	 * @param mapping
	 * @param actionForm
	 * @param request
	 * @param httpServletResponse
	 * @return
	 * @throws Exception ActionForward
	 */
	public ActionForward toAddRight(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse httpServletResponse) throws Exception {
		
		List rights = sms.findRightsAll();
		request.setAttribute("rights", rights);
		
		/*List modules = sms.findModulesAll();
		request.setAttribute("modules", modules);*/
		
		
		
		return mapping.findForward("listRights"); //本页快速修改
	}
	
	/**
	 * 保存资源
	 * @Methods Name saveRight
	 * @Create In 2008-3-14 By peixf
	 * @param mapping
	 * @param actionForm
	 * @param request
	 * @param httpServletResponse
	 * @return
	 * @throws Exception ActionForward
	 */
	public ActionForward saveRight(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse httpServletResponse) throws Exception {
		
		/*String id = request.getParameter("id");
		String name = request.getParameter("name");
		String type = request.getParameter("type");
		String className = request.getParameter("className");
		String methodName = request.getParameter("methodName");
		String moduleId = request.getParameter("moduleId");
		
		
		
		Right res = new Right();
		res.setId(String.valueOf(id);*/
		Right rig = (Right) BeanUtil.getObject(request, Right.class);
		String moduleId = request.getParameter("moduleId");
		
		
		Right right = sms.saveRight(rig);
		
		//List Rights = sms.findRightsAll();
		//request.setAttribute("Rights", Rights);
		

		try {
			httpServletResponse.getWriter().write("{success:" + true + ",id:"+ right.getId() + "}");
			//httpServletResponse.getWriter().write("{success:" + true + ",right:"+ right.toString() + "}");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return null;
		//return HttpUtil.redirect("rightManage.do?methodCall=listRights");
	
	}
	
	public ActionForward removeRight(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse httpServletResponse) throws Exception {

		String RightId = request.getParameter("id");
		sms.removeRight(RightId);
		
		//List Rights = sms.findRightsAll();
		//request.setAttribute("Rights", Rights);
		
		try {
			httpServletResponse.getWriter().write("{success:" + true +"}");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
		//return HttpUtil.redirect("rightManage.do?methodCall=listRights");
	}
	
	public ActionForward findRight(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse httpServletResponse) throws Exception {

		String rightId = request.getParameter("id");
		List right = sms.findRightByIdForPage(rightId);
		
		JSONArray jsonObject = JSONArray.fromObject(right);
		System.out.println(jsonObject.toString());
		
		try {
			httpServletResponse.getWriter().write("{success:" + true + ",right:"+ jsonObject.toString() + "}");
		} catch (IOException e) {
			e.printStackTrace();
			httpServletResponse.getWriter().write("{success:" + false + "}");
		}
		
		return null;
	}
	
	public ActionForward findRightKeyName(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse httpServletResponse) throws Exception {

		String rightId = request.getParameter("id");
		Right right = sms.findRightById(rightId);
		try {
			httpServletResponse.getWriter().write("{success:" + true + ",keyName:"+"\""+ right.getKeyName()+"\""+ "}");
		} catch (IOException e) {
			e.printStackTrace();
			httpServletResponse.getWriter().write("{success:" + false + "}");
		}
		
		return null;
	}
	
	public ActionForward getRight(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse httpServletResponse) throws Exception {
		
		List rightList = sms.findRightsAllForPage();
		
		JSONArray jsonObject = JSONArray.fromObject(rightList);
		System.out.println(jsonObject.toString());
		
		try {
			httpServletResponse.getWriter().write("{success:" + true + ",rightList:"+ jsonObject.toString() + "}");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
}
