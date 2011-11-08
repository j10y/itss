package com.xp.commonpart.action;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.general.PieDataset;

import com.opensymphony.xwork2.ActionSupport;
import com.xp.commonpart.bean.StatisticsPicture;
import com.xp.commonpart.service.BaseService;
import com.xp.commonpart.service.ComQueryService;
import com.xp.commonpart.service.SelectDataService;
import com.xp.commonpart.util.ContextHolder;
import com.xp.commonpart.util.JfreeChartUtil;

public class JfreeChartAction extends ActionSupport{
	private SelectDataService selectDataService=(SelectDataService) ContextHolder.getBean("selectDataService");
	private BaseService baseService=(BaseService) ContextHolder.getBean("baseService");
	private ComQueryService comqueryService=(ComQueryService) ContextHolder.getBean("comqueryService");
	
	public String toStatisticsPicture() throws Exception {
		HttpServletRequest request=ServletActionContext.getRequest();
		HttpServletResponse response=ServletActionContext.getResponse();
		PrintWriter out;
		StatisticsPicture stp;
		String itemName= "";
		String lableTickName= "";
		String numberName="";
		List<Map> listdata;
		try {
			Map map=comqueryService.queryTableForAjaxService(request);
			String sql=(String) map.get("sql");
			response.setContentType("text/plain;charset=utf-8");
			response.getContentType();
			out = response.getWriter();
			String id=request.getParameter("id");
			stp = new StatisticsPicture();
			List list=baseService.findObjectByPar(StatisticsPicture.class,"id",Long.parseLong(id));
			stp=(StatisticsPicture)list.get(0) ;
			if(stp.getItemName()!=null){
				itemName=stp.getItemName().getColumnName();
			}
			if(stp.getLableTickName()!=null){
				lableTickName=stp.getLableTickName().getColumnName();
			}
			if(stp.getNumberName()!=null){
				numberName=stp.getNumberName().getColumnName();
			}
			if(stp.getTjPictureType().equals("1")){
				sql="select chartsql."+itemName+", sum(chartsql."+numberName+") as numberName from ("+sql+") chartsql group by chartsql."+itemName;
			}else{
				sql="select chartsql."+itemName+",chartsql."+lableTickName+", sum(chartsql."+numberName+") as numberName from ("+sql+") chartsql group by chartsql."+itemName+",chartsql."+lableTickName;
			}
			listdata = selectDataService.getData(sql);
			List listdateset=new ArrayList();
		    if(stp.getTjPictureType().equals("1")){
		    	for(Map mapdata:listdata){
		    		String[] values =new String[2];
		    		values[0]=mapdata.get(itemName)+"";
		   			values[1]=mapdata.get("numberName")+"";
		   			listdateset.add(values);
		    	}
		    }else{
		    	for(Map mapdata:listdata){
		    		String[] values =new String[3];
		    		values[0]=mapdata.get("numberName")+"";
		    		values[1]=mapdata.get(itemName)+"";
		   			values[2]=mapdata.get(lableTickName)+"";
		   			listdateset.add(values);
		    	}
		    }
			if(stp.getTjPictureType().equals("1")){//
				PieDataset dataset=JfreeChartUtil.getDataSetPie(listdateset);
				Map maps=new HashMap();
				maps.put("title", stp.getTjPictureName());
				String fileName=JfreeChartUtil.generatePieChart(request.getSession(),new PrintWriter(out),480,250,maps,dataset,null);
				String graphURL = request.getContextPath() + "/servlet/DisplayChart?filename=" + fileName;
				request.setAttribute("graphURL", graphURL);
				return "showpie";
			}else if(stp.getTjPictureType().equals("2")){//
				CategoryDataset dataset=JfreeChartUtil.getDataSetBar(listdateset);
				Map maps=new HashMap();
				maps.put("title", stp.getTjPictureName());
				maps.put("X", stp.getXName());
				maps.put("Y", stp.getYName());
				String fileName=JfreeChartUtil.generateBarChart(request.getSession(),new PrintWriter(out),700,250,maps,dataset,null);
				String graphURL = request.getContextPath() + "/servlet/DisplayChart?filename=" + fileName;
				request.setAttribute("graphURL", graphURL);
				return "showbar";
			}else if(stp.getTjPictureType().equals("3")){//
				CategoryDataset dataset=JfreeChartUtil.getDataSetBar(listdateset);
				Map maps=new HashMap();
				maps.put("title", stp.getTjPictureName());
				maps.put("X", stp.getXName());
				maps.put("Y", stp.getYName());
				String fileName=JfreeChartUtil.generateBarChartY(request.getSession(),new PrintWriter(out),700,250,maps,dataset,null);
				String graphURL = request.getContextPath() + "/servlet/DisplayChart?filename=" + fileName;
				request.setAttribute("graphURL", graphURL);
				return "showbar";
			}else if(stp.getTjPictureType().equals("4")){//
				CategoryDataset dataset=JfreeChartUtil.getDataSetLine(listdateset);
				Map maps=new HashMap();
				maps.put("title", stp.getTjPictureName());
				maps.put("X", stp.getXName());
				maps.put("Y", stp.getYName());
				String fileName=JfreeChartUtil.generateLineChart(request.getSession(),new PrintWriter(out),700,250,maps,dataset,null);
				String graphURL = request.getContextPath() + "/servlet/DisplayChart?filename=" + fileName;
				request.setAttribute("graphURL", graphURL);
				return "showline";
			}
		} catch (RuntimeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
