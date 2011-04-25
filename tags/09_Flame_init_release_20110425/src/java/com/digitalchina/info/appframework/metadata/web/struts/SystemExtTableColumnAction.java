//package com.digitalchina.info.appframework.metadata.web.struts;
//
//import java.util.ArrayList;
//import java.util.Iterator;
//import java.util.List;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import org.apache.struts.action.ActionForm;
//import org.apache.struts.action.ActionForward;
//import org.apache.struts.action.ActionMapping;
//
//import com.digitalchina.info.appframework.metadata.ColumnDTO;
//import com.digitalchina.info.appframework.metadata.entity.Column;
//import com.digitalchina.info.appframework.metadata.entity.ExtOptionData;
//import com.digitalchina.info.appframework.metadata.entity.PropertyType;
//import com.digitalchina.info.appframework.metadata.entity.SystemMainTable;
//import com.digitalchina.info.appframework.metadata.entity.SystemMainTableColumn;
//import com.digitalchina.info.appframework.metadata.entity.SystemMainTableColumnType;
//import com.digitalchina.info.appframework.metadata.entity.SystemMainTableExtColumn;
//import com.digitalchina.info.appframework.metadata.entity.ValidateType;
//import com.digitalchina.info.appframework.metadata.service.MetaDataService;
//import com.digitalchina.info.appframework.metadata.service.SystemColumnService;
//import com.digitalchina.info.appframework.metadata.service.SystemExtColumnServcie;
//import com.digitalchina.info.appframework.metadata.service.SystemMainColumnService;
//import com.digitalchina.info.appframework.metadata.service.SystemMainTableService;
//import com.digitalchina.info.framework.security.entity.Module;
//import com.digitalchina.info.framework.security.service.SecurityManageService;
//import com.digitalchina.info.framework.util.BeanUtil;
//import com.digitalchina.info.framework.util.HttpUtil;
//import com.digitalchina.info.framework.web.adapter.struts.BaseDispatchAction;
//import com.digitalchina.info.framework.web.json.JsonUtil;
//
//public class SystemExtTableColumnAction extends BaseDispatchAction{
//	private SystemColumnService scs = (SystemColumnService) getBean("systemColumnService");
//	private SystemMainTableService smts = (SystemMainTableService) getBean("systemMainTableService");
//	private SystemMainColumnService smcs = (SystemMainColumnService) getBean("systemMainColumnService");
//	private SecurityManageService sms = (SecurityManageService) getBean("securityManageService");
//	private MetaDataService sds = (MetaDataService) getBean("metaDataService");
//	private SystemExtColumnServcie secs = (SystemExtColumnServcie) getBean("systemExtColumnService");
//	public ActionForward toExtForm(ActionMapping mapping,
//			ActionForm actionForm, HttpServletRequest request,
//			HttpServletResponse response) throws Exception {
//	
//		String tableId=request.getParameter("tableId");
//		request.setAttribute("tableId", tableId);
//		String extendTableId=request.getParameter("id");	
//		if(extendTableId!=null){
//		SystemMainTableExtColumn smtec=secs.findExtendColumn(extendTableId);
//		request.setAttribute("detail", smtec);
//		request.setAttribute("id", extendTableId);
//		//SystemMainTableColumn smtc = smcs.findSystemMainTableColumnById(tableId);	
//		String selectType=smtec.getSystemMainTableColumnType().getColumnTypeName();
//		Integer extSelectType=smtec.getExtSelectType();
//		if(extSelectType!=null&&extSelectType==0){
//				List sysMainTables = getService().findAllBy(SystemMainTable.class, "tableCnName", true);
//				List fcolumns = scs.findSystemTableColumns(smtec.getForeignTable());
//				request.setAttribute("sysMainTables", sysMainTables);
//				request.setAttribute("fcolumns", fcolumns);
//			}
////		if(extSelectType!=null&&extSelectType==2){
////			Integer extSelectColumnNum=smtec.getExtendTableColumnNum();
////				if(selectType.equals("select")){
////					List<ExtOption> extOptions=secs.findExtOptionsByextSelectColumnNum(selectType,extSelectColumnNum);
////				    request.setAttribute("extOptions", extOptions);
////				}
////			}
//		}
//		SystemMainTable smt = smts.findSystemMainTable(tableId);
//		request.setAttribute("smt", smt);
//		List propertyTypes = getService().findAll(PropertyType.class);
//		request.setAttribute("propertyTypes", propertyTypes);
//		
//		//SystemMainTableExtColumn systemMainTableExtColumn=
////		SystemMainTable ftable = smtc.getForeignTable();
////		List fcolumns = scs.findSystemTableColumns(ftable);
////		
//		List systemMainTableColumnTypes = getService().findAll(SystemMainTableColumnType.class);
//		request.setAttribute("systemMainTableColumnTypes", systemMainTableColumnTypes);
//		
//		List validateTypes = getService().findAll(ValidateType.class);
//		request.setAttribute("validateTypes", validateTypes);
//		
//		List modules = getService().findAll(Module.class);
//		request.setAttribute("modules", modules);
//		
//
//		
//		return mapping.findForward("extForm");
//	}
//	
//	public ActionForward findSysMainTableByTable(ActionMapping mapping,
//			ActionForm actionForm, HttpServletRequest request,
//			HttpServletResponse response) throws Exception {
//		List sysMainTableMaps = new ArrayList();
//		List<SystemMainTable> sysMainTables = getService().findAllBy(SystemMainTable.class, "tableCnName", true);
//		for(SystemMainTable sysMainTable:sysMainTables){
//			ColumnDTO cw = new ColumnDTO();
//			cw.setId(sysMainTable.getId());
//			cw.setColumnName(sysMainTable.getTableName());
//			cw.setColumnCnName(sysMainTable.getTableCnName());			
//			sysMainTableMaps.add(cw);
//		}
//		String result = JsonUtil.toJSONString(sysMainTableMaps);
//
//		response.setCharacterEncoding("gbk");
//		response.getWriter().write("{success:true,data:"+result+"}");
//		response.getWriter().flush();		
//		return null;
//	}
//	
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
//		return HttpUtil.redirect("sysMainTable.do?methodCall=toForm&id="+mainTableId);
//	}
//	
//	public ActionForward remove(ActionMapping mapping,
//			ActionForm actionForm, HttpServletRequest request,
//			HttpServletResponse response) throws Exception {
//		
//		String[] ids = request.getParameterValues("id");
//		
//		this.secs.removeSystemExtTableColumn(ids);
//		
//		int pageNo = HttpUtil.getInt(request, "pageNo", 1);
//		String smtId = request.getParameter("smtId");
//
//		return HttpUtil.redirect("sysMainTable.do?methodCall=toForm&id="+smtId+"&pageNo="+pageNo); //+pageNo+"&level="+level
//	}
//
//	public ActionForward addOrUpdateColumns(ActionMapping mapping,
//			ActionForm actionForm, HttpServletRequest request,
//			HttpServletResponse response) throws Exception {
//		String extendTableId=request.getParameter("id");
//		SystemMainTableExtColumn smtec=secs.findExtendColumn(extendTableId);
//		List <ExtOptionData> list=secs.findExtOptionDataByExtColId(extendTableId);
//		request.setAttribute("extOptionDatas", list);
//		request.setAttribute("smtec", smtec);
//		request.setAttribute("extendTableId", extendTableId);
//		return mapping.findForward("columns");
//	}
//	public ActionForward saveSelectOption(ActionMapping mapping,
//			ActionForm actionForm, HttpServletRequest request,
//			HttpServletResponse response) throws Exception {
//		
//		String extendTableId=request.getParameter("extColumnId");
//		SystemMainTableExtColumn smtec=secs.findExtendColumn(extendTableId);
//		ExtOptionData extOption=(ExtOptionData)BeanUtil.getObject(request, ExtOptionData.class);
//		secs.saveExtOption(extOption);
//		List <ExtOptionData> list=secs.findExtOptionDataByExtColId(extendTableId);
//		request.setAttribute("extOptionDatas", list);
//		request.setAttribute("smtec", smtec);
//		request.setAttribute("extendTableId", extendTableId);
//		return mapping.findForward("columns");
//	}
//	
//	public ActionForward removeOptionById(ActionMapping mapping,
//			ActionForm actionForm, HttpServletRequest request,
//			HttpServletResponse response) throws Exception {
//		String id=request.getParameter("id");
//		String extendTableId=request.getParameter("extColumnId");
//		SystemMainTableExtColumn smtec=secs.findExtendColumn(extendTableId);
//		secs.removeOptionById(Long.parseLong(id));
//		List <ExtOptionData> list=secs.findExtOptionDataByExtColId(extendTableId);
//		request.setAttribute("extOptionDatas", list);
//		request.setAttribute("smtec", smtec);
//		request.setAttribute("extendTableId", extendTableId);
//		return mapping.findForward("columns");
//	}
//	
//	public ActionForward modifyOptionById(ActionMapping mapping,
//			ActionForm actionForm, HttpServletRequest request,
//			HttpServletResponse response) throws Exception {
//		String id=request.getParameter("id");
//		String extendTableId=request.getParameter("extColumnId");
//		SystemMainTableExtColumn smtec=secs.findExtendColumn(extendTableId);
//		//secs.removeOptionById(Long.parseLong(id));
//		ExtOptionData extOption=(ExtOptionData) secs.findOptionById(Long.parseLong(id));
//		List<ExtOptionData> extOptions=secs.findExtOptionDataByExtColId(extendTableId);
//		if(extOption!=null){
//		   extOptions.remove(extOption);
//		}
//		request.setAttribute("extOptionDatas", extOptions);
//		request.setAttribute("extOption", extOption);
//		request.setAttribute("smtec", smtec);
//		request.setAttribute("extendTableId", extendTableId);
//		return mapping.findForward("columns");
//	}
//}
