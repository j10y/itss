package com.xpsoft.oa.action.kpi;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections.map.LinkedMap;

import com.xpsoft.core.command.QueryFilter;
import com.xpsoft.core.util.AppUtil;
import com.xpsoft.core.util.ContextUtil;
import com.xpsoft.core.web.action.BaseAction;
import com.xpsoft.oa.model.kpi.HrPaAuthorizepbc;
import com.xpsoft.oa.model.kpi.HrPaAuthpbccitem;
import com.xpsoft.oa.model.kpi.HrPaKpiPBC2User;
import com.xpsoft.oa.model.kpi.HrPaKpiitem2user;
import com.xpsoft.oa.model.system.AppUser;
import com.xpsoft.oa.service.kpi.HrPaAuthorizepbcService;
import com.xpsoft.oa.service.kpi.HrPaAuthpbccitemService;
import com.xpsoft.oa.service.kpi.HrPaKpiPBC2UserCmpService;
import com.xpsoft.oa.service.kpi.HrPaKpiPBC2UserService;
import com.xpsoft.oa.service.kpi.HrPaKpiitem2userService;

public class HrPaAuthorizepbcAction extends BaseAction{
	@Resource
	private HrPaAuthorizepbcService hrPaAuthorizepbcService;
	private HrPaAuthorizepbc hrPaAuthorizepbc;
	private long id;
	
	public HrPaAuthorizepbcService getHrPaAuthorizepbcService() {
		return hrPaAuthorizepbcService;
	}
	public void setHrPaAuthorizepbcService(
			HrPaAuthorizepbcService hrPaAuthorizepbcService) {
		this.hrPaAuthorizepbcService = hrPaAuthorizepbcService;
	}
	public HrPaAuthorizepbc getHrPaAuthorizepbc() {
		return hrPaAuthorizepbc;
	}
	public void setHrPaAuthorizepbc(HrPaAuthorizepbc hrPaAuthorizepbc) {
		this.hrPaAuthorizepbc = hrPaAuthorizepbc;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	
	public String list(){
		return "success";
	}
	
	@SuppressWarnings("unchecked")
	public String currentList() {
		AppUser currentUser = ContextUtil.getCurrentUser();
		QueryFilter filter = new QueryFilter(this.getRequest());
		String fullname = this.getRequest().getParameter("fullname");
		//判断当前用户是不是部门负责人
		String sql1 = "select depId from arch_rec_user where deptUserId = " + currentUser.getUserId();
		List<Map<String, Object>> mapList1 = this.hrPaAuthorizepbcService.findDataList(sql1);
		String depIds = "";
		if(mapList1.size() > 0) {
			for(int m = 0; m < mapList1.size() - 1; m++) {
				depIds += mapList1.get(m).get("depId").toString() + ",";
			}
			depIds += mapList1.get(mapList1.size() - 1).get("depId").toString();
		}
		String sql2 = "select * from (" +
				"SELECT DISTINCT count(*) as total FROM hr_pa_kpipbc2user LEFT JOIN hr_pa_authorizepbc ON " +
				"hr_pa_kpipbc2user.id = hr_pa_authorizepbc.pbcid, app_user WHERE " +
				"hr_pa_kpipbc2user.belongUser = app_user.userId  and hr_pa_authorizepbc.userid = " + currentUser.getUserId() + 
				" union SELECT DISTINCT count(*) as total FROM hr_pa_kpipbc2user , app_user WHERE hr_pa_kpipbc2user.belongUser = app_user.userId  ";
		sql2 += ("".equals(depIds)) ? " and app_user.depId in(0)) a" : "and app_user.depId in (" + depIds + ")) a";
		List<Map<String, Object>> mapList2 = this.hrPaAuthorizepbcService.findDataList(sql2);
		//int total = Integer.parseInt(mapList2.get(0).get("total").toString()) + Integer.parseInt(mapList2.get(1).get("total").toString());
		String sql3 = "select * from (" +
				"SELECT DISTINCT hr_pa_kpipbc2user.id as pbcId, hr_pa_authorizepbc.id as authId, " +
				"hr_pa_kpipbc2user.pbcName, app_user.fullname FROM hr_pa_kpipbc2user LEFT JOIN hr_pa_authorizepbc ON " +
				"hr_pa_kpipbc2user.id = hr_pa_authorizepbc.pbcid, app_user WHERE " +
				"hr_pa_kpipbc2user.belongUser = app_user.userId  and hr_pa_authorizepbc.userid = " + currentUser.getUserId() + 
				" union SELECT DISTINCT hr_pa_kpipbc2user.id as pbcId, '0' as authId, hr_pa_kpipbc2user.pbcName, " +
				"app_user.fullname FROM hr_pa_kpipbc2user , app_user WHERE hr_pa_kpipbc2user.belongUser = app_user.userId  ";
		sql3 += ("".equals(depIds)) ? " and app_user.depId in(0)) a" : "and app_user.depId in (" + depIds + ")) a";
		sql3+= (fullname == null || "".equals(fullname)) ? "" : " where a.fullname like '%" + fullname + "%'";
		sql3 += " limit " + filter.getPagingBean().getStart() + ", " + filter.getPagingBean().getPageSize();
		List<Map<String, Object>> mapList3 = this.hrPaAuthorizepbcService.findDataList(sql3);
		StringBuffer buff = new StringBuffer("{success:true,'totalCounts':'" + mapList2.get(0).get("total") + "',result:[");
		for(int j = 0; j < mapList3.size(); j++) {
			buff.append("{'authId':'").append(mapList3.get(j).get("authId") == null ? "0" : mapList3.get(j).get("authId").toString())
					.append("','pbcId':'").append(mapList3.get(j).get("pbcId").toString())
					.append("','fullname':'").append(mapList3.get(j).get("fullname").toString())
					.append("','pbcName':'").append(mapList3.get(j).get("pbcName")).append("'},");
		}
		this.jsonString = buff.toString();
		if(mapList3.size() > 0) {
			this.jsonString = this.jsonString.substring(0, this.jsonString.length() - 1);
		}
		this.jsonString += "]}";
		
		return "success";
	}
	
	public String get(){
		return "success";
	}
	
	@SuppressWarnings("unchecked")
	public String preview() {
		AppUser currentUser = ContextUtil.getCurrentUser();
		HrPaKpiPBC2UserCmpService hrPaKpiPBC2UserCmpService = (HrPaKpiPBC2UserCmpService)AppUtil.getBean("hrPaKpiPBC2UserCmpService");
		String authId = this.getRequest().getParameter("authId");
		String pbcId = this.getRequest().getParameter("pbcId");
		if("0".equals(authId)) {
			String sql2 = "select a.id, a.weight, a.result, b.id as piId, b.paName, b.paMode from hr_pa_kpiitem2user a, hr_pa_performanceindex b where " +
					"a.pbcId = " + pbcId + " and a.piId = b.id order by a.id";
			List<Map<String, Object>> mapList2 = this.hrPaAuthorizepbcService.findDataList(sql2);
			Map<Map<String, Object>, List<Map<String, Object>>> itemMap = new LinkedMap();
			for(int i = 0; i < mapList2.size(); i++) {
				//判断是否门店打分考核项
				String sql4 = "select id from sp_pa_kpipbc2user where fromPi = " + mapList2.get(i).get("id");
				List<Map<String, Object>> mapList4 = this.hrPaAuthorizepbcService.findDataList(sql4);
				if(mapList4.size() > 0) {//是门店打分考核项
					mapList2.get(i).put("isShop", "true");
				} else {//不是门店打分考核项
					mapList2.get(i).put("isShop", "false");
				}
				String sql3 = "select id, pisScore, pisDesc from hr_pa_performanceindexscore where piId = " + 
						mapList2.get(i).get("piId").toString() + " order by pisScore";
				List<Map<String, Object>> mapList3 = this.hrPaAuthorizepbcService.findDataList(sql3);
				itemMap.put(mapList2.get(i), mapList3);
			}
			//判断定量考核指标目标和达成数据是否已经导入
			String unfinished = "";
			List<Map> unfinishList = hrPaKpiPBC2UserCmpService.isKpiItemScoreForUser(currentUser.getUserId().toString(), null, this.id + "");
			if(unfinishList.size() > 0) {
				unfinished += "下列定量考核指标任务额度和达成数据未导入：\r\n";
				for(int j = 0; j < unfinishList.size() - 1; j++) {
					unfinished += unfinishList.get(j).get("desc") + "\r\n";
				}
				unfinished += unfinishList.get(unfinishList.size() - 1).get("desc");
			}
			//调用接口填充未导入数据
			this.getRequest().setAttribute("unfinished", unfinished);
			this.getRequest().setAttribute("pbcId", pbcId);
			this.getRequest().setAttribute("authId", authId);
			this.getRequest().setAttribute("itemMap", itemMap);
			this.getRequest().setAttribute("isDeptUser", "true");
		} else {
			//拼装SQL语句取出id, weight和关联的paName
			String sql = "select a.id, a.weight, a.result, c.id as piId, c.paName, c.paMode from hr_pa_authpbcitem a, hr_pa_kpiitem2user b, " +
					"hr_pa_performanceindex c where a.apbcId = " + authId + " and a.akpiItem2uId = b.id and b.piId = c.id";
			Map<Map<String, Object>, List<Map<String, Object>>> itemMap = new LinkedMap();
			List<Map<String, Object>> authorItemList = this.hrPaAuthorizepbcService.findDataList(sql);
			for(int i = 0; i < authorItemList.size(); i++) {
				String sql2 = "select id, pisScore, pisDesc from hr_pa_performanceindexscore where piId = " + 
						authorItemList.get(i).get("piId").toString() + " order by pisScore";
				List<Map<String, Object>> pisList = this.hrPaAuthorizepbcService.findDataList(sql2);
				itemMap.put(authorItemList.get(i), pisList);
			}
			this.getRequest().setAttribute("pbcId", pbcId);
			this.getRequest().setAttribute("authId", authId);
			this.getRequest().setAttribute("itemMap", itemMap);
			this.getRequest().setAttribute("isDeptUser", "false");
		}
		
		return "show";
	}
	
	@SuppressWarnings("unchecked")
	public String gridScore() {
		AppUser currentUser = ContextUtil.getCurrentUser();
		HrPaKpiPBC2UserService hrPaKpiPBC2UserService = (HrPaKpiPBC2UserService)AppUtil.getBean("hrPaKpiPBC2UserService");
		HrPaKpiitem2userService hrPaKpiitem2userService = (HrPaKpiitem2userService)AppUtil.getBean("hrPaKpiitem2userService");
		//取得pbc
		String authId = this.getRequest().getParameter("authId");
		String pbcId = this.getRequest().getParameter("pbcId");
		if("0".equals(authId)) {//当前用户是部门负责人
			try {
				//取得PBC关联的定性定性考核项且不是门店打分的考核项
				String sql2 = "select a.id from hr_pa_kpiitem2user a, hr_pa_kpipbc2user b, hr_pa_performanceindex c where " +
						"a.pbcId = b.id and a.piId = c.id and b.id = " + pbcId + " and c.paMode = 12 and not exists " +
						"(select d.fromPi from sp_pa_kpipbc2user d where a.id = d.fromPi)";
				List<Map<String, Object>> mapList2 = this.hrPaAuthorizepbcService.findDataList(sql2);
				List<HrPaKpiitem2user> itemList = new ArrayList<HrPaKpiitem2user>();
				for(int i = 0; i < mapList2.size(); i++) {
					HrPaKpiitem2user item = hrPaKpiitem2userService.get(Long.parseLong(mapList2.get(i).get("id").toString()));
					item.setResult(Double.parseDouble(this.getRequest().getParameter(String.valueOf(item.getId()))));
					itemList.add(item);
				}
				//插入数据库
				hrPaKpiitem2userService.multiSave(itemList);
				//判断是否计算最终结果
				if("true".equals(this.getRequest().getParameter("calTotal"))) {//计算最终结果，则进入计算该PBC总分步骤
					String[] calResult = hrPaKpiPBC2UserService.calTotalScore(Long.parseLong(pbcId)).trim().split(",");
					String sql3 = "select a.id, a.weight, a.result, b.id as piId, b.paName, b.paMode from hr_pa_kpiitem2user a, hr_pa_performanceindex b where " +
							"a.pbcId = " + pbcId + " and a.piId = b.id order by a.id";
					List<Map<String, Object>> mapList3 = this.hrPaAuthorizepbcService.findDataList(sql3);
					Map<Map<String, Object>, List<Map<String, Object>>> itemMap = new LinkedMap();
					for(int i = 0; i < mapList3.size(); i++) {
						String sql4 = "select id, pisScore, pisDesc from hr_pa_performanceindexscore where piId = " + 
								mapList3.get(i).get("piId").toString();
						List<Map<String, Object>> mapList4 = this.hrPaAuthorizepbcService.findDataList(sql4);
						itemMap.put(mapList3.get(i), mapList4);
					}
					if("true".equals(calResult[0])) {
						this.getRequest().setAttribute("totalScore", calResult[1]);
					}
					this.getRequest().setAttribute("pbcId", pbcId);
					this.getRequest().setAttribute("itemMap", itemMap);
					this.getRequest().setAttribute("isDeptUser", "true");
					this.getRequest().setAttribute("forAudit", "true");
					return "show";
				}
				//返回成功标记
				this.getRequest().setAttribute("flag", "1");
			} catch(Exception e) {
				e.printStackTrace();
				this.getRequest().setAttribute("flag", "0");
			}
		} else {//当前用户不是部门负责人
			try {
				this.hrPaAuthorizepbc = this.hrPaAuthorizepbcService.get(Long.parseLong(authId));
				//取得pbc关联的考核项
				Map<String, String> map = new HashMap<String, String>();
				map.put("Q_authorPbc.id_L_EQ", authId);
				QueryFilter filter = new QueryFilter(map);
				HrPaAuthpbccitemService hrPaAuthpbccitemService = (HrPaAuthpbccitemService)AppUtil.getBean("hrPaAuthpbccitemService");
				List<HrPaAuthpbccitem> itemList = hrPaAuthpbccitemService.getAll(filter);
				//填入打分结果
				for(int i = 0; i < itemList.size(); i++) {
					HrPaAuthpbccitem item = itemList.get(i);
					Double resultOld = item.getResult();
					Double resultSubmit = Double.parseDouble(this.getRequest().getParameter(String.valueOf(item.getId())));
					Double resultNew = 0.0;
					//判断是否多次打分
					if(resultOld == 0) {
						resultNew = resultSubmit;
					} else {
						resultNew = (resultOld + resultSubmit) / 2;
					}
					itemList.get(i).setResult(resultNew);
				}
				//插入数据库
				hrPaAuthpbccitemService.multiSave(itemList);
				//返回成功标记
				this.getRequest().setAttribute("flag", "1");
			} catch(Exception e) {
				e.printStackTrace();
				this.getRequest().setAttribute("flag", "0");
			}
		}
		
		return "gradeResult";
	}
	
	public String save() {
		Date currentDate = new Date();
		AppUser currentUser = ContextUtil.getCurrentUser();
		HrPaAuthpbccitemService hrPaAuthpbccitemService = (HrPaAuthpbccitemService)AppUtil.getBean("hrPaAuthpbccitemService");
		//获取表单信息
		long userId = Long.parseLong(this.getRequest().getParameter("userId"));
		long pbcId = Long.parseLong(this.getRequest().getParameter("pbcId"));
		String[] authorPbcItems = this.getRequest().getParameter("authorItems").trim().split(" ");
		//判断该userId的授权打分模板是否已存在
		Map<String, String> map = new HashMap<String, String>();
		map.put("Q_userPbc.id_L_EQ", String.valueOf(pbcId));
		map.put("Q_authorTo.id_L_EQ", String.valueOf(userId));
		QueryFilter filter = new QueryFilter(map);
		List<HrPaAuthorizepbc> list = this.hrPaAuthorizepbcService.getAll(filter);
		if(list.size() <= 0) {//不存在该userId的授权打分模板
			//保存授权PBC基本信息
			HrPaAuthorizepbc authorPbc = new HrPaAuthorizepbc();
			AppUser authorTo = new AppUser(userId);
			HrPaKpiPBC2User userPbc = new HrPaKpiPBC2User();
			userPbc.setId(pbcId);
			authorPbc.setAuthorTo(authorTo);
			authorPbc.setUserPbc(userPbc);
			authorPbc.setAuthDate(currentDate);
			authorPbc.setAuthPerson(currentUser);
			//插入数据库
			HrPaAuthorizepbc authorPbcNew = this.hrPaAuthorizepbcService.save(authorPbc);
			//保存授权PBC关联的考核指标信息
			List<HrPaAuthpbccitem> itemList = new ArrayList<HrPaAuthpbccitem>();
			for(int i = 0; i < authorPbcItems.length; i++) {
				String[] properties = authorPbcItems[i].trim().split(",");
				HrPaAuthpbccitem item = new HrPaAuthpbccitem();
				item.setAuthorPbc(authorPbcNew);
				item.setAkpiItem2uId(Long.parseLong(properties[0]));
				item.setWeight(Double.parseDouble(properties[1]));
				item.setResult(new Double(0));//得分默认为0
				itemList.add(item);
			}
			//批量插入数据库
			hrPaAuthpbccitemService.multiSave(itemList, pbcId);
		} else {//存在该userId的授权打分模板
			//保存授权PBC基本信息
			HrPaAuthorizepbc authorPbc = list.get(0);
			authorPbc.setAuthDate(currentDate);
			authorPbc.setAuthPerson(currentUser);
			//插入数据库
			HrPaAuthorizepbc authorPbcNew = this.hrPaAuthorizepbcService.save(authorPbc);
			//保存授权PBC关联的考核指标信息
			//取得原授权PBC关联的考核指标信息
			Map<String, String> map2 = new HashMap<String, String>();
			map2.put("Q_authorPbc.id_L_EQ", String.valueOf(authorPbc.getId()));
			QueryFilter filter2 = new QueryFilter(map2);
			List<HrPaAuthpbccitem> authpbcItemList = hrPaAuthpbccitemService.getAll(filter2);
			//判断原考核指标信息有没有被更改，如果被更改则覆盖
			for(int i = 0; i < authorPbcItems.length; i++) {
				boolean flag = false;
				String[] properties = authorPbcItems[i].trim().split(",");
				for(int j = 0; j < authpbcItemList.size(); j++) {
					HrPaAuthpbccitem item = authpbcItemList.get(j);
					if(Long.parseLong(properties[0]) == item.getAkpiItem2uId()) {//原考核指标信息列表中存在要插入的数据，则为更改
						authpbcItemList.get(j).setWeight(Double.parseDouble(properties[1]));//重新设置权重
						flag = true;
						break;
					}
				}
				//如果不存在要插入的信息，则插入
				if(!flag) {
					HrPaAuthpbccitem item = new HrPaAuthpbccitem();
					item.setAuthorPbc(authorPbcNew);
					item.setAkpiItem2uId(Long.parseLong(properties[0]));
					item.setWeight(Double.parseDouble(properties[1]));
					item.setResult(new Double(0));
					authpbcItemList.add(item);
				}
			}
			hrPaAuthpbccitemService.multiSave(authpbcItemList, pbcId);
		}
		
		this.jsonString = new String("{success:true}");
		
		return "success";
	}
}
