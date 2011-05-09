/*
 * 收款panel
 */

//提交方法
function submit() {
	Ext.getCmp('saveButton').disable();
	Ext.getCmp('submitButton').disable();
	var relationUserId = Ext.getCmp("relationUserCombo").getValue();
	if(relationUserId==""){
		Ext.Msg.alert("提示信息", "请选择联系人！");
		Ext.getCmp('saveButton').enable();
		Ext.getCmp('submitButton').enable();
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
				url : webContext + '/businessAccountWorkflow_apply.action',
				params : {
					dataId : curbaId,
					bzparam : "{dataId :'" + curbaId + "',applyId : '"+ curbaId + "',requireId : '" + curRequireId 
							+ "',applyType: 'baproject',applyTypeName: '商务结算收款申请',workflowHistory:'com.zsgj.itil.require.entity.BusinessAccountApplyHis'}",
					defname : 'businessAccount11251188913109'
				},
				success : function(response, options) {
					var meg = Ext.decode(response.responseText);
					if (meg.id != undefined) {
						//Ext.Msg.alert("提示", "启动工作流成功");//remove by lee for 用户要求去掉提示
						window.location = webContext + '/user/businessAccount/requirePlanInfo.jsp?dataId='+curRequireId;
					} else {
						Ext.Msg.alert("提示", "启动工作流失败", function() {
							Ext.getCmp('saveButton').enable();
							Ext.getCmp('submitButton').enable();
							alert(meg.Exception);
						});
					}
				},
				failure : function(response, options) {
					Ext.getCmp('saveButton').enable();
					Ext.getCmp('submitButton').enable();
					Ext.MessageBox.alert("提示", "启动工作流失败");
				}
			}, this);
		},
		failure : function(response, options) {
			Ext.getCmp('saveButton').enable();
			Ext.getCmp('submitButton').enable();
			Ext.MessageBox.alert("提示", "启动工作流失败");
		}
	});
}
function save() {
	Ext.getCmp('saveButton').disable();
	Ext.getCmp('submitButton').disable();
	var relationUserId = Ext.getCmp("relationUserCombo").getValue();
	if(relationUserId==""){
		Ext.Msg.alert("提示信息", "请选择联系人！");
		Ext.getCmp('saveButton').enable();
		Ext.getCmp('submitButton').enable();
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
			Ext.Msg.alert("提示信息", jsonObj.message);
			Ext.getCmp('saveButton').enable();
			Ext.getCmp('submitButton').enable();
		},
		failure : function(response, options) {
			Ext.getCmp('saveButton').enable();
			Ext.getCmp('submitButton').enable();
			Ext.Msg.alert("提示信息", jsonObj.message);
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
			text : '确认',
			handler : function() {
				if (Ext.getCmp('addWinPanel').form.isValid()) {
					var curCostCenter = Ext.getCmp('costCenter').getValue(); //成本中心
					var money = Ext.getCmp('uMoney').getValue(); //实际金额
					Ext.Ajax.request({
						url : webContext+ '/businessAccountAction_initIncomeInfo.action',
						method : 'POST',
						params : {
							baId : curDataId,		//当前申请ID
							incomePlanId : curId,	//收款计划ID
							costCenter : curCostCenter,
							money : money
						},
						success : function(response) {
							var res = response.responseText;
							nowRec = Ext.decode(res);
							if (nowRec.result) {
								Ext.Msg.alert('提示', '保存成功');
								Ext.getCmp("incomeGrid").store.reload();
								Ext.getCmp("_window").close();
							} else {
								Ext.Msg.alert('提示', '不能超过计划剩余金额');
							}
						},
						failure : function() {
							Ext.Msg.alert('提示', '保存失败');
						}
					});
				} else {
					Ext.Msg.alert('提示', '带红线的为必填项');
				}
			}
		}, {
			text : '取消',
			handler : function() {
				Ext.getCmp('_window').close();
			}
		}],
		items : [{
			html : '<font style="font-size:13px;text-align:right">成本中心：</font>',
			border : false,
			style : 'width:100;text-align:right'
		}, {
			xtype : 'textfield',
			id : 'costCenter',
			allowBlank : false,
			style : 'width:160;text-align:left'
		}, {
			html : '<font style="font-size:13px;text-align:right">收款金额：</font>',
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
		title : '更新新收款计划',
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
			text : '确认',
			handler : function() {
				if (Ext.getCmp('modifyWinPanel').form.isValid()) {
					var curId = record.get("itil_RealIncome$id");
					var curCostCenter = Ext.getCmp('costCenter').getValue(); //成本中心
					var money = Ext.getCmp('uMoney').getValue(); //实际金额
					//alert(curId+"=="+curCostCenter+"=="+money);
					Ext.Ajax.request({
						url : webContext+ '/businessAccountAction_saveIncomeInfo.action',
						method : 'POST',
						params : {
							realIncomeId : curId,	//收款计划ID
							costCenter : curCostCenter,
							money : money
						},
						success : function(response) {
							var res = response.responseText;
							nowRec = Ext.decode(res);
							if (nowRec.result) {
								Ext.Msg.alert('提示', '保存成功');
								Ext.getCmp("incomeGrid").store.reload();
								Ext.getCmp("_window").close();
							} else {
								Ext.Msg.alert('提示', '不能超过计划剩余金额');
							}
						},
						failure : function() {
							Ext.Msg.alert('提示', '保存失败');
						}
					});

				} else {
					Ext.Msg.alert('提示', '带红线的为必填项');
				}
			}
		}, {
			text : '取消',
			handler : function() {
				Ext.getCmp('_window').close();
			}
		}],
		items : [{
			html : '<font style="font-size:13px;text-align:right">成本中心：</font>',
			border : false,
			style : 'width:100;text-align:right'
		}, {
			xtype : 'textfield',
			id : 'costCenter',
			allowBlank : false,
			style : 'width:160;text-align:left'
		}, {
			html : '<font style="font-size:13px;text-align:right">收款金额：</font>',
			border : false,
			style : 'width:100;text-align:right'
		}, {
			xtype : 'numberfield',
			id : 'uMoney',
			allowBlank : false,
			style : 'width:160;text-align:left'
		}]
	});
	Ext.getCmp('costCenter').setValue(record.get("itil_RealIncome$costCenter")); //成本中心
	Ext.getCmp('uMoney').setValue(record.get("itil_RealIncome$realMoney")); //实际金额
	var _window = new Ext.Window({
		id : '_window',
		title : '更新新收款计划',
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
		height : 80,
		frame : true,
		width : 800,// this.width - 15,
//		autoScroll : true,
		layout : 'table',
		layoutConfig : {
			columns : 4
		},
		items : [{html : "申请编号:&nbsp;",cls : 'common-text',style : 'width:150;text-align:right'}, 
			{xtype : 'textfield',id : "applyNum",readOnly : true,emptyText : "系统自动生成",width : 200},
			{html : "联系人:&nbsp;",cls : 'common-text',style : 'width:150;text-align:right'}, 
			new Ext.form.ComboBox({
				xtype : 'combo',
				hiddenName : 'relationUser',
				id : 'relationUserCombo',
				width : 200,
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
			html : "申请理由:&nbsp;",
			cls : 'common-text',
			style : 'width:150;text-align:right'
		},*/new Ext.form.Hidden( {
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
				fieldLabel : '自动编号'
			})]
	})
//	Ext.getCmp('businessAccountId').setValue(this.dataId);
//	Ext.getCmp('applyNum').setValue(this.applyNum);
//	Ext.getCmp('relationUserCombo').setValue(this.userId);
//	Ext.getCmp('relationUserCombo').initComponent();
//	Ext.getCmp('apply$desc').setValue(this.descn);
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
	var addButton = new Ext.Button({
		text : '增加',
		pressed : true,
		iconCls : 'add',
		scope : this,
		handler : function() {
			var varTMPId = Ext.getCmp("planCombo").getValue();
			if (varTMPId == "") {
				Ext.getCmp("planCombo").setValue("");
				Ext.Msg.alert("提示", "请选择后添加");
				return;
			}
			view(varTMPId);
		}
	});
	var modifyButton = new Ext.Button({
		text : '修改',
		pressed : true,
		iconCls : 'edit',
		scope : this,
		handler : function() {
			var gridPanel = Ext.getCmp("incomeGrid");
			var record = gridPanel.getSelectionModel().getSelected();
			var records = gridPanel.getSelectionModel().getSelections();
			if (!record) {
				Ext.Msg.alert("提示", "请先选择要修改的收款条目!");
				return;
			}
			if (records.length > 1) {
				Ext.Msg.alert("提示", "不能同时修改多个收款条目");
			} else {
				modifyView(record);
			}

		}
	});
	var deleteButton = new Ext.Button({
		text : '删除',
		pressed : true,
		iconCls : 'delete',
		scope : this,
		handler : function() {
			var record = Ext.getCmp("incomeGrid").getSelectionModel()
					.getSelected();
			var records = Ext.getCmp("incomeGrid").getSelectionModel()
					.getSelections();
			if (!record) {
				Ext.Msg.alert("提示", "请选择要删除的收款条目信息");
				return;
			}
			if (records.length == 0) {
				Ext.MessageBox.alert('提示', '最少选择一条收款条目信息进行删除!');
				return;
			}
			Ext.MessageBox.confirm("确认删除", "是否要删除所选择的收款条目信息?",
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
		text : '保存收款条目',
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
		fieldLabel : '收款条目',
		displayField : 'name',
		valueField : 'id',
		emptyText : '请选择...',
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
		})
	});
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
		height:'auto',
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

/*
 * tabPanel
 */
function getTabPanel() {
	var tab = new Ext.Panel({
		deferredRender : false,
		frame : true,
		plain : true,
		//width : Ext.get('gridWidth').getWidth() - 15,
		height : 'auto',
		layout:'table',
		layoutConfig:{
			columns:1
		},
		items : [this.getFormPanel(),this.getIncomeGridPanel()],
		buttonAlign : "center",
		buttons : [{
			text : '保存',
			id : 'saveButton',
			pressed : true,
			iconCls : 'save',
			scope : this,
			handler : function() {
				save();
			}
		}, {
			text : '提交',
			id : 'submitButton',
			pressed : true,
			iconCls : 'submit',
			scope : this,
			handler : function() {
				submit();
			}
		}, {
			text : '返回',
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