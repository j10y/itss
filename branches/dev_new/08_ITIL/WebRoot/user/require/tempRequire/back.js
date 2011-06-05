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
			baseCls : 'x-plain',// 是否设置和背景色同步
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
		//add by lee for 更换为统一的验证规则结果后台配置必填项 in 20091103 begin
		if(!Ext.getCmp('panel_tempRequire').form.isValid()){
			Ext.MessageBox.alert("提示","页面中带红色波浪线的为必填项,请填写完整。谢谢您合作！");	
			return false;
		}
		//add by lee for 更换为统一的验证规则结果后台配置必填项 in 20091103 end
		Ext.getCmp('saveButton').disable();
		Ext.getCmp('workFlowButton').disable();
		var name = Ext.getCmp('SpecialRequirement$name').getValue();
		if(name==""||name==null){
			Ext.MessageBox.alert("提示","请填写需求名称！");
			Ext.getCmp('saveButton').enable();
			Ext.getCmp('workFlowButton').enable();
			return;
		}
		var formParam = Ext.encode(getFormParam('panel_tempRequire',false));
		Ext.Ajax.request({
			url : webContext + '/requireAction_saveApplyDraft.action',
			params : {
				pagePanel　:　'panel_tempRequire',
				info : formParam
			},
			success : function(response, options) {
				Ext.MessageBox.alert("提示","保存成功！",function(){
				Ext.getCmp('saveButton').enable();
				Ext.getCmp('workFlowButton').enable();
				});
			},
			failure : function(response, options) {
				Ext.MessageBox.alert("保存失败");
				Ext.getCmp('saveButton').enable();
				Ext.getCmp('workFlowButton').enable();
			}
		}, this);
	},
	getFormpanel_typicalRequire_Input : function() {
		var da = new DataAction();
		var data = null;
		// 判断是新增还是修改
		var biddata = "";

		if (this.dataId != '0') {
			data = da.getPanelElementsForEdit("page_tempRequireBack","panel_tempRequire", this.dataId);// 这是要随时变得
			biddata = da.split(data);
		} 
		var curbuttons = new Array();
		var saveButton = new Ext.Button({
			text : '保存',
			id : 'saveButton',
			pressed : true,
			iconCls : 'save',
			scope : this,
			handler : this.save
		});
		var submitButton = new Ext.Button({
			text : '审批',
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
													Ext.Msg.alert("提示", "审批提交失败，异常信息如下，请您让管理员检查流程！", function() {
														Ext.Msg.alert("提示",jsonobject.Exception);
													});
												} else {
													Ext.Msg.alert("提示", "审批提交成功", function() {
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
												Ext.MessageBox.alert("提示信息：", "提交失败",function(){
														Ext.getCmp("modifySubmit").enable();									
												});
											},
											scope : this,
											clientValidation : false
									});
							},
							text : '确认',
							scope : this
						}
						, {
							xtype : 'button',
							handler : function() {
								auditFormWin.close();
							},
							text : '取消',
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
							html : '审批意见:',
							colspan : 5,
							width : 'auto',
							height : 25,
							style : 'font-size:9pt;text-align:left'
						}, new Ext.form.TextArea({
							fieldLabel : '&nbsp;审批意见',
							height : 150,
							width : 430,
							name : 'comment',
							colspan : 7
						}), {
							html : '重新提交:',
							style : 'font-size:9pt;width:120;text-align:right;margin:15 0 20 20'
						}, new Ext.form.Radio({
							xtype : 'radio',
							id:"reSubmit",
							width : 20,
							name : 'result',
							checked : true,
							inputValue : 'Y',
							fieldLabel : '重新提交'
						}), {
							html : '取消申请:',
							style : 'font-size:9pt;width:120;text-align:right;margin:15 0 20 20'
						}, new Ext.form.Radio({
							xtype : 'radio',
							width : 20,
							name : 'result',
							checked : false,
							inputValue : 'N',
							fieldLabel : '取消申请'
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
				backCreatorForm("打回处理",taskId);
			
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
				title : "需求申请",
				items : biddata,
				tbar : curbuttons
			});
		return this.formpanel_typicalRequire_Input;
	},
	items : this.items,
	buttons : this.buttons,
	/*
	 * clientvalidation 初始化
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