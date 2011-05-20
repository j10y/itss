PageTemplates = Ext.extend(Ext.Panel, {
	id : "PageTemplates",
	layout : 'table',
	align : 'center',
	foredit : true,
	width : 1050,
	frame : true,
	autoScroll : true,
	defaults : {
		bodyStyle : 'padding:4px'
	},
	layoutConfig : {
		columns : 1 
	},
	getFormnewsupportgroup_pagepanel : function() {
		var da = new DataAction();
		var data = null;
		// 判断是新增还是修改
		var biddata = "";
		if (this.dataId !="") {
			data = da.getPanelElementsForEdit("newsupportgroup_pagemodel",
					"newsupportgroup_pagepanel", this.dataId);// 这是要随时变得
			biddata = da.split(data);
		} else {
			data = da.getPanelElementsForAdd("newsupportgroup_pagepanel");
			biddata = da.split(data);
		}
		this.formnewsupportgroup_pagepanel = new Ext.form.FormPanel({
				id : 'newsupportgroup_pagepanel',
				layout : 'table',
				height : 'auto',
				width : 800,
				frame : true,
				collapsible : true,
				defaults : {
					bodyStyle : 'padding:4px'
				},
				layoutConfig : {
					columns : 4
				},
				title : "新建服务组面板",
				items : biddata
			});
		return this.formnewsupportgroup_pagepanel;
	},
	getSupportEngineer : function() {
		this.deliveryTeam = new Ext.form.ComboBox({
			name : "deliveryTeam",
			id : 'deliveryTeamCombo',
			width : 200,
			displayField : 'name',
			fieldLabel:"交付团队",
			valueField : 'id',
			lazyRender : true,
			allowBlank : true,
			minChars : 50,
			resizable : true,
			triggerAction : 'all',
			selectOnFocus : true,
			store : new Ext.data.JsonStore({
				url:webContext+"/configItemAction_findConfigItemExtendInfo.action?clazz=com.zsgj.itil.config.extci.entity.DeliveryTeam",
				fields:["id","name"],
				totalProperty:"rowCount",
				root:"data",
				id:"id"
			}),
			pageSize : 10,
			listeners : {
				'beforequery' : function(queryEvent) {
					var store=queryEvent.combo.store;
					store.baseParams={
						"name":queryEvent.query,
						fuzzyQuery:"name"
					};
					store.load({
						params:{
							start:0
						}
					});
					return false;
				}
			},
			initComponent : function() {
					this.store.load({
					params:{
						id:Ext.getCmp("deliveryTeamCombo").getValue(),
						start:0
					},
					callback:function(r, options, success){
						Ext.getCmp("deliveryTeamCombo").setValue(Ext.getCmp("deliveryTeamCombo").getValue());
					}
				});
			}
		});

		// 查询面板
		var servicepanel = new Ext.form.FormPanel({
			id:"serviceProviderForm",
			layout : 'table',
			height : 60,
			width : 605,
			frame : true,
			defaults : {
				bodyStyle : 'padding:4px'
			},
			layoutConfig : {
				columns : 4
			},
			title : "查询列表",
			items : [{
				html : "交付团队:",
				cls : 'common-text',
				width : 70,
				style : 'width:150;text-align:right'  
			}, this.deliveryTeam,{
				html : "工程师itcode:",
				cls : 'common-text',
				width : 100,
				style : 'width:150;text-align:right'
			},new Ext.form.TextField({
				xtype : 'textfield',
				id : 'supportGroupEngineer',
				width : 200,
				allowBlank : true
			})]
		});

		this.storeGrid = new Ext.data.JsonStore({
			url:webContext+"/supportGroupAction_findAllServiceEngineers.action",
			fields:["id","userInfo"],
			totalProperty:"rowCount",
			root:"data",
			id:"id"
		});
		
		this.pageBar = new Ext.PagingToolbar({
			pageSize : 10,
			store : this.storeGrid,
			displayInfo : true,
			displayMsg : '当前显示 {0}-{1}条记录 /共{2}条记录',
			emptyMsg : "无显示数据"
		});
		var Check = new Ext.grid.CheckboxSelectionModel();
		
		this.grid = new Ext.grid.GridPanel({
			region : "center",
			id : 'storeGrid',
			store : this.storeGrid,
			columns : [Check, {
								header : '工程师名称',
								dataIndex : 'userInfo',
								width : 400,
								sortable : true
							}],
			sm : Check,
			trackMouseOver : false,
			loadMask : true,
			height : 320,
			width : 605,
			tbar : [
					{
						text : "查询工程师",
						scope : this,
						iconCls : "search",
						handler : function() {
							var deliveryTeamId=Ext.getCmp("deliveryTeamCombo").getValue();
							var itcode = Ext.getCmp("supportGroupEngineer").getValue();
							this.storeGrid.baseParams={
												'userInfo.userName' : itcode,
												deliveryTeamId : deliveryTeamId,
												fuzzyQuery:"userInfo.userName"
											}
							this.storeGrid.load();
						}
					},'-',{
						text : "清除",
						scope : this,
						iconCls :"reset",
						handler : function() {
							Ext.getCmp("serviceProviderForm").form.reset();
						}
					},
					new Ext.Toolbar.TextItem('【<font color=red>请先选择服务商类型之后单击查询</font>】')],
			bbar : this.pageBar
		});
		this.grid.on("rowdblclick", this.modifyby, this);
		var ite = new Array();
		ite.push(servicepanel);
		ite.push(this.grid);
		this.storeGrid.load();
		
		var modifyWin = new Ext.Window({
			title : '新增服务工程师',
			modal : true,
			height : 450,
			width : 620,
			resizable : true,
			viewConfig : {
				autoFill : true,
				forceFit : true
			},
			layoutConfig : {
				columns : 1
			},
			scope : this,
			layout : 'table',
			buttons : [{
				xtype : 'button',
				handler : function() {
				    var records = this.grid.getSelectionModel().getSelections();
				    var store=Ext.getCmp('SupportGroupEngineer_pagepanel').getStore();
				    for(var i=0;i<records.length;i++){
				    	for(var j=0;j<store.getCount();j++){
				    		var record=store.getAt(j);
				    		if(record.get('id')==records[i].get('id')){
				    			store.remove(record);
				    			break;
				    		}
				    	}
				    }
				   	store.add(records);
				},
				text : '增加',
				scope : this
			}, {
				xtype : 'button',
				handler : function() {
					modifyWin.close()
				},
				text : '关闭',
				scope : this
			}],
			items : ite
		}); 
		modifyWin.show();
		return modifyWin;
	},
	deleteSupportEngineer : function() {
		var records = Ext.getCmp("SupportGroupEngineer_pagepanel")
				.getSelectionModel().getSelections();
		if (records.length < 1) {
			Ext.Msg.alert("提示", "请选择要删除的工程师！");
			return;
		}
		Ext.MessageBox.confirm('提示', '确定删除？', function(button, text) {
			if(button=='yes'){
				var engineerGrid=Ext.getCmp('SupportGroupEngineer_pagepanel');
				var records = engineerGrid.getSelectionModel().getSelections();
			    var store=engineerGrid.getStore();
			    for(var i=0;i<records.length;i++){
			    	store.remove(records[i]);
			    }
			}
		}, this);
	},
	getGridComSupportGroupEngineer_pagepanel : function() {
		this.store= new Ext.data.JsonStore({
			url : webContext
					+ '/supportGroupAction_findEngineersBySupportGroupId.action',
			root : "data",
			fields : ['id', 'userInfo']
		});

		this.sm = new Ext.grid.CheckboxSelectionModel();
		this.cm=new Ext.grid.ColumnModel([this.sm,{
				header : '工程师名称',
				dataIndex : 'userInfo',
				width:400,
				sortable: true
			}]);
		this.gridComSupportGroupEngineer_pagepanel = new Ext.grid.EditorGridPanel({
			id : 'SupportGroupEngineer_pagepanel',
			store : this.store,
			cm : this.cm,
			sm : this.sm,
			title : "服务组工程师",
			trackMouseOver : false,
			loadMask : true,
			width : 800,
			height:250,
			frame : true,
			tbar : [{
				text : "<font color=red>添加工程师</font>",
				scope : this,
				iconCls : "add",
				handler : this.getSupportEngineer
			},'-', {
				text : "<font color=blue>删除工程师</font>",
				scope : this,
				iconCls : "remove",
				handler : this.deleteSupportEngineer
			}]
		});
		if (this.dataId !="") {
			this.store.load({
				params : {
					supportGroupId : this.dataId
				}
			});
		}
		return this.gridComSupportGroupEngineer_pagepanel;
	},
	getDoublepanel : function() {
		var da = new DataAction();
		var todata=[];
		if(this.dataId!=""){
			var url = webContext
					+ '/supportGroupAction_findAllSupportGroupServiceItem.action?supportId='
					+ this.dataId;
			todata = da.ajaxGetData(url);
		}
		url = webContext + '/supportGroupAction_serviceItemData.action?official=1';
		var fromdata = da.ajaxGetData(url);
		var newFromData=new Array();
		toNext:for (i = 0; i < fromdata.length; i++) {
			for (j = 0; j < todata.length; j++) {
				if (fromdata[i][0] == todata[j][0]) {
					continue toNext;
				}
			}
			newFromData.push(fromdata[i]);
		}
		fromdata=newFromData;
		var item = {
			id:"serviceItem",
			colspan : 2,
			xtype : "itemselector",
			iconUp:webContext+"/user/event/supportGroup/multisel/up2.gif",
			iconDown:webContext+"/user/event/supportGroup/multisel/down2.gif",
			iconLeft:webContext+"/user/event/supportGroup/multisel/left2.gif",
			iconRight:webContext+"/user/event/supportGroup/multisel/right2.gif",
			iconTop:webContext+"/user/event/supportGroup/multisel/top2.gif",
			iconBottom:webContext+"/user/event/supportGroup/multisel/bottom2.gif",
			name : "serviceItem",// 提交时的字段名
			dataFields : ["id", "name"],
			fromData : fromdata,
			toData : todata,
			msWidth : 360,// 注意是每个页面的宽度
			style : "margin: 0,23,10,13",
//			autoScroll : true,
			msHeight : 220,
			frame : true,
			valueField : "id",
			displayField : "name",
			toLegend : "<font color=green>已选服务项</font>",
			fromLegend : "<font color=green>可选服务项</font>"
		};
		var panel = new Ext.form.FormPanel({
			layout : 'table',
			height : 270,
			id : 'double',
			title : "支持组默认服务项选择【<font style='font-weight:lighter' color = red >双击即可选择服务项到相应的组</font>】",
			width : 800,
			frame : true,
			defaults : {
				bodyStyle : 'padding:4px'
			},
			layoutConfig : {
				columns : 2
			},
			items : item
		})
		return panel;

	},
	saveAll : function() {
		Ext.getCmp("saveButton").disable();
		Ext.getCmp("backButton").disable();
		var records = Ext.getCmp('SupportGroupEngineer_pagepanel').getStore().getRange();
		var supportGroupForm = Ext.getCmp("newsupportgroup_pagepanel").getForm();
		var serviceItems = Ext.getCmp('serviceItem').getValue();
    	if(!supportGroupForm.isValid()){
			Ext.getCmp("saveButton").enable();
			Ext.getCmp("backButton").enable();
			Ext.Msg.alert("提示","带红色波浪线的选项为必填！");
			return;
		}
		if(records.length == 0){
			Ext.getCmp("saveButton").enable();
			Ext.getCmp("backButton").enable();
			Ext.Msg.alert("提示","请添加工程师！");
			return;
		}
		if(serviceItems.length == 0){
			Ext.getCmp("saveButton").enable();
			Ext.getCmp("backButton").enable();
			Ext.Msg.alert("提示","至少选择一个默认服务项！");
			return;
		}
		var engineersId = new Array();
		for(var i = 0;i < records.length;i++){
			engineersId.push(records[i].get('id'));
		}
		var supportGroup = Ext.encode(getFormParam('newsupportgroup_pagepanel'));
		engineersId = Ext.encode(engineersId);
	    Ext.Ajax.request({
				url : webContext+'/supportGroupAction_saveOrModifySupportGroup.action',
				params : { 
					supportGroup : supportGroup,
					engineersId : engineersId,
					serviceItemsId : serviceItems,
					supportGroupId : this.dataId
			  },
				success : function(response, options) {
					Ext.getCmp("saveButton").enable();
					Ext.getCmp("backButton").enable();
					if(response.responseText != ""){
						Ext.MessageBox.alert("提示","您选择的以下服务项已有一级支持组："+response.responseText);
						return;
					}
					Ext.MessageBox.alert("提示","保存成功！",function(){//2010-09-19 modified by huzh for bug
						window.location = webContext +"/user/event/supportGroup/supportGroup.jsp"
					});
				},
				failure : function(response, options) {
					Ext.getCmp("saveButton").enable();
					Ext.getCmp("backButton").enable();
					Ext.MessageBox.alert("提示","保存失败！");
				}
	   		});

	},
	items : this.items,
	initComponent : function() {
		var items = new Array();
		var pa = new Array();
		this.pa = pa;
		var gd = new Array();
		this.gd = gd;
		var temp = new Array();
		this.temp = temp;
		var formname = new Array();
		this.formname = formname;
		var gridname = new Array();
		this.gridname = gridname;
		this.model = "newsupportgroup_pagemodel";
		this.mybuttons = {
			layout : 'table',
			height : 'auto',
			width : 600,
			style : 'margin:4px 6px 4px 335px',
			align : 'center',
			defaults : {
				bodyStyle : 'padding:4px'
			},
			items : [{
						id:"saveButton",
						xtype : 'button',
						style : 'margin:4px 10px 4px 0',
						text : '保存',
						iconCls : "save",
						scope : this,
						handler : this.saveAll
					}, {
						id:"backButton",
						xtype : 'button',
						iconCls : "back",
						style : 'margin:4px 10px 4px 0',
						handler : function() {
							history.back();
						},
						text : '返回'
					}]
		};
		this.getFormnewsupportgroup_pagepanel();
		this.pa.push(this.formnewsupportgroup_pagepanel);
		this.formname.push("newsupportgroup_pagepanel");
		temp.push(this.formnewsupportgroup_pagepanel);
		this.getGridComSupportGroupEngineer_pagepanel();
		this.gd.push(this.gridComSupportGroupEngineer_pagepanel);
		this.gridname.push("SupportGroupEngineer_pagepanel");
		temp.push(this.gridComSupportGroupEngineer_pagepanel);
		var doublepanel = this.getDoublepanel();
		temp.push(doublepanel);
		items = temp;
		items.push(this.mybuttons);
		this.items = items;
		PageTemplates.superclass.initComponent.call(this);
	}
})