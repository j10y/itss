<%@ page contentType="text/html; charset=GBK"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>


<html>
<head>
<title>简易订单申请表单页面</title>
<%@include file="/includefiles.jsp"%>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/extEngine/resources/css/ext-all.css" />
<script type="text/javascript" src="${pageContext.request.contextPath}/js/My97DatePicker/WdatePicker.js"></script>


    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/editable-column-tree.css" />
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/Ext.ux.UploadDialog.css" />

    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/b2b-grid.css" />  
    <script type="text/javascript" src="${pageContext.request.contextPath}/extEngine/adapter/ext/ext-base.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/extEngine/ext-all.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/ext-lang-zh_CN-GBK.js"></script>
   
  	
<script language="javascript">
function openSubWindow(url,title){
window.open(url,title,
			"scrollbars=yes,resizable=yes,height=600,width=700,status=yes,toolbar=no,menubar=no,location=no,fullscreen=no,left=5,top=5",
			true);
}

function changeSkin(value){
	   		 Ext.util.CSS.swapStyleSheet('window', '../../extEngine/resources/css/' + value + '.css');
	}
	function updateTab(id,title, url) {
    	var tab = mainPanel.getItem(id);
  		if(tab){
   			mainPanel.remove(tab);
   		}
    	tab = addTab(id,title,url);
   		mainPanel.setActiveTab(tab);
    }
	
	Ext.BLANK_IMAGE_URL = '../../extEngine/resources/images/default/s.gif';
	Ext.onReady(function(){	
	   Ext.QuickTips.init();

	   Ext.state.Manager.setProvider(new Ext.state.CookieProvider()); 
	   	   
	  
	});
	
	Ext.override(Ext.grid.GridView, {
    templates: {
        cell: new Ext.Template(
               '<td class="x-grid3-col x-grid3-cell x-grid3-td-{id} {css}" style="{style}" tabIndex="0" {cellAttr}>',
               '<div class="x-grid3-cell-inner x-grid3-col-{id}" {attr}>{value}</div>',
               "</td>"
           )
    }
	});
	
</script>
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
<body onload="changeSkin('${userInfo.userViewStyle}');">

<c:set var="actionPath" value="${pageContext.request.contextPath}/order/orderApply.do" scope="page" />
	
<div class="x-panel-header" style="margin-left:10px;margin-right:10px;">
订单申请信息
</div>

<div class="x-panel-body" style="margin-left:10px;margin-right:10px;">

 <div class="x-panel-mc">
   
   <table width="100%" align="left" class="x-panel-mc" >
   
   <form class="x-form-field" action="${actionPath}" method="post">

      <tr class="x-grid3-header">
         <td class="x-window-body" style="font-size:9pt; width:100%;">
           <span style="float:right;align:right;" >
               <a href="#" 
                  onclick="javascript:openSubWindow('${pageContext.request.contextPath}/system/userTableColumnSet.do?methodCall=listUserTableColumnSettings&settingType=input&mainTableId=202','');"
               >输入项目定制</a>
           </span>
         </td>
      </tr>      
   
       <tr class="x-grid3-header">
         <td class="x-window-body" style="font-size:9pt; width:100%;">
           <c:forEach var="column" items="${requestScope.userColumns}" varStatus="status"> 
            
            
			<c:set var="columnTypeName" scope="page" value="${column.column.systemMainTableColumnType.columnTypeName}"/>
			<c:set var="item" value="${column.column}" scope="page"/>				
			<c:set var="extType" value="${column.column.validateType.validateTypeName}" scope="page" />
			<c:set var="isMustInputExt" value="${column.column.isMustInput}" scope="page" />
			<c:set var="propertyNames" value="${column.column.propertyName}s" scope="page"/>
			<c:set var="propertyName" value="${column.column.propertyName}" scope="page"/>
			     
			   
		    
			   <c:if test="${columnTypeName eq 'hidden'}">
				  <div class="col" style="display: none;">
				      <div class="colM" >
		                &nbsp;
		              </div>
		              <div class="colC" >
				        <input name="${column.column.propertyName}" type="hidden" value="${requestScope.selectTypeMap[propertyName]}"
			    	           class="x-form-text">
				      </div>
				  </div>
			   </c:if>
			   
			   <c:if test="${columnTypeName eq 'text'}">
			      <div class="col">
				      <div class="colM" >
		                ${column.columnCnName}：
		              </div>
		              <div class="colC" >
				        <input type="text" name="${column.column.propertyName}" value="${requestScope.selectTypeMap[propertyName]}" 
				               class="x-form-text">
				      </div>
				  </div>
	         	  
			   </c:if>
			   
			   <c:if test="${columnTypeName eq 'textArea'}">
			      <c:if test="${status.index%2 eq 0}">
				      <div class="col1">
					      <div class="colM" >
			                
			              </div>
			              <div class="colC" >
					         	
					      </div>
					  </div>
				  </c:if>
			      <div class="col2">
				      <div class="colM2" >
		                ${column.columnCnName}：
		              </div>
				      <div class="colC2" >   
				        <TEXTAREA name="${column.column.propertyName}" cols="67" rows="3" 
											 	 style="background-color:#ffffff;" type="_moz">${requestScope.selectTypeMap[propertyName]}</TEXTAREA>
				      </div>
				  </div>
										
			   </c:if>
									
									
			   <c:if test="${columnTypeName eq 'dateText'}">
			      <div class="col">
				      <div class="colM" >
		                ${column.columnCnName}：
		              </div>
		              <div class="colC" >
				        <input type="text" name="${column.column.propertyName}" value="${requestScope.selectTypeMap[propertyName]}" 
				               class="x-form-text" readonly onfocus="WdatePicker({isShowWeek:true})">
				      </div>
				  </div>
				  
			  </c:if>
			  
			  <c:if test="${columnTypeName eq 'yesNoSelect'}">
			      <div class="col">
				      <div class="colM" >
		                ${column.columnCnName}：
		              </div>
		              <div class="colC" >
				         <c:set var="propertyNames" value="${column.column.propertyName}s" scope="page"/>
						 <select name="${column.column.propertyName}"  class="x-form-text" style="width:146px;" >
		                      <option value="">
		                      </option>
		                      <c:forEach var="item" items="${requestScope.selectTypeMap[propertyNames]}">
			                      <option value="${item.key}"
			                       <c:if test="${requestScope.selectTypeMap[propertyName] eq item.key}">selected</c:if>
			                      >
			                      ${item.value}
			                      </option>
		                      </c:forEach>
			             </select>
				      </div>
				  </div>
				  
					
			  </c:if>
			  
			  <c:if test="${columnTypeName eq 'yesNoRadio'}">
			  
				  	<div class="col">
					      <div class="colM" >
			                ${column.columnCnName}：
			              </div>
			              <div class="colC" >
					         <c:set var="propertyNames" value="${column.column.propertyName}s" scope="page"/>
		                		<c:forEach var="item" items="${requestScope.selectTypeMap[propertyNames]}">
					                ${item.value}
					                <input name="${column.column.propertyName}" type="radio" value="${item.key}" 
					                <c:if test="${requestScope.selectTypeMap[propertyName] eq item.key}">checked</c:if>
					                >   
				                </c:forEach>
					      </div>
					  </div>
				  
						
			  </c:if>
									
			
			  <c:if test="${columnTypeName eq 'radio'}">
			      <div class="col">
					      <div class="colM" >
			                ${column.columnCnName}：
			              </div>
			              <div class="colC" >
					         	<c:if test="${column.column.extSelectType==0}">
				                    <c:forEach var="item" items="${requestScope.selectTypeMap[propertyNames]}">
				                       ${item[column.column.foreignTableValueColumn.columnName]}
				                       <input name="${column.column.propertyName}" type="radio" value="${item[column.column.foreignTableKeyColumn.propertyName]}" 
				                       <c:if test="${requestScope.selectTypeMap[propertyName] eq item[column.column.foreignTableKeyColumn.propertyName]}">checked</c:if>
				                       >
				                    </c:forEach>
			                   </c:if>
			                   <c:if test="${column.column.extSelectType==2}">
				                   <c:forEach var="item" items="${requestScope.selectTypeMap[propertyNames]}">
				                   ${item.extOptionValue}
				                    <input name="${column.column.propertyName}" type="radio" value="${item.id}"
				                    <c:if test="${item.id eq requestScope.selectTypeMap[propertyName]}">checked</c:if>
				                    >
				                   </c:forEach>
			                   </c:if>    
					      </div>
					  </div>
					                         
			 </c:if>
			 
			 
			 <c:if test="${columnTypeName eq 'checkbox'}">
			 
			 		<div class="col">
				      <div class="colM" >
		                ${column.columnCnName}：
		              </div>
		              <div class="colC" >
				           <c:if test="${column.column.extSelectType==0}">
		                      <c:forEach var="item" items="${requestScope.selectTypeMap[propertyNames]}">
		                         ${item[column.column.foreignTableValueColumn.propertyName]}:
		                         <input name="${column.column.propertyName}" type="checkbox" value="${item[column.column.foreignTableKeyColumn.propertyName]}" 
		                         <c:forEach var="its" items="${requestScope.selectTypeMap[propertyName]}">
		                         <c:if test="${its eq item[column.column.foreignTableKeyColumn.propertyName]}">checked</c:if>
		                         </c:forEach>
		                         >
		                      </c:forEach>
	                      </c:if>
	                      <c:if test="${column.column.extSelectType==2}">
	                      <c:forEach var="item" items="${requestScope.selectTypeMap[propertyNames]}">
	                      ${item.extOptionValue}
	                       <input name="${column.column.propertyName}" type="checkbox" value="${item.id}"
		                      <c:forEach var="its" items="${requestScope.selectTypeMap[propertyName] }">
		                      <c:if test="${item.id eq its}">checked</c:if>
		                      </c:forEach>
		                      >
	                      </c:forEach>
	                      </c:if>          
				      </div>
				  </div>                          
			</c:if>
															
              <c:if test="${columnTypeName eq 'select'}">
			  	  
			      <div class="col">
				      <div class="colM" >
		                ${column.columnCnName}：
		              </div>
		              <div class="colC" >
				         <select name="${column.column.propertyName}"  class="x-form-text" style="width:146px;">
		                      <option value="">
		                      </option>
		                      <c:if test="${column.column.extSelectType!=2}">
		                      <c:forEach var="item" items="${requestScope.selectTypeMap[propertyNames]}">
		                     
			                      <option value="${item[column.column.foreignTableKeyColumn.propertyName]}"
			                      <c:if test="${item[column.column.foreignTableKeyColumn.propertyName] eq requestScope.selectTypeMap[propertyName]}">selected</c:if>
			                      >
			                      ${item[column.column.foreignTableValueColumn.propertyName]}
			                      </option>
		                      </c:forEach>
		                      </c:if> 
		                      <c:if test="${column.column.extSelectType==2}">
		                      <c:forEach var="item" items="${requestScope.selectTypeMap[propertyNames]}">
			                      <option value="${item.id}" 
			                      <c:if test="${item.id eq requestScope.selectTypeMap[propertyName]}">selected</c:if>
			                      >
			                      ${item.extOptionValue}
			                      </option>
		                      </c:forEach>
		                      </c:if>                               
							    
			              </select>
				      </div>
				  </div>
			    
				  
			  </c:if>	
             
             
             
				  
				  
             </c:forEach>   
             
             
              <div class="col2">
			      <div class="colM2" >
	              
	              </div>
			      <div class="colC2" >   
			          <input name="methodCall" type="hidden" value="save">
			          <INPUT type="submit" class="x-form-btns" value="保 存">
			      </div>
			  </div>
			  	
           </td>
          
       </tr>
      
       </form>
   </table>
                    
    
 </div>
</div>
		
		

	

	
	</td>
</tr>
</table>
		
	</body>
</html>
