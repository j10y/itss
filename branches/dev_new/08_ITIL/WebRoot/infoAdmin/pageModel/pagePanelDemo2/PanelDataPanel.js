var ddFunction = function(node, refNode, selections) {   
    for(var i = 0; i < selections.length; i ++) {
    	var order = node.indexOf(node.lastChild)+1;
        var record = selections[i];
        //cls�����м�¼������������ϵͳ������������չ�ֶ�
        //���cls="mainColumn",��ʾ��������;���cls="extendColumn",��ʾ����չ�ֶ�
        //icon�����м�¼����������Դ��ϵͳ����ID(tSystemMainTable���ID)
        node.insertBefore(new Ext.tree.TreeNode({   
            text: record.get('stcName'),   
            id: record.get('stcId'),   
            leaf : false   
            //smtId: record.get('smtId'),
            //smtName: record.get('smtName') ,
            //isMainColumn:record.get('isMainColumn') ,
        }), refNode); 
        
         //�ڵ�Id����pagePanelColumnId
         var id = "";
 		 //��ID
    	 var parentId = node.id;
    	 //ϵͳ����Id(��ѡ��ϵͳ���������б��֮��,smtId���ܴ�����)
    	 var smtId = record.get('smtId');        
         //�����ֶ�id
 		 var stcId = record.get('stcId');
         //�����ֶ�����
         var stcName = record.get('stcName');
         //�Ƿ�Ϊϵͳ�����ֶ�'true'ΪSystemMainTableColumn,'false'ΪSystemMainTableExtColumn
         var isMainColumn= record.get('isMainColumn');
         //����PagePanelID
         var pagePanelId = ppId;
         //������Ŀ�Ƿ���������(��Ҷ�ӽ���cls��������־)
         var nodeParent = refNode;
    
     
     //ͬ�����浽���ݿ�   
     DWREngine.setAsync(false);
     PanelManager.ajaxSavePanelColumn(id, parentId, smtId, stcId, pagePanelId,isMainColumn,order);
     DWREngine.setAsync(true);
     //���ڵ��������
     node.reload();
    }   
}

PanelDataPanel = Ext.extend(Ext.tree.TreePanel, {
	title: 'panel',
	animate: true,
	autoScroll: true,
	rootVisible: true,
	containerScroll: true,
	columnWidth:.5,
	lines: true,
	height: 800,
	enableDD: true,   
    ddGroup: "tgDD", 
    removeFlag: false,

    listeners: { 
        beforenodedrop: function(dropEvent) {   
            var node = dropEvent.target;    // Ŀ����   
            var data = dropEvent.data;      // ��ק������   
            var point = dropEvent.point;    // ��ק��Ŀ�����λ��   
            // ���data.nodeΪ�գ�����tree�������ק�����Ǵ�grid��tree����ק����Ҫ����   
            if(!data.node) {   
                switch(point) {   
                    case 'append':   
                        // ���ʱ��Ŀ����Ϊnode���ӽ����Ϊ��   
                        ddFunction(node, null, data.selections);
                        //��������Ϣ(templateItem:id,����/��չ��Id,mainTableItemId,ģ��Id)
                        break;   
                    case 'above':   
                        // ���뵽node֮�ϣ�Ŀ����Ϊnode��parentNode���ӽ��Ϊnode   
                        ddFunction(node.parentNode, node, data.selections);   
                        break;   
                    case 'below':   
                        // ���뵽node֮�£�Ŀ����Ϊnode��parentNode���ӽ��Ϊnode��nextSibling   
                        ddFunction(node.parentNode, node.nextSibling, data.selections);
                        break;   
                }   
            }    
        },
        //���ڵ��ƶ�ʱ�����¼�
        movenode: function(tree, node, oldParent, newParent, index) {
				PanelManager.ajaxMoveNode(node.id, oldParent.id, newParent.id, index);
        },		
		// ���ڵ�ɾ��ʱ�����¼�
		remove:  function(tree, parentNode, node) {
				if (this.removeFlag) {
				   PanelManager.ajaxRemoveNode(node.id);
				}
		},
		contextmenu: function(node, e){
		            var menu;
		            if (!menu){
		                menu = new Ext.menu.Menu([{
		                    text : 'ɾ�����',
		                    handler : function(){
		                       //ɾ�������Ϣ
		                       Ext.MessageBox.confirm("ɾ����ʾ","�Ƿ����Ҫɾ�����ݣ�",function(ret){
								if(ret=="yes"){
			                       var nodeId = node.id;
			                       PanelManager.ajaxRemoveNode(nodeId);
			                       node.parentNode.reload();
							    }
							   });
		                    }
		                }]);
		             menu.showAt(e.getPoint()); 
		           }   
		},
		checkchange:function(node, checked){
			var nodeId = node.id;
			if(checked == true){
				alert(node.id+"ѡ��");
				PanelManager.ajaxIsDisplayNode(nodeId, 1);
			}else{
				alert(node.id+"��ѡ");
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

