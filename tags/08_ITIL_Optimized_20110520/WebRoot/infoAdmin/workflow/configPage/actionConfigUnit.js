ActionConfigUnit = Ext.extend(Ext.Panel, {
	id : "ActionConfigUnit",
	//title : "��������",
	// layout : 'table',
	height : 420,
	// autoScroll : true,
	//align : 'center',
	//foredit : true,
	width : 600,
	//layout:'fit',
	//frame : true,
	
	defaults : {
		bodyStyle : 'padding:4px'
	},
	layoutConfig : {
		columns : 1
	},
	freshPage : function() {
		window.location.reload();
	},

	goBackUrl : function() {
		history.go(-1);
	},

	items : this.items,
	// ��ʼ��
	initComponent : function() {// �������涨��ı�����ȫ�ֱ���
		ActionConfigUnit.superclass.initComponent.call(this);// �ø����ȳ�ʼ��
		//product = '';
		removeIds = '';

		//var clazz = "com.zsgj.info.framework.workflow.entity.ActionConfigUnit"
		var da = new DataAction();
		var orderAction = new OrderAction();
		//var obj = da.getElementsForHead(clazz);
		
		var sm = new Ext.grid.CheckboxSelectionModel();
		this.store = new Ext.data.JsonStore({
			url : webContext
					+ '/workflow/processconfig.do?methodCall=getActionConfigUnit&virtualDefinitionInfoId='+this.virtualDefinitionInfoId+"&nodeId="+this.nodeId,
			root : "data",
			fields : ["id", "eventName", "actionName"]
		});
		
		var obj=[{
			header : "�¼�����",
			dataIndex : "eventName"
		}, {
			header : "action����",
			dataIndex : "actionName"
		}];

		var sm = new Ext.grid.CheckboxSelectionModel();
		var columns = new Array();

		var codes = [['entry'], ['leave']];
		var stores = new Ext.data.SimpleStore({

			fields : ['eventName'],
			data : codes

		});
		columns[0] = sm;
		for (var i = 0; i < obj.length; i++) {
			var headItem = obj[i];
			var title = headItem.header;
			var alignStyle = 'left';
			

			var propertyName = headItem.dataIndex;
			if (propertyName == "outFile" || propertyName == "cfxmlFile"
					|| propertyName == "cfrFile") {
				headItem.editor = "";
			}

			var isHidden = false;
			if (propertyName == 'id') {
				isHidden = true;
			}
			if (propertyName == 'eventName') {
				headItem.editor = new Ext.form.ComboBox({
					name : 'eventName',
					store : stores,
					triggerAction : 'all',
					displayField : 'eventName',
					valueField : 'eventName',
					mode : 'local',
					width : 200

				});

			}


			if (propertyName == 'actionName') {
				headItem.editor = new Ext.form.TextField({
					name : 'actionName'
					
				});
			}

			var columnItem = {
				header : title,
				dataIndex : propertyName,
				sortable : true,
				hidden : isHidden,
				align : alignStyle,
				editor : headItem.editor,
				renderer : headItem.renderer
			};

			columns[i + 1] = columnItem;
		}
		this.cm = new Ext.grid.ColumnModel(columns);

//		this.store = orderAction.getUpdateStore(clazz, this.processId,
//				this.nodeId);
		// this.store.paramNames.orderApplyAAP = aapId;
		this.cm.defaultSortable = true;
		
        var viewConfig = Ext.apply({
			forceFit : true
		}, this.gridViewConfig);

		this.pageBar = new Ext.PagingToolbarExt({
			pageSize :10,//ʹ�õ���ϵͳĬ��ֵ
			store : this.store,
			displayInfo : true,
			displayMsg : '��ǰ��ʾ {0}-{1}����¼ /��{2}����¼',
			emptyMsg : "����ʾ����"
		});
		
		this.grid = new Ext.grid.EditorGridPanel({
			id : 'com.actionConfigUnit',
			store : this.store,
			cm : this.cm,
			sm : sm,
			// title : '������״̬��������(�ɹ�ѡ�н����ύ��Ĭ��ȫ���ύ)',
			trackMouseOver : false,
			loadMask : true,
			autoScroll : true,
			clicksToEdit : 2,
			height : 340,
			width : 730,
			frame : true,
			bbar : this.pageBar,
			tbar : [{
				xtype : 'button',
				style : 'margin:4px 10px 4px 0',
				text : '����',
				scope : this,
				iconCls : 'add',
				handler : function() {
					var cmp = Ext.getCmp('com.actionConfigUnit');
					var store = cmp.getStore();
					if (store.recordType) {
						var rec = new store.recordType({
					//		newRecord : true
						});
						rec.fields.each(function(f) {
						//	rec.data['id']=null;
							rec.data['actionName'] = null;
							rec.data['eventName']=null;
						});
						rec.commit();
						store.add(rec);
						return rec;
					} else {
						return false;
					}

				}
			}, {
				xtype : 'button',
				style : 'margin:4px 10px 4px 0',
				text : 'ɾ��',
				iconCls : 'remove',
				scope : this,
				handler : function() {

					var cmp = Ext.getCmp('com.actionConfigUnit');
					var record = cmp.getSelectionModel().getSelected();
					var records = cmp.getSelectionModel().getSelections();
					if (!record) {
						Ext.Msg.alert("��ʾ", "����ѡ��Ҫɾ������!");
						return;
					}
					if (records.length == 0) {
						Ext.MessageBox.alert('����', '����ѡ��һ����Ϣ������ɾ��!');
						return;
					}

					Ext.Msg.confirm('��ʾ��', '��ȷ��Ҫ����ɾ��������?', function(button) {
						if (button == 'yes') {
							for (var i = 0; i < records.length; i++) {
								var record = records[i];
								var id = record.get('id');
								removeIds += id;
								removeIds += ",";
								this.store.remove(records[i]);
							}
							// ɾ����Ҫɾ��������Ϣ��ͬʱɾ��save�����е�����߼� ADD by DJ ��

							orderAction.deleteActionConfigUnitRecord(removeIds);
							// ���ɾ���б�
							removeIds = '';
						}
					}, this);
				}
			}, {
				xtype : 'button',
				style : 'margin:4px 10px 4px 0',
				text : '����',
				iconCls : 'save',
				scope : this,
				handler : function() {

					var cmp = Ext.getCmp('com.actionConfigUnit');
					var gridParam = cmp.getStore().getModifiedRecords();
					gridParam = [];
					var count = cmp.getStore().getCount();
					for (var i = 0; i < count; i++) {
						gridParam[i] = cmp.getStore().getAt(i);
					}
					for (i = 0; i < gridParam.length; i++) {
						var record = gridParam[i];
						if (record.get('eventName') == null
								|| record.get('eventName') == '') {
							Ext.Msg.alert('��ʾ', '����д�¼�����');
							return;
						}
						if (record.get('actionName') == null
								|| record.get('actionName') == '') {
							Ext.Msg.alert('��ʾ', '����action����');
							return;
						}

					}
                    var product='';
					for (i = 0; i < gridParam.length; i++) {
						product += Ext.encode(gridParam[i].data) + ",";
					}
					// ���淢������Ϣ�����ݿ���
					orderAction.saveActionConfigUnitRecord(product,this.virtualDefinitionInfoId,this.nodeId);
					// ���product
//					product = '';
//					Ext.MessageBox.alert('��ʾ', "��������Ϣ����ɹ���");
				}
			}]

		}

		);
		var param = '';
		param.start = 1;
		
		this.formValue = param;
		this.pageBar.formValue = this.formValue;
		this.store.removeAll();
		this.store.load({
			params : param
		});

		this.add(this.grid)
	}
});
