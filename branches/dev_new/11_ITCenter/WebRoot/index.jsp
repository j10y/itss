<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@include file="/includefiles.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<title>IT服务支持中心（ITcenter)</title>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-1.6.1.min.js">
		</script>
		<script type="text/javascript">
	function switchMenu(curr_id, total_num) {

		for ( var i = 0; i < total_num; i++) {
			var title = document.getElementById(i);

			var div = document.getElementById('newsDiv' + i);
			if (i == curr_id) {
				title.className = "news_menu_over";
				if (curr_id == 0) {
					title.background = "images/index2_12.gif";
				} else if (curr_id == 1) {
					title.background = "images/index2_05.gif";
				}
				div.style.display = "block";

			} else {
				title.className = "news_menu_out";
				if (curr_id == 0) {
					title.background = "images/index2_09.gif";
				} else if (curr_id == 1) {
					title.background = "images/index2_03.gif";
				}
				div.style.display = "none";
			}
		}
	}

	function showDiv(curr_id, total_num) {
		for ( var i = 2; i <= total_num; i++) {
			var div = document.getElementById('Layer' + i);
			if (i == curr_id) {
				div.style.display = "block";
			} else {
				div.style.display = "none";
			}
		}
	}
	function disShowDIv(id) {
		var div = document.getElementById('Layer' + id);
		div.style.display = "none";
	}
	function getKnowledgeInfo(columnType) {
		var text = '';
		Ext.Ajax
				.request(
						{ //读取操作手册及常见问题的栏目里的信息
							url : webContext
									+ '/knowledgeAction_getInfosAction.action',
							params : {
								infoLength : '30', //信息标题长度
								offset : '0', //取第N些条信息开始
								length : '6', //取第N些条信息结束
								columnType : columnType
							//栏目类型	knowledge 实体 0:代表所有类型 ，4:客户端，  5:邮件 ，8:网络 ，12: 协同， 14：ERP 
							},
							success : function(response, options) {
								var responseArray = Ext.util.JSON
										.decode(response.responseText);
								for ( var i = 0; i < responseArray.length; i++) {
									text += "<table width='100%' border='0' cellspacing='1' cellpadding='0'>";
									text += "<tr>";
									text += "<td width='8' rowspan='2'><img src='images/1x1_pix.gif' width='8' height='15' /></td>";
									text += "<td width='10'><img src='images/sidebar_li_br.gif' width='6' height='6' /></td>";
									text += " <td height='26' class='bannertxt' align='left'>&nbsp;&nbsp;<span class='bannertxt'><a class='nomalLink' href='"+responseArray[i].linkUrl+"' target='_blank'>"
											+ responseArray[i].summary
											+ "</a></span></td>";
									text += " <td  class='bluetxt' style='padding-right:15px;' align='right'>("
											+ responseArray[i].createDate
											+ ")</td>";
									text += " </tr>";
									text += "<tr>";
									text += "<td height='1' colspan='3' background='images/dotted_grey.gif'><img src='images/1x1_pix.gif' width='1' height='1' /></td>";
									text += "</tr>";
									text += "</table>";
								}
								text += "<table width='100%' border='0' cellspacing='0' cellpadding='3'>";
								text += "<tr>";
								text += "<td><div align='right' class='bannertxt'><a href='${pageContext.request.contextPath}/knowledgeAction_getListInfoAction.action?infoLength=30&pageSize=10&columnType="
										+ columnType
										+ "' class='bannertxt' target='_blank'>更多</a></div></td>";
								text += "<td width='14'>&nbsp;</td>";
								text += "</tr></table>";
								document.getElementById("knowledge0").className = "beforeClick";
								document.getElementById("knowledge5").className = "beforeClick";
								document.getElementById("knowledge8").className = "beforeClick";
								document.getElementById("knowledge4").className = "beforeClick";
								document.getElementById("knowledge14").className = "beforeClick";
								document.getElementById("knowledge12").className = "beforeClick";
								document.getElementById("knowledge13").className = "beforeClick";
								document.getElementById("knowledge10").className = "beforeClick";
								document.getElementById("knowledge"
										+ columnType).className = "afterClick";
								document.getElementById("knowledgeArea").innerHTML = text;

							},
							failure : function(response, options) {
							}
						}, this);
	}

	function UTF8UrlEncode(input) {
		var output = "";
		var currentChar = '';

		for ( var counter = 0; counter < input.length; counter++) {
			currentChar = input.charCodeAt(counter);

			if ((48 <= currentChar) && (currentChar <= 57))
				output = output + input.charAt(counter);
			else if ((65 <= currentChar) && (currentChar <= 90))
				output = output + input.charAt(counter);
			else if ((97 <= currentChar) && (currentChar <= 122))
				output = output + input.charAt(counter);
			else
				output = output + UTF8UrlEncodeChar(currentChar);
		}
		return output;
	}

	function UTF8UrlEncodeChar(input) {
		if (input <= 0x7F)
			return "%" + input.toString(16);

		var leadByte = 0xFF80;
		var hexString = "";
		var leadByteSpace = 5;
		while (input > (Math.pow(2, leadByteSpace + 1) - 1)) {
			hexString = "%" + ((input & 0x3F) | 0x80).toString(16) + hexString;
			leadByte = (leadByte >> 1);
			leadByteSpace--;
			input = input >> 6;
		}
		return ("%" + (input | (leadByte & 0xFF)).toString(16) + hexString)
				.toUpperCase();
	}
	function doDcSearch() {
		var input = document.getElementById("textfield").value;
		var utf8Value = encodeURI(input);
		alert("无连接");
		//var searchUrl = "http://10.1.120.181:9080/DCSearch/search.action?col=all&key="+utf8Value+"&itcode=anonymous";
		//window.open(searchUrl);
	}
	/**
	*查询用户信息
	*/
	function searchUser(){
		var username = document.getElementById("fieldUserSearch").value;
		//提交信息，调用servlet-->wbservice
		if(document.getElementById("fieldUserSearch").value==="请输入账号或姓名"){
			return;
		}
		if(username.length==0){
			document.getElementById("fieldUserSearch").value="请输入账号或姓名";
			return;
		}else{
			window.open("${pageContext.request.contextPath}/userAction_getUserListByParam.action?firstFlag=1&username=" + username);
			return;
			$.post("${pageContext.request.contextPath}/userAction_getUserListByParam.action", { username : username },
   				function(data){
     			alert(data);
  				document.getElementById("fieldUserSearch").value="请输入账号或姓名";
   			});				
			
		}
		
		
	}
</script>
	</head>

	<body>
		<%@include file="/header.jsp"%>
		<script language="javascript" for="document" event="onkeydown">
	var keyCode = event.keyCode ? event.keyCode : event.which ? event.which
			: event.charCode;
	if (keyCode == 13) {
		doDcSearch();
	}
</script>
		<table width="940" align="center" cellpadding="0" cellspacing="3"
			bgcolor="#FFFFFF">
			<tr>
				<td valign="top">
					<table width="173" border="0" cellpadding="5" cellspacing="1"
						bgcolor="#FFFFFF">
						<tr>
							<td valign="middle" bgcolor="#FFFFFF">
								<img src="images/ITservice01.gif" alt="" width="173" height="29" />
							</td>
						</tr>
						<tr>
							<td valign="top" bgcolor="#FFFFFF">
								<table width="100%" border="0" cellspacing="0" cellpadding="0">
									<tr>
										<td>
											<table width="165" border="0" cellspacing="1" cellpadding="0">
												<tr>
													<td width="8" rowspan="2">
														<img src="images/1x1_pix.gif" width="8" height="15" />
													</td>
													<td width="7">
														<img src="images/arrow_red_down.gif" width="5" height="12" />
													</td>
													<td height="23" class="bannertxtbold">
														服务目录
													</td>
												</tr>
												<tr>
													<td height="1" colspan="2" bgcolor="#999999">
														<img src="images/1x1_pix.gif" width="1" height="1" />
													</td>
												</tr>
											</table>
										</td>
									</tr>
									<tr>
										<td>
											<table width="165" border="0" cellspacing="1" cellpadding="0">
												<tr>
													<td width="8" rowspan="2">
														<img src="images/1x1_pix.gif" width="8" height="15" />
													</td>
													<td width="7">
														<img src="images/1x1_pix.gif" alt="" width="8" height="15" />
													</td>
													<td height="23" class="bannertxt">
														<a href="" target="_blank" class="nomalLink">服务目录</a>
													</td>
												</tr>
												<tr>
													<td height="1" colspan="2"
														background="images/dotted_grey.gif">
														<img src="images/1x1_pix.gif" width="1" height="1" />
													</td>
												</tr>
											</table>
											<table width="165" border="0" cellspacing="1" cellpadding="0">
												<tr>
													<td width="8" rowspan="2">
														<img src="images/1x1_pix.gif" width="8" height="15" />
													</td>
													<td width="7">
														<img src="images/1x1_pix.gif" alt="" width="8" height="15" />
													</td>
													<td height="23" class="bannertxt">
														<a href="http://localhost:8080/bmrbs/" target="_blank" class="nomalLink">会议室预订</a>
													</td>
												</tr>
												<tr>
													<td height="1" colspan="2"
														background="images/dotted_grey.gif">
														<img src="images/1x1_pix.gif" width="1" height="1" />
													</td>
												</tr>
											</table>
											<table width="165" border="0" cellspacing="1" cellpadding="0">
												<tr>
													<td width="8" rowspan="2">
														<img src="images/1x1_pix.gif" width="8" height="15" />
													</td>
													<td width="7">
														<img src="images/1x1_pix.gif" alt="" width="8" height="15" />
													</td>
													<td height="23" class="bannertxt">
														<a href="http://mail.shopin.cn/" target="_blank" class="nomalLink">企业邮箱</a>
													</td>
												</tr>
												<tr>
													<td height="1" colspan="2"
														background="images/dotted_grey.gif">
														<img src="images/1x1_pix.gif" width="1" height="1" />
													</td>
												</tr>
											</table>
										</td>
									</tr>
								</table>
								<table width="100%" border="0" cellspacing="0" cellpadding="0">
									<tr>
										<td>
											<table width="165" border="0" cellspacing="1" cellpadding="0">
												<tr>
													<td width="8" rowspan="2">
														<img src="images/1x1_pix.gif" width="8" height="15" />
													</td>
													<td width="7">
														<img src="images/arrow_red_down.gif" alt="" width="5"
															height="12" />
													</td>
													<td height="23" class="bannertxtbold">
														IT服务申请
													</td>
												</tr>
												<tr>
													<td height="1" colspan="2" bgcolor="#999999">
														<img src="images/1x1_pix.gif" alt="" width="1" height="1" />
													</td>
												</tr>
											</table>
										</td>
									</tr>
									<tr>
										<td>
											<table width="165" border="0" cellspacing="1" cellpadding="0">
												<tr>
													<td width="8" rowspan="2">
														<img src="images/1x1_pix.gif" width="8" height="15" />
													</td>
													<td width="7">
														<img src="images/1x1_pix.gif" alt="" width="8" height="15" />
													</td>
													<td height="23" class="bannertxt">
														<a href="" target="_blank" class="nomalLink">提交IT服务申请(ITSS)</a>
													</td>
												</tr>
												<tr>
													<td height="1" colspan="2"
														background="images/dotted_grey.gif">
														<img src="images/1x1_pix.gif" width="1" height="1" />
													</td>
												</tr>
											</table>
										</td>
									</tr>
								</table>
								<table width="100%" border="0" cellspacing="0" cellpadding="0">
									<tr>
										<td>
											<table width="165" border="0" cellspacing="1" cellpadding="0">
												<tr>
													<td width="8" rowspan="2">
														<img src="images/1x1_pix.gif" width="8" height="15" />
													</td>
													<td width="7">
														<img src="images/arrow_red_down.gif" alt="" width="5"
															height="12" />
													</td>
													<td height="23" class="bannertxtbold">
														IT新服务
													</td>
												</tr>
												<tr>
													<td height="1" colspan="2" bgcolor="#999999">
														<img src="images/1x1_pix.gif" alt="" width="1" height="1" />
													</td>
												</tr>
											</table>
										</td>
									</tr>
									<tr>
										<td>
											<table width="165" border="0" cellspacing="1" cellpadding="0">
												<tr>
													<td width="8" rowspan="2">
														<img src="images/1x1_pix.gif" width="8" height="15" />
													</td>
													<td width="7">
														<img src="images/1x1_pix.gif" alt="" width="8" height="15" />
													</td>
													<td height="23" class="bannertxt">
														<a href="" target="_blank" class="nomalLink">远程接入软令牌</a>
													</td>
												</tr>
												<tr>
													<td height="1" colspan="2"
														background="images/dotted_grey.gif">
														<img src="images/1x1_pix.gif" width="1" height="1" />
													</td>
												</tr>
											</table>
											<table width="165" border="0" cellspacing="1" cellpadding="0">
												<tr>
													<td width="8" rowspan="2">
														<img src="images/1x1_pix.gif" width="8" height="15" />
													</td>
													<td width="7">
														<img src="images/1x1_pix.gif" alt="" width="8" height="15" />
													</td>
													<td height="23" class="bannertxt">
														<a href="" target="_blank" class="nomalLink">Notes客户端升级</a>
													</td>
												</tr>
												<tr>
													<td height="1" colspan="2"
														background="images/dotted_grey.gif">
														<img src="images/1x1_pix.gif" width="1" height="1" />
													</td>
												</tr>
											</table>
											<table width="165" border="0" cellspacing="1" cellpadding="0">
												<tr>
													<td width="8" rowspan="2">
														<img src="images/1x1_pix.gif" width="8" height="15" />
													</td>
													<td width="7">
														<img src="images/1x1_pix.gif" alt="" width="8" height="15" />
													</td>
													<td height="23" class="bannertxt">
														<a href="" target="_blank" class="nomalLink">企业移动终端（试用）</a>
													</td>
												</tr>
												<tr>
													<td height="1" colspan="2"
														background="images/dotted_grey.gif">
														<img src="images/1x1_pix.gif" width="1" height="1" />
													</td>
												</tr>
											</table>
											<table width="165" border="0" cellspacing="1" cellpadding="0">
												<tr>
													<td width="8" rowspan="2">
														<img src="images/1x1_pix.gif" width="8" height="15" />
													</td>
													<td width="7">
														&nbsp;
													</td>
													<td height="23">
														<a href="" target="_blank" class="nomalLink">团队协作空间（试用）</a>
													</td>
												</tr>
												<tr>
													<td height="1" colspan="2"
														background="images/dotted_grey.gif">
														<img src="images/1x1_pix.gif" width="1" height="1" />
													</td>
												</tr>
											</table>
										</td>
									</tr>
								</table>
								<table width="100%" border="0" cellspacing="0" cellpadding="0">
									<tr>
										<td>
											<table width="165" border="0" cellspacing="1" cellpadding="0">
												<tr>
													<td width="8" rowspan="2">
														<img src="images/1x1_pix.gif" width="8" height="15" />
													</td>
													<td width="7">
														<img src="images/arrow_red_down.gif" alt="" width="5"
															height="12" />
													</td>
													<td height="23" class="bannertxtbold">
														我的服务信息
													</td>
												</tr>
												<tr>
													<td height="1" colspan="2" bgcolor="#999999">
														<img src="images/1x1_pix.gif" alt="" width="1" height="1" />
													</td>
												</tr>
											</table>
										</td>
									</tr>
									<tr>
										<td>
											<table width="165" border="0" cellspacing="1" cellpadding="0">
												<tr>
													<td width="8" rowspan="2">
														<img src="images/1x1_pix.gif" width="8" height="15" />
													</td>
													<td width="7">
														<img src="images/1x1_pix.gif" alt="" width="8" height="15" />
													</td>
													<td height="23" class="bannertxt">
														<a href="/user/require/account.jsp" target="_blank"
															class="nomalLink">我的帐号信息</a>
													</td>
												</tr>
												<tr>
													<td height="1" colspan="2"
														background="images/dotted_grey.gif">
														<img src="images/1x1_pix.gif" width="1" height="1" />
													</td>
												</tr>
											</table>
											<table width="165" border="0" cellspacing="1" cellpadding="0">
												<tr>
													<td width="8" rowspan="2">
														<img src="images/1x1_pix.gif" width="8" height="15" />
													</td>
													<td width="7">
														<img src="images/1x1_pix.gif" alt="" width="8" height="15" />
													</td>
													<td height="23" class="bannertxt">
														<a href="/user/event/eventReport/MyEvents.jsp"
															target="_blank" class="nomalLink">我提过的问题</a>
													</td>
												</tr>
												<tr>
													<td height="1" colspan="2"
														background="images/dotted_grey.gif">
														<img src="images/1x1_pix.gif" width="1" height="1" />
													</td>
												</tr>
											</table>
											<table width="165" border="0" cellspacing="1" cellpadding="0">
												<tr>
													<td width="8" rowspan="2">
														<img src="images/1x1_pix.gif" width="8" height="15" />
													</td>
													<td width="7">
														<img src="images/1x1_pix.gif" alt="" width="8" height="15" />
													</td>
													<td height="23" class="bannertxt">
														<a
															href="/user/require/report/requireApplyMySelfReport.jsp"
															target="_blank" class="nomalLink">我提过的需求</a>
													</td>
												</tr>
												<tr>
													<td height="1" colspan="2"
														background="images/dotted_grey.gif">
														<img src="images/1x1_pix.gif" width="1" height="1" />
													</td>
												</tr>
											</table>
											<table width="165" border="0" cellspacing="1" cellpadding="0">
												<tr>
													<td width="8" rowspan="2">
														<img src="images/1x1_pix.gif" width="8" height="15" />
													</td>
													<td width="7">
														&nbsp;
													</td>
													<td height="23" class="bannertxt">
														<a href="" target="_blank" class="nomalLink">WWW费用查询</a>
													</td>
												</tr>
												<tr>
													<td height="1" colspan="2"
														background="images/dotted_grey.gif">
														<img src="images/1x1_pix.gif" width="1" height="1" />
													</td>
												</tr>
											</table>
											<table width="165" border="0" cellspacing="1" cellpadding="0">
												<tr>
													<td width="8" rowspan="2">
														<img src="images/1x1_pix.gif" width="8" height="15" />
													</td>
													<td width="7">
														<img src="images/1x1_pix.gif" alt="" width="8" height="15" />
													</td>
													<td height="23" class="bannertxt">
														<a href="" target="_blank" class="nomalLink">通讯费用查询</a>
													</td>
												</tr>
												<tr>
													<td height="1" colspan="2"
														background="images/dotted_grey.gif">
														<img src="images/1x1_pix.gif" width="1" height="1" />
													</td>
												</tr>
											</table>
										</td>
									</tr>
								</table>
								<table width="165" border="0" cellpadding="0" cellspacing="0">
									<tr>
										<td class="style1">
											<img src="images/1x1_pix.gif" width="38" height="20" />
										</td>
									</tr>
								</table>
							</td>
						</tr>
					</table>
				</td>
				<td valign="top" background="images/kropli_linia_top.gif"
					bgcolor="#FFFFFF">
					<img src="images/1x1_pix.gif" width="1" height="2" />
				</td>
				<td align="center" valign="top">
					<table width="100%" border="0" cellspacing="1" cellpadding="5">
						<tr>
							<td>
								<a href="" target="_blank"><img src="images/ad.jpg"
										style="border: 0" height="140" />
								</a>
							</td>
						</tr>

					</table>
					<table width="500" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td>
								<img src="images/1x1_pix.gif" width="50" height="8" />
							</td>
						</tr>
						<tr>
							<td>
								<img src="images/ITservicesupport.png" width="500" height="29" />
							</td>
						</tr>
					</table>
					<table width="500" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td class="bannertxt">
								<img src="images/1x1_pix.gif" alt="" width="20" height="15" />
							</td>
						</tr>
					</table>
					<table width="500" border="0" cellpadding="0" cellspacing="0">
						<tr>
							<td valign="top">
								<table width="100%" border="0" cellspacing="0" cellpadding="0">
									<tr>
										<td id="0" onClick="switchMenu(0,2)" width="105" height="25"
											class="news_menu_out" align="center"
											background="images/index2_03.gif">
											IT小贴士
										</td>
										<td width="4"></td>
										<td id="1" onClick="switchMenu(1,2)" width="162"
											class="news_menu_over" background="images/index2_05.gif">
											操作手册及常见问题
										</td>
										<td width="229">
											&nbsp;
										</td>
									</tr>
								</table>
								<table width="100%" border="0" cellspacing="1" cellpadding="0"
									bgcolor="#CCCCCC">
									<tr>
										<td colspan="4" valign="top" height="240" bgcolor="#F4F4F4">


											<div id="newsDiv0" style="display: none;">
												<!-- IT小贴士开始 -->
											</div>
											<!-- IT小贴士end -->

											<div id="newsDiv1" style="display: block;">
												<!-- 操作手册及常见问题开始 -->
												<table width='100%' border='0' cellspacing='1'
													cellpadding='0'>
													<tr>
														<td width='8' rowspan='2'>
															<img src='images/1x1_pix.gif' width='8' height='15' />
														</td>
														<td width='60'>
															<a id="knowledge0" class='afterClick'
																onClick='getKnowledgeInfo(0)'>所有更新</a>
														</td>
														<td height='30' class='bannertxt' align='right'
															style="padding-right: 10px;">
															<a id="knowledge5" class="beforeClick"
																onClick='getKnowledgeInfo(5)'>邮件</a> |
															<a id="knowledge8" class='beforeClick'
																onClick='getKnowledgeInfo(8)'>网络及网络接入</a> |
															<a id="knowledge4" class='beforeClick'
																onClick='getKnowledgeInfo(4)'>客户端</a> |
															<a id="knowledge14" class='beforeClick'
																onClick='getKnowledgeInfo(14)'>ERP</a> |
															<a id="knowledge12" class='beforeClick'
																onClick='getKnowledgeInfo(12)'>协同</a> |
															<a id="knowledge13" class='beforeClick'
																onClick='getKnowledgeInfo(13)'>BI应用</a> |
															<a id="knowledge10" class='beforeClick'
																onClick='getKnowledgeInfo(10)'>IT安全</a>
														</td>
													</tr>
													<tr>
														<td height='1' colspan='2'
															background='images/dotted_grey.gif'>
															<img src='images/1x1_pix.gif' width='1' height='1' />
														</td>
													</tr>
												</table>
												<span id="knowledgeArea"></span>

											</div>
											<!-- 操作手册及常见问题end -->

										</td>
									</tr>
								</table>
							</td>
						</tr>
					</table>
					<table width="500" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td>
								<img src="images/1x1_pix.gif" width="50" height="15" />
							</td>
						</tr>
						<!--       
        <tr>
          <td height="13"><a href="${pageContext.request.contextPath}/noticeAction_getListInfoAction.action?infoLength=30&pageSize=10&columnType=4" target="_blank"><img src="images/ITpeixun.png" style="border: 0px" width="500" height="27" /></a></td>
        </tr>
 - -->
						<tr>
							<td height="20">
								<table width="500" border="0" cellspacing="0" cellpadding="0">
									<tr>
										<td width="20%" background="images/ITpeixun_02.gif">
											<img src="images/ITpeixun_01.gif"></img>
										</td>
										<td width="20%" background="images/ITpeixun_02.gif">
											<a href="" target="_blank"><img style="border: 0"
													src="images/ITpeixun_03.gif"></img>
											</a>
										</td>
										<td width="45%" background="images/ITpeixun_02.gif">
											<img src="images/ITpeixun_02.gif"></img>
										</td>
										<td width="10%" background="images/ITpeixun_02.gif">
											<a
												href="${pageContext.request.contextPath}/noticeAction_getListInfoAction.action?infoLength=30&pageSize=10&columnType=4"
												target="_blank"><img style="border: 0"
													src="images/ITpeixun_07.gif"></img>
											</a>
										</td>
										<td width="5%" background="images/ITpeixun_02.gif">
											&nbsp;
										</td>
									</tr>
								</table>
							</td>
						</tr>
						<tr>
							<td id="ITTrain" height="58">
							</td>
						</tr>
					</table>
					<table width="500" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td class="bannertxt">
								<img src="images/1x1_pix.gif" alt="" width="20" height="15" />
							</td>
						</tr>
					</table>
					<table width="401" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td>
								<img src="images/t1.png" width="500" height="31" />
							</td>
						</tr>
					</table>
					<table width="480" border="0" cellpadding="0" cellspacing="1"
						bgcolor="#e4e4e4">
						<tr>
							<td width="178" valign="top" bgcolor="#FFFFFF" align="left">
								<img src="images/online.png" width="95" height="26" />
							</td>
							<td width="174" valign="top" bgcolor="#FFFFFF">
								
							</td>
						</tr>
						<tr>
							<td width="50%" bgcolor="#FFFFFF" height="100" align="center">
								<table width="85%" border="0" cellspacing="0" cellpadding="0">
									<tr>
										<td width="50" rowspan="2" align="center">
											<img src="images/P1.png" width="19" height="20" />
										</td>
										<td>
											<p align="left" class="bannertxt">
												在线即时支持（建设中）
												<br />
											</p>
										</td>
									</tr>
								</table>
								<table width="85%" height="28" border="0" cellpadding="0"
									cellspacing="0">
									<tr>
										<td width="50" height="19" align="center">
											<img src="images/C1.png" width="21" height="19" />
										</td>
										<td>
											<p align="left" class="bannertxt">
												<a href="/user/event/eventNormal/eventCreate.jsp"
													target="_blank" class="nomalLink">网上问答</a>
											</p>
										</td>
									</tr>
								</table>
							</td>
							<td align="center" bgcolor="#FFFFFF">
								
							</td>
						</tr>
					</table>
					<table width="500" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td class="bannertxt">
								<img src="images/1x1_pix.gif" alt="" width="20" height="15" />
							</td>
						</tr>
					</table>
					<table width="460" border="0" cellpadding="3" cellspacing="0">
						
					</table>
				</td>
				<td valign="top" background="images/kropli_linia_top.gif">
					<img src="images/1x1_pix.gif" alt="" width="1" height="2" />
				</td>
				<td valign="top">
					<!-- 
    <table border="0" cellspacing="6" cellpadding="0">
      <tr>
        <td align="left" valign="middle"><div align="left">
          <input name="textfield" type="text" class="dcsoutextfiled" id="textfield" value="..输入搜索内容" onfocus="this.value=''" size="19" />
        </div></td>
        <td valign="middle"><img style="border:0;cursor: pointer;" src="images/DCSou_button.gif" onclick="doDcSearch()" width="55" height="21" class="dcsoutextfiled" /></td>
        <td width="30" valign="middle" ><a href="#" class="redtxt" target="_blank"></a></td>
      </tr>
    </table>
  -->
					<table width="173" border="0" cellpadding="5" cellspacing="1"
						bgcolor="#FFFFFF">
						<tr>
							<td>
								<img src="images/ITuserSearch.png" width="225" style="border: 0" height="29" />
							</td>
						</tr>						
						<tr>
							<td>
								<input id="fieldUserSearch" type="text" name="username" onfocus="this.value=''" value="请输入账号或姓名"/><input id="btnUserSearch" onclick="searchUser()" type="button" value="查询"/
							</td>
						</tr>
						<tr>
							<td valign="middle" bgcolor="#FFFFFF">
								<img src="images/ITservicemanage.png" alt="" width="225"
									height="34" />
							</td>
						</tr>
						<tr>
							<td valign="top" bgcolor="#FFFFFF">
								<table width="100%" border="0" cellspacing="0" cellpadding="0">
									<tr>
										<td>
											<a
												href="${pageContext.request.contextPath}/noticeAction_getListInfoAction.action?infoLength=30&pageSize=10&columnType=1"
												target="_blank"><img src="images/ITgonggao.png"
													width="225" style="border: 0" height="29" />
											</a>
										</td>
									</tr>
									<tr>
										<td valign="top">
											<span id="ITNOTICE"></span>
											<table width="165" border="0" cellpadding="0" cellspacing="0">
												<tr>
													<td class="style1">
														<img src="images/1x1_pix.gif" alt="" width="38"
															height="20" />
													</td>
												</tr>
											</table>
										</td>
									</tr>
								</table>
								<table width="100%" border="0" cellspacing="0" cellpadding="0">
									<tr>
										<td>
											<a
												href="${pageContext.request.contextPath}/knowFileListColumn.jsp?columnType=1"
												target="_blank"><img src="images/ITmanger.png"
													width="225" height="29" style="border: 0" />
											</a>
										</td>
									</tr>
									<tr>
										<td valign="top">
											<table width="100%" border="0" cellspacing="1"
												cellpadding="0">
												<tr>
													<td width="8" height='35'>
														<img src="images/sidebar_li_cat.gif" width="4" height="5" />
													</td>
													<td height="26">
														<a
															href="${pageContext.request.contextPath}/knowFileAction_getListInfoAction.action?infoLength=30&pageSize=10&columnType=1"
															class="nomalLink" target="_blank">IT管理制度</a>
													</td>
												</tr>
												<tr>
													<td colspan="2" background="images/dotted_grey.gif">
														<img src="images/1x1_pix.gif" width="1" height="1" />
													</td>
												</tr>
											</table>
											<table width="100%" border="0" cellspacing="1"
												cellpadding="0">
												<tr>
													<td width="8" height='35'>
														<img src="images/sidebar_li_cat.gif" width="4" height="5" />
													</td>
													<td height="26">
														<a
															href="${pageContext.request.contextPath}/knowFileAction_getListInfoAction.action?infoLength=30&pageSize=10&columnType=2"
															class="nomalLink" target="_blank">IT管理工作体系</a>
													</td>
												</tr>
												<tr>
													<td height="1" colspan="2"
														background="images/dotted_grey.gif">
														<img src="images/1x1_pix.gif" width="1" height="1" />
													</td>
												</tr>
											</table>
											<table width="100%" border="0" cellspacing="1"
												cellpadding="0">
												<tr>
													<td width="8" height='35'>
														<img src="images/sidebar_li_cat.gif" width="4" height="5" />
													</td>
													<td height="26">
														<a
															href="${pageContext.request.contextPath}/knowFileAction_getListInfoAction.action?infoLength=30&pageSize=10&columnType=3"
															class="nomalLink" target="_blank">IT服务报告
													</td>
												</tr>
												<tr>
													<td height="1" colspan="2"
														background="images/dotted_grey.gif">
														<img src="images/1x1_pix.gif" width="1" height="1" />
													</td>
												</tr>
											</table>
											<table width="100%" border="0" cellspacing="1"
												cellpadding="0">
												<tr>
													<td width="8" height='35'>
														<img src="images/sidebar_li_cat.gif" width="4" height="5" />
													</td>
													<td height="26">
														<a
															href="${pageContext.request.contextPath}/knowFileAction_getListInfoAction.action?infoLength=30&pageSize=10&columnType=5"
															class="nomalLink" target="_blank">IT违规通报
													</td>
												</tr>
												<tr>
													<td height="1" colspan="2"
														background="images/dotted_grey.gif">
														<img src="images/1x1_pix.gif" width="1" height="1" />
													</td>
												</tr>
											</table>
											<table width="165" border="0" cellpadding="0" cellspacing="0">
												<tr>
													<td class="style1">
														<img src="images/1x1_pix.gif" alt="" width="38"
															height="20" />
													</td>
												</tr>
											</table>
										</td>
									</tr>
								</table>
								<table width="100%" border="0" cellspacing="0" cellpadding="0">
									<tr>
										<td>
											<a
												href="${pageContext.request.contextPath}/knowFileListColumn.jsp?columnType=2"
												target="_blank"><img style="border: 0px"
													src="images/ITsupport.png" width="225" height="30" />
											</a>
										</td>
									</tr>
									<tr>
										<td id="serviceProvide" valign="top">

											<table width="100%" border="0" cellspacing="1"
												cellpadding="0">
												<tr>
													<td width="8" height='35'>
														<img src="images/sidebar_li_cat.gif" width="4" height="5" />
													</td>
													<td height="26">
														<a
															href="${pageContext.request.contextPath}/knowFileAction_getListInfoAction.action?infoLength=30&pageSize=10&columnType=4"
															class="nomalLink" target="_blank">IT实战</a>
													</td>
												</tr>
												<tr>
													<td height="1" colspan="2"
														background="images/dotted_grey.gif">
														<img src="images/1x1_pix.gif" width="1" height="1" />
													</td>
												</tr>
											</table>
											<table width="100%" border="0" cellspacing="1"
												cellpadding="0">
												<tr>
													<td width="8" height='35'>
														<img src="images/sidebar_li_cat.gif" width="4" height="5" />
													</td>
													<td height="26">
														<a
															href="${pageContext.request.contextPath}/knowFileAction_getListInfoAction.action?infoLength=30&pageSize=10&columnType=6"
															class="nomalLink" target="_blank">他山之石</a>
													</td>
												</tr>
												<tr>
													<td height="1" colspan="2"
														background="images/dotted_grey.gif">
														<img src="images/1x1_pix.gif" width="1" height="1" />
													</td>
												</tr>
											</table>
											<table width="100%" border="0" cellspacing="1"
												cellpadding="0">
												<tr>
													<td width="8" height='35'>
														<img src="images/sidebar_li_cat.gif" width="4" height="5" />
													</td>
													<td height="26">
														<a
															href="${pageContext.request.contextPath}/knowFileAction_getListInfoAction.action?infoLength=30&pageSize=10&columnType=7"
															class="nomalLink" target="_blank">所获奖项</a>
													</td>
												</tr>
												<tr>
													<td height="1" colspan="2"
														background="images/dotted_grey.gif">
														<img src="images/1x1_pix.gif" width="1" height="1" />
													</td>
												</tr>
											</table>
											<table width="100%" border="0" cellspacing="1"
												cellpadding="0">
												<tr>
													<td width="8" height='35'>
														<img src="images/sidebar_li_cat.gif" width="4" height="5" />
													</td>
													<td height="26">
														<a
															href="${pageContext.request.contextPath}/knowFileAction_getListInfoAction.action?infoLength=30&pageSize=10&columnType=8"
															class="nomalLink" target="_blank">外部专访</a>
													</td>
												</tr>
												<tr>
													<td height="1" colspan="2"
														background="images/dotted_grey.gif">
														<img src="images/1x1_pix.gif" width="1" height="1" />
													</td>
												</tr>
											</table>


										</td>
									</tr>
								</table>
								<table width="165" border="0" cellpadding="0" cellspacing="0">
									<tr>
										<td class="style1">
											<img src="images/1x1_pix.gif" width="38" height="15" />
										</td>
									</tr>
								</table>
								<a href="mailto:demo@shopin.com"><img src="images/more.png"
										width="227" height="70" style="border: 0px" />
								</a>
							</td>
						</tr>
					</table>
					<p class="bluetxt">
						&nbsp;
					</p>
				</td>
			</tr>
		</table>

		<%@include file="/footer.jsp"%>
	</body>
	<script type="text/javascript">
	var text = "";
	var text2 = "";
	var text3 = "";
	var text4 = "";
	var text5 = "";
	Ext.Ajax
			.request(
					{
						url : webContext
								+ '/noticeAction_getInfosAction.action',
						params : {
							infoLength : '17', //信息标题长度
							offset : '0', //取第N些条信息开始
							length : '5', //取第N些条信息结束
							columnType : '1' //栏目类型	1：IT公告
						},
						success : function(response, options) {
							var responseArray = Ext.util.JSON
									.decode(response.responseText);
							for ( var i = 0; i < responseArray.length; i++) {
								text += " <table width='100%' border='0' cellspacing='1' cellpadding='0'>";
								text += "<tr>";
								text += "<td width='8'><img src='images/sidebar_li_cat.gif' width='4' height='5' /></td>";
								text += "<td height='26'><span class='bluetxt'><a class='nomalLink' href='"+responseArray[i].linkUrl+"' target='_blank'>"
										+ responseArray[i].title
										+ "</a></span><span class='bluetxt'> "
										+ responseArray[i].isToday
										+ "</span></td>";
								text += " </tr>";
								text += "<tr>";
								text += "<td height='1' colspan='2' background='images/dotted_grey.gif'><img src='images/1x1_pix.gif' width='1' height='1' /></td>";
								text += "</tr>";
								text += "</table>";
							}
							document.getElementById("ITNOTICE").innerHTML = text;
						},
						failure : function(response, options) {
						}
					}, this);

	Ext.Ajax
			.request(
					{
						url : webContext
								+ '/noticeAction_getInfosAction.action',
						params : {
							infoLength : '30', //信息标题长度
							offset : '0', //取第N些条信息开始
							length : '6', //取第N些条信息结束
							columnType : '5' //栏目类型	5:IT小贴士
						},
						success : function(response, options) {
							var responseArray = Ext.util.JSON
									.decode(response.responseText);
							text2 += "<table width='100%' border='0' cellspacing='1' cellpadding='0'>";
							text2 += "<tr>";
							text2 += "<td height='14' colspan='3'  ></td>";
							text2 += "</tr>";
							text2 += "<tr>";
							text2 += "<td height='1' colspan='3'    background='images/dotted_grey.gif'><img src='images/1x1_pix.gif' width='1' height='1' /></td>";
							text2 += "</tr>";
							text2 += "</table>";
							for ( var i = 0; i < responseArray.length; i++) {
								text2 += "<table width='100%' border='0'  cellspacing='1' cellpadding='0'>";
								text2 += "<tr>";
								text2 += "<td width='8' rowspan='2'><img src='images/1x1_pix.gif' width='8' height='15' /></td>";
								text2 += "<td width='10'><img src='images/sidebar_li_br.gif' width='6' height='6' /></td>";
								text2 += " <td height='28' class='bannertxt' align='left'>&nbsp;&nbsp;<span class='bannertxt'><a class='nomalLink' href='"+responseArray[i].linkUrl+"' target='_blank'>"
										+ responseArray[i].title
										+ "</a></span></td>";
								text2 += " <td  class='bluetxt' style='padding-right:15px;' align='right'>("
										+ responseArray[i].createDate
										+ ")</td>";
								text2 += " </tr>";
								text2 += "<tr>";
								text2 += "<td height='1' colspan='3' background='images/dotted_grey.gif'><img src='images/1x1_pix.gif' width='1' height='1' /></td>";
								text2 += "</tr>";
								text2 += "</table>";
							}
							text2 += "<table width='100%' border='0' cellspacing='0' cellpadding='3'>";
							text2 += "<tr>";
							text2 += "<td><div align='right' class='bannertxt'><a href='${pageContext.request.contextPath}/noticeAction_getListInfoAction.action?infoLength=30&pageSize=10&columnType=5' class='bannertxt' target='_blank'>更多</a></div></td>";
							text2 += "<td width='14'>&nbsp;</td>";
							text2 += "</tr></table>";
							document.getElementById("newsDiv0").innerHTML = text2;
						},
						failure : function(response, options) {
						}
					}, this);

	Ext.Ajax
			.request(
					{
						url : webContext
								+ '/noticeAction_getInfosAction.action',
						params : {
							infoLength : '30', //信息标题长度
							offset : '0', //取第N些条信息开始
							length : '2', //取第N些条信息结束
							columnType : '4' //栏目类型	3:IT培训
						},
						success : function(response, options) {
							var responseArray = Ext.util.JSON
									.decode(response.responseText);
							text3 += "<table width='100%' border='0' cellspacing='1' cellpadding='0'>";
							text3 += "<tr>";
							text3 += "<td height='5' colspan='3' ></td>";
							text3 += "</tr>";
							text3 += "</table>";
							for ( var i = 0; i < responseArray.length; i++) {
								text3 += "<table width='100%' border='0' cellspacing='1' cellpadding='0'>";
								text3 += "<tr>";
								text3 += "<td width='8'><img src='images/1x1_pix.gif' width='8' height='15' /></td>";
								text3 += "<td width='1'><img src='images/entry_before.gif' width='13' height='9' /></td>";
								text3 += "<td height='28' align='left' class='bannertxt' ><a class='nomalLink' href='"+responseArray[i].linkUrl+"' target='_blank'>"
										+ responseArray[i].title + "</a></td>";
								text3 += " <td  class='bluetxt' style='padding-right:15px;' align='right'>("
										+ responseArray[i].createDate
										+ ")</td>";
								text3 += " </tr>";
								text3 += "<tr>";
								text3 += "<td height='1' colspan='4' background='images/dotted_grey.gif'><img src='images/1x1_pix.gif' width='1' height='1' /></td>";
								text3 += "</tr>";
								text3 += "</table>";
							}
							document.getElementById("ITTrain").innerHTML = text3;
						},
						failure : function(response, options) {
						}
					}, this);

	Ext.Ajax
			.request(
					{ //读取操作手册及常见问题的栏目里的信息
						url : webContext
								+ '/knowledgeAction_getInfosAction.action',
						params : {
							infoLength : '30', //信息标题长度
							offset : '0', //取第N些条信息开始
							length : '6', //取第N些条信息结束
							columnType : '0' //栏目类型	knowledge 实体 0:代表所有类型
						},
						success : function(response, options) {
							var responseArray = Ext.util.JSON
									.decode(response.responseText);
							for ( var i = 0; i < responseArray.length; i++) {
								text5 += "<table width='100%' border='0' cellspacing='1' cellpadding='0'>";
								text5 += "<tr>";
								text5 += "<td width='8' rowspan='2'><img src='images/1x1_pix.gif' width='8' height='15' /></td>";
								text5 += "<td width='10'><img src='images/sidebar_li_br.gif' width='6' height='6' /></td>";
								text5 += " <td height='26' class='bannertxt' align='left'>&nbsp;&nbsp;<span class='bannertxt'><a class='nomalLink' href='"+responseArray[i].linkUrl+"' target='_blank'>"
										+ responseArray[i].summary
										+ "</a></span></td>";
								text5 += " <td  class='bluetxt' style='padding-right:15px;' align='right'>("
										+ responseArray[i].createDate
										+ ")</td>";
								text5 += " </tr>";
								text5 += "<tr>";
								text5 += "<td height='1' colspan='3' background='images/dotted_grey.gif'><img src='images/1x1_pix.gif' width='1' height='1' /></td>";
								text5 += "</tr>";
								text5 += "</table>";
							}
							text5 += "<table width='100%' border='0' cellspacing='0' cellpadding='3'>";
							text5 += "<tr>";
							text5 += "<td><div align='right' class='bannertxt'><a href='${pageContext.request.contextPath}/knowledgeAction_getListInfoAction.action?infoLength=30&pageSize=10&columnType=0' class='bannertxt' target='_blank'>更多</a></div></td>";
							text5 += "<td width='14'>&nbsp;</td>";
							text5 += "</tr></table>";
							document.getElementById("knowledgeArea").innerHTML = text5;
						},
						failure : function(response, options) {
						}
					}, this);

	Ext.Ajax
			.request(
					{
						url : webContext
								+ '/knowFileAction_getInfosAction.action',
						params : {
							infoLength : '15', //信息标题长度
							offset : '0', //取第N些条信息开始
							length : '4', //取第N些条信息结束
							columnType : '4' //栏目类型	knowFile实体 4:业务支持
						},
						success : function(response, options) {
							var responseArray = Ext.util.JSON
									.decode(response.responseText);
							for ( var i = 0; i < responseArray.length; i++) {
								text4 += "<table width='100%' border='0'  cellspacing='1' cellpadding='0'>";
								text4 += "<tr>";
								text4 += "<td width='8'><img src='images/sidebar_li_cat.gif' width='4' height='5' /></td>";
								text4 += "<td height='30'><a class='nomalLink' href='"+responseArray[i].linkUrl+"' target='_blank'>"
										+ responseArray[i].name
										+ "</a><span class='bluetxt'> "
										+ responseArray[i].isToday + "</span>";
								text4 += " </td>";
								text4 += " </tr>";
								text4 += "<tr>";
								text4 += "<td height='1' colspan='2' background='images/dotted_grey.gif'><img src='images/1x1_pix.gif' width='1' height='1' /></td>";
								text4 += "</tr>";
								text4 += "</table>";
							}
							////document.getElementById("serviceProvide").innerHTML =text4;
						},
						failure : function(response, options) {
						}
					}, this);
</script>
</html>
