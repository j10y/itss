package com.xpsoft.oa.service.kpi.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.xpsoft.core.command.QueryFilter;
import com.xpsoft.core.service.impl.BaseServiceImpl;
import com.xpsoft.core.util.AppUtil;
import com.xpsoft.oa.dao.kpi.HrPaKpiPBC2UserDao;
import com.xpsoft.oa.model.kpi.HrPaAuthorizepbc;
import com.xpsoft.oa.model.kpi.HrPaKpiPBC2User;
import com.xpsoft.oa.model.kpi.HrPaKpiitem2user;
import com.xpsoft.oa.service.kpi.HrPaAuthorizepbcService;
import com.xpsoft.oa.service.kpi.HrPaKpiPBC2UserCmpService;
import com.xpsoft.oa.service.kpi.HrPaKpiPBC2UserService;
import com.xpsoft.oa.service.kpi.HrPaKpiitem2userService;

public class HrPaKpiPBC2UserServiceImpl extends BaseServiceImpl<HrPaKpiPBC2User>
		implements HrPaKpiPBC2UserService {
	private HrPaKpiPBC2UserDao dao;
	
	public HrPaKpiPBC2UserServiceImpl(HrPaKpiPBC2UserDao dao) {
		super(dao);
		this.dao = dao;
	}
	/*
	 * 计算个人考核模板最终得分
	 * @param pbcId
	 * 个人考核模板ID
	 * */
	/*
	@SuppressWarnings("unchecked")
	public void calculateTotal(Long pbcId) {
		//取得该考核模板关联的所有考核项
		String sql = "select id, result, weight from hr_pa_kpiitem2user where pbcId = " + pbcId;
		List<Map<String, Object>> list = this.findDataList(sql);
		Double total = new Double(0);
		for(int i = 0; i < list.size(); i++) {
			Map<String, Object> map = list.get(i);
			if(Double.parseDouble((String)map.get("result")) != 0) {//已计算平均值，进入计算
				total += Double.parseDouble((String)map.get("result")) * Double.parseDouble((String)map.get("weight"));
			}
		}
		//取得个人考核模板，并保存结果
		HrPaKpiPBC2User userPbc = this.get(pbcId);
		userPbc.setTotalScore(new Float(total));
		//插入数据库
		this.save(userPbc);
	}
	*/
	
	/*
	 * 批量计算个人考核模板最终得分
	 * @param pbcIds
	 * 个人考核模板ID数组
	 * */
	public void multiCal(String pbcIds) {
		HrPaKpiitem2userService hrPaKpiitem2userService = (HrPaKpiitem2userService)AppUtil.getBean("hrPaKpiitem2userService");
		HrPaAuthorizepbcService hrPaAuthorizepbcService = (HrPaAuthorizepbcService)AppUtil.getBean("hrPaAuthorizepbcService");
		HrPaKpiPBC2UserCmpService hrPaKpiPBC2UserCmpService = (HrPaKpiPBC2UserCmpService)AppUtil.getBean("hrPaKpiPBC2UserCmpService");
		String[] pbcIdArray = pbcIds.trim().split(",");
		//计算关联定性考核项考核项平均分
		for(int i = 0; i < pbcIdArray.length; i++) {
			long pbcId = Long.parseLong(pbcIdArray[i]);
			hrPaKpiitem2userService.multiCal(pbcId);
			//删除关联的授权考核模板
			Map<String, String> map = new HashMap<String, String>();
			map.put("Q_userPbc.id_L_EQ", String.valueOf(pbcId));
			QueryFilter filter = new QueryFilter(map);
			List<HrPaAuthorizepbc> list = hrPaAuthorizepbcService.getAll(filter);
			for(int j = 0; j < list.size(); j++) {
				hrPaAuthorizepbcService.remove(list.get(j));
			}
		}
		//计算总分
		hrPaKpiPBC2UserCmpService.countScoreForKpiPbcUser(pbcIds);
	}
	/*
	 * 获取所有定性考核指标未完成打分的记录
	 * @param depId
	 * 部门ID
	 * */
	@SuppressWarnings("unchecked")
	public List<String> getAllUnfinished(Long depId) {
		HrPaKpiitem2userService hrPaKpiitem2userService = (HrPaKpiitem2userService)AppUtil.getBean("hrPaKpiitem2userService");
		List<String> result = new ArrayList<String>();
		//获取该部门所有人的个人考核模板ID
		String sql = "select a.id from hr_pa_kpipbc2user a, app_user b, emp_profile c where " +
				"c.depId = " + depId + " and c.userId = b.userId and b.userId = a.belongUser";
		List<Map<String, Object>> userPbcIdList = this.findDataList(sql);
		//循环对每个个人考核模板查找未完成打分的记录
		for(int i = 0; i < userPbcIdList.size(); i++) {
			String userPbcId = userPbcIdList.get(i).get("id").toString();
			//获取个人考核模板关联的考核项
			Map<String, String> map = new HashMap<String, String>();
			map.put("Q_pbc2User.id_L_EQ", userPbcId);
			QueryFilter filter = new QueryFilter(map);
			List<HrPaKpiitem2user> kpiItem2userList = hrPaKpiitem2userService.getAll(filter);
			//查找考核项关联的所有授权打分记录，判断是否都已经打分
			for(int j = 0; j < kpiItem2userList.size(); j++) {
				String sql2 = "select id from hr_pa_authpbcitem where akpiItem2uId = " + kpiItem2userList.get(j).getId() + 
						" and result = 0";
				List<Map<String, Object>> authpbcItemList = this.findDataList(sql2);
				for(int o = 0; o < authpbcItemList.size(); o++) {
					result.add(authpbcItemList.get(o).get("id").toString());
				}
			}
		}
		return result;
	}
}
