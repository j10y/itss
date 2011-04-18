package com.digitalchina.info.bussutil.protal.action.struts;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.digitalchina.info.bussutil.protal.service.PortalColumnService;

public class PortalColumnAction extends ExtTemplateAction {
     private PortalColumnService portalColumnService = (PortalColumnService)this.getBean("portalColumnService");
	public PortalColumnService getPortalColumnService() {
		return portalColumnService;
	}

	public void setPortalColumnService(PortalColumnService portalColumnService) {
		this.portalColumnService = portalColumnService;
	}

	@Override
	protected void onInitEntity(HttpServletRequest request, Object o,
			ActionForm form) {
		// TODO Auto-generated method stub

	}

	/**
	 * 根据protalContainerId取得portalColumn
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward getPortalColumns(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response){
		String portalId=this.getHttp().getParameter(request, "portalId");
		if(StringUtils.isNotEmpty(portalId)){
			String  json=this.getPortalColumnService().getPortalColumnByPortalId(portalId).json();
			this.jsonPrint(response, json);
		}
		
		return null;
	}

}
