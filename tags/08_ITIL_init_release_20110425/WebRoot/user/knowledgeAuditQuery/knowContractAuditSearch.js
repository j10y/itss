KnowContractAuditPanel = Ext.extend(Ext.Panel, {
	id : "knowContractAuditPanel",
	closable : true,
	frame : true,
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
			Ext.Msg.alert("��ʾ", "����ѡ��Ҫ�޸ĵ��У�");
			return;
		}
		if (records.length > 1) {
			Ext.Msg.alert("��ʾ", "�޸�ʱֻ��ѡ��һ�У�");
			return;
		}
		var recordId = record.get("KnowContract$id");
		window.location = webContext
				+ '/user/knowledgeAuditQuery/knowContractAuditHis.jsp?dataId='
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
						columns : 4
					},
					title :'��ѯ����',
					items : [{
							html : '��ͬ����:',
							cls : 'common-text',
							style : 'width:100;text-align:right'
						}, new Ext.form.TextField({
							fieldLabel : '��ͬ����',
							xtype : 'textfield',
							id : 'name',
							name : 'name',
							width : 200,
							allowBlank : true
						}),{
							html : '������:',
							cls : 'common-text',
							style : 'width:80;text-align:right'
						}, new Ext.form.ComboBox(
							{
								id : 'createUserCombo',
								fieldLabel : "������",  
								width : 200,
								hiddenName : 'createUser', 
								displayField : 'userName',
								valueField : 'id',
								resizable : true,
								emptyText :'��������б���ѡ��...',
								triggerAction : 'all',
								store : new Ext.data.JsonStore({
									url : webContext
											+ '/extjs/comboDataAction?clazz=com.digitalchina.info.framework.security.entity.UserInfo',
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
		var DataHead = da
				.getListPanelElementsForHead("KnowledgeContractQuery_pagepanel");
		this.modelTableName = "KnowContract";
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
			if(propertyName=="KnowContract$descn"
			    ||propertyName=="KnowContract$createUser"
			      ||propertyName=="KnowContract$createDate"
			       ||propertyName=="KnowContract$oldKnowContract"){
				isHiddenColumn = false;
			}
			var columnItem = {
				header : title,
				dataIndex : propertyName,
				sortable : true,
				hidden : isHiddenColumn,
				align : alignStyle
			}
			//2010-04-24 add by huzh for �е��� begin
			if(propertyName=="KnowContract$id"){
				columnItem.width=70;
			}
			if(propertyName=="KnowContract$name"){
				columnItem.width=200;
			}
			if(propertyName=="KnowContract$descn"){
				columnItem.width=230;
			}
			if(propertyName=="KnowContract$createDate"){
				columnItem.width=120;
			}
			if(propertyName=="KnowContract$createUser"){
				columnItem.width=100;
			}
			//2010-04-24 add by huzh for �е��� end
			columns[i + 1] = columnItem;
			fields[i] = propertyName;
		}
		this.storeMapping = fields;
		this.cm = new Ext.grid.ColumnModel(columns);
		this.store = da.getPanelJsonStore("KnowledgeContractList_pagepanel",
				DataHead);
		this.store.on("load",function(store,records, opt) {
					for(var i=0;i<records.length;i++){
						var cDate=records[i].get("KnowContract$createDate")
						if(cDate!=""){
							records[i].set("KnowContract$createDate",cDate.substring(0,16));
						}
						 if(records[i].get("KnowContract$status")==2){
							records[i].set("KnowContract$status","������");
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
					displayMsg : '��ǰ��ʾ {0}-{1}����¼ /��{2}����¼',
					emptyMsg : "����ʾ����"
				});
		this.searchConfig = function() {
			if(Ext.getCmp('createUserCombo').getRawValue()!=''&&Ext.getCmp('createUserCombo').getValue()==''){
			    Ext.Msg.alert("��ʾ","��������б���ѡ����ȷ�Ĵ����ˣ�");
			    return false;
			}
			if(Ext.getCmp('createUserCombo').getRawValue()==''){
				Ext.getCmp('createUserCombo').clearValue();
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
								text : '��ѯ',
								iconCls : 'search'
							},'-',{
								xtype : 'button',
								style : 'margin:2px 0px 2px 5px',
								handler : function() {
									Ext.getCmp("knowSearchForm").getForm()
											.reset();
								},
								text : '���',
								iconCls : 'reset'
							},new Ext.Toolbar.TextItem("<font color=red style='font-weight:lighter' >��˫����鿴��ϸ��Ϣ��</font>")],
					bbar : pageBar
				});

		this.konwledgeGrid.on("headerdblclick", this.fitWidth, this);
		this.konwledgeGrid.on("rowdblclick", this.lookInto, this);
		var items = new Array();
		items.push(this.fp);
		items.push(this.konwledgeGrid);
		this.items = items;

		KnowContractAuditPanel.superclass.initComponent.call(this);
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
