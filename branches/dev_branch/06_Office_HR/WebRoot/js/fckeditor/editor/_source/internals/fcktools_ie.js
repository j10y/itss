FCKTools.CancelEvent=function(a){return false;};FCKTools._AppendStyleSheet=function(b,a){return b.createStyleSheet(a).owningElement;};FCKTools.AppendStyleString=function(b,c){if(!c){return null;}var a=b.createStyleSheet("");a.cssText=c;return a;};FCKTools.ClearElementAttributes=function(a){a.clearAttributes();};FCKTools.GetAllChildrenIds=function(a){var d=new Array();for(var c=0;c<a.all.length;c++){var b=a.all[c].id;if(b&&b.length>0){d[d.length]=b;}}return d;};FCKTools.RemoveOuterTags=function(a){a.insertAdjacentHTML("beforeBegin",a.innerHTML);a.parentNode.removeChild(a);};FCKTools.CreateXmlObject=function(a){var c;switch(a){case"XmlHttp":if(document.location.protocol!="file:"){try{return new XMLHttpRequest();}catch(d){}}c=["MSXML2.XmlHttp","Microsoft.XmlHttp"];break;case"DOMDocument":c=["MSXML2.DOMDocument","Microsoft.XmlDom"];break;}for(var b=0;b<2;b++){try{return new ActiveXObject(c[b]);}catch(d){}}if(FCKLang.NoActiveX){alert(FCKLang.NoActiveX);FCKLang.NoActiveX=null;}return null;};FCKTools.DisableSelection=function(b){b.unselectable="on";var c,a=0;while((c=b.all[a++])){switch(c.tagName){case"IFRAME":case"TEXTAREA":case"INPUT":case"SELECT":break;default:c.unselectable="on";}}};FCKTools.GetScrollPosition=function(b){var a=b.document;var c={X:a.documentElement.scrollLeft,Y:a.documentElement.scrollTop};if(c.X>0||c.Y>0){return c;}return{X:a.body.scrollLeft,Y:a.body.scrollTop};};FCKTools.AddEventListener=function(c,a,b){c.attachEvent("on"+a,b);};FCKTools.RemoveEventListener=function(c,a,b){c.detachEvent("on"+a,b);};FCKTools.AddEventListenerEx=function(e,a,b,d){var c=new Object();c.Source=e;c.Params=d||[];c.Listener=function(f){return b.apply(c.Source,[f].concat(c.Params));};if(FCK.IECleanup){FCK.IECleanup.AddItem(null,function(){c.Source=null;c.Params=null;});}e.attachEvent("on"+a,c.Listener);e=null;d=null;};FCKTools.GetViewPaneSize=function(c){var b;var a=c.document.documentElement;if(a&&a.clientWidth){b=a;}else{b=c.document.body;}if(b){return{Width:b.clientWidth,Height:b.clientHeight};}else{return{Width:0,Height:0};}};FCKTools.SaveStyles=function(c){var d=FCKTools.ProtectFormStyles(c);var b=new Object();if(c.className.length>0){b.Class=c.className;c.className="";}var a=c.style.cssText;if(a.length>0){b.Inline=a;c.style.cssText="";}FCKTools.RestoreFormStyles(c,d);return b;};FCKTools.RestoreStyles=function(b,a){var c=FCKTools.ProtectFormStyles(b);b.className=a.Class||"";b.style.cssText=a.Inline||"";FCKTools.RestoreFormStyles(b,c);};FCKTools.RegisterDollarFunction=function(a){a.$=a.document.getElementById;};FCKTools.AppendElement=function(b,a){return b.appendChild(this.GetElementDocument(b).createElement(a));};FCKTools.ToLowerCase=function(a){return a.toLowerCase();};