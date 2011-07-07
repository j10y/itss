package com.htsoft.oa.action.system;

import com.google.gson.Gson;
import com.htsoft.core.command.QueryFilter;
import com.htsoft.core.util.ContextUtil;
import com.htsoft.core.web.action.BaseAction;
import com.htsoft.core.web.paging.PagingBean;
import com.htsoft.oa.model.system.AppUser;
import com.htsoft.oa.model.system.UserSub;
import com.htsoft.oa.service.system.AppUserService;
import com.htsoft.oa.service.system.UserSubService;
import flexjson.DateTransformer;
import flexjson.JSONSerializer;
import java.util.List;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

public class UserSubAction extends BaseAction {

	@Resource
	private UserSubService userSubService;
	private UserSub userSub;

	@Resource
	private AppUserService appUserService;
	private Long subId;

	public Long getSubId() {
		/* 42 */return this.subId;
	}

	public void setSubId(Long subId) {
		/* 46 */this.subId = subId;
	}

	public UserSub getUserSub() {
		/* 50 */return this.userSub;
	}

	public void setUserSub(UserSub userSub) {
		/* 54 */this.userSub = userSub;
	}

	public String list() {
		/* 62 */QueryFilter filter = new QueryFilter(getRequest());
		/* 63 */filter.addFilter("Q_userId_L_EQ", ContextUtil
				.getCurrentUserId().toString());
		/* 64 */filter.addFilter("Q_subAppUser.delFlag_SN_EQ", "0");
		/* 65 */List list = this.userSubService.getAll(filter);
		/* 66 */StringBuffer buff = new StringBuffer(
				"{success:true,'totalCounts':")
		/* 67 */.append(filter.getPagingBean().getTotalItems()).append(
				",result:");
		/* 68 */JSONSerializer serializer = new JSONSerializer();
		/* 69 */serializer.transform(new DateTransformer("yyyy-MM-dd"),
				new String[] { "subAppUser.accessionTime" });
		/* 70 */buff.append(serializer.exclude(
				new String[] { "subAppUser.password", "class" })
				.serialize(list));
		/* 71 */buff.append("}");
		/* 72 */this.jsonString = buff.toString();
		/* 73 */return "success";
	}

	public String combo() {
		/* 77 */QueryFilter filter = new QueryFilter(getRequest());
		/* 78 */filter.addFilter("Q_userId_L_EQ", ContextUtil
				.getCurrentUserId().toString());
		/* 79 */List<UserSub> list = this.userSubService.getAll(filter);
		/* 80 */StringBuffer buff = new StringBuffer("[");
		/* 81 */for (UserSub sub : list) {
			/* 82 */buff.append("['"
					+ sub.getSubAppUser().getUserId().toString() + "','"
					+ sub.getSubAppUser().getFullname() + "'],");
		}
		/* 84 */if (list.size() > 0) {
			/* 85 */buff.deleteCharAt(buff.length() - 1);
		}
		/* 87 */buff.append("]");
		/* 88 */setJsonString(buff.toString());
		/* 89 */return "success";
	}

	public String multiDel() {
		/* 97 */String[] ids = getRequest().getParameterValues("ids");
		/* 98 */if (ids != null) {
			/* 99 */for (String id : ids) {
				/* 100 */this.userSubService.remove(new Long(id));
			}
		}

		/* 104 */this.jsonString = "{success:true}";

		/* 106 */return "success";
	}

	public String get() {
		/* 114 */UserSub userSub = (UserSub) this.userSubService
				.get(this.subId);

		/* 116 */Gson gson = new Gson();

		/* 118 */StringBuffer sb = new StringBuffer("{success:true,data:");
		/* 119 */sb.append(gson.toJson(userSub));
		/* 120 */sb.append("}");
		/* 121 */setJsonString(sb.toString());

		/* 123 */return "success";
	}

	public String save() {
		/* 129 */String subUserIds = getRequest().getParameter("subUserIds");
		/* 130 */String[] strSubUserId = subUserIds.split(",");
		/* 131 */for (int i = 0; i < strSubUserId.length; i++) {
			/* 132 */UserSub usb = new UserSub();
			/* 133 */usb.setUserId(ContextUtil.getCurrentUserId());
			/* 134 */Long subUserId = new Long(strSubUserId[i]);
			/* 135 */AppUser subAppUser = (AppUser) this.appUserService
					.get(subUserId);
			/* 136 */usb.setSubAppUser(subAppUser);
			/* 137 */this.userSubService.save(usb);
		}
		/* 139 */setJsonString("{success:true}");
		/* 140 */return "success";
	}
}

/*
 * Location:
 * C:\Users\Jack\Downloads\oa\joffice131Tomcat6\joffice131Tomcat6\tomcat6
 * -joffice\webapps\joffice1.3.1\WEB-INF\classes\ Qualified Name:
 * com.htsoft.oa.action.system.UserSubAction JD-Core Version: 0.6.0
 */