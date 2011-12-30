MeetingRoomSearchForm = Ext.extend(Ext.Window, {
	formPanel : null,
	constructor : function(a) {
		if(a == null) {
			a = {};
		}
		Ext.apply(this, a);
		this.initComponents();
		MeetingRoomSearchForm.superclass.constructor.call(this, {
			id : "MeetingRoomSearchFormWin",
			layout : "fit",
			width : 450,
			height : 280,
			modal : true,
			title : "搜索可用会议室",
			items : [
				this.formPanel
			],
			buttonAlign : "center",
			buttons : this.buttons,
			listeners : {
				afterrender : function() {
					Ext.getCmp("mhmContainer").hide();
				}
			}
		});
	},
	initComponents : function() {
		this.formPanel = new Ext.FormPanel({
			layout : "form",
			bodyStyle : "padding:10px 10px 10px 10px",
			border : false,
			url : __ctxPath + "/kpi/searchMeetingRoom.do",
			id : "MeetingRoomSearchForm",
			defaultType : "textfield",
			items : [
				{
					fieldLabel : "与会人数",
					style : "margin-top:10px;",
					xtype : "numberfield",
					labelStyle : "width:150px;text-align:right;margin-top:10px;",
					name : "attendNum",
					id : "attendNum",
					value : "2",
					allowBlank : false
				}, {
					fieldLabel : "会议用时（单位：小时）",
					labelStyle : "width:150px;text-align:right;",
					name : "meetingTime",
					id : "meetingTime",
					width : 130,
					xtype : "combo",
					value : "1",
					editable : false,
					triggerAction : "all",
					allowBlank : false,
					store : [
						["0.5","0.5"], ["1","1"], ["1.5","1.5"], ["2","2"], ["2.5","2.5"], ["3","3"], ["3.5","3.5"], ["4","4"], 
						["4.5","4.5"], ["5","5"], ["5.5","5.5"], ["6","6"], ["6.5","6.5"], ["7","7"], ["7.5","7.5"], ["8","8"] 
					]
				}, {
					fieldLabel : "开始日期",
					labelStyle : "width:150px;text-align:right;",
					width : 130,
					xtype : "datefield",
					name : "startDate",
					id : "startDate",
					format : "Y-m-d",
					value : new Date(),
					minValue : new Date().format("Y-m-d"),
					id : "startDate",
					allowBlank : false,
					listeners : {
						select : function() {
							Ext.getCmp("endDate").setValue(Ext.getCmp("startDate").getValue());
							Ext.getCmp("endDate").minValue = Ext.getCmp("startDate").getValue();
						}
					}
				}, {
					fieldLabel : "结束日期",
					labelStyle : "width:150px;text-align:right;",
					width : 130,
					xtype : "datefield",
					name : "endDate",
					id : "endDate",
					format : "Y-m-d",
					value : new Date(),
					minValue : new Date().format("Y-m-d"),
					id : "endDate",
					allowBlank : false
				}, {
					fieldLabel : "预定区域",
					labelStyle : "width:150px;text-align:right;",
					xtype: 'checkboxgroup',
					width : 130,
					id : "area",
					items : [
						{
							boxLabel : "来广营办公区",
							inputValue : "2",
							name : "area",
							checked : "true"
						}
					]
				}, {
					fieldLabel : "需要会议参考时间",
					labelStyle : "width:150px;text-align:right;",
					xtype : "radiogroup",
					id : "referTime",
					width : 130,
					items : [
						{
							boxLabel : "否",
							name : "referTime",
							inputValue : "0",
							checked : true
						}, {
							boxLabel : "是",
							name : "referTime",
							inputValue : "1",
							listeners : {
								check : function() {
									if(this.checked) {
										Ext.getCmp("mhmContainer").show();
									} else {
										Ext.getCmp("mhmContainer").hide();
									}
								}
							}
						}
					]
				}, {
					xtype : "container",
					id : "mhmContainer",
					layout : "form",
					border : false,
					items : [
						{
							layout : "column",
							border : false,
							anchor : "100%",
							items : [
								{
									layout : "form",
									columnWidth : 0.37,
									border : false,
									style : "text-align:right;",
									items : [
										{
											xtype : "label",
											text : "会议时间："
										}
									]
								}, {
									layout : "form",
									columnWidth : 0.19,
									border : false,
									items : [
										{
											name : "meetingHour",
											id : "meetingHour",
											xtype : "combo",
											hideLabel : true,
											editable : false,
											width : 70,
											triggerAction : "all",
											store : [
												 ["8","8"], ["9","9"], ["10","10"], ["11","11"], ["12","12"], ["13","13"], ["14","14"], 
												 ["15","15"], ["16","16"], ["17","17"], ["18","18"], ["19","19"], ["20","20"]
											]
										}
									]
								}, {
									layout : "form",
									columnWidth : 0.1,
									border : false,
									items : [
										{
											xtype : "label",
											text : "时"
										}
									]
								}, {
									layout : "form",
									columnWidth : 0.19,
									border : false,
									items : [
										{
											name : "meetingMin",
											id : "meetingMin",
											xtype : "combo",
											hideLabel : true,
											editable : false,
											width : 70,
											triggerAction : "all",
											store : [
												 ["00","00"], ["30","30"]
											]
										}
									]
								}, {
									layout : "form",
									columnWidth : 0.15,
									border : false,
									items : [
										{
											xtype : "label",
											text : "分"
										}
									]
								}
							]
						}
					]
				}
			]
		});
		this.buttons = [
			{
				text : "查询",
				handler : this.search.createCallback(this.formPanel, this)
			}, {
				text : "关闭",
				handler : this.cancel.createCallback(this)
			}
		];
	},
	search : function(b, a) {
		if(b.getForm().isValid()) {
			var array = new Array();
			array.push(Ext.getCmp("attendNum").getValue());
			array.push(Ext.getCmp("meetingTime").getValue());
			array.push(Ext.getCmp("startDate").getValue());
			array.push(Ext.getCmp("endDate").getValue());
			array.push(Ext.getCmp("referTime").getValue());
			array.push(Ext.getCmp("meetingHour").getValue());
			array.push(Ext.getCmp("meetingMin").getValue());
			Ext.getCmp("areaTabPanel").removeAll();
			Ext.getCmp("MeetingRoomView").search_free(Ext.getCmp("MeetingRoomView"), array);
			a.close();
		}
	},
	cancel : function(a) {
		a.close();
	}
});