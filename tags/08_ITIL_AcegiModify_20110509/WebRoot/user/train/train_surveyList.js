PagePanel = Ext.extend(Ext.Panel, {
	id : "PagePanel",
	// title:this.pageModeltitle,
	closable : true,
	viewConfig : {
		autoFill : true,
		forceFit : true
	},
	layout : 'border',
	getSearchForm : function() {
		var da = new DataAction();
		var data = da.getPanelElementsForQuery("page_trainSurvey"); // da.getElementsForQuery("com.zsgj.itil.train.entity.Survey");
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
			title : "试卷列表",
			items : biddata
		});
		return this.panel;
	},
	removeSurvey : function() {
		var store = this.store;
		var record = this.grid.getSelectionModel().getSelected();
		var records = this.grid.getSelectionModel().getSelections();
		// alert(Ext.encode(record.data));
		var clazz = "com.zsgj.itil.train.entity.Survey";
		if (!record) {
			Ext.Msg.alert("提示", "请先选择要删除的行!");
			return;
		}
		var da = new DataAction();
		var ids = "";
		var firm = Ext.Msg.confirm('提示', '您确定要进行删除操作吗?', function(button) {
			if (button == 'yes') {
				for (var i = 0; i < records.length; i++) {

					var scId = records[i].get("TrainSurvey$id");
					Ext.Ajax.request({

						url : webContext + '/trainPlan_removeSurvey.action',
						params : {
							dataId : scId,
							clazz : clazz
						},
						success : function(response, options) {
							Ext.MessageBox.alert("删除成功", "", function() {
								store.reload();
									// window.location=
									// webContext+"/user/train/train_instructorList.jsp";
								});

						},
						failure : function(response, options) {
							Ext.MessageBox.alert("删除失败");
						}
					}, this);
				}
			}
		}, this);

	},
	items : this.items,
	initComponent : function() {
		var da = new DataAction();
		var buttonUtil = new ButtonUtil();
		this.mybuttons = buttonUtil.getButtonForModel("page_train_survey_list",
				this);
		this.fp = this.getSearchForm();
		this.modelTableName = "TrainSurvey";
		var obj = da.getListPanelElementsForHead("page_trainSurvey");
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
			var modelTableId = this.modelTableName + "$id";
			var isHidden = headItem.isHidden;
			if (isHidden == 'true') {
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
		this.store = da.getPanelJsonStore("page_trainSurvey", obj); // add
																	// header
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
		this.grid.on("rowdblclick", this.modify, this);
		this.on("removeSurvey", this.removeSurvey, this);
		var items = new Array();
		items.push(this.fp);
		items.push(this.grid);
		this.items = items;

		PagePanel.superclass.initComponent.call(this);
		var params = {
			'mehtodCall' : 'query',
			'start' : 0
		};

//		this.pageBar.formValue = param;
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
