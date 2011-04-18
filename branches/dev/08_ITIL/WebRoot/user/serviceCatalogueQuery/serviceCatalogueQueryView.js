PagePanel = Ext.extend(Ext.Panel, {
	id : "PageTemplates",
	//layout : 'table',
	//height : 'auto',
	//align : 'center',
	foredit : true,
	width : 790,
	frame : true,
	autoScroll : true,
	defaults : {
				//autoHeight : true,
				forceFit : true,
				bodyStyle : 'padding:2px'
			},
	layoutConfig : {
		columns : 1
	},
	getTreePanel : function() {
		var sm = new Ext.grid.CheckboxSelectionModel();
		var url = webContext
				+ '/sciRelationShip_listRelationShips.action?serviceCatalogueId='
				+ this.dataId;
		var conn = Ext.lib.Ajax.getConnectionObject().conn;
		conn.open("post", url, false);
		conn.send(null);
		var data = "";
		if (conn.status == "200") {
			// alert(conn.responseText);
			var responseText = conn.responseText;
			if (responseText == '' || responseText == null) {
				Ext.MessageBox.alert("提示信息：", "数据库数据为空");
				data = "";

			} else {
				responseText = clearReturn(responseText);
				data = eval("(" + responseText + ")");
			}
		}
		//alert(Ext.encode(data));
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
			width : 775,
			height : 520,
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
			//autoExpandColumn : 'Name',
			title : "服务目录关系图   <font color=red><提示:红色字体的服务项或服务目录表示不可用的></font>",
			root_title : "<font color=black>服务目录</font>"
		});
		this.store.on('load', function() {
			//Ext.getCmp('tree').expand(true);
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
	getButtons : function() {
		return [{
			xtype : 'button',
			style : 'margin:4px 10px 4px 0',
			text : '<font color=purple>查看</font>',
			iconCls : 'back',
			scope : this,
			id : 'postButton',
			handler : function() {

				// window.location=
				// webContext+"/user/service/sciRelationShipForm.jsp?dataId="+this.dataId;
				window.location = webContext
						+ "/sciRelationShip_getRootRelationShipData.action?rootCataId="
						+ this.dataId + "&type=query";
			}
		}, {
			xtype : 'button',
			style : 'margin:4px 10px 4px 0',
			iconCls : 'back',
			handler : function() {
				history.back();
			},
			text : '<font color=purple>返回</font>'
		}]
	},
	items : this.items,
	/*
	 * clientvalidation 初始化
	 */
	initComponent : function() {
		var items = new Array();
		this.getTreePanel();
		this.mybuttons = this.getButtons();
		this.buttons = {
			layout : 'table',
			height : 'auto',
			width : 'auto',
			style : 'margin:4px 6px 4px 300px',
			// colspan: 4,
			align : 'center',
			defaults : {
				bodyStyle : 'padding:4px'
			},
			layoutConfig : {
				columns : 4
			},
			items : this.mybuttons
		}
		items.push(this.treePanel);
		// items.push(this.buttons);
		this.items = items;
		PagePanel.superclass.initComponent.call(this);
	}
})