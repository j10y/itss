PagePanel = Ext.extend(Ext.Panel, {
	id : "KnowledgeManager",
	closable : true,
	frame : true,
	viewConfig : {
		autoFill : true,
		forceFit : true 
	},
	layout : 'border',
	getknowSearchForm : function() {
		var serviceItemBySu = new Ext.form.ComboBox({
			name : "serviceItemBySu",
			id : 'serviceItemBySu',
			fieldLabel : "������",
			//width : 180,
			width : 200, //2010-04-16 modified by huzh for bug(��ҳ�����������һҳ�������ʾ������)
			hiddenName : 'serviceItem',
			displayField : 'name',
			valueField : 'id',
			lazyRender : true,
			minChars : 50,
			emptyText :'��������б���ѡ��...',
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
			listeners : {
				'beforequery' : function(queryEvent) {
					Ext.getCmp('problemTypebySu').clearValue();
//					var discValue = Ext.getCmp('serviceItemTypebySu').getValue();
					var param = queryEvent.combo.getRawValue();
					var val = queryEvent.combo.getValue();
					if (queryEvent.query == '') {
						param = '';
					}
					this.store.baseParams={name : param ,official:1};
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
			//width : 180,
			width : 200, //2010-04-16 modified by huzh for bug(��ҳ�����������һҳ�������ʾ������)
			hiddenName : 'knowProblemType',
			displayField : 'name',
			valueField : 'id',
			lazyRender : true,
			minChars : 50,
			emptyText :'��������б���ѡ��...',
			resizable : true,
			triggerAction : 'all',
			selectOnFocus : true,
			store : new Ext.data.JsonStore({
				url : webContext  + '/knowledgeAction_findKnowProblemType.action',
				fields : ['id', 'name'],
				totalProperty : 'rowCount',
				root : 'data',
				id : 'id'
			}),
			pageSize : 10,
			listeners : {
				'beforequery' : function(queryEvent) {
					var discValue = Ext.getCmp('serviceItemBySu').getValue();
					var param = queryEvent.combo.getRawValue();
					var val = queryEvent.combo.getValue();
					if (queryEvent.query == '') {
						param = '';
					}
					this.store.baseParams={
							"name" : param,
							serviceItem : discValue,
							deleteFlag : 0 //������ɾ������������
						};
					this.store.load();
					return false;
				}
				
			}
		});
		var summary = new Ext.form.TextField({
			name : "summary",
			fieldLabel : "��������",
			id : 'summary',
			//width : 180
			width : 200
		});
		this.panel = new Ext.form.FormPanel({
			id:"knowSearchForm",
			region : "north", 
			layout : 'table',
			height : 60,
			frame : true,
			collapsible : true,
			defaults : {
				bodyStyle : 'padding:2px'
			},
			layoutConfig : {
				columns : 6
			},
			title : "��ѯ����",
			items : [{
				html : "������:",
				cls : 'common-text',
				width : 80,
				style : 'width:150;text-align:right'
			}, serviceItemBySu, {
				html : "��������:",
				cls : 'common-text',
				width : 80,
				style : 'width:150;text-align:right'
			}, problemTypebySu, {
				html : "��������:",
				cls : 'common-text',
				width : 80,
				style : 'width:150;text-align:right'
			}, summary]
		});
		return this.panel;
	},
	show:function(){ 
            var record =  Ext.getCmp('konwledgeGrid').getSelectionModel().getSelected();
			var records =  Ext.getCmp('konwledgeGrid').getSelectionModel().getSelections();
			if(!record){
				Ext.Msg.alert("��ʾ","����ѡ��Ҫ�鿴���У�");
				return;
			}
			if(records.length>1){
				Ext.Msg.alert("��ʾ","�鿴ʱֻ��ѡ��һ�У�");
				return;
			}
			var knowledgeId=record.get('Knowledge$id');	
	        var da = new DataAction();
	        var data=da.getKnowledgeFormPanelElementsForEdit("KnowLedgeSolution_pagepanel",knowledgeId);
	       	//2010-06-30 modified by huzh begin
	       	for(var i=0;i<data.length;i++){
	       		if(data[i].id=="Knowledge$serviceItemCombo"){
	       			data[i].on("select",function(){
			    	  Ext.getCmp("Knowledge$knowProblemTypeCombo").clearValue();
	       			});
	       			data[i].on("beforequery",function(queryEvent){
							var param = queryEvent.combo.getRawValue();
							if (queryEvent.query == '') {
								param = '';
							}
							this.store.baseParams={
								name : param,
								official : 1
							}
							this.store.load({
								params:{
									start:0
								}
							});
							return false;
			   		 });
			    }
	       		if(data[i].id=="Knowledge$knowProblemTypeCombo"){
	       				data[i].on("beforequery",function(queryEvent){
							var serviceitem=Ext.getCmp("Knowledge$serviceItemCombo").getValue();
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
			   			 });
	       		}
       		}
	        //2010-06-30 modified by huzh end
	        var dataform = this.split(data);
	        var knowForm = new Ext.form.FormPanel({
	             id:'knowledgeSkip',
		         layout : 'table',
		         width : 630,     //2010-04-20 modified by huzh 
		         height : 'auto',
		         layoutConfig : {
			           columns : 4
		             },
		          defaults : {
			        bodyStyle : 'padding:6px'
		         },
		         frame : true,
		         items : dataform
	           });
			var windowSkip = new Ext.Window({
				title : '�鿴��ϸ��Ϣ',
				width : 650,  //2010-04-20 modified by huzh 
				height :'auto',
				modal : true,
				//maximizable : true,
				items : [knowForm],
				bodyStyle : 'padding:2px',
				buttons : [{
				text : '�ݴ���',
				id : 'savelistbutton',
				handler : function() {
					var si=Ext.getCmp('Knowledge$serviceItemCombo');
					var kt=Ext.getCmp('Knowledge$knowProblemTypeCombo');
					var su=Ext.getCmp('Knowledge$summary');
					if(su.getValue().trim()==""){
						su.setValue('');
					}
					if(si.getValue().trim()==""){
						si.clearValue();
					}
					if(kt.getValue().trim()==""){
						kt.clearValue();
					}
					Ext.getCmp("savelistbutton").disable();
					Ext.getCmp("submitknowbutton").disable();
					Ext.getCmp("closebutton").disable();
					if (Ext.getCmp("knowledgeSkip").getForm().isValid()) {
						var panelTypeParam = Ext.encode(getFormParam("knowledgeSkip"));
						var resolvent = Ext.encode(Ext.getCmp('Knowledge$resolvent').getValue());
						Ext.Ajax.request({
							url : webContext+ '/knowledgeAction_saveKnowledgeChangDraft.action',
							params : {
								panelTypeParam : panelTypeParam,
								resolvent : resolvent.substring(1,resolvent.length-1)
							},
							method : 'post',
							success : function(response) {
								Ext.MessageBox.alert("��ʾ", "���������ݳɹ���",function(){
									windowSkip.close();
								});
								var params = Ext.getCmp("knowSearchForm").getForm().getValues(false);
								var store=Ext.getCmp("konwledgeGrid").getStore();
						        params.start = 0;  
						        params.status=1;
//						        Ext.getCmp("pagetoolBar").formValue=param;
						        store.on('beforeload', function(a) {   
								      Ext.apply(a.baseParams,params);   
								});
						        store.load({
						            params : params
						        });
								
							},
							failure : function(response, options) {
								Ext.MessageBox.alert("��ʾ", "����������ʧ�ܣ�");
								Ext.getCmp("savelistbutton").enable();
								Ext.getCmp("submitknowbutton").enable();
								Ext.getCmp("closebutton").enable();
							}
						});
					}else{
						 Ext.MessageBox.alert("��ʾ", "��ɫ�����߲���Ϊ�����"); 
						 Ext.getCmp("savelistbutton").enable();
						 Ext.getCmp("submitknowbutton").enable();
						 Ext.getCmp("closebutton").enable();
						 return;
					}
				},
				scope : this
			}, {
				text : '�ύ���',
				id : 'submitknowbutton',
				handler : function() {
					var si=Ext.getCmp('Knowledge$serviceItemCombo');
					var kt=Ext.getCmp('Knowledge$knowProblemTypeCombo');
					var su=Ext.getCmp('Knowledge$summary');
					if(su.getValue().trim()==""){
						su.setValue('');
					}
					if(si.getValue().trim()==""){
						si.clearValue();
					}
					if(kt.getValue().trim()==""){
						kt.clearValue();
					}
					Ext.getCmp("savelistbutton").disable();
					Ext.getCmp("submitknowbutton").disable();
					Ext.getCmp("closebutton").disable();
					if (Ext.getCmp("knowledgeSkip").getForm().isValid()) {
//						var panelTypeParam = Ext.encode(Ext
//								.getCmp("knowledgeSkip").form.getValues(false));
						var panelTypeParam = Ext.encode(getFormParam('knowledgeSkip'));
						var resolvent = Ext.encode(Ext.getCmp('Knowledge$resolvent').getValue());
						Ext.Ajax.request({
							url : webContext
									+ '/knowledgeAction_saveKnowledgeChangDraft.action',
							params : {
								knowledgeId : knowledgeId,
								panelTypeParam : panelTypeParam,
								resolvent : resolvent.substring(1,resolvent.length-1)
							},
							method : 'post',
							success : function(response) {
								var r =Ext.decode(response.responseText);
                               if(r.success==false){
                             	 Ext.MessageBox.alert("��ʾ", "�ý�������ѱ�����������ڱ�������У��������ٴζ����ύ������룡",function(){
								}); 
                               }else{
									 var dataId = eval('('
											+ response.responseText + ')').dataId;
								//---------------------------------------------
								//�ύ���̣���ͬ������������ļ���
									var dataType = 8;//��������Id
									Ext.Ajax.request({
											url : webContext
													+ '/knowledgeWorkflow_apply.action',
											params : {
												dataId : dataId,
												model : this.model,
												bzparam : "{dataId :'"
														+ dataId
														+ "',dataType : '"
														+ dataType
														+ "',applyId : '"
														+ dataId
														+ "', applyType: 'kproject',applyTypeName: '֪ʶ�������',customer:''}",
											    defname : "KnowledgeChangeProcess1276415967871"
											},
											success:function(response){
												var meg = Ext.decode(response.responseText);
												if (meg.Exception != undefined) {
													Ext.Msg.alert("��ʾ",meg.Exception);
												}else{
													Ext.Msg.alert("��ʾ","�ύ�������ɹ���",function(){
														windowSkip.close();
													});
													var param = Ext.getCmp("knowSearchForm").getForm().getValues(false);
													var store=Ext.getCmp("konwledgeGrid").getStore();
											        param.start = 1;  
											        param.status=1;
											        Ext.getCmp("pagetoolBar").formValue=param;
											        store.load({
											            params : param
											        });
												}
											}
									});
	                             }
								
							},
							failure : function(response, options) {
								Ext.getCmp("savelistbutton").enable();
								Ext.getCmp("submitknowbutton").enable();
								Ext.getCmp("closebutton").enable();
								Ext.MessageBox.alert("��ʾ", "����������ʧ�ܣ�");
							}
						});

					}else{
						 Ext.getCmp("savelistbutton").enable();
						Ext.getCmp("submitknowbutton").enable();
						Ext.getCmp("closebutton").enable();
					 Ext.MessageBox.alert("��ʾ", "��ɫ�����߲���Ϊ�����"); 
					}
				},
				scope : this
			}, {
				text : '�ر�',
				id : 'closebutton',
				handler : function() {
					windowSkip.close();
				},
				scope : this
			}]
			  });
	        windowSkip.show();
      },
      split : function(data) {
		var labellen = 90;  //2010-04-20 modified by huzh 
		var itemlen = 200;
		var throulen = 300;
		if (Ext.isIE) {
			throulen = 300;
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
	initComponent : function(){
		var da = new DataAction();
		
		var DataHead=da.getListPanelElementsForHead("KnowLedgeSolutionList_pagepanel");
		this.modelTableName="Knowledge";  
		var sm = new Ext.grid.CheckboxSelectionModel();
		var columns = new Array();
		var fields = new Array();
		columns[0] = sm;
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
            if(propertyName=="Knowledge$status"
            	||propertyName=="Knowledge$knowledgeCisn"
           		  ||propertyName=="Knowledge$createUser"
           			||propertyName=="Knowledge$createDate"){
				isHiddenColumn = false;  
			}
			var columnItem = {
				header : title,
				dataIndex : propertyName,
				sortable : true,
				hidden : isHiddenColumn,
				align : alignStyle
			}
			if(propertyName=="Knowledge$createUser"){
				columnItem.width=100;
			}
			if(propertyName=="Knowledge$createDate"){
				columnItem.width=120;
			}
			if(propertyName=="Knowledge$summary"){
				columnItem.width=200;
			}
			if(propertyName=="Knowledge$id"){
				columnItem.width=70;
			}
			if(propertyName=="Knowledge$knowProblemType"){
				columnItem.width=180;
			}
			if(propertyName=="Knowledge$serviceItem"){
				columnItem.width=180;
			}
			columns[i + 1] = columnItem;
			fields[i] = propertyName;
		}
		this.storeMapping = fields;
		this.cm = new Ext.grid.ColumnModel(columns);
		this.store= da.getPanelJsonStore("KnowLedgeSolutionList_pagepanel", DataHead);
		this.store.on("load",function(store,records, opt) {
				for(var i=0;i<records.length;i++){
					var cDate=records[i].get("Knowledge$createDate")
					if(cDate!=""){
						records[i].set("Knowledge$createDate",cDate.substring(0,16));
					}
					if(records[i].get("Knowledge$status")==0){
					 	records[i].set("Knowledge$status","�ݸ�");
					}else if(records[i].get("Knowledge$status")==1){
						records[i].set("Knowledge$status","��ʽ");
					}else if(records[i].get("Knowledge$status")==2){
						records[i].set("Knowledge$status","������");
		  			}else if(records[i].get("Knowledge$status")==3){
						records[i].set("Knowledge$status","����");
					}else if(records[i].get("Knowledge$status")==4){
						records[i].set("Knowledge$status","����ݸ�");
		  			}
				}
		});
		this.fp = this.getknowSearchForm();
		this.store.paramNames.sort = "orderBy";
		this.store.paramNames.dir = "orderType";
		this.cm.defaultSortable = true;
		var viewConfig = Ext.apply({
			forceFit : true
		}, this.gridViewConfig);

		var pageBar = new Ext.PagingToolbar({
			id :"pagetoolBar",
			pageSize : 10,
			store : this.store,
			displayInfo : true,
			displayMsg : '��ǰ��ʾ {0}-{1}����¼ /��{2}����¼',
			emptyMsg : "����ʾ����"
		});
		this.searchConfig=function(){
			if((Ext.getCmp('serviceItemBySu').getRawValue() != '' 
		           && Ext.getCmp('serviceItemBySu').getValue() == '')
		            ||(Ext.getCmp('problemTypebySu').getRawValue() != '' 
		             && Ext.getCmp('problemTypebySu').getValue() == '')){
			    Ext.Msg.alert("��ʾ","��������б���ѡ����ȷ�ķ��������͡���������������ͣ�");
			    return;
		    }
			if(Ext.getCmp('serviceItemBySu').getRawValue()==''){
				Ext.getCmp('serviceItemBySu').clearValue();
			}
			if(Ext.getCmp('problemTypebySu').getRawValue()==''){
				Ext.getCmp('problemTypebySu').clearValue();
			}
			var params = Ext.getCmp("knowSearchForm").getForm().getValues(false);
			var store=Ext.getCmp("konwledgeGrid").getStore();
	        params.start = 0;  
	        params.status=1;
//	        pageBar.formValue=param;
	        store.on('beforeload', function(a) {   
			      Ext.apply(a.baseParams,params);   
			});
	        store.load({
	            params : params
	        });
	},
		this.konwledgeGrid = new Ext.grid.GridPanel({
			id:"konwledgeGrid",
			region : "center",
			store : this.store,
			cm : this.cm,
			sm : sm,
			trackMouseOver : false,
			loadMask : true,
			y : 140,
			height : 200,
			anchor : '0 -35',
			tbar : [{
					xtype : 'button',
					style : 'margin:2px 0px 2px 5px',
					handler :this.searchConfig,
					text : '��ѯ',
					iconCls : 'search'
				},'-',{
					xtype : 'button',
					style : 'margin:2px 0px 2px 5px',
					text : '���',
					handler : this.show,
					scope : this,
					iconCls : 'edit'
					},'-',{
					xtype : 'button',
					style : 'margin:2px 0px 2px 5px',
					handler :function(){
						Ext.getCmp("knowSearchForm").getForm().reset();
					},
					text : '���',
					iconCls : 'reset'
				},new Ext.Toolbar.TextItem("<font color=red style='font-weight:lighter' >��˫����鿴��ϸ��Ϣ��</font>")],
			bbar : pageBar
		});
		
		this.konwledgeGrid.on("headerdblclick", this.fitWidth, this);
		this.konwledgeGrid.on("rowdblclick", this.show, this);
		var items = new Array();
		items.push(this.fp);
		items.push(this.konwledgeGrid);
		this.items = items;
		
		PagePanel.superclass.initComponent.call(this);
		var params = {
			 start : 0,
			'status':1
		};
//		pageBar.formValue = param;
		this.store.on('beforeload', function(a) {   
		      Ext.apply(a.baseParams,params);   
		});
		this.store.removeAll();
		this.store.load({
			params : params
		});
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