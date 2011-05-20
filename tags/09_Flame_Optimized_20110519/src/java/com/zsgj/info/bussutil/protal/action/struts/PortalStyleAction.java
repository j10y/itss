package com.zsgj.info.bussutil.protal.action.struts;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionForm;

import com.zsgj.info.bussutil.protal.service.PortalContainerService;


public class PortalStyleAction extends ExtTemplateAction {
    private PortalContainerService portalContainerService = (PortalContainerService)this.getBean("portalContainerService");
	public PortalContainerService getPortalContainerService() {
		return portalContainerService;
	}

	public void setPortalContainerService(
			PortalContainerService portalContainerService) {
		this.portalContainerService = portalContainerService;
	}

	@Override
	protected void onInitEntity(HttpServletRequest request, Object o,
			ActionForm form) {
		// TODO Auto-generated method stub
		

	}

	
	protected String getPageJson(HttpServletRequest request){
        String json=this.getPortalContainerService().getPortalAllStylesJson();
		return json;
	}

}
