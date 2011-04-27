
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
		 
		//������ģ��ʱ��Ҫ�������cm:ÿһ�еĸ�ʽ����
		this.columns = this.cm;
		
		//������ģ��ʱ��Ҫ�������dataUrl�����ݼ��ص�Url
		this.loader = new Ext.tree.TreeLoader({
			 dataUrl: this.dataUrl,
			 uiProviders: {
			 	'col': Ext.tree.ColumnNodeUI
			 }
		});
		
		this.root = new Ext.tree.AsyncTreeNode({
			draggable: false,
			id: '0',
			text: '��ͬ��������',
			expanded: true
		});
		
		ColumnTreePanel.superclass.initComponent.call(this);
	}
});
	   
