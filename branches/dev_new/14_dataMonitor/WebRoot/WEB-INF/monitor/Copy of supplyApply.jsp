<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<HTML>
	<HEAD>
		<TITLE></TITLE>	
		<style type="text/css">
    	body {width:960px;margin:0 auto;}
		.table-info td { padding:5px; line-height:24px; font-size:14px;background-color:#FFFFFF;}
		.table-info table td  { border-bottom:1px solid #e0e0e0;background-color:#FFFFFF;}
		img{ border:0;}
		.frame{ 
		    width:800px; 
		    margin:0 auto;
			padding:0;
			text-align:left;
		}
		.f_name{font-size:22px; color:red;}
		h2{ font-size:22px; color:#0056AA; margin:0;}
		.gap{ height:8px; background: #999999; margin: 8px 0; font-size:1px;}
		</style>	
		<script type="text/javascript">
		function isEmail(str)
		{
		 if(str.match(/[\w-.]+@{1}[\w-]+\.{1}\w{2,4}(\.{0,1}\w{2}){0,1}/ig)!=str)
		  return false;
		 else
		  return true;
		}
		function isNumber(s)
		{
		 var patrn=/^[-,+]{0,1}[0-9]{0,}[.]{0,1}[0-9]{0,}$/;
		 if (!patrn.exec(s))
		   return false;
		 return true;
		}
		
		function isTel(str){
			var pattern=/^(([0\+]\d{2,3}-)?(0\d{2,3})-)?(\d{7,8})(-(\d{3,}))?$/;
			if(!pattern.test(str)){
				//alert("电话号码格式不正确");
				return false;
			}else{
				return true;
			}
		}
		function isMobi(str){
			var pattern=/^(?:13\d|15\d|18\d)-?\d{5}(\d{3}|\*{3})$/;
			if(!pattern.test(str)){
				//alert("手机号码格式不正确");
				return false;
			}else{
				return true;
			}
		}
		function submitForm(){
		var supplyName=document.getElementById("supplyApply.supplyName").value;
		var brandRegistArea=document.getElementById("supplyApply.brandRegistArea").value;
		var brandBelong=document.getElementById("supplyApply.brandBelong").value;
		var brandName=document.getElementById("supplyApply.brandName").value;
		var brandPrice=document.getElementById("supplyApply.brandPrice").value;
		var brandClass=document.getElementById("supplyApply.brandClass").value;
		var brandStyle=document.getElementById("supplyApply.brandStyle").value;
		var storeNum=document.getElementById("supplyApply.storeNum").value;
		var storeAddress=document.getElementById("supplyApply.storeAddress").value;
		var website=document.getElementById("supplyApply.website").value;
		var cooperateWebSite=document.getElementById("supplyApply.cooperateWebSite").value;
		var companyAddress=document.getElementById("supplyApply.companyAddress").value;
		var companyTel=document.getElementById("supplyApply.companyTel").value;
		var companyEmail=document.getElementById("supplyApply.companyEmail").value;
		var businessUser=document.getElementById("supplyApply.businessUser").value;
		var businessTel=document.getElementById("supplyApply.businessTel").value;
		var businessEmail=document.getElementById("supplyApply.businessEmail").value;
		var eCommerceUser=document.getElementById("supplyApply.eCommerceUser").value;
		var eCommerceTel=document.getElementById("supplyApply.eCommerceTel").value;
		var eCommerceEmail=document.getElementById("supplyApply.eCommerceEmail").value;
		var upload=document.getElementById("upload").value;
		if(supplyName.length==0){
		alert("供应商名称不能为空！");
		return false;
		}
		if(brandRegistArea.length==0){
		alert("品牌注册地不能为空！");
		return false;
		}
		if(brandBelong.length==0){
		alert("品牌持有不能为空！");
		return false;
		}
		if(brandName.length==0){
		alert("经营品牌名称[中/英]不能为空！");
		return false;
		}
		if(brandPrice.length==0){
		alert("品牌价格带不能为空！");
		return false;
		}
		if(brandClass.length==0){
		alert("品牌所属类别不能为空！");
		return false;
		}
		if(brandStyle.length==0){
		alert("品牌风格不能为空！");
		return false;
		}
		if(storeNum.length==0){
			//alert("拥有实体店/专柜数量不能为空！");
			//return false;
		}else if(!isNumber(storeNum)){
			alert("拥有实体店/专柜数量必须为数字！");
			return false;
		}
		/**
		if(storeAddress.length==0){
		alert("实体店区域分布不能为空！");
		return false;
		}
		if(website.length==0){
			alert("品牌官方网站不能为空！");
			return false;
		}
		if(cooperateWebSite.length==0){
			alert("合作网站不能为空！");
			return false;
		}*/
		if(companyAddress.length==0){
			alert("公司地址不能为空！");
			return false;
		}
		if(companyTel.length==0){
			alert("公司电话不能为空！");
			return false;
		}
		//else if(isTel(companyTel)==false&&isMobi(companyTel)==false){
		//	alert("公司电话格式不正确！");
		//	return false;
		//}
		if(companyEmail.length==0){
			alert("E-mail不能为空！");
			return false;
		}
		//else if(!isEmail(companyEmail)){
		//	alert("E-mail格式不正确！");
		//	return false;
		//}
		if(businessUser.length==0){
			alert("业务负责人/职位不能为空！");
			return false;
		}
		if(businessTel.length==0){
			alert("业务负责人手机不能为空！");
			return false;
		}
		//else if(isMobi(businessTel)==false){
		//	alert("业务负责人手机格式不正确！");
		//	return false;
		//}
		if(businessEmail.length==0){
			alert("业务负责人E-mail不能为空！");
			return false;
		}
		//else if(!isEmail(businessEmail)){
		//	alert("业务负责人E-mail格式不正确！");
		//	return false;
		//}
		if(eCommerceUser.length==0){
			//alert("电商负责人/职位不能为空！");
			//return false;
		}
		if(eCommerceTel.length==0){
			//alert("电商负责人手机不能为空！");
			//return false;
		}
		//else if(isMobi(eCommerceTel)==false){
		//	alert("电商负责人手机格式不正确！");
		//	return false;
		//}
		
		if(eCommerceEmail.length==0){
			//alert("电商负责人E-mail不能为空！");
			//return false;
		}
		//else if(!isEmail(eCommerceEmail)){
		//	alert("电商负责人E-mail格式不正确！");
		//	return false;
		//}
		//if(upload.length==0){
		//	alert("品牌宣传/产品图需打包上传，不能为空！");
		//	return false;
		//}
		var xform = document.supplyForm;
			xform.submit();
		}
		</script>
	</HEAD>
<body  >
<form id="supplyForm" name="supplyForm"action="${pageContext.request.contextPath}/datamonitor/supply_submitSupply.action" method="post"  enctype ="multipart/form-data"><div align="center">
<div class="frame">
<img src="${pageContext.request.contextPath}/Images/logo.gif" border="0"/>
  <div class="gap"></div>
</div>
<div style="font-size:20px"><strong>合作资料表 </strong></div>
<input type="hidden"id="supplyApply.id" name="supplyApply.id" >
<table class="table-info"align="center"width="830" border="0" cellpadding="1" cellspacing="1" bgcolor="black">
<tr >
<td rowspan=2  style="height:40px;width:170">
供应商名称<font color="#ff0000">*</font>
</td>
<td rowspan=2 colspan=2  style="height:40px;width:220">
<input id="supplyApply.supplyName" name="supplyApply.supplyName" style="height:25">
</td>
<td  style="height:40px;width:200">
品牌注册地（国家/地区）<font color="#ff0000">*</font>
</td>
<td colspan=2  style="height:40px;width:230">
<input id="supplyApply.brandRegistArea" name="supplyApply.brandRegistArea"  style="height:25">
</td>
</tr>
<tr >
<td  style="height:40px">
品牌持有<font color="#ff0000">*</font>
</td>
<td colspan=2  style="height:40px">
<input id="supplyApply.brandBelong" name="supplyApply.brandBelong" style="height:25">
</td>
</tr>
<tr >
<td  style="height:40px">
经营品牌名称（中/英）<font color="#ff0000">*</font>
</td>
<td colspan=2  style="height:40px">
<input id="supplyApply.brandName" name="supplyApply.brandName" style="height:25">
</td>
<td  style="height:40px">
品牌价格带<font color="#ff0000">*</font>
</td>
<td colspan=2  style="height:40px">
<input id="supplyApply.brandPrice" name="supplyApply.brandPrice" style="height:25">
</td>
</tr>
<tr >
<td  style="height:40px">
品牌所属类别<font color="#ff0000">*</font>
</td>
<td colspan=2  style="height:40px">
<input id="supplyApply.brandClass" name="supplyApply.brandClass" style="height:25">
</td>
<td  style="height:40px">
品牌风格（适合人群）<font color="#ff0000">*</font>
</td>
<td colspan=2  style="height:40px">
<input id="supplyApply.brandStyle" name="supplyApply.brandStyle" style="height:25">
</td>
</tr>
<tr >
<td  style="height:40px">
拥有实体店/专柜数量
</td>
<td colspan=2  style="height:40px">
<input id="supplyApply.storeNum" name="supplyApply.storeNum"style="height:25">
</td>
<td  style="height:40px">
实体店区域分布（省市级）
</td>
<td colspan=2  style="height:40px">
<input id="supplyApply.storeAddress" name="supplyApply.storeAddress" style="height:25">
</td>
</tr>
<tr >
<td  style="height:40px">
品牌官方网站（网址）
</td>
<td colspan=2  style="height:40px">
<input id="supplyApply.website" name="supplyApply.website" style="height:25">
</td>
<td  style="height:40px">
合作网站（5家以内）
</td>
<td colspan=2  style="height:40px">
<input id="supplyApply.cooperateWebSite" name="supplyApply.cooperateWebSite"style="height:25">
</td>
</tr>
<tr>
<td colspan=6  style="height:40px" align=center> 
<strong>联系方式</strong> 
</td>
</tr>
<tr >
<td  style="height:40px;">
公司地址<font color="#ff0000">*</font>
</td>
<td  style="height:40px;width:150px">
<input id="supplyApply.companyAddress" name="supplyApply.companyAddress" style="height:25">
</td>
<td  style="height:40px;width:100px">
公司电话<font color="#ff0000">*</font>
</td>
<td  style="height:40px;width:160px">
<input id="supplyApply.companyTel" name="supplyApply.companyTel" style="height:25">
</td>
<td  style="height:40px;width:90px">
E-mail<font color="#ff0000">*</font>
</td>
<td  style="height:40px;width:150px">
<input id="supplyApply.companyEmail" name="supplyApply.companyEmail" style="height:25">
</td>
</tr>
<tr >
<td  style="height:40px">
业务负责人/职位<font color="#ff0000">*</font>
</td>
<td  style="height:40px">
<input id="supplyApply.businessUser" name="supplyApply.businessUser" style="height:25">
</td>
<td  style="height:40px">
手机<font color="#ff0000">*</font>
</td>
<td  style="height:40px">
<input id="supplyApply.businessTel" name="supplyApply.businessTel" style="height:25">
</td>
<td  style="height:40px;width:80px">
E-mail<font color="#ff0000">*</font>
</td>
<td  style="height:40px;width:150px">
<input id="supplyApply.businessEmail" name="supplyApply.businessEmail" style="height:25">
</td>
</tr>
<tr >
<td  style="height:40px">
电商负责人/职位
</td>
<td  style="height:40px">
<input id="supplyApply.eCommerceUser" name="supplyApply.eCommerceUser" style="height:25">
</td>
<td  style="height:40px">
手机
</td>
<td  style="height:40px">
<input id="supplyApply.eCommerceTel" name="supplyApply.eCommerceTel"style="height:25">
</td>
<td  style="height:40px">
E-mail
</td>
<td  style="height:40px">
<input id="supplyApply.eCommerceEmail" name="supplyApply.eCommerceEmail" style="height:25">
</td>
</tr>
<tr>
<td colspan=6  style="height:40px" align=center>
品牌宣传/产品图（所有产品系列至少5套图，每款产品的原价，上品折扣网销售价格）
</td>
</tr>
<tr>
<td colspan=1  style="height:40px" align=center>
上传附件
</td>
<td colspan=5  style="height:40px" align=center>
<input type="file" id="upload" name="upload">
</td>
</tr>
<tr>
<td colspan=1  style="height:40px" align=center>
入驻须知
</td>
<td colspan=5  style="height:40px" style="height:25">
请务必确保申请入驻及后续经营阶段提供的相关资质的真实性，一旦发现虚假资质，上品将立即终止合作并拉入上品折扣合作黑名单。
</td>
</tr>
<tr>
<td  colspan=6  style="height:40px" align=center>
<input type="button" id="button"value="在线提交" onclick="submitForm()"></input>
</td>
</tr>
</table>
 </form> 
</body>	
</HTML>
