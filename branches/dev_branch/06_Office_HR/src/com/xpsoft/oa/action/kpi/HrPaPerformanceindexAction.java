package com.xpsoft.oa.action.kpi;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import jxl.Sheet;
import jxl.Workbook;

import com.xpsoft.core.command.QueryFilter;
import com.xpsoft.core.model.Ftp;
import com.xpsoft.core.util.AppUtil;
import com.xpsoft.core.util.ContextUtil;
import com.xpsoft.core.web.action.BaseAction;
import com.xpsoft.oa.model.kpi.HrPaDatadictionary;
import com.xpsoft.oa.model.kpi.HrPaPerformanceindex;
import com.xpsoft.oa.model.kpi.HrPaPerformanceindexscore;
import com.xpsoft.oa.model.kpi.HrPaPisrule;
import com.xpsoft.oa.model.system.AppUser;
import com.xpsoft.oa.model.system.Department;
import com.xpsoft.oa.service.kpi.HrPaPerformanceindexService;
import com.xpsoft.oa.service.kpi.HrPaPerformanceindexscoreService;
import com.xpsoft.oa.service.kpi.HrPaPisruleService;
import com.xpsoft.oa.service.system.DepartmentService;

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
	
	public String getForCopy(){
		this.hrPaPerformanceindex = (HrPaPerformanceindex)this.hrPaperformanceindexService.get(this.id);
		this.hrPaPerformanceindex.setPaName(this.hrPaPerformanceindex.getPaName() + " - 副本");
		
		JSONSerializer json = new JSONSerializer();
		StringBuffer buff = new StringBuffer("{success:true,data:");
		buff.append(json.exclude(new String[] {}).serialize(this.hrPaPerformanceindex));
		//判断如果是定量考核指标，则取得其分数关联的考核标准
		String oldAcKeys = "";
		String oldAcNames = "";
		String canCopy = "true";
		if(this.hrPaPerformanceindex.getMode().getId() == 13) {
			String sql = "select a.formula from hr_pa_pisrule a, hr_pa_performanceindexscore b where " +
					"a.pisId = b.id and b.piId = " + this.hrPaPerformanceindex.getId();
			List<Map<String, Object>> mapList = this.hrPaperformanceindexService.findDataList(sql);
			Map<String, String> map = new HashMap<String, String>();
			for(Map<String, Object> item : mapList) {
				String formula = item.get("formula").toString();
				Set set=new HashSet();
				String regex="\\{[^\\{\\}]+\\}";//匹配{}
				Pattern pattern=Pattern.compile(regex);
				Matcher matcher=pattern.matcher(formula);
				while(matcher.find()){
					String	group=matcher.group();
					set.add(group);
				}
				Map<String, String> cnMap = new HashMap<String, String>();
				for(Object acKey : set) {
					String[] keys = acKey.toString().trim().replace("{", "").replace("}", "").split("_");
					String sql2 = "select acName from hr_pa_assessmentcriteria where acKey = '" + keys[0] + "'";
					List<Map<String, Object>> mapList2 = this.hrPaperformanceindexService.findDataList(sql2);
					String acName = "";
					if(mapList2.size() > 0) {
						acName = mapList2.get(0).get("acName").toString();
					} else {
						this.logger.error("关键字为【" + keys[0] + "】的标准不存在或已删除，请核实！");
					}
					map.put(keys[0], acName);
				}
			}
			if(map.size() > 1) {
				canCopy = "false";
			}
			for(Map.Entry<String, String> entry : map.entrySet()) {
				oldAcKeys += entry.getKey() + ",";
				oldAcNames += entry.getValue() + "，";
			}
			if(map.size() > 0) {
				oldAcKeys = oldAcKeys.substring(0, oldAcKeys.length() - 1);
				oldAcNames = oldAcNames.substring(0, oldAcNames.length() - 1);
			}
		}
		buff.append(",canCopy:'" + canCopy + "',oldAcKeys:'" + oldAcKeys + "',oldAcNames:'" + oldAcNames + "'");
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
	
	public String copy() {
		HrPaPerformanceindexscoreService hrPaPerformanceindexscoreService = (HrPaPerformanceindexscoreService)AppUtil.getBean("hrPaPerformanceindexscoreService");
		HrPaPisruleService hrPaPisruleService = (HrPaPisruleService)AppUtil.getBean("hrPaPisruleService");
		Date currentDate = new Date();
		AppUser currentUser = ContextUtil.getCurrentUser();
		//保存考核指标基本信息
		HrPaPerformanceindex pi = new HrPaPerformanceindex();
		pi.setPaName(this.hrPaPerformanceindex.getPaName());
		pi.setBelongDept(this.hrPaPerformanceindex.getBelongDept());
		pi.setType(this.hrPaPerformanceindex.getType());
		pi.setFrequency(this.hrPaPerformanceindex.getFrequency());
		pi.setMode(this.hrPaPerformanceindex.getMode());
		pi.setPaDesc(this.hrPaPerformanceindex.getPaDesc());
		pi.setRemark(this.hrPaPerformanceindex.getRemark());
		pi.setPublishStatus(3);
		pi.setFromPi(new Long(0));
		pi.setCreateDate(currentDate);
		pi.setCreatePerson(currentUser);
		pi.setModifyDate(currentDate);
		pi.setModifyPerson(currentUser);
		//判断是否有父级考核指标
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
		pi = this.hrPaperformanceindexService.save(pi);
		
		//保存考核指标关联的分数
		Map<String, String> map = new HashMap<String, String>();
		map.put("Q_pi.id_L_EQ", String.valueOf(this.hrPaPerformanceindex.getId()));
		QueryFilter filter = new QueryFilter(map);
		List<HrPaPerformanceindexscore> oldScoreList = hrPaPerformanceindexscoreService.getAll(filter);
		List<HrPaPerformanceindexscore> newScoreList = new ArrayList<HrPaPerformanceindexscore>();
		List<HrPaPisrule> newRuleList = new ArrayList<HrPaPisrule>();
		if(this.hrPaPerformanceindex.getMode().getId() == 12) {
			for(HrPaPerformanceindexscore item : oldScoreList) {
				HrPaPerformanceindexscore newItem = new HrPaPerformanceindexscore();
				newItem.setPi(pi);
				newItem.setPisScore(item.getPisScore());
				newItem.setPisDesc(item.getPisDesc());
				newItem.setPisType(item.getPisType());
				newItem.setCoefficient(item.getCoefficient());
				newScoreList.add(newItem);
			}
		} else {
			String oldAcKey = this.getRequest().getParameter("oldAcKey");
			String newAcKey = this.getRequest().getParameter("newAcKey");
			if(oldAcKey == "" || newAcKey == "") {
				this.jsonString = "{failure:true}";
				return "success";
			}
			for(HrPaPerformanceindexscore item : oldScoreList) {
				String sql = "select pisId, pisAC, formula from hr_pa_pisrule where pisId = " + item.getId();
				HrPaPerformanceindexscore newItem = new HrPaPerformanceindexscore();
				newItem.setPi(pi);
				newItem.setPisScore(item.getPisScore());
				newItem.setPisDesc(item.getPisDesc());
				newItem.setPisType(item.getPisType());
				newItem.setCoefficient(item.getCoefficient());
				newScoreList.add(newItem);
				//保存分数关联的公式
				HrPaPisrule rule = new HrPaPisrule();
				List<Map<String, Object>> mapList = this.hrPaperformanceindexService.findDataList(sql);
				if(mapList.size() <= 0) {
					this.jsonString = "{failure:true}";
					return "success";
				}
				String formula = mapList.get(0).get("formula").toString();
				formula = formula.replaceAll(oldAcKey + "_", newAcKey + "_");
				rule.setFormula(formula);
				newRuleList.add(rule);
			}
		}
		hrPaPerformanceindexscoreService.multiSave(newScoreList, newRuleList, this.hrPaPerformanceindex.getMode().getId());
		
		this.jsonString = "{success:true}";
		return "success";
	}
	
	public String uploadPi() {
		Date currentDate = new Date();
		AppUser currentUser	= ContextUtil.getCurrentUser();
		DepartmentService departmentService = (DepartmentService)AppUtil.getBean("departmentService");
		//设置部门
		List<Department> depList = departmentService.getAll();
		Map<String, Department> depMap = new HashMap<String, Department>();
		for(Department dept : depList) {
			depMap.put(dept.getDepName(), dept);
		}
		//设置考核指标类型
		HrPaDatadictionary paType = new HrPaDatadictionary();
		paType.setId(5);
		//设置考核指标频度
		HrPaDatadictionary paFrequency = new HrPaDatadictionary();
		paFrequency.setId(7);
		//设置考核方式
		HrPaDatadictionary paMode = new HrPaDatadictionary();
		paMode.setId(12);
		//获取要导入的excel
		String filePath = this.getRequest().getParameter("filePath");
		boolean isFtp = new Boolean(String.valueOf(AppUtil.getSysConfig().get("isFtp")));
		File file = null;
		if(isFtp){
			String defaultProfix = String.valueOf(AppUtil.getSysConfig().get("file.upload.ftp.sysprofix"));
			Ftp ftp = new Ftp(1, "fileUpload", String.valueOf(AppUtil.getSysConfig().get("file.upload.ftp.host")),
					new Integer(String.valueOf(AppUtil.getSysConfig().get("file.upload.ftp.port"))), "", "");
			ftp.setUsername(String.valueOf(AppUtil.getSysConfig().get("file.upload.ftp.user")));
			ftp.setPassword(String.valueOf(AppUtil.getSysConfig().get("file.upload.ftp.passwd")));
			ftp.setPath("");
			
			String fileP = filePath;
			fileP = fileP.substring(fileP.indexOf(defaultProfix));
			try {
				file = ftp.retrieve(fileP);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				this.logger.error("数据导入失败，原因：" + e);
				this.jsonString = "{success:true,'flag':'0'}";
			}
			
		}else{
			String defaultProfix = String.valueOf(AppUtil.getSysConfig().get("file.upload.default.perfix"));
			int len = defaultProfix.length();
			filePath = filePath.substring(filePath.indexOf(defaultProfix));
			file = new File(this.getRequest().getRealPath("/") + filePath);
		}
		try {
			Workbook book = Workbook.getWorkbook(file);
			Sheet sheet = book.getSheet(0);
			int col = sheet.getColumns();
			int row = sheet.getRows();
			String scoreStandard = "";
			//判断数据格式是否有误
			boolean flag = false;
			for(int i = 1; i < row; i++) {
				flag = true;
				System.out.println("部门名称：" + sheet.getCell(0, i).getContents());
				System.out.println("考核指标名称：" + sheet.getCell(1, i).getContents());
				System.out.println("是否唯一否决指标：" + sheet.getCell(2, i).getContents());
				System.out.println("描述：" + sheet.getCell(3, i).getContents());
				System.out.println("备注信息：" + sheet.getCell(4, i).getContents());
				System.out.println("评分标准：" + sheet.getCell(5, i).getContents());
				scoreStandard = sheet.getCell(5, i).getContents();
				String[] scores = scoreStandard.trim().split("&");
				for(int j = 0; j < scores.length; j++) {
					String[] scoreArray = scores[j].trim().split("_");
					if(scoreArray.length == 3) {
						System.out.println("分数：" + scoreArray[0] + "     绩效系数：" + scoreArray[1] + "     描述：" + scoreArray[2]);
					} else {
						flag = false;
						break;
					}
				}
				if(!flag) {
					System.out.println("评分分标准格式有误，请核实！");
					break;
				}
				System.out.println("***************************************************");
			}
			//数据格式正确，则进行导入操作
			if(flag) {
				List<Map<HrPaPerformanceindex, List<HrPaPerformanceindexscore>>> list = 
						new ArrayList<Map<HrPaPerformanceindex,List<HrPaPerformanceindexscore>>>();
				for(int i = 1; i < row; i++) {
					Map<HrPaPerformanceindex, List<HrPaPerformanceindexscore>> map = 
							new HashMap<HrPaPerformanceindex, List<HrPaPerformanceindexscore>>();
					HrPaPerformanceindex pi = new HrPaPerformanceindex();
					List<HrPaPerformanceindexscore> pisList = new ArrayList<HrPaPerformanceindexscore>();
					pi.setPaName(sheet.getCell(1, i).getContents().trim());
					pi.setBelongDept(depMap.get(sheet.getCell(0, i).getContents().trim()));
					pi.setType(paType);
					pi.setFrequency(paFrequency);
					pi.setMode(paMode);
					String[] onlyArray = sheet.getCell(2, i).getContents().trim().split("_");
					if(onlyArray.length == 4) {
						pi.setPaIsOnlyNegative(1);
						pi.setBaseScore(Double.parseDouble(onlyArray[1]));
						pi.setFinalScore(Double.parseDouble(onlyArray[2]));
						pi.setFinalCoefficient(Double.parseDouble(onlyArray[3]));
					} else {
						pi.setPaIsOnlyNegative(0);
						pi.setBaseScore(new Double(0));
						pi.setFinalScore(new Double(0));
						pi.setFinalCoefficient(new Double(0));
					}
					pi.setPaDesc(sheet.getCell(3, i).getContents().trim());
					pi.setRemark(sheet.getCell(4, i).getContents().trim());
					pi.setPublishStatus(3);
					pi.setCreateDate(currentDate);
					pi.setCreatePerson(currentUser);
					pi.setModifyDate(currentDate);
					pi.setModifyPerson(currentUser);
					pi.setFromPi(new Long(0));
					scoreStandard = sheet.getCell(5, i).getContents();
					String[] scores = scoreStandard.trim().split("&");
					for(int j = 0; j < scores.length; j++) {
						String[] scoreArray = scores[j].trim().split("_");
						if(scoreArray.length == 3) {
							HrPaPerformanceindexscore pis = new HrPaPerformanceindexscore();
							System.out.println("分数：" + scoreArray[0] + "     绩效系数：" + scoreArray[1] + "     描述：" + scoreArray[2]);
							pis.setPisScore(BigDecimal.valueOf(Double.parseDouble(scoreArray[0].trim())));
							pis.setCoefficient(Double.parseDouble(scoreArray[1].trim()));
							pis.setPisType(paMode);
							pis.setPisDesc(scoreArray[2].trim());
							pisList.add(pis);
						} else {
							flag = false;
							break;
						}
					}
					map.put(pi, pisList);
					list.add(map);
				}
				boolean result = this.hrPaperformanceindexService.uploadPi(list);
				if(result) {
					System.out.println("导入成功！");
					this.jsonString = "{success:true,'flag':'1','count':'" + list.size() + "'}";
				}
			}
		} catch(Exception e) {
			this.logger.error("导入出错，原因：" + e);
			this.jsonString = "{success:true,'flag':'0'}";
		}
		return "success";
	}
}
