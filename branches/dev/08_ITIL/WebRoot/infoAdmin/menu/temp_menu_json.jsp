<%@ page contentType="text/plain;charset=utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<c:set var="len" value="${fn:length(list)-1}" ></c:set>
<c:set var="name" value="" ></c:set>
[
<c:forEach items="${list}" var="obj" varStatus="i">
{
	id:'${obj.id}',
	text:'${obj.menuName}',
	parentId:'${obj.parentItem.id}',
	<c:if test="${obj.leafFlag == 1}">
		leaf:true,
	</c:if>
	<c:if test="${obj.enable == 1}">
			checked: true,
	</c:if>
	<c:if test="${obj.enable == 0}">
			checked: false,
	</c:if>
	expanded: true
}
	<c:if test="${i.index<len}">,</c:if>
</c:forEach>
]