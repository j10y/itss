PagePanel = Ext.extend(Ext.Panel, {
	id : "PagePanel",
	// title:this.pageModeltitle,
	closable : true,
	viewConfig : {
		autoFill : true,
		forceFit : true
	},
	layout : 'border',
	reset:function(){
		this.fp.form.reset();
	},
	search : function() {
		var tempDept = Ext.getCmp('searchDept').getValue();
		var tempAudit = Ext.getCmp('searchAuditCombo').getValue();
		var param = {
					departmentName : tempDept,
					auditUser : tempAudit,
					start: 0
					};
		this.store.baseParams=param;
		this.store.removeAll();
		this.store.load();
	},
	create : function() {
		var clazz = "com.zsgj.itil.require.entity.RequireApplyDefaultAudit";
		var panel = 'requireApplyDefaultAuditPanel';
		var da = new DataAction();
		var data = da.getPanelElementsForAdd(panel);
		//add by awen for change dept textfield to combo on 2011-06-08 begin
		data[1] = new Ext.form.ComboBox(
				{
					hiddenName : "itil_RequireApplyDefaultAudit$departmentName",
					id : "itil_RequireApplyDefaultAudit$departmentNameCombo",
					width : "null",
					fieldLabel : "����",
					lazyRender : true,
					displayField : "departName",
					valueField : "id",
					forceSelection : true,
					emptyText : "��ѡ��...",
					allowBlank : true,
					name : "itil_RequireApplyDefaultAudit$departmentName",
					triggerAction : "all",
					queryDelay : 700,
					store : new Ext.data.JsonStore(
							{
								url : webContext
										+ "/extjs/comboDataAction?clazz=com.zsgj.info.framework.security.entity.Department",
								fields : [ "id", "departName" ],
								totalProperty : "rowCount",
								root : "data"
							}),
					pageSize : 10,
					listeners : {
						"beforequery" : function(queryEvent) {
							var store = queryEvent.combo.store;
							store.baseParams.departName = queryEvent.combo
									.getRawValue();
							store.baseParams.start = 0;
							store.load();
							return false;
						},
						"select" : function(combo, record, index) {
							Ext.getCmp("itil_RequireApplyDefaultAudit$departmentNameCombo").setValue(record.data['departName'] + "/" + record.data['id']);
						}
					},
					initComponent : function() {
						if (this.getValue() != '') {
							var combo = this;
							this.store
									.load({
										params : {
											id : combo
													.getValue(),
											start : 0
										},
										callback : function(r,
												options,
												success) {
											if (r.length > 0) {
												combo
														.setRawValue(r[0]
																.get(combo.displayField));
											}
										}
									});
						}
					}
				});
		//add by awen for change dept textfield to combo on 2011-06-08 end
		
		var custdata = da.split(data);
		var envForm = new Ext.form.FormPanel({
			id : 'envFormPanelForAdd',
			layout : 'table',
			width : 390,
			height : 350,
			layoutConfig : {
				columns : 2
			},
			defaults : {
				bodyStyle : 'padding:15px '
			},
			frame : true,
			items : custdata

		});
		
		var store = this.store;
	     var win = new Ext.Window({
				title : 'ERP������',
				width : 400,
				height : 400,
				modal : true,
				maximizable : true,
				items : envForm,
				closeAction : 'hide',
				bodyStyle : 'padding:4px',
				buttons : [{//�����ťʱ�����ȴ�����еõ��������ݣ����ύ����ӦAction�еķ���
					text : '����',
					//id:'postButton',
					handler : function() {
						var bsParam = Ext.encode(getFormParam('envFormPanelForAdd',false));
						da.saveDataFromPanel(panel,bsParam,function(){						
							store.reload();
							win.close()
						});	
					},
					scope : this

				}, {
					text : '�ر�',
					handler : function() {
						win.hide();
					},
					scope : this
				}]

			});
		win.show();

	},
	//ɾ��
	remove : function() {
		
		var clazz = "com.zsgj.itil.require.entity.RequireApplyDefaultAudit";
		var record = this.grid.getSelectionModel().getSelected();
		var records = this.grid.getSelectionModel().getSelections();
		if (!record) {
			Ext.Msg.alert("��ʾ", "����ѡ��Ҫɾ������!");
			return;
		}
		if (records.length == 0) {
			Ext.MessageBox.alert('����', '����ѡ��һ����Ϣ������ɾ��!');
			return;
		}
		var id = new Array();
		var da = new DataAction();
//		for(var i=0;i<records.length;i++){			
//			id[i] = records[i].data.id;
//		//da.removeData(clazz,{'id':id[i]});
//		
//		}
		//this.store.reload();

		// var param="";
		var firm  =Ext.Msg.confirm('��ʾ��', '��ȷ��Ҫ����ɾ��������?', function(button) {
			if (button == 'yes') {
				for(var i=0;i<records.length;i++){
				id[i] = records[i].get('id');
				Ext.Ajax.request({
						url : webContext
								+ '/requireAction_removeRequireAudit.action',
						params : {id:id[i]},
						timeout:100000, 
						success:function(response){
	                       var r =Ext.decode(response.responseText);
	                       if(!r.success){
	                       		Ext.Msg.alert("��ʾ��Ϣ","����ɾ��ʧ��",function(){
	                       				//this.store.reload();
	                       			//location = 'ibmRelPerson.jsp'
	                       			//firm.close();
	                       		});
	                       }
	                       else{
                                Ext.Msg.alert("��ʾ��Ϣ","�ɹ�ɾ������!",function(){
                                	
                                   // location = 'ibmRelPerson.jsp'
                                    //this.store.reload()
                                },this);
	                       }
	                       this.store.reload();
                          // location = 'ibmCust.jsp'  
	                   },scope:this
						
						
						
						
						
					});
				}
			}
		}, this);

	},
	
	//�޸�
	modify:function(){
		var record = this.grid.getSelectionModel().getSelected();
	    var records = this.grid.getSelectionModel().getSelections();
		if(!record){
				Ext.Msg.alert("��ʾ","����ѡ��Ҫ�޸ĵ���!");
				return;
			}
		if(records.length>1){
				Ext.Msg.alert("��ʾ","�޸�ʱֻ��ѡ��һ��!");
				return;
			}
		var id = record.get("id");//********************************�õ��޸��е�id
	
		
		var clazz = "com.zsgj.itil.require.entity.RequireApplyDefaultAudit";
		var panel = 'requireApplyDefaultAuditPanel';
		var da = new DataAction();
		var data = da.getSingleFormPanelElementsForEdit(panel,id);
		
		var tempValue = data[1].value;
		//add by awen for change dept textfield to combo on 2011-06-08 begin
		data[1] = new Ext.form.ComboBox(
				{
					hiddenName : "itil_RequireApplyDefaultAudit$departmentName",
					id : "itil_RequireApplyDefaultAudit$departmentNameCombo",
					width : "null",
					fieldLabel : "����",
					lazyRender : true,
					displayField : "departName",
					valueField : "id",
					forceSelection : true,
					emptyText : "��ѡ��...",
					allowBlank : true,
					name : "itil_RequireApplyDefaultAudit$departmentName",
					triggerAction : "all",
					queryDelay : 700,
					store : new Ext.data.JsonStore(
							{
								url : webContext
										+ "/extjs/comboDataAction?clazz=com.zsgj.info.framework.security.entity.Department",
								fields : [ "id", "departName" ],
								totalProperty : "rowCount",
								root : "data"
							}),
					pageSize : 10,
					listeners : {
						"beforequery" : function(queryEvent) {
							var store = queryEvent.combo.store;
							store.baseParams.departName = queryEvent.combo
									.getRawValue();
							store.baseParams.start = 0;
							store.load();
							return false;
						},
						"select" : function(combo, record, index) {
							Ext.getCmp("itil_RequireApplyDefaultAudit$departmentNameCombo").setValue(record.data['departName'] + "/" + record.data['id']);
						}
					},
					initComponent : function() {
						if (this.getValue() != '') {
							var combo = this;
							this.store
									.load({
										params : {
											id : combo
													.getValue(),
											start : 0
										},
										callback : function(r,
												options,
												success) {
											if (r.length > 0) {
												combo
														.setRawValue(r[0]
																.get(combo.displayField));
											}
										}
									});
						}
					}
				});		
		
		data[1].value = tempValue;
		//add by awen for change dept textfield to combo on 2011-06-08 end
		var custdata = da.split(data);
		
		var envForm = new Ext.form.FormPanel({
			id : 'envFormPanel',
			layout : 'table',
			width : 500,
			height : 480,
			layoutConfig : {
				columns : 2
			},
			defaults : {
				bodyStyle : 'padding:15px'
			},
			frame : true,
			items : custdata

		});
		var store=this.store;
		var win1 = new Ext.Window({
				title : 'Erp������',
				width : 500,
				height : 480,
				maximizable : true,
				items : envForm,
				closeAction : 'hide',
				bodyStyle : 'padding:4px',

				buttons : [{
					text : '����',
					handler : function() {
						var bsParam = Ext.encode(getFormParam('envFormPanel',false));
						da.saveDataFromPanel(panel,bsParam,function(){						
							store.reload();
							win1.close()
						});							
					},
					scope : this

				}, {
					text : '�ر�',
					handler : function() {
						//location = 'ibmCust.jsp'
						win1.close();						
					},
					listeners: {
						'beforeclose':  function(p) {						
							return true;
						}
					},
					scope : this
				}]
			});
			
		win1.show();
	},
	getSearchForm : function() {
		this.panel = new Ext.form.FormPanel({
			id : 'searchPanel',
			region : "north",
			layout : 'column',
			height : 'auto',
			width : 'auto',
			frame : true,
			collapsible : true,
			defaults : {
				bodyStyle : 'padding:4px'
			},
		   title : "��ѯ�б�",
		   items :[{
					columnWidth : .5,
					layout : 'form',
					border : false,
					items : [new Ext.form.TextField({
						fieldLabel : '��������',
						id:'searchDept',
						name:'searchDept',
						xtype : 'textfield',
						colspan : 0,
						rowspan : 0,
					
						style : '',
						width : 200,
						value : '',
						allowBlank : true,
						validator : '',
						vtype : ''
					})]
				},{
					columnWidth : .5,
					layout : 'form',
					border : false,
					items : [new Ext.form.ComboBox({
						xtype : 'combo',
						hiddenName : 'searchAudit',
						id : 'searchAuditCombo',
						width : 200,
						style : '',
						fieldLabel : '������',
						colspan : 0,
						rowspan : 0,
						lazyRender : true,
						displayField : 'userName',
						valueField : 'id',
						emptyText : '��ѡ��...',
						allowBlank : true,
						typeAhead : true,
						name : 'searchAudit',
						triggerAction : 'all',
						minChars : 50,
						queryDelay : 700,
						store : new Ext.data.JsonStore({
							url : webContext
									+ '/extjs/comboDataAction?clazz=com.zsgj.info.framework.security.entity.UserInfo',
							fields : ['id', 'userName'],
							listeners : {
								beforeload : function(store, opt) {
									if (opt.params['searchAudit'] == undefined) {
										opt.params['userName'] = Ext
												.getCmp('searchAuditCombo').defaultParam;
									}
								}
							},
							totalProperty : 'rowCount',
							root : 'data',
							id : 'id'
						}),
						pageSize : 10,
						listeners : {
							'beforequery' : function(queryEvent) {
								var param = queryEvent.combo.getRawValue();
								this.defaultParam = param;
								if (queryEvent.query == '') {
									param = '';
								}
								this.store.load({
									params : {
										userName : param,
										start : 0
									}
								});
								return true;
							}
						},
						initComponent : function() {
							this.store.load({
								params : {
									id : Ext
											.getCmp('searchAuditCombo')
											.getValue(),
									start : 0
								},
								callback : function(r, options, success) {
									Ext
											.getCmp('searchAuditCombo')
											.setValue(Ext
													.getCmp('searchAuditCombo')
													.getValue());
								}
							});
						}
					})]
				}]
		});
		return this.panel;
	},
	items : this.items,
	initComponent : function() {
		 this.fp = this.getSearchForm();
		var sm = new Ext.grid.CheckboxSelectionModel();
		 //this.cm = new Ext.grid.ColumnModel(columns);
		this.store = new Ext.data.JsonStore({
			url : webContext + "/requireAction_listRequireAudit.action",
			
			fields: ['id','departmentName','cadreBizAudit','cadreFinanceAudit','groupFinanceAudit','cadreBusinessAudit','clientItManager','sortNum','enable'],
			root:'data',
			totalProperty : 'rowCount',
			sortInfo: {field: "sortNum", direction: "ASC"}
		});
		this.store.paramNames.sort = "orderBy";
		this.store.paramNames.dir = "orderType";
		// this.cm.defaultSortable = true;
		var viewConfig = Ext.apply({
			forceFit : true
		}, this.gridViewConfig);

		this.pageBar = new Ext.PagingToolbar({
			pageSize : 10,
			store : this.store,
			displayInfo : true,
			displayMsg : '��ǰ��ʾ {0}-{1}����¼ /��{2}����¼',
			emptyMsg : "����ʾ����"
		});
		this.grid = new Ext.grid.GridPanel({
			region : "center",
			store : this.store,
			// cm : this.cm,
			columns : [sm, {header : '�Զ����',dataIndex : 'id',align : 'left',sortable : true,hidden : true}, 
						{header : '����ֵ',dataIndex : 'sortNum',align : 'left',sortable : true,hidden : true}, 
						{header : '��������',dataIndex : 'departmentName',align : 'left',sortable : true,hidden : false}, 
						{header : '����������',dataIndex : 'cadreBizAudit',align : 'left',sortable : true,hidden : false},
						{header : '���Ų���������',dataIndex : 'cadreFinanceAudit',align : 'left',sortable : true,hidden : false}, 
						{header : '��˾����������',dataIndex : 'groupFinanceAudit',align : 'left',sortable : true,hidden : false},
						{header : '��˾����������',dataIndex : 'cadreBusinessAudit',align : 'left',sortable : true,hidden : false},
						{header : '��Ʒ����',dataIndex : 'clientItManager',align : 'left',sortable : true,hidden : false},
						{header : '����',dataIndex : 'enable',align : 'left',sortable : true,renderer:function(value){
							if(value=='1'){
								return '��';
							}else{
								return '��';
							}
						},hidden : false},
						{header : '����ֵ',dataIndex : 'sortNum',align : 'left',sortable : true,hidden : false}],
			sm : sm,
			trackMouseOver : false,
			loadMask : true,
			y : 140,
			height : 200,
			anchor : '0 -35',
			bbar : this.pageBar,
			tbar : ['   ', {
				text : '����',
				pressed : true,
				handler : this.create,
				scope : this,
				iconCls : 'add'
			}, '   ', {
				text : 'ɾ��',
				pressed : true,
				handler : this.remove,
				scope : this,
				iconCls : 'remove'
			}, '    &nbsp;|&nbsp;    ', {
				text : '��ѯ',
				pressed : true,
				handler : this.search,
				scope : this,
				iconCls : 'search'
			}, '   ', {
				text : ' ����',
				pressed : true,
				handler : this.reset,
				scope : this,
				iconCls : 'reset'
			},'     ',{
				text : ' �޸�',
				pressed : true,
				handler : this.modify,
				scope : this,
				iconCls : 'edit'
			}]
			
		});

		this.grid.on("headerdblclick", this.fitWidth, this);
		this.grid.on("rowdblclick", this.modify, this);
		var items = new Array();
		items.push(this.fp);
		items.push(this.grid);
		this.items = items;

		PagePanel.superclass.initComponent.call(this);
		var param = {
			'mehtodCall' : 'query',
			'start' : 0,
			'status':1
		};
		this.store.baseParams=param;
		this.store.removeAll();
		this.store.load();
	},
	fitWidth : function(grid, columnIndex, e) {
		var c = columnIndex;
		var w = grid.view.getHeaderCell(c).firstChild.scrollWidth;
		for (var i = 0, i = grid.store.getCount(); i < l; i++) {
			w = Math.max(w, grid.view.getCell(i, c).firstChild.scrollWidth);
		}
		grid.colModel.setColumnWidth(c, w);
	}
});
