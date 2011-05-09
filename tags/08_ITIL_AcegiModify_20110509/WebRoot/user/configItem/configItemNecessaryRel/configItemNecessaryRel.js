PageTemplates = Ext.extend(Ext.FormPanel, {
	id : 'configItemNecessaryRelPanel',
	layout : 'table',
	frame : true,
	defaults : {
		bodyStyle : 'padding:4px'
	},
	layoutConfig : {
		columns : 4
	},
	buttonAlign:"center",
	listeners:{
		render:function(panel){
			panel.insert(4,{html:"<span style=font-weight:bold >���ù�ϵ��Ϣ��ȱʡֵ��</span>",cls: 'common-text',style:'margin:5 0 5 25;text-align:left;',colspan:4});
		}
	},
	buttons:[{
		text:'����',
		iconCls : 'save',
		handler:function(){
		var button=this;
		if(!Ext.getCmp('configItemNecessaryRelPanel').form.isValid()){
			Ext.MessageBox.alert("��ʾ",'����д����Ϣ����������ȷ.');
			return;
		}
/*		if(Ext.getCmp('ConfigItemNecessaryRel$configItemTypeCombo').getValue()==Ext.getCmp('ConfigItemNecessaryRel$otherConfigItemTypeCombo').getValue()){
			Ext.MessageBox.alert("��ʾ",'���������ͺ͹������������Ͳ�����ͬ.');
			return;
		}*/
		button.disable();
			var configItemNecessaryRelPanel=Ext.encode(getFormParam('configItemNecessaryRelPanel'));
			Ext.Ajax.request({
				url:webContext+"/configItemAction_saveConfigItemNecessaryRel.action",
				params:{
					configItemNecessaryRelPanel:configItemNecessaryRelPanel,
					id:Ext.getCmp("ConfigItemNecessaryRel$id").getValue()
				},
				success:function(response){
					var responseText=response.responseText;
					if(responseText.trim()!=""){
						responseText=Ext.decode(responseText);
						if(responseText.exist!=undefined){
							Ext.Msg.alert("��ʾ��",'�Ѵ��ڴ˱�Ҫ��ϵ��',function(){
								button.enable();
							});
						}
						if(responseText.reverse!=undefined){
							var otherConfigItemType=Ext.getCmp('ConfigItemNecessaryRel$otherConfigItemTypeCombo').getRawValue()
							var configItemType=Ext.getCmp('ConfigItemNecessaryRel$configItemTypeCombo').getRawValue()
							var parentOrChildType=Ext.getCmp('ConfigItemNecessaryRel$parentOrChildTypeCombo').getRawValue()
							Ext.Msg.alert("��ʾ��",'�Ѵ���"'+otherConfigItemType+"-->"+configItemType+'",��ϵ����Ϊ:"'+parentOrChildType+'"�ı�Ҫ��ϵ��',function(){
								button.enable();
							});
						}
					}else{
						Ext.Msg.alert("��ʾ��",'����ɹ���',function(){
							window.parent.Ext.getCmp('gridPanel').getStore().reload();
							window.parent.Ext.getCmp('configItemNecessaryRelWin').close();
						});
					}
				},
				failure:function(){
					Ext.Msg.alert("��ʾ��",'ϵͳ�쳣��');
					button.enable();
				}
				
			})
		}
	}
	],	
	split : function(data) {
		var labellen = 100;
		var itemlen = 150;
		var throulen = 400;
		if (Ext.isIE) {
			throulen = 400;
		} else {
			throulen = 400;
		}
		if (data == null || data.length == 0) {
			return null;
		}
		var hid = 0;
		var hidd = new Array();
		var longData = new Array();
		var longitems = 0;
		for (i = 0; i < data.length; i++) {
			data[i].style = data[i].style == null ? "" : data[i].style;
			if (data[i].xtype == "textarea") {
				data[i].style += "'margin:5 0 5 0;'";
			}
			if (data[i].xtype == "hidden") {
				hidd[hid] = data[i];
				hid++;
				continue;
			}
			if (data[i].xtype == "combo") {
				data[i].resizable = true;
			}
			if (data[i].width == null || data[i].width == 'null'
					|| data[i].width == "") {
				data[i].style += "width:" + itemlen + "px";
				data[i].width = itemlen;
			} else {
				if (data[i].width == "9999") {//ͨ��  
					if ((i - hid + longitems) % 2 == 1) {//���Ҳ�����ǰһ����ͨ                                             
						longData[2 * (i - hid) - 1].colspan = 3;
					} else {//��ռһ��
						longitems++;
					}
					data[i].colspan = 3;//������ͨ                                            
					data[i].width = throulen;
					if (data[i].xtype == "textarea") {
						data[i].height = 50;
					}
					data[i].style += "width:" + throulen + "px;";
				} else {//�������ȣ����� 
					data[i].style += "width:" + itemlen + "px";
					data[i].width = itemlen;
				}
			}
			longData[2 * (i - hid)] = {
				html : data[i].fieldLabel + ":",
				cls : 'common-text',
				style : 'width:' + labellen + ';text-align:right'
			};
			longData[2 * (i - hid) + 1] = data[i];
		}
		for (i = 0; i < hidd.length; i++) {
			longData[longData.length] = hidd[i];
		}
		return longData;
	},
 getconfigItemNecessaryRelPanel: function(dataId) {
	 var da = new DataAction();
	 if(dataId!="")
	 	var data = da.getSingleFormPanelElementsForEdit("panel_ConfigItemNecessaryRel", dataId);
	 else
		var data = da.getPanelElementsForAdd("panel_ConfigItemNecessaryRel");
	for(var i=0;i<data.length;i++){
		if(data[i].id=="ConfigItemNecessaryRel$attachQuotiety"){
			data[i]=new Ext.form.NumberField({
				id:data[i].id,
				name:data[i].name,
				value:data[i].value,
				height:data[i].height,
				width:data[i].width,
				allowBlank:data[i].allowBlank,
				fieldLabel:data[i].fieldLabel
			});
		}
		if(data[i].id=='ConfigItemNecessaryRel$configItemTypeCombo'){
			data[i].store.baseParams={
				deployFlag:1
			}
		}
		if(data[i].id=='ConfigItemNecessaryRel$otherConfigItemTypeCombo'){
			data[i].store.baseParams={
				deployFlag:1
			}
		}
		if(data[i].id=="ConfigItemNecessaryRel$parentOrChildTypeCombo"){
				data[i].store=new Ext.data.SimpleStore({
					fields:['id','name'],
					data:[[1,"��-->��"],[2,"��-->��"]]});
				if(dataId==""){
					data[i].value=1;
				}
		}
		if(data[i].id=="ConfigItemNecessaryRel$description"){
			data[i].width=9999;		
		}
		if(data[i].id=="ConfigItemNecessaryRel$configItemTypeCombo"&&dataId!=""){
			data[i].readOnly=true;
			data[i].hideTrigger=true;
			data[i].emptyText="";
		}
		if(data[i].id=="ConfigItemNecessaryRel$otherConfigItemTypeCombo"&&dataId!=""){
			data[i].readOnly=true;
			data[i].hideTrigger=true;
			data[i].emptyText="";
		}
		if(data[i].id=="ConfigItemNecessaryRel$isOptionalCombo"&&dataId==""){
			data[i].value=0;
		}
		if(data[i].id=="ConfigItemNecessaryRel$relationShipGradeCombo"&&dataId==""){
			data[i].value=2;
			data[i].initComponent();
		}
		if(data[i].id=="ConfigItemNecessaryRel$btechnoInfoAllowBlankCombo"&&dataId==""){
			data[i].value=1;
		}
		if(data[i].id=="ConfigItemNecessaryRel$atechnoInfoAllowBlankCombo"&&dataId==""){
			data[i].value=1;
		}
		if(data[i].id=="ConfigItemNecessaryRel$relationShipTypeCombo"&&dataId==""){
			data[i].value=6;
			data[i].initComponent();
		}
		if(data[i].id=="ConfigItemNecessaryRel$attachQuotiety"&&dataId==""){
			data[i].value=1;
		}
	}
	var biddata = this.split(data);
	 return biddata;
	},
    items : this.items,
	initComponent : function() {
		this.items = this.getconfigItemNecessaryRelPanel(this.dataId);
		PageTemplates.superclass.initComponent.call(this);
	}
})