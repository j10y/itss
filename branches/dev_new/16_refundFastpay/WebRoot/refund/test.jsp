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
<%@ page import="com.alipay.services.*"%>
<%@ page import="com.alipay.util.*"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<title>即时到帐批量退款无密接口</title>
	</head>
	<BODY>
		<FORM name=alisubmit action="${pageContext.request.contextPath }/notify_url.jsp" method=post target="_blank">
			<div style="text-align: center; font-size: 9pt; font-family: 宋体">
			  退款批次号：<INPUT type="text" size="30" name="batch_no" value="20111201002"><br />
			成功笔数：<INPUT type="text" size="30" name="success_num" value="10"><br />
			返回数据集：<INPUT type="text" size="30" name="result_details" value="2011072008603963^0^上品折扣支付宝退款"><br />
                <INPUT type="submit" value="测试"  name="btnAlipay">
			</div>
		</FORM>
	</BODY>
</html>