//模板组件
PageModelComponentPanel = Ext.extend(Ext.Panel, {
	id : "mb",
	title : "PageModel",
	header : false,
	closable : true,
	layout : 'table',
	autoScroll : true,
	border : false,
	width: 'auto',
	layoutConfig : {
		columns : 1
	},
	loadData : function(){
		
    	var param = {'modelName':pageModuleName};
    	Ext.getCmp("button").store.load({
    		params: param
    	}); 		
    },	
	initComponent : function() { 
		this.grida = new GridModifyPanel();	
		this.grida.search();
		//this.buttonPanel = new ButtonTypePanel();	
		this.buttonPanel = new buttonType();	
		this.tree = new PagteModelTreePanel();
		this.tree.expandAll();				
		this.grid = new PageModelGridPanel();	
		this.panel = new Ext.Panel({
			layout : 'column',
			items : [this.tree, this.grid],
			tbar : ['&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;',
					'请选择面板所属模块',
					new Ext.form.ComboBox({
						store : new Ext.data.JsonStore({
							url: webContext+'/pageModel/pageModelManage.do?methodCall=findModuleListByEntity',
							fields: ['id','name'],
						    root:'data',
							sortInfo: {field: "id", direction: "ASC"}
						}),
						valueField : "id",
						displayField : "name",
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
						selectOnFocus: true,
						listeners: {
							select: function(combo, record, index){
								//将combobox当中或得的数据往后台发送。
								//这个就是当监听到下拉列表被选择的时候，触发相应的事件
								var id = record.get('id');	
								
								var param = {
									'pgId':id,
									'start' : 1
								};
								
								Ext.getCmp('pageBar2').formValue = param;
								this.grid.store.load({params:param});
						    },  						    
							scope: this
						}
					})
		   ]
		});
	
		this.items =[this.grida,this.buttonPanel,this.panel];
		PageModelComponentPanel.superclass.initComponent.call(this);
	}

});