<%@ page language="java" pageEncoding="utf-8" contentType="text/html;charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="org.jasig.cas.client.authentication.AttributePrincipal" %>
<%
	AttributePrincipal principal = (AttributePrincipal)request.getUserPrincipal(); 
	String username = principal.getName(); 	
	request.setAttribute("currentUser", username);
	request.setAttribute("SERVLETPATH", request.getServletPath());
%>
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html;charset=UTF-8" />
  <title>欢迎使用统一用户管理系统</title>
  	<style type="text/css">
  		.uac-top{
        	background-image:url(${pageContext.request.contextPath}/images/banner.jpg);
        	background-color:#eff0e8;
	    	background-repeat:no-repeat;
	    	height:105px;
	    }
  	</style>
	<%@include file="/includefiles.jsp"%>
	<script type="text/javascript">
		var panels = [];
		var servletPath = "${SERVLETPATH}"; 
		var currentUser = '${currentUser }';
	</script>
	<script type="text/javascript" src="<c:url value="/user/user.js"/>" ></script>
    <SCRIPT SRC="<c:url value="/js/stcookie.js"/>" language="JavaScript1.1"></SCRIPT>
    <script type="text/javascript">
    Ext.onReady(function(){
	   Ext.QuickTips.init();
	   Ext.state.Manager.setProvider(new Ext.state.CookieProvider()); 
	   Ext.Ajax.request({  
	        url: webContext + '/user?methodCall=getDetailByUid', 
	        params : {
	        	uid : '${currentUser }'
	        },
	        async: false,
	        method: 'POST',
	        success: function(response, opts) {	
	        	obj = Ext.util.JSON.decode(response.responseText);
	        	if(obj.success){
	        		Ext.getCmp("userPanel").form.setValues(obj);
	        		Ext.getCmp("uidCopy").setValue(obj.uid);
	        		
	        		Ext.getCmp("userUid").setReadOnly(true);
	        		Ext.getCmp("help").setVisible(false);
	        		Ext.getCmp("btnUserAdd").setVisible(false);//不可以增加
	        		Ext.getCmp("btnUserAdd").setVisible(false);//不可以增加						        		
	        		Ext.getCmp("btnUserDel").setVisible(false);//不可以删除
	        		Ext.getCmp("btnUserSelect").setVisible(false);//不可以删除
	        		Ext.getCmp("userPanel").setTitle(obj.uid + "基本信息");
				}			         	
	        },
	        failure: function(response, opts) {
	         	Ext.Msg.alert("提示", "DN为：" + dn + "的用户信息获取失败！");
	        }   
	    });	
	    Ext.getCmp("userPanel").render("content");
	    /*var viewport = new Ext.Viewport({
				id:'mainViewport',
				layout : 'fit',
				items : [Ext.getCmp("userPanel")]
			});
		viewport.doLayout();*/					     
   		Ext.getCmp("departmentNumber1").setValue(obj.deptNo);
   		Ext.getCmp("departmentNumber1").setRawValue(obj.deptName);
   		Ext.getCmp("userTypeCombo").setReadOnly(true);
    });
	</script>
</head>
<body>
<div id="banner" class="uac-top"></div>
<div id="content"></div>
</body>
</html>
