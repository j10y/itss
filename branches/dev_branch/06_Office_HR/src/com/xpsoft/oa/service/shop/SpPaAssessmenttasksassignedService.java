package com.xpsoft.oa.service.shop;

import java.util.List;

import com.xpsoft.core.service.BaseService;
import com.xpsoft.oa.model.shop.SpPaAssessmenttasksassigned;

public interface SpPaAssessmenttasksassignedService extends BaseService<SpPaAssessmenttasksassigned>{
	/*
	 * 批量保存数据
	 * params: 数据集，数据保存类型
	 * */
	public void multiSave(List<SpPaAssessmenttasksassigned> list, String templateId, Long deptId);
}
