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
			title : "��ѯ�б�",
			items : [{
						html:'���������ͣ�',
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
						html:'���������ƣ�',
						cls : 'common-text'	,
						style : 'width:100;text-align:right;'							
					},new Ext.form.TextField({
							id : 'configItemName',
							name : 'configItemName',
						    width : 150													
					}),
					{
						html:'�������ţ�',
						cls : 'common-text'	,
						style : 'width:100;text-align:right;'							
					},new Ext.form.TextField({
							id : 'configItemNum',
							name : 'configItemNum',
							width : 150						
					}),
					{
						html:'ά������ʦ��',
						cls : 'common-text',
						style : 'width:100;text-align:right;'								
					},new Ext.form.TextField({
							id : 'engineer',
							name : 'engineer',
							width : 150						
					}),
					{
						html:'��ѡ/���룺',
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
						    	data:[[2,'��ѡ'],
						    		  [1,'��ѡ']
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
			{header : "������",dataIndex : "modifyNo",width : 100,sortable: true}, 
			{header : "�������",dataIndex : "name",width : 100,sortable: true},
			{header : "�Ƿ����",dataIndex : "emergent",width : 60,sortable: true},
			{header : "�ύ��",dataIndex : "applyUser",width : 80,sortable: true},
			{header : "�ύʱ��",dataIndex : "applyDate",width : 80,sortable: true}
		]);
		
		this.pageBar = new Ext.PagingToolbar({
			pageSize : 10,
			store : this.draftStore,
			displayInfo : true,
			displayMsg : '��ǰ��ʾ {0}-{1}����¼ /��{2}����¼',
			emptyMsg : "����ʾ����",
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
			Ext.MessageBox.alert('��ʾ', '��ѡ���Ҫ��ϵ!');
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
			Ext.Msg.alert("��ʾ", "��ѡ�������룬û�����½�!");
			return;
		}
		if (batchModifyrecord.length != 1) {
			Ext.MessageBox.alert('��ʾ', 'ֻ��ѡ��һ���������!');
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
						childCode = "��";
					}
					var parentCode = relationArray[i].parentConfigItemCode;
					if(parentCode=='null'){
						parentCode = "��";
					}
					htmlText = htmlText + '<li style="padding:8px,8px,8px,30px;text-indent: -15px; position: relative;">���������������ƣ�<span style="font-weight:bold">'
									+relationArray[i].parentConfigItemTypeName +'</span>�������������ͱ�ţ�<span style="font-weight:bold">'
			    					+ parentCode+' </span> , ���������������ƣ�<span style="font-weight:bold">'
			    					+relationArray[i].childConfigItemTypeName+'</span> , ���������ţ�<span style="font-weight:bold">'+childCode
			    					+' </span> �ı�Ҫ��ϵ�Ѿ����ڣ������ظ����</li>';
				}
				if(necessaryRelationIsExist==1){
					var contextWindow = new Ext.Window({
						id : 'contextWindow',
						title : '������ʾ',
						layout : 'fit',
			    		width:400,
			    		height:200,
			    		autoScroll:true,
			    		modal : true,
			    		buttonAlign:'center',
			    		html : htmlText,
			    		buttons : [{
			    			text : '�ر�',
			    			scope : this,
			    			handler : function (){
			    				contextWindow.close();
			    			}			    			
			    		}]
					});
					contextWindow.show();
				}else{
					Ext.Msg.alert("��ʾ","�����ɹ�",function(){
						Ext.getCmp("draftWin").close();
					});
				}
			},
			failure:function(){
				Ext.Msg.alert("��ʾ","����ʧ��");
			}
      	})      	
      },
    select : function(){
		var record = Ext.getCmp('configItemNecessaryGrid').getSelectionModel().getSelected();
		var records = Ext.getCmp('configItemNecessaryGrid').getSelectionModel().getSelections();

		if (records.length == 0) {
			Ext.MessageBox.alert('��ʾ', '��ѡ���Ҫ��ϵ!');
			return;
		}  
    	var batchModifyGrid=this.getBatchModifyGrid();
    	var draft = new Ext.Window({
    		id:"draftWin",
    		title : 'ѡ��������',
    		layout : 'fit',
    		width:500,
    		height:300,
    		modal : true,
    		items : batchModifyGrid,
    		buttons : [{
    				text :'���ӵ�������',
    				handler :  this.add2Draft,
    				iconCls : 'add'    				
    		},{
    				text :'�ر�',
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
			displayMsg : '��ǰ��ʾ {0}-{1}����¼ /��{2}����¼',
			emptyMsg : "����ʾ����",
			plugins :new Ext.ux.ProgressBarPager()
		});
		this.grid = new Ext.grid.GridPanel({
			id : 'configItemNecessaryGrid',
			region : "center",
			store :  this.store,
			sm : sm,
			columns : [sm,
					 {header : "����������",width : 100,sortable : true,	dataIndex : 'configItemTypeName',hidden : false},
					 {header : "����������",width : 150,sortable : true,dataIndex : 'configItemName',hidden : false},
					 {header : "��������",width : 100,sortable : true,dataIndex : 'configItemNum',hidden : false},
					 {header : "ȱ�ٵı�Ҫ��ϵ",width : 150,sortable : true,dataIndex : 'necessaryRelation',hidden : false},
					 {header : "��ϵ����",width :80,sortable : true,dataIndex : 'necessaryRelationType',hidden : false,renderer:function(value){					 
					 	if(value=='1'){
					 		return '��->��';
					 	}else{
					 		return '��->��';
					 	}
					 }},
					 {header : "��ѡ/��ѡ",width : 100, sortable : true,dataIndex : 'isOptional',hidden : false,renderer:function(value){
					 	if(value=='1'){
					 		return '��ѡ'
					 	}else if( value='0'){
					 		return '��ѡ'
					 	}
					 }},

					 {header : "ά������ʦ",width : 100,sortable : true,dataIndex : 'engineer',hidden : false,renderer:function(value){
					 	if(value!="null"){
					 		return value;
					 	}
					 }}
			],
			loadMask : true,
			tbar : [{
				text : '��ѯ',
				handler : this.search,
				scope : this,
				iconCls : 'search'
			},"|", {
				text : '����',
				handler : this.reset,
				scope : this,
				iconCls : 'reset'
			},"|",{
				text : '������Ҫ��ϵ',
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