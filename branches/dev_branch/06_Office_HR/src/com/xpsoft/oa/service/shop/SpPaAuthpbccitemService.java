package com.xpsoft.oa.service.shop;

import java.util.List;

import com.xpsoft.core.service.BaseService;
import com.xpsoft.oa.model.shop.SpPaAuthpbccitem;

public interface SpPaAuthpbccitemService extends BaseService<SpPaAuthpbccitem>{
	/*
	 * 批量保存数据
	 * */
	public void multiSave(List<SpPaAuthpbccitem> list);
	public void multiSave(List<SpPaAuthpbccitem> list, Long pbcId);
}
