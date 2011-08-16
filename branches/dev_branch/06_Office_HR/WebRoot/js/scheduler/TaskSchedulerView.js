Ext.ns("TaskSchedulerView");
TaskSchedulerView = Ext
		.extend(
				Ext.Panel,
				{
					searchPanel : null,
					gridPanel : null,
					store : null,
					topbar : null,
					constructor : function(a) {
						Ext.applyIf(this, a);
						this.initUIComponents();
						TaskSchedulerView.superclass.constructor.call(this, {
							id : "TaskSchedulerView",
							title : "任务时间设定",
							iconCls : "menu-notice",
							region : "center",
							layout : "border",
							items : [this.gridPanel ]
						});
					},
					initUIComponents : function() {
						this.store = new Ext.data.Store({
							proxy : new Ext.data.HttpProxy({
								url : __ctxPath + "/scheduler/listTaskScheduler.do"
							}),
							reader : new Ext.data.JsonReader({
								root : "result",
								totalProperty : "totalCounts",
								id : "id",
								fields : [ {
									name : "id",
									type : "int"
								}, "taskName", "runTime", "cronTrigger","desc"]
							}),
							remoteSort : true
						});
						this.store.setDefaultSort("id", "desc");
						this.store.load({
							param : {
								start : 0,
								limit : 25
							}
						});
						var b = new Ext.grid.CheckboxSelectionModel();
						var a = new Ext.grid.ColumnModel(
								{
									columns : [
											b,
											new Ext.grid.RowNumberer(),
											{
												header : "id",
												dataIndex : "id",
												hidden : true
											},
											{
												header : "任务名称",
												dataIndex : "taskName"
											},
											{
												header : "执行周期",
												dataIndex : "runTime"
											},
											{
												header : "cronTrigger",
												dataIndex : "cronTrigger"
											},
											{
												header : "描述信息",
												dataIndex : "desc"
											},
											{
												header : "管理",
												dataIndex : "id",
												sortable : false,
												width : 50,
												renderer : function(f, e, c, i,
														d) {
													var h = c.data.id;
													var g = "";
													/*
														g = '<button title="删除" value=" " class="btn-del" onclick="TaskSchedulerView.remove('
																+ h
																+ ')"></button>';*/
														g += '&nbsp;<button title="编辑" value=" " class="btn-edit" onclick="TaskSchedulerView.edit('
																+ h
																+ ')"></button>';
													return g;
												}
											} ],
									defaults : {
										sortable : true,
										menuDisabled : false,
										width : 100
									}
								});
						this.topbar = new Ext.Toolbar({
							id : "CopyFoxBillFootBar",
							height : 30,
							bodyStyle : "text-align:left",
							items : []
						});
						/*
						if (isGranted("_CopyFoxBillAdd")) {
							this.topbar.add(new Ext.Button({
								iconCls : "btn-add",
								text : "添加",
								handler : function() {
									new TaskSchedulerForm().show();
								}
							}));
						}
						if (isGranted("_CopyFoxBillDel")) {
							this.topbar.add(new Ext.Button({
								iconCls : "btn-del",
								text : "删除",
								handler : function() {
									var e = Ext.getCmp("TaskSchedulerGrid");
									var c = e.getSelectionModel()
											.getSelections();
									if (c.length == 0) {
										Ext.ux.Toast.msg("信息", "请选择要删除的记录！");
										return;
									}
									var f = Array();
									for ( var d = 0; d < c.length; d++) {
										f.push(c[d].data.id);
									}
									TaskSchedulerView.remove(f);
								}
							}));
						}*/
						this.gridPanel = new Ext.grid.GridPanel({
							id : "TaskSchedulerGrid",
							tbar : this.topbar,
							region : "center",
							store : this.store,
							trackMouseOver : true,
							disableSelection : false,
							loadMask : true,
							autoHeight : true,
							cm : a,
							sm : b,
							viewConfig : {
								forceFit : true,
								enableRowBody : false,
								showPreview : false
							},
							bbar : new Ext.PagingToolbar({
								pageSize : 25,
								store : this.store,
								displayInfo : true,
								displayMsg : "当前显示从{0}至{1}， 共{2}条记录",
								emptyMsg : "当前没有记录"
							})
						});
						this.gridPanel.addListener("rowdblclick", function(d,
								c, f) {
							d.getSelectionModel().each(function(e) {
								if (isGranted("_CopyFoxBillEdit")) {
									TaskSchedulerView.edit(e.data.id);
								}
							});
						});
					}
				});
TaskSchedulerView.remove = function(b) {
	var a = Ext.getCmp("TaskSchedulerGrid");
	Ext.Msg.confirm("信息确认", "您确认要删除该记录吗？", function(c) {
		if (c == "yes") {
			Ext.Ajax.request({
				url : __ctxPath + "/scheduler/multiDelTaskScheduler.do",
				params : {
					ids : b
				},
				method : "post",
				success : function() {
					Ext.ux.Toast.msg("操作信息", "成功删除所选记录！");
					a.getStore().reload({
						params : {
							start : 0,
							limit : 25
						}
					});
				}
			});
		}
	});
};
TaskSchedulerView.edit = function(a) {
	new TaskSchedulerForm({
		id : a
	}).show();
};