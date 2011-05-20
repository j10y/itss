package com.zsgj.info.appframework.pagemodel.web.struts;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.zsgj.info.appframework.metadata.entity.SystemMainTable;
import com.zsgj.info.appframework.metadata.entity.SystemMainTableColumn;
import com.zsgj.info.appframework.metadata.entity.TableSettingType;
import com.zsgj.info.appframework.metadata.entity.UserTableSetting;
import com.zsgj.info.appframework.pagemodel.entity.PageGroupPanelTable;
import com.zsgj.info.appframework.pagemodel.entity.PageModel;
import com.zsgj.info.appframework.pagemodel.entity.PageModelBtn;
import com.zsgj.info.appframework.pagemodel.entity.PageModelPanel;
import com.zsgj.info.appframework.pagemodel.entity.PageModelPanelMiddleTable;
import com.zsgj.info.appframework.pagemodel.entity.PageModelPanelTable;
import com.zsgj.info.appframework.pagemodel.entity.PagePanel;
import com.zsgj.info.appframework.pagemodel.entity.PagePanelRelation;
import com.zsgj.info.appframework.pagemodel.entity.PagePanelTable;
import com.zsgj.info.appframework.pagemodel.entity.PagePanelType;
import com.zsgj.info.appframework.pagemodel.service.PageGroupPanelService;
import com.zsgj.info.appframework.pagemodel.service.PageModelGenService;
import com.zsgj.info.appframework.pagemodel.service.PageModelPanelService;
import com.zsgj.info.appframework.pagemodel.service.PageModelService;
import com.zsgj.info.appframework.pagemodel.service.PagePanelService;
import com.zsgj.info.appframework.pagemodel.servlet.PageParameter;
import com.zsgj.info.framework.context.ContextHolder;
import com.zsgj.info.framework.dao.support.Page;
import com.zsgj.info.framework.exception.ApplicationException;
import com.zsgj.info.framework.security.entity.Module;
import com.zsgj.info.framework.util.BeanUtil;
import com.zsgj.info.framework.util.HttpUtil;
import com.zsgj.info.framework.web.adapter.struts.BaseDispatchAction;
import com.zsgj.info.framework.web.json.JsonUtil;

public class PageModelAction extends BaseDispatchAction{
	static final String FSP = System.getProperty("file.separator");
 
//	private SystemColumnService scs = (SystemColumnService) getBean("systemColumnService");
//	private SystemMainTableService smts = (SystemMainTableService) getBean("systemMainTableService");
//	private SystemMainColumnService smcs = (SystemMainColumnService) getBean("systemMainColumnService");
//	private SecurityManageService sms = (SecurityManageService) getBean("securityManageService");
//	private SystemExtColumnServcie secs=(SystemExtColumnServcie)getBean("systemExtColumnService");
	private PageModelService pms = (PageModelService) getBean("pageModelService");
	private PagePanelService pps = (PagePanelService) getBean("pagePanelService");
	private PageModelPanelService pmps = (PageModelPanelService) getBean("pageModelPanelService");
//	private CustomerTableService cts = (CustomerTableService) getBean("customerTableService"); 
	
	private PageModelGenService pageModelGenService = (PageModelGenService) ContextHolder.getBean("pageModelGenService");
	//private ConfigItemService configItemService = (ConfigItemService) ContextHolder.getBean("configItemService");
	private PageGroupPanelService pgps = (PageGroupPanelService) ContextHolder.getBean("pageGroupPanelService");
	public ActionForward genPageModelCode(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String FSP = System.getProperty("file.separator");
		String keyName = request.getParameter("name");
		PageModel model = pms.findPageModel(keyName);
		 
		String pagePath = model.getPagePath();
		boolean extis = this.pms.existPageModelCountByPagePath(pagePath);
		if(extis&& model.getId()==null){
			throw new ApplicationException("您输入的页面路径已经存在，请更换其他文件名称或路径");
		}
		String pagePathUrl = request.getSession().getServletContext().getRealPath(FSP);
		
		//*********************************************************************
		//我现在需要的是从根的下面开始写一直写到最后。
		
		PageModel model2 = pms.findPageModel$$$$$(keyName);
		List<PageModelPanel> pmps2 = model2.getPagePanels();
//		if(StringUtils.isNotBlank(dataId)&& !dataId.equals("0")){
//			pmps2 = configItemService.findPageModelPanel(keyName, dataId);
//		}
		 //下面是将pagemodel的各种属性放入模型之中
	    PageParameter para=new PageParameter();
		Integer settingType = model2.getSettingType();
	     if(settingType==1){
	    	 para.setModelTableName(model.getSystemMainTable().getTableName());
			 para.setModelName(model.getName());
			 para.setPagePath(model.getPagePath());
			 para.setPanelname(model.getMainPagePanel().getName());
	    	 para.setClazz(model.getMainPagePanel().getSystemMainTable().getClassName());
	    	 
	    	 pageModelGenService.generatePageModelCode(para,settingType,pagePathUrl);
	     }else if(settingType==2){
				para.setModelName(model.getName());
				para.setModelTitle(model.getTitle());
				para.setModelTableName(model.getSystemMainTable().getTableName());
				para.setPagePath(model.getPagePath());
				para.setPagePathType(model.getPagePanelType().getName());
				List<PageParameter> list = new ArrayList<PageParameter>();
		        //下面的是将panel的属性放入到模型之中
			     for(PageModelPanel item : pmps2){ //遍历第1层面板
				    list.add(this.genPagePaneModel(item));
			    }
			    para.setPanels(list);
			    
			    pageModelGenService.generatePageModelCode(para,settingType,pagePathUrl);
	     }
		
		
		return HttpUtil.redirect("pageModelManage.do?methodCall=toPageModelEditForm&id="+model2.getId());
	}
	
	
	private PageParameter getChildPanel(PagePanelRelation pmp){
		String fcolumnPropName = "";
		String pcolumnPropName = "";
		PagePanel panel = pmp.getPagePanel();
//		String panelName = panel.getName();
//		Long panelId= panel.getId();
//		PagePanel parent = pmp.getParentPagePanel();
//		Long parentId = parent.getId();
		//程序进入此处，肯定是从分组面板进入的
		List<PageGroupPanelTable> list2 = this.pgps.findGroupPanelTableBySub(pmp.getParentPagePanel(), panel);
		PageGroupPanelTable pgpt = null;
		if(list2!=null && !list2.isEmpty()){
			pgpt = list2.iterator().next();
		}
		if(pgpt!=null){
			SystemMainTable parentPanelTable = pgpt.getParentPanelTable();
			SystemMainTableColumn fcolumn = pgpt.getSubPanelTableFColumn();
			fcolumnPropName = fcolumn.getPropertyName();
			String pptableName = parentPanelTable.getTableName();
			SystemMainTableColumn pc = pgpt.getParentPanelTablePColumn();
			pcolumnPropName = pc.getPropertyName();
			pcolumnPropName = pptableName +"$"+ pcolumnPropName;
		}
		
		
		PageParameter para=new PageParameter();
		para.setFcolumnPropName(fcolumnPropName);
		para.setPcolumnPropName(pcolumnPropName);
		para.setPanelname(panel.getName());
		para.setPanelTitle(panel.getTitle());
		para.setPanelTableName(panel.getSystemMainTable().getTableCnName());
		para.setClazz(panel.getSystemMainTable().getClassName());
		para.setXtype(panel.getXtype().getName());
		
		Integer readonly = pmp.getReadonly();
		if(readonly==null) readonly = 0;
		para.setReadonlyFlag(String.valueOf(readonly));
		
		String groupFlag=String.valueOf(panel.getGroupFlag());
		para.setGroupFlag(groupFlag);
		String queryFlag=String.valueOf(panel.getGroupFlag());
		para.setQueryFlag(queryFlag);
		String order=String.valueOf(panel.getGroupFlag());
	    para.setOrder(order);
	    List<PageParameter> list = new ArrayList<PageParameter>();
	    Set<PagePanelRelation> childpprs = panel.getChildPagePanels();
		if(!childpprs.isEmpty()){
			for(PagePanelRelation ppr : childpprs){
				list.add(this.getChildPanel(ppr));
			
			}
		}
		para.setChildPagePanels(list);
		return para;
	}
	private PageParameter genPagePaneModel(PageModelPanel pmp){
		PagePanel panel = pmp.getPagePanel();
		String fcolumnPropName = "";
		String pcolumnPropName = "";
		if(panel.getGroupFlag()!=null&& panel.getGroupFlag().intValue()==0){
			//如当前财务面板改成editorgrid
			if(panel.getXtype().getName().equalsIgnoreCase("editorgrid")||panel.getXtype().getName().equalsIgnoreCase("grid")){
				SystemMainTable subTable = panel.getSystemMainTable();
				List<PageModelPanelTable>  pmpts = this.pms.findPageModelPanelTableBySub(
							pmp.getPageModel(), panel, subTable);
				for(PageModelPanelTable pmpt : pmpts){
					//配置项面板
					PagePanel parentPanel = pmpt.getParentPagePanel();
					//配置项父表
					SystemMainTable parentTable = parentPanel.getSystemMainTable();
					SystemMainTableColumn parentPanelTablePColumn = pmpt.getParentPanelTablePColumn();
					//ConfigItem$id
					pcolumnPropName = parentTable.getTableName()+"$"+parentPanelTablePColumn.getPropertyName();
					SystemMainTableColumn subFc = pmpt.getSubPanelTableFColumn();
					fcolumnPropName = subFc.getPropertyName();
				}
				//通过子面板和子面板主表获取关联表等信息
//				List<PageModelPanelMiddleTable>  pmpMiddts = this.pms.findPageModelPanelMiddleTableBySub(
//						pmp.getPageModel(), panel, subTable);
//				for(PageModelPanelMiddleTable pmpt : pmpMiddts){
					//配置项面板
//					SystemMainTable middleTable = pmpt.getMiddleTable();
//					SystemMainTableColumn parentColumn = pmpt.getParentColumn();
					
					
//				}
				
			}
			//System.out.println("panelname: "+ panel.getName());
			
		}
		
		
		PageParameter para=new PageParameter();
		para.setFcolumnPropName(fcolumnPropName);
		para.setPcolumnPropName(pcolumnPropName);
		para.setPanelname(panel.getName());
		para.setPanelTitle(panel.getTitle());
		if(panel.getSystemMainTable()!=null){
			para.setPanelTableName(panel.getSystemMainTable().getTableCnName());
			para.setClazz(panel.getSystemMainTable().getClassName());
		}
		
		Integer readonly = pmp.getReadonly();
		if(readonly==null) readonly = 0;
		
		para.setReadonlyFlag(String.valueOf(readonly));
		para.setXtype(panel.getXtype().getName());
		String groupFlag=String.valueOf(panel.getGroupFlag());
		para.setGroupFlag(groupFlag);
		String queryFlag=String.valueOf(panel.getGroupFlag());
		para.setQueryFlag(queryFlag);
		String order=String.valueOf(panel.getGroupFlag());
	    para.setOrder(order);
	    List<PageParameter> list = new ArrayList<PageParameter>();
	    Set<PagePanelRelation> childpprs = panel.getChildPagePanels();
		if(!childpprs.isEmpty()){
			for(PagePanelRelation ppr : childpprs){
				list.add(this.getChildPanel(ppr));
			}
		  }
		para.setChildPagePanels(list);
		return para;
	}
	
	
	public ActionForward list(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		//pps.saveExtTable();
		//cts.genNewEntityAndMap("com.digitalchina.itil.config.entity", "HelloTest", ConfigItem.class);
		
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
		Page page = pms.findPageModel(params, pageNo, 15);
		request.setAttribute("page", page);
		
		
		return mapping.findForward("listPageModel");
	}
	
	public ActionForward toPageModelEditForm(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
	
		List settingTypes = super.getService().findAll(TableSettingType.class);
		request.setAttribute("settingTypes", settingTypes);
		
		String id = request.getParameter("id");
		if(StringUtils.isNotBlank(id)){
			//当其不是空串或是null的时候
			PageModel pageModel = (PageModel) super.getService().find(PageModel.class, id);
			request.setAttribute("detail", pageModel);
			
			List<PageModelPanelTable> pageModelPanelTables = this.pms.findPageModelPanelTableByModel(pageModel);
			request.setAttribute("pageModelPanelTables", pageModelPanelTables);
		}
		List pagePanelTypes=getService().find(PagePanelType.class, "groupFlag", Integer.valueOf(1));
		request.setAttribute("pagePanelTypes", pagePanelTypes);
		
		List sysMainTables = getService().findAll(SystemMainTable.class);
		request.setAttribute("sysMainTables", sysMainTables);
		
		List modules = getService().findAll(Module.class);
		request.setAttribute("modules", modules);
		
		List pagePanels = getService().findAll(PagePanel.class);
		request.setAttribute("pagePanels", pagePanels);
		
		List<PagePanelType> panelTypes = pps.findAllGroupPanelTypes();
		request.setAttribute("pagePanelTypes", panelTypes);
		
		return mapping.findForward("pageModelEditForm");
	}
	
	/**
	 * 初始的时候给一些按钮制定下一个页面，要分为“保存，修改，查询”
	 * @Methods Name save
	 * @Create In Dec 5, 2008 By Administrator
	 * @param mapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception ActionForward
	 */
	
	public ActionForward save(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		PageModel pageModel = (PageModel) BeanUtil.getObject(request, PageModel.class);
		
		String pagePath = pageModel.getPagePath();
		if(StringUtils.isNotBlank(pagePath)){
			boolean extis = this.pms.existPageModelCountByPagePath(pagePath);
			if(extis&& pageModel.getId()==null){
				throw new ApplicationException("您输入的页面路径已经存在，请更换其他文件名称或路径");
			}
			
		}
		
		boolean isInsert = false;
		if(pageModel.getId()==null){
			isInsert = true;
			if(pagePath==null||pagePath.equals("")){
				Module module = pageModel.getModule();
				if(module==null){
					throw new ApplicationException("所属模块必须选择");
				}
				module = (Module) super.getService().find(Module.class, String.valueOf(module.getId()));
				PagePanel mainPanel = pageModel.getMainPagePanel();
				mainPanel = (PagePanel) getService().find(PagePanel.class, String.valueOf(mainPanel.getId()));
				SystemMainTable smt = mainPanel.getSystemMainTable();
				
				Integer settingType = pageModel.getSettingType();
				if(settingType==null){
					throw new ApplicationException("页面显示类型必须选择");
				}
				if(settingType.intValue()==UserTableSetting.LIST){
					String defaultPagePathName = smt.getTableName()+"_List.jsp";
					defaultPagePathName = "/user/"+module.getServiceKeyName()+"/userExt/"+ defaultPagePathName;
					pageModel.setPagePath(defaultPagePathName);
					
				}else{
					String defaultPagePathName = smt.getTableName()+"_Input.jsp";
					defaultPagePathName = "/user/"+module.getServiceKeyName()+"/userExt/"+ defaultPagePathName;
					pageModel.setPagePath(defaultPagePathName);
				}
			}
		}
		this.pms.savePageModel(pageModel);
		if(isInsert){
			this.pms.savePageModelBtn(pageModel);
		}
		
		return HttpUtil.redirect("pageModelManage.do?methodCall=toPageModelEditForm&id="+pageModel.getId());
	}		
	
	public ActionForward remove(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		String[] pagePanelIds = request.getParameterValues("id");
		int pageNo = HttpUtil.getInt(request, "pageNo", 1);
		this.pms.removePageModel(pagePanelIds);
		
		return HttpUtil.redirect("pageModelManage.do?methodCall=list&pageNo="+pageNo); 
	}
	
	
	/*加载相应的pageModel的所有的PagePanel
	 * */
	public ActionForward loadPageModel(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		String parentId = request.getParameter("id");
		String pageModelId = request.getParameter("mid"); 
		
		if("-100".equals(parentId) && !"".equals(pageModelId)){
			
			PageModel model = (PageModel)pmps.findPageModelById(pageModelId);			
			List<PageModelPanel> list = pmps.findPagePanelByNoParent(model);
			request.setAttribute("list",list);	
		}else if("".equals(pageModelId)){
			request.setAttribute("list", null);
		}else{
			/*思路就是首先得到父类的pagePanel，通过次找到相应的pageModelPanel,在找到相应的子pagePanel
			 * */
			List<PageModelPanel> list = pmps.findPageModelPanelByDoubleId(parentId);
			request.setAttribute("list", list);
		}

		return mapping.findForward("pageModelPanelShow");		


	}
	
	/*获得某一类的list形式
	 * */
	public ActionForward findModuleListByEntity(ActionMapping mapping ,ActionForm actionForm,
			HttpServletRequest request,HttpServletResponse response){
		List model = super.getService().findAll(Module.class);			
		String json = "";
		for(int i=0; i< model.size(); i++){
		Module pageModel = (Module)model.get(i);			
		Long id = pageModel.getId();
		String name = pageModel.getName();
		json += "{\"id\":"+id+",\"name\":\""+name+"\"},";
		}
		json = "{data:[" + json.substring(0, json.length()-1) + "]}";
		System.out.println("创建用户时,发往前台的module数据： "+json);	

		try {			
		response.setCharacterEncoding("utf-8");
		PrintWriter pw = response.getWriter();	
		pw.write(json);		
		} catch (IOException e) {
		e.printStackTrace();
		}
		return null;
	}
	
	public ActionForward findPanelTypeByEntity(ActionMapping mapping ,ActionForm actionForm,
			HttpServletRequest request,HttpServletResponse response){
		List type = super.getService().findAll(PagePanelType.class);			
		String json = "";
		for(int i=0; i< type.size(); i++){
		PagePanelType pageType = (PagePanelType)type.get(i);			
		Long id = pageType.getId();
		String name = pageType.getCnName();
		json += "{\"type\":"+id+",\"name\":\""+name+"\"},";
		}
		json = "{data:[" + json.substring(0, json.length()-1) + "]}";
		System.out.println("创建用户时,发往前台的module数据： "+json);	
		try {			
		response.setCharacterEncoding("utf-8");
		PrintWriter pw = response.getWriter();	
		pw.write(json);		
		} catch (IOException e) {
		e.printStackTrace();
		}
		return null;
	}
	
	
	public ActionForward modifyAndSavePanelMessage(ActionMapping mapping ,ActionForm actionForm,
			HttpServletRequest request,HttpServletResponse response) throws IOException{
//		String json = "{success:true}";		
		String pagePanelId = request.getParameter("panelId");
		String modifyDis =request.getParameter("display");
		String modifyTypeId = request.getParameter("type");		
		String title = request.getParameter("titleDisplay");
		String readonly = request.getParameter("readonly");
		/*当用户不做任何修改的时候*/
		if(modifyDis.equals("")&&modifyTypeId.equals("")&&title.equals("")&&readonly.equals("")){				
			PrintWriter pw = response.getWriter();
			pw.write("{success:true}");
			return null;
		/*当用户修改一个的时候*/		
		}else if(!modifyDis.equals("")&&modifyTypeId.equals("")&&title.equals("")&&readonly.equals("")){
			PageModelPanel modifyPageModelPanel = pmps.findPageModelPanelById(pagePanelId);
			modifyPageModelPanel.setIsDisplay(Integer.parseInt(modifyDis));
			super.getService().save(modifyPageModelPanel);
			PrintWriter pw = response.getWriter();
			pw.write("{success:true}");
			return null;
			
		}else if(modifyDis.equals("")&&!modifyTypeId.equals("")&&title.equals("")&&readonly.equals("")){
			PageModelPanel modifyPageModelPanel = pmps.findPageModelPanelById(pagePanelId);
			PagePanelType pageType = pmps.findPagePanelTypeById(modifyTypeId);
			modifyPageModelPanel.setXtype(pageType);
			modifyPageModelPanel.getPagePanel().setXtype(pageType);
			super.getService().save(modifyPageModelPanel);
			super.getService().save(modifyPageModelPanel.getPagePanel());
			PrintWriter pw = response.getWriter();
			pw.write("{success:true}");
			return null;
		}else if(modifyDis.equals("")&&modifyTypeId.equals("")&&!title.equals("")&&readonly.equals("")){
			PageModelPanel modifyPageModelPanel = pmps.findPageModelPanelById(pagePanelId);
			modifyPageModelPanel.setTitleDisplayFlag(Integer.parseInt(title));
			super.getService().save(modifyPageModelPanel);
			PrintWriter pw = response.getWriter();
			pw.write("{success:true}");
			return null;
		}else if(modifyDis.equals("")&&modifyTypeId.equals("")&&title.equals("")&&!readonly.equals("")){
			PageModelPanel modifyPageModelPanel = pmps.findPageModelPanelById(pagePanelId);
			modifyPageModelPanel.setReadonly(Integer.parseInt(readonly));
			super.getService().save(modifyPageModelPanel);
			PrintWriter pw = response.getWriter();			
			pw.write("{success:true}");
			return null;
			/*当修改两个的时候*/
		}else if(!modifyDis.equals("")&&!modifyTypeId.equals("")&&title.equals("")&&!readonly.equals("")){
			PageModelPanel modifyPageModelPanel = pmps.findPageModelPanelById(pagePanelId);
			PagePanelType pageType = pmps.findPagePanelTypeById(modifyTypeId);			
			modifyPageModelPanel.setIsDisplay(Integer.parseInt(modifyDis));			
			modifyPageModelPanel.setXtype(pageType);
			super.getService().save(modifyPageModelPanel);
			PrintWriter pw = response.getWriter();
			pw.write("{success:true}");
			return null;
		}else if(!modifyDis.equals("")&&modifyTypeId.equals("")&&!title.equals("")&&readonly.equals("")){
			PageModelPanel modifyPageModelPanel = pmps.findPageModelPanelById(pagePanelId);
			modifyPageModelPanel.setIsDisplay(Integer.parseInt(modifyDis));
			modifyPageModelPanel.setTitleDisplayFlag(Integer.parseInt(title));
			super.getService().save(modifyPageModelPanel);
			PrintWriter pw = response.getWriter();
			pw.write("{success:true}");
			return null;
		}else if(!modifyDis.equals("")&&modifyTypeId.equals("")&&title.equals("")&&!readonly.equals("")){
			PageModelPanel modifyPageModelPanel = pmps.findPageModelPanelById(pagePanelId);
			modifyPageModelPanel.setIsDisplay(Integer.parseInt(modifyDis));
			modifyPageModelPanel.setReadonly(Integer.parseInt(readonly));
			super.getService().save(modifyPageModelPanel);
			PrintWriter pw = response.getWriter();
			pw.write("{success:true}");
			return null;
		}else if(modifyDis.equals("")&&!modifyTypeId.equals("")&&!title.equals("")&&readonly.equals("")){
			PageModelPanel modifyPageModelPanel = pmps.findPageModelPanelById(pagePanelId);
			PagePanelType pageType = pmps.findPagePanelTypeById(modifyTypeId);
			modifyPageModelPanel.setXtype(pageType);
			modifyPageModelPanel.setTitleDisplayFlag(Integer.parseInt(title));
			super.getService().save(modifyPageModelPanel);
			PrintWriter pw = response.getWriter();
			pw.write("{success:true}");
			return null;
		}else if(modifyDis.equals("")&&!modifyTypeId.equals("")&&title.equals("")&&!readonly.equals("")){
			PageModelPanel modifyPageModelPanel = pmps.findPageModelPanelById(pagePanelId);
			PagePanelType pageType = pmps.findPagePanelTypeById(modifyTypeId);
			modifyPageModelPanel.setXtype(pageType);
			modifyPageModelPanel.setReadonly(Integer.parseInt(readonly));
			super.getService().save(modifyPageModelPanel);
			PrintWriter pw = response.getWriter();
			pw.write("{success:true}");
			return null;
		}else if(modifyDis.equals("")&&modifyTypeId.equals("")&&!title.equals("")&&!readonly.equals("")){
			PageModelPanel modifyPageModelPanel = pmps.findPageModelPanelById(pagePanelId);
			modifyPageModelPanel.setTitleDisplayFlag(Integer.parseInt(title));
			modifyPageModelPanel.setReadonly(Integer.parseInt(readonly));
			super.getService().save(modifyPageModelPanel);
			PrintWriter pw = response.getWriter();
			pw.write("{success:true}");
			return null;
			/*当修改三个的时候*/
		}else if(!modifyDis.equals("")&&!modifyTypeId.equals("")&&!title.equals("")&&readonly.equals("")){
			PageModelPanel modifyPageModelPanel = pmps.findPageModelPanelById(pagePanelId);
			modifyPageModelPanel.setIsDisplay(Integer.parseInt(modifyDis));
			modifyPageModelPanel.setTitleDisplayFlag(Integer.parseInt(title));
			PagePanelType pageType = pmps.findPagePanelTypeById(modifyTypeId);
			modifyPageModelPanel.setXtype(pageType);
			super.getService().save(modifyPageModelPanel);
			PrintWriter pw = response.getWriter();
			pw.write("{success:true}");
			return null;
		}else if(!modifyDis.equals("")&&modifyTypeId.equals("")&&!title.equals("")&&!readonly.equals("")){
			PageModelPanel modifyPageModelPanel = pmps.findPageModelPanelById(pagePanelId);
			modifyPageModelPanel.setIsDisplay(Integer.parseInt(modifyDis));
			modifyPageModelPanel.setTitleDisplayFlag(Integer.parseInt(title));
			modifyPageModelPanel.setReadonly(Integer.parseInt(readonly));
			super.getService().save(modifyPageModelPanel);
			PrintWriter pw = response.getWriter();
			pw.write("{success:true}");
			return null;
		}else if(modifyDis.equals("")&&!modifyTypeId.equals("")&&!title.equals("")&&!readonly.equals("")){
			PageModelPanel modifyPageModelPanel = pmps.findPageModelPanelById(pagePanelId);
			PagePanelType pageType = pmps.findPagePanelTypeById(modifyTypeId);
			modifyPageModelPanel.setXtype(pageType);
			modifyPageModelPanel.setTitleDisplayFlag(Integer.parseInt(title));
			modifyPageModelPanel.setReadonly(Integer.parseInt(readonly));
			super.getService().save(modifyPageModelPanel);
			PrintWriter pw = response.getWriter();
			pw.write("{success:true}");
			return null;
			/*最后当全部修改的时候*/
		}else{
			PageModelPanel modifyPageModelPanel = pmps.findPageModelPanelById(pagePanelId);
			PagePanelType pageType = pmps.findPagePanelTypeById(modifyTypeId);			
			modifyPageModelPanel.setIsDisplay(Integer.parseInt(modifyDis));
			modifyPageModelPanel.setXtype(pageType);
			modifyPageModelPanel.setReadonly(Integer.parseInt(readonly));
			modifyPageModelPanel.setTitleDisplayFlag(Integer.parseInt(title));
			super.getService().save(modifyPageModelPanel);			
			PrintWriter pw = response.getWriter();
			pw.write("{success:true}");
			return null;
		}
		
			
	}
	
	/**
	 * 当我选择的时候页面字段显示方式的时候，显示出页面相关按钮类型
	 */
	public ActionForward findButtonTypeByPageModel(ActionMapping mapping , ActionForm form ,
			HttpServletRequest request , HttpServletResponse response ){
		String json = "";	
		String pageModelId = request.getParameter("pageModelId");
		PageModel pageModel = pms.findPageModelById(pageModelId); 
			//(PageModel)super.getService().find(PageModel.class, pageModelId)	;
		if(pageModel!=null){
			List<PageModelBtn> btn = pmps.findPageModelBtnByPageModel(pageModel);
			for(int i=0; i< btn.size(); i++){
				PageModelBtn modelBtn = (PageModelBtn)btn.get(i);			
				Long id = modelBtn.getId();
				String name = modelBtn.getBtnName();			
				Integer order = modelBtn.getOrder();
				String link = modelBtn.getLink();
				String imageUrl = modelBtn.getImageUrl();
				Integer isDisplay = modelBtn.getIsDisplay();
				Integer openWinFlag = modelBtn.getOpenWinFlag();
				String method = modelBtn.getMethod();
				if(isDisplay==null){
					isDisplay=1;
				}
				if(openWinFlag==null){
					openWinFlag=1;
				}
				if(link==null){
					link="";
				}
				if(method==null){
					method="";
				}
				String nextPageModelName;
				if(modelBtn.getNextPageModel()==null){
					nextPageModelName = "请选择...";
				}else{
					nextPageModelName = modelBtn.getNextPageModel().getName();
				}
				json += "{\"id\":"+id+",\"method\":\""+method+"\",\"isDisplay\":"+isDisplay+",\"openWinFlag\":"+openWinFlag+",\"imageUrl\":\""+imageUrl+"\",\"btnName\":\""+name+"\",\"order\":"+order+",\"link\":\""+link+"\",\"pageModelName\":\""+nextPageModelName+"\"},";
			}//end
			//System.out.println("json:"+json);
			if(!json.equals("")){
				json = "{data:[" + json.substring(0, json.length()-1) + "]}";
				//System.out.println("创建用户时,发往前台的button数据： "+json);	
			}else{
				json = "{data:[]}";
			}
			
			try {			
				response.setCharacterEncoding("utf-8");
				PrintWriter pw = response.getWriter();	
				pw.write(json);		
				pw.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		return null;
	}
	
	/**
	 * 由于用户的修改来重新保存按钮的种类modifyButtonTypeByCustomer
	 * @throws UnsupportedEncodingException 
	 */
	public ActionForward modifyButtonTypeByCustomer(ActionMapping mapping ,ActionForm form,
			HttpServletRequest request ,HttpServletResponse response ) throws UnsupportedEncodingException{
//		boolean marker = false;
		String js = "";
		String pageModelId = request.getParameter("ModelId");
		String btnName = request.getParameter("btnName");
		String id = request.getParameter("id");
		String link = request.getParameter("link");
		String imageUrl = request.getParameter("imageUrl");
		String order = request.getParameter("order");
		String method = request.getParameter("method");
		String pageModelName = request.getParameter("pageModelName");
		String isDisplay = request.getParameter("isDisplay");
		String openWinFlag = request.getParameter("openWinFlag");
		btnName = new String(btnName.getBytes("iso8859-1"),"GBK");
		id = new String(id.getBytes("iso8859-1"),"GBK");
		link = new String(link.getBytes("iso8859-1"),"GBK");
		pageModelName = new String(pageModelName.getBytes("iso8859-1"),"GBK");
		pageModelId = new String(pageModelId.getBytes("iso8859-1"),"GBK");
		method = new String(method.getBytes("iso8859-1"),"GBK");
		isDisplay = new String(isDisplay.getBytes("iso8859-1"),"GBK");
		openWinFlag = new String(openWinFlag.getBytes("iso8859-1"),"GBK");
		
		PageModel pageModel = pms.findPageModelById(pageModelId);
			//(PageModel)super.getService().find(PageModel.class, pageModelId,true);
		if(id==null || id.equals("null")){
			PageModelBtn btn = new PageModelBtn();
			btn.setBtnName(btnName);
			btn.setLink(link);
			if(!imageUrl.equals("null")){
				btn.setImageUrl(imageUrl);
			}
			if(!method.equals("null")){
				btn.setMethod(method);
			}
			btn.setMethod("");
			//*******************************************************************************************************************
			if(!order.equals("null")){
				btn.setOrder(Integer.parseInt(order));
			}			
			List list = super.getService().find(PageModelBtn.class, "pageModel", pageModel);
			int sequence = list.size()+1;
			btn.setOrder(sequence);
			
			//*******************************************************************************************************************
			if(isDisplay.equals("null")){
				btn.setIsDisplay(1);
			}
			//*******************************************************************************************************************
			if(!link.equals("null")){
				btn.setLink(link);
			}
			btn.setLink("");
			//*******************************************************************************************************************
			if(openWinFlag.equals("null")){
				btn.setOpenWinFlag(1);
			}
			//*******************************************************************************************************************
//			if(!pageModelName.equals("null")){
//				String nextPageModelId = pmps.findPageModelByRealName(pageModelName);
//				PageModel nextPageModel = pmps.findPageModelById(nextPageModelId);
//			}
			//***************************
			btn.setNextPageModel(null);
			btn.setPageModel(pageModel);
			super.getService().save(btn);
			
		}else{
			
			if(!pageModelName.equals("请选择...")){
				
				PageModelBtn btn = pms.findPageModelBtnByModifyId(id);
				PageModel oldNextPageModel = btn.getNextPageModel();
				if(!method.equals("null")){
					btn.setMethod(method);
				}
				//*******************************************************************************************************************
				if(!pageModelName.equals("null")){
					String nextPageModelId = pmps.findPageModelByRealName(pageModelName);
					PageModel nextPageModel = pmps.findPageModelById(nextPageModelId);
					btn.setNextPageModel(nextPageModel);
					if(oldNextPageModel!=nextPageModel){
						String pagePath = nextPageModel.getPagePath();
						if(btn.getMethod().equalsIgnoreCase("modifyByPage")){
							pagePath = pagePath+"?dataId=";
						}
						btn.setLink(pagePath); 
					}else{
						if(!link.equals("null")){
							btn.setLink(link);
						}
					}
				}else{
					if(!link.equals("null")){
						btn.setLink(link);
					}
				}
				//*******************************************************************************************************************
				if(!btnName.equals("null")){
					btn.setBtnName(btnName);
				}
				if(!imageUrl.equals("null")){
					btn.setImageUrl(imageUrl);
				}
				//*******************************************************************************************************************
				if(!link.equals("null")){
					//if(!link.equalsIgnoreCase(btn.getLink())){
				//		btn.setLink(link);
					//}
				}
				
				//*******************************************************************************************************************
				if(!order.equals("null")){
					btn.setOrder(Integer.parseInt(order));
				}					
				super.getService().save(btn);
			}else{
				PageModelBtn btn = pms.findPageModelBtnByModifyId(id); 
				if(!btnName.equals("null")){
					btn.setBtnName(btnName);
				}
				if(!method.equals("null")){
					btn.setMethod(method);
				}
				if(!link.equals("null")){
					btn.setLink(link);
				}
				if(!imageUrl.equals("null")){
					btn.setImageUrl(imageUrl);
				}
				if(!order.equals("null")){
					btn.setOrder(Integer.parseInt(order));
				}					
				super.getService().save(btn);
			}
	   }
		js += "{success:'kkkkkk'}";
		response.setCharacterEncoding("gbk");
		try {
			response.getWriter().write(js);
			response.getWriter().flush();
		} catch (IOException e) {
			
			e.printStackTrace();
		}		
		return null;
		
	}
	
	/**
	 * 找到所有的button对应的pagemodel
	 */
	public ActionForward findPageModelBtn(ActionMapping mapping ,ActionForm form,
			HttpServletRequest request, HttpServletResponse response){
		String json = "";
		List model = super.getService().findAll(PageModel.class);
		for(int i=0; i< model.size(); i++){
			PageModel pageModel = (PageModel)model.get(i);
			Long id = pageModel.getId();
			String name = pageModel.getName();
			json += "{\"pageModelId\":"+id+",\"name\":\""+name+"\"},";
		}
		json = "{data:[" + json.substring(0, json.length()-1) + "]}";
		System.out.println("创建用户时,发往前台的module数据： "+json);	
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
	 * @Methods Name toPageModelEditForm
	 * @Create In Dec 8, 2008 By Administrator
	 * @param mapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception ActionForward
	 */
	public ActionForward findSystemMainTableByModule(ActionMapping mapping ,ActionForm form,
			HttpServletRequest request,HttpServletResponse response){
		String fmodulId = request.getParameter("fmodulId");
		Module selectedModule = (Module)super.getService().find(Module.class, fmodulId);
		List<SystemMainTable> systemTables = super.getService().find(SystemMainTable.class, "module", selectedModule);
		List list = new ArrayList();
		for(SystemMainTable  smt : systemTables){
			Map map = new HashMap();
			map.put("id", smt.getId());
			map.put("tableCnName", smt.getTableCnName());
			list.add(map);
		}
		String result = JsonUtil.toJSONString(list);			
		//request.setAttribute("systemTable", systemTables);
		response.setCharacterEncoding("gbk");
		try {
			response.getWriter().write("{success:true,data:"+result+"}");
			response.getWriter().flush();		
		} catch (IOException e) {			
			e.printStackTrace();
		}		
		return null;
	}
	/**
	 * 用来保存相应的pagePanel
	 * @Methods Name newPagePanelOfPageModel
	 * @Create In Dec 17, 2008 By Administrator
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 */ 
	public ActionForward newPagePanelOfPageModel(ActionMapping mapping ,ActionForm form,
			HttpServletRequest request,HttpServletResponse response){
		String pageModelId = request.getParameter("pageModelId");
		String parentPagePanelId = request.getParameter("parentPagePanelId");
		String order = request.getParameter("order");
		//这样就保存了相应的系统主表字段，最后保存相应的实体
		String panelTitle = request.getParameter("panelTitle");
		String panelName = request.getParameter("panelName");
		String systemMainTable = request.getParameter("systemMainTable");
		String panelType = request.getParameter("type");
		PagePanelType pagePanelType = pps.findPagePanelTypeByXtype(panelType);
		SystemMainTable mainTable= pps.findSystemMainTable(systemMainTable);
		
		PagePanel newPagePanel = new PagePanel();
		newPagePanel.setTitle(panelTitle);
		newPagePanel.setName(panelName);
		newPagePanel.setSystemMainTable(mainTable);
		newPagePanel.setXtype(pagePanelType);
		String pagePanelId = ((PagePanel)super.getService().save(newPagePanel)).getId()+"";
		//这段是来保存新建的pageModelPanel
		PageModelPanel obj = new PageModelPanel();
		PagePanel parentPanel = null;
		if ("".equals(parentPagePanelId) || "-100".equals(parentPagePanelId)) {
			obj.setParentPagePanel(null);
		} else {
			parentPanel = pmps.findTemplateItemById(parentPagePanelId);
			obj.setParentPagePanel(parentPanel);
		}
		
		PagePanel pagePanel = pmps.findPagePanelById(pagePanelId);				
		PageModel pageModel = pmps.findPageModelById(pageModelId);
		obj.setPageModel(pageModel);
		obj.setPagePanel(pagePanel);
		obj.setIsDisplay(1);
		obj.setTitleDisplayFlag(1);
		obj.setReadonly(1);
		
		Integer ord = Integer.parseInt(order);
		obj.setOrder(ord);
		pmps.savePageModelPanel(obj);
		PrintWriter pw;
		try {
			pw = response.getWriter();
			pw.write("{success:true}");		
		} catch (IOException e) {
			
			e.printStackTrace();
		}		
		return null;
	}
	
	/**
	 * 找到pageModel所选的module对应的系统主表
	 * @Methods Name newPagePanelOfPageModel
	 * @Create In Dec 17, 2008 By Administrator
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 */
	public ActionForward findSystemMainTable(ActionMapping mapping ,ActionForm form,
			HttpServletRequest request,HttpServletResponse response){
		
		String modelId = request.getParameter("moduleId"); 
		PageModel pageModel = (PageModel)super.getService().find(PageModel.class, modelId);
		List systemTable = pms.findSystemMainTable(pageModel.getModule());				
		String json = "";
		for(int i=0; i< systemTable.size(); i++){
		SystemMainTable mainTable = (SystemMainTable)systemTable.get(i);			
		Long id = mainTable.getId();
		String name = mainTable.getTableCnName();
		json += "{\"systemMainTable\":"+id+",\"name\":\""+name+"\"},";
		}
		json = "{data:[" + json.substring(0, json.length()-1) + "]}";
		System.out.println("创建用户时,发往前台的module数据： "+json);	
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
		
		String PageModelPanelId = request.getParameter("pnode"); 
		PageModelPanel pmp = pmps.findPageModelPanelById(PageModelPanelId);
		PagePanel pagePanel = pmp.getPagePanel();
		
		PagePanelType ppt = (PagePanelType)pagePanel.getXtype();
//		Long pid = pagePanel.getXtype().getId();
//		Long id = ppt.getId();
		String typeName = ppt.getCnName();
		Integer display = pmp.getIsDisplay();
		Integer titleDisplay = pmp.getTitleDisplayFlag();
		Integer readonly = pmp.getReadonly();
		String json = "";
		json += "{\"typeName\":\""+typeName+"\""+",\"display\":"+display+",\"titleDisplay\":"+titleDisplay+",\"readonly\":"+readonly+"}";
		System.out.println("++++++++++++");
		System.out.println(json);
		System.out.println("++++++++++++"); 
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
	
	public ActionForward removePagePanelBtn(ActionMapping mapping ,ActionForm form,
			HttpServletRequest request,HttpServletResponse response){
		String pagePanelBtn = request.getParameter("ids");
		String[] btn = pagePanelBtn.split(",");
		for(int i=0 ; i<btn.length ; i++){
			super.getService().remove(PageModelBtn.class, btn[i]);
		}
		//*************************************************************************************************
		String PageModelId = request.getParameter("modId");
		PageModel pageModel = (PageModel)super.getService().find(PageModel.class, PageModelId);
		List list = super.getService().find(PageModelBtn.class, "pageModel", pageModel);
		PageModelBtn pmb = null;
		for(int i=0;i< list.size();i++){
			pmb = (PageModelBtn)list.get(i);
			pmb.setOrder(i+1);
		}
		super.getService().save(pmb);
		//*************************************************************************************************
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
	
	public ActionForward modifyCheckColumn(ActionMapping mapping ,ActionForm form,
			HttpServletRequest request,HttpServletResponse response){
		String btnId = request.getParameter("id");
		String openWinFlag = request.getParameter("openWinFlag");
		String isDisplay = request.getParameter("isDisplay");
		PageModelBtn pmb = (PageModelBtn)super.getService().find(PageModelBtn.class, btnId);
		pmb.setIsDisplay(Integer.parseInt(isDisplay));
		pmb.setOpenWinFlag(Integer.parseInt(openWinFlag));
		super.getService().save(pmb);
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
		System.out.println("创建用户时,发往前台的module数据： "+json);	
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
		System.out.println("创建用户时,发往前台的module数据： "+json);	
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
		System.out.println("创建用户时,发往前台的module数据： "+json);	
		try {			
		response.setCharacterEncoding("utf-8");
		PrintWriter pw = response.getWriter();	
		pw.write(json);		
		} catch (IOException e) {
		e.printStackTrace();
		}
		return null;
	}
	

	public ActionForward toPageModelList (ActionMapping mapping ,ActionForm form,
			HttpServletRequest request ,HttpServletResponse response ) throws UnsupportedEncodingException{
		
		String json = "[";
		String pmID = request.getParameter("pmID");
		if(StringUtils.isNotBlank(pmID)){
			PageModel pageModel = pmps.findPageModelById(pmID);
			List<PageModelPanelTable> lists = pms.findPageModelPanelTableByModel(pageModel);
			
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
		}
		return null;
		
	}
	public ActionForward toPageModelPanelTableRemove(ActionMapping mapping ,ActionForm form,
			HttpServletRequest request ,HttpServletResponse response ) throws UnsupportedEncodingException{
				String ids = request.getParameter("ids");
				String[] rid=ids.split(","); 
				for(String tableID:rid){
					pms.removePageModelPanelTable(tableID);
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
	public ActionForward findAllPageModel (ActionMapping mapping ,ActionForm form,
			HttpServletRequest request ,HttpServletResponse response ) throws UnsupportedEncodingException{
			List list = pms.findAllPagePanel();
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
		PageModelPanelTable pmpt = null;
		if(ppId!=null&&ppId.trim().length()!=0){
			pmpt = pms.findPageModelPanelTable(ppId);
		}
		if(!Pattern.matches("^\\+?[0-9][0-9]*$", value)){
			if(pmpt.getSubPagePanel()==null){
				value="";
				return null;
			}else{
				value = String.valueOf(pmpt.getSubPagePanel().getId());
			}
		}
		PagePanel pagePanel = pms.findPagePanelById(value);
		List lists = pms.findAllMainTableByPanel(pagePanel);
		String json = "";
		PagePanelTable pagePanelTable = new PagePanelTable();
		for(int i=0; i< lists.size(); i++){
			pagePanelTable=(PagePanelTable) lists.get(i);
			
				Long code = pagePanelTable.getSystemMainTable().getId();
				String name = pagePanelTable.getSystemMainTable().getTableCnName();
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
		PageModelPanelTable pmpt = null;
		if(ppId!=null&&ppId.trim().length()!=0){
			pmpt = pms.findPageModelPanelTable(ppId);
		}
		if(!Pattern.matches("^\\+?[0-9][0-9]*$", value)){
			if(pmpt.getParentPagePanel()==null){
				value="";
				return null;
			}else{
				value = String.valueOf(pmpt.getParentPagePanel().getId());
			}
		}
		PagePanel pagePanel = pms.findPagePanelById(value);
		List lists = pms.findAllMainTableByPanel(pagePanel);
		String json = "";
		PagePanelTable pagePanelTable = new PagePanelTable();
		for(int i=0; i< lists.size(); i++){
			pagePanelTable=(PagePanelTable) lists.get(i);
			
				Long code = pagePanelTable.getSystemMainTable().getId();
				String name = pagePanelTable.getSystemMainTable().getTableCnName();
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
		PageModelPanelTable pmpt = null;
		if(ppId!=null&&ppId.trim().length()!=0){
			pmpt = pms.findPageModelPanelTable(ppId);
		}
		if(!Pattern.matches("^\\+?[0-9][0-9]*$", value)){
			if(pmpt.getSubPanelTable()==null){
				value = "";
				return null;
			}else{
				value = String.valueOf(pmpt.getSubPanelTable().getId());
			}	
		}
		SystemMainTable systemMainTable = pms.findSystemMainTable(value);
		List lists = pms.findAllSystemMainTableColumnByName(systemMainTable.getTableName());
		String json = "";
		SystemMainTableColumn systemMainTableColumn = new SystemMainTableColumn();
		for(int i=0; i< lists.size(); i++){
			systemMainTableColumn=(SystemMainTableColumn) lists.get(i);
			//if(systemMainTableColumn.getForeignTable()!=null){
				Long code = systemMainTableColumn.getId();
				String name = systemMainTableColumn.getColumnCnName();
				json += "{name:\""+name+"\",code:\""+code+"\"},";
			//}			
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
		System.out.println(json);
		
		return null;
		
	}
	public ActionForward findAllForeignCol2 (ActionMapping mapping ,ActionForm form,
			HttpServletRequest request ,HttpServletResponse response ) throws UnsupportedEncodingException{
		request.setCharacterEncoding("GBK");
		String value = HttpUtil.ConverUnicode(request.getParameter("parentPanelTable"));
		String ppId = request.getParameter("id");
		if((value==null||!Pattern.matches("^\\+?[0-9][0-9]*$", value))&&ppId==null){
			return null;
		}
		PageModelPanelTable pmpt = null;
		if(ppId!=null&&ppId.trim().length()!=0){
			pmpt = pms.findPageModelPanelTable(ppId);
		}
		if(!Pattern.matches("^\\+?[0-9][0-9]*$", value)){
			if(pmpt.getParentPanelTable()==null){
				value = "";
				return null;
			}else{
				value = String.valueOf(pmpt.getParentPanelTable().getId());
			}	
		}
		SystemMainTable systemMainTable = pms.findSystemMainTable(value);
		List lists = pms.findAllSystemMainTableColumnByName(systemMainTable.getTableName());
		String json = "";
		SystemMainTableColumn systemMainTableColumn = new SystemMainTableColumn();
		for(int i=0; i< lists.size(); i++){
			systemMainTableColumn=(SystemMainTableColumn) lists.get(i);
			//if(systemMainTableColumn.getForeignTable()!=null){
				Long code = systemMainTableColumn.getId();
				String name = systemMainTableColumn.getColumnCnName();
				json += "{name:\""+name+"\",code:\""+code+"\"},";
			//}			
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
		System.out.println(json);
		
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
		PageModelPanelTable pmpt = null;
		if(ppId!=null){
			pmpt = pms.findPageModelPanelTable(ppId);
		}
		if(!Pattern.matches("^\\+?[0-9][0-9]*$", value)){
			if(pmpt.getParentPanelTable()==null){
				value="";
				return null;
			}else{
				value = String.valueOf(pmpt.getParentPanelTable().getId());
			}
		}
		//SystemMainTableColumn systemMainTableColumn = pms.findSystemMainTablePrimaryKeyColumn(value);
		SystemMainTable systemMainTable = pms.findSystemMainTable(value);
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
		SystemMainTable systemMainTable = pms.findSystemMainTable(value);
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
	public ActionForward toForeignTableSave (ActionMapping mapping ,ActionForm form,
			HttpServletRequest request ,HttpServletResponse response ) throws UnsupportedEncodingException{
				String ppId = HttpUtil.ConverUnicode(request.getParameter("ppId"));
				String price = HttpUtil.ConverUnicode(request.getParameter("price"));
//				PageModel pageModel = pmps.findPageModelById(ppId);
//				List<PageModelPanelTable> lists = pms.findPageModelPanelTableByModel(pageModel);
				PageModelPanelTable pageModelPanelTable = null;
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
							pageModelPanelTable = pms.findPageModelPanelTable(value);
						}
						if(key.equals("id")&&value=="null"){
							pageModelPanelTable = null;
						}
						if(key.equals("subPagePanel")&&!Pattern.matches("^\\+?[0-9][0-9]*$", value)&&!value.equals("")&&value!="null"){
							value = String.valueOf(pageModelPanelTable.getSubPagePanel().getId());
						}
						if(key.equals("subPagePanel")&&(value.equals("")||value=="null")){
							value = "";
						}
						if(key.equals("subPanelTable")&&!Pattern.matches("^\\+?[0-9][0-9]*$", value)&&!value.equals("")&&value!="null"){
							value = String.valueOf(pageModelPanelTable.getSubPanelTable().getId());
						}
						if(key.equals("subPanelTable")&&(value.equals("")||value=="null")){
							value = "";
						}
						if(key.equals("subPanelTableFColumn")&&!Pattern.matches("^\\+?[0-9][0-9]*$", value)&&!value.equals("")&&value!="null"){
							value = String.valueOf(pageModelPanelTable.getSubPanelTableFColumn().getId());
						}
						if(key.equals("subPanelTableFColumn")&&(value.equals("")||value=="null")){
							value = "";
						}
						if(key.equals("parentPagePanel")&&!Pattern.matches("^\\+?[0-9][0-9]*$", value)&&!value.equals("")&&value!="null"){
							value = String.valueOf(pageModelPanelTable.getParentPagePanel().getId());
						}
						if(key.equals("parentPagePanel")&&(value.equals("")||value=="null")){
							value = "";
						}
						if(key.equals("parentPanelTable")&&!Pattern.matches("^\\+?[0-9][0-9]*$", value)&&!value.equals("")&&value!="null"){
							value = String.valueOf(pageModelPanelTable.getParentPanelTable().getId());
						}
						if(key.equals("parentPanelTable")&&(value.equals("")||value=="null")){
							value = "";
						}
						if(key.equals("parentPanelTablePColumn")&&!Pattern.matches("^\\+?[0-9][0-9]*$", value)&&!value.equals("")&&value!="null"){
							value = String.valueOf(pageModelPanelTable.getParentPanelTablePColumn().getId());
						}
						if(key.equals("parentPanelTablePColumn")&&(value.equals("")||value=="null")){
							value = "";
						}
						priceMap.put(key, value);
					}
					priceMap.put("pageModel",ppId);
					pageModelPanelTable = (PageModelPanelTable) BeanUtil.getObject(priceMap,PageModelPanelTable.class);
					pms.savePageModelPanelTable(pageModelPanelTable);
					//System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
				}
				
				return null;
		
	}
	
}	
