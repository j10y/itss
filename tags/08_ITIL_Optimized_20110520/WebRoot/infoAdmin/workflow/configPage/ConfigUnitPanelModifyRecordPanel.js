ConfigUnitModifyRecordPanel = Ext.extend(Ext.Panel, {
	
	id : "mb",
	header : false,
	closable : true,
	layout : 'fit',
	autoScroll : true,
	border : false,
	layoutConfig : {
		columns : 1
	}, 	
	//���ط��� 
	returned : function(){
		window.location = webContext + '/infoAdmin/workflow/configPage/configUnit.jsp';
	},	
	// ���淽��
	saveConfigItem : function() {
		alert(this.mofifyForm.unit);
		Ext.Ajax.request({
			//����action�д洢�ķ�����												
			url: webContext+'/configUnit_configUnitSave.action?configUnitId='+this.mofifyForm.unit,
			params : this.mofifyForm.searchForms.form.getValues(),
			method : 'post',			
			//�洢�ɹ�����õĺ�����
			success : function(response) {
				var result = Ext.decode(response.responseText);					
				if(result.failure){
					Ext.MessageBox.alert("��ʾ��Ϣ��", "��������ʧ�ܣ�ԭ������������������ϵ�����Ѵ���");		
					this.searchForms.form.reset();
				}else{
					 Ext.Msg.alert("��ʾ��Ϣ","�������ݳɹ�!",function(){                                 	
                    	window.location = webContext + '/infoAdmin/workflow/configPage/configUnit.jsp';
                    },this);				 					 		
				}																					
			},
            scope:this
		});			
	},
	
	initComponent : function() { 		
		this.unit = this.configUnit;
		this.itemName = "";	
		this.configUnitName = new Ext.form.TextField({
			xtype:"textfield",
    		name : 'configUnitname',
			fieldLabel : '���õ�Ԫ����'			
			
		});
		
		this.configUnitDesc = new Ext.form.TextArea({	
			xtype:"textfield",
			name : 'description',
			fieldLabel : '���õ�Ԫ����'
		});		
		
		this.configUnitUrl = new Ext.form.TextField({	
			xtype:"textfield",
			name : 'url',
			width : 200,
			fieldLabel : '���õ�Ԫ����'			
			
		});		
		this.searchForms = new Ext.form.FormPanel({
			id : "search",
			layout : 'table',
			height :200,
			width : 585,
			labelWidth : 150,		
			y : 0,
			anchor : '0 -0',
			frame : true,	
			reader: new Ext.data.JsonReader({//��������þ��൱�ڰѺ�̨���͵�typeӳ�䵽��Ӧ�Ŀؼ�type�����ˣ�
					//��ȡ��֮���Ұ����ݷŵ�record���У���store�ļ�����һ����
			    		root: 'list',
		                successProperty: '@success'
			    },[{
		              name: 'configUnitname',//�Ƕ�Ӧ��record�е�hiddenName
		              mapping: 'configUnitname'  //��Ӧ���Ƕ�ȡ֮���record
		            },{
		              name: 'description',
		              mapping: 'description'
		            },{
		              name: 'url',
		              mapping: 'url'
		            }
	        ]),
			layoutConfig : {columns: 4},
			items : [
				{html: "&nbsp;���õ�Ԫ����:&nbsp;" ,cls: 'common-text', style:'width:120;height:20;text-align:left;margin:5 0 5 0;'},
				this.configUnitName,					
				{html: "&nbsp;&nbsp;���õ�Ԫ����:" ,cls: 'common-text', style:'width:120;height:20;text-align:left;margin:5 0 5 0;'},
				this.configUnitUrl,
				{html: "&nbsp;&nbsp;���õ�Ԫ����:" ,cls: 'common-text', style:'width:120;height:20;text-align:left;margin:5 3 5 0;'},
				this.configUnitDesc
				]
		});
		this.searchForms.load({//��������form��������
			 url: webContext+'/configUnit_findConfigUnitFormValue.action?configUnitId='+this.unit,
			 timeout: 3000,
			 success: function(action,editorForm){
			 	
			 /*	configUnitHandlerStore.load({							 		
			 		callback: function(r, options, success){							 			
			 			var value = searchForms.form.findField('unitHandlerId').getValue();								 			
			 			searchForms.form.findField('unitHandlerId').setValue(value)
			 		}
			 	});				*/ 	
			 								 	
			 }	
		 });
		
		this.items =[this.searchForms];
		ConfigUnitAddRecordPanel.superclass.initComponent.call(this);
	}

});