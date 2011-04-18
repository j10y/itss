package com.digitalchina.info.appframework.metadata.web.struts;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.digitalchina.info.appframework.extjs.servlet.CoderForList;
import com.digitalchina.info.appframework.metadata.entity.SystemMainTable;
import com.digitalchina.info.appframework.metadata.entity.SystemMainTableIdBuilder;
import com.digitalchina.info.appframework.metadata.entity.UserTableSetting;
import com.digitalchina.info.appframework.metadata.service.SystemColumnService;
//import com.digitalchina.info.appframework.metadata.service.SystemExtColumnServcie;
import com.digitalchina.info.appframework.metadata.service.SystemMainColumnService;
import com.digitalchina.info.appframework.metadata.service.SystemMainTableService;
import com.digitalchina.info.appframework.metadata.service.SystemTableIdGenService;
import com.digitalchina.info.framework.dao.support.Page;
import com.digitalchina.info.framework.security.entity.Department;
import com.digitalchina.info.framework.security.entity.Module;
import com.digitalchina.info.framework.security.service.SecurityManageService;
import com.digitalchina.info.framework.util.BeanUtil;
import com.digitalchina.info.framework.util.HttpUtil;
import com.digitalchina.info.framework.web.adapter.struts.BaseDispatchAction;
import com.digitalchina.info.framework.web.json.HibernateJsonUtil;

/**
 * 系统主表编号生成器Action
 * @Class Name SystemTableIdGenAction
 * @Author peixf
 * @Create In Mar 26, 2009
 */
public class SystemTableIdGenAction extends BaseDispatchAction{
	
	private SystemColumnService scs = (SystemColumnService) getBean("systemColumnService");
	private SystemMainTableService smts = (SystemMainTableService) getBean("systemMainTableService");
	private SystemMainColumnService smcs = (SystemMainColumnService) getBean("systemMainColumnService");
	private SecurityManageService sms = (SecurityManageService) getBean("securityManageService");
	//private SystemExtColumnServcie secs=(SystemExtColumnServcie)getBean("systemExtColumnService");
	private SystemTableIdGenService stgs=(SystemTableIdGenService)getBean("systemTableIdGenService");
	
	public ActionForward list(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String json = "";
		int pageSize = 10;
		int pageNo = HttpUtil.getInt(request, "start", 1);
		String orderBy = HttpUtil.getString(request, "orderBy", "id");
		boolean isAsc = HttpUtil.getBoolean(request, "isAsc", false);
	
		Map requestParams = HttpUtil.requestParam2Map(request);
		
		Page page = stgs.findAllSystemMainTableIdBuilder(requestParams, pageNo, pageSize, orderBy, isAsc);
		
		Long total = page.getTotalCount();
		List<SystemMainTableIdBuilder> queryList = page.list();
		for(int i=0; i<queryList.size(); i++){
			SystemMainTableIdBuilder s = queryList.get(i);
			SystemMainTable smt = s.getSystemMainTable();
			Department department = s.getDepartment();
		}
		//***********************2期*************************
		
//		List<Map<String, Object>> listData = metaDataManager
//				.getEntityMapDataForList(clazz, queryList);
//		List<UserTableSetting> userVisibleColumns = metaDataManager
//				.getUserColumnForList(clazz);
//		json = CoderForList.encode(userVisibleColumns, listData,total);
//		json = json.replaceAll("\\\\", "\\\\\\\\");
//		
		return mapping.findForward("list");
	}
	
	public ActionForward toDetail(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
	
		List modules = getService().findAll(Module.class);
		request.setAttribute("modules", modules);
		
		int pageNo = HttpUtil.getInt(request, "pageNo", 1);
		String moduleId = request.getParameter("module");
		String tableName = request.getParameter("tableName");
		Module module = null;
		if(StringUtils.isNotBlank(moduleId)){
			 module = (Module) getService().find(Module.class, moduleId);
			 request.setAttribute("module", module);
		}
		if(StringUtils.isNotBlank(tableName)){
			 request.setAttribute("tableName", tableName);
		}
		Page page = smts.findSystemMainTableByModule(module, tableName, pageNo, pageSize);
		request.setAttribute("page", page);
		
		return mapping.findForward("tjList");
	}
	
	
	public ActionForward toForm(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
	
		String smtId = request.getParameter("id");
		SystemMainTable smt = smts.findSystemMainTable(smtId);
		request.setAttribute("smt", smt);
		
		List modules = getService().findAll(Module.class);
		request.setAttribute("modules", modules);
		
		List columns = scs.findSystemTableColumns(smt);
		request.setAttribute("columns", columns);
		
		List mainColumns = smcs.findSystemMainTableColumns(smt);
		request.setAttribute("mainColumns", mainColumns);
		
//		List extColumns =secs.findSystemExtendColumns(smt);
//		request.setAttribute("extColumns", extColumns);
		
		return mapping.findForward("form");
	}
	public ActionForward toAdd(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
	
//		String smtId = request.getParameter("id");
//		SystemMainTable smt = smts.findSystemMainTable(smtId);
//		request.setAttribute("smt", smt);
		
		List modules = getService().findAll(Module.class);
		request.setAttribute("modules", modules);
		
//		List columns = scs.findSystemTableColumns(smt);
//		request.setAttribute("columns", columns);
//		
//		List mainColumns = smcs.findSystemMainTableColumns(smt);
//		request.setAttribute("mainColumns", mainColumns);
//		
//		List extColumns =secs.findSystemExtendColumns(smt);
//		request.setAttribute("extColumns", extColumns);
		
		return mapping.findForward("form");
	} 
	
	public ActionForward save(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
	
		SystemMainTable smt = (SystemMainTable) BeanUtil.getObject(request, SystemMainTable.class);
		String tableCnName = HttpUtil.ConverUnicode(request.getParameter("tableCnName"));
		smt.setTableCnName(tableCnName);
		this.smts.saveSystemMainTable(smt);

		//HibernateJsonUtil
		String result = HibernateJsonUtil.toJSONString(smt);

		response.setCharacterEncoding("gbk");
		response.getWriter().write("{success:true,data:"+result+"}");
		response.getWriter().flush();		
		return null;
	}
	
	public ActionForward saveTable(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
	
		SystemMainTable smt = (SystemMainTable) BeanUtil.getObject(request, SystemMainTable.class);
		this.smts.saveSystemMainTable(smt);
	
		return HttpUtil.redirect("sysMainTable.do?methodCall=toForm&id="+smt.getId()); 
	}
	
	
	public ActionForward loadNewTables(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
	
		//String smtId = request.getParameter("mainTableId");
		//SystemMainTable smt = this.smts.findSystemMainTable(smtId);
		this.smts.saveSystemMainTableFromMapping();

		//HibernateJsonUtil
		String result = "";

		response.setCharacterEncoding("gbk");
		response.getWriter().write("{success:true,data:"+result+"}");
		response.getWriter().flush();		
		return null;
	}
	
	public ActionForward remove(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		String[] ids = request.getParameterValues("id");
		int pageNo = HttpUtil.getInt(request, "pageNo", 1);
		smts.removeSystemMainTable(ids);
		
		return HttpUtil.redirect("sysMainTable.do?methodCall=list&pageNo="+pageNo); //+pageNo+"&level="+level
	}
	
	
	
	
	
	
	
	
}
