/*
 * 源数据面板
 */
PageModelGridPanel = Ext.extend(Ext.Panel, {
	title: '页面面板',
	autoScroll: true,
	columnWidth:.5,
	animate: true,
	containerScroll: true,
    height: 500,
	smtId:"",
	store: "", 
	
	// 查找方法
	searchConfigItem : function() {
		
		var param = this.searchForm.form.getValues(true);		
		/*全用unicode编码*/
		var searchFactor = unicode(this.searchForm.form.findField('searchFactor').getValue());
		var comboxName = this.searchForm.form.findField('id').getValue();
		var param = {
					searchFactor : searchFactor,
					comboxName : comboxName,
					start: 0
					};
		this.store.baseParams=param;
		this.store.removeAll();
		this.store.load();
	},

	reset : function() {
		this.searchForm.form.reset();
	}, 

	initComponent: function(){
		
		var csm = new Ext.grid.CheckboxSelectionModel();
		
		this.store = new Ext.data.JsonStore({ 				
				url: webContext+'/pageModel/pagePanelManage.do?methodCall=showAllPagePanel',
				fields: ['id','name','foreignName','flag'],
			    root:'data',
			    totalProperty : 'rowCount',
				sortInfo: {field: "id", direction: "ASC"}
		}); 
		this.cm = new Ext.grid.ColumnModel([   
		   		    
		    {header: "panel名称", width: 100, sortable: true, dataIndex: 'name'}, 
		    {header: "panel英文名称", width: 100, sortable: true, dataIndex: 'foreignName'},
		    {header: "是否设为分组面板", width: 100, sortable: true, dataIndex: 'flag'},
		    {id:'id', header: "id", width: 100, sortable: true, dataIndex: 'id',hidden:true}	
		]); 
		
		var viewConfig = Ext.apply({
			forceFit : true
		}, this.gridViewConfig);
		
		// 创建分页ToolBar
		this.pageBar = new Ext.PagingToolbar({
				pageSize : 10,
				store : this.store,
				displayInfo : true,
				displayMsg : '当前显示 {0}-{1}条记录 /共{2}条记录',
				emptyMsg : '无显示数据'
		});
		
		this.searchFactor = new Ext.form.TextField({
			name : 'searchFactor',
			fieldLabel : '查询panel名字'
			
		});
		
		this.searchBox = new Ext.form.ComboBox({
			
			store : new Ext.data.JsonStore({
				url: webContext+'/pageModel/pageModelManage.do?methodCall=findModuleListByEntity',
				fields: ['id','name'],
			    root:'data',
				sortInfo: {field: "id", direction: "ASC"}
			}),
			valueField : "id",
			displayField : "name",
			fieldLabel : '所属模板',
            emptyText: '请选择',
			mode : 'remote',
			forceSelection : true,
			hiddenName : "id",
			editable : false,
			triggerAction : 'all', 
			lazyRender: true,
            typeAhead: true,
			allowBlank : true,
			name : "name",
			selectOnFocus: true			
		});
		
		this.searchForm = new Ext.form.FormPanel({
			id : "search",
			layout : 'table',
			height : 40,
			labelWidth : 100,		
			frame : true,	
			layoutConfig : {columns: 4},
			items : [
				{html: "面板名称:&nbsp;" ,cls: 'common-text', style:'width:60;height:20;text-align:left;margin:5 0 5 0;'},
				this.searchFactor,				
				{html: "&nbsp;面板所属模型:&nbsp;" ,cls: 'common-text', style:'width:100;height:20;text-align:left;margin:5 0 5 0;'},
				this.searchBox
				]
		});
		
		this.grid = new Ext.grid.GridPanel({
			
				id :'grid',
		        store: this.store,
		        cm: this.cm,
		        sm: csm,
		        trackMouseOver:false,    
		        loadMask: true,
		        height: 430,
		        y : 40,
				anchor : '0 -40',
		        frame:true,
		        ddGroup: "tgDD",
				enableDragDrop: true,    
		        autoScroll: true,
		        autoEncode : true,
		        clickToEdit: 1,
		        viewConfig : {
					autoFill : true,
					forceFit : true
		        },
		        tbar : ['   ', {
				text : ' 查询',
				pressed : true,
				handler : this.searchConfigItem,
				scope : this,
				iconCls : 'search',
				cls : "x-btn-text-icon"
				}, '&nbsp;|&nbsp;',{
				text : ' 重置',
				pressed : true,
				handler : this.reset,
				scope : this,
				iconCls : 'reset'
				}],
				bbar : this.pageBar
		}); 
		
		var param = {
			'start' : 0
		};
		this.store.load({
			params : param
		});
		
		this.items = [this.searchForm,this.grid];		
		PageModelGridPanel.superclass.initComponent.call(this);	
	}

});