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
							{html: "�˵���:",width : 80 ,cls: 'common-text', style:'width:100;height:20;text-align:center;margin:5 0 5 0;'},
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
							{html: "�˵���:" ,width : 80,cls: 'common-text', style:'width:100;height:20;text-align:center;margin:5 0 5 0;'},
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
		Ext.Msg.confirm("ȷ��ɾ��", "ȷ��Ҫɾ����ѡ�ڵ���", function(btn) {
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
		Ext.Msg.alert("����", "����ɾ�������˵���");
	}
},
//��չ����
FormEditWin = function(){
	var curFormWin;
	
	return {
		width : 400,
		height : 300,
		
		// ��ʾ�����Ŀ¼����
		showAddDirWin : function(parentNode){
			var dirForm = initDirPanel('');
            var win = new Ext.Window({
					id : 'editWin',
					title : "�˵�",
					width : 400,
					height : 240,
					maximizable : true,
					modal : true,
					items: dirForm,								
					buttons:[{xtype:'button',text:'����',handler:function(){saveMenuItem(parentNode.id,'',0,parentNode)	},scope:this},
        						{xtype:'button',handler:function(){Ext.getCmp('editWin').close();},text:'�ر�',scope:this}]						
				});
			win.show();
		},
		// ��ʾ���Ҷ�Ӳ˵�����
		showAddLeafWin : function(parentNode) {
			var leafPanel = initLeafPanel('');
            var win = new Ext.Window({
					id : 'editWin',
					title : "�˵�",
					width : 400,
					height : 240,
					maximizable : true,
					modal : true,
					items: leafPanel,								
					buttons:[{xtype:'button',text:'����',handler:function(){saveMenuItem(parentNode.id,'',1,parentNode)}	,scope:this},
        						{xtype:'button',handler:function(){Ext.getCmp('editWin').close();},text:'�ر�',scope:this}]						
				});
			win.show();
		},
		showEditDirWin : function(node) {
			var dirForm = initDirPanel(node.text);
            var win = new Ext.Window({
					id : 'editWin',
					title : "�˵�",
					width : 400,
					height : 240,
					maximizable : true,
					modal : true,
					items: dirForm,								
					buttons:[{xtype:'button',text:'����',handler:function(){saveMenuItem('',node.id,0,node);},scope:this},
        						{xtype:'button',handler:function(){Ext.getCmp('editWin').close();},text:'�ر�',scope:this}]						
				});
			win.show();
		},
		showEditLeafWin : function(node) {
			var temp = node.text.split("-----");
			var dirForm = initLeafPanel(temp[0],temp[1]);
            var win = new Ext.Window({
					id : 'editWin',
					title : "�˵�",
					width : 400,
					height : 240,
					maximizable : true,
					modal : true,
					items: dirForm,								
					buttons:[{xtype:'button',text:'����',handler:function(){saveMenuItem('',node.id,1,node)},scope:this},
        						{xtype:'button',handler:function(){Ext.getCmp('editWin').close();},text:'�ر�',scope:this}]						
				});
			win.show();
		}
	}
}();

// ������
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
				Ext.Msg.alert("������ʾ","����ͨ��NavTree.setMgr()����mgr");
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
					text : "ģ��˵�",
					expanded : true
				});
			}	
			if(!nav){
				nav = new Ext.tree.TreePanel({
					id: 'menuTEree',
					title : "ϵͳ�˵�����",
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
								// ΪҶ�ӽڵ�ʱ���������������
								event.stopEvent();
							}
						}
					}
				});
				// ����Ҽ��˵�
				nav.on("contextmenu", this.showTreeMenu);
				// ���ڵ��ƶ�ʱ�����¼�
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
			// ����Ҷ�Ӳ˵�
			if(!leafMenu){
				leafMenu = new Ext.menu.Menu({
					items : [{text : "�༭",iconCls:'edit',handler : function() {FormEditWin.showEditLeafWin(nodeSelected);}}, "-",
								{text : "ɾ��",iconCls: 'delete',handler : function() {removeMenuItem(nodeSelected);}}]
				});
			}
		},
		setDirMenu: function(){
			// ����Ŀ¼�˵�
			if(!dirMenu){
				dirMenu = new Ext.menu.Menu({
					items : [ {text : "�༭",iconCls:'edit',handler : function() {FormEditWin.showEditDirWin(nodeSelected);}}, "-",
								{text : "���Ҷ�ӽڵ�",iconCls:'add',handler : function() {FormEditWin.showAddLeafWin(nodeSelected);}}, "-", 
								{text : "���Ŀ¼�ڵ�",iconCls:'add',handler : function() {FormEditWin.showAddDirWin(nodeSelected);}}, "-", 
								{text : "ɾ��",iconCls: 'delete',handler : function() {removeMenuItem(nodeSelected);}}]
				});
			}
		},
		showTreeMenu : function(node, e){
			nodeSelected = node;
			nodeSelected.select();
			if (node.isLeaf()) {
				// ��ʾҶ�ӽڵ�˵�
				leafMenu.showAt(e.getPoint());
			} else {
				// ��ʾĿ¼�ڵ�˵�
				dirMenu.showAt(e.getPoint());
			}
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
	NavTree.setMgr("ϵͳģ��");
	NavTree.init();
	NavTree.show();
});