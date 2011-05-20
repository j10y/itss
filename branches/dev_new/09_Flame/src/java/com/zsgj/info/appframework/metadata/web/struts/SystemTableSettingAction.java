package com.zsgj.info.appframework.metadata.web.struts;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.zsgj.info.appframework.metadata.entity.SystemMainTable;
import com.zsgj.info.appframework.metadata.entity.SystemTableSetting;
import com.zsgj.info.appframework.metadata.service.SystemColumnService;
import com.zsgj.info.appframework.metadata.service.SystemMainColumnService;
import com.zsgj.info.appframework.metadata.service.SystemMainTableService;
import com.zsgj.info.appframework.metadata.service.UserColumnService;
import com.zsgj.info.framework.exception.ApplicationException;
import com.zsgj.info.framework.security.entity.Module;
import com.zsgj.info.framework.security.entity.Role;
import com.zsgj.info.framework.security.entity.UserInfo;
import com.zsgj.info.framework.util.BeanUtil;
import com.zsgj.info.framework.util.HttpUtil;
import com.zsgj.info.framework.web.adapter.struts.BaseDispatchAction;
import com.zsgj.info.framework.web.json.HibernateJsonUtil;

/**
 * 系统可见字段设置action
 * @Class Name SystemTableSettingAction
 * @Author peixf
 * @version 3.0
 * @Create In 2008-7-25
 */
public class SystemTableSettingAction extends BaseDispatchAction{
	
	private UserColumnService ucs = (UserColumnService) getBean("userColumnService");
	private SystemColumnService scs = (SystemColumnService) getBean("systemColumnService");
	private SystemMainTableService smts = (SystemMainTableService) getBean("systemMainTableService");
	private SystemMainColumnService smcs = (SystemMainColumnService) getBean("systemMainColumnService");
//	private SecurityManageService sms = (SecurityManageService) getBean("securityManageService");
	
	/**
	 * 系统可见字段
	 * @Methods Name listColumn
	 * @Create In 2008-8-12 By peixf
	 */
	public ActionForward list(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		String smtId = request.getParameter("smtId");
		String settingTypeStr = request.getParameter("settingType");
		Integer settingType = null;
		if(StringUtils.isBlank(settingTypeStr)){
			settingType = SystemTableSetting.LIST;
		}else if(settingTypeStr.equalsIgnoreCase("1")){//列表
			settingType = SystemTableSetting.LIST;
		}else if(settingTypeStr.equalsIgnoreCase("2")){ //输入
			settingType = SystemTableSetting.INPUT;
		}else if(settingTypeStr.equalsIgnoreCase("5")){ //导出
			settingType = SystemTableSetting.EXPORT;
		}
		SystemMainTable smt = (SystemMainTable) getService().find(SystemMainTable.class, smtId);
		request.setAttribute("smt", smt);
		
		List<SystemTableSetting> stss = ucs.findSystemColumns(smt, settingType);//
		request.setAttribute("sysTableSettings", stss);
		
		List userInfos = getService().findAllBy(UserInfo.class, "itcode", false);
		request.setAttribute("userInfos", userInfos);
		
		List roles = getService().findAllBy(Role.class, "name", true);
		request.setAttribute("roles", roles);
		
		return mapping.findForward("listColumns");
	}
	
	public ActionForward saveAll(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		String mainTableId = request.getParameter("smtId");
		SystemMainTable smt = (SystemMainTable) getService().find(SystemMainTable.class, mainTableId);
		
		String settingTypeStr = request.getParameter("settingType");
		Integer settingType = null;
		if(StringUtils.isBlank(settingTypeStr)){
			settingType = SystemTableSetting.LIST;
		}else if(settingTypeStr.equalsIgnoreCase("1")){//列表
			settingType = SystemTableSetting.LIST;
		}else if(settingTypeStr.equalsIgnoreCase("2")){ //输入
			settingType = SystemTableSetting.INPUT;
		}else if(settingTypeStr.equalsIgnoreCase("5")){ //导出
			settingType = SystemTableSetting.EXPORT;
		}
		

		Map<String,String> params = HttpUtil.requestParam2Map(request);
		this.ucs.saveAllSystemTableSettingColumn(smt, settingType, params);

		return HttpUtil.redirect("sysTableSetting.do?methodCall=list&smtId="+smt.getId()+"&settingType="+settingTypeStr);
	}
	
	
	public ActionForward saveToUser(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		String smtId = request.getParameter("smtId");
		String settingTypeStr = request.getParameter("settingType");
		Integer settingType = null;
		if(StringUtils.isBlank(settingTypeStr)){
			settingType = SystemTableSetting.LIST;
		}else if(settingTypeStr.equalsIgnoreCase("1")){//列表
			settingType = SystemTableSetting.LIST;
		}else if(settingTypeStr.equalsIgnoreCase("2")){ //输入
			settingType = SystemTableSetting.INPUT;
		}else if(settingTypeStr.equalsIgnoreCase("5")){ //导出
			settingType = SystemTableSetting.EXPORT;
		}
		SystemMainTable smt = (SystemMainTable) getService().find(SystemMainTable.class, smtId);
		request.setAttribute("smt", smt);
		
		String isAllUserStr = request.getParameter("isAllUser");
		if(StringUtils.isNotBlank(isAllUserStr)){
			if(isAllUserStr.equals("1")){
				this.ucs.saveSystemTableSettingColumnToUser(smt, settingType);
			}else if(isAllUserStr.equals("2")){
//				String role = request.getParameter("role");
				
				this.ucs.saveSystemTableSettingColumnToUser(smt, settingType);
			}else{
				String userInfoStr = request.getParameter("userInfo");
				UserInfo user = null;
				if(StringUtils.isNotBlank(userInfoStr)){
					user = (UserInfo) getService().find(UserInfo.class, userInfoStr);
					this.ucs.saveSystemTableSettingColumnToUser(smt, user, settingType);
				}
			}
		}
		return HttpUtil.redirect("sysTableSetting.do?methodCall=list&smtId="+smt.getId());
	}
	
	public ActionForward sortSettingColumn(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		String smtId = request.getParameter("smtId");
		SystemMainTable smt = (SystemMainTable) getService().find(SystemMainTable.class, smtId, true);
		request.setAttribute("smt", smt);
		
		String settingTypeStr = request.getParameter("settingType");
		Integer settingType = null;
		if(StringUtils.isBlank(settingTypeStr)){
			settingType = SystemTableSetting.LIST;
		}else if(settingTypeStr.equalsIgnoreCase("1")){//列表
			settingType = SystemTableSetting.LIST;
		}else if(settingTypeStr.equalsIgnoreCase("2")){ //输入
			settingType = SystemTableSetting.INPUT;
		}else if(settingTypeStr.equalsIgnoreCase("5")){ //导出
			settingType = SystemTableSetting.EXPORT;
		}
		
		String targetOrderFlag =  request.getParameter("targetOrderFlag");
		String sourceItemIdstr = request.getParameter("sourceOrderFlags");
		if(StringUtils.isBlank(sourceItemIdstr)){
			throw new ApplicationException("请选择需要移动查询字段");
		}
		sourceItemIdstr = sourceItemIdstr.substring(1);
		String[] sourceOrderFlags = sourceItemIdstr.split("#");
		this.ucs.saveSystemTableSettingColumnSort(smt, settingType, targetOrderFlag, sourceOrderFlags);
		
		
		return HttpUtil.redirect("sysTableSetting.do?methodCall=list&smtId="+smt.getId());
	}
	
	public ActionForward toForm(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
	
		String smtId = request.getParameter("id");
		SystemMainTable smt = smts.findSystemMainTable(smtId);
		request.setAttribute("smt", smt);
		
		List modules = getService().findAll(Module.class);
		request.setAttribute("modules", modules);
		
		List columns = scs.findSystemTableColumns(smt);
		request.setAttribute("columns", columns);
		
		List mainColumns = smcs.findSystemMainTableColumns(smt);
		request.setAttribute("mainColumns", mainColumns);
		
		
		return mapping.findForward("form");
	}

	public ActionForward save(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
	
		SystemMainTable smt = (SystemMainTable) BeanUtil.getObject(request, SystemMainTable.class);
		String tableCnName = HttpUtil.ConverUnicode(request.getParameter("tableCnName"));
		smt.setTableCnName(tableCnName);
		this.smts.saveSystemMainTable(smt);

		//HibernateJsonUtil
		String result = HibernateJsonUtil.toJSONString(smt);

		response.setCharacterEncoding("gbk");
		response.getWriter().write("{success:true,data:"+result+"}");
		response.getWriter().flush();		
		return null;
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
		
		String ids = request.getParameter("id");
		String smtId = request.getParameter("smtId");
		String settingType = request.getParameter("settingType");
//		int pageNo = HttpUtil.getInt(request, "pageNo", 1);
		smts.removeSystemTableSetting(ids);
		
		return HttpUtil.redirect("sysTableSetting.do?methodCall=list&smtId="+smtId+"&settingType="+settingType);
	}
	
	
	
	
	
	
	
	
}
