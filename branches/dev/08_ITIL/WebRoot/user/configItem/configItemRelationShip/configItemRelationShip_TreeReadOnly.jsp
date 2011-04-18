<%@ page language="java" pageEncoding="gbk"%> 
<html>
<head>
    <%@include file="/includefiles.jsp"%>
   <script type="text/javascript" src="${pageContext.request.contextPath}/user/configItem/configItemRelationShip/configItemRelationShip_TreeReadOnly.js"></script>
<style type="text/css">
    #tree1, #tree2 {
    	float:left;
    	margin:20px;
    	border:1px solid #c3daf9;
    	width:250px;
    	height:300px;
    }
    .folder .x-tree-node-icon{
		background:transparent url(../tree-image/folder.gif);
	}
	.x-tree-node-expanded .x-tree-node-icon{
		background:transparent url(../tree-image/folder-open.gif);
	}
    .common-text {
    	font-size:9pt;
    }

    </style>
    <script type="text/javascript">
    	function changeSkin(value){
	   		 Ext.util.CSS.swapStyleSheet('window', webContext+'/ext-3.2.1/resources/css/' + value + '.css');
		}
        Ext.BLANK_IMAGE_URL = '${pageContext.request.contextPath}/ext-3.2.1/resources/images/default/s.gif';
    	Ext.onReady(function(){
			var tree = new PagteModelTreePanel();
			new Ext.Viewport({
		          layout:'fit',
		          items:[tree]
       		});
        });
        Ext.override(Ext.tree.TreeNodeUI, {
				onDblClick :function(e){
					e.preventDefault();
					if(this.disabled){
						return;
					}
					if(this.checkbox){
						this.toggleCheck();
					}
					if(!this.animating&& this.node.hasChildNodes()){
					 	var isExpand = this.node.ownerTree.doubleClickExpand;
						if(isExpand){
							this.node.toggle();
						};
					}
					this.fireEvent("dblclick",this.node, e);
			}    
		});
     </script>   
</head>
<body onload="changeSkin('${userInfo.userViewStyle}');">
<div id="panel"></div>
</body>
</html>
