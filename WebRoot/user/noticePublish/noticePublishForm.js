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
	workSubmit22 : function() {
		var dataId = this.dataId;
		if (dataId == "") {
			Ext.MessageBox.alert("��ʾ", "���ȱ���ݸ�");
			return;
		}
		Ext.Ajax.request({
			url : webContext + '/noticeManagerWorkflow_apply.action',
			params : {
				dataId : this.dataId,
				model : this.model,
				bzparam : "{dataId :'" + this.dataId
						+ "',applyId : '" + this.dataId
						+ "',applyNum : '-----"
						+ "',applyName : '" + Ext.getCmp('NewNotice$title').getValue()
						+ "',applyType: 'nproject',applyTypeName: '��������',customer:'',alterFlag:'����','workflowHistory':'com.zsgj.itil.notice.entity.NoticeAuditHis'}",
				defname : "noticeManager1237967838187"
			},
			success : function(response, options) {
				Ext.Msg.alert("��ʾ", "�����������ɹ�", function() {
					window.location = webContext
							+ '/user/noticePublish/noticePublishList.jsp';
				});
			},
			failure : function(response, options) {
				Ext.MessageBox.alert("��ʾ", "����������ʧ��");
			}
		}, this);
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
	getPanelArea : function() {
		var dataId = this.dataId;
		var contents = "";
		var contentIntro = new Ext.form.TextArea({
			xtype : "textarea",
			height : 200,
			name : "content",
			width : 800,
			id : "content",
			readOnly : true,
			listeners : {
				"render" : function(f) {
					Ext.Ajax.request({
						url : webContext + '/noticeaction_find.action',
						method : "POST",
						params : {
							dataId : dataId
						},
						success : function(response, options) {
							contents = Ext.decode(response.responseText);
							Ext.getCmp('content').setValue(contents);
						}
					})
				}
			}
		})
		var item = new Array();
		item.push({
			//html : "��������:",
			cls : 'common-text',
			style : 'margin:4px 6px 4px 0'
		}, contentIntro);

		var cpanel = new Ext.form.FormPanel({
			id : 'cataPanel',
			title : '��������',
			layout : 'table',
			region : 'north',
			height : 280,
			width : 'auto',
			frame : true,
			collapsible : true,
			defaults : {
				bodyStyle : 'padding:16px'
			},
			layoutConfig : {
				columns : 8
			},
			items : item
		});
		return cpanel;

	},

	getFormpage_notice : function() {
		var da = new DataAction();
		var data = null;
		// �ж������������޸�
		var biddata = "";
		var buttonUtil = new ButtonUtil();
		this.getFormButtons = buttonUtil.getButtonForPanel("page_notice", this);
		if (this.dataId != '0') {
			data = da.getPanelElementsForEdit("page_noticePublish_form",
					"page_notice", this.dataId);// ����Ҫ��ʱ���
			for (var i = 0; i < data.length; i++) {
				if (data[i].xtype == 'textfield') {
					data[i].readOnly = true;
				}
				if (data[i].xtype == 'datefield') {
					data[i].readOnly = true;
					data[i].hideTrigger = true;
				}
			}
			biddata = da.split(data);
		} else {
			data = da.getPanelElementsForAdd("page_notice");
			//alert("bb");
			biddata = da.split(data);
		}
		if (this.getFormButtons.length != 0) {
			this.formpage_notice = new Ext.form.FormPanel({
				id : 'page_notice',
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
				title : "����",
				items : biddata,
				buttons : this.getFormButtons
			});
		} else {
			this.formpage_notice = new Ext.form.FormPanel({
				id : 'page_notice',
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
				title : "����",
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
		this.model = "page_noticePublish_form";
		var buttonUtil = new ButtonUtil();
		this.mybuttons = buttonUtil.getButtonForModel(
				"page_noticePublish_form", this);
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
		temp.push(this.getPanelArea());
		items = temp;
		items.push(this.allbuttons);
		this.items = items;
		this.on("workSubmit22", this.workSubmit22, this);
		PageTemplates.superclass.initComponent.call(this);
	}
})