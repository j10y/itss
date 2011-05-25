function getFormParam(panelId,overwrite) {
	var string = Ext.getCmp(panelId).form.getValues(true);
	if(!string || !string.length){
                return {};
            }
    var obj = {};
    var pairs = string.split('&');
    var pair, name, value;
    for(var i = 0, len = pairs.length; i < len; i++){
        pair = pairs[i].split('=');
        name = decodeURIComponent(pair[0]);
        value = Ext.encode(decodeURIComponent(pair[1]));
        if(value == ""){
        	value = null;
        }else{
        	value = value.substring(1,value.length-1);
        }
        if(overwrite !== true){
            if(typeof obj[name] == "undefined"){
                obj[name] = value;
            }else if(typeof obj[name] == "string"){
                obj[name] = [obj[name]];
                obj[name].push(value);
            }else{
                obj[name].push(value);
            }
        }else{
            obj[name] = value;
        }
    }
    return obj;
}

function showVailed(value) {
	Ext.example.msg('提示信息', '{0}', value);
}

/*
 * Unicode
 */
function unicode(s) {
	var len = s.length;
	var rs = "";
	for (var i = 0; i < len; i++) {
		var k = s.substring(i, i + 1);
		rs += "&#" + s.charCodeAt(i) + ";";
	}
	return rs;
}
/*
 *add by liuying at 20100609
 *参数为组件ID 返回转译后并去掉首尾双引号的字符串 
 */
function getEncodeValue(s){
	var v=Ext.getCmp(s).getValue();
	var temp=Ext.encode(v);
	return temp.substring(1,temp.length-1);
}

/*
 * 反unicode
 */
function runicode(s) {
	var k = s.split(";");
	var r = "";
	for (var x = 0; x < k.length; x++) {
		var m = k[x].replace(/&#/, "");
		r += String.fromCharCode(m);
	}
	return r;
}

// 清除字符串中的回行字符，以避免js解析出错，在js对字符串解析(如eval)前调用
function clearReturn(s) {
	// var x = s.replace(/\s+/g,"\\n");
	var x = s.replace(/(\r\n)+/g, "");
	return x;
}

function encodeReturns(s) {
	var x = s.replace(/(\r\n)+/g, "\\r\\n");
	return x;
}

function encodeHtmlReturns(s) {
	var x = s.replace(/(\r\n)+/g, "<BR>");
	return x;
}

DataAction = Ext.extend(Ext.util.Observable, {

	getPanelData : function(panelname, dataId) {
		var url = webContext
				+ '/extjs/panelData?method=findPanelDataById&panelname='
				+ panelname + '&dataId=' + dataId;
		var data = this.ajaxGetpage(url);
		return data;
	},

	//获取pageModel数据
	getPageModel : function(name, dataId) {
		var url = webContext + '/extjs/pageView?method=pageModel&name=' + name
				+ '&dataId=' + dataId;
		var data = this.ajaxGetpage(url);
		return data;
	},

	/*************************STRUTS2**********************/
	getPageModelForConfigItem : function(name, dataId) {
		//var url = webContext+'/extjs/pageView?method=pageModelConfigItem&name='+name+'&dataId='+dataId;
		//var data = this.ajaxGetpage(url);
		//return data;          
		var url = webContext + '/ci_forModelConfigItem.action?name=' + name
				+ '&dataId=' + dataId;
		var data = this.ajaxGetData(url);
		return data;
	},

	getPageModelAdd : function(name, configItemTypeId) {
		var url = webContext + '/extjs/pageView?method=pageModelAdd&name='
				+ name + '&configItemTypeId=' + configItemTypeId;
		var data = this.ajaxGetpage(url);
		return data;
	},

	getPageModelAddForConfigItem : function(name, configItemTypeId) {
		var url = webContext + '/ci_forModelForAdd.action?name=' + name
				+ '&configItemTypeId=' + configItemTypeId;
		var data = this.ajaxGetpage(url);
		return data;
	},

	/************************STRUTS2***************************/
	getPageModelCustPanels : function(modelName, configItemTypeId) {
		//var url = webContext+'/extjs/pageView?method=custPanel&modelName='+modelName+'&configItemTypeId='+configItemTypeId;
		//var data = this.ajaxGetpage(url);
		//return data;           
		var url = webContext + '/ci_forCustPanel.action?panelname=' + panelname;
		var data = this.ajaxGetData(url);
		return data;
	},

	getPageModelForAdd : function(modelName, configItemTypeId) {
		var url = webContext + '/extjs/pageView?method=pageModelAdd&modelName='
				+ modelName + '&configItemTypeId=' + configItemTypeId;
		var data = this.ajaxGetpage(url);
		return data;
	},
	//2期框架获得新增时的元数据
	getPanelElementsForQuery : function(panelname) {
		var url = webContext + '/extjs/pageView?method=query&panelname='
				+ panelname;
		var data = this.ajaxGetData(url);
		// alert(Ext.encode(data));
		return data;
	},

	//2期框架获得新增时的元数据
	getPanelElementsForAdd : function(panelname) {
		//var url = webContext+'/extjs/metaData?method=add&clazz='+clazz;  
		var url = webContext + '/extjs/pageView?method=save&panelname='
				+ panelname;
		var data = this.ajaxGetData(url);
		//  alert(Ext.encode(data));
		return data;
	},
	//2期框架获得编辑时的元数据
	getSingleFormPanelElementsForEdit : function(panelname, id) {
		//var url = webContext+'/extjs/metaData?method=edit&clazz='+clazz+'&id='+id;
		var url = webContext
				+ '/extjs/pageView?method=saveSingleForm&panelname='
				+ panelname + '&id=' + id;
		//alert("2");
		var data = this.ajaxGetData(url);
		return data;
	},
	//2期框架获得编辑时的元数据
	getSingleFormPanelElementsForLook : function(panelname, id) {
		var url = webContext
				+ '/extjs/pageView?method=saveSingleFormForLook&panelname='
				+ panelname + '&id=' + id;
		var data = this.ajaxGetData(url);
		return data;
	},
	//需求管理获取普通流程主面板元数据
	getRequireFormPanelElementsForEdit : function(panelname, id, oldId) {
		var url = webContext
				+ '/requireAction_getApplyRequireFormPanel.action?panelname='
				+ panelname + '&id=' + id + '&oldId=' + oldId;
		//alert("2");
		var data = this.ajaxGetData(url);
		return data;
	},
	//需求管理获取普通流程主面板元数据
	getModifyRequireFormPanelElementsForEdit : function(panelname, id, oldId) {
		var url = webContext
				+ '/requireAction_getModifyRequireFormPanel.action?panelname='
				+ panelname + '&id=' + id + '&oldId=' + oldId;
		//alert("2");
		var data = this.ajaxGetData(url);
		return data;
	},
	getKnowledgeFormPanelElementsForEdit : function(panelname, id) {
		var url = webContext
				+ '/knowledgeAction_getKnowledgePanelData.action?panelname='
				+ panelname + '&id=' + id;
		//alert("2");
		var data = this.ajaxGetData(url);
		return data;
	},
	//2期框架获得树形列表（treeGrid）提供表头数据
	getGridTreeHead : function(panelname) {
		var url = webContext + '/extjs/pageView?method=gridTreeHead&panelname='
				+ panelname;
		var data = this.ajaxGetData(url);
		return data;
	},
	//2期框架获得编辑时的元数据
	getPanelElementsForEdit : function(modelname, panelname, id) {
		//var url = webContext+'/extjs/metaData?method=edit&clazz='+clazz+'&id='+id;
		var url = webContext + '/extjs/pageView?method=save&modelname='
				+ modelname + '&panelname=' + panelname + '&id=' + id;
		var data = this.ajaxGetData(url);
		return data;
	},
	//2期框架获得查看数据
	getPanelElementsForLook : function(modelname, panelname, id) {
		//var url = webContext+'/extjs/metaData?method=edit&clazz='+clazz+'&id='+id;
		var url = webContext + '/extjs/pageView?method=look&modelname='
				+ modelname + '&panelname=' + panelname + '&id=' + id;
		//alert("2");
		var data = this.ajaxGetData(url);
		return data;
	},

	//2期框架为列表页面初始表头，列表页面初始表头只需要字段信息，不需要数据
	getListPanelElementsForHead : function(panelname) { //alert("getListPanelElementsForHead");
		var url = webContext + '/extjs/pageView?method=listHead&panelname='
				+ panelname;
		//alert("3");
		var data = this.ajaxGetData(url);
		return data;
	},

	//2期框架为表单页面的grid panel初始表头
	getPanelElementsForHead : function(panelname) { //alert("getPanelElementsForHead");
		var url = webContext + '/extjs/pageView?method=head&panelname='
				+ panelname;
		//alert("3");
		var data = this.ajaxGetData(url);
		return data;
	},

	//2期框架，为配置项单独获得新增时的元数据
	/************************STRUTS2***************************/
	getPanelConfigItemElementsForAdd : function(panelname) {
		//var url = webContext+'/extjs/pageView?method=saveConfigItem&panelname='+panelname;
		var url = webContext + '/ci_forSaveConfigItemPanel.action?panelname='
				+ panelname;
		var data = this.ajaxGetData(url);
		return data;
	},

	//2期框架，为配置项单独获得编辑时的元数据
	/************************STRUTS2***************************/
	getPanelConfigItemElementsForEdit : function(modelname, panelname, id) {

		//var url = webContext+'/extjs/pageView?method=saveConfigItem&modelname='+modelname+'&panelname='+panelname+'&id='+id;
		var url = webContext + '/ci_forSaveConfigItem.action?modelname='
				+ modelname + '&panelname=' + panelname + '&id=' + id;
		var data = this.ajaxGetData(url);
		if (data == "no result") {
			alert("当前配置项未发布，无法正常显示页面!");
			history.go(-1);
		}
		return data;
	},
	//********** 

	//获得增加时的元数据
	getElementsForAdd : function(clazz) {
		//var url = webContext+'/extjs/metaData?method=add&clazz='+clazz;  
		var url = webContext + '/extjs/metaData?method=save&clazz=' + clazz;
		var data = this.ajaxGetData(url);
		//alert(Ext.encode(data));
		return data;
	},
	//获得增加时的元数据(只读)     
	//获得编辑时的元数据
	getElementsForLook : function(clazz, id) {
		//var url = webContext+'/extjs/metaData?method=edit&clazz='+clazz+'&id='+id;
		var url = webContext + '/extjs/metaData?method=look&clazz=' + clazz
				+ '&id=' + id;
		var data = this.ajaxGetData(url);
		return data;
	},

	//获得编辑时的元数据
	getElementsForEdit : function(clazz, id) {
		//var url = webContext+'/extjs/metaData?method=edit&clazz='+clazz+'&id='+id;
		var url = webContext + '/extjs/metaData?method=save&clazz=' + clazz
				+ '&id=' + id;
		var data = this.ajaxGetData(url);
		return data;
	},
	//获得编辑时的无实体部分数据(现只专用于服务项无实体数据)
	getElementForIncorporeity : function(dataId) {
		var url = webContext + '/serviceItem_getItems.action?dataId=' + dataId;
		var data = this.ajaxGetData(url);
		return data;
	},

	//获得表头的元数据
	getElementsForHead : function(clazz) { //alert("getElementsForHead");
		var url = webContext + '/extjs/metaData?method=head&clazz=' + clazz;
		var data = this.ajaxGetData(url);
		return data;
	},

	//获得查询的元数据
	getElementsForQuery : function(clazz) {
		var url = webContext + '/extjs/metaData?method=query&clazz=' + clazz;
		var data = this.ajaxGetData(url);
		// alert(Ext.encode(data));
		return data;
	},

	//获得html格式的查询表单数据
	getHtmlElementsForQuery : function(clazz) {
		var url = webContext + '/extjs/metaData?method=html&clazz=' + clazz;
		var data = this.ajaxGetData(url);
		//alert(Ext.encode(data));
		return data;
	},

	//同步调用查询函数
	queryData : function(clazz, queryParams) {
		var url = webContext + '/extjs/metaData?method=query&'
				+ Ext.urlEncode(queryParams);
		var data = this.ajaxGetData(url);
		// alert(Ext.encode(data));
		return data
	},

	//grid异步调用查询函数,
	getJsonStore : function(clazz) { //alert("getJsonStore");
		var url = webContext + '/extjs/metaData?method=head&clazz=' + clazz;
		var data = this.ajaxGetData(url);
		var fields = new Array();
		for (i = 0; i < data.length; i++) {
			fields[i] = data[i].dataIndex;
		}
		var store = new Ext.data.JsonStore({
			id : Ext.id(),
			url : webContext + '/extjs/dataAction?method=query&clazz=' + clazz,
			root : "data",
			fields : fields,
			totalProperty : "rowCount",
			remoteSort : false,
			timeout : 8000
				//sortInfo:{field:'id',direction:'ASC'}
		});
		return store;
	},
	
	//grid异步调用查询函数,过滤获取用户角色权限可查看数据
	getJsonStoreForUser : function(clazz,propertyName) {
		var url = webContext + '/extjs/metaData?method=head&clazz=' + clazz;
		var data = this.ajaxGetData(url);
		var fields = new Array();
		for (i = 0; i < data.length; i++) {
			fields[i] = data[i].dataIndex;
		}
		var store = new Ext.data.JsonStore({
			id : Ext.id(),
			url : webContext + '/extjs/dataAction?method=queryForUser&clazz=' + clazz + '&propertyName=' + propertyName,
			root : "data",
			fields : fields,
			totalProperty : "rowCount",
			remoteSort : false,
			timeout : 8000
				//sortInfo:{field:'id',direction:'ASC'}
		});
		return store;
	},
	
	//grid异步调用查询函数, 改进版，减少对表头的多余获取
	getJsonStore2 : function(clazz, header) { //alert("getJsonStore");
		//var url = webContext+'/extjs/metaData?method=head&clazz='+clazz;
		//var data = this.ajaxGetData(url); 
		var fields = new Array();
		for (i = 0; i < header.length; i++) {
			fields[i] = header[i].dataIndex;
		}
		var store = new Ext.data.JsonStore({
			id : Ext.id(),
			url : webContext + '/extjs/dataAction?method=query&clazz=' + clazz,
			root : "data",
			fields : fields,
			totalProperty : "rowCount",
			remoteSort : false,
			timeout : 8000
				//sortInfo:{field:'id',direction:'ASC'}
		});
		return store;
	},

	//grid异步调用查询函数, 新修改，增加主对象id
	getPanelJsonStore : function(panelname) { //alert("getPanelJsonStore");
		// alert(girdId);
		var url = webContext + '/extjs/pageView?method=head&panelname='
				+ panelname;
		var data = this.ajaxGetData(url);
		var dataStr = Ext.util.JSON.encode(data);
		var fields = new Array();
		for (i = 0; i < data.length; i++) {
			fields[i] = data[i].dataIndex;
		}

		var store = new Ext.data.JsonStore({
			id : Ext.id(),
			url : webContext + '/extjs/pageData?method=query&panelname='
					+ panelname, //改成pageQuery测试
			root : "data",
			fields : fields,
			totalProperty : "rowCount",
			remoteSort : false,
			timeout : 8000
				//sortInfo:{field:'id',direction:'ASC'}
		});
		return store;
	},

	//grid异步调用查询函数, 新修改，增加对用户查看数据权限的过滤
	getPanelJsonStoreForUser : function(panelname) { //alert("getPanelJsonStore");
		// alert(girdId);
		var url = webContext + '/extjs/pageView?method=head&panelname='
				+ panelname;
		var data = this.ajaxGetData(url);
		var dataStr = Ext.util.JSON.encode(data);
		var fields = new Array();
		for (i = 0; i < data.length; i++) {
			fields[i] = data[i].dataIndex;
		}

		var store = new Ext.data.JsonStore({
			id : Ext.id(),
			url : webContext + '/extjs/pageData?method=queryForUser&panelname='
					+ panelname, //改成pageQuery测试
			root : "data",
			fields : fields,
			totalProperty : "rowCount",
			remoteSort : false,
			timeout : 8000
				//sortInfo:{field:'id',direction:'ASC'}
		});
		return store;
	},
	
	//2期框架新增，grid异步调用查询函数, 列表页面使用，直接使用前面查询获得表头信息，必须多获取1次表头
	getPanelJsonStore : function(panelname, header) { //modfiy by peixf 
		//var dataStr = Ext.util.JSON.encode(header);
		//alert(dataStr);
		var fields = new Array();
		for (i = 0; i < header.length; i++) {
			fields[i] = header[i].dataIndex;
		}

		var store = new Ext.data.JsonStore({
			id : Ext.id(),
			url : webContext + '/extjs/pageData?method=query&panelname='
					+ panelname, //改成pageQuery测试
			root : "data",
			fields : fields,
			totalProperty : "rowCount",
			remoteSort : false,
			timeout : 8000
				//sortInfo:{field:'id',direction:'ASC'}
		});
		return store;
	},
	
	//2期框架新增，grid异步调用查询函数, 列表页面使用，直接使用前面查询获得表头信息，必须多获取1次表头
	getPanelJsonStoreForUser : function(panelname, header,propertyName) { //modfiy by peixf 
		//var dataStr = Ext.util.JSON.encode(header);
		//alert(dataStr);
		var fields = new Array();
		for (i = 0; i < header.length; i++) {
			fields[i] = header[i].dataIndex;
		}

		var store = new Ext.data.JsonStore({
			id : Ext.id(),
			url : webContext + '/extjs/pageData?method=queryForUser&panelname='
					+ panelname +'&propertyName='+propertyName, //改成pageQuery测试
			root : "data",
			fields : fields,
			totalProperty : "rowCount",
			remoteSort : false,
			timeout : 8000
				//sortInfo:{field:'id',direction:'ASC'}
		});
		return store;
	},

	//2期框架新增，grid异步调用查询函数, 列表页面使用，直接使用前面查询获得表头信息，必须多获取1次表头
	getPanelJsonStore : function(panelname, header) { //modfiy by peixf 
		//var dataStr = Ext.util.JSON.encode(header);
		//alert(dataStr);
		var fields = new Array();
		for (i = 0; i < header.length; i++) {
			fields[i] = header[i].dataIndex;
		}

		var store = new Ext.data.JsonStore({
			id : Ext.id(),
			url : webContext + '/extjs/pageData?method=query&panelname='
					+ panelname, //改成pageQuery测试
			root : "data",
			fields : fields,
			totalProperty : "rowCount",
			remoteSort : false,
			timeout : 8000
				//sortInfo:{field:'id',direction:'ASC'}
		});
		return store;
	},
	//2期框架新增，grid异步调用查询函数, 表单页面的使用
	getPagePanelJsonStore : function(panelname, header) { //modfiy by peixf 
		// alert(girdId);
		//var url = webContext+'/extjs/pageView?method=head&panelname='+panelname;
		//var data = this.ajaxGetData(url); 
		var fields = new Array();
		for (i = 0; i < header.length; i++) {
			fields[i] = header[i].dataIndex;
		}

		var store = new Ext.data.JsonStore({
			id : Ext.id(),
			url : webContext + '/extjs/pageData?method=pageQuery&panelname='
					+ panelname, //改成pageQuery测试
			root : "data",
			fields : fields,
			totalProperty : "rowCount",
			remoteSort : false,
			timeout : 8000
				//sortInfo:{field:'id',direction:'ASC'}
		});
		return store;
	},

	//2期框架新增，完成表单页面editorgrid数据的初始化,by peixf
	getPanelEditorJsonStore : function(modelname, panelname, dataId) { //alert("getPanelEditorJsonStore");
		// alert(girdId);
		var url = webContext + '/extjs/pageView?method=head&panelname='
				+ panelname;
		var data = this.ajaxGetData(url);
		var fields = new Array();
		for (i = 0; i < data.length; i++) {
			fields[i] = data[i].dataIndex;
		}
		var url2 = webContext + '/extjs/pageView?method=save&modelname='
				+ modelname + '&panelname=' + panelname + '&id=' + dataId;;

		var store = new Ext.data.JsonStore({
			id : Ext.id(),
			url : url2, //webContext+'/extjs/pageView?method=query&panelname='+panelname,
			root : "data",
			fields : fields,
			totalProperty : "rowCount",
			remoteSort : false,
			timeout : 8000
				//sortInfo:{field:'id',direction:'ASC'}
		});
		return store;
	},

	//导出数据
	exportData : function(clazz, mainList, fileRootPath, sheetName, filePrefix) {

	},

	unicodeValues : function(obj) {
		for (var v in obj) {
			obj[v] = unicode(obj[v]);
		}
		return obj;
	},

	//异步保存数据,成功时回调函数为successfn(response,options)
	saveData : function(clazz, params, successfn) {
		var vp = null;
		if (params != null) {
			vp = Ext.apply(params, vp, {});
			vp = this.unicodeValues(vp);
		}
		//alert(Ext.encode(params));
		Ext.Ajax.request({
			url : webContext + '/extjs/dataAction?method=save&clazz=' + clazz,
			params : vp,
			method : 'post',
			success : successfn,
			failure : function(response, options) {
				var p = clazz.lastIndexOf(".") + 1;
				Ext.MessageBox.alert("提示信息：", "保存数据失败(" + clazz.substring(p)
						+ ")");
			}
		});
	},
	saveDataFromPanel : function(panel, params, successfn) {		
		Ext.Ajax.request({
			url:webContext + '/requireAction_savePanelData.action',
			params:{
				pagePanel:panel,
				info:params
			},
			method : 'post',
			success : successfn,
			failure : function(response, options) {
				Ext.MessageBox.alert("提示信息：", "保存数据失败");
			}
		});
	},	
	//form异步保存数据，需要调用unicodeValues函数编码参数
	getSaveOptions : function(clazz, params) {
		params = this.unicodeValues(params);
		var options = {
			method : "post",
			params : params,
			url : webContext + '/extjs/dataAction?method=save&clazz=' + clazz,
			success : function(response, options) {
				var p = clazz.lastIndexOf(".") + 1;
				Ext.MessageBox.alert("提示信息：", "保存数据成功(" + clazz.substring(p)
						+ ")");
			},
			failure : function(response, options) {
				var p = clazz.lastIndexOf(".") + 1;
				Ext.MessageBox.alert("提示信息：", "保存数据失败(" + clazz.substring(p)
						+ ")");
			},
			scope : this,
			clientValidation : false
				//以后改成true
		}
		return options;
	},

	//异步删除数据
	removeData : function(clazz, params) {
		Ext.Ajax.request({
			url : webContext + '/extjs/dataAction?method=remove&clazz=' + clazz,
			params : params,
			method : 'post',
			success : function(response, options) {
				var p = clazz.lastIndexOf(".") + 1;
				Ext.MessageBox.alert("提示信息：", "删除数据成功(" + clazz.substring(p)
						+ "[" + Ext.encode(params) + "])");
			},
			failure : function(response, options) {
				var p = clazz.lastIndexOf(".") + 1;
				Ext.MessageBox.alert("提示信息：", "删除数据失败(" + clazz.substring(p)
						+ "[" + Ext.encode(params) + "])");
			}
		});
	},

	//获得工作流任务列表
	next : function(processId) {
		url : webContext + '/extjs/workflow?method=next&procId=' + processId;
		var data = this.ajaxGetData(url);
		return data;
	},

	//获得工作流任务列表
	tasks : function(actorId) {
		url : webContext + '/extjs/workflow?method=tasks&actorId=' + actorId;
		var data = this.ajaxGetData(url);
		return data;
	},

	// 异步启动工作流
	apply : function(defname, param, fn, deptcode, userAssign) {
		// 必须提前保存，获得申请Id,才能提交审批，业务数据包括申请类型，申请编号，最终客户名称（与流程有关）
		// da.apply("orderbid",{applyType:'bidApply',applyTypeName:'BID单下单申请',applyId:this.dataId,customerName:this.finalCustomer});
		//alert("asd");
		var paramDeptCode = "";
		var paramUserAssign = "";
		if (deptcode != null && deptcode != "undefined" && deptcode != "") {
			paramDeptCode = "&deptcode=" + deptcode;
		}
		if (userAssign != null && userAssign != "undefined" && userAssign != "") {
			paramUserAssign = "&userAssign=" + userAssign
		}
		var params = 'method=apply&defname=' + defname + "&bzparam="
				+ Ext.encode(param) + paramDeptCode + paramUserAssign;
		//alert(params);

		Ext.Ajax.request({
			url : webContext + '/extjs/workflow?' + params,
			method : 'post',
			scope : this,
			// params:Ext.urlDecode(params),
			success : function(response, options) {
				//alert(response.responseText);
				var data = eval("(" + response.responseText + ")");
				if (data.id == 'role') {//申请用户填写预指派人员
					this.popUserAssignWin(defname, deptcode, data.roles, param,
							fn);
				} else {
					Ext.MessageBox.alert("提示信息：", "启动工作流('" + defname + "')成功",
							function(btn) {
								if (btn == 'ok') {
									fn();
								}
							});
				}
			},
			failure : function(response, options) {
				Ext.MessageBox.alert("提示信息：", "启动工作流('" + defname + "')失败");
			}
		}, this);
	},

	//用户指派窗口
	popUserAssignWin : function(defname, deptcode, roles, param, fn) {
		var da = new DataAction();
		var url = webContext + '/workflow/preassign.do?methodCall=workmates';
		var fromdata = da.ajaxGetData(url);

		var todata = [];//da.ajaxGetData(url);
		var item = {
			colspan : 3,
			xtype : "itemselector",
			name : "proxySelect",
			//fieldLabel:"双栏选框",
			dataFields : ["code", "desc"],
			fromData : fromdata,
			toData : todata,
			msWidth : 195,
			style : "margin: 0,23,10,13",
			autoScroll : true,
			msHeight : 150,
			width : 500,
			frame : true,
			valueField : "code",
			displayField : "desc",
			imagePath : webContext + "/js/",
			//switchToFrom:true,
			fieldLabel : "选择执行人",
			toLegend : "已选人",
			fromLegend : "可选人"
		};

		var formitems = new Array();
		for (i = 0; i < roles.length; i++) {
			var taskName = {
				html : "流程角色：" + roles[i].name,
				style : 'font-size:9pt',
				colspan : 3
			};
			var seleUser = {};
			Ext.apply(seleUser, item, {});
			seleUser.name = roles[i].id;
			seleUser.id = roles[i].id + "componentId";
			formitems[i * 2] = taskName;
			formitems[i * 2 + 1] = seleUser;
		}
		//alert(Ext.encode(formitems));
		var assignForm = new Ext.form.FormPanel({
			layout : 'table',
			width : 420,
			frame : true,
			//autoScroll :true,
			viewConfig : {
				autoFill : true,
				forceFit : true
			},
			defaults : {
				bodyStyle : 'padding:4px'
			},
			layoutConfig : {
				columns : 3
			},
			items : formitems
		});
		var win = new Ext.Window({
			title : '流程审批空置角色指派',
			modal : true,
			height : 450,
			width : 450,
			autoScroll : true,
			resizable : false,
			buttons : [{
				xtype : 'button',
				handler : function() {
					// 表单数据
					var bsParam = assignForm.form.getValues(false);
					var formParam = Ext.encode(bsParam);
					var valid = true;
					for (key in bsParam) {
						if (bsParam[key] == "") {
							Ext.MessageBox.alert("提示信息：",
									"流程中有空角色没有被指定用户,请确保选择完全。");
							Ext.getCmp(key + "componentId")
									.markInvalid("请选择至少一项。");
							valid = false;
						} else {
							Ext.getCmp(key + "componentId").clearInvalid();
						}
					}
					if (!valid) {
						return;
					}
					this.apply(defname, param, fn, deptcode, formParam);
					win.close();
				},
				text : '保存',
				scope : this
			}, {
				xtype : 'button',
				handler : function() {
					win.close()
				},
				text : '关闭',
				scope : this
			}],
			items : [assignForm]

		})
		win.show();
		//空角色，人员
	},

	//异步执行工作流任务，首次进入，下一步弹出审核页面
	audit : function(id) {
		Ext.Ajax.request({
			url : webContext + '/extjs/workflow?id=' + id,
			method : 'post',
			success : function(response, options) {
				var data = eval("(" + response.responseText + ")");
				var formName = data.formName;
				if (formName != '') {
					//                                                alert(formName);
				} else {
					Ext.MessageBox.alert("提示信息：", "审批任务(" + id + ")完成");
				}
			},
			failure : function(response, options) {
				Ext.MessageBox.alert("提示信息：", "审批任务(" + id + ")失败");
			}
		});
	},

	// 串行申请服务端数据,不对外
	ajaxGetData : function(url) {
		var data = null;
		Ext.Ajax.request({  
	        url: url,  
	        async: false,  
	        success: function(response, opts) { 
	        	data = Ext.util.JSON.decode(response.responseText);
	        }  
	    }); 
	    return data;
//		
//		var conn = Ext.lib.Ajax.getConnectionObject().conn;
//		conn.open("post", url, false);
//		conn.send(null);
//		if (conn.status == "200") {
//			//alert(conn.responseText);
//			var responseText = conn.responseText;
//			if (responseText != null && responseText.indexOf("<!DOCTYPE ") >= 0) {
//				Ext.MessageBox.alert("提示信息：", "下列请求所返回的数据不正确：\n<br>'" + url
//						+ "'");
//				return;
//			}
//			//alert(responseText);
//			responseText = clearReturn(responseText);
//			var data = eval("(" + responseText + ")");
//			return data;
//		} else {
//			return 'no result';
//		}
	},

	ajaxGetpage : function(url) {
		Ext.Ajax.request({  
	        url: url,  
	        async: false,  
	        success: function(response, opts) {
	        	
	         	var data = Ext.util.JSON.decode(response.responseText);
	         	return data;
	        }  
	    }); 
//		var conn = Ext.lib.Ajax.getConnectionObject().conn;
//		conn.open("post", url, false);
//		conn.send(null);
//		if (conn.status == "200") {
//			//alert(conn.responseText);
//			//注意只有这种格式才能够真正的解析json
//			var data = eval('(' + conn.responseText + ')');
//			return data;
//		} else {
//			return 'no result';
//		}
	},

	//4列form布局
	split : function(data) {
		var labellen = 135;
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
			//add by lee for 为下拉列表增加可拖拽属性以修改不能看全信息的BUG in 20091112 BEGIN
			if (data[i].xtype == "combo") {
				data[i].resizable = true;
			}
			//add by lee for 为下拉列表增加可拖拽属性以修改不能看全信息的BUG in 20091112 END
			if (data[i].width == null || data[i].width == 'null'
					|| data[i].width == "") {
				data[i].style += "width:" + itemlen + "px";
				data[i].width = itemlen;
			} else {
				if (data[i].width == "9999") {//通栏  
					// alert("data");
					if ((i - hid + longitems) % 2 == 1) {//在右侧栏，前一栏贯通                                             
						longData[2 * (i - hid) - 1].colspan = 3;
					} else {//多占一栏
						longitems++;
					}
					data[i].colspan = 3;//本栏贯通                                            
					data[i].width = throulen;
					if (data[i].xtype == "textarea") {
						data[i].height = 150;
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
	},
	//为只读面板提供的split方法 add by lee for readOnlyFormPanle
	splitForReadOnly : function(data) {
		var labellen = 135;
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
				data[i].items[0].disabled=true;
				data[i].items[1].html=data[i].items[1].html.replace(/display:/g, "display:none");
			}
			if (data[i].xtype == "fckeditor") {
				data[i] = {
					value : data[i].value,
					xtype : "htmleditor",
					colspan : 3,
					fieldLabel : data[i].fieldLabel,
					cls : 'common-text',
					width: data[i].width 
				};
			}
			if (data[i].width == null || data[i].width == 'null'
					|| data[i].width == "" || data[i].width == "null") {
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
	}

});

function validate_number(value) {//add by musicbear for 数字格式验证 in 2009 11 18
	  if(!value) return false; 
	  var strP=/^\d+(\.\d+)?$/; 
	  if(!strP.test(value)) return false; 
	  try{ 
	  if(parseFloat(value)!=value) return false; 
	  } 
	  catch(ex) 
	  { 
	   return false; 
	  } 
	  return true; 
}

function validate_integer(value) {	//add by musicbear for 正整数格式验证 in 2009 11 18
	var regu = /^[-]{0,1}[0-9]{1,}$/;
	return regu.test(value);
}

function validate_double(value) {//不带符号的浮点数
	var check_float = new RegExp("^[1-9][0-9]*(\.[0-9]{1,6})?$");
	var r = check_float.exec(value);
	return r != null;

}

function validate_currency(value) {
	return true;
}

function validate_phone(value) {
//	var patrn=/^[+]{0,1}(\d){1,3}[ ]?([-]?((\d)|[ ]){1,12})+$/;   
//	return pattern.test(value);
	return true;
}

function validate_mobile(value) {
//	var patrn=/^[+]{0,1}(\d){1,3}[ ]?([-]?((\d)|[ ]){1,12})+$/; 
//	return pattern.test(value);
	return true;
}

function validate_email(value) {
//	var pattern = /^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+(\.[a-zA-Z0-9_-])+/;         
//	return pattern.test(value);
	return true;
}

function validate_zip(value) {
	return true;
}

function validate_date(value) {
	return true;
}

function validate_idcard(value) {
	return true;
}

function validate_english(value) {
	return true;
}

function validate_chinese(value) {
	return true;
}

//patch
Ext.override(Ext.grid.GridView, {
	layout : function() {
		if (!this.mainBody) {
			return;
		}
		var g = this.grid;
		var c = g.getGridEl(), cm = this.cm, expandCol = g.autoExpandColumn, gv = this;
		var csize = c.getSize(true);
		var vw = csize.width;
		if (vw < 20 || csize.height < 20) {
			return;
		}
		if (g.autoHeight) {
			csize.height = this.mainHd.getHeight() + this.mainBody.getHeight();
			if (!this.forceFit) {
				csize.height += this.scrollOffset;
			}
		}
		this.el.setSize(csize.width, csize.height);
		var hdHeight = this.mainHd.getHeight();
		var vh = csize.height - (hdHeight);
		this.scroller.setSize(vw, vh);
		if (this.innerHd) {
			this.innerHd.style.width = (vw) + 'px';
		}
		if (this.forceFit) {
			if (this.lastViewWidth != vw) {
				this.fitColumns(false, false);
				this.lastViewWidth = vw;
			}
		} else {
			this.autoExpand();
		}
		this.onLayout(vw, vh);
	}
});

//url,params(split by &),width,height,buttons,top,left
function showPageInWindow(url, title, params, buttons) {
	this.url = webContext + "/" + url;
	var param = '';
	var close = {
		xtype : 'button',
		handler : function() {
			this.contentWin.close()
		},
		text : '关闭',
		scope : this
	};
	if (buttons == null) {
		buttons = [close];
	} else {
		buttons[buttons.length] = close;
	}
	if (params != null) {
		param = params.replace("&", "****");
	}
	this.contentWin = new Ext.Window({
		title : title,
		modal : true,
		height : 500,
		width : 800,
		resizable : false,
		draggable : true,
		autoLoad : {
			//装载审批有关信息，与流程有关
			url : webContext + "/tabFrame.jsp?url=" + this.url + "?" + param,
			text : "页面正在加载中......",
			method : 'post',
			scope : this
		},
		viewConfig : {
			autoFill : true,
			forceFit : true
		},
		layout : 'fit',
		buttonAlign : 'center',
		scope : this,
		buttons : buttons,
		items : [{
			html : "正在加载页面内容......"
		}]
	});
	//alert(width);
	//this.auditContentWin.setPagePosition(210,117);
	this.contentWin.setPagePosition(200, 60);
	this.contentWin.show();
}
