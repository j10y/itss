PagePanel = Ext.extend(Ext.Panel, {
	closable : true,
	height:800,
	layout : 'border',
	getSearchForm : function() {
		
		var costReduceTypeStore = new Ext.data.SimpleStore({
				fields : ['value', 'id'],
				data : [['配置项', "1"], ['服务项', "2"]]
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
				html : "成本归结类型:",
				cls : 'common-text',
				width : 150,
				style : 'width:150;text-align:center'
			}, {
				xtype : 'combo',
				readOnly : false,
				id : "costReduceTypeId",
				name : "costReduceType",
				hiddenName : "costReduceType",
				fieldLabel : '成本归结类型',
				store : costReduceTypeStore,
				displayField : 'value',
				valueField : "id",
				allowBlank : false,
				triggerAction : "all",
				emptyText : "请选择",
				mode : 'local',
				listeners : {
					"select" : function() {
						var tet = Ext.get("costReduceTypeId").dom.value;
						if (tet == "配置项") {
							Ext.getCmp('configItemId').setRawValue("");
							Ext.fly('configItem').dom.innerHTML='配置项<font color=\"red\">*</font>:&nbsp;';
						} else {
							Ext.getCmp('configItemId').setRawValue("");
							Ext.fly('configItem').dom.innerHTML='服务项<font color=\"red\">*</font>:&nbsp;';
							
						}
					},
					'blur' :  function(combo){
							var nowVal = combo.getRawValue();
							if(nowVal!=""&&nowVal!="配置项"&&nowVal!="服务项"){
								combo.setValue("");
								Ext.Msg.alert("提示信息","不能直接输入，请查询后选择！");
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
			displayMsg : '当前显示 {0}-{1}条记录 /共{2}条记录',
			emptyMsg : "无显示数据"
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
			text : '查询',
			iconCls : 'search'
		});
		var resetButton = new Ext.Button({
			pressed:true,
			style : 'margin:2px 0px 2px 5px',
			handler :function(){
				Ext.getCmp("searchForm").getForm().reset();
			},
			text : '重置',
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
				header : '自动编号',
				dataIndex : 'id',
				align : 'left',
				sortable : true,
				width:150,
				hidden : true
			},  {
				header : '成本归结类型',
				dataIndex : 'costReduceType',
				align : 'center',
				width:50,
				sortable : false,
				hidden : false
			}, {
				header : '成本项',
				dataIndex : 'costItem',
				align : 'center',
				sortable : false,
				width:150,
				hidden : false
			}, {
				header : '费用类型',
				dataIndex : 'financeCostTypeName',
				align : 'center',
				sortable : true,
				width:150,
				hidden : false
			},{
				header : '成本发生金额',
				dataIndex : 'amount',
				align : 'center',
				sortable : false,
				width:80,
				hidden : false
			}, {
				header : '财务报销人员',
				dataIndex : 'reimbursementName',
				align : 'center',
				sortable : false,
				width:150,
				hidden : false
			}, {
				header : '服务提供商',
				dataIndex : 'serviceProvider',
				align : 'center',
				sortable : false,
				width:150,
				hidden : true
			}, {
				header : '成本中心',
				dataIndex : 'costCenterName',
				align : 'center',
				sortable : false,
				width:150,
				hidden : false
			}, {
				header : '当前费用发生日期',
				dataIndex : 'costDateName',
				align : 'center',
				sortable : false,
				width:150,
				hidden : false
			}, {
				header : '借款日期',
				dataIndex : 'borrowDateName',
				align : 'center',
				sortable : false,
				width:150,
				hidden : true
			}, {
				header : '借款类型',
				dataIndex : 'borrowTypeName',
				align : 'center',
				sortable : false,
				width:150,
				hidden : true
			}, {
				header : '费用数据来源',
				dataIndex : 'costDataSourceName',
				align : 'center',
				sortable : false,
				width:150,
				hidden : false
			}, {
				header : '费用明细说明',
				dataIndex : 'costDetailExplanation',
				align : 'center',
				sortable : false,
				width:150,
				hidden : true
			}, {
				header : '费用审批人',
				dataIndex : 'costAuditUserName',
				align : 'center',
				sortable : false,
				width:150,
				hidden : false
			}, {
				header : '费用提交人',
				dataIndex : 'costApplyUserName',
				align : 'center',
				sortable : false,
				width:150,
				hidden : false
			}, {
				header : '费用提交日期',
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