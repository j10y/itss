<%@page contentType="text/html;charset=gbk" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<HTML>
<head>
<style type="text/css" media="screen">
body{font-size:12px;}
.title{background:url(images/bg.gif) no-repeat 1px 2px;padding:1px;height:18px;line-height:18px;color:#fff;text-indent:20px;}
td{text-align:center;font:smaller Verdana,"Andale Mono",Courier,"Courier New",monospace;font-size:12px;}
th{font:smaller Verdana,"宋体",Courier,"Courier New",monospace;font-weight:bold;font-size:12px;}
</style>

<title>帐号清单</title>
<script type="text/javascript">
function test(){
	var ym=document.searchform.yearAndMonth.value;
	if(ym.length!=6){
		alert("查询条件必须是6位数字，如201006");
		return false;
	}
	if(ym.match(/\D/)==null){
		return true;
	}else{
		alert("查询条件必须是6位数字，如201006");
		return false;
	}
}
</script>
</head>
<body>
<h3 align="center">${yearAndMonth}明细一览</h3>
     <center>
       <table width="600" border="0" cellspacing="0" cellpadding="4">
         <tr>
           <td>
           <div align="right">
	        	<form name='searchform' action="${pageContext.request.contextPath}/accountAction_findWWWMonthDetail.action" method="post" onsubmit='return(test())'>
	             <input type="text" name="yearAndMonth" size="6" maxlength="6" value="${yearAndMonth}">
	             <input type="submit" name="button" id="button" value="查询" >
	            </form>
           </div>
           </td>
         </tr>
       </table>
       <table align="center" border="0" cellpadding="0" cellspacing="0" style="font-size: 13px;">
	<tr>
	<td>
     <table cellpadding="4" cellspacing="1" width="600" style="background-color:#bcbcbc;">
       <th bgcolor="#E4E4E4">日期</th>
         <th bgcolor="#E4E4E4">流量(MB)</th>
       <c:forEach var="data" items="${list}"> 
      	 <tr>
    		<td style="background-color:#ffffff;"><a href="${pageContext.request.contextPath}/accountAction_findWWWDayDetail.action?calendar=${data[1]}">${data[1]}</td>
    		<td style="background-color:#ffffff;">${data[0]}</td>
    	</tr>
    </c:forEach>
     </table>
     </td>
     </tr>
     </table>
</center>
</body>
</HTML>