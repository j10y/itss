<%@ page language="java" pageEncoding="gbk"%>
<html>
<head>
  <title></title>
   <%@include file="/includefiles.jsp"%>    	  
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
	function changeSkin(value){
	   		 Ext.util.CSS.swapStyleSheet('window', webContext+'/extEngine/resources/css/' + value + '.css');
	}
	function updateTab(id,title, url) {
    	var tab = mainPanel.getItem(id);
  		if(tab){
   			mainPanel.remove(tab);
   		}
    	tab = addTab(id,title,url);
   		mainPanel.setActiveTab(tab);
    }
	
	Ext.BLANK_IMAGE_URL = webContext+'/extEngine/resources/images/default/s.gif';
	Ext.onReady(function(){	
	   Ext.QuickTips.init();
	   Ext.state.Manager.setProvider(new Ext.state.CookieProvider());
	   var tabs = new Ext.TabPanel({
	        width:450,
	        activeTab: 0,
	        frame:true,
	        height: 600,
	        items:[
	            {title: '事件类型维护',
	            autoLoad : {
					url : webContext + "/tabFrame.jsp?url=" + webContext
							+ "/user/event/eventTypeManager/eventTypeManager.jsp",
					text : "页面正在加载中......",
					method : 'post',
					scope : this
					}
	            },
	            {title: '服务项事件类型查询',
	            autoLoad : {
					url : webContext + "/tabFrame.jsp?url=" + webContext
							+ "/user/event/eventTypeManager/serviceItemEventTypeSearch.jsp",
					text : "页面正在加载中......",
					method : 'post',
					scope : this
					}
	            }         		            	            
	        ]
    	});
	 new Ext.Viewport({
		enableTabScroll:true,
		layout:'fit', 
        width: 1000,
	   	items:[tabs]
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
