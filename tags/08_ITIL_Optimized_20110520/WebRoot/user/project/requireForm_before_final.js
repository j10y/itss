PageTemplates = Ext.extend(Ext.Panel, {
	id : "PageTemplates",
	layout : 'table',
	height : 'auto',
	align : 'center',
	foredit : true,
	width : 1050,
	frame : true,
	autoScroll : true,
	defaults : {
		bodyStyle : 'padding:4px'
	},
	layoutConfig : {
		columns : 1  
	},
	getTabpanel : function(tab,tabTitle){
		this.tabPanel = new Ext.TabPanel({           
			xtype : 'tabpanel',
			activeTab : 0,
            enableTabScroll:true, 
            //minTabWidth:100,
            //resizeTabs:true,
            title:tabTitle,
			deferredRender : false,
			frame : true,
			plain : true,
            border : false, 
            //tabPosition:'bottom',
			baseCls : 'x-plain',// �Ƿ����úͱ���ɫͬ��
			width : 900,
			//bodyBorder : true,
			defaults : {
				autoHeight : true,
				bodyStyle : 'padding:2px'
			},
			items : tab
		});
		return this.tabPanel;

	},
	getPanel : function(appa,panelTitle) {
		this.Panel = new Ext.Panel({
			id : "Pages",
			height : 'auto',
			align : 'center',
			title:panelTitle,
			border : false,
            defaults : {
                 bodyStyle : 'padding:5px'
            },
			width :'auto',
			frame : true,
			autoScroll : true,
			layoutConfig : {
				columns : 1
			},
			items : appa
		});
		return this.Panel;

	}, 
	
	
 getFormflm_requireForm_panel: function() {
      var da = new DataAction();
		  var data = null;
			// �ж������������޸�
			var biddata = "";
			
			if (this.dataId != '0') {
				data = da.getPanelElementsForEdit("flm_requireFormPage", "flm_requireForm_panel", this.dataId);// ����Ҫ��ʱ���
				biddata = da.split(data);
			} else {
				data = da.getPanelElementsForAdd("flm_requireForm_panel");
				biddata = da.split(data);
			}
			var buttonUtil = new ButtonUtil();
			this.getFormButtons = buttonUtil.getButtonForPanel("flm_requireForm_panel",this);
			if(this.getFormButtons.length!=0){
		this.formflm_requireForm_panel= new Ext.form.FormPanel({
			id : 'flm_requireForm_panel',
			layout : 'table',
			height : 'auto',
			width : 800,
			frame : true,
			collapsible : true,
			defaults : {
				bodyStyle : 'padding:4px'
			},
			layoutConfig : {
				columns : 4
			},
			title : "������Ϣ",
			items : biddata,
			buttons : this.getFormButtons
		});
		}else{
			this.formflm_requireForm_panel= new Ext.form.FormPanel({
			id : 'flm_requireForm_panel',
			layout : 'table',
			height : 'auto',
			width : 800,
			frame : true,
			collapsible : true,
			defaults : {
				bodyStyle : 'padding:4px'
			},
			layoutConfig : {
				columns : 4
			},
			title : "������Ϣ",
			items : biddata
		});
		}
		return this.formflm_requireForm_panel;
	},
getGridflm_projectPlan_grid:function(){
		var da = new DataAction();
		var obj = da.getPanelElementsForHead("flm_projectPlan_grid");
		var buttonUtil = new ButtonUtil();
		var getGridButtons = buttonUtil.getButtonForPanel("flm_projectPlan_grid",this);
		var sm = new Ext.grid.CheckboxSelectionModel();
		var columns = new Array();
		columns[0] = sm;
		// ѭ�������е��У�Ȼ��������������
		for (var i = 0; i < obj.length; i++) {
			var headItem = obj[i];
			var title = headItem.header;
			var alignStyle = 'left';
			var propertyName = headItem.dataIndex;
			var editor = headItem.editor;
			var renderer = headItem.renderer;
			var hide = headItem.hidden;
			var isHidden = false;
			if (hide == 'true') {
				isHidden = true;
			}
			// ��ÿһ����������
			var columnItem = "";
			if (editor.xtype == 'combo') {
				columnItem = {
					header : title,
					dataIndex : propertyName,
					sortable : true,
					hidden : isHidden,
					align : alignStyle,
					renderer : renderer,
					editor : editor
				}
			} else {
				columnItem = {
					header : title,
					dataIndex : propertyName,
					sortable : true,
					hidden : isHidden,
					align : alignStyle,
					editor : editor
				}
			}
			columns[i + 1] = columnItem;
		}
		this.cm = new Ext.grid.ColumnModel(columns);
		this.store = da.getPagePanelJsonStore("flm_projectPlan_grid",obj);
		this.cm.defaultSortable = true;
		var viewConfig = Ext.apply({
			forceFit : true
		}, this.gridViewConfig);
		this.formValue = '';
		this.gridflm_projectPlan_grid= new Ext.grid.EditorGridPanel({
			id : 'flm_projectPlan_grid',
			store : this.store,
			cm : this.cm,
			sm : sm,
			title :"������Ŀ�ƻ�",
			trackMouseOver : false,
			loadMask : true,
			clicksToEdit : 2,
			collapsible : true,
			autoScroll : true,
			loadMask : true,
			autoHeight : true,
			width : 800,// this.width - 15,
			frame : true,
			tbar : [getGridButtons]
		});
			if (this.dataId != '0') {
					//if(fcname==undefined) { fcname="configItem"; }
					//if(pcname==undefined) { pcname="ConfigItem$id"; }
					var pcnameValue = Ext.getCmp("flm_Requirement$id").getValue();
					var str = '{' + '\"' + "requirement" + '\"' + ':' + '\"'
							+ pcnameValue + '\"' + '}';// ����Ҫ��ʱ���
					var param = eval('(' + str + ')');
					param.methodCall = 'query';
					// alert(str);
					this.store.load({
						params : param
					});
				}
		return this.gridflm_projectPlan_grid;
	},
 getFormflm_contract_panel: function() {
      var da = new DataAction();
		  var data = null;
			// �ж������������޸�
			var biddata = "";
			
			if (this.dataId != '0') {
				data = da.getPanelElementsForEdit("flm_requireFormPage", "flm_contract_panel", this.dataId);// ����Ҫ��ʱ���
				biddata = da.split(data);
			} else {
				data = da.getPanelElementsForAdd("flm_contract_panel");
				biddata = da.split(data);
			}
			var buttonUtil = new ButtonUtil();
			this.getFormButtons = buttonUtil.getButtonForPanel("flm_contract_panel",this);
			if(this.getFormButtons.length!=0){
		this.formflm_contract_panel= new Ext.form.FormPanel({
			id : 'flm_contract_panel',
			layout : 'table',
			height : 'auto',
			width : 800,
			frame : true,
			collapsible : true,
			defaults : {
				bodyStyle : 'padding:4px'
			},
			layoutConfig : {
				columns : 4
			},
			title : "��ͬ��Ϣ",
			items : biddata,
			buttons : this.getFormButtons
		});
		}else{
			this.formflm_contract_panel= new Ext.form.FormPanel({
			id : 'flm_contract_panel',
			layout : 'table',
			height : 'auto',
			width : 800,
			frame : true,
			collapsible : true,
			defaults : {
				bodyStyle : 'padding:4px'
			},
			layoutConfig : {
				columns : 4
			},
			title : "��ͬ��Ϣ",
			items : biddata
		});
		}
		return this.formflm_contract_panel;
	},
 getFormflm_contractAnalysePanel: function() {
      var da = new DataAction();
		  var data = null;
			// �ж������������޸�
			var biddata = "";
			
			
			var treedata = this.getContractAnalyseReportPage();
			
			
			
			if (this.dataId != '0') {
				data = da.getPanelElementsForEdit("flm_requireFormPage", "flm_contractAnalysePanel", this.dataId);// ����Ҫ��ʱ���
				biddata = da.split(data);
			} else {
				data = da.getPanelElementsForAdd("flm_contractAnalysePanel");
				biddata = da.split(data);
			}
			var buttonUtil = new ButtonUtil();
			this.getFormButtons = buttonUtil.getButtonForPanel("flm_contractAnalysePanel",this);
			if(this.getFormButtons.length!=0){
		this.formflm_contractAnalysePanel= new Ext.form.FormPanel({
			id : 'flm_contractAnalysePanel',
			layout : 'table',
			height : 'auto',
			width : 800,
			frame : true,
			collapsible : true,
			defaults : {
				bodyStyle : 'padding:4px'
			},
			layoutConfig : {
				columns : 4
			},
			title : "��ͬ��������",
			items : biddata,
			buttons : this.getFormButtons
		});
		
		
		this.treePanel= new Ext.form.FormPanel({
						layout : 'column',
						height : 'auto',
						width : this.width-15,
						frame : true,
						collapsible : true,
						defaults : {
							bodyStyle : 'padding:4px'
						},
						layoutConfig : {
							columns : 1
						},
						items : treedata
					});
		
		
		
		this.tmpPanel = new Ext.Panel({
						    title:'��ͬ��������',
					     	items:[this.formflm_contractAnalysePanel, this.treePanel]
					});
		
		
		}else{
			this.formflm_contractAnalysePanel= new Ext.form.FormPanel({
				
				
			id : 'flm_contractAnalysePanel',
			layout : 'table',
			height : 'auto',
			width : 800,
			frame : true,
			collapsible : true,
			defaults : {
				bodyStyle : 'padding:4px'
			},
			layoutConfig : {
				columns : 4
			},
			title : "��ͬ��������",
			items : biddata
		});
		
		
		this.treePanel= new Ext.form.FormPanel({
						layout : 'column',
						height : 'auto',
						width : this.width-15,
						frame : true,
						collapsible : true,
						defaults : {
							bodyStyle : 'padding:4px'
						},
						layoutConfig : {
							columns : 1
						},
						items : treedata
					});
					
					
					this.tmpPanel = new Ext.Panel({
						    title:'��ͬ��������',
					     	items:[this.formflm_contractAnalysePanel, this.treePanel]
					});
		
		}
		return this.tmpPanel;
	},
  items : this.items,
	/*
	 * clientvalidation ��ʼ��
	 */
	initComponent : function() {
	    var items = new Array();
	    var pa = new Array();
		  this.pa = pa;
		var gd = new Array();
		  this.gd = gd;
		var temp = new Array();
		  this.temp = temp;
		var formname = new Array();
		  this.formname=formname;
		var gridname = new Array();
		  this.gridname=gridname;
		this.model="flm_requireFormPage";
		var buttonUtil = new ButtonUtil();
		this.mybuttons = buttonUtil.getButtonForModel("flm_requireFormPage",this);
		if(this.mybuttons!=""){
			this.buttons = {
			layout : 'table',
			height : 'auto',
			width : 800,
			style : 'margin:4px 6px 4px 300px',
			align : 'center',
			defaults : {
				bodyStyle : 'padding:4px'
			},
			items : this.mybuttons
			};
		}else{
			this.buttons = {
			layout : 'table',
			height : 'auto',
			width : 800,
			style : 'margin:4px 6px 4px 300px',
			align : 'center',
			defaults : {
				bodyStyle : 'padding:4px'
			}
			};
		}
		
		       this.getFormflm_requireForm_panel();
		       this.pa.push(this.formflm_requireForm_panel);
		       this.formname.push("flm_requireForm_panel");
		       temp.push(this.formflm_requireForm_panel);
		       this.getGridflm_projectPlan_grid();
		       this.gd.push(this.gridflm_projectPlan_grid);
		       this.gridname.push("flm_projectPlan_grid");
		       temp.push(this.gridflm_projectPlan_grid);
		       this.getFormflm_contract_panel();
		       this.pa.push(this.formflm_contract_panel);
		       this.formname.push("flm_contract_panel");
		       temp.push(this.formflm_contract_panel);
		       this.getFormflm_contractAnalysePanel();
		       this.pa.push(this.formflm_contractAnalysePanel);
		       this.formname.push("flm_contractAnalysePanel");
		       temp.push(this.tmpPanel);//formflm_contractAnalysePanel);
          items.push(this.getTabpanel(temp));
		items.push(this.buttons);
		this.on('saveTree',this.saveTree,this);
		   this.items = items;
		PageTemplates.superclass.initComponent.call(this);
	},
	getContractAnalyseReportPage: function(){
		  
			  var tipp = "<font color='red'>&nbsp;[˫����ֵ�������ڵ㣬�ɶ���Ӧ���ݽ��б༭]</font>";
			  var processmsg = {text:'', press:false, id:'processMsg'};
			  
				this.tree = new ColumnTreePanel({
					cm : [{
				            header:'����Ŀ����',
				            width:350,
				            dataIndex:'name'
					      },{
				            header: '����',
				            width:80,
				            dataIndex:'currency'
					      },{
				            header: '��ֵ',
				            width:220,
				            dataIndex:'value'
					      },{
				            header:'id',
				            width:20,
			            	hidden:true,
				            dataIndex:'id'
					     }],  
					dataUrl: webContext+'/contractAnalyseReport_find.action?requireId='+this.dataId,
					tbar: [tipp, '<div id="processMsg"></div>']
				});
				
				var te = new Ext.tree.ColumnTreeEditor(this.tree,{
				        completeOnEnter: true,
				        autosize: true,
				        ignoreNoChange: true   
	      }); 
	      this.tree.expandAll();
	            
        var panel = new Ext.Panel({
        		autoScroll: true,
		        items: [this.tree]
        });
				
			return panel;
	 },
	 
	 saveTree: function(){
	 	         
						
	 	           var baseInfo = this.formflm_contractAnalysePanel.form.getValues();

	    				 var json = this.tree.toJsonString(null,
									function(key, val) {
										return (key =='id'||key =='value');
									}, 
									{id:'id',value:'value'}
								);
								var msg = Ext.get("processMsg");
	
								Ext.Ajax.request({
                        url: webContext+'/contractAnalyseReport_saveData.action',
                        method: 'POST',
                        params: this.formflm_contractAnalysePanel.form.getValues(),
                        success: function(response, options){
                        	  var id = Ext.decode(response.responseText).id;
	                        	msg.load({			
															  url: webContext+'/contractAnalyseReport_save.action',
															  method:'post',
														    params:{
														    	data: json,
														    	model: 'flm_requireFormPage',
														    	requireId: this.dataId,
														    	analyseId: id
														    },
														    scope:this,
														    callback:function(result){
														    	    
														    },
															  text: "���ڱ�������......"
														});	
														
														msg.show();			
														ReturnValue = Ext.MessageBox.alert("����", "�����ͬ����������Ϣ�ɹ���");
                        },
                        failure: function(response, options){
                            ReturnValue = Ext.MessageBox.alert("����", "�����ͬ����������Ϣʧ�ܣ�");
                        },
                        scope:this
               });
										
	 }		
})