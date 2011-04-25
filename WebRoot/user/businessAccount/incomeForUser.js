/*
 * �տ�panel
 */
// function getApplyInfoPanel(){
function getFormPanel() {
	var formPanel = new Ext.form.FormPanel({
		id : "formPanel",
		title : "�տ�����",
		frame : true,
		width : 800,// this.width - 15,
		autoScroll : true,
		layout : 'table',
		layoutConfig : {
			columns : 4
		},
		items : [
		
		{
			html : "������:&nbsp;",
			cls : 'common-text',
			style : 'width:150;text-align:right'
		}, {
			xtype : 'textfield',
			id : "applyNum",
			readOnly : true,
			emptyText : "ϵͳ�Զ�����",
			fieldLabel : '������',
			height : 0,
			width : 200
		}, {
			html : "��ϵ��:&nbsp;",
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
			fieldLabel : '��ϵ��',
			lazyRender : true,
			displayField : 'userName',
			valueField : 'id',
			emptyText : '��ѡ��...',
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
			html : "��������:&nbsp;",
			cls : 'common-text',
			style : 'width:150;text-align:right'
		},*/new Ext.form.Hidden({
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
				fieldLabel : '�Զ����'
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
	function showIncomeHis(){	
		var sm = new Ext.grid.CheckboxSelectionModel();
	   this.store = new Ext.data.JsonStore({ 
                    id: Ext.id(),
                    url : webContext+'/businessAccountAction_getAllIncomeInfo.action?baId='+this.requireId,
                    root:"data",
                    fields:[{name:'SRIncomePlan$id'},
                   			{name:'SRIncomePlan$name'},
                    		{name:'SRIncomePlan$descn'},
                    		{name:'SRIncomePlan$money'},
                    		{name:'SRIncomePlan$startDate'},
                    		{name:'SRIncomePlan$endDate'},
                    		{name:'itil_upDatePlan$id'},
                    		{name:'itil_upDatePlan$money'},
                    		{name:'itil_upDatePlan$startDate'},
                    		{name:'itil_upDatePlan$endDate'},
                    		{name:'itil_upDatePlan$descn'},
                    		{name:'itil_Realncome$id'},
                    		{name:'itil_Realncome$realIcomeDate'},
                    		{name:'itil_Realncome$costCenter'},
                    		{name:'itil_Realncome$realIcomeMoney'}],
                    totalProperty:"rowCount",
                    remoteSort:false,             
                   	timeout:8000
                    });
		var viewConfig = Ext.apply({
			forceFit : true
		}, this.gridViewConfig);
		this.formValue = '';
		this.allIncomeGrid= new Ext.grid.GridPanel({
			id : 'allIncome',
			store : this.store,
			sm : sm,
			columns:[sm,
					{header:'�Զ����',dataIndex:'SRIncomePlan$id',align : 'left',sortable : true,hidden:true},
					{header:'�տ�����',dataIndex:'SRIncomePlan$name',align : 'left',sortable : true,hidden:false},
					{header:'�տ�����',dataIndex:'SRIncomePlan$descn',align : 'left',sortable : true,hidden:false},
					{header:'ԭ�տ���',dataIndex:'SRIncomePlan$money',align : 'left',sortable : true,hidden:true},
					{header:'ԭ��ʼʱ��',dataIndex:'SRIncomePlan$startDate',align : 'left',sortable : true,hidden:true},
					{header:'ԭ����ʱ��',dataIndex:'SRIncomePlan$endDate',align : 'left',sortable : true,hidden:true},
					{header:'�Զ����',dataIndex:'itil_upDatePlan$id',align : 'left',sortable : true,hidden:true},
					{header:'���½��',dataIndex:'itil_upDatePlan$money',align : 'left',sortable : true,hidden:true},
					{header:'���¿�ʼ����',dataIndex:'itil_upDatePlan$startDate',align : 'left',sortable : true,hidden:true},
					{header:'���½�������',dataIndex:'itil_upDatePlan$endDate',align : 'left',sortable : true,hidden:true},
					{header:'����',dataIndex:'itil_upDatePlan$descn',align : 'left',sortable : true,hidden:true},
					{header:'�Զ����',dataIndex:'itil_Realncome$id',align : 'left',sortable : true,hidden:true},
					{header:'�տ�ɱ�����',dataIndex:'itil_Realncome$costCenter',align : 'left',sortable : true,hidden:false},
					{header:'ʵ���տ�Ǯ��',dataIndex:'itil_Realncome$realIcomeMoney',align : 'left',sortable : true,hidden:false},
					{header:'ʵ���տ�ʱ��',dataIndex:'itil_Realncome$realIcomeDate',align : 'left',sortable : true,hidden:false}],
			trackMouseOver : false,
			loadMask : true,
			collapsible : true,
			autoScroll : true,
			loadMask : true,
			autoHeight : true,
			width : 800,// this.width - 15,
			frame : true
		});
		this.store.load();
		var _window = new Ext.Window({
					id : '_window',
					title : '�տ��¼',
					maximizable : true,
					width : 600,
					height : 410,
					layout : 'table',
					layoutConfig : {
						cloumns : 1
					},
					items : [allIncomeGrid]
				});
		_window.show();
	}
	var incomeGrid= new Ext.grid.GridPanel({
		id : 'incomeGrid',
		store : this.store,
		sm : sm,
		columns:[sm,
			{header:'�Զ����',dataIndex:'SRIncomePlan$id',align : 'left',sortable : true,hidden:true},
			{header:'�տ�����',dataIndex:'SRIncomePlan$name',align : 'left',sortable : true,hidden:false},
			{header:'�տ�����',dataIndex:'SRIncomePlan$descn',align : 'left',sortable : true,hidden:true},
			{header:'�տ���',dataIndex:'plan$money',align : 'left',sortable : true,hidden:true},
			{header:'�ƻ���ʼʱ��',dataIndex:'plan$startDate',align : 'left',sortable : true,hidden:false},
			{header:'�ƻ�����ʱ��',dataIndex:'plan$endDate',align : 'left',sortable : true,hidden:false},
			{header:'ʣ����',dataIndex:'plan$balance',align : 'left',sortable : true,hidden:false},
			{header:'�Զ����',dataIndex:'itil_RealIncome$id',align : 'left',sortable : true,hidden:true},
			{header:'�տ�ɱ�����',dataIndex:'itil_RealIncome$costCenter',align : 'left',sortable : true,hidden:false},
			{header:'ʵ���տ���',dataIndex:'itil_RealIncome$realMoney',align : 'left',sortable : true,hidden:false},
			//{header:'ʵ���տ�ʱ��',dataIndex:'itil_Realncome$realIcomeDate',align : 'left',sortable : true,hidden:false}],
			{header:'<font color=red>ʵ���տ�ʱ��</font>',
					renderer:function(value){
						if(value instanceof Date) {
							return new Date(value).dateFormat('Y-m-d');
						}return value;
					},
					editor:new Ext.form.DateField({
						xtype:'datefield',
						allowBlank:false,
						validator:'',
						format:'Y-m-d',
						fieldLabel:'ʵ���տ�ʱ��'
					}),
					dataIndex:'itil_RealIncome$realDate',
					align : 'left',
					sortable : true,
					hidden:false}],
		title :"<font color=red>�տ���Ŀ</font>",
		trackMouseOver : false,
		loadMask : true,
		clicksToEdit : 1,
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
	biddata = da.splitForReadOnly(data);
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
		title : "����",// "���������ƿ�������",
		items : biddata
	});
	return formpanel_SpecialRequire3_Input;
}

//����������
function getFormpanel_SRAnalyse() {
	var sra = new SRAction();
	var praId = sra.getProjectRequireAnalyseId(this.requireId);
	var da = new DataAction();
	var data = null;
	// �ж������������޸�
	var biddata = "";
	if (praId != '0') {
		data = da.getSingleFormPanelElementsForEdit("panel_SRAnalyse", praId);// ����Ҫ��ʱ���
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
		title : "��Ŀ�������",
		items : biddata
	});
	return formpanel_SRAnalyse;
}
//�����ͬ���
function getFormpanel_SRprojectContracts() {
	var sra = new SRAction();
	var rcId = sra.getRequirementContractId(this.requireId);
	var da = new DataAction();
	var data = null;// �ж������������޸�
	var biddata = "";
	if (rcId != '0') {
		data = da.getSingleFormPanelElementsForEdit("panel_SRprojectContracts",rcId);// ����Ҫ��ʱ���
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
		title : "���Ի������ͬ",
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
		title : "���������Ϣ",
		deferredRender : false,
		activeTab : 0,
		frame : true,
		plain : true,
		width : Ext.get('gridWidth').getWidth() - 30,
		defaults : {
			autoHeight : true
		},
		items : [{
			title : "���������Ϣ",
			width : "auto",
			items : [this.getFormpanel_SpecialRequire3_Input()]
		},{
			title : "���������Ϣ",
			width : "auto",
			items : [this.getFormpanel_SRAnalyse()]
		},{
			title : "�����ͬ��Ϣ",
			width : "auto",
			items : [this.getFormpanel_SRprojectContracts()]
		},{
			title : "<font color=red>�տ�����</font>",
			width : "auto",
			items : [this.getFormPanel(), this.getIncomeGridPanel()]
		},
		histroyForm
		]
	});
	return tab;
}