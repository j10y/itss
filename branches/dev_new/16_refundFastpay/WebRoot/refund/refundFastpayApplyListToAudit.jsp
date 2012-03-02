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
	
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
	
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
		<title>即时到帐批量退款申请列表</title>
		<style type="text/css">
			.bgg {
			    border-color: #8AB78A;
			    width: 1024px;
			}
			.t1 {
			    border-style: solid;
			    border-width: 1px;
			    font-size: 12px;
			    text-align: center;
			}
		</style>
		
		
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
		
		#auditDialog {
		    background-color:#FFF;
		    border:1px solid #888;
		    display:none;
		    height:200px;
		    left:50%;
		    margin:-100px 0 0 -100px;
		    padding:12px;
		    position:fixed !important; /* 浮动对话框 */
		    position:absolute;
		    top:50%;
		    width:300px;
		    z-index:5;
		}
		
		#importDialog {
		    background-color:#FFF;
		    border:1px solid #888;
		    display:none;
		    height:200px;
		    left:50%;
		    margin:-100px 0 0 -100px;
		    padding:12px;
		    position:fixed !important; /* 浮动对话框 */
		    position:absolute;
		    top:50%;
		    width:300px;
		    z-index:5;
		}
		#resultDialog {
		    background-color:#FFF;
		    border:1px solid #888;
		    display:none;
		    height:200px;
		    left:50%;
		    margin:-100px 0 0 -100px;
		    padding:12px;
		    position:fixed !important; /* 浮动对话框 */
		    position:absolute;
		    top:50%;
		    width:300px;
		    z-index:5;
		}
		
		
		</style>
		
		
		
		<script type="text/javascript" src="${pageContext.request.contextPath }/js/jquery-1.6.1.min.js">
		</script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/DatePicker/DatePicker/WdatePicker.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/common.js"></script>
		
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
		    //$("#dialog").show();
		    //$("#auditDialog").show();
		    //$("#importDialog").show();
		    //$("#resultDialog").show();
		}
		//关闭灰色 jQuery 遮罩
		function closeBg() {
		    $("#fullbg,#dialog,#auditDialog,#importDialog,#resultDialog").hide();
		}
		</script>
		
		<script type="text/javascript">
		
			
			
			function logout(){
				window.location.href = "${pageContext.request.contextPath }/logout.jsp";
			}
			
			function checkForm(){
				
				//校验结束时间大于开始时间
				var applyStartTime = document.getElementById("applyTime_start").value;
				if(applyStartTime==""||applyStartTime==undefined){
					alert("请正确输入开始日期！");
					return false;
				}				
				var applyEndTime = document.getElementById("applyTime_end").value;
				if(applyEndTime==""||applyEndTime==undefined){
					alert("请正确输入结束日期！");
					return false;
				}
				var startArray = new Array();
				startArray = applyStartTime.split("-");
				var endArray = new Array();
				endArray = applyEndTime.split("-");
				//alert(startArray + "\n" + endArray);
				var startDate=new Date();
				startDate.setFullYear(startArray[0], startArray[1]-1, startArray[2]);
				startDate.setHours(0); //设置 Date 对象中的小时 (0 ~ 23)。 1 3 
				startDate.setMinutes(0);// 设置 Date 对象中的分钟 (0 ~ 59)。 1 3 
				startDate.setSeconds(0); //设置 Date 对象中的秒钟 (0 ~ 59)。 1 3 
				startDate.setMilliseconds(0); 
				var endDate=new Date();
				endDate.setFullYear(endArray[0], endArray[1]-1, endArray[2]);
				endDate.setHours(0); //设置 Date 对象中的小时 (0 ~ 23)。 1 3 
				endDate.setMinutes(0);// 设置 Date 对象中的分钟 (0 ~ 59)。 1 3 
				endDate.setSeconds(0); //设置 Date 对象中的秒钟 (0 ~ 59)。 1 3 
				endDate.setMilliseconds(0); 
				if(endDate.getTime() < startDate.getTime()){
					alert("结束日期应大于开始日期！");
				}
				
				//发送查询			
				
				$.post("${pageContext.request.contextPath }/refundFastpay/import?methodCall=searchRefundFastpayApplyList", 
						{ 
							'startDate' : applyStartTime,
							'endDate' : applyEndTime,
							'applyStatus' : document.getElementById("applyStatusSelect").value,
							'applyUser' : '<%=currentUser%>',
							'currentPage' : document.getElementById("currentPage").value,
							'pageSize' : document.getElementById("pageSize").value,
							'flag' : document.getElementById("flag").value
						},
		   				function(data){
			     			var dataObj = eval("(" + data + ")");
			     			var resultArray= new Array();
			     			resultArray = dataObj.data;
			     			if(dataObj.success){
			     				document.getElementById("flag").value=1;
			     				document.getElementById("rowCount").value = dataObj.rowCount;
			     				document.getElementById("currentPage").value = dataObj.currentPage;
			     				
			     				var currentPage = dataObj.currentPage;
				     			var tatalPage = dataObj.totalPage;
				     			
								var resultStr = "";
								
								
								var resultStr = '<center>' + 
								'<div class="t1 bgg">' + 
				     			'<table width="100%" cellspacing="0" cellpadding="0" border="0">' + 
				     			'<tbody>' + 
				     			
				     			'<tr bgcolor="#F15930">' + 
				     			'<td height="20" align="center" class="f9pt"><font style="font-family: Verdana, 宋体;">查询结果</font></td>' + 
				     			'</tr>' + 
				     			
				     			'<tr bgcolor="9bd1d2">' + 
				     			'<td>' +
				     			
				     			'<table width="100%" cellspacing="1" cellpadding="7" border="0">' + 
				     			'<tbody>' + 
				     			'<tr align="center" bgcolor="#EFF7F0" >' + 
				     			'<td width="">序号</td>' +
				     			'<td width="">申请编号</td>' + 
				     			'<td width="">退款批次号</td>' + 
				     			'<td width="">申请状态</td>' + 
				     			'<td width="">申请人</td>' + 
				     			'<td width="">申请时间</td>' + 
				     			'<td width="">申请备注</td>' + 
				     			'<td width="">审核人</td>' + 
				     			'<td width="">审核时间</td>' + 
				     			'<td width="">审核备注</td>' + 
				     			'<td width="">导入人</td>' + 
				     			'<td width="">导入时间</td>' + 
				     			'<td width="">导入备注</td>' + 
				     			'<td width="">操作</td>' + 
				     			'</tr>';
				     			
				     			for(var i=0; i<resultArray.length; i++){
				     				resultStr += '<tr align="center" bgcolor="#EFF7F0" >' + 
					     			'<td width="">' + (i+1) + '</td>' + 
					     			'<td width="">' + resultArray[i].applyNo + '</td>' + 
					     			'<td width="">' + resultArray[i].batchNo + '</td>' + 
					     			
					     			'<td width="">' + 
					     					(resultArray[i].applyStatus==1 ? "待审核" : (resultArray[i].applyStatus== 2 ? "审批通过，未导入" : (resultArray[i].applyStatus==3 ? "拒绝" : (resultArray[i].applyStatus==4 ? "导入成功" : (resultArray[i].applyStatus==5 ? "导入失败" : "草稿"))))) + 
					     			'</td>' + 
					     			
					     			'<td width="">' + resultArray[i].applyUser + '</td>' + 
					     			'<td width="">' + resultArray[i].applyTime + '</td>' + 
					     			'<td width="">' + resultArray[i].applyRemark + '</td>' + 
					     			'<td width="">' + resultArray[i].auditUser + '</td>' + 
					     			'<td width="">' + resultArray[i].auditTime + '</td>' + 
					     			'<td width="">' + resultArray[i].auditRemark + '</td>' + 
					     			'<td width="">' + resultArray[i].importUser + '</td>' +
					     			'<td width="">' + resultArray[i].importTime + '</td>' +
					     			'<td width="">' + resultArray[i].importRemark + '</td>' +
					     			'<td width=""><nobr><a href="#" onclick="downloadFile(' + resultArray[i].id + ')">下载</a>|' + 
					     			(resultArray[i].applyStatus==1 ? '<a href="#" onclick="openAuditApplyWin(' + resultArray[i].id + ')">审核</a>' : (resultArray[i].applyStatus== 2 ? '<a href="#" onclick="openImportApplyWin(' + resultArray[i].id + ')">导入</a>' : '----')) +				     			
					     			'</nobr></td>' +
					     			'</tr>';
				     			}
				     			
				     			resultStr += '</tbody>' + 
				     			'</table>' + 
				     			
				     			'</td>' + 
				     			'</tr>' + 
				     			'<tr bgcolor="#F15930">' + 
				     			'<td height="20" align="center" class="f9pt"><font style="font-family: Verdana, 宋体;">'+
				     			'第'+ currentPage +'页，共' + tatalPage + '页&nbsp;&nbsp;' +
				     			(currentPage > 1 ? '<a href="#" onclick="prePage();">上一页</a>' : "|") +
				     			(currentPage < tatalPage ? '<a href="#" onclick="nextPage();">下一页</a>' : "|") +
				     			'</font></td>' + 
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
				
			}
			function nextPage(){
				document.getElementById("currentPage").value= parseInt(document.getElementById("currentPage").value) + 1;
				checkForm();
			}
			
			function prePage(){
				document.getElementById("currentPage").value= parseInt(document.getElementById("currentPage").value) - 1;
				checkForm();
			}
			
			function onSelectChange(){
				document.getElementById("flag").value=0;//设置为新进入页面
			}
			
			function openAuditApplyWin(applyId){
				showBg();
				$("#auditDialog").show();
				document.getElementById("applyId").value = applyId;
			}
			
			/**
			*
			*/
			function auditApply(){
				//Ajax请求，请求结束，去掉遮罩层
				//校验
				var auditRemark = document.getElementById("auditRemark").value;
				var applyId = document.getElementById("applyId").value;
				if(auditRemark=='' || auditRemark==undefined){
					alert("请填写备注信息!");
					return false;
				}
				$("#auditDialog").hide();
				$("#dialog").show();
				$.post("${pageContext.request.contextPath }/refundFastpay/import?methodCall=auditRefundFastpayApply", 
					{ 
						'applyId' : applyId,
						'auditUser' : '<%=currentUser%>',
						'auditRemark' : unicode(document.getElementById("auditRemark").value),
						'applyStatus' : document.getElementById("applyStatus1").value
					},
	   				function(data){
						var dataObj = eval("(" + data + ")");
		     			if(dataObj.success){
		     				alert("审批成功！");
		     			}else{
		     				alert("审批失败！\n错误信息:" + dataObj.msg);
		     			}
		     			document.forms['auditDialogForm'].reset();
		     			checkForm();
		     			closeBg();
					});
			}
			
			function openImportApplyWin(applyId){
				showBg();
				$("#importDialog").show();
				document.getElementById("applyId_import").value = applyId;
			}
			
			function importApply(){
				//Ajax请求，请求结束，去掉遮罩层
				//校验
				var importRemark = document.getElementById("importRemark").value;
				var applyId = document.getElementById("applyId_import").value;
				if(importRemark=='' || importRemark==undefined){
					alert("请填写备注信息!");
					return false;
				}
				$("#importDialog").hide();
				$("#dialog").show();
				$.post("${pageContext.request.contextPath }/refundFastpay/import?methodCall=importRefundFastpayApply", 
					{ 
						'applyId' : applyId,
						'importUser' : '<%=currentUser%>',
						'importRemark' : unicode(document.getElementById("importRemark").value)
					},
	   				function(data){
						var dataObj = eval("(" + data + ")");
		     			if(dataObj.success){
		     				document.forms['importDialogForm'].reset();
			     			checkForm();
			     			closeBg();
		     				alert("导入成功！");
		     			}else{
		     				document.forms['importDialogForm'].reset();
			     			checkForm();
			     			closeBg();
		     				showBg();
		     				document.getElementById("resultDialogHtml").innerHTML=  dataObj.msg;
		     				$("#resultDialog").show();
		     			}
		     			
					});
			}
			
			function downloadFile(applyId){
				window.location.href = "${pageContext.request.contextPath }/refundFastpay/import?methodCall=downloadFile&applyId=" + applyId;
				return;
				$.post("${pageContext.request.contextPath }/refundFastpay/import?methodCall=downloadFile", 
						{ 
							'applyId' : applyId
						},
		   				function(data){
							
			     			
						});
			}
			
		</script>
	</head>
	<body>
		<!--
		<a href="#" onclick="logout();">退出</a>
		-->
		<br>
		<h3 style="color:red" align="center">
			*所有待处理的批量退款申请
		</h3>
		<br>
		<br>
		<form id="refundFastpayQueryForm" action="#" method="post" target="_blank">
			
			<!-- flag 0第一次 1 非第一次 -->
			<c:if test="${not empty flag }">
			<input id="flag" type="hidden" name="flag" value="${flag }">
			</c:if>
			<c:if test="${empty flag }">
			<input id="flag" type="hidden" name="flag" value="0">
			</c:if>
			<input id="rowCount" type="hidden" name="rowCount" value="">
			<input id="currentPage" type="hidden" name="currentPage" value="1">
			<input id="pageSize" type="hidden" name="pageSize" value="10">
			<div style="text-align: center; font-size: 9pt; font-family: 宋体">
			  	<table align="center">
			  		<tr>
			  			<td>开始日期：</td>
			  			<td>
			  				<input 
			  				onchange="onSelectChange();"
			  				style="height: 20px; width: 160px; font-size: 12px;" 
			  				class="Wdate" type="text" 
			  				runat="server" 
			  				id="applyTime_start" 
			  				name="applyTime_start" 
			  				size="18" 
			  				onclick="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd'})" 
			  				ispost="true" />
			  			</td>
			  			<td>结束日期：</td>
			  			<td>
			  				<input 
			  				onchange="onSelectChange();"
			  				style="height: 20px; width: 160px; font-size: 12px;" 
			  				class="Wdate" type="text" 
			  				runat="server" 
			  				id="applyTime_end" 
			  				name="applyTime_end" 
			  				size="18" 
			  				onclick="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd'})" 
			  				ispost="true" />
			  			</td>
			  			<td>状态：</td>
			  			<td>
			  				<select id="applyStatusSelect" name="applyStatus" size="1" onchange="onSelectChange();">
								<option value="0">草稿（无效状态）
								<option value="-1" selected>全部
								<option value="1">待审批
								<option value="2">审批通过，未进行导入
								<option value="3">拒绝
								<option value="4">审批通过，导入成功
								<option value="5">审批通过，导入失败
							</select>
			  			</td>
			  		</tr>
			  	</table>
			  	<br>
              	<INPUT type="button" onclick="checkForm();" value="查询"  name="btnAlipayApplySearch">
              	<INPUT type="reset"" value="重置" onclick="onSelectChange();" name="btnAlipayApplySearchReset">
			</div>
			
			<br>
			<div id="resultDiv" style="text-align: center; font-size: 9pt; font-family: 宋体">
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
			<p>正在加载，请稍后...</p>
		</div>
		<div id="auditDialog">
			<form id="auditDialogForm" >
				<table align="center">
					<tr>
						<td>备注<font color="red">*</font>：</td><td><textarea id="auditRemark" name="auditRemark" width="100%"></textarea></td>
					</tr>
					<tr>
						<td></td>
						<td>
							<input type="radio" name="status" checked="checked" onclick="javascript:document.getElementById('applyStatus1').value=2">同意
							<input type="radio" name="status" onclick="javascript:document.getElementById('applyStatus1').value=3">拒绝
						</td>
					</tr>
					<tr>
						<td></td><td><input id="applyId" type="hidden"></td>
					</tr>
					<tr>
						<td></td><td><input id="applyStatus1" name="applyStatus" type="hidden" value="2"></td>
					</tr>
					<tr align="center">
						<td colspan="2">
							<INPUT type="button" onclick="auditApply();" value="确定">
	              			<INPUT type="reset" onclick="closeBg();" value="取消">
             			</td>
					</tr>
				</table>
			</form>
		</div>
		
		<div id="importDialog">
			<form id="importDialogForm" >
				<table align="center">
					<tr>
						<td>备注<font color="red">*</font>：</td><td><textarea id="importRemark" name="importRemark" width="100%"></textarea></td>
					</tr>
					<tr>
						<td></td><td><input id="applyId_import" type="hidden"></td>
					</tr>
					<tr align="center">
						<td colspan="2">
							<INPUT type="button" onclick="importApply();" value="确定">
	              			<INPUT type="reset" onclick="closeBg();" value="取消">
             			</td>
					</tr>
				</table>
			</form>
		</div>
		<div id="resultDialog" style="OVERFLOW:auto;">
			<p><a href="#" onclick="closeBg();">关闭</a></p>
			<div id="resultDialogHtml"></div>
		</div>
	</body>
</html>