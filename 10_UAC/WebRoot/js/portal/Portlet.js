com.faceye.portal.Portlet = {
	init : function() {
		// create the Data Store
		var store = new Ext.data.Store({
			// load using script tags for cross domain, if the data in on the
			// same domain as
			// this page, an HttpProxy would be better
			proxy : new Ext.data.HttpProxy({
				url : BP + 'portletAction.do?method=index'
			}),

			// create reader that reads the Topic records
			reader : new Ext.data.JsonReader({
				root : 'root',
				totalProperty : 'total',
				id : 'id',
				fields : ['id', 'name']
			})
		});

		function renderTopic(value, p, record) {
			// ȡ�õ�ǰ���ڵ���ļ�¼��ID
			var id = record.data.id;
			return String.format('<a href="#" onclick="onClickLink(\'' + id
					+ '\')">{0}</a>', record.get('name'));
		}

		function renderLast(value, p, r) {
			return String.format('{0}<br/>by {1}', value
					.dateFormat('M j, Y, g:i a'), r.data['lastposter']);
		}

		var cm = new Ext.grid.ColumnModel([
				new Ext.grid.CheckboxSelectionModel(), {
					id : 'id', // id assigned so we can apply custom css (e.g.
								// .x-grid-col-topic b { color:#333 })
					header : 'ID',
					dataIndex : 'id',
					hidden : true
				}, {
					header : "����",
					dataIndex : 'name',
					renderer : renderTopic
				}]);
		//alert('dd');
		// by default columns are sortable
		cm.defaultSortable = true;
		// ����¼���޸ĵ��� window
		var win;
		// ����༭����window
		var updateWin;
		var grid = new Ext.grid.GridPanel({
			el : 'topic-grid',
			title : 'Portlet�б�',
			autoHeight : true,
			bodyStyle : 'width:100%',
			loadMask : true,
			stripeRows : true,
			trackMouseOver : true,
			layoutConfig : {
				autoWidth : true
			},
			store : store,
			cm : cm,
			trackMouseOver : false,
			sm : new Ext.grid.CheckboxSelectionModel(),
			loadMask : true,
			viewConfig : {
				forceFit : true,
				enableRowBody : true,
				showPreview : true
			},
			bbar : com.faceye.ui.util.PaggingToolBar(15, store),
			tbar : [{
				text : '���',
				tooltip : 'Add a new row',
				iconCls : 'default-tabs-tool-bar-add',
				handler : function() {
					/**
					 * ----------------Start Add button
					 * config-----------------------
					 */
					if (!win) {
						var innerForm = new Ext.FormPanel({
							labelWidth : 80, // label settings here cascade
												// unless overridden
							url : BP + 'portletAction.do?method=save',
							frame : true,
							// title: 'Simple Form',
							bodyStyle : 'padding:5px 5px 0',
							width : 580,
							// defaults: {width: 210},
							renderTo : win,
							layout : 'form',
							monitorValid : true,
							defaultType : 'textfield',
							items : [{
								name : 'id',
								hidden : true,
								hideLabel : true
							}, {
								fieldLabel : 'Portlet����',
								name : 'name',
								width : 300,
								allowBlank : false,
								vtypeText : 'Portlet���ֲ���Ϊ��'
							}, {
								fieldLabel : '·��',
								name : 'url',
								width : 300,
								vtypeText : 'Portlet���ֲ���Ϊ��'
							}, {
								fieldLabel : 'Ԥ��ͼ·��',
								name : 'imageSrc',
								width : 300,
								vtypeText : 'Ԥ��ͼ·������Ϊ��'
							}, {
								fieldLabel : '��ʼ������',
								name : 'init',
								width : 300,
								vtypeText : '��ʼ����������Ϊ��'
							}, {
								fieldLabel : '��Դ�ļ�λ��',
								width : 300,
								//height : 330,
								name : 'source'
							}]
						});

						// innerForm.addListener('click',
						// function(){alert('keup');});
						// ����� ��Portlet�����ʱ,����click�¼�.

						win = new Ext.Window({
							layout : 'fit',
							// ģʽ����
							modal : true,
							width : 480,
							// autoHeight:true,
							height : 350,
							closeAction : 'hide',
							plain : true,
							title : '�����Portlet',
							buttonAlign : 'center',
							buttons : [{
								text : 'ȷ��',
								scope : com.faceye.portal.Portlet,
								type : 'submit',
								formBind : true,
								disabled : false,
								handler : function() {
									name = innerForm.form.findField('name').getValue();
									innerForm.form.findField('name').setValue(unicode(name));
									innerForm.getForm().submit({
										method : 'POST',
										params : {
											entityClass : 'com.digitalchina.ibmb2b.protal.entity.Portlet'
										},
										waitMsg : '���ڱ�������...',
										// reset:'/faceye/portletAction.do?method=generatorJSONList&entityClass=com.faceye.core.componentsupport.dao.model.DataType',
										success : function(form, action) {
											// grid.getView().refresh();
											form.reset();
											Ext.Msg.alert('Portlet����',
													'Portlet����ɹ�!');
											this.disabled = false;
											win.hide();
											// ���¼������ݵ�grid
											store.load();
										},
										failure : function() {
											Ext.Msg.alert('Portlet����',
													'Portlet����ʧ��!');
											this.disabled = false;
										}
									});
								}
							}, {
								text : '����',
								handler : function() {
									innerForm.getForm().reset();
									win.hide();
								}
							}, {
								text : '����',
								handler : function() {
									innerForm.getForm().reset();
								}
							}]
						});
						win.add(innerForm);
					}
					win.show(this);

					/**
					 * ---------------End Add button
					 * config------------------------
					 */

				}
			}, "-",{
				text : '�༭',
				tooltip : '�༭ѡ�еļ�¼��һ��ֻ���Ա༭һ����',
				iconCls : 'edit',
				handler : function() {
					var selectionModel = grid.getSelectionModel();
					// ȡ�ù�ѡ���˶�������¼��
					var selectedCount = selectionModel.getCount();
					if (selectedCount == 0) {
						Ext.Msg.alert('�༭����', '��û��ѡ���κ����ݣ���ѡ����Ҫ�༭�����ݣ�');
						return;
					} else if (selectedCount > 1) {
						Ext.Msg.alert('�༭����',
								'��ֻ��ѡ��һ�����ݣ�����ͬʱѡ��������ݣ���ѡ����Ҫ�༭�����ݣ�');
						return;
					} else {
						// ȡ�ñ�ѡ�е�����
						var record = selectionModel.getSelected();
						// ȡ�ñ�ѡ�����ݵ�����
						var id = record.id;
						var updateForm = new Ext.FormPanel({
							labelWidth : 80, // label settings here cascade
												// unless overridden
							url : BP + 'portletAction.do?method=save',
							frame : true,
							// title: 'Simple Form',
							bodyStyle : 'padding:5px 5px 0',
							width : 480,
							height : 350,
							// defaults: {width: 230},
							layout : 'form',
							defaultType : 'textfield',
							reader : new Ext.data.JsonReader({
								root : 'rows',
								// totalProperty: 'total',
								success : true,
								fields : ['id', 'name', 'url', 'imageSrc',
										'source', 'init']
							}),
							items : [{
								xtype : 'hidden',
								name : 'id'
							},
									// parentIdText,
									{
										fieldLabel : 'Portlet����',
										name : 'name',
										allowBlank : false,
										width : 300
									}, {
										fieldLabel : '·��',
										name : 'url',
										width : 300,
										vtypeText : 'Ԥ��ͼ·������Ϊ��'
									}, {
										fieldLabel : 'Ԥ��ͼ·��',
										name : 'imageSrc',
										width : 300,
										vtypeText : 'Ԥ��ͼ·������Ϊ��'
									}, {
										fieldLabel : '��ʼ������',
										name : 'init',
										width : 300,
										vtypeText : '��ʼ����������Ϊ��'
									}, {
										fieldLabel : '��Դ�ļ�λ��',
										width : 300,
										//height : 330,
										name : 'source'
									}]
						});

						updateWin = new Ext.Window({
							layout : 'fit',
							// ģʽ����
							modal : true,
							width : 580,
							height : 450,
							closeAction : 'hide',
							plain : true,
							buttonAlign : 'center',
							buttons : [{
								text : '�ύ',
								scope : com.faceye.portal.Portlet,
								type : 'submit',
								disabled : false,
								handler : function() {
									name = updateForm.form.findField('name').getValue();
									updateForm.form.findField('name').setValue(unicode(name));
									updateForm.getForm().submit({
										method : 'POST',
										params : {
											entityClass : 'com.digitalchina.ibmb2b.protal.entity.Portlet'
										},
										waitMsg : '���ڱ�������......',
										// reset:'/faceye/portletAction.do?method=generatorJSONList&entityClass=com.faceye.core.componentsupport.dao.model.DataType',
										success : function(form, action) {
											// grid.getView().refresh();
											Ext.Msg.alert('Portlet����',
													'Portlet����ɹ�!');
											form.reset();
											this.disabled = false;
											updateWin.hide();
											// ���¼������ݵ�grid
											store.load();
										},
										failure : function() {
											Ext.Msg.alert('Portlet����',
													'Portlet����ʧ��!');
											this.disabled = false;
										}
									});
								}
							}, {
								text : '�ر�',
								handler : function() {
									updateWin.hide();
								}
							}, {
								text : '����',
								handler : function() {
									updateForm.getForm().reset();
									updateWin.hide();
								}
							}]
						});
						updateWin.add(updateForm);
						updateForm.getForm().load({
							url : BP
									+ 'portletAction.do?method=update&entityClass=com.digitalchina.ibmb2b.protal.entity.Portlet&id='
									+ id,
							waitMsg : '���ڼ������ݣ����Ժ�...'
						});
						updateWin.show(this);
						// loadStore.load();
						// alert(loadStore.getTotalCount());
						// var loadRecord=loadStore.getAt(0);
						// alert(loadRecord.name)
						// alert(loadRecord.id);

					}
				}
			}, "-",{
				text : 'ɾ��',
				tooltip : 'ɾ��ѡ�еļ�¼��һ�ο���ɾ��������',
				iconCls : 'default-tabs-tool-bar-remove',
				handler : function() {
					var selectionModel = grid.getSelectionModel();
					// ȡ�ù�ѡ���˶�������¼��
					var selectedCount = selectionModel.getCount();
					if (selectedCount == 0) {
						Ext.Msg.alert('ɾ������', '��û��ѡ��Ҫɾ�������ݣ���ѡ����׼��ɾ��������');
						return;
					} else {
						// ȡ��Ҫɾ�������ݵ�ID
						Ext.Msg.confirm('ɾ������', '��ȷ��Ҫɾ��ѡ�е�������?', function(btn,
								text) {
							if (btn == 'yes') {
								var records = selectionModel.getSelections();
								var _ids = '';
								for (var i = 0; i < records.length; i++) {
									_ids += records[i].id;
									_ids += '_';
								}
								// ����ɾ�����ݵ�����
								Ext.Ajax.request({
									url : BP + 'portletAction.do?method=remove',
									failure : function() {
										Ext.Msg.alert('Portletɾ��',
												'Portletɾ��ʧ�ܣ�');
									},
									success : function() {
										Ext.Msg.alert('Portletɾ��',
												'Portletɾ���ɹ���');
									},
									params : {
										entityClass : 'com.digitalchina.ibmb2b.protal.entity.Portlet',
										ids : _ids
									}
								});
								store.load();

								// alert(nodesStore);
								// alert(nodesStore);

							} else {
								return;
							}
						});
					}

				}
			}]

		});
		grid.render();
		// trigger the data store load
		store.load({
			params : {
				start : 0,
				limit : 15
			}
		});
		function toggleDetails(btn, pressed) {
			var view = grid.getView();
			view.showPreview = pressed;
			view.refresh();
		}
	}
};
/**
 * ������ϸ��ʾ
 */
function onClickLink(id) {
	var win;
	if (!win) {
		win = new Ext.Window({
			layout : 'fit',
			width : 450,
			height : 200,
			// ģʽ����
			modal : true,
			closeAction : 'hide',
			title : 'Portlet��ϸ',
			plain : true,
			buttonAlign : 'center',
			buttons : [{
				text : '�ر�',
				type : 'submit',
				disabled : false,
				handler : function() {
					win.hide(this);
				}
			}]
		});
		var tpl = new Ext.Template('<p>Portlet����:{name}</p><br/>'
						+ '<p>Portlet·��:{url}</p><br/>'
						+ '<p>PortletͼƬλ��:{imageSrc}</p><br/>'
						+ '<p>Portlet��ʼ������:{init}</p><br/>'
						+ '<p>Portlet��Դ�ļ�λ��:{source}</p>');
		var nodes = new Ext.data.JsonStore({
			url : BP + 'portletAction.do?method=detail',
			root : 'rows',
			fields : ['id', 'url', 'imageSrc', 'name', 'init','source']
		});
		// tpl.overwrite(win.body,nodes);
		nodes.load({
			params : {
				id : id,
				entityClass : 'com.digitalchina.ibmb2b.protal.entity.Portlet'
			},
			callback : function(r, options, success) {
				var record = nodes.getAt(0);
				tpl.overwrite(win.body, record.data);
			}
		});

	}
	win.show(this);
};
