Ext.onReady(function(){

	var store= new Ext.data.JsonStore({
	       	url: webContext+'/requireWorkflow_tasks.action?actorId='+this.userID,
	       	root:"data",
	       	totalProperty:"rowCount",
	  		remoteSort:true,  
	  		//timeout:300000,
  			fields:['taskId','applyId','applyNum','applyName','delegateApplyUser','applyUser','applyDate','applyType','applyTypeName','taskName']
  		});
    var grid = new Ext.grid.GridPanel({
        width:'auto',
        layout:'fit',
        title:'�ȴ��Ҵ�����ʺź���������',
        store: store,
        trackMouseOver:false,
        tools:[{id:'refresh',qtip:'ˢ��',handler:function(e,tooEl,panel){panel.getStore().reload()}}],
        disableSelection:true,
        loadMask: true,
        sm : new Ext.grid.RowSelectionModel({singleSelect:true}),
        columns:[
		    
         {	header: '������', 
	        width: 100, 
	        sortable: true,
	        dataIndex: 'taskId',
	        hidden:true
        },
    	{	
        	header: '������ID', 
	        width: 50, 
	        sortable: true,
	        dataIndex: 'applyId',
	        scope:this,
	        hidden:true
        },
        {	
        	header: '������', 
	        width: 100, 
	        sortable: true,
	        dataIndex: 'applyNum',
	        scope:this	       
         },  
    	{	
        	header: '��������', 
	        width: 150, 
	        sortable: true,
	        dataIndex: 'applyName',
	        scope:this	       
         },   
          {	
        	header:'��������', 
	        width: 100, 
	        sortable: true,
	        hidden:false,
	        dataIndex: 'delegateApplyUser',
	        scope:this	       
         }, 
         {	
        	header: '������', 
	        width: 100, 
	        sortable: true,
	        dataIndex: 'applyUser',
	        scope:this	       
         },  
          {	
        	header:'����ʱ��', 
	        width: 80, 
	        sortable: true,
	        dataIndex: 'applyDate',
	        scope:this	       
         },  
        {	
	        header: '��������', 
	        width: 150, 
	        sortable: true, 
	        dataIndex: 'applyTypeName'
        },
        {	header: '��ǰ��������', 
	        width: 150, 
	        sortable: true,
	        dataIndex: 'taskName'
        },
        {	header: 'applyType', 
	        width: 50, 
	        sortable: true,
	        hidden:true,
	        dataIndex: 'applyType'
        }

    ],
		listeners:{
        	rowdblclick:function(){
        		var record = this.getSelectionModel().getSelected();
		    	var applyId = record.get("applyId");		    	
	        	var applyType = record.get('applyType');
	        	//var tmpTaskName = record.get('taskName');
	        	//var taskName = record.get('taskName');
	        	var taskId = record.get('taskId');
	        	//var pageUrl='/servlet/getPageModel?taskId='+taskId;
                //var param = ""+taskId+","+taskName+","+applyType+","+applyId+","+pageUrl;
          		 window.open(webContext+"/infoAdmin/workflow/configPage/auditFromMail.jsp?taskId="+taskId+"&dataId="+applyId+"&goStartState=null&taskName=&applyType="+applyType+"&browseFlag=");
          		//showAuditWindow(taskId,taskName,applyType,applyId,pageUrl);
        	}
        }
    });
    scope:this,
    grid.render('pageView');
    store.load();
    new Ext.Viewport({ 
		layout : 'fit', 
		items : [grid] 
	}); 

});