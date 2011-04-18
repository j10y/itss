package com.digitalchina.info.appframework.metadata.web.struts;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.digitalchina.info.appframework.metadata.entity.SystemMainTable;
import com.digitalchina.info.appframework.metadata.entity.SystemTableSetting;
import com.digitalchina.info.appframework.metadata.entity.UserTableSetting;
import com.digitalchina.info.appframework.metadata.service.SystemColumnService;
import com.digitalchina.info.appframework.metadata.service.SystemMainColumnService;
import com.digitalchina.info.appframework.metadata.service.SystemMainTableService;
import com.digitalchina.info.appframework.metadata.service.UserColumnService;
import com.digitalchina.info.framework.exception.ApplicationException;
import com.digitalchina.info.framework.security.entity.Role;
import com.digitalchina.info.framework.security.entity.UserInfo;
import com.digitalchina.info.framework.security.service.SecurityManageService;
import com.digitalchina.info.framework.util.HttpUtil;
import com.digitalchina.info.framework.web.adapter.struts.BaseDispatchAction;

/**
 * 系统可见字段设置action
 * @Class Name SystemTableSettingAction
 * @Author peixf
 * @version 3.0
 * @Create In 2008-7-25
 */
public class UserTableSettingAction extends BaseDispatchAction{
	
	private UserColumnService ucs = (UserColumnService) getBean("userColumnService");
	private SystemColumnService scs = (SystemColumnService) getBean("systemColumnService");
	private SystemMainTableService smts = (SystemMainTableService) getBean("systemMainTableService");
	private SystemMainColumnService smcs = (SystemMainColumnService) getBean("systemMainColumnService");
	private SecurityManageService sms = (SecurityManageService) getBean("securityManageService");
	
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
			settingType = UserTableSetting.LIST;
		}else if(settingTypeStr.equalsIgnoreCase("1")){//列表
			settingType = UserTableSetting.LIST;
		}else if(settingTypeStr.equalsIgnoreCase("2")){ //输入
			settingType = UserTableSetting.INPUT;
		}else if(settingTypeStr.equalsIgnoreCase("5")){ //导出
			settingType = UserTableSetting.EXPORT;
		}
		SystemMainTable smt = (SystemMainTable) getService().find(SystemMainTable.class, smtId);
		
		request.setAttribute("smt", smt);
		
		List<UserTableSetting> stss = ucs.findUserColumnsAll(smt, settingType);//
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
		
		String settingTypeStr = request.getParameter("settingType");
		Integer settingType = null;
		if(StringUtils.isBlank(settingTypeStr)){
			settingType = UserTableSetting.LIST;
		}else if(settingTypeStr.equalsIgnoreCase("1")){//列表
			settingType = UserTableSetting.LIST;
		}else if(settingTypeStr.equalsIgnoreCase("2")){ //输入
			settingType = UserTableSetting.INPUT;
		}else if(settingTypeStr.equalsIgnoreCase("5")){ //导出
			settingType = UserTableSetting.EXPORT;
		}
		
		String mainTableId = request.getParameter("smtId");
		SystemMainTable smt = (SystemMainTable) getService().find(SystemMainTable.class, mainTableId);
		request.setAttribute("smt", smt);
		
		List userTableColumnSettings = this.ucs.findUserColumnsAll(smt, settingType);
		for(int i=0; i<userTableColumnSettings.size(); i++){
			UserTableSetting sts = (UserTableSetting) userTableColumnSettings.get(i);
			Long utsId = sts.getId();
			String isDisplayPara = request.getParameter("isDisplay"+utsId);
			String lengthForPage = request.getParameter("lengthForPage"+utsId);
			String isMustInput = request.getParameter("isMustInput"+utsId);
			String hiddenValue = request.getParameter("hiddenValue"+utsId);
//			StringTokenizer token = new StringTokenizer(isDisplayPara, "|");
//			String itemId = token.nextToken();
//			String trueOrFalse = token.nextToken();
			
			if(isDisplayPara==null){
				isDisplayPara="0";
			}
			sts.setIsDisplay(Integer.valueOf(isDisplayPara));
			sts.setLengthForPage(lengthForPage);
			if(StringUtils.isNotBlank(isMustInput)){
				sts.setIsMustInput(Integer.valueOf(isMustInput));
			}
			if(StringUtils.isNotBlank(hiddenValue)){
				sts.setHiddenValue(hiddenValue);
			}
			//--------------------------
			String orderPara = request.getParameter("order"+utsId);
			sts.setOrder(Integer.valueOf(orderPara));
			getService().save(sts);
			
		}	
		return HttpUtil.redirect("userTableSetting.do?methodCall=list&smtId="+smt.getId()+"&settingType="+settingTypeStr);
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
		this.ucs.saveUserTableSettingColumnSort(smt, settingType, targetOrderFlag, sourceOrderFlags);
		
		
		return HttpUtil.redirect("userTableSetting.do?methodCall=list&smtId="+smt.getId());
	}
	
	
	
	
	
	
	
	
}
