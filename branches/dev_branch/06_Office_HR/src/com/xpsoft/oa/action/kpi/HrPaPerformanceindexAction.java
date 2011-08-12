package com.xpsoft.oa.action.kpi;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.struts2.ServletActionContext;

import com.xpsoft.core.command.QueryFilter;
import com.xpsoft.core.util.AppUtil;
import com.xpsoft.core.util.ContextUtil;
import com.xpsoft.core.web.action.BaseAction;
import com.xpsoft.oa.model.kpi.HrPaPerformanceindex;
import com.xpsoft.oa.service.kpi.HrPaPerformanceindexService;
import com.xpsoft.oa.service.kpi.HrPaPerformanceindexscoreService;

import flexjson.JSONSerializer;

public class HrPaPerformanceindexAction extends BaseAction {
	@Resource
	private HrPaPerformanceindexService hrPaperformanceindexService;
	private HrPaPerformanceindex hrPaPerformanceindex;
	private long id;
	
	public HrPaPerformanceindexService getHrPaperformanceindexService() {
		return hrPaperformanceindexService;
	}

	public void setHrPaperformanceindexService(
			HrPaPerformanceindexService hrPaperformanceindexService) {
		this.hrPaperformanceindexService = hrPaperformanceindexService;
	}

	public HrPaPerformanceindex getHrPaPerformanceindex() {
		return hrPaPerformanceindex;
	}

	public void setHrPaPerformanceindex(HrPaPerformanceindex hrPaPerformanceindex) {
		this.hrPaPerformanceindex = hrPaPerformanceindex;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
	
	public String list() {
		QueryFilter filter = new QueryFilter(getRequest());
		List<HrPaPerformanceindex> list = this.hrPaperformanceindexService.getAll(filter);
		
		StringBuffer buff = new StringBuffer("{success:true,'totalCounts':")
				.append(filter.getPagingBean().getTotalItems()).append(",result:");
		JSONSerializer json = new JSONSerializer();
		buff.append(json.exclude(new String[] {}).serialize(list));
		buff.append("}");
		this.jsonString = buff.toString();
		
		return "success";
	}
	
	public String multiDel(){
		String[] ids = this.getRequest().getParameterValues("ids");
		HrPaPerformanceindexscoreService hrPaPerformanceindexscoreService = (HrPaPerformanceindexscoreService)AppUtil.getBean("hrPaPerformanceindexscoreService");
		if(ids != null) {
			for(String id : ids) {
				hrPaPerformanceindexscoreService.removeByPiId(Long.parseLong(id));
				this.hrPaperformanceindexService.remove(new Long(id));
			}
		}
		
		this.jsonString = "{success:true}";
		
		return "success";
	}
	
	public String get(){
		this.hrPaPerformanceindex = (HrPaPerformanceindex)this.hrPaperformanceindexService.get(this.id);
		
		JSONSerializer json = new JSONSerializer();
		StringBuffer buff = new StringBuffer("{success:true,data:");
		buff.append(json.exclude(new String[] {}).serialize(this.hrPaPerformanceindex));
		buff.append("}");
		this.jsonString = buff.toString();
		
		return "success";
	}
	
	public String save(){
		Date currentDate = new Date();
		//新建一个HrPaPerformanceindex并为其赋值
		//1. 新增和修改有区别的项
		HrPaPerformanceindex hpp = new HrPaPerformanceindex();
		if(this.hrPaPerformanceindex.getId() == 0) {//新增
			hpp.setCreateDate(currentDate);
			hpp.setCreatePerson(ContextUtil.getCurrentUserId());
		} else {//修改
			hpp.setId(this.hrPaPerformanceindex.getId());
			Date createDate = new Date(Long.parseLong(getRequest().getParameter("hrPaPerformanceindex.createDate")));
			hpp.setCreateDate(createDate);
			hpp.setCreatePerson(this.hrPaPerformanceindex.getCreatePerson());
		}
		//2. 新增和修改没有区别的项
		hpp.setPublishStatus(0);//处于草稿状态
		hpp.setPaName(this.hrPaPerformanceindex.getPaName());
		hpp.setPaType(Long.parseLong(getRequest().getParameter("hrPaPerformanceindex.paType")));
		hpp.setPaFrequency(Long.parseLong(getRequest().getParameter("hrPaPerformanceindex.paFrequency")));
		hpp.setPaMode(Long.parseLong(getRequest().getParameter("hrPaPerformanceindex.paMode")));
		if(this.hrPaPerformanceindex.getPaIsOnlyNegative() == 1) {
			hpp.setPaIsOnlyNegative(1);//是唯一否决项目
		} else {
			hpp.setPaIsOnlyNegative(0);//不是唯一否决项目
		}
		hpp.setPaDesc(this.hrPaPerformanceindex.getPaDesc());
		hpp.setRemark(this.hrPaPerformanceindex.getRemark());
		hpp.setModifyDate(currentDate);
		hpp.setModifyPerson(ContextUtil.getCurrentUserId());
		//将数据插入数据库
		long id = this.hrPaperformanceindexService.save(hpp).getId();
		this.jsonString = new String("{success:true,data:{piId:" + id + "}}");
		
		return "success";
	}
	
	public String publish() {
		long piId = Long.parseLong(this.getRequest().getParameter("piId"));
		boolean flag = this.hrPaperformanceindexService.saveToPublish(piId);
		System.out.println(flag);
		this.jsonString = "{success:true,flag:" + String.valueOf(flag) + "}";
		return "success";
	}
}
