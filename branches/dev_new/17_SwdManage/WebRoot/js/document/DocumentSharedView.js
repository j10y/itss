Ext.ns("DocumentSharedView");var DocumentSharedView=function(){return new Ext.Panel({id:"DocumentSharedView",title:"共享文档列表",iconCls:"menu-folder-shared",autoScroll:true,items:[new Ext.FormPanel({id:"SharedDocumentForm",height:40,frame:false,border:false,layout:"hbox",layoutConfig:{padding:"5",align:"middle"},defaults:{xtype:"label",margins:{top:0,right:4,bottom:4,left:4}},items:[{text:"文档名称"},{xtype:"textfield",name:"document.docName"},{text:"共享人"},{xtype:"textfield",name:"fullname"},{text:"创建时间 从"},{xtype:"datefield",format:"Y-m-d",name:"from"},{text:"至"},{xtype:"datefield",format:"Y-m-d",name:"to"},{xtype:"button",text:"查询",iconCls:"search",handler:function(){var a=Ext.getCmp("SharedDocumentForm");var b=Ext.getCmp("DocumentSharedGrid");if(a.getForm().isValid()){a.getForm().submit({waitMsg:"正在提交查询",url:__ctxPath+"/document/shareListDocument.do",method:"post",params:{start:0,limit:25},success:function(d,e){var c=Ext.util.JSON.decode(e.response.responseText);b.getStore().loadData(c);}});}}},{xtype:"button",text:"重置",iconCls:"reset",handler:function(){var a=Ext.getCmp("SharedDocumentForm");a.getForm().reset();}}]}),this.setup()]});};DocumentSharedView.prototype.setup=function(){return this.grid();};DocumentSharedView.prototype.grid=function(){var d=new Ext.grid.CheckboxSelectionModel();var a=new Ext.grid.ColumnModel({columns:[d,new Ext.grid.RowNumberer(),{header:"docId",dataIndex:"docId",hidden:true},{header:"文档名称",dataIndex:"docName",width:120},{header:"创建时间",dataIndex:"createtime"},{header:"共享人",dataIndex:"fullname"},{header:"附件",dataIndex:"haveAttach",renderer:function(j,g,e){if(j==""||j=="0"){return"无附件";}else{var h=e.data.attachFiles;var k="";for(var f=0;f<h.length;f++){k+='<a href="#" onclick="FileAttachDetail.show('+h[f].fileId+');" class="attachment">'+h[f].fileName+"</a>";k+="&nbsp;";}return k;}}},{header:"查看",dataIndex:"docId",width:50,renderer:function(i,h,f,l,g){var k=f.data.docId;var e=f.data.docName;var j='<button title="查看" value=" " class="btn-readdocument" onclick="DocumentSharedView.read('+k+",'"+e+"')\">&nbsp;</button>";return j;}}],defaults:{menuDisabled:false,width:100}});var b=this.store();b.load({params:{start:0,limit:25}});var c=new Ext.grid.GridPanel({id:"DocumentSharedGrid",store:b,trackMouseOver:true,disableSelection:false,loadMask:true,autoHeight:true,maxHeight:600,cm:a,sm:d,viewConfig:{forceFit:true,enableRowBody:false,showPreview:false},bbar:new Ext.PagingToolbar({pageSize:25,store:b,displayInfo:true,displayMsg:"当前显示从{0}至{1}， 共{2}条记录",emptyMsg:"当前没有记录"})});c.addListener("rowdblclick",function(g,f,h){g.getSelectionModel().each(function(e){DocumentSharedView.read(e.data.docId,e.data.docName);});});return c;};DocumentSharedView.prototype.store=function(){var a=new Ext.data.Store({proxy:new Ext.data.HttpProxy({url:__ctxPath+"/document/shareListDocument.do"}),reader:new Ext.data.JsonReader({root:"result",totalProperty:"totalCounts",id:"id",fields:[{name:"docId",type:"int"},"docName","fullname","content","createtime","haveAttach","attachFiles"]}),remoteSort:true});return a;};DocumentSharedView.read=function(d,b){var c=Ext.getCmp("centerTabPanel");var a=Ext.getCmp("DocumentShared");if(a==null){a=new DocumentSharedPanel(d,b);c.add(a);c.activate(a);}else{c.remove("DocumentShared");a=new DocumentSharedPanel(d,b);c.add(a);c.activate(a);}};