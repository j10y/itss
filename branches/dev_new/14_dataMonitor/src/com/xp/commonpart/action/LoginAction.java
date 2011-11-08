package com.xp.commonpart.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.xp.commonpart.service.BaseService;
import com.xp.commonpart.service.SelectDataService;
import com.xp.commonpart.util.ContextHolder;
import com.xp.commonpart.util.DES;

public class LoginAction {
	private SelectDataService selectDataService=(SelectDataService) ContextHolder.getBean("selectDataService");
	private BaseService baseService=(BaseService) ContextHolder.getBean("baseService");
	public String toLogout(){
		HttpServletRequest request=ServletActionContext.getRequest();
		request.getSession().removeAttribute("usermap");
		request.getSession().removeAttribute("sysconfigmap");
		request.getSession().invalidate();
		Map sysconfigmap=null;
		if(request.getSession().getAttribute("sysconfigmap")!=null){
			sysconfigmap=(Map) request.getSession().getAttribute("sysconfigmap");
		}else{
			String configsql="select * from sys_sec_pagconfig";
			List list=selectDataService.getData(configsql);
			if(list!=null&&list.size()>0){
				sysconfigmap=(Map) list.get(0);
				request.getSession().setAttribute("sysconfigmap", sysconfigmap);
			}
		}
		return "login";
	}
	public String toLogin(){
		HttpServletRequest request=ServletActionContext.getRequest();
		String flag="true";
		String username=request.getParameter("login_username");
		String passwd=request.getParameter("login_password");
		Map sysconfigmap=null;
		if(request.getSession().getAttribute("sysconfigmap")!=null){
			sysconfigmap=(Map) request.getSession().getAttribute("sysconfigmap");
		}else{
			String configsql="select * from sys_sec_pagconfig";
			List list=selectDataService.getData(configsql);
			if(list!=null&&list.size()>0){
				sysconfigmap=(Map) list.get(0);
				request.getSession().setAttribute("sysconfigmap", sysconfigmap);
			}
		}
		Map usermap=null;
		if(request.getSession().getAttribute("usermap")!=null){
			usermap=(Map) request.getSession().getAttribute("usermap");
		}else{
			if(username!=null&&username.length()>0&&passwd!=null&&passwd.length()>0){
				try {
					DES des=new DES();
					passwd=des.encrypt(passwd);
					String sql="select * from sys_sec_userinfo where username='"+username+"' and passwd='"+passwd+"'";
					List list=selectDataService.getData(sql);
					if(list!=null&&list.size()>0){
						usermap=(Map) list.get(0);
					}else{
						flag="false";
						request.setAttribute("error", "用户名密码不正确！");
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}else{
				flag="false";
			}
			
		}
		if(flag.equals("false")){
			return "fault";
		}
		//usermap.put("username","tongjp");
		request.getSession().setAttribute("usermap", usermap);
		return "sucess";
	}
	public String getMenuList(){
		HttpServletRequest request=ServletActionContext.getRequest();
		HttpServletResponse response=ServletActionContext.getResponse();
		String username=request.getParameter("username");
		String json="";
		try {
			List list1=selectDataService.getData("select sys_sec_role.operateid from sys_sec_userinfo,sys_sec_role where sys_sec_role.id in (sys_sec_userinfo.role) and sys_sec_userinfo.username='"+username+"'");
			String operateid="0";
			if(list1!=null&&list1.size()>0){
				operateid=((Map)list1.get(0)).get("operateid").toString();
				String sql="select * from sys_sec_operate where sys_sec_operate.id in ("+operateid+") and sys_sec_operate.level='1'";
				List<Map> list=selectDataService.getData(sql);
				json = "[";
				for(Map map:list){
					json+="{'id':'"+map.get("id")+"','operatename':'"+map.get("operatename")+"','url':'"+map.get("url")+"','icon':'"+map.get("icon")+"'},";
				}
				if(list.size()>0){
					json=json.substring(0,json.length()-1);
				}
				json+="]";
			}
		} catch (RuntimeException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
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
