package com.xpsoft.oa.action.kpi;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.xpsoft.core.command.QueryFilter;
import com.xpsoft.core.util.AppUtil;
import com.xpsoft.core.util.ContextUtil;
import com.xpsoft.core.web.action.BaseAction;
import com.xpsoft.oa.model.hrm.EmpProfile;
import com.xpsoft.oa.model.kpi.HrPaAuthorizepbc;
import com.xpsoft.oa.model.kpi.HrPaAuthpbccitem;
import com.xpsoft.oa.model.kpi.HrPaKpiPBC2User;
import com.xpsoft.oa.model.kpi.HrPaKpiitem;
import com.xpsoft.oa.model.kpi.HrPaKpiitem2user;
import com.xpsoft.oa.model.kpi.HrPaKpiitemHist;
import com.xpsoft.oa.model.kpi.HrPaKpipbc;
import com.xpsoft.oa.model.kpi.HrPaKpipbcHist;
import com.xpsoft.oa.model.kpi.HrPaPerformanceindex;
import com.xpsoft.oa.model.system.AppUser;
import com.xpsoft.oa.service.hrm.EmpProfileService;
import com.xpsoft.oa.service.kpi.HrPaAuthorizepbcService;
import com.xpsoft.oa.service.kpi.HrPaAuthpbccitemService;
import com.xpsoft.oa.service.kpi.HrPaKpiPBC2UserService;
import com.xpsoft.oa.service.kpi.HrPaKpiitem2userService;
import com.xpsoft.oa.service.kpi.HrPaKpiitemHistService;
import com.xpsoft.oa.service.kpi.HrPaKpiitemService;
import com.xpsoft.oa.service.kpi.HrPaKpipbcHistService;
import com.xpsoft.oa.service.kpi.HrPaKpipbcService;
import com.xpsoft.oa.service.kpi.HrPaPerformanceindexService;
import com.xpsoft.oa.service.system.AppUserService;

import flexjson.JSONSerializer;

public class HrPaKpipbcAction extends BaseAction{
	@Resource
	private HrPaKpipbcService hrPaKpipbcService;
	private HrPaKpipbc hrPaKpipbc;
	private long id;
	
	public HrPaKpipbcService getHrPaKpipbcService() {
		return hrPaKpipbcService;
	}
	public void setHrPaKpipbcService(HrPaKpipbcService hrPaKpipbcService) {
		this.hrPaKpipbcService = hrPaKpipbcService;
	}
	public HrPaKpipbc getHrPaKpipbc() {
		return hrPaKpipbc;
	}
	public void setHrPaKpipbc(HrPaKpipbc hrPaKpipbc) {
		this.hrPaKpipbc = hrPaKpipbc;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	
	public String list() {
		QueryFilter filter = new QueryFilter(this.getRequest());
		List<HrPaKpipbc> list = this.hrPaKpipbcService.getAll(filter);
		
		StringBuffer buff = new StringBuffer("{success:true,'totalCounts':")
				.append(filter.getPagingBean().getTotalItems()).append(",result:");
		JSONSerializer json = new JSONSerializer();
		buff.append(json.exclude(new String[] {}).serialize(list));
		buff.append("}");
		this.jsonString = buff.toString();
		
		return "success";
	}
	
	public String get() {
		this.hrPaKpipbc = (HrPaKpipbc)this.hrPaKpipbcService.get(this.id);
		
		JSONSerializer json = new JSONSerializer();
		StringBuffer buff = new StringBuffer("{success:true,data:");
		buff.append(json.exclude(new String[] {}).serialize(this.hrPaKpipbc));
		buff.append("}");
		this.jsonString = buff.toString();
		
		return "success";
	}
	
	public String preview() {
		this.hrPaKpipbc = this.hrPaKpipbcService.get(this.id);
		
		HrPaKpiitemService hrPaKpiitemService = (HrPaKpiitemService)AppUtil.getBean("hrPaKpiitemService");
		Map<String, String> map = new HashMap<String, String>();
		map.put("Q_pbc.id_L_EQ", String.valueOf(this.id));
		QueryFilter filter = new QueryFilter(map);
		List<HrPaKpiitem> kpiItemList = hrPaKpiitemService.getAll(filter);
		this.getRequest().setAttribute("kpiItemList", kpiItemList);
		
		return "show";
	}
	
	public String multiDel() {
		String[] ids = this.getRequest().getParameterValues("ids");
		if(ids != null) {
			for(String id : ids) {
				HrPaKpipbc hpk = this.hrPaKpipbcService.get(new Long(id));
				hpk.setPublishStatus(new Integer("4"));//状态置为已删除
				this.hrPaKpipbcService.save(hpk);
			}
		}
		
		return "success";
	}
	
	public HrPaKpipbc saveAsDraft()	{
		Date currentDate = new Date();
		AppUser currentUser = ContextUtil.getCurrentUser();
		HrPaKpiitemService hrPaKpiitemService = (HrPaKpiitemService)AppUtil.getBean("hrPaKpiitemService");
		//获取考核模板关联的考核项，并将所有考核项数据以” “拆分成单个考核项数组
		String[] hrPaKpiitemArray = this.getRequest().getParameter("hrPaKpiitems").trim().split(" ");
		if(this.hrPaKpipbc.getId() == 0) {//新增PBC考核模板
			//保存PBC模板基本信息
			this.hrPaKpipbc.setCreateDate(currentDate);
			this.hrPaKpipbc.setCreatePerson(currentUser);
			this.hrPaKpipbc.setModifyDate(currentDate);
			this.hrPaKpipbc.setModifyPerson(currentUser);
			HrPaKpipbc pbcNew = this.hrPaKpipbcService.save(this.hrPaKpipbc);
			//保存PBC模板关联的考核项
			if(!this.getRequest().getParameter("hrPaKpiitems").trim().equals("")) {
				for(int i = 0; i < hrPaKpiitemArray.length; i++) {
					//新建一个HrPaKpiitem并为其赋值
					HrPaKpiitem hrPaKpiitem = new HrPaKpiitem();
					HrPaPerformanceindex pi = new HrPaPerformanceindex();
					//将单个考核项数据已”,“拆分成数据字段数组
					String[] itemArray = hrPaKpiitemArray[i].split(",");
					hrPaKpiitem.setPbc(pbcNew);
					pi.setId(Long.parseLong(itemArray[1]));
					hrPaKpiitem.setPi(pi);
					hrPaKpiitem.setWeight(Double.parseDouble(itemArray[2]));
					hrPaKpiitem.setResult(0);//得分默认为零
					//插入数据库
					hrPaKpiitemService.save(hrPaKpiitem);
				}
			}
			return pbcNew;
		} else {//修改PBC考核模板
			//获取原PBC考核模板
			HrPaKpipbc pbcOld = this.hrPaKpipbcService.get(this.hrPaKpipbc.getId());
			if(pbcOld.getPublishStatus() == 3) {
				//复制一份新的PBC考核模板
				HrPaKpipbc pbcOldCopy = new HrPaKpipbc();
				pbcOldCopy.setPbcName(this.hrPaKpipbc.getPbcName());
				pbcOldCopy.setBelongDept(this.hrPaKpipbc.getBelongDept());
				pbcOldCopy.setBelongPost(this.hrPaKpipbc.getBelongPost());
				pbcOldCopy.setFrequency(this.hrPaKpipbc.getFrequency());
				pbcOldCopy.setTotalScore(this.hrPaKpipbc.getTotalScore());
				pbcOldCopy.setCreateDate(currentDate);
				pbcOldCopy.setCreatePerson(currentUser);
				pbcOldCopy.setPublishStatus(this.hrPaKpipbc.getPublishStatus());
				pbcOldCopy.setModifyDate(currentDate);
				pbcOldCopy.setModifyPerson(currentUser);
				pbcOldCopy.setFromPbc(pbcOld.getId());
				//插入数据库
				HrPaKpipbc pbcNew = this.hrPaKpipbcService.save(pbcOldCopy);
				//保存PBC模板关联考核项
				if(!this.getRequest().getParameter("hrPaKpiitems").trim().equals("")) {
					for(int j = 0; j < hrPaKpiitemArray.length; j++) {
						//新建一个HrPaKpiitem并为其赋值
						HrPaKpiitem hrPaKpiitem = new HrPaKpiitem();
						HrPaPerformanceindex pi = new HrPaPerformanceindex();
						//将单个考核项数据已”,“拆分成数据字段数组
						String[] itemArray = hrPaKpiitemArray[j].split(",");
						hrPaKpiitem.setPbc(pbcNew);
						pi.setId(Long.parseLong(itemArray[1]));
						hrPaKpiitem.setPi(pi);
						hrPaKpiitem.setWeight(Double.parseDouble(itemArray[2]));
						hrPaKpiitem.setResult(0);//得分默认为零
						//插入数据库
						hrPaKpiitemService.save(hrPaKpiitem);
					}
				}
				return pbcNew;
			} else if(pbcOld.getPublishStatus() == 0 || pbcOld.getPublishStatus() == 1 || pbcOld.getPublishStatus() == 2) {//原PBC模板为草稿、审核中、退回状态
				//保存PBC模板基本信息
				pbcOld.setPbcName(this.hrPaKpipbc.getPbcName());
				pbcOld.setBelongDept(this.hrPaKpipbc.getBelongDept());
				pbcOld.setBelongPost(this.hrPaKpipbc.getBelongPost());
				pbcOld.setFrequency(this.hrPaKpipbc.getFrequency());
				pbcOld.setPublishStatus(this.hrPaKpipbc.getPublishStatus());
				pbcOld.setModifyDate(currentDate);
				pbcOld.setModifyPerson(currentUser);
				pbcOld.setTotalScore(this.hrPaKpipbc.getTotalScore());
				//插入数据库
				HrPaKpipbc pbcNew = this.hrPaKpipbcService.save(pbcOld);
				//删除PBC模板关联的考核项
				Map<String, String> map = new HashMap<String, String>();
				map.put("Q_pbc.id_L_EQ", String.valueOf(this.hrPaKpipbc.getId()));
				QueryFilter filter = new QueryFilter(map);
				List<HrPaKpiitem> hrPaKpiitemOldList = hrPaKpiitemService.getAll(filter);
				for(int o = 0; o < hrPaKpiitemOldList.size(); o++) {
					hrPaKpiitemService.remove(hrPaKpiitemOldList.get(o));
				}
				//保存PBC模板关联的考核项
				if(!this.getRequest().getParameter("hrPaKpiitems").trim().equals("")) {
					for(int p = 0; p < hrPaKpiitemArray.length; p++) {
						//新建一个HrPaKpiitem并为其赋值
						HrPaKpiitem hrPaKpiitem = new HrPaKpiitem();
						HrPaPerformanceindex pi = new HrPaPerformanceindex();
						//将单个考核项数据已”,“拆分成数据字段数组
						String[] itemArray = hrPaKpiitemArray[p].split(",");
						hrPaKpiitem.setPbc(pbcNew);
						pi.setId(Long.parseLong(itemArray[1]));
						hrPaKpiitem.setPi(pi);
						hrPaKpiitem.setWeight(Double.parseDouble(itemArray[2]));
						hrPaKpiitem.setResult(0);//得分默认为零
						//插入数据库
						hrPaKpiitemService.save(hrPaKpiitem);
					}
				}
				return pbcNew;
			} else {
				return null;
			}
		}
	}
	
	public HrPaKpipbc saveToAudit() {
		//流程和保存草稿一样
		return this.saveAsDraft();
	}
	
	public String saveToPublish(long pbcId) {
		Date currentDate = new Date();
		AppUser currentUser = ContextUtil.getCurrentUser();
		HrPaKpiitemService hrPaKpiitemService = (HrPaKpiitemService)AppUtil.getBean("hrPaKpiitemService");
		if(pbcId != 0) {
			//获取PBC考核模板
			HrPaKpipbc pbcToPublish = this.hrPaKpipbcService.get(pbcId);
			if(pbcToPublish.getFromPbc() == 0) {//是一套新发布的PBC模板
				pbcToPublish.setPublishStatus(3);//置为已发布状态
				pbcToPublish.setModifyDate(currentDate);
				pbcToPublish.setModifyPerson(currentUser);
				//插入数据库
				HrPaKpipbc pbcAfterPublish = this.hrPaKpipbcService.save(pbcToPublish);
				//同步个人考核模板
				this.addUserPbc(pbcAfterPublish);
			} else {//是修改已有的PBC模板
				//获取原PBC模板
				HrPaKpipbc fromPbc = this.hrPaKpipbcService.get(pbcToPublish.getFromPbc());
				//将原PBC模板复制到历史表里边
				HrPaKpipbcHistService hrPaKpipbcHistService = (HrPaKpipbcHistService)AppUtil.getBean("hrPaKpipbcHistService");
				HrPaKpiitemHistService hrPaKpiitemHistService = (HrPaKpiitemHistService)AppUtil.getBean("hrPaKpiitemHistService");
				HrPaKpipbcHist hrPaKpipbcHist = new HrPaKpipbcHist();
				hrPaKpipbcHist.setFromPbc(fromPbc.getId());
				hrPaKpipbcHist.setPbcName(fromPbc.getPbcName());
				hrPaKpipbcHist.setBelongDept(fromPbc.getBelongDept().getDepId());
				hrPaKpipbcHist.setBelongPost(fromPbc.getBelongPost().getJobId());
				hrPaKpipbcHist.setFrequency(fromPbc.getFrequency().getId());
				hrPaKpipbcHist.setCreateDate(currentDate);
				hrPaKpipbcHist.setCreatePerson(currentUser.getUserId());
				hrPaKpipbcHist.setPublishStatus(fromPbc.getPublishStatus());
				hrPaKpipbcHist.setTotalScore(fromPbc.getTotalScore());
				hrPaKpipbcHist.setModifyDate(currentDate);
				hrPaKpipbcHist.setModifyPerson(currentUser.getUserId());
				//插入数据库
				HrPaKpipbcHist pbcHist = hrPaKpipbcHistService.save(hrPaKpipbcHist);
				//将原PBC模板关联的考核项复制到历史表里边
				Map<String, String> map = new HashMap<String, String>();
				map.put("Q_pbc.id_L_EQ", String.valueOf(fromPbc.getId()));
				QueryFilter filter = new QueryFilter(map);
				List<HrPaKpiitem> fromPbcItemList = hrPaKpiitemService.getAll(filter);
				for(int i = 0; i < fromPbcItemList.size(); i++) {
					HrPaKpiitemHist hrPaKpiitemHist = new HrPaKpiitemHist();
					//hrPaKpiitemHist.setId(fromPbcItemList.get(i).getId());
					hrPaKpiitemHist.setPbc(pbcHist);
					hrPaKpiitemHist.setPiId(fromPbcItemList.get(i).getPi().getId());
					hrPaKpiitemHist.setWeight(fromPbcItemList.get(i).getWeight());
					hrPaKpiitemHist.setResult(fromPbcItemList.get(i).getResult());
					//插入数据库
					hrPaKpiitemHistService.save(hrPaKpiitemHist);
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
				//插入数据库
				HrPaKpipbc pbcAfterPublish = this.hrPaKpipbcService.save(fromPbc);
				//同步PBC模板关联的考核项
				//删除fromPbc关联的考核项
				for(int j = 0; j < fromPbcItemList.size(); j++) {
					hrPaKpiitemService.remove(fromPbcItemList.get(j));
				}
				//将pbcToPublish关联的考核项插入到fromPbc关联的里边
				Map<String, String> map2 = new HashMap<String, String>();
				map2.put("Q_pbc.id_L_EQ", String.valueOf(pbcToPublish.getId()));
				QueryFilter filter2 = new QueryFilter(map2);
				List<HrPaKpiitem> pbcTopublishItemList = hrPaKpiitemService.getAll(filter2);
				for(int o = 0; o < pbcTopublishItemList.size(); o++) {
					HrPaKpiitem hrPaKpiitem = new HrPaKpiitem();
					hrPaKpiitem.setPbc(fromPbc);
					hrPaKpiitem.setPi(pbcTopublishItemList.get(o).getPi());
					hrPaKpiitem.setWeight(pbcTopublishItemList.get(o).getWeight());
					hrPaKpiitem.setResult(pbcTopublishItemList.get(o).getResult());
					//插入数据库
					hrPaKpiitemService.save(hrPaKpiitem);
					//删除pbcToPublish关联的考核项
					hrPaKpiitemService.remove(pbcTopublishItemList.get(o));
				}
				//删除pbcToPublish模板
				this.hrPaKpipbcService.remove(pbcToPublish);
				//同步个人考核模板
				this.addUserPbc(pbcAfterPublish);
			}
		}
		
		this.jsonString = new String("{success:true}");
		
		return "success";
	}
	
	public String save() {
		HrPaKpipbc pbc = this.saveAsDraft();
		if(pbc.getPublishStatus() == 1) {
			this.saveToPublish(pbc.getId());
		}
		
		this.jsonString = new String("{success:true}");
		
		return "success";
	}
	
	public void addUserPbc(HrPaKpipbc pbc) {
		Date currentDate = new Date();
		AppUser currentUser = ContextUtil.getCurrentUser();
		HrPaKpiPBC2UserService hrPaKpiPBC2UserService = (HrPaKpiPBC2UserService)AppUtil.getBean("hrPaKpiPBC2UserService");
		HrPaKpiitemService hrPaKpiitemService = (HrPaKpiitemService)AppUtil.getBean("hrPaKpiitemService");
		HrPaKpiitem2userService hrPaKpiitem2userService = (HrPaKpiitem2userService)AppUtil.getBean("hrPaKpiitem2userService");
		HrPaAuthpbccitemService hrPaAuthpbccitemService = (HrPaAuthpbccitemService)AppUtil.getBean("hrPaAuthpbccitemService");
		HrPaAuthorizepbcService hrPaAuthorizepbcService = (HrPaAuthorizepbcService)AppUtil.getBean("hrPaAuthorizepbcService");
		HrPaPerformanceindexService hrPaPerformanceindexService = (HrPaPerformanceindexService)AppUtil.getBean("hrPaPerformanceindexService");
		//1. 找到哪些人是这个有这个PBC关联的岗位
		EmpProfileService empProfileService = (EmpProfileService)AppUtil.getBean("empProfileService");
		Map<String, String> profileMap = new HashMap<String, String>();
		profileMap.put("Q_jobId_L_EQ", String.valueOf(pbc.getBelongPost().getJobId()));
		QueryFilter profileFilter = new QueryFilter(profileMap);
		List<EmpProfile> profileList = empProfileService.getAll(profileFilter);
		AppUserService appUserService = (AppUserService)AppUtil.getBean("appUserService");
		//找到个人所属部门负责人userId
		Long chiefId = new Long(0);
		String chiefSql = "select deptUserId from arch_rec_user where depId = " + pbc.getBelongDept().getDepId();
		List<Map<String, Object>> chiefList = this.hrPaKpipbcService.findDataList(chiefSql);
		if(chiefList.size() > 0) {
			chiefId = Long.parseLong(chiefList.get(0).get("deptUserId").toString());
		}
		//2. 循环对每个人添加PBC考核模板
		for(int i = 0; i < profileList.size(); i++) {
			//取出要插入PBC考核模板关联的考核项
			Map<String, String> map2 = new HashMap<String, String>();
			map2.put("Q_pbc.id_L_EQ", String.valueOf(pbc.getId()));
			QueryFilter filter2 = new QueryFilter(map2);
			List<HrPaKpiitem> hrPaKpiitemList = hrPaKpiitemService.getAll(filter2);
			
			AppUser user = appUserService.get(profileList.get(i).getUserId());
			//2.1. 判断hr_pa_kpipbc2user表中有没有该User的模板
			Map<String, String> map = new HashMap<String, String>();
			map.put("Q_belongUser.userId_L_EQ", String.valueOf(user.getUserId()));
			QueryFilter filter = new QueryFilter(map);
			List<HrPaKpiPBC2User> hrPaKpiPBC2UserList = hrPaKpiPBC2UserService.getAll(filter);
			if(hrPaKpiPBC2UserList.size() <= 0) {//在hr_pa_kpipbc2user表中没有该User的模板，则为其创建新的模板
				//2.1.1. 保存个人考核模板基本信息
				HrPaKpiPBC2User hrPaKpiPBC2User = new HrPaKpiPBC2User();
				hrPaKpiPBC2User.setPbcName(user.getFullname() + "的考核模板");
				hrPaKpiPBC2User.setFromPBC(String.valueOf(pbc.getId()));
				hrPaKpiPBC2User.setBelongUser(user);
				hrPaKpiPBC2User.setFrequency(pbc.getFrequency());
				hrPaKpiPBC2User.setCreatePerson(pbc.getCreatePerson());
				hrPaKpiPBC2User.setCreateDate(pbc.getCreateDate());
				hrPaKpiPBC2User.setPublishStatus(pbc.getPublishStatus());
				hrPaKpiPBC2User.setTotalScore(pbc.getTotalScore());
				hrPaKpiPBC2User.setModifyDate(currentDate);
				hrPaKpiPBC2User.setModifyPerson(currentUser);
				//插入数据库
				hrPaKpiPBC2User = hrPaKpiPBC2UserService.save(hrPaKpiPBC2User);
				//给负责人添加默认授权考核模板
				HrPaAuthorizepbc hrPaAuthorizepbc = new HrPaAuthorizepbc();
				hrPaAuthorizepbc.setUserPbc(hrPaKpiPBC2User);
				hrPaAuthorizepbc.setAuthorTo(new AppUser(chiefId));
				hrPaAuthorizepbc.setAuthDate(currentDate);
				hrPaAuthorizepbc.setAuthPerson(currentUser);
				//插入数据库
				HrPaAuthorizepbc authPbcNew = hrPaAuthorizepbcService.save(hrPaAuthorizepbc);
				
				//2.1.2. 保存个人考核模板关联的考核项
				for(int j = 0; j < hrPaKpiitemList.size(); j++) {
					HrPaKpiitem2user hrPaKpiitem2user = new HrPaKpiitem2user();
					hrPaKpiitem2user.setPbc2User(hrPaKpiPBC2User);
					hrPaKpiitem2user.setPiId(hrPaKpiitemList.get(j).getPi().getId());
					hrPaKpiitem2user.setWeight(hrPaKpiitemList.get(j).getWeight());//直接将岗位PBC模板权值复制给个人
					hrPaKpiitem2user.setResult(new Double(0));//等待定时计算时设置结果
					//插入数据库
					HrPaKpiitem2user kpiItem2userNew = hrPaKpiitem2userService.save(hrPaKpiitem2user);
					//判断该考核项关联的考核指标是定量还是定性
					Long mode = hrPaPerformanceindexService.get(hrPaKpiitemList.get(j).getPi().getId()).getMode().getId();
					if(mode == 12) {
						//给负责人添加默认考核模板关联的考核项
						HrPaAuthpbccitem hrPaAuthpbccitem = new HrPaAuthpbccitem();
						hrPaAuthpbccitem.setAuthorPbc(authPbcNew);
						hrPaAuthpbccitem.setAkpiItem2uId(kpiItem2userNew.getId());
						hrPaAuthpbccitem.setResult(new Double(0));
						hrPaAuthpbccitem.setWeight(new Double(0));
						//插入数据库
						hrPaAuthpbccitemService.save(hrPaAuthpbccitem);
					}
				}
			} else {//在hr_pa_kpipbc2user表中有该User的模板，则合并模板
				HrPaKpiPBC2User hrPaKpiPBC2User = hrPaKpiPBC2UserList.get(0);
				String[] fromPbcArray = hrPaKpiPBC2User.getFromPBC().split(",");
				boolean flag = false;
				//判断要插入的模板和已存在模板考核频度是否一致
				for(int p = 0; p < fromPbcArray.length; p++) {
					HrPaKpipbc hrPaKpipbc = hrPaKpipbcService.get(Long.parseLong(fromPbcArray[p]));
					if(pbc.getFrequency().getId() != hrPaKpipbc.getFrequency().getId()) {
						return ;    //如果出现频率不一致的则直接返回，不进行合并。
					}
				}
				//清除个人PBC冗余考核项
				Map<String, String> map3 = new HashMap<String, String>();
				map3.put("Q_pbc2User.id_L_EQ", String.valueOf(hrPaKpiPBC2User.getId()));
				QueryFilter filter3 = new QueryFilter(map3);
				List<HrPaKpiitem2user> hrPaKpiitem2userList = hrPaKpiitem2userService.getAll(filter3);
				for(int q = 0; q < hrPaKpiitem2userList.size(); q++) {
					boolean flag2 = hrPaKpiitemService.findByPiIdAndPbcId(hrPaKpiitem2userList.get(q).getPiId(), fromPbcArray);
					if(!flag2) {
						//首先清除已授权的该考核项的信息
						Map<String, String> map4 = new HashMap<String, String>();
						map4.put("Q_akpiItem2uId_L_EQ", String.valueOf(hrPaKpiitem2userList.get(q).getId()));
						QueryFilter filter4 = new QueryFilter(map4);
						List<HrPaAuthpbccitem> authpbcItemList = hrPaAuthpbccitemService.getAll(filter4);
						for(int r = 0; r < authpbcItemList.size(); r++) {
							hrPaAuthpbccitemService.remove(authpbcItemList.get(r));
						}
						//清除该考核项
						hrPaKpiitem2userService.remove(hrPaKpiitem2userList.get(q));
					}
				}
				//2.2.1. 判断该User原PBC模板是否包含要插入的PBC模板
				for(int k = 0; k < fromPbcArray.length; k++) {
					if(String.valueOf(pbc.getId()).equals(fromPbcArray[k])) {
						flag = true;
						break;
					}
				}
				//2.2.2. 保存个人考核模板基本信息
				if(!flag) {//原模板中不包含要插入的PBC模板
					hrPaKpiPBC2User.setFromPBC(hrPaKpiPBC2User.getFromPBC() + "," + pbc.getId());
				}
				hrPaKpiPBC2User.setPublishStatus(0);//设置为未加权值状态
				hrPaKpiPBC2User.setModifyDate(currentDate);
				hrPaKpiPBC2User.setModifyPerson(currentUser);
				hrPaKpiPBC2UserService.save(hrPaKpiPBC2User);
				//找到个人所属部门负责人默认授权考核模板
				HrPaAuthorizepbc authorPbc = new HrPaAuthorizepbc();
				String sql = "select id from hr_pa_authorizepbc where pbcId = " + hrPaKpiPBC2User.getId() + " and userId = " + chiefId;
				List<Map<String, Object>> defaultAuthList = this.hrPaKpipbcService.findDataList(sql);
				if(defaultAuthList.size() > 0) {
					authorPbc.setId(Long.parseLong(defaultAuthList.get(0).get("id").toString()));
				}
				//2.2.3. 合并个人考核模板关联的考核项
				for(int n = 0; n < hrPaKpiitemList.size(); n++) {
					boolean flag2 = false;
					HrPaKpiitem2user itemNew = new HrPaKpiitem2user();
					for(int o = 0; o < hrPaKpiitem2userList.size(); o++) {
						if(hrPaKpiitemList.get(n).getPi().getId().longValue() == hrPaKpiitem2userList.get(o).getPiId().longValue()) {
							flag2 = true;
							itemNew = hrPaKpiitem2userList.get(o);
							break;
						}
					}
					itemNew.setPbc2User(hrPaKpiPBC2User);
					itemNew.setPiId(hrPaKpiitemList.get(n).getPi().getId());
					itemNew.setWeight(hrPaKpiitemList.get(n).getWeight());//直接将岗位PBC模板权值复制给个人
					itemNew.setResult(new Double(0));//等待定时计算时设置结果
					HrPaKpiitem2user itemNewSave = hrPaKpiitem2userService.save(itemNew);
					if(!flag2) {
						//判断该考核项关联的考核指标是定量还是定性
						Long mode = hrPaPerformanceindexService.get(itemNewSave.getPiId()).getMode().getId();
						if(mode == 12) {
							//给负责人添加默认考核模板关联的考核项
							HrPaAuthpbccitem hrPaAuthpbccitem = new HrPaAuthpbccitem();
							hrPaAuthpbccitem.setAuthorPbc(authorPbc);
							hrPaAuthpbccitem.setAkpiItem2uId(itemNewSave.getId());
							hrPaAuthpbccitem.setResult(new Double(0));
							hrPaAuthpbccitem.setWeight(new Double(0));
							//插入数据库
							hrPaAuthpbccitemService.save(hrPaAuthpbccitem);
						}
					}
				}
			}
		}
	}
}
