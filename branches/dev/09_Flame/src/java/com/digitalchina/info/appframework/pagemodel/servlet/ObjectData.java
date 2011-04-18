package com.digitalchina.info.appframework.pagemodel.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;

import com.digitalchina.info.appframework.metadata.MetaDataManager;
import com.digitalchina.info.appframework.metadata.entity.SystemMainTable;
import com.digitalchina.info.appframework.metadata.entity.UserTableSetting;
import com.digitalchina.info.appframework.pagemodel.PageManager;
import com.digitalchina.info.appframework.pagemodel.entity.PageModel;
import com.digitalchina.info.appframework.pagemodel.entity.PagePanel;
import com.digitalchina.info.appframework.pagemodel.entity.PagePanelColumn;
import com.digitalchina.info.appframework.pagemodel.service.PageModelPanelService;
import com.digitalchina.info.appframework.pagemodel.service.PagePanelService;
import com.digitalchina.info.framework.context.ContextHolder;
import com.digitalchina.info.framework.dao.support.Page;
import com.digitalchina.info.framework.service.Service;
import com.digitalchina.info.framework.util.BeanUtil;
import com.digitalchina.info.framework.util.HttpUtil;
import com.digitalchina.info.framework.util.PropertiesUtil;
import com.digitalchina.info.framework.web.adapter.servlet.BaseServlet;
/*import com.digitalchina.itil.config.service.ConfigItemService;
import com.digitalchina.itil.project.service.RequirementService;*/

public class ObjectData extends BaseServlet {
	private PageManager pageManager =  (PageManager) ContextHolder.getBean("pageManager");
	private MetaDataManager metaDataManager = (MetaDataManager) ContextHolder.getBean("metaDataManager");
	private PagePanelService pagePanelService=(PagePanelService) ContextHolder.getBean("pagePanelService");
	private PageModelPanelService pageModelPanelService=(PageModelPanelService) ContextHolder.getBean("pageModelPanelService");
	private Service service = (Service) ContextHolder.getBean("baseService");
	//private ConfigItemService configItemService = (ConfigItemService) ContextHolder.getBean("configItemService");
	
	public ObjectData() {
		super();
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");  
		String msg = "";
		String method = request.getParameter("method");
		String model = request.getParameter("model");
		
		if (StringUtils.isBlank(method)) {
			msg = "Error: no method parameter name specified.";
		} 
		else {
			if (method.trim().equalsIgnoreCase("save")) {
				msg = save(request);
			} /*else if (method.trim().equalsIgnoreCase("saveConfigItem")) {
				msg = saveConfigItem(request);
			}*/ else if (method.trim().equalsIgnoreCase("remove")) {
				msg = remove(request);
			} else if (method.trim().equalsIgnoreCase("removeGridColumn")) {
				msg = removeGridColumn(request);
			} else if (method.trim().equalsIgnoreCase("query")) {
				msg = query(request);
			} else if (method.trim().equalsIgnoreCase("export")) {
				msg = export(request);
			} else if (method.trim().equalsIgnoreCase("queryCobom")){
				msg = queryCobom(request);
			} else if (method.trim().equalsIgnoreCase("pageQuery")) {
				msg = query(request);
			} else if (method.trim().equalsIgnoreCase("savePanel")) {
				msg = savePanel(request,response);
			} else if (method.trim().equalsIgnoreCase("treequery")) {
				msg = treeQuery(request);
			} else if (method.trim().equalsIgnoreCase("queryForUser")) {
				msg = queryForUser(request);
			}
		}
		response.setContentType("text/plain");
		PrintWriter out = response.getWriter();
		out.println(msg);
		out.flush();
		out.close();
	}
	@SuppressWarnings("unchecked")
	private String queryForUser(HttpServletRequest request){
		String json = "";
		int pageSize = HttpUtil.getInt(request, "pageSize", 10);
		int start = HttpUtil.getInt(request, "start", 0);
		int pageNo=start/pageSize+1;
		String orderBy = HttpUtil.getString(request, "orderBy", "id");
		boolean isAsc = HttpUtil.getBoolean(request, "isAsc", false);
		String panelName = request.getParameter("panelname");
		String propertyName = request.getParameter("propertyName");//过滤字段
		
		PagePanel pp=pagePanelService.findPagePanel(panelName);
		SystemMainTable smt=pp.getSystemMainTable();
		String className=smt.getClassName();
		Class clazz=null;
		try {
			clazz=Class.forName(className);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		Map requestParams = HttpUtil.requestParam2Map(request);
		Page page = null;
		page = metaDataManager.queryForUser(clazz, requestParams, pageNo, pageSize, orderBy, isAsc,propertyName);
		
		Long total = page.getTotalCount();
		List queryList = page.list();
		//***********************2期*************************
		List<Map<String, Object>> listData = pageManager.getEntityMapDataForList(panelName, queryList);
		List<PagePanelColumn> pagePanelColumns=pageManager.getUserPagePanelColumn(panelName);
		json = CoderForList.encode(pagePanelColumns, listData, total); 
//		json = json.replaceAll("[\\n|\\r]","");//删除字符串中的回行字符
//		json = json.replaceAll("\\\\", "\\\\\\\\");
		return json;
	}
		
	private String treeQuery(HttpServletRequest request) {
		String json = "";
		int pageSize = HttpUtil.getInt(request, "pageSize", 10);
		int start = HttpUtil.getInt(request, "start", 0);
		int pageNo=start/pageSize+1;
		String orderBy = HttpUtil.getString(request, "orderBy", "id");
		boolean isAsc = HttpUtil.getBoolean(request, "isAsc", false);
		String panelName = request.getParameter("panelname");
		
		PagePanel pp=pagePanelService.findPagePanel(panelName);
		SystemMainTable smt=pp.getSystemMainTable();
		String className=smt.getClassName();
		Class clazz=null;
		try {
			clazz=Class.forName(className);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		Map requestParams = HttpUtil.requestParam2Map(request);
		Page page =  metaDataManager.query(clazz, requestParams, 1, pageSize, orderBy, isAsc);		
		Long total = page.getTotalCount();
		List queryList = page.list();
		//***********************2期*************************
		List<Map<String, Object>> listData = pageManager.getEntityMapDataForList(panelName, queryList);
		List<PagePanelColumn> pagePanelColumns=pageManager.getUserPagePanelColumn(panelName);
		json = TreeDateCode.encode(pagePanelColumns, listData, total); 
		json = json.replaceAll("[\\n|\\r]","");//删除字符串中的回行字符
		String temp="[";
		//temp+="{success: true, rowCount:10,data:[";
//		temp+="{'ConfigItem$id':'126','ConfigItem$configItemType':'外部客户','ConfigItem$name':'','ConfigItem$configItemStatus':'','ConfigItem$customer':'','ConfigItem$useDate':'','ConfigItem$financeInfo':'','ConfigItem$status':'','_id':1,'_parent':null,'_level':1,'_lft':1,'_rgt':6,'_is_leaf':false},";
//		temp+="{'ConfigItem$id':'125','ConfigItem$configItemType':'服务','ConfigItem$name':'dddd333','ConfigItem$configItemStatus':'','ConfigItem$customer':'','ConfigItem$useDate':'','ConfigItem$financeInfo':'','ConfigItem$status':'','_id':2,'_parent':1,'_level':2,'_lft':2,'_rgt':5,'_is_leaf':false},";
//		temp+="{'ConfigItem$id':'124','ConfigItem$configItemType':'服务','ConfigItem$name':'eee','ConfigItem$configItemStatus':'','ConfigItem$customer':'神州数码政务公司','ConfigItem$useDate':'','onfigItem$financeInfo':'','ConfigItem$status':'','_id':3,'_parent':2,'_level':3,'_lft':3,'_rgt':4,'_is_leaf':true}";
		//List depts = this.configItemService.findAllDeptByParentDepartcode("50008953");
		temp+="{'company':'0. Johnson & Johnson','_id':1,'_parent':null,'_level':1,'_lft':1,'_rgt':8,'_is_leaf':false},";
		temp+="{'company':'1. American International Group, Inc.','_id':2,'_parent':1,'_level':2,'_lft':2,'_rgt':3,'_is_leaf':true},";
		temp+="{'company':'2. Alcoa Inc','_id':3,'_parent':1,'_level':2,'_lft':4,'_rgt':5,'_is_leaf':true}";
       temp+="]";
		return temp;

	}

	
	/**
	 * 保存表单面板数据
	 * @Methods Name saveFormData
	 * @Create In 2008-12-31 By lee
	 * @param request
	 * @return String
	 * @throws IOException 
	 */
	private String savePanel(HttpServletRequest request,HttpServletResponse response) throws IOException {
		//String json = "{success:true}";
		String info=request.getParameter("info");
		String modelId=request.getParameter("dataId");
		String panel=request.getParameter("panel");
		String model=request.getParameter("model");
		Map<String,List<Map<String,Object>>> panelDataMap = new HashMap<String,List<Map<String,Object>>>();
		JSONObject panelJO = JSONObject.fromObject(info);
		Iterator panelIter = panelJO.keys();
		while(panelIter.hasNext()) {
			List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
			String panelName = (String) panelIter.next();
			String panelData = panelJO.getString(panelName); //一个panel的Json字符串
			
			JSONArray jsonArray = JSONArray.fromObject(panelData);   
			Object[] panelDataArrays = jsonArray.toArray();
			for(int i=0; i<panelDataArrays.length; i++){
				JSONObject record = (JSONObject) panelDataArrays[i];
				Map<String,Object> recordMap = new HashMap<String,Object>();
				Iterator columnIter = record.keys();
				while(columnIter.hasNext()){
					String columnName = (String) columnIter.next();
					String columnValue = record.getString(columnName);
					recordMap.put(columnName, columnValue); //此时的字段名称是带了表名前缀的
				}
				list.add(recordMap);
			}
			panelDataMap.put(panelName, list);
						
		}
		String dataId=pageManager.saveSinglePanelData(model,panel, panelDataMap);
		String json = "{\'"+"id"+"\'"+":'"+dataId+"'}";
		//System.out.println("发送前台数据："+json);
		return json;
	}
	private static Object[] getObjectArray4Json(String jsonString) {   
        JSONArray jsonArray = JSONArray.fromObject(jsonString);   
        return jsonArray.toArray();   
  
    }   

	public String save(HttpServletRequest request){
		String json = "{success:true}";
		//将表单的所有数据转成Java对象格式直接传递给后端，而不是每个panel单独保存，这样无法处理第一次保存时数据的关联
		Map<String,List<Map<String,Object>>> panelDataMap = new HashMap<String,List<Map<String,Object>>>();
		
		//{panel1:{[columnName:columnValue,...]}, panel2:{[columnName:columnValue,...]}}
		String model = request.getParameter("model");
		String info = request.getParameter("info");
		//info = HttpUtil.ConverUnicode(info);
		JSONObject panelJO = JSONObject.fromObject(info); //Json格式的panel字符串转化成JAVA Json对象
		Iterator panelIter = panelJO.keys();
		while(panelIter.hasNext()) {
			List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
			String panelName = (String) panelIter.next();
			String panelData = panelJO.getString(panelName); //一个panel的Json字符串
			
			JSONArray jsonArray = JSONArray.fromObject(panelData);   
			Object[] panelDataArrays = jsonArray.toArray();
			for(int i=0; i<panelDataArrays.length; i++){
				JSONObject record = (JSONObject) panelDataArrays[i];
				Map<String,Object> recordMap = new HashMap<String,Object>();
				Iterator columnIter = record.keys();
				while(columnIter.hasNext()){
					String columnName = (String) columnIter.next();
					String columnValue = record.getString(columnName);
					recordMap.put(columnName, columnValue); //此时的字段名称是带了表名前缀的
				}
				list.add(recordMap);
			}
			panelDataMap.put(panelName, list);
						
		}
		pageManager.savePageModelData(model, panelDataMap);
		return json;

	}

	
//	public String saveConfigItem(HttpServletRequest request){
//		String json = "{success:true}";
//		//将表单的所有数据转成Java对象格式直接传递给后端，而不是每个panel单独保存，这样无法处理第一次保存时数据的关联
//		Map<String,List<Map<String,Object>>> panelDataMap = new HashMap<String,List<Map<String,Object>>>();
//		
//		//{panel1:{[columnName:columnValue,...]}, panel2:{[columnName:columnValue,...]}}
//		String model = request.getParameter("model");
//		String info = request.getParameter("info");
//		//info = HttpUtil.ConverUnicode(info);
//		JSONObject panelJO = JSONObject.fromObject(info); //Json格式的panel字符串转化成JAVA Json对象
//		Iterator panelIter = panelJO.keys();
//		while(panelIter.hasNext()) {
//			List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
//			String panelName = (String) panelIter.next();
//			String panelData = panelJO.getString(panelName); //一个panel的Json字符串
//			
//			JSONArray jsonArray = JSONArray.fromObject(panelData);   
//			Object[] panelDataArrays = jsonArray.toArray();
//			for(int i=0; i<panelDataArrays.length; i++){
//				JSONObject record = (JSONObject) panelDataArrays[i];
//				Map<String,Object> recordMap = new HashMap<String,Object>();
//				Iterator columnIter = record.keys();
//				while(columnIter.hasNext()){
//					String columnName = (String) columnIter.next();
//					String columnValue = record.getString(columnName);
//					recordMap.put(columnName, columnValue); //此时的字段名称是带了表名前缀的
//				}
//				list.add(recordMap);
//			}
//			panelDataMap.put(panelName, list);
//						
//		}
//		configItemService.saveModelConfigItem(model, panelDataMap);
//		return json;
//	}

	private String remove(HttpServletRequest request) {
		String json = "{success:true}";
		//String pClazz = request.getParameter("clazz");
		String modelName = request.getParameter("model");
		String dataId = request.getParameter("dataId");
		
		PageModel model = this.pageManager.findPageModel(modelName);
		SystemMainTable smt = model.getSystemMainTable();
		String className = smt.getClassName();
		Class clazz = this.getClass(className);
		Object bean = (Object) BeanUtil.getObject(request, clazz);
		service.remove(bean);
		// service.remove(clazz,id);
		return json;
	}
	/**
	 * 删除可编辑列表面板中行操作
	 * @Methods Name removeGridColumn
	 * @Create In Mar 13, 2009 By lee
	 * @param request
	 * @return String
	 */
	private String removeGridColumn(HttpServletRequest request){
		String json = "{success:true}";
		String panelName = request.getParameter("panel");
		String dataId = request.getParameter("id");
		if(dataId==null){
			return json;
		}
		PagePanel pagePanel = pagePanelService.findPagePanel(panelName);
		SystemMainTable smt = pagePanel.getSystemMainTable();
		String className = smt.getClassName();
		Class clazz = this.getClass(className);
		service.remove(clazz, dataId);
		return json;
	}
	@SuppressWarnings("unchecked")
	private String queryOld(HttpServletRequest request) {
		String json = "";
		int pageSize = HttpUtil.getInt(request, "pageSize", 10);
		int start = HttpUtil.getInt(request, "start", 0);
		int pageNo=start/pageSize+1;
		String orderBy = HttpUtil.getString(request, "orderBy", "id");
		boolean isAsc = HttpUtil.getBoolean(request, "isAsc", false);
//		String pClazz = request.getParameter("clazz");
//		Class clazz = getClass(pClazz);
		String panelName = request.getParameter("panelname");
		PagePanel pp=pagePanelService.findPagePanel(panelName);
		SystemMainTable smt=pp.getSystemMainTable();
		String className=smt.getClassName();
		Class clazz=null;
		try {
			clazz=Class.forName(className);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		Map requestParams = HttpUtil.requestParam2Map(request);
//		Map<Object, Object> queryParamValues = metaDataManager.genQueryParams(
//				clazz, requestParams);
	
		Page page = metaDataManager.query(clazz, requestParams, 
				pageNo, pageSize, orderBy, isAsc);
		Long total = page.getTotalCount();
		List queryList = page.list();
		//***********************2期*************************
		List<Map<String, Object>> listData = pageManager.getEntityMapDataForList(panelName, queryList);
		List<PagePanelColumn> pagePanelColumns=pageManager.getUserPagePanelColumn(panelName);
		json = CoderForList.encode(pagePanelColumns, listData, total); 
		return json;
	}
	
	
	@SuppressWarnings("unchecked")
	private String query(HttpServletRequest request) {
		String json = "";
		int start = HttpUtil.getInt(request, "start", 0);
		int pageSize = HttpUtil.getInt(request, "pageSize",10);
		int pageNo=start/pageSize+1;
		String orderBy = HttpUtil.getString(request, "orderBy", "id");
		boolean isAsc = HttpUtil.getBoolean(request, "isAsc", false);
		String panelName = request.getParameter("panelname");
		PagePanel pp=pagePanelService.findPagePanel(panelName);
		SystemMainTable smt=pp.getSystemMainTable();
		String className=smt.getClassName();
		Class clazz=null;
		try {
			clazz=Class.forName(className);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		Map requestParams = HttpUtil.requestParam2Map(request);
		Page page = null;
		
//		String configItemRequireId = request.getParameter("configItemRequireId");
//		if(StringUtils.isNotBlank(configItemRequireId)){
//			RequirementService rs = (RequirementService) ContextHolder.getBean("requirementService");
//			page = rs.findConfigItemByRequire(configItemRequireId, pageNo, pageSize);
//		}else{
//			page = metaDataManager.query(clazz, requestParams, pageNo, pageSize, orderBy, isAsc);
//		}
		page = metaDataManager.query(clazz, requestParams, pageNo, pageSize, orderBy, isAsc);
		
		Long total = page.getTotalCount();
		List queryList = page.list();
		//***********************2期*************************
		List<Map<String, Object>> listData = pageManager.getEntityMapDataForList(panelName, queryList);
		List<PagePanelColumn> pagePanelColumns=pageManager.getUserPagePanelColumn(panelName);
		json = CoderForList.encode(pagePanelColumns, listData, total); 
//		json = json.replaceAll("[\\n|\\r]","");//删除字符串中的回行字符
//		json = json.replaceAll("\\\\", "\\\\\\\\");
		return json;
	}
	
	
	@SuppressWarnings("unchecked")
	private String queryCobom(HttpServletRequest request) {
		String json = "";
		int pageSize = HttpUtil.getInt(request, "pageSize", 10);
		int start = HttpUtil.getInt(request, "start", 0);
		int pageNo=start/pageSize+1;
		String orderBy = HttpUtil.getString(request, "orderBy", "id");
		boolean isAsc = HttpUtil.getBoolean(request, "isAsc", false);
		String pClazz = request.getParameter("clazz");
		Class clazz = getClass(pClazz);
		Map<String, String> requestParams = HttpUtil.requestParam2Map(request);
		Map<Object, Object> queryParamValues = metaDataManager.genQueryParams(
				clazz, requestParams);
		Page page = metaDataManager.query(clazz, queryParamValues, null,
				pageNo, pageSize, orderBy, isAsc);
		Long total = page.getTotalCount();
		List queryList = page.list();
		List<Map<String, Object>> listData = metaDataManager
				.getEntityMapDataForList(clazz, queryList);
		List<UserTableSetting> userVisibleColumns = metaDataManager
				.getUserColumnForList(clazz);
		//json = CoderForList.encode(userVisibleColumns, listData,total);
		return json;
	}
	
	private int getInt(HttpServletRequest request,String param,int defaultValue){
		String strValue = request.getParameter(param);
		if(strValue==null){
			return defaultValue;
		}else{
			return Integer.parseInt(strValue);
		}
	}

	private String export(HttpServletRequest request) {
		String json = "";
		return json;
	}

	@SuppressWarnings("unchecked")
	private Class getClass(String className) {
		Class clazz = null;
		try {
			clazz = Class.forName(className);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return clazz;
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	public void init() throws ServletException {
	}
}
