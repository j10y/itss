<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">

<HTML xmlns="http://www.w3.org/1999/xhtml">
<HEAD><TITLE>服务项流程</TITLE>
<%@include file="/includefiles.jsp"%>
<META HTTP-EQUIV="Content-Type" CONTENT="text/html; charset=GBK"/> 
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache">
<META HTTP-EQUIV="Expires" CONTENT="0">  
<META content="MSHTML 6.00.2900.5583" name=GENERATOR>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/CssAdmin.css">
<script language="javascript" src="${pageContext.request.contextPath}/js/Admin.js"></script>
</HEAD>

<body>

<TABLE cellSpacing=1 cellPadding=3 width="100%" bgColor=#6298e1 border=0>
  <TBODY>
  <TR>
    <TD noWrap height=24><FONT color=#ffffff><IMG height=18 
      src="${pageContext.request.contextPath}/images/Explain.gif" width=18 align=absMiddle 
      border=0>&nbsp;<STRONG>服务项发布管理：查询，添加、修改</STRONG></FONT></TD></TR>
    <TR>
	    <TD noWrap align=middle bgColor=#ebf2f9 height=24>
	    <a href="${pageContext.request.contextPath}/infoAdmin/serviceItemUserTableAction.do?methodCall=addTable&serviceItemId=${serviceItem.id}"" 
	    	target="mainFrame" >[服务项需求主表配置]</a>
	    <a href="${pageContext.request.contextPath}/infoAdmin/serviceItemProcessAction.do?methodCall=list&&serviceItemId=${serviceItemProcess.serviceItem.id}" 
	    	target="mainFrame" >[服务项需求流程配置]</a>
	    </TD>
      </TR>
   </TBODY>
 </TABLE>

<TABLE cellSpacing=1 cellPadding=0 width="100%" border=0>
 <TBODY>
 <TR>
 <TD height=30>系统后台管理&nbsp;-&gt;&nbsp;服务项发布管理&nbsp;-&gt;&nbsp;${serviceItem.name}
  </TD>
  </TR>
  </TBODY>
</TABLE>

<TABLE cellSpacing=1 cellPadding=3 width="100%" bgColor=#6298e1 border=0>
  <FORM name="formDel" action="${pageContext.request.contextPath}/infoAdmin/serviceItemProcessAction.do" method="post">
  <input type="hidden" name="methodCall" value="remove">
  <input type="hidden" name="serviceItemId" value=${serviceItem.id}>
  <input type="hidden" name="pageNo" value="${param.pageNo}">
 <TBODY>
  <TR>
    <TD  width="20" bgColor=#8db5e9>
    <FONT color=#ffffff><STRONG>ID</STRONG></FONT>
    </TD>
    <TD nowrap  bgColor=#8db5e9 >
    <STRONG><FONT color=#ffffff>流程名称</FONT></STRONG>
    </TD>
    <TD nowrap bgColor=#8db5e9 >
    <STRONG><FONT color=#ffffff>流程类型</FONT></STRONG>
    </TD>
    <TD nowrap bgColor=#8db5e9 >
    <STRONG><FONT color=#ffffff>流程申请表单</FONT></STRONG>
    </TD>
     <TD nowrap bgColor=#8db5e9 >
    <STRONG><FONT color=#ffffff>流程入口面板</FONT></STRONG>
    </TD>
    <TD nowrap bgColor=#8db5e9 >
    <STRONG><FONT color=#ffffff>流程入口页面</FONT></STRONG>
    </TD>
    <TD nowrap bgColor=#8db5e9 >
    <STRONG><FONT color=#ffffff>流程启动按钮</FONT></STRONG>
    </TD>
    <TD noWrap  bgColor=#8db5e9>
    <FONT color=#ffffff><STRONG>流程申请协议</STRONG></FONT>
    </TD><TD colspan="2" width=80 bgColor=#8db5e9 align="center"><STRONG>
   <FONT color=#ffffff>操作</FONT></STRONG> 
    <!-- <INPUT class=button id=submitAllSearch style="WIDTH: 16px; HEIGHT: 18px" onclick=CheckAll(this.form) type=button value=全 name=buttonAllSelect> 
	<INPUT class=button id=submitOtherSelect style="WIDTH: 16px; HEIGHT: 18px" onclick=CheckOthers(this.form) type=button value=反 name=buttonOtherSelect> 
    -->
    </TD>
  
  </TR>
  
 <c:forEach  var="item" items="${requestScope.processes}" varStatus="status">   
 <c:if test="${empty param.methodCall ||param.methodCall eq 'list'||param.methodCall eq 'save'||param.methodCall eq 'remove' || param.methodCall eq 'toEditProcess' &&param.id ne item.id ||param.methodCall eq 'toAddProcess'}">
  <TR onmouseover="this.style.backgroundColor = '#FFFFFF'" style="CURSOR: hand" 
      onmouseout="this.style.backgroundColor = ''" bgColor=#ebf2f9>
    <TD   width="20" >${item.id }</TD>
    <td nowrap valign="middle">${item.definitionName}</td>
    <td nowrap   valign="middle">
    
      <c:if test="${item.sidProcessType eq 0}">申请流程</c:if>
       <c:if test="${item.sidProcessType eq 1}">变更流程</c:if>
       <c:if test="${item.sidProcessType eq 2}">删除流程</c:if>
    
	</td>
    <td nowrap valign="middle">
    <a href="${pageContext.request.contextPath}/pageModel/pagePanelManage.do?methodCall=toPagePanelEditForm&id=${item.pagePanel.id}" target="_blank">${item.pagePanel.name}</a>
    
    </td>
     <td nowrap valign="middle">
	<a href="${pageContext.request.contextPath}/pageModel/pagePanelManage.do?methodCall=toPagePanelEditForm&id=${item.pageListPanel.id}" target="_blank">${item.pageListPanel.name}</a>
	</td>
	<td nowrap valign="middle">
	<a href="${pageContext.request.contextPath}/pageModel/pageModelManage.do?methodCall=toPageModelEditForm&id=${item.pageModel.id}" target="_blank">${item.pageModel.name}</a>
	</td>
	<td nowrap valign="middle">${item.buttonName}</td>
     <td nowrap valign="middle">${item.agreement}</td>
     
    <TD width=40 align="center">
    <A onclick='changeAdminFlag("修改")' 
       href="${pageContext.request.contextPath}/infoAdmin/serviceItemProcessAction.do?methodCall=toEditProcess&id=${item.id}&serviceItemId=${serviceItem.id}">
      <FONT color="#330099">修改</FONT></A>
      <!-- <INPUT name="id" type="checkbox" value="${item.id}" style="WIDTH: 13px; HEIGHT: 13px"> -->
      </TD>
   <TD width=40 align="center">
    <A onclick='changeAdminFlag("删除")' 
       href="${pageContext.request.contextPath}/infoAdmin/serviceItemProcessAction.do?methodCall=remove&id=${item.id}&serviceItemId=${serviceItem.id}">
      <FONT color="#330099">删除</FONT></A>
      <!-- <INPUT name="id" type="checkbox" value="${item.id}" style="WIDTH: 13px; HEIGHT: 13px"> -->
      </TD>
    </TR>
 </c:if> 
 
 <c:if test="${!empty param.methodCall && param.methodCall eq 'toEditProcess' && param.id eq item.id }">      
			
      <tr bgColor=#ebf2f9>
     <TD  width="20">${item.id }</TD>
       
        <td nowrap   valign="middle">
          <input name="id" type="hidden"  value="${process.id}" >           
           <!-- <input name="definitionName" style="width:100%;" value="${process.definitionName}" class="textfield"> -->             
          <select name="definitionName" id="definitionName"  class="textfield">
		        <option value="">${process.definitionName}</option>
		        <c:forEach var="item" items="${requestScope.definations}" varStatus="status">
		         <c:if test="${not empty item.virtualDefinitionDesc}">
		          <option value="${item.id}">${item.virtualDefinitionDesc}</option>
		         </c:if>
		       </c:forEach>
		      </select>
          </td>
          <td nowrap   valign="middle">
		      <select name=sidProcessType id="sidProcessType"  class="textfield">
		          <option value=""></option>
		          <option value="0" ${process.sidProcessType eq 0?' selected':'' }>申请流程</option>
		          <option value="1" ${process.sidProcessType eq 1?' selected':'' }>变更流程</option>
		          <option value="2" ${process.sidProcessType eq 2?' selected':'' }>撤销流程</option>
		      </select>
		  </td>
		  
		  <td nowrap   valign="middle"">
		      <select name="pagePanel" id="pagePanel"  class="textfield">
		           <option value=""></option>
		        <c:forEach var="item" items="${requestScope.pagePanels}" varStatus="status">
		          <option value="${item.id}" ${process.pagePanel.id eq item.id?' selected':'' }>${item.name}</option>
		       </c:forEach>
		      </select>
		  </td>
		  
		   <td nowrap   valign="middle"">
		      <select name="pageListPanel" id="pageListPanel"  class="textfield">
		           <option value=""></option>
		        <c:forEach var="item" items="${requestScope.pagePanels}" varStatus="status">
		          <option value="${item.id}" ${process.pageListPanel.id eq item.id?' selected':'' }>${item.name}</option>
		       </c:forEach>
		      </select>
		  </td>
		  <td nowrap   valign="middle"">
		      <select name="pageModel" id="pageModel"  class="textfield">
		           <option value=""></option>
		        <c:forEach var="item" items="${requestScope.pageModels}" varStatus="status">
		          <option value="${item.id}" ${process.pageModel.id eq item.id?' selected':'' }>${item.name}</option>
		       </c:forEach>
		      </select>
		  </td>
		  <td  nowrap valign="middle">
           <input name="buttonName" id="buttonName" type="testField" >             
          </td>
          <td  nowrap valign="middle">
           <input name="agreement" id="paValue" type="hidden" >   
           <input type="button" id="editAgreement" value="编辑协议" class="button">             
          </td>
          <td nowrap height="25" align="center" valign="middle"> 
          <input type="button" name="Submit" value="保存" class="button" onclick="save();">
		<a href="${pageContext.request.contextPath}/infoAdmin/serviceItemProcessAction.do?methodCall=list&serviceItemId=${serviceItem.id}">取消</a></td>
		                <td nowrap height="25" align="center" valign="middle">
		<a href="${pageContext.request.contextPath}/infoAdmin/serviceItemProcessAction.do?methodCall=remove&id=${process.id}&serviceItemId=${serviceItem.id}">删除</a>
		</td>
      </tr>
            
</c:if>
</c:forEach> 

 <c:if test="${!empty param.methodCall && param.methodCall eq 'toAddProcess'  }">      
 <form action="${pageContext.request.contextPath}/infoAdmin/serviceItemProcessAction.do?methodCall=save" method="post">
         <tr bgColor=#ebf2f9>
        <TD  width="20">${item.id }</TD>
          
           <td nowrap  height="25" valign="middle"> 
            <input name="serviceItemId" type="hidden"  value="${serviceItem.id}">
		    <select name="definitionName" id="definitionName" style="width:100%;" class="textfield">
		        <option value="">无</option>
		        <c:forEach var="item" items="${requestScope.definations}" varStatus="status">
		         <c:if test="${not empty item.virtualDefinitionDesc}">
		          <option value="${item.id}"}>${item.virtualDefinitionDesc}</option>
		         </c:if>
		       </c:forEach>
		      </select>
		    </td>
		    <td nowrap   valign="middle">
		      <select name=sidProcessType id="sidProcessType" style="width:100%;" class="textfield">
		          <option value=""></option>
		          <option value="0" ${process.sidProcessType eq 0?' selected':''}>申请流程</option>
		          <option value="1" ${process.sidProcessType eq 1?' selected':'' }>变更流程</option>
		          <option value="2" ${process.sidProcessType eq 2?' selected':'' }>撤销流程</option>
		      </select>
		  </td>
		  <td nowrap   valign="middle"">
		      <select name="pagePanel" id="pagePanel"  class="textfield">
		           <option value="">${process.pagePanel.title}</option>
		        <c:forEach var="item" items="${requestScope.pagePanels}" varStatus="status">
		         <c:if test="${not empty item.title}">
		          <option value="${item.id}" ${process.pagePanel.id eq item.id or siut.pagePanel.id eq item.id ? ' selected':''}>${item.name}</option>
		         </c:if>
		       </c:forEach>
		      </select>
		  </td>     
		  
		  <td nowrap   valign="middle"">
		      <select name="pageListPanel" id="pageListPanel"  class="textfield">
		           <option value=""></option>
		        <c:forEach var="item" items="${requestScope.pagePanels}" varStatus="status">
		          <option value="${item.id}" ${process.pageListPanel.id eq item.id or siut.pageListPanel.id eq item.id ?' selected':'' }>${item.name}</option>
		       </c:forEach>
		      </select>
		  </td>
		   <td nowrap   valign="middle"">
		      <select name="pageModel" id="pageModel"  class="textfield">
		           <option value=""></option>
		        <c:forEach var="item" items="${requestScope.pageModels}" varStatus="status">
		          <option value="${item.id}" ${process.pageModel.id eq item.id ?' selected':'' }>${item.name}</option>
		       </c:forEach>
		      </select>
		  </td>
		  <td  nowrap valign="middle">
           <input name="buttonName" id="buttonName" type="testField" >             
          </td>    
           <td nowrap  height="25" valign="middle"> 
           <input name="agreement" id="paValue" type="hidden" >  
           <input type="button" id="editAgreement" value="编辑协议" class="button">                 
           </td>
           
           <td nowrap height="25" align="center" valign="middle">
           <input type="button" name="Submit" value="保存" class="button" onclick="save();">
		</td>
           <td nowrap height="25" align="center" valign="middle">
	<a href="${pageContext.request.contextPath}/infoAdmin/serviceItemProcessAction.do?methodCall=list&serviceItemId=${serviceItem.id} ">取消</a>
 </td>
   </tr>
  </form>
</c:if>    
   <script>
	  function save(){
	    var xform = document.formDel;
	    xform.methodCall.value="save";
	    xform.submit();
	  }
	  Ext.QuickTips.init();
	  Ext.onReady(function(){
	    var win;
	    var button = Ext.get('editAgreement');
		var agreementValue = '${process.agreement}';
	    button.on('click', function(){
	        if(!win){
	            win = new Ext.Window({
	            	id :'win',
	                applyTo : 'hello-win',
	                layout : 'fit',
	                width : 510,
	                height : 300,
	                closeAction :'hide',
	                plain : true,
	                items : new Ext.Panel({
	           		//applyTo        : 'hello-panel',
	             	deferredRender : false,
	             	border : false,
	             	items:[new Ext.form.HtmlEditor({id:'agreementEditor',value:agreementValue})]//new Ext.form.TextField({id:'aaa',fieldLabel:'aaa'})
	          	}),
	
	         	 buttons: [{
	               	text : '保存',
	               	handler : function(){
	               		var curValue = Ext.getCmp('agreementEditor').getValue();
	               		Ext.getDom("paValue").value=curValue;
	               		win.hide();
	               	}
	          		},{
	             	text : '关闭',
	            	handler : function(){
	                	win.hide();
	             	}
	           	}]
	    	});
	   	}
	 	win.show(button);
	    });
	});
	</script> 
  <script>
    function addProcess(){
      window.location.href="${pageContext.request.contextPath}/infoAdmin/serviceItemProcessAction.do?methodCall=toAddProcess&serviceItemId=${serviceItem.id}";
    }
  </script>
  <TR>
    <TD noWrap bgColor=#ebf2f9 colSpan=7 align="right">&nbsp;
   
   </TD>
    <TD colSpan=2 noWrap bgColor=#ebf2f9>
    <INPUT class=button  onclick="addProcess();" type=button value=增加流程 >&nbsp;
    </TD></TR>

    <TD noWrap bgColor=#d7e4f7 colSpan=10>
    
    </TD>
   </TR>

</TBODY></TABLE></TD></TR></FORM></TBODY></TABLE>
<div id="hello-win" class="x-hidden">
    <div class="x-window-header">申请流程协议</div>
</div>
</body>
</html>
