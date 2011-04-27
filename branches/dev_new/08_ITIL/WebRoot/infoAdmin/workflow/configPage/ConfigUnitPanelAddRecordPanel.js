ConfigUnitAddRecordPanel = Ext.extend(Ext.Panel, {
	
	id : "addmb",
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
		Ext.Ajax.request({
			//����action�д洢�ķ�����												
			url: webContext+'/configUnit_configUnitSave.action',
			params : this.searchForm.searchForms.form.getValues(),
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
			id : "asearch",
			layout : 'table',
			height :200,
			width : 585,
			labelWidth : 150,		
			y : 0,
			anchor : '0 -0',
			frame : true,		
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
	
		this.items =[this.searchForms];
		ConfigUnitAddRecordPanel.superclass.initComponent.call(this);
	}

});