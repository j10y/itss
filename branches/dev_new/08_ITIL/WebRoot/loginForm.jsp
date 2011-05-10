<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="fmt"%>
<html>
<head>
	<%@ include file="/includefiles.jsp" %>
    <title>ITSS</title>
    <script language="JavaScript" type="text/JavaScript">
        <!--
        function MM_reloadPage(init) {  //reloads the window if Nav4 resized
            if (init == true) with (navigator) {
                if ((appName == "Netscape") && (parseInt(appVersion) == 4)) {
                    document.MM_pgW = innerWidth;
                    document.MM_pgH = innerHeight;
                    onresize = MM_reloadPage;
                }
            }
            else if (innerWidth != document.MM_pgW || innerHeight != document.MM_pgH) location.reload();
        }
        MM_reloadPage(true);
        //-->
    </script>
    <script>
    	var serverPath = "${pageContext.request.contextPath}";
    	function gotoIndex() {
			document.body.innerHTML="";	
			Ext.DomHelper.insertHtml('afterBegin',document.body,"<div id=\"loading\" style=\"position:absolute;left: 45%;top: 40%;font-size:12px;\">"
							+ "<div class=\"loading-indicator1\">"
							+ "<img src=\"" + serverPath + "/images/blue-loading.gif\" width=\"32\" height=\"32\" style=\"margin-right:8px;float:left;vertical-align:top;\"/>"
							+ "欢迎使用IT服务系统<br />"
							+ "<span id=\"loading-msg\">转接中...</span>" + "</div>"
							+ "</div>");
			window.location.href = serverPath + "/login.do?methodCall=logined";
		}
    </script>
    <script language="javascript">
        function getfocus() {
            document.getElementById("userId").focus();
        }
        function checkForm(){
            var userId = document.getElementById("userId").value;
            if(userId.length<=0){
                document.getElementById("checkUserId").innerHTML= "<font style='font-family:arial;font-size:80%;color:red'>" + i18n("c.login.userId.isEmpty") + "</font>";
                return false;
            }
            var pwd = document.getElementById("pwd").value;
            if(pwd.length<=0){
                document.getElementById("checkPwd").innerHTML= "<font style='font-family:arial;font-size:80%;color:red'>" + i18n("c.login.pwd.isEmpty") + "</font>";
                return false;
            }
            document.forms['LogFrm'].submit();
        }
    </script>
    <script language="javascript">

        document.onkeydown = function(e){
            if(!e) e = window.event;//火狐中是 window.event
            if((e.keyCode || e.which) == 13){
                checkForm();//提交表单
            }
        }
    </script>
</head>

<body onload=getfocus()>

<table width="100%" height="100%" border="0" cellpadding="0" cellspacing="0">
    <tr>
        <form name="LogFrm" action="j_spring_security_check" method="post" onsubmit="checkForm();">
            <td>
                <table width="100%" border="0" cellspacing="0" cellpadding="0">
                    <tr>
                        <td height="111" >
                            <table width="300" height="95" border="0" align="center" cellpadding="0" cellspacing="0">
                                <tr>
                                    <td width="40%" align="left"><span class="font5">用户名称：</span></td>
                                    <td width="60%">
                                        <input id='userId' name="j_username" onchange="document.getElementById('checkUserId').innerHTML=''" onfocus="document.getElementById('checkUserId').innerHTML=''" type="text" style="background-image:url(${pageContext.request.contextPath}/_images/input_bg.gif); height:23px; width:182px">
                                    </td>

                                </tr>
                                <tr>
                                    <td></td>
                                    <td><div id="checkUserId"></div></td>
                                </tr>
                                <tr>
                                    <td align="left"><span class="font5">用户密码：</span></td>
                                    <td>
                                        <input id="pwd" name="j_password" onchange="document.getElementById('checkUserId').innerHTML=''" onfocus="document.getElementById('checkPwd').innerHTML=''" type="password" style="background-image:url(${pageContext.request.contextPath}/_images/input_bg.gif); height:23px; width:182px">
                                    </td>
                                </tr>
                                <tr>
                                    <td></td>
                                    <td><div id="checkPwd"></div></td>
                                </tr>
                            </table>
                        </td>
                    </tr>
                    <tr align="center">
                    
                        <td height="39" >
                        	
                            <table width="122" height="17" border="0" align="center" cellpadding="0" cellspacing="0">
                                <tr align="center">                               
                                    <input type="button" onclick="checkForm();" value="提交"/>
                                </tr>
                            </table>
                        </td>
                    </tr>             
                </table>
            </td>
        </form>
    </tr>
</table>

</body>
</html>
