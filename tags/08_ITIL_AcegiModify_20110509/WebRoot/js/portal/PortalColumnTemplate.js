/**
 * portal style
 */
com.faceye.portal.PortalColumnTemplate = {
	init : function() {
		// create the Data Store
		var store = new Ext.data.Store({
			// load using script tags for cross domain, if the data in on the
			// same domain as
			// this page, an HttpProxy would be better
			proxy : new Ext.data.HttpProxy({
				url : BP + 'portalColumnTemplateAction.do?method=index'
			}),

			// create reader that reads the Topic records
			reader : new Ext.data.JsonReader({
				root : 'root',
				totalProperty : 'total',
				id : 'id',
				fields : ['id', 'name', 'imageSrc', 'columnScale']
			})
		});

		function renderTopic(value, p, record) {
			// 取得当前正在点击的记录的ID
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
					header : "名称",
					dataIndex : 'name',
					renderer : renderTopic
				}, {
					header : "图片路径",
					dataIndex : 'imageSrc'
				}, {
					header : "比例",
					dataIndex : 'columnScale'
				}]);

		// by default columns are sortable
		cm.defaultSortable = true;
		// 定义录入修改弹出 window
		var win;
		// 定义编辑弹出window
		var updateWin;
		var grid = new Ext.grid.GridPanel({
			el : 'topic-grid',
			title : '样式列表',
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
				text : '添加',
				tooltip : 'Add a new row',
				iconCls : 'add',
				handler : function() {
					/**
					 * ----------------Start Add button
					 * config-----------------------
					 */
					if (!win) {
						var innerForm = new Ext.FormPanel({
							labelWidth : 80, // label settings here cascade
												// unless overridden
							url : BP
									+ 'portalColumnTemplateAction.do?method=save',
							frame : true,
							// title: 'Simple Form',
							bodyStyle : 'padding:5px 5px 0',
							width : 370,
							// defaults: {width: 210},
							renderTo : win,
							layout : 'form',
							defaultType : 'textfield',
							items : [{
								name : 'id',
								hidden : true,
								hideLabel : true
							}, {
								fieldLabel : '名字',
								name : 'name',
								width : 300,
								allowBlank : false,
								vtypeText : '名字不能为空'
							}, {
								fieldLabel : '图片路径',
								name : 'imageSrc',
								width : 300,
								allowBlank : false,
								vtypeText : '图片路径不能为空'
							}, {
								fieldLabel : '比例',
								name : 'columnScale',
								width : 300,
								allowBlank : false,
								vtypeText : '比例不能为空'
							}]
						});

						// innerForm.addListener('click',
						// function(){alert('keup');});
						// 当点击 父样式输入框时,激活click事件.

						win = new Ext.Window({
							layout : 'fit',
							// 模式窗口
							modal : true,
							width : 450,
							height : 200,
							closeAction : 'hide',
							plain : true,
							title : '添加新样式',
							buttonAlign : 'center',
							buttons : [{
								text : '确定',
								scope : com.faceye.portal.PortalColumnTemplate,
								type : 'submit',
								disabled : false,
								handler : function() {
									innerForm.getForm().submit({
										method : 'POST',
										params : {
											entityClass : 'com.faceye.components.portal.dao.model.PortalColumnTemplate'
										},
										waitMsg : '正在保存数据...',
										// reset:'/faceye/portalColumnTemplateAction.do?method=generatorJSONList&entityClass=com.faceye.core.componentsupport.dao.model.DataType',
										success : function(form, action) {
											// grid.getView().refresh();
											form.reset();
											Ext.Msg.alert('样式保存', '样式保存成功!');
											this.disabled = false;
											win.hide();
											// 重新加载数据到grid
											store.load();
										},
										failure : function() {
											Ext.Msg.alert('样式保存', '样式保存失败!');
											this.disabled = false;
										}
									});
								}
							}, {
								text : '放弃',
								handler : function() {
									innerForm.getForm().reset();
									win.hide();
								}
							}, {
								text : '重置',
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
			}, {
				text : '编辑',
				tooltip : '编辑选中的记录，一次只可以编辑一条。',
				iconCls : 'option',
				handler : function() {
					var selectionModel = grid.getSelectionModel();
					// 取得共选择了多少条记录。
					var selectedCount = selectionModel.getCount();
					if (selectedCount == 0) {
						Ext.Msg.alert('编辑数据', '您没有选中任何数据，请选择您要编辑的数据！');
						return;
					} else if (selectedCount > 1) {
						Ext.Msg.alert('编辑数据',
								'您只能选择一条数据，不能同时选择多条数据，请选择您要编辑的数据！');
						return;
					} else {
						// 取得被选中的数据
						var record = selectionModel.getSelected();
						// 取得被选中数据的主键
						var id = record.id;

						var updateForm = new Ext.FormPanel({
							labelWidth : 80, // label settings here cascade
												// unless overridden
							url : BP
									+ 'portalColumnTemplateAction.do?method=save',
							frame : true,
							// title: 'Simple Form',
							bodyStyle : 'padding:5px 5px 0',
							width : 450,
							// defaults: {width: 230},
							layout : 'form',
							defaultType : 'textfield',
							reader : new Ext.data.JsonReader({
								root : 'rows',
								// totalProperty: 'total',
								success : true,
								fields : ['id', 'name', 'imageSrc',
										'columnScale']
							}),
							items : [{
								xtype : 'hidden',
								name : 'id'
							},
									// parentIdText,
									{
										fieldLabel : '名字',
										name : 'name',
										allowBlank : false,
										width : 300
									}, {
										fieldLabel : '图片路径',
										name : 'imageSrc',
										width : 300,
										allowBlank : false,
										vtypeText : '图片路径不能为空'
									}, {
										fieldLabel : '比例',
										name : 'columnScale',
										width : 300,
										allowBlank : false,
										vtypeText : '比例不能为空'
									}]
						});

						updateWin = new Ext.Window({
							layout : 'fit',
							// 模式窗口
							modal : true,
							width : 450,
							height : 200,
							closeAction : 'hide',
							plain : true,
							buttonAlign : 'center',
							buttons : [{
								text : '提交',
								scope : com.faceye.portal.PortalColumnTemplate,
								type : 'submit',
								disabled : false,
								handler : function() {
									updateForm.getForm().submit({
										method : 'POST',
										params : {
											entityClass : 'com.faceye.components.portal.dao.model.PortalColumnTemplate'
										},
										waitMsg : '正在保存数据......',
										// reset:'/faceye/portalColumnTemplateAction.do?method=generatorJSONList&entityClass=com.faceye.core.componentsupport.dao.model.DataType',
										success : function(form, action) {
											// grid.getView().refresh();
											Ext.Msg.alert('样式保存', '样式保存成功!');
											form.reset();
											this.disabled = false;
											updateWin.hide();
											// 重新加载数据到grid
											store.load();
										},
										failure : function() {
											Ext.Msg.alert('样式保存', '样式保存失败!');
											this.disabled = false;
										}
									});
								}
							}, {
								text : '关闭',
								handler : function() {
									updateWin.hide();
								}
							}, {
								text : '放弃',
								handler : function() {
									updateForm.getForm().reset();
									updateWin.hide();
								}
							}]
						});
						updateWin.add(updateForm);
						updateForm.getForm().load({
							url : BP
									+ 'portalColumnTemplateAction.do?method=update&entityClass=com.faceye.components.portal.dao.model.PortalColumnTemplate&id='
									+ id,
							waitMsg : '正在加载数据，请稍后...'
						});
						updateWin.show(this);
						// loadStore.load();
						// alert(loadStore.getTotalCount());
						// var loadRecord=loadStore.getAt(0);
						// alert(loadRecord.name)
						// alert(loadRecord.id);

					}
				}
			}, {
				text : '删除',
				tooltip : '删除选中的记录，一次可以删除多条。',
				iconCls : 'remove',
				handler : function() {
					var selectionModel = grid.getSelectionModel();
					// 取得共选择了多少条记录。
					var selectedCount = selectionModel.getCount();
					if (selectedCount == 0) {
						Ext.Msg.alert('删除操作', '您没有选中要删除的数据，请选择您准备删除的数据');
						return;
					} else {
						// 取得要删除的数据的ID
						Ext.Msg.confirm('删除数据', '您确认要删除选中的数据吗?', function(btn,
								text) {
							if (btn == 'yes') {
								var records = selectionModel.getSelections();
								var _ids = '';
								for (var i = 0; i < records.length; i++) {
									_ids += records[i].id;
									_ids += '_';
								}
								// 发送删除数据的请求
								Ext.Ajax.request({
									url : BP
											+ 'portalColumnTemplateAction.do?method=remove',
									failure : function() {
										Ext.Msg.alert('样式删除', '样式删除失败！');
									},
									success : function() {
										Ext.Msg.alert('样式删除', '样式删除成功！');
									},
									params : {
										entityClass : 'com.faceye.components.portal.dao.model.PortalColumnTemplate',
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
 * 数据明细显示
 */
function onClickLink(id) {
	var win;
	if (!win) {
		win = new Ext.Window({
			layout : 'fit',
			width : 450,
			height : 200,
			// 模式窗口
			modal : true,
			closeAction : 'hide',
			title : '样式明细',
			plain : true,
			buttonAlign : 'center',
			buttons : [{
				text : '关闭',
				type : 'submit',
				disabled : false,
				handler : function() {
					win.hide(this);
				}
			}]
		});
		var tpl = new Ext.Template('<p>名称:{name}</p>' + '<p>图片路径{imageSrc}</p>'
						+ '<p>比例{columnScale}</p>');
		var nodes = new Ext.data.JsonStore({
			url : BP + 'portalColumnTemplateAction.do?method=detail',
			root : 'rows',
			fields : ['id', 'name', 'imageScr', 'columnScale']
		});
		// tpl.overwrite(win.body,nodes);
		nodes.load({
			params : {
				id : id,
				entityClass : 'com.faceye.components.portal.dao.model.PortalColumnTemplate'
			},
			callback : function(r, options, success) {
				var record = nodes.getAt(0);
				tpl.overwrite(win.body, record.data);
			}
		});

	}
	win.show(this);
};
