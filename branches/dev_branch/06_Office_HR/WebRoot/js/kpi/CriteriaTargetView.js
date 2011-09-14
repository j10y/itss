CriteriaTargetView = Ext.extend(Ext.Panel, {
	acPanel : null,
	acGridPanel1 : null,
	acGridPanel2 : null,
	buttonPanel : null,
	store : null,
	constructor : function(a) {
		if(a == null) {
			a = {};
		}
		Ext.apply(this, a);
		this.initComponents();
		CriteriaTargetView.superclass.constructor.call(this, {
			id : "CriteriaTargetView",
			title : "考核标准目标导入",
			region : "center",
			layout : "border",
			style : "padding:5px",
			items : [
				this.acPanel,
				this.buttonPanel
			]
		});
	},
	initComponents : function() {
		this.store = new Ext.data.JsonStore({
			url : __ctxPath + "/kpi/listHrPaAssessmentcriteria.do?Q_publishStatus_N_EQ=3",
			totalProperty : "totalCounts",
			id : "id",
			root : "result",
			remoteSort : true,
			fields : [
				{
					name : "id",
					type : "int"
				},
				"acName",
				"acKey"
			]
		});
		this.store.setDefaultSort("id", "desc");
		this.store.load({
			params : {
				start : 0,
				limit : 30
			}
		});
		var fields = [
			{
				name : "id",
				mapping : "id"
			}, {
				name : "acName",
				mapping : "acName"
			}, {
				name : "acKey",
				mapping : "acKey"
			}
		];
		var store2 = new Ext.data.JsonStore({
			fields : fields,
			root : "result"
		});
		var a = new Ext.grid.ColumnModel({
			columns : [
				{
					header : "id",
					id : "id",
					dataIndex : "id",
					hidden : true
				}, {
					header : "考核标准名称",
					id : "acName",
					dataIndex : "acName"
				}, {
					header : "考核标准关键字",
					id : "acKey",
					dataIndex : "acKey"
				}
			],
			defaults : {
				sortable : true,
				menuDisable : false,
			}
		});
		//构造第一个GridPanel
		this.acGridPanel1 = new Ext.grid.GridPanel({
			id : "targetGridPanel1",
			ddGroup : "targetSecondGridDDGroup",
			store : this.store,
			cm : a,
			enableDragDrop : true,
			stripeRows : true,
			autoExpandColumn : "acName",
			title : "被选择考核标准列表",
			bbar : new Ext.PagingToolbar({
				pageSize : 30,
				store : this.store,
				displayInfo : true,
				displayMsg : "当前显示{0}至{1}，共{2}条记录",
				emptyMsg : "当前没有记录"
			})
		});
		this.acGridPanel2 = new Ext.grid.GridPanel({
			id : "targetGridPanel2",
			ddGroup : "targetFirstGridDDGroup",
			store : store2,
			cm : a,
			enableDragDrop : true,
			autoExpandColumn : "acName",
			title : "已选择考核标准列表",
			listeners : {
				"afterrender" : function() {
					var blankRecord =  Ext.data.Record.create(fields);

			        var secondGridDropTargetEl = Ext.getCmp("targetGridPanel2").getView().scroller.dom;
			        var secondGridDropTarget = new Ext.dd.DropTarget(secondGridDropTargetEl, {
		                ddGroup    : 'targetSecondGridDDGroup',
		                notifyDrop : function(ddSource, e, data){
	                        var records =  ddSource.dragData.selections;
	                        Ext.each(records, ddSource.grid.store.remove, ddSource.grid.store);
	                        Ext.getCmp("targetGridPanel2").store.add(records);
	                        return true;
	                	}
	        		});
	    	
			        var firstGridDropTargetEl =  Ext.getCmp("targetGridPanel1").getView().scroller.dom;
			        var firstGridDropTarget = new Ext.dd.DropTarget(firstGridDropTargetEl, {
		                ddGroup    : 'targetFirstGridDDGroup',
		                notifyDrop : function(ddSource, e, data){
	                        var records =  ddSource.dragData.selections;
	                        Ext.each(records, ddSource.grid.store.remove, ddSource.grid.store);
	                        Ext.getCmp("targetGridPanel1").store.add(records);
	                        return true;
	                	}
	        		});
				}
			}
		});
		this.acPanel = new Ext.Panel({
			layout : "hbox",
			region : "center",
			defaults : {
				flex : 1
			},
			layoutConfig : {
				align : "stretch"
			},
			items : [
				this.acGridPanel1,
				this.acGridPanel2
			],
			bbar : [
				"->"
			]
		});
		var dept = __ctxPath + "/system/listDepartment.do?opt=appUser";
		var departments = new TreeSelector("targetDept.depName", dept, "请选择所属部门", "targetDept.depId");
		var downloadDept = new TreeSelector("targetDownloadDept.depName", dept, "请选择所属部门", "targetDownloadDept.depId");
		departments.hide();
		downloadDept.hide();
		this.buttonPanel = new Ext.Panel({
			id : "buttonPanel",
			region : "south",
			height : 150,
			items : [
				{
					xtype : "container",
					layout : "column",
					items : [
						{
							xtype : "form",
							border : false,
							columnWidth : 0.5,
							bodyStyle : "margin:30px",
							items : [
								{
									id : "targetDownloadFileType",
									xtype : "combo",
									fieldLabel : "请选择要下载的excel模板类型",
									labelStyle : "width:180px",
									mode : "local",
									allowBlank : false,
									editable : false,
									triggerAction : "all",
									store : [
										[
											"1",
											"按品类分类"
										], [
											"2",
											"按人员分类"
										]
									],
									listeners : {
										select : function(e) {
											if(Ext.getCmp("targetDownloadFileType").getValue() == 2) {
												downloadDept.show();
											} else {
												downloadDept.hide();
											}
										}
									}
								}, {
									id : "targetDownloadDept.depId",
									value : "0",
									xtype : "hidden"
								},
								downloadDept,
								new Ext.Button({
									id : "targetButton1",
									text : "生成目标excel模板",
									style : "margin:10px 0 0 70px",
									handler : function() {
										if(!Ext.getCmp("targetDownloadFileType").isValid()) {
											return ;
										}
										var acItems = "";
										var acStore = Ext.getCmp("targetGridPanel2").getStore();
										if(acStore.getCount() <= 0) {
											Ext.MessageBox.show({
												title : "操作信息",
												msg : "请选择考核标准！",
												buttons : Ext.MessageBox.OK,
												icon : Ext.MessageBox.ERROR
											});
											return ;
										}
										for(var i = 0; i < acStore.getCount(); i++) {
											var item = acStore.getAt(i).data;
											acItems +=item.id + ",";
										}
										acItems = acItems.substr(0, acItems.length - 1);
										Ext.Ajax.request({
											url : __ctxPath + "/kpi/createExcelHrPaAssessmenttasksassigned.do",
											params : {
												acIds : acItems,
												downloadFileType : Ext.getCmp("targetDownloadFileType").getValue(),
												deptId : Ext.getCmp("targetDownloadDept.depId").getValue()
											},
											success : function(d) {
												var e = Ext.util.JSON.decode(d.responseText);
												new Ext.Window({
													id : "targetDownloadWin",
													title : "请下载目标excel模板",
													width : 300,
													height : 180,
													modal : true,
													layout : "fit",
													items : [
														{
															id : "targetDownloadPanel",
															html : "<div style='text-align:center;height:120px;line-height:120px'><a href='" + __ctxPath + e.filePath + "'>请在此下载目标excel模板！</a></div>"
														}
													]
												}).show();
											}
										});
									}
								})
							]
						}, {
							xtype : "form",
							border : false,
							columnWidth : 0.5,
							bodyStyle : "margin:30px",
							items : [
								{
									id : "targetUploadFileType",
									xtype : "combo",
									fieldLabel : "请选择要上传的excel模板类型",
									labelStyle : "width:180px",
									mode : "local",
									allowBlank : false,
									editable : false,
									triggerAction : "all",
									store : [
										[
											"1",
											"按品类分类"
										], [
											"2",
											"按人员分类"
										]
									],
									listeners : {
										select : function(e) {
											if(Ext.getCmp("targetUploadFileType").getValue() == 1) {
												departments.show();
											} else {
												departments.hide();
											}
										}
									}
								},{
									id : "targetDept.depId",
									value : "0",
									xtype : 'hidden'
								},
								departments,
								new Ext.Button({
									id : "targetButton2",
									text : "导入目标excel数据",
									style : "margin:10px 0 0 70px",
									handler : function() {
										if(!Ext.getCmp("targetUploadFileType").isValid()) {
											return ;
										}
										if(Ext.getCmp("targetUploadFileType").getValue() == 1) {
											if(Ext.getCmp("targetDept.depName").getValue() == "") {
												Ext.MessageBox.show({
													title : "操作信息",
													msg : "请选择所属部门！",
													buttons : Ext.MessageBox.OK,
													icon : Ext.MessageBox.ERROR
												});
												return ;
											}
										}
										var a = App.createUploadDialog({
											file_cat : "kpiTasksassigned",
											callback : function (c) {
												for (var b = 0; b < c.length; b++) {
													Ext.Ajax.request({
														url : __ctxPath + "/kpi/readExcelHrPaAssessmenttasksassigned.do",
														params : {
															filePath : c[b].filepath,
															uploadFileType : Ext.getCmp("targetUploadFileType").getValue(),
															deptId : Ext.getCmp("targetDept.depId").getValue()
														},
														success : function(d) {
															var e = Ext.util.JSON.decode(d.responseText);
															if(e.flag == 1) {
																Ext.MessageBox.show({
																	title : "操作信息",
																	msg : "数据导入成功，共导入" + e.count + "条数据！",
																	buttons : Ext.MessageBox.OK,
																	icon : Ext.MessageBox.INFO
																});
															} else {
																Ext.MessageBox.show({
																	title : "操作信息",
																	msg : "数据导入失败，请核实数据！",
																	buttons : Ext.MessageBox.OK,
																	icon : Ext.MessageBox.ERROR
																});
															}
														}
													});
												}
											}
										});
										a.show();
									}
								})
							]
						}
					]
				}
			]
		});
	}
});