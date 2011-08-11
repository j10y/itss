package com.xpsoft.oa.action.hrm;

import java.util.List;

import javax.annotation.Resource;

import com.xpsoft.core.command.QueryFilter;
import com.xpsoft.core.web.action.BaseAction;
import com.xpsoft.oa.model.archive.ArchivesType;
import com.xpsoft.oa.model.hrm.BudgetItem;
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
		/* 130 */this.budgetItemService.save(this.budgetItem);
		/* 131 */setJsonString("{success:true}");
		/* 132 */return "success";
	}
	
	public String tree(){
		List<BudgetItem> itemList = this.budgetItemService.getAll();
	 
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
