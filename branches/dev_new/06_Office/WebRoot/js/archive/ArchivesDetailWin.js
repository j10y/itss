ArchivesDetailWin = Ext.extend(Ext.Window, {
	formPanel : null,
	constructor : function(a) {
		Ext.applyIf(this, a);
		this.init();
		ArchivesDetailWin.superclass.constructor.call(this, {
			title : "公文详情",
			id : "ArchivesDetailWin",
			iconCls : "btn-archives-detail",
			layout : "form",
			modal : true,
			height : 430,
			width : 750,
			maximizable : true,
			border : false,
			autoScroll : true,
			buttonAlign : "center",
			buttons : [ {
				text : "关闭",
				iconCls : "btn-del",
				handler : this.closePanel,
				scope : this
			} ],
			items : [ this.panel ]
		});

		if(this.isDist){
			Ext.Ajax.request({
				url : __ctxPath
						+ "/archive/viewArchivesDist.do",
				params : {
					archDistId : this.archivesId												
				},
				success : function() {
					try{						
						if(Ext.getCmp("ArchivesDistView")) Ext.getCmp("ArchivesDistView").gridPanel.store.reload();
						if(Ext.getCmp("DistArchivesPanelView")) Ext.getCmp("DistArchivesPanelView").refresh();						
					}catch(e){
						
					}
				}
			});
		}
	},
	closePanel : function() {
		this.close();
	},
	init : function() {
		this.panel = new Ext.Panel({
			autoHeight : true,
			autoScroll : true,
			autoLoad : {
				url : __ctxPath + "/pages/archive/archiveInfo.jsp?archivesId="
						+ this.archivesId
			}
		});
	}
});