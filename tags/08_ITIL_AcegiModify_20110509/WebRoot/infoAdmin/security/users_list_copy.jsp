<%@page contentType="text/html; charset=gbk"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<HTML>
	<HEAD>
		<TITLE>��Դ�б�</TITLE>
		<%@include file="/includefiles.jsp"%>
		<META http-equiv=Content-Type content="text/html; charset=gbk">
	</head>
	<link href="../css/style.css" rel="stylesheet" type="text/css">
	<LINK href="../css/extremecomponents.css" type="text/css"
		rel="stylesheet">
	<body>

<TABLE width="100%" id=2 cellSpacing=0 cellPadding=0 width=100%
	border=0>
	<TBODY>
		<TR>
			<TD width="100%" align="center" vAlign=top bgColor=#CCCCCC>
				<table width="100%" border="0" cellspacing="1" cellpadding="2">
					<tr bgcolor="#EFEFEF">
						<form
							action="${pageContext.request.contextPath}/admin/userRoleManage.do"
							method="get">
						<td  align=absMiddle>
						    <input name="methodCall" type="hidden" value="listUsers">
							�û���<INPUT name="userName" size=20>
						</td>
						<td  align=absMiddle>
							��ʵ����<INPUT name="realName" size=20>					
						</td>
						<td  align=absMiddle>
							<INPUT id="search" style="height:23px;" type="submit"
								value="�����û�">			
						</td>
						
						</form>
					</tr>

					<tr bgcolor="#EFEFEF">
						<td width="10%" colspan="3">
							<a
								href="${pageContext.request.contextPath}/admin/userRoleManage.do?methodCall=toAddUsers">������û�</a>
						</td>

					</tr>
				</table>
				<TABLE width="100%" border=0 align=center cellPadding=0
					cellSpacing=0>
					<TBODY>
						<TR>
							<TD bgcolor="#CCCCCC">


			<table width=100% border=0 align=center cellpadding="2"
				cellspacing=1>
				<tbody>
					<tr bgcolor="#3a95c2">
						<!-- #ffcc99 -->
			
						<td nowrap height="25" valign="middle">
							<font color="#FFFFFF">����</font>
						</td>
			
						<td nowrap height="25" valign="middle">
							<font color="#FFFFFF">�û���</font>
						</td>
					    <td nowrap height="25" valign="middle">
							<font color="#FFFFFF">����</font>
						</td>
						<td nowrap height="25" valign="middle">
							<font color="#FFFFFF">�����ʼ�</font>
						</td>
						<td nowrap height="25" valign="middle">
							<font color="#FFFFFF">�Ƿ����</font>
						</td>
						<td nowrap height="25" align="center" valign="middle">
							<font color="#FFFFFF">�޸�</font>
						</td>
						<td nowrap height="25" align="center" valign="middle">
							<font color="#FFFFFF">ɾ��</font>
						</td>
					</tr>
					<c:forEach var="item" items="${requestScope.users}"
						varStatus="status">
						<c:if
							test="${empty param.methodCall ||param.methodCall eq 'listUsers' || param.methodCall eq 'toEditUsers' &&param.id ne item.id ||param.methodCall eq 'toAddUser'}">
			
							<tr
								bgcolor="<c:if test="${status.index%2==0}" var="t">#dfe4e8</c:if><c:if test="${!t}">#FFFFFF</c:if>">
								<!-- #FEFAFA -->
			
								<td nowrap valign="middle">
									<a
										href="${pageContext.request.contextPath}/admin/userRoleManage.do?methodCall=toEditUsers&id=${item.id}">${item.realName}</a>
								</td>
								<td nowrap valign="middle">
									${item.userName}
								</td>
								<td nowrap valign="middle">
									${item.password}
								</td>
								<td nowrap valign="middle">
									${item.email}
								</td>
								<td nowrap valign="middle">
									
									<c:if test="${item.enabled eq 1}">��</c:if>
									<c:if test="${item.enabled eq 0}">��</c:if>
									
								</td>
								<td nowrap height="25" align="center" valign="middle">
									<a
										href="${pageContext.request.contextPath}/admin/userRoleManage.do?methodCall=toEditUsers&id=${item.id}">�޸�</a>
								</td>
								<td nowrap height="25" align="center" valign="middle">
									<a
										href="${pageContext.request.contextPath}/admin/userRoleManage.do?methodCall=removeUsers&id=${item.id}">ɾ��</a>
								</td>
							</tr>
						</c:if>
			
					</c:forEach>
			
			
					<tr bgcolor="#E8E8E8">
						<td height="25" valign="middle">
							<INPUT type="button" size=20 value="�½��û�"
								onclick="window.location.href='${pageContext.request.contextPath}/admin/userRoleManage.do?methodCall=toAddUsers'">
							
						</td>
						<td height="25" colspan="3" valign="middle">
							
						</td>
			
						<td height="25" align="center" valign="middle">&nbsp;</td>
			            <td height="25" align="center" valign="middle">&nbsp;</td>
			          <td height="25" align="center" valign="middle">&nbsp;</td>
					</tr>
				</tbody>
			</table>
							</TD>
						</TR>
					</TBODY>
				</TABLE>
			</TD>
		</TR>
	</TBODY>
</TABLE>
		<br>
	</body>
</html>
