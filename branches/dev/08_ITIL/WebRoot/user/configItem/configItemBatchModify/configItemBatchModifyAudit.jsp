<%@ page language="java" pageEncoding="gbk"%>
<html>
<head>
  <title>��ӭʹ��IT����ϵͳ</title>
    <%@include file="/includefiles.jsp"%>                          
  	<script type="text/javascript" src="${pageContext.request.contextPath}/user/configItem/configItemBatchModify/configItemBatchModifyReadOnly.js"></script> 
  	<script type="text/javascript" src="${pageContext.request.contextPath}/user/configItem/configItemBatchModify/configItemBatchModifyAuditHis.js"></script> 
  	
	<style type="text/css"> 
		.x-head{
			background:url(images/titlelog.png) no-repeat left;
			height:65px;
			background-color: 'blank'
		}
		html, body {
	        font:normal 12px verdana;
	        margin:0;
	        padding:0;
	        border:0 none;
	        overflow:hidden;
	        height:100%;
	    }
		p {
		    margin:5px;
		}
	    .nav {
	        background-image:url(images/other/folder_go.png);
	    }
	    .cls {
	    	font-size:9pt;
	    }
	    .common-text {
	    	font-size:9pt;
	    }
    </style>
	<script type="text/javascript">
	window.parent.auditContentWin.setTitle("���ñ������");
	function audit(){
		window.parent.auditContentWin.audit();
	}
	function changeSkin(value){
	   		 Ext.util.CSS.swapStyleSheet('window', webContext+'/ext-3.2.1/resources/css/' + value + '.css');
	}
	function updateTab(id,title, url) {
    	var tab = mainPanel.getItem(id);
  		if(tab){
   			mainPanel.remove(tab);
   		}
    	tab = addTab(id,title,url);
   		mainPanel.setActiveTab(tab);
    }
	
	Ext.BLANK_IMAGE_URL = webContext+'/ext-3.2.1/resources/images/default/s.gif';
	Ext.onReady(function(){	
	   Ext.QuickTips.init();
	   Ext.state.Manager.setProvider(new Ext.state.CookieProvider());
	   var dataId="${param.dataId}"
	   	var conn = Ext.lib.Ajax.getConnectionObject().conn;
		var url=webContext+"/configItemAction_getModifyTypeAndTypeId.action?modifyId="+dataId;
		conn.open("post", url, false);
		conn.send(null);
		if (conn.status == "200") {
			var responseText = conn.responseText;
			var result=Ext.decode(responseText);
			var type=result.type;
			var typeId=result.typeId;
		}
	   var pageTemplates = new PageTemplates({dataId:dataId,type:type,typeId:typeId,
	   										buttons:[{text :'����',
												id:'submit',
												handler : function(){
													audit();
												},
												scope : this,
												iconCls : 'submit',
												cls : "x-btn-text-icon"
											}]
	   });
	   new Ext.Viewport({
	   	 layout:'fit',
	   	 items:[pageTemplates]
	   });
	});
	
	Ext.override(Ext.grid.GridView, {
    templates: {
        cell: new Ext.Template(
               '<td class="x-grid3-col x-grid3-cell x-grid3-td-{id} {css}" style="{style}" tabIndex="0" {cellAttr}>',
               '<div class="x-grid3-cell-inner x-grid3-col-{id}" {attr}>{value}</div>',
               "</td>"
           )
    }
	});
	</script>
</head>
<body onload="changeSkin('${userInfo.userViewStyle}');">
</body>
</html>
