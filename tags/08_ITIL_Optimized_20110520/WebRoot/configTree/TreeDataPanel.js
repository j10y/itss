/*
 * Ŀ���������
 */
 
//Ŀ��ڵ㣬�ӽڵ㣬��ѡ�������
var ddFunction = function(node, refNode, selections) {   
    for(var i = 0; i < selections.length; i ++) {
    	
    	var order = node.indexOf(node.lastChild)+1;
    	
        var record = selections[i];
        //cls�����м�¼������������ϵͳ������������չ�ֶ�
        //���cls="mainColumn",��ʾ��������;���cls="extendColumn",��ʾ����չ�ֶ�
        //icon�����м�¼����������Դ��ϵͳ����ID(tSystemMainTable���ID)
        node.insertBefore(new Ext.tree.TreeNode({   
            text: record.get('name'),   
            id: record.get('id'),   
            leaf: record.get('leaf')   
           // cls: record.get('cls'),
            //icon: record.get('icon') 
        }), refNode); //�����ǲ��Ǹ��ӽڵ�
        
         //ģ��
         var id = "";
 		 //ģ������Ŀ���ڵ�ID
    	 var parentId = node.id;
    	 //ϵͳ����Id(��ѡ��ϵͳ���������б��֮��,smtId���ܴ�����)
    	 //var smtId = record.get('icon');        
         //������Ŀid
 		 var itemId = record.get('id');
         //������Ŀ����
         var name = record.get('name');
         //�Ƿ���Ҷ��
         var isLeaf = record.get('leaf');
         //ģ��id
         //var templateId = tmpId;
         //������Ŀ�Ƿ���������(��Ҷ�ӽ���cls��������־)
         //var flag = record.get('cls');
         var nodeParent = refNode;
     
     //ͬ�����浽���ݿ�   
     DWREngine.setAsync(false);
     TemplateManager.ajaxSaveTemplateItem(id, parentId, name, smtId, itemId, templateId, flag, order);
     //��ʲô�ط������ˣ���ô�ã�
     DWREngine.setAsync(true);
     //���ڵ��������
     node.reload();
    }   
}

TreeDataPanel = Ext.extend(Ext.tree.TreePanel, {
	title: '������',
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

    listeners: { //��ק֮ǰ�ᴥ����������Ա������ק������
        beforenodedrop: function(dropEvent) {   
            var node = dropEvent.target;    // Ŀ����   
            var data = dropEvent.data;      // ��ק������   
            var point = dropEvent.point;    // ��ק��Ŀ�����λ��   
            // ���data.nodeΪ�գ�����tree�������ק�����Ǵ�grid��tree����ק����Ҫ����   
            if(!data.node) {   
                switch(point) {   
                    case 'append':   
                        // ���ʱ��Ŀ����Ϊnode���ӽ����Ϊ��   
                        ddFunction(node, null, data.selections);//selections��ʾ����˼?
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
				TemplateManager.ajaxMoveNode(node.id, oldParent.id, newParent.id, index);
        },		
		// ���ڵ�ɾ��ʱ�����¼�
		remove:  function(tree, parentNode, node) {
				if (this.removeFlag) {
				   TemplateManager.ajaxRemoveNode(node.id);
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
			                       TemplateManager.ajaxRemoveNode(nodeId);
			                       node.parentNode.reload();
							    }
							   });
		                    }
		                }]);
		             menu.showAt(e.getPoint()); 
		           }   
		}
	},
    
	initComponent: function() {
		
		//������ģ��ʱ��Ҫ�������text:������������
		this.root = new Ext.tree.AsyncTreeNode({
			text: '������',
			draggable: false,
			id: '100'     		
		}),
		
		//������ģ��ʱ��Ҫ�������dataUrl:�������ݵ�url
		this.loader = new Ext.tree.TreeLoader({
			dataUrl:webContext+'/treeSourceData.action'
		});
		
		this.loader.on('beforeload', function(treeloader, node) {
					treeloader.baseParams = {
						id : node.id					
					};
					
		}, this);
		
						 
		TreeDataPanel.superclass.initComponent.call(this);
	}
});

