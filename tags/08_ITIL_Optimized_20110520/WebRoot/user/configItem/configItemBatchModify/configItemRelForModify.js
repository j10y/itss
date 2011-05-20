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
RelForModifyPanel = Ext.extend(Ext.Panel, {
	id : "relForModifyPanel",	
	frame : true,
	autoScroll : true,
	getModifyRelPanel:function(rid){
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
    		name :'childCode'
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
				layout: 'table',
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
		              name: 'childType',
		              mapping: 'childType'
		            },{
		              name: 'childTypeId',
		              mapping: 'childTypeId'
		            },{
		              name: 'childName',
		              mapping: 'childName'
		            },{
		              name: 'childItem',
		              mapping: 'childItem'
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
		this.relPanel.load({
		 	url: webContext+'/configItemAction_findRelationShipInfo.action?rid='+rid,
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
	initComponent : function() {
		var rid=this.rid;
		this.items = [this.getModifyRelPanel(rid)];
		RelForModifyPanel.superclass.initComponent.call(this);
	}

});
