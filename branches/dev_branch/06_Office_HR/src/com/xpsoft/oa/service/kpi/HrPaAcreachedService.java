package com.xpsoft.oa.service.kpi;

import java.util.List;

import com.xpsoft.core.service.BaseService;
import com.xpsoft.oa.model.kpi.HrPaAcreached;

public interface HrPaAcreachedService extends BaseService<HrPaAcreached>{
	/*
	 * 批量保存数据
	 * params: 数据集，数据保存类型
	 * */
	public void multiSave(List<HrPaAcreached> list, String templateId);
}
