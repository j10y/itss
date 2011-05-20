PagePanel = Ext.extend(Ext.Panel, {
	id : "PagePanel",
	//title:this.pageModeltitle,
	closable : true,
	viewConfig : {
		autoFill : true,
		forceFit : true
	},
	layout : 'border',
 	searchInPage : function() {
                var param = this.fp.form.getValues(false);
              //  alert(Ext.encode(param)); //**** query params ***
                param.methodCall = 'query';
                param.start = 0;  
                param.rootFlag=1;
                param.status=1;
                this.formValue = param;         
                this.pageBar.formValue = this.formValue;
				this.store.baseParams=param;
				this.store.load();
      },
  addForModelByPage:function(){
      	
		window.location=webContext+"/user/serviceIssue/sciRelationShipIssueList.jsp";
      },
      
  removeServiceCatelogue : function(){			  
			    var record = this.grid.getSelectionModel().getSelected();
				var records = this.grid.getSelectionModel().getSelections();
					if(records<1){
						Ext.Msg.alert("提示","请先选择要删除的记录!");
						return;
					}
					var ids = new Array();
				for(var i=0;i<records.length;i++){
					ids[i] = records[i].get("ServiceCatalogue$id");
				}
				var gridStore = this.store;
				var firm  =Ext.Msg.confirm('提示', '您确定要进行删除操作吗?', function(button) {
			if (button == 'yes') {
				Ext.Ajax.request({
					
					url : webContext+'/sciRelationShip_removeServiceCatalogue.action',
					//url : webContext+'/extjs/pageData?method=remove',
					params : {
						dataId : ids,
						model: this.model
					},
					success : function(response, options) {
						Ext.MessageBox.alert("提示","删除成功",function(){
							gridStore.reload();
							
						});
						//window.location= webContext+"/user/service/serviceCatalogueList.jsp";
					},
					failure : function(response, options) {
						Ext.MessageBox.alert("提示","删除失败");
					}
				}, this);
					}
		}, this);
	},
  lookInPage:function(){
  		var record = this.grid.getSelectionModel().getSelected();
  		 var records = this.grid.getSelectionModel().getSelections();
  		if(!record){
				Ext.Msg.alert("提示","请先选择要查看的行!");
				return;
			}
		if(records.length>1){
				Ext.Msg.alert("提示","查看时只能选择一行!");
				return;
			}
  		var dataId = record.get("ServiceCatalogue$id");
  		//alert(dataId+"data");
  		window.location= webContext+"/sciRelationShip_getRootRelationShipData.action?rootCataId="+dataId+"&type=issue";
  		//window.location=webContext+"/user/serviceIssue/sciRelationShipIssueForm.jsp?dataId="+dataId+'&type=issue';
  },
	getSearchForm : function() {
		var da = new DataAction();
		var data = da.getPanelElementsForQuery("page_ServiceCatalogue"); //da.getElementsForQuery("com.zsgj.itil.service.entity.ServiceCatalogue");
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
        this.mybuttons=buttonUtil.getButtonForModel("page_ServiceCatalogueIssue",this);
		this.fp = this.getSearchForm();
		 this.modelTableName="ServiceCatalogue";
		var obj = da.getListPanelElementsForHead("page_ServiceCatalogue");
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
		this.store = da.getPanelJsonStore("page_ServiceCatalogue",obj); //add header 
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
		this.formValue = '';
		this.pageBar.formValue = this.formValue;
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
		this.grid.on("rowdblclick", this.lookInPage, this);
		this.on("searchInPage",this.searchInPage,this);
		this.on("lookInPage",this.lookInPage,this);
		this.on("addForModelByPage",this.addForModelByPage,this);
		this.on("removeServiceCatelogue",this.removeServiceCatelogue,this);
		var items = new Array();
		items.push(this.fp);
		items.push(this.grid);
		this.items = items;
		
		PagePanel.superclass.initComponent.call(this);
		var param = {
			'mehtodCall' : 'query',
			'start' : 0,
			'rootFlag' :1,
			'status':1
		};
		
		this.pageBar.formValue = param;
		this.store.baseParams=param;
		this.store.load();
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





