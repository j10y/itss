<%@ page contentType="text/plain;charset=gbk" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<c:set var="len" value="${fn:length(list)-1}" ></c:set>
[
<c:forEach items="${list}" var="obj" varStatus="i">
{
	id:'${obj.id}',
	text:'${obj.menuName}'+'-----'+'${obj.menuUrl}',
	parentId:'${obj.parentItem.id}',
	<c:if test="${obj.leafFlag == 0}">
		text:'${obj.menuName}',
	</c:if>
	<c:if test="${obj.leafFlag == 1}">
		text:'${obj.menuName}'+'-----'+'${obj.menuUrl}',
		leaf:true,
		listeners: {click: {
                       fn: function(){
                          window.location = "#";
                       }
			      }
	    },
	</c:if>
	expanded: true
}
	<c:if test="${i.index<len}">,</c:if>
</c:forEach>
]