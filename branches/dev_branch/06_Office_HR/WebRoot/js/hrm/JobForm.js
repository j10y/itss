JobForm = Ext.extend(Ext.Window, {
	formPanel : null,
	constructor : function(a) {
		if (a == null) {
			a = {};
		}
		Ext.apply(this, a);
		this.initComponents();
		JobForm.superclass.constructor.call(this, {
			id : "JobFormWin",
			layout : "fit",
			iconCls : "menu-job",
			items : this.formPanel,
			modal : true,
			height : 470,
			width : 700,
			maximizable : true,
			title : "岗位详细信息",
			buttonAlign : "center",
			buttons : this.buttons
		});
	},
	initComponents : function() {
		
		var a = __ctxPath + "/system/listDepartment.do?opt=appUser";
		this.jobDepartmentName = new TreeSelector("jobDepartmentName", a,
				"所属部门", "depId", false);
		this.formPanel = new Ext.FormPanel({
			layout : "form",
			bodyStyle : "padding:10px 10px 10px 10px",
			border : false,
			url : __ctxPath + "/hrm/saveJob.do",
			id : "JobForm",
			defaults : {
				anchor : "98%,98%"
			},
			defaultType : "textfield",
			items : [ {
				name : "job.jobId",
				id : "jobId",
				xtype : "hidden",
				value : this.jobId == null ? "" : this.jobId
			}, {
				fieldLabel : "岗位名称",
				name : "job.jobName",
				id : "jobName",
				allowBlank : false,
				blankText : "岗位名称不能为空!"
			}, {
				fieldLabel : "所属部门",
				name : "job.depId",
				id : "depId",
				xtype : "hidden"
			}, this.jobDepartmentName, {
				fieldLabel : "band",
				hiddenName : "job.band.id",
				maxHeight : 200,
				id : "band",
				xtype : "combo",
				mode : "local",
				editable : false,
				valueField : "id",
				displayField : "name",
				triggerAction : "all",
				store : new Ext.data.SimpleStore({
					fields : ["id","name"]
				}),
				listeners : {
					focus : function(d) {
						var c = Ext.getCmp("band").getStore();
						if(c.getCount() <= 0) {
							Ext.Ajax.request({
								url : __ctxPath + "/kpi/loadHrPaDatadictionary.do",
								method : "post",
								params : {
									parentId : 24
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
				fieldLabel : "族群",
				hiddenName : "job.race.id",
				maxHeight : 200,
				id : "race",
				xtype : "combo",
				mode : "local",
				editable : false,
				valueField : "id",
				displayField : "name",
				triggerAction : "all",
				store : new Ext.data.SimpleStore({
					fields : ["id","name"]
				}),
				listeners : {
					focus : function(d) {
						var c = Ext.getCmp("race").getStore();
						if(c.getCount() <= 0) {
							Ext.Ajax.request({
								url : __ctxPath + "/kpi/loadHrPaDatadictionary.do",
								method : "post",
								params : {
									parentId : 18
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
				fieldLabel : "序列",
				hiddenName : "job.seq.id",
				maxHeight : 200,
				id : "seq",
				xtype : "combo",
				mode : "local",
				editable : false,
				valueField : "id",
				displayField : "name",
				triggerAction : "all",
				store : new Ext.data.SimpleStore({
					fields : ["id","name"]
				}),
				listeners : {
					focus : function(d) {
						var c = Ext.getCmp("seq").getStore();R
						if(c.getCount() <= 0) {
							Ext.Ajax.request({
								url : __ctxPath + "/kpi/loadHrPaDatadictionary.do",
								method : "post",
								params : {
									parentId : 19          //考核频度的根节点ID
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
				fieldLabel : "说明书",
				name : "job.memo",
				id : "memo",
				height: 250,
				xtype : "fckeditor"
			} ]
		});
		if (this.jobId != null && this.jobId != "undefined") {
			this.formPanel.getForm().load(
					{
						deferredRender : false,
						url : __ctxPath + "/hrm/getJob.do?jobId=" + this.jobId,
						waitMsg : "正在载入数据...",
						success : function(b, c) {
							Ext.getCmp("jobDepartmentName").setValue(
									c.result.data.department.depName);
							Ext.getCmp("band").setValue(c.result.data.band.id);
							Ext.getCmp("band").setRawValue(c.result.data.band.name);
							Ext.getCmp("seq").setValue(c.result.data.seq.id);
							Ext.getCmp("seq").setRawValue(c.result.data.seq.name);
							Ext.getCmp("race").setValue(c.result.data.race.id);
							Ext.getCmp("race").setRawValue(c.result.data.race.name);
						},
						failure : function(b, c) {
						}
					});
		}
		this.buttons = [ {
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
		} ];
	},
	reset : function(a) {
		a.getForm().reset();
	},
	cancel : function(a) {
		a.close();
	},
	save : function(a, b) {
		if (a.getForm().isValid()) {
			a.getForm().submit({
				method : "POST",
				waitMsg : "正在提交数据...",
				success : function(c, e) {
					Ext.ux.Toast.msg("操作信息", "成功保存信息！");
					var d = Ext.getCmp("JobGrid");
					if (d != null) {
						d.getStore().reload();
					}
					b.close();
				},
				failure : function(c, d) {
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