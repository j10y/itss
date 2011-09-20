package com.xpsoft.oa.service.kpi.impl;

import java.text.DecimalFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.xpsoft.core.command.QueryFilter;
import com.xpsoft.core.service.impl.BaseServiceImpl;
import com.xpsoft.core.util.AppUtil;
import com.xpsoft.core.util.DateUtil;
import com.xpsoft.oa.dao.kpi.HrPaKpiitem2userDao;
import com.xpsoft.oa.model.kpi.HrPaKpiitem2user;
import com.xpsoft.oa.service.kpi.HrPaAuthpbccitemService;
import com.xpsoft.oa.service.kpi.HrPaKpiitem2userService;

public class HrPaKpiitem2userServiceImpl extends BaseServiceImpl<HrPaKpiitem2user>
		implements HrPaKpiitem2userService {
	private HrPaKpiitem2userDao dao;
	
	public HrPaKpiitem2userServiceImpl(HrPaKpiitem2userDao dao) {
		super(dao);
		this.dao = dao;
	}
	/*
	 * 计算个人考核模板定性考核项的多人多次打分平均分数
	 * @param itemId
	 * 考核项ID
	 * */
	@SuppressWarnings("unchecked")
	public void calculateAvg(Long itemId) {
		DecimalFormat doubleFormat = new DecimalFormat();
		doubleFormat.applyPattern("###.00");
		//取得个人考核模板关联的该考核项，并与部门负责人打分合并，保存结果
		HrPaKpiitem2user item = this.get(itemId);
		Date fdOfMonth = DateUtil.getFirstDayOfMonth(new Date());
		//判断是否门店打分考核指标
		String sql3 = "select id, totalScore, fromPi from sp_pa_kpipbc2user where " +
				"createDate >= '" + DateUtil.convertDateToString(fdOfMonth) + "' and fromPi = " + itemId;
		List<Map<String, Object>> mapList3 = this.findDataList(sql3);
		if(mapList3.size() > 0) {
			Double totalResult = 0.0d;
			int totalCount = 0;
			for(int i = 0; i < mapList3.size(); i++) {
				totalResult += Double.parseDouble(mapList3.get(i).get("totalScore").toString());
				totalCount++;
			}
			Double avgResult = totalResult / totalCount;
			item.setResult(Double.parseDouble(doubleFormat.format(avgResult)));
			this.save(item);
			return ;
		}
		//从授权考核模板关联的考核项中取到所有该考核项的打分记录
		String sql = "select id, weight, result from hr_pa_authpbcitem where akpiItem2uId = " + itemId;
		List<Map<String, Object>> list = this.findDataList(sql);
		Double avgResult = new Double(0);
		Double totalWeight = new Double(1);
		for(int i = 0; i < list.size(); i++) {
			Map<String, Object> map = list.get(i);
			//判断是否已经打分，result为0则表示没有打分
			if(Double.parseDouble(map.get("result").toString()) != 0) {//已经打分，则进入计算
				avgResult += (Double.parseDouble(map.get("result").toString()) * Double.parseDouble(map.get("weight").toString()));
				totalWeight = totalWeight - Double.parseDouble(map.get("weight").toString());
			}
		}
		Double result = Double.valueOf(doubleFormat.format(avgResult + item.getResult() * totalWeight));
		item.setResult(result);
		//保存相关联的绩效系数
		int resultIndex = 0;
		String sql2 = "select a.id, a.pisScore, a.coefficient from hr_pa_performanceindexscore a, hr_pa_kpiitem2user b where " +
				"b.id = " + itemId + " and a.piId = b.piId";
		List<Map<String, Object>> mapList2 = this.findDataList(sql2);
		for(int i = 0; i < mapList2.size() - 1; i++) {
			if(result >= Double.parseDouble(mapList2.get(i).get("pisScore").toString())) {
				resultIndex = i;
			}
		}
		item.setCoefficient(Double.parseDouble(mapList2.get(resultIndex).get("coefficient").toString()));
		//插入数据库
		this.save(item);
	}
	/*
	 * 批量计算考核模板关联所有考核项平均分
	 * @param pbcId
	 * 考核模板ID
	 * */
	public void multiCal(Long pbcId) {
		String sql = "select a.id from hr_pa_kpiitem2user a, hr_pa_performanceindex b where " +
				"a.pbcId = " + pbcId + " and a.piId = b.id and b.paMode = 12";
		List<Map<String, Object>> mapList = this.findDataList(sql);
		for(int i = 0; i < mapList.size(); i++) {
			this.calculateAvg(Long.parseLong(mapList.get(i).get("id").toString()));
		}
	}
	/*
	 * 批量保存
	 * @param list
	 * 要保存的数据集
	 * */
	public void multiSave(List<HrPaKpiitem2user> list) {
		for(int i = 0; i < list.size(); i++) {
			this.save(list.get(i));
		}
	}
}
