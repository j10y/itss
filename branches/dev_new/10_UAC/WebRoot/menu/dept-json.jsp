<%@ page contentType="text/html;charset=gbk" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<c:if test="${not empty list}">
<c:set var="len" value="${fn:length(list)-1}" ></c:set>
[
<c:forEach items="${list}" var="obj" varStatus="i">
{
	id:'${obj.menuId}',
	text:'${obj.menuName}',
	leaf:true,
	iconCls : 'icon-class',
	cls : "x-btn-text-icon",
	expanded: true
}<c:if test="${i.index<len}">,</c:if>
</c:forEach>
]
</c:if>
<c:if test="${not empty json}">
	${json }
</c:if>