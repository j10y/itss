package com.zsgj.itil.config.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.jspsmart.upload.SmartUpload;
import com.zsgj.info.appframework.metadata.entity.SystemMainTable;
import com.zsgj.info.appframework.metadata.service.SystemColumnService;
import com.zsgj.info.appframework.metadata.service.SystemMainColumnService;
import com.zsgj.info.appframework.metadata.service.SystemMainTableService;
import com.zsgj.info.appframework.pagemodel.entity.PagePanel;
import com.zsgj.info.appframework.pagemodel.service.PageModelGenService;
import com.zsgj.info.framework.dao.support.Page;
import com.zsgj.info.framework.exception.ApplicationException;
import com.zsgj.info.framework.security.entity.Module;
import com.zsgj.info.framework.security.service.SecurityManageService;
import com.zsgj.info.framework.util.BeanUtil;
import com.zsgj.info.framework.util.HttpUtil;
import com.zsgj.info.framework.web.adapter.struts.BaseDispatchAction;
import com.zsgj.info.framework.web.json.HibernateJsonUtil;
import com.zsgj.itil.config.entity.ConfigItemType;
import com.zsgj.itil.config.service.CustomerTableService;

/**
 * 系统主表管理
 * @Class Name SystemMainTableAction
 * @Author peixf
 * @version 3.0
 * @Create In 2008-7-25
 */
public class UserMainTableAction extends BaseDispatchAction{
	
	static final String FSP = System.getProperty("file.separator");
	static final String LSP = System.getProperty("line.separator");
	private SystemColumnService scs = (SystemColumnService) getBean("systemColumnService");
	private SystemMainTableService smts = (SystemMainTableService) getBean("systemMainTableService");
	private SystemMainColumnService smcs = (SystemMainColumnService) getBean("systemMainColumnService");
	private SecurityManageService sms = (SecurityManageService) getBean("securityManageService");
	private CustomerTableService cts=(CustomerTableService) getBean("customerTableService");
	private PageModelGenService pmgs = (PageModelGenService) super.getBean("pageModelGenService");

	public ActionForward list(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
	
		List modules = getService().findAll(Module.class);
		request.setAttribute("modules", modules);
		
		int pageNo = HttpUtil.getInt(request, "pageNo", 1);
		String moduleId = request.getParameter("module");
		String duxh = request.getParameter("duxh");
		String tableName = request.getParameter("tableName");
		Module module = null;
		if(StringUtils.isNotBlank(moduleId)){
			 module = (Module) getService().find(Module.class, moduleId);
			 request.setAttribute("module", module);
		}
		if(StringUtils.isNotBlank(tableName)){
			 request.setAttribute("tableName", tableName);
		}
		Page page = cts.findSystemMainTableByModule(module, tableName, pageNo, pageSize);
		request.setAttribute("page", page);
		return mapping.findForward("list");
	}
	
	
	public ActionForward toForm(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
	
		String smtId = request.getParameter("id");
		SystemMainTable smt = smts.findSystemMainTable(smtId);
		request.setAttribute("smt", smt);
		
		ConfigItemType citype = this.cts.findConfigItemTypeByTable(smt);
		request.setAttribute("citype", citype);
		
		List modules = getService().findAll(Module.class);
		request.setAttribute("modules", modules);
		
		List columns = scs.findSystemTableColumns(smt);
		request.setAttribute("columns", columns);
		
		List mainColumns = smcs.findSystemMainTableColumns(smt);
		request.setAttribute("mainColumns", mainColumns);
		
//		List extColumns =secs.findSystemExtendColumns(smt);
//		request.setAttribute("extColumns", extColumns);
		
//		List<PagePanel> userPanels = this.cts.findAllPagePanels();
//		request.setAttribute("userPanels", userPanels);
		
		List<PagePanel> userPanels = this.cts.findAllBasePanels();
		request.setAttribute("userPanels", userPanels);
		
		List<PagePanel> userGroupPanels = this.cts.findAllGroupPanels();
		request.setAttribute("userGroupPanels", userGroupPanels);
		
		List<ConfigItemType> configItemTypes = cts.findAllTopConfigItems();
		request.setAttribute("configItemTypes", configItemTypes);
		
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
		
		List<ConfigItemType> configItemTypes = cts.findAllTopConfigItems();
		request.setAttribute("configItemTypes", configItemTypes);
	
		
		return mapping.findForward("form");
	} 
	
	public ActionForward save(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
	
		SystemMainTable smt = (SystemMainTable) BeanUtil.getObject(request, SystemMainTable.class);
		String tableCnName = HttpUtil.ConverUnicode(request.getParameter("tableCnName"));
		smt.setTableCnName(tableCnName);
		this.cts.saveSystemMainTable(smt);

		//HibernateJsonUtil
		String result = HibernateJsonUtil.toJSONString(smt);

		response.setCharacterEncoding("gbk");
		response.getWriter().write("{success:true,data:"+result+"}");
		response.getWriter().flush();		
		return null;
	}
	
	/**
	 * 保存配置项类型主表
	 * @Methods Name saveTable
	 * @Create In 2009-2-21 By sa
	 * @param mapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception ActionForward
	 */
	public ActionForward saveTable(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
	
		SystemMainTable smt = (SystemMainTable) BeanUtil.getObject(request, SystemMainTable.class);
		this.cts.saveSystemMainTable(smt);
		/*
		ConfigItemType citype = this.cts.findConfigItemTypeByTable(smt);
		if(citype==null){
			cts.saveConfigItemTablePanel(smt, 0);
			citype = this.cts.findConfigItemTypeByTable(smt);
		}	
		request.setAttribute("citype", citype);
		
		ConfigItemType ciptype = null;
		String parentConfigItemType = request.getParameter("parentConfigItemType");
		if(StringUtils.isNotBlank(parentConfigItemType)){
			ciptype = (ConfigItemType) super.getService().find(ConfigItemType.class, parentConfigItemType);
		}
		if(citype!=null){
			citype.setParentConfigItemType(ciptype);
		}else{
			cts.saveConfigItemTablePanel(smt, 0);
		}
		
		String pagePaneId = request.getParameter("pagePanel");
		String groupPanelId = request.getParameter("groupPanel");
		PagePanel pagePanel = null;
		PagePanel groupPanel = null;
		if(StringUtils.isNotBlank(pagePaneId)){
			pagePanel = (PagePanel) getService().find(PagePanel.class, pagePaneId);
		}
		if(StringUtils.isNotBlank(groupPanelId)){
			groupPanel = (PagePanel) getService().find(PagePanel.class, groupPanelId);
		}
		if(citype!=null){
			citype.setPagePanel(pagePanel);
			citype.setGroupPanel(groupPanel);
			super.getService().save(citype);
		}*/
		
		
		return HttpUtil.redirect("userMainTable.do?methodCall=toForm&id="+smt.getId()); 
	}
	
	
	public ActionForward importConfigItemExcel(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
	
		request.setCharacterEncoding("gbk");
		response.setCharacterEncoding("gbk"); 
		
		SmartUpload su = new SmartUpload();
		su.initialize(super.getServlet(), request,  response);
		
		SystemMainTable smt = null;
		
		try {
			su.upload();
			String smtId = su.getRequest().getParameter("systemMainTableIds");
			smt = (SystemMainTable) getService().find(SystemMainTable.class, smtId, true);
			
			ConfigItemType citype = this.cts.findConfigItemTypeByTable(smt);
			request.setAttribute("citype", citype);
			
			String uploadUrl =  "upload"+ FSP + "excel" + FSP + "ci"+ FSP; //FSP+
			String sPathFileName =request.getSession().getServletContext().getRealPath(FSP)+ uploadUrl;
			File file=new File(sPathFileName);
			if(!file.exists())
				file.mkdirs();
			for (int i=0;i<su.getFiles().getCount();i++){
				//文件上传后的文件对象
				com.jspsmart.upload.File myFile = su.getFiles().getFile(i);
				
				String filePathName = myFile.getFilePathName();
				
				if(StringUtils.isNotBlank(filePathName)){
					
					String extFileName = myFile.getFileExt();
					String sSaveFileName = "ci_" + System.currentTimeMillis()+"."+extFileName;
					myFile.saveAs(sPathFileName+sSaveFileName);
					HSSFWorkbook workbook = new HSSFWorkbook(new FileInputStream(sPathFileName+sSaveFileName));
					cts.saveConfigItemExcel(workbook, smt);
				}
		  }
		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationException("上传文件发生异常，请联系管理员");
		}
		return HttpUtil.redirect("userMainTable.do?methodCall=toForm&id="+smt.getId()+"&success=true"); 
	}
	
	/**
	 * 导出配置项模板
	 * @Methods Name exportConfigItemTemplateExcel
	 * @Create In Feb 1, 2010 By duxh
	 * @param mapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception 
	 * @Return ActionForward
	 */
	public ActionForward exportConfigItemTemplateExcel(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
	
		try {
			String citypeTableId = request.getParameter("citypeTableId");
			SystemMainTable citypeTable = (SystemMainTable) super.getService().find(SystemMainTable.class, citypeTableId);
			
			String subDir = "download"+ FSP +"citypeExcel";
			String webRoot = request.getSession().getServletContext().getRealPath(FSP);
			String fileRootPath = webRoot + subDir;
			File file=new File(fileRootPath);
			if(!file.exists())
				file.mkdirs();
			HSSFWorkbook wb =cts.getConfigItemTemplateExcel(citypeTable);
			String excelFileName = citypeTable.getTableName()+"_"+"template"+"_" + System.currentTimeMillis() + ".xls";
			String excelFullFileName = fileRootPath + FSP + excelFileName;
			FileOutputStream fileout = new FileOutputStream(excelFullFileName);
			wb.write(fileout);
			fileout.close();
			request.setAttribute("fileName",excelFileName);
			response.setCharacterEncoding("gbk");
			response.getWriter().write("{success:true,data:{fileName:'"+excelFileName+"'}}");
			response.getWriter().flush();		
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationException();
		}
	}
	
	/**
	 * 导出配置项数据Excel
	 * @Methods Name exportConfigItemExcel
	 * @Create In Feb 1, 2010 By duxh
	 * @param mapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception 
	 * @Return ActionForward
	 */
	public ActionForward exportConfigItemExcel(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
			String citypeTableId = request.getParameter("citypeTableId");
			ConfigItemType ciType = (ConfigItemType) super.getService().find(ConfigItemType.class, citypeTableId);
			String subDir = "download"+ FSP +"citypeData";
			String webRoot = request.getSession().getServletContext().getRealPath(FSP);
			String fileRootPath = webRoot + subDir;
			File file=new File(fileRootPath);
			if(!file.exists())
				file.mkdirs();
			HSSFWorkbook wb = cts.getConfigItemExcel(ciType);
			String excelFileName = ciType.getTableName() + "_" + System.currentTimeMillis() + ".xls";
			String excelFullFileName = fileRootPath + FSP + excelFileName;
			FileOutputStream fileout = new FileOutputStream(excelFullFileName);
			wb.write(fileout);
			fileout.close();
			request.setAttribute("fileName", excelFileName);
			response.setCharacterEncoding("gbk");
			response.getWriter().write("{success:true,data:{fileName:'"+excelFileName+"'}}");
			response.getWriter().flush();		
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationException();
		}
	}
	
	
	public ActionForward genAllCITypeDataForRelation(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
	
		
		//String citypeTableId = request.getParameter("citypeTableId");
		//SystemMainTable citypeTable = (SystemMainTable) super.getService().find(SystemMainTable.class, citypeTableId);
		//ConfigItemType ciType = (ConfigItemType) super.getService().find(ConfigItemType.class, citypeTableId);
		
		String subDir = "download"+ FSP +"citypeData";
		String webRoot = request.getSession().getServletContext().getRealPath(FSP);
		if(!webRoot.endsWith(FSP)){
			webRoot = webRoot + FSP;
		}
		
		String fileRootPath = webRoot + subDir;
		
		String fileName = this.cts.exportAllConfigItemForRelation(fileRootPath);
		request.setAttribute("fileName", fileName);
	
		response.setCharacterEncoding("gbk");
		response.getWriter().write("{success:true,data:{fileName:'"+fileName+"'}}");
		response.getWriter().flush();		
		return null;
		
	}
	
	public ActionForward deployTable(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		//pmgs.generatePageModelCode("configItemFinanceMix");
		SystemMainTable smt = (SystemMainTable) BeanUtil.getObject(request, SystemMainTable.class);
		smt = (SystemMainTable) super.getService().find(SystemMainTable.class, String.valueOf(smt.getId()));
		String tableName = smt.getTableName();
		String className = smt.getClassName();
		int lastDot = className.lastIndexOf(".");
		String sourcePkg = className.substring(0, lastDot);
		String sourceClass = className.substring(lastDot+1);
		this.cts.saveSystemMainTableDeploy(smt);
		
		SystemMainTable smtEvent = cts.findUserTableEvent(smt);
		if(smtEvent!=null){
			cts.saveSystemMainTableDeploy(smtEvent);
		}else{
			smtEvent = cts.saveEventTableByMainTable(smt);
			cts.saveSystemMainTableDeploy(smtEvent);
		}
		
		return HttpUtil.redirect("userMainTable.do?methodCall=toForm&id="+smt.getId()); 
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
		this.cts.removeSystemMainTable(ids);
		
		return HttpUtil.redirect("userMainTable.do?methodCall=list&pageNo="+pageNo); //+pageNo+"&level="+level
	}
	/**
	 * 配置项关系导入
	 * @Methods Name importCIRDataExcel
	 * @Create In Feb 23, 2010 By duxh
	 * @param mapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @throws Exception 
	 * @Return ActionForward
	 */
	public ActionForward importCIRDataExcel(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("gbk");
		response.setCharacterEncoding("gbk"); 
		SmartUpload su = new SmartUpload();
		su.initialize(super.getServlet(), request,  response);
		try {
			su.upload();
			String uploadUrl =  "upload"+ FSP + "excel" + FSP + "cir"+ FSP; //FSP+
			String sPathFileName =request.getSession().getServletContext().getRealPath(FSP)+ uploadUrl;
			File file=new File(sPathFileName);
			if(!file.exists())
				file.mkdirs();
			for (int i=0;i<su.getFiles().getCount();i++){
				com.jspsmart.upload.File myFile = su.getFiles().getFile(i);
				String filePathName = myFile.getFilePathName();
				if(StringUtils.isNotBlank(filePathName)){
					String extFileName = myFile.getFileExt();
					String sSaveFileName = "cir_" + System.currentTimeMillis()+"."+extFileName;
					myFile.saveAs(sPathFileName+sSaveFileName);
					HSSFWorkbook workbook = new HSSFWorkbook(new FileInputStream(sPathFileName+sSaveFileName));
					cts.saveCIRExcel(workbook);
				}
		  }
		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationException("上传文件发生异常，请联系管理员");
		}
		return null;
	}
}
