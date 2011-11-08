package com.xp.commonpart.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;
import com.xp.commonpart.Page;
import com.xp.commonpart.Response;
import com.xp.commonpart.bean.SysTemUserLog;
import com.xp.commonpart.service.BaseService;
import com.xp.commonpart.service.SelectDataService;
import com.xp.commonpart.util.ContextHolder;
import com.xp.commonpart.util.json.JsonObjectUtil;

public class SampleAction extends ActionSupport{
	private SelectDataService selectDataService=(SelectDataService) ContextHolder.getBean("selectDataService");
	private BaseService baseService=(BaseService) ContextHolder.getBean("baseService");
	public String getPage1(){
		HttpServletRequest request=ServletActionContext.getRequest();
		String sql="Select * from SYS_SEC_USERLOG";
		Page page=selectDataService.getListForPage(request, sql);
		request.setAttribute("page", page);
		return "page1";
	}
	public String getPage2(){
		HttpServletRequest request=ServletActionContext.getRequest();
		LinkedHashMap proMap=new LinkedHashMap();
		//proMap.put("operateUserName", "0");
		LinkedHashMap orderMap=new LinkedHashMap();
		//proMap.put("operateUserName", "asc");
		Page page=baseService.findObjectPageByParamAndOrder(SysTemUserLog.class, proMap, orderMap, request);
		request.setAttribute("page", page);
		return "page2";
	}
	public String getPage3(){
		return "page3";
	}
	public void toPage3(){
		HttpServletRequest request=ServletActionContext.getRequest();
		HttpServletResponse response=ServletActionContext.getResponse();
		LinkedHashMap proMap=new LinkedHashMap();
		//proMap.put("operateUserName", "0");
		LinkedHashMap orderMap=new LinkedHashMap();
		//proMap.put("operateUserName", "asc");
		Page page=baseService.findObjectPageByParamAndOrder(SysTemUserLog.class, proMap, orderMap, request);
		List list=(List)page.getData();
		String json="{'total':"+page.getTotal()+",'rows':[";
		for(int i=0;i<list.size();i++){
			SysTemUserLog st=(SysTemUserLog) list.get(i);
			json+="{'id':'"+st.getId()+"','operateUserName':'"+st.getOperateUserName()+"','operateDate':'"+st.getOperateDate()+"','operateIP':'"+st.getOperateIP()+"'},";
		}
		if(list!=null&&list.size()>0){
			json=json.substring(0,json.length()-1);
		}
		json+="]}";
		json=json.replaceAll("'", "\"");
		try {
        	response.setCharacterEncoding("utf-8");
			PrintWriter out=response.getWriter();
			out.print(json); 
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public String getPage4(){
		return "page4";
	}
	public void toPage4(){
		HttpServletRequest request=ServletActionContext.getRequest();
		HttpServletResponse response=ServletActionContext.getResponse();
		String sql="Select * from SYS_SEC_USERLOG";
		Page page=selectDataService.getListForPage(request, sql);
		List list=(List)page.getData();
		String json="{'total':"+page.getTotal()+",'rows':[";
		for(int i=0;i<list.size();i++){
			Map st=(Map) list.get(i);
			json+="{'id':'"+st.get("id")+"','OPERATEUSERNAME':'"+st.get("OPERATEUSERNAME")+"','OPERATEDATE':'"+st.get("OPERATEDATE")+"','OPERATEIP':'"+st.get("OPERATEIP")+"'},";
		}
		if(list!=null&&list.size()>0){
			json=json.substring(0,json.length()-1);
		}
		json+="]}";
		json=json.replaceAll("'", "\"");
		try {
        	response.setCharacterEncoding("utf-8");
			PrintWriter out=response.getWriter();
			out.print(json); 
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public String exportData(){
		HttpServletRequest request=ServletActionContext.getRequest();
		HttpServletResponse response=ServletActionContext.getResponse();
		String sql="Select * from SYS_SEC_USERLOG";
		List list=selectDataService.getData(sql);
		//List list=(List)page.getData();
		String json="";
		String fileRootPath=request.getRealPath("exportFile");
		json=baseService.exportData(fileRootPath, "文件", "文件", new String[]{"operateUserName","operateDate","operateIP"}, new String[]{"OPERATEUSERNAME","OPERATEDATE","OPERATEIP"}, list, "maplist");
		try {
        	response.setCharacterEncoding("utf-8");
			PrintWriter out=response.getWriter();
			out.print(json); 
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
