RoomForm = Ext.extend(Ext.Window, {
	formPanel : null,
	constructor : function(a) {
		if(a == null) {
			a = {};
		}
		Ext.apply(this, a);
		this.initComponents();
		RoomForm.superclass.constructor.call(this, {
			id : "RoomFormWin",
			layout : "fit",
			items : this.formPanel,
			modal : true,
			height : 300,
			width : 350,
			title : "区域详细信息",
			buttonAlign : "center",
			buttons : this.buttons
		});
	},
	initComponents : function() {
		this.formPanel = new Ext.FormPanel({
			layout : "form",
			bodyStyle : "padding:10px 10px 10px 10px",
			border : false,
			url : __ctxPath + "/system/saveMrbsRoom.do",
			id : "RoomForm",
			defaults : {
				anchor : "95%,95%"
			},
			defaultType : "textfield",
			items : [
				{
					name : "mrbsRoom.id",
					id : "mrbsRoomId",
					xtype : "hidden",
					value : this.mrbsRoomId == null ? "" : this.mrbsRoomId
				}, {
					fieldLabel : "会议室名称",
					name : "mrbsRoom.roomName",
					id : "roomName",
					allowBlank:false
				}, {
					fieldLabel : "容纳人数",
					name : "mrbsRoom.capacity",
					id : "capacity",
					xtype:"numberfield",
					allowBlank:false
				},{
					fieldLabel : "简述",
					name : "mrbsRoom.roomAdminEmail",
					id : "roomAdminEmail",
					xtype : "textarea"
				},{
					fieldLabel : "所在区域",
					hiddenName:"mrbsRoom.area.id",
					id : "areaName",
					xtype : "combo",
					allowBlank : false,
					triggerAction : "all",
					mode : "local",
					valueField : "id",
					displayField : "name",
					store : new Ext.data.SimpleStore({
						url : __ctxPath + "/system/comboMrbsArea.do",
						fields : ["id", "name"],
						remoteSort : true
					}),
					listeners : {
						beforequery : function(queryEvent) {
							var store = queryEvent.combo.store;
							store.baseParams = {
								"Q_areaName_S_LK" : queryEvent.query,
								"Q_flag_N_EQ" : 1
							};
							store.load();
							return false;
						}
					}
				}
			]
		});
		if(this.mrbsRoomId != null && this.mrbsRoomId != "undefined") {
			this.formPanel.getForm().load({
				deferredRender : false,
				url : __ctxPath + "/system/getMrbsRoom.do?id=" + this.mrbsRoomId,
				waitMsg : "正在载入数据……",
				success : function(f, d) {
					var e = Ext.util.JSON.decode(d.response.responseText);
					Ext.getCmp("areaName").setValue(e.data.areaId);
					Ext.getCmp("areaName").setRawValue(e.data.areaName);
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
					if(Ext.getCmp("RoomView")){
						Ext.getCmp("RoomView").gridPanel.store.reload({
							params : {
								start : 0,
								limit : 25
							}
						});
					}					
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