package com.xpsoft.oa.service.kpi;

import java.util.List;

import com.xpsoft.core.service.BaseService;
import com.xpsoft.oa.model.kpi.HrPaAssessmenttasksassigned;

public interface HrPaAssessmenttasksassignedService extends BaseService<HrPaAssessmenttasksassigned>{
	/*
	 * 批量保存数据
	 * params: 数据集，数据保存类型
	 * */
	public void multiSave(List<HrPaAssessmenttasksassigned> list, String type);
}
