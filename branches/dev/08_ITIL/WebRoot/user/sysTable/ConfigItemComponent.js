//ģ�����
ConfigItemComponentPanel = Ext.extend(Ext.Panel, {
	id : "tc",
	title : "������",
//	closable : true,
//	viewConfig : {
//		autoFill : true,
//		forceFit : true
//	},
//	layout : 'column',
//	layoutConfig : {
//		columns : 1
//	},
	layout : 'table',
	height : 'auto',
	align : 'center',
	//foredit : true,
	width : 1300,
//	frame : true,

	defaults : {
		bodyStyle : 'padding:4px'
	},
	layoutConfig : {
		columns : 1
	},
	
	items:this.items,
//	autoScroll : true,
//	height : 1000,
	initComponent : function() {
		this.grid = new ConfigSourceDataPanel();
		//this.grid2 = new ConfigSourceDataPanel2();
//		this.items =[addForm, this.grid, this.grid2];
		
		this.panel = new Ext.form.FormPanel({
			layout : 'column',
			height : 'auto',
			width : '1300',
			frame : true,
			defaults : {
				bodyStyle : 'padding:4px'
			},
			layoutConfig : {
				columns : 4
			},
			title : "<div align='center'>��Ŀ������Ϣ</div>",
			items : addForm
		});
		
//		this.panel2 = new Ext.Panel({
//			layout : 'table',
//			height : 'auto',
//			width : this.width-15,
//			frame : true,
//			defaults : {
//				bodyStyle : 'padding:4px'
//			},
//			layoutConfig : {
//				columns : 4
//			},
//			title : "<div align='center'>��Ŀ������Ϣ</div>",
//			items : this.grid
//		});
//		
//		this.panel3 = new Ext.Panel({
//			layout : 'table',
//			height : 'auto',
//			width : this.width-15,
//			frame : true,
//			defaults : {
//				bodyStyle : 'padding:4px'
//			},
//			layoutConfig : {
//				columns : 4
//			},
//			title : "<div align='center'>��Ŀ������Ϣ</div>",
//			items : this.grid2
//		});
		
		this.items = [addForm,this.grid];
		
		ConfigItemComponentPanel.superclass.initComponent.call(this);

	}

});