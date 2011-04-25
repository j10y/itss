package com.digitalchina.info.appframework.template.web.struts;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.digitalchina.info.appframework.template.entity.DeptMenuTemplate;
import com.digitalchina.info.appframework.template.entity.UserMenu;
import com.digitalchina.info.appframework.template.service.DeptTemplateMenuService;
import com.digitalchina.info.appframework.template.service.SystemTemplateMenuService;
import com.digitalchina.info.appframework.template.service.UserTemplateMenuService;
import com.digitalchina.info.framework.dao.support.Page;
import com.digitalchina.info.framework.exception.ApplicationException;
import com.digitalchina.info.framework.security.service.DepartmentService;
import com.digitalchina.info.framework.util.BeanUtil;
import com.digitalchina.info.framework.util.HttpUtil;
import com.digitalchina.info.framework.web.adapter.struts.BaseDispatchAction;

/**
 * 系统菜单模板管理
 * @Class Name SystemMainTableAction
 * @Author peixf
 * @version 3.0
 * @Create In 2008-7-25
 */
public class UserMenuTemplateAction extends BaseDispatchAction{
	
	private DepartmentService ds = (DepartmentService) getBean("deptService");
	private SystemTemplateMenuService stms = (SystemTemplateMenuService)getBean("systemTemplateMenuService");
	private DeptTemplateMenuService dtms = (DeptTemplateMenuService) getBean("deptTemplateMenuService");
	private UserTemplateMenuService utms = (UserTemplateMenuService)getBean("userTemplateMenuService");
	
	public ActionForward listTemplate(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
	
		int pageNo = HttpUtil.getInt(request, "pageNo", 1);
		String itcode = request.getParameter("itcode");
		String realName = request.getParameter("realName");
		Map params = new HashMap();
		params.put("itcode", itcode);
		params.put("realName", realName);
		
		String userInfo = request.getParameter("userInfo");
		params.put("userInfo", userInfo);
		
		Page page = utms.findUserMenus(params, pageNo, 10);
		request.setAttribute("page", page);
		
		List deptTemplateMenus = dtms.findDeptMenuTemplates();
		request.setAttribute("deptTemplateMenus", deptTemplateMenus);
		
		return mapping.findForward("listTemplate");
	}
	
	public ActionForward templateDetail(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
	
		/*List depts = ds.findDeptAll();
		request.setAttribute("depts", depts);*/
		//List userInfos = this.utms.findUserAsUserAdminRight();
		//request.setAttribute("userInfos", userInfos);
				
		List deptTemplateMenus = dtms.findDeptMenuTemplates();
		request.setAttribute("deptTemplateMenus", deptTemplateMenus);
		
		String id = request.getParameter("id");
		if(StringUtils.isNotBlank(id)){
			UserMenu um = (UserMenu) getService().find(UserMenu.class, id);
			request.setAttribute("detail", um);
		}
		
		return mapping.findForward("detail");
	}

	public ActionForward saveTemplate(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
	
		UserMenu dmt = (UserMenu) BeanUtil.getObject(request, UserMenu.class);
//		begin
		DeptMenuTemplate deptMenuTemplateNew = dmt.getDeptMenuTemplate();
		if(deptMenuTemplateNew==null){
			throw new ApplicationException("请选择部门菜单模板");
		}
		
		if(dmt.getId()!=null){
			UserMenu umOld = (UserMenu) getService().find(UserMenu.class, String.valueOf(dmt.getId()));
			DeptMenuTemplate dmtOld = umOld.getDeptMenuTemplate();
			if(deptMenuTemplateNew.getId().intValue()!= dmtOld.getId().intValue()){
				utms.saveUserDeptTemplateChange(dmt, deptMenuTemplateNew);
			}
		}
		//end
		utms.saveUserMenu(dmt);
		
		return HttpUtil.redirect("userTemplateManage.do?methodCall=listTemplate");
	}

	public ActionForward removeTemplate(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		String[] ids = request.getParameterValues("id");
		utms.removeUserMenu(ids);
		
		return HttpUtil.redirect("userTemplateManage.do?methodCall=listTemplate");
	}
	
	
	
}
