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
					var btechnoInfoDisplayName=result.btechnoInfoDisplayName.trim();
					if(atechnoInfoDisplayName.length!=0){
						Ext.getDom("atechnoInfoLabel").innerHTML=atechnoInfoDisplayName;
					}
					if(btechnoInfoDisplayName.length!=0){
						Ext.getDom("btechnoInfoLabel").innerHTML=btechnoInfoDisplayName;
					}
				}
			},
			failure : function(response, options) {}
		})
	}
}
RelationshipPanel = Ext.extend(Ext.TabPanel, {
	id : "relationshipPanel",	
	frame : true,
	activeTab :0,
	autoScroll : true,
	layoutOnTabChange:true,
	deferredRender : false,
	forceLayout:true,
	buttonAlign:'center',
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
    		store : relationRelStore,
			valueField : "id",
			displayField : "name",
			mode: 'remote',
			readOnly:true,
		    hideTrigger:true,
			hiddenName : "relationShipType",
			editable : false,
			triggerAction : 'all', 
			allowBlank : true,
			name : "relationShipType",
			width: 200
    	});
    	this.relationGrade = new Ext.form.ComboBox({
    		store : relationGraStore,
			valueField : "id",
			displayField : "name",
			mode: 'remote',
			readOnly:true,
		    hideTrigger:true,
			hiddenName : "relationShipGrade",
			editable : false,
			triggerAction : 'all', 
			allowBlank : true,
			name : "relationShipGrade",
			width: 200
    	});
    	this.attachQuotiety = new Ext.form.NumberField({
    		width: 200,
    		readOnly:true,
    		name :'attachQuotiety'
    	});
    	this.atechnoInfo = new Ext.form.TextField({
    		id:"atechnoInfo",
    		width: 200,
    		readOnly:true,
    		name :'atechnoInfo'
    	});
    	this.btechnoInfo = new Ext.form.TextField({
    		id:"btechnoInfo",
    		width: 200,
    		readOnly:true,
    		name :'btechnoInfo'
    	});
    	this.otherInfo = new Ext.form.TextField({
    		width: 200,
    		readOnly:true,
    		name :'otherInfo'
    	});
   		this.relPanel = new Ext.form.FormPanel({
   				id : 'relPanel',
				layout: 'table',
				frame:true,
				title : "��ϵ��Ϣ",
				defaults : {
					bodyStyle : 'padding:4px'
				},
				layoutConfig : {
					columns : 4
				},
				reader: new Ext.data.JsonReader({
			    		root: 'data'
			    },[{
		              name: 'parentItem',
		              mapping: 'parentItem'
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
		              name: 'parentCode',
		              mapping: 'parentCode'
		            },{
		              name: 'childType',
		              mapping: 'childType'
		            },{
		              name: 'childTypeId',
		              mapping: 'childTypeId'
		            },{
		              name: 'childItem',
		              mapping: 'childItem'
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
		            }
		        ]),
				items:[
					{html: "������:&nbsp;",cls: 'common-text',style:'width:90;text-align:left;'},	
					this.parentType,
					{html: "������:&nbsp;",cls: 'common-text',style:'width:90;text-align:left;'},	
					this.childType,					
					{html: "������:&nbsp;",cls: 'common-text',style:'width:90;text-align:left;'},	
					this.parentName,
					{html: "������:&nbsp;",cls: 'common-text',style:'width:90;text-align:left;'},	
					this.childName,					
					{html: "�����:&nbsp;",cls: 'common-text',style:'width:90;text-align:left;'},	
					this.parentCode,
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
		this.relPanel.load({
		 	url: webContext+'/configItemAction_findRelationShipInfo.action?rid='+rid+"&modifyId="+modifyId,
			 success: function(action,relPanel){
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
		
		return this.relPanel;
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
					readOnly:true,
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
					readOnly:true,
					hideTrigger:true,
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
			id : 'relPlanPanel',
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
	initComponent : function() {
		var modifyId=this.modifyId;
		var pid=this.pid;
		var rid=this.rid;
		var oldRid=this.oldRid;
		this.items = [this.getModifyRelPanel(rid,modifyId),this.getPlanPanel(pid)];
		RelationshipPanel.superclass.initComponent.call(this);
		if(oldRid!=''){
			Ext.getCmp('relationshipPanel').addButton({
				text:'�鿴ԭ��ϵ',
				iconCls:'look'				
			},function(){
					var url=webContext+'/user/configItem/configItemBatchModify/configItemRelForModify.jsp';
					var win=new Ext.Window({
								title:'ԭ��ϵ��Ϣ',
								width:600,
								frame:true,
								maximizable : true,
								autoScroll : true,
								height:230,
								modal : true,
								autoLoad : {
									url : webContext + "/tabFrame.jsp?url="+url+"?rid="+oldRid,
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
	}
});
