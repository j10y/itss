package com.xpsoft.oa.action.kpi;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections.map.LinkedMap;

import com.xpsoft.core.command.QueryFilter;
import com.xpsoft.core.util.AppUtil;
import com.xpsoft.core.util.ContextUtil;
import com.xpsoft.core.util.JsonUtil;
import com.xpsoft.core.web.action.BaseAction;
import com.xpsoft.oa.model.kpi.HrPaDatadictionary;
import com.xpsoft.oa.model.kpi.HrPaKpiPBC2User;
import com.xpsoft.oa.model.kpi.HrPaKpiPBC2UserAuditHis;
import com.xpsoft.oa.model.kpi.HrPaKpiPBC2UserCmp;
import com.xpsoft.oa.model.kpi.HrPaPerformanceindexscore;
import com.xpsoft.oa.model.system.AppUser;
import com.xpsoft.oa.service.kpi.HrPaKpiPBC2UserAuditHisService;
import com.xpsoft.oa.service.kpi.HrPaKpiPBC2UserCmpService;
import com.xpsoft.oa.service.kpi.HrPaKpiPBC2UserService;
import com.xpsoft.oa.service.kpi.HrPaKpiitem2userService;
import com.xpsoft.oa.service.kpi.HrPaPerformanceindexscoreService;

import flexjson.JSONSerializer;

public class HrPaKpiPBC2UserAuditHisAction extends BaseAction {

	@Resource
	private HrPaKpiPBC2UserAuditHisService hrPaKpiPBC2UserAuditHisService;
	
	private HrPaKpiPBC2UserAuditHis hrPaKpiPBC2UserAuditHis;
	
	@Resource
	private HrPaKpiPBC2UserService hrPaKpiPBC2UserService;
	
	@Resource
	private HrPaKpiPBC2UserCmpService hrPaKpiPBC2UserCmpService;
	
	private Long id;

	public Long getId() {
		/* 35 */return this.id;
	}

	public void setId(Long id) {
		/* 39 */this.id = id;
	}

	public HrPaKpiPBC2UserAuditHis getHrPaKpiPBC2UserAuditHis() {
		/* 43 */return this.hrPaKpiPBC2UserAuditHis;
	}

	public void setHrPaKpiPBC2UserAuditHis(HrPaKpiPBC2UserAuditHis hrPaKpiPBC2UserAuditHis) {
		/* 47 */this.hrPaKpiPBC2UserAuditHis = hrPaKpiPBC2UserAuditHis;
	}

	public String list() {
		QueryFilter filter = new QueryFilter(getRequest());
		List list = this.hrPaKpiPBC2UserAuditHisService.getAll(filter);

		StringBuffer buff = new StringBuffer(
			"{success:true,'totalCounts':")
			.append(filter.getPagingBean().getTotalItems()).append(
			",result:");

//		JSONSerializer serializer = new JSONSerializer();
		JSONSerializer serializer = JsonUtil
		.getJSONSerializer(new String[] { "checkTime"});
		buff.append(serializer.exclude(new String[] { "class" })
			.serialize(list));
		buff.append("}");

		this.jsonString = buff.toString();	

		return "success";
	}
	
//
//	public String combo() {
//		QueryFilter filter = new QueryFilter(getRequest());
//		List<HrPaKpiPBC2UserAuditHis> list = this.hrPaKpiPBC2UserAuditHisService.getAll(filter);
//		StringBuffer sb = new StringBuffer("[");
//		for (HrPaKpiPBC2UserAuditHis hrPaKpiPBC2UserAuditHis : list) {
//			sb.append("['").append(hrPaKpiPBC2UserAuditHis.getId()).append("','")
//					.append(hrPaKpiPBC2UserAuditHis.getName()).append("'],");
//		}
//		if (list.size() > 0) {
//			sb.deleteCharAt(sb.length() - 1);
//		}
//		sb.append("]");
//		setJsonString(sb.toString());
//		return "success";
//	}

	public String multiDel() {
		String[] ids = getRequest().getParameterValues("ids");
		if (ids != null) {
			for (String id : ids) {
				//this.hrPaKpiPBC2UserAuditHisService.remove(new Long(id));
				HrPaKpiPBC2UserAuditHis hrPaKpiPBC2UserAuditHis =  this.hrPaKpiPBC2UserAuditHisService.get(new Long(id));
				this.hrPaKpiPBC2UserAuditHisService.save(hrPaKpiPBC2UserAuditHis);
			}
		}

		/* 103 */this.jsonString = "{success:true}";

		/* 105 */return "success";
	}

	public String get() {
		HrPaKpiPBC2UserAuditHis hrPaKpiPBC2UserAuditHis = (HrPaKpiPBC2UserAuditHis) this.hrPaKpiPBC2UserAuditHisService.get(this.id);

		StringBuffer sb = new StringBuffer("{success:true,totalCounts:1,data:[");

		//JSONSerializer serializer = new JSONSerializer();
		JSONSerializer serializer = JsonUtil
		.getJSONSerializer(new String[] { "checkTime"});
		sb.append(serializer.exclude(
						new String[] { "class", "department.class" })
						.serialize(hrPaKpiPBC2UserAuditHis));
		sb.append("]}");
		setJsonString(sb.toString());
		return "success";
	}

	public String save() {
		boolean isNew=false;
		AppUser user = ContextUtil.getCurrentUser();
		this.hrPaKpiPBC2UserAuditHis.setCheckUser(user);//审批人
		this.hrPaKpiPBC2UserAuditHis.setCheckTime(new Date());
		HrPaKpiPBC2User hrPaKpiPBC2User = new HrPaKpiPBC2User();
		hrPaKpiPBC2User.setId(Long.valueOf(getRequest().getParameter("hrPaKpiPBC2User")));
		this.hrPaKpiPBC2UserAuditHis.setHrPaKpiPBC2UserId(hrPaKpiPBC2User.getId());
		this.hrPaKpiPBC2UserAuditHisService.save(this.hrPaKpiPBC2UserAuditHis);
		setJsonString("{success:true,hrPaKpiPBC2UserAuditHisId:'" + hrPaKpiPBC2UserAuditHis.getId() + "'}");
		return "success";
	}
	
	public String check() {
		
		boolean isNew=false;
		AppUser user = ContextUtil.getCurrentUser();
		this.hrPaKpiPBC2UserAuditHis.setCheckUser(user);//审批人
		this.hrPaKpiPBC2UserAuditHis.setCheckTime(new Date());
		
		HrPaKpiPBC2User hrPaKpiPBC2User = this.hrPaKpiPBC2UserService.get(Long.valueOf(getRequest().getParameter("hrPaKpiPBC2UserId")));
		/**
		 *  HrPaKpiPBC2User发布状态
            0：草稿
            1：审核中
            2：退回
            3：审核完毕，发布
            4：删除标记
		 */
		if(this.hrPaKpiPBC2UserAuditHis.getCheckStatus().intValue()==1){//HrPaKpiPBC2UserAuditHis.chechStatus 0拒绝 1通过
			hrPaKpiPBC2User.setPublishStatus(3);//通过
			//绩效导出
			HrPaKpiPBC2UserCmpService hrPaKpiPBC2UserCmpService = (HrPaKpiPBC2UserCmpService)AppUtil.getBean("hrPaKpiPBC2UserCmpService");
			hrPaKpiPBC2UserCmpService.saveHrPaKpiPBC2UserCmp(hrPaKpiPBC2User.getId());
		}else{
			hrPaKpiPBC2User.setPublishStatus(2);//退回
			this.hrPaKpiPBC2UserService.save(hrPaKpiPBC2User);
		}	
		this.hrPaKpiPBC2UserAuditHis.setHrPaKpiPBC2UserId(Long.valueOf(getRequest().getParameter("hrPaKpiPBC2UserId")));
		this.hrPaKpiPBC2UserAuditHisService.save(this.hrPaKpiPBC2UserAuditHis);
		setJsonString("{success:true}");
		return "success";
	}
	
	/**
	 * 获取历史信息
	 * Q_HrPaKpiPBC2User.id_L_EQ=
	 * @return
	 */
	public String listHis() {
		HrPaPerformanceindexscoreService hrPaPerformanceindexscoreService = (HrPaPerformanceindexscoreService)AppUtil.getBean("hrPaPerformanceindexscoreService");
		String hrPaKpiPBC2UserId = getRequest().getParameter("hrPaKpiPBC2UserId");
		HrPaKpiPBC2UserCmp hrPaKpiPBC2User = this.hrPaKpiPBC2UserCmpService.get(Long.valueOf(hrPaKpiPBC2UserId));//完成
		
		Map filterMap = new HashMap();
		filterMap.put("Q_hrPaKpiPBC2UserId_L_EQ", hrPaKpiPBC2UserId);
		QueryFilter filter = new QueryFilter(filterMap);
		List<HrPaKpiPBC2UserAuditHis> list = this.hrPaKpiPBC2UserAuditHisService.getAll(filter);
		
		String listHis = "<table class=\"table-info\" cellpadding=\"0\" cellspacing=\"1\" width=\"98%\" align=\"center\">";
		listHis += "<tr><td>审批人</td><td>审批结果</td><td>审批意见</td><td>审批时间</td></tr>";
		for(int j = 0; j < list.size(); j++) {
			listHis += "<tr><td>" + list.get(j).getCheckUser().getFullname() + "</td><td>" + (list.get(j).getCheckStatus().intValue()==1?"同意":"拒绝") + "</td><td>" + 
			list.get(j).getCheckRemark() + "</td><td>" + 
			com.xpsoft.core.util.DateUtil.formatDateTimeToString(list.get(j).getCheckTime()) + "</td></tr>";
		}
		listHis +="</table>";
		getRequest().setAttribute("listHis", listHis);
		
		/*
		HrPaKpiitem2userService hrPaKpiitem2userService = (HrPaKpiitem2userService)AppUtil.getBean("hrPaKpiitem2userService");
		String sql = "select b.paName, a.result, a.weight from hr_pa_kpiitem2userCmp a, hr_pa_performanceindex b where " +
		"a.pbcId = " + hrPaKpiPBC2User.getId() + " and a.piId = b.id";
		List<Map<String, Object>> list3 = hrPaKpiitem2userService.findDataList(sql);
		String content = "<table class=\"table-info\" cellpadding=\"0\" cellspacing=\"1\" width=\"98%\" align=\"center\">";
		content += "<tr><td>考核指标名称</td><td>得分</td><td>权重</td></tr>";
		for(int j = 0; j < list3.size(); j++) {
			content += "<tr><td>" + list3.get(j).get("paName") + "</td><td>" + list3.get(j).get("result") + "</td><td>" + 
					list3.get(j).get("weight") + "</td></tr>";
		}
		content +="</table>";
		
		getRequest().setAttribute("content", content);
		getRequest().setAttribute("totalScore", hrPaKpiPBC2User.getTotalScore());
		getRequest().setAttribute("pbcName", hrPaKpiPBC2User.getPbcName());
		getRequest().setAttribute("user", hrPaKpiPBC2User.getBelongUser().getFullname());
		*/
		
		//取得PBC基础信息
		String sql = "select depName, position, depName, standardName from emp_profile where userId = " + hrPaKpiPBC2User.getBelongUser().getUserId();
		List<Map<String, Object>> mapList = this.hrPaKpiPBC2UserService.findDataList(sql);
		if(mapList.size() > 0) {
			this.getRequest().setAttribute("fullname", hrPaKpiPBC2User.getBelongUser().getFullname());
			this.getRequest().setAttribute("position", mapList.get(0).get("position"));
			this.getRequest().setAttribute("depName", mapList.get(0).get("depName"));
			String[] bandGrade = mapList.get(0).get("standardName").toString().trim().split("_");
			if(bandGrade.length == 3) {
				this.getRequest().setAttribute("bandGrade", "Band：" + bandGrade[1] + "档：" + bandGrade[2]);
			}
			this.getRequest().setAttribute("lineManager", hrPaKpiPBC2User.getLineManager().getFullname());
			this.getRequest().setAttribute("createDate", hrPaKpiPBC2User.getCreateDate());
			this.getRequest().setAttribute("pbcResult", hrPaKpiPBC2User.getTotalScore());
		}
		//取得业务目标考核项信息
		String sql2 = "select a.id, a.piId, b.paName, b.paDesc, a.result, a.weight, a.remark from hr_pa_kpiitem2usercmp a, hr_pa_performanceindex b where " +
		"a.pbcId = " + hrPaKpiPBC2User.getId() + " and a.piId = b.id and b.paType = " + HrPaDatadictionary.BUSSINESS_GOAL;
		List<Map<String, Object>> mapList2 = this.hrPaKpiPBC2UserService.findDataList(sql2);
		Double busResult = 0d;
		if(mapList2.size() > 0) {
			for(int i = 0; i < mapList2.size(); i++) {
				busResult += Double.parseDouble(mapList2.get(i).get("result").toString()) * 
						Double.parseDouble(mapList2.get(i).get("weight").toString());
				Map<String, String> map2 = new HashMap<String, String>();
				map2.put("Q_pi.id_L_EQ", mapList2.get(i).get("piId").toString());
				QueryFilter filter2 = new QueryFilter(map2);
				List<HrPaPerformanceindexscore> list2 = hrPaPerformanceindexscoreService.getAll(filter2);
				Map<String, String> pisMap2 = new LinkedMap();
				for(int j = 0; j < list2.size(); j++) {
					pisMap2.put(list2.get(j).getPisScore().toString(), list2.get(j).getPisDesc());
				}
				mapList2.get(i).put("busPisMap", pisMap2);
			}
		}
		this.getRequest().setAttribute("busGoalList", mapList2);
		this.getRequest().setAttribute("busResult", busResult);
		//取得员工管理目标考核项信息
		String sql3 = "select a.id, a.piId, b.paName, b.paDesc, a.result, a.weight, a.remark from hr_pa_kpiitem2usercmp a, hr_pa_performanceindex b where " +
		"a.pbcId = " + hrPaKpiPBC2User.getId() + " and a.piId = b.id and b.paType = " + HrPaDatadictionary.PEOPLE_MANAGEMENT_GOAL;
		List<Map<String, Object>> mapList3 = this.hrPaKpiPBC2UserService.findDataList(sql3);
		Double pmResult = 0d;
		if(mapList3.size() > 0) {
			for(int i = 0; i < mapList3.size(); i++) {
				pmResult += Double.parseDouble(mapList3.get(i).get("result").toString()) * 
						Double.parseDouble(mapList3.get(i).get("weight").toString());
				Map<String, String> map3 = new HashMap<String, String>();
				map3.put("Q_pi.id_L_EQ", mapList3.get(i).get("piId").toString());
				QueryFilter filter3 = new QueryFilter(map3);
				List<HrPaPerformanceindexscore> list3 = hrPaPerformanceindexscoreService.getAll(filter3);
				Map<String, String> pisMap3 = new LinkedMap();
				for(int j = 0; j < list3.size(); j++) {
					pisMap3.put(list3.get(j).getPisScore().toString(), list3.get(j).getPisDesc());
				}
				mapList3.get(i).put("pmPisMap", pisMap3);
			}
		}
		this.getRequest().setAttribute("pmGoalList", mapList3);
		this.getRequest().setAttribute("pmResult", pmResult);
		//取得个人发展目标考核项信息
		String sql4 = "select a.id, a.piId, b.paName, b.paDesc, a.result, a.weight, a.remark from hr_pa_kpiitem2usercmp a, hr_pa_performanceindex b where " +
		"a.pbcId = " + hrPaKpiPBC2User.getId() + " and a.piId = b.id and b.paType = " + HrPaDatadictionary.INDIVIDUAL_DEVELOPMENT_GOAL;
		List<Map<String, Object>> mapList4 = this.hrPaKpiPBC2UserService.findDataList(sql4);
		Double idResult = 0d;
		if(mapList4.size() > 0) {
			for(int i = 0; i < mapList4.size(); i++) {
				idResult += Double.parseDouble(mapList4.get(i).get("result").toString()) * 
						Double.parseDouble(mapList4.get(i).get("weight").toString());
				Map<String, String> map4 = new HashMap<String, String>();
				map4.put("Q_pi.id_L_EQ", mapList4.get(i).get("piId").toString());
				QueryFilter filter4 = new QueryFilter(map4);
				List<HrPaPerformanceindexscore> list4 = hrPaPerformanceindexscoreService.getAll(filter4);
				Map<String, String> pisMap4 = new LinkedMap();
				for(int j = 0; j < list4.size(); j++) {
					pisMap4.put(list4.get(j).getPisScore().toString(), list4.get(j).getPisDesc());
				}
				mapList4.get(i).put("idPisMap", pisMap4);
			}
		}
		this.getRequest().setAttribute("idGoalList", mapList4);
		this.getRequest().setAttribute("idResult", idResult);
		
		return "listHis";
	}
}
