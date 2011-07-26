package com.xpsoft.oa.action.personal;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.xpsoft.core.command.QueryFilter;
import com.xpsoft.core.web.action.BaseAction;
import com.xpsoft.core.web.paging.PagingBean;
import com.xpsoft.oa.model.personal.DutySystem;
import com.xpsoft.oa.model.personal.DutySystemSections;
import com.xpsoft.oa.service.personal.DutySystemService;
import java.lang.reflect.Type;
import java.util.List;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

public class DutySystemAction extends BaseAction {

	@Resource
	private DutySystemService dutySystemService;
	private DutySystem dutySystem;
	private Long systemId;

	public Long getSystemId() {
		/* 33 */return this.systemId;
	}

	public void setSystemId(Long systemId) {
		/* 37 */this.systemId = systemId;
	}

	public DutySystem getDutySystem() {
		/* 41 */return this.dutySystem;
	}

	public void setDutySystem(DutySystem dutySystem) {
		/* 45 */this.dutySystem = dutySystem;
	}

	public String list() {
		/* 53 */QueryFilter filter = new QueryFilter(getRequest());
		/* 54 */List<DutySystem> list = this.dutySystemService.getAll(filter);

		/* 56 */Type type = new TypeToken<List<DutySystem>>() {
		}
		/* 56 */.getType();
		/* 57 */StringBuffer buff = new StringBuffer(
				"{success:true,'totalCounts':")
		/* 58 */.append(filter.getPagingBean().getTotalItems()).append(
				",result:");

		/* 60 */Gson gson = new Gson();
		/* 61 */buff.append(gson.toJson(list, type));
		/* 62 */buff.append("}");

		/* 64 */this.jsonString = buff.toString();

		/* 66 */return "success";
	}

	public String setting() {
		/* 72 */if (this.systemId != null) {
			/* 73 */this.dutySystem = ((DutySystem) this.dutySystemService
					.get(this.systemId));
		}
		/* 75 */StringBuffer buff = new StringBuffer("{success:true,result:[{");

		/* 77 */if (this.dutySystem != null) {
			/* 78 */String[] ids = this.dutySystem.getSystemSetting().split(
					"[|]");
			/* 79 */String[] desc = this.dutySystem.getSystemDesc()
					.split("[|]");

			/* 81 */if ((desc != null) && (desc.length == 7)) {
				/* 82 */for (int i = 0; i < desc.length; i++) {
					/* 83 */buff.append("day").append(i).append(":'")
							.append(desc[i]).append("',");
				}
			}
			/* 86 */if ((ids != null) && (ids.length == 7)) {
				/* 87 */for (int i = 0; i < ids.length; i++) {
					/* 88 */buff.append("dayId").append(i).append(":'")
							.append(ids[i]).append("',");
				}
			}

			/* 92 */buff.deleteCharAt(buff.length() - 1);
		} else {
			/* 95 */buff
					.append("day0:'',day1:'',day2:'',day3:'',day4:'',day5:'',day6:''")
					/* 96 */.append(
							",dayId0:'',dayId1:'',dayId2:'',dayId3:'',dayId4:'',dayId5:'',dayId6:''");
		}
		/* 98 */buff.append("}]}");
		/* 99 */setJsonString(buff.toString());
		/* 100 */return "success";
	}

	public String multiDel() {
		/* 109 */String[] ids = getRequest().getParameterValues("ids");
		/* 110 */if (ids != null) {
			/* 111 */for (String id : ids) {
				/* 112 */this.dutySystemService.remove(new Long(id));
			}
		}

		/* 116 */this.jsonString = "{success:true}";

		/* 118 */return "success";
	}

	public String get() {
		/* 126 */DutySystem dutySystem = (DutySystem) this.dutySystemService
				.get(this.systemId);

		/* 128 */Gson gson = new Gson();

		/* 130 */StringBuffer sb = new StringBuffer("{success:true,data:");
		/* 131 */sb.append(gson.toJson(dutySystem));
		/* 132 */sb.append("}");
		/* 133 */setJsonString(sb.toString());

		/* 135 */return "success";
	}

	public String combo() {
		/* 140 */StringBuffer sb = new StringBuffer();

		/* 142 */List<DutySystem> dutySystemList = this.dutySystemService
				.getAll();
		/* 143 */sb.append("[");
		/* 144 */for (DutySystem dutySystem : dutySystemList) {
			/* 145 */sb.append("['").append(dutySystem.getSystemId())
					.append("','").append(dutySystem.getSystemName())
					.append("'],");
		}
		/* 147 */if (dutySystemList.size() > 0) {
			/* 148 */sb.deleteCharAt(sb.length() - 1);
		}
		/* 150 */sb.append("]");
		/* 151 */setJsonString(sb.toString());
		/* 152 */return "success";
	}

	public String save() {
		/* 159 */String data = getRequest().getParameter("data");

		/* 161 */Gson gson = new Gson();
		/* 162 */DutySystemSections[] dss = gson.fromJson(data,
				DutySystemSections[].class);

		/* 164 */this.dutySystem.setSystemSetting(dss[0].dayIdToString());
		/* 165 */this.dutySystem.setSystemDesc(dss[0].dayToString());

		/* 167 */this.dutySystemService.save(this.dutySystem);

		/* 169 */setJsonString("{success:true}");
		/* 170 */return "success";
	}
}