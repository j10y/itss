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
	//返回方法 
	returned : function(){
		window.location = webContext + '/infoAdmin/workflow/configPage/configUnit.jsp';
	},	
	// 保存方法
	saveConfigItem : function() {
		alert(this.mofifyForm.unit);
		Ext.Ajax.request({
			//调用action中存储的方法。												
			url: webContext+'/configUnit_configUnitSave.action?configUnitId='+this.mofifyForm.unit,
			params : this.mofifyForm.searchForms.form.getValues(),
			method : 'post',			
			//存储成功后调用的函数。
			success : function(response) {
				var result = Ext.decode(response.responseText);					
				if(result.failure){
					Ext.MessageBox.alert("提示信息：", "保存数据失败，原因是您输入的配置项关系名称已存在");		
					this.searchForms.form.reset();
				}else{
					 Ext.Msg.alert("提示信息","保存数据成功!",function(){                                 	
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
			fieldLabel : '配置单元名称'			
			
		});
		
		this.configUnitDesc = new Ext.form.TextArea({	
			xtype:"textfield",
			name : 'description',
			fieldLabel : '配置单元描述'
		});		
		
		this.configUnitUrl = new Ext.form.TextField({	
			xtype:"textfield",
			name : 'url',
			width : 200,
			fieldLabel : '配置单元链接'			
			
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
			reader: new Ext.data.JsonReader({//这个的作用就相当于把后台发送的type映射到相应的控件type当中了，
					//读取到之后并且把数据放到record当中，和store的加载是一样的
			    		root: 'list',
		                successProperty: '@success'
			    },[{
		              name: 'configUnitname',//是对应的record中的hiddenName
		              mapping: 'configUnitname'  //对应的是读取之后的record
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
				{html: "&nbsp;配置单元名称:&nbsp;" ,cls: 'common-text', style:'width:120;height:20;text-align:left;margin:5 0 5 0;'},
				this.configUnitName,					
				{html: "&nbsp;&nbsp;配置单元链接:" ,cls: 'common-text', style:'width:120;height:20;text-align:left;margin:5 0 5 0;'},
				this.configUnitUrl,
				{html: "&nbsp;&nbsp;配置单元描述:" ,cls: 'common-text', style:'width:120;height:20;text-align:left;margin:5 3 5 0;'},
				this.configUnitDesc
				]
		});
		this.searchForms.load({//用来加载form表单的数据
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