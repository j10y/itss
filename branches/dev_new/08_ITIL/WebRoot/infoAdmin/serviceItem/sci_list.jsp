<%@ page language="java" pageEncoding="gbk"%>
<html>
<head>
  <title></title>
  	<%@include file="/includefiles.jsp"%> 
  	<script type="text/javascript" src="${pageContext.request.contextPath}/infoAdmin/serviceItem/sci_list.js"></script>	  
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
    Ext.onReady(function(){	
	      Ext.QuickTips.init();
		  Ext.state.Manager.setProvider(new Ext.state.CookieProvider()); 
		   var pagePanel=new PagePanel();
		   	var pageView = new Ext.Viewport({
					layout : 'fit',
					items : [pagePanel]
				});
				pagePanel.render("pageView");
	});
	</script>
</head>
<div id=pageView stype="text-align:center"></div>
</center>
</body>
</html>