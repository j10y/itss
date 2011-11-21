<%@ page language="java" pageEncoding="utf-8" contentType="text/html;charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="org.jasig.cas.client.authentication.AttributePrincipal" %>
<%
	AttributePrincipal principal = (AttributePrincipal)request.getUserPrincipal(); 
	String username = principal.getName(); 
	request.setAttribute("SERVLETPATH", request.getServletPath());
	
	if(username==null || username.equals("")) response.sendRedirect(request.getContextPath()+ "/index.jsp");
%>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html;charset=UTF-8" />
  <title>欢迎使用统一用户管理系统</title>
	<%@include file="/includefiles.jsp"%>
	<script type="text/javascript">
		var panels = [];
		var servletPath = "${SERVLETPATH}"; 
	</script>
	<script type="text/javascript" src="<c:url value="/dept/dept.js"/>" ></script>
	<script type="text/javascript" src="<c:url value="/user/user.js"/>" ></script>
	<script type="text/javascript" src="<c:url value="/user/userImport.js"/>" ></script>
	<script type="text/javascript" >
		function addTab(id,tabTitle, targetUrl){
	        Ext.getCmp('sysmainpanel').add({
		       	id:id,
			    title: tabTitle,
			    iconCls: 'tabs',
			    autoLoad: {
		    		url: "tabFrame.jsp?url="+targetUrl, 
		    		scope: this
		    		},
		        autoScroll:false,
			   	closable:true
			}).show();
	    }
			    
	    function updateTab(id,title, url) {
	    	var tab = Ext.getCmp('sysmainpanel').getItem(id);
	  		if(tab){
	   			Ext.getCmp('sysmainpanel').remove(tab);
	   		}
	    	tab = addTab(id,title,url);
	   		Ext.getCmp('sysmainpanel').setActiveTab(tab);
	    }
	</script>
  	<script type="text/javascript" src="<c:url value="/index.js"/>" ></script>
    <SCRIPT SRC="<c:url value="/js/stcookie.js"/>" language="JavaScript1.1"></SCRIPT>
	<style type="text/css">
        html, body {
            font: normal 12px verdana;
            margin: 0;
            padding: 0;
            border: 0 none;
            overflow: hidden;
            height: 100%;
        }
        .empty .x-panel-body {
            padding-top:20px;
            text-align:center;
            font-style:italic;
            color: gray;
            font-size:11px;
        }
        .sys-top{
	    	background-image:url(images/titlelog.png);
	    	background-color:#ffffff;
	    	background-repeat:no-repeat;
	    	height:90px;
	    }
	    .sys-top-logo{
	    	position:absolute;
	    	top:0px;
	    	right:0px;
	    	margin : 5 0 0 0;
	    	background-image:url(images/logo.jpg);
	    	background-repeat:no-repeat;
	    	width:200px;
	    	height:65px;
	    	z-index:99;
	    }
	    .sys-top-tool{
	    	position:absolute;
	    	top:65px;
	    	left:0px;
	    }
	    .x-toolbar-whDefine{
			border-color:#a9bfd3;
		    background:transparent!important;
		}
		.itil-userName{
			color:#724c98;
			font-weight:600!important;
		}
		.nav {
		    background-image:url(images/cls/folder_go.png ) !important;
		}
    </style>
    <script type="text/javascript">
	var toadmin= false;	
	var webContext = "${pageContext.request.contextPath}";
	var currentUser = '<%=username%>';
	var userName = "你好&nbsp;<span class='itil-userName'>" + currentUser + "</span>&nbsp;";

	Ext.BLANK_IMAGE_URL = 'ext-3.2.1/resources/images/default/s.gif';
	var demoData= new Cookie(document, currentUser, 240);
	var dataFound=demoData.load();
	
	function refreshWorkflow(){
		
	}
    Ext.onReady(function(){
	   Ext.QuickTips.init();
	   Ext.state.Manager.setProvider(new Ext.state.CookieProvider()); 
	   com.dc.ui.IndexPage.init('${systemAdmin}', webContext, '');
    });
	</script>
</head>
<body onload="refreshWorkflow();">
<div>
<div class="sys-top-logo"></div>
  <div id="north" >
    <div id="up" class="sys-top"></div>
    <div id="down" class="sys-top-tool"></div>
  </div>
</div>
</body>
</html>
