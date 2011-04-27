//模板组件
ConfigItemComponentPanel = Ext.extend(Ext.Panel, {
	id : "tc",
	title : "配置项",
	closable : true,
	viewConfig : {
		autoFill : true,
		forceFit : true
	},
	layout : 'absolute',
	autoScroll : true,
	height : 1000,
	initComponent : function() {

		ConfigItemComponentPanel.superclass.initComponent.call(this);

		this.tree = new TreeDataPanel();
		this.tree.expandAll();
		alert(123);
		this.grid = new ConfigSourceDataPanel();
				
		this.panel = new Ext.Panel({
			y : 0,
			anchor : '0 -0',
			height : 800,
			layout:'column',
			items : [this.tree, this.grid],
			tbar : ['&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;',
					'源数据请选择配置项主表',
					new Ext.form.ComboBox({
						store : new Ext.data.JsonStore({
							url: webContext+'/BoxData.action',
							fields: ['id','name'],
						    root:'data',
							sortInfo: {field: "id", direction: "ASC"}
						}),
						valueField : "id",
						displayField : "name",
		                emptyText: '请选择配置主表',
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
								//将系统主表id传到后台,在后台设置系统主表隶属的模板，同时将系统主表ID放到 request中发到页面
//								这个就是当监听到下拉列表被选择的时候，触发相应的时间
								//id: 系统主表Id
								//var id = record.get('id');						
								this.grid.store.load();
						    },
							scope: this
						}
					})
		   ]
		});
        
		this.add(this.panel);
	}

});