package com.xpsoft.oa.action.kpi;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.xpsoft.core.command.QueryFilter;
import com.xpsoft.core.util.AppUtil;
import com.xpsoft.core.web.action.BaseAction;
import com.xpsoft.oa.model.kpi.HrPaPerformanceindexscore;
import com.xpsoft.oa.service.kpi.HrPaPerformanceindexscoreService;
import com.xpsoft.oa.service.kpi.HrPaPisruleService;

import flexjson.JSONSerializer;

public class HrPaPerformanceindexscoreAction extends BaseAction{
	//定性考核和定量考核关键字，对应数据库里边的ID
	private final static long QUALITATIVE_ASSESSMENT = 12;
	private final static long QUANTITATIVE_ASSESSMENT = 13;
	@Resource
	private HrPaPerformanceindexscoreService hrPaPerformanceindexscoreService;
	private HrPaPerformanceindexscore hrPaPerformanceindexscore;
	private long id;
	
	public HrPaPerformanceindexscoreService getHrPaPerformanceindexscoreService() {
		return hrPaPerformanceindexscoreService;
	}
	public void setHrPaPerformanceindexscoreService(
			HrPaPerformanceindexscoreService hrPaPerformanceindexscoreService) {
		this.hrPaPerformanceindexscoreService = hrPaPerformanceindexscoreService;
	}
	public HrPaPerformanceindexscore getHrPaPerformanceindexscore() {
		return hrPaPerformanceindexscore;
	}
	public void setHrPaPerformanceindexscore(
			HrPaPerformanceindexscore hrPaPerformanceindexscore) {
		this.hrPaPerformanceindexscore = hrPaPerformanceindexscore;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	
	public String list(){
	
		QueryFilter filter = new QueryFilter(getRequest());
		List<HrPaPerformanceindexscore> list = this.hrPaPerformanceindexscoreService.getAll(filter);
		
		StringBuffer buff = new StringBuffer("{success:true,result:");
		JSONSerializer json = new JSONSerializer();
		buff.append(json.exclude(new String[] {}).serialize(list));
		buff.append("}");
		this.jsonString = buff.toString();
		
		return "success";
	}
	
	public String multiDel() {
		String[] ids = this.getRequest().getParameterValues("ids");
		HrPaPisruleService hrPaPisruleService = (HrPaPisruleService)AppUtil.getBean("hrPaPisruleService");
		if(ids != null) {
			for(String id : ids) {
				hrPaPisruleService.removeByPisId(Long.valueOf(id));
				this.hrPaPerformanceindexscoreService.remove(new Long(id));
			}
		}
		
		this.jsonString = "{success:true}";
		
		return "success";
	}
	public String get(){
		this.hrPaPerformanceindexscore = (HrPaPerformanceindexscore)this.hrPaPerformanceindexscoreService.get(this.id);
		
		JSONSerializer json = new JSONSerializer();
		StringBuffer buff = new StringBuffer("{success:true,data:");
		buff.append(json.exclude(new String[] {}).serialize(this.hrPaPerformanceindexscore));
		//为定量考核添加公式
		if(this.hrPaPerformanceindexscore.getPisType().getId() == this.QUANTITATIVE_ASSESSMENT) {
			HrPaPisruleService hrPaPisruleService = (HrPaPisruleService)AppUtil.getBean("hrPaPisruleService");
			Map<String, String> map = new HashMap<String, String>();
			map.put("Q_pis.id_L_EQ", String.valueOf(this.hrPaPerformanceindexscore.getId()));
			QueryFilter filter = new QueryFilter(map);
			long prId = hrPaPisruleService.getAll(filter).get(0).getId();
			String formula = hrPaPisruleService.getAll(filter).get(0).getFormula();
			buff.deleteCharAt(buff.length() - 1);
			buff.append(",'prId':'" + prId + "','formula':'" + formula + "'}");
		}
		buff.append("}");
		this.jsonString = buff.toString();
		
		return "success";
	}
	
	public String save(){
		
		return "success";
	}
}
