<%@ page contentType="text/html;charset=gbk" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<c:set var="len" value="${fn:length(scirelation)-1}" ></c:set>
[
<c:forEach items="${scirelation}" var="obj" varStatus="i">
{
	id:'${obj.id}',
	<c:if test="${not empty obj.childDepartments }" var="flag">
		text: '${obj.departName}',
		icon : '${pageContext.request.contextPath}/images/other/class.gif',
		cls : "x-btn-text-icon",
	</c:if>
	<c:if test="${not flag}" >
		text: '${obj.departName}',
		icon : webContext+'/images/cls/gears.gif',
		cls : "x-btn-text-icon",
		leaf:true,
	</c:if>
	href: '#',
	expanded: false
}
	<c:if test="${i.index<len}">,</c:if>
</c:forEach>
]