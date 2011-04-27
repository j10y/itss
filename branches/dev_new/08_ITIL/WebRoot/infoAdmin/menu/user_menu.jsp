<%@ page contentType="text/html;charset=gbk" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=gbk">
	<title>菜单模板</title>
	<%@include file="/includefiles.jsp"%>
	<style type="text/css">
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
        background-image:url(${pageContext.request.contextPath}/extEngine/resources/images/other/folder_go.png);
    }
    .cls {
    	font-size:9pt;
    }
    .common-text {
    	font-size:9pt;
    }
    
    </style>
    <script type="text/javascript" src="${pageContext.request.contextPath}/infoAdmin/menu/user_menu.js"></script>
    <script type="text/javascript">
	Ext.onReady(function(){	
	    Ext.QuickTips.init();
	    Ext.BLANK_IMAGE_URL = webContext+'/extEngine/resources/images/default/s.gif';
		  Ext.state.Manager.setProvider(new Ext.state.CookieProvider()); 
          var pagePanel = new PagePanel();	
		  new Ext.Viewport({
		   	 layout:'fit',
		   	 items:[pagePanel]
		  });
	});
	</script>
</head>
<body>
</body>
</html>