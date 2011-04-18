PagePanel = Ext.extend(Ext.Panel, {
	id : "PageTemplates",
	layout : 'border',
	width : 900,
	frame : true,
	defaults : {
		bodyStyle : 'padding:4px'
	},

	forLooK : function() {
		var record = this.grid.getSelectionModel().getSelected();
		var dataId = record.get("id");
		var da = new DataAction();
		var data = da.getKnowledgeFormPanelElementsForEdit("CCCallInfoSolutionForm_pagepanel", dataId);
		var konwledgecontext = "";
        for(i = 0; i < data.length; i++){
        	if(data[i].id=="Knowledge$resolvent"){
        		konwledgecontext = data[i].value;
        	}
        }
		var windowSkip = new Ext.Window({
			title : '�������',
			maximizable : true,
			autoScroll : true,
			width : 600,
			height :400,
			modal : true,
			items : [{html : konwledgecontext}],
			bodyStyle : 'padding:4px',
			buttons : [{
				xtype : 'button',
				id : 'useKnowButton',
				handler : function() {
					var customerItcode=Ext.getCmp("customerItcode").getValue();
					if(customerItcode==""||customerItcode=='null'||customerItcode==null){
						Ext.MessageBox.alert("��ʾ", "������д�ͻ���Ϣ�е�ItCode��");
						return;	
					}
					Ext.getCmp("useKnowButton").disable();
					Ext.getCmp("useKnowAndMailButton").disable();
					Ext.getCmp("copyKnowButton").disable();
					var callPhone=Ext.getCmp("callPhone").getValue();
					var callName=Ext.getCmp("callName").getValue();
					var department=Ext.getCmp("department").getValue();
					var callId=this.callId;
					var submitUserItcode=this.submitUserItcode;
						Ext.Ajax.request({
							url : webContext
									+ '/eventAction_endCCEventWithValidKnowledge.action',
							params : {
								customerItcode:customerItcode,
								callPhone:callPhone,
								callId:callId,
								callName:callName,
								department:department,
								submitUserItcode:submitUserItcode,
								knowledgeId : dataId
							},
							success : function(response, options) {
								Ext.MessageBox.alert("��ʾ","�ɹ�ʹ�øý���������һ���¼���",function(){
									windowSkip.close();
									window.location = webContext +"/ccEntry/CCCallSuccess.jsp"
								});
							},
							scope : this,
							failure : function(response, options) {
								Ext.MessageBox.alert("��ʾ","����ʧ�ܣ�");
								Ext.getCmp("useKnowButton").enable();
								Ext.getCmp("useKnowAndMailButton").enable();
								Ext.getCmp("copyKnowButton").enable();
							}
						}, this);
					
				},
				text : '�ѽ��',
				style : 'width:80;text-align:right',
				scope : this
			},{
				xtype : 'button',
				id : 'useKnowAndMailButton',
				handler : function() {
					var customerItcode=Ext.getCmp("customerItcode").getValue();
					if(customerItcode==""||customerItcode=='null'||customerItcode==null){
						Ext.MessageBox.alert("��ʾ", "������д�ͻ���Ϣ�е�ItCode��");
						return;	
					}
					Ext.getCmp("useKnowButton").disable();
					Ext.getCmp("useKnowAndMailButton").disable();
					Ext.getCmp("copyKnowButton").disable();
					var callPhone=Ext.getCmp("callPhone").getValue();
					var callName=Ext.getCmp("callName").getValue();
					var department=Ext.getCmp("department").getValue();
					var callId=this.callId;
					var submitUserItcode=this.submitUserItcode;
						Ext.Ajax.request({
							url : webContext
									+ '/eventAction_endCCEventWithValidKnowledge.action',
							params : {
								customerItcode:customerItcode,
								callPhone:callPhone,
								callId:callId,
								callName:callName,
								department:department,
								submitUserItcode:submitUserItcode,
								knowledgeId : dataId,
								mailFlag : "yes"
							},
							success : function(response, options) {
								Ext.MessageBox.alert("��ʾ","�ɹ�ʹ�øý���������һ���¼���",function(){
									windowSkip.close();
									window.location = webContext +"/ccEntry/CCCallSuccess.jsp"
								});
							},
							scope : this,
							failure : function(response, options) {
								Ext.MessageBox.alert("��ʾ","����ʧ�ܣ�");
								Ext.getCmp("useKnowButton").enable();
								Ext.getCmp("useKnowAndMailButton").enable();
								Ext.getCmp("copyKnowButton").enable();
							}
						}, this);
					
				},
				text : '�������Ͳ����',
				style : 'width:100;text-align:right',
				scope : this
			}, {
				xtype : 'button',
				id : 'copyKnowButton',
				handler : function() {
					Ext.Ajax.request({
							url : webContext
									+ '/knowledgeAction_modifyKnowledgeUseTimes.action',
							params : {
								knowledgeId : dataId
							},
							success : function(response, options) {},
							scope : this,
							failure : function(response, options) {}
					}, this);
					var oldcontext=Ext.getCmp("Knowledge$resolvent_other").getValue();
					var newcontext=oldcontext+konwledgecontext;
					Ext.getCmp("Knowledge$resolvent_other").setValue(newcontext);
				},
				text : '���ƽ������',
				style : 'width:80;text-align:right',
				scope : this
			}]
		});
		windowSkip.show();
	},
	getCCCallInfoPanel : function() {
			var da = new DataAction();
			var url = webContext + '/eventAction_selectCCallData.action?customerItcode='
							+ this.customerItcode;
			var CCalldata = da.ajaxGetData(url);
			if(CCalldata.success==false){
				Ext.Msg.alert("��ʾ",'itcode�����ڣ�');
			}
			var url = webContext + '/accountSystemAdminAction_findRBType.action?username='
						+ this.customerItcode;
			var userlist = da.ajaxGetData(url);
			var tempStyle="";
			if(userlist.type==0){
				 tempStyle='color:red';
			}
			if(userlist.type==1){
				 tempStyle='font-weight:800';
			}
			
		var callName = new Ext.form.TextField({
			name : "customerItcode",
			allowBlank : false,
			fieldLabel : "itcode",
			id : 'customerItcode',
			style:tempStyle,
			width : 170,
			listeners:{
				change:function(cmp, newValue, oldValue){
					var url = webContext + '/eventAction_selectCCallData.action?customerItcode='
						+ newValue;
					var CCallChangeData = da.ajaxGetData(url);
					if(CCallChangeData.success==false){
						Ext.Msg.alert("��ʾ",'itcode�����ڣ�');
						Ext.getCmp("customerItcode").setValue(oldValue);
					}else{
						var itcode=Ext.getCmp('customerItcode').getValue();
						var url = webContext + '/accountSystemAdminAction_findRBType.action?username='
								+ itcode;
						var userlist = da.ajaxGetData(url);
						var tempStyle="font-weight:400;color:black";
						if(userlist.type==0){
							tempStyle='font-weight:400;color:red';
						}
						if(userlist.type==1){
							tempStyle='font-weight:800;color:black';
						}
						Ext.getCmp("customerItcode").getEl().applyStyles(tempStyle);
						Ext.getCmp("callName").getEl().applyStyles(tempStyle);
						Ext.getCmp("callPhone").getEl().applyStyles(tempStyle);
						Ext.getCmp("department").getEl().applyStyles(tempStyle);
						Ext.getCmp("userTelephone").getEl().applyStyles(tempStyle);
						Ext.getCmp("mobilePhone").getEl().applyStyles(tempStyle);
						Ext.getCmp("platform").getEl().applyStyles(tempStyle);
						Ext.getCmp("postCode").getEl().applyStyles(tempStyle);
						Ext.getCmp("personScope").getEl().applyStyles(tempStyle);
						
						Ext.getCmp("callName").setValue(CCallChangeData.callName);
						Ext.getCmp("department").setValue(CCallChangeData.department);
						Ext.getCmp("userTelephone").setValue(CCallChangeData.userTelephone);
						Ext.getCmp("mobilePhone").setValue(CCallChangeData.mobilePhone);
						Ext.getCmp("platform").setValue(CCallChangeData.platform);
						Ext.getCmp("postCode").setValue(CCallChangeData.postCode);
						Ext.getCmp("personScope").setValue(CCallChangeData.personScope);
					}
				}
			}
		});
		var telephone = new Ext.form.TextField({
			name : "callPhone",
			fieldLabel : "�绰����",
			readOnly : true,
			id : 'callPhone',
			style:tempStyle,
			width : 170
		});
		var Name = new Ext.form.TextField({
			name : "callName",
			fieldLabel : "����",
			readOnly : true,
			style:tempStyle,
			id : 'callName',
			width : 170
		});
		var mobilePhone = new Ext.form.TextField({
			name : "mobilePhone",
			fieldLabel : "�ֻ�",
			id : 'mobilePhone',
			style:tempStyle,
			readOnly : true,
			width : 170
		});
		var userTelephone = new Ext.form.TextField({
			name : "userTelephone",
			fieldLabel : "���˵绰����",
			id : 'userTelephone',
			style:tempStyle,
			readOnly : true,
			width : 170
		});
		var subDeparment = new Ext.form.TextField({
			name : "department",
			fieldLabel : "��������",
			readOnly : true,
			id : 'department',
			style:tempStyle,
			width : 170
		});
		var platform = new Ext.form.TextField({
			name : "platform",
			fieldLabel : "����ƽ̨",
			readOnly : true,
			id : 'platform',
			style:tempStyle,
			width : 170
		});
		var postCode = new Ext.form.TextField({
			name : "postCode",
			fieldLabel : "ְλ",
			readOnly : true,
			id : 'postCode',
			style:tempStyle,
			width : 170
		});
		var personScope = new Ext.form.TextField({
			name : "personScope",
			fieldLabel : "�����ӷ�Χ",
			readOnly : true,
			id : 'personScope',
			style:tempStyle,
			width : 170
		});
		var AddToRedButton = new Ext.Button({
			style : 'margin:0px 0px 0px 5px',
			scope : this,
			handler :function(){
				var name=Ext.getCmp('customerItcode').getValue();
				Ext.Ajax.request({
						url : webContext
								+ '/accountSystemAdminAction_addRAndBUserList.action',
						params : {
							manageritcode : name,
							managername : '0'
							
						},
						success : function(response, options) {
							var r = Ext.decode(response.responseText);
							if(r.noitcode==1){
								Ext.Msg.alert("��ʾ","����ʧ�ܣ�ϵͳ��û�����itcode��");
							}else if(r.noitcode==2){
									Ext.Msg.alert("��ʾ","����ʧ�ܣ�����û��Ѿ��ں�������У�");
							}else{
								Ext.Msg.alert("��ʾ", "����ɹ���", function() {
								});
							}
							
						},
						failure : function(response, options) {
							Ext.MessageBox.alert("��ʾ", "����ʧ�ܣ�");
						}
					}, this);
			
			},
			text : '���������',
			iconCls : 'add'
		});
		var AddToBlackButton = new Ext.Button({
			style : 'margin:0px 0px 0px 5px',
			scope : this,
			handler :function(){
				var name=Ext.getCmp('customerItcode').getValue();
				Ext.Ajax.request({
						url : webContext
								+ '/accountSystemAdminAction_addRAndBUserList.action',
						params : {
							manageritcode : name,
							managername : '1'
							
						},
						success : function(response, options) {
							var r = Ext.decode(response.responseText);
							if(r.noitcode==1){
								Ext.Msg.alert("��ʾ","����ʧ�ܣ�ϵͳ��û�����itcode��");
							}else if(r.noitcode==2){
									Ext.Msg.alert("��ʾ","����ʧ�ܣ�����û��Ѿ��ں�������У�");
							}else{
								Ext.Msg.alert("��ʾ", "����ɹ���", function() {
								});
							}
							
						},
						failure : function(response, options) {
							Ext.MessageBox.alert("��ʾ", "����ʧ�ܣ�");
						}
					}, this);
			
			},
			text : '���������',
			iconCls : 'add'
		});
		var cataPanel = new Ext.form.FormPanel({
//			title :"�ͻ���Ϣ",
			region:'north',
			width : 880,
			layout : 'table',
//			autoScroll : true,
			height : 75,
//		    collapsible : true,
			layoutConfig : {
					columns : 7
				},
				items : [{
					html : "ItCode:",
					cls : 'common-text',
					width : 78,
					style : 'width:150;text-align:right'
				}, callName, {
					html : "�绰:",
					cls : 'common-text',
					width : 68,
					style : 'width:150;text-align:right'
				}, telephone, {
					html : "��������:",
					cls : 'common-text',
					width : 78,
					style : 'width:150;text-align:right'
				}, subDeparment,AddToRedButton,{
					html : "����:",
					cls : 'common-text',
					width : 78,
					style : 'width:150;text-align:right'
				}, Name, {
					html : "���˵绰:",
					cls : 'common-text',
					width : 68,
					style : 'width:150;text-align:right'
				}, userTelephone, {
					html : "�����ֻ�:",
					cls : 'common-text',
					width : 78,
					style : 'width:150;text-align:right'
				}, mobilePhone, AddToBlackButton,{
					html : "����ƽ̨:",
					cls : 'common-text',
					width : 78,
					style : 'width:150;text-align:right'
				}, platform,{
					html : "ְλ����:",
					cls : 'common-text',
					width : 68,
					style : 'width:150;text-align:right'
				},postCode,{
					html : "�����ӷ�Χ:",
					cls : 'common-text',
					width : 78,
					style : 'width:150;text-align:right'
				},personScope]
			});
			Ext.getCmp("customerItcode").setValue(this.customerItcode);
			Ext.getCmp("callPhone").setValue(this.callPhone);
			Ext.getCmp("callName").setValue(CCalldata.callName);
			Ext.getCmp("department").setValue(CCalldata.department);
			Ext.getCmp("userTelephone").setValue(CCalldata.userTelephone);
			Ext.getCmp("mobilePhone").setValue(CCalldata.mobilePhone);
			Ext.getCmp("platform").setValue(CCalldata.platform);
			Ext.getCmp("postCode").setValue(CCalldata.postCode);
			Ext.getCmp("personScope").setValue(CCalldata.personScope);
		return cataPanel;
	},
	
	getGrid:function(){
		this.storeList = new Ext.data.JsonStore({
			url : webContext + '/knowledgeAction_findKnowledgeByServiceItem.action',
			fields : ['id', 'summary'],
			totalProperty : 'rowCount',
			root : 'data',
			id : 'id'
		});
		this.cm = new Ext.grid.ColumnModel([{
			header : "�������",
			dataIndex : "summary",
			width : 240  
		}]);
		var viewConfig = Ext.apply({
			forceFit : true
		}, this.gridViewConfig);
		this.pageBar = new Ext.PagingToolbar({
		 	id : "knowPageBar",
			pageSize : 10,
			store : this.storeList,
			displayInfo : false
		});
		this.formValue = '';
		this.pageBar.formValue = this.formValue;
		this.grid = new Ext.grid.GridPanel({
			store : this.storeList,
			id : "solutionGrid",
			cm : this.cm,
			colspan : 2,
			autoScroll : true,
			loadMask : true,
			height : 265,
			width : 265,
			viewConfig: {
               forceFit:true
             },
			bbar : this.pageBar
		});
		var params = {
			siId : Ext.getCmp("childsort").getValue(),
			summaryKeyWord:Ext.getCmp("problemName").getValue(),
			start : 0

		};

//		this.pageBar.formValue = param;
		this.storeList.on('beforeload', function(a) {   
					      Ext.apply(a.baseParams,params);   
					});
		this.storeList.removeAll();
		this.storeList.load({
			params : params
		});
		this.grid.on("rowdblclick", this.forLooK, this);
		return this.grid;
	},                  
      
	getEventPanel:function() {
		this.engineer = new Ext.form.ComboBox({
			name : "engineer",
			id : 'engineer',
			fieldLabel : "ָ�ɹ���ʦ",
			width : 200,
			hiddenName : 'groups$engineer',
			displayField : 'name', 
			valueField : 'id',
			resizable : true,
			emptyText :'��������б���ѡ��...',
			triggerAction : 'all',
			store : new Ext.data.JsonStore({
				url : webContext
						+ '/eventAction_findAllEngineersByServiceItem.action',
				fields : ['id', 'name'],
				totalProperty : 'rowCount',
				root : 'data',
				id : 'id'
			}),
			pageSize : 10,
			listeners : {
				'beforequery' : function(queryEvent) {
					var si=Ext.getCmp("childsort");
					if(si.getRawValue().trim()==""){
						si.setValue("");
					}
					if(si.getValue()==""){
						Ext.MessageBox.alert("��ʾ","���ȴ������б���ѡ������");
						return false;
					}
					var serviceItemId=si.getValue();
					var param = queryEvent.combo.getRawValue();
					var val = queryEvent.combo.getValue();
					if (queryEvent.query == '') {
						param = '';
					}
					this.store.baseParams={name : param,serviceItemId:serviceItemId};
					this.store.load();
					return false;
				}
			}
		});
		var cpanel = new Ext.form.FormPanel({
			id : 'cataPanel',
			layout : 'table',
			height : 'auto',
			width :585,
			style :'margin:0px;',
			defaults : {
				bodyStyle : 'padding:0px'
			},
			frame : true,
			layoutConfig : {
				 columns:4
			  },
			items : [{
					html : '�¼�����:',
					cls : 'common-text',
					style : 'width:70;text-align:right'
				},new Ext.form.TextField({
					fieldLabel : '�¼�����',
					xtype : 'textfield',
					colspan : 0,
					rowspan : 0,
					id : 'Event$summary',
					name : 'Event$summary',
					width : 200,
					allowBlank : false
				}), {
					html : '�¼�������:',
					cls : 'common-text',
					style : 'width:85;text-align:right'
				},new Ext.form.ComboBox({
					xtype : 'combo',
					hiddenName : 'Event$ponderance',
					id : 'Event$ponderanceCombo',
					width : 200,
					fieldLabel : '�¼�������',
					lazyRender : true,
					emptyText : '��������б���ѡ��...',
					allowBlank : true,
					name : 'Event$ponderance',
					triggerAction : 'all',
					mode : 'local',
					forceSelection :true,
					store : new Ext.data.SimpleStore({
							fields : ['ponId', 'ponName'],
							data : [['1', '��Ҫ����'], ['2', '���ش���'], ['3', 'ϵͳ����'],[]]
					}),
					valueField : 'ponId',
					displayField : 'ponName',
					listeners : {
						'expand' : function(combo) {
							combo.reset();
						}
					}
				}),{
					html : '�¼�����:',
					cls : 'common-text',
					style : 'width:70;text-align:right'
				},new Ext.form.TextArea({
						xtype:"textarea",
						id:"Event$description",
						name:"Event$description",
						width:485,
						height:90,
						style : 'margin:1px 0px 1px 0px;',
						colspan : 3,
						allowBlank:false,
						fieldLabel : "�¼�����",
						emptyText :'������������������4000������'
				}),new Ext.Panel({
						height : 23,
						layout : 'table',
						width :557,
						colspan : 4,
						items:[{
									html : '����:',
									cls : 'common-text',
									style : 'width:70;text-align:right'
								},new Ext.form.ComboBox({
									name : "problemSort",
									id : 'problemSort',
									fieldLabel : "����",
									width : 200,
									hiddenName : 'Event$problemSort',
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
									}
								}),{
									html : '����:',
									cls : 'common-text',
									style : 'width:55;text-align:right'
								},{
									xtype:"panel",
									layout:"table",
									width:490,
									colspan : 3,
									layoutConfig:{columns:4},
									fieldLabel:"������ַ",
									defaults:{baseCls:"margin : 10 15 12 15"},
									items:[{fieldLabel:"������ַ",
											xtype:"button",
											disabled:false,
											text:"<font color=red>�� ��</font>",
											width:50,
											scope:this,
											handler:function(){
											var attachmentFlag = Ext.getCmp("Event$appendix").getValue();
											if(attachmentFlag==""||attachmentFlag=="null"){
												attachmentFlag = Date.parse(new Date());
												Ext.getCmp("Event$appendix").setValue(attachmentFlag);
												var ud=new UpDownLoadFile();
												ud.getUpDownLoadFileSu(attachmentFlag,"2454","com.digitalchina.info.appframework.metadata.entity.SystemFile","eventappendixname");
											}else{
												var ud=new UpDownLoadFile();
												ud.getUpDownLoadFileSu(attachmentFlag,"2454","com.digitalchina.info.appframework.metadata.entity.SystemFile","eventappendixname");
											}
										}
										},{id:"eventappendixname",
											width:450,
											border : true,
											html:"",
											cls : "common-text",
											style : "width:100;text-align:left"
									}]
								},new Ext.form.Hidden({
									xtype:"hidden",
									id:"Event$appendix",
									name:"Event$appendix",
									style:"",
									value:"null",
									fieldLabel:"nowtime"
							})]})]
		});
		var engineerButtons=new Ext.Panel({
			height : 25,
			layout : 'table',
			width :585,
			layoutConfig : {
				 columns:4
			  },
			items:[{
					html : "ָ�ɹ���ʦ:",
					cls : 'common-text',
					width : 73,
					style : 'width:100;text-align:right'
				}, 
			this.engineer,{
				xtype : 'button',
				iconCls : 'submit',
				id : 'dealButton',
				handler : function(){this.submitEvent()},
				text : '�ύ��������ʦ',
				style : 'width:100;text-align:left',
				scope : this
			},{
				xtype : 'button',
				iconCls : 'submit',
				id : 'submitButton',
				handler : function(){this.UserSelfDeal()},
				text : '������ύ�û�',
				style : 'width:100;text-align:right;margin-left:60px',
				scope : this
			}]
		});
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
		this.mailCheckBox=new Ext.form.Checkbox({
			id:'mailCheckBox',  
			xtype:"checkbox",
			name:"Know$mailFlag",
			hiddenName : 'Know$mailFlagCB',
			fieldLabel:"�Ƿ��������ͽ������",
			boxLabel:"��",
			checked:false
		});
		this.mailCheckBox.on("check", function(field) {
			var value = field.getValue();
			if (value == false) {
				this.mailEnabledValue.setValue("no");
				return;
			} else if (value == true) {
				this.mailEnabledValue.setValue("yes");
				return;
			}
		}, this);
		this.mailEnabledValue = new Ext.form.Hidden({
			id : 'mailEnabledHid',
			name : 'mailEnabled',
			value : "no"
		});
		var submitKnowButtons=new Ext.Panel({
			height : 23,
			layout : 'table',
			width :557,
			colspan : 4,
			layoutConfig : {
				 columns:9
			  },
			items:[{
					html : "����ʽ:",
					cls : 'common-text',
					width : 73,
					style : 'width:100;text-align:right;'
				}, 
				new Ext.form.ComboBox({
						xtype : 'combo',
						name : 'Event$eventDealType',
						hiddenName : 'Event$eventDealType',
						id : 'Event$eventDealTypeCombo',
						width : 150,
						fieldLabel : '�¼�����ʽ',
						lazyRender : true,
						forceSelection :true,
						allowBlank : true,
						triggerAction : 'all',
						minChars : 50,
						queryDelay : 700,
						mode : 'local',
						allowBlank : true,
						store : new Ext.data.SimpleStore({
							fields : ['typeId', 'typeName'],
							data : [['1', '�绰'], ['2', '�绰+Զ��'], ['3', '�ֳ�'], ['4', '�ʼ�'],['5','sametime'],[]]
						}),
						emptyText : '��ѡ��...',
						valueField : 'typeId',
						displayField : 'typeName',
						value :"1",//Ĭ��ѡ�񡰵绰��
						listeners : {
							'expand' : function(combo) {
								combo.reset();
							}
						}
					}),
					{
					html : "�Ƿ��ύ֪ʶ:",
					cls : 'common-text',
					width : 80,
					style : 'width:100;text-align:right;margin-left:25px'
				}, this.submitCheckBox,this.enabledValue,{
					html : "�Ƿ��������ͽ������:",
					cls : 'common-text',
					width : 140,
					style : 'width:100;text-align:right;margin-left:15px'
				}, this.mailCheckBox,this.mailEnabledValue]
		});
		var buttonsPanel=new Ext.Panel({
			height : 'auto',
			layout : 'table',
			width :585,
			frame : true,
			layoutConfig : {
				 columns:1
			  },
			items:[engineerButtons]
		});
		this.childsort = new Ext.form.ComboBox({
			name : "childsort",
			id : 'childsort',
			fieldLabel : "������",
			width : 200,
			hiddenName : 'Event$scidData',
			displayField : 'name', 
			forceSelection : true,
			valueField : 'id',
			allowBlank : false,
			resizable : true,
			emptyText :'��������б���ѡ��...',
			triggerAction : 'all',
			store : new Ext.data.JsonStore({
				url : webContext
						+ '/eventAction_findServiceItem.action',
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
					this.store.baseParams={name : param,official:1};
					this.store.load();
					return false;
				},
				'select' : function() {
					Ext.getCmp("engineer").clearValue();
					Ext.getCmp("Knowledge$knowProblemTypeCombo").clearValue();
					var sstore = Ext.getCmp("solutionGrid").getStore();
					var serviceItem = Ext.getCmp("childsort").getValue();
					var keyWord=Ext.getCmp("problemName").getValue();
				    var params={
				    	siId : serviceItem,
				    	summaryKeyWord : keyWord,
				    	start : 0
				    };
//				    Ext.getCmp("knowPageBar").formValue=params;
				     sstore.on('beforeload', function(a) {   
					      Ext.apply(a.baseParams,params);   
					});
					sstore.load({
						params:params
					});
				}
			}
		});
		var sweviceItempanel=new Ext.form.FormPanel({
				height : 30,
				id : 'servicePanel',
				layout : 'table',
				width : 278,
				colspan: 2 ,
				layoutConfig : {
					columns : 2
				},
				defaults : {
					bodyStyle : 'padding:0px'
				},
				items : [{
							html : "������:",
							cls : 'common-text',
							width : 77,
							style : 'width:100;text-align:right'
							}, 
							this.childsort
						]
			});
		var keyWordPanel=new Ext.Panel({
			layout:'table',
//			collapsible : true,
			height : 25,
			width :265,
			region:'center',
			items:[{
					html : '��������ؼ���:',
					cls : 'common-text',
					style : 'width:95;text-align:right'
				}, new Ext.form.TextField({
					fieldLabel : '��������',
					xtype : 'textfield',
					id : 'problemName',
					name : 'problemName',
					width : 105,
					allowBlank : true
				}),{
					xtype :'button',
					text : 'ɸѡ',
					iconCls : 'search',
					handler : function(){
						if(Ext.getCmp("childsort").getRawValue().trim()==""){
							Ext.getCmp("childsort").setValue("");
						}
						var sstore = Ext.getCmp("solutionGrid").getStore();
						var serviceItem = Ext.getCmp("childsort").getValue();
						var keyword = Ext.getCmp("problemName").getValue();
					    var params={
					    	siId : serviceItem,
					    	summaryKeyWord : keyword,
					    	start : 0
					    };
//					     Ext.getCmp("knowPageBar").formValue=params;
					     sstore.on('beforeload', function(a) {   
					     	 Ext.apply(a.baseParams,params);   
							});
						sstore.load({
							params:params
						});
					},
					style : 'width:40;text-align:right;margin-left:10px',
					scope : this
			}]
		});
		var getGridPanel=new Ext.Panel({
			layout:'table',
//			autoScroll : true,
			height : 'auto',
			rowspan: 2,
			width :277,
			frame : true,
			layoutConfig:{
				columns:1
			},
			region:'center',
			items:[keyWordPanel,this.getGrid()]
		});
		var knowledgePanel=new Ext.Panel({
			layout:'table',
			height : 'auto',
			rowspan: 2,
			width :290,
			frame: true,
			layoutConfig:{
				columns:1
			},
			region:'center',
			items:[sweviceItempanel,getGridPanel]
		});
		var knowPanel = new Ext.form.FormPanel({
			id : 'knowPanel',
			layout : 'table',
			height : 'auto',
			width :585,
			buttonAlign : 'center',
			frame : true,
			layoutConfig : {
				 columns:4
			 },
			items : [{
					html : '��������:',
					cls : 'common-text',
					style : 'width:73;text-align:right'
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
					style : 'width:88;text-align:right'
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
					style : 'width:70;text-align:right'
				},new Ext.form.FCKeditor({
					height : 100,
					colspan : 3,
					id:'Knowledge$resolvent_other',
					name : 'Knowledge$resolvent_other',
					allowBlank : true,
					value:"ԭ��<br><br>���������",//2010-07-12 add by huzh for ��Ĭ��ֵ
					width : 483
				}),submitKnowButtons]
		});
		var knowledge=new Ext.Panel({
			layout:'table',
//			autoScroll : true,
//			collapsible : true,
			height : 'auto',
			rowspan: 2,
			style : 'margin:0px',
			layoutConfig:{
				columns:1
			},
			region:'center',
			items:[knowPanel,buttonsPanel]
		});
		
		var xpanel=new Ext.Panel({
			layout:'table',
//			frame : true,
//			autoScroll : true,
//			collapsible : true,
			style : 'margin-left:0px',
//			title:"�¼�������Ϣ��",
			layoutConfig:{
				columns:2
			},
			region:'center',
			items:[knowledgePanel,cpanel,knowledge]
		})
		return xpanel;
	},

	//�ύ�¼�
	   submitEvent :function(){
		   	var customerItcode=Ext.getCmp("customerItcode").getValue();
			if(customerItcode.trim()==""||customerItcode=='null'||customerItcode==null){
				Ext.MessageBox.alert("��ʾ", "�ͻ���Ϣ�е�ItCodeΪ�����");
				return;	
			}
			Ext.getCmp("submitButton").disable();
			Ext.getCmp("dealButton").disable();
			if (Ext.getCmp("cataPanel").getForm().isValid()&&Ext.getCmp("servicePanel").getForm().isValid()) {
				var panelparam = Ext.encode(getFormParam('cataPanel'));
				var callPhone=Ext.getCmp("callPhone").getValue();
				var callName=Ext.getCmp("callName").getValue();
				var department=Ext.getCmp("department").getValue();
				var callId=this.callId;
				var submitUserItcode=this.submitUserItcode;
				Ext.Ajax.request({
					url : webContext
							+ '/eventAction_submitEventCC.action',
					params : {
						customerItcode : customerItcode,
						callPhone : callPhone,
						callId : callId,
						callName : callName,
						department : department,
						submitUserItcode : submitUserItcode,
						serviceItem : Ext.getCmp("childsort").getValue(),
						engineer : Ext.getCmp("engineer").getValue(),
						panelparam : panelparam
					},
					success : function(response, options) {
						var userID = eval('(' + response.responseText+ ')').userID;							
						var eventName = eval('('+ response.responseText + ')').eventName;
						var eventCisn = eval('(' + response.responseText
							+ ')').eventCisn;
						var eventSubmitUser = eval('(' + response.responseText
							+ ')').eventSubmitUser;
						var eventSubmitDate = eval('(' + response.responseText
							+ ')').eventSubmitDate
						this.dataId = eval('(' + response.responseText+ ')').eventId;
	                    var users = userID;   
	                    if(users!="") { 
						// ----------------------------
						Ext.Ajax.request({
							url : webContext
									+ '/eventWorkflow_apply.action',
							params : {
								creator : customerItcode,
								dataId : this.dataId,
								model : this.model,
								bzparam : "{dataId :'"
										+ this.dataId
										+ "',users:'engineerProcess:"
										+ userID
										+ "',applyId : '"
										+ this.dataId
										+ "',eventName : '"
										+ eventName
										+"',eventSubmitUser:'"
										+eventSubmitUser
										+"',eventSubmitDate:'"
										+eventSubmitDate
										+ "',eventCisn:'"
										+ eventCisn
										+ "', applyType: 'eproject',applyTypeName: '�¼�����������',customer:'',workflowHistory:'com.digitalchina.itil.event.entity.EventAuditHis'}",
								defname : 'eventAndProblemProcess1240370895640'
							},
							success : function(response, options) {},
							failure : function(response, options) {
								Ext.MessageBox.alert("��ʾ", "����������ʧ�ܣ�");
								Ext.getCmp("submitButton").enable();
								Ext.getCmp("dealButton").enable();
							}
						}, this);
	                    }else{
	                        Ext.MessageBox.alert("��ʾ","�˷���û��֧����֧�֣�");
	                        Ext.getCmp("submitButton").enable(); 
	                        Ext.getCmp("dealButton").enable();
	                    }  
	                    Ext.MessageBox.alert("��ʾ", "�ύ�ɹ���",function(){
						 window.location = webContext+"/ccEntry/CCCallSuccess.jsp"
						});
						
					},
					failure : function(response, options) {
						Ext.MessageBox.alert("��ʾ", "�ύʧ�ܣ�");
	                     Ext.getCmp("submitButton").enable(); 
	                     Ext.getCmp("dealButton").enable();
					}
				}, this);
				}else {
					Ext.MessageBox.alert("��ʾ","��ɫ�����߲���Ϊ����������޷��ύ��");
					Ext.getCmp("submitButton").enable();
					Ext.getCmp("dealButton").enable();
				}
		},
		//���߹���ʦ�Դ���
	UserSelfDeal : function() {
		var si= Ext.getCmp('childsort');
		if (si.getRawValue().trim()=="") {
			si.setValue("");
		}
		var type=Ext.getCmp("Knowledge$knowProblemTypeCombo");
		if(type.getRawValue().trim()==""){
			type.setValue("");
		}
		var customerItcode=Ext.getCmp("customerItcode").getValue();
		if(customerItcode.trim()==""||customerItcode=='null'||customerItcode==null){
			Ext.MessageBox.alert("��ʾ", "�ͻ���Ϣ�е�ItCodeΪ�����");
			return;	
		}
		//2010-06-21 modified by huzh for bug begin
		var resolvent=Ext.getCmp("Knowledge$resolvent_other").getValue();
//		if(resolve.trim()==''||type.getValue()==""||Ext.getCmp("Knowledge$summary").getValue().trim()==""){
		//2010-06-21 modified by huzh for bug end
		if (Ext.getCmp("cataPanel").getForm().isValid()
				&&Ext.getCmp("servicePanel").getForm().isValid()) {
			if(type.getValue()==""||Ext.getCmp("Knowledge$summary").getValue().trim()==""){	
				Ext.MessageBox.alert("��ʾ", "����д�������͡��������⼰���������");
				return;
			}
			if(Ext.getCmp("Event$eventDealTypeCombo").getValue()==""){	
				Ext.MessageBox.alert("��ʾ", "��������б���ѡ���¼�����ʽ��");
				return;
			}
			Ext.getCmp("dealButton").disable();
			Ext.getCmp("submitButton").disable();
			var knowparam = Ext.encode(getFormParam('knowPanel'));
			var eventparam = Ext.encode(getFormParam('cataPanel'));
			var customerItcode=Ext.getCmp("customerItcode").getValue();
			var callPhone=Ext.getCmp("callPhone").getValue();
			var callName=Ext.getCmp("callName").getValue();
			var department=Ext.getCmp("department").getValue();
			var callId=this.callId;
			var submitUserItcode=this.submitUserItcode;
			var resolve=Ext.encode(resolvent);
			Ext.Ajax.request({
				url : webContext
						+ '/eventAction_saveCCallSulotion.action',
				params : {
					knowparam:knowparam,
					resolvent:resolve.substring(1,resolve.length-1),
					eventparam:eventparam,
					serviceItem : Ext.getCmp("childsort").getValue(),
					customerItcode:customerItcode,
					callPhone:callPhone,
					callId:callId,
					callName:callName,
					department:department,
					submitUserItcode:submitUserItcode
				},
				success : function(response, options) {
					if(Ext.getCmp("enabledHid").getValue()=="yes"){//2010-06-25 modified by huzh for ѡ�����ύ֪ʶ
						var dataId = eval('('+ response.responseText + ')').knowledgeId;
						var dataType = eval('('+ response.responseText + ')').knowledgeTypeId;
						var createUser = eval('('+ response.responseText + ')').createUser;
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
									defname : 'KnowledgeProcess1276415839731'
								},
								success:function(response,options){
								},
								failure:function(response,options){
									Ext.MessageBox.alert("��ʾ", "�ύʧ�ܣ�",function(){
									Ext.getCmp("dealButton").enable();
									Ext.getCmp("submitButton").enable();
									});
								}
						}, this);
					}
					Ext.MessageBox.alert("��ʾ", "�ύ�ɹ���",function(){
						window.location=webContext+'/ccEntry/CCCallSuccess.jsp';
					});
				},
				failure : function(response, options) {
					Ext.MessageBox.alert("��ʾ", "�ύʧ�ܣ�",function(){
					Ext.getCmp("dealButton").enable();
					Ext.getCmp("submitButton").enable();
					});
				}
			}, this);
		}else{
		Ext.MessageBox.alert("��ʾ","��ɫ�����߲���Ϊ����������޷��ύ��",function(){
					Ext.getCmp("dealButton").enable();
					Ext.getCmp("submitButton").enable();
			});
		}
	},
	items : this.items,
	initComponent : function() {
		var param=this.CCCallParam.split("*");
		this.loginItCode=param[0];
		this.submitUserItcode=param[1];
		this.customerItcode=param[2];
		this.callId=param[3];
		this.callPhone=param[4];
		var items = new Array();
		items.push(this.getCCCallInfoPanel());
		items.push(this.getEventPanel());
		Ext.getCmp('childsort').setValue("");
		Ext.getCmp('Event$summary').setValue("");
		Ext.getCmp('Event$description').setValue("");
		this.items = items;
		PagePanel.superclass.initComponent.call(this);
	}
})
