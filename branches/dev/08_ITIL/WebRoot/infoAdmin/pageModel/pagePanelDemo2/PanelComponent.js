PanelComponent = Ext.extend(Ext.Panel, {
	id : "pc",
	title : "PanelDemo2",
	closable : true,
	viewConfig : {
		autoFill : true,
		forceFit : true
	},
	layout : 'absolute',
	autoScroll : true,
	height : 2000,
	initComponent : function() {
		PanelComponent.superclass.initComponent.call(this);
		this.tree = new PanelDataPanel();
		this.tree.expandAll();
		//this.grid = new ColumnDataPanel();
		this.panel = new Ext.Panel({
			y : 0,
			anchor : '0 -0',
			layout : 'column',
			height : 800,
			items : [this.tree],//this.grid
			tbar : ['&nbsp;&nbsp;&nbsp;&nbsp;',
					'��ѡ������Դϵͳ����',
					new Ext.form.ComboBox({
						store : new Ext.data.JsonStore({
							url: webContext+'/pageModel/pagePanelManage.do?methodCall=mainTableList&pagePanelId='+ppId,
							fields: ['relatedMainTableId','relatedMainTableName'],
						    root:'data'
						}),
						valueField : "relatedMainTableId",
						displayField : "relatedMainTableName",
		                emptyText: '��ѡ������',
						mode : 'remote',
						forceSelection : true,
						hiddenName : "relatedMainTableId",
						editable : false,
						triggerAction : 'all', 
						lazyRender: true,
			            typeAhead: true,
						allowBlank : true,
						name : "relatedMainTableName",
						selectOnFocus: true,
						listeners: {
							select: function(combo, record, index){
								//id: ϵͳ����Id
								var id = record.get('relatedMainTableId');
								var curppId=ppId;//��ǰ��ѡϵͳ����Id
								//PanelManager.ajaxSaveColumns(curppId,id);
								//this.tree.initComponent();
								//this.grid.store.load({params:{'cursmtId':id}});
								Ext.Ajax.request({
									url : webContext+'/pageModel/pagePanelManage.do?methodCall=saveColumnsFormSystemMainTable',
									params : {
									ppId : ppId,
									cursmtId:id
									}
								});
						    },
							scope: this
						}
					})
		   ]
	});

		this.add(this.panel);

	}

});