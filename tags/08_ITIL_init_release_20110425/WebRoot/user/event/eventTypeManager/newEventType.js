//url���������
function reunicode(s) {
	var k = s.split(";");
	var r = "";
	for (var x = 0; x < k.length; x++) {
		var m = k[x].replace(/@/, "");
		r += String.fromCharCode(m);
	}
	return r;
}
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
	getFormPanel : function() {
		this.formPanel = new Ext.form.FormPanel({
				id : 'formPanel',
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
				title : "�½��¼��������",
				items : [{
				html : "�¼��������ƣ�",
				cls : 'common-text',
				width : 110,
				style : 'width:200;text-align:right'
					}, new Ext.form.TextField({
						fieldLabel : "�¼���������",
						allowBlank : false,
						id : 'typeName',
						width : 200
					})]
			});
		return this.formPanel;
	},
	getDoublepanel : function() {
		var da = new DataAction();
		var todata=[];
		if(this.dataId!=""){
			var url = webContext
					+ '/eventAction_findAllEventTypeServiceItem.action?dataId='
					+ this.dataId;
			todata = da.ajaxGetData(url);
		}
		url = webContext + '/eventAction_findAllServiceItem.action?official=1';
		var fromdata = da.ajaxGetData(url);
		// �Ƚ����ݽ��ظ�������ȥ��
		var newFromData=new Array();
		toNext:for (i = 0; i < fromdata.length; i++) {
			for (j = 0; j < todata.length; j++) {
				if (fromdata[i][0] == todata[j][0]) {
					continue toNext;
				}
			}
			newFromData.push(fromdata[i]);
		}
		fromdata=newFromData;
		//2010-06-30 midified by huzh for ȥ���޸� begin
		var item = {
			id:"serviceItem",
			colspan : 2,
			xtype : "itemselector",
			name : "serviceItem",// �ύʱ���ֶ���
			dataFields : ["id", "name"],
			fromData : fromdata,
			toData : todata,
			msWidth : 330,// ע����ÿ��ҳ��Ŀ��
			style : "margin: 0,20,10,13",
//			autoScroll : true,
			msHeight : 300,
			frame : true,
			valueField : "id",
			displayField : "name",
			toLegend : "<font color=green>��ѡ������</font>",
			fromLegend : "<font color=green>��ѡ������</font>"
		};
		var panel = new Ext.form.FormPanel({
			layout : 'table',
			height : 360,
			id : 'double',
			title : "�¼�����Ĭ�Ϸ�����ѡ��<font style='font-weight:lighter' color = red >˫������ѡ��������Ӧ���¼�����</font>��",
			width : 800,
			frame : true,
			defaults : {
				bodyStyle : 'padding:4px'
			},
			layoutConfig : {
				columns : 2
			},
			items : item
		})
		return panel;

	},
	saveAll : function() {
		Ext.getCmp("saveButton").disable();
		Ext.getCmp("backButton").disable();
		var serviceItems = Ext.getCmp('serviceItem').getValue();
		var eventTypeName=Ext.getCmp("typeName").getValue()
		if(eventTypeName==''){
			Ext.MessageBox.alert("��ʾ","����д�¼��������ƣ�");
			Ext.getCmp("saveButton").enable();
			Ext.getCmp("backButton").enable();
			return;
		}
	    Ext.Ajax.request({
				url : webContext+'/eventAction_saveOrModifyEventType.action',
				params : { 
					name : eventTypeName,
					serviceItemsId : serviceItems,
					dataId : this.dataId
			  },
				success : function(response, options) {
					Ext.getCmp("saveButton").enable();
					Ext.getCmp("backButton").enable();
					if(response.responseText != ""){
						Ext.MessageBox.alert("��ʾ","��ѡ������·��������ж�Ӧ���¼����ͣ�"+response.responseText+"��");
						return;
					}
					Ext.MessageBox.alert("��ʾ","����ɹ���");
					this.typeName=Ext.getCmp("typeName").getValue();
				},
				failure : function(response, options) {
					Ext.getCmp("saveButton").enable();
					Ext.getCmp("backButton").enable();
					Ext.MessageBox.alert("��ʾ","����ʧ�ܣ�");
				}
	   		});
	},
	
	
	items : this.items,
	initComponent : function() {
		var items = new Array();
		this.getFormPanel();
		this.mybuttons = {
				layout : 'table',
				height : 'auto',
				width : 800,
				style : 'margin:4px 6px 4px 305px',
				align : 'center',
				defaults : {
					bodyStyle : 'padding:4px'
				},
				items : [{
						id:"saveButton",
						xtype : 'button',
						style : 'margin:4px 10px 4px 0',
						text : '����',
						iconCls : "save",
						scope : this,
						handler : this.saveAll
					}, {
						id:"backButton",
						xtype : 'button',
						iconCls : "back",
						style : 'margin:4px 10px 4px 0',
						handler : function() {
							history.back();
						},
						text : '����'
					}]
			};
		this.typeName=reunicode(this.typeName);
		Ext.getCmp("typeName").setValue(this.typeName);
		items.push(this.formPanel);
		var doublepanel = this.getDoublepanel();//
		items.push(doublepanel);
		items.push(this.mybuttons);
		this.items = items;
		PageTemplates.superclass.initComponent.call(this);
	}
})