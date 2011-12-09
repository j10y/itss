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
							id : "infoPoor.id",
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
													name : "infoPoor.bandId.id",
													id : "infoPoor.bandId",
													xtype : "hidden"
												}, {
													fieldLabel : "品牌名称",
													name : "infoPoor.bandName",
													id : "infoPoor.bandName",
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
													}),
													listeners : {
														focus : function (b) {
															var a = Ext.getCmp("infoPoor.bandName").getStore();
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
														},
														select:function(e,c,d){
															var bandId=Ext.getCmp("infoPoor.bandName").getValue();
															Ext.getCmp("infoPoor.bandId").setValue(bandId);
														}
													}
												}, {
													name : "infoPoor.floorNumId.id",
													id : "infoPoor.floorNumId",
													xtype : "hidden"
												}, {
													fieldLabel : "楼层",
													name : "infoPoor.floorNumName",
													id : "infoPoor.floorNumName",
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
														 + "/bandpoor/getFloorScoreManage.do",
														fields : [
															"fromFloorNumId",
															"fromFloorNumName"]
													}),
													listeners : {
														focus : function (b) {
															var a = Ext.getCmp("infoPoor.floorNumName").getStore();
															if (a.getCount() <= 0) {
																Ext.Ajax.request({
																	url : __ctxPath + "/bandpoor/getFloorScoreManage.do",
																	method : "post",
																	success : function (d) {
																		var c = Ext.util.JSON.decode(d.responseText);
																		a.loadData(c);
																	}
																});
															}
														},
														select:function(e,c,d){
															var floorNumId=Ext.getCmp("infoPoor.floorNumName").getValue();
															Ext.getCmp("infoPoor.floorNumId").setValue(floorNumId);
														}
													}
												}, {
													name : "infoPoor.mainPriceId.id",
													id : "infoPoor.mainPriceId",
													xtype : "hidden"
												}, {
													fieldLabel : "主力价格带",
													name : "infoPoor.mainPriceName",
													id : "infoPoor.mainPriceName",
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
														 + "/bandpoor/getMainPriceScoreManage.do",
														fields : [
															"mainPriceId",
															"mainPriceName"]
													}),
													listeners : {
														focus : function (b) {
															var a = Ext.getCmp("infoPoor.mainPriceName").getStore();
															if (a.getCount() <= 0) {
																Ext.Ajax.request({
																	url : __ctxPath + "/bandpoor/getMainPriceScoreManage.do",
																	method : "post",
																	success : function (d) {
																		var c = Ext.util.JSON.decode(d.responseText);
																		a.loadData(c);
																	}
																});
															}
														},
														select:function(e,c,d){
															var mainPriceId=Ext.getCmp("infoPoor.mainPriceName").getValue();
															Ext.getCmp("infoPoor.mainPriceId").setValue(mainPriceId);
														}
													}
												}, {
													name : "infoPoor.saleStoreid.id",
													id : "infoPoor.saleStoreid",
													xtype : "hidden"
												}, {
													fieldLabel : "销售场所",
													name : "infoPoor.saleStoreName",
													id : "infoPoor.saleStoreName",
													maxHeight : 200,
													xtype : "combo",
													mode : "local",
													editable : true,
													allowBlank : false,
													triggerAction : "all",
													valueField : "saleStoreid",
													displayField : "saleStoreName",
													store : new Ext.data.SimpleStore({
														url : __ctxPath
														 + "/bandpoor/getSaleStoreScoreManage.do",
														fields : [
															"saleStoreid",
															"saleStoreName"]
													}),
													listeners : {
														focus : function (b) {
															var a = Ext.getCmp("infoPoor.saleStoreName").getStore();
															if (a.getCount() <= 0) {
																Ext.Ajax.request({
																	url : __ctxPath + "/bandpoor/getSaleStoreScoreManage.do",
																	method : "post",
																	success : function (d) {
																		var c = Ext.util.JSON.decode(d.responseText);
																		a.loadData(c);
																	}
																});
															}
														},
														select:function(e,c,d){
															var saleStoreid=Ext.getCmp("infoPoor.saleStoreName").getValue();
															Ext.getCmp("infoPoor.saleStoreid").setValue(saleStoreid);
														}
													}
												}, {
													name : "infoPoor.bandBusinessAreaId.id",
													id : "infoPoor.bandBusinessAreaId",
													xtype : "hidden"
												}, {
													fieldLabel : "商圈",
													name : "infoPoor.bandBusinessAreaName",
													id : "infoPoor.bandBusinessAreaName",
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
														 + "/bandpoor/getBusinessAreaScoreManage.do",
														fields : [
															"bandBusinessAreaId",
															"bandBusinessAreaName"]
													}),
													listeners : {
														focus : function (b) {
															var a = Ext.getCmp("infoPoor.saleStoreName").getValue();
															if (a != null
																 && a != ""
																 && a != "undefined") {
																Ext.getCmp("infoPoor.bandBusinessAreaName").getStore().reload({
																	params : {
																		saleStoreid : a
																	}
																});
															} else {
																Ext.ux.Toast
																.msg(
																	"操作信息",
																	"请先选择相应的商场！");
															}
														},
														select:function(e,c,d){
															var bandBusinessAreaId=Ext.getCmp("infoPoor.bandBusinessAreaName").getValue();
															Ext.getCmp("infoPoor.bandBusinessAreaId").setValue(bandBusinessAreaId);
														}
													}
												}
											]
										}, {
											layout : "form",
											columnWidth : 0.5,
											border : false,
											style : "padding-left:0px;",
											items : [{
													name : "infoPoor.proClassId.id",
													id : "infoPoor.proClassId",
													xtype : "hidden"
												}, {
													fieldLabel : "主力品类",
													name : "infoPoor.proClassName",
													id : "infoPoor.proClassName",
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
															var a = Ext.getCmp("infoPoor.proClassName").getStore();
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
															var proClassId=Ext.getCmp("infoPoor.proClassName").getValue();
															Ext.getCmp("infoPoor.proClassId").setValue(proClassId);
														}
													}
												}, {
													name : "infoPoor.bandStyleId.id",
													id : "infoPoor.bandStyleId",
													xtype : "hidden"
												}, {
													fieldLabel : "品牌风格",
													name : "infoPoor.bandStyleName",
													id : "infoPoor.bandStyleName",
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
														 + "/bandpoor/getBandStyleScoreManage.do",
														fields : [
															"bandStyleId",
															"bandStyleName"]
													}),
													listeners : {
														focus : function (b) {
															var a = Ext.getCmp("infoPoor.proClassName").getValue();
															if (a != null
																 && a != ""
																 && a != "undefined") {
																Ext.getCmp("infoPoor.bandStyleName").getStore().reload({
																	params : {
																		proClassId : a
																	}
																});
															} else {
																Ext.ux.Toast
																.msg(
																	"操作信息",
																	"请先选择相应的品类！");
															}
														},
														select:function(e,c,d){
															var bandStyleId=Ext.getCmp("infoPoor.bandStyleName").getValue();
															Ext.getCmp("infoPoor.bandStyleId").setValue(bandStyleId);
														}
													}
												}, {
													name : "infoPoor.bandChannelID.id",
													id : "infoPoor.bandChannelID",
													xtype : "hidden"
												}, {
													fieldLabel : "销售渠道",
													name : "infoPoor.bandChannelName",
													id : "infoPoor.bandChannelName",
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
														 + "/bandpoor/getChannelScoreManage.do",
														fields : [
															"bandChannelID",
															"bandChannelName"]
													}),
													listeners : {
														focus : function (b) {
															var a = Ext.getCmp("infoPoor.bandChannelName").getStore();
															if (a.getCount() <= 0) {
																Ext.Ajax.request({
																	url : __ctxPath + "/bandpoor/getChannelScoreManage.do",
																	method : "post",
																	success : function (d) {
																		var c = Ext.util.JSON.decode(d.responseText);
																		a.loadData(c);
																	}
																});
															}
														},
														select:function(e,c,d){
															var bandChannelID=Ext.getCmp("infoPoor.bandChannelName").getValue();
															Ext.getCmp("infoPoor.bandChannelID").setValue(bandChannelID);
														}
													}
												}, {
													fieldLabel : "官网",
													width : 170,
													name : "infoPoor.webSite",
													xtype : "textfield",
													id : "infoPoor.webSite"
												},{
													name : "infoPoor.picuurepathid",
													id : "infoPoor.picuurepathid",
													xtype : "hidden"
												}, {
													width : 170,
													xtype : "button",
													iconCls : "btn-add",
													text : "上传图片",
													handler : function () {
														//var d = Ext.getCmp("companyLogo");
														var e = App.createUploadDialog({
																file_cat : "system/company",
																callback : function (g) {
																	var d = Ext.getCmp("infoPoor.picuurepathid");	
																	for (var e = 0; e < g.length; e++) {
																		if (d.getValue() != "") {
																			d.setValue(d.getValue() + ",");
																		}
																		d.setValue(d.getValue() + g[e].fileId);
																		var gg = Ext.getCmp("LogoPanel");
																		Ext.DomHelper.append(gg.body, '<span><img src="' + "" +  __ctxPath +g[e].filepath +
																			'"  width="100" height="100" /><img class="img-delete" src="' + __ctxPath +
																			'/images/system/delete.gif" onclick="ScoreManageForm.removeFile(this,' + g[e].fileId + ')"/>&nbsp;|&nbsp;</span>');
																	}
																},
																permitted_extensions : ["jpg", "png"]
															});
														e.show("queryBtn");
													}
												}
											]
										}
									]
								}
							]
						}, {
							xtype : "panel",
							height : 270,
							id : "LogoPanel",
							autoScroll : true,
							html : ''
						}
					]
				});
			if (this.id != null && this.id != "undefined") {
				this.formPanel.getForm().load({
					deferredRender : false,
					url : __ctxPath + "/bandpoor/getScoreManage.do?id=" + this.id,
					waitMsg : "正在载入数据...",
					success : function (a, b) {
					var d = Ext.util.JSON.decode(b.response.responseText).data[0];
					Ext.getCmp("infoPoor.id").setValue(d.id);
					Ext.getCmp("infoPoor.bandId").setValue(d.bandId.id);
					Ext.getCmp("infoPoor.bandName").setValue(d.bandName);
					Ext.getCmp("infoPoor.floorNumId").setValue(d.floorNumId.id);
					Ext.getCmp("infoPoor.floorNumName").setValue(d.floorNumName);
					Ext.getCmp("infoPoor.mainPriceId").setValue(d.mainPriceId.id);
					Ext.getCmp("infoPoor.mainPriceName").setValue(d.mainPriceName);
					Ext.getCmp("infoPoor.saleStoreid").setValue(d.saleStoreid.id);
					Ext.getCmp("infoPoor.saleStoreName").setValue(d.saleStoreName);
					Ext.getCmp("infoPoor.bandBusinessAreaId").setValue(d.bandBusinessAreaId.id);
					Ext.getCmp("infoPoor.bandBusinessAreaName").setValue(d.bandBusinessAreaName);
					Ext.getCmp("infoPoor.proClassId").setValue(d.proClassId.id);
					Ext.getCmp("infoPoor.proClassName").setValue(d.proClassName);
					Ext.getCmp("infoPoor.bandStyleId").setValue(d.bandStyleId.id);
					Ext.getCmp("infoPoor.bandStyleName").setValue(d.bandStyleName);
					Ext.getCmp("infoPoor.bandChannelID").setValue(d.bandChannelID.id);
					Ext.getCmp("infoPoor.bandChannelName").setValue(d.bandChannelName);
					Ext.getCmp("infoPoor.webSite").setValue(d.webSite);
					
					
					var p = Ext.util.JSON.decode(b.response.responseText).picture;
					var d = Ext.getCmp("infoPoor.picuurepathid");	
						for(var i=0;i<p.length;i++){
						if (d.getValue() != "") {
							d.setValue(d.getValue() + ",");
						}
						d.setValue(d.getValue() + p[i].fileAttach.fileId);
						var gg = Ext.getCmp("LogoPanel");
						Ext.DomHelper.append(gg.body, '<span><img src="' + "" +  __ctxPath +p[i].fileAttach.filePath +
							'"  width="100" height="100" /><img class="img-delete" src="' + __ctxPath +
							'/images/system/delete.gif" onclick="ScoreManageForm.removeFile(this,' + p[i].fileAttach.fileId + ')"/>&nbsp;|&nbsp;</span>');
						}
					},
					failure : function (a, b) {
						Ext.ux.Toast.msg("编辑", "载入失败");
					}
				});
			}
			this.buttons = [{
					text : "保存",
					iconCls : "btn-save",
					handler : function () {
						var a = Ext.getCmp("ScoreManageForm");
						if (a.getForm().isValid()) {
							a.getForm().submit({
								method : "post",
								waitMsg : "正在提交数据...",
								success : function (b, c) {
									Ext.ux.Toast.msg("操作信息", "成功保存信息！");
									Ext.getCmp("ScoreManageGrid").getStore().reload();
									Ext.getCmp("ScoreManageFormWin").close();
								},
								failure : function (b, c) {
									Ext.MessageBox.show({
										title : "操作信息",
										msg : "信息保存出错，请联系管理员！",
										buttons : Ext.MessageBox.OK,
										icon : "ext-mb-error"
									});
									Ext.getCmp("ScoreManageFormWin").close();
								}
							});
						} else {
							Ext.MessageBox.show({
								title : "操作信息",
								msg : "红线框的为必填字段！",
								buttons : Ext.MessageBox.OK,
								icon : "ext-mb-error"
							});
						}
					}
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
		}
	});
ScoreManageForm.removeFile = function (e, a) {
	
	var b = Ext.getCmp("infoPoor.picuurepathid");
	var d = b.getValue();
	if (d.indexOf(",") < 0) {
	b.setValue("");
	} else {
	d = d.replace("," + a, "").replace(a + ",", "");
	b.setValue(d);
	}
	var c = Ext.get(e.parentNode);
	c.remove();
};
