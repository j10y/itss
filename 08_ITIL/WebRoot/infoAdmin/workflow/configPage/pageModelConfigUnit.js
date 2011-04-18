PageModelConfigUnit = Ext.extend(Ext.Panel, {
	id : "PageModelConfigUnit",
	height : 400,
	// autoScroll : true,
	align : 'center',
	foredit : true,
	width : 600,
	// frame : true,
	fitWidth : function(grid, columnIndex, e) {
		var c = columnIndex;
		var w = grid.view.getHeaderCell(c).firstChild.scrollWidth;
		for (var i = 0, l = grid.store.getCount(); i < l; i++) {
			w = Math.max(w, grid.view.getCell(i, c).firstChild.scrollWidth);
		}
		grid.colModel.setColumnWidth(c, w);
	},
	defaults : {
		bodyStyle : 'padding:4px'
	},
	layoutConfig : {
		columns : 1
	},
	freshPage : function() {
		window.location.reload();
	},

	goBackUrl : function() {
		history.go(-1);
	},

	items : this.items,
	getNodeName : function() {
		var url = webContext
				+ '/workflow/processconfig.do?methodCall=getNodeName&virtualDefinitionInfoId='
				+ this.virtualDefinitionInfoId + "&nodeId=" + this.nodeId;
		var conn = Ext.lib.Ajax.getConnectionObject().conn;
		conn.open("get", url, false);
		conn.send(null);
		// alert(conn.responseText)
		if (conn.status == "200") {
			var responseText = conn.responseText;
			// responseText = clearReturn(responseText);
			var data = Ext.decode(responseText);
			var nodeName = data.nodeName;
			return nodeName
		} else {
			return 'no result';
		}
	},
	getVitualDefintionName : function() {
		var url = webContext
				+ '/workflow/processconfig.do?methodCall=getVitualDefintionName&virtualDefinitionInfoId='
				+ this.virtualDefinitionInfoId + "&nodeId=" + this.nodeId;
		var conn = Ext.lib.Ajax.getConnectionObject().conn;
		conn.open("get", url, false);
		conn.send(null);
		// alert(conn.responseText)
		if (conn.status == "200") {
			var responseText = conn.responseText;
			// responseText = clearReturn(responseText);
			var data = Ext.decode(responseText);
			var virtualDefinitionDesc = data.virtualDefinitionDesc;
			return virtualDefinitionDesc
		} else {
			return 'no result';
		}
	},

	// ��ʼ��
	initComponent : function() {// �������涨��ı�����ȫ�ֱ���
		ActionConfigUnit.superclass.initComponent.call(this);// �ø����ȳ�ʼ��
		product = '';
		removeIds = '';

		var orderAction = new OrderAction();
		var roleStore = orderAction.getRoleStore(this.virtualDefinitionInfoId,
				this.nodeId);
		var pageModelStore = orderAction.getPageModelStore(
				this.virtualDefinitionInfoId, this.nodeId)
		var columns = new Array();
		var sm = new Ext.grid.CheckboxSelectionModel();
		this.store = new Ext.data.JsonStore({
			url : webContext
					+ '/workflow/processconfig.do?methodCall=getPageModelConfigUnit&virtualDefinitionInfoId='
					+ this.virtualDefinitionInfoId + "&nodeId=" + this.nodeId,
			root : "data",
			fields : ["id", "virtualDesc", "nodeName", "roleName",
					"pageModelName"],
			totalProperty : 'rowCount'
		});

		var obj = [{
					header : "������������",
					dataIndex : "virtualDesc"
				}, {
					header : "�ڵ�����",
					dataIndex : "nodeName"
				}, {
					header : "��ɫ����",
					dataIndex : "roleName"
				}, {
					header : "ҳ��ģ������",
					dataIndex : "pageModelName"
				}];

		columns[0] = sm;
		for (var i = 0; i < obj.length; i++) {
			var headItem = obj[i];
			var title = headItem.header;
			var alignStyle = 'left';

			var propertyName = headItem.dataIndex;
			if (propertyName == "outFile" || propertyName == "cfxmlFile"
					|| propertyName == "cfrFile") {
				headItem.editor = "";
			}
			var isHidden = false;
			if (propertyName == 'id') {
				isHidden = true;
			}
			if (propertyName == 'roleName') {
				headItem.editor = new Ext.form.ComboBox({
							xtype : "combo",
							id : "com.pageModelConfigUnit.combo",
							name : "role",
							fieldLabel : "ϵͳ��ɫ:",
							displayField : "name",
							valueField : "name",
							store : roleStore,
							// transform : "skins",
							// lazyRender : true,
							triggerAction : "all",
							selectOnFocus : true,
							temptyText : '��ѡ��...',
							typeAhead : true
						});
			}

			if (propertyName == 'pageModelName') {
				headItem.editor = new Ext.form.ComboBox({
					xtype : "combo",
					id : "com.pageModel.combo",
					fieldLabel : "ҳ��ģ������:",
					valueField : "name",
					displayField : "name",
					listWidth : 200,
					maxHeight : 200,
					emptyText: '��ѡ��',
					mode : 'remote',
					forceSelection : true,
					hiddenName : "name",
					editable : true,
					triggerAction : 'all', 
					lazyRender: true,
		            typeAhead: false,
		            autoScroll:true,
					allowBlank : true,
					name : "name",
					selectOnFocus: true,
					width : 200,
					// *******************************************************************
					store : new Ext.data.JsonStore({// displayField
						id : Ext.id(),
						url : webContext
								+'/workflow/processconfig.do?methodCall=getAllPageModel&processId='
							    + this.virtualDefinitionInfoId+'&nodeId='+this.nodeId +"&displayField=name",
						fields: ['name'],
						totalProperty:'rowCount',
						root:'data',
						id:'id',			
						sortInfo: {field: "name", direction: "ASC"},
						listeners : {
							beforeload : function(store, opt) {
								if (opt.params['name'] == undefined) {
									opt.params['name'] = Ext.getCmp("com.pageModel.combo").defaultParam;
								}
							}
						}
					}),
					// *******************************************************************
					pageSize:10,
					listeners:{
						blur:function(combo){//����ʧȥ�����ʱ��
							if(combo.getRawValue()==''){
								combo.reset();
							}
						},
						beforequery : function(queryEvent){
							var param = queryEvent.query;
							this.defaultParam=param;
							this.store.load({
								params:{name:param,start:0}
							});
							return true;
						}
					}					
				});
			}
			var columnItem = {
				header : title,
				dataIndex : propertyName,
				sortable : true,
				hidden : isHidden,
				align : alignStyle,
				editor : headItem.editor,
				renderer : headItem.renderer
			};

			columns[i + 1] = columnItem;
		}
		this.cm = new Ext.grid.ColumnModel(columns);
		this.cm.defaultSortable = true;
		var viewConfig = Ext.apply({
					forceFit : true
				}, this.gridViewConfig);
		this.pageBar = new Ext.PagingToolbar({
					pageSize : 10,// ʹ�õ���ϵͳĬ��ֵ
					store : this.store,
					displayInfo : true,
					displayMsg : '��ǰ��ʾ {0}-{1}����¼ /��{2}����¼',
					emptyMsg : "����ʾ����"
				});
		var processId = this.virtualDefinitionInfoId;
		var processName = this.getVitualDefintionName();

		var nodeName = this.getNodeName();

		var desc = this.desc;
		var nodeId = this.nodeId;
		this.grid = new Ext.grid.EditorGridPanel({
			id : 'com.pageModelConfigUnit',
			store : this.store,
			cm : this.cm,
			sm : sm,
			// title : '������״̬��������(�ɹ�ѡ�н����ύ��Ĭ��ȫ���ύ)',
			trackMouseOver : false,
			loadMask : true,
			clicksToEdit : 2,
			autoScroll : true,
			height : 340,
			width : 730,
			frame : true,
			bbar : this.pageBar,
			tbar : [
					{
						xtype : 'button',
						style : 'margin:4px 10px 4px 0',
						text : '����',
						scope : this,
						iconCls : 'add',
						handler : function() {
							var cmp = Ext.getCmp('com.pageModelConfigUnit');
							var store = cmp.getStore();
							var combo = Ext
									.getCmp('com.pageModelConfigUnit.combo');
							var roleName = combo.getValue();

							if (store.recordType) {
								var rec = new store.recordType({
										// newRecord : true
										});

								rec.fields.each(function(f) {
											rec.data['pageModelName'] = null;
											rec.data['virtualDesc'] = processName;
											// rec.data['nodeDesc'] = desc;
											rec.data['nodeName'] = nodeName;
											rec.data['roleName'] = null;
										});
								rec.commit();
								store.add(rec);
								return rec;
							} else {
								return false;
							}

						}
					},
					{
						xtype : 'button',
						style : 'margin:4px 10px 4px 0',
						text : 'ɾ��',
						iconCls : 'delete',
						scope : this,
						handler : function() {

							var cmp = Ext.getCmp('com.pageModelConfigUnit');
							var record = cmp.getSelectionModel().getSelected();
							var records = cmp.getSelectionModel()
									.getSelections();
							if (!record) {
								Ext.Msg.alert("��ʾ", "����ѡ��Ҫɾ������!");
								return;
							}
							if (records.length == 0) {
								Ext.MessageBox.alert('����', '����ѡ��һ����Ϣ������ɾ��!');
								return;
							}

							Ext.Msg.confirm('��ʾ��', '��ȷ��Ҫ����ɾ��������?', function(
									button) {
								if (button == 'yes') {
									for (var i = 0; i < records.length; i++) {
										var record = records[i];
										var id = record.get('id');
										removeIds += id;
										removeIds += ",";
										this.store.remove(records[i]);
									}
									// ɾ����Ҫɾ��������Ϣ��ͬʱɾ��save�����е�����߼� ADD by DJ ��

									orderAction
											.deletePageModelConfigUnitRecord(removeIds);
									// ���ɾ���б�
									removeIds = '';
								}
							}, this);
						}
					},
					{
						xtype : 'button',
						style : 'margin:4px 10px 4px 20',
						text : '����',
						iconCls : 'save',
						scope : this,
						handler : function() {

							var j = 0;
							var role = '';
							var cmp = Ext.getCmp('com.pageModelConfigUnit');
							var gridParam = cmp.getStore().getModifiedRecords();
							gridParam = [];
							var count = cmp.getStore().getCount();
							for (var i = 0; i < count; i++) {
								gridParam[i] = cmp.getStore().getAt(i);
							}
							for (i = 0; i < gridParam.length; i++) {
								var record = gridParam[i];
								if (record.get('pageModelName') == null
										|| record.get('pageModelName') == '') {
									Ext.Msg.alert('��ʾ', '��ѡ��ҳ��ģ������');
									return;
								}
								if (record.get("roleName") == null
										&& record.get("pageModelName") != null) {
									j = j + 1;
								}
								role = role + record.get("roleName");
								role = role + ',';
							}
							if (j >= 2) {
								Ext.Msg.alert('��ʾ', 'Ĭ�Ͻ���ֻ������һ����');
								return;
							}
							role = role.substring(0, role.length - 1);
							var s = role.split(",");
							for (i = 0; i < s.length; i++) {
								if (role.indexOf(s[i]) != role
										.lastIndexOf(s[i])) {
									Ext.Msg.alert('��ʾ', 'һ����ɫֻ������һ������ģ�ͣ�');
									return;
								}

							}
							for (i = 0; i < gridParam.length; i++) {
								product += Ext.encode(gridParam[i].data) + ",";
							}
							// ���淢������Ϣ�����ݿ���
							orderAction.savePageModelConfigUnitRecord(product,
									processId, this.nodeId, this.desc,
									processName);
						}
					},
					new Ext.Toolbar.TextItem('<font color=red>��ɫ����Ϊ�գ���������������ڵ��Ĭ�Ͻ���</font>')]
		});
		var param = {
			'mehtodCall' : 'query',
			'start' : 0
		};
		this.store.on('beforeload', function(a) {   
					      Ext.apply(a.baseParams,param);   
					});
		this.store.removeAll();
		this.store.load({
					params : param
				});
		//roleStore.load();

		this.add(this.grid)
	}
});
