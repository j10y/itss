ConfigItemInfo = Ext.extend(Ext.TabPanel, {
	id : "configItem",
	frame : true,
	activeTab : 0,
	closable:true,
	layoutOnTabChange:true,
	deferredRender : false,
	forceLayout:true,
	buttonAlign:'center',
	
 getBasicPanel: function(configItemId) {
	var da = new DataAction();
	var data = da.getPanelElementsForEdit("configItemFinanceNewModifyMix", "configItemBasicPanel", configItemId);
	for(var i=0;i<data.length;i++){
		data[i].id=data[i].id,
		data[i].readOnly=true;
		data[i].hideTrigger=true;
		data[i].emptyText="";
		data[i].allowBlank=true;
	}
	 var biddata = da.split(data);
	 var basicPanel= new Ext.form.FormPanel({
		id : 'basicPanel',
		layout : 'table',
		frame:true,
		autoScroll : true,
		defaults : {
			bodyStyle : 'padding:4px'
		},
		layoutConfig : {
			columns : 4
		},
		title : "基础信息",
		items : biddata
	 });
	 return basicPanel;
	},
 getFinancePanelPanel: function(configItemId) {
      var da = new DataAction();
	  var data = da.getPanelElementsForEdit("configItemFinanceNewModifyMix", "financeEditPanel", configItemId);
	  for(var i=0;i<data.length;i++){
	   	 data[i].id=data[i].id,
		 data[i].readOnly=true;
		 data[i].hideTrigger=true;
		 data[i].emptyText="";
		 data[i].allowBlank=true;
	  }
	  var biddata = da.split(data);
	  var financePanel= new Ext.form.FormPanel({
			id : 'financePanel',
			layout : 'table',
			frame:true,
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
			var data=da.getSingleFormPanelElementsForEdit(result.panelName,result.extendId);
			for(var i=0;i<data.length;i++){
				data[i].id=data[i].id,
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
				frame:true,
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
    	var configItemTypeId=Ext.getCmp("ConfigItem$configItemTypeCombo").getValue();
    	temp.push(this.getExtendPanel(this.dataId,configItemTypeId));
		this.items = temp;
		ConfigItemInfo.superclass.initComponent.call(this);
	}
})