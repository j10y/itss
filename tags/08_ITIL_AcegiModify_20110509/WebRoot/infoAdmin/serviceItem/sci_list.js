PagePanel = Ext.extend(Ext.Panel, {
	id : "PagePanel",
	// baseCls : 'background: #6495ED;',
	closable : true,
	autoScroll : true,
	viewConfig : {
		autoFill : true,
		forceFit : true
	},
	frame : true,
	items : this.items,
	show : function() {
		var record = this.grid.getSelectionModel().getSelected();
		var id = record.get("id");
		window.location = webContext
				+ "/infoAdmin/serviceItem/serviceItem_info.jsp?dataId=" + id;
	},
	search : function() {
		var sType = Ext.getCmp('serviceItemTypeCombo').getValue();
		var sStatus = Ext.getCmp('serviceStatusCombo').getValue();
		var sName = Ext.getCmp('serviceItemName').getValue();
		var param = {
			sciType : sType,
			sciStatus : sStatus,
			sciName : sName,
			start : 0
		};
		this.store.baseParams=param;
		this.store.removeAll();
		this.store.load();
	},
	reset : function() {
		Ext.getCmp('serviceItemTypeCombo').clearValue();
		Ext.getCmp('serviceStatusCombo').clearValue();
		Ext.getCmp('serviceItemName').clearValue();
	},
	create : function() {
		window.location = webContext+ "/infoAdmin/serviceItem/serviceItem_info.jsp";
	},
	remove : function() {
		var record = this.grid.getSelectionModel().getSelected();
		var records = this.grid.getSelectionModel().getSelections();
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
							var rId = records[i].get("id");
							Ext.Ajax.request({
								url : webContext + '/serviceItem_removeSci.action?serviceItemId='+rId,
								success : function(response, options) {
								},
								failure : function(response, options) {
									Ext.MessageBox.alert("����ʧ��");
								}
							}, this);
						}
					}
				}
			}, this)
		}
	},
	initComponent : function() {
		var searchType = new Ext.form.ComboBox({
			hiddenName : 'serviceItemType',
			id : 'serviceItemTypeCombo',
			width : 200,
			fieldLabel : '����',
			lazyRender : true,
			displayField : 'name',
			valueField : 'id',
			allowBlank : true,
			typeAhead : true,
			name : 'serviceItemType',
			triggerAction : 'all',
			minChars : 50,
			queryDelay : 700,
			store : new Ext.data.JsonStore({
				url : webContext
						+ '/extjs/comboDataAction?clazz=com.zsgj.itil.service.entity.ServiceItemType',
				fields : ['id', 'name'],
				listeners : {
					beforeload : function(store, opt) {
						if (opt.params['serviceItemType'] == undefined) {
							opt.params['name'] = Ext
									.getCmp('serviceItemTypeCombo').defaultParam;
						}
					}
				},
				totalProperty : 'rowCount',
				root : 'data',
				id : 'id'
			}),
			pageSize : 10,
			listeners : {
				'beforequery' : function(queryEvent) {
					var param = queryEvent.combo.getRawValue();
					this.defaultParam = param;
					if (queryEvent.query == '') {
						param = '';
					}
					this.store.load({
						params : {
							name : param,
							start : 0
						}
					});
					return true;
				}
			}
		});
		var searchStatus = new Ext.form.ComboBox({
			hiddenName : 'serviceStatus',
			id : 'serviceStatusCombo',
			width : 200,
			fieldLabel : '״̬',
			lazyRender : true,
			displayField : 'name',
			valueField : 'id',
			allowBlank : true,
			typeAhead : true,
			name : 'serviceStatus',
			triggerAction : 'all',
			minChars : 50,
			queryDelay : 700,
			store : new Ext.data.JsonStore({
				url : webContext
						+ '/extjs/comboDataAction?clazz=com.zsgj.itil.service.entity.ServiceStatus',
				fields : ['id', 'name'],
				listeners : {
					beforeload : function(store, opt) {
						if (opt.params['serviceStatus'] == undefined) {
							opt.params['name'] = Ext
									.getCmp('serviceStatusCombo').defaultParam;
						}
					}
				},
				totalProperty : 'rowCount',
				root : 'data',
				id : 'id'
			}),
			pageSize : 10,
			listeners : {
				'beforequery' : function(queryEvent) {
					var param = queryEvent.combo.getRawValue();
					this.defaultParam = param;
					if (queryEvent.query == '') {
						param = '';
					}
					this.store.load({
						params : {
							name : param,
							start : 0
						}
					});
					return true;
				}
			}
		});
		var searchName = new Ext.form.TextField({
			fieldLabel : '��ѯ����',
			emptyText : '��������ҷ���������',
			id : 'serviceItemName',
			name : 'serviceItemName',
			width : 200
		});
		// �����������
		this.store = new Ext.data.JsonStore({
			url : webContext + '/serviceItem_listServiceItem.action',
			fields : ['id', 'serviceItemCode', 'name', 'serviceItemType',
					'serviceStatus', 'serviceType'],
			totalProperty : "rowCount",
			root : "data",
			id : 'id'
		});

		this.pageBar = new Ext.PagingToolbar({
			id : 'pagebar',
			pageSize : 15,
			store : this.store,
			displayInfo : true,
			displayMsg : '��ǰ��ʾ {0}-{1}����¼ /��{2}����¼',
			emptyMsg : "û�з�������������"
		});
		var sm = new Ext.grid.CheckboxSelectionModel();

		// ����Grid������
		this.grid = new Ext.grid.GridPanel({
			height : 500,
			loadMask : true,
			id : 'gridpanel',
			frame : true,
			store : this.store,
			bbar : this.pageBar,
			autoScroll : true,
			trackMouseOve : true,
			tbar : [new Ext.Toolbar.TextItem("����������"), '-', searchType,
					new Ext.Toolbar.TextItem("������״̬"), '-', searchStatus,
					new Ext.Toolbar.TextItem("����������"), '-', searchName, {
						pressed : true,
						text : "����",
						iconCls : 'search',
						scope : this,
						handler : this.search
					}, {
						pressed : true,
						text : "����",
						iconCls : 'reset',
						scope : this,
						handler : this.reset
					}, {
						pressed : true,
						text : "�����·�����",
						iconCls : 'add',
						scope : this,
						handler : this.create
					}, {
						pressed : true,
						text : "ɾ��������",
						iconCls : 'remove',
						scope : this,
						handler : this.remove
					}],
			sm : sm,
			columns : [sm, {
				id : 'id',
				header : "id",
				width : 50,
				sortable : true,
				dataIndex : 'id',
				hidden : true
			}, {
				header : "��������",
				width : 100,
				sortable : true,
				dataIndex : 'serviceItemCode'
			}, {
				header : "����������",
				width : 150,
				sortable : true,
				dataIndex : 'name'
			}, {
				header : "����������",
				width : 100,
				sortable : true,
				dataIndex : 'serviceItemType'
			}, {
				header : "������״̬",
				width : 100,
				sortable : true,
				dataIndex : 'serviceStatus'
			}, {
				header : "��������",
				width : 100,
				sortable : true,
				dataIndex : 'serviceType'
			}]
		})
		var param = {
			pageSize : 15,
			tableName : "",
			start : 0
		};
		this.store.baseParams=param;
		this.store.removeAll();
		this.store.load();
		this.grid.on("rowdblclick", this.show, this);
		var item = new Array();
		item.push(this.grid);
		this.items = item;
		PagePanel.superclass.initComponent.call(this);
	}
});
