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
	noticeConfirm : function() {
		audit();
	},
	alertNext : function() {
		addMarkUserToNext();
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
			baseCls : 'x-plain',// �Ƿ����úͱ���ɫͬ��
			width : 'auto',
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
		if(!Ext.getCmp('page_notice').form.isValid()){
			Ext.MessageBox.alert("��ʾ","ҳ���д���ɫ�����ߵ�Ϊ������,����д������лл��������");	
			return false;
		}
		var param = Ext.encode(getFormParam('page_notice'));
		Ext.Ajax.request({
			url : webContext + '/noticeaction_save.action',
			method : "POST",
			params : {
				info : param
			},
			success : function(response, options) {
				Ext.MessageBox.alert("����ɹ�");
			},
			failure : function(response, options) {
				Ext.MessageBox.alert("����ʧ��");
			},
			scope : this
		});
	},
	alertAdd : function() {
		addMarkUser();
	},
	getFormpage_notice : function() {
		var da = new DataAction();
		var data = null;
		// �ж������������޸�
		var biddata = "";
		var buttonUtil = new ButtonUtil();
		this.getFormButtons = buttonUtil.getButtonForPanel("page_notice", this);
		data = da.getPanelElementsForEdit("page_notice_form_audit",
				"page_notice", this.dataId);// ����Ҫ��ʱ���
		biddata = da.split(data);
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
				//title : "����",//remove by lee for ������ in 20100428
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
				//title : "����",//remove by lee for ������ in 20100428
				items : biddata
			});
		}
		return this.formpage_notice;
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
		this.model = "page_notice_form_audit";
		var buttonUtil = new ButtonUtil();
		this.mybuttons = buttonUtil.getButtonForModel("page_notice_form_audit",
				this);
		this.mybuttons.push({
			xtype : 'button',
			text : '����',
			scope : this,
			iconCls : 'save',
			id : 'saveButton',
			handler : this.save

		});
		if (this.mybuttons != "") {
			this.allbuttons = {
				layout : 'table',
				height : 'auto',
				width : 820,
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
				width : 820,
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
		this.on("noticeConfirm", this.noticeConfirm, this);
		this.on("alertAdd", this.alertAdd, this);
		this.on("alertNext", this.alertNext, this);
		//add by lee for checkout date in 20100409 begin
		Ext.getCmp("NewNotice$beginDate").on("change",function(obj, newvalue, oldvalue){
			if(Ext.getCmp("NewNotice$endDate").getValue()!=""&&Ext.getCmp("NewNotice$endDate").getValue()<newvalue){
			Ext.MessageBox.alert("��ʾ","��ʼʱ�䲻�ܴ��ڽ���ʱ��");
			Ext.getCmp("NewNotice$beginDate").reset();
			}},this);
		Ext.getCmp("NewNotice$endDate").on("change",function(obj, newvalue, oldvalue){
			if(Ext.getCmp("NewNotice$beginDate").getValue()!=""&&Ext.getCmp("NewNotice$beginDate").getValue()>newvalue){
			Ext.MessageBox.alert("��ʾ","��ʼʱ�䲻�ܴ��ڽ���ʱ��");
			Ext.getCmp("NewNotice$endDate").reset();
			}},this);
		//add by lee for checkout date in 20100409 end
		PageTemplates.superclass.initComponent.call(this);
	}
})