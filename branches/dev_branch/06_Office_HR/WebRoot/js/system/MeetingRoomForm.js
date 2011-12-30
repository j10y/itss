MeetingRoomForm = Ext.extend(Ext.Window, {
	tabPanel : null,
	gridPanel : null,
	formPanel : null,
	store : null,
	constructor : function(a) {
		if(a == null) {
			a = {};
		}
		Ext.apply(this, a);
		this.initComponents();
		MeetingRoomForm.superclass.constructor.call(this, {
			id : "MeetingRoomFormWin",
			layout : "border",
			width : 800,
			height : 450,
			modal : true,
			items : [
				this.tabPanel
			],
			buttonAlign : "center",
			buttons : this.buttons,
			listeners:{
				afterrender: function(){
					Ext.getCmp('endDate').hide();
					Ext.getCmp('allday').hide();
					Ext.getCmp('repeatWeekDay').hide();
					Ext.getCmp('weekSpan').hide();
				}
			
			}
		});
	},
	rightDate : null,
	maxDate : null,
	emptyText:"",
	initComponents : function() {
		var date = new Date();
		if(isGranted("_SelectEveryDayCalendar")){
			date.setDate(date.getDate()-1);
		}else{
			date.setDate(date.getDate()+2);
			// max
			var date_max = new Date();
			date_max.setDate(date_max.getDate()+7);
			this.maxDate  = date_max;
			this.emptyText = "您只能预订两天以后的会议!";
		}
		this.rightDate = date;
		
		this.store = new Ext.data.JsonStore({
			url : __ctxPath + "/system/listMrbsSchedule.do?Q_room.id_L_EQ=" + this.roomId,
			totalProperty : "totalCounts",
			id : "id",
			root : "result",
			remoteSort : true,
			fields : [
				{
					name : "id",
					type : "int"
				},
				"date",
				"week",
				"startHour",
				"endHour",
				"roomName",
				{
					name : "createUser",
					mapping : "createBy.fullname"
				},
				"description"
			]
		});
		this.store.setDefaultSort("startTime", "asc");
		this.store.load({
			params : {
				start : 0,
				limit : 25
			}
		});
		var b = new Array();
		if(isGranted('_DeleteOrderRoom')){
			b.push({
				iconCls : "btn-del",
				qtip : "删除",
				style : "margin:0 3px 0 3px"
			});
		}
		this.rowActions = new Ext.ux.grid.RowActions({
			header : "管理",
			width : 80,
			actions : b
		});
		
		var a = new Ext.grid.ColumnModel({
			columns : [
				new Ext.grid.RowNumberer(),
				{
					header : "id",
					dataIndex : "id",
					hidden : true
				}, {
					header : "日期",
					dataIndex : "date"
				}, {
					header : "星期",
					dataIndex : "week"
				}, {
					header : "开始",
					dataIndex : "startHour"
				}, {
					header : "结束",
					dataIndex : "endHour"
				}, {
					header : "预订者",
					dataIndxe : "createUser"
				}, {
					header : "会议主题",
					dataIndex : "description"
				},
				this.rowActions
			],
			defaults : {
				sortable : true,
				menuDisable : false,
				width : 100
			}
		});
		this.gridPanel = new Ext.grid.GridPanel({
			id : "MeetingRoomGrid",
			title : this.roomName + "已预订详情",
			store : this.store,
			autoWidth : true,
			autoHeight : true,
			stripeRows : true,
			closeable : true,
			trackMouseOver : true,
			disableSelection : false,
			loadMask : true,
			plugins : this.rowActions,
			cm : a,
			viewConfig : {
				forceFit : true,
				enableRowBody : false,
				showPreview : false
			},
			bbar : new Ext.PagingToolbar({
				pageSize : 25,
				store : this.store,
				displayInfo : true,
				displayMsg : "当前显示{0}至{1}，共{2}条记录",
				emptyMsg : "当前没有记录"
			})
		});
		this.rowActions.on("action", this.onRowAction, this);
		if(isGranted('_EditOrderRoom')){
			this.gridPanel.addListener("rowdblclick", function(grid, index, event) {
				grid.getSelectionModel().each(function(item) {
					//this.tabPanel.activate(this.formPanel);
					//alert(item.data.id);return;
					Ext.getCmp('MeetingRoomTabPanel').activate(Ext.getCmp('orderFormPanel'));
					Ext.getCmp('orderFormPanel').getForm().load({
						deferredRender : false,
						url : __ctxPath + "/system/getMrbsSchedule.do?id=" + item.data.id,
						waitMsg : "正在载入数据……",
						success : function(f, d) {
							var e = Ext.util.JSON.decode(d.response.responseText);
							//alert(d.response.responseText);
							//去掉 重复预订 选项
							Ext.getCmp('repeat_order').hide();
							//去掉 每天 选项
							//Ext.getCmp('hasEveryDay').removeAll();
							//去掉 每周 选项
							//Ext.getCmp('hasEveryWeek').removeAll();
							//去掉 隔N周 选项
							//Ext.getCmp('hasSpanWeek').removeAll();
							// 更改 预订 面板 title
							Ext.getCmp('orderFormPanel').setTitle("修改预订");
							// 更改 提交 按钮
							Ext.getCmp('submit_btn').setText('保存');
							//<--更改 提交 按钮 事件
							Ext.getCmp('submit_btn').setHandler(function(){
										Ext.getCmp('orderFormPanel').getForm().submit({
											url : __ctxPath+"/system/saveMrbsSchedule.do",
											params: {
	       										 mrbsSchedule_id : '1234'
	    									},
											method : "post",
											waitMsg : "正在提交数据……",
											success : function() {
												Ext.ux.Toast.msg("提示信息","保存成功！");
												Ext.getCmp('MeetingRoomFormWin').close();
												Ext.getCmp('areaTabPanel').removeAll();
												Ext.getCmp('MeetingRoomView').search(Ext.getCmp('MeetingRoomView'),new Date().format("Y-m-d"));
											},
											failure : function(c, d) {
												Ext.MessageBox.show({
													title : "操作信息",
													msg : d.result.msg,
													buttons : Ext.MessageBox.OK,
													icon : Ext.MessageBox.ERROR
												});
												return ;
											}
										});
							});//-->
							
						},
						failure : function() {
							
						}
				});
				});
			});
		}
		this.formPanel = new Ext.FormPanel({
			id:'orderFormPanel',
			title : "预订",
			layout : "form",
			url:__ctxPath+"/system/saveMrbsRepeat.do",
			defaultType : "textfield",
			bodyStyle : "padding:10px",
			autoScroll:true,
			defaults : {
				anchor : "95%,95%"
			},
			items : [{
					name : 'repeat_id',
					  id : 'repeatId',
					xtype : 'hidden'
				},
				{
					name : 'schedule_id',
					  id : 'scheduleId',
					xtype : 'hidden'
				},
				{
					name : 'mrbsRepeat.room.id',
					  id : 'roomId',
					  fieldLabel:'会议室ID',
						xtype : 'hidden',
						value : this.roomId	
				},{
					name : 'mrbsRepeat.room.roomName',
					id	: 'roomName',
					fieldLabel:'会议室',
					xtype : 'hidden',
					value : this.roomName
				},{
					name : 'mrbsRepeat.orderman',
					id 	:'orderman',
					fieldLabel : '会议组织者',
					value : curUserInfo.fullname,
					readOnly : true,
					allowBlank:false
				},{
					name : 'mrbsRepeat.description',
					id 	:'description',
					fieldLabel : '会议主题',
					xtype : 'textarea',
					emptyText:'请输入会议主题',
					value :'',
					allowBlank:false
				},{
					xtype:'container',
					layout:'form',
					border:false,
					items:[{
						layout:'column',
						border : false,
						items:[{
								columnWidth:0.5,
								layout : 'form',
								border : false,
								items :[{
									name : 'mrbsRepeat.num',
									id 	:'num',
									fieldLabel : '与会人数',
									width: 250,
									readOnly:true,
									xtype : 'numberfield',
									value :'',
									allowBlank:false
								}]
							},{
								columnWidth:0.5,
								layout: 'form',
								border : false,
								items: [{
									name : 'mrbsRepeat.projector',
									id : 'projector',
									defaultType: 'checkbox',
									hideLabels:true,
									value : '1',
									border: false,
									items:[{boxLabel:'投影仪',name:'mrbsRepeat.projector',inputValue:'1'}]
								}]
							}
							]
					},{
						layout:'column',
						border : false,
						fieldLabel:'与会人员名单',
						items:[{
								columnWidth:0.9,
								layout : 'form',
								border : false,
								items :[{
									layout:'column',
									border:false,
									items:[{
										name : 'attendList',
										id 	:'attendList',
										width: 250,
										xtype : 'textarea',
										readOnly:true,
										value :'',
										allowBlank:false
									},{
										xtype : "button",
										text:'选择人员',
										iconCls : "btn-mail_recipient",
										name:'selectAttend',
										id : 'selectAttend',
										handler : function() {
												UserSelector.getView(function(d, c) {
													Ext.getCmp("attendList").setValue(c);
													Ext.getCmp("attendIdList").setValue(d);
													if(d){
														if(d.trim() != ""){
															Ext.getCmp('num').setValue(d.split(',').length);
														}
													}
													
												}, false).show();
										}
									},{
										xtype : "hidden",
										name : "attendIdList",
										id : "attendIdList"
									}]
									
								}]
							}
							]
					}]
				},{
					name:'mrbsRepeat.startDate',
					id : 'startDate',
					fieldLabel:'开始日期',
					xtype:'datefield',
					format: 'Y-m-d',
					 anchor:"46%",
					disableDays:[0,6],
					value : (this.start_date)?this.start_date:"",
					allowBlank:false,
					//minValue:new Date().format("Y-m-d"),
					minValue:this.rightDate,
					maxValue:this.maxDate,
					emptyText:this.emptyText
					
					
				},{
					xtype:'container',
					layout:'form',
					border:false,
					anchor:"46%",
					items:[{
						layout:'column',
						fieldLabel:'开始时间',
						border:false,
						items:[{
									columnWidth:.25,
									layout:'form',
									border:false,
									items:[{
										xtype: 'combo',
										name:'mrbsRepeat.startHour',
										hideLabel : true,
										width:50,
										border:false,
										value:(this.start_hour)?this.start_hour:"08",
										allowBlank:false,
										triggerAction : "all",
										store : [
												 ["08","08"], ["09","09"],["10","10"], ["11","11"],["12","12"], ["13","13"],["14","14"], ["15","15"],
												 ["16","16"], ["17","17"],["18","18"], ["19","19"],["20","20"]
											]
										
									}]
								},{
									columnWidth:.25,
									width:50,
									layout:'form',
									border:false,
									items:[{
										xtype :'label',
										border:false,
										text:'时'
									}]
								},{
									columnWidth:.25,
									layout:'form',
									border:false,
									items:[{
										name:'mrbsRepeat.startMini',
										xtype: 'combo',
										hideLabel : true,
										width:50,
										border:false,
										value:(this.start_mini)?this.start_mini:"00",
										allowBlank:false,
										triggerAction : "all",
										store : [
												 ["00","00"], ["05","05"],["10","10"], ["15","15"],["20","20"], ["25","25"],["30","30"], ["35","35"],
												 ["40","40"], ["45","45"],["50","50"], ["55","55"]
											]
										
									}]
								},{
									columnWidth:.25,
									width:50,
									layout:'form',
									border:false,
										items:[{
										xtype :'label',
										border:false,
										text:'分'
									}]
								}]
					}]
					
				},{
					xtype:'container',
					layout:'form',
					border:false,
					anchor:"46%",
					items:[{
						layout:'column',
						fieldLabel:'结束时间',
						border:false,
						items:[{
									columnWidth:.25,
									layout:'form',
									border:false,
									items:[{
										name:'mrbsRepeat.endHour',
										xtype: 'combo',
										hideLabel : true,
										width:50,
										border:false,
										value:(this.end_hour)?this.end_hour:"20",
										allowBlank:false,
										triggerAction : "all",
										store : [
												 ["08","08"], ["09","09"],["10","10"], ["11","11"],["12","12"], ["13","13"],["14","14"], ["15","15"],
												 ["16","16"], ["17","17"],["18","18"], ["19","19"],["20","20"]
											]
									}]
								},{
									columnWidth:.25,
									width:50,
									layout:'form',
									border:false,
									items:[{
										xtype :'label',
										border:false,
										text:'时'
									}]
								},{
									columnWidth:.25,
									layout:'form',
									border:false,
									items:[{
										name:'mrbsRepeat.endMini',
										xtype: 'combo',
										hideLabel : true,
										width:50,
										border:false,
										value:(this.end_mini)?this.end_mini:"00",
										allowBlank:false,
										triggerAction : "all",
										store : [
												 ["00","00"], ["05","05"],["10","10"], ["15","15"],["20","20"], ["25","25"],["30","30"], ["35","35"],
												 ["40","40"], ["45","45"],["50","50"], ["55","55"]
											]
									}]
								},{
									columnWidth:.25,
									width:50,
									layout:'form',
									border:false,
										items:[{
										xtype :'label',
										border:false,
										text:'分'
									}]
								}]
					}]
					
				},{
					xtype:'container',
					id:'repeat_order',
					layout:'form',
					anchor:"64%",
					border:false,
					items:[{
						layout:'column',
						fieldLabel:'重复预订',
						border:false,
						items:[{
									columnWidth:.25,
									layout:'form',
									border:false,
									items:[{
										defaultType: 'radio',
										border:false,
										items:[{
											boxLabel:'当天',
											name:'mrbsRepeat.repOpt',
											inputValue:'0',
											checked:true,
											listeners : {
															check : function() {
																if(this.checked) {
																	Ext.getCmp("endDate").hide();
																	Ext.getCmp("allday").hide();
																	Ext.getCmp("repeatWeekDay").hide();
																	Ext.getCmp("weekSpan").hide();
																} else {
																	
																}
															}
														}
										}]
									}]
								},{
									columnWidth:.25,
									layout:'form',
									border:false,
									items:[{
										id : 'hasEveryDay',
										defaultType: 'radio',
										border:false,
										items:[]
									}]
								},{
									columnWidth:.25,
									layout:'form',
									border:false,
									items:[{
										id : 'hasEveryWeek',
										defaultType: 'radio',
										border:false,
										items:[]
									}]
								},{
									columnWidth:.25,
									layout:'form',
									border:false,
									items:[{
										id : 'hasSpanWeek',
										defaultType: 'radio',
										border:false,
										items:[]
									}]
								}]
					}]
				},{
					id : 'endDate',
					xtype:'container',
					layout:'form',
					border:false,
					items:[{
						layout:'column',
						border:false,
						items:[{
							columnWidth:.15,
							layout:'form',
							border:false,
							items:[{
								xtype:'label',
								text:'结束日期:'
							}]
						},{
							columnWidth:.85,
							layout:'form',
							border:false,
							bodyStyle:"padding:0px;",
							items:[{
								hideLabel:true,
								width:246,
								name:'mrbsRepeat.endDate',
								xtype:'datefield',
								format: 'Y-m-d',
								disableDays:[0,6],
								allowBlank:true,
								minValue:this.rightDate,
								//value:new Date(),
								emptyText:this.emptyText
								
								
							}]
						}]
					}]
					
				},{
					id : 'allday',
					xtype:'container',
					layout:'form',
					border:false,
					defaults: {
						bodyStyle:'margin-top:10px'
					},
					anchor:"39%",
					items:[{
						layout:'column',
						border:false,
						items:[
						{
							columnWidth:.36,
							layout:'form',
							border:false,
							items:[{
								xtype:'label',
								border:false,
								text:'全天:'
							}]
							
						},{
							columnWidth:.31,
							layout:'form',
							border:false,
							items:[{
								defaultType:'radio',
								border:false,
								items:[{boxLabel:'是',name:'mrbsRepeat.allday',inputValue:'1'}]
							}]
							
						},{
							columnWidth:.33,
							layout:'form',
							border:false,
							items:[{
								defaultType:'radio',
								border:false,
								items:[{boxLabel:'否',name:'mrbsRepeat.allday',inputValue:'0',checked:true}]
							}]
						}]
					}]
				},{
					id : 'repeatWeekDay',
					xtype:'container',
					layout:'form',
					defaults: {
						bodyStyle:'margin-top:10px'
					},
					border:false,
					anchor:"77%",
					items:[{
						layout:'column',
						border:false,
						items:[{
							columnWidth:.18,
							layout:'form',
							border:false,
							items:[{
								xtype:'label',
								border:false,
								text:'星期几:'
							}]
							
						},{
							columnWidth:.22,
							layout:'form',
							border:false,
							items:[{
								defaultType:'radio',
								hideLabel:true,
								border:false,
								items:[{boxLabel:'星期一',name:'mrbsRepeat.repeatWeekDay',inputValue:'1',checked:true}]
							}]
							
						},{
							columnWidth:.15,
							layout:'form',
							border:false,
							items:[{
								defaultType:'radio',
								border:false,
								hideLabel:true,
								items:[{boxLabel:'星期二',name:'mrbsRepeat.repeatWeekDay',inputValue:'2'}]
							}]
						},{
							columnWidth:.15,
							layout:'form',
							border:false,
							items:[{
								defaultType:'radio',
								border:false,
								hideLabel:true,
								items:[{boxLabel:'星期三',name:'mrbsRepeat.repeatWeekDay',inputValue:'3'}]
							}]
						},{
							columnWidth:.15,
							layout:'form',
							border:false,
							items:[{
								defaultType:'radio',
								border:false,
								hideLabel:true,
								items:[{boxLabel:'星期四',name:'mrbsRepeat.repeatWeekDay',inputValue:'4'}]
							}]
						},{
							columnWidth:.15,
							layout:'form',
							border:false,
							items:[{
								width:200,
								defaultType:'radio',
								border:false,
								hideLabel:true,
								items:[{boxLabel:'星期五',name:'mrbsRepeat.repeatWeekDay',inputValue:'5'}]
							}]
						}]
					}]
				},{
					id:'weekSpan',
					xtype:'container',
					layout:'form',
					defaults: {
						bodyStyle:'margin-top:10px'
					},
					items:[{
						layout:'column',
						border:false,
						items:[{
							columnWidth:.15,
							layout:'form',
							border:false,
							items:[{
								xtype:'label',
								text:'隔周:'
							}]
						},{
							columnWidth:.85,
							layout:'form',
							border:false,
							items:[{
								hideLabel:true,
								xtype:'combo',
								name:'mrbsRepeat.weekSpan',
								store:new Ext.data.SimpleStore({
									fields:['value','text'],
									data:[['1','1'],['2','2'],['3','3'],['4','4'],['5','5']]
								}),
								displayField:'text',
								valueField:'value',
								mode:'local',
								listeners:{
									afterrender:function(){
										this.value = 1;
									}
								}
							}]
						}]
						
					}]
					
					
					
				}
			]
		});
		
		//1. if the current user has the permission of '_SelectEveryDay' ,show it 
		if(isGranted('_SelectEveryDay')){
			Ext.getCmp('hasEveryDay').add({
											boxLabel:'每天',
											name:'mrbsRepeat.repOpt',
											inputValue:'1',
											listeners : {
															check : function() {
																if(this.checked) {
																	Ext.getCmp("endDate").show();
																	Ext.getCmp("allday").show();
																	Ext.getCmp("repeatWeekDay").hide();
																	Ext.getCmp("weekSpan").hide();
																} else {
																	
																}
															}
														}
										});
		}
		
		//2.if the current user has the permission of '_SelectEveryWeek' ,show it
		if(isGranted('_SelectEveryWeek')){
			Ext.getCmp('hasEveryWeek').add({
											boxLabel:'每周',
											name:'mrbsRepeat.repOpt',
											inputValue:'2',
											listeners : {
															check : function() {
																if(this.checked) {
																	Ext.getCmp("endDate").show();
																	Ext.getCmp("allday").show();
																	Ext.getCmp("repeatWeekDay").show();
																	Ext.getCmp("weekSpan").hide();
																} else {
																	
																}
															}
														}
										});
		}
		
		// 3.if the current user has the permission of '_SelectSpanWeek' ,show it
		if(isGranted('_SelectSpanWeek')){
			Ext.getCmp('hasSpanWeek').add({
											boxLabel:'隔N周',
											name:'mrbsRepeat.repOpt',
											inputValue:'3',
											listeners : {
															check : function() {
																if(this.checked) {
																	Ext.getCmp("endDate").show();
																	Ext.getCmp("allday").show();
																	Ext.getCmp("repeatWeekDay").show();
																	Ext.getCmp("weekSpan").show();
																} else {
																	
																}
															}
														}});
		}
		
		
		this.tabPanel = new Ext.TabPanel({
			id : "MeetingRoomTabPanel",
			region : "center",
			autoScroll:true,
			activeTab : this.activeId,
			items : [
				this.gridPanel,
				this.formPanel
			]
		});
		this.buttons = [{
				id : 'submit_btn',
				text : "提交",
				handler : this.save.createCallback(this.formPanel, this)
			}, {
				text : "取消",
				handler : this.cancel.createCallback(this)
			}
		];
	},
	onRowAction :function(c, a, d, e, b) {
			//alert(a.data.id);
		Ext.Msg.confirm("信息确认", "您确认要删除所选记录吗？", function(c) {
		if(c == "yes") {
			Ext.Ajax.request({
				url : __ctxPath + "/system/deleteMrbsSchedule.do?id="+a.data.id,
				params : {
				},
				method : "post",
				success : function(ddd) {
					var ee = Ext.util.JSON.decode(ddd.responseText);
					Ext.ux.Toast.msg("提示信息", "成功删除所选记录！");
					Ext.getCmp('MeetingRoomGrid').getStore().reload();
					Ext.getCmp('areaTabPanel').removeAll();
					Ext.getCmp('MeetingRoomView').search(Ext.getCmp('MeetingRoomView'),new Date().format("Y-m-d"));
					
				},
				failure : function() {
					Ext.MessageBox.show({
						title : "操作信息",
						msg : "删除失败，请联系管理员！",
						buttons : Ext.MessageBox.OK,
						icon : Ext.MessageBox.ERROR
					});
				}
			});
		}
	});
	},
	save : function(b, a) {
		//alert(curUserInfo.rights);return false;
		if(b.getForm().isValid()) {
			b.getForm().submit({
				method : "post",
				waitMsg : "正在提交数据……",
				success : function() {
					Ext.ux.Toast.msg("提示信息","保存成功！");
					a.close();
					Ext.getCmp('areaTabPanel').removeAll();
					Ext.getCmp('MeetingRoomView').search(Ext.getCmp('MeetingRoomView'),new Date().format("Y-m-d"));
				},
				failure : function(c, d) {
					Ext.MessageBox.show({
						title : "操作信息",
						msg : d.result.msg,
						buttons : Ext.MessageBox.OK,
						icon : Ext.MessageBox.ERROR
					});
					return ;
				}
			});
		}
	},
	cancel : function(a) {
		a.close();
	}
});


