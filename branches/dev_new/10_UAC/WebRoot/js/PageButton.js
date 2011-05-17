// ��ť������
ButtonUtil = Ext.extend(Ext.util.Observable, {
	getModelButtonPanel : function(modelName, scope) {
		var buttons = this.getButtonForModel(modelName, scope);
		var buttonPanel = {
			layout : 'table',
			height : 'auto',
			width : 800,
			style : 'margin:4px 6px 4px 300px',
			align : 'center',
			defaults : {
				bodyStyle : 'padding:4px'
			},
			items : buttons
		};
		return buttonPanel;
	},
	getPanelButtonPanel : function(panelName, scope) {
		var buttons = this.getButtonForPanel(panelName, scope);
		var buttonPanel = {
			layout : 'table',
			height : 'auto',
			width : 800,
			style : 'margin:4px 6px 4px 300px',
			align : 'center',
			defaults : {
				bodyStyle : 'padding:4px'
			},
			items : buttons
		};
		return buttonPanel;
	},
	getButtonForProcess : function(serviceItemId, scope){
		var url = webContext+'/serviceItem_getSCIProcessButton.action?serviceItemId=' + serviceItemId;
		var data = this.ajaxGetpage(url);
		var processButtons = this.getProcessButton(data, serviceItemId,scope);
		return processButtons;
	},
	getButtonForModel : function(modelName, scope) {
		var url = webContext
				+ '/extjs/pageView?method=pageModelButton&modelName='
				+ modelName;
		var data = this.ajaxGetpage(url);
		var modelButtons = this.getButtons(data, scope);
		return modelButtons;
	},
	getButtonForPanel : function(panelName, scope) {
		var url = webContext
				+ '/extjs/pageView?method=pagePanelButton&panelName='
				+ panelName;
		var data = this.ajaxGetpage(url);
		var panelButtons = this.getButtons(data, scope);
		return panelButtons;
	},
	getButtons : function(buttonStrs, scope) {
		var barbuttons = new Array();
		for (var i = 0; i < buttonStrs.length; i++) {
			var btnName = buttonStrs[i].btnName;// ��ť������
			var container = buttonStrs[i].container;// ��ť����������ģ����������
			var containerTable = buttonStrs[i].containerTable;// ��ť����������
			var method = buttonStrs[i].method;// ��ť��Ӧ����
			var link = buttonStrs[i].link;// ��ťָ������
			var nextPageModel = buttonStrs[i].nextPageModel;// ��ťָ����һ��pageModel
			var imageUrl = buttonStrs[i].imageUrl;// ��ťͼ��
			// ����Button�����ݲ�ͬ�Ķ������찴ť
			if (method == "addByPage") {
				barbuttons[i] = this.createAddByPage(btnName, link, imageUrl,
						scope);
			} else if (method == "addByWindow") {
				barbuttons[i] = this.createAddByWindow(btnName, link,
						container, imageUrl, nextPageModel, scope);
			} else if (method == "addByColumn") {
				barbuttons[i] = this.createAddByColumn(btnName, link, imageUrl,
						scope);
			} else if (method == "saveForModel") {
				barbuttons[i] = this.createSaveForModel(btnName, link,
						imageUrl, scope);
			} else if (method == "saveForFormPanel") {
				barbuttons[i] = this.createSaveForFormPanel(btnName, link,
						container, imageUrl, scope);
			} else if (method == "saveForGridPanel") {
				barbuttons[i] = this.createSaveForGridPanel(btnName, link,
						container, imageUrl, scope);
			} else if (method == "saveForModelConfigItem") {
				barbuttons[i] = this.createSaveForModelConfigItem(btnName,
						link, imageUrl, scope);
			} else if (method == "removeForModel") {
				barbuttons[i] = this.createRemoveForModel(btnName, link,
						imageUrl, scope);
			} else if (method == "removeByColumn") {
				barbuttons[i] = this.createRemoveByColumn(btnName, link,
						container, containerTable, imageUrl, scope);
			} else if (method == "removeForGrid") {
				barbuttons[i] = this.createRemoveForGrid(btnName, link,
						imageUrl, scope);
			} else if (method == "search") {// ��ѯ
				barbuttons[i] = this.createSearch(btnName, link, imageUrl,
						scope);
			} else if (method == "reset") {
				barbuttons[i] = this
						.createReset(btnName, link, imageUrl, scope);
			} else if (method == "resetPanel") {// ���õ����
				barbuttons[i] = this.createResetForPanel(btnName, container,
						imageUrl, scope);
			} else if (method == "modifyByPage") {// ͨ���ض���ҳ������޸�
				var linkPath = link;
				barbuttons[i] = this.createModifyByPage(btnName, link,
						imageUrl, scope);
				scope.modify = function() {// Ϊҳ��˫���¼�������ֵ
					var record = this.grid.getSelectionModel().getSelected();
					var records = this.grid.getSelectionModel().getSelections();
					if (!record) {
						Ext.Msg.alert("��ʾ", "����ѡ��Ҫ�޸ĵ���!");
						return;
					}
					if (records.length > 1) {
						Ext.Msg.alert("��ʾ", "�޸�ʱֻ��ѡ��һ��!");
						return;
					}
					var modelTableId = this.modelTableName + "$id";
					var dataId = record.get(modelTableId);
					// alert("link:"+webContext+ linkPath + dataId);
					window.location = webContext + linkPath + dataId;
				}
			} else if (method == "viewByPage") {// ͨ���ض���ҳ����в鿴
				var linkPath = link;
				barbuttons[i] = this.createModifyByPage(btnName, link,
						imageUrl, scope);
				scope.modify = function() {// Ϊҳ��˫���¼�������ֵ
					var record = this.grid.getSelectionModel().getSelected();
					var records = this.grid.getSelectionModel().getSelections();
					if (!record) {
						Ext.Msg.alert("��ʾ", "����ѡ��Ҫ�鿴����!");
						return;
					}
					if (records.length > 1) {
						Ext.Msg.alert("��ʾ", "�鿴ʱֻ��ѡ��һ��!");
						return;
					}
					var modelTableId = this.modelTableName + "$id";
					var dataId = record.get(modelTableId);
					// alert("link:"+webContext+ linkPath + dataId);
					window.location = webContext + linkPath + dataId;
				}
			} else if (method == "modifyByWindow") {// ͨ��������������޸�
				barbuttons[i] = this.createModifyByWindow(btnName, link,
						container, containerTable, imageUrl, nextPageModel,
						scope);
				scope.modify = function() {
					alert("ͨ��������������޸���δʵ�֣�");
				}
			} else if (method == "submit") {// �ύ
				barbuttons[i] = this.createSubmit(btnName, link, imageUrl,
						scope);
			} else if (method == "import") {// ����
				barbuttons[i] = this.createImport(btnName, link, imageUrl,
						scope);
			} else if (method == "export") {// ����
				barbuttons[i] = this.createExport(btnName, link, imageUrl,
						scope);
			} else if (method == "upload") {// �ϴ�
				barbuttons[i] = this.createUpLoad(btnName, link, imageUrl,
						scope);
			} else if (method == "download") {// ����
				barbuttons[i] = this.createDownLoad(btnName, link, imageUrl,
						scope);
			} else if (method == "goBack") {// ����
				barbuttons[i] = this.createGoBack(btnName, link, imageUrl,
						scope);
			} else if (method == "workflow") {// ���������������
				barbuttons[i] = this.createWorkFlow(btnName, link, imageUrl,
						scope);
			} else if (method == "yesSubmit") {// ���������������ֻ��ͬ��
				barbuttons[i] = this.createYesSubmit(btnName, link, imageUrl,
						scope);
			} else if (method == "noSubmit") {// ���������������ֻ�ܾܾ�
				barbuttons[i] = this.createNoSubmit(btnName, link, imageUrl,
						scope);
			} else if (method == "addMarkUser") {// ��������������У�Ϊ��ǰ�ڵ�ڵ��ṩ��ǩ��
				barbuttons[i] = this.createAddMarkUser(btnName, link, imageUrl,
						scope);
			} else if (method == "addNextMarkUser") {// ��������������У�Ϊ��ǰ�ڵ�ڵ��ṩ��ǩ��
				barbuttons[i] = this.createAddNextMarkUser(btnName, link, imageUrl,
						scope);
			} else {//
				barbuttons[i] = this.createPageEvent(btnName, method, link,
						imageUrl, scope);
			}
		}
		return barbuttons;
	},
	getProcessButton : function(data, serviceItemId,scope) {
		var barbuttons = new Array();
		for (var i = 0; i < data.length; i++) {
			var btnName = data[i].btnName;// ��ť������
			var processType = data[i].processType;// ��������
			var link = data[i].link;// ��ťָ������
			var spId = data[i].id;
			//alert("��ť����"+btnName+",��������:"+processType+",���ҳ��:"+link);
			// ����Button�����ݲ�ͬ�Ķ������찴ť
			var icon = "";
			if(processType=="0"){
				icon = 'add';
			}else if(processType=="1"){
				icon = 'edit';
			}else if(processType=="2"){
				icon = 'remove';
			}
			barbuttons[i] = this.createProcessButton(btnName, spId, icon);
		}
		return barbuttons;
	},
	createProcessButton : function(btnName, spId, imageUrl){
		var button = new Ext.Button({
			text : btnName,
			pressed : true,
			iconCls : imageUrl,
			handler : function() {
				window.location = webContext + "/requireAction_toProcessEnterPage.action?serviceItemProcessId="+spId;
			}
		});
		return button;
	},
	/**
	 * ͨ���ض������ҳ������� linkָ���ض���ҳ��
	 */
	createAddByPage : function(btnName, link, imageUrl, scope) {
		var button = new Ext.Button({
			text : btnName,
			pressed : true,
			iconCls : imageUrl,
			scope : 'add',
			handler : function() {
				if (link == "")
					alert("δָ�������ת����ҳ�棡");
				window.location = webContext + link;
			}
		});
		return button;
	},
	/*
	 * ͨ���������������
	 * 
	 */
	createAddByWindow : function(btnName, link, container, imageUrl,
			nextPageModel, scope) {
		var button = new Ext.Button({
			text : btnName,
			pressed : true,
			iconCls : 'add',
			scope : scope,
			handler : function() {
				if (link == "")
					alert("δָ�������ת����ҳ�棡");
				this.addWin = new Ext.Window({
					id : "contarinerWindow",
					scope : this,
					title : "���",
					modal : true,
					height : 500,
					width : 800,
					resizable : false,
					draggable : true,
					autoLoad : {
						url : webContext + "/tabFrame.jsp?url=" + webContext
								+ link + "****parentId=" + this.dataId
								+ "****parentTabId=" + container,
						text : "ҳ�������......",
						method : 'post',
						scripts : true,
						scope : this
					},
					viewConfig : {
						autoFill : true,
						forceFit : true
					},
					layout : 'fit',
					items : [{
						html : "���ڼ���ҳ������......"
					}]
				});
				this.addWin.setPagePosition(400, 100);
				this.addWin.show();
				this.addWin.on("deactivate", function() {
					window.location = window.location.href.toString()
							+ '&tabId=' + container;
				}, this);
			}
		});
		return button;
	},
	/*
	 * ͨ���ڱ�������һ�����ݵ���ʽ���,���������ݿ���в���
	 * 
	 */
	createAddByColumn : function(btnName, link, imageUrl, scope) {
		var button = new Ext.Button({
			text : btnName,
			pressed : true,
			iconCls : 'add',
			scope : scope,
			handler : function() {
				var store = this.store;
				if (store.recordType) {
					var rec = new store.recordType({
						newRecord : true
					});
					rec.fields.each(function(f) {
						rec.data[f.name] = f.defaultValue || null;
					});
					rec.commit();
					store.add(rec);
					return rec;
				}
				return false;
			}
		});
		return button;
	},
	/*
	 * ��������ҳ��������Ϣ
	 * 
	 */
	createSaveForModel : function(btnName, link, imageUrl, scope) {
		var button = new Ext.Button({
			text : btnName,
			pressed : true,
			iconCls : 'save',
			scope : scope,
			handler : function() {
				if (link == "")
					alert("δָ�������ת����ҳ�棡");
				var formParam = "";
				var gridParam = "";
				var param = '{';
				if (this.pa.length != "0") {
					for (var i = 0; i < this.pa.length; i++) {
						var fP = Ext.encode(this.pa[i].form.getValues(false));
						//********
						if (!this.pa[i].form.isValid()) {
								Ext.Msg.alert('��ʾ', '����ɫ�����ߵ��������ȷ��д!');
								return;
							}
						//********
						formParam += '\"' + this.formname[i] + '\"' + ':[' + fP
								+ '],';
					}
					param += formParam;
				}
				if (this.gd.length != "0") {
					for (var k = 0; k < this.gd.length; k++) {
						var gParam = "";
						var gP = this.gd[k].getStore().getRange(0,
								this.gd[k].getStore().getCount());
						for (i = 0; i < gP.length; i++) {
							gParam += Ext.encode(gP[i].data) + ",";
						}
						gParam = gParam.slice(0, gParam.length - 1);
						gridParam += '\"' + this.gridname[k] + '\"' + ':['
								+ gParam + '],';
					}
					param += gridParam;
				}
				param = param.slice(0, param.length - 1) + '}';
				Ext.Ajax.request({
					url : webContext + '/extjs/pageData?method=save',
					params : {
						info : param,
						model : this.model
					},
					success : function(response, options) {
						if (link == null || link == "null" || link == "") {
							window.location = window.location.href.toString();
						} else {
							window.location = webContext + link;
						}
					},
					failure : function(response, options) {
						Ext.MessageBox.alert("����ʧ��");
					}
				}, this);
			}
		});
		return button;
	},
	/*
	 * ���浥����ҳ����Ϣ
	 * 
	 */
	createSaveForFormPanel : function(btnName, link, container, imageUrl, scope) {
		var button = new Ext.Button({
			text : btnName,
			pressed : true,
			iconCls : 'save',
			scope : scope,
			handler : function() {
				if (link == "")
					alert("δָ�������ת����ҳ�棡");
				/*
				 * var fP =
				 * Ext.encode(Ext.getCmp(container).form.getValues(false)); var
				 * formParam = '{\"' + container + '\"' + ':[' + fP + ']}';
				 * alert(formParam); Ext.Ajax.request({ url : webContext +
				 * '/extjs/pageData?method=savePanel', params : { info :
				 * formParam, model : this.model, modelId : this.dataId, },
				 * success : function(response, options) {
				 * Ext.MessageBox.alert("����ɹ�"); }, failure : function(response,
				 * options) { Ext.MessageBox.alert("����ʧ��"); } }, this);
				 */
				var formParam = "";
				var gridParam = "";
				var param = '{';
				if (this.pa.length != "0") {
					for (var i = 0; i < this.pa.length; i++) {
						var fP = Ext.encode(this.pa[i].form.getValues(false));
						formParam += '\"' + this.formname[i] + '\"' + ':[' + fP
								+ '],';
					}
					param += formParam;
				}
				if (this.gd.length != "0") {
					for (var k = 0; k < this.gd.length; k++) {
						var gParam = "";
						var gP = this.gd[k].getStore().getRange(0,
								this.gd[k].getStore().getCount());
						for (i = 0; i < gP.length; i++) {
							gParam += Ext.encode(gP[i].data) + ",";
						}
						gParam = gParam.slice(0, gParam.length - 1);
						gridParam += '\"' + this.gridname[k] + '\"' + ':['
								+ gParam + '],';
					}
					param += gridParam;
				}
				param = param.slice(0, param.length - 1) + '}';
				// alert(param);
				// //************************************��ʾ��屣�������************************************
				Ext.Ajax.request({
					url : webContext + '/extjs/pageData?method=savePanel',
					params : {
						info : param,
						panel : container,
						model : this.model
					},
					success : function(response, options) {
						var responseArray = Ext.util.JSON
								.decode(response.responseText);
						var curId = responseArray.id;
						dataId = curId;
						if (link == null || link == "null" || link == "") {
							window.location = window.location.href.toString()
									+ '&tabId=' + container;
						} else {
							window.location = webContext + link + dataId
									+ "&tabId=" + container;
						}
						Ext.MessageBox.alert("����ɹ�");
					},
					failure : function(response, options) {
						Ext.MessageBox.alert("����ʧ��");
					}
				}, this);
			}
		});
		return button;
	},
	/*
	 * ���浥����ҳ����Ϣ
	 * 
	 */
	createSaveForGridPanel : function(btnName, link, container, imageUrl, scope) {
		var button = new Ext.Button({
			text : btnName,
			pressed : true,
			iconCls : 'save',
			scope : scope,
			handler : function() {
				if (link == "")
					alert("δָ�������ת����ҳ�棡");
				var formParam = "";
				var gridParam = "";
				var param = '{';
				if (this.pa.length != "0") {
					for (var i = 0; i < this.pa.length; i++) {
						var fP = Ext.encode(this.pa[i].form.getValues(false));
						formParam += '\"' + this.formname[i] + '\"' + ':[' + fP
								+ '],';
					}
					param += formParam;
				}
				if (this.gd.length != "0") {
					for (var k = 0; k < this.gd.length; k++) {
						var gParam = "";
						var gP = this.gd[k].getStore().getRange(0,
								this.gd[k].getStore().getCount());
						for (i = 0; i < gP.length; i++) {
							gParam += Ext.encode(gP[i].data) + ",";
						}
						gParam = gParam.slice(0, gParam.length - 1);
						gridParam += '\"' + this.gridname[k] + '\"' + ':['
								+ gParam + '],';
					}
					param += gridParam;
				}
				param = param.slice(0, param.length - 1) + '}';

				Ext.Ajax.request({
					url : webContext + '/extjs/pageData?method=savePanel',
					params : {
						info : param,
						panel : container,
						model : this.model
					},
					success : function(response, options) {
						var responseArray = Ext.util.JSON
								.decode(response.responseText);
						var curId = responseArray.id;
						dataId = curId;
						// alert(webContext+link+dataId);
						if (link == null || link == "null" || link == "") {
							window.location = window.location.href.toString()
									+ '&tabId=' + container;
						} else {
							window.location = webContext + link + dataId
									+ "&tabId=" + container;
						}
						Ext.MessageBox.alert("����ɹ�");
					},
					failure : function(response, options) {
						Ext.MessageBox.alert("����ʧ��");
					}
				}, this);
			}
		});
		return button;
	},

	/***************************************************************************
	 * ������������Ϣ��ҳ������������ݵķ���
	 */
	createSaveForModelConfigItem : function(btnName, link, imageUrl, scope) {
		var button = new Ext.Button({
			text : btnName,
			pressed : true,
			iconCls : 'save',
			scope : scope,
			handler : function() {
				if (link == "")
					alert("δָ�������ת����ҳ�棡");
				var formParam = "";
				var gridParam = "";
				var param = '{';
				if (this.pa.length != "0") {
					for (var i = 0; i < this.pa.length; i++) {
						var fP = Ext.encode(this.pa[i].form.getValues(false));
						if (!this.pa[i].form.isValid()) {
							Ext.Msg.alert('��ʾ', '����ɫ�����ߵ��������ȷ��д!');
							return;
						}
						formParam += '\"' + this.formname[i] + '\"' + ':[' + fP
								+ '],';
					}
					param += formParam;
				}
				if (this.gd.length != "0") {
					for (var k = 0; k < this.gd.length; k++) {
						var gParam = "";
						var gP = this.gd[k].getStore().getRange(0,
								this.gd[k].getStore().getCount());
						for (i = 0; i < gP.length; i++) {
							gParam += Ext.encode(gP[i].data) + ",";
						}
						gParam = gParam.slice(0, gParam.length - 1);
						gridParam += '\"' + this.gridname[k] + '\"' + ':['
								+ gParam + '],';
					}
					param += gridParam;
				}
				param = param.slice(0, param.length - 1) + '}';
				Ext.Ajax.request({
					url : webContext + '/ci_saveConfigItem.action?',
					params : {
						info : param,
						model : this.model
					},
					success : function(response, options) {
						// alert(link);
						Ext.MessageBox.alert("��ʾ","����ɹ�",function(){
							window.location = webContext + link;
						});
						
					},
					failure : function(response, options) {
						Ext.MessageBox.alert("��ʾ","����ʧ��");
					}
				}, this);
			}
		});
		return button;
	},
	// end ***********************************

	/*
	 * ɾ������ҳ��������Ϣ
	 * 
	 */
	createRemoveForModel : function(btnName, link, imageUrl, scope) {
		var button = new Ext.Button({
			text : btnName,
			pressed : true,
			iconCls : 'remove',
			scope : scope,
			handler : function() {
				if (link == "")
					alert("δָ�������ת����ҳ�棡");
				//alert(this.model);
				Ext.Ajax.request({
					url : webContext + '/extjs/pageData?method=remove',
					params : {
						dataId : this.dataId,
						model : this.model
					},
					success : function(response, options) {
						window.location = webContext + link;
					},
					failure : function(response, options) {
						Ext.MessageBox.alert("ɾ��ʧ��");
					}
				}, this);
			}
		});
		return button;
	},
	/*
	 * ɾ��������Ϣ���˷�������������ݿ���в���
	 * 
	 */
	createRemoveByColumn : function(btnName, link, container, containerTable,
			imageUrl, scope) {
		var button = new Ext.Button({
			text : btnName,
			pressed : true,
			iconCls : 'remove',
			scope : scope,
			handler : function() {
				var record = Ext.getCmp(container).getSelectionModel()
						.getSelected();
				var records = Ext.getCmp(container).getSelectionModel()
						.getSelections();
				if (!record) {
					Ext.Msg.alert("��ʾ", "����ѡ��Ҫɾ������!");
					return;
				}
				if (records.length == 0) {
					Ext.MessageBox.alert('����', '����ѡ��һ����Ϣ������ɾ��!');
				} else {
					Ext.MessageBox.confirm('��ʾ��', '��ȷ��Ҫ���иò�����', function(btn) {
						if (btn == 'yes') {
							if (records) {
								for (var i = 0; i < records.length; i++) {
									this.store.remove(records[i]);
									this.removedIds += records[i]
											.get(containerTable + "$id")
											+ ",";
								}
							}
						}
					}, this)
				}
			}
		});
		return button;
	},
	/*
	 * ɾ��������Ϣ��ɾ�����ݿ��Ӧ��Ϣ
	 * 
	 */
	createRemoveForGrid : function(btnName, link, imageUrl, scope) {
		var button = new Ext.Button({
			text : btnName,
			pressed : true,
			iconCls : 'remove',
			scope : scope,
			handler : function() {
				var record = this.grid.getSelectionModel().getSelected();
				var records = this.grid.getSelectionModel().getSelections();
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
						button) {
					if (button == 'yes') {
						for (var i = 0; i < records.length; i++) {
							id[i] = records[i].data.id;
							Ext.Ajax.request({
								url : webContext
										+ '/extjs/dataAction?method=remove&clazz='
										+ this.clazz,
								params : {
									id : id[i]
								},
								timeout : 100000,
								success : function(response) {
									var r = Ext.decode(response.responseText);
									if (!r.success) {
										Ext.Msg.alert("��ʾ��Ϣ", "����ɾ��ʧ��",
												function() {

												});
									}
									this.store.reload();
								},
								scope : this

							});
						}
					}
				}, this);
			}
		});
		return button;
	},
	/*
	 * ��ѯ
	 * 
	 */
	createSearch : function(btnName, link, imageUrl, scope) {
		var button = new Ext.Button({
			text : btnName,
			pressed : true,
			iconCls : 'search',
			scope : scope,
			handler : function() {
				var param = this.panel.form.getValues(false);
				param.methodCall = 'query';
				param.start = 1;
				this.formValue = param;
				this.pageBar.formValue = this.formValue;
				this.store.removeAll();
				this.store.load({
					params : param
				});
			}
		});
		return button;
	},
	/*
	 * ����,�����б�ҳ������������
	 * 
	 */
	createReset : function(btnName, link, imageUrl, scope) {
		var button = new Ext.Button({
			text : btnName,
			pressed : true,
			iconCls : 'refresh',
			scope : scope,
			handler : function() {
				this.panel.form.reset();
			}
		});
		return button;
	},
	/*
	 * �������������
	 * 
	 */
	createResetForPanel : function(btnName, container, imageUrl, scope) {
		var button = new Ext.Button({
			text : btnName,
			pressed : true,
			iconCls : 'refresh',
			scope : scope,
			handler : function() {
				Ext.getCmp(container).form.reset();
			}
		});
		return button;
	},
	/*
	 * ͨ���ض���ҳ�����޸�����
	 * 
	 */
	createModifyByPage : function(btnName, link, imageUrl, scope) {
		var button = new Ext.Button({
			text : btnName,
			pressed : true,
			iconCls : 'edit',
			scope : scope,
			handler : function() {
				if (link == "")
					alert("δָ�������ת����ҳ�棡");
				var record = this.grid.getSelectionModel().getSelected();
				var records = this.grid.getSelectionModel().getSelections();
				if (!record) {
					Ext.Msg.alert("��ʾ", "����ѡ��Ҫ�޸ĵ���!");
					return;
				}
				if (records.length > 1) {
					Ext.Msg.alert("��ʾ", "�޸�ʱֻ��ѡ��һ��!");
					return;
				}
				var modelTableId = this.modelTableName + "$id";
				var dataId = record.get(modelTableId);
				if (link == "")
					alert("δָ����ת����ҳ�棡");
				window.location = webContext + link + dataId;
			}
		});
		return button;
	},
	/*
	 * ͨ�������������޸�����
	 * 
	 */
	createModifyByWindow : function(btnName, link, container, containerTable,
			imageUrl, nextPageModel, scope) {
		var button = new Ext.Button({
			text : btnName,
			pressed : true,
			iconCls : 'edit',
			scope : scope,
			handler : function() {
				if (link == "")
					alert("δָ�������ת����ҳ�棡");
				var record = Ext.getCmp(container).getSelectionModel()
						.getSelected();
				var records = Ext.getCmp(container).getSelectionModel()
						.getSelections();
				if (!record) {
					Ext.Msg.alert("��ʾ", "����ѡ��Ҫ�޸ĵ���!");
					return;
				}
				if (records.length > 1) {
					Ext.Msg.alert("��ʾ", "�޸�ʱֻ��ѡ��һ��!");
					return;
				}
				var modelTableId = containerTable + "$id";
				var selectId = record.get(modelTableId);
				alert(webContext + "/tabFrame.jsp?url=" + webContext + link
						+ "?dataId=" + selectId + "****parentId=" + this.dataId
						+ "****parentTabId=" + container);
				this.modifyWin = new Ext.Window({
					title : "���",
					modal : true,
					height : 500,
					width : 800,
					resizable : false,
					draggable : true,
					autoLoad : {
						url : webContext + "/tabFrame.jsp?url=" + webContext
								+ link + "?dataId=" + selectId
								+ "****parentId=" + this.dataId
								+ "****parentTabId=" + container,
						text : "ҳ�������......",
						method : 'post',
						scripts : true,
						scope : this
					},
					viewConfig : {
						autoFill : true,
						forceFit : true
					},
					layout : 'fit',
					items : [{
						html : "���ڼ���ҳ������......"
					}]
				});
				this.modifyWin.setPagePosition(400, 100);
				this.modifyWin.show();
				this.modifyWin.on("deactivate", function() {
					window.location = window.location.href.toString()
							+ '&tabId=' + container;
				}, this);
			}
		});
		return button;
	},
	/*
	 * �ύ��ť
	 * 
	 */
	createSubmit : function(btnName, link, imageUrl, scope) {
		var button = new Ext.Button({
			text : btnName,
			pressed : true,
			iconCls : 'submit',
			scope : scope,
			handler : function() {
				var param = {
					dataId : this.dataId,
					applyId : this.dataId,
					applyType : 'project',
					applyTypeName : '��������',
					customer : ''
				};
				var defname = "itilproject";
				var da = new DataAction();
				da.apply(defname, param, Ext.emptyFn, "50101635", null);// ���߿Ƽ�
			}
		});
		return button;
	},
	/*
	 * ���밴ť
	 * 
	 */
	createImport : function(btnName, link, imageUrl, scope) {
		var button = new Ext.Button({
			text : btnName,
			pressed : true,
			iconCls : 'folder_go',
			scope : scope,
			handler : function() {
				alert("������δʵ�֣�");
			}
		});
		return button;
	},
	/*
	 * ������ť
	 * 
	 */
	createExport : function(btnName, link, imageUrl, scope) {
		var button = new Ext.Button({
			text : btnName,
			pressed : true,
			iconCls : 'export',
			scope : scope,
			handler : function() {
				alert("������δʵ�֣�");
			}
		});
		return button;
	},
	/*
	 * �ϴ���ť
	 * 
	 */
	createUpLoad : function(btnName, link, imageUrl, scope) {
		var button = new Ext.Button({
			text : btnName,
			pressed : true,
			iconCls : imageUrl,
			scope : 'folder_go',
			handler : function() {
				alert("�ϴ���δʵ�֣�");
			}
		});
		return button;
	},
	/*
	 * ���ذ�ť
	 * 
	 */
	createDownLoad : function(btnName, link, imageUrl, scope) {
		var button = new Ext.Button({
			text : btnName,
			pressed : true,
			iconCls : 'download',
			scope : scope,
			handler : function() {
				alert("������δʵ�֣�");
			}
		});
		return button;
	},
	/*
	 * ���ذ�ť
	 * 
	 */
	createGoBack : function(btnName, link, imageUrl, scope) {
		var button = new Ext.Button({
			text : btnName,
			pressed : true,
			iconCls : 'back',
			scope : scope,
			handler : function() {
				window.location.href = webContext + link;
			}
		});
		return button;
	},
	createWorkFlow : function(btnName, link, imageUrl, scope) {
		var button = new Ext.Button({
			text : btnName,
			pressed : true,
			iconCls : 'submit',
			scope : scope,
			handler : function() {
				window.parent.auditContentWin.audit();
			}
		});
		return button;
	},
	//����������ȷ�ϰ�ť��װ
	createYesSubmit : function(btnName, link, imageUrl, scope) {
		if(btnName==""){
			btnName = 'ͬ��';
		}
		var button = new Ext.Button({
			text : btnName,//btnName,	//modify by lee for �޸�ΪĬ�����ƣ�mantis��IT��Ŀ0000821 in 20090830
			pressed : true,
			iconCls : 'submit',
			scope : scope,
			handler : function() {
				window.parent.auditContentWin.specialAudit();
			}
		});
		return button;
	},
	//�����������ܾ���ť��װ
	createNoSubmit : function(btnName, link, imageUrl, scope) {
		var button = new Ext.Button({
			text : '�ܾ�',//btnName,	//modify by lee for �޸�ΪĬ�����ƣ�mantis��IT��Ŀ0000821 in 20090830
			pressed : true,
			iconCls : 'remove',
			scope : scope,
			handler : function() {
				window.parent.auditContentWin.specialNoAudit();
			}
		});
		return button;
	},
	createAddMarkUser : function(btnName, link, imageUrl, scope) {
		var button = new Ext.Button({
			text : btnName,
			pressed : true,
			iconCls : 'add',
			scope : scope,
			handler : function() {
				window.parent.auditContentWin.addMarkUser();
			}
		});
		return button;
	},
	createAddNextMarkUser : function(btnName, link, imageUrl, scope) {
		var button = new Ext.Button({
			text : btnName,
			pressed : true,
			iconCls : 'add',
			scope : scope,
			handler : function() {
				window.parent.auditContentWin.addNextMarkUser();
			}
		});
		return button;
	},
	/*
	 * δ֪��ť������ֱ�ӵ���ҳ��ʵ�ֵķ��� methodΪҳ���Զ���pageEvent�¼��� ������ҳ��ע���¼������������ô˷���
	 */
	createPageEvent : function(btnName, method, link, imageUrl, scope) {
		var button = new Ext.Button({
			text : btnName,
			pressed : true,
			iconCls : imageUrl,
			scope : scope,
			handler : function() {
				this.fireEvent(method);
			}
		});
		return button;
	},
	// ����������������,������
	ajaxGetData : function(url) {
		var conn = Ext.lib.Ajax.getConnectionObject().conn;
		conn.open("post", url, false);
		conn.send(null);
		if (conn.status == "200") {
			// alert(conn.responseText);
			var data = eval(conn.responseText);
			return data;
		} else {
			return 'no result';
		}
	},
	ajaxGetpage : function(url) {
		var conn = Ext.lib.Ajax.getConnectionObject().conn;
		conn.open("post", url, false);
		conn.send(null);
		if (conn.status == "200") {
			// alert(conn.responseText);
			// ע��ֻ�����ָ�ʽ���ܹ������Ľ���json
			var data = eval('(' + conn.responseText + ')');
			return data;
		} else {
			return 'no result';
		}
	}
});