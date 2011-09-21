package com.xpsoft.oa.action.shop;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.xpsoft.core.command.QueryFilter;
import com.xpsoft.core.util.AppUtil;
import com.xpsoft.core.web.action.BaseAction;
import com.xpsoft.oa.model.shop.SpPaDatadictionary;
import com.xpsoft.oa.model.shop.SpPaPerformanceindexscore;
import com.xpsoft.oa.service.shop.SpPaPerformanceindexscoreService;
import com.xpsoft.oa.service.shop.SpPaPisruleService;

import flexjson.JSONSerializer;

public class SpPaPerformanceindexscoreAction extends BaseAction{
	@Resource
	private SpPaPerformanceindexscoreService spPaPerformanceindexscoreService;
	private SpPaPerformanceindexscore spPaPerformanceindexscore;
	private long id;
	
	public SpPaPerformanceindexscoreService getSpPaPerformanceindexscoreService() {
		return spPaPerformanceindexscoreService;
	}
	public void setSpPaPerformanceindexscoreService(
			SpPaPerformanceindexscoreService spPaPerformanceindexscoreService) {
		this.spPaPerformanceindexscoreService = spPaPerformanceindexscoreService;
	}
	public SpPaPerformanceindexscore getSpPaPerformanceindexscore() {
		return spPaPerformanceindexscore;
	}
	public void setSpPaPerformanceindexscore(
			SpPaPerformanceindexscore spPaPerformanceindexscore) {
		this.spPaPerformanceindexscore = spPaPerformanceindexscore;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	
	public String list(){
	
		QueryFilter filter = new QueryFilter(getRequest());
		List<SpPaPerformanceindexscore> list = this.spPaPerformanceindexscoreService.getAll(filter);
		
		StringBuffer buff = new StringBuffer("{success:true,result:");
		JSONSerializer json = new JSONSerializer();
		buff.append(json.exclude(new String[] {}).serialize(list));
		buff.append("}");
		this.jsonString = buff.toString();
		
		return "success";
	}
	
	public String multiDel() {
		String[] ids = this.getRequest().getParameterValues("ids");
		SpPaPisruleService spPaPisruleService = (SpPaPisruleService)AppUtil.getBean("spPaPisruleService");
		if(ids != null) {
			for(String id : ids) {
				spPaPisruleService.removeByPisId(Long.valueOf(id));
				this.spPaPerformanceindexscoreService.remove(new Long(id));
			}
		}
		
		this.jsonString = "{success:true}";
		
		return "success";
	}
	public String get(){
		this.spPaPerformanceindexscore = (SpPaPerformanceindexscore)this.spPaPerformanceindexscoreService.get(this.id);
		
		JSONSerializer json = new JSONSerializer();
		StringBuffer buff = new StringBuffer("{success:true,data:");
		buff.append(json.exclude(new String[] {}).serialize(this.spPaPerformanceindexscore));
		
		buff.append("}");
		this.jsonString = buff.toString();
		
		return "success";
	}
	
	public String save(){
		
		return "success";
	}
}
