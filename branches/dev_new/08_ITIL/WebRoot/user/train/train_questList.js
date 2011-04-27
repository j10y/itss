PageTemplates = Ext.extend(Ext.Panel, {
	id : "PageTemplates",
	layout : 'table',
	height : 'auto',
	align : 'center',
	foredit : true,
	width : 1050,
	frame : true,
	autoScroll : true,
	defaults : {
		bodyStyle : 'padding:4px'
	},
	layoutConfig : {
		columns : 1
	},
	save : function() {
		var info = Ext.encode(Ext.getCmp('page_trainSurvey').getForm()
				.getValues());
		if (!Ext.getCmp('page_trainSurvey').form.isValid()) {
			Ext.MessageBox.alert("��ʾ", "����ȷ��д��Ϣ��лл��������");
			return false;
		}
		// alert(info);
		Ext.Ajax.request({
			url : webContext + '/trainPlan_newSave.action',
			method : "POST",
			params : {
				info : info
			},
			success : function(response, options) {
				var responseArray = Ext.util.JSON.decode(response.responseText);
				if (responseArray == "100") {
					Ext.MessageBox.alert("��ʾ", "���ܱ��棬һ���ʾ�����ֻ����һ�������ķ���������ѡ",
							function() {
							});
					return;
				}
				var dataId = responseArray.id;
				Ext.MessageBox.alert("��ʾ", "����ɹ�", function() {
							window.location = webContext
									+ "/user/train/train_questList.jsp?dataId="
									+ dataId;
						});
			},
			failure : function(response, options) {
				Ext.MessageBox.alert("��ʾ", "����ʧ��");
			},
			scope : this
		});

	},
	gotoBack : function() {
		history.back();
	},
	view : function() {
		var dataId = this.dataId;
		if (dataId == "" || dataId == 'null') {
			Ext.Msg.alert("��ʾ", "�㻹����Ԥ��");
		} else {
			window.location = webContext
					+ "/trainPlan_findQuest.action?dataId=" + dataId;
		}
	},
	create : function() {
		var info = Ext.getCmp('page_trainSurvey').getForm().getValues();
		var dataId = info.TrainSurvey$id;
		if (dataId == "" || dataId == 'null') {
			Ext.Msg.alert("��ʾ", "�㻹û�б����ʾ�,�����½�����!");
			return;
		}
		var clazz = "com.zsgj.itil.train.entity.Quest";
		var clszz2 = "com.zsgj.itil.train.entity.QuestOption";
		var store2 = new Ext.data.JsonStore({
					url : webContext + '/trainPlan_fingQuestOption.action',
					fields : ['id', 'content', 'answerNo'],
					root : "data"
				});
		store2.load();
		// this.grida = new PagePanel();
		var sm = new Ext.grid.CheckboxSelectionModel();
		var grid = new Ext.grid.EditorGridPanel({
					// title: '����ѡ��',
					id : 'grid',
					height : 300,
					width : 660,
					autoScroll : true,
					sm : sm,
					clickToEdit : 2,
					// viewConfig: {
					// autoFill: true,
					// forceFit: true
					// },
					columns : [sm, {
								header : "���",
								dataIndex : "id",
								hidden : true
							}, {
								header : "����ѡ����",
								width : 60,
								dataIndex : "answerNo",
								editor : new Ext.form.TextField()
							}, {
								header : "����ѡ������",
								dataIndex : "content",
								editor : new Ext.form.TextField()
							}],
					store : store2,
					autoExpandColumn : 2,
					tbar : ['   ',

					{
						text : '����',
						pressed : true,
						id : 'exportBtn',
						handler : function() {
							// alert(Ext.getCmp('grid').store);
							var store = Ext.getCmp('grid').store;
							if (store.recordType) {
								var rec = new store.recordType({
											newRecord : true
										});
								rec.fields.each(function(f) {
											rec.data[f.name] = f.defaultValue
													|| null;
										});
								rec.commit();
								store.add(rec);
								return rec;
							}
							return false;

						},
						scope : this,
						iconCls : 'add',
						cls : "x-btn-text-icon"
					},
							// '&nbsp;|&nbsp;',
							// {
							// text:"ɾ��",
							// pressed: true,
							// handler: function(){
							// alert("ɾ��");
							//                         	
							//                         	
							// },
							// scope:this,
							// iconCls:'delete',
							// cls:"x-btn-text-icon"
							// },
							'&nbsp;|&nbsp;<font color="red">˫������ֶο��Զ�������޸ģ�</font>']

				});
		var da = new DataAction();
		var data = da.getElementsForAdd(clazz);
		var item = new Array();
		for (i = 0; i < data.length; i++) {
			if (data[i].xtype == 'combo') {
				// data[i].listeners={
				// beforequery:function(){data[i].store.reload(); }}
				data[i].listeners = {
					select : function(combo, record, index) {
						// combo.store.load();
						var id = record.get("id")
						// alert(id);
						if (id == 1 || id == 2) {
							// alert(Ext.encode(tabs));
							// tabs.removeTab({title : '����ѡ��',items:grid});
							// alert(tabs.items.length);
							if (tabs.items.length != 1) {
								tabs.remove(Ext.getCmp('sec'));

							}
							// alert(Ext.encode(data[i].store));
							// alert("jjjjjjjjjjjjjjjjj");
							// tabs.add({id:"root",title : '�����', items:
							// envForm});
							tabs.add({
										id : "sec",
										title : '����ѡ��',
										items : grid,
										listeners : {
											activate : function() {
											}
										}
									});
							// alert(grid);
							// if(tabs.items.length!=1){
							// tabs.remove(sec);
							//							
							// }
							Ext.getCmp('win').hide();
							// item.push(envForm);
							// item.push(grid);
							win.show();
							// store.reload();
						}
						if (id == 3 || id == 4) {
							// alert(34);
							if (tabs.items.length != 1) {
								tabs.remove(Ext.getCmp('sec'));

							}
							Ext.getCmp('win').hide();
							win.show();
						}
					}
				};

			}
		}

		var custdata = da.split(data);
		var envForm = new Ext.form.FormPanel({
					id : 'envForm',
					layout : 'table',
					width : 650,
					height : 300,
					layoutConfig : {
						columns : 2
					},
					defaults : {
						bodyStyle : 'padding:15px'
					},
					frame : true,
					items : custdata

				});

		var tabs = new Ext.TabPanel({
					activeTab : 0,
					width : 'auto',
					height : 300,
					autoDestroy : false,
					layoutOnTabChange : true,
					items : [{
								id : "root",
								title : '�����',
								items : envForm
							}]
				});
		item.push(tabs);
		// item.push(grid);
		// item.push(envForm);
		var store = this.store;
		// Ext.extend(Ext.TabPanel);
		var win = new Ext.Window({
					id : 'win',
					title : '����',
					width : 700,
					height : 'auto',
					maximizable : true,
					items : item,
					closeAction : 'hide',
					bodyStyle : 'padding:4px',
					buttons : [{
						text : '����',
						handler : function() {
							// alert("aaa");
							var info = Ext.getCmp('envForm').getForm()
									.getValues();
							if (info.questName == "" || info.questType == "") {
								Ext.MessageBox.alert("��ʾ", "ȱ�ٱ�����Ϣ������д��ȫ�ٱ���");
								return;
							}
							// alert(Ext.encode(info));
							var stored = this.store;
							var store = Ext.getCmp('grid').store;
							var product = "";
							store.each(function(record) {
										if (record.dirty) {
											product += Ext.encode(record.data)
													+ ",";
										}
									})
							// alert(Ext.encode(product));
							product = unicode(product);
							// alert(dataId);
							Ext.Ajax.request({
										url : webContext
												+ '/trainPlan_saveQuest.action',
										params : {
											id : info.id,
											dataId : dataId,
											questName : info.questName,
											questType : info.questType,
											product : product
										},
										success : function(response, options) {
											Ext.MessageBox.alert("��ʾ", "����ɹ�",
													function() {
														stored.reload();
														win.close()
														// window.location=
													// webContext+"/user/train/train_instructorList.jsp";
												});

										},
										failure : function(response, options) {
											Ext.MessageBox.alert("����ʧ��");
										}

									})
							// store.reload();
							// win.close()

						},
						scope : this

					}, {
						text : '�ر�',
						handler : function() {
							// location = 'ibmCust.jsp'
							win.close();

						},
						listeners : {
							'beforeclose' : function(p) {

								return true;
							}

						},
						scope : this
					}]
				});
		win.addListener('beforeshow', function(o) {
					currentWindow = o;
				});
		win.addListener('destroy', function(o) {
					currentWindow = null;
				});
		win.show();

	},
	popup : function() {
		var record = this.gridCompanel_trainQuest.getSelectionModel()
				.getSelected();
		if (!record) {
			Ext.Msg.alert("��ʾ", "����ѡ��Ҫ�޸ĵ���!");
			return;
		}
		var id = record.get("TrainQuest$id");// ********************************�õ��޸��е�id
		// var clazz = "com.zsgj.itil.train.entity.Quest";

		// var info = Ext.getCmp('cataPanel').getForm().getValues();
		// //alert(Ext.encode(info));
		// if(dataId==""||dataId=='null'){
		// alert("�㻹û�б����ʾ�,�����½�����!");
		// return;
		// }
		var clazz = "com.zsgj.itil.train.entity.Quest";
		var clszz2 = "com.zsgj.itil.train.entity.QuestOption";
		var store2 = new Ext.data.JsonStore({
			url : webContext + '/trainPlan_fingQuestOption.action',
			fields : ['id', 'content', 'answerNo'],
			root : "data"

				// data:data,fields:["id","name","organization","homepage"]
			});
		store2.load({
					params : {
						id : id
					}
				});
		// this.grida = new PagePanel();
		var sm = new Ext.grid.CheckboxSelectionModel();
		var grid = new Ext.grid.EditorGridPanel({
			// title: '����ѡ��',
			id : 'grid',
			height : 300,
			width : 660,
			autoScroll : true,
			sm : sm,
			clickToEdit : 2,
			columns : [sm, {
						header : "���",
						dataIndex : "id",
						hidden : true
					}, {
						header : "����ѡ����",
						width : 50,
						dataIndex : "answerNo",
						editor : new Ext.form.TextField()
					}, {
						header : "����ѡ������",
						width : 400,
						dataIndex : "content",
						editor : new Ext.form.TextField()
					}],
			store : store2,
			autoExpandColumn : 2,
			tbar : ['   ',

			{
						text : '����',
						pressed : true,
						id : 'exportBtn',
						handler : function() {
							var store = Ext.getCmp('grid').store;
							if (store.recordType) {
								var rec = new store.recordType({
											newRecord : true
										});
								rec.fields.each(function(f) {
											rec.data[f.name] = f.defaultValue
													|| null;
										});
								rec.commit();
								store.add(rec);
								return rec;
							}
							return false;

						},
						scope : this,
						iconCls : 'add',
						cls : "x-btn-text-icon"
					}, '&nbsp;|&nbsp;', {
						text : "ɾ��",
						pressed : true,
						handler : function() {
							var grid = Ext.getCmp('grid');
							var record = grid.getSelectionModel().getSelected();
							var records = grid.getSelectionModel()
									.getSelections();
							var clazz = "com.zsgj.itil.train.entity.QuestOption";
							if (!record) {
								Ext.Msg.alert("��ʾ", "����ѡ��Ҫɾ������!");
								return;
							}
							var da = new DataAction();
							for (var i = 0; i < records.length; i++) {
								var scId = records[i].get("id");
								store2.remove(records[i]);
								Ext.Ajax.request({

									url : webContext
											+ '/trainPlan_removeQuestOption.action',
									params : {
										dataId : scId,
										clazz : clazz
									},
									success : function(response, options) {
										Ext.MessageBox.alert("��ʾ", "ɾ���ɹ�",
												function() {
													store2.reload();
													// window.location=
												// webContext+"/user/train/train_instructorList.jsp";
											});

									},
									failure : function(response, options) {
										Ext.MessageBox.alert("��ʾ", "ɾ��ʧ��");
									}
								}, this);
							}

						},
						scope : this,
						iconCls : 'delete',
						cls : "x-btn-text-icon"
					}, '&nbsp;|&nbsp;<font color="red">˫������ֶο��Զ�������޸ģ�</font>']

		});
		var da = new DataAction();
		var data = da.getElementsForEdit(clazz, id);
		var item = new Array();
		for (i = 0; i < data.length; i++) {
			if (data[i].xtype == 'combo') {
				// data[i].listeners={
				// beforequery:function(){data[i].store.reload(); }}
				// alert("sss"+id+" sss");
				// var id = this.id;
				// if(Ext.getCmp('questTypeCombo')==1||Ext.getCmp('questTypeCombo')==2){
				// alert("ajsdhfalkjsdfh");
				//		 			
				// }
				Ext.Ajax.request({

							url : webContext
									+ '/trainPlan_findQuestType.action',
							params : {
								id : id
							},
							success : function(response, options) {
								// alert(response.responseText);
								var typeid = response.responseText;
								if (typeid == 1 || typeid == 2) {
									// alert("ssss");
									tabs.add({
												id : "sec",
												title : '����ѡ��',
												items : grid,
												listeners : {
													activate : function() {
													}
												}
											});
								}
							}
						}, this)
				data[i].on("select", function(combo, record, index) {
							var id = record.get("id")
							if (id == 1 || id == 2) {
								if (tabs.items.length != 1) {
									tabs.remove(Ext.getCmp('sec'));

								}
								tabs.add({
											id : "sec",
											title : '����ѡ��',
											items : grid,
											listeners : {
												activate : function() {
												}
											}
										});
								Ext.getCmp('win').hide();
								win.show();
							}
							if (id == 3 || id == 4) {
								if (tabs.items.length != 1) {
									tabs.remove(Ext.getCmp('sec'));

								}
								Ext.getCmp('win').hide();
								win.show();
							}
						}, this);

			}
		}

		var custdata = da.split(data);
		var envForm = new Ext.form.FormPanel({
					id : 'envForm',
					layout : 'table',
					width : 650,
					height : 300,
					layoutConfig : {
						columns : 2
					},
					defaults : {
						bodyStyle : 'padding:15px'
					},
					frame : true,
					items : custdata

				});

		var tabs = new Ext.TabPanel({
					activeTab : 0,
					width : 'auto',
					height : 300,
					autoDestroy : false,
					layoutOnTabChange : true,
					items : [{
								id : "root",
								title : '�����',
								items : envForm
							}]
				});
		item.push(tabs);
		var store = this.store;
		var win = new Ext.Window({
					id : 'win',
					title : '����',
					width : 700,
					height : 'auto',
					maximizable : true,
					items : item,
					closeAction : 'hide',
					bodyStyle : 'padding:4px',
					buttons : [{
						text : '����',
						handler : function() {
							var info = Ext.getCmp('envForm').getForm()
									.getValues();
							if (info.questName == "" || info.questType == "") {
								Ext.MessageBox.alert("��ʾ", "ȱ�ٱ�����Ϣ������д��ȫ�ٱ���");
								return;
							}
							var stored = this.store;
							var store = Ext.getCmp('grid').store;
							var product = "";
							store.each(function(record) {
										if (record.dirty) {
											product += Ext.encode(record.data)
													+ ",";
										}
									})
							product = unicode(product);
							Ext.Ajax.request({
										url : webContext
												+ '/trainPlan_saveQuest.action',
										params : {
											id : info.id,
											dataId : this.dataId,
											questName : info.questName,
											questType : info.questType,
											product : product
										},
										success : function(response, options) {
											Ext.MessageBox.alert("��ʾ", "����ɹ�",
													function() {
														stored.reload();
														win.close()
														// window.location=
													// webContext+"/user/train/train_instructorList.jsp";
												});
										},
										failure : function(response, options) {
											Ext.MessageBox.alert("����ʧ��");
										}
									})
						},
						scope : this

					}, {
						text : '�ر�',
						handler : function() {
							win.close();
						},
						listeners : {
							'beforeclose' : function(p) {
								return true;
							}
						},
						scope : this
					}]
				});
		win.addListener('beforeshow', function(o) {
					currentWindow = o;
				});
		win.addListener('destroy', function(o) {
					currentWindow = null;
				});
		win.show();

	},
	removeQuest : function() {
		var store = this.store;
		var record = this.gridCompanel_trainQuest.getSelectionModel()
				.getSelected();
		var records = this.gridCompanel_trainQuest.getSelectionModel()
				.getSelections();
		var clazz = "com.zsgj.itil.train.entity.Quest";
		if (!record) {
			Ext.Msg.alert("��ʾ", "����ѡ��Ҫɾ������!");
			return;
		}
		var da = new DataAction();
		var ids = "";
		var stored = this.store;
		for (var i = 0; i < records.length; i++) {

			var scId = records[i].get("TrainQuest$id");
			Ext.Ajax.request({

						url : webContext + '/trainPlan_removeQuest.action',
						params : {
							dataId : scId,
							clazz : clazz
						},
						success : function(response, options) {
							Ext.MessageBox.alert("��ʾ", "ɾ���ɹ�", function() {
										stored.reload();
									});
							// window.location=
							// webContext+"/user/train/train_instructorList.jsp";

						},
						failure : function(response, options) {
							Ext.MessageBox.alert("��ʾ", "ɾ��ʧ��");
						}
					}, this);
		}

	},
	getTabpanel : function(tab, tabTitle) {
		this.tabPanel = new Ext.TabPanel({
					xtype : 'tabpanel',
					activeTab : 0,
					enableTabScroll : true,
					// minTabWidth:100,
					// resizeTabs:true,
					title : tabTitle,
					deferredRender : false,
					frame : true,
					plain : true,
					border : false,
					// tabPosition:'bottom',
					baseCls : 'x-plain',// �Ƿ����úͱ���ɫͬ��
					width : 900,
					// bodyBorder : true,
					defaults : {
						autoHeight : true,
						bodyStyle : 'padding:2px'
					},
					items : tab
				});
		return this.tabPanel;

	},
	getPanel : function(appa, panelTitle) {
		this.Panel = new Ext.Panel({
					id : "Pages",
					height : 'auto',
					align : 'center',
					title : panelTitle,
					border : false,
					defaults : {
						bodyStyle : 'padding:5px'
					},
					width : 'auto',
					frame : true,
					autoScroll : true,
					layoutConfig : {
						columns : 1
					},
					items : appa
				});
		return this.Panel;

	},

	getFormpage_trainSurvey : function() {
		var da = new DataAction();
		var data = null;
		var biddata = "";
		var buttonUtil = new ButtonUtil();
		this.getFormButtons = buttonUtil.getButtonForPanel("page_trainSurvey",
				this);
		if (this.dataId != '0') {
			data = da.getPanelElementsForEdit("page_train_quest_list2",
					"page_trainSurvey", this.dataId);// ����Ҫ��ʱ���
			biddata = da.split(data);
		} else {
			data = da.getPanelElementsForAdd("page_trainSurvey");
			biddata = da.split(data);
		}
		if (this.getFormButtons.length != 0) {
			this.formpage_trainSurvey = new Ext.form.FormPanel({
						id : 'page_trainSurvey',
						layout : 'table',
						height : 'auto',
						width : 800,
						frame : true,
						collapsible : true,
						defaults : {
							bodyStyle : 'padding:4px'
						},
						layoutConfig : {
							columns : 4
						},
						title : "�ʾ�",
						items : biddata,
						buttonAlign : 'center',
						buttons : this.getFormButtons
					});
		} else {
			this.formpage_trainSurvey = new Ext.form.FormPanel({
						id : 'page_trainSurvey',
						layout : 'table',
						height : 'auto',
						width : 800,
						frame : true,
						collapsible : true,
						defaults : {
							bodyStyle : 'padding:4px'
						},
						layoutConfig : {
							columns : 4
						},
						title : "�ʾ�",
						items : biddata
					});
		}
		return this.formpage_trainSurvey;
	},
	getGridCompanel_trainQuest : function() {
		var da = new DataAction();
		var obj = da.getListPanelElementsForHead("panel_trainQuest");
		var buttonUtil = new ButtonUtil();
		var getGridButtons = buttonUtil.getButtonForPanel("panel_trainQuest",
				this);
		var sm = new Ext.grid.CheckboxSelectionModel();
		var columns = new Array();
		columns[0] = sm;
		// ѭ�������е��У�Ȼ��������������
		for (var i = 0; i < obj.length; i++) {
			var headItem = obj[i];
			var title = headItem.header;
			var alignStyle = 'left';
			var propertyName = headItem.dataIndex;
			var hide = headItem.isHidden;
			var isHidde = false;
			if (hide == 'true') {
				isHidde = true;
			}
			// ��ÿһ����������
			var columnItem = "";
			columnItem = {
				header : title,
				dataIndex : propertyName,
				sortable : true,
				hidden : isHidde,
				align : alignStyle
			}
			columns[i + 1] = columnItem;
		}
		this.cm = new Ext.grid.ColumnModel(columns);
		this.store = da.getPagePanelJsonStore("panel_trainQuest", obj);
		this.cm.defaultSortable = true;
		var viewConfig = Ext.apply({
					forceFit : true
				}, this.gridViewConfig);
		this.gridCompanel_trainQuest = new Ext.grid.EditorGridPanel({
					id : 'panel_trainQuest',
					store : this.store,
					cm : this.cm,
					sm : sm,
					title : "����",
					loadMask : true,
					collapsible : true,
					autoScroll : true,
					height : 200,
					loadMask : true,
					width : 800,// this.width - 15,
					frame : true,
					tbar : [getGridButtons]
				});
		if (this.dataId != '0') {
			var pcnameValue = "";
			var fcnameObj = Ext.getCmp("TrainSurvey$id");
			if (fcnameObj != undefined) {
				pcnameValue = Ext.getCmp("TrainSurvey$id").getValue();
			}
			var str = '{' + '\"' + "survey" + '\"' + ':' + '\"' + pcnameValue
					+ '\"' + '}';// ����Ҫ��ʱ���

			if (this.model == "flm_requireFormPage"
					|| "panel_trainQuest" == "configItemListPanel") { //
				var str = '{\"configItemRequireId\":\"' + this.dataId + '\"}';
			}
			var param = eval('(' + str + ')');
			param.methodCall = 'query';
			// alert(str);
			this.store.load({
						params : param
					});
		}
		return this.gridCompanel_trainQuest;
	},
	items : this.items,
	/*
	 * clientvalidation ��ʼ��
	 */
	initComponent : function() {
		var dataId = this.dataId;
		var temp = new Array();
		this.temp = temp;
		this.model = "page_train_quest_list2";
		var buttonUtil = new ButtonUtil();
		this.mybuttons = buttonUtil.getButtonForModel("page_train_quest_list2",
				this);
		if (this.mybuttons != "") {
			this.allbuttons = {
				layout : 'table',
				height : 'auto',
				width : 800,
				// style : 'margin:4px 6px 4px 300px',
				align : 'center',
				defaults : {
					bodyStyle : 'padding:4px'
				},
				items : this.mybuttons
			};
		} else {
			this.allbuttons = {
				layout : 'table',
				height : 'auto',
				width : 800,
				// style : 'margin:4px 6px 4px 300px',
				align : 'center',
				defaults : {
					bodyStyle : 'padding:4px'
				}
			};
		}

		this.getFormpage_trainSurvey();
		temp.push(this.formpage_trainSurvey);
		this.getGridCompanel_trainQuest();
		temp.push(this.gridCompanel_trainQuest);
		temp.push(this.allbuttons);
		this.items = temp;
		this.gridCompanel_trainQuest.on("rowdblclick", this.popup, this);
		this.on("save", this.save, this);
		this.on("gotoBack", this.gotoBack, this);
		this.on("view", this.view, this);
		this.on("create", this.create, this);
		this.on("popup", this.popup, this);
		this.on("removeQuest", this.removeQuest, this);
		PageTemplates.superclass.initComponent.call(this);
		if (dataId == "" || dataId == null || dataId == '0') {// 2010-08-20
																// modified by
																// huzh for bug
			var param = {
				'mehtodCall' : 'query',
				'start' : 0
			};
		} else {
			var param = {
				'mehtodCall' : 'query',
				'survey' : dataId,
				'start' : 0
			};
			// this.pageBar.formValue = param;

			this.store.removeAll();
			this.store.load({
						params : param
					});
		}

	}
})