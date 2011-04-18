PagePanel = Ext.extend(Ext.Panel, {
	  id : "engineer",
	  closable : true,
	  autoScroll:true,
	  viewConfig : {
		autoFill : true,
		forceFit : true
	  },
	  layout : 'absolute',
	  //列出问题
    getSearchFor: function() {
           var da = new DataAction();
		   var data=da.getSingleFormPanelElementsForEdit("problemDetail_pagepanel",this.pdataId);
		   for(var i=0;i<data.length;i++){
	   		if(data[i].xtype=="panel"){
                data[i].items[0].disabled=true;
				data[i].items[1].html=data[i].items[1].html.replace(/display:/g, "display:none");;
      		}
      		else{
      		data[i].readOnly=true;
			data[i].hideTrigger=true;
			data[i].emptyText="";
      		}
		   }
		   var biddata = da.split(data);
	       this.panel = new Ext.form.FormPanel({ 
			 layout : 'table',
			 height : 'auto',
			 width : 753,
			 frame : true,
			 collapsible : true,
			 defaults : {
				bodyStyle : 'padding:4px'
			 },
			 layoutConfig : {
				columns : 4
			  } ,
			 title : "问题详细资料",
			 items : biddata
		     });
	     return this.panel;
		
	   },
	modifyProblemRelation :function(){
	    var clazz = "com.digitalchina.itil.event.entity.ProblemRelation";
		var record = this.problemRelationGrid.getSelectionModel().getSelected();
	    var records = this.problemRelationGrid.getSelectionModel().getSelections();
		        if (!record) {
			         Ext.Msg.alert("提示", "请先选择要修改或查看的的行！");
			         return;
		           }
		         if (records.length == 0) {
			         Ext.MessageBox.alert('提示', '选择一条信息！');
			          return;
		        }
		var problemReId = record.get("ProblemRelation$id");
	   	var conn = Ext.lib.Ajax.getConnectionObject().conn;
        conn.open("POST", webContext+'/problemAction_relationProblem.action?problemId='+problemReId,false);
        conn.send(null);	
        var result = Ext.decode(conn.responseText);
        var parentProblemId=result.parentProblemId;
        var RelationEventName=result.RelationEventName;
        var RelatioProblemName=result.RelatioProblemName;
        var RelationProblemTypeName=result.RelationProblemTypeName;
        //关联事件
	    this.raletionEvent = new Ext.form.ComboBox({
		    id :'raletionEvent',
			width : 200,
			editable : false,
			displayField : 'summary',
			resizable : true,
			triggerAction : 'all',
		    store : new Ext.data.JsonStore({
		    })
	    });
	    Ext.getCmp('raletionEvent').setValue(RelationEventName);
	    //关联事件的问题
	    this.problemName = new Ext.form.ComboBox({
			id : 'problemCombox',
			width : 200,
			editable : false,
			displayField : 'problemName',
			valueField : 'problemId',
			resizable : true,
			triggerAction : 'all',
			store : new Ext.data.JsonStore({
				
			})
		}); 
	    Ext.getCmp('problemCombox').setValue(RelatioProblemName);
	    var problemRelationTypeStore = new Ext.data.JsonStore({
		});
		//问题关系设置
		var problemRelationTypeCombo = new Ext.form.ComboBox({
			id : 'problemRelationTypeCombo',
			width : 200,
			editable : false,
			displayField : 'name',
			valueField : 'id',
			resizable : true,
			triggerAction : 'all',
			store : problemRelationTypeStore
		});	
		Ext.getCmp('problemRelationTypeCombo').setValue(RelationProblemTypeName);
		var da = new DataAction();
		var data=da.getSingleFormPanelElementsForLook("problemForm_pagepanel",parentProblemId);
		var dataform = da.split(data);
        this.problemRelation = new Ext.form.FormPanel({
            id:'problemRelation',
			layout : 'table',
			width : 650,
			height : 150,
			layoutConfig : {
				columns : 2
			},
			defaults : {
				bodyStyle : 'padding:5px'
			},
			frame : true,
			items : dataform
		    });
	    this.panel = new Ext.form.FormPanel({
	        id:'problemRelationModify', 
			layout : 'table',
			height : 'auto',
			width : 650,
			frame : true,
			defaults : {
				bodyStyle : 'padding:4px'
			},
			layoutConfig : {
				columns : 6
			},
			items :[{xtype : 'fieldset',	
			         width : 650,
					height : 'auto',
					defaults : {
						bodyStyle : 'padding:5px'
					},
					layout : 'table',
					layoutConfig : {
						columns : 4
					},
			        items:[
			         {html: " 关联事件：&nbsp;",cls: 'common-text',style:'width:80;text-align:right'},
			         this.raletionEvent, 
			         {html: " 相关问题：&nbsp;",cls: 'common-text',style:'width:80;text-align:right'},
			         this.problemName,
			         {html: " 问题关系类型：&nbsp;",cls: 'common-text',style:'width:100;text-align:right'},
			         problemRelationTypeCombo
			         ]
			        }]
		});
	    var winProblem = new Ext.Window({
	            id :'problemRelationWindow',
				title : '关联问题',
				width : 680,
				height : 350,
				//maximizable : true,
				autoScroll:true,
				items : [this.panel,this.problemRelation],
				closeAction : 'hide',
				bodyStyle : 'padding:4px',
				buttons : [ {
					text : '关闭',
					handler : function() {
						winProblem.close();
					},
					scope : this
				          }]

			});
		winProblem.show();
	}, 
	
	

	
		  //弹出问题关系窗口
	  operate : function(problemReId,relationEventId,relatioProblemId,relationProblemTypeId){
	  	var thisPdataId = this.pdataId;
		var hidProblemRelId = new Ext.form.Hidden({
				xtype : 'hidden',
				id : 'problemRelId',
				name : 'problemRelId'
			});
	  	var pr_eventCombo = new Ext.form.ComboBox({
			xtype : 'combo',
			hiddenName : 'pr_event',
			id : 'pr_eventCombo',
			width : 200,
			style : '',
			fieldLabel : '事件',
			lazyRender : true,
			displayField : 'summary',
			valueField : 'id',
			emptyText : '请选择...',
			allowBlank : false,
			//typeAhead : true,
			name : 'pr_event',
			resizable : true,
			triggerAction : 'all',
			minChars : 50,
			queryDelay : 700,
			store : new Ext.data.JsonStore({
				url : webContext + '/extjs/comboDataAction?clazz=com.digitalchina.itil.event.entity.Event',
				fields : ['id', 'summary'],
				listeners : {
					beforeload : function(store, opt) {
						if (opt.params['pr_event'] == undefined) {
							opt.params['summary'] = Ext.getCmp('pr_eventCombo').defaultParam;
						}
					}
				},
				totalProperty : 'rowCount',
				root : 'data',
				id : 'id'
			}),
			pageSize : 10,
			listeners : {
				'beforequery' : function(queryEvent) {
					var param = queryEvent.combo.getRawValue();
					this.defaultParam = param;
					if (queryEvent.query == '') {
						param = '';
					}
					this.store.load({
						params : {
							summary : param,
							start : 0
						}
					});
					return true;
				},
				'select' : function(combo, record, index){
					
				}
			},
			initComponent : function() {
				this.store.load({
					params : {
						id : Ext.getCmp('pr_eventCombo').getValue(),
						start : 0
					},
					callback : function(r, options, success) {
						Ext.getCmp('pr_eventCombo').setValue(Ext.getCmp('pr_eventCombo').getValue());
					}
				});
			}
		});
		var pr_problemCombo = new Ext.form.ComboBox({
			xtype : 'combo',
			hiddenName : 'pr_problem',
			id : 'pr_problemCombo',
			width : 200,
			style : '',
			fieldLabel : '关联问题',
			lazyRender : true,
			displayField : 'summary',
			valueField : 'id',
			emptyText : '请选择...',
			allowBlank : false,
			//typeAhead : true,
			name : 'pr_problem',
			resizable : true,
			triggerAction : 'all',
			minChars : 50,
			queryDelay : 700,
			store : new Ext.data.JsonStore({
				url : webContext+ '/problemAction_findEventProblems.action',
				fields : ['id', 'summary'],
				listeners : {
					beforeload : function(store, opt) {
						opt.params['relEventId'] = Ext.getCmp('pr_eventCombo').getValue();
						if (opt.params['pr_problem'] == undefined) {
							opt.params['summary'] = Ext.getCmp('pr_problemCombo').defaultParam;
						}
					}
				},
				totalProperty : 'rowCount',
				root : 'data',
				id : 'id'
			}),
			pageSize : 10,
			listeners : {
				'beforequery' : function(queryEvent) {
					var param = queryEvent.combo.getRawValue();
					this.defaultParam = param;
					if (queryEvent.query == '') {
						param = '';
					}
					this.store.load({
						params : {
							relEventId : relEventId,
							summary : summary,
							status : "nodelete",
							start : 0
						}
					});
					return true;
				},
				'select':function(combo, record, index){
						var curEventId = record.get('id');
					if(curEventId==thisPdataId){
						Ext.Msg.alert("提示","相同问题不能添加关系！");
						Ext.getCmp('pr_problemCombo').clearValue();
					}
				}
				
			},
			initComponent : function() {
				this.store.load({
					params : {
						id : Ext.getCmp('pr_problemCombo').getValue(),
						start : 0
					},
					callback : function(r, options, success) {
						Ext.getCmp('pr_problemCombo').setValue(Ext.getCmp('pr_problemCombo').getValue());
					}
				});
			}
		});
		var problemRelTypeCombo = new Ext.form.ComboBox({
			xtype : 'combo',
			hiddenName : 'pr_prType',
			id : 'pr_prTypeCombo',
			width : 200,
			style : '',
			fieldLabel : '问题关系类型',
			lazyRender : true,
			displayField : 'name',
			valueField : 'id',
			emptyText : '请选择...',
			allowBlank : false,
			//typeAhead : true,
			name : 'pr_prType',
			resizable : true,
			triggerAction : 'all',
			minChars : 50,
			queryDelay : 700,
			store : new Ext.data.JsonStore({
				url : webContext
						+ '/extjs/comboDataAction?clazz=com.digitalchina.itil.event.entity.ProblemRelationType',
				fields : ['id', 'name'],
				listeners : {
					beforeload : function(store, opt) {
						if (opt.params['pr_prType'] == undefined) {
							opt.params['name'] = Ext.getCmp('pr_prTypeCombo').defaultParam;
						}
					}
				},
				totalProperty : 'rowCount',
				root : 'data',
				id : 'id'
			}),
			pageSize : 10,
			listeners : {
				'beforequery' : function(queryEvent) {
					var param = queryEvent.combo.getRawValue();
					this.defaultParam = param;
					if (queryEvent.query == '') {
						param = '';
					}
					this.store.load({
						params : {
							name : param,
							start : 0
						}
					});
					return true;
				}
			},
			initComponent : function() {
				this.store.load({
					params : {
						id : Ext.getCmp('pr_prTypeCombo').getValue(),
						start : 0
					},
					callback : function(r, options, success) {
						Ext.getCmp('pr_prTypeCombo').setValue(Ext.getCmp('pr_prTypeCombo').getValue());
					}
				});
			}
		});
		
		var prPanel = new Ext.form.FormPanel({
			id : 'prPanel',
			title : '问题关系',
			layout : 'table',
			height : 'auto',
			width : 710,
			frame : true,
			//autoScroll : true,
			defaults : {
				bodyStyle : 'padding:5px'
			},
			layoutConfig : {
				columns : 4
			},
			items : [{html : "关联事件：",cls : 'common-text',style : 'width:110;text-align:right'}, pr_eventCombo, 
					{html : "关联问题：",cls : 'common-text',style : 'width:110;text-align:right'}, pr_problemCombo, 
					{html : "关系类型：",cls : 'common-text',style : 'width:110;text-align:right'}, problemRelTypeCombo]});
		
		var formproblemForm_pagepanel = new Ext.form.FormPanel({
			id : 'problemForm_pagepanel2',
			layout : 'table',
			height : 'auto',
			width : 710,
			frame : true,
			collapsible : true,
			defaults : {
				bodyStyle : 'padding:5px'
			},
			layoutConfig : {
				columns : 4
			},
			reader : new Ext.data.JsonReader({
				root : 'form',
				successProperty : 'success'
			}, [{name : 'Problem$id2',mapping : 'Problem$id'}, 
				{name : 'Problem$status2',mapping : 'Problem$status'}, 
				{name : 'Problem$submitUser2',mapping : 'Problem$submitUser'}, 
				{name : 'Problem$contactEmail2',mapping : 'Problem$contactEmail'}, 
				{name : 'Problem$summary2',mapping : 'Problem$summary'}, 
				{name : 'Problem$submitTime2',mapping : 'Problem$submitTime'}, 
				{name : 'Problem$problemCisn2',mapping : 'Problem$problemCisn'}, 
				{name : 'Problem$remark2',mapping : 'Problem$remark'}, 
				{name : 'Problem$files2',mapping : 'Problem$files'}]),
			title : "关联问题",
			items : [{
				html : '问题状态：',
				cls : 'common-text',
				style : 'width:110;text-align:right'
			}, new Ext.form.ComboBox({
				xtype : 'combo',
				hiddenName : 'Problem$status2',
				id : 'Problem$status2Combo',
				width : 200,
				style : '',
				fieldLabel : '问题状态',
				readOnly : true,
				hideTrigger:true,
				lazyRender : true,
				displayField : 'name',
				valueField : 'id',
				emptyText : '请选择...',
				allowBlank : true,
				//typeAhead : true,
				name : 'Problem$status2',
				resizable : true,
				triggerAction : 'all',
				minChars : 50,
				queryDelay : 700,
				store : new Ext.data.JsonStore({
					url : webContext
							+ '/extjs/comboDataAction?clazz=com.digitalchina.itil.event.entity.ProblemStatus',
					fields : ['id', 'name'],
					listeners : {
						beforeload : function(store, opt) {
							if (opt.params['Problem$status2'] == undefined) {
								opt.params['name'] = Ext
										.getCmp('Problem$status2Combo').defaultParam;
							}
						}
					},
					totalProperty : 'rowCount',
					root : 'data',
					id : 'id'
				}),
				pageSize : 10,
				listeners : {
					'beforequery' : function(queryEvent) {
						var param = queryEvent.combo.getRawValue();
						this.defaultParam = param;
						if (queryEvent.query == '') {
							param = '';
						}
						this.store.load({
							params : {
								name : param,
								start : 0
							}
						});
						return true;
					}
				},
				initComponent : function() {
					this.store.load({
						params : {
							id : Ext.getCmp('Problem$status2Combo').getValue(),
							start : 0
						},
						callback : function(r, options, success) {
							Ext.getCmp('Problem$status2Combo').setValue(Ext
									.getCmp('Problem$status2Combo').getValue());
						}
					});
				}
			}), {
				html : '问题提交人：',
				cls : 'common-text',
				style : 'width:110;text-align:right'
			}, new Ext.form.ComboBox({
				xtype : 'combo',
				hiddenName : 'Problem$submitUser2',
				id : 'Problem$submitUser2Combo',
				width : 200,
				style : '',
				fieldLabel : '问题提交人',
				readOnly : true,
				hideTrigger:true,
				lazyRender : true,
				displayField : 'realName',
				valueField : 'id',
				emptyText : '请选择...',
				allowBlank : true,
				//typeAhead : true,
				name : 'Problem$submitUser2',
				resizable : true,
				triggerAction : 'all',
				minChars : 50,
				queryDelay : 700,
				store : new Ext.data.JsonStore({
					url : webContext
							+ '/extjs/comboDataAction?clazz=com.digitalchina.info.framework.security.entity.UserInfo',
					fields : ['id', 'realName'],
					listeners : {
						beforeload : function(store, opt) {
							if (opt.params['Problem$submitUser2'] == undefined) {
								opt.params['realName'] = Ext
										.getCmp('Problem$submitUser2Combo').defaultParam;
							}
						}
					},
					totalProperty : 'rowCount',
					root : 'data',
					id : 'id'
				}),
				pageSize : 10,
				listeners : {
					'beforequery' : function(queryEvent) {
						var param = queryEvent.combo.getRawValue();
						this.defaultParam = param;
						if (queryEvent.query == '') {
							param = '';
						}
						this.store.load({
							params : {
								realName : param,
								start : 0
							}
						});
						return true;
					}
				},
				initComponent : function() {
					this.store.load({
						params : {
							id : Ext.getCmp('Problem$submitUser2Combo')
									.getValue(),
							start : 0
						},
						callback : function(r, options, success) {
							Ext.getCmp('Problem$submitUser2Combo').setValue(Ext
									.getCmp('Problem$submitUser2Combo')
									.getValue());
						}
					});
				}
			}), {
				html : '问题联系人邮件：',
				cls : 'common-text',
				style : 'width:110;text-align:right'
			}, new Ext.form.TextField({
				fieldLabel : '问题联系人邮件',
				xtype : 'textfield',
				readOnly : true,
				id : 'Problem$contactEmail2',
				name : 'Problem$contactEmail2',
				style : '',
				width : 200,
				value : '',
				allowBlank : true,
				validator : '',
				vtype : ''
			}), {
				html : '问题名称：',
				cls : 'common-text',
				style : 'width:110;text-align:right'
			}, new Ext.form.TextField({
				fieldLabel : '问题名称',
				xtype : 'textfield',
				readOnly : true,
				id : 'Problem$summary2',
				name : 'Problem$summary2',
				style : '',
				width : 200,
				value : '',
				allowBlank : true,
				validator : '',
				vtype : ''
			}), {
				html : '问题提交时间：',
				cls : 'common-text',
				style : 'width:110;text-align:right'
			}, new Ext.form.DateField({
				xtype : 'datefield',
				id : 'Problem$submitTime2',
				readOnly : true,
				hideTrigger:true,
				name : 'Problem$submitTime2',
				width : 200,
				style : '',
				value : '',
				allowBlank : true,
				validator : '',
				format : 'Y-m-d',
				fieldLabel : '问题提交时间'
			}), {
				html : '问题编号：',
				cls : 'common-text',
				style : 'width:110;text-align:right'
			}, new Ext.form.TextField({
				fieldLabel : '问题编号',
				xtype : 'textfield',
				readOnly : true,
				id : 'Problem$problemCisn2',
				name : 'Problem$problemCisn2',
				style : '',
				width : 200,
				value : '',
				allowBlank : true,
				validator : '',
				vtype : ''
			}), {
				html : '备注：',
				cls : 'common-text',
				style : 'width:110;text-align:right'
			}, new Ext.form.TextArea({
				xtype : 'textarea',
				id : 'Problem$remark2',
				readOnly : true,
				name : 'Problem$remark2',
				width : 513,
				height : 60,
				colspan : 3,
				allowBlank : true,
				validator : '',
				fieldLabel : '备注'
			})
//			, {
//				html : '附件:',
//				cls : 'common-text',
//				style : 'width:135;text-align:right'
//			}, {
//				fieldLabel : '附件',
//				xtype : 'button',
//				text : '<font color=red>上传</font>/<font color=green>下载</font>',
//				width : '50',
//				scope : this,
//				handler : function() {
//					var attachmentFlag = Ext.getCmp('Problem$files2').getValue();
//					if (attachmentFlag == '') {
//						attachmentFlag = Date.parse(new Date());
//						Ext.getCmp('Problem$files2').setValue(attachmentFlag);
//						var ud = new UpDownLoadFile();
//						ud
//								.getUpDownLoadFileSu(attachmentFlag, '3376',
//										'com.digitalchina.info.appframework.metadata.entity.SystemFile');
//					} else {
//						var ud = new UpDownLoadFile();
//						ud
//								.getUpDownLoadFileSu(attachmentFlag, '3376',
//										'com.digitalchina.info.appframework.metadata.entity.SystemFile');
//					}
//				}
//			}, new Ext.form.Hidden({
//				xtype : 'hidden',
//				id : 'Problem$files2',
//				name : 'Problem$files2',
//				style : '',
//				value : 'null',
//				fieldLabel : 'nowtime'
//			}), new Ext.form.Hidden({
//				xtype : 'hidden',
//				id : 'Problem$id2',
//				colspan : 0,
//				rowspan : 0,
//				name : 'Problem$id2',
//				width : 200,
//				style : '',
//				value : '',
//				fieldLabel : '自动编号'
//			})
			]
		});
		if (problemReId!= "") {
			formproblemForm_pagepanel.load({
				url : webContext
						+ '/extjs/panelData?method=findPanelDataById&panelName=problemForm_pagepanel&dataId='+ relatioProblemId,
				timeout : 30,
				success : function(action, form) {
					Ext.getCmp("Problem$status2Combo").initComponent();
					Ext.getCmp("Problem$submitUser2Combo").initComponent();
				}
			});
			Ext.getCmp("pr_eventCombo").setValue(relationEventId);
			Ext.getCmp('pr_eventCombo').readOnly = true;
			Ext.getCmp('pr_eventCombo').hideTrigger = true;
			Ext.getCmp("pr_eventCombo").initComponent();
			Ext.getCmp("pr_problemCombo").setValue(relatioProblemId);
			Ext.getCmp('pr_problemCombo').readOnly = true;
			Ext.getCmp('pr_problemCombo').hideTrigger = true;
			Ext.getCmp("pr_problemCombo").initComponent();
			Ext.getCmp("pr_prTypeCombo").setValue(relationProblemTypeId);
			Ext.getCmp('pr_prTypeCombo').readOnly = true;
			Ext.getCmp('pr_prTypeCombo').hideTrigger = true;
			Ext.getCmp("pr_prTypeCombo").initComponent();
		}else{
			formproblemForm_pagepanel.load({
				url : webContext
						+ '/extjs/panelData?method=findPanelDataById&panelName=problemForm_pagepanel&dataId='+ this.pdataId,
				timeout : 30,
				success : function(action, form) {
					Ext.getCmp("Problem$status2Combo").initComponent();
					Ext.getCmp("Problem$submitUser2Combo").initComponent();
				}
			});
		}
		
		var problemWin = new Ext.Window({
			    id :'problemRelationWindow',
				title : '关联问题关系',
				width : 728,
				height : 330,
				//maximizable : true,
				autoScroll:true,
				items : [prPanel,formproblemForm_pagepanel],
				bodyStyle : 'padding:2px',
				buttons : [ {
					text : '保存',	
					handler : function() { 
						if (Ext.getCmp("prPanel").getForm().isValid()) {
							var curProblemId = this.pdataId;
							var parentProblemId = Ext.getCmp('pr_problemCombo').getValue();
							var curEventId = this.EventId;
							var releventId = Ext.getCmp('pr_eventCombo').getValue();
							var problemRelationTypeId = Ext.getCmp('pr_prTypeCombo').getValue();
		                  Ext.Ajax.request({
			                    url: webContext+'/problemAction_createProblemRelation.action',
			                    params: {
			                    	   curRelId:problemReId,
						               curEventId : curEventId,
						               parentEventId : releventId,
						               currProblemId:curProblemId,
						               parentProblemId:parentProblemId,
						               problemRelationTypeId : problemRelationTypeId
					            },
			                    method:'post', 
			                    success:function(response,options){
			                    	var data = Ext.util.JSON.decode(response.responseText);
			                    	if(data.isExist=='true'){
			                    		Ext.getCmp('problemRelationGrid').getStore().reload();
				                    	Ext.getCmp('problemRelationWindow').close();
			                    	}else{
			                    		Ext.MessageBox.alert('提示','问题关系已存在！');
			                    	}
			                     },
			                    failure:function(response,options){
				                     Ext.MessageBox.alert('提示','问题关系设置失败！');
			                     }
		                 });	
		              }else{
		              Ext.MessageBox.alert('提示','带红线的框必须填写！');
		              }
				  },
					scope : this
				},{
					text : '关闭',
					handler : function() {
						problemWin.close();
					},
					scope : this
				}]

			});
		problemWin.show();
	  },
	
	  modifyProblemRelationss:function(){
		var record = this.problemRelationGrid.getSelectionModel().getSelected();
	    var records = this.problemRelationGrid.getSelectionModel().getSelections();
		        if (!record) {
			         Ext.Msg.alert("提示", "请先选择要修改或查看的的行！");
			         return;
		           }
		         if (records.length == 0) {
			      Ext.MessageBox.alert('提示', '选择一条信息！');
			      return;
		        }
		var problemReId = record.get("id");
	   	var conn = Ext.lib.Ajax.getConnectionObject().conn;
        conn.open("POST", webContext+'/problemAction_relationProblem.action?prId='+problemReId,false);
        conn.send(null);	
        var result = Ext.decode(conn.responseText);
        var relationEventId=result.relationEventId;
        var relatioProblemId=result.relatioProblemId;
        var relationProblemTypeId=result.relationProblemTypeId;
        this.operate(problemReId,relationEventId,relatioProblemId,relationProblemTypeId);
	  },
	  
	//列出日志
	  dealfile:function(){ 
	            var record = this.grid.getSelectionModel().getSelected();
				var records = this.grid.getSelectionModel().getSelections();
				if(!record){
					Ext.Msg.alert("提示","请先选择要查看的行！");
					return;
				}
				if(records.length>1){
					Ext.Msg.alert("提示","查看时只能选择一行！");
					return;
				}
				var ddId=record.get('ProblemHandleLog$id');	
		        var da = new DataAction();
		        var data=da.getSingleFormPanelElementsForEdit("problemLogform_pagpanel",ddId);
		       	for(var  i=0;i<data.length;i++){
			     if(data[i].xtype=="panel"){
					data[i].items[0].disabled=true;
					data[i].items[1].html=data[i].items[1].html.replace(/display:/g, "display:none");
            
          		}
				data[i].readOnly=true;
				data[i].hideTrigger=true;
				data[i].emptyText="";
			}
			//2010-04-20 modified by huzh for 页面修整 begin
		       // var dataform = da.split(data);
		         var dataform = this.splitOwn(data);
		    //2010-04-20 modified by huzh for 页面修整 end
		        var envForm = new Ext.form.FormPanel({
		             id:'problemloglook',
			         layout : 'table',
			         width : 580,
			         height : 230,
			         layoutConfig : {
				           columns : 2
			             },
			          defaults : {
				        bodyStyle : 'padding:5px'
			         },
			         frame : true,
			         items : dataform

		           });
		  var win = new Ext.Window({
				title : '查看问题日志',
				width : 598,
				height : 301,
				modal : true,
				//maximizable : true,
				items : envForm,
				closeAction : 'hide',
				bodyStyle : 'padding:2px',
				buttons : [
					{
					text : '关闭',
					handler : function() {
							win.close();	
					},
	
					scope : this
				}]
			  });
	       win.show();
      },  
	 //问题关联相关事件问题
    problemRelation :function(currentProblemId){
//		var da = new DataAction();
//		var plg=da.getListPanelElementsForHead("problemRationList_pagepanel");
//		var sm = new Ext.grid.CheckboxSelectionModel();
//		var columns = new Array();
//		var fields = new Array();
//		columns[0] = sm;
//		for (var i = 0; i < plg.length; i++) {
//			var headItem = plg[i];
//			var title = headItem.header;
//			var alignStyle = 'left';
//			var propertyName = headItem.dataIndex;
//			var isHiddenColumn = false;         
//            var isHidden = headItem.isHidden;
//               if(propertyName=="ProblemRelation$id"){
//                       	isHiddenColumn = true;
//                                               }   
//			var columnItem = {
//				header : title,
//				dataIndex : propertyName,
//				sortable : true,
//				hidden : isHiddenColumn,
//				align : alignStyle
//			}
//			columns[i + 1] = columnItem;
//			fields[i] = propertyName;
//		}
//		this.storeMapping = fields;
//		this.cm = new Ext.grid.ColumnModel(columns);
//		this.storeRelation= da.getPanelJsonStore("problemRationList_pagepanel", plg);
//		this.storeRelation.paramNames.sort = "orderBy";
//		this.storeRelation.paramNames.dir = "orderType";
//		this.cm.defaultSortable = true;
    	
    	var sm = new Ext.grid.CheckboxSelectionModel();// 复选框
		var cm = new Ext.grid.ColumnModel([sm,

					{header:'id',dataIndex:'id',hidden:true,sortable:true},
																
					
					{header:'关联问题编号',dataIndex:'parentProblemCisn',sortable:true},
																
					
					{header:'关联问题',width :160,dataIndex:'problem',sortable:true},
																
					
					{header:'所属事件',width :160,dataIndex:'event',sortable:true},
																
					
					{header:'问题关系类型',dataIndex:'typeName',sortable:true},
																
					
					{header:'问题状态',width :70,dataIndex:'problemStatus',sortable:true},
																
					
//					{header:'问题处理人',dataIndex:'dealUser',sortable:true}, //2010-04-20 deleted by huzh for 无处理人
																
					
					{header:'问题结束时间',dataIndex:'closeDate',sortable:true}
															
																
					
					]);
		this.storeRelation=new Ext.data.JsonStore({
				url : webContext + '/problemAction_getProblemRelByCurrProblem.action?problemId='+currentProblemId,
			//2010-04-20 modified by huzh for 无处理人 begin
				//fields : ['id', 'parentProblemCisn','problem','event','typeName','problemStatus','dealUser','closeDate'],
				fields : ['id', 'parentProblemCisn','problem','event','typeName','problemStatus','closeDate'],
			//2010-04-20 modified by huzh for 无处理人 begin
				totalProperty : 'rowCount',
				sortInfo:
				{field:'parentProblemCisn',direction:'asc'},
				root : 'data',
				id : 'id'
				
		});
    	
    	
    	
		var viewConfig = Ext.apply({
			forceFit : true
		}, this.gridViewConfig);

		this.pageBar = new Ext.PagingToolbar({
			pageSize : 10,
			store : this.storeRelation,
			displayInfo : true,
			displayMsg : '当前显示 {0}-{1}条记录 /共{2}条记录',
			emptyMsg : "无显示数据"
		});
		this.problemRelationGrid = new Ext.grid.GridPanel({
		   id : 'problemRelation',
			store : this.storeRelation,
			cm : cm,
			sm : sm,
			autoScroll : true, 
			trackMouseOver : false,
			loadMask : true,
			width : 753,
			height : 200,
			title:"问题关系【<font color=red>双击行可以查看详细信息</font>】",
			bbar : this.pageBar
		});	
		var params = {
			'mehtodCall' : 'query',
			'problem' :this.pdataId,
			'start' : 0
		
		};	
//		this.pageBar.formValue = paramrela;
		this.storeRelation.on('beforeload', function(a) {   
		      Ext.apply(a.baseParams,params);   
			});
		this.storeRelation.removeAll();
		this.storeRelation.load({
			params : params
		});
		return this.problemRelationGrid;
	},
	//获取服务目录树
	getServiceItemTree : function(){
	
		return new PagteModelTreePanel({dataId:this.EventId});
	
	
	},
	   //2010-04-20 add by huzh for 页面修整 begin
   splitOwn : function(data) {
        var labellen = 85;
        var itemlen = 200;
        var throulen = 20;
        if(Ext.isIE){
                  throulen = 450;
        }
        else{
                  throulen = 540;
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

                  if (data[i].width == null || data[i].width == 'null'
                                      || data[i].width == "") {
                            data[i].style += "width:" + itemlen + "px";
                            data[i].width = itemlen;
                  } else {
                            if (data[i].width == "9999") {//通栏  
                                      if ((i-hid+longitems) % 2 == 1) {//在右侧栏，前一栏贯通                                             
                                                longData[2 * (i - hid) - 1].colspan = 3;                                                            
                                      }
                                      else{//多占一栏
                                                longitems ++;
                                      }
                                      data[i].colspan = 3;//本栏贯通                                            
                                      data[i].width = throulen;
                                      if(data[i].xtype == "textarea"){
                                      data[i].height = 150;
                                      }
                                      data[i].style += "width:" + throulen + "px;";
                            } else {//正常长度，半栏 
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
      
    //2010-04-20 add by huzh for 页面修整 end
		//add by duxh for 配置项变更----begin---
   getModifyGrid:function(pdataId,EventId,taskId){
	    var backUrl=webContext+ '/user/event/transactionFlow/skipengineerprobleminfo.jsp?pdataId='+pdataId+"&EventId="+EventId+"&taskId="+taskId;
	    backUrl=escape(backUrl);
		var url=webContext+'/user/configItem/configItemBatchModify/configItemBatchModifyListForExteriorReadOnly.jsp?typeId='+pdataId+"&type=problem&backUrl="+backUrl;
	    var panel=new Ext.Panel({
    		id:"configItemPanel",
    		title:"配置项变更单",
    		height:250,
    		frame:true,
			html:"<IFRAME SRC='"+url+"' width='100%' height='100%' frameborder='0'></IFRAME>"
    	})
    	return panel;
   },
   //add by duxh for 配置项变更----end---
	// 初始化
	initComponent : function() {//在这里面定义的变量是全局变量
	    this.fp=this.getSearchFor();
	    this.ppp=this.problemRelation(this.pdataId);
//	    this.ServiceItemByEventTree=new DisabledTreePanel({dataId:this.EventId,pId:this.pdataId});
		var da = new DataAction();
		var prolog=da.getListPanelElementsForHead("problemLoglist_pagpanel");
		var sm =new Ext.grid.CheckboxSelectionModel();
		var columns= new Array();
		var fields = new Array();
		columns[0]=sm;
		for(var i=0;i<prolog.length;i++){
			var headItem =prolog[i];
			var title = headItem.header;//
			var alignStyle ='left';
			var propertyName = headItem.dataIndex;//
			var isHidden=false;
			if(propertyName=="ProblemHandleLog$id"){
			 isHidden =true;
			}
		var columnItem={
			header:title,
			dataIndex:propertyName,
			sortable:true,
			hidden:isHidden,   
			align:alignStyle
		}
		if(propertyName=="ProblemHandleLog$handleLog"){
			columnItem.width=200;
		}
		if(propertyName=="ProblemHandleLog$handleUser"){
			columnItem.width=200;
		}
		if(propertyName=="ProblemHandleLog$handleDate"){
			columnItem.width=100;
		}
		columns[i+1]=columnItem;
		fields[i]=propertyName;
		}
	
		this.storeMapping=fields;
		this.cm=new Ext.grid.ColumnModel(columns);
		this.storeprolog= da.getPanelJsonStore("problemLoglist_pagpanel", prolog);
//		this.storeprolog.on("load",function(store,records,opt){
//			store.each(function(record){
//				var opDate = record.get("ProblemHandleLog$handleDate");
//				opDate = opDate.substring(0,19);
//				
//			});
//		});
		this.storeprolog.paramNames.sort="orderBy";
		this.storeprolog.paramNames.dir="orderType";
		this.cm.defaultSortable=true;
		PagePanel.superclass.initComponent.call(this);
		var viewConfig = Ext.apply({//什么意思？
			forceFit : true
		}, this.gridViewConfig);
   
		this.pageBar = new Ext.PagingToolbar({
			pageSize :10, //sys_pageSize,//使用的是系统默认值
			store : this.storeprolog,
			displayInfo : true,
			displayMsg : '当前显示 {0}-{1}条记录 /共{2}条记录',
			emptyMsg : "无显示数据"
		});
		
		this.grid = new Ext.grid.GridPanel({
		    id :'problemloggrid',
			store : this.storeprolog,
			cm : this.cm,
			autoScroll : true, 
			sm : sm,
			trackMouseOver : false,
			loadMask : true,
			//y : 650,
			width:753,
			height:200,
			title:"所有日志【<font color=red>双击日志行可以查看详细信息</font>】",
			bbar : this.pageBar
		}); 
		 this.mybutton={ 
		        layout : 'table',
			    height : 'auto',
			    width : 'auto',
			    style : 'margin:6px 6px 10px 370px',
			    colspan : 4,
			    align : 'center',
			    defaults : {
				bodyStyle : 'padding:4px'
			    },
			    layoutConfig : {
				 columns : 4
			    },
			    xtype :'button',
				text : '返回',
				iconCls : 'back',
				handler : function() {
					window.location =webContext+'/user/event/transactionFlow/engineerDealEvent/eventDeal.jsp?dataId='+this.EventId+'&taskId='+this.taskId;
				},
				scope : this	
			   };
		//add by duxh for 配置项变更----begin---
		var pdataId=this.pdataId;
		var EventId=this.EventId;
		var taskId=this.taskId;
		var getModifyGrid=this.getModifyGrid;
		//add by duxh for 配置项变更----end---			   
	    this.Panel = new Ext.Panel({
			id : "Pages",
			height : 'auto',
			width:765,
			align : 'center',
			autoScroll : true,
			layout : 'table',
			border : true,
			defaults : {
				bodyStyle : 'padding:5px'
			},
			frame : true,
			autoScroll : true,
			layoutConfig : {
				columns : 1
			},
			items :[this.fp,this.grid,this.ppp,getModifyGrid(pdataId,EventId,taskId),this.mybutton]		//this.ServiceItemByEventTree, 
		});	
	    this.add(this.Panel);
	    this.problemRelationGrid.on("rowdblclick",this.modifyProblemRelationss,this);
	    this.grid.on("rowdblclick",this.dealfile,this);
		this.grid.on("headerdblclick", this.fitWidth, this);//行(row)被右击时触发
		var params = {
			'mehtodCall' : 'query',
			 problem:this.pdataId,
			'start' : 0
		};
//		this.pageBar.formValue =paramprolog; 
		this.storeprolog.on('beforeload', function(a) {   
		      Ext.apply(a.baseParams,params);   
			});
		this.storeprolog.removeAll();//删除数据存储器中的所有数据
		this.storeprolog.load({
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
