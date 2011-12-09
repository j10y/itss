<%@page import="net.shopin.alipay.util.SpringContextUtils"%>
<%
/* *
 功能：支付宝服务器异步通知页面
 版本：3.2
 日期：2011-03-17
 说明：
 以下代码只是为了方便商户测试而提供的样例代码，商户可以根据自己网站的需要，按照技术文档编写,并非一定要使用该代码。
 该代码仅供学习和研究支付宝接口使用，只是提供一个参考。

 //***********页面功能说明***********
 创建该页面文件时，请留心该页面文件中无任何HTML代码及空格。
 该页面不能在本机电脑测试，请到服务器上做测试。请确保外部可以访问该页面。
 该页面调试工具请使用写文本函数logResult，该函数在com.alipay.util文件夹的AlipayNotify.java类文件中
 如果没有收到该页面返回的 success 信息，支付宝会在24小时内按一定的时间策略重发通知
 //********************************
 * */
%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page import="java.util.*"%>
<%@ page import="com.alipay.util.*"%>
<%@ page import="com.alipay.services.*"%>
<%@ page import="com.alipay.config.*"%>
<%
	//获取支付宝POST过来反馈信息
	Map<String,String> params = new HashMap<String,String>();
	Map requestParams = request.getParameterMap();
	for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
		String name = (String) iter.next();
		String[] values = (String[]) requestParams.get(name);
		String valueStr = "";
		for (int i = 0; i < values.length; i++) {
			valueStr = (i == values.length - 1) ? valueStr + values[i]
					: valueStr + values[i] + ",";
		}
		//乱码解决，这段代码在出现乱码时使用。如果mysign和sign不相等也可以使用这段代码转化
		//valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
		params.put(name, valueStr);
	}
	
	//获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以下仅供参考)//
	String batch_no = request.getParameter("batch_no");	//原请求退款批次号
	String success_num = request.getParameter("success_num");	//退交易成功的笔数  0<= success_num<= batch_num
	String result_details = request.getParameter("result_details");	//处理结果详情
	System.out.println("------------------------------");
	System.out.println(result_details);
	System.out.println("------------------------------");
	net.shopin.alipay.service.RefundFastpayService refundFastpayService = (net.shopin.alipay.service.RefundFastpayService)SpringContextUtils.getBean("refundFastpayService");
	refundFastpayService.asynResultRefundFastpay(batch_no, Integer.valueOf(success_num).intValue(), result_details);
	
	//----------------result_details格式-------------
	//第一笔交易#第二笔交易#第三笔交易...#第N笔交易
	
	//----------------第N笔交易格式------------------
	//第一种：交易退款数据集$收费退款数据集|分润退款数据集|分润退款数据集...|分润退款数据集
	//第二种：交易退款数据集$收费退款数据集
	//第三种：交易退款数据集|分润退款数据集|分润退款数据集...|分润退款数据集
	//第四种：交易退款数据集
	
	//----------------交易退款信息格式----------------
	//原付款支付宝交易号^退款总金额^处理结果码
	
	//----------------收费退款数据集格式--------------
	//被收费人支付宝账号[交易时支付宝收取服务费的账户]^被收费人支付宝账号对应用户ID[2088开头16位纯数字]^退款金额^处理结果码
	
	//----------------分润退款数据集------------------
	//转出人支付宝账号[原分润帐户]^转出人支付宝账号对应用户ID[2088开头16位纯数字]^转入人支付宝账号[平台中间帐户]^转入人支付宝账号对应用户ID^退款金额^处理结果代码
	
	//获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以上仅供参考)//

	if(AlipayNotify.verify(params)){//验证成功
		//////////////////////////////////////////////////////////////////////////////////////////
		//请在这里加上商户的业务逻辑程序代码

		//——请根据您的业务逻辑来编写程序（以下代码仅作参考）——
		
		//判断是否在商户网站中已经做过了这次通知返回的处理（可参考“集成教程”中“3.4返回数据处理”）
			//如果没有做过处理，那么执行商户的业务程序
			//如果有做过处理，那么不执行商户的业务程序
			
		out.println("success");	//请不要修改或删除

		//——请根据您的业务逻辑来编写程序（以上代码仅作参考）——

		//////////////////////////////////////////////////////////////////////////////////////////
	}else{//验证失败
		out.println("fail");
	}
%>
