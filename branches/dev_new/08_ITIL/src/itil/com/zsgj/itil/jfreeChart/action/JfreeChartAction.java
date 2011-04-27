package com.zsgj.itil.jfreeChart.action;
//package com.zsgj.itil.jfreeChart.action;
//
//import java.io.PrintWriter;
//import java.util.HashMap;
//import java.util.HashSet;
//import java.util.List;
//import java.util.Map;
//import java.util.Set;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import org.apache.struts.action.ActionForm;
//import org.apache.struts.action.ActionForward;
//import org.apache.struts.action.ActionMapping;
//import org.jfree.data.category.CategoryDataset;
//import org.jfree.data.general.PieDataset;
//
//import com.zsgj.info.appframework.metadata.entity.SystemMainTable;
//import com.zsgj.info.appframework.metadata.entity.SystemMainTableColumn;
//import com.zsgj.info.appframework.pagemodel.servlet.PageParameter;
//import com.zsgj.info.framework.service.Service;
//import com.zsgj.info.framework.util.HttpUtil;
//import com.zsgj.info.framework.util.asm.render.FreemarkerRender;
//import com.zsgj.info.framework.web.adapter.struts.BaseDispatchAction;
//import com.zsgj.itil.jfreeChart.entity.JfreeChartView;
//import com.zsgj.itil.jfreeChart.entity.StatisticsPicture;
//import com.zsgj.itil.service.service.JfreeChartService;
//import com.zsgj.itil.util.JfreeChartUtil;
//
//public class JfreeChartAction extends BaseDispatchAction{
//	private Service bs = getService();
//	private JfreeChartService jfcs = (JfreeChartService) getBean("jfreeChartService");
//	public ActionForward save(ActionMapping mapping,
//			ActionForm actionForm, HttpServletRequest request,
//			HttpServletResponse response) throws Exception {
//		String tjPictureName=request.getParameter("tjPictureName");
//		String tjPictureURL=request.getParameter("tjPictureURL");
//		String tjPictureType=request.getParameter("tjPictureType");
//		String XName=request.getParameter("XName");
//		String YName=request.getParameter("YName");
//		String[] ids=request.getParameterValues("id");
//		String lableTickName=request.getParameter("lableTickName");
//		String itemName=request.getParameter("itemName");
//		String sqlString="Select ";
//		Set<String> sqlSelects=new HashSet();
//		Set<String> sqlFroms =new HashSet();
//		Set<String> sqlWheres =new HashSet();
//		Set<String> sqlGroups =new HashSet();
//		Set<String> sqlOrders =new HashSet();
//		String smtTableName=request.getParameter("smtTableName");
//		String smtTableId=request.getParameter("smtId");
//		SystemMainTable smt=(SystemMainTable) bs.find(SystemMainTable.class, smtTableId);
//		if(ids.length>0){
//			for(String id:ids){
//				String columnName=request.getParameter("columnName_"+id);
//				String foreignTable=request.getParameter("foreignTable_"+id);
//				String sqlType=request.getParameter("sqlType_"+id);
//				String foreignTableValueColumn=request.getParameter("foreignTableValueColumn_"+id);
//				String foreignTableKeyColumn=request.getParameter("foreignTableKeyColumn_"+id);
//				if(sqlType.equals("1")){//MAX
//					if(foreignTable==null||foreignTable.length()<1){
//						String ss="MAX("+smtTableName+"."+columnName+") as '"+columnName+"'";
//						sqlSelects.add(ss);
//						sqlFroms.add(smtTableName);
//					}else{
//						String ss="MAX("+foreignTable+"."+foreignTableValueColumn+") as '"+columnName+"'";
//						sqlSelects.add(ss);
//						sqlFroms.add(foreignTable);
//						String ss1=smtTableName+"."+foreignTableKeyColumn+"."+columnName+"="+foreignTable+"."+foreignTableKeyColumn;
//						sqlWheres.add(ss1);
//					}
//				}else if(sqlType.equals("2")){//COUNT
//					if(foreignTable==null||foreignTable.length()<1){
//						sqlSelects.add("COUNT("+smtTableName+"."+columnName+") as 'num'");
//						sqlFroms.add(smtTableName);
//						sqlOrders.add("COUNT("+smtTableName+"."+columnName+")");
//					}else{
//						sqlSelects.add("COUNT("+foreignTable+"."+foreignTableValueColumn+") as 'num'");
//						sqlFroms.add(foreignTable);
//						sqlWheres.add(smtTableName+"."+columnName+"="+foreignTable+"."+foreignTableKeyColumn);
//						sqlOrders.add("COUNT("+foreignTable+"."+foreignTableValueColumn+")");
//					}
//				}
////				else if(sqlType.equals("3")){
////					if(foreignTable==null){
////						sqlSelects.add("MAX("+smtTableName+"."+columnName+") as '"+columnName+"'");
////						sqlFroms.add(smtTableName);
////						sqlOrders.add(smtTableName+"."+columnName);
////					}else{
////						sqlSelects.add("MAX("+foreignTable+"."+foreignTableValueColumn+") as '"+foreignTableValueColumn+"'");
////						sqlFroms.add(foreignTable);
////						sqlWheres.add(smtTableName+".id="+foreignTableKeyColumn+"."+foreignTableValueColumn);
////						sqlOrders.add(foreignTable+"."+foreignTableValueColumn);
////					}
////				}
//				else if(sqlType.equals("4")){//GROUP BY
//					if(foreignTable==null||foreignTable.length()<1){
//						sqlSelects.add("MAX("+smtTableName+"."+columnName+") as '"+columnName+"'");
//						sqlFroms.add(smtTableName);
//						sqlGroups.add(smtTableName+"."+columnName);
//					}else{
//						sqlSelects.add("MAX("+foreignTable+"."+foreignTableValueColumn+") as '"+columnName+"'");
//						sqlFroms.add(foreignTable);
//						sqlWheres.add(smtTableName+"."+columnName+"="+foreignTable+"."+foreignTableKeyColumn);
//						sqlGroups.add(foreignTable+"."+foreignTableValueColumn);
//					}
//				}else{//其他类型
//					if(foreignTable==null||foreignTable.length()<1){
//						sqlSelects.add(smtTableName+"."+columnName+" as '"+columnName+"'");
//						sqlFroms.add(smtTableName);
//						//sqlGroups.add(smtTableName+"."+columnName);
//						sqlOrders.add(smtTableName+"."+columnName);
//					}else{
//						sqlSelects.add(foreignTable+"."+foreignTableValueColumn+" as '"+columnName+"'");
//						sqlFroms.add(foreignTable);
//						sqlWheres.add(smtTableName+"."+columnName+"="+foreignTable+"."+foreignTableKeyColumn);
//						//sqlGroups.add(foreignTable+"."+foreignTableValueColumn);
//						sqlOrders.add(foreignTable+"."+foreignTableValueColumn);
//					}
//				}
//				
//			}
//			for(String sqlSelect:sqlSelects){
//				sqlString=sqlString+sqlSelect+",";
//			}
//			sqlString=sqlString.substring(0, sqlString.length()-1)+" from ";
//			for(String sqlFrom:sqlFroms){
//				sqlString=sqlString+sqlFrom+",";
//			}
//			if(sqlWheres.size()>0&&!sqlWheres.isEmpty()){
//				sqlString=sqlString.substring(0, sqlString.length()-1)+" where ";
//				for(String sqlWhere:sqlWheres){
//					sqlString=sqlString+sqlWhere+" and ";
//				}
//			}else {
//				sqlString=sqlString.substring(0, sqlString.length()-1);
//			}
//			if(sqlString.indexOf("and")>0){
//				sqlString=sqlString.substring(0,sqlString.lastIndexOf("and"))+" group by ";
//			}else{
//				sqlString=sqlString+" group by ";
//			}
//			
//			for(String sqlGroup:sqlGroups){
//				sqlString=sqlString+sqlGroup+",";
//			}
//			sqlString=sqlString.substring(0, sqlString.length()-1)+" order by ";
//			for(String sqlOrder:sqlOrders){
//				sqlString=sqlString+sqlOrder+" desc,";
//			}
//			sqlString=sqlString.substring(0, sqlString.length()-1);
//		}
//		StatisticsPicture statp=new StatisticsPicture();
//		statp.setTjPictureName(tjPictureName);
//		statp.setTjPictureURL(tjPictureURL);
//		statp.setTjPictureType(tjPictureType);
//		statp.setSqlString(sqlString);
//		statp.setSysMainTable(smt);
//		SystemMainTableColumn smtc1=new SystemMainTableColumn();
//		smtc1.setId(Long.parseLong(itemName));
//		statp.setItemName(smtc1);
//		if(!tjPictureType.equals("1")){
//			statp.setXName(XName);
//			statp.setYName(YName);
//			SystemMainTableColumn smtc2=new SystemMainTableColumn();
//			smtc2.setId(Long.parseLong(lableTickName));
//			statp.setLableTickName(smtc2);
//		}
//		StatisticsPicture st=(StatisticsPicture) bs.save(statp);
//		
//		String FSP = System.getProperty("file.separator");
//		String pagePathUrl = request.getSession().getServletContext().getRealPath(FSP);
//		String path=tjPictureURL;
//		JfreeChartView jcv=new JfreeChartView();
//			jcv.setDataId(st.getId().toString());
//		  String fileName= path.substring((path.lastIndexOf("/")+1));
//		  String jsName= fileName.substring(0, (fileName.lastIndexOf(".")));
//		  String filePath= path.substring(1, (path.lastIndexOf("/")));
//		     jcv.setFilePath(filePath+"/"+jsName);
//		  String filepath=  filePath.replace("/", "\\");
//		  FreemarkerRender render = new FreemarkerRender();
//		   String rootPath=pagePathUrl+filepath+"\\";
//		  //"E:\\JAVA\\jboss-4.2.2.GA\\server\\default\\.\\deploy\\itil.war\\user\\itil";
//				String TemplatePath="com/zsgj/info/appframework/pagemodel/ftl/jfreechartview.ftl";
//				    render.renderJfree(jcv, TemplatePath,rootPath+jsName+".js");
//				String TemplatePath2="com/zsgj/info/appframework/pagemodel/ftl/jfreechartviewjsp.ftl";
//				    render.renderJfree(jcv, TemplatePath2,rootPath+fileName);
//		return HttpUtil.redirect("/infoAdmin/sysMainTable.do?methodCall=toTjForm&id="+smtTableId);
//	}
//	
//	public ActionForward makepicture(ActionMapping mapping,
//			ActionForm actionForm, HttpServletRequest request,
//			HttpServletResponse response) throws Exception {		
//		String tjPictureURL=request.getParameter("tjPictureURL");
//		String id=request.getParameter("dataId");
//		String smtTableId=request.getParameter("smtTableId");
//		String FSP = System.getProperty("file.separator");
//		String pagePathUrl = request.getSession().getServletContext().getRealPath(FSP);
//		String path=tjPictureURL;
//		JfreeChartView jcv=new JfreeChartView();
//			jcv.setDataId(id);
//		  String fileName= path.substring((path.lastIndexOf("/")+1));
//		  String jsName= fileName.substring(0, (fileName.lastIndexOf(".")));
//		  String filePath= path.substring(1, (path.lastIndexOf("/")));
//		     jcv.setFilePath(filePath+"/"+jsName);
//		  String filepath=  filePath.replace("/", "\\");
//		  FreemarkerRender render = new FreemarkerRender();
//		   String rootPath=pagePathUrl+filepath+"\\";
//		  //"E:\\JAVA\\jboss-4.2.2.GA\\server\\default\\.\\deploy\\itil.war\\user\\itil";
//				String TemplatePath="com/zsgj/info/appframework/pagemodel/ftl/jfreechartview.ftl";
//				    render.renderJfree(jcv, TemplatePath,rootPath+jsName+".js");
//				String TemplatePath2="com/zsgj/info/appframework/pagemodel/ftl/jfreechartviewjsp.ftl";
//				    render.renderJfree(jcv, TemplatePath2,rootPath+fileName);
//		return HttpUtil.redirect("/infoAdmin/sysMainTable.do?methodCall=toTjForm&id="+smtTableId);
//	}
//	public ActionForward remove(ActionMapping mapping,
//			ActionForm actionForm, HttpServletRequest request,
//			HttpServletResponse response) throws Exception {
//		String smtTableId=request.getParameter("smtId");
//		String[] ids=request.getParameterValues("id");
//		for(String id:ids){
//			bs.remove(StatisticsPicture.class, id);
//		}
////		StatisticsPicture st=(StatisticsPicture) bs.find(StatisticsPicture.class, objectId);
////		jfcs.findJfreeChart(st);
//		return HttpUtil.redirect("/infoAdmin/sysMainTable.do?methodCall=toTjForm&id="+smtTableId);
//	}
//	
//	public ActionForward findRequire(ActionMapping mapping,
//			ActionForm actionForm, HttpServletRequest request,
//			HttpServletResponse response) throws Exception {
//		response.setContentType("text/plain;charset=gbk");
//		response.getContentType();
//	    PrintWriter out = response.getWriter();
//		List values=jfcs.findRequire();
//		//String values=new String[][];
//		PieDataset dataset=JfreeChartUtil.getDataSetPie(values);
//		Map maps=new HashMap();
//		maps.put("title", "需求按所属部门个数百分比");
//		String fileName=JfreeChartUtil.generatePieChart(request.getSession(),new PrintWriter(out),580,250,maps,dataset);
//		String graphURL = request.getContextPath() + "/servlet/DisplayChart?filename=" + fileName;
//		request.setAttribute("graphURL", graphURL);
//		return mapping.findForward("showpie");
//	}
//	
//	public ActionForward toStatisticsPicture(ActionMapping mapping,
//			ActionForm actionForm, HttpServletRequest request,
//			HttpServletResponse response) throws Exception {
//		response.setContentType("text/plain;charset=gbk");
//		response.getContentType();
//	    PrintWriter out = response.getWriter();
//	    String id=request.getParameter("id");
//	    StatisticsPicture stp=(StatisticsPicture) bs.find(StatisticsPicture.class, id);
//		List values=jfcs.findJfreeChart(stp);
//		//String values=new String[][];
//		if(stp.getTjPictureType().equals("1")){//饼图
//			PieDataset dataset=JfreeChartUtil.getDataSetPie(values);
//			Map maps=new HashMap();
//			maps.put("title", stp.getTjPictureName());
//			String fileName=JfreeChartUtil.generatePieChart(request.getSession(),new PrintWriter(out),580,250,maps,dataset);
//			String graphURL = request.getContextPath() + "/servlet/DisplayChart?filename=" + fileName;
//			request.setAttribute("graphURL", graphURL);
//			return mapping.findForward("showpie");
//		}else if(stp.getTjPictureType().equals("2")){//柱状-横向
//			CategoryDataset dataset=JfreeChartUtil.getDataSetBar(values);
//			Map maps=new HashMap();
//			maps.put("title", stp.getTjPictureName());
//			maps.put("X", stp.getXName());
//			maps.put("Y", stp.getYName());
//			String fileName=JfreeChartUtil.generateBarChart(request.getSession(),new PrintWriter(out),700,250,maps,dataset);
//			String graphURL = request.getContextPath() + "/servlet/DisplayChart?filename=" + fileName;
//			request.setAttribute("graphURL", graphURL);
//			return mapping.findForward("showbar");
//		}else if(stp.getTjPictureType().equals("3")){//柱状-纵向
//			CategoryDataset dataset=JfreeChartUtil.getDataSetBar(values);
//			Map maps=new HashMap();
//			maps.put("title", stp.getTjPictureName());
//			maps.put("X", stp.getXName());
//			maps.put("Y", stp.getYName());
//			String fileName=JfreeChartUtil.generateBarChartY(request.getSession(),new PrintWriter(out),700,250,maps,dataset);
//			String graphURL = request.getContextPath() + "/servlet/DisplayChart?filename=" + fileName;
//			request.setAttribute("graphURL", graphURL);
//			return mapping.findForward("showbar");
//		}else if(stp.getTjPictureType().equals("4")){//曲线
//			CategoryDataset dataset=JfreeChartUtil.getDataSetLine(values);
//			Map maps=new HashMap();
//			maps.put("title", stp.getTjPictureName());
//			maps.put("X", stp.getXName());
//			maps.put("Y", stp.getYName());
//			String fileName=JfreeChartUtil.generateLineChart(request.getSession(),new PrintWriter(out),700,250,maps,dataset);
//			String graphURL = request.getContextPath() + "/servlet/DisplayChart?filename=" + fileName;
//			request.setAttribute("graphURL", graphURL);
//			return mapping.findForward("showline");
//		}
//		return null;
//	}
//}
