//模板组件
ModifyPanel = Ext.extend(Ext.Panel, {
	//id : "tc",
	title : "系统主表",
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
		this.grid = new GridModifyPanel();
	//	this.grid2 = new GridModifyPanel2();
//		this.items =[addForm, this.grid, this.grid2];
		
//		this.panel = new Ext.form.FormPanel({
//			layout : 'column',
//			height : 'auto',
//			width : '1300',
//			frame : true,
//			defaults : {
//				bodyStyle : 'padding:4px'
//			},
//			layoutConfig : {
//				columns : 4
//			},
//			title : "<div align='center'>项目基本信息</div>",
//			items : modifyForm
//		});
//		
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
//			title : "<div align='center'>项目申请信息</div>",
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
//			title : "<div align='center'>项目申请信息</div>",
//			items : this.grid2
//		});
		//this.items = [modifyForm];
		this.items = [modifyForm,this.grid];
		//alert(this.id);
		ModifyPanel.superclass.initComponent.call(this);

	}

});