PagePanel = Ext.extend(Ext.Panel, {
	id : "PagePanel",
	//title:this.pageModeltitle,
	closable : true,
	viewConfig : {
		autoFill : true,
		forceFit : true
	},
	layout : 'border',
	lookConfigItem : function(){
	 	var record = this.grid.getSelectionModel().getSelected();
		var records = this.grid.getSelectionModel().getSelections();
			if(!record){
				Ext.Msg.alert("提示","请先选择要查看的记录!");
				return;
			}
			if(records.length>1){
				Ext.Msg.alert("提示","一次只能查看一条记录!");
				return;
			}
		var newId = record.get("ConfigItem$id");
		window.location = webContext+"/user/configQuery/configItemQueryInfo.jsp?dataId="+newId;
	},
	Query:function(){
		var  param = this.fp.form.getValues(false);
		param.methodCall = 'query';
		param.start = 1;
		param.status = 1;

		this.store.load({
			params : param
		});
		
	},
	getSearchForm : function() {
		var da = new DataAction();
		var data = da.getPanelElementsForQuery("businessAccountList"); //da.getElementsForQuery("com.digitalchina.itil.config.entity.ConfigItem");
		var biddata = da.split(data);
		this.panel = new Ext.form.FormPanel({
			region : "north",
			layout : 'table',
			height : 'auto',
			width : 'auto',
			frame : true,
			collapsible : true,
			defaults : {
				bodyStyle : 'padding:4px'
			},
			layoutConfig : {
				columns : 6
			},
			title : "查询列表",
			items : biddata
		});
		return this.panel;
	},
	items : this.items,
	initComponent : function(){
		var da = new DataAction();
        var buttonUtil=new ButtonUtil();
        this.mybuttons=buttonUtil.getButtonForModel("configItemListQuery",this);
		this.fp = this.getSearchForm();
		this.modelTableName="ConfigItem";
		var obj = da.getListPanelElementsForHead("businessAccountList");
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
		this.store = da.getPanelJsonStore("businessAccountList",obj); //add header 
		this.store.paramNames.sort = "orderBy";
		this.store.paramNames.dir = "orderType";
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
		this.grid.on("rowdblclick", this.lookConfigItem, this);
		this.on("lookConfigItem",this.lookConfigItem,this);
		this.on("query",this.Query,this);
		var items = new Array();
		items.push(this.fp);
		items.push(this.grid);
		this.items = items;
		
		PagePanel.superclass.initComponent.call(this);
		var param = {
			'mehtodCall' : 'query',
			'start' : 0
		};
		
//		this.pageBar.formValue = param;
		this.store.on('beforeload', function(a) {   
					      Ext.apply(a.baseParams,param);   
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