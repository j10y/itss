EventCommentPanel = Ext.extend(Ext.Panel, {
	id : "eventCommentTab",
	title : '�¼���ע',
	layout : 'table',
	height : 'auto',
	autoScroll : true,
	align : 'center',
	foredit : true,
	width : 'auto',
	frame : true,
	defaults : {
		bodyStyle : 'padding:4px',
		overflow : 'auto'
	},
	layoutConfig : {
		columns : 1
	},
	items : this.items,
	ecfp : this.ecfp,
	
	initComponent : function() {	
		//�¼���ע�б�
		
		//����¼���ע
		this.ecfp = new Ext.form.FormPanel({
			id : 'ecfp',
			title : '�¼���ע',
			layout : 'table',
			height : 'auto',
			width : 1000,
			frame : true,
			autoScroll : true,
			defaults : {
				bodyStyle : 'padding:4px',
				overflow : 'auto'
			},
			layoutConfig : {
				columns : 2
			},		
			items : [{xtype : 'fieldset',
					title : '�¼���ע',
					width : 'auto',
					height : 'auto',
					defaults : {
						bodyStyle : 'padding:4px'
					},
					layout : 'table',
					layoutConfig : {
						columns : 2
					},
					items : [{html: "����¼���ע��&nbsp;",cls: 'common-text',style:'width:150;text-align:right'},
				{xtype : 'textarea', 
				name : 'remark', 
				fieldLabel : '�¼���ע', 
				width : 516,
				height : 100}]
				}]
		});
		this.items = [this.ecfp];
		EventCommentPanel.superclass.initComponent.call(this);
	}
});
	