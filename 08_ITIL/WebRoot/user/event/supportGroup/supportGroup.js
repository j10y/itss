PagePanel = Ext.extend(Ext.Panel, {
	id : "PagePanel",
	// title:this.pageModeltitle,
	closable : true,
	viewConfig : {
		autoFill : true,
		forceFit : true
	},
	layout : 'border',
	searchSupport : function (){
		this.store.baseParams={
					deleteFlag : 0,
					groupName : Ext.getCmp('groupName').getValue(),
					groupRank : Ext.getCmp('groupRank').getValue()
				};
		this.store.load({
					params : {
						start : 0
					}
				});
	},
	modifyby : function() {
		var record = this.grid.getSelectionModel().getSelected();
		var records = this.grid.getSelectionModel().getSelections();
		if (!record) {
			Ext.Msg.alert("��ʾ", "����ѡ��Ҫ�޸ĵ��У�");
			return;
		}
		if (records.length > 1) {
			Ext.Msg.alert("��ʾ", "�޸�ʱֻ��ѡ��һ�У�");
			return;
		}
		var dataId = record.get("SupportGroup$id"); 
		window.location = webContext+"/supportGroupAction_toModifyorShow.action?dataId="+dataId;

	},
	getSearchForm : function() {
		this.panel = new Ext.form.FormPanel({
		    id :'supportgroupquery',
			region : "north",
			layout : 'table',
			height : 60,
			frame : true,
			collapsible : true,
			defaults : {
				bodyStyle : 'padding:2px'
			},
			layoutConfig : {
				columns : 6
			},
			title : "��ѯ�б�",
			items : [{
						html : '֧��������:',
						cls : 'common-text',
						style : 'width:120;text-align:right'
					},new Ext.form.TextField({
						fieldLabel:"֧��������",
						xtype:"textfield",
						id:"groupName",
						name:"groupName",
						width : 150,
						allowBlank:true
					}),{
						html:'֧���鼶��:',
						cls : 'common-text'	,
						style : 'width:100;text-align:right'							
					},new Ext.form.ComboBox({
						xtype : 'combo',
						id : 'groupRank',
						mode : 'local',
						triggerAction : 'all',
						forceSelection : true,
						allowBlank : true,
						store : new Ext.data.SimpleStore({
							fields : ['groupId', 'GroupName'],
							data : [['1', '��һ����'], ['2', '�ڶ�����'],[]]
						}),
						emptyText : '��ѡ��...',
						valueField : 'groupId',
						displayField : 'GroupName',
						name : 'groupRank',
						width : 150,
						fieldLabel : '֧���鼶��',
						listeners : {
							'expand' : function(combo) {
								combo.reset();
							}
						}
					})]
			
		});
		return this.panel;
	},
	items : this.items,
	initComponent : function() {
		var da = new DataAction();
		this.fp = this.getSearchForm();
		this.modelTableName = "SupportGroup";
		var obj = da.getListPanelElementsForHead("supportgroup_pagepanel");
		var sm = new Ext.grid.CheckboxSelectionModel();
		var columns = new Array();
		var fields = new Array();
		columns[0] = sm;
		for (var i = 0; i < obj.length; i++) {
			var headItem = obj[i];
			var title = headItem.header;
			var alignStyle = 'left';
			var propertyName = headItem.dataIndex;
			var isHiddenColumn = false;
			var modelTableId = this.modelTableName + "$id";
			var isHidden = headItem.isHidden;
			if (isHidden == 'true') {
				isHiddenColumn = true;
			}
			var columnItem = {
				header : title,
				dataIndex : propertyName,
				sortable : true,
				hidden : isHiddenColumn,
				align : alignStyle
			}
			if(propertyName=="SupportGroup$id"){
				columnItem.width=70;
			}
			if(propertyName=="SupportGroup$groupName"){
				columnItem.width=150;
			}
			if(propertyName=="SupportGroup$groupRank"){
				columnItem.width=90;
			}
			if(propertyName=="SupportGroup$groupLeader"){
				columnItem.width=160;
			}
			
			if(propertyName=="SupportGroup$groupConfirmer"){
				columnItem.width=160;
			}
			if(propertyName=="SupportGroup$groupContractOrFileer"){
				columnItem.width=160;
			}
			if(propertyName=="SupportGroup$groupSolutioner"){
				columnItem.width=160;
			}
			if(propertyName=="SupportGroup$groupAlterer"){
				columnItem.width=160;
			}
			columns[i + 1] = columnItem;
			fields[i] = propertyName;
		}
		this.storeMapping = fields;
		this.cm = new Ext.grid.ColumnModel(columns);
		this.store = da.getPanelJsonStore("supportgroup_pagepanel", obj); 
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
		this.grid = new Ext.grid.GridPanel({
			region : "center",
			store : this.store,
			cm : this.cm,
			sm : sm,
			trackMouseOver : false,
			loadMask : true,
			y : 140,
			anchor : '0 -35',
			tbar : [{
					text : "��ѯ֧����",
					scope : this,
					iconCls : "search",
					handler : this.searchSupport
				 },'-',{
					text : "�½�֧����",
					scope : this,
					iconCls : "add",
					handler : function() {
							window.location = webContext+ "/user/event/supportGroup/newsupportGroup.jsp";
					}
				 },'-',{
					text : "ɾ��֧����",
					scope : this,
					iconCls : "remove",
					handler : function() {
						var gridStore=this.store;
						Ext.Ajax.request({
								url : webContext
										+ '/supportGroupAction_confirmUserInfo.action',
								success : function(response, options) {
									var result=Ext.decode(response.responseText);
									 if(result.isAdmin=='yes'){
									 	var record = this.grid.getSelectionModel().getSelected();
										var records = this.grid.getSelectionModel().getSelections();
										if (!record) {
											Ext.Msg.alert("��ʾ", "��ѡ����Ҫɾ����֧���飡");
											return;
										}
										var ids = '';
										//����һ��ѡ����֧�������ɾ��
										for(var i = 0 ; i< records.length ; i++){
											ids += records[i].get('SupportGroup$id') + ',';
										};
										Ext.Ajax.request({
											url : webContext + '/supportGroupAction_removeSupportGroup.action',
											params :{
												ids : ids
											},
											success : function(response, options){
												var isSuccess =  Ext.decode(response.responseText).success;
												if(isSuccess){
													Ext.Msg.alert('��ʾ','ɾ���ɹ���',function(){
													   gridStore.reload();
													});
												}else{
													Ext.Msg.alert('��ʾ','ɾ��ʧ�ܣ�',function(){
														gridStore.reload();
													});
												}
											},
											failure : function(){
												Ext.Msg.alert('��ʾ','ɾ��ʧ�ܣ�');
											},
											scope:this
										},this);
						            }else{
						            	Ext.Msg.alert("��ʾ", "�Բ�������Ȩ���д˲�����");
						            	return;
						            }
							},
							scope : this,
							failure : function(response, options) {}
						}, this);
					}
			} ,   new Ext.Toolbar.TextItem("<font color=red style='font-weight:lighter' >��˫����鿴��ϸ��Ϣ��</font>")],
			bbar : this.pageBar
		});

		this.grid.on("headerdblclick", this.fitWidth, this);
		this.grid.on("rowdblclick", this.modifyby, this);
		var items = new Array();
		items.push(this.fp);
		items.push(this.grid);
		this.items = items;
		PagePanel.superclass.initComponent.call(this);
		var params = {
			'mehtodCall' : 'query',
			 deleteFlag  : 0,
			 start : 0
		};
		this.store.on('beforeload', function(a) {   
					      Ext.apply(a.baseParams,params);   
					});
		this.store.removeAll();
		this.store.load({
			params : params
		});
	},
	fitWidth : function(grid, columnIndex, e) {
		var c = columnIndex;
		var w = grid.view.getHeaderCell(c).firstChild.scrollWidth;
		for (var i = 0, i = grid.store.getCount(); i < l; i++) {
			w = Math.max(w, grid.view.getCell(i, c).firstChild.scrollWidth);
		}
		grid.colModel.setColumnWidth(c, w);
	}
});
