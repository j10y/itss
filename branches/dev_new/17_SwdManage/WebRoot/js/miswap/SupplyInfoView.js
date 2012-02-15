SupplyInfoView = Ext.extend(Ext.Panel, {
	constructor : function(a) {
		if(a == null) {
			a = {};
		}
		Ext.apply(this, a);
		this.initComponents();
		SupplyInfoView.superclass.constructor.call(this, {
			id : "SupplyInfoView",
			title : "供应商管理",
			region : "center",
			layout : "border",
			items : [
				this.searchPanel,
				this.gridPanel
			]
		});
	},
	searchPanel : null,
	gridPanel : null,
	store : null,
	topbar : null,
	initComponents : function() {
		this.searchPanel = new Ext.FormPanel({
			region : "north",
			height : 40,
			frame : false,
			border : false,
			layout : "hbox",
			layoutConfig : {
				padding : "5",
				align : "middle"
			},
			defaults : {
				xtype : "label",
				margins : {
					top : 0,
					right : 4,
					bottom : 4,
					left : 4
				}
			},
			items : [
				{
					text : "查询条件：供应商编码"
				}, {
					fieldLabel : "供应商编码",
					name : "Q_supplyId_S_LK",
					xtype : "textfield"
				}, {
					text : "供应商名称"
				}, {
					fieldLabel : "供应商名称",
					name : "Q_companyName_S_LK",
					xtype : "textfield"
				}, {
					xtype : "button",
					text : "查询",
					handler : this.search.createCallback(this)
				}
			]
		});
		this.store = new Ext.data.JsonStore({
			url : __ctxPath + "/danpin/listSupplyInfo.do",
			totalProperty : "totalCounts",
			id : "sid",
			root : "result",
			remoteSort : true,
			fields : [
				{
					name : "sid",
					type : "int"
				},
				"supplyId",
				"companyName"
			]
		});
		this.store.setDefaultSort("sid", "desc");
		this.store.load({
			params : {
				start : 0,
				limit : 25
			}
		});
		var b = new Array();
		if(isGranted("_SupplyInfoSendConfig")) {
			b.push({
				iconCls : "btn-setting",
				qtip : "发送配置",
				style : "margin:0 3px 0 3px"
			});
		}
		if(isGranted("_SupplyLinkerManage")) {
			b.push({
				iconCls : "menu-appuser",
				qtip : "联系人管理",
				style : "margin:0 3px 0 3px"
			});
		}
		this.rowActions = new Ext.ux.grid.RowActions({
			header : "管理",
			width : 80,
			actions : b
		});
		var c = new Ext.grid.CheckboxSelectionModel();
		var a = new Ext.grid.ColumnModel({
			columns : [
				c,
				new Ext.grid.RowNumberer(),
				{
					header : "sid",
					dataIndex : "sid",
					hidden : true
				}, {
					header : "供应商编码",
					dataIndex : "supplyId"
				}, {
					header : "供应商名称",
					dataIndex : "companyName"
				},
				this.rowActions
			],
			defaults : {
				sortable : true,
				menuDisable : false,
				width : 100
			}
		});
		this.gridPanel = new Ext.grid.GridPanel({
			id : "SupplyInfoGrid",
			region : "center",
			autoWidth : true,
			autoHeight : true,
			stripeRows : true,
			closeable : true,
			store : this.store,
			trackMouseOver : true,
			disableSelection : false,
			loadMask : true,
			cm : a,
			sm : c,
			plugins : this.rowActions,
			viewConfig : {
				forceFit : true,
				enableRowBody : false,
				showPreview : false
			},
			bbar : new Ext.PagingToolbar({
				pageSize : 25,
				store : this.store,
				displayInfo : true,
				displayMsg : "当前显示{0}至{1}，共{2}条记录",
				emptyMsg : "当前没有记录"
			})
		});
		this.rowActions.on("action", this.onRowAction, this);
	},
	search : function(a) {
		if(a.searchPanel.getForm().isValid()) {
			a.searchPanel.getForm().submit({
				waitMsg : "正在提交查询……",
				url : __ctxPath + "/danpin/listSupplyInfo.do",
				success : function(c, d) {
					var e = Ext.util.JSON.decode(d.response.responseText);
					a.gridPanel.getStore().loadData(e);
				}
			});
		}
	},
	manageLinker : function(a) {
		new SupplyLinkerView({
			supplyInfoSid : a.data.sid
		}).show();
	},
	configSend : function(a) {
		new SupplyConfigForm({
			supplyInfoSid : a.data.sid
		}).show();
	},
	onRowAction : function(c, a, d, e, b) {
		switch(d) {
			case "menu-appuser":
				this.manageLinker(a);
				break ;
			case "btn-setting":
				this.configSend(a);
				break ;
			default:
				break ;
		}
	}
});