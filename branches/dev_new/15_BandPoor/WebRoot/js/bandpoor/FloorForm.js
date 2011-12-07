FloorForm = Ext.extend(Ext.Window, {
	formPanel : null,
	constructor : function(a) {
		if(a == null) {
			a = {};
		}
		Ext.apply(this, a);
		this.initComponents();
		FloorForm.superclass.constructor.call(this, {
			id : "FloorFormWin",
			layout : "fit",
			items : this.formPanel,
			modal : true,
			height : 180,
			width : 350,
			title : "楼层详细信息",
			buttonAlign : "center",
			buttons : this.buttons
		});
	},
	initComponents : function() {
		this.formPanel = new Ext.FormPanel({
			layout : "form",
			bodyStyle : "padding:10px 10px 10px 10px",
			border : false,
			url : __ctxPath + "/bandpoor/saveFloor.do",
			id : "FloorForm",
			defaults : {
				anchor : "95%,95%"
			},
			defaultType : "textfield",
			items : [
				{
					name : "floor.id",
					id : "floorId",
					xtype : "hidden",
					value : this.floorId == null ? "" : this.floorId
				}, {
					fieldLabel : "名称",
					name : "floor.floorName",
					id : "floorName",
					allowBlank : false,
					blankText : "名称不能为空！"
				}, {
					fieldLabel : "描述",
					name : "floor.floorDesc",
					id : "floorDesc",
					xtype : "textarea"
				}
			]
		});
		if(this.floorId != null && this.floorId != "undefined") {
			this.formPanel.getForm().load({
				deferredRender : false,
				url : __ctxPath + "/bandpoor/getFloor.do?id=" + this.floorId,
				waitMsg : "正在载入数据……",
				success : function(f, d) {
					
				},
				failure : function() {
					
				}
			});
		}
		this.buttons = [
			{
				text : "保存",
				iconCls : "btn-save",
				handler : this.save.createCallback(this.formPanel, this)
			}, {
				text : "取消",
				iconCls : "btn-cancel",
				handler : this.cancel.createCallback(this)
			}
		];
	},
	cancel : function(a) {
		a.close();
	},
	save : function(a, b) {
		if(a.getForm().isValid()) {
			a.getForm().submit({
				method : "post",
				waitMsg : "正在提交数据……",
				success : function() {
					Ext.ux.Toast.msg("提示信息","保存成功！");
					Ext.getCmp("FloorView").gridPanel.store.reload({
						params : {
							start : 0,
							limit : 25
						}
					});
					b.close();
				},
				failure : function(c, d) {
					Ext.MessageBox.show({
						title : "操作信息",
						msg : d.result.msg,
						buttons : Ext.MessageBox.OK,
						icon : Ext.MessageBox.ERROR
					});
					return ;
				}
			});
		}
	}
});