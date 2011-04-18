// 全局路径
var basePath = "http://localhost:8080"+webContext;
if(typeof(glbRootPath) != "undefined"){
	basePath = glbRootPath;
}

// 导航菜单树
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
				Ext.Msg.alert("警告提示","请先通过NavTree.setMgr()设置mgr");
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
					text : "系统菜单",
					expanded : true
				});
			}
			
			if(!nav){
				nav = new Ext.tree.TreePanel({
					title : "系统菜单定制",
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
								// 为叶子节点时，点击不进入链接
								event.stopEvent();
							}
						}
					}
				});
				// 添加右键菜单
				nav.on("contextmenu", this.showTreeMenu);
				// 当节点文本改变时触发事件
				nav.on("textchange", function(node, newText, oldText) {
					if (!titleChangeFlag && newText != oldText) {
						mgr.ajaxUpdateTitle(node.id, newText, function(success) {
							if (!success) {
								Ext.Msg.show({
									title : "操作失败！",
									msg : "菜单修改失败！",
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
				// 当节点移动时触发事件
				nav.on("movenode", function(tree, node, oldParent, newParent, index) {
					mgr.ajaxMoveNode(node.id, oldParent.id, newParent.id, index);
				});
				// 当节点删除时触发事件
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

// 文档加载完毕执行
Ext.onReady(function(){
	Ext.BLANK_IMAGE_URL = webContext+"/extEngine/resources/images/default/s.gif";
	if(typeof(NavigateMenuManager)=="undefined"){
		Ext.Msg.alert("警告提示","请先设置DWR，并实例化NavigateMenuManager");
	}else{
		NavTree.setMgr(NavigateMenuManager);
		NavTree.init();
		NavTree.show();
	}
});