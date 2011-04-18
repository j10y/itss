<%@ page contentType="text/plain;charset=gbk" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<c:set var="len" value="${fn:length(list)-1}" ></c:set>
[
<c:forEach items="${list}" var="obj" varStatus="i">
{
	id:'${obj.id}',
	text:'${obj.menuName}',
	<c:if test="${obj.leafFlag == 1}">
		leaf:true,
		listeners: {click: {
                       fn: function(){
                          window.location = "#";
                       }
			      }
	    },
	</c:if>
	<c:if test="${obj.enabled == 1}">
	checked: true,
	</c:if>
	<c:if test="${obj.enabled == 0}">
	checked: false,
	</c:if>
	href: '#',
	expanded: true
}
	<c:if test="${i.index<len}">,</c:if>
</c:forEach>
]