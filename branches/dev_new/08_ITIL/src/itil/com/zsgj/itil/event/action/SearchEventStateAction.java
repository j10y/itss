package com.zsgj.itil.event.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zsgj.info.framework.dao.support.Page;
import com.zsgj.info.framework.util.HttpUtil;
import com.zsgj.info.framework.web.adapter.struts2.BaseAction;
import com.zsgj.itil.actor.entity.SupportGroup;
import com.zsgj.itil.event.entity.Event;
import com.zsgj.itil.event.service.SearchEventStateService;

public class SearchEventStateAction extends BaseAction {
	
	private SearchEventStateService searchEventStateService ;
	
	
	
	public SearchEventStateService getSearchEventStateService() {
		return searchEventStateService;
	}
	public void setSearchEventStateService(
			SearchEventStateService searchEventStateService) {
		this.searchEventStateService = searchEventStateService;
	}



	public void query () throws Exception{
	
		HttpServletRequest request =super.getRequest();
		HttpServletResponse response =super.getResponse();
		
		int pageSize = HttpUtil.getInt(request, "pageSize",10);
		int start = HttpUtil.getInt(request, "start", 0);
		int pageNo=start/pageSize+1;
		String summary = HttpUtil.getString(request, "summary","");
		String eventStatus = HttpUtil.getString(request, "eventStatus");
		String supportGroup = HttpUtil.getString(request, "supportGroup","");
		String createUser = HttpUtil.getString(request, "createUser","");
		String dealuser = HttpUtil.getString(request, "dealUser","");
//		String submitUser = HttpUtil.getString(request, "submitUser","");
//		String eventCisn = HttpUtil.getString(request, "eventCisn","");
		
		Page page = searchEventStateService.getSearchEventStateGridInfo(summary, eventStatus, supportGroup, createUser, dealuser, pageNo, pageSize);
		List dataList = page.list();
		Long total = page.getTotalCount();
		StringBuffer sb = new StringBuffer("{success: true, rowCount:" + total + ",data:[");
		for(int i = 0;i<dataList.size();i++){
			if(i != 0){
				sb.append(",");
			}	
				Object[] obj = (Object[]) dataList.get(i);
				Event event = (Event)obj[0];
				SupportGroup sg = (SupportGroup)obj[1];
				String strDealUserName = "";
				String strCreateUserName = "";
				String strPonderanceName = "";
				String strFrequencyName = "";
				String strCloseDate = "";
				String strSubmitDate = "";
				String strStatus = "";
				String groupName="";
				if(event.getDealuser() !=null){
					strDealUserName = event.getDealuser().getRealName()+"/"+event.getDealuser().getUserName();
				}
				if(event.getCreateUser() !=null){
					strCreateUserName = event.getCreateUser().getRealName()+"/"+event.getCreateUser().getUserName();
				}
				if(event.getPonderance() !=null){
					strPonderanceName = event.getPonderance().getName();
				}
				if(event.getFrequency() !=null){
					strFrequencyName = event.getFrequency().getName();
				}
				if(event.getClosedDate()!=null){
					strCloseDate=event.getClosedDate().toString().substring(0, 16);
				}
				if(event.getSubmitDate()!=null){
					strSubmitDate=event.getSubmitDate().toString().substring(0, 16);
				}
				if(event.getEventStatus()!=null){
					strStatus=event.getEventStatus().getName();
				}
				if(sg!=null){
					groupName=sg.getGroupName();
				}
				sb.append("{");
				sb.append("'id':'"+event.getId()+"',");
				sb.append("'eventCisn':'"+event.getEventCisn()+"',");
				sb.append("'summary':'"+event.getSummary()+"',");
				sb.append("'createUser':'"+strCreateUserName+"',");
				sb.append("'submitDate':'"+strSubmitDate+"',");
				sb.append("'dealuser':'"+strDealUserName+"',");
				sb.append("'eventStatus':'"+strStatus+"',");
				sb.append("'ponderance':'"+strPonderanceName+"',");
				sb.append("'closedDate':'"+strCloseDate+"',");
				sb.append("'frequency':'"+strFrequencyName+"',");
				sb.append("'supportGroup':'"+groupName+"'");
				sb.append("}");
		}
		sb.append("] }");
		response.setContentType("text/plain");
		response.setCharacterEncoding("UTF-8");		
		PrintWriter out;
		try {
			out = response.getWriter();
			out.println(sb.toString());
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}	
	}

}
