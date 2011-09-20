package com.xpsoft.oa.service.shop.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.xpsoft.core.service.impl.BaseServiceImpl;
import com.xpsoft.core.util.DateUtil;
import com.xpsoft.oa.dao.shop.SpPaAcreachedDao;
import com.xpsoft.oa.model.shop.SpPaAcreached;
import com.xpsoft.oa.service.shop.SpPaAcreachedService;

public class SpPaAcreachedServiceImpl extends BaseServiceImpl<SpPaAcreached>
	implements SpPaAcreachedService{
	private SpPaAcreachedDao dao;
	
	public SpPaAcreachedServiceImpl(SpPaAcreachedDao dao){
		super(dao);
		this.dao = dao;
	}
	/*
	 * 批量保存数据
	 * params: 数据集，数据保存类型
	 * */
	public void multiSave(List<SpPaAcreached> list, String templateId, Long deptId) {
		//删除相同模板ID的当月份的数据
		String sql = "delete from sp_pa_acreached where " +
				" templateId = '" + templateId + "' and inputDate > '" + DateUtil.convertDateToString(DateUtil.getFirstDayOfMonth(new Date())) + "'";
		sql += deptId == null ? "" : " and deptId = " + deptId;
		boolean flag = this.removeDatabySql(sql);
		if(!flag) {
			logger.error("模板ID为：" + templateId + "关联的考核标准目标数据删除失败！");
		}
		if(list.size() > 0) {
			for(int i = 0; i < list.size(); i++) {
				this.save(list.get(i));
			}
		}
	}
}
