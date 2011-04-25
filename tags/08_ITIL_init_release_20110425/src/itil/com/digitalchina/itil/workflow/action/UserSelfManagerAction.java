package com.digitalchina.itil.workflow.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import com.digitalchina.info.appframework.pagemodel.entity.PageModel;
import com.digitalchina.info.appframework.pagemodel.entity.PagePanel;
import com.digitalchina.info.framework.context.ContextHolder;
import com.digitalchina.info.framework.context.UserContext;
import com.digitalchina.info.framework.web.adapter.struts2.BaseAction;
import com.digitalchina.info.framework.workflow.ParameterService;
import com.digitalchina.info.framework.workflow.TaskService;
import com.digitalchina.info.framework.workflow.WorkflowConstants;
import com.digitalchina.info.framework.workflow.entity.VirtualDefinitionInfo;
import com.digitalchina.info.framework.workflow.info.TaskInfo;
import com.digitalchina.itil.require.service.BusinessAccountService;
import com.digitalchina.itil.service.entity.ServiceItem;
import com.digitalchina.itil.service.entity.ServiceItemProcess;
import com.digitalchina.itil.service.entity.ServiceItemUserTable;
import com.digitalchina.itil.service.service.ServiceItemProcessService;

/**
 * 用户自提交protal用
 * @Class Name UserSelfViewAction
 * @Author lee
 * @Create In Aug 19, 2009
 */
public class UserSelfManagerAction extends BaseAction{
	private TaskService ts = (TaskService)ContextHolder.getBean("taskService");
	private ParameterService pms = (ParameterService)ContextHolder.getBean("parameterService");
	private ServiceItemProcessService sips = (ServiceItemProcessService) ContextHolder.getBean("serviceItemProcessService");
	private BusinessAccountService baService = (BusinessAccountService) ContextHolder.getBean("businessAccountService");
	private String forwardUrl;
	
	public String applyTasks(){
		String json = "";
		int rowCount = 0;
		HttpServletRequest request = super.getRequest();
		String actorId = request.getParameter("actorId");
	  	List<TaskInfo> list = new ArrayList();
	  	list = ts.getAllActiveTaskNodeMessage(actorId);
		for(TaskInfo taskInfo:list) {
			String str = "";
			str += "'defname':'"+taskInfo.getDefinitionName()+"',";
			str += "'defdesc':'"+taskInfo.getDefinitionDesc()+"',";
			str += "nodeName:'"+taskInfo.getNodeName()+"',";
			str += "'taskId':'"+taskInfo.getId()+"',";
			str += "'taskName':'"+taskInfo.getName()+"',";
			//add by guangsa for 需要显示流程开始的时间（用户提交的那个时间）in 20100830 begin
			str += "'createDate':'"+taskInfo.getProcessCreateDate()+"',";
			//add by guangsa for 需要显示流程开始的时间（用户提交的那个时间）in 20100830 end
			//add by guangsa for 流程到我这个环节的时间 in 20100830 begin
			str += "'startDate':'"+taskInfo.getStart()+"',";
			//add by guangsa for 流程到我这个环节的时间 in 20100830 end
			String defDesc = (String)taskInfo.getBizParams().get("defDesc");
			//用实际名称代替用户系统名
			Map bizParams = pms.listVariablesByProcessId(taskInfo.getProcessId());
			JSONObject jo = JSONObject.fromObject(bizParams);
			String strBizParams = jo.toString();
			strBizParams = strBizParams==null||strBizParams.equals("null")?"''":strBizParams;
			if(strBizParams.startsWith("{")) {
				strBizParams = strBizParams.substring(1);
			}
			if(strBizParams.endsWith("}")) {
				strBizParams = strBizParams.substring(0,strBizParams.length()-1);
			}
			
			str += strBizParams+",";
			str += "'comments':'"+toBlank(taskInfo.getComments().getValue("comment"))+"',";
			str += "'defDesc':'"+defDesc+"'";
			str = "{"+str+"},";
			
			String applyUserItCode = (String)bizParams.get("applyUser");//获取流程提交人ITCOD
			String curUser = UserContext.getUserInfo()==null?"":UserContext.getUserInfo().getUserName();	//获取当前用户
			json += str;
		}		
		json = deleteComma(json);
		json =  "{success: true ,data:["+json+"]}";
		HttpServletResponse res = super.getResponse();
		res.setCharacterEncoding("utf-8");
		PrintWriter pw;
		try {
			pw = res.getWriter();
			pw.write(json);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	public String auditTasks(){
		String json = "";
		int rowCount = 0;
		HttpServletRequest request = super.getRequest();
		String actorId = request.getParameter("actorId");
	  	List<TaskInfo> list = new ArrayList();
	  	list = ts.listTasks(actorId);
//	  	list = ts.getAllActiveTaskNodeMessage(actorId);
		for(TaskInfo taskInfo:list) {
			String str = "";
			str += "'defname':'"+taskInfo.getDefinitionName()+"',";
			str += "'defdesc':'"+taskInfo.getDefinitionDesc()+"',";
			str += "nodeName:'"+taskInfo.getNodeName()+"',";
			str += "'taskId':'"+taskInfo.getId()+"',";
			str += "'taskName':'"+taskInfo.getName()+"',";
			//add by guangsa for 需要显示流程开始的时间（用户提交的那个时间）in 20100830 begin
			str += "'createDate':'"+toBlank(taskInfo.getProcessCreateDate())+"',";
			//add by guangsa for 需要显示流程开始的时间（用户提交的那个时间）in 20100830 end
			//add by guangsa for 流程到我这个环节的时间 in 20100830 begin
			str += "'startDate':'"+toBlank(taskInfo.getStart())+"',";
			//add by guangsa for 流程到我这个环节的时间 in 20100830 end
			String applyUserItCode = (String)taskInfo.getBizParams().get("applyUser");//获取流程提交人ITCOD
			String virDesc = (String)taskInfo.getBizParams().get("virDesc");//获取流程提交人ITCOD
			//用实际名称代替用户系统名
			Map bizParams = pms.listVariablesByProcessId(taskInfo.getProcessId());
			
			JSONObject jo = JSONObject.fromObject(bizParams);
			String strBizParams = jo.toString();
			strBizParams = strBizParams==null||strBizParams.equals("null")?"''":strBizParams;
			if(strBizParams.startsWith("{")) {
				strBizParams = strBizParams.substring(1);
			}
			if(strBizParams.endsWith("}")) {
				strBizParams = strBizParams.substring(0,strBizParams.length()-1);
			}
			
			str += strBizParams+",";
			//remove by lee for 删除多余传参 in 20101009
			//str += "'comments':'"+toBlank(taskInfo.getComments().getValue("comment"))+"',";
			str += "'applyUser':'"+applyUserItCode+"',";
			str += "'virDesc':'"+virDesc+"'";
			str = "{"+str+"},";
			
			
			String curUser = UserContext.getUserInfo()==null?"":UserContext.getUserInfo().getUserName();	//获取当前用户
			json += str;
		}		
		json = deleteComma(json);
		json =  "{success: true ,data:["+json+"]}";
		HttpServletResponse res = super.getResponse();
		res.setCharacterEncoding("utf-8");
		PrintWriter pw;
		try {
			pw = res.getWriter();
			pw.write(json);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 获取用户提交的正在处理的数据
	 * @Methods Name task
	 * @Create In Aug 19, 2009 By lee
	 * @return String
	 */
	public String tasks(){
		String json = "";
		int rowCount = 0;
		HttpServletRequest request = super.getRequest();
		//需要的参数
		String actorId = request.getParameter("actorId");
	  	List<TaskInfo> list = ts.getAllActiveTaskNodeMessage(actorId);
		for(TaskInfo taskInfo:list) {
			String str = "";
			str += "'defname':'"+taskInfo.getDefinitionName()+"',";
			str += "'defdesc':'"+taskInfo.getDefinitionDesc()+"',";
			str += "nodeName:'"+taskInfo.getNodeName()+"',";
			str += "'taskId':'"+taskInfo.getId()+"',";
			str += "'taskName':'"+taskInfo.getName()+"',";
			//用实际名称代替用户系统名
			str += "'startDate':'"+toBlank(taskInfo.getStart())+"',";
			Map bizParams = pms.listVariablesByProcessId(taskInfo.getProcessId());
			JSONObject jo = JSONObject.fromObject(bizParams);
			String strBizParams = jo.toString();
			strBizParams = strBizParams==null||strBizParams.equals("null")?"''":strBizParams;
			if(strBizParams.startsWith("{")) {
				strBizParams = strBizParams.substring(1);
			}
			if(strBizParams.endsWith("}")) {
				strBizParams = strBizParams.substring(0,strBizParams.length()-1);
			}
			
			str += strBizParams;
			//remove by lee for 删除多余传参 in 20101009
			//str += "'comments':'"+toBlank(taskInfo.getComments().getValue("comment"))+"'";
			str = "{"+str+"},";
			
			String applyUserItCode = (String)bizParams.get("applyUser");//获取流程提交人ITCOD
			String curUser = UserContext.getUserInfo()==null?"":UserContext.getUserInfo().getUserName();	//获取当前用户
//			if(curUser.equals(applyUserItCode)){
			json += str;
//				rowCount++;
//			}
		}		
		json = deleteComma(json);
		json =  "{success: true, rowCount:'"+rowCount+"',data:["+json+"]}";
		//System.out.print("发送用户提交申请数据"+json);//remove by lee for 去掉数据的控制台输出 in 20091121
		HttpServletResponse res = super.getResponse();
		res.setCharacterEncoding("utf-8");
		PrintWriter pw;
		try {
			pw = res.getWriter();
			pw.write(json);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 显示申请页面
	 * @Methods Name showMeThePage
	 * @Create In Sep 2, 2009 By lee
	 * @return String
	 */
	public String showMeThePage(){
		HttpServletRequest request = super.getRequest();
		HttpServletResponse response = super.getResponse();
		
		String dataId = request.getParameter("dataId");		//获取申请主实体ID
		String applyType = request.getParameter("applyType");//获取流程类型
		String processId = request.getParameter("processId");//获取流程ID
		String serviceItemId = request.getParameter("serviceiItemId");//获取服务项ID（需求、账号部分特有）
		String knowLedgeType = request.getParameter("knowledgeType");	//获取知识部分类型
		
		if(applyType.equals("acproject")){//账号管理部分流程
			ServiceItem serviceItem = (ServiceItem) this.getService().find(ServiceItem.class, serviceItemId);
			ServiceItemUserTable siut = (ServiceItemUserTable) this.getService().findUnique(ServiceItemUserTable.class, "serviceItem", serviceItem);
			String className = siut.getClassName();
			Object applyObj = this.getService().find(this.toClass(className), dataId);//获取申请主实体
			BeanWrapper bw = new BeanWrapperImpl(applyObj);
	//modify by lee for 修正帐号显示不正常的BUG in 20100607 begin		
	//		Integer processType = (Integer) bw.getPropertyValue("processType");
	//		ServiceItemProcess serviceItemProcess = sips.findProcessesByServiceItemAndType(serviceItem, processType);
			
			ServiceItemProcess serviceItemProcess = (ServiceItemProcess) bw.getPropertyValue("serviceItemProcess");
			serviceItemProcess = sips.findServiceItemProcessById(serviceItemProcess.getId().toString());
			Integer processType = serviceItemProcess.getSidProcessType();
	//modify by lee for 修正帐号显示不正常的BUG in 20100607 end			
			PageModel enterPage = serviceItemProcess.getPageModel();
			PagePanel enterPanel = serviceItemProcess.getPagePanel();
			if(enterPage!=null){
				forwardUrl = serviceItemProcess.getPageModel().getPagePath();
			}
			
			VirtualDefinitionInfo virtualDefinitionInfo = serviceItemProcess.getProcessInfo();
			String vname = virtualDefinitionInfo.getVirtualDefinitionName();
			String vdescription = virtualDefinitionInfo.getVirtualDefinitionDesc();
			request.setAttribute("serviceItemId", serviceItemId);
			request.setAttribute("serviceItemProcessId",serviceItemProcess.getId());
			request.setAttribute("processType", processType);
			request.setAttribute("reqClass", className);
			request.setAttribute("processName", vname);
			request.setAttribute("description", vdescription);
			request.setAttribute("status", "1");
			request.setAttribute("dataId", dataId);
			request.setAttribute("readOnly", "1");	//只读标记
			if(enterPage!=null){
				return "forward";
			}else if(enterPanel!=null){
				request.setAttribute("pagePanel", enterPanel.getName());
				return "reqPanelEnter";
			}else{
				request.setAttribute("errorMessage", "未找到流程入口，请联系管理员！");
				return "error";
			}
		}else if(applyType.equals("rproject")){//需求管理部分流程
			ServiceItem serviceItem = (ServiceItem) this.getService().find(ServiceItem.class, serviceItemId);
			ServiceItemUserTable siut = (ServiceItemUserTable) this.getService().findUnique(ServiceItemUserTable.class, "serviceItem", serviceItem);
			String className = siut.getClassName();
			Object applyObj = this.getService().find(this.toClass(className), dataId);//获取申请主实体
			BeanWrapper bw = new BeanWrapperImpl(applyObj);
			Integer processType = (Integer) bw.getPropertyValue("processType");
			ServiceItemProcess serviceItemProcess = sips.findProcessesByServiceItemAndType(serviceItem, processType);
			
			PageModel enterPage = serviceItemProcess.getEndPageModel();
			PagePanel enterPanel = serviceItemProcess.getPagePanel();
			if(enterPage!=null){
				forwardUrl = serviceItemProcess.getEndPageModel().getPagePath()+"?dataId="+dataId;
			}
			
			VirtualDefinitionInfo virtualDefinitionInfo = serviceItemProcess.getProcessInfo();
			String vname = virtualDefinitionInfo.getVirtualDefinitionName();
			String vdescription = virtualDefinitionInfo.getVirtualDefinitionDesc();
			request.setAttribute("serviceItemId", serviceItemId);
			request.setAttribute("serviceItemProcessId",serviceItemProcess.getId());
			request.setAttribute("processType", processType);
			request.setAttribute("reqClass", className);
			request.setAttribute("processName", vname);
			request.setAttribute("description", vdescription);
			request.setAttribute("status", "1");
			request.setAttribute("dataId", dataId);
			request.setAttribute("readOnly", "1");	//只读标记
			if(enterPage!=null){
				return "forward";
			}else 
				if(enterPanel!=null){
				request.setAttribute("pagePanel", enterPanel.getName());
				return "reqPanelEnter";
			}else{
				request.setAttribute("errorMessage", "未找到流程入口，请联系管理员！");
				return "error";
			}
		}else if(applyType.equals("mproject")){//批量配置项变更流程
			request.setAttribute("dataId", dataId);
			return "ciBatchModifyEnter";
		}else if(applyType.equals("eproject")){//事件管理部分流程
			request.setAttribute("dataId", dataId);
			return "eventEnter";
		}else if(applyType.equals("kproject")){//知识管理部分流程
			request.setAttribute("dataId",dataId);
			if(knowLedgeType.equals("6")){//合同类型
				return "knowContractEnter";
			}else if(knowLedgeType.equals("7")){//文件类型
				return "knowFileEnter";
			}else if(knowLedgeType.equals("8")){//解决方案类型
				return "knowProjectEnter";
			}
			return "knowledgeEnter";
		}else if(applyType.equals("nproject")){//公告管理部分流程
			request.setAttribute("dataId",dataId);
			return "noticeEnter";
		}else if(applyType.equals("baproject")){//公告管理部分流程
			request.setAttribute("dataId",dataId);
			if(baService.getRealIncomeByBaId(dataId).isEmpty()){
				return "baPaymentEnter";
			}if(baService.getRealPaymentByBaId(dataId).isEmpty()){
				return "baIncomeEnter";
			}
		}else if(applyType.equals("cproject")){//服务目录管理部分流程
			request.setAttribute("dataId",dataId);
			request.setAttribute("processId",processId);
			return "serviceCataEnter";
		}
		
		return null;
	}
	private String toBlank(Object o){
		return o==null?"":(String)o;		
	}
	private Class toClass(String className){
		Class clazz = null;
		try {
			clazz = Class.forName(className);
		} catch (ClassNotFoundException e) {
			System.out.print(className+"类不存在！");
			e.printStackTrace();
		}
		return clazz;
	}
	private String deleteComma(String json){
		if(json.endsWith(",")) {
			json = json.substring(0, json.length()-1);
		}
		return json;
	}
	/**
	 * 为需求管理提供入口页面链接
	 * @Methods Name getUrlForReq
	 * @Create In Sep 2, 2009 By lee
	 * @param dataId
	 * @param serviceItemId
	 * @return String
	 */
	private String getUrlForReq(String dataId, String serviceItemId){
		
		return null;
	}
	public String getForwardUrl() {
		return forwardUrl;
	}
	public void setForwardUrl(String forwardUrl) {
		this.forwardUrl = forwardUrl;
	}
}
