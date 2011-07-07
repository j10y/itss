// ���������б�   
ListProcessPanel = Ext.extend(Ext.Panel,{
	id : "ListTaskPanel",
	closable : true,
	height : 'auto',
	width : 'auto',
	viewConfig : {
		autoFill : true,
		forceFit : true
	},
	layout : 'border',
	items : this.items,
	reset : function() {
		this.fp.form.reset();
	},

	listAll : function() {
		var actor = Ext.getCmp("userCombo").getValue();
		if(actor==""||actor==null||actor==undefined){
			this.store.removeAll();
			return;			
		}
		this.store.removeAll();
		var param = {
			start : 0,
			actor : actor
		};
		this.store.load({
			params : param

		});
		this.store.load();
	},

	view : function(grid) {
		var record = this.grid.getSelectionModel()
				.getSelected();
		var records = this.grid.getSelectionModel()
				.getSelections();
		if (!record) {
			Ext.Msg.alert("��ʾ", "����ѡ��Ҫ���õ�������!");
			return;
		}
		if (records.length > 1) {
			Ext.Msg.alert("��ʾ", "�޸�ʱֻ��ѡ��һ��!");
			return;
		}
		var id = record.get("id");

		window.location = webContext
				+ "/infoAdmin/workflow/configPage/taskInfo.jsp?virtualDefinitionInfoId="
				+ id;
	},

	// ��ʼ��
	initComponent : function() {
		ListProcessPanel.superclass.initComponent.call(this);
		var da = new DataAction();

		var sm = new Ext.grid.CheckboxSelectionModel();
		this.store = new Ext.data.JsonStore(
				{
					url : webContext
							+ '/workflow/listTask.do?methodCall=listTaskDetailByActor',
					root : "data",
					fields : [ "id", "taskIdANDprocessId","processId", "processName",
							"taskName",
							"auditPerson"],
					totalProperty : 'rowCount'
				});
		this.cm = new Ext.grid.ColumnModel([ sm, {
			header : "���� ",
			dataIndex : "taskIdANDprocessId",
			width : 200,
			renderer : function(value){
				//return "<a href='" + webContext + "/workflow/history.do?procid="+value+"&methodCall=list'>" + "HISTORY" + "</a>";
				var ids = value.split("|");
				return "<a href='#' onclick='getProcessHistory(" + ids[1] + ");' >" + "�鿴��ʷ" 
				+ "</a>&nbsp;&nbsp;|&nbsp;&nbsp;<a href='#' onclick='endProcess(" + ids[1] + ");' >" + "��������"
				+ "</a>&nbsp;&nbsp;|&nbsp;&nbsp;<a href='#' onclick='reassign(" + ids[0] + ");' >" + "����ָ��"
				+ "</a>";
			}
		}, {
			header : "����ʵ��ID",
			dataIndex : "processId",
			width : 300
		}, {
			header : "����ID",
			dataIndex : "id",
			width : 150
		}, {
			header : "������������",
			dataIndex : "processName",
			width : 300
		}, {
			header : "�ڵ�����",
			dataIndex : "taskName",
			width : 150
		}, {
			header : "������",
			dataIndex : "auditPerson",
			width : 300
		}]);
		this.store.paramNames.sort = "orderBy";
		this.store.paramNames.dir = "orderType";
		this.cm.defaultSortable = true;

		var viewConfig = Ext.apply({
			forceFit : true
		}, this.gridViewConfig);

		this.pageBar = new Ext.PagingToolbar({
			pageSize : 10,
			store : this.store,
			displayInfo : true,
			displayMsg : '��ǰ��ʾ {0}-{1}����¼ /��{2}����¼',
			emptyMsg : "����ʾ����"
		});
		this.formValue = '';
		this.pageBar.formValue = this.formValue;
		
		

		this.grid = new Ext.grid.GridPanel(
				{
					id : 'mainGrid',
					title : '�û������б�',
					region :'center',
					store : this.store,
					cm : this.cm,
					sm : sm,
					trackMouseOver : false,
					loadMask : true,
					clicksToEdit : 2,
					autoScroll : true,
					frame : true,
					//bbar : this.pageBar,
					tbar : [{xtype:'label',text:'�û��˺ţ�'},{
							anchor:"50%",
							fieldLabel:"�û��˺�",
							labelStyle:"width:130",
							maxLength:255,
							id:'userCombo',
							name:"actor",
							xtype:"combo",
							allowBlank:false,
							emptyText:'�������û��˺�,�磺zhangsan',
							typeAhead : true,
							defaultParam : "",
							triggerAction : "all",
							displayField : 'realName',
							valueField : "userName",
							store : new Ext.data.JsonStore({
								url : webContext + '/system/utilAction.do?methodCall=searchComboMessage&className=com.zsgj.info.framework.security.entity.UserInfo&propertyName=userName&nameField=realName&valueField=userName',
								fields : ['userName', 'realName'],
								totalProperty : 'rowCount',
								root : 'data',
								listeners : {
									beforeload : function(store, opt) {
										var param = Ext.getCmp('userCombo').defaultParam;
										if (opt.params['propertyValue'] == undefined) {
											opt.params['propertyValue'] = param;
										}
									}
								}
							}),
							listeners : {
								'beforequery' : function(queryEvent) {
									var param = queryEvent.query;
									this.defaultParam = param;
									Ext.getCmp("userCombo").store.load({
												params : {
													propertyValue : param,
													start : 1
												}
											});
									return true;
								},
								"blur" : function(combo){
									var nowVal = combo.getRawValue();
									var nowId = combo.getValue();
									if(nowVal==""){
										combo.setValue("")
									}else if(nowId==""){
										combo.setValue("");
										Ext.Msg.alert("��ʾ��Ϣ","����ֱ�����룬���ѯ��ѡ��");
									}
								}
							}						

					},{xtype:'button',
						text:'��ѯ',
						iconCls:'search',
						handler:function(){
							var actor = Ext.getCmp("userCombo").getValue();
							if(actor!=""&&actor!=null&&actor!=undefined){
								this.listAll();
							}else{
								this.store.removeAll();
								Ext.Msg.alert("��ʾ", "������ѡ���û�");
							}
						},
						scope:this
					},{xtype:'button',
						text:'����',
						iconCls:'reset',
						handler:function(){
							Ext.getCmp("userCombo").reset();
						}
					}]

				});
		this.grid.on("rowdblclick", this.view, this);
		var param = {
			'start' : 0
		};
		this.store.on('beforeload', function(store) {
			Ext.apply(store.baseParams, param);
		});
//		this.store.removeAll();
//		this.store.load({
//			params : param
//
//		});
		this.add(this.grid);
	},
	fitWidth : function(grid, columnIndex, e) {
		var c = columnIndex;
		var w = grid.view.getHeaderCell(c).firstChild.scrollWidth;
		for ( var i = 0, l = grid.store.getCount(); i < l; i++) {
			w = Math
					.max(
							w,
							grid.view.getCell(i, c).firstChild.scrollWidth);
		}
		grid.colModel.setColumnWidth(c, w);
	}

});
