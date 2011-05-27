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
	save : function() {
		var oldNoticeId = this.dataId;
		if(!Ext.getCmp('page_notice').form.isValid()){
			Ext.MessageBox.alert("提示","页面中带红色波浪线的为必填项,请填写完整。谢谢您合作！");	
			return false;
		}
		var param = Ext.encode(getFormParam('page_notice'));

		Ext.Ajax.request({
			url : webContext + '/noticeaction_saveAlter.action',
			method : "POST",
			method : "POST",
			params : {
				info : param
			},
			success : function(response, options) {
				var responseArray = Ext.util.JSON.decode(response.responseText);
				var newNoticeId = responseArray.newNoticeId;
				var newNoticeName = responseArray.newNoticeName;
				var newNoticeType = responseArray.newNoticeType;
				Ext.Ajax.request({
					url : webContext + '/configWorkflow_findProcessByPram.action',
					params : {
						modleType : 'Notice_Sample',//
						processStatusType : '1'//
					},
					success : function(response, options) {
						var responseArray = Ext.util.JSON
								.decode(response.responseText);
						var vpid = responseArray.vpid;
						if(vpid!=""&&vpid!=undefined&&vpid.length>0){
							Ext.Ajax.request({
								url : webContext + '/noticeManagerWorkflow_apply.action',
								params : {
									dataId : newNoticeId,
									model : this.model,
									bzparam : "{dataId :'" + newNoticeId
											+ "',applyNum : '-----"
											+ "',applyName : '" + Ext.getCmp('NewNotice$title').getValue()
											+ "',oldDataId:'" + oldNoticeId
											+ "',applyId : '" + newNoticeId
											+ "',newNoticeName : '" + newNoticeName
											+ "',newNoticeType : '" + newNoticeType
											+ "',applyType: 'nproject',applyTypeName: '公告变更审批',customer:'',alterFlag:'变更'}",
									defname : vpid
								},
								success : function(response, options) {
									var meg = Ext.decode(response.responseText);
									if (meg.id != undefined) {
										Ext.Msg.alert("提示", "启动工作流成功", function() {
											window.location = webContext
													+ '/user/noticeChange/noticeChangeList.jsp';
										});
									} else {
										Ext.Msg.alert("提示", "启动工作流失败", function() {
											alert(meg.Exception);
										});
									}
								},
								failure : function(response, options) {
									Ext.MessageBox.alert("提示", "启动工作流失败");
								}
							}, this);
						}else{
							Ext.MessageBox.alert("未找到对应的流程，请查看是否配置!");
						}
					},
					failure : function(response, options) {
						Ext.MessageBox.alert("未找到对应的流程，请查看是否配置!");
					}
				}, this);
			},
			failure : function(response, options) {
				Ext.MessageBox.alert('提示', "变更失败");
			},
			scope : this
		});
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
			border : false,
			baseCls : 'x-plain',// 是否设置和背景色同步
			width : 820,
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

	getFormpage_notice : function() {
		var da = new DataAction();
		var data = null;
		// 判断是新增还是修改
		var biddata = "";

		var buttonUtil = new ButtonUtil();
		this.getFormButtons = buttonUtil.getButtonForPanel("page_notice", this);
		if (this.dataId != '0') {
			data = da.getPanelElementsForEdit("page_noticeChange_form",
					"page_notice", this.dataId);// 这是要随时变得
		} else {
			data = da.getPanelElementsForAdd("page_notice");

		}
		biddata = da.split(data);
		if (this.flag == "yes") {
			for (var i = 0; i < biddata.length; i++) {
				biddata[i].readOnly = true;
				biddata[i].disable = true;
				biddata[i].hideTrigger = true;
				biddata[i].emptyText = "";
			}
		}
		if (this.getFormButtons.length != 0) {
			this.formpage_notice = new Ext.form.FormPanel({
				id : 'page_notice',
				layout : 'table',
				height : 'auto',
				width : 820,
				frame : true,
				collapsible : true,
				defaults : {
					bodyStyle : 'padding:4px'
				},
				layoutConfig : {
					columns : 4
				},
				title : "公告",
				items : biddata,
				buttons : this.getFormButtons
			});
		} else {
			this.formpage_notice = new Ext.form.FormPanel({
				id : 'page_notice',
				layout : 'table',
				height : 'auto',
				width : 820,
				frame : true,
				collapsible : true,
				defaults : {
					bodyStyle : 'padding:4px'
				},
				layoutConfig : {
					columns : 4
				},
				title : "公告",
				items : biddata
			});
		}
		if (this.flag == "yes") {
			Ext.getCmp("page_notice").title = "公告 <font style='font-weight:lighter' color = red face='楷体_GB2312'>【已存在变更审批中的公告】</font>";
		}
		return this.formpage_notice;
	},
	items : this.items,
	/*
	 * clientvalidation 初始化
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
		this.model = "page_noticeChange_form";
		var buttonUtil = new ButtonUtil();
		this.mybuttons = new Array();
		var buttons = buttonUtil.getButtonForModel("page_noticeChange_form",
				this);
		if (this.flag == "yes") {
			for (var i = 0; i < buttons.length; i++) {
				if (buttons[i].text == "返回") {
					this.mybuttons.push(buttons[i]);
				}
			}
		} else {
			for (var i = 0; i < buttons.length; i++) {
				this.mybuttons.push(buttons[i]);
			}
		}
		if (this.mybuttons != "") {
			this.allbuttons = {
				layout : 'table',
				height : 'auto',
				width : 800,
				style : 'margin:4px 6px 4px 300px',
				align : 'center',
				defaults : {
					bodyStyle : 'padding:4px'
				},
				items : this.mybuttons
			};
		} else {
			this.allbuttons = {
				layout : 'table',
				height : 'auto',
				width : 800,
				style : 'margin:4px 6px 4px 300px',
				align : 'center',
				defaults : {
					bodyStyle : 'padding:4px'
				}
			};
		}

		this.getFormpage_notice();
		this.pa.push(this.formpage_notice);
		this.formname.push("page_notice");
		temp.push(this.formpage_notice);
		items = temp;
		items.push(this.allbuttons);
		this.items = items;
		this.on("save", this.save, this);
		//add by lee for checkout date in 20100409 begin
		Ext.getCmp("NewNotice$beginDate").on("change",function(obj, newvalue, oldvalue){
			if(Ext.getCmp("NewNotice$endDate").getValue()!=""&&Ext.getCmp("NewNotice$endDate").getValue()<newvalue){
			Ext.MessageBox.alert("提示","起始时间不能大于结束时间");
			Ext.getCmp("NewNotice$beginDate").reset();
			}},this);
		Ext.getCmp("NewNotice$endDate").on("change",function(obj, newvalue, oldvalue){
			if(Ext.getCmp("NewNotice$beginDate").getValue()!=""&&Ext.getCmp("NewNotice$beginDate").getValue()>newvalue){
			Ext.MessageBox.alert("提示","起始时间不能大于结束时间");
			Ext.getCmp("NewNotice$endDate").reset();
			}},this);
		//add by lee for checkout date in 20100409 end
		PageTemplates.superclass.initComponent.call(this);
	}
})