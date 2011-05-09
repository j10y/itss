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
        	header: header[0], 
	        width: 50, 
	        sortable: true,
	        dataIndex: 'applyId',
	        scope:this,
	        hidden:true
        },
        {	
        	header: header[1], 
	        width: 100, 
	        sortable: true,
	        dataIndex: 'applyNum',
	        scope:this	       
         },  
    	{	
        	header: header[2], 
	        width: 150, 
	        sortable: true,
	        dataIndex: 'applyName',
	        scope:this	       
         },   
          {	
        	header: header[3], 
	        width: 100, 
	        sortable: true,
	        hidden:true,
	        dataIndex: 'delegateApplyUser',
	        scope:this	       
         }, 
         {	
        	header: header[4], 
	        width: 100, 
	        sortable: true,
	        dataIndex: 'applyUser',
	        scope:this	       
         },  
          {	
        	header: header[5], 
	        width: 80, 
	        sortable: true,
	        dataIndex: 'applyDate',
	        scope:this	       
         },  
        {	
	        header: header[6], 
	        width: 100, 
	        sortable: true, 
	        dataIndex: 'applyTypeName'
        },
        {	header: header[7], 
	        width: 150, 
	        sortable: true,
	        dataIndex: 'taskName'
        }
    ];
    TasksGrid.superclass.constructor.call(this, {
    	id : 'porlet-requireTasks',  	
         store: new Ext.data.JsonStore({
			id:Ext.id(),
	       	url: webContext+'/requireWorkflow_tasks.action?actorId='+actorId,
	       	root:"data",
	       	totalProperty:"rowCount",
	  		remoteSort:false,
	  		sortInfo:{field:"applyName",direction:"DESC"},//add by musicbear for°´IDÄæÐò in 2009 11 16
	  		timeout:300000,
  			fields:['applyId','applyNum','applyName','delegateApplyUser','applyUser','applyDate','applyTypeName','taskName','startDate','taskId','defdesc','pageUrl','reqClass','isRefuseFlag'],
  			autoLoad:true,
  			listeners :{
  				load :function(){
  					    var porletContent = Ext.getCmp('porlet-requireTasks');
        				var pageData = porletContent.getStore().getRange();
        				for(var i = 0;i<pageData.length;i++){
        					if(pageData[i].get("isRefuseFlag")=="true"){
        						
        						var tempApplyNum = "<font color=#FF0000>"+ pageData[i].get("applyNum") +"</font>";
        						var tempApplyName =  "<font color=#FF0000>"+ pageData[i].get("applyName") +"</font>";
        						
	       						pageData[i].set("applyNum" ,tempApplyNum );
	       						pageData[i].set("applyName" ,tempApplyName );
	       						pageData[i].dirty=false;       						 
	       						pageData[i].commit(); 

        					}
        				}
  				}
  			}
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
        		//var applyId = value;//
	        	var applyType = record.get('applyType');//
	        	var tmpTaskName = record.get('taskName');//
	        	var taskName = record.get('taskName');//
	        	var taskId = record.get('taskId');//
	        	var reqClass = record.get('reqClass');      	
	        	var pageUrl='/servlet/getPageModel?taskId='+taskId;
 
	        	var param = ""+taskId+","+taskName+","+applyType+","+applyId+","+pageUrl+","+reqClass;
          		showAuditWindow(taskId,taskName,applyType,applyId,pageUrl,reqClass);
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
			//add by likang for  2009-8-29 begin
		portlet.on("resize",function(c,w,h){
			tasks.setSize(w - 4,180);
		});
		//add by likang for  2009-8-29 end
		return portlet;
	}
};
function reload() {
	var porletContent = Ext.getCmp('porlet-requireTasks');
	porletContent.getStore().reload();
}



//add by likang 2009-8-29 for  begin
function getToolBar(){

	var toolBar = new Ext.Toolbar(
		[
		
			'ÉêÇëÃû³Æ£º',
			new Ext.form.TextField({
				id :'reqName4AccoutTasks',
				width:100
			
			}),
			new Ext.Toolbar.Spacer(),new Ext.Toolbar.Spacer(),
			{
				text : "²éÑ¯",
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
	var grid = Ext.getCmp('porlet-requireTasks');
	var accountNameKey = Ext.getCmp('reqName4AccoutTasks').getValue().toUpperCase();
	grid.getStore().filterBy(function(record,id){		
		var accountName = record.get('applyName').toUpperCase();
		//alert(accountName);
		if(accountName.indexOf(accountNameKey)>=0){
			return true;
		}
		return false;
	});
}

//add by likang 2009-8-29 for å¢žåŠ æŸ¥è¯¢åŠŸèƒ½ end
