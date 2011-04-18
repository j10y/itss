
ColumnTreePanel = Ext.extend(Ext.tree.ColumnTree, {
	autoWidth: true,
	rootVisible:true,
//	autoScroll: true,
	columnWidth:.5,
	containerScroll: true,
	enableDD: false,
	frame: true,
	lines: true,
	height: 460,
	
	initComponent: function(){
		 
		//创建此模板时需要传入参数cm:每一列的格式定义
		this.columns = this.cm;
		
		//创建此模板时需要传入参数dataUrl：数据加载的Url
		this.loader = new Ext.tree.TreeLoader({
			 dataUrl: this.dataUrl,
			 uiProviders: {
			 	'col': Ext.tree.ColumnNodeUI
			 }
		});
		
		this.root = new Ext.tree.AsyncTreeNode({
			draggable: false,
			id: '0',
			text: '合同分析报告',
			expanded: true
		});
		
		ColumnTreePanel.superclass.initComponent.call(this);
	}
});
	   
