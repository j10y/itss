Ext.ns("ProviderView");ProviderView=Ext.extend(Ext.Panel,{searchPanel:null,gridPanel:null,store:null,topbar:null,constructor:function(a){Ext.applyIf(this,a);this.initUIComponents();ProviderView.superclass.constructor.call(this,{id:"ProviderView",title:"供应商列表",iconCls:"menu-provider",region:"center",layout:"border",items:[this.searchPanel,this.gridPanel]});},initUIComponents:function(){this.searchPanel=new Ext.FormPanel({id:"ProviderSearchForm",height:35,region:"north",frame:false,border:false,layout:"hbox",layoutConfig:{padding:"5",align:"middle"},defaults:{style:"padding:0px 5px 0px 5px;",border:false,anchor:"98%,98%",labelWidth:75,xtype:"label"},items:[{text:"请输入查询条件:"},{text:"供应商名称"},{xtype:"textfield",name:"Q_providerName_S_LK",width:100},{text:"联系人"},{xtype:"textfield",name:"Q_contactor_S_LK",width:100},{text:"等级"},{hiddenName:"Q_rank_N_EQ",width:100,xtype:"combo",mode:"local",editable:false,triggerAction:"all",store:[["null","　"],["1","一级供应商"],["2","二级供应商"],["3","三级供应商"],["4","四级供应商"]]},{xtype:"button",text:"查询",iconCls:"search",handler:function(){var c=Ext.getCmp("ProviderSearchForm");var d=Ext.getCmp("ProviderGrid");if(c.getForm().isValid()){c.getForm().submit({waitMsg:"正在提交查询",url:__ctxPath+"/customer/listProvider.do",success:function(f,g){var e=Ext.util.JSON.decode(g.response.responseText);d.getStore().loadData(e);}});}}}]});this.store=new Ext.data.Store({proxy:new Ext.data.HttpProxy({url:__ctxPath+"/customer/listProvider.do"}),reader:new Ext.data.JsonReader({root:"result",totalProperty:"totalCounts",id:"id",fields:[{name:"providerId",type:"int"},"rank","providerName","contactor","phone","address"]}),remoteSort:true});this.store.setDefaultSort("providerId","desc");this.store.load({params:{start:0,limit:25}});var b=new Ext.grid.CheckboxSelectionModel();var a=new Ext.grid.ColumnModel({columns:[b,new Ext.grid.RowNumberer(),{header:"providerId",dataIndex:"providerId",hidden:true},{header:"等级",dataIndex:"rank",width:40,renderer:function(c){if(c=="1"){return'<img title="一级供应商" src="'+__ctxPath+'/images/flag/customer/grade_one.png"/>';}else{if(c=="2"){return'<img title="二级供应商" src="'+__ctxPath+'/images/flag/customer/grade_two.png"/>';}else{if(c=="3"){return'<img title="三级供应商" src="'+__ctxPath+'/images/flag/customer/grade_three.png"/>';}else{return'<img title="四级供应商" src="'+__ctxPath+'/images/flag/customer/grade_four.png"/>';}}}}},{header:"供应商名字",dataIndex:"providerName",width:120},{header:"联系人",dataIndex:"contactor",width:80},{header:"电话",dataIndex:"phone",width:80},{header:"地址",dataIndex:"address",width:150},{header:"管理",dataIndex:"providerId",width:100,sortable:false,renderer:function(f,e,c,i,d){var h=c.data.providerId;var g="";if(isGranted("_ProviderDel")){g='<button title="删除" value=" " class="btn-del" onclick="ProviderView.remove('+h+')">&nbsp;&nbsp;</button>';}if(isGranted("_ProviderEdit")){g+='&nbsp;<button title="编辑" value=" " class="btn-edit" onclick="ProviderView.edit('+h+')">&nbsp;&nbsp;</button>';}return g;}}],defaults:{sortable:true,menuDisabled:false,width:100}});this.topbar=new Ext.Toolbar({id:"ProviderFootBar",height:30,bodyStyle:"text-align:left",items:[]});if(isGranted("_ProviderAdd")){this.topbar.add(new Ext.Button({iconCls:"btn-add",text:"添加供应商",xtype:"button",handler:function(){new ProviderForm().show();}}));}if(isGranted("_ProviderDel")){this.topbar.add(new Ext.Button({iconCls:"btn-del",text:"删除供应商",xtype:"button",handler:function(){var e=Ext.getCmp("ProviderGrid");var c=e.getSelectionModel().getSelections();if(c.length==0){Ext.ux.Toast.msg("信息","请选择要删除的记录！");return;}var f=Array();for(var d=0;d<c.length;d++){f.push(c[d].data.providerId);}ProviderView.remove(f);}}));}this.topbar.add(new Ext.Button({xtype:"button",text:"发送邮件",iconCls:"btn-mail_send",handler:function(){var e=Ext.getCmp("ProviderGrid");var c=e.getSelectionModel().getSelections();if(c.length==0){Ext.ux.Toast.msg("信息","请选择要发送邮件的供应商！");return;}var f=Array();var g=Array();for(var d=0;d<c.length;d++){f.push(c[d].data.providerId);g.push(c[d].data.providerName);}new SendMailForm({ids:f,names:g,type:1}).show();}}));this.gridPanel=new Ext.grid.GridPanel({id:"ProviderGrid",tbar:this.topbar,region:"center",store:this.store,trackMouseOver:true,disableSelection:false,loadMask:true,autoHeight:true,cm:a,sm:b,viewConfig:{forceFit:true,enableRowBody:false,showPreview:false},bbar:new Ext.PagingToolbar({pageSize:25,store:this.store,displayInfo:true,displayMsg:"当前显示从{0}至{1}， 共{2}条记录",emptyMsg:"当前没有记录"})});AppUtil.addPrintExport(this.gridPanel);this.gridPanel.addListener("rowdblclick",function(d,c,f){d.getSelectionModel().each(function(e){if(isGranted("_ProviderEdit")){ProviderView.edit(e.data.providerId);}});});}});ProviderView.remove=function(b){var a=Ext.getCmp("ProviderGrid");Ext.Msg.confirm("信息确认",'删除供应商，则该供应商下的<font color="red">产品</font>也删除，您确认要删除该记录吗？',150,function(c){if(c=="yes"){Ext.Ajax.request({url:__ctxPath+"/customer/multiDelProvider.do",params:{ids:b},method:"post",success:function(){Ext.ux.Toast.msg("信息提示","成功删除所选记录！");a.getStore().reload({params:{start:0,limit:25}});}});}});};ProviderView.edit=function(a){new ProviderForm({providerId:a}).show();};