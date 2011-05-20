package com.zsgj.itil.dao;

import java.util.List;

import com.zsgj.itil.jfreeChart.entity.StatisticsPicture;

public interface JfreeChartDao {
	List selectJfreeChart(StatisticsPicture statisticsPicture);
	List selectRequire();
}
