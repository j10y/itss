UnScoreManageForm = Ext.extend(Ext.Window, {
		formPanel : null,
		constructor : function (a) {
			Ext.applyIf(this, a);
			this.initUIComponents();
			UnScoreManageForm.superclass.constructor.call(this, {
				layout : "fit",
				id : "UnScoreManageFormWin",
				iconCls : "menu-ScoreManage",
				items : this.formPanel,
				title : "不可评分品牌信息录入",
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
					url : __ctxPath + "/bandpoor/saveScoreManageUn.do",
					layout : "form",
					id : "UnScoreManageForm",
					frame : false,
					defaults : {
						width : 300,
						anchor : "98%,98%",
						labelWidth : 80
					},
					formId : "UnScoreManageFormId",
					defaultType : "textfield",
					items : [{
							name : "infoPoor.id",
							id : "infoPoorForm.id",
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
													id : "infoPoorForm.bandId",
													xtype : "hidden"
												}, {
													xtype : 'container',
													layout : 'hbox',
													style : "margin-bottom:5px",
													items:[{
															xtype : 'label',
															text : '品牌名称:',
															width : 85
														},{
														fieldLabel : "品牌名称",
														name : "infoPoor.bandName",
														id : "infoPoorForm.bandName",
														maxHeight : 200,
														width : 170,
														xtype : "combo",
														mode : "local",
														editable : true,
														allowBlank : false,
														triggerAction : "all",
														valueField : "fromBandId",
														displayField : "fromBandName",
														store : new Ext.data.SimpleStore({
															url : __ctxPath
															 + "/bandpoor/getBandsScoreManageUn.do",
															fields : [
																"fromBandId",
																"fromBandName"]
														}),
														listeners : {
															focus : function (b) {
																var a = Ext.getCmp("infoPoorForm.bandName").getStore();
																	Ext.Ajax.request({
																		url : __ctxPath + "/bandpoor/getBandsScoreManageUn.do",
																		method : "post",
																		success : function (d) {
																			var c = Ext.util.JSON.decode(d.responseText);
																			a.loadData(c);
																		}
																	});
															},
															select:function(e,c,d){
																var bandId=Ext.getCmp("infoPoorForm.bandName").getValue();
																Ext.getCmp("infoPoorForm.bandId").setValue(bandId);
															}
														}
													}, {
														xtype:'button',
														text : '添加',
														handler : function(){
															new BandForm({bandStatus:1,lockBandStatus:true}).show();
														}
													}]
												}, {
													fieldLabel : "主力商品",
													name : "infoPoor.mainProductName",
													id : "infoPoorForm.mainProductName",
													xtype : "textfield",
													width : 170,
													allowBlank : false
												}, {
													xtype : "container",
													layout : 'hbox',
													style : "margin-bottom:5px",
													items : [
														{
															xtype : "label",
															text : "主力价格段：",
															width : 85
														}, {
															name : "infoPoor.mainPriceStart",
															id : "infoPoorForm.mainPriceStart",
															xtype : "numberfield",
															width : 73
														}, {
															xtype : "label",
															text : "~",
															width : 10
														}, {
															name : "infoPoor.mainPriceEnd",
															id : "infoPoorForm.mainPriceEnd",
															xtype : "numberfield",
															width : 73
														}, {
															xtype : "label",
															text : "元",
															width : 14
														}
													]
												}, {
													name : "infoPoor.saleStoreid.id",
													id : "infoPoorForm.saleStoreid",
													xtype : "hidden"
												}, {
													fieldLabel : "销售场所",
													name : "infoPoor.saleStoreName",
													id : "infoPoorForm.saleStoreName",
													maxHeight : 200,
													xtype : "combo",
													mode : "local",
													editable : true,
													triggerAction : "all",
													valueField : "saleStoreid",
													displayField : "saleStoreName",
													readOnly :true,
													store : new Ext.data.SimpleStore({
														url : __ctxPath
														 + "/bandpoor/getSaleStoreScoreManageUn.do",
														fields : [
															"saleStoreid",
															"saleStoreName"]
													}),
													listeners : {
														/*focus : function (b) {
														
															var a = Ext.getCmp("infoPoorForm.saleStoreName").getStore();
															if (a.getCount() <= 0) {
																Ext.Ajax.request({
																	url : __ctxPath + "/bandpoor/getSaleStoreScoreManageUn.do",
																	method : "post",
																	success : function (d) {
																		var c = Ext.util.JSON.decode(d.responseText);
																		a.loadData(c);
																	}
																});
															}
														},
														select:function(e,c,d){
															var saleStoreid=Ext.getCmp("infoPoorForm.saleStoreName").getValue();
															var saleStoreName=Ext.getCmp("infoPoorForm.saleStoreName").getRawValue();
															if(saleStoreName&&saleStoreName=="其他"){
																Ext.getCmp("infoPoorForm.saleSroteDesc").allowBlank = false;
															}else{
																Ext.getCmp("infoPoorForm.saleSroteDesc").clearInvalid();
																Ext.getCmp("infoPoorForm.saleSroteDesc").allowBlank = true;
															}
															Ext.getCmp("infoPoorForm.saleStoreid").setValue(saleStoreid);
														}*/
													},
													width : 170
												}, {
													name : "infoPoor.bandChannelID.id",
													id : "infoPoorForm.bandChannelID",
													xtype : "hidden"
												}, {
													fieldLabel : "销售渠道",
													name : "infoPoor.bandChannelName",
													id : "infoPoorForm.bandChannelName",
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
														 + "/bandpoor/getChannelScoreManageUn.do",
														fields : [
															"bandChannelID",
															"bandChannelName"]
													}),
													listeners : {
														focus : function (b) {
															var a = Ext.getCmp("infoPoorForm.bandChannelName").getStore();
															if (a.getCount() <= 0) {
																Ext.Ajax.request({
																	url : __ctxPath + "/bandpoor/getChannelScoreManageUn.do",
																	method : "post",
																	success : function (d) {
																		var c = Ext.util.JSON.decode(d.responseText);
																		a.loadData(c);
																	}
																});
															}
														},
														select:function(e,c,d){
															var bandChannelID=Ext.getCmp("infoPoorForm.bandChannelName").getValue();
															Ext.getCmp("infoPoorForm.bandChannelID").setValue(bandChannelID);
														}
													},
													width : 170
												}, {
													fieldLabel : "产地",
													name : "infoPoor.companyAddress",
													id : "infoPoorForm.companyAddress",
													xtype : "textfield",
													width : 170,
													allowBlank : false
												}, {
													fieldLabel : "产品线",
													name : "infoPoor.productLine",
													id : "infoPoorForm.productLine",
													xtype : "textfield",
													width : 170,
													allowBlank : false
												}, {
													fieldLabel : "品牌介绍",
													name : "infoPoor.bandDesc",
													id : "infoPoorForm.bandDesc",
													xtype : "textarea",
													width : 170,
													allowBlank : false
												}
											]
										}, {
											layout : "form",
											columnWidth : 0.5,
											border : false,
											style : "padding-left:0px;",
											items : [{
													name : "infoPoor.proClassId.id",
													id : "infoPoorForm.proClassId",
													xtype : "hidden"
												}, {
													fieldLabel : "品类归属",
													name : "infoPoor.proClassName",
													id : "infoPoorForm.proClassName",
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
														 + "/bandpoor/getProClassScoreManageUn.do",
														fields : [
															"fromProClassId",
															"fromProClassName"]
													}),
													listeners : {
														focus : function (b) {
															var a = Ext.getCmp("infoPoorForm.proClassName").getStore();
															if (a.getCount() <= 0) {
																Ext.Ajax.request({
																	url : __ctxPath + "/bandpoor/getProClassScoreManageUn.do",
																	method : "post",
																	success : function (d) {
																		var c = Ext.util.JSON.decode(d.responseText);
																		a.loadData(c);
																	}
																});
															}
														},
														select:function(e,c,d){
															var proClassId=Ext.getCmp("infoPoorForm.proClassName").getValue();
															Ext.getCmp("infoPoorForm.proClassId").setValue(proClassId);
														}
													},
													width : 170
												},  {
													fieldLabel : "官网",
													width : 170,
													name : "infoPoor.webSite",
													xtype : "textfield",
													id : "infoPoorForm.webSite"
												},  {
													fieldLabel : "联系人",
													width : 170,
													name : "infoPoor.contactUser",
													xtype : "textfield",
													id : "infoPoorForm.contactUser"
												},  {
													fieldLabel : "销售场所描述",
													width : 170,
													name : "infoPoor.saleSroteDesc",
													xtype : "textfield",
													id : "infoPoorForm.saleSroteDesc"
												},  {
													fieldLabel : "联系方式",
													width : 170,
													name : "infoPoor.contactPhone",
													xtype : "textfield",
													id : "infoPoorForm.contactPhone"
												},  {
													fieldLabel : "公司性质",
													width : 170,
													name : "infoPoor.companyNature",
													xtype : "textfield",
													allowBlank : false,
													id : "infoPoorForm.companyNature"
												}, {
													name : "infoPoor.picuurepathid",
													id : "infoPoorForm.picuurepathid",
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
																	var d = Ext.getCmp("infoPoorForm.picuurepathid");	
																	for (var e = 0; e < g.length; e++) {
																		if (d.getValue() != "") {
																			d.setValue(d.getValue() + ",");
																		}
																		d.setValue(d.getValue() + g[e].fileId);
																		var gg = Ext.getCmp("LogoPanel");
																		Ext.DomHelper.append(gg.body, '<span><img src="' + "" +  __ctxPath +g[e].filepath +
																			'"  width="100" height="100" /><img class="img-delete" src="' + __ctxPath +
																			'/images/system/delete.gif" onclick="UnScoreManageForm.removeFile(this,' + g[e].fileId + ')"/>&nbsp;|&nbsp;</span>');
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
					url : __ctxPath + "/bandpoor/getScoreManageUn.do?id=" + this.id,
					waitMsg : "正在载入数据...",
					success : function (a, b) {
					var d = Ext.util.JSON.decode(b.response.responseText).data[0];
					Ext.getCmp("infoPoorForm.id").setValue(d.id);
					Ext.getCmp("infoPoorForm.bandId").setValue(d.bandId.id);
					Ext.getCmp("infoPoorForm.bandName").setValue(d.bandName);
					Ext.getCmp("infoPoorForm.mainPriceStart").setValue(d.mainPriceStart);
					Ext.getCmp("infoPoorForm.mainPriceEnd").setValue(d.mainPriceEnd);
					Ext.getCmp("infoPoorForm.mainProductName").setValue(d.mainProductName);
					Ext.getCmp("infoPoorForm.bandChannelID").setValue(d.bandChannelID.id);
					Ext.getCmp("infoPoorForm.bandChannelName").setValue(d.bandChannelName);
					Ext.getCmp("infoPoorForm.companyAddress").setValue(d.companyAddress);
					Ext.getCmp("infoPoorForm.companyAddress").setValue(d.companyAddress);
					Ext.getCmp("infoPoorForm.productLine").setValue(d.productLine);
					Ext.getCmp("infoPoorForm.bandDesc").setValue(d.bandDesc);
					
					
					Ext.getCmp("infoPoorForm.proClassId").setValue(d.proClassId.id);
					Ext.getCmp("infoPoorForm.proClassName").setValue(d.proClassName);
					Ext.getCmp("infoPoorForm.webSite").setValue(d.webSite);
					Ext.getCmp("infoPoorForm.contactUser").setValue(d.contactUser);
					Ext.getCmp("infoPoorForm.saleSroteDesc").setValue(d.saleSroteDesc);
					Ext.getCmp("infoPoorForm.contactPhone").setValue(d.contactPhone);
					Ext.getCmp("infoPoorForm.companyNature").setValue(d.companyNature);
					
					//Ext.getCmp("infoPoorForm.saleStoreid").setValue(d.saleStoreid.id);
					Ext.getCmp("infoPoorForm.saleStoreName").setValue(d.saleStoreName);
					if(d.saleStoreName&&d.saleStoreName=="其他"){
						Ext.getCmp("infoPoorForm.saleSroteDesc").allowBlank = false;
					}
					
					
					var p = Ext.util.JSON.decode(b.response.responseText).picture;
					var d = Ext.getCmp("infoPoorForm.picuurepathid");	
						for(var i=0;i<p.length;i++){
						if (d.getValue() != "") {
							d.setValue(d.getValue() + ",");
						}
						d.setValue(d.getValue() + p[i].fileAttach.fileId);
						var gg = Ext.getCmp("LogoPanel");
						Ext.DomHelper.append(gg.body, '<span><img src="' + "" +  __ctxPath +p[i].fileAttach.filePath +
							'"  width="100" height="100" /><img class="img-delete" src="' + __ctxPath +
							'/images/system/delete.gif" onclick="UnScoreManageForm.removeFile(this,' + p[i].fileAttach.fileId + ')"/>&nbsp;|&nbsp;</span>');
						}
					},
					failure : function (a, b) {
						Ext.ux.Toast.msg("编辑", "载入失败");
					}
				});
			}else{
				Ext.getCmp("infoPoorForm.saleStoreName").setValue("其他");
				Ext.getCmp("infoPoorForm.saleStoreid").setValue("");
			}
			this.buttons = [{
					text : "保存",
					iconCls : "btn-save",
					handler : function () {
						var a = Ext.getCmp("UnScoreManageForm");
						if (a.getForm().isValid()) {
							var minValue = Ext.getCmp("infoPoorForm.mainPriceStart").getValue();
							var maxValue = Ext.getCmp("infoPoorForm.mainPriceEnd").getValue();
							if(minValue >= maxValue) {
								Ext.MessageBox.show({
									title : "操作信息",
									msg : "主力价格段信息填写有误，请核实！",
									buttons : Ext.MessageBox.OK,
									icon : "ext-mb-error"
								});
								return ;
							}
							a.getForm().submit({
								method : "post",
								waitMsg : "正在提交数据...",
								success : function (b, c) {
									Ext.ux.Toast.msg("操作信息", "成功保存信息！");
									Ext.getCmp("UnScoreManageGrid").getStore().reload();
									Ext.getCmp("UnScoreManageFormWin").close();
								},
								failure : function (b, c) {
									Ext.MessageBox.show({
										title : "操作信息",
										msg : c.result.msg,
										buttons : Ext.MessageBox.OK,
										icon : "ext-mb-error"
									});
									Ext.getCmp("UnScoreManageFormWin").close();
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
UnScoreManageForm.removeFile = function (e, a) {
	
	var b = Ext.getCmp("infoPoorForm.picuurepathid");
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
