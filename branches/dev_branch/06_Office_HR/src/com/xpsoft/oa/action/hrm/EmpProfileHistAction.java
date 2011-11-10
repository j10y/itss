package com.xpsoft.oa.action.hrm;

import java.util.List;

import javax.annotation.Resource;

import com.xpsoft.core.command.QueryFilter;
import com.xpsoft.core.util.JsonUtil;
import com.xpsoft.core.web.action.BaseAction;
import com.xpsoft.oa.model.hrm.EmpProfileHist;
import com.xpsoft.oa.service.hrm.EmpProfileHistService;

import flexjson.JSONSerializer;

public class EmpProfileHistAction extends BaseAction {

	@Resource
	private EmpProfileHistService empProfileHistService;
	private EmpProfileHist empProfileHist;
	private Long profileId;

	public Long getProfileId() {
		return this.profileId;
	}

	public void setProfileId(Long profileId) {
		this.profileId = profileId;
	}

	public EmpProfileHist getEmpProfileHist() {
		return this.empProfileHist;
	}

	public void setEmpProfileHist(EmpProfileHist empProfileHist) {
		this.empProfileHist = empProfileHist;
	}

	public String list() {
		QueryFilter filter = new QueryFilter(getRequest());

		List list = this.empProfileHistService.getAll(filter);

		StringBuffer buff = new StringBuffer(
				"{success:true,'totalCounts':")
		.append(filter.getPagingBean().getTotalItems()).append(
				",result:");

		JSONSerializer serializer = JsonUtil
				.getJSONSerializer(new String[] { "birthday", "startWorkDate",
						"checktime", "createtime", "modifiedDate" });
		buff.append(serializer.exclude(
				new String[] { "class", "job.class", "job.department" })
				.serialize(list));
		buff.append("}");

		this.jsonString = buff.toString();

		return "success";
	}
}