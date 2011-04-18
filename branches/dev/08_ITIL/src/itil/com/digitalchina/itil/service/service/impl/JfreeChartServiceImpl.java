package com.digitalchina.itil.service.service.impl;

import java.util.List;

import com.digitalchina.info.framework.dao.BaseDao;
import com.digitalchina.itil.dao.JfreeChartDao;
import com.digitalchina.itil.jfreeChart.entity.StatisticsPicture;
import com.digitalchina.itil.service.service.JfreeChartService;

public class JfreeChartServiceImpl extends BaseDao implements JfreeChartService{
	private JfreeChartDao jfreeChartDao;
	public JfreeChartDao getJfreeChartDao() {
		return jfreeChartDao;
	}
	public void setJfreeChartDao(JfreeChartDao jfreeChartDao) {
		this.jfreeChartDao = jfreeChartDao;
	}
	public List findJfreeChart(StatisticsPicture statisticsPicture) {
		// TODO Auto-generated method stub
		return jfreeChartDao.selectJfreeChart(statisticsPicture);
	}
	public List findRequire() {
		// TODO Auto-generated method stub
		return jfreeChartDao.selectRequire();
	}

}
