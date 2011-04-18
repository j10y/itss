PanelDataPanel = Ext.extend(Ext.tree.TreePanel, {
	title: 'ҳ������ֶ�',
	width: 600,
	animate: true,
	autoScroll: true,
	rootVisible: true,
	containerScroll: true,
//	columnWidth:.5,
	colspan:1,
	lines: true,
	height:500,
	//autoHeight��: true,
	expandable:true,
	enableDD: true,   
    ddGroup: "tgDD",
    ddScroll:true,
    removeFlag: false,

    listeners: { 
        //���ڵ��ƶ�ʱ�����¼�
        movenode: function(tree, node, oldParent, newParent, index) {
				PanelManager.ajaxMoveNode(node.id, oldParent.id, newParent.id, index);
				node.reload();
        },		
		// ���ڵ�ɾ��ʱ�����¼�
//		remove:  function(tree, parentNode, node) {
//				if (this.removeFlag) {
//				   PanelManager.ajaxRemoveNode(node.id);
//				}
//		},
//		contextmenu: function(node, e){
//		            var menu;
//		            if (!menu){
//		                menu = new Ext.menu.Menu([{
//		                    text : 'ɾ�����',
//		                    handler : function(){
//		                       //ɾ�������Ϣ
//		                       Ext.MessageBox.confirm("ɾ����ʾ","�Ƿ����Ҫɾ�����ݣ�",function(ret){
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
		
		//������ģ��ʱ��Ҫ�������text:������������
		this.root = new Ext.tree.AsyncTreeNode({
			text: ppTitle,
			draggable: false,
			id: '0'
		}),
		
		//������ģ��ʱ��Ҫ�������dataUrl:�������ݵ�url
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