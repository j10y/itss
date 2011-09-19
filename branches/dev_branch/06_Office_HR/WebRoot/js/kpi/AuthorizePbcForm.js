AuthorizePbcForm = Ext.extend(Ext.Window, {
	constructor : function(a) {
		if(a == null) {
			a = {};
		}
		Ext.apply(this, a);
		this.initComponents();
		AuthorizePbcForm.superclass.constructor.call(this, {
			id : "AuthorizePbcFormWin",
			title : "授权操作界面",
			width : 830,
			height : 500,
			modal : true,
			layout : "border",
			items : [
				this.authorizeUserPanel,
				this.gridPanel
			],
			buttonAlign : "center",
			buttons : this.buttons
		});
	},
	authorizeUserPanel : null,
	gridPanel : null,
	store : null,
	initComponents : function() {
		var a = __ctxPath + "/system/listDepartment.do?opt=appUser";
		var departments = new TreeSelector("depName", a, "所属部门", "depId");
		departments.addListener("expand", function() {
			Ext.getCmp("depNameTree").addListener("click", function() {
				var c = Ext.getCmp("authorUser").getStore();
				c.loadData("[]");
				Ext.getCmp("authorUser").setValue("");
				Ext.getCmp("authorUser").setRawValue("");
				Ext.Ajax.request({
					url : __ctxPath + "/system/selectAppUser.do",
					method : "post",
					params : {
						depId : Ext.getCmp("depId").getValue()
					},
					success : function(f) {
						var e = Ext.util.JSON.decode(f.responseText);
						c.loadData(e.result);
					}
				});
			});
		});
		this.authorizeUserPanel = new Ext.FormPanel({
			id : "AuthorizePbcForm",
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
					name : "depId",
					id : "depId",
					xtype : "textfield",
					hidden : true
				}, {
					text : "被授权人信息：部门"
				},
				departments,
				{
					text : "姓名"
				}, {
					fieldLabel : "姓名",
					maxHeight : 200,
					id : "authorUser",
					xtype : "combo",
					mode : "local",
					editable : false,
					valueField : "userId",
					displayField : "fullname",
					triggerAction : "all",
					store : new Ext.data.JsonStore({
						fields : ["userId", "fullname"]
					}),
					allowBlank :false,
					blankText : "姓名不能为空！"
				}
			]
		});
		this.store = new Ext.data.JsonStore({
			url : __ctxPath + "/kpi/authorListHrPaKpiitem2user.do?pbcId=" + this.pbcId,
			id : "id",
			root : "result",
			remoteSort : true,
			fields : [
				{
					name : "id",
					type : "int"
				},
				"paName",
				"weight",
				"desc"
			]
		});
		this.store.setDefaultSort("id", "desc");
		this.store.load({
			params : {
				start : 0,
				limit : 100
			}
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
					header : "考核指标名称",
					dataIndex : "paName",
					width : 60
				}, {
					header : "权重（%）",
					dataIndex : "authorWeight",
					editor : new Ext.form.Field({
						xtype : "textfield"
					}),
					width : 30
				}, {
					header : "授权信息",
					dataIndex : "desc",
					renderer : function(value, metadata, record) {
						metadata.attr = 'style="white-space:normal;"';
						return value;
					}
				}
			],
			defaults : {
				sortable : true,
				menuDisabled : false
			}
		});
		this.gridPanel = new Ext.grid.EditorGridPanel({
			id : "AuthorizePbcFormGrid",
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
			}
		});
		this.buttons = [
			{
				text : "取消",
				handler : this.cancel.createCallback(this)
			}, {
				text : "授权",
				handler : this.authorize.createCallback(this)
			}
		];
	},
	cancel : function(a) {
		a.close();
	},
	authorize : function(a) {
		var e = Ext.getCmp("AuthorizePbcFormGrid");
		var c = e.getSelectionModel().getSelections();
		if(!a.authorizeUserPanel.getForm().isValid()) {
			return ;
		}
		if(c.length <= 0) {
			Ext.ux.Toast.msg("提示信息", "请选择要授权的考核指标！");
			return ;
		}
		var authorItems = "";
		for(var i = 0; i < c.length; i++) {
			if(isNaN(c[i].data.authorWeight) || c[i].data.authorWeight <= 0 || c[i].data.authorWeight > 100) {
				Ext.MessageBox.show({
					title : "操作信息",
					msg : "请正确填写权重！",
					buttons : Ext.MessageBox.OK,
					icon : Ext.MessageBox.ERROR
				});
				return ;
			}
			authorItems += c[i].data.id + "," + parseInt(c[i].data.authorWeight)/100 + " ";
		}
		Ext.Ajax.request({
			url : __ctxPath + "/kpi/saveHrPaAuthorizepbc.do",
			params : {
				userId : Ext.getCmp("authorUser").getValue(),
				pbcId : a.pbcId,
				authorItems : authorItems
			},
			success : function(d) {
				Ext.ux.Toast.msg("提示信息", "授权成功！");
				if(a.from == "AuthorizePbcView") {
					var authorStore = Ext.getCmp("AuthorizePbcGrid").getStore();
					for(var i = 0; i < authorStore.getCount(); i++) {
						if(a.pbcId == authorStore.getAt(i).data.id) {
							authorStore.remove(authorStore.getAt(i));
						}
					}
				}
				a.close();
			},
			failure : function(d) {
				Ext.MessageBox.show({
					title : "操作信息",
					msg : "授权失败，请联系管理员！",
					buttons : Ext.MessageBox.OK,
					icon : Ext.MessageBox.ERROR
				});
			}
		});
	}
});