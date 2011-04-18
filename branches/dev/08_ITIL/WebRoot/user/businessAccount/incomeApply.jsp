<%@ page language="java" pageEncoding="gbk"%> 
<html>
<head>
<META HTTP-EQUIV="Content-Type" CONTENT="text/html; charset=utf-8">
<title>config</title>
<%@include file="/includefiles.jsp"%>	
   <script type="text/javascript" src="${pageContext.request.contextPath}/user/businessAccount/incomeApply.js"></script>
   <script type="text/javascript" src="${pageContext.request.contextPath}/user/businessAccount/BaAction.js"></script>
	
	
<style type="text/css">
    .x-head {
				background: url(images/titlelog.png) no-repeat left;
				height: 65px;
				background-color: 'blank'
			}
	html,body {
		font: normal 12px verdana;
		margin: 0;
		padding: 0;
		border: 0 none;
		overflow: hidden;
		height: 100%;
	}
	p {
		margin: 5px;
	}
	.nav {
		background-image: url(images/other/folder_go.png);
	}
	.cls {
		font-size: 9pt;
	}
	.common-text {
		font-size: 9pt;
	}
    </style>
    <script type="text/javascript">
        
    </script>
    <script type="text/javascript">
        Ext.BLANK_IMAGE_URL = '${pageContext.request.contextPath}/extEngine/resources/images/default/s.gif';
        this.dataId = "${dataId}";
        var ba = new BaAction();
        var data = ba.getIncomeBa(this.dataId);
        this.requireId = data.reqId;
        this.applyNum = data.applyNum;
        this.userId = data.userId;
        this.descn = data.descn;
    	Ext.onReady(function(){
    		Ext.QuickTips.init();
    		var panel = this.getTabPanel();			
			var a = new Ext.Viewport({
		          layout:'fit',
		          items:[panel]
       		});
			a.render("Customer");
        });
     </script>   
    
</head>
<body>
<div id="Customer" style="height:100%"></div>
</body>
</html>
