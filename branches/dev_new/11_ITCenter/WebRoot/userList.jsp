<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@include file="/includefiles.jsp"%>
<%@include file="/header.jsp" %>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>员工查询结果</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<script type="text/javascript"
			src="${pageContext.request.contextPath}/js/jquery-1.6.1.min.js">
		</script>
		<style type="text/css">
		@import url("DCIT.css");
			.pop-box {
				z-index: 9999; /*这个数值要足够大，才能够显示在最上层*/
				margin-bottom: 3px;
				display: none;
				position: absolute;
				background: #FFF;
				border: solid 1px #f36b47;
			}
			
			.pop-box { //
				width:100%;
				color: #FFF;
				cursor: default; 
				height: 18px;
				font-size: 14px;
				font-weight: bold;
				text-align: left;
				padding-left: 8px;
				padding-bottom: 2px; 
				background:	url("${pageContext.request.contextPath}/images/header_bg.png")
					repeat-x 0 0 ;
			}
			
			.pop-box-body {
				clear: both;
				margin: 12px;
				padding: 2px;
				background: #f36b47;
			}
			</style>
		<script type="text/javascript" >
			var webContext="${pageContext.request.contextPath}";
		
			function getDetail(ahref){
				var uid = ahref.id;//uid
				popupDiv("pop-div",uid);
			}
			
			function popupDiv(div_id, uid) {
				$.post("${pageContext.request.contextPath}/userAction_getUserDetailByUid.action", { uid : uid },
	   				function(data){
	     			var dataObj = eval("(" + data + ")");
	     			var htmlContent = "<TABLE width='100%' cellpadding='0' cellspacing='1' style='background:#fbd0c5;line-height:25px;'>"
	     				+ "<TR><TD colspan='2' rowspan='4' width='30%' align='center'><IMG onerror='this.src=\""+ webContext +"/images/default.jpg\"' src='"+ webContext +"/images/userphoto/" + uid + ".jpg'></IMG></TD><TD align='right' style='font-family: 宋体; font-size: 14px; color: black;'>账号：</TD><TD style='font-family: 宋体; font-size: 13px; color: #666666;'>" + dataObj.uid + "</TD></TR>"
	     				+ "<TR><TD align='right' style='font-family: 宋体; font-size: 14px; color: black;'>姓名：</TD><TD style='font-family: 宋体; font-size: 13px; color: #666666;'>" + dataObj.cn + "</TD></TR>"
	     				+ "<TR><TD align='right' style='font-family: 宋体; font-size: 14px; color: black;'>部门：</TD><TD style='font-family: 宋体; font-size: 13px; color: #666666;'>" + dataObj.deptName+ "</TD></TR>"
	     				+ "<TR><TD align='right' style='font-family: 宋体; font-size: 14px; color: black;' >职位：</TD><TD style='font-family: 宋体; font-size: 13px; color: #666666;'>" + dataObj.title+ "</TD></TR>"
	     				+ "<TR><TD align='right' style='font-family: 宋体; font-size: 14px; color: black;'>电子邮箱：</TD><TD colspan='3' style='font-family: 宋体; font-size: 13px; color: #666666;'>" + dataObj.mail + "</TD></TR>"
	     				+ "<TR><TD align='right' style='font-family: 宋体; font-size: 14px; color: black;'>座机：</TD><TD colspan='3' style='font-family: 宋体; font-size: 13px; color: #666666;'>" + dataObj.mobile + "</TD></TR>"
	     				+ "<TR><TD align='right' style='font-family: 宋体; font-size: 14px; color: black;'>手机：</TD><TD colspan='3' style='font-family: 宋体; font-size: 13px; color: #666666;'>" + dataObj.telephoneNumber + "</TD></TR>"
	     				+ "</TABLE>";
	     			//document.getElementById("pop-div-content").innerHTML = htmlContent;
					$("#pop-div-title").html("<font color=\"white\">" + dataObj.cn + "的基本信息"+"</font>");
	     			$("#pop-div-content").html(htmlContent);
	   			});						
			    var div_obj = $("#"+div_id);   
			    var windowWidth = document.documentElement.clientWidth;       
			    var windowHeight = document.documentElement.clientHeight;       
			    var popupHeight = div_obj.height();       
			    var popupWidth = div_obj.width(); 
			    //添加并显示遮罩层   
			    $("<div id='mask'></div>").addClass("mask")   
			                              .width(windowWidth * 0.99)   
			                              .height(windowHeight * 0.99)   
			                              .click(function() {hideDiv(div_id); })   
			                              .appendTo("body")   
			                              .fadeIn(200);   
			    div_obj.css({"position": "absolute"})   
			           .animate({left: windowWidth/2-popupWidth/2,    
			                     top: windowHeight/2-popupHeight/2, opacity: "show" }, "slow");   
			}   
			  
			function hideDiv(div_id) {   
			    $("#mask").remove();   
			    $("#" + div_id).animate({left: 0, top: 0, opacity: "hide" }, "slow");   
			}
			
		</script>
	</head>

	<body>
		
		<table width="940" align="center" cellpadding="0" cellspacing="0"
			border="0">
			<tr>
				<div id='pop-div' style="width: 400px; height: 270px"
					class="pop-box">
					<div id="pop-div-title" class=".pop-box">
						标题位置
					</div>
					<div id="pop-div-content" class="pop-box-body">
						<p>
							正文内容
						</p>
					</div>
					<div class='buttonPanel' style="text-align: right"
						mce_style="text-align: right">
						<input value="关闭" id="btn1" onclick="hideDiv('pop-div');"
							type="button" />
					</div>
				</div>
			</tr>
			<tr>
				<td colspan="3">
					<br>
					<font style="font-family: 宋体; font-size: 13px; color: #666666;" align="left"><a href="${pageContext.request.contextPath}/index.jsp">首页</a>->员工查询结果</font>
				</td>
			</tr>
			<tr>
				<td>
					&nbsp;
				</td>
				<td>					
					<p><font style="font-family: 宋体; font-size: 12px; color: #f25930;">提示：查看详情请点击账号，发送邮件请点击EMAIL</font></p>
				</td>
				<td>
					&nbsp;
				</td>
			</tr>
			<tr >
				<td  height="350">
					&nbsp;
				</td>
				<td  width="800" style="vertical-align: top" align="center" >
					<table width="100%" border="0" cellpadding="0" cellspacing="1"
						style="line-height: 25px;font-family: 宋体; font-size: 14px; color: #666666;">
						<!-- 
						<c:forEach var="knowFile" items="${pagination.data}">

							<tr>
								<td width="2%">
									<img src="images/pot_12.gif"></img>
								</td>
								<td width="2%"></td>
								<td align="left">
									<a
										href="${pageContext.request.contextPath}/knowFileAction_getContentInfoAction.action?id=${knowFile.id}"
										target="_blank" class="listLink">${knowFile.name}</a>
								</td>
								<td width="10%" class="bannertxt">
									<fmt:formatDate value="${knowFile.createDate}"
										pattern="yyyy-MM-dd" />
								</td>
							</tr>
						</c:forEach>
						 -->
						
						<tr bgcolor="#f6623a" style="font-family: 宋体; font-size: 14px; color: white;">
							<th>
								账号
							</th>
							<th>
								姓名
							</th>
							<th>
								职位
							</th>
							<th>
								EMAIL
							</th>
							<th>
								座机
							</th>
							<th>
								手机
							</th>
						</tr>
						<c:if test="${not empty data }">
							<c:forEach var="user" items="${data}">

								<tr bgcolor="#fee6e6" style="font-family: 宋体; font-size: 13px; color: #666666;" align="left">
									<td>
										<a id="${user.uid }" href="#" onclick="getDetail(this)">${user.uid
											}</a>
									</td>
									<td>
										${user.cn }&nbsp;
									</td>
									<td>
										${user.title }&nbsp;
									</td>
									<td>
										<a href="mailto:${user.mail }">${user.mail }</a>&nbsp;
									</td>
									<td>
										${user.telephoneNumber }&nbsp;
									</td>
									<td>
										${user.mobile }&nbsp;
									</td>
								</tr>
							</c:forEach>
						</c:if>
						<c:if test="${empty data }">
							<tr style="font-family: 宋体; font-size: 13px; color: #666666;" align="left">
								<td colspan="4" align="center">查询结果为空</td>
							</tr>
						</c:if>
					</table>
				</td>
				<td>
					&nbsp;
				</td>
			</tr>
			<tr>
				<td colspan="3" align="center">
					<span style="font-family: 宋体; font-size: 14px; color: #666666;">当前页数：${currentPage}
						总页数：${totalPage}</span>

					<c:if test="${currentPage!=1}">
						<c:url var="first"
							value="${pageContext.request.contextPath}/userAction_getUserListByParam.action?firstFlag=0">
							<c:param name="currentPage" value="1" />
							<c:param name="firstFlag" value="0" />
						</c:url>
						<c:url var="previous"
							value="${pageContext.request.contextPath}/userAction_getUserListByParam.action?firstFlag=0">
							<c:param name="currentPage" value="${currentPage-1}" />
							<c:param name="firstFlag" value="0" />
						</c:url>
						<a href="${first}" class='nomalLink'>第一页</a>
						<a href="${previous}" class='nomalLink'>上一页</a>
					</c:if>

					<c:if test="${currentPage<totalPage}">
						<c:url var="next"
							value="${pageContext.request.contextPath}/userAction_getUserListByParam.action?firstFlag=0">
							<c:param name="currentPage" value="${currentPage+1}" />
							<c:param name="firstFlag" value="0" />
						</c:url>
						<c:url var="last"
							value="${pageContext.request.contextPath}/userAction_getUserListByParam.action?firstFlag=0">
							<c:param name="currentPage" value="${totalPage}" />
							<c:param name="firstFlag" value="0" />
						</c:url>
						<a href="${next}" class='nomalLink'>下一页</a>
						<a href="${last}" class='nomalLink'>最后一页</a>
					</c:if>
				</td>
			</tr>
			<tr>
				<td colspan="3">
					&nbsp;
				</td>
			</tr>

		</table>
		<%@include file="/footer.jsp"%>
	</body>
</html>
