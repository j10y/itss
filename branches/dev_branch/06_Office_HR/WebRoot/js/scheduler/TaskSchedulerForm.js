TaskSchedulerForm = Ext.extend(Ext.Window, {
	formPanel : null,
	constructor : function(a) {
		Ext.applyIf(this, a);
		this.initUIComponents();
		TaskSchedulerForm.superclass.constructor.call(this, {
			layout : "fit",
			id : "TaskSchedulerFormWin",
			iconCls : "menu-notice",
			title : "设定详细信息",
			width : 500,
			height : 260,
			minWidth : 499,
			minHeight : 259,
			items : this.formPanel,
			maximizable : true,
			border : false,
			modal : true,
			plain : true,
			buttonAlign : "center",
			buttons : this.buttons
		});
	},
	initUIComponents : function() {
	
		this.formPanel = new Ext.FormPanel({
			url : __ctxPath + "/scheduler/saveTaskScheduler.do",
			layout : "form",
			id : "TaskSchedulerForm",
			bodyStyle : "padding:5px;",
			frame : false,
			width : 480,
			formId : "TaskSchedulerFormId",
			defaultType : "textfield",
			items : [ {
				name : "taskScheduler.id",
				id : "taskScheduler.id",
				xtype : "hidden",
				value : this.id == null ? "" : this.id
			},{
				name : "taskScheduler.cronTrigger",
				id : "taskScheduler.cronTrigger",
				xtype : "hidden"
			},{
				fieldLabel : "任务名称",
				width:300,
				name : "taskScheduler.taskName",
				id : "taskScheduler.taskName"
			},{
				fieldLabel : "描述信息",
				width:300,
				name : "taskScheduler.desc",
				id : "taskScheduler.desc"
			},{fieldLabel : "周期",
				id : "taskScheduler.cycle",
				xtype : "combo",
				triggerAction : "all",
				store : [
					"每年",
					"每半年",
					"每季度",
					"每月",
					"每天"
				],
				listeners : {
					select : function(d) {
					var cy= d.getValue();
						if(cy=="每月"||cy=="每天"){
							Ext.getCmp("taskScheduler.month").disable();
							Ext.getCmp("taskScheduler.month").setValue("");
							if(cy=="每天"){
								Ext.getCmp("taskScheduler.day").disable();
								Ext.getCmp("taskScheduler.day").setValue("");
							}else{
								Ext.getCmp("taskScheduler.day").enable();
							}
						}else{
							Ext.getCmp("taskScheduler.month").enable();
							Ext.getCmp("taskScheduler.day").enable();
						}
					}
				}
			},{fieldLabel : "月",
				id : "taskScheduler.month",
				xtype : "combo",
				triggerAction : "all",
				store : ["最后一个月","1","2","3","4","5","6","7","8","9","10","11","12"],
			},{fieldLabel : "日",
				id : "taskScheduler.day",
				xtype : "combo",
				triggerAction : "all",
				store : ["最后一天","1","2","3","4","5","6","7","8","9","10","11","12","13","14","15","16","17","18","19","20","21","22","23","24","25","26","27","28","29","30","31"],
			}, {
				fieldLabel : "时间",
				name : "taskScheduler.datetime",
				xtype : "datetimefield",
				format : "H:i:s",
				allowBlank : false,
				id : "taskScheduler.datetime"
			}]
		});
		if (this.id != null && this.id != "undefined") {
			this.formPanel.getForm().load(
					{
						deferredRender : false,
						url : __ctxPath + "/scheduler/getTaskScheduler.do?id="
								+ this.id,
						waitMsg : "正在载入数据...",
						success : function(b, c) {
							var f = Ext.util.JSON.decode(c.response.responseText).data;
							Ext.getCmp("taskScheduler.id").setValue(f.id);
							Ext.getCmp("taskScheduler.taskName").setValue(f.taskName);
							Ext.getCmp("taskScheduler.desc").setValue(f.desc);
							Ext.getCmp("taskScheduler.desc").disable();
							Ext.getCmp("taskScheduler.cronTrigger").setValue(f.cronTrigger);
						},
						failure : function(a, b) {
							Ext.ux.Toast.msg("编辑", "载入失败");
						}
					});
		}
		this.buttons = [ {
			text : "保存",
			iconCls : "btn-save",
			handler : function() {
				var a = Ext.getCmp("TaskSchedulerForm");
				if (a.getForm().isValid()) {
					a.getForm().submit({
						method : "post",
						waitMsg : "正在提交数据...",
						success : function(b, c) {
							Ext.ux.Toast.msg("操作信息", "成功保存信息！");
							Ext.getCmp("TaskSchedulerGrid").getStore().reload();
							Ext.getCmp("TaskSchedulerFormWin").close();
						},
						failure : function(b, c) {
							Ext.MessageBox.show({
								title : "操作信息",
								msg : "信息保存出错，请联系管理员！",
								buttons : Ext.MessageBox.OK,
								icon : "ext-mb-error"
							});
							Ext.getCmp("TaskSchedulerFormWin").close();
						}
					});
				}
			}
		}, {
			text : "取消",
			iconCls : "btn-cancel",
			handler : function() {
				Ext.getCmp("TaskSchedulerFormWin").close();
			}
		} ];
	}
});