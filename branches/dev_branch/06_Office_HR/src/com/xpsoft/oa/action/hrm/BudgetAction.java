package com.xpsoft.oa.action.hrm;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import com.xpsoft.core.command.QueryFilter;
import com.xpsoft.core.util.ContextUtil;
import com.xpsoft.core.util.JsonUtil;
import com.xpsoft.core.web.action.BaseAction;
import com.xpsoft.oa.model.hrm.Budget;
import com.xpsoft.oa.model.system.AppUser;
import com.xpsoft.oa.model.system.Department;
import com.xpsoft.oa.service.hrm.BudgetService;

import flexjson.JSONSerializer;

public class BudgetAction extends BaseAction {

	@Resource
	private BudgetService budgetService;
	private Budget budget;
	private Long budgetId;

	public Long getBudgetId() {
		/* 35 */return this.budgetId;
	}

	public void setBudgetId(Long budgetId) {
		/* 39 */this.budgetId = budgetId;
	}

	public Budget getBudget() {
		/* 43 */return this.budget;
	}

	public void setBudget(Budget budget) {
		/* 47 */this.budget = budget;
	}

	public String list() {
		QueryFilter filter = new QueryFilter(getRequest());
		List list = this.budgetService.getAll(filter);

		StringBuffer buff = new StringBuffer(
			"{success:true,'totalCounts':")
			.append(filter.getPagingBean().getTotalItems()).append(
			",result:");

//		JSONSerializer serializer = new JSONSerializer();
		JSONSerializer serializer = JsonUtil
		.getJSONSerializer(new String[] { "beginDate", "endDate", "createDate", "modifyDate" });
		buff.append(serializer.exclude(new String[] { "class" })
			.serialize(list));
		buff.append("}");

		this.jsonString = buff.toString();

		return "success";
	}

	public String combo() {
		QueryFilter filter = new QueryFilter(getRequest());
		List<Budget> list = this.budgetService.getAll(filter);
		StringBuffer sb = new StringBuffer("[");
		for (Budget budget : list) {
			sb.append("['").append(budget.getBudgetId()).append("','")
					.append(budget.getName()).append("'],");
		}
		if (list.size() > 0) {
			sb.deleteCharAt(sb.length() - 1);
		}
		sb.append("]");
		setJsonString(sb.toString());
		return "success";
	}

	public String multiDel() {
		/* 96 */String[] ids = getRequest().getParameterValues("ids");
		/* 97 */if (ids != null) {
			/* 98 */for (String id : ids) {
				/* 99 */this.budgetService.remove(new Long(id));
			}
		}

		/* 103 */this.jsonString = "{success:true}";

		/* 105 */return "success";
	}

	public String get() {
		Budget budget = (Budget) this.budgetService.get(this.budgetId);

		StringBuffer sb = new StringBuffer("{success:true,totalCounts:1,data:[");

		//JSONSerializer serializer = new JSONSerializer();
		JSONSerializer serializer = JsonUtil
		.getJSONSerializer(new String[] { "beginDate", "endDate", "createDate", "modifyDate" });
		sb.append(serializer.exclude(
						new String[] { "class", "department.class" })
						.serialize(budget));
		sb.append("]}");
		setJsonString(sb.toString().replaceAll("\\s[\\d]{2}:[\\d]{2}:[\\d]{2}", ""));
		return "success";
	}

	public String save() {
		AppUser user = ContextUtil.getCurrentUser();
		if(this.budget.getBudgetId()!=null){
			this.budget.setModifyDate(new Date());
			this.budget.setModifyPerson(user);
			//创建人和创建时间永远不变
//			Budget budget = this.budgetService.get(this.budget.getBudgetId());
//			this.budget.setCreateDate(budget.getCreateDate());
//			this.budget.setCreatePerson(budget.getCreatePerson());
		}else{
			this.budget.setCreateDate(new Date());
			this.budget.setCreatePerson(user);
		}
		this.budget.setBelongDept(new Department(Long.valueOf(getRequest().getParameter("budget.belongDept.depId"))));
		this.budgetService.save(this.budget);
		setJsonString("{success:true,budgetId:'" + budget.getBudgetId() + "'}");
		return "success";
	}
}
