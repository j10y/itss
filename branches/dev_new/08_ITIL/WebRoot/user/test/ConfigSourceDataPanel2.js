/*
 * 源数据面板
 */
ConfigSourceDataPanel2 = Ext.extend(Ext.grid.GridPanel, {
	title: '扩展字段设置',
	width: 1200,
	autoScroll: true,
	animate: true,
	containerScroll: true,
	enableDragDrop: true,   
    ddGroup: "tgDD",  
    //height: 320,
	smtId:"",
	store: "", 
	
	initComponent: function(){
		
		this.store = new Ext.data.JsonStore({ 				
				url: webContext+'/GridSourceData.action',
				fields: ['id','name'],
			    root:'data',
				sortInfo: {field: "id", direction: "ASC"}
		}),
		this.cm = new Ext.grid.ColumnModel([   
		    {header: "名称", width: 150, sortable: true, dataIndex: 'name'}, 
		    {id:'id', header: "id", width: 100, sortable: true, dataIndex: 'id'}		    
		]), 
		
		this.grid = new Ext.grid.EditorGridPanel({
		        store: this.store,
		        cm: this.cm,
		        trackMouseOver:false,    
		        loadMask: true
		});  
				
		ConfigSourceDataPanel2.superclass.initComponent.call(this);	
	}

});