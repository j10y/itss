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
			title : tabTitle,
			deferredRender : false,
			frame : true,
			plain : true,
			border : true,
			baseCls : 'x-plain',// �Ƿ����úͱ���ɫͬ��
			width : 800,
			defaults : {
				autoHeight : true,
				bodyStyle : 'padding:2px'
			},
			items : tab,
			tbar : [new Ext.Toolbar.TextItem('<font color=red>��ʾ��</font><font color=blue>1.ҳ���д���ɫ<font color=red>*</font>�ŵı����������д���������ύ���룡</font>')]

		});
		return this.tabPanel;

	},

	getFormpanel_NotesIDFile : function() {
		var curscid = this.serviceItemId;
		var pName = this.processName;
		var processType = this.processType;
		var processInfoId=this.serviceItemProcess;
		this.formpanel_NotesIDFile = new Ext.form.FormPanel({
			id : 'panel_NotesIDFile',
			layout : 'column',
			height : 'auto',
			width : 800,
			frame : true,
			//collapsible : true,
			defaults : {
				bodyStyle : 'padding:4px'
			},
			reader : new Ext.data.JsonReader({
				root : 'form',
				successProperty : 'success'
			}, [{
				name : 'AccountApplyMainTable$mail',
				mapping : 'AccountApplyMainTable$mail'
			}, {
				name : 'AccountApplyMainTable$createUser',
				mapping : 'AccountApplyMainTable$createUser'
			}, {
				name : 'AccountApplyMainTable$createDate',
				mapping : 'AccountApplyMainTable$createDate'
			}, {
				name : 'AccountApplyMainTable$modifyUser',
				mapping : 'AccountApplyMainTable$modifyUser'
			}, {
				name : 'AccountApplyMainTable$modifyDate',
				mapping : 'AccountApplyMainTable$modifyDate'
			}, {
				name : 'AccountApplyMainTable$id',
				mapping : 'AccountApplyMainTable$id'
			}, {
				name : 'AccountApplyMainTable$name',
				mapping : 'AccountApplyMainTable$name'
			}, {
				name : 'AccountApplyMainTable$oldApply',
				mapping : 'AccountApplyMainTable$oldApply'
			}, {
				name : 'AccountApplyMainTable$processType',
				mapping : 'AccountApplyMainTable$processType'
			}, {
				name : 'AccountApplyMainTable$status',
				mapping : 'AccountApplyMainTable$status'
			}, {
				name : 'AccountApplyMainTable$deleteFlag',
				mapping : 'AccountApplyMainTable$deleteFlag'
			}, {
				name : 'AccountApplyMainTable$serviceItem',
				mapping : 'AccountApplyMainTable$serviceItem'
			}, {
				name : 'AccountApplyMainTable$applyDate',
				mapping : 'AccountApplyMainTable$applyDate'
			}, {
				name : 'AccountApplyMainTable$applyUser',
				mapping : 'AccountApplyMainTable$applyUser'
			}, {
				name : 'AccountApplyMainTable$delegateApplyUser',
				mapping : 'AccountApplyMainTable$delegateApplyUser'
			}, {
				name : 'AccountApplyMainTable$applyUserTel',
				mapping : 'AccountApplyMainTable$applyUserTel'
			}, {
				name : 'AccountApplyMainTable$delegateApplyTel',
				mapping : 'AccountApplyMainTable$delegateApplyTel'
			}, {
				name : 'AccountApplyMainTable$attachment',
				mapping : 'AccountApplyMainTable$attachment'
			}, {
				name : 'AccountApplyMainTable$confirmUser',
				mapping : 'AccountApplyMainTable$confirmUser'
			}, 
			{
				name : 'sUserInfos$employeeCode',
				mapping : 'sUserInfos$employeeCode'
			}, {
				name : 'sUserInfos$costCenterCode',
				mapping : 'sUserInfos$costCenterCode'
			},
			 {
				name : 'sUserInfos$userType',
				mapping : 'sUserInfos$userType'
			},
				{
				name : 'NotesIDFile$id',
				mapping : 'NotesIDFile$id'
			}, {
				name : 'NotesIDFile$fileName',
				mapping : 'NotesIDFile$fileName'
			}, {
				name : 'NotesIDFile$webMail',
				mapping : 'NotesIDFile$webMail'
			}, {
				name : 'NotesIDFile$dcMail',
				mapping : 'NotesIDFile$dcMail'
			}, {
				name : 'NotesIDFile$attachment',
				mapping : 'NotesIDFile$attachment'
			},
			 {
				name : 'NotesIDFile$noPassword',
				mapping : 'NotesIDFile$noPassword'
			},
			 {
				name : 'NotesIDFile$password',
				mapping : 'NotesIDFile$password'
			},
			{
				name : 'NotesIDFile$createDate',
				mapping : 'NotesIDFile$createDate'
			}]),
			title : "��ȡNotesID�ļ�����",
			items : [{
			xtype : 'fieldset',
		    title : '��������Ϣ',
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
			
			items:[{
				html : '������:',
				cls : 'common-text',
				style : 'width:135;text-align:right'
			}, new Ext.form.TextField({
				fieldLabel : '������',
				xtype : 'textfield',
				colspan : 0,
				rowspan : 0,
				id : 'AccountApplyMainTable$name',
				name : 'AccountApplyMainTable$name',
				style : '',
				width : 200,
				readOnly : true,
			    
				emptyText : '�Զ�����',
				value : '',
				allowBlank : true,
				validator : '',
				vtype : ''
			}), {
				html : '��������:',
				cls : 'common-text',
				style : 'width:135;text-align:right'
			}, new Ext.form.DateField({
				xtype : 'datefield',
				id : 'AccountApplyMainTable$applyDate',
				colspan : 0,
				rowspan : 0,
				name : 'AccountApplyMainTable$applyDate',
				width : 200,
				style : '',
				value : '',
				allowBlank : true,
				hideTrigger:true,
				readOnly : true,
				validator : '',
				format : 'Y-m-d',
				fieldLabel : '��������'
			}), {
				html : '������:',
				cls : 'common-text',
				style : 'width:135;text-align:right'
			}, new Ext.form.ComboBox({
				xtype : 'combo',
				hiddenName : 'AccountApplyMainTable$applyUser',
				id : 'AccountApplyMainTable$applyUserCombo',
				width : 200,
				style : '',
				fieldLabel : '������',
				hideTrigger:true,
				colspan : 0,
				rowspan : 0,
				readOnly : true,
				lazyRender : true,
				displayField : 'userName',
				valueField : 'id',
				emptyText : '��ѡ��...',
				allowBlank : true,
				typeAhead : true,
				name : 'AccountApplyMainTable$applyUser',
				triggerAction : 'all',
				minChars : 50,
				queryDelay : 700,
				store : new Ext.data.JsonStore({
					url : webContext
							+ '/extjs/comboDataAction?clazz=com.digitalchina.info.framework.security.entity.UserInfo',
					fields : ['id', 'userName'],
					listeners : {
						beforeload : function(store, opt) {
							if (opt.params['AccountApplyMainTable$applyUser'] == undefined) {
								opt.params['userName'] = Ext
										.getCmp('AccountApplyMainTable$applyUserCombo').defaultParam;
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
								userName : param,
								start : 0
							}
						});
						return true;
					}
				},
				initComponent : function() {
						if(this.getValue()!=''){
							var combo=this;
							this.store.load({
								params : {
									id : this.getValue(),
									start : 0
								},
								callback : function(r, options, success) {
									if(r.length>0){
										combo.setRawValue(r[0].get(combo.displayField));
									}
								}
							});
						}
					}
			}),
			{
				html : '<font color=red>*</font>��ϵ�绰:',
				cls : 'common-text',
				style : 'width:135;text-align:right'
			}, new Ext.form.TextField({
				fieldLabel : '��������ϵ�绰',
				xtype : 'textfield',
				colspan : 0,
			
				readOnly : true,
				rowspan : 0,
				id : 'AccountApplyMainTable$applyUserTel',
				name : 'AccountApplyMainTable$applyUserTel',
				style : '',
				width : 200,
				value : '',
				allowBlank : false,
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
				readOnly : true,
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
				readOnly : true,
				value : '',
				allowBlank : true,
				validator : '',
				vtype : ''
			}),
			{
				html : '�û����/Ա����:',
				cls : 'common-text',
				style : 'width:135;text-align:right'
			},new Ext.form.ComboBox({
				xtype : 'combo',
				hiddenName : 'sUserInfos$userType',
				id : 'sUserInfos$userTypeCombo',
				width : 200,
				style : '',
				fieldLabel : '�û�����',
				colspan : 0,
				rowspan : 0,
				lazyRender : true,
				readOnly : true,
				hideTrigger:true,
				displayField : 'name',
				valueField : 'id',
				emptyText : '��ѡ��...',
				allowBlank : true,
				typeAhead : true,
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
				]}, 
				{
			xtype : 'fieldset',
		    title : '�����ʺ���Ϣ',
			layout : 'table',
		    anchor : '100%',
		    animCollapse:true,
			collapsible :true,
		    style : 'border:1px dotted #b0acac;margin:15px 0px 0px 0px',
			autoHeight : true,
		    defaults : {
				bodyStyle : 'padding:4px'
			},
			layoutConfig : {
				columns : 1
			},
			items:[
			{
			xtype : 'fieldset',
		    title : '<font color=red>���»�ȡID�ļ�(��ѡ)</font>',
			layout : 'table',
		    anchor : '100%',
		    id:"idFile",
		    checkboxToggle:'true',
		    checkboxName:'file',
		    collapsed:true,
		    animCollapse:false,
			collapsible :false,
			autoHeight : true,
			style : 'border:1px dotted #b0acac;margin:5px 0px 0px 25px',
		    defaults : {
				bodyStyle : 'padding:4px'
			},
			layoutConfig : {
				columns : 4
			},
			items:[
			{
						html : '<center><a href="http://bjas2/ITHELPME.nsf/00dc6ddbfdcfea6848256d59001efa87/b1e5c359d4f8aab7482575690023cfb6?OpenDocument" target="_blank"><font color=red size="4px" style="text-decoration:none">�����»�ȡID�ļ�����β�����</font></a></center>',
						cls : 'common-text',
						colspan : 4,
						rowspan : 0,
						style : 'border:1px dotted #b0acac;width:600;text-align:left;margin:0px 0px 0px 75px'
		    },
			{
				html : '��Ҫ��ȡID�ļ����ʼ��ʺ�����:',
				cls : 'common-text',
				style : 'width:135;text-align:right'
			}, new Ext.form.TextField({
				xtype : 'textfield',
				id : 'NotesIDFile$fileName',
				name : 'NotesIDFile$fileName',
				fieldLabel : 'id�ļ�',
				colspan : 0,
				rowspan : 0,
				style : '',
				width : 200,
				readOnly : true,
				value : '',
				allowBlank : true,
				validator : '',
				vtype : ''
				//modify by liuying for ��Ҫ��ȡID�ļ����ʼ��ʺ����Ƹ�Ϊ�ı��� at 20100421 start
//				store : new Ext.data.JsonStore({
//					url : webContext
//							+ '/extjs/comboDataAction?clazz=com.digitalchina.itil.account.entity.DCContacts',
//					fields : ['id', 'userNames'],
//					listeners : {
//						beforeload : function(store, opt) {
//							if (opt.params['NotesIDFile$fileName'] == undefined) {
//								opt.params['userNames'] = Ext
//										.getCmp('NotesIDFile$fileNameCombo').defaultParam;
//							}
//						}
//					},
//					totalProperty : 'rowCount',
//					root : 'data',
//					id : 'id'
//				}),
//				pageSize : 10,
//				listeners : {
//					'beforequery' : function(queryEvent) {if(this.readOnly==true){return false}
//						var param = queryEvent.combo.getRawValue();
//						this.defaultParam = param;
//						if (queryEvent.query == '') {
//							param = '';
//						}
//						this.store.load({
//							params : {
//								userName : param,
//								start : 0
//							}
//						});
//						return true;
//					}
//				},
//				initComponent : function() {
//					this.store.load({
//						params : {
//							id : Ext.getCmp('NotesIDFile$fileNameCombo')
//									.getValue(),
//							start : 0
//						},
//						callback : function(r, options, success) {
//							Ext
//									.getCmp('NotesIDFile$fileNameCombo')
//									.setValue(Ext
//											.getCmp('NotesIDFile$fileNameCombo')
//											.getValue());
//						}
//					});
//				}
		//modify by liuying for ��Ҫ��ȡID�ļ����ʼ��ʺ����Ƹ�Ϊ�ı��� at 20100421 end
			}),  {
				html : '���ܴ�ID�ļ�����������:',
				cls : 'common-text',
				style : 'width:135;text-align:right'
			}, 
			new Ext.form.TextField({
				xtype : 'textfield',
				id : 'NotesIDFile$dcMail',
				fieldLabel : 'DC����',
				name : 'NotesIDFile$dcMail',
				
				colspan : 0,
				rowspan : 0,
				style : '',
				width : 200,
				readOnly : true,
				value : '',
				allowBlank : true,
				validator : '',
				vtype : ''  
				//modify by liuying for ��Ҫ��ȡID�ļ����ʼ��ʺ����Ƹ�Ϊ�ı��� at 20100421 start
//				store : new Ext.data.JsonStore({
//					url : webContext
//							+ '/extjs/comboDataAction?clazz=com.digitalchina.itil.account.entity.DCContacts',
//					fields : ['id', 'email'],
//					listeners : {
//						beforeload : function(store, opt) {
//							if (opt.params['NotesIDFile$dcMail'] == undefined) {
//								opt.params['email'] = Ext
//										.getCmp('NotesIDFile$dcMailCombo').defaultParam;
//							}
//						}
//					},
//					totalProperty : 'rowCount',
//					root : 'data',
//					id : 'id'
//				}),
//				pageSize : 10,
//				listeners : {
//					'beforequery' : function(queryEvent) {if(this.readOnly==true){return false}
//						var param = queryEvent.combo.getRawValue();
//						this.defaultParam = param;
//						if (queryEvent.query == '') {
//							param = '';
//						}
//						this.store.load({
//							params : {
//								email : param,
//								start : 0
//							}
//						});
//						return true;
//					}
//				},
//				initComponent : function() {
//					this.store.load({
//						params : {
//							id : Ext.getCmp('NotesIDFile$dcMailCombo')
//									.getValue(),
//							start : 0
//						},
//						callback : function(r, options, success) {
//							Ext.getCmp('NotesIDFile$dcMailCombo').setValue(Ext
//									.getCmp('NotesIDFile$dcMailCombo')
//									.getValue());
//						}
//					});
//				}
		//modify by liuying for ��Ҫ��ȡID�ļ����ʼ��ʺ����Ƹ�Ϊ�ı��� at 20100421 end
			}),
				{
				html : 'Ҳ�������ⲿ������������ID(��ѡ):',
				cls : 'common-text',
				style : 'width:135;text-align:right'
			}, new Ext.form.TextField({
				fieldLabel : '�ⲿ����',
				xtype : 'textfield',
				colspan : 0,
				rowspan : 0,
				id : 'NotesIDFile$webMail',
				name : 'NotesIDFile$webMail',
				style : '',
				width : 200,
				value : '',
				allowBlank : true,
				validator : '',
				vtype : 'email'
			})
			,
			{
				html : '��ʼ����:',
				cls : 'common-text',
				style : 'width:135;text-align:right'
			}, new Ext.form.TextField({
				xtype : 'textfield',
				id : 'NotesIDFile$password',
				colspan : 0,
				rowspan : 0,
				name : 'NotesIDFile$password',
				width : 200,
			    style : 'color:red',
				allowBlank : true,
				validator : '',
				fieldLabel : '��ע˵��'

			})
		]},
		{
			xtype : 'fieldset',
		  
			layout : 'table',
		    anchor : '100%',
		    animCollapse:true,
			collapsible :false,
		    style : 'border:0px dotted #b0acac;margin:0px 0px 0px 0px',
			autoHeight : true,
		    defaults : {
				bodyStyle : 'padding:4px'
			},
			layoutConfig : {
				columns : 2
			},
			
		items:[
			{
				        xtype: 'checkbox',  // each item will be a checkbox
                        labelSeparator: '',
                        colspan : 2,
                        cls : 'common-text',
                        style : 'margin:0px 0px 0px 30px',
                        inputValue:'1',
                        id:'NotesIDFile$noPassword',
                        name:'NotesIDFile$noPassword',
                        boxLabel: '<font size=2px>ʹ�ó�ʼ����ID�ļ���½�������룬��ѡ�д���</font>'
              }
			 ]},
			
			
			
			new Ext.form.Hidden({
				xtype : 'hidden',
				id : 'AccountApplyMainTable$createUser',
				colspan : 0,
				rowspan : 0,
				name : 'AccountApplyMainTable$createUser',
				width : 200,
				style : '',
				value : '',
				fieldLabel : '������'
			}), new Ext.form.Hidden({
				xtype : 'hidden',
				id : 'AccountApplyMainTable$createDate',
				colspan : 0,
				rowspan : 0,
				name : 'AccountApplyMainTable$createDate',
				width : 200,
				style : '',
				value : '',
				fieldLabel : '��������'
			}), new Ext.form.Hidden({
				xtype : 'hidden',
				id : 'AccountApplyMainTable$modifyUser',
				colspan : 0,
				rowspan : 0,
				name : 'AccountApplyMainTable$modifyUser',
				width : 200,
				style : '',
				value : '',
				fieldLabel : '����޸���'
			}), new Ext.form.Hidden({
				xtype : 'hidden',
				id : 'AccountApplyMainTable$modifyDate',
				colspan : 0,
				rowspan : 0,
				name : 'AccountApplyMainTable$modifyDate',
				width : 200,
				style : '',
				value : '',
				fieldLabel : '����޸�����'
			}), new Ext.form.Hidden({
				xtype : 'hidden',
				id : 'AccountApplyMainTable$id',
				colspan : 0,
				rowspan : 0,
				name : 'AccountApplyMainTable$id',
				width : 200,
				style : '',
				value : '',
				fieldLabel : '�Զ����'
			}), new Ext.form.Hidden({
				xtype : 'hidden',
				id : 'AccountApplyMainTable$oldApply',
				colspan : 0,
				rowspan : 0,
				name : 'AccountApplyMainTable$oldApply',
				width : 200,
				style : '',
				value : '',
				fieldLabel : '���ǰ����'
			}), new Ext.form.Hidden({
				xtype : 'hidden',
				id : 'AccountApplyMainTable$processType',
				colspan : 0,
				rowspan : 0,
				name : 'AccountApplyMainTable$processType',
				width : 200,
				style : '',
				value : '',
				fieldLabel : '��������'
			}), new Ext.form.Hidden({
				xtype : 'hidden',
				id : 'AccountApplyMainTable$status',
				colspan : 0,
				rowspan : 0,
				name : 'AccountApplyMainTable$status',
				width : 200,
				style : '',
				value : '',
				fieldLabel : '״̬'
			}), new Ext.form.Hidden({
				xtype : 'hidden',
				id : 'AccountApplyMainTable$deleteFlag',
				colspan : 0,
				rowspan : 0,
				name : 'AccountApplyMainTable$deleteFlag',
				width : 200,
				style : '',
				value : '',
				fieldLabel : 'ɾ��״̬'
			}), new Ext.form.Hidden({
				xtype : 'hidden',
				id : 'AccountApplyMainTable$serviceItem',
				colspan : 0,
				rowspan : 0,
				name : 'AccountApplyMainTable$serviceItem',
				width : 200,
				style : '',
				value : '',
				fieldLabel : '��������'
			}), new Ext.form.Hidden({
				xtype : 'hidden',
				id : 'NotesIDFile$id',
				colspan : 0,
				rowspan : 0,
				name : 'NotesIDFile$id',
				width : 200,
				style : '',
				value : '',
				fieldLabel : '�Զ����'
			}), new Ext.form.Hidden({
				xtype : 'hidden',
				id : 'NotesIDFile$createDate',
				colspan : 0,
				rowspan : 0,
				name : 'NotesIDFile$createDate',
				width : 200,
				style : '',
				value : '',
				fieldLabel : '��Ч����'
			})]}
			,{
			xtype : 'fieldset',
		    title : '������Ϣ',
			layout : 'table',
		    anchor : '100%',
		    animCollapse:true,
			collapsible :true,
			autoHeight : true,
			style : 'border:1px dotted #b0acac;margin:20px 0px 0px 0px',
		    defaults : {
				bodyStyle : 'padding:4px'
			},
			layoutConfig : {
				columns : 2
			},
			items:[
			
           {
				html : '�ʼ�ID�ļ�:',
				cls : 'common-text',
				style : 'width:85;text-align:right'
			},
//			{
//				fieldLabel : '����',
//				xtype : 'button',
//				text : '<font color=red>�ϴ�</font>/<font color=green>����</font>',
//				width : '50',
//				scope : this,
//				handler : function() {
//					var attachmentFlag = Ext.getCmp('NotesIDFile$attachment')
//							.getValue();
//					if (attachmentFlag == '') {
//						attachmentFlag = Date.parse(new Date());
//						Ext.getCmp('NotesIDFile$attachment')
//								.setValue(attachmentFlag);
//						var ud = new UpDownLoadFile();
//						ud
//								.getUpDownLoadFileSu(attachmentFlag, '7887',
//										'com.digitalchina.info.appframework.metadata.entity.SystemFile');
//					} else {
//						var ud = new UpDownLoadFile();
//						ud
//								.getUpDownLoadFileSu(attachmentFlag, '7887',
//										'com.digitalchina.info.appframework.metadata.entity.SystemFile');
//					}
//				}
//			},
			{xtype:'panel',layout:'table',width:650,layoutConfig:{columns:4},
			fieldLabel:'������ַ',defaults:{baseCls:'margin : 10 15 12 15'},
			items:[

			{id:'NotesIDFile010attachment',width:600,border : true,html:'',cls : 'common-text',style : 'width:100;text-align:left'}]},
			new Ext.form.Hidden({
				xtype : 'hidden',
				id : 'NotesIDFile$attachment',
				name : 'NotesIDFile$attachment',
				style : '',
				value : 'null',
				fieldLabel : 'nowtime'
			})
			]
			}],
			buttons:[{
			text : '����',
				id:'back',
				iconCls : 'back',
				handler : function() {
				window.history.back(-1);
				}
			}
			],
			buttonAlign:'center'
		
		});
		if (this.dataId == "0" || this.dataId == "null") {
		
		}else{
			this.formpanel_NotesIDFile.load({
				url : webContext
						+ '/accountAction_getIDFileDraftData.action?panelName=panel_NotesIDFile&dataId='
						+ this.dataId,
				timeout : 30,
				scope:this,
				
				success : function(action, form) {
					 Ext.getCmp("AccountApplyMainTable$applyUserCombo")
							           .initComponent();
							           // Ext.getCmp("NotesIDFile$fileNameCombo")
							          // .initComponent();
							          // Ext.getCmp("NotesIDFile$dcMailCombo")
							         //  .initComponent();
					var da=new DataAction();
					var url=webContext
						+ '/accountAction_getFileList.action?clazzName=com.digitalchina.itil.config.extlist.entity.NotesIDFile&dataId='
						+ this.dataId+"&columnName=attachment&columnid=7887&hiddenId=NotesIDFile010attachment";
					var value=da.ajaxGetData(url);
					document.getElementById("NotesIDFile010attachment").innerHTML=value.file;
				  Ext.getCmp("AccountApplyMainTable$applyUserCombo")
							           .initComponent();
							             var idFile= Ext.getCmp("NotesIDFile$fileName").getValue();
							            if(idFile!=null&&idFile!=''){
							           	Ext.getCmp("idFile").expand(true);
							            Ext.getDom('file').checked=true;
							           }
				}
			});
			
			
		}
		return this.formpanel_NotesIDFile;
	},
	items : this.items,
	/*
	 * clientvalidation ��ʼ��
	 */
	initComponent : function() {
		var histroyForm = new HistroyForm({
			reqId : this.dataId,
			reqClass : "com.digitalchina.itil.require.entity.AccountApplyMainTable"
		});
		
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
		this.model = "ar_IDFileApply";
		

		this.getFormpanel_NotesIDFile();
		this.pa.push(this.formpanel_NotesIDFile);
		this.formname.push("panel_NotesIDFile");
		temp.push(this.formpanel_NotesIDFile);
		if (this.status != 0) {
			temp.push(histroyForm);
		}
		items.push(this.getTabpanel(temp));
		this.items = items;
		PageTemplates.superclass.initComponent.call(this);
		}
})