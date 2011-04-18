OperatePanel = Ext.extend(Ext.Panel, {
	layout : 'table',
	height : 'auto',
	align : 'center',
	foredit : true,
	width : 'auto',
	frame : true,
	// title : this.description,
	autoScroll : true,
	defaults : {
		bodyStyle : 'padding:4px'
	},
	layoutConfig : {
		columns : 1
	},

	back : function() {
		window.location = webContext
				+ "/requireAction_toRequireInfo.action?serviceItemId="
				+ this.serviceItemId;
	},
	getformPanel : function() {
		var da = new DataAction();
		var data = null;
		data = da.getSingleFormPanelElementsForEdit(this.pagePanel,this.dataId);
		for (i = 0; i < data.length; i++) {
			data[i].readOnly = true;
			data[i].hideTrigger = true;
		}

		var infodata = da.split(data);

		this.formPanel = new Ext.form.FormPanel({
			layout : 'table',
			height : 'auto',
			width : 768,
			title : '需求信息',
			frame : true,
			collapsible : true,
			defaults : {
				bodyStyle : 'padding:4px'
			},
			layoutConfig : {
				columns : 4
			},
			items : infodata
		});
		return this.formPanel;
	},

	items : this.items,

	initComponent : function() {
		this.id = this.dataId;
		this.cls = this.reqClass;
		var formPanel = this.getformPanel();
		if (this.status != 0) {
			var histroyForm = new HistroyForm({
				reqId : this.id,
				reqClass : this.cls
			});

			this.tab = new Ext.TabPanel({
				xtype : 'tabpanel',
				activeTab : 0,
				title : '详细信息',
				enableTabScroll : true,
				deferredRender : false,
				frame : true,
				plain : true,
				border : false,
				baseCls : 'x-plain',
				width : 900,
				defaults : {
					autoHeight : true,
					autoHeight : true,
					bodyStyle : 'padding:2px'
				},
				items : [formPanel, histroyForm]
			});

			this.items = [this.tab];
		} else {
			this.items = [formPanel];
		}
		OperatePanel.superclass.initComponent.call(this);
	}
})