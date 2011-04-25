PanelComponent = Ext.extend(Ext.Panel, {
	id : "pc",
	width: window.screen.availWidth-224,
	closable : true,
	viewConfig : {
		autoFill : true,
		forceFit : true
	},
	layout : 'absolute',
	autoScroll : true,
	border : false,
	height : 500,
	initComponent : function() {
		PanelComponent.superclass.initComponent.call(this);
		this.tree = new PanelDataPanel();
		this.tree.expandAll();
		this.grid = new ButtonDataPanel();
		this.panel = new Ext.Panel({
			y : 0,
			anchor : '0 -0',
			layout : 'column',
			height : 800,
			items : [this.tree,this.grid]
	});
		this.add(this.panel);
	}
});