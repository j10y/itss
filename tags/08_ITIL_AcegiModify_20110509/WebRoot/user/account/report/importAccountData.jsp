<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<%@ page language="java" import="java.util.*" pageEncoding="gbk"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<HTML>
	<HEAD>
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/extEngine/resources/css/ext-all.css" />
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/common.css" />
	<script type="text/javascript">
	function setImportType(){
		var ob=document.getElementsByName('ScImportType');
		var myLay=document.getElementById("isNewOrUp");
		var ob1=document.getElementsByName('isnew');
		var ScImportType='';
		var isNewType=1;
		for(var i = 0;i<ob.length;i++){
	              if(ob[i].checked){
	             	ScImportType=ob[i].value;
	        }
	    }
	    for(var i = 0;i<ob1.length;i++){
	              if(ob1[i].checked){
	             	isNewType=ob1[i].value;
	        }
	    }
	    var url=document.getElementById("downloadmodle");
	    
	    if(ScImportType==1){
		    	url.href="${pageContext.request.contextPath}/user/account/report/modle/domainaccount.xls";
	    }else if(ScImportType==2){
		    if(isNewType==1){
		    	url.href="${pageContext.request.contextPath}/user/account/report/modle/updateemailaccount.xls";
		    }else{
		    	url.href="${pageContext.request.contextPath}/user/account/report/modle/addemailaccount.xls";
		    }
	     	
	    }
	    else if(ScImportType==3){
	     	url.href="${pageContext.request.contextPath}/user/account/report/modle/wwwaccount.xls";
	    }
	    else if(ScImportType==4){
	     	url.href="${pageContext.request.contextPath}/user/account/report/modle/msnaccount.xls";
	    }
	    else if(ScImportType==5){
	     if(isNewType==1){
		    	url.href="${pageContext.request.contextPath}/user/account/report/modle/updateVPNaccount.xls";
		    }else{
		     	url.href="${pageContext.request.contextPath}/user/account/report/modle/remoteaccount.xls";
		    }
	    }
	    else if(ScImportType==7){
	     	url.href="${pageContext.request.contextPath}/user/account/report/modle/erpaccount.xls";
	    }
	    else if(ScImportType==8){
	     	url.href="${pageContext.request.contextPath}/user/account/report/modle/e-bridgeaccount.xls";
	    }
	    else if(ScImportType==9){
	     	url.href="${pageContext.request.contextPath}/user/account/report/modle/e-logisticsaccount.xls";
	    }
	    else if(ScImportType==10){
	     	url.href="${pageContext.request.contextPath}/user/account/report/modle/scmaccount.xls";
	    }
	    else if(ScImportType==11){
	     	url.href="${pageContext.request.contextPath}/user/account/report/modle/pushemailaccount.xls";
	    }
	    else if(ScImportType==13){
	     	url.href="${pageContext.request.contextPath}/user/account/report/modle/biaccount.xls";
	    }
	    else if(ScImportType==15){
	     	url.href="${pageContext.request.contextPath}/user/account/report/modle/telephoneaccount.xls";
	    }
	    else if(ScImportType==16){
	     	url.href="${pageContext.request.contextPath}/user/account/report/modle/mobiletelephoneaccount.xls";
	    }
		if(ScImportType==2){
			myLay.style.display="block"; 
		}else if(ScImportType==5){
			myLay.style.display="block"; 
		}else{
			for(var i = 0;i<ob1.length;i++){
			ob1[i].checked=false;
		    }
			myLay.style.display="none";
		}
		var xform = document.getElementById("fileForm");
	   	xform.ScImportType1.value=ScImportType;
	  	xform.isnew1.value=isNewType;
	}
	function importFile(){ 
		var ob=document.getElementsByName('ScImportType');
		var ScImportType='';
		for(var i = 0;i<ob.length;i++){
	              if(ob[i].checked){
	             	ScImportType=ob[i].value;
	        }
	    }
		var ob1=document.getElementsByName('isnew');
		var isNewType='';
	    for(var i = 0;i<ob1.length;i++){
	              if(ob1[i].checked){
	             	isNewType=ob1[i].value;
	        }
	    }
	
	   if(ScImportType==''){
	   		alert("请选择要导入的帐号类型！")
	   		return false;
	   }
	   var xform = document.getElementById("fileForm");
	   //	xform.ScImportType1.value=ScImportType;
	  	xform.isnew1.value=isNewType;
	  	xform.submit();
	}
	
	</script>
	</HEAD>
<body>
<form id="fileForm" action="${pageContext.request.contextPath}/account/importAccountData.do?methodCall=importAccountDataExcel"
method="post" enctype="multipart/form-data" >
<input type="hidden" name="ScImportType1" value="13" >
<input type="hidden" name="isnew1"  >
<div class="x-panel-body" >
<table  width="100%">
<tr class="x-grid-panel x-panel-mc x-panel-body">
<td width="50" height="25" class="x-grid3-header">
<input class=button  onclick="importFile()" type=button  name="submitexport" value="确定导入">
</td>
<td width="50" height="25" class="x-grid3-header">
<a id="downloadmodle" name="downloadmodle" href="modle/biaccount.xls">下载模板</a>
</td>
<td colspan="4" height="25" class="x-grid3-header">
</td>
</tr>
<tr class="x-grid-panel x-panel-mc ">
<td  class="x-grid3" colspan="6">
请选择导入类型
</td>
</tr>
</table>
<table width="100%" border=0>
<tr class="x-grid-panel x-panel-mc ">
<td class="x-grid3"   height="25">
<input type="radio" name="ScImportType" value="13" onclick="setImportType();" checked>BI帐号
</td>
<td class="x-grid3"  w height="25">
<input type="radio" name="ScImportType" value="9" onclick="setImportType();">E-Logistics帐号
</td>
<td class="x-grid3"  w height="25">
<input type="radio" name="ScImportType" value="10" onclick="setImportType();">SCM帐号
</td>
<td class="x-grid3"   height="25">
<input type="radio" name="ScImportType" value="2" onclick="setImportType();">邮件帐号
</td>
<td class="x-grid3"   height="25">
<input type="radio" name="ScImportType" value="11" onclick="setImportType();">PushMail帐号
</td>
<td class="x-grid3"   height="25">
<input type="radio" name="ScImportType" value="8" onclick="setImportType();">E-Bridge帐号
</td>
</tr>
<tr class="x-grid-panel x-panel-mc ">
<td class="x-grid3"   height="25">
<input type="radio" name="ScImportType" value="7" onclick="setImportType();">ERP帐号
</td>
<td class="x-grid3"  w height="25">
<input type="radio" name="ScImportType" value="4" onclick="setImportType();">MSN帐号
</td>
<td class="x-grid3"  w height="25">
<input type="radio" name="ScImportType" value="3" onclick="setImportType();">WWW帐号
</td>
<td class="x-grid3"   height="25">
<input type="radio" name="ScImportType" value="5" onclick="setImportType();">远程接入帐号
</td>
<td class="x-grid3"   height="25">
<input type="radio" name="ScImportType" value="1" onclick="setImportType();">域帐号
</td>
<td class="x-grid3"   height="25">
<input type="radio" name="ScImportType" value="15" onclick="setImportType();">座机帐号
</td>
<td class="x-grid3"   height="25">
</td>
</tr>
<tr class="x-grid-panel x-panel-mc ">
<td class="x-grid3"   height="25">
<input type="radio" name="ScImportType" value="16" onclick="setImportType();">手机帐号
</td>

</tr>
<tr id="isNewOrUp" class="x-grid-panel x-panel-mc " style="display:none"   >
<td class="x-grid3"   height="25">
<input type="radio" name="isnew" value="0" onclick="setImportType();">新建
</td>
<td class="x-grid3"  w height="25">
<input type="radio" name="isnew" value="1" onclick="setImportType();">更新
</td>
<td class="x-grid3"   height="25">
</td>
<td class="x-grid3"   height="25">
</td>
<td class="x-grid3"   height="25">
</td>
<td class="x-grid3"   height="25">
</td>
</tr>
</table>
<table width="80%" >
<tr class="x-grid-panel x-panel-mc " >
<td class="x-grid3" height="40">
<input name="fromFile" type="file"  id="fromFile" size=41>
</td>
</tr>
</table>
<table width="80%" >
<c:forEach  var="item" items="${errors}">
<tr class="x-grid-panel x-panel-mc " >
<td class="x-grid3" height="40">
${item }
</td>
</tr>
</c:forEach>
</table>
</div>
</form>
</body>	
</HTML>
