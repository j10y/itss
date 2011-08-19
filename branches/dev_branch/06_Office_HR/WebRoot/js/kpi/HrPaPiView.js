HrPaPiView = Ext.extend(Ext.Window, {
	searchPanel : null,
	gridPanel : null,
	store : null,
	constructor : function(a) {
		if(a == null) {
			a = {};
		}
		Ext.apply(this, a);
		this.initComponents();
		HrPaPiView.superclass.constructor.call(this, {
			id : "HrPaPiView",
			layout : "border",
			title : "考核项目列表",
			modal : true,
			width : 650,
			height : 370,
			buttonAlign : "center",
			buttons : this.buttons,
			items : [
				this.searchPanel,
				this.gridPanel
			]
		});
	},
	initComponents : function() {
		this.searchPanel = new Ext.FormPanel({
			region : "north",
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
					text : "查询条件：考核项目名称"
				}, {
					fieldLabel : "考核项目名称",
					name : "Q_paName_S_LK",
					xtype : "textfield"
				}, {
					xtype : "button",
					text : "查询",
					handler : this.search.createCallback(this)
				}
			]
		});
		this.store = new Ext.data.JsonStore({
			url : __ctxPath + "/kpi/listHrPaPerformanceindex.do?Q_publishStatus_N_EQ=3&Q_frequency.id_L_LE=" + this.frequencyId,
			totalProperty : "totalCounts",
			id : "id",
			root : "result",
			remoteSort : true,
			fields : [
				{
					name : "id",
					type : "int"
				},
				"paName",
				{
					name : "type.name",
					mapping : "type.name"
				}, {
					name : "mode.name",
					mapping : "mode.name"
				}
			]
		});
		this.store.setDefaultSort("id", "desc");
		this.store.load({
			params : {
				start : 0,
				limit : 10
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
					header : "考核项目名称",
					dataIndex : "paName"
				}, {
					header : "考核项目类型",
					dataIndex : "type.name"
				}, {
					header : "考核方式",
					dataIndex : "mode.name"
				}
			],
			defaults : {
				sortable : true,
				menuDisable : false,
				width : 100
			}
		});
		this.gridPanel = new Ext.grid.GridPanel({
			id : "HrPaPiGrid",
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
			viewConfig : {
				forceFit : true,
				enableRowBody : false,
				showPreview : false
			},
			bbar : new Ext.PagingToolbar({
				pageSize : 10,
				store : this.store,
				displayInfo : true,
				displayMsg : "当前显示{0}至{1}，共{2}条记录",
				emptyMsg : "当前没有记录"
			})
		});
		this.buttons = [
			{
				text : "取消",
				handler : this.cancel.createCallback(this)
			}, {
				text : "添加",
				handler : this.addToHrPaKpipbcForm.createCallback(this.gridPanel, this)
			}
		];
	},
	search : function(a) {
		if(a.searchPanel.getForm().isValid()) {
			a.searchPanel.getForm().submit({
				waitMsg : "正在提交查询……",
				url : __ctxPath + "/kpi/listHrPaPerformanceindex.do?Q_publishStatus_N_EQ=3&Q_frequency.id_L_LE=" + this.frequencyId,
				success : function(c, d) {
					var e = Ext.util.JSON.decode(d.response.responseText);
					a.gridPanel.getStore().loadData(e);
				}
			});
		}
	},
	cancel : function(b) {
		b.close();
	},
	addToHrPaKpipbcForm : function(a, b) {
		var c = a.getSelectionModel().getSelections();
		var d = Ext.getCmp("HrPaKpiitemGrid");
		for(var j = 0; j < c.length; j++) {
			for(var k = 0; k < d.getStore().getCount(); k++) {
				if(d.getStore().getAt(k).data.piId == c[j].data.id) {
					Ext.MessageBox.show({
						title : "操作信息",
						msg : "您插入了重复考核项目，请核实！",
						buttons : Ext.MessageBox.OK,
						icon : Ext.MessageBox.ERROR
					});
					return ;
				}
			}
		}
		b.close();
		var PiRecord = Ext.data.Record.create([
			{
				name : "id",
				type : "int"
			}, {
				name : "piId",
				type : "int"
			}, {
				name : "pi.paName",
				type : "string"
			}, {
				name : "weight",
				type : "float"
			}
		]);
		
		for(var i = 0; i < c.length; i++) {
			var pi = new PiRecord({
				"id" : 0,
				"piId" : c[i].data.id,
				"pi.paName" : c[i].data.paName,
				"weight" : 0
			});
			d.getStore().add(pi);
		}
	}
});