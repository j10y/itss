package com.digitalchina.info.bussutil.protal.action.struts;

import java.io.Serializable;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.digitalchina.info.bussutil.protal.entity.PortalContainer;
import com.digitalchina.info.bussutil.protal.entity.PortletSubscribe;
import com.digitalchina.info.bussutil.protal.service.PortalContainerService;
import com.digitalchina.info.framework.context.UserContext;
import com.digitalchina.info.framework.dao.support.Page;
import com.digitalchina.info.framework.security.entity.UserInfo;
import com.digitalchina.info.framework.util.json.JSONUtil;
import com.digitalchina.info.framework.util.json.StringPool;


public class PortalContainerAction extends ExtTemplateAction {
	private PortalContainerService portalContainerService = (PortalContainerService)this.getBean("portalContainerService");

	@Override
	protected void onInitEntity(HttpServletRequest request, Object o,
			ActionForm form) {
		// TODO Auto-generated method stub

	}


	public PortalContainerService getPortalContainerService() {
		return portalContainerService;
	}

	public void setPortalContainerService(
			PortalContainerService portalContainerService) {
		this.portalContainerService = portalContainerService;
	}

	/**
	 * 判断用户是否在自己的地盘
	 */
	public ActionForward isAtMyPlace(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		Serializable userId = super.getHttp().getUserId(request);
		String username = this.get("user", request);
		StringBuffer json = new StringBuffer("{\"success\":true,\"rows\":[{\"");
		if (userId.equals(StringPool.USER_GUEST_ID)) {
			json.append("login\":\"no\"}]}");
		} else if (StringUtils.isNotEmpty(username)) {
			UserInfo user =UserContext.getUserInfo();
			if (user.getId().equals(userId)) {
				json.append("login\":\"yes\"}]}");
			} else {
				json.append("login\":\"no\"}]}");
			}
		} else {
			json.append("login\":\"yes\"}]}");
		}
		jsonPrint(response, json.toString());
		return null;
	}

	/*
	 * 进入用户自己的portal
	 */
	public ActionForward my(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		Serializable userId = super.getHttp().getUserId(request);
		String username = this.get("user", request);
		if (StringUtils.isNotEmpty(username)) {
			request.getSession().setAttribute(StringPool.BLOG_USER, username);
		} else {
			if (request.getSession().getAttribute(StringPool.BLOG_USER) != null) {
				request.getSession().removeAttribute(StringPool.BLOG_USER);
			}
		}
		if (!this.getPortalContainerService().isExistsUserPortalContainer(
				userId)) {
			this.getPortalContainerService().saveDefaultUserPortalContainer(
					userId);
		}
		return null;
	}

	/**
	 * 添加用户自定义的portal
	 */
	public ActionForward saveUserDefinePortal(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		Serializable userId = super.getHttp().getUserId(request);
		this.getPortalContainerService().saveDefaultUserDefinePortalContainer(
				userId);
		return null;
	}

	/**
	 * 取得用户的portal容器
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward getPortalContainer(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		Serializable userId = super.getHttp().getUserId(request);
		String username = this.get("user", request);
		if (StringUtils.isNotEmpty(username)) {
			UserInfo user =UserContext.getUserInfo();
			if (null != user) {
				userId = user.getId();
			}
		}

		PortalContainer pc = this.getPortalContainerService()
				.getPortalContainerByUserId(userId);
		if(pc == null){
			System.out.println("没找到protal");
			this.getPortalContainerService().saveDefaultUserDefinePortalContainer(userId);
			pc = this.getPortalContainerService().getPortalContainerByUserId(userId);
		}
		
		this.jsonPrint(response, JSONUtil.rowJson(pc.json()));
		return null;
	}

	public ActionForward getNewerPortalContainer(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		Page ps = this.getPortalContainerService()
				.getNewerPortalContainers(
						this.getHttp().getCurrentIndex(request),
						this.getHttp().getPageSize(request));
		if (null != ps) {
			this.jsonPrint(response, ps.json());
		}
		return null;
	}

	/**
	 * portlet订阅
	 * 
	 * @return
	 */
	public ActionForward portletSubscribe(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		Map params = this.getRequestParameterMap(request);
		String portalId = params.get("portalId").toString();
		String portletId = params.get("portletId").toString();
		this.getPortalContainerService().saveUserPortletSubscribe(portalId,
				portletId);
		return null;
	}
	
	public ActionForward saveUserPorletRefersh(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		Serializable userId = super.getHttp().getUserId(request);
		Map params = this.getRequestParameterMap(request);
		String portalId = params.get("porletID").toString();
		String portalColumnId = params.get("porlatColumnID").toString();
		String refersh = params.get("newValue").toString();
		this.getPortalContainerService().saveChangePortletRefersh(portalId, portalColumnId, new Long(refersh));
		return null;
	}
	
	public ActionForward getUserPorletRefersh(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		Serializable userId = super.getHttp().getUserId(request);
		Map params = this.getRequestParameterMap(request);
		String portalId = params.get("porletID").toString();
		String portalColumnId = params.get("porlatColumnID").toString();
		PortletSubscribe ps = this.getPortalContainerService().getPortletSubscribeByPortletAndPortleColumn(portalId, portalColumnId);
		this.jsonPrint(response, JSONUtil.rowJson(ps.jsonRefersh()));
		return null;
	}
	
	public ActionForward removeUserPortletSubscribe(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		Map params = this.getRequestParameterMap(request);
		String portletId = params.get("portletId").toString();
		Serializable portalId = params.get("portalId").toString();
		this.getPortalContainerService().removeUserPorletSubscribe(portletId,
				portalId);
		return null;
	}

	/**
	 * 移动 portlet
	 */
	public ActionForward movePortlet(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		Map params = this.getRequestParameterMap(request);
		String portalId = params.get("portalId").toString();
		String portletId = params.get("portletId").toString();
		String portalColumnId = params.get("portalColumnId").toString();
		int index = Integer.parseInt(params.get("index").toString());
		this.getPortalContainerService().saveChangePortletPosition(portletId,
				portalColumnId, portalId, index);
		return null;
	}

}
