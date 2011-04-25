var findTechnoInfo=function(){
	var parentConfigItemType=Ext.getCmp('parentTypeId').getValue();
	var childConfigItemType=Ext.getCmp('childTypeId').getValue();
	if(parentConfigItemType!=""&&childConfigItemType!=""){
		Ext.Ajax.request({
			url:webContext+"/configItemAction_findTechnoInfo.action",
			params:{
				parentConfigItemType:parentConfigItemType,
				childConfigItemType:childConfigItemType
			},
			success : function(response, options) {
				var result=response.responseText;
				if(result.trim().length!=0){
					result=Ext.decode(result);
					var atechnoInfoDisplayName=result.atechnoInfoDisplayName.trim();
					var atechnoInfoAllowBlank=result.atechnoInfoAllowBlank.trim();
					var atechnoInfoTip=result.atechnoInfoTip.trim();
					var atechnoInfo=result.atechnoInfo.trim();
					var btechnoInfoDisplayName=result.btechnoInfoDisplayName.trim();
					var btechnoInfoAllowBlank=result.btechnoInfoAllowBlank.trim();
					var btechnoInfoTip=result.btechnoInfoTip.trim();
					var btechnoInfo=result.btechnoInfo.trim();
					if(atechnoInfoDisplayName.length!=0){
						Ext.getDom("atechnoInfoLabel").innerHTML=atechnoInfoDisplayName;
					}else{
						Ext.getDom("atechnoInfoLabel").innerHTML="A�˼�����Ϣ";
					}
					if(atechnoInfoAllowBlank.length!=0){
						if(atechnoInfoAllowBlank=="1"){
							var atechnoInfoField=Ext.getCmp("atechnoInfo");
							atechnoInfoField.allowBlank=false;
							atechnoInfoField.blankText="";
							atechnoInfoField.invalidText="";
							atechnoInfoField.validate();
						}else{
							var atechnoInfoField=Ext.getCmp("atechnoInfo");
							atechnoInfoField.allowBlank=true;
							atechnoInfoField.blankText="";
							atechnoInfoField.invalidText="";
							atechnoInfoField.validate();
						}
					}else{
						var atechnoInfoField=Ext.getCmp("atechnoInfo");
						atechnoInfoField.allowBlank=true;
						atechnoInfoField.blankText="";
						atechnoInfoField.invalidText="";
						atechnoInfoField.validate();
					}
					if(atechnoInfoTip.length!=0){
						if(Ext.getCmp("atechnoInfoTip")!=undefined){
							Ext.getCmp("atechnoInfoTip").destroy();
						}
						new Ext.ToolTip({
							id:"atechnoInfoTip",
					 		target:"atechnoInfo",
					 		html:atechnoInfoTip,
					 		dismissDelay:0,
					 		trackMouse:true
					 	})
					}else{
						if(Ext.getCmp("atechnoInfoTip")!=undefined){
							Ext.getCmp("atechnoInfoTip").destroy();
						}
					}
					
					if(atechnoInfo.length!=0&&Ext.getCmp("atechnoInfo").getValue().trim()==""){
						Ext.getCmp("atechnoInfo").setValue(atechnoInfo);
					}
					if(btechnoInfoDisplayName.length!=0){
						Ext.getDom("btechnoInfoLabel").innerHTML=btechnoInfoDisplayName;
					}else{
						Ext.getDom("btechnoInfoLabel").innerHTML="B�˼�����Ϣ";
					}
					if(btechnoInfoAllowBlank.length!=0){
						if(btechnoInfoAllowBlank=="1"){
							var btechnoInfoField=Ext.getCmp("btechnoInfo");
							btechnoInfoField.allowBlank=false;
							btechnoInfoField.blankText="";
							btechnoInfoField.invalidText="";
							btechnoInfoField.validate();
						}else{
							var btechnoInfoField=Ext.getCmp("btechnoInfo");
							btechnoInfoField.allowBlank=true;
							btechnoInfoField.blankText="";
							btechnoInfoField.invalidText="";
							btechnoInfoField.validate();
						}
					}else{
						var btechnoInfoField=Ext.getCmp("btechnoInfo");
						btechnoInfoField.allowBlank=true;
						btechnoInfoField.blankText="";
						btechnoInfoField.invalidText="";
						btechnoInfoField.validate();						
					}
					if(btechnoInfoTip.length!=0){
						if(Ext.getCmp("btechnoInfoTip")!=undefined){
							Ext.getCmp("btechnoInfoTip").destroy();
						}
						new Ext.ToolTip({
							id:"btechnoInfoTip",
					 		target:"btechnoInfo",
					 		html:btechnoInfoTip,
					 		dismissDelay:0,
					 		trackMouse:true
					 	})
					}else{
						if(Ext.getCmp("btechnoInfoTip")!=undefined){
							Ext.getCmp("btechnoInfoTip").destroy();
						}
					}
					if(btechnoInfo.length!=0&&Ext.getCmp("btechnoInfo").getValue().trim()==""){
						Ext.getCmp("btechnoInfo").setValue(btechnoInfo);
					}
				}else{
					Ext.getDom("atechnoInfoLabel").innerHTML="A�˼�����Ϣ";
					var atechnoInfoField=Ext.getCmp("atechnoInfo");
					atechnoInfoField.allowBlank=true;
					atechnoInfoField.blankText="";
					atechnoInfoField.invalidText="";
					atechnoInfoField.validate();
					if(Ext.getCmp("atechnoInfoTip")!=undefined){
						Ext.getCmp("atechnoInfoTip").destroy();
					}					
					Ext.getDom("btechnoInfoLabel").innerHTML="B�˼�����Ϣ";
					var btechnoInfoField=Ext.getCmp("btechnoInfo");
					btechnoInfoField.allowBlank=true;
					btechnoInfoField.blankText="";
					btechnoInfoField.invalidText="";
					btechnoInfoField.validate();
					if(Ext.getCmp("btechnoInfoTip")!=undefined){
						Ext.getCmp("btechnoInfoTip").destroy();
					}					
				}
			},
			failure : function(response, options) {
				Ext.getDom("atechnoInfoLabel").innerHTML="A�˼�����Ϣ";
				var atechnoInfoField=Ext.getCmp("atechnoInfo");
				atechnoInfoField.allowBlank=true;
				atechnoInfoField.blankText="";
				atechnoInfoField.invalidText="";
				atechnoInfoField.validate();
				if(Ext.getCmp("atechnoInfoTip")!=undefined){
					Ext.getCmp("atechnoInfoTip").destroy();
				}					
				Ext.getDom("btechnoInfoLabel").innerHTML="B�˼�����Ϣ";
				var btechnoInfoField=Ext.getCmp("btechnoInfo");
				btechnoInfoField.allowBlank=true;
				btechnoInfoField.blankText="";
				btechnoInfoField.invalidText="";
				btechnoInfoField.validate();
				if(Ext.getCmp("btechnoInfoTip")!=undefined){
					Ext.getCmp("btechnoInfoTip").destroy();
				}					
			}
		})
	}
}
SelectRelItemPanel=Ext.extend(Ext.Panel, {
	id:'selectRelItemPanel',
	title:'ѡ������',
	layout:'border',
	width:500,
	height:400,
	//region:'center',
	initComponent: function(){
		var modifyId=Ext.getCmp('relationshipPanel').modifyId;
		var	searchConfigItem= function() {
			var item = Ext.getCmp('relSelectItem').getValue();
			var name = Ext.getCmp('relItemName').getValue();
			var type = Ext.getCmp('relItemType').getValue();
			var code = Ext.getCmp('relItemCode').getValue();
			var param = {
						item : item,
						name : name,
						type:type,
						code:code,
						start:0
				};
			store.baseParams=param;
			store.load();
		}
		this.searchConfigItem=searchConfigItem;
		var store = new Ext.data.JsonStore({ 				
						url: webContext+'/configItemAction_findItem.action?modifyId='+modifyId,
						fields: ['id','name','Type','TypeId','itemCode'],//configType
					    root:'data',
					    totalProperty : 'rowCount',
						sortInfo: {field: "id", direction: "ASC"}
		}); 
		var sm = new Ext.grid.CheckboxSelectionModel();		
		var cm = new Ext.grid.ColumnModel([sm,
				    {header: "����",  sortable: true, dataIndex: 'name'}, 
				    {header: "����", sortable: true, dataIndex: 'Type'},
				    {header: "���", sortable: true, dataIndex: 'itemCode'},
				    {header: "id",  sortable: true, dataIndex: 'id',hidden:true}	
			]); 
				
			var selectItem = new Ext.form.ComboBox({
					store:new Ext.data.SimpleStore({
			        		fields:['id','name'],
			        		data:[["ci","������"],["si","������"]]
			        }),
	        		id : "relSelectItem",
					valueField : "id",
					displayField : "name",
		            emptyText: '��ѡ��',
		            value:"ci",
		            width:150,
					mode : 'local',
					hiddenName : "item",
					editable : false,
					triggerAction : 'all', 
					allowBlank : true,
					listeners: {
								select: function(combo, record, index){
									Ext.getCmp("relItemType").clearValue();
						           	searchConfigItem();
							    },  						    
								scope: this
							}
				});	
			var itemName = new Ext.form.TextField({
				id:"relItemName",
				width:150,
				name : 'name'
			});
				
			var itemType = new Ext.form.ComboBox({
				store : new Ext.data.JsonStore({
					url: webContext+'/configItemAction_findItemTypeByItem.action',
					fields: ['id','name'],
				    root:'data',
					sortInfo: {field: "id", direction: "ASC"}
				}),
				id:'relItemType',
				valueField : "id",
				displayField : "name",
	            emptyText: '��ѡ��',
				mode : 'remote',
				maxHeight:200,
				width:150,
				resizable:true,
				hiddenName : "type",
				editable : false,
				triggerAction : 'all', 
				allowBlank : true,
				listeners: {
					select: function(combo, record, index){
			           	searchConfigItem();
				    },
				    beforequery  :function(queryEvent){
						var item=Ext.getCmp('relSelectItem').getValue();
						if(item==""){
						   Ext.Msg.alert("��ʾ",'��ѡ��ģ��!');
						}else{
							queryEvent.combo.store.baseParams={
								item:item
							};
							queryEvent.combo.store.reload();
						}
						return false;
					}
				}
			});
				
			var itemCode = new Ext.form.TextField({
				id:"relItemCode",
				width:150,
				name : 'code'
			});
				
			var pageBar = new Ext.PagingToolbar({
					pageSize : 10,
					store : store,
					displayInfo : true,
					displayMsg : '��ǰ��ʾ {0}-{1}����¼ /��{2}����¼',
					emptyMsg : '����ʾ����',
					plugins :new Ext.ux.ProgressBarPager()
			});
				
			var searchForm = new Ext.form.FormPanel({
				id : "relItemSearch",
				layout : 'table',
				region:'north',
				height:61,
				autoScroll: true,
				frame : true,
				keys:{
				    key:Ext.EventObject.ENTER,
				    fn: function(){
				    	searchConfigItem();
				    },
				    scope: this
				},
				defaults : {
					bodyStyle : 'padding:4px'
				},
				layoutConfig : {columns: 4},
				items : [
					{html: "&nbsp;&nbsp;ģ��:" ,cls: 'common-text', style:'width:50;height:20;text-align:left;'},
					selectItem,
					{html: "&nbsp;&nbsp;����:" ,cls: 'common-text', style:'width:50;height:20;text-align:left;'},
					itemType,
					{html: "&nbsp;&nbsp;����:" ,cls: 'common-text', style:'width:50;height:20;text-align:left;'},
					itemName,
					{html: "&nbsp;&nbsp;���:" ,cls: 'common-text', style:'width:50;height:20;text-align:left;'},
					itemCode
					]
			});
				var grid = new Ext.grid.GridPanel({
						id :'addRelItemGrid',
				        store: store,
				        sm:sm,
				        cm: cm,
				        region:'center',
				        frame:true,
				        loadMask: true,
				        tbar : ['   ', {
						text : ' ��ѯ',
						handler : searchConfigItem,
						scope : this,
						iconCls : 'search',
						cls : "x-btn-text-icon"
						},"-",
						{
						text : ' ����',
						handler : function(){
							searchForm.form.reset();
						},
						scope : this,
						iconCls : 'reset',
						cls : "x-btn-text-icon"
						},"-",
						{
						text : ' �鿴',
						handler : function(){
							grid.fireEvent('rowdblclick',grid);
						},
						scope : this,
						iconCls : 'look',
						cls : "x-btn-text-icon"
						},"-",
						{
						text : '��Ϊ��',
						handler : function(){
							var records = grid.getSelectionModel().getSelections();
							if(records.length==0){
								Ext.Msg.alert("��ʾ","��ѡ��������!");
								return;
							}
							if(records.length>1){
								Ext.Msg.alert("��ʾ","ֻ��ѡ��һ��������!");
								return;
							}
							if(Ext.getCmp('childItem').getValue()=='si'&&Ext.getCmp("relSelectItem").getValue()=='ci'){
								Ext.Msg.alert("��ʾ","���������Ϊ������ĸ�!");
								return;
							}
							var itemCode=records[0].get('itemCode');
							var itemName=records[0].get('name');
							var Type=records[0].get('Type');
							var TypeId=records[0].get('TypeId');
							if(itemCode==Ext.getCmp('childCode').getValue()){
								Ext.Msg.alert("��ʾ","�����Ӳ�����ͬ!");
								return;
							}
							if(Ext.getCmp('parentCode').getValue()!=""){
								Ext.Msg.confirm("��ʾ","��ϵ���������ݣ�ȷ�ϸ�����",function(button){
									if(button=='yes'){
										Ext.getCmp('parentItem').setValue(Ext.getCmp("relSelectItem").getValue());
										Ext.getCmp('parentName').setValue(itemName);
										Ext.getCmp('parentCode').setValue(itemCode);
										Ext.getCmp('parentType').setValue(Type);
										Ext.getCmp('parentTypeId').setValue(TypeId);
										findTechnoInfo();
									}
								})
							}else{
								Ext.getCmp('parentItem').setValue(Ext.getCmp("relSelectItem").getValue());
								Ext.getCmp('parentName').setValue(itemName);
								Ext.getCmp('parentCode').setValue(itemCode);
								Ext.getCmp('parentType').setValue(Type);
								Ext.getCmp('parentTypeId').setValue(TypeId);
								findTechnoInfo();
							}
						},
						scope : this,
						iconCls : 'add',
						cls : "x-btn-text-icon"
						},"-",
						{
						text : '��Ϊ��',
						handler : function(){
							var records = grid.getSelectionModel().getSelections();
							if(records.length==0){
								Ext.Msg.alert("��ʾ","��ѡ��������!");
								return;
							}
							if(records.length>1){
								Ext.Msg.alert("��ʾ","ֻ��ѡ��һ��������!");
								return;
							}
							if(Ext.getCmp('parentItem').getValue()=='ci'&&Ext.getCmp("relSelectItem").getValue()=='si'){
								Ext.Msg.alert("��ʾ","���������Ϊ������ĸ�!");
								return;
							}
							var itemCode=records[0].get('itemCode');
							var itemName=records[0].get('name');
							var Type=records[0].get('Type');
							var TypeId=records[0].get('TypeId');
							if(itemCode==Ext.getCmp('parentCode').getValue()){
								Ext.Msg.alert("��ʾ","�����Ӳ�����ͬ!");
								return;
							}
							if(Ext.getCmp('childCode').getValue()!=""){
								Ext.Msg.confirm("��ʾ","��ϵ���������ݣ�ȷ�ϸ�����",function(button){
									if(button=='yes'){
										Ext.getCmp('childItem').setValue(Ext.getCmp("relSelectItem").getValue());
										Ext.getCmp('childName').setValue(itemName);
										Ext.getCmp('childCode').setValue(itemCode);
										Ext.getCmp('childType').setValue(Type);
										Ext.getCmp('childTypeId').setValue(TypeId);
										findTechnoInfo();
									}
								})
							}else{
								Ext.getCmp('childItem').setValue(Ext.getCmp("relSelectItem").getValue());
								Ext.getCmp('childName').setValue(itemName);
								Ext.getCmp('childCode').setValue(itemCode);
								Ext.getCmp('childType').setValue(Type);
								Ext.getCmp('childTypeId').setValue(TypeId);
								findTechnoInfo();
							}
						},
						scope : this,
						iconCls : 'add',
						cls : "x-btn-text-icon"
						}
						],
						bbar : pageBar
				}); 
				grid.on('rowdblclick',function(grid,rowIndex,eventObject){
					var records = grid.getSelectionModel().getSelections();
					if(records.length==0){
						Ext.Msg.alert("��ʾ","��ѡ��������!");
						return;
					}
					if(records.length>1){
						Ext.Msg.alert("��ʾ","ֻ��ѡ��һ��������!");
						return;
					}
					var itemsId=records[0].get('id');
					var url=webContext+'/user/configItem/configItemBatchModify/configItemInfoForLook.jsp';
					var win=new Ext.Window({
						title:'��������Ϣ',
						width:730,
						frame:true,
						maximizable : true,
						autoScroll : true,
						height:350,
						modal : true,
						autoLoad : {
							url : webContext + "/tabFrame.jsp?url="+url+"?dataId="+ itemsId,
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
				})
			var param = {
				item : "ci",
				start: 0
			};
			store.baseParams=param;
			store.load()
			this.items = [searchForm,grid];	
		SelectRelItemPanel.superclass.initComponent.call(this);	
	}
})
RelationshipPanel = Ext.extend(Ext.TabPanel, {
	id : "relationshipPanel",	
	frame : true,
	activeTab : 0,
	autoScroll : true,
	layoutOnTabChange:true,
	deferredRender : false,
	forceLayout:true,
	buttonAlign:'center',
	buttons:[
		  	{
			text:'�����ϵ��Ϣ',
			iconCls:'save',
			handler:function(){
				if(!Ext.getCmp('relPanel').form.isValid()||
				   !Ext.getCmp('relPlanPanel').form.isValid()){
					Ext.MessageBox.alert("��ʾ",'����д����Ϣ����������ȷ.');
					return;
				}
				if(Ext.getCmp('CIBatchModifyPlan$startDate').getValue()>Ext.getCmp('CIBatchModifyPlan$endDate').getValue()){
					Ext.MessageBox.alert("��ʾ",'����ƻ��Ŀ�ʼʱ�䲻�ܴ��ڽ���ʱ��.');
					return;
				}
				var saveRelButton=this;
				saveRelButton.disable();
				var modifyId=Ext.getCmp('relationshipPanel').modifyId;
				var rid=Ext.getCmp('relationshipPanel').rid;
				var oldRid=Ext.getCmp('relationshipPanel').oldRid;
				if(Ext.getCmp('childCode').getValue()==''||Ext.getCmp('parentCode').getValue()==''){
					Ext.Msg.alert("��ʾ","��ѡ��������!",function(){
						saveRelButton.enable();
					});
					return;
				}
				var parentItem=Ext.getCmp('parentItem').getValue();
				var childItem=Ext.getCmp('childItem').getValue();
				var parentCode=Ext.getCmp('parentCode').getValue();
				var childCode=Ext.getCmp('childCode').getValue();
				var conn = Ext.lib.Ajax.getConnectionObject().conn;
				var url=webContext+"/configItemAction_relValidate.action?parentCode="+parentCode+
																		"&childCode="+childCode+
																		'&modifyId='+modifyId+
																		"&rid="+rid+
																		"&oldRid="+oldRid+
																		"&parentItem="+parentItem+
																		"&childItem="+childItem;
				conn.open("post", url, false);
				conn.send(null);
				if (conn.status == "200") {
					var responseText = conn.responseText;
					if(responseText.trim()!=''){
						Ext.Msg.alert("��ʾ",responseText,function(){
							saveRelButton.enable();
						});
						return;
					}
				} else {
					Ext.Msg.alert("��ʾ","ϵͳ�쳣",function(){
						saveRelButton.enable();
					});
					return;
				}
				var modifyId=Ext.getCmp('relationshipPanel').modifyId;
				var title=Ext.getCmp('relationshipPanel').title;
				var rel=getFormParam("relPanel");
				if(rel.parentItem=='ci'){
					rel.parentConfigItemType=rel.parentTypeId;
					rel.parentConfigItemCode=rel.parentCode;
					rel.parentServiceItemCode="";
					rel.parentServiceItemType="";
				}else if(rel.parentItem=='si'){
					rel.parentServiceItemType=rel.parentTypeId;
					rel.parentServiceItemCode=rel.parentCode;
					rel.parentConfigItemCode="";
					rel.parentConfigItemType="";
				}else{
					rel.parentConfigItemType="";
					rel.parentConfigItemCode="";
					rel.parentServiceItemCode="";
					rel.parentServiceItemType="";
				}
				if(rel.childItem=='ci'){
					rel.childConfigItemType=rel.childTypeId;
					rel.childConfigItemCode=rel.childCode;
					rel.childServiceItemCode="";
					rel.childServiceItemType="";
				}else if(rel.childItem=='si'){
					rel.childServiceItemType=rel.childTypeId;
					rel.childServiceItemCode=rel.childCode;
					rel.childConfigItemCode="";
					rel.childConfigItemType="";
				}else{
					rel.childConfigItemType="";
					rel.childConfigItemCode="";
					rel.childServiceItemCode="";
					rel.childServiceItemType="";
				}
				var relPanel=Ext.encode(rel);
				var relPlanPanel=Ext.encode(getFormParam("relPlanPanel"));
				Ext.Ajax.request({
					url:webContext+"/configItemAction_saveOrUpdateRelAndPlan.action",
					params:{
						relPanel:relPanel,
						relPlanPanel:relPlanPanel,
						modifyId:modifyId,
						oldRid:oldRid
					},
					success : function(response, options) {
						Ext.Msg.alert("��ʾ","�����ϵ��Ϣ�ɹ���",function(){
							saveRelButton.enable();
							window.parent.Ext.getCmp('modifyRelGrid').getStore().reload();
							var modifyTab=window.parent.Ext.getCmp('modifyTab');
							modifyTab.remove(modifyTab.getActiveTab());							
/*							var rid=Ext.decode(response.responseText).id;
							var pid=Ext.decode(response.responseText).pid;
							var url=webContext+'/user/configItem/configItemBatchModify/configItemRelCreate.jsp?rid='+rid+"&modifyId="+modifyId+"&pid="+pid+"&oldRid="+oldRid;
							window.location=url;*/
						});
					},
					failure : function(response, options) {
						Ext.Msg.alert("��ʾ","�����ϵ��Ϣʧ�ܣ�",function(){
							saveRelButton.enable();
						});
					}
				});
			}
		  },{
			text:'���ù�ϵ��Ϣ',
			iconCls:'reset',
			handler:function(){
				window.location.reload();
				/*Ext.getCmp('relPanel').form.findField('parentType').reset();
				Ext.getCmp('relPanel').form.findField('parentTypeId').reset();
				Ext.getCmp('relPanel').form.findField('parentItem').reset();
				Ext.getCmp('relPanel').form.findField('childType').reset();
				Ext.getCmp('relPanel').form.findField('childTypeId').reset();
				Ext.getCmp('relPanel').form.findField('childItem').reset();
				Ext.getCmp('relPanel').form.findField('parentName').reset();
				Ext.getCmp('relPanel').form.findField('parentCode').reset();
				Ext.getCmp('relPanel').form.findField('childName').reset();
				Ext.getCmp('relPanel').form.findField('childCode').reset();
				Ext.getCmp('relPanel').form.findField('relationShipType').setValue(6);
				Ext.getCmp('relPanel').form.findField('relationShipType').setRawValue("����");
				Ext.getCmp('relPanel').form.findField('relationShipGrade').setValue(2);
				Ext.getCmp('relPanel').form.findField('relationShipGrade').setRawValue("����");
				Ext.getCmp('relPanel').form.findField('attachQuotiety').reset();
				Ext.getCmp('relPanel').form.findField('atechnoInfo').reset();
				Ext.getCmp('relPanel').form.findField('btechnoInfo').reset();
				Ext.getCmp('relPanel').form.findField('otherInfo').reset();*/
			}
		}
		],
	getModifyRelPanel:function(rid,modifyId){
		this.parentType = new Ext.form.TextField({
			id:'parentType',
    		width: 200,
    		readOnly:true,
    		name :'parentType'
    	});
		this.parentName = new Ext.form.TextField({
			id:'parentName',
    		width: 200,
    		readOnly:true,
    		name :'parentName'
    	});
		this.parentCode = new Ext.form.TextField({
			id:'parentCode',
    		width: 200,
    		readOnly:true,
    		name :'parentCode'
    	});
    	
		this.childType = new Ext.form.TextField({
    		width: 200,
    		readOnly:true,
    		name :'childType',
    		id :'childType'
    	});
		this.childName = new Ext.form.TextField({
    		width: 200,
    		readOnly:true,
    		name :'childName',
    		id :'childName'
    	});
		this.childCode = new Ext.form.TextField({
    		width: 200,
    		readOnly:true,
    		name :'childCode',
    		id :'childCode'
    	});
		var relationRelStore = new Ext.data.JsonStore({
			url: webContext+'/configItemAction_findAllRelationType.action',
			fields: ['id','name'],
	    	root:'data',									
			sortInfo: {field: "id", direction: "ASC"}
		});
		
		var relationGraStore = new Ext.data.JsonStore({
			url: webContext+'/configItemAction_findAllRelationGrade.action',
			fields: ['id','name'],
	    	root:'data',		
			sortInfo: {field: "id", direction: "ASC"}
		});
    	this.relationType = new Ext.form.ComboBox({		
    		id:'relType',
    		store : relationRelStore,
    		listeners :{
				afterrender :function(comp){
					if(rid==''){
						comp.setValue(6);
						comp.setRawValue("����");
					}
				}
			},
			valueField : "id",
			displayField : "name",
            emptyText:"��ѡ��...",
			hiddenName : "relationShipType",
			editable : false,
			triggerAction : 'all', 
			name : "relationShipType",
			width: 200
    	});
    	this.relationGrade = new Ext.form.ComboBox({
    		id:'relGrade',
    		store : relationGraStore,
			valueField : "id",
			displayField : "name",
			emptyText:"��ѡ��...",
			hiddenName : "relationShipGrade",
			editable : false,
			triggerAction : 'all', 
			name : "relationShipGrade",
			width: 200,
			listeners :{
				afterrender:function(comp){
					if(rid==''){
						comp.setValue(2);
						comp.setRawValue("����");
					}
				}
			}
    	});
    	this.attachQuotiety = new Ext.form.NumberField({
    		width: 200,
    		name :'attachQuotiety'
    	});
    	this.atechnoInfo = new Ext.form.TextField({
    		id:"atechnoInfo",
    		width: 200,
    		name :'atechnoInfo'
    	});
    	this.btechnoInfo = new Ext.form.TextField({
    		id:"btechnoInfo",
    		width: 200,
    		name :'btechnoInfo'
    	});
    	this.otherInfo = new Ext.form.TextField({
    		width: 200,
    		name :'otherInfo'
    	});
   		this.relPanel = new Ext.form.FormPanel({
   				id : 'relPanel',
				layout: 'table',
				autoScroll: true,
				height:400,
				width:300,
				defaults : {
					bodyStyle : 'padding:4px'
				},
				layoutConfig : {
					columns : 2
				},
				reader: new Ext.data.JsonReader({
			    		root: 'data'
			    },[{
		              name: 'id',
		              mapping: 'id'
		            },{
		              name: 'parentType',
		              mapping: 'parentType'
		            },{
		              name: 'parentTypeId',
		              mapping: 'parentTypeId'
		            },{
		              name: 'parentName',
		              mapping: 'parentName'
		            },{
		              name: 'parentItem',
		              mapping: 'parentItem'
		            },{
		              name: 'childTypeId',
		              mapping: 'childTypeId'
		            },{
		              name: 'childType',
		              mapping: 'childType'
		            },{
		              name: 'childItem',
		              mapping: 'childItem'
		            },{
		              name: 'parentCode',
		              mapping: 'parentCode'
		            },{
		              name: 'childName',
		              mapping: 'childName'
		            },{
		              name: 'childCode',
		              mapping: 'childCode'
		            },
			    	{
		              name: 'relationShipType',
		              mapping: 'relationShipType'
		            },{
		              name: 'relationShipGrade',
		              mapping: 'relationShipGrade'
		            },{
		              name: 'attachQuotiety',
		              mapping: 'attachQuotiety'
		            },
		              {
		              name: 'atechnoInfo',
		              mapping: 'atechnoInfo'
		            },{
		              name: 'btechnoInfo',
		              mapping: 'btechnoInfo'
		            },{
		              name: 'otherInfo',
		              mapping: 'otherInfo'
		            },{
		              name: 'status',
		              mapping: 'status'
		            },{
		              name: 'createUser',
		              mapping: 'createUser'
		            },{
		              name: 'createDate',
		              mapping: 'createDate'
		            },{
		              name: 'modifyUser',
		              mapping: 'modifyUser'
		            },{
		              name: 'modifyDate',
		              mapping: 'modifyDate'
		            }
		        ]),
				items:[
					{html: "������:&nbsp;",cls: 'common-text',style:'width:90;text-align:left;'},	
					this.parentType,
					{html: "������:&nbsp;",cls: 'common-text',style:'width:90;text-align:left;'},	
					this.parentName,
					{html: "�����:&nbsp;",cls: 'common-text',style:'width:90;text-align:left;'},	
					this.parentCode,
					{html: "������:&nbsp;",cls: 'common-text',style:'width:90;text-align:left;'},	
					this.childType,
					{html: "������:&nbsp;",cls: 'common-text',style:'width:90;text-align:left;'},	
					this.childName,
					{html: "�ӱ��:&nbsp;",cls: 'common-text',style:'width:90;text-align:left;'},	
					this.childCode,									
					{html: "��ϵ����:&nbsp;",cls: 'common-text',style:'width:90;text-align:left;'},	
					this.relationType,									
					{html: "��ϵ����:&nbsp;",cls: 'common-text',style:'width:90;text-align:left;'},	
					this.relationGrade,										
					{html: "<span id='atechnoInfoLabel'>A�˼�����Ϣ</span>:&nbsp;",cls: 'common-text',style:'width:90;text-align:left;'},	
					this.atechnoInfo,
					{html: "<span id='btechnoInfoLabel'>B�˼�����Ϣ</span>:&nbsp;",cls: 'common-text',style:'width:90;text-align:left;'},	
					this.btechnoInfo,
					{html: "�鼯ϵ��:&nbsp;",cls: 'common-text',style:'width:90;text-align:left;'},	
					this.attachQuotiety,					
					{html: "������Ϣ:&nbsp;",cls: 'common-text',style:'width:90;text-align:left;'},	
					this.otherInfo,
					new Ext.form.Field({
						name:'id',
						hidden:true
					})
					,
					new Ext.form.Field({
						name:'status',
						hidden:true
					})	,
					new Ext.form.Field({
						id:'createUser',
						name:'createUser',
						hidden:true
					})	,
					new Ext.form.Field({
						name:'createDate',
						hidden:true
					}),
					new Ext.form.Field({
						name:'modifyUser',
						hidden:true
					}),
					new Ext.form.Field({
						name:'modifyDate',
						hidden:true
					}),
					new Ext.form.TextField({
						id : "parentItem",
				        width:200,
				        hidden:true,
						name:'parentItem'
					}),
					 new Ext.form.TextField({
						id : "childItem",
				        width:200,
				        hidden:true,
						name:'childItem'
					}),
					new Ext.form.TextField({
						id:'parentTypeId',
			    		hidden:true,
			    		name :'parentTypeId'
			    	}),
			    	new Ext.form.TextField({
			    		hidden:true,
			    		name :'childTypeId',
			    		id :'childTypeId'
			    	})
					]
		});	
		if(rid!=''){
			this.relPanel.load({
			 	url: webContext+'/configItemAction_findRelationShipInfo.action?rid='+rid+"&modifyId="+modifyId,
				 success: function(action,relPanel){
				 	var parentTypeId=Ext.getCmp("parentTypeId").getValue();
				 	var parentType=Ext.getCmp("parentType").getValue();
				 	var parentCode=Ext.getCmp("parentCode").getValue();
				 	var childTypeId=Ext.getCmp("childTypeId").getValue();
				 	var childType=Ext.getCmp("childType").getValue();
				 	var childCode=Ext.getCmp("childCode").getValue();
				 	if(parentTypeId!=""&&parentCode==""){
				 		Ext.getCmp("relItemType").setValue(parentTypeId);
				 		Ext.getCmp("relItemType").setRawValue(parentType);
				 		Ext.getCmp("selectRelItemPanel").searchConfigItem();
				 	}else if(childTypeId!=''&&childCode==""){
				 		Ext.getCmp("relItemType").setValue(childTypeId);
				 		Ext.getCmp("relItemType").setRawValue(childType);
				 		Ext.getCmp("selectRelItemPanel").searchConfigItem();
				 	}
				 	relationRelStore.load({							 		
				 		callback: function(r, options, success){							 			
				 			var value = relPanel.form.findField('relationShipType').getValue();								 			
				 			relPanel.form.findField('relationShipType').setValue(value)
				 		}
				 	});							 	
				 	relationGraStore.load({
				 		callback: function(r, options, success){
				 			var value = relPanel.form.findField('relationShipGrade').getValue();							 			
				 			relPanel.form.findField('relationShipGrade').setValue(value);
				 		}							 	
				 	});
				 	findTechnoInfo();							 	
				 }	
			 });
		}
		var modifyRelPanel=new Ext.Panel({
			title : "��ϵ��Ϣ",
			frame : true,
			autoScroll: true,
			//layout: 'border',
			layout: 'table',
			layoutConfig : {
					columns : 2
			},
			frame : true,
			items:[this.relPanel,new SelectRelItemPanel()]
		})
		return modifyRelPanel;
	},
	getPlanPanel:function(pid){
		var da = new DataAction();
		if(pid!=''){
	 		var data = da.getSingleFormPanelElementsForEdit("pagepanel_CIBatchModifyPlan_form",pid);
		}else{
			var data = da.getPanelElementsForAdd("pagepanel_CIBatchModifyPlan_form");
		}
		for(var i=0;i<data.length;i++){
			if(data[i].id=='CIBatchModifyPlan$startDate'){
				data[i]=new Ext.form.DateField({
					id:data[i].id,
					name:data[i].name,
					hideTrigger:true,
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
					format:"Y-m-d H:i:s",
					width:data[i].width,
					allowBlank:data[i].allowBlank,
					fieldLabel:data[i].fieldLabel,
					value:data[i].value
				})
			}
			if(pid==''){
				if(data[i].id=='CIBatchModifyPlan$officerCombo'){
					data[i].value=window.parent.Ext.getCmp("CIBatchModify$applyUser").getValue();
					data[i].initComponent();
				}
				if(data[i].id=='CIBatchModifyPlan$startDate'){
					var currentDate=new Date();
					currentDate.setDate(currentDate.getDate()+1);
					currentDate.setHours(20);
					currentDate.setMinutes(0);
					currentDate.setSeconds(0);
					data[i].value=currentDate;
				}
				if(data[i].id=='CIBatchModifyPlan$endDate'){
					var currentDate=new Date();
					currentDate.setDate(currentDate.getDate()+1);
					currentDate.setHours(20);
					currentDate.setMinutes(0);
					currentDate.setSeconds(0);					
					data[i].value=currentDate;
				}
			}
	 	}		
	  	var biddata = da.split(data);
		var planPanel= new Ext.form.FormPanel({
			id : 'relPlanPanel',
			layout : 'table',
			frame : true,
			autoScroll: true,
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
	initComponent : function() {
		var modifyId=this.modifyId;
		var pid=this.pid;
		var rid=this.rid;
		this.items = [this.getModifyRelPanel(rid,modifyId),this.getPlanPanel(pid)];
		RelationshipPanel.superclass.initComponent.call(this);
	}
});
