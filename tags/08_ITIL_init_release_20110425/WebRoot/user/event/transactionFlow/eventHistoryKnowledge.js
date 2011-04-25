HistroyForm = Ext.extend(Ext.Panel, {
	id : "HistroyForm",
	layout : 'table',
	border:true,
	frame : true,
	layoutConfig : {
		columns : 1
	},
	closable : true,
    width:750,
    height :'auto',
	addHistorySolution : function() {
            var da = new DataAction();
            var data =da.getSingleFormPanelElementsForEdit("KnowLedgeSolution_pagepanel",this.knowledgeId);
            var biddata = this.splitForReadOnly(data);
            this.formUserForLookSulotion_pagepanel= new Ext.form.FormPanel({
                    id : 'UserForLookSulotion_pagepanel',
                    layout : 'table',
                    width : 750,
                    height : 400,
                    frame : true,
                    border:true,
//                    collapsible : true,
                    defaults : {
                        bodyStyle : 'padding:2px'
                    },
                    layoutConfig : {
                        columns : 4
                    },
//                        title :'历史解决方案',
                    items : biddata
                });
              return this.formUserForLookSulotion_pagepanel;
	    },
	    splitForReadOnly : function(data) {
		var labellen = 120; 
		var itemlen = 200;
		var throulen = 530;
		if (Ext.isIE) {
			throulen = 530;
		} else {
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
			data[i].readOnly = true;
			if (data[i].xtype == "textarea") {
				data[i].style += "'margin:5 0 5 0;'";
			}
			if (data[i].xtype == "hidden") {
				hidd[hid] = data[i];
				hid++;
				continue;
			}
			if (data[i].xtype == 'combo' || data[i].xtype == 'datefield') {
				data[i].hideTrigger = true;
			}
			if (data[i].xtype == "panel") {
//				var dd = Ext.encode(data[i]).replace(/display:/g,
//						"display:none").replace("\"disabled\":false",
//						"\"disabled\":true");
//				data[i] = Ext.decode(dd);
			}
			if (data[i].xtype == "fckeditor") {
				data[i] = {
					html : data[i].value,
//					xtype : "fckeditor", //2010-04-20 modified by huzh
					autoScroll:true,
					frame :true,
					colspan : 3,
					fieldLabel : data[i].fieldLabel,
					cls : 'common-text',
					width: data[i].width,
					height : 300
				};
			}
			if (data[i].width == null || data[i].width == 'null'
					|| data[i].width == "") {
				data[i].style += "width:" + itemlen + "px";
				data[i].width = itemlen;
			} else {
				if (data[i].width == "9999") {// 通栏
					if ((i - hid + longitems) % 2 == 1) {// 在右侧栏，前一栏贯通
						longData[2 * (i - hid) - 1].colspan = 3;
					} else {// 多占一栏
						longitems++;
					}
					data[i].colspan = 3;// 本栏贯通
					data[i].width = throulen;
					if (data[i].xtype == "textarea") {
						data[i].height = 150;
					}
					
					data[i].style += "width:" + throulen + "px;";
				} else {// 正常长度，半栏
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
	items : this.items,

	initComponent : function() {
		this.panel=this.addHistorySolution();
		this.items=[this.panel];
		HistroyForm.superclass.initComponent.call(this);
	}
})
