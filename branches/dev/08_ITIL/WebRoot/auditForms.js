// ������
function showAuditWindow1(taskId, taskName, applyType, applyId) {
	xdgAuditForm("�µ������", taskId);
}
//����˵Ĵ��ڣ��ڲ����������Ϣ�����߿���ʹ��sheet��ʽ����
function showAuditWindow(taskId,taskName,applyType,applyId,pageUrl,reqClass,goStartState){
	//�����ҵ����Ϣurl
	//��Ҫ��ر�־��
	this.goBack = taskName == "���������"?"****back=Y":"";
	this.buttonText = "����";
	if (taskName == "���������") {
		this.buttonText = "����";
	}
	this.url = webContext + pageUrl;	
	this.auditContentWin = new Ext.Window({
		title : '�������ݴ���',
		modal : true,
		height : 500,
		layout:'fit',
		width : 800,
		resizable : true,
		maximizable : true,
		//���ӱ��ڵ�ļ�ǩ��
		addMarkUser : function(){
		  var addMarkUrl= webContext + "/infoAdmin/workflow/configPage/addMarkUser.jsp";
		  var auditFormWin = new Ext.Window({
		  	    title : '����ǰ�ڵ�ָ�ɼ�ǩ��',
				modal : true,
				height : 345,
			    width : 735,
				resizable : true,
				autoScroll : true,
				viewConfig : {
					autoFill : true,
					forceFit : true
				},
				buttonAlign : 'center',
				autoLoad : {
					url : webContext + "/tabFrame.jsp?url="+addMarkUrl+"?taskId="+ taskId,
					text : "ҳ�����ڼ�����......",
					method : 'post',
					scope : this
				},
				layout : 'fit',
				buttons: [{
					xtype : 'button',
					style : 'margin:4px 10px 4px 0',
					text : '�ر�',
					scope : this,
					iconCls : 'add',
					handler : function(){
					  auditFormWin.close();
					} 
				},{
					xtype : 'button',
					style : 'margin:4px 10px 4px 0',
					text : '����',
					scope : this,
					iconCls : 'save',
					handler : function(){
					  var iframeTest = window.frames["iframeTest"];//document.frames["iframeTest"];   
                	  iframeTest.test();//����Ǹ����÷�����һ��Ҫһ�£����⻹��Ҫ��Ҫ��Χ
					} 
				}]
				
			});
			//this.auditFormWin.setPagePosition(400, 100);
			auditFormWin.show();
		  
		},
		//�����¸��ڵ�ļ�ǩ��
		addMarkUserToNext : function(){
		  var addMarkUrl= webContext + "/infoAdmin/workflow/configPage/addMarkUserToNextNode.jsp";
		  var auditFormWin = new Ext.Window({
		  	    title : '����һ���ڵ�ָ�ɼ�ǩ��',
				modal : true,
				height : 345,
			    width : 735,
				resizable : true,
				autoScroll : true,
				viewConfig : {
					autoFill : true,
					forceFit : true
				},
				buttonAlign : 'center',
				autoLoad : {
					url : webContext + "/tabFrame.jsp?url="+addMarkUrl+"?taskId="+ taskId,
					text : "ҳ�����ڼ�����......",
					method : 'post',
					scope : this
				},
				layout : 'fit',
				buttons: [{
					xtype : 'button',
					style : 'margin:4px 10px 4px 0',
					text : '�ر�',
					scope : this,
					iconCls : 'remove',
					iconCls : 'add',
					handler : function(){
					  auditFormWin.close();
				}}]
				
			});
			//this.auditFormWin.setPagePosition(400, 100);
			auditFormWin.show();
		  
		},
		//���������ͨ������form
		audit : function() {//���������ť��ʱ�����
			Ext.Ajax.request({
				url : webContext + '/extjs/workflow?method=audit&id=' + taskId,
				success : function(response, options) {
					var data = eval("(" + response.responseText + ")");
					var forName = data.formName;
					var formScript = "";
					if (applyType == "applyCancel"
							|| applyType == "stockCancel"
							|| applyType == "bidCancel"
							|| applyType == "aapCancel"
							|| applyType == "contractCancel") {// �ǳ��������ʱ��
						formScript = data.formName + "('" + taskName + "','"
								+ taskId + "','isCancel');";
					} else {// �������������
						formScript = data.formName + "('" + taskName + "','"
								+ taskId + "');";
					}
					eval(formScript);
				},
				failure : function() {
					Ext.MessageBox.alert("��ʾ��Ϣ��", "��ȡ��������ʧ��");
				}
			});
		},
		//���������ֻ��ͬ�ⲻ�ܾܾ����������
		//modify by lee for packing function in 20090707 begin
		//specialAudit : function(taskId, titles) {//���������ť��ʱ�����
		specialAudit : function() {//���������ť��ʱ�����
		//modify by lee for packing function in 20090707 end
				this.form = new Ext.FormPanel({
				layout : 'table',
				layoutConfig : {
					columns : 7
				},
				frame : true,
				bodyStyle : 'padding:5px 5px 5px 10px',
				width : 'auto',
				height : 'auto',
				isFormField : true,
				items : [{
					html : '�������:',
					colspan : 7,
					width : 'auto',
					height : 25,
					style : 'font-size:9pt;text-align:left'
				}, new Ext.form.TextArea({
					fieldLabel : '&nbsp;�������',
					height : 150,
					width : 430,
					name : 'comment',
					colspan : 7
				}),
				{
					xtype : 'hidden',
					name : 'result',
					value : 'Y'
				}, 
				{
					xtype : 'hidden',
					name : 'done',
					value : 'yes'
				}, {
					xtype : 'hidden',
					name : 'id',
					value : taskId
				}]
			});
			showSpecialAuditForm(this.form, 475, 300, taskName);
		},
		//add by guangsa for ֻ�ܾܾ���� in 20090730 begin
		//���������ֻ�ܾܾ�����ͬ����������
		specialNoAudit : function(){
			this.form = new Ext.FormPanel({
				layout : 'table',
				layoutConfig : {
					columns : 7
				},
				frame : true,
				bodyStyle : 'padding:5px 5px 5px 10px',
				width : 'auto',
				height : 'auto',
				isFormField : true,
				items : [{
					html : '�������:',
					colspan : 7,
					width : 'auto',
					height : 25,
					style : 'font-size:9pt;text-align:left'
				}, new Ext.form.TextArea({
					fieldLabel : '&nbsp;�������',
					height : 150,
					width : 430,
					name : 'comment',
					colspan : 7
				}),
				{
					xtype : 'hidden',
					name : 'result',
					value : 'N'
				}, 
				{
					xtype : 'hidden',
					name : 'done',
					value : 'yes'
				}, {
					xtype : 'hidden',
					name : 'id',
					value : taskId
				}]
			});
			showSpecialAuditForm(this.form, 475, 300, taskName);
		},
		//add by guangsa for ֻ�ܾܾ���� in 20090730 end
		//��������Ӧ���̻��˵���ʼ�ڵ㣬Ȼ������Ӧ�Ĵ�ز���
		goStartStateAudit : function(vProcessId,nodeId,processId,titles){
			this.form = new Ext.FormPanel({
				id : 'defaultAudit',
				layout : 'table',
				layoutConfig : {
					columns : 7
				},
				frame : true,
				bodyStyle : 'padding:5px 5px 5px 10px',
				width : 'auto',
				height : 'auto',
				isFormField : true,
				items : [{
					html : '�������:',
					colspan : 7,
					width : 'auto',
					height : 25,
					style : 'font-size:9pt;text-align:left'
				}, new Ext.form.TextArea({
					fieldLabel : '&nbsp;�������',
					height : 150,
					width : 430,
					name : 'comment',
					colspan : 7
				}),{
					html : '',
					style : 'width:60'
				},		// ����λ��
				{
					xtype : 'hidden',
					name : 'done',
					value : 'yes'
				}, {
					xtype : 'hidden',
					name : 'vProcessId',
					value : vProcessId
				}, {
					xtype : 'hidden',
					name : 'nodeId',
					value : nodeId
				},{
					xtype : 'hidden',
					name : 'processId',
					value : processId
				}]
		});
		showGoStartStateWorkflowAuditForm(this.form, 475, 300, taskName,vProcessId,nodeId,processId);
	},
		//��������Ӧ�����ҵ�����ֻ���û���ȷ������ͨ�����Żᱣ�沢�������̣������ͬ������򲻻ᱣ�棬ֱ��������
		saveAndAudit : function(taskId, titles){
			this.form = new Ext.FormPanel({
				id : 'defaultAudit',
				layout : 'table',
				layoutConfig : {
					columns : 7
				},
				frame : true,
				bodyStyle : 'padding:5px 5px 5px 10px',
				width : 'auto',
				height : 'auto',
				isFormField : true,
				items : [{
					html : '�������:',
					colspan : 7,
					width : 'auto',
					height : 25,
					style : 'font-size:9pt;text-align:left'
				}, new Ext.form.TextArea({
					fieldLabel : '&nbsp;�������',
					height : 150,
					width : 430,
					name : 'comment',
					colspan : 7
				}),{
					html : 'ͬ��:',
					style : 'font-size:9pt;width:80;text-align:right;margin:15 0 20 20'
				}, new Ext.form.Radio({
					xtype : 'radio',
					width : 20,
					name : 'result',
					inputValue : 'Y',
					checked : true,
					fieldLabel : 'ͬ��'
				}), {
					html : '�ܾ�:',
					style : 'font-size:9pt;width:60;text-align:right;margin:15 0 20 20'
				}, new Ext.form.Radio({
					xtype : 'radio',
					width : 20,
					name : 'result',
					checked : false,
					inputValue : 'N',
					fieldLabel : '�ܾ�'
				}), {
					html : '',
					style : 'width:60'
				},		// ����λ��
				{
					xtype : 'hidden',
					name : 'done',
					value : 'yes'
				}, {
					xtype : 'hidden',
					name : 'id',
					value : taskId
				}]
		});
		saveAndAuditForm(this.form, 475, 300, taskName);
	},
	//���������������Ծ���˵����
	skipWorkflowAudit : function(taskId, titles) {//���������ť��ʱ�����
			this.form = new Ext.FormPanel({
				layout : 'table',
				layoutConfig : {
					columns : 8
				},
				frame : true,
				bodyStyle : 'padding:5px 5px 5px 10px',
				width : 'auto',
				height : 'auto',
				isFormField : true,
				items : [{
					html : '�������:',
					colspan : 8,
					width : 'auto',
					height : 25,
					style : 'font-size:9pt;text-align:left'
				}, new Ext.form.TextArea({
					fieldLabel : '&nbsp;�������',
					height : 150,
					width : 430,
					name : 'comment',
					colspan : 7
				}), {
						html : 'ͬ��:',
						style : 'font-size:9pt;width:80;text-align:right;margin:15 0 20 20'
					}, new Ext.form.Radio({
						xtype : 'radio',
						width : 20,
						name : 'result',
						inputValue : 'Y',
						checked : true,
						fieldLabel : 'ͬ��'
					}), {
						html : '���:',
						style : 'font-size:9pt;width:60;text-align:right;margin:15 0 20 20'
					}, new Ext.form.Radio({
						xtype : 'radio',
						width : 20,
						name : 'result',
						checked : false,
						inputValue : 'N',
						fieldLabel : '���'
					}), {
						html : '',
						style : 'width:60'
					},		// ����λ��
					{
						xtype : "combo",
						id : "skipNodeMessage",
						fieldLabel : "ҳ��ģ������:",
						valueField : "name",
						displayField : "name",
						listWidth : 200,
						maxHeight : 200,
						emptyText: '��ѡ��',
						mode : 'remote',
						forceSelection : true,
						hiddenName : "name",
						editable : true,
						triggerAction : 'all', 
						lazyRender: true,
			            typeAhead: false,
			            autoScroll:true,
						allowBlank : true,
						name : "name",
						selectOnFocus: true,
						width : 200,
						// *******************************************************************
						store : new Ext.data.JsonStore({
							id : Ext.id(),
							url : webContext
									+'/extjs/workflow?method=getAllNodeMessagek&taskId='+taskId,
							fields: ['id','name'],
							totalProperty:'rowCount',
							root:'data',
							id:'id',			
							sortInfo: {field: "name", direction: "ASC"}
						})
				},
				{
					xtype : 'hidden',
					name : 'result',
					value : 'Y'
				}, 
				{
					xtype : 'hidden',
					name : 'done',
					value : 'yes'
				}, {
					xtype : 'hidden',
					name : 'id',
					value : taskId
				}]
			});
			showSkipWorkflowAuditForm(this.form, 475, 300, taskName,taskId);
		},
		id : "auditContentWindow",
		draggable : true,
		autoLoad : {// "****taskId="+ taskId + 
			// װ�������й���Ϣ���������й�,�˴��ɰ��������ƴ��ݹ�ȥ��auditInfo.jsp�����������ƾ����Ƿ���Ҫ�޸���������
			url : webContext + "/tabFrame.jsp?url=" + this.url + "****dataId="
					+ applyId + "****reqClass=" + reqClass +"****goStartState="+goStartState,
			text : "ҳ�����ڼ�����......",
			method : 'post',
			scope : this
		},
		viewConfig : {
			autoFill : true,
			forceFit : true
		},
		layout : 'fit',
		buttonAlign : 'center',
		items : [{
			html : "���ڼ���ҳ������......"
		}]
	});
	this.auditContentWin.setPagePosition(200, 60);
	this.auditContentWin.show();
}

//���¼���˵Ĵ��ڣ��ڲ����������Ϣ�����߿���ʹ��sheet��ʽ����
function showEventAuditWindow(taskId,eventName,taskName,applyType,applyId,pageUrl,reqClass){
	//�����ҵ����Ϣurl
	//��Ҫ��ر�־��
	this.goBack = taskName == "���������"?"****back=Y":"";
	this.buttonText = "����";
	if (taskName == "���������") {
		this.buttonText = "����";
	}
	this.url = webContext + pageUrl;

	this.auditContentWin = new Ext.Window({
		title : '�������ݴ���',
		modal : true,
		height : 500,
		width : 800,
		resizable : true,
		maximizable : true,
		audit : function() {
			Ext.Ajax.request({
				url : webContext + '/extjs/workflow?method=audit&id=' + taskId,
				success : function(response, options) {
					var data = eval("(" + response.responseText + ")");
					var forName = data.formName;
					var formScript = "";
					if (applyType == "applyCancel"
							|| applyType == "stockCancel"
							|| applyType == "bidCancel"
							|| applyType == "aapCancel"
							|| applyType == "contractCancel") {// �ǳ��������ʱ��
						formScript = data.formName + "('" + taskName + "','"
								+ taskId + "','isCancel');";
					} else {// �������������
						formScript = data.formName + "('" + taskName + "','"
								+ taskId + "');";
					}
					eval(formScript);
				},
				failure : function() {
					Ext.MessageBox.alert("��ʾ��Ϣ��", "��ȡ��������ʧ��");
				}
			});
		},
		id : "auditContentWindow",
		draggable : true,
		autoLoad : {
			// װ�������й���Ϣ���������й�,�˴��ɰ��������ƴ��ݹ�ȥ��auditInfo.jsp�����������ƾ����Ƿ���Ҫ�޸���������
			url : webContext + "/tabFrame.jsp?url=" + this.url + "****dataId="
					+ applyId + "****reqClass=" + reqClass + "****taskId="
					+ taskId,
			text : "ҳ�����ڼ�����......",
			method : 'post',
			scope : this
		},
		viewConfig : {
			autoFill : true,
			forceFit : true
		},
		layout : 'fit',
		buttonAlign : 'center',
		items : [{
			html : "���ڼ���ҳ������......"
		}]
	});
	// this.auditContentWin.setPagePosition(210,117);
	this.auditContentWin.setPagePosition(200, 60);
	this.auditContentWin.show();
}

//�򿪹�����˵Ĵ���
function showNoticeAuditWindow(taskId,taskName,applyType,applyId,pageUrl,reqClass,goStartState){
	this.goBack = taskName == "���������"?"****back=Y":"";
	this.buttonText = "����";
	if (taskName == "���������") {
		this.buttonText = "����";
	}
	this.url = webContext + pageUrl;	
	this.auditContentWin = new Ext.Window({
		title : '�������ݴ���',
		modal : true,
		height : 500,
		width : 800,
		autoScroll:true,
		resizable : true,
		maximizable : true,
		//���ӱ��ڵ�ļ�Ǯ��
		audit : function() {
			this.form = new Ext.FormPanel({
				id : 'noticeAudit',
				layout : 'table',
				layoutConfig : {
					columns : 7
				},
				frame : true,
				bodyStyle : 'padding:5px 5px 5px 10px',
				width : 'auto',
				height : 'auto',
				isFormField : true,
				items : [{
					html : '�������<font color=red>(����)</font>:',
					colspan : 7,
					width : 'auto',
					height : 25,
					style : 'font-size:9pt;text-align:left'
				}, new Ext.form.TextArea({
					id : 'noticeAuditComment',
					fieldLabel : '&nbsp;�������',
					height : 150,
					width : 430,
					name : 'comment',
					colspan : 7
				}), {
					html : 'ͬ��:',
					style : 'font-size:9pt;width:80;text-align:right;margin:15 0 20 20'
				}, new Ext.form.Radio({
					xtype : 'radio',
					width : 20,
					name : 'result',
					inputValue : 'Y',
					checked : true,
					fieldLabel : 'ͬ��'
				}), {
					html : '�ܾ�:',
					style : 'font-size:9pt;width:60;text-align:right;margin:15 0 20 20'
				}, new Ext.form.Radio({
					xtype : 'radio',
					width : 20,
					name : 'result',
					checked : false,
					inputValue : 'N',
					fieldLabel : '�ܾ�'
				}), {
					html : '',
					style : 'width:60'
				},		// ����λ��
				{
					xtype : 'hidden',
					name : 'done',
					value : 'yes'
				}, {
					xtype : 'hidden',
					name : 'id',
					value : taskId
				}]
			});
			showNoticeAuditForm(this.form, 475, 300, taskName);
		},
		addMarkUser : function(){
		  var addMarkUrl= webContext + "/infoAdmin/workflow/configPage/addMarkUser.jsp";
		  var auditFormWin = new Ext.Window({
		  	    title : '����ǰ�ڵ�ָ�ɼ�ǩ��',
				modal : true,
				height : 345,
			    width : 735,
				resizable : true,
				autoScroll : true,
				viewConfig : {
					autoFill : true,
					forceFit : true
				},
				buttonAlign : 'center',
				autoLoad : {
					url : webContext + "/tabFrame.jsp?url="+addMarkUrl+"?taskId="+ taskId,
					text : "ҳ�����ڼ�����......",
					method : 'post',
					scope : this
				},
				layout : 'fit',
				buttons: [{
					xtype : 'button',
					style : 'margin:4px 10px 4px 0',
					text : '�ر�',
					scope : this,
					iconCls : 'add',
					handler : function(){
					  auditFormWin.close();
					} 
				},{
					xtype : 'button',
					style : 'margin:4px 10px 4px 0',
					text : '����',
					scope : this,
					iconCls : 'save',
					handler : function(){
					  var iframeTest = window.frames["iframeTest"];//document.frames["iframeTest"];   
                	  iframeTest.test();//����Ǹ����÷�����һ��Ҫһ�£����⻹��Ҫ��Ҫ��Χ
					} 
				}]
				
			});
			//this.auditFormWin.setPagePosition(400, 100);
			auditFormWin.show();
		  
		},
		//�����¸��ڵ�ļ�ǩ��
		addMarkUserToNext : function(){
		  var addMarkUrl= webContext + "/infoAdmin/workflow/configPage/addMarkUserToNextNode.jsp";
		  var auditFormWin = new Ext.Window({
		  	    title : '����һ���ڵ�ָ�ɼ�ǩ��',
				modal : true,
				height : 345,
			    width : 735,
				resizable : true,
				autoScroll : true,
				viewConfig : {
					autoFill : true,
					forceFit : true
				},
				buttonAlign : 'center',
				autoLoad : {
					url : webContext + "/tabFrame.jsp?url="+addMarkUrl+"?taskId="+ taskId,
					text : "ҳ�����ڼ�����......",
					method : 'post',
					scope : this
				},
				layout : 'fit',
				buttons: [{
					xtype : 'button',
					style : 'margin:4px 10px 4px 0',
					text : '�ر�',
					scope : this,
					iconCls : 'remove',
					iconCls : 'add',
					handler : function(){
					  auditFormWin.close();
				}}]
				
			});
			//this.auditFormWin.setPagePosition(400, 100);
			auditFormWin.show();
		  
		},
		//���������ֻ��ͬ�ⲻ�ܾܾ����������
		specialAudit : function() {//���������ť��ʱ�����
				this.form = new Ext.FormPanel({
				layout : 'table',
				layoutConfig : {
					columns : 7
				},
				frame : true,
				bodyStyle : 'padding:5px 5px 5px 10px',
				width : 'auto',
				height : 'auto',
				isFormField : true,
				items : [{
					html : '�������:',
					colspan : 7,
					width : 'auto',
					height : 25,
					style : 'font-size:9pt;text-align:left'
				}, new Ext.form.TextArea({
					fieldLabel : '&nbsp;�������',
					height : 150,
					width : 430,
					name : 'comment',
					colspan : 7
				}),
				{
					xtype : 'hidden',
					name : 'result',
					value : 'Y'
				}, 
				{
					xtype : 'hidden',
					name : 'done',
					value : 'yes'
				}, {
					xtype : 'hidden',
					name : 'id',
					value : taskId
				}]
			});
			showSpecialAuditForm(this.form, 475, 300, taskName);
		},

		id : "auditContentWindow",
		draggable : true,
		autoLoad : {// "****taskId="+ taskId + 
			// װ�������й���Ϣ���������й�,�˴��ɰ��������ƴ��ݹ�ȥ��auditInfo.jsp�����������ƾ����Ƿ���Ҫ�޸���������
			url : webContext + "/tabFrame.jsp?url=" + this.url + "****dataId="
					+ applyId + "****reqClass=" + reqClass +"****goStartState="+goStartState,
			text : "ҳ�����ڼ�����......",
			method : 'post',
			scope : this
		},
		viewConfig : {
			autoFill : true,
			forceFit : true
		},
		layout : 'fit',
		buttonAlign : 'center',
		items : [{
			html : "���ڼ���ҳ������......"
		}]
	});
	this.auditContentWin.setPagePosition(200, 60);
	this.auditContentWin.show();
}

function showNoticeAuditForm(auditForm, width, height, title) {
	if (title == "���������") {
		title = "�����˴���������";
	}
	this.noticeAuditFormWin = new Ext.Window({
		title : title,
		modal : true,
		height : height,
		width : width,
		resizable : true,
		autoScroll : true,
		autoScroll : true,
		viewConfig : {
			autoFill : true,
			forceFit : true
		},
		layout : 'fit',
		buttonAlign : 'center',
		buttons : [{
			xtype : 'button',
			handler : function() {
				var temp = Ext.getCmp('noticeAuditComment').getValue();
				if(temp == ''){
					Ext.Msg.alert("��ʾ", "������д���������");
					return;
				}
				auditForm.getForm().submit({
					method : "get",
					params : getFormParam(auditForm.getId()),
					url : webContext + '/extjs/workflow?method=audit&done',
					success : function(action, form) {
						var jsonobject = Ext.util.JSON.decode(form.response.responseText);//����form�����е�ץȡ����ֵ�ķ���
						if (jsonobject.Exception != undefined) {
							Ext.Msg.alert("��ʾ", "�����ύʧ�ܣ��쳣��Ϣ���£������ù���Ա������̣�", function() {
								Ext.Msg.alert("��ʾ",jsonobject.Exception);
							});
						} else {
							Ext.Msg.alert("��ʾ", "�����ύ�ɹ�", function() {
							});
						}
						if (jsonobject.id == 'role') {// �����û���дԤָ����Ա
							var nextNodeName = jsonobject.nextNodeName;
							this.popUserAssignWin(nextNodeName);
						}
						var auditContentWindow = Ext.getCmp("auditContentWindow");
						if (auditContentWindow) {
							auditContentWindow.close();
						}
						var taskGridInMainPanel = Ext.getCmp("porlet-taskGridInMainPanel");
						if (taskGridInMainPanel) {taskGridInMainPanel.store.reload();
						}
						var postOrderGridInMainPanel = Ext.getCmp("postOrderGridInMainPanel");
						if (postOrderGridInMainPanel) {
							postOrderGridInMainPanel.store.reload();
						}
						//add by lee for close the window after confirm in 20090924 begin
						var curpath = window.location.pathname;
						if(curpath=='/infoAdmin/workflow/configPage/auditFromMail.jsp'){
							window.close();
						}
						//add by lee for close the window after confirm in 20090924 end
					},
					failure : function(response, options) {
						Ext.MessageBox.alert("��ʾ��Ϣ��", "�ύ����ʧ��");
					},
					scope : this,
					clientValidation : false
						// �Ժ�ĳ�true
			});
				this.noticeAuditFormWin.close();
				Ext.getCmp("auditContentWindow").close();
			},
			text : 'ȷ��',
			scope : this
		}
		, {
			xtype : 'button',
			handler : function() {
				this.noticeAuditFormWin.close();
//				Ext.getCmp("auditContentWindow").close();
				//add by lee for close the window after confirm in 20090922 begin
				var curpath = window.location.pathname;
				if(curpath=='/infoAdmin/workflow/configPage/auditFromMail.jsp'){
					window.close();
				}
				//add by lee for close the window after confirm in 20090922 end
			},
			text : 'ȡ��',
			scope : this
		}
		],
		items : [auditForm]
	});
	this.noticeAuditFormWin.setPagePosition(400, 100);
	this.noticeAuditFormWin.show();
}

//��������Ӧ�����ҵ�����ֻ���û���ȷ������ͨ�����Żᱣ�沢�������̣������ͬ������򲻻ᱣ�棬ֱ��������
function saveAndAuditForm(auditForm, width, height, title){
	if (title == "���������") {
		title = "�����˴���������";
	}
	this.auditFormWin = new Ext.Window({
		title : title,
		modal : true,
		height : height,
		width : width,
		resizable : true,
		autoScroll : true,
		autoScroll : true,
		viewConfig : {
			autoFill : true,
			forceFit : true
		},
		layout : 'fit',
		buttonAlign : 'center',
		buttons : [{
			xtype : 'button',
			handler : function() {
				var auditresult = getFormParam(auditForm.getId()).result;
				if(auditresult=='Y'){
					var iframeTest = window.frames["iframeTest"];//document.frames["iframeTest"];   
                	iframeTest.test();//����Ǹ����÷�����һ��Ҫһ�£����⻹��Ҫ��Ҫ��Χ
                	//return;
				}
				auditForm.getForm().submit({
					method : "get",
					params : getFormParam(auditForm.getId()),
					url : webContext + '/extjs/workflow?method=audit&done',// +Ext.urlEncode(getFormParam(auditForm.getId())),
					success : function(action, form) {

						var jsonobject = Ext.util.JSON
								.decode(form.response.responseText);
						if (jsonobject.id == 'role') {// �����û���дԤָ����Ա
							var nextNodeName = jsonobject.nextNodeName;
							this.popUserAssignWin(nextNodeName);
						}
						var auditContentWindow = Ext
								.getCmp("auditContentWindow");
						if (auditContentWindow) {
							auditContentWindow.close();
						}
						var taskGridInMainPanel = Ext
								.getCmp("porlet-taskGridInMainPanel");
						if (taskGridInMainPanel) {
							taskGridInMainPanel.store.reload();
						}
						var postOrderGridInMainPanel = Ext
								.getCmp("postOrderGridInMainPanel");
						if (postOrderGridInMainPanel) {
							postOrderGridInMainPanel.store.reload();
						}
						//add by lee for close the window after confirm in 20090924 begin
						var curpath = window.location.pathname;
						if(curpath=='/infoAdmin/workflow/configPage/auditFromMail.jsp'){
							window.close();
						}
						//add by lee for close the window after confirm in 20090924 end
					},
					failure : function(response, options) {
						Ext.MessageBox.alert("��ʾ��Ϣ��", "�ύ����ʧ��");
					},
					scope : this,
					clientValidation : false
						// �Ժ�ĳ�true
						});
				this.auditFormWin.close();
				Ext.getCmp("auditContentWindow").close();
			},
			text : 'ͬ��',
			scope : this
		}
		, {
			xtype : 'button',
			handler : function() {
				this.auditFormWin.close();
//				Ext.getCmp("auditContentWindow").close();
				//add by lee for close the window after confirm in 20090922 begin
				var curpath = window.location.pathname;
				if(curpath=='/infoAdmin/workflow/configPage/auditFromMail.jsp'){
					window.close();
				}
				//add by lee for close the window after confirm in 20090922 end
			},
			text : 'ȡ��',
			scope : this
		}
		],
		items : [auditForm]
	});
	this.auditFormWin.setPagePosition(400, 100);
	this.auditFormWin.show();
}
//���������ֻ��ͬ�ⲻ�ܾܾ����������
function showSpecialAuditForm(auditForm, width, height, title) {
	if (title == "���������") {
		title = "�����˴���������";
	}
	this.auditFormWin = new Ext.Window({
		title : title,
		modal : true,
		height : height,
		width : width,
		resizable : true,
		autoScroll : true,
		autoScroll : true,
		// renderTo:'foo',
		viewConfig : {
			autoFill : true,
			forceFit : true
		},
		layout : 'fit',
		buttonAlign : 'center',
		buttons : [{
			xtype : 'button',
			handler : function() {
				auditForm.getForm().submit({
					method : "get",
					params : getFormParam(auditForm.getId()),
					url : webContext + '/extjs/workflow?method=audit&done',// +Ext.urlEncode(getFormParam(auditForm.getId())),
					success : function(action, form) {
						var jsonobject = Ext.util.JSON.decode(form.response.responseText);//����form�����е�ץȡ����ֵ�ķ���
						if (jsonobject.Exception != undefined) {
							Ext.Msg.alert("��ʾ", "�����ύʧ�ܣ��쳣��Ϣ����:\""+jsonobject.Exception+"\"�������ù���Ա������̣�", function() {
							});
						} else {
							Ext.Msg.alert("��ʾ", "�����ύ�ɹ�", function() {
							});
						}
						var jsonobject = Ext.util.JSON
								.decode(form.response.responseText);
						if (jsonobject.id == 'role') {// �����û���дԤָ����Ա
							var nextNodeName = jsonobject.nextNodeName;
							this.popUserAssignWin(nextNodeName);
						}
						var auditContentWindow = Ext
								.getCmp("auditContentWindow");
						if (auditContentWindow) {
							auditContentWindow.close();
						}
						var taskGridInMainPanel = Ext
								.getCmp("porlet-taskGridInMainPanel");
						if (taskGridInMainPanel) {
							taskGridInMainPanel.store.reload();
						}
						var postOrderGridInMainPanel = Ext
								.getCmp("postOrderGridInMainPanel");
						if (postOrderGridInMainPanel) {
							postOrderGridInMainPanel.store.reload();
						}
						//add by lee for close the window after confirm in 20090924 begin
						var curpath = window.location.pathname;
						if(curpath=='/infoAdmin/workflow/configPage/auditFromMail.jsp'){
							window.close();
						}
						//add by lee for close the window after confirm in 20090924 end
					},
					failure : function(response, options) {
						Ext.MessageBox.alert("��ʾ��Ϣ��", "�ύ����ʧ��");
					},
					scope : this,
					clientValidation : false
						// �Ժ�ĳ�true
						});
				this.auditFormWin.close();
				Ext.getCmp("auditContentWindow").close();
			},
			text : 'ȷ��',
			scope : this
		}
		, {
			xtype : 'button',
			handler : function() {
				this.auditFormWin.close();
//				Ext.getCmp("auditContentWindow").close();
				//add by lee for close the window after confirm in 20090922 begin
				var curpath = window.location.pathname;
				if(curpath=='/infoAdmin/workflow/configPage/auditFromMail.jsp'){
					window.close();
				}
				//add by lee for close the window after confirm in 20090922 end
			},
			text : 'ȡ��',
			scope : this
		}
		],
		items : [auditForm]
	});
	this.auditFormWin.setPagePosition(400, 100);
	this.auditFormWin.show();
}
//��ʾ������������
function showSkipWorkflowAuditForm(auditForm, width, height, title, taskId) {
	if (title == "���������") {
		title = "�����˴���������";
	}
	this.auditFormWin = new Ext.Window({
		title : title,
		modal : true,
		height : height,
		width : width,
		resizable : true,
		autoScroll : true,
		autoScroll : true,
		// renderTo:'foo',
		viewConfig : {
			autoFill : true,
			forceFit : true
		},
		layout : 'fit',
		buttonAlign : 'center',
		buttons : [{
			xtype : 'button',
			handler : function() {
				auditForm.getForm().submit({
					method : "get",
					params : getFormParam(auditForm.getId()),
					url : webContext + '/extjs/workflow?method=audit&done',// +Ext.urlEncode(getFormParam(auditForm.getId())),
					success : function(action, form) {
						var jsonobject = Ext.util.JSON.decode(form.response.responseText);
					},
					failure : function(response, options) {
						Ext.MessageBox.alert("��ʾ��Ϣ��", "�ύ����ʧ��");
					},
					scope : this,
					clientValidation : false
						// �Ժ�ĳ�true
						});
				this.auditFormWin.close();
				Ext.getCmp("auditContentWindow").close();
			},
			text : 'ͬ��',
			scope : this
		},
		{
			xtype : 'button',
			handler : function() {
				var nodeName = Ext.getCmp("skipNodeMessage").getValue();
				auditForm.getForm().submit({
					method : "post",
					params : {
						'fromNodeName':unicode(nodeName),
						'taskId' :taskId
					},
					url : webContext + '/extjs/workflow?method=getWorkFlowSkipGoBack',// +Ext.urlEncode(getFormParam(auditForm.getId())),
					success : function(action, form) {
						var jsonobject = Ext.util.JSON.decode(form.response.responseText);
					},
					failure : function(response, options) {
						Ext.MessageBox.alert("��ʾ��Ϣ��", "�ύ����ʧ��");
					},
					scope : this,
					clientValidation : false
						// �Ժ�ĳ�true
				});
				this.auditFormWin.close();
//				Ext.getCmp("auditContentWindow").close();
			},
			text : 'ȡ��',
			scope : this
		}
		],
		items : [auditForm]
	});
	this.auditFormWin.setPagePosition(400, 100);
	this.auditFormWin.show();
}


// �������Ի��򣬽����������ڲ�����
function showAuditForm(auditForm, width, height, title) {
	if (title == "���������") {
		title = "�����˴���������";
	}
	this.auditFormWin = new Ext.Window({
		title : title,
		modal : true,
		height : height,
		width : width,
		resizable : true,
		autoScroll : true,
		autoScroll : true,
		viewConfig : {
			autoFill : true,
			forceFit : true
		},
		layout : 'fit',
		buttonAlign : 'center',
		buttons : [{
			xtype : 'button',
			handler : function() {
				auditForm.getForm().submit({
					method : "get",
					params : getFormParam(auditForm.getId()),
					url : webContext + '/extjs/workflow?method=audit&done',
					success : function(action, form) {
						var jsonobject = Ext.util.JSON.decode(form.response.responseText);//����form�����е�ץȡ����ֵ�ķ���
						if (jsonobject.Exception != undefined) {
							Ext.Msg.alert("��ʾ", "�����ύʧ�ܣ��쳣��Ϣ���£������ù���Ա������̣�", function() {
								Ext.Msg.alert("��ʾ",jsonobject.Exception);
							});
						} else {
							Ext.Msg.alert("��ʾ", "�����ύ�ɹ�", function() {
							});
						}
						if (jsonobject.id == 'role') {// �����û���дԤָ����Ա
							var nextNodeName = jsonobject.nextNodeName;
							this.popUserAssignWin(nextNodeName);
						}
						var auditContentWindow = Ext
								.getCmp("auditContentWindow");
						if (auditContentWindow) {
							auditContentWindow.close();
						}
						var taskGridInMainPanel = Ext
								.getCmp("porlet-taskGridInMainPanel");
						if (taskGridInMainPanel) {
							taskGridInMainPanel.store.reload();
						}
						var postOrderGridInMainPanel = Ext
								.getCmp("postOrderGridInMainPanel");
						if (postOrderGridInMainPanel) {
							postOrderGridInMainPanel.store.reload();
						}
						//add by lee for close the window after confirm in 20090924 begin
						var curpath = window.location.pathname;
						if(curpath=='/infoAdmin/workflow/configPage/auditFromMail.jsp'){
							window.close();
						}
						//add by lee for close the window after confirm in 20090924 end
					},
					failure : function(response, options) {
						Ext.MessageBox.alert("��ʾ��Ϣ��", "�ύ����ʧ��");
					},
					scope : this,
					clientValidation : false
						// �Ժ�ĳ�true
			});
				this.auditFormWin.close();
				Ext.getCmp("auditContentWindow").close();
			},
			text : 'ȷ��',
			scope : this
		}
		, {
			xtype : 'button',
			handler : function() {
				this.auditFormWin.close();
//				Ext.getCmp("auditFormWin").close();
				//add by lee for close the window after confirm in 20090922 begin
				var curpath = window.location.pathname;
				if(curpath=='/infoAdmin/workflow/configPage/auditFromMail.jsp'){
					window.close();
				}
				//add by lee for close the window after confirm in 20090922 end
			},
			text : 'ȡ��',
			scope : this
		}
		],
		items : [auditForm]
	});
	this.auditFormWin.setPagePosition(400, 100);
	this.auditFormWin.show();
}

// ȱʡ�������Ի���,ͳһ�ĵ��ø�ʽ���������ƣ������ţ�
defaultAuditForm = function(title, taskId) {
	this.form = new Ext.FormPanel({
		id : 'defaultAudit',
		layout : 'table',
		layoutConfig : {
			columns : 7
		},
		frame : true,
		bodyStyle : 'padding:5px 5px 5px 10px',
		width : 'auto',
		height : 'auto',
		isFormField : true,
		items : [{
			html : '�������:',
			colspan : 7,
			width : 'auto',
			height : 25,
			style : 'font-size:9pt;text-align:left'
		}, new Ext.form.TextArea({
			fieldLabel : '&nbsp;�������',
			height : 150,
			width : 430,
			name : 'comment',
			colspan : 7
		}), {
			html : 'ͬ��:',
			style : 'font-size:9pt;width:80;text-align:right;margin:15 0 20 20'
		}, new Ext.form.Radio({
			xtype : 'radio',
			width : 20,
			name : 'result',
			inputValue : 'Y',
			checked : true,
			fieldLabel : 'ͬ��'
		}), {
			html : '�ܾ�:',
			style : 'font-size:9pt;width:60;text-align:right;margin:15 0 20 20'
		}, new Ext.form.Radio({
			xtype : 'radio',
			width : 20,
			name : 'result',
			checked : false,
			inputValue : 'N',
			fieldLabel : '�ܾ�'
		}), {
			html : '',
			style : 'width:60'
		},		// ����λ��
		{
			xtype : 'hidden',
			name : 'done',
			value : 'yes'
		}, {
			xtype : 'hidden',
			name : 'id',
			value : taskId
		}]
	});
	showAuditForm(this.form, 475, 300, title);
}

// ����Ϊ���ƶԻ���
// ���������
backCreatorForm = function(title, taskId, isCancel) {

	this.form = new Ext.FormPanel({
		layout : 'table',
		layoutConfig : {
			columns : 5
		},
		frame : true,
		bodyStyle : 'padding:5px 5px 5px 10px',
		width : 'auto',
		height : 'auto',
		isFormField : true,
		// items:this.items
		items : [{
			html : '�������:',
			colspan : 5,
			width : 'auto',
			height : 25,
			style : 'font-size:9pt;text-align:left'
		}, new Ext.form.TextArea({
			fieldLabel : '&nbsp;�������',
			height : 150,
			width : 430,
			name : 'comment',
			colspan : 7
		}), {
			html : '�����ύ:',
			style : 'font-size:9pt;width:120;text-align:right;margin:15 0 20 20'
		}, new Ext.form.Radio({
			xtype : 'radio',
			width : 20,
			name : 'result',
			checked : true,
			inputValue : 'Y',
			fieldLabel : '�����ύ'
		}), {
			html : 'ȡ������:',
			style : 'font-size:9pt;width:120;text-align:right;margin:15 0 20 20'
		}, new Ext.form.Radio({
			xtype : 'radio',
			width : 20,
			name : 'result',
			checked : false,
			inputValue : 'N',
			fieldLabel : 'ȡ������'
		}), {
			html : '',
			style : 'width:60'
		},		// ����λ��
				{
					xtype : 'hidden',
					name : 'done',
					value : 'yes'
				}, {
					xtype : 'hidden',
					name : 'id',
					value : taskId
				}]
	});
	showAuditForm(this.form, 475, 300, title);
}

//��ʾ���̻��˵���ʼ�ڵ������form��
function showGoStartStateWorkflowAuditForm(auditForm, width, height, title, vProcessId ,nodeId, processId) {
	if (title == "���������") {
		title = "�����˴���������";
	}
	this.auditFormWin = new Ext.Window({
		title : title,
		modal : true,
		height : height,
		width : width,
		resizable : true,
		autoScroll : true,
		autoScroll : true,
		// renderTo:'foo',
		viewConfig : {
			autoFill : true,
			forceFit : true
		},
		layout : 'fit',
		buttonAlign : 'center',
		buttons : [{
			xtype : 'button',
			handler : function() {
				auditForm.getForm().submit({
					method : "post",
					params : {
						'vProcessId':vProcessId,
						'processId' :processId,
						'nodeId' :nodeId
					},
					url : webContext + '/extjs/workflow?method=StartStateAfreshSubmit',
					success : function(action, form) {
						var jsonobject = Ext.util.JSON.decode(form.response.responseText);
					},
					failure : function(response, options) {
						Ext.MessageBox.alert("��ʾ��Ϣ��", "�ύ����ʧ��");
					},
					scope : this,
					clientValidation : false
						// �Ժ�ĳ�true
						});
				this.auditFormWin.close();
				Ext.getCmp("auditContentWindow").close();
			},
			text : '�����ύ',
			scope : this
		},
		{
			xtype : 'button',
			handler : function() {
				auditForm.getForm().submit({
					method : "post",
					params : {
						'vProcessId':vProcessId,
						'processId' :processId
					},
					url : webContext + '/extjs/workflow?method=StartStateToCancelFlow',
					success : function(action, form) {
						var jsonobject = Ext.util.JSON
								.decode(form.response.responseText);
					},
					failure : function(response, options) {
						Ext.MessageBox.alert("��ʾ��Ϣ��", "�ύ����ʧ��");
					},
					scope : this,
					clientValidation : false
						// �Ժ�ĳ�true
				});
				this.auditFormWin.close();
				Ext.getCmp("auditContentWindow").close();
			},
			text : '��������',
			scope : this
		}
		],
		items : [auditForm]
	});
	this.auditFormWin.setPagePosition(400, 100);
	this.auditFormWin.show();
}




// �޽�ɫʱ���������ʾ�Ѻý���
popUserAssignWin = function(nextNodeName) {
	var win = new Ext.Window({
		title : '��ʾ����',
		modal : true,
		height : 350,
		width : 450,
		autoScroll : true,
		resizable : false,
		html : "<font color=red size=12px>��һ������ڵ�:" + nextNodeName + ",û����Ӧ�������������ҹ���Ա��ʵ��</font>",
		buttonAlign:'center',
		buttons : [{
			xtype : 'button',
			handler : function() {
				win.close();
			},
			text : '�ر�',
			scope : this
		}]

	})
	win.show();
}

//�ҵ���������
function showMeThePage(dataId, applyType, serviceItemId, dataType,processId){
	var showWin = new Ext.Window({
		title:'�ҵ�����',
		modal : true,
		height : 500,
		width : 800,
		resizable : false,
		draggable : true,
		maximizable : true,
		autoLoad : {
			url : webContext + "/tabFrame.jsp?url="
					+ webContext + "/userSelfWorkflow_showMeThePage.action?dataId="
					+ dataId + "****applyType=" + applyType + "****serviceiItemId="
					+ serviceItemId + "****knowledgeType=" + dataType+ "****processId=" + processId,
			text : "ҳ�������......",
			method : 'post',
			scripts : true,
			scope : this
		},
		viewConfig : {
			autoFill : true,
			forceFit : true
		},
		layout : 'fit',
		items : [{
			html : "���ڼ���ҳ������......"
		}],
		buttonAlign : 'center',
		buttons:[{text : "�ر�",
					xtype : 'button',
					pressed : true,
					//iconCls : "remove",
					handler : function() {
						showWin.close();
					},
					scope : this}]
	});
	showWin.setPagePosition(200, 100);
	showWin.show();
}


