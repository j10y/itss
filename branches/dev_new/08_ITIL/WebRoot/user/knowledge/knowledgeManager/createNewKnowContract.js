PagePanel = Ext.extend(Ext.Panel, {
	id : "createNewKnowContract",
	closable : true,
	autoScroll : true,
	viewConfig : {
		autoFill : true,
		forceFit : true
	},
	layout : 'fit',
	getKnowledgeFrom : function() {
		var da = new DataAction();
		var data = da.getPanelElementsForAdd("KnowLedgeContract_pagepanel");
		for (var i = 0; i < data.length; i++) {
			if (data[i].name == 'KnowContract$number') {
				data[i].disabled = true;
				data[i].emptyText = "自动生成";
			}
		}
		var dataItem = da.split(data);
		this.fromPanel = new Ext.form.FormPanel({
			id : 'fromPanel',
			layout : 'table',
			height : 'auto',
			width : 820,
			frame : true,
			collapsible : true,
			defaults : {
				bodyStyle : 'padding:6px'
			},
			layoutConfig : {
				columns : 4
			},
			title : '填写合同草稿<font color="black">【</font><font color="red">~~~</font><font color="black" style="font-weight:lighter" face="楷体_GB2312">为必填项,请您填写!】</font>',
			items : dataItem
		});
		return this.fromPanel;
	},
	items : this.items,
	initComponent : function() {
		var templeteId;
		this.fromPanel = this.getKnowledgeFrom();
		this.mybutton = {
			layout : 'table',
			height : 'auto',
			width : 'auto',
			style : 'margin:4px 6px 4px 300px',
			colspan : 4,
			align : 'center',
			defaults : {
				bodyStyle : 'padding:4px'
			},
			layoutConfig : {
				columns : 4
			},
			items : [{
				xtype : 'button',
				id : 'savedaraftbutton',
				text : '暂存',
				iconCls : 'save',
				handler : function() {
					var name=Ext.getCmp('KnowContract$name');
					if(name.getValue().trim()==""){
						name.setValue('');
					}
					if (!Ext.getCmp('fromPanel').form.isValid()) {
						Ext.MessageBox.alert("提示", "带红色波浪线的部分为必填项！");
						return false;
					}
					Ext.getCmp('savedaraftbutton').disable();
					Ext.getCmp('submitbutton').disable();
					Ext.getCmp('backbutton').disable();
					if (Ext.getCmp("fromPanel").getForm().isValid()) {
						var knowContractParam = Ext.encode(getFormParam('fromPanel'));
						Ext.Ajax.request({
							url : webContext
									+ '/knowledgeAction_createKnowContract.action',
							params : {
								knowContractParam : knowContractParam
							},
							method : 'post',
							success : function(response) {
								Ext.MessageBox.alert("提示", "保存草稿成功！",function(){
								Ext.getCmp('savedaraftbutton').enable();
								Ext.getCmp('submitbutton').enable();
								window.location = webContext
											+ '/user/knowledge/knowledgeManager/knowContractManager.jsp';
								});
							},
							failure : function(response, options) {
								Ext.getCmp('savedaraftbutton').enable();
								Ext.getCmp('submitbutton').enable();
								Ext.getCmp('backbutton').enable();
								Ext.MessageBox.alert("提示", "保存草稿失败！");
							}
						});
					}
				},
				scope : this
			}, {
				xtype : 'button',
				id : 'submitbutton',
				text : '提交',
				iconCls : 'submit',
				handler : function() {
					var name=Ext.getCmp('KnowContract$name');
					if(name.getValue().trim()==""){
						name.setValue('');
					}
					if (!Ext.getCmp('fromPanel').form.isValid()) {
						Ext.MessageBox.alert("提示", "带红色波浪线的部分为必填项！");
						return false;
					}
					Ext.getCmp('savedaraftbutton').disable();
					Ext.getCmp('submitbutton').disable();
					Ext.getCmp('backbutton').disable();
					var knowContractParam = Ext.encode(getFormParam('fromPanel'));
					Ext.Ajax.request({
						url : webContext
								+ '/knowledgeAction_createKnowContract.action',
						params : {
							knowContractParam : knowContractParam
						},
						method : 'post',
						success : function(response) {
							var dataId  = eval('(' + response.responseText + ')').dataId;
							var dataType = 6;//
							Ext.Ajax.request({
								url : webContext
										+ '/knowledgeWorkflow_apply.action',
								params : {
									dataId : dataId,
									model : this.model,
									bzparam : "{dataId :'"
											+ dataId
											+ "',dataType : '"
											+ dataType
											+ "',applyId : '"
											+ dataId
											+ "', applyType: 'kproject',applyTypeName: '知识审批',customer:''}",
									defname : 'KnowContractProcess1306215801136'
								},
								success : function(response) {
									var meg = Ext.decode(response.responseText);
									if (meg.Exception != undefined) {
										Ext.Msg.alert("提示", meg.Exception);
									} else {
										Ext.Msg.alert("提示", "提交成功！",
												function() {
													window.location = webContext
																+ '/user/knowledge/knowledgeManager/knowContractManager.jsp';
												});
									}
								}
							});

						},
						failure : function(response, options) {
							Ext.getCmp('savedaraftbutton').enable();
							Ext.getCmp('submitbutton').enable();
							Ext.getCmp('backbutton').enable();
							Ext.MessageBox.alert("提示", "保存草稿失败！");
						}
					});
				},
				scope : this
			}, {
				xtype : 'button',
				text : '返回',
				iconCls : 'back',
				id : 'backbutton',
				handler : function() {
						window.location = webContext
							+ '/user/knowledge/knowledgeManager/knowContractManager.jsp';
				},
				scope : this
			}]
		};
		this.Panelss = new Ext.Panel({
					id : "knowledge",
					align : 'center',
					layout : 'table',
					border : true,
					autoScroll : true,
					defaults : {
						bodyStyle : 'padding:5px'
					},
					width : 720,
					height : 'auto',
					frame : true,
					layoutConfig : {
						columns : 1
					},
					items : [this.fromPanel, this.mybutton]
				});

		this.items=[this.Panelss];
		PagePanel.superclass.initComponent.call(this);
	}
});