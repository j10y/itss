package com.digitalchina.info.appframework.metadata.web.action;

import java.util.List;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.digitalchina.info.appframework.metadata.entity.SystemMainTable;
import com.digitalchina.info.appframework.metadata.entity.SystemTableSetting;
import com.digitalchina.info.appframework.metadata.entity.UserTableSetting;
import com.digitalchina.info.appframework.metadata.service.MetaDataService;
import com.digitalchina.info.framework.context.UserContext;
import com.digitalchina.info.framework.security.entity.UserInfo;
import com.digitalchina.info.framework.security.service.SecurityManageService;
import com.digitalchina.info.framework.util.HttpUtil;
import com.digitalchina.info.framework.web.adapter.struts.BaseDispatchAction;

/**
 * 用户表字段显示与排序方式设置action
 * @Class Name UserTableSettingAction
 * @Author peixf
 * @Create In 2008-3-28
 */
public class UserTableSettingAction extends BaseDispatchAction{
	private MetaDataService sds = (MetaDataService) getBean("metaDataService");
	private SecurityManageService sms = (SecurityManageService) getBean("securityManageService");
	
	
	
	public ActionForward setColumnOrder(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse httpServletResponse) throws Exception {
		String settingTypeStr = request.getParameter("settingType");
		Integer settingType = null;
		if(settingTypeStr==null){
			settingType = SystemTableSetting.LIST;
		}else{
			settingType = Integer.valueOf(settingTypeStr);
		}
		String mainTableId = request.getParameter("mainTableId");
		SystemMainTable systemMainTable = sds.findSystemMainTable(mainTableId);
		List sysTableSettings = sds.findSysTableSettingColumns(systemMainTable, settingType);
		
		
		request.setAttribute("sysTableSettings", sysTableSettings);
		request.setAttribute("sysMainTable", systemMainTable);
		request.setAttribute("settingType", settingType);
		
		List userInfos = sds.findUserInfosOrderByName();
		request.setAttribute("userInfos", userInfos);
		
		return mapping.findForward("sysTableSettings");
		
	}
	
	public ActionForward initColumnOrderToSysTableSetting(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse httpServletResponse) throws Exception {
		
		String mainTableId = request.getParameter("mainTableId");
		SystemMainTable systemMainTable = sds.findSystemMainTable(mainTableId);
		
		String isAllUser = request.getParameter("isAllUser");
		String userInfo = request.getParameter("userInfo");
		
		sds.initColumnsToSysTableSetting(systemMainTable);
		
		request.setAttribute("sysMainTable", systemMainTable);
		
		return HttpUtil.redirect("userTableSet.do?methodCall=setColumnOrder&mainTableId="+mainTableId);
		
	}
	
	public ActionForward saveSysTableColumnSetting(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse httpServletResponse) throws Exception {
		
		String settingType = request.getParameter("settingType");
		Integer settingTypeInteger = null;
		if(StringUtils.isNotBlank(settingType) ){
			if(settingType.equalsIgnoreCase("1")){
				settingTypeInteger = UserTableSetting.LIST;
			}
			else if(settingType.equalsIgnoreCase("2")){
				settingTypeInteger = UserTableSetting.INPUT;
			}
		}
		String mainTableId = request.getParameter("mainTableId");
		SystemMainTable systemMainTable = sds.findSystemMainTable(mainTableId);
		
		List userTableColumnSettings = sds.findSysTableSettingColumns(systemMainTable, settingTypeInteger);
		for(int i=0; i<userTableColumnSettings.size(); i++){
			SystemTableSetting sts = (SystemTableSetting) userTableColumnSettings.get(i);
			Long utsId = sts.getId();
			String isDisplayPara = request.getParameter("isDisplay"+utsId);
			StringTokenizer token = new StringTokenizer(isDisplayPara, "|");
			String itemId = token.nextToken();
			String trueOrFalse = token.nextToken();
			sts.setIsDisplay(Integer.valueOf(trueOrFalse));
			//--------------------------
			String orderPara = request.getParameter("order"+utsId);
			sts.setOrder(Integer.valueOf(orderPara));
			sds.saveSystemTableSetting(sts);
			
		}	
		return HttpUtil.redirect("userTableSet.do?methodCall=setColumnOrder&mainTableId="+mainTableId+"&settingType="+settingType);
	}
	
	public ActionForward synchrSysColumnToUserTableSetting(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse httpServletResponse) throws Exception {
		
		String mainTableId = request.getParameter("mainTableId");
		SystemMainTable systemMainTable = sds.findSystemMainTable(mainTableId);
		
		String settingTypeToUser = request.getParameter("settingTypeToUser");
		Integer settingType = null;
		if(StringUtils.isNotBlank(settingTypeToUser) ){
			if(settingTypeToUser.equalsIgnoreCase("1")){
				settingType = UserTableSetting.LIST;
			}
			else if(settingTypeToUser.equalsIgnoreCase("2")){
				settingType = UserTableSetting.INPUT;
			}
		}
		
		String isAllUserStr = request.getParameter("isAllUser");
		boolean isAllUser = false;
		if(isAllUserStr.equalsIgnoreCase("1")){
			isAllUser = true;
		}
		
		String userInfoStr = request.getParameter("userInfo");
		UserInfo assignedUser = null;
		if(userInfoStr!=null&& !userInfoStr.equals("")){
			assignedUser = (UserInfo) getService().find(UserInfo.class, userInfoStr);
		}
		
		sds.saveForSynchrSysColumnToUserTableSetting(systemMainTable, settingType, isAllUser, assignedUser);
		
		return HttpUtil.redirect("userTableSet.do?methodCall=setColumnOrder&mainTableId="+mainTableId+"&settingType="+settingType);
	}
	
	
	/**
	 * 增加某一个扩展字段后，给指定用户增加用户字段设置信息
	 * @Methods Name setUserTable
	 * @Create In 2008-4-10 By peixf
	 * @param mapping
	 * @param actionForm
	 * @param request
	 * @param httpServletResponse
	 * @return
	 * @throws Exception ActionForward
	 */
	public ActionForward setUserTable(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse httpServletResponse) throws Exception {
		
		UserInfo userInfo = UserContext.getUserInfo();
		String mainTableId = request.getParameter("mainTableId");
		SystemMainTable systemMainTable = sds.findSystemMainTable(mainTableId);
		String header = request.getHeader("referer");
		sds.saveNewColumnToUserTableSetting(userInfo, systemMainTable);
		
		return HttpUtil.redirect("columnsDef.do?methodCall=listAllColumns&mainTableId="+mainTableId);
	}
	
	/**
	 * 用户获取关于某一列表（主表）的所以用户字段设置信息
	 * @Methods Name listUserTableColumnSettings
	 * @Create In 2008-4-10 By peixf
	 * @param mapping
	 * @param actionForm
	 * @param request
	 * @param httpServletResponse
	 * @return
	 * @throws Exception ActionForward
	 */
	public ActionForward listUserTableColumnSettings(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse httpServletResponse) throws Exception {
		
//		String tableName = request.getParameter("tableName");
//		SystemMainTable systemMainTable = sds.findSystemMainTableByName(tableName);
//		Long  mainTableId = systemMainTable.getId();
		
		String settingType = request.getParameter("settingType");
		Integer settingTypeInteger = null;
		if(StringUtils.isNotBlank(settingType) ){
			if(settingType.equalsIgnoreCase("list")){
				settingTypeInteger = UserTableSetting.LIST;
			}
			else if(settingType.equalsIgnoreCase("input")){
				settingTypeInteger = UserTableSetting.INPUT;
			}
		}
		request.setAttribute("settingType", settingType);//转发参数给页面
		
		UserInfo userInfo = UserContext.getUserInfo();
		String mainTableId = request.getParameter("mainTableId");
		SystemMainTable systemMainTable = sds.findSystemMainTable(mainTableId);
		request.setAttribute("mainTableId", mainTableId);
		
		List userTableColumnSettings = sds.findUserTableSetting(userInfo, systemMainTable, settingTypeInteger);
		request.setAttribute("userTableColumnSettings", userTableColumnSettings);
		
		return mapping.findForward("list");
	}
	
	/**
	 * 当前用户设置在某一个列表界面打开字段显示设置页面，设置自己可见的字段及排序
	 * @Methods Name saveUserTableColumnSettings
	 * @Create In 2008-4-10 By peixf
	 * @param mapping
	 * @param actionForm
	 * @param request
	 * @param httpServletResponse
	 * @return
	 * @throws Exception ActionForward
	 */
	public ActionForward saveUserTableColumnSettings(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse httpServletResponse) throws Exception {
		
		String settingType = request.getParameter("settingType");
		Integer settingTypeInteger = null;
		if(StringUtils.isNotBlank(settingType) ){
			if(settingType.equalsIgnoreCase("list")){
				settingTypeInteger = UserTableSetting.LIST;
			}
			else if(settingType.equalsIgnoreCase("input")){
				settingTypeInteger = UserTableSetting.INPUT;
			}
		}
		
		UserInfo userInfo = UserContext.getUserInfo();
		String mainTableId = request.getParameter("mainTableId");
		SystemMainTable systemMainTable = sds.findSystemMainTable(mainTableId);
		
		List userTableColumnSettings = sds.findUserTableSetting(userInfo, systemMainTable, settingTypeInteger);
		for(int i=0; i<userTableColumnSettings.size(); i++){
			UserTableSetting uts = (UserTableSetting) userTableColumnSettings.get(i);
			Long utsId = uts.getId();
			String isDisplayPara = request.getParameter("isDisplay"+utsId);
			StringTokenizer token = new StringTokenizer(isDisplayPara, "|");
			String itemId = token.nextToken();
			String trueOrFalse = token.nextToken();
			uts.setIsDisplay(Integer.valueOf(trueOrFalse));
			//--------------------------
			String orderPara = request.getParameter("order"+utsId);
			uts.setOrder(Integer.valueOf(orderPara));
			sds.saveUserTableSetting(uts);
			
		}
		
		return HttpUtil.redirect("userTableSet.do?methodCall=listUserTableColumnSettings&settingType="+settingType+"&mainTableId="+mainTableId);
	}
	/**
	 * 根据系统主表名称查找系统主表Id
	 * @Methods Name findSysTableId
	 * @Create In Jul 15, 2008 By itnova
	 * @param mapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception ActionForward
	 */
	public ActionForward findSysTableId(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String tableName = request.getParameter("tableName");
		SystemMainTable smt = sds.findSystemMainTableByName(tableName);
		Long  mainTableId = smt.getId();
		String result = ""+mainTableId+"";
		request.setAttribute("sysMainTable", smt);
		
		response.setCharacterEncoding("gbk");
		response.getWriter().write(result);
		response.getWriter().flush();
		return null;
	}
	
}
