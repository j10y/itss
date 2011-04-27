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
	getOldApply : function(){
		var curSi = this.serviceItemId;
		var oldApply = new Ext.form.ComboBox({
			xtype:'combo',
			hiddenName: 'SpecialRequirement$oldApply',
			id :'SpecialRequirement$oldApplyCombo',
			width:'null',
			style:'',
			fieldLabel:'���ǰ����',
			lazyRender: true,
			displayField: 'name',
			valueField :'id',
			emptyText:'��ѡ��...',
			allowBlank:false,
			typeAhead:true,
			name:'SpecialRequirement$oldApply',
			triggerAction:'all',
			minChars :50,
			queryDelay : 700,
			store:new Ext.data.JsonStore({
				url:webContext+'/extjs/comboDataAction?clazz=com.zsgj.itil.require.entity.SpecialRequirement&serviceItem='+curSi+"&status=1",
				fields:['id','name'],
				listeners:{
					beforeload : function(store, opt){
						if(opt.params['SpecialRequirement$oldApply'] == undefined){
							opt.params['name'] =Ext.getCmp('SpecialRequirement$oldApplyCombo').defaultParam;
						}
					}
				},
				totalProperty:'rowCount',
				root:'data',
				id:'id'
			}),
			pageSize:10,
			listeners:{
				'beforequery' : function(queryEvent){
					var param = queryEvent.combo.getRawValue();
					this.defaultParam = param;
					if(queryEvent.query==''){
						param='';
					}
					this.store.load({
						params:{
							name:param,
							start:0
						}
					});
					return true;
				}
			},
			initComponent : function() {
				this.store.load({
					params:{
						id:Ext.getCmp('SpecialRequirement$oldApplyCombo').getValue(),
						start:0
					},
				callback:function(r, options, success){
					Ext.getCmp('SpecialRequirement$oldApplyCombo').setValue(Ext.getCmp('SpecialRequirement$oldApplyCombo').getValue());
				}
			});
		}});
		return oldApply;
	},
	save : function() {
		//add by lee for ����Ϊͳһ����֤��������̨���ñ����� in 20091103 begin
		if(!Ext.getCmp('panel_typicalRequire_m_Input').form.isValid()){
			Ext.MessageBox.alert("��ʾ","ҳ���д���ɫ�����ߵ�Ϊ������,����д������лл��������");	
			return false;
		}
		//add by lee for ����Ϊͳһ����֤��������̨���ñ����� in 20091103 end
		var dept=Ext.getCmp('SpecialRequirement$flatCombo').getValue();
		var deptName=Ext.getCmp('SpecialRequirement$flatCombo').getRawValue();//add by zhangzy for ���뱾��ѡ���ޡ�ʱbug in 2009 11 24
		if(dept==""||dept==null){
			Ext.MessageBox.alert("��ʾ","��ѡ�����뱾����");
			Ext.getCmp('saveButton').enable();
			Ext.getCmp('workFlowButton').enable();
			return;
		}
		if(deptName=="��"){//add by zhangzy for ���뱾��ѡ���ޡ�ʱbug in 2009 11 24
			Ext.MessageBox.alert("��ʾ","��ѡ�����뱾����");
			Ext.getCmp('saveButton').enable();
			Ext.getCmp('workFlowButton').enable();
			return;
		}		
		var curscid = this.serviceItemId;
		var curModel = this.model;
		var curProcessName = this.processName;
		var curStatus = this.status;
		var curProcessType = this.processType;
		var name=Ext.getCmp('SpecialRequirement$name').getValue()
		if(name==""){
		Ext.Msg.alert("��ʾ","��������������");
		return;
		}
		var curscid = this.serviceItemId;
		var formParam = Ext.getCmp('panel_typicalRequire_m_Input').form.getValues(false);
		var vp = null;
		if (formParam != null) {
			vp = Ext.apply(formParam, vp, {});
		}
		var curUrl = webContext
				+ '/requireAction_saveModifyDraft.action?' 
				+ 'pagePanel=panel_typicalRequire_m_Input'
				+ '&serviceItemId=' + curscid
				+ '&processType=' + curProcessType
				+ '&status=' + curStatus;
		Ext.Ajax.request({
			url : curUrl,
			params : vp,

			success : function(response, options) {
				var responseArray = Ext.util.JSON.decode(response.responseText);
				var curId = responseArray.id;
				window.location = webContext//modify by zhangzy for ����ݸ����ת in 2009 11 19
									+ "/requireSIAction_toRequireInfoByServiceItemId.action?id="
									+ curscid;
			},
			failure : function(response, options) {
				Ext.MessageBox.alert("����ʧ��");
			}
		}, this);
	},
	// ���沢�ύ
	saveAndSubmit : function() {
		//add by lee for ����Ϊͳһ����֤��������̨���ñ����� in 20091103 begin
		if(!Ext.getCmp('panel_typicalRequire_m_Input').form.isValid()){
			Ext.MessageBox.alert("��ʾ","ҳ���д���ɫ�����ߵ�Ϊ������,����д������лл��������");	
			return false;
		}
		//add by lee for ����Ϊͳһ����֤��������̨���ñ����� in 20091103 end
		//add by zhangzy for ���������б�ѡ�񣬶����ֶ�����Ҳ�����ύ��bug in 2009 11 21 start
		if(Ext.getCmp('SpecialRequirement$oldApplyCombo').getValue()==""){
			Ext.MessageBox.alert("��ʾ","��Ҫ��������̲�����,��ȷ�ϣ�");	
			return false;
		}
		//add by zhangzy for ���������б�ѡ�񣬶����ֶ�����Ҳ�����ύ��bug in 2009 11 21 end
		var dept=Ext.getCmp('SpecialRequirement$flatCombo').getValue();
		var deptName=Ext.getCmp('SpecialRequirement$flatCombo').getRawValue();//add by zhangzy for ���뱾��ѡ���ޡ�ʱbug
		var comfirmUserName=Ext.getCmp('SpecialRequirement$confirmUserCombo').getRawValue();		
		if(name==""||name==null){
			Ext.MessageBox.alert("��ʾ","����д�������ƣ�");
			Ext.getCmp('saveButton').enable();
			Ext.getCmp('workFlowButton').enable();
			return;
		}
		if(dept==""||dept==null){
			Ext.MessageBox.alert("��ʾ","��ѡ�����뱾����");
			Ext.getCmp('saveButton').enable();
			Ext.getCmp('workFlowButton').enable();
			return;
		}
		if(deptName=="��"){//add by zhangzy for ���뱾��ѡ���ޡ�ʱbug
			Ext.MessageBox.alert("��ʾ","��ѡ�����뱾����");
			Ext.getCmp('saveButton').enable();
			Ext.getCmp('workFlowButton').enable();
			return;
		}
		if(comfirmUserName=="��"){
			Ext.MessageBox.alert("��ʾ","��ѡ����ҵ�������ˣ�");
			Ext.getCmp('saveButton').enable();
			Ext.getCmp('workFlowButton').enable();
			return;
		}
		var curscid = this.serviceItemId;
		var curModel = this.model;
		var curProcessName = this.processName;
		var curStatus = this.status;
		var curProcessType = this.processType;
		var name=Ext.getCmp('SpecialRequirement$name').getValue()
		if(name==""){
		Ext.Msg.alert("��ʾ","��������������");
		return;
		}
		Ext.getCmp('saveTemp').disable(); //add by zhangzy for ������沢�ύ�� ��ť��� in 2009 11 17
		Ext.getCmp('workFlowButton').disable();
		var curscid = this.serviceItemId;
		var curModel = this.model;
		var curProcessName = this.processName;
		var formParam = Ext.getCmp('panel_typicalRequire_m_Input').form.getValues(false);
		var vp = null;
		if (formParam != null) {
			vp = Ext.apply(formParam, vp, {});
		}
		var curUrl = webContext
				+ '/requireAction_saveModifyDraft.action?' 
				+ 'pagePanel=panel_typicalRequire_m_Input'
				+ '&serviceItemId=' + curscid
				+ '&processType=' + curProcessType
				+ '&status=' + curStatus;
		Ext.Ajax.request({
			url : curUrl,
			params : vp,
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
						bzparam : "{dataId :'" + curDataId 
								+ "',applyId : '"+ curDataId
								+ "',serviceItemId : '"+ curscid
								+ "',reqName : '"+ reqName
								+ "',reqCode : '" + reqCode
								+ "',reqDate : '" + reqDate
								+ "',workflowHistory:'com.zsgj.itil.service.entity.ServiceItemApplyAuditHis',applyType: 'rproject'}",
						defname : curProcessName
					},
					success : function(response, options) {
						Ext.Msg.alert("��ʾ", "�����ύ�ɹ�", function() {//modify by zhangzy for ��������ύ��ʾ  in 2009 11 21
							window.location = webContext
									+ "/requireSIAction_toRequireInfoByServiceItemId.action?id="
									+ curscid;
						});
					},
					failure : function(response, options) {
						Ext.Msg.alert("��ʾ", "�����ύʧ��", function() {
							window.location = webContext
									+ "/infoAdmin/serviceItemProcessAction.do?methodCall=toApplyPage&serviceItemId="
									+ curscid + "&processType=0" + "&dataId=" + curDataId;
						});
					}
				}, this);
			},
			failure : function(response, options) {
				Ext.MessageBox.alert("����ʧ��");
			}
		}, this);
	},
	back : function() {
		window.location = webContext
				+ "/requireAction_toRequireInfo.action?serviceItemId="
				+ this.serviceItemId;
	},
	
 getFormpanel_typicalRequire_m_Input: function() {
 	var pName = this.processName;
 	
 	  var da = new DataAction();
		var data = null;
		// �ж������������޸�
		var biddata = "";
		data = da.getModifyRequireFormPanelElementsForEdit("panel_typicalRequire_m_Input",this.dataId,this.oldId);
		
			for (i = 0; i < data.length; i++) {
				var idStr = data[i].id + "";
			if (idStr.indexOf('$applyNum') > 0
					|| idStr.indexOf('$requireId') > 0 
					|| idStr.indexOf('$applyUser') > 0) {// add by zhangzy for������ֻ������ in 2009 11 17 
				data[i].readOnly = true;
				data[i].hideTrigger = true;
				if (data[i].value == '') {
						data[i].emptyText = '�Զ�����';
						// data[i].readOnly = true;
						data[i].disabled = true;
					}
				}
			if(idStr=='SpecialRequirement$oldApplyCombo'){
					data[i]=this.getOldApply();
			}
			if (idStr.indexOf('$applyUser')> 0) {
					data[i].emptyText = '������ITCODE���в�ѯ';
			}
			if (idStr.indexOf('$confirmUserCombo') > 0) {
					data[i].emptyText = '������ITCODE���в�ѯ';
					data[i].fieldLabel = '��ҵ��������';//add by lee for ���û�Ҫ���޸����� in 20091113 
			}
			if (idStr.indexOf('$flatCombo') > 0) {
					data[i].fieldLabel = '���뱾��';//add by lee for ���û�Ҫ���޸����� in 20091113 
			}
			if(idStr=='SpecialRequirement$flatCombo'){
					data[i].store = new Ext.data.JsonStore({
					url:webContext+"/extjs/comboDataAction?clazz=com.zsgj.itil.require.entity.RequireApplyDefaultAudit&orderBy=sortNum&deleteFlag=0&enable=1",
					fields:['id','departmentName'],
					totalProperty:'rowCount',
					root:'data',
					id:'id'
				});
				data[i].initComponent();
			}
			}
			biddata = da.split(data);
		
		var curbuttons = new Array();
		var saveButton = new Ext.Button({
			text : '����Ϊ�ݸ�',
			id : 'saveTemp', //add by zhangzy for ������沢�ύ�� ��ť���
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
		if(this.status==0){
			curbuttons.push(saveButton);
			curbuttons.push(submitButton);
		}
		curbuttons.push(backButton);
		this.formpanel_typicalRequire_m_Input = new Ext.form.FormPanel({
			id : 'panel_typicalRequire_m_Input',
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
			title :this.description,
			items : biddata,
			//buttons : curbuttons
			tbar : [curbuttons]
		});

	
		return this.formpanel_typicalRequire_m_Input;
	},
  items : this.items,
	/*
	 * clientvalidation ��ʼ��
	 */
	initComponent : function() {
		var spId = this.serviceItemProcessId;		
	     this.getFormpanel_typicalRequire_m_Input(); 
		 
		if (this.status != 0) {
			var histroyForm = new HistroyForm({
			reqId : this.dataId,
			reqClass : "com.zsgj.itil.require.entity.SpecialRequirement"
		    });
			 this.tab = new Ext.TabPanel({
				xtype : 'tabpanel',
				activeTab : 1,
				title : '�¼���ϸ��Ϣ',
				enableTabScroll : true,
				// minTabWidth:100,
				//resizeTabs : true,
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
			 items : [this.formpanel_typicalRequire_m_Input, histroyForm]
		});
			this.items = [this.tab];
		} else {
			this.items = [this.formpanel_typicalRequire_m_Input];
		}
		PageTemplates.superclass.initComponent.call(this);  
		var curDataId = this.dataId;
		Ext.getCmp('SpecialRequirement$oldApplyCombo').addListener('collapse',
				function(box) {
					var newv = box.getValue();
					if (newv != ""&&curDataId=="") {
						window.location = webContext + "/requireAction_toProcessEnterPage.action?serviceItemProcessId="+spId+"&oldId="+newv;
					}
				});	
//add by zhangzy for ��ʾ������ǩ�� in 20091118 begin
		Ext.getCmp('SpecialRequirement$flatCombo').on('select',function(){
			var flatId = Ext.getCmp('SpecialRequirement$flatCombo').getValue();
			Ext.Ajax.request({
					url : webContext + '/SRAction_getCadreBizAudit.action?flatId=' +flatId,
					success : function(response, options) {
						var responseArray = Ext.util.JSON.decode(response.responseText);
						var auditUser = responseArray.auditUser;
						Ext.Msg.alert("������ǩ��", auditUser);
					},
					failure : function(response, options) {
						Ext.Msg.alert("��ʾ", "��ȡ������ǩ��ʧ��");
					}
				}, this);
		});
//add by zhangzy for ��ʾ������ǩ�� in 20091118 end		
	}
})