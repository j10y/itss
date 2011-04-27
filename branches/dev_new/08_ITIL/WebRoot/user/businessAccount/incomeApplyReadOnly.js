ApplyPanel = Ext.extend(Ext.Panel, {
	id : "applyPanel",
	height : 'auto',
	width : 'auto',
	layout : 'fit',
	frame : true,
	items : this.items,
	/*
	 * 获得主formpanel
	 */
	getMainForm : function() {
		
		var businessAccount = new Ext.form.Hidden({
				xtype : 'hidden',
				id : 'businessAccountId',
				fieldLabel : 'ID'
			})
		
		var searchForm = new Ext.form.FormPanel({
			title : '收款申请',
			id : "formPanel",
			collapsible : true,
			frame : true,
			autoScroll : true,
			layout : "table",
			defaults : {
				height : 30
			},
			layoutConfig : {
				columns : 1
			},
			items : [{
				xtype : 'fieldset',
				title : '增加申请条目',
				height : 150,
				layout : 'table',
				layoutConfig : {
					columns : 4
				},
				defaults : {
					bodyStyle : 'padding:8px 0px 8px 0px'
				},
				items : [
				{
					html : "收款计划:&nbsp;",
					cls : 'common-text',
					style : 'width:150;text-align:right'
				}, 
				 {
					html : "收款条目:&nbsp;",
					colspan : 1,
					cls : 'common-text',
					style : 'width:150;text-align:right',
					id : "ccc"
				}, {
					id : "product",
					style : 'width:550',
					colspan : 3
				}, {
					id : "productId",
					hidden : true,
					style : 'width:550',
					colspan : 3
				}]
			}, {
				xtype : "fieldset",
				title : "申请理由",
				layout : 'table',
				defaults : {
					bodyStyle : 'padding:10px 40px 10px 40px'
				},
				items : [{
					xtype : 'textarea',
					id : "applyDescn",
					fieldLabel : '项目描述',
					height : 550,
					width : 550
				}]
			}],
			buttons : [
			{
				text : "暂存",
				pressed : true,
				scope : this,
				iconCls : 'save',
				handler : this.formSave
			},
			{
				text : "提交申请",
				pressed : true,
				iconCls : 'submit',
				handler : this.formSubmit
			}, {
				text : "返 回",
				pressed : true,
				iconCls : 'back',
				handler : function() {

					history.go(-1);
				}
			}],
			buttonsalign : 'left'
		});
		return searchForm;
	},
	initComponent : function() {
		this.searchForm = this.getMainForm();
		this.items = [this.searchForm];
		ApplyPanel.superclass.initComponent.call(this);
	}
});

}