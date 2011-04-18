<%@page contentType="text/html; charset=GBK"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<html>
<HEAD><TITLE>订单申请列表</TITLE>
<%@include file="/includefiles.jsp"%>


<style type="text/css">
       .col{
	        width: 40%;
	        background-color: #red; 
	        float: left;
	        height: 40px;
	    }
	    .colM{
	        padding-right: 4px;
	        padding-top: 5px;
	        width: 45%;
			text-align: right;
	        background-color:#red; 
	        float:left;
	    }
	    .colC{
	        width: 50%;
	        vertical-align: baseline; 
	        float:left;
	    }
	    
	    .col2{
	        width: 80%;
	        background-color: #red; 
	        float: left;
	        height: 40px;
	        margin-top: 0px;
	        margin-bottom: 20px;
	    }
	    
	    .colM2{
	        padding-right: 4px;
	        padding-top: 5px;
	        width: 22.5%;
			text-align: right;
	        background-color:#red; 
	        float:left;
	    }
	    .colC2{
	        width: 70%;
	        vertical-align: baseline; 
	        float:left;
	    }
	    
    </style>
</head>
  <script type="text/javascript" src="${pageContext.request.contextPath}/js/My97DatePicker/WdatePicker.js"></script>
<script language="javascript">
    function changeSkin(value){
	   		 Ext.util.CSS.swapStyleSheet('window', '${pageContext.request.contextPath}/extEngine/resources/css/' + value + '.css');
	   		 
	}
</script>
  <script language="javascript">
  function openSubWindow(url,title){
		window.open(url,title,
					"scrollbars=yes,resizable=yes,height=600,width=700,status=yes,toolbar=no,menubar=no,location=no,fullscreen=no,left=5,top=5",
					true);
  }
  function submitQuery(nextOrPrevFlag){
     var xform = document.getElementById("queryForm");
     var pageNo = document.getElementById("pageNo"); 
     if(nextOrPrevFlag=="next"){
     	pageNo.value = parseFloat(pageNo.value)+1;
     }else if(nextOrPrevFlag=="prev"){
        pageNo.value = parseFloat(pageNo.value)-1;
     }else{
     	pageNo.value = "";
     }
     //alert(pageNo.value);
     xform.submit();
  
  }
  
    function submitQueryToOutput(){ 
   	 var output = document.getElementById("output");
   	 var uploading = document.getElementById("uploading");
   	 var pageNo = document.getElementById("pageNo"); 
   	 pageNo.value = 1;
   	 if(output.style.display=="block"){
	     output.style.display="none";
	 }else{
	    output.style.display=="block";
	 }
	 if(uploading.style.display=="none"){
	     uploading.style.display="block";
	 }
	 //output.innerText= "";
	 //output.innerText= uploading.innerText;
     var xform = document.getElementById("queryForm");
     xform.action="${pageContext.request.contextPath}/order/orderApply.do?methodCall=export";
     xform.submit();
  
  }
  
  </script>
<body class="BODY" onload="changeSkin('${userInfo.userViewStyle}');">
<div class="x-panel-bbar x-toolbar" style="margin-left:10px;margin-right:10px;">

<TABLE width="100%" id=2 cellSpacing=0 cellPadding=0 width=100% border=0>
   <tr>
    <td "nowrap" >订单信息查询<input type="hidden"  name="methodCall" value="saveExtendColumn"></td>
    <td width="10%"> 
		</td>
	<td width="2%">
	</td>
   <td width="10%">
		<input type="button" class="" value="显示设置" 
		onclick="javascript:openSubWindow('${pageContext.request.contextPath}/system/userTableColumnSet.do?methodCall=listUserTableColumnSettings&settingType=list&mainTableId=202','');">
	</td>
  </tr>
</table>
</div>
<div class="x-panel-tbar x-toolbar" style="margin-left:10px;margin-right:10px;">
<c:set var="actionPath" value="${pageContext.request.contextPath}/order/orderApply.do" scope="page"/>
<c:set var="tableWidth" value="50%" scope="page"/>

<c:if test="${not empty userQueryColumns}">
<table id="queryColumns" width="100%" border=0 align=center cellPadding=0 cellSpacing=0>
<tbody>
    <tr>
    <td   align="left" valign="middle" >
   <form id="queryForm" action="${actionPath}" method="post">
   
   
	<input id="pageNo" name="pageNo" type="hidden" size="4" value="${page.currentPageNo}">
	<input name="currentPage" type="hidden" value="${currentPage }" >
	<input name="methodCall" type="hidden" value="list" >

	<c:forEach  var="item" items="${userQueryColumns}" varStatus="status">
	 <c:set var="sysQueryColumn" value="${item.systemTableQueryColumn}" scope="page"/>
     <c:set var="column" value="${item.systemTableQueryColumn.column}" scope="page"/>
     <c:set var="extSelectType" value="${item.systemTableQueryColumn.column.extSelectType}" scope="page"/>
     <c:set var="columnName" value="${item.systemTableQueryColumn.column.columnName}" scope="page"/>
     <c:set var="propertyName" value="${item.systemTableQueryColumn.column.propertyName}" scope="page"/>
	 <c:set var="columnCnName" value="${item.systemTableQueryColumn.column.columnCnName}" scope="page"/>
	 
	 <div align="left" style="display:table-cell; *display: inline; zoom:1; vertical-align:middle" >
	 <font color="#000000" style="font-size:13px;">${columnCnName}:</font>
	 	<c:if test="${column.systemMainTableColumnType.columnTypeName eq 'text' or column.systemMainTableColumnType.columnTypeName eq 'textArea' }">  <!-- 文本框 -->
	 		
	 		<c:if test="${sysQueryColumn.matchModeStr eq 'MATCH_MODE_BETWEEN'}" var="notBetween"> 
	 		<input name="${propertyName}Begin" type="text" class="x-form-text" style="width:18%";>
	 		<input name="${propertyName}End" type="text" class="x-form-text" style="width:18%";>  
	 		</c:if>
	 		<c:if test="${not notBetween}"> 
	 		<input name="${propertyName}" type="text" value="${param[propertyName]}" class="x-form-text">
	 		</c:if>
	 	</c:if>
	 	<c:if test="${column.systemMainTableColumnType.columnTypeName eq 'hidden' }">  <!-- 隐藏 -->	
	 		<input name="${propertyName}" type="text" value="${param[propertyName]}" class="x-form-text">
	 	</c:if>
	 	<c:if test="${column.systemMainTableColumnType.columnTypeName eq 'checkbox' }" >  <!-- 复选框 -->
	 		 <c:set var="foreignPropNames" scope="page" value="${propertyName}s"/>
	 		<c:if test="${extSelectType !=2}">
                 <c:forEach var="fitem" items="${requestScope[foreignPropNames]}">
                    ${fitem[column.foreignTableValueColumn.propertyName]}:
                    <input name="${column.propertyName}" type="checkbox" value="${fitem[column.foreignTableKeyColumn.propertyName]}" 
                    >
                 </c:forEach>
                </c:if>
              <c:if test="${extSelectType ==2}">
                <c:forEach var="fitem" items="${requestScope[foreignPropNames]}">
                ${fitem.extOptionValue}:
                 <input name="${column.propertyName}" type="checkbox" value="${fitem.id}"
                 >
                </c:forEach>
            </c:if>       
	 	</c:if>
	 	<c:if test="${column.systemMainTableColumnType.columnTypeName eq 'radio'}">
	                  <c:set var="foreignPropNames" scope="page" value="${propertyName}s"/>
	                     <c:if test="${column.extSelectType==0}">
	                      <c:forEach var="item" items="${requestScope[foreignPropNames]}">
	                         ${item[column.foreignTableValueColumn.columnName]}
	                         <input name="${column.propertyName}" type="radio" value="${item[column.foreignTableKeyColumn.propertyName]}" 
	                         >
	                      </c:forEach>
	                     </c:if>
	                     <c:if test="${column.extSelectType==2}">
	                     <c:forEach var="item" items="${requestScope[foreignPropNames]}">
	                     ${item.extOptionValue}
	                      <input name="${ column.propertyName}" type="radio" value="${item.id}"
	                      >
	                     </c:forEach>
	                     </c:if>
	                                                        
		</c:if>
	 	<c:if test="${column.systemMainTableColumnType.columnTypeName eq 'dateText' }">  <!-- 日期控件 -->
	 		<c:if test="${sysQueryColumn.matchModeStr eq 'MATCH_MODE_BETWEEN'}"> 
	 		<input name="${propertyName}Begin" type="text" class="Wdate" readonly onfocus="WdatePicker({isShowWeek:true})">
  
	 		<input name="${propertyName}End" type="text" class="Wdate" readonly onfocus="WdatePicker({isShowWeek:true})"> 
	 		</c:if>
	 		<c:if test="${sysQueryColumn.matchModeStr eq 'MATCH_MODE_EXACT'}"> 
	 		<input name="${propertyName}" type="text" class="Wdate" readonly onfocus="WdatePicker({isShowWeek:true})">
	 		</c:if>
	 	</c:if>
	 	<c:if test="${column.systemMainTableColumnType.columnTypeName eq 'yesNoSelect'}"><!-- 是否列表 -->       
	 		 <select name="${propertyName}">
             <option value=""></option>
             <option value="1" >是</option>
             <option value="0" >否</option>
             </select>     
	 	</c:if>
	 	<c:if test="${column.systemMainTableColumnType.columnTypeName eq 'yesNoRadio'}"><!-- 是否列表 -->       
	         是<input name="${propertyName}" type="radio" value="1"  >
	         否<input name="${propertyName}" type="radio" value="0"  >   
	 	</c:if>
	 	<c:if test="${column.systemMainTableColumnType.columnTypeName eq 'select'}">  <!-- 普通列表 -->
	 	 <c:set var="foreignPropNames" scope="page" value="${propertyName}s"/>   
	 	
	 	
	 	 		<c:if test="${column.foreignTable.level eq '2'}"> 
	 	 		   <c:set var="parentForeignTableNames" value="parent${column.foreignTable.tableName}s" scope="page"/>
	 	 		   <c:if test="${not empty requestScope[parentForeignTableNames]}">
	 		           <select id="parent${propertyName}" name="parent${propertyName}" style="width:25%;"
	 		           		   onchange="changeChild${propertyName}();" class="x-form-select-one">
                       <option value=""></option>                                
					    <c:forEach  var="pItem" items="${requestScope[parentForeignTableNames]}">　 
                        <option value="${pItem['id']}">     
                        ${pItem[column.foreignTableValueColumn.propertyName]}
                        </option> 
     		　　        </c:forEach>
                      </select>
                  </c:if> 
                  <script>
                    sarray${propertyName} = new Array();
					sarray${propertyName}[0] = new Array();
					sarray${propertyName}[0][0]=null;//关联实体id
					sarray${propertyName}[0][1]='';  //关联实体名称属性
					sarray${propertyName}[0][2]=0;  //关联实体父类型属性的id
					
					<c:forEach  var="item" items="${requestScope[foreignPropNames]}" varStatus="status">
						sarray${propertyName}[${status.index}] = new Array();
						sarray${propertyName}[${status.index}][0]=${item.id };
						sarray${propertyName}[${status.index}][1]='${item[column.foreignTableValueColumn.propertyName]}'; 
						<c:set var="parent" value="parent${column.foreignTable.tableName}" scope="page"/> 
						sarray${propertyName}[${status.index}][2]=${item[parent]['id']}; 
					</c:forEach>
					//--------------------
                  </script> 
	 		    </c:if><!-- end level eq '2' -->
	 		    
	 		 <select name="${propertyName}" class="x-form-select-one"><!-- style="width:50%;" -->
             <option value=""></option>
		      <c:if test="${empty column.foreignTable.level or column.foreignTable.level eq '1'}">
		     
		      		<c:if test="${extSelectType !=2}">               
		              <c:forEach  var="fitem" items="${requestScope[foreignPropNames]}">
		               <option value="${fitem.id}"
		               <c:if test="${param[propertyName] eq fitem.id }"> selected</c:if>
		               >${fitem[column.foreignTableValueColumn.propertyName]}</option>
		              </c:forEach>
		            </c:if>
		            <c:if test="${extSelectType ==2}">
		              <c:forEach  var="fitem" items="${requestScope[foreignPropNames]}">
		               <option value="${fitem.id}"
		               <c:if test="${param[propertyName] eq fitem.id }"> selected</c:if>
		               >${fitem.extOptionValue}</option>
		              </c:forEach>
		            </c:if>
		      </c:if>
             </select>     
             <script>
             	function changeChild${propertyName}(){     
					var parent = document.getElementById("parent${propertyName}");
					var child = document.getElementById("${propertyName}");
					
						var parentSelectedId,i,j;
						for (i = child.options.length-1; i>=0  ; --i){
							  child.options[i] = null;  
						}
						
						parentSelectedId = parent.options[parent.selectedIndex].value;
						
						if(parentSelectedId==0){
						 child.options[0] = new Option('','');
						}
						 child.options[0] = new Option('',''); 
						j = 1;	
						
						for (i=0 ;i< sarray${propertyName}.length ;i++){
						   // alert(parentSelectedId+"/"+sarray${propertyName}[i][2]);
							if (parentSelectedId == sarray${propertyName}[i][2]){   
								
								child.options[j] = new Option(sarray${propertyName}[i][1],sarray${propertyName}[i][0]); 
								++j;
							}
						} 
					} 
             </script> 
	 	</c:if>	 
	 </div> 
	</c:forEach>
	<br/>
	<div class="x-panel-bbar x-toolbar" >
	<input type="button" class="x-btn-text-icon" value="检索" onclick="submitQuery('');" >
	<input type="button" value="导出" onclick="submitQueryToOutput();">
	<input type="button" value="查询设置" 
		onclick="javascript:openSubWindow('${pageContext.request.contextPath}/system/tableQuerySet.do?methodCall=listUserQueryColumn&mainTableId=202','');">
	<input type="button" value="增加" 
		onclick="javascript:location.href='${pageContext.request.contextPath}/order/orderApply.do?methodCall=toForm'">
	</div>
	</form>
</td>
 </tr> 
    </tbody>
</table>
</c:if>
</div>
<div class="x-panel-body" style="margin-left:10px;margin-right:10px;">
<div id="output" align="center" style="font-size:13px;display:block;" >
 <c:if test="${not empty fileName}" var="isEmptyFileName">
 导出成功，请点击文件名下载：
 <a href="${pageContext.request.contextPath}/output/order/${fileName}" target="_plank">
  <font color="#006697">${fileName}</font>
 </a><br>
 </c:if>
</div>

<div id="uploading" align="center" style="font-size:13px;display:none;">
  正在导出Excel文件，请稍候&nbsp;
</div>


<TABLE width="100%" border=0 align=center cellPadding=0 cellSpacing=0>
  <TBODY>
    <TR>
      <TD>
        <table width=100% border=0 align=center cellpadding="2" cellspacing=1>
                  <tbody>
                    <tr class="x-grid-panel x-panel-mc x-panel-body"><!-- #ffcc99 -->
                    
               <c:forEach  var="item" items="${requestScope.columnsVisible}" varStatus="status">
                   <td  nowrap height="25"  valign="middle" class="x-grid3-header">
                  		
                   		${item.column.columnCnName}
                   		
                   </td>
              </c:forEach>
                    <td nowrap height="25" align="center" valign="middle" class="x-grid3-header">
                      修改 </td>
                      <td nowrap height="25" align="center" valign="middle" class="x-grid3-header">
                      删除</td>
                    </tr> 
                  <c:forEach  var="item" items="${list}" varStatus="status">
	

                    <tr class="x-grid-panel x-panel-mc x-panel-body"><!-- #FEFAFA -->
                      
                      <c:forEach  var="column" items="${requestScope.columnsVisible}" varStatus="status">	
                                        
                        <td nowrap valign="middle" class="x-grid3">
                          <c:set var="columnTypeName" value="${column.column.systemMainTableColumnType.columnTypeName}" scope="page"/>
                          <c:if test="${columnTypeName eq 'hidden'}">       
                            ${item.id}
                          </c:if>
                          <c:if test="${columnTypeName eq 'text' or columnTypeName eq 'dateText'or columnTypeName eq 'textArea'}">       
                            ${item[column.column.propertyName]}
                          </c:if>       
                           <c:if test="${columnTypeName eq 'checkbox'}"> 
                            ${item[column.column.propertyName]}
                          </c:if>
                          <c:if test="${columnTypeName eq 'radio'}">
                           ${item[column.column.propertyName]}  
                          </c:if>
                          <c:if test="${columnTypeName eq 'select'}">
                          ${item[column.column.propertyName]}  
                          </c:if>
                          <c:if test="${columnTypeName eq 'yesNoSelect'}">
                            ${item[column.column.propertyName]}
                          </c:if>
                          <c:if test="${columnTypeName eq 'yesNoRadio'}">
                            ${item[column.column.propertyName]}
                          </c:if>
                        </td>                 
                     </c:forEach>
                      <td nowrap height="25" align="center" valign="middle" class="x-grid3">
                          <a href="${actionName}?methodCall=toForm&id=${item.id}">修改</a>
                      </td>
                      <td nowrap height="25" align="center" valign="middle" class="x-grid3">
						<a href="${actionName}?methodCall=remove&id=${item.id}">删除</a>
					 </td>
                    </tr>

</c:forEach>
         </tbody>
                </table></TD>
            </TR>
          </TBODY>
      </TABLE></TD>
    </TR>
  </TBODY>
</TABLE>
</div>

<div class="x-tab-panel-footer" style="margin-left:10px;margin-right:10px;">
<font color="#000000" style="font-size:13px;"> 
共${page.totalCount}条记录,共${page.totalPageCount}页,当前第${page.currentPageNo}页<c:if test="${page.currentPageNo gt 1}">,<a href="javascript:submitQuery('prev');">上一页</a></c:if><c:if test="${page.currentPageNo lt page.totalPageCount}">,<a href="javascript:submitQuery('next');" >下一页</a>
</c:if></font>
<div>
</body>
</html>