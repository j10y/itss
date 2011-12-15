DXCUnScoreManage = Ext.extend(Ext.Panel, {
	constructor : function (a) {
		if (a == null) {
			a = {};
		}
		Ext.apply(this, a);
		this.initComponents();
		DXCUnScoreManage.superclass.constructor.call(this, {
			id : "DXCUnScoreManage",
			title : "不可评分待选池考核",
			region : "center",
			iconCls : "menu-personal-salary",
			layout : "border",
			items : [this.searchPanel, this.gridPanel]
		});
	},
	typeId : null,
	searchPanel : null,
	gridPanel : null,
	store : null,
	topbar : null,
	initComponents : function () {
		this.searchPanel = new Ext.FormPanel({
				region : "north",
				frame : false,
				border : false,
				layout : "hbox",
				height : 40,
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
				items : [{
						text : "请输入查询条件:"
					}, {
						text : "品牌"
					}, {
						id : "UnScoreManageSearchFormBandId",
						width : 120,
						name : "bandName",
						maxHeight : 200,
						xtype : "combo",
						mode : "local",
						editable : true,
						triggerAction : "all",
						valueField : "DXCBandId",
						displayField : "DXCBandName",
						store : new Ext.data.SimpleStore(
						{
							url : __ctxPath
									+ "/bandpoor/getBandsScoreManage.do",
							fields : [
									"DXCBandId",
									"DXCBandName"]
						}),
						listeners : {
							focus : function (e) {
								var d = Ext.getCmp("UnScoreManageSearchFormBandId").getStore();
								if (d.getCount() <= 0) {
									Ext.Ajax.request({
										url : __ctxPath + "/bandpoor/getBandsScoreManage.do",
										method : "post",
										success : function (g) {
											var f = Ext.util.JSON.decode(g.responseText);
											d.loadData(f);
										}
									});
								}
							}
						}
					}, {
						xtype : "button",
						text : "查询",
						iconCls : "search",
						handler : this.search.createCallback(this)
					}
				]
			});
		this.store = new Ext.data.JsonStore({
				url : __ctxPath + "/bandpoor/listDxcUnScore.do",
				root : "result",
				baseParams : {
				},
				totalProperty : "totalCounts",
				remoteSort : false,
				fields : [
					{
						name : "id",
						type : "int"
					}, 
					"bandName",
					"targetValue",
					"requireValue",
					"bandRankValue",
					"selBandRankValue",
					"status",
					"createDate",
					"createUser",
					"content"
				]
			});
		this.store.setDefaultSort("id", "desc");
		this.store.load({
			params : {
				start : 0,
				limit : 25
			}
		});
		/*
		var b = new Array();
		if(isGranted("_DXCUnScoreManageCheck")) {
			b.push({
				iconCls : "btn-check",
				qtip : "考核",
				style : "margin:0 3px 0 3px"
			});
		}
		if(isGranted("_DXCUnScoreManageDel")) {
			b.push({
				iconCls : "btn-del",
				qtip : "删除",
				style : "margin:0 3px 0 3px"
			});
		}
		this.rowActions = new Ext.ux.grid.RowActions({
			header : "管理",
			width : 80,
			actions : b
		});
		*/
		var d = new Ext.ux.grid.RowExpander({
				tpl : new Ext.Template('<div style="padding:5px 5px 5px 62px;">{content}</div>')
			});
		var c = new Ext.grid.CheckboxSelectionModel();
		var a = new Ext.grid.ColumnModel({
				columns : [c, new Ext.grid.RowNumberer(), d, {
						header : "id",
						dataIndex : "id",
						hidden : true
					}, {
						header : "品牌名称",
						width : 60,
						dataIndex : "bandName"
					}, {
						header : "品类评效",
						width : 60,
						dataIndex : "targetValue"
					}, {
						header : "本品牌评效",
						width : 60,
						dataIndex : "requireValue"
					}, {
						header : "品类排名",
						width : 60,
						dataIndex : "bandRankValue"
					}, {
						header : "本品牌排名",
						width : 60,
						dataIndex : "selBandRankValue"
					}, {
						header : "状态",
						width : 60,
						dataIndex : "status",
						renderer : function (e) {
							if(e == "1") {
								return "新建";
							}
							if(e == "2") {
								return "达成通过";
							}
							if(e == "3") {
								return "未达成";
							}
						}
					}, {
						header : "品牌类型",
						width : 60,
						dataIndex : "infoType",
						renderer : function (e) {
							return "不可评分";
						}
					}, {
						header : "创建日期",
						width : 60,
						dataIndex : "createDate"
					}, {
						header : "创建人",
						width : 60,
						dataIndex : "createUser"
					}
				],
				defaults : {
					sortable : true,
					menuDisabled : false,
					width : 100
				}
			});
		this.dxctopbar = new Ext.Toolbar({
				id : "DXCUnScoreManageFootBar",
				height : 30,
				bodyStyle : "text-align:left",
				items : []
			});
		if (isGranted("_DXCUnScoreManageCheck")) {
			this.dxctopbar.add(new Ext.Button({
					iconCls : "btn-setting",
					text : "考核",
					handler : this.check,
					scope : this
				}));
		}
		if (isGranted("_DXCUnScoreManageDel")) {
			this.dxctopbar.add(new Ext.Button({
					iconCls : "btn-del",
					text : "删除",
					handler : this.delRecords,
					scope : this
				}));
		}		
		this.gridPanel = new Ext.grid.GridPanel({
				id : "DXCUnScoreManageGrid",
				region : "center",
				stripeRows : true,
				plugins : d,
				tbar : this.dxctopbar,
				store : this.store,
				trackMouseOver : true,
				disableSelection : false,
				loadMask : true,
				autoHeight : true,
				cm : a,
				sm : c,
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
		//this.rowActions.on("action", this.onRowAction, this);
	},
	search : function (a) {
		if (a.searchPanel.getForm().isValid()) {
			a.searchPanel.getForm().submit({
				waitMsg : "正在提交查询",
				url : __ctxPath + "/bandpoor/listDxcUnScore.do",
				success : function (c, d) {
					var b = Ext.util.JSON.decode(d.response.responseText);
					a.gridPanel.getStore().loadData(b);
				}
			});
		}
	},
	check : function () {
			var c = Ext.getCmp("DXCUnScoreManageGrid");
			var a = c.getSelectionModel().getSelections();
			if (a.length == 0) {
				Ext.ux.Toast.msg("信息", "请选择要考核的记录！");
				return;
			}
			if(a.length > 1) {
				Ext.ux.Toast.msg("信息", "一次只能选择一条记录！");
				return;
			}
			if(a[0].data.status != "1") {
				Ext.ux.Toast.msg("信息", "不是新建状态的记录不能被考核！");
				return;
			}
			var formPanel = new Ext.FormPanel({
				layout : "form",
				bodyStyle : "padding:10px 10px 10px 10px",
				border : false,
				id : "bandUnScoreValueForm",
				defaults : {
					anchor : "98%,98%"
				},
				defaultType : "numberfield",
				items : [
					{
						fieldLabel : "品类评效",
						name : "targetValue",
						id : "targetValue",
						value : a[0].data.targetValue
					}, {
						fieldLabel : "本品牌评效",
						name : "requireValue",
						id : "requireValue",
						value : a[0].data.requireValue
					}, {
						fieldLabel : "品类排名",
						name : "bandRankValue",
						id : "bandRankValue",
						value : a[0].data.bandRankValue
					}, {
						fieldLabel : "本品牌排名",
						name : "selBandRankValue",
						id : "selBandRankValue",
						value : a[0].data.selBandRankValue
					}
				]
			});
			var win = new Ext.Window({
				id : "checkUnScoreWin",
				title : "考核",
				width : "300",
				height : "200",
				layout : "fit",
				modal : true,
				maximizable : true,
				items : formPanel,
				buttonAlign : "center",
				buttons : [
					{
						text : "保存",
						iconCls : "btn-save",
						handler : function() {
							Ext.getCmp("bandUnScoreValueForm").getForm().submit({
								waitMsg : "正在提交数据...",
								url : __ctxPath + "/bandpoor/checkDxcUnScore.do?method=save&id=" + a[0].data.id,
								success : function() {
									Ext.getCmp("checkUnScoreWin").close();
									Ext.getCmp("DXCUnScoreManageGrid").getStore().reload();
									Ext.ux.Toast.msg("提示信息", "保存成功！");
								},
								failure : function() {
									Ext.MessageBox.show({
										title : "操作信息",
										msg : "保存出错，请联系管理员！",
										buttons : Ext.MessageBox.OK,
										icon : Ext.MessageBox.ERROR
									});
								}
							});
						}
					}, {
						text : "考核",
						iconCls : "btn-check",
						handler : function() {
							if(Ext.getCmp("targetValue").getValue() == "") {
								Ext.MessageBox.show({
									title : "操作信息",
									msg : "品类评效不允许为空！",
									buttons : Ext.MessageBox.OK,
									icon : Ext.MessageBox.ERROR
								});
								return ;
							}
							if(Ext.getCmp("requireValue").getValue() == "") {
								Ext.MessageBox.show({
									title : "操作信息",
									msg : "本品牌评效不允许为空！",
									buttons : Ext.MessageBox.OK,
									icon : Ext.MessageBox.ERROR
								});
								return ;
							}
							if(Ext.getCmp("bandRankValue").getValue() == "") {
								Ext.MessageBox.show({
									title : "操作信息",
									msg : "品类排名不允许为空！",
									buttons : Ext.MessageBox.OK,
									icon : Ext.MessageBox.ERROR
								});
								return ;
							}
							if(Ext.getCmp("selBandRankValue").getValue() == "") {
								Ext.MessageBox.show({
									title : "操作信息",
									msg : "本品牌排名不允许为空！",
									buttons : Ext.MessageBox.OK,
									icon : Ext.MessageBox.ERROR
								});
								return ;
							}
							Ext.getCmp("bandUnScoreValueForm").getForm().submit({
								waitMsg : "正在提交数据...",
								url : __ctxPath + "/bandpoor/checkDxcUnScore.do?method=check&id=" + a[0].data.id,
								success : function() {
									Ext.getCmp("checkUnScoreWin").close();
									Ext.getCmp("DXCUnScoreManageGrid").getStore().reload();
									Ext.ux.Toast.msg("提示信息", "保存成功！");
								},
								failure : function() {
									Ext.MessageBox.show({
										title : "操作信息",
										msg : "保存出错，请联系管理员！",
										buttons : Ext.MessageBox.OK,
										icon : Ext.MessageBox.ERROR
									});
								}
							});
						}
					}
				]
			}).show();
	},
	delRecords : function () {
		var c = Ext.getCmp("DXCUnScoreManageGrid");
		var a = c.getSelectionModel().getSelections();
		if (a.length == 0) {
			Ext.ux.Toast.msg("信息", "请选择要删除的记录！");
			return;
		}
		var d = Array();
		for (var b = 0; b < a.length; b++) {
			d.push(a[b].data.id);
		}
		this.delByIds(d);
	},
	delByIds : function (a) {
		Ext.Msg.confirm("信息确认", "您确认要删除所选记录吗？", function (b) {
			if (b == "yes") {
				Ext.Ajax.request({
					url : __ctxPath + "/bandpoor/multiDelDxcUnScore.do",
					params : {
						ids : a
					},
					method : "POST",
					success : function (c, d) {
						Ext.ux.Toast.msg("信息提示", "成功删除所选记录！");
						Ext.getCmp("DXCUnScoreManageGrid").getStore().reload();
					},
					failure : function (c, d) {
						Ext.ux.Toast.msg("操作信息", "操作出错，请联系管理员！");
					}
				});
			}
		});
	},
	onRowAction : function(c, a, d, e, b) {
		switch(d) {
			case "btn-del":
				break ;
			case "btn-check":
				this.check(a);
				break ;
			default:
				break ;
		}
	}
});
