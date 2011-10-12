ButtonDataPanel = Ext.extend(Ext.Panel, {
	title: '��尴ť',
	width: window.screen.availWidth/2-112,
	autoScroll: true,
	columnWidth:.5,
	layout:'fit',
	
	initComponent: function(){
		
		var csm = new Ext.grid.CheckboxSelectionModel();
		var openWinFlagcombo=new Ext.grid.CheckColumn({
       		header: "�Ƿ񴰿�",
       		dataIndex: 'openWinFlag',
       		width: 55
    	});
    	var isDisplay=new Ext.grid.CheckColumn({
       		header: "�Ƿ���ʾ",
       		dataIndex: 'isDisplay',
       		width: 55
    	});
		this.store = new Ext.data.JsonStore({ 				
				url: webContext+'/pageModel/pagePanelManage.do?methodCall=getButtons&pagePanelId='+ppId,
				fields: ['id','btnType','btnName','method','link','nextPageModel','openWinFlag','imageUrl','order','isDisplay'],
			    root:'data',
				sortInfo: {field: "id", direction: "ASC"}
		});
		this.removedButtons="";
		this.cm = new Ext.grid.ColumnModel([csm,{//������Ǳ���ı�ͷ
	                    header: "��ť����",
	                    sortable: true,
	                    hideable: false,
	                    dataIndex: "btnName",
	                    width: 80,
	                    editor: new Ext.form.TextField()
                	}, {
	                    header: "��ť����",
	                    sortable: true,
	                    hidden: true,
	                    dataIndex: "btnType",
	                    width: 100,					
	                    editor: new Ext.form.ComboBox({
							store : new Ext.data.JsonStore({
								url: webContext+'/pageModel/pageModelManage.do?methodCall=findAllPageModel',
								fields: ['pageModelId','name'],
							    root:'data',
								sortInfo: {field: "pageModelId", direction: "ASC"}
							}), 
							valueField : "name",
							displayField : "name",
							id : 'comboxId',
			                emptyText: '',
							mode : 'remote',
							forceSelection : true,
							hiddenName : "name",
							editable : false,
							triggerAction : 'all', 
							lazyRender: true,
				            typeAhead: true,
							allowBlank : true,
							name : "name",
							selectOnFocus: true
						})
                	},{
	                    header: "�¸�ҳ��",
	                    sortable: true,
	                    hideable: false,
	                    dataIndex: "nextPageModel",
	                    width: 150,					
	                    editor: new Ext.form.ComboBox({
							store : new Ext.data.JsonStore({
								url: webContext+'/pageModel/pageModelManage.do?methodCall=findPageModelBtn',
								fields: ['pageModelId','name'],
							    root:'data',
								sortInfo: {field: "pageModelId", direction: "ASC"}
							}), 
							valueField : "name",
							displayField : "name",
							id : 'comboxId',
			                emptyText: '',
							mode : 'remote',
							forceSelection : true,
							hiddenName : "pageModelId",
							editable : false,
							triggerAction : 'all', 
							lazyRender: true,
				            typeAhead: true,
							allowBlank : true,
							name : "name",
							selectOnFocus: true
						})
                	}, {
	                    header: "����",
	                    sortable: true,
	                    hideable: false,
	                    dataIndex: "method",
	                    width: 55,
	                    editor: new Ext.form.TextField()
                	},{
	                    header: "����",
	                    sortable: true,
	                    hideable: false,
	                    dataIndex: "link",
	                    width: 55,
	                    editor: new Ext.form.TextField()
                	},{
	                    header: "��ť��ʾ",
	                    sortable: true,
	                    hideable: false,
	                    dataIndex: "imageUrl",
	                    width: 55,
	                    editor: new Ext.form.TextField()
                	},{
	                    header: "����",
	                    sortable: true,
	                    hideable: false,
	                    dataIndex: "order",
	                    width: 50,
	                    editor: new Ext.form.TextField()
                	},
                		openWinFlagcombo,
                		isDisplay,            	
                	{
                		header: "id",
                		sortable: true,
			            hidden: true,
			            dataIndex: "id"
		     		}
		     ]); 
		this.grid = new Ext.grid.EditorGridPanel({
		        store: this.store,
		        cm: this.cm,
		        sm: csm,
		        trackMouseOver:false,    
		        loadMask: true,
		        width: window.screen.availWidth/2-112,
		        height: 450,
		        frame:true,
		        autoScroll: true,
		        autoEncode : true,
		        plugins:[openWinFlagcombo,isDisplay],
		        clickToEdit: 1,
				//y : 0,
				//anchor : '0 -0',
				//viewConfig : {
				//	autoFill : true,
				//	forceFit : true
				//},
				tbar : [{
				xtype : 'button',
				style : 'margin:4px 10px 4px 0',
				text : '���Ӱ�ť',
				pressed : true,
				scope : this,
				iconCls : 'add',
				handler : function() {
					var store = this.store;
					if (store.recordType) {
						var rec = new store.recordType({
							newRecord : true
						});
						rec.fields.each(function(f) {
							rec.data[f.name] = f.defaultValue || null;
						});
						rec.commit();
						store.add(rec);
						return rec;
					}
					return false;
				}

			}, {
				xtype : 'button',
				style : 'margin:4px 10px 4px 0',
				text : 'ɾ��',
				pressed : true,
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
									for (var i = 0; i < records.length; i++) {
										this.removedButtons += records[i].get("id")+ ",";
										this.store.remove(records[i]);
									}
								}
							}
						}, this)
					}

				}
			},{
				xtype : 'button',
				style : 'margin:4px 10px 4px 0',
				text : '����',
				pressed : true,
				scope : this,
				iconCls : 'save',
				handler : function() {
					var store = this.store;
					var removedButtons = this.removedButtons;
					Ext.MessageBox.confirm('��ʾ��Ϣ', '�Ƿ����Ҫ�޸�',function(ret){
						if(ret=="yes"){
						var buttonparam = store.getRange(0,store.getCount());
						var buttonmsg = "";
						for (i = 0; i < buttonparam.length; i++) {
						buttonmsg += Ext.encode(buttonparam[i].data) + ",";
						}
						buttonmsg = unicode(buttonmsg);
						Ext.Ajax.request({
				        url:webContext+'/pageModel/pagePanelManage.do?methodCall=modifyButton',
				        params : {
							ppId : ppId,
							buttonmsg :buttonmsg,
							removedButtons : removedButtons
						},
				        success:function(response){
				        	Ext.Msg.alert("����ɹ���");
					        if(response.responseText.indexOf("�ܾ�") != -1){
					        	Ext.Msg.alert("��ʾ��Ϣ","��û��Ȩ���޸���Ϣ");
					        }else{
						        var result = Ext.decode(response.responseText);
						        if(!result.success){
						        	Ext.Msg.alert("��ʾ��Ϣ","���ݱ���ʧ�ܣ�������ԭ�����£�<br/>"+(r.errors.msg?r.errors.msg:"δ֪ԭ��"));
						        }
					        }
					        store.reload();
				        },scope:this});		
		   				}else{
		   					Ext.Msg.alert("��ʾ��Ϣ","�޸���Ϣδ���棡");
		   				}
					});
				}
			}, {
				xtype : 'button',
				style : 'margin:4px 10px 4px 0',
				text : '����Ĭ�ϰ�ť',
				pressed : true,
				iconCls : 'add',
				scope : this,
				handler : function() {
					var store = this.store;
					Ext.Ajax.request({
				        url:webContext+'/pageModel/pagePanelManage.do?methodCall=initButton',
				        params : {
							ppId : ppId
						},
				        success:function(response){
				        	Ext.Msg.alert("����ɹ���");
					        if(response.responseText.indexOf("�ܾ�") != -1){
					        	Ext.Msg.alert("��ʾ��Ϣ","��û��Ȩ�޳�ʼ��");
					        }else{
						        var result = Ext.decode(response.responseText);
						        if(!result.success){
						        	Ext.Msg.alert("��ʾ��Ϣ","��ʼ��ʧ�ܣ�������ԭ�����£�<br/>"+(r.errors.msg?r.errors.msg:"δ֪ԭ��"));
						        }
					        }
					        store.reload();
				        },scope:this});		
					}
				}
			]
		});  
		this.store.load();
		this.items = [this.grid];
		ButtonDataPanel.superclass.initComponent.call(this);	
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