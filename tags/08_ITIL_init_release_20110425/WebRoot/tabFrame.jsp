<%@ page language="java" pageEncoding="utf-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!-- 注意此处用&转换原有参数中的'****'，如果引用连接的参数中本来就有'****'字符串，可能会出现解析错误，要避免 -->
<c:set var='url' value='${fn:replace(param.url, "****", "&")}'/>
<IFRAME SRC="${url}" width="100%" height="100%" frameborder="0" id='iframeTest'></IFRAME>
