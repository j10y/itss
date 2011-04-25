package com.digitalchina.info.framework.workflow.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.digitalchina.info.framework.context.ContextHolder;
import com.digitalchina.info.framework.util.HttpUtil;
import com.digitalchina.info.framework.workflow.ContextService;
import com.digitalchina.info.framework.workflow.TaskService;
import com.digitalchina.info.framework.workflow.base.FormHelper;
import com.digitalchina.info.framework.workflow.info.TaskInfo;

public class TaskServlet extends HttpServlet {
	
	/**
	 * @Field long serialVersionUID 
	 */
	private static final long serialVersionUID = -3259482508784959501L;

	/**
	 * Constructor of the object.
	 */
	public TaskServlet() {
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
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
//		request.setCharacterEncoding("GBK");
		//response.setContentType("text/plain");
		response.setCharacterEncoding("GBK");
		String strTaskId = request.getParameter("id");
		String closeParent = request.getParameter("closeParent");
		long taskId = 0;		
		if(strTaskId!=null&&!strTaskId.equals("")) {
			taskId = Long.parseLong(strTaskId);
		}
		
		TaskService tm = (TaskService)ContextHolder.getBean("taskService");
		ContextService vm = (ContextService)ContextHolder.getBean("contextService");
		
		TaskInfo ti = tm.getTaskInfo(taskId);
		String formurl = FormHelper.findForm(ti.getDefinitionName(),ti.getNodeName()).trim();
		String done = request.getParameter("done");
		
		if(done==null) {//执行前显示	
			if(request.getParameter("system")!=null) {//管理界面
				formurl = "/infoAdmin/workflow/defaultForm.jsp";
			}
			RequestDispatcher rd = getServletContext().getRequestDispatcher(formurl);   
            rd.forward(request,response);   
		}
		else {//执行任务
			Enumeration para = request.getParameterNames();
			//如果上下文中有对应名称的变量，则把页面数据传进去
			Map mapVar = vm.listVariablesByTaskId(taskId);
			for (;para.hasMoreElements();) {
				String varName = (String)para.nextElement();
				if(mapVar.containsKey(varName)) {
					String v = request.getParameter(varName);
					//字符集转换
					v = new String(v.getBytes("iso8859-1"),"gbk");
					vm.setVariableByTaskId(taskId, varName, v);
				}
		    }
			tm.execute(taskId);
			response.setContentType("text/plain");
			response.setCharacterEncoding("gbk");
			PrintWriter out = response.getWriter();		
			if(closeParent==null) {
				out.println(new String(getCloseHtml().getBytes("gbk"),"iso8859-1"));
			}
			else {
				out.println(getCloseAllHtml());
			}
			out.flush();
			out.close();
		}
	}

	/**
	 * The doPost method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to post.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		doGet(request,response);
		
//		response.setContentType("text/plain");
//		PrintWriter out = response.getWriter();
//		out
//				.println("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">");
//		out.println("<HTML>");
//		out.println("  <HEAD><TITLE>A Servlet</TITLE></HEAD>");
//		out.println("  <BODY>");
//		out.print("    This is ");
//		out.print(this.getClass());
//		out.println(", using the POST method");
//		out.println("  </BODY>");
//		out.println("</HTML>");
//		out.flush();
//		out.close();
	}

	/**
	 * Initialization of the servlet. <br>
	 *
	 * @throws ServletException if an error occure
	 */
	public void init() throws ServletException {
		// Put your code here
	}
	
	String getCloseHtml() {
		String html = "";
		
		html += "<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">";
		html += "<HTML>";
		html += "<Script language=\"javaScript\">";
		html += "if(window.opener){";
		html += "	window.opener.location.reload();";
		html += "}";
		html += "window.close();";
		html += "</Script>";
		html += "</HTML>";
		
		return html;
	}
	

	

	String getCloseAllHtml() {
		String html = "";		
		//html += "<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">";
		//html += "<META http-equiv=Content-Type content=\"text/html; charset=GBK\">";
		html += "<HTML>";
		html += "<br/><br/><br/>";
		html += "<table align=\"center\">";
		html += "<tr><td align=\"center\" height=\"40\">";
		html += "<font size=\"2\">成功提交！</font>";
		html += "</td></tr>";
		html += "<tr><td align=\"center\" height=\"40\">";
		html += "<a href=\"javascript:closeAll();\"><font size=\"2\">关闭窗口</font></a>";
		html += "</td></tr>";
		html += "</table>";
		html += "<script language=\"javascript\">";
//		html += "if(window.opener.opener){";
//		html += "	window.opener.opener.location.reload();";
//		html += "}";
		html += "function closeAll(){";
		html += "if(window.opener){";
		html += "	window.opener.close();";
		html += "}";
		html += "window.close();";
		html += "};";
		html += "</script>";
		html += "</HTML>";
		
		return html;
	}
}
