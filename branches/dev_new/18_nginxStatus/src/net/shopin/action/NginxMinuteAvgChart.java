package net.shopin.action;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import jofc2.OFCException;
import jofc2.model.Chart;
import jofc2.model.axis.Label;
import jofc2.model.axis.XAxis;
import jofc2.model.axis.YAxis;
import jofc2.model.elements.LineChart;
import net.shopin.utils.NginxMinuteArgAlarmDataUtil;
import net.shopin.utils.NginxSecondAlarmDataUtil;

import org.apache.struts2.ServletActionContext;

/**
 * 最近24小时的曲线图
 * 
 * @author wchao
 * 
 */
public class NginxMinuteAvgChart {

	public String execute() throws OFCException, IOException {
		// y轴数据集合-NGINX活動鏈接，通過調用數據庫接口獲取
		List<String> timeLabels = new ArrayList<String>();
		
		List<Number> webDataSet = new ArrayList<Number>();
		List<Number> appDataSet = new ArrayList<Number>();
		
		// x轴数据集合-時間，當前時間前24小時
		List<Label> xLabel = new ArrayList<Label>();
		
		for(int i=0; i<60; i++){
//			webDataSet.add((int)(Math.random()*200));
//			appDataSet.add((int)(Math.random()*200));			
			xLabel.add(new Label((i+1) + ("\n分\n前")));
		}
		Object [] webKeyArray = NginxSecondAlarmDataUtil.webKeyQueue.toArray();
		for(Object key : webKeyArray){
			webDataSet.add(Integer.valueOf(NginxMinuteArgAlarmDataUtil.webDataMap.get(key).split(",")[0]));
		}
		Object [] appKeyArray = NginxSecondAlarmDataUtil.appKeyQueue.toArray();
		for(Object key : webKeyArray){
			appDataSet.add(Integer.valueOf(NginxMinuteArgAlarmDataUtil.appDataMap.get(key).split(",")[0]));
		}
		
		// 设置X轴显示日期：以一小時為單位，顯示當前時間24小時內信息
		XAxis labels = new XAxis();
		labels.addLabels(xLabel);
		
		// 设置Y轴显示值域:Range的三个参数含义为：坐标最小值，最大值和步进值
		YAxis range = new YAxis();
		range.setRange(0, 300, 10);
		// OFC折线图设置
		LineChart webLineChart = new LineChart(LineChart.Style.NORMAL);
		webLineChart.addValues(webDataSet);
		webLineChart.setColour("#ff0000");
		webLineChart.setText("WEB Nginx活动连接曲线");
		
		LineChart appLineChart = new LineChart(LineChart.Style.NORMAL);
		appLineChart.addValues(appDataSet);
		appLineChart.setColour("#6666FF");
		appLineChart.setText("APP Nginx活动连接曲线");
		
		// 图表设置
		Chart chart = new Chart("Nginx活动连接走勢曲线");
		chart.setXAxis(labels);
		chart.setYAxis(range);
		chart.addElements(webLineChart);
		chart.addElements(appLineChart);
		
		// 打印JSON格式的文本
		System.out.print(chart.toString());
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("application/json-rpc;charset=utf-8");
		response.setHeader("Cache-Control", "no-cache");
		response.setHeader("Expires", "0");
		response.setHeader("Pragma", "No-cache");
		response.getWriter().write(chart.toString());
		return null;
	}
}
