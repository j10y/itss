package com.xpsoft.oa.action.kpi;

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
import com.xpsoft.oa.model.kpi.HrPaAuthorizepbc;
import com.xpsoft.oa.model.kpi.HrPaAuthpbccitem;
import com.xpsoft.oa.model.kpi.HrPaKpiPBC2User;
import com.xpsoft.oa.model.kpi.HrPaPerformanceindexscore;
import com.xpsoft.oa.model.system.AppUser;
import com.xpsoft.oa.service.kpi.HrPaAuthorizepbcService;
import com.xpsoft.oa.service.kpi.HrPaAuthpbccitemService;

import flexjson.JSONSerializer;

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
	
	public String currentList() {
		AppUser currentUser = ContextUtil.getCurrentUser();
		Map<String, String> map = new HashMap<String, String>();
		map.put("Q_authorTo.userId_L_EQ", String.valueOf(currentUser.getUserId()));
		QueryFilter filter = new QueryFilter(map);
		List<HrPaAuthorizepbc> list = this.hrPaAuthorizepbcService.getAll(filter);
		
		StringBuffer buff = new StringBuffer("{success:true,'totalCounts':")
				.append(filter.getPagingBean().getTotalItems()).append(",result:");
		JSONSerializer json = new JSONSerializer();
		buff.append(json.exclude(new String[] {}).serialize(list));
		buff.append("}");
		this.jsonString = buff.toString();
		
		return "success";
	}
	
	public String get(){
		return "success";
	}
	
	@SuppressWarnings("unchecked")
	public String preview() {
		this.hrPaAuthorizepbc = this.hrPaAuthorizepbcService.get(this.id);
		//拼装SQL语句取出id, weight和关联的paName
		String sql = "select a.id, a.weight, c.id as piId, c.paName from hr_pa_authpbcitem a, hr_pa_kpiitem2user b, " +
				"hr_pa_performanceindex c where a.apbcId = " + this.id + " and a.akpiItem2uId = b.id and b.piId = c.id";
		Map<Map<String, Object>, List<Map<String, Object>>> itemMap = new HashMap<Map<String,Object>, List<Map<String,Object>>>();
		List<Map<String, Object>> authorItemList = this.hrPaAuthorizepbcService.findDataList(sql);
		for(int i = 0; i < authorItemList.size(); i++) {
			String sql2 = "select id, pisScore, pisDesc from hr_pa_performanceindexscore where piId = " + 
					authorItemList.get(i).get("piId").toString();
			List<Map<String, Object>> pisList = this.hrPaAuthorizepbcService.findDataList(sql2);
			itemMap.put(authorItemList.get(i), pisList);
		}
		
		this.getRequest().setAttribute("itemMap", itemMap);
		
		return "show";
	}
	
	@SuppressWarnings("unchecked")
	public String gridScore() {
		try {
			//取得pbc
			String pbcId = this.getRequest().getParameter("pbcId");
			this.hrPaAuthorizepbc = this.hrPaAuthorizepbcService.get(Long.parseLong(pbcId));
			//取得pbc关联的考核项
			Map<String, String> map = new HashMap<String, String>();
			map.put("Q_authorPbc.id_L_EQ", pbcId);
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
			hrPaAuthpbccitemService.multiSave(itemList);
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
			//判断原考核指标信息有没有被更改，如果呗更改则覆盖
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
			hrPaAuthpbccitemService.multiSave(authpbcItemList);
		}
		
		this.jsonString = new String("{success:true}");
		
		return "success";
	}
}
