//�鳤�����¼�ָ��
EventAssignPanel = Ext.extend(Ext.Panel, {
	id : "eventAssignPanel",
//	title : '�¼�ָ��',
	layout : 'table',
	height : 'auto',
	width : 'auto',
	autoScroll : true,
	align : 'center',
	foredit : true,
	frame : true,
	defaults : {
		bodyStyle : 'padding:2px',
		overflow : 'auto'
	},
	layoutConfig : {
		columns : 1
	},
	items : this.items,
	
	//�¼�����
	getEventAssign : function(eventAssignCombo, eventRemarkField) {
		var itemsTemp;
		var data = this.data;
		var assignItem = [{html: "�¼������:",cls: 'common-text',style:'width:110;text-align:right'},
				eventAssignCombo,
				{html: "����¼���ע:",cls: 'common-text',style:'width:110;text-align:right'},
				eventRemarkField
				];
		var eventAssign = new Ext.form.FieldSet({
			//modify by awen for add promopt on 2009-9-1 begin
			title : '�¼�����<font color="red">����ָ�ɴ�����ʦ��</font>',
			//modify by awen for add promopt on 2009-9-1 end
			width : 700,
			height : 'auto',
			defaults : {
				bodyStyle : 'padding:4px'
			},
			layout : 'table',
			layoutConfig : {
				columns : 2
			},
			items : assignItem								
		});
		return eventAssign;
	},
	
	//�¼���ע
	getEventRemark : function() {
		var eventRemark = new Ext.form.FieldSet({
			title : '�¼���ע',
			width : 'auto',
			height : 'auto',
			defaults : {
				bodyStyle : 'padding:4px'
			},
			layout : 'table',
			layoutConfig : {
				columns : 2
			},
			items : [{html: "����¼���ע��&nbsp;",cls: 'common-text',style:'width:150;text-align:right'},
			{xtype : 'textarea', 
			name : 'remark', 
			fieldLabel : '�¼���ע', 
			width : 516,
			height : 100}]
			});
		return eventRemark;
	},
	
	//�¼�����
	getEventDetails : function() {
		var eventDetails = new Ext.form.FieldSet({
					title : '�¼�����',
					width : 700,
					height : 'auto',
					defaults : {
						bodyStyle : 'padding:4px'
					},
					layout : 'table',
					layoutConfig : {
						columns : 4
					},
					items : Ext.getCmp('eventAssignPanel').data
				})
		return eventDetails;		
	},
	split : function(data) {
		var labellen = 110;
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
		//alert(this.dataId);         
		for (i = 0; i < data.length; i++) {

			data[i].style = data[i].style == null ? "" : data[i].style;
			if (data[i].xtype == "textarea") {
				data[i].style += "'margin:5 0 5 0;'";
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
			//add by lee for Ϊ�����б����ӿ���ק�������޸Ĳ��ܿ�ȫ��Ϣ��BUG in 20091112 END
			if (data[i].width == null || data[i].width == 'null'
					|| data[i].width == "") {
				data[i].style += "width:" + itemlen + "px";
				data[i].width = itemlen;
			} else {
				if (data[i].width == "9999") {//ͨ��  
					// alert("data");
					if ((i - hid + longitems) % 2 == 1) {//���Ҳ�����ǰһ����ͨ                                             
						longData[2 * (i - hid) - 1].colspan = 3;
					} else {//��ռһ��
						longitems++;
					}
					data[i].colspan = 3;//������ͨ                                            
					data[i].width = throulen;
					if (data[i].xtype == "textarea") {
						data[i].height = 150;
						data[i].width = 520;
					}
					data[i].style += "width:" + throulen + "px;";
				} else {//�������ȣ����� 
					data[i].style += "width:" + itemlen + "px";
					data[i].width = itemlen;
				}
			}
			//alert(data[i].width+data[i].name);
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
	
	//
	initComponent : function() {
		var da = new DataAction();
       // var data1= da.getSingleFormPanelElementsForEdit("page_event_suppGroupLeader", "panel_event_suppGroupLeader", eventId);
        var data1= da.getSingleFormPanelElementsForEdit("panel_event_suppGroupLeader", eventId);
			for(var i=0;i<data1.length;i++){
			data1[i].readOnly=true;
			data1[i].hideTrigger=true;
			data1[i].emptyText="";
		}
        this.data = this.split(data1);//4��form����
		//����ʦ�б�洢��
//		this.engineerStore = new Ext.data.JsonStore({
//			url : webContext + '/supportGroupAction_findCurrentGroupEngineers.action?eventId=' + eventId,
//			fields : ['id', 'userInfo'],
//			totalProperty : 'rowCount',
//			root : 'data',
//			id : 'engineerStore'
//		});
		//�¼��������б�
		var eventAssignCombo = new Ext.form.ComboBox({
			id : 'eventAssignCombo',
			fieldLabel : '�¼������',
			displayField : 'userInfo',
			lazyRender : true,
			valueField : 'id',
			allowBlank : false,
			listWidth  :300,
			width : 300,
			hiddenName : 'userInfo',
			resizable : true,
			triggerAction : 'all',
			selectOnFocus : true,
			forceSelection : true,
			emptyText : '������������ѡ��...',
			mode : 'remote',
			pageSize : 10,
			name : 'eventAssign',
			store : new Ext.data.JsonStore({
				url : webContext + '/supportGroupAction_findCurrentGroupEngineers.action?eventId=' + eventId,
				fields : ['id', 'userInfo'],
				totalProperty : 'rowCount',
				root : 'data'
			}),
			listeners : {
				'beforequery' : function(queryEvent) {
					var param = queryEvent.combo.getRawValue();
					if (queryEvent.query == '') {
						param = '';
					}
					this.store.load({
						params : {
							itcode : param,
							start : 0
						}
					});
					return false;
				}
			}	
		});
		Ext.getCmp("eventAssignCombo").setValue("");
		//�����¼�,������,���¼����������һ��eventAssign
		var remark;
		var url = webContext+'/eventAction_findLatestEventAssign.action?eventId=' + eventId;
		var conn = Ext.lib.Ajax.getConnectionObject().conn;
		conn.open("get", url, false);
		conn.send(null);
		if (conn.status == "200") {
			var responseText = conn.responseText;
			responseText = responseText.replace(/\r\n/g, '\\r\\n');
			responseText = responseText.substr(0,responseText.lastIndexOf('}') + 1);
			var data = Ext.decode(responseText);
			remark = data.remark;
		} 
		//-------------begin------------------
		var remarkTMP = remark;//��ʱ����rememark���ύ������ʱ���бȽϣ���ֹ�����鳤�����ύ����ʦremark
		//--------------end-------------------
		var eventRemarkField = new Ext.form.TextArea({ 
				name : 'remark', 
				fieldLabel : '�¼���ע', 
				width : 516,
				height : 100,
				value : remark});
		//�¼�������ȷ��
		this.efp = new Ext.form.FormPanel({
			id : 'efp',
			title : '�¼�ָ��',
			layout : 'table',
			height : 'auto',
			width : 750,
			frame : true,
			autoScroll : true,
			defaults : {
				bodyStyle : 'padding:4px',
				overflow : 'auto'
			},
			layoutConfig : {
				columns : 1
			},	
			items : [Ext.getCmp('eventAssignPanel').getEventAssign(eventAssignCombo, eventRemarkField),
					 Ext.getCmp('eventAssignPanel').getEventDetails()
				],
			buttonAlign : 'center',
			buttons : [
				    {xtype : 'button', 
				    text : '�ύ', 
				    iconCls : 'submit', 
				    handler : function(){
					var engineerId = eventAssignCombo.getValue();
					var user = eventAssignCombo.getRawValue();
					var remark = eventRemarkField.getValue();
					Ext.getCmp('efp').disable();
					//�ύ
					if(user==''){
						Ext.MessageBox.alert('��ʾ', '��ָ�ɹ���ʦ��');
						Ext.getCmp('efp').enable();
						return false;
					}
					//�ύʱ�Ա��¼���ע
					remarkTMP = remarkTMP.replace(/\r\n/g, '');
					remark = remark.replace(/\r\n/g, '');
					if(remarkTMP == remark){
						Ext.MessageBox.alert('��ʾ', '��������д���䱸ע��');
						Ext.getCmp('efp').enable();
						return false;
					}
					Ext.MessageBox.confirm('��ȷ��', '�¼�����ָ�ɸ�<font color=\'red\'>' + user + '</font>����', 
						function(button, text){
							if(button == 'yes'){
							//����¼�����
								Ext.Ajax.request({
									url: webContext+'/eventAction_eventAssign.action',
									params: {
												eventId : eventId,
												userId : engineerId,
												remark : Ext.encode(remark)
											},
									method:'post', 
									success:function(response,options){
										var users = engineerId;
										//------------------------------------------�ύ������----------------------------------------
										var url = webContext+'/extjs/workflow?method=getDataFromPage&taskId=' + taskId + '&next=reAssign' + '&users=engineerProcess:' + users;
										var conn = Ext.lib.Ajax.getConnectionObject().conn;
										conn.open("get", url, false);
										conn.send(null);
										if (conn.status == "200") {
											var responseText = conn.responseText;
											var data = Ext.decode(responseText);
											if(data.success){
												window.parent.auditContentWin.close();
											}else{
												Ext.getCmp('efp').enable();
												Ext.MessageBox.alert('��ʾ','�¼�����ʧ�ܣ�');
											}
										} 
										//-------------------------------
									},
									failure:function(response,options){
										Ext.getCmp('efp').enable();
										Ext.MessageBox.alert('��ʾ','�¼�����ʧ�ܣ�');
									}
								});
							}else{
								Ext.getCmp('efp').enable();
							}
						});
					}
				}
			//2010-09-25 modified by huzh begnin
//				,{xtype : 'button', 
//				text : '���', 
//				iconCls : 'reset', 
//				handler : function(){
//						Ext.getCmp('efp').form.reset();//ע�⣺reset()Ϊ���ؼ������¼��غ�ĳ�ʼֵ
//						//eventRemarkField.setValue('');
//					}
//				}
			//2010-09-25 modified by huzh end
			]
		});
		//�¼��Ѿ��з����˾���ʾ��дע������--���λص������鳤���
		this.items = [this.efp];
		for(var i = 0; i < this.data.length; i++){
			var headItem = this.data[i];
			if(headItem.id == 'Event$eventName' && headItem.value != 'δ�����¼�'){
				this.items = [this.efp];
			}
		}
		EventAssignPanel.superclass.initComponent.call(this);
	}
});
