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
<br>
<TABLE width="100%" border=0 align=center cellPadding=0 cellSpacing=0>
  <TBODY>
    <TR>
      <TD bgcolor="#CCCCCC">
      
                
				<table width=100% border=0 align=center cellpadding="2" cellspacing=1>
                  <tbody>
                    <tr bgcolor="#3a95c2"><!-- #ffcc99 -->
                     
                      <td   width="20%"  height="25"  valign="middle"><font color="#FFFFFF">授权名称</font></td>
                      <td  width="20%" height="25" valign="middle"><font color="#FFFFFF">权限</font></td>
                      <td nowrap height="25" valign="middle"><font color="#FFFFFF">资源</font></td>
                      <%--<td nowrap height="25" valign="middle"><font color="#FFFFFF">方法/URL后缀</font></td>
                      <td nowrap height="25" valign="middle"><font color="#FFFFFF">所属模块</font></td>
                      
                      --%><td width="120" height="25" align="center" valign="middle">
                      <font color="#FFFFFF">修改</font> </td>
                      <td width="50"  height="25" align="center" valign="middle">
                      <font color="#FFFFFF">删除</font> </td>
                    </tr> 
					<c:forEach  var="item" items="${requestScope.authorizations}" varStatus="status">
<c:if test="${empty param.methodCall ||param.methodCall eq 'listAuthorizations' || param.methodCall eq 'toEditAuthorization' &&param.id ne item.id ||param.methodCall eq 'toAddAuthorization'}">

                    <tr  bgcolor="<c:if test="${status.index%2==0}" var="t">#dfe4e8</c:if><c:if test="${!t}">#FFFFFF</c:if>"><!-- #FEFAFA -->
                      
                      <td width="20%" valign="middle">${item.name}</td>
                      <td  width="20%" valign="middle">${item.right.keyName}</td>
                      <td nowrap valign="middle">${item.resource.name}(${item.resource.shortClassMethodName})</td>
                      <%--<td nowrap valign="middle">${item.methodName}</td>
                      <td nowrap valign="middle">${item.module.name}</td>
                      --%><td nowrap height="25" align="center" valign="middle">
                          <a href="${pageContext.request.contextPath}/admin/authorizationManage.do?methodCall=toEditAuthorization&id=${item.id}">修改</a>
                      </td>
                      <td nowrap height="25" align="center" valign="middle">
						<a href="${pageContext.request.contextPath}/admin/authorizationManage.do?methodCall=removeAuthorization&id=${item.id}">删除</a>
					 </td>
                    </tr>
</c:if>
<c:if test="${!empty param.methodCall && param.methodCall eq 'toEditAuthorization' && param.id eq item.id }">      
					<form action="${pageContext.request.contextPath}/admin/authorizationManage.do?methodCall=saveAuthorization" method="post">
                    <tr bgcolor="#fdecae">
                   
                     
                      <td width="20%"    valign="middle">
                      <input name="id" type="hidden" style="width:100px;" value="${authorization.id}" >           
                       <input name="name" style="width:100%;" value="${authorization.name}" >             
                      </td>
                      <td width="20%"  height="25" valign="middle">
	                      <select name="rightId" style="width:100%;">
	                      <option value=""></option>
	                     	 <c:forEach  var="item" items="${rights}">
	                          <option value="${item.id}" 
	                          <c:if test="${authorization.right.id eq item.id}"> selected</c:if>
	                          >${item.keyName}
	                      	</option>
                      		</c:forEach>
	                      </select>
                      </td>
                       <td nowrap height="25" valign="middle">
	                      <select name="resourceId" style="width:100%;">
	                      <option value=""></option>
		                     	 <c:forEach  var="item" items="${resources}">
		                          <option value="${item.id}" 
		                          <c:if test="${authorization.resource.id eq item.id}"> selected</c:if>
		                          >${item.name}(${item.shortClassMethodName})
		                      </option>
	                      </c:forEach>
	                      </select>
                      </td>
                      <td  height="25" align="center" valign="middle">
                      <input type="submit" name="Submit" value="提交">
        				<a href="${pageContext.request.contextPath}/admin/authorizationManage.do?methodCall=listAuthorizations">取消</a></td>
                      <td  height="25" align="center" valign="middle">
						<a href="${pageContext.request.contextPath}/admin/authorizationManage.do?methodCall=removeAuthorization&id=${authorization.id}">删除</a>
					 </td>
                    </tr>
                    </form>
</c:if>
</c:forEach>
<c:if test="${!empty param.methodCall && param.methodCall eq 'toAddAuthorization'  }">      
					<form action="${pageContext.request.contextPath}/admin/authorizationManage.do?methodCall=saveAuthorization" method="post">
                    <tr bgcolor="#fdecae">
						<td nowrap  height="25" valign="middle"> 
                         <input name="name" style="width:100%;" value="" >             
                         </td>
                      
                     <td nowrap height="25" valign="middle">
	                      <select name="rightId" style="width:100%;">
	                      <option value=""></option>
		                     	 <c:forEach  var="item" items="${rights}">
		                          <option value="${item.id}" 
		                          <c:if test="${authorization.right.id eq item.id}"> selected</c:if>
		                          >${item.keyName}
		                      </option>
	                      </c:forEach>
	                      </select>
                      </td>
                       <td nowrap height="25" valign="middle">
	                      <select name="resourceId" style="width:100%;">
	                      <option value=""></option>
		                     	 <c:forEach  var="item" items="${resources}">
		                          <option value="${item.id}" 
		                          <c:if test="${authorization.resource.id eq item.id}"> selected</c:if>
		                          >${item.name}(${item.shortClassMethodName})
		                      </option>
	                      </c:forEach>
	                      </select>
                      </td>
                      
                      <td height="25" align="center" colspan="2" valign="middle">
                      <input type="submit" name="Submit" value="保存">
					  <a href="${pageContext.request.contextPath}/admin/authorizationManage.do?methodCall=listAuthorizations">取消</a>
					  </td>
					  </tr>
					  </form>
</c:if>                   
					                   
                    <tr bgcolor="#E8E8E8">
                      <td  height="25" valign="middle">
                      <a href="${pageContext.request.contextPath}/admin/authorizationManage.do?methodCall=toAddAuthorization">添加记录</a>
                      </td>
                      <td  height="25" colspan="3" valign="middle"><table width="100%" height="20" border="0" align="center" cellspacing="2">
                          
                      </table></td>
                      <td height="25" align="center" valign="middle">&nbsp;</td><%--
                      <td height="25" align="center" valign="middle">&nbsp;</td>
                      <td height="25" align="center" valign="middle">&nbsp;</td>
                    --%></tr>
                  </tbody>
                </table></TD>
            </TR>
          </TBODY>
      </TABLE></TD>
    </TR>
  </TBODY>
</TABLE>
</body>
</html>
