package com.xpsoft.oa.action.hrm;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.xpsoft.core.command.QueryFilter;
import com.xpsoft.core.util.ContextUtil;
import com.xpsoft.core.web.action.BaseAction;
import com.xpsoft.core.web.paging.PagingBean;
import com.xpsoft.oa.model.hrm.SalaryPayoff;
import com.xpsoft.oa.model.hrm.StandSalaryItem;
import com.xpsoft.oa.model.system.AppUser;
import com.xpsoft.oa.service.hrm.SalaryPayoffService;
import com.xpsoft.oa.service.hrm.StandSalaryItemService;
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
		/* 138 */this.salaryPayoffService.save(checkSalaryPayoff);
		setJsonString("{success:true,msg:'审核通过'}");
		/* 139 */return "success";
	}

	public String personal() {
		/* 146 */QueryFilter filter = new QueryFilter(getRequest());
		/* 147 */List<SalaryPayoff> list = this.salaryPayoffService
				.getAll(filter);

		/* 151 */StringBuffer buff = new StringBuffer(
				"{success:true,'totalCounts':")
		/* 152 */.append(filter.getPagingBean().getTotalItems()).append(
				",result:[");
		/* 153 */for (SalaryPayoff salaryDetail : list) {
			/* 154 */buff.append("{recordId:'")
			/* 155 */.append(salaryDetail.getRecordId())
			/* 156 */.append("',fullname:'")
			/* 157 */.append(salaryDetail.getFullname())
			/* 158 */.append("',profileNo:'")
			/* 159 */.append(salaryDetail.getProfileNo())
			/* 160 */.append("',idNo:'")
			/* 161 */.append(salaryDetail.getIdNo())
			/* 162 */.append("',standAmount:'")
			/* 163 */.append(salaryDetail.getStandAmount())
			/* 164 */.append("',acutalAmount:'")
			/* 165 */.append(salaryDetail.getAcutalAmount())
			/* 166 */.append("',startTime:'")
			/* 167 */.append(salaryDetail.getStartTime())
			/* 168 */.append("',endTime:'")
			/* 169 */.append(salaryDetail.getEndTime())
			/* 170 */.append("',checkStatus:'")
			/* 171 */.append(salaryDetail.getCheckStatus());
			/* 172 */List<StandSalaryItem> items = this.standSalaryItemService
					.getAllByStandardId(salaryDetail.getStandardId());
			/* 173 */StringBuffer content = new StringBuffer(
					"<table class=\"table-info\" cellpadding=\"0\" cellspacing=\"1\" width=\"98%\" align=\"center\"><tr>");

			/* 175 */if ((salaryDetail.getEncourageAmount() != new BigDecimal(0))
					&&
					/* 176 */(salaryDetail.getEncourageAmount() != null)) {
				/* 177 */content.append("<th>")
				/* 178 */.append("奖励金额</th><td>")
				/* 179 */.append(salaryDetail.getEncourageAmount())
				/* 180 */.append("</td>");
			}

			/* 183 */if ((salaryDetail.getEncourageAmount() != new BigDecimal(0))
					&&
					/* 184 */(salaryDetail.getEncourageAmount() != null)) {
				/* 185 */content.append("<th>")
				/* 186 */.append("扣除金额</th><td>")
				/* 187 */.append(salaryDetail.getDeductAmount())
				/* 188 */.append("</td>");
			}

			/* 191 */if ((salaryDetail.getEncourageAmount() != new BigDecimal(0))
					&&
					/* 192 */(salaryDetail.getEncourageAmount() != null)) {
				/* 193 */content.append("<th>")
				/* 194 */.append("效绩金额</th><td>")
				/* 195 */.append(salaryDetail.getAchieveAmount())
				/* 196 */.append("</td>");
			}
			content.append("</tr>");
			content.append("</tr>");
			/* 175 */if ((salaryDetail.getProvident() != new BigDecimal(0))
					&&
					/* 176 */(salaryDetail.getProvident() != null)) {
				/* 177 */content.append("<th>")
				/* 178 */.append("公积金</th><td>")
				/* 179 */.append(salaryDetail.getProvident())
				/* 180 */.append("</td>");
			}

			/* 183 */if ((salaryDetail.getInsurance()!= new BigDecimal(0))
					&&
					/* 184 */(salaryDetail.getInsurance() != null)) {
				/* 185 */content.append("<th>")
				/* 186 */.append("保险</th><td>")
				/* 187 */.append(salaryDetail.getInsurance())
				/* 188 */.append("</td>");
			}

			/* 191 */if ((salaryDetail.getSelftax() != new BigDecimal(0))
					&&
					/* 192 */(salaryDetail.getSelftax() != null)) {
				/* 193 */content.append("<th>")
				/* 194 */.append("个人所得税</th><td>")
				/* 195 */.append(salaryDetail.getSelftax())
				/* 196 */.append("</td>");
			}
			content.append("</tr>");
			/* 198 */content
					.append("</table><table class=\"table-info\" cellpadding=\"0\" cellspacing=\"1\" width=\"98%\" align=\"center\"><tr>");
			/* 199 */for (StandSalaryItem item : items) {
				/* 200 */content.append("<th>")
				/* 201 */.append(item.getItemName())
				/* 202 */.append("</th>");
			}
			/* 204 */content.append("</tr><tr>");
			/* 205 */for (StandSalaryItem item2 : items) {
				/* 206 */content.append("<td>")
				/* 207 */.append(item2.getAmount())
				/* 208 */.append("</td>");
			}
			/* 210 */content.append("</tr></table>");
			/* 211 */buff.append("',content:'")
			/* 212 */.append(content.toString())
			/* 213 */.append("'},");
		}
		/* 215 */if (list.size() > 0) {
			/* 216 */buff.deleteCharAt(buff.length() - 1);
		}
		/* 218 */buff.append("]}");

		/* 220 */this.jsonString = buff.toString();
		/* 221 */return "success";
	}
	public String export(){
//		QueryFilter filter = new QueryFilter(getRequest());
//		List<SalaryPayoff> list = this.salaryPayoffService
//				.getAll(filter);
		String [] titles=new String[18];
		String [] proNames=new String[18];
		String sql="select department.depName,app_user.fullname,emp_profile.position,emp_profile.accessionTime," +
				"emp_profile.bankNo,emp_profile.standardMoney,emp_profile.perCoefficient,salary_payoff.achieveAmount," +
				"salary_payoff.encourageAmount,salary_payoff.encourageDesc,salary_payoff.provident,salary_payoff.insurance," +
				"salary_payoff.deductAmount,salary_payoff.deductDesc," +
				"salary_payoff.selftax,salary_payoff.acutalAmount,salary_payoff.regTime,salary_payoff.memo " +
				"from salary_payoff,department,app_user,emp_profile " +
				"where salary_payoff.userId=app_user.userId and emp_profile.userId=app_user.userId and department.depId=app_user.depId";
		List<Map> list=this.salaryPayoffService.findDataList(sql);
		String fileRootPath=getRequest().getRealPath("exportFile");
		titles[0]="部门";
		titles[1]="姓名";
		titles[2]="岗位";
		titles[3]="入职时间";
		titles[4]="银行卡号";
		titles[5]="固定工资";
		titles[6]="浮动工资";
		titles[7]="绩效奖金";
		titles[8]="补贴";
		titles[9]="补贴详细";
		titles[10]="公积金";
		titles[11]="保险";
		titles[12]="扣除";
		titles[13]="扣除详细";
		titles[14]="个人所得税";
		titles[15]="实发金额";
		titles[16]="统计时间";
		titles[17]="备注";
		
		proNames[0]="depName";
		proNames[1]="fullname";
		proNames[2]="position";
		proNames[3]="accessionTime";
		proNames[4]="bankNo";
		proNames[5]="standardMoney";
		proNames[6]="perCoefficient";
		proNames[7]="achieveAmount";
		proNames[8]="encourageAmount";
		proNames[9]="encourageDesc";
		proNames[10]="provident";
		proNames[11]="insurance";
		proNames[12]="deductAmount";
		proNames[13]="deductDesc";
		proNames[14]="selftax";
		proNames[15]="acutalAmount";
		proNames[16]="regTime";
		proNames[17]="memo";
		
//		proNames[0]="fullname";
//		proNames[1]="profileNo";
//		proNames[2]="idNo";
//		proNames[3]="standAmount";
//		proNames[4]="encourageAmount";
//		proNames[5]="deductAmount";
//		proNames[6]="achieveAmount";
//		proNames[7]="provident";
//		proNames[8]="insurance";
//		proNames[9]="selftax";
//		proNames[10]="acutalAmount";
//		proNames[11]="regTime";
//		proNames[12]="memo";
		
		Gson gson = new Gson();
		this.jsonString ="{success:true,data:"+gson.toJson("exportFile/"+this.salaryPayoffService.exportData(fileRootPath, "薪资", "salarypayoff", titles, proNames, list, "maplist"))+"}";
		return "success";
	}
}
