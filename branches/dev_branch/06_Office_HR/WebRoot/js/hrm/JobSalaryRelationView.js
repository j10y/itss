JobSalaryRelationView = Ext.extend(Ext.Panel, {
	constructor : function(a) {
		if (a == null) {
			a = {};
		}
		Ext.apply(this, a);
		this.initComponents();
		JobSalaryRelationView.superclass.constructor.call(this, {
			id : "JobSalaryRelationView",
			iconCls : "menu-JobSalaryRelation",
			title : "岗位薪标关系管理",
			region : "center",
			layout : "border",
			items : [ this.searchPanel, this.gridPanel ]
		});
	},
	typeId : null,
	searchPanel : null,
	gridPanel : null,
	store : null,
	topbar : null,
	initComponents : function() {
		this.searchPanel = new Ext.FormPanel({
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
			items : [ {
				text : "查询条件："
			}, {
				text : "所属部门："
			}, {
				name : "Q_department.depName_S_LK",
				xtype : "textfield"
			}, {
				text : "所属岗位："
			}, {
				name : "Q_job.jobName_S_LK",
				xtype : "textfield"
			}, {
				xtype : "button",
				text : "查询",
				iconCls : "search",
				handler : this.search.createCallback(this)
			}, {
				name : "Q_deleteFlag_N_EQ",
				width : 80,
				xtype : "hidden",
				value : 0
			} ]
		});
		this.store = new Ext.data.JsonStore({
			url : __ctxPath + "/hrm/listJobSalaryRelation.do",
			baseParams : {
				"Q_deleteFlag_N_EQ" : 0
			},
			root : "result",
			totalProperty : "totalCounts",
			remoteSort : true,
			fields : [ {
				name : "relationId",
				type : "int"
			}, "department"
			, "job"
			, "standSalary"
			, "empCount"
			, "onEmpCount"
			, "totalMoney" ]
		});
		this.store.setDefaultSort("relationId", "desc");
		this.store.load({
			params : {
				start : 0,
				limit : 25
			}
		});
		var b = new Array();
		if (isGranted("_JobSalaryRelationDel")) {
			b.push({
				iconCls : "btn-del",
				qtip : "删除",
				style : "margin:0 3px 0 3px"
			});
		}
		if (isGranted("_JobSalaryRelationEdit")) {
			b.push({
				iconCls : "btn-edit",
				qtip : "编辑",
				style : "margin:0 3px 0 3px"
			});
		}
		this.rowActions = new Ext.ux.grid.RowActions({
			header : "管理",
			width : 80,
			actions : b
		});
		var c = new Ext.grid.CheckboxSelectionModel();
		var a = new Ext.grid.ColumnModel({
			columns : [ c, new Ext.grid.RowNumberer(), {
				header : "relationId",
				dataIndex : "relationId",
				hidden : true
			}, {
				header : "所属部门",
				dataIndex : "department",
				renderer : function(d) {
					return d.depName;
				}
			}, {
				header : "岗位",
				dataIndex : "job",
				renderer : function(d) {
					return d.jobName;
				}
			}, {
				header : "薪资标准",
				dataIndex : "standSalary",
				renderer : function(d) {
					return d.standardName+"/"+d.standardNo;
				}
			}, {
				header : "编制人数",
				dataIndex : "empCount"
			}, {
				header : "在岗人数",
				dataIndex : "onEmpCount"
			}, {
				header : "总金额",
				dataIndex : "totalMoney"
			}, this.rowActions ],
			defaults : {
				sortable : true,
				menuDisabled : false,
				width : 100
			}
		});
		this.topbar = new Ext.Toolbar({
			height : 30,
			bodyStyle : "text-align:left",
			items : []
		});
		if (isGranted("_JobSalaryRelationAdd")) {
			this.topbar.add(new Ext.Button({
				iconCls : "btn-add",
				text : "添加关系",
				handler : this.createRecord
			}));
		}
		if (isGranted("_JobSalaryRelationDel")) {
			this.topbar.add(new Ext.Button({
				iconCls : "btn-del",
				text : "删除关系",
				handler : this.delRecords,
				scope : this
			}));
		}

		this.gridPanel = new Ext.grid.GridPanel({
			id : "JobSalaryRelationGrid",
			region : "center",
			stripeRows : true,
			tbar : this.topbar,
			store : this.store,
			trackMouseOver : true,
			disableSelection : false,
			loadMask : true,
			autoHeight : true,
			cm : a,
			sm : c,
			plugins : this.rowActions,
			viewConfig : {
				forceFit : true,
				autoFill : true,
				forceFit : true
			},
			bbar : new Ext.PagingToolbar({
				pageSize : 25,
				store : this.store,
				displayInfo : true,
				displayMsg : "当前页记录索引{0}-{1}， 共{2}条记录",
				emptyMsg : "当前没有记录"
			})
		});
		this.gridPanel.addListener("rowdblclick", function(f, d, g) {
			f.getSelectionModel().each(function(e) {
				if (isGranted("_JobSalaryRelationEdit")) {
					new JobSalaryRelationForm({
						relationId : e.data.relationId
					}).show();
				}
			});
		});
		this.rowActions.on("action", this.onRowAction, this);
	},
	search : function(a) {
		if (a.searchPanel.getForm().isValid()) {
			a.searchPanel.getForm().submit({
				waitMsg : "正在提交查询",
				url : __ctxPath + "/hrm/listJobSalaryRelation.do",
				success : function(c, d) {
					var b = Ext.util.JSON.decode(d.response.responseText);
					a.gridPanel.getStore().loadData(b);
				}
			});
		}
	},
	createRecord : function() {
		new JobSalaryRelationForm().show();
	},
	delByIds : function(a) {
		Ext.Msg.confirm("信息确认", "您确认要删除所选记录吗？", function(b) {
			if (b == "yes") {
				Ext.Ajax.request({
					url : __ctxPath + "/hrm/multiDelJobSalaryRelation.do",
					params : {
						ids : a
					},
					method : "POST",
					success : function(c, d) {
						Ext.ux.Toast.msg("操作信息", "成功删除信息！");
						Ext.getCmp("JobSalaryRelationGrid").getStore().reload();
					},
					failure : function(c, d) {
						Ext.ux.Toast.msg("操作信息", "操作出错，请联系管理员！");
					}
				});
			}
		});
	},
	delRecords : function(b) {
		var d = Ext.getCmp("JobSalaryRelationGrid");
		var a = d.getSelectionModel().getSelections();
		if (a.length == 0) {
			Ext.ux.Toast.msg("信息", "请选择要删除的记录！");
			return;
		}
		var e = Array();
		for ( var c = 0; c < a.length; c++) {
			e.push(a[c].data.relationId);
		}
		this.delByIds(e);
	},
	editRecord : function(a) {
		new JobSalaryRelationForm({
			relationId : a.data.relationId
		}).show();
	},
	recovery : function(a) {
		new RecoveryJobSalaryRelationWin().show();
	},
	onRowAction : function(c, a, d, e, b) {
		switch (d) {
		case "btn-del":
			this.delRecords();
			break;
		case "btn-edit":
			this.editRecord(a);
			break;
		default:
			break;
		}
	}
});