RelListPanel=Ext.extend(Ext.Panel, {
	id:'relListPanel',
	frame:true,
	layout:'border',
	closable:true,
	getRelListGrid:function(){
		var sm = new Ext.grid.CheckboxSelectionModel();
		var cm = new Ext.grid.ColumnModel([sm,{header:'id',dataIndex:'id',hidden:true,sortable:true},
											/*{header:'父模块',dataIndex:'parentItem',sortable:true,renderer :function(value){
												if(value=='ci')
													return '配置项';
												else if(value=='si')
													return '服务项';
											}},*/
											{header:'父类型',dataIndex:'parentType',sortable:true},
											{header:'父名称',dataIndex:'parentName',sortable:true},
											{header:'父编号',dataIndex:'parentCode',sortable:true},
											/*{header:'子模块',dataIndex:'childItem',sortable:true,renderer :function(value){
												if(value=='ci')
													return '配置项';
												else if(value=='si')
													return '服务项';
											}},*/
											{header:'子类型',dataIndex:'childType',sortable:true},
											{header:'子名称',dataIndex:'childName',sortable:true},											
											{header:'子编号',dataIndex:'childCode',sortable:true},
											{header:'关系类型',dataIndex:'relationShipType',sortable:true},
											{header:'关系紧密程度',dataIndex:'relationShipGrade',sortable:true},
											{header:'归集系数',dataIndex:'attachQuotiety',sortable:true},
											{header:'A端技术信息',dataIndex:'atechnoInfo',sortable:true},
											{header:'B端技术信息',dataIndex:'btechnoInfo',sortable:true},
											{header:'其他信息',dataIndex:'otherInfo',sortable:true}
											]);
		this.store=new Ext.data.JsonStore({
				url : webContext
						+'/configItemAction_getRelList.action',
				fields : ['id',/*'parentItem',*/'parentType','parentName','parentCode',/*'childItem',*/'childType','childName','childCode','relationShipType','relationShipGrade','attachQuotiety','atechnoInfo','btechnoInfo','otherInfo'],
				totalProperty : 'rowCount',
				root : 'data'
		});
		this.pageBar = new Ext.PagingToolbar({
			id:'pageBar',
			pageSize :10,// 使用的是系统默认值
			store : this.store,
			displayInfo : true,
			displayMsg : '当前显示 {0}-{1}条记录 /共{2}条记录',
			emptyMsg : "无显示数据",
			plugins :new Ext.ux.ProgressBarPager()
		});
		this.grid = new Ext.grid.GridPanel({
			id:"relListGrid",
			store : this.store,
			cm : cm,
			sm : sm,
			region:'center',
			frame:true,
			loadMask : true,
			autoScroll:true,
			bbar : this.pageBar,
			tbar:[
				{text : ' 查询',
				handler : function(){
						var basicSearchForm=Ext.getCmp("basicSearchForm");			
						var advancedSearchForm=Ext.getCmp("advancedSearchForm");
						if(basicSearchForm!=undefined){
							basicSearchForm=Ext.encode(getFormParam("basicSearchForm"));
							
						}else if(advancedSearchForm!=undefined){
							advancedSearchForm=Ext.encode(getFormParam("advancedSearchForm"));
						}
						var params={
								basicSearchForm:basicSearchForm,
								advancedSearchForm:advancedSearchForm,
								start:0
						};
						Ext.getCmp("relListGrid").store.baseParams=params;
						Ext.getCmp("relListGrid").store.load();
				},
				scope : this,
				iconCls : 'search',
				cls : "x-btn-text-icon"
				
			},"-",{
				id:'advanced',
				text:'高级查询',
				handler : function(){
					var searchForm=Ext.getCmp("searchForm");		
					var basicSearchForm=Ext.getCmp("basicSearchForm");
					var advancedSearchForm=Ext.getCmp("advancedSearchForm");
					if(basicSearchForm!=undefined){
						Ext.getCmp('advanced').setText('基本查询');
						searchForm.remove(basicSearchForm);
						searchForm.setHeight(85);
						searchForm.add(this.getAdvancedSearchForm());
						searchForm.doLayout();
						this.doLayout();
					}else if(advancedSearchForm!=undefined){
						Ext.getCmp('advanced').setText('高级查询');
						searchForm.remove(advancedSearchForm);
						searchForm.setHeight(61);
						searchForm.add(this.getBasicSearchForm());
						searchForm.doLayout();
						this.doLayout();
					}
				},
				scope : this,
				iconCls : 'search',
				cls : "x-btn-text-icon"
			},"-",{
				text:'重置',
				handler : function(){
					var basicSearchForm=Ext.getCmp("basicSearchForm");
					var advancedSearchForm=Ext.getCmp("advancedSearchForm");
					if(basicSearchForm!=undefined){
						basicSearchForm.form.reset();
					}else if(advancedSearchForm!=undefined){
						advancedSearchForm.form.reset();
					}
				},
				scope : this,
				iconCls : 'reset',
				cls : "x-btn-text-icon"
			},"-",{
				text:'查看',
				handler : function(){
					this.grid.fireEvent('rowdblclick',this.grid);
				},
				scope : this,
				iconCls : 'look',
				cls : "x-btn-text-icon"
			}
			]
			});
		this.grid.on("rowdblclick",function(grid,rowIndex,eventObject){
			var records = grid.getSelectionModel().getSelections();
			if(records.length==0){
				Ext.Msg.alert("提示","请选择关系!");
				return;
			}
			if(records.length>1){
				Ext.Msg.alert("提示","只能选择一条关系!");
				return;
			}
			var rid=records[0].get('id');
			var relReadOnly=new RelReadOnly({rid:rid})
			var win=new Ext.Window({
						title:'关系信息',
						width:610,
						frame:true,
						maximizable : true,
						autoScroll : true,
						height:230,
						modal : true,
						items:relReadOnly,
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
		},this);
		var params={
				start:0
			}
		this.store.load();
		return this.grid;
	},
	getAdvancedSearchForm:function(){
		var parentItem = new Ext.form.ComboBox({
			store:new Ext.data.SimpleStore({
	        		fields:['id','name'],
	        		data:[["ci","配置项"],["si","服务项"]]
	        }),
	        id:'parentItemCombo',
    		hiddenName : "parentItem",
			valueField : "id",
			displayField : "name",
            emptyText: '请选择',
            width:100,
			mode : 'local',
			value:'ci',
			resizable:true,
			editable : false,
			triggerAction : 'all', 
			allowBlank : true,
			listeners: {
						select: function(combo, record, index){
							Ext.getCmp("parentItemTypeCombo").clearValue();
					    },  						    
						scope: this
					}
		});	
		var parentItemType = new Ext.form.ComboBox({
			store : new Ext.data.JsonStore({
				url: webContext+'/configItemAction_findItemTypeByItem.action',
				fields: ['id','name'],
			    root:'data',
				sortInfo: {field: "id", direction: "ASC"}
			}),
			id:'parentItemTypeCombo',
			hiddenName:'parentItemType',
			valueField : "id",
			listeners:{
				beforequery  :function(queryEvent){
						var item=Ext.getCmp('parentItemCombo').getValue();
						if(item==""){
						   Ext.Msg.alert("提示",'请选择模块!');
						}else{
							queryEvent.combo.store.baseParams={
								item:item
							};
							queryEvent.combo.store.reload();
						}
						return false;
				}
			}, 
			displayField : "name",
			resizable:true,
            emptyText: '请选择',
			mode : 'remote',
			 width:100,
			 listWidth:150,
			 maxHeight:200,
			editable : false,
			triggerAction : 'all', 
			allowBlank : true
		});
		var parentItemName = new Ext.form.TextField({
				id:"parentItemName",
				 width:100,
				name : 'parentItemName'
		});
		var parentItemCode = new Ext.form.TextField({
				id:"parentItemCode",
				 width:100,
				name : 'parentItemCode'
		});
		var childItem = new Ext.form.ComboBox({
			store:new Ext.data.SimpleStore({
	        		fields:['id','name'],
	        		data:[["ci","配置项"],["si","服务项"]]
	        }),
	        id:'childItemCombo',
    		hiddenName : "childItem",
			valueField : "id",
			displayField : "name",
            emptyText: '请选择',
            width:100,
			mode : 'local',
			resizable:true,
			value:'ci',
			editable : false,
			triggerAction : 'all', 
			allowBlank : true,
			listeners: {
						select: function(combo, record, index){
							Ext.getCmp("childItemTypeCombo").clearValue();
					    },  						    
						scope: this
					}
		});	
		var childItemType = new Ext.form.ComboBox({
			store : new Ext.data.JsonStore({
				url: webContext+'/configItemAction_findItemTypeByItem.action',
				fields: ['id','name'],
			    root:'data',
				sortInfo: {field: "id", direction: "ASC"}
			}),
			listeners:{
				beforequery  :function(queryEvent){
						var item=Ext.getCmp('childItemCombo').getValue();
						if(item==""){
						   Ext.Msg.alert("提示",'请选择模块!');
						}else{
							queryEvent.combo.store.baseParams={
								item:item
							};
							queryEvent.combo.store.reload();
						}
						return false;
					}
			}, 
			id:'childItemTypeCombo',
			hiddenName:'childItemType',
			valueField : "id",
			displayField : "name",
			resizable:true,
            emptyText: '请选择',
			mode : 'remote',
			maxHeight:200,
			listWidth:150,
			 width:100,
			editable : false,
			triggerAction : 'all', 
			allowBlank : true
		});
		var childItemName = new Ext.form.TextField({
				id:"childItemName",
				 width:100,
				name : 'childItemName'
		});
		var childItemCode = new Ext.form.TextField({
				id:"childItemCode",
				 width:100,
				name : 'childItemCode'
		});
		var advancedSearchForm = new Ext.form.FormPanel({
				title:'高级查询',
				id : "advancedSearchForm",
				layout : 'table',
				frame : true,
				keys:{
				    key:Ext.EventObject.ENTER,
				    fn: function(){
					    var basicSearchForm=Ext.getCmp("basicSearchForm");			
						var advancedSearchForm=Ext.getCmp("advancedSearchForm");
						if(basicSearchForm!=undefined){
							basicSearchForm=Ext.encode(getFormParam("basicSearchForm"));
							
						}else if(advancedSearchForm!=undefined){
							advancedSearchForm=Ext.encode(getFormParam("advancedSearchForm"));
						}
						var params={
								basicSearchForm:basicSearchForm,
								advancedSearchForm:advancedSearchForm,
								start:0
						};
						Ext.getCmp("relListGrid").store.baseParams=params;
						Ext.getCmp("relListGrid").store.load();
				    },
				    scope: this
				},				
				autoScroll:true,
				height:85,
				defaults : {
					bodyStyle : 'padding:4px'
				},
				layoutConfig : {columns: 8},
				items : [
					{html: "父模块:" ,cls: 'common-text', style:'width:60;height:20;text-align:left;'},
					parentItem,
					{html: "父类型:" ,cls: 'common-text', style:'width:60;height:20;text-align:left;'},
					parentItemType,
					{html: "父名称:" ,cls: 'common-text', style:'width:60;height:20;text-align:left;'},
					parentItemName,
					{html: "父编号:" ,cls: 'common-text', style:'width:60;height:20;text-align:left;'},
					parentItemCode,
					{html: "子模块:" ,cls: 'common-text', style:'width:60;height:20;text-align:left;'},
					childItem,
					{html: "子类型:" ,cls: 'common-text', style:'width:60;height:20;text-align:left;'},
					childItemType,
					{html: "子名称:" ,cls: 'common-text', style:'width:60;height:20;text-align:left;'},
					childItemName,
					{html: "子编号:" ,cls: 'common-text', style:'width:60;height:20;text-align:left;'},
					childItemCode
					]
			});
		return advancedSearchForm;
	},
	getBasicSearchForm:function(){
		var item = new Ext.form.ComboBox({
				store:new Ext.data.SimpleStore({
		        		fields:['id','name'],
		        		data:[["ci","配置项"],["si","服务项"]]
		        }),
		        id:'itemCombo',
        		hiddenName : "item",
				valueField : "id",
				displayField : "name",
	            emptyText: '请选择',
	            value:'ci',
	            width:100,
				mode : 'local',
				resizable:true,
				editable : false,
				triggerAction : 'all', 
				allowBlank : true,
				listeners: {
							select: function(combo, record, index){
								Ext.getCmp("itemTypeCombo").clearValue();
						    },  						    
							scope: this
						}
			});	
		var itemType = new Ext.form.ComboBox({
			store : new Ext.data.JsonStore({
				url: webContext+'/configItemAction_findItemTypeByItem.action',
				fields: ['id','name'],
			    root:'data',
				sortInfo: {field: "id", direction: "ASC"}
			}),
			listeners:{
				beforequery  :function(queryEvent){
						var item=Ext.getCmp('itemCombo').getValue();
						if(item==""){
						   Ext.Msg.alert("提示",'请选择模块!');
						}else{
							queryEvent.combo.store.baseParams={
								item:item
							};
							queryEvent.combo.store.reload();
						}
						return false;
					}
			}, 
			id:'itemTypeCombo',
			hiddenName:'itemType',
			valueField : "id",
			displayField : "name",
			resizable:true,
            emptyText: '请选择',
			mode : 'remote',
			listWidth:150,
			 width:100,
			 maxHeight:200,
			editable : false,
			triggerAction : 'all', 
			allowBlank : true
		});
		var itemName = new Ext.form.TextField({
				id:"itemName",
				 width:100,
				name : 'itemName'
		});
		var itemCode = new Ext.form.TextField({
				id:"itemCode",
				 width:100,
				name : 'itemCode'
		});
		var basicSearchForm = new Ext.form.FormPanel({
				title:'基本查询',
				id : "basicSearchForm",
				layout : 'table',
				frame : true,
				autoScroll:true,
				keys:{
				    key:Ext.EventObject.ENTER,
				    fn: function(){
					    var basicSearchForm=Ext.getCmp("basicSearchForm");			
						var advancedSearchForm=Ext.getCmp("advancedSearchForm");
						if(basicSearchForm!=undefined){
							basicSearchForm=Ext.encode(getFormParam("basicSearchForm"));
							
						}else if(advancedSearchForm!=undefined){
							advancedSearchForm=Ext.encode(getFormParam("advancedSearchForm"));
						}
						var params={
								basicSearchForm:basicSearchForm,
								advancedSearchForm:advancedSearchForm,
								start:0
						};
						Ext.getCmp("relListGrid").store.baseParams=params;
						Ext.getCmp("relListGrid").store.load();
				    },
				    scope: this
				},
				height:61,
				defaults : {
					bodyStyle : 'padding:4px'
				},
				layoutConfig : {columns: 8},
				items : [
					{html: "模块:" ,cls: 'common-text', style:'width:40;height:20;text-align:left;'},
					item,
					{html: "类型:" ,cls: 'common-text', style:'width:40;height:20;text-align:left;'},
					itemType,
					{html: "名称:" ,cls: 'common-text', style:'width:40;height:20;text-align:left;'},
					itemName,
					{html: "编号:" ,cls: 'common-text', style:'width:40;height:20;text-align:left;'},
					itemCode
					]
			});
		return basicSearchForm;
	},
	getSearchForm:function(data){
		var searchForm = new Ext.Panel({
				id : "searchForm",
				region:'north',
				height:61,
				items : data
			});
		return searchForm;
	},
	initComponent: function(){
        this.items = [this.getSearchForm(this.getBasicSearchForm()),this.getRelListGrid()];	
		RelListPanel.superclass.initComponent.call(this);	
	}
})
