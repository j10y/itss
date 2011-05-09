PanelDataPanel = Ext.extend(Ext.tree.TreePanel, {
	title: '页面面板字段',
	width: 600,
	animate: true,
	autoScroll: true,
	rootVisible: true,
	containerScroll: true,
//	columnWidth:.5,
	colspan:1,
	lines: true,
	height:500,
	//autoHeight　: true,
	expandable:true,
	enableDD: true,   
    ddGroup: "tgDD",
    ddScroll:true,
    removeFlag: false,

    listeners: { 
        //当节点移动时触发事件
        movenode: function(tree, node, oldParent, newParent, index) {
				PanelManager.ajaxMoveNode(node.id, oldParent.id, newParent.id, index);
				node.reload();
        },		
		// 当节点删除时触发事件
//		remove:  function(tree, parentNode, node) {
//				if (this.removeFlag) {
//				   PanelManager.ajaxRemoveNode(node.id);
//				}
//		},
//		contextmenu: function(node, e){
//		            var menu;
//		            if (!menu){
//		                menu = new Ext.menu.Menu([{
//		                    text : '删除结点',
//		                    handler : function(){
//		                       //删除结点信息
//		                       Ext.MessageBox.confirm("删除提示","是否真的要删除数据？",function(ret){
//								if(ret=="yes"){
//			                       var nodeId = node.id;
//			                       PanelManager.ajaxRemoveNode(nodeId);
//			                       node.parentNode.reload();
//							    }
//							   });
//		                    }
//		                }]);
//		             menu.showAt(e.getPoint()); 
//		           }   
//		},
		checkchange:function(node, checked){
			var nodeId = node.id;
			if(checked == true){
				PanelManager.ajaxIsDisplayNode(nodeId, 1);
			}else{
				PanelManager.ajaxIsDisplayNode(nodeId, 0);
			}
		}
	},
    
	initComponent: function() {
		
		//创建此模板时需要传入参数text:树根结点的名称
		this.root = new Ext.tree.AsyncTreeNode({
			text: ppTitle,
			draggable: false,
			id: '0'
		}),
		
		//创建此模板时需要传入参数dataUrl:加载数据的url
		this.loader = new Ext.tree.TreeLoader({
			dataUrl:webContext+'/pageModel/pagePanelManage.do?methodCall=loadPagePanel'
		});
		
		this.loader.on('beforeload', function(treeloader, node) {
					treeloader.baseParams = {
						id : node.id,
						ppId : ppId
					};
		}, this);
				 
		PanelDataPanel.superclass.initComponent.call(this);
	}
});