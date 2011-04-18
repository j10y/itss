package com.digitalchina.info.appframework.pagemodel.autocreat.servlet;

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
import com.digitalchina.info.appframework.pagemodel.entity.PagePanel;
import com.digitalchina.info.appframework.pagemodel.entity.PagePanelColumn;
import com.digitalchina.info.appframework.pagemodel.entity.PagePanelType;
import com.digitalchina.info.appframework.pagemodel.service.PageModelPanelService;
import com.digitalchina.info.appframework.pagemodel.service.PagePanelService;
import com.digitalchina.info.appframework.pagemodel.servlet.CoderForList;
import com.digitalchina.info.appframework.pagemodel.servlet.CoderForSave;
import com.digitalchina.info.framework.context.ContextHolder;
import com.digitalchina.info.framework.dao.support.Page;
import com.digitalchina.info.framework.service.Service;
import com.digitalchina.info.framework.util.HttpUtil;
import com.digitalchina.info.framework.util.PropertiesUtil;
import com.digitalchina.info.framework.web.adapter.servlet.BaseServlet;

public class ObjectData extends BaseServlet {
	private PageManager pageManager =  (PageManager) ContextHolder.getBean("pageManager");
	private MetaDataManager metaDataManager = (MetaDataManager) ContextHolder.getBean("metaDataManager");
	private PagePanelService pagePanelService=(PagePanelService) ContextHolder.getBean("pagePanelService");
	private PageModelPanelService pageModelPanelService=(PageModelPanelService) ContextHolder.getBean("pageModelPanelService");
	private Service service = (Service) ContextHolder.getBean("baseService");
	
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
			if (method.trim().equalsIgnoreCase("findPanelDataById")) {
				msg = findPanelDataById(request);
			} 

		}
		response.setContentType("text/plain");
		PrintWriter out = response.getWriter();
		out.println(msg);
		out.flush();
		out.close();
	}
	
	
	private String findPanelDataById(HttpServletRequest request) throws IOException {
		String json = "";
		String model=request.getParameter("modelName");
		String panelName=request.getParameter("panelName");
		PagePanel panel = this.pagePanelService.findPagePanel(panelName);
		PagePanelType ppt = panel.getXtype();
		String xtype = ppt.getName();
		
		if(xtype.equals("form")){
			String dataId=request.getParameter("dataId");
			
			Map<String, Object> dataMap = this.pageManager.getFormPanelDataForEdit(panelName, dataId);
//			List<PagePanelColumn> pagePanelColumns=pageManager.getUserPagePanelColumn(panelName);
//			String webContext = request.getContextPath();
//			dataMap.put("webContext", webContext);
//			json = CoderForSave.encode(pagePanelColumns, dataMap, true);
			
			JSONArray jsonObject = JSONArray.fromObject(dataMap);
			//json = "["+jsonObject.toString() + "]";
			json = "{success:" + true + ",form:"+ jsonObject.toString() + "}";
			
			
		}else if(xtype.equals("editorgrid")|| xtype.equals("grid")){
			
			int pageSize = HttpUtil.getInt(request, "pageSize", 10);
			int start = HttpUtil.getInt(request, "start", 0);
			int pageNo=start/pageSize+1;
			String orderBy = HttpUtil.getString(request, "orderBy", "id");
			boolean isAsc = HttpUtil.getBoolean(request, "isAsc", false);
					
			PagePanel pp=pagePanelService.findPagePanel(panelName);
			SystemMainTable smt=pp.getSystemMainTable();
			String className=smt.getClassName();
			Class clazz=this.getClass(className);
			
			Map requestParams = HttpUtil.requestParam2Map(request);
			Page page = metaDataManager.query(clazz, requestParams, pageNo, pageSize, orderBy, isAsc);
			
			Long total = page.getTotalCount();
			List queryList = page.list();
			
			List<Map<String, Object>> listData = pageManager.getEntityMapDataForList(panelName, queryList);
			JSONArray jsonObject = JSONArray.fromObject(listData);
			json = jsonObject.toString();
			
//			List<PagePanelColumn> pagePanelColumns=pageManager.getUserPagePanelColumn(panelName);
			json = "{success: true, rowCount:"+total+",data:["+json+"]}";
			
			
			json = json.replaceAll("[\\n|\\r]","");//删除字符串中的回行字符
			json = json.replaceAll("\\\\", "\\\\\\\\");
			
		}
		return json;
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

	@SuppressWarnings("unchecked")
	private String query(HttpServletRequest request) {
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
		Page page = null;

		page = metaDataManager.query(clazz, requestParams, pageNo, pageSize, orderBy, isAsc);
		
		Long total = page.getTotalCount();
		List queryList = page.list();
		//***********************2期*************************
		List<Map<String, Object>> listData = pageManager.getEntityMapDataForList(panelName, queryList);
		List<PagePanelColumn> pagePanelColumns=pageManager.getUserPagePanelColumn(panelName);
		json = CoderForList.encode(pagePanelColumns, listData, total); 
		json = json.replaceAll("[\\n|\\r]","");//删除字符串中的回行字符
		json = json.replaceAll("\\\\", "\\\\\\\\");
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
