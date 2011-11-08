package com.xp.monitor.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.map.ListOrderedMap;
import org.apache.struts2.ServletActionContext;

import com.xp.commonpart.bean.TreeObject;
import com.xp.commonpart.service.BaseService;
import com.xp.commonpart.service.ComQueryService;
import com.xp.commonpart.service.SelectDataService;
import com.xp.commonpart.util.ChartUtil;
import com.xp.commonpart.util.ContextHolder;
import com.xp.commonpart.util.MainTableChart;

public class monitorAction {
	private SelectDataService selectDataService=(SelectDataService) ContextHolder.getBean("selectDataService");
	private BaseService baseService=(BaseService) ContextHolder.getBean("baseService");
	private ComQueryService comqueryService=(ComQueryService) ContextHolder.getBean("comqueryService");
	public String toMonitor(){
		
		return "index";
	}
	
	public String getMenuTreeData(){
		HttpServletRequest request=ServletActionContext.getRequest();
		HttpServletResponse response=ServletActionContext.getResponse();
		String operid=request.getParameter("operid");
		String json="";
		String sql="select * from databaselist";
		List<ListOrderedMap> list=selectDataService.getData(sql);
		List<TreeObject> tlist=new ArrayList();
		for(ListOrderedMap map:list){
			TreeObject to=new TreeObject();
			to.setId(map.get("id").toString());
			to.setName(map.get("ipaddress")+"|"+map.get("dataName")+"|"+map.get("userName"));
			to.setPid("");
			to.setIcons("mnode");
			to.setUrl(map.get("url")+"");
			tlist.add(to);
		}
		String ischecked="0";
		
		json=comqueryService.getTreeJson(tlist,ischecked,operid);
		try {
        	response.setCharacterEncoding("utf-8");
			PrintWriter out=response.getWriter();
			out.print(json.toString());   
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	return null;
	}
	public String toGetAllMonitor(){
		HttpServletRequest request=ServletActionContext.getRequest();
		HttpServletResponse response=ServletActionContext.getResponse();
		String startdate=request.getParameter("startdate");
		String enddate=request.getParameter("enddate");
		String sql="select concat(databaselist.ipaddress,'|',databaselist.dataName) as type, monitorInfo.createDate as date,monitorInfo.status as totalFinish from databaselist,monitorInfo where monitorInfo.databaseId=databaselist.id";
		if(startdate!=null&&startdate.length()>0){
			sql+=" and UNIX_TIMESTAMP(monitorInfo.createDate)>=UNIX_TIMESTAMP('"+startdate+"')";
		}
		if(enddate!=null&&enddate.length()>0){
			sql+=" and UNIX_TIMESTAMP(monitorInfo.createDate)<=UNIX_TIMESTAMP('"+enddate+"')";
		}
		List list=selectDataService.getData(sql);
		MainTableChart mainTableChart=new MainTableChart();
		mainTableChart.setSeriesName("type");
		mainTableChart.setAxisvalueX("date");
		mainTableChart.setAxisvalueY("totalFinish");
		mainTableChart.setAxisNameY("统计");
		String parm=ChartUtil.getDataSetByPar(mainTableChart, list);		
		request.setAttribute("parm", parm);
		request.setAttribute("startdate", startdate);
		request.setAttribute("enddate", enddate);
		return "ztjk";
	}
}
