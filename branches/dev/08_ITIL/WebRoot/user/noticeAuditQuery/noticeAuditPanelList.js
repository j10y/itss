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
				fieldLabel : "公告标题",
				id : 'title',
				width : 150
			});
			var searchPanel = new Ext.form.FormPanel({
				title:'公告审批状态查询',
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
					html : "公告标题:",
					cls : 'common-text',
					width : 80,
					style : 'width:150;text-align:right'
				}, name]
			});
		return searchPanel;
	},
		show : function() {
			var record = this.grid.getSelectionModel().getSelected();
			var records = this.grid.getSelectionModel().getSelections();
			var clazz = "com.digitalchina.itil.notice.entity.NoticeAuditHis";
			if (!record) {
				Ext.Msg.alert("提示", "请先选择要修改的行!");
				return;
			}
			if (records.length > 1) {
				Ext.Msg.alert("提示", "修改时只能选择一行!");
				return;
			}
			var recordId = record.get("NewNotice$id");
			var pid = "";
			var conn = Ext.lib.Ajax.getConnectionObject().conn;
						conn.open("POST",  webContext+'/extjs/dataAction?method=query&clazz='+clazz+"&methodCall=query"+"&newNotice="+recordId+"&isAsc=false"+"&start="+1,false);
						conn.send(null);	
					if(conn.status=='200'){
						var result = Ext.decode(conn.responseText);
						pid = result.data[0].processId;
						}
			window.location = webContext
					+ '/user/noticeAuditQuery/noticeAuditHis.jsp?dataId=' + recordId+"&processId="+pid;
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
			displayMsg : '当前显示 {0}-{1}条记录 /共{2}条记录',
			emptyMsg : "无显示数据"
		});
		this.searchConfig=function(){
			var params = Ext.getCmp("searchForm").getForm().getValues(false);
			var store=Ext.getCmp("page_notice_list").getStore();
	        params.start = 0;  
	        params.auditflag=2;
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
					text : '查询',
					iconCls : 'search'
				},'-',{
					xtype : 'button',
					style : 'margin:2px 0px 2px 5px',
					handler :function(){
						Ext.getCmp("searchForm").getForm().reset();
					},
					text : '重置',
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
			auditflag:2
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