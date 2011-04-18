PagePanel = Ext.extend(Ext.Panel, {
	closable : true,

	height:800,
	layout : 'border',
	getSearchForm : function() {
		var applyNum = new Ext.form.TextField({
			name : "deptname",
			fieldLabel : "������",
			id : "deptname",
			width : 150
		});
		/*var applytype = new Ext.form.TextField({
			name : "rbtype",
			fieldLabel : "�������",
			id : "rbtype",
			emptyText:"������'��'����'��'",
			width : 150
		});*/
		
		this.panel = new Ext.form.FormPanel({
			id:"searchForm",
			region : "north", 
			layout : 'table',
			height : 40,
			frame : true,
			defaults : {
				bodyStyle : 'padding:4px'
			},
			layoutConfig : {
				columns : 4
			},
			items : [{
				html : "�û�ITCODE:",
				cls : 'common-text',
				width : 150,
				style : 'width:150;text-align:right'
			}, applyNum/*,{
				html : "��/������:",
				cls : 'common-text',
				width : 150,
				style : 'width:150;text-align:right'
			}, applytype*/
		
			]
		});

		return this.panel;
	},
	getAddform :function(){
	
		var addform= new Ext.form.FormPanel({
			id:"addForm",
			layout : 'table',
			title : '���Ӻں�������Ϣ',
			frame : true,
			collapsible : false,
			defaults : {
				bodyStyle : 'padding:4px'
			},
			layoutConfig : {
				columns : 2
			},
			
			items : [{
				html : "�û�itcode:",
				cls : 'common-text',
				width : 100,
				style : 'width:150;text-align:right'
			}, new Ext.form.TextField({
				name : "amanageritcode",
				fieldLabel : "��ǩ��itcode",
				id : "amanageritcode",
				allowBlank : false,
				width : 200
			}),{
				html : "��/������:",
				cls : 'common-text',
				width : 100,
				style : 'width:150;text-align:right'
			},new Ext.form.ComboBox({
				xtype : 'combo',
				id : 'itil_ac_PersonFormalAccount$password',
				colspan : 0,
				rowspan : 0,
				name : 'itil_ac_PersonFormalAccount$password',
				width : 200,
			   // style : 'color:red',
			    displayField : 'text',
			    valueField : 'value',
				allowBlank : false,
				validator : '',
				fieldLabel : '��ע˵��',
				emptyText : '��ѡ��...',
				mode: 'local',
				store:new Ext.data.SimpleStore({
			             fields: ['value', 'text'],
			             data : [['1','������'],['0','������']]
			        })

			})
			],
			buttons : [{
				text : '����',
				iconCls : 'save',
				id:'save',
				handler : function() {
					if(!Ext.getCmp('addForm').form.isValid()){
					Ext.MessageBox.alert("��ʾ","������ϢΪ������,����д������лл��������");	
					return false;
					}
					var amanageritcode=Ext.getCmp('amanageritcode').getValue().trim();
					var amanagername=Ext.getCmp('itil_ac_PersonFormalAccount$password').getValue();
					
					if(amanageritcode==''||amanageritcode==null){
						Ext.MessageBox.alert("��ʾ","�û�itcode����Ϊ��,лл���ĺ���!");
						return false;
					}
					if(amanagername==''||amanagername==null){
						Ext.MessageBox.alert("��ʾ","��/�����Ͳ���Ϊ��,лл���ĺ���!");
						return false;
					}
					
					Ext.getCmp("save").disable();
					Ext.getCmp("back").disable();
					Ext.Ajax.request({
						url : webContext
								+ '/accountSystemAdminAction_addRAndBUserList.action',
						params : {
							manageritcode : amanageritcode,
							managername : amanagername
							
						},
						success : function(response, options) {
							var r = Ext.decode(response.responseText);
							if(r.noitcode==1){
								Ext.Msg.alert("��ʾ","����ʧ�ܣ�ϵͳ��û�����itcode��");
								Ext.getCmp("save").enable();
								Ext.getCmp("back").enable();
							}else if(r.noitcode==2){
									Ext.Msg.alert("��ʾ","����ʧ�ܣ�����û��Ѿ��ں�������У�");
									Ext.getCmp("save").enable();
									Ext.getCmp("back").enable();
							}else{
								Ext.Msg.alert("��ʾ", "����ɹ�", function() {
									Ext.getCmp('grid').store.load();
									Ext.getCmp('addwin').close();
								});
							}
							
						},
						failure : function(response, options) {
							Ext.MessageBox.alert("��ʾ", "����ʧ��!");
							Ext.getCmp("save").enable();
			                Ext.getCmp("back").enable();
						}
					}, this);
				}
			},  {
				text : 'ȡ��',
			    id:'back',
				iconCls : 'back',
				handler : function() {
					Ext.getCmp('addwin').close();
				}
			}]
		});
		return addform;
	
	},
	
	items : this.items,
	initComponent : function(){
		var getModifyform=this.getModifyform;
		var sm = new Ext.grid.CheckboxSelectionModel();
		
		this.fp = this.getSearchForm();

		this.store = new Ext.data.JsonStore({
				url : webContext + "/accountSystemAdminAction_listRAndBUserList.action",
				fields : ['id', 'countSignItcode', 'countSignName', 'department'],
				root : 'data',
				totalProperty : 'rowCount',
				sortInfo : {
					field : "id",
					direction : "DESC"
				},
				baseParams : {
				}
			});
		var pageBar = new Ext.PagingToolbar({
			pageSize : 10,
			store : this.store,
			displayInfo : true,
			displayMsg : '��ǰ��ʾ {0}-{1}����¼ /��{2}����¼',
			emptyMsg : "����ʾ����"
		});
		
		
		
		
		var searchConfig=function(){
			var param = Ext.getCmp("searchForm").getForm().getValues(false);
			var store=Ext.getCmp("grid").getStore();
	        param.start = 0;  
	        store.on('beforeload', function(a) {   
					      Ext.apply(a.baseParams,param);   
					});
	        store.load({
	            params : param
	        });
		};
		var searchButton = new Ext.Button({
			style : 'margin:2px 0px 2px 5px',
			handler :searchConfig,
			pressed:true,
			text : '��ѯ',
			iconCls : 'search'
		});
		var resetButton = new Ext.Button({
			pressed:true,
			style : 'margin:2px 0px 2px 5px',
			handler :function(){
				Ext.getCmp("searchForm").getForm().reset();
			},
			text : '����',
			iconCls : 'reset'
		});
		var AddButton = new Ext.Button({
			pressed:true,
			style : 'margin:2px 0px 2px 5px',
			scope : this,
			handler :function(){
				var addwin= new Ext.Window({ 
					id:'addwin',
	                width:340, 
	                modal : true,
	                height:160,   
	                items: [this.getAddform()]   
	           	});   
	           	addwin.show();
			},
			text : '���',
			iconCls : 'add'
		});
		var removeButton = new Ext.Button({
			text:'�޸�Ϊ������',
			pressed:true,
			iconCls:'edit',
			style : 'margin:2px 0px 2px 5px',
			scope : this,
			handler:function(){
				var record = this.grid.getSelectionModel().getSelected();
				var records = this.grid.getSelectionModel().getSelections();
				if (!record) {
					Ext.Msg.alert("��ʾ", "����ѡ��Ҫ�޸ĵ���!");
					return;
				}
				if (records.length == 0) {
					Ext.MessageBox.alert('��ʾ', '��ѡ��һ����Ϣ�����޸�!');
					return;
				}
				if(records.length > 1){
					Ext.MessageBox.alert('��ʾ', 'ÿ��ֻ���޸�һ����¼!');
					return;
				}
			
				var mid=record.get("id");
								
				Ext.Ajax.request({
						url : webContext
								+ '/accountSystemAdminAction_modifyRAndBUserList.action',
						params : {
							id:mid,
							manageritcode : '0'
						},
						success : function(response, options) {
						var r = Ext.decode(response.responseText);
							if(r.success){
									Ext.Msg.alert("��ʾ", "�޸ĳɹ�", function() {
									Ext.getCmp('grid').store.load();
										
									});
							}else{
								Ext.Msg.alert("��ʾ","����ʧ�ܣ�");
							}
						},
						failure : function(response, options) {
							Ext.MessageBox.alert("��ʾ", "����ʧ��!");
						}
					}, this);
			}
		});
		var modifyToBlackButton = new Ext.Button({
			text:'�޸�Ϊ������',
			pressed:true,
			iconCls:'edit',
			style : 'margin:2px 0px 2px 5px',
			scope : this,
			handler:function(){
				var record = this.grid.getSelectionModel().getSelected();
				var records = this.grid.getSelectionModel().getSelections();
				if (!record) {
					Ext.Msg.alert("��ʾ", "����ѡ��Ҫ�޸ĵ���!");
					return;
				}
				if (records.length == 0) {
					Ext.MessageBox.alert('��ʾ', '��ѡ��һ����Ϣ�����޸�!');
					return;
				}
				if(records.length > 1){
					Ext.MessageBox.alert('��ʾ', 'ÿ��ֻ���޸�һ����¼!');
					return;
				}
			
				var mid=record.get("id");
								
				Ext.Ajax.request({
						url : webContext
								+ '/accountSystemAdminAction_modifyRAndBUserList.action',
						params : {
							id:mid,
							manageritcode : '1'
						},
						success : function(response, options) {
						var r = Ext.decode(response.responseText);
							if(r.success){
									Ext.Msg.alert("��ʾ", "�޸ĳɹ�", function() {
									Ext.getCmp('grid').store.load();
										
									});
							}else{
								Ext.Msg.alert("��ʾ","����ʧ�ܣ�");
							}
						},
						failure : function(response, options) {
							Ext.MessageBox.alert("��ʾ", "����ʧ��!");
						}
					}, this);
			}
		});
		var DelButton = new Ext.Button({
			pressed:true,
			style : 'margin:2px 0px 2px 5px',
			scope : this,
			handler :function(){
				var record = this.grid.getSelectionModel().getSelected();
				var records = this.grid.getSelectionModel().getSelections();
				if (!record) {
					Ext.Msg.alert("��ʾ", "����ѡ��Ҫɾ������!");
					return;
				}
				if (records.length == 0) {
					Ext.MessageBox.alert('��ʾ', '��ѡ��һ����Ϣ����ɾ��!');
					return;
				}
				if(records.length > 1){
					Ext.MessageBox.alert('��ʾ', 'ÿ��ֻ��ɾ��һ����¼!');
					return;
				}
				var mid=record.get("id");
				Ext.Ajax.request({
						url : webContext
								+ '/accountSystemAdminAction_DeleteRAndBUserList.action',
						params : {
							id:mid
						},
						success : function(response, options) {
							
							var r = Ext.decode(response.responseText);
							if(r.success){
								if(r.noitcode){
								}else{
									Ext.Msg.alert("��ʾ", "ɾ���ɹ�", function() {
										Ext.getCmp('grid').store.load();
									});
								}
							}else{
								Ext.Msg.alert("��ʾ","ɾ��ʧ�ܣ�");
							}
							
						},
						failure : function(response, options) {
							Ext.MessageBox.alert("��ʾ", "ɾ��ʧ��!");
							
						}
					}, this);
			},
			text : 'ɾ��',
			iconCls : 'remove'
		});
		var mybuttons = new Array();
		var buttonUtil = new ButtonUtil();
		mybuttons.push(searchButton);
		mybuttons.push(resetButton);
		mybuttons.push(AddButton);
		mybuttons.push(removeButton);
		mybuttons.push(modifyToBlackButton);
		mybuttons.push(DelButton);
		
		this.grid = new Ext.grid.GridPanel({
			id:"grid",
			name:"grid",
			region : "center",
			store : this.store,
			columns : [sm, {
				header : '�Զ����',
				dataIndex : 'id',
				align : 'left',
				sortable : true,
				width:150,
				hidden : true
			},  {
				header : '�û�ITCODE',
				dataIndex : 'department',
				align : 'center',
				width:150,
				sortable : false,
				hidden : false
			}, {
				header : '�������',
				dataIndex : 'countSignItcode',
				align : 'center',
				sortable : false,
				width:150,
				hidden : false
			}, {
				header : '�������',
				dataIndex : 'countSignName',
				align : 'center',
				sortable : false,
				width:150,
				hidden : true
			}
			],
			sm : sm,
			loadMask : true,
			tbar : mybuttons,
			bbar : pageBar
		});
		var items = new Array();
		items.push(this.fp);
		items.push(this.grid);
		this.items = items;
		
		var params={start:0};
		this.store.on('beforeload', function(a) {   
					      Ext.apply(a.baseParams,params);   
					});
        this.store.load({
            params :params
        });
		PagePanel.superclass.initComponent.call(this);
	}
});