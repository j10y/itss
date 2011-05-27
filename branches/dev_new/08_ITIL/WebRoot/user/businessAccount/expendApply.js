/*
 * �տ�panel
 */

function save() {
	Ext.getCmp('saveButton').disable();
	Ext.getCmp('submitButton').disable();
	var curbaId = Ext.getCmp('businessAccountId').getValue();
	var desc = Ext.getCmp("apply$desc").getValue();
	Ext.Ajax.request({
		url : webContext + '/businessAccountAction_saveExpendBusinessAccount.action',
		params : {
			desc : unicode(desc),
			businessAccountId : curbaId,
			requireId : this.requireId
		},
		success : function(response) {
			var jsonObj = Ext.decode(response.responseText);
			Ext.Msg.alert("��ʾ��Ϣ", jsonObj.message);
			Ext.getCmp('saveButton').enable();
			Ext.getCmp('submitButton').enable();
		},
		failure : function(response, options) {
			Ext.getCmp('saveButton').enable();
			Ext.getCmp('submitButton').enable();
			Ext.MessageBox.alert("��ʾ", "����ʧ��");
		}
	});
}
function submit() {
	Ext.getCmp('saveButton').disable();
	Ext.getCmp('submitButton').disable();
	var curbaId = Ext.getCmp('businessAccountId').getValue();
	var desc = Ext.getCmp("apply$desc").getValue();
	Ext.Ajax.request({
		url : webContext
				+ '/businessAccountAction_saveExpendBusinessAccount.action',
		params : {
			desc : unicode(desc),
			businessAccountId : curbaId,
			requireId : this.requireId
		},
		success : function(response) {
			var jsonObj = Ext.decode(response.responseText);
			var curRequireId = this.requireId;
			Ext.Ajax.request({
				url : webContext + '/configWorkflow_findProcessByPram.action',
				params : {
					modleType : 'Busi_OutFeel',//
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
										+ "',applyType: 'baproject',applyTypeName: '������㸶������',workflowHistory:'com.zsgj.itil.require.entity.BusinessAccountApplyHis'}",
								defname : vpid
							},
							success : function(response, options) {
								var meg = Ext.decode(response.responseText);
								if (meg.id != undefined) {
									//Ext.Msg.alert("��ʾ", "�����������ɹ�");//remove by lee for �û�Ҫ��ȥ����ʾ
									window.location = webContext + '/user/businessAccount/requirePlanInfo.jsp?dataId='+curRequireId;
								} else {
									Ext.Msg.alert("��ʾ", "����������ʧ��", function() {
										Ext.getCmp('saveButton').enable();
										Ext.getCmp('submitButton').enable();
										alert(meg.Exception);
									});
								}
							},
							failure : function(response, options) {
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
			Ext.getCmp('saveButton').enable();
			Ext.getCmp('submitButton').enable();
			Ext.MessageBox.alert("��ʾ", "����������ʧ��");
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
						url : webContext+ '/businessAccountAction_initPaymentInfo.action',
						method : 'POST',
						params : {
							baId : curDataId,		//��ǰ����ID
							expendPlanId : curId,	//�տ�ƻ�ID
							costCenter : curCostCenter,
							money : money
						},
						success : function(response) {
							var res = response.responseText;
							nowRec = Ext.decode(res);
							if (nowRec.result) {
								Ext.Msg.alert('��ʾ', '����ɹ�');
								Ext.getCmp("paymengGrid").store.reload();
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
			html : '<font style="font-size:13px;text-align:right">�����</font>',
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
		title : '�����¸���ƻ�',
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
					var curId = record.get("itil_RealPayment$id");
					var curCostCenter = Ext.getCmp('costCenter').getValue(); //�ɱ�����
					var money = Ext.getCmp('uMoney').getValue(); //ʵ�ʽ��
					Ext.Ajax.request({
						url : webContext+ '/businessAccountAction_savePaymentInfo.action',
						method : 'POST',
						params : {
							realPaymentId : curId,	//�տ�ƻ�ID
							costCenter : curCostCenter,
							money : money
						},
						success : function(response) {
							var res = response.responseText;
							nowRec = Ext.decode(res);
							if (nowRec.result) {
								Ext.Msg.alert('��ʾ', '����ɹ�');
								Ext.getCmp("paymengGrid").store.reload();
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
			html : '<font style="font-size:13px;text-align:right">�����</font>',
			border : false,
			style : 'width:100;text-align:right'
		}, {
			xtype : 'numberfield',
			id : 'uMoney',
			allowBlank : false,
			style : 'width:160;text-align:left'
		}]
	});
	Ext.getCmp('costCenter').setValue(record.get("itil_RealPayment$costCenter")); //�ɱ�����
	Ext.getCmp('uMoney').setValue(record.get("itil_RealPayment$realMoney")); //ʵ�ʽ��
	var _window = new Ext.Window({
		id : '_window',
		title : '�����¸���ƻ�',
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

function getpaymentGridPanel() {
	var sm = new Ext.grid.CheckboxSelectionModel();
	 this.store = new Ext.data.JsonStore({ 
	            url : webContext+'/businessAccountAction_getAllPaymentInfo.action?baId='+this.dataId,
	            fields:[{name:'SRExpendPlan$id'},
	            {name:'SRExpendPlan$name'},
	        	{name:'SRExpendPlan$descn'},
	          	{name:'plan$money'},
	          	{name:'plan$startDate'},
	           	{name:'plan$endDate'},
	           	{name:'plan$balance'},
	           	{name:'itil_RealPayment$id'},
	           	{name:'itil_RealPayment$realDate'},
	           	{name:'itil_RealPayment$costCenter'},
	           	{name:'itil_RealPayment$realMoney'}],
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
				Ext.Msg.alert("��ʾ", "��ѡ������");
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
			var gridPanel = Ext.getCmp("paymengGrid");
			var record = gridPanel.getSelectionModel().getSelected();
			var records = gridPanel.getSelectionModel().getSelections();
			if (!record) {
				Ext.Msg.alert("��ʾ", "����ѡ��Ҫ�޸ĵĸ�����Ŀ!");
				return;
			}
			if (records.length > 1) {
				Ext.Msg.alert("��ʾ", "����ͬʱ�޸Ķ��������Ŀ");
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
			var record = Ext.getCmp("paymengGrid").getSelectionModel()
					.getSelected();
			var records = Ext.getCmp("paymengGrid").getSelectionModel()
					.getSelections();
			if (!record) {
				Ext.Msg.alert("��ʾ", "��ѡ��Ҫɾ���ĸ�����Ŀ��Ϣ");
				return;
			}
			if (records.length == 0) {
				Ext.MessageBox.alert('��ʾ', '����ѡ��һ��������Ŀ��Ϣ����ɾ��!');
				return;
			}
			Ext.MessageBox.confirm("ȷ��ɾ��", "�Ƿ�Ҫɾ����ѡ��ĸ�����Ŀ��Ϣ?",
					function(button) {
						if (button == "yes") {
							for (var i = 0; i < records.length; i++) {
								Ext.getCmp("paymengGrid").store
										.remove(records[i]);
							}
						}
					});
		}
	});
	var saveButton = new Ext.Button({
		text : '���渶����Ŀ',
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
		fieldLabel : '������Ŀ',
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
					+ '/businessAccountAction_getExpendPlanCombo.action?dataId='
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
	this.grid = new Ext.grid.GridPanel({
		id : 'paymengGrid',
		store : this.store,
		sm : sm,
		columns : [sm, 
			{header:'�Զ����',dataIndex:'SRExpendPlan$id',align : 'left',sortable : true,hidden:true},
			{header:'��������',dataIndex:'SRExpendPlan$name',align : 'left',sortable : true,hidden:false},
			{header:'��������',dataIndex:'SRExpendPlan$descn',align : 'left',sortable : true,hidden:false},
			{header:'������',dataIndex:'plan$money',align : 'left',sortable : true,hidden:true},
			{header:'�ƻ���ʼʱ��',dataIndex:'plan$startDate',align : 'left',sortable : true,hidden:false},
			{header:'�ƻ�����ʱ��',dataIndex:'plan$endDate',align : 'left',sortable : true,hidden:false},
			{header:'ʣ����',dataIndex:'plan$balance',align : 'left',sortable : true,hidden:false},
			{header:'�Զ����',dataIndex:'itil_RealPayment$id',align : 'left',sortable : true,hidden:true},
			{header:'ʵ�ʸ���ʱ��',dataIndex:'itil_RealPayment$realDate',align : 'left',sortable : true,hidden:true},
			{header:'����ɱ�����',dataIndex:'itil_RealPayment$costCenter',align : 'left',sortable : true,hidden:false},
			{header:'ʵ�ʸ�����',dataIndex:'itil_RealPayment$realMoney',align : 'left',sortable : true,hidden:false}],
		//		title : "������Ŀ",
		trackMouseOver : false,
		loadMask : true,
		clicksToEdit : 2,
		collapsible : true,
		autoScroll : true,
		loadMask : true,
		//autoHeight : true,
		height : 250,
		width : 800,// this.width - 15,
		frame : true,
		tbar : [planCombo, addButton, modifyButton, deleteButton],
		listeners : {
			"dblclick" : function() {
				var record = Ext.getCmp("paymengGrid").getSelectionModel().getSelected();
				modifyView(record);
			}
		}
	});
	this.store.load();
	return this.grid;
}

function getFormPanel() {
	var formPanel = new Ext.form.FormPanel({
		id : "formPanel",
		title : "��������",
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
			width : 200,
			colspan : 3
		}, 
			/*{
			html : "��������:&nbsp;",
			cls : 'common-text',
			style : 'width:150;text-align:right'
		},*/ new Ext.form.Hidden({
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
	});
	Ext.getCmp('businessAccountId').setValue(this.dataId);
	Ext.getCmp('applyNum').setValue(this.applyNum);
	Ext.getCmp('apply$desc').setValue(this.descn);
	return formPanel;
}
/*
 * tabPanel
 */
function getTabPanel() {
	var tab = new Ext.Panel({
		deferredRender : false,
		activeTab : 0,
		frame : true,
		plain : true,
		width : Ext.get('gridWidth').getWidth() - 30,
		defaults : {
			autoHeight : true
		},
		items : [this.getFormPanel(), this.getpaymentGridPanel()],
		buttonAlign : "center",
		buttons : [{
			text : '����',
			id : 'saveButton',
			pressed : true,
			iconCls : 'save',
			scope : this,
			handler : function() {
				save();
			}
		}, {
			text : '�ύ',
			id : 'submitButton',
			pressed : true,
			iconCls : 'submit',
			scope : this,
			handler : function() {
				submit();
			}
		}, {
			text : '����',
			pressed : true,
			iconCls : 'save',
			scope : this,
			handler : function() {
				history.go(-1);
			}
		}]
	});
	return tab;
}