	EventAuditPanel = Ext.extend(Ext.Panel, {
		id : "eventAuditPanel",
		closable : true,
		frame : true,
		height : 'auto',
		viewConfig : {
			autoFill : true,
			forceFit : true
		},
		layoutConfig : {
			columns : 1
		},
		modify : function() {
			var record = this.grid.getSelectionModel().getSelected();
			var records = this.grid.getSelectionModel().getSelections();
			if (!record) {
				Ext.Msg.alert("提示", "请先选择要修改的行！");
				return;
			}
			if (records.length > 1) {
				Ext.Msg.alert("提示", "修改时只能选择一行！");
				return;
			}
			var recordId = record.get("id");
			window.location = webContext
					+ '/user/eventAuditQuery/eventAuditHis.jsp?dataId=' + recordId;
		},
	
		getSearchForm : function() {
			return new Ext.form.FormPanel({
				layout : 'table',
				height : '300',
				width : 'auto', 
				renderTo : Ext.getBody(),
				defaults : {
					bodyStyle : 'padding:4px'
				},
				layoutConfig : {
					columns : 4
				},
				items : this.getMyItems()
			});
		},
	
		getBaseParam : function() {
			return {
				status : 2
			};
		},

		getMyItems : function() {
			var clazz = "com.zsgj.itil.event.entity.Event";
			var da = new DataAction();
			return da.split(da.getElementsForQuery(clazz));
		},
		
		search : function() {
			var param = this.fp.form.getValues(false);
			this.formValue = param;
			this.pageBar.formValue = this.formValue;
			param.start = 0;
			param.status=2;
			//param.applyUser=userId;
			//alert(Ext.encode(param));
			this.store.removeAll();
			this.store.load({
				params : param
			});
		},
	
		reset : function() {
			this.fp.form.reset();
		},
	
		getTopbar : function() {
			return ['   ', {
				text : '查询',
				handler : this.search,
				scope : this,
				iconCls : 'search'
			}, '-', {
				text : ' 清除',
				handler : this.reset,
				scope : this,
				iconCls : 'reset'
			}];
		},
	
		initComponent : function() {
			this.fp = this.getSearchForm();
			var clazz = "com.zsgj.itil.event.entity.Event";
			var da = new DataAction();
			var obj = da.getElementsForHead(clazz);
			var sm = new Ext.grid.CheckboxSelectionModel();
			var columns = new Array();
			var fields = new Array();
			columns[0] = sm;
			for (var i = 0; i < obj.length; i++) {
				var headItem = obj[i];
				var propertyName = headItem.dataIndex;
				var alignStyle = 'left';
	
				var title = headItem.header;
				var isHidden = false;
				var hidden = headItem.hidden;
				if (hidden == 'true') {
					isHidden = true;
				} else {
					isHidden = false;
				}
				if (propertyName == 'id' || propertyName == 'scidType' || propertyName == 'scidData' || propertyName == 'eventName') {
					isHidden = true;
				}
				var columnItem = {
					header : title,
					width : 120,
					dataIndex : propertyName,
					sortable : true,
					hidden : isHidden
				};
				columns[i + 1] = columnItem;
				fields[i] = propertyName;
			}
	
			this.storeMapping = fields;
	
			this.cm = new Ext.grid.ColumnModel(columns);
	
			this.store = da.getJsonStore(clazz);
			this.cm.defaultSortable = true;
	
			 var viewConfig = Ext.apply({
			 forceFit : true
			 }, this.gridViewConfig);
	
			EventAuditPanel.superclass.initComponent.call(this);
			this.pageBar = new Ext.PagingToolbar({
				pageSize : 10,
				store : this.store,
				displayMsg : '当前显示 {0}-{1}条记录 /共{2}条记录',
				emptyMsg : "无显示数据"
			});
			this.topbar = this.getTopbar();
			this.grid = new Ext.grid.GridPanel({
				store : this.store,
				cm : this.cm,
				sm : sm,
				height : 400,
				width : 1216,
				trackMouseOver : false,
				loadMask : true,
				viewConfig : {
					autoScroll : true
				},
				//y : 1000,
				anchor : '0 -0',
				tbar : this.topbar,
				bbar : this.pageBar
			});
			this.grid.on("rowdblclick", this.modify, this);
			this.add(this.fp);
			this.add(this.grid);
	
			var param = this.getBaseParam();
			param.start = 0;
			//param.eventStatus='2_5';
			//param.applyUser=userId;
//			this.pageBar.formValue = param;
			this.store.on('beforeload', function(a) {   
					      Ext.apply(a.baseParams,param);   
					});

			this.store.load({
				params : param
			});
		}
	});
