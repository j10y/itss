package com.zsgj.itil.service.service;

import java.util.List;

import com.zsgj.itil.jfreeChart.entity.StatisticsPicture;

public interface JfreeChartService {
	List findJfreeChart(StatisticsPicture statisticsPicture);
	List findRequire();
}
