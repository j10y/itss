package com.digitalchina.itil.require.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.NumberFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.commons.lang.StringUtils;

import com.digitalchina.info.appframework.metadata.MetaDataManager;
import com.digitalchina.info.appframework.metadata.entity.SystemMainTable;
import com.digitalchina.info.appframework.pagemodel.autocreat.servlet.CoderForList;
import com.digitalchina.info.appframework.pagemodel.entity.PagePanel;
import com.digitalchina.info.appframework.pagemodel.entity.PagePanelColumn;
import com.digitalchina.info.appframework.pagemodel.service.PagePanelColumnService;
import com.digitalchina.info.appframework.pagemodel.service.PagePanelService;
import com.digitalchina.info.framework.context.ContextHolder;
import com.digitalchina.info.framework.context.UserContext;
import com.digitalchina.info.framework.dao.support.Page;
import com.digitalchina.info.framework.exception.ApplicationException;
import com.digitalchina.info.framework.security.entity.UserInfo;
import com.digitalchina.info.framework.util.DateUtil;
import com.digitalchina.info.framework.util.HttpUtil;
import com.digitalchina.info.framework.web.adapter.struts2.BaseAction;
import com.digitalchina.itil.config.extlist.entity.SRExpendPlan;
import com.digitalchina.itil.config.extlist.entity.SRIncomePlan;
import com.digitalchina.itil.require.entity.BusinessAccount;
import com.digitalchina.itil.require.entity.RealIncome;
import com.digitalchina.itil.require.entity.RealPayment;
import com.digitalchina.itil.require.entity.SpecialRequirement;
import com.digitalchina.itil.require.entity.UpDatePlan;
import com.digitalchina.itil.require.entity.UpDatePlanEvent;
import com.digitalchina.itil.require.service.BusinessAccountService;

/**
 * �������Action
 * @Class Name BuinessAccountAction
 * @Author lee
 * @Create In Sep 8, 2009
 */
public class BusinessAccountAction extends BaseAction{
	BusinessAccountService baService = (BusinessAccountService) ContextHolder.getBean("businessAccountService");
	PagePanelService pps = (PagePanelService) getBean("pagePanelService");
	PagePanelColumnService ppcs = (PagePanelColumnService) getBean("pagePanelColumnService");
	MetaDataManager metaDataManager = (MetaDataManager) getBean("metaDataManager");

	/**
	 * ��ȡδ����ո���ƻ�������
	 * @Methods Name queryRequireByName
	 * @Create In Sep 9, 2009 By lee
	 * @return String
	 */
	public String  queryRequireByName(){
		HttpServletRequest request = super.getRequest();
		HttpServletResponse response = super.getResponse();
		String panelName = "panel_businessAccount_list";//request.getParameter("panelName");
		String json="";
		String name= request.getParameter("name");
		if(name==null){
			name = "";
		}
		int start = HttpUtil.getInt(super.getRequest(), "start", 0);
		int pageNo = start / pageSize + 1;
		String requireId= request.getParameter("requireId");
		if(requireId==null){
			requireId = "";
		}
	    Page page = baService.findUnFinshedRequire(name,requireId , pageNo, 10	);
		Long total = page.getTotalCount();
		List queryList = page.list();
		PagePanel panel = pps.findPagePanel(panelName);
		List<PagePanelColumn> columns = ppcs.findColumnByPanel(panel);
		SystemMainTable smt = (SystemMainTable) getService().find(SystemMainTable.class, panel.getSystemMainTable().getId().toString());
		String tableName = "SpecialRequirement";//smt.getName();
		List<Map<String, Object>> listData = metaDataManager.getEntityMapDataForList(SpecialRequirement.class, queryList,tableName);
		json = CoderForList.encode(columns, listData, total);
		response.setContentType("text/plain");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out;
		try {
			out = response.getWriter();
			out.println(json);
			out.flush();
			out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;	
	}
	
	public String  queryFinishedRequireByName(){
		HttpServletRequest request = super.getRequest();
		HttpServletResponse response = super.getResponse();
		String panelName = "panel_businessAccount_list";//request.getParameter("panelName");
		String json="";
		String name= request.getParameter("name");
		if(name==null){
			name = "";
		}
		int start = HttpUtil.getInt(super.getRequest(), "start", 0);
		int pageNo = start / pageSize + 1;
		String requireId= request.getParameter("requireId");
		if(requireId==null){
			requireId = "";
		}
	    Page page = baService.findFinshedRequire(name,requireId , pageNo, 10);
		Long total = page.getTotalCount();
		List queryList = page.list();
		PagePanel panel = pps.findPagePanel(panelName);
		List<PagePanelColumn> columns = ppcs.findColumnByPanel(panel);
		SystemMainTable smt = (SystemMainTable) getService().find(SystemMainTable.class, panel.getSystemMainTable().getId().toString());
		String tableName = "SpecialRequirement";//smt.getName();
		List<Map<String, Object>> listData = metaDataManager.getEntityMapDataForList(SpecialRequirement.class, queryList,tableName);
		json = CoderForList.encode(columns, listData, total);
		response.setContentType("text/plain");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out;
		try {
			out = response.getWriter();
			out.println(json);
			out.flush();
			out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;	
	}
	
	/**
	 * ��ȡ�տ�ƻ���Ϣ
	 * @Methods Name getIncomePlanInfo
	 * @Create In Sep 8, 2009 By lee
	 * @return String
	 */
	public String getIncomePlanInfo(){
		HttpServletRequest request = super.getRequest();
		HttpServletResponse response = super.getResponse();
		String dataId= request.getParameter("dataId");
		SpecialRequirement specialRequire = (SpecialRequirement) this.getService().find(SpecialRequirement.class, dataId, true);
		NumberFormat format = NumberFormat.getCurrencyInstance();
		format.setParseIntegerOnly(true);
		String json = "";
		List<SRIncomePlan> incomePlans = this.getService().find(SRIncomePlan.class, "specialRequire", specialRequire);
		for(SRIncomePlan plan : incomePlans){
			UpDatePlan upDatePlan = (UpDatePlan) this.getService().findUnique(UpDatePlan.class, "incomePlan", plan);
			String str = "name:'" + plan.getName()+"',desc:'"+plan.getDescn()+"',id:'"+plan.getId()+"',";
			if(upDatePlan!=null){//����ƻ������£������¼ƻ�����Ϣ��ʾ
				str += "startDate:'"+DateUtil.convertDateToString(upDatePlan.getStartDate())+"',";
				str += "endDate:'"+DateUtil.convertDateToString(upDatePlan.getEndDate())+"',";
				str += "money:'"+format.format(upDatePlan.getMoney())+"',";
			}else{//����ƻ�δ�����£���ԭ�ƻ�����Ϣ��ʾ
				str += "startDate:'"+DateUtil.convertDateToString(plan.getStartDate())+"',";
				str += "endDate:'"+DateUtil.convertDateToString(plan.getEndDate())+"',";
				str += "money:'"+format.format(plan.getMoney())+"',";
			}
			//�õ������Ϣ
			str += "balance:'"+format.format(baService.getIncomeBalanceByPlanId(plan.getId().toString()))+"'";
			str = "{"+str+"},";
			json = json+str;
		}
		
	   if(json.endsWith(",")){
		   json = json.substring(0,json.length()-1);
	   }
	   String returnStr = "{success:true,rowCount:'"+incomePlans.size()+"',data:["+json+"]}";
		response.setContentType("text/plain");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out;
		try {
			out = response.getWriter();
			out.println(returnStr);
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * ��ȡ����ƻ���Ϣ
	 * @Methods Name getPaymentPlanInfo
	 * @Create In Sep 9, 2009 By lee
	 * @return String
	 */
	public String getPaymentPlanInfo(){
		HttpServletRequest request = super.getRequest();
		HttpServletResponse response = super.getResponse();
		String dataId= request.getParameter("dataId");
		SpecialRequirement specialRequire = (SpecialRequirement) this.getService().find(SpecialRequirement.class, dataId, true);
		NumberFormat format = NumberFormat.getCurrencyInstance();
		format.setParseIntegerOnly(true);
		String json = "";
		List<SRExpendPlan> expendPlans = this.getService().find(SRExpendPlan.class, "specialRequire", specialRequire);
		for(SRExpendPlan plan : expendPlans){
			UpDatePlan upDatePlan = (UpDatePlan) this.getService().findUnique(UpDatePlan.class, "expendPlan", plan);
			String str = "name:'" + plan.getName()+"',desc:'"+plan.getDescn()+"',id:'"+plan.getId()+"',";
			if(upDatePlan!=null){//����ƻ������£������¼ƻ�����Ϣ��ʾ
				str += "startDate:'"+DateUtil.convertDateToString(upDatePlan.getStartDate())+"',";
				str += "endDate:'"+DateUtil.convertDateToString(upDatePlan.getEndDate())+"',";
				str += "money:'"+format.format(upDatePlan.getMoney())+"',";
			}else{//����ƻ�δ�����£���ԭ�ƻ�����Ϣ��ʾ
				str += "startDate:'"+DateUtil.convertDateToString(plan.getStartDate())+"',";
				str += "endDate:'"+DateUtil.convertDateToString(plan.getEndDate())+"',";
				str += "money:'"+format.format(plan.getMoney())+"',";
			}
			//�õ������Ϣ
			str += "balance:'"+format.format(baService.getExpendBalanceByPlanId(plan.getId().toString()))+"'";
			str = "{"+str+"},";
			json = json+str;
		}
		
	   if(json.endsWith(",")){
		   json = json.substring(0,json.length()-1);
	   }
	   String returnStr = "{success:true,rowCount:'"+expendPlans.size()+"',data:["+json+"]}";
		response.setContentType("text/plain");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out;
		try {
			out = response.getWriter();
			out.println(returnStr);
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * �����տ�ƻ�
	 * @Methods Name updateIncomePlan
	 * @Create In Sep 9, 2009 By lee
	 * @return String
	 */
	public String updateIncomePlan() {
		HttpServletRequest request = super.getRequest();
		HttpServletResponse response = super.getResponse();
		PrintWriter printWriter = null;
		
		String jsonString="";
		//���µĽ��
		String id = HttpUtil.getString(request, "id", "");
		String money = HttpUtil.getString(request, "money", "0");
		String startDate = HttpUtil.getString(request, "startDate", "");
		String endDate = HttpUtil.getString(request, "endDate", "");
		try {
			printWriter = response.getWriter();
			baService.saveIncomeUpdatePlan(id, money, startDate, endDate);
			jsonString = "{success:true}";
		} catch (Exception e) {
			e.printStackTrace();
			jsonString = "{success:false,message:'����������'}";
		}
		printWriter.write(jsonString);
		printWriter.flush();
		printWriter.close();
		return null;
	}
	public String updateExpendPlan(){
		HttpServletRequest request = super.getRequest();
		HttpServletResponse response = super.getResponse();
		PrintWriter printWriter = null;
		
		String jsonString="";
		//���µĽ��
		String id = HttpUtil.getString(request, "id", "");
		String money = HttpUtil.getString(request, "money", "0");
		String startDate = HttpUtil.getString(request, "startDate", "");
		String endDate = HttpUtil.getString(request, "endDate", "");
		try {
			printWriter = response.getWriter();
			baService.saveExpendUpdatePlan(id, money, startDate, endDate);
			jsonString = "{success:true}";
		} catch (Exception e) {
			e.printStackTrace();
			jsonString = "{success:false,message:'����������'}";
		}
		printWriter.write(jsonString);
		printWriter.flush();
		printWriter.close();
		return null;
	}
	/**
	 * ��ȡ�տ�ƻ�������ʷ
	 * @Methods Name getIncomeUpdatePlanHis
	 * @Create In Sep 9, 2009 By lee
	 * @return String
	 */
	public String getIncomeUpdatePlanHis() {
		HttpServletRequest request = super.getRequest();
		HttpServletResponse response = super.getResponse();
		response.setContentType("text/plain");
		response.setCharacterEncoding("gbk");
		PrintWriter printWriter = null;
		String id = HttpUtil.getString(request, "id", "");
		int pageSize = 10;
		int start = HttpUtil.getInt(request, "start", 0);
		int pageNo = start/pageSize+1;//2010-09-16 modified by huzh
		String jsonString="{success:true,rowCount:'";
		Page page = baService.listIncomeUpdatePlanHis(id,pageNo,pageSize);
		jsonString += page.getTotalCount() + "',data:[";
		List<UpDatePlanEvent> list = page.getData();
		NumberFormat format = NumberFormat.getCurrencyInstance();
		format.setParseIntegerOnly(true);
		for (UpDatePlanEvent event : list) {
			jsonString += "{";
			jsonString += "id:'" + event.getId() + "',";
			jsonString += "updateMoney:'" + format.format(event.getMoney()) + "',";
			jsonString += "updateStartDate:'" + DateUtil.convertDateToString(event.getStartDate()) + "',";
			jsonString += "updateEndDate:'" + DateUtil.convertDateToString(event.getEndDate()) + "',";
			jsonString += "createUser:'" + event.getCreateUser().getRealName() + "',";
			jsonString += "modifyDate:'" + DateUtil.convertDateTimeToString(event.getCreateDate()) + "'";
			jsonString += "},";
		}
		if (jsonString.endsWith(",")) {
			jsonString = jsonString.substring(0,jsonString.length() - 1);
		}
		jsonString += "]}";
		
		try {
			printWriter = response.getWriter();
			printWriter.write(jsonString);
		} catch (IOException e) {
			printWriter.write("{success:false,message:'��������������ϵ����Ա'}");
		}
		printWriter.flush();
		printWriter.close();
		return null;
	}
	/**
	 * ��ȡ����ƻ�������ʷ
	 * @Methods Name getExpendUpdatePlanHis
	 * @Create In Sep 9, 2009 By lee
	 * @return String
	 */
	public String getExpendUpdatePlanHis() {
		HttpServletRequest request = super.getRequest();
		HttpServletResponse response = super.getResponse();
		response.setContentType("text/plain");
		response.setCharacterEncoding("gbk");
		PrintWriter printWriter = null;
		String id = HttpUtil.getString(request, "id", "");
		int pageSize = 10;
		int start = HttpUtil.getInt(request, "start", 0);
		int pageNo = start/pageSize+1 ;//2010-09-16 modified by huzh
		String jsonString="{success:true,rowCount:'";
		Page page = baService.listExpendUpdatePlanHis(id,pageNo,pageSize);
		NumberFormat format = NumberFormat.getCurrencyInstance();
		format.setParseIntegerOnly(true);
		jsonString += page.getTotalCount() + "',data:[";
		List<UpDatePlanEvent> list = page.getData();
		for (UpDatePlanEvent event : list) {
			jsonString += "{";
			jsonString += "id:'" + event.getId() + "',";
			jsonString += "updateMoney:'" + format.format(event.getMoney()) + "',";
			jsonString += "updateStartDate:'" + DateUtil.convertDateToString(event.getStartDate()) + "',";
			jsonString += "updateEndDate:'" + DateUtil.convertDateToString(event.getEndDate()) + "',";
			jsonString += "createUser:'" + event.getCreateUser().getRealName() + "',";
			jsonString += "modifyDate:'" + DateUtil.convertDateTimeToString(event.getCreateDate()) + "'";
			jsonString += "},";
		}
		if (jsonString.endsWith(",")) {
			jsonString = jsonString.substring(0,jsonString.length() - 1);
		}
		jsonString += "]}";
		
		try {
			printWriter = response.getWriter();
			printWriter.write(jsonString);
		} catch (IOException e) {
			printWriter.write("{success:false,message:'��������������ϵ����Ա'}");
		}
		printWriter.flush();
		printWriter.close();
		return null;
	}
	/**
	 * �����տ������������
	 * @Methods Name saveIncomeBuinessAccount
	 * @Create In Sep 9, 2009 By lee
	 * @Modify By lee in 20091030 
	 * @return String
	 */
	public String saveIncomeBusinessAccount(){
		HttpServletRequest request = super.getRequest();
		HttpServletResponse response = super.getResponse();
		PrintWriter printWriter = null;
		response.setCharacterEncoding("gbk");
		response.setContentType("text/plain");
		String relationUserId = HttpUtil.getString(request, "relationUserId", "");
		String businessAccountId = HttpUtil.getString(request, "businessAccountId", "");
		String desc = HttpUtil.getString(request, "desc", "");
		desc = HttpUtil.ConverUnicode(desc);
		desc = desc.replaceAll("'", "\\\\'");
		desc = desc.replaceAll(":", "\\\\:");
		desc = desc.replaceAll("\"", "\\\"");
		BusinessAccount businessAccount = (BusinessAccount) getService().find(BusinessAccount.class, businessAccountId,true);
		businessAccount.setApplyDate(new Date());
		businessAccount.setApplyUser(UserContext.getUserInfo());
		businessAccount.setDescn(desc);
		businessAccount.setRelationUser((UserInfo) getService().find(UserInfo.class, relationUserId));
		this.getService().save(businessAccount);
		try {
			printWriter = response.getWriter();
			printWriter.write("{success:true,message:'����ɹ�'}");
		} catch (Exception e) {
			printWriter.write("{success:false}");
			e.printStackTrace();
		}
		printWriter.flush();
		return null;
	}
	/**
	 * ���渶�������������
	 * @Methods Name saveExpendBuinessAccount
	 * @Create In Sep 9, 2009 By lee
	 * @Modify By lee in 20091030 
	 * @return String
	 */
	public String saveExpendBusinessAccount(){
		HttpServletRequest request = super.getRequest();
		HttpServletResponse response = super.getResponse();
		PrintWriter printWriter = null;
		response.setCharacterEncoding("gbk");
		response.setContentType("text/plain");
		String businessAccountId = HttpUtil.getString(request, "businessAccountId", "");
		String desc = HttpUtil.getString(request, "desc", "");
		desc = HttpUtil.ConverUnicode(desc);
		desc = desc.replaceAll("'", "\\\\'");
		desc = desc.replaceAll(":", "\\\\:");
		desc = desc.replaceAll("\"", "\\\"");
		BusinessAccount businessAccount = (BusinessAccount) getService().find(BusinessAccount.class, businessAccountId,true);
		businessAccount.setApplyDate(new Date());
		businessAccount.setApplyUser(UserContext.getUserInfo());
		businessAccount.setDescn(desc);
		businessAccount = (BusinessAccount) this.getService().save(businessAccount);
		
		try {
			printWriter = response.getWriter();
			printWriter.write("{success:true,message:'����ɹ�'}");
		} catch (Exception e) {
			printWriter.write("{success:false,message:'����ʧ��'}");
			e.printStackTrace();
		}
		printWriter.flush();
		return null;
	}
	
	/**
	 * ��ȡ�տ�ƻ���Ϣ
	 * @Methods Name getIncomeInfo
	 * @Create In Sep 9, 2009 By lee
	 * @return String
	 */
	public String getIncomeInfo(){
		HttpServletRequest request = super.getRequest();
		HttpServletResponse response = super.getResponse();
		PrintWriter printWriter = null;
		String palnId = HttpUtil.getString(request, "id", "");
		String jsonString = "";
		SRIncomePlan plan = (SRIncomePlan) getService().find(SRIncomePlan.class, palnId,true);
		UpDatePlan uPlan = (UpDatePlan) this.getService().findUnique(UpDatePlan.class, "incomePlan", plan);
		NumberFormat format = NumberFormat.getCurrencyInstance();
		format.setParseIntegerOnly(true);
		jsonString += "{success:true,";
		jsonString += "SRIncomePlan$id:'" + plan.getId() + "',";
		jsonString += "SRIncomePlan$name:'" + plan.getName() + "',";
		jsonString += "SRIncomePlan$descn:'" + (plan.getDescn()==null ? "" : plan.getDescn())+ "',";
		jsonString += "plan$money:'" + format.format((uPlan!=null?uPlan.getMoney():plan.getMoney()))+ "',";
		if(uPlan!=null){
			jsonString += "plan$startDate:'" + DateUtil.convertDateToString(uPlan.getStartDate()) + "',";
		}else{
			jsonString += "plan$startDate:'" + DateUtil.convertDateToString(plan.getStartDate()) + "',";
		}
		if(uPlan!=null){
			jsonString += "plan$endDate:'" + DateUtil.convertDateToString(uPlan.getEndDate()) + "',";
		}else{
			jsonString += "plan$endDate:'" + DateUtil.convertDateToString(plan.getEndDate()) + "',";
		}
		jsonString += "plan$balance:'" + format.format(baService.getIncomeBalanceByPlanId(plan.getId().toString())) + "'}";
		response.setContentType("text/plain");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out;
		try {
			printWriter = response.getWriter();
			printWriter.write(jsonString);
		} catch (Exception e) {
			printWriter.write("{success:false,message:'��ѯʧ�ܡ�����ϵ����Ա'}");
			e.printStackTrace();
		}
		printWriter.flush();
		return null;
	}
	/**
	 * ��ȡ����ƻ���Ϣ
	 * @Methods Name getExtendInfo
	 * @Create In Sep 1, 2009 By lee
	 * @return String
	 */
	public String getExtendInfo(){
		HttpServletRequest request = super.getRequest();
		HttpServletResponse response = super.getResponse();
		PrintWriter printWriter = null;
		String palnId = HttpUtil.getString(request, "id", "");
		String jsonString = "";
		SRExpendPlan plan = (SRExpendPlan) getService().find(SRExpendPlan.class, palnId,true);
		UpDatePlan uPlan = (UpDatePlan) this.getService().findUnique(UpDatePlan.class, "expendPlan", plan);
		NumberFormat format = NumberFormat.getCurrencyInstance();
		format.setParseIntegerOnly(true);
		jsonString += "{success:true,";
		jsonString += "SRExpendPlan$id:'" + plan.getId() + "',";
		jsonString += "SRExpendPlan$name:'" + plan.getName() + "',";
		jsonString += "SRExpendPlan$descn:'" + (plan.getDescn()==null ? "" : plan.getDescn())+ "',";
		jsonString += "plan$money:'" + format.format((uPlan!=null?uPlan.getMoney():plan.getMoney()))+ "',";
		if(uPlan!=null){
			jsonString += "plan$startDate:'" + DateUtil.convertDateToString(uPlan.getStartDate()) + "',";
		}else{
			jsonString += "plan$startDate:'" + DateUtil.convertDateToString(plan.getStartDate()) + "',";
		}
		if(uPlan!=null){
			jsonString += "plan$endDate:'" + DateUtil.convertDateToString(uPlan.getEndDate()) + "',";
		}else{
			jsonString += "plan$endDate:'" + DateUtil.convertDateToString(plan.getEndDate()) + "',";
		}
		jsonString += "plan$balance:'" + format.format(baService.getExpendBalanceByPlanId(plan.getId().toString())) + "'}";
		response.setContentType("text/plain");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out;
		try {
			printWriter = response.getWriter();
			printWriter.write(jsonString);
		} catch (Exception e) {
			printWriter.write("{success:false,message:'��ѯʧ�ܡ�����ϵ����Ա'}");
			e.printStackTrace();
		}
		printWriter.flush();
		return null;
	}
	
	/**
	 * ��ȡ��ѡ�տ�ƻ�����
	 * @Methods Name getIncomePlanCombo
	 * @Create In Sep 9, 2009 By lee
	 * @return String
	 */
	public String getIncomePlanCombo(){
		HttpServletRequest request = super.getRequest();
		HttpServletResponse response = super.getResponse();

		String json = "";
		String dataId = request.getParameter("dataId");
		List<SRIncomePlan> list = baService.findUnFinshedIncomePlan(dataId);
		for (SRIncomePlan incomePlan : list) {
			String str = "";
			str = "id:'" + incomePlan.getId() + "'," + "name:'"
					+ incomePlan.getName() + "'";
			str = "{" + str + "},";
			json = json + str;
		}
		if (json.endsWith(",")) {
			json = json.substring(0, json.length() - 1);
		}
		String returnStr = "{success:true,rowCount:'" + list.size()+ "',data:[" + json + "]}";
		response.setContentType("text/plain");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out;
		try {
			out = response.getWriter();
			out.println(returnStr);
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * ��ȡ��ѡ����ƻ�����
	 * @Methods Name getExpendPlanCombo
	 * @Create In Sep 9, 2009 By lee
	 * @return String
	 */
	public String getExpendPlanCombo(){
		HttpServletRequest request = super.getRequest();
		HttpServletResponse response = super.getResponse();

		String json = "";
		String dataId = request.getParameter("dataId");
		List<SRExpendPlan> list = baService.findUnFinshedExpendPlan(dataId);
		for (SRExpendPlan plan : list) {
			String str = "";
			str = "id:'" + plan.getId() + "'," + "name:'" + plan.getName() + "'";
			str = "{" + str + "},";
			json = json + str;
		}
		if (json.endsWith(",")) {
			json = json.substring(0, json.length() - 1);
		}
		String returnStr = "{success:true,rowCount:'" + list.size()+ "',data:[" + json + "]}";
		response.setContentType("text/plain");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out;
		try {
			out = response.getWriter();
			out.println(returnStr);
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * ��ȡ�տ����������Ϣ
	 * @Methods Name getIncomeBusinessAccount
	 * @Create In Aug 24, 2009 By lee
	 * @return String
	 */
	public String getIncomeBusinessAccount() {
		HttpServletRequest request = super.getRequest();
		HttpServletResponse response = super.getResponse();
		String dataId = request.getParameter("dataId");
		BusinessAccount ba = (BusinessAccount) this.getService().find(BusinessAccount.class, dataId);
		
		String reqId = ba.getRequire().getId().toString();
		String applyNum = ba.getApplyNum();
		UserInfo user = ba.getRelationUser();
		String userId = "";
		if(user!=null){
			userId = user.getId().toString();
		}
		String descn = ba.getDescn();
		//add by lee for ȥ�� null ��ʾBUG in 20091030 begin
		if(descn==null){
			descn = "";
		}
		//add by lee for ȥ�� null ��ʾBUG in 20091030 end
		String returnStr = "{success:true,reqId:'"+reqId+"',applyNum:'" + applyNum +"',userId:'" + userId +"',descn:'" + descn +"'}";
		response.setContentType("text/plain");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out;
		try {
			out = response.getWriter();
			out.println(returnStr);
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;
	}
	
	/**
	 * ��ȡ�տ����������Ϣ
	 * @Methods Name getIncomeBusinessAccount
	 * @Create In Aug 24, 2009 By lee
	 * @return String
	 */
	public String getExplandBusinessAccount() {
		HttpServletRequest request = super.getRequest();
		HttpServletResponse response = super.getResponse();
		String dataId = request.getParameter("dataId");
		BusinessAccount ba = (BusinessAccount) this.getService().find(BusinessAccount.class, dataId);
		
		String reqId = ba.getRequire().getId().toString();
		String applyNum = ba.getApplyNum();
		String descn = ba.getDescn();
		//add by lee for ȥ�� null ��ʾBUG in 20091030 begin
		if(descn==null){
			descn = "";
		}
		//add by lee for ȥ�� null ��ʾBUG in 20091030 end
		String returnStr = "{success:true,reqId:'"+reqId+"',applyNum:'" + applyNum +"',descn:'" + descn +"'}";
		response.setContentType("text/plain");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out;
		try {
			out = response.getWriter();
			out.println(returnStr);
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;
	}
	
	/**
	 * ��ȡ������������տ���Ŀ
	 * @Methods Name getAllIncomeInfo
	 * @Create In Sep 9, 2009 By lee
	 * @return String
	 */
	public String getAllIncomeInfo() {
		HttpServletRequest request = super.getRequest();
		HttpServletResponse response = super.getResponse();
		PrintWriter printWriter = null;
		response.setCharacterEncoding("gbk");
		response.setContentType("text/plain");
		String businessAccountId = HttpUtil.getString(request, "baId", "");
		String str = "";
		NumberFormat format = NumberFormat.getCurrencyInstance();
		format.setParseIntegerOnly(true);
		List<RealIncome> realIncomes = baService.getRealIncomeByBaId(businessAccountId);
		for(RealIncome realIncome : realIncomes ){
			SRIncomePlan plan = realIncome.getIncomePlan();
			UpDatePlan uPlan = realIncome.getUpDatePlan();
			String jsonString = "{";
			//�տ�ƻ�ID
			jsonString += "SRIncomePlan$id:'" + plan.getId() + "',";
			//�տ�����
			jsonString += "SRIncomePlan$name:'" + plan.getName() + "',";
			//�տ�����
			jsonString += "SRIncomePlan$descn:'" + (plan.getDescn()==null ? "" : plan.getDescn())+ "',";
			//�տ���
			if(uPlan!=null){
				jsonString += "plan$money:'" + format.format(uPlan.getMoney()) + "',";
			}else{
				jsonString += "plan$money:'" + format.format(plan.getMoney()) + "',";
			}
			//�ƻ���ʼʱ��
			if(uPlan!=null){
				jsonString += "plan$startDate:'" + DateUtil.convertDateToString(uPlan.getStartDate()) + "',";
			}else{
				jsonString += "plan$startDate:'" + DateUtil.convertDateToString(plan.getStartDate()) + "',";
			}
			//�ƻ�����ʱ��
			if(uPlan!=null){
				jsonString += "plan$endDate:'" + DateUtil.convertDateToString(uPlan.getEndDate()) + "',";
			}else{
				jsonString += "plan$endDate:'" + DateUtil.convertDateToString(plan.getEndDate()) + "',";
			}
			//���
			jsonString += "plan$balance:'" + format.format(baService.getIncomeBalanceByPlanId(plan.getId().toString())) + "',";
			//ʵ���տ�ID
			jsonString += "itil_RealIncome$id:'" + realIncome.getId() + "',";
			//ʵ���տ�ʱ��
			Date realDate = realIncome.getRealDate();
			if(realDate!=null){
				jsonString += "itil_RealIncome$realDate:'" + DateUtil.convertDateToString(realDate) + "',";
			}else{
				jsonString += "itil_RealIncome$realDate:'',";
			}
			//ʵ���տ�ɱ�����
			jsonString += "itil_RealIncome$costCenter:'" + realIncome.getCostCenter() + "',";
			//ʵ���տ���
			jsonString += "itil_RealIncome$realMoney:'" + format.format(realIncome.getRealMoney()) + "'},";
			str +=jsonString;
		}

		
		if(str.endsWith(",")){
			str = str.substring(0,str.length()-1);
		}
		str = "{success:true,data:["+str+"]}";
		try {
			printWriter = response.getWriter();
			printWriter.write(str);
		} catch (Exception e) {
			printWriter.write("{success:false,message:'��ѯʧ�ܡ�����ϵ����Ա'}");
			e.printStackTrace();
		}
		printWriter.flush();
		return null;
	}
	
	/**
	 * ��ȡ����������и�����Ŀ
	 * @Methods Name getAllPaymentInfo
	 * @Create In Sep 9, 2009 By lee
	 * @return String
	 */
	public String getAllPaymentInfo() {
		HttpServletRequest request = super.getRequest();
		HttpServletResponse response = super.getResponse();
		String businessAccountId = HttpUtil.getString(request, "baId", "");
		String str = "";
		NumberFormat format = NumberFormat.getCurrencyInstance();
		format.setParseIntegerOnly(true);
		List<RealPayment> realPayments = baService.getRealPaymentByBaId(businessAccountId);
		for(RealPayment realPayment : realPayments ){
			SRExpendPlan plan = realPayment.getExpendPlan();
			UpDatePlan uPlan = realPayment.getUpDatePlan();
			String jsonString = "{";
			jsonString += "SRExpendPlan$id:'" + plan.getId() + "',";
			jsonString += "SRExpendPlan$name:'" + plan.getName() + "',";
			jsonString += "SRExpendPlan$descn:'" + (plan.getDescn()==null ? "" : plan.getDescn())+ "',";
			if(uPlan!=null){
				jsonString += "plan$money:'" + format.format(uPlan.getMoney()) + "',";
			}else{
				jsonString += "plan$money:'" + format.format(plan.getMoney()) + "',";
			}
			//�ƻ���ʼʱ��
			if(uPlan!=null){
				jsonString += "plan$startDate:'" + DateUtil.convertDateToString(uPlan.getStartDate()) + "',";
			}else{
				jsonString += "plan$startDate:'" + DateUtil.convertDateToString(plan.getStartDate()) + "',";
			}
			//�ƻ�����ʱ��
			if(uPlan!=null){
				jsonString += "plan$endDate:'" + DateUtil.convertDateToString(uPlan.getEndDate()) + "',";
			}else{
				jsonString += "plan$endDate:'" + DateUtil.convertDateToString(plan.getEndDate()) + "',";
			}
			//���
			jsonString += "plan$balance:'" + format.format(baService.getExpendBalanceByPlanId(plan.getId().toString())) + "',";
			jsonString += "itil_RealPayment$id:'" + realPayment.getId() + "',";
			Date realDate = realPayment.getRealDate();
			if(realDate!=null){
				jsonString += "itil_RealPayment$realDate:'" + DateUtil.convertDateToString(realDate) + "',";
			}else{
				jsonString += "itil_RealPayment$realDate:'',";
			}
			jsonString += "itil_RealPayment$costCenter:'" + realPayment.getCostCenter() + "',";
			jsonString += "itil_RealPayment$realMoney:'" + format.format(realPayment.getRealMoney()) + "'},";
			str +=jsonString;
		}

		
		if(str.endsWith(",")){
			str = str.substring(0,str.length()-1);
		}
		str = "{success:true,data:["+str+"]}";
		
		response.setContentType("text/plain");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out;
		try {
			out = response.getWriter();
			out.println(str);
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * �����տ�ʱ��
	 * @Methods Name saveIncomeDate
	 * @Create In Sep 1, 2009 By lee
	 * @return String
	 */
	public String saveIncomeDate(){
		HttpServletRequest request = super.getRequest();
		HttpServletResponse response = super.getResponse();
		
		String incomeId = request.getParameter("id");
		String incomeDate = request.getParameter("date");
		
		RealIncome ri = (RealIncome) this.getService().find(RealIncome.class, incomeId,true);
		ri.setRealDate(DateUtil.convertStringToDate(incomeDate));
		this.getService().save(ri);
		
		String json = "{success : true}";
		response.setContentType("text/plain");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out;
		try {
			out = response.getWriter();
			out.println(json);
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * ���渶��ʱ��
	 * @Methods Name savePaymentDate
	 * @Create In Sep 2, 2009 By lee
	 * @return String
	 */
	public String savePaymentDate(){
		HttpServletRequest request = super.getRequest();
		HttpServletResponse response = super.getResponse();
		
		String paymentId = request.getParameter("id");
		String paymentDate = request.getParameter("date");
		
		RealPayment re = (RealPayment) this.getService().find(RealPayment.class, paymentId,true);
		re.setRealDate(DateUtil.convertStringToDate(paymentDate));
		this.getService().save(re);
		
		String json = "{success : true}";
		response.setContentType("text/plain");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out;
		try {
			out = response.getWriter();
			out.println(json);
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * ��ȡ�տ�ƻ���ʷ
	 * @Methods Name getAllIncomeHis
	 * @Create In Sep 1, 2009 By lee
	 * @return String
	 */
	public String getAllIncomeHis(){
		HttpServletRequest request = super.getRequest();
		HttpServletResponse response = super.getResponse();
		String requireId = HttpUtil.getString(request, "reqId", "");
		String json = "";
		NumberFormat format = NumberFormat.getCurrencyInstance();
		format.setParseIntegerOnly(true);
		List<RealIncome> realIncomes = baService.getRealIncomeByReqId(requireId);
		for(RealIncome realIncome:realIncomes ){
			SRIncomePlan plan = realIncome.getIncomePlan();
			UpDatePlan uPlan = realIncome.getUpDatePlan();
			String jsonString = "{";
			//�տ�ƻ�ԭʼ��Ϣ
			jsonString += "SRIncomePlan$id:'" + plan.getId() + "',";
			jsonString += "SRIncomePlan$name:'" + plan.getName() + "',";
			jsonString += "SRIncomePlan$descn:'" + (plan.getDescn()==null ? "" : plan.getDescn())+ "',";
			jsonString += "SRIncomePlan$money:'" + format.format((plan.getMoney()==null ? "" : plan.getMoney()))+ "',";
			jsonString += "SRIncomePlan$startDate:'" + DateUtil.convertDateToString(plan.getStartDate())+ "',";
			jsonString += "SRIncomePlan$endDate:'" + DateUtil.convertDateToString(plan.getEndDate())+ "',";
			//�տ���¼ƻ���Ϣ
			if(uPlan!=null){
				jsonString += "itil_upDatePlan$id:'" + uPlan.getId() + "',";
				jsonString += "itil_upDatePlan$money:'" + format.format(uPlan.getMoney())  + "',";
				jsonString += "itil_upDatePlan$startDate:'" + DateUtil.convertDateToString(uPlan.getStartDate()) + "',";
				jsonString += "itil_upDatePlan$endDate:'" + DateUtil.convertDateToString(uPlan.getEndDate()) + "',";
				jsonString += "itil_upDatePlan$descn:'" + (uPlan.getDescn() == null ? "" :   uPlan.getDescn())+ "',";
			}
			//��ʵ�տ���Ϣ
			jsonString += "itil_RealIncome$id:'" + realIncome.getId() + "',";
			jsonString += "itil_RealIncome$realDate:'" + (realIncome.getRealDate()==null ? "" : DateUtil.convertDateToString(realIncome.getRealDate()))+ "',";
			jsonString += "itil_RealIncome$costCenter:'" + realIncome.getCostCenter() + "',";
			jsonString += "itil_RealIncome$realMoney:'" + format.format(realIncome.getRealMoney()) + "'},";
			json +=jsonString;
		}

		
		if(json.endsWith(",")){
			json = json.substring(0,json.length()-1);
		}
		json = "{success:true,data:["+json+"]}";
		response.setContentType("text/plain");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out;
		try {
			out = response.getWriter();
			out.println(json);
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * ��ȡ����ƻ���ʷ
	 * @Methods Name getAllPaymentHis
	 * @Create In Sep 2, 2009 By lee
	 * @return String
	 */
	public String getAllPaymentHis(){
		HttpServletRequest request = super.getRequest();
		HttpServletResponse response = super.getResponse();
		String requireId = HttpUtil.getString(request, "reqId", "");
		String json = "";
		NumberFormat format = NumberFormat.getCurrencyInstance();
		format.setParseIntegerOnly(true);
		List<RealPayment> payments = baService.getRealPaymentByReqId(requireId);
		for(RealPayment payment : payments ){
			SRExpendPlan plan = payment.getExpendPlan();
			UpDatePlan uPlan = payment.getUpDatePlan();
			String jsonString = "{";
			//�տ�ƻ�ԭʼ��Ϣ
			jsonString += "SRExpendPlan$id:'" + plan.getId() + "',";
			jsonString += "SRExpendPlan$name:'" + plan.getName() + "',";
			jsonString += "SRExpendPlan$descn:'" + (plan.getDescn()==null ? "" : plan.getDescn())+ "',";
			jsonString += "SRExpendPlan$money:'" + format.format((plan.getMoney()==null ? "" : plan.getMoney()))+ "',";
			jsonString += "SRExpendPlan$startDate:'" + DateUtil.convertDateToString(plan.getStartDate())+ "',";
			jsonString += "SRExpendPlan$endDate:'" + DateUtil.convertDateToString(plan.getEndDate())+ "',";
			//�տ���¼ƻ���Ϣ
			if(uPlan!=null){
				jsonString += "itil_upDatePlan$id:'" + uPlan.getId() + "',";
				jsonString += "itil_upDatePlan$money:'" + format.format(uPlan.getMoney())  + "',";
				jsonString += "itil_upDatePlan$startDate:'" + DateUtil.convertDateToString(uPlan.getStartDate()) + "',";
				jsonString += "itil_upDatePlan$endDate:'" + DateUtil.convertDateToString(uPlan.getEndDate()) + "',";
				jsonString += "itil_upDatePlan$descn:'" + (uPlan.getDescn() == null ? "" : uPlan.getDescn())+ "',";
			}
			//��ʵ�տ���Ϣ
			jsonString += "itil_RealPayment$id:'" + payment.getId() + "',";
			jsonString += "itil_RealPayment$realDate:'" + (payment.getRealDate()==null ? "" : DateUtil.convertDateToString(payment.getRealDate()))+ "',";
			jsonString += "itil_RealPayment$costCenter:'" + payment.getCostCenter() + "',";
			jsonString += "itil_RealPayment$realMoney:'" + format.format(payment.getRealMoney()) + "'},";
			json +=jsonString;
		}

		
		if(json.endsWith(",")){
			json = json.substring(0,json.length()-1);
		}
		json = "{success:true,data:["+json+"]}";
		response.setContentType("text/plain");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out;
		try {
			out = response.getWriter();
			out.println(json);
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * ��ת���׿��������������ҳ�棬����ǰ��ѡ����׿�ƻ�
	 * @Methods Name toIncomeApplyPage
	 * @Create In Oct 29, 2009 By lee
	 * @return String
	 */
	public String toIncomeApplyPage(){
		HttpServletRequest request = super.getRequest();
		HttpServletResponse response = super.getResponse();
		String planIds = request.getParameter("planIds");
		String requireId = request.getParameter("requireId");

		//�½������������ݸ�
		SpecialRequirement requirement = (SpecialRequirement) getService().find(SpecialRequirement.class, requireId) ;
		UserInfo relationUser = requirement.getApplyUser();
		Map dataMap = new HashMap();
		dataMap.put("require", requirement);
		dataMap.put("relationUser", relationUser);
		dataMap.put("applyUser", UserContext.getUserInfo());
		dataMap.put("applyDate", new Date());
		dataMap.put("status", Integer.valueOf(0));
		dataMap.put("descn", "");
		BusinessAccount businessAccount = (BusinessAccount) metaDataManager.saveEntityData(BusinessAccount.class, dataMap);
		String businessAccountId = businessAccount.getId().toString();
		
		//Ϊ����ݸ������ѡ�տ���
		if (StringUtils.isNotBlank(planIds)) {
			JSONArray ja =  JSONArray.fromObject(planIds);
			Object[] planIdStrs = ja.toArray();	//�õ�ѡ����׿���Ŀ��id����
			String costCenter = requirement.getCostConter();//�õ���������ɱ�����
			for (int i = 0; i < planIdStrs.length ; i ++) {
				RealIncome money = new RealIncome();
				SRIncomePlan plan = (SRIncomePlan) getService().find(SRIncomePlan.class, planIdStrs[i].toString());
				UpDatePlan uPlan = (UpDatePlan) this.getService().findUnique(UpDatePlan.class, "incomePlan", plan);
				money.setCostCenter(costCenter);
				Double moDouble = baService.getIncomeBalanceByPlanId(planIdStrs[i].toString());//�õ�Ϊ�տ��
				money.setRealMoney(moDouble);
				money.setIncomePlan(plan);
				money.setUpDatePlan(uPlan);
				money.setBusinessAccount(businessAccount);
				getService().save(money);
			}
		}
		request.setAttribute("dataId", businessAccountId);
		return "incomeApply";
	}
	/**
	 * �����տ���Ŀ
	 * @Methods Name initIncomeInfo
	 * @Create In Oct 29, 2009 By lee
	 * @return String
	 */
	public String initIncomeInfo(){
		HttpServletRequest request = super.getRequest();
		HttpServletResponse response = super.getResponse();
		
		String baId = request.getParameter("baId");//�տ�����ID
		String incomePlanId = request.getParameter("incomePlanId");//�տ�ƻ�ID
		String costCenter = request.getParameter("costCenter");//ʵ���տ�ƻ���Ӧ�ĳɱ�����
		String moneyStr = request.getParameter("money");		//ʵ���տ�ƻ���Ӧ�Ľ��
		
		Double balance = baService.getCurIncomeBalance(baId, incomePlanId);
		Double money = Double.valueOf(moneyStr);
		
		String json = "";
		if(money<=balance){//ʵ�ʽ�����ʣ��������ӳɹ�
			RealIncome realIncome = new RealIncome();
			BusinessAccount businessAccount = (BusinessAccount) getService().find(BusinessAccount.class, baId);
			SRIncomePlan plan = (SRIncomePlan) getService().find(SRIncomePlan.class, incomePlanId);
			UpDatePlan uPlan = (UpDatePlan) this.getService().findUnique(UpDatePlan.class, "incomePlan", plan);
			realIncome.setCostCenter(costCenter);
			realIncome.setRealMoney(money);
			realIncome.setIncomePlan(plan);
			realIncome.setUpDatePlan(uPlan);
			realIncome.setBusinessAccount(businessAccount);
			this.getService().save(realIncome);
			json = "{success : true , result : true}";
		}else{
			json = "{success : true , result : false}";
		}
		
		response.setContentType("text/plain");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out;
		try {
			out = response.getWriter();
			out.println(json);
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * �����޸��տ���Ŀ
	 * @Methods Name saveIncomeInfo
	 * @Create In Oct 29, 2009 By lee
	 * @return String
	 */
	public String saveIncomeInfo(){
		HttpServletRequest request = super.getRequest();
		HttpServletResponse response = super.getResponse();
		
		String incomeId = request.getParameter("realIncomeId");//ʵ���տ�ƻ�ID
		String costCenter = request.getParameter("costCenter");//ʵ���տ�ƻ���Ӧ�ĳɱ�����
		String moneyStr = request.getParameter("money");		//ʵ���տ�ƻ���Ӧ�Ľ��
		
		RealIncome ri = (RealIncome) this.getService().find(RealIncome.class, incomeId,true);
		String baId = ri.getBusinessAccount().getId().toString();
		String planId = ri.getIncomePlan().getId().toString();
		Double oldMoney = ri.getRealMoney();
		ri.setCostCenter(costCenter);
		Double money = Double.valueOf(moneyStr);
		Double balance = baService.getCurIncomeBalance(baId, planId)+oldMoney;
		
		String json = "{success : true}";
		if(money<=balance){//ʵ�ʽ�����ʣ��������ӳɹ�
			ri.setRealMoney(money);
			this.getService().save(ri);
			json = "{success : true , result : true}";
		}else{
			json = "{success : true , result : false}";
		}
		response.setContentType("text/plain");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out;
		try {
			out = response.getWriter();
			out.println(json);
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * ɾ���տ���Ŀ
	 * @Methods Name removeIncomeInfo
	 * @Create In Oct 29, 2009 By lee
	 * @return String
	 */
	public String removeIncomeInfo(){
		HttpServletRequest request = super.getRequest();
		HttpServletResponse response = super.getResponse();
		
		String incomeId = request.getParameter("realIncomeId");//ʵ���տ�ƻ�ID
		
		RealIncome realIncome = (RealIncome) this.getService().find(RealIncome.class, incomeId,true);
		this.getService().remove(realIncome);
		String json = "{success : true}";
		response.setContentType("text/plain");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out;
		try {
			out = response.getWriter();
			out.println(json);
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * ��ת�������������������ҳ�棬����ǰ��ѡ��ĸ���ƻ�
	 * @Methods Name toExpendApplyPage
	 * @Create In Oct 29, 2009 By lee
	 * @return String
	 */
	public String toExpendApplyPage(){
		HttpServletRequest request = super.getRequest();
		HttpServletResponse response = super.getResponse();
		String planIds = request.getParameter("planIds");
		String requireId = request.getParameter("requireId");

		//�½������������ݸ�
		SpecialRequirement requirement = (SpecialRequirement) getService().find(SpecialRequirement.class, requireId) ;
		Map dataMap = new HashMap();
		dataMap.put("require", requirement);
		dataMap.put("applyUser", UserContext.getUserInfo());
		dataMap.put("applyDate", new Date());
		dataMap.put("status", Integer.valueOf(0));
		dataMap.put("descn", "");
		BusinessAccount businessAccount = (BusinessAccount) metaDataManager.saveEntityData(BusinessAccount.class, dataMap);
		String businessAccountId = businessAccount.getId().toString();
		
		//Ϊ����ݸ������ѡ������
		if (StringUtils.isNotBlank(planIds)) {
			JSONArray ja =  JSONArray.fromObject(planIds);
			Object[] planIdStrs = ja.toArray();	//�õ�ѡ��ĸ�����Ŀ��id����
			//String costCenter = requirement.getCostConter();//�õ���������ɱ�����
			for (int i = 0; i < planIdStrs.length ; i ++) {
				RealPayment money = new RealPayment();
				SRExpendPlan plan = (SRExpendPlan) getService().find(SRExpendPlan.class, planIdStrs[i].toString());
				UpDatePlan uPlan = (UpDatePlan) this.getService().findUnique(UpDatePlan.class, "expendPlan", plan);
				money.setCostCenter("");
				Double moDouble = baService.getExpendBalanceByPlanId(planIdStrs[i].toString());//�õ�Ϊ�տ��
				money.setRealMoney(moDouble);
				money.setExpendPlan(plan);
				money.setUpDatePlan(uPlan);
				money.setBusinessAccount(businessAccount);
				getService().save(money);
			}
		}
		request.setAttribute("dataId", businessAccountId);
		return "paymentApply";
	}
	/**
	 * ����������Ŀ
	 * @Methods Name initPaymentInfo
	 * @Create In Oct 29, 2009 By lee
	 * @return String
	 */
	public String initPaymentInfo(){
		HttpServletRequest request = super.getRequest();
		HttpServletResponse response = super.getResponse();
		
		String baId = request.getParameter("baId");//��������ID
		String expendPlanId = request.getParameter("expendPlanId");//����ƻ�ID
		String costCenter = request.getParameter("costCenter");//ʵ�ʸ���ƻ���Ӧ�ĳɱ�����
		String moneyStr = request.getParameter("money");		//ʵ�ʸ���ƻ���Ӧ�Ľ��
		
		Double balance = baService.getCurExpendBalance(baId, expendPlanId);
		Double money = Double.valueOf(moneyStr);
		
		String json = "";
		if(money<=balance){//ʵ�ʽ�����ʣ��������ӳɹ�
			RealPayment realPayment = new RealPayment();
			BusinessAccount businessAccount = (BusinessAccount) getService().find(BusinessAccount.class, baId);
			SRExpendPlan plan = (SRExpendPlan) getService().find(SRExpendPlan.class, expendPlanId);
			UpDatePlan uPlan = (UpDatePlan) this.getService().findUnique(UpDatePlan.class, "expendPlan", plan);
			realPayment.setCostCenter(costCenter);
			realPayment.setRealMoney(money);
			realPayment.setExpendPlan(plan);
			realPayment.setUpDatePlan(uPlan);
			realPayment.setBusinessAccount(businessAccount);
			getService().save(realPayment);
			json = "{success : true , result : true}";
		}else{
			json = "{success : true , result : false}";
		}
		
		response.setContentType("text/plain");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out;
		try {
			out = response.getWriter();
			out.println(json);
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * �����޸ĸ�����Ŀ
	 * @Methods Name savePaymentInfo
	 * @Create In Oct 29, 2009 By lee
	 * @return String
	 */
	public String savePaymentInfo(){
		HttpServletRequest request = super.getRequest();
		HttpServletResponse response = super.getResponse();
		
		String paymentId = request.getParameter("realPaymentId");//ʵ�ʸ���ƻ�ID
		String costCenter = request.getParameter("costCenter");//ʵ�ʸ���ƻ���Ӧ�ĳɱ�����
		String moneyStr = request.getParameter("money");		//ʵ�ʸ���ƻ���Ӧ�Ľ��
		
		RealPayment rp = (RealPayment) this.getService().find(RealPayment.class, paymentId,true);
		String baId = rp.getBusinessAccount().getId().toString();
		String planId = rp.getExpendPlan().getId().toString();
		Double oldMoney = rp.getRealMoney();
		rp.setCostCenter(costCenter);
		Double money = Double.valueOf(moneyStr);
		Double balance = baService.getCurExpendBalance(baId, planId)+oldMoney;
		
		String json = "{success : true}";
		if(money<=balance){//ʵ�ʽ�����ʣ��������ӳɹ�
			rp.setRealMoney(money);
			this.getService().save(rp);
			json = "{success : true , result : true}";
		}else{
			json = "{success : true , result : false}";
		}
		response.setContentType("text/plain");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out;
		try {
			out = response.getWriter();
			out.println(json);
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * ɾ��������Ŀ
	 * @Methods Name removePaymentInfo
	 * @Create In Oct 29, 2009 By lee
	 * @return String
	 */
	public String removePaymentInfo(){
		HttpServletRequest request = super.getRequest();
		HttpServletResponse response = super.getResponse();
		
		String paymentId = request.getParameter("realPaymentId");//ʵ�ʸ���ƻ�ID
		
		RealPayment realPayment = (RealPayment) this.getService().find(RealPayment.class, paymentId,true);
		this.getService().remove(realPayment);
		String json = "{success : true}";
		response.setContentType("text/plain");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out;
		try {
			out = response.getWriter();
			out.println(json);
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * ��ѯ�̱������б�
	 * @Methods Name findBusinessAccount
	 * @Create In Nov 9, 2009 By duxh
	 * @return String
	 */
	public String findBusinessAccount(){
		try {
			HttpServletRequest request = super.getRequest();
			String applyNum = request.getParameter("applyNum");
			String requireIdTemp = request.getParameter("requireId");
			String applyDateTemp = request.getParameter("applyDate");
			String statusTemp = request.getParameter("status");
			int pageSize = HttpUtil.getInt(super.getRequest(), "pageSize", 10);
			int start = HttpUtil.getInt(super.getRequest(), "start", 0);
			Long requireId=null;
			if(requireIdTemp.trim().length()!=0){
				requireId=Long.parseLong(requireIdTemp);
			}
			java.sql.Date applyDate=null;
			if(applyDateTemp.trim().length()!=0){
				applyDate=java.sql.Date.valueOf(applyDateTemp);
			}
			Integer status=null;
			if(!statusTemp.trim().equals("3")){//��״̬
				status=Integer.parseInt(statusTemp);
			}
			Page page=baService.findBusinessAccount(applyNum, requireId, applyDate, status, start,pageSize);
			List<BusinessAccount> businessAccounts=page.list();
			StringBuilder json = new StringBuilder("{success:true,rowCount:" + page.getTotalCount() + ",data:[");
			for (int i = 0; i < businessAccounts.size(); i++) {
				if (i != 0)
					json.append(",");
				json.append("{");
				json.append("id:'" + businessAccounts.get(i).getId() + "',");
				json.append("applyNum:'" + businessAccounts.get(i).getApplyNum() + "',");
				json.append("require:'" + businessAccounts.get(i).getRequire().getName() + "',");
				json.append("descn:'" + businessAccounts.get(i).getDescn() + "',");
				UserInfo user=businessAccounts.get(i).getApplyUser();
				json.append("applyUser:'"+user.getRealName()+ "/" + user.getUserName() + "/"
						+ user.getDepartment().getDepartName()+ "',");
				json.append("applyDate:'" + businessAccounts.get(i).getApplyDate()+ "',");
				String str=null;
				if(businessAccounts.get(i).getStatus().compareTo(0)==0){
					str="�ݸ�";
				}
				if(businessAccounts.get(i).getStatus().compareTo(1)==0){
					str="������";
				}
				if(businessAccounts.get(i).getStatus().compareTo(2)==0){
					str="�������";
				}
				json.append("status:'" + str+ "'");
				json.append("}");
			}
			json.append("]");
			json.append("}");
			super.getResponse().setContentType("text/plain");
			super.getResponse().setCharacterEncoding("UTF-8");
			PrintWriter out =getResponse().getWriter();
			out.println(json);
			out.flush();
			out.close();
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationException();
		}
	}
	/**
	 * �̱������ѯ�б�˫����¼��תҳ�档
	 * @Methods Name pageForward
	 * @Create In Nov 11, 2009 By duxh
	 * @return String
	 */
	public String pageForward(){
		HttpServletRequest request = super.getRequest();
		String businessAccountId = request.getParameter("businessAccountId");
		request.setAttribute("dataId", businessAccountId);
		BusinessAccount businessAccount=(BusinessAccount) getService().find(BusinessAccount.class, businessAccountId);
		Integer status=businessAccount.getStatus();
		RealIncome income=(RealIncome) getService().findUnique(RealIncome.class, "businessAccount", businessAccount);
		if(income!=null){
			if(status.compareTo(1)==0)
				return "incomeForUser";
			else if(status.compareTo(2)==0)
				return "incomeForUser";
			else
				return "back_income";
		}else{
			if(status.compareTo(1)==0)
				return "paymentForUser";
			else if(status.compareTo(2)==0)
				return "paymentForUser";
			else
				return "back_payment";
		}
	}
}
