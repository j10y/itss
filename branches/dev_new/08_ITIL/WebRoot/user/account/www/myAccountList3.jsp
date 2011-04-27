<%@page contentType="text/html;charset=gbk" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<HTML>
<head>
<style type="text/css" media="screen">
body{font-size:12px;}
.title{background:url(images/bg.gif) no-repeat 1px 2px;padding:1px;height:18px;line-height:18px;color:#fff;text-indent:20px;}
td{text-align:center;font:smaller Verdana,"Andale Mono",Courier,"Courier New",monospace;font-size:10px;}
th{font:smaller Verdana,"宋体",Courier,"Courier New",monospace;font-weight:bold;font-size:12px;}
a.nomalLink,a.nomalLink:visited {
	text-decoration: none;
	color: black;
}
a.nomalLink:hover,a.nomalLink:active {
	text-decoration: underline;
	color: black;
}
</style>

<title>帐号清单</title>
</head>
<body>
<h3 align="center">${calendar}明细一览</h3>
<center>
<form action="${pageContext.request.contextPath}/accountAction_findWWWDayDetail.action" method="post">
  <table align="center" border="0" cellpadding="0" cellspacing="0" >
	<tr>
	<td>
     <table cellpadding="4" cellspacing="1" width="800" style="background-color:#bcbcbc;">
       <tr>
         <th width="100" bgcolor="#E4E4E4">访问者IP</th>
         <th width="150" bgcolor="#E4E4E4">访问时间</th>
         <th width="400" bgcolor="#E4E4E4">访问地址</th>
         <th width="150" bgcolor="#E4E4E4">访问流量（字节）</th>
     </tr>
      
       <c:forEach var="data" items="${list}"> 
      	 <tr>
    		<td style="background-color:#ffffff;">${data[0]}</td>
    		<td style="background-color:#ffffff;">${data[1]}</td>
    		<td style="text-align:left;background-color:#ffffff;">${data[2]}</td>
    		<td style="background-color:#ffffff;">${data[3]}</td>
    	</tr>
	   </c:forEach>
	  
     </table>
     </td>
    </tr>
  </table>
       <p>共${totalCount}条记录 ${pageNo}/${pageCount}页
       	<c:if test="${pageNo!=1}">
			<c:url var="first" value="${pageContext.request.contextPath}/accountAction_findWWWDayDetail.action">
				<c:param name="pageNo" value="1" />
				<c:param name="calendar" value="${calendar}" />
			</c:url>
			<c:url var="previous" value="${pageContext.request.contextPath}/accountAction_findWWWDayDetail.action">
				<c:param name="pageNo" value="${pageNo-1}" />
				<c:param name="calendar" value="${calendar}" />
			</c:url>
			<a href="${first}" class="nomalLink">首页</a>|
   	   		<a href="${previous}" class="nomalLink">上一页</a>|
  		</c:if>
		
		<c:if test="${pageNo<pageCount}">
			<c:url var="next" value="${pageContext.request.contextPath}/accountAction_findWWWDayDetail.action">
				<c:param name="pageNo" value="${pageNo+1}" />
				<c:param name="calendar" value="${calendar}" />
			</c:url>
			<c:url var="last" value="${pageContext.request.contextPath}/accountAction_findWWWDayDetail.action">
				<c:param name="pageNo" value="${pageCount}" />
				<c:param name="calendar" value="${calendar}" />
			</c:url>
			<a href="${next}" class="nomalLink">下一页</a>|
  		    <a href="${last}" class="nomalLink">末页</a>
		</c:if>
			<input type="text" name="pageNo" style="width:20;height:16" value="${pageNo}"/>
			<input type="hidden" name="calendar" value="${calendar}"/>
			<input type="submit" value="翻页" style="height:20"/>
		 </p>
		 </form>
     </center>
 <br>
</body>
</HTML>