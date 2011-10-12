/*
 * �տ�panel
 */
//�ύ����
function submit() {
	Ext.getCmp('saveButton').disable();
	Ext.getCmp('submitButton').disable();
	Ext.getCmp('backButton').disable();
	var relationUserId = Ext.getCmp("relationUserCombo").getValue();
	if(relationUserId==""){
		Ext.Msg.alert("��ʾ��Ϣ", "��ѡ����ϵ�ˣ�");
		Ext.getCmp('saveButton').enable();
		Ext.getCmp('submitButton').enable();
		Ext.getCmp('backButton').enable();
	}
	var curbaId = Ext.getCmp('businessAccountId').getValue();
	var desc = Ext.getCmp("apply$desc").getValue();
	Ext.Ajax.request({
		url : webContext + '/businessAccountAction_saveIncomeBusinessAccount.action',
		params : {
			relationUserId : relationUserId,
			desc : unicode(desc),
			businessAccountId : curbaId,
			requireId : this.requireId
		},
		success : function(response) {
			var curRequireId = this.requireId;
			Ext.Ajax.request({
				url : webContext + '/configWorkflow_findProcessByPram.action',
				params : {
					modleType : 'Busi_InFeel',//
					processStatusType : '0'//
				},
				success : function(response, options) {
					var responseArray = Ext.util.JSON
							.decode(response.responseText);
					var vpid = responseArray.vpid;
					if(vpid!=""&&vpid!=undefined&&vpid.length>0){
						Ext.Ajax.request({
							url : webContext + '/businessAccountWorkflow_apply.action',
							params : {
								dataId : curbaId,
								bzparam : "{dataId :'" + curbaId + "',applyId : '"+ curbaId + "',requireId : '" + curRequireId 
										+ "',applyType: 'baproject',applyTypeName: '��������տ�����',workflowHistory:'com.zsgj.itil.require.entity.BusinessAccountApplyHis'}",
								defname : vpid
							},
							success : function(response, options) {
								var meg = Ext.decode(response.responseText);
								if (meg.id != undefined) {
									//Ext.Msg.alert("��ʾ", "�����������ɹ�",function(){//remove by lee for �û�Ҫ��ȥ����ʾ
										window.location = webContext+'/user/businessAccount/searchBusinessAccount.jsp';
									//});
									
								} else {
									Ext.Msg.alert("��ʾ", "����������ʧ��", function() {
										Ext.getCmp('saveButton').enable();
										Ext.getCmp('submitButton').enable();
										Ext.getCmp('backButton').enable();
										alert(meg.Exception);
									});
								}
							},
							failure : function(response, options) {
								Ext.getCmp('backButton').enable();
								Ext.getCmp('saveButton').enable();
								Ext.getCmp('submitButton').enable();
								Ext.MessageBox.alert("��ʾ", "����������ʧ��");
							}
						}, this);
					}else{
						Ext.MessageBox.alert("δ�ҵ���Ӧ�����̣���鿴�Ƿ�����!");
					}
				},
				failure : function(response, options) {
					Ext.MessageBox.alert("δ�ҵ���Ӧ�����̣���鿴�Ƿ�����!");
				}
			}, this);
		},
		failure : function(response, options) {
			Ext.getCmp('backButton').enable();
			Ext.getCmp('saveButton').enable();
			Ext.getCmp('submitButton').enable();
			Ext.MessageBox.alert("��ʾ", "����������ʧ��");
		}
	});
}
function save() {
	Ext.getCmp('saveButton').disable();
	Ext.getCmp('submitButton').disable();
	Ext.getCmp('backButton').disable();
	var relationUserId = Ext.getCmp("relationUserCombo").getValue();
	if(relationUserId==""){
		Ext.Msg.alert("��ʾ��Ϣ", "��ѡ����ϵ�ˣ�");
		Ext.getCmp('saveButton').enable();
		Ext.getCmp('submitButton').enable();
		Ext.getCmp('backButton').enable();
	}
	var curbaId = Ext.getCmp('businessAccountId').getValue();
	var desc = Ext.getCmp("apply$desc").getValue();
	Ext.Ajax.request({
		url : webContext
				+ '/businessAccountAction_saveIncomeBusinessAccount.action',
		params : {
			relationUserId : relationUserId,
			desc : unicode(desc),
			businessAccountId : curbaId,
			requireId : this.requireId
		},
		success : function(response) {
			var jsonObj = Ext.decode(response.responseText);
			Ext.Msg.alert("��ʾ��Ϣ", jsonObj.message);
			Ext.getCmp('saveButton').enable();
			Ext.getCmp('backButton').enable();
			Ext.getCmp('submitButton').enable();
		},
		failure : function(response, options) {
			Ext.getCmp('saveButton').enable();
			Ext.getCmp('backButton').enable();
			Ext.getCmp('submitButton').enable();
			Ext.Msg.alert("��ʾ��Ϣ", jsonObj.message);
		}
	});
}
function saveAndSubmit() {
	var relationUserId = Ext.getCmp("relationUserCombo").getValue();
	if(relationUserId==""){
		Ext.Msg.alert("��ʾ��Ϣ", "��ѡ����ϵ�ˣ�");
		return;
	}
	var curbaId = Ext.getCmp('businessAccountId').getValue();
	var desc = Ext.getCmp("apply$desc").getValue();
	Ext.Ajax.request({
		url : webContext
				+ '/businessAccountAction_saveIncomeBusinessAccount.action',
		params : {
			relationUserId : relationUserId,
			desc : unicode(desc),
			businessAccountId : curbaId,
			requireId : this.requireId
		},
		success : function(response) {
			var jsonObj = Ext.decode(response.responseText);
			Ext.Msg.alert("��ʾ��Ϣ", jsonObj.message);
			window.parent.auditContentWin.specialAudit();
		},
		failure : function(response, options) {
			Ext.Msg.alert("��ʾ��Ϣ", jsonObj.message);
		}
	});
}

function view(curId) {
	var curDataId = this.dataId;
	var _panel = new Ext.form.FormPanel({
		id : 'addWinPanel',
		layout : 'table',
		width : 300,
		height : 150,
		frame : true,
		layoutConfig : {
			columns : 2
		},
		defaults : {
			bodyStyle : 'padding:4px'
		},
		buttonAlign : 'center',
		buttons : [{
			text : 'ȷ��',
			handler : function() {
				if (Ext.getCmp('addWinPanel').form.isValid()) {
					var curCostCenter = Ext.getCmp('costCenter').getValue(); //�ɱ�����
					var money = Ext.getCmp('uMoney').getValue(); //ʵ�ʽ��
					Ext.Ajax.request({
						url : webContext+ '/businessAccountAction_initIncomeInfo.action',
						method : 'POST',
						params : {
							baId : curDataId,		//��ǰ����ID
							incomePlanId : curId,	//�տ�ƻ�ID
							costCenter : curCostCenter,
							money : money
						},
						success : function(response) {
							var res = response.responseText;
							nowRec = Ext.decode(res);
							if (nowRec.result) {
								Ext.Msg.alert('��ʾ', '����ɹ�');
								Ext.getCmp("incomeGrid").store.reload();
								Ext.getCmp("_window").close();
							} else {
								Ext.Msg.alert('��ʾ', '���ܳ����ƻ�ʣ����');
							}
						},
						failure : function() {
							Ext.Msg.alert('��ʾ', '����ʧ��');
						}
					});
				} else {
					Ext.Msg.alert('��ʾ', '�����ߵ�Ϊ������');
				}
			}
		}, {
			text : 'ȡ��',
			handler : function() {
				Ext.getCmp('_window').close();
			}
		}],
		items : [{
			html : '<font style="font-size:13px;text-align:right">�ɱ����ģ�</font>',
			border : false,
			style : 'width:100;text-align:right'
		}, {
			xtype : 'textfield',
			id : 'costCenter',
			allowBlank : false,
			style : 'width:160;text-align:left'
		}, {
			html : '<font style="font-size:13px;text-align:right">�տ��</font>',
			border : false,
			style : 'width:100;text-align:right'
		}, {
			xtype : 'numberfield',
			id : 'uMoney',
			allowBlank : false,
			style : 'width:160;text-align:left'
		}]
	});

	var _window = new Ext.Window({
		id : '_window',
		title : '�������տ�ƻ�',
		width : 300,
		height : 200,
		layout : 'table',
		layoutConfig : {
			cloumns : 1
		},
		items : [_panel]
	});
	_window.show();
}
function modifyView(record) {
	var _panel = new Ext.form.FormPanel({
		id : 'modifyWinPanel',
		layout : 'table',
		width : 300,
		height : 150,
		frame : true,
		layoutConfig : {
			columns : 2
		},
		defaults : {
			bodyStyle : 'padding:4px'
		},
		buttonAlign : 'center',
		buttons : [{
			text : 'ȷ��',
			handler : function() {
				if (Ext.getCmp('modifyWinPanel').form.isValid()) {
					var curId = record.get("itil_RealIncome$id");
					var curCostCenter = Ext.getCmp('costCenter').getValue(); //�ɱ�����
					var money = Ext.getCmp('uMoney').getValue(); //ʵ�ʽ��
					//alert(curId+"=="+curCostCenter+"=="+money);
					Ext.Ajax.request({
						url : webContext+ '/businessAccountAction_saveIncomeInfo.action',
						method : 'POST',
						params : {
							realIncomeId : curId,	//�տ�ƻ�ID
							costCenter : curCostCenter,
							money : money
						},
						success : function(response) {
							var res = response.responseText;
							nowRec = Ext.decode(res);
							if (nowRec.result) {
								Ext.Msg.alert('��ʾ', '����ɹ�');
								Ext.getCmp("incomeGrid").store.reload();
								Ext.getCmp("_window").close();
							} else {
								Ext.Msg.alert('��ʾ', '���ܳ����ƻ�ʣ����');
							}
						},
						failure : function() {
							Ext.Msg.alert('��ʾ', '����ʧ��');
						}
					});

				} else {
					Ext.Msg.alert('��ʾ', '�����ߵ�Ϊ������');
				}
			}
		}, {
			text : 'ȡ��',
			handler : function() {
				Ext.getCmp('_window').close();
			}
		}],
		items : [{
			html : '<font style="font-size:13px;text-align:right">�ɱ����ģ�</font>',
			border : false,
			style : 'width:100;text-align:right'
		}, {
			xtype : 'textfield',
			id : 'costCenter',
			allowBlank : false,
			style : 'width:160;text-align:left'
		}, {
			html : '<font style="font-size:13px;text-align:right">�տ��</font>',
			border : false,
			style : 'width:100;text-align:right'
		}, {
			xtype : 'numberfield',
			id : 'uMoney',
			allowBlank : false,
			style : 'width:160;text-align:left'
		}]
	});
	Ext.getCmp('costCenter').setValue(record.get("itil_RealIncome$costCenter")); //�ɱ�����
	Ext.getCmp('uMoney').setValue(record.get("itil_RealIncome$realMoney")); //ʵ�ʽ��
	var _window = new Ext.Window({
		id : '_window',
		title : '�������տ�ƻ�',
		width : 300,
		height : 200,
		layout : 'table',
		layoutConfig : {
			cloumns : 1
		},
		items : [_panel]
	});
	_window.show();

}

function getFormPanel() {
	var formPanel = new Ext.form.FormPanel({
		id : "formPanel",
		//title : "�տ�����",
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
						+ '/extjs/comboDataAction?clazz=com.zsgj.info.framework.security.entity.UserInfo',
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
		/*	{
			html : "��������:&nbsp;",
			cls : 'common-text',
			style : 'width:150;text-align:right'
		},*/new Ext.form.Hidden({
			xtype : "textarea",
			width : 546,
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
	Ext.getCmp('businessAccountId').setValue(this.dataId);
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
	
		var addButton = new Ext.Button({
		text : '����',
		pressed : true,
		iconCls : 'add',
		scope : this,
		handler : function() {
			var varTMPId = Ext.getCmp("planCombo").getValue();
			if (varTMPId == "") {
				Ext.getCmp("planCombo").setValue("");
				Ext.Msg.alert("��ʾ", "��ѡ�������");
				return;
			}
			view(varTMPId);
		}
	});
	var modifyButton = new Ext.Button({
		text : '�޸�',
		pressed : true,
		iconCls : 'edit',
		scope : this,
		handler : function() {
			var gridPanel = Ext.getCmp("incomeGrid");
			var record = gridPanel.getSelectionModel().getSelected();
			var records = gridPanel.getSelectionModel().getSelections();
			if (!record) {
				Ext.Msg.alert("��ʾ", "����ѡ��Ҫ�޸ĵ��տ���Ŀ!");
				return;
			}
			if (records.length > 1) {
				Ext.Msg.alert("��ʾ", "����ͬʱ�޸Ķ���տ���Ŀ");
			} else {
				modifyView(record);
			}
		}
	});
	var deleteButton = new Ext.Button({
		text : 'ɾ��',
		pressed : true,
		iconCls : 'delete',
		scope : this,
		handler : function() {
			var record = Ext.getCmp("incomeGrid").getSelectionModel()
					.getSelected();
			var records = Ext.getCmp("incomeGrid").getSelectionModel()
					.getSelections();
			if (!record) {
				Ext.Msg.alert("��ʾ", "��ѡ��Ҫɾ�����տ���Ŀ��Ϣ");
				return;
			}
			if (records.length == 0) {
				Ext.MessageBox.alert('��ʾ', '����ѡ��һ���տ���Ŀ��Ϣ����ɾ��!');
				return;
			}
			Ext.MessageBox.confirm("ȷ��ɾ��", "�Ƿ�Ҫɾ����ѡ����տ���Ŀ��Ϣ?",
					function(button) {
						if (button == "yes") {
							for (var i = 0; i < records.length; i++) {
								Ext.getCmp("incomeGrid").store
										.remove(records[i]);
							}
						}
					});
		}
	});
	var saveButton = new Ext.Button({
		text : '�����տ���Ŀ',
		pressed : true,
		iconCls : 'save',
		scope : this,
		handler : function() {
			save();
		}
	});
	var planCombo = new Ext.form.ComboBox({
		xtype : 'combo',
		id : 'planCombo',
		width : 200,
		style : '',
		fieldLabel : '�տ���Ŀ',
		displayField : 'name',
		valueField : 'id',
		emptyText : '��ѡ��...',
		allowBlank : true,
		typeAhead : true,
		name : 'planCombo',
		triggerAction : 'all',
		minChars : 50,
		queryDelay : 700,
		store : new Ext.data.JsonStore({
			url : webContext
					+ '/businessAccountAction_getIncomePlanCombo.action?dataId='
					+ this.requireId,
			fields : ['id', 'name'],
			totalProperty : 'rowCount',
			root : 'data',
			id : 'id'
		}),
		listeners : {
			'beforequery' : function(queryEvent) {
			}
		}
	});
	
	var incomeGrid= new Ext.grid.GridPanel({
		id : 'incomeGrid',
		store : this.store,
		sm : sm,
		columns:[sm,
			{header:'�Զ����',dataIndex:'SRIncomePlan$id',align : 'left',sortable : true,hidden:true},
			{header:'�տ�����',dataIndex:'SRIncomePlan$name',align : 'left',sortable : true,hidden:false},
			{header:'�տ�����',dataIndex:'SRIncomePlan$descn',align : 'left',sortable : true,hidden:false},
			{header:'�տ���',dataIndex:'plan$money',align : 'left',sortable : true,hidden:true},
			{header:'�ƻ���ʼʱ��',dataIndex:'plan$startDate',align : 'left',sortable : true,hidden:false},
			{header:'�ƻ�����ʱ��',dataIndex:'plan$endDate',align : 'left',sortable : true,hidden:false},
			{header:'ʣ����',dataIndex:'plan$balance',align : 'left',sortable : true,hidden:false},
			{header:'�Զ����',dataIndex:'itil_RealIncome$id',align : 'left',sortable : true,hidden:true},
			{header:'ʵ���տ�ʱ��',dataIndex:'itil_RealIncome$realDate',align : 'left',sortable : true,hidden:true},
			{header:'�տ�ɱ�����',dataIndex:'itil_RealIncome$costCenter',align : 'left',sortable : true,hidden:false},
			{header:'ʵ���տ���',dataIndex:'itil_RealIncome$realMoney',align : 'left',sortable : true,hidden:false}],
		title :"�տ���Ŀ",
		trackMouseOver : false,
		loadMask : true,
		clicksToEdit : 2,
		collapsible : true,
		autoScroll : true,
		loadMask : true,
		autoHeight : true,
		width : 800,
		frame : true,
		tbar : [planCombo, addButton, modifyButton, deleteButton],
		listeners : {
			"dblclick" : function() {
				var record = Ext.getCmp("incomeGrid").getSelectionModel().getSelected();
				modifyView(record);
			}
		}
		
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
			data[i].id = data[i].id + 8;// �ı�Combobox��id
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
			title : "�տ�����",
			width : "auto",
			items : [this.getFormPanel(), this.getIncomeGridPanel()]
		},
		histroyForm	
		]
	});
	return tab;
}