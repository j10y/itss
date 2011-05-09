
PagteModelTreePanel = Ext.extend(Ext.tree.TreePanel, {
	id:'tree',
	title: '服务项与配置项关系维护',
	animate: true,
	autoScroll: true,
	containerScroll: true,
	lines: true,
	width:300,
	height: 530,
	enableDD: true,   
    ddGroup: "tgDD", 
    removeFlag: false,
	frame:true,
	tbar:this.tbar,	
	initComponent: function() {
	    this.idd = this.dataId
	    this.kernel = '';
		var sra = new SRAction();
		var data = sra.getCIRelationShipPicInfo(this.dataId);
		var serviceId = sra.getReqServiceItemId(this.dataId);
		
		var serviceName =''; 
		var rootId = '';
		if(serviceId!='0'){
			var ciRelationShip = sra.getReqServiceItemShip(serviceId);//得到服务项的关系
			serviceName = ciRelationShip.name;
			rootId = ciRelationShip.id;
		}
		this.picId=data.id;
		this.symbol = '0';
		this.markerPanel = '0';
		this.marker = '0';
		this.typeName ='';
		this.display ='';
		this.titleDisplay = '';
		this.readonly ='';
		this.root = new Ext.tree.AsyncTreeNode({
			text: serviceName,//data.name
			draggable: false,
			expanded:true,
			id: rootId   
		}),
		this.loader = new Ext.tree.TreeLoader({
			 dataUrl:webContext+'/ciRelationShip_loadConfigItemRelation.action'
		});
		
		this.loader.on('beforeload', function(treeloader, node) {
					treeloader.baseParams = {
						id : node.id,//关系id						
						configId : " ",    //这个就是上面表单保存时的id,关系名的id
						configItemId : ""//配置项的id
					};
					

		}, this);
		
						 
		PagteModelTreePanel.superclass.initComponent.call(this);
	}
});


	
	