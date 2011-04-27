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
<BR>
<TABLE width="100%" border=0 align=center cellPadding=0 cellSpacing=0>
  <TBODY>
    <TR>
      <TD bgcolor="#CCCCCC">
      
                
				<table width=100% border=0 align=center cellpadding="2" cellspacing=1>
                  <tbody>
                    <tr bgcolor="#3a95c2"><!-- #ffcc99 -->
                     
                      <td  nowrap height="25"  valign="middle"><font color="#FFFFFF">权限名称</font></td>
                      <td nowrap height="25" valign="middle"><font color="#FFFFFF">权限关键字</font></td>
                      <td nowrap height="25" valign="middle"><font color="#FFFFFF">描述</font></td><%--
                      <td nowrap height="25" valign="middle"><font color="#FFFFFF">方法/URL后缀</font></td>
                      <td nowrap height="25" valign="middle"><font color="#FFFFFF">所属模块</font></td>
                      
                      --%><td nowrap height="25" align="center" valign="middle">
                      <font color="#FFFFFF">修改</font> </td>
                      <td nowrap height="25" align="center" valign="middle">
                      <font color="#FFFFFF">删除</font> </td>
                    </tr> 
					<c:forEach  var="item" items="${requestScope.rights}" varStatus="status">
<c:if test="${empty param.methodCall ||param.methodCall eq 'listRights' || param.methodCall eq 'toEditRight' &&param.id ne item.id ||param.methodCall eq 'toAddRight'}">

                    <tr  bgcolor="<c:if test="${status.index%2==0}" var="t">#dfe4e8</c:if><c:if test="${!t}">#FFFFFF</c:if>"><!-- #FEFAFA -->
                      
                      <td nowrap valign="middle">
                      ${item.name}
                      </td>
                      <td nowrap valign="middle">${item.keyName}</td>
                      <td nowrap valign="middle">${item.descn}</td>

                      <td nowrap height="25" align="center" valign="middle">
                          <a href="${pageContext.request.contextPath}/admin/rightManage.do?methodCall=toEditRight&id=${item.id}">修改</a>
                      </td>
                      <td nowrap height="25" align="center" valign="middle">
						<a href="${pageContext.request.contextPath}/admin/rightManage.do?methodCall=removeRight&id=${item.id}">删除</a>
					 </td>
                    </tr>
</c:if>
<c:if test="${!empty param.methodCall && param.methodCall eq 'toEditRight' && param.id eq item.id }">      
					<form action="${pageContext.request.contextPath}/admin/rightManage.do?methodCall=saveRight" method="post">
                    <tr bgcolor="#fdecae">
                   
                     
                      <td nowrap   valign="middle">
                      <input name="id" type="hidden" style="width:100px;" value="${right.id}" >           
                       <input name="name" style="width:100%;" value="${right.name}" >             
                      </td>
                      <td  nowrap height="25" valign="middle"> 
                       <input name="keyName" style="width:100%;" value="${right.keyName}" >              
                      </td>
                      <td nowrap  height="25" valign="middle"> 
                       <input name="descn" style="width:100%;" value="${right.descn}">              
                      </td><%--
                       <td nowrap  height="25" valign="middle"> 
                       <input name="methodName" style="width:100%;" value="${right.methodName}">              
                      </td>
                      
                      <td nowrap height="25" valign="middle">
                      <select name="moduleId" style="width:100%;">
                      <option value=""></option>
                      <c:forEach  var="item" items="${modules}">
                          <option value="${item.id}" 
                          <c:if test="${right.module.id eq item.id}"> selected</c:if>
                          >${item.name}</option>
                      </c:forEach>
                      </select>
                      </td>
                      --%><td nowrap height="25" align="center" valign="middle">
                      <input type="submit" name="Submit" value="提交">
        				<a href="${pageContext.request.contextPath}/admin/rightManage.do?methodCall=listRights">取消</a></td>
                      <td nowrap height="25" align="center" valign="middle">
						<a href="${pageContext.request.contextPath}/admin/rightManage.do?methodCall=removeRight&id=${right.id}">删除</a>
					 </td>
                    </tr>
                    </form>
</c:if>
</c:forEach>
<c:if test="${!empty param.methodCall && param.methodCall eq 'toAddRight'  }">      
					<form action="${pageContext.request.contextPath}/admin/rightManage.do?methodCall=saveRight" method="post">
                    <tr bgcolor="#fdecae">
						<td nowrap  height="25" valign="middle"> 
                       <input name="name" style="width:100%;"  >             
                      </td>
                      <td nowrap  height="25" valign="middle"> 
                       <input name="keyName" style="width:100%;"  >              
                      </td>
                      <td nowrap  height="25" valign="middle"> 
                       <input name="descn" style="width:100%;" value="">              
                      </td>
                       <%--<td nowrap  height="25" valign="middle"> 
                       <input name="methodName" style="width:100%;" value="">              
                      </td>
                      
                      <td  height="25" valign="middle">
                      <select name="moduleId" style="width:100%;">
                      <option value=""></option>
                      <c:forEach  var="item" items="${modules}">
                          <option value="${item.id}" 
                          <c:if test="${right.module.id eq item.id}"> selected</c:if>
                          >${item.name}</option>
                      </c:forEach>
                      </select>
                      </td>
                      --%><td height="25" align="center" colspan="2" valign="middle">
                      
                      <input type="submit" name="Submit" value="保存">&nbsp;
					  <a href="${pageContext.request.contextPath}/admin/rightManage.do?methodCall=listRights">取消</a>
					  </td>
					  </tr>
					  </form>
</c:if>                   
					                   
                    <tr bgcolor="#E8E8E8">
                      <td  height="25" valign="middle"><a href="${pageContext.request.contextPath}/admin/rightManage.do?methodCall=toAddRight">添加记录</a></td>
                      <td  height="25" colspan="3" valign="middle"><table width="100%" height="20" border="0" align="center" cellspacing="2">
                        
                      </table></td>
                      <td height="25" align="center" valign="middle">&nbsp;</td>
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
</TABLE>
</body>
</html>
