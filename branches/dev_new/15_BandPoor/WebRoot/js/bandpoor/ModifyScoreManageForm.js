ModifyScoreManageForm = Ext.extend(Ext.Window, {
		formPanel : null,
		constructor : function (a) {
			Ext.applyIf(this, a);
			this.initUIComponents();
			ModifyScoreManageForm.superclass.constructor.call(this, {
				layout : "fit",
				id : "ModifyScoreManageFormWin",
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
					id : "ModifyScoreManageForm",
					frame : false,
					defaults : {
						width : 300,
						anchor : "98%,98%",
						labelWidth : 80
					},
					formId : "ModifyScoreManageFormId",
					defaultType : "textfield",
					items : [{
							name : "infoPoor.id",
							id : "infoPoorModify.id",
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
													id : "infoPoorModify.bandId",
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
														id : "infoPoorModify.bandName",
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
															 + "/bandpoor/getAllBandsScoreManage.do",
															fields : [
																"fromBandId",
																"fromBandName"]
														}),
														listeners : {
															focus : function (b) {
																var a = Ext.getCmp("infoPoorModify.bandName").getStore();
																Ext.Ajax.request({
																	url : __ctxPath + "/bandpoor/getAllBandsScoreManage.do",
																	method : "post",
																	success : function (d) {
																		var c = Ext.util.JSON.decode(d.responseText);
																		a.loadData(c);
																	}
																});
															},
															select:function(e,c,d){
																var bandId=Ext.getCmp("infoPoorModify.bandName").getValue();
																Ext.getCmp("infoPoorModify.bandId").setValue(bandId);
															}
														}
													}, {
														xtype:'button',
														text : '添加',
														handler : function(){
															new BandForm({bandStatus:0,lockBandStatus:true}).show();
														}
													}]
													
												}, {
													name : "infoPoor.floorNumId.id",
													id : "infoPoorModify.floorNumId",
													xtype : "hidden"
												}, {
													fieldLabel : "楼层",
													name : "infoPoor.floorNumName",
													id : "infoPoorModify.floorNumName",
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
															var a = Ext.getCmp("infoPoorModify.floorNumName").getStore();
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
															var floorNumId=Ext.getCmp("infoPoorModify.floorNumName").getValue();
															Ext.getCmp("infoPoorModify.floorNumId").setValue(floorNumId);
														}
													}
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
															id : "infoPoorModify.mainPriceStart",
															xtype : "numberfield",
															width : 60
														}, {
															xtype : "label",
															text : "~",
															width : 10
														}, {
															name : "infoPoor.mainPriceEnd",
															id : "infoPoorModify.mainPriceEnd",
															xtype : "numberfield",
															width : 60
														}, {
															xtype : "label",
															text : "元",
															width : 10
														}
													]
												}, {
													name : "infoPoor.saleStoreid.id",
													id : "infoPoorModify.saleStoreid",
													xtype : "hidden"
												}, {
													fieldLabel : "销售场所",
													name : "infoPoor.saleStoreName",
													id : "infoPoorModify.saleStoreName",
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
															var a = Ext.getCmp("infoPoorModify.saleStoreName").getStore();
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
															var saleStoreid=Ext.getCmp("infoPoorModify.saleStoreName").getValue();
															Ext.getCmp("infoPoorModify.saleStoreid").setValue(saleStoreid);
														}
													}
												}, {
													name : "infoPoor.bandBusinessAreaId.id",
													id : "infoPoorModify.bandBusinessAreaId",
													xtype : "hidden"
												}, {
													fieldLabel : "商圈",
													name : "infoPoor.bandBusinessAreaName",
													id : "infoPoorModify.bandBusinessAreaName",
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
															var a = Ext.getCmp("infoPoorModify.saleStoreName").getValue();
															if (a != null
																 && a != ""
																 && a != "undefined") {
																Ext.getCmp("infoPoorModify.bandBusinessAreaName").getStore().reload({
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
															var bandBusinessAreaId=Ext.getCmp("infoPoorModify.bandBusinessAreaName").getValue();
															Ext.getCmp("infoPoorModify.bandBusinessAreaId").setValue(bandBusinessAreaId);
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
													id : "infoPoorModify.proClassId",
													xtype : "hidden"
												}, {
													fieldLabel : "主力品类",
													name : "infoPoor.proClassName",
													id : "infoPoorModify.proClassName",
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
															var a = Ext.getCmp("infoPoorModify.proClassName").getStore();
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
															var proClassId=Ext.getCmp("infoPoorModify.proClassName").getValue();
															Ext.getCmp("infoPoorModify.proClassId").setValue(proClassId);
														}
													}
												}, {
													name : "infoPoor.bandStyleId.id",
													id : "infoPoorModify.bandStyleId",
													xtype : "hidden"
												}, {
													fieldLabel : "品牌风格",
													name : "infoPoor.bandStyleName",
													id : "infoPoorModify.bandStyleName",
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
															var a = Ext.getCmp("infoPoorModify.proClassName").getValue();
															if (a != null
																 && a != ""
																 && a != "undefined") {
																Ext.getCmp("infoPoorModify.bandStyleName").getStore().reload({
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
															var bandStyleId=Ext.getCmp("infoPoorModify.bandStyleName").getValue();
															Ext.getCmp("infoPoorModify.bandStyleId").setValue(bandStyleId);
														}
													}
												}, {
													name : "infoPoor.bandChannelID.id",
													id : "infoPoorModify.bandChannelID",
													xtype : "hidden"
												}, {
													fieldLabel : "销售渠道",
													name : "infoPoor.bandChannelName",
													id : "infoPoorModify.bandChannelName",
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
															var a = Ext.getCmp("infoPoorModify.bandChannelName").getStore();
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
															var bandChannelID=Ext.getCmp("infoPoorModify.bandChannelName").getValue();
															Ext.getCmp("infoPoorModify.bandChannelID").setValue(bandChannelID);
														}
													}
												}, {
													fieldLabel : "官网",
													width : 170,
													name : "infoPoor.webSite",
													xtype : "textfield",
													id : "infoPoorModify.webSite"
												},{
													name : "infoPoor.picuurepathid",
													id : "infoPoorModify.picuurepathid",
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
																	var d = Ext.getCmp("infoPoorModify.picuurepathid");	
																	for (var e = 0; e < g.length; e++) {
																		if (d.getValue() != "") {
																			d.setValue(d.getValue() + ",");
																		}
																		d.setValue(d.getValue() + g[e].fileId);
																		var gg = Ext.getCmp("LogoPanel");
																		Ext.DomHelper.append(gg.body, '<span><img src="' + "" +  __ctxPath +g[e].filepath +
																			'"  width="100" height="100" /><img class="img-delete" src="' + __ctxPath +
																			'/images/system/delete.gif" onclick="ModifyScoreManageForm.removeFile(this,' + g[e].fileId + ')"/>&nbsp;|&nbsp;</span>');
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
					Ext.getCmp("infoPoorModify.id").setValue(d.id);
					Ext.getCmp("infoPoorModify.bandId").setValue(d.bandId.id);
					Ext.getCmp("infoPoorModify.bandName").setValue(d.bandName);
					Ext.getCmp("infoPoorModify.floorNumId").setValue(d.floorNumId.id);
					Ext.getCmp("infoPoorModify.floorNumName").setValue(d.floorNumName);
					//Ext.getCmp("infoPoorModify.mainPriceId").setValue(d.mainPriceId.id);
					//Ext.getCmp("infoPoorModify.mainPriceName").setValue(d.mainPriceName);
					Ext.getCmp("infoPoorModify.mainPriceStart").setValue(d.mainPriceStart);
					Ext.getCmp("infoPoorModify.mainPriceEnd").setValue(d.mainPriceEnd);
					Ext.getCmp("infoPoorModify.saleStoreid").setValue(d.saleStoreid.id);
					Ext.getCmp("infoPoorModify.saleStoreName").setValue(d.saleStoreName);
					Ext.getCmp("infoPoorModify.bandBusinessAreaId").setValue(d.bandBusinessAreaId.id);
					Ext.getCmp("infoPoorModify.bandBusinessAreaName").setValue(d.bandBusinessAreaName);
					Ext.getCmp("infoPoorModify.proClassId").setValue(d.proClassId.id);
					Ext.getCmp("infoPoorModify.proClassName").setValue(d.proClassName);
					Ext.getCmp("infoPoorModify.bandStyleId").setValue(d.bandStyleId.id);
					Ext.getCmp("infoPoorModify.bandStyleName").setValue(d.bandStyleName);
					Ext.getCmp("infoPoorModify.bandChannelID").setValue(d.bandChannelID.id);
					Ext.getCmp("infoPoorModify.bandChannelName").setValue(d.bandChannelName);
					Ext.getCmp("infoPoorModify.webSite").setValue(d.webSite);
					
					
					var p = Ext.util.JSON.decode(b.response.responseText).picture;
					var d = Ext.getCmp("infoPoorModify.picuurepathid");	
						for(var i=0;i<p.length;i++){
						if (d.getValue() != "") {
							d.setValue(d.getValue() + ",");
						}
						d.setValue(d.getValue() + p[i].fileAttach.fileId);
						var gg = Ext.getCmp("LogoPanel");
						Ext.DomHelper.append(gg.body, '<span><img src="' + "" +  __ctxPath +p[i].fileAttach.filePath +
							'"  width="100" height="100" /><img class="img-delete" src="' + __ctxPath +
							'/images/system/delete.gif" onclick="ModifyScoreManageForm.removeFile(this,' + p[i].fileAttach.fileId + ')"/>&nbsp;|&nbsp;</span>');
						}
					},
					failure : function (a, b) {
						Ext.ux.Toast.msg("编辑", "载入失败");
					}
				});
			}else{
					Ext.getCmp("infoPoorModify.bandChannelID").setValue(1);
					Ext.getCmp("infoPoorModify.bandChannelName").setValue("实体店");
			}
			this.buttons = [{
					text : "保存",
					iconCls : "btn-save",
					handler : function () {
						var a = Ext.getCmp("ModifyScoreManageForm");
						if (a.getForm().isValid()) {
							a.getForm().submit({
								method : "post",
								waitMsg : "正在提交数据...",
								success : function (b, c) {
									Ext.ux.Toast.msg("操作信息", "成功保存信息！");
									Ext.getCmp("ModifyScoreManageGrid").getStore().reload();
									Ext.getCmp("ModifyScoreManageFormWin").close();
								},
								failure : function (b, c) {
									Ext.MessageBox.show({
										title : "操作信息",
										msg : c.result.msg,
										buttons : Ext.MessageBox.OK,
										icon : "ext-mb-error"
									});
									Ext.getCmp("ModifyScoreManageFormWin").close();
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
ModifyScoreManageForm.removeFile = function (e, a) {
	
	var b = Ext.getCmp("infoPoorModify.picuurepathid");
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
