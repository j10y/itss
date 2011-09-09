package com.xpsoft.oa.service.kpi.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.xpsoft.core.service.impl.BaseServiceImpl;
import com.xpsoft.core.util.DateUtil;
import com.xpsoft.oa.dao.kpi.HrPaAssessmenttasksassignedDao;
import com.xpsoft.oa.model.kpi.HrPaAssessmenttasksassigned;
import com.xpsoft.oa.service.kpi.HrPaAssessmenttasksassignedService;

public class HrPaAssessmenttasksassignedServiceImpl extends BaseServiceImpl<HrPaAssessmenttasksassigned>
	implements HrPaAssessmenttasksassignedService{
	private HrPaAssessmenttasksassignedDao dao;
	
	public HrPaAssessmenttasksassignedServiceImpl(HrPaAssessmenttasksassignedDao dao){
		super(dao);
		this.dao = dao;
	}
	/*
	 * 批量保存数据
	 * params: 数据集，数据保存类型
	 * */
	public void multiSave(List<HrPaAssessmenttasksassigned> list, String templateId) {
		//删除相同模板ID的当月份的数据
		String sql = "delete from hr_pa_assessmenttasksassigned where " +
				" templateId = '" + templateId + "' and publishDate > '" + DateUtil.convertDateToString(DateUtil.getFirstDayOfMonth(new Date())) + "'";
		boolean flag = this.removeDatabySql(sql);
		if(!flag) {
			logger.error("模板ID为：" + templateId + "关联的考核标准目标数据删除失败！");
		}
		if(list.size() > 0) {
			for(int i = 0; i < list.size(); i++) {
				this.save(list.get(i));
			}
		}
	}
}
