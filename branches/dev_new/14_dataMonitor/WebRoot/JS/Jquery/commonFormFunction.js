/***检查表单所有元素***/
function checkForm(formName)
{
    var oForm=document.forms[formName];
 	var eles = oForm.elements;
    //遍历所有表元素
		for(var i=0;i<eles.length;i++)
	    {	//验证密码
	    	if(eles[i].attributes['valueSePass']==undefined){
	    		
	    	}else{
	    	var valueSePass=eles[i].attributes['valueSePass'].nodeValue;
		    	if(valueSePass=="true"){
		    			var value=eles[i].value;
		    			var name=eles[i].name;
		    			var valuese=document.getElementById(name+"_se").value;
		    			if(value==valuese){
		    			
		    			}else{
		    				alert("两次密码不一致，请从再次确认！");
		    				return false;
		    			}
		    			
		    	}
	    	}
	    	var mustInput="";
			    if(eles[i].attributes['mustInput']==undefined){
			    	mustInput=false;
			    }else{
			    	mustInput=eles[i].attributes['mustInput'].nodeValue;
			    }  
			    if(mustInput!=null && mustInput=="true"){
			    	if(trim(eles[i].value)==""){
			     		if(eles[i].objName!=null){
			      			//alert(eles[i].objName+"不可以为空");
			      			alert("该选项不可以为空");
			     		}
			     		else{
			      			//alert(eles[i].name+"该选项为必输字段");
			      			alert("带*的必填选项不能为空");
			     		}
			     		eles[i].focus();         
			     		return false;   
			    	}
			   	}
		}	   	
 	return true;
}

function clearForm(formName)
{
    var oForm=document.forms[formName];
    var eles = oForm.elements;
    //遍历所有表元素
	 for(var i=0;i<eles.length;i++)
	    {
	    if(eles[i].attributes['isclear']!=undefined){
		     var sType=eles[i].attributes['isclear'].nodeValue;
			 if(sType){
				eles[i].value="";
			 }
		}	 
    }
}
//for jquery
function getformParms(formName){
	var oForm=document.forms[formName];
	var str="";
    var eles = oForm.elements;
    //遍历所有表元素
 	for(var i=0;i<eles.length;i++)
    {
		 if(eles[i].attributes['ispost']!=undefined){
	    	 var sType=eles[i].attributes['ispost'].nodeValue;
			 if(sType){
			 	var val=eles[i].value;
			 	val=val.replace(/\r/g,'');
			 	val=val.replace(/\n/g,'<br/>');
			 	val=val.replace(/\'/g,'&apos;');
			 	val=val.replace(/\"/g,'&quot;');
				str+=eles[i].name+":'"+val+"',";
			 }
		 }
    }
    if(str.length>0){
    	str=str.substring(0,str.length-1);
    }
    return str;
}
//for jsp
function getformPaemsj(formName){
	var oForm=document.forms[formName];
	var str="";
    var eles = oForm.elements;
    //遍历所有表元素
 	for(var i=0;i<eles.length;i++)
    {	
    	 if(eles[i].attributes['ispost']!=undefined){
	    	 var sType=eles[i].attributes['ispost'].nodeValue;
			 if(sType){
				str+=eles[i].name+"="+eles[i].value+"&";
			 }
    	 }
    }
    if(str.length>0){
    	str=str.substring(0,str.length-1);
    }   
    return str;
}
function validData(id){
   	if(document.getElementById(id).attributes['valueType']==undefined){
   		
   	}else{
   		var ss=document.getElementById(id).attributes['valueType'].nodeValue;
   		
   		var ele=document.getElementById(id);
   		if(ss=='0'){
   		}else if(ss=='1'){
   			if(ele.value.length>0){
   				if(!isChn(ele.value)){
   					alert('该字段必须为中文类型!');
	   				//$.messager.alert('','该字段必须为中文类型!','warning',function(){
						ele.focus();
					//});
   					return false;
   				}
   			}
   		}else if(ss=='2'){
   			var elevalue=document.getElementById(id).value;
   			if(!isNumber(elevalue)){
   				alert('该字段必须为数字类型!');
   				//$.messager.alert('','该字段必须为数字类型!','warning',function(){
	   				ele.focus();
   				//});
   				return false;
   			}
   		}else if(ss=='3'){
   			if(ele.value.length>0){
   				if(!isEnlish(ele.value)){
   					alert('该字段必须为英文字母类型!');
	   				//$.messager.alert('','该字段必须为英文字母类型!','warning',function(){
						ele.focus();
					//});
   					return false;
   				}
   			}
   		}else if(ss=='4'){
	   		 if(ele.value.length>0&&!isEmail(ele.value)){
	   		 	 alert('请输入有效邮箱!');
				 //$.messager.alert('','请输入有效邮箱!','warning',function(){
					ele.focus();
				// });
				 return false;
			  }
   		}else if(ss=='5'){
   			var eles=ele.value.split(",");
   			for(var i=0;i<eles.length;i++){
	   			if(eles[i].length>0){
	   				if(!checkip(eles[i])){
	   					alert('IP格式不正确!');
	   					//$.messager.alert('','IP格式不正确!','warning',function(){
							ele.focus();
					 	//});
	   					return false;
	   				}
	   			}
   			}
   			
   		}else if(ss=='6'){
   			if(ele.value.length>0){
   				var teflag=false;
   				if(!isTel(ele.value)){
   				}else{
   					teflag=true;
   				}
   				if(!isMobi(ele.value)){
   				}else{
   					teflag=true;
   				}
   				if(teflag==false){
   					alert('电话号码格式不正确!');
	   				//$.messager.alert('','电话号码格式不正确!','warning',function(){
		   				ele.focus();
	   				//});
   					return false;
   				}
   			}
   		}else if(ss=='7'){
   			if(ele.value.length>0){
   				if(!check_mac(ele.value)){
   					alert('MAC地址格式不正确!');
   					//$.messager.alert('','MAC地址格式不正确!','warning',function(){
						ele.focus();
				 	//});
   					return false;
   				}
   			}
   		}
  	}
  	if(document.getElementById(id).attributes['isunique']==undefined){
   		
   	}else{
   		var ss=document.getElementById(id).attributes['isunique'].nodeValue;
   		if(ss=='1'){
   			var ele=document.getElementById(id).value;
   			var tablename=$("#tableRealName").val();
   			var keyColumnName=$("#keyColumnName").val();
   			var realtableid=$("#realtableid").val();
   			var url=GlobalUtil.weburl+"/comquery_checkData.action";
   			var params={'tablename':tablename,'value':ele,'columnName':id,'keyColumnName':keyColumnName,'realtableid':realtableid};
			$.post(
					url, //服务器要接受的url
					params, //传递的参数 
					function(json){
					if(json=="1"){
						alert("该值已存在，不能重复写入！");
						document.getElementById(id).focus();
						//$.messager.alert('','该值已存在，不能重复写入！','warning',function(){
						//	document.getElementById(id).focus();
				 		//});
						return false
					}else if(json=="0"){
						
					}else{
						alert("该值已存在，并超过"+json+"条，不能重复写入！");
						document.getElementById(id).focus();
						//$.messager.alert('','该值已存在，并超过'+json+'条，不能重复写入！','warning',function(){
						//	document.getElementById(id).focus();
				 		//});
						return false
					}
				});
   		}
  	}
}
function validString(ele)
{
 if(ele.stringLen!=null && !isNaN(ele.stringLen))
 {
  var value=new String(ele.value);
  if(value.length>parseInt(ele.stringLen))
  {
   alert("您输入的"+convertNullToSpace(ele.objName)+"最大长度为"+ele.stringLen);
   ele.focus();     
   return false;
  }
 }
 return true;
}

/***检查是否为数字***/
function checkNumber()
{
 var ele=event.srcElement;
 if(ele.valueType!=null)
 {
  if(ele.mustInput!=null && ele.mustInput=="true")
  {
   if(trim(ele.value)=="")
   {
    if(ele.objName!=null)
    {
     alert(ele.objName+"不可以为空");
    }
    else
    {
     alert("该文本框为必输字段");
    }
    ele.focus();     
    return false;   
   }
  } 
  if(ele.valueType=="int")
   checkInt(ele);
  else if(ele.valueType=="float")
   checkFloat(ele);
  else
   ;
 }
 return true;
} 


/***检查是否为整数***/
function checkInt(ele)
{
 if(!isInt(ele.value))
 {
  alert(ele.objName+"请输入有效整数");
  ele.focus();
  return false;
 }
 else
 {
  if(ele.maxInput!=null && !isNaN(ele.maxInput))
   if(parseInt(ele.maxInput)<parseInt(ele.value))
   {
     alert("您输入的"+ convertNullToSpace(ele.objName)+"值应该小于"+ele.maxInput);         
    ele.focus();
    return false;
   }      
  if(ele.minInput!=null && !isNaN(ele.minInput))
   if(parseInt(ele.minInput)>parseInt(ele.value))
   {
    alert("您输入的"+ convertNullToSpace(ele.objName)+"值应该大于"+ele.minInput);
    ele.focus();
    return false;
   }   
 }
 return true;
} 

/***检查是否为小数***/
function checkFloat(ele)
{
    if(isNaN(ele.value))
    {
  alert(ele.objName+"请输入有效数字");
  ele.focus();
  return false;
    }
 else
 {
        if(ele.decimalLen!=null && !checkDecimal(ele.value,ele.decimalLen))
     {
   alert("您输入的"+convertNullToSpace(ele.objName)+"值小数位最多为"+ele.decimalLen);
   ele.focus();     
   return false;
        } 
  if(ele.maxInput!=null && !isNaN(ele.maxInput))
   if(parseInt(ele.maxInput)<parseInt(ele.value))
   {
     alert("您输入的"+ convertNullToSpace(ele.objName)+"值应该小于等于"+ele.maxInput);           
    ele.focus();
    return false;
   }      
  if(ele.minInput!=null && !isNaN(ele.minInput))
   if(parseInt(ele.minInput)>parseInt(ele.value))
   {
    alert("您输入的"+ convertNullToSpace(ele.objName)+"值应该大于等于"+ele.minInput);
    ele.focus();
    return false;
   }   
 }
 return true;
} 

/***检查是否为字符串***/
function checkString()
{
 var ele=event.srcElement;
 if(ele.valueType!=null)
 {
  if(ele.mustInput!=null && ele.mustInput=="true")
  {
   if(trim(ele.value)=="")
   {
    if(ele.objName!=null)
    {
     alert(ele.objName+"不可以为空");
    }
    else
    {
     alert("该文本框为必输字段");
    }
    ele.focus();     
    return false;   
   }
  } 
  if(ele.stringLen!=null && !isNaN(ele.stringLen))
  {
   var value=new String(ele.value);
   if(value.length>parseInt(ele.stringLen))
   {
    alert("您输入的"+convertNullToSpace(ele.objName)+"最大长度为"+ele.stringLen);
    ele.focus();     
    return false;
   }
  }
 }
 return true;
}
/***检查是否为日期格式***/
function checkDate()
{
 var ele=event.srcElement;
 if(ele.valueType!=null)
 {
  if(ele.mustInput!=null && ele.mustInput=="true")
  {
   if(trim(ele.value)=="")
   {
    if(ele.objName!=null)
    {
     alert(ele.objName+"不可以为空");
    }
    else
    {
     alert("该文本框为必输字段");
    }
    ele.focus();     
    return false;   
   }
  } 
  if(!isDate(ele.value))
  {
   alert(ele.objName+"请输入有效日期(yyyy-mm-dd)");
   ele.focus();
   return false;
  }
 }
 return true;
} 

/***检查是否为电子邮箱***/
function checkEmail()
{
 var ele=event.srcElement;
 if(ele.valueType!=null)
 {
  if(ele.mustInput!=null && ele.mustInput=="true")
  {
   if(trim(ele.value)=="")
   {
    if(ele.objName!=null)
    {
     alert(ele.objName+"不可以为空");
    }
    else
    {
     alert("该文本框为必输字段");
    }
    ele.focus();     
    return false;   
   }
  } 
  if(!isEmail(ele.value))
  {
   alert(ele.objName+"请输入有效邮箱");
   ele.focus();
   return false;
  }
 }
 return true;
}
/***验证是否为整数***/
function validInt(ele)
{
 if(!isInt(ele.value))
 {
  alert(ele.objName+"必须是有效整数");
  ele.focus();
  return false;
 }
 else
 {
  if(ele.maxInput!=null && !isNaN(ele.maxInput))
   if(parseInt(ele.maxInput)<parseInt(ele.value))
   {
     alert("您输入的"+ convertNullToSpace(ele.objName)+"值应该小于"+ele.maxInput);         
    ele.focus();
    return false;
   }      
  if(ele.minInput!=null && !isNaN(ele.minInput))
   if(parseInt(ele.minInput)>parseInt(ele.value))
   {
    alert("您输入的"+ convertNullToSpace(ele.objName)+"值应该大于"+ele.minInput);
    ele.focus();
    return false;
   }   
 }
 return true;
} 

/***验证是否为小数***/
function validFloat(ele)
{
    if(isNaN(ele.value))
    {
  alert(ele.objName+"必须是有效数字");
  ele.focus();
  return false;
    }
 else
 {
        if(ele.decimalLen!=null && !checkDecimal(ele.value,ele.decimalLen))
     {
   alert("您输入的"+convertNullToSpace(ele.objName)+"值小数位最多为"+ele.decimalLen);
   ele.focus();     
   return false;
        } 
  if(ele.maxInput!=null && !isNaN(ele.maxInput))
   if(parseInt(ele.maxInput)<parseInt(ele.value))
   {
     alert("您输入的"+ convertNullToSpace(ele.objName)+"值应该小于"+ele.maxInput);           
    ele.focus();
    return false;
   }      
  if(ele.minInput!=null && !isNaN(ele.minInput))
   if(parseInt(ele.minInput)>parseInt(ele.value))
   {
    alert("您输入的"+ convertNullToSpace(ele.objName)+"值应该大于"+ele.minInput);
    ele.focus();
    return false;
   }   
 }
 return true;
} 

/***验证是否为字符串***/
function validString(ele)
{
 if(ele.stringLen!=null && !isNaN(ele.stringLen))
 {
  var value=new String(ele.value);
  if(value.length>parseInt(ele.stringLen))
  {
   alert("您输入的"+convertNullToSpace(ele.objName)+"最大长度为"+ele.stringLen);
   ele.focus();     
   return false;
  }
 }
 return true;
}
/***验证是否为日期格式***/
function validDate(ele)
{
 if(!isDate(ele.value)&&ele.value!="")
 {
  alert("请输入有效日期(yyyy-mm-dd)");
  ele.focus();
  return false;
    }
 return true;
} 

/***验证是否为电子邮箱***/
function validEmail(ele)
{
 if(!isEmail(ele.value))
 {
  alert("请输入有效邮箱");
  ele.focus();
  return false;
    }
 return true;
}
/***验证单选按钮是否需要选择***/
function validRadio(ele)
{
 //var rads = document.getElementsByName(ele.name);
    eval("var rads="+name+"."+ele.name);
 var selectCount=0;
 for(var i=0;i<rads.length;i++)
    {
  if(rads[i].checked)
        {
   selectCount++;
        }
    }
 
 if(ele.mustSelect!=null && ele.mustSelect)
 {
  if(selectCount==0)
  {
   alert("请选择"+convertNullToSpace(ele.objName));
   ele.focus();     
   return false;
  }
 }
 return true;
}
/***验证复选按钮是否需要选择***/
function validBox(ele)
{
 //var rads = document.getElementsByName(ele.name);
    eval("var chks="+name+"."+ele.name);
 var selectCount=0;
 for(var i=0;i<chks.length;i++)
    {
  if(chks[i].checked)
        {
   selectCount++;
        }
    }
 if(ele.minSelect!=null && !isNaN(ele.minSelect))
 {
  if(selectCount<parseInt(ele.minSelect))
  {
   alert(convertNullToSpace(ele.objName)+"至少选择"+ele.minSelect+"项");
   ele.focus();     
   return false;
  }
 }
 if(ele.maxSelect!=null && !isNaN(ele.maxSelect))
 {
  if(selectCount>parseInt(ele.maxSelect))
  {
   alert(convertNullToSpace(ele.objName)+"至多选择"+ele.maxSelect+"项");
   ele.focus();     
   return false;
  }
 }
 return true;
}
/***验证下拉列表框是否需要选择***/
function validSelect(ele)
{
 //var rads = document.getElementsByName(ele.name);
 if(ele.mustSelect!=null && ele.mustSelect)
 {
  if(ele.selectedIndex==0)
  {
   alert("请选择"+convertNullToSpace(ele.objName));
   ele.focus();     
   return false;
  }
 }
 return true;
}
/***验证列表框的选择项数***/
function validList(ele)
{
 //var rads = document.getElementsByName(ele.name);
    var selectCount=0;
 for(var i=0;i<ele.options.length;i++)
    {
        if(ele.options[i].selected)
        {
            selectCount++;
        }
    }
 alert(selectCount);
 if(ele.minSelect!=null && !isNaN(ele.minSelect))
 {
  if(selectCount<parseInt(ele.minSelect))
  {
   alert(convertNullToSpace(ele.objName)+"至少选择"+ele.minSelect+"项");
   ele.focus();     
   return false;
  }
 }
 if(ele.maxSelect!=null && !isNaN(ele.maxSelect))
 {
  if(selectCount>parseInt(ele.maxSelect))
  {
   alert(convertNullToSpace(ele.objName)+"至多选择"+ele.maxSelect+"项");
   ele.focus();     
   return false;
  }
 }
 return true;
}
/***判断是否为整数***/
function isInt(s)
{
 var patrn=/^[-,+]{0,1}[0-9]{0,}$/;
 if (!patrn.exec(s))
   return false;
 return true;
}
/***判断是否为数字***/
function isNumber(s)
{
 var patrn=/^[-,+]{0,1}[0-9]{0,}[.]{0,1}[0-9]{0,}$/;
 if (!patrn.exec(s))
   return false;
 return true;
}
/***判断是否为日期***/
function isDate(str)
{
 var r = str.match(/^(\d{1,4})(-|\/)(\d{1,2})\2(\d{1,2})$/); 
 if(r==null)
 {
  return false;
 } 
 var d= new Date(r[1], r[3]-1, r[4]); 
 if(!(d.getFullYear()==r[1]&&(d.getMonth()+1)==r[3]&&d.getDate()==r[4]))
 {  
  return false;
 }
 return true;
}
/***判断是否为邮箱***/
function isEmail(str)
{
 if(str.match(/[\w-.]+@{1}[\w-]+\.{1}\w{2,4}(\.{0,1}\w{2}){0,1}/ig)!=str)
  return false;
 else
  return true;
}
/***将NULL转化为空格，用于显示对象名称***/
function convertNullToSpace(paramValue)
{
  if(paramValue==null)
    return "";
  else 
    return paramValue;
}
/***检查小数位数***/
function checkDecimal(num,decimalLen)
{
  var len = decimalLen*1+1;
  if(num.indexOf('.')>0)
  {
    num=num.substr(num.indexOf('.')+1,num.length-1);  
    if ((num.length)<len)
 {
      return true;
    }
 else
 {
      return false;
    }
  }
  return true;
}
/***去除空格***/
function trim(str)
{
 if (str.length > 0) 
 {
  while ((str.substring(0,1) == " ") && (str.length > 0)) 
  {
   str = str.substring(1,str.length);
  }
  while (str.substring(str.length-1,str.length) == " ") 
  {
   str = str.substring(0,str.length-1);
  }
 }
 return str;
}

function checkip(ip)
{
	var pattern=/^(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9])\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9]|0)\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9]|0)\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[0-9])$/;
	flag_ip=pattern.test(ip);
	if(!flag_ip)
	{   
	    //alert("IP格式不正确!");  
	    return false;
	}else{
		return true;
	}
}
function isChn(str){
	var pattern=/[^\u4E00-\u9FA5]/;
	//alert(pattern.test(str));
	if(pattern.test(str)){
		//alert("该字段必须为中文类型");
		return false;
	}else{
		return true;
	}
}
function isEnlish(str){
	var pattern=/^[A-Z a-z]+$/;
	if(!pattern.test(str)){
		//alert("该字段必须为英文字母类型");
		return false;
	}else{
		return true;
	}
}
function isTel(str){
	var pattern=/^(([0\+]\d{2,3}-)?(0\d{2,3})-)?(\d{7,8})(-(\d{3,}))?$/;
	if(!pattern.test(str)){
		//alert("电话号码格式不正确");
		return false;
	}else{
		return true;
	}
}
function isMobi(str){
	var pattern=/^(?:13\d|15[89]|18\d)-?\d{5}(\d{3}|\*{3})$/;
	if(!pattern.test(str)){
		//alert("手机号码格式不正确");
		return false;
	}else{
		return true;
	}
}
function check_mac(chkstr)
{
     //var chkstr=document.getElementById("mac").value;
     if(chkstr.length==0){
		return true;
     }
     var pattern="/^([0-9A-Fa-f]{2})(-[0-9A-Fa-f]{2}){5}|([0-9A-Fa-f]{2})(:[0-9A-Fa-f]{2}){5}/";
     eval("var pattern=" + pattern);
     var add_p1 = pattern.test(chkstr);
   	 if(chkstr.length>17){
	 	add_p1=false;
	 }
     return add_p1;
}