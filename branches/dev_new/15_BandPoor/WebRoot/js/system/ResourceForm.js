ResourceForm = Ext.extend(Ext.Window, {
	record : null,
	formPanel : null,
	gridPanel : null,
	topbar    : null,
	store : null,
	constructor : function(a) {
		Ext.applyIf(this, a);
		this.initUIComponents();
		ResourceForm.superclass.constructor.call(this, {
			layout : "border",
			id : "ResourceFormWin",
			iconCls : "menu-resource",
			items : [this.formPanel,this.gridPanel],
			title : "资源详细信息",
			width : 600,
			border : false,
			height : 500,
			modal : true,
			plain : true,
			bodyStyle : "padding:5px;",
			buttonAlign : "center",
			buttons : this.buttons
		});
	},
	initUIComponents : function() {
		this.formPanel = new Ext.FormPanel( {
			url : __ctxPath + "/system/saveAppFunction.do",
			region : "north",
			height : 70,
			layout : "form",
			id : "ResourceForm",
			frame : false,
			defaults : {
				width : 300,
				anchor : "98%,98%",
				labelWidth : 60
			},
			formId : "ResourceFormId",
			defaultType : "textfield",
			items : [
					{
						name : "appFunction.functionId",
						id : "functionId",
						xtype : "hidden",
						value : this.functionId == null ? "" : this.functionId
					},
					{
						fieldLabel : "资源关键字",
						name : "appFunction.funKey",
						id : "funKey"
					}, {
						fieldLabel : "资源名称",
						name : "appFunction.funName",
						id : "funName",
					} ]
		});
		if (this.functionId != null && this.functionId != "undefined") {
			this.formPanel.getForm().load(
					{
						deferredRender : false,
						url : __ctxPath + "/system/getAppFunction.do?functionId="
								+ this.functionId,
						waitMsg : "正在载入数据...",
						success : function(a, b) {
						},
						failure : function(a, b) {
						}
					});
		}
		this.buttons = [ {
			text : "保存",
			iconCls : "btn-save",
			handler : this.save.createCallback(this.formPanel, this)
		}, {
			text : "重置",
			iconCls : "btn-reset",
			handler : this.reset.createCallback(this.formPanel)
		}, {
			text : "取消",
			iconCls : "btn-cancel",
			handler : this.cancel.createCallback(this)
		} ];
		
		
		this.store = new Ext.data.JsonStore( {
			url : __ctxPath + "/system/listFunUrl.do?Q_appFunction.functionId_L_EQ="+((this.functionId==null||this.functionId == "undefined")?0 : this.functionId),
			root : "result",
			totalProperty : "totalCounts",
			remoteSort : true,
			fields : [
				{
					name : "urlId",
					type : "int",
				},
				"functionId",
				"urlPath"
			]
		});
		this.store.setDefaultSort("urlId", "asc");
		this.store.load({
			params : {
				start : 0,
				limit : 25
			}
		});
		
		var c = new Ext.grid.CheckboxSelectionModel({handleMouseDown:Ext.emptyFn});
		var a = new Ext.grid.ColumnModel({
			columns : [
				c,
				new Ext.grid.RowNumberer(),
				{
					header : "urlId",
					dataIndex : "urlId",
					hidden : true
				}, {
					header : "functionId",
					dataIndex : "functionId",
					hidden : true
				}, {
					header : "资源URL",
					dataIndex : "urlPath",
					width : 250,
					editor :new Ext.grid.GridEditor(
						 new Ext.form.TextField({
						allowBlank : false
					})
					
					)
				}
			],
			defaults : {
				sortable : true,
				menuDisable : false
			}
		});
		
		this.topbar = new Ext.Toolbar( {
			id : "FunUTopBar",
			height : 30,
			bodyStyle : "text-align:left",
			items : [],
		});
		if (isGranted("_DictionaryAdd")) {
			this.topbar.add(new Ext.Button( {
				iconCls : "btn-add",
				text : "添加URL",
				handler : this.createRecord,
				scope : this
			}));
		}
		if (isGranted("_DictionaryDel")) {
			this.topbar.add(new Ext.Button( {
				iconCls : "btn-del",
				text : "删除URL",
				handler : this.delRecords,
				scope : this
			}));
		}
		
		this.gridPanel = new Ext.grid.EditorGridPanel({
			id : "funUrlGrid",
			region : "center",
			autoWidth : true,
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
				autoFill : true,
				forceFit : true
			},
			tbar : this.topbar,
			bbar : new Ext.PagingToolbar({
				pageSize : 100,
				store : this.store,
				displayInfo : true,
				displayMsg : "当前显示{0}至{1}，共{2}条记录",
				emptyMsg : "当前没有记录"
			})
		});
		this.gridPanel.addListener("rowdblclick", function(f, d, g) {
			f.getSelectionModel().each(function(e) {
				
			});
		});
		this.record = new Ext.data.Record.create([
			{name:'urlId'},
			{name:'functionId'},
			{name:'urlPath'}
			]);
		
		
		
	},
	
	createRecord : function(){
		var p = new this.record({
			urlId:'',
			functionId:this.functionId,
			urlPath:''
		});
		this.gridPanel.stopEditing();
		this.store.insert(this.store.getCount(),p);
		this.gridPanel.startEditing(this.store.getCount(),2);
		
	},
	delRecords : function() {
		var c = Ext.getCmp("funUrlGrid");
		var a = c.getSelectionModel().getSelections();
		if (a.length == 0) {
			Ext.ux.Toast.msg("信息", "请选择要删除的记录！");
			return;
		}
		this.store.remove(a);
	},
	
	
	reset : function(a) {
		a.getForm().reset();
	},
	cancel : function(a) {
		a.close();
	},
	save : function(a, b) {
		var ja = [];
		var sss = Ext.getCmp("funUrlGrid").getStore();
		Ext.ux.Toast.msg(sss.getCount());
		Ext.each(sss,function(item){
			ja.push(item.data);
		});
		if (a.getForm().isValid()) {
			a.getForm().submit( {
				method : "POST",
				waitMsg : "正在提交数据...",
				success : function(c, e) {
					Ext.ux.Toast.msg("操作信息", "成功保存信息！");
					var d = Ext.getCmp("ResourceGrid");
					if (d != null) {
						d.getStore().reload();
					}
					b.close();
				},
				failure : function(c, d) {
					Ext.MessageBox.show( {
						title : "操作信息",
						msg : "信息保存出错，请联系管理员！",
						buttons : Ext.MessageBox.OK,
						icon : Ext.MessageBox.ERROR
					});
					b.close();
				},
				params : {
					funUrls: encodeURIComponent(Ext.encode(ja))
				}
			});
		}
	}
});