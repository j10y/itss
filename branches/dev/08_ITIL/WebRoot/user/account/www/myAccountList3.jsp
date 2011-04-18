<%@page contentType="text/html;charset=gbk" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<HTML>
<head>
<style type="text/css" media="screen">
body{font-size:12px;}
.title{background:url(images/bg.gif) no-repeat 1px 2px;padding:1px;height:18px;line-height:18px;color:#fff;text-indent:20px;}
td{text-align:center;font:smaller Verdana,"Andale Mono",Courier,"Courier New",monospace;font-size:10px;}
th{font:smaller Verdana,"����",Courier,"Courier New",monospace;font-weight:bold;font-size:12px;}
a.nomalLink,a.nomalLink:visited {
	text-decoration: none;
	color: black;
}
a.nomalLink:hover,a.nomalLink:active {
	text-decoration: underline;
	color: black;
}
</style>

<title>�ʺ��嵥</title>
</head>
<body>
<h3 align="center">${calendar}��ϸһ��</h3>
<center>
<form action="${pageContext.request.contextPath}/accountAction_findWWWDayDetail.action" method="post">
  <table align="center" border="0" cellpadding="0" cellspacing="0" >
	<tr>
	<td>
     <table cellpadding="4" cellspacing="1" width="800" style="background-color:#bcbcbc;">
       <tr>
         <th width="100" bgcolor="#E4E4E4">������IP</th>
         <th width="150" bgcolor="#E4E4E4">����ʱ��</th>
         <th width="400" bgcolor="#E4E4E4">���ʵ�ַ</th>
         <th width="150" bgcolor="#E4E4E4">�����������ֽڣ�</th>
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
       <p>��${totalCount}����¼ ${pageNo}/${pageCount}ҳ
       	<c:if test="${pageNo!=1}">
			<c:url var="first" value="${pageContext.request.contextPath}/accountAction_findWWWDayDetail.action">
				<c:param name="pageNo" value="1" />
				<c:param name="calendar" value="${calendar}" />
			</c:url>
			<c:url var="previous" value="${pageContext.request.contextPath}/accountAction_findWWWDayDetail.action">
				<c:param name="pageNo" value="${pageNo-1}" />
				<c:param name="calendar" value="${calendar}" />
			</c:url>
			<a href="${first}" class="nomalLink">��ҳ</a>|
   	   		<a href="${previous}" class="nomalLink">��һҳ</a>|
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
			<a href="${next}" class="nomalLink">��һҳ</a>|
  		    <a href="${last}" class="nomalLink">ĩҳ</a>
		</c:if>
			<input type="text" name="pageNo" style="width:20;height:16" value="${pageNo}"/>
			<input type="hidden" name="calendar" value="${calendar}"/>
			<input type="submit" value="��ҳ" style="height:20"/>
		 </p>
		 </form>
     </center>
 <br>
</body>
</HTML>