RewardsPunishmentsView = Ext.extend(Ext.Panel, {
	searchPanel : null,
	gridPanel : null,
	store : null,
	topbar : null,
	constructor : function(a) {
		Ext.applyIf(this, a);
		this.initUIComponents();
		RewardsPunishmentsView.superclass.constructor.call(this, {
			id : "RewardsPunishmentsView",
			title : "人员奖惩",
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
				text : "奖惩备注"
			}, {
				name : "Q_remark_S_LK",
				xtype : "textfield",
				width : 100
			}, {
				text : "奖惩类型"
			}, {
				/*fieldLabel : "奖惩类型",
				name : "Q_rpType.itemValue_S_LK",
				id : "rpType",
				maxHeight : 200,
				xtype : "combo",
				mode : "local",
				editable : true,
				triggerAction : "all",
				store : [],
				listeners : {
					focus : function(d) {
						var c = Ext.getCmp("rpType").getStore();
						if (c.getCount() <= 0) {
							Ext.Ajax.request({
								url : __ctxPath
										+ "/system/loadDictionary.do",
								method : "post",
								params : {
									itemName : "奖惩类型"
								},
								success : function(
										f) {
									var e = Ext.util.JSON.decode(f.responseText);
									c.loadData(e);
								}
							});
						}
					}
				}*/

				fieldLabel : "奖惩类型",
				name : "Q_rpType.typeName_S_LK",
				id : "rpType",
				maxHeight : 200,
				xtype : "combo",
				mode : "remote",
				editable : true,
				triggerAction : "all",
				displayField: "typeName",
				valueField: "typeName",
				store : new Ext.data.SimpleStore({
					url : __ctxPath + "/rewardsPunishments/comboRewardsPunishmentsType.do",
					fields : ["typeId", "typeName"]
				})
			
			}, {
				xtype : "button",
				text : "查询",
				iconCls : "search",
				handler : this.search.createCallback(this)
			} ]
		});
		this.store = new Ext.data.JsonStore(
				{
					url : __ctxPath + "/rewardsPunishments/listRewardsPunishments.do",
					root : "result",
					totalProperty : "totalCounts",
					remoteSort : true,
					fields : [ "rpId",
					           {name : "amount", mapping : "amount"}, 
					           {name : "rpType", mapping : "rpType.typeName"},
					           {name : "remark", mapping : "remark"}, 
					           {name : "appUser", mapping : "appUser.fullname"}, 
					           {name : "profileNo", mapping : "empProfile.profileNo"}, 
					           {name : "createPerson", mapping : "createPerson.fullname"}, 
					           {name : "createDate", mapping : "createDate"}]
				});
		this.store.setDefaultSort("rpId", "desc");
		this.store.load({
			params : {
				start : 0,
				limit : 25
			}
		});
		this.rowActions = new Ext.ux.grid.RowActions({
			header : "管理",
			width : 80,
			actions : [ {
				iconCls : "btn-del",
				qtip : "删除",
				style : "margin:0 3px 0 3px"
			}, {
				iconCls : "btn-edit",
				qtip : "编辑",
				style : "margin:0 3px 0 3px"
			} ]
		});
		var b = new Ext.grid.CheckboxSelectionModel();
		var a = new Ext.grid.ColumnModel({
			columns : [ b, new Ext.grid.RowNumberer(), {
				header : "rpId",
				dataIndex : "rpId",
				hidden : true
			}, {
				header : "受奖惩人",
				dataIndex : "appUser"
			},  {
				header : "受奖惩人档案编号",
				dataIndex : "profileNo"
			}, {
				header : "奖惩类型",
				dataIndex : "rpType"
			}, {
				header : "金额",
				dataIndex : "amount"
			}, {
				header : "备注",
				dataIndex : "remark"
			}, {
				header : "创建人",
				dataIndex : "createPerson"
			}, {
				header : "创建日期",
				dataIndex : "createDate"
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
			items : [ {
				iconCls : "btn-add",
				text : "添加",
				xtype : "button",
				handler : this.createRecord
			}, {
				iconCls : "btn-del",
				text : "删除",
				xtype : "button",
				handler : this.delRecords,
				scope : this
			} ]
		});
		this.gridPanel = new Ext.grid.GridPanel({
			id : "RewardsPunishmentsGrid",
			region : "center",
			stripeRows : true,
			tbar : this.topbar,
			store : this.store,
			trackMouseOver : true,
			disableSelection : false,
			loadMask : true,
			autoHeight : true,
			cm : a,
			sm : b,
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
		this.gridPanel.addListener("rowdblclick", function(d, c, f) {
			d.getSelectionModel().each(function(e) {
				new RewardsPunishmentsForm(e.data.rpId).show();
			});
		});
		this.rowActions.on("action", this.onRowAction, this);
	},
	search : function(a) {
		if (a.searchPanel.getForm().isValid()) {
			a.searchPanel.getForm().submit({
				waitMsg : "正在提交查询",
				url : __ctxPath + "/rewardsPunishments/listRewardsPunishments.do",
				success : function(c, d) {
					var b = Ext.util.JSON.decode(d.response.responseText);
					a.gridPanel.getStore().loadData(b);
				}
			});
		}
	},
	createRecord : function() {
		new RewardsPunishmentsForm().show();
	},
	delByIds : function(a) {
		Ext.Msg.confirm("信息确认", "您确认要删除所选记录吗？", function(b) {
			if (b == "yes") {
				Ext.Ajax.request({
					url : __ctxPath + "/rewardsPunishments/multiDelRewardsPunishments.do",
					params : {
						ids : a
					},
					method : "POST",
					success : function(c, d) {
						Ext.ux.Toast.msg("操作信息", "成功删除该记录！");
						Ext.getCmp("RewardsPunishmentsGrid").getStore().reload();
					},
					failure : function(c, d) {
						Ext.ux.Toast.msg("操作信息", "操作出错，请联系管理员！");
					}
				});
			}
		});
	},
	delRecords : function() {
		var c = Ext.getCmp("RewardsPunishmentsGrid");
		var a = c.getSelectionModel().getSelections();
		if (a.length == 0) {
			Ext.ux.Toast.msg("信息", "请选择要删除的记录！");
			return;
		}
		var d = Array();
		for ( var b = 0; b < a.length; b++) {
			d.push(a[b].data.rpId);
		}
		this.delByIds(d);
	},
	editRecord : function(a) {
		new RewardsPunishmentsForm({
			rpId : a.data.rpId
		}).show();
	},
	onRowAction : function(c, a, d, e, b) {
		switch (d) {
		case "btn-del":
			this.delByIds(a.data.rpId);
			break;
		case "btn-edit":
			this.editRecord(a);
			break;
		default:
			break;
		}
	}
});