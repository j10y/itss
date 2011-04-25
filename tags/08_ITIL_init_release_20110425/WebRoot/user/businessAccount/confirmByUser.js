/*
 * 收款panel
 */
// function getApplyInfoPanel(){
function getFormPanel() {
	var formPanel = new Ext.form.FormPanel({
		id : "formPanel",
		//title : "收款申请",
		frame : true,
		width : 800,// this.width - 15,
		autoScroll : true,
		layout : 'table',
		layoutConfig : {
			columns : 4
		},
		items : [
		
		{
			html : "申请编号:&nbsp;",
			cls : 'common-text',
			style : 'width:150;text-align:right'
		}, {
			xtype : 'textfield',
			id : "applyNum",
			readOnly : true,
			emptyText : "系统自动生成",
			fieldLabel : '申请编号',
			height : 0,
			width : 200
		}, {
			html : "联系人:&nbsp;",
			cls : 'common-text',
			style : 'width:150;text-align:right'
		}, 
		new Ext.form.ComboBox({
			xtype : 'combo',
			hiddenName : 'relationUser',
			id : 'relationUserCombo',
			width : 200,
			readOnly : true,
			hideTrigger : true,
			fieldLabel : '联系人',
			lazyRender : true,
			displayField : 'userName',
			valueField : 'id',
			emptyText : '请选择...',
			allowBlank : true,
			typeAhead : true,
			name : 'relationUser',
			triggerAction : 'all',
			minChars : 50,
			queryDelay : 700,
			store : new Ext.data.JsonStore({
				url : webContext
						+ '/extjs/comboDataAction?clazz=com.digitalchina.info.framework.security.entity.UserInfo',
				fields : ['id', 'userName'],
				listeners : {
					beforeload : function(store, opt) {
						if (opt.params['relationUser'] == undefined) {
							opt.params['userName'] = Ext.getCmp('relationUserCombo').defaultParam;
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
			},
			initComponent : function() {
				this.store.load({
					params : {
						id : Ext.getCmp('relationUserCombo').getValue(),
						start : 0
					},
					callback : function(r, options, success) {
						Ext.getCmp('relationUserCombo').setValue(Ext.getCmp('relationUserCombo').getValue());
					}
				});
			}
		}), 
			/*{
			html : "申请理由:&nbsp;",
			cls : 'common-text',
			style : 'width:150;text-align:right'
		}, */new Ext.form.Hidden({
			xtype : "textarea",
			width : 546,
			readOnly : true,
			id : "apply$desc",
			height : 50,
			colspan : 3
		}),new Ext.form.Hidden({
				xtype : 'hidden',
				id : 'businessAccountId',
				name : 'businessAccountId',
				width : 200,
				fieldLabel : '自动编号'
			})]
	})
	Ext.getCmp('applyNum').setValue(this.applyNum);
	Ext.getCmp('relationUserCombo').setValue(this.userId);
	Ext.getCmp('relationUserCombo').initComponent();
	Ext.getCmp('apply$desc').setValue(this.descn);
	return formPanel;
}
function getIncomeGridPanel() {
   var sm = new Ext.grid.CheckboxSelectionModel();
  
   this.store = new Ext.data.JsonStore({ 
                url : webContext+'/businessAccountAction_getAllIncomeInfo.action?baId='+this.dataId,
                fields:[{name:'SRIncomePlan$id'},
                {name:'SRIncomePlan$name'},
            	{name:'SRIncomePlan$descn'},
              	{name:'plan$money'},
              	{name:'plan$startDate'},
               	{name:'plan$endDate'},
               	{name:'plan$balance'},
               	{name:'itil_RealIncome$id'},
               	{name:'itil_RealIncome$realDate'},
               	{name:'itil_RealIncome$costCenter'},
               	{name:'itil_RealIncome$realMoney'}],
                totalProperty:"rowCount",
                root:"data",
                remoteSort:false,             
               	timeout:8000
                });
	var viewConfig = Ext.apply({
		forceFit : true
	}, this.gridViewConfig);
	this.formValue = '';
	var incomeGrid= new Ext.grid.GridPanel({
		id : 'incomeGrid',
		store : this.store,
		sm : sm,
		columns:[sm,
			{header:'自动编号',dataIndex:'SRIncomePlan$id',align : 'left',sortable : true,hidden:true},
			{header:'收款名称',dataIndex:'SRIncomePlan$name',align : 'left',sortable : true,hidden:false},
			{header:'收款描述',dataIndex:'SRIncomePlan$descn',align : 'left',sortable : true,hidden:false},
			{header:'收款金额',dataIndex:'plan$money',align : 'left',sortable : true,hidden:true},
			{header:'计划开始时间',dataIndex:'plan$startDate',align : 'left',sortable : true,hidden:false},
			{header:'计划结束时间',dataIndex:'plan$endDate',align : 'left',sortable : true,hidden:false},
			{header:'剩余金额',dataIndex:'plan$balance',align : 'left',sortable : true,hidden:false},
			{header:'自动编号',dataIndex:'itil_RealIncome$id',align : 'left',sortable : true,hidden:true},
			{header:'实际收款时间',dataIndex:'itil_RealIncome$realDate',align : 'left',sortable : true,hidden:true},
			{header:'收款成本中心',dataIndex:'itil_RealIncome$costCenter',align : 'left',sortable : true,hidden:false},
			{header:'实际收款金额',dataIndex:'itil_RealIncome$realMoney',align : 'left',sortable : true,hidden:false}],
		title :"收款条目",
		trackMouseOver : false,
		loadMask : true,
		clicksToEdit : 2,
		collapsible : true,
		autoScroll : true,
		loadMask : true,
		autoHeight : true,
		width : 800,
		frame : true
		
	});
	this.store.load();
	return incomeGrid;
}

function getFormpanel_SpecialRequire3_Input() {
	var da = new DataAction();
	var biddata = "";
	var data = da.getSingleFormPanelElementsForEdit("panel_SpecialRequire_Input", this.requireId);
	for (i = 0; i < data.length; i++) {
		if (data[i].xtype == 'combo' || data[i].xtype == 'datefield') {
			data[i].id = data[i].id + 8;// 改变Combobox的id
			data[i].readOnly = true;
			data[i].hideTrigger = true;
		}
		data[i].readOnly = true;
	}
	biddata = da.split(data);
	var formpanel_SpecialRequire3_Input = new Ext.form.FormPanel({
		id : 'panel_SpecialRequire3_Input',
		layout : 'table',
		height : 'auto',
		width : 800,
		frame : true,
		collapsible : true,
		defaults : {
			bodyStyle : 'padding:4px'
		},
		layoutConfig : {
			columns : 4
		},
		items : biddata
	});
	return formpanel_SpecialRequire3_Input;
}

//需求分析面吧
function getFormpanel_SRAnalyse() {
	var sra = new SRAction();
	var praId = sra.getProjectRequireAnalyseId(this.requireId);
	var da = new DataAction();
	var data = null;
	// 判断是新增还是修改
	var biddata = "";
	if (praId != '0') {
		data = da.getSingleFormPanelElementsForEdit("panel_SRAnalyse", praId);// 这是要随时变得
	} else {
		data = da.getSingleFormPanelElementsForEdit("panel_SRAnalyse", "");
	}
	biddata = da.splitForReadOnly(data);
	var formpanel_SRAnalyse = new Ext.form.FormPanel({
		id : 'panel_SRAnalyse',
		layout : 'table',
		height : 'auto',
		width : 800,
		frame : true,
		collapsible : true,
		defaults : {
			bodyStyle : 'padding:4px'
		},
		layoutConfig : {
			columns : 4
		},
		title : "项目需求分析",
		items : biddata
	});
	return formpanel_SRAnalyse;
}
//需求合同面板
function getFormpanel_SRprojectContracts() {
	var sra = new SRAction();
	var rcId = sra.getRequirementContractId(this.requireId);
	var da = new DataAction();
	var data = null;// 判断是新增还是修改
	var biddata = "";
	if (rcId != '0') {
		data = da.getSingleFormPanelElementsForEdit("panel_SRprojectContracts",rcId);// 这是要随时变得
	} else {
		data = da.getSingleFormPanelElementsForEdit("panel_SRprojectContracts","");
	}
	biddata = da.splitForReadOnly(data);
	var formpanel_SRprojectContracts = new Ext.form.FormPanel({
		id : 'panel_SRprojectContracts',
		layout : 'table',
		height : 'auto',
		width : 800,
		frame : true,
		collapsible : true,
		defaults : {
			bodyStyle : 'padding:4px'
		},
		layoutConfig : {
			columns : 4
		},
		title : "个性化需求合同",
		items : biddata
	});
	return formpanel_SRprojectContracts;
}

/*
 * tabPanel
 */
function getTabPanel() {
	var histroyForm = new HistroyForm({dataId : this.dataId});
	var tab = new Ext.TabPanel({
		title : "需求基本信息",
		deferredRender : false,
		activeTab : 0,
		frame : true,
		plain : true,
		width : Ext.get('gridWidth').getWidth() - 30,
		defaults : {
			autoHeight : true
		},
		items : [{
			title : "需求基本信息",
			width : "auto",
			items : [this.getFormpanel_SpecialRequire3_Input()]
		},{
			title : "需求分析信息",
			width : "auto",
			items : [this.getFormpanel_SRAnalyse()]
		},{
			title : "需求合同信息",
			width : "auto",
			items : [this.getFormpanel_SRprojectContracts()]
		},{
			title : "收款申请",
			width : "auto",
			items : [this.getFormPanel(), this.getIncomeGridPanel()]
		},
		histroyForm	
		]
	});
	return tab;
}