PagePanel = Ext.extend(Ext.Panel, {
	closable : true,

	height:800,
	layout : 'border',
	getSearchForm : function() {
		var applyNum = new Ext.form.TextField({
			name : "deptname",
			fieldLabel : "��������",
			id : "deptname",
			width : 150
		});
		
		this.panel = new Ext.form.FormPanel({
			id:"searchForm",
			region : "north", 
			layout : 'table',
			height : 240,
			frame : true,
			defaults : {
				bodyStyle : 'padding:4px'
			},
			layoutConfig : {
				columns : 1
			},
			items : [ {
			xtype : 'fieldset',
		    title : 'ע������',
			layout : 'table',
		    width : '100%',
			autoHeight : true,
		    defaults : {
				bodyStyle : 'padding:4px'
			},
			layoutConfig : {
				columns : 4
			},
			items:[{
				html : '<h3><font color=red>SBU��ǩ�˱��˵��:</font></h3><br> 1��ҳ����ʾ����Ϣ���ʺ��������漰��SBU��ǩʱ�ж��Ƿ���ҪSBU��ǩ�������ӷ�Χ��Ӧ����Ӧ���̵ļ�ǩ����Ϣ��<br>2��ÿ����¼�����Ӧһ����ǩ�ˡ�<br>3�����޸ļ�ǩ����Ϣ֮ǰ�ύ�����̣���ת����ǩ�˴�������ʱ���ҵ��޸�֮ǰ�ļ�ǩ��ȥ�������޸ļ�ǩ��ֻ���޸��Ժ��ύ��������Ч�����޸ļ�ǩ�˲�����Ѿ���ת��ԭ��ǩ�˴�����������ת�Ƶ��¼�ǩ�˴���<br>4�������ݲ�����ɾ������ĳ�������ӷ�Χ��ĳ�����̲���ҪSBU��ǩ�˻�������SBU��ǩ������ϵ��̨������Ա�޸ġ�',
				cls : 'common-text',
				colspan : 4,
				rowspan : 0
			}]},{
			xtype : 'fieldset',
		    title : '��ѯ����',
			layout : 'table',
		    anchor : '100%',
			autoHeight : true,
		    defaults : {
				bodyStyle : 'padding:4px'
			},
			layoutConfig : {
				columns : 4
			},
			items:[{
				html : "��������:",
				cls : 'common-text',
				width : 150,
				style : 'width:150;text-align:center'
			}, applyNum]}
		
			]
		});

		return this.panel;
	},

	getModifyform :function(mid,department,countSignItcode,countSignName){
		var modifyform= new Ext.form.FormPanel({
			id:"modifyForm",
			layout : 'table',
			title : '�޸ļ�ǩ����Ϣ',
			frame : true,
			collapsible : false,
			defaults : {
				bodyStyle : 'padding:4px'
			},
			layoutConfig : {
				columns : 2
			},
			
			items : [{
				html : "������:",
				cls : 'common-text',
				width : 100,
				style : 'width:150;text-align:right'
			}, new Ext.form.TextField({
				name : "mmanageritcode",
				fieldLabel : "������",
				id : "mmanageritcode",
				value:countSignItcode,
				readOnly: true,
				allowBlank : false,
				width : 200
			}),{
				html : "�����ӷ�Χ:",
				cls : 'common-text',
				width : 100,
				style : 'width:150;text-align:right'
			}, new Ext.form.TextField({
				name : "mmanagername",
				fieldLabel : "�����ӷ�Χ",
				id : "mmanagername",
				allowBlank : false,
				readOnly: true,
				value:countSignName,
				width : 200
			}),{
				html : "��ǩ��:",
				cls : 'common-text',
				width : 100,
				style : 'width:150;text-align:right'
			}, new Ext.form.TextField({
				name : "mdeptname",
				fieldLabel : "��ǩ��",
				id : "mdeptname",
				allowBlank : false,
				value:department,
				width : 200
			}),new Ext.form.Hidden({
					xtype : 'hidden',
					id : 'mid',
					name : 'mspid',
					value : mid,
					fieldLabel : 'isTemp'
				})
			],
			buttons : [{
				text : '�����޸�',
				iconCls : 'save',
				id:'save',
				handler : function() {
					if(!Ext.getCmp('modifyForm').form.isValid()){
					Ext.MessageBox.alert("��ʾ","������ϢΪ������,����д������лл��������");	
					return false;
					}
					var adeptname=Ext.getCmp('mdeptname').getValue().trim();
					var mid=Ext.getCmp('mid').getValue();
					if(adeptname==''||adeptname==null){
						Ext.MessageBox.alert("��ʾ","�������Ʋ���Ϊ��,лл���ĺ���!");
						return false;
					}
					
					Ext.getCmp("save").disable();
					Ext.getCmp("back").disable();
					Ext.Ajax.request({
						url : webContext
								+ '/accountSystemAdminAction_modifyAccountSBUOfficer.action',
						params : {
							id:mid,
							deptname : adeptname
							
						},
						success : function(response, options) {
							
							var r = Ext.decode(response.responseText);
							if(r.success){
								if(r.noitcode){
									Ext.Msg.alert("��ʾ","����ʧ�ܣ�ϵͳ��û�����itcode��");
									Ext.getCmp("save").enable();
									Ext.getCmp("back").enable();
								}else{
									Ext.Msg.alert("��ʾ", "����ɹ�", function() {
										Ext.getCmp('grid').store.load();
										Ext.getCmp('modifywin').close();
									});
								}
							}else{
								Ext.Msg.alert("��ʾ","����ʧ�ܣ�");
									Ext.getCmp("save").enable();
									Ext.getCmp("back").enable();
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
					Ext.getCmp('modifywin').close();
				}
			}]
		});
		
		return modifyform;
	},
	
	items : this.items,
	initComponent : function(){
		var getModifyform=this.getModifyform;
		var sm = new Ext.grid.CheckboxSelectionModel();
		
		this.fp = this.getSearchForm();

		this.store = new Ext.data.JsonStore({
				url : webContext + "/accountSystemAdminAction_listAccountSBUOfficer.action",
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
		
		var removeButton = new Ext.Button({
			text:'�޸�',
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
				var department=record.get("department");
				var countSignItcode=record.get("countSignItcode");
				var countSignName=record.get("countSignName");
				
				var win= new Ext.Window({ 
					modal : true,
					id:'modifywin',
	                width:340,   
	                height:180,   
	                items: [getModifyform(mid,department,countSignItcode,countSignName)]   
	           	});   
	           	win.show();

			}
		});
		var mybuttons = new Array();
		var buttonUtil = new ButtonUtil();
		mybuttons.push(searchButton);
		mybuttons.push(resetButton);
		mybuttons.push(removeButton);
		
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
			}, {
				header : '������',
				dataIndex : 'countSignItcode',
				align : 'center',
				sortable : false,
				width:150,
				hidden : false
			}, {
				header : '�����ӷ�Χ',
				dataIndex : 'countSignName',
				align : 'center',
				sortable : true,
				width:150,
				hidden : false
			},{
				header : 'SBU��ǩ��',
				dataIndex : 'department',
				align : 'center',
				width:150,
				sortable : false,
				hidden : false
			}
			],
			sm : sm,
			loadMask : true,
			tbar : mybuttons,
			bbar : pageBar,
			listeners :{'rowdblclick':function(gd,num){
				var mid=gd.store.getAt(num).get("id");
				var department=gd.store.getAt(num).get("department");
				var countSignItcode=gd.store.getAt(num).get("countSignItcode");
				var countSignName=gd.store.getAt(num).get("countSignName");
			
				var win= new Ext.Window({ 
					modal : true,
					id:'modifywin',
	                width:340,   
	                height:180,   
	                items: [getModifyform(mid,department,countSignItcode,countSignName)]   
	           	});   
				
	           	win.show();
			}} 
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