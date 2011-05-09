PagePanel = Ext.extend(Ext.Panel, {
	closable : true,
	viewConfig : {
		autoFill : true,
		forceFit : true 
	},
	layout : 'border',
	getSearchForm : function() {
		var serviceItemType = new Ext.form.ComboBox({
			hiddenName : 'serviceItemType',
			id : 'serviceItemTypeCombo',
			width : 200,
			displayField : 'name',
			valueField : 'id',
			allowBlank : true,
			hideTrigger:false,
			name : 'serviceItemType',
			triggerAction : 'all',
			store : new Ext.data.JsonStore({
				url : webContext
						+ '/extjs/comboDataAction?clazz=com.zsgj.itil.service.entity.ServiceItemType',
				fields : ['id', 'name'],
				listeners : {
					beforeload : function(store, opt) {
						if (opt.params['serviceItemType'] == undefined) {
							opt.params['name'] = Ext
									.getCmp('serviceItemTypeCombo').defaultParam;
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
		});
		var serviceType = new Ext.form.ComboBox({
			hiddenName : 'serviceType',
			id : 'serviceTypeCombo',
			width : 200,
			displayField : 'name',
			valueField : 'id',
			allowBlank : true,
			hideTrigger:false,
			name : 'serviceType',
			triggerAction : 'all',
			store : new Ext.data.JsonStore({
				url : webContext
						+ '/extjs/comboDataAction?clazz=com.zsgj.itil.service.entity.ServiceType',
				fields : ['id', 'name'],
				listeners : {
					beforeload : function(store, opt) {
						if (opt.params['serviceType'] == undefined) {
							opt.params['name'] = Ext
									.getCmp('serviceTypeCombo').defaultParam;
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
		});
		var serviceItemName = new Ext.form.TextField({
			name : "serviceItemName",
			id : "serviceItemName",
			width : 150
		});
		this.panel = new Ext.form.FormPanel({
			id:"searchForm",
			region : "north", 
			layout : 'table',
			height : 70,
			frame : true,
			collapsible : true,
			defaults : {
				bodyStyle : 'padding:4px'
			},
			items : [
//			{
//				html : "����������:",
//				cls : 'common-text',
//				width : 80,
//				style : 'width:150;text-align:right'
//			},serviceItemType,
			{
				html : "��������:",
				cls : 'common-text',
				width : 80,
				style : 'width:150;text-align:right'
			}, serviceType, {
				html : "����������:",
				cls : 'common-text',
				width : 80,
				style : 'width:150;text-align:right'
			}, serviceItemName
			]
		});

		return this.panel;
	},
	search : function(){
		//var tempServiceItemType = Ext.getCmp('serviceItemTypeCombo').getValue();
		var tempServiceType = Ext.getCmp('serviceTypeCombo').getValue();
		var tempServiceItemName = Ext.getCmp('serviceItemName').getValue();
		var param = {
					//serviceItemType : tempServiceItemType,
					serviceType : tempServiceType,
					serviceItemName : tempServiceItemName,
					start: 1
		};
	    this.formValue = param;
		this.pageBar.formValue = this.formValue;
		var formParam = Ext.encode(param);
		this.store.removeAll();
		this.store.load({
			params : param
		})
	},
	reset : function() {
		//Ext.getCmp('serviceItemTypeCombo').clearValue();
		Ext.getCmp('serviceTypeCombo').clearValue();
		Ext.getCmp('serviceItemName').setValue("");
      },
	items : this.items,
	initComponent : function(){		 
		
		this.fp = this.getSearchForm();
		var sm = new Ext.grid.CheckboxSelectionModel();
		this.store = new Ext.data.JsonStore({
			url : webContext + "/requireSIAction_listServiceItemByUserAction.action",
			
			fields: ['id','serviceItemCode','serviceType','serviceItemType','name','serviceStatus','beginDate','endDate','descn','costWay','official'],
			root:'data',
			totalProperty : 'rowCount',
			sortInfo: {field: "serviceItemCode", direction: "asc"}
		});
//		this.store.paramNames.sort = "orderBy";
//		this.store.paramNames.dir = "orderType";
		// this.cm.defaultSortable = true;
		var viewConfig = Ext.apply({
			forceFit : true
		}, this.gridViewConfig);

		this.pageBar = new Ext.PagingToolbar({
			pageSize : 10,
			store : this.store,
			displayInfo : true,
			displayMsg : '��ǰ��ʾ {0}-{1}����¼ /��{2}����¼',
			emptyMsg : "����ʾ����"
		});
		this.formValue = '';
		this.pageBar.formValue = this.formValue;
		this.grid = new Ext.grid.GridPanel({
			region : "center",
			store : this.store,
			columns : [sm, {header : '�Զ����',dataIndex : 'id',align : 'left',sortable : true,hidden : true}, 
						{header : '������', width: 300,dataIndex : 'serviceItemCode',align : 'left',sortable : true,hidden : false}, 
						{header : '��������', dataIndex : 'serviceType',align : 'left',sortable : true,hidden : true}, 
						{header : '��������',width: 500 ,dataIndex : 'name',align : 'left',sortable : true,hidden : false}, 
						{header : '����������',dataIndex : 'serviceItemType',align : 'left',sortable : true,hidden : true},
						{header : '������״̬',dataIndex : 'serviceStatus',align : 'left',sortable : true,hidden : true}, 
						{header : '��ʼʱ��',dataIndex : 'beginDate',align : 'left',sortable : true,hidden : true},
						{header : '����ʱ��',dataIndex : 'endDate',align : 'left',sortable : true,hidden : true},
						{header : '����������',dataIndex : 'descn',align : 'left',sortable : true,hidden : true},
						{header : '�Ʒѷ�ʽ	',dataIndex : 'costWay',align : 'left',sortable : true,hidden : true},
						{header : '֧����',dataIndex : 'official',align : 'left',sortable : true,hidden : true},						
						{header : "��������",
							width : 100,
							sortable : true,
							align : 'center',
							dataIndex : 'aaa',
							renderer : function(value,cellmeta,record,rowIndex,columnIndex,store){
								var curId = record.get('id');
								var serviceItemType = record.get('serviceItemType');
								var process = record.get('process');
								var enter = "";
								if(process=='false'){
									enter = "��������";
								}else if (serviceItemType == '250') {
									enter = "<a href='"+webContext+"\/requireSIAction_toRequireInfo2.action?id=" + curId +"'>��  ��</a>";
								} else {
									enter = "<a href='"+webContext+"\/requireSIAction_toRequireInfo.action?id=" + curId +"'>��  ��</a>";
								}
								return enter;
							}
					}					
					],
			sm : sm,
			trackMouseOver : false,
			loadMask : true,
			y : 140,
			height : 200,
			anchor : '0 -35',
			//bbar : this.pageBar,
			tbar : ['   ',  {
				text : '��ѯ',
				pressed : true,
				handler : this.search,
				scope : this,
				iconCls : 'search'
			}, '   ', {
				text : ' ����',
				pressed : true,
				handler : this.reset,
				scope : this,
				iconCls : 'reset'
			}]
			
		});

		this.grid.on("headerdblclick", this.fitWidth, this);
		this.grid.on("rowdblclick", this.modify, this);
		var items = new Array();
		items.push(this.fp);
		items.push(this.grid);
		this.items = items;

		PagePanel.superclass.initComponent.call(this);
		var param = {
			'mehtodCall' : 'query',
			'start' : 1,
			'status':1
		};

		//this.pageBar.formValue = param;

		this.store.removeAll();
		this.store.load({
			params : param
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
