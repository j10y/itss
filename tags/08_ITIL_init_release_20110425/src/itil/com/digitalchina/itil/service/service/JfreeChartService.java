package com.digitalchina.itil.service.service;

import java.util.List;

import com.digitalchina.itil.jfreeChart.entity.StatisticsPicture;

public interface JfreeChartService {
	List findJfreeChart(StatisticsPicture statisticsPicture);
	List findRequire();
}
