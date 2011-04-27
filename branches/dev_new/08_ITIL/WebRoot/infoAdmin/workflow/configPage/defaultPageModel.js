function test(){
	defaultPageModel.goBackUrl();
}
DefaultPageModel = Ext.extend(Ext.Panel, {
	id : "DefaultPageModel",
	height : 420,
	width : 600,
	
	defaults : {
		bodyStyle : 'padding:4px'
	},
	layoutConfig : {
		columns : 1
	},
	freshPage : function() {
		window.location.reload();
	},

	goBackUrl : function() {
		history.go(-1);
	},

	items : this.items,
	// 初始化
	initComponent : function() {// 在这里面定义的变量是全局变量
		DefaultPageModel.superclass.initComponent.call(this);// 让父类先初始化
		var taskId=this.taskId;
		this.form = new Ext.FormPanel({
		title:'审批',
		id:"defaultForm",
		layout:'table',
        layoutConfig: {
	        columns: 7
	    },
	    frame:true,
	    bodyStyle:'padding:5px 5px 5px 10px',
	    width: 800,
	    height: 415,
	    isFormField:true,
	    buttonAlign:"center",
	    html:"<p>没有审批内容</p>",
	     buttons:[
        	{xtype:'button',
	        	handler:function(){audit();},
	        	text:'确认',
	        	scope:this
        	},{xtype:'button',
	        	handler:function(){winClose();},
	        	text:'取消',
	        	scope:this
        	},{xtype:'button',
	        	handler:function(){specialNoAudit();},
	        	text:'拒绝',
	        	scope:this
        	},{xtype:'button',
	        	handler:function(){goStartStateAudit();},
	        	text:'开始节点提交',
	        	scope:this
        	},{xtype:'button',
	        	handler:function(){addMarkUser();},
	        	text:'给当前节点加签',
	        	scope:this
        	},{xtype:'button',
	        	handler:function(){addMarkUserToNext();},
	        	text:'给下个节点加签',
	        	scope:this
        	},{xtype:'button',
        		text:'流程回退',
	        	handler:function(){
	        		Ext.Ajax.request({
									url: webContext+ '/extjs/workflow?method=getWorkFlowGoBack',
									params : {							        	
										taskId : taskId
									},
									success : function(response, options) {
										Ext.MessageBox.alert("提示", "保存成功", function() {		
											alert("回去喽！！！！！！！！！！！！");
											winClose();
											});
					
									},
									failure : function(response, options) {
										Ext.MessageBox.alert("保存失败");
										Ext.getCmp('saveBtn').enable();
									}						
						})	;
	        	},
	        	scope:this
        	},{xtype:'button',
        		text:'任务信息',
	        	handler:function(){
	        		Ext.Ajax.request({
									url: webContext+ '/extjs/workflow?method=getTaskInfoMessage',
									params : {							        	
										vProcessName : 'vProcessName',
										dataId : 'dataId'
									},
									success : function(response, options) {
										var pageUrl = response.responseText;
										alert("==="+pageUrl);
									},
									failure : function(response, options) {
										Ext.MessageBox.alert("保存失败");
										Ext.getCmp('saveBtn').enable();
									}						
						})	;
	        	},
	        	scope:this
        	}
        ],
	    items: [
        	{html:'同意:',style:'font-size:9pt;width:80;text-align:right;margin:15 0 20 20'},
			new Ext.form.Radio({
				xtype:'radio',
				width:20,
				name:'result',
				inputValue:'Y',
				checked:true,
				fieldLabel:'同意'		
			}),
			{html:'拒绝:',style:'font-size:9pt;width:60;text-align:right;margin:15 0 20 20'},
			new Ext.form.Radio({
				xtype:'radio',
				width:20,
				name:'result',
				checked:false,
				inputValue:'N',
				fieldLabel:'拒绝'		
			}),
			{html:'',style:'width:60'},//调节位置
			{xtype:'hidden',name:'done',value:'yes'}
		]
	});
	this.add(this.form);
	}
});
