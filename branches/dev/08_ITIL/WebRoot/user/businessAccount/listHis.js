var gridStore = new Ext.data.JsonStore({
			url : webContext
					+ '/businessAccountAction_queryFinishedRequireByName.action',
			totalProperty : 'rowCount',
			root : 'data',
			id : 'id',
			fields : ["SpecialRequirement$id", "SpecialRequirement$name", "SpecialRequirement$oldApply", "SpecialRequirement$requireId",
					"SpecialRequirement$applyDate", "SpecialRequirement$applyDept", "SpecialRequirement$costConter", "SpecialRequirement$applyUser",
					"SpecialRequirement$tel", "SpecialRequirement$descn", "SpecialRequirement$requireLevel", "SpecialRequirement$reason",
					"SpecialRequirement$oldSystemLink", "SpecialRequirement$actualStatus", "SpecialRequirement$useStation", "SpecialRequirement$includeAndExpend", "SpecialRequirement$flag", "SpecialRequirement$mobilePhone", "SpecialRequirement$confirmUser"]
});

AccountList = Ext.extend(Ext.Panel, {
	id : "PagePanel",
	//title:this.pageModeltitle,
	closable : true,
	viewConfig : {
		autoFill : true,
		forceFit : true
	},
	layout : 'border',
	
	getPanelJsonStore : function(panelname, header) { 
		var fields = new Array();
		for (i = 0; i < header.length; i++) {
			fields[i] = header[i].dataIndex;
		}

		var store = new Ext.data.JsonStore({
			id : Ext.id(),
			url : webContext + '/businessAccountAction_queryFinishedRequireByName.action', //改成pageQuery测试
			root : "data",
			fields : fields,
			totalProperty : "rowCount",
			remoteSort : false,
			timeout : 8000
		});
		return store;
	},
	
	showInfo : function(){
		
		var record = this.grid.getSelectionModel().getSelected();
		var records = this.grid.getSelectionModel().getSelections();
		if (!record) {
			Ext.Msg.alert("提示", "请先选择要修改的行!");
			return;
		}
		if (records.length > 1) {
			Ext.Msg.alert("提示", "修改时只能选择一行!");
			return;
		}
		var dataId = record.get("SpecialRequirement$id");
//		alert(dataId);
		window.location = webContext+ "/user/businessAccount/requirePlanHisInfo.jsp?dataId="+dataId;
	},
	getSearchForm : function() {
		var formPanel = new Ext.form.FormPanel({
			title : "已完成商务结算信息",
			id : "formPanel",
			region : "north",
			collapsible : true,
			// collapsed : true,
			frame : true,
			labelAlign : "right",
			layout : 'table',
			layoutConfig : {
				columns : 6
			},
			defaults : {
				bodyStyle : 'padding:8px 0px 8px 0px'
			},
			items : [{
				html : "需求名称:&nbsp;",
				cls : 'common-text',
				style : 'width:100;text-align:right'
			}, {
				xtype : 'textfield',
				id : "name",
				fieldLabel : '需求名称',
				height : 0,
				width : 150
			}, {
				html : "需求编号:&nbsp;",
				cls : 'common-text',
				style : 'width:100;text-align:right'
			}, {
				xtype : 'textfield',
				id : "requireId",
				fieldLabel : '需求编号',
				height : 0,
				width : 150
			}],
			keys : [{
				key : [10, 13],
				fn : this.searchProject
			}]
		});
		return formPanel;
	},
	items : this.items,
	initComponent : function(){
		var da = new DataAction();
        var buttonUtil=new ButtonUtil();
        this.mybuttons=buttonUtil.getButtonForModel("businessAccount_list",this);
		this.fp = this.getSearchForm();
		 this.modelTableName="SpecialRequirement";
		var obj = da.getListPanelElementsForHead("panel_businessAccount_list");
		var sm = new Ext.grid.CheckboxSelectionModel();
		var columns = new Array();
		var fields = new Array();
		columns[0] = sm;
		for (var i = 0; i < obj.length; i++) {
			var headItem = obj[i];
			var title = headItem.header;
			var alignStyle = 'left';
			var propertyName = headItem.dataIndex;
			var isHiddenColumn = false;
            var modelTableId = this.modelTableName+"$id";         
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
//		this.store = da.getPanelJsonStore("panel_businessAccount_list",obj); //add header 
		this.store = gridStore;
		this.cm.defaultSortable = true;
		var viewConfig = Ext.apply({
			forceFit : true
		}, this.gridViewConfig);

		this.pageBar = new Ext.PagingToolbar({
			pageSize : 10,
			store : this.store,
			displayInfo : true,
			displayMsg : '当前显示 {0}-{1}条记录 /共{2}条记录',
			emptyMsg : "无显示数据"
		});
//		this.formValue = '';
//		this.pageBar.formValue = this.formValue;
		this.grid = new Ext.grid.GridPanel({
			region : "center",
			store : this.store,
			cm : this.cm,
			sm : sm,
			trackMouseOver : false,
			loadMask : true,
			y : 140,
			height : 200,
			anchor : '0 -35',
			tbar : [this.mybuttons],
			bbar : this.pageBar
		});
		
		this.grid.on("headerdblclick", this.fitWidth, this);
		this.grid.on("rowdblclick", this.showInfo, this);
		var items = new Array();
		items.push(this.fp);
		items.push(this.grid);
		this.items = items;
		this.on("showInfo",this.showInfo,this);
		AccountList.superclass.initComponent.call(this);
		var param = {
			'mehtodCall' : 'query',
			'start' : 0
		};
		
//		this.pageBar.formValue = param;
		this.store.on('beforeload', function(a) {   
					      Ext.apply(a.baseParams,params);   
					});
		this.store.removeAll();
		this.store.load({
			params : param
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





