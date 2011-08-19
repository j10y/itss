HrPaKpipbcFormView = Ext.extend(Ext.Window, {
	formPanel : null,
	gridPanel : null,
	store : null,
	topbar : null,
	constructor : function(a) {
		if(a == null) {
			a = {};
		}
		Ext.apply(this, a);
		this.initComponents();
		HrPaKpipbcFormView.superclass.constructor.call(this, {
			id : "HrPaKpipbcFormViewWin",
			region : "center",
			layout : "border",
			items : [
				this.formPanel,
				this.gridPanel
			],
			modal : true,
			height : 600,
			width : 850,
			autoScroll : true,
			title : "考核模板查看",
			buttonAlign : "center",
			buttons : this.buttons
		});
	},
	initComponents : function() {
		this.formPanel = new Ext.FormPanel({
			region : "north",
			height : 100,
			bodyStyle : "padding:20px 20px 20px 20px",
			frame : false,
			border : false,
			layout : "form",
			layoutConfig : {
				padding : "5",
				align : "middle"
			},
			id : "HrPaKpipbcForm",
			reader : new Ext.data.JsonReader({
				root : "data"
			},[
				{
					name : "hrPaKpipbc.pbcName",
					mapping : "pbcName"
				}, {
					name : "hrPaKpipbc.belongDept.depId",
					mapping : "belongDept.depId"
				}, {
					name : "hrPaKpipbc.belongDept.depName",
					mapping : "belongDept.depName"
				}, {
					name : "hrPaKpipbc.belongPost.jobName",
					mapping : "belongPost.jobName"
				}, {
					name : "hrPaKpipbc.frequency.name",
					mapping : "frequency.name"
				}
			]),
			items : [ 
				{
					xtype : "container",
					border : false,
					layout : "column",
					items : [
						{
							layout : "form",
							border : false,
							columnWidth : 0.5,
							items : [
								{
									fieldLabel : "考核模板名称",
									name : "hrPaKpipbc.pbcName",
									id : "pbcName",
									xtype : "textfield",
									width : 165,
									readOnly : true,
									allowBlank : false,
									blankText : "考核模板名称不能为空！"
								}, {
									fieldLabel : "所属部门",
									name : "hrPaKpipbc.belongDept.depName",
									id : "depName",
									xtype : "textfield",
									width : 165,
									readOnly : true
								}
							]
						}, {
							layout : "form",
							border : false,
							columnWidth : 0.5,
							items : [
								{
									fieldLabel : "考核频度",
									name : "hrPaKpipbc.frequency.name",
									xtype : "textfield",
									readOnly : true
								}, {
									fieldLabel : "所属岗位",
									name : "hrPaKpipbc.belongPost.jobName",
									xtype : "textfield",
									readOnly : true
								}
							]
						}
					]
				}
			]
		});
		var pbcId = (this.pbcId == null || this.pbcId == "undefined") ? 0 : this.pbcId;
		this.store = new Ext.data.JsonStore({
			url : __ctxPath + "/kpi/listHrPaKpiitem.do?Q_pbc.id_L_EQ=" + pbcId,
			id : "id",
			root : "result",
			remoteSort : true,
			fields : [
				{
					name : "id",
					type : "int"
				}, {
					name : "piId",
					mapping : "pi.id"
				}, {
					name : "pi.paName",
					mapping : "pi.paName"
				},
				"weight"
			]
		});
		this.store.setDefaultSort("id", "asc");
		this.store.load({
			params : {
				start : 0,
				limit : 100
			}
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
					header : "piId",
					dataIndex : "piId",
					hidden : true
				}, {
					header : "考核项目名称",
					dataIndex : "pi.paName",
					width : 250
				}, {
					header : "权重",
					dataIndex : "weight",
					editor : new Ext.form.Field({
						xtype : "textfield"
					})
				}
			],
			defaults : {
				sortable : true,
				menuDisable : false
			}
		});
		this.gridPanel = new Ext.grid.GridPanel({
			id : "HrPaKpiitemGrid",
			region : "center",
			autoWidth : true,
			autoHeight : true,
			stripeRows : true,
			autoScroll : true,
			closeable : true,
			store : this.store,
			trackMouseOver : true,
			disableSelection : false,
			loadMask : true,
			cm : a,
			sm : c,
			viewConfig : {
				forceFit : true,
				enableRowBody : false,
				showPreview : false
			},
			bbar : new Ext.PagingToolbar({
				pageSize : 100,
				store : this.store,
				displayInfo : true,
				displayMsg : "当前显示{0}至{1}，共{2}条记录",
				emptyMsg : "当前没有记录"
			})
		});
		if(this.pbcId != null && this.pbcId != "undefined") {
			this.formPanel.getForm().load({
				deferredRender : false,
				url : __ctxPath + "/kpi/getHrPaKpipbc.do?id=" + this.pbcId,
				waitMsg : "正在载入数据……",
				success : function(f, d) {
					
				},
				failure : function() {
					
				}
			});
		}
	}
});