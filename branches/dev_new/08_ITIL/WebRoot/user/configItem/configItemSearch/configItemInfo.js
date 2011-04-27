PageTemplates = Ext.extend(Ext.TabPanel, {
	activeTab : 0,
	frame : true,
	deferredRender : false,
	forceLayout:true,
	width : 730,
	id:"configItem",
	buttonAlign:'center',
	buttons:[
	  {
		text:'返回',
		iconCls:'back',
		handler:function(){
			window.location=webContext+"/user/configItem/configItemSearch/configItemList.jsp";
	 	}
	 }
	],
	autoScroll : true,
	defaults : {
		bodyStyle : 'padding:2px'
	},
 getBasicPanel: function(configItemId) {
 	var getExtendPanel=this.getExtendPanel;
	 var da = new DataAction();
	 if(configItemId!="")
	 	var data = da.getPanelElementsForEdit("configItemFinanceNewModifyMix", "configItemBasicPanel", configItemId);
	 else
		var data = da.getPanelElementsForAdd("configItemBasicPanel");
	for(var i=0;i<data.length;i++){
      		data[i].readOnly=true;
			data[i].hideTrigger=true;
			data[i].emptyText="";
			data[i].allowBlank=true;
	 }
	 var biddata = da.split(data);
	 var basicPanel= new Ext.form.FormPanel({
		id : 'basicPanel',
		layout : 'table',
		frame : true,
		autoScroll : true,
		defaults : {
			bodyStyle : 'padding:4px'
		},
		layoutConfig : {
			columns : 4
		},
		title : "配置项基础信息",
		items : biddata
	 });
	Ext.getCmp("ConfigItem$configItemTypeCombo").addListener('select',function(combo,record,index){
		var configItemTab=Ext.getCmp("configItem");
		if(Ext.getCmp("extendPanel")!=undefined)
			configItemTab.remove("extendPanel");
		var extendPanel =getExtendPanel(configItemId,record.get('id'));
		configItemTab.add(extendPanel);
		configItemTab.doLayout();
	});
	 return basicPanel;
	},
 getFinancePanelPanel: function(configItemId) {
      var da = new DataAction();
      if(this.dataId!="")
	 	 var data = da.getPanelElementsForEdit("configItemFinanceNewModifyMix", "financeEditPanel", configItemId);
	  else
	  	 var data = da.getPanelElementsForAdd("financeEditPanel");
  	 for(var i=0;i<data.length;i++){
      		data[i].readOnly=true;
			data[i].hideTrigger=true;
			data[i].emptyText="";
			data[i].allowBlank=true;
	   }
	  var biddata = da.split(data);
		var financePanel= new Ext.form.FormPanel({
			id : 'financePanel',
			layout : 'table',
			frame : true,
			autoScroll : true,
			defaults : {
				bodyStyle : 'padding:4px'
			},
			layoutConfig : {
				columns : 4
			},
			title : "财务信息",
			items : biddata
		});
		return financePanel;
	},
	getExtendPanel:function(configItemId,configItemTypeId){
		var conn = Ext.lib.Ajax.getConnectionObject().conn;
		var url=webContext+"/configItemAction_getPagePanelName.action?configItemTypeId="+configItemTypeId+"&configItemId="+configItemId;
		conn.open("post", url, false);
		conn.send(null);
		if (conn.status == "200") {
			var responseText = conn.responseText;
			var da = new DataAction();
			var result=Ext.decode(responseText);
			var displayServerPassword=result.displayServerPassword;
			var data;
			if(result.extendId!=undefined){
				data=da.getSingleFormPanelElementsForEdit(result.panelName,result.extendId);
			}else{
				data=da.getPanelElementsForAdd(result.panelName);
			}
			 for(var i=0;i<data.length;i++){
	      		data[i].readOnly=true;
				data[i].hideTrigger=true;
				data[i].emptyText="";
				data[i].allowBlank=true;
				 if(data[i].id=="Server$iloRemoteManagePassword"&&!displayServerPassword){
					data[i].inputType="password"; 
					data[i].readOnly=true; 
				}				
		   	 }
			data = da.split(data);
			 var extendPanel= new Ext.form.FormPanel({
				id : 'extendPanel',
				layout : 'table',
				frame : true,
				autoScroll : true,
				defaults : {
					bodyStyle : 'padding:4px'
				},
				layoutConfig : {
					columns : 4
				},
				title : result.panelTitle,
				items:data
	 		});
			return extendPanel;
		} else {
			Ext.MessageBox.alert("提示","获取扩展信息失败");
		}
	},
    items : this.items,
	initComponent : function() {
	    var temp = new Array();
        temp.push(this.getBasicPanel(this.dataId));
        temp.push(this.getFinancePanelPanel(this.dataId));
        if(this.dataId!=""){
        	var configItemTypeId=Ext.getCmp("ConfigItem$configItemTypeCombo").getValue();
        	temp.push(this.getExtendPanel(this.dataId,configItemTypeId));
        }
		this.items = temp;
		PageTemplates.superclass.initComponent.call(this);
	}
})