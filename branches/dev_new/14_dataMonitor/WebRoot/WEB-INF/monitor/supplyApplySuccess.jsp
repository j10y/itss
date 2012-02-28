<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<HTML>
	<HEAD>
		<TITLE></TITLE>	
		<style type="text/css">
    	body {width:960px;margin:0 auto;}
		.table-info td { padding:5px; line-height:24px; font-size:14px;background-color=#FFFFFF;}
		.table-info table td  { border-bottom:1px solid #e0e0e0;}
		img{ border:0;}
		.frame{ 
		    width:800px; 
		    margin:0 auto;
			padding:0;
			text-align:left;
		}
		.f_name{font-size:22px; color:red;}
		h2{ font-size:22px; color:#0056AA; margin:0;}
		.gap{ height:8px; background: #999999; margin: 8px 0; font-size:1px;}
		</style>	
	</HEAD>
<body  >
<table align="center"><tr><td>
<div class="frame">
<img src="${pageContext.request.contextPath}/Images/logo.gif" border="0"/>
  <div class="gap"></div>
  <div align="center" >
  <c:if test="${result eq '1'}">
  感谢您提交入驻申请，我们会仔细审阅。<br/>
入驻咨询：（010）59578755 /  59578757  /  59578771<br/>
(周一至周五 10:00-18:00)<br/>
期待您的加入，共同开创美好明天 <br/>
  </c:if>
  <c:if test="${result eq 2}">
  提交失败，请返回提交页重新提交！
  </c:if>
  </div>
</div>
</td></tr></table>
</body>	
</HTML>
