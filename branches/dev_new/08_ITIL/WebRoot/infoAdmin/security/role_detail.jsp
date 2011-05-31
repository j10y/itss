<%@ page pageEncoding="gbk" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<META HTTP-EQUIV="Content-Type" CONTENT="text/html; charset=GBK"/>
<META content="" name=Description>
<title>系统菜单模板设置</title>
<%@include file="/includefiles.jsp"%>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/CssAdmin.css">
<script language="javascript" src="${pageContext.request.contextPath}/js/Admin.js"></script>
<script type="text/javascript">
function initSelect(cobj, fselect){
  var j=1;  
  if(fselect!=undefined){
    for(var i=fselect.options.length-1;i>=0;--i){ 
	  fselect.options[i]=null;  
    }
  }
  
  for(var i=0 ;i< cobj.length ;i++){
	fselect.options[j] = new Option(cobj[i].templateName,cobj[i].id); 
	j = j +1;
  }
}

 function findDeptTemplateByDept(){ 
   var serverPath = "${pageContext.request.contextPath}";
   var department = Ext.getDom("department");
   var deptMenuTemplate = Ext.getDom("deptMenuTemplate"); 
    
   var department = department.options[department.selectedIndex].value;
   
   Ext.Ajax.request({
      url: serverPath+"/admin/roleManage.do?methodCall=findDeptTemplateByDept", 
      params:{
          department: department
      },
      method:'POST',
      success:function(response){
        var data = response.responseText;
        var result = Ext.decode(data);
        
        var obj = result.data;
        
        initSelect(obj, deptMenuTemplate);
       
 		
  		
      }//end func
  });
}
</script>
<body>
			
<TABLE cellSpacing=1 cellPadding=3 width="100%" bgColor=#6298e1 border=0>
  <TBODY>
  <TR>
    <TD noWrap height=24><FONT color=#ffffff><IMG height=18 
      src="${pageContext.request.contextPath}/images/Explain.gif" width=18 align=absMiddle 
      border=0>&nbsp;<STRONG>系统角色管理：添加、修改角色</STRONG></FONT></TD></TR>
    <TR>
    <TD noWrap align=middle bgColor=#ebf2f9 height=36>
     <a href="${pageContext.request.contextPath}/admin/roleManage.do?methodCall=listRoles">角色列表</a>&nbsp;
     <A href="${pageContext.request.contextPath}/admin/roleManage.do?methodCall=toAddRoles">增加角色</A>
      </TD>
    </TR>
  </TBODY>
</TABLE>
 
 
<TABLE cellSpacing=1 cellPadding=0 width="100%" border=0>
 <TBODY>
 <TR>
 <TD height=30>系统后台管理&nbsp;-&gt;&nbsp;系统角色管理&nbsp;-&gt;&nbsp;角色信息
  </TD>
  </TR>
  </TBODY>
</TABLE>

  <table width="100%" border="0" cellpadding="3" cellspacing="1" bgcolor="#6298E1">
  <form action="${pageContext.request.contextPath}/admin/roleManage.do"  method="post">
  <tr>
    <td  align="center" bgcolor="#EFF3FF">角色名称:</td>
    <td  bgcolor="#EFF3FF" colspan="4">
    <INPUT name="methodCall" type="hidden" value="saveRoles">
    <INPUT name="id" type="hidden" value="${role.id}">
    <INPUT name="name" size=20 value="${role.name}"> 
    </td>
  </tr>
  <tr>
    <td  align="center" bgcolor="#EFF3FF">描述:</td>
    <td colspan="2"  bgcolor="#EFF3FF">
    <INPUT name="descn" size=80 value="${role.descn}"> 
   </td>
   </tr>
   
   <tr>
    <td  align="center" bgcolor="#EFF3FF">隶属部门</td>
    <td  bgcolor="#EFF3FF" colspan="3">
    <font color="red">对于公用角色请选择上品折扣，将自动带出上品折扣下的所有菜单模板</font><br>
     <select name="department" id="department" class="textfield" onchange="findDeptTemplateByDept();">
      <option value="1101">上品折扣</option>
      <c:forEach var="item" items="${requestScope.depts}" varStatus="status">
      <option value="${item.departCode}" ${role.department.departCode eq item.departCode?' selected':''}>${item.fullDepartName}</option>
     </c:forEach>
    </select>
   
   </td>
   
  </tr>
  
   <tr>
    <td  align="center" bgcolor="#EFF3FF">菜单模板</td>
    <td  bgcolor="#EFF3FF" colspan="3">
     <select name="deptMenuTemplate" id="deptMenuTemplate" class="textfield">
       <option value=""></option>
      <c:forEach var="item" items="${requestScope.deptMenuTemplates}">
      <option value="${item.id}" ${role.deptMenuTemplate.id eq item.id?' selected':''}>${item.templateName}</option>
     </c:forEach>
    </select>
   
   </td>
   
  </tr>
  
  
   
  <tr bgcolor="#8db5e9">
    <td height="1" colspan="5"  align="center" bgcolor="#FFFFFF"></td>
    </tr>
  <tr bgcolor="#8db5e9">
    <td colspan="2" bgcolor="#8db5e9"><STRONG><FONT color=#ffffff>授权名称</FONT></STRONG></td>
    <td width="74%"><FONT color=#ffffff><STRONG>选择</STRONG></FONT> </td>
  </tr>
  
  <c:forEach  var="item" items="${requestScope.authorizations}" varStatus="status">
  <tr onmouseover="this.style.backgroundColor = '#FFFFFF'" style="CURSOR: hand" 
      onmouseout="this.style.backgroundColor = ''" bgColor=#ebf2f9>
    <td colspan="2" bgcolor="#EFF3FF">${item.name} </td>
    <td  align="left" bgcolor="#EFF3FF">
<input name="au_check" type="checkbox" <c:if test="${item.checked}">checked</c:if> onclick="func(this,${item.id});">
<input id="au_check_id${item.id}"  name="au_check_name" type="hidden" value="<c:if test="${item.checked}">${item.id}|true</c:if><c:if test="${not item.checked}">${item.id}|false</c:if>">
	                      
	</td>
  </tr>
  
 
  </c:forEach>
  <tr>
    <td colspan="2" bgcolor="#EFF3FF"><input name="submit" type="submit" class="button" value="保存角色授权">      </td>
    <td  align="center" bgcolor="#EFF3FF">&nbsp;</td>
  </tr>
  </form>	
</table> 
						
				
<script language="javascript">
  function au_check(obj, au_check_name)
  { alert("hd");
    if(obj.check==true)
    {
      document.getElementById(au_check_name).value='true';
    }
    else
    {
      document.getElementById(au_check_name).value='false';
    }
  }
  function func(obj,itemId){ 
     //alert("is au_check:" + obj.checked);
     var valueObj = document.getElementById("au_check_id"+itemId);
     valueObj.value = ""+itemId+"|"+(obj.checked?true:false);
     //alert(valueObj.value);
  }
</script>
</body>
</html>
