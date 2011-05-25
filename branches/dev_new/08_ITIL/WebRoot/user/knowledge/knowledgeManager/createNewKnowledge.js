PagePanel = Ext.extend(Ext.Panel, {
	id : "createNewKnowledge",
	closable : true,
	autoScroll : true,
	viewConfig : {
		autoFill : true,
		forceFit : true
	},
	layout : 'fit',
	getKnowledgeFrom : function() {
		var da = new DataAction();
		var data = da.getPanelElementsForAdd("KnowLedgeSolution_pagepanel");
		for (var i = 0; i < data.length; i++) {
			if (data[i].name == 'Knowledge$knowledgeCisn') {
				data[i].disabled = true;
				data[i].emptyText = "�Զ�����";
			}
			if (data[i].name == 'Knowledge$serviceItem') {
				data[i].store.on('beforeload',function(store, opt) {
					if (opt.params['Knowledge$serviceItem'] == undefined) {
						opt.params['name'] = Ext.getCmp('Knowledge$serviceItemCombo').defaultParam;
						opt.params['official'] = 1;
					}
				});
				data[i].on('select', function() {
							Ext.getCmp('Knowledge$knowProblemTypeCombo').clearValue();
				});
			}
			if (data[i].name == 'Knowledge$knowProblemType') {
				data[i].store.on('beforeload',function(store, opt) {
							if (opt.params['Knowledge$knowProblemType'] == undefined) {
								opt.params['name'] = Ext.getCmp('Knowledge$knowProblemTypeCombo').defaultParam;
								opt.params['serviceItem'] = Ext.getCmp('Knowledge$serviceItemCombo').getValue();
								opt.params['deleteFlag'] = 0;
							}
						});
			}
		}
		var dataItem = da.split(data);
		this.fromPanel = new Ext.form.FormPanel({
			id : 'fromPanel',
			layout : 'table',
			height : 300,
			width : 820,
			frame : true,
			collapsible : true,
			defaults : {
				bodyStyle : 'padding:6px'
			},
			layoutConfig : {
				columns : 4
			},
			title : '��д��������ݸ�<font color="black">��</font><font color="red">~~~</font><font color="black" style="font-weight:lighter" face="����_GB2312">Ϊ������,������д!��</font>',
			items : dataItem
		});
		return this.fromPanel;
	},
	items : this.items,
	initComponent : function() {
		this.fromPanel = this.getKnowledgeFrom();
		this.mybutton = {
			layout : 'table',
			height : 'auto',
			width : 'auto',
			style : 'margin:4px 6px 4px 300px',
			colspan : 4,
			align : 'center',
			defaults : {
				bodyStyle : 'padding:4px'
			},
			layoutConfig : {
				columns : 4
			},
			items : [{
				xtype : 'button',
				id : 'savedaraftbutton',
				text : '�ݴ�',
				iconCls : 'save',
				handler : function() {
					var si=Ext.getCmp('Knowledge$serviceItemCombo');
					var kt=Ext.getCmp('Knowledge$knowProblemTypeCombo');
					var su=Ext.getCmp('Knowledge$summary');
					if(su.getValue().trim()==""){
						su.setValue('');
					}
					if(si.getValue().trim()==""){
						si.clearValue();
					}
					if(kt.getValue().trim()==""){
						kt.clearValue();
					}
					if (!Ext.getCmp('fromPanel').form.isValid()) {
						Ext.MessageBox.alert("��ʾ", "����ɫ�����ߵĲ���Ϊ�����");
						return false;
					}
					Ext.getCmp('savedaraftbutton').disable();
					Ext.getCmp('submitbutton').disable();
					Ext.getCmp('backbutton').disable();
					if (Ext.getCmp("fromPanel").getForm().isValid()) {
						var knowledgeParam = Ext.encode(getFormParam('fromPanel'));
						Ext.Ajax.request({
							url : webContext
									+ '/knowledgeAction_createKnowledge.action',
							params : {
								knowledgeParam : knowledgeParam
							},
							method : 'post',
							success : function(response) {
								Ext.MessageBox.alert("��ʾ", "����ݸ�ɹ���",function(){
								Ext.getCmp('savedaraftbutton').enable();
								Ext.getCmp('submitbutton').enable();
								Ext.getCmp('backbutton').enable();
									window.location = webContext
											+ '/user/knowledge/knowledgeManager/knowledgeManager.jsp';
								});
							},
							failure : function(response, options) {
								Ext.getCmp('savedaraftbutton').enable();
								Ext.getCmp('submitbutton').enable();
								Ext.getCmp('backbutton').enable();
								Ext.MessageBox.alert("��ʾ", "����ݸ�ʧ�ܣ�");
							}
						});
					}
				},
				scope : this
			}, {
				xtype : 'button',
				id : 'submitbutton',
				text : '�ύ',
				iconCls : 'submit',
				handler : function() {
					var si=Ext.getCmp('Knowledge$serviceItemCombo');
					var kt=Ext.getCmp('Knowledge$knowProblemTypeCombo');
					var su=Ext.getCmp('Knowledge$summary');
					if(su.getValue().trim()==""){
						su.setValue('');
					}
					if(si.getValue().trim()==""){
						si.clearValue();
					}
					if(kt.getValue().trim()==""){
						kt.clearValue();
					}
					if (!Ext.getCmp('fromPanel').form.isValid()) {
						Ext.MessageBox.alert("��ʾ", "����ɫ�����ߵĲ���Ϊ�����");
						return false;
					}
					Ext.getCmp('savedaraftbutton').disable();
					Ext.getCmp('submitbutton').disable();
					Ext.getCmp('backbutton').disable();
					var knowledgeParam = Ext.encode(getFormParam('fromPanel'));
					Ext.Ajax.request({
						url : webContext
								+ '/knowledgeAction_createKnowledge.action',
						params : {
							knowledgeParam : knowledgeParam
						},
						method : 'post',
						success : function(response) {
							var dataId = eval('(' + response.responseText + ')').dataId;
							var dataType = 8;
							Ext.Ajax.request({
								url : webContext
										+ '/knowledgeWorkflow_apply.action',
								params : {
									dataId : dataId,
									model : this.model,
									bzparam : "{dataId :'"
											+ dataId
											+ "',dataType : '"
											+ dataType
											+ "',applyId : '"
											+ dataId
											+ "', applyType: 'kproject',applyTypeName: '֪ʶ����',customer:''}",
									defname : 'KnowledgeProcess1306209462284'
								},
								success : function(response) {
									var meg = Ext.decode(response.responseText);
									if (meg.Exception != undefined) {
										Ext.Msg.alert("��ʾ", meg.Exception);
									} else {
										Ext.Msg.alert("��ʾ", "�ύ�ɹ���",
												function() {
														window.location = webContext
																+ '/user/knowledge/knowledgeManager/knowledgeManager.jsp';
												});
									}
								}
							});

						},
						failure : function(response, options) {
							Ext.getCmp('savedaraftbutton').enable();
							Ext.getCmp('submitbutton').enable();
							Ext.getCmp('backbutton').enable();
							Ext.MessageBox.alert("��ʾ", "����ݸ�ʧ�ܣ�");
						}
					});
				},
				scope : this
			}, {
				xtype : 'button',
				text : '����',
				iconCls : 'back',
				id :'backbutton',
				handler : function() {
					window.location = webContext
							+ '/user/knowledge/knowledgeManager/knowledgeManager.jsp';
				},
				scope : this
			}]
		};
		this.Panelss = new Ext.Panel({
					id : "knowledge",
					align : 'center',
					layout : 'table',
					border : true,
					autoScroll : true,
					defaults : {
						bodyStyle : 'padding:5px'
					},
					width : 720,
					height : 300,
					frame : true,
					layoutConfig : {
						columns : 1
					},
					items : [this.fromPanel, this.mybutton]
				});
				this.items=[this.Panelss];
				PagePanel.superclass.initComponent.call(this);
	}
});