package com.xpsoft.oa.service.shop.impl;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;

import com.xpsoft.core.service.impl.BaseServiceImpl;
import com.xpsoft.core.util.AppUtil;
import com.xpsoft.oa.dao.shop.SpPaAuthpbccitemDao;
import com.xpsoft.oa.model.shop.SpPaAuthpbccitem;
import com.xpsoft.oa.service.shop.SpPaAuthpbccitemService;

public class SpPaAuthpbccitemServiceImpl extends BaseServiceImpl<SpPaAuthpbccitem>
	implements SpPaAuthpbccitemService{
	private SpPaAuthpbccitemDao dao;
	
	public SpPaAuthpbccitemServiceImpl(SpPaAuthpbccitemDao dao){
		super(dao);
		this.dao = dao;
	}
	/*
	 * 批量保存数据
	 * */
	public void multiSave(List<SpPaAuthpbccitem> list) {
		DecimalFormat doubleFormat = new DecimalFormat();
		doubleFormat.applyPattern("###.00");
		if(list.size() > 0) {
			for(int i = 0; i < list.size(); i++) {
				list.get(i).setResult(Double.parseDouble(doubleFormat.format(list.get(i).getResult())));
				this.save(list.get(i));
			}
		}
	}
	
	public void multiSave(List<SpPaAuthpbccitem> list, Long pbcId) {
		if(list.size() > 0) {
			for(int i = 0; i < list.size(); i++) {
				this.save(list.get(i));
			}
		}
	}
}
