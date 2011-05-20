<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">

<HTML xmlns="http://www.w3.org/1999/xhtml">
<HEAD><TITLE>系统主表</TITLE>
<%@include file="/includefiles.jsp"%>
<META HTTP-EQUIV="Content-Type" CONTENT="text/html; charset=GBK"/> 
<META content="" name=Keywords>
<META content="" name=Description>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/CssAdmin.css">
<script language="javascript" src="${pageContext.request.contextPath}/js/Admin.js"></script>
	<SCRIPT language="javascript">
	function saveDispFlag(){
	  var dispFlags="";
	  <c:forEach var="item" items="${requestScope.sysTableQueryColumns}" varStatus="status">
	    var dispFlags = document.getElementById("column_checked${item.id}");//isDisplay${item.id}
	    alert("${item.columnCnName}:"+dispFlags.value);
	    var tempFlag=0;
	   // alert(dispFlags.checked);
	    if(dispFlags.checked==true){ alert("cek");
	     tempFlag = 1;
	    }
	    if(${item.isDisplay}!=tempFlag){
	       alert("${item.columnCnName}:"+${item.isDisplay});
	    }
	  </c:forEach>
	}
	
	function getColumnCnNamesByTableId(){      
		var tableId,i,j;
		var foreignTables = document.getElementById("foreignTables");
		var foreignTableColumns = document.getElementById("foreignTableColumns");
		
		for (i = foreignTableColumns.options.length-1; i>=0  ; --i){ 
			  foreignTableColumns.options[i] = null;  
		}
		tableId = foreignTables.options[foreignTables.selectedIndex].value;
		if(tableId==0){
		 foreignTableColumns.options[0] = new Option('','');
		}
		 foreignTableColumns.options[0] = new Option('',''); 
		j = 1;	
		for (i=0 ;i< tableColumns.length ;i++){
			if (tableId == tableColumns[i][3]){   
				foreignTableColumns.options[j] = new Option(tableColumns[i][2],tableColumns[i][1]); 
				++j;
			}
		}
	}
	
	function openSubWindow(url,title){
		window.open(url,title,
					"scrollbars=yes,resizable=yes,height=400,width=600,status=yes,toolbar=no,menubar=no,location=no,fullscreen=no,left=5,top=5",
					true);
	}
	
	
	
	//-->
	</SCRIPT>
<body>
<TABLE cellSpacing=1 cellPadding=3 width="100%" bgColor=#6298e1 border=0>
  <TBODY>
  <TR>
    <TD noWrap height=24><FONT color=#ffffff><IMG height=18 
      src="${pageContext.request.contextPath}/images/Explain.gif" width=18 align=absMiddle 
      border=0>&nbsp;<STRONG>主表系统查询列表：显示主表不同查询条件的查询</STRONG></FONT></TD></TR>
    <TR>
    <TD noWrap align=middle bgColor=#ebf2f9 height=36>
      <TABLE cellSpacing=0 width="100%" border=0>
        <TBODY>
        <TR>

  
         
          <TD align="center"> 
         
         <a href="${pageContext.request.contextPath}/infoAdmin/sysTableSetting.do?methodCall=list">用户可见字段设置</a>
	    &nbsp;
	     <a href="${pageContext.request.contextPath}/infoAdmin/sysTableQuery.do?methodCall=list&smtId=${smt.id}">系统主表查询设置</a>
	     
         <INPUT name="pageNo" type="hidden" value="${param.pageNo}">
         <INPUT name="methodCall" type="hidden" value="list">
        
         </TD>
        	
          </TR>
          </TBODY>
          </TABLE>
       </TD>
      </TR>
   </TBODY>
 </TABLE>
         
<form  action="${pageContext.request.contextPath}/system/tableQuerySet.do?mainTableId=${systemMainTable.id}" method="post">
	<input type="hidden" name="settingType" value="${settingType}">
	<input type="hidden" name="methodCall" value="saveUserQueryColumn">
	<table id="sortTable" width=100% border=0 align=center cellpadding="2" cellspacing=1>
    <tbody id="sortTbody">
    
     <%--<tr  bgcolor="#FFFFFF">        
    <td "nowrap" align="center" bgcolor="#FFFFFF" colspan="7"> <br>
    <input name="submit" type="submit" class="xinputButton" onclick="" value="保存修改">
     <input name="submit" type="button" class="xinputButton" onclick="window.opener.location.reload();" value="预览">
    </td>
    </tr>--%>
  
<TR>
    <TD noWrap width="20" bgColor=#8db5e9>
    <FONT color=#ffffff><STRONG>ID</STRONG></FONT>
    </TD>
    <TD noWrap bgColor=#8db5e9 >
    <STRONG><FONT color=#ffffff>是否显示</FONT></STRONG>
    </TD>
    <TD noWrap bgColor=#8db5e9>
    <FONT color=#ffffff><STRONG>字段名称</STRONG></FONT>
    </TD>
    <TD noWrap bgColor=#8db5e9>
    <FONT color=#ffffff><STRONG>所属主表称</STRONG></FONT>
    </TD>
    <TD noWrap  bgColor=#8db5e9>
    <FONT color=#ffffff><STRONG>排序</STRONG></FONT>
    </TD>
    
    <TD noWrap bgColor=#8db5e9>
    <a onclick="sortColumns(0);" href="#"><FONT color=#ffffff><STRONG>移到到此处</STRONG></FONT></a>
    </TD>

  
  </TR>
  
<c:forEach  var="item" items="${requestScope.sysTableQueryColumns}" varStatus="status">

<TR onmouseover="this.style.backgroundColor = '#FFFFFF'" style="CURSOR: hand" 
      onmouseout="this.style.backgroundColor = ''" bgColor=#ebf2f9>
    <TD  width="20">${item.id }</TD>
    <TD noWrap>${empty item.isDisplay or item.isDisplay eq 1?'显示':'不显示'}</TD>
    
    <TD noWrap>${item.columnCnName }</TD>
    <TD noWrap>${item.systemMainTable.tableCnName }</TD>
    
    <TD noWrap>
 	<input id="order${item.id}" name="order${item.id}" type="hidden" style="width:30px" value="${item.order}">&nbsp;
	</TD>
    <TD noWrap>移动</TD>
    
  </TR>
    
    


</c:forEach>
          			
     
  <TR>
    <TD noWrap bgColor=#ebf2f9 colSpan=5 align="right">&nbsp;
   
     
    </TD>
    <TD colSpan=2 noWrap bgColor=#ebf2f9>
   
    </TD></TR>

    <TD noWrap bgColor=#d7e4f7 colSpan=10>
    
    
       
    </TD>
   </TR>  			                
               
                  </tbody>
</table>
</form>

<script language="javascript"> 
	
  function func(obj,itemId){ 
     var valueObj = document.getElementById("column_checked"+itemId);
     valueObj.value = ""+itemId+"|"+(obj.checked?1:0); 
  }
  
  function chanageBgColor(checkedTrIndex){ 
     var tr = document.getElementById("tr"+checkedTrIndex);
     var sort_checked = document.getElementById("sort_checked"+checkedTrIndex);
     if(sort_checked.checked==true){
       tr.bgColor="#ababab";
     }else{
       tr.bgColor="#dfe4e8"; //note must uppcase 'bgColor'
     }
  }
  
  function sortColumns(targetColumnId){
     var sorted = false;
     <c:forEach var="item" items="${requestScope.userQueryColumns}" varStatus="status">
     	var sort_checked = document.getElementById("sort_checked${item.id}");
     	if(sort_checked.checked==true){  //loop column checked to move 	  
     	  //alert("src item.id: "+${item.id});
     	  var trSrc = document.getElementById("tr${item.id}");
     	  var orderSrc = document.getElementById("order"+${item.id});
     	  var orderTarget = document.getElementById("order"+targetColumnId); 
     	  if(trSrc!=null){
     	    if(sorted==false){
     	      sorted = true;
     	    }
     	    var trTarget = document.getElementById("tr"+targetColumnId); 
     	    var sortTBody = document.getElementById("sortTbody");
     	    //trTarget.appendChild(trSrc);
     	    //var emptyTr =  document.getElementById("emptyTr");
     	    //alert("emptyTr: "+emptyTr);
     	    //trTarget.insertBefore(emptyTr);
     	    //alert(trTarget.nextSibling.nodeName);
     	    sortTBody.insertBefore(trSrc, trTarget.nextSibling);
     	     
     	    //trTarget.parentNode.appendChild(trSrc);
     	    //reSortAfterColumnMove();
     	    //
     	    //emptyTr.insertBefore(trSrc);
     	    
     	    sort_checked.checked = false;
     	    //trSrc.bgColor="red";
     	    
     	  
     	    
     	  }
     	}
     	
     </c:forEach>
   //  alert("sort over");
     if(sorted){
       reSortAfterColumnMove();
     }
    
  }
  
  function reSortAfterColumnMove(){
    var tbody = document.getElementById("sortTbody");
    var trAll = tbody.childNodes; 
    var index = -1;
    for(i=0; i<trAll.length; i++){
      
      var tr = trAll[i];
      var itemId = tr.attributes['id'].value;
      if(itemId!=null&& itemId!="null" && itemId!=""&& itemId!="tr0"){
         index = index + 1;
         //alert("i: "+i+", order itemId: "+itemId);
         var order = document.getElementById("order"+itemId.substring(2));
         
         order.value=index+1;
        
     }
      
    }
    
  }
  
   
</script>
</body>
</html>
