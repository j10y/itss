<%@ page contentType="text/html;charset=gbk" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
	<title>�˵��༭</title>
	<%@include file="/includefiles.jsp"%>
	<meta http-equiv="Content-Type" content="text/html; charset=gbk">
	<script type="text/javascript">
		function checkForm(){
		    var xform = document.getElementById("myForm");
			if(xform.title.value == ""){ 
			    alert("���ⲻ��Ϊ�գ�");
				return;
			}else{
				xform.action= webContext+"/servlet/sysMenuSave";   
	    		xform.submit(); 	
    		}
		}
	</script>
</head>
<body style="background-color: white">
	<br/><br/>
	<form id="myForm" action= webContext+"/servlet/sysMenuSave" method="post">
		<input type="hidden" name="id" value="${obj.id}"/>
		<input type="hidden" name="parentId" value="${obj.parentMenu.id}"/>
		<input type="hidden" name="leaf" value="${obj.leafFlag}"/>
		<input type="hidden" name="number" value="${obj.menuOrder}"/>
		<input type="hidden" name="smtId" value="${obj.systemMenuTemplate.id}"/>
		<table align="center">
			<tr><td width="60">���⣺</td>
				<td><input type="text" name="title" value="${obj.menuName}"/></td></tr>
			<c:if test="${obj.leafFlag==1}">
			<tr><td>URL��</td>
				<td><input type="text" name="url" value="${obj.menuUrl}"/></td></tr>
			</c:if>
			<tr><td colspan="2" align="center">
					<br/>
					<input type="button" value="����" onclick="checkForm();">
					&nbsp;&nbsp;
					<input type="reset" value="����">
					&nbsp;&nbsp;
					<input type="button" value="ȡ��" onclick="window.parent.FormEditWin.close();">
				</td></tr>
		</table>
	</form>
</body>
</html>
