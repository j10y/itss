<%@ page language="java" import="java.util.*" pageEncoding="GB18030"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<%@ page import="com.digitalchina.info.framework.workflow.TaskService" %>
<%@ page import="org.jbpm.taskmgmt.exe.TaskInstance" %>
<%@ page import="com.digitalchina.info.framework.context.ContextHolder" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'executetask.jsp' starting page</title>
    <%@include file="/includefiles.jsp"%>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

  </head>
  
  <body>
  <form action="/pmcFramework/jbpm/executetask.jsp">
  	variables:<br/>
    <%
        String done = request.getParameter("done");
        String v = request.getParameter("v");
        String id = request.getParameter("id");
        if(done==null){
        	if(v!=null&&!v.trim().equals("")){
        		v = v.substring(1,v.length()-1);
        		String[] va = v.split(",");
        		for(int i=0;i<va.length;i++){
		        	String name = va[i].split("=")[0];
		        	String value = va[i].split("=")[1];
  		%>  		    
    	    	<%=name%>:<input name="<%=name%>" value="<%=value%>"><br/>   		    
    		    <%
   		        }
   			}
   		%>
    	<input type="submit"><br/>  
    	<input type="hidden" name="id" value="<%=id%>">
    	<input type="hidden" name="done" value="go">
    	</form> 	
    	<%
   	    }
   	    else{
   	    	TaskService pmi = (TaskService)ContextHolder.getBean("taskManager");
   	    	pmi.execute(Long.parseLong(id));
 	    	%>
    ok!    
    <%
    	}
    %>
  </body>
</html>
