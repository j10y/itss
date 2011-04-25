// �ڵ���Ϣ
NodeViewPanel = Ext.extend(Ext.Panel, {
	id : "NodeViewPanel",
	title : "���̽ڵ��б�",
	//closable : true,
	autoScroll : true,
	// layout : 'table',
	height : 'auto',
	align : 'center',
	foredit : true,
	width : 1050,
	frame : true,
	viewConfig : {
		autoFill : true,
		forceFit : true
	},
	items : this.items,
	reset : function() {
		this.fp.form.reset();
	},
	// ����
	search : function() {
		var param = this.fp.form.getValues(false);

		this.formValue = param;
		this.pageBar.formValue = this.formValue;
		param.start = 1;

		this.store.removeAll();
		this.store.load({
			params : param
		});
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
		var id = record.get("id");
		var desc = record.get("desc");
		var processName = record.get("definitionName");
		//nodeName = unicode(nodeName);
		// ����������������
		var url = webContext
				+ '/workflow/processconfig.do?methodCall=getConfigUnit&id='
				+ id + '&desc=' + desc+"&processName="+processName+"&processId="+this.processId;
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
		var processId=this.processId;
		
		var panels = new Array();
		for (var i = 0; i < data.length; i++) {
			var configUnit = data[i];
			var name = configUnit.name;
			
			var url = configUnit.url;
			var panel = new Ext.Panel({
				title : name,
				height :  350,
				frame : true,
				width :  730,
				autoLoad : {
//					url : webContext+"/tabFrame.jsp?url="+ webContext+url,
					url : webContext +"/tabFrame.jsp?url="+ webContext+url+"?nodeId="+id+"****processId="+processId+"****processName="+processName+"****desc="+desc,
					scope : this,
					scripts : true
				}
			});
			panels[i] = panel;
		}

		var nodeConfig = new Ext.TabPanel({
			id : "com.digitalchina.nodeConfig",
			height :  420,
			frame : true,
			width : 740,
			items : panels
		});

		
		var win = new Ext.Window({
			title : '�ڵ�����',
			width :750,
			height : 450,
			modal : true,
			buttonAlign:"center",
			 buttons : [
			 	//{
//			 xtype : 'button',
//			 text : "�ύ"
//			 // handler : this.deploysubmit
//			 }, 
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
	getTopbar : function() {
		return ['   ', {
			text : '��ѯ',
			pressed : true,
			handler : this.search,
			scope : this,
			iconCls : 'search'
		}, '   ', {
			text : '�鿴����',
			pressed : true,
			handler : this.view,
			scope : this,
			iconCls : 'search'
		}, {
			text : '��д����֪ͨ',
			pressed : true,
			handler : this.notice,
			scope : this,
			iconCls : 'add'
		}, '    ', {
			text : ' ����',
			pressed : true,
			handler : this.reset,
			scope : this,
			iconCls : 'reset'
		}];
	},
	goBackUrl : function() {
		history.go(-1);
	},

	getButtons : function() {

		var butt = [{
			xtype : 'button',
			style : 'margin:4px 10px 4px 0',
			handler : this.goBackUrl,
			text : '����'
		}];
		return butt;
	},
	// ��ʼ�����г�������������нڵ�
	initComponent : function() {

		
		var sm = new Ext.grid.CheckboxSelectionModel();
		this.store = new Ext.data.JsonStore({
			url : webContext
					+ "/workflow/processconfig.do?methodCall=listAllNode&processName="
					+ this.processName,
			root : 'data',
			fields : ["id", "nodeName", "definitionName", "desc"]
		});
		this.colM = new Ext.grid.ColumnModel([sm,{
			header : "��������",
			dataIndex : "definitionName"
		}, {
			header : "�ڵ�����",
			dataIndex : "nodeName"
		}, {
			header : "�ڵ�����",
			dataIndex : "desc"
		}]);
		this.grid = new Ext.grid.GridPanel({
			id : 'com.node.grid',
			title : "˫��ĳ���ڵ��н��нڵ�����",
			layout : 'table',
			trackMouseOver : false,
			loadMask : true,
			clicksToEdit : 2,
			sm:sm,
			height : 400,
			width : 800,
			cm : this.colM,
			store : this.store
				// autoExpandColumn : 2
		});
		this.store.load();
		this.grid.on("rowdblclick", this.nodeConfig, this);
		this.mybuttons = this.getButtons();
		this.buttons = {
			layout : 'table',
			height : 'auto',
			width : 800,
			style : 'margin:4px 6px 4px 300px',
			// colspan: 4,
			align : 'center',
			defaults : {
				bodyStyle : 'padding:4px'
			},
			// layoutConfig: {columns: 4},
			items : this.mybuttons
		}
		this.items = [this.grid, this.buttons];
		NodeViewPanel.superclass.initComponent.call(this);
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
