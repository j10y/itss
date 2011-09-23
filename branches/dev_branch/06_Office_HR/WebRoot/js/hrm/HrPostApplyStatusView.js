HrPostApplyStatusView = Ext.extend(Ext.Panel, {
	constructor : function(a) {
		if(a == null) {
			a = {};
		}
		Ext.apply(this, a);
		this.initComponents();
		HrPostApplyStatusView.superclass.constructor.call(this, {
			id : "HrPostApplyStatusView",
			title : "转正申请状态",
			region : "center",
			layout : "border",
			items : [
				this.gridPanel
			]
		});
	},
	gridPanel : null,
	store : null,
	initComponents : function() {
		this.store = new Ext.data.JsonStore({
			url : __ctxPath + "/hrm/listStatusHrPostApply.do",
			totalProperty : "totalCounts",
			id : "id",
			root : "result",
			remoteSort : true,
			fields : [
				{
					name : "id",
					type : "int"
				}, {
					name : "applyUser.fullname",
					mapping : "applyUser.fullname"
				},
				"postName",
				"accessionTime",
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
		if(isGranted("_HrPostApplyQuery")) {
			b.push({
				iconCls : "btn-preview",
				qtip : "查看",
				style : "margin:0 3px 0 3px"
			});
		}
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
					header : "姓名",
					dataIndex : "applyUser.fullname"
				}, {
					header : "所属岗位",
					dataIndex : "postName"
				}, {
					header : "入职时间",
					dataIndex : "accessionTime",
					renderer : function(c) {
						return c;
					}
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
						if(d == 5) {
							return "<font color='red'>待直线经理审批</font>";
						}
						if(d == 6) {
							return "<font color='red'>待人力资源部复核</font>";
						}
						if(d == 7) {
							return "<font color='red'>待分管副总裁确认</font>";
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
			id : "HrPostApplyGrid",
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
				if(isGranted("_HrPostApplyQuery")) {
					new HrPostApplyPreview({
						applyId : e.data.id
					}).show();
				}
			});
		});
		this.rowActions.on("action", this.onRowAction, this);
	},
	previewHrPostApply : function(a) {
		new HrPostApplyPreview({
			applyId : a.data.id
		}).show();
	},
	onRowAction : function(c, a, d, e, b) {
		switch(d) {
			case "btn-preview":
				this.previewHrPostApply(a);
				break ;
			default:
				break ;
		}
	}
});