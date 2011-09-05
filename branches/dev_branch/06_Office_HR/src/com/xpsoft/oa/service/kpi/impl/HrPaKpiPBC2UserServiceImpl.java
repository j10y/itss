package com.xpsoft.oa.service.kpi.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.xpsoft.core.command.QueryFilter;
import com.xpsoft.core.service.impl.BaseServiceImpl;
import com.xpsoft.core.util.AppUtil;
import com.xpsoft.core.util.ContextUtil;
import com.xpsoft.oa.dao.kpi.HrPaKpiPBC2UserDao;
import com.xpsoft.oa.model.kpi.HrPaKpiPBC2User;
import com.xpsoft.oa.model.kpi.HrPaKpiitem2user;
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
	 * 计算个人PBC最终得分
	 * @param pbcIds
	 * PBC ID
	 * */
	public String calTotalScore(Long pbcId) {
		HrPaKpiitem2userService hrPaKpiitem2userService = (HrPaKpiitem2userService)AppUtil.getBean("hrPaKpiitem2userService");
		HrPaKpiPBC2UserCmpService hrPaKpiPBC2UserCmpService = (HrPaKpiPBC2UserCmpService)AppUtil.getBean("hrPaKpiPBC2UserCmpService");
		//计算关联定量考核项分数
		hrPaKpiPBC2UserCmpService.saveKpiItemScoreForUser(ContextUtil.getCurrentUserId().toString(), null, pbcId.toString());
		//计算关联定性考核项考核项平均分
		hrPaKpiitem2userService.multiCal(pbcId);
		//计算总分
		return hrPaKpiPBC2UserCmpService.countScoreForKpiPbcUser(pbcId);
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
