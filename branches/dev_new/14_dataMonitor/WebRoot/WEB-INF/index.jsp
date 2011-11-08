<%@ page contentType="text/html; charset=utf-8" language="java"
	import="java.sql.*" errorPage=""%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<html>
<head>
<title>${sysconfigmap['title']}</title>
	<link rel="stylesheet" type="text/css"
			href="${pageContext.request.contextPath}/JS/Jquery/themes/default/easyui.css" />
		<link rel="stylesheet" type="text/css"
			href="${pageContext.request.contextPath}/JS/Jquery/themes/icon.css" />
		<link rel="stylesheet" type="text/css"
			href="${pageContext.request.contextPath}/CSS/PageGrid/pagegrid.css" />
		<link rel="stylesheet" type="text/css"
			href="${pageContext.request.contextPath}/CSS/TreePanel/TreePanel.css"/>
		<script type="text/javascript"
			src="${pageContext.request.contextPath}/JS/Jquery/jquery-1.4.2.min.js"></script>
		<script type="text/javascript"
			src="${pageContext.request.contextPath}/JS/Jquery/jquery.easyui.min.js"></script>
		<script type="text/javascript"
			src="${pageContext.request.contextPath}/JS/Jquery/common.js"></script>
		<script type="text/javascript"
			src="${pageContext.request.contextPath}/JS/Jquery/TreePanel.js"></script>
		<script type="text/javascript"
			src="${pageContext.request.contextPath}/JS/Jquery/commontree.js"></script>
		<script type="text/javascript">
		$(function(){
			$('#tt').tabs({
			});
			var url="${pageContext.request.contextPath}/login_getMenuList.action";
			var username =$("#username").val();
			var params={username:username};
			 	$.post(
					url, //服务器要接受的url
					params, //传递的参数 
					function(json){ 
						var ob=eval("("+json+")");
						for(var i=0;i<ob.length;i++){
							var opname=ob[i]['operatename'];
							var taburl="<iframe id='frame_"+ob[i]['id']+"'name='manageright' scrolling='yes' frameborder='0'  src='0' style='width:100%;height:100%;'></iframe>";
							$('#tt').tabs('add',{
								title:opname,
								content:taburl,
								closable:false
							});
						}
						$("#tt").tabs({onSelect:function(title){
							for(var i=0;i<ob.length;i++){
								var opname=ob[i]['operatename'];
								if(opname==title){
								var id=ob[i]['id'];
								var activeurl=ob[i]['url'];
								var frameid=document.getElementById("frame_"+id).src;
								if((frameid=="0"||frameid.indexOf("${pageContext.request.contextPath}/0")>=0)&&activeurl.length>0){
									document.getElementById("frame_"+id).src=activeurl+"?operid="+id;
								}
								}
							}
						}})	
					}
				);
		});
	</script>
</head>
<body class="easyui-layout">
        <input type="hidden" id="username" name="username" value="${usermap['username']}">
		<div region="north" title="" border="false" style="height:50px;padding:0px; background: #dfe8f7;" >
		<div align="center"> 
			<font size="5"><strong>${sysconfigmap['title']}</strong></font>
		</div> 
		<div align="reight">
		欢迎${usermap['realname']}登录该系统
		<a href="login_toLogout.action">退出</a> 
		<c:if test="${usermap['username'] eq 'admin'}">
		<a href="manage_toAdminManage.action">进入后台管理系统</a>
		</c:if>
		</div>
		</div>
		<div region="center" title="" style="overflow:hidden;">
		<div id="tt" style="width:auto;height:500px;">
			
		</div>
		</div>
</body>
</html>