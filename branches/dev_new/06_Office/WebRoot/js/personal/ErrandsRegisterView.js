Ext.ns("ErrandsRegisterView");
var ErrandsRegisterView = function() {
	return new Ext.Panel(
			{
				id : "ErrandsRegisterView",
				title : "请假单列表",
				iconCls : "menu-holiday",
				autoScroll : true,
				items : [
						new Ext.FormPanel(
								{
									id : "ErrandsRegisterSearchForm",
									height : 40,
									frame : false,
									border : false,
									layout : "hbox",
									layoutConfig : {
										padding : "5",
										align : "middle"
									},
									defaults : {
										xtype : "label",
										margins : {
											top : 0,
											right : 4,
											bottom : 4,
											left : 4
										}
									},
									items : [
											{
												text : "查询条件:"
											},
											{
												text : "开始时间:从"
											},
											{
												xtype : "datetimefield",
												format : "Y-m-d H:i:s",
												name : "Q_startTime_D_GE",
												editable : false
											},
											{
												text : "到"
											},
											{
												xtype : "datetimefield",
												format : "Y-m-d H:i:s",
												name : "Q_endTime_D_LE",
												editable : false
											},{
												text : "请假类型:"
											},
											{

												fieldLabel : "请假类型",
												hiddenName : "Q_leaveTypeId_L_EQ",
												emptyText : "请选择请假类型",
												xtype : "combo",
												mode : "local",
												anchor : "98%",
												width : 80,
												allowBlank : true,
												editable : false,
												valueField : "typeId",
												displayField : "typeName",
												triggerAction : "all",
												store : new Ext.data.SimpleStore({
													autoLoad : true,
													url : __ctxPath + "/personal/comboLeaveType.do",
													fields : [ "typeId", "typeName", "processDefId"]
												})
											
											},
											{
												text : "审批状态:"
											},
											{
												xtype : "combo",
												hiddenName : "Q_status_SN_EQ",
												mode : "local",
												width : 80,
												editable : false,
												triggerAction : "all",
												store : [ [ "1", "待部门负责人审批" ],
															[ "2", "待分管领导审批" ],
															[ "3", "待主管领导审批" ],
															[ "4", "待人事登记" ],
															[ "5", "审批通过" ] ]
											},
											{
												xtype : "button",
												text : "查询",
												iconCls : "search",
												handler : function() {
													var a = Ext
															.getCmp("ErrandsRegisterSearchForm");
													var b = Ext
															.getCmp("ErrandsRegisterGrid");
													if (a.getForm().isValid()) {
														a
																.getForm()
																.submit(
																		{
																			waitMsg : "正在提交查询",
																			params : {
																				Q_flag_SN_EQ : 1
																			},
																			url : __ctxPath
																					+ "/personal/listErrandsRegister.do",
																			success : function(
																					d,
																					e) {
																				var c = Ext.util.JSON
																						.decode(e.response.responseText);
																				b
																						.getStore()
																						.loadData(
																								c);
																			}
																		});
													}
												}
											},
											{
												xtype : "button",
												text : "重置",
												iconCls : "btn-reseted",
												handler : function() {
													var a = Ext
															.getCmp("ErrandsRegisterSearchForm");
													a.getForm().reset();
												}
											} ]
								}), this.setup() ]
			});
};
ErrandsRegisterView.prototype.setup = function() {
	return this.grid();
};
ErrandsRegisterView.prototype.grid = function() {
	var d = new Ext.grid.CheckboxSelectionModel();
	var a = new Ext.grid.ColumnModel(
			{
				columns : [
						d,
						new Ext.grid.RowNumberer(),
						{
							header : "dateId",
							dataIndex : "dateId",
							hidden : true
						},
						{
							header : "描述",
							dataIndex : "descp"
						},
						{
							header : "请假类型",
							dataIndex : "leaveTypeName"
						},
						{
							header : "开始日期",
							dataIndex : "startTime"
						},
						{
							header : "结束日期",
							dataIndex : "endTime"
						},
						{
							header : "审批状态",
							dataIndex : "status",
							renderer : function(e) {
								if (e == "1") {
									return "<font color='red'>待部门负责人审批</font>";
								}
								if (e == "2") {
									return "<font color='red'>待分管领导审批</font>";
								}
								if (e == "3") {
								    return '<font color="red">待主管领导审批</font>';
								}
								if (e == "4") {
									return '<font color="red">待人事登记</font>';
								}
								if (e == "5") {
									return '<font color="green">审批通过</font>';
								}
								if (e == "6") {
									return '<font color="red">未通过</font>';
								}
							}
						},
						{
							header : "管理",
							dataIndex : "dateId",
							width : 50,
							sortable : false,
							renderer : function(h, g, e, k, f) {
								var j = e.data.dateId;
//								var i = '<button title="删除" value=" " class="btn-del" onclick="ErrandsRegisterView.remove('
//										+ j + ')">&nbsp;&nbsp;</button>';
//								i += '&nbsp;<button title="详细" value=" " class="btn-showDetail" onclick="ErrandsRegisterView.detail('
//										+ j + ')">&nbsp;&nbsp;</button>';
								var i = '<button title="详细" value=" " class="btn-showDetail" onclick="ErrandsRegisterView.detail('
										+ j + ')">&nbsp;&nbsp;</button>';
								return i;
							}
						} ],
				defaults : {
					sortable : true,
					menuDisabled : false,
					width : 100
				}
			});
	var b = this.store();
	b.load({
		params : {
			start : 0,
			limit : 25
		}
	});
	var c = new Ext.grid.GridPanel({
		id : "ErrandsRegisterGrid",
		tbar : this.topbar(),
		store : b,
		trackMouseOver : true,
		disableSelection : false,
		loadMask : true,
		autoHeight : true,
		cm : a,
		sm : d,
		viewConfig : {
			forceFit : true,
			enableRowBody : false,
			showPreview : false
		},
		bbar : new Ext.PagingToolbar({
			pageSize : 25,
			store : b,
			displayInfo : true,
			displayMsg : "当前显示从{0}至{1}， 共{2}条记录",
			emptyMsg : "当前没有记录"
		})
	});
	c.addListener("rowdblclick", function(g, f, h) {
		g.getSelectionModel().each(function(e) {
			ErrandsRegisterView.detail(e.data.dateId);
		});
	});
	return c;
};
ErrandsRegisterView.prototype.store = function() {
	var a = new Ext.data.Store({
		proxy : new Ext.data.HttpProxy({
			url : __ctxPath + "/personal/listErrandsRegister.do"
		}),
		baseParams : {
			"Q_flag_SN_EQ" : 1
		},
		reader : new Ext.data.JsonReader({
			root : "result",
			totalProperty : "totalCounts",
			id : "id",
			fields : [ {
				name : "dateId",
				type : "int"
			}, {
				name : "userName",
				mapping : "appUser.fullname"
			}, "descp", "startTime", "endTime", "approvalId", "status",
					"approvalOption", "approvalName", "flag", "leaveTypeName" ]
		}),
		remoteSort : true
	});
	a.setDefaultSort("dateId", "desc");
	return a;
};
ErrandsRegisterView.prototype.topbar = function() {
	var a = new Ext.Toolbar({
		id : "ErrandsRegisterFootBar",
		height : 30,
		bodyStyle : "text-align:left",
		items : [ {
			iconCls : "btn-add",
			text : "添加请假单",
			xtype : "button",
			handler : function() {
				new ErrandsRegisterForm();
			}
		}, {
			iconCls : "btn-del",
			text : "删除请假单",
			xtype : "button",
			handler : function() {
				var d = Ext.getCmp("ErrandsRegisterGrid");
				var b = d.getSelectionModel().getSelections();
				if (b.length == 0) {
					Ext.ux.Toast.msg("信息", "请选择要删除的记录！");
					return;
				}
				var e = Array();
				for ( var c = 0; c < b.length; c++) {
					e.push(b[c].data.dateId);
				}
				ErrandsRegisterView.remove(e);
			}
		} ]
	});
	return a;
};
ErrandsRegisterView.remove = function(b) {
	var a = Ext.getCmp("ErrandsRegisterGrid");
	Ext.Msg.confirm("信息确认", "您确认要删除该记录吗？", function(c) {
		if (c == "yes") {
			Ext.Ajax.request({
				url : __ctxPath + "/personal/multiDelErrandsRegister.do",
				params : {
					ids : b
				},
				method : "post",
				success : function() {
					Ext.ux.Toast.msg("信息提示", "成功删除所选记录！");
					a.getStore().reload({
						params : {
							start : 0,
							limit : 25
						}
					});
				}
			});
		}
	});
};
ErrandsRegisterView.detail = function(a) {
	new ErrandsRegisterDetail(a);
};