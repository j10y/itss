/**
 * Portal ����
 */
com.faceye.portal.Portal = {
	init : function() {
		Ext.QuickTips.init();
		Ext.state.Manager.setProvider(new Ext.state.CookieProvider());
		Ext.form.Field.prototype.msgTarget = 'side';
		/**
		 * portal�ṹ����
		 */
		var tabs = this.buildPortalContainer();
		var portalContainerStore = new Ext.data.JsonStore({
			baseParams : {
				user : userID  
			},
			url : BP + 'portalContainerAction.do?method=getPortalContainer',
			root : 'rows',
			fields : ['id', 'name']
		});
		portalContainerStore.load({
			callback : function(r, options, success) {
				var portalContainerId = r[0].data['id'];
				var portalStore = new Ext.data.JsonStore({
					url : BP + 'portalAction.do?method=getPortals',
					root : 'root',
					fields : ['id', 'name']
				});
				portalStore.load({
					params : {
						portalContainerId : portalContainerId
					},
					callback : function(portalRecords, portalOptions,
							portalSuccess) {
						for (var i = 0; i < portalRecords.length; i++) {
							var portalId = portalRecords[i].data['id'];
							var portalName = portalRecords[i].data['name'];
							com.faceye.portal.BuildPortalPanel.init(portalId,
									portalName);
						}
					}
				})
			}
		});
		// var viewport = this.buildViewport(tabs);
		// viewport.render(Ext.getDom("center"));
		var viewport = tabs;
		
		porletUtil.reload();
		return viewport;
	},

	/**
	 * �������
	 */
	buildViewport : function(tabs) {
		var viewport = Ext.getCmp('portal-container');
		if (tabs) {
			tabs.destroy();
		}
		// var bottom = com.faceye.ui.BottomLayout.init();
		// var header = com.faceye.ui.HeaderPanel.init();
		// var log = com.faceye.ui.LogPanel.init();
		viewport = new Ext.Panel({
			id : 'portal-container',
			title: '�ҵĹ���̨',
			border : false,
			region : "center",
			items : [tabs]
		});
		return viewport
	},

	/**
	 * ����portals���� ��Ҫ�Ǹ���portal���
	 */
	buildPortalContainer : function() {
		var tabs = Ext.getCmp('portals-container');
		// ����±�ǩҳ�İ�Ť����ʱ���ε�������Ժ�������Ŀ��Ҫ�����ṩʹ�á�
		var addNewTablePanelButton = new Ext.Button({
			id : 'default-tabs-tool-bar-add',
			text : '����±�ǩҳ',
			iconCls : 'add',
			handler : buildDefaultUserDefinitionPortal
		});
		
		if (!tabs) {
			tabs = new Ext.TabPanel({
				id : 'portals-container',
				region : "center",
				deferredRender : false,
				enableTabScroll : true,
				activeTab : 0,
				autoDestroy : true,
				collapseFirst : true,
				animCollapse : true,
				//tbar:[addNewTablePanelButton]
				//style : 'padding:0px 0px 16px 0px',
				plugins : new Ext.ux.TabCloseMenu()
			});
		}
		return tabs;
	}
};

/**
 * ѡ�а�ʽ��Ĵ���. �������ΪcolumnStyleName.
 */
function onCheckColumnStyle(portalColumnTemplateId) {
	var tabs = Ext.getCmp('portals-container');
	var activeTab = tabs.getActiveTab();
	var win = Ext.getCmp('default-toolbar-column-style-select-window');
	var portal = activeTab.items.itemAt(0);
	Ext.Ajax.request({
		url : BP + 'portalAction.do?method=changePortalColumnTempate',
		params : {
			portalId : portal.id,
			portalColumnTemplateId : portalColumnTemplateId
		},
		success : function() {
			activeTab.remove(portal);
			activeTab.fireEvent('activate', this);
			Ext.Msg.alert('��ʽ', '��ʽ�ı�ɹ���');
			// win.hide();
		},
		failure : function() {
			Ext.Msg.alert('��ʽ', '��ʽ�ı�ʧ�ܣ�');

		}
	});
};

/**
 * �����'����±�ǩҳ'��Ťʱ,���ñ�����. ����µı�ǩҳ
 */
function buildDefaultUserDefinitionPortal() {
	// var portainsContainer = Ext.getCmp('portals-container');
	Ext.Ajax.request({
		url : BP + 'portalContainerAction.do?method=saveUserDefinePortal',
		success : function() {
			var store = new Ext.data.JsonStore({
				url : BP + 'portalAction.do?method=getLastPortalByUserId',
				root : 'rows',
				fields : ['id', 'name']
			});
			store.load({
				callback : function(r, options, success) {
					var portalId = r[0].data['id'];
					var portalName = r[0].data['name'];
					com.faceye.portal.BuildPortalPanel.init(portalId,
							portalName);
					var panel = Ext.getCmp(portalId + '_p');
					var tabs = Ext.getCmp('portals-container');
					tabs.activate(panel);
				}
			})

		}
	});
};

/**
 * ����װ�� portal��panel
 */
com.faceye.portal.BuildPortalPanel = {
	init : function(portalId, portalName) {
		var homePanelId = portalId;
				
		var tabs = Ext.getCmp('portals-container');
		
		//�Ѱ�ť�ӵ�tools�� by wanghe 2009-12-08
		 var toolsButton = new Ext.SplitButton({
		   	text: '���湤��',
//		   	iconCls: 'default-tabs-tool-bar-tools',
		   	menu: new Ext.menu.Menu({
		        items: [
			        {
			        	text: 'ˢ������', 
			        	iconCls: 'default-tabs-tool-bar-refresh',
			        	handler: function(){
			        		//������ҳΪ�����
							var activeTabId = homePanelId+"_p";
							tabs.setActiveTab(activeTabId);
							var activeTab = tabs.getActiveTab();
							var portal = activeTab.items.itemAt(0);
							activeTab.remove(portal);
							activeTab.fireEvent('activate', this);
			        	}
			        },
			        {
			       		text : 'ѡ�����湤��',
						iconCls : 'default-tabs-tool-bar-add',
			        	handler: function(){
			        	//������ҳΪ�����
							var activeTabId = homePanelId+"_p";
							tabs.setActiveTab(activeTabId);
							var activeTab = tabs.getActiveTab();
							var portalId =  activeTab.id.substr(0,activeTab.id.indexOf('_'));// activeTab.items.itemAt(0).id;
							updateTab('addPortalet','ѡ�����湤��',BP+ "portletAction.do?method=toPortletSelect****portalId="+ portalId);
			        	
			        	}
			        },
			        {
			       		text : 'ҳ������',
						iconCls : 'default-tabs-tool-bar-edit',
			        	handler: function(){
							    var win = Ext.getCmp('default-toolbar-column-style-select-window');
								if (win) {
									win.destroy();
								}
								var templateStore = new Ext.data.JsonStore({
									url : BP
											+ 'portalColumnTemplateAction.do?method=getPortalColummnTemplates',
									root : 'root',
									fields : ['id', 'name', 'imageSrc', 'columnScale']
								});
								templateStore.load();
								var tpl = new Ext.XTemplate(
										'<tpl for=".">',
										'<div class="thumb-wrap" id="{id}">',
										'<div class="thumb"><img src="{imageSrc}" title="{columnScale}" onclick="onCheckColumnStyle(\'{id}\')"></img></div>',
										'<span class="x-editable">{columnScale}</span></div>',
										'</tpl>', '<div class="x-clear"></div>');
				
								var dataView = new Ext.DataView({
									store : templateStore,
									tpl : tpl,
									border : false,
									autoHeight : true,
									title : '��ʽ����',
									autoWidth : true,
									multiSelect : true,
									overClass : 'x-view-over',
									itemSelector : 'div.thumb-wrap',
									emptyText : 'û�п�����ʾ�İ�ʽ',
									prepareData : function(data) {
										data.imageSrc = BP + data.imageSrc;
										return data;
									}
								});
								/**
								 * �༭��ǩҳ���ֵ� form
								 */
								var editLabelNameForm = new Ext.FormPanel({
									layout : 'form',
									labelWidth : 45,
									url : '#',
									frame : false,
									hideBorders : false,
									border : false,
									defaultType : 'textfield',
									labelAlign : 'right',
									monitorValid : true,
									bodyStyle : '{padding:5px 0 5px 0}',
									items : [{
										fieldLabel : '<font color="red">*</font>����',
										name : 'name',
										width : 200,
										allowBlank : false,
										maxLength : 20,
										maxLengthText : '���Ƴ��Ȳ��ܳ���20����Ч�ַ�!',
										msgTarget : 'under',
										blankText : '���Ʋ���Ϊ��',
										validateOnBlur : true
									}],
									buttons : [{
										id : 'editLabelNameForm-btn',
										text : '�ύ',
										scope : this,
										formBind : true,
										type : 'submit',
										handler : function(btn) {
											var tabs = Ext.getCmp('portals-container');
											var newTitle = editLabelNameForm.items.itemAt(0)
													.getValue();
													
											//������ҳΪ�����
											var activeTabId = homePanelId+"_p";
											tabs.setActiveTab(activeTabId);		
											var activeTab = tabs.getActiveTab();
											
											//alert("activeTab:" + activeTab.id);
											//var portalId = activeTab.items.itemAt(0).getId();
											var portalId = activeTab.id.substr(0,activeTab.id.indexOf('_'));
											
											Ext.Ajax.request({
												url : BP
														+ 'portalAction.do?method=changePortalName',
												method : 'POST',
												params : {
													portalId : portalId,
													portalName : unicode(newTitle)
												},
												success: function(){
													activeTab.setTitle(newTitle);
													Ext.MessageBox.alert('��ʾ','ҳ�������Ѿ��޸ĳɹ���',Ext.MessageBox.INFO);
												}
											});
										}
									}]
								});
								/**
								 * ����ǰ��ǩ�������Զ�����form ��field.
								 */
								editLabelNameForm.on('afterlayout', function() {
									var tabs = Ext.getCmp('portals-container');
									var activeTabId = homePanelId+"_p";
									tabs.setActiveTab(activeTabId);		
									var activeTab = tabs.getActiveTab();
									var currentTitle = activeTab.title;
									editLabelNameForm.items.itemAt(0).setValue(currentTitle);
								});
								/**
								 * �����༭��ǩ���� form ��ʼװ�ظ����ܿ��windwo����
								 */
								win = new Ext.Window({
									id : 'default-toolbar-column-style-select-window',
									layout : 'accordion',
									width : 300,
									buttonAlign : 'center',
									title : '����ҳ������',
									// height : 400,
									autoHeight : true,
									x : this.getEl().getX() -  500,
									y : this.getEl().getY() + 20,
									expandOnShow : true,
									// plain : true,
									modal : true,
									// border:false,
									autoScroll : false,
									shadow : true,
									items : [{
										title : '����',
										border : false,
										layout : 'form',
										autoHeight : true,
										// autoScroll : true,
										items : [editLabelNameForm]
									}, {
										title : '��ʽ',
										layout : 'fit',
										border : false,
										autoScroll : true,
										autoHeight : true,
										items : [dataView]
									}, {
										title : 'ͼ��',
										border : false,
										layout : 'fit'
									}],
									buttons : [{
										text : '�ر�',
										type : 'submit',
										handler : function() {
											Ext.getCmp('default-toolbar-column-style-select-window').hide();
										}
									}]
								});
								win.show();
			        	
			        	}
			        }
		        ]
		   	})
		});
		
		Ext.getCmp('tools_Bywanghe').insertButton(5,toolsButton);
		
		var panel = new Ext.Panel({
			id : portalId + '_p',
			title : portalName,
			iconCls : 'tabs',
			autoScroll : true,
			closable : false,
			//autoHeight : true,
			border : false,
			listeners : {
				activate : function(tab) {
					if (!tab.items) {
						com.faceye.portal.BuildPortal
								.init(portalId, portalName);
						com.faceye.portal.BuildPortalColumn.init(portalId,
								portalName);
					}
				},
				beforedestroy : function(tab) {
					var portalId = tab.id.split('_')[0];
					Ext.Ajax.request({
						url : BP + 'portalAction.do?method=remove',
						params : {
							portalId : portalId
						}
					});

				}
			}
		});
		if (tabs.items.length > 0) {
			panel.closable = true;
		}
		tabs.add(panel);
		if (tabs.items.itemAt(0).id == panel.id) {
			tabs.activate(panel);
		}
		tabs.doLayout();
		
	}
};

/**
 * ����portal
 */
com.faceye.portal.BuildPortal = {
	init : function(portalId, portalName) {
		var portal = new Ext.ux.Portal({
			id : portalId,
			border : false,
			style : 'padding:0 0 0 0'
		});
	}
};
/**
 * ����portalColumn
 */
com.faceye.portal.BuildPortalColumn = {
	init : function(portalId, portalName) {
		var tabs = Ext.getCmp('portals-container');
		var portal = Ext.getCmp(portalId);
		var panel = Ext.getCmp(portalId + '_p');
		var portalColumnStore = new Ext.data.JsonStore({
			url : BP + 'portalColumnAction.do?method=getPortalColumns',
			root : 'root',
			fields : ['id', 'name', 'icon', 'iconCls', 'singleColumnScale',
					'createTime']
		});
		portalColumnStore.load({
			params : {
				portalId : portalId
			},
			callback : function(portalColumnRecords, portalColumnOptions,
					portalColumnSuccess) {
				for (var j = 0; j < portalColumnRecords.length; j++) {
					var portalColumnId = portalColumnRecords[j].data['id'];
					var singleColumnScale = portalColumnRecords[j].data['singleColumnScale']
							/ 100;
					var portalColumn = new Ext.ux.PortalColumn({
						id : portalColumnId,
						columnWidth : singleColumnScale,
						style : 'padding:2px 2px 2px 2px'
					});
					com.faceye.portal.BuildPortlet.init(portalColumnId);
					portal.add(portalColumn);
				}
				panel.add(portal);
				tabs.doLayout();
			}
		});
	}
};
/**
 * ����portlet
 */
com.faceye.portal.BuildPortlet = {
	init : function(portalColumnId) {
		var portalColumn = Ext.getCmp(portalColumnId);
		// var classLoader = new ClassLoader();
		// classLoader
		// .loadFormFileSrc('/faceye/scripts/portal/TraditionPortlet.js');
		// var portlet = com.faceye.portal.portlet.TraditionPortlet.init();
		// portalColumn.add(portlet);
		var portletStore = new Ext.data.JsonStore({
			url : BP + 'portletAction.do?method=getPortletsByPortalColumn',
			root : 'root',
			fields : ['id', 'name', 'source', 'createTime', 'url', 'imageSrc',
					'init','defalutRefersh']
		});
		portletStore.load({
			params : {
				portalColumnId : portalColumnId
			},
			callback : function(r, options, success) {
				for (var i = 0; i < r.length; i++) {
					// classLoader.loadFormFileSrc(r[i].data['url']);
					// var portlet =
					// com.faceye.portal.portlet.SinglePortlet.init();
					// portalColumn.add(portlet);
					var id = r[i].data['id'];
					var name = r[i].data['name'];
					var url = r[i].data['url'];
					var source = r[i].data['source'];
					var portalColum = options.params.portalColumnId;
					
					Ext.Ajax.request({
						url : r[i].data['url'],
						params : {
							id : id,
							name : name,
							source : source,
							portalColum: portalColum
						},
						success : function(response, options) {
							var source = response.responseText;
							var headerDom = document
									.getElementsByTagName('head').item(0);
									
							var jsDom = document.createElement('script');
							jsDom.type = 'text/javascript';
							jsDom.language = 'javascript';
							jsDom.charset='gbk';
							jsDom.defer = true;
							jsDom.text = source;
							headerDom.appendChild(jsDom);
							var portlet = com.faceye.portal.portlet.SinglePortlet
									.init(options.params.id,
											options.params.name,
											options.params.source,
											options.params.portalColum);
							portalColumn.add(portlet);
							portalColumn.doLayout();
						}
					});
				}

			}
		});
	}
}