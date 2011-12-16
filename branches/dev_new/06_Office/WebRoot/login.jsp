<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@page import="com.xpsoft.core.util.AppUtil"%>
<%@page import="org.apache.commons.lang.StringUtils"%>
<HTML>
	<HEAD>
		<TITLE>欢迎登录<%=AppUtil.getCompanyName()%>协同办公系统</TITLE>
		
		<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/ext3/resources/css/ext-all.css" />
		<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/ext3/resources/css/ext-patch.css" />
		<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/login.css" />
		<%
		response.addHeader("__timeout","true");
		String codeEnabled=(String)AppUtil.getSysConfig().get("codeConfig");
		if(StringUtils.isEmpty(codeEnabled)){//若当前数据库没有配置验证码参数
			codeEnabled="1";//代表需要输入
		}
		request.setAttribute("codeEnabled", new Integer(codeEnabled));
		%>
		<script type="text/javascript">
			var __ctxPath="<%=request.getContextPath() %>";
			var __loginImage=__ctxPath+"<%=AppUtil.getCompanyLogo()%>";
		</script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/ext3/adapter/ext/ext-base.gzjs"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/ext3/ext-all.gzjs"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/ext3/ext-lang-zh_CN.js"></script>
		
		<style type="text/css">
		<!--
		body {
			margin-left: 0px;
			margin-top: 0px;
			margin-right: 0px;
			margin-bottom: 0px;
		}
		-->
		</style>
		<script type="text/javascript">
				function checkForm(){
						var checkBlank=true;
						checkBlank=Ext.getCmp("namefield").isValid();
						checkBlank=Ext.getCmp("pwdfield").isValid();
						<c:if test="${codeEnabled eq 1}">
							checkBlank=Ext.getCmp("codefield").isValid();					
						</c:if>
						if(!checkBlank){
							Ext.Msg.alert("提示", "请正确输入登陆信息！");
							return;
						}
						var username = Ext.getCmp("namefield").getValue();
						var password = Ext.getCmp("pwdfield").getValue();
						var checkCode = "";
						/*if(username==""){
								Ext.Msg.alert("提示", "用户名不能为空！");
								return;
						}
						if(password==""){
							Ext.Msg.alert("提示", "密码不能为空！");
								return;
						}*/
						<c:if test="${codeEnabled eq 1}">
							checkCode = Ext.getCmp("codefield").getValue();
							/*if(checkCode==""){
									Ext.Msg.alert("提示", "验证码不能为空！");
									return;
							}*/
						</c:if>
						Ext.Ajax.request({
							url : __ctxPath + "/login.do",
							method :"Post",
							params : {username:username,
								password:password,
								checkCode:checkCode
								},
							success : function(d, g) {
								var f = Ext.util.JSON.decode(d.responseText);
								handleLoginResult(f);
							}
						});
				}
				
				function handleLoginResult(a) {
					if (a.success) {
						var b = new Ext.ProgressBar({
							text : "正在登录..."
						});
						b.show();
						window.location.href = __ctxPath + "/index.jsp";
					} else {
						Ext.MessageBox.show({
							title : "操作信息",
							msg : a.msg,
							buttons : Ext.MessageBox.OK,
							icon : Ext.MessageBox.ERROR
						});
					}
				}
				
				function refeshCode() {
					var a = document.getElementById("loginCode");
					a.innerHTML='<img border="0" height="30" width="150" src="' + __ctxPath
							+ '/CaptchaImg?rand=' + Math.random() + '"/><a href="javascript:refeshCode()">换一张</a>';
				}
				
				document.onkeydown = function(e){
					if(!e) e = window.event;//火狐中是window.event
					if((e.keyCode || e.which) == 13){
							checkForm();
					}
				}
				Ext.onReady(function(){	
		
				    Ext.QuickTips.init();
				    Ext.state.Manager.setProvider(new Ext.state.CookieProvider()); 
					var username = new Ext.form.TextField({id:"namefield",allowBlank:false,width:200,name:"username",renderTo :"username"});
					username.focus();
					var password = new Ext.form.TextField({id:"pwdfield",allowBlank:false,width:200,name:"password",inputType:"password",renderTo :"password"});
					var password = new Ext.form.TextField({id:"codefield",allowBlank:false,width:200,name:"checkCode",renderTo :"checkCode"});
				});
		
				
		</script>
		
		<STYLE>
		* {
			margin: 0;
			padding: 0
		}
		
		BODY {
			border-top-width: 0px;
			border-left-width: 0px;
			border-bottom-width: 0px;
			margin: 0px auto 0 auto;
			font-size: 12px;
			font-family: 宋体;
			background-color: #fff;
			border-right-width: 0px;
			color: #000000;
			background-color:#154d88;
		}
		
		a:link {
			font-size: 12px;
			font-weight: normal;
			color: #000000;
			text-decoration: none;
		}
		
		a:visited {
			font-size: 12px;
			font-weight: normal;
			color: #000000;
			text-decoration: none;
		}
		
		a:hover {
			font-size: 12px;
			font-weight: normal;
			color: #FF8000;
			text-decoration: none;
		}
		
		a:active {
			font-size: 12px;
			font-weight: normal;
			color: #000000;
			text-decoration: none;
		}
		
		a img {
			border: none
		}
		</STYLE>

		<style type="text/css" vsbcode="AutoCreate">
		</style>
		<META Name="keywords" Content="阳泉红盾信息网  阳泉市工商行政管理局,阳泉市工商行政管理局">
	</HEAD>
	<SCRIPT LANGUAGE='javascript'>
<!--
	function _vsb_jsq() {
		var c = navigator.appName == 'Netscape' ? screen.pixelDepth
				: screen.colorDepth;
		var r = '&size=' + screen.width + 'x' + screen.height
				+ '&treeid=112&color=' + c + '&pageurl='
				+ escape(document.location) + '&refer='
				+ escape(document.referrer) + '&pagename=/email.jsp&newsid=';
		document
				.write('<IMG width=0 height=0 style=display:none src=/system/resource/code/datainput.jsp?owner=638695886'
						+ r + '>');
	}
	_vsb_jsq();
//-->
</SCRIPT>
	<BODY>
		<CENTER>
		<TABLE style="background-color:#ffffff;" cellspacing="0" cellpadding="0" width="990" align="center" border="0" >
			<TBODY>
				<TR>
					<TD width="574" height="134">
						<IMG height="134" src="./images/biao_2.jpg" width="574">
					</TD>
					<TD width="35" rowspan="3"></TD>
					<TD valign="center" align="right" width="381">
						<!-- 
						<A href="http://www.yqaic.gov.cn">返回首页</A>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						 -->
					</TD>
				</TR>
				<TR>
					<TD height="346">
						<IMG height="346" src="./images/left_4.jpg" width="574">
					</TD>
					<TD valign="top">
						<TABLE cellspacing="0" cellpadding="0" width="100%" border="0">
							<TBODY>
								<TR>
									<TD colspan="3" height="42"></TD>
								</TR>
								<TR>
									<TD colspan="3" height="34">
										<IMG height="34" src="./images/right_6.jpg" width="147">
									</TD>
								</TR>
								<TR>
									<TD colspan="3" height="25"></TD>
								</TR>
								<TR>
									<TD style="FONT-SIZE: 13px; COLOR: #000000" align="middle"
										width="61" height="30">
										用户名：
									</TD>
									<TD colspan="2">
										<div id="username">
									</TD>
								</TR>
								<!--
								<TR>
									<TD colspan="3" height="14"></TD>
								</TR>
								-->
								<TR>
									<TD style="FONT-SIZE: 13px; COLOR: #000000" align="middle"
										width="61" height="30">
										密&nbsp;&nbsp;码：
									</TD>
									<TD colspan="2">
										<div id="password"></div>
									</TD>
								</TR>
								<c:if test="${codeEnabled eq 1}">
								<!--
								<TR>
									<TD colspan="3" height="14"></TD>
								</TR>
								-->
								<TR>
									<TD style="FONT-SIZE: 13px; COLOR: #000000" align="middle"
										width="61" height="30">
										验证码：
									</TD>
									<TD colspan="2">
										<div id="checkCode"></div>
									</TD>
								</TR>
								<TR>
					              <TD class="blue"></TD>
					              <TD id="loginCode"><img border="0" height="30" width="150" src="<%=request.getContextPath() %>/CaptchaImg"/><a href="javascript:refeshCode()">换一张</a></TD>
					              <TD>&nbsp;</TD>
					            </TR>
					            </c:if>
					            <TR>
					              <TD>&nbsp;</TD>
					              <TD>
					              <TABLE width="100%" height="32" border="0" cellpadding="0" cellspacing="0">
					                  <TR>
					                    <TD width="36%">
					                    <LABEL>
					                      <input type="checkbox" name="_spring_security_remember_me" value="checkbox"/>
					                    </LABEL>
					                    </TD>
					                    <TD width="64%" align="left" class="black">记住密码</TD>
					                  </TR>
					              </TABLE>
					              </TD>
					              <TD>&nbsp;</TD>
					            </TR>
								<TR>
									<TD colspan="3" height="25"></TD>
								</TR>
								<TR>
									<TD align="middle" colspan="3" height="31">
										<TABLE cellspacing="0" cellpadding="0" width="100%" border="0">
											<TBODY>
												<TR>
													<TD width="90"></TD>
													<TD width="90" height="31">
														<IMG height="31" onclick="checkForm();" src="./images/login_10.jpg" width="90">
													</TD>
													<TD width="90"></TD>
													<TD width="110"></TD>
												</TR>
											</TBODY>
										</TABLE>
									</TD>
								</TR>
								<!--
								<TR>
									<TD valign="top" colspan="3" height="101">
										<TABLE cellspacing="0" cellpadding="0" width="100%" border="0">
											<TBODY>
												<TR>
													<TD colspan="2" height="20"></TD>
												</TR>
												<TR>
													<TD align="middle" colspan="2" height="9">
														<IMG height="9" src="./images/xian_3.jpg" width="322">
													</TD>
												</TR>
												<TR>
											</TBODY>
										</TABLE>
									</TD>
								</TR>
								-->
							</TBODY>
						</TABLE>
					</TD>
				</TR>
				<TR>
					<TD height="66">
						<IMG height="67" src="./images/left-di_5.jpg" width="574">
					</TD>
					<TD>
						&nbsp;
					</TD>
				</TR>
			</TBODY>
		</TABLE>
		<TABLE cellspacing="0" cellpadding="0" width="990" align="center"
			border="0">
			<TBODY>
				<TR>
					<TD background="./images/tiao_19.jpg" height="5"></TD>
				</TR>
				<TR>
					<TD valign="center" align="middle" bgcolor="#f3f3f3" height="100">
						<%=AppUtil.getCompanyName()%>
					</TD>
				</TR>
			</TBODY>
		</TABLE>
		</CENTER>
	</BODY>
</HTML>
