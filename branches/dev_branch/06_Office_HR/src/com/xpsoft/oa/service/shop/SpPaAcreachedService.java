package com.xpsoft.oa.service.shop;

import java.util.List;

import com.xpsoft.core.service.BaseService;
import com.xpsoft.oa.model.shop.SpPaAcreached;

public interface SpPaAcreachedService extends BaseService<SpPaAcreached>{
	/*
	 * 批量保存数据
	 * params: 数据集，数据保存类型
	 * */
	public void multiSave(List<SpPaAcreached> list, String templateId, Long deptId);
}
