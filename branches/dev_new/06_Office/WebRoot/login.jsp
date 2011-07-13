<%@page pageEncoding="UTF-8"%>
<%@page import="com.xpsoft.core.util.AppUtil"%>

<%@page import="org.apache.commons.lang.StringUtils"%><html>
	<head>
		<title>欢迎登录<%=AppUtil.getCompanyName()%>协同办公系统</title>
		<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/ext3/resources/css/ext-all.css" />
		<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/ext3/resources/css/ext-patch.css" />
		<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/login.css" />
		<%
		response.addHeader("__timeout","true");
		String codeEnabled=(String)AppUtil.getSysConfig().get("codeConfig");
		if(StringUtils.isEmpty(codeEnabled)){//若当前数据库没有配置验证码参数
			codeEnabled="1";//代表需要输入
		}
		%>
		<script type="text/javascript">
			var __ctxPath="<%=request.getContextPath() %>";
			var __loginImage=__ctxPath+"<%=AppUtil.getCompanyLogo()%>";
		</script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/ext3/adapter/ext/ext-base.gzjs"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/ext3/ext-all.gzjs"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/ext3/ext-lang-zh_CN.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/App.LoginWin.js"></script>
		<script type="text/javascript">
	 		Ext.onReady(function(){
		 		Ext.QuickTips.init();  
		 		
		 		new App.LoginWin('<%=codeEnabled%>').show();
			});	
		</script>
	</head>
	<body  style="background:url(images/login_bg.gif) repeat ">
		<!--div style="text-align: center;">
			<div id="loginArea">
			
			</div>
		</div-->
		
		<div id="center" align="center" style="text-align: center;position:absolute; background:url(images/login_d_bg.png) no-repeat; left: 20%; top: 103px; width:802px; height:400px;">
       	  	<div id="logo" style="position:absolute; left: 2%; width: 247px; top: 20px; height: 55px;">
       	  		<img src="<%=request.getContextPath() %><%=AppUtil.getCompanyLogo()%>" style="height:55px;max-width:247px;"/>
       	  	</div>
       	  	<div id="loginArea" style="position:absolute; left: 10%; width: 290px; top: 164px; height: 190px;"></div>
       		<div id="cr" style = "position:absolute; right: 5%;  top: 184px; height: 180px;"><embed width="330px" height="152px" src="images/banner.swf" menu="false" /></div>
		</div>
	</body>
</html>