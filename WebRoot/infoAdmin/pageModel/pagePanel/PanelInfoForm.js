//ģ����Ϣ��

function PanelInfoForm(){
	
	this.pagePanelName = new Ext.form.TextField({
             xtype: 'textfield',
	         name: 'pagePanelName',
	         blankText: 'Panel���Ʋ���Ϊ��',
	         width:150
	});
		 
//	 var conn = Ext.lib.Ajax.getConnectionObject().conn;
//	 conn.open("POST", webContext+'/servlet/findAllDept', false);
//	 conn.send(null);
//	 alert(conn.responseText);
	 
	 var data = [["50101635","���߿Ƽ���˾"],["50102458","BTC"],["50109938","���ݽ��Ź�˾"]];
	 var store = new Ext.data.SimpleStore({
//	 	        url: webContext+'/servlet/findAllDept',
	 			data: data,
	 	        fields:["id","name"]
	 	        
	 });
	 //jsonStore�в�ȡ
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
				{html: "ģ������:&nbsp;",cls: 'common-text',style:'width:90;text-align:right'},	
				this.templateName,
				{html: "ģ����������:&nbsp;",cls: 'common-text',style:'width:120;text-align:right'},	
				this.department,
				{html: "ģ������Ӧ����(��д�����ļ�·��):&nbsp;",cls: 'common-text',style:'width:210;text-align:right'},	
				this.ruleFile,
				{html: "������:&nbsp;",cls: 'common-text',style:'width:90;text-align:right'},		        		
	    		this.createUser,
	    		{html: "��������:&nbsp;",cls: 'common-text',style:'width:90;text-align:right'},		        		
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
		Ext.MessageBox.alert("��ʾ","ģ�����Ʋ���Ϊ��");
		return false;
	}
	if(department==''){
		Ext.MessageBox.alert("��ʾ","ģ���������Ų���Ϊ��");
		return false;
	}
	if(ruleFile==''){
		Ext.MessageBox.alert("��ʾ","ģ���Ӧ�����ļ�����Ϊ��");
		return false;
	}
	return true;
}
