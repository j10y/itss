PagePanel = Ext.extend(Ext.Panel, {
	id : "PagePanel",
	//title:this.pageModeltitle,
	closable : true,
	autoScroll : true,
	viewConfig : {
		autoFill : true,
		forceFit : true
	},
	layout : 'border',
	getSearchForm : function() {
		this.panel = new Ext.form.FormPanel({
			region : "north",
			layout : 'table',
			height : 105,
			id : 'eventSearchForm',
			width : 'auto',
			frame : true,
			collapsible : true,
			defaults : {
				bodyStyle : 'padding:4px'
			},
			layoutConfig : {
				columns : 6
			},
			title : "��ѯ�б�",
			items : [{
						html:'�¼����ƣ�',
						cls : 'common-text'	,
						style : 'width:100;text-align:right;'							
					},new Ext.form.TextField({
						id : 'eventName',
						name : 'eventName',
						width : 160,
						fieldLabel : '�¼�����'							
					}),{
						html:'�¼���ţ�',
						cls : 'common-text'	,
						style : 'width:100;text-align:right;'							
					},new Ext.form.TextField({
						id : 'eventCisn',
						name : 'eventCisn',
						width : 160,
						fieldLabel : '�¼����'							
					}),{
						html:'�¼�״̬��',
						cls : 'common-text'	,
						style : 'width:100;text-align:right;'							
					},new Ext.form.ComboBox({
						id : 'eventStatus',
						name : 'eventStatus',
					    lazyRender:true,
					    mode: 'local',
					    forceSelection :true,
					    width : 160,
					    triggerAction: 'all',
					    store: new Ext.data.SimpleStore({
					    	fields:['valueId','stateName'],
					    	data:[[2,'������'],[4,'�ѽ���'],[5,'����ָ��'],[8,'�ȴ��û�ȷ��'],[]]
					    }),
					    valueField : 'valueId',
						displayField : 'stateName'														
					}),
					{
						html:'֧���飺',
						cls : 'common-text',
						style : 'width:100;text-align:right;'								
					},new Ext.form.ComboBox({
				        id : 'supportGroup',
				        displayField : 'groupName',
				        valueField : 'id',
				        lazyRender : true,
				        allowBlank : true,
				        name : 'supportGroup',
				        forceSelection :true,
				        resizable : true,
				        triggerAction : 'all',
				        pageSize : 10,
				        selectOnFocus : true,
				        width : 160,
				        store : new Ext.data.JsonStore({
				            url : webContext + '/supportGroupAction_findSupportGroup.action',
				            fields : ['id', 'groupName'],
				            totalProperty : 'rowCount',
				            root : 'data',
				            id : 'id'
				        }),
				        listeners : {
				            'beforequery' : function(queryEvent) {
				                var param = queryEvent.combo.getRawValue();
				                if (queryEvent.query == '') {
				                    param = '';
				                }
				                this.store.baseParams={"groupName" : param,"deleteFlag" :0};
				                this.store.load();
				                return false;
				            }
			            }
				    }),{
						html:'����ˣ�',
						cls : 'common-text',
						style : 'width:100;text-align:right;'								
					},new Ext.form.TextField({
						id : 'submitUser',
						name : 'submitUser',
						width : 160,
						fieldLabel : '�����'	,
						emptyText :'ItCode������'
					}),{
						html:'�����ˣ�',
						cls : 'common-text',
						style : 'width:100;text-align:right;'								
					},new Ext.form.TextField({
						id : 'createUser',
						name : 'createUser',
						width : 160,
						fieldLabel : '������'	,
						emptyText :'ItCode������'
					}),{
						html:'�����ˣ�',
						cls : 'common-text',
						style : 'width:100;text-align:right;'								
					},new Ext.form.TextField({
						id : 'dealUser',
						name : 'dealUser',
						width : 160,
						fieldLabel : '������'	,
						emptyText :'ItCode������'
					})
			]
		});
		return this.panel;
	},
	items : this.items,
	history : function() {
			var record = this.grid.getSelectionModel().getSelected();
			var recordId = record.get("id");
			window.location = webContext
					+ '/user/event/searchEventStatus/eventAuditHis.jsp?dataId=' + recordId;
		},
	
	initComponent : function(){
		var da = new DataAction();
        this.searchForModel=function() {
			if(Ext.getCmp("eventStatus").getRawValue()==""){
				Ext.getCmp("eventStatus").setValue("");
			}
			if(Ext.getCmp("supportGroup").getRawValue()==""){
				Ext.getCmp("supportGroup").setValue("");
			}
			var tempStore = Ext.getCmp("searchGrid").getStore();
			var params={
						summary :  Ext.getCmp("eventName").getValue(),
						eventCisn :  Ext.getCmp("eventCisn").getValue(),
						createUser :  Ext.getCmp("createUser").getValue(),
						dealUser :  Ext.getCmp("dealUser").getValue(),
						submitUser :  Ext.getCmp("submitUser").getValue(),
						eventStatus :  Ext.getCmp("eventStatus").getValue(),
						supportGroup :  Ext.getCmp("supportGroup").getValue(),
						start:0};
			tempStore.on('beforeload', function(a) {   
					      Ext.apply(a.baseParams,params);   
					});
			tempStore.load({
				params:params
			})
		},
		this.fp = this.getSearchForm();
		this.modelTableName="Event";
		var columns = new Array();
		var fields = new Array();
		var sm = new Ext.grid.CheckboxSelectionModel();
                    
		var gridStore = new Ext.data.JsonStore({
			id : 'searchGridStore',
			url : webContext + '/eventAction_findAllEventByParams.action',
			fields : ['id','eventCisn','summary', 'createUser','submitUser', 'submitDate', 'dealuser',
					'eventStatus', 'ponderance','closedDate','frequency','supportGroup'],
			totalProperty : "rowCount",
			root : "data"					
		});
		var viewConfig = Ext.apply({
			forceFit : true
		}, this.gridViewConfig);

		this.pageBar = new Ext.PagingToolbar({
			id  : 'searchPageBar',
			pageSize : 10,
			store : gridStore,
			displayInfo : true,
			displayMsg : '��ǰ��ʾ {0}-{1}����¼ /��{2}����¼',
			emptyMsg : "����ʾ����"
		});
		this.grid = new Ext.grid.GridPanel({
			id : 'searchGrid',
			region : "center",
			store : gridStore,
			sm : sm,
			columns : [sm,
					 {	
					 	id : 'id',
						header : "���",
						width : 70,
						sortable : true,
						dataIndex : 'id',
						hidden : true
					},{
						header : "�¼����",
						width : 80,
						sortable : true,
						dataIndex : 'eventCisn',
						hidden : false
					},{
						header : "�¼�����",
						width : 150,
						sortable : true,
						dataIndex : 'summary',
						hidden : false
					},{
						header : "�¼�������",
						width : 100,
						sortable : true,
						dataIndex : 'createUser',
						hidden : false
					},{
						header : "�¼������",
						width : 100,
						sortable : true,
						dataIndex : 'submitUser',
						hidden : false
					},{
						header : "�¼�������",
						width : 100,
						sortable : true,
						dataIndex : 'dealuser',
						hidden : false
					},{
						header : "֧����",
						width : 110,
						sortable : true,
						dataIndex : 'supportGroup',
						hidden : false
					},{
						header : "�¼�״̬",
						width : 90,
						sortable : true,
						dataIndex : 'eventStatus',
						hidden : false
					},{
						header : "�¼��ύ����",
						width : 100,
						sortable : true,
						dataIndex : 'submitDate',
						hidden : false
					},{
						header : "�¼�����ʱ��",
						width : 100,
						sortable : true,
						dataIndex : 'closedDate',
						hidden : false
					},{
						header : "�¼�������",
						width : 80,
						sortable : true,
						dataIndex : 'ponderance',
						hidden : false
					},{
						header : "�¼�����Ƶ��",
						width : 90,
						sortable : true,
						dataIndex : 'frequency',
						hidden : false
					}
				],
			trackMouseOver : false,
			loadMask : true,
			y : 140,
			anchor : '0 -35',
			tbar : [{
					xtype : 'button',
					style : 'margin:2px 0px 2px 5px',
					handler :this.searchForModel,
					text : '��ѯ',
					iconCls : 'search'
				},'-',{
					xtype : 'button',
					style : 'margin:2px 0px 2px 5px',
					handler :function(){
						Ext.getCmp("eventSearchForm").getForm().reset();
					},
					text : '���',
							iconCls : 'reset'
				},
				{xtype: 'tbtext',text:"��<font style='font-weight:lighter' color=red >˫����¼�鿴��ϸ��Ϣ</font>��"}],
			bbar : this.pageBar
		});
		this.grid.on("headerdblclick", this.fitWidth, this);
		this.grid.on("rowdblclick", this.history, this);
		var items = new Array();
		items.push(this.fp);
		items.push(this.grid);
		this.items = items;
		PagePanel.superclass.initComponent.call(this);
		var params = {
			'mehtodCall' : 'query',
			'start' : 0
		};		
		gridStore.on('beforeload', function(a) {   
					      Ext.apply(a.baseParams,params);   
					});

		gridStore.removeAll();
		gridStore.load({
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