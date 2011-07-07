Ext.ns("DocFolder");var PersonalDocumentView=function(){var e;var g=new DocumentView();var c=new Ext.tree.TreePanel({region:"west",id:"leftFolderPanel",title:"我的文档目录",collapsible:true,split:true,width:160,height:800,tbar:new Ext.Toolbar({items:[{xtype:"button",iconCls:"btn-refresh",text:"刷新",handler:function(){c.root.reload();}},{xtype:"button",text:"展开",iconCls:"btn-expand",handler:function(){c.expandAll();}},{xtype:"button",text:"收起",iconCls:"btn-collapse",handler:function(){c.collapseAll();}}]}),loader:new Ext.tree.TreeLoader({url:__ctxPath+"/document/listDocFolder.do"}),root:new Ext.tree.AsyncTreeNode({expanded:true}),rootVisible:false,listeners:{"click":function(l){if(l!=null){g.setFolderId(l.id);var k=Ext.getCmp("DocumentView");if(l.id==0){k.setTitle("所有文档");}else{k.setTitle("["+l.text+"]文档列表");}var m=Ext.getCmp("DocumentGrid");var j=m.getStore();j.url=__ctxPath+"/system/listDocument.do";j.baseParams={folderId:l.id};j.params={start:0,limit:25};j.reload({params:{start:0,limit:25}});}}}});function b(j,k){e=new Ext.tree.TreeNode({id:j.id,text:j.text});f.showAt(k.getXY());}c.on("contextmenu",b,c);var f=new Ext.menu.Menu({id:"PerSonalDocumentTreeMenu",items:[{text:"新建目录",scope:this,iconCls:"btn-add",handler:i},{text:"修改目录",scope:this,iconCls:"btn-edit",handler:h},{text:"删除目录",scope:this,iconCls:"btn-delete",handler:d}]});function i(j){var k=e.id;new DocFolderForm(null,k,null);}function h(){var j=e.id;new DocFolderForm(j,null,null);}function d(){var j=e.id;Ext.Msg.confirm("删除操作","你确定删除该目录吗?",function(k){if(k=="yes"){Ext.Ajax.request({url:__ctxPath+"/document/removeDocFolder.do",params:{folderId:j},method:"post",success:function(l,n){var m=Ext.util.JSON.decode(l.responseText);if(m.success==false){Ext.ux.Toast.msg("操作信息",m.message);}else{Ext.ux.Toast.msg("操作信息","成功删除目录！");c.root.reload();}},failure:function(l,m){Ext.MessageBox.show({title:"操作信息",msg:"信息保存出错，请联系管理员！",buttons:Ext.MessageBox.OK,icon:"ext-mb-error"});}});}});}var a=new Ext.Panel({title:"我的文档",iconCls:"menu-personal-doc",layout:"border",id:"PersonalDocumentView",height:800,items:[c,g.getView()]});return a;};