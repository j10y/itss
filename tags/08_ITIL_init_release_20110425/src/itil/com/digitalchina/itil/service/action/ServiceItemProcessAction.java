package com.digitalchina.itil.service.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.jbpm.graph.def.ProcessDefinition;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import com.digitalchina.info.appframework.pagemodel.entity.PageModel;
import com.digitalchina.info.appframework.pagemodel.entity.PagePanel;
import com.digitalchina.info.framework.context.ContextHolder;
import com.digitalchina.info.framework.util.BeanUtil;
import com.digitalchina.info.framework.util.HttpUtil;
import com.digitalchina.info.framework.web.adapter.struts.BaseDispatchAction;
import com.digitalchina.info.framework.workflow.DefinitionService;
import com.digitalchina.info.framework.workflow.entity.DefinitionInfo;
import com.digitalchina.info.framework.workflow.entity.VirtualDefinitionInfo;
import com.digitalchina.itil.service.entity.Constants;
import com.digitalchina.itil.service.entity.ServiceItem;
import com.digitalchina.itil.service.entity.ServiceItemProcess;
import com.digitalchina.itil.service.entity.ServiceItemUserTable;
import com.digitalchina.itil.service.service.ServiceItemProcessService;
import com.digitalchina.itil.service.service.ServiceItemService;
import com.digitalchina.itil.service.service.ServiceItemUserTableService;
/**
 * 服务项流程配置
 * @Class Name ServiceItemProcessAction
 * @Author lee
 * @Create In Feb 24, 2009
 */
public class ServiceItemProcessAction extends BaseDispatchAction{
	ServiceItemProcessService sips = (ServiceItemProcessService) getBean("serviceItemProcessService");
	ServiceItemUserTableService siuts = (ServiceItemUserTableService) getBean("serviceItemUserTableService"); 
	ServiceItemService sis = (ServiceItemService) getBean("serviceItemService");
	DefinitionService ds = (DefinitionService)ContextHolder.getBean("definitionService");
	/**
	 * 为服务项流程列表页面提供数据
	 * @Methods Name list
	 * @Create In Feb 24, 2009 By lee
	 * @param mapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception ActionForward
	 */
	public ActionForward list(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String serviceItemId = request.getParameter("serviceItemId");
		List processes = null;
		if(StringUtils.isNotBlank(serviceItemId)){
			ServiceItem serviceItem = sis.findServiceItemById(serviceItemId);
			request.setAttribute("serviceItem", serviceItem);
			processes = sips.findProcessesByServiceItem(serviceItem);
			ServiceItemUserTable siut = siuts.findServiceItemUserTableByServiceItem(serviceItem);
			request.setAttribute("siut", siut);
		}
		request.setAttribute("processes", processes);
		return mapping.findForward("list");
	}
	/**
	 * 添加服务项流程配置信息
	 * @Methods Name add
	 * @Create In Feb 24, 2009 By lee
	 * @param mapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception ActionForward
	 */
	public ActionForward toAddProcess(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response)throws Exception{
		String serviceItemId = request.getParameter("serviceItemId");
		//java.util.List<DefinitionInfo> list = ds.getLatestDefinitions();
		List<VirtualDefinitionInfo> list = getService().findAll(VirtualDefinitionInfo.class);
		request.setAttribute("definations", list);
		List processes = null;
		//处理流程名
		if(StringUtils.isNotBlank(serviceItemId)){
			ServiceItem serviceItem = sis.findServiceItemById(serviceItemId);
			request.setAttribute("serviceItem", serviceItem);
			processes = sips.findProcessesByServiceItem(serviceItem);
			ServiceItemUserTable siut = siuts.findServiceItemUserTableByServiceItem(serviceItem);
			request.setAttribute("siut", siut);
		}
		List pagePanels = super.getService().findAll(PagePanel.class);
		//add by lee for modify process entry in 20090519 begin
		List pageModels = super.getService().findAll(PageModel.class);
		request.setAttribute("pageModels", pageModels);
		//add by lee for modify process entry in 20090519 end
		request.setAttribute("pagePanels", pagePanels);
		request.setAttribute("processes", processes);
		return mapping.findForward("list");
	}
	/**
	 * 添加服务项流程配置信息
	 * @Methods Name add
	 * @Create In Feb 24, 2009 By lee
	 * @param mapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception ActionForward
	 */
	public ActionForward toEditProcess(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response)throws Exception{
		String serviceItemId = request.getParameter("serviceItemId");
		String id = request.getParameter("id");
		//java.util.List<DefinitionInfo> list = ds.getLatestDefinitions();
		List<VirtualDefinitionInfo> list = getService().findAll(VirtualDefinitionInfo.class);
		request.setAttribute("definations", list);
		ServiceItemProcess serviceItemProcess = sips.findServiceItemProcessById(id);
		request.setAttribute("process", serviceItemProcess);
		List processes = null;
		if(StringUtils.isNotBlank(serviceItemId)){
			ServiceItem serviceItem = sis.findServiceItemById(serviceItemId);
			request.setAttribute("serviceItem", serviceItem);
			processes = sips.findProcessesByServiceItem(serviceItem);
			ServiceItemUserTable siut = siuts.findServiceItemUserTableByServiceItem(serviceItem);
			request.setAttribute("siut", siut);
		}
		List pagePanels = super.getService().findAll(PagePanel.class);
		//add by lee for modify process entry in 20090519 begin
		List pageModels = super.getService().findAll(PageModel.class);
		request.setAttribute("pageModels", pageModels);
		//add by lee for modify process entry in 20090519 end
		request.setAttribute("pagePanels", pagePanels);
		request.setAttribute("processes", processes);
		return mapping.findForward("list");
	}
	/**
	 * 保存服务项流程配置关系
	 * @Methods Name save
	 * @Create In Feb 24, 2009 By lee
	 * @param mapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception ActionForward
	 */
	public ActionForward save(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response)throws Exception{
		String serviceItemId = request.getParameter("serviceItemId");
		String s2=request.getParameter("pagePanel");
		ServiceItemProcess serviceItemProcess = (ServiceItemProcess) BeanUtil.getObject(request, ServiceItemProcess.class);
		ServiceItem serviceItem = sis.findServiceItemById(serviceItemId);
		serviceItemProcess.setServiceItem(serviceItem);
		String id = request.getParameter("id");
		String definition = request.getParameter("definitionName");
		if(StringUtils.isBlank(definition)){
			if(StringUtils.isNotBlank(id)){
				ServiceItemProcess oldProcess = sips.findServiceItemProcessById(serviceItemProcess.getId().toString());
				serviceItemProcess.setDefinitionName(oldProcess.getDefinitionName());
			}
		}else{
			VirtualDefinitionInfo processDefinition = (VirtualDefinitionInfo) getService().find(VirtualDefinitionInfo.class,definition,true);
			serviceItemProcess.setDefinitionName(processDefinition.getVirtualDefinitionDesc());
		}
		if(serviceItemProcess.getId()==null){
			ServiceItemUserTable siut = siuts.findServiceItemUserTableByServiceItem(serviceItem);
			serviceItemProcess.setPageListPanel(siut.getPageListPanel());
			serviceItemProcess.setPagePanel(siut.getPagePanel());
		}
		
		sips.save(serviceItemProcess);
		
		request.setAttribute("serviceItem", serviceItem);
		List processes = sips.findProcessesByServiceItem(serviceItem);
		request.setAttribute("processes", processes);
		ServiceItemUserTable siut = siuts.findServiceItemUserTableByServiceItem(serviceItem);
		request.setAttribute("siut", siut);
		List pagePanels = super.getService().findAll(PagePanel.class);
		request.setAttribute("pagePanels", pagePanels);
		//add by lee for modify process entry in 20090519 begin
		List pageModels = super.getService().findAll(PageModel.class);
		request.setAttribute("pageModels", pageModels);
		//add by lee for modify process entry in 20090519 end
		return mapping.findForward("list");
	}
	/**
	 * 删除服务项流程配置关系
	 * @Methods Name remove
	 * @Create In Feb 24, 2009 By lee
	 * @param mapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception ActionForward
	 */
	public ActionForward remove(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response)throws Exception{
		String[] ids = request.getParameterValues("id");
		int pageNo = HttpUtil.getInt(request, "pageNo", 1);
		for(int i=0;i<ids.length;i++){
		ServiceItemProcess serviceItemProcess = sips.findServiceItemProcessById(ids[i]);
		sips.remove(serviceItemProcess);
		}
		String serviceItemId = request.getParameter("serviceItemId");
		return HttpUtil.redirect("serviceItemProcessAction.do?methodCall=list&serviceItemId="+serviceItemId+"&pageNo="+pageNo);
	}
//	/**
//	 * 跳转至用户协议页面，如无用户协议则直接进入
//	 * @Methods Name toAgreementPage
//	 * @Create In May 19, 2009 By lee
//	 * @param mapping
//	 * @param actionForm
//	 * @param request
//	 * @param response
//	 * @return
//	 * @throws Exception ActionForward
//	 */
//	public ActionForward toAgreementPage(ActionMapping mapping,
//			ActionForm actionForm, HttpServletRequest request,
//			HttpServletResponse response)throws Exception{
//		String processTypeStr = request.getParameter("processType");
//		String serviceItemId = request.getParameter("serviceItemId");
//		String id = request.getParameter("dataId");
//		ServiceItem serviceItem = sis.findServiceItemById(serviceItemId);
//		ServiceItemProcess serviceItemProcess = sips.findProcessesByServiceItemAndType(serviceItem, Integer.valueOf(processTypeStr));
//		PageModel pageModel = serviceItemProcess.getPageModel();
//		if(serviceItemProcess.getAgreement()==null||"".equals(serviceItemProcess.getAgreement())){
//			ServiceItemUserTable serviceItemUserTable= siuts.findServiceItemUserTableByServiceItem(serviceItem);
//			String className = serviceItemUserTable.getClassName();
//			Class clazz = null;
//			clazz = Class.forName(className);
//			Integer processType = serviceItemProcess.getSidProcessType();
//			Integer status = Constants.STATUS_DRAFT;
//			if(StringUtils.isNotBlank(id)){
//			Object object= getService().find(clazz, id, true);
//			BeanWrapper bw = new BeanWrapperImpl(object);
//			processType = (Integer) bw.getPropertyValue("processType");
//			status = (Integer) bw.getPropertyValue("status");
//			}
//			if(id==null){
//				id="";
//			}
//			String virtualDesc= serviceItemProcess.getDefinitionName();
//			/***************根据虚拟流程描述（不会重复，在前台做了校验）得到相应的虚拟流程*********************/
//			VirtualDefinitionInfo virtualDefinitionInfo=(VirtualDefinitionInfo)getService().findUnique(VirtualDefinitionInfo.class, "virtualDefinitionDesc", virtualDesc);
//			String vname = virtualDefinitionInfo.getVirtualDefinitionName();
//			String vdescription = virtualDefinitionInfo.getVirtualDefinitionDesc();
//			String pagePanelName = serviceItemProcess.getPagePanel().getName();
////			request.setAttribute("serviceItemId", serviceItemId);
////			request.setAttribute("processType", processType);
////			request.setAttribute("pagePanel", pagePanelName);
////			request.setAttribute("reqClass", className);
////			request.setAttribute("processName", vname);
////			request.setAttribute("description", vdescription);
////			request.setAttribute("status", status);
////			request.setAttribute("dataId", id);
//			return HttpUtil.redirect(pageModel.getPagePath()+
//					"?serviceItemId="+serviceItemId+
//					"&processName="+vname+
//					"&status="+status+
//					"&dataId="+id);
//		}else{
//			request.setAttribute("process", serviceItemProcess);
//			return mapping.findForward("agreement");
//		}
//	}
	/**
	 * 跳转至申请入口页面
	 * @Methods Name toApplyPage
	 * @Create In May 22, 2009 By lee
	 * @param mapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception ActionForward
	 */
	public ActionForward toApplyPage(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response)throws Exception{
		String processTypeStr = request.getParameter("processType");
		String serviceItemId = request.getParameter("serviceItemId");
		String id = request.getParameter("dataId");
		ServiceItem serviceItem = sis.findServiceItemById(serviceItemId);
		ServiceItemProcess serviceItemProcess = sips.findProcessesByServiceItemAndType(serviceItem, Integer.valueOf(processTypeStr));
		PageModel pageModel = serviceItemProcess.getPageModel();
		try{
			ServiceItemUserTable serviceItemUserTable= siuts.findServiceItemUserTableByServiceItem(serviceItem);
			String className = serviceItemUserTable.getClassName();
			Class clazz = null;
			clazz = Class.forName(className);
			Integer processType = serviceItemProcess.getSidProcessType();
			Integer status = Constants.STATUS_DRAFT;
			if(StringUtils.isNotBlank(id)){
			Object object= getService().find(clazz, id, true);
			BeanWrapper bw = new BeanWrapperImpl(object);
			processType = (Integer) bw.getPropertyValue("processType");
			status = (Integer) bw.getPropertyValue("status");
			}
			//modify by lee for change serviceItemProcess to ORM in 200090707 begin
//			String virtualDesc= serviceItemProcess.getDefinitionName();
//			/***************根据虚拟流程描述（不会重复，在前台做了校验）得到相应的虚拟流程*********************/
//			VirtualDefinitionInfo virtualDefinitionInfo=(VirtualDefinitionInfo)getService().findUnique(VirtualDefinitionInfo.class, "virtualDefinitionDesc", virtualDesc);
			
			VirtualDefinitionInfo virtualDefinitionInfo=serviceItemProcess.getProcessInfo();
			//modify by lee for change serviceItemProcess to ORM in 200090707 end
			String vname = virtualDefinitionInfo.getVirtualDefinitionName();
			String vdescription = virtualDefinitionInfo.getVirtualDefinitionDesc();
			String pagePanelName = serviceItemProcess.getPagePanel().getName();
//			request.setAttribute("serviceItemId", serviceItemId);
//			request.setAttribute("processType", processType);
//			request.setAttribute("pagePanel", pagePanelName);
//			request.setAttribute("reqClass", className);
//			request.setAttribute("processName", vname);
//			request.setAttribute("description", vdescription);
//			request.setAttribute("status", status);
//			request.setAttribute("dataId", id);
			return HttpUtil.redirect(pageModel.getPagePath()+
					"?serviceItemId="+serviceItemId+
					"&processName="+vname+
					"&status="+status+
					"&dataId="+id);
		}catch(Exception e){
			System.out.println("跳转至入口页面失败！");
			e.printStackTrace();
		}
		return null;
	}
}
