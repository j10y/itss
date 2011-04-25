PagePanel = Ext.extend(Ext.Panel, {
    id : "problemPanel",
    closable : true,
    autoScroll:true,
    height : 492,//Ҫ��portal�еĸ߶�һ��
    farme:true,
    layoutConfig : {
            columns : 1
        },
    border:true,
    width:785,
    layout : 'table',
    
    modifyEventServiceTypeAndItem:function(){
        var serviceItemId = Ext.getCmp("childsort").getValue();
        if(Ext.getCmp("childsort").getRawValue()==""){
        	Ext.getCmp("childsort").clearValue();
        }
        
        if(serviceItemId==''){
            Ext.Msg.alert("��ʾ","������������ѡ������");
            return;
        }
        if(Ext.getCmp("Event$description8").getValue().trim()==''){
            Ext.Msg.alert("��ʾ","�¼���������Ϊ�գ�");
            return;
        }
         Ext.getCmp("otherGroup").disable();    
	      Ext.getCmp("saveServiceType").disable();
	      Ext.getCmp("backHead").disable();
	      Ext.getCmp("operateSelf").disable();
        var eventFrequency = frequency;
        var eventPonderance = ponderance;
        if(eventFrequency == '') {
            eventFrequency = frequencyId;
        }
        if(eventPonderance == '') {
            eventPonderance = ponderanceId;
        }
        var pSort=Ext.getCmp("Event$problemSortCombo").getValue();
        if(Ext.getCmp("Event$problemSortCombo").getRawValue()==""){
        	Ext.getCmp("Event$problemSortCombo").clearValue();
        }
        var desc=Ext.encode(Ext.getCmp("Event$description8").getValue());//�¼�����
         Ext.Ajax.request({
                  url :webContext + '/eventAction_modifyEvent.action',
                  params: { 
                             eventId : this.dataId,
                             serviceItemId : serviceItemId,
                             eventFrequency : eventFrequency,
                             eventPonderance : eventPonderance,
                             desc : desc.substring(1,desc.length-1),//�����¼�����
                             problemSortId : pSort
                           },
                  method : 'post', 
                  success : function(){
                      Ext.MessageBox.alert("��ʾ","�����¼��ɹ���",function(){
                        Ext.getCmp("serviceItemBySu").setValue(serviceItemId);
                        Ext.getCmp("serviceItemBySu").initComponent();
                            var param = {
                                'mehtodCall' : 'query',
                                 'start' : 0,
                                 status : 1,
                                 serviceItem : serviceItemId
                            };
                            Ext.getCmp("pageBar").formValue = param;
                            Ext.getCmp("KnowLedgeListGrid").getStore().reload({
                                params : param
                            }); 
                      });
                       Ext.getCmp("otherGroup").enable();    
				      Ext.getCmp("saveServiceType").enable();
				      Ext.getCmp("backHead").enable();
				      Ext.getCmp("operateSelf").enable();
                  },
                  failure : function(response,options){
                    Ext.MessageBox.alert("��ʾ","����ʧ�ܣ�");
                    Ext.getCmp("otherGroup").enable();    
				      Ext.getCmp("saveServiceType").enable();
				      Ext.getCmp("backHead").enable();
				      Ext.getCmp("operateSelf").enable();
                  }
              });
    },
    
    searchgride :function (){
        if(Ext.getCmp('serviceItemBySu').getRawValue()==''){
          Ext.getCmp('serviceItemBySu').clearValue();
        }
            var param = Ext.getCmp('queryKnowLedgeType').getForm().getValues(false);
            param.methodCall = 'query';
            param.start = 0;
            param.status =1;
            this.formValue = param;
            this.pageBar.formValue = this.formValue;
            this.store.removeAll();
            this.store.load({
                params : param
            });
    },
    dealKnow:function(){  
            var record =  Ext.getCmp('KnowLedgeListGrid').getSelectionModel().getSelected();
            var knowledgeId=record.get('Knowledge$id'); 
            var da = new DataAction();
            var data=da.getSingleFormPanelElementsForEdit("KnowLedgeSolution_pagepanel",knowledgeId);
            var konwledgecontext = "";
	        for(i = 0; i < data.length; i++){
	        	data[i].id=data[i].id+6;
	        	if(data[i].id=="Knowledge$resolvent6"){
	        		konwledgecontext = data[i].value;
	        		break;
	        	}
	        }
			var windowSkip = new Ext.Window({
				title : '�鿴��ϸ�������',
				maximizable : true,
				autoScroll : true,
				width : 600,
				height :400,
				modal : true,
				items : [{html : konwledgecontext}],
                buttons : [{
                text : 'ʹ��',
                id : 'useKnowButton',
                	handler : function(){
				   	if(Ext.getCmp("childsort").getValue()==""){
				   		Ext.MessageBox.alert("��ʾ","��������б���ѡ������");
				   		return;
				   	}
				   	if(Ext.getCmp("Event$description8").getValue().trim()==''){
				   		Ext.MessageBox.alert("��ʾ","�¼���������Ϊ�գ�");
				   		return;
				   	}
				   	var pSort=Ext.getCmp("Event$problemSortCombo").getValue();
			        if(Ext.getCmp("Event$problemSortCombo").getRawValue()==""){
			        	Ext.getCmp("Event$problemSortCombo").clearValue();
			        }
			        Ext.getCmp("useKnowButton").disable();
			        Ext.getCmp("closeButton").disable();
                    var eventId = this.dataId;
                    var taskId = this.taskId;
                    var desc=Ext.encode(Ext.getCmp("Event$description8").getValue());//�¼�����
                     var siId=Ext.getCmp("childsort").getValue();
                       Ext.Ajax.request({
                          url :webContext + '/eventAction_endEventWithValidKnowledge.action',
                          params: { 
                              eventId : this.dataId,
                              knowledgeId : knowledgeId,
                              serviceItemId : siId,
                              desc : desc.substring(1,desc.length-1),//�����¼�����
                              problemSortId : pSort
                            },
                          method:'post', 
                          success:function(){
                                Ext.Ajax.request({
                                url: webContext + '/extjs/workflow?method=getDataFromPage&taskId=' + taskId + '&next=touserconfirm',//'&users=userConfirm:' + eventSubmitter,
                                method:'post', 
                                success:function(response,options){
                                Ext.MessageBox.alert('��ʾ','�ɹ�ʹ�øý���������һ���¼���',function(){//���ĳ�ԭ������ʾ 2009-8-23 16:17:33 by wanghao
                                        windowSkip.close();
                                    window.parent.auditContentWin.close();
                                    });
                                    
                                },
                                failure:function(response,options){
                                    Ext.MessageBox.alert('��ʾ','�ύ�û�ȷ��ʧ�ܣ�');
                                    Ext.getCmp("useKnowButton").enable();
			   					    Ext.getCmp("closeButton").enable();
                                }
                        });
                        
                          },
                          failure:function(response,options){
                            Ext.MessageBox.alert("��ʾ","ʹ������ʧ�ܣ�");
                            Ext.getCmp("useKnowButton").enable();
	   					    Ext.getCmp("closeButton").enable();
                          }
                      });
                },
                scope : this
             
             },{
                    text : '�ر�',
                    id : 'closeButton',
                    handler : function() {
                            windowSkip.close(); 
                    },
                    scope : this
                }]
              });
            windowSkip.show();
      },
    //��ת�����⴦��ҳ��
//    deal:function(){
//              var record = this.grid.getSelectionModel().getSelected();
//              var records = this.grid.getSelectionModel().getSelections();
//                if(!record){
//                    Ext.Msg.alert("��ʾ","����ѡ��Ҫ�޸ĵ��У�");
//                    return;
//                }
//                if(records.length>1){
//                    Ext.Msg.alert("��ʾ","�޸�ʱֻ��ѡ��һ�У�");
//                    return;
//                }
//               var pdataId=record.get('Problem$id');    
//               var conn = Ext.lib.Ajax.getConnectionObject().conn;
//               conn.open("POST", webContext+'/problemAction_findProblemStatus.action?eventId='+pdataId,false);
//               conn.send(null); 
//               var result = Ext.decode(conn.responseText);
//               var pStatus=result.STATUS;
//                 if(pStatus=='dealing'){
//                     window.location =webContext+'/user/event/transactionFlow/engineerprobleminfo.jsp?pdataId='+pdataId + '&EventId='+this.dataId + '&taskId=' + this.taskId;
//                 }else{
//                     window.location =webContext+'/user/event/transactionFlow/skipengineerprobleminfo.jsp?pdataId='+pdataId+'&EventId='+this.dataId+'&taskId=' + this.taskId;
//                }
//    },
   //ָ��������
    getSupportAndEngineerComs: function(){
    	this.asistType = new Ext.form.ComboBox({
	        id : 'supportGroup',
	        mode : 'remote',
	        displayField : 'groupName',
	        valueField : 'id',
	        lazyRender : true,
	        allowBlank : false,
	        hiddenName : 'EventAssign$supportGroup',
	        emptyText : '��������б���ѡ��...',
	        resizable : true,
	        triggerAction : 'all',
	        forceSelection :true,
	        pageSize : 10,
	        selectOnFocus : true,
	        width : 200,
	        store : new Ext.data.JsonStore({
	            url : webContext + '/supportGroupAction_findSupportGroup.action',
	            fields : ['id', 'groupName'],
	            totalProperty : 'rowCount',
	            root : 'data',
	            id : 'id'
	        }),
	        listeners : {
	            'beforequery' : function(queryEvent) {
	                var param = queryEvent.combo.getRawValue();
	                if (queryEvent.query == '') {
	                    param = '';
	                }
	                this.store.baseParams={"groupName" : param,"deleteFlag" :0};
	                this.store.load();
	                return false;
	            },
	            'select' : function(combo, record, index) {
	                Ext.getCmp('supportGroupEngineer').clearValue();
	                }
            }
    });
    this.asistName = new Ext.form.ComboBox({
        id : 'supportGroupEngineer',
        name : 'supportGroupEngineer',
        displayField : 'userInfo',
        lazyRender : true,
        valueField : 'id',
       // listWidth :300,
         width : 200,
        hiddenName : 'EventAssign$supportGroupEngineer',
        resizable : true,
        triggerAction : 'all',
        forceSelection :true,
        selectOnFocus : true,
        emptyText : '��������б���ѡ��...',
        mode : 'remote',
        pageSize : 10,
        store:new Ext.data.JsonStore({
            url : webContext + '/supportGroupAction_findSupportGroupEngineer.action',
            fields:['id','userInfo'],
            totalProperty:'rowCount',
            root:'data'
            }),
        listeners:{
            'beforequery' : function(queryEvent){
                var param = queryEvent.combo.getRawValue();
                var groupId=Ext.getCmp('supportGroup').getValue();
                    if (queryEvent.query == '') {
                        param = '';
                    }
                this.store.baseParams={itcode : param,groupId : groupId};
                this.store.load();
                return false;
            }
        }
    });
    },
   getromote : function(flag){
   	if(flag=="first"){
   		Ext.getCmp("firstSelfDeal").disable();    
   		Ext.getCmp("firstOtherGroup").disable();    
   	}else{
      Ext.getCmp("otherGroup").disable();    
      Ext.getCmp("saveServiceType").disable();
      Ext.getCmp("backHead").disable();
      Ext.getCmp("operateSelf").disable();
   	}
        var envForm = new Ext.form.FormPanel({
            id:'eventassignother',
            layout : 'table',
            width : 616,
            height : 'auto',
            layoutConfig : {
                columns : 1
            },
            defaults : {
                bodyStyle : 'padding:5px'
            },
            frame : true,
            items : [{xtype : 'fieldset',   
                    layout : 'table',
                    layoutConfig : {
                        columns : 4
                    },
            title:"ָ��֧����",//ѡ�񷵻�������
            items:[{html: "ָ��֧����:&nbsp;",cls: 'common-text',style:'width:75;text-align:right'},
                    this.asistType,
                    {html: "֧�ֹ���ʦ:&nbsp;",cls: 'common-text',style:'width:85;text-align:right'},
                    this.asistName]
             },{xtype : 'fieldset', 
                    id :'reasion',
                    layout : 'table',
                    layoutConfig : {
                      columns : 2
                    },
            title : "ָ��ԭ����������",//��д���������������
            items : [ new Ext.form.TextArea({
					xtype : 'textarea',
					id : 'EventAssign$remark',
					colspan : 3,
					name : 'EventAssign$remark',
					width : 570,
					height : 70,
					allowBlank : false,
					emptyText :"���ڴ���д���ڴ�����¼������еķ��ֺͷ������԰�����ߵĹ��̿��١�׼ȷ�Ľ���û������⣬лл��",
					fieldLabel : '��ע'
				})]
             }
             ] 

        });
        var otherFlag=flag;
        var win = new Ext.Window({
                title : '�����鴦��',
                width : 630,
                height : 'auto',
                modal : true,
                frame : true,                        
                //maximizable : true,
                items : envForm,
                bodyStyle : 'padding:1px',
                listeners:{
                    "close":function(){
                        if(otherFlag=="first"){
					   		Ext.getCmp("firstSelfDeal").enable();    
					   		Ext.getCmp("firstOtherGroup").enable();    
					   	}else{
					      Ext.getCmp("otherGroup").enable();    
					      Ext.getCmp("saveServiceType").enable();
					      Ext.getCmp("backHead").enable();
					      Ext.getCmp("operateSelf").enable();
					   	}
                    }
                },
                buttons : [{//�����ťʱ�����ȴ�����еõ��������ݣ����ύ����ӦAction�еķ���
                    text : 'ȷ��',
                    id   :'submitbutton',
                    handler : function() {
                        if(Ext.getCmp('eventassignother').getForm().isValid()){
                        Ext.getCmp("submitbutton").disable();
                        if(otherFlag=="second"){
                        	var eventFrequency = frequency;
					        var eventPonderance = ponderance;
					        if(eventFrequency == '') {
					            eventFrequency = frequencyId;
					        }
					        if(eventPonderance == '') {
					            eventPonderance = ponderanceId;
					        }
		                	var si=Ext.getCmp("childsort");
		                	if(si.getRawValue()==""||si.getRawValue()==null){
		                		si.clearValue();
		                	}
//		                	if(si.getValue()==""){
//		                		 Ext.MessageBox.alert("��ʾ","������������ѡ������");
//		                		 return;
//		                	}
		                	var description=Ext.getCmp("Event$description8").getValue();
						   	if(description.trim()==''){
						   		Ext.MessageBox.alert("��ʾ","�¼���������Ϊ�գ�");
						   		return;
						   	}
						   	var pSort=Ext.getCmp("Event$problemSortCombo").getValue();
					        if(Ext.getCmp("Event$problemSortCombo").getRawValue()==""){
					        	Ext.getCmp("Event$problemSortCombo").clearValue();
					        }
						   	var desc=Ext.encode(description);
						   	Ext.Ajax.request({
								url : webContext+ '/eventAction_saveEventDescInDealing.action',
								params : {
										eventId : this.dataId,
										serviceItemId : si.getValue(),
										frequency : eventFrequency,
										ponderance : eventPonderance,
										desc : desc.substring(1,desc.length-1),
										problemSortId : pSort
								},
								success : function(response, options) {},
								failure : function(response, options) {}
							});
                        }
                        if (Ext.encode(Ext.getCmp("EventAssign$remark").getValue()).length!=2) {
                            var eventassignparamother = Ext.encode(getFormParam('eventassignother'));
                            Ext.Ajax.request({
                                url : webContext + '/eventAction_assignOtherGroup.action',
                                params : {  
                                    eid : this.dataId,
                                    eventassignparamother : eventassignparamother
                                    },
                                method : 'post',  
                                success : function(response, options){
                                    var supportGroupEngineerId = eval('('+ response.responseText + ')').data;
                                    if('nodata'==supportGroupEngineerId){
                                        Ext.MessageBox.alert("��ʾ","��ѡ���֧����û�д����ˣ���ѡ������֧���飡",function(){
                                            Ext.getCmp("submitbutton").enable();
                                        });
                                    }else{
                                        //ͬ���õ���ǰ�ڵ�����
                                        var currentNodeName;
                                        var conn = Ext.lib.Ajax.getConnectionObject().conn;
                                        conn.open("POST", webContext+'/eventAction_findCurrentNodeName.action?taskId='+this.taskId,false);
                                        conn.send(null);    
                                        if(conn.status=='200'){
                                            var result = Ext.decode(conn.responseText);
                                            currentNodeName = result.currentNodeName;
                                        }
                                        var users = supportGroupEngineerId;
                                        //--------------------------------------����������----------------------------------------------
                                        if(currentNodeName!='otherOrgProcess'){//�¸��ڵ�����
                                            Ext.Ajax.request({
                                                    url: webContext + '/extjs/workflow?method=getDataFromPage&taskId=' + this.taskId + '&next=otherOrgProcess'+ '&users=otherOrgProcess:' + users,
                                                    method:'post', 
                                                    success:function(response,options){
                                                        Ext.MessageBox.alert('��ʾ','����������ɹ���',function(){
                                                            win.close()
                                                            window.parent.auditContentWin.close();
                                                        });
                                                    },
                                                    failure:function(response,options){
                                                        Ext.MessageBox.alert('��ʾ','����������ʧ�ܣ�',function(){
                                                            Ext.getCmp("submitbutton").enable();
                                                        });
                                                    }
                                            });
                                        }else{//��ǰ�ڵ�����
                                            Ext.Ajax.request({
                                            	//2010-06-18 modified by huzh for ��ǰ�ڵ�Ϊ�����鴦��ʱ��������ʦ����ڵ� begin
                                            	url: webContext + '/extjs/workflow?method=getDataFromPage&taskId=' + this.taskId + '&next=return'+ '&users=engineerProcess:' + users,
                                              //url: webContext + '/extjs/workflow?method=reAssignToNode&taskId=' + this.taskId + '&next=otherOrgProcess' + '&users=otherOrgProcess:' + users,
                                                //2010-06-18 modified by huzh for ��ǰ�ڵ�Ϊ�����鴦��ʱ��������ʦ����ڵ� end    
                                                    method:'post', 
                                                    success:function(response,options){
                                                        Ext.MessageBox.alert('��ʾ','����������ɹ���',function(){
                                                            win.close()
                                                            window.parent.auditContentWin.close();
                                                        });
                                                    },
                                                    failure:function(response,options){
                                                        Ext.MessageBox.alert('��ʾ','����������ʧ�ܣ�',function(){
                                                            Ext.getCmp("submitbutton").enable();
                                                        });
                                                    }
                                            });
                                        }
                                     }
                                },
                                failure:function(response,options){
                                    Ext.MessageBox.alert("��ʾ","��������ʧ�ܣ�",function(){
                                        Ext.getCmp("submitbutton").enable();
                                    });
                                },
                                scope : this
                            });
                        }else{
                            Ext.MessageBox.alert("��ʾ","����д��ע��Ϣ��",function(){
                                Ext.getCmp("submitbutton").enable();
                            });
                        }
                        }else{
                             Ext.MessageBox.alert("��ʾ","��ʾΪ��ɫ�����ߵ���Ϊ�����",function(){
                                Ext.getCmp("submitbutton").enable();
                             });
                        }
                    },
                    scope : this

                }, {
                    text : '�ر�',
                    handler : function() {
                        win.close();
                          if(otherFlag=="first"){
					   		Ext.getCmp("firstSelfDeal").enable();    
					   		Ext.getCmp("firstOtherGroup").enable();    
					   	}else{
					      Ext.getCmp("otherGroup").enable();    
					      Ext.getCmp("saveServiceType").enable();
					      Ext.getCmp("backHead").enable();
					      Ext.getCmp("operateSelf").enable();
					   	}
                    },
                    scope : this
                }]

            });
        win.show();
    },
   //���ر����鳤
   getlocaler : function(){
   	    var si=Ext.getCmp("childsort");
   	    if(si.getRawValue()==""||si.getRawValue()==null){
   	    	si.clearValue();
   	    }
	   	if(si.getValue()==""){
	   		Ext.MessageBox.alert("��ʾ","��������б���ѡ������");
	   		return;
	   	}
	   	if(Ext.getCmp("Event$description8").getValue().trim()==''){
	   		Ext.MessageBox.alert("��ʾ","�¼���������Ϊ�գ�");
	   		return;
	   	}
        var da = new DataAction();
        var data=da.getPanelElementsForAdd("serviceEngineeraddMes_pagepanel");
        var dataform =data;// this.splitOwn(data);
        
        dataform[1].emptyText="���ڴ���д���ڴ�����¼������еķ��ֺͷ������԰�����ߵĹ��̿��١�׼ȷ�Ľ���û������⣬лл��";
        dataform[1].height=120;
        dataform[1].width= 530;
        dataFrom=Ext.encode(dataform)
        var envForm = new Ext.form.FormPanel({
            id:'eventassign',
            layout : 'table',
            width : 540,
            height : 140,
            layoutConfig : {
                columns : 2
            },
            frame : true,
            defaults : {
                bodyStyle : 'padding:2px'
            },
            items : dataform

        });
        var win = new Ext.Window({
                title : '�ύ�����鳤����',
                width : 555,
                height : 200,
                modal : true,
               // maximizable : true,
                items : envForm,    
                closeAction : 'hide',
                bodyStyle : 'padding:1px',
                buttons : [{//�����ťʱ�����ȴ�����еõ��������ݣ����ύ����ӦAction�еķ���
                    text : '����',
                    id  :'savebutton',
                    handler : function() {   
                        if (Ext.encode(Ext.getCmp("EventAssign$remark").getValue()).length==2) {
                            Ext.MessageBox.alert("��ʾ","����д��ע��Ϣ��");
							return;
                         }
                          if (Ext.getCmp("Event$description8").getValue().trim()=="") {
                            Ext.MessageBox.alert("��ʾ","�¼���������Ϊ�գ�");
							return;
                         }
                         Ext.getCmp("savebutton").disable();
                        Ext.getCmp("saveServiceType").disable();
						Ext.getCmp("backHead").disable();
						Ext.getCmp("otherGroup").disable();
						Ext.getCmp("operateSelf").disable();
						var desc=Ext.encode(Ext.getCmp("Event$description8").getValue());//�¼�����
						var pSort=Ext.getCmp("Event$problemSortCombo").getValue();
				        if(Ext.getCmp("Event$problemSortCombo").getRawValue()==""){
				        	Ext.getCmp("Event$problemSortCombo").clearValue();
				        }
                           var eventassignparam = Ext.encode(getFormParam('eventassign'));
                           var siId=si.getValue();
                        	Ext.Ajax.request({
                                url :webContext + '/eventAction_saveEventAssign.action',
                                params: {
                                        eid : this.dataId,
                                        serviceItem : siId,
                                        eventassignparam : eventassignparam,
                                      	 desc : desc.substring(1,desc.length-1),//�����¼�����
                                      	 problemSortId : pSort
                                        },
                                method:'post',  
                                success:function(){
                                    //store.reload();
                                    //-------------search groupLeaderId----------------------
                                    var remark;
                                    var groupLeaderId;
                                    var url = webContext+'/supportGroupAction_findSupprotGroupLeader.action?eventId=' + this.dataId+"&serviceItemId="+siId;
                                    var conn = Ext.lib.Ajax.getConnectionObject().conn;
                                    conn.open("get", url, false);
                                    conn.send(null);
                                    if (conn.status == "200") {
                                        var responseText = conn.responseText;
                                        var data = Ext.decode(responseText);
                                        groupLeaderId = data.groupLeaderId;
                                    }  
                                    if(groupLeaderId=='noLeader'){
                                        Ext.MessageBox.alert('��ʾ','�Բ���û���鳤��');
                                        Ext.getCmp("savebutton").enable();
				                        Ext.getCmp("saveServiceType").enable();
										Ext.getCmp("backHead").enable();
										Ext.getCmp("otherGroup").enable();
										Ext.getCmp("operateSelf").enable();
                                        return false;
                                    }
                                    
                                    var users = groupLeaderId;
                                    
                                    Ext.Ajax.request({
                                        url: webContext + '/extjs/workflow?method=getDataFromPage&taskId=' + this.taskId + '&next=cannotresolve'+ '&users=headerProcess:' + users,
                                        method:'post', 
                                        success:function(response,options){
                                            Ext.MessageBox.alert('��ʾ','���������鳤�ɹ���',function(){
                                                window.parent.auditContentWin.close();
                                            });
                                        },
                                        failure:function(response,options){
                                            Ext.MessageBox.alert('��ʾ','���������鳤ʧ�ܣ�',function(){
                                             Ext.getCmp("savebutton").enable();
						                        Ext.getCmp("saveServiceType").enable();
												Ext.getCmp("backHead").enable();
												Ext.getCmp("otherGroup").enable();
												Ext.getCmp("operateSelf").enable();
                                            });
                                        }
                                    });
                                
                                    win.close();
                                },
                                failure:function(response,options){
                                    Ext.MessageBox.alert("��ʾ","��������ʧ�ܣ�",function(){
                                     Ext.getCmp("savebutton").enable();
				                        Ext.getCmp("saveServiceType").enable();
										Ext.getCmp("backHead").enable();
										Ext.getCmp("otherGroup").enable();
										Ext.getCmp("operateSelf").enable();
                                    });
                                },
                                scope : this
                            });
                    },
                    scope : this
                }, {
                    text : '�ر�',
                    handler : function() {
                        win.hide();
                    },
                    scope : this
                }]
            });
        win.show();
    
    },
    //ɾ������
//    remove : function() {
//        var records = this.grid.getSelectionModel().getSelections();
//        if (records.length==0) {
//            Ext.Msg.alert("��ʾ", "����ѡ��Ҫɾ���ļ�¼��");
//            return;
//        }
//        Ext.Msg.confirm('��ʾ', '��ȷ��Ҫ����ɾ��������', function(button) {
//            if (button == 'yes') {
//                var problemsId=new Array();
//                for(var i=0;i<records.length;i++){
//                    problemsId.push(records[i].get('Problem$id'));
//                }
//                problemsId=Ext.encode(problemsId);
//                Ext.Ajax.request({
//                        url :webContext + '/problemAction_removeProblems.action',
//                        params : {problemsId:problemsId},
//                        success:function(response){
//                            if(response.responseText==''){
//                                Ext.Msg.alert("��ʾ","ɾ���ɹ���",function(){
//                                    Ext.getCmp("problemgrid").getStore().reload();
//                                });
//                            }
//                            else 
//                               Ext.Msg.alert("��ʾ","���Ϊ:"+response.responseText+"�������ѹ��������������ɾ����");
//                       },scope:this
//                        
//                    });
//                }
//        }, this);
//
//    },

    // ��������
// create : function() {
//        var da = new DataAction();
//        var data=da.getPanelElementsForAdd("problemForm_pagepanel");
//         for(var i=0;i<data.length;i++){
//             if(data[i].id=="Problem$problemCisn"){
//               data.remove(data[i]);
//             }
//        }
//        var dataform = this.splitOwn(data);
//        var envForm = new Ext.form.FormPanel({
//            id:"evformaa",
//            layout : 'table',
//            width : 580,
//            height : 250,
//                layoutConfig : {
//                    columns : 4
//                },
//                defaults : {
//                    bodyStyle : 'padding:5px'
//                },
//            frame : true,
//            items : dataform
//
//        });
//        Ext.getCmp("Problem$summary").allowBlank = false;
//        var win = new Ext.Window({
//                title : '�������',
//                width : 598,
//                height : 323,
//                modal : true,
//                //maximizable : true,
//                autoScroll : true,
//                items : [envForm],
//                closeAction : 'hide',
//                bodyStyle : 'padding:2px',
//                buttons : [{
//                    text : '����',
//                    id : 'submitBookInfo',
//                    handler :function() {
//                       if (Ext.getCmp('evformaa').form.isValid()) {
//                       var panelparam = Ext.encode(getFormParam('evformaa'));
//                       Ext.Ajax.request({
//                            url :webContext + '/problemAction_saveProblem.action',
//                            params: { eid:this.dataId,
//                                     panelparam:panelparam
//                                     },
//                            method:'post', 
//                            success:function(){
//                           Ext.MessageBox.alert("��ʾ","���Ᵽ��ɹ���",function(){
//                                
//                                Ext.getCmp('problemgrid').store.reload();
//                               win.close()
//                           });
//                               
//                          },
//                           failure:function(response,options){
//                             Ext.MessageBox.alert("��ʾ","��������ʧ�ܣ�",function(){ Ext.getCmp("submitBookInfo").enable();});
//                          }
//                      });
//                      }else{
//                          Ext.MessageBox.alert("��ʾ","����д�����Ϣ��",function(){
//                           Ext.getCmp("submitBookInfo").enable();
//                          });
//                      }
//                    },
//                    scope : this
//                },{
//                    text : '�ر�',
//                    handler : function() {
//                        win.hide();
//                    },
//                    scope : this
//                }]
//
//            });
//        Ext.getCmp("submitBookInfo").on("click",function(obj){
//              Ext.getCmp("submitBookInfo").disable();
//              });
//        win.show();
//        
//     
//    },
   
   getSearchForm : function() {//�¼���ϸ����
        var childsort = new Ext.form.ComboBox({
            name : "childsort",
            id : 'childsort',
            fieldLabel : "������",
            width : 200,
            hiddenName : 'Event$scidData',
            displayField : 'name',
            valueField : 'id',
            lazyRender : true,
            allowBlank : false,
            minChars : 50,
            resizable : true,
            forceSelection :true,
            triggerAction : 'all',
            emptyText : '������������ѡ��...',
            selectOnFocus : true,
            store : new Ext.data.JsonStore({
                url : webContext
                        + '/eventAction_findServiceItem.action',
                fields : ['id', 'name'],
                totalProperty : 'rowCount',
                root : 'data',
                id : 'id'
            }),
            pageSize : 10,
            initComponent : function() {
            	this.store.load({
						params : {
							id : Ext.getCmp('childsort').getValue(),
							start : 0
						},
						callback : function(r, options, success) {
							Ext.getCmp('childsort').setValue(Ext.getCmp('childsort').getValue());
						}
					});
             },
            listeners : {
                'beforequery' : function(queryEvent) {
                	Ext.getCmp('Knowledge$knowProblemTypeCombo').setValue("");
                    var param = queryEvent.combo.getRawValue();
                    var val = queryEvent.combo.getValue();
                    if (queryEvent.query == '') {
                        param = '';
                    }
                    this.store.baseParams={"name" : param,official:1};
                    this.store.load();
                    return true;
                },
                'select' : function(combo,record,index){
	                Ext.getCmp("serviceItemBySu").setValue(combo.value);
	                Ext.getCmp("serviceItemBySu").initComponent();
	               	var knowGrid=Ext.getCmp("KnowLedgeListGrid");
	               	 var param = {
			            'mehtodCall' : 'query',
			            status : 1,
			            start : 0,
			            serviceItem : Ext.getCmp("serviceItemBySu").getValue()
			        };
			        knowGrid.store.removeAll();
			        knowGrid.store.load({
			            params : param
			        });
                }
                
            }
        });
        //2010-06-23 add by huzh for ����¼��ύ�˵绰 begin
       var submitUserPhone= new Ext.form.TextField({
					fieldLabel : '����˵绰',
					xtype : 'textfield',
					id : 'sUserPhone',
					name : 'sUser$phone',
					width : 200,
					allowBlank : true,
					readOnly : true
				});
				
		var eventDesc=new Ext.form.TextArea({
				xtype:"textarea",
				id:"Event$description8",
				name:"Event$description",
				width:"9999",
				style:"",
				allowBlank:false,
				validator:'',
				fieldLabel:"�¼�����"
			});
		 //2010-06-23 add by huzh for ����¼��ύ�˵绰 end	
		var problemSort=new Ext.form.ComboBox({
				name : "Event$problemSort",
				id : 'Event$problemSortCombo',
				fieldLabel : "����",
				width : 200,
				displayField : 'name', 
				valueField : 'id',
				resizable : true,
				emptyText :'����7888-2��ʹ��',
				triggerAction : 'all',
				allowBlank : true,//����Ϊ��
				forceSelection :true,
				store : new Ext.data.JsonStore({
					url : webContext
							+ '/eventAction_findAllProblemSort.action',
					fields : ['id', 'name'],
					totalProperty : 'rowCount',
					root : 'data',
					id : 'id'
				}),
				pageSize : 10,
				listeners : {
					'beforequery' : function(queryEvent) {
						var param = queryEvent.combo.getRawValue();
						var val = queryEvent.combo.getValue();
						if (queryEvent.query == '') {
							param = '';
						}
						this.store.baseParams={name : param};
						this.store.load();
						return false;
					}
				},
			initComponent : function() {
            	this.store.load({
						params : {
							id : Ext.getCmp('Event$problemSortCombo').getValue(),
							start : 0
						},
						callback : function(r, options, success) {
							Ext.getCmp('Event$problemSortCombo').setValue(Ext.getCmp('Event$problemSortCombo').getValue());
						}
					});
             }
		});
        var da = new DataAction();
        var data=da.getPanelElementsForEdit("page_event_suppGroupEngineer","panel_event_suppGroupEngineer",this.dataId);
        var newdata=new Array();
        newdata.push(Ext.getCmp("childsort"));
         for(var  i=0;i<data.length;i++){
             if(data[i].id=="Event$submitUserCombo"
            	||data[i].id=="Event$createUserCombo"
            		||data[i].id=="Event$dealuserCombo"){
            	data[i].xtype="textfield";
            }
            if(data[i].name=="Event$submitDate"){
                data[i].value=data[i].value.substring(0,19);
            }
             if(data[i].id=="Event$description"){
             	Ext.getCmp("Event$description8").setValue(data[i].value);
                data[i]=eventDesc;
            }
            
            if(data[i].xtype=="panel"){
                data[i].items[0].disabled=true;
				data[i].items[1].html=data[i].items[1].html.replace(/display:/g, "display:none");
                }
            if(data[i].name=="Event$frequency"){
            	data[i].emptyText='��������б���ѡ��...';
                Ext.getCmp('Event$frequencyCombo').setValue(frequencyName);
                data[i].on('select',function(combo,record,index){frequency=combo.value});
            }else if(data[i].name=="Event$ponderance"){
            	data[i].emptyText='��������б���ѡ��...';
                Ext.getCmp('Event$ponderanceCombo').setValue(ponderanceName);
                data[i].on('select',function(combo,record,index){ponderance=combo.value});
            }else if(data[i].name=="Event$description"){
            	newdata.push(Ext.getCmp("Event$problemSortCombo"));
            }else{
                data[i].readOnly=true;
                data[i].hideTrigger=true;
                data[i].emptyText="";
            }

            if(data[i].name!="Event$scidData"){
          	  newdata.push(data[i]); 
            }
              //2010-06-23 modified by huzh for ����¼��ύ�˵绰 begin
             if(data[i].id=="Event$submitUserCombo"){
            	 data[i].xtype="textfield";
        	  	newdata.push(Ext.getCmp("sUserPhone"));
             }	
              //2010-06-23 modified by huzh for ����¼��ύ�˵绰 end
         }
        Ext.getCmp('sUserPhone').setValue(sUserPhone);
        Ext.getCmp('Event$submitUserCombo').setValue(submitUser);
        Ext.getCmp('Event$createUserCombo').setValue(createUser);
        Ext.getCmp('Event$dealuserCombo').setValue(dealuser);
 		Ext.getCmp('Event$problemSortCombo').setValue(problemSortId); 
 		Ext.getCmp('Event$problemSortCombo').initComponent();
        var biddata = this.split(newdata);
        this.panel = new Ext.form.FormPanel({  
            id : 'eventDetails',
            layout : 'table',
            height : 'auto',   
            width : 749,
            frame : true,
            collapsible : true,
            defaults : {
                bodyStyle : 'padding:0px'
            },
            layoutConfig : {
                columns : 4
            },
            title : "�¼���ϸ����",
            items : biddata
        });
        return this.panel;
       
    },
    
     getKnowFrom : function(){
     	this.submitCheckBox=new Ext.form.Checkbox({
				id:'submitCheckBox',  
				xtype:"checkbox",
				name:"Know$SubmitFlag",
				hiddenName : 'Know$SubmitFlagCB',
				fieldLabel:"�Ƿ��ύ֪ʶ",
				boxLabel:"��",
				checked:false
		});
		this.submitCheckBox.on("check", function(field) {
				var value = field.getValue();
				if (value == false) {
					this.enabledValue.setValue("no");
					return;
				} else if (value == true) {
					this.enabledValue.setValue("yes");
					return;
				}
			}, this);
		this.enabledValue = new Ext.form.Hidden({
				id : 'enabledHid',
				name : 'enabled',
				value : "no"
		});
     	var kData=[{
					html : '��������:',
					cls : 'common-text',
					style : 'width:120;text-align:right'
				}, new Ext.form.ComboBox({
					xtype : 'combo',
					hiddenName : 'Knowledge$knowProblemType',
					id : 'Knowledge$knowProblemTypeCombo',
					width : 200,
					fieldLabel : '��������',
					lazyRender : true,
					displayField : 'name',
					valueField : 'id',
					forceSelection :true,
					emptyText : '��������б���ѡ��...',
					allowBlank : true,
					name : 'Knowledge$knowProblemType',
					triggerAction : 'all',
					minChars : 50,
					queryDelay : 700,
					store : new Ext.data.JsonStore({
						url : webContext
								+ '/extjs/comboDataAction?clazz=com.digitalchina.itil.knowledge.entity.KnowProblemType',
						fields : ['id', 'name'],
						totalProperty : 'rowCount',
						root : 'data',
						id : 'id'
					}),
					pageSize : 10,
					listeners : {
						'beforequery' : function(queryEvent) {
							var serviceitem=Ext.getCmp("childsort").getValue();
							if(serviceitem==""){
								Ext.MessageBox.alert("��ʾ","���ȴ������б���ѡ������");
								return false;
							}
							var param = queryEvent.combo.getRawValue();
							if (queryEvent.query == '') {
								param = '';
							}
							this.store.baseParams={
								name : param,
								serviceItem : serviceitem,
								deleteFlag : 0
							}
							this.store.load({
								params:{
									start:0
								}
							});
							return false;
						}
					},
					initComponent : function() {
						this.store.load({
							params : {
								id : Ext.getCmp('Knowledge$knowProblemTypeCombo').getValue(),
								start : 0
							},
							callback : function(r, options, success) {
								Ext.getCmp('Knowledge$knowProblemTypeCombo').setValue(Ext.getCmp('Knowledge$knowProblemTypeCombo').getValue());
							}
						});
					}
				}),{
					html : '��������:',
					cls : 'common-text',
					style : 'width:130;text-align:right'
				}, new Ext.form.TextField({
					fieldLabel : '��������',
					xtype : 'textfield',
					id : 'Knowledge$summary',
					name : 'Knowledge$summary',
					width : 200,
					allowBlank : true
				}),{
					html : '�������:',
					cls : 'common-text',
					style : 'width:120;text-align:right'
				},new Ext.form.FCKeditor({
					height : 100,
					colspan : 3,
					id:'Knowledge$resolvent',
					name : 'Knowledge$resolvent',
					allowBlank : true,
					width : 493
				}),{
					html : "����ʽ:",
					cls : 'common-text',
					style : 'width:120;text-align:right;'
				}, new Ext.form.ComboBox({
					xtype : 'combo',
					name : 'Event$eventDealType',
					hiddenName : 'Event$eventDealType',
					id : 'Event$eventDealTypeCombo',
					width : 200,
					fieldLabel : '�¼�����ʽ',
					lazyRender : true,
					displayField : 'name',
					forceSelection :true,
					valueField : 'id',
					emptyText : '��������б���ѡ��...',
					allowBlank : true,
					triggerAction : 'all',
					minChars : 50,
					queryDelay : 700,
					store : new Ext.data.JsonStore({
						url : webContext
								+ '/extjs/comboDataAction?clazz=com.digitalchina.itil.event.entity.EventDealType',
						fields : ['id', 'name'],
						totalProperty : 'rowCount',
						root : 'data',
						id : 'id'
					}),
					pageSize : 10,
					listeners : {
						'beforequery' : function(queryEvent) {
							var param = queryEvent.combo.getRawValue();
							if (queryEvent.query == '') {
								param = '';
							}
							this.store.baseParams={
								name : param
							}
							this.store.load({
								params : {
									start : 0
								}
							});
							return false;
						}
					},
					initComponent : function() {
						this.store.load({
							params : {
								id : Ext.getCmp('Event$eventDealTypeCombo').getValue(),
								start : 0
							},
							callback : function(r, options, success) {
								Ext.getCmp('Event$eventDealTypeCombo').setValue(Ext.getCmp('Event$eventDealTypeCombo').getValue());
							}
						});
					}
				}),{
					html : "�Ƿ��ύ֪ʶ:",
					cls : 'common-text',
					style : 'width:130;text-align:right;'
				},this.submitCheckBox,this.enabledValue];
			
			this.knowPanel = new Ext.form.FormPanel({
				id : 'knowPanel',
				layout : 'table',
				height : 200,
				width :749,
				defaults : {
	                bodyStyle : 'padding:0px'
	            },
				buttonAlign : "center",
	            buttons : [{
	                xtype : 'button',
	                text : '����',
	                id:'saveServiceType',
	                handler : this.modifyEventServiceTypeAndItem,
	                iconCls : 'save',
	                scope : this,
	                style : 'width:100;text-align:right;margin-left:20px'
	            },         
	             {
	                xtype : 'button',
	                text : '���������鳤',
	                id:'backHead',
	                handler : this.getlocaler,
	                iconCls : 'back',
	                scope : this,
	                style : 'width:100;text-align:right;margin-left:0px'
	            },{ 
	                xtype :'button',
	                text : 'ָ��������',
	                iconCls : 'back',
	                id : "otherGroup", 
	                handler : function(){
	               		 this.getromote("second")
	                
	                },
	                scope : this,
	                style : 'width:100;text-align:right;margin-left:0px'
	            },{
	                xtype :'button',
	                text : '������ύ�û�',
	                id : "operateSelf",
	                iconCls : "save",
	                handler : function() {
	                	var eventFrequency = frequency;
				        var eventPonderance = ponderance;
				        if(eventFrequency == '') {
				            eventFrequency = frequencyId;
				        }
				        if(eventPonderance == '') {
				            eventPonderance = ponderanceId;
				        }
	                	var taskId=this.taskId;
	                	var si=Ext.getCmp("childsort");
	                	if(si.getRawValue()==""||si.getRawValue()==null){
	                		si.clearValue();
	                	}
	                	if(si.getValue()==""){
	                		 Ext.MessageBox.alert("��ʾ","������������ѡ������");
	                		 return;
	                	}
	                	//2010-07-07 add by huzh for �����¼����� begin
	                	var description=Ext.getCmp("Event$description8").getValue();
					   	if(description.trim()==''){
					   		Ext.MessageBox.alert("��ʾ","�¼���������Ϊ�գ�");
					   		return;
					   	}
					   	var desc=Ext.encode(description);
						//2010-07-07 add by huzh for �����¼����� end
	                	var type=Ext.getCmp('Knowledge$knowProblemTypeCombo');
					     var summary=Ext.getCmp('Knowledge$summary');
					     var resolvent=Ext.getCmp('Knowledge$resolvent');
					     if(type.getRawValue().trim()==""){
								type.setValue("");
							}
						var pSort=Ext.getCmp("Event$problemSortCombo").getValue();
				        if(Ext.getCmp("Event$problemSortCombo").getRawValue()==""){
				        	Ext.getCmp("Event$problemSortCombo").clearValue();
				        }
						Ext.getCmp("saveServiceType").disable();
						Ext.getCmp("backHead").disable();
						Ext.getCmp("otherGroup").disable();
						Ext.getCmp("operateSelf").disable();
	                	if(type.getValue()!=""||summary.getValue().trim()!=""||resolvent.getValue().trim()!=""){
	                		if(resolvent.getValue().trim()==''||type.getValue()==""||summary.getValue().trim()==""){
								Ext.MessageBox.alert("��ʾ", "����д�������͡��������Ƽ����������");
								Ext.getCmp("saveServiceType").enable();
								Ext.getCmp("backHead").enable();
								Ext.getCmp("otherGroup").enable();
								Ext.getCmp("operateSelf").enable();
								return;
							}
							if(Ext.getCmp('Event$eventDealTypeCombo').getValue()==""){
								Ext.MessageBox.alert("��ʾ", "��������б���ѡ���¼�����ʽ��");
								Ext.getCmp("saveServiceType").enable();
								Ext.getCmp("backHead").enable();
								Ext.getCmp("otherGroup").enable();
								Ext.getCmp("operateSelf").enable();
								return;
							}
							var panelparam = Ext.encode(getFormParam('knowPanel'));
							var resolve=Ext.encode(Ext.getCmp('Knowledge$resolvent').getValue());
							Ext.Ajax.request({
								url : webContext+ '/eventAction_saveEventSolutionForEngineer.action',
								params : {
									panelparam : panelparam,
									resolvent:resolve.substring(1,resolve.length-1),
									eventId : this.dataId,
									serviceItemId:si.getValue(),
									frequency:eventFrequency,
									ponderance:eventPonderance,
									desc : desc.substring(1,desc.length-1),
									problemSortId : pSort
								},
								success : function(response, options) {
									Ext.MessageBox.alert("��ʾ", "����ɹ���",function(){
									 	Ext.Ajax.request({
											url: webContext + '/extjs/workflow?method=getDataFromPage&taskId=' + taskId + '&next=touserconfirm',
											method:'post', 
											success:function(response,options){
											},
											failure : function(response,options) {
												Ext.MessageBox.alert("��ʾ", "����������ʧ�ܣ�");
											}
									 	});
									 	window.parent.auditContentWin.close();
									});
									
									
								},
								failure : function(response, options) {
									Ext.MessageBox.alert("��ʾ","����ʧ�ܣ�");
									Ext.getCmp("saveServiceType").enable();
									Ext.getCmp("backHead").enable();
									Ext.getCmp("otherGroup").enable();
									Ext.getCmp("operateSelf").enable();
								}
							});
	                	}else{
	                		Ext.Ajax.request({
							url : webContext+ '/eventAction_saveEventDescInDealing.action',
							params : {
								eventId : this.dataId,
								serviceItemId:si.getValue(),
								frequency:eventFrequency,
								ponderance:eventPonderance,
								desc : desc.substring(1,desc.length-1),
								problemSortId : pSort
							},
							success : function(response, options) {
								var result=Ext.decode(response.responseText);
								var service=si.getValue()
								 if(result.success==true){
	                   				this.operateSelf(this.dataId,service);          
					            }
							},
							scope : this,
							failure : function(response, options) {Ext.MessageBox.alert("��ʾ", "�����¼���Ϣʧ�ܣ�");}
						},this);
	                	}
	                },
	                scope : this,
	                 style : 'width:100;text-align:right;margin-left:0px'
	            }],
				autoScroll : true,
				frame : true,
				layoutConfig : {
					 columns:4
				  },
				items : kData
			});
		return this.knowPanel;
    },
    
    getFirstSearchForm : function() {
        var da = new DataAction();
        var data=da.getPanelElementsForEdit("page_event_suppGroupEngineer","panel_event_suppGroupEngineer",this.dataId);
        for(var  i=0;i<data.length;i++){
            if(data[i].name=="Event$submitDate"){
                data[i].value=data[i].value.substring(0,19);
            }
            if(data[i].name=="Event$dealuser"){
                data.remove(data[i]);
            }
            if(data[i].name=="Event$frequency"){
                data.remove(data[i]);
            }
            if(data[i].id=="Event$submitUserCombo"
            	||data[i].id=="Event$createUserCombo"
            		||data[i].id=="Event$ponderanceCombo"){
            	data[i].xtype="textfield";
            }
            if(data[i].xtype=="panel"){
                data[i].items[0].disabled=true;
				data[i].items[1].html=data[i].items[1].html.replace(/display:/g, "display:none");
            }
            data[i].readOnly=true;
            data[i].hideTrigger=true;
            data[i].emptyText="";
        }
	        Ext.getCmp('Event$submitUserCombo').setValue(submitUser);
	        Ext.getCmp('Event$createUserCombo').setValue(createUser);
        var biddata = this.split(data);
    
        this.panel = new Ext.form.FormPanel({  
            id : 'eventDetails',
            layout : 'table',
            xtype : 'fieldset',
            height : 300,   
            width : 770,
            frame : true,
            collapsible : true,
            defaults : {
                bodyStyle : 'padding:5px'
            },
            layoutConfig : {
                columns : 4
            },
            //title : "�¼���ϸ����",
            items :[{xtype : 'fieldset',   
                     width : 710,
                     height : 260,
                     title : "�¼�������Ϣ",
                     style :'margin:0 0 0 20,text-align:center',
                     defaults : {
                        bodyStyle : 'padding:5px'
                     },
                     layout : 'table',
                     layoutConfig : {
                        columns : 4
                    },
                   items : biddata
                 }]

            });
        return this.panel;

    },
    getSearchForpp: function() {
        this.panel = new Ext.form.FormPanel({ 
            layout : 'table',
            height : "auto",
            width : 770,
            frame : true,
            defaults : {
                bodyStyle : 'padding:4px'
            },
            layoutConfig : {
                columns : 2
            },
            items : [{xtype : 'fieldset',   
                     width : 710,
                     height : "auto",
                     title : "ȷ��������",
                      style :'margin:0 0 0 20,text-align:center',
                     defaults : {
                        bodyStyle : 'padding:4px'
                     },
                     layout : 'table',
                     layoutConfig : {
                        columns : 3
                    },
                   items : [{html: "&nbsp;",cls: 'common-text',style:'width:250;text-align:right'},{
                     xtype : "button",
                     text :'��������',
                     id : "firstSelfDeal",
                     style : 'margin:4px 10px 4px 0px',
                     iconCls : 'save',
                     handler : function() {
                       var firm = Ext.Msg.confirm('��ʾ', '��ȷ��Ҫ������¼���', function(
                            button) {
                         if (button == 'yes') {
                          Ext.Ajax.request({
                            url : webContext+'/eventAction_dealTheEvent.action',
                            params : {
                                    eventId : this.dataId
                                    },
                            method : 'post', 
                            success : function(response,options){
                                window.location.reload();
                            },
                            failure : function(response,options){
                                Ext.MessageBox.alert('��ʾ','�¼��������޸�ʧ�ܣ�');
                            }
                        });
                            }
                    }, this);
                    }, 
                    scope : this
                      }
                      ,   {
                         xtype : "button",
                         text : 'ָ��������',//ȷ��������¼�
                         iconCls : 'submit',
                         id :'firstOtherGroup',
                         handler : function(){this.getromote("first")},
                         scope : this
                      }
                   ]
                 }]
        
        });
        return this.panel;
        
    },
    operateSelf:function(eventId,serviceItemId){
            this.modifyWin = new Ext.Window({
                        title : '���������д���',
                        modal : true,
                        id : "modifyWin",
                        height : 400,
                        width : 700,
                        modal : true,
                        autoScroll : true, 
                        resizable : true,
                        viewConfig : {
                            autoFill : true,
                            forceFit : true
                        },
                        scope : this,
                        items : [{ height : 600,
					           	 autoLoad : {
									url : webContext + "/tabFrame.jsp?url=" + webContext
											+ "/user/event/transactionFlow/saveSolution.jsp?eventId="+eventId+"****taskId="+this.taskId+"****serviceItemId="+serviceItemId,
									text : "ҳ�����ڼ�����......",
									method : 'post',
									scope : this
								}
	                    	}]
                    });
            this.modifyWin.show();
            Ext.getCmp("saveServiceType").enable();
			Ext.getCmp("backHead").enable();
			Ext.getCmp("otherGroup").enable();
			Ext.getCmp("operateSelf").enable();
    },
    getKnowledge : function(serviceItemId){
        var eventId = this.dataId;
        var clazz = "com.digitalchina.itil.knowledge.entity.Knowledge";
        var da = new DataAction();
        var data1 = da.getElementsForQuery(clazz);
        var data = da.split(data1);
        var serviceItemBySu = new Ext.form.ComboBox({//��ѯ�����еķ�����
            name : "serviceItemBySu",
            id : 'serviceItemBySu',
            fieldLabel : "������",
            width : 200,
            hiddenName : 'serviceItem',
            displayField : 'name',
            valueField : 'id',
            lazyRender : true,
            minChars : 50,
            resizable : true,
            triggerAction : 'all',
            selectOnFocus : true,
            store : new Ext.data.JsonStore({
                url : webContext
                        + '/eventAction_findServiceItem.action',
                fields : ['id', 'name'],
                totalProperty : 'rowCount',
                root : 'data',
                id : 'id'
            }),
            pageSize : 10,
            initComponent : function() {
            	this.store.load({
						params : {
							id : Ext.getCmp('serviceItemBySu').getValue(),
							start : 0
						},
						callback : function(r, options, success) {
							Ext.getCmp('serviceItemBySu').setValue(Ext.getCmp('serviceItemBySu').getValue());
						}
					});
                    },
            listeners : {
                'beforequery' : function(queryEvent) {
                    Ext.getCmp('problemTypebySu').clearValue();
                    var param = queryEvent.combo.getRawValue();
                    var val = queryEvent.combo.getValue();
                    if (queryEvent.query == '') {
                        param = '';
                    }
                    this.store.baseParams={"name" : param,official:1};
                    this.store.load();
                    return false;
                },
                "select" :function(){
                 Ext.getCmp('problemTypebySu').clearValue();
                }
            }

        });
        var problemTypebySu = new Ext.form.ComboBox({
            name : "problemTypebySu",
            id : 'problemTypebySu',
            fieldLabel : "��������",
            width : 200,
            hiddenName : 'knowProblemType',
            displayField : 'name',
            valueField : 'id',
            lazyRender : true,
            minChars : 50,
            resizable : true,
            triggerAction : 'all',
            emptyText : '��ѡ��...',
            selectOnFocus : true,
            store : new Ext.data.JsonStore({
                url : webContext
                        + '/knowledgeAction_findKnowProblemType.action',
                fields : ['id', 'name'],
                totalProperty : 'rowCount',
                root : 'data',
                id : 'id'
            }),
            pageSize : 10,
            listeners : {
                'beforequery' : function(queryEvent) {
                	 if(Ext.getCmp('serviceItemBySu').getRawValue()==''){
				          Ext.getCmp('serviceItemBySu').clearValue();
				        }
                    var discValue = Ext.getCmp('serviceItemBySu').getValue();
                    var param = queryEvent.combo.getRawValue();
                    var val = queryEvent.combo.getValue();
                    if (queryEvent.query == '') {
                        param = '';
                    }
                    this.store.baseParams={"name" : param,serviceItem : discValue};
                    this.store.load();
                    return false;
                }
            }
        });
        var summary = new Ext.form.TextField({
            name : "summary",
            fieldLabel : "��������",
            id : 'summary',
            //allowBlank : false,
            width : 200
        });
        
        this.queryKnowLedgeType = new Ext.form.FormPanel({
            id :'queryKnowLedgeType',
            layout : 'table',
            height :88,
            width : 733,
            frame : true,
            collapsible : true,
            layoutConfig : {
                columns : 4
            },
            title : '�����������',
            items : [{
                html : "������:",
                cls : 'common-text',
                width : 115,
                style : 'width:150;text-align:right'
            }, serviceItemBySu, {
                html : "��������:",
                cls : 'common-text',
                width : 115,
                style : 'width:150;text-align:right'
            }, problemTypebySu, {
                html : "��������:",
                cls : 'common-text',
                width : 115,
                style : 'width:150;text-align:right'
            }, summary]
        });
        var DataHead=da.getListPanelElementsForHead("KnowLedgeSolutionList_pagepanel");
        var columns = new Array();
        var fields = new Array();
        for (var i = 0; i < DataHead.length; i++) {
            var headItem = DataHead[i];
            var title = headItem.header;
            var alignStyle = 'left';
            var propertyName = headItem.dataIndex;
            var isHiddenColumn = false;         
            var isHidden = headItem.isHidden;
             if(isHidden=='true'){
                    isHiddenColumn = true;  
            }   
            var columnItem = {
                header : title,
                dataIndex : propertyName,
                sortable : true,
                hidden : isHiddenColumn,
                align : alignStyle
            }
            //2010-04-24 add by huzh for �е��� begin
			if(propertyName=="Knowledge$summary"){
				columnItem.width=200;
			}
			if(propertyName=="Knowledge$serviceItem"){
				columnItem.width=180;
			}
			if(propertyName=="Knowledge$knowProblemType"){
				columnItem.width=180;
			}
			//2010-04-24 add by huzh for �е��� end
            columns[i] = columnItem;
            fields[i] = propertyName;
        }
        this.storeMapping = fields;
        this.cm = new Ext.grid.ColumnModel(columns);
        this.store= da.getPanelJsonStore("KnowLedgeSolutionList_pagepanel", DataHead);
        this.store.paramNames.sort = "orderBy";
        this.store.paramNames.dir = "orderType";
        this.cm.defaultSortable = true;
        var viewConfig = Ext.apply({
            forceFit : true
        }, this.gridViewConfig);
        this.pageBar = new Ext.PagingToolbar({
            id:"pageBar",
            pageSize : 10,
            store : this.store,
            displayInfo : true,
            displayMsg : '��ǰ��ʾ {0}-{1}����¼ /��{2}����¼',
            emptyMsg : "����ʾ����"
        });
        this.KnowLedgeListGrid = new Ext.grid.GridPanel({
            id : 'KnowLedgeListGrid',
            store : this.store,
            cm : this.cm,
            width : 733,
            height : 316,
            tbar : [{
                text : '��ѯ',
                handler : this.searchgride,
                scope : this,
                style : 'margin:4px 10px 4px 10px',
                iconCls : 'search'
                },'-',"��<font style='font-weight:lighter' color=red >˫����鿴��ϸ�������</font>��"
                ],
            bbar : this.pageBar,
            scope : this
        }); 
        
        var params = {
            'mehtodCall' : 'query',
            status : 1,
            start : 0,
            serviceItem : serviceItemId
        };
        this.store.on('beforeload', function(a) {   
					      Ext.apply(a.baseParams,params);   
			});
        this.store.removeAll();
        this.store.load({
            params : params
        });
        this.KnowLedgeListGrid.on("rowdblclick",this.dealKnow,this);
        this.PanelKnowledgeData = new Ext.Panel({
            id : "PanelKnowledgeData",
            align : 'center',
            autoScroll:true,
            defaults : {
                bodyStyle : 'padding:2px'
            },
            width : 750,
            height :420,
            frame : true,
            layoutConfig : {
                columns : 1
            },
            items : [this.queryKnowLedgeType,this.KnowLedgeListGrid]
        });
         return this.PanelKnowledgeData;
    
    },
    
    splitForReadOnly : function(data) {
		var labellen = 120; 
		var itemlen = 200;
		var throulen = 530;
		if (Ext.isIE) {
			throulen = 530;
		} else {
			throulen = 540;
		}
		if (data == null || data.length == 0) {
			return null;
		}
		var hid = 0;
		var hidd = new Array();
		var longData = new Array();
		var longitems = 0;
		for (i = 0; i < data.length; i++) {
			data[i].style = data[i].style == null ? "" : data[i].style;
			data[i].readOnly = true;
			if (data[i].xtype == "textarea") {
				data[i].style += "'margin:5 0 5 0;'";
			}
			if (data[i].xtype == "hidden") {
				hidd[hid] = data[i];
				hid++;
				continue;
			}
			if (data[i].xtype == 'combo' || data[i].xtype == 'datefield') {
				data[i].hideTrigger = true;
			}
			if (data[i].xtype == "panel") {
				var dd = Ext.encode(data[i]).replace(/display:/g,
						"display:none").replace("\"disabled\":false",
						"\"disabled\":true");
				data[i] = Ext.decode(dd);
			}
			if (data[i].xtype == "fckeditor") {
				data[i] = {
					id : data[i].id,
					name : data[i].name,
					html : data[i].value,
//					xtype : "fckeditor", //2010-04-20 modified by huzh
					autoScroll:true,
					frame :true,
					colspan : 3,
					fieldLabel : data[i].fieldLabel,
					cls : 'common-text',
					width: data[i].width,
					height : 300
				};
			}
			if (data[i].width == null || data[i].width == 'null'
					|| data[i].width == "") {
				data[i].style += "width:" + itemlen + "px";
				data[i].width = itemlen;
			} else {
				if (data[i].width == "9999") {// ͨ��
					if ((i - hid + longitems) % 2 == 1) {// ���Ҳ�����ǰһ����ͨ
						longData[2 * (i - hid) - 1].colspan = 3;
					} else {// ��ռһ��
						longitems++;
					}
					data[i].colspan = 3;// ������ͨ
					data[i].width = throulen;
					if (data[i].xtype == "textarea") {
						data[i].height = 150;
					}
					
					data[i].style += "width:" + throulen + "px;";
				} else {// �������ȣ�����
					data[i].style += "width:" + itemlen + "px";
					data[i].width = itemlen;
				}
			}
			longData[2 * (i - hid)] = {
				html : data[i].fieldLabel + ":",
				cls : 'common-text',
				style : 'width:' + labellen + ';text-align:right'
			};
			longData[2 * (i - hid) + 1] = data[i];
		}
		for (i = 0; i < hidd.length; i++) {
			longData[longData.length] = hidd[i];
		}
		return longData;
	},
	split : function(data) {
		var labellen = 120;
		var itemlen = 200;
		var throulen = 530;
		if (Ext.isIE) {
			throulen = 530;
		} else {
			throulen = 540;
		}
		if (data == null || data.length == 0) {
			return null;
		}
		var hid = 0;
		var hidd = new Array();
		var longData = new Array();
		var longitems = 0;
		for (i = 0; i < data.length; i++) {

			data[i].style = data[i].style == null ? "" : data[i].style;
			if (data[i].xtype == "textarea") {
				data[i].style += "'margin:0 0 0 0;'";
			}
			if (data[i].xtype == "hidden") {
				hidd[hid] = data[i];
				hid++;
				continue;
			}
			//add by lee for Ϊ�����б����ӿ���ק�������޸Ĳ��ܿ�ȫ��Ϣ��BUG in 20091112 BEGIN
			if (data[i].xtype == "combo") {
				data[i].resizable = true;
			}
			if (data[i].xtype == "fckeditor") {
				data[i] = {
					id: data[i].id,
					name : data[i].name,
					value : data[i].value,
					xtype : "fckeditor",
					colspan : 3,
					fieldLabel : data[i].fieldLabel,
					cls : 'common-text',
					width: data[i].width,
					height : 120
				};
			}
			//add by lee for Ϊ�����б����ӿ���ק�������޸Ĳ��ܿ�ȫ��Ϣ��BUG in 20091112 END
			if (data[i].width == null || data[i].width == 'null'
					|| data[i].width == "") {
				data[i].style += "width:" + itemlen + "px";
				data[i].width = itemlen;
			} else {
				if (data[i].width == "9999") {//ͨ��  
					if ((i - hid + longitems) % 2 == 1) {//���Ҳ�����ǰһ����ͨ                                             
						longData[2 * (i - hid) - 1].colspan = 3;
					} else {//��ռһ��
						longitems++;
					}
					data[i].colspan = 3;//������ͨ                                            
					data[i].width = throulen;
					if (data[i].xtype == "textarea") {
						data[i].height = 90;
						data[i].width = 523;
					}
					data[i].style += "width:" + throulen + "px;";
				} else {//�������ȣ����� 
					data[i].style += "width:" + itemlen + "px";
					data[i].width = itemlen;
				}
			}
			longData[2 * (i - hid)] = {
				html : data[i].fieldLabel + ":",
				cls : 'common-text',
				style : 'width:' + labellen + ';text-align:right'
			};
			longData[2 * (i - hid) + 1] = data[i];
		}
		for (i = 0; i < hidd.length; i++) {
			longData[longData.length] = hidd[i];
		}
		return longData;
	},
    // ��ʼ�� --- begin
    initComponent : function() {
    	this.getSupportAndEngineerComs();
    	Ext.getCmp('supportGroup').setValue("");
		Ext.getCmp('supportGroupEngineer').setValue("");
        var serviceItemId = "";
        var conn = Ext.lib.Ajax.getConnectionObject().conn;
        var url = webContext
                + '/eventAction_getEventServiceTypeAndItemByEventId.action?eventId='
                + this.dataId;
        conn.open("post", url, false);
        conn.send(null);
        if (conn.status == "200") {
            var responseText = conn.responseText;
            var result = Ext.decode(responseText);
            serviceItemId =result.serviceItemId;
        }
    	if(dealuser==''){//�¼��޴�����
		    	this.firstFp = this.getFirstSearchForm();//�¼�������Ϣ
		        this.fpp=this.getSearchForpp();//ȷ��������
	            this.Panel = new Ext.Panel({
		            id : "Pages",
		            height : 'auto',
		            align : 'center',
		            layout : 'table',
		            border : true,
		            defaults : {
		                bodyStyle : 'padding:2px'
		            },
		            width : 'auto',
		            height : 460,
		            frame : true,
		            autoScroll:true,
		            layoutConfig : {
		                columns : 1
		            },
		            items :[this.firstFp,this.fpp]     
	        	}); 
	       		this.add(this.Panel);
		}else if(dealuser!=''){//�¼����ڴ�����
				this.fp=this.getSearchForm();//�¼���ϸ����
				this.knowFormPanel=this.getKnowFrom();
		        this.Knowledge= this.getKnowledge(serviceItemId);//�����������
		        this.EventConfirmPanel = new Ext.Panel({
		            title :'�¼�ȷ��ҳ��',
		            id : "confirmPanel",
		            height : 940,
		            layout : 'table',
		            border : true,
		            width : 800,
		            frame : true,
		            layoutConfig : {
		                columns : 1
		            },
		             items : [this.fp,this.knowFormPanel,this.Knowledge]
		        }); 
		        
		        this.tab  = new Ext.TabPanel({  
			            deferredRender : false,
			            activeTab : 0,
			            frame : true,
			            border : true,
			            width : 765,
			            height :'auto',
			            bodyBorder : true,
			            items : [this.EventConfirmPanel,
			            		{title: '�¼�����ҳ��',
			            		height: 450,
					             autoLoad : {
									url : webContext + "/tabFrame.jsp?url=" + webContext
											+ "/user/event/transactionFlow/engineerDealEvent/eventDeal.jsp?dataId="+this.dataId+"****taskId="+this.taskId,
									text : "ҳ�����ڼ�����......",
									method : 'post',
									scope : this
									}
					            },
			            		{title: '�¼�����״̬',
					             height: 900,
					             autoLoad : {
									url : webContext + "/tabFrame.jsp?url=" + webContext
											+ "/user/event/transactionFlow/engineerDealEvent/eventAuditHis.jsp?dataId="+this.dataId+"****taskId="+this.taskId,
									text : "ҳ�����ڼ�����......",
									method : 'post',
									scope : this
									}
					            }
			            		]
		        });
		        var knowledgeId;
	            var conn = Ext.lib.Ajax.getConnectionObject().conn;
	            var url = webContext+'/knowledgeAction_findKnowledgeByEventId.action?eventId='+this.dataId;
	            conn.open("post", url, false);
	            conn.send(null);
	            if (conn.status == "200") {
	                var responseText = conn.responseText;
	                var data = Ext.decode(responseText);
	                knowledgeId = data.knowledgeId;
	            }
	            if(knowledgeId != 'noknowledge'){
	            	var hisPanel=
	            	{  title:"��ʷ�������",
	            		height : 425,
			           	 autoLoad : {
							url : webContext + "/tabFrame.jsp?url=" + webContext
									+ "/user/event/transactionFlow/eventHistoryKnowledge.jsp?knowledgeId="+knowledgeId,
							text : "ҳ�����ڼ�����......",
							method : 'post',
							scope : this
                		}
	          	  }
	          	  this.tab.add(hisPanel);
	            }
	         	 this.add(this.tab);
	         	 if(serviceItemId!=null&&serviceItemId!=""){
		         	 Ext.getCmp("childsort").setValue(serviceItemId);
	      			 Ext.getCmp("childsort").initComponent();
	      			 Ext.getCmp("serviceItemBySu").setValue(serviceItemId);
	     		     Ext.getCmp("serviceItemBySu").initComponent();
	         	 }
         }
        
    },
    fitWidth : function(grid, columnIndex, e) {
        var c = columnIndex;
        var w = grid.view.getHeaderCell(c).firstChild.scrollWidth;
        for (var i = 0, i = grid.store.getCount(); i < l; i++) {
            w = Math.max(w, grid.view.getCell(i, c).firstChild.scrollWidth);
        }
        grid.colModel.setColumnWidth(c, w);
    }

});
