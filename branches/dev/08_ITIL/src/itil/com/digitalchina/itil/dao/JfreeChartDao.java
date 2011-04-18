package com.digitalchina.itil.dao;

import java.util.List;

import com.digitalchina.itil.jfreeChart.entity.StatisticsPicture;

public interface JfreeChartDao {
	List selectJfreeChart(StatisticsPicture statisticsPicture);
	List selectRequire();
}
