/*
* 目标数据面板
*/

test = function(result){
	if(result=='ERROR_ADD'){
		alert("服务目录不能在服务项下建立");
		Ext.getCmp("tree").error = '1';
		return ;
	}else if(result=='ERROR_ITEM'){
		alert("服务项不能拖到服务项下");
		Ext.getCmp("tree").error = '1';
	}else if(result=='ERROR_MOVE'){
		alert("服务目录不能拖到服务项下");
		Ext.getCmp("tree").error = '1';
	}else if(result=='ERROR_RING'){
		alert("父节点已经存在此服务项，不能设置环状依赖");
		Ext.getCmp("tree").error = '1';
	}else if(result=='ERROR_DOUBLE'){
		alert("不能添加相同的服务项");
		Ext.getCmp("tree").error = '1';
	}else{
		Ext.getCmp("tree").error = '0';
	}
}
testKernel = function(result){
	if(result=='KERNEL_ITEM'){
		Ext.getCmp("tree").kernel = 'item';
		return ;
	}else{
		Ext.getCmp("tree").kernel = 'cata';
		return ;
	}
}

//目标节点，子节点，和选择的数据
var ddFunction = function(node, refNode, selections) {

	for(var i = 0; i < selections.length; i ++) {

		var record = selections[i];//这个record表示表格选中的数据
		node.insertBefore(new Ext.tree.TreeNode({
			text: record.get('name'),
			id: record.get('id'),
			leaf: record.get('leaf')
		}), refNode);

		var parentId = node.id;

		var id = record.get('id');

		var order = node.indexOf(node.lastChild) ;

		DWREngine.setAsync(false);
		SCIRelationShipManager.ajaxAddByCI(id, parentId,order,test);
		if(this.error=='1'){
			return false;
		}
		//父节点加载数据
		node.reload();
	}
}

PagteModelTreePanel = Ext.extend(Ext.tree.TreePanel, {
	id:'tree',
	title: '服务目录关系',
	animate: true,
	autoScroll: true,
	rootVisible: true,
	containerScroll: true,
	//columnWidth:.5,
	lines: true,
	height: 500,
	enableDD: true,
	ddGroup: "tgDD",
	removeFlag: false,
	initComponent: function() {
				this.error = '0';
				this.kernel = ''; //判断关系中包含的是服务目录还是服务项
				this.root = new Ext.tree.AsyncTreeNode({
					text: rootText,
					draggable: false,
					id: rootId
				}),

				//根据从表格得到数据的id 和页面模板的id 来加载后台数据
				this.loader = new Ext.tree.TreeLoader({
					dataUrl:webContext+'/sciRelationShip_loadSCIRelationShip.action'
				});

				this.loader.on('beforeload', function(treeloader, node) {

					treeloader.baseParams = {
						id : node.id,
						rootId : rootId
					};

				}, this);

				PagteModelTreePanel.superclass.initComponent.call(this);
			}
		});