PanelComponent = Ext.extend(Ext.Panel, {
	id : "pc",
	closable : true,
//	viewConfig : {
//		autoFill : true,
//		forceFit : true
//	},
	layout : 'table',
//	autoScroll : true,
	border : false,
	height : 800,
	layoutConfig : {
		columns : 1
	},
	initComponent : function() {
		//PanelComponent.superclass.initComponent.call(this);
		this.grida = new GridModifyPanel();	
		this.grida.search();
		this.tree = new PanelDataPanel();
		this.tree.expandAll();
		this.grid = new ButtonDataPanel();
		this.panel = new Ext.Panel({
			y : 0,
			anchor : '0 -0',
//			layout : 'table',
			height : 800,
			layout:'column',
			items : [this.tree,this.grid]
	});
		//this.add(this.grida,this.panel);
		this.items =[this.grida,this.panel];
		PanelComponent.superclass.initComponent.call(this);
	}
	
});