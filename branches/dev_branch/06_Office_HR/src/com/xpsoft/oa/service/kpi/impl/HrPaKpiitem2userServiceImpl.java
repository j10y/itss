package com.xpsoft.oa.service.kpi.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.xpsoft.core.command.QueryFilter;
import com.xpsoft.core.service.impl.BaseServiceImpl;
import com.xpsoft.core.util.AppUtil;
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
		HrPaAuthpbccitemService hrPaAuthpbccitemService = (HrPaAuthpbccitemService)AppUtil.getBean("hrPaAuthpbccitemService");
		//从授权考核模板关联的考核项中取到所有该考核项的打分记录
		String sql = "select id, weight, result from hr_pa_authpbcitem where akpiItem2uId = " + itemId;
		List<Map<String, Object>> list = this.findDataList(sql);
		Double avgResult = new Double(0);
		for(int i = 0; i < list.size(); i++) {
			Map<String, Object> map = list.get(i);
			//判断是否已经打分，result为0则表示没有打分
			if(Double.parseDouble(map.get("result").toString()) != 0) {//已经打分，则进入计算
				avgResult += (Double.parseDouble(map.get("result").toString()) * Double.parseDouble(map.get("weight").toString()));
			}
			//计算完毕，删除打分记录
			hrPaAuthpbccitemService.remove(Long.parseLong(map.get("id").toString()));
		}
		//取得个人考核模板关联的该考核项，并保存结果
		HrPaKpiitem2user item = this.get(itemId);
		item.setResult(avgResult);
		//插入数据库
		this.save(item);
	}
	/*
	 * 批量计算考核模板关联所有考核项平均分
	 * @param pbcId
	 * 考核模板ID
	 * */
	public void multiCal(Long pbcId) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("Q_pbc2User.id_L_EQ", String.valueOf(pbcId));
		QueryFilter filter = new QueryFilter(map);
		List<HrPaKpiitem2user> itemList = this.getAll(filter);
		for(int i = 0; i < itemList.size(); i++) {
			this.calculateAvg(itemList.get(i).getId());
		}
	}
}
