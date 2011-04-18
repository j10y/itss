ProjectCreatePanel = Ext.extend(Ext.Panel, {
	id : "costHandInputCreatePanel",
	height : 'auto',
	width : 'auto',
	layout : 'fit',
	frame : true,
	items : this.items,

	/*
	 * 获得主formpanel
	 */
	getMainForm : function() {
			auditPersonIitems = [{
				html : '审批人:<font color=\"red\">*</font>:&nbsp;',
				cls : 'common-text',
				style : 'width:100;text-align:right',
				baseCls: "x-plain"//这样就背景色就变成了蓝色替换了白色
			}, {
				id : 'auditPersonItcodeId',
				xtype : 'textfield',
				width : 100,
				allowBlank : false,
				listeners : {
					blur : function(field) {
					if(Ext.getCmp("auditPersonItcodeId").getValue().trim()=="") {
						Ext.getCmp("auditPersonId").setValue("");
						document.getElementById("auditPersonRealName").innerHTML = "<font color=red>【您尚未填写审批人itcode】</font>";
						return true;
					}
					Ext.Ajax.request({
						url : webContext
								+ '/CostHandInputAction_getUserInfoByItcode.action',
						params : {
							itcode : Ext.getCmp("auditPersonItcodeId").getValue()
						},
						method : 'post',
						success : function(response, option) {
							var res = Ext.decode(response.responseText);
							if(res.success) {
								Ext.getCmp("auditPersonId").setValue(res.auditPersonId);
								document.getElementById("auditPersonRealName").innerHTML = "<font color=green>【"+res.auditPersonRealName+"】</font>";
							} else {
								Ext.getCmp("auditPersonId").setValue("");
								document.getElementById("auditPersonRealName").innerHTML = "<font color=red>【"+"查无此人，请联系管理员添加"+"】</font>";
							}
						},
						failure : function() {
							Ext.Msg.alert('提示','当前用户信息获取失败');
						}
					}); 
					return true;
				}
				},
				enableKeyEvents : true,
				validationEvent : 'keypress',
				validator : function() {
					if(Ext.getCmp("auditPersonItcodeId").getValue().trim()=="") {
						Ext.getCmp("auditPersonId").setValue("");
						document.getElementById("auditPersonRealName").innerHTML = "<font color=red>【您尚未填写审批人itcode】</font>";
						return true;
					}
					Ext.Ajax.request({
						url : webContext
								+ '/CostHandInputAction_getUserInfoByItcode.action',
						params : {
							itcode : Ext.getCmp("auditPersonItcodeId").getValue()
						},
						method : 'post',
						success : function(response, option) {
							var res = Ext.decode(response.responseText);
							if(res.success) {
								Ext.getCmp("auditPersonId").setValue(res.auditPersonId);
								document.getElementById("auditPersonRealName").innerHTML = "<font color=green>【"+res.auditPersonRealName+"】</font>";
							} else {
								Ext.getCmp("auditPersonId").setValue("");
								document.getElementById("auditPersonRealName").innerHTML = "<font color=red>【"+"查无此人，请联系管理员添加"+"】</font>";
							}
						},
						failure : function() {
							Ext.Msg.alert('提示','当前用户信息获取失败');
						}
					}); 
					return true;
				}
			}, {
				id : 'auditPersonRealName',
				html : '<font color=red>【您尚未填写审批人itcode】</font>',
				cls : 'common-text',
				style : 'width:180;text-align:left',
				baseCls: "x-plain"//这样就背景色就变成了蓝色替换了白色
			}, {
				id : 'auditPersonId',
				xtype : 'hidden'
			}];
		var costReduceTypeStore = new Ext.data.SimpleStore({
				fields : ['value', 'id'],
				data : [['配置项', "1"], ['服务项', "2"]]
		});
	
		var borrowTypesStore = new Ext.data.SimpleStore({
				fields : ['value', 'id'],
				data : [['直接报销', "1"], ['借款', "2"], ['借款后清帐', "3"]]
		});

		var configItemStore = new Ext.data.JsonStore({
			url : webContext
					+ '/CostHandInputAction_findItem.action',
			fields : ['itemCode', 'name'],
			totalProperty : 'rowCount',
			root : 'data',
			id : 'name'
		});
		
		var financeCostTypeStore = new Ext.data.JsonStore({
			url : webContext
					+ '/CostHandInputAction_findCostType.action',
			fields : ['id', 'value'],
			totalProperty : 'rowCount',
			root : 'data',
			listeners : {
					beforeload : function(store, opt) {
						var param = Ext
								.getCmp('financeCostTypeId').defaultParam;
						if (opt.params['propertyValue'] == undefined) {
							opt.params['propertyValue'] = unicode(param);
						}
					}
			},
			id : 'id'
		});
		
		var reimbursementStore = new Ext.data.JsonStore({
			url : webContext
					+ '/CostHandInputAction_findReimbursement.action',
			fields : ['id', 'realName'],
			totalProperty : 'rowCount',
			root : 'data',
			listeners : {
					beforeload : function(store, opt) {
						var param = Ext
								.getCmp('reimbursementId').defaultParam;
						if (opt.params['propertyValue'] == undefined) {
							opt.params['propertyValue'] = unicode(param);
						}
					}
			},
			id : 'id'
		});
		
		var costCenterStore = new Ext.data.JsonStore({
			url : webContext
					+ '/CostHandInputAction_findFinanceCostCenter.action',
			fields : ['id', 'value'],
			totalProperty : 'rowCount',
			root : 'data',
			listeners : {
					beforeload : function(store, opt) {
						var param = Ext
								.getCmp('costCenterId').defaultParam;
						if (opt.params['propertyValue'] == undefined) {
							opt.params['propertyValue'] = unicode(param);
						}
					}
			},
			id : 'id'
		});
		
		
		
		var searchForm = new Ext.form.FormPanel({
			title : '成本录入',
			id : "formPanel",
			collapsible : true,
			frame : true,
			autoScroll : true,
			layout : "table",
			defaults : {
				height : 30
			},
			layoutConfig : {
				columns : 1
			},
			items : [{
				xtype : 'fieldset',
				title : '成本基本信息录入',
				layout : 'table',
				layoutConfig : {
					columns : 4
				},
				defaults : {
					bodyStyle : 'padding:8px 0px 8px 0px'
				},
				items : [
						{
							html : "成本归结类型<font color=\"red\">*</font>:&nbsp;",
							cls : 'common-text',
							style : 'width:150;text-align:right'
						}, {
							xtype : 'combo',
							readOnly : false,
							id : "costReduceTypeId",
							hiddenName : "costReduceType",
							fieldLabel : '成本归结类型',
							store : costReduceTypeStore,
							displayField : 'value',
							valueField : "id",
							allowBlank : false,
							triggerAction : "all",
							emptyText : "请选择",
							mode : 'local',
							value : 1,
							listeners : {
								"select" : function() {
									var tet = Ext.get("costReduceTypeId").dom.value;
									if (tet == "配置项") {
										Ext.getCmp('configItemId').setRawValue("");
										Ext.fly('configItem').dom.innerHTML='配置项<font color=\"red\">*</font>:&nbsp;';
									} else {
										Ext.getCmp('configItemId').setRawValue("");
										Ext.fly('configItem').dom.innerHTML='服务项<font color=\"red\">*</font>:&nbsp;';
										
									}
								},
								'blur' :  function(combo){
										var nowVal = combo.getRawValue();
										if(nowVal!=""&&nowVal!="配置项"&&nowVal!="服务项"){
											combo.setValue("");
											Ext.Msg.alert("提示信息","不能直接输入，请查询后选择！");
										}
								}
							},
							width : 200
						}, {
							html : "配置项<font color=\"red\">*</font>:&nbsp;",
							cls : 'common-text',
							id:'configItem',
							style : 'width:150;text-align:right'
						}, {
							xtype : 'combo',
							id : "configItemId",
							hiddenName : "configItem",
							fieldLabel : '配置项',
							displayField : 'name',
							valueField : "itemCode",
							pageSize : 10,
							allowBlank : false,
							defaultParam : "",
							triggerAction : "all",
							store : configItemStore,
							emptyText : "请选择",
							listeners : {
								beforequery  :function(queryEvent){
									var item=Ext.getCmp('costReduceTypeId').getValue();
									if(item==""){
									   Ext.Msg.alert("提示",'请选择成本归结类型!');
									}else{
										queryEvent.combo.store.baseParams={
											item:item,
											propertyValue : queryEvent.query
										};
										queryEvent.combo.store.reload();
									}
									return false;
								},
								'blur' :  function(combo){
									var nowVal = combo.getRawValue();
									var nowId = combo.getValue();
									if(nowVal==""){
										combo.setValue("")
									}else if(nowId==""){
										combo.setValue("");
										Ext.Msg.alert("提示信息","不能直接输入，请查询后选择！");
									}
								}
							},
							width : 200
						},{
							html : "费用类型<font color=\"red\">*</font>:&nbsp;",
							cls : 'common-text',
							style : 'width:150;text-align:right'
						}, {
							xtype : 'combo',
							readOnly : false,
							id : "financeCostTypeId",
							hiddenName : "financeCostType",
							fieldLabel : '费用类型',
							store : financeCostTypeStore,
							displayField : 'value',
							valueField : "id",
							defaultParam : "",
							pageSize : 10,
							allowBlank : false,
							triggerAction : "all",
							emptyText : "请选择",
							listeners : {
								'beforequery' : function(queryEvent) {
									var query = queryEvent.query;
									this.defaultParam = query;
									this.store.load({
												params : {
													propertyValue : query,
													start : 0
												}
											});
								},
								'blur' :  function(combo){
									var nowVal = combo.getRawValue();
									var nowId = combo.getValue();
									if(nowVal==""){
										combo.setValue("")
									}else if(nowId==""){
										combo.setValue("");
										Ext.Msg.alert("提示信息","不能直接输入，请查询后选择！");
									}
								}
							},
							width : 200
						},{
							html : "成本发生金额<font color=\"red\">*</font>:&nbsp;",
							cls : 'common-text',
							style : 'width:150;text-align:right'
						},{
							id : "costAmount",
							xtype : 'textfield',
							fieldLabel : '成本发生金额',
							allowBlank : false,
							listeners : {
								'blur' : function(obj, e) {
									var money = Ext.getCmp("costAmount")
											.getValue();
									if (!/^(\d*)(\,|\d|\.)*$/.test(money)
											|| money == "") {
										return;
									}
									var arr = money.split(",");
									money = "";
									for (var i = 0; i < arr.length; i++) {
										money = money + arr[i];
									}
									Ext.getCmp('costAmount').setValue(Ext.util.Format.usMoney(money).substring(1,Ext.util.Format.usMoney(money).length));
								}
							},
							height : 0,
							width : 200
						},{
							html : "报销人:&nbsp;",
							cls : 'common-text',
							style : 'width:150;text-align:right'
						},{
							xtype : 'combo',
							id : 'reimbursementId',
							hiddenName : 'reimbursement',
							fieldLabel : '报销人',
							store : reimbursementStore,
							pageSize : 10,
							displayField : 'realName',
							valueField : 'id',
							defaultParam : "",
							triggerAction : 'all',
							width : 200,
							emptyText : '请选择',
							listeners : {
								'beforequery' : function(queryEvent) {
									var query = queryEvent.query;
									this.defaultParam = query;
									this.store.load({
												params : {
													propertyValue : query,
													start : 0
												}
											});
								},
								"blur" : function(combo){
									var nowVal = combo.getRawValue();
									var nowId = combo.getValue();
									if(nowVal==""){
										combo.setValue("")
									}else if(nowId==""){
										combo.setValue("");
										Ext.Msg.alert("提示信息","不能直接输入，请查询后选择！");
									}
								}
							}
						},{
							html : "服务提供商:&nbsp;",
							cls : 'common-text',
							style : 'width:150;text-align:right'
						},{
							id : "serviceProvider",
							xtype : 'textfield',
							fieldLabel : '服务提供商',
							allowBlank : true,
							height : 0,
							width : 200
						},{
							html : "成本中心<font color=\"red\">*</font>:&nbsp;",
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
							displayField : 'value',
							valueField : "id",
							triggerAction : "all",
							emptyText : "请选择",
							blankText : '该选项为必填项',
							width : 200,
							store : costCenterStore,
							listeners : {
								'beforequery' : function(queryEvent) {
									var param = queryEvent.query;
									this.defaultParam = param;
									this.store.load({
												params : {
													propertyValue : param,
													start : 0
												}
											});
								},
								"blur" : function(combo){
									var nowVal = combo.getRawValue();
									var nowId = combo.getValue();
									if(nowVal==""){
										combo.setValue("")
									}else if(nowId==""){
										combo.setValue("");
										Ext.Msg.alert("提示信息","不能直接输入，请查询后选择！");
									}
								}
							}	
						},{
							html : "费用发生日期<font color=\"red\">*</font>:&nbsp;",
							cls : 'common-text',
							style : 'width:150;text-align:right'
						}, {
							xtype : 'datefield',
							fieldLabel : '费用发生日期',
							id : "costDate",
							format : "Y-m-d",
							allowBlank : false,
							disabled : false,
							width : 200
						},{
							html : "借款标记:&nbsp;",
							cls : 'common-text',
							style : 'width:150;text-align:right'
						}, {
							xtype : 'combo',
							readOnly : false,
							id : "borrowTypeId",
							hiddenName : "borrowType",
							fieldLabel : '借款标记',
							store : borrowTypesStore,
							displayField : 'value',
							valueField : "id",
							allowBlank : true,
							triggerAction : "all",
							emptyText : "请选择",
							mode : 'local',
							listeners : {
								"blur" : function(combo){
									var nowVal = combo.getRawValue();
									if(nowVal!=""&&nowVal!="直接报销"&&nowVal!="借款"&&nowVal!="借款后清帐"){
										combo.setValue("");
										Ext.Msg.alert("提示信息","不能直接输入，请查询后选择！");
									}
								}
							},
							width : 200
						},{
							html : "借款日期:&nbsp;",
							cls : 'common-text',
							style : 'width:150;text-align:right'
						}, {
							xtype : 'datefield',
							fieldLabel : '借款日期',
							id : "borrowDate",
							format : "Y-m-d",
							disabled : false,
							width : 200
						},{
							html : '明细说明:&nbsp',
							cls : 'common-text',
							style : 'width:150;text-align:right'
						}, {
							xtype : 'textarea',
							id : 'CostDetailExplanation',
							width : 550,
							allowBlank : true,
							colspan : 3
						}
				]
			}, {
				xtype : 'fieldset',
				title : '审批人',
				layout : 'table',
				layoutConfig : {
					columns : 4
				},
				defaults : {
					bodyStyle : 'padding:8px 0px 8px 0px',
					overflow : 'auto'
				},
				items : auditPersonIitems,
				scope : this
			}],
			buttons : [
					{
						text : "保存",
						pressed : true,
						iconCls : 'save',
						handler : this.formSave
					}, {
						text : "返 回",
						pressed : true,
						iconCls : 'back',
						handler : function() {
								Ext.MessageBox.confirm("确认信息", "确认要返回？", function(tes) {
									if (tes == "yes") {
										history.go(-1);
									}
								});
						}
					}],
			buttonAlign : 'center'
		});
		return searchForm;
	},
	
	formSave : function() {
		if (Ext.getCmp("formPanel").form.isValid()) {
			var formParam = Ext.encode(getFormParam("formPanel"));
			Ext.Ajax.request({
					url : webContext + '/CostHandInputAction_save.action',
					params : {
						formParam : formParam
					},
					success : function(response, options) {
						var resultJson = Ext.util.JSON
								.decode(response.responseText);
						if(resultJson.success){
							Ext.Msg.alert('提示信息', '保存成功!');
//							window.location = "../search/financeCostSchedules.jsp";
						}
					},
					failure : function() {
						Ext.Msg.alert('提示信息', '保存失败');
					}
			});
		} else {
			Ext.Msg.alert('提示信息', '带红线处为必填项');
		}

	},
	
	initComponent : function() {
		this.searchForm = this.getMainForm();
		this.items = [this.searchForm];
		ProjectCreatePanel.superclass.initComponent.call(this);
	}
});

function unicode(s) {
	var len = s.length;
	var rs = "";
	for (var i = 0; i < len; i++) {
		var k = s.substring(i, i + 1);
		rs += "&#" + s.charCodeAt(i) + ";";
	}
	return rs;
};
function runicode(s) {
          var k = s.split(";");
          var r = "";
          for (var x = 0; x < k.length; x++) {
                    var m = k[x].replace(/&#/, "");
                    r += String.fromCharCode(m);
          }
          return r;
};

Ext.onReady(function() {
			Ext.QuickTips.init();
			var projectCreatePanel = new ProjectCreatePanel();
			projectCreatePanel.render("costHandInputDiv");
			new Ext.Viewport({
						layout : 'fit',
						items : [projectCreatePanel]
					});
});
