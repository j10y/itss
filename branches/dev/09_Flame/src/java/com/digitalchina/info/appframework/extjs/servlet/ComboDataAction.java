package com.digitalchina.info.appframework.extjs.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

import com.digitalchina.info.appframework.metadata.MetaDataManager;
import com.digitalchina.info.appframework.metadata.entity.ExtOptionData;
import com.digitalchina.info.appframework.metadata.entity.SystemMainTable;
import com.digitalchina.info.appframework.metadata.entity.SystemMainTableColumn;
import com.digitalchina.info.appframework.metadata.entity.UserTableSetting;
import com.digitalchina.info.appframework.metadata.service.SystemColumnService;
import com.digitalchina.info.appframework.metadata.service.SystemMainTableService;
import com.digitalchina.info.framework.context.ContextHolder;
import com.digitalchina.info.framework.dao.support.Page;
import com.digitalchina.info.framework.util.HttpUtil;

public class ComboDataAction extends HttpServlet {
	private MetaDataManager metaDataManager = (MetaDataManager) ContextHolder
			.getBean("metaDataManager");
	private SystemColumnService systemColumnService = (SystemColumnService) ContextHolder.getBean("systemColumnService");
	private SystemMainTableService systemMainTableService=(SystemMainTableService) ContextHolder.getBean("systemMainTableService");
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
//		request.setCharacterEncoding("gbk");
		response.setCharacterEncoding("gbk");
		String msg = "";
	    String discValue = request.getParameter("discValue");
		String isAbstract= request.getParameter("isAbstract");
		if(isAbstract!=null&& isAbstract.equals("true")){
			msg = queryAbstractCobom(request);
		}else if(discValue!=null){
			request.getSession(true).setAttribute("discValue", discValue);
		}else{ 
			msg = queryCobom(request);
		}
			
		response.setContentType("text/plain");
		PrintWriter out = response.getWriter();
		out.println(msg);
		out.flush();
		out.close();
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}
	
	/**
	 * 用于查询combo的数据
	 * Nov 4, 2008 By chuanyu ou
	 * @param request
	 * @return 
	 */
	@SuppressWarnings("unchecked")
	private String queryCobom(HttpServletRequest request) {
		String json = "";
		int pageSize = getInt(request, "pageSize", 10);
		int start = getInt(request, "start", 0);
		int pageNo = start / pageSize + 1;
		String orderBy = HttpUtil.getString(request, "orderBy", "id");
		boolean isAsc = HttpUtil.getBoolean(request, "isAsc", true);
		
		Class clazz = null;
		
		String pClazz = request.getParameter("clazz");
		if(pClazz.equals("com.digitalchina.info.appframework.metadata.entity.ExtOptionData")){
			clazz = getClass(pClazz);
			Map requestParams = HttpUtil.requestParam2Map(request);
			Page page = metaDataManager.query(clazz, requestParams, pageNo, pageSize, orderBy, isAsc);
			Long total = page.getTotalCount();
			List<ExtOptionData> queryList = page.list();
			for(ExtOptionData extOptionData:queryList){
				json+="{id:'"+extOptionData.getId()+"',extColumnId:'"+extOptionData.getExtColumnId()+"',extOptionValue:'"+extOptionData.getExtOptionValue()+"'},";
			}
			json = json.substring(0,json.length()-1);
			json = "{success: true, rowCount:"+total+",data:["+json+"]}";
		}else if(StringUtils.isNotBlank(pClazz)){
			clazz = getClass(pClazz);
			
			Map requestParams = HttpUtil.requestParam2Map(request);
			Page page = metaDataManager.query(clazz, requestParams, pageNo, pageSize, orderBy, isAsc);
					
			Long total = page.getTotalCount();
			List queryList = page.list();
			List<Map<String, Object>> listData = metaDataManager
					.getEntityMapDataForList(clazz, queryList);
			//modify by tongjp getcloumn from SystemTableColumn
			SystemMainTable smt=systemMainTableService.findSystemMainTableByClazz(clazz);
			List<SystemMainTableColumn> visibleColumns = systemColumnService.findSystemTableColumns(smt);
				//metaDataManager.getUserColumnForList(clazz);
			json = CoderForList.encodeForComboList(visibleColumns, listData, total);
		}
		
		return json;
	}
	
	/**
	 * 用于抽象查询combo的数据
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private String queryAbstractCobom(HttpServletRequest request) {
		String json = "";
		int pageSize = getInt(request, "pageSize", 10);
		int start = getInt(request, "start", 0);
		int pageNo = start / pageSize + 1;
		String orderBy = HttpUtil.getString(request, "orderBy", "id");
		boolean isAsc = HttpUtil.getBoolean(request, "isAsc", true);
		String pClazz = request.getParameter("clazz");
		
		String isAbstract= request.getParameter("isAbstract");
		if(isAbstract!=null&& isAbstract.equals("true")){
			@SuppressWarnings("unused")
			String discValue = (String) request.getSession(true).getAttribute("discValue");
			if(StringUtils.isNotBlank(discValue)){
				String fdiscTable = request.getParameter("fdiscTable");
				pClazz = systemColumnService.findClassNameByDisc(discValue, fdiscTable);
			}
		}
		
		Page page = null;
		
		Class clazz = getClass(pClazz);
		if(clazz!=null){
			Map requestParams = HttpUtil.requestParam2Map(request);
			page = metaDataManager.query(clazz, requestParams, pageNo, pageSize, orderBy, isAsc);
		
			Long total = page.getTotalCount();
			List queryList = page.list();
			List<Map<String, Object>> listData = metaDataManager
					.getEntityMapDataForList(clazz, queryList);
			List<UserTableSetting> userVisibleColumns = metaDataManager
					.getUserColumnForList(clazz);
			json = CoderForList.encodeAbs(userVisibleColumns, listData, total);
		}
		return json;
	}
	
	private int getInt(HttpServletRequest request, String param,
			int defaultValue) {
		String strValue = request.getParameter(param);
		if (strValue == null) {
			return defaultValue;
		} else {
			return Integer.parseInt(strValue);
		}
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
}
