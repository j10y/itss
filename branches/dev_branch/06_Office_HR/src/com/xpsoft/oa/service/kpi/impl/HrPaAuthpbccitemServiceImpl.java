package com.xpsoft.oa.service.kpi.impl;

import java.util.List;

import com.xpsoft.core.service.impl.BaseServiceImpl;
import com.xpsoft.oa.dao.kpi.HrPaAuthpbccitemDao;
import com.xpsoft.oa.model.kpi.HrPaAuthpbccitem;
import com.xpsoft.oa.service.kpi.HrPaAuthpbccitemService;

public class HrPaAuthpbccitemServiceImpl extends BaseServiceImpl<HrPaAuthpbccitem>
	implements HrPaAuthpbccitemService{
	private HrPaAuthpbccitemDao dao;
	
	public HrPaAuthpbccitemServiceImpl(HrPaAuthpbccitemDao dao){
		super(dao);
		this.dao = dao;
	}
	/*
	 * 批量保存数据
	 * */
	public void multiSave(List<HrPaAuthpbccitem> list) {
		if(list.size() > 0) {
			for(int i = 0; i < list.size(); i++) {
				this.save(list.get(i));
			}
		}
	}
}
