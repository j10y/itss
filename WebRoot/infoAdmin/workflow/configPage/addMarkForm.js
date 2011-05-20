function test(){
	addMarkForm.saveParams();
}

AddMarkForm = Ext.extend(Ext.Panel, {
	id : "AddMarkForm",
	height : 400,
	// autoScroll : true,
	align : 'center',
	foredit : true,
	width : 600,
	// frame : true,
	fitWidth : function(grid, columnIndex, e) {
		var c = columnIndex;
		var w = grid.view.getHeaderCell(c).firstChild.scrollWidth;
		for (var i = 0, l = grid.store.getCount(); i < l; i++) {
			w = Math.max(w, grid.view.getCell(i, c).firstChild.scrollWidth);
		}
		grid.colModel.setColumnWidth(c, w);
	},
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
	getNextNodeInfo : function(taskId) {
		var url = webContext
				+ '/workflow/assginAndAddMark.do?methodCall=getNextNodeInfo&&taskId='
				+ taskId;
		var conn = Ext.lib.Ajax.getConnectionObject().conn;
		conn.open("post", url, false);
		conn.send();
		// alert(conn.responseText)
		if (conn.status == "200") {
			var responseText = conn.responseText;
			// responseText = clearReturn(responseText);
			var data = Ext.decode(responseText);
			return data
		} else {
			return 'no result';
		}
	},
	
	saveParams : function(){
		
		var info = '';
					var cmp = Ext.getCmp('com.addMarkForm');
					var gridParam = cmp.getStore().getModifiedRecords();//�õ��ϴ��ύʱ�����б��޸Ĺ��ļ�¼
					gridParam = [];
					var count = cmp.getStore().getCount();
					for (var i = 0; i < count; i++) {
						gridParam[i] = cmp.getStore().getAt(i);
					}
					for (i = 0; i < gridParam.length; i++) {
						var record = gridParam[i];
					}
					for (i = 0; i < gridParam.length; i++) {
						var record = gridParam[i];
						if (record.get("sequence") == null
								|| record.get("sequence") == '') {
							Ext.Msg.alert('��ʾ', '˳����Ϊ��');
							return;
						}
					}
					var nodeDesc = '';
					var userId = '';
					for (i = 0; i < gridParam.length; i++) {
						if (i == 0) {
							//nodeDesc = Ext.encode(gridParam[i].get("nodeDesc"));
							//add by guangsa for ��߼�ǩЧ�� in 20090720 begin
							  info = info.concat(this.taskId+":");
							//add by guangsa for ��߼�ǩЧ�� in 20090720 end
							
						}
						userId = gridParam[i].get("userId");
						info = info.concat(userId);
						info=info.concat("+").concat(gridParam[i].get("sequence"));
						info=info.concat("+").concat(gridParam[i].get("typeId")).concat(",");
					}
					if (info.lastIndexOf(",") == info.length - 1) {
						info = info.substring(0, info.lastIndexOf(","));
					}
					Ext.Ajax.request({
						url : webContext + '/extjs/workflow?method=addMarkUsersInfo',
						params :{
							taskId : this.taskId,
							addMarkUsers : info
						},
						success : function(response,options) {
							var result = Ext.decode(response.responseText);
							if(result.success){
							  Ext.Msg.alert('��ʾ', '����ɹ�');
							  var store=Ext.getCmp("com.addMarkForm").getStore();
							  store.load();
							}
						},
						failure : function(response,options) {
							var result = Ext.decode(response.responseText);
							if(!result.success){
							 Ext.alert('��ʾ', '����ʧ��');
							}
						}
					});
	
	},
	getUserName : function(userId) {
		var url = webContext
				+ '/workflow/assginAndAddMark.do?methodCall=getUserName&userId='
				+ userId;
		var conn = Ext.lib.Ajax.getConnectionObject().conn;
		conn.open("post", url, false);
		conn.send();
		if (conn.status == "200") {
			var responseText = conn.responseText;
			var data = Ext.decode(responseText);
			var realName = data.realName;
			return realName
		} else {
			return 'no result';
		}
	},

	addMarkUser : function() {
		var orderAction = new OrderAction();
		var nodeStore = orderAction.getNodeStore(this.taskId);
		var userStore = orderAction.getUserStore();
		var sequence = new Ext.form.TextField({
			xtype : 'textfield',
			id : 'sequenceId',
			name : 'sequence',
			labelWidth : 60,
			style : '',
			width : '200',
			allowBlank : true,
			validator : '',
			vtype : '',
			regex : /^[0-9]+$/
		});

		var clazz = "com.zsgj.info.framework.security.entity.UserInfo";

		var userName = new Ext.form.ComboBox({
			id : 'com.userName.combo',
			store : new Ext.data.JsonStore({
				url : webContext
						+ '/workflow/assginAndAddMark.do?methodCall=getUserNames&displayField=realName&clazz='
						+ clazz,
				fields : ['id', 'realName'],
				totalProperty : 'rowCount',
				root : 'data',
				sortInfo : {
					field : "id",
					direction : "ASC"
				},
				listeners : {
					beforeload : function(store, opt) {
						if (opt.params['realName'] == undefined) {
							opt.params['realName'] = unicode(userName.defaultParam);
						}else{
							opt.params['realName'] = unicode(opt.params['realName']);
						}
					}
				}
			}),
			valueField : "id",
			displayField : "realName",
			fieldLabel : "�û�����",
			emptyText : '��ѡ���û�����',
			mode : 'remote',
			forceSelection : true,
			hiddenName : "id",
			editable : true,
			triggerAction : 'all',
			width : 200,
			lazyRender : true,
			pageSize : 10,
			typeAhead : false,
			allowBlank : false,
			name : "realName",
			selectOnFocus : true,
			listWidth : 200,
			maxHeight : 140,
			listeners : {
				blur : function(combo) {// ����ʧȥ�����ʱ��
					if (combo.getRawValue() == '') {
						combo.reset();
					}
				},
				beforequery : function(queryEvent) {

					var param = queryEvent.query;
					this.defaultParam = param;
					this.store.load({
						params : {
							realName : param,
							start : 0
						},
						callback : function(r, options, success) {
							userName.validate();
						}
					});
					return true;
				}
			}
		});
		
		var codes = [['1','֪ͨ����'], ['2','��������']];
		var stores = new Ext.data.SimpleStore({
			fields : ['id','type'],
			data : codes

		});
		var type= new Ext.form.ComboBox({
			       id:"addMarkUserType",
					name : 'typeName',
					store : stores,
					triggerAction : 'all',
					displayField : 'type',
					valueField : 'id',
					mode : 'local',
					width : 200
				});

		var form = new Ext.form.FormPanel({
			buttonAlign : 'center',
			labelAlign : 'right',
			labelWidth : 60,
			height : "auto",
			frame : true,
			items : [{
				layout : 'table',
				height : 110,
				width : '2000',
				layoutConfig : {
					columns : 2
				},
				items : [{
					html : "�û�����:&nbsp;",
					cls : 'common-text',
					style : 'width:90;text-align:right'
				}, userName,{
				    html : "��ǩ����:&nbsp;",
					cls : 'common-text',
					style : 'width:90;text-align:right'
				},type,{
					html : "ִ��˳��:&nbsp;",
					cls : 'common-text',
					style : 'width:90;text-align:right'
				}, sequence
				],
				bbar:[new Ext.Toolbar.TextItem("��ʾ�������ǩ����������ͨ������ѡ���������ͣ��粻�ؿ��Ǽ�ǩ�������ѡ��֪ͨ���ͼ��ɣ�</br>ִ��˳������˳������Խ��Խ����")]
			}]
		});

		// roleListGrid = new RoleListGrid().grid;
		var userDetailWin = new Ext.Window({
			title : '����ǰ�ڵ�ָ������(��ǩ)��',
			modal : true,
			height : 200,
			width : 560,
			resizable : false,
			viewConfig : {
				autoFill : true,
				forceFit : true
			},
			layout : 'absolute',
			buttonAlign : 'center',
			buttons : [{
				xtype : 'button',
				style : 'margin:4px 10px 4px 20',
				text : '����',
				iconCls : 'add',
				scope : this,
				handler : function() {
					var cmp = Ext.getCmp('com.addMarkForm');
					var store = cmp.getStore();
					var userNameId = Ext.getCmp('com.userName.combo')
							.getValue();
					var sequence = Ext.getCmp('sequenceId').getValue();
					var typeId=Ext.getCmp("addMarkUserType").getValue();
					if (userNameId == null || userNameId == '') {
						Ext.Msg.alert("��ʾ", "����ѡ���û�����!");
						return;
					}
					if (typeId == null || typeId == '') {
						Ext.Msg.alert("��ʾ", "����ѡ���ǩ������!");
						return;
					}
					if (sequence == null || sequence == '') {
						Ext.Msg.alert("��ʾ", "������дִ��˳��!");
						return;
					}
//					if (sequence <100) {
//						Ext.Msg.alert("��ʾ", "��дִ��˳���100��ʼ!");
//						return;
//					}
					
					var typeName='';
					if(typeId==1||typeId=='1'){
						typeName="֪ͨ����";
					}else{
					    typeName="��������";
					}
					
					var realName = this.getUserName(userNameId);

					if (store.recordType) {
						var rec = new store.recordType({
								// newRecord : true
						});
						rec.fields.each(function(f) {
							rec.data['typeName'] = typeName;
							rec.data['userName'] = realName;
							rec.data['userId'] = userNameId;
							rec.data['typeId'] = typeId;
							rec.data['sequence'] =parseInt(sequence)+parseInt(100);
						});
						
					  var allParam = store.getRange( 0, store.getCount());
					  for (var i = 0; i < allParam.length; i++) {
						   var oldSequence =  Ext.util.JSON.decode(Ext .encode(allParam[i].data)).sequence;
						   var oldRealName = Ext.util.JSON.decode(Ext .encode(allParam[i].data)).userName;
						 	if(oldSequence==parseInt(sequence)+parseInt(100)){
						 		alert("����д��ִ��˳����Ѿ������ˣ�����������д��");
						 		return;
						 	}
						 	if(oldRealName==realName){
						 		alert("����д�ļ�ǩ���Ѿ����ڣ�����������д��");
						 		return;
						 	}
					  }
					  
						rec.commit();
						store.add(rec);
						userDetailWin.close();
					} else {
						return false;
					}
					
  

				}
			}],
			items : [form]
		});
		userDetailWin.show();
	},

	// ��ʼ��
	initComponent : function() {// �������涨��ı�����ȫ�ֱ���
		AddMarkForm.superclass.initComponent.call(this);// �ø����ȳ�ʼ��

		var orderAction = new OrderAction();
		var nodeStore = orderAction.getNodeStore(this.taskId);
		var userStore = orderAction.getUserStore();
		var columns = new Array();
		var sm = new Ext.grid.CheckboxSelectionModel();
		this.store = new Ext.data.JsonStore({
			id:"addMarkUsersStore",
			url :webContext+'/extjs/workflow?method=getMarkUsers&taskId=' + this.taskId,
			root : "data",
			fields : ["id", "nodeName", "nodeDesc", "userName", "userId",
					"sequence","typeName","typeId"],
			totalProperty : 'rowCount'
		});

		var obj = [
//			{
//			header : "�ڵ�����",
//			dataIndex : "nodeName"
//		}, 
			{
			header : "�û�����",
			dataIndex : "userName"
		}, 
			{
		    header : "��ǩ����",
			dataIndex : "typeName"
		},{
			header : "ִ��˳��",
			dataIndex : "sequence"
		}];

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
			if (propertyName == 'sequence') {
				isHidden = true;
			}
//			if (propertyName == 'sequence') {
//				headItem.editor = new Ext.form.TextField({
//					name : 'sequence'
//				});
//			}
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
		this.cm.defaultSortable = true;
		var viewConfig = Ext.apply({
			forceFit : true
		}, this.gridViewConfig);
		this.pageBar = new Ext.PagingToolbar({
			pageSize : 10,// ʹ�õ���ϵͳĬ��ֵ
			store : this.store,
			displayInfo : true,
			displayMsg : '��ǰ��ʾ {0}-{1}����¼ /��{2}����¼',
			emptyMsg : "����ʾ����"
		});

		this.grid = new Ext.grid.EditorGridPanel({
			id : 'com.addMarkForm',
			store : this.store,
			cm : this.cm,
			sm : sm,
			trackMouseOver : false,
			loadMask : true,
			clicksToEdit : 2,
			autoScroll : true,
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
				handler : this.addMarkUser
			}, {
				xtype : 'button',
				style : 'margin:4px 10px 4px 0',
				text : 'ɾ��',
				iconCls : 'remove',
				scope : this,
				handler : function() {

					var removeIds = '';
					var cmp = Ext.getCmp('com.addMarkForm');
					var record = cmp.getSelectionModel().getSelected();
					var records = cmp.getSelectionModel().getSelections();
					if (!record) {
						Ext.Msg.alert("��ʾ", "����ѡ��Ҫɾ������!");
						return;
					}
					if(records.length>1){
						Ext.Msg.alert("��ʾ", "����ɾ������!");
						return;
					}
					if (records.length == 0) {
						Ext.MessageBox.alert('����', '����ѡ��һ����Ϣ������ɾ��!');
						return;
					}
					
						var info = "";
						info = info.concat(this.taskId+":");
						userId = Ext.encode(record.get("userId"));
						info = info.concat(userId);
						info=info.concat("+").concat(Ext.encode(record.get("sequence")));
						info=info.concat("+").concat(Ext.encode(record.get("typeId")));
					
					
					Ext.Msg.confirm('��ʾ��', '��ȷ��Ҫ����ɾ��������?', function(button) {
						if (button == 'yes') {
//modify by lee for ��ǩ���������ɾ����ǩ��ʱ�쳣��BUG in 20091207 begin
//							for (var i = 0; i < records.length; i++) {
//								var record = records[i];
//								var id = record.get('id');
//								removeIds += id;
//								removeIds += ",";
								this.store.remove(record);
//							}
//modify by lee for ��ǩ���������ɾ����ǩ��ʱ�쳣��BUG in 20091207 end
							Ext.Ajax.request({
								url : webContext + '/extjs/workflow?method=deleteMarkUsersInfo',
								params :{
									taskId : this.taskId,
									deleteMarkUsers : info
								},
								success : function(response,options) {
									var result = Ext.decode(response.responseText);
									if(result.success){
									  Ext.Msg.alert('��ʾ', 'ɾ���ɹ�');
//modify by lee for ��ǩ���������ɾ����ǩ��ʱ�쳣��BUG in 20091207 begin
//									  var store=Ext.getCmp("com.addMarkForm").getStore();
//									  store.load();
									  this.store.reload();
//modify by lee for ��ǩ���������ɾ����ǩ��ʱ�쳣��BUG in 20091207 end
									}
								},
								failure : function(response,options) {
									var result = Ext.decode(response.responseText);
									if(!result.success){
									 Ext.alert('��ʾ', 'ɾ��ʧ��');
									}
								}
						});
							// ɾ����Ҫɾ��������Ϣ��ͬʱɾ��save�����е�����߼� ADD by DJ ��

							// orderAction.deletePageModelConfigUnitRecord(removeIds);
							// ���ɾ���б�
							removeIds = '';
						}
					}, this);
				}
			}, {
				xtype : 'button',
				style : 'margin:4px 10px 4px 20',
				text : '����',
				iconCls : 'save',
				scope : this,
				hidden : true,
				handler : this.saveParams
					
			}]
			//			new Ext.Toolbar.TextItem('<font color=red>ִ��˳��С��100�ģ����ϸ��ڵ����ǰ�ڵ�ָ�ɵļ�ǩ�ˣ�</font>')]
		});
       this.store.load();
		this.add(this.grid)
	}
});
