package com.xpsoft.oa.action.hrm;

import java.util.List;

import javax.annotation.Resource;

import com.xpsoft.core.command.QueryFilter;
import com.xpsoft.core.util.ContextUtil;
import com.xpsoft.core.util.JsonUtil;
import com.xpsoft.core.web.action.BaseAction;
import com.xpsoft.oa.model.hrm.IncomeTax;
import com.xpsoft.oa.model.hrm.IncomeTaxItem;
import com.xpsoft.oa.model.system.AppUser;
import com.xpsoft.oa.service.hrm.IncomeTaxItemService;

import flexjson.JSONSerializer;

public class IncomeTaxItemAction extends BaseAction {

	@Resource
	private IncomeTaxItemService incomeTaxItemService;
	private IncomeTaxItem incomeTaxItem;
	private Long itiId;

	public Long getItiId() {
		return itiId;
	}

	public void setItiId(Long itiId) {
		this.itiId = itiId;
	}

	public IncomeTaxItem getBudget() {
		/* 43 */return this.incomeTaxItem;
	}

	public void setBudget(IncomeTaxItem incomeTaxItem) {
		/* 47 */this.incomeTaxItem = incomeTaxItem;
	}

	public String list() {
		QueryFilter filter = new QueryFilter(getRequest());
		List list = this.incomeTaxItemService.getAll(filter);

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



	public String multiDel() {
		String[] ids = getRequest().getParameterValues("ids");
		if (ids != null) {
			for (String id : ids) {
				this.incomeTaxItemService.remove(new Long(id));
			}
		}
		/* 103 */this.jsonString = "{success:true}";

		/* 105 */return "success";
	}

	public String get() {
		IncomeTaxItem incomeTaxItem = (IncomeTaxItem) this.incomeTaxItemService.get(this.itiId);

		StringBuffer sb = new StringBuffer("{success:true,totalCounts:1,data:[");

		//JSONSerializer serializer = new JSONSerializer();
		JSONSerializer serializer = JsonUtil
		.getJSONSerializer(new String[] { "createDate" });
		sb.append(serializer.exclude(
						new String[] { "class"})
						.serialize(incomeTaxItem));
		sb.append("]}");
		setJsonString(sb.toString());
		return "success";
	}

	public String save() {
		AppUser user = ContextUtil.getCurrentUser();
		IncomeTax it = new IncomeTax();
		it.setItId(Long.valueOf(getRequest().getParameter("incomeTaxItem.incomeTax.itId")));
		this.incomeTaxItemService.save(this.incomeTaxItem);
		setJsonString("{success:true,itiId:'" + incomeTaxItem.getItiId() + "'}");
		return "success";
	}
	
	public IncomeTaxItem getIncomeTaxItem() {
		return incomeTaxItem;
	}

	public void setIncomeTaxItem(IncomeTaxItem incomeTaxItem) {
		this.incomeTaxItem = incomeTaxItem;
	}
	
	
}
