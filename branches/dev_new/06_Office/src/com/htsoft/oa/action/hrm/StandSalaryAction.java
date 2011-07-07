package com.htsoft.oa.action.hrm;

import com.google.gson.Gson;

import com.google.gson.GsonBuilder;

import com.google.gson.reflect.TypeToken;

import com.htsoft.core.command.QueryFilter;

import com.htsoft.core.util.ContextUtil;

import com.htsoft.core.web.action.BaseAction;

import com.htsoft.core.web.paging.PagingBean;

import com.htsoft.oa.model.hrm.StandSalary;

import com.htsoft.oa.model.hrm.StandSalaryItem;

import com.htsoft.oa.model.system.AppUser;

import com.htsoft.oa.service.hrm.StandSalaryItemService;

import com.htsoft.oa.service.hrm.StandSalaryService;

import java.lang.reflect.Type;

import java.text.SimpleDateFormat;

import java.util.Date;

import java.util.List;

import javax.annotation.Resource;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;

public class StandSalaryAction extends BaseAction {
	/* 35 */private static short STATUS_DRAFT = 0;
	/* 36 */private static short STATUS_PASS = 1;
	/* 37 */private static short STATUS_NOT_PASS = 2;

	@Resource
	private StandSalaryService standSalaryService;

	@Resource
	private StandSalaryItemService standSalaryItemService;
	private StandSalary standSalary;
	private String data;
	private Long standardId;
	private String deleteItemIds;

	/* 52 */public String getDeleteItemIds() {
		return this.deleteItemIds;
	}

	public void setDeleteItemIds(String deleteItemIds) {
		/* 56 */this.deleteItemIds = deleteItemIds;
	}

	public String getData() {
		/* 60 */return this.data;
	}

	public void setData(String data) {
		/* 64 */this.data = data;
	}

	public Long getStandardId() {
		/* 68 */return this.standardId;
	}

	public void setStandardId(Long standardId) {
		/* 72 */this.standardId = standardId;
	}

	public StandSalary getStandSalary() {
		/* 76 */return this.standSalary;
	}

	public void setStandSalary(StandSalary standSalary) {
		/* 80 */this.standSalary = standSalary;
	}

	public String list() {
		/* 88 */QueryFilter filter = new QueryFilter(getRequest());
		/* 89 */List<StandSalary> list = this.standSalaryService.getAll(filter);

		/* 91 */Type type = new TypeToken<List<StandSalary>>() {
		}.getType();
		/* 92 */StringBuffer buff = new StringBuffer(
				"{success:true,'totalCounts':")
		/* 93 */.append(filter.getPagingBean().getTotalItems()).append(
				",result:");

		/* 95 */Gson gson = new GsonBuilder()
				.excludeFieldsWithoutExposeAnnotation().create();
		/* 96 */buff.append(gson.toJson(list, type));
		/* 97 */buff.append("}");

		/* 99 */this.jsonString = buff.toString();

		/* 101 */return "success";
	}

	public String multiDel() {
		/* 109 */String[] ids = getRequest().getParameterValues("ids");
		/* 110 */if (ids != null) {
			/* 111 */for (String id : ids) {
				/* 112 */this.standSalaryService.remove(new Long(id));
			}
		}

		/* 116 */this.jsonString = "{success:true}";

		/* 118 */return "success";
	}

	public String get() {
		/* 126 */StandSalary standSalary = (StandSalary) this.standSalaryService
				.get(this.standardId);

		/* 128 */Gson gson = new GsonBuilder()
				.excludeFieldsWithoutExposeAnnotation().create();

		/* 130 */StringBuffer sb = new StringBuffer("{success:true,data:[");
		/* 131 */sb.append(gson.toJson(standSalary));
		/* 132 */sb.append("]}");
		/* 133 */setJsonString(sb.toString());

		/* 135 */return "success";
	}

	public String getform() {
		/* 143 */StandSalary standSalary = (StandSalary) this.standSalaryService
				.get(this.standardId);

		/* 145 */Gson gson = new GsonBuilder()
				.excludeFieldsWithoutExposeAnnotation().create();

		/* 147 */StringBuffer sb = new StringBuffer("{success:true,data:");
		/* 148 */sb.append(gson.toJson(standSalary));
		/* 149 */sb.append("}");
		/* 150 */setJsonString(sb.toString());

		/* 152 */return "success";
	}

	public String save() {
		/* 161 */boolean pass = false;
		/* 162 */StringBuffer buff = new StringBuffer("{");
		/* 163 */if (this.standSalary.getStandardId() == null) {
			/* 164 */if (this.standSalaryService.checkStandNo(this.standSalary
					.getStandardNo()))
				/* 165 */pass = true;
			else
				/* 167 */buff.append("msg:'标准编号已存在,请重新输入.',");
		} else {
			/* 170 */pass = true;
		}

		/* 173 */if (pass) {
			/* 174 */if (this.standSalary.getStandardId() != null) {
				/* 176 */this.standSalary.setModifyName(ContextUtil
						.getCurrentUser().getFullname());
				/* 177 */this.standSalary.setModifyTime(new Date());
			} else {
				/* 179 */this.standSalary.setSetdownTime(new Date());
				/* 180 */this.standSalary.setFramer(ContextUtil
						.getCurrentUser().getFullname());
			}
			/* 182 */this.standSalary.setStatus(Short.valueOf(STATUS_DRAFT));
			/* 183 */if (StringUtils.isNotEmpty(this.deleteItemIds)) {
				/* 184 */String[] ids = this.deleteItemIds.split(",");
				/* 185 */for (String id : ids) {
					/* 186 */if (StringUtils.isNotEmpty(id)) {
						/* 187 */this.standSalaryItemService
								.remove(new Long(id));
					}
				}
			}
			/* 191 */this.standSalaryService.save(this.standSalary);
			/* 192 */if (StringUtils.isNotEmpty(this.data)) {
				/* 193 */Gson gson = new Gson();
				/* 194 */StandSalaryItem[] standSalaryItems = gson.fromJson(
						this.data, StandSalaryItem[].class);
				/* 195 */for (StandSalaryItem standSalaryItem : standSalaryItems) {
					/* 196 */if (standSalaryItem.getItemId().longValue() == -1L) {
						/* 197 */standSalaryItem.setItemId(null);
					}
					/* 199 */standSalaryItem.setStandardId(this.standSalary
							.getStandardId());
					/* 200 */this.standSalaryItemService.save(standSalaryItem);
				}
			}
			/* 203 */buff.append("success:true}");
		} else {
			/* 205 */buff.append("failure:true}");
		}
		/* 207 */setJsonString(buff.toString());
		/* 208 */return "success";
	}

	public String check() {
		/* 212 */String status = getRequest().getParameter("status");
		/* 213 */StandSalary checkStandard = (StandSalary) this.standSalaryService
				.get(this.standSalary.getStandardId());
		/* 214 */checkStandard.setCheckName(ContextUtil.getCurrentUser()
				.getFullname());
		/* 215 */checkStandard.setCheckTime(new Date());
		/* 216 */checkStandard.setCheckOpinion(this.standSalary
				.getCheckOpinion());
		/* 217 */if ((StringUtils.isNotEmpty(status))
				&& (Short.valueOf(status).shortValue() == STATUS_PASS))
			/* 218 */checkStandard.setStatus(Short.valueOf(STATUS_PASS));
		else {
			/* 220 */checkStandard.setStatus(Short.valueOf(STATUS_NOT_PASS));
		}
		/* 222 */this.standSalaryService.save(checkStandard);

		/* 224 */return "success";
	}

	public String number() {
		/* 232 */SimpleDateFormat date = new SimpleDateFormat(
				"yyyyMMddHHmmss-SSSS");
		/* 233 */String standardNo = date.format(new Date());
		/* 234 */setJsonString("{success:true,standardNo:'SN" + standardNo
				+ "'}");
		/* 235 */return "success";
	}

	public String combo() {
		/* 242 */List<StandSalary> list = this.standSalaryService.findByPassCheck();
		/* 243 */Type type = new TypeToken<List<StandSalary>>() {
		}
		/* 243 */.getType();
		/* 244 */StringBuffer buff = new StringBuffer();
		/* 245 */Gson gson = new GsonBuilder()
				.excludeFieldsWithoutExposeAnnotation().create();
		/* 246 */buff.append(gson.toJson(list, type));
		/* 247 */this.jsonString = buff.toString();
		/* 248 */return "success";
	}

}
