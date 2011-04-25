Ext.onReady(function(){

	var store= new Ext.data.JsonStore({
		url: webContext+'/eventWorkflow_tasks.action?actorId='+this.userID,
       	root:"data",
       	totalProperty:"rowCount",
  		remoteSort:true,  
  		//timeout:300000,
		fields:['eventCisn','eventName','eventSubmitUser','eventSubmitDate','ponderance','currEngineer','applyId','applyType','taskName','taskId','defdesc'],
  		autoLoad:true
  		});
    var grid = new Ext.grid.GridPanel({
        width:'auto',
        layout:'fit',
        title:'等待我处理的事件',
        store: store,
        trackMouseOver:false,
         tools:[{id:'refresh',qtip:'刷新',handler:function(e,tooEl,panel){panel.getStore().reload()}}],
        disableSelection:true,
        loadMask: true,
        sm : new Ext.grid.RowSelectionModel({singleSelect:true}),
        columns:[new Ext.grid.RowNumberer(),
	    	{	id:'eventCisn',
		        header: '事件编号', 
		        width: 100, 
		        sortable: true, 
		        dataIndex: 'eventCisn'
	        },
	    	 {	
	    	    id:'eventName',
	        	header: '事件名称', 
		        width: 160,
		        sortable: true,
		        dataIndex: 'eventName',
		        scope:this	       
	         },
	         {	id:'eventSubmitUser',
		        header: '事件提交人', 
		        width: 120, 
		        sortable: true, 
		        dataIndex: 'eventSubmitUser'
	        },
	        {	
	    	    id:'currEngineer',
	        	header: '当前处理工程师', 
		        width: 120,  
		        sortable: true,
		        dataIndex: 'currEngineer',
		        scope:this
	        },
	        {	id:'eventSubmitDate',
		        header: '提交时间', 
		        width: 110, 
		        sortable: true, 
		        dataIndex: 'eventSubmitDate'
	        },
	        {	id:'ponderance',
		        header: '严重性', 
		        width: 120, 
		        sortable: true, 
		        dataIndex: 'ponderance'
	        },{	id : 'taskName',
	        	header: '当前审批环节', 
		        width: 120, 
		        sortable: true,
		        dataIndex: 'taskName'
	        },
	    	 {	
	    	    id:'applyId',
	        	header: '主ID', 
		        width: 120,  
		        sortable: true,
		        dataIndex: 'applyId',
		        scope:this,
		        hidden:true
	        },
	        {	
	        	id : 'taskId',
	        	header: '任务ID', 
		        width: 120, 
		        sortable: true,
		        hidden:true,
		        dataIndex: 'taskId'
	        },
	         {	
	         	id : 'defdesc',
	         	header: '流程名', 
		        width: 120, 
		        sortable: true,
		        hidden:true,
		        dataIndex: 'defdesc'
	        }],
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
                window.open(webContext+"/infoAdmin/workflow/configPage/auditFromMail.jsp?taskId="+taskId+"&dataId="+applyId+"&goStartState=null&taskName=&applyType="+applyType+"&browseFlag=");
          		//showAuditWindow(taskId,taskName,applyType,applyId,pageUrl);
        	}
        }
    });
    scope:this,
    grid.render('pageView');
    new Ext.Viewport({ 
		layout : 'fit', 
		items : [grid] 
	});
});