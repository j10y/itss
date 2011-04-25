NodeTypeAndConfigUnitPanel = Ext.extend(Ext.Panel, {
	id : "NodeTypeAndConfigUnitPanel",
	layout : 'table',
	height : 585,
	align : 'center',
	foredit : true,
	frame : true,

	defaults : {
		bodyStyle : 'padding:4px'
	},
	layoutConfig : {
		columns : 1
	},	
	
	//返回方法 
	returned : function(){
		window.location = webContext + '/infoAdmin/workflow/configPage/nodeType.jsp';
	},	
	// 保存方法
	saveRelation : function() {
		var dataForm = Ext.encode(this.matchingForm.mutilyForm.form.getValues(false));
		dataForm = unicode(dataForm);
		
		Ext.Ajax.request({
				url : webContext
						+ '/nodeType_saveNodeTypeAndConfigUnit.action',
				params : {
					dataForm : dataForm,
					id : this.matchingForm.nodeTypeId
				},
				success : function(response, options) {
					location = 'nodeType.jsp'
				},
				failure : function(response, options) {
					Ext.MessageBox.alert("保存失败");
				}
			}, this);
	
	},
	initComponent : function() {
		
		this.nodeTypeId = this.nodeId;		
		var da = new DataAction();
		var url = webContext +'/nodeType_searchAlreadySelectConfigUnit.action?id='+ this.nodeTypeId;
		var fromdata = da.ajaxGetData(url);
		var todata;
		url = webContext +'/nodeType_searchNoChooseSelectConfigUnit.action?id='+ this.nodeTypeId;
		todata = da.ajaxGetData(url);
		this.nodeTypeName = new Ext.form.TextField({
			id : 'rel',
			name : 'nodeTypeName',
			fieldLabel : '节点类型名称',			
			readOnly :true
		});
		
		this.nodeTypeDesc = new Ext.form.TextArea({
			id : 'description',
			name : 'description',
			fieldLabel : '节点类型描述',
			readOnly :true
		});	
		
		this.nodeTypeMark = new Ext.form.TextField({
			id : 'marks',
			name : 'mark',
			fieldLabel : '节点类型标识',			
			readOnly :true
		});	
		
		this.relation =({					
					xtype : "itemselector",
					name : "proxySelect",
					fieldLabel : "",
					dataFields : ["code", "desc"],
					fromData : fromdata,
					toData : todata,
					style : "margin: 0,23,10,13",
					autoScroll : true,
					msHeight : 150,
					frame : true,
					valueField : "code",
					displayField : "desc",
					switchToFrom : true,
					fieldLabel : "发奖排名单位",
					toLegend : "选择的单位",
					fromLegend : "全部单位"
				});

		this.mutilyForm = new Ext.form.FormPanel({
			layout : 'table',
			height : 'auto',
			width : 'auto',
			frame : true,
			defaults : {
				bodyStyle : 'padding:4px'
			},
			frame : true,	
			reader: new Ext.data.JsonReader({//这个的作用就相当于把后台发送的type映射到相应的控件type当中了，
					//读取到之后并且把数据放到record当中，和store的加载是一样的
			    		root: 'list'
			    },[{
		              name: 'nodeTypeName',//是对应的record中的hiddenName
		              mapping: 'nodeTypeName'  //对应的是读取之后的record
		            },{
		              name: 'description',
		              mapping: 'description'
		            },{
//		              name: 'url',
//		              mapping: 'url'
//		            },{
		              name: 'mark',
		              mapping: 'mark'
		            }
	        ]),
			layoutConfig : {
				columns : 4
			},
			items : [
				{html: "&nbsp;节点类型名称:&nbsp;" ,cls: 'common-text', style:'width:120;height:20;text-align:left;margin:5 0 5 0;'},
				this.nodeTypeName,					
				{html: "&nbsp;&nbsp;节点处理方法:" ,cls: 'common-text', style:'width:120;height:20;text-align:left;margin:5 0 5 0;'},
				this.nodeTypeMark,
				{html: "&nbsp;&nbsp;节点描述:" ,cls: 'common-text', style:'width:120;height:20;text-align:left;margin:5 3 5 0;'},
				this.nodeTypeDesc,
				{html: "&nbsp;&nbsp;节点对应配置单元:" ,cls: 'common-text', style:'width:120;height:20;text-align:left;margin:5 3 5 0;'},
				this.relation
				]
		})
//		this.mutilyForm.load({//用来加载form表单的数据
//			 url: webContext+'/nodeType_modifyNodeTypeMessage.action?nodeId='+this.nodeTypeId,
//			 timeout: 3000,
//			 success: function(action,mutilyForm){
//			 	
//			 }	
//		 });
		 var form = this.mutilyForm;
		 var nodeTypeMark = this.nodeTypeMark;
		 var nodeTypeName = this.nodeTypeName;
		 var nodeTypeDesc = this.nodeTypeDesc;
		Ext.Ajax.request({
			url: webContext+'/nodeType_modifyNodeTypeMessage.action?nodeId='+this.nodeTypeId,
			 timeout: 3000,
			 success: function(response,options){
			 	var result = Ext.decode(response.responseText);
			 	if(result.success){
			 		nodeTypeName.setValue(result.list.nodeTypeName);
			 		nodeTypeMark.setValue(result.list.mark);
			 		nodeTypeDesc.setValue(result.list.description);
			 	}
			 }
		});
		this.items = [this.mutilyForm];
		
		NodeTypeAndConfigUnitPanel.superclass.initComponent.call(this);
	}
})
