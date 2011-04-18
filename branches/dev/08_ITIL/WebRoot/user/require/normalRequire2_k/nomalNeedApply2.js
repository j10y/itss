PageTemplates = Ext.extend(Ext.Panel, {
	id : "PageTemplates",
	layout : 'table',
	height : 'auto',
	align : 'center',
	foredit : true,
	width : 1050,
	frame : true,
	autoScroll : true,
	defaults : {
		bodyStyle : 'padding:4px'
	},
	layoutConfig : {
		columns : 1
	},	
	save : function() {
		//add by lee for ����Ϊͳһ����֤��������̨���ñ����� in 20091103 begin
		if(!Ext.getCmp('panel_ERP_NormalNeed5_Input').form.isValid()){
			Ext.MessageBox.alert("��ʾ","ҳ���д���ɫ�����ߵ�Ϊ������,����д������лл��������");	
			return false;
		}
		var store = Ext.getCmp('panel_requireFactoryInfo_list_1').store;		
		var dataRecords = new Array();
		var i =0;		
		var product = "";
		store.each(function(record) {
			dataRecords[i++] = record;
				product += Ext.encode(record.data) + ",";		
		})
		function isChn(str){
		      var reg = /^[u4E00-u9FA5]+$/;
		      if(!reg.test(str)){
		       return false;
		      }
		      return true;
		}
		for(var i=0;i < dataRecords.length;i++){
			var strFactoryId = dataRecords[i].get("itil_req_RequireFactoryInfo$factoryId");
			var strStockAddressId = dataRecords[i].get("itil_req_RequireFactoryInfo$stockAddressId");
			if(strFactoryId==""||strFactoryId==null){
				Ext.MessageBox.alert("��ʾ","�������Ϊ������,����д������лл��������");	
				return false;
			}else if(strFactoryId.length!=4){
				Ext.MessageBox.alert("��ʾ","������ų���Ϊ4���ַ�,���޸ġ�лл��������");	
				return false;
			}
			else if(!isChn(strFactoryId)){
				Ext.MessageBox.alert("��ʾ","������Ų�����������,���޸ġ�лл��������");	
				return false;
			}
			if(strStockAddressId==""||strStockAddressId==null){
				Ext.MessageBox.alert("��ʾ","���ر��Ϊ������,����д������лл��������");	
				return false;
			}else if(strStockAddressId.length!=4){
				Ext.MessageBox.alert("��ʾ","���ر�ų���Ϊ4���ַ�,���޸ġ�лл��������");	
				return false;
			}
			else if(!isChn(strStockAddressId)){
				Ext.MessageBox.alert("��ʾ","���ر�Ų�����������,���޸ġ�лл��������");	
				return false;
			}		
		}
		if(product==""&&this.dataId==''){
			Ext.MessageBox.alert("��ʾ","������ϸΪ������,����д������лл��������");	
			return false;
		}
		//add by lee for ����Ϊͳһ����֤��������̨���ñ����� in 20091103 end
		Ext.getCmp('saveButton').disable();
		Ext.getCmp('workFlowButton').disable();
		var curscid = this.serviceItemId;
		var scope1 = Ext.getCmp('ERP_NormalNeed$scopes$1').getValue();
		var scope2 = Ext.getCmp('ERP_NormalNeed$scopes$2').getValue();
		var scope3 = Ext.getCmp('ERP_NormalNeed$scopes$3').getValue();
		var scope4 = Ext.getCmp('ERP_NormalNeed$scopes$4').getValue();
		var scope5 = Ext.getCmp('ERP_NormalNeed$scopes$5').getValue();
		var scope6 = Ext.getCmp('ERP_NormalNeed$scopes$6').getValue();
		var scope7 = Ext.getCmp('ERP_NormalNeed$scopes$7').getValue();
		var scope8 = Ext.getCmp('ERP_NormalNeed$scopes$8').getValue();
		if (scope1 == false && scope2 == false && scope3 == false
				&& scope4 == false && scope5 == false && scope6 == false
				&& scope7 == false && scope8 == false) {
			Ext.MessageBox.alert("��ʾ", "��ѡ��ʹ�÷�Χ��");
			Ext.getCmp('saveButton').enable();
			Ext.getCmp('workFlowButton').enable();
			return;
		}

		var formParams = Ext.encode(getFormParam('panel_ERP_NormalNeed5_Input'));;

		Ext.Ajax.request({
			url : webContext + '/requireAction_saveApplyDraftAndBigGrid.action',
			timeout : 100000000,
			params : {
					pagePanel:'panel_ERP_NormalNeed5_Input',
					formParams : formParams,
					product : product,
					serviceItemId:curscid
			},
			success : function(response, options) {
				var responseArray = Ext.util.JSON.decode(response.responseText);
				var curId = responseArray.id;
				window.location = webContext
						+ "/requireSIAction_toRequireInfoByServiceItemId.action?id="
						+ curscid;
			},
			failure : function(response, options) {
				Ext.MessageBox.alert("����ʧ��");
				Ext.getCmp('saveButton').enable();
				Ext.getCmp('workFlowButton').enable();
			}
		}, this);
	},
	// ���沢�ύ
	saveAndSubmit : function() {
		//add by lee for ����Ϊͳһ����֤��������̨���ñ����� in 20091103 begin
		if(!Ext.getCmp('panel_ERP_NormalNeed5_Input').form.isValid()){
			Ext.MessageBox.alert("��ʾ","ҳ���д���ɫ�����ߵ�Ϊ������,����д������лл��������");	
			return false;
		}
		var store = Ext.getCmp('panel_requireFactoryInfo_list_1').store;		
		var dataRecords = new Array();
		var i =0;		
		var product = "";
		store.each(function(record) {
			dataRecords[i++] = record;
			product += Ext.encode(record.data) + ",";	
		})
		function isChn(str){
		      var reg = /^([u4E00-uFA29]|[uE7C7-uE7F3]|[a-zA-Z0-9])*$/;
		      if(!reg.test(str)){
		       return false;
		      }
		      return true;
		}
		for(var i=0;i < dataRecords.length;i++){	
			var strFactoryId = dataRecords[i].get("itil_req_RequireFactoryInfo$factoryId");
			var strStockAddressId = dataRecords[i].get("itil_req_RequireFactoryInfo$stockAddressId");
			if(strFactoryId==""||strFactoryId==null){
				Ext.MessageBox.alert("��ʾ","�������Ϊ������,����д������лл��������");	
				return false;
			}else if(strFactoryId.length!=4){
				Ext.MessageBox.alert("��ʾ","������ų���Ϊ4���ַ�,���޸ġ�лл��������");	
				return false;
			}
			else if(!isChn(strFactoryId)){
				Ext.MessageBox.alert("��ʾ","������Ų�����������,���޸ġ�лл��������");	
				return false;
			}
			if(strStockAddressId==""||strStockAddressId==null){
				Ext.MessageBox.alert("��ʾ","���ر��Ϊ������,����д������лл��������");	
				return false;
			}else if(strStockAddressId.length!=4){
				Ext.MessageBox.alert("��ʾ","���ر�ų���Ϊ4���ַ�,���޸ġ�лл��������");	
				return false;
			}
			else if(!isChn(strStockAddressId)){
				Ext.MessageBox.alert("��ʾ","���ر�Ų�����������,���޸ġ�лл��������");	
				return false;
			}		
		}
		if(product==""&&this.dataId==''){
			Ext.MessageBox.alert("��ʾ","������ϸΪ������,����д������лл��������");	
			return false;
		}
		//add by lee for ����Ϊͳһ����֤��������̨���ñ����� in 20091103 end
		Ext.getCmp('saveButton').disable();
		Ext.getCmp('workFlowButton').disable();
		var curscid = this.serviceItemId;
		var curModel = this.model;
		var curProcessName = this.processName;
		var scope1 = Ext.getCmp('ERP_NormalNeed$scopes$1').getValue();
		var scope2 = Ext.getCmp('ERP_NormalNeed$scopes$2').getValue();
		var scope3 = Ext.getCmp('ERP_NormalNeed$scopes$3').getValue();
		var scope4 = Ext.getCmp('ERP_NormalNeed$scopes$4').getValue();
		var scope5 = Ext.getCmp('ERP_NormalNeed$scopes$5').getValue();
		var scope6 = Ext.getCmp('ERP_NormalNeed$scopes$6').getValue();
		var scope7 = Ext.getCmp('ERP_NormalNeed$scopes$7').getValue();
		var scope8 = Ext.getCmp('ERP_NormalNeed$scopes$8').getValue();
		if (scope1 == false && scope2 == false && scope3 == false
				&& scope4 == false && scope5 == false && scope6 == false
				&& scope7 == false && scope8 == false) {
			Ext.MessageBox.alert("��ʾ", "��ѡ��ʹ�÷�Χ��");
			Ext.getCmp('saveButton').enable();
			Ext.getCmp('workFlowButton').enable();
			return;
		}

		var tempRequireLevel = Ext.getCmp('ERP_NormalNeed$requireLevelCombo').getValue();
		var tempReason = Ext.getCmp('ERP_NormalNeed$reason').getValue();
		if(tempRequireLevel==1&&tempReason==""){
			Ext.MessageBox.alert("��ʾ","�Ӽ����������д�Ӽ����ɣ�");
			Ext.getCmp('saveButton').enable();
			Ext.getCmp('workFlowButton').enable();
			return;
		}
		var formParams = Ext.encode(getFormParam('panel_ERP_NormalNeed5_Input'));;

		Ext.Ajax.request({
			url : webContext + '/requireAction_saveApplyDraftAndBigGrid.action',
			timeout : 100000000,
			params : {
					pagePanel:'panel_ERP_NormalNeed5_Input',
					formParams : formParams,
					product : product,
					serviceItemId:curscid
			},
			timeout : 100000000,
			success : function(response, options) {
				var responseArray = Ext.util.JSON.decode(response.responseText);
				var curDataId = responseArray.id;
				var reqName = responseArray.reqName;
				var reqCode = responseArray.applyNum;
				var reqDate = responseArray.applyDate;
				Ext.Ajax.request({
					url : webContext + '/requireWorkflow_applyAndAssign.action',
					params : {
						dataId : curDataId,
						model : curModel,
						bzparam : "{dataId :'" + curDataId + "',applyId : '"
								+ curDataId + "',serviceItemId : '" + curscid
								+ "',erpxzFlag : '270"
								+ "',reqName : '" + reqName
								+ "',reqCode : '" + reqCode
								+ "',reqDate : '" + reqDate
								+ "',workflowHistory:'com.digitalchina.itil.service.entity.ServiceItemApplyAuditHis'"
								+ ",applyType: 'rproject'}",
						defname : curProcessName
					},
					success : function(response, options) {

						var meg = Ext.decode(response.responseText);
						if (meg.id != undefined) {
							Ext.Msg.alert("��ʾ", "�����ύ�ɹ�", function() {//modify by zhangzy for �û�Ҫ�������ʾ
								window.location = webContext+ "/requireSIAction_toRequireInfoByServiceItemId.action?id="+ curscid;
							});
							
						} else {
							Ext.Msg.alert("��ʾ", "�����ύʧ��", function() {
								alert(meg.Exception);
							});
						}
					},
					failure : function(response, options) {
						Ext.MessageBox.alert("��ʾ", "�����ύʧ��");
					}
				}, this);
			},
			failure : function(response, options) {
				Ext.MessageBox.alert("�����ύʧ��");
				Ext.getCmp('saveButton').enable();
				Ext.getCmp('workFlowButton').enable();
			}
		}, this);
		
	},
	back : function() {
		window.location = webContext
				+ "/requireAction_toRequireInfo.action?serviceItemId="
				+ this.serviceItemId;
	},
	removePlan : function() {
		var record = Ext.getCmp('panel_requireFactoryInfo_list_1').getSelectionModel().getSelected();
		var records = Ext.getCmp('panel_requireFactoryInfo_list_1').getSelectionModel().getSelections();
		var store = Ext.getCmp('panel_requireFactoryInfo_list_1').store;
		if (!record) {
			Ext.Msg.alert("��ʾ", "����ѡ��Ҫɾ������!");
			return;
		}
		if (records.length == 0) {
			Ext.MessageBox.alert('����', '����ѡ��һ����Ϣ������ɾ��!');
		} else {
			Ext.MessageBox.confirm('��ʾ��', '��ȷ��Ҫ���иò�����', function(btn) {
				if (btn == 'yes') {
					var remIds = "";
					if (records) {
						for (var i = 0; i < records.length; i++) {
							this.store.remove(records[i]);
							var tempId = records[i].get("itil_req_RequireFactoryInfo$id");
							if(tempId != null){
								remIds += records[i].get("itil_req_RequireFactoryInfo$id") + ",";
							}
						}
						Ext.Ajax.request({
							url : webContext
									+ '/SRAction_removeRequireFactoryInfo.action',
							params : {
								removedIds : remIds
							},
							success : function(response, options) {
								Ext.MessageBox.alert("��ʾ", "ɾ���ɹ�", function() {
//									store.reload();
//									store.removeAll();
								});

							},
							failure : function(response, options) {
								Ext.MessageBox.alert("ɾ��ʧ��");
							}
						})
					}
				}
			}, this)
		}
	},
        /*Excel���빤����Ϣ*/
        fileImport : function(){
        	var dialog = new Ext.ux.UploadDialog.Dialog({
                        url: webContext+'/require/excelImportInfo.do?methodCall=upload',
                        reset_on_hide: false,
                        draggable: true,
                        width: 400,
                        height: 100,
                        permitted_extensions:['xls'],  
                        allow_close_on_upload: false,
                        upload_autostart: false
                    });
                    dialog.height = 180;
                    dialog.width = 450;
                    dialog.on("uploadsuccess",this.onUploadSuccess,this);
                    dialog.show('show-button');
        },	
  onUploadSuccess : function(dialog, filename, result, record){
              if(result.successFlg == 'allSuccess'){
                   Ext.Msg.alert('��ʾ','����ɹ���',function(){
                   	var store = Ext.getCmp('panel_requireFactoryInfo_list_1').store;   
                        var stringArray = result.resultJson;
                        var TopicRecord = Ext.data.Record.create([
    							{name: 'itil_req_RequireFactoryInfo$factoryId',mapping: 'factoryId', type: 'string'},
   								{name: 'itil_req_RequireFactoryInfo$stockAddressId',mapping: 'stockAddressId', type: 'string'}
						]);
						for(var i = 0 ;i < stringArray.length; i++){
							var myNewRecord = new TopicRecord({ 
								itil_req_RequireFactoryInfo$factoryId : stringArray[i].itil_req_RequireFactoryInfo$factoryId,
								itil_req_RequireFactoryInfo$stockAddressId : stringArray[i].itil_req_RequireFactoryInfo$stockAddressId
							});
							store.add(myNewRecord);
						}
                    dialog.close();
                   });
              }else if(result.successFlg == 'partSuccess'){
					var str = '�Ϸ���Ϣ'+result.successCount+'�����Ƿ���Ϣ'+result.falsCount+'����<br><font color="red" size="4">������ʾ:</font><br>'+result.msg +'<br>�޸�Excel�����ϴ�';
					var alertWin =	new Ext.Window({
						title : '��ʾ��Ϣ',
						autoScroll : true,
						modal : true,
						height : 255,
						width : 400,
						resizable : false,
						viewConfig : {
							autoFill : true,
							forceFit : true
						},
						layout : 'absolute',
						buttons : [{
							xtype : 'button',
							handler : function() {alertWin.close();},
							text : '�ر�',
							iconCls : 'closeWin',
							scope : this
						}],
						items : [{html:str}]
					});
			       alertWin.show();
              }else if(result.successFlg == 'recodeNull'){
                   Ext.Msg.alert('��ʾ','����ʧ��,ԭ��'+result.msg,function(){
                        dialog.close();
                   });
              }
        },      
	getFormpanel_ERP_NormalNeed5_Input : function() {
		var da = new DataAction();
		var data = null;
		// �ж������������޸�
		var biddata = "";
		data = da.getRequireFormPanelElementsForEdit(
				"panel_ERP_NormalNeed5_Input", this.dataId);		
		for (i = 0; i < data.length; i++) {
			
			var idStr = data[i].id + "";
			if (idStr.indexOf('$requirementLevelCombo') > 0
					&& data[i].value == '') {
				data[i].value = '4';
			}
			if(idStr=='ERP_NormalNeed$flatCombo'){
					data[i].store = new Ext.data.JsonStore({
					url:webContext+"/extjs/comboDataAction?clazz=com.digitalchina.itil.require.entity.RequireApplyDefaultAudit&orderBy=sortNum&deleteFlag=0&enable=1",
					fields:['id','departmentName'],
					totalProperty:'rowCount',
					root:'data',
					id:'id'
				});
				data[i].initComponent();
			}			
			//add by lee for ƽ̨��ת������Ĭ��ֵ in 20091116 begin
			if (idStr=='ERP_NormalNeed$isStoreCombo'&& data[i].value == '') {
				data[i].value = '1';
			}
			//add by lee for ƽ̨��ת������Ĭ��ֵ in 20091116 end
			if (idStr.indexOf('$applyNum') > 0
					|| idStr.indexOf('$applyUser') > 0
			) {
				data[i].readOnly = true;
				if (data[i].value == '') {
					data[i].emptyText = '�Զ�����';
					data[i].disabled = true;
				}
			}
			//alert(data[i].fieldLabel);	
			
		}
		
		if (this.status == 1 || this.status == 2) { //add by zhangzy for ������ҳ���������Ϊֻ�� in 2009 11 25
			for (i = 0; i < data.length; i++) {
				if (data[i].xtype == 'combo' || data[i].xtype == 'datefield') {
					data[i].id = data[i].id + 8;// �ı�Combobox��id
					data[i].readOnly = true;
					data[i].hideTrigger = true;
				}
				data[i].readOnly = true;				
			}

		}
		var modifyData = new Array();
		for (var i = 0; i < data.length; i++) {
//			if(i==9){
//				modifyData.push(this.getHtmlText());				
//				}				
			if(i==9){
				modifyData.push(this.getpanel_requireFactoryInfo_list_1());
			}
			modifyData.push(data[i]);
		}
		biddata = da.split(modifyData);	
		var curbuttons = new Array();
		var saveButton = new Ext.Button({
			text : '����Ϊ�ݸ�',
			id : 'saveButton',
			pressed : true,
			iconCls : 'save',
			scope : this,
			handler : this.save
		});
		var submitButton = new Ext.Button({
			text : '���沢�ύ',
			id : 'workFlowButton',
			pressed : true,
			iconCls : 'submit',
			scope : this,
			handler : this.saveAndSubmit
		});
		var backButton = new Ext.Button({
			text : '����',
			id : 'refresh',
			pressed : true,
			iconCls : 'back',
			scope : this,
			handler : this.back
		});
		if (this.status == 0) {
			curbuttons.push(saveButton);
			curbuttons.push(submitButton);
		}
		curbuttons.push(backButton);
		this.formpanel_ERP_NormalNeed5_Input = new Ext.form.FormPanel({
			id : 'panel_ERP_NormalNeed5_Input',
			layout : 'table',
			height : 'auto',
			width : 800,
			frame : true,
			collapsible : true,
			autoScroll :true,
			defaults : {
				bodyStyle : 'padding:4px'
			},
			layoutConfig : {
				columns : 4
			},
			title : this.description,
			items : biddata,
			tbar : curbuttons
		// buttons : curbuttons
		});	
		return this.formpanel_ERP_NormalNeed5_Input;
	},
	getpanel_requireFactoryInfo_list_1:function(){
		var da = new DataAction();
		var obj = da.getPanelElementsForHead("panel_requireFactoryInfo_list_1");
		var buttonUtil = new ButtonUtil();
		var getGridButtons = buttonUtil.getButtonForPanel("panel_requireFactoryInfo_list_1",
				this);		
		var excelInputButton = new Ext.Button({
			id : 'excelInputButton',
			text : "�������빤����ϸ",
			pressed : true,
			handler : this.fileImport,
			scope : this,
			iconCls : 'add',
			cls : "x-btn-text-icon"
		});		
		var modifyButtons = new Array();
			for(var i = 0;i<getGridButtons.length;i++){
				if (this.dataId ==""||this.status == '0') {//�����ύ״̬��ݸ�״̬ʱ��ʾ����ӡ�ɾ������ť
					modifyButtons.push(getGridButtons[i]);
				}	
			}
		modifyButtons.push(excelInputButton);	
		modifyButtons.push(new Ext.Toolbar.TextItem('<p>&nbsp;&nbsp;&nbsp;&nbsp;<a href="'+ webContext + '/tempUpload/uploadModel.xls" target="_blank">���ص���ģ��</a>&nbsp;&nbsp;<font color=red> �������޸�ģ�壬�����޷���ɵ��롿</font></p>'));	
		var sm = new Ext.grid.CheckboxSelectionModel();
		var columns = new Array();
		columns[0] = sm;
		// ѭ�������е��У�Ȼ��������������
		for (var i = 0; i < obj.length; i++) {
			var headItem = obj[i];
			var title = headItem.header;
			var alignStyle = 'left';
			var propertyName = headItem.dataIndex;
			var editor = headItem.editor;
			var renderer = headItem.renderer;
			var hide = headItem.hidden;
			var isHidden = false;
			if (hide == 'true') {
				isHidden = true;
			}
			// ��ÿһ����������
			var columnItem = "";
			if (editor.xtype == 'combo') {
				columnItem = {
					header : title,
					dataIndex : propertyName,
					sortable : true,
					hidden : isHidden,
					align : alignStyle,
					renderer : renderer,
					editor : editor
				}
			} else if (editor.xtype == 'datefield') {
				columnItem = {
					header : title,
					dataIndex : propertyName,
					sortable : true,
					hidden : isHidden,
					align : alignStyle,
					renderer : renderer,
					editor : editor
				}
			} else {
				columnItem = {
					header : title,
					dataIndex : propertyName,
					sortable : true,
					hidden : isHidden,
					align : alignStyle,
					editor : editor
				}
			}
			columns[i + 1] = columnItem;
		}	
		this.cm = new Ext.grid.ColumnModel(columns);
		this.store = da.getPagePanelJsonStore("panel_requireFactoryInfo_list_1", obj);
		this.cm.defaultSortable = true;
		var viewConfig = Ext.apply({
			forceFit : true
		}, this.gridViewConfig);
		this.formValue = '';
		var gridpanel_requireFactoryInfo_list_1 = new Ext.grid.EditorGridPanel({
			fieldLabel : '������ϸ',
			id : 'panel_requireFactoryInfo_list_1',
			title : '<br>��1����������ӡ���������ϸ��Ŀ��˫��������š����ر�Ŷ�Ӧ�м��ɱ༭��<br>��2�����ߵ�����������빤����ϸ��������ģ������excel�ļ��ϴ���ϵͳ��<br>��3��ѡ��ĳ���Ѽ���ϸ�󣬿��Ե����ɾ������ť����ɾ����<br><br>',
			store : this.store,
			cm : this.cm,
			sm : sm,
			trackMouseOver : false,
			loadMask : true,
			clicksToEdit : 2,
			//collapsible : true,					//grid���ذ�ť
			autoScroll : true,
			height : 320,
			width : 9999,// this.width - 15,
			frame : true,
			tbar : [modifyButtons],
           viewConfig: {   
                  forceFit:true   
            },   
           bbar: new Ext.PagingToolbar({   
           	    id:'pagId',
                pageSize: 10,   
                store: this.store,   
               displayInfo: true
           }) 			
		});
		if (this.dataId != '') {
			var pcnameValue = "";
			var fcnameObj = Ext.getCmp("ERP_NormalNeed$id");
			if (fcnameObj != undefined) {
				pcnameValue = Ext.getCmp("ERP_NormalNeed$id").getValue();
			}
			var str = '{' + '\"' + "requireData" + '\"' + ':' + '\"'
					+ pcnameValue + '\"' + '}';
			var param = eval('(' + str + ')');
			param.methodCall = 'query';
			param.start = 1;
			Ext.getCmp('pagId').formValue = param;
			this.store.load({
				params : param
			});
		}		
	return 	gridpanel_requireFactoryInfo_list_1;
		
	}, 
	getPercentAndDayCountPanel : function(){
	var percentAndDayCountPanel = new Ext.form.FieldSet({
			title : '������ϸ��Ϣ��д˵��',
			fieldLabel:'������ϸ��д˵��', 
			id : 'percentAndDayCountPanel',
			width : 9999,
			layout :'table',
			buttonAlign : 'center',
			hideLabel : true,
			layoutConfig : {
						columns : 4
			},
			items : [
				{
					html : '<br>��1����������ӡ���������ϸ��Ŀ��˫��������š����ر�Ŷ�Ӧ�м��ɱ༭��<br>��2�����ߵ�����������빤����ϸ��������ģ������excel�ļ��ϴ���ϵͳ��<br>��3��ѡ��ĳ���Ѽ���ϸ�󣬿��Ե����ɾ������ť����ɾ����<br><br>',
					cls : 'common-text',
					colspan : 4,
					rowspan : 0
				}
			]
		})
		return percentAndDayCountPanel;
	},

	items : this.items,
	/*
	 * clientvalidation ��ʼ��
	 */
	initComponent : function() {
		this.getFormpanel_ERP_NormalNeed5_Input();
		if (this.status != 0) {
			var histroyForm = new HistroyForm({
				reqId : this.dataId,
				reqClass : "com.digitalchina.itil.require.entity.ERP_NormalNeed"
			});
			
			this.tab = new Ext.TabPanel({
				xtype : 'tabpanel',
				activeTab : 1,
				enableTabScroll : true,
				// minTabWidth:100,
				// resizeTabs : true,
				deferredRender : false,
				frame : true,
				plain : true,
				border : false,
				// tabPosition:'bottom',
				baseCls : 'x-plain',// �Ƿ����úͱ���ɫͬ��
				width : 900,
				// bodyBorder : true,
				defaults : {
					autoHeight : true,
					autoHeight : true,
					bodyStyle : 'padding:2px'
				},
				items : [this.formpanel_ERP_NormalNeed5_Input, histroyForm]
			});
			this.items = [this.tab];
		} else {
			this.items = [this.formpanel_ERP_NormalNeed5_Input];
		}	
		this.on("removePlan", this.removePlan, this);		
		PageTemplates.superclass.initComponent.call(this);
	}
})