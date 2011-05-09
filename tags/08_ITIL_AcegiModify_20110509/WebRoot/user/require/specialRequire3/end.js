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
// addy by zhangzy for ��ǰ�����Ϊһ��ʵ�� in 2009 12 9 start	
	getFormpanel_SpecialRequire3Confirm_Input : function() {
		var da = new DataAction();
		var data = null;
		var biddata = "";
		if (this.dataId != '0') {
			data = da.getPanelElementsForEdit("sr3_executeByEngineer",
					"panel_SpecialRequire3Confirm_Input", this.dataId);// ����Ҫ��ʱ���
		} else {
			data = da.getPanelElementsForAdd("panel_SpecialRequire3Confirm_Input");
		}
		for(var i = 0; i < data.length; i++){
			var idStr = data[i].id + "";
			if (idStr=='SpecialRequirement$appManagerCombo') {
					data[i].fieldLabel = 'ʵʩ����';
			}			
			if (idStr=='SpecialRequirement$includeAndExpend') {
					data[i].fieldLabel = '������дʵʩ����';
			}
			if (idStr=='SpecialRequirement$otherInfo') {
					data[i].fieldLabel = '���÷�̯�ɱ�����';
			}				
		}		
		biddata = da.splitForReadOnly(data);
		var formpanel_SpecialRequire3Confirm_Input = new Ext.form.FormPanel({
			id : 'panel_SpecialRequire3Confirm_Input',
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
			title : "��ǰ֧�������������",
			items : biddata
		});
		return formpanel_SpecialRequire3Confirm_Input;
	},	
	items : this.items,
// addy by zhangzy for ��ǰ�����Ϊһ��ʵ�� in 2009 12 9 end	
	/*
	 * clientvalidation ��ʼ��
	 */
	initComponent : function() {
		var histroyForm = new HistroyForm({
			reqId : this.dataId,
			reqClass : "com.zsgj.itil.require.entity.SpecialRequirement"
		});		
		var items = new Array();
		var temp = new Array();
	//add by musicbear for add back button in 20091119 begin
		var backButton = new Ext.Button({
			text : '����',
			//pressed : true,
			iconCls : 'back',
			scope : this,
			handler : function() {
				window.location = webContext
				+ "/requireAction_toRequireInfo.action?serviceItemId="
				+ this.serviceItemId;
			}
		});
		this.mybuttons = new Array();
		this.mybuttons.push(backButton);
	//add by musicbear for add back button in 20091119 end	
		if (this.mybuttons != "") {
			this.buttons = {
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
			this.buttons = {
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
		temp.push(this.getFormpanel_SpecialRequire3Confirm_Input());		
		temp.push(histroyForm);
		items.push(this.getTabpanel(temp));
		this.items = items;
		items.push(this.buttons);
		PageTemplates.superclass.initComponent.call(this);
	}
})