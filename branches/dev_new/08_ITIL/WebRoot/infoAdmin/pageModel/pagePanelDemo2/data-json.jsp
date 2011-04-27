<%@ page contentType="text/plain;charset=gbk" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<c:set var="len" value="${fn:length(list)-1}" ></c:set>
[
<c:forEach items="${list}" var="obj" varStatus="i">
{
	id:'${obj.id}',
	<c:if test="${obj.mainTableColumn ne null}">
		text: '${obj.mainTableColumn.columnCnName}',
	</c:if>
	
	icon : webContext+'/images/cls/gears.gif',
	cls : "x-btn-text-icon",
	<c:if test="${obj.isDisplay == 1}">
	checked: true,
	</c:if>
	<c:if test="${obj.isDisplay == 0}">
	checked: false,
	</c:if>
	href: '#',
	expanded: true
}
	<c:if test="${i.index<len}">,</c:if>
</c:forEach>
]