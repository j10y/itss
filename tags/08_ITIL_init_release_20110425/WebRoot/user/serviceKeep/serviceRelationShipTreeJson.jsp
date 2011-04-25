<%@ page contentType="text/plain;charset=gbk" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<c:set var="len" value="${fn:length(list)-1}" ></c:set>
[
<c:forEach items="${list}" var="obj" varStatus="i">
{
	id:'${obj.id}',
	<c:if test="${obj.serviceCatalogue ne null}">
		<c:if test="${empty obj.dispFlag}">
			text: '${obj.serviceCatalogue.name}',
			icon : '${pageContext.request.contextPath}/images/other/class.gif',
			cls : "x-btn-text-icon",
		</c:if>
		<c:if test="${obj.dispFlag eq '1'}">
			text: '${obj.serviceCatalogue.name}',
			icon : '${pageContext.request.contextPath}/images/other/class.gif',
			cls : "x-btn-text-icon",
		</c:if>
		<c:if test="${obj.dispFlag eq '0'}">
			text: '${obj.serviceCatalogue.name}',
			icon : '${pageContext.request.contextPath}/images/other/dispClass.gif',
			cls : "x-btn-text-icon",
		</c:if>
	</c:if>
	<c:if test="${obj.serviceItem ne null}">
		<c:if test="${empty obj.dispFlag}">
			text: '${obj.serviceItem.name}',
			icon : webContext+'/images/cls/gears.gif',
			cls : "x-btn-text-icon",
		</c:if>
		<c:if test="${obj.dispFlag eq '1'}">
			text: '${obj.serviceItem.name}',
			icon : webContext+'/images/cls/gears.gif',
			cls : "x-btn-text-icon",
		</c:if>
		<c:if test="${obj.dispFlag eq '0'}">
			text: '${obj.serviceItem.name}',
			icon : webContext+'/images/cls/dispGears.gif',
			cls : "x-btn-text-icon",
		</c:if>
	</c:if>
	href: '#',
	expanded: true
}
	<c:if test="${i.index<len}">,</c:if>
</c:forEach>
]