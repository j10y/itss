//�����������״̬�б�
PagePanel = Ext.extend(Ext.Panel, {
	id : "PagePanel",
	closable : true,
	viewConfig : {
		autoFill : true,
		forceFit : true
	},
	layout : 'border',
	getSearchForm : function() {
		this.requireComboBox = new Ext.form.ComboBox({
			id:"require",
			displayField : 'name',
			valueField : 'id',
			listAlign:"t-b",
			listWidth:240,
			triggerAction : 'all',
			emptyText : '��ѡ���������',
			store : new Ext.data.JsonStore({
				url : webContext + '/requireAction_findRequireByName.action',
				fields : ['id', 'name'],
				totalProperty : 'rowCount',
				root : 'data',
				id : 'id'
			}),
			pageSize : 10,
			listeners : {
				'beforequery' : function(queryEvent) {
					var param = queryEvent.combo.getRawValue();
					if (queryEvent.query == '') {
						param = '';
					}
					this.store.baseParams={"name" : param};
					this.store.load();
					return false;
				}
			}

		});
		this.panel = new Ext.form.FormPanel({
			id:"searchCriteria",
			region : "north",
			layout : 'table',
			height : 'auto',
			width : 'auto',
			frame : true,
			collapsible : true,
			defaults : {
				bodyStyle : 'padding:4px'
			},
			layoutConfig : {
				columns : 8
			},
			title : "��ѯ�б�",
			items : [{
				html : "������:",
				cls : 'common-text',
				width : 70,
				style : 'width:150;text-align:left'
			},
			new Ext.form.TextField({
				id:"applyNum",
				emptyText : '��������'
			}), 
			{
				html : "��������:",
				cls : 'common-text',
				width : 70,
				style : 'width:150;text-align:left'
			}, 
			this.requireComboBox,
			{
				html : "����ʱ��:",
				cls : 'common-text',
				width : 70,
				style : 'width:150;text-align:left'
			},
			new Ext.form.DateField({
				id:"applyDate",
				width:150,
				format:"Y-m-d",
				altFormats:"Y-m-d",
				emptyText :'��ѡ������'
			}), 
			{
				html : "״̬:",
				cls : 'common-text',
				width : 45,
				style : 'width:150;text-align:left'
			},
			new Ext.form.ComboBox({
				id:'sta',
				mode :'local',
				editable :false,
				triggerAction :'all',
				hiddenName:'status',
				displayField :'name',
				valueField :'status',
				emptyText :'��ѡ��״̬',
				value:"3",
				store :new Ext.data.SimpleStore( {
					fields : [ "status", "name" ],
					data : [ [ 0, "�ݸ�" ], [ 1, "������" ],[2,"�������"],["3","��"]]
			})
			})]
		});
		return this.panel;
	},
	
	getBusinessAccountGrid : function(){
		this.store=new Ext.data.JsonStore({
			url : webContext + '/businessAccountAction_findBusinessAccount.action',
				fields : ['id', 'applyNum','require','descn','applyUser','applyDate','status'],
				totalProperty : 'rowCount',
				root : 'data'
		});
		this.cm = new Ext.grid.ColumnModel([
			{header : "���",dataIndex : "applyNum",width : 180,sortable: true}, 
			{header : "��������",dataIndex : "require",width : 180,sortable: true},
			{header : "��������",dataIndex : "descn",width : 180,sortable: true},
			{header : "������",dataIndex : "applyUser",width : 180,sortable: true},
			{header : "����ʱ��",dataIndex : "applyDate",width : 180,sortable: true},
			{header : "״̬",dataIndex : "status",width : 180,sortable: true}
		]);
		this.pageBar = new Ext.PagingToolbar({
			pageSize : 10,
			store : this.store,
			displayInfo : true,
			displayMsg : '��ǰ��ʾ {0}-{1}����¼ /��{2}����¼',
			emptyMsg : "����ʾ����"
		});
		this.businessAccountGrid = new Ext.grid.GridPanel({
			id:"businessAccountGrid",
			region : "center",
			store : this.store,
			cm : this.cm,
			trackMouseOver : false,
			loadMask : true,
			bbar : this.pageBar,
			tbar : [{
						text : "��ѯ",
						pressed : true,
						scope : this,
						iconCls : "search",
						handler : function() {
								Ext.getCmp("businessAccountGrid").store.baseParams={
											applyNum:Ext.getCmp("applyNum").getValue(),
											requireId:Ext.getCmp("require").getValue(),
											applyDate:Ext.getCmp("applyDate").getRawValue(),
											status:Ext.getCmp("sta").getValue()};
								Ext.getCmp("businessAccountGrid").store.load();
						}
					},  
					{
					    text : "����",
						pressed : true,
						scope : this,
						iconCls :"reset",
						handler : function() {
							Ext.getCmp("searchCriteria").form.reset();
						
						}
					}]
		});
		return this.businessAccountGrid;
	},
	
	items : this.items,
	
	initComponent : function(){
		var businessAccountGrid=this.getBusinessAccountGrid();
		businessAccountGrid.on("rowdblclick", function(){
			var record = businessAccountGrid.getSelectionModel().getSelected();
			var recordId = record.get("id");
			window.location = webContext+'/businessAccountAction_pageForward.action?businessAccountId='+ recordId;
		}, this);
		var items = new Array();
		items.push(this.getSearchForm());
		items.push(businessAccountGrid);
		this.items = items;
		PagePanel.superclass.initComponent.call(this);
		businessAccountGrid.getStore().baseParams={applyNum:"",requireId:"",applyDate:"",status:"0"};
		businessAccountGrid.getStore().load();
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
