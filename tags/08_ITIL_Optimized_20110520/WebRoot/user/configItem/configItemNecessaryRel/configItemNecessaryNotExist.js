PagePanel = Ext.extend(Ext.Panel, {
	id : "PagePanel",
	autoScroll : true,
	layout : 'border',
	getSearchForm : function() {
		this.panel = new Ext.form.FormPanel({
			region : "north",
			layout : 'table',
			frame : true,
			height:110,
			defaults : {
				bodyStyle : 'padding:5px'
			},
			layoutConfig : {
				columns :4
			},
			title : "查询列表",
			items : [{
						html:'配置项类型：',
						cls : 'common-text',
						style : 'width:100;text-align:right;'								
					},new Ext.form.ComboBox({
							id : 'configItemTypeCombo',
							name : 'configItemType',
							width : 150	,
							displayField : 'name',
							valueField : 'id',
							resizable :true,
							triggerAction : 'all',
							forceSelection: true,
							store : new Ext.data.JsonStore({
								url : webContext
										+ '/extjs/comboDataAction?clazz=com.zsgj.itil.config.entity.ConfigItemType',
								fields : ['id', 'name'],
								totalProperty : 'rowCount',
								root : 'data'
							}),
							pageSize : 10,
							listeners : {
								'beforequery' : function(queryEvent) {
									var param = queryEvent.combo.getRawValue();
									var store=queryEvent.combo.store;
									store.baseParams.name=queryEvent.combo.getRawValue();
									store.baseParams.deployFlag=1;
									store.load({params:{start:0}});
									return false;
								}
							}
							
					}),	{
						html:'配置项名称：',
						cls : 'common-text'	,
						style : 'width:100;text-align:right;'							
					},new Ext.form.TextField({
							id : 'configItemName',
							name : 'configItemName',
						    width : 150													
					}),
					{
						html:'配置项编号：',
						cls : 'common-text'	,
						style : 'width:100;text-align:right;'							
					},new Ext.form.TextField({
							id : 'configItemNum',
							name : 'configItemNum',
							width : 150						
					}),
					{
						html:'维护工程师：',
						cls : 'common-text',
						style : 'width:100;text-align:right;'								
					},new Ext.form.TextField({
							id : 'engineer',
							name : 'engineer',
							width : 150						
					}),
					{
						html:'可选/必须：',
						cls : 'common-text',
						style : 'width:100;text-align:right;'								
					},new Ext.form.ComboBox({
							id : 'isOptionalCombo',
							name : 'isOptional',
							width : 150	,
						    mode: 'local',
						    triggerAction: 'all',
						    store: new Ext.data.SimpleStore({
						    	fields:['valueId','stateName'],
						    	data:[[2,'必选'],
						    		  [1,'可选']
						    		]
						    }),
						    valueField : 'valueId',
							displayField : 'stateName'	
					})
			]
		});
		return this.panel;
	},
	getBatchModifyGrid : function(){
		this.draftStore=new Ext.data.JsonStore({
			url : webContext + '/configItemAction_findBatchModify.action?status=0',
				fields : ['id', 'modifyNo','name','emergent','applyUser','applyDate','status'],
				totalProperty : 'rowCount',
				root : 'data'
		});
		this.sm = new Ext.grid.CheckboxSelectionModel();
		this.cm = new Ext.grid.ColumnModel([this.sm,
			{header : "变更编号",dataIndex : "modifyNo",width : 100,sortable: true}, 
			{header : "变更名称",dataIndex : "name",width : 100,sortable: true},
			{header : "是否紧急",dataIndex : "emergent",width : 60,sortable: true},
			{header : "提交人",dataIndex : "applyUser",width : 80,sortable: true},
			{header : "提交时间",dataIndex : "applyDate",width : 80,sortable: true}
		]);
		
		this.pageBar = new Ext.PagingToolbar({
			pageSize : 10,
			store : this.draftStore,
			displayInfo : true,
			displayMsg : '当前显示 {0}-{1}条记录 /共{2}条记录',
			emptyMsg : "无显示数据",
			plugins :new Ext.ux.ProgressBarPager()
		});
		this.batchModifyGrid = new Ext.grid.GridPanel({
			id:"batchModifyGrid",
			region : "center",
			store : this.draftStore,
			cm : this.cm,
			sm:this.sm,
			trackMouseOver : false,
			loadMask : true,			
			bbar : this.pageBar
		});
		this.draftStore.baseParams={
				start:0
		}
		this.draftStore.load();;		
		return this.batchModifyGrid;
	},	
	search : function(){
		var tempConfigItemNum = Ext.getCmp('configItemNum').getValue();
		var tempConfigItemName = Ext.getCmp('configItemName').getValue();
		var tempConfigItemType = Ext.getCmp('configItemTypeCombo').getValue();
		var tempEngineer = Ext.getCmp('engineer').getValue();
		var tempIsOptional = Ext.getCmp('isOptionalCombo').getValue();
		if(tempIsOptional==2){
			tempIsOptional=0;
		}
		var param = {
					configItemNum : tempConfigItemNum,
					configItemName : tempConfigItemName,
					configItemType: tempConfigItemType,
					engineer: tempEngineer,
					isOptional: tempIsOptional,
					start : 0
		};
		this.store.baseParams=param;
		this.store.load();
	},
	reset : function() {
		this.panel.getForm().reset();
      },	
      add2Draft : function(){      	
		var record = Ext.getCmp('configItemNecessaryGrid').getSelectionModel().getSelected();
		var records = Ext.getCmp('configItemNecessaryGrid').getSelectionModel().getSelections();

		if (records.length == 0) {
			Ext.MessageBox.alert('提示', '请选择必要关系!');
			return;
		}  
		var ids = new Array();
		var configItemIds = new Array();
		var tempConfigItemCode = new Array();
		for(var  i= 0 ; i < records.length;i++){
			ids[i] = records[i].get("configItemNecessaryId");
			tempConfigItemCode[i] = records[i].get("configItemNum");
			configItemIds[i]=records[i].get("configItemId");
		}
		
		var batchModifyrecord = Ext.getCmp("batchModifyGrid").getSelectionModel().getSelections();		
		if (batchModifyrecord.length==0) {
			Ext.Msg.alert("提示", "请选择变更申请，没有请新建!");
			return;
		}
		if (batchModifyrecord.length != 1) {
			Ext.MessageBox.alert('提示', '只能选择一个变更申请!');
			return;
		} 	
		
		var tempBatchModifyId = new Array() ;
		for(var  i= 0 ; i < batchModifyrecord.length;i++){
			tempBatchModifyId[i] = batchModifyrecord[i].get("id");
		}
      	Ext.Ajax.request({
      		url:webContext + '/configItemAction_createConfigItemNecessaryRelation.action',
      		params:{
				necessaryIds : ids,
				configItemIds:configItemIds,
				batchModifyId : tempBatchModifyId[0],
				configItemCodes : tempConfigItemCode
			},
			success:function(response){
				var responseArray =  Ext.util.JSON.decode(response.responseText);
				var necessaryRelationIsExist = responseArray.necessaryRelationIsExist;
				var relationArray = responseArray.relationArray;				
				var htmlText = "";
				for(var  i = 0 ; i < relationArray.length; i++){
					if(i != 0 ){
						htmlText = htmlText +"<br>";
					}
					var childCode = relationArray[i].childConfigItemCode;
					if(childCode=='null'){
						childCode = "无";
					}
					var parentCode = relationArray[i].parentConfigItemCode;
					if(parentCode=='null'){
						parentCode = "无";
					}
					htmlText = htmlText + '<li style="padding:8px,8px,8px,30px;text-indent: -15px; position: relative;">父配置项类型名称：<span style="font-weight:bold">'
									+relationArray[i].parentConfigItemTypeName +'</span>，父配置项类型编号：<span style="font-weight:bold">'
			    					+ parentCode+' </span> , 子配置项类型名称：<span style="font-weight:bold">'
			    					+relationArray[i].childConfigItemTypeName+'</span> , 子配置项编号：<span style="font-weight:bold">'+childCode
			    					+' </span> 的必要关系已经存在，不能重复添加</li>';
				}
				if(necessaryRelationIsExist==1){
					var contextWindow = new Ext.Window({
						id : 'contextWindow',
						title : '错误提示',
						layout : 'fit',
			    		width:400,
			    		height:200,
			    		autoScroll:true,
			    		modal : true,
			    		buttonAlign:'center',
			    		html : htmlText,
			    		buttons : [{
			    			text : '关闭',
			    			scope : this,
			    			handler : function (){
			    				contextWindow.close();
			    			}			    			
			    		}]
					});
					contextWindow.show();
				}else{
					Ext.Msg.alert("提示","操作成功",function(){
						Ext.getCmp("draftWin").close();
					});
				}
			},
			failure:function(){
				Ext.Msg.alert("提示","操作失败");
			}
      	})      	
      },
    select : function(){
		var record = Ext.getCmp('configItemNecessaryGrid').getSelectionModel().getSelected();
		var records = Ext.getCmp('configItemNecessaryGrid').getSelectionModel().getSelections();

		if (records.length == 0) {
			Ext.MessageBox.alert('提示', '请选择必要关系!');
			return;
		}  
    	var batchModifyGrid=this.getBatchModifyGrid();
    	var draft = new Ext.Window({
    		id:"draftWin",
    		title : '选择变更申请',
    		layout : 'fit',
    		width:500,
    		height:300,
    		modal : true,
    		items : batchModifyGrid,
    		buttons : [{
    				text :'增加到此申请',
    				handler :  this.add2Draft,
    				iconCls : 'add'    				
    		},{
    				text :'关闭',
    				handler :  function(){
    					draft.close();
    				}
    		}]
    		
    	});
    	draft.show();
    },  
	items : this.items,
	initComponent : function(){	
		
		this.fp = this.getSearchForm();
		var sm = new Ext.grid.CheckboxSelectionModel();                    
		this.store = new Ext.data.JsonStore({
			id : 'configItemNecessaryGridStore',
			url :webContext +  '/configItemAction_findConfigItemNecessaryRelation.action',
			fields : ['configItemId','configItemType','otherConfigItemType','parentOrChildType', 'configItemName', 'configItemNum', 'configItemNecessaryId',
					'isOptional', 'engineer','configItemTypeName','otherConfigItemTypeName','necessaryRelation','necessaryRelationType'],
			totalProperty : "rowCount",
			root : "data"	,
			sortInfo : {field : 'configItemNum',directTion:'desc'}
		});

		this.pageBar = new Ext.PagingToolbar({
			id  : 'configItemNecessaryPageBar',
			pageSize : 10,
			store : this.store,
			displayInfo : true,
			displayMsg : '当前显示 {0}-{1}条记录 /共{2}条记录',
			emptyMsg : "无显示数据",
			plugins :new Ext.ux.ProgressBarPager()
		});
		this.grid = new Ext.grid.GridPanel({
			id : 'configItemNecessaryGrid',
			region : "center",
			store :  this.store,
			sm : sm,
			columns : [sm,
					 {header : "配置项类型",width : 100,sortable : true,	dataIndex : 'configItemTypeName',hidden : false},
					 {header : "配置项名称",width : 150,sortable : true,dataIndex : 'configItemName',hidden : false},
					 {header : "配置项编号",width : 100,sortable : true,dataIndex : 'configItemNum',hidden : false},
					 {header : "缺少的必要关系",width : 150,sortable : true,dataIndex : 'necessaryRelation',hidden : false},
					 {header : "关系种类",width :80,sortable : true,dataIndex : 'necessaryRelationType',hidden : false,renderer:function(value){					 
					 	if(value=='1'){
					 		return '父->子';
					 	}else{
					 		return '子->父';
					 	}
					 }},
					 {header : "可选/必选",width : 100, sortable : true,dataIndex : 'isOptional',hidden : false,renderer:function(value){
					 	if(value=='1'){
					 		return '可选'
					 	}else if( value='0'){
					 		return '必选'
					 	}
					 }},

					 {header : "维护工程师",width : 100,sortable : true,dataIndex : 'engineer',hidden : false,renderer:function(value){
					 	if(value!="null"){
					 		return value;
					 	}
					 }}
			],
			loadMask : true,
			tbar : [{
				text : '查询',
				handler : this.search,
				scope : this,
				iconCls : 'search'
			},"|", {
				text : '重置',
				handler : this.reset,
				scope : this,
				iconCls : 'reset'
			},"|",{
				text : '建立必要关系',
				handler : this.select,
				scope : this,
				iconCls : 'add'
			}],
			bbar : this.pageBar
		});
		
		var items = new Array();
		items.push(this.fp);
		items.push(this.grid);
		this.items = items;
		PagePanel.superclass.initComponent.call(this);
		var param = {
			'start' : 0
		};
		this.store.baseParams=param;
		this.store.load();
	}
});