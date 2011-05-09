package com.zsgj.info.appframework.metadata.web.struts;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.zsgj.info.appframework.metadata.entity.ExtOptionData;
import com.zsgj.info.appframework.metadata.entity.SystemMainTable;
import com.zsgj.info.appframework.metadata.entity.SystemMainTableColumn;
import com.zsgj.info.appframework.metadata.service.SystemColumnService;
import com.zsgj.info.appframework.metadata.service.SystemMainAndExtColumnService;
import com.zsgj.info.appframework.metadata.service.SystemMainColumnService;
import com.zsgj.info.appframework.metadata.service.SystemMainTableService;
import com.zsgj.info.framework.dao.support.Page;
import com.zsgj.info.framework.security.entity.Module;
import com.zsgj.info.framework.security.service.SecurityManageService;
import com.zsgj.info.framework.util.BeanUtil;
import com.zsgj.info.framework.util.HttpUtil;
import com.zsgj.info.framework.web.adapter.struts.BaseDispatchAction;
import com.zsgj.info.framework.web.json.HibernateJsonUtil;

/**
 * 系统主表管理
 * @Class Name SystemMainTableAction
 * @Author peixf
 * @version 3.0
 * @Create In 2008-7-25
 */
public class SystemMainTableAction extends BaseDispatchAction{
	
	private SystemColumnService scs = (SystemColumnService) getBean("systemColumnService");
	private SystemMainTableService smts = (SystemMainTableService) getBean("systemMainTableService");
	private SystemMainColumnService smcs = (SystemMainColumnService) getBean("systemMainColumnService");
	private SecurityManageService sms = (SecurityManageService) getBean("securityManageService");
	
	private SystemMainAndExtColumnService smecs=(SystemMainAndExtColumnService)getBean("systemMainAndExtColumnService");
	
	public ActionForward list(ActionMapping mapping,
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
		
		return mapping.findForward("list");
	}
	public ActionForward tjList(ActionMapping mapping,
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
	public ActionForward toTjForm(ActionMapping mapping,
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
		
//		List statisPicture=(List) getService().find(StatisticsPicture.class, "sysMainTable", smt);
//		request.setAttribute("statisPicture", statisPicture);
		return mapping.findForward("tjForm");
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
		
		//List mainColumns = smcs.findSystemMainTableColumns(smt);
		List mainColumns=smecs.findColumnByIsExtAndSysMainTable(SystemMainTableColumn.isMain, smt);
		request.setAttribute("mainColumns", mainColumns);
		
		//List extColumns =secs.findSystemExtendColumns(smt);
		List extColumns =smecs.findColumnByIsExtAndSysMainTable(SystemMainTableColumn.isExt, smt);
		request.setAttribute("extColumns", extColumns);
		
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
