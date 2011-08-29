RewardsPunishmentsTypeView = Ext.extend(Ext.Panel, {
	searchPanel : null,
	gridPanel : null,
	store : null,
	topbar : null,
	constructor : function(a) {
		Ext.applyIf(this, a);
		this.initUIComponents();
		RewardsPunishmentsTypeView.superclass.constructor.call(this, {
			id : "RewardsPunishmentsTypeView",
			title : "奖惩分类管理",
			iconCls : "menu-rewardsPunishmentsType",
			region : "center",
			layout : "border",
			items : [ this.searchPanel, this.gridPanel ]
		});
	},
	initUIComponents : function() {
		this.searchPanel = new Ext.FormPanel({
			height : 40,
			region : "north",
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
			items : [ {
				text : "分类名称"
			}, {
				name : "Q_typeName_S_LK",
				xtype : "textfield",
				width : 100
			}, {
				xtype : "button",
				text : "查询",
				iconCls : "search",
				handler : this.search.createCallback(this)
			} ]
		});
		this.store = new Ext.data.JsonStore({
			url : __ctxPath + "/rewardsPunishments/listRewardsPunishmentsType.do?Q_deleteFlag_N_EQ=0",
			root : "result",
			totalProperty : "totalCounts",
			remoteSort : true,
			fields : [ {
				name : "typeId",
				type : "int"
			}, "typeName"
			, "operation"
			, "typeDesc"]
		});
		this.store.setDefaultSort("typeId", "desc");
		this.store.load({
			params : {
				start : 0,
				limit : 25
			}
		});
		var b = new Array();
		if (isGranted("_RewardsPunishmentsTypeDel")) {
			b.push({
				iconCls : "btn-del",
				qtip : "删除",
				style : "margin:0 3px 0 3px"
			});
		}
		if (isGranted("_RewardsPunishmentsTypeEdit")) {
			b.push({
				iconCls : "btn-edit",
				qtip : "编辑",
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
			columns : [ c, new Ext.grid.RowNumberer(), {
				header : "rpTypeId",
				dataIndex : "typeId",
				hidden : true
			}, {
				header : "分类名称",
				dataIndex : "typeName"
			}, {
				header : "符号",
				dataIndex : "operation",
				renderer : function(value){
					return value=="0" ? "-" : "+";
				}
			}, {
				header : "描述",
				dataIndex : "typeDesc"
			}, this.rowActions ],
			defaults : {
				sortable : true,
				menuDisabled : false,
				width : 100
			}
		});
		this.topbar = new Ext.Toolbar({
			height : 30,
			bodyStyle : "text-align:left",
			items : []
		});
		if (isGranted("_RewardsPunishmentsTypeAdd")) {
			this.topbar.add({
				iconCls : "btn-add",
				text : "添加分类",
				xtype : "button",
				handler : this.createRecord
			});
		}
		if (isGranted("_RewardsPunishmentsTypeDel")) {
			this.topbar.add({
				iconCls : "btn-del",
				text : "删除分类",
				xtype : "button",
				handler : this.delRecords,
				scope : this
			});
		}
		this.gridPanel = new Ext.grid.GridPanel({
			id : "RewardsPunishmentsTypeGrid",
			region : "center",
			stripeRows : true,
			tbar : this.topbar,
			store : this.store,
			trackMouseOver : true,
			disableSelection : false,
			loadMask : true,
			autoHeight : true,
			cm : a,
			sm : c,
			plugins : this.rowActions,
			viewConfig : {
				forceFit : true,
				autoFill : true,
				forceFit : true
			},
			bbar : new Ext.PagingToolbar({
				pageSize : 25,
				store : this.store,
				displayInfo : true,
				displayMsg : "当前页记录索引{0}-{1}， 共{2}条记录",
				emptyMsg : "当前没有记录"
			})
		});
		this.gridPanel.addListener("rowdblclick", function(f, d, g) {
			f.getSelectionModel().each(function(e) {
				new RewardsPunishmentsTypeForm({
					recTypeId : e.data.recTypeId
				}).show();
			});
		});
		this.rowActions.on("action", this.onRowAction, this);
	},
	search : function(a) {
		if (a.searchPanel.getForm().isValid()) {
			a.searchPanel.getForm().submit({
				waitMsg : "正在提交查询",
				url : __ctxPath + "/rewardsPunishments/listRewardsPunishmentsType.do?Q_deleteFlag_N_EQ=0",
				success : function(c, d) {
					var b = Ext.util.JSON.decode(d.response.responseText);
					a.gridPanel.getStore().loadData(b);
				}
			});
		}
	},
	createRecord : function() {
		new RewardsPunishmentsTypeForm().show();
	},
	delByIds : function(a) {
		Ext.Msg.confirm("信息确认", "您确认要删除所选记录吗？", function(b) {
			if (b == "yes") {
				Ext.Ajax.request({
					url : __ctxPath + "/rewardsPunishments/multiDelRewardsPunishmentsType.do",
					params : {
						ids : a
					},
					method : "POST",
					success : function(c, d) {
						Ext.ux.Toast.msg("操作信息", "成功删除该！");
						Ext.getCmp("RewardsPunishmentsTypeGrid").getStore().reload();
					},
					failure : function(c, d) {
						Ext.ux.Toast.msg("操作信息", "操作出错，请联系管理员！");
					}
				});
			}
		});
	},
	delRecords : function() {
		var c = Ext.getCmp("RewardsPunishmentsTypeGrid");
		var a = c.getSelectionModel().getSelections();
		if (a.length == 0) {
			Ext.ux.Toast.msg("信息", "请选择要删除的记录！");
			return;
		}
		var d = Array();
		for ( var b = 0; b < a.length; b++) {
			d.push(a[b].data.typeId);
		}
		this.delByIds(d);
	},
	editRecord : function(a) {
		new RewardsPunishmentsTypeForm({
			rpTypeId : a.data.typeId
		}).show();
	},
	onRowAction : function(c, a, d, e, b) {
		switch (d) {
		case "btn-del":
			this.delByIds(a.data.typeId);
			break;
		case "btn-edit":
			this.editRecord(a);
			break;
		default:
			break;
		}
	}
});