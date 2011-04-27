package com.zsgj.itil.util;

import java.awt.Color;
import java.awt.Font;
import java.awt.RenderingHints;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartRenderingInfo;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.entity.StandardEntityCollection;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.plot.PiePlot3D;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer3D;
import org.jfree.chart.servlet.ServletUtilities;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.DefaultValueDataset;
import org.jfree.data.general.PieDataset;


public final class JfreeChartUtil {
	//1、饼状图数据集
	 public static PieDataset getDataSetPie(List<String[]> values) {
			DefaultPieDataset dataset = new DefaultPieDataset();
			for(String[] val:values ){
				dataset.setValue(val[0],Double.parseDouble(val[1].toString()));
			}
			return dataset;
		}

	    //2、柱壮图数据集
	 public static CategoryDataset getDataSetBar(List<String[]> values) {
			DefaultCategoryDataset dataset = new DefaultCategoryDataset();
			for(String[] val:values){
				//dataset.addValue("数量integer", "X", "Y");
				dataset.addValue(Integer.parseInt(val[0].toString()), val[1], val[2]);
			}
			return dataset;
		}

	   //3、	折线图数据集
	 public static CategoryDataset getDataSetLine(List<String[]> values) {
			DefaultCategoryDataset dataset = new DefaultCategoryDataset();
			for(String[] val:values){
				//dataset.addValue("数量integer", "X", "Y");
				dataset.addValue(Integer.parseInt(val[0].toString()), val[1], val[2]);
			}
			return dataset;
		}
	 //4.仪表
	 public static DefaultValueDataset getDataSetDial(String value) {
		 	DefaultValueDataset dataset = new DefaultValueDataset(); 
		 	dataset.setValue(Integer.parseInt(value));
			return dataset;
		}
	 public static String generatePieChart(HttpSession session, PrintWriter pw, int w,
				int h,Map maps,PieDataset dataset) {
			// TODO Auto-generated method stub
			String filename = null;
		//	PieDataset dataset = getDataSet();
			JFreeChart chart = ChartFactory.createPieChart3D(
					maps.get("title").toString(), // 图表标题
					dataset, // 数据集
					false, 	// 是否显示图例
					false, 	// 是否生成工具
					false 	// 是否生成URL链接
			);
			Font font = new Font("宋体", 10, 15); 
			TextTitle txtTitle = null; 
			txtTitle = chart.getTitle(); 
			txtTitle.setFont(font); //标题字体设置
			Font font1 = new Font("楷体", 10, 10);
			PiePlot3D pieplot = (PiePlot3D)chart.getPlot(); 
			pieplot.setLabelFont(font1);//标签字体设置
			pieplot.setLabelGenerator(new StandardPieSectionLabelGenerator(
					("{0}: ({2})"), NumberFormat.getNumberInstance(),
					new DecimalFormat("0.00%")));
			//pieplot.setBackgroundAlpha(0.5f);
			pieplot.setForegroundAlpha(0.7f);
			pieplot.setCircular(false);

			//chart.setBackgroundPaint(Color.pink);
			try {
				/*------得到chart的保存路径----*/	
				ChartRenderingInfo info = new ChartRenderingInfo(new StandardEntityCollection());
				filename = ServletUtilities.saveChartAsPNG(chart, w, h, info,session);
				/*------使用printWriter将文件写出----*/
				ChartUtilities.writeImageMap(pw, filename, info, true);
				//pw.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}	
			return filename;
		}
	 	//水平方向柱状图
		public static String generateBarChart(HttpSession session, PrintWriter pw, int w,
				int h,Map maps,CategoryDataset dataset) {
			// TODO Auto-generated method stub
			String filename = null;
			JFreeChart chart = ChartFactory.createBarChart3D(
					maps.get("title").toString(), // 图表标题
					maps.get("X").toString(), // 目录轴的显示标签
					maps.get("Y").toString(), // 数值轴的显示标签
					dataset, // 数据集
					PlotOrientation.HORIZONTAL, // 图表方向：水平、垂直
					true, 	// 是否显示图例(对于简单的柱状图必须是false)
					false, 	// 是否生成工具
					false 	// 是否生成URL链接
			);
//			PiePlot pieplot = (PiePlot)chart.getPlot();
			CategoryPlot categoryplot = chart.getCategoryPlot(); 
			categoryplot.getRangeAxis().setLabelFont(new Font("宋体", Font.ITALIC, 15)) ;//Y轴
			categoryplot.getDomainAxis().setLabelFont(new Font("宋体", Font.ITALIC, 15));//X轴
			CategoryAxis categoryaxis = categoryplot.getDomainAxis(); 
			categoryaxis.setTickLabelFont(new Font("宋体", Font.ITALIC, 10));
			categoryaxis.setCategoryLabelPositions(CategoryLabelPositions.STANDARD);   
	        categoryaxis.setMaximumCategoryLabelWidthRatio(0.8F);   
	        categoryaxis.setLowerMargin(0.02D);   
	        categoryaxis.setUpperMargin(0.02D);   
			chart.getTitle().setFont(new Font("宋体", 10, 15));//标题
			chart.getLegend().setItemFont(new Font("宋体", 10, 10));//底部
			 //ChartUtilities.applyCurrentTheme(chart);   
			BarRenderer3D renderer = new BarRenderer3D();
			renderer.setItemLabelGenerator(new StandardCategoryItemLabelGenerator()); 
			renderer.setItemLabelFont(new Font("黑体",Font.PLAIN,10));
			renderer.setItemLabelsVisible(true);
			categoryplot.setRenderer(renderer);

		
			try {
				/*------得到chart的保存路径----*/	
				ChartRenderingInfo info = new ChartRenderingInfo(new StandardEntityCollection());
				filename = ServletUtilities.saveChartAsPNG(chart, w, h, info,session);
				/*------使用printWriter将文件写出----*/
				ChartUtilities.writeImageMap(pw, filename, info, true);
			//	pw.flush();
			} catch (IOException e) {
				// TODO 自动生成 catch 块
				e.printStackTrace();
			}	
			return filename;
		}
		//垂直方向柱状图
		public static String generateBarChartY(HttpSession session, PrintWriter pw, int w,
				int h,Map maps,CategoryDataset dataset) {
			// TODO Auto-generated method stub
			String filename = null;
			JFreeChart chart = ChartFactory.createBarChart3D(
					maps.get("title").toString(), // 图表标题
					maps.get("X").toString(), // 目录轴的显示标签
					maps.get("Y").toString(), // 数值轴的显示标签
					dataset, // 数据集
					PlotOrientation.VERTICAL, // 图表方向：水平、垂直
					true, 	// 是否显示图例(对于简单的柱状图必须是false)
					false, 	// 是否生成工具
					false 	// 是否生成URL链接
			);
//			PiePlot pieplot = (PiePlot)chart.getPlot();
			CategoryPlot categoryplot = chart.getCategoryPlot(); 
			categoryplot.getRangeAxis().setLabelFont(new Font("宋体", Font.ITALIC, 15)) ;//Y轴
			categoryplot.getDomainAxis().setLabelFont(new Font("宋体", Font.ITALIC, 15));//X轴
			CategoryAxis categoryaxis = categoryplot.getDomainAxis(); 
			categoryaxis.setTickLabelFont(new Font("宋体", Font.ITALIC, 10));
			categoryaxis.setCategoryLabelPositions(CategoryLabelPositions.STANDARD);   
	        categoryaxis.setMaximumCategoryLabelWidthRatio(0.8F);   
	        categoryaxis.setLowerMargin(0.02D);   
	        categoryaxis.setUpperMargin(0.02D);   
			chart.getTitle().setFont(new Font("宋体", 10, 15));//标题
			chart.getLegend().setItemFont(new Font("宋体", 10, 10));//底部
			 //ChartUtilities.applyCurrentTheme(chart);   
			BarRenderer3D renderer = new BarRenderer3D();
			renderer.setSeriesPaint(0,Color.green);
			//renderer.setSeriesPaint(0, Color.YELLOW, true);
			//renderer.setSeriesFillPaint(0, Color.YELLOW);
			renderer.setItemLabelGenerator(new StandardCategoryItemLabelGenerator()); 
			renderer.setItemLabelFont(new Font("黑体",Font.PLAIN,10));
			renderer.setItemLabelsVisible(true);
			categoryplot.setRenderer(renderer);

		
			try {
				/*------得到chart的保存路径----*/	
				ChartRenderingInfo info = new ChartRenderingInfo(new StandardEntityCollection());
				filename = ServletUtilities.saveChartAsPNG(chart, w, h, info,session);
				/*------使用printWriter将文件写出----*/
				ChartUtilities.writeImageMap(pw, filename, info, true);
			//	pw.flush();
			} catch (IOException e) {
				// TODO 自动生成 catch 块
				e.printStackTrace();
			}	
			return filename;
		}

		public static String generateLineChart(HttpSession session, PrintWriter pw, int w,
				int h,Map maps,CategoryDataset dataset) {
			// TODO Auto-generated method stub
			String filename = null;
			//CategoryDataset dataset = getDataSet2();
			JFreeChart chart = ChartFactory.createLineChart(
					maps.get("title").toString(), // 图表标题
					maps.get("X").toString(), // 目录轴的显示标签
					maps.get("Y").toString(), // 数值轴的显示标签
					dataset, // 数据集
					PlotOrientation.VERTICAL, // 图表方向：水平、垂直
					true, 	// 是否显示图例(对于简单的柱状图必须是false)
					false, 	// 是否生成工具
					false 	// 是否生成URL链接
			);
			
			/*----------设置消除字体的锯齿渲染（解决中文问题）--------------*/
					chart.getRenderingHints().put(RenderingHints.KEY_TEXT_ANTIALIASING,
					RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);
			
			/*------------配置图表属性--------------*/
			// 1,设置整个图表背景颜色
			//chart.setBackgroundPaint(Color.darkGray);
			
			/*------------设定Plot参数-------------*/
			CategoryPlot plot = chart.getCategoryPlot();
			// 2,设置详细图表的显示细节部分的背景颜色
			plot.setBackgroundPaint(Color.PINK);
			// 3,设置垂直网格线颜色
			plot.setDomainGridlinePaint(Color.black);
			//4,设置是否显示垂直网格线
			plot.setDomainGridlinesVisible(true);
			//5,设置水平网格线颜色
			plot.setRangeGridlinePaint(Color.blue);
			//6,设置是否显示水平网格线
			plot.setRangeGridlinesVisible(true);
			//CategoryPlot categoryplot = chart.getCategoryPlot(); 
			plot.getRangeAxis().setLabelFont(new Font("宋体", Font.ITALIC, 15)) ;//Y轴
			plot.getDomainAxis().setLabelFont(new Font("宋体", Font.ITALIC, 15));//X轴
			CategoryAxis categoryaxis = plot.getDomainAxis(); 
			categoryaxis.setTickLabelFont(new Font("宋体", Font.ITALIC, 10));
			categoryaxis.setCategoryLabelPositions(CategoryLabelPositions.STANDARD);   
	        categoryaxis.setMaximumCategoryLabelWidthRatio(0.8F);      
			chart.getTitle().setFont(new Font("宋体", 10, 15));//标题
			chart.getLegend().setItemFont(new Font("宋体", 10, 10));//底部

			try {
				/*------得到chart的保存路径----*/	
				ChartRenderingInfo info = new ChartRenderingInfo(new StandardEntityCollection());
				filename = ServletUtilities.saveChartAsPNG(chart, w, h, info,session);
				/*------使用printWriter将文件写出----*/
				ChartUtilities.writeImageMap(pw, filename, info, true);
				pw.flush();
			} catch (IOException e) {
				// TODO 自动生成 catch 块
				e.printStackTrace();
			}	
			return filename;
		}
}
