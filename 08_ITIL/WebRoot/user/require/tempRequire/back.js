PageTemplates = Ext.extend(Ext.Panel, {
	id : "PageTemplates",
	layout : 'table',
	height : 'auto',
	align : 'center',
	foredit : true,
	width : 1050,
	frame : true,
	buttonAlign : 'center',
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
			//minTabWidth:100,
			//resizeTabs:true,
			title : tabTitle,
			deferredRender : false,
			frame : true,
			plain : true,
			border : false,
			//tabPosition:'bottom',
			baseCls : 'x-plain',// �Ƿ����úͱ���ɫͬ��
			width : 900,
			//bodyBorder : true,
			defaults : {
				autoHeight : true,
				bodyStyle : 'padding:2px'
			},
			items : tab
		});
		return this.tabPanel;

	},
	getPanel : function(appa, panelTitle) {
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
	save : function() {
		//add by lee for ����Ϊͳһ����֤��������̨���ñ����� in 20091103 begin
		if(!Ext.getCmp('panel_tempRequire').form.isValid()){
			Ext.MessageBox.alert("��ʾ","ҳ���д���ɫ�����ߵ�Ϊ������,����д������лл��������");	
			return false;
		}
		//add by lee for ����Ϊͳһ����֤��������̨���ñ����� in 20091103 end
		Ext.getCmp('saveButton').disable();
		Ext.getCmp('workFlowButton').disable();
		var name = Ext.getCmp('SpecialRequirement$name').getValue();
		if(name==""||name==null){
			Ext.MessageBox.alert("��ʾ","����д�������ƣ�");
			Ext.getCmp('saveButton').enable();
			Ext.getCmp('workFlowButton').enable();
			return;
		}
		var formParam = Ext.encode(getFormParam('panel_tempRequire',false));
		Ext.Ajax.request({
			url : webContext + '/requireAction_saveApplyDraft.action',
			params : {
				pagePanel��:��'panel_tempRequire',
				info : formParam
			},
			success : function(response, options) {
				Ext.MessageBox.alert("��ʾ","����ɹ���",function(){
				Ext.getCmp('saveButton').enable();
				Ext.getCmp('workFlowButton').enable();
				});
			},
			failure : function(response, options) {
				Ext.MessageBox.alert("����ʧ��");
				Ext.getCmp('saveButton').enable();
				Ext.getCmp('workFlowButton').enable();
			}
		}, this);
	},
	getFormpanel_typicalRequire_Input : function() {
		var da = new DataAction();
		var data = null;
		// �ж������������޸�
		var biddata = "";

		if (this.dataId != '0') {
			data = da.getPanelElementsForEdit("page_tempRequireBack","panel_tempRequire", this.dataId);// ����Ҫ��ʱ���
			biddata = da.split(data);
		} 
		var curbuttons = new Array();
		var saveButton = new Ext.Button({
			text : '����',
			id : 'saveButton',
			pressed : true,
			iconCls : 'save',
			scope : this,
			handler : this.save
		});
		var submitButton = new Ext.Button({
			text : '����',
			id : 'workFlowButton',
			pressed : true,
			iconCls : 'submit',
			scope : this,
			handler : function(){
				function showAuditForm(auditForm, width, height, title) {
					var auditFormWin = new Ext.Window({
						title : title,
						modal : true,
						height : height,
						width : width,
						resizable : true,
						autoScroll : true,
						viewConfig : {
							autoFill : true,
							forceFit : true
						},
						layout : 'fit',
						buttonAlign : 'center',
						buttons : [{
							xtype : 'button',
							id:'modifySubmit',
							handler : function() {
										Ext.getCmp("modifySubmit").disable();
										auditForm.getForm().submit({
											method : "get",
											params : getFormParam(auditForm.getId()),
											url : webContext + '/extjs/workflow?method=audit&done',
											success : function(action, form) {
												var jsonobject = Ext.util.JSON.decode(form.response.responseText);
												if (jsonobject.Exception != undefined) {
													Ext.Msg.alert("��ʾ", "�����ύʧ�ܣ��쳣��Ϣ���£������ù���Ա������̣�", function() {
														Ext.Msg.alert("��ʾ",jsonobject.Exception);
													});
												} else {
													Ext.Msg.alert("��ʾ", "�����ύ�ɹ�", function() {
														var ciModifyTasks = window.parent.Ext.getCmp('porlet-ciModifyTasks');
														if (ciModifyTasks) {
															ciModifyTasks.getStore().reload();
														}
														var curpath = window.top.location.pathname;
														if(curpath=='/infoAdmin/workflow/configPage/auditFromMail.jsp'){
															window.top.close();
														}
														auditFormWin.close();
														window.parent.Ext.getCmp("auditContentWindow").close();
													});
												}
											},
											failure : function(response, options) {
												Ext.MessageBox.alert("��ʾ��Ϣ��", "�ύʧ��",function(){
														Ext.getCmp("modifySubmit").enable();									
												});
											},
											scope : this,
											clientValidation : false
									});
							},
							text : 'ȷ��',
							scope : this
						}
						, {
							xtype : 'button',
							handler : function() {
								auditFormWin.close();
							},
							text : 'ȡ��',
							scope : this
						}
						],
						items : [auditForm]
					});
					auditFormWin.show();
				}
				var backCreatorForm = function(title, taskId, isCancel) {
					this.form = new Ext.FormPanel({
						layout : 'table',
						layoutConfig : {
							columns : 5
						},
						frame : true,
						bodyStyle : 'padding:5px 5px 5px 10px',
						width : 'auto',
						height : 'auto',
						isFormField : true,
						items : [{
							html : '�������:',
							colspan : 5,
							width : 'auto',
							height : 25,
							style : 'font-size:9pt;text-align:left'
						}, new Ext.form.TextArea({
							fieldLabel : '&nbsp;�������',
							height : 150,
							width : 430,
							name : 'comment',
							colspan : 7
						}), {
							html : '�����ύ:',
							style : 'font-size:9pt;width:120;text-align:right;margin:15 0 20 20'
						}, new Ext.form.Radio({
							xtype : 'radio',
							id:"reSubmit",
							width : 20,
							name : 'result',
							checked : true,
							inputValue : 'Y',
							fieldLabel : '�����ύ'
						}), {
							html : 'ȡ������:',
							style : 'font-size:9pt;width:120;text-align:right;margin:15 0 20 20'
						}, new Ext.form.Radio({
							xtype : 'radio',
							width : 20,
							name : 'result',
							checked : false,
							inputValue : 'N',
							fieldLabel : 'ȡ������'
						}), {
							html : '',
							style : 'width:60'
						},	{
									xtype : 'hidden',
									name : 'done',
									value : 'yes'
								}, {
									xtype : 'hidden',
									name : 'id',
									value : taskId
								}]
					});
					showAuditForm(this.form, 475, 300, title);
				}
				backCreatorForm("��ش���",taskId);
			
			}
		});
		curbuttons.push(saveButton);
		curbuttons.push(submitButton);
			this.formpanel_typicalRequire_Input = new Ext.form.FormPanel({
				id : 'panel_tempRequire',
				layout : 'table',
				height : 'auto',
				width : 800,
				frame : true,
				collapsible : true,
				defaults : {
					bodyStyle : 'padding:4px'
				},
				layoutConfig : {
					columns : 4
				},
				title : "��������",
				items : biddata,
				tbar : curbuttons
			});
		return this.formpanel_typicalRequire_Input;
	},
	items : this.items,
	buttons : this.buttons,
	/*
	 * clientvalidation ��ʼ��
	 */
	initComponent : function() {

		var histroyForm = new HistroyForm({
			reqId : this.dataId,
			reqClass : "com.zsgj.itil.require.entity.SpecialRequirement"
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
		this.model = "page_tempRequireBack";
	
		this.getFormpanel_typicalRequire_Input();
		this.pa.push(this.formpanel_typicalRequire_Input);
		this.formname.push("panel_tempRequire");
		temp.push(this.formpanel_typicalRequire_Input);
		temp.push(histroyForm);
		items.push(this.getTabpanel(temp));
		this.items = items;
		PageTemplates.superclass.initComponent.call(this);
	}
})