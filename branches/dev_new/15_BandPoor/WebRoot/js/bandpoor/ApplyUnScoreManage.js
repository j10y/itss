ApplyUnScoreManage = Ext.extend(Ext.Panel, {
		searchPanel : null,
		gridPanel : null,
		store : null,
		topbar : null,
		constructor : function (a) {
			Ext.applyIf(this, a);
			this.initUIComponents();
			ApplyUnScoreManage.superclass.constructor.call(this, {
				id : "ApplyUnScoreManage",
				iconCls : "menu-dictionary",
				title : "不可评分品牌池",
				region : "center",
				layout : "border",
				items : [this.searchPanel, this.gridPanel]
			});
		},
		initUIComponents : function () {
			this.searchPanel = new Ext.FormPanel({
					height : 35,
					region : "north",
					frame : false,
					border : false,
					layout : "hbox",
					layoutConfig : {
						padding : "5",
						align : "middle"
					},
					id : "ApplyUnScoreManageSearchForm",
					defaults : {
						xtype : "label",
						style : "padding:0px 5px 0px 5px;"
					},
					items : [{
							text : "请输入查询条件:"
						}, {
							text : "品牌"
						}, {
							id : "ApplyUnScoreManageSearchFormBandName",
							width : 120,
							name : "Q_bandName_S_LK",
							maxHeight : 200,
							xtype : "combo",
							mode : "local",
							editable : true,
							triggerAction : "all",
							valueField : "fromBandId",
							displayField : "fromBandName",
							store : new Ext.data.SimpleStore(
							{
								url : __ctxPath
										+ "/bandpoor/getBandsScoreManageUn.do",
								fields : [
										"fromBandId",
										"fromBandName"]
							}),
							listeners : {
								focus : function (e) {
									var d = Ext.getCmp("ApplyUnScoreManageSearchFormBandName").getStore();
									if (d.getCount() <= 0) {
										Ext.Ajax.request({
											url : __ctxPath + "/bandpoor/getBandsScoreManageUn.do",
											method : "post",
											success : function (g) {
												var f = Ext.util.JSON.decode(g.responseText);
												d.loadData(f);
											}
										});
									}
								}
							}
						}, {
							text : "品类"
						}, {
							id : "ApplyUnScoreManageSearchFormProClass",
							width : 120,
							name : "Q_proClassName_S_LK",
							maxHeight : 200,
							xtype : "combo",
							mode : "local",
							editable : true,
							triggerAction : "all",
							valueField : "fromproClassId",
							displayField : "fromproClassName",
							store : new Ext.data.SimpleStore(
							{
								url : __ctxPath
										+ "/bandpoor/getProClassScoreManageUn.do",
								fields : [
										"fromproClassId",
										"fromproClassName"]
							}),
							listeners : {
								focus : function (e) {
									var d = Ext.getCmp("ApplyUnScoreManageSearchFormProClass").getStore();
									if (d.getCount() <= 0) {
										Ext.Ajax.request({
											url : __ctxPath + "/bandpoor/getProClassScoreManageUn.do",
											method : "post",
											success : function (g) {
												var f = Ext.util.JSON.decode(g.responseText);
												d.loadData(f);
											}
										});
									}
								}
							}
						}, {
							text : "品牌风格"
						}, {
							id : "ApplyUnScoreManageSearchFormBandStyle",
							width : 120,
							name : "Q_bandStyleName_S_LK",
							maxHeight : 200,
							xtype : "combo",
							mode : "local",
							editable : true,
							triggerAction : "all",
							valueField : "fromBandStyleId",
							displayField : "fromBandStyleName",
							store : new Ext.data.SimpleStore(
							{
								url : __ctxPath
										+ "/bandpoor/getBandStyleScoreManageUn.do",
								fields : [
										"fromBandStyleId",
										"fromBandStyleName"]
							}),
							listeners : {
								focus : function (b) {
									var a = Ext.getCmp("ApplyUnScoreManageSearchFormProClass").getValue();
									if (a != null
										 && a != ""
										 && a != "undefined") {
										Ext.getCmp("ApplyUnScoreManageSearchFormBandStyle").getStore().reload({
											params : {
												proClassId : a
											}
										});
									} else {
										Ext.ux.Toast
										.msg(
											"操作信息",
											"请先选择相应的品类！");
									}
								}
							}
						},{
							text : "品牌风格"
						},{
							width:120,
							name : "ApplyUnScoreManageSearchFormStatus",
							id : "ApplyUnScoreManageSearchFormStatus",
							xtype : "combo",
							triggerAction : "all",
							store : [["","全部"],["1","新建"],["2","审批通过"],["3","打回"],["4","修改"]],
							listeners : {
									select:function(e,c,d){
										var formStatus=Ext.getCmp("ApplyUnScoreManageSearchFormStatus").getValue();
										Ext.getCmp("Q_infoStatus_N_EQ").setValue(formStatus);
									}
							}
						},{
							xtype : "button",
							text : "查询",
							iconCls : "search",
							handler : this.search.createCallback(this)
						},{
							name : "Q_infoStatus_N_EQ",
							id : "Q_infoStatus_N_EQ",
							xtype : "hidden",
						}
					]
				});
			this.store = new Ext.data.Store({
					proxy : new Ext.data.HttpProxy({
						url : __ctxPath + "/bandpoor/listScoreManageUn.do"
					}),
					reader : new Ext.data.JsonReader({
						root : "result",
						totalProperty : "totalCounts",
						id : "id",
						fields : [{
								name : "id",
								type : "int"
							}, "bandName", "floorNumName", "proClassName","bandStyleName","mainPriceName","saleStoreName","bandBusinessAreaName","bandDesc","infoStatus"]
					}),
					remoteSort : true
				});
			this.store.setDefaultSort("id", "desc");
			this.store.load({
				params : {
					start : 0,
					limit : 25
				}
			});
			
			var c = new Ext.grid.CheckboxSelectionModel();
			var a = new Ext.grid.ColumnModel({
					columns : [c, new Ext.grid.RowNumberer(), {
							header : "id",
							dataIndex : "id",
							hidden : true
						}, {
							header : "品牌名称",
							dataIndex : "bandName"
						}, {
							header : "楼层",
							dataIndex : "floorNumName"
						}, {
							header : "品类",
							dataIndex : "proClassName"
						}, {
							header : "品牌风格",
							dataIndex : "bandStyleName"
						}, {
							header : "主力价格",
							dataIndex : "mainPriceName"
						},{
							header : "销售场所",
							dataIndex : "saleStoreName"
						},{
							header : "商圈",
							dataIndex : "bandBusinessAreaName"
						},{
							header : "备注",
							dataIndex : "bandDesc",
							renderer : function (d) {
								if (d == null || d == "" || d == "undefined") {
									return "无";
								} else {
									return d;
								}
							}
						},{
							header : "状态",
							dataIndex : "infoStatus",
							renderer : function (d) {
								if (d == null || d == "" || d == "undefined") {
									return "无";
								} else if(d==0){
									return "删除";
								} else if(d==1){
									return "新建";
								} else if(d==2){
									return "审核通过";
								} else if(d==3){
									return "打回";
								} else if(d==4){
									return "修改";
								}
							}
						}],
					defaults : {
						sortable : true,
						menuDisabled : false,
						width : 100
					}
				});
			this.topbar = new Ext.Toolbar({
					id : "ApplyUnScoreManageFootBar",
					height : 30,
					bodyStyle : "text-align:left",
					items : []
				});
			if (isGranted("_ApplyUnScoreManagePass")) {
				this.topbar.add(new Ext.Button({
						iconCls : "btn-add",
						text : "审批通过",
						handler : this.passRecords,
						scope : this
					}));
			}
			if (isGranted("_ApplyUnScoreManageUnPass")) {
				this.topbar.add(new Ext.Button({
						iconCls : "btn-del",
						text : "打回",
						handler : this.unPassRecords,
						scope : this
					}));
			}
			this.gridPanel = new Ext.grid.GridPanel({
					id : "ApplyUnScoreManageGrid",
					region : "center",
					tbar : this.topbar,
					store : this.store,
					trackMouseOver : true,
					disableSelection : false,
					loadMask : true,
					autoHeight : true,
					cm : a,
					sm : c,
					plugins : this.rowActions,
					viewConfig : {
						forceFit : true,
						autoFill : true,
						forceFit : true
					},
					bbar : new Ext.PagingToolbar({
						pageSize : 25,
						store : this.store,
						displayInfo : true,
						displayMsg : "当前显示{0}至{1}， 共{2}条记录",
						emptyMsg : "当前没有记录"
					})
				});
		},
		search : function (a) {
			if (a.searchPanel.getForm().isValid()) {
				a.searchPanel.getForm().submit({
					waitMsg : "正在提交查询",
					url : __ctxPath + "/bandpoor/listScoreManageUn.do",
					success : function (c, d) {
						var b = Ext.util.JSON.decode(d.response.responseText);
						a.gridPanel.getStore().loadData(b);
					}
				});
			}
		},
		optionByIds : function (a,status) {
			Ext.Msg.confirm("信息确认", "您确认处理所选记录吗？", function (b) {
				if (b == "yes") {
					Ext.Ajax.request({
						url : __ctxPath + "/bandpoor/applyRecordScoreManageUn.do",
						params : {
							ids : a,
							status:status
						},
						method : "POST",
						success : function (c, d) {
							Ext.ux.Toast.msg("信息提示", "成功操作所选记录！");
							Ext.getCmp("ApplyUnScoreManageGrid").getStore().reload();
						},
						failure : function (c, d) {
							Ext.ux.Toast.msg("操作信息", "操作出错，请联系管理员！");
						}
					});
				}
			});
		},
		passRecords : function () {
			var c = Ext.getCmp("ApplyUnScoreManageGrid");
			var a = c.getSelectionModel().getSelections();
			if (a.length == 0) {
				Ext.ux.Toast.msg("信息", "请选择要审批通过的记录！");
				return;
			}
			var d = Array();
			for (var b = 0; b < a.length; b++) {
				d.push(a[b].data.id);
				if(a[b].data.infoStatus==2){
					Ext.ux.Toast.msg("信息", "审批通过的记录无需再次审批！");
					return;
				}else if(a[b].data.infoStatus==3){
					Ext.ux.Toast.msg("信息", "其中有打回的记录不能再次审批！");
					return;
				}
			}
			this.optionByIds(d,"2");
		},
		unPassRecords : function () {
			var c = Ext.getCmp("ApplyUnScoreManageGrid");
			var a = c.getSelectionModel().getSelections();
			if (a.length == 0) {
				Ext.ux.Toast.msg("信息", "请选择要打回的记录！");
				return;
			}
			var d = Array();
			for (var b = 0; b < a.length; b++) {
				d.push(a[b].data.id);
				if(a[b].data.infoStatus==2){
					Ext.ux.Toast.msg("信息", "审批通过的记录不能打回！");
					return;
				}else if(a[b].data.infoStatus==3){
					Ext.ux.Toast.msg("信息", "打回的记录无需再次打回！");
					return;
				}
			}
			this.optionByIds(d,"3");
		}
	});
