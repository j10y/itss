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
    	{	
    	    id:'accountName',
        	header: header[1], 
	        width: 100, 
	        sortable: true,
	        dataIndex: 'accountName',
	        scope:this	       
         },  
        {	id:'applyTypeName',
	        header: header[2], 
	        width: 150, 
	        sortable: true, 
	        dataIndex: 'applyTypeName'
        },
        {	header: header[3], 
	        width: 150, 
	        sortable: true,
	        dataIndex: 'taskName'
        },
         {	id:'applyTime',
	        header: header[4], 
	        width: 80, 
	        sortable: true, 
	        dataIndex: 'applyTime'
	     },
         {	id:'applyUser',
	        header: header[5], 
	        width: 150, 
	        sortable: true, 
	        dataIndex: 'applyUser'
	     },
         {	header: header[6], 
	        width: 100, 
	        sortable: true,
	        dataIndex: 'taskId',
	        hidden:true
        },
         {	header: header[7], 
	        width: 100, 
	        sortable: true,
	        dataIndex: 'defdesc',
	        hidden:true
        },
        {	header: header[8], 
	        width: 100, 
	        sortable: true,
	        hidden:true,
	        dataIndex: 'pageUrl'
        }
       
    ];
    TasksGrid.superclass.constructor.call(this, {
    	id : 'porlet-accountTasks',  	
         store: new Ext.data.JsonStore({
			id:Ext.id(),
	       	url: webContext+'/accountWorkflow_tasks.action?actorId='+actorId,
	       	root:"data",
	       	totalProperty:"rowCount",
	  		remoteSort:false,  
	  		timeout:300000,
  			fields:['applyId','accountName','applyTypeName','taskName','applyTime','applyUser','taskId','defdesc','pageUrl'],
  			autoLoad:true
  		}),
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
	        	var pageUrl='/servlet/getPageModel?taskId='+taskId;
                var param = ""+taskId+","+taskName+","+applyType+","+applyId+","+pageUrl;
          		showAuditWindow(taskId,taskName,applyType,applyId,pageUrl);
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
		
		//add by likang for 只有一个滚动条 2009-8-29 begin
		portlet.on("resize",function(c,w,h){
			tasks.setSize(w - 4,180);
		});
		//add by likang for 只有一个滚动条 2009-8-29 end
		
		return portlet;
	}
};
function reload() {
	var porletContent = Ext.getCmp('porlet-accountTasks');
	porletContent.getStore().reload();
}



//add by likang 2009-8-29 for查询 begin
function getToolBar(){

	var toolBar = new Ext.Toolbar(
		[
		
			'申请编号',
			new Ext.form.TextField({
				id :'applyNo4AccoutTasks',
				width:100
			
			}),
			new Ext.Toolbar.Spacer(),new Ext.Toolbar.Spacer(),
			{
				text : "查询",
				pressed : true,
				handler : this.filterGrid,
				scope : this
			  //  iconCls:'search'
			}
		
						
		]
	);

	return toolBar;

}

function filterGrid(){
	var grid = Ext.getCmp('porlet-accountTasks');
	var accountNameKey = Ext.getCmp('applyNo4AccoutTasks').getValue().toUpperCase();
	grid.getStore().filterBy(function(record,id){		
		var accountName = record.get('accountName').toUpperCase();
		//alert(accountName);
		if(accountName.indexOf(accountNameKey)>=0){
			return true;
		}
		return false;
	});
}

//add by likang 2009-8-29 for 澧炲姞鏌ヨ鍔熻兘 end
