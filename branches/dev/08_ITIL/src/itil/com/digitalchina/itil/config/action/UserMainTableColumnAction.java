package com.digitalchina.itil.config.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.digitalchina.info.appframework.metadata.ColumnDTO;
import com.digitalchina.info.appframework.metadata.entity.Column;
import com.digitalchina.info.appframework.metadata.entity.PropertyType;
import com.digitalchina.info.appframework.metadata.entity.SystemMainTable;
import com.digitalchina.info.appframework.metadata.entity.SystemMainTableColumn;
import com.digitalchina.info.appframework.metadata.entity.SystemMainTableColumnType;
//import com.digitalchina.info.appframework.metadata.entity.SystemMainTableExtColumn;
import com.digitalchina.info.appframework.metadata.entity.ValidateType;
import com.digitalchina.info.appframework.metadata.service.SystemColumnService;
//import com.digitalchina.info.appframework.metadata.service.SystemExtColumnServcie;
import com.digitalchina.info.appframework.metadata.service.SystemMainColumnService;
import com.digitalchina.info.appframework.metadata.service.SystemMainTableService;
import com.digitalchina.info.framework.exception.ServiceException;
import com.digitalchina.info.framework.security.entity.Module;
import com.digitalchina.info.framework.security.service.SecurityManageService;
import com.digitalchina.info.framework.util.BeanUtil;
import com.digitalchina.info.framework.util.HttpUtil;
import com.digitalchina.info.framework.web.adapter.struts.BaseDispatchAction;
import com.digitalchina.info.framework.web.json.JsonUtil;
import com.digitalchina.itil.config.service.CustomerTableService;

/**
 * 系统主表管理
 * @Class Name SystemMainTableAction
 * @Author peixf
 * @version 3.0
 * @Create In 2008-7-25
 */
public class UserMainTableColumnAction extends BaseDispatchAction{
	
	private SystemColumnService scs = (SystemColumnService) getBean("systemColumnService");
	private SystemMainTableService smts = (SystemMainTableService) getBean("systemMainTableService");
	private SystemMainColumnService smcs = (SystemMainColumnService) getBean("systemMainColumnService");
	private SecurityManageService sms = (SecurityManageService) getBean("securityManageService");
	private CustomerTableService cts = (CustomerTableService)getBean("customerTableService");
//	private SystemExtColumnServcie secs = (SystemExtColumnServcie) getBean("systemExtColumnService");
	
	public ActionForward toForm(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
	
		String smtcId = request.getParameter("id");
		String smtId = request.getParameter("tableId");
		SystemMainTable smt = (SystemMainTable) getService().find(SystemMainTable.class, smtId, true);
		request.setAttribute("smt", smt);
		if(StringUtils.isNotBlank(smtcId)){
			SystemMainTableColumn smtc = smcs.findSystemMainTableColumnById(smtcId);
			request.setAttribute("detail", smtc);
			
			SystemMainTable ftable = smtc.getForeignTable();
			if(ftable!=null){
				List fcolumns = scs.findSystemTableColumns(ftable);
				request.setAttribute("fcolumns", fcolumns);
			}
			
			SystemMainTable refTable = smtc.getReferencedTable();
			if(refTable!=null){
				List fcolumns = scs.findSystemTableColumns(refTable);
				request.setAttribute("refcolumns", fcolumns);
			}
			
		}else{
			SystemMainTableColumn smtc = new SystemMainTableColumn();
			smtc.setSystemMainTable(smt);
			request.setAttribute("detail", smtc);
		}
		
		
		List propertyTypes = getService().findAll(PropertyType.class);
		request.setAttribute("propertyTypes", propertyTypes);
		
		List systemMainTableColumnTypes = getService().findAll(SystemMainTableColumnType.class);
		request.setAttribute("systemMainTableColumnTypes", systemMainTableColumnTypes);
		
		List validateTypes = getService().findAll(ValidateType.class);
		request.setAttribute("validateTypes", validateTypes);
		
		List modules = getService().findAll(Module.class);
		request.setAttribute("modules", modules);
		
		List sysMainTables = getService().findAllBy(SystemMainTable.class, "tableCnName", true);
		request.setAttribute("sysMainTables", sysMainTables);
		
		return mapping.findForward("form");
	}
	
//	public ActionForward toExtForm(ActionMapping mapping,
//			ActionForm actionForm, HttpServletRequest request,
//			HttpServletResponse response) throws Exception {
//	
//		String tableId=request.getParameter("tableId");
//		request.setAttribute("tableId", tableId);
//		String extendTableId=request.getParameter("id");	
//		if(StringUtils.isNotBlank(extendTableId)){
//			SystemMainTableExtColumn smtec=secs.findExtendColumn(extendTableId);
//			request.setAttribute("detail", smtec);
//			request.setAttribute("id", extendTableId);
//			String selectType=smtec.getSystemMainTableColumnType().getColumnTypeName();
//			Integer extSelectType=smtec.getExtSelectType();
//			if(extSelectType!=null&&extSelectType==0){
//				List sysMainTables = getService().findAllBy(SystemMainTable.class, "tableCnName", true);
//				List fcolumns = scs.findSystemTableColumns(smtec.getForeignTable());
//				request.setAttribute("sysMainTables", sysMainTables);
//				request.setAttribute("fcolumns", fcolumns);
//			}
//		}
//		SystemMainTable smt = smts.findSystemMainTable(tableId);
//		request.setAttribute("smt", smt);
//		List propertyTypes = getService().findAll(PropertyType.class);
//		request.setAttribute("propertyTypes", propertyTypes);
//		
//		List systemMainTableColumnTypes = getService().findAll(SystemMainTableColumnType.class);
//		request.setAttribute("systemMainTableColumnTypes", systemMainTableColumnTypes);
//		
//		List validateTypes = getService().findAll(ValidateType.class);
//		request.setAttribute("validateTypes", validateTypes);
//		
//		List modules = getService().findAll(Module.class);
//		request.setAttribute("modules", modules);
//		
//		return mapping.findForward("extform");
//	}

	public ActionForward save(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
	
		SystemMainTableColumn smtc = (SystemMainTableColumn) BeanUtil.getObject(request, SystemMainTableColumn.class);
		SystemMainTable smt = smtc.getSystemMainTable();

		if(smt==null){
			String smtForAdd = request.getParameter("smtForAdd");
			smt = (SystemMainTable) getService().find(SystemMainTable.class, smtForAdd);
			smtc.setSystemMainTable(smt);
		}
		
		cts.saveSystemMainTableColumn(smtc);
		
		//return HttpUtil.redirect("sysMainTableColumn.do?methodCall=toForm&tableId="+smt.getId()+"&id="+smtc.getId());
		return HttpUtil.redirect("userMainTable.do?methodCall=toForm&id="+smt.getId());

	}
	
	
//	public ActionForward saveExtendColumn(ActionMapping mapping,
//			ActionForm actionForm, HttpServletRequest request,
//			HttpServletResponse httpServletResponse) throws Exception {
//		//记录当前主表
//		String mainTableId = request.getParameter("systemMainTable");
//		String systemMainTableColumnType = request.getParameter("systemMainTableColumnType");
//		String extSelectType=request.getParameter("extSelectType");
//		//保存扩展字段
//		SystemMainTableExtColumn extendTable = (SystemMainTableExtColumn) BeanUtil.getObject(request, SystemMainTableExtColumn.class); 
//		
//		boolean isOptionExt = false;
//		
//		SystemMainTableExtColumn smtec=secs.saveExtendColumns(extendTable);
//		return HttpUtil.redirect("userMainTable.do?methodCall=toForm&id="+mainTableId);
//	}
	
	public ActionForward remove(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		String smtId = request.getParameter("smtId");
		SystemMainTable smt = (SystemMainTable) super.getService().find(SystemMainTable.class, smtId);
		Integer deployFlag = smt.getDeployFlag();
		if(deployFlag!=null&& deployFlag.intValue()==1){
			//throw new ApplicationException("当前表已经出于发布状态，不可以删除字段");
		}
		String[] ids = request.getParameterValues("id");
		
		this.cts.removeSystemMainTableColumn(ids);
		
		int pageNo = HttpUtil.getInt(request, "pageNo", 1);
		
		return HttpUtil.redirect("userMainTable.do?methodCall=toForm&id="+smtId+"&pageNo="+pageNo); //+pageNo+"&level="+level
	}
	
	public ActionForward findTableByModule(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
	
		String moduleId = request.getParameter("moduleId");
		Module module = (Module) getService().find(Module.class, moduleId);
		
		List fcolumnMaps = new ArrayList();
		List<SystemMainTable> smtables = smts.findSystemMainTableByModule(module);
		//List fcolumns = scs.findSystemTableColumns(ftable);
		Iterator iter = smtables.iterator();
		while(iter.hasNext()){
			SystemMainTable c = (SystemMainTable) iter.next();
			Map tbMap = new HashMap();
			tbMap.put("id", c.getId());
			//tbMap.put("tableName", c.getTableName());
			tbMap.put("tableCnName", c.getTableCnName());
			
			fcolumnMaps.add(tbMap);
		}
		String result = JsonUtil.toJSONString(fcolumnMaps);

		List modules = getService().findAll(Module.class);
		request.setAttribute("modules", modules);
		
		response.setCharacterEncoding("gbk");
		response.getWriter().write("{success:true,data:"+result+"}");
		response.getWriter().flush();		
		return null;
	}
	
	
	public ActionForward findColumnsByTable(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
	
		String tableId = request.getParameter("ftableId");
		SystemMainTable ftable = (SystemMainTable) getService().find(SystemMainTable.class, tableId);
		
		List fcolumnMaps = new ArrayList();
		List fcolumns = scs.findSystemTableColumns(ftable);
		Iterator iter = fcolumns.iterator();
		while(iter.hasNext()){
			Column c = (Column) iter.next();
			ColumnDTO cw = new ColumnDTO();
			cw.setId(c.getId());
			cw.setColumnName(c.getColumnName());
			cw.setColumnCnName(c.getColumnCnName());
			
			fcolumnMaps.add(cw);
		}
		String result = JsonUtil.toJSONString(fcolumnMaps);

		response.setCharacterEncoding("gbk");
		response.getWriter().write("{success:true,data:"+result+"}");
		response.getWriter().flush();		
		return null;
	}
	
	public ActionForward loadNewColumns(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		response.setCharacterEncoding("gbk");
		
		String smtId = request.getParameter("mainTableId");
		SystemMainTable smt = this.smts.findSystemMainTable(smtId);
		try {
			smcs.saveSystemMainTableColumnsLoadFromDb(smt);
			response.getWriter().println("{success:true,errors:{}}");
		} catch (ServiceException e) {
			e.printStackTrace();
			//throw new ApplicationException("系统主表名称不存在");
			response.getWriter().println("{success:false,errors:{}}");
		}
		
		
		
		response.getWriter().flush();		
		return null;
	}
	
	
	
	
	
	
	
	
}
