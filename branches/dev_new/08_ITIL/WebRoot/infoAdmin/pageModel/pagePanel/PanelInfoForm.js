//模板信息表单

function PanelInfoForm(){
	
	this.pagePanelName = new Ext.form.TextField({
             xtype: 'textfield',
	         name: 'pagePanelName',
	         blankText: 'Panel名称不能为空',
	         width:150
	});
		 
//	 var conn = Ext.lib.Ajax.getConnectionObject().conn;
//	 conn.open("POST", webContext+'/servlet/findAllDept', false);
//	 conn.send(null);
//	 alert(conn.responseText);
	 
	 var data = [["50101635","在线科技公司"],["50102458","BTC"],["50109938","神州金信公司"]];
	 var store = new Ext.data.SimpleStore({
//	 	        url: webContext+'/servlet/findAllDept',
	 			data: data,
	 	        fields:["id","name"]
	 	        
	 });
	 //jsonStore中采取
	 this.department =  new Ext.form.ComboBox({
             xtype: 'combo',
             name: 'department',
             store: store,
             valueField: "id",
      		 displayField: "name",
             hiddenName: 'department',
             mode: 'local',
             width:150
	});
	
	this.ruleFile = new Ext.form.TextField({
             xtype: 'textfield',
             name: 'ruleFile',
             width:150
	});
	
	this.createUser =  new Ext.form.TextField({
             xtype: 'textfield',
             name: 'createUser',
             width:150
	});
	
	this.createDate = new Ext.form.DateField({
			xtype: 'datefield',
			name: 'createDate',
			readOnly: true,
			format: 'Y-m-d',
			width: 150
	});
	
	this.form = new Ext.form.FormPanel({
		layout: 'table', 
		height: 30,  
		width: "auto",
		frame: true,
		layoutConfig: {columns: 6},	    		
		items:[
				{html: "模板名称:&nbsp;",cls: 'common-text',style:'width:90;text-align:right'},	
				this.templateName,
				{html: "模板隶属部门:&nbsp;",cls: 'common-text',style:'width:120;text-align:right'},	
				this.department,
				{html: "模板所对应规则(填写规则文件路径):&nbsp;",cls: 'common-text',style:'width:210;text-align:right'},	
				this.ruleFile,
				{html: "创建人:&nbsp;",cls: 'common-text',style:'width:90;text-align:right'},		        		
	    		this.createUser,
	    		{html: "创建日期:&nbsp;",cls: 'common-text',style:'width:90;text-align:right'},		        		
	    		this.createDate
  	     ]
	});
	
}
var panelInfoForm = new PanelInfoForm();
PanelInfoForm.prototype.beforeAdd = function(){
	
	var pagePanelName = this.pagePanelName.getValue();
	var department = this.department.getValue();
	var ruleFile = this.ruleFile.getValue();
	if(pagePanelName==''){
		Ext.MessageBox.alert("提示","模板名称不能为空");
		return false;
	}
	if(department==''){
		Ext.MessageBox.alert("提示","模板隶属部门不能为空");
		return false;
	}
	if(ruleFile==''){
		Ext.MessageBox.alert("提示","模板对应规则文件不能为空");
		return false;
	}
	return true;
}
