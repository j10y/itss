Ext.ns("MeetingRoomView");
var ssssss = new Array();
MeetingRoomView = Ext.extend(Ext.Panel,{
	constructor : function(a){
		if(a == null){
			a = {};
		}
		//ssssss = this.search(this);
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
	areaTabPanel : new Ext.TabPanel({
			id : "areaTabPanel",
			region : "center",
			items:[]
		}),
	initComponents : function(a){
		this.search(a);
	},
	search : function(a){
		//alert(a);
		Ext.Ajax.request({
					url : __ctxPath + "/system/listMrbsArea.do",
					params : {
						name : 'kanglei'
					},
					method : "post",
					success : function(d) {
						var e = Ext.util.JSON.decode(d.responseText);
						//Ext.ux.Toast.msg("提示信息", "成功删除所选记录！");
						for(var i = 0 ;i<e.result.length;i++){
							var panel1 = new Ext.Panel({
								title : e.result[i].areaName,
								layout:"table",
								defaults:{
										bodyStyle : 'padding:20px;'
								},
								layoutConfig:{
										columns:3
									},
								items:[]
							});
							a.areaTabPanel.add(panel1)
							
							panel1.add({  
			                    height:200,
			                    width:380,
								style:"margin:5px 5px",
			                    title:'子面板一'
			                });
							panel1.add({  
			                    height:200,
			                    width:380,
								style:"margin:5px 5px",
			                    title:'子面板X'
			                });
							panel1.show();
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


MeetingRoomView.getAreaitems = function(){
	    var array = new Array();
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
						array.push(panel1);
						//alert(a.length);
						return array;
						
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
