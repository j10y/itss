package com.zsgj.info.appframework.metadata.web.struts;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.zsgj.info.appframework.metadata.ColumnDTO;
import com.zsgj.info.appframework.metadata.entity.Column;
import com.zsgj.info.appframework.metadata.entity.ExtOptionData;
import com.zsgj.info.appframework.metadata.entity.PropertyType;
import com.zsgj.info.appframework.metadata.entity.SystemMainTable;
import com.zsgj.info.appframework.metadata.entity.SystemMainTableColumn;
import com.zsgj.info.appframework.metadata.entity.SystemMainTableColumnType;
import com.zsgj.info.appframework.metadata.entity.ValidateType;
import com.zsgj.info.appframework.metadata.service.SystemColumnService;
import com.zsgj.info.appframework.metadata.service.SystemMainAndExtColumnService;
import com.zsgj.info.appframework.metadata.service.SystemMainColumnService;
import com.zsgj.info.appframework.metadata.service.SystemMainTableService;
import com.zsgj.info.framework.exception.ServiceException;
import com.zsgj.info.framework.security.entity.Module;
import com.zsgj.info.framework.security.service.SecurityManageService;
import com.zsgj.info.framework.util.BeanUtil;
import com.zsgj.info.framework.util.HttpUtil;
import com.zsgj.info.framework.web.adapter.struts.BaseDispatchAction;
import com.zsgj.info.framework.web.json.JsonUtil;

/**
 * 系统主表管理
 * @Class Name SystemMainTableAction
 * @Author peixf
 * @version 3.0
 * @Create In 2008-7-25
 */
public class SystemMainTableColumnAction extends BaseDispatchAction{
	
	private SystemColumnService scs = (SystemColumnService) getBean("systemColumnService");
	private SystemMainTableService smts = (SystemMainTableService) getBean("systemMainTableService");
	private SystemMainColumnService smcs = (SystemMainColumnService) getBean("systemMainColumnService");
	private SecurityManageService sms = (SecurityManageService) getBean("securityManageService");
	private SystemMainAndExtColumnService smecs=(SystemMainAndExtColumnService)getBean("systemMainAndExtColumnService");
	
	public ActionForward toForm(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
	
		String smtcId = request.getParameter("id");
		String smtId = request.getParameter("tableId");
		SystemMainTable smt = (SystemMainTable) getService().find(SystemMainTable.class, smtId);
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
			
		}
		List<SystemMainTableColumn> columns = scs.findSystemTableColumns(smt);
		request.setAttribute("columns", columns);
		
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
		smcs.saveSystemMainTableColumn(smtc);
		return HttpUtil.redirect("sysMainTable.do?methodCall=toForm&id="+smt.getId());

	}
	
	public ActionForward remove(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		String[] ids = request.getParameterValues("id");
		
		this.smcs.removeSystemMainTableColumn(ids);
		
		int pageNo = HttpUtil.getInt(request, "pageNo", 1);
		String smtId = request.getParameter("smtId");
		
		
		return HttpUtil.redirect("sysMainTable.do?methodCall=toForm&id="+smtId+"&pageNo="+pageNo); //+pageNo+"&level="+level
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
	
	public ActionForward addOrUpdateColumns(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String extendTableId=request.getParameter("id");
		List <ExtOptionData> list=smecs.findExtOptionDataByExtColId(extendTableId);
		request.setAttribute("extOptionDatas", list);
		request.setAttribute("extendTableId", extendTableId);
		return mapping.findForward("columns");
	}
	
	public ActionForward saveSelectOption(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		String extendTableId=request.getParameter("extColumnId");
		ExtOptionData extOption=(ExtOptionData)BeanUtil.getObject(request, ExtOptionData.class);
		smecs.saveExtOption(extOption);
		List <ExtOptionData> list=smecs.findExtOptionDataByExtColId(extendTableId);
		request.setAttribute("extOptionDatas", list);
		request.setAttribute("extendTableId", extendTableId);
		return mapping.findForward("columns");
	}
	
	public ActionForward removeOptionById(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String id=request.getParameter("id");
		String extendTableId=request.getParameter("extColumnId");
		smecs.removeOptionById(Long.parseLong(id));
		List <ExtOptionData> list=smecs.findExtOptionDataByExtColId(extendTableId);
		request.setAttribute("extOptionDatas", list);
		request.setAttribute("extendTableId", extendTableId);
		return mapping.findForward("columns");
	}
	
	public ActionForward modifyOptionById(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
	HttpServletResponse response) throws Exception {
	String id=request.getParameter("id");
	String extendTableId=request.getParameter("extColumnId");
	//secs.removeOptionById(Long.parseLong(id));
	ExtOptionData extOption=(ExtOptionData) smecs.findOptionById(Long.parseLong(id));
	List<ExtOptionData> extOptions=smecs.findExtOptionDataByExtColId(extendTableId);
	if(extOption!=null){
	   extOptions.remove(extOption);
	}
	request.setAttribute("extOptionDatas", extOptions);
	request.setAttribute("extOption", extOption);
	request.setAttribute("extendTableId", extendTableId);
	return mapping.findForward("columns");
}

	
	
	
	
}
