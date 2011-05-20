RelListPanel=Ext.extend(Ext.Panel, {
	id:'relListPanel',
	frame:true,
	layout:'border',
	title:'ѡ���ϵ',
	autoScroll:true,
	closable:true,
	getRelListGrid:function(){
		var sm = new Ext.grid.CheckboxSelectionModel();
		var cm = new Ext.grid.ColumnModel([sm,{header:'id',dataIndex:'id',hidden:true,sortable:true},
											/*{header:'��ģ��',dataIndex:'parentItem',sortable:true,renderer :function(value){
												if(value=='ci')
													return '������';
												else if(value=='si')
													return '������';
											}},*/
											{header:'������',dataIndex:'parentType',sortable:true},
											{header:'������',dataIndex:'parentName',sortable:true},
											{header:'�����',dataIndex:'parentCode',sortable:true},
											/*{header:'��ģ��',dataIndex:'childItem',sortable:true,renderer :function(value){
												if(value=='ci')
													return '������';
												else if(value=='si')
													return '������';
											}},*/
											{header:'������',dataIndex:'childType',sortable:true},
											{header:'������',dataIndex:'childName',sortable:true},											
											{header:'�ӱ��',dataIndex:'childCode',sortable:true},
											{header:'��ϵ����',dataIndex:'relationShipType',sortable:true},
											{header:'��ϵ���̶ܳ�',dataIndex:'relationShipGrade',sortable:true},
											{header:'�鼯ϵ��',dataIndex:'attachQuotiety',sortable:true},
											{header:'A�˼�����Ϣ',dataIndex:'atechnoInfo',sortable:true},
											{header:'B�˼�����Ϣ',dataIndex:'btechnoInfo',sortable:true},
											{header:'������Ϣ',dataIndex:'otherInfo',sortable:true}
											]);
		this.store=new Ext.data.JsonStore({
				url : webContext
						+'/configItemAction_getRelList.action',
				fields : ['id','parentItem','parentType','parentName','parentCode','childItem','childType','childName','childCode','relationShipType','relationShipGrade','attachQuotiety','atechnoInfo','btechnoInfo','otherInfo'],
				totalProperty : 'rowCount',
				root : 'data'
		});
		this.pageBar = new Ext.PagingToolbar({
			id:'pageBar',
			pageSize :10,// ʹ�õ���ϵͳĬ��ֵ
			store : this.store,
			displayInfo : true,
			displayMsg : '��ǰ��ʾ {0}-{1}����¼ /��{2}����¼',
			emptyMsg : "����ʾ����",
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
				{text : ' ��ѯ',
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
				text:'�߼���ѯ',
				handler : function(){
					var searchForm=Ext.getCmp("searchForm");		
					var basicSearchForm=Ext.getCmp("basicSearchForm");
					var advancedSearchForm=Ext.getCmp("advancedSearchForm");
					if(basicSearchForm!=undefined){
						Ext.getCmp('advanced').setText('������ѯ');
						searchForm.remove(basicSearchForm);
						searchForm.setHeight(85);
						searchForm.add(this.getAdvancedSearchForm());
						searchForm.doLayout();
						this.doLayout();
					}else if(advancedSearchForm!=undefined){
						Ext.getCmp('advanced').setText('�߼���ѯ');
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
				text:'����',
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
			},'-',
			{text : ' ���',
				id:"modifyRel",
				handler : function(){
					var modifyRel=Ext.getCmp("modifyRel");
					var records = Ext.getCmp('relListGrid').getSelectionModel().getSelections();
					if(records.length==0){
						Ext.Msg.alert("��ʾ","��ѡ��Ҫ����Ĺ�ϵ!");
						return;
					}
					if(records.length>1){
						Ext.Msg.alert("��ʾ","ֻ��ѡ��һ����ϵ!");
						return;
					}
					modifyRel.disable();
					var rid=records[0].get("id");
					var modifyId=Ext.getCmp("relListPanel").modifyId;
					Ext.Ajax.request({
						url : webContext
								+ '/configItemAction_modifyCIRelValidate.action',
						params : {
							rid:rid,
							modifyId:modifyId
						},
						success : function(response, options) {
							if(response.responseText!=''){
								var result=Ext.decode(response.responseText);
								if(result.error!=undefined){
									Ext.Msg.alert("��ʾ",result.error,function(){
										modifyRel.enable();
									});
									return;
								}
								if(result.exist!=undefined){
									Ext.MessageBox.alert("��ʾ","���Ѵ�����Դ˹�ϵ�ı���ݸ壡",function(){
											modifyRel.enable();
									});
									return;
								}
							}else{
								var url=webContext+'/user/configItem/configItemBatchModify/configItemRelModify.jsp?oldRid='+rid+
																								     "****modifyId="+modifyId;
								var panel=new Ext.Panel({
									title:'�����ϵ',
									closable:true,
									autoLoad : {
										url : webContext + "/tabFrame.jsp?url="+url,
										text : "ҳ�����ڼ�����......",
										method : 'post',
										scope : this
									}
								})
								Ext.getCmp('modifyTab').add(panel);
								Ext.getCmp('modifyTab').doLayout();
								Ext.getCmp('modifyTab').activate(panel);								
								modifyRel.enable();
							}
						},
						failure : function(response, options) {
							Ext.Msg.alert("��ʾ", "ϵͳ�쳣��",function(){
								modifyRel.enable();
							});
						}
					});
				},
				scope : this,
				iconCls : 'add',
				cls : "x-btn-text-icon"
				
			},'-',
			{   text : ' ɾ��',
				id:"removeRel",
				handler : function(){
					var removeRel=Ext.getCmp("removeRel");
					var records = Ext.getCmp('relListGrid').getSelectionModel().getSelections();
					if(records.length==0){
						Ext.Msg.alert("��ʾ","��ѡ��Ҫɾ���Ĺ�ϵ!");
						return;
					}
					if(records.length>1){
						Ext.Msg.alert("��ʾ","ֻ��ѡ��һ����ϵ!");
						return;
					}
					removeRel.disable();
					var rid=records[0].get("id");
					var modifyId=Ext.getCmp("relListPanel").modifyId;
					Ext.Ajax.request({
						url : webContext
								+ '/configItemAction_deleteCIRelValidate.action',
						params : {
							rid:rid,
							modifyId:modifyId
						},
						success : function(response, options) {
							if(response.responseText.trim()!=""){
								var result=Ext.decode(response.responseText);
								if(result.exist!=undefined){
									Ext.MessageBox.alert("��ʾ","���Ѵ�����Դ˹�ϵ�ı���ݸ壡",function(){
											removeRel.enable();
									});
								}
								/*remove by duxh in 20100610 for ��Ϊ����ǿ����,��ʱע��,���ڿ��Ǽӽ���	---begin---							
  								if(result.necessary!=undefined){
									Ext.MessageBox.alert("��ʾ","��Ϊ��Ҫ��ϵ������ɾ����",function(){
											removeRel.enable();
									});
								}
								remove by duxh in 20100610 for ��Ϊ����ǿ����,��ʱע��,���ڿ��Ǽӽ���	    ---end---*/
								return;
							}
							var url=webContext+'/user/configItem/configItemBatchModify/configItemRelDelete.jsp?rid='+rid+
																								     "****modifyId="+modifyId;
							if(Ext.getCmp('deleteRelPanel')!=undefined){
								Ext.getCmp('modifyTab').remove('deleteRelPanel');
							}																	   
							var panel=new Ext.Panel({
								id:'deleteRelPanel',
								title:'ɾ����ϵ',
								closable:true,
								autoLoad : {
									url : webContext + "/tabFrame.jsp?url="+url,
									text : "ҳ�����ڼ�����......",
									method : 'post',
									scope : this
								}
							})
							Ext.getCmp('modifyTab').add(panel);
							Ext.getCmp('modifyTab').doLayout();
							Ext.getCmp('modifyTab').activate(panel);								
							removeRel.enable();
						},
						failure : function(response, options) {
							Ext.Msg.alert("��ʾ", "���ʧ��",function(){
								removeRel.enable();
							});
						}
					}, this);
				},
				scope : this,
				iconCls : 'remove',
				cls : "x-btn-text-icon"
				
			}]
			});
		this.grid.on("rowdblclick",function(grid,rowIndex,eventObject){
			var record = grid.getSelectionModel().getSelected();
			var rid=record.get('id');
			var url=webContext+'/user/configItem/configItemBatchModify/configItemRelForModify.jsp';
			var win=new Ext.Window({
						title:'��ϵ��Ϣ',
						width:610,
						frame:true,
						maximizable : true,
						autoScroll : true,
						height:230,
						modal : true,
						autoLoad : {
							url : webContext + "/tabFrame.jsp?url="+url+"?rid="+rid,
							text : "ҳ�����ڼ�����......",
							method : 'post',
							scope : this
						},
						buttonAlign:"center",
						buttons:[
					  	{	
							text:'���',
							iconCls:'save',
							handler:function(){
								var modifyRel=this;
								modifyRel.disable();
								var modifyId=Ext.getCmp("relListPanel").modifyId;
								Ext.Ajax.request({
									url : webContext
											+ '/configItemAction_modifyCIRelValidate.action',
									params : {
										rid:rid,
										modifyId:modifyId
									},
									success : function(response, options) {
										if(response.responseText!=''){
											var result=Ext.decode(response.responseText);
											if(result.error!=undefined){
												Ext.Msg.alert("��ʾ",result.error,function(){
													modifyRel.enable();
												});
												return;
											}
											if(result.exist!=undefined){
												Ext.MessageBox.alert("��ʾ","���Ѵ�����Դ˹�ϵ�ı���ݸ壡",function(){
														modifyRel.enable();
												});
												return;
											}
										}else{
											var url=webContext+'/user/configItem/configItemBatchModify/configItemRelModify.jsp?oldRid='+rid+
																											     "****modifyId="+modifyId;
											var panel=new Ext.Panel({
												title:'�����ϵ',
												closable:true,
												autoLoad : {
													url : webContext + "/tabFrame.jsp?url="+url,
													text : "ҳ�����ڼ�����......",
													method : 'post',
													scope : this
												}
											})
											Ext.getCmp('modifyTab').add(panel);
											Ext.getCmp('modifyTab').doLayout();
											Ext.getCmp('modifyTab').activate(panel);								
											modifyRel.enable();
											win.close();
										}
									},
									failure : function(response, options) {
										Ext.Msg.alert("��ʾ", "ϵͳ�쳣��",function(){
											modifyRel.enable();
										});
									}
								}, this);
							}
					 	 },{
					 	 	   text : ' ɾ��',
								iconCls : 'remove',
								handler : function(){
									var removeRel=this;
									removeRel.disable();
									var modifyId=Ext.getCmp("relListPanel").modifyId;
									Ext.Ajax.request({
										url : webContext
												+ '/configItemAction_deleteCIRelValidate.action',
										params : {
											rid:rid,
											modifyId:modifyId
										},
										success : function(response, options) {
											if(response.responseText.trim()!=""){
												var result=Ext.decode(response.responseText);
												if(result.exist!=undefined){
													Ext.MessageBox.alert("��ʾ","���Ѵ�����Դ˹�ϵ�ı���ݸ壡",function(){
															removeRel.enable();
													});
												}
												if(result.necessary!=undefined){
													Ext.MessageBox.alert("��ʾ","��Ϊ��Ҫ��ϵ������ɾ����",function(){
															removeRel.enable();
													});
												}
												return;
											}
											var url=webContext+'/user/configItem/configItemBatchModify/configItemRelDelete.jsp?rid='+rid+
																												     "****modifyId="+modifyId;
											
											if(Ext.getCmp('deleteRelPanel')!=undefined){
												Ext.getCmp('modifyTab').remove('deleteRelPanel');
											}																	   
											var panel=new Ext.Panel({
												id:'deleteRelPanel',
												title:'ɾ����ϵ',
												closable:true,
												autoLoad : {
													url : webContext + "/tabFrame.jsp?url="+url,
													text : "ҳ�����ڼ�����......",
													method : 'post',
													scope : this
												}
											})
											Ext.getCmp('modifyTab').add(panel);
											Ext.getCmp('modifyTab').doLayout();
											Ext.getCmp('modifyTab').activate(panel);
											win.close();
										},
										failure : function(response, options) {
											Ext.Msg.alert("��ʾ", "���ʧ��",function(){
												removeRel.enable();
											});
										}
									}, this);
								}
					 	 },{
							text:'�ر�',
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
		this.store.baseParams=params;
		this.store.load();
		return this.grid;
	},
	getAdvancedSearchForm:function(){
		var parentItem = new Ext.form.ComboBox({
			store:new Ext.data.SimpleStore({
	        		fields:['id','name'],
	        		data:[["ci","������"],["si","������"]]
	        }),
	        id:'parentItemCombo',
    		hiddenName : "parentItem",
			valueField : "id",
			displayField : "name",
            emptyText: '��ѡ��',
            width:100,
			mode : 'local',
			resizable:true,
			editable : false,
			triggerAction : 'all',
			value:'ci',
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
						   Ext.Msg.alert("��ʾ",'��ѡ��ģ��!');
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
            emptyText: '��ѡ��',
			mode : 'remote',
			 width:100,
			 maxHeight:200,
			 listWidth:150,
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
	        		data:[["ci","������"],["si","������"]]
	        }),
	        id:'childItemCombo',
    		hiddenName : "childItem",
			valueField : "id",
			displayField : "name",
            emptyText: '��ѡ��',
            width:100,
			mode : 'local',
			value:'ci',
			resizable:true,
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
						   Ext.Msg.alert("��ʾ",'��ѡ��ģ��!');
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
            emptyText: '��ѡ��',
			mode : 'remote',
			 width:100,
			 listWidth:150,
			 maxHeight:200,
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
				title:'�߼���ѯ',
				id : "advancedSearchForm",
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
				height:85,
				defaults : {
					bodyStyle : 'padding:4px'
				},
				layoutConfig : {columns: 8},
				items : [
					{html: "��ģ��:" ,cls: 'common-text', style:'width:60;height:20;text-align:left;'},
					parentItem,
					{html: "������:" ,cls: 'common-text', style:'width:60;height:20;text-align:left;'},
					parentItemType,
					{html: "������:" ,cls: 'common-text', style:'width:60;height:20;text-align:left;'},
					parentItemName,
					{html: "�����:" ,cls: 'common-text', style:'width:60;height:20;text-align:left;'},
					parentItemCode,
					{html: "��ģ��:" ,cls: 'common-text', style:'width:60;height:20;text-align:left;'},
					childItem,
					{html: "������:" ,cls: 'common-text', style:'width:60;height:20;text-align:left;'},
					childItemType,
					{html: "������:" ,cls: 'common-text', style:'width:60;height:20;text-align:left;'},
					childItemName,
					{html: "�ӱ��:" ,cls: 'common-text', style:'width:60;height:20;text-align:left;'},
					childItemCode
					]
			});
		return advancedSearchForm;
	},
	getBasicSearchForm:function(){
		var item = new Ext.form.ComboBox({
				store:new Ext.data.SimpleStore({
		        		fields:['id','name'],
		        		data:[["ci","������"],["si","������"]]
		        }),
		        id:'itemCombo',
        		hiddenName : "item",
				valueField : "id",
				displayField : "name",
	            emptyText: '��ѡ��',
	            width:100,
	            value:'ci',
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
						   Ext.Msg.alert("��ʾ",'��ѡ��ģ��!');
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
            emptyText: '��ѡ��',
			mode : 'remote',
			maxHeight:200,
			 width:100,
			 listWidth:150,
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
				title:'������ѯ',
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
					{html: "ģ��:" ,cls: 'common-text', style:'width:40;height:20;text-align:left;'},
					item,
					{html: "����:" ,cls: 'common-text', style:'width:40;height:20;text-align:left;margin:0 0 0 15;'},
					itemType,
					{html: "����:" ,cls: 'common-text', style:'width:40;height:20;text-align:left;margin:0 0 0 15;'},
					itemName,
					{html: "���:" ,cls: 'common-text', style:'width:40;height:20;text-align:left;margin:0 0 0 15;'},
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
