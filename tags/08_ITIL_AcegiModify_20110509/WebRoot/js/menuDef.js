function validDate() {
	var parent;
	
	
	//alert(document.getElementById("menuId").value);
	
	if (document.getElementById("menuName").value == "") {
		alert("�˵����Ʋ���Ϊ��,����д!");
		return;
	}

	if (document.getElementById("menuUrl").value == "") {
		alert("�˵������Ӳ���Ϊ��,����д!");
		return;
	}

	if (document.getElementById("menuLevel").value == "") {
		alert("�˵��ļ�����Ϊ��,����д��0--99�ļ���!");
		return;
	}else if(document.getElementById("parentMenu").value != "-1" && document.getElementById("menuLevel").value == "0"){
		alert("�Ѿ��и��˵����,������Ϊ0!");
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
				alert("����˵���ʧ��!");
			}
		},
		failure : function(response, options) {
			alert("���Ӳ˵���ʧ��,����ϵ����Ա!");
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
				alert("����˵�ģ��ʧ��!");
			}
		},
		failure : function(response, options) {
			alert("���Ӳ˵�ģ��ʧ��,����ϵ����Ա!");
		}
	});
}
