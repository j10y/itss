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

import com.zsgj.info.appframework.metadata.MetaDataManager;
import com.zsgj.info.appframework.metadata.entity.Column;
import com.zsgj.info.appframework.metadata.entity.SystemMainTable;
import com.zsgj.info.appframework.metadata.entity.SystemMainTableColumn;
import com.zsgj.info.appframework.metadata.entity.TableSettingType;
import com.zsgj.info.appframework.metadata.service.SystemColumnService;
import com.zsgj.info.appframework.metadata.service.SystemMainColumnService;
import com.zsgj.info.appframework.metadata.service.SystemMainTableService;
import com.zsgj.info.appframework.pagemodel.entity.PagePanel;
import com.zsgj.info.appframework.pagemodel.entity.PagePanelBtn;
import com.zsgj.info.appframework.pagemodel.entity.PagePanelColumn;
import com.zsgj.info.appframework.pagemodel.entity.PagePanelFieldSet;
import com.zsgj.info.appframework.pagemodel.entity.PagePanelTable;
import com.zsgj.info.appframework.pagemodel.entity.PagePanelTableRelation;
import com.zsgj.info.appframework.pagemodel.entity.PagePanelType;
import com.zsgj.info.appframework.pagemodel.service.PagePanelBtnService;
import com.zsgj.info.appframework.pagemodel.service.PagePanelColumnService;
import com.zsgj.info.appframework.pagemodel.service.PagePanelService;
import com.zsgj.info.appframework.pagemodel.service.PagePanelTableRelationService;
import com.zsgj.info.appframework.pagemodel.service.PagePanelTableService;
import com.zsgj.info.appframework.pagemodel.servlet.CoderForButton;
import com.zsgj.info.framework.dao.support.Page;
import com.zsgj.info.framework.exception.ApplicationException;
import com.zsgj.info.framework.security.entity.Module;
import com.zsgj.info.framework.util.BeanUtil;
import com.zsgj.info.framework.util.HttpUtil;
import com.zsgj.info.framework.web.adapter.struts.BaseDispatchAction;
import com.zsgj.info.framework.web.json.JsonUtil;

public class PagePanelAction extends BaseDispatchAction{
	private MetaDataManager metaDataManager=(MetaDataManager)getBean("metaDataManager");
	private SystemColumnService scs = (SystemColumnService) getBean("systemColumnService");
	private SystemMainTableService smts = (SystemMainTableService) getBean("systemMainTableService");
	private SystemMainColumnService smcs = (SystemMainColumnService) getBean("systemMainColumnService");
//	private SecurityManageService sms = (SecurityManageService) getBean("securityManageService");
//	private PageModelService pms = (PageModelService) getBean("pageModelService");
	private PagePanelService pps = (PagePanelService) getBean("pagePanelService");
	private PagePanelColumnService ppcs=(PagePanelColumnService) getBean("pagePanelColumnService");
	private PagePanelTableService ppts=(PagePanelTableService) getBean("pagePanelTableService");
	private PagePanelBtnService ppbs=(PagePanelBtnService) getBean("pagePanelBtnService");
	private PagePanelTableRelationService pptrs=(PagePanelTableRelationService) getBean("pagePanelTableRelationService");
	
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
		Page page = pps.findPagePanel(params, pageNo, pageSize);
		request.setAttribute("page", page);
		
		
		return mapping.findForward("listPagePanel");
	}
	
	@SuppressWarnings("unchecked")
	public ActionForward toPagePanelEditForm(ActionMapping mapping,
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
		
		List xtypes=super.getService().find(PagePanelType.class, "groupFlag", Integer.valueOf(0));
		request.setAttribute("xtypes", xtypes);
		
		List settingTypes = super.getService().findAll(TableSettingType.class);
		request.setAttribute("settingTypes", settingTypes);
		
		List modules = getService().findAll(Module.class);
		request.setAttribute("modules", modules);
		
		
		request.setAttribute("systemMainTables", systemMainTables);
		return mapping.findForward("pagePanelEditForm");
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
		int pageSize = 15;
		String paramName = request.getParameter("start");		
		String moduleId = request.getParameter("pgId");
		if(!moduleId.equals("null")){
			Module module = (Module)super.getService().find(Module.class, moduleId);
			int pageNo = this.confirmPageNo(paramName, 1);
			Page page = pps.findPanelByPageModule(module,pageNo, pageSize);
			list = page.list();
			Long rowCount = page.getTotalCount();
			
			String json = "";
			for(int i=0; i< list.size(); i++){
			PagePanel panel = (PagePanel)list.get(i);			
			Long id = panel.getId();
			String flag = panel.getGroupFlag()+"";
			if(flag.equals("1")){
				flag = "是";
			}else{
				flag = "否";
			}
			String name = panel.getTitle();
			json += "{\"id\":"+id+",\"name\":\""+name+"\",\"flag\":\""+flag+"\"},";		
			}
			json = "{success:true,rowCount:"+rowCount+",data:[" + json.substring(0, json.length()-1) + "]}";
			System.out.println("创建用户时,发往前台的部门数据： "+json);	
			try {			
			response.setCharacterEncoding("utf-8");
			PrintWriter pw = response.getWriter();	
			pw.write(json);		
			} catch (IOException e) {
			e.printStackTrace();
			}
		}else{
			String json = "";
			try {			
				response.setCharacterEncoding("utf-8");
				PrintWriter pw = response.getWriter();	
				pw.write(json);		
				} catch (IOException e) {
				e.printStackTrace();
				}
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
		PagePanel pp=null;
		if(pagePanel.getId()!=null){
			if(smt!=pps.findPagePanelById(pagePanel.getId().toString()).getSystemMainTable()){
				pagePanel.setSystemMainTable(smt);
				ppts.removeAll(pagePanel);
				pp=pps.savePagePanel(pagePanel);
				pps.savePanelColumnsFormSysMainTable(pp.getId().toString(),panelSystemMainTableId);
				ppts.save(pagePanel, smt);
			}else{
				pp=pagePanel;
				pagePanel.setSystemMainTable(smt);
				pp=pps.savePagePanel(pagePanel);
			}
		}else{
			pagePanel.setSystemMainTable(smt);
			pp=pps.savePagePanel(pagePanel);
			pps.savePanelColumnsFormSysMainTable(pp.getId().toString(),panelSystemMainTableId);
			ppts.save(pp, smt);
		}
		if(ppbs.findPanelBtnByPanel(pp).isEmpty()){
		ppbs.initPagePanelBtn(pp);
		}
		return HttpUtil.redirect("pagePanelManage.do?methodCall=toPagePanelEditForm&id="+pagePanel.getId()); 
	}
	
	
	public ActionForward remove(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		String[] pagePanelIds = request.getParameterValues("id");
		
		/*if(pagePanelIds!=null&& pagePanelIds.length>0){
			throw new ServiceException("d");
		}*/
		
		int pageNo = HttpUtil.getInt(request, "pageNo", 1);
		this.pps.removePagePanel(pagePanelIds);
		
		return HttpUtil.redirect("pagePanelManage.do?methodCall=list&pageNo="+pageNo); //+pageNo+"&level="+level
	}
	public ActionForward removeSystemMainTable(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		String[] smtIds = request.getParameterValues("id");
		String ppId=request.getParameter("ppId");
		for(String smtId:smtIds){
			ppts.remove(ppId, smtId);
			ppcs.removePanelColumn(ppId, smtId);
		}
//		int pageNo = HttpUtil.getInt(request, "pageNo", 1);
		return HttpUtil.redirect("pagePanelManage.do?methodCall=toPagePanelEditForm&id="+ppId);
	}
	
	public ActionForward removePagePanelTable(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		String[] smtIds = request.getParameterValues("id");
		
		String ppId=request.getParameter("ppId");
		PagePanel pagePanel=pps.findPagePanelById(ppId);
		SystemMainTable pagePanelsmt=pagePanel.getSystemMainTable();
		for(int i=0;i<smtIds.length;i++){
			if(smts.findSystemMainTable(smtIds[i])==pagePanelsmt)
				throw new ApplicationException("不能删除面板主表");
		}
		for(int i=0;i<smtIds.length;i++){
			PagePanelTable ppt=ppts.findPagePanelTableById(smtIds[i]);
			pptrs.remove(pagePanel, ppt.getSystemMainTable());
		}
		ppts.removePagePanelTable(smtIds);
//		int pageNo = HttpUtil.getInt(request, "pageNo", 1);
		return HttpUtil.redirect("pagePanelManage.do?methodCall=toPagePanelEditForm&id="+ppId);
	}
	
	
	public ActionForward saveColumns(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		//获取pagePanenlId
		String pagePanelId = request.getParameter("ppId");
		PagePanel pp = (PagePanel) getService().find(PagePanel.class, pagePanelId);
		request.setAttribute("pp", pp);
		
		List pagePanelColumns = getService().find(PagePanelColumn.class, "pagePanel", pp);
		for(int i=0; i<pagePanelColumns.size(); i++){
			PagePanelColumn ppc = (PagePanelColumn) pagePanelColumns.get(i);
			Long ppcId = ppc.getId();
			String isDisplayPara = request.getParameter("isDisplay"+ppcId);
			String lengthForPage = request.getParameter("lengthForPage"+ppcId);
			String isMustInput = request.getParameter("isMustInput"+ppcId);
//			String hiddenValue = request.getParameter("hiddenValue"+ppcId);

			if(isDisplayPara==null){
				isDisplayPara="0";
			}
			ppc.setIsDisplay(Integer.valueOf(isDisplayPara));
			ppc.setLength(lengthForPage);
			if(StringUtils.isNotBlank(isMustInput)){
				ppc.setIsMustInput(Integer.valueOf(isMustInput));
			}
			String orderPara = request.getParameter("order"+ppcId);
			ppc.setOrder(Integer.valueOf(orderPara));
			getService().save(ppc);
			
		}	
		return null;
	}
	
	/**
	 * 保存pagePanel及pagePanelColumn集合，调用此方法刷新表单页面
	 * @Methods Name saveAll
	 * @Create In 2008-11-26 By lee
	 * @param mapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception ActionForward
	 */
	public ActionForward saveAll(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		PagePanel pp = (PagePanel) BeanUtil.getObject(request, PagePanel.class);
		this.pps.savePagePanel(pp);
		List mainTableColumns = smcs.findSystemMainTableColumns(pp.getSystemMainTable());
		for(int i=0; i<mainTableColumns.size(); i++){
			SystemMainTableColumn smtc = (SystemMainTableColumn) mainTableColumns.get(i);
			Long smtcId = smtc.getId();
			String isDisplayPara = request.getParameter("isDisplay"+smtcId);
			String lengthForPage = request.getParameter("lengthForPage"+smtcId);
			String isMustInput = request.getParameter("isMustInput"+smtcId);
//			String hiddenValue = request.getParameter("hiddenValue"+smtcId);
			String orderPara = request.getParameter("order"+smtcId);
			
			if(isDisplayPara==null){
				isDisplayPara="0";
			}
			PagePanelColumn ppc=ppcs.findPanelColumn(pp, smtc);
			if(ppc==null)
				ppc=new PagePanelColumn();
			ppc.setPagePanel(pp);
			ppc.setSystemMainTable(pp.getSystemMainTable());
			ppc.setMainTableColumn(smtc);
			ppc.setIsDisplay(Integer.valueOf(isDisplayPara));
			ppc.setLength(lengthForPage);
			if(StringUtils.isNotBlank(isMustInput)){
				ppc.setIsMustInput(Integer.valueOf(isMustInput));
			}
			ppc.setOrder(Integer.valueOf(orderPara));
			getService().save(ppc);
		}	
		return HttpUtil.redirect("pagePanelManage.do?methodCall=toPagePanelEditForm&id="+pp.getId());
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
			List<SystemMainTable> curMainTables=pps.findMainTableByPanel(pps.findPagePanelById(pagePanelId));
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
		System.out.println("可选主表数据： "+json);
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
//			if(column instanceof SystemMainTableColumn)
			json += "{\"stcId\":"+stcId+",\"stcName\":\""+stcName+"\",\"smtId\":\""+smtId+"\",\"smtName\":\""+smtName+"\",\"isMainColumn\":\""+"true"+"\"},";
//			if(column instanceof SystemMainTableExtColumn)
//				json += "{\"stcId\":"+stcId+",\"stcName\":\""+stcName+"\",\"smtId\":\""+smtId+"\",\"smtName\":\""+smtName+"\",\"isMainColumn\":\""+"false"+"\"},";
		}
		json = "{data:[" + json.substring(0, json.length()-1) + "]}";
		System.out.println("可选主表数据： "+json);
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
		
		if("0".equals(parentId) && !"".equals(ppId)){
			PagePanel pp = (PagePanel)pps.findPagePanelById(ppId);	
			List<PagePanelColumn> list = pps.findPagePanelColumnNoParent(pp);
			request.setAttribute("list",list);	
		}else if("".equals(ppId)){
			request.setAttribute("list", null);
		}else{
			List<PagePanelColumn> list = pps.findChildenColumnByParentId(parentId);
			request.setAttribute("list", list);
		}

		return mapping.findForward("pagePanelShow");		
	}
	/**
	 * 添加关联系统主表
	 * @Methods Name addForeignTable
	 * @Create In 2008-12-22 By lee
	 * @param mapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward addForeignTable(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		int index=0;//计数器
		String smtId=request.getParameter("systemMainTable");//前台选择主表ID
		String ppId=request.getParameter("ppId");
		PagePanel pagePanel=pps.findPagePanelById(ppId);//当前pagePanel
		SystemMainTable pagePanelsmt=pagePanel.getSystemMainTable();//当前panel指定主表
		List<SystemMainTable> relationedsmts=ppts.findSystemMainTableByPanel(pagePanel);//获取所有已关联表
		SystemMainTable cursmt=smts.findSystemMainTable(smtId);//当前所选主表
		for (SystemMainTable relationedsmt : relationedsmts) {// 遍历已关联主表
			List mainTableColumns = scs.findSystemTableColumns(relationedsmt);// 遍历关联主表字段
			for (int i = 0; i < mainTableColumns.size(); i++) {
				Column column = (Column) mainTableColumns.get(i);
				SystemMainTable foreignTable = column.getForeignTable();
				if (foreignTable != null && foreignTable != pagePanelsmt
						&& foreignTable == cursmt) {
					PagePanelTableRelation tableRelation = new PagePanelTableRelation();
					tableRelation.setForeignTable(foreignTable);
					tableRelation.setForeignTableColumn((SystemMainTableColumn) column);
					tableRelation.setPagePanel(pagePanel);
					tableRelation.setSystemMainTable(pagePanelsmt);
					pptrs.save(tableRelation);
					index++;
				}
			}
		}
		List curMainTableColumns=scs.findSystemTableColumns(cursmt);
		for (int i = 0; i < curMainTableColumns.size(); i++) {
			Column column = (Column) curMainTableColumns.get(i);
			SystemMainTable foreignTable = column.getForeignTable();
			for(SystemMainTable relationedsmt : relationedsmts){
			if (foreignTable != null && foreignTable != cursmt&& foreignTable == relationedsmt) {
				PagePanelTableRelation tableRelation = new PagePanelTableRelation();
				tableRelation.setForeignTable(foreignTable);
				tableRelation.setForeignTableColumn((SystemMainTableColumn) column);
				tableRelation.setPagePanel(pagePanel);
				tableRelation.setSystemMainTable(cursmt);
				pptrs.save(tableRelation);
				index++;
				}
			}
		}
		//if(index>0){
			pps.savePanelColumnsFormSysMainTable(ppId,smtId);
			ppts.save(ppId, smtId);
		//}
	/*else{
			throw new ApplicationException("没有关联关系");
		}*/
		return HttpUtil.redirect("pagePanelManage.do?methodCall=toPagePanelEditForm&id="+ppId);
	}
	public ActionForward saveColumnsFormSystemMainTable(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String smtId = request.getParameter("systemMainTable");
		String ppId = request.getParameter("ppId");
//		PagePanel pagePanel = pps.findPagePanelById(ppId);
		//保存新的表到panelTable之前做判断，是否有外键关联
//		SystemMainTable smt = null;
//		if(StringUtils.isNotBlank(smtId)){
//			smt = (SystemMainTable) getService().find(SystemMainTable.class, smtId,true);
//		}
//		if(smt.getId()!=null){
//		List lists = pps.findMainTableByPanel(pagePanel);
//			for(int i=0; i < lists.size(); i++){
//				if(lists.get(i).equals(smt)==true){
//					throw new ApplicationException("主表已存在");
//				}
//			}
//		List<Column> list = pps.findColumns(smt, pagePanel.getSettingType());	
//		if(!list.isEmpty()){
//			int count = 0;
//			int sum=0;
//			for(int i=0; i < list.size(); i++){
//				Object o = list.get(i);
//				for(int j = 0 ; j<lists.size(); j++){
//					Object o2 = lists.get(j);
//					List l3 = pps.findColumns((SystemMainTable)o2);
//					for(int k = 0 ; k< (l3.size()); k++){
//						Object ok = l3.get(k);
//						if(((SystemMainTableColumn) o).getForeignTable() == null && ((SystemMainTableColumn) ok).getForeignTable()==null){
//							count++;
//						}else if(((SystemMainTableColumn) o).getForeignTable() != o2 && ((SystemMainTableColumn) ok).getForeignTable()!= smt){
//							count++;
//						}
//						sum++;
//					}
//				}
//			}
//			if(count == sum){
//				throw new ApplicationException("没有关联关系");
//			}
//		}
		
//		}
		
		pps.savePanelColumnsFormSysMainTable(ppId,smtId);
		ppts.save(ppId, smtId);
		return HttpUtil.redirect("pagePanelManage.do?methodCall=toPagePanelEditForm&id="+ppId);
	}
	/**
	 * 提供面板可选按钮,由panel类型决定
	 * @Methods Name findButtonByPanel
	 * @Create In 2008-12-15 By lee
	 * @param mapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception ActionForward
	 */
	public ActionForward getButtons(ActionMapping mapping , ActionForm form ,
			HttpServletRequest request , HttpServletResponse response ){			
//		String panelName = request.getParameter("panelName");
//		PagePanel pp = pps.findPagePanel(panelName);
		String json = "";
		String ppId=request.getParameter("pagePanelId");
//		Object obj=request.getAttribute("pagePanelId");
		PagePanel pp=null;
		if(!ppId.equals("")){
			pp = pps.findPagePanelById(ppId);
			List<PagePanelBtn> btns = ppbs.findPanelBtnByPanel(pp);
			json=CoderForButton.encode(btns);
			System.out.println("创建用户时,发往前台的button数据： "+json);	
			try {			
				response.setCharacterEncoding("utf-8");
				PrintWriter pw = response.getWriter();	
				pw.write(json);		
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	@SuppressWarnings("unchecked")
	public ActionForward modifyButton(ActionMapping mapping ,ActionForm form,
			HttpServletRequest request ,HttpServletResponse response ) throws UnsupportedEncodingException{
		String ppId=request.getParameter("ppId"); 
		String buttonmsg = request.getParameter("buttonmsg");
		String removedButtons=request.getParameter("removedButtons");
		buttonmsg=HttpUtil.ConverUnicode(buttonmsg);
		HashMap buttonMap=new HashMap();
		if(buttonmsg!=null&&buttonmsg.endsWith(",")){
			buttonmsg=buttonmsg.substring(0,buttonmsg.length()-1);
		}
		JSONArray ja = JSONArray.fromObject("[" + buttonmsg + "]");
		for (int i = 0; i < ja.size(); i++) {
			JSONObject opl = (JSONObject) ja.get(i);
			Iterator itPrice = opl.keys();
			while (itPrice.hasNext()) {
				String key = (String) itPrice.next();
				String value = opl.getString(key);
				buttonMap.put(key, value);
			}
			buttonMap.put("pagePanel", ppId);
			metaDataManager.saveEntityData(PagePanelBtn.class, buttonMap);
		}
		if(removedButtons!=null&&!removedButtons.equals("")){
			String removedButton[]=removedButtons.split(",");
			for(String removedButtonId:removedButton)
			metaDataManager.removeEntityData(PagePanelBtn.class, removedButtonId);
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
	
	@SuppressWarnings("unchecked")
	public ActionForward showAllPagePanel(ActionMapping mapping ,ActionForm form,
			HttpServletRequest request ,HttpServletResponse response ){
		
		/*分页的功能*/
		int pageSize = 10;
		String paramName = request.getParameter("start");
		String searchFactor = HttpUtil.ConverUnicode(request.getParameter("searchFactor"));//后天把前台给的unicode编码给转换回来
		String searchBox = request.getParameter("comboxName");
		int start = this.confirmPageNo(paramName, 0);	
		int pageNo=start/pageSize+1;
		Page page = pps.findPagePanelByPage(searchFactor,searchBox,pageNo, pageSize);
		List<PagePanel> list = page.list();
		
		Long total = page.getTotalCount();//这是查询出所有的记录
		String json = "";
		for(int i=0; i< list.size(); i++){
			PagePanel panel = (PagePanel)list.get(i);			
			Long id = panel.getId();		
			String name = panel.getTitle();
			String foreignName = panel.getName();
			String flag = panel.getGroupFlag()+"";
			if(flag.equals("1")){
				flag = "是";
			}else{
				flag = "否";
			}
			json += "{\"id\":"+id+",\"foreignName\":\""+foreignName+"\",\"name\":\""+name+"\",\"flag\":\""+flag+"\"},";
		}
		if(json.length()==0){
			json = "{success:true,rowCount:"+"1"+",data:[" + json.substring(0, json.length()) + "]}";
		}else{
			json = "{success:true,rowCount:"+total+",data:[" + json.substring(0, json.length()-1) + "]}";
		}		

		System.out.println("创建用户时,发往前台的部门数据： "+json);	
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
	 * 确定分页的页码
	 * @Methods Name confirmPageNo
	 * @Create In Dec 23, 2008 By Administrator
	 * @param paramName
	 * @param size
	 * @return int
	 */
	public int confirmPageNo(String paramName ,int size){
		
		if(paramName == null || paramName.equals("")){
			return size;
		}
		return Integer.parseInt(paramName);
	}
	
	@SuppressWarnings("unchecked")
	public ActionForward toForeignTableList (ActionMapping mapping ,ActionForm form,
			HttpServletRequest request ,HttpServletResponse response ) throws UnsupportedEncodingException{
		String ppId = request.getParameter("ppId");
		PagePanel pagePanel = pps.findPagePanelById(ppId);
		List<PagePanelTableRelation> lists=pps.findMainTableRelationByPanel(pagePanel);
		String json = "[";
		for(int i=0; i < lists.size(); i++){
			json+="{";
			json=json+"\"id\":"+lists.get(i).getId()+",";
			json=json+"\"systemMainTable\":\""+lists.get(i).getSystemMainTable().getTableCnName()+"\",";
			json=json+"\"foreignTableColumn\":\"" +lists.get(i).getForeignTableColumn().getColumnCnName()+"\",";
			json=json+"\"foreignTable\":\""+lists.get(i).getForeignTable().getTableCnName()+"\"";
			json+="},";
		}
		json = json.substring(0, json.length()-1);
		json+="]";
		//System.out.println(json);
		try{
			response.setCharacterEncoding("gbk");
			PrintWriter pw = response.getWriter();
			pw.write("{success:true,rowCount:10,data:"+ json + "}");
		}catch(Exception e){
			e.printStackTrace();
			
		}
				return null;
		
	}
	@SuppressWarnings("unchecked")
	public ActionForward toForeignTableSave (ActionMapping mapping ,ActionForm form,
			HttpServletRequest request ,HttpServletResponse response ) throws UnsupportedEncodingException{
				String ppId = HttpUtil.ConverUnicode(request.getParameter("ppId"));
				String price = HttpUtil.ConverUnicode(request.getParameter("price"));
				PagePanelTableRelation pagePanelTableRelation = null;
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
						priceMap.put(key, value);
					}
				}
				priceMap.put("pagePanel",ppId);
				pagePanelTableRelation = (PagePanelTableRelation) BeanUtil.getObject(priceMap,PagePanelTableRelation.class);
				pps.savePagePanelTableRelation(pagePanelTableRelation);
				return null;
		
	}
	public ActionForward findAllmainTable (ActionMapping mapping ,ActionForm form,
			HttpServletRequest request ,HttpServletResponse response ) throws UnsupportedEncodingException{
			
			int pageSize = 10;
			int start = this.getInt(request, "start", 0);
			int pageNo = (start / pageSize) + 1;
//			Page page =  pps.findSystemMainTable(pageNo, pageSize);
			Page page = pps.findSystemMainTable(pageNo, pageSize);
			List list = page.list();
			Long rowCount = page.getTotalCount();
//			List list = pps.findAllTable();
			SystemMainTable ststemMainTable=null;
			String json = "";
			for(int i=0; i < list.size(); i++){
				ststemMainTable = (SystemMainTable) list.get(i);
				Long code = ststemMainTable.getId();
				String name = ststemMainTable.getTableCnName();
				if(name==null){
					name = "";
				}
				json += "{name:\""+name+"\",code:\""+code+"\""+",rowCount:"+rowCount+"},";	
			}
			json = "data:[" + json.substring(0, json.length()-1) + "]";
			System.out.println("json:"+json);
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
	public ActionForward findAllForeignCol (ActionMapping mapping ,ActionForm form,
			HttpServletRequest request ,HttpServletResponse response ) throws UnsupportedEncodingException{
		request.setCharacterEncoding("GBK");
		String value = HttpUtil.ConverUnicode(request.getParameter("value"));		
		if(value==null||!Pattern.matches("^\\+?[0-9][0-9]*$", value)){
			return null;
		}
		int pageSize = 5;
		int start = this.getInt(request, "start", 0);
		int pageNo = (start / pageSize) + 1;
		Page page = pps.findForeignKey(Long.valueOf(value), pageNo, pageSize);
		List lists = page.list();
		Long rowCount = page.getTotalCount();
		
		//SystemMainTable systemMainTable = pps.findSystemMainTable(value);
		//List lists = pps.findAllSystemMainTableColumnByName(systemMainTable.getTableName());
		String json = "";
		SystemMainTableColumn systemMainTableColumn = new SystemMainTableColumn();
		for(int i=0; i< lists.size(); i++){
			systemMainTableColumn=(SystemMainTableColumn) lists.get(i);
			if(systemMainTableColumn.getForeignTable()!=null){
				Long code = systemMainTableColumn.getId();
				String name = systemMainTableColumn.getColumnCnName();
				json += "{name:\""+name+"\",code:\""+code+"\""+",rowCount:"+rowCount+"},";	
			}			
		}
		System.out.println("json:"+json);
		if(!json.equals("")&&json!=null){
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
		}		
		return null;
		
	}
	public ActionForward findForeignByColumnID (ActionMapping mapping ,ActionForm form,
			HttpServletRequest request ,HttpServletResponse response ) throws UnsupportedEncodingException{
				String foreignTableColumn = request.getParameter("foreignTableColumn");
				SystemMainTableColumn systemMainTableColumn = new SystemMainTableColumn();
				systemMainTableColumn = pps.findSystemMainTableColumn(foreignTableColumn);
				SystemMainTable systemMainTable = systemMainTableColumn.getForeignTable();
					Long code = systemMainTable.getId();
					String code1=String.valueOf(code);
//					String name = systemMainTable.getTableCnName();
				try {
					response.setCharacterEncoding("GBK");
					PrintWriter pw = response.getWriter();
					pw.write(code1);		
				} catch (Exception e) {
					e.printStackTrace();
				}
				return null;
	}
	public ActionForward toForeignTableRemove(ActionMapping mapping ,ActionForm form,
			HttpServletRequest request ,HttpServletResponse response ) throws UnsupportedEncodingException{
				String ids = request.getParameter("ids");
				String[] rid=ids.split(","); 
				for(String tableID:rid){
					pps.removePagePanelTableRelation(tableID);
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
	public ActionForward initButton (ActionMapping mapping ,ActionForm form,
			HttpServletRequest request ,HttpServletResponse response ) throws UnsupportedEncodingException{
		String ppId=request.getParameter("ppId");
		PagePanel pagePanel=pps.findPagePanelById(ppId);
		ppbs.initPagePanelBtn(pagePanel);
		String json="{success:'true'}";
		response.setCharacterEncoding("gbk");
		try{
			response.getWriter().write(json);
			response.getWriter().flush();
		}catch(IOException e){
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 新增fieldSet
	 * @Methods Name addFieldSet
	 * @Create In May 13, 2009 By guoxl
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 */
	public ActionForward addFieldSet(ActionMapping mapping ,ActionForm form,
			HttpServletRequest request ,HttpServletResponse response){
		try {
			request.setCharacterEncoding("GBK");
		} catch (UnsupportedEncodingException e2) {
			e2.printStackTrace();
		}
		String pagePanelId=request.getParameter("pagePanelId");//所属的pagePanel
		String index =request.getParameter("index");//排序
		PagePanel pagePanel =pps.findPagePanelById(pagePanelId);
		PagePanelColumn pagePanelColumn = pps.savePagePanelColumn(pagePanel, index);
		String title = HttpUtil.ConverUnicode(request.getParameter("title"));	
		
		pps.savePagePanelFieldSet(pagePanelColumn, pagePanel, title);
		
		
		try{
			response.getWriter().write("{success:true}");
			response.getWriter().flush();
		}catch(IOException e){
			e.printStackTrace();
		}
		return null;
	}
	private int getInt(HttpServletRequest request, String param, int defaultValue) {
		String strValue = request.getParameter(param);
		if (strValue == null) {
			return defaultValue;
		} else {
			return Integer.parseInt(strValue)+1;
		}
	}
}
