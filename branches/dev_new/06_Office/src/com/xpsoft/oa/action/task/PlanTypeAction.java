package com.xpsoft.oa.action.task;


import com.google.gson.Gson;

import com.google.gson.reflect.TypeToken;

import com.xpsoft.core.command.QueryFilter;

import com.xpsoft.core.web.action.BaseAction;

import com.xpsoft.core.web.paging.PagingBean;

import com.xpsoft.oa.model.task.PlanType;

import com.xpsoft.oa.service.task.PlanTypeService;

import com.xpsoft.oa.service.task.WorkPlanService;

import java.lang.reflect.Type;

import java.util.List;

import javax.annotation.Resource;

import javax.servlet.http.HttpServletRequest;


public class PlanTypeAction extends BaseAction
{
	
	@Resource
	private PlanTypeService planTypeService;
	private PlanType planType;
	
	@Resource
	private WorkPlanService workPlanService;
	private Long typeId;

	
	public Long getTypeId()
	{
		/* 36 */return this.typeId;
		}

	
	public void setTypeId(Long typeId) {
		/* 40 */this.typeId = typeId;
		}

	
	public PlanType getPlanType() {
		/* 44 */return this.planType;
		}

	
	public void setPlanType(PlanType planType) {
		/* 48 */this.planType = planType;
		}

	
	public String combo()
	{
		/* 53 */StringBuffer sb = new StringBuffer();
		
		/* 55 */List<PlanType> planTypeList = this.planTypeService.getAll();
		/* 56 */sb.append("[");
		/* 57 */for (PlanType planType : planTypeList) {
			/* 58 */sb.append("['").append(planType.getTypeId()).append("','")
					.append(planType.getTypeName()).append("'],");
			}
		/* 60 */if (planTypeList.size() > 0) {
			/* 61 */sb.deleteCharAt(sb.length() - 1);
			}
		/* 63 */sb.append("]");
		/* 64 */setJsonString(sb.toString());
		/* 65 */return "success";
		}

	
	public String list()
	{
		/* 73 */QueryFilter filter = new QueryFilter(getRequest());
		/* 74 */List<PlanType> list = this.planTypeService.getAll(filter);
		
		/* 76 */Type type = new TypeToken<List<PlanType>>() {
		}
		/* 76 */.getType();
		/* 77 */StringBuffer buff = new StringBuffer(
				"{success:true,'totalCounts':")
		/* 78 */.append(filter.getPagingBean().getTotalItems()).append(
				",result:");
		
		/* 80 */Gson gson = new Gson();
		/* 81 */buff.append(gson.toJson(list, type));
		/* 82 */buff.append("}");
		
		/* 84 */this.jsonString = buff.toString();
		
		/* 86 */return "success";
		}

	
	public String multiDel()
	{
		/* 94 */String[] ids = getRequest().getParameterValues("ids");
		/* 95 */if (ids != null) {
			/* 96 */for (String id : ids) {
				/* 97 */QueryFilter filter = new QueryFilter(getRequest());
				/* 98 */filter.addFilter("Q_planType.typeId_L_EQ", id);
				/* 99 */List list = this.workPlanService.getAll(filter);
				/* 100 */if (list.size() > 0) {
					/* 101 */this.jsonString = "{success:false,message:'类型下还有计划，请移走该类型的计划任务后，再删除类型！'}";
					/* 102 */return "success";
					}
				/* 104 */this.planTypeService.remove(new Long(id));
				}
			}
		
		/* 108 */this.jsonString = "{success:true}";
		
		/* 110 */return "success";
		}

	
	public String get()
	{
		/* 118 */PlanType planType = (PlanType) this.planTypeService
				.get(this.typeId);
		
		/* 120 */Gson gson = new Gson();
		
		/* 122 */StringBuffer sb = new StringBuffer("{success:true,data:");
		/* 123 */sb.append(gson.toJson(planType));
		/* 124 */sb.append("}");
		/* 125 */setJsonString(sb.toString());
		
		/* 127 */return "success";
		}

	
	public String save()
	{
		/* 133 */this.planTypeService.save(this.planType);
		/* 134 */setJsonString("{success:true}");
		/* 135 */return "success";
		}
	
}
