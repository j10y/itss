// ȫ��·��
var basePath = "http://localhost:8080"+webContext;
if(typeof(glbRootPath) != "undefined"){
	basePath = glbRootPath;
}

// �����˵���
NavTree = function(){
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
				Ext.Msg.alert("������ʾ","����ͨ��NavTree.setMgr()����mgr");
				return;
			}
			if(!loader){
				loader = new Ext.tree.TreeLoader({
					url : basePath + '/menujson'
				});
				loader.on('beforeload', function(treeloader, node) {
					treeloader.baseParams = {
						id : node.id,
						method : 'tree'
					};
				}, this);
			}
			if(!root){
				root = new Ext.tree.AsyncTreeNode({
					id : '5',
					text : "ϵͳ�˵�",
					expanded : true
				});
			}
			
			if(!nav){
				nav = new Ext.tree.TreePanel({
					title : "ϵͳ�˵�����",
					autoWidth : true,
					autoHeight: true,
					autoScroll : true,
					animate : true,
					loader : loader,
					root : root,
					enableDD : true,
					listeners : {
						'click' : function(node, event) {
							if (node.isLeaf()) {
								// ΪҶ�ӽڵ�ʱ���������������
								event.stopEvent();
							}
						}
					}
				});
				// ����Ҽ��˵�
				nav.on("contextmenu", this.showTreeMenu);
				// ���ڵ��ı��ı�ʱ�����¼�
				nav.on("textchange", function(node, newText, oldText) {
					if (!titleChangeFlag && newText != oldText) {
						mgr.ajaxUpdateTitle(node.id, newText, function(success) {
							if (!success) {
								Ext.Msg.show({
									title : "����ʧ�ܣ�",
									msg : "�˵��޸�ʧ�ܣ�",
									buttons : Ext.Msg.OK,
									icon : Ext.MessageBox.ERROR
								});
								titleChangeFlag = true;
								node.setText(oldText);
								titleChangeFlag = false;
							}
						});
					}
				});
				// ���ڵ��ƶ�ʱ�����¼�
				nav.on("movenode", function(tree, node, oldParent, newParent, index) {
					mgr.ajaxMoveNode(node.id, oldParent.id, newParent.id, index);
				});
				// ���ڵ�ɾ��ʱ�����¼�
				nav.on("remove", function(tree, parentNode, node) {
					if (removeFlag) {
						mgr.ajaxRemoveNode(node.id);
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
		show : function(){
			nav.render(Ext.getBody());
			nav.getRootNode().toggle();
		}
	}
}();

// �ĵ��������ִ��
Ext.onReady(function(){
	Ext.BLANK_IMAGE_URL = webContext+"/extEngine/resources/images/default/s.gif";
	if(typeof(NavigateMenuManager)=="undefined"){
		Ext.Msg.alert("������ʾ","��������DWR����ʵ����NavigateMenuManager");
	}else{
		NavTree.setMgr(NavigateMenuManager);
		NavTree.init();
		NavTree.show();
	}
});