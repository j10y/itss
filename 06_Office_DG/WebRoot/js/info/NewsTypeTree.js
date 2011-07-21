Ext.ns("NewsTypeTree");var NewsTypeTree=function(){return this.setup();};NewsTypeTree.prototype.setup=function(){var b;var c=new Ext.tree.TreePanel({id:"typeTree",title:"新闻类别",region:"west",width:200,height:480,autoScroll:true,collapsible:true,split:true,tbar:new Ext.Toolbar({id:"newsTypeBar",height:30,items:[{text:"刷新",iconCls:"refresh",handler:function(){c.root.reload();}}]}),loader:new Ext.tree.TreeLoader({url:__ctxPath+"/info/treeNewsType.do"}),root:new Ext.tree.AsyncTreeNode({expanded:true}),rootVisible:false,listeners:{"click":NewsTypeTree.clickNode}});if(isGranted("_NewsTypeAdd")||isGranted("_NewsTypeEdit")||isGranted("_NewsTypeDel")){Ext.getCmp("newsTypeBar").add(new Ext.Button({text:"添加",iconCls:"btn-add",handler:function(){new NewsTypeForm().show();}}));c.on("contextmenu",d,c);}var g=new Ext.menu.Menu({id:"NewsTypeTreeMenu",items:[]});if(isGranted("_NewsTypeAdd")){g.add({text:"新建",iconCls:"btn-add",scope:this,handler:e});}if(isGranted("_NewsTypeEdit")){g.add({text:"修改",iconCls:"btn-edit",scope:this,handler:a});}if(isGranted("_NewsTypeDel")){g.add({text:"删除",iconCls:"btn-delete",scope:this,handler:f});}function d(h,i){b=new Ext.tree.TreeNode({id:h.id,text:h.text});g.showAt(i.getXY());}function e(){var h=Ext.getCmp("newsTypeForm");if(h==null){new NewsTypeForm().show();}}function f(){var i=b.id;var h=Ext.getCmp("typeTree");if(i>0){Ext.Msg.confirm("删除操作","你确定删除新闻类型?",function(j){if(j=="yes"){Ext.Ajax.request({url:__ctxPath+"/info/removeNewsType.do",params:{typeId:i},method:"post",success:function(){Ext.ux.Toast.msg("操作信息","删除成功!");var k=Ext.getCmp("NewsTypeView");if(k!=null){k.getStore().reload();}if(h!=null){h.root.reload();}}});}});}}function a(){var i=b.id;var h=Ext.getCmp("newsTypeForm");if(h==null){new NewsTypeForm().show();h=Ext.getCmp("newsTypeForm");}h.form.load({url:__ctxPath+"/info/detailNewsType.do",params:{typeId:i},method:"post",deferredRender:true,layoutOnTabChange:true,waitMsg:"正在载入数据...",success:function(){},failure:function(){Ext.ux.Toast.msg("编辑","载入失败");}});}return c;};NewsTypeTree.clickNode=function(c){if(c!=null){var b=Ext.getCmp("NewsGrid");var a=b.getStore();a.proxy=new Ext.data.HttpProxy({url:__ctxPath+"/info/categoryNews.do"});a.baseParams={typeId:c.id};a.reload({params:{start:0,limit:25}});}};