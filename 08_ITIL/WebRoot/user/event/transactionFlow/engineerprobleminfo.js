PagePanel = Ext.extend(Ext.Panel, {
	  id : "engineer",
	  closable : true,
	  autoScroll:true,
	  viewConfig : {
		autoFill : true,
		forceFit : true
	  },
	  layout : 'absolute',
	  
	  //���������ϵ����
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
			fieldLabel : '�¼�',
			lazyRender : true,
			displayField : 'summary',
			valueField : 'id',
			emptyText : '������������ѡ��...',
			allowBlank : false,
			//typeAhead : true,
			name : 'pr_event',
			resizable : true,
			triggerAction : 'all',
			minChars : 50,
			queryDelay : 700,
			store : new Ext.data.JsonStore({
				url : webContext + '/extjs/comboDataAction?clazz=com.zsgj.itil.event.entity.Event',
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
					Ext.getCmp('pr_problemCombo').clearValue();
					
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
			fieldLabel : '��������',
			lazyRender : true,
			displayField : 'summary',
			valueField : 'id',
			emptyText : '��������б���ѡ��...',
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
				totalProperty : 'rowCount',
				root : 'data',
				id : 'id'
			}),
			pageSize : 10,
			listeners : {
				'beforequery' : function(queryEvent) {
					var summary = queryEvent.combo.getRawValue();
					var relEventId = Ext.getCmp('pr_eventCombo').getValue();
					if (queryEvent.query == '') {
						param = '';
					}
					this.store.load({
						params : {
							//2010-04-28 modified by huzh for ���˵���ɾ�������� begin
							relEventId : relEventId,
							summary : summary,
							status : "nodelete",
							start : 0
							//2010-04-28 modified by huzh for ���˵���ɾ�������� end
						}
					});
					return false;
				},
				'select':function(combo, record, index){
					var curEventId = record.get('id');
					if(curEventId == thisPdataId){
						Ext.Msg.alert("��ʾ","��ͬ���ⲻ����ӹ�ϵ��");
						Ext.getCmp('pr_problemCombo').clearValue();
					}
					//add by huzh start,ѡ���������ʱ��ʾ����������ϸ��Ϣ
                    var problemId = Ext.getCmp('pr_problemCombo').getValue();
         	    	var conn = Ext.lib.Ajax.getConnectionObject().conn;
			        conn.open("POST", webContext+'/problemAction_findProblemDetailByID.action?problemId='+problemId,false);
			        conn.send(null);	
			        var result = Ext.decode(conn.responseText);
			        Ext.getCmp("Problem$remark2").setRawValue(result.remark);
			        Ext.getCmp("Problem$summary2").setRawValue(result.summary);
			        Ext.getCmp("Problem$problemCisn2").setRawValue(result.problemCisn);
			        var submitTime = result.submitTime;
			        var sDate = submitTime.substring(0,16);
			        Ext.getCmp("Problem$submitTime2").setRawValue(sDate);
			        Ext.getCmp("Problem$contactEmail2").setRawValue(result.contactEmail);
			        Ext.getCmp("Problem$status2Combo").setRawValue(result.status);
			        Ext.getCmp("Problem$submitUser2Combo").setRawValue(result.submitUser);
			       //add by huzh end
			}
			},
			// 2010-04-20 modified by huzh begin
			initComponent : function() {
				this.store.load({
					params : {
						relEventId : Ext.getCmp('pr_eventCombo').getValue(),
						id : Ext.getCmp('pr_problemCombo').getValue(),
						start : 0
					},
					callback : function(r, options, success) {
						Ext.getCmp('pr_problemCombo').setValue(Ext.getCmp('pr_problemCombo').getValue());
					}
				});
			}
			// 2010-04-20 modified by huzh end
			
		});
		var problemRelTypeCombo = new Ext.form.ComboBox({
			xtype : 'combo',
			hiddenName : 'pr_prType',
			id : 'pr_prTypeCombo',
			width : 200,
			style : '',
			fieldLabel : '�����ϵ����',
			lazyRender : true,
			displayField : 'name',
			valueField : 'id',
			emptyText : '��������б���ѡ��...',
			allowBlank : false,
			//typeAhead : true,
			name : 'pr_prType',
			resizable : true,
			triggerAction : 'all',
			minChars : 50,
			queryDelay : 700,
			store : new Ext.data.JsonStore({
				url : webContext
						+ '/extjs/comboDataAction?clazz=com.zsgj.itil.event.entity.ProblemRelationType',
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
			title : '�����ϵ',
			layout : 'table',
			height : 'auto',
			width : 710, //2010-04-20 modified by huzh
			frame : true,
			//autoScroll : true,
			defaults : {
				bodyStyle : 'padding:5px'
			},
			layoutConfig : {
				columns : 4
			},
			items : [{html : "�����¼�:",cls : 'common-text',style : 'width:110;text-align:right'}, pr_eventCombo, 
					{html : "��������:",cls : 'common-text',style : 'width:110;text-align:right'}, pr_problemCombo, 
					{html : "��ϵ����:",cls : 'common-text',style : 'width:110;text-align:right'}, problemRelTypeCombo]
		});
		
		var formproblemForm_pagepanel = new Ext.form.FormPanel({
			id : 'problemForm_pagepanel2',
			layout : 'table',
			height : 'auto',
			width : 710, //2010-04-20 modified by huzh
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
					{name : 'Problem$files2',mapping : 'Problem$files'}]
			),
			title : "��������",
			items : [{
				html : '����״̬:',
				cls : 'common-text',
				style : 'width:110;text-align:right'
			}, new Ext.form.ComboBox({
				xtype : 'combo',
				hiddenName : 'Problem$status2',
				id : 'Problem$status2Combo',
				width : 200,
				style : '',
				fieldLabel : '����״̬',
				readOnly : true, 
				hideTrigger : true,
				lazyRender : true,
				displayField : 'name',
				valueField : 'id',
				emptyText : '��������б���ѡ��...',
				allowBlank : true,
				//typeAhead : true,
				name : 'Problem$status2',
				resizable : true,
			    triggerAction : 'all',
				minChars : 50,
				queryDelay : 700,
				store : new Ext.data.JsonStore({
					url : webContext
							+ '/extjs/comboDataAction?clazz=com.zsgj.itil.event.entity.ProblemStatus',
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
				html : '�����ύ��:',
				cls : 'common-text',
				style : 'width:110;text-align:right'
			}, new Ext.form.ComboBox({
				xtype : 'combo',
				hiddenName : 'Problem$submitUser2',
				id : 'Problem$submitUser2Combo',
				width : 200,
				style : '',
				fieldLabel : '�����ύ��',
				readOnly : true,
				hideTrigger:true,
				lazyRender : true,
				displayField : 'userName',
				valueField : 'id',
				emptyText : '������������ѡ��...',
				allowBlank : true,
				//typeAhead : true,
				name : 'Problem$submitUser2',
				resizable : true,
				triggerAction : 'all',
				minChars : 50,
				queryDelay : 700,
				store : new Ext.data.JsonStore({
					url : webContext
							+ '/extjs/comboDataAction?clazz=com.zsgj.info.framework.security.entity.UserInfo',
					fields : ['id', 'userName'],
					listeners : {
						beforeload : function(store, opt) {
							if (opt.params['Problem$submitUser2'] == undefined) {
								opt.params['userName'] = Ext
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
				html : '������ϵ���ʼ�:',
				cls : 'common-text',
				style : 'width:110;text-align:right'
			}, new Ext.form.TextField({
				fieldLabel : '������ϵ���ʼ�',
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
				html : '��������:',
				cls : 'common-text',
				style : 'width:110;text-align:right'
			}, new Ext.form.TextField({
				fieldLabel : '��������',
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
				html : '�����ύʱ��:',
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
				fieldLabel : '�����ύʱ��'
			}), {
				html : '������:',
				cls : 'common-text',
				style : 'width:110;text-align:right'
			}, new Ext.form.TextField({
				fieldLabel : '������',
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
				html : '��ע:',
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
				style : '',
				value : '',
				allowBlank : true,
				validator : '',
				fieldLabel : '��ע'
			})
			]
		});
		if (problemReId != "") {
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
		
		//��������ϵ
		var problemWin = new Ext.Window({
			    id :'problemRelationWindow',
				title : '���������ϵ',
				width : 728,
				height : 330,
				//maximizable : true,
				modal : true, //add
				autoScroll:true,
				items : [prPanel,formproblemForm_pagepanel],
				bodyStyle : 'padding:2px',
				buttons : [ {
					text : '����',	
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
			                    	   curRelId : problemReId,
						               curEventId : curEventId,
						               parentEventId : releventId,
						               currProblemId : curProblemId,
						               parentProblemId : parentProblemId,
						               problemRelationTypeId : problemRelationTypeId
					            },
			                    method:'post', 
			                    success:function(response,options){
			                    	var data = Ext.util.JSON.decode(response.responseText);
			                    	if(data.isExist=='true'){
			                    		Ext.getCmp('problemRelationGrid').getStore().reload();
				                    	Ext.getCmp('problemRelationWindow').close();
			                    	}else{
			                    		Ext.MessageBox.alert('��ʾ','�����ϵ�Ѵ��ڣ�');
			                    	}
			                     },
			                    failure:function(response,options){
				                     Ext.MessageBox.alert('��ʾ','�����ϵ����ʧ�ܣ�');
			                     }
		                 });	
		              }else{
		              Ext.MessageBox.alert('��ʾ','�����ߵĿ������д��');
		              }
				  },
					scope : this
				},{
					text : '�ر�',
					handler : function() {
						problemWin.close();
					},
					scope : this
				}]

			});
		problemWin.show();
	  },
	  
	  createProblemRelation:function(){
	  	 this.operate("","","","");
	  },
	  modifyProblemRelation:function(){
		var record = this.problemRelationGrid.getSelectionModel().getSelected();
	    var records = this.problemRelationGrid.getSelectionModel().getSelections();
	        if (!record) {
			         Ext.Msg.alert("��ʾ", "����ѡ��Ҫ�޸Ļ�鿴���У�");
			         return;
	           }
	         if (records.length != 1) {
				      Ext.MessageBox.alert('��ʾ', '��ѡ��һ�У�');
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
	  //�г�����
    getSearchFor: function() {
           var da = new DataAction();
		   var data=da.getSingleFormPanelElementsForEdit("problemDetail_pagepanel",this.pdataId);
		   for(var i=0;i<data.length;i++){
		   	//add by huzh --- start,����ʾ������
		   	if(data[i].name=="Problem$closedDate"){
		   		data.remove(data[i]);
		   	}
		   	//add by huzh --- end
	   		if(data[i].xtype=="panel"){
                data[i].items[0].disabled=true;
				data[i].items[1].html=data[i].items[1].html.replace(/display:/g, "display:none");
      		}else{
	      		data[i].readOnly=true;
				data[i].hideTrigger=true;
				data[i].emptyText="";
      		}
		   }
		   var biddata = da.split(data);
	       this.panel = new Ext.form.FormPanel({ 
	         id :'problemSkimPanel',
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
			 title : "������ϸ����",
			 items : biddata/*,
			 buttons:[{
                text : '�޸ı���',
				pressed : true,
				handler : function(){
				       if (Ext.getCmp("problemSkimPanel").getForm().isValid()) {
		              var problemPanelParam = Ext.encode(Ext.getCmp("problemSkimPanel").form.getValues(false));
	                  Ext.Ajax.request({
	                      url :webContext + '/problemAction_saveModifyProblem.action',
	                      params: { 
	                                 problemId :this.pdataId,
                                     problemPanelParam : problemPanelParam
                                   },
	                      method:'post', 
	                      success:function(){
						      Ext.MessageBox.alert("��ʾ��Ϣ��","�������ݳɹ�!");
						      window.location.reload();
						  },
	                      failure:function(response,options){
	                      	Ext.MessageBox.alert("��ʾ��Ϣ��","��������ʧ��!");
	                      }
	                  });
	                  }
				},
				scope : this
             
             }]*/
		     });
	     return this.panel;
		
	   },  

	//�г���־
	  dealfile:function(){ 
	            var record = this.grid.getSelectionModel().getSelected();
				var records = this.grid.getSelectionModel().getSelections();
				if(!record){
					Ext.Msg.alert("��ʾ","����ѡ��Ҫ�鿴���У�");
					return;
				}
				if(records.length>1){
					Ext.Msg.alert("��ʾ","�鿴ʱֻ��ѡ��һ�У�");
					return;
				}
				var ddId=record.get('ProblemHandleLog$id');	
                var clazz = "com.zsgj.itil.event.entity.ProblemHandleLog";
		        var da = new DataAction();
		        //var data=da.getElementsForEdit(clazz,dataId);
		        var data=da.getSingleFormPanelElementsForEdit("problemLogform_pagpanel",ddId);
		        //var dataform = da.split(data);
		        var dataform = this.splitOwn(data);
		        var envForm = new Ext.form.FormPanel({
		             id:'problemloglook',
			         layout : 'table',
					 width : 580, //2010-04-20 modified by huzh
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
		  var store = this.store;
		  var win = new Ext.Window({
				title : '������д��־',
				width : 598, //2010-04-20 modified by huzh
				height : 301,
				modal : true,
				//maximizable : true,
				items : envForm,
				closeAction : 'hide',
				bodyStyle : 'padding:2px',
				buttons : [{
					text : '����',
					handler : function() {
						var problemlogparam;
						if (Ext.getCmp("problemloglook").getForm().isValid()) {
	//					    var problemlogparam = Ext.encode(Ext.getCmp("problemloglook").form.getValues(false));
						     problemlogparam = Ext.encode(getFormParam('problemloglook'));
						}
	                 Ext.Ajax.request({
	                      url :webContext + '/problemAction_saveProblemLog.action',
	                      params: {  eid:this.pdataId,
                                     problemlogparam:problemlogparam},
	                      method:'post',  
	                      success:function(){
						     Ext.getCmp('problemloggrid').store.reload();
						     win.close()
						},
	                      failure:function(response,options){
	                      	Ext.MessageBox.alert("��ʾ","��������ʧ�ܣ�");
	                      }
	                  });
					},scope : this
				},
					{
					text : '�ر�',
					handler : function() {
							win.close();	
					},
					listeners: {
						'beforeclose':  function(p) {
							return true;
						}
					},
					scope : this
				}]
			  });
	        win.show();
              },
       //ɾ����־
      remove : function() {
		var clazz = "com.zsgj.itil.event.entity.ProblemHandleLog";
		var record = this.grid.getSelectionModel().getSelected();
		var records = this.grid.getSelectionModel().getSelections();
		if (!record) {
			Ext.Msg.alert("��ʾ", "����ѡ��Ҫɾ�����У�");
			return;
		}
		if (records.length == 0) {
			Ext.MessageBox.alert('��ʾ', '����ѡ��һ����Ϣ������ɾ����');
			return;
		}
		var id = new Array();
		var da = new DataAction();
		var firm  =Ext.Msg.confirm('��ʾ', '��ȷ��Ҫ����ɾ��������', function(button) {
			if (button == 'yes') {
				for(var i=0;i<records.length;i++){	
					id[i] = records[i].get("ProblemHandleLog$id");
				Ext.Ajax.request({
						url : webContext
								+ '/extjs/dataAction?method=remove&clazz='+clazz,
						params : {id:id[i]},
						timeout:100000, 
						success:function(response){
	                       var r =Ext.decode(response.responseText);
	                       if(!r.success){
	                       		Ext.Msg.alert("��ʾ","����ɾ��ʧ�ܣ�",function(){
	                       				
	                       		});
	                       }  
	                       this.storeprolog.reload();  
	                   },scope:this
						
					});
				}
			}
		}, this);

	  },
	  
	  //������־
   create : function() {
		var da = new DataAction();
		var data=da.getPanelElementsForAdd("problemLogform_pagpanel");
		//var dataform = da.split(data);
		var dataform = this.splitOwn(data);
		var envForm = new Ext.form.FormPanel({
		    id:'problemlog',
			layout : 'table',
			width : 580,
			height : 230,
			layoutConfig : {
				columns : 4
			},
			defaults : {
				bodyStyle : 'padding:5px'
			},
			frame : true,
			items : dataform

		});
		var store = this.store;
		var win = new Ext.Window({
				title : '�����־',
				width : 598,
				height : 301,
				modal : true,
				//maximizable : true,
				items : envForm,
				closeAction : 'hide',
				bodyStyle : 'padding:2px',
				buttons : [{
					text : '����',
					id:"saveRecord",
					handler : function() {
						Ext.getCmp("saveRecord").disable();
						if (Ext.getCmp("ProblemHandleLog$handleLog").getValue().trim()!="") {
//					          var problemlogparam = Ext.encode(Ext.getCmp("problemlog").form.getValues(false));
					           var problemlogparam = Ext.encode(getFormParam('problemlog'));
	                          Ext.Ajax.request({
	                               url :webContext + '/problemAction_saveProblemLog.action',
	                               params: {  
	                                     eid:this.pdataId,
                                         problemlogparam:problemlogparam
                                         },
	                               method:'post',  
	                               success:function(){
						                Ext.getCmp('problemloggrid').store.reload();
						                win.close()
						           },
	                               failure:function(response,options){
	                      	           Ext.MessageBox.alert("��ʾ","��������ʧ�ܣ�");
	                               }
	                          });
	                    }else{
	                         Ext.MessageBox.alert("��ʾ","����д�����¼��");
	                         Ext.getCmp("saveRecord").enable();
	                  }
				    	},
					scope : this

				}, {
					text : '�ر�',
					handler : function() {
						win.hide();
					},
					scope : this
				}]

			});
		win.show(); 
    },
	//ɾ�������ϵ
	removeProblem :function(){
	  var clazz = "com.zsgj.itil.event.entity.ProblemRelation";
		var record = this.problemRelationGrid.getSelectionModel().getSelected();
		var records = this.problemRelationGrid.getSelectionModel().getSelections();
		if (!record) {
			Ext.Msg.alert("��ʾ", "����ѡ��Ҫɾ�����У�");
			return;
		}
		if (records.length == 0) {
			Ext.MessageBox.alert('��ʾ', '����ѡ��һ����Ϣ����ɾ����');
			return;
		}
		var id = new Array();
		var da = new DataAction();
		 var firm  =Ext.Msg.confirm('��ʾ', '��ȷ��Ҫ����ɾ��������', function(button) {
		      	    if (button == 'yes') {
				        for(var i=0;i<records.length;i++){	
					        id[i] = records[i].get("id");
				      Ext.Ajax.request({
						  url : webContext + '/problemAction_removeProblemDoubleRel.action',
						  params : {id:id[i]},
						  timeout:100000, 
						  success:function(response){
	                          var r =Ext.decode(response.responseText);
//	                          if(!r.success){
	                       		Ext.Msg.alert("��ʾ","����ɾ���ɹ���",function(){
	                       				  Ext.getCmp('problemRelationGrid').store.reload(); 
	                       		});
//	                         }  
	                    
	                      },scope:this
						
					     });
				    }
			       }
		          }, this);
	},
	 //�����������¼�����
    problemRelation :function(currentProblemId){
		
		var sm = new Ext.grid.CheckboxSelectionModel();// ��ѡ��
		var cm = new Ext.grid.ColumnModel([sm,

					{header:'id',dataIndex:'id',hidden:true,sortable:true},
																
					
					{header:'����������',dataIndex:'parentProblemCisn',sortable:true},
																
					
					{header:'��������',width :160,dataIndex:'problem',sortable:true},
																
					
					{header:'�����¼�',width :160,dataIndex:'event',sortable:true},
																
					
					{header:'�����ϵ����',dataIndex:'typeName',sortable:true},
																
					
					{header:'����״̬',width :70,dataIndex:'problemStatus',sortable:true},
																
					
//					{header:'���⴦����',dataIndex:'dealUser',sortable:true}, //2010-04-20 deleted by huzh for �޴�����
																
					
					{header:'�������ʱ��',dataIndex:'closeDate',sortable:true}
															
																
					
					]);
		this.storeRelation=new Ext.data.JsonStore({
				url : webContext + '/problemAction_getProblemRelByCurrProblem.action?problemId='+currentProblemId,
			//2010-04-20 modified by huzh for �޴����� begin
//				fields : ['id', 'parentProblemCisn','problem','event','typeName','problemStatus','dealUser','closeDate'],
				fields : ['id', 'parentProblemCisn','problem','event','typeName','problemStatus','closeDate'],
			//2010-04-20 modified by huzh for �޴����� begin
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
			displayMsg : '��ǰ��ʾ {0}-{1}����¼ /��{2}����¼',
			emptyMsg : "����ʾ����"
		});
		this.problemRelationGrid = new Ext.grid.GridPanel({
		   id : 'problemRelationGrid',
			store : this.storeRelation,
			cm : cm,
			sm : sm,
            frame : true,
			trackMouseOver : false,
			collapsible : true,
			autoScroll : true, 
			loadMask : true,
			//y : 650,
			width : 753,
			height : 200,
			title : "�����ϵ",
			tbar : ['   ', {
				text : '��������ϵ',
				handler : this.createProblemRelation,
				scope : this,
				iconCls : 'add'
			},'-',{
				text : '�޸������ϵ',
				handler : this.modifyProblemRelation,
				scope : this,
				iconCls : 'edit'
			},'-',{
				text : 'ɾ�������ϵ',
				handler : this.removeProblem,
				scope : this,
				iconCls : 'remove'
			}],
			bbar : this.pageBar
		});	
		var params = {
			'mehtodCall' : 'query',
			'problem' :this.pdataId,
			'start' : 0
		
		};
		
		this.storeRelation.on('beforeload', function(a) {   
					      Ext.apply(a.baseParams,params);   
			});
		this.storeRelation.removeAll();
		this.storeRelation.load({
			params : params
		});
		return this.problemRelationGrid;
	},
	//add by duxh for ��������----begin---
   getModifyGrid:function(pdataId,EventId,taskId){
	    var backUrl=webContext+ '/user/event/transactionFlow/engineerprobleminfo.jsp?pdataId='+pdataId+"&EventId="+EventId+"&taskId="+taskId;
	    backUrl=escape(backUrl);
		var url=webContext+'/user/configItem/configItemBatchModify/configItemBatchModifyListForExterior.jsp?typeId='+pdataId+"&type=problem&backUrl="+backUrl;
	    var panel=new Ext.Panel({
    		id:"configItemPanel",
    		title:"����������",
    		height:250,
    		frame:true,
			html:"<IFRAME SRC='"+url+"' width='100%' height='100%' frameborder='0'></IFRAME>"
    	})
    	return panel;
   },
   //add by duxh for ��������----end---
   //2010-04-20 add by huzh for ҳ������ begin
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
                            if (data[i].width == "9999") {//ͨ��  
                                      if ((i-hid+longitems) % 2 == 1) {//���Ҳ�����ǰһ����ͨ                                             
                                                longData[2 * (i - hid) - 1].colspan = 3;                                                            
                                      }
                                      else{//��ռһ��
                                                longitems ++;
                                      }
                                      data[i].colspan = 3;//������ͨ                                            
                                      data[i].width = throulen;
                                      if(data[i].xtype == "textarea"){
                                      data[i].height = 150;
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
    //2010-04-20 add by huzh for ҳ������ end
	// ��ʼ��
	initComponent : function() {
	    this.fp=this.getSearchFor();
	    this.ppp=this.problemRelation(this.pdataId);
//	    this.ServiceItemByEventId=new PagteModelTreePanel({dataId:this.EventId,pId:this.pdataId});
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
		//this.store = da.getJsonStore(clazz);
		this.storeprolog= da.getPanelJsonStore("problemLoglist_pagpanel", prolog);
		this.storeprolog.paramNames.sort="orderBy";
		this.storeprolog.paramNames.dir="orderType";
		this.cm.defaultSortable=true;
		PagePanel.superclass.initComponent.call(this);
		var viewConfig = Ext.apply({//ʲô��˼��
			forceFit : true
		}, this.gridViewConfig);
   
		this.pageBar = new Ext.PagingToolbar({
			pageSize :10, //sys_pageSize,//ʹ�õ���ϵͳĬ��ֵ
			store : this.storeprolog,
			displayInfo : true,
			displayMsg : '��ǰ��ʾ {0}-{1}����¼ /��{2}����¼',
			emptyMsg : "����ʾ����"
		});
		this.grid = new Ext.grid.GridPanel({
		    id :'problemloggrid',
			store : this.storeprolog,
			cm : this.cm,
			sm : sm,
			collapsible : true,
			trackMouseOver : false,
			loadMask : true,
			autoScroll : true, 
			width:753,
            frame:true,                  
			height:200,
			title:"������־",
			tbar : ['   ', {
				text : '������־',
				handler : this.create,
				scope : this,
				iconCls : 'add'
			},'-',{
				text : '�޸���־',
				handler : this.dealfile,
				scope : this,
				iconCls : 'edit'
			},'-',{
				text : 'ɾ����־',
				handler : this.remove,
				scope : this,
				iconCls : 'remove'
			}
			],
			bbar : this.pageBar
		}); 
		 this.buttons = {
			layout : 'table',
			height : 'auto',
			width : 'auto',
			colspan : 4,
			style : 'margin:4px 6px 4px 300px',
			align : 'center',
			layoutConfig : {
				columns : 4
			},    
			defaults : {
				bodyStyle : 'padding:4px'
			},
			items : [{
			        xtype :'button',
			        id :'problemOverButton',
			        iconCls : "save",
					text : '��������',
					handler : function() {
					   Ext.getCmp("problemOverButton").disable(); 
					  var firm = Ext.Msg.confirm('��ʾ', '��ȷ��Ҫ����������', function(
							button) {
						 if (button == 'yes') {
						 Ext.Ajax.request({
	                      url :webContext + '/problemAction_saveProblemStatus.action',
	                      params: {  
	                                eid:this.pdataId
                                     },
	                      method:'post',  
	                      success:function(response,options){
	                      	//modified by huzh in 20100311 --- start
	                      	  var res =Ext.decode(response.responseText);
	                       if(res.success){
	                      	   Ext.getCmp("problemOverButton").enable();
						       window.location =webContext+'/user/event/transactionFlow/engineerDealEvent/eventDeal.jsp?dataId='+this.EventId+'&taskId='+this.taskId;
	                      }else{
	                      Ext.MessageBox.alert("��ʾ","��������ʧ�ܣ�����������������������������δ���������");//modified 
	                        Ext.getCmp("problemOverButton").enable(); 
	                      }
	                      //modified by huzh in 20100311 --- end 
	                      },
                          scope:this,
	                      failure:function(response,options){
	                      	Ext.MessageBox.alert("��ʾ","ϵͳ�쳣��");//modified 
	                        Ext.getCmp("problemOverButton").enable(); 
	                      }
	                  });
	                  		}else{
	                  			Ext.getCmp("problemOverButton").enable();
	                  		}
					}, this);
					},
					scope : this
				},{
				    xtype :'button',
				    iconCls : 'back',
					text : '����',
					handler : function() {					
						window.location =webContext+'/user/event/transactionFlow/engineerDealEvent/eventDeal.jsp?dataId='+this.EventId+'&taskId='+this.taskId;
					},
					scope : this
				}]
		}
		//add by duxh for ��������----begin---
		var pdataId=this.pdataId;
		var EventId=this.EventId;
		var taskId=this.taskId;
		var getModifyGrid=this.getModifyGrid;
		//add by duxh for ��������----end---
	    this.Panel = new Ext.Panel({
			id : "Pages",
			height : 'auto',
			width:767,
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
			items :[this.fp,this.grid,this.ppp,getModifyGrid(pdataId,EventId,taskId),this.buttons]//this.ServiceItemByEventId
		});	
	   this.add(this.Panel);
	   this.problemRelationGrid.on("rowdblclick",this.modifyProblemRelation,this);
	   this.grid.on("rowdblclick",this.dealfile,this);
		this.grid.on("headerdblclick", this.fitWidth, this);//��(row)���һ�ʱ����
		var params= {
			'mehtodCall' : 'query',
			problem : this.pdataId,
			  start   : 0
		};
//		this.pageBar.formValue =paramprolog; 
		this.storeprolog.on('beforeload', function(a) {   
					      Ext.apply(a.baseParams,params);   
			});
		this.storeprolog.removeAll();//ɾ�����ݴ洢���е���������
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
