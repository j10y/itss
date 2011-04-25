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
        title:'�ȴ��Ҵ�����¼�',
        store: store,
        trackMouseOver:false,
         tools:[{id:'refresh',qtip:'ˢ��',handler:function(e,tooEl,panel){panel.getStore().reload()}}],
        disableSelection:true,
        loadMask: true,
        sm : new Ext.grid.RowSelectionModel({singleSelect:true}),
        columns:[new Ext.grid.RowNumberer(),
	    	{	id:'eventCisn',
		        header: '�¼����', 
		        width: 100, 
		        sortable: true, 
		        dataIndex: 'eventCisn'
	        },
	    	 {	
	    	    id:'eventName',
	        	header: '�¼�����', 
		        width: 160,
		        sortable: true,
		        dataIndex: 'eventName',
		        scope:this	       
	         },
	         {	id:'eventSubmitUser',
		        header: '�¼��ύ��', 
		        width: 120, 
		        sortable: true, 
		        dataIndex: 'eventSubmitUser'
	        },
	        {	
	    	    id:'currEngineer',
	        	header: '��ǰ������ʦ', 
		        width: 120,  
		        sortable: true,
		        dataIndex: 'currEngineer',
		        scope:this
	        },
	        {	id:'eventSubmitDate',
		        header: '�ύʱ��', 
		        width: 110, 
		        sortable: true, 
		        dataIndex: 'eventSubmitDate'
	        },
	        {	id:'ponderance',
		        header: '������', 
		        width: 120, 
		        sortable: true, 
		        dataIndex: 'ponderance'
	        },{	id : 'taskName',
	        	header: '��ǰ��������', 
		        width: 120, 
		        sortable: true,
		        dataIndex: 'taskName'
	        },
	    	 {	
	    	    id:'applyId',
	        	header: '��ID', 
		        width: 120,  
		        sortable: true,
		        dataIndex: 'applyId',
		        scope:this,
		        hidden:true
	        },
	        {	
	        	id : 'taskId',
	        	header: '����ID', 
		        width: 120, 
		        sortable: true,
		        hidden:true,
		        dataIndex: 'taskId'
	        },
	         {	
	         	id : 'defdesc',
	         	header: '������', 
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