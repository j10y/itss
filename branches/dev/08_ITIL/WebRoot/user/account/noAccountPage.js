AgreementPanel = Ext.extend(Ext.Panel, {
	layout : 'table',
	title:'没有存在的帐号',
	height : '800',
	align : 'center',
	foredit : true,
	width : '800',
	frame : true,
	buttonAlign : 'center',
	autoScroll : true,
	defaults : {
		bodyStyle : 'padding:4px'
	},
	layoutConfig : {
		columns : 1 
	},
	//接受协议后跳转到申请表单
	forward : function(){
		window.history.back(-1);

	},
	
	items : this.items,
	buttons : this.buttons,
	initComponent : function() {
		 var yesButton = new Ext.Button({
			text:'返回',
			iconCls:'add',
			scope : this,
			handler:this.forward
		});
		
		this.items=[new Ext.form.HtmlEditor({
						enableAlignments:false,
						enableColors:false,
						enableFont:false,
						enableFontSize:false,
						enableFormat:false,
						enableLinks:false,
						enableLists:false,
						enableSourceEdit:false,
						enableEdit:false,
						readOnly:true,
						width:780,
						height:250,
						id:'agreement',
						value:"不存在可变更的帐号"
					})];
		this.buttons =[yesButton]
    	AgreementPanel.superclass.initComponent.call(this);  
	}
})