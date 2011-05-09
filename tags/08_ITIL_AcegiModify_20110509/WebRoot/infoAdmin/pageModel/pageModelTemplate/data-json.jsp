 <%@ page contentType="text/plain;charset=gbk" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<c:set var="len" value="${fn:length(list)-1}" ></c:set>
[
<c:forEach items="${list}" var="obj" varStatus="i">
{
	id:'${obj.id}',
	text:'${obj.pagePanel.title}'+'('+'${obj.pagePanel.id}'+')',
	icon : '${pageContext.request.contextPath}/images/other/export.gif',
	cls : "x-btn-text-icon",
	expanded: true 
}
	<c:if test="${i.index<len}">,</c:if>
</c:forEach> 
]