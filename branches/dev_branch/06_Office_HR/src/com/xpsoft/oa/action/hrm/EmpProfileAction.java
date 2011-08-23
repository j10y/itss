package com.xpsoft.oa.action.hrm;

import com.xpsoft.core.command.QueryFilter;
import com.xpsoft.core.util.ContextUtil;
import com.xpsoft.core.util.JsonUtil;
import com.xpsoft.core.web.action.BaseAction;
import com.xpsoft.core.web.paging.PagingBean;
import com.xpsoft.oa.model.archive.ArchivesType;
import com.xpsoft.oa.model.hrm.EmpProfile;
import com.xpsoft.oa.model.system.AppUser;
import com.xpsoft.oa.service.hrm.EmpProfileService;
import flexjson.JSONSerializer;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

public class EmpProfileAction extends BaseAction {

	@Resource
	private EmpProfileService empProfileService;
	private EmpProfile empProfile;
	private Long profileId;

	public Long getProfileId() {
		/* 45 */return this.profileId;
	}

	public void setProfileId(Long profileId) {
		/* 49 */this.profileId = profileId;
	}

	public EmpProfile getEmpProfile() {
		/* 53 */return this.empProfile;
	}

	public void setEmpProfile(EmpProfile empProfile) {
		/* 57 */this.empProfile = empProfile;
	}

	public String list() {
		/* 65 */QueryFilter filter = new QueryFilter(getRequest());

		/* 70 */List list = this.empProfileService.getAll(filter);

		/* 72 */StringBuffer buff = new StringBuffer(
				"{success:true,'totalCounts':")
		/* 73 */.append(filter.getPagingBean().getTotalItems()).append(
				",result:");

		/* 75 */JSONSerializer serializer = JsonUtil
				.getJSONSerializer(new String[] { "birthday", "startWorkDate",
						"checktime", "createtime" });
		/* 76 */buff.append(serializer.exclude(
				new String[] { "class", "job.class", "job.department" })
				.serialize(list));
		/* 77 */buff.append("}");

		/* 79 */this.jsonString = buff.toString();

		/* 81 */return "success";
	}

	public String multiDel() {
		/* 89 */String[] ids = getRequest().getParameterValues("ids");
		/* 90 */if (ids != null) {
			/* 91 */for (String id : ids) {
				/* 92 */EmpProfile deletePro = (EmpProfile) this.empProfileService
						.get(new Long(id));
				/* 93 */deletePro.setDelFlag(Short
						.valueOf(EmpProfile.DELETE_FLAG_HAD));
				/* 94 */this.empProfileService.save(deletePro);
			}
		}

		/* 98 */this.jsonString = "{success:true}";

		/* 100 */return "success";
	}

	public String get() {
		/* 108 */EmpProfile empProfile = (EmpProfile) this.empProfileService
				.get(this.profileId);

		/* 110 */JSONSerializer json = JsonUtil.getJSONSerializer(new String[] {
				"birthday", "startWorkDate", "createtime", "checktime", "accessionTime", "departureTime" });

		/* 113 */StringBuffer sb = new StringBuffer("{success:true,data:[");

		/* 115 */sb.append(json.exclude(new String[] { "class", "department" })
				.serialize(empProfile));
		/* 116 */sb.append("]}");
		/* 117 */setJsonString(sb.toString());
		/* 118 */return "success";
	}

	public String save() {
		/* 125 */boolean pass = false;
		/* 126 */StringBuffer buff = new StringBuffer("{");
		/* 127 */if (this.empProfile.getProfileId() == null) {
			/* 128 */if (this.empProfileService.checkProfileNo(this.empProfile
					.getProfileNo())) {
				/* 129 */this.empProfile.setCreator(ContextUtil
						.getCurrentUser().getFullname());
				/* 130 */this.empProfile.setCreatetime(new Date());
				/* 131 */this.empProfile.setDelFlag(Short
						.valueOf(EmpProfile.DELETE_FLAG_NOT));
				/* 132 */pass = true;
			} else {
				/* 134 */buff.append("msg:'档案编号已存在,请重新输入.',");
			}
		}
		/* 137 */else
			pass = true;

		/* 139 */if (pass) {
			if(empProfile.getApprovalStatus()==null){
				this.empProfile.setApprovalStatus(Short
						.valueOf(EmpProfile.CHECK_FLAG_NONE));
			}
			/* 141 */this.empProfileService.save(this.empProfile);
			/* 142 */buff.append("success:true}");
		} else {
			/* 144 */buff.append("failure:true}");
		}
		/* 146 */setJsonString(buff.toString());
		/* 147 */return "success";
	}

	public String number() {
		/* 154 */SimpleDateFormat date = new SimpleDateFormat(
				"yyyyMMddHHmmss-SSSS");
		/* 155 */String profileNo = date.format(new Date());
		/* 156 */setJsonString("{success:true,profileNo:'PN" + profileNo + "'}");
		/* 157 */return "success";
	}

	public String check() {
		/* 165 */EmpProfile checkProfile = (EmpProfile) this.empProfileService
				.get(this.profileId);
		/* 166 */checkProfile.setCheckName(ContextUtil.getCurrentUser()
				.getFullname());
		/* 167 */checkProfile.setChecktime(new Date());
		/* 168 */checkProfile.setApprovalStatus(this.empProfile
				.getApprovalStatus());
		/* 169 */checkProfile.setOpprovalOpinion(this.empProfile
				.getOpprovalOpinion());
		/* 170 */this.empProfileService.save(checkProfile);
		setJsonString("{success:true,msg:'审核通过'}");
		/* 171 */return "success";
	}

	public String recovery() {
		/* 178 */String[] ids = getRequest().getParameterValues("ids");
		/* 179 */if (ids != null) {
			/* 180 */for (String id : ids) {
				/* 181 */EmpProfile deletePro = (EmpProfile) this.empProfileService
						.get(new Long(id));
				/* 182 */deletePro.setDelFlag(Short
						.valueOf(EmpProfile.DELETE_FLAG_NOT));
				/* 183 */this.empProfileService.save(deletePro);
			}
		}
		/* 186 */this.jsonString = "{success:true}";
		/* 187 */return "success";
	}

	public String delphoto() {
		/* 193 */if (this.profileId != null) {
			/* 194 */this.empProfile = ((EmpProfile) this.empProfileService
					.get(this.profileId));
			/* 195 */this.empProfile.setPhoto("");
			/* 196 */this.empProfileService.save(this.empProfile);
			/* 197 */this.jsonString = "{success:true}";
		}
		/* 199 */return "success";
	}
}