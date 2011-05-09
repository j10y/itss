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
	// ��ʼ��
	initComponent : function() {// �������涨��ı�����ȫ�ֱ���
		DefaultPageModel.superclass.initComponent.call(this);// �ø����ȳ�ʼ��
		var taskId=this.taskId;
		this.form = new Ext.FormPanel({
		title:'����',
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
	    html:"<p>û����������</p>",
	     buttons:[
        	{xtype:'button',
	        	handler:function(){audit();},
	        	text:'ȷ��',
	        	scope:this
        	},{xtype:'button',
	        	handler:function(){winClose();},
	        	text:'ȡ��',
	        	scope:this
        	},{xtype:'button',
	        	handler:function(){specialNoAudit();},
	        	text:'�ܾ�',
	        	scope:this
        	},{xtype:'button',
	        	handler:function(){goStartStateAudit();},
	        	text:'��ʼ�ڵ��ύ',
	        	scope:this
        	},{xtype:'button',
	        	handler:function(){addMarkUser();},
	        	text:'����ǰ�ڵ��ǩ',
	        	scope:this
        	},{xtype:'button',
	        	handler:function(){addMarkUserToNext();},
	        	text:'���¸��ڵ��ǩ',
	        	scope:this
        	},{xtype:'button',
        		text:'���̻���',
	        	handler:function(){
	        		Ext.Ajax.request({
									url: webContext+ '/extjs/workflow?method=getWorkFlowGoBack',
									params : {							        	
										taskId : taskId
									},
									success : function(response, options) {
										Ext.MessageBox.alert("��ʾ", "����ɹ�", function() {		
											alert("��ȥඣ�����������������������");
											winClose();
											});
					
									},
									failure : function(response, options) {
										Ext.MessageBox.alert("����ʧ��");
										Ext.getCmp('saveBtn').enable();
									}						
						})	;
	        	},
	        	scope:this
        	},{xtype:'button',
        		text:'������Ϣ',
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
										Ext.MessageBox.alert("����ʧ��");
										Ext.getCmp('saveBtn').enable();
									}						
						})	;
	        	},
	        	scope:this
        	}
        ],
	    items: [
        	{html:'ͬ��:',style:'font-size:9pt;width:80;text-align:right;margin:15 0 20 20'},
			new Ext.form.Radio({
				xtype:'radio',
				width:20,
				name:'result',
				inputValue:'Y',
				checked:true,
				fieldLabel:'ͬ��'		
			}),
			{html:'�ܾ�:',style:'font-size:9pt;width:60;text-align:right;margin:15 0 20 20'},
			new Ext.form.Radio({
				xtype:'radio',
				width:20,
				name:'result',
				checked:false,
				inputValue:'N',
				fieldLabel:'�ܾ�'		
			}),
			{html:'',style:'width:60'},//����λ��
			{xtype:'hidden',name:'done',value:'yes'}
		]
	});
	this.add(this.form);
	}
});
