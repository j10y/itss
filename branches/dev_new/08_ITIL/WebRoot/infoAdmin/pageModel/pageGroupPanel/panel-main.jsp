<%@ page contentType="text/html;charset=gbk" %>
<%@ include file="/infoAdmin/template/userMenu/in.jsp"%>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=gbk">
	<title>菜单定制</title>
	<%@include file="/includefiles.jsp"%>
	<script type="text/javascript" src="${pageContext.request.contextPath}/dwr/engine.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/dwr/util.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/dwr/interface/PanelManager.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/infoAdmin/pageModel/pagePanelDemo3/PanelComponent.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/infoAdmin/pageModel/pagePanelDemo3/PanelDataPanel.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/infoAdmin/pageModel/pagePanelDemo3/ButtonDataPanel.js"></script>
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
	<script type="text/javascript">
        Ext.BLANK_IMAGE_URL = '${pageContext.request.contextPath}/extEngine/resources/images/default/s.gif';
        var pagePanelId = '${pagePanelId}';
    	Ext.onReady(function(){			
			var panel = new PanelComponent();
			panel.render(Ext.getBody());
				
        });
     </script> 
</head>
<body>
</body>
</html>