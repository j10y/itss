// 全局路径
var basePath = "http://localhost:8080"+webContext;
if(typeof(glbRootPath) != "undefined"){
	basePath = glbRootPath;
}

function callBackFindTitles(data){
	
}

//从系统后台加载导航菜单树
NavigateTree = function(){
	var nav;
	var loader;
	var root;
	var removeFlag = false;
	var titleChangeFlag = false;
	var nodeSelected;
	var mgr;
	
	//菜单加载
///////////////////////////////////////////////////////////////////////////////////////////////
	
	var menuTitle = [];
	var title = [];
	var userId = '1';
	//从后台获得导航菜单面板的标题,title为一个存有结点信息的数组
	title = NavigateMenuManager.findAllMenuTitleByUserId(userId,callBackFindTitles);
	
	//导航菜单面板组
	var panels = [];
	for(var i=0; i< title.length; i++){
		var menuItemId  = title[i].id;
		var menuText = title[i].text;
		var item = new Ext.Panel({
			contentEl : 'west_'+i,
			title: title[i].text,  //产品中心(导航面板标题)
			border: false,
			iconCls: 'nav',
			items: new Ext.tree.TreePanel({
				   width: 240,
				   region : "west_"+i,
				   collapsible: true,
				   loader: basePath+'/servlet/loadNodes',
				   root: new Ext.tree.AsyncTreeNode({
				   	    id: menuItemId,
				   	    text: menuText,
				   		expanded: true
				   }),
				   animate: true,
				   bodyBorder : false
			}),
			cls: "x-btn-text-icon"
		});
		
		panels.push(item);
	}
	
	
	var productTree = new Ext.tree.TreePanel({
		width : 240,
		region : "west_1",
		collapsible : true,
		root : root,
		animate : true,
		bodyBorder : false
	});
	
	var productPanel = new Ext.Panel({
		contentEl: 'west_1',
        title: '产品中心',
        border: false,
        iconCls: 'nav',
        animate: true,
        items: [productTree],
        cls: "x-btn-text-icon"
   });
/////////////////////////////////////////////////////////////////////////////////////////////////	
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