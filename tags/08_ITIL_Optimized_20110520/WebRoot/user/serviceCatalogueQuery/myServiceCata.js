MyServiceCataPanel = Ext.extend(Ext.Panel, {
	id : "myServiceCataPanel",
	foredit : true,
	width : 'auto',
	frame : true,
	autoScroll : false,
	defaults : {
				forceFit : true,
				bodyStyle : 'padding:2px'
			},
	layoutConfig : {
		columns : 1
	},
	getTreePanel : function() {
		var sm = new Ext.grid.CheckboxSelectionModel();
		var url = webContext + '/sciRelationShip_listMyServiceCata.action';
		var conn = Ext.lib.Ajax.getConnectionObject().conn;
		conn.open("post", url, false);
		conn.send(null);
		var data = "";
		if (conn.status == "200") {
			var responseText = conn.responseText;
			if (responseText == '' || responseText == null) {
				Ext.MessageBox.alert("提示信息：", "数据库数据为空");
				data = "";

			} else {
				responseText = clearReturn(responseText);
				data = eval("(" + responseText + ")");
			}
		}
		var record = Ext.data.Record.create([{
			name : 'id'
		}, {
			name : 'name'
		}, {
			name : 'typeFlag'
		}, {
			name : 'rootName'
		}, {
			name : '_parent'
		}, {
			name : '_level'
		}, {
			name : '_lft'
		}, {
			name : '_rgt'
		},{
			name : '_is_leaf'
		}]);
		this.store = new Ext.ux.maximgb.treegrid.NestedSetStore({
			autoLoad : true,
			reader : new Ext.data.JsonReader({
				id : '_id'
			}, record),
			proxy : new Ext.data.MemoryProxy(data)
		});
		this.treePanel = new Ext.ux.maximgb.treegrid.GridPanel({
			store : this.store,
			id : "tree",
			width : 850,
			height : 500,
			frame : true,
			master_column_id : 'Name',
			defaults : {
				bodyStyle : 'padding:4px',
				forceFit:true
			},
			columns : [{
				id : 'Name',
				header : "<font color=green>服务目录名称</font>",
				sortable : true,
				width : 400,
				renderer : change,
				dataIndex : 'name'
			}, {
				header : "<font color=green>类型</font>",
				width : 200,
				sortable : true,
				renderer : pctChange,
				dataIndex : 'typeFlag'
			}, {
				header : "<font color=green>根目录</font>",
				width : 200,
				renderer : pctChange,
				sortable : true,
				dataIndex : 'rootName'
			}, {
				header : "id",
				width : 100,
				hidden : true,
				sortable : true,
				dataIndex : 'id'
			}],
			sm : sm,

			stripeRows : true,
			root_title : "<font color=black>服务目录</font>"
		});
		this.store.on('load', function() {
			Ext.getCmp('tree').expandAllNodes();
		});
		function change(val) {
			val = '<span style="color:black;">' + val + '</span>';
			return val;
		}
		function pctChange(val) {
			val = '<span style="color:black;">' + val + '</span>';
			return val;
		}
		return this.treePanel;
	},
	items : this.items,
	/*
	 * clientvalidation 初始化
	 */
	initComponent : function() {
		var items = new Array();
		this.treePanel = this.getTreePanel();
		items.push(this.treePanel);
		this.items = items;
		MyServiceCataPanel.superclass.initComponent.call(this);
	}
})