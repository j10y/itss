PageTemplates = Ext.extend(Ext.TabPanel, {
	id : 'sciTabInfo',
	activeTab : 0,
	enableTabScroll : true,
	deferredRender : false,
	frame : true,
	plain : true,
	border : false,
	baseCls : 'x-plain',// �Ƿ����úͱ���ɫͬ��
	width : 900,
	/** *************************************������Ϣ���***************************** */

	getBasePanel : function() {
		this.basePanel= new Ext.form.FormPanel({
			id : 'basePanel',
			layout : 'table',
			height : 'auto',
			width : 800,
			frame : true,
			buttonAlign:'center',
			collapsible : true,
			defaults : {
				bodyStyle : 'padding:4px'
			},
			layoutConfig : {
				columns : 4
			},
			reader : new Ext.data.JsonReader({root : 'form',successProperty : 'success'},
			[{name:'id',mapping:'id'},
			{name:'serviceItemCode',mapping:'serviceItemCode'},
			{name:'name',mapping:'name'},
			{name:'serviceStatus',mapping:'serviceStatus'},
			{name:'servePrice',mapping:'servePrice'},
			{name:'serveCost',mapping:'serveCost'},
			{name:'costWay',mapping:'costWay'},
			{name:'serviceStandard',mapping:'serviceStandard'},
			{name:'serviceFile',mapping:'serviceFile'},
			{name:'serviceManager',mapping:'serviceManager'},
			{name:'endDate',mapping:'endDate'},
			{name:'beginDate',mapping:'beginDate'},
			{name:'serviceType',mapping:'serviceType'},
			{name:'serviceItemType',mapping:'serviceItemType'},
			{name:'serviceEntry',mapping:'serviceEntry'},
			{name:'deleteFlag',mapping:'deleteFlag'},
			{name:'descn',mapping:'descn'},
			{name:'serviceCataCode',mapping:'serviceCataCode'},
			{name:'official',mapping:'official'}]),
			title : "��������������",
			items : [
			{html : '��������:',cls : 'common-text',style : 'width:135;text-align:right'},
			new Ext.form.TextField({fieldLabel:'��������',xtype:'textfield',id:'serviceItemCode',name:'serviceItemCode',width:200,readOnly:true}),
			{html : '��������:',cls : 'common-text',style : 'width:135;text-align:right'},
			new Ext.form.TextField({fieldLabel:'��������',xtype:'textfield',id:'name',name:'name',width:200,allowBlank:false}),
			{html : '����״̬:',cls : 'common-text',style : 'width:135;text-align:right'},
			new Ext.form.ComboBox({xtype : 'combo',hiddenName : 'serviceStatus',id : 'serviceStatusCombo',width : 200,fieldLabel : '����״̬',lazyRender : true,displayField : 'name',valueField : 'id',emptyText : '��ѡ��...',allowBlank : true,typeAhead : true,name : 'serviceStatus',triggerAction : 'all',minChars : 50,queryDelay : 700,store : new Ext.data.JsonStore({url : webContext+ '/extjs/comboDataAction?clazz=com.digitalchina.itil.service.entity.ServiceStatus',fields : ['id', 'name'],listeners : {beforeload : function(store, opt) {if (opt.params['serviceStatus'] == undefined) {opt.params['name'] = Ext.getCmp('serviceStatusCombo').defaultParam;}}},totalProperty : 'rowCount',root : 'data',id : 'id'}),pageSize : 10,listeners : {'beforequery' : function(queryEvent) {var param = queryEvent.combo.getRawValue();this.defaultParam = param;if (queryEvent.query == '') {param = '';}this.store.load({params : {name : param,start : 0}});return true;}},initComponent : function() {this.store.load({params : {id : Ext.getCmp('serviceStatusCombo').getValue(),start : 0},callback : function(r, options, success) {Ext.getCmp('serviceStatusCombo').setValue(Ext.getCmp('serviceStatusCombo').getValue());}});}}), 
			{html : '����������:',cls : 'common-text',style : 'width:135;text-align:right'},
			new Ext.form.ComboBox({xtype:'combo',hiddenName: 'serviceItemType',id :'serviceItemTypeCombo',width:200,fieldLabel:'����������',lazyRender: true,displayField: 'name',valueField :'id',emptyText:'��ѡ��...',allowBlank:true,typeAhead:true,name:'serviceItemType',triggerAction:'all',minChars :50,queryDelay : 700,store:new Ext.data.JsonStore({url:webContext+'/extjs/comboDataAction?clazz=com.digitalchina.itil.service.entity.ServiceItemType',fields:['id','name'],listeners:{beforeload : function(store, opt){if(opt.params['serviceItemType'] == undefined){opt.params['name'] =Ext.getCmp('serviceItemTypeCombo').defaultParam;}}},totalProperty:'rowCount',root:'data',id:'id'}),pageSize:10,listeners:{'beforequery' : function(queryEvent){var param = queryEvent.combo.getRawValue();this.defaultParam = param;if(queryEvent.query==''){param='';}this.store.load({params:{name:param,start:0}});return true;}},initComponent : function() {this.store.load({params:{id:Ext.getCmp('serviceItemTypeCombo').getValue(),start:0},callback:function(r, options, success){Ext.getCmp('serviceItemTypeCombo').setValue(Ext.getCmp('serviceItemTypeCombo').getValue());}});}}),
			{html : '����۸�:',cls : 'common-text',style : 'width:135;text-align:right'},
			new Ext.form.NumberField({fieldLabel:'����۸�',xtype:'textfield',id:'servePrice',name:'servePrice',width:200,allowBlank:true}),
			{html : '����ɱ�:',cls : 'common-text',style : 'width:135;text-align:right'},
			new Ext.form.NumberField({fieldLabel:'����ɱ�',xtype:'textfield',id:'serveCost',name:'serveCost',width:200,allowBlank:true}),
			{html : '�Ʒѷ�ʽ:',cls : 'common-text',style : 'width:135;text-align:right'},
			new Ext.form.ComboBox({xtype:'combo',hiddenName: 'costWay',id :'costWayCombo',width:200,fieldLabel:'�Ʒѷ�ʽ',lazyRender: true,displayField: 'name',valueField :'id',emptyText:'��ѡ��...',allowBlank:true,typeAhead:true,name:'costWay',triggerAction:'all',minChars :50,queryDelay : 700,store:new Ext.data.JsonStore({url:webContext+'/extjs/comboDataAction?clazz=com.digitalchina.itil.service.entity.CostWay',fields:['id','name'],listeners:{beforeload : function(store, opt){if(opt.params['costWay'] == undefined){opt.params['name'] =Ext.getCmp('costWayCombo').defaultParam;}}},totalProperty:'rowCount',root:'data',id:'id'}),pageSize:10,listeners:{'beforequery' : function(queryEvent){var param = queryEvent.combo.getRawValue();this.defaultParam = param;if(queryEvent.query==''){param='';}this.store.load({params:{name:param,start:0}});return true;}},initComponent : function() {this.store.load({params:{id:Ext.getCmp('costWayCombo').getValue(),start:0},callback:function(r, options, success){Ext.getCmp('costWayCombo').setValue(Ext.getCmp('costWayCombo').getValue());}});}}),
			{html : '�����׼:',cls : 'common-text',style : 'width:135;text-align:right'},
			new Ext.form.TextField({fieldLabel:'�����׼',xtype:'textfield',id:'serviceStandard',name:'serviceStandard',width:200,allowBlank:true}),
			{html : '�û�ʹ���ֲ�:',cls : 'common-text',style : 'width:135;text-align:right'},
			new Ext.form.TextField({fieldLabel:'�û�ʹ���ֲ�',xtype:'textfield',id:'serviceFile',name:'serviceFile',width:200,allowBlank:true}),
			{html : '������:',cls : 'common-text',style : 'width:135;text-align:right'},
			new Ext.form.ComboBox({xtype:'combo',hiddenName: 'serviceManager',id :'serviceManagerCombo',width:200,fieldLabel:'������',lazyRender: true,displayField: 'userName',valueField :'id',emptyText:'��ѡ��...',allowBlank:true,typeAhead:true,name:'serviceManager',triggerAction:'all',minChars :50,queryDelay : 700,store:new Ext.data.JsonStore({url:webContext+'/extjs/comboDataAction?clazz=com.digitalchina.info.framework.security.entity.UserInfo',fields:['id','userName'],listeners:{beforeload : function(store, opt){if(opt.params['serviceManager'] == undefined){opt.params['userName'] =Ext.getCmp('serviceManagerCombo').defaultParam;}}},totalProperty:'rowCount',root:'data',id:'id'}),pageSize:10,listeners:{'beforequery' : function(queryEvent){var param = queryEvent.combo.getRawValue();this.defaultParam = param;if(queryEvent.query==''){param='';}this.store.load({params:{userName:param,start:0}});return true;}},initComponent : function() {this.store.load({params:{id:Ext.getCmp('serviceManagerCombo').getValue(),start:0},callback:function(r, options, success){Ext.getCmp('serviceManagerCombo').setValue(Ext.getCmp('serviceManagerCombo').getValue());}});}}),
			{html : '��ʼ��Ч����:',cls : 'common-text',style : 'width:135;text-align:right'},
			new Ext.form.DateField({xtype:'datefield',id:'beginDate',name:'beginDate',width:200,allowBlank:false,validator:validate_date,format:'Y-m-d',fieldLabel:'��ʼ��Ч����'}),
			{html : '��ֹ��Ч����:',cls : 'common-text',style : 'width:135;text-align:right'},
			new Ext.form.DateField({xtype:'datefield',id:'endDate',name:'endDate',width:200,allowBlank:false,validator:validate_date,format:'Y-m-d',fieldLabel:'��ֹ��Ч����'}),
			{html : '�������:',cls : 'common-text',style : 'width:135;text-align:right'},
			new Ext.form.TextField({fieldLabel:'�������',xtype:'textfield',id:'serviceEntry',name:'serviceEntry',width:200,allowBlank:true}),
			{html : '����Ŀ¼����:',cls : 'common-text',style : 'width:135;text-align:right'},
			new Ext.form.TextField({fieldLabel:'����Ŀ¼����',xtype:'textfield',id:'serviceCataCode',name:'serviceCataCode',width:200,allowBlank:true}),
			{html : '��������:',cls : 'common-text',style : 'width:135;text-align:right'},
			new Ext.form.HtmlEditor({fieldLabel:'��������',xtype:'htmleditor',colspan:3,rowspan:0,id:'descn',name:'descn',width:530,allowBlank:true}),
			{html : '��������:',cls : 'common-text',style : 'width:135;text-align:right'},
			new Ext.form.ComboBox({xtype:'combo',hiddenName: 'serviceType',id :'serviceTypeCombo',width:200,fieldLabel:'��������',lazyRender: true,displayField: 'name',valueField :'id',emptyText:'��ѡ��...',allowBlank:true,typeAhead:true,name:'serviceType',triggerAction:'all',minChars :50,queryDelay : 700,store:new Ext.data.JsonStore({url:webContext+'/extjs/comboDataAction?clazz=com.digitalchina.itil.service.entity.ServiceType',fields:['id','name'],listeners:{beforeload : function(store, opt){if(opt.params['serviceType'] == undefined){opt.params['name'] =Ext.getCmp('serviceTypeCombo').defaultParam;}}},totalProperty:'rowCount',root:'data',id:'id'}),pageSize:10,listeners:{'beforequery' : function(queryEvent){var param = queryEvent.combo.getRawValue();this.defaultParam = param;if(queryEvent.query==''){param='';}this.store.load({params:{name:param,start:0}});return true;}},initComponent : function() {this.store.load({params:{id:Ext.getCmp('serviceTypeCombo').getValue(),start:0},callback:function(r, options, success){Ext.getCmp('serviceTypeCombo').setValue(Ext.getCmp('serviceTypeCombo').getValue());}});}}),
			{html : '�Ƿ���ʽ:',cls : 'common-text',style : 'width:135;text-align:right'},
			new Ext.form.ComboBox({xtype:"combo",id:"officialCombo",width:200,style:"",mode: "local",hiddenName: "official",triggerAction:"all",typeAhead: true,forceSelection: true,allowBlank:true,store: new Ext.data.SimpleStore({fields: ["id", "name"],data: [["1","��"],["0","��"]]}),emptyText:"��ѡ��...",valueField :"id",value :"",displayField: "name",name:"official",fieldLabel:"�Ƿ���ʽ",listeners : {"expand" : function(combo) {combo.reset();}}}),
			new Ext.form.Hidden({xtype:'hidden',id:'id',name:'id',width:200,fieldLabel:'�Զ����'}),
			new Ext.form.Hidden({xtype:'hidden',id:'deleteFlag',name:'deleteFlag',width:200,fieldLabel:'ɾ�����'})] ,
			buttons : [{
				text : '����',
				handler : function() {
					if (!Ext.getCmp('basePanel').form.isValid()) {
						Ext.MessageBox.alert("��ʾ",
								"ҳ���д���ɫ�����ߵ�Ϊ������,����д������лл��������");
						return false;
					}
					var baseParam = getFormParam('basePanel');
					var vp = null;
					if (baseParam != null) {
						vp = Ext.apply(baseParam, vp, {});
					}
					Ext.Ajax.request({
						url : webContext + "/serviceItem_saveBaseInfo.action",
						params : vp,
						success : function(response, options) {
							var responseArray = Ext.util.JSON.decode(response.responseText);
							var curId = responseArray.dataId;
							Ext.MessageBox.alert("����ɹ�");
							window.location = webContext+ "/infoAdmin/serviceItem/serviceItem_info.jsp?dataId="+ curId;
						},
						failure : function(response, options) {
							Ext.MessageBox.alert("����ʧ��");
						}
					}, this);
				}
			}, {
				text : ' �������ӷ��� ',
				handler : function() {
					window.location.href = "serviceItem_info.jsp";
				}
			}]
		});
		 if (this.dataId != "0") {
			this.basePanel.load({
				url : webContext+'/serviceItem_getSiDataById.action?dataId='+ this.dataId,
				timeout : 30,
				success : function(action, form) {
				Ext.getCmp("serviceStatusCombo").initComponent();Ext.getCmp("costWayCombo").initComponent();Ext.getCmp("serviceManagerCombo").initComponent();Ext.getCmp("serviceTypeCombo").initComponent();Ext.getCmp("serviceItemTypeCombo").initComponent();
				}
			});
		}
		return this.basePanel;
	},
	/** *************************************������Ϣ���***************************** */

	getInfoPanel : function() {
		var da = new DataAction();
		var curdataId = dataId;
		var data = da.getElementForIncorporeity(dataId);
		var infodata = da.split(data);
		var infoPanel = null;
		if (infodata == null) {
			infoPanel = new Ext.Panel({
				id : 'infoPanel',
				title : '������Ϣ',
				layout : 'table',
				height : 'auto',
				buttonAlign:'center',
				width : 800,
				frame : true,
				collapsible : true,
				tbar : [{
					text : '��Ϣ�ֶι���',
					pressed : true,
					handler : function() {
						window.location = webContext
								+ "/serviceItem_columnInfo.action?dataId="
								+ curdataId;
					}
				}]
			});
		} else {
			infoPanel = new Ext.form.FormPanel({
				id : 'infoPanel',
				title : '������Ϣ',
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
				items : infodata,
				buttonAlign:'center',
				buttons : [{
					text : '����',
					handler : function() {
						var baseParam = Ext.getCmp('infoPanel').form
								.getValues(false);
						var vp = null;
						var i = 1;
						if (baseParam != null) {
							vp = Ext.apply(baseParam, vp, {});
						}
						Ext.Ajax.request({
							url : webContext
									+ "/serviceItem_saveSpecialInfo.action?dataId="
									+ dataId,
							params : vp,
							success : function(response, options) {
								Ext.MessageBox.alert("����ɹ�");
							},
							failure : function(response, options) {
								Ext.MessageBox.alert("����ʧ��");
							}
						}, this);
					}
				}, {
					text : '��Ϣ�ֶι���',
					handler : function() {
						window.location = webContext
								+ "/serviceItem_columnInfo.action?dataId="
								+ curdataId;
					}
				}]
			});
		}
		return infoPanel;
	},

	/***************************************��������������������******************************/

	getTablePanel : function(sciId) {
		var tablePanel = new Ext.Panel({
			id : "tablePanel",
			//scope : this,
			title : "��������",
			modal : true,
			height : 800,
			width : 800,
			resizable : false,
			//			draggable : true,
			autoLoad : {
				url : webContext
						+ "/tabFrame.jsp?url="
						+ webContext
						+ "/infoAdmin/serviceItemUserTableAction.do?methodCall=addTable****serviceItemId="
						+ sciId,

				text : "ҳ�������......",
				method : 'post',
				scripts : true,
				scope : this
			},
			viewConfig : {
				autoFill : true,
				forceFit : true
			},
			layout : 'table',
			items : [{
				html : "���ڼ���ҳ������......"
			}]
		});
		return tablePanel;
	},

	items : this.items,

	/*
	 * clientvalidation ��ʼ��
	 */
	initComponent : function() {
		var basePanel = this.getBasePanel();
		var infoPanel = this.getInfoPanel();
		var tablePanel = this.getTablePanel(this.dataId);
		var processPanel = new ProcessPanel({
			serviceItemId : this.dataId
		});
		this.items = [basePanel, infoPanel, tablePanel, processPanel];
		this.on("tabchange", function(tab, newTab, currentTab) {
			var sciId = Ext.getCmp("id").getValue();
			if (sciId == "") {
				//				if(newTab==Ext.getCmp('basePanel')){
				//					alert("klajsdflkd");
				//				}
				Ext.getCmp('sciTabInfo').setActiveTab('basePanel');
			}

		}, this);
		PageTemplates.superclass.initComponent.call(this);
	},
	pageMethod : function() {
		alert("��ǰdataId:" + dataId);
	}
})
function unicode(s) {
	var len = s.length;
	var rs = "";
	for (var i = 0; i < len; i++) {
		var k = s.substring(i, i + 1);
		rs += "&#" + s.charCodeAt(i) + ";";
	}
	return rs;
}
