Ext.ns("DutyView");var DutyView=function(){return new Ext.Panel({id:"DutyView",iconCls:"menu-duty",title:"排班列表",autoScroll:true,items:[new Ext.FormPanel({id:"DutySearchForm",height:40,frame:false,border:false,layout:"hbox",layoutConfig:{padding:"5",align:"middle"},defaults:{xtype:"label",margins:{top:0,right:4,bottom:4,left:4}},items:[{text:"查询条件:"},{text:"姓名"},{xtype:"textfield",width:80,name:"Q_fullname_S_LK"},{text:"班制"},{xtype:"textfield",width:80,name:"Q_dutySystem_S_LK"},{text:"开始时间"},{xtype:"datefield",width:80,format:"Y-m-d",name:"Q_startTime_D_GE"},{text:"结束时间"},{xtype:"datefield",width:80,format:"Y-m-d",name:"Q_endTime_D_LE"},{xtype:"button",text:"查询",iconCls:"search",handler:function(){var a=Ext.getCmp("DutySearchForm");var b=Ext.getCmp("DutyGrid");if(a.getForm().isValid()){a.getForm().submit({waitMsg:"正在提交查询",url:__ctxPath+"/personal/listDuty.do",success:function(d,e){var c=Ext.util.JSON.decode(e.response.responseText);b.getStore().loadData(c);}});}}}]}),this.setup()]});};DutyView.prototype.setup=function(){return this.grid();};DutyView.prototype.grid=function(){var d=new Ext.grid.CheckboxSelectionModel();var a=new Ext.grid.ColumnModel({columns:[d,new Ext.grid.RowNumberer(),{header:"dutyId",dataIndex:"dutyId",hidden:true},{header:"姓名 ",dataIndex:"fullname"},{header:"部门",dataIndex:"appUser",renderer:function(e){if(e.department==null){return"";}else{return e.department.depName;}}},{header:"班制",dataIndex:"dutySystem",renderer:function(e){return e.systemName;}},{header:"开始时间",dataIndex:"startTime"},{header:"结束时间",dataIndex:"endTime"},{header:"管理",dataIndex:"dutyId",width:50,sortable:false,renderer:function(h,g,e,k,f){var j=e.data.dutyId;var i="";if(isGranted("_DutyDel")){i='<button title="删除" value=" " class="btn-del" onclick="DutyView.remove('+j+')">&nbsp;&nbsp;</button>';}if(isGranted("_DutyEdit")){i+='&nbsp;<button title="编辑" value=" " class="btn-edit" onclick="DutyView.edit('+j+')">&nbsp;&nbsp;</button>';}return i;}}],defaults:{sortable:true,menuDisabled:false,width:100}});var b=this.store();b.load({params:{start:0,limit:25}});var c=new Ext.grid.GridPanel({id:"DutyGrid",tbar:this.topbar(),store:b,trackMouseOver:true,disableSelection:false,loadMask:true,autoHeight:true,cm:a,sm:d,viewConfig:{forceFit:true,enableRowBody:false,showPreview:false},bbar:new Ext.PagingToolbar({pageSize:25,store:b,displayInfo:true,displayMsg:"当前显示从{0}至{1}， 共{2}条记录",emptyMsg:"当前没有记录"})});c.addListener("rowdblclick",function(g,f,h){g.getSelectionModel().each(function(e){if(isGranted("_DutyEdit")){DutyView.edit(e.data.dutyId);}});});return c;};DutyView.prototype.store=function(){var a=new Ext.data.Store({proxy:new Ext.data.HttpProxy({url:__ctxPath+"/personal/listDuty.do"}),reader:new Ext.data.JsonReader({root:"result",totalProperty:"totalCounts",id:"id",fields:[{name:"dutyId",type:"int"},"userId","fullname","dutySystem","appUser","startTime","endTime"]}),remoteSort:true});a.setDefaultSort("dutyId","desc");return a;};DutyView.prototype.topbar=function(){var a=new Ext.Toolbar({id:"DutyFootBar",height:30,bodyStyle:"text-align:left",items:[]});if(isGranted("_DutyAdd")){a.add(new Ext.Button({iconCls:"btn-add",text:"添加排班",handler:function(){new DutyForm();}}));}if(isGranted("_DutyDel")){a.add(new Ext.Button({iconCls:"btn-del",text:"删除排班",handler:function(){var d=Ext.getCmp("DutyGrid");var b=d.getSelectionModel().getSelections();if(b.length==0){Ext.ux.Toast.msg("信息","请选择要删除的记录！");return;}var e=Array();for(var c=0;c<b.length;c++){e.push(b[c].data.dutyId);}DutyView.remove(e);}}));}return a;};DutyView.remove=function(b){var a=Ext.getCmp("DutyGrid");Ext.Msg.confirm("信息确认","您确认要删除该记录吗？",function(c){if(c=="yes"){Ext.Ajax.request({url:__ctxPath+"/personal/multiDelDuty.do",params:{ids:b},method:"post",success:function(){Ext.ux.Toast.msg("信息提示","成功删除所选记录！");a.getStore().reload({params:{start:0,limit:25}});}});}});};DutyView.edit=function(a){new DutyForm(a);};