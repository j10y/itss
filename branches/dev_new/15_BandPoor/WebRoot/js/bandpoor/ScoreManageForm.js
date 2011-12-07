ScoreManageForm = Ext.extend(Ext.Window, {
		formPanel : null,
		constructor : function (a) {
			Ext.applyIf(this, a);
			this.initUIComponents();
			ScoreManageForm.superclass.constructor.call(this, {
				layout : "fit",
				id : "ScoreManageFormWin",
				iconCls : "menu-ScoreManage",
				items : this.formPanel,
				title : "可评分品牌信息录入",
				width : 680,
				border : false,
				height : 500,
				modal : true,
				plain : true,
				bodyStyle : "padding:5px;",
				buttonAlign : "center",
				buttons : this.buttons
			});
		},
		initUIComponents : function () {
			this.formPanel = new Ext.FormPanel({
					url : __ctxPath + "/bandpoor/saveScoreManage.do",
					layout : "form",
					id : "ScoreManageForm",
					frame : false,
					defaults : {
						width : 300,
						anchor : "98%,98%",
						labelWidth : 80
					},
					formId : "ScoreManageFormId",
					defaultType : "textfield",
					items : [{
							name : "infoPoor.id",
							id : "id",
							xtype : "hidden",
							value : this.id == null ? "" : this.id
						}, {
							xtype : "container",
							border : false,
							//style : "padding-left:10%;padding-right:10%",
							layout : "form",
							items : [{
									layout : "column",
									border : false,
									anchor : "100%",
									items : [{
											layout : "form",
											columnWidth : 0.5,
											border : false,
											style : "padding-left:0px;",
											items : [{
													fieldLabel : "品牌名称",
													name : "infoPoor.bandName",
													id : "bandName",
													maxHeight : 200,
													xtype : "combo",
													mode : "local",
													editable : true,
													allowBlank : false,
													triggerAction : "all",
													valueField : "fromBandId",
													displayField : "fromBandName",
													store : new Ext.data.SimpleStore({
														url : __ctxPath
														 + "/bandpoor/getBandsScoreManage.do",
														fields : [
															"fromBandId",
															"fromBandName"]
													})
												},{
													fieldLabel : "楼层",
													name : "infoPoor.floorNumName",
													id : "floorNumName",
													maxHeight : 200,
													xtype : "combo",
													mode : "local",
													editable : true,
													allowBlank : false,
													triggerAction : "all",
													valueField : "fromFloorNumId",
													displayField : "fromFloorNumName",
													store : new Ext.data.SimpleStore({
														url : __ctxPath
														 + "/bandpoor/getBandsScoreManage.do",
														fields : [
															"fromFloorNumId",
															"fromFloorNumName"]
													})
												},{
													fieldLabel : "主力价格带",
													name : "infoPoor.mainPriceName",
													id : "mainPriceName",
													maxHeight : 200,
													xtype : "combo",
													mode : "local",
													editable : true,
													allowBlank : false,
													triggerAction : "all",
													valueField : "mainPriceId",
													displayField : "mainPriceName",
													store : new Ext.data.SimpleStore({
														url : __ctxPath
														 + "/bandpoor/getBandsScoreManage.do",
														fields : [
															"mainPriceId",
															"mainPriceName"]
													})
												},{
													fieldLabel : "销售场所",
													name : "infoPoor.saleStoreName",
													id : "saleStoreName",
													maxHeight : 200,
													xtype : "combo",
													mode : "local",
													editable : true,
													allowBlank : false,
													triggerAction : "all",
													valueField : "mainPriceId",
													displayField : "saleStoreName",
													store : new Ext.data.SimpleStore({
														url : __ctxPath
														 + "/bandpoor/getBandsScoreManage.do",
														fields : [
															"saleStoreid",
															"saleStoreName"]
													})
												},{
													fieldLabel : "销售渠道",
													name : "infoPoor.bandChannelName",
													id : "bandChannelName",
													maxHeight : 200,
													xtype : "combo",
													mode : "local",
													editable : true,
													allowBlank : false,
													triggerAction : "all",
													valueField : "bandChannelID",
													displayField : "bandChannelName",
													store : new Ext.data.SimpleStore({
														url : __ctxPath
														 + "/bandpoor/getBandsScoreManage.do",
														fields : [
															"bandChannelID",
															"bandChannelName"]
													})
												}
											]
										}, {
											layout : "form",
											columnWidth : 0.5,
											border : false,
											style : "padding-left:0px;",
											items : [
												{
													fieldLabel : "主力品类",
													name : "infoPoor.proClassName",
													id : "proClassName",
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
														 + "/bandpoor/getBandsScoreManage.do",
														fields : [
															"fromProClassId",
															"fromProClassName"]
													}),
													listeners : {
														focus : function (b) {
															var a = Ext.getCmp("bandName").getStore();
															if (a.getCount() <= 0) {
																Ext.Ajax.request({
																	url : __ctxPath + "/bandpoor/getBandsScoreManage.do",
																	method : "post",
																	success : function (d) {
																		var c = Ext.util.JSON.decode(d.responseText);
																		a.loadData(c);
																	}
																});
															}
														}
													}
												},{
													fieldLabel : "品牌风格",
													name : "infoPoor.bandStyleName",
													id : "bandStyleName",
													maxHeight : 200,
													xtype : "combo",
													mode : "local",
													editable : true,
													allowBlank : false,
													triggerAction : "all",
													valueField : "bandStyleId",
													displayField : "bandStyleName",
													store : new Ext.data.SimpleStore({
														url : __ctxPath
														 + "/bandpoor/getBandsScoreManage.do",
														fields : [
															"bandStyleId",
															"bandStyleName"]
													}),
													listeners : {
														focus : function (b) {
															var a = Ext.getCmp("proClassName").getStore();
															if (a.getCount() <= 0) {
																Ext.Ajax.request({
																	url : __ctxPath + "/bandpoor/getBandsScoreManage.do",
																	method : "post",
																	success : function (d) {
																		var c = Ext.util.JSON.decode(d.responseText);
																		a.loadData(c);
																	}
																});
															}
														}
													}
												},{
													fieldLabel : "官网",
													width : 170,
													name : "infoPoor.webSite",
													xtype : "textfield",
													id : "webSite"
												},{
													fieldLabel : "商圈",
													name : "infoPoor.bandBusinessAreaName",
													id : "bandBusinessAreaName",
													maxHeight : 200,
													xtype : "combo",
													mode : "local",
													editable : true,
													allowBlank : false,
													triggerAction : "all",
													valueField : "bandBusinessAreaId",
													displayField : "bandBusinessAreaName",
													store : new Ext.data.SimpleStore({
														url : __ctxPath
														 + "/bandpoor/getBandsScoreManage.do",
														fields : [
															"bandBusinessAreaId",
															"bandBusinessAreaName"]
													})
												}
											]
										}
									]
								}
							]
						},{
							width : 170,
							xtype : "button",
							iconCls : "btn-add",
							text : "上传LOGO",
							handler : function () {
								//var d = Ext.getCmp("companyLogo");
								var e = App.createUploadDialog({
										file_cat : "system/company",
										callback : c,
										permitted_extensions : ["jpg", "png"]
									});
									e.show("queryBtn");
							}
						}
					]
				});
			if (this.dicId != null && this.dicId != "undefined") {
				this.formPanel.getForm().load({
					deferredRender : false,
					url : __ctxPath + "/system/getScoreManage.do?dicId=" + this.dicId,
					waitMsg : "正在载入数据...",
					success : function (a, b) {},
					failure : function (a, b) {}
					
				});
			}
			this.buttons = [{
					text : "保存",
					iconCls : "btn-save",
					handler : this.save.createCallback(this.formPanel, this)
				}, {
					text : "重置",
					iconCls : "btn-reset",
					handler : this.reset.createCallback(this.formPanel)
				}, {
					text : "取消",
					iconCls : "btn-cancel",
					handler : this.cancel.createCallback(this)
				}
			];
		},
		reset : function (a) {
			a.getForm().reset();
		},
		cancel : function (a) {
			a.close();
		},
		save : function (a, b) {
			if (a.getForm().isValid()) {
				a.getForm().submit({
					method : "POST",
					waitMsg : "正在提交数据...",
					success : function (c, e) {
						Ext.ux.Toast.msg("操作信息", "成功保存信息！");
						var d = Ext.getCmp("ArchivesAttendGrid");
						if (d != null) {
							d.getStore().reload();
						}
						b.close();
					},
					failure : function (c, d) {
						Ext.MessageBox.show({
							title : "操作信息",
							msg : "信息保存出错，请联系管理员！",
							buttons : Ext.MessageBox.OK,
							icon : Ext.MessageBox.ERROR
						});
						b.close();
					}
				});
			}
		}
	});
