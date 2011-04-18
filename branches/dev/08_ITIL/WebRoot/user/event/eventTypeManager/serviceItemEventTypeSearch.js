PagePanel = Ext.extend(Ext.Panel, {
	id : "PagePanel",
	layout : 'border',
		getSearchForm : function() {
			var serviceItemBySu = new Ext.form.ComboBox({
			id : 'serviceItemId',
			fieldLabel : "������",
			width : 200,
			hiddenName : 'serviceItem',
			displayField : 'name',
			valueField : 'id',
			lazyRender : true,
			emptyText :'��������б���ѡ��...',
			minChars : 50,
			resizable : true,
			triggerAction : 'all',
			selectOnFocus : true,
			store : new Ext.data.JsonStore({
				url : webContext
						+ '/eventAction_findServiceItem.action',
				fields : ['id', 'name'],
				totalProperty : 'rowCount',
				root : 'data',
				id : 'id'
			}),
			pageSize : 10,
			listeners : {
				'beforequery' : function(queryEvent) {
					var param = queryEvent.combo.getRawValue();
					var val = queryEvent.combo.getValue();
					if (queryEvent.query == '') {
						param = '';
					}
					this.store.baseParams={"name" : param,official:1};//���˵�����ʽ�ķ�����
					this.store.load();
					return false;
				}
			}

		});
			this.fromPanel = new Ext.form.FormPanel({
				region : "north",
				layout : 'table',
				frame : true,
				height : 60,
				title : "��ѯ�б�",
				collapsible : true,
				layoutConfig : {
					columns : 4
				},
				items : [{html: "������:",cls: 'common-text',style:'width:110;text-align:right'}
			    	,serviceItemBySu,
			    	{html: "�¼���������:",cls: 'common-text',style:'width:110;text-align:right'}
			    	,{id : 'typeName',
				     xtype : 'textfield',
			       	fieldLabel : '�¼���������',
			       	width : '200'
				}]
			});
			return this.fromPanel;
		},
	initComponent : function() {
		var sm = new Ext.grid.CheckboxSelectionModel();
		this.fromPanel=this.getSearchForm();
		Ext.getCmp("serviceItemId").setValue("");
		this.store = new Ext.data.JsonStore({
			url : webContext + '/eventAction_findAllServiceItemEventType.action',
			fields : ['siCode', 'siName', 'typeName'],
			totalProperty : 'rowCount',
			root : 'data',
			id : 'id'
		});
		this.cm = new Ext.grid.ColumnModel(
		  [sm,
		  	{header : "��������",
			dataIndex : "siCode",
			width : 100   
		     },
			{header : "����������",
			dataIndex : "siName",
			width : 180   
			},
			{header : "�����¼�����",
			dataIndex : "typeName",
			width : 180   
		}]);
		var viewConfig = Ext.apply({
			forceFit : true
		}, this.gridViewConfig);

		this.pageBar = new Ext.PagingToolbar({
			pageSize : 10,
			store : this.store,
			displayInfo : true,
			displayMsg : '��ǰ��ʾ {0}-{1}����¼ /��{2}����¼',
			emptyMsg : "����ʾ����"
		});
		this.grid = new Ext.grid.GridPanel({
			region : "center",
			id : "sietGrid",
			store : this.store,
			cm : this.cm,
			sm:sm,
			tbar : [{
				text : "��ѯ",
				scope : this,
				iconCls : 'search',
				handler : function(){
					if(Ext.getCmp('serviceItemId').getRawValue()==""){
						Ext.getCmp('serviceItemId').setValue("");
					}
					var typeName = Ext.getCmp('typeName').getValue();
					this.store.removeAll();
					var param = {
						start : 0,
						 siId : Ext.getCmp("serviceItemId").getValue(),
						 typeName: Ext.getCmp("typeName").getValue()
					};
					this.store.load({
						params : param
					});
		 		}
			 }],
			bbar : this.pageBar
		});
		this.grid.on("headerdblclick", this.fitWidth, this);
		var item=new Array();
		item.push(this.fromPanel);
		item.push(this.grid);
		this.items = item;
		var params = {
			 start : 0,
			 siId : "",
			 typeName: ""
		};
		this.store.on('beforeload', function(a) {   
					      Ext.apply(a.baseParams,params);   
			});
		this.store.removeAll();
		this.store.load({
			params : params
		});
		PagePanel.superclass.initComponent.call(this);
	},
	fitWidth : function(grid, columnIndex, e) {
		var c = columnIndex;
		var w = grid.view.getHeaderCell(c).firstChild.scrollWidth;
		for (var i = 0, i = grid.store.getCount(); i < l; i++) {
			w = Math.max(w, grid.view.getCell(i, c).firstChild.scrollWidth);
		}
		grid.colModel.setColumnWidth(c, w);
	}
});
