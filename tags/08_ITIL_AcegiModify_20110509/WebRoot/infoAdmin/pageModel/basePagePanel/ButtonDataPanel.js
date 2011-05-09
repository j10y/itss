ButtonDataPanel = Ext.extend(Ext.Panel, {
	title: '面板按钮',
	width: window.screen.availWidth/2-112,
	autoScroll: true,
	columnWidth:.5,
	layout:'fit',
	
	initComponent: function(){
		
		var csm = new Ext.grid.CheckboxSelectionModel();
		var openWinFlagcombo=new Ext.grid.CheckColumn({
       		header: "是否窗口",
       		dataIndex: 'openWinFlag',
       		width: 55
    	});
    	var isDisplay=new Ext.grid.CheckColumn({
       		header: "是否显示",
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
		this.cm = new Ext.grid.ColumnModel([csm,{//这个就是表格的表头
	                    header: "按钮名称",
	                    sortable: true,
	                    hideable: false,
	                    dataIndex: "btnName",
	                    width: 80,
	                    editor: new Ext.form.TextField()
                	}, {
	                    header: "按钮类型",
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
	                    header: "下个页面",
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
	                    header: "方法",
	                    sortable: true,
	                    hideable: false,
	                    dataIndex: "method",
	                    width: 55,
	                    editor: new Ext.form.TextField()
                	},{
	                    header: "链接",
	                    sortable: true,
	                    hideable: false,
	                    dataIndex: "link",
	                    width: 55,
	                    editor: new Ext.form.TextField()
                	},{
	                    header: "按钮标示",
	                    sortable: true,
	                    hideable: false,
	                    dataIndex: "imageUrl",
	                    width: 55,
	                    editor: new Ext.form.TextField()
                	},{
	                    header: "排序",
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
				text : '增加按钮',
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
				text : '删除',
				pressed : true,
				iconCls : 'remove',
				scope : this,
				handler : function() {
					var record = this.grid.getSelectionModel().getSelected();
					var records = this.grid.getSelectionModel().getSelections();
					if (!record) {
						Ext.Msg.alert("提示", "请先选择要删除的行!");
						return;
					}
					if (records.length == 0) {
						Ext.MessageBox.alert('警告', '最少选择一条信息，进行删除!');
					} else {
						Ext.MessageBox.confirm('提示框', '您确定要进行该操作？', function(btn) {
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
				text : '保存',
				pressed : true,
				scope : this,
				iconCls : 'save',
				handler : function() {
					var store = this.store;
					var removedButtons = this.removedButtons;
					Ext.MessageBox.confirm('提示信息', '是否真的要修改',function(ret){
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
				        	Ext.Msg.alert("保存成功！");
					        if(response.responseText.indexOf("拒绝") != -1){
					        	Ext.Msg.alert("提示信息","您没有权限修改信息");
					        }else{
						        var result = Ext.decode(response.responseText);
						        if(!result.success){
						        	Ext.Msg.alert("提示信息","数据保存失败，由以下原因所致：<br/>"+(r.errors.msg?r.errors.msg:"未知原因"));
						        }
					        }
					        store.reload();
				        },scope:this});		
		   				}else{
		   					Ext.Msg.alert("提示信息","修改信息未保存！");
		   				}
					});
				}
			}, {
				xtype : 'button',
				style : 'margin:4px 10px 4px 0',
				text : '添加默认按钮',
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
				        	Ext.Msg.alert("保存成功！");
					        if(response.responseText.indexOf("拒绝") != -1){
					        	Ext.Msg.alert("提示信息","您没有权限初始化");
					        }else{
						        var result = Ext.decode(response.responseText);
						        if(!result.success){
						        	Ext.Msg.alert("提示信息","初始化失败，由以下原因所致：<br/>"+(r.errors.msg?r.errors.msg:"未知原因"));
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