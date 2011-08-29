package com.xpsoft.oa.service.kpi.impl;

import java.util.List;
import java.util.Map;

import com.xpsoft.core.service.impl.BaseServiceImpl;
import com.xpsoft.oa.dao.kpi.HrPaAcreachedDao;
import com.xpsoft.oa.model.kpi.HrPaAcreached;
import com.xpsoft.oa.service.kpi.HrPaAcreachedService;

public class HrPaAcreachedServiceImpl extends BaseServiceImpl<HrPaAcreached>
	implements HrPaAcreachedService{
	private HrPaAcreachedDao dao;
	
	public HrPaAcreachedServiceImpl(HrPaAcreachedDao dao){
		super(dao);
		this.dao = dao;
	}
	/*
	 * 批量保存数据
	 * params: 数据集，数据保存类型
	 * */
	public void multiSave(List<HrPaAcreached> list, String type) {
		if(list.size() > 0) {
			for(int i = 0; i < list.size(); i++) {
				if("category".equals(type)) {
					String sql = "select id from hr_pa_acreached " +
							"where category = " + list.get(i).getCategory() + " and " +
									"acId = " + list.get(i).getAcId();
					List<Map<String, Object>> countList = this.findDataList(sql);
					if(countList.size() > 0) {
						list.get(i).setId(Long.parseLong(countList.get(0).get("id").toString()));
					}
				} else if("user".equals(type)) {
					String sql = "select id from hr_pa_acreached " +
							"where userId = " + list.get(i).getUserId() + " and " +
									"acId = " + list.get(i).getAcId();
					List<Map<String, Object>> countList = this.findDataList(sql);
					if(countList.size() > 0) {
						list.get(i).setId(Long.parseLong(countList.get(0).get("id").toString()));
					}
				}
				this.save(list.get(i));
			}
		}
	}
}
