ConfigItemInfo = Ext.extend(Ext.TabPanel, {
	id : "configItem",
	frame : true,
	activeTab : 0,
	autoScroll : true,
	layoutOnTabChange:true,
	deferredRender : false,
	forceLayout:true,
	buttonAlign:'center',
 getBasicPanel: function(configItemId) {
 	var getExtendPanel=this.getExtendPanel;
 	var getPlanPanel=this.getPlanPanel;
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
		title : "������Ϣ",
		items : biddata
	 });
	 return basicPanel;
	},
 getFinancePanelPanel: function(configItemId) {
      var da = new DataAction();
      if(configItemId!=""){
	 	 var data = da.getPanelElementsForEdit("configItemFinanceNewModifyMix", "financeEditPanel", configItemId);
      }else{
	  	 var data = da.getPanelElementsForAdd("financeEditPanel");
	  }
	  for(var i=0;i<data.length;i++){
	  		data[i].readOnly=true;
			data[i].hideTrigger=true;
			data[i].emptyText="";
			data[i].allowBlank=true;
		}
	  var biddata = da.split(data);
		var financePanel= new Ext.form.FormPanel({
			id : 'financePanel',
			frame : true,
			layout : 'table',
			autoScroll : true,
			defaults : {
				bodyStyle : 'padding:4px'
			},
			layoutConfig : {
				columns : 4
			},
			title : "������Ϣ",
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
			this.isOrphan=result.isOrphan;
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
			Ext.MessageBox.alert("��ʾ","��ȡ��չ��Ϣʧ��");
		}
	},
	getPlanPanel:function(pid){
		var da = new DataAction();
		if(pid!=''){
	 		var data = da.getSingleFormPanelElementsForEdit("pagepanel_CIBatchModifyPlan_form",pid);
		}else{
			var data = da.getPanelElementsForAdd("pagepanel_CIBatchModifyPlan_form");
		}
		for(var i=0;i<data.length;i++){
			data[i].readOnly=true;
			data[i].hideTrigger=true;
			data[i].emptyText="";
		 }
	  	var biddata = da.split(data);
		var planPanel= new Ext.form.FormPanel({
			id : 'planPanel',
			layout : 'table',
			autoScroll : true,
			frame : true,
			defaults : {
				bodyStyle : 'padding:4px'
			},
			layoutConfig : {
				columns : 4
			},
			title : "����ƻ�",
			items : biddata
		});
		return planPanel;
	},
    items : this.items,
	initComponent : function() {
		var modifyId=this.modifyId;
		var dataId=this.dataId;
		var pid=this.pid;
	    var temp = new Array();
        temp.push(this.getBasicPanel(dataId));
        temp.push(this.getFinancePanelPanel(dataId));
        if(dataId!=""){
        	var configItemTypeId=Ext.getCmp("ConfigItem$configItemTypeCombo").getValue();
        	temp.push(this.getExtendPanel(dataId,configItemTypeId));
        	temp.push(this.getPlanPanel(pid));
        }
		this.items = temp;
		ConfigItemInfo.superclass.initComponent.call(this);
		var cisn=Ext.getCmp("ConfigItem$cisn").getValue();
		if(dataId!=""&&pid!=''&&!this.isOrphan&&cisn.trim().length!=0&&configItemTypeId.trim().length!=0){
				Ext.getCmp('configItem').addButton({
						text:'�鿴��Ҫ��ϵ',
						iconCls:'look'					
					},function(){
							var store = new Ext.data.JsonStore({ 				
								url: webContext+'/configItemAction_findCINecessaryRel.action?itemCode='+cisn+"&TypeId="+configItemTypeId+"&modifyId="+modifyId,
								fields: ['configItemType','otherConfigItemType','parentOrChildType',"isOptional","isExist"],
							    root:'data'
							}); 
							var cm = new Ext.grid.ColumnModel([
								    {header: "����������",  sortable: true, dataIndex: 'configItemType'}, 
								    {header: "��������������", sortable: true, dataIndex: 'otherConfigItemType'},
								    {header: "��ϵ����", sortable: true, dataIndex: 'parentOrChildType'},
								     {header: "�Ƿ��ѡ", sortable: true, dataIndex: 'isOptional'},
								    {header: "�Ƿ��Ѵ���",  sortable: true, dataIndex: 'isExist'}	
							]); 
							var grid = new Ext.grid.GridPanel({
									title:'���б�Ҫ��ϵ',
							        store: store,
							        cm: cm,
							        width:530,
									height:190,
							        autoScroll : true,
							        frame:true,
							        loadMask: true
							}); 			
							var maintenanceStore = new Ext.data.JsonStore({ 				
								url: webContext+'/configItemAction_findMaintenanceRel.action?configItemId='+dataId+"&modifyId="+modifyId,
								fields: ['parentType','parentCode','childType',"childCode"],
							    root:'data'
							}); 
							var maintenanceCm = new Ext.grid.ColumnModel([
								    {header: "������",  sortable: true, dataIndex: 'parentType'}, 
								    {header: "�����", sortable: true, dataIndex: 'parentCode'},
								    {header: "������", sortable: true, dataIndex: 'childType'},
								    {header: "�ӱ��",  sortable: true, dataIndex: 'childCode'}	
							]); 
							var maintenanceGrid = new Ext.grid.GridPanel({
									title:'�������������ɵı�Ҫ��ϵ',
							        store: maintenanceStore,
							        cm: maintenanceCm,
							        width:530,
									height:190,
							        autoScroll : true,
							        frame:true,
							        loadMask: true
							}); 			
							var necessaryRelWin=new Ext.Window({
								title:"��Ҫ��ϵ�鿴",
								width:561,
								height:380,
								maximizable:true,
								autoScroll :true,
								modal:true,
								buttonAlign:'center',
								items:[grid,maintenanceGrid],
								buttons:[
								{
									text:'�ر�',
									handler:function(){
										necessaryRelWin.close();
									}
								}
								]
							});
							necessaryRelWin.show();
							store.reload();
							maintenanceStore.reload();
					});
			}		
	}
})