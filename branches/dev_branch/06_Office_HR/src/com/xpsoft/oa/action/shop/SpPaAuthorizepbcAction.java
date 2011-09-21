package com.xpsoft.oa.action.shop;

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
import com.xpsoft.core.util.DateUtil;
import com.xpsoft.core.web.action.BaseAction;
import com.xpsoft.oa.model.shop.SpPaAuthorizepbc;
import com.xpsoft.oa.model.shop.SpPaAuthpbccitem;
import com.xpsoft.oa.model.shop.SpPaKpiPBC2User;
import com.xpsoft.oa.model.shop.SpPaKpiitem2user;
import com.xpsoft.oa.model.system.AppUser;
import com.xpsoft.oa.service.shop.SpPaAuthorizepbcService;
import com.xpsoft.oa.service.shop.SpPaAuthpbccitemService;
import com.xpsoft.oa.service.shop.SpPaKpiPBC2UserCmpService;
import com.xpsoft.oa.service.shop.SpPaKpiPBC2UserService;
import com.xpsoft.oa.service.shop.SpPaKpiitem2userService;

public class SpPaAuthorizepbcAction extends BaseAction{
	@Resource
	private SpPaAuthorizepbcService spPaAuthorizepbcService;
	private SpPaAuthorizepbc spPaAuthorizepbc;
	private long id;
	
	public SpPaAuthorizepbcService getSpPaAuthorizepbcService() {
		return spPaAuthorizepbcService;
	}
	public void setSpPaAuthorizepbcService(
			SpPaAuthorizepbcService spPaAuthorizepbcService) {
		this.spPaAuthorizepbcService = spPaAuthorizepbcService;
	}
	public SpPaAuthorizepbc getSpPaAuthorizepbc() {
		return spPaAuthorizepbc;
	}
	public void setSpPaAuthorizepbc(SpPaAuthorizepbc spPaAuthorizepbc) {
		this.spPaAuthorizepbc = spPaAuthorizepbc;
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
		//获取最新日期
		String sql6 = "select a.authDate from sp_pa_authorizepbc a order by a.authDate desc limit 1";
		List<Map<String, Object>> mapList6 = this.spPaAuthorizepbcService.findDataList(sql6);
		String newDate = "";
		if(mapList6.size() > 0) {
			newDate = mapList6.get(0).get("authDate").toString().trim().split(" ")[0];
		}
		String sql5 = "select count(*) as total from sp_pa_authorizepbc a, sp_pa_kpipbc2user b, emp_profile c where " + 
				"a.authDate >= '" + newDate + "' and a.userId = " + currentUser.getUserId() + " and a.pbcId = b.id " +
				"and b.publishStatus in (0, 2) and b.belongUser = c.userId";
		sql5 += (fullname == null || "".equals(fullname)) ? "" : " and c.fullname like '%" + fullname + "%'";
		List<Map<String, Object>> mapList5 = this.spPaAuthorizepbcService.findDataList(sql5);
		String sql3 = "select a.id, b.pbcName, c.fullname from sp_pa_authorizepbc a, sp_pa_kpipbc2user b, emp_profile c where " +
				"a.authDate >= '" + newDate + "' and a.userId = " + currentUser.getUserId() + " and a.pbcId = b.id " +
				"and b.publishStatus in (0, 2) and b.belongUser = c.userId";
		sql3 += (fullname == null || "".equals(fullname)) ? "" : " and c.fullname like '%" + fullname + "%'";
		sql3 += " limit " + filter.getPagingBean().getStart() + ", " + filter.getPagingBean().getPageSize();
		List<Map<String, Object>> mapList3 = this.spPaAuthorizepbcService.findDataList(sql3);
		StringBuffer buff = new StringBuffer("{success:true,'totalCounts':'" + mapList5.get(0).get("total") + "',result:[");
		for(int i = 0; i < mapList3.size(); i++) {
			buff.append("{'id':'").append(mapList3.get(i).get("id").toString())
					.append("','fullname':'").append(mapList3.get(i).get("fullname").toString())
						.append("','pbcName':'").append(mapList3.get(i).get("pbcName")).append("'},");
		}
		this.jsonString = buff.toString();
		if(mapList3.size() > 0) {
			this.jsonString = this.jsonString.substring(0, this.jsonString.length() - 1);
		}
		this.jsonString += "]}";
		
		return "success";
	}
	
	public String listAll() {
		AppUser currentUser = ContextUtil.getCurrentUser();
		QueryFilter filter = new QueryFilter(this.getRequest());
		String fullname = this.getRequest().getParameter("fullname");
		//获取最新日期
		String sql5 = "select a.createDate from sp_pa_kpipbc2user a order by a.createDate desc limit 1";
		List<Map<String, Object>> mapList5 = this.spPaAuthorizepbcService.findDataList(sql5);
		String newDate = "";
		if(mapList5.size() > 0) {
			newDate = mapList5.get(0).get("createDate").toString().trim().split(" ")[0];
		}
		String sql4 = "select count(*) as total from sp_pa_kpipbc2user a, emp_profile b where " + 
				"a.createDate >= '" + newDate + "' and a.belongUser = b.userId and a.publishStatus in (0, 2) ";
		sql4 += (fullname == null || "".equals(fullname)) ? "" : " and b.fullname like '%" + fullname + "%'";
		List<Map<String, Object>> mapList4 = this.spPaAuthorizepbcService.findDataList(sql4);
		String sql2 = "select a.id, a.pbcName, b.fullname from sp_pa_kpipbc2user a, emp_profile b where " +
				"a.createDate >= '" + newDate + "' and a.belongUser = b.userId and a.publishStatus in (0, 2) ";
		sql2 += (fullname == null || "".equals(fullname)) ? "" : " and b.fullname like '%" + fullname + "%'";
		sql2 += " limit " + filter.getPagingBean().getStart() + ", " + filter.getPagingBean().getPageSize();
		List<Map<String, Object>> mapList2 = this.spPaAuthorizepbcService.findDataList(sql2);
		StringBuffer buff = new StringBuffer("{success:true,'totalCounts':'" + mapList4.get(0).get("total") + "',result:[");
		for(int j = 0; j < mapList2.size(); j++) {
			buff.append("{'id':'").append(mapList2.get(j).get("id").toString())
					.append("','fullname':'").append(mapList2.get(j).get("fullname").toString())
					.append("','pbcName':'").append(mapList2.get(j).get("pbcName")).append("'},");
		}
		this.jsonString = buff.toString();
		if(mapList2.size() > 0) {
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
		SpPaKpiPBC2UserCmpService spPaKpiPBC2UserCmpService = (SpPaKpiPBC2UserCmpService)AppUtil.getBean("spPaKpiPBC2UserCmpService");
		//拼装SQL语句取出id, weight和关联的paName
		String sql = "select a.id, a.weight, a.result, c.id as piId, c.paName, c.paMode from sp_pa_authpbcitem a, sp_pa_kpiitem2user b, " +
				"sp_pa_performanceindex c where a.apbcId = " + this.id + " and a.akpiItem2uId = b.id and b.piId = c.id";
		Map<Map<String, Object>, List<Map<String, Object>>> itemMap = new LinkedMap();
		List<Map<String, Object>> authorItemList = this.spPaAuthorizepbcService.findDataList(sql);
		for(int i = 0; i < authorItemList.size(); i++) {
			String sql2 = "select id, pisScore, pisDesc from sp_pa_performanceindexscore where piId = " + 
					authorItemList.get(i).get("piId").toString() + " order by pisScore";
			List<Map<String, Object>> pisList = this.spPaAuthorizepbcService.findDataList(sql2);
			itemMap.put(authorItemList.get(i), pisList);
		}
		this.getRequest().setAttribute("pbcId", this.id);
		this.getRequest().setAttribute("itemMap", itemMap);
		this.getRequest().setAttribute("isDeptUser", "false");
		
		return "show";
	}
	
	@SuppressWarnings("unchecked")
	public String previewAll() {
		AppUser currentUser = ContextUtil.getCurrentUser();
		SpPaKpiPBC2UserCmpService spPaKpiPBC2UserCmpService = (SpPaKpiPBC2UserCmpService)AppUtil.getBean("spPaKpiPBC2UserCmpService");
		String sql2 = "select a.id, a.weight, a.result, b.id as piId, b.paName, b.paMode from sp_pa_kpiitem2user a, sp_pa_performanceindex b where " +
				"a.pbcId = " + this.id + " and a.piId = b.id order by a.id";
		List<Map<String, Object>> mapList2 = this.spPaAuthorizepbcService.findDataList(sql2);
		Map<Map<String, Object>, List<Map<String, Object>>> itemMap = new LinkedMap();
		for(int i = 0; i < mapList2.size(); i++) {
			String sql3 = "select id, pisScore, pisDesc from sp_pa_performanceindexscore where piId = " + 
					mapList2.get(i).get("piId").toString() + " order by pisScore";
			List<Map<String, Object>> mapList3 = this.spPaAuthorizepbcService.findDataList(sql3);
			itemMap.put(mapList2.get(i), mapList3);
		}
		this.getRequest().setAttribute("pbcId", this.id);
		this.getRequest().setAttribute("itemMap", itemMap);
		this.getRequest().setAttribute("isDeptUser", "true");
		
		return "showTotal";
	}
	
	@SuppressWarnings("unchecked")
	public String gridScore() {
		AppUser currentUser = ContextUtil.getCurrentUser();
		SpPaKpiPBC2UserService spPaKpiPBC2UserService = (SpPaKpiPBC2UserService)AppUtil.getBean("spPaKpiPBC2UserService");
		SpPaKpiitem2userService spPaKpiitem2userService = (SpPaKpiitem2userService)AppUtil.getBean("spPaKpiitem2userService");
		try {
			//取得pbc
			String pbcId = this.getRequest().getParameter("pbcId");
			this.spPaAuthorizepbc = this.spPaAuthorizepbcService.get(Long.parseLong(pbcId));
			//取得pbc关联的考核项
			Map<String, String> map = new HashMap<String, String>();
			map.put("Q_authorPbc.id_L_EQ", pbcId);
			QueryFilter filter = new QueryFilter(map);
			SpPaAuthpbccitemService spPaAuthpbccitemService = (SpPaAuthpbccitemService)AppUtil.getBean("spPaAuthpbccitemService");
			List<SpPaAuthpbccitem> itemList = spPaAuthpbccitemService.getAll(filter);
			//填入打分结果
			for(int i = 0; i < itemList.size(); i++) {
				SpPaAuthpbccitem item = itemList.get(i);
				Double resultSubmit = Double.parseDouble(this.getRequest().getParameter(String.valueOf(item.getId())));
				itemList.get(i).setResult(resultSubmit);
			}
			//插入数据库
			spPaAuthpbccitemService.multiSave(itemList);
			//返回成功标记
			this.getRequest().setAttribute("flag", "1");
		} catch(Exception e) {
			e.printStackTrace();
			this.getRequest().setAttribute("flag", "0");
		}
		
		return "gradeResult";
	}
	
	@SuppressWarnings("unchecked")
	public String gridTotalScore() {
		AppUser currentUser = ContextUtil.getCurrentUser();
		SpPaKpiPBC2UserService spPaKpiPBC2UserService = (SpPaKpiPBC2UserService)AppUtil.getBean("spPaKpiPBC2UserService");
		SpPaKpiitem2userService spPaKpiitem2userService = (SpPaKpiitem2userService)AppUtil.getBean("spPaKpiitem2userService");
			try {
				String pbcId = this.getRequest().getParameter("pbcId");
				String[] calResult = spPaKpiPBC2UserService.calTotalScore(Long.parseLong(pbcId)).trim().split(",");
				String sql3 = "select a.id, a.weight, a.result, b.id as piId, b.paName, b.paMode from sp_pa_kpiitem2user a, sp_pa_performanceindex b where " +
						"a.pbcId = " + pbcId + " and a.piId = b.id order by a.id";
				List<Map<String, Object>> mapList3 = this.spPaAuthorizepbcService.findDataList(sql3);
				Map<Map<String, Object>, List<Map<String, Object>>> itemMap = new LinkedMap();
				for(int i = 0; i < mapList3.size(); i++) {
					String sql4 = "select id, pisScore, pisDesc from sp_pa_performanceindexscore where piId = " + 
							mapList3.get(i).get("piId").toString();
					List<Map<String, Object>> mapList4 = this.spPaAuthorizepbcService.findDataList(sql4);
					itemMap.put(mapList3.get(i), mapList4);
				}
				if("true".equals(calResult[0])) {
					this.getRequest().setAttribute("totalScore", calResult[1]);
				}
				this.getRequest().setAttribute("pbcId", pbcId);
				this.getRequest().setAttribute("itemMap", itemMap);
				this.getRequest().setAttribute("isDeptUser", "true");
				this.getRequest().setAttribute("forAudit", "true");
			} catch(Exception e) {
				e.printStackTrace();
				this.getRequest().setAttribute("flag", "0");
			}
		
			return "showTotal";
	}
	
	public String save() {
		Date currentDate = new Date();
		AppUser currentUser = ContextUtil.getCurrentUser();
		SpPaAuthpbccitemService spPaAuthpbccitemService = (SpPaAuthpbccitemService)AppUtil.getBean("spPaAuthpbccitemService");
		//获取表单信息
		long userId = Long.parseLong(this.getRequest().getParameter("userId"));
		long pbcId = Long.parseLong(this.getRequest().getParameter("pbcId"));
		String[] authorPbcItems = this.getRequest().getParameter("authorItems").trim().split(" ");
		//判断该userId的授权打分模板是否已存在
		Map<String, String> map = new HashMap<String, String>();
		map.put("Q_userPbc.id_L_EQ", String.valueOf(pbcId));
		map.put("Q_authorTo.id_L_EQ", String.valueOf(userId));
		QueryFilter filter = new QueryFilter(map);
		List<SpPaAuthorizepbc> list = this.spPaAuthorizepbcService.getAll(filter);
		//保存授权PBC基本信息
		SpPaAuthorizepbc authorPbc = new SpPaAuthorizepbc();
		AppUser authorTo = new AppUser(userId);
		SpPaKpiPBC2User userPbc = new SpPaKpiPBC2User();
		userPbc.setId(pbcId);
		authorPbc.setAuthorTo(authorTo);
		authorPbc.setUserPbc(userPbc);
		authorPbc.setAuthDate(currentDate);
		authorPbc.setAuthPerson(currentUser);
		//插入数据库
		SpPaAuthorizepbc authorPbcNew = this.spPaAuthorizepbcService.save(authorPbc);
		//保存授权PBC关联的考核指标信息
		List<SpPaAuthpbccitem> itemList = new ArrayList<SpPaAuthpbccitem>();
		for(int i = 0; i < authorPbcItems.length; i++) {
			String[] properties = authorPbcItems[i].trim().split(",");
			SpPaAuthpbccitem item = new SpPaAuthpbccitem();
			item.setAuthorPbc(authorPbcNew);
			item.setAkpiItem2uId(Long.parseLong(properties[0]));
			item.setWeight(new Double(0));
			item.setResult(new Double(0));//得分默认为0
			itemList.add(item);
		}
		//批量插入数据库
		spPaAuthpbccitemService.multiSave(itemList, pbcId);
		
		this.jsonString = new String("{success:true}");
		
		return "success";
	}
}
