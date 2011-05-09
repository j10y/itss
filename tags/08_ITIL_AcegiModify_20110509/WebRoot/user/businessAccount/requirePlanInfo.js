/*
 * 收款panel
 */
function getSRIncomePanel() {
	var incomePanel = new Ext.form.FormPanel({
				frame : true,
				layout : "table",
				layoutConfig : {
					columns : 1
				},
				items : [{
							xtype : 'fieldset',
							title : '收款计划列表',
							items : this.getIncomeGridPanel(),
							scope : this
						}],
				buttons : [{
					text : "创建收款申请",
					iconCls : "submit",
					handler : function() {
						var record = Ext.getCmp('incomeGridPanel').getSelectionModel().getSelected();
						var records = Ext.getCmp('incomeGridPanel').getSelectionModel().getSelections();
						var temp = new Array();
						for (var i = 0; i < records.length; i++) {
							temp.push(records[i].data.id);
						}
						var planIds = Ext.encode(temp);
						//window.location = "incomeApply.jsp?requireId=" + this.dataId;
						window.location =  webContext + '/businessAccountAction_toIncomeApplyPage.action?requireId='
											+ this.dataId +"&"+"planIds="+planIds;
					},
					pressed : true,
					scope : this
				}, {
					text : "返 回",
					pressed : true,
					iconCls : "back",
					handler : function() {
						history.go(-1);
					},
					scope : this
				}]
			});
	return incomePanel;
}

function getIncomeGridPanel() {
	var incomeGridStore = new Ext.data.JsonStore({
				url : webContext
						+ '/businessAccountAction_getIncomePlanInfo.action?dataId='
						+ this.dataId,
				totalProperty : 'rowCount',
				root : 'data',
				id : 'id',
				fields : ["id", "name", "descn", "money", "startDate","endDate", "balance"]
			})
	var bbar = new Ext.PagingToolbar({
				autoShow : true,
				store : incomeGridStore,
				displayInfo : true,
				displayMsg : '当前显示 {0}-{1}条记录 /共{2}条记录',
				emptyMsg : "无显示数据"
			});
	var sm = new Ext.grid.CheckboxSelectionModel({
				singleSelect : false
			});
	var updateIncomePlan = function() {
		var _sm = Ext.getCmp('incomeGridPanel').getSelectionModel();
		if (_sm.getCount() != 1) {
			Ext.Msg.alert('提示', '只能选择一条记录');
			return;
		}
		var _record = _sm.getSelected();
		var updateIncomePlanPanel = new Ext.form.FormPanel({
			id : 'updateIncomePlanPanel',
			layout : 'table',
			width : 350,
			height : 360,
			frame : true,
			layoutConfig : {
				columns : 2
			},
			defaults : {
				bodyStyle : 'padding:4px'
			},
			buttonAlign : 'center',
			buttons : [{
				text : '确认',
				handler : function() {
					if (Ext.getCmp('updateIncomePlanPanel').form.isValid()) {
						var money = Ext.getCmp('uMoney').getValue();
						var startDate = Ext.getCmp('uStartDate').getRawValue();
						var endDate = Ext.getCmp('uEndDate').getRawValue();
						Ext.Ajax.request({
							url : webContext
									+ '/businessAccountAction_updateIncomePlan.action',
							method : 'POST',
							params : {
								id : _record.get('id'),
								money : money,
								startDate : startDate,
								endDate : endDate
							},
							success : function(response) {
								var res = response.responseText;
								res = Ext.decode(res);
								if (res.success) {
									Ext.Msg.alert('提示', '更新成功');
									Ext.getCmp('incomeGridPanel').store.reload();
									Ext.getCmp('updateIncomePlanwindow').close();
								} else {
									Ext.Msg.alert('提示', '更新失败,原因:'
													+ res.message);
								}
							},
							failure : function() {
								Ext.Msg.alert('提示', '更新失败');
							}
						});
					} else {
						Ext.Msg.alert('提示', '带红线的为必填项');
					}
				}
			}, {
				text : '取消',
				handler : function() {
					Ext.getCmp('updateIncomePlanwindow').close();
				}
			}],
			items : [{
						html : '<font style="font-size:13px">收款名称：</font>',
						border : false,
						style : 'width:140;text-align:right;'
					}, {
						html : '<font style="font-size:13px">'
								+ _record.get('name') + "</font>",
						style : 'width:160;text-align:left'
					}, {
						html : '<font style="font-size:13px;text-align:right">收款内容：</font>',
						border : false,
						style : 'width:140;text-align:right'
					}, {
						html : '<font style="font-size:13px">'
								+ _record.get('descn') + "</font>",
						style : 'width:160;text-align:left'
					}, {
						html : '<font style="font-size:13px;text-align:right">收款金额：</font>',
						border : false,
						style : 'width:140;text-align:right'
					}, {
						html : '<font style="font-size:13px">'
								+ _record.get('money') + "</font>",
						style : 'width:160;text-align:left'
					}, {
						html : '<font style="font-size:13px;text-align:right">计划开始时间：</font>',
						border : false,
						style : 'width:140;text-align:right'
					}, {
						html : '<font style="font-size:13px">'
								+ _record.get('startDate') + "</font>",
						style : 'width:160;text-align:left'
					}, {
						html : '<font style="font-size:13px;text-align:right">计划结束时间：</font>',
						border : false,
						style : 'width:140;text-align:right'
					}, {
						html : '<font style="font-size:13px">'
								+ _record.get('endDate') + "</font>",
						style : 'width:160;text-align:left'
					}, {
						html : '<hr>',
						colspan : 2
					}, {
						html : '<font style="font-size:13px;text-align:right;color:green">更新金额：</font>',
						border : false,
						style : 'width:140;text-align:right'
					}, {
						xtype : 'numberfield',
						id : 'uMoney',
						allowBlank : false,
						style : 'width:160;text-align:left'
					}, {
						html : '<font style="font-size:13px;text-align:right;color:green">更新开始时间：</font>',
						border : false,
						style : 'width:140;text-align:right'
					}, {
						xtype : 'datefield',
						id : 'uStartDate',
						allowBlank : false,
						format : 'Y-m-d',
						style : 'width:160;text-align:left'
					}, {
						html : '<font style="font-size:13px;text-align:right;color:green">更新结束时间：</font>',
						border : false,
						style : 'width:140;text-align:right'
					}, {
						id : 'uEndDate',
						xtype : 'datefield',
						allowBlank : false,
						format : 'Y-m-d',
						style : 'width:160;text-align:left'
					}]
		});

		var updateIncomePlanwindow = new Ext.Window({
					id : 'updateIncomePlanwindow',
					title : '更新新收款计划',
					width : 360,
					height : 410,
					layout : 'table',
					layoutConfig : {
						cloumns : 1
					},
					items : [updateIncomePlanPanel]
				});
		updateIncomePlanwindow.show();
	};
	var upDateButton = new Ext.Button({
				text : '更新收款计划',
				pressed : true,
				iconCls : 'add',
				scope : this,
				handler : updateIncomePlan
			});
	var showPlanHisButton = new Ext.Button({
		text : '查看更新历史',
		pressed : true,
		iconCls : 'search',
		scope : this,
		handler : function() {
			var _sm = Ext.getCmp('incomeGridPanel').getSelectionModel();
			if (_sm.getCount() != 1) {
				Ext.Msg.alert('提示', '只能选择一条记录');
				return;
			}
			var _record = _sm.getSelected();
			var incomeUpdateHisStore = new Ext.data.JsonStore({
				url : webContext
						+ '/businessAccountAction_getIncomeUpdatePlanHis.action?id='
						+ _record.get('id'),
				totalProperty : 'rowCount',
				root : 'data',
				id : 'id',
				fields : ["id", "updateMoney", "updateStartDate",
						"updateEndDate", "createUser", "modifyDate"]
			});
			var pageBar1 = new Ext.PagingToolbar({
						id : 'pagebar',
						pageSize : 10,
						store : incomeUpdateHisStore,
						displayInfo : true,
						displayMsg : '当前显示 {0}-{1}条记录 /共{2}条记录',
						emptyMsg : "无显示数据"
					});
//			this.formValue = '';
//			pageBar1.formValue = this.formValue;
			var incomeUpdateHisGrid = new Ext.grid.GridPanel({
						id : 'incomeUpdateHisGrid',
						width : 620,
						height : 280,
						frame : false,
						autoFill : true,
						collapsible : true,
						colModel : new Ext.grid.ColumnModel([new Ext.grid.RowNumberer(), 
								{header : "更新金额",dataIndex : "updateMoney",sortable : true,width : 130,align : "right"}, 
								{header : "更新开始时间",dataIndex : "updateStartDate",sortable : true,width : 120,align : "center"}, 
								{header : "更新结束时间",dataIndex : "updateEndDate",sortable : true,width : 120,align : "center"},
								{header : "修改人",dataIndex : "createUser",sortable : true,width : 100,align : "center"}, 
								{header : "修改时间",dataIndex : "modifyDate",sortable : true,width : 120,align : "center"},  
								{header : "自动编号",dataIndex : "id",hidden : true,width : 80,align : "center"}]),
						store : incomeUpdateHisStore,
						bbar : pageBar1
					});

			var incomeUpdateHisWindow = new Ext.Window({
						title : "<font style='font-size:12px;color:red'>收款名称：&nbsp;" + _record.get('name') + "</font>",
						width : 630,
						height : 330,
						layout : 'table',
						layoutConfig : {
							cloumns : 1
						},
						items : [incomeUpdateHisGrid]
					});
			incomeUpdateHisWindow.show();
			var param = {
				start : 0,
				'status' : 1
			};

//			pageBar1.formValue = param;
			incomeUpdateHisStore.on('beforeload', function(a) {   
					      Ext.apply(a.baseParams,param);   
					});


			incomeUpdateHisStore.removeAll();
			incomeUpdateHisStore.load({
						params : param
					});
		}
	});
	var showIncomebutton = new Ext.Button({
		text : '查看收款纪录',
		pressed : true,
		iconCls : 'search',
		scope : this,
		handler : function() {
			showIncomeHis();
		}
	});
	function showIncomeHis(){	
		var sm = new Ext.grid.CheckboxSelectionModel();
	   this.store = new Ext.data.JsonStore({ 
                    id: Ext.id(),
                    url : webContext+'/businessAccountAction_getAllIncomeHis.action?reqId='+this.dataId,
                    root:"data",
                    fields:[{name:'SRIncomePlan$id'},
                   			{name:'SRIncomePlan$name'},
                    		{name:'SRIncomePlan$descn'},
                    		{name:'SRIncomePlan$money'},
                    		{name:'SRIncomePlan$startDate'},
                    		{name:'SRIncomePlan$endDate'},
                    		{name:'itil_upDatePlan$id'},
                    		{name:'itil_upDatePlan$money'},
                    		{name:'itil_upDatePlan$startDate'},
                    		{name:'itil_upDatePlan$endDate'},
                    		{name:'itil_upDatePlan$descn'},
                    		{name:'itil_RealIncome$id'},
                    		{name:'itil_RealIncome$realDate'},
                    		{name:'itil_RealIncome$costCenter'},
                    		{name:'itil_RealIncome$realMoney'}],
                    totalProperty:"rowCount",
                    remoteSort:false,             
                   	timeout:8000
                    });
		var viewConfig = Ext.apply({
			forceFit : true
		}, this.gridViewConfig);
		this.formValue = '';
		this.allIncomeGrid= new Ext.grid.GridPanel({
			id : 'allIncomeGrid',
			store : this.store,
			sm : sm,
			columns:[sm,
					{header:'自动编号',dataIndex:'SRIncomePlan$id',align : 'left',sortable : true,hidden:true},
					{header:'收款名称',dataIndex:'SRIncomePlan$name',align : 'left',sortable : true,hidden:false},
					{header:'收款描述',dataIndex:'SRIncomePlan$descn',align : 'left',sortable : true,hidden:false},
					{header:'原收款金额',dataIndex:'SRIncomePlan$money',align : 'left',sortable : true,hidden:true},
					{header:'原开始时间',dataIndex:'SRIncomePlan$startDate',align : 'left',sortable : true,hidden:true},
					{header:'原结束时间',dataIndex:'SRIncomePlan$endDate',align : 'left',sortable : true,hidden:true},
					{header:'自动编号',dataIndex:'itil_upDatePlan$id',align : 'left',sortable : true,hidden:true},
					{header:'更新金额',dataIndex:'itil_upDatePlan$money',align : 'left',sortable : true,hidden:true},
					{header:'更新开始日期',dataIndex:'itil_upDatePlan$startDate',align : 'left',sortable : true,hidden:true},
					{header:'更新结束日期',dataIndex:'itil_upDatePlan$endDate',align : 'left',sortable : true,hidden:true},
					{header:'描述',dataIndex:'itil_upDatePlan$descn',align : 'left',sortable : true,hidden:true},
					{header:'自动编号',dataIndex:'itil_RealIncome$id',align : 'left',sortable : true,hidden:true},
					{header:'收款成本中心',dataIndex:'itil_RealIncome$costCenter',align : 'left',sortable : true,hidden:false},
					{header:'实际收款钱数',dataIndex:'itil_RealIncome$realMoney',align : 'left',sortable : true,hidden:false},
					{header:'实际收款时间',dataIndex:'itil_RealIncome$realDate',align : 'left',sortable : true,hidden:false}],
			trackMouseOver : false,
			loadMask : true,
			collapsible : true,
			autoScroll : true,
			loadMask : true,
			autoHeight : true,
			width : 800,// this.width - 15,
			frame : true
		});
		this.store.load();
		var allIncomeWindow = new Ext.Window({
					id : 'allIncomeWindow',
					title : '收款纪录',
					//closeAction : 'hide',
					maximizable : true,
					width : 600,
					height : 410,
					layout : 'table',
					layoutConfig : {
						cloumns : 1
					},
					items : [allIncomeGrid]
				});
		allIncomeWindow.show();
	}
	var incomeGridPanel = new Ext.grid.GridPanel({
				id : 'incomeGridPanel',
				width : 800,
				height : 200,
				frame : true,
				autoFill : true,
				sm : sm,
				collapsible : true,
				colModel : new Ext.grid.ColumnModel([sm, 
						{header : "收款名称",dataIndex : "name",sortable : true,width : 80,align : "center"}, 
						{header : "收款内容",dataIndex : "descn",sortable : true,width : 80,align : "center"}, 
						{header : "全部收款金额",dataIndex : "money",sortable : true,width : 80,align : "center"}, 
						{header : "计划开始时间",dataIndex : "startDate",sortable : true,width : 100,align : "center"}, 
						{header : "计划结束时间",dataIndex : "endDate",sortable : true,width : 100,align : "center"}, 
						{header : "剩余收款金额",dataIndex : "balance",sortable : true,width : 100,align : "center"},
						{header : "自动编号",dataIndex : "id",hidden : true,width : 80,align : "center"}]),
				store : incomeGridStore,
				tbar : [upDateButton, showPlanHisButton,showIncomebutton]

			});
	incomeGridStore.reload();
	incomeGridPanel.on('celldblclick', updateIncomePlan);
	return incomeGridPanel;
}

/*
 * 付款panel
 */
function getSRExpendPanel() {
	var expendPanel = new Ext.form.FormPanel({
				frame : true,
				layout : "table",
				layoutConfig : {
					columns : 1
				},
				items : [{
							xtype : 'fieldset',
							title : '付款计划列表',
							items : this.getExpendGridPanel(),
							scope : this
						}],
				buttons : [{
					text : "创建付款申请",
					iconCls : "submit",
					handler : function() {
						var record = Ext.getCmp('paymentGrid').getSelectionModel().getSelected();
						var records = Ext.getCmp('paymentGrid').getSelectionModel().getSelections();
						var temp = new Array();
						for (var i = 0; i < records.length; i++) {
							temp.push(records[i].data.id);
						}
						var planIds = Ext.encode(temp);
						//window.location = "incomeApply.jsp?requireId=" + this.dataId;
						window.location =  webContext + '/businessAccountAction_toExpendApplyPage.action?requireId='
											+ this.dataId +"&"+"planIds="+planIds;
						//window.location = "expendApply.jsp?requireId="+ this.dataId;

					},
					pressed : true,
					scope : this
				}, {
					text : "返 回",
					iconCls : "back",
					pressed : true,
					handler : function() {
						history.go(-1);
					},
					scope : this
				}]
			});
	return expendPanel;
}

function getExpendGridPanel() {
	var expendGridStore = new Ext.data.JsonStore({
		url : webContext
				+ '/businessAccountAction_getPaymentPlanInfo.action?dataId='
				+ this.dataId,
		totalProperty : 'rowCount',
		root : 'data',
		id : 'id',
		fields : ["id", "name", "descn", "money", "startDate","endDate", "balance"]
	})
	var updatePayment = function() {
		var _sm = Ext.getCmp('paymentGrid').getSelectionModel();
		if (_sm.getCount() != 1) {
			Ext.Msg.alert('提示', '只能选择一条记录');
			return;
		}
		var _record = _sm.getSelected();
		var updatePaymentPanel = new Ext.form.FormPanel({
			id : 'updatePaymentPanel',
			layout : 'table',
			width : 350,
			height : 360,
			frame : true,
			layoutConfig : {
				columns : 2
			},
			defaults : {
				bodyStyle : 'padding:4px'
			},
			buttonAlign : 'center',
			buttons : [{
				text : '确认',
				handler : function() {
					if (Ext.getCmp('updatePaymentPanel').form.isValid()) {
						var money = Ext.getCmp('uMoney1').getValue();
						var startDate = Ext.getCmp('uStartDate1').getRawValue();
						var endDate = Ext.getCmp('uEndDate1').getRawValue();
						Ext.Ajax.request({
							url : webContext
									+ '/businessAccountAction_updateExpendPlan.action',
							method : 'POST',
							params : {
								id : _record.get('id'),
								money : money,
								startDate : startDate,
								endDate : endDate
							},
							success : function(response) {
								var res = response.responseText;
								res = Ext.decode(res);
								if (res.success) {
									Ext.Msg.alert('提示', '更新成功');
									Ext.getCmp('paymentGrid').store.reload();
									Ext.getCmp('updatePaymentWindow').close();
								} else {
									Ext.Msg.alert('提示', '更新失败,原因:'
													+ res.message);
								}
							},
							failure : function() {
								Ext.Msg.alert('提示', '更新失败');
							}
						});
					} else {
						Ext.Msg.alert('提示', '带红线的为必填项');
					}
				}
			}, {
				text : '取消',
				handler : function() {
					Ext.getCmp('updatePaymentWindow').close();
				}
			}],
			items : [{
						html : '<font style="font-size:13px">收款名称：</font>',
						border : false,
						style : 'width:140;text-align:right;'
					}, {
						html : '<font style="font-size:13px">'
								+ _record.get('name') + "</font>",
						style : 'width:160;text-align:left'
					}, {
						html : '<font style="font-size:13px;text-align:right">收款内容：</font>',
						border : false,
						style : 'width:140;text-align:right'
					}, {
						html : '<font style="font-size:13px">'
								+ _record.get('descn') + "</font>",
						style : 'width:160;text-align:left'
					}, {
						html : '<font style="font-size:13px;text-align:right">收款金额：</font>',
						border : false,
						style : 'width:140;text-align:right'
					}, {
						html : '<font style="font-size:13px">'
								+ _record.get('money') + "</font>",
						style : 'width:160;text-align:left'
					}, {
						html : '<font style="font-size:13px;text-align:right">计划开始时间：</font>',
						border : false,
						style : 'width:140;text-align:right'
					}, {
						html : '<font style="font-size:13px">'
								+ _record.get('startDate') + "</font>",
						style : 'width:160;text-align:left'
					}, {
						html : '<font style="font-size:13px;text-align:right">计划结束时间：</font>',
						border : false,
						style : 'width:140;text-align:right'
					}, {
						html : '<font style="font-size:13px">'
								+ _record.get('endDate') + "</font>",
						style : 'width:160;text-align:left'
					}, {
						html : '<hr>',
						colspan : 2
					}, {
						html : '<font style="font-size:13px;text-align:right;color:green">更新金额：</font>',
						border : false,
						style : 'width:140;text-align:right'
					}, {
						xtype : 'numberfield',
						id : 'uMoney1',
						allowBlank : false,
						style : 'width:160;text-align:left'
					}, {
						html : '<font style="font-size:13px;text-align:right;color:green">更新开始时间：</font>',
						border : false,
						style : 'width:140;text-align:right'
					}, {
						xtype : 'datefield',
						id : 'uStartDate1',
						allowBlank : false,
						format : 'Y-m-d',
						style : 'width:160;text-align:left'
					}, {
						html : '<font style="font-size:13px;text-align:right;color:green">更新结束时间：</font>',
						border : false,
						style : 'width:140;text-align:right'
					}, {
						id : 'uEndDate1',
						xtype : 'datefield',
						allowBlank : false,
						format : 'Y-m-d',
						style : 'width:160;text-align:left'
					}]
		});

		var updatePaymentWindow = new Ext.Window({
					id : 'updatePaymentWindow',
					title : '更新新收款计划',
					closeAction : 'hide',
					width : 360,
					height : 410,
					layout : 'table',
					layoutConfig : {
						cloumns : 1
					},
					items : [updatePaymentPanel]
				});
		updatePaymentWindow.show();
	}
	var upDateButton = new Ext.Button({
				text : '更新付款计划',
				pressed : true,
				iconCls : 'add',
				scope : this,
				handler : updatePayment
			});
	var showPlanHisButton = new Ext.Button({
		text : '查看更新历史',
		pressed : true,
		iconCls : 'search',
		scope : this,
		handler : function() {
			var _sm = Ext.getCmp('paymentGrid').getSelectionModel();
			if (_sm.getCount() != 1) {
				Ext.Msg.alert('提示', '只能选择一条记录');
				return;
			}
			var _record = _sm.getSelected();
			var paymentStore = new Ext.data.JsonStore({
				url : webContext
						+ '/businessAccountAction_getExpendUpdatePlanHis.action?id='
						+ _record.get('id'),
				totalProperty : 'rowCount',
				root : 'data',
				id : 'id',
				fields : ["id", "updateMoney", "updateStartDate",
						"updateEndDate", "createUser", "modifyDate"]
			});
			var pageBar2 = new Ext.PagingToolbar({
						id : 'pagebar1',
						pageSize : 10,
						store : paymentStore,
						displayInfo : true,
						displayMsg : '当前显示 {0}-{1}条记录 /共{2}条记录',
						emptyMsg : "无显示数据"
					});
//			this.formValue = '';
//			pageBar2.formValue = this.formValue;
			var updatePaymentHisGrid = new Ext.grid.GridPanel({
						id : 'updatePaymentHisGrid',
						width : 620,
						height : 280,
						frame : false,
						autoFill : true,
						collapsible : true,
						colModel : new Ext.grid.ColumnModel([new Ext.grid.RowNumberer(), {
									header : "更新金额",
									dataIndex : "updateMoney",
									sortable : true,
									width : 130,
									align : "right"
								}, {
									header : "更新开始时间",
									dataIndex : "updateStartDate",
									sortable : true,
									width : 120,
									align : "center"
								}, {
									header : "更新结束时间",
									dataIndex : "updateEndDate",
									sortable : true,
									width : 120,
									align : "center"
								},{
									header : "修改人",
									dataIndex : "createUser",
									sortable : true,
									width : 100,
									align : "center"
								}, {
									header : "修改时间",
									dataIndex : "modifyDate",
									sortable : true,
									width : 120,
									align : "center"
								},  {
									header : "自动编号",
									dataIndex : "id",
									hidden : true,
									width : 80,
									align : "center"
								}]),
						store : paymentStore,
						bbar : pageBar2
					});

			var updatePaymentHisWindow = new Ext.Window({
						title : "<font style='font-size:12px;color:red'>付款名称：&nbsp;" + _record.get('name') + "</font>",
						width : 630,
						height : 330,
						layout : 'table',
						layoutConfig : {
							cloumns : 1
						},
						items : [updatePaymentHisGrid]
					});
			updatePaymentHisWindow.show();
			var param = {
				start : 0,
				'status' : 1
			};

//			pageBar2.formValue = param;
			paymentStore.on('beforeload', function(a) {   
					      Ext.apply(a.baseParams,param);   
					});


			paymentStore.removeAll();
			paymentStore.load({
						params : param
					});
		}
	});
	var shouPaymentHisbutton = new Ext.Button({
		text : '查看付款纪录',
		pressed : true,
		iconCls : 'search',
		scope : this,
		handler : function() {
			showPaymentHis();
		}
	});
	function showPaymentHis(){	
		
		var sm = new Ext.grid.CheckboxSelectionModel();
	   this.store = new Ext.data.JsonStore({ 
                    id: Ext.id(),
                    url : webContext+'/businessAccountAction_getAllPaymentHis.action?reqId='+this.dataId,
                    root:"data",
                    fields:[{name:'SRExpendPlan$id'},
                   			{name:'SRExpendPlan$name'},
                    		{name:'SRExpendPlan$descn'},
                    		{name:'SRExpendPlan$money'},
                    		{name:'SRExpendPlan$startDate'},
                    		{name:'SRExpendPlan$endDate'},
                    		{name:'itil_upDatePlan$id'},
                    		{name:'itil_upDatePlan$money'},
                    		{name:'itil_upDatePlan$startDate'},
                    		{name:'itil_upDatePlan$endDate'},
                    		{name:'itil_upDatePlan$descn'},
                    		{name:'itil_RealPayment$id'},
                    		{name:'itil_RealPayment$realDate'},
                    		{name:'itil_RealPayment$costCenter'},
                    		{name:'itil_RealPayment$realMoney'}],
                    totalProperty:"rowCount",
                    remoteSort:false,             
                   	timeout:8000
                    });
		var viewConfig = Ext.apply({
			forceFit : true
		}, this.gridViewConfig);
		this.formValue = '';
		this.allpaymentGrid= new Ext.grid.GridPanel({
			id : 'allPayment',
			store : this.store,
			sm : sm,
			columns:[sm,
					{header:'自动编号',dataIndex:'SRExpendPlan$id',align : 'left',sortable : true,hidden:true},
					{header:'付款名称',dataIndex:'SRExpendPlan$name',align : 'left',sortable : true,hidden:false},
					{header:'付款描述',dataIndex:'SRExpendPlan$descn',align : 'left',sortable : true,hidden:false},
					{header:'原付款金额',dataIndex:'SRExpendPlan$money',align : 'left',sortable : true,hidden:true},
					{header:'原开始时间',dataIndex:'SRExpendPlan$startDate',align : 'left',sortable : true,hidden:true},
					{header:'原结束时间',dataIndex:'SRExpendPlan$endDate',align : 'left',sortable : true,hidden:true},
					{header:'自动编号',dataIndex:'itil_upDatePlan$id',align : 'left',sortable : true,hidden:true},
					{header:'更新金额',dataIndex:'itil_upDatePlan$money',align : 'left',sortable : true,hidden:true},
					{header:'更新开始日期',dataIndex:'itil_upDatePlan$startDate',align : 'left',sortable : true,hidden:true},
					{header:'更新结束日期',dataIndex:'itil_upDatePlan$endDate',align : 'left',sortable : true,hidden:true},
					{header:'描述',dataIndex:'itil_upDatePlan$descn',align : 'left',sortable : true,hidden:true},
					{header:'自动编号',dataIndex:'itil_RealPayment$id',align : 'left',sortable : true,hidden:true},
					{header:'付款成本中心',dataIndex:'itil_RealPayment$costCenter',align : 'left',sortable : true,hidden:false},
					{header:'实际付款钱数',dataIndex:'itil_RealPayment$realMoney',align : 'left',sortable : true,hidden:false},
					{header:'实际付款时间',dataIndex:'itil_RealPayment$realDate',align : 'left',sortable : true,hidden:false}],
			trackMouseOver : false,
			loadMask : true,
			collapsible : true,
			autoScroll : true,
			loadMask : true,
			autoHeight : true,
			width : 800,// this.width - 15,
			frame : true
		});
		this.store.load();
		var _window = new Ext.Window({
					id : '_window',
					title : '付款纪录',
					closeAction : 'hide',
					width : 600,
					height : 410,
					layout : 'table',
					layoutConfig : {
						cloumns : 1
					},
					items : [allpaymentGrid]
				});
		_window.show();
	}
	var bbar = new Ext.PagingToolbar({
				autoShow : true,
				store : expendGridStore,
				displayInfo : true,
				displayMsg : '当前显示 {0}-{1}条记录 /共{2}条记录',
				emptyMsg : "无显示数据"
			});
	var sm = new Ext.grid.CheckboxSelectionModel({
				singleSelect : false
			});
	var gridPanel = new Ext.grid.EditorGridPanel({
				id : 'paymentGrid',
				width : 800,
				height : 200,
				frame : true,
				autoFill : true,
				sm : sm,
				collapsible : true,
				colModel : new Ext.grid.ColumnModel([sm, 
						{header : "付款名称",dataIndex : "name",sortable : true,width : 80,align : "center"}, 
						{header : "付款内容",dataIndex : "descn",sortable : true,width : 80,align : "center"}, 
						{header : "全部付款金额",dataIndex : "money",sortable : true,width : 80,align : "center"}, 
						{header : "计划开始时间",dataIndex : "startDate",sortable : true,width : 100,align : "center"}, 
						{header : "计划结束时间",dataIndex : "endDate",sortable : true,width : 100,align : "center"}, 
						{header : "剩余付款金额",dataIndex : "balance",sortable : true,width : 100,align : "center"},
						{header : "自动编号",dataIndex : "id",hidden : true,width : 80,align : "center"}]),
				store : expendGridStore,
				tbar : [upDateButton, showPlanHisButton,shouPaymentHisbutton]

			});
	gridPanel.on('rowdblclick', updatePayment);
	expendGridStore.reload();
	return gridPanel;
}

function getFormpanel_SpecialRequire3_Input() {
	var da = new DataAction();
	var biddata = "";
	var data = da.getSingleFormPanelElementsForEdit(
			"panel_SpecialRequire_Input", this.dataId);
	for (i = 0; i < data.length; i++) {
		if (data[i].xtype == 'combo' || data[i].xtype == 'datefield') {
			data[i].id = data[i].id + 8;// 改变Combobox的id
			data[i].readOnly = true;
			data[i].hideTrigger = true;
		}
		data[i].readOnly = true;
	}
	biddata = da.split(data);
	var formpanel_SpecialRequire3_Input = new Ext.form.FormPanel({
				id : 'panel_SpecialRequire3_Input',
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
				title : "申请",// "解决方案设计开发服务",
				items : biddata
			});
	return formpanel_SpecialRequire3_Input;
}

//需求分析面吧
function getFormpanel_SRAnalyse() {
	var sra = new SRAction();
	var praId = sra.getProjectRequireAnalyseId(this.dataId);
	var da = new DataAction();
	var data = null;
	// 判断是新增还是修改
	var biddata = "";
	if (praId != '0') {
		data = da.getSingleFormPanelElementsForEdit("panel_SRAnalyse", praId);// 这是要随时变得
	} else {
		data = da.getSingleFormPanelElementsForEdit("panel_SRAnalyse", "");
	}
	biddata = da.splitForReadOnly(data);
	var formpanel_SRAnalyse = new Ext.form.FormPanel({
		id : 'panel_SRAnalyse',
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
		title : "项目需求分析",
		items : biddata
	});
	return formpanel_SRAnalyse;
}
//需求合同面板
function getFormpanel_SRprojectContracts() {
	var sra = new SRAction();
	var rcId = sra.getRequirementContractId(this.dataId);
	var da = new DataAction();
	var data = null;// 判断是新增还是修改
	var biddata = "";
	if (rcId != '0') {
		data = da.getSingleFormPanelElementsForEdit("panel_SRprojectContracts",rcId);// 这是要随时变得
	} else {
		data = da.getSingleFormPanelElementsForEdit("panel_SRprojectContracts","");
	}
	biddata = da.splitForReadOnly(data);
	var formpanel_SRprojectContracts = new Ext.form.FormPanel({
		id : 'panel_SRprojectContracts',
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
		title : "个性化需求合同",
		items : biddata
	});
	return formpanel_SRprojectContracts;
}

/*
 * tabPanel
 */
function getTabPanel() {
	var tab = new Ext.TabPanel({
				title : "需求基本信息",
				deferredRender : false,
				activeTab : "draft",
				frame : true,
				plain : true,
				width : Ext.get('gridWidth').getWidth() - 30,
				defaults : {
					autoHeight : true
				},
				items : [{
							title : "需求基本信息",
							width : "auto",
							items : [this.getFormpanel_SpecialRequire3_Input()]
						},{
							title : "需求分析信息",
							width : "auto",
							items : [this.getFormpanel_SRAnalyse()]
						},{
							title : "需求合同信息",
							width : "auto",
							items : [this.getFormpanel_SRprojectContracts()]
						},{
							title : "收款信息",
							width : "auto",
							id : "draft",
							items : [this.getSRIncomePanel()]
						}, {
							title : "付款信息",
							width : "auto",
							items : [this.getSRExpendPanel()]
						}

				]
			});
	return tab;
}