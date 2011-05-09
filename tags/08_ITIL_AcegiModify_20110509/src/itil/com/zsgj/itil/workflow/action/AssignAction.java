package com.zsgj.itil.workflow.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zsgj.info.framework.context.UserContext;
import com.zsgj.info.framework.security.entity.UserInfo;
import com.zsgj.info.framework.web.adapter.struts2.BaseAction;
import com.zsgj.info.framework.workflow.entity.TaskPreAssign;

/**
 * 工作流指派
 * @Class Name AssignAction
 * @Author lee
 * @Create In Dec 2, 2009
 */
public class AssignAction extends BaseAction{
	
	/**
	 * 获取登录人待审信息
	 * @Methods Name showMyWorkmates
	 * @Create In Dec 2, 2009 By lee
	 * @return
	 * @throws Exception String
	 */
	public String showMyWorkmates()	throws Exception {
		HttpServletRequest request = super.getRequest();
		HttpServletResponse response = super.getResponse();
		String json = "";
		List<TaskPreAssign> taskPreAssign = super.getService().findAll(TaskPreAssign.class);
		UserInfo curUser = UserContext.getUserInfo();//获取当前登录人
		for(TaskPreAssign tpa : taskPreAssign){
			if(tpa.getActorId().equals(curUser.getUserName())){
				json += "{'id':"+tpa.getId()+",'userName':'"+tpa.getActorName()+"','proxyName':'"+tpa.getProxyName()+"','proxyStartDate':'"+tpa.getProxyBegin()+"','proxyEndDate':'"+tpa.getProxyEnd()+"'},";
			}
		}
		if(json.endsWith(",")){
			json = "{data:[" + json.substring(0, json.length()-1) + "]}";
		}else{
			json = "{data:[" + json + "]}";
		}
		try {			
			response.setCharacterEncoding("utf-8");
			PrintWriter pw = response.getWriter();	
			pw.write(json);		
			} catch (IOException e) {
			e.printStackTrace();
			}
		return null;
	}
}
