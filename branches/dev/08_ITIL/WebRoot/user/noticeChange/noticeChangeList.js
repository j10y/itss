	PagePanel = Ext.extend(Ext.Panel, {
		id : "PagePanel",
		closable : true,
		viewConfig : {
			autoFill : true,
			forceFit : true
		},
		layout : 'border',
		getSearchForm : function() {
			var name = new Ext.form.TextField({
				name : "title",
				fieldLabel : "�������",
				id : 'title',
				width : 150
			});
			var searchPanel = new Ext.form.FormPanel({
				title:'��������״̬��ѯ',
				id:'searchForm',
				region : "north", 
				layout : 'table',
				height : 60,
				width : 'auto',
				frame : true,
				collapsible : true,
				defaults : {
					bodyStyle : 'padding:4px'
				},
				layoutConfig : {
					columns : 4
				},
				items : [{
					html : "�������:",
					cls : 'common-text',
					width : 80,
					style : 'width:150;text-align:right'
				}, name]
			});
		return searchPanel;
	},
		show  : function() {
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
		var dataId = record.get("NewNotice$id");
		Ext.Ajax.request({
			url : webContext + '/noticeaction_isExistAltering.action',
			params : {
				dataId : dataId
			},
			method : 'post',
			success : function(response) {
				var result = eval('(' + response.responseText + ')');
				var flag = result.flag;
				var noticeId = result.noticeId;
				window.location = webContext
						+ '/user/noticeChange/noticeChangeForm.jsp?dataId='
						+ noticeId + "&flag=" + flag;
			},
			scope : this

		});

	},
	items : this.items,
	initComponent : function(){
		var da = new DataAction();
		var DataHead=da.getListPanelElementsForHead("page_notice_list");
		var sm = new Ext.grid.CheckboxSelectionModel();
		var columns = new Array();
		var fields = new Array();
		columns[0] = sm;
		for (var i = 0; i < DataHead.length; i++) {
			var headItem = DataHead[i];
			var title = headItem.header;
			var alignStyle = 'left';
			var propertyName = headItem.dataIndex;
			var isHiddenColumn = false;         
            var isHidden = headItem.isHidden;
            if(isHidden=='true'){
                    isHiddenColumn = true;  
            }   
			var columnItem = {
				header : title,
				dataIndex : propertyName,
				sortable : true,
				hidden : isHiddenColumn,
				align : alignStyle
			}
			columns[i + 1] = columnItem;
			fields[i] = propertyName;
		}
		this.storeMapping = fields;
		this.cm = new Ext.grid.ColumnModel(columns);
		this.store= da.getPanelJsonStore("page_notice_list", DataHead);
		this.fp = this.getSearchForm();
		this.store.paramNames.sort = "orderBy";
		this.store.paramNames.dir = "orderType";
		this.cm.defaultSortable = true;
		var viewConfig = Ext.apply({
			forceFit : true
		}, this.gridViewConfig);

		var pageBar = new Ext.PagingToolbar({
			pageSize : 10,
			store : this.store,
			displayInfo : true,
			displayMsg : '��ǰ��ʾ {0}-{1}����¼ /��{2}����¼',
			emptyMsg : "����ʾ����"
		});
		this.searchConfig=function(){
			var params = Ext.getCmp("searchForm").getForm().getValues(false);
			var store=Ext.getCmp("page_notice_list").getStore();
	        params.start = 0;  
	        params.auditflag=1;
//	        pageBar.formValue=param;
	        store.on('beforeload', function(a) {   
		      Ext.apply(a.baseParams,params);   
			});
	        store.load({
	            params : params
	        });
	},
		this.grid = new Ext.grid.GridPanel({
			id:"page_notice_list",
			region : "center",
			store : this.store,
			cm : this.cm,
			sm : sm,
			trackMouseOver : false,
			loadMask : true,
			y : 140,
			height : 200,
			anchor : '0 -35',
			tbar : [{
					xtype : 'button',
					style : 'margin:2px 0px 2px 5px',
					handler :this.searchConfig,
					text : '��ѯ',
					iconCls : 'search'
				},'-',{
					xtype : 'button',
					style : 'margin:2px 0px 2px 5px',
					handler :this.show,
					text : '���',
					scope : this,
					iconCls : 'edit'
				},'-',{
					xtype : 'button',
					style : 'margin:2px 0px 2px 5px',
					handler :function(){
						Ext.getCmp("searchForm").getForm().reset();
					},
					text : '����',
					iconCls : 'reset'
		}],
			bbar : pageBar
		});
		
		this.grid.on("headerdblclick", this.fitWidth, this);
		this.grid.on("rowdblclick", this.show, this);
		var items = new Array();
		items.push(this.fp);
		items.push(this.grid);
		this.items = items;
		
		PagePanel.superclass.initComponent.call(this);
		var params = {
			 start : 0,
			auditflag:1
		};
//		pageBar.formValue = param;
		this.store.on('beforeload', function(a) {   
		      Ext.apply(a.baseParams,params);   
		});
		this.store.removeAll();
		this.store.load({
			params : params
		});
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