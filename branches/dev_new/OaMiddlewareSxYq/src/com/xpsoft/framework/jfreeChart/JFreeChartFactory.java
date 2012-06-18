//package com.xpsoft.framework.jfreeChart;
//
//import java.awt.Color;
//import java.awt.Font;
//import java.io.IOException;
//import java.text.DecimalFormat;
//import java.text.NumberFormat;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Map;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpSession;
//import org.jfree.chart.ChartFactory;
//import org.jfree.chart.JFreeChart;
//import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
//import org.jfree.chart.plot.PiePlot;
//import org.jfree.chart.plot.PiePlot3D;
//import org.jfree.chart.servlet.ServletUtilities;
//import org.jfree.chart.title.TextTitle;
//import org.jfree.data.general.DefaultPieDataset;
//import org.jfree.util.Rotation;
///**
// * 生成jfreeChart图片
// * @Class Name JFreeChartFactory
// * @Author likang
// * @Create In Aug 18, 2010
// */
//public class JFreeChartFactory {
//	
//	//报表中涉及到xy轴的位置的样式
//	public static final Integer X_BOTTOM_Y_LEFT = 1;
//	public static final Integer X_TOP_Y_LEFT = 2;
//	public static final Integer X_BOTTOM_Y_RIGHT = 3;
//	public static final Integer X_TOP_Y_RIGHT = 4;
//
//	
//	private static JFreeChartFactory jFreeChartFactory = new JFreeChartFactory();
//	
//	private JFreeChartFactory() {
//		
//	}
//	
//	public static JFreeChartFactory getJFreeChartFactoryInstance() {
//		return jFreeChartFactory;
//	}
//	/**
//	 * 生成平面饼状图或3D饼状图
//	 * @Methods Name getPieChart
//	 * @Create In Mar 3, 2010 By likang
//	 * @param dataMap							数据Map key(String)是每一个section的名称 value(String,Integer,Double,Float)是数值		必须
//	 * @param title								报表标题	必须
//	 * @param chartWidth						报表宽度	必须
//	 * @param chartHeight						报表高度	必须
//	 * @param is3D								是否是3d饼状图
//	 * @param isClearing						是否透明 有默认 可为null
//	 * @param sectionColors						颜色Map可单独设置某一个或所有section的颜色 key(String)是每一个section的名称 value(Color)是颜色 可为null
//	 * @param sectionLableMoreDescription		section块的描述增加百分比的显示
//	 * @param legendLableMoreDescription		图例的描述增加百分比的显示
//	 * @param bgColor							报表背景色	有默认 可为null
//	 * @param sectionlabelBgColor				section描述背景颜色 有默认 可为null
//	 * @param titleFont							标题字体		有默认 可为null
//	 * @param angle								角度			有默认 可为null
//	 * @param sectionLabelFont					section块描述字体 有默认 可为null
//	 * @param legendFont						图例字体 有默认 可为null
//	 * @param explodePercentKey					需要弹出的section的key 不弹出 可为null
//	 * @param explodePercentValue 				需要弹出的section的数值 不弹出 可为null
//	 * @param sectionOutlineColor 				section块边框颜色 有默认 可为null
//	 * @param request
//	 * @return String							返回生成图片的url地址
//	 */
//	public  String getPieChart(Map dataMap,
//			String title,
//			Integer chartWidth, 
//			Integer chartHeight,
//			Boolean is3D,
//			Boolean isClearing,
//			Map sectionColors,
//			Boolean sectionLableMoreDescription,
//			Boolean legendLableMoreDescription,
//			Color bgColor,
//			Color sectionlabelBgColor,
//			Font titleFont,
//			Double angle,
//			Font sectionLabelFont,
//			Font legendFont,
//			String explodePercentKey,
//			Double explodePercentValue,
//			Color sectionOutlineColor,
//			HttpServletRequest request) {
//		// TODO Auto-generated method stub
//		HttpSession session = request.getSession();
//		
//		/*开始设置默认选项*/
//		if(sectionLabelFont == null) {
//			//默认每个section块描述字体
//			sectionLabelFont = new Font("宋体", 0, 12);
//		}
//		
//		boolean hasExplodePercent = false;
//		if(explodePercentKey != null) {
//			hasExplodePercent = true;
//			if(explodePercentValue == null) {
//				//默认弹出section块弹出的大小
//				explodePercentValue = new Double(0.2d);
//			}
//		}
//		
//		//图例字体
//		if(legendFont == null) {
//			legendFont = new Font("宋体", 10, 10);
//		}
//		
//		//标题字体
//		if(titleFont == null) {
//			titleFont = new Font("黑体", 10, 15);
//		}
//		
//		//报表背景默认白色
//		if(bgColor == null) {
//			bgColor = Color.white;
//		}
//	
//		//装载用户需要显示的数据 包括每一个section块的名称和数值
//		DefaultPieDataset dataset = getPieDataset(dataMap);
//		//通过工厂类生成JFreeChart对象
//		JFreeChart chart = null;
//		PiePlot pieplot = null;
//		if(is3D) {
//			chart = ChartFactory.createPieChart3D(title, dataset, true, false, false);
//			pieplot = (PiePlot3D)chart.getPlot();
//		} else {
//			chart = ChartFactory.createPieChart(title, dataset, true, false, false);
//			pieplot = (PiePlot)chart.getPlot();
//		}
//		//设置开始角度
//		if(angle != null) {
//			pieplot.setStartAngle(angle);
//		}
//		//设置方向为"顺时针方向"
//		pieplot.setDirection(Rotation.CLOCKWISE);
//		//设置透明度，0.5F为半透明，1为不透明，0为全透明
//		if(isClearing != null && isClearing) {
//			pieplot.setForegroundAlpha(0.65F);
//		} else {
//			pieplot.setForegroundAlpha(1F);
//		}
//		pieplot.setNoDataMessage("由于您未进行风险评测，因此无法针对您的风险承受能力做出配置建议，请您先进行风险评测再查看配置建议");      
//		pieplot.setLabelFont(sectionLabelFont);
//		/*抽离 section，就是把某一section从饼形图剥离出来 3d不支持*/
//		//如果设置了弹出的sectionkey则弹出
//		if(hasExplodePercent) {
//			pieplot.setExplodePercent(explodePercentKey, explodePercentValue); 
//		}
//		//如果需要单独设置每一个section块的颜色 则设置 
//		if(sectionColors != null) {
//			setEachSectionsColors(pieplot,sectionColors); 
//		}
//		/*设置报表背景等相关信息*/
//		pieplot.setBackgroundPaint(bgColor);
//		/*设置指定 section 轮廓线的颜色，如果不指定，默认值为NULL。*/
//		if(sectionOutlineColor != null) {
//			pieplot.setBaseSectionOutlinePaint(sectionOutlineColor);
//		}
//		//设置section描述背景色
//		if(sectionlabelBgColor != null) {
//			pieplot.setLabelBackgroundPaint(sectionlabelBgColor);
//		}
//		/*设置标题信息字体*/
//		TextTitle txtTitle = null; 
//		txtTitle = chart.getTitle(); 
//		//标题字体设置
//		txtTitle.setFont(titleFont); 
//		/*设置 底部种类框 必须当 ChartFactory.createPieChart3D("IT行业职业分布图", 
//        dataset,
//        true,
//        false,
//        false);倒数第三个参数 为TRUE 也就是显示底部的时候
//		 */
//		//图例字体
//		chart.getLegend().setItemFont(legendFont);
//		/*在下端显示每个块的百分数采用下面自定义样式显示，{0}表示选项，{1}表示数值，{2}表示所占比例*/
//		//pieplot.setLabelGenerator(new StandardPieSectionLabelGenerator(StandardPieSectionLabelGenerator.DEFAULT_SECTION_LABEL_FORMAT));
//		/*设置底部例图中的文字显示内容按照百分比显示（更详细）*/
//		if(legendLableMoreDescription) {
//			/*设置底部例图中的文字显示内容*/
//			//pieplot.setLegendLabelGenerator(new StandardPieSectionLabelGenerator("{0}: ({1}M, {2})"));
//			pieplot.setLegendLabelGenerator(new StandardPieSectionLabelGenerator("{0}={1}({2})",
//	                NumberFormat.getNumberInstance(),
//	                new DecimalFormat("0.00%")));
//		}
//		/*设置section描述中的文字显示内容按照百分比显示（更详细）*/
//		if(sectionLableMoreDescription) {
//			/*默认显示百分比是取整的，如果要让百分比保留二位小数，可以用如下 并且在每一个settion的描述上显示了百分比*/
//			pieplot.setLabelGenerator(new StandardPieSectionLabelGenerator("{0}={1}({2})",
//			                    NumberFormat.getNumberInstance(),
//			                    new DecimalFormat("0.00%")));
//		}
//		//没有数据的时候显示的内容
//		pieplot.setNoDataMessage("由于您未进行风险评测，因此无法针对您的风险承受能力做出配置建议，请您先进行风险评测再查看配置建议");
//		pieplot.setCircular(false);
//		pieplot.setLabelGap(0.02D);
//		String filename = "";
//		try {
//			filename = ServletUtilities.saveChartAsPNG(chart, chartWidth, chartHeight, null, session);
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} 
//		//将图片保存在磁盘上
//		//saveOnDisk(chart,filename,request);
//		String graphURL = request.getContextPath() + "/DisplayChart?filename=" + filename;
//		return graphURL;	
//	}
//	
//	/**
//	 * 为饼状图的每一个section块单独设置颜色 
//	 * @Methods Name setEachSectionsColors
//	 * @Create In Mar 3, 2010 By likang
//	 * @param pieplot
//	 * @param sectionColors void
//	 */
//	private void setEachSectionsColors(PiePlot pieplot, Map sectionColors) {
//		// TODO Auto-generated method stub
//		//遍历map获取要设置section的key和颜色
//		List<String> keyList = new ArrayList<String>();
//		keyList.addAll(sectionColors.keySet());
//		String key = "";
//		Color color = null;
//		for(int i=0; i<keyList.size(); i++) {
//			key = (String) keyList.get(i);
//			color = (Color) sectionColors.get(keyList.get(i));
//			/*单独设置Section的颜色*/
//			pieplot.setSectionPaint(key, color);
//		}
//	}
//
//	/**
//	 * 收集饼状图数据(饼状图辅助方法)
//	 * @Methods Name getPieDataset
//	 * @Create In Mar 3, 2010 By likang
//	 * @param dataMap
//	 * @return DefaultPieDataset
//	 */
//	private DefaultPieDataset getPieDataset(Map dataMap) {
//		// TODO Auto-generated method stub
//		//装载数据的集合
//		DefaultPieDataset dataset = new DefaultPieDataset();
//		//遍历map获取饼状图每部分的名称和值，并装入集合
//		List<String> keyList = new ArrayList<String>();
//		keyList.addAll(dataMap.keySet());
//		String key = "";
//		String value = "";
//		for(int i=0; i<keyList.size(); i++) {
//			key = (String) keyList.get(i);
//			value = String.valueOf(dataMap.get(keyList.get(i)));
//			dataset.setValue(key, Double.parseDouble(value));
//		}
//		return dataset;
//	}
//	
//	
//}
