<%@ page contentType="text/plain;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<c:set var="len" value="${fn:length(list)-1}" ></c:set>
[
<c:forEach items="${list}" var="obj" varStatus="i">
{
	id:'${obj.id}',
	text:'${obj.menuName}',
	icon: webContext+'/images/cls/class.gif',
	cls:'x-btn-text-icon',
	<c:if test="${obj.leafFlag == 1}">
		leaf:true,
		listeners: {click: {
                       fn: function(){
                          updateTab('${obj.menuName}','${obj.menuName}',webContext+'${obj.menuUrl}');
   						  Ext.getCmp('mainViewport').doLayout();
   						  	}
   						  }
	    },
	</c:if>
	expanded: true
}
	<c:if test="${i.index<len}">,</c:if>
</c:forEach>
]