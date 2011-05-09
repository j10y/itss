<%@ page pageEncoding="gbk" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<META HTTP-EQUIV="Content-Type" CONTENT="text/html; charset=GBK"/>
<META content="" name=Description>
<title>����ָ���޸�������</title>
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
      border=0>&nbsp;<STRONG>ϵͳ����ָ���б�</STRONG></FONT></TD></TR>
   </TBODY>
 </TABLE>
<br>
<form name="editForm" id="editForm" action="${pageContext.request.contextPath}/workflow/taskPreAssign.do?methodCall=saveAdd" method=post>
<table width="100%" border="0" cellpadding="3" cellspacing="1" bgcolor="#6298E1">
   <input name="pageNo"  id="pageNo"  type="hidden" value="${pageNo}">
   <input name="definiName"  id="definiName"  type="hidden" value="${definiName}"><!-- ��ѯ���� -->
  
  <tr>
    <td  align="center" colspan="2"  bgcolor="#CFE3FF" height="30" style="font-size:10pt">${title }</td>
  </tr>
 <tr>
    <td  align="center" bgcolor="#EFF3FF">���̶�������</td>
    <td  bgcolor="#EFF3FF" height="30">
    <input name="definitionName"  id="definitionName"  type="hidden" value="${definitionName}">
    <input name="definitionDesc"  id="definitionDesc"  type="hidden" value="${definitionDesc}">
    <div readOnly type="text" class="textfield" style="width:200pt">&nbsp;&nbsp;${definitionDesc}</div>  
   </td>
  </tr>
  <tr>
  <td align="center" bgcolor="#EFF3FF">��������</td>
  <td bgcolor="#EFF3FF" height="30">&nbsp;
  		<select name="departmentCode" id="departmentCode" class="textfield" style="width:150pt">
         	<option value=""></option>
         	<c:forEach var="item" items="${requestScope.departments}" varStatus="departments">
           	<option value="${item.departCode}">${item.departName}</option>
         	</c:forEach>
      	</select>
  </td></tr>
  <tr><td colspan="2" height=1></td></tr>
  
  <tr height="30">
    <td align="right" bgcolor="#EFF3FF">&nbsp;</td>
    <td bgcolor="#EFF3FF">&nbsp;
    <input class=button type="submit" name="Submit" value=" ���� " >&nbsp;&nbsp;
    <input class=button type="button" onclick="toList()" value=" ���� " >
    &nbsp;&nbsp;&nbsp;&nbsp;[�����̽ڵ��Ԥָ�����ڱ���󰴽ڵ�༭]
    </td>
  </tr>
  <tr><td></td></tr>
</table>
</form>
<BR>
 
<p>&nbsp;</p>
</body>
</html>
