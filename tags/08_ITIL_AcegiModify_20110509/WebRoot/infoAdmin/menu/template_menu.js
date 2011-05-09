initDirPanel =function(text){
	var menuName = text.substr(0,'<');
	var dirForm = new Ext.form.FormPanel({
                		id : 'dirForm',
                		layout: 'table',
						height : 230,
						width : 400,
						frame : true,
						layoutConfig: {columns: 2},	    		
						items:[
							{html: "菜单名:",width : 80 ,cls: 'common-text', style:'width:100;height:20;text-align:center;margin:5 0 5 0;'},
							{id : "menuName",xtype : "textfield",width : 250,name : "menuName",value:text,allowBlank : false}	,
							{id : "menuUrl",xtype : "textfield",name : "menuUrl",hidden : true}
							]	
                	});
	return  dirForm;        
},
initLeafPanel =function(text,href){
	var leafForm = new Ext.form.FormPanel({
                		id : 'leafForm',
                		layout: 'table',
						height : 230,
						width : 400,
						frame : true,
						layoutConfig: {columns: 2},	    		
						items:[
							{html: "菜单名:" ,width : 80,cls: 'common-text', style:'width:100;height:20;text-align:center;margin:5 0 5 0;'},
							{id : "menuName",xtype : "textfield",width : 250,name : "menuName",value:text,allowBlank : false},	
							{html: "RUL:" ,width : 80,cls: 'common-text', style:'width:100;height:20;text-align:center;margin:5 0 5 0;'},
							{id : "menuUrl",xtype : "textfield",width : 250,name : "menuUrl",value:href,allowBlank : false}
							]	
                	});
	return  leafForm;        
},
saveMenuItem= function(parentId,id,leaf,node){
	var menuName = Ext.getCmp('menuName').getValue();
	var menuUrl = Ext.getCmp('menuUrl').getValue();
	Ext.Ajax.request({											
		url: webContext+'/menu_saveTemplateMenuItem.action',
		params : {
			parentId : parentId,
			id : id,
			name : menuName,
			url : menuUrl,
			leafFlag : leaf
		},
		method : 'post',
		success : function(response) {
			Ext.getCmp('editWin').close();
			if(node){
				node.parentNode.reload();
			}
		},
        scope:this
	});			
},
removeMenuItem= function(node){
	if (node.id != 0) {
		Ext.Msg.confirm("确认删除", "确定要删除所选节点吗？", function(btn) {
			if (btn == "yes") {
				Ext.Ajax.request({											
					url: webContext+'/menu_removeTemplateMenuItem.action',
					params : {
						id : node.id
					},
					method : 'post',
					success : function(response) {
							node.parentNode.reload();
					},
			        scope:this
				});			
			}
		});
	} else {
		Ext.Msg.alert("警告", "不能删除顶级菜单！");
	}
},
//扩展窗体
FormEditWin = function(){
	var curFormWin;
	
	return {
		width : 400,
		height : 300,
		
		// 显示添加子目录窗口
		showAddDirWin : function(parentNode){
			var dirForm = initDirPanel('');
            var win = new Ext.Window({
					id : 'editWin',
					title : "菜单",
					width : 400,
					height : 240,
					maximizable : true,
					modal : true,
					items: dirForm,								
					buttons:[{xtype:'button',text:'保存',handler:function(){saveMenuItem(parentNode.id,'',0,parentNode)	},scope:this},
        						{xtype:'button',handler:function(){Ext.getCmp('editWin').close();},text:'关闭',scope:this}]						
				});
			win.show();
		},
		// 显示添加叶子菜单窗口
		showAddLeafWin : function(parentNode) {
			var leafPanel = initLeafPanel('');
            var win = new Ext.Window({
					id : 'editWin',
					title : "菜单",
					width : 400,
					height : 240,
					maximizable : true,
					modal : true,
					items: leafPanel,								
					buttons:[{xtype:'button',text:'保存',handler:function(){saveMenuItem(parentNode.id,'',1,parentNode)}	,scope:this},
        						{xtype:'button',handler:function(){Ext.getCmp('editWin').close();},text:'关闭',scope:this}]						
				});
			win.show();
		},
		showEditDirWin : function(node) {
			var dirForm = initDirPanel(node.text);
            var win = new Ext.Window({
					id : 'editWin',
					title : "菜单",
					width : 400,
					height : 240,
					maximizable : true,
					modal : true,
					items: dirForm,								
					buttons:[{xtype:'button',text:'保存',handler:function(){saveMenuItem('',node.id,0,node);},scope:this},
        						{xtype:'button',handler:function(){Ext.getCmp('editWin').close();},text:'关闭',scope:this}]						
				});
			win.show();
		},
		showEditLeafWin : function(node) {
			var temp = node.text.split("-----");
			var dirForm = initLeafPanel(temp[0],temp[1]);
            var win = new Ext.Window({
					id : 'editWin',
					title : "菜单",
					width : 400,
					height : 240,
					maximizable : true,
					modal : true,
					items: dirForm,								
					buttons:[{xtype:'button',text:'保存',handler:function(){saveMenuItem('',node.id,1,node)},scope:this},
        						{xtype:'button',handler:function(){Ext.getCmp('editWin').close();},text:'关闭',scope:this}]						
				});
			win.show();
		}
	}
}();

// 导航树
NavTree = function(){
	var nav;
	var navEditor;
	var leafMenu;
	var dirMenu;
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
					url : webContext + '/menu_loadTemplateMenuItem.action'
				});
				loader.on('beforeload', function(treeloader, node) {
					treeloader.baseParams = {
						id : node.id
					};
				}, this);
			}
			if(!root){
				root = new Ext.tree.AsyncTreeNode({
					id : '0',
					text : "模板菜单",
					expanded : true
				});
			}	
			if(!nav){
				nav = new Ext.tree.TreePanel({
					id: 'menuTEree',
					title : "系统菜单定制",
					autoWidth : true,
					height: 500,
					autoScroll : true,
					animate : true,
					loader : loader,
					root : root,
					enableDD : true,
					containerScroll: true,
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
				// 当节点移动时触发事件
				nav.on("movenode", function(tree, node, oldParent, newParent, index) {
					Ext.Ajax.request({											
						url: webContext+'/menu_moveTemplateMenuItem.action',
						params : {
							id : node.id,
							oldParentId : oldParent.id,
							newParentId : newParent.id,
							nodeIndex : index
						},
						method : 'post',
						success : function(response) {
						},
				        scope:this
					});			
				});
			}
			this.setLeafMenu();
			this.setDirMenu();
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
		setLeafMenu: function(){
			// 设置叶子菜单
			if(!leafMenu){
				leafMenu = new Ext.menu.Menu({
					items : [{text : "编辑",iconCls:'edit',handler : function() {FormEditWin.showEditLeafWin(nodeSelected);}}, "-",
								{text : "删除",iconCls: 'delete',handler : function() {removeMenuItem(nodeSelected);}}]
				});
			}
		},
		setDirMenu: function(){
			// 设置目录菜单
			if(!dirMenu){
				dirMenu = new Ext.menu.Menu({
					items : [ {text : "编辑",iconCls:'edit',handler : function() {FormEditWin.showEditDirWin(nodeSelected);}}, "-",
								{text : "添加叶子节点",iconCls:'add',handler : function() {FormEditWin.showAddLeafWin(nodeSelected);}}, "-", 
								{text : "添加目录节点",iconCls:'add',handler : function() {FormEditWin.showAddDirWin(nodeSelected);}}, "-", 
								{text : "删除",iconCls: 'delete',handler : function() {removeMenuItem(nodeSelected);}}]
				});
			}
		},
		showTreeMenu : function(node, e){
			nodeSelected = node;
			nodeSelected.select();
			if (node.isLeaf()) {
				// 显示叶子节点菜单
				leafMenu.showAt(e.getPoint());
			} else {
				// 显示目录节点菜单
				dirMenu.showAt(e.getPoint());
			}
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
	NavTree.setMgr("系统模板");
	NavTree.init();
	NavTree.show();
});