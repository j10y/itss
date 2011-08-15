package com.xpsoft.oa.action.hrm;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.xpsoft.core.command.QueryFilter;
import com.xpsoft.core.util.ContextUtil;
import com.xpsoft.core.util.JsonUtil;
import com.xpsoft.core.web.action.BaseAction;
import com.xpsoft.oa.model.hrm.Budget;
import com.xpsoft.oa.model.hrm.BudgetItem;
import com.xpsoft.oa.model.hrm.RealExecution;
import com.xpsoft.oa.model.system.AppUser;
import com.xpsoft.oa.service.hrm.RealExecutionService;

import flexjson.JSONSerializer;

public class RealExecutionAction extends BaseAction {

	@Resource
	private RealExecutionService realExecutionService;
	private RealExecution realExecution;
	private Long realExecutionId;

	public Long getRealExecutionId() {
		/* 35 */return this.realExecutionId;
	}

	public void setRealExecutionId(Long realExecutionId) {
		/* 39 */this.realExecutionId = realExecutionId;
	}

	public RealExecution getBudget() {
		/* 43 */return this.realExecution;
	}

	public void setBudget(RealExecution realExecution) {
		/* 47 */this.realExecution = realExecution;
	}

	public String list() {
		QueryFilter filter = new QueryFilter(getRequest());
		List list = this.realExecutionService.getAll(filter);

		StringBuffer buff = new StringBuffer(
			"{success:true,'totalCounts':")
			.append(filter.getPagingBean().getTotalItems()).append(
			",result:");

//		JSONSerializer serializer = new JSONSerializer();
		JSONSerializer serializer = JsonUtil
		.getJSONSerializer(new String[] { "inputDate"});
		buff.append(serializer.exclude(new String[] { "class" })
			.serialize(list));
		buff.append("}");

		this.jsonString = buff.toString();

		return "success";
	}

	public String combo() {
		QueryFilter filter = new QueryFilter(getRequest());
		List<RealExecution> list = this.realExecutionService.getAll(filter);
		StringBuffer sb = new StringBuffer("[");
		for (RealExecution realExecution : list) {
			sb.append("['").append(realExecution.getRealExecutionId()).append("','")
					.append(realExecution.getRemark()).append("'],");
		}
		if (list.size() > 0) {
			sb.deleteCharAt(sb.length() - 1);
		}
		sb.append("]");
		setJsonString(sb.toString());
		return "success";
	}

	public String multiDel() {
		String[] ids = getRequest().getParameterValues("ids");
		if (ids != null) {
			for (String id : ids) {
				//this.realExecutionService.remove(new Long(id));
				RealExecution realExecution =  this.realExecutionService.get(new Long(id));
				realExecution.setDeleteFlag(new Integer("1"));
				this.realExecutionService.save(realExecution);
			}
		}

		/* 103 */this.jsonString = "{success:true}";

		/* 105 */return "success";
	}

	public String get() {
		RealExecution realExecution = (RealExecution) this.realExecutionService.get(this.realExecutionId);

		StringBuffer sb = new StringBuffer("{success:true,totalCounts:1,data:[");

		//JSONSerializer serializer = new JSONSerializer();
		JSONSerializer serializer = JsonUtil
		.getJSONSerializer(new String[] { "inputDate" });
		sb.append(serializer.exclude(
						new String[] { "class", "department.class" })
						.serialize(realExecution));
		sb.append("]}");
		setJsonString(sb.toString());
		return "success";
	}

	public String save() {
		AppUser user = ContextUtil.getCurrentUser();
		if(this.realExecution.getRealExecutionId()!=null){
		}else{
			this.realExecution.setInputDate(new Date());
		}
		this.realExecution.setDeleteFlag(new Integer(0));
		
		Budget budget = new Budget();
		budget.setBudgetId(Long.valueOf(getRequest().getParameter("realExecution.budget.budgetId")));
		this.realExecution.setBudget(budget);
		BudgetItem budgetItem = new BudgetItem();
		budgetItem.setBudgetItemId(Long.valueOf(getRequest().getParameter("realExecution.budgetItem.budgetItemId")));
		this.realExecution.setBudgetItem(budgetItem);
		
		this.realExecutionService.save(this.realExecution);
		setJsonString("{success:true,realExecutionId:'" + realExecution.getRealExecutionId() + "'}");
		return "success";
	}
	
	public String tree() {

		//List list = this.realExecutionService.getAll(filter);

		List<Map> list = this.realExecutionService.treeStatics(Long.valueOf(1));
		

		StringBuffer buff = new StringBuffer();

		JSONSerializer serializer = new JSONSerializer();
		buff.append(serializer.exclude(new String[] { "class" })
			.serialize(list));
		
		this.jsonString = buff.toString();
		
		return "success";
	}

	public RealExecution getRealExecution() {
		return realExecution;
	}

	public void setRealExecution(RealExecution realExecution) {
		this.realExecution = realExecution;
	}
	
	
}
