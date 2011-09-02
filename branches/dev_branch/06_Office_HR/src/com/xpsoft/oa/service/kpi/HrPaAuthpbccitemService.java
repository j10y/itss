package com.xpsoft.oa.service.kpi;

import java.util.List;

import com.xpsoft.core.service.BaseService;
import com.xpsoft.oa.model.kpi.HrPaAuthpbccitem;

public interface HrPaAuthpbccitemService extends BaseService<HrPaAuthpbccitem>{
	/*
	 * 批量保存数据
	 * */
	public void multiSave(List<HrPaAuthpbccitem> list);
	public void multiSave(List<HrPaAuthpbccitem> list, Long pbcId);
}
