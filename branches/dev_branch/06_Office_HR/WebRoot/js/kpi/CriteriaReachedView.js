CriteriaReachedView = Ext.extend(Ext.Panel, {
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
		CriteriaReachedView.superclass.constructor.call(this, {
			id : "CriteriaReachedView",
			title : "考核标准达成导入",
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
			id : "reachGridPanel1",
			ddGroup : "reachSecondGridDDGroup",
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
			id : "reachGridPanel2",
			ddGroup : "reachFirstGridDDGroup",
			store : store2,
			cm : a,
			enableDragDrop : true,
			autoExpandColumn : "acName",
			title : "已选择考核标准列表",
			listeners : {
				"afterrender" : function() {
					var blankRecord =  Ext.data.Record.create(fields);

			        var secondGridDropTargetEl = Ext.getCmp("reachGridPanel2").getView().scroller.dom;
			        var secondGridDropTarget = new Ext.dd.DropTarget(secondGridDropTargetEl, {
		                ddGroup    : 'reachSecondGridDDGroup',
		                notifyDrop : function(ddSource, e, data){
	                        var records =  ddSource.dragData.selections;
	                        Ext.each(records, ddSource.grid.store.remove, ddSource.grid.store);
	                        Ext.getCmp("reachGridPanel2").store.add(records);
	                        return true;
	                	}
	        		});
	    	
			        var firstGridDropTargetEl =  Ext.getCmp("reachGridPanel1").getView().scroller.dom;
			        var firstGridDropTarget = new Ext.dd.DropTarget(firstGridDropTargetEl, {
		                ddGroup    : 'reachFirstGridDDGroup',
		                notifyDrop : function(ddSource, e, data){
	                        var records =  ddSource.dragData.selections;
	                        Ext.each(records, ddSource.grid.store.remove, ddSource.grid.store);
	                        Ext.getCmp("reachGridPanel1").store.add(records);
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
		this.buttonPanel = new Ext.Panel({
			id : "reachButtonPanel",
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
									id : "reachDownloadFileType",
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
									]
								},
								new Ext.Button({
									id : "reachButton1",
									text : "生成达成excel模板",
									style : "margin:10px 0 0 70px",
									handler : function() {
										if(!Ext.getCmp("reachDownloadFileType").isValid()) {
											return ;
										}
										var acItems = "";
										var acStore = Ext.getCmp("reachGridPanel2").getStore();
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
											url : __ctxPath + "/kpi/createExcelHrPaAcreached.do",
											params : {
												acIds : acItems,
												downloadFileType : Ext.getCmp("reachDownloadFileType").getValue()
											},
											success : function(d) {
												var e = Ext.util.JSON.decode(d.responseText);
												new Ext.Window({
													id : "reachDownloadWin",
													title : "请下载达成excel模板",
													width : 300,
													height : 180,
													modal : true,
													layout : "fit",
													items : [
														{
															id : "reachDownloadPanel",
															html : "<div style='text-align:center;height:120px;line-height:120px'><a href='" + __ctxPath + e.filePath + "'>请在此下载达成excel模板！</a></div>"
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
									id : "reachUploadFileType",
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
									]
								},
								new Ext.Button({
									id : "reachButton2",
									text : "导入达成excel数据",
									style : "margin:10px 0 0 70px",
									handler : function() {
										if(!Ext.getCmp("reachUploadFileType").isValid()) {
											return ;
										}
										var a = App.createUploadDialog({
											file_cat : "kpiReached",
											callback : function (c) {
												for (var b = 0; b < c.length; b++) {
													Ext.Ajax.request({
														url : __ctxPath + "/kpi/readExcelHrPaAcreached.do",
														params : {
															filePath : c[b].filepath,
															uploadFileType : Ext.getCmp("reachUploadFileType").getValue()
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