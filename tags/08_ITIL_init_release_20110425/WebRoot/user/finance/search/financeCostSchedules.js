PagePanel = Ext.extend(Ext.Panel, {
	closable : true,
	height:800,
	layout : 'border',
	getSearchForm : function() {
		
		var costReduceTypeStore = new Ext.data.SimpleStore({
				fields : ['value', 'id'],
				data : [['������', "1"], ['������', "2"]]
		});
	
		this.panel = new Ext.form.FormPanel({
			id:"searchForm",
			region : "north", 
			layout : 'table',
			height : 40,
			frame : true,
			defaults : {
				bodyStyle : 'padding:4px'
			},
			layoutConfig : {
				columns : 4
			},
			items : [{
				html : "�ɱ��������:",
				cls : 'common-text',
				width : 150,
				style : 'width:150;text-align:center'
			}, {
				xtype : 'combo',
				readOnly : false,
				id : "costReduceTypeId",
				name : "costReduceType",
				hiddenName : "costReduceType",
				fieldLabel : '�ɱ��������',
				store : costReduceTypeStore,
				displayField : 'value',
				valueField : "id",
				allowBlank : false,
				triggerAction : "all",
				emptyText : "��ѡ��",
				mode : 'local',
				listeners : {
					"select" : function() {
						var tet = Ext.get("costReduceTypeId").dom.value;
						if (tet == "������") {
							Ext.getCmp('configItemId').setRawValue("");
							Ext.fly('configItem').dom.innerHTML='������<font color=\"red\">*</font>:&nbsp;';
						} else {
							Ext.getCmp('configItemId').setRawValue("");
							Ext.fly('configItem').dom.innerHTML='������<font color=\"red\">*</font>:&nbsp;';
							
						}
					},
					'blur' :  function(combo){
							var nowVal = combo.getRawValue();
							if(nowVal!=""&&nowVal!="������"&&nowVal!="������"){
								combo.setValue("");
								Ext.Msg.alert("��ʾ��Ϣ","����ֱ�����룬���ѯ��ѡ��");
							}
					}
				},
				width : 200
			}]
		});
		return this.panel;
	},
	items : this.items,
	initComponent : function(){
		var sm = new Ext.grid.CheckboxSelectionModel();
		this.fp = this.getSearchForm();
		this.store = new Ext.data.JsonStore({
				url : webContext + "/CostHandInputAction_getCostSchedulesList.action",
				fields : ['id', 'costReduceType', 'costItem', 'financeCostTypeName','amount','reimbursementName','serviceProvider','costCenterName','costDateName','borrowDateName','borrowTypeName','costDetailExplanation','costDataSourceName','costAuditUserName','costApplyUserName','costApplyDateName'],
				root : 'data',
				totalProperty : 'rowCount',
				defaultParam:""
			});
		var pageBar = new Ext.PagingToolbar({
			pageSize : 10,
			store : this.store,
			displayInfo : true,
			displayMsg : '��ǰ��ʾ {0}-{1}����¼ /��{2}����¼',
			emptyMsg : "����ʾ����"
		});
		
		var searchConfig=function(){
			var param = Ext.getCmp("searchForm").getForm().getValues(false);
			var store=Ext.getCmp("grid").getStore();
	        param.start = 0;  
	        store.on('beforeload', function(a) {   
					      Ext.apply(a.baseParams,param);   
					});
	        store.load({
	            param : param
	        });
		};
		var searchButton = new Ext.Button({
			style : 'margin:2px 0px 2px 5px',
			handler :searchConfig,
			pressed:true,
			text : '��ѯ',
			iconCls : 'search'
		});
		var resetButton = new Ext.Button({
			pressed:true,
			style : 'margin:2px 0px 2px 5px',
			handler :function(){
				Ext.getCmp("searchForm").getForm().reset();
			},
			text : '����',
			iconCls : 'reset'
		});
		
		var mybuttons = new Array();
		var buttonUtil = new ButtonUtil();
		mybuttons.push(searchButton);
		mybuttons.push(resetButton);

		
		this.grid = new Ext.grid.GridPanel({
			id:"grid",
			name:"grid",
			region : "center",
			store : this.store,
			columns : [sm, {
				header : '�Զ����',
				dataIndex : 'id',
				align : 'left',
				sortable : true,
				width:150,
				hidden : true
			},  {
				header : '�ɱ��������',
				dataIndex : 'costReduceType',
				align : 'center',
				width:50,
				sortable : false,
				hidden : false
			}, {
				header : '�ɱ���',
				dataIndex : 'costItem',
				align : 'center',
				sortable : false,
				width:150,
				hidden : false
			}, {
				header : '��������',
				dataIndex : 'financeCostTypeName',
				align : 'center',
				sortable : true,
				width:150,
				hidden : false
			},{
				header : '�ɱ��������',
				dataIndex : 'amount',
				align : 'center',
				sortable : false,
				width:80,
				hidden : false
			}, {
				header : '��������Ա',
				dataIndex : 'reimbursementName',
				align : 'center',
				sortable : false,
				width:150,
				hidden : false
			}, {
				header : '�����ṩ��',
				dataIndex : 'serviceProvider',
				align : 'center',
				sortable : false,
				width:150,
				hidden : true
			}, {
				header : '�ɱ�����',
				dataIndex : 'costCenterName',
				align : 'center',
				sortable : false,
				width:150,
				hidden : false
			}, {
				header : '��ǰ���÷�������',
				dataIndex : 'costDateName',
				align : 'center',
				sortable : false,
				width:150,
				hidden : false
			}, {
				header : '�������',
				dataIndex : 'borrowDateName',
				align : 'center',
				sortable : false,
				width:150,
				hidden : true
			}, {
				header : '�������',
				dataIndex : 'borrowTypeName',
				align : 'center',
				sortable : false,
				width:150,
				hidden : true
			}, {
				header : '����������Դ',
				dataIndex : 'costDataSourceName',
				align : 'center',
				sortable : false,
				width:150,
				hidden : false
			}, {
				header : '������ϸ˵��',
				dataIndex : 'costDetailExplanation',
				align : 'center',
				sortable : false,
				width:150,
				hidden : true
			}, {
				header : '����������',
				dataIndex : 'costAuditUserName',
				align : 'center',
				sortable : false,
				width:150,
				hidden : false
			}, {
				header : '�����ύ��',
				dataIndex : 'costApplyUserName',
				align : 'center',
				sortable : false,
				width:150,
				hidden : false
			}, {
				header : '�����ύ����',
				dataIndex : 'costApplyDateName',
				align : 'center',
				sortable : false,
				width:150,
				hidden : false
			}
			],
			sm : sm,
			loadMask : true,
			tbar : mybuttons,
			bbar : pageBar,
			listeners :{} 
		});
		var items = new Array();
		items.push(this.fp);
		items.push(this.grid);
		this.items = items;
		var params={start:0};
		this.store.on('beforeload', function(a) {   
					      Ext.apply(a.baseParams,params);   
					});
        this.store.load({
            params :params
        });
		PagePanel.superclass.initComponent.call(this);
	}
});