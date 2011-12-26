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
		this.areaTabPanel = new Ext.TabPanel({
			id : "areaTabPanel",
			activeTab: 0,
			region : "center",
			items:[]
		});
		this.search(a);
	},
	search2 : function(a){
		
	
	},
	search : function(a){
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
						    MeetingRoomView.searchItems("/system/listMrbsRoom.do",a,i,e.result[i].id);
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


MeetingRoomView.searchItems = function(u,t,index,id){

		Ext.Ajax.request({
					url : __ctxPath + u,
					params : {
						areaId:id
					},
					method : "post",
					success : function(d) {
						var e = Ext.util.JSON.decode(d.responseText);
						Ext.ux.Toast.msg("提示信息", d.responseText);
						var panel = t.areaTabPanel.getComponent('area_'+index);
						for(var j=0 ; j<e.result.length;j++){
							var topbar = new Array();
							var roomId = e.result[j].id;
							var roomName = e.result[j].roomName;
							var bibao = function(rid, rname) {
								return function() {
									new MeetingRoomForm({
										roomId : rid,
										roomName : rname
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
