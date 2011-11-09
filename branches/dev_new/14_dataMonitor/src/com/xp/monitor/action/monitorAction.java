package com.xp.monitor.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
import com.xp.commonpart.util.DateUtil;
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
		}else{
			startdate=DateUtil.convertDateTimeToString(DateUtil.addMinutes(new Date(), -120));
			sql+=" and UNIX_TIMESTAMP(monitorInfo.createDate)>=UNIX_TIMESTAMP('"+ startdate+"')";
		}
		if(enddate!=null&&enddate.length()>0){
			sql+=" and UNIX_TIMESTAMP(monitorInfo.createDate)<=UNIX_TIMESTAMP('"+enddate+"')";
		}else{
			enddate=DateUtil.convertDateTimeToString(new Date());
			sql+=" and UNIX_TIMESTAMP(monitorInfo.createDate)<=UNIX_TIMESTAMP('"+ enddate+"')";
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
	public String getPerformance(){
		HttpServletRequest request=ServletActionContext.getRequest();
		HttpServletResponse response=ServletActionContext.getResponse();
		String id=request.getParameter("id");
		request.setAttribute("id", id);
		return "performanece";
	}
	public String getSqlPerformance(){
		HttpServletRequest request=ServletActionContext.getRequest();
		HttpServletResponse response=ServletActionContext.getResponse();
		String id=request.getParameter("id");
		Map resultMap= comqueryService.connectDataBaseByBaseId(id,"1");
		if(resultMap==null||resultMap.isEmpty()){
			try {
	        	response.setCharacterEncoding("utf-8");
				PrintWriter out=response.getWriter();
				out.print("数据连接失败或没有配置性能指标");   
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		String json="";
		Set rekey=resultMap.keySet();
		Iterator it=rekey.iterator();
		while(it.hasNext()){
			Object key=it.next();
			Map pimap=(Map) resultMap.get(key.toString());
			List titlelist=(List) pimap.get("title");
			List<List> dataList=(List) pimap.get("data");
			Map alarmmap=(Map) pimap.get("alarm");
			json+="<table class='MzTreeViewRow' style='font:13px;text-align:center;'>" ;
			json+="<tr><td class='MzTreeViewCell3' colspan='"+titlelist.size()+"'>"+key+"</td></tr>" ;
			json+="<tr>" ;
			for(Object columnName:titlelist){
				json+="<td class='MzTreeViewCell2' >"+columnName+"</td>" ;
			}
			json+="</tr>" ;
			for(int i=0;i<dataList.size();i++){
				List subdataList=dataList.get(i);
				json+="<tr>" ;
				for(int j=0;j<subdataList.size();j++){
					Object datavalue=subdataList.get(j);
					String color="black";
					if(alarmmap!=null&&!alarmmap.isEmpty()&&alarmmap.get(i+1)!=null&&Integer.parseInt(alarmmap.get(i+1)+"")==j+1){
						color="red";
					}
					json+="<td class='MzTreeViewCell2' style='color:"+color+"'>"+datavalue+"</td>" ;
				}
				json+="</tr>" ;
			}
			json+="</table>";
			
		}
		try {
        	response.setCharacterEncoding("utf-8");
			PrintWriter out=response.getWriter();
			out.print(json);   
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
