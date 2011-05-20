package com.zsgj.info.framework.workflow.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jbpm.taskmgmt.exe.TaskInstance;

import com.zsgj.info.framework.context.ContextHolder;
import com.zsgj.info.framework.workflow.TaskService;

/**PageModelServlet
 * 作用：根据任务节点找到配置的PageModel路径
 * @author 杨涛 E-mail: yangtao@info.com
 * @version 创建时间：Feb 25, 2009 3:10:19 PM 类说明
 */

@SuppressWarnings("serial")
public class WorkflowPageServlet extends HttpServlet {

	private TaskService ts = (TaskService) ContextHolder.getBean("taskService");
//	private Service service = (Service) ContextHolder.getBean("baseService");
//	private ContextService vm = (ContextService) ContextHolder.getBean("contextService");


	/**
	 * Constructor of the object.
	 */
	public WorkflowPageServlet() {
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

		String taskId = request.getParameter("taskId");
//		Map mapVar = vm.listVariablesByTaskId(Long.valueOf(taskId));
		TaskInstance t = ts.getTaskById(Long.valueOf(taskId));//得到当前任务实例
		String nodeDesc = t.getToken().getNode().getDescription();
		String formurl = "";
		if("orderAssign".equals(nodeDesc)){
			formurl = "/cmccb2b/order/orderConfirmAudit/orderConfirmAudit.jsp";
		}else if("orderOperate".equals(nodeDesc)){
			formurl = "/cmccb2b/order/orderConfirmAudit/orderConfirmOperate.jsp";
		}
		request.setAttribute("taskId", taskId);
		RequestDispatcher rd = getServletContext().getRequestDispatcher(formurl);   
        rd.forward(request,response);   
		response.setContentType("text/plain");
		PrintWriter out = response.getWriter();
		out.flush();
		out.close();
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
