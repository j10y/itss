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
		<title>即时到帐批量退款查询</title>
		<style type="text/css">
			.bgg {
			    border-color: #8AB78A;
			    width: 900px;
			}
			.t1 {
			    border-style: solid;
			    border-width: 1px;
			    font-size: 12px;
			    text-align: center;
			}
		</style>
		<script type="text/javascript" src="${pageContext.request.contextPath }/js/jquery-1.6.1.min.js">
		</script>		
		<script type="text/javascript">
			function logout(){
				window.location.href = "${pageContext.request.contextPath }/logout.jsp";
			}
			function reset(){
				document.getElementById("batchNoInput").innerText = "";
			}
			function checkForm(){
				var batchNoInputText = document.getElementById("batchNoInput").value;
				if(batchNoInputText){
					$.post("${pageContext.request.contextPath }/refundFastpay/import?methodCall=getResultDetailByBatchNo", { 'batchNo' : batchNoInputText },
		   				function(data){
		     			var dataObj = eval("(" + data + ")");		     			
		     			if(dataObj.success){
		     				//处理返回数据逻辑    				
			     			var batchNo = dataObj.data.batchNo;//退款批次号
			     			var batchNum = dataObj.data.batchNum;//退款笔数
			     			var batchData = dataObj.data.batchData;//退款数据集
			     			var refundDate = dataObj.data.refundDate;//退款时间
			     			var isSuccess = dataObj.data.isSuccess;//成功T 失败F P处理中
			     			var successNum = dataObj.data.successNum;//成功退款笔数
			     			var resultDetails = dataObj.data.resultDetails;//退款结果详情
			     			var relation = dataObj.data.relation;//商家订单号、支付宝交易号关系
			     			var totalRefund = dataObj.data.totalRefund;//退款总金额
			     			
			     			//--整理数据格式Map-开始
			     			var batchDataArray= new Array(); //定义一数组
			     			batchDataArray = batchData.split('#');
			     			
			     			var resultDetailsArray = new Array(); //定义一数组
			     			var resultDetailMap = {};
			     			if(resultDetails && resultDetails != ""){
			     				resultDetailsArray = resultDetails.split('#');
			     				for(var i=0; i<resultDetailsArray.length; i++){
			     					var recordData = resultDetailsArray[i].split("^");
			     					resultDetailMap[recordData[0]] = recordData;
			     				}			     				
			     			}
			     			
			     			var relationArray = new Array(); //定义一数组
			     			var relationMap = {};//{key=支付宝交易号 ,value=[商家订单号,支付宝交易号]}
			     			if(relation && relation != ""){
			     				relationArray = relation.split('#');
			     				for(var i=0; i<relationArray.length; i++){
			     					var recordData = relationArray[i].split("^");
			     					relationMap[recordData[1]] = recordData;
			     				}			     				
			     			}
			     						     			
			     			//--整理数据格式Map-结束
			     			
			     			
			     			var resultStr = '<center><div class="t1 bgg">' + 
			     			'<table width="100%" cellspacing="0" cellpadding="0" border="0">' + 
			     			'<tbody>' + 
			     			'<tr bgcolor="#F15930">' + 
			     			'<td height="20" align="center" class="f9pt"><font >查询结果</font></td>' + 
			     			'</tr>' + 
			     			'<tr bgcolor="9bd1d2">' + 
			     			'<td>' + 
			     			'<table width="100%" cellspacing="1" cellpadding="7" border="0">' + 
			     			'<tbody>' + 
			     			'<tr bgcolor="#EFF7F0">' + 
			     			'<td width="10%">批次号：</td>' + 
			     			'<td width="20%">' + batchNo + '</td>' + 
			     			'<td width="15%">退款笔数：</td>' + 
			     			'<td width="15%">共' + batchNum + '笔</td>' + 
			     			'<td width="15%">退款时间：</td>' + 
			     			'<td width="25%">' + refundDate + '</td>' + 
			     			'</tr>' + 
			     			'<tr bgcolor="#EFF7F0">' + 
			     			'<td>即时返回结果：</td>' + 
			     			'<td>' + (isSuccess=="T" ? "成功" : (isSuccess=="F" ? "失败" : "处理中或银行卡充退中")) + '</td>' + 
			     			'<td colspan="1">退款总金额<br>（单位：元）：</td>' + 
			     			'<td colspan="1">' + (totalRefund!='' ? totalRefund : "---") + '</td>' + 
			     			'<td colspan="1">成功笔数（异步返回）：</td>' + 
			     			'<td colspan="1">' + (successNum!='' ? ('共成功' + successNum + '笔') : "") + '</td>' + 
			     			'</tr>' + 
			     			'</tbody>' + 
			     			'</table>' + 
			     			'</td>' + 
			     			'</tr>' + 
			     			'</tbody>' + 
			     			'<tbody>' + 
			     			'<tr bgcolor="#F15930">' + 
			     			'<td height="20" align="center" class="f9pt"><font style="font-family: Verdana, 宋体;">详细退款信息</font></td>' + 
			     			'</tr>' + 
			     			'<tr bgcolor="9bd1d2">' + 
			     			'<td>' + 
			     			'<table width="100%" cellspacing="1" cellpadding="7" border="0">' + 
			     			'<tbody>' + 
			     			'<tr align="center" bgcolor="#EFF7F0" >' + 
			     			'<td width="5%">序号</td>' +
			     			'<td width="15%">单品订单号</td>' + 
			     			'<td width="20%">支付宝交易号</td>' + 
			     			'<td width="20%">退款金额<br>（单位：元）</td>' + 
			     			'<td width="20%">退款备注</td>' + 
			     			'<td width="20%">异步结果</td>' + 
			     			'</tr>';
			     			
			     			for(var i=0; i<batchDataArray.length; i++){
			     				var recordData = new Array();
			     				recordData = batchDataArray[i].split("^");
			     				
			     				//商家订单号
			     				var outTradeNo = "";
			     				if(relationMap[recordData[0]]){
			     					outTradeNo = relationMap[recordData[0]][0];
			     				}else{
			     					outTradeNo = "-----------";
			     				}
			     				
			     				//异步结果
			     				var result = "";
			     				if(resultDetailMap[recordData[0]]){
			     					result = resultDetailMap[recordData[0]][2];
			     				}else{
			     					result = "-----------";
			     				}
			     				
			     				resultStr += '<tr align="center" bgcolor="#EFF7F0" >' + 
				     			'<td width="5%">' + (i+1) + '</td>' + 
				     			'<td width="15%">' + outTradeNo + '</td>' + 
				     			'<td width="20%">' + recordData[0]+ '</td>' + 
				     			'<td width="20%" align="right">' + recordData[1]+ '</td>' + 
				     			'<td width="20%">' + recordData[2]+ '</td>' + 
				     			'<td width="20%">' + result + '</td>' + 
				     			'</tr>';
			     			}
			     			
			     			
			     			resultStr += '</tbody>' + 
			     			'</table>' + 
			     			'</td>' + 
			     			'</tr>' + 
			     			'</tbody>' +
			     			'</table>' + 
			     			'</div>' + 
			     			'</center>';
			     			
			     			document.getElementById("resultDiv").innerHTML = resultStr;
		     			}else{
		     				alert(dataObj.msg);
		     			}
		   			});
				}else{
					document.getElementById("resultDiv").innerHTML = "";
					alert("请输入原退款批次号！");
					return false;
				}
			}
		</script>
	</head>
	<body>
		<!--
		<a href="#" onclick="logout();">退出</a>
		-->
		<br>
		<h3 style="color:red" align="center">
			*注意：在导入批量退款文件后，立即查询可能不会即时展现所有退款详情，支付宝接口存在延迟<br>
		</h3>
		<br>
		<br>
		<form id="refundFastpayQueryForm" action="#" method="post" target="_blank">
			<div style="text-align: center; font-size: 9pt; font-family: 宋体">
			  请输入原退款批次号：<INPUT  id="batchNoInput" type="text" size="30" name="batchNo" value="">		  
              <INPUT type="button" onclick="checkForm();" value="查询"  name="btnAlipay"><INPUT type="button" onclick="reset();" value="重置"  name="btnAlipaySearchReset">
			</div>
		</form>
		<div id="resultDiv" style="text-align: center; font-size: 9pt; font-family: 宋体">
		</div>
	</body>
</html>