package com.digitalchina.itil.require.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.digitalchina.info.appframework.metadata.entity.SystemMainTable;
import com.digitalchina.info.appframework.metadata.service.SystemColumnService;
import com.digitalchina.info.appframework.metadata.service.SystemMainColumnService;
import com.digitalchina.info.appframework.metadata.service.SystemMainTableService;
import com.digitalchina.info.framework.security.entity.Module;
import com.digitalchina.info.framework.util.BeanUtil;
import com.digitalchina.info.framework.util.HttpUtil;
import com.digitalchina.info.framework.web.adapter.struts.BaseDispatchAction;
import com.digitalchina.itil.require.service.ReqTableService;
/**
 * 需求主表
 * @Class Name ReqTableAction
 * @Author lee
 * @Create In Jun 22, 2009
 */
public class ReqTableAction extends BaseDispatchAction{
	ReqTableService rts = (ReqTableService) getBean("reqTableService");
	SystemColumnService scs = (SystemColumnService) getBean("systemColumnService");
	SystemMainTableService smts = (SystemMainTableService) getBean("systemMainTableService");
	SystemMainColumnService smcs = (SystemMainColumnService) getBean("systemMainColumnService");
	/**
	 * 获取需求主表信息
	 * @Methods Name toForm
	 * @Create In Jun 22, 2009 By lee
	 * @param mapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception ActionForward
	 */
	public ActionForward toForm(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
	
		String smtId = request.getParameter("id");
		SystemMainTable smt = smts.findSystemMainTable(smtId);
		request.setAttribute("reqSmt", smt);
		
		List columns = scs.findSystemTableColumns(smt);
		request.setAttribute("columns", columns);
		
		List mainColumns = smcs.findSystemMainTableColumns(smt);
		request.setAttribute("mainColumns", mainColumns);
		
		return mapping.findForward("form");
	}
	/**
	 * 保存用户自定义需求主表
	 * @Methods Name saveTable
	 * @Create In Jun 22, 2009 By lee
	 * @param mapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception ActionForward
	 */
	public ActionForward saveTable(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		SystemMainTable smt = (SystemMainTable) BeanUtil.getObject(request, SystemMainTable.class);
		SystemMainTable curSmt = rts.saveSystemMainTable(smt);
		return HttpUtil.redirect("reqTableAction.do?methodCall=toForm&id="+curSmt.getId()); 
	}
	/**
	 * 发布用户自定义需求主表
	 * @Methods Name deployTable
	 * @Create In Jun 22, 2009 By lee
	 * @param mapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception ActionForward
	 */
	public ActionForward deployTable(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String tableId = request.getParameter("tableId");
		SystemMainTable smt = smts.findSystemMainTable(tableId);
		rts.saveSystemMainTableDeploy(smt);
		SystemMainTable smtEvent = rts.findUserTableEvent(smt);
		if(smtEvent!=null){
			rts.saveSystemMainTableDeploy(smtEvent);
		}else{
			smtEvent = rts.saveEventTableByMainTable(smt);
			rts.saveSystemMainTableDeploy(smtEvent);
		}
		return HttpUtil.redirect("reqTableAction.do?methodCall=toForm&id="+tableId); 
	}
}
