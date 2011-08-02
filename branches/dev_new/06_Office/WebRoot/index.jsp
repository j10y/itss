<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@page import="com.xpsoft.core.util.AppUtil"%>
<%@page import="com.xpsoft.core.util.ContextUtil"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String basePath=request.getContextPath();
	//登录成功后，需要把该用户显示至在线用户
	AppUtil.addOnlineUser(request.getSession().getId(), ContextUtil.getCurrentUser());
	request.setAttribute("currentUserId", ContextUtil.getCurrentUser().getUserId());
	request.setAttribute("currentUserFullname", ContextUtil.getCurrentUser().getFullname());
	if(ContextUtil.getCurrentUser().getRights().contains("__ALL")){
		request.setAttribute("IS_MANAGER",true);
	}
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<meta http-equiv="msthemecompatible" content="no">
		<title><%=AppUtil.getCompanyName()%>－－办公协同管理系统</title>
		<script type="text/javascript">
			var __currentUserId = "${currentUserId}";
			var __currentUserFullName = "${currentUserFullname}";
		  
	    </script>
		<link rel="stylesheet" type="text/css" href="<%=basePath%>/ext3/resources/css/ext-all-notheme.css" />
		<link rel="stylesheet" type="text/css" href="<%=basePath%>/ext3/resources/css/ext-patch.css" />
		<link rel="stylesheet" type="text/css" href="<%=basePath%>/ext3/ux/css/Portal.css" />
		<link rel="stylesheet" type="text/css" href="<%=basePath%>/ext3/ux/css/Ext.ux.UploadDialog.css" />
		<link rel="stylesheet" type="text/css" href="<%=basePath%>/css/admin.css"/>
		
		<link rel="stylesheet" type="text/css" href="<%=basePath%>/ext3/ux/css/ux-all.css"/>
		<link rel="stylesheet" type="text/css" href="<%=basePath%>/ext3/ux/caltask/calendar.css" />
		<!-- load the extjs libary -->
		<script type="text/javascript" src="<%=basePath%>/js/dynamic.jsp"></script>
		<script type="text/javascript" src="<%=basePath%>/ext3/adapter/ext/ext-base.gzjs"></script>
		<script type="text/javascript" src="<%=basePath%>/ext3/ext-all.gzjs"></script>
		<script type="text/javascript" src="<%=basePath%>/ext3/ext-lang-zh_CN.js"></script>
		<script type="text/javascript" src="<%=basePath%>/js/core/ScriptMgr.js"></script>
		
		<script type="text/javascript" src="<%=basePath%>/js/App.import.js"></script>
		<script type="text/javascript" src="<%=basePath%>/js/core/AppUtil.js"></script>
		<script type="text/javascript" src="<%=basePath%>/js/print/Printer-all.js"></script>
		<script type="text/javascript" src="<%=basePath%>/js/print/renderers/GridPanel.js"></script>
		<script type="text/javascript" src="<%=basePath%>/js/export/Exporter-all.js"></script>
		
		<script type="text/javascript" src="<%=basePath%>/ext3/ux/Ext.ux.IconCombob.js"></script>
		<script type="text/javascript" src="<%=basePath%>/ext3/ux/RowEditor.js"></script>
		<script type="text/javascript" src="<%=basePath%>/js/fckeditor/fckeditor.js"></script>
		<script type="text/javascript" src="<%=basePath%>/ext3/ux/Fckeditor.js"></script>
		<script type="text/javascript" src="<%=basePath%>/ext3/ux/XmlTreeLoader.js"></script>
		<script type="text/javascript" src="<%=basePath%>/ext3/ux/FileUploadField.js"></script>
		<script type="text/javascript" src="<%=basePath%>/ext3/ux/UploadDialog.js"></script>
		<script type="text/javascript" src="<%=basePath%>/ext3/ux/CheckColumn.js"></script>
		<script type="text/javascript" src="<%=basePath%>/ext3/ux/Portal.js"></script>
		<script type="text/javascript" src="<%=basePath%>/ext3/ux/PortalColumn.js"></script>
		<script type="text/javascript" src="<%=basePath%>/ext3/ux/Portlet.js"></script>
		<script type="text/javascript" src="<%=basePath%>/ext3/ux/Toast.js"></script>
		<script type="text/javascript" src="<%=basePath%>/ext3/ux/Ext.ux.grid.RowActions.js"></script>
		<script type="text/javascript" src="<%=basePath%>/ext3/ux/DateTimeField.js"></script>
		<script type="text/javascript" src="<%=basePath%>/ext3/ux/TabCloseMenu.js"></script>
	
		<script type="text/javascript" src="<%=basePath%>/js/core/SystemCalendar.js"></script>
		<script type="text/javascript" src="<%=basePath%>/js/core/TreeSelector.js"></script>
		<script type="text/javascript" src="<%=basePath%>/js/core/date.js"></script>
		<script type="text/javascript" src="<%=basePath%>/js/core/ux/TreePanelEditor.js"></script>
		<script type="text/javascript" src="<%=basePath%>/js/core/ux/TreeXmlLoader.js"></script>
		<script type="text/javascript" src="<%=basePath%>/js/core/ux/WebOffice.js"></script>

		<script type="text/javascript" src="<%=basePath%>/js/selector/UserSelector.js"></script>
		<script type="text/javascript" src="<%=basePath%>/js/selector/UserSubSelector.js"></script>
		<script type="text/javascript" src="<%=basePath%>/js/selector/DepSelector.js"></script>
		<script type="text/javascript" src="<%=basePath%>/js/selector/RoleSelector.js"></script>
		<script type="text/javascript" src="<%=basePath%>/js/selector/GoodsSelector.js"></script>
		<script type="text/javascript" src="<%=basePath%>/js/selector/CarSelector.js"></script>
		<script type="text/javascript" src="<%=basePath%>/js/selector/CustomerSelector.js"></script>
		<script type="text/javascript" src="<%=basePath%>/js/selector/OnlineUserSelector.js"></script>
		<script type="text/javascript" src="<%=basePath%>/js/selector/BookSelector.js"></script>	
		<script type="text/javascript" src="<%=basePath%>/js/selector/ProjectSelector.js"></script>
		<script type="text/javascript" src="<%=basePath%>/js/selector/ProviderSelector.js"></script>
		
		<script type="text/javascript" src="<%=basePath%>/js/info/MessageWin.js"></script>
		<script type="text/javascript" src="<%=basePath%>/js/info/MessageReplyWin.js"></script>
		<script type="text/javascript" src="<%=basePath%>/js/info/MessageDetail.js"></script>
		<script type="text/javascript" src="<%=basePath%>/js/flow/ProcessNextForm.js"></script>
		<script type="text/javascript" src="<%=basePath%>/js/system/FileAttachDetail.js"></script>
		<script type="text/javascript" src="<%=basePath%>/js/system/DiaryDetail.js"></script>
		<script type="text/javascript" src="<%=basePath%>/js/task/WorkPlanDetail.js"></script>
		<script type="text/javascript" src="<%=basePath%>/js/personal/DutyView.js"></script>
		<script type="text/javascript" src="<%=basePath%>/js/personal/DutyForm.js"></script>
        <script type="text/javascript" src="<%=basePath%>/js/sound/soundmanager2.js"></script>
        <script type="text/javascript" src="<%=basePath%>/js/search/SearchForm.js"></script>
        <script type="text/javascript" src="<%=basePath%>/js/changeTheme/changeTheme.js"></script>
    	<link href="<%=basePath%>/css/desktop.css" rel="stylesheet" type="text/css" />	
    	<script type="text/javascript" src="<%=basePath%>/js/ScrollText.js"></script>
    	<script type="text/javascript" src="<%=basePath%>/ext3/ext-basex.js"></script>
	    <script type="text/javascript">
	       var __companyName="<%=AppUtil.getCompanyName()%>";
		   Ext.onReady(function(){
			   	  var storeTheme=getCookie('theme');
			   	  if(storeTheme==null || storeTheme==''){
				   	  storeTheme='ext-all';
			   	  }
			      Ext.util.CSS.swapStyleSheet("theme", __ctxPath+"/ext3/resources/css/"+storeTheme+".css");  
		    });	
		    
		    var	__appSupport = '<%=AppUtil.getPropertity("app.support")%>';
		    var	__appSupportMail = '<%=AppUtil.getPropertity("app.supportMail")%>';
		    var	__appListForwardNode = '<%=AppUtil.getPropertity("app.listForwardNode")%>';
		  
	    </script>
	    <script type="text/javascript" src="<%=basePath%>/js/IndexPage.js"></script>
	    <script type="text/javascript" src="<%=basePath%>/js/App.home.js"></script>
		<script type="text/javascript" src="<%=basePath%>/js/App.js"></script>
		
		<style>
		 --* { margin:0 auto; padding:0;}
		 #header { background:url(images/tlbg.gif) 0 0 repeat-x; height:90px; position:relative;}
		 .headers { background:url(images/tshadow.gif) repeat-x; height:5px; width:100%; overflow:hidden;}
		 #header a { _behavior: url("iepngfix.htc");}
		 #header img { border:none;}
		 .trl {padding:15px 10px;}
		 .trl  .logo { width:160px; float:left;}
		 .trl p { line-height:24px; font-size:12px; padding:6px; padding-left:100px; width:290px; float:left;}
		 .trnav { width:526px; position:absolute; right:0; top:0; height:90px; background:url(images/trbg2.gif) no-repeat;}
		 .trform { height:20px; font-size:12px; padding-left:22px; padding-top:5px; padding-bottom:5px; overflow:hidden}

		 .trab { padding-top:6px; padding-left:142px;}
		 .tra1,.tra2,.tra3,.tra4,.tra5 { display:block; width:48px; height:48px; margin:0 12px; background:url(images/ben48.png) no-repeat; _background:url(images/ben48.gif) no-repeat; float:left; _cursor:pointer}
		 .tra1 { background-position:0 -48px;}
		 .tra2 { background-position:-48px -48px;}
		 .tra3 { background-position:-96px -48px;}
		 .tra4 { background-position:-144px -48px;}
		 .tra5 { background-position:-192px -48px;}
		 .tra1:hover { background-position:0 0;}
		 .tra2:hover { background-position:-48px 0;}
		 .tra3:hover { background-position:-96px 0;}
		 .tra4:hover { background-position:-144px 0;}
		 .tra5:hover { background-position:-192px 0;}
		 .ssbtn { background:url(images/ssbtn.gif); width:51px; height:20px; border:none; cursor:pointer}
		</style>
			
	</head>
	<body >
		<div id="loading">
             <div class="loading-indicator">
                  <img src="<%=basePath%>/images/loading.gif" alt="" width="85" height="81" style="margin-left:10px;" align="absmiddle"/>
                  <div class="clear"></div>
         		    正在加载，请稍候......
             </div>
         </div>
        <div id="loading-mask"></div>

		<div id="header">
			<div class="trl">
				<a href="#" class="logo"><img alt="<%=AppUtil.getCompanyName()%>－－办公协同管理系统"
						src="">
				</a>
				<p>
					欢迎您，<security:authentication property="principal.fullname"/>
					<a href="<%=basePath%>/j_logout.do">注销</a>
					<nobr><span id="nowTime"></span><span id="nowTime2"></span></nobr>
				</p>
			</div>
			<div class="trnav">
				<div class="trform">
					<div id="searchFormDisplay"></div>
					<div id="changeThemeDisplay" style="position:relative;top:-22px;padding-right:30px;z-index:99999;float:right;"></div>
					<div style="position:relative;top:-15px;right:-130px;z-index:99999;float:right;">
							<a href="<%=basePath%>/help/manual.zip" target="blank">帮助</a>
					</div>
					 <!-- 
					 <div id="tool" style="position:relative;padding-left:100px;z-index:99999;float:right;"></div>
					  -->
				</div>
				<div class="trab">
					<a href="#" onclick="App.MyDesktopClick()" class="tra1" title="桌面"></a>
					<a href="#" onclick="App.clickTopTab('PersonalMailBoxView')" class="tra2" title="邮件"></a>
					<a href="#" onclick="App.clickTopTab('CalendarPlanView')" class="tra3" title="任务"></a>
					<a href="#" onclick="App.clickTopTab('WorkPlanView')" class="tra4" title="计划"></a>
					<a href="#" onclick="App.clickTopTab('PersonalDocumentView')" class="tra5" title="文档"></a>
				</div>
			</div>
		</div>
		<div class="headers"></div>
	</body>
</html>