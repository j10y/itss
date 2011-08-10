package com.xpsoft.oa.action.hrm;

import java.util.List;

import javax.annotation.Resource;

import com.xpsoft.core.command.QueryFilter;
import com.xpsoft.core.web.action.BaseAction;
import com.xpsoft.oa.model.hrm.Budget;
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
		/* 55 */QueryFilter filter = new QueryFilter(getRequest());
		/* 56 */List list = this.budgetService.getAll(filter);

		/* 59 */StringBuffer buff = new StringBuffer(
				"{success:true,'totalCounts':")
		/* 60 */.append(filter.getPagingBean().getTotalItems()).append(
				",result:");

		/* 64 */JSONSerializer serializer = new JSONSerializer();
		/* 65 */buff.append(serializer.exclude(new String[] { "class" })
				.serialize(list));
		/* 66 */buff.append("}");

		/* 68 */this.jsonString = buff.toString();

		/* 70 */return "success";
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
		/* 113 */Budget budget = (Budget) this.budgetService.get(this.budgetId);

		/* 117 */StringBuffer sb = new StringBuffer("{success:true,data:");

		/* 119 */JSONSerializer serializer = new JSONSerializer();
		/* 120 */sb
				.append(serializer.exclude(
						new String[] { "class", "department.class" })
						.serialize(budget));
		/* 121 */sb.append("}");
		/* 122 */setJsonString(sb.toString());

		/* 124 */return "success";
	}

	public String save() {
		/* 130 */this.budgetService.save(this.budget);
		/* 131 */setJsonString("{success:true}");
		/* 132 */return "success";
	}
}
