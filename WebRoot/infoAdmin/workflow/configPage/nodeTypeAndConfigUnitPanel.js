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
	
	//���ط��� 
	returned : function(){
		window.location = webContext + '/infoAdmin/workflow/configPage/nodeType.jsp';
	},	
	// ���淽��
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
					Ext.MessageBox.alert("����ʧ��");
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
			fieldLabel : '�ڵ���������',			
			readOnly :true
		});
		
		this.nodeTypeDesc = new Ext.form.TextArea({
			id : 'description',
			name : 'description',
			fieldLabel : '�ڵ���������',
			readOnly :true
		});	
		
		this.nodeTypeMark = new Ext.form.TextField({
			id : 'marks',
			name : 'mark',
			fieldLabel : '�ڵ����ͱ�ʶ',			
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
					fieldLabel : "����������λ",
					toLegend : "ѡ��ĵ�λ",
					fromLegend : "ȫ����λ"
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
			reader: new Ext.data.JsonReader({//��������þ��൱�ڰѺ�̨���͵�typeӳ�䵽��Ӧ�Ŀؼ�type�����ˣ�
					//��ȡ��֮���Ұ����ݷŵ�record���У���store�ļ�����һ����
			    		root: 'list'
			    },[{
		              name: 'nodeTypeName',//�Ƕ�Ӧ��record�е�hiddenName
		              mapping: 'nodeTypeName'  //��Ӧ���Ƕ�ȡ֮���record
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
				{html: "&nbsp;�ڵ���������:&nbsp;" ,cls: 'common-text', style:'width:120;height:20;text-align:left;margin:5 0 5 0;'},
				this.nodeTypeName,					
				{html: "&nbsp;&nbsp;�ڵ㴦����:" ,cls: 'common-text', style:'width:120;height:20;text-align:left;margin:5 0 5 0;'},
				this.nodeTypeMark,
				{html: "&nbsp;&nbsp;�ڵ�����:" ,cls: 'common-text', style:'width:120;height:20;text-align:left;margin:5 3 5 0;'},
				this.nodeTypeDesc,
				{html: "&nbsp;&nbsp;�ڵ��Ӧ���õ�Ԫ:" ,cls: 'common-text', style:'width:120;height:20;text-align:left;margin:5 3 5 0;'},
				this.relation
				]
		})
//		this.mutilyForm.load({//��������form��������
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
