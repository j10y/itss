HrPostAssessmentStatusView = Ext.extend(Ext.Panel, {
	constructor : function(a) {
		if(a == null) {
			a = {};
		}
		Ext.apply(this, a);
		this.initComponents();
		HrPostAssessmentStatusView.superclass.constructor.call(this, {
			id : "HrPostAssessmentStatusView",
			title : "试用期评估状态",
			region : "center",
			layout : "border",
			items : [
				this.gridPanel
			]
		});
	},
	gridPanel : null,
	store : null,
	topbar : null,
	initComponents : function() {
		this.store = new Ext.data.JsonStore({
			url : __ctxPath + "/hrm/listStatusHrPostAssessment.do",
			totalProperty : "totalCounts",
			id : "id",
			root : "result",
			remoteSort : true,
			fields : [
				{
					name : "id",
					type : "int"
				},
				"postApply.applyUser.fullname",
				"postApply.postName",
				"postApply.accessionTime",
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
		if(isGranted("_ApplyQuery")) {
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
		Date.prototype.format = function(format){ 
			var o = { 
				"M+" : this.getMonth()+1, //month 
				"d+" : this.getDate(), //day 
				"h+" : this.getHours(), //hour 
				"m+" : this.getMinutes(), //minute 
				"s+" : this.getSeconds(), //second 
				"q+" : Math.floor((this.getMonth()+3)/3), //quarter 
				"S" : this.getMilliseconds() //millisecond 
			}
			if(/(y+)/.test(format)) { 
				format = format.replace(RegExp.$1, (this.getFullYear()+"").substr(4 - RegExp.$1.length)); 
			} 
			
			for(var k in o) { 
				if(new RegExp("("+ k +")").test(format)) { 
					format = format.replace(RegExp.$1, RegExp.$1.length==1 ? o[k] : ("00"+ o[k]).substr((""+ o[k]).length)); 
				} 
			}
			return format; 
		}
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
					dataIndex : "postApply.applyUser.fullname"
				}, {
					header : "所属岗位",
					dataIndex : "postApply.postName"
				}, {
					header : "入职时间",
					dataIndex : "postApply.accessionTime",
					renderer : function(c) {
						return new Date(c).format("yyyy-MM-dd");
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
			id : "HrPostAssessmentGrid",
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
				if(isGranted("_HrPostAssessmentQuery")) {
					new HrPostAssessmentPreview({
						assessmentId : e.data.id
					}).show();
				}
			});
		});
		this.rowActions.on("action", this.onRowAction, this);
	},
	previewHrPostAssessment : function(a) {
		new HrPostAssessmentPreview({
			assessmentId : a.data.id
		}).show();
	},
	onRowAction : function(c, a, d, e, b) {
		switch(d) {
			case "btn-preview":
				this.previewHrPostAssessment(a);
				break ;
			default:
				break ;
		}
	}
});