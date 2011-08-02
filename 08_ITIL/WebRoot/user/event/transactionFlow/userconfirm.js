PageTemplates = Ext.extend(Ext.Panel, {
	id : "PageTemplates",
	layout : 'table',
	height : 'auto',
	align : 'center',
	foredit : true,
	width : 1050,
	frame : true,
	border:true,
	style :"margin 0px",//2010-04-30 modified by huzh for ҳ���Ż�
	autoScroll : true,
	defaults : {
		bodyStyle : 'padding:4px'
	},
	layoutConfig : {
		columns : 1
	},
	items : this.items,
	eventAssignRemark : function(){ 
		var record = this.earp.getSelectionModel().getSelected();
	    var records = this.earp.getSelectionModel().getSelections();
		if(!record){
				Ext.Msg.alert("��ʾ","����ѡ��Ҫ�޸ĵ��У�");
				return;
			}
		if(records.length>1){
				Ext.Msg.alert("��ʾ","�޸�ʱֻ��ѡ��һ�У�");
				return;
			}
		var eventAssignId = record.get("EventAssign$id");
		var remark = record.get("EventAssign$remark");
		if(remark.length==0){
			remark = '<font color="red">û����д��ע��Ϣ��</font>';
		}
		remark = remark.replace(/\\r\\n/g, '<br>');
		var win = new Ext.Window({
			title : '��ע��Ϣ��ʾ',
			width : 300,
			height : 200,
			html : remark
		});
		win.show();
	},
	getEventSubmiter : function(eventId){
		var eventSubmitter;
		var conn = Ext.lib.Ajax.getConnectionObject().conn;
		var url = webContext+'/eventAction_findEventSubmitUser.action?eventId='+eventId;
		conn.open("GET", url, false);
		conn.send(null);
		if (conn.status == "200") {
			var responseText = conn.responseText;
			var data = Ext.decode(responseText);
			eventSubmitter = data.eventSubmitUser;
		} 
		return eventSubmitter;
	},
	getEventDealUser : function(eventId){
		var eventDealUser;
		var conn = Ext.lib.Ajax.getConnectionObject().conn;
		var url = webContext+'/eventAction_findDealUserOfEvent.action?eventId='+eventId;
		conn.open("GET", url, false);
		conn.send(null);
		var eventCreater;	
		if (conn.status == "200") {
			var responseText = conn.responseText;
			var data = Ext.decode(responseText);
			eventDealUser = data.dealUser;
		} 
		return eventDealUser;
	},
	getTabpanel : function(tab, eventId, taskId, surveyId){
		this.tabPanel = new Ext.TabPanel({           
			xtype : 'tabpanel',
			activeTab : 0,
            enableTabScroll:true, 
			deferredRender : false,
			frame : true,
			plain : true,
            border : true, 
			baseCls : 'x-plain',// �Ƿ����úͱ���ɫͬ��
			width : 750,
			height : "auto", //2010-04-30 modified by huzh for ҳ���Ż� begin
			defaults : {
				autoHeight : true,
				bodyStyle : 'padding:2px'
			},
			items :tab,
  			buttonAlign : 'center',
  			scope : this
		});
		return this.tabPanel;

	},
	getPanel : function(appa,panelTitle) {
		this.Panel = new Ext.Panel({
			id : "Pages",
			height : 'auto',
			align : 'center',
			title:panelTitle,
			border : false,
            defaults : {
                 bodyStyle : 'padding:5px'
            },
			width :'auto',
			frame : true,
			autoScroll : true,
			layoutConfig : {
				columns : 1
			},
			items : appa
		});
		return this.Panel;

	}, 
	//�û�ȷ��--�¼�����
 	getFormUserEventMessage_pagepanel: function(eventId,surveyId,taskId) {
            var buttonConfim=new Ext.Button({
  				id:'btn_confirm',
  				xtype : 'button',
  				text : 'ȷ���ѽ��',
  				iconCls : 'submit',
  				handler : function(){
  					var endEvent=function(){
  						Ext.Ajax.request({
  							url : webContext + '/configWorkflow_findProcessByPram.action',
  							params : {
  								modleType : 'Kno_Solution',//
  								processStatusType : '0'//
  							},
  							success : function(response, options) {
  								var responseArray = Ext.util.JSON
  										.decode(response.responseText);
  								var vpid = responseArray.vpid;
  								if(vpid!=""&&vpid!=undefined&&vpid.length>0){
  									Ext.Ajax.request({
										url : webContext+ '/knowledgeAction_prepareForKnowledgeAuditWorkflow.action',
										params : {
											eventId : eventId
										},
										success : function(response, options) {
											var dataId = eval('('+ response.responseText + ')').knowledgeId;
											var dataType = eval('('+ response.responseText + ')').knowledgeTypeId;
											var createUser = eval('('+ response.responseText + ')').createUser;
											if(dataId!=undefined){
												Ext.Ajax.request({
														url : webContext
																+ '/knowledgeWorkflow_apply.action',
														params : {
															createUser :createUser,
															dataId : dataId,
															model : this.model,
															bzparam : "{dataId :'"
																	+ dataId
																	+ "',dataType : '"
																	+ dataType
																	+ "',applyId : '"
																	+ dataId
																	+ "', applyType: 'kproject',applyTypeName: '֪ʶ����',customer:''}",
															defname : vpid
														},
														success:function(response,options){
														},
														failure:function(response,options){
														}
												}, this);
											}
										}
								}, this);
  									//
				                    //--------------------------------------����������----------------------------------------------
  									Ext.Ajax.request({
											url: webContext + '/extjs/workflow?method=getDataFromPage&taskId=' + taskId + '&next=over',
											method:'post', 
											success:function(response,options){
												var meg = Ext.decode(response.responseText);
												if (meg.Exception == undefined) {
													Ext.Msg.alert("��ʾ", "�¼������ɹ���", function() {
														window.parent.auditContentWin.close();
													});
												} else {
													Ext.Msg.alert("��ʾ", "��������������ʧ�ܣ��쳣��Ϣ���£������ù���Ա������̣�", function() {
														alert(meg.Exception);
													});
												}
											},
											failure:function(response,options){
												Ext.MessageBox.alert('��ʾ','�¼�����ʧ�ܣ�',function(){
												
													Ext.getCmp('btn_confirm').enable();
  													Ext.getCmp('btn_back').enable();
												});
											}
									});
  									
  								}else{
  									Ext.MessageBox.alert("δ�ҵ���Ӧ�����̣���鿴�Ƿ�����!");
  								}
  							},
  							failure : function(response, options) {
  								Ext.MessageBox.alert("δ�ҵ���Ӧ�����̣���鿴�Ƿ�����!");
  							}
  						}, this);
							}
  					Ext.getCmp('btn_confirm').disable();
  					Ext.getCmp('btn_back').disable();
					var isExist;
					Ext.Ajax.request({
						url: webContext + '/eventAction_findIsUserFeedbackOrNot.action?eventId=' + eventId + '&surveyId=' + surveyId,
						method:'post', 
						success:function(response,options){
							var responseText = response.responseText;
							var data = Ext.decode(responseText);
							isExist = data.success;
							if(!isExist){//δ��д�����ʾ�
								Ext.MessageBox.confirm('��ȷ��', 'δ��ɷ������飬�Ƿ������',function(button, text){
									if(button == 'no'){
										Ext.getCmp('btn_confirm').enable();
		  								Ext.getCmp('btn_back').enable();
										return false;
									}
									//endEvent();
								},this);
							}else{
								endEvent();
							}
						}	
  					});
  					

  				}
  			});
  			  var buttonSolution=new Ext.Button({
  				id : 'btn_back',
  				xtype : 'button',
  				text : 'δ���',
  				iconCls : 'back',
  				handler : function(){
  					Ext.getCmp('btn_confirm').disable();
  					Ext.getCmp('btn_back').disable();
					var dealUser = Ext.getCmp('PageTemplates').getEventDealUser(eventId);
  					var users = dealUser;
                    Ext.Ajax.request({
						url: webContext + '/extjs/workflow?method=getDataFromPage&taskId=' + taskId + '&next=unconfirm'+ '&users=engineerProcess:' + users,
						method:'post', 
						success:function(response,options){
							Ext.MessageBox.alert('��ʾ','���ع���ʦ�ɹ���',function(){
								window.parent.auditContentWin.close();
							});
							
						},
						failure:function(response,options){
							Ext.MessageBox.alert('��ʾ','���ع���ʦʧ�ܣ�');
						}
					});
  				}
  			});
  			
 		 var tempPanel = new Ext.Panel({
        	height:80,
        	width:9999,
        	fieldLabel:"˵��",
        	style : 'margin:4px 4px 4px 4px',
        	frame:true,
        	border:true,
        	autoScroll : true,//2010-06-22 add by huzh for �ӹ�����
        	html:"<font size=2 face = '����_GB2312'>1���ڡ�����������У������Բ鿴���������ύ����Ľ��������˵���� <br>  " +
        		 "2��Ϊ�˰�����������IT�������������������ҳ���ϵġ���������ۡ��Է�����̺�IT������������ۡ����ۿ��Զ�ν��У�����������Ϊ׼��</font>"
        });
      	var da = new DataAction();
		var data = null;
		// �ж������������޸�
		var biddata = "";
		var buttonUtil = new ButtonUtil();
		this.getFormButtons = buttonUtil.getButtonForPanel("UserEventMessage_pagepanel",this);
		data = da.getPanelElementsForEdit("page_event_suppGroupLeader", "panel_event_suppGroupLeader", this.dataId);// ����Ҫ��ʱ���
	   
		for(var  i=0;i<data.length;i++){
				data[i].readOnly=true;
				data[i].hideTrigger=true;
				data[i].emptyText="";
			if(data[i].name=="Event$submitDate"){
				data[i].value=data[i].value.substring(0,19);
			}	
			if(data[i].name=="Event$description"){
//				alert(Ext.encode(data[i]));
			}	
			if(data[i].xtype=="panel"){
                data[i].items[0].disabled=true;
				data[i].items[1].html=data[i].items[1].html.replace(/display:/g, "display:none");
        
      		}
		}
		data.push(tempPanel);
		biddata = da.split(data);
		for(var i=0;i<biddata.length;i++){
			var tempName = biddata[i].id;
			if(tempName=='Event$description'){
				biddata[i].height = 100;
			}
		}
		if(this.getFormButtons.length!=0){
		this.formUserEventMessage_pagepanel= new Ext.form.FormPanel({
			id : 'UserEventMessage_pagepanel',
			layout : 'table',
			height : 'auto',
			width : 800,
			frame : true,
			buttons:[buttonPanel],
//			collapsible : true,
			defaults : {
				bodyStyle : 'padding:6px'
			},
			layoutConfig : {
				columns : 4
			},
			title : "�¼�ȷ��",
			items : biddata,
			buttons : this.getFormButtons
		});
		}else{
			this.formUserEventMessage_pagepanel= new Ext.form.FormPanel({
			id : 'UserEventMessage_pagepanel',
			layout : 'table',
			height : 'auto',
			buttonAlign : "center",
			buttons:[buttonConfim,buttonSolution],
			width : 800,
			frame : true,
			border:true,
//			collapsible : true,
			defaults : {
				bodyStyle : 'padding:6px'
			},
			layoutConfig : {
				columns : 4
			},
			title : "�¼�ȷ��",
			items : biddata
			});
		}
		return this.formUserEventMessage_pagepanel;
	},
	//�û�ȷ��--�������
 	getFormUserForLookSulotion_pagepanel: function(eventId) {
   		var da = new DataAction();
	  	var data = null;
		// �ж������������޸�
		var biddata = "";
		var knowledgeId;
		var conn = Ext.lib.Ajax.getConnectionObject().conn;
		var url = webContext+'/knowledgeAction_findKnowledgeByEventId.action?eventId='+eventId;
		conn.open("post", url, false);
		conn.send(null);
		if (conn.status == "200") {
			var responseText = conn.responseText;
			var data = Ext.decode(responseText);
			knowledgeId = data.knowledgeId;
		} 
		if(knowledgeId == 'noknowledge') {
			this.formUserForLookSulotion_pagepanel= new Ext.form.FormPanel({
				id : 'UserForLookSulotion_pagepanel',
				layout : 'table',
				height : 'auto',
				width : 800,
				frame : true,
				collapsible : true,
				defaults : {
					bodyStyle : 'padding:4px'
				},
				layoutConfig : {
					columns : 4
				},
				title : "�������",
				items : [{html : '<font color="red">û�н��������</font>'}]//,
				//buttons : this.getFormButtons
			});
			return this.formUserForLookSulotion_pagepanel;
		}else{
			data =da.getSingleFormPanelElementsForEdit("KnowLedgeSolution_pagepanel",knowledgeId);
			var konwledgecontext = "";
	        for(i = 0; i < data.length; i++){
	        	if(data[i].id=="Knowledge$resolvent"){
	        		konwledgecontext = data[i].value;
	        	}
	        }
			this.formUserForLookSulotion_pagepanel= new Ext.form.FormPanel({
				id : 'UserForLookSulotion_pagepanel',
				title : '�������',
				maximizable : true,
				autoScroll : true,
				width : 800,
				height:400,
				//2010-04-30 modified by huzh for ҳ���Ż� begin
				frame : true,
				items : [{html : konwledgecontext,autoScroll : true,height:370,width:700}]
				//2010-04-30 modified by huzh for ҳ���Ż� end
			});
			return this.formUserForLookSulotion_pagepanel;
		}
		
	},
	//�û�ȷ��--�¼��������
	getProcessProcedure : function(eventId){
		var da = new DataAction();
		var obj = da.getListPanelElementsForHead("eventAssignRemark_pagepanel");
		var sm = new Ext.grid.CheckboxSelectionModel();
		var columns = new Array();
		var fields = new Array();
		columns[0] = sm;
		for (var i = 0; i < obj.length; i++) {
			var headItem = obj[i];
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
			columns[i + 1] = columnItem;
			fields[i] = propertyName;
		}
		this.cm = new Ext.grid.ColumnModel(columns);
		this.rstore = da.getPanelJsonStore("eventAssignRemark_pagepanel",obj); //add header 
		this.rstore.paramNames.sort = "orderBy";
		this.rstore.paramNames.dir = "orderType";
		this.cm.defaultSortable = true;
		var viewConfig = Ext.apply({
			forceFit : true
		}, this.gridViewConfig);

		this.pageBar = new Ext.PagingToolbar({
			pageSize : 10,
			store : this.rstore,
			displayInfo : true,
			displayMsg : '��ǰ��ʾ {0}-{1}����¼ /��{2}����¼',
			emptyMsg : "����ʾ����"
		});
		//�¼��������
		this.earp = new Ext.grid.GridPanel({
			id : 'eventAssignRemark',
			title : '�¼��������',
			width : 700,
			height : 400,
			frame : true,
			store : this.rstore,
			cm : this.cm,
			sm : sm,
			trackMouseOver : false,
			loadMask : true,
			bbar : this.pageBar
		});	
		this.earp.on("rowdblclick",this.eventAssignRemark,this);
		
		var params = {
			'mehtodCall' : 'query',
			'start' : 0,
			'event': eventId
		};
		this.rstore.on('beforeload', function(a) {   
					      Ext.apply(a.baseParams,params);   
			});
		this.rstore.removeAll();
		this.rstore.load({
			params : params
		});
		return this.earp;
	},
	//�û�ȷ��--�û�����
  	getFeedback : function(eventId, taskId, surveyId){

		return new Ext.Panel({
  			title : '<font color="red">���������</font>',
  			id:"feedback_success",
  			width : 'auto',
  			heigth : 'auto',
  			frame : true,
  			autoScroll : true,
  			autoLoad : {
  				url: webContext + '/eventAction_findQuestions.action?surveyId=' + surveyId + '&eventId=' + eventId + "&taskId=" + taskId,
  				nocache : true, 
  				scripts :true,
  				text : "ҳ�����ڼ�����......",
				method : 'post',
				scope : this
  			},
  			viewConfig : {
				autoFill : true,
				forceFit : true
			},
			layout : 'fit'
  		});
	},
	/*
	 * clientvalidation ��ʼ��
	 */
	initComponent : function() {
		//��ѯsurveyId
  		var conn = Ext.lib.Ajax.getConnectionObject().conn;
		var url = webContext+'/eventAction_findEventSurvey.action';
		conn.open("get", url, false);
		conn.send(null);
		if (conn.status == "200") {
			var responseText = conn.responseText;
			var data = Ext.decode(responseText);
			this.surveyId = data.surveyId;
		} 
		this.task=this.taskId;
		if(this.task==""){
			var taskIdurl = webContext+'/eventAction_findTaskIdByDataIdandProcessId.action?dataId='+this.dataId+"&processId="+this.processId;
			conn.open("get", taskIdurl, false);
			conn.send(null);
			if (conn.status == "200") {
				var responseText = conn.responseText;
				var data = Ext.decode(responseText);
				this.task = data.taskId;
			} 
		}
		
		var items = new Array();
		var pa = new Array();
		this.pa = pa;
		var gd = new Array();
		this.gd = gd;
		var temp = new Array();
		this.temp = temp;
		var formname = new Array();
		this.formname=formname;
		var gridname = new Array();
		this.gridname=gridname;
		this.model="UserEventMessage_pageModel";
//		var buttonUtil = new ButtonUtil();
//		this.button = buttonUtil.getButtonForModel("UserEventMessage_pageModel",this);
//		if(this.button!=""){
//			this.mybutton = {
//			layout : 'table',
//			height : 'auto',
//			width : 800,
//			style : 'margin:4px 6px 4px 300px',
//			align : 'center',
//			defaults : {
//				bodyStyle : 'padding:4px'
//			},
//			items : this.mybuttons
//			};
//		}else{
//			this.mybutton = {
//			layout : 'table',
//			height : 'auto',
//			width : 800,
//			style : 'margin:4px 6px 4px 300px',
//			align : 'center',
//			defaults : {
//				bodyStyle : 'padding:4px'
//			}
//			};
//		}
//		

		
		if(this.task!=""&&this.task!="null"){
			this.his = new HistroyForm({dataId:this.dataId});
			this.getFormUserEventMessage_pagepanel(this.dataId, this.surveyId, this.task);
			this.pa.push(this.formUserEventMessage_pagepanel);
			this.formname.push("UserEventMessage_pagepanel");
			temp.push(this.formUserEventMessage_pagepanel);
			this.getFormUserForLookSulotion_pagepanel(this.dataId);
			this.pa.push(this.formUserForLookSulotion_pagepanel);
			this.formname.push("UserForLookSulotion_pagepanel");
			temp.push(this.formUserForLookSulotion_pagepanel);
			temp.push(this.getFeedback(this.dataId, this.task, this.surveyId));
			temp.push(this.his);
		}else{
			var meg={html:'�Ѵ�����������һ���ڣ�' }
	        temp.push(meg);
		}
            items.push(this.getTabpanel(temp));
	    	this.items = items;
       
         //items.push(tempPanel);
		//items.push(this.buttons);
		//items.push(this.buttonNews);
		PageTemplates.superclass.initComponent.call(this);
	}
})