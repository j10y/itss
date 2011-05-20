AgreementPanel = Ext.extend(Ext.Panel, {
	layout : 'table',
	title:'用户申请协议',
	buttonAlign : 'center',
	autoScroll : true,
	viewConfig : {
		autoFill : true,
		forceFit : true
	},
	layout : 'fit',
	//接受协议后跳转到申请表单
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
			text:'接受',
			iconCls:'add',
			scope : this,
			handler:this.forward
		});
		var noButton = new Ext.Button({
			text:'拒绝',
			iconCls:'remove',
			scope : this,
			handler:this.giveUp
		});
		this.buttons =[yesButton,noButton]
    	AgreementPanel.superclass.initComponent.call(this);  
	}
})