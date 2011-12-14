BandStyleView = Ext.extend(Ext.Panel, {
	constructor : function(a) {
		if(a == null) {
			a = {};
		}
		Ext.apply(this, a);
		this.initComponents();
		BandStyleView.superclass.constructor.call(this, {
			id : "BandStyleView",
			title : "品牌风格列表",
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
					text : "查询条件：名称"
				}, {
					fieldLabel : "名称",
					name : "Q_styleName_S_LK",
					xtype : "textfield"
				}, {
					xtype : "button",
					text : "查询",
					handler : this.search.createCallback(this)
				}
			]
		});
		this.store = new Ext.data.JsonStore({
			url : __ctxPath + "/bandpoor/listBandStyle.do?Q_flag_N_EQ=1",
			totalProperty : "totalCounts",
			id : "id",
			root : "result",
			remoteSort : true,
			fields : [
				{
					name : "id",
					type : "int"
				},
				"styleNum",
				"styleName",
				"styleDesc",
				{
					name : "proClassName",
					mapping : "proClassId.proClassName"
				}
			]
		});
		this.store.setDefaultSort("id", "desc");
		this.store.load({
			params : {
				start : 0,
				limit : 25
			}
		});
		var b = new Array();
		if(isGranted("_BandStyleEdit")) {
			b.push({
				iconCls : "btn-edit",
				qtip : "编辑",
				style : "margin:0 3px 0 3px"
			});
		}
		if(isGranted("_BandStyleDel")) {
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
		var c = new Ext.grid.CheckboxSelectionModel();
		var a = new Ext.grid.ColumnModel({
			columns : [
				c,
				new Ext.grid.RowNumberer(),
				{
					header : "id",
					dataIndex : "id",
					hidden : true
				}, {
					header : "编号",
					dataIndex : "styleNum"
				}, {
					header : "名称",
					dataIndex : "styleName"
				}, {
					header : "所属品类",
					dataIndex : "proClassName"
				},
				this.rowActions
			],
			defaults : {
				sortable : true,
				menuDisable : false,
				width : 100
			}
		});
		this.topbar = new Ext.Toolbar({
			height : 30,
			bodyStyle : "text-align:left",
			items : []
		});
		this.topbar.add(new Ext.Button({
			iconCls : "btn-add",
			text : "添加品牌风格",
			handler : this.addBandStyle
		}));
		this.topbar.add(new Ext.Button({
			iconCls : "btn-del",
			text : "删除品牌风格",
			handler : this.delBandStyle
		}));
		this.topbar.add(new Ext.Button({
			text : "批量导入品牌风格",
			handler : this.uploadBandStyle
		}));
		this.topbar.add(new Ext.Button({
			text : "下载品牌风格数据模板excel文件",
			handler : function() {
				window.open(__ctxPath + "/userfiles/dataTemplate/bandStyleTemplate.xls");
			}
		}));
		this.gridPanel = new Ext.grid.GridPanel({
			id : "BandStyleGrid",
			region : "center",
			autoWidth : true,
			autoHeight : true,
			stripeRows : true,
			tbar : this.topbar,
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
		this.gridPanel.addListener("rowdblclick", function(f, d, g) {
			f.getSelectionModel().each(function(e) {
				if(isGranted("_BandStyleEdit")) {
					new BandStyleForm({
						bandStyleId : e.data.id
					}).show();
				}
			});
		});
		this.rowActions.on("action", this.onRowAction, this);
	},
	search : function(a) {
		if(a.searchPanel.getForm().isValid()) {
			a.searchPanel.getForm().submit({
				waitMsg : "正在提交查询……",
				url : __ctxPath + "/bandpoor/listBandStyle.do?Q_flag_N_EQ=1",
				success : function(c, d) {
					var e = Ext.util.JSON.decode(d.response.responseText);
					a.gridPanel.getStore().loadData(e);
				}
			});
		}
	},
	addBandStyle : function() {
		new BandStyleForm().show();
	},
	delBandStyle : function() {
		var e = Ext.getCmp("BandStyleGrid");
		var c = e.getSelectionModel().getSelections();
		if(c.length == 0) {
			Ext.ux.Toast.msg("提示信息", "请选择要删除的记录！");
			return ;
		}
		var f = Array();
		for(var d = 0; d < c.length; d++) {
			f.push(c[d].data.id);
		}
		BandStyleView.remove(f);
	},
	editBandStyle : function(a) {
		new BandStyleForm({
			bandStyleId : a.data.id
		}).show();
	},
	uploadBandStyle : function() {
		var a = App.createUploadDialog({
			file_cat : "uploadData",
			callback : function (c) {
				for (var b = 0; b < c.length; b++) {
					Ext.Ajax.request({
						url : __ctxPath + "/bandpoor/uploadBandStyle.do",
						params : {
							filePath : c[b].filepath
						},
						success : function(d) {
							var e = Ext.util.JSON.decode(d.responseText);
							if(e.flag == "0") {
								Ext.MessageBox.show({
									title : "操作信息",
									msg : e.msg,
									buttons : Ext.MessageBox.OK,
									icon : Ext.MessageBox.ERROR
								});
								return ;
							}
							Ext.MessageBox.show({
								title : "操作信息",
								msg : "数据导入成功！",
								buttons : Ext.MessageBox.OK,
								icon : Ext.MessageBox.INFO
							});
							Ext.getCmp("BandStyleView").gridPanel.store.reload({
								params : {
									start : 0,
									limit : 25
								}
							});
						}
					});
				}
			}
		});
		a.show();
	},
	onRowAction : function(c, a, d, e, b) {
		switch(d) {
			case "btn-del":
				this.delBandStyle();
				break ;
			case "btn-edit":
				this.editBandStyle(a);
				break ;
			default:
				break ;
		}
	}
});
BandStyleView.remove = function(b) {
	var a = Ext.getCmp("BandStyleGrid");
	Ext.Msg.confirm("信息确认", "您确认要删除所选记录吗？", function(c) {
		if(c == "yes") {
			Ext.Ajax.request({
				url : __ctxPath + "/bandpoor/multiDelBandStyle.do",
				params : {
					ids : b
				},
				method : "post",
				success : function(d) {
					var e = Ext.util.JSON.decode(d.responseText);
					Ext.ux.Toast.msg("提示信息", "成功删除所选记录！");
					a.getStore().reload({
						params : {
							start : 0,
							limit : 25
						}
					});
				},
				failure : function() {
					Ext.MessageBox.show({
						title : "操作信息",
						msg : "删除失败，请联系管理员！",
						buttons : Ext.MessageBox.OK,
						icon : Ext.MessageBox.ERROR
					});
				}
			});
		}
	});
}
