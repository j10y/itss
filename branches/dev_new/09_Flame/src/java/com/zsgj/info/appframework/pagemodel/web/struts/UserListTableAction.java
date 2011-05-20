package com.zsgj.info.appframework.pagemodel.web.struts;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.zsgj.info.appframework.metadata.entity.SystemMainTable;
import com.zsgj.info.appframework.metadata.service.SystemColumnService;
import com.zsgj.info.appframework.metadata.service.SystemMainColumnService;
import com.zsgj.info.appframework.metadata.service.SystemMainTableService;
import com.zsgj.info.appframework.pagemodel.service.UserListTableService;
import com.zsgj.info.framework.dao.support.Page;
import com.zsgj.info.framework.security.entity.Module;
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
public class UserListTableAction extends BaseDispatchAction{
	
	private SystemColumnService scs = (SystemColumnService) getBean("systemColumnService");
	private SystemMainTableService smts = (SystemMainTableService) getBean("systemMainTableService");
	private SystemMainColumnService smcs = (SystemMainColumnService) getBean("systemMainColumnService");
//	private SecurityManageService sms = (SecurityManageService) getBean("securityManageService");
//	private SystemExtColumnServcie secs=(SystemExtColumnServcie)getBean("systemExtColumnService");
	//private CustomerTableService cts=(CustomerTableService) getBean("customerTableService");
//	private PageModelGenService pmgs = (PageModelGenService) super.getBean("pageModelGenService");
	private UserListTableService ults = (UserListTableService) getBean("userListTableService");

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
		Page page = ults.findSystemMainTableByModule(module, tableName, pageNo, pageSize);
		request.setAttribute("page", page);
		return mapping.findForward("list");
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
		
		
		return mapping.findForward("form");
	}
	public ActionForward toAdd(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		List modules = getService().findAll(Module.class);
		request.setAttribute("modules", modules);
				
		return mapping.findForward("form");
	} 
	
	public ActionForward save(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
	
		SystemMainTable smt = (SystemMainTable) BeanUtil.getObject(request, SystemMainTable.class);
		String tableCnName = HttpUtil.ConverUnicode(request.getParameter("tableCnName"));
		smt.setTableCnName(tableCnName);
		this.ults.saveSystemMainTable(smt);

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
		this.ults.saveSystemMainTable(smt);			
		
		return HttpUtil.redirect("userListMainTable.do?methodCall=toForm&id="+smt.getId()); 
	}
	
	
	public ActionForward deployTable(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		SystemMainTable smt = (SystemMainTable) BeanUtil.getObject(request, SystemMainTable.class);
		smt = (SystemMainTable) super.getService().find(SystemMainTable.class, String.valueOf(smt.getId()));
//		String tableName = smt.getTableName();
//		String className = smt.getClassName();
//		int lastDot = className.lastIndexOf(".");
//		String sourcePkg = className.substring(0, lastDot);
//		String sourceClass = className.substring(lastDot+1);
		this.ults.saveSystemMainTableDeploy(smt);
		return HttpUtil.redirect("userListMainTable.do?methodCall=toForm&id="+smt.getId()); 
	}
	
	public ActionForward loadNewTables(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
	
		String smtId = request.getParameter("mainTableId");
		SystemMainTable smt = this.smts.findSystemMainTable(smtId);
		this.smts.saveSystemMainTableFromMapping();

		//HibernateJsonUtil
		String result = HibernateJsonUtil.toJSONString(smt);

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
		this.ults.removeSystemMainTable(ids);
		
		return HttpUtil.redirect("userListMainTable.do?methodCall=list&pageNo="+pageNo); 
	}	
	
	
	
	
	
	
	
	
}
