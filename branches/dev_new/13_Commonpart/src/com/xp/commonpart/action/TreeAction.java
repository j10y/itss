package com.xp.commonpart.action;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.xp.commonpart.service.TreeService;
import com.xp.commonpart.util.ContextHolder;

public class TreeAction {
	private TreeService treeService =(TreeService) ContextHolder.getBean("treeService");;
	
	public String getTree() {
		HttpServletRequest request=ServletActionContext.getRequest();
		HttpServletResponse response=ServletActionContext.getResponse();
		String ischecked=request.getParameter("ischecked");
		String treetype=request.getParameter("treetype");
		String nodeid=request.getParameter("nodeid");
		String json="";
		if(nodeid!=null&&nodeid.length()>0){
			json=treeService.getTreeByParams(ischecked, treetype,nodeid);
		}else{
			json=treeService.getTreeByParams(ischecked, treetype);
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
	public String showTreeByNodes() {
		HttpServletRequest request=ServletActionContext.getRequest();
		HttpServletResponse response=ServletActionContext.getResponse();
		String ischecked=request.getParameter("ischecked");
		String treetype=request.getParameter("treetype");
		String nodeid=request.getParameter("nodeid");
		String treename=request.getParameter("treename");
		String json="";
		json=treeService.getTreeByParams(ischecked, treetype,nodeid,treename);
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
	
	public String toTree() {
		return "success";
	}
	
}
