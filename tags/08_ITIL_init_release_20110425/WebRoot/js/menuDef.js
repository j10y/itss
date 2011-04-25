function validDate() {
	var parent;
	
	
	//alert(document.getElementById("menuId").value);
	
	if (document.getElementById("menuName").value == "") {
		alert("菜单名称不能为空,请填写!");
		return;
	}

	if (document.getElementById("menuUrl").value == "") {
		alert("菜单的链接不能为空,请填写!");
		return;
	}

	if (document.getElementById("menuLevel").value == "") {
		alert("菜单的级别不能为空,请填写从0--99的级别!");
		return;
	}else if(document.getElementById("parentMenu").value != "-1" && document.getElementById("menuLevel").value == "0"){
		alert("已经有父菜单项的,级别不能为0!");
		return ;
	}

	saveMenuItem(document.getElementById("menuName").value, document
			.getElementById("menuUrl").value, document
			.getElementById("parentMenu").value, document
			.getElementById("menuLevel").value);
}

function saveMenuItem(name, url, parentId, level) {
	
	
	Ext.Ajax.request({
		url : serverPaht + "/system/menuManage.do?methodCall=saveMenu",
		method : "post",
		params : {
			menuId : document.getElementById("menuId").value,
			menuName : unicode(name),
			menuUrl : url,
			parentMenu : parentId,
			menuLevel : level
		},
		success : function(response, options) {
			if (response.responseText.indexOf("success:true") != -1) {
				document.location.href = serverPaht + "/system/menuManage.do?methodCall=list";
				//history.back();
			}else{
				alert("保存菜单项失败!");
			}
		},
		failure : function(response, options) {
			alert("增加菜单项失败,请联系管理员!");
		}
	});
}

function vaildTemplate(){
	if(document.getElementById("templateName").value == ""){
		alert();
		return ;
	}
	if(document.getElementById("department").value == "-1" || document.getElementById("department") == ""){
		alert();
		return ;
	}
	
	var menuItem = document.getElementsByName("itemCheck");
	var selectCount = 0;
	var selectID = "";
	
	for(var i=0; i<menuItem.length; i++){
		if(menuItem.checked){
			selectID = selectID == "" ?  menuItem[i].value : selectID + "#" + menuItem[i].value; 
			continue;
		}
		selectCount++;
	}
	
	if(selectCount == menuItem.length){
		alert();
		return ;
	}
}

function saveTemplate(id, name, departId, menuItems){
		Ext.Ajax.request({
		url : serverPaht + "/system/menuManage.do?methodCall=saveMenu",
		method : "post",
		params : {
			templateId : id,
			templateName : unicode(name),
			department : departId,
			menuItemList : menuItems,
			exMenuList : exMenuList
		},
		success : function(response, options) {
			if (response.responseText.indexOf("success:true") != -1) {
				document.location.href = serverPaht + "/system/menuManage.do?methodCall=list";
			}else{
				alert("保存菜单模板失败!");
			}
		},
		failure : function(response, options) {
			alert("增加菜单模板失败,请联系管理员!");
		}
	});
}
