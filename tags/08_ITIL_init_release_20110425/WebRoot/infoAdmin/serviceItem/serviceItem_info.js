PageTemplates = Ext.extend(Ext.TabPanel, {
	id : 'sciTabInfo',
	activeTab : 0,
	enableTabScroll : true,
	deferredRender : false,
	frame : true,
	plain : true,
	border : false,
	baseCls : 'x-plain',// 是否设置和背景色同步
	width : 900,
	/** *************************************基础信息面板***************************** */

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
			title : "服务项表单基础面板",
			items : [
			{html : '服务项编号:',cls : 'common-text',style : 'width:135;text-align:right'},
			new Ext.form.TextField({fieldLabel:'服务项编号',xtype:'textfield',id:'serviceItemCode',name:'serviceItemCode',width:200,readOnly:true}),
			{html : '服务名称:',cls : 'common-text',style : 'width:135;text-align:right'},
			new Ext.form.TextField({fieldLabel:'服务名称',xtype:'textfield',id:'name',name:'name',width:200,allowBlank:false}),
			{html : '服务状态:',cls : 'common-text',style : 'width:135;text-align:right'},
			new Ext.form.ComboBox({xtype : 'combo',hiddenName : 'serviceStatus',id : 'serviceStatusCombo',width : 200,fieldLabel : '服务状态',lazyRender : true,displayField : 'name',valueField : 'id',emptyText : '请选择...',allowBlank : true,typeAhead : true,name : 'serviceStatus',triggerAction : 'all',minChars : 50,queryDelay : 700,store : new Ext.data.JsonStore({url : webContext+ '/extjs/comboDataAction?clazz=com.digitalchina.itil.service.entity.ServiceStatus',fields : ['id', 'name'],listeners : {beforeload : function(store, opt) {if (opt.params['serviceStatus'] == undefined) {opt.params['name'] = Ext.getCmp('serviceStatusCombo').defaultParam;}}},totalProperty : 'rowCount',root : 'data',id : 'id'}),pageSize : 10,listeners : {'beforequery' : function(queryEvent) {var param = queryEvent.combo.getRawValue();this.defaultParam = param;if (queryEvent.query == '') {param = '';}this.store.load({params : {name : param,start : 0}});return true;}},initComponent : function() {this.store.load({params : {id : Ext.getCmp('serviceStatusCombo').getValue(),start : 0},callback : function(r, options, success) {Ext.getCmp('serviceStatusCombo').setValue(Ext.getCmp('serviceStatusCombo').getValue());}});}}), 
			{html : '服务项类型:',cls : 'common-text',style : 'width:135;text-align:right'},
			new Ext.form.ComboBox({xtype:'combo',hiddenName: 'serviceItemType',id :'serviceItemTypeCombo',width:200,fieldLabel:'服务项类型',lazyRender: true,displayField: 'name',valueField :'id',emptyText:'请选择...',allowBlank:true,typeAhead:true,name:'serviceItemType',triggerAction:'all',minChars :50,queryDelay : 700,store:new Ext.data.JsonStore({url:webContext+'/extjs/comboDataAction?clazz=com.digitalchina.itil.service.entity.ServiceItemType',fields:['id','name'],listeners:{beforeload : function(store, opt){if(opt.params['serviceItemType'] == undefined){opt.params['name'] =Ext.getCmp('serviceItemTypeCombo').defaultParam;}}},totalProperty:'rowCount',root:'data',id:'id'}),pageSize:10,listeners:{'beforequery' : function(queryEvent){var param = queryEvent.combo.getRawValue();this.defaultParam = param;if(queryEvent.query==''){param='';}this.store.load({params:{name:param,start:0}});return true;}},initComponent : function() {this.store.load({params:{id:Ext.getCmp('serviceItemTypeCombo').getValue(),start:0},callback:function(r, options, success){Ext.getCmp('serviceItemTypeCombo').setValue(Ext.getCmp('serviceItemTypeCombo').getValue());}});}}),
			{html : '服务价格:',cls : 'common-text',style : 'width:135;text-align:right'},
			new Ext.form.NumberField({fieldLabel:'服务价格',xtype:'textfield',id:'servePrice',name:'servePrice',width:200,allowBlank:true}),
			{html : '服务成本:',cls : 'common-text',style : 'width:135;text-align:right'},
			new Ext.form.NumberField({fieldLabel:'服务成本',xtype:'textfield',id:'serveCost',name:'serveCost',width:200,allowBlank:true}),
			{html : '计费方式:',cls : 'common-text',style : 'width:135;text-align:right'},
			new Ext.form.ComboBox({xtype:'combo',hiddenName: 'costWay',id :'costWayCombo',width:200,fieldLabel:'计费方式',lazyRender: true,displayField: 'name',valueField :'id',emptyText:'请选择...',allowBlank:true,typeAhead:true,name:'costWay',triggerAction:'all',minChars :50,queryDelay : 700,store:new Ext.data.JsonStore({url:webContext+'/extjs/comboDataAction?clazz=com.digitalchina.itil.service.entity.CostWay',fields:['id','name'],listeners:{beforeload : function(store, opt){if(opt.params['costWay'] == undefined){opt.params['name'] =Ext.getCmp('costWayCombo').defaultParam;}}},totalProperty:'rowCount',root:'data',id:'id'}),pageSize:10,listeners:{'beforequery' : function(queryEvent){var param = queryEvent.combo.getRawValue();this.defaultParam = param;if(queryEvent.query==''){param='';}this.store.load({params:{name:param,start:0}});return true;}},initComponent : function() {this.store.load({params:{id:Ext.getCmp('costWayCombo').getValue(),start:0},callback:function(r, options, success){Ext.getCmp('costWayCombo').setValue(Ext.getCmp('costWayCombo').getValue());}});}}),
			{html : '服务标准:',cls : 'common-text',style : 'width:135;text-align:right'},
			new Ext.form.TextField({fieldLabel:'服务标准',xtype:'textfield',id:'serviceStandard',name:'serviceStandard',width:200,allowBlank:true}),
			{html : '用户使用手册:',cls : 'common-text',style : 'width:135;text-align:right'},
			new Ext.form.TextField({fieldLabel:'用户使用手册',xtype:'textfield',id:'serviceFile',name:'serviceFile',width:200,allowBlank:true}),
			{html : '服务经理:',cls : 'common-text',style : 'width:135;text-align:right'},
			new Ext.form.ComboBox({xtype:'combo',hiddenName: 'serviceManager',id :'serviceManagerCombo',width:200,fieldLabel:'服务经理',lazyRender: true,displayField: 'userName',valueField :'id',emptyText:'请选择...',allowBlank:true,typeAhead:true,name:'serviceManager',triggerAction:'all',minChars :50,queryDelay : 700,store:new Ext.data.JsonStore({url:webContext+'/extjs/comboDataAction?clazz=com.digitalchina.info.framework.security.entity.UserInfo',fields:['id','userName'],listeners:{beforeload : function(store, opt){if(opt.params['serviceManager'] == undefined){opt.params['userName'] =Ext.getCmp('serviceManagerCombo').defaultParam;}}},totalProperty:'rowCount',root:'data',id:'id'}),pageSize:10,listeners:{'beforequery' : function(queryEvent){var param = queryEvent.combo.getRawValue();this.defaultParam = param;if(queryEvent.query==''){param='';}this.store.load({params:{userName:param,start:0}});return true;}},initComponent : function() {this.store.load({params:{id:Ext.getCmp('serviceManagerCombo').getValue(),start:0},callback:function(r, options, success){Ext.getCmp('serviceManagerCombo').setValue(Ext.getCmp('serviceManagerCombo').getValue());}});}}),
			{html : '起始有效日期:',cls : 'common-text',style : 'width:135;text-align:right'},
			new Ext.form.DateField({xtype:'datefield',id:'beginDate',name:'beginDate',width:200,allowBlank:false,validator:validate_date,format:'Y-m-d',fieldLabel:'起始有效日期'}),
			{html : '终止有效日期:',cls : 'common-text',style : 'width:135;text-align:right'},
			new Ext.form.DateField({xtype:'datefield',id:'endDate',name:'endDate',width:200,allowBlank:false,validator:validate_date,format:'Y-m-d',fieldLabel:'终止有效日期'}),
			{html : '服务入口:',cls : 'common-text',style : 'width:135;text-align:right'},
			new Ext.form.TextField({fieldLabel:'服务入口',xtype:'textfield',id:'serviceEntry',name:'serviceEntry',width:200,allowBlank:true}),
			{html : '服务目录编码:',cls : 'common-text',style : 'width:135;text-align:right'},
			new Ext.form.TextField({fieldLabel:'服务目录编码',xtype:'textfield',id:'serviceCataCode',name:'serviceCataCode',width:200,allowBlank:true}),
			{html : '服务描述:',cls : 'common-text',style : 'width:135;text-align:right'},
			new Ext.form.HtmlEditor({fieldLabel:'服务描述',xtype:'htmleditor',colspan:3,rowspan:0,id:'descn',name:'descn',width:530,allowBlank:true}),
			{html : '服务类型:',cls : 'common-text',style : 'width:135;text-align:right'},
			new Ext.form.ComboBox({xtype:'combo',hiddenName: 'serviceType',id :'serviceTypeCombo',width:200,fieldLabel:'服务类型',lazyRender: true,displayField: 'name',valueField :'id',emptyText:'请选择...',allowBlank:true,typeAhead:true,name:'serviceType',triggerAction:'all',minChars :50,queryDelay : 700,store:new Ext.data.JsonStore({url:webContext+'/extjs/comboDataAction?clazz=com.digitalchina.itil.service.entity.ServiceType',fields:['id','name'],listeners:{beforeload : function(store, opt){if(opt.params['serviceType'] == undefined){opt.params['name'] =Ext.getCmp('serviceTypeCombo').defaultParam;}}},totalProperty:'rowCount',root:'data',id:'id'}),pageSize:10,listeners:{'beforequery' : function(queryEvent){var param = queryEvent.combo.getRawValue();this.defaultParam = param;if(queryEvent.query==''){param='';}this.store.load({params:{name:param,start:0}});return true;}},initComponent : function() {this.store.load({params:{id:Ext.getCmp('serviceTypeCombo').getValue(),start:0},callback:function(r, options, success){Ext.getCmp('serviceTypeCombo').setValue(Ext.getCmp('serviceTypeCombo').getValue());}});}}),
			{html : '是否正式:',cls : 'common-text',style : 'width:135;text-align:right'},
			new Ext.form.ComboBox({xtype:"combo",id:"officialCombo",width:200,style:"",mode: "local",hiddenName: "official",triggerAction:"all",typeAhead: true,forceSelection: true,allowBlank:true,store: new Ext.data.SimpleStore({fields: ["id", "name"],data: [["1","是"],["0","否"]]}),emptyText:"请选择...",valueField :"id",value :"",displayField: "name",name:"official",fieldLabel:"是否正式",listeners : {"expand" : function(combo) {combo.reset();}}}),
			new Ext.form.Hidden({xtype:'hidden',id:'id',name:'id',width:200,fieldLabel:'自动编号'}),
			new Ext.form.Hidden({xtype:'hidden',id:'deleteFlag',name:'deleteFlag',width:200,fieldLabel:'删除标记'})] ,
			buttons : [{
				text : '保存',
				handler : function() {
					if (!Ext.getCmp('basePanel').form.isValid()) {
						Ext.MessageBox.alert("提示",
								"页面中带红色波浪线的为必填项,请填写完整。谢谢您合作！");
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
							Ext.MessageBox.alert("保存成功");
							window.location = webContext+ "/infoAdmin/serviceItem/serviceItem_info.jsp?dataId="+ curId;
						},
						failure : function(response, options) {
							Ext.MessageBox.alert("保存失败");
						}
					}, this);
				}
			}, {
				text : ' 继续增加服务 ',
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
	/** *************************************其他信息面板***************************** */

	getInfoPanel : function() {
		var da = new DataAction();
		var curdataId = dataId;
		var data = da.getElementForIncorporeity(dataId);
		var infodata = da.split(data);
		var infoPanel = null;
		if (infodata == null) {
			infoPanel = new Ext.Panel({
				id : 'infoPanel',
				title : '其他信息',
				layout : 'table',
				height : 'auto',
				buttonAlign:'center',
				width : 800,
				frame : true,
				collapsible : true,
				tbar : [{
					text : '信息字段管理',
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
				title : '其他信息',
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
					text : '保存',
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
								Ext.MessageBox.alert("保存成功");
							},
							failure : function(response, options) {
								Ext.MessageBox.alert("保存失败");
							}
						}, this);
					}
				}, {
					text : '信息字段管理',
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

	/***************************************服务项关联需求主表面板******************************/

	getTablePanel : function(sciId) {
		var tablePanel = new Ext.Panel({
			id : "tablePanel",
			//scope : this,
			title : "需求主表",
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

				text : "页面加载中......",
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
				html : "正在加载页面数据......"
			}]
		});
		return tablePanel;
	},

	items : this.items,

	/*
	 * clientvalidation 初始化
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
		alert("当前dataId:" + dataId);
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
