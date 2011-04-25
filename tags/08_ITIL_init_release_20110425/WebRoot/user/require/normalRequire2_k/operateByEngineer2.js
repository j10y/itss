var gDataId = null;
var allProductIds = null;
function isChn(str){
		      var reg = /^[u4E00-u9FA5]+$/;
		      if(!reg.test(str)){
		       return false;
		      }
		      return true;
}
PageTemplates = Ext.extend(Ext.Panel, {
	id : "PageTemplates",
	layout : 'table',
	height : 'auto',
	align : 'center',
	foredit : true,
	width : 1050,
	frame : true,
	buttonAlign : 'center',
	autoScroll : true,
	defaults : {
		bodyStyle : 'padding:4px'
	},
	layoutConfig : {
		columns : 1
	},
	//暂存
	saveForTemp : function() {
		if(!Ext.getCmp('panel_ErpEngineerFeedback_f_Input').form.isValid()){
			Ext.MessageBox.alert("提示","页面中带红色波浪线的为必填项,请填写完整。谢谢您合作！");	
			return false;
		}
		var store = Ext.getCmp('panel_requireFactoryInfo_list_3').store;
		var dataRecords = new Array();
		var product = "";
		var i = 0;
		store.each(function(record) {
			dataRecords[i++] = record;	
			if (record.dirty) {
				product += Ext.encode(record.data) + ",";
			}
		})
		for(var i=0;i < dataRecords.length;i++){
			var strFactoryId = dataRecords[i].get("itil_req_RequireFactoryInfo$factoryId");
			var strStockAddressId = dataRecords[i].get("itil_req_RequireFactoryInfo$stockAddressId");
			if(strFactoryId==""||strFactoryId==null){
				Ext.MessageBox.alert("提示","工厂编号为必填项,请填写完整。谢谢您合作！");	
				return false;
			}else if(strFactoryId.length!=4){
				Ext.MessageBox.alert("提示","工厂编号长度为4个字符,请修改。谢谢您合作！");	
				return false;
			}
			else if(!isChn(strFactoryId)){
				Ext.MessageBox.alert("提示","工厂编号不能输入中文,请修改。谢谢您合作！");	
				return false;
			}
			if(strStockAddressId==""||strStockAddressId==null){
				Ext.MessageBox.alert("提示","库存地编号为必填项,请填写完整。谢谢您合作！");	
				return false;
			}else if(strStockAddressId.length!=4){
				Ext.MessageBox.alert("提示","库存地编号长度为4个字符,请修改。谢谢您合作！");	
				return false;
			}
			else if(!isChn(strStockAddressId)){
				Ext.MessageBox.alert("提示","库存地编号不能输入中文,请修改。谢谢您合作！");	
				return false;
			}
			if(dataRecords[i].get("itil_req_RequireFactoryInfo$wareHouseId").length>3){
				Ext.MessageBox.alert("提示","仓库号/混合仓库长度为3个字符,请修改。谢谢您合作！");	
				return false;
			}			
			if(dataRecords[i].get("itil_req_RequireFactoryInfo$stockAddressDesc")!=""){
				if(dataRecords[i].get("itil_req_RequireFactoryInfo$stockAddressDesc").length>16){
					Ext.MessageBox.alert("提示","库存地描述长度为16个字符,请修改。谢谢您合作！");
					dataRecords[i].set("itil_req_RequireFactoryInfo$stockAddressDesc","");
					dataRecords[i].commit(); 
					return false;
				}	
			}
		}						
		var formParam = getFormParam('panel_ErpEngineerFeedback_f_Input');
		var vp = null;
		if (formParam != null) {
			vp = Ext.apply(formParam, vp, {});
		}
		Ext.Ajax.request({
			url : webContext+ '/SRAction_reAssignAudit.action?taskId='+this.taskId,
			params : vp,
			success : function(response, options) {
				},
			failure : function(response, options) {
				Ext.MessageBox.alert("重新绑定本节点审批人失败");
			}
		}, this);
		Ext.Ajax.request({
			url : webContext+ '/requireAction_saveLockUser.action?taskId='+this.taskId,
			params : vp,
			success : function(response, options) {
				},
			failure : function(response, options) {
				Ext.MessageBox.alert("错误信息","审批节点加锁失败");
			}
		}, this);			
		var reqId = this.dataId;
		var store = Ext.getCmp('panel_requireFactoryInfo_list_3').store;
		var product = "";
		store.each(function(record) {
			if (record.dirty) {
				product += Ext.encode(record.data) + ",";
			}
		})	
		var formParams = Ext.encode(getFormParam('panel_ErpEngineerFeedback_f_Input'));
		
		Ext.Ajax.request({
			url : webContext+ '/SRAction_saveErpEngineerFeedbackAndGridInfo.action',
			timeout : 100000000,
			params : {
					info : formParams,					
					product : product,
					reqId : reqId
			},
			success : function(response, options) {
				var feedbackId = Ext.util.JSON.decode(response.responseText).id;
				Ext.getCmp('itil_sci_ErpEngineerFeedback$id').setValue(feedbackId);
				Ext.MessageBox.alert("暂存成功");
				},
			failure : function(response, options) {
				Ext.MessageBox.alert("保存失败");
			}
		}, this);
	},
	//保存并提交
	saveAndSubmit : function() {
		if(!Ext.getCmp('panel_ErpEngineerFeedback_f_Input').form.isValid()){
			Ext.MessageBox.alert("提示","页面中带红色波浪线的为必填项,请填写完整。谢谢您合作！");	
			return false;
		}
		var store = Ext.getCmp('panel_requireFactoryInfo_list_3').store;
		var dataRecords = new Array();
		var product = "";
		var i = 0;
		store.each(function(record) {
			dataRecords[i++] = record;	
			if (record.dirty) {
				product += Ext.encode(record.data) + ",";
			}
		})
		for(var i=0;i < dataRecords.length;i++){
			var strFactoryId = dataRecords[i].get("itil_req_RequireFactoryInfo$factoryId");
			var strStockAddressId = dataRecords[i].get("itil_req_RequireFactoryInfo$stockAddressId");
			if(strFactoryId==""||strFactoryId==null){
				Ext.MessageBox.alert("提示","工厂编号为必填项,请填写完整。谢谢您合作！");	
				return false;
			}else if(strFactoryId.length!=4){
				Ext.MessageBox.alert("提示","工厂编号长度为4个字符,请修改。谢谢您合作！");	
				return false;
			}
			else if(!isChn(strFactoryId)){
				Ext.MessageBox.alert("提示","工厂编号不能输入中文,请修改。谢谢您合作！");	
				return false;
			}
			if(strStockAddressId==""||strStockAddressId==null){
				Ext.MessageBox.alert("提示","库存地编号为必填项,请填写完整。谢谢您合作！");	
				return false;
			}else if(strStockAddressId.length!=4){
				Ext.MessageBox.alert("提示","库存地编号长度为4个字符,请修改。谢谢您合作！");	
				return false;
			}
			else if(!isChn(strStockAddressId)){
				Ext.MessageBox.alert("提示","库存地编号不能输入中文,请修改。谢谢您合作！");	
				return false;
			}
			if(dataRecords[i].get("itil_req_RequireFactoryInfo$wareHouseId").length>3){
				Ext.MessageBox.alert("提示","仓库号/混合仓库长度为3个字符,请修改。谢谢您合作！");	
				return false;
			}				
			if(dataRecords[i].get("itil_req_RequireFactoryInfo$stockAddressDesc")!=""){
				if(dataRecords[i].get("itil_req_RequireFactoryInfo$stockAddressDesc").length>16){
					Ext.MessageBox.alert("提示","库存地描述长度为16个字符,请修改。谢谢您合作！");
					dataRecords[i].set("itil_req_RequireFactoryInfo$stockAddressDesc","");
					dataRecords[i].commit(); 
					return false;
				}	
			}
		}			
		
		var reqId = this.dataId;
		var store = Ext.getCmp('panel_requireFactoryInfo_list_3').store;
		var product = "";
		store.each(function(record) {
			if (record.dirty) {
				product += Ext.encode(record.data) + ",";
			}
		})	
		var formParams = Ext.encode(getFormParam('panel_ErpEngineerFeedback_f_Input'));
		Ext.Ajax.request({
			url : webContext+ '/SRAction_saveErpEngineerFeedbackAndGridInfo.action',
			timeout : 100000000,
			params : {
					info : formParams,					
					product : product,
					reqId : reqId
			},
			success : function(response, options) {
				var feedbackId = Ext.util.JSON.decode(response.responseText).id;
				Ext.getCmp('itil_sci_ErpEngineerFeedback$id').setValue(feedbackId);
				window.parent.auditContentWin.specialAudit();
				},
			failure : function(response, options) {
				Ext.MessageBox.alert("保存失败");
			}
		}, this);
				
	},
	removePlan : function() {
		var record = Ext.getCmp('panel_requireFactoryInfo_list_3').getSelectionModel().getSelected();
		var records = Ext.getCmp('panel_requireFactoryInfo_list_3').getSelectionModel().getSelections();
		var store = Ext.getCmp('panel_requireFactoryInfo_list_3').store;
		if (!record) {
			Ext.Msg.alert("提示", "请先选择要删除的行!");
			return;
		}
		if (records.length == 0) {
			Ext.MessageBox.alert('警告', '最少选择一条信息，进行删除!');
		} else {
			Ext.MessageBox.confirm('提示框', '您确定要进行该操作？', function(btn) {
				if (btn == 'yes') {
					var remIds = "";
					if (records) {
						for (var i = 0; i < records.length; i++) {
							this.store.remove(records[i]);
							remIds += records[i].get("itil_req_RequireFactoryInfo$id") + ",";
						}
						Ext.Ajax.request({
							url : webContext
									+ '/SRAction_removeRequireFactoryInfo.action',
							params : {
								removedIds : remIds
							},
							success : function(response, options) {
								Ext.MessageBox.alert("提示", "删除成功", function() {
									store.reload();
									store.removeAll();
								});

							},
							failure : function(response, options) {
								Ext.MessageBox.alert("删除失败");
							}
						})
					}
				}
			}, this)
		}
	},	
	getTabpanel : function(tab, tabTitle) {
		this.tabPanel = new Ext.TabPanel({
			xtype : 'tabpanel',
			activeTab : 0,
			enableTabScroll : true,
			// minTabWidth:100,
			// resizeTabs:true,
			title : tabTitle,
			deferredRender : false,
			frame : true,
			plain : true,
			border : false,
			// tabPosition:'bottom',
			baseCls : 'x-plain',// 是否设置和背景色同步
			width : 900,
			// bodyBorder : true,
			defaults : {
				autoHeight : true,
				bodyStyle : 'padding:2px'
			},
			items : tab
		});
		return this.tabPanel;

	},
	getPanel : function(appa, panelTitle) {
		this.Panel = new Ext.Panel({
			id : "Pages",
			height : 'auto',
			align : 'center',
			title : panelTitle,
			border : false,
			defaults : {
				bodyStyle : 'padding:5px'
			},
			width : 'auto',
			frame : true,
			autoScroll : true,
			layoutConfig : {
				columns : 1
			},
			items : appa
		});
		return this.Panel;

	},

	getFormpanel_ERP_NormalNeed1_Input : function() {
		var da = new DataAction();
		var data = null;
		// 判断是新增还是修改
		var biddata = "";

		var buttonUtil = new ButtonUtil();
		this.getFormButtons = buttonUtil.getButtonForPanel(
				"panel_ERP_NormalNeed5_Input", this);
		if (this.dataId != '0') {
			data = da.getPanelElementsForEdit("nr_operateByEngineer2_k",
					"panel_ERP_NormalNeed5_Input", this.dataId);// 这是要随时变得
			biddata = da.split(data);
		} else {
			data = da.getPanelElementsForAdd("panel_ERP_NormalNeed5_Input");
			biddata = da.split(data);
		}
		if (this.getFormButtons.length != 0) {
			this.formpanel_ERP_NormalNeed1_Input = new Ext.form.FormPanel({
				id : 'panel_ERP_NormalNeed5_Input',
				layout : 'table',
				height : 'auto',
				width : 800,
				frame : true,
				collapsible : true,
				defaults : {
					bodyStyle : 'padding:4px'
				},
				layoutConfig : {
					columns : 4
				},
				title : "ERP常规服务申请",
				items : biddata,
				buttons : this.getFormButtons
			});
		} else {
			this.formpanel_ERP_NormalNeed1_Input = new Ext.form.FormPanel({
				id : 'panel_ERP_NormalNeed5_Input',
				layout : 'table',
				height : 'auto',
				width : 800,
				frame : true,
				collapsible : true,
				defaults : {
					bodyStyle : 'padding:4px'
				},
				layoutConfig : {
					columns : 4
				},
				title : "ERP常规服务申请",
				items : biddata
			});
		}
		return this.formpanel_ERP_NormalNeed1_Input;
	},
	getFormpanel_ErpEngineerFeedback_Input : function() {
		var da = new DataAction();
		var data = null;
		// 判断是新增还是修改
		var biddata = "";

		var buttonUtil = new ButtonUtil();
		this.getFormButtons = buttonUtil.getButtonForPanel(
				"panel_ErpEngineerFeedback_f_Input", this);

		if (this.dataId != '0') {
			data = da.getPanelElementsForEdit("nr_operateByEngineer2_k",
					"panel_ErpEngineerFeedback_f_Input", this.dataId);// 这是要随时变得
			for (var i = 0; i < data.length; i++) {
				// alert(data[i].id);
				if (data[i].id == 'itil_sci_ErpEngineerFeedback$basisFlagCombo') {

					data[i].on('select', function(combo, record, index) {
						var s = Ext.getCmp('itil_sci_ErpEngineerFeedback$basisFlagCombo').getValue();
						if (s == '1') {
							Ext.getCmp('itil_sci_ErpEngineerFeedback$basisEngineerCombo').enable();
						} else {
							Ext.getCmp('itil_sci_ErpEngineerFeedback$basisEngineerCombo').clearValue();
							Ext.getCmp('itil_sci_ErpEngineerFeedback$basisEngineerCombo').disable();
						}
					})
				}

				if (data[i].id == 'itil_sci_ErpEngineerFeedback$basisEngineerCombo') {
					data[i].disabled = true;
				}
			}
		} else {
			data = da.getPanelElementsForEdit("nr_operateByEngineer2_k",
					"panel_ErpEngineerFeedback_f_Input", this.dataId);// 这是要随时变得
			for (var i = 0; i < data.length; i++) {
				if (data[i].id == 'itil_sci_ErpEngineerFeedback$basisFlagCombo') {
					data[i].on('select', function(combo, record, index) {
						var s = Ext.getCmp('itil_sci_ErpEngineerFeedback$basisFlagCombo').getValue();
						if (s == '1') {
							Ext.getCmp('itil_sci_ErpEngineerFeedback$basisEngineerCombo').enable();
						} else {
							Ext.getCmp('itil_sci_ErpEngineerFeedback$basisEngineerCombo').clearValue();
							Ext.getCmp('itil_sci_ErpEngineerFeedback$basisEngineerCombo').disable();
						}
					})
				}

				if (data[i].id == 'itil_sci_ErpEngineerFeedback$basisEngineerCombo') {
					data[i].disabled = true;
				}
			}

		}
		var modifyData = new Array();
		for (var i = 0; i < data.length; i++) {
			if(i==3){
				modifyData.push(this.getpanel_requireFactoryInfo_list_1());
			}
			if(i==4 && this.readOnly != 1){
					modifyData.push(this.getPercentAndDayCountPanel());
			}			
			modifyData.push(data[i]);
		}
		biddata = da.split(modifyData);			
		if (this.getFormButtons.length != 0) {
			this.formpanel_ErpEngineerFeedback_Input = new Ext.form.FormPanel({
				id : 'panel_ErpEngineerFeedback_f_Input',
				layout : 'table',
				height : 'auto',
				width : 800,
				frame : true,
				collapsible : true,
				defaults : {
					bodyStyle : 'padding:4px'
				},
				layoutConfig : {
					columns : 4
				},
				title : "<font color=red>Erp工程师反馈</font>",
				items : biddata,
				buttons : this.getFormButtons
			});
		} else {
			this.formpanel_ErpEngineerFeedback_Input = new Ext.form.FormPanel({
				id : 'panel_ErpEngineerFeedback_f_Input',
				layout : 'table',
				height : 'auto',
				width : 800,
				frame : true,
				collapsible : true,
				defaults : {
					bodyStyle : 'padding:4px'
				},
				layoutConfig : {
					columns : 4
				},
				title : "<font color=red>Erp工程师反馈</font>",
				items : biddata
			});
		}
		return this.formpanel_ErpEngineerFeedback_Input;
	},
	getpanel_requireFactoryInfo_list_1:function(){
		var da = new DataAction();
		var obj = da.getPanelElementsForHead("panel_requireFactoryInfo_list_3");
//		for(var  i = 0; i<obj.length;i++){
//			alert(obj[i].dataIndex);
//		}
		var buttonUtil = new ButtonUtil();
		var getGridButtons = buttonUtil.getButtonForPanel("panel_requireFactoryInfo_list_3",
				this);
		var modifyButtons = new Array();//屏蔽ERP工程师对工厂信息的添加删除按钮，以后可能用户要求有“添加”功能，打开下面注释即可
			for(var i = 0;i<getGridButtons.length;i++){
//				if (getGridButtons[i].id=='ext-comp-1019') {//“添加”按钮
//					modifyButtons.push(getGridButtons[i]);
//				}	
			}
		modifyButtons.push(new Ext.Toolbar.TextItem("<font color='red' style='padding-left:18px;font-size:12px;'>单击“工厂编号”标题处，即可显示下拉条</font>"));	
		var sm = new Ext.grid.CheckboxSelectionModel();
		var columns = new Array();
		columns[0] = sm;
		// 循环出所有的行，然后增加属性修饰
		for (var i = 0; i < obj.length; i++) {
			var headItem = obj[i];
			var title = headItem.header;
			var alignStyle = 'left';
			var propertyName = headItem.dataIndex;
			var editor = headItem.editor;
			var renderer = headItem.renderer;
			var hide = headItem.hidden;
			var isHidden = false;
			if (hide == 'true') {
				isHidden = true;
			}
			// 给每一行增加修饰
			var columnItem = "";
			if (editor.xtype == 'combo') {
				columnItem = {
					header : title,
					dataIndex : propertyName,
					sortable : true,
					hidden : isHidden,
					align : alignStyle,
					renderer : renderer,
					editor : editor
				}
			} else if (editor.xtype == 'datefield') {
				columnItem = {
					header : title,
					dataIndex : propertyName,
					sortable : true,
					hidden : isHidden,
					align : alignStyle,
					renderer : renderer,
					editor : editor
				}
			} else {
				columnItem = {
					header : title,
					dataIndex : propertyName,
					sortable : true,
					hidden : isHidden,
					align : alignStyle,
					editor : editor
				}
			}
			columns[i + 1] = columnItem;
		}	
		this.cm = new Ext.grid.ColumnModel(columns);
		this.store = da.getPagePanelJsonStore("panel_requireFactoryInfo_list_3", obj);
		this.cm.defaultSortable = true;
		var viewConfig = Ext.apply({
			forceFit : true
		}, this.gridViewConfig);
		this.formValue = '';
		var gridpanel_requireFactoryInfo_list_1 = new Ext.grid.EditorGridPanel({
			fieldLabel : '工厂明细',
			id : 'panel_requireFactoryInfo_list_3',
			store : this.store,
			cm : this.cm,
			sm : sm,
			trackMouseOver : false,
			loadMask : true,
			clicksToEdit : 2,
			collapsible : true,
			height : 220,
			width : 9999,
			frame : true,
			tbar : [modifyButtons],
			autoScroll :true,
			listeners :{
				afteredit:function(obj){
							var store = Ext.getCmp('panel_requireFactoryInfo_list_3').store;
							var dataRecords = new Array();
							var product = "";
							var i = 0;
							store.each(function(record) {
								dataRecords[i++] = record;	
								if (record.dirty) {
									product += Ext.encode(record.data) + ",";
								}
							})
							for(var i=0;i < dataRecords.length;i++){
								var strFactoryId = dataRecords[i].get("itil_req_RequireFactoryInfo$factoryId");
								var strStockAddressId = dataRecords[i].get("itil_req_RequireFactoryInfo$stockAddressId");
								if(strFactoryId==""||strFactoryId==null){
									Ext.MessageBox.alert("提示","工厂编号为必填项,请填写完整。谢谢您合作！");	
									return false;
								}else if(strFactoryId.length!=4){
									Ext.MessageBox.alert("提示","工厂编号长度为4个字符,请修改。谢谢您合作！");	
									return false;
								}
								else if(!isChn(strFactoryId)){
									Ext.MessageBox.alert("提示","工厂编号不能输入中文,请修改。谢谢您合作！");	
									return false;
								}
								if(strStockAddressId==""||strStockAddressId==null){
									Ext.MessageBox.alert("提示","库存地编号为必填项,请填写完整。谢谢您合作！");	
									return false;
								}else if(strStockAddressId.length!=4){
									Ext.MessageBox.alert("提示","库存地编号长度为4个字符,请修改。谢谢您合作！");	
									return false;
								}
								else if(!isChn(strStockAddressId)){
									Ext.MessageBox.alert("提示","库存地编号不能输入中文,请修改。谢谢您合作！");	
									return false;
								}
								if(dataRecords[i].get("itil_req_RequireFactoryInfo$wareHouseId").length>3){
									Ext.MessageBox.alert("提示","仓库号/混合仓库长度为3个字符,请修改。谢谢您合作！");	
									return false;
								}								
								if(dataRecords[i].get("itil_req_RequireFactoryInfo$stockAddressDesc")!=""){
									if(dataRecords[i].get("itil_req_RequireFactoryInfo$stockAddressDesc").length>16){
										Ext.MessageBox.alert("提示","库存地描述长度为16个字符,请修改。谢谢您合作！");
										dataRecords[i].set("itil_req_RequireFactoryInfo$stockAddressDesc","");
										dataRecords[i].commit(); 
										return false;
									}	
								}
								if(dataRecords[i].get("itil_req_RequireFactoryInfo$mrpFlag")!=""){
									if(dataRecords[i].get("itil_req_RequireFactoryInfo$mrpFlag").length>1){
										Ext.MessageBox.alert("提示","MRP标识长度为1个字符,请修改。谢谢您合作！");
										dataRecords[i].set("itil_req_RequireFactoryInfo$mrpFlag","");
										dataRecords[i].commit(); 									
										return false;
									}
								}
								if(dataRecords[i].get("itil_req_RequireFactoryInfo$shipCondition")!=""){
									if(dataRecords[i].get("itil_req_RequireFactoryInfo$shipCondition").length>2){
										Ext.MessageBox.alert("提示","装运条件长度为2个字符,请修改。谢谢您合作！");
										dataRecords[i].set("itil_req_RequireFactoryInfo$shipCondition","");
										dataRecords[i].commit(); 								
										return false;
									}
								}
								if(dataRecords[i].get("itil_req_RequireFactoryInfo$shipPoint")!=""){
									if(dataRecords[i].get("itil_req_RequireFactoryInfo$shipPoint").length>4){
										Ext.MessageBox.alert("提示","装运点长度为4个字符,请修改。谢谢您合作！");
										dataRecords[i].set("itil_req_RequireFactoryInfo$shipPoint","");
										dataRecords[i].commit(); 
										return false;
									}
								}
								if(dataRecords[i].get("itil_req_RequireFactoryInfo$noOrdersTransportShipPoint")!=""){
									 if(dataRecords[i].get("itil_req_RequireFactoryInfo$noOrdersTransportShipPoint").length>4){
										Ext.MessageBox.alert("提示","无单运输装运点长度为4个字符,请修改。谢谢您合作！");
										dataRecords[i].set("itil_req_RequireFactoryInfo$noOrdersTransportShipPoint","");
										dataRecords[i].commit(); 										
										return false;
									}		
								}
							}							
							Ext.Ajax.request({
								url : webContext+ '/requireAction_saveGridInfo.action?dataId='+gDataID,
								params : {
										product : product
								},
								success : function(response, options) {
									Ext.getCmp('panel_requireFactoryInfo_list_3').store.reload(); 
									},
								failure : function(response, options) {
									Ext.MessageBox.alert("工厂信息保存失败");
								}
							}, this);
				}
			}, 
			viewConfig: {   
                  forceFit:true   
            },   
           bbar: new Ext.PagingToolbar({   
           	    id:'pagId',
                pageSize: 10,   
                store: this.store,   
               displayInfo: true
           }) 			
		});
		if (this.dataId != '') {
			var pcnameValue = "";
			var fcnameObj = Ext.getCmp("ERP_NormalNeed$id");
			if (fcnameObj != undefined) {
				pcnameValue = Ext.getCmp("ERP_NormalNeed$id").getValue();
			}
			var str = '{' + '\"' + "requireData" + '\"' + ':' + '\"'
					+ pcnameValue + '\"' + '}';
			var param = eval('(' + str + ')');
			param.methodCall = 'query';
			param.start = 0;
			this.store.baseParams=param;
			this.store.load();
		}	
	return 	gridpanel_requireFactoryInfo_list_1;
		
	},
	getPercentAndDayCountPanel : function(){
	var percentAndDayCountPanel = new Ext.form.FieldSet({
			title : '写入ERP',
			fieldLabel:'写入ERP', 
			id : 'percentAndDayCountPanel',
			width : 9999,
			layout :'table',
			buttonAlign : 'center',
			layoutConfig : {
						columns : 4
			},
			items : [
				{
					html : '<h3><font color=red>使用提示:</font></h3><br>1、首先填写“传输请求号”字段信息。<br>2、点击"写入ERP"按钮，自动将库存地信息通过接口写入ERP系统。<br>3、“反馈”字段需手动编辑填写。<br><br>',
					cls : 'common-text',
					colspan : 4,
					rowspan : 0,
					style : 'border:1px dotted #b0acac;width:455;text-align:left;margin:0px 0px 0px 0px'
				}
			],
			 buttons: [{
       				id:"writeErp",
					text :"写入ERP",
					style : "width:150;text-align:right;",
					width:150,
					handler:function(){						
						var writeErpButton = Ext.getCmp('writeErp');
						writeErpButton.disabled=true;
						var transferRequestNumber = Ext.getCmp('itil_sci_ErpEngineerFeedback$transferRequestNumber').getValue();
						if(transferRequestNumber == null || transferRequestNumber == "" ){
							Ext.Msg.alert("提示信息","请先输入“传输请求号”");
							return;
						}else if(transferRequestNumber.length>10){
							Ext.Msg.alert("提示信息","“传输请求号”长度不能大于10位字符");
							return;
						}
					var griData = Ext.getCmp('panel_requireFactoryInfo_list_3').getStore().getRange();
					var productIds = ""
					for (var i = 0; i < griData.length; i++) {
						productIds += griData[i].get("itil_req_RequireFactoryInfo$id") + ",";
					}
						Ext.Ajax.request({
								url : webContext+ '/requireAction_saveRequireFactoryInfo.action',
								params : {
									dataId:this.dataId,
									productIds:allProductIds,
									transferRequestNumber:transferRequestNumber
									
								},
								success : function(response, options) {
									var responseArray =Ext.util.JSON.decode(response.responseText);
									var text = "";
									for(var i=0;i<responseArray.length;i++){
										if(i==0){
											text +="<p style='font-size: 14px;'>SUBRC:"+responseArray[i].SUBRC+"</p>";
										}else{
											text +="<p style='font-size: 14px;'> ARBGB:"+responseArray[i].ARBGB+", MSGNR:"+responseArray[i].MSGNR+", TEXT:"+responseArray[i].TEXT+"。</p>";
										}
									}	
									var resultWindow = new Ext.Window({
										title : '写入ERP反馈信息',
										width : 500,
										height : 250,
										autoScroll : true,
										//autoWidth : true,
										html : text
									});
									resultWindow.show();
	
								},
								failure : function(response, options) {
									Ext.MessageBox.alert("错误信息","写入ERP失败");
								}
							}, this);
						
					}
   			 }]

		});
		return 	percentAndDayCountPanel;
	},	
	items : this.items,
	buttons : this.buttons,
	/*
	 * clientvalidation 初始化
	 */
	initComponent : function() {
		gDataID = this.dataId;
		Ext.Ajax.request({
				url : webContext+ '/requireAction_getAllProductIds.action',
				params : {
					dataId:this.dataId									
				},
		success : function(response, options) {
				var responseArray =Ext.util.JSON.decode(response.responseText);
				allProductIds = responseArray.allProductIds;
		},
		failure : function(response, options) {
				Ext.Msg('错误信息','获取所有工厂编号失败');
		}
		}, this);
		var isLockUser = true;
		var  sra = new SRAction();
		isLockUser = sra.getConfirmLockUser(this.taskId);			
		var histroyForm = new HistroyForm({
			reqId : this.dataId,
			reqClass : "com.digitalchina.itil.require.entity.ERP_NormalNeed"
		});
		var items = new Array();
		var pa = new Array();
		this.pa = pa;
		var gd = new Array();
		this.gd = gd;
		var temp = new Array();
		this.temp = temp;
		var formname = new Array();
		this.formname = formname;
		var gridname = new Array();
		this.gridname = gridname;
		this.model = "nr_operateByEngineer2_k";
		var buttonUtil = new ButtonUtil();
		this.mybuttons = buttonUtil.getButtonForModel("nr_operateByEngineer2_k",
				this);
		this.buttons = new Array();
		if (this.readOnly != 1) {
			this.buttons = this.mybuttons;
		}
		if(!isLockUser){
			for(var i=0;i<this.buttons.length;i++){
				this.buttons[i].disabled=true;
			}								
		}		
		this.getFormpanel_ERP_NormalNeed1_Input();
		this.pa.push(this.formpanel_ERP_NormalNeed1_Input);
		this.formname.push("panel_ERP_NormalNeed5_Input");
		temp.push(this.formpanel_ERP_NormalNeed1_Input);
		this.getFormpanel_ErpEngineerFeedback_Input();
		this.pa.push(this.formpanel_ErpEngineerFeedback_Input);
		this.formname.push("panel_ErpEngineerFeedback_f_Input");
		temp.push(this.formpanel_ErpEngineerFeedback_Input);
		temp.push(histroyForm);
		items.push(this.getTabpanel(temp));

		this.items = items;
		this.on("saveAndAssign", this.saveAndAssign, this);
		this.on('saveAndSubmit', this.saveAndSubmit, this);
		this.on('saveForTemp', this.saveForTemp, this);
		this.on("removePlan", this.removePlan, this);
		// remove by lee for 除掉默认同意 in 20090806 begin
		// var tempErpId =
		// Ext.getCmp('itil_sci_ErpEngineerFeedback$id').getValue();
		// if(tempErpId==''){
		// Ext.getCmp('itil_sci_ErpEngineerFeedback$feekback').setValue('同意');
		// Ext.getCmp('itil_sci_ErpEngineerFeedback$otherInfo').setValue('同意');
		// }
		// remove by lee for 除掉默认同意 in 20090806 end
	
		PageTemplates.superclass.initComponent.call(this);
	}
})