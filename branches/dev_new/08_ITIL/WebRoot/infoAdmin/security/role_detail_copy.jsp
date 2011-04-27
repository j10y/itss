<%@page contentType="text/html; charset=gbk"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<HTML>
<HEAD><TITLE>授权</TITLE>
<%@include file="/includefiles.jsp"%>
<META http-equiv=Content-Type content="text/html; charset=gbk">
	
</head>
 <link href="../css/style.css" rel="stylesheet" type="text/css">
  <LINK href="../css/extremecomponents.css" type="text/css" rel="stylesheet">
<body>
			

	<br><br>
 <form action="${pageContext.request.contextPath}/admin/roleManage.do"  method="post">
    <TABLE width="100%" id=2 cellSpacing=0 cellPadding=0 width=100% border=0>
         <TBODY>
           <TR>
             <TD width="100%" align="center" vAlign=top  bgColor=#CCCCCC>
      
		       <table width="100%"  border="0" cellspacing="0" cellpadding="0">
		          <tr bgcolor="#EFEFEF">
                   	<td  align=absMiddle>
                   	<INPUT name="methodCall" type="hidden" value="saveRoles">
                   	<INPUT name="id" type="hidden" value="${role.id}">
                   	角色名称:<INPUT name="name" size=20 value="${role.name}"> 
                    </td>
                    <td  align=absMiddle>
                   	描述:<INPUT name="descn" size=20 value="${role.descn}"> 
                      
                    </td>
                    <td align=absMiddle width="53%">

                     
                      
                    </td>
                  </tr>
                </table>
  	
							
				<table width=100% border=0 align=center cellpadding="0" cellspacing=1>
                  <tbody>
                    <tr bgcolor="#3a95c2"><!-- #ffcc99 -->
                     <%--<td   width="20%"  height="25"  valign="middle"><font color="#FFFFFF">模块</font></td>
                      --%><td   width="20%"  height="25"  valign="middle"><font color="#FFFFFF">授权名称</font></td>
            			<td width="200" height="25"  valign="middle">
                      <font color="#FFFFFF">&nbsp;选择</font> </td>
                    </tr> 
                 

				<c:forEach  var="item" items="${authorizations}" varStatus="status">
                    <tr  bgcolor="<c:if test="${status.index%2==0}" var="t">#dfe4e8</c:if><c:if test="${!t}">#FFFFFF</c:if>"><!-- #FEFAFA -->
	                      <%--<td width="20%" valign="middle">${item.module.name}</td>
	                      --%><td nowrap valign="middle">${item.name}</td>
	                      <td nowrap height="25"  valign="middle">&nbsp;
	                         <input name="au_check" type="checkbox" <c:if test="${item.checked}">checked</c:if> onclick="func(this,${item.id});">
	                         <input id="au_check_id${item.id}"  name="au_check_name" type="hidden" value="<c:if test="${item.checked}">${item.id}|true</c:if><c:if test="${not item.checked}">${item.id}|false</c:if>">
	                      </td>
                    </tr>
                  
				</c:forEach>
         

                  </tbody>
                </table></TD>
            </TR>
          </TBODY>
      </TABLE><br>
       <INPUT type="submit" value="保存角色授权">&nbsp;
          <INPUT type="button" value="返回角色列表 " 
      onclick="window.location.href='${pageContext.request.contextPath}/admin/roleManage.do?methodCall=listRoles';">
       
   
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
</form>

</body>
</html>
