SRAction = Ext.extend(Ext.util.Observable,{
	//获取个性化需求关联服务项ID
	getReqServiceItemId:function(reqId){
		var url = webContext+'/SRAction_getReqServiceItemId.action?reqId='+reqId;
		var data = this.ajaxGetPage(url);
		var siId = data.id;
		return siId;
	},
	getReqServiceItemShip:function(serviceId){
		var url = webContext+'/SRAction_getReqServiceItemShip.action?serviceId='+serviceId;
		var data = this.ajaxGetPage(url);
		return data;
	},
	
	getReqServiceItemName:function(reqId){
		var url = webContext+'/SRAction_getReqServiceItemId.action?reqId='+reqId;
		var data = this.ajaxGetPage(url);
		var name = data.name;
		return name;
	},
	getReqServiceProviderId:function(reqId){
		var url = webContext+'/SRAction_getReqServiceProviderId.action?reqId='+reqId;
		var data = this.ajaxGetPage(url);
		var spId = data.id;
		return spId;
	},
	getReqGroupFinanceInfoId:function(reqId){
		var url = webContext+'/SRAction_getReqGroupFinanceInfoId.action?reqId='+reqId;
		var data = this.ajaxGetPage(url);
		var spId = data.id;
		return spId;
	},
	getProjectRequireAnalyseId:function(reqId){
		var url = webContext+'/SRAction_getProjectRequireAnalyseId.action?reqId='+reqId;
		var data = this.ajaxGetPage(url);
		var praId = data.id;
		return praId;
	},
	getProjectTransmissionEngineerId:function(reqId){
		var url = webContext+'/SRAction_getProjectTransmissionEngineerId.action?reqId='+reqId;
		var data = this.ajaxGetPage(url);
		var pteId = data.id;
		return pteId;
	
	},
	//得到关系图的eid和名字
	getCIRelationShipPicInfo : function(reqId){
		var url = webContext+'/SRAction_getCIRelationShipPic.action?reqId='+reqId;
		var data = this.ajaxGetPage(url);
		//var praId = data.id;
		return data;
	},
	getRootProjectPlanId:function(reqId){
		var url = webContext+'/SRAction_getRootProjectPlanId.action?reqId='+reqId;
		var data = this.ajaxGetPage(url);
		var praId = data.id;
		return praId;
	},
	getProjectPlanId:function(reqId){
		var url = webContext+'/SRAction_getProjectPlanId.action?reqId='+reqId;
		var data = this.ajaxGetPage(url);
		var praId = data.id;
		return praId;
	},
	getReqEngineerId:function(reqId){
		var url = webContext+'/SRAction_getReqEngineerId.action?reqId='+reqId;
		var data = this.ajaxGetPage(url);
		var reId = data.id;
		return reId;
	},
	getUserId:function(reqId){
		var url = webContext+'/SRAction_getUserId.action?reqId='+reqId;
		var data = this.ajaxGetPage(url);
		var userId = data.id;
		return userId;
	},
	getassistantEngineerUserId:function(reqId){
		var url = webContext+'/SRAction_getassistantEngineerUserId.action?reqId='+reqId;
		var data = this.ajaxGetPage(url);
		var userId = data.id;
		return userId;
	},
	getRequirementContractId:function(reqId){
		var url = webContext+'/SRAction_getRequirementContractId.action?reqId='+reqId;
		var data = this.ajaxGetPage(url);
		var rcId = data.id;
		return rcId;
	},
	getProjectNoticeId:function(reqId){
		var url = webContext+'/SRAction_getProjectNoticeId.action?reqId='+reqId;
		var data = this.ajaxGetPage(url);
		var rcId = data.id;
		return rcId;
	},
	getEntityIdForPanel:function(panelName,reqId){
		var url = webContext+'/SRAction_getEntityIdForPanel.action?panel='+panelName+'&reqId='+reqId;
		var data = this.ajaxGetPage(url);
		var id = data.id;
		return id;
	},
	getProjectPlanGridTreeData:function(rootPlanId){
		var url = webContext + '/SRprojectPlanAction_getGridTreeData.action?rootPlanId='+rootPlanId;
		var data = this.ajaxGetData(url);
		return data;
	},
	getSpecialRequireSurveyId:function(){
		var url = webContext + '/SRAction_findSpecialRequireSurvey.action';
		var data = this.ajaxGetData(url);
		var surveyId = data.surveyId;
		return surveyId;
	},
	getServerManageCiData:function(dataId){
		var url = webContext + '/SRAction_findServerManageCiData.action?dataId='+dataId;
		var data = this.ajaxGetData(url);
		return data;
	},
	getCiDataByCiId:function(ciId){
		var url = webContext + '/SRAction_findServerCiDataByCiId.action?dataId='+ciId;
		var data = this.ajaxGetData(url);
		return data;
	},
	getTreeGridJsonStore:function(readerid,record,rootId){
		
		//alert(record);
		//alert(rootId);
		var url = webContext + '/SRprojectPlanAction_getGridTreeData.action?rootPlanId='+rootId;
		var data = this.ajaxGetData(url);
		var proxydata = new Ext.data.MemoryProxy(data);
		var store = new Ext.ux.maximgb.treegrid.NestedSetStore({
			autoLoad : true,
			reader : new Ext.data.JsonReader({
				id : 'SRflm_ProjectPlan$id'
			}, record),
			proxy : proxydata
		});
		return store;
	},
	getConfirmLockUser:function(taskId){
		var url = webContext
					+ '/requireAction_confirmLockUser.action'
					+ '?taskId=' + taskId;
		var data = this.ajaxGetData(url);
		var isLockUser =data.isLockUser;
		var lockUserName = data.lockUserName;
		if(!isLockUser  ){
			if(lockUserName ==null){
				isLockUser=true;
			}else{
				Ext.MessageBox.alert("提示","本流程节点已被工程师："+lockUserName+" 加锁，您只有查看权限");
			}	
		}		
		return isLockUser;
	},
	selectFinanceType:function(dataId){
		var url = webContext
					+ '/requireAction_selectFinanceType.action'
					+ '?dataId=' + dataId;
		var data = this.ajaxGetData(url);
		var financeType = data.financeType;
		return financeType; 
	},
	// 串行申请服务端数据,不对外
	ajaxGetData : function(url) {
		var conn = Ext.lib.Ajax.getConnectionObject().conn;
		conn.open("post", url, false);
		conn.send(null);
		
		if (conn.status == "200") {
			var responseText = conn.responseText;
			if(responseText!=null&&responseText.indexOf("<!DOCTYPE ")>=0){
				Ext.MessageBox.alert("提示信息：","下列请求所返回的数据不正确：\n<br>'"+url+"'");
				return;
			}
			if(responseText==""){
			return "";
			}
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
