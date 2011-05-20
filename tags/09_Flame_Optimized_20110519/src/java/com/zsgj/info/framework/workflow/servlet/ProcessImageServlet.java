package com.zsgj.info.framework.workflow.servlet;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jbpm.JbpmContext;
import org.jbpm.graph.def.ProcessDefinition;

import com.zsgj.info.framework.workflow.base.JbpmContextFactory;

public class ProcessImageServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request,HttpServletResponse response)
	throws ServletException, IOException {
		long processDefinitionId = Long.parseLong(request.getParameter("definitionId"));
		JbpmContext jbpmContext = JbpmContextFactory.getJbpmContext();
		ProcessDefinition processDefinition = jbpmContext.getGraphSession().loadProcessDefinition(processDefinitionId);
		byte[] bytes = processDefinition.getFileDefinition().getBytes("processimage.jpg");
		OutputStream out = response.getOutputStream();
		out.write(bytes);
		out.flush();
		JbpmContextFactory.closeJbpmContext();
	}
}