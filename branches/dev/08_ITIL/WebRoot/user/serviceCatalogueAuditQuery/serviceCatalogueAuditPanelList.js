	ServiceCatalogueAuditPanel = Ext.extend(Ext.Panel, {
		id : "serviceCatalogueAuditPanel",
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
			//alert("modify");
			var record = this.grid.getSelectionModel().getSelected();
			var records = this.grid.getSelectionModel().getSelections();
			var clazz = "com.digitalchina.itil.service.entity.ServiceCatalogueAuditHis";
			if (!record) {
				Ext.Msg.alert("提示", "请先选择要查看的行!");
				return;
			}
			if (records.length > 1) {
				Ext.Msg.alert("提示", "查看时只能选择一行!");
				return;
			}
			var recordId = record.get("id");
			
			var pid = "";
			var conn = Ext.lib.Ajax.getConnectionObject().conn;
						conn.open("POST",  webContext+'/extjs/dataAction?method=query&clazz='+clazz+"&methodCall=query"+"&serviceCatalogue="+recordId+"&isAsc=false"+"&start="+1,false);
						conn.send(null);	
					if(conn.status=='200'){
						var result = Ext.decode(conn.responseText);
						pid = result.data[0].processId;
						//alert(tableName+"-----tableName");
						}
						
			window.location = webContext
					+ '/user/serviceCatalogueAuditQuery/serviceCatalogueAuditHis.jsp?dataId=' + recordId+"&processId="+pid;
		},
	
		getSearchForm : function() {
			return new SearchForm({
				getMyItems : function() {
					var clazz = "com.digitalchina.itil.service.entity.ServiceCatalogue";
					var da = new DataAction();
					return da.split(da.getElementsForQuery(clazz));
				}
			});
		},
	
		getBaseParam : function() {
			// alert("getBaseParam");
			return {
				status : 2
				 
				//alterFlag : 0
			};
		},
	
		search : function() {
			// alert("search");
			var param = this.fp.form.getValues(false);
			this.formValue = param;
			this.pageBar.formValue = this.formValue;
			param.start = 0;
			param.status=2;
			param.rootFlag=1;
			//param.applyUser=userId;
			//alert(Ext.encode(param));
			this.store.baseParams = param;
			this.store.load();
		},
	
		reset : function() {
			this.fp.form.reset();
		},
	
		getTopbar : function() {
			// alert("getTopbar");
			return ['   ', {
				text : '查询',
				pressed : true,
				handler : this.search,
				scope : this,
				iconCls : 'search'
			}, '    ', {
				text : ' 查看',
				pressed : true,
				handler : this.modify,
				scope : this,
				iconCls : 'look'
			},'   ',{
				text : ' 重置',
				pressed : true,
				handler : this.reset,
				scope : this,
				iconCls : 'reset'
			}];
		},
	
		initComponent : function() {
			// alert("initComponent");
			this.fp = this.getSearchForm();
			// alert("this.fp: " + this.fp);
			var clazz = "com.digitalchina.itil.service.entity.ServiceCatalogue";
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
				// /////////////////////////
				var hidden = headItem.hidden;
				if (hidden == 'true') {
					isHidden = true;
				} else {
					isHidden = false;
				}
				// /////////////////////////
				if (propertyName == 'id') {
					isHidden = true;
				}
				var columnItem = {
					header : title,
					width : 120,
					dataIndex : propertyName,
					sortable : true,
					hidden : isHidden
						// align : alignStyle
				};
				columns[i + 1] = columnItem;
				fields[i] = propertyName;
			}
	
			this.storeMapping = fields;
	
			this.cm = new Ext.grid.ColumnModel(columns);
	
//			this.store = da.getJsonStore(clazz);createUser
			this.store = da.getJsonStoreForUser(clazz,"createUser");
			this.cm.defaultSortable = true;
	
			// var viewConfig = Ext.apply({
			// forceFit : true
			// }, this.gridViewConfig);
	
			ServiceCatalogueAuditPanel.superclass.initComponent.call(this);
			this.pageBar = new Ext.PagingToolbar({
				pageSize : 10,
				store : this.store,
				displayInfo : true,
				displayMsg : '当前显示 {0}-{1}条记录 /共{2}条记录',
				emptyMsg : "无显示数据"
			});
			this.formValue = '';
			// this.pageBar.formValue = this.formValue;
			this.topbar = this.getTopbar();
			this.grid = new Ext.grid.GridPanel({
				store : this.store,
				cm : this.cm,
				sm : sm,
				height : 520,
				trackMouseOver : false,
				loadMask : true,
				y : 40,
				anchor : '0 -38',
				tbar : this.topbar,
				bbar : this.pageBar
			});
			this.grid.on("rowdblclick", this.modify, this);
			this.add(this.fp);
			this.add(this.grid);
	
			var param = this.getBaseParam();
			param.start = 0;
			 param.rootFlag=1;
			//param.finishFlag=2;
			//param.applyUser=userId;
			this.pageBar.formValue = param;
			this.store.baseParams = param;
			this.store.load();
		}
	});
