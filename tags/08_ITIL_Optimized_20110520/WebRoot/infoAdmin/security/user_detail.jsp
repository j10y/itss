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


<body>
			
<TABLE cellSpacing=1 cellPadding=3 width="100%" bgColor=#6298e1 border=0>
  <TBODY>
  <TR>
    <TD noWrap height=24><FONT color=#ffffff><IMG height=18 
      src="${pageContext.request.contextPath}/images/Explain.gif" width=18 align=absMiddle 
      border=0>&nbsp;<STRONG>系统用户管理：添加、修改角色，为用户授予角色</STRONG></FONT></TD></TR>
    <TR>
    <TD noWrap align=middle bgColor=#ebf2f9 height=36>
    <a href="${pageContext.request.contextPath}/admin/userRoleManage.do?methodCall=listUsers">系统用户列表</a>&nbsp;
     <A href="${pageContext.request.contextPath}/admin/userRoleManage.do?methodCall=toAddUsers">增加系统用户</A>
      </TD>
    </TR>
  </TBODY>
</TABLE>
 
 
<TABLE cellSpacing=1 cellPadding=0 width="100%" border=0>
 <TBODY>
 <TR>
 <TD height=30>系统后台管理&nbsp;-&gt;&nbsp;用户管理&nbsp;-&gt;&nbsp;用户信息
  </TD>
  </TR>
  </TBODY>
</TABLE>

  
 
       	
       	
       	
  <table width="100%" border="0" cellpadding="3" cellspacing="1" bgcolor="#6298E1">
  <form name="userForm" id="userForm" action="${pageContext.request.contextPath}/admin/userRoleManage.do"  method="post">
  
       	
  <tr>
    <td width="11%"  align="center" bgcolor="#EFF3FF">用户名：</td>
    <td  bgcolor="#EFF3FF" colspan="5">
     	<INPUT name="methodCall" type="hidden" value="saveUsers">
    	<INPUT name="id" type="hidden" value="${user.id}">
       	<INPUT name="departCode" type="hidden" value="${user.departCode}">
       	<INPUT name="itcode" type="hidden" value="${user.itcode}">
       	<INPUT name="employeeCode" type="hidden" value="${user.employeeCode}">
       	<INPUT name="mobilePhone" type="hidden" value="${user.mobilePhone}">
       	<INPUT name="telephone" type="hidden" value="${user.telephone}">
       
       		
    <INPUT name="userName" size=20 value="${user.userName}" class="textfield">                	
     
    </td>
  </tr>
  
   <tr>
    <td  align="center" bgcolor="#EFF3FF">密&nbsp;码:</td>
    <td colspan="3"  bgcolor="#EFF3FF">
    <INPUT name="password" size=20 value="${user.password}" class="textfield">  
   </td>
   </tr>
  
   <tr>
    <td  align="center" bgcolor="#EFF3FF">ITCODE：</td>
    <td colspan="3"  bgcolor="#EFF3FF">
    <INPUT name="itcode" size=20 value="${user.itcode}" class="textfield">  
   </td>
   </tr>
   
  <tr>
    <td  align="center" bgcolor="#EFF3FF">用户姓名：</td>
    <td colspan="3"  bgcolor="#EFF3FF">
    <INPUT name="realName" size=20 value="${user.realName}" class="textfield">  
   </td>
   </tr>

   <tr>
    <td  align="center" bgcolor="#EFF3FF">电子邮件：</td>
    <td colspan="3"  bgcolor="#EFF3FF">
    <INPUT name="email" size=50 value="${user.email}" class="textfield">  
   </td>
   </tr>
   
   <tr>
    <td  align="center" bgcolor="#EFF3FF">隶属部门</td>
    <td  bgcolor="#EFF3FF" colspan="4">
     <select name="department" id="department" class="textfield" onchange="findRoleByDept2();">
       <option value="50000075">上品折扣</option>
      <c:forEach var="item" items="${requestScope.depts}" varStatus="status">
      <option value="${item.departCode}" 
  ${user.department.departCode eq item.departCode or requestScope.dept.departCode eq item.departCode?' selected':''}>${item.fullDepartName}</option>
     </c:forEach>
    </select>
   
   </td>
   
  </tr>
  
   <tr>
    <td  align="center" bgcolor="#EFF3FF">是否可用：</td>
    <td colspan="3"  bgcolor="#EFF3FF">
    <select name="enabled" id="enabled" class="textfield">
      <option value="1" ${user.enabled eq 1?' selected':''}>是</option>
      <option value="0" ${user.enabled eq 0?' selected':''}>否</option>
    </select>    
   </td>
   </tr>
   
   <tr>
    <td  align="center" bgcolor="#EFF3FF">是否特殊用户：</td>
    <td colspan="3"  bgcolor="#EFF3FF">
    <select name="specialUser" id="specialUser" class="textfield">
      <option value="1" ${user.specialUser eq 1?' selected':''}>是</option>
      <option value="0" ${user.specialUser eq 0?' selected':''}>否</option>
    </select>  
   </td>
   </tr>
  
  <tr bgcolor="#8db5e9">
    <td height="1" colspan="6"  align="center" bgcolor="#FFFFFF"></td>
  </tr>
  <tr bgcolor="#8db5e9">
    <td bgcolor="#8db5e9"><STRONG><FONT color=#ffffff>角色名称</FONT></STRONG></td>
    <td width="23%" bgcolor="#8db5e9"><STRONG><font color="#ffffff">隶属部门</font></STRONG></td>
    <td width="25%" bgcolor="#8db5e9"><STRONG><font color="#ffffff">对应模板</font></STRONG></td>
    
    <td width="41%"><FONT color=#ffffff><STRONG>选择</STRONG></FONT> </td>
  </tr>
  
  <c:forEach  var="item" items="${requestScope.roles}" varStatus="status">
  <tr onmouseover="this.style.backgroundColor = '#FFFFFF'" style="CURSOR: hand" 
      onmouseout="this.style.backgroundColor = ''" bgColor=#ebf2f9>
    <td bgcolor="#EFF3FF">${item.name} </td>
     <td bgcolor="#EFF3FF">${item.department.departName}</td>
     <td bgcolor="#EFF3FF">${item.deptMenuTemplate.templateName}</td>
     
    <td  align="left" bgcolor="#EFF3FF">
		<input name="au_check" type="checkbox" ${item.checked?' checked':''} onclick="func(this,${item.id});">
	    <input id="role_check_id${item.id}"  name="role_check_name" type="hidden" value="<c:if test="${item.checked}">${item.id}|true</c:if><c:if test="${not item.checked}">${item.id}|false</c:if>">
	                                         
	</td>
  </tr>
  </c:forEach>
  
  <tr>
    <td colspan="3" bgcolor="#EFF3FF"><input type="submit" class="button" value="保存用户授权">
	</td>
    <td  align="center" bgcolor="#EFF3FF">&nbsp;</td>
  </tr>
    </form>
    
</table> 

	
				
<script language="javascript">

 function findRoleByDept2(){ 
     var xxform = document.forms[0];
     xxform.methodCall.value="findRoleByDept";// alert(xxform.name + "|" + xxform.action);
     xxform.submit(); //submit button can't use submit to name
   }
   
   
  function findRoleByDept(){ 
   var serverPath = "${pageContext.request.contextPath}";
   var userName = Ext.getDom("userName");
   var password = Ext.getDom("password"); 
   var itcode = Ext.getDom("itcode");
   var realName = Ext.getDom("realName");
   var email = Ext.getDom("email");

   var department = department.options[department.selectedIndex].value;
   var enabled = enabled.options[enabled.selectedIndex].value;
   var specialUser = specialUser.options[specialUser.selectedIndex].value;
   
   Ext.Ajax.request({
      url: serverPath+"/admin/roleManage.do?methodCall=findRoleByDept", 
      params:{
          userName: userName,
          password: password,
          itcode: itcode,
          realName: realName,
          email: email,
          department: department,
          enabled: enabled,
          specialUser: specialUser
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

  function switchEnabled(obj){
  	var valueObj = document.getElementById("user_enabled");
  	valueObj.value = obj.checked?"1":"0";
  	
  }
  
  function func(obj,itemId){ 
     //alert("is au_check:" + obj.checked);
     var valueObj = document.getElementById("role_check_id"+itemId);
     valueObj.value = ""+itemId+"|"+(obj.checked?true:false);
     //alert(valueObj.value);
  }
  
 
   
</script>
</body>
</html>
