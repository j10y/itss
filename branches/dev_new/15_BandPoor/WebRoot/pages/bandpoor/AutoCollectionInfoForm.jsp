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
								},
								select:function(e,c,d){
									var bandId=Ext.getCmp("infoPoor.bandName").getValue();
									Ext.getCmp("infoPoor.bandId").setValue(bandId);
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
    		function getBand(){
	    		 var url=__ctxPath + "/bandpoor/getBandsAllScoreManage.do";
				 var params={};
				 var tree;
				 $.post(
						url, //服务器要接受的url
						params, //传递的参数 
						function(json){ 
							var dataObj=eval("("+json+")");//
							var op="<option id=''></option>";
							for(var i=0;i<dataObj.length;i++){
								op+="<option id='"+dataObj[i][0]+"'>"+dataObj[i][1]+"</option>";
							}
							$("#bandName").html(op);
						})
    		}
    	</script>
</head>
<body>
  <body>
    <table align="center" class="table-info" width="700">
         <tr ><td align="center" colspan="4">自动采集页面</td></tr>
         <tr >
           <td >品牌名称</td>
           <td>
           <div id="selectBand"></div>
           </td>
           <td>品类归属</td>
           <td>
           <select style="width:120px">
           <option></option>
           </select>
           </td>
         </tr>
         <tr >
           <td>主力商品</td>
           <td>
           <input type="text" style="width:120px"/>
           </td>
           <td>官网</td>
           <td>
           <input type="text" style="width:120px"/>
           </td>
         </tr>
         <tr >
           <td>主力价格带</td>
           <td>
           <select style="width:120px">
           <option></option>
           </select>
           </td>
           <td>联系人</td>
           <td>
           <input type="text" style="width:120px"/>
           </td>
         </tr>
         <tr >
           <td>销售场所</td>
           <td>
           <select style="width:120px">
           <option></option>
           </select>
           </td>
           <td>销售场所描述</td>
           <td>
           <input type="text" style="width:120px"/>
           </td>
         </tr>
         <tr >
           <td>渠道来源</td>
           <td>
           <select style="width:120px">
           <option></option>
           </select>
           </td>
           <td>联系方式</td>
           <td>
           <input type="text" style="width:120px"/>
           </td>
         </tr>
         <tr >
           <td>产地</td>
           <td>
           <input type="text" style="width:120px"/>
           </td>
           <td>公司性质</td>
           <td>
           <input type="text" style="width:120px"/>
           </td>
         </tr>
         <tr >
           <td>生产线</td>
           <td>
           <input type="text" style="width:120px"/>
           </td>
           <td></td>
           <td></td>
         </tr>
          <tr >
           <td >品牌介绍</td>
           <td colspan="3">
           <textarea rows="5" cols="70"></textarea>
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
    </table>
  </body>
</html>
