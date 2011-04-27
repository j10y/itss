var ddFunction = function(node, refNode, selections) {   
    for(var i = 0; i < selections.length; i ++) {
    	var order = node.indexOf(node.lastChild)+1;
        var record = selections[i];
        //cls属性中记录的是数据来自系统主表还是来自扩展字段
        //如果cls="mainColumn",表示来自主表;如果cls="extendColumn",表示是扩展字段
        //icon属性中记录的是数据来源的系统主表ID(tSystemMainTable表的ID)
        node.insertBefore(new Ext.tree.TreeNode({   
            text: record.get('stcName'),   
            id: record.get('stcId'),   
            leaf : false   
            //smtId: record.get('smtId'),
            //smtName: record.get('smtName') ,
            //isMainColumn:record.get('isMainColumn') ,
        }), refNode); 
        
         //节点Id亦是pagePanelColumnId
         var id = "";
 		 //父ID
    	 var parentId = node.id;
    	 //系统主表Id(当选择系统主表下拉列表框之后,smtId才能传过来)
    	 var smtId = record.get('smtId');        
         //主表字段id
 		 var stcId = record.get('stcId');
         //主表字段名称
         var stcName = record.get('stcName');
         //是否为系统主表字段'true'为SystemMainTableColumn,'false'为SystemMainTableExtColumn
         var isMainColumn= record.get('isMainColumn');
         //所属PagePanelID
         var pagePanelId = ppId;
         //数据条目是否来自主表(用叶子结点的cls属性来标志)
         var nodeParent = refNode;
    
     
     //同步保存到数据库   
     DWREngine.setAsync(false);
     PanelManager.ajaxSavePanelColumn(id, parentId, smtId, stcId, pagePanelId,isMainColumn,order);
     DWREngine.setAsync(true);
     //父节点加载数据
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
            var node = dropEvent.target;    // 目标结点   
            var data = dropEvent.data;      // 拖拽的数据   
            var point = dropEvent.point;    // 拖拽到目标结点的位置   
            // 如果data.node为空，则不是tree本身的拖拽，而是从grid到tree的拖拽，需要处理   
            if(!data.node) {   
                switch(point) {   
                    case 'append':   
                        // 添加时，目标结点为node，子结点设为空   
                        ddFunction(node, null, data.selections);
                        //保存结点信息(templateItem:id,主表/扩展表Id,mainTableItemId,模板Id)
                        break;   
                    case 'above':   
                        // 插入到node之上，目标结点为node的parentNode，子结点为node   
                        ddFunction(node.parentNode, node, data.selections);   
                        break;   
                    case 'below':   
                        // 插入到node之下，目标结点为node的parentNode，子结点为node的nextSibling   
                        ddFunction(node.parentNode, node.nextSibling, data.selections);
                        break;   
                }   
            }    
        },
        //当节点移动时触发事件
        movenode: function(tree, node, oldParent, newParent, index) {
				PanelManager.ajaxMoveNode(node.id, oldParent.id, newParent.id, index);
        },		
		// 当节点删除时触发事件
		remove:  function(tree, parentNode, node) {
				if (this.removeFlag) {
				   PanelManager.ajaxRemoveNode(node.id);
				}
		},
		contextmenu: function(node, e){
		            var menu;
		            if (!menu){
		                menu = new Ext.menu.Menu([{
		                    text : '删除结点',
		                    handler : function(){
		                       //删除结点信息
		                       Ext.MessageBox.confirm("删除提示","是否真的要删除数据？",function(ret){
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
				alert(node.id+"选中");
				PanelManager.ajaxIsDisplayNode(nodeId, 1);
			}else{
				alert(node.id+"不选");
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

