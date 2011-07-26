package com.xpsoft.oa.action.task;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.xpsoft.core.command.QueryFilter;
import com.xpsoft.core.util.BeanUtil;
import com.xpsoft.core.util.ContextUtil;
import com.xpsoft.core.util.DateUtil;
import com.xpsoft.core.util.StringUtil;
import com.xpsoft.core.web.action.BaseAction;
import com.xpsoft.core.web.paging.PagingBean;
import com.xpsoft.oa.model.system.AppUser;
import com.xpsoft.oa.model.task.CalendarPlan;
import com.xpsoft.oa.model.task.PlanInfo;
import com.xpsoft.oa.service.system.AppUserService;
import com.xpsoft.oa.service.task.CalendarPlanService;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang.time.DateUtils;
import org.apache.commons.logging.Log;

public class CalendarPlanAction extends BaseAction {

	@Resource
	private CalendarPlanService calendarPlanService;
	private CalendarPlan calendarPlan;

	@Resource
	private AppUserService appUserService;
	private List<CalendarPlan> list;
	private Long planId;

	public Long getPlanId() {
		/* 48 */return this.planId;
	}

	public void setPlanId(Long planId) {
		/* 52 */this.planId = planId;
	}

	public CalendarPlan getCalendarPlan() {
		/* 56 */return this.calendarPlan;
	}

	public void setCalendarPlan(CalendarPlan calendarPlan) {
		/* 60 */this.calendarPlan = calendarPlan;
	}

	public List<CalendarPlan> getList() {
		/* 64 */return this.list;
	}

	public void setList(List<CalendarPlan> list) {
		/* 68 */this.list = list;
	}

	public String list() {
		/* 75 */QueryFilter filter = new QueryFilter(getRequest());

		/* 78 */if (getRequest().getParameter("Q_assignerId_L_EQ") == null) {
			/* 79 */filter.addFilter("Q_userId_L_EQ", ContextUtil
					.getCurrentUserId().toString());
		}

		/* 82 */List<CalendarPlan> list = this.calendarPlanService.getAll(filter);

		/* 84 */Type type = new TypeToken<List<CalendarPlan>>() {
		}
		/* 84 */.getType();
		/* 85 */StringBuffer buff = new StringBuffer(
				"{success:true,'totalCounts':")
		/* 86 */.append(filter.getPagingBean().getTotalItems()).append(
				",result:");

		/* 88 */Gson gson = new GsonBuilder()
				.excludeFieldsWithoutExposeAnnotation()
				.setDateFormat("yyyy-MM-dd HH:mm:ss").create();
		/* 89 */buff.append(gson.toJson(list, type));
		/* 90 */buff.append("}");

		/* 92 */this.jsonString = buff.toString();

		/* 94 */return "success";
	}

	public String display() {
		/* 102 */QueryFilter filter = new QueryFilter(getRequest());

		/* 105 */filter.addFilter("Q_userId_L_EQ", ContextUtil
				.getCurrentUserId().toString());
		/* 106 */filter.addSorted("planId", "desc");
		/* 107 */List list = this.calendarPlanService.getAll(filter);
		/* 108 */getRequest().setAttribute("calendarList", list);
		/* 109 */return "display";
	}

	public String today() {
		/* 117 */PagingBean pb = new PagingBean(this.start.intValue(),
				this.limit.intValue());
		/* 118 */List<CalendarPlan> list = this.calendarPlanService.getTodayPlans(
				ContextUtil.getCurrentUserId(), pb);
		/* 119 */List<PlanInfo> planList = new ArrayList();

		/* 121 */for (CalendarPlan plan : list) {
			/* 122 */planList.add(new PlanInfo(plan));
		}

		/* 125 */Gson gson = new GsonBuilder().setDateFormat(
				"yyyy-MM-dd HH:mm:ss").create();

		/* 127 */StringBuffer buff = new StringBuffer(
				"{success:true,'totalCounts':")
		/* 128 */.append(pb.getTotalItems()).append(",result:");
		/* 129 */Type type = new TypeToken<List<PlanInfo>>() {
		}
		/* 129 */.getType();
		/* 130 */buff.append(gson.toJson(planList, type));
		/* 131 */buff.append("}");
		/* 132 */setJsonString(buff.toString());
		/* 133 */return "success";
	}

	public String multiDel() {
		/* 142 */String[] ids = getRequest().getParameterValues("ids");
		/* 143 */if (ids != null) {
			/* 144 */for (String id : ids) {
				/* 145 */this.calendarPlanService.remove(new Long(id));
			}
		}

		/* 149 */this.jsonString = "{success:true}";

		/* 151 */return "success";
	}

	public String get() {
		/* 159 */CalendarPlan calendarPlan = (CalendarPlan) this.calendarPlanService
				.get(this.planId);

		/* 161 */Gson gson = new GsonBuilder().setDateFormat(
				"yyyy-MM-dd HH:mm:ss").create();

		/* 163 */StringBuffer sb = new StringBuffer("{success:true,data:");
		/* 164 */sb.append(gson.toJson(calendarPlan));
		/* 165 */sb.append("}");
		/* 166 */setJsonString(sb.toString());

		/* 168 */return "success";
	}

	public String save() {
		/* 174 */if (this.calendarPlan.getPlanId() == null) {
			/* 175 */this.calendarPlan
					.setStatus(CalendarPlan.STATUS_UNFINISHED);

			/* 177 */AppUser appUser = ContextUtil.getCurrentUser();

			/* 179 */this.calendarPlan.setAssignerId(appUser.getUserId());
			/* 180 */this.calendarPlan.setAssignerName(appUser.getFullname());

			/* 182 */this.calendarPlanService.save(this.calendarPlan);
		} else {
			/* 185 */CalendarPlan cp = (CalendarPlan) this.calendarPlanService
					.get(this.calendarPlan.getPlanId());
			try {
				/* 187 */BeanUtil.copyNotNullProperties(cp, this.calendarPlan);
			} catch (Exception ex) {
				/* 189 */this.logger.error(ex.getMessage());
			}
			/* 191 */this.calendarPlanService.save(cp);
		}

		/* 194 */setJsonString("{success:true}");
		/* 195 */return "success";
	}

	public String my() {
		/* 204 */HttpServletRequest request = getRequest();
		/* 205 */String datafn = request.getParameter("action");

		/* 207 */Date startDate = null;
		/* 208 */Date endDate = null;
		Date reqEndDate;
		/* 210 */if ("month".equals(datafn)) {
			/* 212 */String monthday = request.getParameter("monthday");
			try {
				/* 214 */Date reqDate = DateUtils.parseDate(monthday,
						new String[] { "MM/dd/yyyy" });
				/* 215 */Calendar cal = Calendar.getInstance();
				/* 216 */cal.setTime(reqDate);

				/* 218 */cal.set(5, 1);

				/* 220 */startDate = DateUtil.setStartDay(cal).getTime();

				/* 222 */cal.add(2, 1);
				/* 223 */cal.add(5, -1);

				/* 227 */endDate = DateUtil.setEndDay(cal).getTime();
			} catch (Exception ex) {
				/* 230 */this.logger.error(ex.getMessage());
			}
			/* 232 */} else if ("day".equals(datafn)) {
			/* 235 */String day = request.getParameter("day");
			/* 236 */this.logger.info("day:" + day);
			try {
				/* 238 */Date reqDay = DateUtils.parseDate(day,
						new String[] { "MM/dd/yyyy" });

				/* 240 */Calendar cal = Calendar.getInstance();
				/* 241 */cal.setTime(reqDay);

				/* 244 */startDate = DateUtil.setStartDay(cal).getTime();

				/* 246 */cal.add(2, 1);
				/* 247 */cal.add(5, -1);

				/* 250 */endDate = DateUtil.setEndDay(cal).getTime();
			} catch (Exception ex) {
				/* 253 */this.logger.error(ex.getMessage());
			}
		}
		/* 256 */else if ("week".equals(datafn)) {
			/* 260 */String startweek = request.getParameter("startweek");
			/* 261 */String endweek = request.getParameter("endweek");
			try {
				/* 263 */Date reqStartWeek = DateUtils.parseDate(startweek,
						new String[] { "MM/dd/yyyy" });
				/* 264 */Date reqEndWeek = DateUtils.parseDate(endweek,
						new String[] { "MM/dd/yyyy" });
				/* 265 */Calendar cal = Calendar.getInstance();

				/* 267 */cal.setTime(reqStartWeek);

				/* 269 */startDate = DateUtil.setStartDay(cal).getTime();
				/* 270 */cal.setTime(reqEndWeek);

				/* 272 */endDate = DateUtil.setEndDay(cal).getTime();
			} catch (Exception ex) {
				/* 275 */this.logger.error(ex.getMessage());
			}
		}
		/* 278 */else if ("period".equals(datafn)) {
			/* 279 */String start = request.getParameter("start");
			/* 280 */String end = request.getParameter("end");
			try {
				/* 283 */Date reqStartDate = DateUtils.parseDate(start,
						new String[] { "MM/dd/yyyy" });
				/* 284 */reqEndDate = DateUtils.parseDate(end,
						new String[] { "MM/dd/yyyy" });

				/* 286 */Calendar cal = Calendar.getInstance();

				/* 288 */cal.setTime(reqStartDate);

				/* 290 */startDate = DateUtil.setStartDay(cal).getTime();

				/* 292 */cal.setTime(reqEndDate);

				/* 294 */endDate = DateUtil.setEndDay(cal).getTime();
			} catch (Exception ex) {
				/* 297 */this.logger.info(ex.getMessage());
			}
		} else {
			/* 300 */this.jsonString = "{success:false,errors:'there's enough arguments to generate data'}";
		}

		/* 303 */StringBuffer sb = new StringBuffer();

		/* 309 */List<CalendarPlan> planList = this.calendarPlanService.getByPeriod(
				ContextUtil.getCurrentUserId(), startDate, endDate);

		/* 311 */sb.append("{success:true,totalCount:").append(planList.size())
				.append(",records:[");

		/* 315 */for (CalendarPlan plan : planList) {
			/* 317 */sb.append("{id:'").append(plan.getPlanId()).append("',");

			/* 319 */String subject = plan.getContent();
			/* 320 */if (subject.length() > 12) {
				/* 321 */subject = subject.substring(1, 12) + "...";
			}

			/* 324 */Date endTime = plan.getEndTime();
			/* 325 */if (endTime == null) {
				/* 326 */Calendar curCal = Calendar.getInstance();
				/* 327 */curCal.add(1, 50);
				/* 328 */endTime = curCal.getTime();
			}

			/* 331 */Date startTime = plan.getStartTime();
			/* 332 */if (this.start == null) {
				/* 333 */Calendar curCal = Calendar.getInstance();
				/* 334 */startTime = curCal.getTime();
			}

			/* 337 */sb.append("subject:'")
					.append(StringUtil.convertQuot(subject)).append("',");
			/* 338 */sb.append("description:'")
					.append(StringUtil.convertQuot(plan.getContent()))
					.append("',");
			/* 339 */sb.append("startdate:'")
					.append(DateUtil.formatEnDate(startTime)).append("',");
			/* 340 */sb.append("enddate:'")
					.append(DateUtil.formatEnDate(endTime)).append("',");
			/* 341 */sb.append("color:'").append(plan.getColor()).append("',");
			/* 342 */sb.append("parent:'0',");
			/* 343 */sb.append("priority:'").append(plan.getUrgent())
					.append("'},");
		}

		/* 346 */if (planList.size() > 0) {
			/* 347 */sb.deleteCharAt(sb.length() - 1);
		}
		/* 349 */sb.append("]}");

		/* 351 */this.jsonString = sb.toString();
		/* 352 */return "success";
	}
}

/*
 * Location:
 * C:\Users\Jack\Downloads\oa\joffice131Tomcat6\joffice131Tomcat6\tomcat6
 * -joffice\webapps\joffice1.3.1\WEB-INF\classes\ Qualified Name:
 * com.xpsoft.oa.action.task.CalendarPlanAction JD-Core Version: 0.6.0
 */