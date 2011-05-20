package com.zsgj.info.appframework.metadata.web.struts;

import java.util.List;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.zsgj.info.appframework.metadata.entity.SystemMainTable;
import com.zsgj.info.appframework.metadata.entity.SystemTableRole;
import com.zsgj.info.appframework.metadata.entity.SystemTableRoleColumn;
import com.zsgj.info.appframework.metadata.service.SystemMainTableService;
import com.zsgj.info.appframework.metadata.service.UserColumnService;
import com.zsgj.info.framework.exception.ApplicationException;
import com.zsgj.info.framework.security.entity.Role;
import com.zsgj.info.framework.security.entity.UserInfo;
import com.zsgj.info.framework.util.BeanUtil;
import com.zsgj.info.framework.util.HttpUtil;
import com.zsgj.info.framework.web.adapter.struts.BaseDispatchAction;

/**
 * 系统主表查询设置
 * @Class Name SystemTableQueryAction
 * @Author peixf
 * @version 3.0
 * @Create In 2008-7-25
 */
public class SystemTableRoleAction extends BaseDispatchAction{
	
	private UserColumnService ucs = (UserColumnService) getBean("userColumnService");
//	private SystemColumnService scs = (SystemColumnService) getBean("systemColumnService");
	private SystemMainTableService smts = (SystemMainTableService) getBean("systemMainTableService");
//	private SystemMainColumnService smcs = (SystemMainColumnService) getBean("systemMainColumnService");
//	private SecurityManageService sms = (SecurityManageService) getBean("securityManageService");
	
	public ActionForward list(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		String smtId = request.getParameter("smtId");
		SystemMainTable smt = smts.findSystemMainTable(smtId);
		request.setAttribute("smt", smt);
		
		List sysTableRoles = ucs.findSystemTableRoleAll(smt);
		request.setAttribute("sysTableRoles", sysTableRoles);
		
		return mapping.findForward("list");
	}
	
	
	public ActionForward toForm(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		String smtId = request.getParameter("smtId");
		SystemMainTable smt = smts.findSystemMainTable(smtId);
		request.setAttribute("smt", smt);
		
		List smtTables = getService().findAll(SystemMainTable.class);
		request.setAttribute("smtTables", smtTables);
		
		String roleId = request.getParameter("sRoleId");
		if(StringUtils.isNotBlank(roleId)){
			SystemTableRole stq = (SystemTableRole) getService().find(SystemTableRole.class, roleId);
			request.setAttribute("detail", stq);
		}
		List roles = getService().findAll(Role.class);
		request.setAttribute("roles", roles);
		
		//infoAdmin/sysTableQuery.do?methodCall=list&smtId=7
		return mapping.findForward("detail");
	}
	
	/**
	 * 保存系统角色可见字段
	 * @Methods Name saveTableRole
	 * @Create In 2008-9-6 By sa
	 * @param mapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception ActionForward
	 */
	public ActionForward saveTableRole(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		SystemTableRole str = (SystemTableRole) BeanUtil.getObject(request, SystemTableRole.class);
		this.ucs.saveSystemTableRole(str);
		SystemMainTable smt = str.getSystemMainTable();
		
		return HttpUtil.redirect("sysTableRole.do?methodCall=list&smtId="+smt.getId());
	}

	/**
	 * 删除系统角色可见字段
	 * @Methods Name remove
	 * @Create In 2008-9-6 By sa
	 * @param mapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception ActionForward
	 */
	public ActionForward remove(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		String smtId = request.getParameter("smtId");
		SystemMainTable smt = smts.findSystemMainTable(smtId);
		request.setAttribute("smt", smt);
		
		String[] ids = request.getParameterValues("id");
		this.ucs.removeSystemTableRole(ids);
		
		return HttpUtil.redirect("sysTableRole.do?methodCall=list&smtId="+smt.getId());
	}
	
	/**
	 * 获取系统角色可见字段模板的所有可见字段
	 * @Methods Name listTableRoleColumn
	 * @Create In 2008-9-6 By sa
	 * @param mapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception ActionForward
	 */
	public ActionForward listTableRoleColumn(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		String queryId = request.getParameter("sRoleId");
		SystemTableRole str = (SystemTableRole) getService().find(SystemTableRole.class, queryId);
		request.setAttribute("str", str);
		List<SystemTableRoleColumn> stqcs = this.ucs.findSystemTableRoleColumn(str);
		request.setAttribute("sysTableRoleColumns", stqcs);
		
		List userInfos = getService().findAllBy(UserInfo.class, "itcode", false);
		request.setAttribute("userInfos", userInfos);
		
		return mapping.findForward("listColumns");
	}
	
	/**
	 * 给系统角色可见字段设置排序；
	 * @Methods Name sortTableRoleColumn
	 * @Create In 2008-9-6 By sa
	 * @param mapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception ActionForward
	 */
	public ActionForward sortTableRoleColumn(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		String sRoleId = request.getParameter("sRoleId");
		SystemTableRole str = (SystemTableRole) getService().find(SystemTableRole.class, sRoleId);
		request.setAttribute("str", str);

		String settingTypeStr = request.getParameter("settingType");
		Integer settingType = null;
		if(StringUtils.isBlank(settingTypeStr)){
			settingType = SystemTableRole.LIST;
		}else if(settingTypeStr.equalsIgnoreCase("1")){//列表
			settingType = SystemTableRole.LIST;
		}else if(settingTypeStr.equalsIgnoreCase("2")){ //输入
			settingType = SystemTableRole.INPUT;
		}else if(settingTypeStr.equalsIgnoreCase("5")){ //导出
			settingType = SystemTableRole.EXPORT;
		}
		
		String targetOrderFlag =  request.getParameter("targetOrderFlag");
		String sourceItemIdstr = request.getParameter("sourceOrderFlags");
		if(StringUtils.isBlank(sourceItemIdstr)){
			throw new ApplicationException("请选择需要移动查询字段");
		}
		sourceItemIdstr = sourceItemIdstr.substring(1);
		String[] sourceOrderFlags = sourceItemIdstr.split("#");
		this.ucs.saveSystemTableRoleColumnSort(str, settingType, targetOrderFlag, sourceOrderFlags);
		request.setAttribute("sRoleId", sRoleId);
		
		return HttpUtil.redirect("sysTableRole.do?methodCall=listTableRoleColumn&sRoleId="+sRoleId);
	}
	
	/**
	 * 保存所有角色可见字段
	 * @Methods Name saveAll
	 * @Create In 2008-9-6 By sa
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
		
		String queryId = request.getParameter("sRoleId");
		SystemTableRole stq = (SystemTableRole) getService().find(SystemTableRole.class, queryId);
		request.setAttribute("stq", stq);
		
		List queryColumns = this.ucs.findSystemTableRoleColumn(stq);
		for(int i=0; i<queryColumns.size(); i++){
			SystemTableRoleColumn uts = (SystemTableRoleColumn) queryColumns.get(i);
			Long utsId = uts.getId();
			String isDisplayPara = request.getParameter("isDisplay"+utsId);
			String lengthForPage = request.getParameter("lengthForPage"+utsId);
			String isMustInput = request.getParameter("isMustInput"+utsId);
			String hiddenValue = request.getParameter("hiddenValue"+utsId);
			StringTokenizer token = new StringTokenizer(isDisplayPara, "|");
//			String itemId = token.nextToken();
			String trueOrFalse = token.nextToken();
			uts.setIsDisplay(Integer.valueOf(trueOrFalse));
			uts.setLengthForPage(lengthForPage);
			if(StringUtils.isNotBlank(isMustInput)){
				uts.setIsMustInput(Integer.valueOf(isMustInput));
			}
			if(StringUtils.isNotBlank(hiddenValue)){
				uts.setHiddenValue(hiddenValue);
			}
			//--------------------------
			String orderPara = request.getParameter("order"+utsId);
			if(StringUtils.isNotBlank(orderPara)){
				uts.setOrder(Integer.valueOf(orderPara));
			}else{
				uts.setOrder(i+1);
			}
			
			getService().save(uts);
			
		}
		
		return HttpUtil.redirect("sysTableRole.do?methodCall=listTableRoleColumn&sRoleId="+queryId);
	}
	
	/**
	 * 保存系统角色可见字段到指定角色的所有用户
	 * @Methods Name saveToUser
	 * @Create In 2008-9-5 By sa
	 * @param mapping
	 * @param actionForm
	 * @param request
	 * @param httpServletResponse
	 * @return
	 * @throws Exception ActionForward
	 */
	public ActionForward saveToUser(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse httpServletResponse) throws Exception {
		
		String sRoleId = request.getParameter("sRoleId");
		SystemTableRole str = (SystemTableRole) getService().find(SystemTableRole.class, sRoleId);
		request.setAttribute("str", str);
		
		this.ucs.saveSystemTableRoleColumnToUser(str);
		
		
		return HttpUtil.redirect("sysTableRole.do?methodCall=listTableRoleColumn&sRoleId="+sRoleId);
	}

	
}
