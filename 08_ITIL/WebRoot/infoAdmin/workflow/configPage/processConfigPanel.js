// ������Ϣ�б�
ProcessConfigPanel = Ext.extend(Ext.Panel, {
	id : "ProcessConfigPanel",
	title : "�����б�",
	//closable : true,
	autoScroll : true,
	// layout : 'table',
	height : 'auto',
	align : 'center',
	foredit : true,
	width : 1050,
	frame : true,
	viewConfig : {
		autoFill : true,
		forceFit : true
	},
	items : this.items,
	reset : function() {
		this.fp.form.reset();
	},
	
  
	nodeView : function() {
		var record = this.grid.getSelectionModel().getSelected();
		var records = this.grid.getSelectionModel().getSelections();
		if (!record) {
			Ext.Msg.alert("��ʾ", "����ѡ��Ҫ��ѯ�Ķ�����!");
			return;
		}
		if (records.length > 1) {
			Ext.Msg.alert("��ʾ", "ֻ��ѡ��һ�н��в鿴!");
			return;
		}
		var processName = record.get("name");
		var processId=record.get("processDefinitionId");
		
		window.location = webContext
				+ "/infoAdmin/workflow/configPage/nodeView.jsp?processName=" + processName+"&processId="+processId;
	},
	
	
	
		/* ���ӹ����ļ� */
	addRuleFile : function() {
		var record = this.grid.getSelectionModel().getSelected();
		var records = this.grid.getSelectionModel().getSelections();
		if (!record) {
			Ext.Msg.alert("��ʾ", "����ѡ��Ҫ���ӹ����ļ���������!");
			return;
		}
		if (records.length > 1) {
			Ext.Msg.alert("��ʾ", "ֻ��ѡ��һ�н��в鿴!");
			return;
		}
		var processId=record.get("processDefinitionId");
		var department = new Ext.form.ComboBox({
			id:'department',
			store : new Ext.data.JsonStore({
				url : webContext
						+ '/sysManage/userRoleAction.do?methodCall=findAllDept',
				fields : ['id', 'name'],
				root : 'data',
				sortInfo : {
					field : "id",
					direction : "ASC"
				}
			}),
			valueField : "id",
			displayField : "name",
			fieldLabel : "��������",
			emptyText : '��ѡ����������',
			mode : 'remote',
			forceSelection : true,
			hiddenName : "id",
			editable : false,
			triggerAction : 'all',
			lazyRender : true,
			typeAhead : true,
			allowBlank : true,
			name : "dept",
			selectOnFocus : true,
			width : 390
		});
		
		var definitionType = new Ext.form.ComboBox({
			id:'definitionType',
			store : new Ext.data.JsonStore({
				url : webContext
						+ '/workflow/processconfig.do?methodCall=getAllDefinitionType',
				fields : ['id', 'name'],
				root : 'data',
				sortInfo : {
					field : "id",
					direction : "ASC"
				}
			}),
			valueField : "id",
			displayField : "name",
			fieldLabel : "���̷���",
			emptyText : '��ѡ������',
			mode : 'remote',
			forceSelection : true,
			hiddenName : "id",
			editable : false,
			triggerAction : 'all',
			lazyRender : true,
			typeAhead : true,
			allowBlank : true,
			name : "type",
			selectOnFocus : true,
			width : 390
		});
		

		var writeInfo = new Ext.form.FormPanel({
			id : 'com.zsgj.writeInfo',
			height : 160,
			frame : true,
			width : 500,
			labelWidth : 70,
			labelAlign : "right",
			defaultType : "field",
			buttonAlign : 'left',
			fileUpload:true,
			items : [department,definitionType,
			    {
				id:'addRuleField',
				inputType:'file',
				width:390,
				height:30,
				name : 'ruleName',
				fieldLabel : "�������漰�Ĺ����ļ�"}]
		});

//		var addRuleFile = new Ext.form.FormPanel({
//			// title:'test',
//			id : "com.zsgj.addRuleFile",
//			height : 160,
//			frame : true,
//			width : 1000,
//			// url : webContext + '/upload',
//			labelWidth : 200,
//			labelAlign : "right",
//			defaultType : "field",
//			buttonAlign : 'left',
//			items : [{
//				id:'addRuleField',
//				name : 'ruleName',
//				fieldLabel : "�������漰�Ĺ����ļ�"
//				
//			}]
//
//		});
        
		var win = new Ext.Window({
			title : '����������Ϣ',
			width : 500,
			height : 200,
			modal : true,
			buttons : [{
				xtype : 'button',
				text : "����",
				handler : function(){
					var processType=Ext.getCmp('com.zsgj.writeInfo').getForm().findField('definitionType').getValue();
					var department=Ext.getCmp('com.zsgj.writeInfo').getForm().findField('department').getValue();
					
				Ext.getCmp('com.zsgj.writeInfo').getForm().submit({//�ͻ��˵������ύ��������
                url : webContext + '/workflow/processconfig.do?methodCall=uploadRuleFile&processType='+processType+'&department='+department+"&processId="+processId,
                params : {
						processId : processId,
					//	ruleName : ruleName
						processType : processType,
						department : department
					},
                waitTitle:"���Ժ�",
                waitMsg:"�����ύ�����ݣ����Ժ򡣡���������", 
                //���submitʧ���������@һ��function   
                failure:function(form1,action){         
                   Ext.MessageBox.alert("����ʧ��");
                },   
                success: function(form1,action){   
                   Ext.Msg.alert("��ʾ", "����ɹ�", function(button) {
						  window.location='processConfig.jsp';
						}, this);
                 }                   
             })
             
					
					
					
//					var ruleName=Ext.getCmp('com.zsgj.writeInfo').getForm().findField('addRuleField').getValue();
////					var processType=Ext.getCmp('com.zsgj.writeInfo').getForm().findField('definitionType').getValue();
////					var department=Ext.getCmp('com.zsgj.writeInfo').getForm().findField('department').getValue();
//				  	Ext.Ajax.request({
//					url : webContext + '/workflow/processconfig.do?methodCall=uploadRuleFile',
//					params : {
//						//processId : processId,
//						ruleName : ruleName
//					//	processType : processType,
//					//	department : department
//					},
//					success : function(response, options) {
//
//						Ext.Msg.alert("��ʾ", "����ɹ�", function(button) {
//						  window.location='processConfig.jsp';
//						}, this);
//					},
//					failure : function(response, options) {
//						Ext.MessageBox.alert("����ʧ��");
//					}
//				});
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
	// ����
	search : function() {
		var param = this.fp.form.getValues(false);
		
		this.formValue = param;
		this.pageBar.formValue = this.formValue;
		param.start = 1;
	
		this.store.removeAll();
		this.store.load({
			params : param
		});
	},
	getSearchForm : function() {
		return new ApplySearchForm({
			defaults : {
				bodyStyle : 'padding:4px'
			},
            width : 900,
			height :50,
			layoutConfig : {
				columns : 4
			},

			getMyItems : function() {

				var  clazz = "com.zsgj.info.framework.workflow.entity.DefinitionInfo";
				var da = new DataAction();
				
				var data = da.getElementsForQuery(clazz); 
				return da.split(data);
			}
		});
	},

	// ��ʼ�����г���������
	initComponent : function() {

//		this.definitonName = new Ext.form.TextField({
//			id : 'name',
//			name : 'definitonName',
//			fieldLabel : '��������'			
//			
//		});
//		
//		this.definitonDesc = new Ext.form.TextField({
//			id : 'description',
//			name : 'definitonDesc',
//			fieldLabel : '��������'			
//			
//		});
//		
//		this.processSearch = new Ext.form.FormPanel({
//			// title:'test',
//			id : 'com.digital.processSearch',
//			height : 50,
//			frame : true,
//			width : 900,
//			labelWidth : 100,	
//			layout:'table',
//			//buttonAlign : 'left',
//			layoutConfig : {
//				columns : 6
//			},
//			items : [
//				{html: "&nbsp;��������:&nbsp;" ,cls: 'common-text', style:'width:100;height:20;text-align:left;margin:5 0 5 0;'},
//				this.definitonName,	
//				{html: "&nbsp;��������:&nbsp;" ,cls: 'common-text', style:'width:100;height:20;text-align:left;margin:5 0 5 0;'},
//				this.definitonDesc				
//				]			
////			items : [{
////				id : 'name',
////				xtype : 'textfield',
////				width : 180,
////				name : 'name',
////				fieldLabel : "��������"
////			}, {
////				id : 'description',
////				xtype : "textfield",
////				width : 180,
////				name : 'description',
////				fieldLabel : "��������"
////			}]
//
//		});
//
//		var sm = new Ext.grid.CheckboxSelectionModel();
//		this.store = new Ext.data.JsonStore({
//			url : webContext
//					+ '/workflow/processconfig.do?methodCall=getProcess',
//			root : "data",
//			fields : ["id", "name", "description", "deptName", "version"]
//		});
//		this.colM = new Ext.grid.ColumnModel([sm,{
//			header : "��������",
//			dataIndex : "name"
//		}, {
//			header : "����",
//			dataIndex : "description"
//		},
//		{
//			header : "��������",
//			dataIndex : "deptName"
//		},
//		{
//			header : "�汾��",
//			dataIndex : "version"
//		}]);
		this.fp = this.getSearchForm();
		
		var clazz = "com.zsgj.info.framework.workflow.entity.DefinitionInfo";
		var da = new DataAction();
		var orderAction=new OrderAction();
		//var orderDataAction = new OrderDataAction();
		var obj = da.getElementsForHead(clazz);
		
		var sm = new Ext.grid.CheckboxSelectionModel();
		var columns = new Array();
		var fields = new Array();
		columns[0] = sm;
		for (var i = 0; i < obj.length; i++) {
			var headItem = obj[i];
			var title = headItem.header;
			var alignStyle = 'left';

			var propertyName = headItem.dataIndex;

			var isHidden = false;
			if (propertyName == 'id') {
				isHidden = true;
			}
			var columnItem = {
				header : title,
				dataIndex : propertyName,
				sortable : true,
				hidden : isHidden,
				align : alignStyle
			};
			columns[i + 1] = columnItem;
			fields[i] = propertyName;
		}

		this.storeMapping = fields;
		this.cm = new Ext.grid.ColumnModel(columns);

		this.store = orderAction.getJsonStore(clazz);
		this.store.paramNames.sort = "orderBy";
		this.store.paramNames.dir = "orderType";
		this.cm.defaultSortable = true;

		var viewConfig = Ext.apply({
			forceFit : true
		}, this.gridViewConfig);
		this.pageBar = new Ext.PagingToolbarExt({
			pageSize : sys_pageSize,
			store : this.store,
			displayInfo : true,
			displayMsg : '��ǰ��ʾ {0}-{1}����¼ ',
			emptyMsg : "����ʾ����"
		});
		this.grid = new Ext.grid.GridPanel({

			id : 'com.zsgj.grid',
			title:"˫��ĳ�������в鿴���нڵ�",
			layout : 'table',
			trackMouseOver : false,
			loadMask : true,
			clicksToEdit : 2,
			height : 500,
			width : 900,
			cm : this.cm,
			store : this.store,
			sm:sm,
			autoExpandColumn : 2,
			frame:true,
			tbar: [{
				text : '��ѯ',
				style : 'margin:4px 10px 4px 10px',
				pressed : true,
				handler : this.search,
				scope : this,
				iconCls : 'search'
			}, {
				text : ' ����',
				style : 'margin:4px 10px 4px 10px',
				pressed : true,
				handler : this.reset,
				scope : this,
				iconCls : 'reset'
			},{
				text : ' ����������Ϣ',
				style : 'margin:4px 10px 4px 10px',
				pressed : true,
				handler : this.addRuleFile,
				scope : this,
				iconCls : 'reset'}],
			bbar : this.pageBar
		});
		var param = {
			'start' : 1
		};
		this.formValue = param;
		this.pageBar.formValue = this.formValue;
		this.store.load({
			params : param
		});
		this.grid.on("rowdblclick", this.nodeView, this);
		this.items = [this.fp , this.grid];
		ProcessConfigPanel.superclass.initComponent.call(this);
	},
	fitWidth : function(grid, columnIndex, e) {
		var c = columnIndex;
		var w = grid.view.getHeaderCell(c).firstChild.scrollWidth;
		for (var i = 0, l = grid.store.getCount(); i < l; i++) {
			w = Math.max(w, grid.view.getCell(i, c).firstChild.scrollWidth);
		}
		grid.colModel.setColumnWidth(c, w);
	}

});
