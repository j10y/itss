package com.zsgj.info.appframework.pagemodel.web.struts;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.zsgj.info.appframework.metadata.entity.SystemMainTable;
import com.zsgj.info.appframework.metadata.entity.TableSettingType;
import com.zsgj.info.appframework.pagemodel.entity.PageModel;
import com.zsgj.info.appframework.pagemodel.entity.PageModelPanelTable;
import com.zsgj.info.appframework.pagemodel.service.PageModelService;
import com.zsgj.info.framework.security.entity.Module;
import com.zsgj.info.framework.web.adapter.struts.BaseDispatchAction;

public class PageModelPanelTableAction extends BaseDispatchAction{
	
//	private SystemColumnService scs = (SystemColumnService) getBean("systemColumnService");
//	private SystemMainTableService smts = (SystemMainTableService) getBean("systemMainTableService");
//	private SystemMainColumnService smcs = (SystemMainColumnService) getBean("systemMainColumnService");
//	private SecurityManageService sms = (SecurityManageService) getBean("securityManageService");
	private PageModelService pms = (PageModelService) getBean("pageModelService");
//	private PagePanelService pps = (PagePanelService) getBean("pagePanelService");
//	private PageModelPanelService pmps = (PageModelPanelService) getBean("pageModelPanelService");
//	private CustomerTableService cts = (CustomerTableService) getBean("customerTableService");
	
	public ActionForward toForm(ActionMapping mapping ,ActionForm form ,
			HttpServletRequest request,	HttpServletResponse response){
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
		
		List sysMainTables = getService().findAll(SystemMainTable.class);
		request.setAttribute("sysMainTables", sysMainTables);
		
		List modules = getService().findAll(Module.class);
		request.setAttribute("modules", modules);
		
		
		return mapping.findForward("pageModelEditForm");
	}

}
