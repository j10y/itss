package com.digitalchina.info.framework.workflow.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.jbpm.graph.node.TaskNode;
import org.jbpm.taskmgmt.def.Task;
import org.jbpm.taskmgmt.exe.TaskInstance;

import com.digitalchina.info.appframework.pagemodel.entity.PageModel;
import com.digitalchina.info.framework.context.ContextHolder;
import com.digitalchina.info.framework.context.UserContext;
import com.digitalchina.info.framework.security.entity.UserInfo;
import com.digitalchina.info.framework.security.entity.UserRole;
import com.digitalchina.info.framework.service.Service;
import com.digitalchina.info.framework.workflow.ConfigUnitService;
import com.digitalchina.info.framework.workflow.ContextService;
import com.digitalchina.info.framework.workflow.TaskPageModelService;
import com.digitalchina.info.framework.workflow.TaskService;
import com.digitalchina.info.framework.workflow.entity.ConfigUnitRole;

/**PageModelServlet
 * ���ã���������ڵ��ҵ����õ�PageModel·��
 * @author ���� E-mail: yangtao@info.com
 * @version ����ʱ�䣺Feb 25, 2009 3:10:19 PM ��˵��
 */

public class PageModelServlet extends HttpServlet {

	private TaskService ts = (TaskService) ContextHolder.getBean("taskService");
	private Service service = (Service) ContextHolder.getBean("baseService");
	private TaskPageModelService pageModelService = (TaskPageModelService) ContextHolder.getBean("taskPageModelService");
	private ContextService vm = (ContextService) ContextHolder.getBean("contextService");
	private ConfigUnitService cs = (ConfigUnitService) ContextHolder.getBean("configUnitService");

	/**
	 * Constructor of the object.
	 */
	public PageModelServlet() {
		super();
	}

	/**
	 * Destruction of the servlet. <br>
	 */
	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}

	/**
	 * The doGet method of the servlet. <br>
	 * 
	 * This method is called when a form has its tag value method equals to get.
	 * 
	 * @param request
	 *            the request send by the client to the server
	 * @param response
	 *            the response send by the server to the client
	 * @throws ServletException
	 *             if an error occurred
	 * @throws IOException
	 *             if an error occurred
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);

	}

	/**
	 * The doPost method of the servlet. <br>
	 * 
	 * This method is called when a form has its tag value method equals to
	 * post.
	 * 
	 * @param request
	 *            the request send by the client to the server
	 * @param response
	 *            the response send by the server to the client
	 * @throws ServletException
	 *             if an error occurred
	 * @throws IOException
	 *             if an error occurred
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		request.setCharacterEncoding("utf-8");
		String browseFlag = "";
		String formurl=null;
		String taskId = request.getParameter("taskId");
		String goStartState = request.getParameter("goStartState");
		TaskInstance t = ts.getTaskById(Long.valueOf(taskId));//�õ���ǰ����ʵ��
		//add by lee for �������ʼ���������������ʱ�Խ������ in 20100618 begin
		if(!t.isOpen()){
			formurl="/infoAdmin/workflow/configPage/over.jsp";
		}else{
		//add by lee for �������ʼ���������������ʱ�Խ������ in 20100618 end	
			if(goStartState!=null&&!"".equals(goStartState)&&!"null".equals(goStartState)&&!"undefined".equals(goStartState)){
				String[] startState = goStartState.split(",");
				String nodeId = startState[0];
				String vProcessId = startState[1];	
				String processId = startState[2];
				PageModel pageModel = pageModelService.findPageModelByVritualProcessIdAndNodeId(Long.valueOf(vProcessId), Long.valueOf(nodeId));
				if(pageModel!=null){
					formurl=pageModel.getPagePath()+"?taskId="+taskId;
				}
				//��ɫû��pageModel��û��Ĭ��pageModel,��ʹ��ϵͳĬ�Ͻ���
				if(pageModel==null){
					formurl="/infoAdmin/workflow/configPage/defaultPageModel.jsp?taskId="+taskId;
				}
				
				request.getSession().setAttribute("vProcessId", vProcessId);
				request.getSession().setAttribute("taskName", "��ؽڵ�");
				request.getSession().setAttribute("nodeId", nodeId);
				request.getSession().setAttribute("processId", processId);
			}else{
				Map mapVar = vm.listVariablesByTaskId(Long.valueOf(taskId));
				Task task = t.getTask();
				String taskName = task.getName();
				PageModel pageModel = pageModelService.getPageModel(task,mapVar);
				//add by guangsa for specialAuditPersonInRole in 20090823 begin
				UserInfo u = UserContext.getUserInfo();
				Boolean browsePerson = this.auditPersonInRole(task,mapVar);
				if(browsePerson){
					browseFlag = "1";//�����ǲ鿴�ˣ�����������
				}
				//add by guangsa for specialAuditPersonInRole in 20090823 end
				if(pageModel!=null){
					formurl=pageModel.getPagePath()+"?taskId="+taskId+"&taskName="+taskName+"&browseFlag="+browseFlag;
				}
				//��ɫû��pageModel��û��Ĭ��pageModel,��ʹ��ϵͳĬ�Ͻ���
				if(pageModel==null){
					formurl="/infoAdmin/workflow/configPage/defaultPageModel.jsp?taskId="+taskId+"&taskName="+taskName+"&browseFlag="+browseFlag;
				}
				request.setAttribute("taskId", taskId);
				request.setAttribute("taskName", taskName);
				request.setAttribute("browseFlag", browseFlag);
			}
		}
		RequestDispatcher rd = getServletContext().getRequestDispatcher(formurl);   
        rd.forward(request,response);   
		response.setContentType("text/plain");
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		out.flush();
		out.close();
	}
	/**
	 * �жϵ������Ƿ��ǽ�ɫ�е�������
	 * @param u
	 * @return
	 */
	public boolean auditPersonInRole(Task  task,Map mapVar){
		
		UserInfo u = UserContext.getUserInfo();
		TaskNode tn = task.getTaskNode();
		Long nodeId = tn.getId();
		Long virProcessId=(Long)mapVar.get("VIRTUALDEFINITIONINFO_ID");
		ConfigUnitRole unitRole = cs.findConfigUnitRole(String.valueOf(virProcessId), String.valueOf(nodeId));
		boolean browsePerson = cs.findConfigUnitRoleTableByConfigUnitRole(unitRole, u);//�ж��Ƿ��ǲ鿴��
		return browsePerson;
	}
	/**
	 * Initialization of the servlet. <br>
	 * 
	 * @throws ServletException
	 *             if an error occurs
	 */
	public void init() throws ServletException {
		// Put your code here
	}

}
