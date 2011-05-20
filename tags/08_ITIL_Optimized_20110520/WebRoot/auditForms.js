// 测试用
function showAuditWindow1(taskId, taskName, applyType, applyId) {
	xdgAuditForm("下单岗审核", taskId);
}
//打开审核的窗口，内部附有审核信息，或者考虑使用sheet方式代替
function showAuditWindow(taskId,taskName,applyType,applyId,pageUrl,reqClass,goStartState){
	//申请的业务信息url
	//不要打回标志了
	this.goBack = taskName == "打回申请人"?"****back=Y":"";
	this.buttonText = "审批";
	if (taskName == "打回申请人") {
		this.buttonText = "处理";
	}
	this.url = webContext + pageUrl;	
	this.auditContentWin = new Ext.Window({
		title : '审批内容窗口',
		modal : true,
		height : 500,
		layout:'fit',
		width : 800,
		resizable : true,
		maximizable : true,
		//增加本节点的加签人
		addMarkUser : function(){
		  var addMarkUrl= webContext + "/infoAdmin/workflow/configPage/addMarkUser.jsp";
		  var auditFormWin = new Ext.Window({
		  	    title : '给当前节点指派加签人',
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
					text : "页面正在加载中......",
					method : 'post',
					scope : this
				},
				layout : 'fit',
				buttons: [{
					xtype : 'button',
					style : 'margin:4px 10px 4px 0',
					text : '关闭',
					scope : this,
					iconCls : 'add',
					handler : function(){
					  auditFormWin.close();
					} 
				},{
					xtype : 'button',
					style : 'margin:4px 10px 4px 0',
					text : '保存',
					scope : this,
					iconCls : 'save',
					handler : function(){
					  var iframeTest = window.frames["iframeTest"];//document.frames["iframeTest"];   
                	  iframeTest.test();//这个是个公用方法，一定要一致；另外还需要主要范围
					} 
				}]
				
			});
			//this.auditFormWin.setPagePosition(400, 100);
			auditFormWin.show();
		  
		},
		//增加下个节点的加签人
		addMarkUserToNext : function(){
		  var addMarkUrl= webContext + "/infoAdmin/workflow/configPage/addMarkUserToNextNode.jsp";
		  var auditFormWin = new Ext.Window({
		  	    title : '给下一个节点指派加签人',
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
					text : "页面正在加载中......",
					method : 'post',
					scope : this
				},
				layout : 'fit',
				buttons: [{
					xtype : 'button',
					style : 'margin:4px 10px 4px 0',
					text : '关闭',
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
		//这个是最普通的审批form
		audit : function() {//点击审批按钮的时候调用
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
							|| applyType == "contractCancel") {// 是撤单申请的时候
						formScript = data.formName + "('" + taskName + "','"
								+ taskId + "','isCancel');";
					} else {// 撤单以外的申请
						formScript = data.formName + "('" + taskName + "','"
								+ taskId + "');";
					}
					eval(formScript);
				},
				failure : function() {
					Ext.MessageBox.alert("提示信息：", "获取审批窗口失败");
				}
			});
		},
		//这个是用于只能同意不能拒绝的审批情况
		//modify by lee for packing function in 20090707 begin
		//specialAudit : function(taskId, titles) {//点击审批按钮的时候调用
		specialAudit : function() {//点击审批按钮的时候调用
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
					html : '审批意见:',
					colspan : 7,
					width : 'auto',
					height : 25,
					style : 'font-size:9pt;text-align:left'
				}, new Ext.form.TextArea({
					fieldLabel : '&nbsp;审批意见',
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
		//add by guangsa for 只能拒绝情况 in 20090730 begin
		//这个是用于只能拒绝不能同意的审批情况
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
					html : '审批意见:',
					colspan : 7,
					width : 'auto',
					height : 25,
					style : 'font-size:9pt;text-align:left'
				}, new Ext.form.TextArea({
					fieldLabel : '&nbsp;审批意见',
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
		//add by guangsa for 只能拒绝情况 in 20090730 end
		//这种是适应流程回退到开始节点，然后作相应的打回操作
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
					html : '审批意见:',
					colspan : 7,
					width : 'auto',
					height : 25,
					style : 'font-size:9pt;text-align:left'
				}, new Ext.form.TextArea({
					fieldLabel : '&nbsp;审批意见',
					height : 150,
					width : 430,
					name : 'comment',
					colspan : 7
				}),{
					html : '',
					style : 'width:60'
				},		// 调节位置
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
		//这种是适应如果有业务参数只有用户“确认审批通过”才会保存并且走流程，如果不同即打回则不会保存，直接走流程
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
					html : '审批意见:',
					colspan : 7,
					width : 'auto',
					height : 25,
					style : 'font-size:9pt;text-align:left'
				}, new Ext.form.TextArea({
					fieldLabel : '&nbsp;审批意见',
					height : 150,
					width : 430,
					name : 'comment',
					colspan : 7
				}),{
					html : '同意:',
					style : 'font-size:9pt;width:80;text-align:right;margin:15 0 20 20'
				}, new Ext.form.Radio({
					xtype : 'radio',
					width : 20,
					name : 'result',
					inputValue : 'Y',
					checked : true,
					fieldLabel : '同意'
				}), {
					html : '拒绝:',
					style : 'font-size:9pt;width:60;text-align:right;margin:15 0 20 20'
				}, new Ext.form.Radio({
					xtype : 'radio',
					width : 20,
					name : 'result',
					checked : false,
					inputValue : 'N',
					fieldLabel : '拒绝'
				}), {
					html : '',
					style : 'width:60'
				},		// 调节位置
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
	//这个是用于流程跳跃回退的情况
	skipWorkflowAudit : function(taskId, titles) {//点击审批按钮的时候调用
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
					html : '审批意见:',
					colspan : 8,
					width : 'auto',
					height : 25,
					style : 'font-size:9pt;text-align:left'
				}, new Ext.form.TextArea({
					fieldLabel : '&nbsp;审批意见',
					height : 150,
					width : 430,
					name : 'comment',
					colspan : 7
				}), {
						html : '同意:',
						style : 'font-size:9pt;width:80;text-align:right;margin:15 0 20 20'
					}, new Ext.form.Radio({
						xtype : 'radio',
						width : 20,
						name : 'result',
						inputValue : 'Y',
						checked : true,
						fieldLabel : '同意'
					}), {
						html : '打回:',
						style : 'font-size:9pt;width:60;text-align:right;margin:15 0 20 20'
					}, new Ext.form.Radio({
						xtype : 'radio',
						width : 20,
						name : 'result',
						checked : false,
						inputValue : 'N',
						fieldLabel : '打回'
					}), {
						html : '',
						style : 'width:60'
					},		// 调节位置
					{
						xtype : "combo",
						id : "skipNodeMessage",
						fieldLabel : "页面模型名称:",
						valueField : "name",
						displayField : "name",
						listWidth : 200,
						maxHeight : 200,
						emptyText: '请选择',
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
			// 装载审批有关信息，与流程有关,此处可把任务名称传递过去，auditInfo.jsp根据任务名称决定是否需要修改审批内容
			url : webContext + "/tabFrame.jsp?url=" + this.url + "****dataId="
					+ applyId + "****reqClass=" + reqClass +"****goStartState="+goStartState,
			text : "页面正在加载中......",
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
			html : "正在加载页面内容......"
		}]
	});
	this.auditContentWin.setPagePosition(200, 60);
	this.auditContentWin.show();
}

//打开事件审核的窗口，内部附有审核信息，或者考虑使用sheet方式代替
function showEventAuditWindow(taskId,eventName,taskName,applyType,applyId,pageUrl,reqClass){
	//申请的业务信息url
	//不要打回标志了
	this.goBack = taskName == "打回申请人"?"****back=Y":"";
	this.buttonText = "审批";
	if (taskName == "打回申请人") {
		this.buttonText = "处理";
	}
	this.url = webContext + pageUrl;

	this.auditContentWin = new Ext.Window({
		title : '审批内容窗口',
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
							|| applyType == "contractCancel") {// 是撤单申请的时候
						formScript = data.formName + "('" + taskName + "','"
								+ taskId + "','isCancel');";
					} else {// 撤单以外的申请
						formScript = data.formName + "('" + taskName + "','"
								+ taskId + "');";
					}
					eval(formScript);
				},
				failure : function() {
					Ext.MessageBox.alert("提示信息：", "获取审批窗口失败");
				}
			});
		},
		id : "auditContentWindow",
		draggable : true,
		autoLoad : {
			// 装载审批有关信息，与流程有关,此处可把任务名称传递过去，auditInfo.jsp根据任务名称决定是否需要修改审批内容
			url : webContext + "/tabFrame.jsp?url=" + this.url + "****dataId="
					+ applyId + "****reqClass=" + reqClass + "****taskId="
					+ taskId,
			text : "页面正在加载中......",
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
			html : "正在加载页面内容......"
		}]
	});
	// this.auditContentWin.setPagePosition(210,117);
	this.auditContentWin.setPagePosition(200, 60);
	this.auditContentWin.show();
}

//打开公告审核的窗口
function showNoticeAuditWindow(taskId,taskName,applyType,applyId,pageUrl,reqClass,goStartState){
	this.goBack = taskName == "打回申请人"?"****back=Y":"";
	this.buttonText = "审批";
	if (taskName == "打回申请人") {
		this.buttonText = "处理";
	}
	this.url = webContext + pageUrl;	
	this.auditContentWin = new Ext.Window({
		title : '审批内容窗口',
		modal : true,
		height : 500,
		width : 800,
		autoScroll:true,
		resizable : true,
		maximizable : true,
		//增加本节点的价钱人
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
					html : '审批意见<font color=red>(必填)</font>:',
					colspan : 7,
					width : 'auto',
					height : 25,
					style : 'font-size:9pt;text-align:left'
				}, new Ext.form.TextArea({
					id : 'noticeAuditComment',
					fieldLabel : '&nbsp;审批意见',
					height : 150,
					width : 430,
					name : 'comment',
					colspan : 7
				}), {
					html : '同意:',
					style : 'font-size:9pt;width:80;text-align:right;margin:15 0 20 20'
				}, new Ext.form.Radio({
					xtype : 'radio',
					width : 20,
					name : 'result',
					inputValue : 'Y',
					checked : true,
					fieldLabel : '同意'
				}), {
					html : '拒绝:',
					style : 'font-size:9pt;width:60;text-align:right;margin:15 0 20 20'
				}, new Ext.form.Radio({
					xtype : 'radio',
					width : 20,
					name : 'result',
					checked : false,
					inputValue : 'N',
					fieldLabel : '拒绝'
				}), {
					html : '',
					style : 'width:60'
				},		// 调节位置
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
		  	    title : '给当前节点指派加签人',
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
					text : "页面正在加载中......",
					method : 'post',
					scope : this
				},
				layout : 'fit',
				buttons: [{
					xtype : 'button',
					style : 'margin:4px 10px 4px 0',
					text : '关闭',
					scope : this,
					iconCls : 'add',
					handler : function(){
					  auditFormWin.close();
					} 
				},{
					xtype : 'button',
					style : 'margin:4px 10px 4px 0',
					text : '保存',
					scope : this,
					iconCls : 'save',
					handler : function(){
					  var iframeTest = window.frames["iframeTest"];//document.frames["iframeTest"];   
                	  iframeTest.test();//这个是个公用方法，一定要一致；另外还需要主要范围
					} 
				}]
				
			});
			//this.auditFormWin.setPagePosition(400, 100);
			auditFormWin.show();
		  
		},
		//增加下个节点的加签人
		addMarkUserToNext : function(){
		  var addMarkUrl= webContext + "/infoAdmin/workflow/configPage/addMarkUserToNextNode.jsp";
		  var auditFormWin = new Ext.Window({
		  	    title : '给下一个节点指派加签人',
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
					text : "页面正在加载中......",
					method : 'post',
					scope : this
				},
				layout : 'fit',
				buttons: [{
					xtype : 'button',
					style : 'margin:4px 10px 4px 0',
					text : '关闭',
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
		//这个是用于只能同意不能拒绝的审批情况
		specialAudit : function() {//点击审批按钮的时候调用
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
					html : '审批意见:',
					colspan : 7,
					width : 'auto',
					height : 25,
					style : 'font-size:9pt;text-align:left'
				}, new Ext.form.TextArea({
					fieldLabel : '&nbsp;审批意见',
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
			// 装载审批有关信息，与流程有关,此处可把任务名称传递过去，auditInfo.jsp根据任务名称决定是否需要修改审批内容
			url : webContext + "/tabFrame.jsp?url=" + this.url + "****dataId="
					+ applyId + "****reqClass=" + reqClass +"****goStartState="+goStartState,
			text : "页面正在加载中......",
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
			html : "正在加载页面内容......"
		}]
	});
	this.auditContentWin.setPagePosition(200, 60);
	this.auditContentWin.show();
}

function showNoticeAuditForm(auditForm, width, height, title) {
	if (title == "打回申请人") {
		title = "申请人处理打回申请";
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
					Ext.Msg.alert("提示", "必须填写审批意见！");
					return;
				}
				auditForm.getForm().submit({
					method : "get",
					params : getFormParam(auditForm.getId()),
					url : webContext + '/extjs/workflow?method=audit&done',
					success : function(action, form) {
						var jsonobject = Ext.util.JSON.decode(form.response.responseText);//这是form表单特有的抓取返回值的方法
						if (jsonobject.Exception != undefined) {
							Ext.Msg.alert("提示", "审批提交失败，异常信息如下，请您让管理员检查流程！", function() {
								Ext.Msg.alert("提示",jsonobject.Exception);
							});
						} else {
							Ext.Msg.alert("提示", "审批提交成功", function() {
							});
						}
						if (jsonobject.id == 'role') {// 申请用户填写预指派人员
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
						Ext.MessageBox.alert("提示信息：", "提交数据失败");
					},
					scope : this,
					clientValidation : false
						// 以后改成true
			});
				this.noticeAuditFormWin.close();
				Ext.getCmp("auditContentWindow").close();
			},
			text : '确认',
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
			text : '取消',
			scope : this
		}
		],
		items : [auditForm]
	});
	this.noticeAuditFormWin.setPagePosition(400, 100);
	this.noticeAuditFormWin.show();
}

//这种是适应如果有业务参数只有用户“确认审批通过”才会保存并且走流程，如果不同即打回则不会保存，直接走流程
function saveAndAuditForm(auditForm, width, height, title){
	if (title == "打回申请人") {
		title = "申请人处理打回申请";
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
                	iframeTest.test();//这个是个公用方法，一定要一致；另外还需要主要范围
                	//return;
				}
				auditForm.getForm().submit({
					method : "get",
					params : getFormParam(auditForm.getId()),
					url : webContext + '/extjs/workflow?method=audit&done',// +Ext.urlEncode(getFormParam(auditForm.getId())),
					success : function(action, form) {

						var jsonobject = Ext.util.JSON
								.decode(form.response.responseText);
						if (jsonobject.id == 'role') {// 申请用户填写预指派人员
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
						Ext.MessageBox.alert("提示信息：", "提交数据失败");
					},
					scope : this,
					clientValidation : false
						// 以后改成true
						});
				this.auditFormWin.close();
				Ext.getCmp("auditContentWindow").close();
			},
			text : '同意',
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
			text : '取消',
			scope : this
		}
		],
		items : [auditForm]
	});
	this.auditFormWin.setPagePosition(400, 100);
	this.auditFormWin.show();
}
//这个是用于只能同意不能拒绝的审批情况
function showSpecialAuditForm(auditForm, width, height, title) {
	if (title == "打回申请人") {
		title = "申请人处理打回申请";
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
						var jsonobject = Ext.util.JSON.decode(form.response.responseText);//这是form表单特有的抓取返回值的方法
						if (jsonobject.Exception != undefined) {
							Ext.Msg.alert("提示", "审批提交失败，异常信息如下:\""+jsonobject.Exception+"\"。请您让管理员检查流程！", function() {
							});
						} else {
							Ext.Msg.alert("提示", "审批提交成功", function() {
							});
						}
						var jsonobject = Ext.util.JSON
								.decode(form.response.responseText);
						if (jsonobject.id == 'role') {// 申请用户填写预指派人员
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
						Ext.MessageBox.alert("提示信息：", "提交数据失败");
					},
					scope : this,
					clientValidation : false
						// 以后改成true
						});
				this.auditFormWin.close();
				Ext.getCmp("auditContentWindow").close();
			},
			text : '确定',
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
			text : '取消',
			scope : this
		}
		],
		items : [auditForm]
	});
	this.auditFormWin.setPagePosition(400, 100);
	this.auditFormWin.show();
}
//显示跳步审批流程
function showSkipWorkflowAuditForm(auditForm, width, height, title, taskId) {
	if (title == "打回申请人") {
		title = "申请人处理打回申请";
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
						Ext.MessageBox.alert("提示信息：", "提交数据失败");
					},
					scope : this,
					clientValidation : false
						// 以后改成true
						});
				this.auditFormWin.close();
				Ext.getCmp("auditContentWindow").close();
			},
			text : '同意',
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
						Ext.MessageBox.alert("提示信息：", "提交数据失败");
					},
					scope : this,
					clientValidation : false
						// 以后改成true
				});
				this.auditFormWin.close();
//				Ext.getCmp("auditContentWindow").close();
			},
			text : '取消',
			scope : this
		}
		],
		items : [auditForm]
	});
	this.auditFormWin.setPagePosition(400, 100);
	this.auditFormWin.show();
}


// 打开审批对话框，进行审批，内部函数
function showAuditForm(auditForm, width, height, title) {
	if (title == "打回申请人") {
		title = "申请人处理打回申请";
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
						var jsonobject = Ext.util.JSON.decode(form.response.responseText);//这是form表单特有的抓取返回值的方法
						if (jsonobject.Exception != undefined) {
							Ext.Msg.alert("提示", "审批提交失败，异常信息如下，请您让管理员检查流程！", function() {
								Ext.Msg.alert("提示",jsonobject.Exception);
							});
						} else {
							Ext.Msg.alert("提示", "审批提交成功", function() {
							});
						}
						if (jsonobject.id == 'role') {// 申请用户填写预指派人员
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
						Ext.MessageBox.alert("提示信息：", "提交数据失败");
					},
					scope : this,
					clientValidation : false
						// 以后改成true
			});
				this.auditFormWin.close();
				Ext.getCmp("auditContentWindow").close();
			},
			text : '确认',
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
			text : '取消',
			scope : this
		}
		],
		items : [auditForm]
	});
	this.auditFormWin.setPagePosition(400, 100);
	this.auditFormWin.show();
}

// 缺省的审批对话框,统一的调用格式（任务名称，任务编号）
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
			html : '审批意见:',
			colspan : 7,
			width : 'auto',
			height : 25,
			style : 'font-size:9pt;text-align:left'
		}, new Ext.form.TextArea({
			fieldLabel : '&nbsp;审批意见',
			height : 150,
			width : 430,
			name : 'comment',
			colspan : 7
		}), {
			html : '同意:',
			style : 'font-size:9pt;width:80;text-align:right;margin:15 0 20 20'
		}, new Ext.form.Radio({
			xtype : 'radio',
			width : 20,
			name : 'result',
			inputValue : 'Y',
			checked : true,
			fieldLabel : '同意'
		}), {
			html : '拒绝:',
			style : 'font-size:9pt;width:60;text-align:right;margin:15 0 20 20'
		}, new Ext.form.Radio({
			xtype : 'radio',
			width : 20,
			name : 'result',
			checked : false,
			inputValue : 'N',
			fieldLabel : '拒绝'
		}), {
			html : '',
			style : 'width:60'
		},		// 调节位置
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

// 以下为定制对话框
// 打回申请人
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
			html : '审批意见:',
			colspan : 5,
			width : 'auto',
			height : 25,
			style : 'font-size:9pt;text-align:left'
		}, new Ext.form.TextArea({
			fieldLabel : '&nbsp;审批意见',
			height : 150,
			width : 430,
			name : 'comment',
			colspan : 7
		}), {
			html : '重新提交:',
			style : 'font-size:9pt;width:120;text-align:right;margin:15 0 20 20'
		}, new Ext.form.Radio({
			xtype : 'radio',
			width : 20,
			name : 'result',
			checked : true,
			inputValue : 'Y',
			fieldLabel : '重新提交'
		}), {
			html : '取消申请:',
			style : 'font-size:9pt;width:120;text-align:right;margin:15 0 20 20'
		}, new Ext.form.Radio({
			xtype : 'radio',
			width : 20,
			name : 'result',
			checked : false,
			inputValue : 'N',
			fieldLabel : '取消申请'
		}), {
			html : '',
			style : 'width:60'
		},		// 调节位置
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

//显示流程回退到开始节点的审批form表单
function showGoStartStateWorkflowAuditForm(auditForm, width, height, title, vProcessId ,nodeId, processId) {
	if (title == "打回申请人") {
		title = "申请人处理打回申请";
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
						Ext.MessageBox.alert("提示信息：", "提交数据失败");
					},
					scope : this,
					clientValidation : false
						// 以后改成true
						});
				this.auditFormWin.close();
				Ext.getCmp("auditContentWindow").close();
			},
			text : '重新提交',
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
						Ext.MessageBox.alert("提示信息：", "提交数据失败");
					},
					scope : this,
					clientValidation : false
						// 以后改成true
				});
				this.auditFormWin.close();
				Ext.getCmp("auditContentWindow").close();
			},
			text : '撤销流程',
			scope : this
		}
		],
		items : [auditForm]
	});
	this.auditFormWin.setPagePosition(400, 100);
	this.auditFormWin.show();
}




// 无角色时候产生的提示友好界面
popUserAssignWin = function(nextNodeName) {
	var win = new Ext.Window({
		title : '提示窗口',
		modal : true,
		height : 350,
		width : 450,
		autoScroll : true,
		resizable : false,
		html : "<font color=red size=12px>下一个任务节点:" + nextNodeName + ",没有相应的人审批，请找管理员核实！</font>",
		buttonAlign:'center',
		buttons : [{
			xtype : 'button',
			handler : function() {
				win.close();
			},
			text : '关闭',
			scope : this
		}]

	})
	win.show();
}

//我的审批窗口
function showMeThePage(dataId, applyType, serviceItemId, dataType,processId){
	var showWin = new Ext.Window({
		title:'我的申请',
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
			text : "页面加载中......",
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
			html : "正在加载页面数据......"
		}],
		buttonAlign : 'center',
		buttons:[{text : "关闭",
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


