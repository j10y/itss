package com.zsgj.itil.service.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import com.zsgj.info.appframework.metadata.MetaDataManager;
import com.zsgj.info.appframework.metadata.entity.SystemMainTable;
import com.zsgj.info.appframework.metadata.service.SystemMainTableService;
import com.zsgj.info.appframework.pagemodel.entity.PageModel;
import com.zsgj.info.appframework.pagemodel.entity.PagePanel;
import com.zsgj.info.appframework.pagemodel.service.PageModelService;
import com.zsgj.info.appframework.pagemodel.service.PagePanelService;
import com.zsgj.info.framework.context.ContextHolder;
import com.zsgj.info.framework.dao.support.Page;
import com.zsgj.info.framework.security.entity.Module;
import com.zsgj.info.framework.util.HttpUtil;
import com.zsgj.info.framework.web.adapter.struts2.BaseAction;
import com.zsgj.info.framework.workflow.DefinitionService;
import com.zsgj.info.framework.workflow.entity.VirtualDefinitionInfo;
import com.zsgj.itil.service.entity.ServiceItem;
import com.zsgj.itil.service.entity.ServiceItemProcess;
import com.zsgj.itil.service.service.ServiceItemProcessService;
import com.zsgj.itil.service.service.ServiceItemService;
/**
 * 服务项流程处理类（为后台EXTJS页面提供）
 * @Class Name SCIProcessAction
 * @Author lee
 * @Create In Jun 19, 2009
 */
public class SCIProcessAction extends BaseAction{
	ServiceItemService sis = (ServiceItemService) getBean("serviceItemService");
	ServiceItemProcessService sips = (ServiceItemProcessService) getBean("serviceItemProcessService");
	MetaDataManager metaDataManager = (MetaDataManager) ContextHolder.getBean("metaDataManager");
	PagePanelService pps = (PagePanelService) getBean("pagePanelService");
	PageModelService pms = (PageModelService) getBean("pageModelService");
	SystemMainTableService smts = (SystemMainTableService) getBean("systemMainTableService");
	DefinitionService ds = (DefinitionService) getBean("definitionService");
	/**
	 * 为服务项流程列表页面提供数据
	 * @Methods Name listSciProcess
	 * @Create In Jun 19, 2009 By lee
	 * @return String
	 */
	public String listSciProcess(){
		HttpServletRequest request = super.getRequest();
		HttpServletResponse response = super.getResponse();
		String serviceItemId = request.getParameter("serviceItemId");
		ServiceItem serviceItem = sis.findServiceItemById(serviceItemId);
		List<ServiceItemProcess> processes = sips.findProcessesByServiceItem(serviceItem);
		String json = "";
		for(ServiceItemProcess process : processes){
			String tempStr = "";
			tempStr += "id:" + process.getId();
//			tempStr += ",processInfo:'" + process.getDefinitionName();
			String processName = "";
			if(process.getProcessInfo()!=null){
				processName = process.getProcessInfo().getVirtualDefinitionDesc();
			}
			tempStr += ",processInfo:\"" + processName;
			String reqTable = "";
			if(process.getReqTable()!=null){
				reqTable = process.getReqTable().getTableCnName();
			}
			tempStr += "\",reqTable:\"" + reqTable;
			String startPage = "";
			if(process.getPageModel()!=null){
				startPage = process.getPageModel().getName();
			}
			tempStr += "\",pageModel:\"" + startPage;
			String endPage = "";
			if(process.getEndPageModel()!=null){
				endPage = process.getEndPageModel().getName();
			}
			tempStr += "\",endPageModel:\"" + endPage;
			tempStr += "\",buttonName:\"" + process.getButtonName();
			tempStr += "\",agreement:\"" + (process.getAgreement()==null?"":process.getAgreement()) + "\"";
			json += "{" + tempStr + "},";
		}
		if (json.endsWith(",")) {
			json = json.substring(0, json.length() - 1);
		}
		
		json = "{success: true,data:[" + json + "]}";
		response.setContentType("text/plain");
		response.setCharacterEncoding("UTF-8");
		try {
			PrintWriter out = response.getWriter();
			out.println(json);
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 获取服务项流程信息面板数据
	 * @Methods Name getSciProcessInfo
	 * @Create In Jun 19, 2009 By lee
	 * @return String
	 */
	public String getSciProcessInfo(){
		HttpServletRequest request = super.getRequest();
		HttpServletResponse response = super.getResponse();
		String sciProcessId = request.getParameter("sciProcessId");
		ServiceItemProcess process = null;
		if (StringUtils.isNotBlank(sciProcessId)) {
			process = sips.findServiceItemProcessById(sciProcessId);
		}
		String temp = "";
		if(process.getPageListPanel()!=null){
			temp +="\"ServiceItemProcess$pageListPanel\":"+process.getPageListPanel().getId();
		}else{
			temp +="\"ServiceItemProcess$pageListPanel\":null";
		}
		temp += ",\"ServiceItemProcess$sidProcessType\":"+process.getSidProcessType();
		temp += ",\"ServiceItemProcess$serviceItem\":"+process.getServiceItem().getId();
		temp += ",\"ServiceItemProcess$id\":"+process.getId();
		if(process.getPagePanel()!=null){
			temp += ",\"ServiceItemProcess$pagePanel\":"+process.getPagePanel().getId();
		}else{
			temp += ",\"ServiceItemProcess$pagePanel\":null";
		}
		String agreement = process.getAgreement();
		String temp111 = StringUtils.replace(agreement, "\"", "\\\"");
		temp += ",\"ServiceItemProcess$agreement\":\""+temp111+"\"";
		if(process.getPageModel()!=null){
			temp += ",\"ServiceItemProcess$pageModel\":"+process.getPageModel().getId();
		}else{
			temp += ",\"ServiceItemProcess$pageModel\":null";
		}
		if(process.getEndPageModel()!=null){
			temp += ",\"ServiceItemProcess$endPageModel\":"+process.getEndPageModel().getId();
		}else{
			temp += ",\"ServiceItemProcess$endPageModel\":null";
		}
		if(process.getReqTable()!=null){
			temp += ",\"ServiceItemProcess$reqTable\":"+process.getReqTable().getId();
		}else{
			temp += ",\"ServiceItemProcess$reqTable\":null";
		}
		temp += ",\"ServiceItemProcess$definitionName\":\""+process.getDefinitionName()+"\"";
		if(process.getProcessInfo()!=null){
			temp += ",\"ServiceItemProcess$processInfo\":"+process.getProcessInfo().getId();
		}else{
			temp += ",\"ServiceItemProcess$processInfo\":null";
		}
		temp += ",\"ServiceItemProcess$buttonName\":\""+process.getButtonName()+"\"";
		temp += ",\"ServiceItemProcess$status\":\""+process.getStatus()+"\"";
//		Map<String, Object> dataMap = metaDataManager.getFormDataForEdit(process,"ServiceItemProcess");
//		JSONArray jsonObject = JSONArray.fromObject(dataMap);
//		String json = "{success:" + true + ",form:"+ jsonObject.toString() + "}";
		String json = "{success:" + true + ",form:[{"+ temp + "}]}";
		response.setContentType("text/plain");
		response.setCharacterEncoding("UTF-8");
		try {
			PrintWriter out = response.getWriter();
			out.println(json);
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 保存服务项关联流程实体
	 * @Methods Name saveProcess
	 * @Create In Jun 19, 2009 By lee
	 * @return String
	 */
	public String saveProcess(){
		HttpServletRequest request = super.getRequest();
		HttpServletResponse response = super.getResponse();
		String servcieItemId = request.getParameter("serviceItemId");
		String info = request.getParameter("info");
//		ServiceItem serviceItem = sis.findServiceItemById(servcieItemId);
		JSONObject panelJO = JSONObject.fromObject(info);
		Map<String,Object> dataMap = new HashMap<String,Object>();
		Iterator columnIter = panelJO.keys();
		while(columnIter.hasNext()){
			String columnName = (String) columnIter.next();
			String columnValue = panelJO.getString(columnName);
			columnName = StringUtils.substringAfter(columnName, "$");
			dataMap.put(columnName, columnValue);
		}
		dataMap.put("serviceItem", servcieItemId);
		String processInfoId = (String) dataMap.get("processInfo");
		VirtualDefinitionInfo vd = (VirtualDefinitionInfo) getService().find(VirtualDefinitionInfo.class, processInfoId);
		String processInfoDescn = vd.getVirtualDefinitionDesc();
		dataMap.put("definitionName", processInfoDescn);
		ServiceItemProcess process = (ServiceItemProcess) metaDataManager.saveEntityData(ServiceItemProcess.class,dataMap);
		String json= "{success:true,id:"+process.getId()+"}";
		response.setContentType("text/plain");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out;
		try {
			out = response.getWriter();
			out.println(json);
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 删除服务项关联流程
	 * @Methods Name removeProcess
	 * @Create In Jun 22, 2009 By lee
	 * @return String
	 */
	public String removeProcess(){
		HttpServletRequest request = super.getRequest();
		HttpServletResponse response = super.getResponse();
		String processId = request.getParameter("processId");
		if(StringUtils.isNotBlank(processId)){
			this.getService().remove(ServiceItemProcess.class, processId);
		}
		String json= "{success:true}";
		response.setContentType("text/plain");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out;
		try {
			out = response.getWriter();
			out.println(json);
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 获取面板模型下拉列表数据并提供名称模糊查询(为ExtJS后台提供)
	 * @Methods Name getPanelComboData
	 * @Create In Jun 19, 2009 By lee
	 * @return String
	 */
	public String getPanelComboData(){
		HttpServletRequest request = super.getRequest();
		HttpServletResponse response = super.getResponse();
		String id = request.getParameter("id");
		String pageName = request.getParameter("pageName");
		int start = HttpUtil.getInt(request, "start", 0);
		int pageSize = HttpUtil.getInt(request, "pageSize", 10);
		int pageNo = start / pageSize + 1;
		Map<String,Object> searchMap = new HashMap();
		searchMap.put("pageName", pageName);
		Page page = pps.findPagePanel(searchMap, pageNo, pageSize);
		String json = "";
		List<PagePanel> panels = page.list();
		for(PagePanel panel : panels){
			json += "{id:" + panel.getId() + ",pageName:\"" 
				+ panel.getName() + "/" + panel.getTitle() +"\"},";
		}
		if (json.endsWith(",")) {
			json = json.substring(0, json.length() - 1);
		}
		if(StringUtils.isNotBlank(id)){
			PagePanel pp = pps.findPagePanelById(id);
			json = "{id:"+id+",pageName:\""+pp.getName()+"/"+pp.getTitle()+"\"}";
		}
		json = "{success: true, rowCount:"+page.getTotalCount()+",data:[" + json + "]}";
		response.setContentType("text/plain");
		response.setCharacterEncoding("UTF-8");
		try {
			PrintWriter out = response.getWriter();
			out.println(json);
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 获取页面模型下拉列表数据并提供名称模糊查询
	 * @Methods Name getModelComboData
	 * @Create In Jun 19, 2009 By lee
	 * @return String
	 */
	public String getModelComboData(){
		HttpServletRequest request = super.getRequest();
		HttpServletResponse response = super.getResponse();
		String id = request.getParameter("id");
		String pageName = request.getParameter("pageName");
		int start = HttpUtil.getInt(request, "start", 0);
		int pageSize = HttpUtil.getInt(request, "pageSize", 10);
		int pageNo = start / pageSize + 1;
		Map<String,Object> searchMap = new HashMap();
		searchMap.put("pageName", pageName);
		Page page = pms.findPageModel(searchMap, pageNo, pageSize);
		String json = "";
		List<PageModel> models = page.list();
		json += "{id:\"\",pageName:\"无\"},";
		for(PageModel model : models){
			json += "{id:" + model.getId() + ",pageName:\"" 
				+ model.getName() + "/" + model.getTitle() +"\"},";
		}
		if (json.endsWith(",")) {
			json = json.substring(0, json.length() - 1);
		}
		if(StringUtils.isNotBlank(id)){
			PageModel pm = pms.findPageModelById(id);
			json = "{id:"+id+",pageName:\""+pm.getName()+"/"+pm.getTitle()+"\"}";
		}
		json = "{success: true, rowCount:"+page.getTotalCount()+",data:[" + json + "]}";
		response.setContentType("text/plain");
		response.setCharacterEncoding("UTF-8");
		try {
			PrintWriter out = response.getWriter();
			out.println(json);
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 获取系统主表下拉列表数据并提供名称模糊查询(为ExtJS后台提供)
	 * @Methods Name getSmtComboData
	 * @Create In Jun 19, 2009 By lee
	 * @return String
	 */
	public String getSmtComboData(){
		HttpServletRequest request = super.getRequest();
		HttpServletResponse response = super.getResponse();
		String tableName = request.getParameter("tableName");
		int start = HttpUtil.getInt(request, "start", 0);
		int pageSize = HttpUtil.getInt(request, "pageSize", 10);
		int pageNo = start / pageSize + 1;
		Page page = smts.findSystemMainTableByModule(null, tableName, pageNo, pageSize);
		String json = "";
		List<SystemMainTable> smts = page.list();
		for(SystemMainTable smt : smts){
			json += "{id:" + smt.getId() +",tableName:\"" 
				+ smt.getTableName() + "/" +smt.getTableCnName() +"\"},"; 
		}
		if (json.endsWith(",")) {
			json = json.substring(0, json.length() - 1);
		}
		
		json = "{success: true, rowCount:"+page.getTotalCount()+",data:[" + json + "]}";
		response.setContentType("text/plain");
		response.setCharacterEncoding("UTF-8");
		try {
			PrintWriter out = response.getWriter();
			out.println(json);
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 获取系统主表下拉列表数据并提供名称模糊查询(为ExtJS后台提供)
	 * @Methods Name getProcessComboData
	 * @Create In Jun 19, 2009 By lee
	 * @return String
	 */
	public String getProcessComboData(){
		HttpServletRequest request = super.getRequest();
		HttpServletResponse response = super.getResponse();
		String id = request.getParameter("id");
		String processName = request.getParameter("processName");
		int start = HttpUtil.getInt(request, "start", 0);
		int pageSize = HttpUtil.getInt(request, "pageSize", 10);
		int pageNo = start / pageSize + 1;
		Page page = ds.getProcessDefinition(processName, pageNo, pageSize);
		String json = "";
		List<VirtualDefinitionInfo> processes = page.list();
		for(VirtualDefinitionInfo process : processes){
			json += "{id:" + process.getId() +",processName:\"" 
				+ process.getVirtualDefinitionDesc() + "/" +process.getVirtualDefinitionName() +"\"},"; 
		}
		if (json.endsWith(",")) {
			json = json.substring(0, json.length() - 1);
		}
		if(StringUtils.isNotBlank(id)){
			VirtualDefinitionInfo vd = (VirtualDefinitionInfo) getService().find(VirtualDefinitionInfo.class, id);
			json = "{id:"+id+",processName:\""+vd.getVirtualDefinitionDesc()+"/"+vd.getVirtualDefinitionName()+"\"}";
		}
		json = "{success: true, rowCount:"+page.getTotalCount()+",data:[" + json + "]}";
		response.setContentType("text/plain");
		response.setCharacterEncoding("UTF-8");
		try {
			PrintWriter out = response.getWriter();
			out.println(json);
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 获取流程状态页面流程启动按钮
	 * @Methods Name getSCIProcessButton
	 * @Create In Jun 22, 2009 By lee
	 * @return String
	 */
	public String getSCIProcessButton(){
		HttpServletRequest request = super.getRequest();
		HttpServletResponse response = super.getResponse();
		String serviceItemId = request.getParameter("serviceItemId");
		ServiceItem serviceItem = null;
		if(StringUtils.isNotBlank(serviceItemId)){
			serviceItem = sis.findServiceItemById(serviceItemId);
		}
		String json="[";
		List<ServiceItemProcess> processes = sips.findProcessesByServiceItem (serviceItem);
		if(processes.isEmpty()){
			json+="";
		}else{
			for(ServiceItemProcess process : processes){
				json+="{";
				json += "\"btnName\":\""+process.getButtonName()+"\",";
				json += "\"link\":\""+process.getPageModel().getPagePath()+"\"";
				json+="},";
			}
			if(json.length()>1)
				json = json.substring(0, json.length()-1);
		}
		json += "]";
		response.setContentType("text/plain");
		response.setCharacterEncoding("UTF-8");
		try {
			PrintWriter out = response.getWriter();
			out.println(json);
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
