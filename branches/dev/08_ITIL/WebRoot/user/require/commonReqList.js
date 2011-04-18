PagePanel = Ext.extend(Ext.Panel, {
	id : "PageTemplates",
	layout : 'fit',
	height : 'auto',
	align : 'center',
	foredit : true,
	width : 800,
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
	getNodeInfo:function(node){
		if (node.id == -1) {
			return
		}
		Ext.Ajax.request({
			url : webContext+ '/requireSIAction_getNodeInfo.action?nodeId='+ node.id,
			success : function(response, options) {
				var responseArray = Ext.util.JSON.decode(response.responseText);
				var type = responseArray.type;
				
				if (type == 'item') {
					var data = responseArray.data;
					Ext.getCmp('serviceType').setValue("服务项");
					Ext.getCmp('serviceName').setValue(data.name);
					Ext.getCmp('servicePrice').setValue(data.price);
					Ext.getCmp('descn').setValue(data.descn);
					if(data.process=='true'){
						Ext.getCmp('button').enable();
					}
			
				} else {
					Ext.getCmp('serviceType').setValue("服务目录");
					Ext.getCmp('button').disable();
				}
			},
			failure : function(response, options) {
				Ext.MessageBox.alert("获取服务目录节点信息失败！");
			}
		}, this);
	},
	getTreePanel : function() {
		var loader = new Ext.tree.TreeLoader({
			url : webContext + '/requireSIAction_creatTreeData.action?serviceTypeKeyWord='+this.serviceTypeKeyWord
		});
		var treePanel = new Ext.tree.TreePanel({
			loader : loader,
			id : 'treepanel',
			viewConfig : {// 自适应填充
				autoFill : true,
				forceFit : true
			},
			// border : false,
			//width : 560	,
			height : 400,
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
							window.location = webContext+ "/requireSIAction_toRequireInfo.action?id="+ nowID;
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
		treePanel.on('click',function(node){
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
						}else{
							Ext.getCmp('button').disable();
						}
				
					} else {
						Ext.getCmp('serviceType').setValue("服务目录");
						Ext.getCmp('serviceName').setValue("");
						Ext.getCmp('servicePrice').setValue("");
						Ext.getCmp('descn').setValue("");
						Ext.getCmp('button').disable();
					}
				},
				failure : function(response, options) {
					Ext.MessageBox.alert("错误","获取服务目录节点信息失败！");
				}
			}, this);
			
		},this);

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
			height : 300
		});
		
		var enterButton = new Ext.Button({
			id : 'button',
			iconCls : 'forward',
			fieldLabel : "服务描述",
			text : '进入服务项',
			pressed : true,
			disabled : true,
			handler : function() {
				var nowID = Ext.getCmp('treepanel').getSelectionModel().getSelectedNode().id;
				window.location = webContext + "/requireSIAction_toRequireInfo.action?id=" + nowID;
			}
		});
		
		this.tree = this.getTreePanel();
		this.formPanel = new Ext.form.FormPanel({
			frame: true,
        	labelAlign: 'left',
        	//autoScroll : true,
        	layout: 'column',
			title : "可用服务列表",
			items : [{ 
				columnWidth: 0.5,
	            layout: 'fit',
	            border : true,
	            title : '服务目录列表（<font color=red>单击服务节点查看服务描述，双击服务节点进入服务申请</font>）',
	            items: this.tree
				}, 
				{
				columnWidth: 0.5,
	            xtype: 'fieldset',
	            labelWidth: 55,
	            labelAlign : 'right',
	            buttonAlign : 'center',
	            title : '描述信息(请点击按钮进入服务申请)',
	            //defaults: {width: 200},	// Default config options for child items
	            defaultType: 'textfield',
	            autoHeight: true,
	            bodyStyle: Ext.isIE ? 'padding:0 0 5px 15px;' : 'padding:10px 15px;',
	            border: false,
	            style: {
	                "margin-left": "10px", // when you add custom margin in IE 6...
	                "margin-right": Ext.isIE6 ? (Ext.isStrict ? "-10px" : "-13px") : "0"  // you have to adjust for it somewhere else
	            },
				items : [enterButton,serviceName, startDate, endDate, descn]//,
//				buttons :[{xtype:'button',
//					id : 'button',
//					iconCls:'forward',
//					fieldLabel : "服务描述",
//					text : '进入服务项',
//					pressed:true,
//					disabled : true,
//					handler : function() {
//						var nowID = Ext.getCmp('treepanel').getSelectionModel().getSelectedNode().id;
//						window.location = webContext+ "/requireSIAction_toRequireInfo.action?id="+ nowID;                   
//					}}]
			}]
		});

		var items = new Array();
		items.push(this.formPanel);
		this.items = items;
		PagePanel.superclass.initComponent.call(this);
	}
})