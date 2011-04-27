<%@page contentType="text/html; charset=gbk"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<HTML>
<HEAD><TITLE>资源列表</TITLE>
<%@include file="/includefiles.jsp"%>
<META http-equiv=Content-Type content="text/html; charset=gbk">
</head>
 <link href="../css/style.css" rel="stylesheet" type="text/css">
  <LINK href="../css/extremecomponents.css" type="text/css" rel="stylesheet">
<body>


<TABLE width="100%" border=0 align=center cellPadding=0 cellSpacing=0>
  <TBODY>
    <TR>
      <TD bgcolor="#CCCCCC">
      
                
				<table width=100% border=0 align=center cellpadding="2" cellspacing=1>
                  <tbody>
                    <tr bgcolor="#3a95c2"><!-- #ffcc99 -->
                     
                      <td  nowrap height="25"  valign="middle"><font color="#FFFFFF">角色名称</font></td>
             
                     <td nowrap height="25" valign="middle"><font color="#FFFFFF">描述</font></td>
                     
                      <td nowrap height="25" align="center" valign="middle">
                      <font color="#FFFFFF">修改</font> </td>
                      <td nowrap height="25" align="center" valign="middle">
                      <font color="#FFFFFF">删除</font> </td>
                    </tr> 
					<c:forEach  var="item" items="${requestScope.roles}" varStatus="status">
<c:if test="${empty param.methodCall ||param.methodCall eq 'listRoles' || param.methodCall eq 'toEditRight' &&param.id ne item.id ||param.methodCall eq 'toAddRole'}">

                    <tr  bgcolor="<c:if test="${status.index%2==0}" var="t">#dfe4e8</c:if><c:if test="${!t}">#FFFFFF</c:if>"><!-- #FEFAFA -->
                      
                      <td nowrap valign="middle">
                      <a href="${pageContext.request.contextPath}/admin/roleManage.do?methodCall=toEditRoles&id=${item.id}">${item.name}</a>
                      </td>
                      <td nowrap valign="middle">${item.descn}</td>
                     
                      <td nowrap height="25" align="center" valign="middle">
                          <a href="${pageContext.request.contextPath}/admin/roleManage.do?methodCall=toEditRoles&id=${item.id}">修改</a>
                      </td>
                      <td nowrap height="25" align="center" valign="middle">
						<a href="${pageContext.request.contextPath}/admin/roleManage.do?methodCall=removeRoles&id=${item.id}">删除</a>
					 </td>
                    </tr>
</c:if>

</c:forEach>
            
					                   
                    <tr bgcolor="#E8E8E8">
                      <td  height="25" valign="middle"><INPUT type="button" size=20 value="新建角色" 
                      	onclick="window.location.href='${pageContext.request.contextPath}/admin/roleManage.do?methodCall=toAddRoles'">

</td>
                      <td  height="25" colspan="3" valign="middle"><table width="100%" height="20" border="0" align="center" cellspacing="2">
                         
                      </table></td>
                      
                      <%--<td height="25" align="center" valign="middle">&nbsp;</td>
                      <td height="25" align="center" valign="middle">&nbsp;</td>
                    --%></tr>
                  </tbody>
                </table></TD>
            </TR>
          </TBODY>
      </TABLE></TD>
    </TR>
  </TBODY>
</TABLE><br>
</body>
</html>
