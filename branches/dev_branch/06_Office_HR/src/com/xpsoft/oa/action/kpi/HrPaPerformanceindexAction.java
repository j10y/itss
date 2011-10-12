package com.xpsoft.oa.action.kpi;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.xpsoft.core.command.QueryFilter;
import com.xpsoft.core.util.AppUtil;
import com.xpsoft.core.util.ContextUtil;
import com.xpsoft.core.web.action.BaseAction;
import com.xpsoft.oa.model.kpi.HrPaPerformanceindex;
import com.xpsoft.oa.model.kpi.HrPaPerformanceindexscore;
import com.xpsoft.oa.model.kpi.HrPaPisrule;
import com.xpsoft.oa.model.system.AppUser;
import com.xpsoft.oa.service.kpi.HrPaPerformanceindexService;
import com.xpsoft.oa.service.kpi.HrPaPerformanceindexscoreService;
import com.xpsoft.oa.service.kpi.HrPaPisruleService;

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
	
	public String combo() {
		String frequencyId = this.getRequest().getParameter("frequencyId");
		String sql = "select id, paName from hr_pa_performanceindex where paFrequency > " + frequencyId;
		List<Map<String, Object>> mapList = this.hrPaperformanceindexService.findDataList(sql);
		StringBuffer buff = new StringBuffer("{success:true,result:[");
		for(int i = 0; i < mapList.size(); i++) {
			buff.append("{'id':'" + mapList.get(i).get("id").toString())
					.append("','paName':'" + mapList.get(i).get("paName").toString() + "'},");
		}
		
		this.jsonString = buff.toString();
		if(mapList.size() > 0) {
			this.jsonString = this.jsonString.substring(0, this.jsonString.length() - 1);
		}
		this.jsonString += "]}";
		
		return "success";
	}
	
	public String pbcCombo() {
		QueryFilter filter = new QueryFilter(this.getRequest());
		String publishStatus = this.getRequest().getParameter("publishStatus");
		String frequencyId = this.getRequest().getParameter("frequencyId");
		String depId = this.getRequest().getParameter("depId");
		String paName = this.getRequest().getParameter("paName");
		String sql = "select parentId from department where depId = " + depId;
		List<Map<String, Object>> mapList = this.hrPaperformanceindexService.findDataList(sql);
		String parentId = "0";
		if(mapList.size() > 0) {
			parentId = mapList.get(0).get("parentId").toString();
		}
		String sql3 = "select count(id) as total from hr_pa_performanceindex where " +
				"belongDept in (" + depId + "," + parentId + ") and paFrequency = " + frequencyId + 
				" and publishStatus = " + publishStatus;
		sql3 += (paName == null || "".equals(paName)) ? "" : " and paName like '%" + paName + "%'";
		List<Map<String, Object>> mapList3 = this.hrPaperformanceindexService.findDataList(sql3);
		String sql2 = "select a.id, a.paName, b.name as type, c.name as mode, d.name as frequency from " +
				"hr_pa_performanceindex a, hr_pa_datadictionary b, hr_pa_datadictionary c, hr_pa_datadictionary d where " +
				"a.paType = b.id and a.paMode = c.id and a.paFrequency = d.id and " +
				"a.belongDept in (" + depId + "," + parentId + ") and a.paFrequency = " + frequencyId + 
				" and a.publishStatus = " + publishStatus;
		sql2 += (paName == null || "".equals(paName)) ? "" : " and a.paName like '%" + paName + "%'";
		sql2 += " limit " + filter.getPagingBean().getFirstResult() + ", " + filter.getPagingBean().getPageSize();
		List<Map<String, Object>> mapList2 = this.hrPaperformanceindexService.findDataList(sql2);
		StringBuffer buff = new StringBuffer("{success:true,'totalCounts':'" + mapList3.get(0).get("total") + "',result:[");
		for(int i = 0; i < mapList2.size(); i++) {
			buff.append("{'id':'" + mapList2.get(i).get("id"))
					.append("','paName':'" + mapList2.get(i).get("paName"))
					.append("','type':'" + mapList2.get(i).get("type"))
					.append("','mode':'" + mapList2.get(i).get("mode"))
					.append("','frequency':'" + mapList2.get(i).get("frequency"))
					.append("'},");
		}
		if(mapList2.size() > 0) {
			buff.deleteCharAt(buff.length() - 1);
		}
		buff.append("]}");
		this.jsonString = buff.toString();
		
		return "success";
	}
	
	public String multiDel(){
		String[] ids = this.getRequest().getParameterValues("ids");
		if(ids != null) {
			for(String id : ids) {
				HrPaPerformanceindex hpp = this.hrPaperformanceindexService.get(new Long(id));
				hpp.setPublishStatus(4);//置为已删除状态
				this.hrPaperformanceindexService.save(hpp);
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
	
	@SuppressWarnings("unchecked")
	public String saveAsDraft() {
		Date currentDate = new Date();
		AppUser currentUser = ContextUtil.getCurrentUser();
		String[] indexScores = this.getRequest().getParameter("indexScores").trim().split(" ");
		HrPaPerformanceindexscoreService hrPaPerformanceindexscoreService = 
				(HrPaPerformanceindexscoreService)AppUtil.getBean("hrPaPerformanceindexscoreService");
		HrPaPisruleService hrPaPisruleService = (HrPaPisruleService)AppUtil.getBean("hrPaPisruleService");
		//保存考核指标基本信息
		HrPaPerformanceindex pi = new HrPaPerformanceindex();
		pi.setPaName(this.hrPaPerformanceindex.getPaName());
		pi.setBelongDept(this.hrPaPerformanceindex.getBelongDept());
		pi.setType(this.hrPaPerformanceindex.getType());
		pi.setFrequency(this.hrPaPerformanceindex.getFrequency());
		pi.setMode(this.hrPaPerformanceindex.getMode());
		pi.setPaDesc(this.hrPaPerformanceindex.getPaDesc());
		pi.setRemark(this.hrPaPerformanceindex.getRemark());
		pi.setModifyDate(currentDate);
		pi.setModifyPerson(currentUser);
		pi.setPublishStatus(0);//置为草稿状态
		if(this.hrPaPerformanceindex.getParentPa() != null) {
			if(this.hrPaPerformanceindex.getParentPa().getId() != null) {
				pi.setParentPa(this.hrPaPerformanceindex.getParentPa());
			}
		}
		//判断是否唯一否决项
		if(this.hrPaPerformanceindex.getPaIsOnlyNegative() == null) {
			pi.setPaIsOnlyNegative(0);
			pi.setBaseScore(new Double(0));
			pi.setFinalScore(new Double(0));
			pi.setFinalCoefficient(new Double(0));
		} else {
			pi.setPaIsOnlyNegative(1);
			pi.setBaseScore(this.hrPaPerformanceindex.getBaseScore());
			pi.setFinalScore(this.hrPaPerformanceindex.getFinalScore());
			pi.setFinalCoefficient(this.hrPaPerformanceindex.getFinalCoefficient());
		}
		//判断是新增还是修改
		if(this.hrPaPerformanceindex.getId() == 0) {//新增
			pi.setCreateDate(currentDate);
			pi.setCreatePerson(currentUser);
			pi.setFromPi(new Long(0));
		} else {//修改
			//判断是已发布状态还是草稿状态
			if(this.hrPaPerformanceindex.getPublishStatus() == 3) {
				pi.setFromPi(this.hrPaPerformanceindex.getId());
				pi.setCreateDate(currentDate);
				pi.setCreatePerson(currentUser);
			} else {
				pi.setId(this.hrPaPerformanceindex.getId());
				pi.setFromPi(this.hrPaPerformanceindex.getFromPi());
				pi.setCreateDate(new Date(Long.parseLong(getRequest().getParameter("hrPaPerformanceindex.createDate"))));
				pi.setCreatePerson(this.hrPaPerformanceindex.getCreatePerson());
			}
		}
		HrPaPerformanceindex piNew = this.hrPaperformanceindexService.save(pi);
		//保存考核指标关联的分数
		//判断是新增还是修改
		if(this.hrPaPerformanceindex.getId() == 0) {//新增
			List<HrPaPerformanceindexscore> indexScoreList = new ArrayList<HrPaPerformanceindexscore>();
			List<HrPaPisrule> ruleList = new ArrayList<HrPaPisrule>();
			if(!"".equals(this.getRequest().getParameter("indexScores").trim())) {
				for(int i = 0; i < indexScores.length; i++) {
					String[] itemArray = indexScores[i].trim().split(",");
					HrPaPerformanceindexscore indexScore = new HrPaPerformanceindexscore();
					if(!"0".equals(itemArray[0])) {
						indexScore=hrPaPerformanceindexscoreService.get(Long.parseLong(itemArray[0]));
					}
					indexScore.setPi(piNew);
					indexScore.setPisType(piNew.getMode());
					indexScore.setPisScore(BigDecimal.valueOf(Float.parseFloat(itemArray[1])));
					indexScore.setPisDesc(itemArray[2]);
					//添加绩效系数
					indexScore.setCoefficient(Double.parseDouble(itemArray[3]));
					indexScoreList.add(indexScore);
					//定量考核添加计算公式
					if(piNew.getMode().getId() == 13) {
						HrPaPisrule rule = new HrPaPisrule();
						if(!"undefined".equals(itemArray[4])) {
							rule.setFormula(itemArray[4]);
						}
						ruleList.add(rule);
					}
				}
				//批量添加分数
				hrPaPerformanceindexscoreService.multiSave(indexScoreList, ruleList, piNew.getMode().getId());
			}
		} else {//修改
			//判断是已发布状态还是草稿状态
			if(this.hrPaPerformanceindex.getPublishStatus() == 3) {//已发布状态
				List<HrPaPerformanceindexscore> indexScoreList = new ArrayList<HrPaPerformanceindexscore>();
				List<HrPaPisrule> ruleList = new ArrayList<HrPaPisrule>();
				if(!"".equals(this.getRequest().getParameter("indexScores").trim())) {
					for(int i = 0; i < indexScores.length; i++) {
						String[] itemArray = indexScores[i].trim().split(",");
						HrPaPerformanceindexscore indexScore = new HrPaPerformanceindexscore();
						indexScore.setPi(piNew);
						indexScore.setPisType(piNew.getMode());
						indexScore.setPisScore(BigDecimal.valueOf(Float.parseFloat(itemArray[1])));
						indexScore.setPisDesc(itemArray[2]);
						//添加绩效系数
						indexScore.setCoefficient(Double.parseDouble(itemArray[3]));
						indexScoreList.add(indexScore);
						//定量考核添加计算公式
						if(piNew.getMode().getId() == 13) {
							HrPaPisrule rule = new HrPaPisrule();
							if(!"undefined".equals(itemArray[4])) {//用户新增或修改了分数
								rule.setFormula(itemArray[4]);
							} else {
								//通过得分和piId找到原考核指标关联的该分数的计算公式
								String sql = "select a.formula from hr_pa_pisrule a, hr_pa_performanceindexscore b where " +
										"b.pisScore = " + itemArray[1] + " and b.piId = " + this.hrPaPerformanceindex.getId() + 
										" and a.pisId = b.id limit 1";
								List<Map<String, Object>> formulaList = this.hrPaperformanceindexService.findDataList(sql);
								rule.setFormula(formulaList.get(0).get("formula").toString());
							}
							ruleList.add(rule);
						}
					}
					//批量添加分数
					hrPaPerformanceindexscoreService.multiSave(indexScoreList, ruleList, piNew.getMode().getId());
				}
			} else {//草稿、退回状态
				//获取原考核指标关联的分数
				Map<String, String> map = new HashMap<String, String>();
				map.put("Q_pi.id_L_EQ", String.valueOf(piNew.getId()));
				QueryFilter filter = new QueryFilter(map);
				List<HrPaPerformanceindexscore> oldScoreList = hrPaPerformanceindexscoreService.getAll(filter);
				//清除多余的分数
				for(int i = 0; i < oldScoreList.size(); i++) {
					boolean flag = false;
					long oldScoreId = oldScoreList.get(i).getId();
					for(int j = 0; j < indexScores.length; j++) {
						String[] itemArray = indexScores[j].trim().split(",");
						if(String.valueOf(oldScoreId).equals(itemArray[0])) {
							flag = true;
						}
					}
					if(!flag) {
						if(piNew.getMode().getId() == 13) {
							hrPaPisruleService.removeByPisId(oldScoreList.get(i).getId());
						}
						hrPaPerformanceindexscoreService.remove(oldScoreList.get(i));
					}
				}
				List<HrPaPerformanceindexscore> indexScoreList = new ArrayList<HrPaPerformanceindexscore>();
				List<HrPaPisrule> ruleList = new ArrayList<HrPaPisrule>();
				if(!"".equals(this.getRequest().getParameter("indexScores").trim())) {
					for(int i = 0; i < indexScores.length; i++) {
						String[] itemArray = indexScores[i].trim().split(",");
						HrPaPerformanceindexscore indexScore = new HrPaPerformanceindexscore();
						if(!"0".equals(itemArray[0])) {
							indexScore=hrPaPerformanceindexscoreService.get(Long.parseLong(itemArray[0]));
						}
						indexScore.setPi(piNew);
						indexScore.setPisType(piNew.getMode());
						indexScore.setPisScore(BigDecimal.valueOf(Float.parseFloat(itemArray[1])));
						indexScore.setPisDesc(itemArray[2]);
						//添加绩效系数
						indexScore.setCoefficient(Double.parseDouble(itemArray[3]));
						indexScoreList.add(indexScore);
						//定量考核添加计算公式
						if(piNew.getMode().getId() == 13) {
							Map<String, String> map2 = new HashMap<String, String>();
							map2.put("Q_pis.id_L_EQ", String.valueOf(itemArray[0]));
							QueryFilter filter2 = new QueryFilter(map2);
							HrPaPisrule rule = new HrPaPisrule();
							if(hrPaPisruleService.getAll(filter2).size() > 0) {
								rule = hrPaPisruleService.getAll(filter2).get(0);
							}
							if(!"undefined".equals(itemArray[4])) {
								rule.setFormula(itemArray[4]);
							}
							ruleList.add(rule);
						}
					}
					//批量添加分数
					hrPaPerformanceindexscoreService.multiSave(indexScoreList, ruleList, piNew.getMode().getId());
				}
			}
		}
		
		this.jsonString = "{success:true}";
		
		return "success";
	}
	
	@SuppressWarnings("unchecked")
	public String saveToPublish() {
		Date currentDate = new Date();
		AppUser currentUser = ContextUtil.getCurrentUser();
		
		String[] indexScores = this.getRequest().getParameter("indexScores").trim().split(" ");
		HrPaPerformanceindexscoreService hrPaPerformanceindexscoreService = 
				(HrPaPerformanceindexscoreService)AppUtil.getBean("hrPaPerformanceindexscoreService");
		//保存考核指标基本信息
		HrPaPerformanceindex pi = new HrPaPerformanceindex();
		pi.setPaName(this.hrPaPerformanceindex.getPaName());
		pi.setBelongDept(this.hrPaPerformanceindex.getBelongDept());
		pi.setType(this.hrPaPerformanceindex.getType());
		pi.setFrequency(this.hrPaPerformanceindex.getFrequency());
		pi.setMode(this.hrPaPerformanceindex.getMode());
		pi.setPaDesc(this.hrPaPerformanceindex.getPaDesc());
		pi.setRemark(this.hrPaPerformanceindex.getRemark());
		pi.setModifyDate(currentDate);
		pi.setModifyPerson(currentUser);
		if(this.hrPaPerformanceindex.getParentPa() != null) {
			if(this.hrPaPerformanceindex.getParentPa().getId() != null) {
				pi.setParentPa(this.hrPaPerformanceindex.getParentPa());
			}
		}
		//判断是否唯一否决项
		if(this.hrPaPerformanceindex.getPaIsOnlyNegative() == null) {
			pi.setPaIsOnlyNegative(0);
			pi.setBaseScore(new Double(0));
			pi.setFinalScore(new Double(0));
			pi.setFinalCoefficient(new Double(0));
		} else {
			pi.setPaIsOnlyNegative(1);
			pi.setBaseScore(this.hrPaPerformanceindex.getBaseScore());
			pi.setFinalScore(this.hrPaPerformanceindex.getFinalScore());
			pi.setFinalCoefficient(this.hrPaPerformanceindex.getFinalCoefficient());
		}
		//判断是新增还是修改
		if(this.hrPaPerformanceindex.getId() == 0) {//新增
			pi.setCreateDate(currentDate);
			pi.setCreatePerson(currentUser);
			pi.setPublishStatus(3);//置为已发布状态
			pi.setFromPi(new Long(0));
		} else {//修改
			//判断是修改已发布的还是从草稿直接发布
			if(this.hrPaPerformanceindex.getRemark() != null && !"".equals(this.hrPaPerformanceindex.getRemark())) {//修改已发布的
				pi.setPublishStatus(1);//置为审核中状态
			} else {
				pi.setPublishStatus(3);
			}
			//判断是已发布状态还是草稿状态
			if(this.hrPaPerformanceindex.getPublishStatus() == 3) {
				pi.setFromPi(this.hrPaPerformanceindex.getId());
				pi.setCreateDate(currentDate);
				pi.setCreatePerson(currentUser);
			} else {
				pi.setId(this.hrPaPerformanceindex.getId());
				pi.setFromPi(this.hrPaPerformanceindex.getFromPi());
				pi.setCreateDate(new Date(Long.parseLong(getRequest().getParameter("hrPaPerformanceindex.createDate"))));
				pi.setCreatePerson(this.hrPaPerformanceindex.getCreatePerson());
			}
		}
		HrPaPerformanceindex piNew = this.hrPaperformanceindexService.save(pi);
		//保存考核指标关联的分数
		//判断是新增还是修改
		if(this.hrPaPerformanceindex.getId() == 0) {//新增
			List<HrPaPerformanceindexscore> indexScoreList = new ArrayList<HrPaPerformanceindexscore>();
			List<HrPaPisrule> ruleList = new ArrayList<HrPaPisrule>();
			if(!"".equals(this.getRequest().getParameter("indexScores").trim())) {
				for(int i = 0; i < indexScores.length; i++) {
					String[] itemArray = indexScores[i].trim().split(",");
					HrPaPerformanceindexscore indexScore = new HrPaPerformanceindexscore();
					if(!"0".equals(itemArray[0])) {
						indexScore=hrPaPerformanceindexscoreService.get(Long.parseLong(itemArray[0]));
					}
					indexScore.setPi(piNew);
					indexScore.setPisType(piNew.getMode());
					indexScore.setPisScore(BigDecimal.valueOf(Float.parseFloat(itemArray[1])));
					indexScore.setPisDesc(itemArray[2]);
					//添加绩效系数
					indexScore.setCoefficient(Double.parseDouble(itemArray[3]));
					indexScoreList.add(indexScore);
					//定量考核添加计算公式
					if(piNew.getMode().getId() == 13) {
						HrPaPisrule rule = new HrPaPisrule();
						if(!"undefined".equals(itemArray[4])) {
							rule.setFormula(itemArray[4]);
						}
						ruleList.add(rule);
					}
				}
				//批量添加分数
				hrPaPerformanceindexscoreService.multiSave(indexScoreList, ruleList, piNew.getMode().getId());
			}
		} else {//修改
			//判断是已发布状态还是草稿状态
			if(this.hrPaPerformanceindex.getPublishStatus() == 3) {
				List<HrPaPerformanceindexscore> indexScoreList = new ArrayList<HrPaPerformanceindexscore>();
				List<HrPaPisrule> ruleList = new ArrayList<HrPaPisrule>();
				if(!"".equals(this.getRequest().getParameter("indexScores").trim())) {
					for(int i = 0; i < indexScores.length; i++) {
						String[] itemArray = indexScores[i].trim().split(",");
						HrPaPerformanceindexscore indexScore = new HrPaPerformanceindexscore();
						indexScore.setPi(piNew);
						indexScore.setPisType(piNew.getMode());
						indexScore.setPisScore(BigDecimal.valueOf(Float.parseFloat(itemArray[1])));
						indexScore.setPisDesc(itemArray[2]);
						//添加绩效系数
						indexScore.setCoefficient(Double.parseDouble(itemArray[3]));
						indexScoreList.add(indexScore);
						//定量考核添加计算公式
						if(piNew.getMode().getId() == 13) {
							HrPaPisrule rule = new HrPaPisrule();
							if(!"undefined".equals(itemArray[4])) {//用户新增或修改了分数
								rule.setFormula(itemArray[4]);
							} else {
								//通过得分和piId找到原考核指标关联的该分数的计算公式
								String sql = "select a.formula from hr_pa_pisrule a, hr_pa_performanceindexscore b where " +
										"b.pisScore = " + itemArray[1] + " and b.piId = " + this.hrPaPerformanceindex.getId() + 
										" and a.pisId = b.id limit 1";
								List<Map<String, Object>> formulaList = this.hrPaperformanceindexService.findDataList(sql);
								rule.setFormula(formulaList.get(0).get("formula").toString());
							}
							ruleList.add(rule);
						}
					}
					//批量添加分数
					hrPaPerformanceindexscoreService.multiSave(indexScoreList, ruleList, piNew.getMode().getId());
				}
			} else {
				//获取原考核指标关联的分数
				Map<String, String> map = new HashMap<String, String>();
				map.put("Q_pi.id_L_EQ", String.valueOf(piNew.getId()));
				QueryFilter filter = new QueryFilter(map);
				List<HrPaPerformanceindexscore> oldScoreList = hrPaPerformanceindexscoreService.getAll(filter);
				HrPaPisruleService hrPaPisruleService = (HrPaPisruleService)AppUtil.getBean("hrPaPisruleService");
				//清除多余的分数
				for(int i = 0; i < oldScoreList.size(); i++) {
					boolean flag = false;
					long oldScoreId = oldScoreList.get(i).getId();
					for(int j = 0; j < indexScores.length; j++) {
						String[] itemArray = indexScores[j].trim().split(",");
						if(String.valueOf(oldScoreId).equals(itemArray[0])) {
							flag = true;
						}
					}
					if(!flag) {
						if(piNew.getMode().getId() == 13) {
							hrPaPisruleService.removeByPisId(oldScoreList.get(i).getId());
						}
						hrPaPerformanceindexscoreService.remove(oldScoreList.get(i));
					}
				}
				List<HrPaPerformanceindexscore> indexScoreList = new ArrayList<HrPaPerformanceindexscore>();
				List<HrPaPisrule> ruleList = new ArrayList<HrPaPisrule>();
				if(!"".equals(this.getRequest().getParameter("indexScores").trim())) {
					for(int i = 0; i < indexScores.length; i++) {
						String[] itemArray = indexScores[i].trim().split(",");
						HrPaPerformanceindexscore indexScore = new HrPaPerformanceindexscore();
						if(!"0".equals(itemArray[0])) {
							indexScore=hrPaPerformanceindexscoreService.get(Long.parseLong(itemArray[0]));
						}
						indexScore.setPi(piNew);
						indexScore.setPisType(piNew.getMode());
						indexScore.setPisScore(BigDecimal.valueOf(Float.parseFloat(itemArray[1])));
						indexScore.setPisDesc(itemArray[2]);
						//添加绩效系数
						indexScore.setCoefficient(Double.parseDouble(itemArray[3]));
						indexScoreList.add(indexScore);
						//定量考核添加计算公式
						if(piNew.getMode().getId() == 13) {
							Map<String, String> map2 = new HashMap<String, String>();
							map2.put("Q_pis.id_L_EQ", String.valueOf(itemArray[0]));
							QueryFilter filter2 = new QueryFilter(map2);
							HrPaPisrule rule = new HrPaPisrule();
							if(hrPaPisruleService.getAll(filter2).size() > 0) {
								rule = hrPaPisruleService.getAll(filter2).get(0);
							}
							if(!"undefined".equals(itemArray[4])) {
								rule.setFormula(itemArray[4]);
							}
							ruleList.add(rule);
						}
					}
					//批量添加分数
					hrPaPerformanceindexscoreService.multiSave(indexScoreList, ruleList, piNew.getMode().getId());
				}
			}
		}
		//模拟修改已发布的考核指标审核流程，添加流程后应该移到审核流程中。
		if(piNew.getFromPi() != 0) {
			this.syncIndex(piNew.getId());
		}
		
		this.jsonString = "{success:true}";
		
		return "success";
	}
	
	public void syncIndex(long piId) {
		Date currentDate = new Date();
		AppUser currentUser = ContextUtil.getCurrentUser();
		HrPaPerformanceindexscoreService hrPaPerformanceindexscoreService = 
			(HrPaPerformanceindexscoreService)AppUtil.getBean("hrPaPerformanceindexscoreService");
		//获取复制的考核指标
		HrPaPerformanceindex piCopy = this.hrPaperformanceindexService.get(piId);
		//获取原考核指标
		HrPaPerformanceindex piOld = this.hrPaperformanceindexService.get(piCopy.getFromPi());
		//同步原考核指标内容
		piOld.setPaName(piCopy.getPaName());
		piOld.setBelongDept(piCopy.getBelongDept());
		piOld.setType(piCopy.getType());
		piOld.setFrequency(piCopy.getFrequency());
		piOld.setMode(piCopy.getMode());
		piOld.setPaIsOnlyNegative(piCopy.getPaIsOnlyNegative());
		piOld.setPaDesc(piCopy.getPaDesc());
		piOld.setRemark(piCopy.getRemark());
		piOld.setModifyDate(currentDate);
		piOld.setModifyPerson(currentUser);
		piOld.setPublishStatus(3);
		piOld.setBaseScore(piCopy.getBaseScore());
		piOld.setFinalScore(piCopy.getFinalScore());
		piOld.setFinalCoefficient(piCopy.getFinalCoefficient());
		piOld.setParentPa(piCopy.getParentPa());
		if(piCopy.getParentPa() != null) {
			if(piCopy.getParentPa().getId() != null) {
				piOld.setParentPa(piCopy.getParentPa());
			}
		}
		//插入数据库
		HrPaPerformanceindex piNew = this.hrPaperformanceindexService.save(piOld);
		//保存考核指标关联的分数
		//获取原考核指标关联的分数
		Map<String, String> map = new HashMap<String, String>();
		map.put("Q_pi.id_L_EQ", String.valueOf(piOld.getId()));
		QueryFilter filter = new QueryFilter(map);
		List<HrPaPerformanceindexscore> oldScoreList = hrPaPerformanceindexscoreService.getAll(filter);
		HrPaPisruleService hrPaPisruleService = (HrPaPisruleService)AppUtil.getBean("hrPaPisruleService");
		//删除原考核指标关联的分数
		for(int i = 0; i < oldScoreList.size(); i++) {
			//删除分数关联的公式
			if(piOld.getMode().getId() == 13) {
				hrPaPisruleService.removeByPisId(oldScoreList.get(i).getId());
			}
			hrPaPerformanceindexscoreService.remove(oldScoreList.get(i));
		}
		//将复制的考核指标关联的分数重新关联到原考核指标上
		map.clear();
		map.put("Q_pi.id_L_EQ", String.valueOf(piCopy.getId()));
		QueryFilter filter2 = new QueryFilter(map);
		List<HrPaPerformanceindexscore> copyScoreList = hrPaPerformanceindexscoreService.getAll(filter2);
		for(int j = 0; j < copyScoreList.size(); j++) {
			copyScoreList.get(j).setPi(piNew);
			hrPaPerformanceindexscoreService.save(copyScoreList.get(j));
		}
		//删除复制的考核指标
		this.hrPaperformanceindexService.remove(piCopy);
	}
	
	public String publish() {
		long piId = Long.parseLong(this.getRequest().getParameter("piId"));
		boolean flag = this.hrPaperformanceindexService.saveToPublish(piId);
		this.jsonString = "{success:true,flag:" + String.valueOf(flag) + "}";
		return "success";
	}
}