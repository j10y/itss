package com.digitalchina.info.appframework.extjs.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;

import java.util.List;
import java.util.Map;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

import com.digitalchina.info.appframework.metadata.MetaDataManager;
import com.digitalchina.info.appframework.metadata.entity.UserTableSetting;
import com.digitalchina.info.framework.context.ContextHolder;
import com.digitalchina.info.framework.dao.support.Page;
import com.digitalchina.info.framework.service.Service;
import com.digitalchina.info.framework.util.BeanUtil;
import com.digitalchina.info.framework.util.DateUtil;
import com.digitalchina.info.framework.util.HttpUtil;

public class DataAction extends HttpServlet {
	private MetaDataManager metaDataManager = (MetaDataManager) ContextHolder.getBean("metaDataManager");
	private Service service = (Service) ContextHolder.getBean("baseService");

	public DataAction() {
		super();
	}

	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");  
		String msg = "";
		String method = request.getParameter("method");
		String clazz = request.getParameter("clazz");
		if (StringUtils.isBlank(method)) {
			msg = "Error: no method specified.";
		} else if (StringUtils.isBlank(clazz)) {
			msg = "Error: no class specified.";
		} else {
			if (method.trim().equalsIgnoreCase("save")) {
				msg = save(request);
			} else if (method.trim().equalsIgnoreCase("remove")) {
				msg = remove(request);
			} else if (method.trim().equalsIgnoreCase("query")) {
				msg = query(request);
			} else if (method.trim().equalsIgnoreCase("export")) {
				msg = export(request);
			} else if (method.trim().equalsIgnoreCase("queryCobom")){
				msg = queryCobom(request);
			//add by lee for 提供根据用户角色权限过滤数据 in 20090821 begin
			} else if (method.trim().equalsIgnoreCase("queryForUser")){
				msg = queryForUser(request);
			} 
			//add by lee for 提供根据用户角色权限过滤数据 in 20090821 end
		}
		response.setContentType("text/plain");
		PrintWriter out = response.getWriter();
		out.println(msg);
		out.flush();
		out.close();
	}


	private String save(HttpServletRequest request) {
		String json = "{success:true}";
		String pClazz = request.getParameter("clazz");
		Class clazz = getClass(pClazz);

		Date currentDate = DateUtil.getCurrentDate();
		//for(int i=0;i<request.getParameterMap())
		Map map = new HashMap();
		Enumeration en = request.getParameterNames();//HttpUtil.requestParam2Map(request);
		while(en.hasMoreElements()) {
			String key = (String)en.nextElement();			
			if (key.equalsIgnoreCase("clazz") || key.equalsIgnoreCase("method")) {
				continue;
			}
			String value = request.getParameter(key);
			value = HttpUtil.ConverUnicode(value).trim();
			map.put(key, value);
		}
		map.put("applyDate", currentDate);
		metaDataManager.saveEntityData(clazz, map);
		return json;
	}

	private String remove(HttpServletRequest request) {
		String json = "{success:true}";
		String pClazz = request.getParameter("clazz");
		// String id = request.getParameter("id");
		Class clazz = getClass(pClazz);
		Object bean = (Object) BeanUtil.getObject(request, clazz);
		service.remove(bean);
		// service.remove(clazz,id);
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
		String pClazz = request.getParameter("clazz");
		Class clazz = getClass(pClazz);
		//Map requestParams = HttpUtil.requestParam2Map(request);

		//Page page = metaDataManager.query(clazz, requestParams, 
		Map requestParams = HttpUtil.requestParam2Map(request);
		Map<Object, Object> queryParamValues = metaDataManager.genQueryParams(
				clazz, requestParams);
	
		Page page = metaDataManager.query(clazz, requestParams,
				pageNo, pageSize, orderBy, isAsc);
		Long total = page.getTotalCount();
		List queryList = page.list();
		List<Map<String, Object>> listData = metaDataManager
				.getEntityMapDataForList(clazz, queryList);
		List<UserTableSetting> userVisibleColumns = metaDataManager
				.getUserColumnForList(clazz);
		json = CoderForList.encode(userVisibleColumns, listData,total);
//		json = json.replaceAll("\\\\", "\\\\\\\\");
		return json;
	}
	/**
	 * 提供根据用户角色查看权限过滤用户可查看数据
	 * @Methods Name queryForUser
	 * @Create In Aug 21, 2009 By lee
	 * @param request
	 * @return String
	 */
	@SuppressWarnings("unchecked")
	private String queryForUser(HttpServletRequest request) {
		String json = "";
		int pageSize = HttpUtil.getInt(request, "pageSize", 10);
		int start = HttpUtil.getInt(request, "start", 0);
		int pageNo=start/pageSize+1;
		String orderBy = HttpUtil.getString(request, "orderBy", "id");
		boolean isAsc = HttpUtil.getBoolean(request, "isAsc", false);
		String pClazz = request.getParameter("clazz");
		String propertyName = request.getParameter("propertyName");//过滤字段
		
		Class clazz = getClass(pClazz);
		Map requestParams = HttpUtil.requestParam2Map(request);
		
		Map<Object, Object> queryParamValues = metaDataManager.genQueryParams(
				clazz, requestParams);
		Page page = metaDataManager.queryForUser(clazz, requestParams, pageNo, pageSize, orderBy, isAsc,propertyName);
		Long total = page.getTotalCount();
		List queryList = page.list();
		List<Map<String, Object>> listData = metaDataManager.getEntityMapDataForList(clazz, queryList);
		List<UserTableSetting> userVisibleColumns = metaDataManager.getUserColumnForList(clazz);
		json = CoderForList.encode(userVisibleColumns, listData,total);
//		json = json.replaceAll("\\\\", "\\\\\\\\");
		return json;
	}
	@SuppressWarnings("unchecked")
	private String queryCobom(HttpServletRequest request) {
		String json = "";
		
		int pageSize = getInt(request, "pageSize", 10);
		int start = getInt(request, "start", 0);
		int pageNo = start/pageSize + 1;
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
		json = CoderForList.encode(userVisibleColumns, listData,total);
//		json = json.replaceAll("\\\\", "\\\\\\\\");
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
