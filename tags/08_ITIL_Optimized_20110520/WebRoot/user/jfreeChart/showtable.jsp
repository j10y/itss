<%@ page contentType="text/html;charset=GBK"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
<title>JFreeChartÐ§¹ûÒ³Ãæ</title>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/extEngine/resources/css/ext-all.css" />

<script type="text/javascript" src="${pageContext.request.contextPath}/extEngine/adapter/ext/ext-base.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/extEngine/ext-all.js"></script>
</head>
 <script type="text/javascript" src="${pageContext.request.contextPath}/js/My97DatePicker/WdatePicker.js"></script>
<script language="javascript">
    function changeSkin(value){
	   		 Ext.util.CSS.swapStyleSheet('window', '${pageContext.request.contextPath}/extEngine/resources/css/' + value + '.css');
	   		 
	}
</script>
<body class="BODY" onload="changeSkin('${userInfo.userViewStyle}');">
		<div class="x-panel-body" >
			  <table width="100%" border=0>
			  <tr class="x-grid-panel x-panel-mc x-panel-body">
			  	  <td nowrap height="25"  valign="middle" class="x-grid3-header">
				  </td>
				  <c:forEach var="item" items="${heads}" >
				  <td nowrap height="25"  valign="middle" class="x-grid3-header">
				  ${item }
				  </td>
				  </c:forEach>
			  </tr>
			   <c:forEach var="its" items="${bodys}" varStatus="status">
			   <tr class="x-grid-panel x-panel-mc ">
			   	  <td nowrap valign="middle" class="x-grid3">
				  ${status.count }
				  </td>
			   	  <c:forEach var="item" items="${its}" >
				  <td nowrap valign="middle" class="x-grid3">
				  ${item }
				  </td>
				  </c:forEach>
			   </tr>
			   </c:forEach>
			  </table>
		  </div>
</body>
</html>
