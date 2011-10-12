package com.xpsoft.oa.action.kpi;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import com.lowagie.text.pdf.hyphenation.TernaryTree.Iterator;
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
	
	public String getCnFormula() {
		String enFormula = this.getRequest().getParameter("enFormula");
		Set set=new HashSet();
		String regex="\\{[^\\{\\}]+\\}";//匹配{}
		Pattern pattern=Pattern.compile(regex);
		Matcher matcher=pattern.matcher(enFormula);
		while(matcher.find()){
			String	group=matcher.group();
			set.add(group);
		}
		Map<String, String> cnMap = new HashMap<String, String>();
		for(Object acKey : set) {
			String[] keys = acKey.toString().trim().replace("{", "").replace("}", "").split("_");
			if(keys.length == 2) {
				String sql = "select acName from hr_pa_assessmentcriteria where acKey = '" + keys[0] + "'";
				List<Map<String, Object>> mapList = this.hrPaPerformanceindexscoreService.findDataList(sql);
				String acName = "";
				if(mapList.size() > 0) {
					acName = mapList.get(0).get("acName").toString();
					if("t".equals(keys[1])) {
						acName += "的目标";
					} else if("r".equals(keys[1])) {
						acName += "的达成";
					}
					enFormula = enFormula.replace(acKey.toString(), acName);
				} else {
					this.logger.error("关键字为【" + keys[0] + "】的标准不存在或已删除，请核实！");
				}
			} else {
				this.logger.error("标准【" + acKey.toString() + "】关键字不符合规则，请核实！");
			}
		}
		this.jsonString = "{success:true,data:{'cnFormula':'" + enFormula + "'}}";
		
		return "success";
	}
	
	public String save(){
		
		return "success";
	}
}
