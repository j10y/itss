PagePanel = Ext.extend(Ext.Panel, {
	id : "knowledgePage",
	closable : true,
	autoScroll : true,
	viewConfig : {
		autoFill : true,
		forceFit : true
	},
	layout : 'fit',

	getPanelArea : function(knowledgeId) {
//		var conn = Ext.lib.Ajax.getConnectionObject().conn;
//		conn.open("POST",webContext+ '/knowledgeAction_queryKnowledgetId.action?panel=KnowLedgeSolution_pagepanel',
//						false);
//		conn.send(null);
//		var result = Ext.decode(conn.responseText);
//		var knowledgeId = result.knowledgeId;
		var dataId = knowledgeId; //this.knowLedgeTypeId;
		var contents = "";
//				alert(dataId);
		var contentIntro = new Ext.form.TextArea({
			xtype : "textarea",
			height : 300,
			name : "KnowledgeModifyContent",
			width : 780,
			id : "KnowledgeModifyContent",
			listeners : {
				"render" : function(f) {
					var fckEditor = new FCKeditor("KnowledgeModifyContent");
					//				  			var oEditor = FCKeditorAPI.GetInstance("content");
					//        					oEditor.GetXHTML(true);
					//fckEditor.UpdateLinkedField();  
					//alert(FCKeditorAPI.GetInstance('content').GetXHTML( true ));  
					// var editorInstance=FCKeditorAPI.GetInstance('content');  
					//alert(Ext.get('content').dom.value);  
					if (dataId != 0) {
						Ext.Ajax.request({
							url : webContext
									+ '/noticeaction_findKnowledge.action',
							method : "POST",
							params : {
								dataId : dataId
							},
							success : function(response, options) {
								//alert(FCKeditorAPI+"---2");
								//											alert(response.responseText);
								//											var responseArray = Ext.util.JSON.decode(response.responseText);
								//											alert(responseArray);
								//var content = responseArray.content;
//								alert(response.responseText);
								contents = response.responseText;
//								alert(contents);
								Ext.get('KnowledgeModifyContent').dom.value = contents;//IEˢ��ʱ�������� �����ʹ
								fckEditor.GetData = contents;
								var oEditor = FCKeditorAPI
										.GetInstance("KnowledgeModifyContent");//IE��ʹ ���FCKeditorAPIδ����
								oEditor.SetHTML(contents);
							}
						})
					}
					//Ext.get('content').dom.value=content;//д��Ajax���� ��ֵset��ȥ
					//Ext.get('content').dom.value=fckEditor.Value;
					Ext.get('KnowledgeModifyContent').dom.value = contents;
					fckEditor.GetData = contents;
					fckEditor.Height = 300;
					fckEditor.Width = 780;
					fckEditor.BasePath = webContext + "/FCKeditor/";
					//fckEditor.Config['CustomConfigurationsPath'] = webContext+"/FCKeditor/fckconfig.js" ;
					fckEditor.ToolbarSet = "Default";
					fckEditor.ReplaceTextarea();
					//				  			function FCKeditor_OnComplete( instance ) {  
					//				  				alert("nnnnnn");
					//								     editorInstance=instance;  
					//								 };  
				}
			}
		});
		var item = new Array();
		item.push({
			//html : "��������:",
			cls : 'common-text',
			style : 'text-align:left'
		}, contentIntro);

		var cpanel = new Ext.form.FormPanel({
			id : 'KnowledgeModifyContentPanel',
			title : '�������',
			layout : 'table',
			region : 'north',
			height : 'auto',
			width : 820,
			frame : true,
			collapsible : true,
			defaults : {
				bodyStyle : 'padding:2px'
			},
			layoutConfig : {
				columns : 8
			},
			items : item
		});
		return cpanel;

	},

	dealfile : function() { //��������޸ĵ�����window
		var record = Ext.getCmp('KnowLedgeListGrid').getSelectionModel()
				.getSelected();
		var records = Ext.getCmp('KnowLedgeListGrid').getSelectionModel()
				.getSelections();
		if (!record) {
			Ext.Msg.alert("��ʾ", "����ѡ��Ҫ�鿴���У�");
			return;
		}
		if (records.length > 1) {
			Ext.Msg.alert("��ʾ", "�鿴ʱֻ��ѡ��һ�У�");
			return;
		}
		var knowledgeId = record.get('Knowledge$id');
		var da = new DataAction();
		var data = da.getSingleFormPanelElementsForEdit(
				"KnowLedgeSolution_pagepanel", knowledgeId);
		for (var i = 0; i < data.length; i++) {
			if (data[i].name == 'Knowledge$resolvent') {
				data.remove(data[i]);
			}
		}
		var dataform = this.splitOwn(data);
		var knowForm = new Ext.form.FormPanel({
			id : 'knowledgeSkip',
			layout : 'table',
			width : 820,
			height : 90,
			layoutConfig : {
				columns : 4
			},
			defaults : {
				bodyStyle : 'padding:12px'
			},
			frame : true,
			items : dataform

		});
		var fckPanel = this.getPanelArea(knowledgeId);
		var windowSkip = new Ext.Window({
			title : '�鿴��ϸ��Ϣ',
			width : 820,
			height : 'auto',
			modal : true,
			autoScroll : true,
			maximizable : true,
			items : [knowForm, fckPanel],
			closeAction : 'hide',
			bodyStyle : 'padding:4px',
			buttons : [{
				text : '�ݴ�',
				id : 'savelistbutton',
				handler : function() {
					Ext.getCmp("savelistbutton").disable();
					if (Ext.getCmp("knowledgeSkip").getForm().isValid()) {
						var panelTypeParam = Ext.encode(Ext
								.getCmp("knowledgeSkip").form.getValues(false));
						var oEditor = FCKeditorAPI.GetInstance("KnowledgeModifyContent");//FCKeditor��ʼ���Ժ������FCKeditorAPI
						oEditor.GetXHTML();
						Ext.get('KnowledgeModifyContent').dom.value = oEditor;
						Ext.Ajax.request({
							url : webContext
									+ '/knowledgeAction_saveKnowledgeType.action',
							params : {
								pagePanelString : "KnowLedgeSolution_pagepanel",
								panelTypeParam : panelTypeParam,
								resolvent:Ext.encode(oEditor.GetData())
							},
							method : 'post',
							success : function() {
								Ext.MessageBox.alert("��ʾ", "�������ݳɹ���");
								//window.location.reload();  
								windowSkip.close();
							},
							failure : function(response, options) {
								Ext.MessageBox.alert("��ʾ", "��������ʧ�ܣ�");
							}
						});
					}
				},
				scope : this
			}, {
				text : '�ύ',
				id : 'submitknowbutton',
				handler : function() {
					Ext.getCmp("submitknowbutton").disable();
					if (Ext.getCmp("knowledgeSkip").getForm().isValid()) {
						var panelTypeParam = Ext.encode(Ext
								.getCmp("knowledgeSkip").form.getValues(false));
						Ext.Ajax.request({
							url : webContext
									+ '/knowledgeAction_saveKnowledgeEntity.action',
							params : {
								knowledgeId : knowledgeId,
								panelTypeParam : panelTypeParam
							},
							method : 'post',
							success : function(response) {
								
								var knowledgeIDd = eval('('
										+ response.responseText + ')').knowledgeIDd;
								var knowledgeTypeDD = eval('('
										+ response.responseText + ')').knowledgeTypeDD;
								
												//---------------------------------------------
												//�ύ���̣���ͬ������������ļ���
												var dataId = knowledgeIDd;//����Id
												var dataType = knowledgeTypeDD;//��������Id
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
														defname : 'KnowledgeProcess1276415839731'
													},
													success:function(response){
														var meg = Ext.decode(response.responseText);
														if (meg.Exception != undefined) {
															Ext.Msg.alert("��ʾ",meg.Exception);
														}else{
															Ext.Msg.alert("��ʾ","�ύ�ɹ���",function(){
																windowSkip.close();
															});
														}
													}
											});
												
												//---------------------------------------------
												//window.location.reload();
								//window.location.reload();
							},
							failure : function(response, options) {
								Ext.getCmp("submitknowbutton").enable();
								Ext.MessageBox.alert("��ʾ", "��������ʧ�ܣ�");
							}
						});

					}
				},
				scope : this
			}, {
				text : '�ر�',
				handler : function() {
					windowSkip.close();
				},
				scope : this
			}]
		});
		windowSkip.show();
	},
	skipfile : function() {
		this.modleSkipName = Ext.getCmp('knowledgeTypeCombo').MainTableName;
		var pagePanelString = Ext.getCmp('knowledgeTypeCombo').pagePanelName;
		var KnowledgeTypeID = Ext.getCmp('knowledgeTypeCombo').KnowledgeTypeId;
		var record = Ext.getCmp('KnowLedgeListGrid').getSelectionModel()
				.getSelected();
		var records = Ext.getCmp('KnowLedgeListGrid').getSelectionModel()
				.getSelections();
		if (!record) {
			Ext.Msg.alert("��ʾ", "����ѡ��Ҫ�鿴���У�");
			return;
		}
		if (records.length > 1) {
			Ext.Msg.alert("��ʾ", "�鿴ʱֻ��ѡ��һ�У�");
			return;
		}
		var SkipKnowId = record.get(this.modleSkipName + '$id');
		var clazz = Ext.getCmp('knowledgeTypeCombo').className;
		var da = new DataAction();
		// var data=da.getElementsForEdit(clazz,SkipKnowId);
		var data = da.getSingleFormPanelElementsForEdit(pagePanelString,
				SkipKnowId);
		var dataform = this.splitOwn(data);
		var knowForm = new Ext.form.FormPanel({
			id : 'knowledgeSkip',
			layout : 'table',
			width : 650,
			height : 'auto',
			layoutConfig : {
				columns : 4
			},
			defaults : {
				bodyStyle : 'padding:5px'
			},
			frame : true,
			items : dataform

		});
		var windowSkip = new Ext.Window({
			title : '�鿴��ϸ��Ϣ',
			width : 700,
			height : 'auto',
			modal : true,
			autoScroll : true,
			maximizable : true,
			items : knowForm,
			closeAction : 'hide',
			bodyStyle : 'padding:4px',
			buttons : [{
				text : '�ݴ�',
				handler : function() {
					if (Ext.getCmp("knowledgeSkip").getForm().isValid()) {
						var panelTypeParam = Ext.encode(Ext
								.getCmp("knowledgeSkip").form.getValues(false));
						Ext.Ajax.request({
							url : webContext
									+ '/knowledgeAction_saveKnowledgeType.action',
							params : {
								KnowledgeTypeID : KnowledgeTypeID,
								pagePanelString : pagePanelString,
								panelTypeParam : panelTypeParam
							},
							method : 'post',
							success : function() {
								Ext.MessageBox.alert("��ʾ", "�������ݳɹ���");
								//window.location.reload();  
								windowSkip.close();
							},
							failure : function(response, options) {
								Ext.MessageBox.alert("��ʾ", "��������ʧ�ܣ�");
							}
						});
					}
				},
				scope : this
			}, {
				id : 'submit1',
				text : '�ύ',
				handler : function() {
					Ext.getCmp('submit1').disable();
					if (Ext.getCmp("knowledgeSkip").getForm().isValid()) {
						var panelTypeParam = Ext.encode(Ext
								.getCmp("knowledgeSkip").form.getValues(false));
						Ext.MessageBox.confirm('��ȷ��', '�Ƿ����Ҫ�ύ��', function(
								button, text) {
							if (button == 'yes') {
								//*******************************************************
								Ext.Ajax.request({//���±���ݸ�
									url : webContext
											+ '/knowledgeAction_saveKnowledgeEntity.action',
									params : {
										knowledgeId : SkipKnowId,
										KnowledgeTypeID : KnowledgeTypeID,
										panelTypeParam : panelTypeParam
									},
									method : 'post',
									success : function(response) {
										Ext.MessageBox
												.alert("��ʾ", "�������ݳɹ���");
										//---------------------------------------------
										//�ύ���̣���ͬ������������ļ���
										var dataId = SkipKnowId;//����Id
										var dataType = KnowledgeTypeID;//��������Id
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
														defname : 'knowledgeProcess1240978267609'
													},
													success:function(response){
														var meg = Ext.decode(response.responseText);
														if (meg.Exception != undefined) {
															Ext.Msg.alert("��ʾ",meg.Exception);
														}else{
															Ext.Msg.alert("��ʾ","�ύ�ɹ���",function(){
																windowSkip.close();
															});
														}
													}
											});
									},
									failure : function(response, options) {
										Ext.MessageBox
												.alert("��ʾ", "��������ʧ�ܣ�");
									}
								});
								//*******************************************
							} else {
								//�Ƿ��а�ť
								Ext.getCmp('submit1').enable();
							}
						});
					}
				},
				scope : this
			}, {
				text : '�ر�',
				handler : function() {
					windowSkip.close();
				},
				scope : this
			}]
		});
		windowSkip.show();
	},
	searchForm : function() {
		var param = Ext.getCmp('queryKnowLedgeType').getForm().getValues(false);
		param.methodCall = 'query';
		param.start = 1;
		param.status = 0;
		this.formValue = param;
		this.pageBar.formValue = this.formValue;
		this.store.removeAll();
		this.store.load({
			params : param
		});
	},
	changeKnowledgePanel : function(className, panelname, tableName, id,
			queryPanel) {
		var da = new DataAction();
		var data1 = da.getPanelElementsForQuery(queryPanel);
		var data = da.split(data1);
		//		    alert(queryPanel);
		var queryKnowLedgeType = new Ext.form.FormPanel({
			id : 'queryKnowLedgeType',
			layout : 'table',
			height : 'auto',
			width : 702,
			frame : true,
			collapsible : true,
			defaults : {
				bodyStyle : 'padding:4px'
			},
			layoutConfig : {
				columns : 6
			},
			title : '����' + Ext.getCmp('knowledgeTypeCombo').knowName,
			items : data
		});

		var DataHead = da.getListPanelElementsForHead(panelname);
		this.modelTableName = tableName;
		var sm = new Ext.grid.CheckboxSelectionModel();
		var columns = new Array();
		var fields = new Array();
		columns[0] = sm;
		for (var i = 0; i < DataHead.length; i++) {
			var headItem = DataHead[i];
			var title = headItem.header;
			var alignStyle = 'left';
			var propertyName = headItem.dataIndex;
			var isHiddenColumn = false;
			var isHidden = headItem.isHidden;
			if (isHidden == 'true') {
				isHiddenColumn = true;
			}
			var columnItem = {
				header : title,
				dataIndex : propertyName,
				sortable : true,
				hidden : isHiddenColumn,
				align : alignStyle
			}
			columns[i + 1] = columnItem;
			fields[i] = propertyName;
		}
		this.storeMapping = fields;
		this.cm = new Ext.grid.ColumnModel(columns);
		this.store = da.getPanelJsonStore(panelname, DataHead);
		this.store.paramNames.sort = "orderBy";
		this.store.paramNames.dir = "orderType";
		this.cm.defaultSortable = true;
		var viewConfig = Ext.apply({
			forceFit : true
		}, this.gridViewConfig);
		this.pageBar = new Ext.PagingToolbar({
			pageSize : 10,
			store : this.store,
			displayInfo : true,
			displayMsg : '��ǰ��ʾ {0}-{1}����¼ /��{2}����¼',
			emptyMsg : "����ʾ����"
		});
//		this.formValue = '';
//		this.pageBar.formValue = this.formValue;
		var KnowLedgeListGrid = new Ext.grid.GridPanel({
			id : 'KnowLedgeListGrid',
			store : this.store,
			cm : this.cm,
			sm : sm,
			trackMouseOver : false,
			loadMask : true,
			width : 702,
			height : 300,
			tbar : [
					{
						text : '�½�',
						handler : function() {
							window.location = webContext
									+ "/user/knowledge/createKnowledgeManager.jsp?panel="
									+ Ext.getCmp('knowledgeTypeCombo').pagePanelName
									+ "&knowLedgeTypeId=" + id;
						},
						scope : this,
						iconCls : 'add'
					}, '-', {
						text : '��ѯ',
						handler : this.searchForm,
						scope : this,
						iconCls : 'search'
					}, '-', {
						text : '�޸�',
						handler : this.skipfile,
						scope : this,
						iconCls : 'edit'
					},
					'&nbsp;&nbsp;<font color="black" face="����_GB2312">��˫����鿴��ϸ��Ϣ��</font>'],
			bbar : this.pageBar
		});
		var param = {
			'mehtodCall' : 'query',
			'status' : 0,
			'start' : 0
		};
//		this.pageBar.formValue = param;
		this.store.on('beforeload', function(a) {   
					      Ext.apply(a.baseParams,param);   
					});
		this.store.removeAll();
		this.store.load({
			params : param
		});
		Ext.getCmp('KnowLedgeListGrid').on("rowdblclick", this.skipfile, this);
		var PanelKnowledge = Ext.getCmp('PanelKnowledgeData');
		//		if(tableName=="Knowledge"){
		////			Ext.getCmp("knowledgePage").initComponent();
		//		}else{
		PanelKnowledge.remove('queryKnowLedgeType');
		PanelKnowledge.remove('KnowLedgeListGrid');
		PanelKnowledge.add(queryKnowLedgeType);
		PanelKnowledge.add(KnowLedgeListGrid);
		PanelKnowledge.doLayout();
		//		}

	},
	//֪ʶ����ѡ��
	knowledgeType : function() {
		var knowledgeTypeStore = new Ext.data.JsonStore({
			id : 'knowledgeTypeStore',
			url : webContext + '/knowledgeAction_findKnowledgeTypes.action',
			fields : ['id', 'name'],
			totalProperty : 'rowCount',
			root : 'data'
		});
		var knowledgeTypeCombo = new Ext.form.ComboBox({
			id : 'knowledgeTypeCombo',
			width : 200,
			editable : false,
			displayField : 'name',
			valueField : 'id',
			allowBlank:false,
			store : knowledgeTypeStore,
			resizable : true,
			triggerAction : 'all',
			emptyText : '�������',
			selectOnFocus : true,
			pageSize : 10,
			listeners : {
				'select' : function(combo, record, index) {
					this.KnowledgeTypeId = record.get("id");
					var conn = Ext.lib.Ajax.getConnectionObject().conn;
					conn.open("POST",webContext
											+ '/knowledgeAction_getKnowledgeTypeData.action?KnowledgeTypeId='
											+ this.KnowledgeTypeId, false);
					conn.send(null);
					var result = Ext.decode(conn.responseText);
					this.pagePanelName = result.pagePanelName;
					this.pageListName = result.pageListName;
					this.MainTableName = result.MainTableName;
					this.knowName = result.knowName;
					this.className = result.className;
					this.querPanelName = result.queryPanelName
					if (this.MainTableName == "Knowledge") {
						window.location = webContext
								+ "/user/knowledge/knowledgeManagerPage.jsp";
					} else {
						Ext.getCmp('knowledgePage').changeKnowledgePanel(
								this.className, this.pageListName,
								this.MainTableName, this.KnowledgeTypeId,
								this.querPanelName);
					}

				}
			},
			scope : this
		});
		this.knowpanel = new Ext.form.FormPanel({
			id : 'knowpanel',
			layout : 'table',
			autoScroll : true,
			height : 'auto',
			width : 720,
			frame : true,
			collapsible : true,
			defaults : {
				bodyStyle : 'padding:4px'
			},
			layoutConfig : {
				columns : 2
			},
			items : [{
				html : "֪ʶ����",
				cls : 'common-text',
				style : 'width:150;text-align:right'
			}, knowledgeTypeCombo
//			{html: "<font color='black' face='����_GB2312'>��Ϊ������׼ȷ��ѯ������Ҫ��֪ʶ������ѡ��֪ʶ���ͣ�</font>",cls: 'common-text',style:'width:330;text-align:center'},
//			{html: "<font color='black' face='����_GB2312'>Ĭ��Ϊ�����������֪ʶ��</font>",cls: 'common-text',style:'width:180;text-align:center'}
			
			]
		});

		return this.knowpanel;
	},
	//��������ѯ
	KnowledgeTypeDateList : function() {
		var clazz = "com.zsgj.itil.knowledge.entity.Knowledge";
		var da = new DataAction();
		var data1 = da
				.getPanelElementsForQuery("KnowLedgeSolutionQuery_pagepanel");
		var data = da.split(data1);
		//------------------------------------

		var serviceItemTypebySu = new Ext.form.ComboBox({
			name : "serviceItemTypebySu",
			id : 'serviceItemTypebySu',
			fieldLabel : "����������",
			width : 180,
			invalidText : '��Ϣ����',
			hiddenName : 'serviceItemType',
			displayField : 'name',
			// allowBlank : false,
			valueField : 'id',
			lazyRender : true,
			//typeAhead : true,
			//typeAheadDelay : 10,
			minChars : 50,
			resizable : true,
			triggerAction : 'all',
			// emptyText : '��ѡ��...',
			selectOnFocus : true,
			store : new Ext.data.JsonStore({
				url : webContext + '/eventAction_findscidTypeBySu.action',
				fields : ['id', 'name'],
				totalProperty : 'rowCount',
				root : 'data',
				id : 'id'
			}),
			pageSize : 10,
			listeners : {
				'beforequery' : function(queryEvent) {
					Ext.getCmp('serviceItemBySu').clearValue();
					Ext.getCmp('problemTypebySu').clearValue();
					var param = queryEvent.combo.getRawValue();
					var val = queryEvent.combo.getValue();
					if (queryEvent.query == '') {
						param = '';
					}
					this.store.load({
						params : {
							"name" : param,
							start : 0
						}
					});
					return true;
				}
			}

		});
		var serviceItemBySu = new Ext.form.ComboBox({
			name : "serviceItemBySu",
			id : 'serviceItemBySu',
			fieldLabel : "������",
			width : 180,
			hiddenName : 'serviceItem',
			displayField : 'name',
			valueField : 'id',
			lazyRender : true,
			// allowBlank : false,
			//typeAhead : true,
			//typeAheadDelay : 10,
			minChars : 50,
			resizable : true,
			triggerAction : 'all',
			// emptyText : '��ѡ��...',
			selectOnFocus : true,
			store : new Ext.data.JsonStore({
				url : webContext
						+ '/eventAction_findScidIdBySu.action?store=store',
				fields : ['id', 'name'],
				totalProperty : 'rowCount',
				root : 'data',
				id : 'id',
				listeners : {
					beforeload : function(store, opt) {
						Ext.getCmp('serviceItemTypebySu').defaultParam = Ext
								.getCmp('serviceItemTypebySu').getValue();
						opt.params['id'] = Ext.getCmp('serviceItemTypebySu').defaultParam;

					}
				}
			}),
			pageSize : 10,
			listeners : {
				'beforequery' : function(queryEvent) {
					Ext.getCmp('problemTypebySu').clearValue();
					var discValue = Ext.getCmp('serviceItemTypebySu')
							.getValue();
					// if (discValue == "" || discValue == null) {
					// Ext.MessageBox.alert("��ʾ", "����ѡ�����");
					// return false;
					// }
					var param = queryEvent.combo.getRawValue();
					var val = queryEvent.combo.getValue();
					if (queryEvent.query == '') {
						param = '';
					}
					this.store.load({
						params : {
							"name" : param,
							id : discValue,
							start : 0
						}
					});
					return true;
				}
			}

		});
		var problemTypebySu = new Ext.form.ComboBox({
			name : "problemTypebySu",
			id : 'problemTypebySu',
			fieldLabel : "��������",
			width : 180,
			hiddenName : 'knowProblemType',
			displayField : 'knowProblemType',
			valueField : 'id',
			lazyRender : true,
			// allowBlank : false,
			//typeAhead : true,
			//typeAheadDelay : 10,
			minChars : 50,
			resizable : true,
			triggerAction : 'all',
			// emptyText : '��ѡ��...',
			selectOnFocus : true,
			store : new Ext.data.JsonStore({
				url : webContext
						+ '/eventAction_findProblemTypeBySu.action?store=store',
				fields : ['id', 'knowProblemType'],
				totalProperty : 'rowCount',
				root : 'data',
				id : 'id',
				listeners : {
					beforeload : function(store, opt) {
						Ext.getCmp('serviceItemBySu').defaultParam = Ext
								.getCmp('serviceItemBySu').getValue();
						opt.params['id'] = Ext.getCmp('serviceItemBySu').defaultParam;

					}
				}
			}),
			pageSize : 10,
			listeners : {
				'beforequery' : function(queryEvent) {

					// if(!Ext.getCmp('serviceItemBySu').isValid()){
					// Ext.getCmp('serviceItemBySu').clearValue();
					// }
					var discValue = Ext.getCmp('serviceItemBySu').getValue();
					// if (discValue == "" || discValue == null) {
					// Ext.MessageBox.alert("��ʾ", "����ѡ�������");
					// return false;
					// }
					var param = queryEvent.combo.getRawValue();
					var val = queryEvent.combo.getValue();
					if (queryEvent.query == '') {
						param = '';
					}
					this.store.load({
						params : {
							"knowProblemType" : param,
							id : discValue,
							start : 0
						}
					});
					return true;
				}
			}

		});
		//����������ѯ����++++++++
		var summary = new Ext.form.TextField({
			name : "summary",
			fieldLabel : "��������",
			id : 'summary',
			//allowBlank : false,
			width : 180
		});

		//------------------------------------

		this.queryKnowLedgeType = new Ext.form.FormPanel({
			id : 'queryKnowLedgeType',
			layout : 'table',
			height : 'auto',
			width : 702,
			frame : true,
			collapsible : true,
			defaults : {
				bodyStyle : 'padding:4px'
			},
			layoutConfig : {
				columns : 4
			},
			title : '�����������',
			//			items : data
			items : [{
				html : "����������:",
				cls : 'common-text',
				width : 80,
				style : 'width:150;text-align:right'
			}, serviceItemTypebySu, {
				html : "������:",
				cls : 'common-text',
				width : 80,
				style : 'width:150;text-align:right'
			}, serviceItemBySu, {
				html : "��������:",
				cls : 'common-text',
				width : 80,
				style : 'width:150;text-align:right'
			}, problemTypebySu, {
				html : "��������:",
				cls : 'common-text',
				width : 80,
				style : 'width:150;text-align:right'
			}, summary]
		});
		var DataHead = da
				.getListPanelElementsForHead("KnowLedgeSolutionList_pagepanel");
		this.modelTableName = "Knowledge";
		var sm = new Ext.grid.CheckboxSelectionModel();
		var columns = new Array();
		var fields = new Array();
		columns[0] = sm;
		for (var i = 0; i < DataHead.length; i++) {
			var headItem = DataHead[i];
			var title = headItem.header;
			var alignStyle = 'left';
			var propertyName = headItem.dataIndex;
			var isHiddenColumn = false;
			var isHidden = headItem.isHidden;
			if (isHidden == 'true') {
				isHiddenColumn = true;
			}
			var columnItem = {
				header : title,
				dataIndex : propertyName,
				sortable : true,
				hidden : isHiddenColumn,
				align : alignStyle
			}
			columns[i + 1] = columnItem;
			fields[i] = propertyName;
		}
		this.storeMapping = fields;
		this.cm = new Ext.grid.ColumnModel(columns);
		this.store = da.getPanelJsonStore("KnowLedgeSolutionList_pagepanel",
				DataHead);
		this.store.paramNames.sort = "orderBy";
		this.store.paramNames.dir = "orderType";
		this.cm.defaultSortable = true;
		var viewConfig = Ext.apply({
			forceFit : true
		}, this.gridViewConfig);
		this.pageBar = new Ext.PagingToolbar({
			pageSize : 10,
			store : this.store,
			displayInfo : true,
			displayMsg : '��ǰ��ʾ {0}-{1}����¼ /��{2}����¼',
			emptyMsg : "����ʾ����"
		});
//		this.formValue = '';
//		this.pageBar.formValue = this.formValue;
		this.KnowLedgeListGrid = new Ext.grid.GridPanel({
			id : 'KnowLedgeListGrid',
			store : this.store,
			cm : this.cm,
			sm : sm,
			trackMouseOver : false,
			loadMask : true,
			width : 702,
			height : 300,
			tbar : [
					'   ',
					{
						text : '�½�',
						pressed : true,
						handler : function() {
							var conn = Ext.lib.Ajax.getConnectionObject().conn;
							conn
									.open(
											"POST",
											webContext
													+ '/knowledgeAction_queryKnowledgetId.action?panel=KnowLedgeSolution_pagepanel',
											false);
							conn.send(null);
							var result = Ext.decode(conn.responseText);
							var knowledgeId = result.knowledgeId;
							window.location = webContext
									+ "/user/knowledge/createKnowledgeManager.jsp?panel="
									+ 'KnowLedgeSolution_pagepanel'
									+ "&knowLedgeTypeId=" + knowledgeId;
						},
						scope : this,
						iconCls : 'add'
					}, '   ', {
						text : '��ѯ',
						pressed : true,
						handler : this.searchForm,
						scope : this,
						iconCls : 'search'
					}, '   ', {
						text : '�޸�',
						pressed : true,
						handler : this.dealfile,
						scope : this,
						iconCls : 'edit'
					}, ' ', {
						text : '���',
						pressed : true,
						handler : function() {
							this.queryKnowLedgeType.form.reset();
						},
						scope : this,
						iconCls : 'reset'
					},
					'&nbsp;&nbsp;<font color="black" face="����_GB2312">��˫����鿴��ϸ��Ϣ��</font>'],
			bbar : this.pageBar
		});
		var param = {
			'mehtodCall' : 'query',
			'status' : 0,
			'start' : 0
		};
//		this.pageBar.formValue = param;
		this.store.on('beforeload', function(a) {   
					      Ext.apply(a.baseParams,param);   
					});

		this.store.removeAll();
		this.store.load({
			params : param
		});
		this.PanelKnowledgeData = new Ext.Panel({
			id : "PanelKnowledgeData",
			align : 'center',
			defaults : {
				bodyStyle : 'padding:5px'
			},
			width : 720,
			height : 'auto',
			frame : true,
			layoutConfig : {
				columns : 1
			},
			items : [this.queryKnowLedgeType, this.KnowLedgeListGrid]
		});
		return this.PanelKnowledgeData;
	},
	//��ʼ��
	initComponent : function() {
		this.knowtype = this.knowledgeType();
		this.knowlist = this.KnowledgeTypeDateList();
		this.Panel = new Ext.Panel({
			title :'֪ʶ����<font color="black" style="font-weight:lighter" face="����_GB2312">��</font><font color="red">~~~</font><font color="black" style="font-weight:lighter" face="����_GB2312">Ϊ������,Ϊ������׼ȷ��ѯ������Ҫ��֪ʶ��������д֪ʶ���ͣ�Ĭ��Ϊ�����������֪ʶ��</font>',
			id : "knowledge",
			align : 'center',
			layout : 'table',
			border : false,
			defaults : {
				bodyStyle : 'padding:5px'
			},
			width : 740,
			height : 'auto',
			frame : true,
			layoutConfig : {
				columns : 1
			},
			items : [this.knowtype, this.knowlist]

		});
		this.add(this.Panel);
		Ext.getCmp('KnowLedgeListGrid').on("rowdblclick", this.dealfile, this);

	},
	splitOwn : function(data) {
		var labellen = 85;
		var itemlen = 200;
		var throulen = 20;
		if (Ext.isIE) {
			throulen = 470;
		} else {
			throulen = 540;
		}
		if (data == null || data.length == 0) {
			return null;
		}
		var hid = 0;
		var hidd = new Array();
		var longData = new Array();
		var longitems = 0;
		//alert(this.dataId);         
		for (i = 0; i < data.length; i++) {

			data[i].style = data[i].style == null ? "" : data[i].style;
			if (data[i].xtype == "textarea") {
				data[i].style += "'margin:5 0 5 0;'";
			}
			if (data[i].xtype == "hidden") {
				hidd[hid] = data[i];
				hid++;
				continue;
			}

			//alert(data[i].width+data[i].name);
			if (data[i].width == null || data[i].width == 'null'
					|| data[i].width == "") {
				data[i].style += "width:" + itemlen + "px";
				data[i].width = itemlen;
			} else {
				if (data[i].width == "9999") {//ͨ��  
					// alert("data");
					if ((i - hid + longitems) % 2 == 1) {//���Ҳ�����ǰһ����ͨ                                             
						longData[2 * (i - hid) - 1].colspan = 3;
					} else {//��ռһ��
						longitems++;
					}
					data[i].colspan = 3;//������ͨ                                            
					data[i].width = throulen;
					if (data[i].xtype == "textarea") {
						data[i].height = 150;
					}
					data[i].style += "width:" + throulen + "px;";
				} else {//�������ȣ����� 
					data[i].style += "width:" + itemlen + "px";
					data[i].width = itemlen;
				}
			}
			//alert(data[i].width+data[i].name);
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
	}
});