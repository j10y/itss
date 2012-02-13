package com.xpsoft.oa.action.system;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.xpsoft.core.command.QueryFilter;
import com.xpsoft.core.util.ContextUtil;
import com.xpsoft.core.web.action.BaseAction;
import com.xpsoft.core.web.paging.PagingBean;
import com.xpsoft.oa.model.system.AppUser;
import com.xpsoft.oa.model.system.Diary;
import com.xpsoft.oa.service.system.DiaryService;
import com.xpsoft.oa.service.system.UserSubService;
import flexjson.DateTransformer;
import flexjson.JSONSerializer;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang.StringUtils;

public class DiaryAction extends BaseAction {

	@Resource
	private DiaryService diaryService;

	@Resource
	private UserSubService userSubService;
	private Diary diary;
	private Date from;
	private Date to;
	private Long diaryId;

	public Date getFrom() {
		/* 43 */return this.from;
	}

	public void setFrom(Date from) {
		/* 47 */this.from = from;
	}

	public Date getTo() {
		/* 51 */return this.to;
	}

	public void setTo(Date to) {
		/* 55 */this.to = to;
	}

	public Long getDiaryId() {
		/* 61 */return this.diaryId;
	}

	public void setDiaryId(Long diaryId) {
		/* 65 */this.diaryId = diaryId;
	}

	public Diary getDiary() {
		/* 69 */return this.diary;
	}

	public void setDiary(Diary diary) {
		/* 73 */this.diary = diary;
	}

	public String list() {
		/* 81 */AppUser user = ContextUtil.getCurrentUser();
		/* 82 */QueryFilter filter = new QueryFilter(getRequest());
		/* 83 */filter.addFilter("Q_appUser.userId_L_EQ", user.getId()
				.toString());
		/* 84 */List<Diary> list = this.diaryService.getAll(filter);
		/* 85 */Type type = new TypeToken<List<Diary>>() {
		}
		/* 85 */.getType();
		/* 86 */StringBuffer buff = new StringBuffer(
				"{success:true,'totalCounts':")
		/* 87 */.append(filter.getPagingBean().getTotalItems()).append(
				",result:");
		/* 88 */Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd")
				.excludeFieldsWithoutExposeAnnotation().create();
		/* 89 */buff.append(gson.toJson(list, type));
		/* 90 */buff.append("}");
		/* 91 */this.jsonString = buff.toString();
		/* 92 */return "success";
	}

	public String subUser() {
		/* 100 */PagingBean pb = getInitPagingBean();
		/* 101 */String usrIds = getRequest().getParameter("userId");
		/* 102 */StringBuffer sb = new StringBuffer();
		/* 103 */if (StringUtils.isNotEmpty(usrIds)) {
			/* 104 */sb.append(usrIds);
		} else {
			/* 106 */List<Long> list = this.userSubService.subUsers(ContextUtil
					.getCurrentUserId());
			/* 107 */for (Long l : list) {
				/* 108 */sb.append(l.toString()).append(",");
			}
			/* 110 */if (list.size() > 0) {
				/* 111 */sb.deleteCharAt(sb.length() - 1);
			}
		}
		/* 114 */List diaryList = new ArrayList();

		/* 116 */StringBuffer buff = new StringBuffer(
				"{success:true,'totalCounts':");

		/* 118 */if (sb.length() > 0) {
			/* 119 */diaryList = this.diaryService.getSubDiary(sb.toString(),
					pb);
			/* 120 */buff.append(pb.getTotalItems());
		} else {
			/* 122 */buff.append("0");
		}
		/* 124 */buff.append(",result:");
		/* 125 */JSONSerializer serializer = new JSONSerializer();
		/* 126 */serializer.transform(new DateTransformer("yyyy-MM-dd"),
				new String[] {
				/* 127 */"dayTime" });
		/* 128 */buff.append(serializer.exclude(new String[] { "class" })
				.serialize(diaryList));
		/* 129 */buff.append("}");
		/* 130 */this.jsonString = buff.toString();
		/* 131 */return "success";
	}

	public String search() {
		/* 139 */AppUser user = ContextUtil.getCurrentUser();
		/* 140 */QueryFilter filter = new QueryFilter(getRequest());

		/* 142 */filter.addFilter("Q_appUser.userId_L_EQ", user.getId()
				.toString());

		/* 144 */if (getRequest().getParameter("from") != "") {
			/* 145 */filter.addFilter("Q_dayTime_D_GE", getRequest()
					.getParameter("from"));
		}

		/* 148 */if (getRequest().getParameter("to") != "") {
			/* 149 */filter.addFilter("Q_dayTime_D_LE", getRequest()
					.getParameter("to"));
		}

		/* 152 */filter.addFilter("Q_content_S_LK", this.diary.getContent());

		/* 154 */if (this.diary.getDiaryType() != null) {
			/* 155 */filter.addFilter("Q_diaryType_SN_EQ", this.diary
					.getDiaryType().toString());
		}

		/* 158 */List<Diary> list = this.diaryService.getAll(filter);
		/* 159 */Type type = new TypeToken<List<Diary>>() {
		}
		/* 159 */.getType();
		/* 160 */StringBuffer buff = new StringBuffer(
				"{success:true,'totalCounts':")
		/* 161 */.append(filter.getPagingBean().getTotalItems()).append(
				",result:");
		/* 162 */Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd")
				.create();
		/* 163 */buff.append(gson.toJson(list, type));
		/* 164 */buff.append("}");
		/* 165 */this.jsonString = buff.toString();
		/* 166 */return "success";
	}

	public String multiDel() {
		/* 175 */String[] ids = getRequest().getParameterValues("ids");
		/* 176 */if (ids != null) {
			/* 177 */for (String id : ids) {
				/* 178 */this.diaryService.remove(new Long(id));
			}
		}
		/* 181 */this.jsonString = "{success:true}";
		/* 182 */return "success";
	}

	public String get() {
		/* 190 */Diary diary = (Diary) this.diaryService.get(this.diaryId);
		/* 191 */Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd")
				.create();

		/* 193 */StringBuffer sb = new StringBuffer("{success:true,data:");
		/* 194 */sb.append(gson.toJson(diary));
		/* 195 */sb.append("}");
		/* 196 */setJsonString(sb.toString());
		/* 197 */return "success";
	}

	public String save() {
		/* 203 */AppUser user = ContextUtil.getCurrentUser();
		/* 204 */this.diary.setAppUser(user);
		/* 205 */this.diaryService.save(this.diary);
		/* 206 */setJsonString("{success:true}");
		/* 207 */return "success";
	}

	public String check() {
		/* 211 */String strId = getRequest().getParameter("diaryId");
		/* 212 */if (StringUtils.isNotEmpty(strId)) {
			/* 213 */this.diary = ((Diary) this.diaryService
					.get(new Long(strId)));
		}
		/* 215 */return "check";
	}

	public String display() {
		/* 219 */AppUser user = ContextUtil.getCurrentUser();
		/* 220 */QueryFilter filter = new QueryFilter(getRequest());
		/* 221 */filter.addFilter("Q_appUser.userId_L_EQ", user.getId()
				.toString());
		/* 222 */filter.addSorted("diaryId", "desc");
		/* 223 */List list = this.diaryService.getAll(filter);
		/* 224 */getRequest().setAttribute("diaryList", list);
		/* 225 */return "display";
	}
}

/*
 * Location:
 * C:\Users\Jack\Downloads\oa\joffice131Tomcat6\joffice131Tomcat6\tomcat6
 * -joffice\webapps\joffice1.3.1\WEB-INF\classes\ Qualified Name:
 * com.xpsoft.oa.action.system.DiaryAction JD-Core Version: 0.6.0
 */