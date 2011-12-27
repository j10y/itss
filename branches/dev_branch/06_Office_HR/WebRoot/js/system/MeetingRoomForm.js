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
			]
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
		this.formPanel = new Ext.Panel({
			title : "预订"
		});
		this.tabPanel = new Ext.TabPanel({
			id : "MeetingRoomTabPanel",
			region : "center",
			autoScroll:true,
			activeTab : 0,
			items : [
				this.gridPanel,
				this.formPanel
			]
		});
	}
});