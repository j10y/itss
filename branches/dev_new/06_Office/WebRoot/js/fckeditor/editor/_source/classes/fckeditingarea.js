var FCKEditingArea=function(a){this.TargetElement=a;this.Mode=FCK_EDITMODE_WYSIWYG;if(FCK.IECleanup){FCK.IECleanup.AddItem(this,FCKEditingArea_Cleanup);}};FCKEditingArea.prototype.Start=function(f,j){var b=this.TargetElement;var d=FCKTools.GetElementDocument(b);while(b.firstChild){b.removeChild(b.firstChild);}if(this.Mode==FCK_EDITMODE_WYSIWYG){if(FCK_IS_CUSTOM_DOMAIN){f='<script>document.domain="'+FCK_RUNTIME_DOMAIN+'";<\/script>'+f;}if(FCKBrowserInfo.IsIE){f=f.replace(/(<base[^>]*?)\s*\/?>(?!\s*<\/base>)/gi,"$1></base>");}else{if(!j){var g=f.match(FCKRegexLib.BeforeBody);var a=f.match(FCKRegexLib.AfterBody);if(g&&a){var i=f.substr(g[1].length,f.length-g[1].length-a[1].length);f=g[1]+"&nbsp;"+a[1];if(FCKBrowserInfo.IsGecko&&(i.length==0||FCKRegexLib.EmptyParagraph.test(i))){i='<br type="_moz">';}this._BodyHTML=i;}else{this._BodyHTML=f;}}}var e=this.IFrame=d.createElement("iframe");var l='<script type="text/javascript" _fcktemp="true">window.onerror=function(){return true;};<\/script>';e.frameBorder=0;e.style.width=e.style.height="100%";if(FCK_IS_CUSTOM_DOMAIN&&FCKBrowserInfo.IsIE){window._FCKHtmlToLoad=f.replace(/<head>/i,"<head>"+l);e.src="javascript:void( (function(){"+"document.open() ;"+'document.domain="'+document.domain+'" ;'+"document.write( window.parent._FCKHtmlToLoad );"+"document.close() ;"+"window.parent._FCKHtmlToLoad = null ;"+"})() )";}else{if(!FCKBrowserInfo.IsGecko){e.src="javascript:void(0)";}}b.appendChild(e);this.Window=e.contentWindow;if(!FCK_IS_CUSTOM_DOMAIN||!FCKBrowserInfo.IsIE){var k=this.Window.document;k.open();k.write(f.replace(/<head>/i,"<head>"+l));k.close();}if(FCKBrowserInfo.IsAIR){FCKAdobeAIR.EditingArea_Start(k,f);}if(FCKBrowserInfo.IsGecko10&&!j){this.Start(f,true);return;}if(e.readyState&&e.readyState!="completed"){var h=this;setTimeout(function(){try{h.Window.document.documentElement.doScroll("left");}catch(m){setTimeout(arguments.callee,0);return;}h.Window._FCKEditingArea=h;FCKEditingArea_CompleteStart.call(h.Window);},0);}else{this.Window._FCKEditingArea=this;if(FCKBrowserInfo.IsGecko10){this.Window.setTimeout(FCKEditingArea_CompleteStart,500);}else{FCKEditingArea_CompleteStart.call(this.Window);}}}else{var c=this.Textarea=d.createElement("textarea");c.className="SourceField";c.dir="ltr";FCKDomTools.SetElementStyles(c,{width:"100%",height:"100%",border:"none",resize:"none",outline:"none"});b.appendChild(c);c.value=f;FCKTools.RunFunction(this.OnLoad);}};function FCKEditingArea_CompleteStart(){if(!this.document.body){this.setTimeout(FCKEditingArea_CompleteStart,50);return;}var a=this._FCKEditingArea;a.Document=a.Window.document;a.MakeEditable();FCKTools.RunFunction(a.OnLoad);}FCKEditingArea.prototype.MakeEditable=function(){var a=this.Document;if(FCKBrowserInfo.IsIE){a.body.disabled=true;a.body.contentEditable=true;a.body.removeAttribute("disabled");}else{try{a.body.spellcheck=(this.FFSpellChecker!==false);if(this._BodyHTML){a.body.innerHTML=this._BodyHTML;a.body.offsetLeft;this._BodyHTML=null;}a.designMode="on";a.execCommand("enableObjectResizing",false,!FCKConfig.DisableObjectResizing);a.execCommand("enableInlineTableEditing",false,!FCKConfig.DisableFFTableHandles);}catch(b){FCKTools.AddEventListener(this.Window.frameElement,"DOMAttrModified",FCKEditingArea_Document_AttributeNodeModified);}}};function FCKEditingArea_Document_AttributeNodeModified(a){var b=a.currentTarget.contentWindow._FCKEditingArea;if(b._timer){window.clearTimeout(b._timer);}b._timer=FCKTools.SetTimeout(FCKEditingArea_MakeEditableByMutation,1000,b);}function FCKEditingArea_MakeEditableByMutation(){delete this._timer;FCKTools.RemoveEventListener(this.Window.frameElement,"DOMAttrModified",FCKEditingArea_Document_AttributeNodeModified);this.MakeEditable();}FCKEditingArea.prototype.Focus=function(){try{if(this.Mode==FCK_EDITMODE_WYSIWYG){if(FCKBrowserInfo.IsIE){this._FocusIE();}else{this.Window.focus();}}else{var a=FCKTools.GetElementDocument(this.Textarea);if((!a.hasFocus||a.hasFocus())&&a.activeElement==this.Textarea){return;}this.Textarea.focus();}}catch(b){}};FCKEditingArea.prototype._FocusIE=function(){this.Document.body.setActive();this.Window.focus();var b=this.Document.selection.createRange();var a=b.parentElement();var c=a.nodeName.toLowerCase();if(a.childNodes.length>0||!(FCKListsLib.BlockElements[c]||FCKListsLib.NonEmptyBlockElements[c])){return;}b=new FCKDomRange(this.Window);b.MoveToElementEditStart(a);b.Select();};function FCKEditingArea_Cleanup(){if(this.Document){this.Document.selection.empty();this.Document.body.innerHTML="";}this.TargetElement=null;this.IFrame=null;this.Document=null;this.Textarea=null;if(this.Window){this.Window._FCKEditingArea=null;this.Window=null;}}