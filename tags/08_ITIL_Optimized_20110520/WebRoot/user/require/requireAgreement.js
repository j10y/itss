AgreementPanel = Ext.extend(Ext.Panel, {
	layout : 'table',
	title:'�û�����Э��',
	buttonAlign : 'center',
	autoScroll : true,
	viewConfig : {
		autoFill : true,
		forceFit : true
	},
	layout : 'fit',
	//����Э�����ת�������
	forward : function(){
		window.location = webContext + "/requireAction_toProcessEnterPage.action?serviceItemProcessId="+this.processId+"&toPage=yes";
	},
	giveUp : function(){
		window.history.back(-1);
	},
	buttons : this.buttons,
	initComponent : function() {
		this.html=this.agreement;
		 var yesButton = new Ext.Button({
			text:'����',
			iconCls:'add',
			scope : this,
			handler:this.forward
		});
		var noButton = new Ext.Button({
			text:'�ܾ�',
			iconCls:'remove',
			scope : this,
			handler:this.giveUp
		});
		this.buttons =[yesButton,noButton]
    	AgreementPanel.superclass.initComponent.call(this);  
	}
})