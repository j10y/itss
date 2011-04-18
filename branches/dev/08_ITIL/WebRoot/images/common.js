function openWindow(url, iWidth, iHeight)

{

	var iLeft = (window.screen.width - iWidth)/2;

	var iTop = (window.screen.height - iHeight)/2;

	window.open(url,"","resizable=no,scrollbars=no,top="+ iTop +",left="+ iLeft +",width="+ iWidth +",height="+ iHeight);

}

function TrunPage(val)

{

	document.location = val;

}

function DisplayNewTage()

{

	document.write("<img src='../../Scripts/images/icon/new.gif' width='21' height='9' alt='g∞e' border='0'></img>");

}

function DisplayHotTage()

{

	document.write("<img src='../../Scripts/images/icon/hot.gif' width='21' height='9' alt='Ìpπp' border='0'></img>");

}

function OpenContentWithSize(val, iWidth, iHeight)

{

	var iLeft = (window.screen.width - iWidth)/2;

	var iTop = (window.screen.height - iHeight)/2;

	window.open(val,"","resizable=no,scrollbars=no,top="+ iTop +",left="+ iLeft +",width="+ iWidth +",height="+ iHeight);

}

function OpenContent(val)

{

	window.open(val);

}

function Add()

{

	window.external.AddFavorite(window.location.href,"-N˝VﬁèëN/nwm2m:S?eú^Ëï7bQŸz");

}







function doZoom(size) {

    document.getElementById('fontzoom').style.fontSize=size+'px';

}







			function initScollLeft(marqueesDiv,templayerDiv,spaceBetween){

			

				var marqueesTmp,templayerTmp;

				marqueesWidth=spaceBetween; 

				stopscrollLeft=false; 

				marqueesTmp = document.all.item(marqueesDiv);

				templayerTmp = document.all.item(templayerDiv);

				

				with(marqueesTmp){

				noWrap=true;

				style.width=marqueesWidth; 

				style.height=0;

				style.overflowX="hidden"; 

				style.visibility="";

				onmouseover=new Function("stopscrollLeft=true"); 

				onmouseout=new Function("stopscrollLeft=false"); 

				}

				var i =0;

				while(templayerTmp.offsetWidth<marqueesWidth && i<1){

					templayerTmp.innerHTML+=marqueesTmp.innerHTML;

					i++;

				} 

				

				marqueesTmp.innerHTML=templayerTmp.innerHTML+templayerTmp.innerHTML;

				templayerTmp.innerHTML = "";

				setInterval("scrollLeft('"+marqueesDiv+"','"+templayerDiv+"')",50);

			}



	function initScollUp(marqueesDiv,templayerDiv,spaceBetween,width){

		

		var marqueesTmp,templayerTmp;

		

		marqueesHeight=spaceBetween; 

		stopscrollUp=false; 

		marqueesTmp = document.all.item(marqueesDiv);

		templayerTmp = document.all.item(templayerDiv);

		with(marqueesTmp){

		noWrap=true;

		style.width=width; 

		style.height=marqueesHeight;

		style.overflowY="hidden"; 

		style.visibility="";

		onmouseover=new Function("stopscrollUp=true"); 

		onmouseout=new Function("stopscrollUp=false"); 

		}



		var i =0;

		

		while(templayerTmp.offsetHeight<marqueesHeight && i<2){

		

			templayerTmp.innerHTML=templayerTmp.innerHTML + marqueesTmp.innerHTML+"";

			i++;

		} 

		

		marqueesTmp.innerHTML=templayerTmp.innerHTML+templayerTmp.innerHTML;

		templayerTmp.innerHTML = "";

		setInterval("scrollUp('"+marqueesDiv+"','"+templayerDiv+"')",50);

	}

				preLeft=0; 

				function scrollLeft(marqueesDiv,templayerDiv){ 

					var marqueesTmp,templayerTmp;

					marqueesTmp = document.all.item(marqueesDiv);

					templayerTmp = document.all.item(templayerDiv);

					if(stopscrollLeft==true) return; 

					preLeft=marqueesTmp.scrollLeft; 

					marqueesTmp.scrollLeft+=1; 

					if(preLeft==marqueesTmp.scrollLeft){

					marqueesTmp.scrollLeft=templayerTmp.offsetWidth-marqueesWidth+1;

					}

				}

				

preTop=0; 

function scrollUp(marqueesDiv,templayerDiv){ 

	var marqueesTmp,templayerTmp;

	marqueesTmp = document.all.item(marqueesDiv);

	templayerTmp = document.all.item(templayerDiv);

	

	if(stopscrollUp==true) return; 

	preTop=marqueesTmp.scrollTop; 

	marqueesTmp.scrollTop+=1; 

	if(preTop==marqueesTmp.scrollTop){

	marqueesTmp.scrollTop=templayerTmp.offsetHeight-marqueesHeight+1;

	}

}

	
function font(size)
{
	document.getElementById('font').style.fontSize=size+'px';
}

	