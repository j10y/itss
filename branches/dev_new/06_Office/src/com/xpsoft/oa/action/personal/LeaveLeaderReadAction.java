package com.xpsoft.oa.action.personal;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;

import com.google.gson.Gson;
import com.xpsoft.core.command.QueryFilter;
import com.xpsoft.core.util.ContextUtil;
import com.xpsoft.core.util.JsonUtil;
import com.xpsoft.core.web.action.BaseAction;
import com.xpsoft.oa.model.archive.Archives;
import com.xpsoft.oa.model.personal.ErrandsRegister;
import com.xpsoft.oa.model.personal.LeaveLeaderRead;
import com.xpsoft.oa.model.system.AppUser;
import com.xpsoft.oa.service.personal.ErrandsRegisterService;
import com.xpsoft.oa.service.personal.LeaveLeaderReadService;
import com.xpsoft.oa.service.system.AppUserService;

import flexjson.JSONSerializer;

public class LeaveLeaderReadAction extends BaseAction {

	@Resource
	private LeaveLeaderReadService leaveLeaderReadService;
	private LeaveLeaderRead leaderRead;

	@Resource
	private ErrandsRegisterService errandsRegisterService;

	@Resource
	private AppUserService appUserService;
	private Long readId;
	private String leaderOpinion;
	private Short isPass;
	private ErrandsRegister errandsRegister;

	public Short getIsPass() {
		/* 63 */return this.isPass;
	}

	public void setIsPass(Short isPass) {
		/* 67 */this.isPass = isPass;
	}

	public String getLeaderOpinion() {
		/* 71 */return this.leaderOpinion;
	}

	public void setLeaderOpinion(String leaderOpinion) {
		/* 75 */this.leaderOpinion = leaderOpinion;
	}

	public Long getReadId() {
		/* 79 */return this.readId;
	}

	public void setReadId(Long readId) {
		/* 83 */this.readId = readId;
	}

	public LeaveLeaderRead getLeaveLeaderRead() {
		/* 87 */return this.leaderRead;
	}

	public void setLeaveLeaderRead(LeaveLeaderRead leaderRead) {
		/* 91 */this.leaderRead = leaderRead;
	}

	public String list() {
		QueryFilter filter = new QueryFilter(getRequest());
		filter.addFilter("Q_userId_L_EQ", ContextUtil.getCurrentUserId()
				.toString());
		filter.addFilter("Q_archives.archType_SN_EQ",
				Archives.ARCHIVE_TYPE_RECEIVE.toString());
		List list = this.leaveLeaderReadService.getAll(filter);
		StringBuffer buff = new StringBuffer("{success:true,'totalCounts':")
				.append(filter.getPagingBean().getTotalItems()).append(
						",result:");
		JSONSerializer serializer = JsonUtil.getJSONSerializer(new String[] {
				"createtime", "archives.issueDate", "archives.createtime" });
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
				this.leaveLeaderReadService.remove(new Long(id));
			}
		}

		this.jsonString = "{success:true}";

		return "success";
	}

	public String get() {
		LeaveLeaderRead leaderRead = (LeaveLeaderRead) this.leaveLeaderReadService
				.get(this.readId);

		Gson gson = new Gson();

		StringBuffer sb = new StringBuffer("{success:true,data:");
		sb.append(gson.toJson(leaderRead));
		sb.append("}");
		setJsonString(sb.toString());

		return "success";
	}

	public String save() {
		/* 151 */String dateId = getRequest().getParameter("errandsRegister.dateId");
				 String status = getRequest().getParameter("errandsRegister.status");
		/* 152 */if (StringUtils.isNotEmpty(dateId)) {
			/* 153 */LeaveLeaderRead leader = new LeaveLeaderRead();
			/* 154 */ErrandsRegister errandsRegister = (ErrandsRegister) this.errandsRegisterService
					.get(new Long(dateId));
			/* 155 */AppUser user = ContextUtil.getCurrentUser();
			/* 156 */leader.setErrandsRegister(errandsRegister);
			/* 157 */leader.setLeaderOpinion(this.leaderOpinion);
			/* 158 */leader.setIsPass(this.isPass);
			/* 159 */leader.setUserId(user.getUserId());
			/* 160 */leader.setLeaderName(user.getFullname());
			/* 161 */leader.setCreatetime(new Date());
			/* 162 */this.leaveLeaderReadService.save(leader);
			/* 163 */errandsRegister.setStatus(Short.valueOf(status));
			/* 164 */this.errandsRegisterService.save(errandsRegister);
		}
		/* 166 */setJsonString("{success:true}");
		/* 167 */return "success";
	}

	 public String saveDep() {
	 /* 174 */this.errandsRegister = ((ErrandsRegister) this.errandsRegisterService
	 .get(this.errandsRegister.getDateId()));
	 /* 175 */String errandsRegisterStatus = getRequest().getParameter(
	 "errandsRegisterStatus");
	 /* 176 */if (StringUtils.isNotEmpty(errandsRegisterStatus)) {
	 /* 177 */this.errandsRegister.setStatus(Short.valueOf(Short
	 .parseShort(errandsRegisterStatus)));
	 }
	 /* 179 */this.errandsRegisterService.save(this.errandsRegister);
	
	 /* 181 */this.leaderRead.setLeaderName(ContextUtil.getCurrentUser()
	 .getFullname());
	 /* 182 */this.leaderRead.setUserId(ContextUtil.getCurrentUserId());
	 /* 183 */this.leaderRead.setErrandsRegister(this.errandsRegister);
	 /* 184 */this.leaderRead.setCreatetime(new Date());
	 /* 185 */this.leaderRead.setIsPass(LeaveLeaderRead.IS_PASS);
	 /* 186 */this.leaveLeaderReadService.save(this.leaderRead);
	
	 /* 188 */setJsonString("{success:true,readId:"
	 + this.leaderRead.getReadId() + "}");
	 /* 189 */return "success";
	 }
}
