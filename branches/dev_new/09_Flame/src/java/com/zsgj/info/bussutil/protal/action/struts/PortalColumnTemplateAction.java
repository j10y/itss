package com.zsgj.info.bussutil.protal.action.struts;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.zsgj.info.bussutil.protal.service.PortalColumnService;

public class PortalColumnTemplateAction extends ExtTemplateAction {
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
	
	protected String getPageJson(HttpServletRequest request){
		String json=this.getPortalColumnService().getAllPortalColumnTemplatesJson();
		return json;
	}
	/**
	 * 用于前台版面样式表的显示
	 * @return
	 */
	public ActionForward getPortalColummnTemplates(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response){
		String json=this.getPortalColumnService().getAllPortalColumnTemplatesJson();
		this.jsonPrint(response, json);
		return null;
	}

}
