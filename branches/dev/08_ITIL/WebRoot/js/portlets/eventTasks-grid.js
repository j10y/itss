/*
 * Ext JS Library 2.0.1
 * Copyright(c) 2006-2008, Ext JS, LLC.
 * licensing@extjs.com
 * 
 * http://extjs.com/license
 */


TasksGrid = function(actorId, resource){
	var header = com.faceye.ui.util.ResourceUtil.readXmlHeader(resource,'header','title');
    var columns = [{	
            id:'applyTypeName',
	        header: header[0], 
	        width: 120, 
	        sortable: true, 
	        dataIndex: 'applyTypeName'
        },
        {	id : 'taskName',
        	header: header[1], 
	        width: 120, 
	        sortable: true,
	        dataIndex: 'taskName'
        },  
    	{	id:'eventCisn',
	        header: header[2], 
	        width: 100, 
	        sortable: true, 
	        dataIndex: 'eventCisn'
        },
    	 {	
    	    id:'eventName',
        	header: header[3], 
	        width: 160,//2010-05-04 modified by huzh for 页面优化 
	        sortable: true,
	        dataIndex: 'eventName',
	        scope:this	       
         },
         {	id:'eventSubmitUser',
	        header: header[4], 
	        width: 120, //2010-05-04 modified by huzh for 页面优化 
	        sortable: true, 
	        dataIndex: 'eventSubmitUser'
        },
        {	
    	    id:'currEngineer',
        	header: header[5], 
	        width: 120,  
	        sortable: true,
	        dataIndex: 'currEngineer',
	        scope:this
        },
        {	id:'eventSubmitDate',
	        header: header[6], 
	        width: 110, //2010-05-17 modified by huzh for 页面优化 
	        sortable: true, 
	        dataIndex: 'eventSubmitDate'
        },
        {	id:'ponderance',
	        header: header[7], 
	        width: 120, 
	        sortable: true, 
	        dataIndex: 'ponderance'
        },
    	 {	
    	    id:'applyId',
        	header: header[8], 
	        width: 120,  
	        sortable: true,
	        dataIndex: 'applyId',
	        scope:this,
	        hidden:true
        },
        {	
        	id : 'taskId',
        	header: header[9], 
	        width: 120, 
	        sortable: true,
	        hidden:true,
	        dataIndex: 'taskId'
        },
         {	
         	id : 'defdesc',
         	header: header[10], 
	        width: 120, 
	        sortable: true,
	        hidden:true,
	        dataIndex: 'defdesc'
        },
        {	
        	id : 'pageUrl',
        	header: header[11], 
	        width: 120, 
	        sortable: true,
	        hidden:true,
	        dataIndex: 'pageUrl'
        }
    ];
    TasksGrid.superclass.constructor.call(this, {  	
         store: new Ext.data.JsonStore({
			id:Ext.id(),
	       	url: webContext+'/eventWorkflow_tasks.action?actorId='+actorId,
	       	root:"data",
	       	totalProperty:"rowCount",
	  		remoteSort:false,  
	  		timeout:300000,
	  		// 2010-04-26 modified by huzh for geting dealuser's itcode begin
  			fields:['eventCisn','eventName','eventSubmitUser','eventSubmitDate','ponderance','currEngineer','applyId','applyTypeName','customer','taskName','taskId','defdesc','pageUrl','modifyDataId','rootId','rootText','currEngineerItcode'],
  			//2010-04-26 modified by huzh for geting dealuser's itcode end
  			autoLoad:true
  		}),
  		id:'porlet-eventTasks',
        columns: columns,
        autoScroll:true,
        height:180,
        monitorSindowResize:true,
        width:'auto',
        listeners:{
        	rowdblclick:function(){
        		var record = this.getSelectionModel().getSelected();
        	    // 2010-04-26 modified by huzh for 已有处理人，则弹出提示信息 begin 
        		var curUser=actorId;
        		var currEngineerItcode = record.get("currEngineerItcode");
	        	var taskName = record.get('taskName');
	        	//2010-06-02 modified by huzh for 服务组长处理和用户确认除外 begin
        		if(taskName!="服务组长处理"&&taskName!="用户确认"&&curUser!=currEngineerItcode&&currEngineerItcode!=''&&currEngineerItcode!=null){
        			Ext.MessageBox.alert("提示", "此事件已有处理人，处理人为："+record.get("currEngineer")+"！");
						return ;
					};
				//2010-06-02 modified by huzh for 服务组长处理和用户确认除外 end
        	// 2010-04-26 modified by huzh for 已有处理人，则弹出提示信息 end
		    	var applyId = record.get("applyId");
		    	var eventName = record.get("eventName");		
	        	var applyType = record.get('applyType');
	        	var tmpTaskName = record.get('taskName');
	        	var taskId = record.get('taskId');
	        	var modifyDataId = record.get("modifyDataId");	
	        	var rootId = record.get("rootId");	
	        	var rootText = record.get("rootText");	
	        	var pageUrl='/servlet/getPageModel?taskId='+taskId;
	        	var param = ""+taskId+","+taskName+","+applyType+","+applyId+","+pageUrl+","+modifyDataId+","+rootId+","+rootText+","+eventName;
          		showEventAuditWindow(taskId,eventName,taskName,applyType,applyId,pageUrl,modifyDataId,rootId,rootText);
        	}
        },
        
        //add by likang
        		//tbar:getToolBar(),
  		 //add by likang
        		
        scope:this
    });
}
Ext.extend(TasksGrid, Ext.grid.GridPanel);

com.faceye.portal.portlet.SinglePortlet = {
	init : function(id, name, resource, portalColum) {
		var porletId =  id + '_' + portalColum;
		var tasks = new TasksGrid(userID, resource);
		var portlet = new Ext.ux.Portlet({
			id : porletId + '_' + Ext.id(),
			title : name,
			autoScroll: true,
			height: 210,
			tools : com.faceye.portal.PortletTools.getDefaultPorletTools(id,portalColum,reload)
		});
		portlet.add(tasks);
		
			//add by likang for   只有一个滚动条 2009-8-29 begin
		portlet.on("resize",function(c,w,h){
			tasks.setSize(w - 4,180);
		});
		//add by likang for  只有一个滚动条 2009-8-29 end
		
		return portlet;
	}
};
function reload() {
	var porletContent = Ext.getCmp('porlet-eventTasks');
	porletContent.getStore().reload();
}


//add by likang 2009-8-29 for  begin
//function getToolBar(){
//
//	var toolBar = new Ext.Toolbar(
//		[
//		
//			' ',
//			new Ext.form.TextField({
//				id :'eventNo4AccoutTasks',
//				width:100
//			
//			}),
//			new Ext.Toolbar.Spacer(),new Ext.Toolbar.Spacer(),
//				' ',
//			new Ext.form.TextField({
//				id :'eventName4AccoutTasks',
//				width:100
//			
//			}),
//			new Ext.Toolbar.Spacer(),new Ext.Toolbar.Spacer(),
//			{
//				text : "",
//				pressed : true,
//				handler : this.filterGrid,
//				scope : this
//			  //  iconCls:'search'
//			}
//		
//						
//		]
//	);
//
//	return toolBar;
//
//}

function filterGrid(){
	var grid = Ext.getCmp('porlet-eventTasks');
	var eventNoKey = Ext.getCmp('eventNo4AccoutTasks').getValue().toUpperCase();
		var eventNnameKey = Ext.getCmp('eventName4AccoutTasks').getValue().toUpperCase();

	grid.getStore().filterBy(function(record,id){		
		var applyId = record.get('eventCisn').toUpperCase();
		//alert(applyId);
		var eventName = record.get('eventName').toUpperCase();
		//alert(eventName);
		if(applyId.indexOf(eventNoKey)>=0 & eventName.indexOf(eventNnameKey)>=0){
			return true;
		}
		return false;
	});
}

//add by likang 2009-8-29 for 增加查询功能 end

