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
	historyback : function() {

		history.back();
	},
	save : function() {
		alert("aa");
		var info = Ext.getCmp('Pages').getForm().getValues();
		var dataId = this.dataId;
		alert(Ext.encode(info));
		if (info.surveyName == "" || info.deployFlag == "" || info.name == "") {
			alert("缺少必须数据，请填写完全");
			return;
		};
		Ext.Ajax.request({
			// url : webContext + '/trainPlan_saveQuestOption.action',
			method : "POST",
			params : {
				dataId : dataId,
				id : info.id,
				questName : info.questName,
				survey : info.survey,
				questType : info.questType,
				content : info.content
			},
			success : function(response, options) {
				Ext.MessageBox.alert("保存成功");
				history.back();
				// window.location=webContext+"/user/train/train_questList.jsp";
			},
			failure : function(response, options) {
				Ext.MessageBox.alert("保存失败");
			},
			scope : this
		});
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
			baseCls : 'x-plain',// 是否设置和背景色同步
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
		this.Panel = new Ext.form.FormPanel({
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

	getFormpanel_trainAnswer : function() {
		var da = new DataAction();
		var data = null;
		// 判断是新增还是修改
		var biddata = "";

		var buttonUtil = new ButtonUtil();
		this.getFormButtons = buttonUtil.getButtonForPanel("panel_trainAnswer",
				this);
		if (this.dataId != '0') {
			data = da.getPanelElementsForEdit("page_train_answer_form",
					"panel_trainAnswer", this.dataId);// 这是要随时变得
			biddata = da.split(data);
		} else {
			data = da.getPanelElementsForAdd("panel_trainAnswer");
			biddata = da.split(data);
		}
		if (this.getFormButtons.length != 0) {
			this.formpanel_trainAnswer = new Ext.form.FormPanel({
				id : 'panel_trainAnswer',
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
				title : "问题答案设置",
				items : biddata,
				buttons : this.getFormButtons
			});
		} else {
			this.formpanel_trainAnswer = new Ext.form.FormPanel({
				id : 'panel_trainAnswer',
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
				title : "问题答案设置",
				items : biddata
			});
		}
		return this.formpanel_trainAnswer;
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
		this.model = "page_train_answer_form";
		var buttonUtil = new ButtonUtil();
		this.mybuttons = buttonUtil.getButtonForModel("page_train_answer_form",
				this);
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

		this.getFormpanel_trainAnswer();
		this.pa.push(this.formpanel_trainAnswer);
		this.formname.push("panel_trainAnswer");
		temp.push(this.formpanel_trainAnswer);
		items = temp;
		items.push(this.allbuttons);
		this.items = items;
		this.on("historyback", this.historyback, this);
		this.on("save", this.save, this);
		PageTemplates.superclass.initComponent.call(this);
	}
})