PanelComponent = Ext.extend(Ext.Panel, {
	id : "pc",
	closable : true,
	layout : 'table',
	autoScroll : true,
	border : false,
	layoutConfig : {
		columns : 1
	},
		
	initComponent : function() {
		this.grida = new GroupModifyPanel();	
		this.grida.search();
		this.button = new buttonType();
		this.tree = new PanelDataPanel();
		this.tree.expandAll();
		this.grid = new ButtonDataPanel();
		this.panel = new Ext.Panel({
			layout : 'column',
			items : [this.tree,this.grid]
	});
		this.items =[this.grida,this.button,this.panel];
		//this.grid.store.load();
		PanelComponent.superclass.initComponent.call(this);
	}
});