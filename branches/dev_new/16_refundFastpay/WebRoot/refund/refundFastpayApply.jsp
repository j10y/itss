<%
	/* *
	 *功能：即时到帐批量退款无密接口调试入口页面
	 *版本：3.2
	 *日期：2011-03-17
	 *说明：
	 *以下代码只是为了方便商户测试而提供的样例代码，商户可以根据自己网站的需要，按照技术文档编写,并非一定要使用该代码。
	 */
%>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
	
<%@ page import="org.jasig.cas.client.authentication.AttributePrincipal" %>
<%
	AttributePrincipal principal = (AttributePrincipal)request.getUserPrincipal(); 
	String currentUser = principal.getName();
%>

<%@page import="net.shopin.alipay.util.PropertiesUtil"%>
<%@ page import="com.alipay.services.*"%>
<%@ page import="com.alipay.util.*"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<html>
	<head>
	
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<title>即时到帐批量退款申请</title>
		
		
		<style type="text/css">
		body {
		    font-family:Arial, Helvetica, sans-serif;
		    font-size:12px;
		    margin:0;
		}
		#main {
		    height:1800px;
		    padding-top:90px;
		    edui-filter-align-center 
		}
		#fullbg {
		    background-color:Gray;
		    left:0px;
		    opacity:0.5;
		    position:absolute;
		    top:0px;
		    z-index:3;
		    filter:alpha(opacity=50); /* IE6 */
		    -moz-opacity:0.5; /* Mozilla */
		    -khtml-opacity:0.5; /* Safari */
		}
		#dialog {
		    background-color:#FFF;
		    border:1px solid #888;
		    display:none;
		    height:10px;
		    left:50%;
		    margin:-100px 0 0 -100px;
		    padding:12px;
		    position:fixed !important; /* 浮动对话框 */
		    position:absolute;
		    top:50%;
		    width:200px;
		    z-index:5;
		}
		#dialog p {
		    margin:0 0 12px;
		}
		#dialog p.close {
		    edui-filter-align-right 
		}
		</style>
		
		<script type="text/javascript" src="${pageContext.request.contextPath }/js/jquery-1.6.1.min.js"></script>
		
		<script type="text/javascript">
		//显示灰色 jQuery 遮罩层
		function showBg() {
		    var bh = $("body").height();
		    var bw = $("body").width();
		    $("#fullbg").css({
		        height:bh,
		        width:bw,
		        display:"block"
		    });
		    $("#dialog").show();
		}
		//关闭灰色 jQuery 遮罩
		function closeBg() {
		    $("#fullbg,#dialog").hide();
		}
		</script>

		<script type="text/javascript">
		// 浮动对话框
		$(document).ready(function() {
		    var domThis = $('#dialog')[0];
		    var wh = $(window).height() / 2;
		    $("body").css({
		        "background-image": "url(about:blank)",
		        "background-attachment": "fixed"
		    });
		    domThis.style.setExpression('top', 'eval((document.documentElement).scrollTop + ' + wh + ') + "px"');
		});
		</script>
				
		
		<script type="text/javascript">
			function checkForm(){
				var refundFastpayExcel = document.getElementById("refundFastpayExcel").value;
				var applyRemark = document.getElementById("applyRemark").value;
				if(refundFastpayExcel){
					if(refundFastpayExcel.indexOf(".xls")!=-1||refundFastpayExcel.indexOf(".xlsx")!=-1){
						if(!applyRemark){
							alert("请填写备注信息！");
							return;
						}
					 	//增加提示conrim功能
					     var confm = confirm("为防止重复提交，请检查并确认是否提交？");
					     if(confm == true){
							document.getElementById("refundFastpayForm").submit();
							showBg();
							document.getElementById("refundFastpayForm").reset();
					     }
					}else{
						alert("请选择正确EXCEL文件上传！");
						return false;
					}
				}else{
					alert("请选择EXCEL文件上传！");
					return false;
				}
			}
			/**
			 *返回批量退款申请列表
			 */
			function returnToApplyList(){
				window.location.href = "http://www.baidu.com";
			}
			function logout(){
				window.location.href = "${pageContext.request.contextPath }/logout.jsp";
			}
		</script>
	</head>
	<body oncontextmenu="return false" >
		<!-- 
	    <a href="#" onclick="logout();">退出</a>
	     -->
		<br>
		<h3 style="color:red" align="center">
			*建议上传前验证上传文件格式是否正确，是否已经进行过退款<br>
			*1支付宝即时到账批量退款，最大支持<%=net.shopin.alipay.util.PropertiesUtil.getProperties("alipay.batchNumLimit", "1000")%>笔<br><br>
			<!-- 
			<a href="${pageContext.request.contextPath }/refund/refundFastpaySearch.jsp" target="_blank">点击查询历史批量退款信息</a>
			 -->
		</h3>
		<br>
		<br>
		<form target="_self" id="refundFastpayForm" action="${pageContext.request.contextPath }/refundFastpay/import?methodCall=batchRefundApply" method="post" enctype ="multipart/form-data">
			<input type="hidden" name="applyUser" value="<%=currentUser %>">
			<div style="text-align: center; font-size: 9pt; font-family: 宋体">
				<table align="center">
					<tr>
						<td>请选择EXCEL文件<font color="red">*</font>：</td>
						<td><INPUT id="refundFastpayExcel" type="file" size="30" name="batch_no" value=""></td>
					</tr>
					<tr>
						<td>备注<font color="red">*</font>：</td>
						<td><textarea id="applyRemark" style="height:50;" name="applyRemark" value=""></textarea></td>
					</tr>
				</table>
			  <br/>
			  <br/>
			  <br/>			  
              <INPUT type="button" onclick="checkForm();" value="提交申请"  name="btnAlipay">
			</div>
		</form>
		<!-- 
		<div id="main"><a href="#" onclick="showBg();">点击这里看 jQuery 遮罩层效果.</a></div>
		 -->
		<!-- jQuery 遮罩层 -->
		<div id="fullbg"></div>
		<!-- end jQuery 遮罩层 -->
		<!-- 对话框 -->
		<div id="dialog">
		  <!--
		  <p class="close"><a href="#" onclick="closeBg();">关闭</a></p>
		  -->
		  <p>正在加载，请稍后...</p>
		</div>
	</body>
</html>