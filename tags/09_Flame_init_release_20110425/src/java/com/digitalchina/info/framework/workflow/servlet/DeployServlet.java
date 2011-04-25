package com.digitalchina.info.framework.workflow.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.util.zip.ZipInputStream;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jbpm.JbpmContext;
import org.jbpm.graph.def.ProcessDefinition;

import com.digitalchina.info.framework.workflow.base.JbpmContextFactory;

public class DeployServlet extends HttpServlet
{

	private static final long serialVersionUID = 1L;
	private static Log log;

	public DeployServlet()
	{
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException
	{
		String archive = request.getParameter("archive");
		log.debug("deploying archive " + archive);
		PrintWriter writer = response.getWriter();
		try
		{
			URL archiveUrl = new URL(archive);
			ZipInputStream zis = new ZipInputStream(archiveUrl.openStream());
			JbpmContext jbpmContext = JbpmContextFactory.getJbpmContext();
			ProcessDefinition processDefinition = ProcessDefinition.parseParZipInputStream(zis);
			jbpmContext.deployProcessDefinition(processDefinition);
			zis.close();
			String s = "Deployed archive " + archive + " successfully. ";
			s += "<a href='javascript:history.goback()'>back</a>";
			writer.write(s);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			writer.write("Deploying archive " + archive + " failed");
		}
		finally {
			JbpmContextFactory.closeJbpmContext();
		}
	}

	static 
	{
		log = LogFactory.getLog(org.jbpm.webapp.servlet.DeployServlet.class);
	}
}