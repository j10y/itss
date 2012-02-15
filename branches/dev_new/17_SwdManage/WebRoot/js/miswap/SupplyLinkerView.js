SupplyLinkerView = Ext.extend(Ext.Window, {
	formPanel : null,
	gridPanel : null,
	store : null,
	topbar : null,
	constructor : function(a) {
		if(a == null) {
			a = {};
		}
		Ext.apply(this, a);
		this.initComponents();
		SupplyLinkerView.superclass.constructor.call(this, {
			id : "SupplyLinkerView",
			title : "供应商联系人管理",
			region : "center",
			layout : "border",
			items : [
				this.formPanel,
				this.gridPanel
			],
			modal : true,
			height : 400,
			width : 600,
			buttonAlign : "center",
			buttons : this.buttons
		});
	},
	initComponents : function() {
		this.formPanel = new Ext.FormPanel({
			region : "north",
			height : 90,
			id : "SupplyInfoForm",
			bodyStyle : "padding:20px 20px 20px 20px",
			frame : false,
			border : false,
			layout : "form",
			layoutConfig : {
				padding : "5",
				align : "middle"
			},
			items : [
				{
					name : "supplyInfo.sid",
					id : "sid",
					xtype : "hidden",
					value : this.supplyInfoSid == null ? "" : this.supplyInfoSid
				}, {
					fieldLabel : "供应商编码",
					name : "supplyInfo.supplyId",
					width : 300,
					id : "supplyId",
					xtype : "textfield",
					readOnly : true
				}, {
					fieldLabel : "供应商名称",
					name : "supplyInfo.companyName",
					width : 300,
					id : "companyName",
					xtype : "textfield",
					readOnly : true
				}
			]
		});
		if(this.supplyInfoSid != null && this.supplyInfoSid != "undefined") {
			this.formPanel.getForm().load({
				deferredRender : false,
				url : __ctxPath + "/danpin/getSupplyInfo.do?sid=" + this.supplyInfoSid,
				waitMsg : "正在载入数据……",
				success : function(f, d) {
					
				},
				failure : function() {
					
				}
			});
		}
		var supplyInfoSid = (this.supplyInfoSid == null || this.supplyInfoSid == "undefined") ? 0 : this.supplyInfoSid;
		this.store = new Ext.data.JsonStore({
			url : __ctxPath + "/danpin/listSupplyLinker.do?Q_status_N_EQ=1&Q_supplyInfoSid_L_EQ=" + supplyInfoSid,
			id : "sid",
			root : "result",
			remoteSort : true,
			fields : [
				"sid",
				"linker",
				"linkerPhone",
				"email",
				"isMainLinker"
			]
		});
		this.store.setDefaultSort("id", "asc");
		this.store.load({
			params : {
				start : 0,
				limit : 10
			}
		});
		var b = new Array();
		if(isGranted("_SupplyLinkerDel")) {
			b.push({
				iconCls : "btn-del",
				qtip : "删除",
				style : "margin:0 3px 0 3px"
			});
		}
		if(isGranted("_SupplyLinkerEdit")) {
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
			columns : [
				c,
				new Ext.grid.RowNumberer(),
				{
					header : "sid",
					dataIndex : "sid",
					hidden : true
				}, {
					header : "姓名",
					dataIndex : "linker"
				}, {
					header : "联系电话",
					dataIndex : "linkerPhone"
				}, {
					header : "邮箱",
					dataIndex : "email"
				}, {
					header : "备注",
					dataIndex : "isMainLinker",
					renderer : function(c) {
						if(c == "1") {
							return "<font color='red'>主联系人</font>";
						} else {
							return "";
						}
					}
				},
				this.rowActions
			],
			defaults : {
				sortable : true,
				menuDisable : false
			}
		});
		this.topbar = new Ext.Toolbar({
			height : 30,
			bodyStyle : "text-align:left",
			items : []
		});
		if(isGranted("_SupplyLinkerAdd")) {
			this.topbar.add({
				iconCls : "btn-add",
				text : "添加联系人",
				xtype : "button",
				handler : this.addSupplyLinker
			});
		}
		this.gridPanel = new Ext.grid.EditorGridPanel({
			id : "SupplyLinkerGrid",
			region : "center",
			autoWidth : true,
			autoHeight : true,
			stripeRows : true,
			autoScroll : true,
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
				pageSize : 100,
				store : this.store,
				displayInfo : true,
				displayMsg : "当前显示{0}至{1}，共{2}条记录",
				emptyMsg : "当前没有记录"
			})
		});
		this.gridPanel.addListener("rowdblclick", function(f, d, g) {
			f.getSelectionModel().each(function(e) {
				new SupplyLinkerForm({
					supplyLinkerSid : e.data.sid
				}).show();
			});
		});
		this.rowActions.on("action", this.onRowAction, this);
		this.buttons = [
			{
				text : "关闭",
				handler : this.cancel.createCallback(this)
			}
		];
	},
	cancel : function(a) {
		a.close();
	},
	addSupplyLinker : function() {
		new SupplyLinkerForm({
			supplyInfoSid : Ext.getCmp("SupplyLinkerView").supplyInfoSid
		}).show();
	},
	delSupplyLinker : function() {
		var e = Ext.getCmp("SupplyLinkerGrid");
		var c = e.getSelectionModel().getSelections();
		if(c.length == 0) {
			Ext.ux.Toast.msg("提示信息", "请选择要删除的记录！");
			return ;
		}
		var f = Array();
		for(var d = 0; d < c.length; d++) {
			f.push(c[d].data.sid);
		}
		SupplyLinkerView.remove(f);
	},
	editSupplyLinker : function(a) {
		new SupplyLinkerForm({
			supplyLinkerSid : a.data.sid,
			supplyInfoSid : Ext.getCmp("SupplyLinkerView").supplyInfoSid
		}).show();
	},
	onRowAction : function(c, a, d, e, b) {
		switch(d) {
			case "btn-del":
				this.delSupplyLinker();
				break ;
			case "btn-edit":
				this.editSupplyLinker(a);
				break ;
			default:
				break ;
		}
	}
});
SupplyLinkerView.remove = function(b) {
	var a = Ext.getCmp("SupplyLinkerGrid");
	Ext.Msg.confirm("信息确认", "您确认要删除所选记录吗？", function(c) {
		if(c == "yes") {
			Ext.Ajax.request({
				url : __ctxPath + "/danpin/multiDelSupplyLinker.do",
				params : {
					sids : b
				},
				method : "post",
				success : function() {
					Ext.ux.Toast.msg("提示信息", "成功删除所选记录！");
					a.getStore().reload({
						params : {
							start : 0,
							limit : 10
						}
					});
				}
			});
		}
	});
}