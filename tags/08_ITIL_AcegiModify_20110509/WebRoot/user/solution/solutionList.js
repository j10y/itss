PagePanel = Ext.extend(Ext.Panel, {
	id : "PageTemplates",
	layout : 'fit',
	height : 'auto',
	align : 'center',
	foredit : true,
	width : 1050,
	frame : true,
	autoScroll : true,
	defaults : {
		bodyStyle : 'padding:4px'
	},
	viewConfig : {// ����Ӧ���
		autoFill : true,
		forceFit : true
	},
	layoutConfig : {
		columns : 4
	},
	getAccessory : function() {
		// �ϴ�����
		alert("dsd");
		this.dialog = new Ext.ux.UploadDialog.Dialog({
			url : webContext + '/solution/uploadfile.do?methodCall=upLoadFile',
			// permitted_extensions : ['out', 'xml'],
			reset_on_hide : false,
			draggable : true,
			width : 400,
			height : 180,
			allow_close_on_upload : false,
			upload_autostart : false
		});
		this.dialog.height = 180;
		this.dialog.width = 450;
		this.dialog.on("uploadsuccess", this.onUploadSuccess, this);
		this.dialog.show('show-button');

	},
	onUploadSuccess : function(dialog, filename, result, record) {
		if (result.success == true) {
			dialog.close();
			this.filestore.load({
				params : {
					'nowtime' : result.TIME,
					'start' : 1
				}
			});
		}
	},

	getUpload : function() {
		var da = new DataAction();
		var clazz = "com.zsgj.itil.event.entity.EventFile";
		// var obj = da.getElementsForHead(clazz);
		var obj = da.getListPanelElementsForHead("solutionFile_pagepanel");
		var sm = new Ext.grid.CheckboxSelectionModel();
		var columns = new Array();
		var fields = new Array();
		columns[0] = sm;
		for (var i = 0; i < obj.length; i++) {
			var headItem = obj[i];
			var title = headItem.header;
			var alignStyle = 'left';
			var propertyName = headItem.dataIndex;
			var isHidden = false;
			var hidden = headItem.hidden;
			if (hidden == 'true') {
				isHidden = true;
			} else {
				isHidden = false;
			}
			if (propertyName == 'KnowledgeFile$id'
					|| propertyName == 'KnowledgeFile$nowtime') {
				isHidden = true;
			}
			var columnItem = {
				header : title,
				dataIndex : propertyName,
				sortable : true,
				hidden : isHidden,
				align : alignStyle
					// editor : new Ext.form.TextField()
			};
			columns[i + 1] = columnItem;
			fields[i] = propertyName;
		}
		this.storeMapping = fields;
		this.cm = new Ext.grid.ColumnModel(columns);
		this.filestore = da.getPanelJsonStore("solutionFile_pagepanel", obj);
		// this.filestore = da.getJsonStore(clazz);
		this.cm.defaultSortable = true;
		var viewConfig = Ext.apply({
			forceFit : true
		}, this.gridViewConfig);
		this.formValue = '';
		this.fileGrid = new Ext.grid.EditorGridPanel({
			store : this.filestore,
			cm : this.cm,
			sm : sm,
			id : 'fileGrid',
			// title : '����',
			trackMouseOver : false,
			loadMask : true,
			clicksToEdit : 2,
			height : 200,
			width : 800,
			frame : true,
			tbar : [{
				xtype : 'button',
				style : 'margin:4px 10px 4px 0',
				text : '���Ӹ���',
				pressed : true,
				scope : this,
				iconCls : 'add',
				handler : this.getAccessory

			}, {	// ��ʼ
				xtype : 'button',
				style : 'margin:4px 10px 4px 0',
				text : 'ɾ��',
				pressed : true,
				iconCls : 'remove',
				scope : this,
				handler : function() {

					var record = this.fileGrid.getSelectionModel()
							.getSelected();
					var records = this.fileGrid.getSelectionModel()
							.getSelections();
					if (!record) {
						Ext.Msg.alert("��ʾ", "����ѡ��Ҫɾ������!");
						return;
					}
					if (records.length == 0) {
						Ext.MessageBox.alert('����', '����ѡ��һ����Ϣ������ɾ��!');
						return;
					}
					var id = new Array();
					var da = new DataAction();
					var firm = Ext.Msg.confirm('��ʾ��', '��ȷ��Ҫ����ɾ��������?', function(
							button, text, select) {
						if (select == 'yes,') {
							for (var i = 0; i < records.length; i++) {
								id[i] = records[i].get("KnowledgeFile$id");
								Ext.Ajax.request({
									url : webContext
											+ '/eventAction_deleteUpLoadFile.action',
									params : {
										removedFileIds : id[i]
									},
									timeout : 100000,
									success : function(response, options) {
										// Ext.MessageBox.alert("ɾ����ʾ", "ɾ���ɹ�");
										Ext.Ajax.request({
											url : webContext
													+ '/solution/uploadfile.do?methodCall=upLoadFile&sign=true',
											timeout : 100000,
											success : function(response,
													options) {
												var time = eval('('
														+ response.responseText
														+ ')').TIME;
												Ext.getCmp('fileGrid').store
														.load({
															params : {
																'nowtime' : time,
																'start' : 1
															}
														})
											},
											scope : this
										}, this);

									},
									scope : this
								}, this);
							}
						}
					}, this);
				}
			}]
		})

		return this.fileGrid;

	},
	getKnowledgePanel : function() {

		this.sort = new Ext.form.ComboBox({
			name : "sort",
			id : 'sort',
			fieldLabel : "���������",
			width : 200,
			invalidText : '��Ϣ����',
			hiddenName : 'Event$scidType',
			displayField : 'name',
			allowBlank : false,
			valueField : 'id',
			lazyRender : true,
			typeAhead : 'true',
			minChars : 50,
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
					Ext.getCmp('childsort').clearValue();
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
		this.childsort = new Ext.form.ComboBox({
			name : "childsort",
			id : 'childsort',
			fieldLabel : "������",
			width : 200,
			hiddenName : 'Event$scidData',
			displayField : 'name',
			valueField : 'id',
			lazyRender : true,
			allowBlank : false,
			typeAhead : 'true',
			minChars : 50,
			triggerAction : 'all',
			// emptyText : '��ѡ��...',
			selectOnFocus : true,
			store : new Ext.data.JsonStore({
				url : webContext
						+ '/eventAction_findScidIdBySu.action?store=store',
				fields : ['id', 'name'],
				totalProperty : 'rowCount',
				root : 'data',
				id : 'id'
			}),
			pageSize : 10,
			listeners : {
				'beforequery' : function(queryEvent) {
					var discValue = Ext.getCmp('sort').getValue();
					if (discValue == "" || discValue == null) {
						Ext.MessageBox.alert("��ʾ", "����ѡ�����");
						return false;
					}
					// alert(discValue);
					Ext.Ajax.request({
						url : webContext
								+ '/eventAction_findScidIdBySu.action?id='
								+ discValue
					});
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
		this.UpFile = this.getUpload();
		var da = new DataAction();
		var clazz = "com.zsgj.itil.knowledge.entity.Knowledge";
		// var data = da.getElementsForLook(clazz, 1);
		// var data = da.getElementsForAdd(clazz);

		var data = da.getPanelElementsForAdd("solutionpage");
		// ���¸�дһ��split

		var biddata = da.split(data);
		this.mybuttons = this.getButtons();
		this.buttons = {
			layout : 'table',
			height : 'auto',
			width : 800,
			 style : 'margin:4px 6px 4px 300px',
			 colspan: 4,
			align : 'center',
			 defaults : {
			 bodyStyle : 'padding:4px'
			 },
			 layoutConfig: {columns: 4},
			items : this.mybuttons
		}
		var cpanel = new Ext.form.FormPanel({
			id : 'cataPanel',
			layout : 'table',
			height : 180,
			width : 1190,
			frame : true,
			autoScroll : true,
			collapsible : true,
			defaults : {
				bodyStyle : 'padding:16px'
			},
			layoutConfig : {
				columns : 1
			},
			title : "֪ʶ������Ϣ��",
			items : [{
				xtype : 'fieldset',
				title : '��������ࣨ<font color=red>�������</font>��',
				height : 10,
				width : 1000,
				id : 'base',
				// border:false,
				layout : 'table',
				layoutConfig : {
					columns : 4
				},
				defaults : {
					bodyStyle : 'padding:16px'
				},
				items : [{
					html : "����������:",
					cls : 'common-text',
					width : 135,
					style : 'width:150;text-align:right'
				},

				this.sort, {
					html : "������:",
					cls : 'common-text',
					width : 135,
					style : 'width:150;text-align:right'
				}, this.childsort]
			}, {
				xtype : 'fieldset',
				title : '������Ϣ',
				height : 220,
				// border:false,
				width : 1000,
				id : "basemessage",
				layoutConfig : {
					columns : 4
				},
				defaults : {
					bodyStyle : 'padding:16px'
				},
				layout : 'table',
				items : biddata
			}, {
				xtype : 'fieldset',
				title : '������Ϣ��<font color=red>��ѡ��</font>��',
				height : 220,
				width : 1000,
				items : this.UpFile
			}, this.buttons]
		});
		return cpanel;

	},
	/*
	 * �õ���ť
	 */
	getButtons : function() {
		return [{
			xtype : 'button',
			style : 'margin:4px 10px 4px 0',
			text : '�ύ',
			scope : this,
			id : 'postButton',
			handler : function() {

				if (!Ext.getCmp('sort').isValid()) {
					Ext.getCmp('sort').clearValue();
					Ext.MessageBox.alert("<font color=red>��ע��</font>",
							"<font color=red>��������������,�����޷��ύ</font>");
					return;
				}
				if (!Ext.getCmp('childsort').isValid()) {
					Ext.getCmp('childsort').clearValue();
					Ext.MessageBox.alert("<font color=red>��ע��</font>",
							"<font color=red>������Ϊ������,�����޷��ύ</font>");
					return;
				}
				if (Ext.getCmp("cataPanel").getForm().isValid()) {
					var panelparam = Ext.encode(Ext.getCmp("cataPanel").form
							.getValues(false));
                    panelparam=unicode(panelparam);                                                  
					var gP = Ext.getCmp("fileGrid").getStore().getRange(0,
							Ext.getCmp("fileGrid").getStore().getCount());
					var gParam = "";
					for (i = 0; i < gP.length; i++) {
						gParam += Ext.encode(gP[i].data) + ";";
					}
					gParam = gParam.slice(0, gParam.length - 1);
                     gParam=unicode(gParam);                               
					// alert(gParam);
					Ext.Ajax.request({
						url : webContext + '/solution/uploadfile.do?methodCall=saveSolution',
						params : {
							panelparam : panelparam,
                            gParam:gParam                                          
						},
						success : function(response, options) {
							Ext.MessageBox.alert("�ύ��ʾ", "�ύ�ɹ�");
                           alert(eval('(' + response.responseText + ')').knowledgeId);                                      
						},
						failure : function(response, options) {
							Ext.MessageBox.alert("�ύʧ��");
						}
					}, this);

				} else {
					Ext.MessageBox.alert("<font color=red>��ע��</font>",
							"<font color=red>�����ߵ��ı�Ϊ������,�����޷��ύ</font>");
				}
			}
		}, {
			xtype : 'button',
			style : 'margin:4px 10px 4px 0',
			handler : function() {
				history.back();
			},
			text : '����'
		}]
	},

	items : this.items,
	/*
	 * clientvalidation ��ʼ��
	 */
	initComponent : function() {
		this.query = 0;
		var items = new Array();
		this.knowledgePane = this.getKnowledgePanel();
		items.push(this.knowledgePane);
		this.items = items;
		PagePanel.superclass.initComponent.call(this);
	}
})
