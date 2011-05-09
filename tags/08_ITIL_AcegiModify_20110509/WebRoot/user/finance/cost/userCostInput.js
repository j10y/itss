UserCostInputPanel = Ext.extend(Ext.Panel, {
	id : "userCostInputPanel",
	height : 'auto',
	width : 'auto',
	layout : 'fit',
	frame : true,
	items : this.items,
	getMainForm : function() {
		var searchForm = new Ext.form.FormPanel({
			title : '��Ա�ɱ�¼��',
			id : "userCostInputPanelFormPanel",
			collapsible : true,
			frame : true,
			autoScroll : true,
			layout : "table",
			layoutConfig : {
				columns : 1
			},
			items : [{
				xtype : 'fieldset',
				layout : 'table',
				align:'center',
				layoutConfig : {
					columns : 4
				},
				defaults : {
					bodyStyle : 'padding:16px 0px 8px 0px'
				},
				items : [{
							html : "�ɱ��������&nbsp:&nbsp",
							cls : 'common-text',
							style : 'width:150;text-align:right'
						}, {
							xtype : 'textfield',
							id : "costApportionType",
							fieldLabel : '�ɱ��������',
							value:'������',
							allowBlank : false,
							readOnly:true,
							width : 200
						}, {
							html : "������&nbsp:&nbsp",
							cls : 'common-text',
							style : 'width:150;text-align:right'
						}, {
							xtype : 'textfield',
							id : "configItemType",
							fieldLabel : '������',
							value:'��Ϣ������',
							allowBlank : false,
							readOnly:true,
							width : 200
						}, {
							html : "��������&nbsp:&nbsp",
							cls : 'common-text',
							style : 'width:150;text-align:right'
						}, {
							xtype : 'textfield',
							id : "financeCostType",
							fieldLabel : '������',
							value:'�˹��ܰ�����/A004050100',
							allowBlank : false,
							readOnly:true,
							height : 0,
							width : 200
						}, {
							html : "�ɱ��������&nbsp<font color='red'>*</font>:&nbsp",
							cls : 'common-text',
							style : 'width:150;text-align:right'
						}, {
							id : "costAmount",
							xtype : 'textfield',
							fieldLabel : '�ɱ��������',
							allowBlank : false,
							listeners : {
								'blur' : function(obj, e) {
									var money = Ext.getCmp("costAmount").getValue();
									if (!/^(\d*)(\,|\d|\.)*$/.test(money)
											|| money == "") {
										alert("��������Ч��ֵ");
										return;
									}
								}
							},
							height : 0,
							width : 200
						}, {
							html : "�ɱ�����&nbsp:&nbsp",
							cls : 'common-text',
							style : 'width:150;text-align:right'
						}, {
							xtype : 'combo',
							id : "costCenterId",
							hiddenName : "costCenter",
							fieldLabel : '�ɱ�����',
							defaultParam : "",
							pageSize : 10,
							allowBlank : false,
							displayField : 'costCenter',
							valueField : "id",
							triggerAction : "all",
							value:'1',
							width : 200,
							store : new Ext.data.JsonStore({
								url : webContext + '/userCostInput_findCostCenter.action',
								fields : ['id', 'costCenter'],
								totalProperty : 'rowCount',
								root : 'data',
								listeners : {
									beforeload : function(store, opt) {
										var param = Ext	.getCmp('costCenterId').defaultParam;
										if (opt.params['propertyValue'] == undefined) {
											opt.params['propertyValue'] = unicode(param);
										}
									}
								},
								id : 'id'
							}),
							listeners : {
								'beforequery' : function(queryEvent) {
									
									var param = queryEvent.query;
									this.defaultParam = param;
									param = unicode(param);
									this.store.load({
												params : {
													propertyValue : param,
													start : 0
												}
											});
									return true;
								}
							},
					comboInitComponent : function() {
						if(this.getValue()!=''){
							var combo=this;
							this.store.load({
								params : {
									id : this.getValue(),
									start : 0
								},
								callback : function(r, options, success) {
									if(r.length>0){
										combo.setRawValue(r[0].get(combo.displayField));
									}
								}
							});
						}
					}
						}, {
							html : "�������&nbsp:&nbsp",
							cls : 'common-text',
							style : 'width:150;text-align:right'
						}, {
							xtype : 'textfield',
							id : "borrowType",
							fieldLabel : '�������',
							value:'ֱ�ӱ���',
							allowBlank : false,
							readOnly:true,
							height : 0,
							width : 200
						}, {
							html : "���÷�������:&nbsp;",
							cls : 'common-text',
							style : 'width:150;text-align:right'
						}, {
							xtype : 'datefield',
							fieldLabel : '���÷�������',
							id : "costDate",
							format : "Y-m-d",
							value: this.getCurrentDate(),
							height : 0,
							width : 200
						}, {
							html : "�ɱ�������Դ&nbsp:&nbsp",
							cls : 'common-text',
							style : 'width:150;text-align:right'
						}, {
							xtype : 'textfield',
							id : "costDataResource",
							fieldLabel : '�ɱ�������Դ',
							value:'ϵͳ����',
							allowBlank : false,
							readOnly:true,
							height : 0,
							width : 200
						}]
			}],
			buttons : [{
						text : "����",
						pressed : true,
						iconCls : 'save',
						handler : this.formSave
					  }],
			buttonAlign : 'center'
		});
		return searchForm;
	},
	
	formSave:function(){
		if(validateMethods()){
			Ext.Ajax.request({
				url:webContext+'/userCostInput_saveUserCostMesg.action',
				params:{
					formParams:Ext.util.JSON.encode(Ext.getCmp("userCostInputPanelFormPanel").getForm().getValues()),
					costCenter:Ext.getCmp("costCenterId").getRawValue()
				},
				success : function(action, form) {
						Ext.MessageBox.alert("��ʾ��Ϣ��", "����ɹ�");
				},
				failure : function(response, options) {
					Ext.MessageBox.alert("��ʾ��Ϣ��", "�ύ����ʧ��");
				}
			});
		};
		
	},
	
	/**
	 * �õ��ɱ��������ڣ�Ĭ���ϸ���26��
	 * @return {}
	 */
	getCurrentDate:function(){
		var currentDate = new Date();
		currentDate.setDate(26);
		currentDate.setMonth(currentDate.getMonth()-1);
		return currentDate;
	},

	initComponent : function() {
		this.searchForm = this.getMainForm();
		this.items = [this.searchForm];
		UserCostInputPanel.superclass.initComponent.call(this);
		Ext.getCmp("costCenterId").comboInitComponent();
	}
	
});
function validateMethods(){
		var money = Ext.getCmp("costAmount").getValue();
		if(money==''){
			alert("�ɱ��������Ϊ������");
			return false;
		}else{
			return true;
		}
}
Ext.onReady(function() {
			Ext.QuickTips.init();
			var userCostInputPanel = new UserCostInputPanel();
			userCostInputPanel.render("userCostInputDiv");
			new Ext.Viewport({
						layout : 'fit',
						items : [userCostInputPanel]
					});
		});
