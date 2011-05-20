// ���������б�
TaskInfoPanel = Ext.extend(Ext.Panel, {
	id : "TaskInfoPanel",
	closable : true,
	height : 'auto',
	width : 'auto',
	viewConfig : {
		autoFill : true,
		forceFit : true
	},
	layout : 'fit',
	items : this.items,
	reset : function() {
		this.fp.form.reset();
	},
	// ����
	search : function() {
		var realDefinitionName = this.processSearch.getValue();
		var param = {
			realDefinitionName : realDefinitionName,
			start : 0
		};
		this.store.on('beforeload', function(a) {   
					      Ext.apply(a.baseParams,param);   
					});
		this.store.removeAll();
		this.store.load({
			params : param
		});
	},

	// ɾ��
	removeData : function() {
	},
	// ����
	exportExcel : function() {
	},
	// �ϴ�
	upload : function() {
	},

	goBackUrl : function() {
		history.go(-1);
	},
	nodeConfig : function() {
		var record = this.grid.getSelectionModel().getSelected();
		var records = this.grid.getSelectionModel().getSelections();
		if (!record) {
			Ext.Msg.alert("��ʾ", "����ѡ��Ҫ��ѯ�Ķ�����!");
			return;
		}
		if (records.length > 1) {
			Ext.Msg.alert("��ʾ", "ֻ��ѡ��һ�н��в鿴!");
			return;
		}
		var vitualNodeId = record.get("id");
		var nodeId = record.get("nodeId");
		var desc = record.get("virtualNodeDesc");
		// nodeName = unicode(nodeName);
		// ����������������
		var url = webContext
				+ '/workflow/processconfig.do?methodCall=getConfigUnit&nodeId='
				+ nodeId + "&virtualDefinitionInfoId="
				+ this.virtualDefinitionInfoId + "&vitualNodeId="
				+ vitualNodeId;
		var conn = Ext.lib.Ajax.getConnectionObject().conn;
		conn.open("post", url, false);
		conn.send(null);
		if (conn.status == "200") {
			var responseText = conn.responseText;
			responseText = clearReturn(responseText);
			var data = eval("(" + responseText + ")");
		} else {
			return 'no result';
		}

		// �����е�configunitת����Panel���飬
		var virtualDefinitionInfoId = this.virtualDefinitionInfoId;
		var panels = new Array();
		for (var i = 0; i < data.length; i++) {
			var configUnit = data[i];
			var name = configUnit.name;

			var url = configUnit.url;
			var panel = new Ext.Panel({
				title : name,
				height : 350,
				frame : true,
				width : 730,
				autoLoad : {
					url : webContext + "/tabFrame.jsp?url=" + webContext + url
							+ "?nodeId=" + nodeId
							+ "****virtualDefinitionInfoId="
							+ virtualDefinitionInfoId + "****desc=" + desc,// +"****processName="+processName,
					scope : this,
					scripts : true
				}
			});
			panels[i] = panel;
		}

		var nodeConfig = new Ext.TabPanel({
			id : "com.zsgj.nodeConfig",
			activeTab : 0,
			height : 420,
			frame : true,
			width : 740,
			items : panels
		});

		var win = new Ext.Window({
			title : '�ڵ�����',
			width : 750,
			height : 450,
			modal : true,
			buttonAlign : "center",
			buttons : [
			{
				xtype : 'button',
				handler : function() {
					win.close();
				},
				text : '�ر�',
				scope : this
			}],
			items : nodeConfig
		});
		win.show();
	},


	initComponent : function() {
		TaskInfoPanel.superclass.initComponent.call(this);

		removeIds = '';

		var sm = new Ext.grid.CheckboxSelectionModel();
		this.store = new Ext.data.JsonStore({
			url : webContext
					+ '/workflow/update.do?methodCall=getAllVirtualNodeInfo&virtualDefinitionInfoId='
					+ this.virtualDefinitionInfoId,
			root : "data",
			fields : ["id", "nodeId", "virtualNodeName", "virtualNodeDesc",
					"virtualDefinitionInfo","virtualDefinitionName"],
			totalProperty:'rowCount'
		});
		this.cm = new Ext.grid.ColumnModel([sm, {
			header : "�������ƣ�����������",
			dataIndex : "virtualDefinitionName",
            width:150
		}, {
			header : "�ڵ�����",
			dataIndex : "virtualNodeName",
             width:150
		}, {
			header : "�ڵ�����",
			dataIndex : "virtualNodeDesc",
            width:150                  
		}]);
  
		
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
		this.formValue = '';
		this.pageBar.formValue = this.formValue;

		var processId = this.processId;
		var processName = this.processName;
		var desc = this.desc;
		var nodeId = this.nodeId;
		this.grid = new Ext.grid.GridPanel({
			title : '���̽ڵ��б�',
			id : 'com.taskInfoPanel',
			store : this.store,
			cm : this.cm,
			sm : sm,
			trackMouseOver : false,
			loadMask : true,
			clicksToEdit : 2,
			autoScroll : true,
			height : 490,
			width : 1000,
			frame : true,
			bbar : this.pageBar,
			tbar : [
			 {
				xtype : 'button',
				style : 'margin:4px 10px 4px 0',
				handler : this.goBackUrl,
				pressed : true,
				iconCls : 'back',
				text : '�ص���ʼ����'
			}, new Ext.Toolbar.TextItem('<font color=red>˫��ĳ���ڵ��н��нڵ�����</font>')]

		});
		this.grid.on("rowdblclick", this.nodeConfig, this);
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

		this.add(this.grid);
	},
	fitWidth : function(grid, columnIndex, e) {
		var c = columnIndex;
		var w = grid.view.getHeaderCell(c).firstChild.scrollWidth;
		for (var i = 0, l = grid.store.getCount(); i < l; i++) {
			w = Math.max(w, grid.view.getCell(i, c).firstChild.scrollWidth);
		}
		grid.colModel.setColumnWidth(c, w);
	}

});
