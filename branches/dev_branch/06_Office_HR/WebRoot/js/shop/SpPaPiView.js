SpPaPiView = Ext.extend(Ext.Window, {
	searchPanel : null,
	gridPanel : null,
	store : null,
	constructor : function(a) {
		if(a == null) {
			a = {};
		}
		Ext.apply(this, a);
		this.initComponents();
		SpPaPiView.superclass.constructor.call(this, {
			id : "SpPaPiView",
			layout : "border",
			title : "考核指标列表",
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
					text : "查询条件：考核指标名称"
				}, {
					fieldLabel : "考核指标名称",
					name : "paName",
					xtype : "textfield"
				}, {
					xtype : "button",
					text : "查询",
					handler : this.search.createCallback(this)
				}
			]
		});
		this.store = new Ext.data.JsonStore({
			url : __ctxPath + "/shop/pbcComboSpPaPerformanceindex.do?publishStatus=3&frequencyId=" + this.frequencyId + "&depId=" + this.depId,
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
				"type",
				"mode",
				"frequency"
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
					header : "考核指标名称",
					dataIndex : "paName"
				}, {
					header : "考核指标类型",
					dataIndex : "type"
				}, {
					header : "考核方式",
					dataIndex : "mode"
				}
			],
			defaults : {
				sortable : true,
				menuDisable : false,
				width : 100
			}
		});
		this.gridPanel = new Ext.grid.GridPanel({
			id : "SpPaPiGrid",
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
				handler : this.addToSpPaKpipbcForm.createCallback(this.gridPanel, this)
			}
		];
	},
	search : function(a) {
		if(a.searchPanel.getForm().isValid()) {
			a.searchPanel.getForm().submit({
				waitMsg : "正在提交查询……",
				url : __ctxPath + "/shop/pbcComboSpPaPerformanceindex.do?publishStatus=3&frequencyId=" + a.frequencyId + "&depId=" + a.depId,
				params : {
					start : 0,
					limit : 10
				},
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
	addToSpPaKpipbcForm : function(a, b) {
		var c = a.getSelectionModel().getSelections();
		var d = Ext.getCmp("SpPaKpiitemGrid");
		for(var j = 0; j < c.length; j++) {
			for(var k = 0; k < d.getStore().getCount(); k++) {
				if(d.getStore().getAt(k).data.piId == c[j].data.id) {
					Ext.MessageBox.show({
						title : "操作信息",
						msg : "您插入了重复考核指标，请核实！",
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