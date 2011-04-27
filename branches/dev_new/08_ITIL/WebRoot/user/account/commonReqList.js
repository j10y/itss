PagePanel = Ext.extend(Ext.Panel, {
	id : "PageTemplates",
	layout : 'fit',
	height : 'auto',
	align : 'center',
	foredit : true,
	width : 1050,
	frame : true,
	autoScroll : true,
	defaults : {
		bodyStyle : 'padding:4px'
	},
	viewConfig : {// 自适应填充
		autoFill : true,
		forceFit : true
	},
	layoutConfig : {
		columns : 4
	},
	getTreePanel : function() {
		var loader = new Ext.tree.TreeLoader({
			url : webContext + '/requireSIAction_creatTreeData.action?serviceTypeKeyWord='+this.serviceTypeKeyWord
		});
		var treePanel = new Ext.tree.TreePanel({
			loader : loader,
			id : 'treepanel',
			layout:'fit',
//			viewConfig : {// 自适应填充
//				autoFill : true,
//				forceFit : true
//			},
			// border : false,
			//width : 560	,
			height : 380,
			//height : 'auto',
			autoScroll : true,
			root : new Ext.tree.AsyncTreeNode({
				text : '您可用的服务',
				id : -1
			}),
			rootVisible : false
		});
		loader.on('beforeload', function(treeloader, node) {
			treeloader.baseParams = {
				id : node.id
			};

		}, this);
		
		treePanel.on('dblclick',function(node){
			if (node.id == -1) {
			return
			}
			Ext.Ajax.request({
				url : webContext+ '/requireSIAction_getNodeInfo.action?nodeId='+ node.id,
				success : function(response, options) {
					var responseStr = clearReturn(response.responseText);
					var data = Ext.util.JSON.decode(responseStr);
					var type = data.type;
					
					if (type == 'item') {
						var name = data.name;
						var price = data.price;
						var descn = data.descn;
						Ext.getCmp('serviceType').setValue("服务项");
						Ext.getCmp('serviceName').setValue(data.name);
						Ext.getCmp('servicePrice').setValue(data.price);
						Ext.getCmp('descn').setValue(data.descn);
						if(data.process=='true'){
							Ext.getCmp('button').enable();
							var nowID = Ext.getCmp('treepanel').getSelectionModel().getSelectedNode().id;
							window.location = webContext+ "/requireSIAction_toRequireInfo2.action?id="+ nowID;
						}else{
							Ext.getCmp('button').disable();
							Ext.MessageBox.alert("提示","该服务项无流程申请！");
						}
				
					} else {
						Ext.getCmp('serviceType').setValue("服务目录");
						Ext.getCmp('serviceName').setValue("");
						Ext.getCmp('servicePrice').setValue("");
						Ext.getCmp('descn').setValue("");
						Ext.getCmp('button').disable();
						Ext.MessageBox.alert("提示","您所选择的是服务目录！");
					}
				},
				failure : function(response, options) {
					Ext.MessageBox.alert("错误","获取服务目录节点信息失败！");
				}
			}, this);
		});
		
		treePanel.on('click', function(node) {
			if (node.id == -1) {
				return
			}
			var url = webContext
					+ '/requireSIAction_selectDeseData.action?treeDeseID='
					+ node.id;
			var conn = Ext.lib.Ajax.getConnectionObject().conn;
			conn.open("post", url, false);
			conn.send(null);
			var data = "";
			if (conn.status == "200") {
				// alert(conn.responseText);
				var responseText = conn.responseText;
				if (responseText == '' || responseText == null) {
					Ext.MessageBox.alert("提示信息：", "数据库数据为空");
					data = "";
				} else {
					responseText = clearReturn(responseText);
					data = eval("(" + responseText + ")");
				}
			}
			if (data.servicemessage[0].serviceTage == '服务项') {
				if(data.servicemessage[0].process=='true'){
					Ext.getCmp('serviceType').setValue("服务项");
					Ext.getCmp('serviceName').enable();
					Ext.getCmp('serviceName').setValue(data.servicemessage[0].serviceName);
					Ext.getCmp('servicePrice').enable();
					Ext.getCmp('servicePrice').setValue(data.servicemessage[0].servicePrice);
					Ext.getCmp('descn').enable();
					Ext.getCmp('descn').setValue(data.servicemessage[0].descn);
					Ext.getCmp('button').enable();
				}else{
					Ext.getCmp('serviceType').setValue("服务项");
					Ext.getCmp('serviceName').enable();
					Ext.getCmp('serviceName').setValue(data.servicemessage[0].serviceName);
					Ext.getCmp('servicePrice').enable();
					Ext.getCmp('servicePrice').setValue(data.servicemessage[0].servicePrice);
					Ext.getCmp('descn').enable();
					Ext.getCmp('descn').setValue(data.servicemessage[0].descn);
					Ext.getCmp('button').disable();
				}
			
			} else {
				Ext.getCmp('serviceType').setValue("服务目录");
				Ext.getCmp('serviceName').setValue("");
				Ext.getCmp('serviceName').disable();
				Ext.getCmp('servicePrice').setValue("");
				Ext.getCmp('servicePrice').disable();
				Ext.getCmp('descn').setValue("");
				Ext.getCmp('descn').disable();
				Ext.getCmp('button').disable();
			}
		}, this);
		treePanel.expandAll();

		return treePanel;

	},
	items : this.items,
	/*
	 * clientvalidation 初始化
	 */
	initComponent : function() {
		var da = new DataAction();
		this.name = "服务类型";
		var serviceName = new Ext.form.TextField({
			name : "serviceType",
			fieldLabel : this.name,
			id : 'serviceType',
			readOnly : true,
			width : 200
		});
		var startDate = new Ext.form.TextField({
			name : "serviceName",
			id : 'serviceName',
			fieldLabel : "服务名称",
			
			readOnly : true,
			width : 200
		});
		var endDate = new Ext.form.TextField({
			name : "servicePrice",
			id : 'servicePrice',
			fieldLabel : "服务价格",
			readOnly : true,
			width : 200
		});
		
		var descn = new Ext.form.TextArea({
			name : "descn",
			id : 'descn',
			fieldLabel : "服务描述",
			readOnly : true,
			width : 200,
			height : 150
		});
		this.tree = this.getTreePanel();
		this.formPanel = new Ext.form.FormPanel({
			frame: true,
        	labelAlign: 'left',
        	layout: 'column',
			title : "可用常规服务列表",
			items : [{ 
				columnWidth: 0.3,
	            layout: 'fit',
	            border : true,
	            title : '服务目录列表（<font color=red>单击服务节点查看服务描述</font>）',
	            items: this.tree
				}, 
				{
				columnWidth: 0.3,
	            xtype: 'fieldset',
	            labelWidth: 55,
	            labelAlign : 'right',
	            buttonAlign : 'center',
	            title : '描述信息(有流程项可点击按钮进入查看)',
	            defaults: {width: 200},	// Default config options for child items
	            defaultType: 'textfield',
	            autoHeight: true,
	            bodyStyle: Ext.isIE ? 'padding:0 0 5px 15px;' : 'padding:10px 15px;',
	            border: false,
	            style: {
	                "margin-left": "10px", // when you add custom margin in IE 6...
	                "margin-right": Ext.isIE6 ? (Ext.isStrict ? "-10px" : "-13px") : "0"  // you have to adjust for it somewhere else
	            },
				items : [serviceName, startDate, endDate, descn],
				buttons :[{xtype:'button',
					id : 'button',
					iconCls:'forward',
					fieldLabel : "服务描述",
					text : '进入服务项',
					pressed:true,
					disabled : true,
					handler : function() {
						var nowID = Ext.getCmp('treepanel').getSelectionModel()
								.getSelectedNode().id;
						window.location = webContext+ "/requireSIAction_toRequireInfo2.action?id="
								+ nowID;                   
					}}]
			}]
		});

		var items = new Array();
		items.push(this.formPanel);
		this.items = items;
		PagePanel.superclass.initComponent.call(this);
	}
})