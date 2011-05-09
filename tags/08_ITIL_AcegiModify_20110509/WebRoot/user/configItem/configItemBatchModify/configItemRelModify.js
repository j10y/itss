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
					var btechnoInfoDisplayName=result.btechnoInfoDisplayName.trim();
					var btechnoInfoAllowBlank=result.btechnoInfoAllowBlank.trim();
					var btechnoInfoTip=result.btechnoInfoTip.trim();
					if(atechnoInfoDisplayName.length!=0){
						Ext.getDom("atechnoInfoLabel").innerHTML=atechnoInfoDisplayName;
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
					}
					if(btechnoInfoDisplayName.length!=0){
						Ext.getDom("btechnoInfoLabel").innerHTML=btechnoInfoDisplayName;
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
					}
				}
			},
			failure : function(response, options) {}
		})
	}
}
RelationshipModifyPanel = Ext.extend(Ext.TabPanel, {
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
			text:'保存关系信息',
			iconCls:'save',
			handler:function(){
				if(!Ext.getCmp('relPanel').form.isValid()||
				   !Ext.getCmp('relPlanPanel').form.isValid()){
					Ext.MessageBox.alert("提示",'您填写的信息不完整或不正确.');
					return;
				}
				if(Ext.getCmp('CIBatchModifyPlan$startDate').getValue()>Ext.getCmp('CIBatchModifyPlan$endDate').getValue()){
					Ext.MessageBox.alert("提示",'变更计划的开始时间不能大于结束时间.');
					return;
				}				
				var saveRelButton=this;
				saveRelButton.disable();
				var modifyId=Ext.getCmp('relationshipPanel').modifyId;
				var rid=Ext.getCmp('relationshipPanel').rid;
				var oldRid=Ext.getCmp('relationshipPanel').oldRid;
				if(Ext.getCmp('childCode').getValue()==''){
					Ext.Msg.alert("提示","请选择子项!",function(){
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
						Ext.Msg.alert("提示",responseText,function(){
							saveRelButton.enable();
						});
						return;
					}
				} else {
					Ext.Msg.alert("提示","系统异常",function(){
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
						Ext.Msg.alert("提示","保存关系信息成功！",function(){
							saveRelButton.enable();
							window.parent.Ext.getCmp('modifyRelGrid').getStore().reload();
							var modifyTab=window.parent.Ext.getCmp('modifyTab');
							modifyTab.remove(modifyTab.getActiveTab());							
/*							var rid=Ext.decode(response.responseText).id;
							var pid=Ext.decode(response.responseText).pid;
							var url=webContext+'/user/configItem/configItemBatchModify/configItemRelModify.jsp?rid='+rid+"&modifyId="+modifyId+"&pid="+pid+"&oldRid="+oldRid;
							window.location=url;*/
						});
					},
					failure : function(response, options) {
						Ext.Msg.alert("提示","保存关系信息失败！",function(){
							saveRelButton.enable();
						});
					}
				});
			}
		  },{
				text:'重置关系信息',
				iconCls:'reset',
				handler:function(){
					window.location.reload();
					/*Ext.getCmp('relPanel').form.findField('relationShipType').reset();
					Ext.getCmp('relPanel').form.findField('relationShipGrade').reset();
					Ext.getCmp('relPanel').form.findField('attachQuotiety').reset();
					Ext.getCmp('relPanel').form.findField('atechnoInfo').reset();
					Ext.getCmp('relPanel').form.findField('btechnoInfo').reset();
					Ext.getCmp('relPanel').form.findField('otherInfo').reset();*/
				}
			},{
			
				text:'查看原关系',
				iconCls:'look',
				handler:function(){
					var oldRid=Ext.getCmp('relationshipPanel').oldRid;
					var url=webContext+'/user/configItem/configItemBatchModify/configItemRelForModify.jsp';
					var win=new Ext.Window({
								title:'原关系信息',
								width:610,
								frame:true,
								maximizable : true,
								autoScroll : true,
								height:230,
								modal : true,
								autoLoad : {
									url : webContext + "/tabFrame.jsp?url="+url+"?rid="+oldRid,
									text : "页面正在加载中......",
									method : 'post',
									scope : this
								},
								buttonAlign:"center",
								buttons:[{
									text:'关闭',
									handler:function(){
										win.close();
									}
								}
								]
							});
					win.show();
				}
			}
			
		],
	getModifyRelPanel:function(newRid,modifyId){
		var rid=this.rid;
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
            emptyText:"请选择...",					            
			mode: 'remote',
			hiddenName : "relationShipType",
			editable : false,
			triggerAction : 'all', 
			name : "relationShipType",
			width: 200
    	});
    	this.relationGrade = new Ext.form.ComboBox({
    		store : relationGraStore,
			valueField : "id",
			displayField : "name",
			emptyText:"请选择...",
			mode: 'remote',
			hiddenName : "relationShipGrade",
			editable : false,
			triggerAction : 'all', 
			name : "relationShipGrade",
			width: 200
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
				defaults : {
					bodyStyle : 'padding:4px'
				},
				layoutConfig : {
					columns : 4
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
		              name: 'parentItem',
		              mapping: 'parentItem'
		            },{
		              name: 'parentName',
		              mapping: 'parentName'
		            },{
		              name: 'parentCode',
		              mapping: 'parentCode'
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
					{html: "父类型:&nbsp;",cls: 'common-text',style:'width:90;text-align:left;'},	
					this.parentType,
					{html: "子类型:&nbsp;",cls: 'common-text',style:'width:90;text-align:left;'},	
					this.childType,					
					{html: "父名称:&nbsp;",cls: 'common-text',style:'width:90;text-align:left;'},	
					this.parentName,
					{html: "子名称:&nbsp;",cls: 'common-text',style:'width:90;text-align:left;'},	
					this.childName,					
					{html: "父编号:&nbsp;",cls: 'common-text',style:'width:90;text-align:left;'},	
					this.parentCode,
					{html: "子编号:&nbsp;",cls: 'common-text',style:'width:90;text-align:left;'},	
					this.childCode,									
					{html: "关系类型:&nbsp;",cls: 'common-text',style:'width:90;text-align:left;'},	
					this.relationType,									
					{html: "关系级别:&nbsp;",cls: 'common-text',style:'width:90;text-align:left;'},	
					this.relationGrade,										
					{html: "<span id='atechnoInfoLabel'>A端技术信息</span>:&nbsp;",cls: 'common-text',style:'width:90;text-align:left;'},	
					this.atechnoInfo,
					{html: "<span id='btechnoInfoLabel'>B端技术信息</span>:&nbsp;",cls: 'common-text',style:'width:90;text-align:left;'},	
					this.btechnoInfo,
					{html: "归集系数:&nbsp;",cls: 'common-text',style:'width:90;text-align:left;'},	
					this.attachQuotiety,					
					{html: "其他信息:&nbsp;",cls: 'common-text',style:'width:90;text-align:left;'},	
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
		if(newRid!=''){
			this.relPanel.load({
			 	url: webContext+'/configItemAction_findRelationShipInfo.action?rid='+newRid+"&modifyId="+modifyId,
				 success: function(action,relPanel){
				 	if(rid==''){
			 			relPanel.form.findField('id').setValue("");
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
			title : "关系信息",
			frame : true,
			autoScroll: true,
			frame : true,
			items:[this.relPanel]
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
			title : "变更计划",
			items : biddata
		});
		return planPanel;
	},
	initComponent : function() {
		var modifyId=this.modifyId;
		var newRid=this.rid;
		var pid=this.pid;
		var rid=this.rid;
		if(rid==''&&this.oldRid!=''){
			newRid=this.oldRid;
		}
		this.items = [this.getModifyRelPanel(newRid,modifyId),this.getPlanPanel(pid)];
		RelationshipModifyPanel.superclass.initComponent.call(this);
	}
});
