// JavaScript Document
////////////////////////////////////////////// 注释掉所有错误
function killErrors() { 
  return true; 
} 
//window.onerror = killErrors; 
/////////////////////////////////////////////
function KeyPress(objTR)
{//只允许录入数据字符 0-9 和小数点 
		//var objTR = element.document.activeElement;		
		var txtval=objTR.value;		
		
		var key = event.keyCode;

		if((key < 48||key > 57)&&key != 46)
		{		
			event.keyCode = 0;
		}
		else
		{
			if(key == 46)
			{
				if(txtval.indexOf(".") != -1||txtval.length == 0)
					event.keyCode = 0;
			}
		}
}

function URLencode(sStr)
{
    return escape(sStr).replace(/\+/g, '%2B').replace(/\"/g,'%22').replace(/\'/g, '%27').replace(/\//g,'%2F');
}
///////////////////////////////////////////// 屏蔽F1
//function window.onhelp(){return false}
//////////////////////////////////////////// 修改
function edit()
{
	var len=arguments.length;
	for(var i=0;i<len;i++)
	{
		arguments[i].innerText=arguments[i+1];
	}
	return false;
}
function set_select(obj,type_id) //修改select,obj对应type_id的值被选中
{
	for(var i=0;i<obj.length;i++)
	{
		if(obj.options[i].value==type_id)
		{
			obj.options[i].selected=true;
			break;
		}
	}
}
//////////////////////////////////////////////////////////
function read(url)
{
	document.scripts[0].src=url;
}
/////////////////////////////////////////////////////////屏蔽鼠标右键 
//function document.oncontextmenu(){event.returnValue=false;}
/////////////////////////////////////////////////////////
/*function document.onkeydown()
{ 
 if ((event.keyCode==116)||                 //屏蔽 F5 刷新键 
      (event.ctrlKey && event.keyCode==82)){ //Ctrl + R 
     event.keyCode=0; 
     event.returnValue=false; 
     } 
if (event.keyCode==78&&event.ctrlKey) //屏蔽 Ctrl+n
{event.keyCode = 0; 
event.cancelBubble = true; 
return false; 
}
if (event.keyCode==115&&event.ctrlKey) //屏蔽 alt+f4
{event.keyCode = 0; 
event.cancelBubble = true; 
return false; 
}
if ((window.event.altKey)&&(window.event.keyCode==115))             //屏蔽Alt+F4 
{ 
 window.showModelessDialog("about:blank","","dialogWidth:1px;dialogheight:1px"); 
  return false; 
}
if ((event.keyCode == 8) && 
	 (event.srcElement.type != "text" && 
event.srcElement.type != "textarea" && 
event.srcElement.type != "password") //屏蔽退格删除键 
	 ){ 
event.keyCode=0;
event.returnvalue=false;
}
*//*if ((event.altKey)&&
((event.keyCode==37)|| //屏蔽 Alt+ 方向键 ←
(event.keyCode==39))){ //屏蔽 Alt+ 方向键 →
alert("不能使用ALT+方向键前进或后退！");
event.returnvalue=false;
}

}*/
/*function document.onclick()
{
if(event.srcElement.tagName == "A" && event.shiftKey||event.srcElement.tagName == "IMG" && event.shiftKey) 
{ return false}
}*/
////////////////////////////////////////////////// 回车变成Tab
function next()
{
	if(event.keyCode==13){event.keyCode=9}	
}
////////////////////////////////////////////////// 去掉两边空格
function trim(str)  
{ 
return str.replace(/^\s*(.*?)[\s\n]*$/g, '$1'); 
}
////////////////////////////////////////////////// 遍历所有参数是不是为空
function check()
{	var flag;
	flag=true;
	var len=arguments.length;
	for(var i=0;i<len;i=i+2)
	{
		var obj=arguments[i];
		if(trim(obj.value)=="")
		{
			alert(arguments[i+1]);
			obj.focus();
			flag=false;
			break;
		}
	}
		return flag;	
}
//////////////////////////////////////////////// 
function winOpen(url,w,h)
{
	window.open(url,"show","width="+w+",height="+h+"");
	//showModalDialog(url, "", "dialogWidth:"+w+"px; dialogHeight:"+h+"px;status:no;help:yes;scroll:auto");
}
function showDialog(url,w,h)
{
	showModalDialog(url, "", "dialogWidth:"+w+"px; dialogHeight:"+h+"px;status:no;");
}
////////////////////////////////////////////////
function delconfirm()
{
	return confirm('确认删除？')
}
//////////////////////////////////////////////
  function $(d){return document.getElementById(d);}
/*  function gs(d){var t=$(d);if (t){return t.style;}else{return null;}}
  function gs2(d,a){
    if (d.currentStyle){ 
      var curVal=d.currentStyle[a]
    }else{ 
      var curVal=document.defaultView.getComputedStyle(d, null)[a]
    } 
    return curVal;
  }
  
  function ChatHidden(){gs("ChatBody").display = "none";}
  function ChatShow(){gs("ChatBody").display = "";}
	//关闭
  function ChatClose(){gs("main").display = "none";}
  //打开
  function ChatOpen(){gs("main").display = "";}
  if  (document.getElementById){
    (
      function(){
        if (window.opera){ document.write("<input type='hidden' id='Q' value=' '>"); }
        var n = 500;
        var dragok = false;
        var y,x,d,dy,dx;
        
        function move(e)
        {
          if (!e) e = window.event;
          if (dragok){
            d.style.left = dx + e.clientX - x + "px";
            d.style.top  = dy + e.clientY - y + "px";
			if(document.body.offsetHeight-d.offsetTop<200){d.style.top=370+"px"}
			if(document.body.offsetWidth-d.offsetLeft<380){d.style.left=370+"px"}
            return false;
          }
        }
        
        function down(e){
          if (!e) e = window.event;
          var temp = (typeof e.target != "undefined")?e.target:e.srcElement;
          if (temp.tagName != "HTML"|"BODY" && temp.className != "dragclass"){
            temp = (typeof temp.parentNode != "undefined")?temp.parentNode:temp.parentElement;
          }
          if('TR'==temp.tagName){
            temp = (typeof temp.parentNode != "undefined")?temp.parentNode:temp.parentElement;
            temp = (typeof temp.parentNode != "undefined")?temp.parentNode:temp.parentElement;
            temp = (typeof temp.parentNode != "undefined")?temp.parentNode:temp.parentElement;
          }
        
          if (temp.className == "dragclass"){
            if (window.opera){ document.getElementById("Q").focus(); }
            dragok = true;
            temp.style.zIndex = n++;
            d = temp;
            dx = parseInt(gs2(temp,"left"))|0;
            dy = parseInt(gs2(temp,"top"))|0;
            x = e.clientX;
            y = e.clientY;
            document.onmousemove = move;
            return false;
          }
        }
        
        function up(){
          dragok = false;
          document.onmousemove = null;
        }
        
        document.onmousedown = down;
        document.onmouseup = up;
      
      }
    )();
  }
*/
////////////////////////////////////////////////////////
function createXMLHttpRequest(){
    var xmlhttp;
    try{
        xmlhttp = new ActiveXObject("Msxml2.XMLHTTP");
    }catch(e){
        try{
            xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
        }catch(e){
            try{
                xmlhttp = new XMLHttpRequest();
            }catch(e){
                alert("XMLHttpRequest创建失败!");
            }
        }
    }
    return xmlhttp;
}
//////////////////////////////////////////////////
//菜单
var menuOffX=0	//菜单距连接文字最左端距离
var menuOffY=30	//菜单距连接文字顶端距离

var ie4=document.all&&navigator.userAgent.indexOf("Opera")==-1
var ns6=document.getElementById&&!document.all

function showmenu(e,vmenu,mod){
	which=vmenu
	menuobj=document.getElementById("popmenu")
	menuobj.thestyle=menuobj.style
	menuobj.innerHTML=which
	menuobj.contentwidth=menuobj.offsetWidth
	eventX=e.clientX
	eventY=e.clientY
	var rightedge=document.body.clientWidth-eventX
	var bottomedge=document.body.clientHeight-eventY
	var getlength
		if (rightedge<menuobj.contentwidth){
			getlength=ie4? document.body.scrollLeft+eventX-menuobj.contentwidth+menuOffX : ns6? window.pageXOffset+eventX-menuobj.contentwidth : eventX-menuobj.contentwidth
		}else{
			getlength=ie4? ie_x(event.srcElement)+menuOffX : ns6? window.pageXOffset+eventX : eventX
		}
		menuobj.thestyle.left=getlength+'px'
		if (bottomedge<menuobj.contentheight&&mod!=0){
			getlength=ie4? document.body.scrollTop+eventY-menuobj.contentheight-event.offsetY+menuOffY-23 : ns6? window.pageYOffset+eventY-menuobj.contentheight-10 : eventY-menuobj.contentheight
		}	else{
			getlength=ie4? ie_y(event.srcElement)+menuOffY : ns6? window.pageYOffset+eventY+10 : eventY
		}
	menuobj.thestyle.top=getlength+'px'
	menuobj.thestyle.visibility="visible"
}

function ie_y(e){  
	var t=e.offsetTop;  
	while(e=e.offsetParent){  
		t+=e.offsetTop;  
	}  
	return t;  
}  
function ie_x(e){  
	var l=e.offsetLeft;  
	while(e=e.offsetParent){  
		l+=e.offsetLeft;  
	}  
	return l;  
}  

function highlightmenu(e,state){
	if (document.all)
		source_el=event.srcElement
	else if (document.getElementById)
		source_el=e.target
	if (source_el.className=="menuitems"){
		source_el.id=(state=="on")? "mouseoverstyle" : ""
	}
	else{
		while(source_el.id!="popmenu"){
			source_el=document.getElementById? source_el.parentNode : source_el.parentElement
			if (source_el.className=="menuitems"){
				source_el.id=(state=="on")? "mouseoverstyle" : ""
			}
		}
	}
}

function hidemenu(){if (window.menuobj)menuobj.thestyle.visibility="hidden"}
function dynamichide(e){if ((ie4||ns6)&&!menuobj.contains(e.toElement))hidemenu()}

document.onclick=hidemenu
document.write("<div class=menuskin id=popmenu onmouseover=highlightmenu(event,'on') onmouseout=highlightmenu(event,'off');dynamichide(event)></div>")
// 菜单END
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//菜单滑动
function setTab(ID,CountID){
		for (i=1;i<=CountID ;i++ )
		{
			if (i==ID)
			{
				document.getElementById("Menu_"+ID).className="BgFloatNow";
				document.getElementById("info_"+ID).style.display="";
			}else{
				document.getElementById("Menu_"+i).className="BgFloatLost";
				document.getElementById("info_"+i).style.display="none";
			}
		}
	  }
//////////////////////////////////////
function DoSearchSelect(){
	sType=document.getElementById("SearchType")
		if (sType.value==1)
		{
			document.getElementById('sFrom').action="SearchProduct.asp";
		}else if (sType.value==2)
		{
			document.getElementById('sFrom').action="SearchSolution.asp";
		}
}
///////////////////////////////////////////