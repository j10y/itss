<%@ page language="java" pageEncoding="gbk"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head> 
  <title>欢迎使用IT服务系统</title>   
    <%@include file="/includefiles.jsp"%> 	  
  	<script type="text/javascript" src="${pageContext.request.contextPath}/user/event/transactionFlow/engineer.js"></script>
  	<script type="text/javascript" src="${pageContext.request.contextPath}/user/event/transactionFlow/saveSolution.js"></script>
  	<script type="text/javascript">
  	</script>  
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
		var createUser = '';
		var submitUser = '';
		var dealuser = '';
		var dataId = "${param.dataId}";
		var frequency ='';
		var ponderance ='';
		var frequencyId ='';
		var ponderanceId ='';
		var frequencyName ='';
		var ponderanceName ='';
		var sUserPhone='';
		var problemSortId='';
		Ext.onReady(function(){	
	   Ext.QuickTips.init();
	   Ext.BLANK_IMAGE_URL = webContext+'/extEngine/resources/images/default/s.gif';
	    //-------------------------------页面ajax异步请求获取属性值setValue不成功,采用同步请求-- by wanghao
	   var conn = Ext.lib.Ajax.getConnectionObject().conn;
	   		  //url后边加上随机参数,保证每次都会去服务器获取频率和严重性两个值(解决数据不一致的错误) -- by wanghao
	  		  var timestamp =new Date().getTime();
			  var url = webContext + '/eventAction_findEventByDataId.action?dataId='+dataId+'&timestamp=' + timestamp;
			  conn.open("GET", url, false);
			  conn.send(null);
			  if (conn.status == "200") {
			   var responseText = conn.responseText;
			   var data = Ext.decode(responseText);
			   createUser = data.createUser;
			   submitUser = data.submitUser;
			   dealuser = data.dealuser;
			   frequencyId =data.frequency;
			   ponderanceId =data.ponderance;
			   frequencyName =data.frequencyName;
			   ponderanceName =data.ponderanceName;
			   sUserPhone=data.sUserPhone; //2010-06-23 modified by huzh for 添加事件提交人电话
			   problemSortId=data.problemSort; //2010-07-23 add by huzh for 问题类别
			  }
	   //-------------------------------
	   Ext.state.Manager.setProvider(new Ext.state.CookieProvider()); 
	   var pagePanel = new PagePanel({dataId:"${param.dataId}",taskId : '${param.taskId}',activetab : '${param.activetab}'});
	   pagePanel.render("Customer");
	   new Ext.Viewport({
	   	 layout:'fit',
	   	 items:[pagePanel]
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
<div id="Customer"></div>
</body>
</html>
