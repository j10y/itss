package com.xpsoft.oa.action.hrm;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.xpsoft.core.command.QueryFilter;
import com.xpsoft.core.util.AppUtil;
import com.xpsoft.core.util.ContextUtil;
import com.xpsoft.core.web.action.BaseAction;
import com.xpsoft.core.web.paging.PagingBean;
import com.xpsoft.oa.model.hrm.SalaryPayoff;
import com.xpsoft.oa.model.hrm.StandSalaryItem;
import com.xpsoft.oa.model.system.AppUser;
import com.xpsoft.oa.service.hrm.ExportSalaryService;
import com.xpsoft.oa.service.hrm.SalaryPayoffService;
import com.xpsoft.oa.service.hrm.StandSalaryItemService;
import com.xpsoft.oa.service.kpi.HrPaKpiPBC2UserCmpService;

import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

public class SalaryPayoffAction extends BaseAction {

	@Resource
	private SalaryPayoffService salaryPayoffService;

	@Resource
	private StandSalaryItemService standSalaryItemService;
	private SalaryPayoff salaryPayoff;
	private Long recordId;

	public Long getRecordId() {
		/* 40 */return this.recordId;
	}

	public void setRecordId(Long recordId) {
		/* 44 */this.recordId = recordId;
	}

	public SalaryPayoff getSalaryPayoff() {
		/* 48 */return this.salaryPayoff;
	}

	public void setSalaryPayoff(SalaryPayoff salaryPayoff) {
		/* 52 */this.salaryPayoff = salaryPayoff;
	}

	public String list() {
		/* 60 */QueryFilter filter = new QueryFilter(getRequest());
		/* 61 */List<SalaryPayoff> list = this.salaryPayoffService
				.getAll(filter);

		/* 63 */Type type = new TypeToken<List<SalaryPayoff>>() {
		}.getType();
		/* 64 */StringBuffer buff = new StringBuffer(
				"{success:true,'totalCounts':")
		/* 65 */.append(filter.getPagingBean().getTotalItems()).append(
				",result:");

		/* 67 */Gson gson = new GsonBuilder().setDateFormat(
				"yyyy-MM-dd HH:mm:ss").create();
		/* 68 */buff.append(gson.toJson(list, type));
		/* 69 */buff.append("}");

		/* 71 */this.jsonString = buff.toString();

		/* 73 */return "success";
	}

	public String multiDel() {
		/* 81 */String[] ids = getRequest().getParameterValues("ids");
		/* 82 */if (ids != null) {
			/* 83 */for (String id : ids) {
				/* 84 */this.salaryPayoffService.remove(new Long(id));
			}
		}

		/* 88 */this.jsonString = "{success:true}";

		/* 90 */return "success";
	}

	public String get() {
		/* 98 */SalaryPayoff salaryPayoff = (SalaryPayoff) this.salaryPayoffService
				.get(this.recordId);

		/* 100 */Gson gson = new GsonBuilder().setDateFormat(
				"yyyy-MM-dd HH:mm:ss").create();

		/* 102 */StringBuffer sb = new StringBuffer("{success:true,data:[");
		/* 103 */sb.append(gson.toJson(salaryPayoff));
		/* 104 */sb.append("]}");
		/* 105 */setJsonString(sb.toString());

		/* 107 */return "success";
	}

	public String save() {
		/* 113 */if (this.salaryPayoff.getRecordId() == null) {
			/* 114 */this.salaryPayoff.setCheckStatus(Short
					.valueOf(SalaryPayoff.CHECK_FLAG_NONE));
			/* 115 */this.salaryPayoff.setRegTime(new Date());
			/* 116 */this.salaryPayoff.setRegister(ContextUtil.getCurrentUser()
					.getFullname());
		}
		/* 118 */BigDecimal acutalAmount = this.salaryPayoff.getStandAmount()
				.add(this.salaryPayoff.getEncourageAmount())
				.subtract(this.salaryPayoff.getDeductAmount());
		/* 119 */if (this.salaryPayoff.getAchieveAmount().compareTo(
				new BigDecimal(0)) == 1) {
			/* 120 */acutalAmount = acutalAmount.add(this.salaryPayoff
					.getAchieveAmount());
		}
		/* 122 */this.salaryPayoff.setAcutalAmount(acutalAmount);
		/* 123 */this.salaryPayoffService.save(this.salaryPayoff);
		/* 124 */setJsonString("{success:true}");
		/* 125 */return "success";
	}

	public String check() {
		/* 133 */SalaryPayoff checkSalaryPayoff = (SalaryPayoff) this.salaryPayoffService
				.get(new Long(this.recordId.longValue()));
		/* 134 */checkSalaryPayoff.setCheckTime(new Date());
		/* 135 */checkSalaryPayoff.setCheckName(ContextUtil.getCurrentUser()
				.getFullname());
		/* 136 */checkSalaryPayoff.setCheckStatus(this.salaryPayoff
				.getCheckStatus());
		/* 137 */checkSalaryPayoff.setCheckOpinion(this.salaryPayoff
				.getCheckOpinion());
		Long userId=checkSalaryPayoff.getUserId();
		Double standardMoney=checkSalaryPayoff.getStandAmount()!=null?Double.parseDouble(checkSalaryPayoff.getStandAmount().toString()):0d;
		Double achieveAmount=checkSalaryPayoff.getAchieveAmount()!=null?Double.parseDouble(checkSalaryPayoff.getAchieveAmount().toString()):0d;
		Double deductAmount=checkSalaryPayoff.getDeductAmount()!=null?Double.parseDouble(checkSalaryPayoff.getDeductAmount().toString()):0d;
		Double encourageAmount=checkSalaryPayoff.getEncourageAmount()!=null?Double.parseDouble(checkSalaryPayoff.getEncourageAmount().toString()):0d;
		Double allamount=standardMoney+achieveAmount+encourageAmount-deductAmount;
		Integer month=checkSalaryPayoff.getStartTime().getMonth()+1;
		/* 138 */this.salaryPayoffService.save(checkSalaryPayoff);
		Boolean flag=this.salaryPayoffService.saveRealexecution(userId, allamount, month);
		if(flag!=true){
			setJsonString("{success:true,msg:'审核通过'}");
		}else{
			setJsonString("{success:true,msg:'审核通过'}");
		}
		return "success";
	}

	public String personal() {
		QueryFilter filter = new QueryFilter(getRequest());
		List<SalaryPayoff> list = this.salaryPayoffService
				.getAll(filter);
		StringBuffer buff = new StringBuffer(
				"{success:true,'totalCounts':")
		.append(filter.getPagingBean().getTotalItems()).append(
				",result:[");
		for (SalaryPayoff salaryDetail : list) {
			buff.append("{recordId:'")
			.append(salaryDetail.getRecordId())
			.append("',fullname:'")
			.append(salaryDetail.getFullname())
			.append("',profileNo:'")
			.append(salaryDetail.getProfileNo())
			.append("',idNo:'")
			.append(salaryDetail.getIdNo())
			.append("',standAmount:'")
			.append(salaryDetail.getStandAmount())
			.append("',acutalAmount:'")
			.append(salaryDetail.getAcutalAmount())
			.append("',startTime:'")
			.append(salaryDetail.getStartTime())
			.append("',endTime:'")
			.append(salaryDetail.getEndTime())
			.append("',checkStatus:'")
			.append(salaryDetail.getCheckStatus());
			List<StandSalaryItem> items = this.standSalaryItemService
					.getAllByStandardId(salaryDetail.getStandardId());
			String sql="select emp_profile.* from salary_payoff,emp_profile where salary_payoff.userId =emp_profile.userId and salary_payoff.recordId="+salaryDetail.getRecordId();
			List elist=this.standSalaryItemService.findDataList(sql);
			StringBuffer content = new StringBuffer(
					"<table class=\"table-info\" cellpadding=\"0\" cellspacing=\"1\" width=\"98%\" align=\"center\">");
			content.append("<tr>");
			if ((salaryDetail.getEncourageAmount() != new BigDecimal(0))
					&&
					(salaryDetail.getEncourageAmount() != null)) {
				content.append("<th>")
				.append("奖励金额</th><td>")
				.append(salaryDetail.getEncourageAmount())
				.append("</td>");
			}
			if ((salaryDetail.getDeductAmount() != new BigDecimal(0))
					&&
					(salaryDetail.getDeductAmount() != null)) {
				content.append("<th>")
				.append("扣除金额</th><td>")
				.append(salaryDetail.getDeductAmount())
				.append("</td>");
			}
			if ((salaryDetail.getStandAmount()!= new BigDecimal(0))
					&&
					(salaryDetail.getStandAmount() != null)) {
				content.append("<th>")
				.append("固定工资</th><td>")
				.append(salaryDetail.getStandAmount())
				.append("</td>");
			}
			content.append("</tr>");
			
			content.append("<tr>");
			if (elist!= null&&elist.size()>0) {
				content.append("<th>")
				.append("浮动工资</th><td>")
				.append(((Map)elist.get(0)).get("perCoefficient").toString())
				.append("</td>");
			}
			
			if ((salaryDetail.getPerCoefficient() != new BigDecimal(0))
					&&
					(salaryDetail.getPerCoefficient() != null)) {
				content.append("<th>")
				.append("绩效系数</th><td>")
				.append(salaryDetail.getPerCoefficient())
				.append("</td>");
			}
			if ((salaryDetail.getAchieveAmount() != new BigDecimal(0))
					&&
					(salaryDetail.getAchieveAmount() != null)) {
				content.append("<th>")
				.append("效绩金额</th><td>")
				.append(salaryDetail.getAchieveAmount())
				.append("</td>");
			}
			content.append("</tr>");
			
			content.append("<tr>");
			
			if ((salaryDetail.getIssuedAmount()!= new BigDecimal(0))
					&&
					(salaryDetail.getIssuedAmount() != null)) {
				content.append("<th>")
				.append("应发金额</th><td>")
				.append(salaryDetail.getIssuedAmount())
				.append("</td>");
			}
			if ((salaryDetail.getTaxableAmount() != new BigDecimal(0))
					&&
					(salaryDetail.getTaxableAmount() != null)) {
				content.append("<th>")
				.append("应税金额</th><td>")
				.append(salaryDetail.getTaxableAmount())
				.append("</td>");
			}
			if ((salaryDetail.getAcutalAmount() != new BigDecimal(0))
					&&
					(salaryDetail.getAcutalAmount() != null)) {
				content.append("<th>")
				.append("实发金额</th><td>")
				.append(salaryDetail.getAcutalAmount())
				.append("</td>");
			}
			content.append("</tr>");
			
			content.append("<tr>");
			if ((salaryDetail.getProvident() != new BigDecimal(0))
					&&
					(salaryDetail.getProvident() != null)) {
				content.append("<th>")
				.append("公积金</th><td>")
				.append(salaryDetail.getProvident())
				.append("</td>");
			}
			if ((salaryDetail.getInsurance()!= new BigDecimal(0))
					&&
					(salaryDetail.getInsurance() != null)) {
				content.append("<th>")
				.append("保险</th><td>")
				.append(salaryDetail.getInsurance())
				.append("</td>");
			}
			if ((salaryDetail.getSelftax() != new BigDecimal(0))
					&&
					(salaryDetail.getSelftax() != null)) {
				content.append("<th>")
				.append("个人所得税</th><td>")
				.append(salaryDetail.getSelftax())
				.append("</td>");
			}
			content.append("</tr>");
			
			content.append("<tr>");
			if ((salaryDetail.getProvident() != new BigDecimal(0))
					&&
					(salaryDetail.getProvident() != null)) {
				content.append("<th>")
				.append("描述</th><td colspan=5>")
				.append(salaryDetail.getMemo())
				.append("</td>");
			}
			content.append("</tr>");
			content.append("</table>");
			content.append("<table class=\"table-info\" cellpadding=\"0\" cellspacing=\"1\" width=\"98%\" align=\"center\"><tr>");
			content.append("<th>")
			.append("补助明细")
			.append("</th>");
			content.append("<th>")
			.append("扣款明细")
			.append("</th>");
			content.append("</tr>");
			content.append("<tr>");
			content.append("<td>")
			.append(salaryDetail.getEncourageDesc())
			.append("</td>");
			content.append("<td>")
			.append(salaryDetail.getDeductDesc())
			.append("</td>");
			content.append("</tr></table>");
			content.append("<table class=\"table-info\" cellpadding=\"0\" cellspacing=\"1\" width=\"98%\" align=\"center\"><tr>");
			for (StandSalaryItem item : items) {
				content.append("<th>")
				.append(item.getItemName())
				.append("</th>");
			}
			content.append("</tr><tr>");
			for (StandSalaryItem item2 : items) {
				content.append("<td>")
				.append(item2.getAmount())
				.append("</td>");
			}
			content.append("</tr></table>");
			buff.append("',content:'")
			.append(content.toString())
			.append("'},");
		}
		if (list.size() > 0) {
			buff.deleteCharAt(buff.length() - 1);
		}
		buff.append("]}");
		this.jsonString = buff.toString();
		return "success";
	}
	public String export(){
		String sql="select department.depName,app_user.fullname,app_user.userId," +//部门名称 用户名 用户id
				"emp_profile.position,emp_profile.accessionTime," +//岗位 入职时间
				"emp_profile.bankNo,emp_profile.standardMoney," +//银行卡号 基本工资
				"emp_profile.perCoefficient,emp_profile.positiveTime," +//绩效基数 转正日期
				"salary_payoff.perCoefficient as jxxs,salary_payoff.issuedAmount," +//绩效系数 应发金额
				"salary_payoff.taxableAmount,salary_payoff.achieveAmount," +//应税金额 绩效奖金
				"salary_payoff.encourageAmount,salary_payoff.encourageDesc," +//奖励金额 奖励描述
				"salary_payoff.provident,salary_payoff.insurance," +//公积金 保险
				"salary_payoff.deductAmount,salary_payoff.deductDesc," +//扣款  扣款描述
				"salary_payoff.startTime,salary_payoff.selftax," +//工资计算开始时间  个人所得税
				"salary_payoff.acutalAmount,salary_payoff.regTime,salary_payoff.memo " +//实发金额 备注
				"from salary_payoff,department,app_user,emp_profile " +
				"where salary_payoff.userId=app_user.userId " +
				"and emp_profile.userId=app_user.userId " +
				"and department.depId=app_user.depId";
		String fullname=getRequest().getParameter("Q_fullname_S_LK");
		String checkStatus=getRequest().getParameter("Q_checkStatus_SN_EQ");
		String startTime=getRequest().getParameter("Q_startTime_D_GE");
		String endTime=getRequest().getParameter("Q_endTime_D_LE");
		if(fullname!=null&&fullname.length()>0){
			sql+=" and salary_payoff.fullname like '%"+fullname+"%'";
		}
		if(checkStatus!=null&&checkStatus.length()>0){
			sql+=" and salary_payoff.checkStatus="+checkStatus;		
		}
		if(startTime!=null&&startTime.length()>0){
			sql+=" and date_format(salary_payoff.startTime,'%Y-%m-%d')>=date_format('"+startTime+"','%Y-%m-%d')";	
		}
		if(endTime!=null&&endTime.length()>0){
			sql+=" and date_format(salary_payoff.endTime,'%Y-%m-%d')<=date_format('"+endTime+"','%Y-%m-%d')";	
		}
		ExportSalaryService exportSalaryService=(ExportSalaryService) AppUtil.getBean("exportSalaryService");
		String fileRootPath=getRequest().getRealPath("exportFile");
		String filepath=exportSalaryService.exportSalary(fileRootPath, "薪资", "salarypayoff", sql, "3");
		Gson gson = new Gson();
		this.jsonString ="{success:true,data:"+gson.toJson("exportFile/"+filepath)+"}";
		return "success";
	}
	
	public String count(){
		HrPaKpiPBC2UserCmpService hrPaKpiPBC2UserCmpService=(HrPaKpiPBC2UserCmpService) AppUtil.getBean("hrPaKpiPBC2UserCmpService");
		String deptid=getRequest().getParameter("depid");
		String userid= ContextUtil.getCurrentUserId().toString();
		hrPaKpiPBC2UserCmpService.saveSalarDetail(userid, deptid);
		setJsonString("{success:true}");
		return "success";
	}
}
