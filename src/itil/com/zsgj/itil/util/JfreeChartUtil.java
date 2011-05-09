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
	//1����״ͼ���ݼ�
	 public static PieDataset getDataSetPie(List<String[]> values) {
			DefaultPieDataset dataset = new DefaultPieDataset();
			for(String[] val:values ){
				dataset.setValue(val[0],Double.parseDouble(val[1].toString()));
			}
			return dataset;
		}

	    //2����׳ͼ���ݼ�
	 public static CategoryDataset getDataSetBar(List<String[]> values) {
			DefaultCategoryDataset dataset = new DefaultCategoryDataset();
			for(String[] val:values){
				//dataset.addValue("����integer", "X", "Y");
				dataset.addValue(Integer.parseInt(val[0].toString()), val[1], val[2]);
			}
			return dataset;
		}

	   //3��	����ͼ���ݼ�
	 public static CategoryDataset getDataSetLine(List<String[]> values) {
			DefaultCategoryDataset dataset = new DefaultCategoryDataset();
			for(String[] val:values){
				//dataset.addValue("����integer", "X", "Y");
				dataset.addValue(Integer.parseInt(val[0].toString()), val[1], val[2]);
			}
			return dataset;
		}
	 //4.�Ǳ�
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
					maps.get("title").toString(), // ͼ�����
					dataset, // ���ݼ�
					false, 	// �Ƿ���ʾͼ��
					false, 	// �Ƿ����ɹ���
					false 	// �Ƿ�����URL����
			);
			Font font = new Font("����", 10, 15); 
			TextTitle txtTitle = null; 
			txtTitle = chart.getTitle(); 
			txtTitle.setFont(font); //������������
			Font font1 = new Font("����", 10, 10);
			PiePlot3D pieplot = (PiePlot3D)chart.getPlot(); 
			pieplot.setLabelFont(font1);//��ǩ��������
			pieplot.setLabelGenerator(new StandardPieSectionLabelGenerator(
					("{0}: ({2})"), NumberFormat.getNumberInstance(),
					new DecimalFormat("0.00%")));
			//pieplot.setBackgroundAlpha(0.5f);
			pieplot.setForegroundAlpha(0.7f);
			pieplot.setCircular(false);

			//chart.setBackgroundPaint(Color.pink);
			try {
				/*------�õ�chart�ı���·��----*/	
				ChartRenderingInfo info = new ChartRenderingInfo(new StandardEntityCollection());
				filename = ServletUtilities.saveChartAsPNG(chart, w, h, info,session);
				/*------ʹ��printWriter���ļ�д��----*/
				ChartUtilities.writeImageMap(pw, filename, info, true);
				//pw.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}	
			return filename;
		}
	 	//ˮƽ������״ͼ
		public static String generateBarChart(HttpSession session, PrintWriter pw, int w,
				int h,Map maps,CategoryDataset dataset) {
			// TODO Auto-generated method stub
			String filename = null;
			JFreeChart chart = ChartFactory.createBarChart3D(
					maps.get("title").toString(), // ͼ�����
					maps.get("X").toString(), // Ŀ¼�����ʾ��ǩ
					maps.get("Y").toString(), // ��ֵ�����ʾ��ǩ
					dataset, // ���ݼ�
					PlotOrientation.HORIZONTAL, // ͼ����ˮƽ����ֱ
					true, 	// �Ƿ���ʾͼ��(���ڼ򵥵���״ͼ������false)
					false, 	// �Ƿ����ɹ���
					false 	// �Ƿ�����URL����
			);
//			PiePlot pieplot = (PiePlot)chart.getPlot();
			CategoryPlot categoryplot = chart.getCategoryPlot(); 
			categoryplot.getRangeAxis().setLabelFont(new Font("����", Font.ITALIC, 15)) ;//Y��
			categoryplot.getDomainAxis().setLabelFont(new Font("����", Font.ITALIC, 15));//X��
			CategoryAxis categoryaxis = categoryplot.getDomainAxis(); 
			categoryaxis.setTickLabelFont(new Font("����", Font.ITALIC, 10));
			categoryaxis.setCategoryLabelPositions(CategoryLabelPositions.STANDARD);   
	        categoryaxis.setMaximumCategoryLabelWidthRatio(0.8F);   
	        categoryaxis.setLowerMargin(0.02D);   
	        categoryaxis.setUpperMargin(0.02D);   
			chart.getTitle().setFont(new Font("����", 10, 15));//����
			chart.getLegend().setItemFont(new Font("����", 10, 10));//�ײ�
			 //ChartUtilities.applyCurrentTheme(chart);   
			BarRenderer3D renderer = new BarRenderer3D();
			renderer.setItemLabelGenerator(new StandardCategoryItemLabelGenerator()); 
			renderer.setItemLabelFont(new Font("����",Font.PLAIN,10));
			renderer.setItemLabelsVisible(true);
			categoryplot.setRenderer(renderer);

		
			try {
				/*------�õ�chart�ı���·��----*/	
				ChartRenderingInfo info = new ChartRenderingInfo(new StandardEntityCollection());
				filename = ServletUtilities.saveChartAsPNG(chart, w, h, info,session);
				/*------ʹ��printWriter���ļ�д��----*/
				ChartUtilities.writeImageMap(pw, filename, info, true);
			//	pw.flush();
			} catch (IOException e) {
				// TODO �Զ����� catch ��
				e.printStackTrace();
			}	
			return filename;
		}
		//��ֱ������״ͼ
		public static String generateBarChartY(HttpSession session, PrintWriter pw, int w,
				int h,Map maps,CategoryDataset dataset) {
			// TODO Auto-generated method stub
			String filename = null;
			JFreeChart chart = ChartFactory.createBarChart3D(
					maps.get("title").toString(), // ͼ�����
					maps.get("X").toString(), // Ŀ¼�����ʾ��ǩ
					maps.get("Y").toString(), // ��ֵ�����ʾ��ǩ
					dataset, // ���ݼ�
					PlotOrientation.VERTICAL, // ͼ����ˮƽ����ֱ
					true, 	// �Ƿ���ʾͼ��(���ڼ򵥵���״ͼ������false)
					false, 	// �Ƿ����ɹ���
					false 	// �Ƿ�����URL����
			);
//			PiePlot pieplot = (PiePlot)chart.getPlot();
			CategoryPlot categoryplot = chart.getCategoryPlot(); 
			categoryplot.getRangeAxis().setLabelFont(new Font("����", Font.ITALIC, 15)) ;//Y��
			categoryplot.getDomainAxis().setLabelFont(new Font("����", Font.ITALIC, 15));//X��
			CategoryAxis categoryaxis = categoryplot.getDomainAxis(); 
			categoryaxis.setTickLabelFont(new Font("����", Font.ITALIC, 10));
			categoryaxis.setCategoryLabelPositions(CategoryLabelPositions.STANDARD);   
	        categoryaxis.setMaximumCategoryLabelWidthRatio(0.8F);   
	        categoryaxis.setLowerMargin(0.02D);   
	        categoryaxis.setUpperMargin(0.02D);   
			chart.getTitle().setFont(new Font("����", 10, 15));//����
			chart.getLegend().setItemFont(new Font("����", 10, 10));//�ײ�
			 //ChartUtilities.applyCurrentTheme(chart);   
			BarRenderer3D renderer = new BarRenderer3D();
			renderer.setSeriesPaint(0,Color.green);
			//renderer.setSeriesPaint(0, Color.YELLOW, true);
			//renderer.setSeriesFillPaint(0, Color.YELLOW);
			renderer.setItemLabelGenerator(new StandardCategoryItemLabelGenerator()); 
			renderer.setItemLabelFont(new Font("����",Font.PLAIN,10));
			renderer.setItemLabelsVisible(true);
			categoryplot.setRenderer(renderer);

		
			try {
				/*------�õ�chart�ı���·��----*/	
				ChartRenderingInfo info = new ChartRenderingInfo(new StandardEntityCollection());
				filename = ServletUtilities.saveChartAsPNG(chart, w, h, info,session);
				/*------ʹ��printWriter���ļ�д��----*/
				ChartUtilities.writeImageMap(pw, filename, info, true);
			//	pw.flush();
			} catch (IOException e) {
				// TODO �Զ����� catch ��
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
					maps.get("title").toString(), // ͼ�����
					maps.get("X").toString(), // Ŀ¼�����ʾ��ǩ
					maps.get("Y").toString(), // ��ֵ�����ʾ��ǩ
					dataset, // ���ݼ�
					PlotOrientation.VERTICAL, // ͼ����ˮƽ����ֱ
					true, 	// �Ƿ���ʾͼ��(���ڼ򵥵���״ͼ������false)
					false, 	// �Ƿ����ɹ���
					false 	// �Ƿ�����URL����
			);
			
			/*----------������������ľ����Ⱦ������������⣩--------------*/
					chart.getRenderingHints().put(RenderingHints.KEY_TEXT_ANTIALIASING,
					RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);
			
			/*------------����ͼ������--------------*/
			// 1,��������ͼ������ɫ
			//chart.setBackgroundPaint(Color.darkGray);
			
			/*------------�趨Plot����-------------*/
			CategoryPlot plot = chart.getCategoryPlot();
			// 2,������ϸͼ�����ʾϸ�ڲ��ֵı�����ɫ
			plot.setBackgroundPaint(Color.PINK);
			// 3,���ô�ֱ��������ɫ
			plot.setDomainGridlinePaint(Color.black);
			//4,�����Ƿ���ʾ��ֱ������
			plot.setDomainGridlinesVisible(true);
			//5,����ˮƽ��������ɫ
			plot.setRangeGridlinePaint(Color.blue);
			//6,�����Ƿ���ʾˮƽ������
			plot.setRangeGridlinesVisible(true);
			//CategoryPlot categoryplot = chart.getCategoryPlot(); 
			plot.getRangeAxis().setLabelFont(new Font("����", Font.ITALIC, 15)) ;//Y��
			plot.getDomainAxis().setLabelFont(new Font("����", Font.ITALIC, 15));//X��
			CategoryAxis categoryaxis = plot.getDomainAxis(); 
			categoryaxis.setTickLabelFont(new Font("����", Font.ITALIC, 10));
			categoryaxis.setCategoryLabelPositions(CategoryLabelPositions.STANDARD);   
	        categoryaxis.setMaximumCategoryLabelWidthRatio(0.8F);      
			chart.getTitle().setFont(new Font("����", 10, 15));//����
			chart.getLegend().setItemFont(new Font("����", 10, 10));//�ײ�

			try {
				/*------�õ�chart�ı���·��----*/	
				ChartRenderingInfo info = new ChartRenderingInfo(new StandardEntityCollection());
				filename = ServletUtilities.saveChartAsPNG(chart, w, h, info,session);
				/*------ʹ��printWriter���ļ�д��----*/
				ChartUtilities.writeImageMap(pw, filename, info, true);
				pw.flush();
			} catch (IOException e) {
				// TODO �Զ����� catch ��
				e.printStackTrace();
			}	
			return filename;
		}
}
