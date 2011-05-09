var webContext = "";
var curpath = window.location.pathname;
var index = curpath.indexOf("/",1);
webContext = curpath.substr(0,index);

SpecialRequireAction = Ext.extend(Ext.util.Observable,{
	getReqServiceItemId:function(reqId,clazz){
		var url = webContext+'/specialRequireAction_getReqServiceItemId.action?reqId='+reqId+'&reqClass='+clazz;
		var data = this.ajaxGetPage(url);
		var siId = data.id;
		return siId;
	},
	
	getReqServiceItemShip:function(serviceId){
		var url = webContext+'/specialRequireAction_getReqServiceItemShip.action?serviceId='+serviceId;
		var data = this.ajaxGetPage(url);
		return data;
	},
	
	getReqServiceItemName:function(reqId,clazz){
		var url = webContext+'/specialRequireAction_getReqServiceItemId.action?reqId='+reqId+'&reqClass='+clazz;
		var data = this.ajaxGetPage(url);
		var name = data.name;
		return name;
	},
	getReqServiceProviderId:function(reqId,clazz){
		var url = webContext+'/specialRequireAction_getReqServiceProviderId.action?reqId='+reqId+'&reqClass='+clazz;
		var data = this.ajaxGetPage(url);
		var spId = data.id;
		return spId;
	},
	
	getReqGroupFinanceInfoId:function(reqId,clazz){
		var url = webContext+'/specialRequireAction_getReqGroupFinanceInfoId.action?reqId='+reqId+'&reqClass='+clazz;
		var data = this.ajaxGetPage(url);
		var spId = data.id;
		return spId;
	},
	getProjectRequireAnalyseId:function(reqId,clazz){
		var url = webContext+'/specialRequireAction_getProjectRequireAnalyseId.action?reqId='+reqId+'&reqClass='+clazz;
		var data = this.ajaxGetPage(url);
		var praId = data.id;
		return praId;
	},
	getProjectTransmissionEngineerId:function(reqId,clazz){
		var url = webContext+'/specialRequireAction_getProjectTransmissionEngineerId.action?reqId='+reqId+'&reqClass='+clazz;
		var data = this.ajaxGetPage(url);
		var pteId = data.id;
		return pteId;
	
	},
	//得到关系图的eid和名字
	getCIRelationShipPicInfo : function(reqId,clazz){
		var url = webContext+'/specialRequireAction_getCIRelationShipPic.action?reqId='+reqId+'&reqClass='+clazz;
		var data = this.ajaxGetPage(url);
		//var praId = data.id;
		return data;
	},
	getRootProjectPlanId:function(reqId,clazz){
		var url = webContext+'/specialRequireAction_getRootProjectPlanId.action?reqId='+reqId+'&reqClass='+clazz;
		var data = this.ajaxGetPage(url);
		var praId = data.id;
		return praId;
	},
	getProjectPlanId:function(reqId,clazz){
		var url = webContext+'/specialRequireAction_getProjectPlanId.action?reqId='+reqId+'&reqClass='+clazz;
		var data = this.ajaxGetPage(url);
		var praId = data.id;
		return praId;
	},
	getReqEngineerId:function(reqId,clazz){
		var url = webContext+'/specialRequireAction_getReqEngineerId.action?reqId='+reqId+'&reqClass='+clazz;
		var data = this.ajaxGetPage(url);
		var reId = data.id;
		return reId;
	},
	getUserId:function(reqId,clazz){
		var url = webContext+'/specialRequireAction_getUserId.action?reqId='+reqId+'&reqClass='+clazz;
		var data = this.ajaxGetPage(url);
		var userId = data.id;
		return userId;
	},
	getassistantEngineerUserId:function(reqId,clazz){
		var url = webContext+'/specialRequireAction_getassistantEngineerUserId.action?reqId='+reqId+'&reqClass='+clazz;
		var data = this.ajaxGetPage(url);
		var userId = data.id;
		return userId;
	},
	getRequirementContractId:function(reqId,clazz){
		var url = webContext+'/specialRequireAction_getRequirementContractId.action?reqId='+reqId+'&reqClass='+clazz;
		var data = this.ajaxGetPage(url);
		var rcId = data.id;
		return rcId;
	},
	getProjectNoticeId:function(reqId,clazz){
		var url = webContext+'/specialRequireAction_getProjectNoticeId.action?reqId='+reqId+'&reqClass='+clazz;
		var data = this.ajaxGetPage(url);
		var rcId = data.id;
		return rcId;
	},
	getEntityIdForPanel:function(panelName,reqId,clazz){
		var url = webContext+'/specialRequireAction_getEntityIdForPanel.action?panel='+panelName+'&reqId='+reqId+'&reqClass='+clazz;
		var data = this.ajaxGetPage(url);
		var id = data.id;
		return id;
	},
	getProjectPlanGridTreeData:function(rootPlanId){
		var url = webContext + '/projectPlanAction_getGridTreeData.action?rootPlanId='+rootPlanId;
		var data = this.ajaxGetData(url);
		return data;
	},
	getTreeGridJsonStore:function(readerid,record,rootId){
		var url = webContext + '/projectPlanAction_getGridTreeData.action?rootPlanId='+rootId;
		var data = this.ajaxGetData(url);
		var proxydata = new Ext.data.MemoryProxy(data);
		var store = new Ext.ux.maximgb.treegrid.NestedSetStore({
			autoLoad : true,
			reader : new Ext.data.JsonReader({
				id : 'flm_ProjectPlan$id'
			}, record),
			proxy : proxydata
		});
		return store;
	},
	// 串行申请服务端数据,不对外
	ajaxGetData : function(url) {
		var conn = Ext.lib.Ajax.getConnectionObject().conn;
		conn.open("post", url, false);
		conn.send(null);
		if (conn.status == "200") {
			//alert(conn.responseText);
			var responseText = conn.responseText;
			if(responseText!=null&&responseText.indexOf("<!DOCTYPE ")>=0){
				Ext.MessageBox.alert("提示信息：","下列请求所返回的数据不正确：\n<br>'"+url+"'");
				return;
			}
			//alert(responseText);
			responseText = clearReturn(responseText);
			var data = eval("(" + responseText + ")");
			return data;
		} else {
			return 'no result';
		}
	},

	ajaxGetPage:function(url){
		var conn = Ext.lib.Ajax.getConnectionObject().conn;
		conn.open("post",url, false);
		conn.send(null);
		if (conn.status == "200") {
			//alert(conn.responseText);
			//注意只有这种格式才能够真正的解析json
			var data = eval('('+conn.responseText+')');
			return data;
		} else {
			return 'no result';
		}
	},


	//4列form布局
	split : function(data) {
		var labellen = 135;
		var itemlen = 200;
		var throulen = 530;
		if(Ext.isIE){
			throulen = 530;
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
					if ((i-hid+longitems) % 2 == 1) {//在右侧栏，前一栏贯通
						longData[2 * (i - hid) - 1].colspan = 3;
					}
					else{//多占一栏
						longitems ++;
					}
					data[i].colspan = 3;//本栏贯通
					data[i].width = throulen;
					if(data[i].xtype == "textarea"){
						data[i].height = 200;
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
	}

});
