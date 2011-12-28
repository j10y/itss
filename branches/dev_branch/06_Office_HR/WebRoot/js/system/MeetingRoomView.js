var MeetingRoomView = Ext.extend(Ext.Panel,{
	constructor : function(a){
		if(a == null){
			a = {};
		}
		Ext.apply(this,a);
		this.initComponents(this);
		MeetingRoomView.superclass.constructor.call(this,{
			id:"MeetingRoomView",
			title:"预订办公室",
			region : "center",
			layout : "border",
			items:[
			 	this.areaTabPanel
			]
		});
	},
	areaTabPanel : null,
	initComponents : function(a){
		var tabbar = new Ext.Toolbar({
			items : [
				"->",
				{
					id : "selectDate",
					xtype : "datefield",
					format : "Y-m-d",
					value : new Date(),
					minValue : new Date().format("Y-m-d"),
					listeners : {
						select : function() {
							//alert(Ext.getCmp("selectDate").getValue());
							Ext.getCmp("areaTabPanel").removeAll();
							Ext.getCmp("MeetingRoomView").search(Ext.getCmp("MeetingRoomView"), Ext.getCmp("selectDate").getValue().format('Y-m-d'));
						}
					}
				}, {
					xtype : "label",
					html : "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"
				}, {
					xtype : "button",
					text : "查询",
					handler : function() {
						new MeetingRoomSearchForm().show();
					}
				}, {
					xtype : "button",
					text : "帮助"
				}, {
					xtype : "label",
					html : "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"
				}
			]
		});
		this.areaTabPanel = new Ext.TabPanel({
			id : "areaTabPanel",
			activeTab: 0,
			region : "center",
			tbar : tabbar,
			items:[]
		});
		this.search(a, new Date().format("Y-m-d"));
	},
	search_free : function(a,searchForm){
		Ext.Ajax.request({
					url : __ctxPath + "/system/listMrbsArea.do",
					params : {
					},
					method : "post",
					success : function(d) {
						var e = Ext.util.JSON.decode(d.responseText);
						//Ext.ux.Toast.msg("提示信息", d.responseText);
						for(var i = 0 ;i<e.result.length;i++){
							var panel1 = new Ext.Panel({
								id : 'area_'+i,
								title : e.result[i].areaName,
								autoDestroy: true,
								layout:"table",
								defaults:{
										bodyStyle : 'padding:20px;'
								},
								layoutConfig:{
										columns:3
									},
								items:[]
							});
							
							Ext.getCmp('areaTabPanel').add(panel1);
						    MeetingRoomView.searchItems("/system/listMrbsRoom.do?",a,i,e.result[i].id,searchForm);
						}
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
	
	},
	search : function(a, b){
		Ext.Ajax.request({
					url : __ctxPath + "/system/listMrbsArea.do",
					params : {
					},
					method : "post",
					success : function(d) {
						var e = Ext.util.JSON.decode(d.responseText);
						//Ext.ux.Toast.msg("提示信息", d.responseText);
						for(var i = 0 ;i<e.result.length;i++){
							var panel1 = new Ext.Panel({
								id : 'area_'+i,
								title : e.result[i].areaName,
								autoDestroy: true,
								layout:"table",
								defaults:{
										bodyStyle : 'padding:20px;'
								},
								layoutConfig:{
										columns:3
									},
								items:[]
							});
							
							Ext.getCmp('areaTabPanel').add(panel1);
						    MeetingRoomView.searchItems("/system/listMrbsRoom.do?searchDate=" + b,a,i,e.result[i].id);
						}
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

/**
 * 
 * @param {Object} u
 * @param {Object} t
 * @param {Object} index
 * @param {Object} id
 * @param {Object} searchForm
 * @return {TypeName} 
 * 
 * searchForm:当使用 查询空闲办公室时使用的参数，第一次加载 和 点击 日历的查询 不使用此参数
 */
MeetingRoomView.searchItems = function(u,t,index,id,searchForm){

		Ext.Ajax.request({
					url : __ctxPath + u,
					params : {
						areaId:id,
						attendNum:(searchForm)?searchForm.get('attendNum').getValue():"",
						meetingTime:(searchForm)?searchForm.get('meetingTime').getValue():"",
						startDate:(searchForm)?searchForm.get('startDate').getValue():"",
						endDate:(searchForm)?searchForm.get('endDate').getValue():"",
						referTime:(searchForm)?searchForm.get('referTime').getValue():"",
						meetingHour:(searchForm)?searchForm.get('meetingHour').getValue():""
					},
					method : "post",
					success : function(d) {
						var e = Ext.util.JSON.decode(d.responseText);
						//Ext.ux.Toast.msg("提示信息", d.responseText);
						var panel = t.areaTabPanel.getComponent('area_'+index);
						for(var j=0 ; j<e.result.length;j++){
							var topbar = new Array();
							var roomId = e.result[j].id;
							var roomName = e.result[j].roomName;
							var bibao = function(rid, rname) {
								return function() {
									new MeetingRoomForm({
										roomId : rid,
										roomName : rname,
										activeId : 0
									}).show();
								}
							}
							topbar.push(new Ext.Button({
								text : "查看",
								handler : bibao(roomId, roomName)
							}));
							var rPanel = new Ext.Panel({
								height:200,
	                    		width:380,
	                    		style:"margin:5px 5px",
								title:"<b>"+e.result[j].roomName+ "</b>&nbsp;&nbsp;&nbsp;"+e.result[j].room_admin_email,
								html:e.result[j].content,
								tbar : topbar
							});
							panel.add(rPanel);
						}
						panel.show();
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
		
	
	
};

var orderFun = function(rid,rname){

	new MeetingRoomForm({roomId : rid,
						roomName : rname,
						activeId:1
}).show();
}
