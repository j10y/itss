PagePanel = Ext.extend(Ext.Panel, {
	id : "PagePanel",
	closable : true,
	viewConfig : {
		autoFill : true,
		forceFit : true
	},
	layout : 'border',
	items : this.items,

	initComponent : function() {
		// 创建表格数据
		this.store = new Ext.data.JsonStore({
			url : webContext
					+ '/requireSIAction_listSiForReq.action?serviceType='
					+ this.serviceType,
			fields : ['id', 'serviceItemCode', 'name', 'servePrice','serviceItemType', 'descn','process'],
			root : "data",
			id : 'id',
			sortInfo:{field:"name",direction:"ASC"}//排序
		});
		this.store.load();
		// 创建Grid表格组件
		this.grid = new Ext.grid.GridPanel({
			region : "center",
			y : 140,
			height : 1000,
			anchor : '0 -35',
			id : 'gridpanel',
			frame : true,
			store : this.store,
			autoScroll : true,
			trackMouseOver : true,
			bbar : ["->"],
			columns : [
//				{
//				id : 'id',
//				header : "id",
//				width : 50,
//				sortable : true,
//				dataIndex : 'id',
//				hidden : true
//			},
			{
				header : "服务项编号",
				width : 150,
				sortable : true,
				tooltip : '服务项编号',
				dataIndex : 'serviceItemCode'

			}, {
				header : "服务项名称",
				width : 350,
				sortable : true,
				tooltip : '服务项名称',
				dataIndex : 'name'
				
			},
//			{
//				header : "分摊标准",
//				width : 100,
//				sortable : true,
//				tooltip : '分摊标准',
//				align : 'right',//数字右对齐
//				hidden : true,
//				dataIndex : 'servePrice'
//				
//			}, {
//				header : "服务项类型",
//				width : 150,
//				sortable : true,
//				hidden : true,
//				dataIndex : 'serviceItemType'
//			}, {
//				header : "服务描述",
//				width : 400,
//				sortable : true,
//				hidden : true,
//				dataIndex : 'descn'
//			}, {
//				header : "是否有流程",
//				sortable : true,
//				hidden : true,
//				dataIndex : 'process'
//			},
				{
				//header : "进入申请",
				width : 100,
				sortable : true,
				align : 'center',
				dataIndex : 'aaa',
				renderer : function(value,cellmeta,record,rowIndex,columnIndex,store){
					var curId = record.get('id');
					var serviceItemType = record.get('serviceItemType');
					var process = record.get('process');
					var enter = "";
					if(process=='false'){
						enter = "无需申请";
					}else if (serviceItemType == '250') {
						enter = "<a href='"+webContext+"\/requireSIAction_toRequireInfo2.action?id=" + curId +"'>进  入</a>";
					} else {
						enter = "<a href='"+webContext+"\/requireSIAction_toRequireInfo.action?id=" + curId +"'>进  入</a>";
					}
					return enter;
				}
			}]
		});
		
        //add by 2009-11-23 gaowen GripPanle ToopTip
		Ext.override(Ext.ToolTip, {
			onTargetOver : function(e) {
				if (this.disabled || e.within(this.target.dom, true)) {
					return;
				}
				var t = e.getTarget(this.delegate);
				if (t) {
					this.triggerElement = t;
					this.clearTimer('hide');
					this.targetXY = e.getXY();
					this.delayShow();
				}
			},
			onMouseMove : function(e) {
				var t = e.getTarget(this.delegate);
				if (t) {
					this.targetXY = e.getXY();
					if (t === this.triggerElement) {
						if (!this.hidden && this.trackMouse) {
							this.setPagePosition(this.getTargetXY());
						}
					} else {
						this.hide();
						this.lastActive = new Date(0);
						this.onTargetOver(e);
					}
				} else if (!this.closable && this.isVisible()) {
					this.hide();
				}
			},
			hide : function() {
				this.clearTimer('dismiss');
				this.lastActive = new Date();
				delete this.triggerElement;
				Ext.ToolTip.superclass.hide.call(this);
			}
		});
		this.grid.on('render', function(grid) {
			var store = grid.getStore(); // Capture the Store.   
			var view = grid.getView(); // Capture the GridView.   

			this.grid.tip = new Ext.ToolTip({
				target : view.mainBody, // The overall target element.   
				delegate : '.x-grid3-row', // Each grid row causes its own seperate show and hide.   
				trackMouse : true, // Moving within the row should not hide the tip.   
				renderTo : document.body, // Render immediately so that tip.body can be referenced prior to the first show.     
				listeners : { // Change content dynamically depending on which element triggered the show.   
					beforeshow : function updateTipBody(tip) {
						var rowIndex = tip.triggerElement.rowIndex
						var record = store.getAt(rowIndex);
						var descn = record.get('descn');
						tip.body.update(descn);
					}
				}
			});
		}, this);
		
		this.grid.on("rowdblclick", function() {
			var record = this.grid.getSelectionModel().getSelected();
			var curId = record.get('id');
			var serviceItemType = record.get('serviceItemType');
			var process = record.get('process');
			if(process=='false'){
				Ext.MessageBox.alert("提示","该服务项无流程申请！");
				return;
			}
			if (serviceItemType == '250') {
				window.location = webContext + "/requireSIAction_toRequireInfo2.action?id=" + curId;
			} else {
				window.location = webContext + "/requireSIAction_toRequireInfo.action?id=" + curId;
			}
		}, this);
		var item = new Array();
		item.push(this.grid);
		this.items = item;
		PagePanel.superclass.initComponent.call(this);
	}
});
