PagePanel = Ext.extend(Ext.Panel, {
	id : "PagePanel",
	//title:this.pageModeltitle,
	closable : true,
	viewConfig : {
		autoFill : true,
		forceFit : true
	},
	layout : 'border',
	//begin
	getSearchFormQueryParams: function(){
		return [
			{html: "&nbsp;主表名称:",cls: 'common-text',style:'width:90;text-align:center'},//这里是一个对象	
			{
             	xtype: 'textfield',
             	name: 'tableName',
             	width: 200
            },
            {html: "&nbsp;&nbsp;&nbsp;&nbsp;隶属部门:",cls: 'common-text',style:'width:120;text-align:center'},	
			{
             	xtype: 'textfield',
             	name: 'department',
             	width:200
            }
  	     ];
	},
	//end
	getSearchForm : function() {
		var da = new DataAction();
		var data = da.getPanelElementsForQuery("panel_tableCodeBuilder"); //da.getElementsForQuery("com.zsgj.info.appframework.metadata.entity.SystemMainTableIdBuilder");
	   
		//var biddata = da.split(data);
	
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
			items : this.getSearchFormQueryParams()
		});
		return this.panel;
	},
	createSearchButton : function(btnName,link,imageUrl){
		var button=new Ext.Button({
			text:btnName,
			pressed:true,
			iconCls:'search',
			//scope : scope,
			handler:function(){
				var param = this.panel.form.getValues(false);
				param.methodCall = 'query';
                param.start = 1;                       
				this.formValue = param;
				this.pageBar.formValue = this.formValue;
				this.store.removeAll();
				this.store.load({
					params : param
				});
			}
		});
		return button;
	},
	getPanelJsonStore:function(panelname){ 
         // alert(girdId);
        var url = webContext+'/extjs/pageView?method=head&panelname='+panelname;
        var data = this.ajaxGetData(url); 
        var dataStr = Ext.util.JSON.encode(data);
        var fields = new Array();
        for(i=0;i<data.length;i++){
         	  fields[i] = data[i].dataIndex;
        }

        var store = new Ext.data.JsonStore({ 
            id: Ext.id(),
            url : webContext+'/extjs/pageData?method=query&panelname='+panelname, //改成pageQuery测试
            root:"data",
            fields:fields,
                      totalProperty:"rowCount",
                      remoteSort:false,             
                      timeout:8000
                      //sortInfo:{field:'id',direction:'ASC'}
            });
            return store;
    },
    toAdd2: function(){ alert("dd");
		window.location.href=webContext+'/infoAdmin/userExpand/tableCodeBuilderForm.jsp';
	},
	items : this.items,
	initComponent : function(){
		var da = new DataAction();
        var buttonUtil=new ButtonUtil();
        this.mybuttons=buttonUtil.getButtonForModel("page_tableCodeBuilder",this);
		this.fp = this.getSearchForm();
		this.modelTableName="tSystemMainTableIdBuilder";
		var obj = da.getListPanelElementsForHead("panel_tableCodeBuilder");
		
		var sm = new Ext.grid.CheckboxSelectionModel();
		//var fields = new Array();
		var fields = ['tSystemMainTableIdBuilder$userName', 
							 'tSystemMainTableIdBuilder$department', 
							 'tSystemMainTableIdBuilder$prefix',
							 'tSystemMainTableIdBuilder$length', 
							 'tSystemMainTableIdBuilder$ruleFileName'];
		//var columns = new Array();
		/**columns[0] = sm;
		
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
			alert(propertyName);
			columns[i + 1] = columnItem;
			fields[i] = propertyName;
		}**/
		this.storeMapping = fields;
		
		this.cm = new Ext.grid.ColumnModel([sm,{
			header : "系统主表",
			dataIndex : "tSystemMainTableIdBuilder$systemMainTable",
			sortable : true,
			hidden : false,
			align : 'left'
		}, {
			header : "隶属部门",
			dataIndex : "tSystemMainTableIdBuilder$department",
			sortable : true,
			hidden : false,
			align : 'left'
		}, {
			header : "编号前缀",
			dataIndex : "tSystemMainTableIdBuilder$prefix",
			sortable : true,
			hidden : false,
			align : 'left'
		}, {
			header : "编号总长度",
			dataIndex : "tSystemMainTableIdBuilder$length",
			sortable : true,
			hidden : false,
			align : 'left'
		}, {
			header : "规则文件",
			dataIndex : "tSystemMainTableIdBuilder$ruleFileName",
			sortable : true,
			hidden : false,
			align : 'left'
		}]);
		this.store = da.getPanelJsonStore("panel_tableCodeBuilder",obj); //add header 
		//begin
		/**
		var store = new Ext.data.JsonStore({ 
            id: Ext.id(),
            url : webContext+'/extjs/pageData?method=query&panelname='+panelname, //改成pageQuery测试
            root:"data",
            fields:fields,
                      totalProperty:"rowCount",
                      remoteSort:false,             
                      timeout:8000
                      //sortInfo:{field:'id',direction:'ASC'}
        });**/
		//end
		this.store.paramNames.sort = "orderBy";
		this.store.paramNames.dir = "orderType";
		this.cm.defaultSortable = true;
		var viewConfig = Ext.apply({
			forceFit : true
		}, this.gridViewConfig);

		this.pageBar = new Ext.PagingToolbarExt({
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
			//tbar : [this.mybuttons], //old
			//begin
			tbar : [
				{
					xtype : 'button',
					style : 'margin:4px 10px 4px 10px',
					text : '查询',
					scope : this,
					pressed : true,
					iconCls : 'search',
					handler:function(){
						var param = this.panel.form.getValues(false);
						param.methodCall = 'query';
		                param.start = 1;                       
						this.formValue = param;
						this.pageBar.formValue = this.formValue;
						this.store.removeAll();
						this.store.load({
							params : param
						});
					}
				},
				//this.createSearch("query","link","imgurl"),
				{
				xtype : 'button',
				style : 'margin:4px 10px 4px 10px',
				text : '增加',
				scope : this,
				pressed : true,
				iconCls : 'add',
				handler : function(){
						window.location.href=webContext+'/infoAdmin/userExpand/tableCodeBuilderForm.jsp';
					}
				}, 
				{
				xtype : 'button',
				style : 'margin:4px 10px 4px 10px',
				text : '修改',
				scope : this,
				pressed : true,
				iconCls : 'edit',
				handler : this.modify
				},
				{
				xtype : 'button',
				style : 'margin:4px 10px 4px 10px',
				text : '删除',
				scope : this,
				pressed : true,
				iconCls : 'delete',
				handler : this.deleteVirtualProcess
				},
				new Ext.Toolbar.TextItem('<font color=red>双击某条记录</font>')
				
			],
			//end		
			
			bbar : this.pageBar
		});
		
		this.grid.on("headerdblclick", this.fitWidth, this);
		this.grid.on("rowdblclick", this.modify, this);
		var items = new Array();
		items.push(this.fp);
		items.push(this.grid);
		this.items = items;
		
		PagePanel.superclass.initComponent.call(this);
		var param = {
			'mehtodCall' : 'query',
			'start' : 1
		};
		
		this.pageBar.formValue = param;
		
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





