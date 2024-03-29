var NewProcess = function() {
	return this.getView();
};
NewProcess.prototype.getView = function() {
	var d;
	var b = new ProDefinitionView(false);
	var c = new Ext.tree.TreePanel({
		region : "west",
		title : "流程分类",
		collapsible : true,
		split : true,
		width : 150,
		height : 900,
		tbar : new Ext.Toolbar({
			items : [ {
				xtype : "button",
				iconCls : "btn-refresh",
				text : "刷新",
				handler : function() {
					c.root.reload();
				}
			} ]
		}),
		loader : new Ext.tree.TreeLoader({
			url : __ctxPath + "/flow/rootProType.do"
		}),
		root : new Ext.tree.AsyncTreeNode({
			expanded : true
		}),
		rootVisible : false,
		listeners : {
			"click" : function(f) {
				b.setTypeId(f.id);
				var e = Ext.getCmp("ProDefinitionGrid0");
				e.getStore().proxy.conn.url = __ctxPath
						+ "/flow/listProDefinition.do?typeId=" + f.id;
				e.getStore().load({
					params : {
						start : 0,
						limit : 25
					}
				});
			}
		}
	});
	var a = new Ext.Panel({
		title : "新建流程",
		layout : "border",
		iconCls : "menu-flowNew",
		id : "NewProcess",
		height : 800,
		items : [ c, b.getView() ]
	});
	return a;
};