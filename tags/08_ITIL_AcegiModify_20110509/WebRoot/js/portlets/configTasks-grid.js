/*
 * Ext JS Library 2.0.1
 * Copyright(c) 2006-2008, Ext JS, LLC.
 * licensing@extjs.com
 * 
 * http://extjs.com/license
 */


TasksGrid = function(actorId, resource){
	var header = com.faceye.ui.util.ResourceUtil.readXmlHeader(resource,'header','title');
    var columns = [  
    	 {	
    	    id:'applyId',
        	header: header[0], 
	        width: 50, 
	        sortable: true,
	        dataIndex: 'applyId',
	        scope:this,
	        hidden:true
        },
        {	id:'applyTypeName',
	        header: header[1], 
	        width: 120, 
	        sortable: true, 
	        dataIndex: 'applyTypeName'
        },
        {	header: header[2], 
	        width: 120, 
	        sortable: true,
	        dataIndex: 'taskName'
        },
        {	header: header[3], 
	        width: 100, 
	        sortable: true,
	        dataIndex: 'taskId'
        },
         {	header: header[5], 
	        width: 100, 
	        sortable: true,
	        dataIndex: 'defdesc'
        },
        {	header: header[6], 
	        width: 100, 
	        sortable: true,
	        hidden:true,
	        dataIndex: 'pageUrl'
        }
    ];
    TasksGrid.superclass.constructor.call(this, {  	
         store: new Ext.data.JsonStore({
			id:Ext.id(),
	       	url: webContext+'/configWorkflow_tasks.action?actorId='+actorId,
	       	root:"data",
	       	totalProperty:"rowCount",
	  		remoteSort:false,  
	  		timeout:300000,
  			fields:['applyId','applyTypeName','customer','taskName','taskId','defdesc','pageUrl','modifyDataId','rootId','rootText'],
  			autoLoad:true
  		}),
  		id:'porlet-configTasks',
        columns: columns,
        autoScroll:true,
        height:170,
        width:'auto',
        listeners:{
        	rowdblclick:function(){
        		var record = this.getSelectionModel().getSelected();
		    	var applyId = record.get("applyId");	
	        	var applyType = record.get('applyType');
	        	var tmpTaskName = record.get('taskName');
	        	var taskName = record.get('taskName');
	        	var taskId = record.get('taskId');
	        	var modifyDataId = record.get("modifyDataId");	
	        	var rootId = record.get("rootId");	
	        	var rootText = record.get("rootText");	
	        	var pageUrl='/servlet/getPageModel?taskId='+taskId;
	        	var param = ""+taskId+","+taskName+","+applyType+","+applyId+","+pageUrl+","+modifyDataId+","+rootId+","+rootText;
          		showAuditWindow(taskId,taskName,applyType,applyId,pageUrl,modifyDataId,rootId,rootText);
        	}
        },
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
			//add by likang for  只有一个滚动条  2009-8-29 begin
		portlet.on("resize",function(c,w,h){
			tasks.setSize(w - 4,180);
		});
		//add by likang for 只有一个滚动条 2009-8-29 end
		return portlet;
	}
};
function reload() {
	var porletContent = Ext.getCmp('porlet-configTasks');
	porletContent.getStore().reload();
}



