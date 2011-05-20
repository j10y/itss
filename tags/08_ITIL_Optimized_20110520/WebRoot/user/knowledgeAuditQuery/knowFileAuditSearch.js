KnowFileAuditPagePanel = Ext.extend(Ext.Panel, {
	id : "knowFileAuditPanel",
	closable : true,
	frame : true,
	autoScroll : true,
	viewConfig : {
		autoFill : true,
		forceFit : true
	},
	layout : 'border',
	items : this.items,
	lookInto : function() {
		var record = this.konwledgeGrid.getSelectionModel().getSelected();
		var records = this.konwledgeGrid.getSelectionModel().getSelections();
		if (!record) {
			Ext.Msg.alert("提示", "请先选择要修改的行！");
			return;
		}
		if (records.length > 1) {
			Ext.Msg.alert("提示", "修改时只能选择一行！");
			return;
		}
		var recordId = record.get("KnowFile$id");
		window.location = webContext
				+ '/user/knowledgeAuditQuery/knowFileAuditHis.jsp?dataId='
				+ recordId;

	},
	getSearchForm : function() {
		this.panel= new Ext.form.FormPanel({
					id : 'knowSearchForm',
					region : "north",
					layout : 'table',
					height : 60,
					frame : true,
					collapsible : true,
					defaults : {
						bodyStyle : 'padding:2px'
					},
					layoutConfig : {
						columns : 6
					},
					title : '查询条件',
					items : [{
							html : '文件类型:',
							cls : 'common-text',
							style : 'width:70;text-align:right'
						}, new Ext.form.ComboBox({
							id : 'knowFileTypeCombo',
							fieldLabel : "文件类型",
							width : 200,
							hiddenName : 'knowFileType',
							displayField : 'name',
							valueField : 'id',
							resizable : true,
							emptyText :'请从下拉列表中选择...',
							triggerAction : 'all',
							store : new Ext.data.JsonStore({
								url : webContext
										+ '/extjs/comboDataAction?clazz=com.zsgj.itil.knowledge.entity.KnowFileType',
								fields : ['id', 'name'],
								listeners : {
									beforeload : function(store, opt) {
										if (opt.params['knowFileType'] == undefined) {
											opt.params['name'] = Ext
													.getCmp('knowFileTypeCombo').defaultParam;
										}
									}
								},
								totalProperty : 'rowCount',
								root : 'data',
								id : 'id'
							}),
							pageSize : 10,
							listeners : {
								'beforequery' : function(queryEvent) {
									var param = queryEvent.combo.getRawValue();
									this.defaultParam = param;
									if (queryEvent.query == '') {
										param = '';
									}
			
									this.store.load({
										params : {
											name : param,
											start : 0
										}
									});
									return true;
								}
							}
						}),{
							html : '文件名称:',
							cls : 'common-text',
							style : 'width:70;text-align:right'
						}, new Ext.form.TextField({
							fieldLabel : '文件名称',
							xtype : 'textfield',
							id : 'name',
							name : 'name',
							width : 200,
							allowBlank : true
						}),{
							html : '创建人:',
							cls : 'common-text',
							style : 'width:70;text-align:right'
						}, new Ext.form.ComboBox(
							{
								id : 'createUserCombo',
								fieldLabel : "创建人",  
								width : 200,
								hiddenName : 'createUser', 
								displayField : 'userName',
								valueField : 'id',
								resizable : true,
								emptyText :'请从下拉列表中选择...',
								triggerAction : 'all',
								store : new Ext.data.JsonStore({
									url : webContext
											+ '/extjs/comboDataAction?clazz=com.zsgj.info.framework.security.entity.UserInfo',
									fields : ['id', 'userName'],
									listeners : {
										beforeload : function(store, opt) {
											if (opt.params['createUser'] == undefined) {
												opt.params['userName'] = Ext
														.getCmp('createUserCombo').defaultParam;
											}
										}
									},
									totalProperty : 'rowCount',
									root : 'data',
									id : 'id'
								}),
								pageSize : 10,
								listeners : {
									'beforequery' : function(queryEvent) {
											var param = queryEvent.combo.getRawValue();
											this.defaultParam = param;
											if (queryEvent.query == '') {
												param = '';
											}
											this.store.load({
												params : {
													userName : param,
													start : 0
												}
											});
											return true;
										}
								}
							}
						)],
					scope : this
				});
			return this.panel; 
	},

	initComponent : function() {
		var da = new DataAction();
		var DataHead = da.getListPanelElementsForHead("KnowledgeFileQuery_pagepanel");
		this.modelTableName = "KnowFile";
		var sm = new Ext.grid.CheckboxSelectionModel();
		var columns = new Array();
		var fields = new Array();
		columns[0] = sm;
		for (var i = 0; i < DataHead.length; i++) {
			var headItem = DataHead[i];
			var title = headItem.header;
			var alignStyle = 'left';
			var propertyName = headItem.dataIndex;
			var isHiddenColumn = false;
			var isHidden = headItem.isHidden;
			if (isHidden == 'true') {
				isHiddenColumn = true;
			}
			if(propertyName=="KnowFile$createDate"
			     ||propertyName=="KnowFile$createUser"
			      ||propertyName=="KnowFile$oldKnowFile"){
				isHiddenColumn = false;
			}
			var columnItem = {
				header : title,
				dataIndex : propertyName,
				sortable : true,
				hidden : isHiddenColumn,
				align : alignStyle
			}
			//2010-04-24 add by huzh for 列调整 begin
			if(propertyName=="KnowFile$id"){
				columnItem.width=70;
			}
			if(propertyName=="KnowFile$name"){
				columnItem.width=200;
			}
			if(propertyName=="KnowFile$descn"){
				columnItem.width=230;
			}
			if(propertyName=="KnowFile$createDate"){
				columnItem.width=120;
			}
			if(propertyName=="KnowFile$createUser"){
				columnItem.width=100;
			}
			if(propertyName=="KnowFile$knowFileType"){
				columnItem.width=140;
			}
			//2010-04-24 add by huzh for 列调整 end
			columns[i + 1] = columnItem;
			fields[i] = propertyName;
		}
		this.storeMapping = fields;
		this.cm = new Ext.grid.ColumnModel(columns);
		this.store = da.getPanelJsonStore("KnowledgeFileList_pagepanel",
				DataHead);
		this.store.on("load",function(store,records, opt) {
					for(var i=0;i<records.length;i++){
						var cDate=records[i].get("KnowFile$createDate")
						if(cDate!=""){
							records[i].set("KnowFile$createDate",cDate.substring(0,16));
						}
						 if(records[i].get("KnowFile$status")==2){
							records[i].set("KnowFile$status","审批中");
			  			}
					}
		});
		this.fp = this.getSearchForm();
		this.store.paramNames.sort = "orderBy";
		this.store.paramNames.dir = "orderType";
		this.cm.defaultSortable = true;
		var viewConfig = Ext.apply({
					forceFit : true
				}, this.gridViewConfig);

		var pageBar = new Ext.PagingToolbar({
					pageSize : 10,
					store : this.store,
					displayInfo : true,
					displayMsg : '当前显示 {0}-{1}条记录 /共{2}条记录',
					emptyMsg : "无显示数据"
				});
		this.searchConfig = function() {
			if(Ext.getCmp('createUserCombo').getRawValue()!=''&&Ext.getCmp('createUserCombo').getValue()==''){
			    Ext.Msg.alert("提示","请从下拉列表中选择正确的创建人！");
			    return false;
			}
			if(Ext.getCmp('createUserCombo').getRawValue()==''){
				Ext.getCmp('createUserCombo').clearValue();
			}
			if(Ext.getCmp('knowFileTypeCombo').getRawValue()==''){
				Ext.getCmp('knowFileTypeCombo').clearValue();
			}
			var params = Ext.getCmp("knowSearchForm").getForm().getValues(false);
			var store = Ext.getCmp("konwledgeGrid").getStore();
			params.start = 0;
			params.status = 2;
//			pageBar.formValue = param;
			store.on('beforeload', function(a) {   
			      Ext.apply(a.baseParams,params);   
			});
			store.load({
						params : params
					});
		}, 
		this.konwledgeGrid = new Ext.grid.GridPanel({
					id : "konwledgeGrid",
					region : "center",
					store : this.store,
					cm : this.cm,
					sm : sm,
					trackMouseOver : false,
					loadMask : true,
					y : 140,
					height : 200,
					anchor : '0 -35',
					tbar : [{
								xtype : 'button',
								style : 'margin:2px 0px 2px 5px',
								handler : this.searchConfig,
								text : '查询',
								iconCls : 'search'
							},'-',{
								xtype : 'button',
								style : 'margin:2px 0px 2px 5px',
								handler : function() {
									Ext.getCmp("knowSearchForm").getForm()
											.reset();
								},
								text : '清除',
								iconCls : 'reset'
							},new Ext.Toolbar.TextItem("<font color=red style='font-weight:lighter' >【双击后查看详细信息】</font>")],
					bbar : pageBar
				});

		this.konwledgeGrid.on("headerdblclick", this.fitWidth, this);
		this.konwledgeGrid.on("rowdblclick", this.lookInto, this);
		var items = new Array();
		items.push(this.fp);
		items.push(this.konwledgeGrid);
		this.items = items;

		KnowFileAuditPagePanel.superclass.initComponent.call(this);
		var params = {
			start : 0,
			'status' : 2
		};
//		pageBar.formValue = param;
		this.store.on('beforeload', function(a) {   
			      Ext.apply(a.baseParams,params);   
			});
		this.store.removeAll();
		this.store.load({
					params : params
				});
	},
	fitWidth : function(grid, columnIndex, e) {
		var c = columnIndex;
		var w = grid.view.getHeaderCell(c).firstChild.scrollWidth;
		for (var i = 0, i = grid.store.getCount(); i < l; i++) {
			w = Math.max(w, grid.view.getCell(i, c).firstChild.scrollWidth);
		}
		grid.colModel.setColumnWidth(c, w);
	}
});
