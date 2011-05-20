PagePanel = Ext.extend(Ext.Panel, {
	id : "PageTemplates",
	layout : 'fit',
	height : 'auto',
	align : 'center',
	foredit : true,
	width : 1050,
	frame : true,
	autoScroll : true,
	defaults : {
		bodyStyle : 'padding:4px'
	},
	viewConfig : {// 自适应填充
		autoFill : true,
		forceFit : true
	},
	layoutConfig : {
		columns : 4
	},
	getTreePanel : function() {
		var loader = new Ext.tree.TreeLoader({
			url :  webContext + '/admin/queryDepartment.do?methodCall=queryAllDepartment'
		});
		var treePanel = new Ext.tree.TreePanel({
			loader : loader,
			id : 'treepanel',
			viewConfig : {// 自适应填充
				autoFill : true,
				forceFit : true
			},
			// border : false,
			width : 560,
			height : 400,
			autoScroll : true,
			root : new Ext.tree.AsyncTreeNode({
				text : '上品折扣(<font color=blue>50000075</font>)',
				id : -1
			})
		});
		loader.on('beforeload', function(treeloader, node) {
			treeloader.baseParams = {
				id : node.id
			};

		}, this);
		//treePanel.expandPath(Ext.data.Node.getPath(2));

		return treePanel;

	},
	items : this.items,
	/*
	 * clientvalidation 初始化
	 */
	initComponent : function() {
		var da = new DataAction();
		this.tree = this.getTreePanel();
		var items = new Array();
		items.push(this.tree);
		this.items = items;
		PagePanel.superclass.initComponent.call(this);
	}
})