AgreementPanel = Ext.extend(Ext.Panel, {
	layout : 'table',
	title:'û�д��ڵ��ʺ�',
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
	//����Э�����ת�������
	forward : function(){
		window.history.back(-1);

	},
	
	items : this.items,
	buttons : this.buttons,
	initComponent : function() {
		 var yesButton = new Ext.Button({
			text:'����',
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
						value:"�����ڿɱ�����ʺ�"
					})];
		this.buttons =[yesButton]
    	AgreementPanel.superclass.initComponent.call(this);  
	}
})