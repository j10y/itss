package com.zsgj.info.appframework.metadata.web.struts;

import java.util.List;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.zsgj.info.appframework.metadata.entity.MatchMode;
import com.zsgj.info.appframework.metadata.entity.SystemMainTable;
import com.zsgj.info.appframework.metadata.entity.SystemTableQuery;
import com.zsgj.info.appframework.metadata.entity.SystemTableQueryColumn;
import com.zsgj.info.appframework.metadata.service.SystemColumnService;
import com.zsgj.info.appframework.metadata.service.SystemMainColumnService;
import com.zsgj.info.appframework.metadata.service.SystemMainTableService;
import com.zsgj.info.appframework.metadata.service.UserColumnService;
import com.zsgj.info.framework.exception.ApplicationException;
import com.zsgj.info.framework.security.entity.UserInfo;
import com.zsgj.info.framework.security.service.SecurityManageService;
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
public class SystemTableQueryAction extends BaseDispatchAction{
	
	private UserColumnService ucs = (UserColumnService) getBean("userColumnService");
	private SystemColumnService scs = (SystemColumnService) getBean("systemColumnService");
	private SystemMainTableService smts = (SystemMainTableService) getBean("systemMainTableService");
	private SystemMainColumnService smcs = (SystemMainColumnService) getBean("systemMainColumnService");
	private SecurityManageService sms = (SecurityManageService) getBean("securityManageService");
	
	public ActionForward list(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		String smtId = request.getParameter("smtId");
		SystemMainTable smt = smts.findSystemMainTable(smtId);
		request.setAttribute("smt", smt);
		
		List sysTableQueries = ucs.findSystemTableQueryAll(smt);
		request.setAttribute("sysTableQueries", sysTableQueries);
		
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
		
		String queryId = request.getParameter("queryId");
		if(StringUtils.isNotBlank(queryId)){
			SystemTableQuery stq = (SystemTableQuery) getService().find(SystemTableQuery.class, queryId);
			request.setAttribute("detail", stq);
		}
		//infoAdmin/sysTableQuery.do?methodCall=list&smtId=7
		return mapping.findForward("detail");
	}
	
	
	public ActionForward saveQuery(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		SystemTableQuery stq = (SystemTableQuery) BeanUtil.getObject(request, SystemTableQuery.class);
		this.ucs.saveSystemTableQuery(stq);
		SystemMainTable smt = stq.getSystemMainTable();
		
		return HttpUtil.redirect("sysTableQuery.do?methodCall=list&smtId="+smt.getId());
	}

	
	public ActionForward remove(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		String smtId = request.getParameter("smtId");
		SystemMainTable smt = smts.findSystemMainTable(smtId);
		request.setAttribute("smt", smt);
		
		String[] ids = request.getParameterValues("id");
		this.ucs.removeSystemTableQuery(ids);
		
		return HttpUtil.redirect("sysTableQuery.do?methodCall=list&smtId="+smt.getId());
	}
	
	public ActionForward listQueryColumn(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		String queryId = request.getParameter("queryId");
		SystemTableQuery stq = (SystemTableQuery) getService().find(SystemTableQuery.class, queryId);
		request.setAttribute("stq", stq);
		List<SystemTableQueryColumn> stqcs = this.ucs.findSystemTableQueryColumn(stq);
		request.setAttribute("sysTableQueryColumns", stqcs);
		
		List matchModes = getService().findAll(MatchMode.class);
		request.setAttribute("matchModes", matchModes);
		
		List userInfos = getService().findAllBy(UserInfo.class, "itcode", false);
		request.setAttribute("userInfos", userInfos);
		
		return mapping.findForward("listColumns");
	}
	
	public ActionForward sortQueryColumn(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		String queryId = request.getParameter("queryId");
		SystemTableQuery stq = (SystemTableQuery) getService().find(SystemTableQuery.class, queryId);
		request.setAttribute("stq", stq);
		
		String targetOrderFlag =  request.getParameter("targetOrderFlag");
		String sourceItemIdstr = request.getParameter("sourceOrderFlags");
		if(StringUtils.isBlank(sourceItemIdstr)){
			throw new ApplicationException("请选择需要移动查询字段");
		}
		sourceItemIdstr = sourceItemIdstr.substring(1);
		String[] sourceOrderFlags = sourceItemIdstr.split("#");
		this.ucs.saveSystemTableQueryColumnSort(stq, targetOrderFlag, sourceOrderFlags);
		request.setAttribute("queryId", queryId);
		
		return HttpUtil.redirect("sysTableQuery.do?methodCall=listQueryColumn&queryId="+queryId);
	}
	
	
	public ActionForward saveAll(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		String queryId = request.getParameter("queryId");
		SystemTableQuery stq = (SystemTableQuery) getService().find(SystemTableQuery.class, queryId);
		request.setAttribute("stq", stq);
		
		List queryColumns = this.ucs.findSystemTableQueryColumn(stq);
		for(int i=0; i<queryColumns.size(); i++){
			SystemTableQueryColumn uts = (SystemTableQueryColumn) queryColumns.get(i);
			Long utsId = uts.getId();
			String isDisplayPara = request.getParameter("isDisplay"+utsId);
			String lengthForPage = request.getParameter("lengthForPage"+utsId);
			String isMustInput = request.getParameter("isMustInput"+utsId);
			String isHiddenItem = request.getParameter("isHiddenItem"+utsId);
			String hiddenValue = request.getParameter("hiddenValue"+utsId);
			String matchMode = request.getParameter("matchMode"+utsId);
			StringTokenizer token = new StringTokenizer(isDisplayPara, "|");
			String itemId = token.nextToken();
			String trueOrFalse = token.nextToken();
			if(isHiddenItem!=null){
				StringTokenizer token1 = new StringTokenizer(isHiddenItem, "|");
				String itemId1 = token1.nextToken();
				String trueOrFalse1 = token1.nextToken();
				uts.setIsHiddenItem(Integer.valueOf(trueOrFalse1));
			}
			uts.setIsDisplay(Integer.valueOf(trueOrFalse));
			uts.setMatchMode(new MatchMode(Long.valueOf(matchMode)));
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
		
		return HttpUtil.redirect("sysTableQuery.do?methodCall=listQueryColumn&queryId="+queryId);
	}
	
	public ActionForward saveToUser(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse httpServletResponse) throws Exception {
		
		String queryId = request.getParameter("queryId");
		SystemTableQuery stq = (SystemTableQuery) getService().find(SystemTableQuery.class, queryId);
		request.setAttribute("stq", stq);
		
		String isAllUserStr = request.getParameter("isAllUser");
		if(StringUtils.isNotBlank(isAllUserStr)){
			if(isAllUserStr.equals("1")){
				this.ucs.saveSystemTableQueryColumnToUser(stq);
			}else{
				String userInfoStr = request.getParameter("userInfo");
				UserInfo user = null;
				if(StringUtils.isNotBlank(userInfoStr)){
					user = (UserInfo) getService().find(UserInfo.class, userInfoStr);
					this.ucs.saveSystemTableQueryColumnToUser(stq, user);
				}
			}
		}
		return HttpUtil.redirect("sysTableQuery.do?methodCall=listQueryColumn&queryId="+queryId);
	}

	
}
