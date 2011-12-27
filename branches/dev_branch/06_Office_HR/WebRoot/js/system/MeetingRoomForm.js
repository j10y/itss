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
	initComponents : function() {
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
				}
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
		this.formPanel = new Ext.FormPanel({
			title : "预订",
			layout : "form",
			url:__ctxPath+"/system/saveMrbsRepeat.do",
			defaultType : "textfield",
			bodyStyle : "padding:10px",
			autoScroll:true,
			defaults : {
				anchor : "95%,95%"
			},
			items : [
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
					value :'',
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
									items:[{boxLabel:'投影仪'}]
								}]
							}
							]
					},{
						layout:'column',
						border : false,
						items:[{
								columnWidth:0.5,
								layout : 'form',
								border : false,
								items :[{
									name : 'attendList',
									id 	:'attendList',
									fieldLabel : '与会人员名单',
									width: 250,
									xtype : 'textarea',
									value :'',
									allowBlank:false
								}]
							},{
								columnWidth:0.5,
								layout: 'form',
								border : false,
								items:[{
									emptyText:'请在此查询..',
									name:'selectAttend',
									id : 'selectAttend',
									xtype:'combo'
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
					value:new Date(),
					allowBlank:false
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
										xtype: 'textfield',
										name:'mrbsRepeat.startHour',
										hideLabel : true,
										width:50,
										border:false,
										value:'09',
										allowBlank:false
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
										xtype: 'textfield',
										hideLabel : true,
										width:50,
										border:false,
										value:'00',
										allowBlank:false
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
										xtype: 'textfield',
										hideLabel : true,
										width:50,
										border:false,
										value:'20',
										allowBlank:false
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
										xtype: 'textfield',
										hideLabel : true,
										width:50,
										border:false,
										value:'00',
										allowBlank:false
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
										defaultType: 'radio',
										border:false,
										items:[{
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
										}]
									}]
								},{
									columnWidth:.25,
									layout:'form',
									border:false,
									items:[{
										defaultType: 'radio',
										border:false,
										items:[{
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
										}]
									}]
								},{
									columnWidth:.25,
									layout:'form',
									border:false,
									items:[{
										defaultType: 'radio',
										border:false,
										items:[{
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
														}}]
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
							columnWidth:.2,
							layout:'form',
							border:false,
							items:[{
								xtype:'label',
								text:'结束日期'
							}]
						},{
							columnWidth:.8,
							layout:'form',
							border:false,
							items:[{
								name:'mrbsRepeat.endDate',
								xtype:'datefield',
								format: 'Y-m-d',
								anchor:"46%",
								disableDays:[0,6],
								value:new Date(),
								allowBlank:false
							}]
						}]
					}]
					
				},{
					id : 'allday',
					xtype:'container',
					layout:'form',
					border:false,
					anchor:"39%",
					items:[{
						layout:'column',
						border:false,
						items:[
						{
							columnWidth:.33,
							layout:'form',
							border:false,
							items:[{
								xtype:'label',
								border:false,
								text:'全天'
							}]
							
						},{
							columnWidth:.33,
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
								items:[{boxLabel:'否',name:'mrbsRepeat.allday',inputValue:'0'}]
							}]
						}]
					}]
				},{
					id : 'repeatWeekDay',
					xtype:'container',
					layout:'form',
					border:false,
					anchor:"77%",
					items:[{
						layout:'column',
						border:false,
						items:[{
							columnWidth:.1,
							layout:'form',
							border:false,
							items:[{
								xtype:'label',
								border:false,
								text:'星期几'
							}]
							
						},{
							columnWidth:.1,
							layout:'form',
							border:false,
							items:[{
								defaultType:'radio',
								border:false,
								items:[{boxLabel:'星期一',name:'mrbsRepeat.repeatWeekDay',inputValue:'1'}]
							}]
							
						},{
							columnWidth:.2,
							layout:'form',
							border:false,
							items:[{
								defaultType:'radio',
								border:false,
								items:[{boxLabel:'星期二',name:'mrbsRepeat.repeatWeekDay',inputValue:'2'}]
							}]
						},{
							columnWidth:.2,
							layout:'form',
							border:false,
							items:[{
								defaultType:'radio',
								border:false,
								items:[{boxLabel:'星期三',name:'mrbsRepeat.repeatWeekDay',inputValue:'3'}]
							}]
						},{
							columnWidth:.2,
							layout:'form',
							border:false,
							items:[{
								defaultType:'radio',
								border:false,
								items:[{boxLabel:'星期四',name:'mrbsRepeat.repeatWeekDay',inputValue:'4'}]
							}]
						},{
							columnWidth:.2,
							layout:'form',
							border:false,
							items:[{
								defaultType:'radio',
								border:false,
								items:[{boxLabel:'星期五',name:'mrbsRepeat.repeatWeekDay',inputValue:'5'}]
							}]
						}]
					}]
				},{
					id:'weekSpan',
					xtype:'container',
					layout:'form',
					items:[{
						layout:'column',
						border:false,
						items:[{
							columnWidth:.1,
							layout:'form',
							border:false,
							items:[{
								xtype:'label',
								text:'隔周'
							}]
						},{
							columnWidth:.9,
							layout:'form',
							border:false,
							items:[{
								xtype:'combo',
								name:'mrbsRepeat.weekSpan',
								allowBlank:false,
								store:new Ext.data.SimpleStore({
									fields:['value','text'],
									data:[['1','1'],['2','2'],['3','3'],['4','4'],['5','5']]
								}),
								displayField:'text',
								valueField:'value',
								mode:'local',
								emptyText:'请选择..'
							}]
						}]
						
					}]
					
					
					
				}
			]
		});
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
				text : "提交",
				handler : this.save.createCallback(this.formPanel, this)
			}, {
				text : "取消",
				handler : this.cancel.createCallback(this)
			}
		];
	},
	save : function(b, a) {
		if(b.getForm().isValid()) {
		   //validate
			b.getForm().submit({
				method : "post",
				waitMsg : "正在提交数据……",
				success : function() {
					Ext.ux.Toast.msg("提示信息","保存成功！");
					a.close();
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


