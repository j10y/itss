PagteModelTreePanel = Ext.extend(Ext.tree.TreePanel, {
	id:'tree',
	animate: true,
	autoScroll: true,
	rootVisible: true,
	height:500,
	frame:true,
	border:true,
    listeners: {
    	dblclick:function(node,e){
    		var rid = node.attributes.rid; 
    		if(node.attributes.itemCode=="0"||node.attributes.rid=="0"){
    			return;
    		}
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
	            emptyText:"",		
	            readOnly:true,
				hideTrigger:true,
				mode: 'remote',
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
				emptyText:"",		
	            readOnly:true,
				hideTrigger:true,
				mode: 'remote',
				hiddenName : "relationShipGrade",
				editable : false,
				triggerAction : 'all', 
				allowBlank : true,
				name : "relationShipGrade",
				width: 200
        	});
        	this.attachQuotiety = new Ext.form.TextField({
        		readOnly:true,
        		name :'attachQuotiety'
        	});
        	this.atechnoInfo = new Ext.form.TextField({
        		readOnly:true,
        		name :'atechnoInfo'
        	});
        	this.btechnoInfo = new Ext.form.TextField({
        		readOnly:true,
        		name :'btechnoInfo'
        	});
        	this.otherInfo = new Ext.form.TextField({
        		readOnly:true,
        		name :'otherInfo'
        	});
        	        			                	
           	this.editForm = new Ext.form.FormPanel({
						layout: 'table',
						frame : true,
						reader: new Ext.data.JsonReader({
					    		root: 'data'
					    },[{
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
						layoutConfig: {columns: 2},	    		
						items:[	
						    {html:"<font color=green>[父]:</font><font color=red >"+node.parentNode.text+"</font>",colspan:"2"},
						    {html:"<font color=green>[子]:</font><font color=red >"+node.text+"</font>",colspan:"2"},
							{html: "关系类型:",cls: 'common-text',style:'width:80;height:20;text-align:left;margin:5 0 0 0;'},	
							this.relationType,									
							{html: "关系级别:",cls: 'common-text',style:'width:80;height:20;text-align:left;margin:5 0 0 0;'},	
							this.relationGrade,										
							{html: "归集系数:",cls: 'common-text',style:'width:80;height:20;text-align:left;margin:5 0 0 0;'},	
							this.attachQuotiety,
							{html: "A端技术信息:",cls: 'common-text',style:'width:80;height:20;text-align:left;margin:5 0 0 0;'},	
							this.atechnoInfo,
							{html: "B端技术信息:",cls: 'common-text',style:'width:80;height:20;text-align:left;margin:5 0 0 0;'},	
							this.btechnoInfo,
							{html: "其他信息:",cls: 'common-text',style:'width:80;height:20;text-align:left;margin:5 0 0 0;'},	
							this.otherInfo
							]
			});	
			
			this.editForm.load({
			 url: webContext+'/configItemAction_findRelationShipInfo.action?rid='+rid,
			 success: function(action,editorForm){
			 	relationRelStore.load({							 		
			 		callback: function(r, options, success){							 			
			 			var value = editorForm.form.findField('relationShipType').getValue();								 			
			 			editorForm.form.findField('relationShipType').setValue(value)
			 		}
			 	});							 	
			 	relationGraStore.load({
			 		callback: function(r, options, success){
			 			var value = editorForm.form.findField('relationShipGrade').getValue();							 			
			 			editorForm.form.findField('relationShipGrade').setValue(value);
			 		}							 	
			 	});
			 								 	
			 }	
			 });
           var win = new Ext.Window({
					id : 'editWin',
					title : "关系维护",
					width : 440,
					maximizable : true,
					modal : true,
					buttonAlign:'center',
					items: this.editForm,								
					buttons:[
        		{	xtype:'button',
        	 		handler:function(){
        				win.close();
        			},
        			text:'关闭',
        			scope:this
        		}]						
				});
				win.show();
        	}
     },
	initComponent: function() {
		
		this.loader = new Ext.tree.TreeLoader({
			 url:webContext+'/configItemAction_listConfigItemRelation.action'
		});
		
		this.loader.on('beforeload', function(treeloader, node) {
					treeloader.baseParams = {
						itemCode : node.attributes.itemCode
					};
		}, this);
		
		this.root = new Ext.tree.AsyncTreeNode({
			text: '上品公司',
			draggable: false,
			itemCode: '0',
			itemType:'',
			icon : webContext+'/images/cls/user.gif',
			expanded:true,
			doubleClickExpand:false
		}),
						 
		PagteModelTreePanel.superclass.initComponent.call(this);
	}
});
