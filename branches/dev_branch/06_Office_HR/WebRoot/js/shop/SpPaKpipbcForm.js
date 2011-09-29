SpPaKpipbcForm = Ext.extend(Ext.Window, {
	formPanel : null,
	gridPanel : null,
	store : null,
	topbar : null,
	constructor : function(a) {
		if(a == null) {
			a = {};
		}
		Ext.apply(this, a);
		this.initComponents();
		SpPaKpipbcForm.superclass.constructor.call(this, {
			id : "SpPaKpipbcFormWin",
			region : "center",
			layout : "border",
			items : [
				this.formPanel,
				this.gridPanel
			],
			modal : true,
			height : 625,
			width : 850,
			autoScroll : true,
			title : "考评项目录入/修改",
			buttonAlign : "center",
			buttons : this.buttons
		});
	},
	initComponents : function() {
		var a = __ctxPath + "/system/listDepartment.do?opt=appUser";
		var departments = new TreeSelector("spPaKpipbc.belongDept.depName", a, "所属部门", "spPaKpipbc.belongDept.depId");
		departments.addListener("expand", function() {
			Ext.getCmp("spPaKpipbc.belongDept.depNameTree").addListener("click", function() {
				var c = Ext.getCmp("belongPost").getStore();
				c.loadData("[]");
				Ext.getCmp("belongPost").setValue("");
				Ext.getCmp("belongPost").setRawValue("");
				Ext.getCmp("sp2UserPbc").setValue("");
				Ext.getCmp("sp2UserPbc").setRawValue("");
				Ext.Ajax.request({
					url : __ctxPath + "/hrm/comboJob.do",
					method : "post",
					params : {
						depId : Ext.getCmp("spPaKpipbc.belongDept.depId").getValue()
					},
					success : function(f) {
						var e = Ext.util.JSON.decode(f.responseText);
						c.loadData(e);
					}
				});
			});
		});
		this.formPanel = new Ext.FormPanel({
			region : "north",
			height : 145,
			id : "SpPaKpipbcForm",
			bodyStyle : "padding:20px 20px 20px 20px",
			frame : false,
			border : false,
			layout : "form",
			layoutConfig : {
				padding : "5",
				align : "middle"
			},
			url : __ctxPath + "/shop/saveSpPaKpipbc.do",
			id : "SpPaKpipbcForm",
			reader : new Ext.data.JsonReader({
				root : "data"
			},[
				{
					name : "spPaKpipbc.id",
					mapping : "id"
				}, {
					name : "spPaKpipbc.pbcName",
					mapping : "pbcName"
				}, {
					name : "spPaKpipbc.belongDept.depId",
					mapping : "belongDept.depId"
				}, {
					name : "spPaKpipbc.belongDept.depName",
					mapping : "belongDept.depName"
				}, {
					name : "spPaKpipbc.createDate",
					mapping : "createDate"
				}, {
					name : "spPaKpipbc.createPerson.userId",
					mapping : "createPerson.userId"
				}
			]),
			items : [
				{
					name : "spPaKpipbc.id",
					id : "pbcId",
					xtype : "hidden",
					value : this.pbcId == null ? "" : this.pbcId
				}, {
					name : "spPaKpipbc.belongDept.depId",
					id : "spPaKpipbc.belongDept.depId",
					xtype : "hidden"
				}, {
					fieldLabel : "创建时间",
					name : "spPaKpipbc.createDate",
					id : "createDate",
					xtype : "hidden"
				}, {
					fieldLabel : "创建人",
					name : "spPaKpipbc.createPerson.userId",
					id : "createPerson",
					xtype : "hidden"
				}, {
					fieldLabel : "状态",
					name : "spPaKpipbc.publishStatus",
					id : "publishStatus",
					xtype : "hidden"
				}, 
				{
					fieldLabel : "考核指标列表",
					name : "spPaKpiitems",
					id : "spPaKpiitems",
					xtype : "hidden"
				}, {
					xtype : "container",
					border : false,
					layout : "column",
					items : [
						{
							layout : "form",
							border : false,
							columnWidth : 0.5,
							items : [
								{
									fieldLabel : "项目名称",
									name : "spPaKpipbc.pbcName",
									id : "pbcName",
									xtype : "textfield",
									width : 165,
									allowBlank : false,
									blankText : "项目名称不能为空！"
								},
								departments,
								{
									fieldLabel : "指向HR考核PBC",
									maxHeight : 200,
									id : "sp2UserPbc",
									xtype : "combo",
									mode : "local",
									editable : false,
									valueField : "id",
									displayField : "pbcName",
									triggerAction : "all",
									store : new Ext.data.JsonStore({
										root : "result",
										remoteSort : true,
										fields : ["id", "pbcName"]
									}),
									listeners : {
										focus : function() {
											var c = Ext.getCmp("sp2UserPbc").getStore();
											var fId = Ext.getCmp("frequency").getValue();
											var depId = Ext.getCmp("spPaKpipbc.belongDept.depId").getValue();
											if(fId != "" && depId != null) {
												Ext.Ajax.request({
													url : __ctxPath + "/shop/comboUserPbcSpPaKpipbc.do",
													method : "post",
													params : {
														frequencyId : fId,
														depId : depId
													},
													success : function(f) {
														var e = Ext.util.JSON.decode(f.responseText);
														c.loadData(e);
													}
												});
											}
										},
										select : function() {
											Ext.getCmp("fromPi").setValue("");
											Ext.getCmp("fromPi").setRawValue("");
										}
									}
								}, {
									fieldLabel : "项目类型",
									hiddenName : "spPaKpipbc.pbcType.id",
									maxHeight : 200,
									id : "pbcType",
									xtype : "combo",
									mode : "local",
									editable : false,
									valueField : "id",
									displayField : "name",
									triggerAction : "all",
									allowBlank : false,
									blankText : "PBC类型不能为空！",
									store : new Ext.data.SimpleStore({
										remoteSort : true,
										fields : ["id","name"]
									}),
									listeners : {
										focus : function(d) {
											var c = Ext.getCmp("pbcType").getStore();
											if(c.getCount() <= 0) {
												Ext.Ajax.request({
													url : __ctxPath + "/shop/loadSpPaDatadictionary.do",
													method : "post",
													params : {
														parentId : 15          //PBC类型的根节点ID
													},
													success : function(f) {
														var e = Ext.util.JSON.decode(f.responseText);
														c.loadData(e);
													}
												});
											}
										}
									}
								}
							]
						}, {
							layout : "form",
							border : false,
							columnWidth : 0.5,
							items : [
								{
									fieldLabel : "考核频度",
									hiddenName : "spPaKpipbc.frequency.id",
									name : "spPaKpipbc.frequency.name",
									maxHeight : 200,
									id : "frequency",
									xtype : "combo",
									mode : "local",
									editable : false,
									valueField : "id",
									displayField : "name",
									triggerAction : "all",
									allowBlank : false,
									blankText : "PBC类型不能为空！",
									store : new Ext.data.SimpleStore({
										fields : ["id","name"]
									}),
									listeners : {
										focus : function(d) {
											var c = Ext.getCmp("frequency").getStore();
											if(c.getCount() <= 0) {
												Ext.Ajax.request({
													url : __ctxPath + "/shop/loadSpPaDatadictionary.do",
													method : "post",
													params : {
														parentId : 3           //考核频度的根节点ID
													},
													success : function(f) {
														var e = Ext.util.JSON.decode(f.responseText);
														c.loadData(e);
													}
												});
											}
										},
										select : function() {
											Ext.getCmp("sp2UserPbc").setValue("");
											Ext.getCmp("sp2UserPbc").setRawValue("");
										}
									}
								}, {
									fieldLabel : "所属岗位",
									hiddenName : "spPaKpipbc.belongPost.jobId",
									maxHeight : 200,
									id : "belongPost",
									xtype : "combo",
									mode : "local",
									editable : false,
									valueField : "id",
									displayField : "name",
									triggerAction : "all",
									allowBlank : false,
									blankText : "PBC类型不能为空！",
									store : new Ext.data.SimpleStore({
										fields : ["id","name"]
									}),
									listeners : {
										focus : function(d) {
											var c = Ext.getCmp("belongPost").getStore();
											if(c.getCount() <= 0) {
												Ext.Ajax.request({
													url : __ctxPath + "/hrm/comboJob.do",
													method : "post",
													params : {
														depId : Ext.getCmp("spPaKpipbc.belongDept.depId").getValue()
													},
													success : function(f) {
														var e = Ext.util.JSON.decode(f.responseText);
														c.loadData(e);
													}
												});
											}
										}
									}
								}, {
									fieldLabel : "指向HR考核指标",
									hiddenName : "spPaKpipbc.fromPi",
									name : "spPaKpipbc.fromPi.paName",
									maxHeight : 200,
									id : "fromPi",
									xtype : "combo",
									mode : "local",
									editable : false,
									valueField : "id",
									displayField : "paName",
									triggerAction : "all",
									allowBlank : false,
									store : new Ext.data.JsonStore({
										root : "result",
										remoteSort : true,
										fields : ["id", "paName"]
									}),
									listeners : {
										focus : function() {
											var c = Ext.getCmp("fromPi").getStore();
											var pbcId = Ext.getCmp("sp2UserPbc").getValue();
											if(pbcId != "") {
												Ext.Ajax.request({
													url : __ctxPath + "/shop/comboPbcItemSpPaKpipbc.do",
													method : "post",
													params : {
														pbcId : pbcId
													},
													success : function(f) {
														var e = Ext.util.JSON.decode(f.responseText);
														c.loadData(e);
													}
												});
											}
										}
									}
								}
							]
						}
					]
				}
			]
		});
		var pbcId = (this.pbcId == null || this.pbcId == "undefined") ? 0 : this.pbcId;
		this.store = new Ext.data.JsonStore({
			url : __ctxPath + "/shop/listSpPaKpiitem.do?Q_pbc.id_L_EQ=" + pbcId,
			id : "id",
			root : "result",
			remoteSort : true,
			fields : [
				{
					name : "id",
					type : "int"
				}, {
					name : "piId",
					mapping : "pi.id"
				}, {
					name : "pi.paName",
					mapping : "pi.paName"
				}
			]
		});
		this.store.setDefaultSort("id", "asc");
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
					header : "piId",
					dataIndex : "piId",
					hidden : true
				}, {
					header : "考核指标名称",
					dataIndex : "pi.paName",
					width : 250
				}
			],
			defaults : {
				sortable : true,
				menuDisable : false
			}
		});
		this.topbar = new Ext.Toolbar({
			height : 30,
			bodyStyle : "text-align:left",
			items : []
		});
		if(isGranted("_PbcItemAdd")) {
			this.topbar.add({
				iconCls : "btn-add",
				text : "添加考核指标",
				xtype : "button",
				handler : this.addSpPaKpiitem
			});
		}
		if(isGranted("_PbcItemDel")) {
			this.topbar.add({
				iconCls : "btn-del",
				text : "删除考核指标",
				xtype : "button",
				handler : this.delSpPaKpiitem
			});
		}
		this.gridPanel = new Ext.grid.EditorGridPanel({
			id : "SpPaKpiitemGrid",
			region : "center",
			autoWidth : true,
			autoHeight : true,
			stripeRows : true,
			autoScroll : true,
			tbar : this.topbar,
			closeable : true,
			store : this.store,
			trackMouseOver : true,
			disableSelection : false,
			loadMask : true,
			cm : a,
			sm : c,
			viewConfig : {
				forceFit : true,
				enableRowBody : false,
				showPreview : false
			},
			bbar : new Ext.PagingToolbar({
				pageSize : 100,
				store : this.store,
				displayInfo : true,
				displayMsg : "当前显示{0}至{1}，共{2}条记录",
				emptyMsg : "当前没有记录"
			})
		});
		this.gridPanel.addListener("rowdblclick", function(f, d, g) {
			f.getSelectionModel().each(function(e) {
				
			});
		});
		if(this.pbcId != null && this.pbcId != "undefined") {
			this.formPanel.getForm().load({
				deferredRender : false,
				url : __ctxPath + "/shop/getSpPaKpipbc.do?id=" + this.pbcId,
				waitMsg : "正在载入数据……",
				success : function(f, d) {
					var e = Ext.util.JSON.decode(d.response.responseText);
					Ext.getCmp("frequency").setValue(e.data.frequency.id);
					Ext.getCmp("frequency").setRawValue(e.data.frequency.name);
					Ext.getCmp("belongPost").setValue(e.data.belongPost.jobId);
					Ext.getCmp("belongPost").setRawValue(e.data.belongPost.jobName);
					Ext.getCmp("sp2UserPbc").setValue(e.pbcId);
					Ext.getCmp("sp2UserPbc").setRawValue(e.pbcName);
					Ext.getCmp("fromPi").setValue(e.data.fromPi);
					Ext.getCmp("fromPi").setRawValue(e.paName);
					Ext.getCmp("pbcType").setValue(e.data.pbcType.id);
					Ext.getCmp("pbcType").setRawValue(e.data.pbcType.name);
				},
				failure : function() {
					
				}
			});
		}
		this.buttons = [
			{
				text : "取消",
				handler : this.cancel.createCallback(this)
			}, {
				text : "保存草稿",
				handler : this.saveAsDraft.createCallback(this.formPanel, this)
			}, {
				text : "确认发布",
				handler : this.saveToPublish.createCallback(this.formPanel, this)
			}
		];
	},
	addSpPaKpiitem : function() {
		var fId = Ext.getCmp("frequency").getValue();
		var depId = Ext.getCmp("spPaKpipbc.belongDept.depId").getValue();
		//判断是否已选择部门
		if(isNaN(depId) || depId <= 0) {
			Ext.MessageBox.show({
				title : "操作信息",
				msg : "请选择部门！",
				buttons : Ext.MessageBox.OK,
				icon : Ext.MessageBox.ERROR
			});
			return ;
		}
		//判断是否已选择考核频度
		if(isNaN(fId) || fId <= 0) {
			Ext.MessageBox.show({
				title : "操作信息",
				msg : "请选择正确的考核频度！",
				buttons : Ext.MessageBox.OK,
				icon : Ext.MessageBox.ERROR
			});
			return ;
		}
		new SpPaPiView({
			frequencyId : Ext.getCmp("frequency").getValue(),
			depId : Ext.getCmp("spPaKpipbc.belongDept.depId").getValue()
		}).show();
	},
	delSpPaKpiitem : function() {
		var e = Ext.getCmp("SpPaKpiitemGrid");
		var c = e.getSelectionModel().getSelections();
		if(c.length == 0) {
			Ext.ux.Toast.msg("提示信息", "请选择要删除的记录！");
			return ;
		}
		//只是前台页面删除，数据库并不真正删除
		var allStore = e.getStore();
		Ext.each(c, function(item) {
			allStore.remove(item);
		});
	},
	cancel : function(b) {
		b.close();
	},
	saveAsDraft : function(a, b) {
		if(a.getForm().isValid()) {
			Ext.getCmp("publishStatus").setValue(0);
			//将考核项拼装成规定格式字符串
			var items = "";
			var gridStore = b.gridPanel.getStore();
			for(var j = 0; j < gridStore.getCount(); j++) {
				var item = gridStore.getAt(j).data;
				items += item.id + "," + item.piId + " ";
			};
			items = items.substr(0, items.length - 1);
			Ext.getCmp("spPaKpiitems").setValue(items);
			a.getForm().submit({
				method : "post",
				waitMsg : "正在提交数据……",
				success : function(c, d) {
					Ext.ux.Toast.msg("提示信息", "保存草稿成功！");
					if(b.from == "draft") {
						Ext.getCmp("DraftSpPaKpipbcView").gridPanel.store.reload({
							params : {
								start : 0,
								limit : 25
							}
						});
					} else if(b.from == "publish") {
						Ext.getCmp("PublishSpPaKpipbcView").gridPanel.store.reload({
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
						msg : "信息录入有误，请核实！",
						buttons : Ext.MessageBox.OK,
						icon : Ext.MessageBox.ERROR
					});
					return ;
				}
			});
		}
	},
	saveToPublish : function(a, b) {
		if(a.getForm().isValid()) {
			Ext.getCmp("publishStatus").setValue(1);
			var gridStore = b.gridPanel.getStore();
			if(gridStore.getCount() <= 0) {
				Ext.MessageBox.show({
					title : "操作信息",
					msg : "请添加考核指标！",
					buttons : Ext.MessageBox.OK,
					icon : Ext.MessageBox.ERROR
				});
				return ;
			}
			var items = "";
			for(var j = 0; j < gridStore.getCount(); j++) {
				var item = gridStore.getAt(j).data;
				items += item.id + "," + item.piId + " ";
			};
			items = items.substr(0, items.length - 1);
			Ext.getCmp("spPaKpiitems").setValue(items);
			a.getForm().submit({
				method : "post",
				waitMsg : "正在提交数据……",
				success : function(c, d) {
					Ext.ux.Toast.msg("提示信息", "成功发布！");
					if(b.from == "draft") {
						Ext.getCmp("DraftSpPaKpipbcView").gridPanel.store.reload({
							params : {
								start : 0,
								limit : 25
							}
						});
					} else if(b.from == "publish") {
						Ext.getCmp("PublishSpPaKpipbcView").gridPanel.store.reload({
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
						msg : "信息录入有误，请核实！",
						buttons : Ext.MessageBox.OK,
						icon : Ext.MessageBox.ERROR
					});
					return ;
				}
			});
		}
	}
});