//组长进行事件指派
EventAssignPanel = Ext.extend(Ext.Panel, {
	id : "eventAssignPanel",
//	title : '事件指派',
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
	
	//事件分配
	getEventAssign : function(eventAssignCombo, eventRemarkField) {
		var itemsTemp;
		var data = this.data;
		var assignItem = [{html: "事件分配给:",cls: 'common-text',style:'width:110;text-align:right'},
				eventAssignCombo,
				{html: "添加事件备注:",cls: 'common-text',style:'width:110;text-align:right'},
				eventRemarkField
				];
		var eventAssign = new Ext.form.FieldSet({
			//modify by awen for add promopt on 2009-9-1 begin
			title : '事件分配<font color="red">【请指派处理工程师】</font>',
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
	
	//事件备注
	getEventRemark : function() {
		var eventRemark = new Ext.form.FieldSet({
			title : '事件备注',
			width : 'auto',
			height : 'auto',
			defaults : {
				bodyStyle : 'padding:4px'
			},
			layout : 'table',
			layoutConfig : {
				columns : 2
			},
			items : [{html: "添加事件备注：&nbsp;",cls: 'common-text',style:'width:150;text-align:right'},
			{xtype : 'textarea', 
			name : 'remark', 
			fieldLabel : '事件备注', 
			width : 516,
			height : 100}]
			});
		return eventRemark;
	},
	
	//事件详情
	getEventDetails : function() {
		var eventDetails = new Ext.form.FieldSet({
					title : '事件详情',
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
			//add by lee for 为下拉列表增加可拖拽属性以修改不能看全信息的BUG in 20091112 BEGIN
			if (data[i].xtype == "combo") {
				data[i].resizable = true;
			}
			//add by lee for 为下拉列表增加可拖拽属性以修改不能看全信息的BUG in 20091112 END
			if (data[i].width == null || data[i].width == 'null'
					|| data[i].width == "") {
				data[i].style += "width:" + itemlen + "px";
				data[i].width = itemlen;
			} else {
				if (data[i].width == "9999") {//通栏  
					// alert("data");
					if ((i - hid + longitems) % 2 == 1) {//在右侧栏，前一栏贯通                                             
						longData[2 * (i - hid) - 1].colspan = 3;
					} else {//多占一栏
						longitems++;
					}
					data[i].colspan = 3;//本栏贯通                                            
					data[i].width = throulen;
					if (data[i].xtype == "textarea") {
						data[i].height = 150;
						data[i].width = 520;
					}
					data[i].style += "width:" + throulen + "px;";
				} else {//正常长度，半栏 
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
        this.data = this.split(data1);//4列form布局
		//工程师列表存储器
//		this.engineerStore = new Ext.data.JsonStore({
//			url : webContext + '/supportGroupAction_findCurrentGroupEngineers.action?eventId=' + eventId,
//			fields : ['id', 'userInfo'],
//			totalProperty : 'rowCount',
//			root : 'data',
//			id : 'engineerStore'
//		});
		//事件分配人列表
		var eventAssignCombo = new Ext.form.ComboBox({
			id : 'eventAssignCombo',
			fieldLabel : '事件分配给',
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
			emptyText : '请从下拉类表中选择...',
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
		//根据事件,处理人,按事件排序查出最后一个eventAssign
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
		var remarkTMP = remark;//临时保存rememark，提交工作流时进行比较，防止服务组长重新提交工程师remark
		//--------------end-------------------
		var eventRemarkField = new Ext.form.TextArea({ 
				name : 'remark', 
				fieldLabel : '事件备注', 
				width : 516,
				height : 100,
				value : remark});
		//事件详情与确认
		this.efp = new Ext.form.FormPanel({
			id : 'efp',
			title : '事件指派',
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
				    text : '提交', 
				    iconCls : 'submit', 
				    handler : function(){
					var engineerId = eventAssignCombo.getValue();
					var user = eventAssignCombo.getRawValue();
					var remark = eventRemarkField.getValue();
					Ext.getCmp('efp').disable();
					//提交
					if(user==''){
						Ext.MessageBox.alert('提示', '请指派工程师！');
						Ext.getCmp('efp').enable();
						return false;
					}
					//提交时对比事件备注
					remarkTMP = remarkTMP.replace(/\r\n/g, '');
					remark = remark.replace(/\r\n/g, '');
					if(remarkTMP == remark){
						Ext.MessageBox.alert('提示', '请重新填写分配备注！');
						Ext.getCmp('efp').enable();
						return false;
					}
					Ext.MessageBox.confirm('请确认', '事件重新指派给<font color=\'red\'>' + user + '</font>处理？', 
						function(button, text){
							if(button == 'yes'){
							//添加事件分配
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
										//------------------------------------------提交工作流----------------------------------------
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
												Ext.MessageBox.alert('提示','事件分配失败！');
											}
										} 
										//-------------------------------
									},
									failure:function(response,options){
										Ext.getCmp('efp').enable();
										Ext.MessageBox.alert('提示','事件分配失败！');
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
//				text : '清除', 
//				iconCls : 'reset', 
//				handler : function(){
//						Ext.getCmp('efp').form.reset();//注意：reset()为表单控件的重新加载后的初始值
//						//eventRemarkField.setValue('');
//					}
//				}
			//2010-09-25 modified by huzh end
			]
		});
		//事件已经有分配人就显示填写注释区域--二次回到服务组长面板
		this.items = [this.efp];
		for(var i = 0; i < this.data.length; i++){
			var headItem = this.data[i];
			if(headItem.id == 'Event$eventName' && headItem.value != '未命名事件'){
				this.items = [this.efp];
			}
		}
		EventAssignPanel.superclass.initComponent.call(this);
	}
});
