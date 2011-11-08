<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	
	<script type="text/javascript" src="${pageContext.request.contextPath}/JS/Jquery/jquery.blockUI.js"></script>
	<script type="text/javascript">
	//等待效果
	function waiting(){
		//当保存的时候出现遮罩
	   	$.blockUI({message: '请稍后...' , 
							  overlayCSS:{
							  		backgroundColor:'#eee'
							  },
							  css: {
							  		border:'none',
							  		padding:'15px', 
							  		backgroundColor:'#000', 
							  		'-webkit-border-radius':'10px',
							  		'-moz-border-radius':'10px',
							  		opacity:.5,
							  		color: '#fff'}
							 });
	}
	//关闭等待效果
	function closeWait(){
		$.unblockUI();
	}
	function getPage(){ 
		   waiting();
		   var xform = document.forms[0];
		   var pageNo=document.getElementById("skipPage").value;
		   document.getElementById("page").value=pageNo;
		   xform.submit();
		   
	 }
	function pagequery(pageNo){
		
		waiting(); 
		//alert();
		var xform = document.forms[0];
		document.getElementById("page").value=pageNo;
		xform.submit();
	}
	</script>
	<style> 
	A:link {
	    color: #2f2f2f;
		text-decoration: none;
	}
	A:visited {
	    color: #2f2f2f;
		text-decoration: none;
	}
	A:hover { 
		color: #FF6600;
		text-decoration: none;
	}
	.first{
		height:26px;width:28px;background: url('${pageContext.request.contextPath}/Images/Page/pagination_first.gif');border: 0px solid;CURSOR:hand;
	}
	.first_a{
		height:26px;width:28px;background: url('${pageContext.request.contextPath}/Images/Page/pagination_first_a.gif');border: 0px solid;CURSOR:hand;
	}
	.first_b{
		height:26px;width:28px;background: url('${pageContext.request.contextPath}/Images/Page/pagination_first_b.gif');border: 0px solid;CURSOR:hand;
	}
	.prev{
		height:26px;width:28px;background: url('${pageContext.request.contextPath}/Images/Page/pagination_prev.gif');border: 0px solid;CURSOR:hand;
	}
	.prev_a{
		height:26px;width:28px;background: url('${pageContext.request.contextPath}/Images/Page/pagination_prev_a.gif');border: 0px solid;CURSOR:hand;
	}
	.prev_b{
		height:26px;width:28px;background: url('${pageContext.request.contextPath}/Images/Page/pagination_prev_b.gif');border: 0px solid;CURSOR:hand;
	}
	.next{
		height:26px;width:28px;background: url('${pageContext.request.contextPath}/Images/Page/pagination_next.gif');border: 0px solid;CURSOR:hand;
	}
	.next_a{
		height:26px;width:28px;background: url('${pageContext.request.contextPath}/Images/Page/pagination_next_a.gif');border: 0px solid;CURSOR:hand;
	}
	.next_b{
		height:26px;width:28px;background: url('${pageContext.request.contextPath}/Images/Page/pagination_next_b.gif');border: 0px solid;CURSOR:hand;
	}
	.last{
		height:26px;width:28px;background: url('${pageContext.request.contextPath}/Images/Page/pagination_last.gif');border: 0px solid;CURSOR:hand;
	}
	.last_a{
		height:26px;width:28px;background: url('${pageContext.request.contextPath}/Images/Page/pagination_last_a.gif');border: 0px solid;CURSOR:hand;
	}
	.last_b{
		height:26px;width:28px;background: url('${pageContext.request.contextPath}/Images/Page/pagination_last_b.gif');border: 0px solid;CURSOR:hand;
	}
	.load{
		height:26px;width:28px;background: url('${pageContext.request.contextPath}/Images/Page/pagination_load.gif');border: 0px solid;CURSOR:hand;
		}
	.load_a{
		height:26px;width:28px;background: url('${pageContext.request.contextPath}/Images/Page/pagination_load_a.gif');border: 0px solid;CURSOR:hand;
	}
	</style> 
  </head>
  <body>
    <table width="100%" border="0" cellpadding="1" cellspacing="1" bgcolor="#CCCCCC">
		  <tr style="font-size:12px">
		  <td width="40px"bgcolor="#F5F5F5">
		  <select id=""name="" >
   				<option value="${page.pageSize }" >${page.pageSize }</option>
   		  </select>
   		 </td>
		 <td  width="75px"bgcolor="#F5F5F5">
		  <input type="button" value="" onclick="javascript:pagequery(1);"class="first" onmouseover="this.className='first_a'" onmouseout="this.className='first'"/>
		  <c:if test="${page.page > 1}">
		  <input type="button" value="" onclick="pagequery(${page.page-1 });" class="prev" onmouseover="this.className='prev_a'" onmouseout="this.className='prev'"/>
		  </c:if>
		  <c:if test="${page.page <= 1}">
		   	<input type="button" value=""  class="prev_b" disabled="disabled"/>
		  </c:if>
		  </td>
		 <td  width="150px"bgcolor="#F5F5F5">
		  页<select id="skipPage"name="skipPage" onchange="getPage()">
   					<c:forEach begin="1" end="${page.pageNum }" var="wp">
   						<option value="${wp }"  <c:if test="${page.page==wp}">selected="selected"</c:if>>${wp }</option>
   					</c:forEach>
   				</select> 共${page.pageNum }页&nbsp;
   		</td>
		 <td  width="105px" bgcolor="#F5F5F5">		
		  <c:if test="${page.page < page.pageNum}">
		  
		  <input type="button" value="" onclick="javascript:pagequery(${page.page+1 });" class="next" onmouseover="this.className='next_a'" onmouseout="this.className='next'"/>
		  </c:if>
		  <c:if test="${page.page >= page.pageNum}">
		   <input type="button" value="" onclick="" class="next_b"disabled="disabled"/>
		  </c:if>
		   <input type="button" value="" onclick="pagequery(${page.pageNum });" class="last" onmouseover="this.className='last_a'" onmouseout="this.className='last'"/>      
		   <input type="button" value="" onclick="pagequery('1');" class="load" onmouseover="this.className='load_a'" onmouseout="this.className='load'"/>      
		 </td>
		 
		 <td  bgcolor="#F5F5F5" align="right">
		 显示&nbsp;${(page.page-1)*page.pageSize+1 }&nbsp;-&nbsp;${(page.page)*page.pageSize }&nbsp;共&nbsp;${page.total }&nbsp;条
		  </td>
		  </tr>
	  </table>
  </body>
</html>
