<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<%@ page language="java" import="java.util.*" pageEncoding="gbk"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<HTML>
	<HEAD>
		<TITLE></TITLE>	
		
	</HEAD>
<body style="width:960px">
<form action="${pageContext.request.contextPath}/monitor_reSetJob.action">
<table>
<tr>
<td></td>
<td><input type="text" id="ctrigger" name="ctrigger" ></input></td>
<td></td>
<td><input type="text" id="cronexpress" name="cronexpress" ></input></td>
<td>
<input type="submit" value="Ìá½»">
</td>
</tr>
</table>
</form>
</body>	
</HTML>
