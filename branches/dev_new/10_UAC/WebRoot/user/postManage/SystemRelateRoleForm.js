SystemRelateRoleForm = Ext.extend(Ext.Window, {
	formPanel : null,
	constructor : function(a) {
		if(a == null) {
			a = {};
		}
		Ext.apply(this, a);
		this.initComponents();
		SystemRelateRoleForm.superclass.constructor.call(this, {
			id : "SystemRelateRoleFormWin",
			layout : "fit",
			items : this.roleUserPanel,
			modal : true,
			height : 250,
			width : 400,
			title : "关联角色信息",
			buttonAlign : "center",
			buttons : this.buttons
		});
	},
	initComponents : function() {
		var k = new Ext.data.Store({
			url : webContext + "/system?methodCall=listMembers&systemDN=" + this.systemDN,
			reader : new Ext.data.JsonReader({
				root : "result",
				fields : [
					"dn",
					"displayName"
				]
			}),
			remoteSort : true
		});
		k.load();
		var l = new Ext.grid.ColumnModel({
			columns : [
				new Ext.grid.RowNumberer(),
				{
					header : "dn",
					dataIndex : "dn",
					hidden : true
				}, {
					header : "姓名",
					dataIndex : "displayName"
				}, {
					header : "管理",
					dataIndex : "dn",
					sortable : false,
					width : 60,
					renderer : function(r, q, o, u, p) {
						var t = o.data.dn;
						var s = "";
						s += '<a href="#" title="解除关联" onclick="SystemRelateRoleForm.del()">解除关联</a>';
						return s;
					}
				}
			],
			defaults : {
				sortable : true,
				menuDisable : true,
				width : 100
			}
		});
		this.roleUserPanel = new Ext.grid.GridPanel({
			id : "SystemRolePanel",
			width : 640,
			height : 200,
			border : true,
			store : k,
			autoScroll : true,
			trackMouseOver : true,
			disableSelection : false,
			loadMask : true,
			tbar : this.topbar,
			cm : l,
			viewConfig : {
				forceFit : true,
				enableRowBody : false,
				showPreview : false
			},
			tbar : [
					{
						id : "roleName",
						xtype : "combo",
						fieldLabel : "角色",
						triggerAction : "all",
						mode : "local",
						valueField : "dn",
						displayField : "displayName",
						store : new Ext.data.SimpleStore({
							url : webContext + "/role?methodCall=combo",
							fields : ["dn", "displayName"],
							remoteSort : true
						}),
						listeners : {
							beforequery : function(queryEvent) {
								var store = queryEvent.combo.store;
								store.baseParams = {
									"param" : queryEvent.query
								};
								store.load();
								return false;
							}
						}
					},
					{
						xtype : "button",
						text : "添加",
						handler : function() {
							var f = Ext.getCmp("SystemRolePanel");
							for(var i = 0; i < f.getStore().getCount(); i++) {
								if(f.getStore().getAt(i).data.dn == Ext.getCmp("roleName").getValue()) {
									Ext.MessageBox.show({
										title : "操作信息",
										msg : "您添加了重复的角色，请核实！",
										buttons : Ext.MessageBox.OK,
										icon : Ext.MessageBox.ERROR
									});
									return ;
								}
							}
							var newRecord = Ext.data.Record.create([
								{
									name : "dn",
									type : "string"
								}, {
									name : "displayName",
									type : "string"
								}
							]);
							var newData = new newRecord({
								"dn" : Ext.getCmp("roleName").getValue(),
								"displayName" : Ext.getCmp("roleName").getRawValue()
							});
							f.getStore().add(newData);
						}
					}
				]
		});
		
		
		this.buttons = [
			{
				text : "保存",
				handler : this.save.createCallback(this, this.formPanel)
			}, {
				text : "取消",
				handler : this.cancel.createCallback(this)
			}
		];
	},
	save : function(a, b) {
		var roleDNs = "";
		var f = Ext.getCmp("SystemRolePanel");
		for(var i = 0; i < f.getStore().getCount(); i++) {
			if(i < f.getStore().getCount()-1){
				roleDNs += f.getStore().getAt(i).data.dn + "#";
			}else{
				roleDNs += f.getStore().getAt(i).data.dn;
			}
			
		}
		Ext.Ajax.request({
			url : webContext + "/system?methodCall=relatRoles",
			params : {
				systemDN : a.systemDN,
				systemOccupants : roleDNs
			},
			success : function(d) {
				Ext.getCmp("SystemRolePanel").getStore().reload();
				Ext.MessageBox.show({
					title : "操作信息",
					msg : "关联成功！",
					buttons : Ext.MessageBox.OK,
					icon : Ext.MessageBox.INFO
				});
			},
			failure : function() {
				Ext.MessageBox.show({
					title : "操作信息",
					msg : "操作失败，请联系管理员！",
					buttons : Ext.MessageBox.OK,
					icon : Ext.MessageBox.ERROR
				});
			}
		});
		
	},
	cancel : function(a) {
		a.close();
	}
});
SystemRelateRoleForm.del  =  function() {
	var e = Ext.getCmp("SystemRolePanel");
	var c = e.getSelectionModel().getSelections();
	if(c.length == 0) {
		Ext.ux.Toast.msg("提示信息","请选择要删除的记录！");
		return ;
	}
	var allStore = e.getStore();
	Ext.each(c, function(item) {
		allStore.remove(item);
	});
}