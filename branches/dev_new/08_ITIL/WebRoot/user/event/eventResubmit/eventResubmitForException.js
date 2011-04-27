PagePanel = Ext.extend(Ext.Panel, {
	id : "PageTemplates",
	frame : true,
	title:"�쳣�¼�������Ϣ��<font style='font-weight:lighter' color=red >~~~Ϊ�����Ϊ������׼ȷ��λ��ʱ������⣬������д��</font>��",
	autoScroll : true,
	defaults : {  
		bodyStyle : 'padding:4px'
	},
	layout : 'border',
	
	
	getEventPanel : function() {
		this.eventType = new Ext.form.ComboBox({
			name : "eventtype",
			id : 'eventtype',
			fieldLabel : "�¼�����",  
			width : 200,
			forceSelection :true,
			displayField : 'name',
			hiddenName : 'Event$eventType',//���ӵ�
			allowBlank : false,
			valueField : 'id',
			resizable : true,
			emptyText :'��������б���ѡ��...',
			triggerAction : 'all',
			store : new Ext.data.JsonStore({
				url : webContext + '/eventAction_findAllEventTypeByName.action',
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
					this.store.baseParams={"typeName" : param,type:"combox"};//2010-05-31 modified by huzh for bug
					this.store.load();
					return false;
				},
				'select':function(){
					Ext.getCmp("serviceItem").setValue('');
				}
			}
		});
		this.serviceItem = new Ext.form.ComboBox({
			name : "serviceItem",
			id : 'serviceItem',
			fieldLabel : "������",
			width : 200,
			hiddenName : 'Event$scidData',
			displayField : 'name', 
			valueField : 'id',
			forceSelection :true,
			resizable : true,
			emptyText :'��������б���ѡ��...',
			triggerAction : 'all',
			store : new Ext.data.JsonStore({
				url : webContext
						+ '/eventAction_findServiceItemByEventType.action',
				fields : ['id', 'name'],
				totalProperty : 'rowCount',
				root : 'data',
				id : 'id'
			}),
			pageSize : 10,
			listeners : {
				'beforequery' : function(queryEvent) {
					var param = queryEvent.combo.getRawValue();
					var type = Ext.getCmp("eventtype").getValue();
					var val = queryEvent.combo.getValue();
					if (queryEvent.query == '') {
						param = '';
					}
					this.store.baseParams={eventtype:type,name : param,official:1};
					this.store.load();
					return false;
				}
			}

		});
		var da = new DataAction();
		var data = da.getSingleFormPanelElementsForEdit("eventResubmitPanel",this.dataId);
		var newdata=new Array();
        newdata.push(Ext.getCmp("eventtype"));
        newdata.push(Ext.getCmp("serviceItem"));
		for (var i = 0; i < data.length; i++) {
			newdata.push(data[i]);
			if(data[i].xtype=="combo"){
				data[i].emptyText ='��������б���ѡ��...';
			};
			if (data[i].name == "Event$description") {
				data[i].fieldLabel = "�¼�����";
				data[i].width = 9999;
				data[i].allowBlank=false;
				data[i].emptyText = '������������������4000������';
			};
			if (data[i].name == "Event$eventtype"||data[i].name == "Event$summary") {
				data[i].allowBlank=false;
			};
			if (data[i].name == "Event$frequency"
			      ||data[i].name == "Event$ponderance"
			         ||data[i].name == "Event$submitUser"
			          ||data[i].name == "Event$submitDate"
			          	||data[i].name == "Event$createUser"
			          	  ||data[i].name == "Event$eventCisn") {
				data[i].readOnly = true;
				 data[i].hideTrigger=true;
			};
		}
		var biddata = this.split(newdata);
		this.mybuttons = this.getButtons();
		var cpanel = new Ext.form.FormPanel({
			id : 'cataPanel',
			layout : 'table',
			height : 450,
			width : 700,
			buttonAlign : 'center',
			buttons : this.mybuttons,
			bodyStyle : 'padding:4px,6px,0px,6px',
			autoScroll : true,
			frame : true,
			layoutConfig : {
				 columns:4
			  },
			 defaults : {
				bodyStyle : 'padding:5px'
			},
			items : biddata
		});
		return cpanel;
	},
	
	getButtons : function() {
		return [{
			xtype : 'button',
			style : 'margin:4px 4px 4px 0',
			text : '�ύ',
			scope : this,
			iconCls : 'submit',
			id : 'postButton',
			handler : function() {
				var eventtype=Ext.getCmp('eventtype');
				Ext.getCmp('postButton').disable();
				if (Ext.getCmp("cataPanel").getForm().isValid()) {
					if (eventtype.getValue() == "") {
						eventtype.clearValue();
						Ext.MessageBox.alert("��ʾ","��������б���ѡ����ȷ�������࣬������Ч��");
						Ext.getCmp("postButton").enable();
						return;
					}
					var panelparam = Ext.encode(getFormParam('cataPanel'));
					panelparam=unicode(panelparam);
					Ext.Ajax.request({
						waitTitle : "���Ժ�",
						waitMsg : " ���������ύ���Ժ�........",
						url : webContext + '/eventAction_resubmitEvent.action',
						method : "POST",
						params : {
							panelparam : panelparam,
							eventtype : Ext.getCmp("eventtype").getValue(),
							serviceItem : Ext.getCmp("serviceItem").getValue()
						},
						success : function(response, options) {
							var userID = eval('(' + response.responseText + ')').userID;
							var eventName = eval('(' + response.responseText + ')').eventName;
							var eventCisn = eval('(' + response.responseText + ')').eventCisn;
							var eventSubmitUser = eval('(' + response.responseText + ')').eventSubmitUser;
							var eventSubmitDate = eval('(' + response.responseText + ')').eventSubmitDate
							this.dataId = eval('(' + response.responseText + ')').eventId;
							var users = userID;
							if (users != null&&users!='') {//2010-04-28 modified by huzh for �й���ʦ���� 
								Ext.Ajax.request({
									url : webContext + '/eventWorkflow_apply.action',
									params : {
										dataId : this.dataId,
										model : this.model,
										bzparam : "{dataId :'" + this.dataId
												+ "',users:'engineerProcess:" + users
												+ "',applyId : '" + this.dataId
												+ "',eventName : '" + eventName
												+"',eventSubmitUser:'" +eventSubmitUser
												+"',eventSubmitDate:'" +eventSubmitDate
												+ "',eventCisn:'" + eventCisn
												+ "', applyType: 'eproject',applyTypeName: '�¼�����������',customer:'',workflowHistory:'com.zsgj.itil.event.entity.EventAuditHis'}",
										defname : 'eventAndProblemProcess1240370895640'
									},
									success : function(response, options) {
											Ext.Msg.alert( "��ʾ", "���ѳɹ��ύIT���⣬���ǻ���2��ʱ��������ϵ�������",
												function() {
													window.location=webContext+'/user/event/eventResubmit/eventResubmitSuccess.jsp';
												});
									},
									failure : function(response, options) {
										Ext.MessageBox.alert("��ʾ", "�����ύʧ�ܣ�");
										Ext.getCmp("postButton").enable();
									}
								}, this);
							} else {
								Ext.MessageBox.alert("��ʾ", "һ��֧���鹤��ʦҲû�У�");//2010-04-28 modified by huzh for û�й���ʦ
								Ext.getCmp("postButton").enable();
							}
						},
						failure : function(response, options) {
							Ext.MessageBox.alert("��ʾ", "�ύʧ�ܣ�");
							Ext.getCmp("postButton").enable();
						}
					}, this);

				} else {
					Ext.MessageBox.alert("��ʾ","��ɫ�����߲���Ϊ����������޷��ύ��");
					Ext.getCmp("postButton").enable();
				}
			}
		}]
	},
	split : function(data) {
		var labellen = 90;
		var itemlen = 200;
		var throulen = 480;
		if (Ext.isIE) {
			throulen = 450;
		} else {
			throulen = 490;
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
			if (data[i].xtype == "combo") {
				data[i].resizable = true;
			}
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
						data[i].height = 200;
						data[i].width = 488;
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
			if(data[i].id=="Event$summary"){
				data[i].width = 490;
			}
		}
		for (i = 0; i < hidd.length; i++) {
			longData[longData.length] = hidd[i];
		}
		return longData;
	},
	items : this.items,
	initComponent : function() {
		var items = new Array();
		items.push(this.getEventPanel());
		Ext.getCmp("eventtype").setValue("");
		this.items = items;
		PagePanel.superclass.initComponent.call(this);
	}
})
