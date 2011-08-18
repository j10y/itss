package com.xpsoft.oa.action.hrm;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.beanutils.BeanUtils;

import com.xpsoft.core.command.QueryFilter;
import com.xpsoft.core.util.ContextUtil;
import com.xpsoft.core.util.JsonUtil;
import com.xpsoft.core.web.action.BaseAction;
import com.xpsoft.oa.model.hrm.EmpProfile;
import com.xpsoft.oa.model.hrm.RewardsPunishments;
import com.xpsoft.oa.model.system.AppUser;
import com.xpsoft.oa.model.system.Department;
import com.xpsoft.oa.model.system.Dictionary;
import com.xpsoft.oa.service.hrm.RewardsPunishmentsService;
import com.xpsoft.oa.service.hrm.RealExecutionService;

import flexjson.JSONSerializer;

public class RewardsPunishmentsAction extends BaseAction {

	@Resource
	private RewardsPunishmentsService rewardsPunishmentsService;
	
	@Resource
	private RealExecutionService realExecutionService;
	
	private RewardsPunishments rewardsPunishments;
	private Long rpId;

	public Long getRpId() {
		return rpId;
	}

	public void setRpId(Long rpId) {
		this.rpId = rpId;
	}

	public RewardsPunishments getRewardsPunishments() {
		return this.rewardsPunishments;
	}

	public void setRewardsPunishments(RewardsPunishments rewardsPunishments) {
		this.rewardsPunishments = rewardsPunishments;
	}

	public String list() {
		QueryFilter filter = new QueryFilter(getRequest());
		List list = this.rewardsPunishmentsService.getAll(filter);

		StringBuffer buff = new StringBuffer(
			"{success:true,'totalCounts':")
			.append(filter.getPagingBean().getTotalItems()).append(
			",result:");

//		JSONSerializer serializer = new JSONSerializer();
		JSONSerializer serializer = JsonUtil
		.getJSONSerializer(new String[] {"createDate" });
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
				this.rewardsPunishmentsService.remove(new Long(id));
			}
		}

		this.jsonString = "{success:true}";
		return "success";
	}

	public String get() {
		RewardsPunishments rewardsPunishments = (RewardsPunishments) this.rewardsPunishmentsService.get(this.rpId);

		StringBuffer sb = new StringBuffer("{success:true,totalCounts:1,data:[");

		//JSONSerializer serializer = new JSONSerializer();
		JSONSerializer serializer = JsonUtil
		.getJSONSerializer(new String[] { "createDate"});
		sb.append(serializer.exclude(
						new String[] { "class", "department.class" })
						.serialize(rewardsPunishments));
		sb.append("]}");
		setJsonString(sb.toString().replaceAll("\\s[\\d]{2}:[\\d]{2}:[\\d]{2}", ""));
		return "success";
	}

	public String save() {
		AppUser user = ContextUtil.getCurrentUser();
		if(this.rewardsPunishments.getRpId()!=null){
			
		}else{
			this.rewardsPunishments.setCreateDate(new Date());
			this.rewardsPunishments.setCreatePerson(user);
		}
		AppUser appUser = new AppUser();
		appUser.setUserId(Long.valueOf(getRequest().getParameter("rewardsPunishments.appUser.userId")));
		this.rewardsPunishments.setAppUser(appUser);
		EmpProfile empProfile = new EmpProfile();
		empProfile.setProfileId(Long.valueOf(getRequest().getParameter("rewardsPunishments.empProfile.profileId")));
		this.rewardsPunishments.setEmpProfile(empProfile);
		Dictionary rpType = new Dictionary();
		rpType.setDicId(Long.valueOf(getRequest().getParameter("rewardsPunishments.rpType.dicId")));
		this.rewardsPunishments.setRpType(rpType);
		this.rewardsPunishmentsService.save(this.rewardsPunishments);
		setJsonString("{success:true,rewardsPunishmentsId:'" + rewardsPunishments.getRpId() + "'}");
		return "success";
	}
}
