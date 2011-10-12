HrPromotionHistView = Ext.extend(Ext.Panel, {
	constructor : function(a) {
		if(a == null) {
			a = {};
		}
		Ext.apply(this, a);
		this.initComponents();
		HrPromotionHistView.superclass.constructor.call(this, {
			id : "HrPromotionHistView",
			title : "晋升历史",
			region : "center",
			layout : "border",
			items : [
				this.searchPanel,
				this.gridPanel
			]
		});
	},
	searchPanel : null,
	gridPanel : null,
	store : null,
	topbar : null,
	initComponents : function() {
		this.searchPanel = new Ext.FormPanel({
			region : "north",
			height : 40,
			frame : false,
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
					text : "查询条件：姓名"
				}, {
					fieldLabel : "姓名",
					name : "Q_promApply.applyUser.fullname_S_LK",
					xtype : "textfield"
				}, {
					xtype : "button",
					text : "查询",
					handler : this.search.createCallback(this)
				}
			]
		});
		this.store = new Ext.data.JsonStore({
			url : __ctxPath + "/hrm/listHistHrPromAssessment.do?Q_publishStatus_N_EQ=3",
			totalProperty : "totalCounts",
			id : "id",
			root : "result",
			remoteSort : true,
			fields : [
				{
					name : "id",
					type : "int"
				}, {
					name : "promApply.applyUser.fullname",
					mapping : "promApply.applyUser.fullname"
				}, {
					name : "applyId",
					mapping : "promApply.id"
				},
				"promApply.nowPositionName",
				"promApply.applyPositionName",
				"publishStatus"
			]
		});
		this.store.setDefaultSort("id", "desc");
		this.store.load({
			params : {
				start : 0,
				limit : 25
			}
		});
		var b = new Array();
		b.push({
			iconCls : "btn-preview",
			qtip : "查看",
			style : "margin:0 3px 0 3px"
		});
		this.rowActions = new Ext.ux.grid.RowActions({
			header : "管理",
			width : 80,
			actions : b
		});
		var c = new Ext.grid.CheckboxSelectionModel();
		var a = new Ext.grid.ColumnModel({
			columns : [
				c,
				new Ext.grid.RowNumberer(),
				{
					header : "id",
					dataIndex : "id",
					hidden : true
				}, {
					header : "applyId",
					dataIndex : "applyId",
					hidden : true
				}, {
					header : "姓名",
					dataIndex : "promApply.applyUser.fullname"
				}, {
					header : "原岗位",
					dataIndex : "promApply.nowPositionName"
				}, {
					header : "拟晋升岗位",
					dataIndex : "promApply.applyPositionName"
				}, {
					header : "状态",
					dataIndex : "publishStatus",
					renderer : function(d) {
						if(d == 0) {        //草稿
							return "<font color='red'>草稿</font>";
						}
						if(d == 1) {        //审核中
							return "<font color='red'>审核中</font>";
						}
						if(d == 2) {        //退回
							return "<font color='red'>退回</font>";
						}
						if(d == 3) {        //审核完毕，发布
							return "<font color='green'>已审批</font>";
						}
						if(d == 4) {        //删除标记
							return "<font color='red'>已删除</font>";
						}
						if(d == 8) {        //待考核期评估
							return "<font color='red'>待考核期评估</font>";
						}
						if(d == 9) {        //待领导批准
							return "<font color='red'>待领导批准</font>";
						}
						if(d == 10) {        //待人力资源确认
							return "<font color='red'>待人力资源确认</font>";
						}
						if(d == 11) {        //待晋升面谈
							return "<font color='red'>待晋升面谈</font>";
						}
						if(d == 12) {        //待任命发文
							return "<font color='red'>待任命发文</font>";
						}
					}
				},
				this.rowActions
			],
			defaults : {
				sortable : true,
				menuDisable : false,
				width : 100
			}
		});
		this.gridPanel = new Ext.grid.GridPanel({
			id : "HrPromotionHistGrid",
			region : "center",
			autoWidth : true,
			autoHeight : true,
			stripeRows : true,
			closeable : true,
			store : this.store,
			trackMouseOver : true,
			disableSelection : false,
			loadMask : true,
			cm : a,
			sm : c,
			plugins : this.rowActions,
			viewConfig : {
				forceFit : true,
				enableRowBody : false,
				showPreview : false
			},
			bbar : new Ext.PagingToolbar({
				pageSize : 25,
				store : this.store,
				displayInfo : true,
				displayMsg : "当前显示{0}至{1}，共{2}条记录",
				emptyMsg : "当前没有记录"
			})
		});
		this.gridPanel.addListener("rowdblclick", function(f, d, g) {
			f.getSelectionModel().each(function(e) {
				new HrPromotionHistForm({
					assessmentId : e.data.id,
					applyId : e.data.applyId
				}).show();
			});
		});
		this.rowActions.on("action", this.onRowAction, this);
	},
	search : function(a) {
		if(a.searchPanel.getForm().isValid()) {
			a.searchPanel.getForm().submit({
				waitMsg : "正在提交查询……",
				url : __ctxPath + "/hrm/listHistHrPromAssessment.do?Q_publishStatus_I_EQ=3",
				success : function(c, d) {
					var e = Ext.util.JSON.decode(d.response.responseText);
					a.gridPanel.getStore().loadData(e);
				}
			});
		}
	},
	previewHrPromAssessment : function(a) {
		new HrPromotionHistForm({
			assessmentId : a.data.id,
			applyId : a.data.applyId
		}).show();
	},
	onRowAction : function(c, a, d, e, b) {
		switch(d) {
			case "btn-preview":
				this.previewHrPromAssessment(a);
				break ;
			default:
				break ;
		}
	}
});