package com.xpsoft.oa.service.kpi.impl;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;

import com.xpsoft.core.service.impl.BaseServiceImpl;
import com.xpsoft.core.util.AppUtil;
import com.xpsoft.oa.dao.kpi.HrPaAuthpbccitemDao;
import com.xpsoft.oa.model.kpi.HrPaAuthpbccitem;
import com.xpsoft.oa.service.kpi.HrPaAuthorizepbcService;
import com.xpsoft.oa.service.kpi.HrPaAuthpbccitemService;
import com.xpsoft.oa.service.kpi.HrPaKpiPBC2UserService;

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
		DecimalFormat doubleFormat = new DecimalFormat();
		doubleFormat.applyPattern("###.00");
		if(list.size() > 0) {
			for(int i = 0; i < list.size(); i++) {
				list.get(i).setResult(Double.parseDouble(doubleFormat.format(list.get(i).getResult())));
				this.save(list.get(i));
			}
		}
	}
	
	public void multiSave(List<HrPaAuthpbccitem> list, Long pbcId) {
		if(list.size() > 0) {
			for(int i = 0; i < list.size(); i++) {
				this.save(list.get(i));
			}
		}
	}
}
