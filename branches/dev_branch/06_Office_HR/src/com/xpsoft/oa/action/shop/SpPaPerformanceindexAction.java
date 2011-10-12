package com.xpsoft.oa.action.shop;

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
import com.xpsoft.oa.model.shop.SpPaDatadictionary;
import com.xpsoft.oa.model.shop.SpPaPerformanceindex;
import com.xpsoft.oa.model.shop.SpPaPerformanceindexscore;
import com.xpsoft.oa.model.shop.SpPaPisrule;
import com.xpsoft.oa.model.system.AppUser;
import com.xpsoft.oa.service.shop.SpPaPerformanceindexService;
import com.xpsoft.oa.service.shop.SpPaPerformanceindexscoreService;
import com.xpsoft.oa.service.shop.SpPaPisruleService;

import flexjson.JSONSerializer;

public class SpPaPerformanceindexAction extends BaseAction {
	@Resource
	private SpPaPerformanceindexService spPaperformanceindexService;
	private SpPaPerformanceindex spPaPerformanceindex;
	private long id;
	
	public SpPaPerformanceindexService getSpPaperformanceindexService() {
		return spPaperformanceindexService;
	}

	public void setSpPaperformanceindexService(
			SpPaPerformanceindexService spPaperformanceindexService) {
		this.spPaperformanceindexService = spPaperformanceindexService;
	}

	public SpPaPerformanceindex getSpPaPerformanceindex() {
		return spPaPerformanceindex;
	}

	public void setSpPaPerformanceindex(SpPaPerformanceindex spPaPerformanceindex) {
		this.spPaPerformanceindex = spPaPerformanceindex;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
	
	public String list() {
		QueryFilter filter = new QueryFilter(getRequest());
		List<SpPaPerformanceindex> list = this.spPaperformanceindexService.getAll(filter);
		
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
		String sql = "select id, paName from sp_pa_performanceindex where paFrequency > " + frequencyId;
		List<Map<String, Object>> mapList = this.spPaperformanceindexService.findDataList(sql);
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
		List<Map<String, Object>> mapList = this.spPaperformanceindexService.findDataList(sql);
		String parentId = "0";
		if(mapList.size() > 0) {
			parentId = mapList.get(0).get("parentId").toString();
		}
		String sql3 = "select count(id) as total from sp_pa_performanceindex where " +
				"belongDept in (" + depId + "," + parentId + ") and paFrequency = " + frequencyId + 
				" and publishStatus = " + publishStatus;
		sql3 += (paName == null || "".equals(paName)) ? "" : " and paName like '%" + paName + "%'";
		List<Map<String, Object>> mapList3 = this.spPaperformanceindexService.findDataList(sql3);
		String sql2 = "select a.id, a.paName, b.name as type, c.name as mode, d.name as frequency from " +
				"sp_pa_performanceindex a, sp_pa_datadictionary b, sp_pa_datadictionary c, sp_pa_datadictionary d where " +
				"a.paType = b.id and a.paMode = c.id and a.paFrequency = d.id and " +
				"a.belongDept in (" + depId + "," + parentId + ") and a.paFrequency = " + frequencyId + 
				" and a.publishStatus = " + publishStatus;
		sql2 += (paName == null || "".equals(paName)) ? "" : " and a.paName like '%" + paName + "%'";
		sql2 += " limit " + filter.getPagingBean().getFirstResult() + ", " + filter.getPagingBean().getPageSize();
		List<Map<String, Object>> mapList2 = this.spPaperformanceindexService.findDataList(sql2);
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
	
	public String comboHrPi() {
		String frequencyId = this.getRequest().getParameter("frequencyId");
		String sql = "select id, paName from hr_pa_performanceindex where paFrequency = " + frequencyId;
		List<Map<String, Object>> mapList = this.spPaperformanceindexService.findDataList(sql);
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
	
	public String multiDel(){
		String[] ids = this.getRequest().getParameterValues("ids");
		if(ids != null) {
			for(String id : ids) {
				SpPaPerformanceindex hpp = this.spPaperformanceindexService.get(new Long(id));
				hpp.setPublishStatus(4);//置为已删除状态
				this.spPaperformanceindexService.save(hpp);
			}
		}
		
		this.jsonString = "{success:true}";
		
		return "success";
	}
	
	public String get(){
		this.spPaPerformanceindex = (SpPaPerformanceindex)this.spPaperformanceindexService.get(this.id);
		
		JSONSerializer json = new JSONSerializer();
		StringBuffer buff = new StringBuffer("{success:true,data:");
		buff.append(json.exclude(new String[] {}).serialize(this.spPaPerformanceindex));
		buff.append("}");
		this.jsonString = buff.toString();
		
		return "success";
	}
	
	@SuppressWarnings("unchecked")
	public String saveAsDraft() {
		Date currentDate = new Date();
		AppUser currentUser = ContextUtil.getCurrentUser();
		String[] indexScores = this.getRequest().getParameter("indexScores").trim().split(" ");
		SpPaPerformanceindexscoreService spPaPerformanceindexscoreService = 
				(SpPaPerformanceindexscoreService)AppUtil.getBean("spPaPerformanceindexscoreService");
		SpPaPisruleService spPaPisruleService = (SpPaPisruleService)AppUtil.getBean("spPaPisruleService");
		//保存考核指标基本信息
		//新建定性考核
		SpPaDatadictionary mode = new SpPaDatadictionary();
		mode.setId(SpPaDatadictionary.PA_QUALITATIVE_ASSESSMENT);
		SpPaPerformanceindex pi = new SpPaPerformanceindex();
		pi.setPaName(this.spPaPerformanceindex.getPaName());
		pi.setBelongDept(this.spPaPerformanceindex.getBelongDept());
		pi.setType(this.spPaPerformanceindex.getType());
		pi.setFrequency(this.spPaPerformanceindex.getFrequency());
		pi.setMode(mode);
		pi.setPaDesc(this.spPaPerformanceindex.getPaDesc());
		pi.setRemark(this.spPaPerformanceindex.getRemark());
		pi.setModifyDate(currentDate);
		pi.setModifyPerson(currentUser);
		pi.setPublishStatus(0);//置为草稿状态
		if(this.spPaPerformanceindex.getParentPa() != null) {
			if(this.spPaPerformanceindex.getParentPa().getId() != null) {
				pi.setParentPa(this.spPaPerformanceindex.getParentPa());
			}
		}
		//判断是否唯一否决项
		if(this.spPaPerformanceindex.getPaIsOnlyNegative() == null) {
			pi.setPaIsOnlyNegative(0);
			pi.setBaseScore(new Double(0));
			pi.setFinalScore(new Double(0));
			pi.setFinalCoefficient(new Double(0));
		} else {
			pi.setPaIsOnlyNegative(1);
			pi.setBaseScore(this.spPaPerformanceindex.getBaseScore());
			pi.setFinalScore(this.spPaPerformanceindex.getFinalScore());
			pi.setFinalCoefficient(this.spPaPerformanceindex.getFinalCoefficient());
		}
		//判断是新增还是修改
		if(this.spPaPerformanceindex.getId() == 0) {//新增
			pi.setCreateDate(currentDate);
			pi.setCreatePerson(currentUser);
			pi.setFromPi(new Long(0));
		} else {//修改
			//判断是已发布状态还是草稿状态
			if(this.spPaPerformanceindex.getPublishStatus() == 3) {
				pi.setFromPi(this.spPaPerformanceindex.getId());
				pi.setCreateDate(currentDate);
				pi.setCreatePerson(currentUser);
			} else {
				pi.setId(this.spPaPerformanceindex.getId());
				pi.setFromPi(this.spPaPerformanceindex.getFromPi());
				pi.setCreateDate(new Date(Long.parseLong(getRequest().getParameter("hrPaPerformanceindex.createDate"))));
				pi.setCreatePerson(this.spPaPerformanceindex.getCreatePerson());
			}
		}
		SpPaPerformanceindex piNew = this.spPaperformanceindexService.save(pi);
		//保存考核指标关联的分数
		//判断是新增还是修改
		if(this.spPaPerformanceindex.getId() == 0) {//新增
			List<SpPaPerformanceindexscore> indexScoreList = new ArrayList<SpPaPerformanceindexscore>();
			List<SpPaPisrule> ruleList = new ArrayList<SpPaPisrule>();
			if(!"".equals(this.getRequest().getParameter("indexScores").trim())) {
				for(int i = 0; i < indexScores.length; i++) {
					String[] itemArray = indexScores[i].trim().split(",");
					SpPaPerformanceindexscore indexScore = new SpPaPerformanceindexscore();
					if(!"0".equals(itemArray[0])) {
						indexScore=spPaPerformanceindexscoreService.get(Long.parseLong(itemArray[0]));
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
						SpPaPisrule rule = new SpPaPisrule();
						if(!"undefined".equals(itemArray[4])) {
							rule.setFormula(itemArray[4]);
						}
						ruleList.add(rule);
					}
				}
				//批量添加分数
				spPaPerformanceindexscoreService.multiSave(indexScoreList, ruleList, piNew.getMode().getId());
			}
		} else {//修改
			//判断是已发布状态还是草稿状态
			if(this.spPaPerformanceindex.getPublishStatus() == 3) {//已发布状态
				List<SpPaPerformanceindexscore> indexScoreList = new ArrayList<SpPaPerformanceindexscore>();
				List<SpPaPisrule> ruleList = new ArrayList<SpPaPisrule>();
				if(!"".equals(this.getRequest().getParameter("indexScores").trim())) {
					for(int i = 0; i < indexScores.length; i++) {
						String[] itemArray = indexScores[i].trim().split(",");
						SpPaPerformanceindexscore indexScore = new SpPaPerformanceindexscore();
						indexScore.setPi(piNew);
						indexScore.setPisType(piNew.getMode());
						indexScore.setPisScore(BigDecimal.valueOf(Float.parseFloat(itemArray[1])));
						indexScore.setPisDesc(itemArray[2]);
						//添加绩效系数
						indexScore.setCoefficient(Double.parseDouble(itemArray[3]));
						indexScoreList.add(indexScore);
						//定量考核添加计算公式
						if(piNew.getMode().getId() == 13) {
							SpPaPisrule rule = new SpPaPisrule();
							if(!"undefined".equals(itemArray[4])) {//用户新增或修改了分数
								rule.setFormula(itemArray[4]);
							} else {
								//通过得分和piId找到原考核指标关联的该分数的计算公式
								String sql = "select a.formula from sp_pa_pisrule a, sp_pa_performanceindexscore b where " +
										"b.pisScore = " + itemArray[1] + " and b.piId = " + this.spPaPerformanceindex.getId() + 
										" and a.pisId = b.id limit 1";
								List<Map<String, Object>> formulaList = this.spPaperformanceindexService.findDataList(sql);
								rule.setFormula(formulaList.get(0).get("formula").toString());
							}
							ruleList.add(rule);
						}
					}
					//批量添加分数
					spPaPerformanceindexscoreService.multiSave(indexScoreList, ruleList, piNew.getMode().getId());
				}
			} else {//草稿、退回状态
				//获取原考核指标关联的分数
				Map<String, String> map = new HashMap<String, String>();
				map.put("Q_pi.id_L_EQ", String.valueOf(piNew.getId()));
				QueryFilter filter = new QueryFilter(map);
				List<SpPaPerformanceindexscore> oldScoreList = spPaPerformanceindexscoreService.getAll(filter);
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
							spPaPisruleService.removeByPisId(oldScoreList.get(i).getId());
						}
						spPaPerformanceindexscoreService.remove(oldScoreList.get(i));
					}
				}
				List<SpPaPerformanceindexscore> indexScoreList = new ArrayList<SpPaPerformanceindexscore>();
				List<SpPaPisrule> ruleList = new ArrayList<SpPaPisrule>();
				if(!"".equals(this.getRequest().getParameter("indexScores").trim())) {
					for(int i = 0; i < indexScores.length; i++) {
						String[] itemArray = indexScores[i].trim().split(",");
						SpPaPerformanceindexscore indexScore = new SpPaPerformanceindexscore();
						if(!"0".equals(itemArray[0])) {
							indexScore=spPaPerformanceindexscoreService.get(Long.parseLong(itemArray[0]));
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
							SpPaPisrule rule = new SpPaPisrule();
							if(spPaPisruleService.getAll(filter2).size() > 0) {
								rule = spPaPisruleService.getAll(filter2).get(0);
							}
							if(!"undefined".equals(itemArray[4])) {
								rule.setFormula(itemArray[4]);
							}
							ruleList.add(rule);
						}
					}
					//批量添加分数
					spPaPerformanceindexscoreService.multiSave(indexScoreList, ruleList, piNew.getMode().getId());
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
		SpPaPerformanceindexscoreService spPaPerformanceindexscoreService = 
				(SpPaPerformanceindexscoreService)AppUtil.getBean("spPaPerformanceindexscoreService");
		//保存考核指标基本信息
		//新建定性考核
		SpPaDatadictionary mode = new SpPaDatadictionary();
		mode.setId(SpPaDatadictionary.PA_QUALITATIVE_ASSESSMENT);
		SpPaPerformanceindex pi = new SpPaPerformanceindex();
		pi.setPaName(this.spPaPerformanceindex.getPaName());
		pi.setBelongDept(this.spPaPerformanceindex.getBelongDept());
		pi.setType(this.spPaPerformanceindex.getType());
		pi.setFrequency(this.spPaPerformanceindex.getFrequency());
		pi.setMode(mode);
		pi.setPaDesc(this.spPaPerformanceindex.getPaDesc());
		pi.setRemark(this.spPaPerformanceindex.getRemark());
		pi.setModifyDate(currentDate);
		pi.setModifyPerson(currentUser);
		if(this.spPaPerformanceindex.getParentPa() != null) {
			if(this.spPaPerformanceindex.getParentPa().getId() != null) {
				pi.setParentPa(this.spPaPerformanceindex.getParentPa());
			}
		}
		//判断是否唯一否决项
		if(this.spPaPerformanceindex.getPaIsOnlyNegative() == null) {
			pi.setPaIsOnlyNegative(0);
			pi.setBaseScore(new Double(0));
			pi.setFinalScore(new Double(0));
			pi.setFinalCoefficient(new Double(0));
		} else {
			pi.setPaIsOnlyNegative(1);
			pi.setBaseScore(this.spPaPerformanceindex.getBaseScore());
			pi.setFinalScore(this.spPaPerformanceindex.getFinalScore());
			pi.setFinalCoefficient(this.spPaPerformanceindex.getFinalCoefficient());
		}
		//判断是新增还是修改
		if(this.spPaPerformanceindex.getId() == 0) {//新增
			pi.setCreateDate(currentDate);
			pi.setCreatePerson(currentUser);
			pi.setPublishStatus(3);//置为已发布状态
			pi.setFromPi(new Long(0));
		} else {//修改
			//判断是修改已发布的还是从草稿直接发布
			if(this.spPaPerformanceindex.getRemark() != null && !"".equals(this.spPaPerformanceindex.getRemark())) {//修改已发布的
				pi.setPublishStatus(1);//置为审核中状态
			} else {
				pi.setPublishStatus(3);
			}
			//判断是已发布状态还是草稿状态
			if(this.spPaPerformanceindex.getPublishStatus() == 3) {
				pi.setFromPi(this.spPaPerformanceindex.getId());
				pi.setCreateDate(currentDate);
				pi.setCreatePerson(currentUser);
			} else {
				pi.setId(this.spPaPerformanceindex.getId());
				pi.setFromPi(this.spPaPerformanceindex.getFromPi());
				pi.setCreateDate(new Date(Long.parseLong(getRequest().getParameter("spPaPerformanceindex.createDate"))));
				pi.setCreatePerson(this.spPaPerformanceindex.getCreatePerson());
			}
		}
		SpPaPerformanceindex piNew = this.spPaperformanceindexService.save(pi);
		//保存考核指标关联的分数
		//判断是新增还是修改
		if(this.spPaPerformanceindex.getId() == 0) {//新增
			List<SpPaPerformanceindexscore> indexScoreList = new ArrayList<SpPaPerformanceindexscore>();
			List<SpPaPisrule> ruleList = new ArrayList<SpPaPisrule>();
			if(!"".equals(this.getRequest().getParameter("indexScores").trim())) {
				for(int i = 0; i < indexScores.length; i++) {
					String[] itemArray = indexScores[i].trim().split(",");
					SpPaPerformanceindexscore indexScore = new SpPaPerformanceindexscore();
					if(!"0".equals(itemArray[0])) {
						indexScore=spPaPerformanceindexscoreService.get(Long.parseLong(itemArray[0]));
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
						SpPaPisrule rule = new SpPaPisrule();
						if(!"undefined".equals(itemArray[4])) {
							rule.setFormula(itemArray[4]);
						}
						ruleList.add(rule);
					}
				}
				//批量添加分数
				spPaPerformanceindexscoreService.multiSave(indexScoreList, ruleList, piNew.getMode().getId());
			}
		} else {//修改
			//判断是已发布状态还是草稿状态
			if(this.spPaPerformanceindex.getPublishStatus() == 3) {
				List<SpPaPerformanceindexscore> indexScoreList = new ArrayList<SpPaPerformanceindexscore>();
				List<SpPaPisrule> ruleList = new ArrayList<SpPaPisrule>();
				if(!"".equals(this.getRequest().getParameter("indexScores").trim())) {
					for(int i = 0; i < indexScores.length; i++) {
						String[] itemArray = indexScores[i].trim().split(",");
						SpPaPerformanceindexscore indexScore = new SpPaPerformanceindexscore();
						indexScore.setPi(piNew);
						indexScore.setPisType(piNew.getMode());
						indexScore.setPisScore(BigDecimal.valueOf(Float.parseFloat(itemArray[1])));
						indexScore.setPisDesc(itemArray[2]);
						//添加绩效系数
						indexScore.setCoefficient(Double.parseDouble(itemArray[3]));
						indexScoreList.add(indexScore);
						//定量考核添加计算公式
						if(piNew.getMode().getId() == 13) {
							SpPaPisrule rule = new SpPaPisrule();
							if(!"undefined".equals(itemArray[4])) {//用户新增或修改了分数
								rule.setFormula(itemArray[4]);
							} else {
								//通过得分和piId找到原考核指标关联的该分数的计算公式
								String sql = "select a.formula from sp_pa_pisrule a, sp_pa_performanceindexscore b where " +
										"b.pisScore = " + itemArray[1] + " and b.piId = " + this.spPaPerformanceindex.getId() + 
										" and a.pisId = b.id limit 1";
								List<Map<String, Object>> formulaList = this.spPaperformanceindexService.findDataList(sql);
								rule.setFormula(formulaList.get(0).get("formula").toString());
							}
							ruleList.add(rule);
						}
					}
					//批量添加分数
					spPaPerformanceindexscoreService.multiSave(indexScoreList, ruleList, piNew.getMode().getId());
				}
			} else {
				//获取原考核指标关联的分数
				Map<String, String> map = new HashMap<String, String>();
				map.put("Q_pi.id_L_EQ", String.valueOf(piNew.getId()));
				QueryFilter filter = new QueryFilter(map);
				List<SpPaPerformanceindexscore> oldScoreList = spPaPerformanceindexscoreService.getAll(filter);
				SpPaPisruleService spPaPisruleService = (SpPaPisruleService)AppUtil.getBean("spPaPisruleService");
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
							spPaPisruleService.removeByPisId(oldScoreList.get(i).getId());
						}
						spPaPerformanceindexscoreService.remove(oldScoreList.get(i));
					}
				}
				List<SpPaPerformanceindexscore> indexScoreList = new ArrayList<SpPaPerformanceindexscore>();
				List<SpPaPisrule> ruleList = new ArrayList<SpPaPisrule>();
				if(!"".equals(this.getRequest().getParameter("indexScores").trim())) {
					for(int i = 0; i < indexScores.length; i++) {
						String[] itemArray = indexScores[i].trim().split(",");
						SpPaPerformanceindexscore indexScore = new SpPaPerformanceindexscore();
						if(!"0".equals(itemArray[0])) {
							indexScore=spPaPerformanceindexscoreService.get(Long.parseLong(itemArray[0]));
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
							SpPaPisrule rule = new SpPaPisrule();
							if(spPaPisruleService.getAll(filter2).size() > 0) {
								rule = spPaPisruleService.getAll(filter2).get(0);
							}
							if(!"undefined".equals(itemArray[4])) {
								rule.setFormula(itemArray[4]);
							}
							ruleList.add(rule);
						}
					}
					//批量添加分数
					spPaPerformanceindexscoreService.multiSave(indexScoreList, ruleList, piNew.getMode().getId());
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
		SpPaPerformanceindexscoreService spPaPerformanceindexscoreService = 
			(SpPaPerformanceindexscoreService)AppUtil.getBean("spPaPerformanceindexscoreService");
		//获取复制的考核指标
		SpPaPerformanceindex piCopy = this.spPaperformanceindexService.get(piId);
		//获取原考核指标
		SpPaPerformanceindex piOld = this.spPaperformanceindexService.get(piCopy.getFromPi());
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
		SpPaPerformanceindex piNew = this.spPaperformanceindexService.save(piOld);
		//保存考核指标关联的分数
		//获取原考核指标关联的分数
		Map<String, String> map = new HashMap<String, String>();
		map.put("Q_pi.id_L_EQ", String.valueOf(piOld.getId()));
		QueryFilter filter = new QueryFilter(map);
		List<SpPaPerformanceindexscore> oldScoreList = spPaPerformanceindexscoreService.getAll(filter);
		SpPaPisruleService spPaPisruleService = (SpPaPisruleService)AppUtil.getBean("spPaPisruleService");
		//删除原考核指标关联的分数
		for(int i = 0; i < oldScoreList.size(); i++) {
			//删除分数关联的公式
			if(piOld.getMode().getId() == 13) {
				spPaPisruleService.removeByPisId(oldScoreList.get(i).getId());
			}
			spPaPerformanceindexscoreService.remove(oldScoreList.get(i));
		}
		//将复制的考核指标关联的分数重新关联到原考核指标上
		map.clear();
		map.put("Q_pi.id_L_EQ", String.valueOf(piCopy.getId()));
		QueryFilter filter2 = new QueryFilter(map);
		List<SpPaPerformanceindexscore> copyScoreList = spPaPerformanceindexscoreService.getAll(filter2);
		for(int j = 0; j < copyScoreList.size(); j++) {
			copyScoreList.get(j).setPi(piNew);
			spPaPerformanceindexscoreService.save(copyScoreList.get(j));
		}
		//删除复制的考核指标
		this.spPaperformanceindexService.remove(piCopy);
	}
	
	public String publish() {
		long piId = Long.parseLong(this.getRequest().getParameter("piId"));
		boolean flag = this.spPaperformanceindexService.saveToPublish(piId);
		this.jsonString = "{success:true,flag:" + String.valueOf(flag) + "}";
		return "success";
	}
}
