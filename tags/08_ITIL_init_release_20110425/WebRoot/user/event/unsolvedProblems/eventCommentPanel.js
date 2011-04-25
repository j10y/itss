EventCommentPanel = Ext.extend(Ext.Panel, {
	id : "eventCommentTab",
	title : '事件备注',
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
		//事件备注列表
		
		//添加事件备注
		this.ecfp = new Ext.form.FormPanel({
			id : 'ecfp',
			title : '事件备注',
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
					title : '事件备注',
					width : 'auto',
					height : 'auto',
					defaults : {
						bodyStyle : 'padding:4px'
					},
					layout : 'table',
					layoutConfig : {
						columns : 2
					},
					items : [{html: "添加事件备注：&nbsp;",cls: 'common-text',style:'width:150;text-align:right'},
				{xtype : 'textarea', 
				name : 'remark', 
				fieldLabel : '事件备注', 
				width : 516,
				height : 100}]
				}]
		});
		this.items = [this.ecfp];
		EventCommentPanel.superclass.initComponent.call(this);
	}
});
	