package com.digitalchina.info.bussutil.protal.action.struts;

import java.io.Serializable;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.digitalchina.info.bussutil.protal.service.PortletService;

public class PortletAction extends ExtTemplateAction {
    private PortletService portletService = (PortletService)this.getBean("portletService");
	public PortletService getPortletService() {
		return portletService;
	}

	public void setPortletService(PortletService portletService) {
		this.portletService = portletService;
	}

	@Override
	protected void onInitEntity(HttpServletRequest request, Object o,
			ActionForm form) {
		// TODO Auto-generated method stub
		
	}

    protected String getPageJson(HttpServletRequest request){
    	String json=this.getPortletService().getPortletsJson(super.getHttp().getCurrentIndex(request),super.getHttp().getPageSize(request));
    	return json;
    }
    /**
     *  根据portalColumn id 取得portlet
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward getPortletsByPortalColumn(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response){
    	Map params=this.getRequestParameterMap(request);
    	String portalColumnId=params.get("portalColumnId").toString();
    	String json=this.getPortletService().getPortletsByPortalColumnId(portalColumnId).json();
    	this.jsonPrint(response, json);
    	return null;
    }
    
    /**
     * 根据当前用户ID为用户进行portlet订阅
     */
    public ActionForward getPortletsByUserIdForSubscribe(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response){
    	Serializable userId=this.getHttp().getUserId(request);
    	int startIndex=this.getHttp().getCurrentIndex(request);
    	int pageSize=this.getHttp().getPageSize(request);
    	Serializable portalId=this.getHttp().getParameter(request, "portalId");
    	String json=this.getPortletService().getPagePortletsByUserIdForUserSubscribe(userId, portalId, (startIndex == 0 ? 1 : startIndex), pageSize);
    	this.jsonPrint(response, json);
    	return null;
    }
    
    public ActionForward toPortletSelect(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response){
    	
    	Serializable portalId=this.getHttp().getParameter(request, "portalId");
    	
    	request.setAttribute("portalId", portalId);
    	return mapping.findForward("userSubscrbie");
    }
}
