<#macro creatListPanel  modelTableName clazz panelName modelName item formMapping columnItem fields button>
PagePanel = Ext.extend(Ext.Panel, {
	id : "PagePanel",
	//title:this.pageModeltitle,
	closable : true,
	viewConfig : {
		autoFill : true,
		forceFit : true
	},
	layout : 'border',
	getSearchForm : function() {
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
			items : ${item}
		});
		return this.panel;
	},
	items : this.items,
	initComponent : function(){
		this.fp = this.getSearchForm();
		var sm = new Ext.grid.CheckboxSelectionModel();
		//this.cm = new Ext.grid.ColumnModel(columns);
		 this.store = new Ext.data.JsonStore({ 
                    id: Ext.id(),
                     url : webContext+'/extjs/pageData?method=query&panelname='+"${panelName}",
                    root:"data",
                    fields:${fields},
                    totalProperty:"rowCount",
                    remoteSort:false,             
                   	timeout:8000
                    //sortInfo:{field:'id',direction:'ASC'}
                    });            
		this.store.paramNames.sort = "orderBy";
		this.store.paramNames.dir = "orderType";
		//this.cm.defaultSortable = true;
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
			//cm : this.cm,
			columns:${columnItem},
			sm : sm,
			trackMouseOver : false,
			loadMask : true,
			y : 140,
			height : 200,
			anchor : '0 -35',
			<#if "${model.button}"!="">
			  tbar : ${button},
			</#if>
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
</#macro>
<@creatListPanel 
	modelTableName="${model.modelTableName}"
	modelName="${model.modelName}" 
	panelName="${model.panelname}" 
	clazz="${model.clazz}"
	item="${model.item}"
	formMapping="${model.formMapping}"
	columnItem="${model.columnItem}"
	fields="${model.fields}"
	button="${model.button}"
/> 





