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
 	var getSpecification=function(configItemTypeId){
	  if(configItemTypeId.trim()!=""){
	 	var specification="";
	 	var typeName="";
	 	switch (configItemTypeId){
			case "73":specification="ƽ̨����+�ص�����";typeName="�칫�ص�";break;//�칫�ص�  
			case "74":specification="�ص�+¥��+�����߹��̡�+���";typeName="���߹���";break;//���߹���  
			case "75":specification="���+��-��+�յ�+����+��-��+����+��ר�ߡ�";typeName="ר��";break;//ר��
			case "76":specification="�ص�+¥��+��������";typeName="����";break;//����
			case "77":specification="���������+��-��+IP��ַ+��-��+������+��-��+λ��";typeName="���������";break;//���������
			case "78":specification="�ص�-�ͺ�-¥��-·�������";typeName="·����";break;//·����
			case "79":specification="�ص�-�ͺ�-¥��-���������";typeName="������";break;//������
			case "80":specification="Ʒ��+��-��+�ͺ�";typeName="�洢������";break;//�洢������
			case "81":specification="�ص�+¥��+��������+�յ�Ʒ��+��-��+�ͺ�";typeName="�յ�";break;//�յ�
			case "82":specification="�ص�+Ʒ��+UPS+��-��+���";typeName="����ϵ�Դ";break;//����ϵ�Դ
			case "83":specification="�ص�+�����+��-��+��ţ���λ���֣�";typeName="�����";break;//�����
			case "84":specification="�ص�+Ʒ��+�ͺ�+��-��+PBX";typeName="�̿ؽ�����";break;//�̿ؽ�����
			case "86":specification="���ݿ�����+��-��+�汾";typeName="���ݿ�";break;//���ݿ�
			case "87":specification="�м������+��-��+�汾";typeName="�м��";break;//�м��
			case "88":specification="Ӧ���������+��-��+�汾";typeName="Ӧ�����";break;//Ӧ�����
			case "89":specification="�������+��-��+�ͺ�+��-��+��������";typeName="�ӿڿ�_���";break;//�ӿڿ�_���
			case "98":specification="�ص�+¥��+��������+�豸����+��-��+��ţ���λ���֣�";typeName="�����豸";break;//�����豸
			case "100":specification="�칫�ص�+¥��+������+��-��+������";typeName="ͨ�û���";break;//ͨ�û���
			case "105":specification="����������";typeName="�ڲ�������";break;//�ڲ�������
			case "106":specification="��Ѷ���������������";typeName="��Ѷ�����������";break;//��Ѷ�����������
			case "107":specification="�ص�+¥��+����������+��Ѷ�����ն�����";typeName="��Ѷ�����ն�";break;//��Ѷ�����ն�
			case "218":specification="����������";typeName="�ⲿ������";break;//�ⲿ������
			case "220":specification="����ʦ��������+Itcode";typeName="�ⲿ���񹤳�ʦ";break;//�ⲿ���񹤳�ʦ
			case "221":specification="Ʒ��+��-��+�ͺ�";typeName="����";break;//����
			case "222":specification="Ӧ������Ҫ����_IP��ַ_������";typeName="���������";break;//���������
			case "223":specification="�����������";typeName="�������";break;//�������
			case "224":specification="�칫�ص�+¥��+��-��+�豸���";typeName="��ȫ�����豸";break;//��ȫ�����豸
			case "228":specification="����ʦ��������+Itcode";typeName="�ڲ����񹤳�ʦ";break;//�ڲ����񹤳�ʦ
			case "266":specification="����ʦ��������+Itcode";typeName="Ӧ�ù���Ա";break;//Ӧ�ù���Ա
		}
		if(Ext.getCmp("specificationTip")!=undefined){
			Ext.getCmp("specificationTip").destroy();
		}
		new Ext.ToolTip({
			id:"specificationTip",
	 		target:"ConfigItem$name",
	 		html:specification,
	 		dismissDelay:0,
	 		trackMouse:true
	 	})
	  }
 	} 	
 	var getExtendPanel=this.getExtendPanel;
	var da = new DataAction();
	var data = da.getPanelElementsForEdit("configItemFinanceNewModifyMix", "configItemBasicPanel", configItemId);
	var configItemTypeId="";
	for(var i=0;i<data.length;i++){
		data[i].readOnly=true;
		data[i].hideTrigger=true;
		data[i].emptyText="";
		data[i].allowBlank=true;
		if(data[i].id=='ConfigItem$configItemTypeCombo'){
			if(data[i].value!=undefined){
				configItemTypeId=data[i].value;
			}
		}
	}
	Ext.getCmp("ConfigItem$name").on("render",function(component){
		getSpecification(configItemTypeId);
	})
	 var biddata = da.split(data);
	 var basicPanel= new Ext.form.FormPanel({
		id : 'basicPanel',
		layout : 'table',
		frame : true,
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
	  var data = da.getPanelElementsForEdit("configItemFinanceNewModifyMix", "financeEditPanel", configItemId);
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
			var data=da.getSingleFormPanelElementsForEdit(result.panelName,result.extendId);
			for(var i=0;i<data.length;i++){
				 data[i].readOnly=true;
				 data[i].hideTrigger=true;
				 data[i].emptyText="";
				 data[i].allowBlank=true;
				 if(data[i].id=="Server$iloRemoteManagePassword"&&!displayServerPassword){
					data[i].inputType="password"; 
					data[i].readOnly=true; 
				}
				if(data[i].id=='SpecialLine$startLocationCombo'){
					data[i].store=new Ext.data.JsonStore({
						url:webContext+"/configItemAction_findConfigItemExtendInfo.action?clazz=com.digitalchina.itil.config.extci.entity.OfficeLocation",
						fields:['id','locationName'],
						totalProperty:'rowCount',
						root:'data'});
					data[i].initComponent();
				}
				if(data[i].id=='SpecialLine$endLocationCombo'){
					data[i].store=new Ext.data.JsonStore({
						url:webContext+"/configItemAction_findConfigItemExtendInfo.action?clazz=com.digitalchina.itil.config.extci.entity.OfficeLocation",
						fields:['id','locationName'],
						totalProperty:'rowCount',
						root:'data'});
					data[i].initComponent();
				}
			}
			data = da.split(data);
			 var extendPanel= new Ext.form.FormPanel({
				id : 'extendPanel',
				frame : true,
				layout : 'table',
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
	 	var data = da.getSingleFormPanelElementsForEdit("pagepanel_CIBatchModifyPlan_form",pid);
	    for(var i=0;i<data.length;i++){
			 data[i].readOnly=true;
			 data[i].hideTrigger=true;
			 data[i].emptyText="";
			if(data[i].id=='CIBatchModifyPlan$startDate'){
				data[i]=new Ext.form.DateField({
					id:data[i].id,
					name:data[i].name,
					hideTrigger:true,
					readOnly:true,
					format:"Y-m-d H:i:s",
					width:data[i].width,
					allowBlank:data[i].allowBlank,
					fieldLabel:data[i].fieldLabel,
					value:data[i].value
				})
			}
			if(data[i].id=='CIBatchModifyPlan$endDate'){
				data[i]=new Ext.form.DateField({
					id:data[i].id,
					name:data[i].name,
					hideTrigger:true,
					readOnly:true,
					format:"Y-m-d H:i:s",
					width:data[i].width,
					allowBlank:data[i].allowBlank,
					fieldLabel:data[i].fieldLabel,
					value:data[i].value
				})
			}
		}
	  	var biddata = da.split(data);
		var planPanel= new Ext.form.FormPanel({
			id : 'planPanel',
			layout : 'table',
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
		var oldId=this.oldId;
		var title=this.title;
	    var temp = new Array();
        temp.push(this.getBasicPanel(dataId));
        temp.push(this.getFinancePanelPanel(dataId));
    	var configItemTypeId=Ext.getCmp("ConfigItem$configItemTypeCombo").getValue();
    	temp.push(this.getExtendPanel(dataId,configItemTypeId));
    	temp.push(this.getPlanPanel(pid));
		this.items = temp;
		ConfigItemInfo.superclass.initComponent.call(this);
		var cisn=Ext.getCmp("ConfigItem$cisn").getValue();
		if(oldId!=''){
			if(this.isOrphan){
					var cisn=Ext.getCmp("ConfigItem$cisn").getValue();
					var conn = Ext.lib.Ajax.getConnectionObject().conn;
					var url=webContext+"/configItemAction_findCIRel.action?oldId="+oldId;
					conn.open("post", url, false);
					conn.send(null);
					if (conn.status == "200") {
						var result = Ext.decode(conn.responseText);
						if(result.rels!=""){
							Ext.getCmp('configItem').addButton({
								text:'�鿴�������ϵ',
								iconCls:'look'					
							},function(){
			
									new Ext.Window({
										id:"relWin",
										title:"�������ϵ",
										width:350,
										height:300,
										closable :false,
										maximizable:true,
										autoScroll :true,
										modal:true,
										buttonAlign:'center',
										html :"�����������ɹ�֮��,���¹�ϵ����ɾ����"+result.rels,
										buttons:[
										{
											text:'�ر�',
											handler:function(){
												Ext.getCmp('relWin').close();
											}
										}
										]
									}).show();
							});
						}
					}
			}
			Ext.getCmp('configItem').addButton({
				text:'�鿴ԭ������',
				iconCls:'look'					
			},function(){
				var url=webContext+'/user/configItem/configItemBatchModify/configItemInfoForLook.jsp';
					var win=new Ext.Window({
						title:'ԭ��������Ϣ',
						width:700,
						frame:true,
						maximizable : true,
						autoScroll : true,
						height:350,
						modal : true,
						autoLoad : {
							url : webContext + "/tabFrame.jsp?url="+url+"?dataId="+ oldId,
							text : "ҳ�����ڼ�����......",
							method : 'post',
							scope : this
						},
						buttonAlign:"center",
						buttons:[{
							text:'�ر�',
							handler:function(){
								win.close();
							}
						}
						]
					});
					win.show();
			});
		}
		if(dataId!=""&&!this.isOrphan&&cisn.trim().length!=0&&configItemTypeId.trim().length!=0){
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