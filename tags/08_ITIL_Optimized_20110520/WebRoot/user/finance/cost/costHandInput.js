ProjectCreatePanel = Ext.extend(Ext.Panel, {
	id : "costHandInputCreatePanel",
	height : 'auto',
	width : 'auto',
	layout : 'fit',
	frame : true,
	items : this.items,

	/*
	 * �����formpanel
	 */
	getMainForm : function() {
			auditPersonIitems = [{
				html : '������:<font color=\"red\">*</font>:&nbsp;',
				cls : 'common-text',
				style : 'width:100;text-align:right',
				baseCls: "x-plain"//�����ͱ���ɫ�ͱ������ɫ�滻�˰�ɫ
			}, {
				id : 'auditPersonItcodeId',
				xtype : 'textfield',
				width : 100,
				allowBlank : false,
				listeners : {
					blur : function(field) {
					if(Ext.getCmp("auditPersonItcodeId").getValue().trim()=="") {
						Ext.getCmp("auditPersonId").setValue("");
						document.getElementById("auditPersonRealName").innerHTML = "<font color=red>������δ��д������itcode��</font>";
						return true;
					}
					Ext.Ajax.request({
						url : webContext
								+ '/CostHandInputAction_getUserInfoByItcode.action',
						params : {
							itcode : Ext.getCmp("auditPersonItcodeId").getValue()
						},
						method : 'post',
						success : function(response, option) {
							var res = Ext.decode(response.responseText);
							if(res.success) {
								Ext.getCmp("auditPersonId").setValue(res.auditPersonId);
								document.getElementById("auditPersonRealName").innerHTML = "<font color=green>��"+res.auditPersonRealName+"��</font>";
							} else {
								Ext.getCmp("auditPersonId").setValue("");
								document.getElementById("auditPersonRealName").innerHTML = "<font color=red>��"+"���޴��ˣ�����ϵ����Ա���"+"��</font>";
							}
						},
						failure : function() {
							Ext.Msg.alert('��ʾ','��ǰ�û���Ϣ��ȡʧ��');
						}
					}); 
					return true;
				}
				},
				enableKeyEvents : true,
				validationEvent : 'keypress',
				validator : function() {
					if(Ext.getCmp("auditPersonItcodeId").getValue().trim()=="") {
						Ext.getCmp("auditPersonId").setValue("");
						document.getElementById("auditPersonRealName").innerHTML = "<font color=red>������δ��д������itcode��</font>";
						return true;
					}
					Ext.Ajax.request({
						url : webContext
								+ '/CostHandInputAction_getUserInfoByItcode.action',
						params : {
							itcode : Ext.getCmp("auditPersonItcodeId").getValue()
						},
						method : 'post',
						success : function(response, option) {
							var res = Ext.decode(response.responseText);
							if(res.success) {
								Ext.getCmp("auditPersonId").setValue(res.auditPersonId);
								document.getElementById("auditPersonRealName").innerHTML = "<font color=green>��"+res.auditPersonRealName+"��</font>";
							} else {
								Ext.getCmp("auditPersonId").setValue("");
								document.getElementById("auditPersonRealName").innerHTML = "<font color=red>��"+"���޴��ˣ�����ϵ����Ա���"+"��</font>";
							}
						},
						failure : function() {
							Ext.Msg.alert('��ʾ','��ǰ�û���Ϣ��ȡʧ��');
						}
					}); 
					return true;
				}
			}, {
				id : 'auditPersonRealName',
				html : '<font color=red>������δ��д������itcode��</font>',
				cls : 'common-text',
				style : 'width:180;text-align:left',
				baseCls: "x-plain"//�����ͱ���ɫ�ͱ������ɫ�滻�˰�ɫ
			}, {
				id : 'auditPersonId',
				xtype : 'hidden'
			}];
		var costReduceTypeStore = new Ext.data.SimpleStore({
				fields : ['value', 'id'],
				data : [['������', "1"], ['������', "2"]]
		});
	
		var borrowTypesStore = new Ext.data.SimpleStore({
				fields : ['value', 'id'],
				data : [['ֱ�ӱ���', "1"], ['���', "2"], ['��������', "3"]]
		});

		var configItemStore = new Ext.data.JsonStore({
			url : webContext
					+ '/CostHandInputAction_findItem.action',
			fields : ['itemCode', 'name'],
			totalProperty : 'rowCount',
			root : 'data',
			id : 'name'
		});
		
		var financeCostTypeStore = new Ext.data.JsonStore({
			url : webContext
					+ '/CostHandInputAction_findCostType.action',
			fields : ['id', 'value'],
			totalProperty : 'rowCount',
			root : 'data',
			listeners : {
					beforeload : function(store, opt) {
						var param = Ext
								.getCmp('financeCostTypeId').defaultParam;
						if (opt.params['propertyValue'] == undefined) {
							opt.params['propertyValue'] = unicode(param);
						}
					}
			},
			id : 'id'
		});
		
		var reimbursementStore = new Ext.data.JsonStore({
			url : webContext
					+ '/CostHandInputAction_findReimbursement.action',
			fields : ['id', 'realName'],
			totalProperty : 'rowCount',
			root : 'data',
			listeners : {
					beforeload : function(store, opt) {
						var param = Ext
								.getCmp('reimbursementId').defaultParam;
						if (opt.params['propertyValue'] == undefined) {
							opt.params['propertyValue'] = unicode(param);
						}
					}
			},
			id : 'id'
		});
		
		var costCenterStore = new Ext.data.JsonStore({
			url : webContext
					+ '/CostHandInputAction_findFinanceCostCenter.action',
			fields : ['id', 'value'],
			totalProperty : 'rowCount',
			root : 'data',
			listeners : {
					beforeload : function(store, opt) {
						var param = Ext
								.getCmp('costCenterId').defaultParam;
						if (opt.params['propertyValue'] == undefined) {
							opt.params['propertyValue'] = unicode(param);
						}
					}
			},
			id : 'id'
		});
		
		
		
		var searchForm = new Ext.form.FormPanel({
			title : '�ɱ�¼��',
			id : "formPanel",
			collapsible : true,
			frame : true,
			autoScroll : true,
			layout : "table",
			defaults : {
				height : 30
			},
			layoutConfig : {
				columns : 1
			},
			items : [{
				xtype : 'fieldset',
				title : '�ɱ�������Ϣ¼��',
				layout : 'table',
				layoutConfig : {
					columns : 4
				},
				defaults : {
					bodyStyle : 'padding:8px 0px 8px 0px'
				},
				items : [
						{
							html : "�ɱ��������<font color=\"red\">*</font>:&nbsp;",
							cls : 'common-text',
							style : 'width:150;text-align:right'
						}, {
							xtype : 'combo',
							readOnly : false,
							id : "costReduceTypeId",
							hiddenName : "costReduceType",
							fieldLabel : '�ɱ��������',
							store : costReduceTypeStore,
							displayField : 'value',
							valueField : "id",
							allowBlank : false,
							triggerAction : "all",
							emptyText : "��ѡ��",
							mode : 'local',
							value : 1,
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
						}, {
							html : "������<font color=\"red\">*</font>:&nbsp;",
							cls : 'common-text',
							id:'configItem',
							style : 'width:150;text-align:right'
						}, {
							xtype : 'combo',
							id : "configItemId",
							hiddenName : "configItem",
							fieldLabel : '������',
							displayField : 'name',
							valueField : "itemCode",
							pageSize : 10,
							allowBlank : false,
							defaultParam : "",
							triggerAction : "all",
							store : configItemStore,
							emptyText : "��ѡ��",
							listeners : {
								beforequery  :function(queryEvent){
									var item=Ext.getCmp('costReduceTypeId').getValue();
									if(item==""){
									   Ext.Msg.alert("��ʾ",'��ѡ��ɱ��������!');
									}else{
										queryEvent.combo.store.baseParams={
											item:item,
											propertyValue : queryEvent.query
										};
										queryEvent.combo.store.reload();
									}
									return false;
								},
								'blur' :  function(combo){
									var nowVal = combo.getRawValue();
									var nowId = combo.getValue();
									if(nowVal==""){
										combo.setValue("")
									}else if(nowId==""){
										combo.setValue("");
										Ext.Msg.alert("��ʾ��Ϣ","����ֱ�����룬���ѯ��ѡ��");
									}
								}
							},
							width : 200
						},{
							html : "��������<font color=\"red\">*</font>:&nbsp;",
							cls : 'common-text',
							style : 'width:150;text-align:right'
						}, {
							xtype : 'combo',
							readOnly : false,
							id : "financeCostTypeId",
							hiddenName : "financeCostType",
							fieldLabel : '��������',
							store : financeCostTypeStore,
							displayField : 'value',
							valueField : "id",
							defaultParam : "",
							pageSize : 10,
							allowBlank : false,
							triggerAction : "all",
							emptyText : "��ѡ��",
							listeners : {
								'beforequery' : function(queryEvent) {
									var query = queryEvent.query;
									this.defaultParam = query;
									this.store.load({
												params : {
													propertyValue : query,
													start : 0
												}
											});
								},
								'blur' :  function(combo){
									var nowVal = combo.getRawValue();
									var nowId = combo.getValue();
									if(nowVal==""){
										combo.setValue("")
									}else if(nowId==""){
										combo.setValue("");
										Ext.Msg.alert("��ʾ��Ϣ","����ֱ�����룬���ѯ��ѡ��");
									}
								}
							},
							width : 200
						},{
							html : "�ɱ��������<font color=\"red\">*</font>:&nbsp;",
							cls : 'common-text',
							style : 'width:150;text-align:right'
						},{
							id : "costAmount",
							xtype : 'textfield',
							fieldLabel : '�ɱ��������',
							allowBlank : false,
							listeners : {
								'blur' : function(obj, e) {
									var money = Ext.getCmp("costAmount")
											.getValue();
									if (!/^(\d*)(\,|\d|\.)*$/.test(money)
											|| money == "") {
										return;
									}
									var arr = money.split(",");
									money = "";
									for (var i = 0; i < arr.length; i++) {
										money = money + arr[i];
									}
									Ext.getCmp('costAmount').setValue(Ext.util.Format.usMoney(money).substring(1,Ext.util.Format.usMoney(money).length));
								}
							},
							height : 0,
							width : 200
						},{
							html : "������:&nbsp;",
							cls : 'common-text',
							style : 'width:150;text-align:right'
						},{
							xtype : 'combo',
							id : 'reimbursementId',
							hiddenName : 'reimbursement',
							fieldLabel : '������',
							store : reimbursementStore,
							pageSize : 10,
							displayField : 'realName',
							valueField : 'id',
							defaultParam : "",
							triggerAction : 'all',
							width : 200,
							emptyText : '��ѡ��',
							listeners : {
								'beforequery' : function(queryEvent) {
									var query = queryEvent.query;
									this.defaultParam = query;
									this.store.load({
												params : {
													propertyValue : query,
													start : 0
												}
											});
								},
								"blur" : function(combo){
									var nowVal = combo.getRawValue();
									var nowId = combo.getValue();
									if(nowVal==""){
										combo.setValue("")
									}else if(nowId==""){
										combo.setValue("");
										Ext.Msg.alert("��ʾ��Ϣ","����ֱ�����룬���ѯ��ѡ��");
									}
								}
							}
						},{
							html : "�����ṩ��:&nbsp;",
							cls : 'common-text',
							style : 'width:150;text-align:right'
						},{
							id : "serviceProvider",
							xtype : 'textfield',
							fieldLabel : '�����ṩ��',
							allowBlank : true,
							height : 0,
							width : 200
						},{
							html : "�ɱ�����<font color=\"red\">*</font>:&nbsp;",
							cls : 'common-text',
							style : 'width:150;text-align:right'
						}, {
							xtype : 'combo',
							id : "costCenterId",
							hiddenName : "costCenter",
							fieldLabel : '�ɱ�����',
							defaultParam : "",
							pageSize : 10,
							allowBlank : false,
							displayField : 'value',
							valueField : "id",
							triggerAction : "all",
							emptyText : "��ѡ��",
							blankText : '��ѡ��Ϊ������',
							width : 200,
							store : costCenterStore,
							listeners : {
								'beforequery' : function(queryEvent) {
									var param = queryEvent.query;
									this.defaultParam = param;
									this.store.load({
												params : {
													propertyValue : param,
													start : 0
												}
											});
								},
								"blur" : function(combo){
									var nowVal = combo.getRawValue();
									var nowId = combo.getValue();
									if(nowVal==""){
										combo.setValue("")
									}else if(nowId==""){
										combo.setValue("");
										Ext.Msg.alert("��ʾ��Ϣ","����ֱ�����룬���ѯ��ѡ��");
									}
								}
							}	
						},{
							html : "���÷�������<font color=\"red\">*</font>:&nbsp;",
							cls : 'common-text',
							style : 'width:150;text-align:right'
						}, {
							xtype : 'datefield',
							fieldLabel : '���÷�������',
							id : "costDate",
							format : "Y-m-d",
							allowBlank : false,
							disabled : false,
							width : 200
						},{
							html : "�����:&nbsp;",
							cls : 'common-text',
							style : 'width:150;text-align:right'
						}, {
							xtype : 'combo',
							readOnly : false,
							id : "borrowTypeId",
							hiddenName : "borrowType",
							fieldLabel : '�����',
							store : borrowTypesStore,
							displayField : 'value',
							valueField : "id",
							allowBlank : true,
							triggerAction : "all",
							emptyText : "��ѡ��",
							mode : 'local',
							listeners : {
								"blur" : function(combo){
									var nowVal = combo.getRawValue();
									if(nowVal!=""&&nowVal!="ֱ�ӱ���"&&nowVal!="���"&&nowVal!="��������"){
										combo.setValue("");
										Ext.Msg.alert("��ʾ��Ϣ","����ֱ�����룬���ѯ��ѡ��");
									}
								}
							},
							width : 200
						},{
							html : "�������:&nbsp;",
							cls : 'common-text',
							style : 'width:150;text-align:right'
						}, {
							xtype : 'datefield',
							fieldLabel : '�������',
							id : "borrowDate",
							format : "Y-m-d",
							disabled : false,
							width : 200
						},{
							html : '��ϸ˵��:&nbsp',
							cls : 'common-text',
							style : 'width:150;text-align:right'
						}, {
							xtype : 'textarea',
							id : 'CostDetailExplanation',
							width : 550,
							allowBlank : true,
							colspan : 3
						}
				]
			}, {
				xtype : 'fieldset',
				title : '������',
				layout : 'table',
				layoutConfig : {
					columns : 4
				},
				defaults : {
					bodyStyle : 'padding:8px 0px 8px 0px',
					overflow : 'auto'
				},
				items : auditPersonIitems,
				scope : this
			}],
			buttons : [
					{
						text : "����",
						pressed : true,
						iconCls : 'save',
						handler : this.formSave
					}, {
						text : "�� ��",
						pressed : true,
						iconCls : 'back',
						handler : function() {
								Ext.MessageBox.confirm("ȷ����Ϣ", "ȷ��Ҫ���أ�", function(tes) {
									if (tes == "yes") {
										history.go(-1);
									}
								});
						}
					}],
			buttonAlign : 'center'
		});
		return searchForm;
	},
	
	formSave : function() {
		if (Ext.getCmp("formPanel").form.isValid()) {
			var formParam = Ext.encode(getFormParam("formPanel"));
			Ext.Ajax.request({
					url : webContext + '/CostHandInputAction_save.action',
					params : {
						formParam : formParam
					},
					success : function(response, options) {
						var resultJson = Ext.util.JSON
								.decode(response.responseText);
						if(resultJson.success){
							Ext.Msg.alert('��ʾ��Ϣ', '����ɹ�!');
//							window.location = "../search/financeCostSchedules.jsp";
						}
					},
					failure : function() {
						Ext.Msg.alert('��ʾ��Ϣ', '����ʧ��');
					}
			});
		} else {
			Ext.Msg.alert('��ʾ��Ϣ', '�����ߴ�Ϊ������');
		}

	},
	
	initComponent : function() {
		this.searchForm = this.getMainForm();
		this.items = [this.searchForm];
		ProjectCreatePanel.superclass.initComponent.call(this);
	}
});

function unicode(s) {
	var len = s.length;
	var rs = "";
	for (var i = 0; i < len; i++) {
		var k = s.substring(i, i + 1);
		rs += "&#" + s.charCodeAt(i) + ";";
	}
	return rs;
};
function runicode(s) {
          var k = s.split(";");
          var r = "";
          for (var x = 0; x < k.length; x++) {
                    var m = k[x].replace(/&#/, "");
                    r += String.fromCharCode(m);
          }
          return r;
};

Ext.onReady(function() {
			Ext.QuickTips.init();
			var projectCreatePanel = new ProjectCreatePanel();
			projectCreatePanel.render("costHandInputDiv");
			new Ext.Viewport({
						layout : 'fit',
						items : [projectCreatePanel]
					});
});
