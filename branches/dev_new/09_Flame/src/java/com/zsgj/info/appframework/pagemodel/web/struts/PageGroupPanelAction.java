package com.zsgj.info.appframework.pagemodel.web.struts;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.zsgj.info.appframework.metadata.entity.Column;
import com.zsgj.info.appframework.metadata.entity.SystemMainTable;
import com.zsgj.info.appframework.metadata.entity.SystemMainTableColumn;
import com.zsgj.info.appframework.metadata.entity.TableSettingType;
import com.zsgj.info.appframework.metadata.service.SystemMainTableService;
import com.zsgj.info.appframework.pagemodel.entity.PageGroupPanelTable;
import com.zsgj.info.appframework.pagemodel.entity.PagePanel;
import com.zsgj.info.appframework.pagemodel.entity.PagePanelColumn;
import com.zsgj.info.appframework.pagemodel.entity.PagePanelRelation;
import com.zsgj.info.appframework.pagemodel.entity.PagePanelTable;
import com.zsgj.info.appframework.pagemodel.entity.PagePanelTableRelation;
import com.zsgj.info.appframework.pagemodel.entity.PagePanelType;
import com.zsgj.info.appframework.pagemodel.service.PageGroupPanelService;
import com.zsgj.info.appframework.pagemodel.service.PagePanelRelationService;
import com.zsgj.info.appframework.pagemodel.service.PagePanelService;
import com.zsgj.info.framework.dao.support.Page;
import com.zsgj.info.framework.exception.ApplicationException;
import com.zsgj.info.framework.security.entity.Module;
import com.zsgj.info.framework.util.BeanUtil;
import com.zsgj.info.framework.util.HttpUtil;
import com.zsgj.info.framework.web.adapter.struts.BaseDispatchAction;
import com.zsgj.info.framework.web.json.JsonUtil;

public class PageGroupPanelAction extends BaseDispatchAction{

	private SystemMainTableService smts = (SystemMainTableService) getBean("systemMainTableService");
//	private SystemMainColumnService smcs = (SystemMainColumnService) getBean("systemMainColumnService");
//	private PageModelService pms = (PageModelService) getBean("pageModelService");
	private PagePanelService pps = (PagePanelService) getBean("pagePanelService");
	private PageGroupPanelService pgps = (PageGroupPanelService) getBean("pageGroupPanelService");
	private PagePanelRelationService pprs = (PagePanelRelationService) getBean("pagePanelRelationService");
//	private PageModelPanelService pmps = (PageModelPanelService) getBean("pageModelPanelService");
	
	public ActionForward list(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		//pps.saveExtTable();
		List settingTypes = super.getService().findAll(TableSettingType.class);
		request.setAttribute("settingTypes", settingTypes);
		
		List modules = getService().findAll(Module.class);
		request.setAttribute("modules", modules);
		
		int pageNo = HttpUtil.getInt(request, "pageNo", 1);
		String moduleId = request.getParameter("module");
		String pageName = request.getParameter("pageName");
		String settingType = request.getParameter("settingType");
		
		Map<String, Object> params = new HashMap<String, Object>();
		Module module = null;
		if(StringUtils.isNotBlank(moduleId)){
			 module = (Module) getService().find(Module.class, moduleId);
			 params.put("module", module);
			 request.setAttribute("module", module);
		}
		if(StringUtils.isNotBlank(pageName)){
			 params.put("pageName", pageName);
			 request.setAttribute("pageName", pageName);
		}
		if(StringUtils.isNotBlank(settingType)){
			 params.put("settingType", settingType);
			 request.setAttribute("settingType", Integer.valueOf(settingType));
		}
		Page page = pgps.findPagePanel(params, pageNo, pageSize);
		request.setAttribute("page", page);
		
		
		return mapping.findForward("listPageGroupPanel");
	}
	
	@SuppressWarnings("unchecked")
	public ActionForward toPageGroupPanelEditForm(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		List foreignTables = getService().findAll(SystemMainTable.class);
		request.setAttribute("foreignTables", foreignTables);
	
		PagePanel pagePanel;
		List systemMainTables=getService().findAll(SystemMainTable.class);
		if(request.getParameter("id")==null){
			pagePanel=new PagePanel();
			pagePanel.setSettingType(Integer.valueOf(1));
		}else{
			String id = request.getParameter("id");
			pagePanel=pps.findPagePanelById(id);
			//新增是pagePanel没有id
			List<PagePanelTable> pagePanelTables=pps.findPagePanelTable(pagePanel);
			request.setAttribute("pagePanelTables", pagePanelTables);
			for(PagePanelTable item : pagePanelTables){
				SystemMainTable smt = item.getSystemMainTable();
				systemMainTables.remove(smt);
			}
			//获取panel之间的主表关系
			List<PagePanelTableRelation> pagePanelTableRelations=pps.findMainTableRelationByPanel(pagePanel);
			request.setAttribute("pagePanelTableRelations", pagePanelTableRelations);
		}
		
		
		//systemMainTables.removeAll(relatedSystemMainTables);
		
		request.setAttribute("pp", pagePanel);
//		Module module=pagePanel.getModule();
		
		List xtypes=super.getService().find(PagePanelType.class, "groupFlag", Integer.valueOf(1));
		request.setAttribute("xtypes", xtypes);
		
		List settingTypes = super.getService().findAll(TableSettingType.class);
		request.setAttribute("settingTypes", settingTypes);
		
		List modules = getService().findAll(Module.class);
		request.setAttribute("modules", modules);
		
		
		request.setAttribute("systemMainTables", systemMainTables);
		return mapping.findForward("pageGroupPanelEditForm");//pageGroupPanelEditForm
	}
	
	/**
	 * 提供模块获取主表, 后台页面仿照sys_table_column_detail.jsp中的选择外键字段联动功能
	 * @Methods Name findTableByModule
	 * @Create In 2008-11-24 By sa
	 * @param mapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception ActionForward
	 */
	@SuppressWarnings("unchecked")
	public ActionForward findTableByModule(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
	
		String moduleId = request.getParameter("module");
		String result = "";
		
		if(StringUtils.isNotBlank(moduleId)){
			Module module = (Module) getService().find(Module.class, moduleId);
			List<SystemMainTable> tables = pps.findTableByModule(module);
			
			List fTableMaps = new ArrayList();
			Iterator iter = tables.iterator();
			while(iter.hasNext()){
				SystemMainTable smt = (SystemMainTable) iter.next();
				Map map = new HashMap();
				map.put("id", smt.getId());
				map.put("tableCnName", smt.getTableCnName());
				fTableMaps.add(map);
			}
			JsonUtil.toJSONString(fTableMaps);
		}
		
		//System.out.println(result);
		response.setCharacterEncoding("gbk");
		response.getWriter().write("{success:true,data:"+result+"}");
		response.getWriter().flush();		
		return null;
	}
	
	/*根据选择的module来得到相应的pagePanel
	 * */
	public ActionForward findPagePanelByModule(ActionMapping mapping,ActionForm actionForm
			,HttpServletRequest request,HttpServletResponse response){
		List list = null;
		String moduleId = request.getParameter("pgId");
		Module module = (Module)super.getService().find(Module.class, moduleId);
		list = pps.findPagePanelByModule(module);
		
		String json = "";
		for(int i=0; i< list.size(); i++){
		PagePanel panel = (PagePanel)list.get(i);			
		Long id = panel.getId();
		
		String name = panel.getTitle();
		json += "{\"id\":"+id+",\"name\":\""+name+"\",\"leaf\":false},";
		}
		json = "{data:[" + json.substring(0, json.length()-1) + "]}";
		//System.out.println("创建用户时,发往前台的部门数据： "+json);	
		try {			
		response.setCharacterEncoding("utf-8");
		PrintWriter pw = response.getWriter();	
		pw.write(json);		
		} catch (IOException e) {
		e.printStackTrace();
		}
		return null;
	}
	
	
	/**
	 * 选定某个模块的主表后，调用此方法刷新表单页面，带回所有可见字段
	 * @Methods Name findColumnByTable
	 * @Create In 2008-11-24 By sa
	 * @param mapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception ActionForward
	 */
	public ActionForward findColumnByTable(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		//表单直接保存，获取表单参数
		PagePanel pagePanel = (PagePanel) BeanUtil.getObject(request, PagePanel.class);
		request.setAttribute("pp", pagePanel);
		//取出3个参数
		Integer settingType = pagePanel.getSettingType();
		SystemMainTable smt = pagePanel.getSystemMainTable();
		Module module=pagePanel.getModule();
		//选择的主表的所有可见字段
		List<Column> list = pps.findColumns(smt, settingType);
		List<PagePanelColumn> pagePanelColumns=pps.saveColumnToPanelColumn(pagePanel, list);
		request.setAttribute("pagePanelColumns", pagePanelColumns);
		
		List settingTypes = super.getService().findAll(TableSettingType.class);
		request.setAttribute("settingTypes", settingTypes);
		
		List modules = getService().findAll(Module.class);
		request.setAttribute("modules", modules);
		
		List systemMainTables=getService().find(SystemMainTable.class, "module", module);
		request.setAttribute("systemMainTables", systemMainTables);

		return mapping.findForward("pagePanelEditForm");
	}
	
	public ActionForward save(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
	
		PagePanel pagePanel = (PagePanel) BeanUtil.getObject(request, PagePanel.class);
		String panelSystemMainTableId = request.getParameter("panelSystemMainTable");
		SystemMainTable smt = null;
		if(StringUtils.isNotBlank(panelSystemMainTableId)){
			smt = (SystemMainTable) getService().find(SystemMainTable.class, panelSystemMainTableId);
		}
		pagePanel.setSystemMainTable(smt);
		pgps.savePagePanel(pagePanel);
		return HttpUtil.redirect("pageGroupPanelManage.do?methodCall=toPageGroupPanelEditForm&id="+pagePanel.getId()); 
	}
	
	
	public ActionForward remove(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		String[] pagePanelIds = request.getParameterValues("id");
		int pageNo = HttpUtil.getInt(request, "pageNo", 1);
		this.pgps.removePagePanel(pagePanelIds);
		//pprs.removePagePanelRelation(pagePanelIds); //不需要，删除操作需要在同一个业务方法完成
		return HttpUtil.redirect("pageGroupPanelManage.do?methodCall=list&pageNo="+pageNo); //+pageNo+"&level="+level
	}

	
	/**
	 * 获取可以给pagePanel提供Column的系统主表，即与Panel现有关联主表关联的主表
	 * @Methods Name mainTableList
	 * @Create In 2008-11-27 By lee
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return null
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public ActionForward mainTableList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		String pagePanelId=(String) request.getAttribute("pagePanelId");
		List<SystemMainTable> mainTables=new ArrayList();
		if(pagePanelId==null||pagePanelId.equals("")){
			mainTables=getService().findAll(SystemMainTable.class);
		}else{
			List<SystemMainTable> curMainTables=pps.findMainTableByPanel(pgps.findPagePanelById(pagePanelId));
			for(SystemMainTable curMainTable:curMainTables){
				List<SystemMainTable> relatedMainTables=getService().find(SystemMainTable.class, "systemMainTable", curMainTable);
				for(SystemMainTable relatedMainTable:relatedMainTables){
					if(!mainTables.contains(relatedMainTable)){
						mainTables.add(relatedMainTable);
					}
				}
			}
		}
		String json = "";
		for(SystemMainTable mainTable:mainTables){		
			Long did = mainTable.getId();
			String name = mainTable.getTableCnName();
			json += "{\"relatedMainTableId\":"+did+",\"relatedMainTableName\":\""+name+"\"},";
		}
		json = "{data:[" + json.substring(0, json.length()-1) + "]}";
		//System.out.println("可选主表数据： "+json);
		try {			
			response.setCharacterEncoding("gbk");
			PrintWriter pw = response.getWriter();	
			pw.write(json);		
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 选定某个模块的主表后，调用此方法刷新表单页面，带回所有可见字段
	 * @Methods Name findColumnByTable
	 * @Create In 2008-11-24 By sa
	 * @param mapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception ActionForward
	 */
	public ActionForward getColumnByTable(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String smtId=(String) request.getParameter("cursmtId");
		String smtName=smts.findSystemMainTable(smtId).getTableCnName();
		int settingType=1;
		List<Column> columns=pps.findColumns(smts.findSystemMainTable(smtId),settingType);
		String json = "";
		for(Column column:columns){
			Long stcId = column.getId();
			String stcName = column.getColumnCnName();
		//	if(column instanceof SystemMainTableColumn)
			json += "{\"stcId\":"+stcId+",\"stcName\":\""+stcName+"\",\"smtId\":\""+smtId+"\",\"smtName\":\""+smtName+"\",\"isMainColumn\":\""+"true"+"\"},";
		//	if(column instanceof SystemMainTableExtColumn)
		//		json += "{\"stcId\":"+stcId+",\"stcName\":\""+stcName+"\",\"smtId\":\""+smtId+"\",\"smtName\":\""+smtName+"\",\"isMainColumn\":\""+"false"+"\"},";
		}
		json = "{data:[" + json.substring(0, json.length()-1) + "]}";
		//System.out.println("可选主表数据： "+json);
		try {			
			response.setCharacterEncoding("gbk");
			PrintWriter pw = response.getWriter();	
			pw.write(json);		
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 获取pagePanel树形结果元数据
	 * @Methods Name loadPagePanel
	 * @Create In 2008-11-28 By lee
	 * @param mapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public ActionForward loadPagePanel(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		String parentId = request.getParameter("id");
		String ppId = request.getParameter("ppId"); 
		
		if("-100".equals(parentId) && !"".equals(ppId)){
			
			PagePanel currentPagePanel = (PagePanel)super.getService().find(PagePanel.class,ppId );
			List<PagePanelRelation> list = pprs.findPagePanelRelationByPageAddOrder(currentPagePanel);
			//List<PageModelPanelRelation> list = super.getService().find(PagePanelRelation.class, "parentPagePanel", currentPagePanel);
			
			request.setAttribute("list",list);	
		}else if("".equals(ppId)){
			request.setAttribute("list", null);
		}
		return mapping.findForward("pageGroupPanelShow");		
	}
	
	public ActionForward saveColumnsFormSystemMainTable(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String smtId = request.getParameter("systemMainTable");
		String ppId = request.getParameter("ppId");
		PagePanel pagePanel = pps.findPagePanelById(ppId);
		//保存新的表到panelTable之前做判断，是否有外键关联
		SystemMainTable smt = null;
		if(StringUtils.isNotBlank(smtId)){
			smt = (SystemMainTable) getService().find(SystemMainTable.class, smtId,true);
		}
		if(smt.getId()!=null){
		List lists = pps.findMainTableByPanel(pagePanel);
//			for(int i=0; i < lists.size(); i++){
//				if(lists.get(i).equals(smt)==true){
//					throw new ApplicationException("主表已存在");
//				}
//			}
		List<Column> list = pps.findColumns(smt, pagePanel.getSettingType());	
		int count = 0;
		int sum=0;
		for(int i=0; i < list.size(); i++){
			Object o = list.get(i);
			for(int j = 0 ; j<lists.size(); j++){
				Object o2 = lists.get(j);
				List l3 = pps.findColumns((SystemMainTable)o2);
				for(int k = 0 ; k< (l3.size()); k++){
					Object ok = l3.get(k);
					if(((SystemMainTableColumn) o).getForeignTable() == null && ((SystemMainTableColumn) ok).getForeignTable()==null){
						count++;
					}else if(((SystemMainTableColumn) o).getForeignTable() != o2 && ((SystemMainTableColumn) ok).getForeignTable()!= smt){
						count++;
					}
					sum++;
				}
			}
		}
		if(count == sum){
			throw new ApplicationException("没有关联关系");
		}
		}
		
		pps.savePanelColumnsFormSysMainTable(ppId,smtId);
		//ppts.save(ppId, smtId);
		return HttpUtil.redirect("pagePanelGroupManage.do?methodCall=toPageGroupPanelEditForm&id="+ppId);
	}	
	/**
	 * 根据条件来进行相应的查找相应的panel类型
	 * @Methods Name findPanelTypeByEntity
	 * @Create In Dec 24, 2008 By Administrator
	 * @param mapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @return ActionForward
	 */
	public ActionForward findPanelTypeByEntity(ActionMapping mapping ,ActionForm actionForm,
			HttpServletRequest request,HttpServletResponse response){
		
		String panelRelationId = request.getParameter("id");
		// request.getParameter("typeName");
		List type = pps.searchPagePanelByPanelName(panelRelationId);		
		//List type = super.getService().findAll(PagePanelType.class);			
		String json = "";
		for(int i=0; i< type.size(); i++){
		PagePanelType pageType = (PagePanelType)type.get(i);			
		Long id = pageType.getId();
		String name = pageType.getCnName();
		json += "{\"typeId\":"+id+",\"name\":\""+name+"\"},";
		}
		json = "{data:[" + json.substring(0, json.length()-1) + "]}";
		//System.out.println("创建用户时,发往前台的module数据： "+json);	
		try {			
		response.setCharacterEncoding("utf-8");
		PrintWriter pw = response.getWriter();	
		pw.write(json);		
		} catch (IOException e) {
		e.printStackTrace();
		}
		return null;
	}
	/**
	 * 
	 * @Methods Name findSystemMainTable
	 * @Create In Dec 18, 2008 By Administrator
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 */
	public ActionForward gainDefaultValue(ActionMapping mapping ,ActionForm form,
			HttpServletRequest request,HttpServletResponse response){
		
		String PagePanelRelationId = request.getParameter("pnode"); 
		PagePanelRelation ppr = pprs.findPagePanelRelationById(PagePanelRelationId);
		int display = ppr.getIsDisplay();
		int onlyRead = ppr.getReadonly();
		int title = ppr.getTitleDisplayFlag();
		PagePanel pagePanel = ppr.getPagePanel();
		
		PagePanelType ppt = (PagePanelType)pagePanel.getXtype();
//		Long pid = pagePanel.getXtype().getId();
//		Long id = ppt.getId();
		String typeName = ppt.getCnName();
		
//		Long panelId = pagePanel.getId();
		String json = "";
		json += "{\"display\":"+display+",\"onlyRead\":"+onlyRead+",\"title\":"+title+",\"typeName\":\""+typeName+"\"}";
		//System.out.println("json:"+json);
		try {
			response.setCharacterEncoding("utf-8");
			PrintWriter pw = response.getWriter();
			pw = response.getWriter();
			pw.write(json);		
		} catch (IOException e) {
			
			e.printStackTrace();
		}		
		return null;
	}
	/**
	 * 
	 * @Methods Name idDisplayByPagePanel
	 * @Create In Dec 25, 2008 By Administrator
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 */
	@SuppressWarnings("unchecked")
	public ActionForward idDisplayByPagePanel(ActionMapping mapping ,ActionForm form,
			HttpServletRequest request,HttpServletResponse response){		
		List isDisplay = new ArrayList();
		isDisplay.add(0, "否");	
		isDisplay.add(1, "是");					
		String json = "";
		for(int i=0; i< isDisplay.size(); i++){
			int id = i;
			String name = (String)isDisplay.get(i);			
			json += "{\"display\":"+id+",\"name\":\""+name+"\"},";
		}
		json = "{data:[" + json.substring(0, json.length()-1) + "]}";
		//System.out.println("创建用户时,发往前台的module数据： "+json);	
		try {			
		response.setCharacterEncoding("utf-8");
		PrintWriter pw = response.getWriter();	
		pw.write(json);		
		} catch (IOException e) {
		e.printStackTrace();
		}
		return null;
	}
	/**
	 * 
	 * @Methods Name isTitleDisplayByPagePanel
	 * @Create In Dec 25, 2008 By Administrator
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 */
	@SuppressWarnings("unchecked")
	public ActionForward isTitleDisplayByPagePanel(ActionMapping mapping ,ActionForm form,
			HttpServletRequest request,HttpServletResponse response){
		List isDisplay = new ArrayList();
		isDisplay.add(0, "否");	
		isDisplay.add(1, "是");					
		String json = "";
		for(int i=0; i< isDisplay.size(); i++){
			int id = i;
			String name = (String)isDisplay.get(i);			
			json += "{\"titleDisplay\":"+id+",\"name\":\""+name+"\"},";
		}
		json = "{data:[" + json.substring(0, json.length()-1) + "]}";
		//System.out.println("创建用户时,发往前台的module数据： "+json);	
		try {			
		response.setCharacterEncoding("utf-8");
		PrintWriter pw = response.getWriter();	
		pw.write(json);		
		} catch (IOException e) {
		e.printStackTrace();
		}
		return null;
	}
	/**
	 * 
	 * @Methods Name idreadonlyByPagePanel
	 * @Create In Dec 25, 2008 By Administrator
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 */
	@SuppressWarnings("unchecked")
	public ActionForward idreadonlyByPagePanel(ActionMapping mapping ,ActionForm form,
			HttpServletRequest request,HttpServletResponse response){
		List isDisplay = new ArrayList();
		isDisplay.add(0, "否");	
		isDisplay.add(1, "是");					
		String json = "";
		for(int i=0; i< isDisplay.size(); i++){
			int id = i;
			String name = (String)isDisplay.get(i);			
			json += "{\"readonly\":"+id+",\"name\":\""+name+"\"},";
		}
		json = "{data:[" + json.substring(0, json.length()-1) + "]}";
		//System.out.println("创建用户时,发往前台的module数据： "+json);	
		try {			
		response.setCharacterEncoding("utf-8");
		PrintWriter pw = response.getWriter();	
		pw.write(json);		
		} catch (IOException e) {
		e.printStackTrace();
		}
		return null;
	}
	/**
	 * 
	 * @Methods Name modifyAndSavePanelMessage
	 * @Create In Dec 25, 2008 By Administrator
	 * @param mapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException ActionForward
	 */
	public ActionForward modifyAndSavePanelMessage(ActionMapping mapping ,ActionForm actionForm,
			HttpServletRequest request,HttpServletResponse response) throws IOException{
		//System.out.println(request.getCharacterEncoding());
		//System.out.println(HttpUtil.GBKtoUTF8(request.getParameter("readonly"))); 
		//PagePanelType xtypeEntity = (PagePanelType)super.getService().findUnique(PagePanelType.class, "cnName", HttpUtil.GBKtoUTF8(request.getParameter("type")));
//		String json = "{success:true}";		
		String pagePanelRelationId = request.getParameter("panelId");		
		String modifyDis =request.getParameter("display");
		String modifyTypeId = request.getParameter("typeId");//xtypeEntity.getId()+"";		
		String title = request.getParameter("titleDisplay");
		String readonly = request.getParameter("readonly");
		
		PagePanelRelation ppr = pprs.findPagePanelRelationById(pagePanelRelationId);
		PagePanelType ppt = (PagePanelType)super.getService().find(PagePanelType.class, modifyTypeId);
		ppr.getPagePanel().setXtype(ppt);
		ppr.setIsDisplay(Integer.parseInt(modifyDis));
		ppr.setReadonly(Integer.parseInt(readonly));
		ppr.setTitleDisplayFlag(Integer.parseInt(title));
		
		
		super.getService().save(ppr);
		PrintWriter pw = response.getWriter();
		pw.write("{success:true}");
		return null;
			
	}
	/**
	 * 改进comboBOx的bug
	 * @Methods Name findPanelFormValue
	 * @Create In Dec 28, 2008 By Administrator
	 * @param mapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @return ActionForward
	 */
	public ActionForward findPanelFormValue(ActionMapping mapping ,ActionForm actionForm,
			HttpServletRequest request,HttpServletResponse response){
		String pagePanelRelationId = request.getParameter("id");
		PagePanelRelation panelRelationEntity = pprs.findPagePanelRelationById(pagePanelRelationId);
		List list = pprs.findPartPagePanelRelationByRelationObject(panelRelationEntity);
		
		JSONArray jsonObject = JSONArray.fromObject(list);
		//System.out.println(jsonObject.toString());
		//PrintWriter pw;
		try {
			//pw = response.getWriter();
//			System.out.println(response.getCharacterEncoding());
			//pw.write("{success:" + true + ",list:"+ jsonObject.toString() + "}");
			response.setCharacterEncoding("gbk");
			response.getWriter().write("{success:" + true + ",list:"+ jsonObject.toString() + "}");
			response.getWriter().flush();
						
		} catch (IOException e) {
			
			e.printStackTrace();
		}		
		return null;
	}
	public ActionForward toPageGroupList (ActionMapping mapping ,ActionForm form,
			HttpServletRequest request ,HttpServletResponse response ) throws UnsupportedEncodingException{
		String ppId = request.getParameter("ppId");
		PagePanel pagePanel = pgps.findPagePanelById(ppId);
	//	List<PageGroupPanelTable> lists =super.getService().findAll(PageGroupPanelTable.class);//, "pagePanel", pagePanel);
		List<PageGroupPanelTable> lists =  pgps.findAllPageGroupPanelTableByGroupPanel(pagePanel);
			//pps.findPageGroupPanelTableByPanel(pagepanel);
		String json = "[";
		for(int i=0; i < lists.size(); i++){
			json+="{";
			json=json+"\"id\":"+lists.get(i).getId()+",";
			if(lists.get(i).getSubPagePanel()==null){
				json=json+"\"subPagePanel\":\""+""+"\",";
			}else{
				json=json+"\"subPagePanel\":\""+lists.get(i).getSubPagePanel().getTitle()+"\",";
			}
			if(lists.get(i).getSubPanelTable()==null){
				json=json+"\"subPanelTable\":\""+""+"\",";
			}else{
				json=json+"\"subPanelTable\":\""+lists.get(i).getSubPanelTable().getTableCnName()+"\",";
			}
			if(lists.get(i).getSubPanelTableFColumn()==null){
				json=json+"\"subPanelTableFColumn\":\""+""+"\",";
			}else{
				json=json+"\"subPanelTableFColumn\":\""+lists.get(i).getSubPanelTableFColumn().getColumnCnName()+"\",";
			}
			if(lists.get(i).getParentPagePanel()==null){
				json=json+"\"parentPagePanel\":\""+""+"\",";
			}else{
				json=json+"\"parentPagePanel\":\""+lists.get(i).getParentPagePanel().getTitle()+"\",";
			}
			if(lists.get(i).getParentPanelTable()==null){
				json=json+"\"parentPanelTable\":\""+""+"\",";
			}else{
				json=json+"\"parentPanelTable\":\""+lists.get(i).getParentPanelTable().getTableCnName()+"\",";
			}
			if(lists.get(i).getParentPanelTablePColumn()==null){
				json=json+"\"parentPanelTablePColumn\":\""+""+"\"";
			}else{
				json=json+"\"parentPanelTablePColumn\":\""+lists.get(i).getParentPanelTablePColumn().getColumnCnName()+"\"";
			}
			json+="},";
		}
		json = json.substring(0, json.length()-1);
		json+="]";
		try{
			response.setCharacterEncoding("gbk");
			PrintWriter pw = response.getWriter();
			pw.write("{success:true,rowCount:10,data:"+ json + "}");
		}catch(Exception e){
			e.printStackTrace();
			
		}
				return null;
		
	}
	public ActionForward toPageGroupPanelTableRemove(ActionMapping mapping ,ActionForm form,
			HttpServletRequest request ,HttpServletResponse response ) throws UnsupportedEncodingException{
				String ids = request.getParameter("ids");
				String[] rid=ids.split(","); 
				for(String tableID:rid){
					pgps.removePageGroupPanelTable(tableID);
				}
				String js = "{success:'kkkkkk'}";
				response.setCharacterEncoding("gbk");
				try {
					response.getWriter().write(js);
					response.getWriter().flush();
				} catch (IOException e) {
					
					e.printStackTrace();
				}
				return null;
		
	}
	public ActionForward findAllPagePanel (ActionMapping mapping ,ActionForm form,
			HttpServletRequest request ,HttpServletResponse response ) throws UnsupportedEncodingException{
			List list = pgps.findAllPagePanel();
			PagePanel pagePanel=null;
			String json = "";
			for(int i=0; i < list.size(); i++){
				pagePanel = (PagePanel) list.get(i);
				Long code = pagePanel.getId();
				String name = pagePanel.getTitle();
				if(name==null){
					name = "";
				}
				json += "{name:\""+name+"\",code:\""+code+"\"},";	
			}
			json = "data:[" + json.substring(0, json.length()-1) + "]";
			try {
			response.setCharacterEncoding("gbk");
			PrintWriter pw = response.getWriter();
			json = "{success:true,rowCount:50,"+ json + "}";
			pw.write(json);		
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		
	}
	public ActionForward findAllMainTable (ActionMapping mapping ,ActionForm form,
			HttpServletRequest request ,HttpServletResponse response ) throws UnsupportedEncodingException{
		request.setCharacterEncoding("GBK");
		String value = HttpUtil.ConverUnicode(request.getParameter("value"));
		String ppId = request.getParameter("id");
		if((value==null||!Pattern.matches("^\\+?[0-9][0-9]*$", value))&&ppId==null){
			return null;
		}
		PageGroupPanelTable pgpt = null;
		if(ppId!=null&&ppId.trim().length()!=0){
			pgpt = pgps.findPageGroupPanelTable(ppId);
		}
		if(!Pattern.matches("^\\+?[0-9][0-9]*$", value)){
			if(pgpt.getSubPagePanel()==null){
				value="";
				return null;
			}else{
				value = String.valueOf(pgpt.getSubPagePanel().getId());
			}
		}
		PagePanel pagePanel = pgps.findPagePanelById(value);
		List lists = pgps.findMainTableByPanel(pagePanel);
		String json = "";
		for(int i=0; i< lists.size(); i++){
			SystemMainTable smt	=(SystemMainTable) lists.get(i);
			
				Long code = smt.getId();
				String name = smt.getTableCnName();
				json += "{name:\""+name+"\",code:\""+code+"\"},";
				
		}
		json = "data:[" + json.substring(0, json.length()-1) + "]";
		
		try {
			response.setCharacterEncoding("GBK");
			PrintWriter pw = response.getWriter();
			json = "{success:true,rowCount:50,"+ json + "}";
			pw.write(json);		
			} catch (Exception e) {
				e.printStackTrace();
			}
		//System.out.println(json);
		
		return null;
		
	}
	public ActionForward findAllForeignCol (ActionMapping mapping ,ActionForm form,
			HttpServletRequest request ,HttpServletResponse response ) throws UnsupportedEncodingException{
		request.setCharacterEncoding("GBK");
		String value = HttpUtil.ConverUnicode(request.getParameter("subPanelTableValue"));
		String ppId = request.getParameter("id");
		if((value==null||!Pattern.matches("^\\+?[0-9][0-9]*$", value))&&ppId==null){
			return null;
		}
		PageGroupPanelTable pgpt = null;
		if(ppId!=null&&ppId.trim().length()!=0){
			pgpt = pgps.findPageGroupPanelTable(ppId);
		}
		if(!Pattern.matches("^\\+?[0-9][0-9]*$", value)){
			if(pgpt.getSubPanelTable()==null){
				value = "";
				return null;
			}else{
				value = String.valueOf(pgpt.getSubPanelTable().getId());
			}	
		}
		SystemMainTable systemMainTable = pgps.findSystemMainTable(value);
		List lists = pgps.findAllSystemMainTableColumnByName(systemMainTable.getTableName());
		String json = "";
		SystemMainTableColumn systemMainTableColumn = new SystemMainTableColumn();
		for(int i=0; i< lists.size(); i++){
			systemMainTableColumn=(SystemMainTableColumn) lists.get(i);
			//注释了判断是否外键字段判断
				Long code = systemMainTableColumn.getId();
				String name = systemMainTableColumn.getColumnCnName();
				json += "{name:\""+name+"\",code:\""+code+"\"},";
					
		}
		json = "data:[" + json.substring(0, json.length()-1) + "]";
		
		try {
			response.setCharacterEncoding("GBK");
			PrintWriter pw = response.getWriter();
			json = "{success:true,rowCount:50,"+ json + "}";
			pw.write(json);		
			} catch (Exception e) {
				e.printStackTrace();
			}
		//System.out.println(json);
		
		return null;
		
	}
	public ActionForward findAllMainTable2 (ActionMapping mapping ,ActionForm form,
			HttpServletRequest request ,HttpServletResponse response ) throws UnsupportedEncodingException{
		request.setCharacterEncoding("GBK");
		String value = HttpUtil.ConverUnicode(request.getParameter("value"));
		String ppId = request.getParameter("id");
		if((value==null||!Pattern.matches("^\\+?[0-9][0-9]*$", value))&&ppId==null){
			return null;
		}
		PageGroupPanelTable pgpt = null;
		if(ppId!=null&&ppId.trim().length()!=0){
			pgpt = pgps.findPageGroupPanelTable(ppId);
		}
		if(!Pattern.matches("^\\+?[0-9][0-9]*$", value)){
			if(pgpt.getParentPagePanel()==null){
				value="";
				return null;
			}else{
				value = String.valueOf(pgpt.getParentPagePanel().getId());
			}
		}
		PagePanel pagePanel = pgps.findPagePanelById(value);
		List lists = pgps.findMainTableByPanel(pagePanel);
		String json = "";
		for(int i=0; i< lists.size(); i++){
			SystemMainTable smt	=(SystemMainTable) lists.get(i);
			
				Long code = smt.getId();
				String name = smt.getTableCnName();
				json += "{name:\""+name+"\",code:\""+code+"\"},";
				
		}
		json = "data:[" + json.substring(0, json.length()-1) + "]";
		
		try {
			response.setCharacterEncoding("GBK");
			PrintWriter pw = response.getWriter();
			json = "{success:true,rowCount:50,"+ json + "}";
			pw.write(json);		
			} catch (Exception e) {
				e.printStackTrace();
			}
		//System.out.println(json);
		
		return null;
		
	}
	public ActionForward findPrimaryKeyColumn (ActionMapping mapping ,ActionForm form,
			HttpServletRequest request ,HttpServletResponse response ) throws UnsupportedEncodingException{
		request.setCharacterEncoding("GBK");
		String value = HttpUtil.ConverUnicode(request.getParameter("parentPanelTable"));
		String ppId = request.getParameter("id");
		if((value==null||!Pattern.matches("^\\+?[0-9][0-9]*$", value))&&ppId==null){
			return null;
		}
		PageGroupPanelTable pgpt = null;
		if(ppId!=null&&ppId.trim().length()!=0){
			pgpt = pgps.findPageGroupPanelTable(ppId);
		}
		if(!Pattern.matches("^\\+?[0-9][0-9]*$", value)){
			if(pgpt.getParentPanelTable()==null){
				value="";
				return null;
			}else{
				value = String.valueOf(pgpt.getParentPanelTable().getId());
			}
		}
		//SystemMainTableColumn systemMainTableColumn = pms.findSystemMainTablePrimaryKeyColumn(value);
		SystemMainTable systemMainTable = pgps.findSystemMainTable(value);
		SystemMainTableColumn systemMainTableColumn =systemMainTable.getPrimaryKeyColumn();
		String json = "";
				Long code = systemMainTableColumn.getId();
				String name = systemMainTableColumn.getColumnCnName();
				json += "{name:\""+name+"\",code:\""+code+"\"},";	
		json = "data:[" + json.substring(0, json.length()-1) + "]";
		
		try {
			response.setCharacterEncoding("GBK");
			PrintWriter pw = response.getWriter();
			json = "{success:true,rowCount:50,"+ json + "}";
			pw.write(json);		
			} catch (Exception e) {
				e.printStackTrace();
			}
		System.out.println(json);
		
		return null;
		
	}
	public ActionForward findPrimaryKeyColumnID (ActionMapping mapping ,ActionForm form,
			HttpServletRequest request ,HttpServletResponse response ) throws UnsupportedEncodingException{
		request.setCharacterEncoding("GBK");
		String value = HttpUtil.ConverUnicode(request.getParameter("parentPanelTable"));
		if(value==null||!Pattern.matches("^\\+?[0-9][0-9]*$", value)){
			return null;
		}
		//SystemMainTableColumn systemMainTableColumn = pms.findSystemMainTablePrimaryKeyColumn(value);
		SystemMainTable systemMainTable = pgps.findSystemMainTable(value);
		SystemMainTableColumn systemMainTableColumn =systemMainTable.getPrimaryKeyColumn();
		
				Long code = systemMainTableColumn.getId();
				String code1=String.valueOf(code);
				
		
		try {
			response.setCharacterEncoding("GBK");
			PrintWriter pw = response.getWriter();
			Thread.sleep(100);
			pw.write(code1);	
			} catch (Exception e) {
				e.printStackTrace();
			}
		
		return null;
		
	}
	@SuppressWarnings("unchecked")
	public ActionForward toPageGroupTableSave (ActionMapping mapping ,ActionForm form,
			HttpServletRequest request ,HttpServletResponse response ) throws UnsupportedEncodingException{
				String ppId = HttpUtil.ConverUnicode(request.getParameter("ppId"));
				String price = HttpUtil.ConverUnicode(request.getParameter("price"));
				PageGroupPanelTable pageGroupPanelTable = null;
				HashMap priceMap = new HashMap();
				if (price != null && price.endsWith(",")) {
					price = price.substring(0, price.length() - 1);
				}
				JSONArray ja = JSONArray.fromObject("[" + price + "]");
				for (int i = 0; i < ja.size(); i++) {
					JSONObject opl = (JSONObject) ja.get(i);
					Iterator itPrice = opl.keys();
					while (itPrice.hasNext()) {
						String key = (String) itPrice.next();
						String value = opl.getString(key);
						if(key.equals("id")&& value!= "null"){
							pageGroupPanelTable = pgps.findPageGroupPanelTable(value);
						}
						if(key.equals("id")&&value=="null"){
							pageGroupPanelTable = null;
						}
						if(key.equals("subPagePanel")&&!Pattern.matches("^\\+?[0-9][0-9]*$", value)&&!value.equals("")&&value!="null"){
							value = String.valueOf(pageGroupPanelTable.getSubPagePanel().getId());
						}
						if(key.equals("subPagePanel")&&(value.equals("")||value=="null")){
							value = "";
						}
						if(key.equals("subPanelTable")&&!Pattern.matches("^\\+?[0-9][0-9]*$", value)&&!value.equals("")&&value!="null"){
							value = String.valueOf(pageGroupPanelTable.getSubPanelTable().getId());
						}
						if(key.equals("subPanelTable")&&(value.equals("")||value=="null")){
							value = "";
						}
						if(key.equals("subPanelTableFColumn")&&!Pattern.matches("^\\+?[0-9][0-9]*$", value)&&!value.equals("")&&value!="null"){
							value = String.valueOf(pageGroupPanelTable.getSubPanelTableFColumn().getId());
						}
						if(key.equals("subPanelTableFColumn")&&(value.equals("")||value=="null")){
							value = "";
						}
						if(key.equals("parentPagePanel")&&!Pattern.matches("^\\+?[0-9][0-9]*$", value)&&!value.equals("")&&value!="null"){
							value = String.valueOf(pageGroupPanelTable.getParentPagePanel().getId());
						}
						if(key.equals("parentPagePanel")&&(value.equals("")||value=="null")){
							value = "";
						}
						if(key.equals("parentPanelTable")&&!Pattern.matches("^\\+?[0-9][0-9]*$", value)&&!value.equals("")&&value!="null"){
							value = String.valueOf(pageGroupPanelTable.getParentPanelTable().getId());
						}
						if(key.equals("parentPanelTable")&&(value.equals("")||value=="null")){
							value = "";
						}
						if(key.equals("parentPanelTablePColumn")&&!Pattern.matches("^\\+?[0-9][0-9]*$", value)&&!value.equals("")&&value!="null"){
							value = String.valueOf(pageGroupPanelTable.getParentPanelTablePColumn().getId());
						}
						if(key.equals("parentPanelTablePColumn")&&(value.equals("")||value=="null")){
							value = "";
						}
						priceMap.put(key, value);
					}
				}
				priceMap.put("pagePanel",ppId);
				pageGroupPanelTable = (PageGroupPanelTable) BeanUtil.getObject(priceMap,PageGroupPanelTable.class);
				pgps.savePageGroupPanelTable(pageGroupPanelTable);
				return null;
		
	}
}
