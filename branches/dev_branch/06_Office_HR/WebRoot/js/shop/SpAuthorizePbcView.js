SpAuthorizePbcView = Ext.extend(Ext.Window, {
	constructor : function(a) {
		if(a == null) {
			a = {};
		}
		Ext.apply(this, a);
		this.initComponents();
		SpAuthorizePbcView.superclass.constructor.call(this, {
			id : "SpAuthorizePbcView",
			title : "待授权项目列表",
			width : 700,
			modal : true,
			height : 500,
			layout : "border",
			items : [
				this.gridPanel
			],
			buttonAlign : "center",
			buttons : this.buttons
		});
	},
	gridPanel : null,
	store : null,
	initComponents : function() {
		this.store = new Ext.data.JsonStore({
			remoteSort : true,
			fields : [
				{
					name : "id",
					type : "int"
				},
				"fullname",
				"pbcName",
				"pbcName"
			]
		});
		for(var i = 0; i < this.userPbcs.length; i++) {
			this.store.add(this.userPbcs[i]);
		}
		var b = new Array();
		b.push({
			iconCls : "btn-edit",
			qtip : "查看",
			style : "margin:0 3px 0 20px"
		});
		this.rowActions = new Ext.ux.grid.RowActions({
			header : "管理",
			width : 80,
			actions : b
		});
		var a = new Ext.grid.ColumnModel({
			columns : [
				this.rowActions,
				{
					header : "id",
					dataIndex : "id",
					hidden : true
				}, {
					header : "姓名",
					dataIndex : "fullname"
				}, {
					header : "岗位",
					dataIndex : "position"
				}, {
					header : "待考评项目",
					dataIndex : "pbcName"
				}
			],
			defaults : {
				sortable : true,
				menuDisabled : false,
				width : 100
			}
		});
		this.gridPanel = new Ext.grid.GridPanel({
			id : "AuthorizePbcGrid",
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
			plugins : this.rowActions,
			viewConfig : {
				forceFit : true,
				enableRowBody : false,
				showPreview : false
			}
		});
		this.rowActions.on("action", this.onRowAction, this);
		this.buttons = [
			{
				text : "完成",
				handler : this.finish.createCallback(this)
			}
		]
	},
	authorize : function(a) {
		new AuthorizePbcForm({
			pbcId : a.data.id,
			from : "SpAuthorizePbcView"
		}).show();
	},
	onRowAction : function(c, a, d, e, b) {
		switch(d) {
			case "btn-edit":
				this.authorize(a);
				break ;
			default:
				break ;
		}
	},
	finish : function(a) {
		if(a.gridPanel.getStore().getCount() > 0) {
			Ext.MessageBox.show({
				title : "操作信息",
				msg : "您还有未授权的考评项目，请核实！",
				buttons : Ext.MessageBox.OK,
				icon : Ext.MessageBox.ERROR
			});
			return ;
		}
		a.close();
	}
});