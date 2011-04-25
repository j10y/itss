// ȫ��·��
var basePath = "http://localhost:8080"+webContext;
//����˵���Ϣ�󷵻ص�id

if(typeof(glbRootPath) != "undefined"){
	basePath = glbRootPath;
}


//�˵���
MenuTree = function(){
	var nav;
	var loader;
	var root;
	var removeFlag = false;
	var titleChangeFlag = false;
	var nodeSelected;
	var mgr;
	
	return {
		init : function(){
			if(!mgr){
				Ext.Msg.alert("������ʾ","����ͨ��MenuTree.setMgr()����mgr");
				return;
			}
			if(!loader){
				loader = new Ext.tree.TreeLoader({
					url : basePath + '/servlet/deptMenuJson'
				});
				loader.on('beforeload', function(treeloader, node) {
					treeloader.baseParams = {
						id : node.id,
						smtId: smtId,
						dmtId: dmtId,
						method : 'tree'
					};
				}, this);
			}
			if(!root){
				root = new Ext.tree.AsyncTreeNode({
					id : '0',
					text : "���Ų˵�",
					expanded : true
				});
			}
			
			if(!nav){
				nav = new Ext.tree.TreePanel({
					title : "",
					autoWidth : true,
					height: 500,
					autoScroll : true,
					animate : true,
					loader : loader,
					root : root,
					enableDD : true,
					collapsible : true,
					bodyBorder : false,
					listeners : {
						'click' : function(node, event) {
							if (node.isLeaf()) {
								// ΪҶ�ӽڵ�ʱ���������������
								event.stopEvent();
							}
						}
					}
				});
				
				// ���ڵ��ƶ�ʱ�����¼�
				nav.on("movenode", function(tree, node, oldParent, newParent, index) {
					mgr.ajaxMoveNode(node.id, oldParent.id, newParent.id, index);
				});

				nav.on("checkchange",function(node,checked){
					var nodeId = node.id;
					if(checked == true){
					   mgr.ajaxEnableNode(nodeId, 1);
					}else{
					   mgr.ajaxEnableNode(nodeId, 0);	
					}
				});
			}

		},
		setMgr : function(manager){
			mgr = manager;
		},
		getMgr : function(){
			return mgr;
		},
		getId : function(){
			return id;
		},
	
		show : function(){
			nav.render(Ext.getBody());
			nav.getRootNode().toggle();
		}
	}
}();

// �ĵ��������ִ��
Ext.onReady(function(){
	Ext.BLANK_IMAGE_URL = webContext+"/extEngine/resources/images/default/s.gif";
	if(typeof(DeptMenuTemplateManager)=="undefined"){
		Ext.Msg.alert("������ʾ","��������DWR����ʵ����DeptMenuTemplateManager");
	}else{
		MenuTree.setMgr(DeptMenuTemplateManager);
		MenuTree.init();
		MenuTree.show();
	}
});