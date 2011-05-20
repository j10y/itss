RuleConfigUnit = Ext.extend(Ext.Panel, {
	id : "RuleConfigUnit",
	height : 400,
	align : 'center',
	foredit : true,
	width : 600,
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
			responseText = clearReturn(responseText);
			var data = Ext.decode(responseText);
			var nodeName = data.nodeName;
			return nodeName
		} else {
			return 'no result';
		}
	},
	save : function() {
		var orderAction = new OrderAction();
		var bsParam = this.rulePanel.form.getValues(false);
		var formParam = Ext.encode(bsParam);
		orderAction.saveRuleConfigUnitRecord(formParam,
				this.virtualDefinitionInfoId, this.nodeId, this.desc);
	},

	// ��ʼ��
	initComponent : function() {// �������涨��ı�����ȫ�ֱ���
		RuleConfigUnit.superclass.initComponent.call(this);// �ø����ȳ�ʼ��
		product = '';
		removeIds = '';
		var da = new DataAction();
		var orderAction = new OrderAction();
		var data = orderAction.getRuleData(this.virtualDefinitionInfoId,
				this.nodeId);
		var ids = data.id;
		var ruleName = data.ruleName;
		if (ruleName == null || ruleName == 'null') {
			ruleName = '';
		}  
		var ruleFileName = data.ruleFileName;
		
   
		this.rulePanel = new Ext.form.FormPanel({
			id : 'com.rulePanel',
			// layout : '',
			height : 340,
			width : 730,
			frame : true,
			defaults : {
				bodyStyle : 'padding:4px'
			},
			layoutConfig : {
				columns : 4
			},
			title : "��������(<font color=red>һ���ڵ�����һ������</font>)",
			items : [
				{
				fieldLabel : '��������',
				xtype : 'textfield',
				id : 'ruleName',
				name : 'ruleName',
				labelWidth : 60,
				value : ruleName,
				style : '',
				// height:50,
				width : '150',
				allowBlank : true,
				validator : '',
				vtype : ''
			}, {
				fieldLabel : '�����ļ�����',
				xtype : 'textfield',
				id : 'ruleFileName',
				name : 'ruleFileName',
				labelWidth : 60,
				style : '',
				// height:50,
				width : '150',
				value : ruleFileName,
				allowBlank : true,
				validator : '',
				vtype : '',
				readOnly : true
			}, {
				xtype : 'hidden',
				id : 'id',
				name : 'id',
				width : 'null',
				style : '',
				value : ids,
				fieldLabel : '�Զ����'
			}],
			buttons : [{
				handler : this.save,
				scope : this,
				iconCls : 'save',
				text : '����'
			}]
		})
		this.add(this.rulePanel);

	}
});
