/*
 * 源数据面板
 */
ConfigSourceDataPanel = Ext.extend(Ext.grid.GridPanel, {
	title: '配置项源数据',
	width: 550,
	autoScroll: true,
	columnWidth:.5,//表示列宽为一半
	animate: true,
	containerScroll: true,
	enableDragDrop: true,   
    ddGroup: "tgDD",  
    height: 320,
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
				
		ConfigSourceDataPanel.superclass.initComponent.call(this);	
	}

});