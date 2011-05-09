UserCostInputPanel = Ext.extend(Ext.Panel, {
	id : "userCostInputPanel",
	height : 'auto',
	width : 'auto',
	layout : 'fit',
	frame : true,
	items : this.items,
	getMainForm : function() {
		var searchForm = new Ext.form.FormPanel({
			title : '人员成本录入',
			id : "userCostInputPanelFormPanel",
			collapsible : true,
			frame : true,
			autoScroll : true,
			layout : "table",
			layoutConfig : {
				columns : 1
			},
			items : [{
				xtype : 'fieldset',
				layout : 'table',
				align:'center',
				layoutConfig : {
					columns : 4
				},
				defaults : {
					bodyStyle : 'padding:16px 0px 8px 0px'
				},
				items : [{
							html : "成本归结类型&nbsp:&nbsp",
							cls : 'common-text',
							style : 'width:150;text-align:right'
						}, {
							xtype : 'textfield',
							id : "costApportionType",
							fieldLabel : '成本归结类型',
							value:'配置项',
							allowBlank : false,
							readOnly:true,
							width : 200
						}, {
							html : "配置项&nbsp:&nbsp",
							cls : 'common-text',
							style : 'width:150;text-align:right'
						}, {
							xtype : 'textfield',
							id : "configItemType",
							fieldLabel : '配置项',
							value:'信息化管理部',
							allowBlank : false,
							readOnly:true,
							width : 200
						}, {
							html : "费用类型&nbsp:&nbsp",
							cls : 'common-text',
							style : 'width:150;text-align:right'
						}, {
							xtype : 'textfield',
							id : "financeCostType",
							fieldLabel : '配置项',
							value:'人工总包费用/A004050100',
							allowBlank : false,
							readOnly:true,
							height : 0,
							width : 200
						}, {
							html : "成本发生金额&nbsp<font color='red'>*</font>:&nbsp",
							cls : 'common-text',
							style : 'width:150;text-align:right'
						}, {
							id : "costAmount",
							xtype : 'textfield',
							fieldLabel : '成本发生金额',
							allowBlank : false,
							listeners : {
								'blur' : function(obj, e) {
									var money = Ext.getCmp("costAmount").getValue();
									if (!/^(\d*)(\,|\d|\.)*$/.test(money)
											|| money == "") {
										alert("请输入有效数值");
										return;
									}
								}
							},
							height : 0,
							width : 200
						}, {
							html : "成本中心&nbsp:&nbsp",
							cls : 'common-text',
							style : 'width:150;text-align:right'
						}, {
							xtype : 'combo',
							id : "costCenterId",
							hiddenName : "costCenter",
							fieldLabel : '成本中心',
							defaultParam : "",
							pageSize : 10,
							allowBlank : false,
							displayField : 'costCenter',
							valueField : "id",
							triggerAction : "all",
							value:'1',
							width : 200,
							store : new Ext.data.JsonStore({
								url : webContext + '/userCostInput_findCostCenter.action',
								fields : ['id', 'costCenter'],
								totalProperty : 'rowCount',
								root : 'data',
								listeners : {
									beforeload : function(store, opt) {
										var param = Ext	.getCmp('costCenterId').defaultParam;
										if (opt.params['propertyValue'] == undefined) {
											opt.params['propertyValue'] = unicode(param);
										}
									}
								},
								id : 'id'
							}),
							listeners : {
								'beforequery' : function(queryEvent) {
									
									var param = queryEvent.query;
									this.defaultParam = param;
									param = unicode(param);
									this.store.load({
												params : {
													propertyValue : param,
													start : 0
												}
											});
									return true;
								}
							},
					comboInitComponent : function() {
						if(this.getValue()!=''){
							var combo=this;
							this.store.load({
								params : {
									id : this.getValue(),
									start : 0
								},
								callback : function(r, options, success) {
									if(r.length>0){
										combo.setRawValue(r[0].get(combo.displayField));
									}
								}
							});
						}
					}
						}, {
							html : "借款类型&nbsp:&nbsp",
							cls : 'common-text',
							style : 'width:150;text-align:right'
						}, {
							xtype : 'textfield',
							id : "borrowType",
							fieldLabel : '借款类型',
							value:'直接报销',
							allowBlank : false,
							readOnly:true,
							height : 0,
							width : 200
						}, {
							html : "费用发生日期:&nbsp;",
							cls : 'common-text',
							style : 'width:150;text-align:right'
						}, {
							xtype : 'datefield',
							fieldLabel : '费用发生日期',
							id : "costDate",
							format : "Y-m-d",
							value: this.getCurrentDate(),
							height : 0,
							width : 200
						}, {
							html : "成本数据来源&nbsp:&nbsp",
							cls : 'common-text',
							style : 'width:150;text-align:right'
						}, {
							xtype : 'textfield',
							id : "costDataResource",
							fieldLabel : '成本数据来源',
							value:'系统计算',
							allowBlank : false,
							readOnly:true,
							height : 0,
							width : 200
						}]
			}],
			buttons : [{
						text : "保存",
						pressed : true,
						iconCls : 'save',
						handler : this.formSave
					  }],
			buttonAlign : 'center'
		});
		return searchForm;
	},
	
	formSave:function(){
		if(validateMethods()){
			Ext.Ajax.request({
				url:webContext+'/userCostInput_saveUserCostMesg.action',
				params:{
					formParams:Ext.util.JSON.encode(Ext.getCmp("userCostInputPanelFormPanel").getForm().getValues()),
					costCenter:Ext.getCmp("costCenterId").getRawValue()
				},
				success : function(action, form) {
						Ext.MessageBox.alert("提示信息：", "保存成功");
				},
				failure : function(response, options) {
					Ext.MessageBox.alert("提示信息：", "提交数据失败");
				}
			});
		};
		
	},
	
	/**
	 * 得到成本发生日期，默认上个月26日
	 * @return {}
	 */
	getCurrentDate:function(){
		var currentDate = new Date();
		currentDate.setDate(26);
		currentDate.setMonth(currentDate.getMonth()-1);
		return currentDate;
	},

	initComponent : function() {
		this.searchForm = this.getMainForm();
		this.items = [this.searchForm];
		UserCostInputPanel.superclass.initComponent.call(this);
		Ext.getCmp("costCenterId").comboInitComponent();
	}
	
});
function validateMethods(){
		var money = Ext.getCmp("costAmount").getValue();
		if(money==''){
			alert("成本发生金额为必填项");
			return false;
		}else{
			return true;
		}
}
Ext.onReady(function() {
			Ext.QuickTips.init();
			var userCostInputPanel = new UserCostInputPanel();
			userCostInputPanel.render("userCostInputDiv");
			new Ext.Viewport({
						layout : 'fit',
						items : [userCostInputPanel]
					});
		});
