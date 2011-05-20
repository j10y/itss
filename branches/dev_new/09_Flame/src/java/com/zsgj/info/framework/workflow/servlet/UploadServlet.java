
package com.zsgj.info.framework.workflow.servlet;

import java.io.*;
import java.util.Iterator;
import java.util.List;
import java.util.zip.ZipInputStream;
import javax.servlet.http.*;
import org.apache.commons.fileupload.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jbpm.JbpmContext;
import org.jbpm.graph.def.ProcessDefinition;

import com.zsgj.info.framework.context.ContextHolder;
import com.zsgj.info.framework.service.Service;
import com.zsgj.info.framework.workflow.base.JbpmContextFactory;
import com.zsgj.info.framework.workflow.entity.DefinitionInfo;

public class UploadServlet extends HttpServlet
{

	private static final long serialVersionUID = 1L;
	private static Log log;

	private Service service = (Service) ContextHolder.getBean("baseService");
	public UploadServlet()
	{
	}

	public void service(HttpServletRequest request, HttpServletResponse response)
		throws IOException
	{
		response.setContentType("text/plain");
		response.getWriter().println(handleRequest(request));
	}

	public void printInput(HttpServletRequest request)
		throws IOException
	{
		InputStream inputStream = request.getInputStream();
		StringBuffer buffer = new StringBuffer();
		int read;
		while ((read = inputStream.read()) != -1) 
			buffer.append((char)read);
		log.debug(buffer.toString());
	}

	@SuppressWarnings("deprecation")
	private String handleRequest(HttpServletRequest request)
	{
		if (!FileUpload.isMultipartContent(request))
		{
			log.debug("Not a multipart request");
			return "Not a multipart request";
		}
		try {
			Iterator iterator;
			DiskFileUpload fileUpload = new DiskFileUpload();
			List list = fileUpload.parseRequest(request);
			iterator = list.iterator();
			if (!iterator.hasNext()) {				
				log.debug("No process file in the request");
				return "No process file in the request";
			}
			else {
				FileItem fileItem;
				fileItem = (FileItem)iterator.next();
				if (fileItem.getContentType().indexOf("application/x-zip-compressed") == -1&&
						fileItem.getContentType().indexOf("application/octet-stream")==-1) {	
					log.debug("Not a process archive");
					return "Not a process archive";
				}
				else {
					return doDeployment(fileItem);
				}
			}
		} catch (FileUploadException e) {
			e.printStackTrace();
			return null;
		}		
	}

	/**
	 * 发布ProcessDefinition时，保存到DefinitionInfo中
	 * TODO
	 * Feb 17, 2009 By Administrator
	 * @param fileItem
	 * @return TODO
	 */
	private String doDeployment(FileItem fileItem)
	{
		ProcessDefinition processDefinition = null;
		try {
			ZipInputStream zipInputStream = new ZipInputStream(fileItem.getInputStream());
			JbpmContext jbpmContext = JbpmContextFactory.getJbpmContext();
			processDefinition = ProcessDefinition.parseParZipInputStream(zipInputStream);
			log.debug("Created a processdefinition : " + processDefinition.getName());
			jbpmContext.deployProcessDefinition(processDefinition);
			DefinitionInfo definitionInfo=new DefinitionInfo(processDefinition);
			definitionInfo.setProcessDefinitionId(processDefinition.getId());
			service.save(definitionInfo);
			zipInputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		finally {
			JbpmContextFactory.closeJbpmContext();
		}
		return "Deployed archive " + processDefinition.getName() + " successfully";
	}

	static 
	{
		log = LogFactory.getLog(org.jbpm.webapp.servlet.UploadServlet.class);
	}
}