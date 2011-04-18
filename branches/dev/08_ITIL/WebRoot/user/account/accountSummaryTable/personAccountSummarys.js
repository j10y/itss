PageTemplates = Ext.extend(Ext.Panel, {
	id : "PageTemplates",
	layout : 'table',
	height : 'auto',
	align : 'center',
	foredit : true,
	width : 1050,
	frame : true,
	oldtelnum : null,//add by liuying at 20100517
	oldmobnum : null,//add by liuying at 20100517
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
			width : 900,
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
		this.formpanel_accountSummary_list = new Ext.form.FormPanel({
			id : 'panel_accountSummary_list',
			layout : 'column',
			height : 'auto',
			width : 900,
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
			}, {
				name : 'mail$mailValue',
				mapping : 'mail$mailValue'
			}, {
				name : 'www$wwwAccountValue',
				mapping : 'www$wwwAccountValue'
			}, {
				name : 'bi$referSalary',
				mapping : 'bi$referSalary'
			}, {
				name : 'eb$telephone',
				mapping : 'eb$telephone'
			},  {
				name : 'erp$erpUserName',
				mapping : 'erp$erpUserName'
			},  {
				name : 'mail$mailServer',
				mapping : 'mail$mailServer'
			}, {
				name : 'vpn$endDate',
				mapping : 'vpn$endDate'
			}, 
			{
				name : 'domain$id',
				mapping : 'domain$id'
			},
			{
				name : 'domain$accountState',
				mapping : 'domain$accountState'
			},
				{
				name : 'domain$rightsDesc',
				mapping : 'domain$rightsDesc'
			},
				{
				name : 'domain$remarkDesc',
				mapping : 'domain$remarkDesc'
			},
				{
				name : 'mail$id',
				mapping : 'mail$id'
			},
				{
				name : 'mail$accountState',
				mapping : 'mail$accountState'
			},
				{
				name : 'domain$remarkDesc',
				mapping : 'domain$remarkDesc'
			},
				{
				name : 'www$id',
				mapping : 'www$id'
			},
				{
				name : 'www$accountState',
				mapping : 'www$accountState'
			},
				{
				name : 'www$remarkDesc',
				mapping : 'www$remarkDesc'
			},
			
			{
				name : 'msn$id',
				mapping : 'msn$id'
			},
			{
				name : 'msn$accountState',
				mapping : 'msn$accountState'
			},
				{
				name : 'msn$rightsDesc',
				mapping : 'msn$rightsDesc'
			},
				{
				name : 'msn$remarkDesc',
				mapping : 'msn$remarkDesc'
			},
			
			{
				name : 'erp$id',
				mapping : 'erp$id'
			},
			{
				name : 'erp$accountState',
				mapping : 'erp$accountState'
			},
				{
				name : 'erp$name',
				mapping : 'erp$name'
			},
				{
				name : 'erp$remarkDesc',
				mapping : 'erp$remarkDesc'
			},
			
			
			{
				name : 'bi$id',
				mapping : 'bi$id'
			},
			{
				name : 'bi$accountState',
				mapping : 'bi$accountState'
			},
				{
				name : 'bi$remarkDesc',
				mapping : 'bi$remarkDesc'
			},
			
			
			
			{
				name : 'vpn$id',
				mapping : 'vpn$id'
			},
			{
				name : 'vpn$cardNumber',
				mapping : 'vpn$cardNumber'
			},
			
			{
				name : 'vpn$accountState',
				mapping : 'vpn$accountState'
			},
				{
				name : 'vpn$rightsDesc',
				mapping : 'vpn$rightsDesc'
			},
				
			{
				name : 'eb$id',
				mapping : 'eb$id'
			},
				{
				name : 'eb$accountState',
				mapping : 'eb$accountState'
			},
			{
				name : 'eb$rightsDesc',
				mapping : 'eb$rightsDesc'
			},
				{
				name : 'eb$remarkDesc',
				mapping : 'eb$remarkDesc'
			},
			
			
			{
				name : 'scm$id',
				mapping : 'scm$id'
			},
				{
				name : 'scm$accountState',
				mapping : 'scm$accountState'
			},
			{
				name : 'scm$rightsDesc',
				mapping : 'scm$rightsDesc'
			},
				{
				name : 'scm$remarkDesc',
				mapping : 'scm$remarkDesc'
			},
			
			
			{
				name : 'el$id',
				mapping : 'el$id'
			},
				{
				name : 'el$accountState',
				mapping : 'el$accountState'
			},
			{
				name : 'el$rightsDesc',
				mapping : 'el$rightsDesc'
			},
				{
				name : 'eb$remarkDesc',
				mapping : 'eb$remarkDesc'
			},
			
			{
				name : 'pushMail$id',
				mapping : 'pushMail$id'
			},
				{
				name : 'pushMail$accountState',
				mapping : 'pushMail$accountState'
			},
			{
				name : 'pushMail$rightsDesc',
				mapping : 'pushMail$rightsDesc'
			},
				{
				name : 'pushMail$remarkDesc',
				mapping : 'pushMail$remarkDesc'
			},
			{
				name : 'telephone$id',
				mapping : 'telephone$id'
			},
				{
				name : 'telephone$accountState',
				mapping : 'telephone$accountState'
			},
			{
				name : 'telephone$yearMoney',
				mapping : 'telephone$yearMoney'
			},
			{
				name : 'telephone$voip',
				mapping : 'telephone$voip'
			},
				{
				name : 'telephone$telephoneNumber',
				mapping : 'telephone$telephoneNumber'
			},
			{
				name : 'mobileTelephone$id',
				mapping : 'mobileTelephone$id'
			},
				{
				name : 'mobileTelephone$accountState',
				mapping : 'mobileTelephone$accountState'
			},
			{
				name : 'mobileTelephone$rightsDesc',
				mapping : 'mobileTelephone$rightsDesc'
			},
				{
				name : 'mobileTelephone$mobileTelephone',
				mapping : 'mobileTelephone$mobileTelephone'
			},
			{
				name : 'account$comment',
				mapping : 'account$comment'
			}
			
			
			]),
			title : "<center>Ա���ʺ���Ϣ��Ҫ��</center>",
			items : [
            {
			xtype : 'fieldset',
		    title : 'Ա��������Ϣ',
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
			}), {
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
			}), {
				html : '�ʼ�������:',
				cls : 'common-text',
				style : 'width:135;text-align:right'
			}, new Ext.form.ComboBox({
				xtype : 'combo',
				hiddenName : 'sUserInfos$mailServer',
				id : 'sUserInfos$mailServerCombo',
				width : 200,
				style : '',
				fieldLabel : '�ʼ�������',
				colspan : 0,
				rowspan : 0,
				lazyRender : true,
				displayField : 'name',
				valueField : 'id',
				emptyText : '��ѡ��...',
				allowBlank : true,
				
				name : 'sUserInfos$mailServer',
				triggerAction : 'all',
				minChars : 50,
				queryDelay : 700,
				store : new Ext.data.JsonStore({
					url : webContext
							+ '/extjs/comboDataAction?clazz=com.digitalchina.info.framework.security.entity.MailServer',
					fields : ['id', 'name'],
					listeners : {
						beforeload : function(store, opt) {
							if (opt.params['sUserInfos$mailServer'] == undefined) {
								opt.params['name'] = Ext
										.getCmp('sUserInfos$mailServerCombo').defaultParam;
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
							id : Ext.getCmp('sUserInfos$mailServerCombo')
									.getValue(),
							start : 0
						},
						callback : function(r, options, success) {
							Ext
									.getCmp('sUserInfos$mailServerCombo')
									.setValue(Ext
											.getCmp('sUserInfos$mailServerCombo')
											.getValue());
						}
					});
				}
			}), {
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
			})]},
			{
			xtype : 'fieldset',
		    title : '�ʺ���Ϣ',
			layout : 'table',
			layoutConfig : {
				columns : 4 
			},
		    anchor : '100%',
		    animCollapse:true,
			collapsible :true,
		    style : 'border:1px dotted #b0acac;margin:15px 0px 0px 0px',
			autoHeight : true,
		   items:[
			{
				columnWidth : 0.25,
				border : true,
				html : '<b><font size=2px> �ʺ�����</font></b>',
				height:25,
				cls : 'common-text',
				style : 'width:150;text-align:center;margin:0px 0px 0px 30px'
			},
			{
				columnWidth : 0.25,
				border : true,
				html : '<b><font size=2px>״̬</font></b>',
				height:25,
				cls : 'common-text',
				style : 'width:197;text-align:center;'
			},
			{
				columnWidth : 0.25,
				border : true,
				html : '<b><font size=2px>����Ҫ��</font></b>',
				height:25,
				cls : 'common-text',
				style : 'width:197;text-align:center'
			},
			{
				columnWidth : 0.25,
				border : true,
				html : '<b><font size=2px>��ע˵��</font></b>',
				height:25,
				cls : 'common-text',
				style : 'width:197;text-align:center'
			},
			{
				columnWidth : 0.25,
				border : true,
				html : '���ʺ�',
				cls : 'common-text',
				height:30,
				style : 'width:150;text-align:center;border:1px dotted #b0acac;margin:0px 0px 0px 30px'
			},
			{
			columnWidth : 0.25,
			height:30,
			items:[
			new Ext.form.Hidden({
				xtype : 'hidden',
				id : 'domain$id',
				colspan : 0,
				rowspan : 0,
				name : 'domain$id',
				width : 200,
				style : '',
				value : '',
				fieldLabel : '�Զ����'
			}),
			new Ext.form.TextField({
				fieldLabel : '״̬',
				xtype : 'textfield',
				colspan : 0,
				rowspan : 0,
				emptyText : '0',
				id : 'domain$accountState',
				name : 'domain$accountState',
				style : '',
				width : 200,
				value : '',
				allowBlank : true,
				validator : '',
				vtype : ''
			}) 
			]
			},
			
		{
			columnWidth : 0.25,
				height:30,
			items:[
			new Ext.form.TextField({
				fieldLabel : '����',
				xtype : 'textfield',
				colspan : 0,
			
				rowspan : 0,
				id : 'domain$rightsDesc',
				name : 'domain$rightsDesc',
				style : '',
				width : 200,
				value : '',
				allowBlank : true,
				validator : '',
				vtype : ''
			}) 
			]
			},
			{
			columnWidth : 0.25,
			height:30,
			items:[
			new Ext.form.TextField({
				fieldLabel : '����',
				xtype : 'textfield',
				colspan : 0,
				rowspan : 0,
				id : 'domain$remarkDesc',
				name : 'domain$remarkDesc',
				style : '',
				width : 200,
				value : '',
				allowBlank : true,
				validator : '',
				vtype : ''
			}) 
			]
			},
			{
				columnWidth : 0.25,
				border : true,
				html : '�ʼ��ʺ�',
				cls : 'common-text',
				height:30,
				style : 'width:150;text-align:center;border:1px dotted #b0acac;margin:0px 0px 0px 30px'
			},
			{
			columnWidth : 0.25,
				height:30,
			items:[
			new Ext.form.Hidden({
				xtype : 'hidden',
				id : 'mail$id',
				colspan : 0,
				rowspan : 0,
				name : 'mail$id',
				width : 200,
				style : '',
				value : '',
				fieldLabel : '�Զ����'
			}),
			new Ext.form.TextField({
				fieldLabel : '����',
				xtype : 'textfield',
				colspan : 0,
				rowspan : 0,
				emptyText : '0',
				id : 'mail$accountState',
				name : 'mail$accountState',
				style : '',
				width : 200,
				value : '',
				allowBlank : true,
				validator : '',
				vtype : ''
			}) 
			]
			},
			
		{
			columnWidth : 0.25,
			layout:"table",
			height:30,
			layoutConfig : {
				columns : 2
			},
			items:[
			
		{
				html : '��������:',
				cls : 'common-text',
				style : 'width:65;text-align:left'
			}, new Ext.form.ComboBox({
				xtype : 'combo',
				hiddenName : 'mail$mailValue',
				id : 'mail$mailValueCombo',
				width : 125,
				style : '',
				fieldLabel : '��������',
				colspan : 0,
				rowspan : 0,
				lazyRender : true,
				displayField : 'volume',
				valueField : 'id',
				emptyText : '��ѡ��...',
				allowBlank : true,
				
				name : 'mail$mailValue',
				triggerAction : 'all',
				minChars : 50,
				queryDelay : 700,
				store : new Ext.data.JsonStore({
					url : webContext
							+ '/extjs/comboDataAction?clazz=com.digitalchina.itil.config.extlist.entity.MailVolume',
					fields : ['id', 'volume'],
					listeners : {
						beforeload : function(store, opt) {
							if (opt.params['mail$mailValue'] == undefined) {
								opt.params['volume'] = Ext
										.getCmp('mail$mailValueCombo').defaultParam;
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
									.getCmp('mail$mailValueCombo')
									.getValue(),
							start : 0
						},
						callback : function(r, options, success) {
							Ext
									.getCmp('mail$mailValueCombo')
									.setValue(Ext
											.getCmp('mail$mailValueCombo')
											.getValue());
						}
					});
				}
			})
			]
			},
			{
			columnWidth : 0.25,
			height:30,
			items:[
			new Ext.form.TextField({
				fieldLabel : '����',
				xtype : 'textfield',
				colspan : 0,
				rowspan : 0,
				id : 'mail$remakDesc',
				name : 'mail$remakDesc',
				style : '',
				width : 200,
				value : '',
				allowBlank : true,
				validator : '',
				vtype : ''
			}) 
			]
			},
			{
				columnWidth : 0.25,
				border : true,
				html : 'WWW�ʺ�',
				cls : 'common-text',
				height:30,
				style : 'width:150;text-align:center;border:1px dotted #b0acac;margin:0px 0px 0px 30px'
			},
			{
			columnWidth : 0.25,
				height:30,
			items:[
			new Ext.form.Hidden({
				xtype : 'hidden',
				id : 'www$id',
				colspan : 0,
				rowspan : 0,
				name : 'www$id',
				width : 200,
				style : '',
				value : '',
				fieldLabel : '�Զ����'
			}),
			new Ext.form.TextField({
				fieldLabel : '����',
				xtype : 'textfield',
				colspan : 0,
				rowspan : 0,
				emptyText : '0',
				id : 'www$accountState',
				name : 'www$accountState',
				style : '',
				width : 200,
				value : '',
				allowBlank : true,
				validator : '',
				vtype : ''
			}) 
			]
			},
			
		{
			columnWidth : 0.25,
			layout:"table",
			height:30,
			layoutConfig : {
				columns : 2
			},
			items:[
			
		{
				html : '���:',
				cls : 'common-text',
				style : 'width:65;text-align:left'
			}, new Ext.form.ComboBox({
				xtype : 'combo',
				hiddenName : 'www$wwwAccountValue',
				id : 'www$wwwAccountValueCombo',
				width : 125,
				style : '',
				fieldLabel : 'www�ʺŶ��',
				colspan : 0,
				rowspan : 0,
				lazyRender : true,
				displayField : 'type',
				valueField : 'id',
				emptyText : '��ѡ��...',
				allowBlank : true,
				
				name : 'www$wwwAccountValue',
				triggerAction : 'all',
				minChars : 50,
				queryDelay : 700,
				store : new Ext.data.JsonStore({
					url : webContext
							+ '/extjs/comboDataAction?clazz=com.digitalchina.itil.config.extlist.entity.WWWScanType',
					fields : ['id', 'type'],
					listeners : {
						beforeload : function(store, opt) {
							if (opt.params['www$wwwAccountValue'] == undefined) {
								opt.params['type'] = Ext
										.getCmp('www$wwwAccountValueCombo').defaultParam;
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
									.getCmp('www$wwwAccountValueCombo')
									.getValue(),
							start : 0
						},
						callback : function(r, options, success) {
							Ext
									.getCmp('www$wwwAccountValueCombo')
									.setValue(Ext
											.getCmp('www$wwwAccountValueCombo')
											.getValue());
						}
					});
				}
			})
			]
			},
			{
			columnWidth : 0.25,
			height:30,
			items:[
			new Ext.form.TextField({
				fieldLabel : '����',
				xtype : 'textfield',
				colspan : 0,
				rowspan : 0,
				id : 'www$remakDesc',
				name : 'www$remakDesc',
				style : '',
				width : 200,
				value : '',
				allowBlank : true,
				validator : '',
				vtype : ''
			}) 
			]
			},
			{
				columnWidth : 0.25,
				border : true,
				html : 'MSN�ʺ�',
				cls : 'common-text',
				height:30,
				style : 'width:150;text-align:center;border:1px dotted #b0acac;margin:0px 0px 0px 30px'
			},
			{
			columnWidth : 0.25,
				height:30,
			items:[
			new Ext.form.Hidden({
				xtype : 'hidden',
				id : 'msn$id',
				colspan : 0,
				rowspan : 0,
				name : 'msn$id',
				width : 200,
				style : '',
				value : '',
				fieldLabel : '�Զ����'
			}),
			new Ext.form.TextField({
				fieldLabel : '����',
				xtype : 'textfield',
				colspan : 0,
				rowspan : 0,
				emptyText : '0',
				id : 'msn$accountState',
				name : 'msn$accountState',
				style : '',
				width : 200,
				value : '',
				allowBlank : true,
				validator : '',
				vtype : ''
			}) 
			]
			},
			
		{
			columnWidth : 0.25,
				height:30,
			items:[
			new Ext.form.TextField({
				fieldLabel : '����',
				xtype : 'textfield',
				colspan : 0,
			
				rowspan : 0,
				id : 'msn$rightsDesc',
				name : 'msn$rightsDesc',
				style : '',
				width : 200,
				value : '',
				allowBlank : true,
				validator : '',
				vtype : ''
			}) 
			]
			},
			{
			columnWidth : 0.25,
			height:30,
			items:[
			new Ext.form.TextField({
				fieldLabel : '����',
				xtype : 'textfield',
				colspan : 0,
				rowspan : 0,
				id : 'msn$remakDesc',
				name : 'msn$remakDesc',
				style : '',
				width : 200,
				value : '',
				allowBlank : true,
				validator : '',
				vtype : ''
			}) 
			]
			},
			{
				columnWidth : 0.25,
				border : true,
				html : 'Զ�̽����ʺ�',
				cls : 'common-text',
				height:50,
				style : 'width:150;text-align:center;border:1px dotted #b0acac;margin:0px 0px 0px 30px'
			},
			{
			columnWidth : 0.25,
				height:50,
			items:[
			new Ext.form.Hidden({
				xtype : 'hidden',
				id : 'vpn$id',
				colspan : 0,
				rowspan : 0,
				name : 'vpn$id',
				width : 200,
				style : '',
				value : '',
				fieldLabel : '�Զ����'
			}),
			new Ext.form.TextField({
				fieldLabel : '����',
				xtype : 'textfield',
				colspan : 0,
				rowspan : 0,
				emptyText : '0',
				id : 'vpn$accountState',
				name : 'vpn$accountState',
				style : '',
				width : 200,
				value : '',
				allowBlank : true,
				validator : '',
				vtype : ''
			}) 
			]
			},
			
		  {
			columnWidth : 0.25,
			height:50,
			items:[
			new Ext.form.TextField({
				fieldLabel : '����',
				xtype : 'textfield',
				colspan : 0,
			
				rowspan : 0,
				id : 'vpn$rightsDesc',
				name : 'vpn$rightsDesc',
				style : '',
				width : 200,
				value : '',
				allowBlank : true,
				validator : '',
				vtype : ''
			}) 
			]
			},
			{
			columnWidth : 0.25,
			layout:"table",
			height:50,
			layoutConfig : {
				columns : 2
			},
			items:[
			
		      {
					html : '���ƿ���:',
					cls : 'common-text',
					style : 'width:65;text-align:right'
				}, new Ext.form.TextField({
					fieldLabel : '<font color=red>*</font>���ƿ���',
					xtype : 'textfield',
					colspan : 0,
					rowspan : 0,
					id : 'vpn$cardNumber',
					name : 'vpn$cardNumber',
					style : '',
					width : 125,
					value : '',
					allowBlank : true,
					validator : '',
					vtype : ''
			}),
			
			{
				html : '��������:',
				cls : 'common-text',
				style : 'width:65;text-align:right'
			}, new Ext.form.DateField({
				xtype : 'datefield',
				id : 'vpn$endDate',
				colspan : 0,
				rowspan : 0,
				name : 'vpn$endDate',
				width : 125,
				style : '',
				value : '',
				allowBlank : true,
				validator : '',
				format : 'Y-m-d',
				fieldLabel : '��������'
			})
			
			]
			},
			{
				columnWidth : 0.25,
				border : true,
				html : 'ERP�ʺ�',
				cls : 'common-text',
				height:30,
				style : 'width:150;text-align:center;border:1px dotted #b0acac;margin:0px 0px 0px 30px'
			},
			{
			columnWidth : 0.25,
				height:30,
			items:[
			new Ext.form.Hidden({
				xtype : 'hidden',
				id : 'erp$id',
				colspan : 0,
				rowspan : 0,
				name : 'erp$id',
				width : 200,
				style : '',
				value : '',
				fieldLabel : '�Զ����'
			}),
			new Ext.form.TextField({
				fieldLabel : '����',
				xtype : 'textfield',
				colspan : 0,
				rowspan : 0,
				emptyText : '0',
				id : 'erp$accountState',
				name : 'erp$accountState',
				style : '',
				width : 200,
				value : '',
				allowBlank : true,
				validator : '',
				vtype : ''
			}) 
			]
			},
			
		{
			columnWidth : 0.25,
				height:30,
			items:[
			new Ext.form.TextField({
				fieldLabel : '����',
				xtype : 'textfield',
				colspan : 0,
			
				rowspan : 0,
				id : 'erp$rightsDesc',
				name : 'erp$rightsDesc',
				style : '',
				width : 200,
				value : '',
				allowBlank : true,
				validator : '',
				vtype : ''
			}) 
			]
			},
			{
			columnWidth : 0.25,
			height:30,
			items:[
			new Ext.form.TextField({
				fieldLabel : '����',
				xtype : 'textfield',
				colspan : 0,
				rowspan : 0,
				id : 'erp$remakDesc',
				name : 'erp$remakDesc',
				style : '',
				width : 200,
				value : '',
				allowBlank : true,
				validator : '',
				vtype : ''
			}) 
			]
			},
			{
				columnWidth : 0.25,
				border : true,
				html : 'BI�ʺ�',
				cls : 'common-text',
				height:30,
				style : 'width:150;text-align:center;border:1px dotted #b0acac;margin:0px 0px 0px 30px'
			},
			{
			columnWidth : 0.25,
				height:30,
			items:[
			new Ext.form.Hidden({
				xtype : 'hidden',
				id : 'bi$id',
				colspan : 0,
				rowspan : 0,
				name : 'bi$id',
				width : 200,
				style : '',
				value : '',
				fieldLabel : '�Զ����'
			}),
			new Ext.form.TextField({
				fieldLabel : '����',
				xtype : 'textfield',
				colspan : 0,
				rowspan : 0,
				emptyText : '0',
				id : 'bi$accountState',
				name : 'bi$accountState',
				style : '',
				width : 200,
				value : '',
				allowBlank : true,
				validator : '',
				vtype : ''
			}) 
			]
			},
			
		{
			columnWidth : 0.25,
			layout:"table",
			height:30,
			layoutConfig : {
				columns : 2
			},
			items:[
			
		{
				html : '�Ƿ��漰н��:',
				cls : 'common-text',
				style : 'width:45;text-align:left'
			}, new Ext.form.ComboBox({
				xtype : 'combo',
				id : 'bi$referSalaryCombo',
				style : 'float:left;align:left',
				mode : 'local',
				hiddenName : 'bi$referSalary',
				colspan : 0,
				rowspan : 0,
				triggerAction : 'all',
				
				forceSelection : true,
				allowBlank : true,
				store : new Ext.data.SimpleStore({
					fields : ['id', 'name'],
					data : [['1', '��'], ['0', '��']]
				}),
				emptyText : '��ѡ��...',
				valueField : 'id',
				value : '',
				displayField : 'name',
				name : 'bi$referSalary',
				width : 125,
				fieldLabel : '�Ƿ��漰н��',
				listeners : {
					'expand' : function(combo) {
						combo.reset();
					}
				}
			})
			]
			},
			{
			columnWidth : 0.25,
			height:30,
			items:[
			new Ext.form.TextField({
				fieldLabel : '����',
				xtype : 'textfield',
				colspan : 0,
				rowspan : 0,
				id : 'bi$remakDesc',
				name : 'bi$remakDesc',
				style : '',
				width : 200,
				value : '',
				allowBlank : true,
				validator : '',
				vtype : ''
			}) 
			]
			},
			{
				columnWidth : 0.25,
				border : true,
				html : 'E-Bridge�ʺ�',
				cls : 'common-text',
				height:30,
				style : 'width:150;text-align:center;border:1px dotted #b0acac;margin:0px 0px 0px 30px'
			},
			{
			columnWidth : 0.25,
				height:30,
			items:[
			new Ext.form.Hidden({
				xtype : 'hidden',
				id : 'eb$id',
				colspan : 0,
				rowspan : 0,
				name : 'eb$id',
				width : 200,
				style : '',
				value : '',
				fieldLabel : '�Զ����'
			}),
			new Ext.form.TextField({
				fieldLabel : '����',
				xtype : 'textfield',
				colspan : 0,
				rowspan : 0,
				emptyText : '0',
				id : 'eb$accountState',
				name : 'eb$accountState',
				style : '',
				width : 200,
				value : '',
				allowBlank : true,
				validator : '',
				vtype : ''
			}) 
			]
			},
			
		{
			columnWidth : 0.25,
			layout:"table",
			height:30,
			layoutConfig : {
				columns : 2
			},
			items:[
			
		{
				html : '�ֻ�����:',
				cls : 'common-text',
				style : 'width:65;text-align:right'
			}, new Ext.form.NumberField({
				fieldLabel : '�ֻ�����',
				xtype : 'textfield',
				colspan : 0,
				rowspan : 0,
				id : 'eb$telephone',
				name : 'eb$telephone',
				style : '',
				width : 125,
				value : '',
				allowBlank : true,
				validator : '',
				vtype : ''
			})
			]
			},
			{
			columnWidth : 0.25,
			height:30,
			items:[
			new Ext.form.TextField({
				fieldLabel : '����',
				xtype : 'textfield',
				colspan : 0,
				rowspan : 0,
				id : 'eb$remakDesc',
				name : 'eb$remakDesc',
				style : '',
				width : 200,
				value : '',
				allowBlank : true,
				validator : '',
				vtype : ''
			}) 
			]
			},
			{
				columnWidth : 0.25,
				border : true,
				html : 'E-logistics�ʺ�',
				cls : 'common-text',
				height:30,
				style : 'width:150;text-align:center;border:1px dotted #b0acac;margin:0px 0px 0px 30px'
			},
			{
			columnWidth : 0.25,
				height:30,
			items:[
			new Ext.form.Hidden({
				xtype : 'hidden',
				id : 'el$id',
				colspan : 0,
				rowspan : 0,
				name : 'el$id',
				width : 200,
				style : '',
				value : '',
				fieldLabel : '�Զ����'
			}),
			new Ext.form.TextField({
				fieldLabel : '����',
				xtype : 'textfield',
				colspan : 0,
				rowspan : 0,
				emptyText : '0',
				id : 'el$accountState',
				name : 'el$accountState',
				style : '',
				width : 200,
				value : '',
				allowBlank : true,
				validator : '',
				vtype : ''
			}) 
			]
			},
			
		{
			columnWidth : 0.25,
				height:30,
			items:[
			new Ext.form.TextField({
				fieldLabel : '����',
				xtype : 'textfield',
				colspan : 0,
			
				rowspan : 0,
				id : 'el$rightsDesc',
				name : 'el$rightsDesc',
				style : '',
				width : 200,
				value : '',
				allowBlank : true,
				validator : '',
				vtype : ''
			}) 
			]
			},
			{
			columnWidth : 0.25,
			height:30,
			items:[
			new Ext.form.TextField({
				fieldLabel : '����',
				xtype : 'textfield',
				colspan : 0,
				rowspan : 0,
				id : 'el$remarkDesc',
				name : 'el$remarkDesc',
				style : '',
				width : 200,
				value : '',
				allowBlank : true,
				validator : '',
				vtype : ''
			}) 
			]
			},
			{
				columnWidth : 0.25,
				border : true,
				html : 'SCM�ʺ�',
				cls : 'common-text',
				height:30,
				style : 'width:150;text-align:center;border:1px dotted #b0acac;margin:0px 0px 0px 30px'
			},
			{
			columnWidth : 0.25,
				height:30,
			items:[
			new Ext.form.Hidden({
				xtype : 'hidden',
				id : 'scm$id',
				colspan : 0,
				rowspan : 0,
				name : 'scm$id',
				width : 200,
				style : '',
				value : '',
				fieldLabel : '�Զ����'
			}),
			new Ext.form.TextField({
				fieldLabel : '����',
				xtype : 'textfield',
				colspan : 0,
				rowspan : 0,
				emptyText : '0',
				id : 'scm$accountState',
				name : 'scm$accountState',
				style : '',
				width : 200,
				value : '',
				allowBlank : true,
				validator : '',
				vtype : ''
			}) 
			]
			},
			
		{
			columnWidth : 0.25,
				height:30,
			items:[
			new Ext.form.TextField({
				fieldLabel : '����',
				xtype : 'textfield',
				colspan : 0,
			
				rowspan : 0,
				id : 'scm$rightsDesc',
				name : 'scm$rightsDesc',
				style : '',
				width : 200,
				value : '',
				allowBlank : true,
				validator : '',
				vtype : ''
			}) 
			]
			},
			{
			columnWidth : 0.25,
			height:30,
			items:[
			new Ext.form.TextField({
				fieldLabel : '����',
				xtype : 'textfield',
				colspan : 0,
				rowspan : 0,
				id : 'scm$remarkDesc',
				name : 'scm$remarkDesc',
				style : '',
				width : 200,
				value : '',
				allowBlank : true,
				validator : '',
				vtype : ''
			}) 
			]
			},
			{
				columnWidth : 0.25,
				border : true,
				html : 'Traveler�ʺ�',//�޸�pushmail�ʺ�ΪTraveler�ʺ�
				cls : 'common-text',
				height:30,
				style : 'width:150;text-align:center;border:1px dotted #b0acac;margin:0px 0px 0px 30px'
			},
			{
			columnWidth : 0.25,
				height:30,
			items:[
			new Ext.form.Hidden({
				xtype : 'hidden',
				id : 'pushMail$id',
				colspan : 0,
				rowspan : 0,
				name : 'pushMail$id',
				width : 200,
				style : '',
				value : '',
				fieldLabel : '�Զ����'
			}),
			new Ext.form.TextField({
				fieldLabel : '����',
				xtype : 'textfield',
				colspan : 0,
				rowspan : 0,
				emptyText : '0',
				id : 'pushMail$accountState',
				name : 'pushMail$accountState',
				style : '',
				width : 200,
				value : '',
				allowBlank : true,
				validator : '',
				vtype : ''
			}) 
			]
			},
			
		{
			columnWidth : 0.25,
				height:30,
			items:[
			new Ext.form.TextField({
				fieldLabel : '����',
				xtype : 'textfield',
				colspan : 0,
			
				rowspan : 0,
				id : 'pushMail$rightsDesc',
				name : 'pushMail$rightsDesc',
				style : '',
				width : 200,
				value : '',
				allowBlank : true,
				validator : '',
				vtype : ''
			}) 
			]
			},
			{
			columnWidth : 0.25,
			height:30,
			items:[
			new Ext.form.TextField({
				fieldLabel : '����',
				xtype : 'textfield',
				colspan : 0,
				rowspan : 0,
				id : 'pushMail$remarkDesc',
				name : 'pushMail$remarkDesc',
				style : '',
				width : 200,
				value : '',
				allowBlank : true,
				validator : '',
				vtype : ''
			}) 
			]
			},
				{
				columnWidth : 0.25,
				border : true,
				html : '����',
				cls : 'common-text',
				height:50,
				style : 'width:150;text-align:center;border:1px dotted #b0acac;margin:0px 0px 0px 30px'
			},
			{
			columnWidth : 0.25,
				height:50,
			items:[
			new Ext.form.Hidden({
				xtype : 'hidden',
				id : 'telephone$id',
				colspan : 0,
				rowspan : 0,
				name : 'telephone$id',
				width : 200,
				style : '',
				value : '',
				fieldLabel : '�Զ����'
			}),
			new Ext.form.TextField({
				fieldLabel : '����',
				xtype : 'textfield',
				colspan : 0,
				rowspan : 0,
				emptyText : '0',
				id : 'telephone$accountState',
				name : 'telephone$accountState',
				style : '',
				width : 200,
				value : '',
				allowBlank : true,
				readOnly:true,
				validator : '',
				vtype : ''
			}) 
			]
			},
			
		{
			columnWidth : 0.25,
			
			layout:"table",
			height:50,
			layoutConfig : {
				columns : 2
			},
			items:[
			{
				html : '������:',
				cls : 'common-text',
				style : 'width:65;text-align:left'
			}, new Ext.form.NumberField({
				fieldLabel : '������',
				xtype : 'textfield',
				colspan : 0,
				rowspan : 0,
				id : 'telephone$yearMoney',
				name : 'telephone$yearMoney',
				style : '',
				width : 125,
				value : '',
				allowBlank : true,
				validator : '',
				vtype : ''
			})
			]
			},
			{
			columnWidth : 0.25,
		    layout:"table",
			height:50,
			layoutConfig : {
				columns : 2
			},
			items:[
			{
				html : '��������:',
				cls : 'common-text',
				style : 'width:65;text-align:left'
			}, new Ext.form.TextField({
				fieldLabel : '��������',
				xtype : 'textfield',
				colspan : 0,
				rowspan : 0,
				id : 'telephone$telephoneNumber',
				name : 'telephone$telephoneNumber',
				style : '',
				width : 125,
				value : '',
				allowBlank : true,
				validator : '',
				vtype : ''
			}),
			{
				html : 'VOIP����:',
				cls : 'common-text',
				style : 'width:65;text-align:left'
			}, new Ext.form.TextField({
				fieldLabel : '��������',
				xtype : 'textfield',
				colspan : 0,
				rowspan : 0,
				id : 'telephone$voip',
				name : 'telephone$voip',
				style : '',
				width : 125,
				value : '',
				allowBlank : true,
				validator : '',
				vtype : ''
			})
			]
			},
				{
				columnWidth : 0.25,
				border : true,
				html : '�ֻ�',
				cls : 'common-text',
				height:30,
				style : 'width:150;text-align:center;border:1px dotted #b0acac;margin:0px 0px 0px 30px'
			},
			{
			columnWidth : 0.25,
				height:30,
			items:[
			new Ext.form.Hidden({
				xtype : 'hidden',
				id : 'mobileTelephone$id',
				colspan : 0,
				rowspan : 0,
				name : 'mobileTelephone$id',
				width : 200,
				style : '',
				value : '',
				fieldLabel : '�Զ����'
			}),
			new Ext.form.TextField({
				fieldLabel : '����',
				xtype : 'textfield',
				colspan : 0,
				rowspan : 0,
				emptyText : '0',
				id : 'mobileTelephone$accountState',
				name : 'mobileTelephone$accountState',
				style : '',
				width : 200,
				value : '',
				allowBlank : true,
				validator : '',
				readOnly:true,
				vtype : ''
			}) 
			]
			},
			
		{
			columnWidth : 0.25,
				height:30,
			items:[
			new Ext.form.TextField({
				fieldLabel : '����',
				xtype : 'textfield',
				colspan : 0,
			    rowspan : 0,
				id : 'mobileTelephone$rightsDesc',
				name : 'mobileTelephone$rightsDesc',
				style : '',
				width : 200,
				value : '',
				allowBlank : true,
				validator : '',
				vtype : ''
			}) 
			]
			},
			{
			columnWidth : 0.25,
		    layout:"table",
			height:30,
			layoutConfig : {
				columns : 2
			},
			items:[
			{
				html : '�ֻ�����:',
				cls : 'common-text',
				style : 'width:65;text-align:left'
			}, new Ext.form.NumberField({
				fieldLabel : '��������',
				xtype : 'textfield',
				colspan : 0,
				rowspan : 0,
				id : 'mobileTelephone$mobileTelephone',
				name : 'mobileTelephone$mobileTelephone',
				style : '',
				width : 125,
				value : '',
				allowBlank : true,
				validator : '',
				vtype : ''
			})
			]
			}
			
	]	
			},
			{
			xtype : 'fieldset',
		    title : 'Ա����Ҫ���޸ļ�¼',
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
					width : 750,
					emptyText : '�������Ҫ����ԭ��'
					
				})
			]
			}
			
			
			],
			buttons : [{
				text : '����',
				iconCls : 'save',
				id:'save',
				handler : function() {
				//add by liuying at 20100517 for �޸ĸ�������������ֻ�����ʱ������֤��Ϣ start
					var newtelnum=Ext.getCmp("telephone$telephoneNumber").getValue();
					var newmobnum=Ext.getCmp("mobileTelephone$mobileTelephone").getValue();
					if(oldtelnum==''&&oldmobnum==''){
						Ext.MessageBox.confirm("��ʾ","��ȷ��Ҫ�޸ĸ�Ա���ʺ���Ϣ",function(btn){
								if(btn=="yes"){
							var info = Ext.encode(getFormParam('panel_accountSummary_list'));
							var comment=Ext.getCmp('account$comment').getValue();
							
							Ext.Ajax.request({
								url : webContext
										+ '/accountAction_savePersonSummaryData.action',
								params : {
									info : info,
									comment:comment,
									userid:userid
								},
		                         success : function(response, options) {
									var responseArray = Ext.util.JSON
											.decode(response.responseText);
									var curId = responseArray.id;
							       Ext.MessageBox.alert("��ʾ","����ɹ�");
									},
								failure : function(response, options) {
									Ext.MessageBox.alert("����ʧ��");
					   
								}
							}, this);
									
								}else{
								
								}
							});
					}else{
						if(newtelnum==oldtelnum&&newmobnum==oldmobnum){
							Ext.MessageBox.confirm("��ʾ","��ȷ��Ҫ�޸ĸ�Ա���ʺ���Ϣ",function(btn){
								if(btn=="yes"){
							var info = Ext.encode(getFormParam('panel_accountSummary_list'));
							var comment=Ext.getCmp('account$comment').getValue();
							Ext.Ajax.request({
								url : webContext
										+ '/accountAction_savePersonSummaryData.action',
								params : {
									info : info,
									comment:comment,
									userid:userid
								},
		                         success : function(response, options) {
									var responseArray = Ext.util.JSON
											.decode(response.responseText);
									var curId = responseArray.id;
							       Ext.MessageBox.alert("��ʾ","����ɹ�");
									},
								failure : function(response, options) {
									Ext.MessageBox.alert("����ʧ��");
					   
								}
							}, this);
									
								}else{
								
								}
							});
						}else{
							if(oldtelnum!=''&&(newtelnum==''||newtelnum==null)){
								Ext.MessageBox.alert("��ʾ","����/�ֻ����벻�ܱ��޸�Ϊ�գ�");
								return false;
							}
							if(oldmobnum!=''&&(newmobnum==''||newmobnum==null)){
								Ext.MessageBox.alert("��ʾ","����/�ֻ����벻�ܱ��޸�Ϊ�գ�");
								return false;
							}
							if(oldtelnum!=''&&newtelnum!=''&&oldtelnum!=newtelnum){
								Ext.Ajax.request({
									url : webContext
											+ '/accountAction_getTelAccount.action',
									params : {
										num : newtelnum
									},
			                         success : function(response, options) {
										var responseArray = Ext.util.JSON
												.decode(response.responseText);
										if(responseArray.success){
											Ext.MessageBox.confirm("��ʾ","��ȷ��Ҫ�޸ĸ�Ա���ʺ���Ϣ",function(btn){
													if(btn=="yes"){
												var info = Ext.encode(getFormParam('panel_accountSummary_list'));
												var comment=Ext.getCmp('account$comment').getValue();
												
												Ext.Ajax.request({
													url : webContext
															+ '/accountAction_savePersonSummaryData.action',
													params : {
														info : info,
														comment:comment,
														userid:userid
													},
							                         success : function(response, options) {
														var responseArray = Ext.util.JSON
																.decode(response.responseText);
														var curId = responseArray.id;
												       Ext.MessageBox.alert("��ʾ","����ɹ�");
														},
													failure : function(response, options) {
														Ext.MessageBox.alert("����ʧ��");
										   
													}
												}, this);
														
													}else{
													
													}
												});
										}else{
											Ext.MessageBox.alert("��ʾ","����"+newtelnum+"�Ѿ��������˺��´��ڣ��������޸�Ϊ�˺��룡");
											return false;
										}
								     },  
									failure : function(response, options) {
										Ext.MessageBox.alert("����ʧ��");
						   
									}
								}, this);
							
							
						}else{
							Ext.MessageBox.confirm("��ʾ","��ȷ��Ҫ�޸ĸ�Ա���ʺ���Ϣ",function(btn){
								if(btn=="yes"){
									var info = Ext.encode(getFormParam('panel_accountSummary_list'));
									var comment=Ext.getCmp('account$comment').getValue();
									
									Ext.Ajax.request({
										url : webContext
												+ '/accountAction_savePersonSummaryData.action',
										params : {
											info : info,
											comment:comment,
											userid:userid
										},
				                         success : function(response, options) {
											var responseArray = Ext.util.JSON
													.decode(response.responseText);
											var curId = responseArray.id;
									       Ext.MessageBox.alert("��ʾ","����ɹ�");
											},
										failure : function(response, options) {
											Ext.MessageBox.alert("����ʧ��");
							   
										}
									}, this);
									
								}else{
								}
							}
							);
						}
					}
					 }           
				}
				//add by liuying at 20100517 for �޸ĸ�������������ֻ�����ʱ������֤��Ϣ end
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
						    + '/accountAction_initPersonSummaryData.action',
				                params : {
							    userInfo : userid
				                },
			 timeout : 30,
				success : function(action, form) {
					Ext.getCmp("sUserInfos$departmentCombo").initComponent();
					Ext.getCmp("sUserInfos$sameMailDeptCombo").initComponent();
					Ext.getCmp("sUserInfos$workSpaceCombo").initComponent();
					Ext.getCmp("sUserInfos$mailServerCombo").initComponent();
					Ext.getCmp("sUserInfos$userTypeCombo").initComponent();
//					Ext.getCmp("sUserInfos$personnelScopeCombo").initComponent();
					//add by liuying at 20100517 for �޸ĸ�������������ֻ�����ʱ������֤��Ϣ start
					oldtelnum=Ext.getCmp("telephone$telephoneNumber").getValue();
					oldmobnum=Ext.getCmp("mobileTelephone$mobileTelephone").getValue();
					//add by liuying at 20100517 for �޸ĸ�������������ֻ�����ʱ������֤��Ϣ end
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
		
		
		this.getFormpanel_accountSummary_list();

		this.pa.push(this.formpanel_accountSummary_list);
		this.formname.push("panel_accountSummary_list");
		temp.push(this.formpanel_accountSummary_list);
		items = temp;
		
		this.items = items;
		PageTemplates.superclass.initComponent.call(this);
	}
})