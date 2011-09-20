package com.xpsoft.oa.service.shop.impl;

import java.util.List;
import java.util.Map;

import com.xpsoft.core.service.impl.BaseServiceImpl;
import com.xpsoft.oa.dao.shop.SpPaAssessmentcriteriaDao;
import com.xpsoft.oa.model.shop.SpPaAssessmentcriteria;
import com.xpsoft.oa.service.shop.SpPaAssessmentcriteriaService;

public class SpPaAssessmentcriteriaServiceImpl extends BaseServiceImpl<SpPaAssessmentcriteria>
	implements SpPaAssessmentcriteriaService{
	private SpPaAssessmentcriteriaDao dao;
	
	public SpPaAssessmentcriteriaServiceImpl(SpPaAssessmentcriteriaDao dao){
		super(dao);
		this.dao = dao;
	}
	/*
	 * 取得所有考核标准关键字和名称
	 * */
	public Map<String, String> getKeyAndName() {
		return this.dao.getKeyAndName();
	}
	/*
	 * 判断关键字是否存在
	 * @param key
	 * 要判断的关键字
	 * */
	public boolean checkKey(String key) {
		boolean flag = false;
		String sql = "select id from sp_pa_assessmentcriteria where acKey = '" + key + "'";
		List<Map<String, Object>> list = this.findDataList(sql);
		if(list.size() > 0) {//list里边有数据，表明关键字重复
			flag = true;
		}
		return flag;
	}
}
