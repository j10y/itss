Ext.ns("MeetingRoomView");
MeetingRoomView = Ext.extend(Ext.Panel,{
	constructor : function(a){
		if(a == null){
			a = {};
		}
		Ext.apply(this,a);
		this.initComponents();
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
	initComponents : function(){
		this.areaTabPanel = new Ext.TabPanel({
			id : "areaTabPanel",
			region : "center",
			items:[],
			listeners : {
				afterrender:function(){
					Ext.Ajax.request({
					url : __ctxPath + "/system/listMrbsArea.do",
					params : {
						name : 'kanglei'
					},
					method : "post",
					success : function(d) {
						//var e = Ext.util.JSON.decode(d.responseText);
						Ext.ux.Toast.msg("提示信息", "成功删除所选记录！");
						var panel1 = new Ext.Panel({
							title : "来逛", 
							width : 100,
							height: 100
						});
						
						Ext.getCmp("MeetingRoomView").areaTabPanel.items.push(panel1);
						
						alert(Ext.getCmp("MeetingRoomView").areaTabPanel.items.length);
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
			}
		});
	}

	
});