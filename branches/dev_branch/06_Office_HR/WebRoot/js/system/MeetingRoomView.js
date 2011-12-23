Ext.ns("MeetingRoomView");
var MeetingRoomView = function() {
	return new Ext.Panel(
			{
				id : "MeetingRoomView",
				title : "会议室预订",
				iconCls : "menu-appointment",
				autoScroll : true,
				items : [
						new Ext.TabPanel({
							activeTab: 0,
							defaults:{autoHeight: true},
							items:[
								new Ext.Panel({
									title:"来广营办公区",
									layout:"table",
									autoLoad: __ctxPath+"/task/listAppointment.do",
									defaults:{
										bodyStyle : 'padding:20px;'
									},
									layoutConfig:{
										columns:3
									},
									items:[
											
				{  
                    height:200,
                    width:380,
					style:"margin:5px 5px",
                    title:'子面板一',  
                },  
                {  
                	 height:200,
                    width:380,
                	style:"margin:5px 5px",
                    title:'子面板二',  
                },  
                {  
                	 height:200,
                    width:380,
                	style:"margin:5px 5px",
                    title:'子面板二',  
                },  
                {  
                	 height:200,
                    width:380,
                	style:"margin:5px 5px",
                    title:'子面板二',  
                },  
                {  
                	 height:200,
                    width:380,
                	style:"margin:5px 5px",
                    title:'子面板二',  
                },  
                {  
                	 height:200,
                    width:380,
                	style:"margin:5px 5px",
                    title:'子面板二',  
                }
										
									]
								})
								]
						})]
			});
};
