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
		<title>即时到帐批量退款</title>
		<script type="text/javascript">
			function checkForm(){
				var refundFastpayExcel = document.getElementById("refundFastpayExcel").value;
				if(refundFastpayExcel){
					if(refundFastpayExcel.indexOf(".xls")!=-1||refundFastpayExcel.indexOf(".xlsx")!=-1){
					     //增加提示conrim功能
					     var confm = confirm("为防止重复提交，请检查并确认是否提交？");
					     if(confm == true){
							document.getElementById("refundFastpayForm").submit();
							alert("请稍等退款结果，谨防重复提交！！！");
					     }
					}else{
						alert("请选择EXCEL文件上传！");
						return false;
					}
				}else{
					alert("请正确选择EXCEL文件上传！");
					return false;
				}
			}
			function logout(){
				window.location.href = "${pageContext.request.contextPath }/logout.jsp";
			}
		</script>
	</head>
	<body>
		<!-- 
	    <a href="#" onclick="logout();">退出</a>
	     -->
		<br>
		<h3 style="color:red" align="center">
			*建议上传前验证上传文件格式是否正确，是否已经进行过退款<br>
			*支付宝即时到账批量退款，最大支持<%=net.shopin.alipay.util.PropertiesUtil.getProperties("alipay.batchNumLimit", "1000")%>笔<br><br>
			<!-- 
			<a href="${pageContext.request.contextPath }/refund/refundFastpaySearch.jsp" target="_blank">点击查询历史批量退款信息</a>
			 -->
		</h3>
		<br>
		<br>
		<form id="refundFastpayForm" action="${pageContext.request.contextPath }/refundFastpay/import?methodCall=batchRefundImport" method="post" enctype ="multipart/form-data" target="_blank">
			<div style="text-align: center; font-size: 9pt; font-family: 宋体">
			  请选择EXCEL文件：<INPUT id="refundFastpayExcel" type="file" size="30" name="batch_no" value=""><br />
			  <br/>
			  <br/>
			  <br/>
			  <br/>			  
              <INPUT type="button" onclick="checkForm();" value="提交"  name="btnAlipay">
			</div>
		</form>
	</body>
</html>