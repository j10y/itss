package com.htsoft.oa.action.info;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.htsoft.core.command.QueryFilter;
import com.htsoft.core.util.ContextUtil;
import com.htsoft.core.web.action.BaseAction;
import com.htsoft.core.web.paging.PagingBean;
import com.htsoft.oa.model.info.AppTips;
import com.htsoft.oa.service.info.AppTipsService;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang.StringUtils;

public class AppTipsAction extends BaseAction {

	@Resource
	private AppTipsService appTipsService;
	private AppTips appTips;
	private Long tipsId;

	public Long getTipsId() {
		/* 38 */return this.tipsId;
	}

	public void setTipsId(Long tipsId) {
		/* 42 */this.tipsId = tipsId;
	}

	public AppTips getAppTips() {
		/* 46 */return this.appTips;
	}

	public void setAppTips(AppTips appTips) {
		/* 50 */this.appTips = appTips;
	}

	public String list() {
		/* 58 */QueryFilter filter = new QueryFilter(getRequest());
		/* 59 */filter.addFilter("Q_appUser.userId_L_EQ", ContextUtil
				.getCurrentUserId().toString());
		/* 60 */List<AppTips> list = this.appTipsService.getAll(filter);

		/* 62 */Type type = new TypeToken<List<AppTips>>() {
		}
		/* 62 */.getType();
		/* 63 */StringBuffer buff = new StringBuffer(
				"{success:true,'totalCounts':")
		/* 64 */.append(filter.getPagingBean().getTotalItems()).append(
				",result:");

		/* 66 */Gson gson = new GsonBuilder()
				.excludeFieldsWithoutExposeAnnotation()
				.setDateFormat("yyyy-MM-dd HH:mm:ss").create();
		/* 67 */buff.append(gson.toJson(list, type));
		/* 68 */buff.append("}");

		/* 70 */this.jsonString = buff.toString();

		/* 72 */return "success";
	}

	public String multiDel() {
		AppTips tips;
		if (getRequest().getParameter("ids").equals("all")) {
			QueryFilter filter = new QueryFilter(getRequest());
			filter.addFilter("Q_appUser.userId_L_EQ", ContextUtil
					.getCurrentUserId().toString());
			List list = this.appTipsService.getAll(filter);
			for (Iterator localIterator = list.iterator(); localIterator
					.hasNext();) {
				tips = (AppTips) localIterator.next();
				this.appTipsService.remove(tips);
			}
		} else {
			String[] ids = getRequest().getParameterValues("ids");
			if (ids != null) {
				for (int i = 0; i < ids.length; i++) {
					String id = ids[i];
					this.appTipsService.remove(new Long(id));
				}
			}
		}
		this.jsonString = "{success:true}";

		return "success";
	}

	public String get() {
		/* 104 */AppTips appTips = (AppTips) this.appTipsService
				.get(this.tipsId);

		/* 106 */Gson gson = new Gson();

		/* 108 */StringBuffer sb = new StringBuffer("{success:true,data:");
		/* 109 */sb.append(gson.toJson(appTips));
		/* 110 */sb.append("}");
		/* 111 */setJsonString(sb.toString());

		/* 113 */return "success";
	}

	public String save() {
		/* 119 */String data = getRequest().getParameter("data");
		/* 120 */if (StringUtils.isNotEmpty(data)) {
			/* 121 */Gson gson = new Gson();
			/* 122 */AppTips[] tips = gson.fromJson(data, AppTips[].class);
			/* 123 */for (AppTips tip : tips) {
				/* 124 */if (tip.getTipsId().longValue() == -1L) {
					/* 125 */tip.setTipsId(null);
					/* 126 */SimpleDateFormat date = new SimpleDateFormat(
							"yyMMddHHmmssSSS");
					/* 127 */String customerNo = date.format(new Date());
					/* 128 */tip.setTipsName("tips" + customerNo);
					/* 129 */tip.setCreateTime(new Date());
				}
				/* 131 */tip.setAppUser(ContextUtil.getCurrentUser());
				/* 132 */this.appTipsService.save(tip);
			}
		}

		/* 136 */setJsonString("{success:true}");
		/* 137 */return "success";
	}
}

/*
 * Location:
 * C:\Users\Jack\Downloads\oa\joffice131Tomcat6\joffice131Tomcat6\tomcat6
 * -joffice\webapps\joffice1.3.1\WEB-INF\classes\ Qualified Name:
 * com.htsoft.oa.action.info.AppTipsAction JD-Core Version: 0.6.0
 */