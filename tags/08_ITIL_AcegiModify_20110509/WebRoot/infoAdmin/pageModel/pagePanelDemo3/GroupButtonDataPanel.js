ButtonDataPanel = Ext.extend(Ext.Panel, {
	    
    title: '面板',
	width: 'auto',
	autoScroll: true,
	columnWidth:.5,
	animate: true,
	containerScroll: true,   
    height: 500,
    layoutConfig : {
    	columns : 1
    },
    
	smtId:"",
	store: "",
	
	// 查找方法
	searchConfigItem : function() {
		
		var param = this.searchForm.form.getValues(true);		
		/*全用unicode编码*/
		var searchFactor = unicode(this.searchForm.form.findField('searchFactor').getValue());
		var param = {
					searchFactor : searchFactor,
					start: 0
					};
		this.store.baseParams=param
		this.store.removeAll();
		this.store.load({
			params : param
		})
	},

	reset : function() {
		this.searchForm.form.reset();
	},
	
	initComponent: function(){
		
		
		var csm = new Ext.grid.CheckboxSelectionModel();		

		this.store = new Ext.data.JsonStore({ 				
				url: webContext+'/pageModel/pagePanelManage.do?methodCall=showAllPagePanel',
				fields: ['id','name','flag'],
			    root:'data',
			    totalProperty : 'rowCount',
				sortInfo: {field: "id", direction: "ASC"}
		}); 

		this.cm = new Ext.grid.ColumnModel([   
		   		    
		    {header: "所属panel名称", width: 30, sortable: true, dataIndex: 'name'}, 
		    {header: "是否设为分组面板", width: 30, sortable: true, dataIndex: 'flag'},
		    {id:'id', header: "id", width: 30, sortable: true, dataIndex: 'id'}	
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
		
		this.searchForm = new Ext.form.FormPanel({
			id : "search",
			height : 40,
			labelWidth : 100,
			labelAlign : "right",
			frame : true,
			defaults : {xtype:"textfield" ,width:180},
			items : [
				{name:"searchFactor" , fieldLabel:"查询panel名字"}
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
		ButtonDataPanel.superclass.initComponent.call(this);	
	}

});