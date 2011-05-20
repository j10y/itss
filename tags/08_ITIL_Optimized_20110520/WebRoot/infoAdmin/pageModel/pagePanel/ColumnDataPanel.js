/*
 * Column���
 */
ColumnDataPanel = Ext.extend(Ext.grid.GridPanel, {
	title: 'Դ���������ֶ�',
	width: 550,
	autoScroll: true,
	columnWidth:.5,
	animate: true,
	containerScroll: true,
	enableDragDrop: true,   
    ddGroup: "tgDD",  
    height: 320,
	smtId:"",
	store: "", 
	listeners: {
		notifyDrop : function(dd, e, data) {
			var sm = grid.getSelectionModel();   
			var rows = sm.getSelections();   

			var cindex = dd.getDragData(e).rowIndex;   
			if (cindex == undefined || cindex < 0){   
				e.cancel=true;   
				return;   
			}   
			for (i = 0; i < rows.length; i++) {   
			   rowData = ds.getById(rows[i].id);   
				if (!this.copy) {   
					ds.remove(ds.getById(rows[i].id));   	 // remove in datasource.   
					ds.insert(cindex, rowData);              //insert record .   
				}   
			}   
	    }

	},
	
	initComponent: function(){
		
		this.store = new Ext.data.JsonStore({ 
				url: webContext+'/pageModel/pagePanelManage.do?methodCall=getColumnByTable',
				fields: ['stcId','stcName','smtId','smtName','isMainColumn'],
			    root:'data',
				sortInfo: {field: "stcId", direction: "ASC"}
		}),
		this.cm = new Ext.grid.ColumnModel([
		{id:'smtcId', header: "�ֶ�ID",hidden:true, width: 100, sortable: true, dataIndex: 'stcId'},
			{header: "�ֶ�����", width: 150, sortable: true, dataIndex: 'stcName'},
			{header: "����ID", width: 150, hidden:true, sortable: true, dataIndex: 'smtId'},
			{header: "��������", width: 150, sortable: true, dataIndex: 'smtName'},
			{header: "�Ƿ�Ϊ�����ֶ�", width: 150, hidden:true, sortable: true, dataIndex: 'isMainColumn'}	    
		]), 
		
		this.grid = new Ext.grid.EditorGridPanel({
		        store: this.store,
		        cm: this.cm,
		        trackMouseOver:false,    
		        loadMask: true
		});  
//		this.store.load();
				
		ColumnDataPanel.superclass.initComponent.call(this);	
	}

});