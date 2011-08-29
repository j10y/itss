package com.xpsoft.oa.action.hrm;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;

import com.google.gson.Gson;
import com.xpsoft.core.command.QueryFilter;
import com.xpsoft.core.web.action.BaseAction;
import com.xpsoft.oa.model.hrm.RewardsPunishmentsType;
import com.xpsoft.oa.service.hrm.RewardsPunishmentsTypeService;

import flexjson.JSONSerializer;

public class RewardsPunishmentsTypeAction extends BaseAction {

	@Resource
	private RewardsPunishmentsTypeService rewardsPunishmentsTypeService;
	private RewardsPunishmentsType rewardsPunishmentsType;
	private Long rpTypeId;

	public Long getRpTypeId() {
		return rpTypeId;
	}

	public void setRpTypeId(Long rpTypeId) {
		this.rpTypeId = rpTypeId;
	}

	public RewardsPunishmentsType getRewardsPunishmentsType() {
		/* 43 */return this.rewardsPunishmentsType;
	}

	public void setRewardsPunishmentsType(
			RewardsPunishmentsType rewardsPunishmentsType) {
		/* 47 */this.rewardsPunishmentsType = rewardsPunishmentsType;
	}

	public String list() {
		/* 55 */QueryFilter filter = new QueryFilter(getRequest());
		/* 56 */List list = this.rewardsPunishmentsTypeService.getAll(filter);

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
		/* 76 */QueryFilter filter = new QueryFilter(getRequest());
		/* 77 */List<RewardsPunishmentsType> list = this.rewardsPunishmentsTypeService
				.getAll(filter);
		/* 78 */StringBuffer sb = new StringBuffer("[");
		/* 79 */for (RewardsPunishmentsType type : list) {
			/* 80 */sb.append("['").append(type.getTypeId()).append("','")
					.append(type.getTypeName()).append("'],");
		}
		/* 82 */if (list.size() > 0) {
			/* 83 */sb.deleteCharAt(sb.length() - 1);
		}
		/* 85 */sb.append("]");
		/* 86 */setJsonString(sb.toString());
		/* 87 */return "success";
	}

	public String multiDel() {
		/* 96 */String[] ids = getRequest().getParameterValues("ids");
		/* 97 */if (ids != null) {
			/* 98 */for (String id : ids) {
				/* 99 */// this.rewardsPunishmentsTypeService.remove(new
						// Long(id));
				RewardsPunishmentsType type = this.rewardsPunishmentsTypeService
						.get(Long.valueOf(id));
				type.setDeleteFlag(Integer.valueOf(1));
				this.rewardsPunishmentsTypeService.save(type);

			}
		}

		/* 103 */this.jsonString = "{success:true}";

		/* 105 */return "success";
	}

	public String get() {
		/* 113 */RewardsPunishmentsType rewardsPunishmentsType = (RewardsPunishmentsType) this.rewardsPunishmentsTypeService
				.get(this.rpTypeId);

		/* 117 */StringBuffer sb = new StringBuffer("{success:true,data:");

		/* 119 */JSONSerializer serializer = new JSONSerializer();
		/* 120 */sb.append(serializer.exclude(
				new String[] { "class", "department.class" }).serialize(
				rewardsPunishmentsType));
		/* 121 */sb.append("}");
		/* 122 */setJsonString(sb.toString());

		/* 124 */return "success";
	}

	public String save() {
		this.rewardsPunishmentsType.setDeleteFlag(Integer.valueOf(0));
		/* 130 */this.rewardsPunishmentsTypeService
				.save(this.rewardsPunishmentsType);
		/* 131 */setJsonString("{success:true}");
		/* 132 */return "success";
	}
}
