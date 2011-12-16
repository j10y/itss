<%@ page pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/admin.css" />
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/ext3/resources/css/ext-all.css" />
		<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/ext3/resources/css/ext-patch.css" />
		<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/ext3/ux/css/Portal.css" />
		<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/ext3/ux/css/Ext.ux.UploadDialog.css" />
		<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/ext3/ux/treegrid/treegrid.css" />
		<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/admin.css"/>
		
		<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/ext3/ux/css/ux-all.css"/>
		<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/ext3/ux/caltask/calendar.css" />
		<!-- load the extjs libary -->
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/dynamic.jsp"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery-1.4.2.min.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/ext3/adapter/ext/ext-base.gzjs"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/ext3/ext-all.gzjs"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/ext3/vtype.mine.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/ext3/ext-lang-zh_CN.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/core/ScriptMgr.js"></script>
		
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/App.import.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/core/AppUtil.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/print/Printer-all.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/print/renderers/GridPanel.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/export/Exporter-all.js"></script>
		
		<script type="text/javascript" src="<%=request.getContextPath()%>/ext3/ux/Ext.ux.IconCombob.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/ext3/ux/RowEditor.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/fckeditor/fckeditor.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/ext3/ux/Fckeditor.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/ext3/ux/XmlTreeLoader.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/ext3/ux/FileUploadField.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/ext3/ux/UploadDialog.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/ext3/ux/CheckColumn.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/ext3/ux/Toast.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/ext3/ux/Ext.ux.grid.RowActions.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/ext3/ux/DateTimeField.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/ext3/ux/TabCloseMenu.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/ext3/ux/TableFormLayout.js"></script>
    	<link href="<%=request.getContextPath()%>/css/desktop.css" rel="stylesheet" type="text/css" />	
    	<script type="text/javascript" src="<%=request.getContextPath()%>/js/ScrollText.js"></script>
    	<script type="text/javascript" src="<%=request.getContextPath()%>/ext3/ext-basex.js"></script>
    	<style type="text/css">
    	body {width:960px;margin:0 auto;}
		.table-info td { padding:5px; line-height:24px; font-size:12px;}
		.table-info table td  { border-bottom:1px solid #e0e0e0;}
		</style>
    	<script type="text/javascript">
    		Ext.onReady(function(){
    			var panel = new Ext.Panel({
							xtype : "panel",
							height : 270,
							id : "LogoPanel",
							autoScroll : true,
							renderTo : "imagepanel",
							html : ''
						}); 
						
				new Ext.form.ComboBox({
							name : "infoPoor.bandName",
							id : "infoPoor.bandName",
							maxHeight : 200,
							width:200,
							xtype : "combo",
							mode : "local",
							editable : false,
							triggerAction : "all",
							valueField : "fromBandId",
							displayField : "fromBandName",
							renderTo : "selectBand",
							store : new Ext.data.SimpleStore({
								url : __ctxPath
								 + "/bandpoor/getAllBandsScoreManage.do",
								fields : [
									"fromBandId",
									"fromBandName"]
							}),
							listeners : {
								focus : function (b) {
									var a = Ext.getCmp("infoPoor.bandName").getStore();
									if (a.getCount() <= 0) {
										Ext.Ajax.request({
											url : __ctxPath + "/bandpoor/getAllBandsScoreManage.do",
											method : "post",
											success : function (d) {
												var c = Ext.util.JSON.decode(d.responseText);
												a.loadData(c);
											}
										});
									}
								}
							}
						}
				);	
				new Ext.form.ComboBox({
							name : "infoPoor.proClassName",
							id : "infoPoor.proClassName",
							maxHeight : 200,
							width:200,
							xtype : "combo",
							mode : "local",
							editable : true,
							triggerAction : "all",
							valueField : "fromProClassId",
							displayField : "fromProClassName",
							renderTo : "selectProClass",
							store : new Ext.data.SimpleStore({
								url : __ctxPath
								 + "/bandpoor/getProClassScoreManage.do",
								fields : [
									"fromProClassId",
									"fromProClassName"]
							}),
							listeners : {
								focus : function (b) {
									var a = Ext.getCmp("infoPoor.proClassName").getStore();
									if (a.getCount() <= 0) {
										Ext.Ajax.request({
											url : __ctxPath + "/bandpoor/getProClassScoreManage.do",
											method : "post",
											success : function (d) {
												var c = Ext.util.JSON.decode(d.responseText);
												a.loadData(c);
											}
										});
									}
								}
							}
						}
				);
				
				new Ext.form.ComboBox({
						name : "infoPoor.saleStoreName",
						id : "infoPoor.saleStoreName",
						maxHeight : 200,
						width:200,
						xtype : "combo",
						mode : "local",
						editable : true,
						triggerAction : "all",
						valueField : "saleStoreid",
						displayField : "saleStoreName",
						renderTo : "selectSaleStore",
						store : new Ext.data.SimpleStore({
							url : __ctxPath
							 + "/bandpoor/getSaleStoreScoreManage.do",
							fields : [
								"saleStoreid",
								"saleStoreName"]
						}),
						listeners : {
							focus : function (b) {
								var a = Ext.getCmp("infoPoor.saleStoreName").getStore();
								if (a.getCount() <= 0) {
									Ext.Ajax.request({
										url : __ctxPath + "/bandpoor/getSaleStoreScoreManage.do",
										method : "post",
										success : function (d) {
											var c = Ext.util.JSON.decode(d.responseText);
											a.loadData(c);
										}
									});
								}
							}
						}
					}
				);	
				
				new Ext.form.ComboBox({
						name : "infoPoor.bandChannelName",
						id : "infoPoor.bandChannelName",
						maxHeight : 200,
						width:200,
						xtype : "combo",
						mode : "local",
						editable : true,
						triggerAction : "all",
						valueField : "bandChannelID",
						displayField : "bandChannelName",
						renderTo : "selectBandChannel",
						store : new Ext.data.SimpleStore({
							url : __ctxPath
							 + "/bandpoor/getChannelScoreManage.do",
							fields : [
								"bandChannelID",
								"bandChannelName"]
						}),
						listeners : {
							focus : function (b) {
								var a = Ext.getCmp("infoPoor.bandChannelName").getStore();
								if (a.getCount() <= 0) {
									Ext.Ajax.request({
										url : __ctxPath + "/bandpoor/getChannelScoreManage.do",
										method : "post",
										success : function (d) {
											var c = Ext.util.JSON.decode(d.responseText);
											a.loadData(c);
										}
									});
								}
							}
						}
					}
				);		
    		})
    		removeFile = function (e, a) {
				var b =document.getElementById("infoPoor.picuurepathid");
				var d = b.value;
				if (d.indexOf(",") < 0) {
				b.value="";
				} else {
				d = d.replace("," + a, "").replace(a + ",", "");
				b.value=d;
				}
				var c = Ext.get(e.parentNode);
				c.remove();
			};

    		function uploadImage(){
    		var e = App.createUploadDialog({
					file_cat : "system/company",
					callback : function (g) {
						var d = document.getElementById("infoPoor.picuurepathid");
						for (var e = 0; e < g.length; e++) {
							if (d.value != "") {
								d.value=d.value + ",";
							}
							d.value=d.value + g[e].fileId;
							var gg = Ext.getCmp("LogoPanel");
							Ext.DomHelper.append(gg.body, '<span><img src="' + "" +  __ctxPath +g[e].filepath +
								'"  width="100" height="100" /><img class="img-delete" src="' + __ctxPath +
								'/images/system/delete.gif" onclick="removeFile(this,' + g[e].fileId + ')"/>&nbsp;|&nbsp;</span>');
						}
					},
					permitted_extensions : ["jpg", "png"]
				});
				e.show("queryBtn");
    		} 
    		function saveInfo(){
    			var bandId=Ext.getCmp("infoPoor.bandName").getValue();
    			var bandName=Ext.getCmp("infoPoor.bandName").getRawValue();
    			
    			var proClassId=Ext.getCmp("infoPoor.proClassName").getValue();
    			var proClassName=Ext.getCmp("infoPoor.proClassName").getRawValue();
    			
    			var mainProductName=document.getElementById("mainProductName").value;
    			
    			var webSite=document.getElementById("webSite").value;
    			
    			//var mainPriceId=Ext.getCmp("infoPoor.mainPriceName").getValue();
    			//var mainPriceName=Ext.getCmp("infoPoor.mainPriceName").getRawValue();
    			
    			var contactUser=document.getElementById("contactUser").value;
    			
    			var saleStoreId=Ext.getCmp("infoPoor.saleStoreName").getValue();
    			var saleStoreName=Ext.getCmp("infoPoor.saleStoreName").getRawValue();
    			
    			var saleSroteDesc=document.getElementById("saleSroteDesc").value;
    			
    			var bandChannelId=Ext.getCmp("infoPoor.bandChannelName").getValue();
    			var bandChannelName=Ext.getCmp("infoPoor.bandChannelName").getRawValue();
    			
    			var contactPhone=document.getElementById("contactPhone").value;
    			
    			var companyAddress=document.getElementById("companyAddress").value;
    			
    			var companyNature=document.getElementById("companyNature").value;
    			
    			var productLine=document.getElementById("productLine").value;
    			
    			var bandDesc=document.getElementById("bandDesc").value;
    			
    			var picuurepathid=document.getElementById("infoPoor.picuurepathid").value;
    			
    			var mainPriceStart=document.getElementById("mainPriceStart").value;
    			
    			var mainPriceEnd=document.getElementById("mainPriceEnd").value;
    			
    			if(proClassId==null||proClassId.length==0){
    				alert("品类归属不能为空，为必填项！");
    				return;
    			}
    			if(mainProductName==null||mainProductName.length==0){
    				alert("主力商品不能为空，为必填项！");
    				return;
    			}
    			if(mainPriceStart==null||mainPriceStart.length==0||mainPriceEnd==null||mainPriceEnd.length==0){
    				alert("主力价格不能为空，为必填项！");
    				return;
    			}
    			if(companyNature==null||companyNature.length==0){
    				alert("公司性质不能为空，为必填项！");
    				return;
    			}
    			if(companyAddress==null||companyAddress.length==0){
    				alert("产地不能为空，为必填项！");
    				return;
    			}
    			if(bandChannelId==null||bandChannelId.length==0){
    				alert("渠道不能为空，为必填项！");
    				return;
    			}
    			if(productLine==null||productLine.length==0){
    				alert("产品线不能为空，为必填项！");
    				return;
    			}
    			if(bandDesc==null||bandDesc.length==0){
    				alert("品牌描述不能为空，为必填项！");
    				return;
    			}
    			if((saleStoreName=="其他")&&(saleSroteDesc==null||saleSroteDesc.length==0)){
    				alert("若销售场所选择为其他，则销售场所描述不能为空，为必填项！");
    				return;
    			}
    			if((bandId==null||bandId.length==0)&&(contactUser==null||contactUser.length==0)){
    				alert("若没有找到相关品牌，则联系人不能为空为必填项！");
    				return;
    			}
    			if((bandId==null||bandId.length==0)&&(contactPhone==null||contactPhone.length==0)){
    				alert("若没有找到相关品牌，则联系电话不能为空为必填项！");
    				return;
    			}
    			var url=__ctxPath+"/bandpoor/saveAutoCollenction.do";
			    var params={bandId:bandId,bandName:bandName,proClassId:proClassId,
			    proClassName:proClassName,mainProductName:mainProductName,
			    webSite:webSite,mainPriceStart:mainPriceStart,mainPriceEnd:mainPriceEnd,
			    contactUser:contactUser,saleStoreId:saleStoreId,saleStoreName:saleStoreName,
			    saleSroteDesc:saleSroteDesc,bandChannelId:bandChannelId,bandChannelName:bandChannelName,
			    contactPhone:contactPhone,companyAddress:companyAddress,companyNature:companyNature,
			    productLine:productLine,bandDesc:bandDesc,picuurepathid:picuurepathid};
			    var tree;
				$.post(
					url, //服务器要接受的url
					params, //传递的参数 
					function(json){ 
						var dataObj=eval("("+json+")");//
						if(dataObj['success']==true){
						alert("保存成功");
						}
					})
    		}
    	</script>
</head>
<body>
  <body>
  <table align="center" class="table-info" border="0" cellpadding="0" cellspacing="0" width="950">
         <tr ><td align="left" colspan="4" style="font-size:14px; font-weight:bold; color:#f15930;border-bottom:1px solid #f15930; ">自动采集页面</td></tr>
         <tr><td style="padding:30px 125px;">
     	<table border="0" cellpadding="0" cellspacing="0" width="700">
         <tr >
           <td >品牌名称</td>
           <td>
           
           <div id="selectBand"></div>
           </td>
           <td>品类归属</td>
           <td>
            <div id="selectProClass"></div>
           </td>
         </tr>
         <tr >
           <td>主力商品</td>
           <td>
           <input type="text" id="mainProductName" style="width:200px;height:18px"/>
           </td>
           <td>官网</td>
           <td>
           <input type="text" id="webSite" style="width:200px;height:18px"/>
           </td>
         </tr>
         <tr >
           <td>主力价格带</td>
           <td>
           <table>
           <tr><td><input type="text" id="mainPriceStart" style="width:50px;height:18px"/></td><td>~</td><td><input type="text" id="mainPriceEnd" style="width:50px;height:18px"/></td><td>元</td></tr></table>
           </td>
           <td>联系人</td>
           <td>
           <input type="text" id="contactUser" style="width:200px;height:18px"/>
           </td>
         </tr>
         <tr >
           <td>销售场所</td>
           <td>
           
            <div id="selectSaleStore"></div>
           </td>
           <td>销售场所描述</td>
           <td>
           <input type="text" id="saleSroteDesc" style="width:200px;height:18px"/>
           </td>
         </tr>
         <tr >
           <td>渠道来源</td>
           <td>
          
            <div id="selectBandChannel"></div>
           </td>
           <td>联系方式</td>
           <td>
           <input type="text" id="contactPhone" style="width:200px;height:18px"/>
           </td>
         </tr>
         <tr >
           <td>产地</td>
           <td>
           <input type="text"  id="companyAddress" style="width:200px;height:18px"/>
           </td>
           <td>公司性质</td>
           <td>
           <input type="text" id="companyNature" style="width:200px;height:18px"/>
           </td>
         </tr>
         <tr >
           <td>产品线</td>
           <td>
           <input type="text" id="productLine" style="width:200px;height:18px"/>
           </td>
           <td></td>
           <td></td>
         </tr>
          <tr >
           <td >品牌介绍</td>
           <td colspan="3">
           <textarea id="bandDesc" rows="5" cols="70"></textarea>
           </td>
         </tr>
          <tr >
           <td ><input type="button" onclick="uploadImage()" value="上传图片"></td>
           <td colspan="3">
           <input type="hidden" id="infoPoor.picuurepathid" name="infoPoor.picuurepathid"value=""/>
           </td>
         </tr>
         <tr >
           <td style="height:300px"colspan="4">
           <div id="imagepanel">
           </div>
           </td>
         </tr>
         <tr >
           <td align="center" colspan="4">
          <input type="button" name="保存" value="提交" onclick="saveInfo()"/>
           </td>
         </tr>
     </table></td></tr> 
    </table>
  </body>
</html>
