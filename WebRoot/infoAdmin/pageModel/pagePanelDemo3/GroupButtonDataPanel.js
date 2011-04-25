ButtonDataPanel = Ext.extend(Ext.Panel, {
	    
    title: '���',
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
	
	// ���ҷ���
	searchConfigItem : function() {
		
		var param = this.searchForm.form.getValues(true);		
		/*ȫ��unicode����*/
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
		   		    
		    {header: "����panel����", width: 30, sortable: true, dataIndex: 'name'}, 
		    {header: "�Ƿ���Ϊ�������", width: 30, sortable: true, dataIndex: 'flag'},
		    {id:'id', header: "id", width: 30, sortable: true, dataIndex: 'id'}	
		]);
		
	    var viewConfig = Ext.apply({
			forceFit : true
		}, this.gridViewConfig);
		
		// ������ҳToolBar
		this.pageBar = new Ext.PagingToolbar({
				pageSize : 10,
				store : this.store,
				displayInfo : true,
				displayMsg : '��ǰ��ʾ {0}-{1}����¼ /��{2}����¼',
				emptyMsg : '����ʾ����'
		});
		
		this.searchForm = new Ext.form.FormPanel({
			id : "search",
			height : 40,
			labelWidth : 100,
			labelAlign : "right",
			frame : true,
			defaults : {xtype:"textfield" ,width:180},
			items : [
				{name:"searchFactor" , fieldLabel:"��ѯpanel����"}
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
				text : ' ��ѯ',
				pressed : true,
				handler : this.searchConfigItem,
				scope : this,
				iconCls : 'search',
				cls : "x-btn-text-icon"
				}, '&nbsp;|&nbsp;',{
				text : ' ����',
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