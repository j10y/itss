Savesolution = Ext.extend(Ext.Panel, {
	id : "PageTemplates",
	layout : 'table',
	foredit : true,
	frame : true,
	//autoScroll : true,
	defaults : {
		bodyStyle : 'padding:4px'
	},
	viewConfig : {// 自适应填充
		autoFill : true,
		forceFit : true
	},
	layoutConfig : {
		columns : 1
	},
	getTreePanel : function() {
		var loader = new Ext.tree.TreeLoader({
			url : webContext + '/problemAction_queryTreeData.action'
		});
		var treePanel = new Ext.tree.TreePanel({
			region : "east",
			loader : loader,
			id : 'treepanel',
			border : false,
			width : 290,
			height : 'auto',
			autoScroll : true,
			rootVisible : false,
			root : new Ext.tree.AsyncTreeNode({
				text : '当前事件',
				id : "root",
				expanded : true
			})
		});
		loader.on(
			'beforeload', function(treeloader, node) {
				treeloader.baseParams = {
					eventId : this.eventId
			};
		}, this);
        var comboxWithTree = new Ext.form.ComboBox({
			store : new Ext.data.SimpleStore({
				fields : [],
				data : [[]]
			}),
			editable : false,
			//fieldLabel : "问题日志",
            width : 484,
			mode : 'local',
			resizable : true,
			triggerAction : 'all',
			maxHeight : 'auto',
			tpl : "<tpl for='.'><div style='height:200px'><div id='app'></div></div></tpl>",
			selectedClass : '',
			onSelect : Ext.emptyFn
		});             
		treePanel.on('click', function(node) {
			var treeid = node.id;
			if(treeid.indexOf('_P') != -1 || treeid == 'root')
				   return;
			treeid = treeid.substring(0,treeid.length-2);
			var url = webContext
					+ '/problemAction_queryLogData.action?problemId=' + treeid;
			var conn = Ext.lib.Ajax.getConnectionObject().conn;
			conn.open("post", url, false);
			conn.send(null);
			var data = "";
			if (conn.status == "200") {
				var responseText = conn.responseText;
				if (responseText == '' || responseText == null) {
					Ext.MessageBox.alert("提示", "数据库数据为空！");
					data = "";
				} else {
					responseText = encodeReturns(responseText);
					data = eval("(" + responseText + ")");
				}
			}  
				 if (data.problemlog[0].problemlogdata != 'nolog') {
				    Ext.getCmp('descn1').enable();
				    	Ext.getCmp('descn1').setValue(data.problemlog[0].problemlogdata);
                    Ext.getCmp('postButton').enable();
				 } else {
					 Ext.getCmp('descn1').setValue("");
					 Ext.getCmp('descn1').disable();
					 Ext.getCmp('postButton').disable();
				 }
               comboxWithTree.collapse();
			}, this);
			comboxWithTree.on('expand', function() {
				treePanel.expandAll();
				treePanel.render('app');
			});
		return comboxWithTree;

	},
	 splitOwn: function(data) {
	 	var labellen = 90; //2010-04-21 modified by huzh
		var itemlen = 200;
        var throulen = 530;
        if(Ext.isIE){
                throulen = 400;
        }
        else{
                throulen = 430;
        }
		if (data == null || data.length == 0) {
			return null;
		}
		var hid = 0;
		var hidd = new Array();
		var longData = new Array();
		var longitems = 0;
		//alert(this.dataId);         
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

			//alert(data[i].width+data[i].name);
			if (data[i].width == null || data[i].width == 'null'
					|| data[i].width == "") {
				data[i].style += "width:" + itemlen + "px";
				data[i].width = itemlen;
			} else {
				if (data[i].width == "9999") {//通栏  
					// alert("data");
					if ((i - hid + longitems) % 2 == 1) {//在右侧栏，前一栏贯通                                             
						longData[2 * (i - hid) - 1].colspan = 3;
					} else {//多占一栏
						longitems++;
					}
					data[i].colspan = 3;//本栏贯通                                            
					data[i].width = throulen;
					if (data[i].xtype == "textarea") {
						data[i].height = 150;
					}
					data[i].style += "width:" + throulen + "px;";
				} else {//正常长度，半栏 
					data[i].style += "width:" + itemlen + "px";
					data[i].width = itemlen;
				}
			}
			//alert(data[i].width+data[i].name);
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
	items : this.items,
	/*
	 * clientvalidation 初始化
	 */
	initComponent : function() {
		var taskId = this.taskId;
		var serviceItem_Id = this.serviceItemId;
		var items = new Array();
		this.submitCheckBox=new Ext.form.Checkbox({
			id:'submitCheckBox',  
			xtype:"checkbox",
			name:"Know$SubmitFlag",
			hiddenName : 'Know$SubmitFlagCB',
			fieldLabel:"是否提交知识",
			boxLabel:"是",
			checked:false
		});
		this.submitCheckBox.on("check", function(field) {
			var value = field.getValue();
			if (value == false) {
				this.enabledValue.setValue("no");
				return;
			} else if (value == true) {
				this.enabledValue.setValue("yes");
				return;
			}
		}, this);
		this.enabledValue = new Ext.form.Hidden({
			id : 'enabledHid',
			name : 'enabled',
			value : "no"
		});
		this.dealTypeCombo=new Ext.form.ComboBox();
			
			var submitKnowButtons=new Ext.Panel({
				height : 23,
				layout : 'table',
				width :557,
				colspan : 4,
				layoutConfig : {
					 columns:9
				  },
				items:[{
						html : "处理方式:",
						cls : 'common-text',
						width : 73,
						style : 'width:100;text-align:right;'
					}, 
					new Ext.form.ComboBox({
							xtype : 'combo',
							name : 'Event$eventDealType',
							hiddenName : 'Event$eventDealType',
							id : 'Event$eventDealTypeCombo',
							width : 150,
							fieldLabel : '事件处理方式',
							lazyRender : true,
							forceSelection :true,
							allowBlank : true,
							triggerAction : 'all',
							minChars : 50,
							queryDelay : 700,
							mode : 'local',
							allowBlank : true,
							store : new Ext.data.SimpleStore({
								fields : ['typeId', 'typeName'],
								data : [['1', '电话'], ['2', '电话+远程'], ['3', '现场'], ['4', '邮件'],[]]
							}),
							emptyText : '请选择...',
							valueField : 'typeId',
							displayField : 'typeName',
							value :"1",//默认选择“电话”
							listeners : {
								'expand' : function(combo) {
									combo.reset();
								}
							}
						}),
						{
						html : "是否提交知识:",
						cls : 'common-text',
						width : 80,
						style : 'width:100;text-align:right;margin-left:30px'
					}, this.submitCheckBox,this.enabledValue]
				});
			var kData=[{
					html : '问题类型:',
					cls : 'common-text',
					style : 'width:80;text-align:right'
				}, new Ext.form.ComboBox({
					xtype : 'combo',
					hiddenName : 'Knowledge$knowProblemType',
					id : 'Knowledge$knowProblemTypeCombo',
					width : 200,
					fieldLabel : '问题类型',
					lazyRender : true,
					displayField : 'name',
					valueField : 'id',
					forceSelection :true,
					emptyText : '请从下拉列表中选择...',
					allowBlank : true,
					name : 'Knowledge$knowProblemType',
					triggerAction : 'all',
					minChars : 50,
					queryDelay : 700,
					store : new Ext.data.JsonStore({
						url : webContext
								+ '/extjs/comboDataAction?clazz=com.digitalchina.itil.knowledge.entity.KnowProblemType',
						fields : ['id', 'name'],
						totalProperty : 'rowCount',
						root : 'data',
						id : 'id'
					}),
					pageSize : 10,
					listeners : {
						'beforequery' : function(queryEvent) {
							var serviceitem=serviceItem_Id;
							if(serviceitem==""){
								Ext.MessageBox.alert("提示","请先从下拉列表中选择服务项！");
								return false;
							}
							var param = queryEvent.combo.getRawValue();
							if (queryEvent.query == '') {
								param = '';
							}
							this.store.baseParams={
								name : param,
								serviceItem : serviceitem,
								deleteFlag : 0
							}
							this.store.load({
								params:{
									start:0
								}
							});
							return false;
						}
					},
					initComponent : function() {
						this.store.load({
							params : {
								id : Ext.getCmp('Knowledge$knowProblemTypeCombo').getValue(),
								start : 0
							},
							callback : function(r, options, success) {
								Ext.getCmp('Knowledge$knowProblemTypeCombo').setValue(Ext.getCmp('Knowledge$knowProblemTypeCombo').getValue());
							}
						});
					}
				}),{
					html : '具体问题:',
					cls : 'common-text',
					style : 'width:110;text-align:right'
				}, new Ext.form.TextField({
					fieldLabel : '具体问题',
					xtype : 'textfield',
					id : 'Knowledge$summary',
					name : 'Knowledge$summary',
					width : 200,
					allowBlank : true
				}),{
					html : '解决方法:',
					cls : 'common-text',
					style : 'width:80;text-align:right'
				},new Ext.form.FCKeditor({
					height : 100,
					colspan : 3,
					id:'Knowledge$resolvent',
					name : 'Knowledge$resolvent',
					allowBlank : true,
					width : 493
				}),{
						html : "处理方式:",
						cls : 'common-text',
						width : 80,
						style : 'width:100;text-align:right;'
					}, 
					new Ext.form.ComboBox({
							xtype : 'combo',
							name : 'Event$eventDealType',
							hiddenName : 'Event$eventDealType',
							id : 'Event$eventDealTypeCombo',
							width : 200,
							fieldLabel : '事件处理方式',
							lazyRender : true,
							forceSelection :true,
							allowBlank : true,
							triggerAction : 'all',
							minChars : 50,
							queryDelay : 700,
							mode : 'local',
							allowBlank : true,
							store : new Ext.data.SimpleStore({
								fields : ['typeId', 'typeName'],
								data : [['1', '电话'], ['2', '电话+远程'], ['3', '现场'], ['4', '邮件'],[]]
							}),
							emptyText : '请选择...',
							valueField : 'typeId',
							displayField : 'typeName',
							listeners : {
								'expand' : function(combo) {
									combo.reset();
								}
							}
						}),
						{
						html : "是否提交知识:",
						cls : 'common-text',
						width : 80,
						style : 'width:80;text-align:right;margin-left:30px'
					}, this.submitCheckBox,this.enabledValue];
		this.Treepanel = this.getTreePanel();
		var descn1 = new Ext.form.TextArea({
			name : "descn1",
			id : 'descn1',
			width : 482,
			height : 120
		});
		var EventSolution = new Ext.form.FormPanel({
			id : 'EventSolution',
			width : 656,
			frame : true,
			collapsible : true,
			defaults : {
				bodyStyle : 'padding:5px'
			},
            border:false,                  
			items : [{
				xtype : 'fieldset',
				title : '参考问题日志',
				width : 640,
				id : 'base',
				layout : 'table',
                layoutConfig : {
                    columns : 2
                },
				defaults : {
					bodyStyle : 'padding:5px'
				},
				
				items : [{
				html : "问题日志：",
				cls : 'common-text',
				style : 'width:90;text-align:right'
			},this.Treepanel,{
				html : "日志描述：",
				cls : 'common-text',
				style : 'width:90;text-align:right'
			},descn1],
                buttons:[{text : '复制到下面',
                              scope : this,
                              style : 'margin:4px 10px 4px 0',
                              id : 'postButton',
                              width:100,
                              handler : function() {
                                var logvalue= Ext.getCmp('descn1').getValue();//新的日志
                                var message=Ext.getCmp('Knowledge$resolvent').getValue();//老的日志
                                var allmessage="";
                                if(message==""){
                                  allmessage=logvalue;
                                }else{
                                  allmessage=message+"<br>"+logvalue;
                                }
                                  allmessage=encodeHtmlReturns(allmessage);

                                 Ext.getCmp('Knowledge$resolvent').setValue(allmessage);
                                
                              }}]
			}, {
				xtype : 'fieldset',
				title : '解决方案基本信息',
				width : 640,
				id : "basemessage",
				layoutConfig : {
					columns :4
				},
				layout : 'table',
				items : kData,
				buttons : [{
					style : 'margin:4px 10px 4px 0',
					text : '保存并结束事件',
					scope : this,
					id : 'postButton',
					handler : function() {
					    Ext.getCmp("postButton").disable();
						if (Ext.getCmp("EventSolution").getForm().isValid()) {
							var panelparam = Ext.encode(getFormParam('EventSolution'));
							var resolve=Ext.encode( Ext.getCmp('Knowledge$resolvent').getValue());
							Ext.Ajax.request({
								url : webContext+ '/eventAction_saveEventSolution.action',
								params : {
									panelparam : panelparam,
									serviceItemId : serviceItem_Id,
									resolvent:resolve.substring(1,resolve.length-1),
									eventId : this.eventId
								},
								success : function(response, options) {
									Ext.MessageBox.alert("提示", "保存成功！",function(){
										 	Ext.Ajax.request({
												url: webContext + '/extjs/workflow?method=getDataFromPage&taskId=' + taskId + '&next=touserconfirm',
												method:'post', 
												success:function(response,options){
												},
												failure : function(response,options) {
													Ext.MessageBox.alert("提示", "启动工作流失败！");
												}
										 	});
										 	window.parent.parent.auditContentWin.close();
									});
									
								},
								// add in 2009-10-20 by duxh---begin----
								failure : function(response, options) {
									Ext.MessageBox.alert("提示","保存失败！");
									Ext.getCmp("postButton").enable();
								}
							});

						} else {
							Ext.MessageBox.alert("提示",
									"带红线的文本为必填项，否则无法提交！",function(){
									Ext.getCmp("postButton").enable();
									});
						}

					}
				}]

			}]
		});
		

		this.items = EventSolution;
		Savesolution.superclass.initComponent.call(this);
	}
})
