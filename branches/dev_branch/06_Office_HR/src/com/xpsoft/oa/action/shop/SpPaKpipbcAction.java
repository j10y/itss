package com.xpsoft.oa.action.shop;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.xpsoft.core.command.QueryFilter;
import com.xpsoft.core.util.AppUtil;
import com.xpsoft.core.util.ContextUtil;
import com.xpsoft.core.util.DateUtil;
import com.xpsoft.core.web.action.BaseAction;
import com.xpsoft.oa.model.hrm.EmpProfile;
import com.xpsoft.oa.model.kpi.HrPaDatadictionary;
import com.xpsoft.oa.model.shop.SpPaAuthpbccitem;
import com.xpsoft.oa.model.shop.SpPaKpiPBC2User;
import com.xpsoft.oa.model.shop.SpPaKpiitem;
import com.xpsoft.oa.model.shop.SpPaKpiitem2user;
import com.xpsoft.oa.model.shop.SpPaKpiitemHist;
import com.xpsoft.oa.model.shop.SpPaKpipbc;
import com.xpsoft.oa.model.shop.SpPaKpipbcHist;
import com.xpsoft.oa.model.shop.SpPaPerformanceindex;
import com.xpsoft.oa.model.system.AppUser;
import com.xpsoft.oa.service.hrm.EmpProfileService;
import com.xpsoft.oa.service.shop.SpPaAuthpbccitemService;
import com.xpsoft.oa.service.shop.SpPaKpiPBC2UserService;
import com.xpsoft.oa.service.shop.SpPaKpiitem2userService;
import com.xpsoft.oa.service.shop.SpPaKpiitemHistService;
import com.xpsoft.oa.service.shop.SpPaKpiitemService;
import com.xpsoft.oa.service.shop.SpPaKpipbcHistService;
import com.xpsoft.oa.service.shop.SpPaKpipbcService;
import com.xpsoft.oa.service.system.AppUserService;

import flexjson.JSONSerializer;

public class SpPaKpipbcAction extends BaseAction{
	@Resource
	private SpPaKpipbcService spPaKpipbcService;
	private SpPaKpipbc spPaKpipbc;
	private long id;
	
	public SpPaKpipbcService getSpPaKpipbcService() {
		return spPaKpipbcService;
	}
	public void setSpPaKpipbcService(SpPaKpipbcService spPaKpipbcService) {
		this.spPaKpipbcService = spPaKpipbcService;
	}
	public SpPaKpipbc getSpPaKpipbc() {
		return spPaKpipbc;
	}
	public void setSpPaKpipbc(SpPaKpipbc spPaKpipbc) {
		this.spPaKpipbc = spPaKpipbc;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	
	public String list() {
		QueryFilter filter = new QueryFilter(this.getRequest());
		List<SpPaKpipbc> list = this.spPaKpipbcService.getAll(filter);
		
		StringBuffer buff = new StringBuffer("{success:true,'totalCounts':")
				.append(filter.getPagingBean().getTotalItems()).append(",result:");
		JSONSerializer json = new JSONSerializer();
		buff.append(json.exclude(new String[] {}).serialize(list));
		buff.append("}");
		this.jsonString = buff.toString();
		
		return "success";
	}
	
	public String get() {
		this.spPaKpipbc = (SpPaKpipbc)this.spPaKpipbcService.get(this.id);
		String sql = "select a.id, b.pbcName, c.paName from hr_pa_kpiitem2user a, hr_pa_kpipbc2user b, hr_pa_performanceindex c where " +
				"a.id = " + this.spPaKpipbc.getFromPi() + " and a.pbcId = b.id and a.piId = c.id";
		List<Map<String, Object>> mapList = this.spPaKpipbcService.findDataList(sql);
		String paName = "";
		String pbcId = "";
		String pbcName = "";
		if(mapList.size() > 0) {
			pbcId = mapList.get(0).get("id").toString();
			pbcName = mapList.get(0).get("pbcName").toString();
			paName = mapList.get(0).get("paName").toString();
		}
		
		JSONSerializer json = new JSONSerializer();
		StringBuffer buff = new StringBuffer("{success:true,'paName':'" + paName + "','pbcName':'" + pbcName + "','pbcId':'" + pbcId + "',data:");
		buff.append(json.exclude(new String[] {}).serialize(this.spPaKpipbc));
		buff.append("}");
		this.jsonString = buff.toString();
		
		return "success";
	}
	
	public String comboUserPbc() {
		String depId = this.getRequest().getParameter("depId");
		String fId = this.getRequest().getParameter("frequencyId");
		String pbcName = this.getRequest().getParameter("pbcName");
		String sql = "select a.id, a.pbcName from hr_pa_kpipbc2user a, emp_profile b where " +
				"a.belongUser = b.userId and a.frequency = " + fId + " and b.depId = " + depId;
		sql += (pbcName == null || "".equals(pbcName)) ? "" : " and a.pbcName like '%" + pbcName + "%'";
		List<Map<String, Object>> mapList = this.spPaKpipbcService.findDataList(sql);
		StringBuffer buff = new StringBuffer("{success:true,result:[");
		for(int i = 0; i < mapList.size(); i++) {
			buff.append("{'id':'").append(mapList.get(i).get("id").toString())
					.append("','pbcName':'").append(mapList.get(i).get("pbcName")).append("'},");
		}
		this.jsonString = buff.toString();
		if(mapList.size() > 0) {
			this.jsonString = this.jsonString.substring(0, this.jsonString.length() - 1);
		}
		this.jsonString += "]}";
		
		return "success";
	}
	
	public String comboPbcItem() {
		String pbcId = this.getRequest().getParameter("pbcId");
		String sql = "select a.id, b.paName from hr_pa_kpiitem2user a, hr_pa_performanceindex b where " +
				"b.paMode = " + 12 + " and a.pbcId = " + pbcId + " and a.piId = b.id";
		List<Map<String, Object>> mapList = this.spPaKpipbcService.findDataList(sql);
		StringBuffer buff = new StringBuffer("{success:true,result:[");
		for(int i = 0; i < mapList.size(); i++) {
			buff.append("{'id':'").append(mapList.get(i).get("id").toString())
					.append("','paName':'").append(mapList.get(i).get("paName")).append("'},");
		}
		this.jsonString = buff.toString();
		if(mapList.size() > 0) {
			this.jsonString = this.jsonString.substring(0, this.jsonString.length() - 1);
		}
		this.jsonString += "]}";
		
		return "success";
	}
	
	public String preview() {
		this.spPaKpipbc = this.spPaKpipbcService.get(this.id);
		
		SpPaKpiitemService spPaKpiitemService = (SpPaKpiitemService)AppUtil.getBean("spPaKpiitemService");
		Map<String, String> map = new HashMap<String, String>();
		map.put("Q_pbc.id_L_EQ", String.valueOf(this.id));
		QueryFilter filter = new QueryFilter(map);
		List<SpPaKpiitem> kpiItemList = spPaKpiitemService.getAll(filter);
		this.getRequest().setAttribute("kpiItemList", kpiItemList);
		
		return "show";
	}
	
	public String multiDel() {
		SpPaKpiPBC2UserService spPaKpiPBC2UserService = (SpPaKpiPBC2UserService)AppUtil.getBean("spPaKpiPBC2UserService");
		SpPaKpiitemService spPaKpiitemService = (SpPaKpiitemService)AppUtil.getBean("spPaKpiitemService");
		SpPaKpiitem2userService spPaKpiitem2userService = (SpPaKpiitem2userService)AppUtil.getBean("spPaKpiitem2userService");
		SpPaAuthpbccitemService spPaAuthpbccitemService = (SpPaAuthpbccitemService)AppUtil.getBean("spPaAuthpbccitemService");
		String[] ids = this.getRequest().getParameterValues("ids");
		if(ids != null) {
			for(String id : ids) {
				SpPaKpipbc hpk = this.spPaKpipbcService.get(new Long(id));
				hpk.setPublishStatus(new Integer("4"));//状态置为已删除
				this.spPaKpipbcService.save(hpk);
				//同步与该PBC关联的个人PBC
				String sql = "select a.id, a.fromPBC from sp_pa_kpipbc2user a, emp_profile b where " +
						"a.belongUser = b.userId and b.jobId = " + hpk.getBelongPost().getJobId();
				List<Map<String, Object>> mapList = this.spPaKpipbcService.findDataList(sql);
				for(int i = 0; i < mapList.size(); i++) {
					String[] fromPBCArray = mapList.get(i).get("fromPBC").toString().trim().split(",");
					List<String> fromPBCList = Arrays.asList(fromPBCArray);
					if(fromPBCList.contains(hpk.getId() + "")) {//个人PBC中包含该PBC
						String pbcIds = "";
						for(int j = 0; j < fromPBCList.size(); j++) {
							if(Long.parseLong(fromPBCList.get(j)) != hpk.getId()) {
								pbcIds += fromPBCList.get(j) + ",";
							}
						}
						if(pbcIds.length() > 0) {
							pbcIds = pbcIds.substring(0, pbcIds.length() - 1);
						}
						SpPaKpiPBC2User pbc2User = spPaKpiPBC2UserService.get(Long.parseLong(mapList.get(i).get("id").toString()));
						pbc2User.setFromPBC(pbcIds);
						spPaKpiPBC2UserService.save(pbc2User);
						//清除个人PBC冗余考核项
						Map<String, String> map3 = new HashMap<String, String>();
						map3.put("Q_pbc2User.id_L_EQ", mapList.get(i).get("id").toString());
						QueryFilter filter3 = new QueryFilter(map3);
						List<SpPaKpiitem2user> spPaKpiitem2userList = spPaKpiitem2userService.getAll(filter3);
						if(pbcIds.length() > 0) {
							for(int q = 0; q < spPaKpiitem2userList.size(); q++) {
								boolean flag2 = spPaKpiitemService.findByPiIdAndPbcId(spPaKpiitem2userList.get(q).getPiId(), pbcIds);
								if(!flag2) {
									//首先清除已授权的该考核项的信息
									Map<String, String> map4 = new HashMap<String, String>();
									map4.put("Q_akpiItem2uId_L_EQ", String.valueOf(spPaKpiitem2userList.get(q).getId()));
									QueryFilter filter4 = new QueryFilter(map4);
									List<SpPaAuthpbccitem> authpbcItemList = spPaAuthpbccitemService.getAll(filter4);
									for(int r = 0; r < authpbcItemList.size(); r++) {
										spPaAuthpbccitemService.remove(authpbcItemList.get(r));
									}
									//清除该考核项
									spPaKpiitem2userService.remove(spPaKpiitem2userList.get(q));
								}
							}
						} else {
							for(int q = 0; q < spPaKpiitem2userList.size(); q++) {
								spPaKpiitem2userService.remove(spPaKpiitem2userList.get(q));
							}
							spPaKpiPBC2UserService.remove(pbc2User);
						}
					}
				}
			}
		}
		
		return "success";
	}
	
	public SpPaKpipbc saveAsDraft()	{
		Date currentDate = new Date();
		AppUser currentUser = ContextUtil.getCurrentUser();
		SpPaKpiitemService spPaKpiitemService = (SpPaKpiitemService)AppUtil.getBean("spPaKpiitemService");
		//获取考核模板关联的考核项，并将所有考核项数据以” “拆分成单个考核项数组
		String[] spPaKpiitemArray = this.getRequest().getParameter("spPaKpiitems").trim().split(" ");
		if(this.spPaKpipbc.getId() == 0) {//新增PBC考核模板
			//保存PBC模板基本信息
			this.spPaKpipbc.setCreateDate(currentDate);
			this.spPaKpipbc.setCreatePerson(currentUser);
			this.spPaKpipbc.setModifyDate(currentDate);
			this.spPaKpipbc.setModifyPerson(currentUser);
			this.spPaKpipbc.setCoefficient(new Double(0));
			SpPaKpipbc pbcNew = this.spPaKpipbcService.save(this.spPaKpipbc);
			//保存PBC模板关联的考核项
			if(!this.getRequest().getParameter("spPaKpiitems").trim().equals("")) {
				for(int i = 0; i < spPaKpiitemArray.length; i++) {
					//新建一个HrPaKpiitem并为其赋值
					SpPaKpiitem spPaKpiitem = new SpPaKpiitem();
					SpPaPerformanceindex pi = new SpPaPerformanceindex();
					//将单个考核项数据已”,“拆分成数据字段数组
					String[] itemArray = spPaKpiitemArray[i].split(",");
					spPaKpiitem.setPbc(pbcNew);
					pi.setId(Long.parseLong(itemArray[1]));
					spPaKpiitem.setPi(pi);
					spPaKpiitem.setWeight(new Double(0));
					spPaKpiitem.setResult(0);//得分默认为零
					spPaKpiitem.setCoefficient(new Double(0));//权重默认为零
					//插入数据库
					spPaKpiitemService.save(spPaKpiitem);
				}
			}
			return pbcNew;
		} else {//修改PBC考核模板
			//获取原PBC考核模板
			SpPaKpipbc pbcOld = this.spPaKpipbcService.get(this.spPaKpipbc.getId());
			if(pbcOld.getPublishStatus() == 3) {
				//复制一份新的PBC考核模板
				SpPaKpipbc pbcOldCopy = new SpPaKpipbc();
				pbcOldCopy.setPbcName(this.spPaKpipbc.getPbcName());
				pbcOldCopy.setBelongDept(this.spPaKpipbc.getBelongDept());
				pbcOldCopy.setBelongPost(this.spPaKpipbc.getBelongPost());
				pbcOldCopy.setFrequency(this.spPaKpipbc.getFrequency());
				pbcOldCopy.setTotalScore(this.spPaKpipbc.getTotalScore());
				pbcOldCopy.setCreateDate(currentDate);
				pbcOldCopy.setCreatePerson(currentUser);
				pbcOldCopy.setPublishStatus(this.spPaKpipbc.getPublishStatus());
				pbcOldCopy.setModifyDate(currentDate);
				pbcOldCopy.setModifyPerson(currentUser);
				pbcOldCopy.setFromPbc(pbcOld.getId());
				pbcOldCopy.setCoefficient(new Double(0));
				pbcOldCopy.setFromPi(this.spPaKpipbc.getFromPi());
				pbcOldCopy.setPbcType(this.spPaKpipbc.getPbcType());
				//插入数据库
				SpPaKpipbc pbcNew = this.spPaKpipbcService.save(pbcOldCopy);
				//保存PBC模板关联考核项
				if(!this.getRequest().getParameter("spPaKpiitems").trim().equals("")) {
					for(int j = 0; j < spPaKpiitemArray.length; j++) {
						//新建一个HrPaKpiitem并为其赋值
						SpPaKpiitem spPaKpiitem = new SpPaKpiitem();
						SpPaPerformanceindex pi = new SpPaPerformanceindex();
						//将单个考核项数据已”,“拆分成数据字段数组
						String[] itemArray = spPaKpiitemArray[j].split(",");
						spPaKpiitem.setPbc(pbcNew);
						pi.setId(Long.parseLong(itemArray[1]));
						spPaKpiitem.setPi(pi);
						spPaKpiitem.setWeight(new Double(0));
						spPaKpiitem.setResult(0);//得分默认为零
						spPaKpiitem.setCoefficient(new Double(0));//权重默认为零
						//插入数据库
						spPaKpiitemService.save(spPaKpiitem);
					}
				}
				return pbcNew;
			} else if(pbcOld.getPublishStatus() == 0 || pbcOld.getPublishStatus() == 1 || pbcOld.getPublishStatus() == 2) {//原PBC模板为草稿、审核中、退回状态
				//保存PBC模板基本信息
				pbcOld.setPbcName(this.spPaKpipbc.getPbcName());
				pbcOld.setBelongDept(this.spPaKpipbc.getBelongDept());
				pbcOld.setBelongPost(this.spPaKpipbc.getBelongPost());
				pbcOld.setFrequency(this.spPaKpipbc.getFrequency());
				pbcOld.setPublishStatus(this.spPaKpipbc.getPublishStatus());
				pbcOld.setModifyDate(currentDate);
				pbcOld.setModifyPerson(currentUser);
				pbcOld.setTotalScore(this.spPaKpipbc.getTotalScore());
				pbcOld.setCoefficient(new Double(0));
				pbcOld.setFromPi(this.spPaKpipbc.getFromPi());
				pbcOld.setPbcType(this.spPaKpipbc.getPbcType());
				//插入数据库
				SpPaKpipbc pbcNew = this.spPaKpipbcService.save(pbcOld);
				//删除PBC模板关联的考核项
				Map<String, String> map = new HashMap<String, String>();
				map.put("Q_pbc.id_L_EQ", String.valueOf(this.spPaKpipbc.getId()));
				QueryFilter filter = new QueryFilter(map);
				List<SpPaKpiitem> hrPaKpiitemOldList = spPaKpiitemService.getAll(filter);
				for(int o = 0; o < hrPaKpiitemOldList.size(); o++) {
					spPaKpiitemService.remove(hrPaKpiitemOldList.get(o));
				}
				//保存PBC模板关联的考核项
				if(!this.getRequest().getParameter("spPaKpiitems").trim().equals("")) {
					for(int p = 0; p < spPaKpiitemArray.length; p++) {
						//新建一个HrPaKpiitem并为其赋值
						SpPaKpiitem hrPaKpiitem = new SpPaKpiitem();
						SpPaPerformanceindex pi = new SpPaPerformanceindex();
						//将单个考核项数据已”,“拆分成数据字段数组
						String[] itemArray = spPaKpiitemArray[p].split(",");
						hrPaKpiitem.setPbc(pbcNew);
						pi.setId(Long.parseLong(itemArray[1]));
						hrPaKpiitem.setPi(pi);
						hrPaKpiitem.setWeight(new Double(0));
						hrPaKpiitem.setResult(0);//得分默认为零
						hrPaKpiitem.setCoefficient(new Double(0));//权重默认为零
						//插入数据库
						spPaKpiitemService.save(hrPaKpiitem);
					}
				}
				return pbcNew;
			} else {
				return null;
			}
		}
	}
	
	public SpPaKpipbc saveToAudit() {
		//流程和保存草稿一样
		return this.saveAsDraft();
	}
	
	public String saveToPublish(long pbcId) {
		Date currentDate = new Date();
		AppUser currentUser = ContextUtil.getCurrentUser();
		SpPaKpiitemService spPaKpiitemService = (SpPaKpiitemService)AppUtil.getBean("spPaKpiitemService");
		if(pbcId != 0) {
			//获取PBC考核模板
			SpPaKpipbc pbcToPublish = this.spPaKpipbcService.get(pbcId);
			if(pbcToPublish.getFromPbc() == 0) {//是一套新发布的PBC模板
				pbcToPublish.setPublishStatus(3);//置为已发布状态
				pbcToPublish.setModifyDate(currentDate);
				pbcToPublish.setModifyPerson(currentUser);
				//插入数据库
				SpPaKpipbc pbcAfterPublish = this.spPaKpipbcService.save(pbcToPublish);
				//同步个人考核模板
				this.addUserPbc(pbcAfterPublish);
			} else {//是修改已有的PBC模板
				//获取原PBC模板
				SpPaKpipbc fromPbc = this.spPaKpipbcService.get(pbcToPublish.getFromPbc());
				//将原PBC模板复制到历史表里边
				SpPaKpipbcHistService spPaKpipbcHistService = (SpPaKpipbcHistService)AppUtil.getBean("spPaKpipbcHistService");
				SpPaKpiitemHistService spPaKpiitemHistService = (SpPaKpiitemHistService)AppUtil.getBean("spPaKpiitemHistService");
				SpPaKpipbcHist spPaKpipbcHist = new SpPaKpipbcHist();
				spPaKpipbcHist.setFromPbc(fromPbc.getId());
				spPaKpipbcHist.setPbcName(fromPbc.getPbcName());
				spPaKpipbcHist.setBelongDept(fromPbc.getBelongDept().getDepId());
				spPaKpipbcHist.setBelongPost(fromPbc.getBelongPost().getJobId());
				spPaKpipbcHist.setFrequency(fromPbc.getFrequency().getId());
				spPaKpipbcHist.setCreateDate(currentDate);
				spPaKpipbcHist.setCreatePerson(currentUser.getUserId());
				spPaKpipbcHist.setPublishStatus(fromPbc.getPublishStatus());
				spPaKpipbcHist.setTotalScore(fromPbc.getTotalScore());
				spPaKpipbcHist.setModifyDate(currentDate);
				spPaKpipbcHist.setModifyPerson(currentUser.getUserId());
				spPaKpipbcHist.setCoefficient(fromPbc.getCoefficient());
				spPaKpipbcHist.setFromPi(fromPbc.getFromPi());
				spPaKpipbcHist.setPbcType(fromPbc.getPbcType().getId());
				//插入数据库
				SpPaKpipbcHist pbcHist = spPaKpipbcHistService.save(spPaKpipbcHist);
				//将原PBC模板关联的考核项复制到历史表里边
				Map<String, String> map = new HashMap<String, String>();
				map.put("Q_pbc.id_L_EQ", String.valueOf(fromPbc.getId()));
				QueryFilter filter = new QueryFilter(map);
				List<SpPaKpiitem> fromPbcItemList = spPaKpiitemService.getAll(filter);
				for(int i = 0; i < fromPbcItemList.size(); i++) {
					SpPaKpiitemHist spPaKpiitemHist = new SpPaKpiitemHist();
					//hrPaKpiitemHist.setId(fromPbcItemList.get(i).getId());
					spPaKpiitemHist.setPbc(pbcHist);
					spPaKpiitemHist.setPiId(fromPbcItemList.get(i).getPi().getId());
					spPaKpiitemHist.setWeight(new Double(0));
					spPaKpiitemHist.setResult(fromPbcItemList.get(i).getResult());
					spPaKpiitemHist.setCoefficient(fromPbcItemList.get(i).getCoefficient());
					//插入数据库
					spPaKpiitemHistService.save(spPaKpiitemHist);
				}
				//同步原PBC模板，将pbcToPublish同步到fromPbc
				//同步PBC模板基本信息
				fromPbc.setPbcName(pbcToPublish.getPbcName());
				fromPbc.setBelongDept(pbcToPublish.getBelongDept());
				fromPbc.setBelongPost(pbcToPublish.getBelongPost());
				fromPbc.setFrequency(pbcToPublish.getFrequency());
				fromPbc.setPublishStatus(3);//置为已发布状态
				fromPbc.setTotalScore(pbcToPublish.getTotalScore());
				fromPbc.setModifyDate(currentDate);
				fromPbc.setModifyPerson(currentUser);
				fromPbc.setCoefficient(pbcToPublish.getCoefficient());
				fromPbc.setFromPi(pbcToPublish.getFromPi());
				fromPbc.setPbcType(pbcToPublish.getPbcType());
				//插入数据库
				SpPaKpipbc pbcAfterPublish = this.spPaKpipbcService.save(fromPbc);
				//同步PBC模板关联的考核项
				//删除fromPbc关联的考核项
				for(int j = 0; j < fromPbcItemList.size(); j++) {
					spPaKpiitemService.remove(fromPbcItemList.get(j));
				}
				//将pbcToPublish关联的考核项插入到fromPbc关联的里边
				Map<String, String> map2 = new HashMap<String, String>();
				map2.put("Q_pbc.id_L_EQ", String.valueOf(pbcToPublish.getId()));
				QueryFilter filter2 = new QueryFilter(map2);
				List<SpPaKpiitem> pbcTopublishItemList = spPaKpiitemService.getAll(filter2);
				for(int o = 0; o < pbcTopublishItemList.size(); o++) {
					SpPaKpiitem spPaKpiitem = new SpPaKpiitem();
					spPaKpiitem.setPbc(fromPbc);
					spPaKpiitem.setPi(pbcTopublishItemList.get(o).getPi());
					spPaKpiitem.setWeight(new Double(0));
					spPaKpiitem.setResult(pbcTopublishItemList.get(o).getResult());
					spPaKpiitem.setCoefficient(pbcTopublishItemList.get(o).getCoefficient());
					//插入数据库
					spPaKpiitemService.save(spPaKpiitem);
					//删除pbcToPublish关联的考核项
					spPaKpiitemService.remove(pbcTopublishItemList.get(o));
				}
				//删除pbcToPublish模板
				this.spPaKpipbcService.remove(pbcToPublish);
				//同步个人考核模板
				this.addUserPbc(pbcAfterPublish);
			}
		}
		
		this.jsonString = new String("{success:true}");
		
		return "success";
	}
	
	public String save() {
		SpPaKpipbc pbc = this.saveAsDraft();
		if(pbc.getPublishStatus() == 1) {
			this.saveToPublish(pbc.getId());
		}
		
		this.jsonString = new String("{success:true}");
		
		return "success";
	}
	
	public void addUserPbc(SpPaKpipbc pbc) {
		Date currentDate = new Date();
		AppUser currentUser = ContextUtil.getCurrentUser();
		SpPaKpiPBC2UserService spPaKpiPBC2UserService = (SpPaKpiPBC2UserService)AppUtil.getBean("spPaKpiPBC2UserService");
		SpPaKpiitemService spPaKpiitemService = (SpPaKpiitemService)AppUtil.getBean("spPaKpiitemService");
		SpPaKpiitem2userService spPaKpiitem2userService = (SpPaKpiitem2userService)AppUtil.getBean("spPaKpiitem2userService");
		SpPaAuthpbccitemService spPaAuthpbccitemService = (SpPaAuthpbccitemService)AppUtil.getBean("spPaAuthpbccitemService");
		String fdOfMonth = DateUtil.convertDateToString(DateUtil.getFirstDayOfMonth(new Date()));
		//取得PBC的考核频度
		String sql = "select name from sp_pa_datadictionary where id = " + pbc.getFrequency().getId();
		List<Map<String, Object>> mapList = this.spPaKpipbcService.findDataList(sql);
		String frequencyName = mapList.get(0).get("name").toString();
		//1. 找到哪些人是这个有这个PBC关联的岗位
		EmpProfileService empProfileService = (EmpProfileService)AppUtil.getBean("empProfileService");
		Map<String, String> profileMap = new HashMap<String, String>();
		profileMap.put("Q_jobId_L_EQ", String.valueOf(pbc.getBelongPost().getJobId()));
		QueryFilter profileFilter = new QueryFilter(profileMap);
		List<EmpProfile> profileList = empProfileService.getAll(profileFilter);
		AppUserService appUserService = (AppUserService)AppUtil.getBean("appUserService");
		//2. 循环对每个人添加PBC考核模板
		for(int i = 0; i < profileList.size(); i++) {
			//取出要插入PBC考核模板关联的考核项
			Map<String, String> map2 = new HashMap<String, String>();
			map2.put("Q_pbc.id_L_EQ", String.valueOf(pbc.getId()));
			QueryFilter filter2 = new QueryFilter(map2);
			List<SpPaKpiitem> spPaKpiitemList = spPaKpiitemService.getAll(filter2);
			
			AppUser user = appUserService.get(profileList.get(i).getUserId());
			//2.1. 判断hr_pa_kpipbc2user表中有没有该User的模板
			Map<String, String> map = new HashMap<String, String>();
			map.put("Q_belongUser.userId_L_EQ", String.valueOf(user.getUserId()));
			map.put("Q_fromPBC_S_EQ", pbc.getId() + "");
			map.put("Q_createDate_D_GT", fdOfMonth);
			QueryFilter filter = new QueryFilter(map);
			List<SpPaKpiPBC2User> spPaKpiPBC2UserList = spPaKpiPBC2UserService.getAll(filter);
			//默认每次发布都为user创建新的模板
			if(1 == 1) {//在hr_pa_kpipbc2user表中没有该User的模板，则为其创建新的模板
				//2.1.1. 保存个人考核模板基本信息
				SpPaKpiPBC2User spPaKpiPBC2User = new SpPaKpiPBC2User();
				spPaKpiPBC2User.setPbcName(user.getFullname() + "的" + frequencyName + "PBC - " + pbc.getPbcName());
				spPaKpiPBC2User.setFromPBC(String.valueOf(pbc.getId()));
				spPaKpiPBC2User.setBelongUser(user);
				spPaKpiPBC2User.setFrequency(pbc.getFrequency());
				spPaKpiPBC2User.setCreatePerson(pbc.getCreatePerson());
				spPaKpiPBC2User.setCreateDate(currentDate);
				spPaKpiPBC2User.setPublishStatus(0);//默认为草稿状态
				spPaKpiPBC2User.setTotalScore(pbc.getTotalScore());
				spPaKpiPBC2User.setModifyDate(currentDate);
				spPaKpiPBC2User.setModifyPerson(currentUser);
				spPaKpiPBC2User.setCoefficient(new Double(0));
				spPaKpiPBC2User.setFromPi(pbc.getFromPi());
				spPaKpiPBC2User.setPbcType(pbc.getPbcType().getId());
				//插入数据库
				spPaKpiPBC2User = spPaKpiPBC2UserService.save(spPaKpiPBC2User);
				
				//2.1.2. 保存个人考核模板关联的考核项
				for(int j = 0; j < spPaKpiitemList.size(); j++) {
					SpPaKpiitem2user spPaKpiitem2user = new SpPaKpiitem2user();
					spPaKpiitem2user.setPbc2User(spPaKpiPBC2User);
					spPaKpiitem2user.setPiId(spPaKpiitemList.get(j).getPi().getId());
					spPaKpiitem2user.setWeight(new Double(0));//直接将岗位PBC模板权值复制给个人
					spPaKpiitem2user.setResult(new Double(0));//等待计算时设置结果
					spPaKpiitem2user.setCoefficient(new Double(0));//等待计算时设置结果
					//插入数据库
					spPaKpiitem2userService.save(spPaKpiitem2user);
				}
			} else {//在hr_pa_kpipbc2user表中有该User的模板，则合并模板
				SpPaKpiPBC2User spPaKpiPBC2User = spPaKpiPBC2UserList.get(0);
				String[] fromPbcArray = spPaKpiPBC2User.getFromPBC().split(",");
				boolean flag = false;
				//判断要插入的模板和已存在模板考核频度是否一致
				for(int p = 0; p < fromPbcArray.length; p++) {
					SpPaKpipbc spPaKpipbc = spPaKpipbcService.get(Long.parseLong(fromPbcArray[p]));
					if(pbc.getFrequency().getId() != spPaKpipbc.getFrequency().getId()) {
						return ;    //如果出现频率不一致的则直接返回，不进行合并。
					}
				}
				//清除个人PBC冗余考核项
				Map<String, String> map3 = new HashMap<String, String>();
				map3.put("Q_pbc2User.id_L_EQ", String.valueOf(spPaKpiPBC2User.getId()));
				QueryFilter filter3 = new QueryFilter(map3);
				List<SpPaKpiitem2user> spPaKpiitem2userList = spPaKpiitem2userService.getAll(filter3);
				for(int q = 0; q < spPaKpiitem2userList.size(); q++) {
					boolean flag2 = spPaKpiitemService.findByPiIdAndPbcId(spPaKpiitem2userList.get(q).getPiId(), spPaKpiPBC2User.getFromPBC());
					if(!flag2) {
						//首先清除已授权的该考核项的信息
						Map<String, String> map4 = new HashMap<String, String>();
						map4.put("Q_akpiItem2uId_L_EQ", String.valueOf(spPaKpiitem2userList.get(q).getId()));
						QueryFilter filter4 = new QueryFilter(map4);
						List<SpPaAuthpbccitem> authpbcItemList = spPaAuthpbccitemService.getAll(filter4);
						for(int r = 0; r < authpbcItemList.size(); r++) {
							spPaAuthpbccitemService.remove(authpbcItemList.get(r));
						}
						//清除该考核项
						spPaKpiitem2userService.remove(spPaKpiitem2userList.get(q));
						spPaKpiitem2userList.remove(q);
					}
				}
				spPaKpiPBC2User.setPublishStatus(0);//设置为未加权值状态
				spPaKpiPBC2User.setModifyDate(currentDate);
				spPaKpiPBC2User.setModifyPerson(currentUser);
				spPaKpiPBC2UserService.save(spPaKpiPBC2User);
				//2.2.3. 合并个人考核模板关联的考核项
				for(int n = 0; n < spPaKpiitemList.size(); n++) {
					SpPaKpiitem2user itemNew = new SpPaKpiitem2user();
					for(int o = 0; o < spPaKpiitem2userList.size(); o++) {
						if(spPaKpiitemList.get(n).getPi().getId().longValue() == spPaKpiitem2userList.get(o).getPiId().longValue()) {
							itemNew = spPaKpiitem2userList.get(o);
							break;
						}
					}
					itemNew.setPbc2User(spPaKpiPBC2User);
					itemNew.setPiId(spPaKpiitemList.get(n).getPi().getId());
					itemNew.setWeight(new Double(0));//直接将岗位PBC模板权值复制给个人
					itemNew.setResult(new Double(0));//等待计算时设置结果
					itemNew.setCoefficient(new Double(0));//等待计算时设置结果
					//插入数据库
					spPaKpiitem2userService.save(itemNew);
				}
			}
		}
	}
}
