PageTemplates = Ext.extend(Ext.Panel, {
	id : "PageTemplates",
	layout : 'table',
	height : 800,
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
	
	workSubmit : function() {
		Ext.getCmp('submitButton').disable();//��ť����
		var dataId = this.dataId;
		if(Ext.isEmpty(Ext.getCmp('NewNotice$newNoticeTypeCombo').value)){
			Ext.MessageBox.alert("��ʾ", "����δѡ�񹫸����ͣ���ѡ��лл��");
			Ext.getCmp('submitButton').enable();//��ť��Ч
			return;
		}
		if(!Ext.getCmp('page_notice').form.isValid()){
			Ext.MessageBox.alert("��ʾ","ҳ���д���ɫ�����ߵ�Ϊ���������д������лл��������");	
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
				var noticeId = eval('('+response.responseText+')').newNoticeId
				var newNoticeName = eval('('+response.responseText+')').newNoticeName
				var newNoticeType = eval('('+response.responseText+')').newNoticeType
				Ext.Ajax.request({
					url : webContext + '/noticeManagerWorkflow_apply.action',
					params : {
						dataId : noticeId,
						model : this.model,
						bzparam : "{dataId :'"
								+ noticeId
								+ "',applyId : '"
								+ noticeId
								+ "',newNoticeName : '"
								+ newNoticeName
								+ "',newNoticeType : '"
								+ newNoticeType
								+ "',applyType: 'nproject',applyTypeName: '��������',customer:''}",
						defname : "NoticeManager1249905823812"
					},
					success : function(response, options) {
						var meg = Ext.decode(response.responseText);
						if (meg.id != undefined) {
							Ext.Msg.alert("��ʾ", "�Ѿ��ɹ��ύ�������͸�������", function() {

								window.location = webContext
										+ '/user/notice/noticeList.jsp';
							});
						} else {
							Ext.Msg.alert("��ʾ", "����������ʧ��", function() {
								Ext.getCmp('submitButton').enable();//��ť��Ч
								alert(meg.Exception);
							});
						}
					},
					failure : function(response, options) {
						Ext.getCmp('submitButton').enable();//��ť��Ч
						Ext.MessageBox.alert("��ʾ", "����������ʧ�ܣ�");
					}
				}, this);
			},
			failure : function(response, options) {
				Ext.getCmp('submitButton').enable();//��ť��Ч
				Ext.MessageBox.alert("��ʾ","����ʧ�ܣ�");
			},
			scope : this
		});
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
				Ext.MessageBox.alert("��ʾ","����ɹ���");
				history.back();
			},
			failure : function(response, options) {
				Ext.MessageBox.alert("��ʾ","����ʧ�ܣ�");
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
			baseCls : 'x-plain',// �Ƿ����úͱ���ɫͬ��
			width : 900,
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
	getFormpage_notice : function() {
		var da = new DataAction();
		var data = null;
		// �ж������������޸�
		var biddata = "";

		var buttonUtil = new ButtonUtil();
		this.getFormButtons = buttonUtil.getButtonForPanel("page_notice", this);
		if (this.dataId != '0') {
			data = da.getPanelElementsForEdit("page_notice_form",
					"page_notice", this.dataId);// ����Ҫ��ʱ���
			biddata = da.split(data);
		} else {
			data = da.getPanelElementsForAdd("page_notice");
			biddata = da.split(data);
		}
		for(var i=0;i<biddata.length;i++ ){
			if(biddata[i].fieldLabel=='�������') {
				biddata[i].allowBlank = false;
				biddata[i].blankText = '�˴�Ϊ������';
			}
			if(biddata[i].fieldLabel=='��������') {
				biddata[i].allowBlank = false;
				biddata[i].emptyText = '';
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
				title : "����<font color='black' style='font-weight:lighter' face='����_GB2312' font-size='9px'>��</font><font  color=red >~~~</font> <font  color = black  style='font-weight:lighter' face='����_GB2312' font-size='9px'>Ϊ�����������д!��</font>",
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
				title : "����<font color='black' style='font-weight:lighter' face='����_GB2312' font-size='9px'>��</font><font  color=red >~~~</font> <font  color = black  style='font-weight:lighter' face='����_GB2312' font-size='9px'>Ϊ�����������д!��</font>",
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
		this.model = "page_notice_form";
		var buttonUtil = new ButtonUtil();
		this.mybuttons = buttonUtil.getButtonForModel("page_notice_form", this);

		var button = new Ext.Button({
			id : 'submitButton',
			text : '�ύ',
			scope : this,
			iconCls : 'submit',
			handler : this.workSubmit
		});
		this.mybuttons[1]=button;
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
		this.on("workSubmit", this.workSubmit, this);
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