<%@ page contentType="text/html; charset=GBK"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
	
	<html>
		<head>
			<title>��������</title>
			<%@include file="/includefiles.jsp"%>
	<link href="${pageContext.request.contextPath}/css/olstyle.css" rel="stylesheet" type="text/css">
	
	<SCRIPT language="javascript">
	
	</SCRIPT>
	</head>
	
	<body class="BODY">
	
	<hr>
<table width="300" align="center"><tr><td>
<form action="${pageContext.request.contextPath}/wftask">
	<input type="hidden" name="done" value="y">
	<input type="hidden" name="id" value="${param.id}">
	<fieldset style="align:center;width:300px; border : 1px solid #ff9900;text-align:center;COLOR:#ff9900;FONT-SIZE: 
	12px;font-family: Verdana;padding:5px;">
	<legend>������Ϣ</legend>

		<table width="100%" border="0" align="center" cellpadding="2" cellspacing="2">		
			<tr>
				<td width="50%" align="right" bgcolor="E6EAEC" nowrap>
					�������
				</td>
				<td width="50%" bgcolor="E6EAEC">
					<textarea rows="3" cols="30" name="comment"></textarea>
				</td>			
			</tr>	
			<tr>
				<td width="50%" align="right" bgcolor="E6EAEC" nowrap>
					��������
				</td>
				<td width="50%" bgcolor="E6EAEC" align="center">
					ͨ��<input name="result" type="radio" value="Y/PASS" checked>&nbsp;&nbsp;
					����<input name="result" type="radio" value="Y/RESERVE">&nbsp;&nbsp;
					�ܾ�<input name="result" type="radio" value="N/REJECT">
				</td>			
			</tr>		
		</table>
		<input name="submit" type="submit" value="ȷ��">&nbsp;&nbsp;
		<input name="cancel" type="button" value="ȡ��" onclick="window.close()">
	</fieldset>	 
</form>
</td></tr></table>

</body>
</html>
