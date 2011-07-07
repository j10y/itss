Ext.ns("MessageRecView");MessageRecView=Ext.extend(Ext.Panel,{gridPanel:null,searchPanel:null,receiveStore:null,constructor:function(a){Ext.applyIf(this,a);this.initUIComponents();MessageRecView.superclass.constructor.call(this,{id:"MessageRecView",autoHeight:true,layout:"form",items:[this.searchPanel,this.gridPanel]});},initUIComponents:function(){this.searchPanel=new Ext.FormPanel({height:80,title:"已收信息显示",id:"receiveSearchForm",frame:false,border:false,url:__ctxPath+"/info/listShortMessage.do",layout:"hbox",layoutConfig:{padding:"5",align:"middle"},defaults:{xtype:"label",margins:{top:0,right:4,bottom:4,left:4}},items:[{text:"类型"},{hiddenName:"shortMessage.msgType",xtype:"combo",mode:"local",width:100,editable:false,triggerAction:"all",store:[["1","个人信息"],["2","日程安排"],["3","计划任务"],["4","系统消息"]]},{text:"发送人"},{xtype:"textfield",name:"shortMessage.sender"},{text:"从"},{xtype:"datefield",format:"Y-m-d",name:"from",editable:false},{text:"到"},{xtype:"datefield",format:"Y-m-d",name:"to",editable:false},{text:"查询",xtype:"button",iconCls:"search",handler:this.search.createCallback(this)},{xtype:"button",text:"重置",iconCls:"reset",handler:this.reset.createCallback(this)},{xtype:"button",text:"标记为已读",iconCls:"ux-flag-blue",handler:this.setReadFlag.createCallback(this)},{iconCls:"btn-del",text:"删除信息",xtype:"button",handler:this.mutDel},{xtype:"hidden",name:"start",value:0},{xtype:"hidden",name:"limit",value:11}]});this.receiveStore=new Ext.data.Store({proxy:new Ext.data.HttpProxy({url:__ctxPath+"/info/listShortMessage.do"}),reader:new Ext.data.JsonReader({root:"result",totalProperty:"totalCounts",id:"id",fields:[{name:"receiveId",type:"int"},{name:"messageId",mapping:"shortMessage.messageId",type:"int"},{name:"msgType",mapping:"shortMessage.msgType",type:"int"},{name:"senderId",mapping:"shortMessage.senderId",type:"int"},{name:"sender",mapping:"shortMessage.sender"},{name:"content",mapping:"shortMessage.content"},{name:"sendTime",mapping:"shortMessage.sendTime"},{name:"readFlag"}]}),remoteSort:true});this.receiveStore.setDefaultSort("id","desc");this.receiveStore.load({params:{start:0,limit:11}});var b=new Ext.grid.CheckboxSelectionModel();var a=new Ext.grid.ColumnModel({columns:[b,new Ext.grid.RowNumberer(),{header:"状态",dataIndex:"readFlag",width:60,renderer:function(c){return c=="1"?"<img src='"+__ctxPath+"/images/btn/info/email_open.png'/>":"<img src='"+__ctxPath+"/images/btn/info/email.png'/>";}},{header:"类别",dataIndex:"msgType",width:60,renderer:function(c){if(c=="1"){return"<p style='color:green;'>个人信息</p>";}else{if(c=="2"){return"<p style='color:green;'>日程安排</p>";}else{if(c=="3"){return"<p style='color:green;'>计划任务</p>";}else{if(c=="4"){return"<p style='color:green;'>代办任务提醒</p>";}else{if(c=="5"){return"<p style='color:green;'>系统提醒</p>";}else{return"<p style='color:green;'>其他</p>";}}}}}}},{header:"发送人",dataIndex:"sender",width:40},{header:"内容",dataIndex:"content",width:300},{header:"发送时间",dataIndex:"sendTime",width:90},{header:"操作",dataIndex:"receiveId",width:120,renderer:function(h,g,e,j,f){var c=e.data.receiveId;var d=e.data.msgType;var i='<button title="删除" value=" " class="btn-del" onclick="MessageRecView.removeReceiveMessage('+c+')">&nbsp;</button>';if(d=="1"){i+='&nbsp;<button title="回复" value=" " class="btn-update" onclick="MessageRecView. reply('+c+')">&nbsp;</button>';}return i;}}],defaults:{sortable:true,menuDisabled:true,width:100},listeners:{hiddenchange:function(c,d,e){saveConfig(d,e);}}});this.gridPanel=new Ext.grid.GridPanel({id:"ReceiveMessage",height:338,store:this.receiveStore,shim:true,trackMouseOver:true,disableSelection:false,autoScroll:true,loadMask:true,cm:a,sm:b,viewConfig:{forceFit:true,enableRowBody:false,showPreview:false},bbar:new Ext.PagingToolbar({pageSize:11,store:this.receiveStore,displayInfo:true,displayMsg:"当前显示从{0}至{1}， 共{2}条记录",emptyMsg:"当前没有记录"})});},search:function(b){var a=b.gridPanel;var c=b.searchPanel;c.getForm().submit({waitMsg:"正在提交查询信息",success:function(f,g){var h=a.getStore();var e=g.response.responseText.replace("success:true,","");var d=Ext.util.JSON.decode(e);h.loadData(d,false);},failure:function(d,e){}});},reset:function(a){a.searchPanel.getForm().reset();},setReadFlag:function(b){var d=Ext.getCmp("ReceiveMessage");var a=d.getSelectionModel().getSelections();if(a.length==0){Ext.ux.Toast.msg("信息","请选择信息！");return;}var e=Array();for(var c=0;c<a.length;c++){e.push(a[c].data.receiveId);}Ext.Ajax.request({url:__ctxPath+"/info/multiReadInMessage.do",params:{ids:e},method:"post",success:function(){Ext.ux.Toast.msg("操作信息","成功标记所选信息为已读！");d.getStore().reload();}});},mutDel:function(){var c=Ext.getCmp("ReceiveMessage");var a=c.getSelectionModel().getSelections();if(a.length==0){Ext.ux.Toast.msg("信息","请选择要删除的记录！");return;}var d=Array();for(var b=0;b<a.length;b++){d.push(a[b].data.receiveId);}MessageRecView.removeReceiveMessage(d);}});MessageRecView.removeReceiveMessage=function(a){var b=Ext.getCmp("ReceiveMessage");Ext.Msg.confirm("删除操作","你确定要删除该信息吗?",function(c){if(c=="yes"){Ext.Ajax.request({url:__ctxPath+"/info/multiRemoveInMessage.do",params:{ids:a},method:"post",success:function(){Ext.ux.Toast.msg("操作信息","删除信息成功！");b.getStore().reload();}});}});};MessageRecView.reply=function(a){var d=Ext.getCmp("MessageManageView");d.removeAll(true);var c=new MessageForm();d.add(c);var b=Ext.getCmp("mFormPanel");d.doLayout();b.form.load({url:__ctxPath+"/info/replyInMessage.do",params:{receiveId:a},method:"post",deferredRender:true,layoutOnTabChange:true,success:function(){Ext.Ajax.request({url:__ctxPath+"/info/knowInMessage.do",method:"POST",params:{receiveId:a},success:function(e,f){},failure:function(e,f){},scope:this});},failure:function(){}});};MessageSendView=Ext.extend(Ext.Panel,{searchPanel:null,gridPanel:null,store:null,constructor:function(a){Ext.applyIf(this,a);this.initUIComponents();MessageSendView.superclass.constructor.call(this,{id:"MessageSendView",autoHeight:true,layout:"form",items:[this.searchPanel,this.gridPanel]});},initUIComponents:function(){this.searchPanel=new Ext.FormPanel({height:80,title:"已发信息显示",id:"sendSearchForm",url:__ctxPath+"/info/listInMessage.do",frame:false,border:false,layout:"hbox",layoutConfig:{padding:"5",align:"middle"},defaults:{xtype:"label",margins:{top:0,right:4,bottom:4,left:4}},items:[{text:"收信人"},{xtype:"textfield",name:"inMessage.userFullname"},{text:"从"},{xtype:"datefield",format:"Y-m-d",name:"from",editable:false},{text:"到"},{xtype:"datefield",format:"Y-m-d",name:"to",editable:false},{text:"查询",xtype:"button",iconCls:"search",handler:this.search.createCallback(this)},{text:"重置",xtype:"button",iconCls:"reset",handler:this.reset.createCallback(this)},{xtype:"hidden",name:"start",value:0},{xtype:"hidden",name:"limit",value:11}]});this.store=new Ext.data.Store({proxy:new Ext.data.HttpProxy({url:__ctxPath+"/info/listInMessage.do"}),reader:new Ext.data.JsonReader({root:"result",totalProperty:"totalCounts",id:"id",fields:[{name:"receiveId",type:"int"},{name:"messageId",mapping:"shortMessage.messageId",type:"int"},{name:"msgType",mapping:"shortMessage.msgType",type:"int"},{name:"content",mapping:"shortMessage.content"},{name:"userId",type:"int"},"userFullname",{name:"sendTime",mapping:"shortMessage.sendTime"}]}),remoteSort:true});this.store.setDefaultSort("id","desc");this.store.load({params:{start:0,limit:11}});this.rowActions=new Ext.ux.grid.RowActions({header:"管理",width:120,actions:[{iconCls:"btn-update",qtip:"重发",style:"margin:0 3px 0 3px"}]});var b=new Ext.grid.CheckboxSelectionModel();var a=new Ext.grid.ColumnModel({columns:[b,new Ext.grid.RowNumberer(),{header:"收信人",dataIndex:"userFullname",width:100},{header:"内容",dataIndex:"content",width:250},{header:"发送时间",dataIndex:"sendTime",width:90},this.rowActions],defaults:{sortable:true,menuDisabled:true,width:100},listeners:{hiddenchange:function(c,d,e){saveConfig(d,e);}}});this.gridPanel=new Ext.grid.GridPanel({id:"sendMessage",height:330,store:this.store,shim:true,trackMouseOver:true,disableSelection:false,autoScroll:true,loadMask:true,cm:a,sm:b,plugins:this.rowActions,viewConfig:{forceFit:true,enableRowBody:false,showPreview:false},bbar:new Ext.PagingToolbar({pageSize:11,store:this.store,displayInfo:true,displayMsg:"当前显示从{0}至{1}， 共{2}条记录",emptyMsg:"当前没有记录"})});this.rowActions.on("action",this.onRowAction,this);},search:function(b){var c=b.gridPanel;var a=b.searchPanel;a.getForm().submit({waitMsg:"正在提交查询信息",success:function(f,h){var g=c.getStore();var e=h.response.responseText.replace("success:true,","");var d=Ext.util.JSON.decode(e);g.loadData(d,false);}});},reset:function(a){a.searchPanel.getForm().reset();},reSend:function(a){var b=Ext.getCmp("sendMessage");Ext.Ajax.request({url:__ctxPath+"/info/sendShortMessage.do",params:{userId:a.data.userId+",",content:a.data.content},method:"post",success:function(){Ext.ux.Toast.msg("操作信息","重发成功！");b.getStore().reload();}});},onRowAction:function(c,a,d,e,b){switch(d){case"btn-update":this.reSend(a);break;default:break;}}});MessageManageView=Ext.extend(Ext.Panel,{constructor:function(a){Ext.applyIf(this,a);this.initUIComponents();MessageManageView.superclass.constructor.call(this,{id:"MessageManageView",iconCls:"menu-message",title:"我的消息",layout:"form",tbar:this.toolbar,autoHeight:true,items:[]});this.addRecPanel(this);},initUIComponents:function(){this.toolbar=new Ext.Toolbar({height:30,layout:"column",items:[new Ext.Button({text:"发送信息",iconCls:"btn-sendM",handler:this.addSendFormPanel.createCallback(this)}),{xtype:"button",text:"已发信息",iconCls:"btn-sendMessage",handler:this.addSendPanel.createCallback(this)},{text:"已收信息",iconCls:"btn-receiveMessage",handler:this.addRecPanel.createCallback(this)}]});},addRecPanel:function(a){a.removeAll(true);var b=new MessageRecView();a.add(b);a.doLayout();},addSendPanel:function(a){a.removeAll(true);var b=new MessageSendView();a.add(b);a.doLayout();},addSendFormPanel:function(b){b.removeAll(true);var a=new MessageForm();b.add(a);b.doLayout();}});