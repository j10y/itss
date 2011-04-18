PagePanel = Ext.extend(Ext.Panel, {
    id : "problemPanel",
    closable : true,
    autoScroll:true,
    height : 492,//要与portal中的高度一样
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
            Ext.Msg.alert("提示","请从下拉类表中选择服务项！");
            return;
        }
        if(Ext.getCmp("Event$description8").getValue().trim()==''){
            Ext.Msg.alert("提示","事件描述不能为空！");
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
        var desc=Ext.encode(Ext.getCmp("Event$description8").getValue());//事件描述
         Ext.Ajax.request({
                  url :webContext + '/eventAction_modifyEvent.action',
                  params: { 
                             eventId : this.dataId,
                             serviceItemId : serviceItemId,
                             eventFrequency : eventFrequency,
                             eventPonderance : eventPonderance,
                             desc : desc.substring(1,desc.length-1),//保存事件描述
                             problemSortId : pSort
                           },
                  method : 'post', 
                  success : function(){
                      Ext.MessageBox.alert("提示","保存事件成功！",function(){
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
                    Ext.MessageBox.alert("提示","保存失败！");
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
				title : '查看详细解决方法',
				maximizable : true,
				autoScroll : true,
				width : 600,
				height :400,
				modal : true,
				items : [{html : konwledgecontext}],
                buttons : [{
                text : '使用',
                id : 'useKnowButton',
                	handler : function(){
				   	if(Ext.getCmp("childsort").getValue()==""){
				   		Ext.MessageBox.alert("提示","请从下拉列表中选择服务项！");
				   		return;
				   	}
				   	if(Ext.getCmp("Event$description8").getValue().trim()==''){
				   		Ext.MessageBox.alert("提示","事件描述不能为空！");
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
                    var desc=Ext.encode(Ext.getCmp("Event$description8").getValue());//事件描述
                     var siId=Ext.getCmp("childsort").getValue();
                       Ext.Ajax.request({
                          url :webContext + '/eventAction_endEventWithValidKnowledge.action',
                          params: { 
                              eventId : this.dataId,
                              knowledgeId : knowledgeId,
                              serviceItemId : siId,
                              desc : desc.substring(1,desc.length-1),//保存事件描述
                              problemSortId : pSort
                            },
                          method:'post', 
                          success:function(){
                                Ext.Ajax.request({
                                url: webContext + '/extjs/workflow?method=getDataFromPage&taskId=' + taskId + '&next=touserconfirm',//'&users=userConfirm:' + eventSubmitter,
                                method:'post', 
                                success:function(response,options){
                                Ext.MessageBox.alert('提示','成功使用该解决方案解决一个事件！',function(){//更改成原来的提示 2009-8-23 16:17:33 by wanghao
                                        windowSkip.close();
                                    window.parent.auditContentWin.close();
                                    });
                                    
                                },
                                failure:function(response,options){
                                    Ext.MessageBox.alert('提示','提交用户确认失败！');
                                    Ext.getCmp("useKnowButton").enable();
			   					    Ext.getCmp("closeButton").enable();
                                }
                        });
                        
                          },
                          failure:function(response,options){
                            Ext.MessageBox.alert("提示","使用数据失败！");
                            Ext.getCmp("useKnowButton").enable();
	   					    Ext.getCmp("closeButton").enable();
                          }
                      });
                },
                scope : this
             
             },{
                    text : '关闭',
                    id : 'closeButton',
                    handler : function() {
                            windowSkip.close(); 
                    },
                    scope : this
                }]
              });
            windowSkip.show();
      },
    //跳转到问题处理页面
//    deal:function(){
//              var record = this.grid.getSelectionModel().getSelected();
//              var records = this.grid.getSelectionModel().getSelections();
//                if(!record){
//                    Ext.Msg.alert("提示","请先选择要修改的行！");
//                    return;
//                }
//                if(records.length>1){
//                    Ext.Msg.alert("提示","修改时只能选择一行！");
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
   //指派其他组
    getSupportAndEngineerComs: function(){
    	this.asistType = new Ext.form.ComboBox({
	        id : 'supportGroup',
	        mode : 'remote',
	        displayField : 'groupName',
	        valueField : 'id',
	        lazyRender : true,
	        allowBlank : false,
	        hiddenName : 'EventAssign$supportGroup',
	        emptyText : '请从下拉列表中选择...',
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
        emptyText : '请从下拉列表中选择...',
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
            title:"指派支持组",//选择返回组名称
            items:[{html: "指派支持组:&nbsp;",cls: 'common-text',style:'width:75;text-align:right'},
                    this.asistType,
                    {html: "支持工程师:&nbsp;",cls: 'common-text',style:'width:85;text-align:right'},
                    this.asistName]
             },{xtype : 'fieldset', 
                    id :'reasion',
                    layout : 'table',
                    layoutConfig : {
                      columns : 2
                    },
            title : "指派原因及问题描述",//添写返回其它组的理由
            items : [ new Ext.form.TextArea({
					xtype : 'textarea',
					id : 'EventAssign$remark',
					colspan : 3,
					name : 'EventAssign$remark',
					width : 570,
					height : 70,
					allowBlank : false,
					emptyText :"请在此填写你在处理该事件过程中的发现和分析，以帮助后边的工程快速、准确的解决用户的问题，谢谢！",
					fieldLabel : '备注'
				})]
             }
             ] 

        });
        var otherFlag=flag;
        var win = new Ext.Window({
                title : '其他组处理',
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
                buttons : [{//点击按钮时，首先从面板中得到所有数据，在提交给相应Action中的方法
                    text : '确定',
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
//		                		 Ext.MessageBox.alert("提示","请从下拉类表中选择服务项！");
//		                		 return;
//		                	}
		                	var description=Ext.getCmp("Event$description8").getValue();
						   	if(description.trim()==''){
						   		Ext.MessageBox.alert("提示","事件描述不能为空！");
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
                                        Ext.MessageBox.alert("提示","您选择的支持组没有处理人，请选择其他支持组！",function(){
                                            Ext.getCmp("submitbutton").enable();
                                        });
                                    }else{
                                        //同步得到当前节点名称
                                        var currentNodeName;
                                        var conn = Ext.lib.Ajax.getConnectionObject().conn;
                                        conn.open("POST", webContext+'/eventAction_findCurrentNodeName.action?taskId='+this.taskId,false);
                                        conn.send(null);    
                                        if(conn.status=='200'){
                                            var result = Ext.decode(conn.responseText);
                                            currentNodeName = result.currentNodeName;
                                        }
                                        var users = supportGroupEngineerId;
                                        //--------------------------------------启动工作流----------------------------------------------
                                        if(currentNodeName!='otherOrgProcess'){//下个节点问题
                                            Ext.Ajax.request({
                                                    url: webContext + '/extjs/workflow?method=getDataFromPage&taskId=' + this.taskId + '&next=otherOrgProcess'+ '&users=otherOrgProcess:' + users,
                                                    method:'post', 
                                                    success:function(response,options){
                                                        Ext.MessageBox.alert('提示','返回其他组成功！',function(){
                                                            win.close()
                                                            window.parent.auditContentWin.close();
                                                        });
                                                    },
                                                    failure:function(response,options){
                                                        Ext.MessageBox.alert('提示','返回其他组失败！',function(){
                                                            Ext.getCmp("submitbutton").enable();
                                                        });
                                                    }
                                            });
                                        }else{//当前节点问题
                                            Ext.Ajax.request({
                                            	//2010-06-18 modified by huzh for 当前节点为其他组处理时跳到工程师处理节点 begin
                                            	url: webContext + '/extjs/workflow?method=getDataFromPage&taskId=' + this.taskId + '&next=return'+ '&users=engineerProcess:' + users,
                                              //url: webContext + '/extjs/workflow?method=reAssignToNode&taskId=' + this.taskId + '&next=otherOrgProcess' + '&users=otherOrgProcess:' + users,
                                                //2010-06-18 modified by huzh for 当前节点为其他组处理时跳到工程师处理节点 end    
                                                    method:'post', 
                                                    success:function(response,options){
                                                        Ext.MessageBox.alert('提示','返回其他组成功！',function(){
                                                            win.close()
                                                            window.parent.auditContentWin.close();
                                                        });
                                                    },
                                                    failure:function(response,options){
                                                        Ext.MessageBox.alert('提示','返回其他组失败！',function(){
                                                            Ext.getCmp("submitbutton").enable();
                                                        });
                                                    }
                                            });
                                        }
                                     }
                                },
                                failure:function(response,options){
                                    Ext.MessageBox.alert("提示","保存数据失败！",function(){
                                        Ext.getCmp("submitbutton").enable();
                                    });
                                },
                                scope : this
                            });
                        }else{
                            Ext.MessageBox.alert("提示","请填写备注信息！",function(){
                                Ext.getCmp("submitbutton").enable();
                            });
                        }
                        }else{
                             Ext.MessageBox.alert("提示","显示为红色波浪线的项为必填项！",function(){
                                Ext.getCmp("submitbutton").enable();
                             });
                        }
                    },
                    scope : this

                }, {
                    text : '关闭',
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
   //返回本组组长
   getlocaler : function(){
   	    var si=Ext.getCmp("childsort");
   	    if(si.getRawValue()==""||si.getRawValue()==null){
   	    	si.clearValue();
   	    }
	   	if(si.getValue()==""){
	   		Ext.MessageBox.alert("提示","请从下拉列表中选择服务项！");
	   		return;
	   	}
	   	if(Ext.getCmp("Event$description8").getValue().trim()==''){
	   		Ext.MessageBox.alert("提示","事件描述不能为空！");
	   		return;
	   	}
        var da = new DataAction();
        var data=da.getPanelElementsForAdd("serviceEngineeraddMes_pagepanel");
        var dataform =data;// this.splitOwn(data);
        
        dataform[1].emptyText="请在此填写你在处理该事件过程中的发现和分析，以帮助后边的工程快速、准确的解决用户的问题，谢谢！";
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
                title : '提交本组组长处理',
                width : 555,
                height : 200,
                modal : true,
               // maximizable : true,
                items : envForm,    
                closeAction : 'hide',
                bodyStyle : 'padding:1px',
                buttons : [{//点击按钮时，首先从面板中得到所有数据，在提交给相应Action中的方法
                    text : '保存',
                    id  :'savebutton',
                    handler : function() {   
                        if (Ext.encode(Ext.getCmp("EventAssign$remark").getValue()).length==2) {
                            Ext.MessageBox.alert("提示","请填写备注信息！");
							return;
                         }
                          if (Ext.getCmp("Event$description8").getValue().trim()=="") {
                            Ext.MessageBox.alert("提示","事件描述不能为空！");
							return;
                         }
                         Ext.getCmp("savebutton").disable();
                        Ext.getCmp("saveServiceType").disable();
						Ext.getCmp("backHead").disable();
						Ext.getCmp("otherGroup").disable();
						Ext.getCmp("operateSelf").disable();
						var desc=Ext.encode(Ext.getCmp("Event$description8").getValue());//事件描述
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
                                      	 desc : desc.substring(1,desc.length-1),//保存事件描述
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
                                        Ext.MessageBox.alert('提示','对不起，没有组长！');
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
                                            Ext.MessageBox.alert('提示','返到本组组长成功！',function(){
                                                window.parent.auditContentWin.close();
                                            });
                                        },
                                        failure:function(response,options){
                                            Ext.MessageBox.alert('提示','返到本组组长失败！',function(){
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
                                    Ext.MessageBox.alert("提示","保存数据失败！",function(){
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
                    text : '关闭',
                    handler : function() {
                        win.hide();
                    },
                    scope : this
                }]
            });
        win.show();
    
    },
    //删除问题
//    remove : function() {
//        var records = this.grid.getSelectionModel().getSelections();
//        if (records.length==0) {
//            Ext.Msg.alert("提示", "请先选择要删除的记录！");
//            return;
//        }
//        Ext.Msg.confirm('提示', '您确定要进行删除操作吗？', function(button) {
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
//                                Ext.Msg.alert("提示","删除成功！",function(){
//                                    Ext.getCmp("problemgrid").getStore().reload();
//                                });
//                            }
//                            else 
//                               Ext.Msg.alert("提示","编号为:"+response.responseText+"的问题已关联配置项，不允许删除！");
//                       },scope:this
//                        
//                    });
//                }
//        }, this);
//
//    },

    // 创建问题
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
//                title : '添加问题',
//                width : 598,
//                height : 323,
//                modal : true,
//                //maximizable : true,
//                autoScroll : true,
//                items : [envForm],
//                closeAction : 'hide',
//                bodyStyle : 'padding:2px',
//                buttons : [{
//                    text : '保存',
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
//                           Ext.MessageBox.alert("提示","问题保存成功！",function(){
//                                
//                                Ext.getCmp('problemgrid').store.reload();
//                               win.close()
//                           });
//                               
//                          },
//                           failure:function(response,options){
//                             Ext.MessageBox.alert("提示","保存数据失败！",function(){ Ext.getCmp("submitBookInfo").enable();});
//                          }
//                      });
//                      }else{
//                          Ext.MessageBox.alert("提示","请填写相关信息！",function(){
//                           Ext.getCmp("submitBookInfo").enable();
//                          });
//                      }
//                    },
//                    scope : this
//                },{
//                    text : '关闭',
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
   
   getSearchForm : function() {//事件详细资料
        var childsort = new Ext.form.ComboBox({
            name : "childsort",
            id : 'childsort',
            fieldLabel : "服务项",
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
            emptyText : '请从下拉类表中选择...',
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
        //2010-06-23 add by huzh for 添加事件提交人电话 begin
       var submitUserPhone= new Ext.form.TextField({
					fieldLabel : '提出人电话',
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
				fieldLabel:"事件描述"
			});
		 //2010-06-23 add by huzh for 添加事件提交人电话 end	
		var problemSort=new Ext.form.ComboBox({
				name : "Event$problemSort",
				id : 'Event$problemSortCombo',
				fieldLabel : "类型",
				width : 200,
				displayField : 'name', 
				valueField : 'id',
				resizable : true,
				emptyText :'仅供7888-2线使用',
				triggerAction : 'all',
				allowBlank : true,//允许为空
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
            	data[i].emptyText='请从下拉列表中选择...';
                Ext.getCmp('Event$frequencyCombo').setValue(frequencyName);
                data[i].on('select',function(combo,record,index){frequency=combo.value});
            }else if(data[i].name=="Event$ponderance"){
            	data[i].emptyText='请从下拉列表中选择...';
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
              //2010-06-23 modified by huzh for 添加事件提交人电话 begin
             if(data[i].id=="Event$submitUserCombo"){
            	 data[i].xtype="textfield";
        	  	newdata.push(Ext.getCmp("sUserPhone"));
             }	
              //2010-06-23 modified by huzh for 添加事件提交人电话 end
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
            title : "事件详细资料",
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
				fieldLabel:"是否提交知识",
				boxLabel:"是",
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
					html : '问题类型:',
					cls : 'common-text',
					style : 'width:120;text-align:right'
				}, new Ext.form.ComboBox({
					xtype : 'combo',
					hiddenName : 'Knowledge$knowProblemType',
					id : 'Knowledge$knowProblemTypeCombo',
					width : 200,
					fieldLabel : '问题类型',
					lazyRender : true,
					displayField : 'name',
					valueField : 'id',
					forceSelection :true,
					emptyText : '请从下拉列表中选择...',
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
								Ext.MessageBox.alert("提示","请先从下拉列表中选择服务项！");
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
					html : '具体问题:',
					cls : 'common-text',
					style : 'width:130;text-align:right'
				}, new Ext.form.TextField({
					fieldLabel : '具体问题',
					xtype : 'textfield',
					id : 'Knowledge$summary',
					name : 'Knowledge$summary',
					width : 200,
					allowBlank : true
				}),{
					html : '解决方法:',
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
					html : "处理方式:",
					cls : 'common-text',
					style : 'width:120;text-align:right;'
				}, new Ext.form.ComboBox({
					xtype : 'combo',
					name : 'Event$eventDealType',
					hiddenName : 'Event$eventDealType',
					id : 'Event$eventDealTypeCombo',
					width : 200,
					fieldLabel : '事件处理方式',
					lazyRender : true,
					displayField : 'name',
					forceSelection :true,
					valueField : 'id',
					emptyText : '请从下拉列表中选择...',
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
					html : "是否提交知识:",
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
	                text : '保存',
	                id:'saveServiceType',
	                handler : this.modifyEventServiceTypeAndItem,
	                iconCls : 'save',
	                scope : this,
	                style : 'width:100;text-align:right;margin-left:20px'
	            },         
	             {
	                xtype : 'button',
	                text : '返到本组组长',
	                id:'backHead',
	                handler : this.getlocaler,
	                iconCls : 'back',
	                scope : this,
	                style : 'width:100;text-align:right;margin-left:0px'
	            },{ 
	                xtype :'button',
	                text : '指派其它组',
	                iconCls : 'back',
	                id : "otherGroup", 
	                handler : function(){
	               		 this.getromote("second")
	                
	                },
	                scope : this,
	                style : 'width:100;text-align:right;margin-left:0px'
	            },{
	                xtype :'button',
	                text : '解决并提交用户',
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
	                		 Ext.MessageBox.alert("提示","请从下拉类表中选择服务项！");
	                		 return;
	                	}
	                	//2010-07-07 add by huzh for 保存事件描述 begin
	                	var description=Ext.getCmp("Event$description8").getValue();
					   	if(description.trim()==''){
					   		Ext.MessageBox.alert("提示","事件描述不能为空！");
					   		return;
					   	}
					   	var desc=Ext.encode(description);
						//2010-07-07 add by huzh for 保存事件描述 end
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
								Ext.MessageBox.alert("提示", "请填写问题类型、问题名称及解决方法！");
								Ext.getCmp("saveServiceType").enable();
								Ext.getCmp("backHead").enable();
								Ext.getCmp("otherGroup").enable();
								Ext.getCmp("operateSelf").enable();
								return;
							}
							if(Ext.getCmp('Event$eventDealTypeCombo').getValue()==""){
								Ext.MessageBox.alert("提示", "请从下拉列表中选择事件处理方式！");
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
									Ext.MessageBox.alert("提示", "保存成功！",function(){
									 	Ext.Ajax.request({
											url: webContext + '/extjs/workflow?method=getDataFromPage&taskId=' + taskId + '&next=touserconfirm',
											method:'post', 
											success:function(response,options){
											},
											failure : function(response,options) {
												Ext.MessageBox.alert("提示", "启动工作流失败！");
											}
									 	});
									 	window.parent.auditContentWin.close();
									});
									
									
								},
								failure : function(response, options) {
									Ext.MessageBox.alert("提示","保存失败！");
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
							failure : function(response, options) {Ext.MessageBox.alert("提示", "保存事件信息失败！");}
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
            //title : "事件详细资料",
            items :[{xtype : 'fieldset',   
                     width : 710,
                     height : 260,
                     title : "事件基本信息",
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
                     title : "确定处理人",
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
                     text :'我来处理',
                     id : "firstSelfDeal",
                     style : 'margin:4px 10px 4px 0px',
                     iconCls : 'save',
                     handler : function() {
                       var firm = Ext.Msg.confirm('提示', '您确定要处理此事件吗？', function(
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
                                Ext.MessageBox.alert('提示','事件处理人修改失败！');
                            }
                        });
                            }
                    }, this);
                    }, 
                    scope : this
                      }
                      ,   {
                         xtype : "button",
                         text : '指派其他组',//确定处理此事件
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
                        title : '解决方案填写面板',
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
									text : "页面正在加载中......",
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
        var serviceItemBySu = new Ext.form.ComboBox({//查询条件中的服务项
            name : "serviceItemBySu",
            id : 'serviceItemBySu',
            fieldLabel : "服务项",
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
            fieldLabel : "问题类型",
            width : 200,
            hiddenName : 'knowProblemType',
            displayField : 'name',
            valueField : 'id',
            lazyRender : true,
            minChars : 50,
            resizable : true,
            triggerAction : 'all',
            emptyText : '请选择...',
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
            fieldLabel : "具体问题",
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
            title : '检索解决方案',
            items : [{
                html : "服务项:",
                cls : 'common-text',
                width : 115,
                style : 'width:150;text-align:right'
            }, serviceItemBySu, {
                html : "问题类型:",
                cls : 'common-text',
                width : 115,
                style : 'width:150;text-align:right'
            }, problemTypebySu, {
                html : "具体问题:",
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
            //2010-04-24 add by huzh for 列调整 begin
			if(propertyName=="Knowledge$summary"){
				columnItem.width=200;
			}
			if(propertyName=="Knowledge$serviceItem"){
				columnItem.width=180;
			}
			if(propertyName=="Knowledge$knowProblemType"){
				columnItem.width=180;
			}
			//2010-04-24 add by huzh for 列调整 end
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
            displayMsg : '当前显示 {0}-{1}条记录 /共{2}条记录',
            emptyMsg : "无显示数据"
        });
        this.KnowLedgeListGrid = new Ext.grid.GridPanel({
            id : 'KnowLedgeListGrid',
            store : this.store,
            cm : this.cm,
            width : 733,
            height : 316,
            tbar : [{
                text : '查询',
                handler : this.searchgride,
                scope : this,
                style : 'margin:4px 10px 4px 10px',
                iconCls : 'search'
                },'-',"【<font style='font-weight:lighter' color=red >双击后查看详细解决方法</font>】"
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
				if (data[i].width == "9999") {// 通栏
					if ((i - hid + longitems) % 2 == 1) {// 在右侧栏，前一栏贯通
						longData[2 * (i - hid) - 1].colspan = 3;
					} else {// 多占一栏
						longitems++;
					}
					data[i].colspan = 3;// 本栏贯通
					data[i].width = throulen;
					if (data[i].xtype == "textarea") {
						data[i].height = 150;
					}
					
					data[i].style += "width:" + throulen + "px;";
				} else {// 正常长度，半栏
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
			//add by lee for 为下拉列表增加可拖拽属性以修改不能看全信息的BUG in 20091112 BEGIN
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
			//add by lee for 为下拉列表增加可拖拽属性以修改不能看全信息的BUG in 20091112 END
			if (data[i].width == null || data[i].width == 'null'
					|| data[i].width == "") {
				data[i].style += "width:" + itemlen + "px";
				data[i].width = itemlen;
			} else {
				if (data[i].width == "9999") {//通栏  
					if ((i - hid + longitems) % 2 == 1) {//在右侧栏，前一栏贯通                                             
						longData[2 * (i - hid) - 1].colspan = 3;
					} else {//多占一栏
						longitems++;
					}
					data[i].colspan = 3;//本栏贯通                                            
					data[i].width = throulen;
					if (data[i].xtype == "textarea") {
						data[i].height = 90;
						data[i].width = 523;
					}
					data[i].style += "width:" + throulen + "px;";
				} else {//正常长度，半栏 
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
    // 初始化 --- begin
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
    	if(dealuser==''){//事件无处理人
		    	this.firstFp = this.getFirstSearchForm();//事件基本信息
		        this.fpp=this.getSearchForpp();//确定处理人
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
		}else if(dealuser!=''){//事件存在处理人
				this.fp=this.getSearchForm();//事件详细资料
				this.knowFormPanel=this.getKnowFrom();
		        this.Knowledge= this.getKnowledge(serviceItemId);//检索解决方案
		        this.EventConfirmPanel = new Ext.Panel({
		            title :'事件确认页面',
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
			            		{title: '事件处理页面',
			            		height: 450,
					             autoLoad : {
									url : webContext + "/tabFrame.jsp?url=" + webContext
											+ "/user/event/transactionFlow/engineerDealEvent/eventDeal.jsp?dataId="+this.dataId+"****taskId="+this.taskId,
									text : "页面正在加载中......",
									method : 'post',
									scope : this
									}
					            },
			            		{title: '事件审批状态',
					             height: 900,
					             autoLoad : {
									url : webContext + "/tabFrame.jsp?url=" + webContext
											+ "/user/event/transactionFlow/engineerDealEvent/eventAuditHis.jsp?dataId="+this.dataId+"****taskId="+this.taskId,
									text : "页面正在加载中......",
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
	            	{  title:"历史解决方案",
	            		height : 425,
			           	 autoLoad : {
							url : webContext + "/tabFrame.jsp?url=" + webContext
									+ "/user/event/transactionFlow/eventHistoryKnowledge.jsp?knowledgeId="+knowledgeId,
							text : "页面正在加载中......",
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
