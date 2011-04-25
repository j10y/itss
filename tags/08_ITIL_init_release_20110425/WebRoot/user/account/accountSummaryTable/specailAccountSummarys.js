PageTemplates = Ext.extend(Ext.Panel, {
	id : "PageTemplates",
	layout : 'table',
	height : 'auto',
	align : 'center',
	foredit : true,
	width : 1050,
	frame : true,
	autoScroll : true,
	defaults : {
		bodyStyle : 'padding:4px'
	},
	layoutConfig : {
		columns : 1
	},
	getTabpanel : function(tab, tabTitle) {
		this.tabPanel = new Ext.TabPanel({
			xtype : 'tabpanel',
			activeTab : 0,
			enableTabScroll : true,
			
			// minTabWidth:100,
			// resizeTabs:true,
			title : tabTitle,
			deferredRender : false,
			frame : true,
			plain : true,
			border : false,
			// tabPosition:'bottom',
			baseCls : 'x-plain',// �Ƿ����úͱ���ɫͬ��
			width : 1000,
			// bodyBorder : true,
			defaults : {
				autoHeight : true,
				bodyStyle : 'padding:2px'
			},
			items : tab
		});
		return this.tabPanel;

	},
	getPanel : function(appa, panelTitle) {
		var userid=this.userId;
		this.Panel = new Ext.Panel({
			id : "Pages",
			height : 'auto',
			align : 'center',
			title : panelTitle,
			border : false,
			defaults : {
				bodyStyle : 'padding:5px'
			},
			width : 'auto',
			frame : true,
			autoScroll : true,
			layoutConfig : {
				columns : 1
			},
			items : appa
		});
		return this.Panel;

	},


	getFormpanel_accountSummary_list : function() {
		var userid=this.userId;
		this.formpanel_accountSummary_list = new Ext.form.FormPanel({
			id : 'panel_accountSummary_list',
			layout : 'column',
			region:'center',
			height : 'auto',
			width : 800,
			frame : true,
			collapsible : true,
			defaults : {
				bodyStyle : 'padding:4px'
			},
			reader : new Ext.data.JsonReader({
				root : 'form',
				successProperty : 'success'
			}, [{
				name : 'sUserInfos$itcode',
				mapping : 'sUserInfos$itcode'
			}, {
				name : 'sUserInfos$realName',
				mapping : 'sUserInfos$realName'
			}, {
				name : 'sUserInfos$userName',
				mapping : 'sUserInfos$userName'
			}, {
				name : 'sUserInfos$department',
				mapping : 'sUserInfos$department'
			}, {
				name : 'sUserInfos$email',
				mapping : 'sUserInfos$email'
			}, {
				name : 'sUserInfos$telephone',
				mapping : 'sUserInfos$telephone'
			}, {
				name : 'sUserInfos$mobilePhone',
				mapping : 'sUserInfos$mobilePhone'
			}, {
				name : 'sUserInfos$employeeCode',
				mapping : 'sUserInfos$employeeCode'
			}, {
				name : 'sUserInfos$costCenterCode',
				mapping : 'sUserInfos$costCenterCode'
			}, {
				name : 'sUserInfos$sameMailDept',
				mapping : 'sUserInfos$sameMailDept'
			}, {
				name : 'sUserInfos$workSpace',
				mapping : 'sUserInfos$workSpace'
			}, {
				name : 'sUserInfos$mailServer',
				mapping : 'sUserInfos$mailServer'
			}, {
				name : 'sUserInfos$userType',
				mapping : 'sUserInfos$userType'
			}, {
				name : 'sUserInfos$personnelScope',
				mapping : 'sUserInfos$personnelScope'
			},
			{
				name : 'account$comment',
				mapping : 'account$comment'
			}
			
			]),
			title : "<center><font size=3px>Ա�������ʺ���Ϣ��Ҫ��</font></center>",
			items : [
            {
			xtype : 'fieldset',
		    title : '������Ϣ',
		    layout : 'table',
		    anchor : '100%',
			autoHeight : true,
			animCollapse:true,
		    collapsible :true,
		    style : 'border:1px dotted #b0acac',
		    defaults : {
				bodyStyle : 'padding:4px'
			},
			layoutConfig : {
				columns : 4
			},
			items : [
			 {
				html : '����:',
				cls : 'common-text',
				style : 'width:135;text-align:right'
			}, new Ext.form.TextField({
				fieldLabel : '����',
				xtype : 'textfield',
				colspan : 0,
				rowspan : 0,
				id : 'sUserInfos$realName',
				name : 'sUserInfos$realName',
				style : '',
				width : 200,
				value : '',
				allowBlank : true,
				validator : '',
				vtype : ''
			}), 
			 {
				html : 'ITCode:',
				cls : 'common-text',
				style : 'width:135;text-align:right'
			}, new Ext.form.TextField({
				fieldLabel : 'ITCode',
				xtype : 'textfield',
				colspan : 0,
				rowspan : 0,
				id : 'sUserInfos$itcode',
				name : 'sUserInfos$itcode',
				style : '',
				width : 200,
				value : '',
				allowBlank : true,
				validator : '',
				vtype : ''
			}), 
			{
				html : 'Ա�����:',
				cls : 'common-text',
				style : 'width:135;text-align:right'
			}, new Ext.form.TextField({
				fieldLabel : 'Ա�����',
				xtype : 'textfield',
				colspan : 0,
				rowspan : 0,
				id : 'sUserInfos$employeeCode',
				name : 'sUserInfos$employeeCode',
				style : '',
				width : 200,
				value : '',
				allowBlank : true,
				validator : '',
				vtype : ''
			}), {
				html : '�ɱ����ĺ�:',
				cls : 'common-text',
				style : 'width:135;text-align:right'
			}, new Ext.form.TextField({
				fieldLabel : '�ɱ����ĺ�',
				xtype : 'textfield',
				colspan : 0,
				rowspan : 0,
				id : 'sUserInfos$costCenterCode',
				name : 'sUserInfos$costCenterCode',
				style : '',
				width : 200,
				value : '',
				allowBlank : true,
				validator : '',
				vtype : ''
			}), 
			{
				html : '��������:',
				cls : 'common-text',
				style : 'width:135;text-align:right'
			}, new Ext.form.ComboBox({
				xtype : 'combo',
				hiddenName : 'sUserInfos$department',
				id : 'sUserInfos$departmentCombo',
				width : 200,
				style : '',
				fieldLabel : '��������',
				colspan : 0,
				rowspan : 0,
				lazyRender : true,
				displayField : 'departName',
				valueField : 'id',
				emptyText : '��ѡ��...',
				allowBlank : true,
				
				name : 'sUserInfos$department',
				triggerAction : 'all',
				minChars : 50,
				queryDelay : 700,
				store : new Ext.data.JsonStore({
					url : webContext
							+ '/extjs/comboDataAction?clazz=com.digitalchina.info.framework.security.entity.Department',
					fields : ['id', 'departName'],
					listeners : {
						beforeload : function(store, opt) {
							if (opt.params['sUserInfos$department'] == undefined) {
								opt.params['departName'] = Ext
										.getCmp('sUserInfos$departmentCombo').defaultParam;
							}
						}
					},
					totalProperty : 'rowCount',
					root : 'data',
					id : 'id'
				}),
				pageSize : 10,
				listeners : {
					'beforequery' : function(queryEvent) {if(this.readOnly==true){return false}
						var param = queryEvent.combo.getRawValue();
						this.defaultParam = param;
						if (queryEvent.query == '') {
							param = '';
						}
						this.store.load({
							params : {
								departName : param,
								start : 0
							}
						});
						return true;
					}
				},
				initComponent : function() {
					this.store.load({
						params : {
							id : Ext.getCmp('sUserInfos$departmentCombo')
									.getValue(),
							start : 0
						},
						callback : function(r, options, success) {
							Ext
									.getCmp('sUserInfos$departmentCombo')
									.setValue(Ext
											.getCmp('sUserInfos$departmentCombo')
											.getValue());
						}
					});
				}
			}),
				{
				html : '�ʼ��ȼ۸�����:',
				cls : 'common-text',
				style : 'width:135;text-align:right'
			}, new Ext.form.ComboBox({
				xtype : 'combo',
				hiddenName : 'sUserInfos$sameMailDept',
				id : 'sUserInfos$sameMailDeptCombo',
				width : 200,
				style : '',
				fieldLabel : '�ʼ��ȼ۸�����',
				colspan : 0,
				rowspan : 0,
				lazyRender : true,
				displayField : 'name',
				valueField : 'id',
				emptyText : '��ѡ��...',
				allowBlank : true,
				
				name : 'sUserInfos$sameMailDept',
				triggerAction : 'all',
				minChars : 50,
				queryDelay : 700,
				store : new Ext.data.JsonStore({
					url : webContext
							+ '/extjs/comboDataAction?clazz=com.digitalchina.info.framework.security.entity.SameMailDept',
					fields : ['id', 'name'],
					listeners : {
						beforeload : function(store, opt) {
							if (opt.params['sUserInfos$sameMailDept'] == undefined) {
								opt.params['name'] = Ext
										.getCmp('sUserInfos$sameMailDeptCombo').defaultParam;
							}
						}
					},
					totalProperty : 'rowCount',
					root : 'data',
					id : 'id'
				}),
				pageSize : 10,
				listeners : {
					'beforequery' : function(queryEvent) {if(this.readOnly==true){return false}
						var param = queryEvent.combo.getRawValue();
						this.defaultParam = param;
						if (queryEvent.query == '') {
							param = '';
						}
						this.store.load({
							params : {
								name : param,
								start : 0
							}
						});
						return true;
					}
				},
				initComponent : function() {
					this.store.load({
						params : {
							id : Ext.getCmp('sUserInfos$sameMailDeptCombo')
									.getValue(),
							start : 0
						},
						callback : function(r, options, success) {
							Ext
									.getCmp('sUserInfos$sameMailDeptCombo')
									.setValue(Ext
											.getCmp('sUserInfos$sameMailDeptCombo')
											.getValue());
						}
					});
				}
			}),
			{
				html : '�����ص�:',
				cls : 'common-text',
				style : 'width:135;text-align:right'
			}, new Ext.form.ComboBox({
				xtype : 'combo',
				hiddenName : 'sUserInfos$workSpace',
				id : 'sUserInfos$workSpaceCombo',
				width : 200,
				style : '',
				fieldLabel : '�����ص�',
				colspan : 0,
				rowspan : 0,
				lazyRender : true,
				displayField : 'name',
				valueField : 'id',
				emptyText : '��ѡ��...',
				allowBlank : true,
				
				name : 'sUserInfos$workSpace',
				triggerAction : 'all',
				minChars : 50,
				queryDelay : 700,
				store : new Ext.data.JsonStore({
					url : webContext
							+ '/extjs/comboDataAction?clazz=com.digitalchina.info.framework.security.entity.WorkSpace',
					fields : ['id', 'name'],
					listeners : {
						beforeload : function(store, opt) {
							if (opt.params['sUserInfos$workSpace'] == undefined) {
								opt.params['name'] = Ext
										.getCmp('sUserInfos$workSpaceCombo').defaultParam;
							}
						}
					},
					totalProperty : 'rowCount',
					root : 'data',
					id : 'id'
				}),
				pageSize : 10,
				listeners : {
					'beforequery' : function(queryEvent) {if(this.readOnly==true){return false}
						var param = queryEvent.combo.getRawValue();
						this.defaultParam = param;
						if (queryEvent.query == '') {
							param = '';
						}
						this.store.load({
							params : {
								name : param,
								start : 0
							}
						});
						return true;
					}
				},
				initComponent : function() {
					this.store.load({
						params : {
							id : Ext.getCmp('sUserInfos$workSpaceCombo')
									.getValue(),
							start : 0
						},
						callback : function(r, options, success) {
							Ext
									.getCmp('sUserInfos$workSpaceCombo')
									.setValue(Ext
											.getCmp('sUserInfos$workSpaceCombo')
											.getValue());
						}
					});
				}
			}), 
			{
				html : '�û�����:',
				cls : 'common-text',
				style : 'width:135;text-align:right'
			}, new Ext.form.ComboBox({
				xtype : 'combo',
				hiddenName : 'sUserInfos$userType',
				id : 'sUserInfos$userTypeCombo',
				width : 200,
				style : '',
				fieldLabel : '�û�����',
				colspan : 0,
				rowspan : 0,
				lazyRender : true,
				displayField : 'name',
				valueField : 'id',
				emptyText : '��ѡ��...',
				allowBlank : true,
				
				name : 'sUserInfos$userType',
				triggerAction : 'all',
				minChars : 50,
				queryDelay : 700,
				store : new Ext.data.JsonStore({
					url : webContext
							+ '/extjs/comboDataAction?clazz=com.digitalchina.info.framework.security.entity.UserType',
					fields : ['id', 'name'],
					listeners : {
						beforeload : function(store, opt) {
							if (opt.params['sUserInfos$userType'] == undefined) {
								opt.params['name'] = Ext
										.getCmp('sUserInfos$userTypeCombo').defaultParam;
							}
						}
					},
					totalProperty : 'rowCount',
					root : 'data',
					id : 'id'
				}),
				pageSize : 10,
				listeners : {
					'beforequery' : function(queryEvent) {if(this.readOnly==true){return false}
						var param = queryEvent.combo.getRawValue();
						this.defaultParam = param;
						if (queryEvent.query == '') {
							param = '';
						}
						this.store.load({
							params : {
								name : param,
								start : 0
							}
						});
						return true;
					}
				},
				initComponent : function() {
					this.store.load({
						params : {
							id : Ext.getCmp('sUserInfos$userTypeCombo')
									.getValue(),
							start : 0
						},
						callback : function(r, options, success) {
							Ext.getCmp('sUserInfos$userTypeCombo').setValue(Ext
									.getCmp('sUserInfos$userTypeCombo')
									.getValue());
						}
					});
				}
			})
            ]},{
            xtype : 'fieldset',
            title : '�����ʺ���Ϣ',
			layout:'fit',
		    anchor : '100%',
		    animCollapse:true,
		    height:400,
		    style : 'border:1px dotted #b0acac;margin:15px 0px 0px px',
			
		    defaults : {
				bodyStyle : 'padding:4px'
			},
			layoutConfig : {
				columns : 2
			},
			items:[
			new Ext.Panel({
		    frame:true,
		    layout:'fit',
		    style:'margin:0px 0px 0px 25px',
			items:[this.grid2]}
			)
			]},
			{
			xtype : 'fieldset',
		    title : 'Ա�������ʺż�Ҫ���޸ļ�¼',
		    layout : 'table',
		    anchor : '100%',
			autoHeight : true,
			animCollapse:true,
		    collapsible :true,
		    style : 'border:1px dotted #b0acac;margin:10px 0px 0px 0px ',
		    defaults : {
				bodyStyle : 'padding:4px'
			},
			layoutConfig : {
				columns : 1
			},
			items : [
			 new Ext.form.HtmlEditor({
					height : 250,
					id:'account$comment',
					name : 'account$comment',
					enableLists : true,
					enableSourceEdit : false,
					allowBlank : false,
					width : 750
					
				})
			]
			}
			],
			buttons : [{
				text : '����',
				iconCls : 'save',
				id:'save',
				handler : function() {
		           var record =  Ext.getCmp('account').getSelectionModel().getSelected();
		           var records =  Ext.getCmp('account').getSelectionModel().getSelections();
		            var id = new Array();
		             var accountState = new Array();
		              var mailValue = new Array();
		               var wwwAccountValue = new Array();
		                var erpUserName = new Array();
		                 var telephone = new Array();
		                  var accountName = new Array();
		                   var rightsDesc = new Array();
		                    var remarkDesc = new Array();
		                     var cardNumber=new Array();
		                       var endDate = new Array();
		           for(var i=0;i<records.length;i++){
				     id[i] = records[i].get('id');
				     accountState[i]=records[i].get('accountState');
				      mailValue[i]=records[i].get('mailValue');
				       wwwAccountValue[i]=records[i].get('wwwAccountValue');
				        endDate[i]=records[i].get('endDate');
				        //modify by liuying at 20100126 start
				        var endDate=endDate[i];
				        if(endDate!='--'){
				         endDate=endDate[i].format('Y-m-d');
				        }
						//modify by liuying at 20100126 end
				        erpUserName[i]=records[i].get('erpUserName');
				         telephone[i]=records[i].get('telephone');
				          rightsDesc[i]=records[i].get('rightsDesc');
				           remarkDesc[i]=records[i].get('remarkDesc');
				            accountName[i]=records[i].get('accountName');
				            cardNumber[i]=records[i].get('cardNumber');
				     Ext.Ajax.request({
						url : webContext
								+ '/accountAction_saveSpecailAccountSummary.action',
						params : {
						id:id[i],
						accountState:accountState[i],
						mailValue:mailValue[i],
						wwwAccountValue:wwwAccountValue[i],
						erpUserName:erpUserName[i],
						accountName:accountName[i],
						remarkDesc:remarkDesc[i],
						rightsDesc:rightsDesc[i],
						telephone:telephone[i],
						cardNumber:cardNumber[i],
						endDate:endDate
						},
						timeout:100000, 
						success:function(response){
	                       var r =Ext.decode(response.responseText);
	                       if(!r.success){
	                       	 Ext.Msg.alert("��ʾ","����ʧ��");
	                       		 return false;	
	                       }
	                       else{
                              
	                       }
	                       
	                   },scope:this});
				}
				Ext.Msg.alert("��ʾ","����ɹ�");
            
				}
			},  {
				text : '�˳�',
				iconCls : 'back',
				id:'back',
				handler : function() {
				window.close();
				}
			}]
		});
		var userid=this.userId;
		this.formpanel_accountSummary_list.load({
				
				 url : webContext
						    + '/accountAction_initSpecailSummaryData.action',
				                params : {
							    userInfo : userid
							 
				 },
			    timeout : 30,
				success : function(action, form) {
					Ext.getCmp("sUserInfos$departmentCombo").initComponent();
					Ext.getCmp("sUserInfos$sameMailDeptCombo").initComponent();
					Ext.getCmp("sUserInfos$workSpaceCombo").initComponent();
//					Ext.getCmp("sUserInfos$mailServerCombo").initComponent();
					Ext.getCmp("sUserInfos$userTypeCombo").initComponent();
//					Ext.getCmp("sUserInfos$personnelScopeCombo")
//							.initComponent();
				}
			});
		
		return this.formpanel_accountSummary_list;
	},
	items : this.items,
	/*
	 * clientvalidation ��ʼ��
	 */
	initComponent : function() {
		var items = new Array();
		var pa = new Array();
		this.pa = pa;
		var gd = new Array();
		this.gd = gd;
		var temp = new Array();
		this.temp = temp;
		var formname = new Array();
		this.formname = formname;
		var gridname = new Array();
		this.gridname = gridname;
		this.model = "ar_personAccountSummary";
		this.store = new Ext.data.JsonStore({
			         url : webContext + "/accountAction_listSpecailAccountSummary.action",
			          fields: ['id','accountType','accountState','mailValue','wwwAccountValue','erpUserName','telephone','cardNumber','endDate','rightsDesc','accountName','remarkDesc'],//add by liuying at 20100126 for ҳ�����
			          root:'data',
			          totalProperty : 'rowCount',
			          sortInfo: {field: "id", direction: "ASC"},
			          baseParams :{
			          	 userInfo : this.userId
			          }
		     });
		     
		this.store.paramNames.sort = "orderBy";
		this.store.paramNames.dir = "orderType";
		
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
		this.formValue = '';
		this.pageBar.formValue = this.formValue;
		var sm = new Ext.grid.CheckboxSelectionModel();
	    this.grid2 = new Ext.grid.EditorGridPanel({
	                   id:'account',
	                   region : "center",
	                   name:'account',
	                   title:'˫����Ԫ��ɱ༭',
			           store : this.store,
			           viewConfig: {
                       forceFit:true
                     },
			       columns : [sm, {header : '�Զ����',dataIndex : 'id',align : 'left',sortable : true,hidden : true}, 
						{header : '<font color=red>�ʺ�����</font>',dataIndex : 'accountType',align : 'center',sortable : true,hidden : false},
						{header : '<font color=red>�ʺ���</font>',dataIndex : 'accountName',align : 'center',sortable : true,hidden : false,editor:new Ext.grid.GridEditor(new Ext.form.TextField({allowBlank:true}))},//add by liuying at 20100126 for ҳ����� end
						{header : '<font color=red>״̬</font>',dataIndex : 'accountState',align : 'center',sortable : true,hidden : false,editor:new Ext.grid.GridEditor(new Ext.form.TextField({allowBlank:true}))},
						{header : '<font color=red>�����С(�ʼ��ʺ�)</font>',dataIndex : 'mailValue',align : 'center',sortable : true,hidden : false,
						editor:new Ext.grid.GridEditor(new Ext.form.ComboBox({
				xtype : 'combo',
				hiddenName : 'itil_ac_PersonFormalAccount$mailValue',
				id : 'itil_ac_PersonFormalAccount$mailValueCombo',
				width : 200,
				style : '',
				fieldLabel : '��������',
				colspan : 0,
				rowspan : 0,
				lazyRender : true,
				displayField : 'volume',
				valueField : 'volume',
				emptyText : '��ѡ��...',
				allowBlank : false,
				
				name : 'itil_ac_PersonFormalAccount$mailValue',
				triggerAction : 'all',
				minChars : 50,
				queryDelay : 700,
				store : new Ext.data.JsonStore({
					url : webContext
							+ '/extjs/comboDataAction?clazz=com.digitalchina.itil.config.extlist.entity.MailVolume',
					fields : ['id', 'volume'],
					listeners : {
						beforeload : function(store, opt) {
							if (opt.params['itil_ac_PersonFormalAccount$mailValue'] == undefined) {
								opt.params['volume'] = Ext
										.getCmp('itil_ac_PersonFormalAccount$mailValueCombo').defaultParam;
							}
						}
					},
					totalProperty : 'rowCount',
					root : 'data',
					id : 'id'
				}),
				pageSize : 10,
				listeners : {
					'beforequery' : function(queryEvent) {if(this.readOnly==true){return false}
						var param = queryEvent.combo.getRawValue();
						this.defaultParam = param;
						if (queryEvent.query == '') {
							param = '';
						}
						this.store.load({
							params : {
								volume : param,
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
									.getCmp('itil_ac_PersonFormalAccount$mailValueCombo')
									.getValue(),
							start : 0
						},
						callback : function(r, options, success) {
							Ext
									.getCmp('itil_ac_PersonFormalAccount$mailValueCombo')
									.setValue(Ext
											.getCmp('itil_ac_PersonFormalAccount$mailValueCombo')
											.getValue());
						}
					});
				}
			}))
						
						
						},  
				{header : '<font color=red>WWW���(www�ʺ�)</font>',dataIndex : 'wwwAccountValue',align : 'center',sortable : true,hidden : false,
				 editor:new Ext.grid.GridEditor( new Ext.form.ComboBox({
				xtype : 'combo',
				hiddenName : 'itil_ac_PersonFormalAccount$wwwAccountValue',
				id : 'itil_ac_PersonFormalAccount$wwwAccountValueCombo',
				width : 200,
				style : '',
				fieldLabel : 'www�ʺŶ��',
				colspan : 0,
				rowspan : 0,
				lazyRender : true,
				displayField : 'type',
				valueField : 'type',
				emptyText : '��ѡ��...',
				allowBlank : false,
				
				name : 'itil_ac_PersonFormalAccount$wwwAccountValue',
				triggerAction : 'all',
				minChars : 50,
				queryDelay : 700,
				store : new Ext.data.JsonStore({
					url : webContext
							+ '/extjs/comboDataAction?clazz=com.digitalchina.itil.config.extlist.entity.WWWScanType',
					fields : ['id', 'type'],
					listeners : {
						beforeload : function(store, opt) {
							if (opt.params['itil_ac_PersonFormalAccount$wwwAccountValue'] == undefined) {
								opt.params['type'] = Ext
										.getCmp('itil_ac_PersonFormalAccount$wwwAccountValueCombo').defaultParam;
							}
						}
					},
					totalProperty : 'rowCount',
					root : 'data',
					id : 'id'
				}),
				pageSize : 10,
				listeners : {
					'beforequery' : function(queryEvent) {if(this.readOnly==true){return false}
						var param = queryEvent.combo.getRawValue();
						this.defaultParam = param;
						if (queryEvent.query == '') {
							param = '';
						}
						this.store.load({
							params : {
								type : param,
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
									.getCmp('itil_ac_PersonFormalAccount$wwwAccountValueCombo')
									.getValue(),
							start : 0
						},
						callback : function(r, options, success) {
							Ext
									.getCmp('itil_ac_PersonFormalAccount$wwwAccountValueCombo')
									.setValue(Ext
											.getCmp('itil_ac_PersonFormalAccount$wwwAccountValueCombo')
											.getValue());
						}
					});
				}
			}))},
						{header : '<font color=red>ERP�ʺ�����(����ERP�ʺ�)</font>',dataIndex : 'erpUserName',align : 'center',sortable : true,hidden : false,editor:new Ext.grid.GridEditor(new Ext.form.TextField({allowBlank:true}))},
						{header : '<font color=red>�ֻ���(EB�ʺ�)</font>',dataIndex : 'telephone',align : 'center',sortable : true,hidden : false,editor:new Ext.grid.GridEditor(new Ext.form.TextField({allowBlank:true}))}, 
						{header : '<font color=red>���ƿ���(VPN�ʺ�)</font>',dataIndex : 'cardNumber',align : 'center',sortable : true,hidden : false,editor:new Ext.grid.GridEditor(new Ext.form.TextField({allowBlank:true}))}, 
						{header : '<font color=red>��������</font>',dataIndex : 'endDate',align : 'center',sortable : true,hidden : false,editor:new Ext.grid.GridEditor(new Ext.form.DateField({
				xtype : 'datefield',
		        colspan : 0,
				rowspan : 0,
				width : 200,
				style : '',
				value : '',
				allowBlank : true,
			    validator : '',
				format : 'Y-m-d',
				fieldLabel : '��������'
			}))}, 
						{header : '<font color=red>����Ҫ��</font>',dataIndex : 'rightsDesc',align : 'center',sortable : true,hidden : false,editor:new Ext.grid.GridEditor(new Ext.form.TextField({allowBlank:true}))},
						{header : '<font color=red>��ע˵��</font>',dataIndex : 'remarkDesc',align : 'center',sortable : true,hidden : false,editor:new Ext.grid.GridEditor(new Ext.form.TextField({allowBlank:true}))}
				],
			sm : sm,
			trackMouseOver : false,
			loadMask : true,
		    height : 400,
		    width:700,
			tbar : new Ext.Toolbar(['-',{
			text:'�޸�',
			iconCls : 'edit',
		    pressed : true,
		    handler : function(){},
				scope : this
			
			 },'-',{
				text:'���',
				iconCls : 'add',
				handler:function(){}
			
			},'-',{
				text:'ɾ��',
				iconCls : 'delete',
				handler:function(){
					
				}
			
			},'-'
			
			]),
			bbar : this.pageBar
			
		});
		var param = {
			'mehtodCall' : 'query',
			'start' : 0,
			'rootFlag' :1,
			'status':0
		};
		
		this.store.on('beforeload', function(a) {   
					      Ext.apply(a.baseParams,param);   
					});
		
		this.store.removeAll();
		this.store.load({
			params : param
		});
//		this.grid2.on("rowdblclick", this.modify, this);//lookInPage
        this.getFormpanel_accountSummary_list();
		this.pa.push(this.formpanel_accountSummary_list);
		this.formname.push("panel_accountSummary_list");
		temp.push(this.formpanel_accountSummary_list);
		items = temp;
		
		this.items = items;
		PageTemplates.superclass.initComponent.call(this);
	}
})