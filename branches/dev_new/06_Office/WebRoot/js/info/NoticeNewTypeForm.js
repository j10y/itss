NoticeNewTypeForm = Ext.extend(Ext.Window, {
	formPanel : null,
	constructor : function(a) {
		Ext.applyIf(this, a);
		this.initUIComponents();
		NoticeNewTypeForm.superclass.constructor.call(this, {
			items : this.formPanel,
			id : "newsTypeFormWin",
			border : false,
			title : "通知类型",
			iconCls : "menu-news_type",
			width : 260,
			height : 150,
			layout : "fit",
			modal : true,
			plain : true,
			buttonAlign : "center",
			buttons : this.buttons
		});
	},
	initUIComponents : function() {
		this.initUI();
		this.buttons = [ {
			text : "保存",
			iconCls : "btn-save",
			scope : this,
			handler : function() {
				var b = Ext.getCmp("NoticeNewTypeGrid");
				var a = Ext.getCmp("typeTree");
				if (this.formPanel.getForm().isValid()) {
					this.formPanel.getForm().submit({
						waitMsg : "正在提交通知类型信息",
						success : function(c, d) {
							Ext.ux.Toast.msg("操作信息", "添加通知类型成功！");
							Ext.getCmp("newsTypeFormWin").close();
							if (b != null) {
								b.getStore().reload();
							}
							if (a != null) {
								a.root.reload();
							}
						}
					});
				}
			}
		}, {
			text : "取消",
			iconCls : "btn-cancel",
			handler : function() {
				Ext.getCmp("newsTypeFormWin").close();
			}
		} ];
	}
});
NoticeNewTypeForm.prototype.initUI = function() {
	this.formPanel = new Ext.form.FormPanel({
		id : "newsTypeForm",
		frame : false,
		layout : "form",
		bodyStyle : "padding:5px;",
		defaultType : "textfield",
		url : __ctxPath + "/info/addNoticeNewType.do",
		defaultType : "textfield",
		labelWidth : 80,
		reader : new Ext.data.JsonReader({
			root : "data"
		}, [ {
			name : "typeId",
			mapping : "typeId"
		}, {
			name : "typeName",
			mapping : "typeName"
		}, {
			name : "sn",
			mapping : "sn"
		} ]),
		defaults : {
			allowBlank : false,
			selectOnFocus : true,
			msgTarget : "side"
		},
		items : [ {
			xtype : "hidden",
			name : "newsType.typeId",
			id : "typeId"
		}, {
			fieldLabel : "类型名称",
			name : "newsType.typeName",
			blankText : "类型名称为必填!",
			id : "typeName"
		}, {
			xtype : "hidden",
			name : "newsType.sn",
			id : "sn"
		} ]
	});
};