package com.xpsoft.oa.action.hrm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.xpsoft.core.command.QueryFilter;
import com.xpsoft.core.util.ContextUtil;
import com.xpsoft.core.web.action.BaseAction;
import com.xpsoft.oa.model.hrm.Budget;
import com.xpsoft.oa.model.hrm.BudgetItem;
import com.xpsoft.oa.model.system.AppUser;
import com.xpsoft.oa.service.hrm.BudgetItemService;

import flexjson.JSONSerializer;

public class BudgetItemAction extends BaseAction {

	@Resource
	private BudgetItemService budgetItemService;
	private BudgetItem budgetItem;
	private Long budgetItemId;

	public Long getBudgetItemId() {
		/* 35 */return this.budgetItemId;
	}

	public void setBudgetItemId(Long budgetItemId) {
		/* 39 */this.budgetItemId = budgetItemId;
	}

	public BudgetItem getBudgetItem() {
		/* 43 */return this.budgetItem;
	}

	public void setBudgetItem(BudgetItem budgetItem) {
		/* 47 */this.budgetItem = budgetItem;
	}

	public String list() {
		/* 55 */QueryFilter filter = new QueryFilter(getRequest());
		/* 56 */List list = this.budgetItemService.getAll(filter);

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
		List<BudgetItem> list = this.budgetItemService.getAll(filter);
		StringBuffer sb = new StringBuffer("[");
		for (BudgetItem budgetItem : list) {
			sb.append("['").append(budgetItem.getBudgetItemId()).append("','")
					.append(budgetItem.getName()).append("'],");
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
				/* 99 */this.budgetItemService.remove(new Long(id));
			}
		}

		/* 103 */this.jsonString = "{success:true}";

		/* 105 */return "success";
	}

	public String get() {
		/* 113 */BudgetItem budgetItem = (BudgetItem) this.budgetItemService.get(this.budgetItemId);

		/* 117 */StringBuffer sb = new StringBuffer("{success:true,data:");

		/* 119 */JSONSerializer serializer = new JSONSerializer();
		/* 120 */sb
				.append(serializer.exclude(
						new String[] { "class", "department.class" })
						.serialize(budgetItem));
		/* 121 */sb.append("}");
		/* 122 */setJsonString(sb.toString());

		/* 124 */return "success";
	}

	public String save() {
		AppUser user = ContextUtil.getCurrentUser();
		
		BudgetItem parent = getRequest().getParameter("budgetItem.parent.budgetItemId")!=null && 
			!getRequest().getParameter("budgetItem.parent.budgetItemId").equals("0") ? 
			new BudgetItem() : null;
		if(parent!=null){
			parent.setBudgetItemId(Long.valueOf(getRequest().getParameter("budgetItem.parent.budgetItemId")));
		}
		this.budgetItem.setParent(parent);
		
		Budget budget = new Budget();
		budget.setBudgetId(Long.valueOf(getRequest().getParameter("budgetItem.budget.budgetId")));
		this.budgetItem.setBudget(budget);
		this.budgetItem.setDeleteFlag(Integer.valueOf(0));//未删除
		this.budgetItemService.save(this.budgetItem);
		setJsonString("{success:true, budgetItemId:'" + this.budgetItem.getBudgetItemId() + "'}");
		return "success";
	}
	
	public String tree(){
		List<BudgetItem> itemList = null;
		if(getRequest().getParameter("budgetId")!=null){
			Map map = new HashMap();
			map.put("Q_budget.budgetId_L_EQ", getRequest().getParameter("budgetId"));
			QueryFilter paramQueryFilter = new QueryFilter(map);
			itemList = this.budgetItemService.getAll(paramQueryFilter);
		}else{
			itemList = new ArrayList();
		}
	 
		//这里需要对其进行树形结构的json重组
		
		StringBuffer sb = new StringBuffer();
		sb.append("[{id:'0',text:'所有成本要素',expanded:true,children:[");
		for (BudgetItem item : itemList) {
			sb.append("{id:'" + item.getBudgetItemId()).append("',text:'" + item.getName()).append("',leaf:true,expanded:true},");
		}
		if (itemList.size() > 0) {
			sb.deleteCharAt(sb.length() - 1);
		}
		sb.append("]}]");
		setJsonString(sb.toString());
		return "success";
	}
}
