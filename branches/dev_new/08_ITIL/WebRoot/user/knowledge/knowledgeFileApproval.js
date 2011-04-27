PageTemplates = Ext.extend(Ext.Panel, {
	id : "PageTemplates",
	layout : 'table',
	//height : 'auto',
	align : 'center',
	foredit : true,
	width:800,
	//width : 'auto',
	frame : true,
	autoScroll : true,
//	defaults : {
//		bodyStyle : 'padding:4px'
//	},
	layoutConfig : {
		columns : 1
	},
	testfun : function() {
		var sto = this.hisGrid.getStore();
		var record = sto.getAt(sto.getTotalCount()-1);
		if (record == undefined) {
			this.picPanel.add(new Ext.Panel({
				html : 'û�п���ʾ������ͼ',
				laytou : 'fit',
				frame : true,
				align : 'center'
			}));
			this.picPanel.doLayout();
			return;
		}
		var pid = record.get('processId');
		var url = webContext + '/extjs/workflow?method=next&procid=' + pid;
		var da = new DataAction();
		var data = da.ajaxGetData(url);//��ѯ�����Ľ��
		var dataClass = [{
				name : 'knowFile',
				mapping : 'knowFile'
			},{
				name : 'processId',
				mapping : 'processId'
			},  {
				name : 'comment',
				mapping : 'comment'
			}, {
				name : 'resultFlag',
				mapping : 'resultFlag'
			},{
				name : 'nodeName',
				mapping : 'nodeName'
			}, {
				name : 'approver',
				mapping : 'approver'
			}, {
				name : 'approverDate',
				mapping : 'approverDate'
			}];
		var gridRecord = Ext.data.Record.create(dataClass);
		
		if (data[0]!=undefined) {
			var dataRecord = new gridRecord({
					processId : data[0].processId,
					nodeName : data[0].nodeName,
					approver : data[0].actorId,
					resultFlag : '���ȴ�����',
					knowledge : record.get('knowFile')
				});
			sto.add([dataRecord]);		
			var url = webContext + "/workflow/history.do?pid=" + pid
					+ "&methodCall=view";
					
			this.picPanel.add(new Ext.Panel({
				html : '<iframe id="myframebidhis" frameborder="no" myframebidhis="ifr"width="100%" height="500" scrolling="auto" src='
					+ url + '></iframe>'
			}));
			this.picPanel.doLayout();
		}
	},
	getKnowFileForm: function(knowfileId) {
		var da = new DataAction();
		var	data = da.getSingleFormPanelElementsForEdit("KnowLedgeFile_pagepanel", knowfileId);// ����Ҫ��ʱ���
				for(var i=0;i<data.length;i++){
					if(data[i].id=='KnowFile$number'){
						data[i].readOnly=true;
		                data[i].hideTrigger=true;
		                data[i].emptyText="";
					}
				}
		var biddata = this.split(data);
		this.knowFormPanel= new Ext.form.FormPanel({
			id : 'KnowLedgeFile_pagepanel',
			layout : 'table',
			height : 300,
			width : 300,
			frame : true,
			autoScroll : true,
			defaults : {
				bodyStyle : 'padding:2px'
			},
			layoutConfig : {
				columns : 4
			},
			buttonAlign : 'center',
			buttons :[{
					xtype : 'button',
					text : '����',
					iconCls : 'submit',
					id : 'saveKnowFile',
					handler : function(){
						if(Ext.getCmp('KnowFile$name').getValue().trim()==""){
							Ext.getCmp('KnowFile$name').setValue('');
						}
						if (!Ext.getCmp('KnowLedgeFile_pagepanel').form.isValid()) {
							Ext.MessageBox.alert("��ʾ", "����ɫ�����ߵĲ���Ϊ�����");
							return false;
						}
						Ext.getCmp('saveKnowFile').disable();
						Ext.getCmp('submitKnowFile').disable();
						if (Ext.getCmp("KnowLedgeFile_pagepanel").getForm().isValid()) {
							var descn = Ext.encode(Ext.getCmp('KnowFile$descn').getValue());
							var knowFileParam = Ext.encode(getFormParam('KnowLedgeFile_pagepanel'));
							Ext.Ajax.request({
								url : webContext
										+ '/knowledgeAction_saveKnowFileInApproval.action',
								params : {
									knowFileParam : knowFileParam,
									descn : descn.substring(1,descn.length-1)
								},
								method : 'post',
								success : function(response) {
									Ext.MessageBox.alert("��ʾ", "�����޸ĳɹ���",function(){
									Ext.getCmp('saveKnowFile').enable();
									Ext.getCmp('submitKnowFile').enable();
									});
								},
								failure : function(response, options) {
									Ext.getCmp('saveKnowFile').enable();
									Ext.getCmp('submitKnowFile').enable();
									Ext.MessageBox.alert("��ʾ", "�����޸�ʧ�ܣ�");
								}
							});
						}
				}},{
					xtype : 'button',
					text : '����',
					iconCls : 'submit',
					id : 'submitKnowFile',
					handler : function(){
						if(Ext.getCmp('KnowFile$name').getValue().trim()==""){
							Ext.getCmp('KnowFile$name').setValue('');
						}
						if (!Ext.getCmp('KnowLedgeFile_pagepanel').form.isValid()) {
							Ext.MessageBox.alert("��ʾ", "����ɫ�����ߵĲ���Ϊ�����");
							return false;
						}
						Ext.getCmp('saveKnowFile').disable();
						Ext.getCmp('submitKnowFile').disable();
						if (Ext.getCmp("KnowLedgeFile_pagepanel").getForm().isValid()) {
							var descn = Ext.encode(Ext.getCmp('KnowFile$descn').getValue());
							var knowFileParam = Ext.encode(getFormParam('KnowLedgeFile_pagepanel'));
							Ext.Ajax.request({
								url : webContext
										+ '/knowledgeAction_saveKnowFileInApproval.action',
								params : {
									knowFileParam : knowFileParam,
									descn : descn.substring(1,descn.length-1)
								},
								method : 'post',
								success : function(response) {
										audit();
								},
								failure : function(response, options) {}
							});
						}
				}}],
			title : "�ļ�������Ϣ",
			items : biddata
		});
		return this.knowFormPanel;
	},
	
	getGrid : function(kplanId, clazz, picPanel) {
		var pPanel = picPanel;
		var da = new DataAction();
		var obj = da.getElementsForHead(clazz);

		var sm = new Ext.grid.CheckboxSelectionModel();
		var columns = new Array();
		var fields = new Array();

		columns[0] = sm;
		for (var i = 0; i < obj.length; i++) {
			var headItem = obj[i];
			var title = headItem.header;
			var alignStyle = 'left';

			var propertyName = headItem.dataIndex;

			var isHidden = false;
			//2010-04-24 modified by huzh for ����ĳЩ�� begin
			if (propertyName == 'id'||propertyName=="comment"|| propertyName == 'processName'|| propertyName == 'nodeId'||propertyName == 'alterFlag'||propertyName == 'knowFile') {
				isHidden = true;
			}
			//2010-04-24 modified by huzh for ����ĳЩ�� begin
			var columnItem = {
				header : title,
				dataIndex : propertyName,
				sortable : true,
				hidden : isHidden,
				align : alignStyle
			};
						//2010-04-24 add by huzh for �е��� begin
			if(propertyName=="approver"){
				columnItem.width=200;
			}
			if(propertyName=="comment"){
				columnItem.width=160;
			}
			if(propertyName=="resultFlag"){
				columnItem.width=100;
			}
			if(propertyName=="approverDate"){
				columnItem.width=160;
			}
			if(propertyName=="processId"){
				columnItem.width=90;
			}
			if(propertyName=="nodeName"){
				columnItem.width=160;
			}
			//2010-04-24 add by huzh for �е��� end
			columns[i + 1] = columnItem;
			fields[i] = propertyName;
		}

		this.storeMapping = fields;
		this.cm = new Ext.grid.ColumnModel(columns);

		this.store = da.getJsonStore(clazz);

		this.cm.defaultSortable = true;

		var viewConfig = Ext.apply({
			forceFit : true
		}, this.gridViewConfig);

		this.formValue = '';

		this.grid = new Ext.grid.GridPanel({
			store : this.store,
			cm : this.cm,
			sm : sm,
			trackMouseOver : false,
			loadMask : true,
			border: true,
			height : 700,
			width  :800
			//tbar : [new Ext.Toolbar.TextItem('<font color=red>��ʾ��</font><font color=blue>˫��������ʷ��Ŀ�ɲ鿴�������</font>')]
		});
		this.grid.on("headerdblclick", this.fitWidth, this);
		this.grid.on("celldblclick",function(g,row){
			if(g.getStore().getAt(row).get('comment')==undefined){
			  }else{
				Ext.Msg.show({
				   title:'�������',
				   msg:'�����ˣ�'+g.getStore().getAt(row).get('approver')+'<br>'+'������ƣ�'+g.getStore().getAt(row).get('nodeName')+'<br>'+'��������� '+g.getStore().getAt(row).get('comment'),
				   buttons: Ext.Msg.OK,
				   animEl: 'elId',
				   icon: Ext.MessageBox.INFO
				});
			}
		},this);
		var param = {
			'start' : 0
		};
		param.methodCall = 'query';
		param.start = 0;
		var pId;
		var url = webContext+'/knowledgeAction_findProcessIdOfLatestProcess.action?kId=' + kplanId + '&kType=1'+"&time="+new Date();
		var conn = Ext.lib.Ajax.getConnectionObject().conn;
		conn.open("get", url, false);
		conn.send(null);
		if (conn.status == "200") {
			var responseText = conn.responseText;
			var data = Ext.decode(responseText);
			pId = data.processId;
		} 
		param.knowledge = kplanId;
		param.processId = pId;
		param.isAsc=true;
		this.store.removeAll();
		this.store.on('load', this.testfun, this);
		this.store.load({
			params : param
		});
		return this.grid;
	},
	split : function(data) {
		var labellen = 135;
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
	 items : this.items,
	 initComponent : function() {
		 var clazz = 'com.zsgj.itil.knowledge.entity.KnowFileAuditHis';
		this.picPanel = new Ext.Panel({
			title : '��������ͼ',
			layout : 'fit',
			height : 500,
			width : 780
		});
		this.hisGrid = this.getGrid(this.dataId,clazz,this.picPanel);
		var hisPanel = new Ext.Panel({
			title :  "������ʷ��<font style='font-weight:lighter' color=red>˫��������ʷ��Ŀ�ɲ鿴�������</font>��",
			width : 780,
			height : 200,
			layout : 'fit',
			border: true,
			items : this.hisGrid
		});
	   this.knowFilePanel=this.getKnowFileForm(this.dataId);
	   //2010-05-17 modified by huzh for ��Ϊ֪ʶ����������ԭ֪ʶ��Ϣ��� begin
	   var panelItem=new Array();
		panelItem.push(this.knowFilePanel);
		var oldKnowFile=Ext.getCmp("KnowFile$oldKnowFile").getValue();
		if(oldKnowFile!=""){//����ԭ�ļ���Ϊ֪ʶ���
		  var oldpanel={  
	                  title: 'ԭ�ļ�������Ϣ',
		        	  height : 350,
		              autoLoad : {
						url : webContext + "/tabFrame.jsp?url=" + webContext
								+ "/user/knowledge/oldknowFile.jsp?dataId="+oldKnowFile,
						text : "ҳ�����ڼ�����......",
						method : 'post',
						scope : this
				      }
                 };
         	  panelItem.push(oldpanel);
		}
		 var auditStatusPanel=new Ext.Panel({
					xtype : 'panel',
					id : "first1",
					title : '����״̬',
					frame : true,
					border : true,
					height:"auto",
					width : 780,
					items : [hisPanel, this.picPanel]
				});
		panelItem.push(auditStatusPanel);
	   this.tab  = new Ext.TabPanel({	
           //title:'�ļ���ϸ��Ϣ',
			enableTabScroll : true,
			deferredRender : false,
			activeTab:0,
		    frame : true,
			plain : false,
			border : true,
			width : 753,
			height :"auto",
		    bodyBorder : true,
			items : panelItem
		});
		//2010-05-17 modified by huzh for ��Ϊ֪ʶ����������ԭ֪ʶ��Ϣ��� end
		this.items = [this.tab];
		PageTemplates.superclass.initComponent.call(this);
	 }
})