<%@ page contentType="text/html; charset=GBK" language="java" import="java.sql.*" errorPage="" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<META HTTP-EQUIV="Content-Type" CONTENT="text/html; charset=GBK"/>
<META content="" name=Description>
<title>系统主表属性与字段设置</title>
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

function saveSystemMainTable(){ 
    var serverPath = "${pageContext.request.contextPath}";

    var id = Ext.getDom("id").value;
    var tableName = Ext.getDom("tableName").value;
    var tableCnName = Ext.getDom("tableCnName").value;
    var className = Ext.getDom("className").value;
    var primaryKeyColumn = Ext.getDom("primaryKeyColumn");
    var module = Ext.getDom("module");
    var pkselectid = primaryKeyColumn.selectedIndex;
    var pkId=primaryKeyColumn.options[primaryKeyColumn.selectedIndex].value;
    var pkName=primaryKeyColumn.options[primaryKeyColumn.selectedIndex].text;
    var moduleId = module.options[module.selectedIndex].value;

    Ext.Ajax.request({
       url: serverPath+"/infoAdmin/sysMainTable.do?methodCall=save", 
       params:{
           id: id, 
           tableName: tableName,
           tableCnName: unicode(tableCnName),
           className: className,
           primaryKeyColumn: pkId,
           module: moduleId
           
       },
       method:'POST',
       success:function(response){
        
         var smt = Ext.decode(response.responseText);
         Ext.getDom("tableName").value=smt.data.tableName;
         Ext.getDom("tableCnName").value=smt.data.tableCnName;
         Ext.getDom("className").value=smt.data.className
         Ext.getDom("primaryKeyColumn").selectedIndex=pkselectid;
         
         alert("系统主表属性保存成功!");
         
       }
  });
}

function loadNewColumn(mainTableId){ 
    var serverPath = "${pageContext.request.contextPath}";

    Ext.Ajax.request({
       url: serverPath+"/infoAdmin/sysMainTableColumn.do?methodCall=loadNewColumns", 
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
</script>
</head>
<body>
<TABLE cellSpacing=1 cellPadding=3 width="100%" bgColor=#6298e1 border=0 height="56">
  <TBODY>
  <TR>
    <TD noWrap height=24><FONT color=#ffffff><IMG height=18 
      src="${pageContext.request.contextPath}/images/Explain.gif" width=18 align=absMiddle 
      border=0>&nbsp;<STRONG>系统主表查询管理：新增或修改查询</STRONG></FONT></TD></TR>
  <TR>
    <TD noWrap align=middle bgColor=#ebf2f9 height=24>
     <a href="${pageContext.request.contextPath}/infoAdmin/sysTableSetting.do?methodCall=list&smtId=${smt.id}&settingType=1">系统可见字段设置</a>
    &nbsp;
     <a href="${pageContext.request.contextPath}/infoAdmin/sysTableQuery.do?methodCall=list&smtId=${smt.id}">系统主表查询设置</a>
    &nbsp;
	     <a href="${pageContext.request.contextPath}/infoAdmin/sysTableRole.do?methodCall=list&smtId=${smt.id}">系统角色可见字段设置</a> 
	</TD>
   
 </TR>
</TBODY>
</TABLE>
<BR>

系统后台管理 -&gt; 系统主表 -&gt; 系统角色可见字段模板
<table width="100%" border="0" cellpadding="3" cellspacing="1" bgcolor="#6298E1">
 <form name="editForm" id="editForm" action="${pageContext.request.contextPath}/infoAdmin/sysTableRole.do?methodCall=saveTableRole" method=post>
 
  <tr>
    <td width="10%" align="center" bgcolor="#EFF3FF">中文名称</td>
    <td width="40%" bgcolor="#EFF3FF">
    <input name="id"  id="id"   type="hidden" value="${detail.id}">
    <input name="roleSettingCnName"  id="roleSettingCnName" type="text" class="textfield" 
    		style="WIDTH:250px;" value="${detail.roleSettingCnName}"></td>
    </td>
    <td width="10%" align="center" bgcolor="#EFF3FF">关键字</td>
    <td width="40%" bgcolor="#EFF3FF">
    <input name="roleSettingName" id="roleSettingName"  type="text" class="textfield" 
    		style="WIDTH:250px;" value="${detail.roleSettingName}">
    
  </tr>
  <tr>
   <td  width="10%" align="center" bgcolor="#EFF3FF">所属主表</td>
    <td width="40%"bgcolor="#EFF3FF">
  
    <select name="systemMainTable" id="systemMainTable"  class="textfield">
       <option value=""></option>
        <c:forEach var="item" items="${requestScope.smtTables}" varStatus="status">
         <c:if test="${not empty item.tableCnName}">
          <option value="${item.id}" ${param.smtId eq item.id or detail.systemMainTable.id eq item.id?' selected':''}>${item.tableCnName}</option>
         </c:if>
       </c:forEach>
    </select>
    </td>
    
    <td align="center" bgcolor="#EFF3FF">角色</td>
    <td bgcolor="#EFF3FF">
	<select name="role" id="role" style="width:100px;" class="textfield">
      <c:forEach var="item" items="${requestScope.roles}" varStatus="status">
         <c:if test="${not empty item.name}">
          <option value="${item.id}" ${param.smtId eq item.id or detail.role.id eq item.id?' selected':''}>${item.name}</option>
         </c:if>
       </c:forEach>
    </select>
	</td>
   
     <tr>
    <td width="10%" align="center" bgcolor="#EFF3FF">设置类型	</td>
    <td width="40%" bgcolor="#EFF3FF">
     <select name="settingType" id="settingType" style="width:100px;" class="textfield">
       <option value="1">列表</option>
       <option value="2">输入</option>
       <option value="5">导出</option>
      </select>
     &nbsp;
    </td>
    <td width="10%" bgcolor="#EFF3FF"></td>
    <td width="40%" bgcolor="#EFF3FF"></td>
  </tr>
  </tr>
  <tr>
    <td align="right" bgcolor="#EFF3FF">&nbsp;</td>
    <td colspan="3" bgcolor="#EFF3FF"><input class=button type="submit" name="Submit" value="保存系统角色可见字段模板" >
    </td>
  </tr>
  <tr><td></td></tr>
  </form>
</table>
<BR>

<p>&nbsp;</p>
</body>
</html>
