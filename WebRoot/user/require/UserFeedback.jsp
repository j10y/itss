<%@ page language="java" pageEncoding="gbk"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head> 
  <title>��ӭʹ��IT����ϵͳ</title>   
    <%@include file="/includefiles.jsp"%>  	  
  	<script type="text/javascript" src="${pageContext.request.contextPath}/user/event/engineer.js"></script>
  	<script type="text/javascript" src="${pageContext.request.contextPath}/user/event/eventRelationPanel.js"></script>
  	<script type="text/javascript" src="${pageContext.request.contextPath}/user/event/saveSolution.js"></script>
  	<script type="text/javascript">
  		var taskId = '${taskId}';
  	</script>
	<style type="text/css">
		.x-head{ 
			background:url(images/titlelog.png) no-repeat left;
			height:65px;
			background-color: 'blank'
		}
		html, body {
	        font:normal 12px verdana;
	        margin:0;
	        padding:0;
	        border:0 none;
	        overflow:hidden;
	        height:100%;
	    }
		p {
		    margin:5px;
		}
	    .nav {
	        background-image:url(images/other/folder_go.png);
	    }
	    .cls {
	    	font-size:9pt;
	    }
	    .common-text {
	    	font-size:9pt;
	    }
    </style>
</head>
  <head>
    <!-- html-16 ��-2 -->
  </head>
  <body bgcolor="#F7F7F7">
    
    <div align="center"
    style="background-color:#FFFFCC">
	    <table>
		 
		  <tr>
		    <td colspan="2" 
			    bgcolor="#C8C5C4"
				>1���������IT�����������ۣ�</td>
		  </tr>
          <tr>
			<td align="center">
			  �ǳ�����<input type="checkbox"
			             name="hobby"
						 value="travel"/>
			 ���� <input type="checkbox"
			             name="hobby"
						 value="reading"/>
			  һ�� <input type="checkbox"
			             name="hobby"
						 value="music"/>
			  �ǳ������� <input type="checkbox"
			             name="hobby"
						 value="friends"/><br>
			 </td>
		  <tr>
		  <tr>
		    <td colspan="2" 
			    bgcolor="#C8C5C4"
				>
            </td>
		  </tr>
		  <tr>
		    <td colspan="2" 
			    bgcolor="#C8C5C4"
				>����������κ�����ͽ��飬���������:</td>
		  </tr>
		  <tr>
			<td align="center" >
			  <textarea cols="80"
			            rows="5"></textarea>
			</td>
		  </tr>
		   <tr>
		    <td colspan="2" 
			    bgcolor="#C8C5C4"
				>
		
			</td>
		  </tr>
		</table>
	</div>
  </body>
</html>
