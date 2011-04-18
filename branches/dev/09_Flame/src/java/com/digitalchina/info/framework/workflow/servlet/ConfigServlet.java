package com.digitalchina.info.framework.workflow.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ConfigServlet extends HttpServlet
{

	private static final long serialVersionUID = 1L;
	private static Log log;

	public ConfigServlet()
	{
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException
	{
		String method = request.getParameter("method");
		log.debug("deploying archive " + method);
		String json = "";
		if(method==null||method.equals("")) {
			json = "{success:false,message:'method parameter is empty.'}";
		}
		else if(method.equals("listNodeTypes")) {
			json = listNodeTypes();
		}
		else if(method.equals("listHandlers")) {
			json = listHandlers();
		}
		else if(method.equals("listConfigUnits")) {
			json = listConfigUnits();
		}
		PrintWriter writer = response.getWriter();
		writer.println(json);
		writer.flush();
		writer.close();		
	}
	
	private String listNodeTypes() {
		//������Ϣ
		String[][] nodeTypes = new String[2][];
		String[] nodeType1 = new String[] {"node","��ͨ�ڵ�"};
		nodeTypes[0] = nodeType1;
		String[] nodeType2 = new String[] {"taskNode","����ڵ�"};
		nodeTypes[1] = nodeType2;
		
		//����json
		String json = "";
		for(String[] nodeType: nodeTypes) {
			json += "{'nodeName','"+nodeType[0]+"'},";
		}
		if(json.endsWith(",")) {
			json = json.substring(0,json.length()-1);
		}
		return json;
	}
	
	private String listHandlers() {
		//������Ϣ
		String[][] handlers = new String[3][];
		String[] handler1 = new String[] {"actionHandler","��ͨ�ڵ�"};
		handlers[0] = handler1;
		String[] handler2 = new String[] {"assignmentHandler","����ڵ�"};
		handlers[1] = handler2;
		String[] handler3 = new String[] {"taskPageHandler","����ڵ�"};
		handlers[2] = handler3;
		
		//����json
		String json = "";
		for(String[] handler: handlers) {
			json += "{'nodeName','"+handler[0]+"'},";
		}
		if(json.endsWith(",")) {
			json = json.substring(0,json.length()-1);
		}
		return json;
	}
	
	private String listConfigUnits() {
		String json = "";
		return json;
	}

	static 
	{
		log = LogFactory.getLog(org.jbpm.webapp.servlet.DeployServlet.class);
	}
}