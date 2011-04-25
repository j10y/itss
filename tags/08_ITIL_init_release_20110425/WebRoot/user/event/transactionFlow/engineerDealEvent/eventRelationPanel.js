EventRelationPanel = Ext.extend(Ext.Panel, {
	id : "eventRelationTab",
	associateventId : '',
	title : '事件关系指定',
	layout : 'table',
	height : 'auto',
	autoScroll : true,
	align : 'center',
	foredit : true,
	width : 'auto',
	frame : true,
	defaults : {
		bodyStyle : 'padding:4px',
		overflow : 'auto'
	},
	layoutConfig : {
		columns : 1
	},
	items : this.items,
	
	opeate : function(eventReId,parentEventId,eventRelTypeId) {
		new Ext.form.Hidden({
				xtype : 'hidden',
				id : 'eventRelId',
				name : 'eventRelId'
			})
		var thisEventId = this.EventId;
		// 事件的下拉列表
		var rel_eventCombo = new Ext.form.ComboBox({
			xtype : 'combo',
			hiddenName : 'rel_event',
			id : 'rel_eventCombo',
			width : 200,
			style : '',
			fieldLabel : '事件',
			lazyRender : true,
			displayField : 'summary',
			valueField : 'id',
			emptyText : '请选择...',
			allowBlank : false,
			//typeAhead : true,
			name : 'rel_event',
			resizable : true,
			triggerAction : 'all',
			minChars : 50,
			queryDelay : 700,
			store : new Ext.data.JsonStore({
				url : webContext + '/eventAction_findEvents.action',
				fields : ['id', 'summary'],
				listeners : {
					beforeload : function(store, opt) {
						if (opt.params['rel_event'] == undefined) {
							opt.params['summary'] = Ext.getCmp('rel_eventCombo').defaultParam;
						}
					}
				},
				totalProperty : 'rowCount',
				root : 'data',
				id : 'id'
			}),
			pageSize : 10,
			listeners : {
				'beforequery' : function(queryEvent) {
					var param = queryEvent.combo.getRawValue();
					this.defaultParam = param;
					if (queryEvent.query == '') {
						param = '';
					}
					this.store.load({
						params : {
							summary : param,
							start : 0
						}
					});
					return true;
				},
				'select' : function(combo, record, index){
					var curEventId = record.get('id');
					if(curEventId == thisEventId){
						Ext.Msg.alert("提示","相同事件不能添加关系！");
						Ext.getCmp('rel_eventCombo').clearValue();
					}
					Ext.getCmp('panel_event_suppGroupEngineer').load({
						url : webContext+ '/extjs/panelData?method=findPanelDataById&panelName=panel_event_suppGroupEngineer&dataId='
								+ curEventId,
						timeout : 30,
						success : function(action, form) {
							Ext.getCmp("Event$submitUser2Combo").initComponent();
							Ext.getCmp("Event$dealuser2Combo").initComponent();
							Ext.getCmp("Event$createUser2Combo").initComponent();
							Ext.getCmp("Event$frequency2Combo").initComponent();
							Ext.getCmp("Event$ponderance2Combo").initComponent();
						}
					});
				}
			},
			initComponent : function() {
				this.store.load({
					params : {
						id : Ext.getCmp('rel_eventCombo').getValue(),
						start : 0
					},
					callback : function(r, options, success) {
						Ext.getCmp('rel_eventCombo').setValue(Ext.getCmp('rel_eventCombo').getValue());
					}
				});
			}
		});
		// 事件关系类型
		var eventRelTypeCombo = new Ext.form.ComboBox({
			xtype : 'combo',
			hiddenName : 'eventRelType',
			id : 'rel_eventTypeCombo',
			width : 200,
			style : '',
			fieldLabel : '事件类型',
			lazyRender : true,
			displayField : 'name',
			valueField : 'id',
			emptyText : '请选择...',
			allowBlank : false,
			//typeAhead : true,
			name : 'eventRelType',
			resizable : true,
			triggerAction : 'all',
			minChars : 50,
			queryDelay : 700,
			store : new Ext.data.JsonStore({
				url : webContext
						+ '/extjs/comboDataAction?clazz=com.digitalchina.itil.event.entity.EventRelationType',
				fields : ['id', 'name'],
				listeners : {
					beforeload : function(store, opt) {
						if (opt.params['eventRelType'] == undefined) {
							opt.params['name'] = Ext.getCmp('rel_eventTypeCombo').defaultParam;
						}
					}
				},
				totalProperty : 'rowCount',
				root : 'data',
				id : 'id'
			}),
			pageSize : 10,
			listeners : {
				'beforequery' : function(queryEvent) {
					var param = queryEvent.combo.getRawValue();
					this.defaultParam = param;
					if (queryEvent.query == '') {
						param = '';
					}
					this.store.load({
						params : {
							name : param,
							start : 0
						}
					});
					return true;
				}
			},
			initComponent : function() {
				this.store.load({
					params : {
						id : Ext.getCmp('rel_eventTypeCombo').getValue(),
						start : 0
					},
					callback : function(r, options, success) {
						Ext.getCmp('rel_eventTypeCombo').setValue(Ext.getCmp('rel_eventTypeCombo').getValue());
					}
				});
			}
		});
		// 关联关系面板
		var eventRelationPanel = new Ext.form.FormPanel({
			id : 'eventRelationPanel',
			title : '事件关系',
			layout : 'table',
			height : 'auto',
			width : 706, //2010-04-20 modified by huzh
			frame : true,
			autoScroll : true,
			defaults : {
				bodyStyle : 'padding:4px'
			},
			layoutConfig : {
				columns : 4
			},
			items : [{
				html : "选择相关事件：",
				cls : 'common-text',
				style : 'width:100;text-align:right'
			}, rel_eventCombo, {
				html : "此事件与当前事件关系：",
				cls : 'common-text',
				style : 'width:155;text-align:right'//2010-05-04 modified by huzh for 页面优化
			}, eventRelTypeCombo]
		});
		// 获取关联事件信息面板
		var formpanel_event_suppGroupEngineer = new Ext.form.FormPanel({
			id : 'panel_event_suppGroupEngineer',
			layout : 'table',
			height : 'auto',
			width : 706, //2010-04-20 modified by huzh
			frame : true,
			collapsible : true,
			defaults : {
				bodyStyle : 'padding:5px'
			},
			layoutConfig : {
				columns : 4
			},
			reader : new Ext.data.JsonReader({
				root : 'form',
				successProperty : 'success'
			}, [{name : 'Event$id2',mapping : 'Event$id'}, 
				{name : 'Event$submitUser2',mapping : 'Event$submitUser'}, 
				{name : 'Event$eventCisn2',mapping : 'Event$eventCisn'}, 
				{name : 'Event$summary2',mapping : 'Event$summary'}, 
				{name : 'Event$submitDate2',mapping : 'Event$submitDate'}, 
				{name : 'Event$dealuser2',mapping : 'Event$dealuser'}, 
				{name : 'Event$createUser2',mapping : 'Event$createUser'}, 
				{name : 'Event$frequency2',mapping : 'Event$frequency'}, 
				{name : 'Event$ponderance2',mapping : 'Event$ponderance'}, 
				{name : 'Event$description2',mapping : 'Event$description'}, 
				{name : 'Event$appendix2',mapping : 'Event$appendix'}]),
			title : "服务工程师事件信息",
			items : [{
				html : '事件提出人：',
				cls : 'common-text',
				style : 'width:100;text-align:right'
			}, new Ext.form.ComboBox({
				xtype : 'combo',
				hiddenName : 'Event$submitUser2',
				id : 'Event$submitUser2Combo',
				width : 200,
				style : '',
				fieldLabel : '事件提出人',
				readOnly : true,
				hideTrigger:true,
				lazyRender : true,
				displayField : 'userName',
				valueField : 'id',
				emptyText : '请选择...',
				allowBlank : true,
				//typeAhead : true,
				name : 'Event$submitUser2',
				resizable : true,
				triggerAction : 'all',
				minChars : 50,
				queryDelay : 700,
				store : new Ext.data.JsonStore({
					url : webContext
							+ '/extjs/comboDataAction?clazz=com.digitalchina.info.framework.security.entity.UserInfo',
					fields : ['id', 'userName'],
					listeners : {
						beforeload : function(store, opt) {
							if (opt.params['Event$submitUser2'] == undefined) {
								opt.params['userName'] = Ext.getCmp('Event$submitUser2Combo').defaultParam;
							}
						}
					},
					totalProperty : 'rowCount',
					root : 'data',
					id : 'id'
				}),
				pageSize : 10,
				listeners : {
					'beforequery' : function(queryEvent) {
						var param = queryEvent.combo.getRawValue();
						this.defaultParam = param;
						if (queryEvent.query == '') {
							param = '';
						}
						this.store.load({
							params : {
								userName : param,
								start : 0
							}
						});
						return true;
					}
				},
				initComponent : function() {
					this.store.load({
						params : {
							id : Ext.getCmp('Event$submitUser2Combo').getValue(),
							start : 0
						},
						callback : function(r, options, success) {
							Ext.getCmp('Event$submitUser2Combo').setValue(Ext.getCmp('Event$submitUser2Combo').getValue());
						}
					});
				}
			}), {
				html : '事件编号：',
				cls : 'common-text',
				style : 'width:100;text-align:right'
			}, new Ext.form.TextField({
				fieldLabel : '事件编号',
				xtype : 'textfield',
				readOnly : true,
				id : 'Event$eventCisn2',
				name : 'Event$eventCisn2',
				style : '',
				width : 200,
				value : '',
				allowBlank : true,
				validator : '',
				vtype : ''
			}), {
				html : '事件名称：',
				cls : 'common-text',
				style : 'width:100;text-align:right'
			}, new Ext.form.TextField({
				fieldLabel : '事件名称',
				xtype : 'textfield',
				readOnly : true,
				id : 'Event$summary2',
				name : 'Event$summary2',
				style : '',
				width : 200,
				value : '',
				allowBlank : true,
				validator : '',
				vtype : ''
			}), {
				html : '事件提交时间：',
				cls : 'common-text',
				style : 'width:100;text-align:right'
			}, new Ext.form.TextField({
				fieldLabel : '事件提交时间',
				xtype : 'textfield',
				readOnly : true,
				hideTrigger:true,
				id : 'Event$submitDate2',
				name : 'Event$submitDate2',
				style : '',
				width : 200,
				value : '',
				allowBlank : true,
				validator : '',
				vtype : ''
			}), {
				html : '事件处理人：',
				cls : 'common-text',
				style : 'width:100;text-align:right'
			}, 
			new Ext.form.ComboBox({
				xtype : 'combo',
				hiddenName : 'Event$dealuser2',
				id : 'Event$dealuser2Combo',
				width : 200,
				style : '',
				fieldLabel : '事件处理人',
				readOnly : true,
				hideTrigger:true,
				lazyRender : true,
				displayField : 'userName',
				valueField : 'id',
				emptyText : '请选择...',
				allowBlank : true,
				//typeAhead : true,
				name : 'Event$dealuser2',
				resizable : true,
				triggerAction : 'all',
				minChars : 50,
				queryDelay : 700,
				store : new Ext.data.JsonStore({
					url : webContext
							+ '/extjs/comboDataAction?clazz=com.digitalchina.info.framework.security.entity.UserInfo',
					fields : ['id', 'userName'],
					listeners : {
						beforeload : function(store, opt) {
							if (opt.params['Event$dealuser2'] == undefined) {
								opt.params['userName'] = Ext.getCmp('Event$dealuser2Combo').defaultParam;
							}
						}
					},
					totalProperty : 'rowCount',
					root : 'data',
					id : 'id'
				}),
				pageSize : 10,
				listeners : {
					'beforequery' : function(queryEvent) {
						var param = queryEvent.combo.getRawValue();
						this.defaultParam = param;
						if (queryEvent.query == '') {
							param = '';
						}
						this.store.load({
							params : {
								userName : param,
								start : 0
							}
						});
						return true;
					}
				},
				initComponent : function() {
					this.store.load({
						params : {
							id : Ext.getCmp('Event$dealuser2Combo').getValue(),
							start : 0
						},
						callback : function(r, options, success) {
							Ext.getCmp('Event$dealuser2Combo').setValue(Ext.getCmp('Event$dealuser2Combo').getValue());
						}
					});
				}
			}), {
				html : '事件创建人：',
				cls : 'common-text',
				style : 'width:100;text-align:right'
			}, new Ext.form.ComboBox({
				xtype : 'combo',
				hiddenName : 'Event$createUser2',
				id : 'Event$createUser2Combo',
				width : 200,
				style : '',
				fieldLabel : '事件创建人',
				readOnly : true,
				hideTrigger:true,
				lazyRender : true,
				displayField : 'userName',
				valueField : 'id',
				emptyText : '请选择...',
				allowBlank : true,
				//typeAhead : true,
				name : 'Event$createUser2',
				resizable : true,
				triggerAction : 'all',
				minChars : 50,
				queryDelay : 700,
				store : new Ext.data.JsonStore({
					url : webContext
							+ '/extjs/comboDataAction?clazz=com.digitalchina.info.framework.security.entity.UserInfo',
					fields : ['id', 'userName'],
					listeners : {
						beforeload : function(store, opt) {
							if (opt.params['Event$createUser2'] == undefined) {
								opt.params['userName'] = Ext.getCmp('Event$createUser2Combo').defaultParam;
							}
						}
					},
					totalProperty : 'rowCount',
					root : 'data',
					id : 'id'
				}),
				pageSize : 10,
				listeners : {
					'beforequery' : function(queryEvent) {
						var param = queryEvent.combo.getRawValue();
						this.defaultParam = param;
						if (queryEvent.query == '') {
							param = '';
						}
						this.store.load({
							params : {
								userName : param,
								start : 0
							}
						});
						return true;
					}
				},
				initComponent : function() {
					this.store.load({
						params : {
							id : Ext.getCmp('Event$createUser2Combo').getValue(),
							start : 0
						},
						callback : function(r, options, success) {
							Ext.getCmp('Event$createUser2Combo').setValue(Ext.getCmp('Event$createUser2Combo').getValue());
						}
					});
				}
			}), {
				html : '出现频率：',
				cls : 'common-text',
				style : 'width:100;text-align:right'
			}, new Ext.form.ComboBox({
				xtype : 'combo',
				hiddenName : 'Event$frequency2',
				id : 'Event$frequency2Combo',
				width : 200,
				style : '',
				fieldLabel : '出现频率',
				readOnly : true,
				hideTrigger:true,
				lazyRender : true,
				displayField : 'name',
				valueField : 'id',
				emptyText : '请选择...',
				allowBlank : true,
				//typeAhead : true,
				name : 'Event$frequency2',
				resizable : true,
				triggerAction : 'all',
				minChars : 50,
				queryDelay : 700,
				store : new Ext.data.JsonStore({
					url : webContext
							+ '/extjs/comboDataAction?clazz=com.digitalchina.itil.event.entity.EventFrequency',
					fields : ['id', 'name'],
					listeners : {
						beforeload : function(store, opt) {
							if (opt.params['Event$frequency2'] == undefined) {
								opt.params['name'] = Ext.getCmp('Event$frequency2Combo').defaultParam;
							}
						}
					},
					totalProperty : 'rowCount',
					root : 'data',
					id : 'id'
				}),
				pageSize : 10,
				listeners : {
					'beforequery' : function(queryEvent) {
						var param = queryEvent.combo.getRawValue();
						this.defaultParam = param;
						if (queryEvent.query == '') {
							param = '';
						}
						this.store.load({
							params : {
								name : param,
								start : 0
							}
						});
						return true;
					}
				},
				initComponent : function() {
					this.store.load({
						params : {
							id : Ext.getCmp('Event$frequency2Combo').getValue(),
							start : 0
						},
						callback : function(r, options, success) {
							Ext.getCmp('Event$frequency2Combo').setValue(Ext.getCmp('Event$frequency2Combo').getValue());
						}
					});
				}
			}), {
				html : '事件严重性：',
				cls : 'common-text',
				style : 'width:100;text-align:right'
			}, new Ext.form.ComboBox({
				xtype : 'combo',
				hiddenName : 'Event$ponderance2',
				id : 'Event$ponderance2Combo',
				width : 200,
				style : '',
				fieldLabel : '事件严重性',
				readOnly : true,
				hideTrigger:true,
				lazyRender : true,
				displayField : 'name',
				valueField : 'id',
				emptyText : '请选择...',
				allowBlank : true,
				//typeAhead : true,
				name : 'Event$ponderance2',
				resizable : true,
				triggerAction : 'all',
				minChars : 50,
				queryDelay : 700,
				store : new Ext.data.JsonStore({
					url : webContext
							+ '/extjs/comboDataAction?clazz=com.digitalchina.itil.event.entity.EventPonderance',
					fields : ['id', 'name'],
					listeners : {
						beforeload : function(store, opt) {
							if (opt.params['Event$ponderance2'] == undefined) {
								opt.params['name'] = Ext.getCmp('Event$ponderance2Combo').defaultParam;
							}
						}
					},
					totalProperty : 'rowCount',
					root : 'data',
					id : 'id'
				}),
				pageSize : 10,
				listeners : {
					'beforequery' : function(queryEvent) {
						var param = queryEvent.combo.getRawValue();
						this.defaultParam = param;
						if (queryEvent.query == '') {
							param = '';
						}
						this.store.load({
							params : {
								name : param,
								start : 0
							}
						});
						return true;
					}
				},
				initComponent : function() {
					this.store.load({
						params : {
							id : Ext.getCmp('Event$ponderance2Combo').getValue(),
							start : 0
						},
						callback : function(r, options, success) {
							Ext.getCmp('Event$ponderance2Combo').setValue(Ext.getCmp('Event$ponderance2Combo').getValue());
						}
					});
				}
			}), {
				html : '描述：',
				cls : 'common-text',
				style : 'width:100;text-align:right'
			}, new Ext.form.TextArea({
				xtype : 'textarea',
				id : 'Event$description2',
				colspan : 3,
				readOnly : true,
				name : 'Event$description2',
				width : 550,
				height : 90,
				style : '',
				value : '',
				allowBlank : true,
				validator : '',
				fieldLabel : '描述'
			}), {
				html : '附件地址：',
				cls : 'common-text',
				style : 'width:100;text-align:right'
			}, 
			
			{xtype:'panel',layout:'table',width:250,layoutConfig:{columns:4},
			fieldLabel:'附件地址',defaults:{baseCls:'margin : 10 15 12 15'},
			items:[
			{
				fieldLabel:'附件',
				xtype:'button',
				text:'<font color=red>上 传</font>',
				disabled:true,
				width:50,
				scope:this,
				handler:function(){
				var attachmentFlag = Ext.getCmp('Event$appendix').getValue();
				if(attachmentFlag==''||attachmentFlag=='null'){
					attachmentFlag = Date.parse(new Date());
					Ext.getCmp('Event$appendix').setValue(attachmentFlag);
					var ud=new UpDownLoadFile();
					//String hiddenId = String.valueOf(System.currentTimeMillis());
					ud.getUpDownLoadFileSu(attachmentFlag,'2454','com.digitalchina.info.appframework.metadata.entity.SystemFile','appendix');
				}else{
					var ud=new UpDownLoadFile();
					ud.getUpDownLoadFileSu(attachmentFlag,'2454','com.digitalchina.info.appframework.metadata.entity.SystemFile','appendix');
				}}
			},
			{id:'appendix',width:600,border : true,html:'',cls : 'common-text',style : 'width:100;text-align:left'}]}, 
//				{
//				fieldLabel : '附件地址',
//				xtype : 'button',
//				text : '<font color=red>上传</font>/<font color=green>下载</font>',
////				disabled:true,
//				width : '50',
//				scope : this,
//				handler : function() {
//					var attachmentFlag = Ext.getCmp('Event$appendix2').getValue();
//					if (attachmentFlag == ''||attachmentFlag=='null') {
//						attachmentFlag = Date.parse(new Date());
//						Ext.getCmp('Event$appendix2').setValue(attachmentFlag);
//						var ud = new UpDownLoadFile();
//						ud.getUpDownLoadFileSu(attachmentFlag, '2454','com.digitalchina.info.appframework.metadata.entity.SystemFile');
//					} else {
//						var ud = new UpDownLoadFile();
//						ud.getUpDownLoadFileSu(attachmentFlag, '2454','com.digitalchina.info.appframework.metadata.entity.SystemFile');
//					}
//				}
//			},
			new Ext.form.Hidden({
				xtype : 'hidden',
				id : 'Event$appendix2',
				name : 'Event$appendix2',
				style : '',
				value : 'null',
				fieldLabel : 'nowtime'
			}), new Ext.form.Hidden({
				xtype : 'hidden',
				id : 'Event$id2',
				colspan : 0,
				rowspan : 0,
				name : 'Event$id2',
				width : 200,
				style : '',
				value : '',
				fieldLabel : '自动编号'
			})]
		});
		if (eventReId != "") {
			formpanel_event_suppGroupEngineer.load({
				url : webContext+ '/extjs/panelData?method=findPanelDataById&panelName=panel_event_suppGroupEngineer&dataId='
						+ parentEventId,
				timeout : 30,
				success : function(action, form) {
					Ext.getCmp("Event$submitUser2Combo").initComponent();
					Ext.getCmp("Event$dealuser2Combo").initComponent();
					Ext.getCmp("Event$createUser2Combo").initComponent();
					Ext.getCmp("Event$frequency2Combo").initComponent();
					Ext.getCmp("Event$ponderance2Combo").initComponent();
					
					var da=new DataAction();
					var url=webContext
						+ '/eventAction_getFileList.action?clazzName=com.digitalchina.itil.event.entity.Event&dataId='
						+ parentEventId+"&columnName=appendix&columnid=2454&hiddenId=appendix";
					var value=da.ajaxGetData(url);
//					alert(value.file);
					document.getElementById("appendix").innerHTML=value.file;
				},
				failure:function(action, form){
					Ext.MessageBox.alert("提示","获取面板数据失败！");
			    }
			});
			Ext.getCmp('eventRelId').setValue(eventReId);
			Ext.getCmp('rel_eventCombo').setValue(parentEventId);
//			Ext.getCmp('rel_eventCombo').readOnly = true;
//			Ext.getCmp('rel_eventCombo').hideTrigger = true;
			Ext.getCmp('rel_eventTypeCombo').setValue(eventRelTypeId);
			Ext.getCmp("rel_eventCombo").initComponent();
			Ext.getCmp("rel_eventTypeCombo").initComponent();
		}else{
			var eventId = this.EventId;
			formpanel_event_suppGroupEngineer.load({
				url : webContext+ '/extjs/panelData?method=findPanelDataById&panelName=panel_event_suppGroupEngineer&dataId='
						+ this.EventId,
				timeout : 30,
				success : function(action, form) {
					Ext.getCmp("Event$submitUser2Combo").initComponent();
					Ext.getCmp("Event$dealuser2Combo").initComponent();
					Ext.getCmp("Event$createUser2Combo").initComponent();
					Ext.getCmp("Event$frequency2Combo").initComponent();
					Ext.getCmp("Event$ponderance2Combo").initComponent();
							var da=new DataAction();
					var url=webContext
						+ '/eventAction_getFileList.action?clazzName=com.digitalchina.itil.event.entity.Event&dataId='
						+ eventId+"&columnName=appendix&columnid=2454&hiddenId=appendix";
					var value=da.ajaxGetData(url);
//					alert(value.file);
					document.getElementById("appendix").innerHTML=value.file;
				}
			});
			Ext.getCmp('rel_eventTypeCombo').setValue(1);
			Ext.getCmp("rel_eventTypeCombo").initComponent();
		}
		 var eventWin = new Ext.Window({
			    id :'eventRelationWindow',
				title : '选择相关事件',
				width : 724,
				height : 389,
				//maximizable : true,
				autoScroll:true,
				items : [eventRelationPanel,formpanel_event_suppGroupEngineer],
				bodyStyle : 'padding:1px',
				buttons : [ {
					text : '保存',	
					handler : function() { 
						if(Ext.getCmp('rel_eventCombo').getValue() == ''){
								Ext.MessageBox.alert('提示','请选择关联事件！');
								return ;
							}
						if (Ext.getCmp("eventRelationPanel").getForm().isValid()) {
							var releventId = Ext.getCmp('rel_eventCombo').getValue();
							var eventRelationTypeId = Ext.getCmp('rel_eventTypeCombo').getValue();
							var curEventId = this.EventId;
		                  Ext.Ajax.request({
			                    url: webContext+'/eventAction_createEventRelation.action',
			                    params: {
			                    	   curRelId : eventReId,
						               curEventId : curEventId,
						               parentEventId : releventId,
						               eventRelationTypeId : eventRelationTypeId
					            },
			                    method:'post', 
			                    success:function(response,options){
			                    	var data = Ext.util.JSON.decode(response.responseText);
			                    	if(data.isExist=='true'){
			                    		Ext.getCmp('eventRelationTab').store.reload();
				                    	Ext.getCmp('eventRelationWindow').close();
			                    	}else{
			                    		Ext.MessageBox.alert('提示','事件关系已存在！');
			                    	}
			                     },
			                    failure:function(response,options){
				                     Ext.MessageBox.alert('提示','事件关系设置失败！');
			                     }
		                 });	
		              }
				  },
					scope : this
				},{
					text : '关闭',
					handler : function() {
						eventWin.close();
					},
					scope : this
				}]

			});
		eventWin.show();

	},

	//新增关联事件
	addRelationEvent : function(){
		this.opeate("","","");
	},
	//修改关联事件
	modifyRelationEvent : function(){
         var clazz = "com.digitalchina.itil.event.entity.EventRelation";
         var record = this.erp.getSelectionModel().getSelected();
         var records = this.erp.getSelectionModel().getSelections();
         if (!record) {
	         Ext.Msg.alert("提示", "请先选择要修改和查看的的行！");
	         return;
           }
         if (records.length == 0) {
	      Ext.MessageBox.alert('提示', '选择一条信息！');
	      return;
        }
		var eventReId=record.get("id");
	    var conn = Ext.lib.Ajax.getConnectionObject().conn;
        conn.open("POST", webContext+'/eventAction_relationEvent.action?eventId='+eventReId,false);
        conn.send(null);	
        var result = Ext.decode(conn.responseText);
        var parentEventId=result.ParentEventId;//parentEvent的id
        var eventRelationId=result.EventRelationId;//eventRelationType的id
	   	this.opeate(eventReId,parentEventId,eventRelationId);
	},
	getEventRelations : function(currentEventId){
		var sm = new Ext.grid.CheckboxSelectionModel();// 复选框
		var cm = new Ext.grid.ColumnModel([sm,{header:'id',dataIndex:'id',hidden:true,sortable:true},
											{header:'关联事件编号',dataIndex:'parentEventCisn',sortable:true},
											{header:'关联事件',dataIndex:'event',sortable:true},
											{header:'事件关系类型',dataIndex:'typeName',sortable:true},
											{header:'事件状态',dataIndex:'eventStatus',sortable:true},
											{header:'事件处理人',dataIndex:'dealUser',sortable:true},
											{header:'事件结束时间',dataIndex:'closeDate',sortable:true}
										
											]);
		this.store=new Ext.data.JsonStore({
				url : webContext + '/eventAction_getEventRelByCurrEvent.action?eventId='+currentEventId,
				fields : ['id', 'parentEventCisn','event','typeName','eventStatus','dealUser','closeDate'],
				totalProperty : 'rowCount',
				sortInfo:{field:'parentEventCisn',direction:'asc'},
				root : 'data',
				id : 'id'
				
		});

		var viewConfig = Ext.apply({
			forceFit : true
		}, this.gridViewConfig);

		this.pageBar = new Ext.PagingToolbar({
			pageSize : 10,
			store : this.store,
			displayInfo : true,
			displayMsg : '当前显示 {0}-{1}条记录 /共{2}条记录',
			emptyMsg : "无显示数据"
		});
		this.formValue = '';
		this.pageBar.formValue = this.formValue;
		//事件关系列表
		this.erp = new Ext.grid.GridPanel({
			id : "eventRelPanel",
			width : 752,
			height : 200,
			autoScroll : true, 
			store : this.store,
			collapsible : true,
			cm : cm,
			sm : sm,
            frame : true,
            title : "关联关系设定面板",
			tbar : [{
				xtype : 'button',
				style : 'margin:4px 10px 4px 0',
				text : '增加关联事件',
				scope : this,
				iconCls : 'add',
				handler : this.addRelationEvent

			},'-',{
				xtype : 'button',
				style : 'margin:4px 10px 4px 0',
				text : '修改关联事件',
				scope : this,
				iconCls : 'edit',
				handler : this.modifyRelationEvent

			},'-',{
			   xtype : 'button',
			   text : '删除关联事件',
			   scope : this,
			   iconCls : 'remove',
			   style : 'margin:4px 10px 4px 0',
			   handler : function(){
	             var clazz = "com.digitalchina.itil.event.entity.EventRelation";
		         var record = this.erp.getSelectionModel().getSelected();
		         var records = this.erp.getSelectionModel().getSelections();
		         if (!record) {
			         Ext.Msg.alert("提示", "请先选择要删除的行！");
			         return;
		           }
		         if (records.length == 0) {
			      Ext.MessageBox.alert('提示', '最少选择一条信息，进行删除！');
			      return;
		        }
		         var id = new Array();
		         var da = new DataAction();
		         var firm  =Ext.Msg.confirm('提示框', '您确定要进行删除操作吗?', function(button) {
		      	    if (button == 'yes') {
				        for(var i=0;i<records.length;i++){	
					        id[i] = records[i].get("id");
				      Ext.Ajax.request({
						  url : webContext + '/eventAction_removeEventRelation.action',
						  params : {id:id[i]},
						  timeout:100000, 
						  success:function(response){
	                       		Ext.Msg.alert("提示","数据删除成功！",function(){
	                       				Ext.getCmp("eventRelPanel").getStore().reload(); 
	                       		});
	                        
	                      },scope:this
	                      });
				    }
			       }
		          }, this);
			    }
		      }
			],
		bbar : this.pageBar
		});	
		var params = {
			'start' : 0
		};
//		this.pageBar.formValue = param;
		this.store.on('beforeload', function(a) {   
					      Ext.apply(a.baseParams,params);   
			});
		
		this.store.removeAll();
		this.store.load({
			params : params
		});
		return this.erp;
	},
	
	initComponent : function() {
		this.erfp = new Ext.form.FormPanel({
			id : 'erfp',
			title : '事件关系',
			layout : 'table',
			height : 'auto',
		    width : 780,
			frame : true,
			autoScroll : true,
			defaults : {
				bodyStyle : 'padding:4px',
				overflow : 'auto'
			},
			layoutConfig : {
				columns : 1
			},		
			items : this.getEventRelations(this.EventId)
				
		});
		this.items = [this.erfp];
		this.erp.on("rowdblclick",this.modifyRelationEvent,this);
		EventRelationPanel.superclass.initComponent.call(this);
	}
	
});
	