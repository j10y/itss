KnowledgeAuditPagePanel = Ext.extend(Ext.Panel, {
	id : "knowledgeAuditPanel",
	closable : true,
	frame : true,
	autoScroll : true,
	viewConfig : {
		autoFill : true,
		forceFit : true
	},
	layout : 'border',
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
		var recordId = record.get("Knowledge$id");
		window.location = webContext
				+ '/user/knowledgeAuditQuery/knowledgeAuditHis.jsp?dataId='
				+ recordId;

	},
	getSearchForm : function() {
		var serviceItemBySu = new Ext.form.ComboBox({
			name : "serviceItemBySu",
			id : 'serviceItemBySu',
			fieldLabel : "服务项",
			//width : 180,
			width : 200, //2010-04-16 modified by huzh for bug(分页工具栏“最后一页”标记显示不出来)
			hiddenName : 'serviceItem',
			displayField : 'name',
			valueField : 'id',
			lazyRender : true,
			minChars : 50,
			emptyText :'请从下拉列表中选择...',
			resizable : true,
			triggerAction : 'all',
			selectOnFocus : true,
			store : new Ext.data.JsonStore({
				url : webContext
						+ '/eventAction_findServiceItem.action',
				fields : ['id', 'name'],
				totalProperty : 'rowCount',
				root : 'data',
				id : 'id'
			}),
			pageSize : 10,
			listeners : {
				'beforequery' : function(queryEvent) {
					Ext.getCmp('problemTypebySu').clearValue();
//					var discValue = Ext.getCmp('serviceItemTypebySu').getValue();
					var param = queryEvent.combo.getRawValue();
					var val = queryEvent.combo.getValue();
					if (queryEvent.query == '') {
						param = '';
					}
					this.store.baseParams={"name" : param,official:1};
					this.store.load();
					return false;
				},
				"select" :function(){
					 Ext.getCmp('problemTypebySu').clearValue();
				}
			}
		});
		var problemTypebySu = new Ext.form.ComboBox({
			name : "problemTypebySu",
			id : 'problemTypebySu',
			fieldLabel : "问题类型",
			//width : 180,
			width : 200, //2010-04-16 modified by huzh for bug(分页工具栏“最后一页”标记显示不出来)
			hiddenName : 'knowProblemType',
			displayField : 'name',
			valueField : 'id',
			lazyRender : true,
			minChars : 50,
			resizable : true,
			emptyText :'请从下拉列表中选择...',
			triggerAction : 'all',
			selectOnFocus : true,
			store : new Ext.data.JsonStore({
				url : webContext  + '/knowledgeAction_findKnowProblemType.action',
				fields : ['id', 'name'],
				totalProperty : 'rowCount',
				root : 'data',
				id : 'id'
			}),
			pageSize : 10,
			listeners : {
				'beforequery' : function(queryEvent) {
					var discValue = Ext.getCmp('serviceItemBySu').getValue();
					var param = queryEvent.combo.getRawValue();
					var val = queryEvent.combo.getValue();
					if (queryEvent.query == '') {
						param = '';
					}
					this.store.baseParams={
						"name" : param,
						serviceItem : discValue,
						deleteFlag:0//过滤掉已删除的问题类型
						};
					this.store.load();
					return false;
				}
			}
		});
		var summary = new Ext.form.TextField({
			name : "summary",
			fieldLabel : "具体问题",
			id : 'summary',
			//width : 180
			width : 200
		});
		this.panel = new Ext.form.FormPanel({
			id:"knowSearchForm",
			region : "north", 
			layout : 'table',
			height : 60,
//			width : 'auto',
			frame : true,
//			autoScroll : true,
			collapsible : true,
			defaults : {
				bodyStyle : 'padding:2px'
			},
			layoutConfig : {
				columns : 6
			},
			title : '查询条件',
			items : [{
				html : "服务项:",
				cls : 'common-text',
				width : 80,
				style : 'width:150;text-align:right'
			}, serviceItemBySu, {
				html : "问题类型:",
				cls : 'common-text',
				width : 80,
				style : 'width:150;text-align:right'
			}, problemTypebySu, {
				html : "具体问题:",
				cls : 'common-text',
				width : 80,
				style : 'width:150;text-align:right'
			}, summary]
		});

		return this.panel;
	},

	items : this.items,
	initComponent : function() {
		var da = new DataAction();
		var DataHead = da.getListPanelElementsForHead("KnowledgeSolutionQuery_pagepanel");
		this.modelTableName = "Knowledge";
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
			 if(propertyName=="Knowledge$knowledgeCisn"
                 ||propertyName=="Knowledge$createDate"
                   ||propertyName=="Knowledge$createUser"
                    ||propertyName=="Knowledge$oldKnowledge"){
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
			if(propertyName=="Knowledge$createUser"){
				columnItem.width=100;
			}
			if(propertyName=="Knowledge$createDate"){
				columnItem.width=120;
			}
			if(propertyName=="Knowledge$summary"){
				columnItem.width=200;
			}
			if(propertyName=="Knowledge$id"){
				columnItem.width=70;
			}
			if(propertyName=="Knowledge$knowProblemType"){
				columnItem.width=180;
			}
			if(propertyName=="Knowledge$serviceItem"){
				columnItem.width=180;
			}
			//2010-04-24 add by huzh for 列调整 end
			columns[i + 1] = columnItem;
			fields[i] = propertyName;
		}
		this.storeMapping = fields;
		this.cm = new Ext.grid.ColumnModel(columns);
		this.store = da.getPanelJsonStore("KnowledgeSolutionList_pagepanel",DataHead);
		this.store.on("load",function(store,records, opt) {
					for(var i=0;i<records.length;i++){
						var cDate=records[i].get("Knowledge$createDate");
						if(cDate!=""){
							records[i].set("Knowledge$createDate",cDate.substring(0,16));
						}
						 if(records[i].get("Knowledge$status")==2){
							records[i].set("Knowledge$status","审批中");
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
			if((Ext.getCmp('serviceItemBySu').getRawValue() != '' 
		              && Ext.getCmp('serviceItemBySu').getValue() == '')
		                ||(Ext.getCmp('problemTypebySu').getRawValue() != '' 
		                  && Ext.getCmp('problemTypebySu').getValue() == '')){
			    Ext.Msg.alert("提示","请从下拉列表中选择正确的服务项类型、服务项或问题类型！");
			    return;
		    }
			if(Ext.getCmp('serviceItemBySu').getRawValue()==''){
				Ext.getCmp('serviceItemBySu').clearValue();
			}
			if(Ext.getCmp('problemTypebySu').getRawValue()==''){
				Ext.getCmp('problemTypebySu').clearValue();
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
							},'-', {
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

		this.konwledgeGrid.on("rowdblclick", this.lookInto, this);
		this.items = [this.fp,this.konwledgeGrid];
		KnowledgeAuditPagePanel.superclass.initComponent.call(this);
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
	}
});
