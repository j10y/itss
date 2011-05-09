<%@ page language="java" pageEncoding="gbk"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
  <title>欢迎使用IT服务系统</title>
	<%@include file="/includefiles.jsp"%>
  	<script type="text/javascript" src="<c:url value="/index.js"/>" ></script>   
	<script type="text/javascript" src="<c:url value="/auditForms.js"/>"></script> 
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
        .itil-top{
	    	background-image:url(images/titlelog.png);
	    	background-repeat:no-repeat;
	    	height:90px;
	    }
	    .itil-top-logo{
	    	position:absolute;
	    	top:0px;
	    	right:0px;
	    	background-image:url();
	    	background-repeat:no-repeat;
	    	width:130px;
	    	height:65px;
	    	z-index:99;
	    }
	    .itil-top-tool{
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
    </style>
    <script type="text/javascript">
	var userID = '${userInfo.userName}';
	var toadmin= false;	
	var webContext = "${pageContext.request.contextPath}";
 	var BP = '${pageContext.request.contextPath}/';
	var currentUser = '${userInfo.userName}';
	var userName = "你好&nbsp;<span class='itil-userName'>" + '${userInfo.realName}' + "</span>&nbsp;";
	var userId = '${userInfo.id}';
	var currentUserReal = '${userInfo.realName}';

	Ext.BLANK_IMAGE_URL = 'extEngine/resources/images/default/s.gif';
	var demoData= new Cookie(document, currentUser, 240);
	var dataFound=demoData.load();
		    
    function addTab(id,tabTitle, targetUrl){
        Ext.getCmp('itssmainpanel').add({
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
    	var tab = Ext.getCmp('itssmainpanel').getItem(id);
  		if(tab){
   			Ext.getCmp('itssmainpanel').remove(tab);
   		}
    	tab = addTab(id,title,url);
   		Ext.getCmp('itssmainpanel').setActiveTab(tab);
    }

	if(typeof(glbRootPath) != "undefined"){
		basePath = glbRootPath;
	}
	
	function toLogin() {
		window.location.href= webContext + "/j_logout.do";
	}
	function refreshWorkflow(){//刷新用户审批任务列表
		setInterval(function(){
			Ext.getCmp('myApply').getStore().reload();
			Ext.getCmp('myAudit').getStore().reload();
			},300000)
	}
    Ext.onReady(function(){
	   Ext.QuickTips.init();
	   Ext.state.Manager.setProvider(new Ext.state.CookieProvider()); 
	   com.dc.ui.IndexPage.init('${systemAdmin}',webContext,'');
    });
	</script>
</head>
<body onload="refreshWorkflow();">
<div class="itil-top-logo"></div>
  <div id="north" >
    <div id="up" class="itil-top"></div>
    <div id="down" class="itil-top-tool"></div>
  </div>
</body>
</html>
