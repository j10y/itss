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
			baseCls : 'x-plain',// 是否设置和背景色同步
			width : 800,
			defaults : {
				autoHeight : true,
				bodyStyle : 'padding:2px'
			},
			items : tab,
			tbar : [new Ext.Toolbar.TextItem('<font color=red>提示：</font><font color=blue><font color=red>审批时要严格授权</font>，如果单据审核不通过，请点击页面下方的<font color=red>“驳回”</font>按钮</font>')]

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
				name : 'NotesIDFile$createDate',
				mapping : 'NotesIDFile$createDate'
			}]),
			title : "获取NotesID文件申请",
			items : [{
			xtype : 'fieldset',
		    title : '申请人信息',
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
				html : '申请编号:',
				cls : 'common-text',
				style : 'width:135;text-align:right'
			}, new Ext.form.TextField({
				fieldLabel : '申请编号',
				xtype : 'textfield',
				colspan : 0,
				rowspan : 0,
				id : 'AccountApplyMainTable$name',
				name : 'AccountApplyMainTable$name',
				style : '',
				width : 200,
				readOnly : true,
			    
				emptyText : '自动生成',
				value : '',
				allowBlank : true,
				validator : '',
				vtype : ''
			}), {
				html : '申请日期:',
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
				fieldLabel : '申请日期'
			}), {
				html : '申请人:',
				cls : 'common-text',
				style : 'width:135;text-align:right'
			}, new Ext.form.ComboBox({
				xtype : 'combo',
				hiddenName : 'AccountApplyMainTable$applyUser',
				id : 'AccountApplyMainTable$applyUserCombo',
				width : 200,
				style : '',
				fieldLabel : '申请人',
				hideTrigger:true,
				colspan : 0,
				rowspan : 0,
				readOnly : true,
				displayField : 'userName',
				valueField : 'id',
				emptyText : '请选择...',
				allowBlank : true,
				name : 'AccountApplyMainTable$applyUser',
				minChars : 50,
             	store : new Ext.data.JsonStore({
					url : webContext
							+ '/extjs/comboDataAction?clazz=com.zsgj.info.framework.security.entity.UserInfo',
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
				html : '<font color=red>*</font>联系电话:',
				cls : 'common-text',
				style : 'width:135;text-align:right'
			}, new Ext.form.TextField({
				fieldLabel : '申请人联系电话',
				xtype : 'textfield',
				colspan : 0,
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
				html : '员工编号:',
				cls : 'common-text',
				style : 'width:135;text-align:right'
			}, new Ext.form.TextField({
				fieldLabel : '员工编号',
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
				html : '成本中心号:',
				cls : 'common-text',
				style : 'width:135;text-align:right'
			}, new Ext.form.TextField({
				fieldLabel : '成本中心号',
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
				html : '用户类别/员工组:',
				cls : 'common-text',
				style : 'width:135;text-align:right'
			},new Ext.form.ComboBox({
				xtype : 'combo',
				hiddenName : 'sUserInfos$userType',
				id : 'sUserInfos$userTypeCombo',
				width : 200,
				style : '',
				fieldLabel : '用户类型',
				colspan : 0,
				rowspan : 0,
				lazyRender : true,
				readOnly : true,
				hideTrigger:true,
				displayField : 'name',
				valueField : 'id',
				emptyText : '请选择...',
				allowBlank : true,
				typeAhead : true,
				name : 'sUserInfos$userType',
				triggerAction : 'all',
				minChars : 50,
				queryDelay : 700,
				store : new Ext.data.JsonStore({
					url : webContext
							+ '/extjs/comboDataAction?clazz=com.zsgj.info.framework.security.entity.UserType',
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
		    title : '申请帐号信息',
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
		    title : '<font color=red>重新获取ID文件(必选)</font>',
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
				html : '<center></center>',
				cls : 'common-text',
				colspan : 4,
				rowspan : 0,
				style : 'border:1px dotted #b0acac;width:600;text-align:left;margin:0px 0px 0px 75px'
			},
			{
				html : '需要获取ID文件的邮件帐号名称:',
				cls : 'common-text',
				style : 'width:135;text-align:right'
			}, new Ext.form.TextField({
				xtype : 'textfield',
				id : 'NotesIDFile$fileName',
				name : 'NotesIDFile$fileName',
				fieldLabel : 'id文件',
				colspan : 0,
				rowspan : 0,
				style : '',
				width : 200,
				readOnly : true,
				value : '',
				allowBlank : true,
				validator : '',
				vtype : ''
			}),  {
				html : '接受此ID文件的内网邮箱:',
				cls : 'common-text',
				style : 'width:135;text-align:right'
			}, 
			new Ext.form.TextField({
				xtype : 'textfield',
				id : 'NotesIDFile$dcMail',
				fieldLabel : 'DC邮箱',
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
				
			}),
				{
				html : '也可填入外部邮箱名来接收ID(可选):',
				cls : 'common-text',
				style : 'width:135;text-align:right'
			}, new Ext.form.TextField({
				fieldLabel : '外部邮箱',
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
				html : '初始密码:',
				cls : 'common-text',
				style : 'width:135;text-align:right'
			}, new Ext.form.ComboBox({
				xtype : 'combo',
				id : 'itil_ac_PersonFormalAccount$password',
				colspan : 0,
				rowspan : 0,
				name : 'itil_ac_PersonFormalAccount$password',
				width : 200,
			    style : 'color:red',
			    displayField : 'text',
			    valueField : 'value',
				allowBlank : true,
				validator : '',
				fieldLabel : '备注说明',
				emptyText : '请选择...',
				mode: 'local',
				store:new Ext.data.SimpleStore({
			             fields: ['value', 'text'],
			             data : [['password123','password123'],['000000','000000']]
			        })

			}),
			{
				html : 'ID文件所有者员工编号:',
				cls : 'common-text',
				style : 'width:135;text-align:right'
			}, new Ext.form.TextField({
				xtype : 'textfield',
				id : 'employeeCode',
				colspan : 0,
				rowspan : 0,
				name : 'employeeCode',
				width : 200,
				allowBlank : true,
				validator : '',
				fieldLabel : '备注说明'

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
                        boxLabel: '<font size=2px>使用初始邮箱ID文件登陆邮箱申请，请选中此项</font>'
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
				fieldLabel : '创建人'
			}), new Ext.form.Hidden({
				xtype : 'hidden',
				id : 'AccountApplyMainTable$createDate',
				colspan : 0,
				rowspan : 0,
				name : 'AccountApplyMainTable$createDate',
				width : 200,
				style : '',
				value : '',
				fieldLabel : '创建日期'
			}), new Ext.form.Hidden({
				xtype : 'hidden',
				id : 'AccountApplyMainTable$modifyUser',
				colspan : 0,
				rowspan : 0,
				name : 'AccountApplyMainTable$modifyUser',
				width : 200,
				style : '',
				value : '',
				fieldLabel : '最后修改人'
			}), new Ext.form.Hidden({
				xtype : 'hidden',
				id : 'AccountApplyMainTable$modifyDate',
				colspan : 0,
				rowspan : 0,
				name : 'AccountApplyMainTable$modifyDate',
				width : 200,
				style : '',
				value : '',
				fieldLabel : '最后修改日期'
			}), new Ext.form.Hidden({
				xtype : 'hidden',
				id : 'AccountApplyMainTable$id',
				colspan : 0,
				rowspan : 0,
				name : 'AccountApplyMainTable$id',
				width : 200,
				style : '',
				value : '',
				fieldLabel : '自动编号'
			}), new Ext.form.Hidden({
				xtype : 'hidden',
				id : 'AccountApplyMainTable$oldApply',
				colspan : 0,
				rowspan : 0,
				name : 'AccountApplyMainTable$oldApply',
				width : 200,
				style : '',
				value : '',
				fieldLabel : '变更前申请'
			}), new Ext.form.Hidden({
				xtype : 'hidden',
				id : 'AccountApplyMainTable$processType',
				colspan : 0,
				rowspan : 0,
				name : 'AccountApplyMainTable$processType',
				width : 200,
				style : '',
				value : '',
				fieldLabel : '流程类型'
			}), new Ext.form.Hidden({
				xtype : 'hidden',
				id : 'AccountApplyMainTable$status',
				colspan : 0,
				rowspan : 0,
				name : 'AccountApplyMainTable$status',
				width : 200,
				style : '',
				value : '',
				fieldLabel : '状态'
			}), new Ext.form.Hidden({
				xtype : 'hidden',
				id : 'AccountApplyMainTable$deleteFlag',
				colspan : 0,
				rowspan : 0,
				name : 'AccountApplyMainTable$deleteFlag',
				width : 200,
				style : '',
				value : '',
				fieldLabel : '删除状态'
			}), new Ext.form.Hidden({
				xtype : 'hidden',
				id : 'AccountApplyMainTable$serviceItem',
				colspan : 0,
				rowspan : 0,
				name : 'AccountApplyMainTable$serviceItem',
				width : 200,
				style : '',
				value : '',
				fieldLabel : '所属服务'
			}), new Ext.form.Hidden({
				xtype : 'hidden',
				id : 'NotesIDFile$id',
				colspan : 0,
				rowspan : 0,
				name : 'NotesIDFile$id',
				width : 200,
				style : '',
				value : '',
				fieldLabel : '自动编号'
			}), new Ext.form.Hidden({
				xtype : 'hidden',
				id : 'NotesIDFile$createDate',
				colspan : 0,
				rowspan : 0,
				name : 'NotesIDFile$createDate',
				width : 200,
				style : '',
				value : '',
				fieldLabel : '生效日期'
			})]}
			,{
			xtype : 'fieldset',
		    title : '附件信息',
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
				html : '附件:',
				cls : 'common-text',
				style : 'width:85;text-align:right'
			},
//				{
//				fieldLabel : '附件',
//				xtype : 'button',
//				text : '<font color=red>上 传</font>',
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
//										'com.zsgj.info.appframework.metadata.entity.SystemFile');
//					} else {
//						var ud = new UpDownLoadFile();
//						ud
//								.getUpDownLoadFileSu(attachmentFlag, '7887',
//										'com.zsgj.info.appframework.metadata.entity.SystemFile');
//					}
//				}
//			},
			{xtype:'panel',layout:'table',width:250,layoutConfig:{columns:4},
			fieldLabel:'附件地址',defaults:{baseCls:'margin : 10 15 12 15'},
			items:[
			{
			fieldLabel:'附件',
			xtype:'button',
			text:'<font color=red>上 传</font>',
			width:50,
			scope:this,
			handler:function(){
			var attachmentFlag = Ext.getCmp('NotesIDFile$attachment').getValue();
			if(attachmentFlag==''||attachmentFlag=='null'){
			attachmentFlag = Date.parse(new Date());
			Ext.getCmp('NotesIDFile$attachment').setValue(attachmentFlag);
			var ud=new UpDownLoadFile();
			//String hiddenId = String.valueOf(System.currentTimeMillis());
			ud.getUpDownLoadFileSu(attachmentFlag,'7887','com.zsgj.info.appframework.metadata.entity.SystemFile','NotesIDFile010attachment');
			}else{
			var ud=new UpDownLoadFile();
			ud.getUpDownLoadFileSu(attachmentFlag,'7887','com.zsgj.info.appframework.metadata.entity.SystemFile','NotesIDFile010attachment');
			}}
			},
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
			buttons : [{
				text : '保存并提交',
				id : 'submit',
				iconCls : 'submit',
				handler : function() {
					var dataId=Ext.getCmp('AccountApplyMainTable$id').getValue();
					var attachmentFlag=Ext.getCmp('NotesIDFile$attachment').getValue();
					var password=Ext.getCmp('itil_ac_PersonFormalAccount$password').getValue();
					if(password==''||password=='null'){
						Ext.MessageBox.alert("提示","请选择初始密码");
						return false;
					}
					if(attachmentFlag==''||attachmentFlag=='null'){
						Ext.MessageBox.alert("提示","请上传ID文件附件");
						return false;
					}
					
					Ext.Ajax.request({
						url : webContext+ '/accountAction_saveIDFileAttachment.action',
						params : {
							attachmentFlag : attachmentFlag,
							dataId:dataId,
							password:password
						},
                        success : function(response, options) {
							window.parent.auditContentWin.specialAudit();
						},
						failure : function(response, options) {
							Ext.MessageBox.alert("保存失败");
						}
					}, this);
				}
			},
			{
				text : '驳回',
				id:'back',
				iconCls : 'back',
				handler : function() {
					window.parent.auditContentWin.specialNoAudit();
			 }}
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
				success : function(action, form) {
				
				  Ext.getCmp("AccountApplyMainTable$applyUserCombo")
							           .initComponent();
							           
							           var idFile= Ext.getCmp("NotesIDFile$fileName").getValue();
							            if(idFile!=null&&idFile!=''){
							           	Ext.getCmp("idFile").expand(true);
							           Ext.getDom('file').checked=true;
							           }
							            							           
							           //add by liuying at 20100323 for 增加显示id文件所属者的员工编号 start
							            var name= Ext.getCmp("NotesIDFile$fileName").getValue();
							            Ext.Ajax.request({
											url : webContext
													+ '/accountAction_getEmployeeCode.action',
											params : {
												userName : name
											},
											success : function(response, options) {
												var responseArray = Ext.util.JSON
														.decode(response.responseText);
												if (responseArray.success) {
													Ext.getCmp('employeeCode').setValue(responseArray.code);
												} else {
			
												}
											},
											failure : function(response, options) {
			
											}
										}, this);
										////add by liuying at 20100323 for 增加显示id文件所属者的员工编号 end
				}
			});
			
			
		}
		return this.formpanel_NotesIDFile;
	},
	items : this.items,
	/*
	 * clientvalidation 初始化
	 */
	initComponent : function() {
		var histroyForm = new HistroyForm({
			reqId : this.dataId,
			reqClass : "com.zsgj.itil.require.entity.AccountApplyMainTable"
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