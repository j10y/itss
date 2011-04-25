PagePanel = Ext.extend(Ext.Panel, {
	id : "PageTemplates",
	frame : true,
	title : "�¼�������Ϣ��<font style='font-weight:lighter' color=red >~~~Ϊ�����Ϊ������׼ȷ��λ��ʱ������⣬������д��</font>��",
	autoScroll : true,
	defaults : {
		bodyStyle : 'padding:4px'
	},
	layout : 'border',

	getPanel : function() {
		this.eventType = new Ext.form.ComboBox({
			name : "eventtype",
			id : 'eventtype',
			fieldLabel : "�¼�����",
			width : 200,
			displayField : 'name',
			allowBlank : false,
			valueField : 'id',
			resizable : true,
			emptyText : '��������б���ѡ��...',
			triggerAction : 'all',
			store : new Ext.data.JsonStore({
				url : webContext + '/eventAction_findAllEventTypeByName.action',
				fields : ['id', 'name'],
				totalProperty : 'rowCount',
				root : 'data',
				id : 'id'
			}),
			pageSize : 10,
			listeners : {
				'beforequery' : function(queryEvent) {
					// Ext.getCmp('eventtype').clearValue();
					var param = queryEvent.combo.getRawValue();
					if (queryEvent.query == '') {
						param = '';
					}
					this.store.baseParams = {
						"typeName" : param,
						type : "combox"
					};// 2010-05-31 modified by huzh for bug
					this.store.load();
					return false;
				},
				'select' : function() {
					var sstore = Ext.getCmp("solutionGrid").getStore();
					var eventtypeId = Ext.getCmp("eventtype").getValue();
					var params = {
						eventtypeId : eventtypeId,
						start : 0
					};
					// Ext.getCmp("knowPageBar").formValue=params;
					sstore.on('beforeload', function(a) {
								Ext.apply(a.baseParams, params);
							});

					sstore.load({
								params : params
							});
				}
			}
		});

		var panel = new Ext.form.FormPanel({
					height : 30,
					region : 'north',
					id : 'servicePanel',
					// border:false,
					layout : 'table',
					layoutConfig : {
						columns : 2
					},
					defaults : {
						bodyStyle : 'padding:5px'
					},
					items : [{
								html : "�¼�����:",
								cls : 'common-text',
								width : 90,
								style : 'width:100;text-align:right'
							}, this.eventType]
				});
		return panel;
	},
	lookInfo : function() {
		var record = this.grid.getSelectionModel().getSelected();
		var dataId = record.get("id");
		var da = new DataAction();
		var data = da.getKnowledgeFormPanelElementsForEdit(
				"CCCallInfoSolutionForm_pagepanel", dataId);
		var konwledgecontext = "";
		for (i = 0; i < data.length; i++) {
			if (data[i].id == "Knowledge$resolvent") {
				konwledgecontext = data[i].value;
			}
		}
		var windowSkip = new Ext.Window({
			title : '�鿴��ϸ��Ϣ',
			maximizable : true,
			autoScroll : true,
			width : 600,
			height : 400,
			modal : true,
			items : [{
						html : konwledgecontext
					}],
			bodyStyle : 'padding:4px',
			buttons : [{
						text : '�ر�',
						handler : function() {
							windowSkip.close();
						},
						scope : this
					}, {
						handler : function() {
							Ext.getCmp("useKnowButton").disable();
							Ext.Ajax.request({
								url : webContext
										+ '/knowledgeAction_useSolution.action',
								params : {
									SolutionID : dataId
								},
								success : function(response, options) {
									Ext.MessageBox.alert("��ʾ",
											"�ɹ�ʹ�øý���������һ���¼���", function() {
												windowSkip.close();
											});
								},
								scope : this,
								failure : function(response, options) {
									Ext.MessageBox.alert("��ʾ", "����ʧ�ܣ�");
									Ext.getCmp("useKnowButton").enable();
								}
							}, this);
						},
						text : 'ʹ��',
						id : 'useKnowButton',
						style : 'width:80;text-align:right',
						scope : this
					}]
		});
		windowSkip.show();
	},

	getEventPanel : function() {
		var da = new DataAction();
		var data = da.getPanelElementsForAdd("eventpage");
		for (var i = 0; i < data.length; i++) {
			if (data[i].name == "Event$summary") {
				data[i].fieldLabel = "�¼�����";
			}
			if (data[i].name == "Event$ponderance") {
				data[i].emptyText = '��������б���ѡ��...';
			}
			if (data[i].name == "Event$description") {
				data[i].fieldLabel = "�¼�����";
				data[i].width = 9999;
				data[i].allowBlank = false;
				data[i].emptyText = '������������������4000������';
			}
		}
		Ext.getCmp('Event$summary').allowBlank = false;
		Ext.getCmp('Event$summary').setValue("");
		if (this.isSupportEngineer == "no") {// ����֧���鹤��ʦ
			for (var k = 0; k < data.length; k++) {
				if (data[k].name == "Event$ponderance") {
					data.remove(data[k]);
				}
			}
		}

		var biddata = this.split(data);
		this.mybuttons = this.getButtons();
		var cpanel = new Ext.form.FormPanel({
					id : 'cataPanel',
					layout : 'table',
					height : 307,
					width : 570,
					buttonAlign : 'center',
					buttons : this.mybuttons,
					bodyStyle : 'padding:2px,2px,0px,2px',
					autoScroll : true,
					frame : true,
					layoutConfig : {
						columns : 4
					},
					items : biddata
				});
		var xpanel = new Ext.Panel({
					layout : 'table',
					frame : true,
					autoScroll : true,
					layoutConfig : {
						columns : 2
					},
					region : 'center',
					items : [this.getGrid(), cpanel]
				})
		return xpanel;
	},

	getGrid : function() {
		this.storeList = new Ext.data.JsonStore({
					url : webContext
							+ '/knowledgeAction_findKnowledgeByEventType.action',
					fields : ['id', 'summary'],
					totalProperty : 'rowCount',
					root : 'data',
					id : 'id'
				});
		this.cm = new Ext.grid.ColumnModel([{
					header : "��������",
					dataIndex : "summary",
					width : 240
				}]);
		var viewConfig = Ext.apply({
					forceFit : true
				}, this.gridViewConfig);
		this.pageBar = new Ext.PagingToolbar({
					id : "knowPageBar",
					pageSize : 10,
					store : this.storeList,
					displayInfo : false
				});
		this.grid = new Ext.grid.GridPanel({
			store : this.storeList,
			id : "solutionGrid",
			title : "��Ӧ���������<font style='font-weight:lighter' color=red >˫����鿴��ϸ��Ϣ</font>��",
			cm : this.cm,
			autoScroll : true,
			loadMask : true,
			frame : true,
			height : 307,
			width : 280, // 2010-04-22 modified by huzh for ȥ�����������
			viewConfig : {
				forceFit : true
			},
			bbar : this.pageBar
		});

		var eventtypeId = null;
		if (Ext.getCmp("eventtype").getValue() != '') {
			eventtypeId = Ext.getCmp("eventtype").getValue();
		}
		var params = {
			eventtypeId : eventtypeId,
			start : 0
		};
		// this.pageBar.formValue = param;
		this.storeList.on('beforeload', function(a) {
					Ext.apply(a.baseParams, params);
				});
		this.storeList.removeAll();
		this.storeList.load({
					params : params
				});
		this.grid.on("rowdblclick", this.lookInfo, this);
		return this.grid;
	},

	getButtons : function() {
		return [{
			xtype : 'button',
			style : 'width:50;text-align:right;margin-left:20px',
			text : '�ύ',
			scope : this,
			iconCls : 'submit',
			id : 'postButton',
			handler : function() {
				Ext.getCmp('postButton').disable();
				// 2010-04-28 modified by huzh for ��֤�޸� begin
				if (Ext.getCmp("cataPanel").getForm().isValid()
						&& Ext.getCmp("servicePanel").getForm().isValid()) {
					var eventtype = Ext.getCmp('eventtype');
					if (eventtype.getValue() == "") {
						eventtype.clearValue();
						Ext.MessageBox.alert("��ʾ", "��������б���ѡ����ȷ�������࣬������Ч��");
						Ext.getCmp("postButton").enable();
						return;
					}
					// 2010-04-28 modified by huzh for ��֤�޸� end
					// var panelparam = Ext.encode(Ext.getCmp("cataPanel").form.getValues(false));
					var panelparam = Ext.encode(getFormParam('cataPanel'));
					panelparam = unicode(panelparam);
					Ext.Ajax.request({
						waitTitle : "���Ժ�",
						waitMsg : " ���������ύ���Ժ�........",
						url : webContext + '/eventAction_submitEvent.action',
						method : "POST",
						params : {
							panelparam : panelparam,
							eventtype : Ext.getCmp("eventtype").getValue()
						},
						success : function(response, options) {
							var userID = eval('(' + response.responseText + ')').userID;
							var eventName = eval('(' + response.responseText
									+ ')').eventName;
							var eventCisn = eval('(' + response.responseText
									+ ')').eventCisn;
							var eventSubmitUser = eval('('
									+ response.responseText + ')').eventSubmitUser;
							var eventSubmitDate = eval('('
									+ response.responseText + ')').eventSubmitDate
							this.dataId = eval('(' + response.responseText
									+ ')').eventId;
							var users = userID;
							if (users != null && users != '') {// 2010-04-28 modified by huzh for �й���ʦ����
								Ext.Ajax.request({
									url : webContext
											+ '/eventWorkflow_apply.action',
									params : {
										dataId : this.dataId,
										model : this.model,
										bzparam : "{dataId :'"
												+ this.dataId
												+ "',users:'engineerProcess:"
												+ users
												+ "',applyId : '"
												+ this.dataId
												+ "',eventName : '"
												+ eventName
												+ "',eventSubmitUser:'"
												+ eventSubmitUser
												+ "',eventSubmitDate:'"
												+ eventSubmitDate
												+ "',eventCisn:'"
												+ eventCisn
												+ "', applyType: 'eproject',applyTypeName: '�¼�����������',customer:'',workflowHistory:'com.digitalchina.itil.event.entity.EventAuditHis'}",
										defname : 'eventAndProblemProcess1240370895640'
									},
									success : function(response, options) {
										Ext.Msg.alert("��ʾ",
												"���ѳɹ��ύIT���⣬���ǻ���2��ʱ��������ϵ�������",
												function() {
													window.location = webContext
															+ "/eventAction_toCreatePage.action";
												});
									},
									failure : function(response, options) {
										Ext.MessageBox.alert("��ʾ", "�����ύʧ�ܣ�");
										Ext.getCmp("postButton").enable();
									}
								}, this);
							} else {
								Ext.MessageBox.alert("��ʾ", "һ��֧���鹤��ʦҲû�У�");// 2010-04-28 modified by huzh for û�й���ʦ
								Ext.getCmp("postButton").enable();
							}
						},
						failure : function(response, options) {
							Ext.MessageBox.alert("��ʾ", "�ύʧ�ܣ�");
							Ext.getCmp("postButton").enable();
						}
					}, this);

				} else {
					Ext.MessageBox.alert("��ʾ", "��ɫ�����߲���Ϊ����������޷��ύ��");
					Ext.getCmp("postButton").enable();
				}
			}
		}]
	},
	split : function(data) {
		var labellen = 70;
		var itemlen = 200;
		var throulen = 480;
		if (Ext.isIE) {
			throulen = 450;
		} else {
			throulen = 490;
		}
		if (data == null || data.length == 0) {
			return null;
		}
		var hid = 0;
		var hidd = new Array();
		var longData = new Array();
		var longitems = 0;
		// alert(this.dataId);
		for (i = 0; i < data.length; i++) {
			data[i].style = data[i].style == null ? "" : data[i].style;
			if (data[i].xtype == "textarea") {
				data[i].style += "'margin:2 0 2 0;'";
			}
			if (data[i].xtype == "hidden") {
				hidd[hid] = data[i];
				hid++;
				continue;
			}
			// add by lee for Ϊ�����б����ӿ���ק�������޸Ĳ��ܿ�ȫ��Ϣ��BUG in 20091112 BEGIN
			if (data[i].xtype == "combo") {
				data[i].resizable = true;
			}
			// add by lee for Ϊ�����б����ӿ���ק�������޸Ĳ��ܿ�ȫ��Ϣ��BUG in 20091112 END
			if (data[i].width == null || data[i].width == 'null'
					|| data[i].width == "") {
				data[i].style += "width:" + itemlen + "px";
				data[i].width = itemlen;
			} else {
				if (data[i].width == "9999") {// ͨ��
					if ((i - hid + longitems) % 2 == 1) {// ���Ҳ�����ǰһ����ͨ
						longData[2 * (i - hid) - 1].colspan = 3;
					} else {// ��ռһ��
						longitems++;
					}
					data[i].colspan = 3;// ������ͨ
					data[i].width = throulen;
					if (data[i].xtype == "textarea") {
						data[i].height = 200;
						data[i].width = 470;
					}
					data[i].style += "width:" + throulen + "px;";
				} else {// �������ȣ�����
					data[i].style += "width:" + itemlen + "px";
					data[i].width = itemlen;
				}
			}
			longData[2 * (i - hid)] = {
				html : data[i].fieldLabel + ":",
				cls : 'common-text',
				style : 'width:' + labellen + ';text-align:right'
			};
			longData[2 * (i - hid) + 1] = data[i];
		}
		for (i = 0; i < hidd.length; i++) {
			longData[longData.length] = hidd[i];
		}

		return longData;
	},
	items : this.items,
	initComponent : function() {
		var items = new Array();
		items.push(this.getPanel());
		items.push(this.getEventPanel());
		Ext.getCmp('eventtype').setValue("");
		this.items = items;
		PagePanel.superclass.initComponent.call(this);
	}
})
