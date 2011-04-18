ConfigUnitTimerPanel = Ext.extend(Ext.Panel, {	
	id : "ConfigUnitTimerPanel",	
	closable : true,
	animate: true,
	viewConfig : {//����Ӧ���
		autoFill : true,
		forceFit : true
	},
	layout : 'absolute',	
	
	addTimerClass : function() {		
		var record = Ext.getCmp("tgrid").getSelectionModel().getSelected();				
		if (!record) {
			Ext.Msg.alert("��ʾ", "����ѡ��Ҫ���õĶ�ʱ��!");
			return;
		}
		var timerId = record.get("id");
		var writeInfo = new Ext.form.FormPanel({
			id : 'com.digitalchina.writeInfo',
			height : 220,
			frame : true,
			width : 520,
			labelWidth : 130,
			labelAlign : "right",
			defaultType : "field",
			buttonAlign : 'left',
			fileUpload : true,
			items : [{
				id : 'TimerField',
				inputType : 'file',
				width : 340,
				height : 30,
				name : 'TimerFieldName',
				fieldLabel : "�˽ڵ��漰�ĵ����ļ�"
			}]
		});
		
		var win = new Ext.Window({
			id:'window',
			title : '���ӽڵ������Ϣ',
			width : 500,
			height : 220,
			modal : true,
			buttons : [{
				xtype : 'button',
				text : "����",
				handler : function() {
					Ext.getCmp('com.digitalchina.writeInfo').getForm().submit({
						url : webContext+ '/configUnit/timerClass.do?methodCall=uploadTimerClass&timerId='+timerId,
						method : 'post',
						failure : function(form1, action) {
							Ext.MessageBox.alert("����ʧ��");
						},
						success : function(form1, action) {
							Ext.Msg.alert("��ʾ", "����ɹ�", function(button) {								
								win.close();
							}, this);
						}
					})
				}
			}, {
				xtype : 'button',
				handler : function() {
					win.close();
				},
				text : '�ر�',
				scope : this
			}],
			items : [writeInfo]
		});
		win.show();		
	},
	// ��ʼ��
	initComponent : function() {//�������涨��ı�����ȫ�ֱ���
		
		this.processName = '';
		this.nodeName = '';
		this.csm = new Ext.grid.CheckboxSelectionModel();		
		this.store = new Ext.data.JsonStore({ 				
				url: webContext+ '/configUnit_showNodeTimer.action',
				fields: ['id','defName','nodeName','frequently','inverse','isListener'],
			    root:'data',
				sortInfo: {field: "id", direction: "ASC"}
		}); 
		this.comboStore = new Ext.data.JsonStore({ 				
				url: webContext+ '/configUnit_showNodeTranstion.action?virtualDefinitionInfoId='+virtualDefinitionInfoId+'&nodeId='+nodeId,
				fields: ['id','defName'],
			    root:'data',
				sortInfo: {field: "id", direction: "ASC"}
		}); 
		var isListener=new Ext.grid.CheckColumn({
       		header: "�Ƿ����ü���",
       		dataIndex: 'isListener'       		
    	});
		this.cm = new Ext.grid.ColumnModel([this.csm,{
	                    header: "��������",
	                    sortable: true,
	                    dataIndex: "defName"	                             
                	},{
	                    header: "�ڵ�����",
	                    sortable: true,
	                    dataIndex: "nodeName"	                              
                	}
                		//isListener
                	,{
	                    header: "��ʱʱ��(��λСʱ)",
	                    sortable: true,
	                    dataIndex: "frequently",	
	                    allowBlank : true,
	                    editor: new Ext.form.TextField()
                	},   
                	{
	                    header: "��ʱת��",
	                    sortable: true,
	                    dataIndex: "inverse",	
	                    allowBlank : true,
	                    editor: new Ext.form.TextField()
                	},      
                	{
                		header: "id",
                		sortable: true,
			            hidden: true,
			            dataIndex: "id"
		     		}
		     ]);
		this.grid = new Ext.grid.EditorGridPanel({	
				id :'tgrid',
				store: this.store,
		        cm: this.cm,
		        sm: this.csm,
		        trackMouseOver:false,    
		        loadMask: true,
		        height: 'auto',		       
		        y : 0,
				anchor : '0 -0',
		        frame:true,
		        autoScroll: true,
		        autoEncode : true,
		         //plugins:[isListener],
		        clickToEdit: 1,			        
		        viewConfig : {
					autoFill : true,
					forceFit : true
		        },
			    tbar : ['&nbsp;', {
					xtype : 'button',
					style : 'margin:4px 10px 4px 0',
					text : '����',
					scope : this,
					iconCls : 'add',
					handler : function() {
					var store = this.store;
					var count = store.getCount();					
					if(count==1){
						//Ext.Msg.alert("ֻ������һ����Ϣ");
						alert("�˽ڵ�ֻ������һ����������");
						return;
					}	
					if (store.recordType) {
						var rec = new store.recordType({
							newRecord : true
						});
						rec.fields.each(function(f) {
								rec.data['defName'] = virtualDesc;
								rec.data['nodeName'] = nodeName;
								rec.data['frequently'] = null;
								rec.data['inverse'] = null;
								//rec.data['isListener'] = null;
						});
						rec.commit();
						store.add(rec);
						return rec;
					}
					return false;
					}
					}, '|&nbsp;', {
					xtype : 'button',
					style : 'margin:4px 10px 4px 0',
					text : 'ɾ��',					
					iconCls : 'remove',
					scope : this,
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
						Ext.MessageBox.confirm('��ʾ��', '��ȷ��Ҫ���иò�����', function(btn) {
							if (btn == 'yes') {
								if (records) {	
									this.removeIds = new Array();
									for (var i = 0; i < records.length; i++) {
										this.removeIds.push(records[i].get("id"));
										this.store.remove(records[i]);
									}										
									}								
								Ext.Ajax.request({						
					                   url: webContext+ '/configUnit_removeNodeTimerMessage.action',
				                       params:{
				                           'ids':this.removeIds				                           
				                       },
				                       mothod:'POST',
				                       success:function(response){
				                           var r=Ext.decode(response.responseText);
				                           if(!r.success){
				                       		Ext.Msg.alert("��ʾ��Ϣ","����ɾ��ʧ��",function(){	 
				                       			this.store.reload();
				                       		});
				                       }
				                       else{
				                       		
			                                Ext.Msg.alert("��ʾ��Ϣ","����ɾ���ɹ�!",function(){ 
			                                	this.store.reload();
			                                },this);
				                       }	 
				                       }, 
				                       scope:this
		                   		});
									}
								}, this)
							}
				this.removedButtons = "";
				}
			},'|&nbsp;', {
						xtype : 'button',
						style : 'margin:4px 10px 4px 0',
						text : '����',
						iconCls : 'save',
						scope : this,
						handler : function() {
							var cmp = Ext.getCmp('tgrid');
							
							var gridParam = cmp.getStore().getModifiedRecords();
							gridParam = [];
							var count = cmp.getStore().getCount();
							var data = "";					
							for (var i = 0; i < count; i++) {
								gridParam[i] = cmp.getStore().getAt(i);	
								if(gridParam[i].get("frequently")==null||""==gridParam[i].get("frequently")){
									Ext.Msg.alert("��ʾ��Ϣ","���ݱ���ʧ��",function(){
										alert("����¼�������ĵ�����Ϣ");
									});
									return;
								}								
								if(gridParam[i].get("inverse")==null||""==gridParam[i].get("inverse")){
									Ext.Msg.alert("��ʾ��Ϣ","���ݱ���ʧ��",function(){
										alert("����¼�������ĵ�����Ϣ");
									});
									return;
								}
								if(!(gridParam[i].get("frequently").match("^[0-9]+$"))){
								Ext.Msg.alert('��ʾ', '��ʱʱ������Ϊ���֣�');								
									return;
								}
								
									if(gridParam[i].get("id")==null||""==gridParam[i].get("id")){//���һ��ֵ��δ����Ļ���ô����null
										data="";
										alert(data);
										//return;
									}else{
										data=gridParam[i].get("id");
										alert("=="+data);
										//return;
									}
									
									Ext.Ajax.request({
							        url: webContext+ '/configUnit_saveNodeTimerMessage.action?virtualDefinitionInfoId='+virtualDefinitionInfoId+"&nodeId="+nodeId,
							        params : {
							        	id : data,
										defName : gridParam[i].get("defName"),
										nodeName : gridParam[i].get("nodeName"),
										frequently : gridParam[i].get("frequently"),
										inverse : gridParam[i].get("inverse")
										//isListener : gridParam[i].get("isListener")
									},
							        success:function(response){
				                       var r =Ext.decode(response.responseText);				                       
				                       if(!r.success){
				                       		Ext.Msg.alert("��ʾ��Ϣ","���ݱ���ʧ��",function(){	 
				                       			this.store.reload();
				                       		});
				                       }
				                       else{
				                       		
			                                Ext.Msg.alert("��ʾ��Ϣ","���ݱ���ɹ�!",function(){ 			                                	
			                                	this.store.reload();
			                                },this);
				                       }	                      
				                       
				                   },scope:this});	
						}
				}
			},'|&nbsp;', {
					xtype : 'button',
					style : 'margin:4px 10px 4px 0',
					text : '�ϴ�TIMER�ļ�<font color=red>(һ���ڵ�ֻ����һ����ʱ����Ϣ)</font>',//�����������������ǽ������һ����¼Ϊ׼
					scope : this,
					iconCls : 'add',
					handler : this.addTimerClass
					}],
					bbar : this.pageBar
		});		
		// ������ҳToolBar
		this.pageBar = new Ext.PagingToolbar({			
				pageSize : 10,
				store : this.store,
				displayInfo : true,
				displayMsg : '��ǰ��ʾ {0}-{1}����¼ /��{2}����¼',
				emptyMsg : '����ʾ����'
		});
		
		this.formValue = '';
		this.pageBar.formValue = this.formValue;	
		var param = {
			'start' : 0,
			'virtualDefinitionInfoId' : virtualDefinitionInfoId,
			'nodeId' : nodeId			
		};
		this.store.on('beforeload', function(a) {   
					      Ext.apply(a.baseParams,param);   
					});
		this.store.load({
			params : param
		});
		
		this.items = [this.grid];			
		ConfigUnitTimerPanel.superclass.initComponent.call(this);	
	}

});
Ext.grid.CheckColumn = function(config){
    Ext.apply(this, config);
    if(!this.id){
        this.id = Ext.id();
    }
    this.renderer = this.renderer.createDelegate(this);
};

Ext.grid.CheckColumn.prototype ={
    init : function(grid){
        this.grid = grid;
        this.grid.on('render', function(){
            var view = this.grid.getView();
            view.mainBody.on('mousedown', this.onMouseDown, this);
        }, this);
    },

    onMouseDown : function(e, t){
        if(t.className && t.className.indexOf('x-grid3-cc-'+this.id) != -1){
            e.stopEvent();
            var index = this.grid.getView().findRowIndex(t);
            var record = this.grid.store.getAt(index);
            record.set(this.dataIndex, (!record.data[this.dataIndex]?1:0));
        }
    },

    renderer : function(v, p, record){
        p.css += ' x-grid3-check-col-td'; 
        return '<div class="x-grid3-check-col'+(v?'-on':'')+' x-grid3-cc-'+this.id+'">&#160;</div>';
    }
};