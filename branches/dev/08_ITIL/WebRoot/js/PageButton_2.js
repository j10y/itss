//按钮工具类
ButtonUtil = Ext.extend(Ext.util.Observable,{
	getModelButtonPanel : function(modelName,scope){
		var buttons = this.getButtonForModel(modelName,scope);
		var buttonPanel = {
			layout : 'table',
			height : 'auto',
			width : 800,
			style : 'margin:4px 6px 4px 300px',
			align : 'center',
			defaults : {
				bodyStyle : 'padding:4px'
			},
			items : buttons
		};
		return buttonPanel;
	},
	getPanelButtonPanel : function(panelName,scope){
		var buttons = this.getButtonForPanel(panelName,scope);
		var buttonPanel = {
			layout : 'table',
			height : 'auto',
			width : 800,
			style : 'margin:4px 6px 4px 300px',
			align : 'center',
			defaults : {
				bodyStyle : 'padding:4px'
			},
			items : buttons
		};
		return buttonPanel;
	},
	getButtonForModel : function(modelName,scope){
		var url = webContext+'/extjs/pageView?method=pageModelButton&modelName='+modelName;
		var data = this.ajaxGetpage(url);
		var modelButtons=this.getButtons(data,scope);
		return modelButtons;
	},
	getButtonForPanel : function(panelName,scope){
		var url = webContext+'/extjs/pageView?method=pagePanelButton&panelName='+panelName;
		var data = this.ajaxGetpage(url);
		var panelButtons=this.getButtons(data,scope);
		return panelButtons;
	},
	getButtons : function(buttonStrs,scope) {
		var barbuttons=new Array();
		for(var i = 0;i<buttonStrs.length;i++){
			var btnName=buttonStrs[i].btnName;//按钮中文名
			var container=buttonStrs[i].container;//按钮容器名，即模板或面板名称
			var method=buttonStrs[i].method;//按钮对应动作
			var link=buttonStrs[i].link;//按钮指定连接
			var nextPageModel=buttonStrs[i].nextPageModel;//按钮指定下一个pageModel
			var imageUrl=buttonStrs[i].imageUrl;//按钮图标
			//构造Button，根据不同的动作构造按钮
			if(method=="addByPage"){
				barbuttons[i]=this.createAddByPage(btnName,link,imageUrl,scope);
			}else if(method=="addByWindow"){
				barbuttons[i]=this.createAddByWindow(btnName,link,imageUrl,nextPageModel,scope);
			}else if(method=="addByColumn"){
				barbuttons[i]=this.createAddByColumn(btnName,link,imageUrl,scope);
			}else if(method=="saveForModel"){
				barbuttons[i]=this.createSaveForModel(btnName,link,imageUrl,scope);
			}else if(method=="saveForFormPanel"){
				barbuttons[i]=this.createSaveForFormPanel(btnName,container,imageUrl,scope);
			}else if(method=="saveForGridPanel"){
				barbuttons[i]=this.createSaveForGridPanel(btnName,container,imageUrl,scope);
			}else if(method=="saveForModelConfigItem"){
				barbuttons[i]=this.createSaveForModelConfigItem(btnName,link,imageUrl,scope);
			}else if(method=="saveForPanelConfigItem"){
				barbuttons[i]=this.createSaveForPanelConfigItem(btnName,container,imageUrl,scope);
			}else if(method=="removeForModel"){
				barbuttons[i]=this.createRemoveForModel(btnName,link,imageUrl,scope);
			}else if(method=="removeByColumn"){
				barbuttons[i]=this.createRemoveByColumn(btnName,link,imageUrl,scope);
			}else if(method=="removeForGrid"){
				barbuttons[i]=this.createRemoveForGrid(btnName,link,imageUrl,scope);
			}else if(method=="search"){//查询
				barbuttons[i]=this.createSearch(btnName,link,imageUrl,scope);
			}else if(method=="reset"){
				barbuttons[i]=this.createReset(btnName,link,imageUrl,scope);
			}else if(method=="resetPanel"){//重置单面板
				barbuttons[i]=this.createResetForPanel(btnName,container,imageUrl,scope);
			}else if(method=="modifyByPage"){//通过重定向页面进行修改
				var linkPath=link;
				barbuttons[i]=this.createModifyByPage(btnName,link,imageUrl,scope);
				scope.modify=function(){//为页面双击事件方法赋值
					var record = this.grid.getSelectionModel().getSelected();
					var records = this.grid.getSelectionModel().getSelections();
					if(!record){
						Ext.Msg.alert("提示","请先选择要修改的行!");
						return;
					}
					if(records.length>1){
						Ext.Msg.alert("提示","修改时只能选择一行!");
						return;
					}
					var modelTableId = this.modelTableName+"$id";
					var dataId = record.get(modelTableId);
					window.location = webContext+ linkPath + dataId;
				}
			}else if(method=="modifyByWindow"){//通过弹出窗体进行修改
				barbuttons[i]=this.createModifyByPage(btnName,link,imageUrl,nextPageModel,scope);
				scope.modify=function(){
					alert("通过弹出窗体进行修改暂未实现！");
				}
			}else if(method=="submit"){//提交
				barbuttons[i]=this.createSubmit(btnName,link,imageUrl,scope);
			}else if(method=="import"){//导入
				barbuttons[i]=this.createImport(btnName,link,imageUrl,scope);
			}else if(method=="export"){//导出
				barbuttons[i]=this.createExport(btnName,link,imageUrl,scope);
			}else if(method=="upload"){//上传
				barbuttons[i]=this.createUpLoad(btnName,link,imageUrl,scope);
			}else if(method=="download"){//下载
				barbuttons[i]=this.createDownLoad(btnName,link,imageUrl,scope);
			}else if(method=="goBack"){//返回
				barbuttons[i]=this.createGoBack(btnName,link,imageUrl,scope);
			}else{//
				barbuttons[i]=this.createPageEvent(btnName,method,link,imageUrl,scope);
			}
		}
		return barbuttons;
	},
	/**通过重定向添加页面来添加
	*link指定重定向页面
	*/
	createAddByPage : function(btnName,link,imageUrl,scope){
		var button=new Ext.Button({
			text:btnName,
			pressed:true,
			iconCls:imageUrl,
			scope : scope,
			handler:function(){
				if(link=="")
				alert("未指定添加跳转链接页面！");
				window.location = webContext+link;
			}
		});
		return button;
	},
	/*通过弹出窗体来添加
	*
	*/
	createAddByWindow : function(btnName,link,imageUrl,nextPageModel,scope){
		var button=new Ext.Button({
			text:btnName,
			pressed:true,
			iconCls:imageUrl,
			scope : scope,
			handler:function(){
				if(link=="")
				alert("未指定添加跳转链接页面！");
				if(nextPageModel=="")
				alert("未指定添加跳转窗体页面模板！");
				window.location = webContext+link+"?pageModel="+nextPageModel;
			}
		});
		return button;
	},
	/*通过在表单中增加一条数据的形式添加,并不对数据库进行操作
	*
	*/
	createAddByColumn : function(btnName,link,imageUrl,scope){
		var button=new Ext.Button({
			text:btnName,
			pressed:true,
			iconCls:imageUrl,
			scope : scope,
			handler : function() {
				var store = this.store;
				if (store.recordType) {
					var rec = new store.recordType({
						newRecord : true
					});
					rec.fields.each(function(f) {
						rec.data[f.name] = f.defaultValue || null;
					});
					rec.commit();
					store.add(rec);
					return rec;
				}
				return false;
			}
		});
		return button;
	},
	/*保存整个页面所有信息
	*
	*/
	createSaveForModel : function(btnName,link,imageUrl,scope){
		var button=new Ext.Button({
			text:btnName,
			pressed:true,
			iconCls:imageUrl,
			scope : scope,
			handler:function(){
				var formParam = "";
				var gridParam = "";
				var param = '{'; 
				if (this.pa.length != "0") {
					for (var i = 0; i < this.pa.length; i++) {
						var fP = Ext.encode(this.pa[i].form.getValues(false));
						formParam += '\"' + this.formname[i] + '\"' + ':[' + fP + '],';
					}
					param += formParam;
				}
				if (this.gd.length != "0") {
					for (var k = 0; k < this.gd.length; k++) {
						var gParam = "";
						var gP = this.gd[k].getStore().getRange(0,
								this.gd[k].getStore().getCount());
						for (i = 0; i < gP.length; i++) {
							gParam += Ext.encode(gP[i].data) + ",";
						}
						gParam = gParam.slice(0, gParam.length - 1);
						gridParam += '\"' + this.gridname[k] + '\"' + ':[' + gParam
								+ '],';
					}
					param += gridParam;
				}
				param = param.slice(0, param.length - 1) + '}';
			  alert(param);
				Ext.Ajax.request({
					url : webContext + '/extjs/pageData?method=save',
					params : {
						info : param,
						model : this.model
					},
					success : function(response, options) {
						Ext.MessageBox.alert("保存成功");
						//window.location = webContext + link;
					},
					failure : function(response, options) {
						Ext.MessageBox.alert("保存失败");
					}
				}, this);
			}
		});
		return button;
	},
	/*保存单独表单页面信息
	*
	*/
	createSaveForFormPanel : function(btnName,container,imageUrl,scope){
		var button=new Ext.Button({
			text:btnName,
			pressed:true,
			iconCls:imageUrl,
			scope : scope,
			handler:function(){
				/*var fP = Ext.encode(Ext.getCmp(container).form.getValues(false));
				var formParam = '{\"' + container + '\"' + ':[' + fP + ']}';
				alert(formParam);
				Ext.Ajax.request({
					url : webContext + '/extjs/pageData?method=savePanel',
					params : {
						info : formParam,
						model : this.model,
						modelId : this.dataId,
					},
					success : function(response, options) {
						Ext.MessageBox.alert("保存成功");
					},
					failure : function(response, options) {
						Ext.MessageBox.alert("保存失败");
					}
				}, this);*/
				var formParam = "";
				var gridParam = "";
				var param = '{'; 
				if (this.pa.length != "0") {
					for (var i = 0; i < this.pa.length; i++) {
						var fP = Ext.encode(this.pa[i].form.getValues(false));
						formParam += '\"' + this.formname[i] + '\"' + ':[' + fP + '],';
					}
					param += formParam;
				}
				if (this.gd.length != "0") {
					for (var k = 0; k < this.gd.length; k++) {
						var gParam = "";
						var gP = this.gd[k].getStore().getRange(0,
								this.gd[k].getStore().getCount());
						for (i = 0; i < gP.length; i++) {
							gParam += Ext.encode(gP[i].data) + ",";
						}
						gParam = gParam.slice(0, gParam.length - 1);
						gridParam += '\"' + this.gridname[k] + '\"' + ':[' + gParam
								+ '],';
					}
					param += gridParam;
				}
				param = param.slice(0, param.length - 1) + '}';
			  alert(param);
				Ext.Ajax.request({
					url : webContext + '/extjs/pageData?method=savePanel',
					params : {
						info : param,
						panel : container,
						model : this.model
					},
					success : function(response, options) {
						Ext.MessageBox.alert("保存成功");
						//window.location = webContext + link;
					},
					failure : function(response, options) {
						Ext.MessageBox.alert("保存失败");
					}
				}, this);
			}
		});
		return button;
	},
	/*保存单独表单页面信息
	*
	*/
	createSaveForGridPanel : function(btnName,container,imageUrl,scope){
		var button=new Ext.Button({
			text:btnName,
			pressed:true,
			iconCls:imageUrl,
			scope : scope,
			handler:function(){
				/*var gParam = "";
				var gP = Ext.getCmp(container).getStore().getRange(0,Ext.getCmp(container).getStore().getCount());
				for (i = 0; i < gP.length; i++) {
					gParam += Ext.encode(gP[i].data) + ",";
				}
				gParam = gParam.slice(0, gParam.length - 1);
				var gridParam = '\{"' + container + '\"' + ':[' + gParam + ']}';
				alert(gridParam);
				Ext.Ajax.request({
					url : webContext + '/extjs/pageData?method=savePanel',
					params : {
						info : gridParam,
						model : this.model
					},
					success : function(response, options) {
						Ext.MessageBox.alert("保存成功");
					},
					failure : function(response, options) {
						Ext.MessageBox.alert("保存失败");
					}
				}, this);*/
				var formParam = "";
				var gridParam = "";
				var param = '{'; 
				if (this.pa.length != "0") {
					for (var i = 0; i < this.pa.length; i++) {
						var fP = Ext.encode(this.pa[i].form.getValues(false));
						formParam += '\"' + this.formname[i] + '\"' + ':[' + fP + '],';
					}
					param += formParam;
				}
				if (this.gd.length != "0") {
					for (var k = 0; k < this.gd.length; k++) {
						var gParam = "";
						var gP = this.gd[k].getStore().getRange(0,
								this.gd[k].getStore().getCount());
						for (i = 0; i < gP.length; i++) {
							gParam += Ext.encode(gP[i].data) + ",";
						}
						gParam = gParam.slice(0, gParam.length - 1);
						gridParam += '\"' + this.gridname[k] + '\"' + ':[' + gParam
								+ '],';
					}
					param += gridParam;
				}
				param = param.slice(0, param.length - 1) + '}';
			  alert(param);
				Ext.Ajax.request({
					url : webContext + '/extjs/pageData?method=savePanel',
					params : {
						info : param,
						panel : container,
						model : this.model
					},
					success : function(response, options) {
						Ext.MessageBox.alert("保存成功");
					},
					failure : function(response, options) {
						Ext.MessageBox.alert("保存失败");
					}
				}, this);
			}
		});
		return button;
	},
	
	
	/******************************
	* 保存配置项信息表单页面所有面板数据的方法
	*/
	createSaveForModelConfigItem : function(btnName,link,imageUrl,scope){
		var button=new Ext.Button({
			text:btnName,
			pressed:true,
			iconCls:imageUrl,
			scope : scope,
			handler:function(){
				alert("baoc");
				var formParam = "";
				var gridParam = "";
				var param = '{'; 
				if (this.pa.length != "0") {
					for (var i = 0; i < this.pa.length; i++) {
						var fP = Ext.encode(this.pa[i].form.getValues(false));
						formParam += '\"' + this.formname[i] + '\"' + ':[' + fP + '],';
					}
					param += formParam;
				}
				if (this.gd.length != "0") {
					for (var k = 0; k < this.gd.length; k++) {
						var gParam = "";
						var gP = this.gd[k].getStore().getRange(0,
								this.gd[k].getStore().getCount());
						for (i = 0; i < gP.length; i++) {
							gParam += Ext.encode(gP[i].data) + ",";
						}
						gParam = gParam.slice(0, gParam.length - 1);
						gridParam += '\"' + this.gridname[k] + '\"' + ':[' + gParam
								+ '],';
					}
					param += gridParam;
				}
				param = param.slice(0, param.length - 1) + '}';
			 // alert(param);
				Ext.Ajax.request({
					url : webContext + '/extjs/pageData?method=saveConfigItem',
					params : {
						info : param,
						model : this.model
					},
					success : function(response, options) {
						//alert(link);
						window.location = webContext + link;
					},
					failure : function(response, options) {
						Ext.MessageBox.alert("保存失败");
					}
				}, this);
			}
		});
		return button;
	},
	/*保存
	*
	*/
	createSaveForPanelConfigItem : function(btnName,container,imageUrl,scope){
		var button=new Ext.Button({
			text:btnName,
			pressed:true,
			iconCls:imageUrl,
			scope : scope,
			handler:function(){
				alert("baoc");
				var formParam = "";
				var gridParam = "";
				var param = '{'; 
				if (this.pa.length != "0") {
					for (var i = 0; i < this.pa.length; i++) {
						var fP = Ext.encode(this.pa[i].form.getValues(false));
						formParam += '\"' + this.formname[i] + '\"' + ':[' + fP + '],';
					}
					param += formParam;
				}
				if (this.gd.length != "0") {
					for (var k = 0; k < this.gd.length; k++) {
						var gParam = "";
						var gP = this.gd[k].getStore().getRange(0,
								this.gd[k].getStore().getCount());
						for (i = 0; i < gP.length; i++) {
							gParam += Ext.encode(gP[i].data) + ",";
						}
						gParam = gParam.slice(0, gParam.length - 1);
						gridParam += '\"' + this.gridname[k] + '\"' + ':[' + gParam
								+ '],';
					}
					param += gridParam;
				}
				param = param.slice(0, param.length - 1) + '}';
				Ext.Ajax.request({
					url : webContext + '/extjs/pageData?method=saveConfigItemPanel',
					params : {
						info : param,
						panel : container,
						model : this.model
					},
					success : function(response, options) {
						//alert(link);
						window.location = webContext + link;
					},
					failure : function(response, options) {
						Ext.MessageBox.alert("保存失败");
					}
				}, this);
			}
		});
		return button;
	},
	//end ***********************************
	
	/*删除整个页面所有信息
	*
	*/
	createRemoveForModel : function(btnName,link,imageUrl,scope){
		var button=new Ext.Button({
			text:btnName,
			pressed:true,
			iconCls:imageUrl,
			scope : scope,
			handler:function(){
				Ext.Ajax.request({
					url :webContext+'/extjs/pageData?method=remove',
					params : {
						dataId : this.dataId,
						model: this.model
					},
					success : function(response, options) {
						window.location= webContext+link;
					},
					failure : function(response, options) {
						Ext.MessageBox.alert("删除失败");
					}
				}, this);
			}
		});
		return button;
	},
	/*删除表单中信息，此方法并不会对数据库进行操作
	*
	*/
	createRemoveByColumn : function(btnName,link,imageUrl,scope){
		var button=new Ext.Button({
			text:btnName,
			pressed:true,
			iconCls:imageUrl,
			scope : scope,
			handler : function() {
				var record = this.grid.getSelectionModel().getSelected();
				var records = this.grid.getSelectionModel().getSelections();
				if (!record) {
					Ext.Msg.alert("提示", "请先选择要删除的行!");
					return;
				}
				if (records.length == 0) {
					Ext.MessageBox.alert('警告', '最少选择一条信息，进行删除!');
				} else {
					Ext.MessageBox.confirm('提示框', '您确定要进行该操作？', function(
					btn) {
						if (btn == 'yes') {
							if (records) {
								for (var i = 0; i < records.length; i++) {
									this.store.remove(records[i]);
									this.removedPriceIds += records[i].get("id")+ ",";
								}
							}
						}
					}, this)
				}
			}
		});
		return button;
	},
	/*删除表单中信息，删除数据库对应信息
	*
	*/
	createRemoveForGrid : function(btnName,link,imageUrl,scope){
		var button=new Ext.Button({
			text:btnName,
			pressed:true,
			iconCls:imageUrl,
			scope : scope,
			handler:function(){
				var record = this.grid.getSelectionModel().getSelected();
				var records = this.grid.getSelectionModel().getSelections();
				if (!record) {
					Ext.Msg.alert("提示", "请先选择要删除的行!");
					return;
				}
				if (records.length == 0) {
					Ext.MessageBox.alert('警告', '最少选择一条信息，进行删除!');
					return;
				}
				var id = new Array();
				var da = new DataAction();
				var firm  =Ext.Msg.confirm('提示框', '您确定要进行删除操作吗?', function(button) {
					if (button == 'yes') {
						for(var i=0;i<records.length;i++){
							id[i] = records[i].data.id;
							Ext.Ajax.request({
								url : webContext+ '/extjs/dataAction?method=remove&clazz='+this.clazz,
								params : {id:id[i]},
								timeout:100000,
								success:function(response){
									var r =Ext.decode(response.responseText);
									if(!r.success){
										Ext.Msg.alert("提示信息","数据删除失败",function(){

										});
									}
									this.store.reload();
								},scope:this

							});
						}
					}
				}, this);
			}
		});
		return button;
	},
	/*查询
	*
	*/
	createSearch : function(btnName,link,imageUrl,scope){
		var button=new Ext.Button({
			text:btnName,
			pressed:true,
			iconCls:imageUrl,
			scope : scope,
			handler:function(){
				var param = this.panel.form.getValues(false);
				param.methodCall = 'query';
                param.start = 1;                       
				this.formValue = param;
				this.pageBar.formValue = this.formValue;
				this.store.removeAll();
				this.store.load({
					params : param
				});
			}
		});
		return button;
	},
	/*重置,用于列表页面重置搜索栏
	*
	*/
	createReset : function(btnName,link,imageUrl,scope){
		var button=new Ext.Button({
			text:btnName,
			pressed:true,
			iconCls:imageUrl,
			scope : scope,
			handler:function(){
				this.panel.form.reset();
			}
		});
		return button;
	},
	/*重置面板中数据
	*
	*/
	createResetForPanel : function(btnName,container,imageUrl,scope){
		var button=new Ext.Button({
			text:btnName,
			pressed:true,
			iconCls:imageUrl,
			scope : scope,
			handler:function(){
				Ext.getCmp(container).form.reset();
			}
		});
		return button;
	},
	/*通过重定向页面来修改数据
	*
	*/
	createModifyByPage : function(btnName,link,imageUrl,scope){
		var button=new Ext.Button({
			text:btnName,
			pressed:true,
			iconCls:imageUrl,
			scope : scope,
			handler:function(){
				var record = this.grid.getSelectionModel().getSelected();
				var records = this.grid.getSelectionModel().getSelections();
				if(!record){
					Ext.Msg.alert("提示","请先选择要修改的行!");
					return;
				}
				if(records.length>1){
					Ext.Msg.alert("提示","修改时只能选择一行!");
					return;
				}
				var modelTableId = this.modelTableName+"$id";
				var dataId = record.get(modelTableId);
				if(link=="")
				alert("未指定跳转链接页面！");
				window.location = webContext+ link + dataId;
			}
		});
		return button;
	},
	/*通过弹出窗体来修改数据
	*
	*/
	createModifyByWindow : function(btnName,link,imageUrl,nextPageModel,scope){
		var button=new Ext.Button({
			text:btnName,
			pressed:true,
			iconCls:imageUrl,
			scope : scope,
			handler:function(){
				var record = this.grid.getSelectionModel().getSelected();
				var records = this.grid.getSelectionModel().getSelections();
				if(!record){
					Ext.Msg.alert("提示","请先选择要修改的行!");
					return;
				}
				if(records.length>1){
					Ext.Msg.alert("提示","修改时只能选择一行!");
					return;
				}
				var modelTableId = this.modelTableName+"$id";
				var dataId = record.get(modelTableId);
				if(link=="")
				alert("未指定跳转链接页面！");
				if(nextPageModel=="")
				alert("未指定跳转窗体页面模板！");
				window.location = webContext+link+"?pageModel="+nextPageModel+"&dataId="+dataId;
			}
		});
		return button;
	},
	/*提交按钮
	*
	*/
	createSubmit : function(btnName,link,imageUrl,scope){
		var button=new Ext.Button({
			text:btnName,
			pressed:true,
			iconCls:imageUrl,
			scope : scope,
			handler:function(){
				Ext.Ajax.request({
					url :webContext+'/extjs/pageData?method=submit',
					params : {
						dataId : this.dataId,
						model: this.model
					},
					success : function(response, options) {
						window.location= webContext+link;
					},
					failure : function(response, options) {
						Ext.MessageBox.alert("提交失败");
					}
				}, this);
			}
		});
		return button;
	},
	/*导入按钮
	*
	*/
	createImport : function(btnName,link,imageUrl,scope){
		var button=new Ext.Button({
			text:btnName,
			pressed:true,
			iconCls:imageUrl,
			scope : scope,
			handler:function(){
				alert("导入暂未实现！");
			}
		});
		return button;
	},
	/*导出按钮
	*
	*/
	createExport : function(btnName,link,imageUrl,scope){
		var button=new Ext.Button({
			text:btnName,
			pressed:true,
			iconCls:imageUrl,
			scope : scope,
			handler:function(){
				alert("导出暂未实现！");
			}
		});
		return button;
	},
	/*上传按钮
	*
	*/
	createUpLoad : function(btnName,link,imageUrl,scope){
		var button=new Ext.Button({
			text:btnName,
			pressed:true,
			iconCls:imageUrl,
			scope : scope,
			handler:function(){
				alert("上传暂未实现！");
			}
		});
		return button;
	},
	/*下载按钮
	*
	*/
	createDownLoad : function(btnName,link,imageUrl,scope){
		var button=new Ext.Button({
			text:btnName,
			pressed:true,
			iconCls:imageUrl,
			scope : scope,
			handler:function(){
				alert("下载暂未实现！");
			}
		});
		return button;
	},
	/*返回按钮
	*
	*/
	createGoBack : function(btnName,link,imageUrl,scope){
		var button=new Ext.Button({
			text:btnName,
			pressed:true,
			iconCls:imageUrl,
			scope : scope,
			handler:function(){
				window.location.href = webContext+link;
			}
		});
		return button;
	},
	/*未知按钮，用于直接调用页面实现的方法
	*method为页面自定义pageEvent事件名
	*必须在页面注册事件才能正常调用此方法
	*/
	createPageEvent : function(btnName,method,link,imageUrl,scope){
		var button=new Ext.Button({
			text:btnName,
			pressed:true,
			iconCls:imageUrl,
			scope : scope,
			handler : function(){
				this.fireEvent(method);
			}
		});
		return button;
	},
	//串行申请服务端数据,不对外
	ajaxGetData:function(url){
		var conn = Ext.lib.Ajax.getConnectionObject().conn;
		conn.open("post",url, false);
		conn.send(null);
		if (conn.status == "200") {
			//alert(conn.responseText);
			var data = eval(conn.responseText);
			return data;
		} else {
			return 'no result';
		}
	},
	ajaxGetpage:function(url){
		var conn = Ext.lib.Ajax.getConnectionObject().conn;
		conn.open("post",url, false);
		conn.send(null);
		if (conn.status == "200") {
			//alert(conn.responseText);
			//注意只有这种格式才能够真正的解析json
			var data = eval('('+conn.responseText+')');
			return data;
		} else {
			return 'no result';
		}
	}
});