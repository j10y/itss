<%@ page pageEncoding="gbk" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<META HTTP-EQUIV="Content-Type" CONTENT="text/html; charset=GBK"/>
<META content="" name=Description>
<title>任务指派修改与增加</title>
<%@include file="/includefiles.jsp"%>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/CssAdmin.css">
<script language="javascript" src="${pageContext.request.contextPath}/js/Admin.js"></script>

<script type="text/javascript" src="${pageContext.request.contextPath}/js/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript">
 function query(pageNo){ 
   var xform = document.formSearch;
   var moduleSelect = xform.definiName;
  
   var selectModuleId = moduleSelect.options[moduleSelect.selectedIndex].value;

   xform.pageNo.value = pageNo;
   xform.methodCall.value="list";
   xform.submit();
 }
 
 function initSelect(cobj, fselect){
  var j=1;  
  if(fselect!=undefined){
    for(var i=fselect.options.length-1;i>=0;--i){ 
	  fselect.options[i]=null;  
    }
  }
  
  for(var i=0 ;i< cobj.length ;i++){
	fselect.options[j] = new Option(cobj[i].nodeName,cobj[i].id); 
	j = j +1;
  }
}


function setUserName(userSel){
	var realName = userSel.options[userSel.selectedIndex].text;
	document.getElementById("actorName").value = realName;
	//alert(document.getElementById("actorName").value);
}
 function findNodeByDefinition(){ 
   var serverPath = "${pageContext.request.contextPath}";
   var definitionName = Ext.getDom("definitionName");
   var taskName = Ext.getDom("taskName"); 
    
   var defId = definitionName.options[definitionName.selectedIndex].value;
   
   Ext.Ajax.request({
      url: serverPath+"/workflow/taskPreAssign.do?methodCall=findNodeByDefinition", 
      params:{
          definiName: defId
      },
      method:'POST',
      success:function(response){
        var data = response.responseText;
        var result = Ext.decode(data);
        
        var obj = result.data;
        //alert(obj);
        //alert(taskName);
        initSelect(obj, taskName);
       
 		
  		
      }//end func
  });
}

function toList(){
	location = "taskPreAssign.do?methodCall=list";
}
</script>
</head>
<body>
<TABLE cellSpacing=1 cellPadding=3 width="100%" bgColor=#6298e1 border=0>
  <TBODY>
  <TR>
    <TD noWrap height=24><FONT color=#ffffff><IMG height=18 
      src="${pageContext.request.contextPath}/images/Explain.gif" width=18 align=absMiddle 
      border=0>&nbsp;<STRONG>系统任务指派列表</STRONG></FONT></TD></TR>
   </TBODY>
 </TABLE>
<br>

<table width="100%" border="0" cellpadding="3" cellspacing="1" bgcolor="#6298E1">
 <form name="editForm" id="editForm" action="${pageContext.request.contextPath}/workflow/taskPreAssign.do?methodCall=save" method=post>
   <input name="definitionName"  id="definitionName"  type="hidden" value="${detail.definitionName}">
   <input name="pageNo"  id="pageNo"  type="hidden" value="${pageNo}">
   <input name="definiName"  id="definiName"  type="hidden" value="${definiName}"><!-- 查询条件 -->
  
  <tr>
    <td  align="center" colspan="2"  bgcolor="#CFE3FF" height="30" style="font-size:10pt">${title }</td>
    </td>
  </tr>
 
 
 <tr height="30">
    <td  align="center" bgcolor="#EFF3FF">流程定义名称</td>
    <td  bgcolor="#EFF3FF" colspan="">
    <input name="id"  id="id"   type="hidden" value="${detail.id}">
    <div name="definitionDesc" class="textfield" style="width:200pt">&nbsp;&nbsp;${detail.definitionDesc}</div>
   </td>
  </tr>
  <tr height="30">
    <td  align="center" bgcolor="#EFF3FF">任务名称</td>
    <td  bgcolor="#EFF3FF">
     <div name="definitionDesc" class="textfield" style="width:200pt">&nbsp;&nbsp;${detail.nodeName}</div>  
    </td>  
  </tr>
  
  <tr height="30">
    <td  align="center" bgcolor="#EFF3FF">流程角色</td>
    <td  bgcolor="#EFF3FF">&nbsp;
    	<select name="workflowRoleId" id="workflowRole" class="textfield" style="width:150pt;height:14pt">
         	<option value=""></option>
         	<c:forEach var="item" items="${requestScope.workflowRoles}" varStatus="status">
           		<option value="${item.id}" ${detail.workflowRole eq item?' selected':'' }>${item.name}</option>
         	</c:forEach>
    	</select>
    </td>
   
  </tr>
 
  <tr height="30">
    <td align="right" bgcolor="#EFF3FF">&nbsp;</td>
    <td colspan="" bgcolor="#EFF3FF">&nbsp;
   <input class=button type="submit" name="Submit" value=" 保存 " >&nbsp;&nbsp;
   <input class=button type="button" onclick="toList()" value=" 返回 " >
    </td>
  </tr>
  <tr><td></td></tr>
  </form>
</table>
<BR>
 
<p>&nbsp;</p>
</body>
</html>
