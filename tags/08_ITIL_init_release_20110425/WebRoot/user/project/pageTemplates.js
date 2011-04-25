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
	getForm : function(biddata, faNo) { 
		var buttonUtil = new ButtonUtil();
		this.getFormButtons = buttonUtil.getButtonForPanel(faNo,this);
		if(this.getFormButtons.length!=0){
		this.formPanel = new Ext.form.FormPanel({
			id : faNo,
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
			title : this.formTitle,
			items : biddata,
			buttons : this.getFormButtons
		});
		}else{
			this.formPanel = new Ext.form.FormPanel({
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
			title : this.formTitle,
			items : biddata
		});
		}
		return this.formPanel;
	},
	getEditGrid : function(gridName,fcname,pcname) {
		// alert("构造面板"+gridName);
		var da = new DataAction();
		var obj = da.getPanelElementsForHead(gridName);
		var buttonUtil = new ButtonUtil();
		var getGridButtons = buttonUtil.getButtonForPanel(gridName,this);
		// 前面的复选框
		var sm = new Ext.grid.CheckboxSelectionModel();
		var columns = new Array();
		columns[0] = sm;
		// 循环出所有的行，然后增加属性修饰
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
			// 给每一行增加修饰
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
			} else if(editor.xtype=='datefield'){
				columnItem = {
					header : title,
					dataIndex : propertyName,
					sortable : true,
					hidden : isHidden,
					align : alignStyle,
                    renderer : renderer,                              
					editor : editor
				}
			}else{
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
		// 根据字段添加数据
		// this.store = da.getJsonStore(clazz)
		this.store = da.getPagePanelJsonStore(gridName,obj);//add by peixf
		// alert(this.store);
		// 设置排序的
		this.cm.defaultSortable = true;
		var viewConfig = Ext.apply({
			forceFit : true
		}, this.gridViewConfig);
		this.formValue = '';
		this.grid = new Ext.grid.EditorGridPanel({
			id : gridName,
			store : this.store,
			cm : this.cm,
			sm : sm,
			title : this.editGirdTitle,
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
			var pcnameValue = Ext.getCmp(pcname).getValue();
			//alert("edit fcname:"+fcname);
			//alert(pcname); alert(pcnameValue);
			//alert("edit pcnameValue:"+pcnameValue);
			var str = '{' + '\"' + fcname + '\"' + ':' + '\"'
					+ pcnameValue + '\"' + '}';// 这是要随时变得
			var param = eval('(' + str + ')');
			param.methodCall = 'query';
			// alert(str);
			this.store.load({
				params : param
			});
		}

		return this.grid;
	},
	getGrid : function(gridName,fcname,pcname) {
		var da = new DataAction();
		var obj = da.getListPanelElementsForHead(gridName);
		var buttonUtil = new ButtonUtil();
		var getGridButtons = buttonUtil.getButtonForPanel(gridName,this);
		var sm = new Ext.grid.CheckboxSelectionModel();
		var columns = new Array();
		columns[0] = sm;
		// 循环出所有的行，然后增加属性修饰
		for (var i = 0; i < obj.length; i++) {
			var headItem = obj[i];
			var title = headItem.header;
			var alignStyle = 'left';
			var propertyName = headItem.dataIndex;
			var hide = headItem.isHidden;
			var isHidde = false;
			if (hide == 'true') {
				isHidde = true;
			}
			// 给每一行增加修饰
			var columnItem = "";
				columnItem = {
					header : title,
					dataIndex : propertyName,
					sortable : true,
					hidden : isHidde,
					align : alignStyle
				}
			columns[i + 1] = columnItem;
		}

		this.cm = new Ext.grid.ColumnModel(columns);
		// 根据字段添加数据
		this.store = da.getPagePanelJsonStore(gridName,obj);//add by peixf
		// 设置排序的
		this.cm.defaultSortable = true;
		var viewConfig = Ext.apply({
			forceFit : true
		}, this.gridViewConfig);
		this.formValue = '';
		this.grid = new Ext.grid.GridPanel({
			id : gridName,
			store : this.store,
			cm : this.cm,
			sm : sm,
			title : this.editGirdTitle,
			trackMouseOver : false,
			loadMask : true,
			collapsible : true,
			autoScroll : true,
			loadMask : true,
			autoHeight : true,
			width : 800,// this.width - 15,
			frame : true,
			tbar : [getGridButtons]
		});
		if (this.dataId != '0') {
			var pcnameValue = Ext.getCmp(pcname).getValue();
			var str = '{' + '\"' + fcname + '\"' + ':' + '\"' + pcnameValue + '\"' + '}';// 这是要随时变得
			var param = eval('(' + str + ')');
			param.methodCall = 'query';
			this.store.load({
				params : param
			});
		}

		return this.grid;
	},
	getTabpanel : function(tab,tabTitle) {
                  //  alert(tabTitle);
		this.tabPanel = new Ext.TabPanel({
			xtype : 'tabpanel',
			activeTab : 0,
            title:tabTitle,
			enableTabScroll : true,
			// minTabWidth:100,
			//resizeTabs : true,
			deferredRender : false,
			frame : true,
			plain : true,
			border : false,
			// tabPosition:'bottom',
			baseCls : 'x-plain',// 是否设置和背景色同步
			width : 900,
			// bodyBorder : true,
			defaults : {
				autoHeight : true,
				bodyStyle : 'padding:2px'
			},
			items : tab
		});
		return this.tabPanel;

	},
	getPanel : function(appa,panTitle) {
		this.Panel = new Ext.Panel({
			id : "Pages",
			height : 'auto',
			align : 'center',
            title:panTitle,
			border : false,
			defaults : {
				bodyStyle : 'padding:5px'
			},
			width : 'auto',
			frame : true,
			autoScroll : true,
			layoutConfig : {
				columns : 1
			},
			items : appa
		});
		return this.Panel;

	},
	getPanelTemplate : function(fcname,pcname) {//*************************
		var da = new DataAction();
		if (this.panelObj.xtype == "form") {
			this.formname.push(this.panelObj.panelname);
			this.formTitle = this.panelObj.title;
			var formClazz = this.panelObj.clazz;
			var foNa = this.panelObj.panelname;
			var divfloat = this.panelObj.divFloat;
			var panelId = this.panelObj.id;
			var data = null;
			// 判断是新增还是修改
			var biddata = "";
			if (this.dataId != '0') {
				data = da.getPanelConfigItemElementsForEdit(this.model, foNa, this.dataId);// 这是要随时变得
				biddata = da.split(data);
			} else {
				data = da.getPanelConfigItemElementsForAdd(foNa);
				biddata = da.split(data);
			}
			this.pa[this.p] = this.getForm(biddata,foNa);
			this.itemsArray.push(this.pa[this.p]);
			this.p++;

		}else if (this.panelObj.xtype == "grid") {
			// 取出表名放进数组中，在保存的时候会用到panel的名字
			this.gridname.push(this.panelObj.panelname);
			this.editGirdTitle = this.panelObj.title;
			var gridClazz = this.panelObj.clazz;
			var gridName = this.panelObj.panelname;
			var panelTableName = this.panelObj.panelTableName;
			this.gd[this.n] = this.getGrid(gridName,fcname,pcname); //alert(pcname);
			this.itemsArray.push(this.gd[this.n]);
			this.n++;
		}else if (this.panelObj.xtype == "editorgrid") {
			// 取出表名放进数组中，在保存的时候会用到panel的名字
			this.gridname.push(this.panelObj.panelname);
			this.editGirdTitle = this.panelObj.title;
			var gridClazz = this.panelObj.clazz;
			var gridName = this.panelObj.panelname;
			//alert(gridName);
			var panelTableName = this.panelObj.panelTableName;
			
			this.gd[this.n] = this.getEditGrid(gridName,fcname,pcname); //alert(pcname);
			this.itemsArray.push(this.gd[this.n]);
			this.n++;
		}

	},
	getChildpanelTemplate : function(layout, pagePanelchild,groupTitle) {
		var itm = new Array();
		var pnm;
		var second;
                    //alert(pagePanelchild);
		for (var r = 0; r < pagePanelchild.length; r++) {
			if (pagePanelchild[r].groupFlag == '1') {
				var lay = pagePanelchild[r].xtype;
                var childGrTitle = pagePanelchild[r].title;                       
				pagePanelchild = pagePanelchild[r].childPagePanels;
				pnm = this.getChildpanelTemplate(lay, pagePanelchild,childGrTitle);
				itm.push(pnm);
				if (layout == "panel") {
					second = this.getPanel(itm,groupTitle);
				} else {
					second = this.getTabpanel(itm,groupTitle);
				}
			} else {
				this.panelObj = pagePanelchild[r]; //*********************8888888 ne aaddds
				var fcname= this.panelObj.fcolumnName;//customer
				var pcname= this.panelObj.pcolumnName;//CustomerIn$id
				//alert("in group: fcname:"+fcname+", pcname:"+pcname);
				//alert(fcname);alert(pcname);
				this.itemsArray = itm;
				this.getPanelTemplate(fcname,pcname);
				if (layout == "tabpanel") {    
					second = this.getTabpanel(this.itemsArray,groupTitle);
				} else {
					second = this.getPanel(this.itemsArray,groupTitle);
				}
			}
		}
		return second;

	},

	items : this.items,

	/*
	 * clientvalidation 初始化
	 */
	initComponent : function() {
		var da = new DataAction();
		if(this.configItemTypeId!=""){
			var json = da.getPageModelAdd("requireConfigPage",this.configItemTypeId);
		}else{
			var json = da.getPageModelForConfigItem("requireConfigPage",this.dataId);//getPageModel
		}
		this.model = json.pageModel[0].name;
		var buttonUtil = new ButtonUtil();
		this.mybuttons = buttonUtil.getButtonForModel(this.model,this);
		var pagePanel = json.panels;
		// 下面主要是用来对面板进行初始化
		var items = new Array();
		var temp = new Array();
		var pa = new Array();
		this.pa = pa;
		var gd = new Array();
		this.gd = gd;
		var formname = new Array();
		this.formname = formname;
		var gridname = new Array();
		this.gridname = gridname;
        //var panTitle=new Array();
        //var tabTemp=new Array();
		this.my = "0";
		this.p="0";
        this.n="0";        
		// 下面利用for循环来获取初始化面板信息
		var child = '';
		for (var k = 0; k < pagePanel.length; k++) {
			if (pagePanel[k].groupFlag == '1') {
				var laye = pagePanel[k].xtype;
                var groupTitle=pagePanel[k].title ;
				pagePanelchild = pagePanel[k].childPagePanels;
                     //alert(pagePanelchild);
				child = this.getChildpanelTemplate(laye, pagePanelchild,groupTitle);
				temp.push(child);
                //panTitle.push(groupTitle);
			} else {
				this.panelObj = pagePanel[k];
				var fcname= this.panelObj.fcolumnName;//customer
				var pcname= this.panelObj.pcolumnName;//CustomerIn$id
				//alert("root fcname:"+fcname+", pcname:"+pcname);
				this.itemsArray = temp;
				this.getPanelTemplate(fcname, pcname); //1月份凌晨修改bypeixf
                //panTitle.push(pagePanel[k].title);                        
			}

		}
		// 下面的代码是为了让保存时获得面板对象用的
		// 按钮
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
		// 下面是用来将所有子面板添加到整个面板中
		if (json.pageModel[0].pagePathType == "tabpanel") {
			items.push(this.getTabpanel(temp));
		} else {
			items = temp;
		}
		items.push(this.buttons);
		this.items = items;
		this.on("pageEvent", this.pageMethod, this);
		this.on("saveRequireConfig", this.saveRequireConfig, this);
		var citId=this.configItemTypeId;
		if(citId!=null&&citId!="")
			Ext.getCmp('ConfigItem$configItemTypeCombo').store.load({
				params:{
					id:citId,
					start:0 
				},
				callback:function(r, options, success){
					Ext.getCmp('ConfigItem$configItemTypeCombo').setValue(citId);
				}
			});
		 	Ext.getCmp('ConfigItem$configItemTypeCombo').addListener('change', function(box, newv, oldv) {
       				if(newv!=citId){
                    window.location= webContext+'/user/config/configItemInfo.jsp?configItemTypeId='+newv;
                    }
    			});
    	PageTemplates.superclass.initComponent.call(this);  
	},
	pageMethod : function(){
		alert("当前dataId:"+dataId);
	},
	saveRequireConfig : function(){
		//if(parentId==""){
		//	Ext.MessageBox.alert("请先保存需求信息！");
		//}else{
		var formParam = "";
		var gridParam = "";
		var param = '{'; 
		if (this.pa.length != "0") {
			for (var i = 0; i < this.pa.length; i++) {
				var fP = Ext.encode(this.pa[i].form.getValues(false));
				formParam += '\"' + this.formname[i] + '\"' + ':[' + fP + '],';
			}
			param += formParam;
		}
		if (this.gd.length != "0") {
			for (var k = 0; k < this.gd.length; k++) {
				var gParam = "";
				var gP = this.gd[k].getStore().getRange(0,this.gd[k].getStore().getCount());
				for (i = 0; i < gP.length; i++) {
					gParam += Ext.encode(gP[i].data) + ",";
				}
				gParam = gParam.slice(0, gParam.length - 1);
				gridParam += '\"' + this.gridname[k] + '\"' + ':[' + gParam + '],';
			}
			param += gridParam;
		}
		param = param.slice(0, param.length - 1) + '}';
		alert("调用后台保存方法，传参为：\n"+
							"requireId:"+parentId+"\n"+
							"model:"+this.model+"\n"+
							"info:"+param+"\n"
							);
		Ext.Ajax.request({
			  url: webContext+'/requireConfigItem_save.action',
			//url : webContext + '/extjs/pageData?method=save',
			params : {
				requireId : parentId,
				info : param,
				model : this.model
			},
			success : function(response, options) {
				var responseArray = Ext.util.JSON.decode(response.responseText);  
				var curId = responseArray.id;
				window.location = webContext + '/user/project/configItemInfo.jsp?dataId='+curId;
			},
			failure : function(response, options) {
				Ext.MessageBox.alert("保存失败");
			}
		}, this);
	//}
	}
})
