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
	listeners: {click: {
                    fn: function(){
                        var target = '${obj.target}';
                        if(target == 'blank'){
                            var menuId = ${obj.menuId};
                            if(menuId!="10400102"){
                                window.open("../${obj.url}");
                            }else{
                                var Sys = {};
                                var ua = navigator.userAgent.toLowerCase();
                                window.ActiveXObject ? Sys.ie = ua.match(/msie ([\d.]+)/)[1] : (Sys.ff = YS.ua.match(/msie ([\d.]+)/)[1]);
                                
                                if(Sys.ie && Sys.ie=='6.0'){
                                    showModalDialog("../help/about.jsp", "","dialogWidth:"+421+"px;dialogHeight:"+360+"px;dialogLeft:"+200+"px;dialogTop:"+200+"px;help:no");
                                }else{
                                    showModalDialog("../help/about.jsp", "","dialogWidth:"+420+"px;dialogHeight:"+315+"px;dialogLeft:"+200+"px;dialogTop:"+200+"px;help:no");
                                }
                            }
                        }else{
                      	    updateTab("${obj.menuId}", "${obj.menuName}","${obj.url}");
                        }
                        //updateTab("${obj.menuId}", "${obj.menuName}","${obj.url}");
                    }
		      }
    },
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