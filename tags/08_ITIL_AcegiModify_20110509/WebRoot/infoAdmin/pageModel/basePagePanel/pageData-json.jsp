<%@ page contentType="text/plain;charset=utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<c:set var="len" value="${fn:length(list)-1}" ></c:set>
<c:set var="name" value="" ></c:set>
[
<c:forEach items="${list}" var="obj" varStatus="i">
{
	id:'${obj.id}',
	<c:if test="${obj.mainTableColumn ne null}">
		text: '['+'${obj.systemMainTable.tableCnName}'+']     '+'${obj.mainTableColumn.columnCnName}',
	</c:if>
	<c:if test="${obj.mainTableColumn eq null}">
		<c:if test="${obj.mainTableColumn eq null}">
			text:'['+'FieldSet'+']     '+'${obj.columnCnName}',
		</c:if>
	</c:if>
	<c:if test="${obj.isDisplay == 1}">
			checked: true,
	</c:if>
	<c:if test="${obj.isDisplay == 0}">
			checked: false,
	</c:if>
	icon : '${pageContext.request.contextPath}/images/other/class.gif',
	cls : "x-btn-text-icon",
	<c:if test="${obj.isMustInput == 1}">
			icon : webContext+'/images/cls/gears.gif',
			cls : "x-btn-text-icon",
	</c:if>
	href: '#',
	expanded: true
}
	<c:if test="${i.index<len}">,</c:if>
</c:forEach>
]