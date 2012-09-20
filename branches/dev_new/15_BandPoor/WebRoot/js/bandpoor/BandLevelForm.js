BandLevelForm = Ext.extend(Ext.Window, {
	formPanel : null,
	constructor : function(a) {
		if(a == null) {
			a = {};
		}
		Ext.apply(this, a);
		this.initComponents();
		BandLevelForm.superclass.constructor.call(this, {
			id : "BandLevelFormWin",
			layout : "fit",
			items : this.formPanel,
			modal : true,
			height : 235,
			width : 350,
			title : "商圈详细信息",
			buttonAlign : "center",
			buttons : this.buttons
		});
	},
	initComponents : function() {
		this.formPanel = new Ext.FormPanel({
			layout : "form",
			bodyStyle : "padding:10px 10px 10px 10px",
			border : false,
			url : __ctxPath + "/bandpoor/saveBandLevel.do",
			id : "BandLevelForm",
			defaults : {
				anchor : "95%,95%"
			},
			defaultType : "textfield",
			items : [
				{
					name : "bandLevel.id",
					id : "bandLevel.bandLevelId",
					xtype : "hidden",
					value : this.bandLevelId == null ? "" : this.bandLevelId
				}, {
													name : "bandLevel.proClassId.id",
													id : "bandLevel.proClassId",
													xtype : "hidden"
												}, {
													fieldLabel : "品类",
													name : "bandLevel.proClassName",
													id : "bandLevel.proClassName",
													maxHeight : 200,
													xtype : "combo",
													mode : "local",
													editable : true,
													allowBlank : false,
													triggerAction : "all",
													valueField : "fromProClassId",
													displayField : "fromProClassName",
													store : new Ext.data.SimpleStore({
														url : __ctxPath
														 + "/bandpoor/getProClassScoreManage.do",
														fields : [
															"fromProClassId",
															"fromProClassName"]
													}),
													listeners : {
														focus : function (b) {
															var a = Ext.getCmp("bandLevel.proClassName").getStore();
															if (a.getCount() <= 0) {
																Ext.Ajax.request({
																	url : __ctxPath + "/bandpoor/getProClassScoreManage.do",
																	method : "post",
																	success : function (d) {
																		var c = Ext.util.JSON.decode(d.responseText);
																		a.loadData(c);
																	}
																});
															}
														},
														select:function(e,c,d){
															var proClassId=Ext.getCmp("bandLevel.proClassName").getValue();
															Ext.getCmp("bandLevel.proClassId").setValue(proClassId);
														}
													}
												}, 
				
				{
					fieldLabel : "品牌池分类名称",
					name : "bandLevel.levelName",
					id : "bandLevel.levelName",
					allowBlank : false,
					blankText : "品牌池分类名称不能为空！"
				}, {
					fieldLabel : "分数下限",
					xtype:"numberfield",
					name : "bandLevel.startValue",
					id : "bandLevel.startValue",
					allowBlank : false,
					blankText : "分数下限不能为空！"
				}, {
					fieldLabel : "分数上限",
					xtype:"numberfield",
					name : "bandLevel.endValue",
					id : "bandLevel.endValue",
					allowBlank : false,
					blankText : "分数上限不能为空！"
				},  
				{
					fieldLabel : "品牌池分类描述",
					name : "bandLevel.levelDesc",
					id : "bandLevel.levelDesc",
					xtype:"textarea"
				}
			]
		});
		if(this.bandLevelId != null && this.bandLevelId != "undefined") {
			this.formPanel.getForm().load({
				deferredRender : false,
				url : __ctxPath + "/bandpoor/getBandLevel.do?id=" + this.bandLevelId,
				waitMsg : "正在载入数据……",
				success : function(f, d) {
					var e = Ext.util.JSON.decode(d.response.responseText);
					//var e =Ext.util.JSON.decode(d.response.responseText).data[0];
					Ext.getCmp("bandLevel.bandLevelId").setValue(e.data.id);
					Ext.getCmp("bandLevel.proClassId").setValue(e.data.proClassId.id);
					Ext.getCmp("bandLevel.proClassName").setValue(e.data.proClassName);
					Ext.getCmp("bandLevel.levelName").setValue(e.data.levelName);
					Ext.getCmp("bandLevel.startValue").setValue(e.data.startValue);
					Ext.getCmp("bandLevel.endValue").setValue(e.data.endValue);
					Ext.getCmp("bandLevel.levelDesc").setValue(e.data.levelDesc);
					
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
		   var ev = Ext.getCmp("bandLevel.endValue").getValue();
		   var sv = Ext.getCmp("bandLevel.startValue").getValue();
		   if(ev<sv){
		   	Ext.MessageBox.show({
						title : "操作信息",
						msg : "分数上限小于下限",
						buttons : Ext.MessageBox.OK,
						icon : Ext.MessageBox.ERROR
					});
					return ;
		   }
			a.getForm().submit({
				method : "post",
				waitMsg : "正在提交数据……",
				success : function() {
					Ext.ux.Toast.msg("提示信息","保存成功！");
					Ext.getCmp("BandLevelView").gridPanel.store.reload({
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