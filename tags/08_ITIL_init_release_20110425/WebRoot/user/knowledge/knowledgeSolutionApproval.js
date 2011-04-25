PageTemplates = Ext.extend(Ext.Panel, {
	id : "PageTemplates",
	layout : 'table',
	//height : 900,
	width:800,
	align : 'center',
	 closable : true,
	foredit : true,
	width : 'auto',
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
				name : 'knowledge',
				mapping : 'knowledge'
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
					knowledge : record.get('knowledge')
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
	getKnowledgeForm: function(knowledgeId) {
		var da = new DataAction();
		var	data = da.getSingleFormPanelElementsForEdit("KnowLedgeSolution_pagepanel", knowledgeId);// ����Ҫ��ʱ���
			//2010-04-20 add by huzh for ���ɱ༭ begin
			for(var i=0;i<data.length;i++){
				if(data[i].id!='Knowledge$summary'&&data[i].id!='Knowledge$resolvent'&&data[i].id!='Knowledge$knowProblemTypeCombo'){
					data[i].readOnly=true;
	                data[i].hideTrigger=true;
	                data[i].emptyText="";
				}
                if(data[i].name=="Knowledge$resolvent"){
                    if(data[i].value.indexOf("\"<p>")!=-1){
                        data[i].value = Ext.decode(data[i].value);
                    }
                }
                if(data[i].id=='Knowledge$knowProblemTypeCombo'){
                	data[i].store.on('beforeload',function(store, opt) {
						if (opt.params['Knowledge$knowProblemType'] == undefined) {
							opt.params['name'] = Ext.getCmp('Knowledge$knowProblemTypeCombo').defaultParam;
							opt.params['serviceItem'] = Ext.getCmp('Knowledge$serviceItemCombo').getValue();
							opt.params['deleteFlag'] = 0;
						}
					});
                }
			}
			//2010-04-20 add by huzh for ���ɱ༭ end
		var	biddata = da.split(data);
		this.knowFormPanel= new Ext.form.FormPanel({
			id : 'KnowLedgeSolution_pagepanel',
			layout : 'table',
			height : 300,
			width : 700,
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
				id : 'saveKnowledge',
				iconCls : 'submit',
				handler : function(){
					if(Ext.getCmp('Knowledge$summary').getValue().trim()==""){
						Ext.getCmp('Knowledge$summary').setValue('');
					}
					if (!Ext.getCmp('KnowLedgeSolution_pagepanel').form.isValid()) {
						Ext.MessageBox.alert("��ʾ", "����ɫ�����ߵĲ���Ϊ�����");
						return false;
					}
					Ext.getCmp('saveKnowledge').disable();
					Ext.getCmp('submitKnowledge').disable();
					if (Ext.getCmp("KnowLedgeSolution_pagepanel").getForm().isValid()) {
						var resolvent = Ext.encode(Ext.getCmp('Knowledge$resolvent').getValue());
						var knowledgeParam = Ext.encode(getFormParam('KnowLedgeSolution_pagepanel'));
						Ext.Ajax.request({
							url : webContext
									+ '/knowledgeAction_saveKnowledgeInApproval.action',
							params : {
								knowledgeParam : knowledgeParam,
								resolvent : resolvent.substring(1,resolvent.length-1)
							},
							method : 'post',
							success : function(response) {
								Ext.MessageBox.alert("��ʾ", "�����޸ĳɹ���",function(){
								Ext.getCmp('saveKnowledge').enable();
								Ext.getCmp('submitKnowledge').enable();
								});
							},
							failure : function(response, options) {
								Ext.getCmp('saveKnowledge').enable();
								Ext.getCmp('submitKnowledge').enable();
								Ext.MessageBox.alert("��ʾ", "�����޸�ʧ�ܣ�");
							}
						});
					}
				
			}},{
				xtype : 'button',
				text : '����',
				iconCls : 'submit',
				id : 'submitKnowledge',
				handler : function(){
					if(Ext.getCmp('Knowledge$summary').getValue().trim()==""){
						Ext.getCmp('Knowledge$summary').setValue('');
					}
					if (!Ext.getCmp('KnowLedgeSolution_pagepanel').form.isValid()) {
						Ext.MessageBox.alert("��ʾ", "����ɫ�����ߵĲ���Ϊ�����");
						return false;
					}
					Ext.getCmp('saveKnowledge').disable();
					Ext.getCmp('submitKnowledge').disable();
					if (Ext.getCmp("KnowLedgeSolution_pagepanel").getForm().isValid()) {
						var resolvent = Ext.encode(Ext.getCmp('Knowledge$resolvent').getValue());
						var knowledgeParam = Ext.encode(getFormParam('KnowLedgeSolution_pagepanel'));
						Ext.Ajax.request({
							url : webContext
									+ '/knowledgeAction_saveKnowledgeInApproval.action',
							params : {
								knowledgeParam : knowledgeParam,
								resolvent : resolvent.substring(1,resolvent.length-1)
							},
							method : 'post',
							success : function(response) {
								audit();
							},
							failure : function(response, options) {}
						});
					}
					
			}}],
			title : "�������������Ϣ",
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
			if (propertyName == 'id'||propertyName=="comment"|| propertyName == 'processName'|| propertyName == 'nodeId'||propertyName == 'alterFlag'||propertyName == 'knowledge') {
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
		var url = webContext+'/knowledgeAction_findProcessIdOfLatestProcess.action?kId=' + kplanId + '&kType=2'+"&time="+new Date();
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
	 items : this.items,
	 initComponent : function() {
		 var clazz = 'com.digitalchina.itil.knowledge.entity.KnowledgeAuditHis';
		this.picPanel = new Ext.Panel({
			title : '��������ͼ',
			layout : 'fit',
			height : 500,
			width : 760
		});
		this.hisGrid = this.getGrid(this.dataId,clazz,this.picPanel);
		var hisPanel = new Ext.Panel({
			title :  "������ʷ��<font style='font-weight:lighter' color=red>˫��������ʷ��Ŀ�ɲ鿴�������</font>��",
			width : 750,
			height : 200,
			layout : 'fit',
			border: true,
			items : this.hisGrid
		});
	   this.knowledgePanel=this.getKnowledgeForm(this.dataId);
	   //2010-05-17 modified by huzh for ��Ϊ֪ʶ����������ԭ֪ʶ��� begin
	   var auditStatusPanel=new Ext.Panel({ 
						xtype : 'panel',
						id : "first1",
						title : '����״̬',
						frame : true,
						border : true,
						height:"auto",
						width : 760,
						items : [hisPanel, this.picPanel]
		});
		var panelItem=new Array();
		panelItem.push(this.knowledgePanel);
		var oldKnowledge=Ext.getCmp("Knowledge$oldKnowledge").getValue();
		if(oldKnowledge!=""){//����ԭ���������Ϊ֪ʶ���
		   var oldpanel={title: 'ԭ�������������Ϣ',
			        	  height : 300,
			              autoLoad : {
							url : webContext + "/tabFrame.jsp?url=" + webContext
									+ "/user/knowledge/oldknowledge.jsp?dataId="+oldKnowledge,
							text : "ҳ�����ڼ�����......",
							method : 'post',
							scope : this
					      }
                     };
         	  panelItem.push(oldpanel);
			}
		panelItem.push(auditStatusPanel);	
	   this.tab  = new Ext.TabPanel({	
           // title:'���������ϸ��Ϣ',
			enableTabScroll : true,
			deferredRender : false,
			activeTab:0,
		    frame : true,
			plain : false,
			border : true,
			width : 750,
			height :"auto",
		    bodyBorder : true,
			items : panelItem
		});
		//2010-05-17 modified by huzh for ��Ϊ֪ʶ����������ԭ֪ʶ��� end
		this.items = [this.tab];
		PageTemplates.superclass.initComponent.call(this);
	 }
})