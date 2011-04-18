PagePanel = Ext.extend(Ext.Panel, {
	id : "createknowledgeManager",
	closable : true,
	autoScroll : true,
	viewConfig : {
		autoFill : true,
		forceFit : true
	},
	layout : 'fit',
	// ֪ʶ����ѡ��
	knowledgeType : function() {
		var da = new DataAction();
		var data1 = da.getPanelElementsForAdd(this.panel);
		for (var i = 0; i < data1.length; i++) {
			if (data1[i].name == 'Knowledge$knowledgeCisn'
					|| data1[i].name == 'KnowFile$number'
					|| data1[i].name == 'KnowContract$number') {
				data1[i].disabled = true;
				data1[i].emptyText = "�Զ�����";
			}
     //2010-05-13 delete by huzh for û�� begin
//			if (data1[i].name == 'Knowledge$reason') {
//				data1.remove(data1[i]);
//			}
	  //2010-05-13 delete by huzh for û�� end
			if (data1[i].name == 'Knowledge$serviceItem') {
				data1[i].on('select', function() {
							Ext.getCmp('Knowledge$knowProblemTypeCombo')
									.clearValue();
						}, this);
			}
			if (data1[i].name == 'Knowledge$knowProblemType') {
				data1[i].store = new Ext.data.JsonStore({
					url : webContext
							+ '/extjs/comboDataAction?clazz=com.digitalchina.itil.knowledge.entity.KnowProblemType',
					fields : ['id', 'name'],
					listeners : {
						beforeload : function(store, opt) {
							if (opt.params['Knowledge$knowProblemType'] == undefined) {
								opt.params['name'] = Ext
										.getCmp('Knowledge$knowProblemTypeCombo').defaultParam;
								opt.params['serviceItem'] = Ext
										.getCmp('Knowledge$serviceItemCombo')
										.getValue();
							//2010-04-20 add by huzh for ���˵���ɾ������������ begin
								opt.params['deleteFlag'] = 0;
							//2010-04-20 add by huzh for ���˵���ɾ������������ end
							}
						}
					},
					totalProperty : 'rowCount',
					root : 'data',
					id : 'id'
				});
			}
		}
		var data = da.split(data1);
		this.panelType = new Ext.form.FormPanel({
			id : 'panelType',
			layout : 'table',
			height : 'auto',
			width : 820,
			frame : true,
			collapsible : true,
			defaults : {
				bodyStyle : 'padding:6px'
			},
			layoutConfig : {
				columns : 4
			},
			title : '��д֪ʶ�ݸ�<font color="black">��</font><font color="red">*</font><font color="black" style="font-weight:lighter" face="����_GB2312">Ϊ������,������д!��</font>',
			items : data
		});
		return this.panelType;
	},

	// ��ʼ��
	initComponent : function() {
		var templeteId;
		this.knowtype = this.knowledgeType();

		var conn = Ext.lib.Ajax.getConnectionObject().conn;
		conn.open("POST",webContext
								+ '/knowledgeAction_getKnowledgeTypeData.action?KnowledgeTypeId='
								+ this.knowLedgeTypeId, false);
		conn.send(null);
		var result = Ext.decode(conn.responseText);
		this.MainTableName = result.MainTableName;
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
					if (!Ext.getCmp('panelType').form.isValid()) {
						Ext.MessageBox.alert("��ʾ", "ҳ���д���ɫ�����ߵ�Ϊ���������д���������");
						return false;
					}
					Ext.getCmp('savedaraftbutton').disable();
					Ext.getCmp('submitbutton').disable();
					if (Ext.getCmp("panelType").getForm().isValid()) {

						var panelTypeParam = Ext
								.encode(Ext.getCmp("panelType").form
										.getValues(false));
						var knowLedgeFlag = "";
						var resolvent = "";
						// //////////////////////////////////////////////////////////////////
						if (this.MainTableName == "Knowledge") {
							knowLedgeFlag = "know"; //���������Ƿ�Ϊ�����������
							resolvent = Ext.encode(Ext
									.getCmp('Knowledge$resolvent').getValue());
						}
						var mainTablea=this.MainTableName;
						Ext.Ajax.request({
							url : webContext
									+ '/knowledgeAction_saveKnowledgeDraft.action',
							params : {
								KnowledgeTypeID : this.knowLedgeTypeId,
								panelTypeParam : panelTypeParam,
								resolvent : resolvent,
								knowLedgeFlag : knowLedgeFlag
							},
							// /////////////////////////////////////
							method : 'post',
							success : function(response) {
								Ext.MessageBox.alert("��ʾ", "����ݸ�ɹ���",function(){
								Ext.getCmp('savedaraftbutton').enable();
								Ext.getCmp('submitbutton').enable();
								templeteId = eval('(' + response.responseText
										+ ')').templeteId;
								 if (mainTablea == "Knowledge") {
									window.location = webContext
											+ '/user/knowledge/knowledgeManager/knowledgeManager.jsp';
									}else if(mainTablea == "KnowFile"){
										window.location = webContext
											+ '/user/knowledge/knowledgeManager/knowFileManager.jsp';
									}else{
										window.location = webContext
											+ '/user/knowledge/knowledgeManager/knowContractManager.jsp';
								}
								});
								
								// modified by huzh end
							},
							failure : function(response, options) {
								Ext.getCmp('savedaraftbutton').enable();
								Ext.getCmp('submitbutton').enable();
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
					if (!Ext.getCmp('panelType').form.isValid()) {
						Ext.MessageBox.alert("��ʾ", "ҳ���д���ɫ�����ߵ�Ϊ���������д���������");
						return false;
					}
					Ext.getCmp('savedaraftbutton').disable();
					Ext.getCmp('submitbutton').disable();
					var panelTypeParam = Ext.encode(Ext.getCmp("panelType").form.getValues(false));
					var knowLedgeFlag = "";
					var resolvent = "";
					if (this.MainTableName == "Knowledge") {
						knowLedgeFlag = "know";
						resolvent = Ext.encode(Ext.getCmp('Knowledge$resolvent').getValue());
					}
					var knowLTypeId = this.knowLedgeTypeId;
					var mainTable=this.MainTableName;
					Ext.Ajax.request({
						url : webContext
								+ '/knowledgeAction_saveKnowledgeDraft.action',
						params : {
							KnowledgeTypeID : this.knowLedgeTypeId,
							panelTypeParam : panelTypeParam,
							resolvent : resolvent,
							knowLedgeFlag : knowLedgeFlag
						},
						method : 'post',
						success : function(response) {
							var proName='';
							if (mainTable == "Knowledge") {
							   proName="KnowledgeProcess1270117935593";
							}else if(mainTable == "KnowFile"){
							   proName="KnowFileProcess1270118003203";
							}else{
							    proName="KnowContractProcess1270118032000";
							}
							//add by huzh
							templeteId = eval('(' + response.responseText + ')').templeteId;
							var panelTypeParam = Ext.encode(Ext
									.getCmp("panelType").form.getValues(false));

							var dataId = templeteId;// ����Id
							var dataType = knowLTypeId;// this.knowLedgeTypeId;//��������Id
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
//									defname : 'KnowledgeProcess1270117935593'
								    defname : proName
								},
								success : function(response) {
									var meg = Ext.decode(response.responseText);
									if (meg.Exception != undefined) {
										Ext.Msg.alert("��ʾ", meg.Exception);
									} else {
										Ext.Msg.alert("��ʾ", "�ύ�ɹ���",
												function() {
													if (mainTable == "Knowledge") {
														window.location = webContext
																+ '/user/knowledge/knowledgeManager/knowledgeManager.jsp';
														}else if(mainTable == "KnowFile"){
															window.location = webContext
																+ '/user/knowledge/knowledgeManager/knowFileManager.jsp';
														}else{
															window.location = webContext
																+ '/user/knowledge/knowledgeManager/knowContractManager.jsp';
													}
												});
									}
								}
							});

						},
						failure : function(response, options) {
							Ext.getCmp('savedaraftbutton').enable();
							Ext.getCmp('submitbutton').enable();
							Ext.MessageBox.alert("��ʾ", "����ݸ�ʧ�ܣ�");
						}
					});
				},
				scope : this
			}, {
				xtype : 'button',
				text : '����',
				iconCls : 'back',
				handler : function() {
					if (this.MainTableName == "Knowledge") {
					window.location = webContext
							+ '/user/knowledge/knowledgeManager/knowledgeManager.jsp';
					}else if(this.MainTableName == "KnowFile"){
						window.location = webContext
							+ '/user/knowledge/knowledgeManager/knowFileManager.jsp';
					}else{
						window.location = webContext
							+ '/user/knowledge/knowledgeManager/knowContractManager.jsp';
					}
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
					height : 'auto',
					frame : true,
					layoutConfig : {
						columns : 1
					},
					items : [this.knowtype, this.mybutton]
				});

		this.add(this.Panelss);
	}
});