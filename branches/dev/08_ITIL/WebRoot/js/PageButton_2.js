//��ť������
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
			var btnName=buttonStrs[i].btnName;//��ť������
			var container=buttonStrs[i].container;//��ť����������ģ����������
			var method=buttonStrs[i].method;//��ť��Ӧ����
			var link=buttonStrs[i].link;//��ťָ������
			var nextPageModel=buttonStrs[i].nextPageModel;//��ťָ����һ��pageModel
			var imageUrl=buttonStrs[i].imageUrl;//��ťͼ��
			//����Button�����ݲ�ͬ�Ķ������찴ť
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
			}else if(method=="search"){//��ѯ
				barbuttons[i]=this.createSearch(btnName,link,imageUrl,scope);
			}else if(method=="reset"){
				barbuttons[i]=this.createReset(btnName,link,imageUrl,scope);
			}else if(method=="resetPanel"){//���õ����
				barbuttons[i]=this.createResetForPanel(btnName,container,imageUrl,scope);
			}else if(method=="modifyByPage"){//ͨ���ض���ҳ������޸�
				var linkPath=link;
				barbuttons[i]=this.createModifyByPage(btnName,link,imageUrl,scope);
				scope.modify=function(){//Ϊҳ��˫���¼�������ֵ
					var record = this.grid.getSelectionModel().getSelected();
					var records = this.grid.getSelectionModel().getSelections();
					if(!record){
						Ext.Msg.alert("��ʾ","����ѡ��Ҫ�޸ĵ���!");
						return;
					}
					if(records.length>1){
						Ext.Msg.alert("��ʾ","�޸�ʱֻ��ѡ��һ��!");
						return;
					}
					var modelTableId = this.modelTableName+"$id";
					var dataId = record.get(modelTableId);
					window.location = webContext+ linkPath + dataId;
				}
			}else if(method=="modifyByWindow"){//ͨ��������������޸�
				barbuttons[i]=this.createModifyByPage(btnName,link,imageUrl,nextPageModel,scope);
				scope.modify=function(){
					alert("ͨ��������������޸���δʵ�֣�");
				}
			}else if(method=="submit"){//�ύ
				barbuttons[i]=this.createSubmit(btnName,link,imageUrl,scope);
			}else if(method=="import"){//����
				barbuttons[i]=this.createImport(btnName,link,imageUrl,scope);
			}else if(method=="export"){//����
				barbuttons[i]=this.createExport(btnName,link,imageUrl,scope);
			}else if(method=="upload"){//�ϴ�
				barbuttons[i]=this.createUpLoad(btnName,link,imageUrl,scope);
			}else if(method=="download"){//����
				barbuttons[i]=this.createDownLoad(btnName,link,imageUrl,scope);
			}else if(method=="goBack"){//����
				barbuttons[i]=this.createGoBack(btnName,link,imageUrl,scope);
			}else{//
				barbuttons[i]=this.createPageEvent(btnName,method,link,imageUrl,scope);
			}
		}
		return barbuttons;
	},
	/**ͨ���ض������ҳ�������
	*linkָ���ض���ҳ��
	*/
	createAddByPage : function(btnName,link,imageUrl,scope){
		var button=new Ext.Button({
			text:btnName,
			pressed:true,
			iconCls:imageUrl,
			scope : scope,
			handler:function(){
				if(link=="")
				alert("δָ�������ת����ҳ�棡");
				window.location = webContext+link;
			}
		});
		return button;
	},
	/*ͨ���������������
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
				alert("δָ�������ת����ҳ�棡");
				if(nextPageModel=="")
				alert("δָ�������ת����ҳ��ģ�壡");
				window.location = webContext+link+"?pageModel="+nextPageModel;
			}
		});
		return button;
	},
	/*ͨ���ڱ�������һ�����ݵ���ʽ���,���������ݿ���в���
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
	/*��������ҳ��������Ϣ
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
						Ext.MessageBox.alert("����ɹ�");
						//window.location = webContext + link;
					},
					failure : function(response, options) {
						Ext.MessageBox.alert("����ʧ��");
					}
				}, this);
			}
		});
		return button;
	},
	/*���浥����ҳ����Ϣ
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
						Ext.MessageBox.alert("����ɹ�");
					},
					failure : function(response, options) {
						Ext.MessageBox.alert("����ʧ��");
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
						Ext.MessageBox.alert("����ɹ�");
						//window.location = webContext + link;
					},
					failure : function(response, options) {
						Ext.MessageBox.alert("����ʧ��");
					}
				}, this);
			}
		});
		return button;
	},
	/*���浥����ҳ����Ϣ
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
						Ext.MessageBox.alert("����ɹ�");
					},
					failure : function(response, options) {
						Ext.MessageBox.alert("����ʧ��");
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
						Ext.MessageBox.alert("����ɹ�");
					},
					failure : function(response, options) {
						Ext.MessageBox.alert("����ʧ��");
					}
				}, this);
			}
		});
		return button;
	},
	
	
	/******************************
	* ������������Ϣ��ҳ������������ݵķ���
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
						Ext.MessageBox.alert("����ʧ��");
					}
				}, this);
			}
		});
		return button;
	},
	/*����
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
						Ext.MessageBox.alert("����ʧ��");
					}
				}, this);
			}
		});
		return button;
	},
	//end ***********************************
	
	/*ɾ������ҳ��������Ϣ
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
						Ext.MessageBox.alert("ɾ��ʧ��");
					}
				}, this);
			}
		});
		return button;
	},
	/*ɾ��������Ϣ���˷�������������ݿ���в���
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
					Ext.Msg.alert("��ʾ", "����ѡ��Ҫɾ������!");
					return;
				}
				if (records.length == 0) {
					Ext.MessageBox.alert('����', '����ѡ��һ����Ϣ������ɾ��!');
				} else {
					Ext.MessageBox.confirm('��ʾ��', '��ȷ��Ҫ���иò�����', function(
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
	/*ɾ��������Ϣ��ɾ�����ݿ��Ӧ��Ϣ
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
					Ext.Msg.alert("��ʾ", "����ѡ��Ҫɾ������!");
					return;
				}
				if (records.length == 0) {
					Ext.MessageBox.alert('����', '����ѡ��һ����Ϣ������ɾ��!');
					return;
				}
				var id = new Array();
				var da = new DataAction();
				var firm  =Ext.Msg.confirm('��ʾ��', '��ȷ��Ҫ����ɾ��������?', function(button) {
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
										Ext.Msg.alert("��ʾ��Ϣ","����ɾ��ʧ��",function(){

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
	/*��ѯ
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
	/*����,�����б�ҳ������������
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
	/*�������������
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
	/*ͨ���ض���ҳ�����޸�����
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
					Ext.Msg.alert("��ʾ","����ѡ��Ҫ�޸ĵ���!");
					return;
				}
				if(records.length>1){
					Ext.Msg.alert("��ʾ","�޸�ʱֻ��ѡ��һ��!");
					return;
				}
				var modelTableId = this.modelTableName+"$id";
				var dataId = record.get(modelTableId);
				if(link=="")
				alert("δָ����ת����ҳ�棡");
				window.location = webContext+ link + dataId;
			}
		});
		return button;
	},
	/*ͨ�������������޸�����
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
					Ext.Msg.alert("��ʾ","����ѡ��Ҫ�޸ĵ���!");
					return;
				}
				if(records.length>1){
					Ext.Msg.alert("��ʾ","�޸�ʱֻ��ѡ��һ��!");
					return;
				}
				var modelTableId = this.modelTableName+"$id";
				var dataId = record.get(modelTableId);
				if(link=="")
				alert("δָ����ת����ҳ�棡");
				if(nextPageModel=="")
				alert("δָ����ת����ҳ��ģ�壡");
				window.location = webContext+link+"?pageModel="+nextPageModel+"&dataId="+dataId;
			}
		});
		return button;
	},
	/*�ύ��ť
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
						Ext.MessageBox.alert("�ύʧ��");
					}
				}, this);
			}
		});
		return button;
	},
	/*���밴ť
	*
	*/
	createImport : function(btnName,link,imageUrl,scope){
		var button=new Ext.Button({
			text:btnName,
			pressed:true,
			iconCls:imageUrl,
			scope : scope,
			handler:function(){
				alert("������δʵ�֣�");
			}
		});
		return button;
	},
	/*������ť
	*
	*/
	createExport : function(btnName,link,imageUrl,scope){
		var button=new Ext.Button({
			text:btnName,
			pressed:true,
			iconCls:imageUrl,
			scope : scope,
			handler:function(){
				alert("������δʵ�֣�");
			}
		});
		return button;
	},
	/*�ϴ���ť
	*
	*/
	createUpLoad : function(btnName,link,imageUrl,scope){
		var button=new Ext.Button({
			text:btnName,
			pressed:true,
			iconCls:imageUrl,
			scope : scope,
			handler:function(){
				alert("�ϴ���δʵ�֣�");
			}
		});
		return button;
	},
	/*���ذ�ť
	*
	*/
	createDownLoad : function(btnName,link,imageUrl,scope){
		var button=new Ext.Button({
			text:btnName,
			pressed:true,
			iconCls:imageUrl,
			scope : scope,
			handler:function(){
				alert("������δʵ�֣�");
			}
		});
		return button;
	},
	/*���ذ�ť
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
	/*δ֪��ť������ֱ�ӵ���ҳ��ʵ�ֵķ���
	*methodΪҳ���Զ���pageEvent�¼���
	*������ҳ��ע���¼������������ô˷���
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
	//����������������,������
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
			//ע��ֻ�����ָ�ʽ���ܹ������Ľ���json
			var data = eval('('+conn.responseText+')');
			return data;
		} else {
			return 'no result';
		}
	}
});