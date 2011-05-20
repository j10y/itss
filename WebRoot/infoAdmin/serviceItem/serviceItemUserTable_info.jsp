<%@ page contentType="text/html; charset=GBK" language="java" import="java.sql.*" errorPage="" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<META HTTP-EQUIV="Content-Type" CONTENT="text/html; charset=GBK"/>
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache">
<META HTTP-EQUIV="Expires" CONTENT="0">  
<title>�û������������ֶ�����</title>
<%@include file="/includefiles.jsp"%>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/CssAdmin.css">
<script language="javascript" src="${pageContext.request.contextPath}/js/Admin.js"></script>
<script type="text/javascript">

function unicode(s) {
	var len = s.length;
	var rs = "";
	for (var i = 0; i < len; i++) {
		var k = s.substring(i, i + 1);
		rs += "&#" + s.charCodeAt(i) + ";";
	}
	return rs;
}

function saveUserTable(){ 
    var serverPath = "${pageContext.request.contextPath}";

    var id = Ext.getDom("id").value;
    var tableName = Ext.getDom("tableName").value;
    var tableCnName = Ext.getDom("tableCnName").value;
    var className = Ext.getDom("className").value;
    var primaryKeyColumn = Ext.getDom("primaryKeyColumn");
    var serviceItemType = Ext.getDom("serviceItemType");
    var serviceItem = Ext.getDom("serviceItem");
    var pkselectid = primaryKeyColumn.selectedIndex;
    var pkId=primaryKeyColumn.options[primaryKeyColumn.selectedIndex].value;
    var pkName=primaryKeyColumn.options[primaryKeyColumn.selectedIndex].text;
    var serviceItemTypeId = serviceItemType.options[serviceItemType.selectedIndex].value;
    var serviceItemId = serviceItem.options[serviceItem.selectedIndex].value;

    Ext.Ajax.request({
       url: serverPath+"/infoAdmin/serviceItemUserTableAction.do?methodCall=saveTable", 
       params:{
           id: id, 
           tableName: tableName,
           tableCnName: unicode(tableCnName),
           className: className,
           primaryKeyColumn: pkId,
           serviceItemId: serviceItemId
       },
       method:'POST',
       success:function(response){
        
         var smt = Ext.decode(response.responseText);
         Ext.getDom("tableName").value=smt.data.tableName;
         Ext.getDom("tableCnName").value=smt.data.tableCnName;
         Ext.getDom("className").value=smt.data.className
         Ext.getDom("primaryKeyColumn").selectedIndex=pkselectid;
         alert("�û��������Ա���ɹ�!");
       }
  });
}

function loadNewColumn(mainTableId){ 
    var serverPath = "${pageContext.request.contextPath}";

    Ext.Ajax.request({
       url: serverPath+"/infoAdmin/userMainTableColumn.do?methodCall=loadNewColumns", 
       params:{
           mainTableId: mainTableId
           
       },
       method:'POST',
       success:function(response){
        
         var smt = Ext.decode(response.responseText);
         window.location.href = window.location.href;
         
         
       }
  });
}

function addNewColumn(mainTableId){  //alert(mainTableId);
    var deployFlag = ${empty smt.deployFlag or smt.deployFlag eq 0 ? 0 : 1}; 
    if(deployFlag==0){
      document.location.href="${pageContext.request.contextPath}/infoAdmin/serviceItemUserTableColumn.do?methodCall=toForm&tableId="+mainTableId+"&id=&serviceItem=${siut.serviceItem.id}&siut=${siut.id}";
    }else{
      document.location.href="${pageContext.request.contextPath}/infoAdmin/serviceItemUserTableColumn.do?methodCall=toExtForm&tableId="+mainTableId+"&id=&serviceItem=${siut.serviceItem.id}&siut=${siut.id}";
    }
   // 
}

//�ж�������ַ��������ģ��ɰ��������ַ���
function isChinese(s){
 // ������ʽ����
 var re = new RegExp("[\\u4e00-\\u9fa5]", "");
 // ��֤�Ƿ�պ�ƥ��
 var yesorno = re.test(s);
 if(yesorno){
        return true;
 }
 else{
        return false;
 }
}

//�ж�������ַ����Ƿ������ģ�ֻ��ʹ���Ĳ��ܰ��������ַ���
function isOnlyChinese(s){
 // ������ʽ����
 var re = new RegExp("^[\\u4e00-\\u9fa5]+$", "");
 // ��֤�Ƿ�պ�ƥ��
 var yesorno = re.test(s);
 if(yesorno){
  return true;
 }
 else{
  return false;
 }
}


function saveNewTable(){
  var xform = document.editForm;
  //var primaryKeyColumn = document.editForm.primaryKeyColumn;
 // var pkSelectIndex = primaryKeyColumn[primaryKeyColumn.selectedIndex].value;
  
  if(xform.tableName.value==""){
  	 alert("�û�����Ӣ����������д");
  	 xform.tableName.focus();
  	 return false;
  }else if(xform.tableCnName.value==""){
  	 alert("�û�����������������д");
  	 xform.tableCnName.focus();
  	 return false;
  }

  if(isChinese(xform.tableName.value)){
     alert("�û�����Ӣ���������԰�������");
  	 xform.tableName.focus();
  	 return false;
  }
  
  if(!isChinese(xform.tableCnName.value)){
     alert("�û�������������д����");
  	 xform.tableCnName.focus();
  	 return false;
  }
  
  xform.submit();

 
}

function deployNewTable(){ 
  var result = confirm("ȷ�Ϸ����˷���������������"); //ע�ⷢ���ɹ������������ֶΣ�ֻ�����´η�������������Ч��
  if(result){
   var xform = document.getElementById("editForm");
   xform.methodCall.value="deployTable";
   xform.submit();
  }
 
}
function initSelect(cobj, fselect){
  var j=1;
  for(var i=fselect.options.length-1;i>=0;--i){ 
	fselect.options[i]=null;  
  }
  for(var i=0 ;i< cobj.length ;i++){
	fselect.options[j] = new Option(cobj[i].name,cobj[i].id); 
	j = j +1;
  }
}

function findServiceItemBySCIT(){
	var serverPath = "${pageContext.request.contextPath}";
	var serviceItemType=document.getElementById("serviceItemType").value;//Ext.getDom("serviceItemType");
	var serviceItem=Ext.getDom("serviceItem");
	Ext.Ajax.request({
      url: serverPath+"/infoAdmin/serviceItemUserTableAction.do?methodCall=findServiceItemBySCIT", 
      params:{
          serviceItemType: serviceItemType
      },
      method:'POST',
      success:function(response){
        var data = response.responseText;
        var result = Ext.decode(data);
        var obj = result.data;
        initSelect(obj, serviceItem);
      }//end func
  });
}
function genTableExcel(citypeTableId){ 
	alert("�˹�������ʵ����***");
	return;
    var serverPath = "${pageContext.request.contextPath}";
    var citypeTableId = document.getElementById("id").value;

    Ext.Ajax.request({
       url: serverPath+"/infoAdmin/userMainTable.do?methodCall=genCITypeTableExcel", 
       params:{
           citypeTableId: citypeTableId
           
       },
       method:'POST',
       success:function(response){
        
         var resultJsonObject = Ext.decode(response.responseText);
        // alert(response.responseText);
         var filename = resultJsonObject.data.fileName;
         var tableCnName = document.getElementById("tableCnName").value;
         var file = document.getElementById("file");
         file.innerHTML="<a href=\"${pageContext.request.contextPath}/download/citypeExcel/"+filename+"\" target=\"_blank\"><font color=red>����"+tableCnName+"EXCELģ��</font></a>";
         //window.location.href = window.location.href;
         
         
       }
  });
} 
</script>
</head>
<body>
<!-- 
<TABLE cellSpacing=1 cellPadding=3 width="100%" bgColor=#6298e1 border=0 height="56">
  <TBODY>
  <TR>
    <TD noWrap height=24><FONT color=#ffffff><IMG height=18 
      src="${pageContext.request.contextPath}/images/Explain.gif" width=18 align=absMiddle 
      border=0>&nbsp;<STRONG>�����������������</STRONG></FONT></TD></TR>
  <TR>
    <TD noWrap align=middle bgColor=#ebf2f9 height=24>
    <a href="${pageContext.request.contextPath}/infoAdmin/serviceItemUserTableAction.do?methodCall=add&&id=${siut.id}" 
    	target="mainFrame" >[������������������]</a>
    <a href="${pageContext.request.contextPath}/infoAdmin/serviceItemProcessAction.do?methodCall=list&&serviceItemId=${siut.serviceItem.id}" 
    	target="mainFrame" >[������������������]</a>
    </TD>
 </TR>
</TBODY>
</TABLE>
<BR>
 -->

�û���̨���� -&gt; ��������������
<table width="100%" border="0" cellpadding="3" cellspacing="1" bgcolor="#6298E1">
 <form name="editForm" id="editForm" action="${pageContext.request.contextPath}/infoAdmin/serviceItemUserTableAction.do" method=post>
 	   
  <tr>
    <td width="12%" align="center" bgcolor="#EFF3FF">�û�����Ӣ����</td>
    <td width="38%" bgcolor="#EFF3FF">
    <input name="methodCall"  id="methodCall"  type="hidden" value="saveTable">
    <input name="id"  id="id"   type="hidden" value="${siut.id}">
    <input name="systemMainTable"  id="systemMainTable"   type="hidden" value="${siut.systemMainTable.id}">
    <input name="serviceItemId"  id="serviceItemId"   type="hidden" value="${siut.serviceItem.id}">
      <input name="tableName" id="tableName"  type="text" class="textfield" style="WIDTH:250px;" value="${siut.systemMainTable.tableName}">
    
    <br>
    </td>
    <td  width="12%" bgcolor="#EFF3FF">�û�����������</td>
    <td width="38%" bgcolor="#EFF3FF">
     <input name="tableCnName"  id="tableCnName" type="text" class="textfield" style="WIDTH:250px;" value="${siut.systemMainTable.tableCnName}">
    
     <input name="className" id="className" type="hidden" class="textfield" value="${siut.className}">
     <!-- <input name="primaryKeyColumn" id="primaryKeyColumn" type="hidden" class="textfield" value="${smt.primaryKeyColumn.id}">     -->     
            
    <br>
    </td>
  </tr>

  <tr>  
    <td width="10%" align="center" bgcolor="#EFF3FF">����������</td>
    <td width="40%" bgcolor="#EFF3FF">
     <select name="serviceItemType" id="serviceItemType"  class="textfield" onchange="findServiceItemBySCIT()">
        <option value="">��</option>
        <c:forEach var="item" items="${requestScope.serviceItemTypes}" varStatus="status">
         <c:if test="${not empty item.name}">
          <option value="${item.id}" ${siut.serviceItem.serviceItemType.id eq item.id? ' selected':'' }>${item.name}</option>
         </c:if>
       </c:forEach>
      </select>
      
    </td>
    <td width="10%" bgcolor="#EFF3FF">����������</td>
    <td width="40%" bgcolor="#EFF3FF">
     <select name="serviceItem" id="serviceItem" style="width:300px;" class="textfield">
     	<option value="">��</option>
       <c:forEach var="item" items="${requestScope.serviceItems}" varStatus="status">
         <option value="${item.id}" ${siut.serviceItem.id eq item.id? ' selected':'' }>${item.name}</option>
        </c:forEach>
      </select>
 
    </td>
  </tr>
 
 <tr>  
    <td width="10%" align="center" bgcolor="#EFF3FF">����ҳ�����</td>
    <td width="40%" bgcolor="#EFF3FF" >
    <select name="pagePanel" id="pagePanel"  class="textfield">
        <option value="">��</option>
        <c:forEach var="item" items="${requestScope.pagePanels}" varStatus="status">
         <c:if test="${not empty item.name}">
          <option value="${item.id}" ${siut.pagePanel.id eq item.id? ' selected':'' }>${item.title}��${item.name}��</option>
         </c:if>
       </c:forEach>
      </select>
      <c:if test="${siut.pagePanel.groupFlag ne 1}">
      <a href="${pageContext.request.contextPath}/pageModel/pagePanelManage.do?methodCall=toPagePanelEditForm&id=${siut.pagePanel.id}">�鿴</a>
      </c:if>
     <c:if test="${siut.pagePanel.groupFlag eq 1}">
      <a href="${pageContext.request.contextPath}/pageModel/pageGroupPanelManage.do?methodCall=toPageGroupPanelEditFormid=${siut.pagePanel.id}">�鿴�������</a>
      </c:if> 
   		
    </td>
   <td align="left" bgcolor="#EFF3FF">״̬</td>
    <td  bgcolor="#EFF3FF">
     <select name="deployFlag" id="deployFlag" style="width:100px;" class="textfield">
       <option value="0" ${empty siut.deployFlag or siut.deployFlag eq 0?' selected':''}>δ����</option>
       <option value="1" ${siut.deployFlag eq 1?' selected':''}>�ѷ���</option>
    
    </select> 
   			<!-- ${empty siut.deployFlag or siut.deployFlag eq 0? 'δ����' : '�ѷ���'} -->
    </td>
    
  </tr>

 <tr>  
    <td width="10%" align="center" bgcolor="#EFF3FF">�б�ҳ�����</td>
    <td width="40%" bgcolor="#EFF3FF" >
    
    
     <select name="pageListPanel" id="pageListPanel"  class="textfield">
        <option value="">��</option>
        <c:forEach var="item" items="${requestScope.pagePanels}" varStatus="status">
         <c:if test="${not empty item.name}">
          <option value="${item.id}" ${siut.pageListPanel.id eq item.id? ' selected':'' }>${item.title}��${item.name}��</option>
         </c:if>
       </c:forEach>
      </select>
      <c:if test="${siut.pagePanel.groupFlag ne 1}">
      <a href="${pageContext.request.contextPath}/pageModel/pagePanelManage.do?methodCall=toPagePanelEditForm&id=${siut.pageListPanel.id}">�鿴</a>
      </c:if>
     <c:if test="${csiut.pagePanel.groupFlag eq 1}">
      <a href="${pageContext.request.contextPath}/pageModel/pageGroupPanelManage.do?methodCall=toPageGroupPanelEditFormid=${siut.pageListPanel.id}">�鿴�������</a>
      </c:if> 
   		
   		
    <!-- 
     <select name="groupPanel" id="groupPanel"  class="textfield">
        <option value="">��</option>
        <c:forEach var="item" items="${requestScope.groupPanels}" varStatus="status">
         <c:if test="${not empty item.name}">
          <option value="${item.id}" ${siut.groupPanel.id eq item.id? ' selected':'' }>${item.title}��${item.name}��</option>
         </c:if>
       </c:forEach>
      </select>
  
     <c:if test="${not empty  siut.groupPanel}">
      <a href="${pageContext.request.contextPath}/pageModel/pageGroupPanelManage.do?methodCall=toPageGroupPanelEditForm&id=${citype.groupPanel.id}">�鿴�������</a>
     </c:if>  -->
      <input name="userListFlag"  id="userListFlag"   type="hidden" value="${siut.systemMainTable.userListFlag}">
     <input name="userExtFlag"  id="userExtFlag"   type="hidden" value="${siut.systemMainTable.userExtFlag}">
   		<input name="deployFlag"  id="deployFlag"   type="hidden" value="${siut.systemMainTable.deployFlag}">
   		<input name="templateFlag"  id="templateFlag"   type="hidden" value="${siut.systemMainTable.deployFlag}">
   		
    </td>
   <td align="left" bgcolor="#EFF3FF">�����ֶ�</td>
    <td  bgcolor="#EFF3FF">
   	 <select name="primaryKeyColumn" id="primaryKeyColumn" style="width:100px;" class="textfield">
       <option value=""></option>
        <c:forEach var="item" items="${requestScope.mainColumns}" varStatus="status">
         <c:if test="${not empty item.columnCnName}">
          <option value="${item.id}" ${siut.systemMainTable.primaryKeyColumn.id eq item.id?' selected':''}>${item.columnCnName}</option>
         </c:if>
       </c:forEach>
    </select> 
    </td>
    
  </tr>
  
  <tr>
    <td align="right" bgcolor="#EFF3FF">&nbsp;</td>
    <td bgcolor="#EFF3FF">
   		<input class=button type="button"  onclick="saveNewTable();" value="�����������������">&nbsp;
    	<input class=button type="button" onclick="deployNewTable();" value="����������">&nbsp;
    	<input class=button type="button" onclick="genTableExcel();" value="����Excelģ��">
    	
    	
    </td>
     <td  colspan="2" bgcolor="#EFF3FF">
   		<div style="float:left" id="file"></div>
    	
    </td>
  </tr>
  <tr><td></td></tr>
  </form>
</table>
<BR>

  �û���̨���� -&gt; �û�������ֶ�
<TABLE cellSpacing=1 cellPadding=3 width="100%" bgColor=#6298e1 border=0>
  <FORM name="formDel" action="${pageContext.request.contextPath}/infoAdmin/userMainTableColumn.do" method="post">
    <input type="hidden" name="methodCall" value="remove">
    <input type="hidden" name="pageNo" value="${param.pageNo}">
    <input type="hidden" name="smtId" value="${siut.systemMainTable}">
    <TBODY>
      <TR>
        <TD  width="20" bgColor=#8db5e9> <FONT color=#ffffff><STRONG>ID</STRONG></FONT> </TD>
		<TD width="100"  bgColor=#8db5e9><FONT color=#ffffff><strong>�ֶ�����</strong></FONT> </TD>
		 <TD width="100"  bgColor=#8db5e9> <FONT color=#ffffff><STRONG>�ֶ���������</STRONG></FONT> </TD>
        <TD noWrap bgColor=#8db5e9><FONT color=#ffffff><strong>�ֶ�����</strong></FONT> </TD>
       
       
        <TD noWrap bgColor=#8db5e9><FONT color=#ffffff><strong>���������</strong></FONT></TD>
        
        <TD noWrap  bgColor=#8db5e9><FONT color=#ffffff><strong>������</strong></FONT></TD>
        <TD noWrap bgColor=#8db5e9><FONT color=#ffffff><strong>��������ʾ�ֶ�</strong></FONT></TD>
        <TD noWrap bgColor=#8db5e9><FONT color=#ffffff><strong>������֤����</strong></FONT></TD>
		 
        <TD  width=76 bgColor=#8db5e9 align="center"><STRONG> <FONT color=#ffffff>����</FONT></STRONG>
            <INPUT class=button id=submitAllSearch style="WIDTH: 16px; HEIGHT: 18px" onclick=CheckAll(this.form) type=button value=ȫ name=buttonAllSelect>
            <INPUT class=button id=submitOtherSelect style="WIDTH: 16px; HEIGHT: 18px" onclick=CheckOthers(this.form) type=button value=�� name=buttonOtherSelect>
        </TD>
      </TR>
      <c:forEach  var="item" items="${requestScope.mainColumns}" varStatus="status">
      <TR onmouseover="this.style.backgroundColor = '#FFFFFF'" style="CURSOR: hand" 
      onmouseout="this.style.backgroundColor = ''" bgColor=#ebf2f9>
        <TD  width="20">${item.id }</TD>
        <TD  noWrap>
        <A onclick='changeAdminFlag("�޸ĵ�����Ŀ")' 
          href="${pageContext.request.contextPath}/infoAdmin/serviceItemUserTableColumn.do?methodCall=toForm&tableId=${siut.systemMainTable.id}&id=${item.id}&serviceItem=${siut.serviceItem.id}&siut=${siut.id}">
        ${item.columnName }&nbsp; 
        </A>
        </TD>
         <TD noWrap>${item.columnCnName }</TD>
        <TD noWrap>${item.propertyType.sqlColumnType}(${item.propertyType.propertyTypeName })&nbsp; </TD>
      
       
        <TD noWrap>${item.systemMainTableColumnType.columnTypeCnName }&nbsp;</TD>
        <TD noWrap>${item.foreignTable.tableCnName }&nbsp;</TD>
        <TD noWrap>${item.foreignTableValueColumn.columnCnName }&nbsp;</TD>
        <TD noWrap>${item.validateType.validateTypeCnName }&nbsp;</TD>
		
        <TD width=48 align="center"> 
		<A onclick='changeAdminFlag("�޸ĵ�����Ŀ")' 
           href="${pageContext.request.contextPath}/infoAdmin/serviceItemUserTableColumn.do?methodCall=toForm&tableId=${siut.systemMainTable.id}&id=${item.id}&serviceItem=${siut.serviceItem.id}&siut=${siut.id}"> <FONT color="#330099">�޸�</FONT>
		</A><INPUT name="id" type="checkbox" value="${item.id}" style="WIDTH:13px;HEIGHT:13px">
        </TD>
      </TR>
      </c:forEach>
      
       <c:forEach  var="item" items="${requestScope.extColumns}" varStatus="status">
      <TR onmouseover="this.style.backgroundColor = '#FFFFFF'" style="CURSOR: hand" 
      onmouseout="this.style.backgroundColor = ''" bgColor=#ebf2f9>
        <TD  width="20">${item.id }</TD>
        <TD  noWrap>
        <A  
          href="${pageContext.request.contextPath}/infoAdmin/userMainTableColumn.do?methodCall=toExtForm&tableId=${siut.systemMainTable.id}&id=${item.id}">
        ${item.columnName }&nbsp; 
        </A>
        </TD>
        <TD noWrap>${item.columnCnName}&nbsp; </TD>
        <TD noWrap>��չ</TD>
        <TD noWrap>${item.systemMainTableColumnType.columnTypeCnName }
        <c:if test="${item.systemMainTableColumnType.id==3 and item.extSelectType==2}">
        <A href="javascript:openOptionWindow('${pageContext.request.contextPath}/infoAdmin/sysExtTableColumn.do?methodCall=addOrUpdateColumns&id=${item.id}','�����б�');">(�����޸������б�)</A>
        </c:if>
        <c:if test="${item.systemMainTableColumnType.id==4 and item.extSelectType==2}">
        <A href="javascript:openOptionWindow('${pageContext.request.contextPath}/infoAdmin/sysExtTableColumn.do?methodCall=addOrUpdateColumns&id=${item.id}','��ѡ��');">(�����޸ĵ�ѡ��)</A>
        </c:if>
        <c:if test="${item.systemMainTableColumnType.id==5 and item.extSelectType==2}">
        <A href="javascript:openOptionWindow('${pageContext.request.contextPath}/infoAdmin/sysExtTableColumn.do?methodCall=addOrUpdateColumns&id=${item.id}','��ѡ��');">(�����޸Ķ�ѡ��)</A>
        </c:if>
        </TD>
        <TD noWrap>${item.foreignTable.tableCnName }&nbsp;</TD>
        <TD noWrap>${item.foreignTableValueColumn.columnCnName }&nbsp;</TD>
        <TD noWrap>${item.validateType.validateTypeCnName }&nbsp;</TD>
		
        <TD width=48 align="center"> 
		<A 
            href="${pageContext.request.contextPath}/infoAdmin/userMainTableColumn.do?methodCall=toExtForm&tableId=${siut.systemMainTable}&id=${item.id}"> <FONT color="#330099">�޸�</FONT>
		</A><INPUT name="id" type="checkbox" value="${item.id}" style="WIDTH:13px;HEIGHT:13px">
        </TD>
      </TR>
      </c:forEach>
      
      
      
      <TR> 
        
        <TD noWrap bgColor=#ebf2f9 colSpan=7 align="right">&nbsp;
       </TD>
         <TD colSpan=1 noWrap bgColor=#ebf2f9 align="right">
       <INPUT class=button  onclick="addNewColumn(${siut.systemMainTable.id});" type=button value="�������ֶ�" >&nbsp;  
        </TD>
        <TD colSpan=1 noWrap bgColor=#ebf2f9 align="left">
          <INPUT class=button  onclick="ConfirmDel('ɾ���ֶν�����ɾ�����û��ɼ��ֶκͲ�ѯ�ֶΣ���ȷ��ɾ����');" 
    		type=button value="ɾ����ѡ" name="submitDelSelect">
        </TD>
      </TR>
      
   </TBODY>
  </form>
  
</TABLE>
<br>
</body>
</html>
