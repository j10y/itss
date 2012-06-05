package net.shopin.action;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import jofc2.OFCException;
import jofc2.model.Chart;
import jofc2.model.axis.Label;
import jofc2.model.axis.XAxis;
import jofc2.model.axis.YAxis;
import jofc2.model.elements.LineChart;
import net.shopin.utils.AppUtil;
import net.shopin.utils.NginxSecondAlarmDataUtil;

import org.apache.struts2.ServletActionContext;

/**
 * ���24Сʱ������ͼ
 * 
 * @author wchao
 * 
 */
public class NginxSecondChart {

	public String execute() throws OFCException, IOException {
		// y�����ݼ���-NGINX���朽ӣ�ͨ�^�{�Ô�����ӿګ@ȡ
		List<String> timeLabels = new ArrayList<String>();
		
		List<Number> webDataSet = new ArrayList<Number>();
		List<Number> appDataSet = new ArrayList<Number>();
		
		// x�����ݼ���-�r�g����ǰ�r�gǰ24С�r
		List<Label> xLabel = new ArrayList<Label>();
		
		for(int i=0; i<60; i++){			
			xLabel.add(new Label((i+1) + ("\n��\nǰ")));
		}
		Object [] webKeyArray = NginxSecondAlarmDataUtil.webKeyQueue.toArray();
		for(Object key : webKeyArray){
			webDataSet.add(Integer.valueOf(key.toString()));
		}
		Collections.reverse(webDataSet);
		
		Object [] appKeyArray = NginxSecondAlarmDataUtil.appKeyQueue.toArray();
		for(Object key : appKeyArray){
			appDataSet.add(Integer.valueOf(key.toString()));
		}
		Collections.reverse(appDataSet);
		
		// ����X����ʾ���ڣ���һС�r���λ���@ʾ��ǰ�r�g24С�r����Ϣ
		XAxis labels = new XAxis();
		labels.addLabels(xLabel);
		
		// ����Y����ʾֵ��:Range��������������Ϊ��������Сֵ�����ֵ�Ͳ���ֵ
		YAxis range = new YAxis();
		range.setRange(0,
				Integer.valueOf(AppUtil.getProperties("activeAppNum", "300")),
				10);
		// OFC����ͼ����
		LineChart webLineChart = new LineChart(LineChart.Style.NORMAL);
		webLineChart.addValues(webDataSet);
		webLineChart.setColour("#6666FF");
		webLineChart.setText("WEB Nginx���������");
		
		LineChart appLineChart = new LineChart(LineChart.Style.NORMAL);
		appLineChart.addValues(appDataSet);
		appLineChart.setColour("#ff0000");
		appLineChart.setText("APP Nginx���������");
		
		// ͼ������
		Chart chart = new Chart("Nginx������߄�����");
		chart.setXAxis(labels);
		chart.setYAxis(range);
		chart.addElements(webLineChart);
		chart.addElements(appLineChart);
		
		// ��ӡJSON��ʽ���ı�
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
