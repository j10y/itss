/*
* Ŀ���������
*/

test = function(result){
	if(result=='ERROR_ADD'){
		alert("����Ŀ¼�����ڷ������½���");
		Ext.getCmp("tree").error = '1';
		return ;
	}else if(result=='ERROR_ITEM'){
		alert("��������ϵ���������");
		Ext.getCmp("tree").error = '1';
	}else if(result=='ERROR_MOVE'){
		alert("����Ŀ¼�����ϵ���������");
		Ext.getCmp("tree").error = '1';
	}else if(result=='ERROR_RING'){
		alert("���ڵ��Ѿ����ڴ˷�����������û�״����");
		Ext.getCmp("tree").error = '1';
	}else if(result=='ERROR_DOUBLE'){
		alert("���������ͬ�ķ�����");
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

//Ŀ��ڵ㣬�ӽڵ㣬��ѡ�������
var ddFunction = function(node, refNode, selections) {

	for(var i = 0; i < selections.length; i ++) {

		var record = selections[i];//���record��ʾ���ѡ�е�����
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
		//���ڵ��������
		node.reload();
	}
}

PagteModelTreePanel = Ext.extend(Ext.tree.TreePanel, {
	id:'tree',
	title: '����Ŀ¼��ϵ',
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
				this.kernel = ''; //�жϹ�ϵ�а������Ƿ���Ŀ¼���Ƿ�����
				this.root = new Ext.tree.AsyncTreeNode({
					text: rootText,
					draggable: false,
					id: rootId
				}),

				//���ݴӱ��õ����ݵ�id ��ҳ��ģ���id �����غ�̨����
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