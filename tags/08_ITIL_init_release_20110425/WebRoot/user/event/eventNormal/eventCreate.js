PagePanel = Ext.extend(Ext.Panel, {
	id : "PageTemplates",
	frame : true,
	title : "事件反馈信息表【<font style='font-weight:lighter' color=red >~~~为必填项，为帮我们准确定位及时解决问题，请您填写！</font>】",
	autoScroll : true,
	defaults : {
		bodyStyle : 'padding:4px'
	},
	layout : 'border',

	getPanel : function() {
		this.eventType = new Ext.form.ComboBox({
			name : "eventtype",
			id : 'eventtype',
			fieldLabel : "事件类型",
			width : 200,
			displayField : 'name',
			allowBlank : false,
			valueField : 'id',
			resizable : true,
			emptyText : '请从下拉列表中选择...',
			triggerAction : 'all',
			store : new Ext.data.JsonStore({
				url : webContext + '/eventAction_findAllEventTypeByName.action',
				fields : ['id', 'name'],
				totalProperty : 'rowCount',
				root : 'data',
				id : 'id'
			}),
			pageSize : 10,
			listeners : {
				'beforequery' : function(queryEvent) {
					// Ext.getCmp('eventtype').clearValue();
					var param = queryEvent.combo.getRawValue();
					if (queryEvent.query == '') {
						param = '';
					}
					this.store.baseParams = {
						"typeName" : param,
						type : "combox"
					};// 2010-05-31 modified by huzh for bug
					this.store.load();
					return false;
				},
				'select' : function() {
					var sstore = Ext.getCmp("solutionGrid").getStore();
					var eventtypeId = Ext.getCmp("eventtype").getValue();
					var params = {
						eventtypeId : eventtypeId,
						start : 0
					};
					// Ext.getCmp("knowPageBar").formValue=params;
					sstore.on('beforeload', function(a) {
								Ext.apply(a.baseParams, params);
							});

					sstore.load({
								params : params
							});
				}
			}
		});

		var panel = new Ext.form.FormPanel({
					height : 30,
					region : 'north',
					id : 'servicePanel',
					// border:false,
					layout : 'table',
					layoutConfig : {
						columns : 2
					},
					defaults : {
						bodyStyle : 'padding:5px'
					},
					items : [{
								html : "事件类型:",
								cls : 'common-text',
								width : 90,
								style : 'width:100;text-align:right'
							}, this.eventType]
				});
		return panel;
	},
	lookInfo : function() {
		var record = this.grid.getSelectionModel().getSelected();
		var dataId = record.get("id");
		var da = new DataAction();
		var data = da.getKnowledgeFormPanelElementsForEdit(
				"CCCallInfoSolutionForm_pagepanel", dataId);
		var konwledgecontext = "";
		for (i = 0; i < data.length; i++) {
			if (data[i].id == "Knowledge$resolvent") {
				konwledgecontext = data[i].value;
			}
		}
		var windowSkip = new Ext.Window({
			title : '查看详细信息',
			maximizable : true,
			autoScroll : true,
			width : 600,
			height : 400,
			modal : true,
			items : [{
						html : konwledgecontext
					}],
			bodyStyle : 'padding:4px',
			buttons : [{
						text : '关闭',
						handler : function() {
							windowSkip.close();
						},
						scope : this
					}, {
						handler : function() {
							Ext.getCmp("useKnowButton").disable();
							Ext.Ajax.request({
								url : webContext
										+ '/knowledgeAction_useSolution.action',
								params : {
									SolutionID : dataId
								},
								success : function(response, options) {
									Ext.MessageBox.alert("提示",
											"成功使用该解决方案解决一个事件！", function() {
												windowSkip.close();
											});
								},
								scope : this,
								failure : function(response, options) {
									Ext.MessageBox.alert("提示", "保存失败！");
									Ext.getCmp("useKnowButton").enable();
								}
							}, this);
						},
						text : '使用',
						id : 'useKnowButton',
						style : 'width:80;text-align:right',
						scope : this
					}]
		});
		windowSkip.show();
	},

	getEventPanel : function() {
		var da = new DataAction();
		var data = da.getPanelElementsForAdd("eventpage");
		for (var i = 0; i < data.length; i++) {
			if (data[i].name == "Event$summary") {
				data[i].fieldLabel = "事件名称";
			}
			if (data[i].name == "Event$ponderance") {
				data[i].emptyText = '请从下拉列表中选择...';
			}
			if (data[i].name == "Event$description") {
				data[i].fieldLabel = "事件描述";
				data[i].width = 9999;
				data[i].allowBlank = false;
				data[i].emptyText = '描述性文字请限制在4000字以内';
			}
		}
		Ext.getCmp('Event$summary').allowBlank = false;
		Ext.getCmp('Event$summary').setValue("");
		if (this.isSupportEngineer == "no") {// 不是支持组工程师
			for (var k = 0; k < data.length; k++) {
				if (data[k].name == "Event$ponderance") {
					data.remove(data[k]);
				}
			}
		}

		var biddata = this.split(data);
		this.mybuttons = this.getButtons();
		var cpanel = new Ext.form.FormPanel({
					id : 'cataPanel',
					layout : 'table',
					height : 307,
					width : 570,
					buttonAlign : 'center',
					buttons : this.mybuttons,
					bodyStyle : 'padding:2px,2px,0px,2px',
					autoScroll : true,
					frame : true,
					layoutConfig : {
						columns : 4
					},
					items : biddata
				});
		var xpanel = new Ext.Panel({
					layout : 'table',
					frame : true,
					autoScroll : true,
					layoutConfig : {
						columns : 2
					},
					region : 'center',
					items : [this.getGrid(), cpanel]
				})
		return xpanel;
	},

	getGrid : function() {
		this.storeList = new Ext.data.JsonStore({
					url : webContext
							+ '/knowledgeAction_findKnowledgeByEventType.action',
					fields : ['id', 'summary'],
					totalProperty : 'rowCount',
					root : 'data',
					id : 'id'
				});
		this.cm = new Ext.grid.ColumnModel([{
					header : "具体问题",
					dataIndex : "summary",
					width : 240
				}]);
		var viewConfig = Ext.apply({
					forceFit : true
				}, this.gridViewConfig);
		this.pageBar = new Ext.PagingToolbar({
					id : "knowPageBar",
					pageSize : 10,
					store : this.storeList,
					displayInfo : false
				});
		this.grid = new Ext.grid.GridPanel({
			store : this.storeList,
			id : "solutionGrid",
			title : "相应解决方案【<font style='font-weight:lighter' color=red >双击后查看详细信息</font>】",
			cm : this.cm,
			autoScroll : true,
			loadMask : true,
			frame : true,
			height : 307,
			width : 280, // 2010-04-22 modified by huzh for 去掉横向滚动条
			viewConfig : {
				forceFit : true
			},
			bbar : this.pageBar
		});

		var eventtypeId = null;
		if (Ext.getCmp("eventtype").getValue() != '') {
			eventtypeId = Ext.getCmp("eventtype").getValue();
		}
		var params = {
			eventtypeId : eventtypeId,
			start : 0
		};
		// this.pageBar.formValue = param;
		this.storeList.on('beforeload', function(a) {
					Ext.apply(a.baseParams, params);
				});
		this.storeList.removeAll();
		this.storeList.load({
					params : params
				});
		this.grid.on("rowdblclick", this.lookInfo, this);
		return this.grid;
	},

	getButtons : function() {
		return [{
			xtype : 'button',
			style : 'width:50;text-align:right;margin-left:20px',
			text : '提交',
			scope : this,
			iconCls : 'submit',
			id : 'postButton',
			handler : function() {
				Ext.getCmp('postButton').disable();
				// 2010-04-28 modified by huzh for 验证修改 begin
				if (Ext.getCmp("cataPanel").getForm().isValid()
						&& Ext.getCmp("servicePanel").getForm().isValid()) {
					var eventtype = Ext.getCmp('eventtype');
					if (eventtype.getValue() == "") {
						eventtype.clearValue();
						Ext.MessageBox.alert("提示", "请从下拉列表中选择正确的问题类，手输无效！");
						Ext.getCmp("postButton").enable();
						return;
					}
					// 2010-04-28 modified by huzh for 验证修改 end
					// var panelparam = Ext.encode(Ext.getCmp("cataPanel").form.getValues(false));
					var panelparam = Ext.encode(getFormParam('cataPanel'));
					panelparam = unicode(panelparam);
					Ext.Ajax.request({
						waitTitle : "请稍后",
						waitMsg : " 问题正在提交请稍后........",
						url : webContext + '/eventAction_submitEvent.action',
						method : "POST",
						params : {
							panelparam : panelparam,
							eventtype : Ext.getCmp("eventtype").getValue()
						},
						success : function(response, options) {
							var userID = eval('(' + response.responseText + ')').userID;
							var eventName = eval('(' + response.responseText
									+ ')').eventName;
							var eventCisn = eval('(' + response.responseText
									+ ')').eventCisn;
							var eventSubmitUser = eval('('
									+ response.responseText + ')').eventSubmitUser;
							var eventSubmitDate = eval('('
									+ response.responseText + ')').eventSubmitDate
							this.dataId = eval('(' + response.responseText
									+ ')').eventId;
							var users = userID;
							if (users != null && users != '') {// 2010-04-28 modified by huzh for 有工程师存在
								Ext.Ajax.request({
									url : webContext
											+ '/eventWorkflow_apply.action',
									params : {
										dataId : this.dataId,
										model : this.model,
										bzparam : "{dataId :'"
												+ this.dataId
												+ "',users:'engineerProcess:"
												+ users
												+ "',applyId : '"
												+ this.dataId
												+ "',eventName : '"
												+ eventName
												+ "',eventSubmitUser:'"
												+ eventSubmitUser
												+ "',eventSubmitDate:'"
												+ eventSubmitDate
												+ "',eventCisn:'"
												+ eventCisn
												+ "', applyType: 'eproject',applyTypeName: '事件与问题审批',customer:'',workflowHistory:'com.digitalchina.itil.event.entity.EventAuditHis'}",
										defname : 'eventAndProblemProcess1240370895640'
									},
									success : function(response, options) {
										Ext.Msg.alert("提示",
												"您已成功提交IT问题，我们会在2工时内与您联系并解决！",
												function() {
													window.location = webContext
															+ "/eventAction_toCreatePage.action";
												});
									},
									failure : function(response, options) {
										Ext.MessageBox.alert("提示", "问题提交失败！");
										Ext.getCmp("postButton").enable();
									}
								}, this);
							} else {
								Ext.MessageBox.alert("提示", "一个支持组工程师也没有！");// 2010-04-28 modified by huzh for 没有工程师
								Ext.getCmp("postButton").enable();
							}
						},
						failure : function(response, options) {
							Ext.MessageBox.alert("提示", "提交失败！");
							Ext.getCmp("postButton").enable();
						}
					}, this);

				} else {
					Ext.MessageBox.alert("提示", "红色波浪线部分为必填项，否则无法提交！");
					Ext.getCmp("postButton").enable();
				}
			}
		}]
	},
	split : function(data) {
		var labellen = 70;
		var itemlen = 200;
		var throulen = 480;
		if (Ext.isIE) {
			throulen = 450;
		} else {
			throulen = 490;
		}
		if (data == null || data.length == 0) {
			return null;
		}
		var hid = 0;
		var hidd = new Array();
		var longData = new Array();
		var longitems = 0;
		// alert(this.dataId);
		for (i = 0; i < data.length; i++) {
			data[i].style = data[i].style == null ? "" : data[i].style;
			if (data[i].xtype == "textarea") {
				data[i].style += "'margin:2 0 2 0;'";
			}
			if (data[i].xtype == "hidden") {
				hidd[hid] = data[i];
				hid++;
				continue;
			}
			// add by lee for 为下拉列表增加可拖拽属性以修改不能看全信息的BUG in 20091112 BEGIN
			if (data[i].xtype == "combo") {
				data[i].resizable = true;
			}
			// add by lee for 为下拉列表增加可拖拽属性以修改不能看全信息的BUG in 20091112 END
			if (data[i].width == null || data[i].width == 'null'
					|| data[i].width == "") {
				data[i].style += "width:" + itemlen + "px";
				data[i].width = itemlen;
			} else {
				if (data[i].width == "9999") {// 通栏
					if ((i - hid + longitems) % 2 == 1) {// 在右侧栏，前一栏贯通
						longData[2 * (i - hid) - 1].colspan = 3;
					} else {// 多占一栏
						longitems++;
					}
					data[i].colspan = 3;// 本栏贯通
					data[i].width = throulen;
					if (data[i].xtype == "textarea") {
						data[i].height = 200;
						data[i].width = 470;
					}
					data[i].style += "width:" + throulen + "px;";
				} else {// 正常长度，半栏
					data[i].style += "width:" + itemlen + "px";
					data[i].width = itemlen;
				}
			}
			longData[2 * (i - hid)] = {
				html : data[i].fieldLabel + ":",
				cls : 'common-text',
				style : 'width:' + labellen + ';text-align:right'
			};
			longData[2 * (i - hid) + 1] = data[i];
		}
		for (i = 0; i < hidd.length; i++) {
			longData[longData.length] = hidd[i];
		}

		return longData;
	},
	items : this.items,
	initComponent : function() {
		var items = new Array();
		items.push(this.getPanel());
		items.push(this.getEventPanel());
		Ext.getCmp('eventtype').setValue("");
		this.items = items;
		PagePanel.superclass.initComponent.call(this);
	}
})
