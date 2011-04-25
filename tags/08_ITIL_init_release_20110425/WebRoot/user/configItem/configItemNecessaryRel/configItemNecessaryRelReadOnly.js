PageTemplates = Ext.extend(Ext.FormPanel, {
	id : 'configItemNecessaryPanel',
	layout : 'table',
	frame : true,
	defaults : {
		bodyStyle : 'padding:4px'
	},
	layoutConfig : {
		columns : 4
	},
	buttonAlign:"center",
	split : function(data) {
		var labellen = 100;
		var itemlen = 150;
		var throulen = 400;
		if (Ext.isIE) {
			throulen = 400;
		} else {
			throulen = 400;
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
			if (data[i].xtype == "combo") {
				data[i].resizable = true;
			}
			if (data[i].width == null || data[i].width == 'null'
					|| data[i].width == "") {
				data[i].style += "width:" + itemlen + "px";
				data[i].width = itemlen;
			} else {
				if (data[i].width == "9999") {//通栏  
					if ((i - hid + longitems) % 2 == 1) {//在右侧栏，前一栏贯通                                             
						longData[2 * (i - hid) - 1].colspan = 3;
					} else {//多占一栏
						longitems++;
					}
					data[i].colspan = 3;//本栏贯通                                            
					data[i].width = throulen;
					if (data[i].xtype == "textarea") {
						data[i].height = 50;
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
				style : 'width:' + labellen + ';text-align:left'
			};
			longData[2 * (i - hid) + 1] = data[i];
		}
		for (i = 0; i < hidd.length; i++) {
			longData[longData.length] = hidd[i];
		}
		return longData;
	},
 getConfigItemNecessaryPanel: function(dataId) {
	 var da = new DataAction();
	 if(dataId!="")
	 	var data = da.getSingleFormPanelElementsForEdit("panel_ConfigItemNecessaryRel", dataId);
	 else
		var data = da.getPanelElementsForAdd("panel_ConfigItemNecessaryRel");
	for(var i=0;i<data.length;i++){
		if(data[i].id=="ConfigItemNecessaryRel$attachQuotiety"){
			data[i]=new Ext.form.NumberField({
				id:data[i].id,
				name:data[i].name,
				value:data[i].value,
				height:data[i].height,
				width:data[i].width,
				allowBlank:data[i].allowBlank,
				fieldLabel:data[i].fieldLabel
			});
		}
		if(data[i].id=="ConfigItemNecessaryRel$description"){
			data[i].width=9999;		
		}
		if(data[i].id=="ConfigItemNecessaryRel$parentOrChildTypeCombo"){
				data[i].store=new Ext.data.SimpleStore({
					fields:['id','name'],
					data:[[1,"父-->子"],[2,"子-->父"]]});
		}
		data[i].readOnly=true;
		data[i].hideTrigger=true;
		data[i].emptyText="";
		data[i].allowBlank=true;
	}
	 var biddata = this.split(data);
	 return biddata;
	},
    items : this.items,
	initComponent : function() {
		this.items = this.getConfigItemNecessaryPanel(this.dataId);
		PageTemplates.superclass.initComponent.call(this);
	}
})