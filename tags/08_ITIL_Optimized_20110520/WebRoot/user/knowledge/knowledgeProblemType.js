PagePanel = Ext.extend(Ext.Panel, {
	id : "PagePanel",
	closable : true,
	viewConfig : {
		autoFill : true,
		forceFit : true
	},
	layout : 'border',
	searchForm :function (){
		var si=Ext.getCmp('serviceItemCombo');
		if(si.getRawValue().trim()==""){
			si.setValue("");
		}
	    var param = Ext.getCmp('searchform').getForm().getValues(false);
		param.methodCall = 'query';
		param.start =0;
		this.store.removeAll();
		this.store.load({
			params : param
		});
	},
	create : function() {
		this.serviceItmeCombo = new Ext.form.ComboBox({
			name : "KnowProblemType_create$serviceItem",
			id : 'KnowProblemType_create$serviceItemCombo',
			fieldLabel : "服务项",
			width : 200,
			hiddenName : "KnowProblemType_create$serviceItem",
			displayField : 'name', 
			forceSelection : true,
			valueField : 'id',
			allowBlank : false,
			forceSelection :true,
			resizable : true,
			emptyText :'请从下拉列表中选择...',
			triggerAction : 'all',
			store : new Ext.data.JsonStore({
				url : webContext
						+ '/eventAction_findServiceItemByGroupLeader.action',
				fields : ['id', 'name'],
				totalProperty : 'rowCount',
				root : 'data',
				id : 'id'
			}),
			pageSize : 10,
			listeners : {
				'beforequery' : function(queryEvent) {
					var param = queryEvent.combo.getRawValue();
					var val = queryEvent.combo.getValue();
					if (queryEvent.query == '') {
						param = '';
					}
					this.store.baseParams={name : param,official:1};
					this.store.load();
					return false;
				}
			}
		});
		var knowForm = new Ext.form.FormPanel({
		    id:'knowProblemType',
			layout : 'table',
			width : 378,   
            height : 153,
			layoutConfig : {
				columns : 2
			},
			defaults : {
				bodyStyle : 'padding:12px'
			},
			frame : true,
			items : [{
						html : '问题类型名称:',
						cls : 'common-text',
						style : 'width:100;text-align:right'
					},new Ext.form.TextField({
						fieldLabel:"问题类型名称",
						xtype:"textfield",
						id:"KnowProblemType_create$name",
						name:"KnowProblemType_create$name",
						width : 200,
						allowBlank:false
					}),{
						html : '服务项:',
						cls : 'common-text',
						style : 'width:100;text-align:right'
					},this.serviceItmeCombo]

		});
		Ext.getCmp("KnowProblemType_create$name").setValue("");
		Ext.getCmp("KnowProblemType_create$serviceItemCombo").setValue("");
		var win = new Ext.Window({
				title : '添加问题类型',
				width : 395, 
			    height : 225,
				modal : true,
				items : knowForm,
				bodyStyle : 'padding:2px', 
				buttons : [
				  {
					text : '保存',
					id   : 'knowproblemtypesaveid',
					handler : function() {
						if (Ext.getCmp("knowProblemType").getForm().isValid()) {
							Ext.getCmp("knowproblemtypesaveid").disable();
					        var problemTypeparam = Ext.encode(getFormParam("knowProblemType"));
					        Ext.Ajax.request({
	                        url :webContext + '/knowledgeAction_saveProblemType.action',
	                        params: {  
                                     problemTypeparam : problemTypeparam,
                                      siId : Ext.getCmp("KnowProblemType_create$serviceItemCombo").getValue()
                                     },
	                        method:'post',  
	                        success:function(response,options){
		                        	Ext.MessageBox.alert("提示","保存数据成功！",function(){
		                        	   Ext.getCmp('knowProblemGrid').store.reload();
							           win.close();
		                        	});
						    },
	                        failure:function(response,options){
	                         	Ext.MessageBox.alert("提示","保存数据失败！");
	                         	 Ext.getCmp("knowproblemtypesaveid").enable();
	                        }
	                        });
	                     }else{
	                         Ext.MessageBox.alert("提示","红色波浪线部分为必填项！");
	                         Ext.getCmp("knowproblemtypesaveid").enable();
	                     }
					   },
				 scope : this

				}, {
					text : '关闭',
					handler : function() {
						win.close();
					},
					scope : this
				}]

			});
	    Ext.getCmp("knowproblemtypesaveid").on("click",function(obj){
		      Ext.getCmp("knowproblemtypesaveid").disable();
		      });
		win.show();
    },
       
     deleteType : function(){
     		 var record = this.grid.getSelectionModel().getSelected();
			var records = this.grid.getSelectionModel().getSelections();
			if(!record){
				Ext.Msg.alert("提示","请先选择要修改的行！");
				return;
			}
			if(records.length>1){
				Ext.Msg.alert("提示","修改时只能选择一行！");
				return;
			}
			var knowledgeId=record.get('KnowProblemType$id');	
			Ext.Ajax.request({
                    url :webContext + '/knowledgeAction_confirmUserInfo.action',
                    params: {  
                    	problmeTypeId : knowledgeId
                     },
                    method:'post',  
                    success:function(response,options){
                    	var msg = Ext.decode(response.responseText);
						if (msg.flag=='yes') {
							//modify begin
							var da = new DataAction();    
					        var data=da.getSingleFormPanelElementsForEdit("KnowLedgeProblemTypeForm_pagepanel",knowledgeId);
					        for(var i=0;i<data.length;i++){
					        	if(data[i].id=="KnowProblemType$name"||data[i].id=="KnowProblemType$serviceItemCombo"){
						        	data[i].readOnly=true;
						        	data[i].hideTrigger=true;
									data[i].emptyText="";
					        	}
							}
					        var dataform = da.split(data);
					        var envForm = new Ext.form.FormPanel({
					             id:'problemTypeModify',
						         layout : 'table',
						         width : 378,
						         height : 153, 
						         layoutConfig : {
							           columns : 2
						             },
						          defaults : {
							        bodyStyle : 'padding:12px'
						         },
						         frame : true,
						         items : dataform
			
					        });
					        var win = new Ext.Window({
							     title : '修改问题类型删除状态',
							     width : 395, 
							     height : 225,
							     modal : true,
							     items : envForm,
							     bodyStyle : 'padding:2px', 
							     buttons : [{
								      text : '保存',
								      id : 'modifyDeleteFlag',
								      handler : function() {
							              if (Ext.getCmp("problemTypeModify").getForm().isValid()) {
							              	 Ext.getCmp("modifyDeleteFlag").disable();
			                                 var problemTypeparam = Ext.encode(getFormParam("problemTypeModify"));
							                  Ext.Ajax.request({
			                                      url :webContext + '/knowledgeAction_saveProblemType.action',
			                                      params: { 
			                                           problemTypeparam : problemTypeparam
			                                       },
			                                      method:'post',  
			                                      success:function(response,options){
							                        	Ext.MessageBox.alert("提示","删除状态修改成功！",function(){
							                        	   Ext.getCmp('knowProblemGrid').store.reload();
												           win.close();
							                        	});
								                   },
			                                     	 failure:function(response,options){
			                         	                  Ext.MessageBox.alert("提示","删除状态修改失败！");
			                         	                   Ext.getCmp("modifyDeleteFlag").enable();
			                                        }
			                                   });
			                                   }
							                },
							                scope : this
							           },
								       {
								             text : '关闭',
								             handler : function() {
										          win.close();	
								               },
								             scope : this
							           }]
								    });
						       win.show();
							
							//modify end
							
                   		}else{
                   			Ext.MessageBox.alert("提示","对不起，您无权删除该服务项对应的问题类型！");
                   		}
				      
				    },
                    failure:function(response,options){
                     	Ext.MessageBox.alert("提示","系统出错！");
                    }
            });
     },
	getSearchForm : function() {
		this.panel = new Ext.form.FormPanel({
		    id :'searchform',
			region : "north",
			layout : 'table',
			height : 60,
			width : 'auto',
			frame : true,
			collapsible : true,
			defaults : {
				bodyStyle : 'padding:4px'
			},
			layoutConfig : {
				columns : 6
			},
			title : "查询列表",
			items : [{
				html : "问题类型名称:",
				cls : 'common-text',
				width : 110,
				style : 'width:180;text-align:right;'
			}, new Ext.form.TextField({
				xtype : 'textfield',
				colspan : 0,
				rowspan : 0,
				id : 'name',
				name : 'name',
				width : 200,
				allowBlank : true
			}), {
				html : "服务项:",
				cls : 'common-text',
				width : 80,
				style : 'width:150;text-align:right'
			}, new Ext.form.ComboBox({
				id : 'serviceItemCombo',
				fieldLabel : "服务项",
				width : 200,
				hiddenName : 'serviceItem',
				displayField : 'name',
				valueField : 'id',
				resizable : true,
				forceSelection :true,
				emptyText :'请从下拉列表中选择...',
				triggerAction : 'all',
				store : new Ext.data.JsonStore({
					url : webContext
							+ '/eventAction_findServiceItem.action',
					fields : ['id', 'name'],
					totalProperty : 'rowCount',
					root : 'data',
					id : 'id'
				}),
				pageSize : 10,
				listeners : {
					'beforequery' : function(queryEvent) {
						var param = queryEvent.combo.getRawValue();
						var val = queryEvent.combo.getValue();
						if (queryEvent.query == '') {
							param = '';
						}
						this.store.baseParams={name : param,official:1};
						this.store.load();
						return false;
					}
				}
			})]
		});
		return this.panel;
	},
	items : this.items,
	initComponent : function(){
		this.fp = this.getSearchForm();
		var da = new DataAction();
		this.modelTableName="KnowProblemType";
		var obj = da.getListPanelElementsForHead("KnowLedgeProblemType_pagepanel");
		var sm = new Ext.grid.CheckboxSelectionModel();
		var columns = new Array();
		var fields = new Array();
		columns[0] = sm;
		for (var i = 0; i < obj.length; i++) {
			var headItem = obj[i];
			var title = headItem.header;

			var alignStyle = 'left';
			var propertyName = headItem.dataIndex;
			
			var isHiddenColumn = false;
            var modelTableId = this.modelTableName+"$id";         
            var isHidden = headItem.isHidden;
            if(isHidden=='true'){
            	isHiddenColumn = true;
            }
			var columnItem = {
				header : title,
				dataIndex : propertyName,
				sortable : true,
				hidden : isHiddenColumn,
				align : alignStyle
			}
			if(propertyName=="KnowProblemType$id"){
				columnItem.width=70;
			}
			if(propertyName=="KnowProblemType$name"){
				columnItem.width=200;
			}
			if(propertyName=="KnowProblemType$serviceItem"){
				columnItem.width=230;
			}
			if(propertyName=="KnowProblemType$deleteFlag"){
				columnItem.width=80;
			}
			columns[i + 1] = columnItem;
			fields[i] = propertyName;
		}
		this.storeMapping = fields;
		this.cm = new Ext.grid.ColumnModel(columns);
		this.store = da.getPanelJsonStore("KnowLedgeProblemType_pagepanel",obj); //add header 
		this.store.paramNames.sort = "orderBy";
		this.store.paramNames.dir = "orderType";
		this.cm.defaultSortable = true;
		var viewConfig = Ext.apply({
			forceFit : true
		}, this.gridViewConfig);

		this.pageBar = new Ext.PagingToolbar({
			pageSize : 10,
			store : this.store,
			displayInfo : true,
			displayMsg : '当前显示 {0}-{1}条记录 /共{2}条记录',
			emptyMsg : "无显示数据"
		});
		this.grid = new Ext.grid.GridPanel({
		    id : 'knowProblemGrid',
			region : "center",
			store : this.store,
			cm : this.cm,
			sm : sm,
			trackMouseOver : false,
			loadMask : true,
			y : 140,
			height : 200,
			anchor : '0 -35',
			tbar : [{
				text : '新增',
				handler : this.create,
				scope : this,
				iconCls : 'add'
			},'-',{
				text : '查询',
				handler : this.searchForm,
				scope : this,
				iconCls : 'search'
			},'-',{
				text : '修改删除状态',
				handler : this.deleteType,
				scope : this,
				iconCls : 'reset'
			}],
			bbar : this.pageBar
		});
		
		this.grid.on("headerdblclick", this.fitWidth, this);
		this.grid.on("rowdblclick", this.deleteType, this);
		var items = new Array();
		items.push(this.fp);
		items.push(this.grid);
		this.items = items;
		
		PagePanel.superclass.initComponent.call(this);
		var params = {
			'mehtodCall' : 'query',
			'start' : 0
		};
		
//		this.pageBar.formValue = param;
		this.store.on('beforeload', function(a) {   
		      Ext.apply(a.baseParams,params);   
		});
		this.store.removeAll();
		this.store.load({
			params : params
		});
	},
	fitWidth : function(grid, columnIndex, e) {
		var c = columnIndex;
		var w = grid.view.getHeaderCell(c).firstChild.scrollWidth;
		for (var i = 0, i = grid.store.getCount(); i < l; i++) {
			w = Math.max(w, grid.view.getCell(i, c).firstChild.scrollWidth);
		}
		grid.colModel.setColumnWidth(c, w);
	}
});





