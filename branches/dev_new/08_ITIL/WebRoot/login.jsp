<%@ page language="java" pageEncoding="gbk"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="fmt"%>
<%@ include file="/includefiles.jsp" %>
<html>
	<head>
		<title>IT服务系统</title>

		<META HTTP-EQUIV="Content-Type" CONTENT="text/html; charset=gbk">

		<style type="text/css">
		#loading-mask {
			position: absolute;
			left: 0;
			top: 0;
			width: 100%;
			height: 100%;
			z-index: 20000;
			background-color: white;
		}
		
		#loading {
			position: absolute;
			left: 45%;
			top: 40%;
			padding: 2px;
			z-index: 20001;
			height: auto;
		}
		
		#loading a {
			color: #225588;
		}
		
		#loading .loading-indicator1 {
			background: white;
			color: #444;
			font: bold 13px tahoma, arial, helvetica;
			padding: 10px;
			margin: 0;
			height: auto;
		}
		
		#loading-msg {
			font: normal 10px arial, tahoma, sans-serif;
		}
		</style>
		<script type="text/javascript" src="<c:url value="/js/login.js"/>"></script>
		<SCRIPT SRC="<c:url value="/js/stcookie.js"/>" language="JavaScript1.1"></SCRIPT>
		<script type="text/javascript"> 
		function changeSkin(value){
			Ext.util.CSS.swapStyleSheet('window', 'extEngine/resources/css/' + value + '.css');
			document.getElementById('j_username').focus(true);
		}
		var serverPath = "${pageContext.request.contextPath}";
//		Ext.onReady(loginForm.init());
		  function  CheckInfo(e)  
		  {
            var isie = (document.all) ? true : false; 
            var key; 
              if (isie) 
                  key = window.event.keyCode; 
              else 
                  key = e.which; 
                  if(key==13){                  
                       submitNewForm();                  
                    }	                 
		  }   

		function submitNewForm(){
				Ext.QuickTips.init();
				Ext.state.Manager.setProvider(new Ext.state.CookieProvider());
				Ext.form.Field.prototype.msgTarget = 'side';		
							
				var userName = document.getElementById("j_username").value;
				var passWd = document.getElementById("j_password").value;
				if (userName == "") {								
						document.getElementById('loginMessage').innerHTML = "\u8bf7\u8f93\u5165ITcode\uff01";
						document.getElementById('j_username').focus(true);							
					return;
				}
				if (passWd == "") {	
						document.getElementById('loginMessage').innerHTML = "\u8bf7\u8f93\u5165ITpassword\uff01";
						document.getElementById('j_password').focus(true);							
					return;					
				}				
				Ext.Ajax.request({
					params : {
								j_username : userName,
								j_password : passWd
					},				
					url : serverPath + '/j_spring_security_check',
					success : function(response, options) {
						var responseArray = Ext.util.JSON.decode(response.responseText);
						var curSuccess = responseArray.success;
						if(curSuccess==true){
							//window.location='/index.jsp';
							var demoData = new Cookie(document, userName, 240);
							demoData.loginName = userName;
							demoData.password = passWd;
							demoData.names = '';
							demoData.store();
							gotoIndex();
						}else{
							document.getElementById('loginMessage').innerHTML = "ITcode或ItPassword错误！";
							document.getElementById('j_username').value="";
							document.getElementById('j_password').value="";
							document.getElementById('j_username').focus(true);	
						}
					},
					failure : function(response, options) {
						document.getElementById('loginMessage').innerHTML = "ITcode或ItPassword错误！";
						document.getElementById('j_username').focus(true);							
					return;	
					}
				});
			}		
 		</script>



	</head>
	<body onLoad="changeSkin('xtheme-gray')" bgcolor="#FFFFFF" onkeypress="CheckInfo(event)" >	
<!--	remove by zhangzy for 用户要求更换登录窗口 in 2009 12 12 
	<div id="center" align="center" style="position:absolute; background:url(images/login-FlameDemo.png) no-repeat; left: 20%; top: 103px; width:802px; height:400px;">
       	  <div id="login" style="position:absolute; left: 10%; width: 290px; top: 174px; height: 190px;"></div>
	</div>
-->

<div id="center" align="center" style="position:absolute;  left: 35%; top: 153px; width:802px; height:400px;">

	<table id="center" align="left"  border="0" cellspacing="0" cellpadding="0">
		<tr valign="top">
			<td >
				<table id="center" align="left"  border="0" cellspacing="0" cellpadding="0">
					<tr>
						<td align="right" width="15%" style="text-align:right;padding-right:4px;"><img src="images/other/ecblank01.gif"></img></td>
						<td id="loginMessage" style="font-size:12px; font-family:宋体;text-align:left">请输入登录信息:</td>						
					</tr>
					<tr>
						<td colspan="2" height="25">&nbsp;</td>
					</tr>
					<tr>
						<td align="right" style="text-align:right;padding-right:4px;" height="30">ITcode:</td>
						<td><input id="j_username" value="" size=20 maxlength=50 autocomplete=off style="height:18px;"></td>						
					</tr> 
					<tr>
						<td align="right" style="text-align:right;padding-right:4px;" height="30">ITpassword:</td>
						<td><input id="j_password" type="password" value="" size=21 maxlength=50 autocomplete=off style="height:18px;"></td>						
					</tr>
					<tr>
						<td colspan="2" height="50"  align="center"><input type="button" onclick="submitNewForm()"  value=" 登 录 "></td>												
					</tr>										
				</table>
			</td>
			<td width="10%" >&nbsp;</td>			
			<td valign="top"   valign= style="font-size:12px; font-family:宋体"><img src="images/other/ecblank02.jpg"></img></td>
		</tr>
	</table>
</div>
<div style="position:absolute;  left: 25%; top: 420px; width:902px; height:20px;font-family: 宋体;font-size: 12px;" >
神州数码 版权所有 Copyright 2000-2009 All Right Reserved.
集团信息化管理部（DCIT） 提供支持平台
</div>
	 
</body>
</html>

<script type="text/javascript">
	//alert(document.body.clientHeight);
		
</script>
