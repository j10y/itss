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
    	    id:'applyNum',
        	header: header[1], 
	        width: 100, 
	        sortable: true,
	        dataIndex: 'applyNum',
	        scope:this	       
         },
         {	
    	    id:'reqName',
        	header: header[2], 
	        width: 150, 
	        sortable: true,
	        dataIndex: 'reqName',
	        scope:this	       
         },
        {	id:'applyTypeName',
	        header: header[3], 
	        width: 150, 
	        sortable: true, 
	        dataIndex: 'applyTypeName'
        },
        {	header: header[4], 
	        width: 150, 
	        sortable: true,
	        dataIndex: 'taskName'
        },
        {	header: header[5], 
	        width: 100, 
	        sortable: true,
	        dataIndex: 'taskId',
	        hidden:true
        },
         {	header: header[6], 
	        width: 100, 
	        sortable: true,
	        dataIndex: 'defdesc',
	        hidden:true
        },
        {	header: header[7], 
	        width: 100, 
	        sortable: true,
	        hidden:true,
	        dataIndex: 'pageUrl'
        },
        {	id:'approveTime',
	        header: header[8], 
	        width: 80, 
	        sortable: true, 
	        dataIndex: 'approveTime',
	        hidden:false
        }
    ];
    TasksGrid.superclass.constructor.call(this, {
    	id : 'porlet-businessAccountTasks',  	
         store: new Ext.data.JsonStore({
			id:Ext.id(),
	       	url: webContext+'/businessAccountWorkflow_tasks.action?actorId='+actorId,
	       	//显示不出来是因为数据有问题，参考下面url
	       	root:"data",
	       	totalProperty:"rowCount",
	  		remoteSort:false,  
	  		timeout:300000,
  			fields:['applyId','applyNum','reqName','applyTypeName','taskName','taskId','defdesc','pageUrl','approveTime'],
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
		   		//var applyType = record.get("applyType");
        		//var applyId = value;//申请编号，获得业务数据
	        	var applyType = record.get('applyType');//申请类别
	        	var tmpTaskName = record.get('taskName');//怪怪的问题，要加一行多余的
	        	var taskName = record.get('taskName');//任务名称，作为审核标题	
	        	var taskId = record.get('taskId');//任务编号    	
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
			//add by likang for  只用一个滚动条  2009-8-29 begin
		portlet.on("resize",function(c,w,h){
			tasks.setSize(w - 4,180);
		});
		//add by likang for 只用一个滚动条 2009-8-29 end
		return portlet;
	}
};
function reload() {
	var porletContent = Ext.getCmp('porlet-businessAccountTasks');
	porletContent.getStore().reload();
}


//add by likang 2009-8-29 for 增加查询功能 begin
function getToolBar(){

	var toolBar = new Ext.Toolbar(
		[
		
			' 申请编号：',
			new Ext.form.TextField({
				id :'applyNo4AccoutTasks',
				width:100
			
			}),
			new Ext.Toolbar.Spacer(),new Ext.Toolbar.Spacer(),
			{
				text : "查 询",
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

//add by likang 2009-8-29 for 增加查询功能 end
