var FCKFitWindow=function(){this.Name="FitWindow";};FCKFitWindow.prototype.Execute=function(){var b=window.frameElement;var h=b.style;var j=parent;var l=j.document.documentElement;var a=j.document.body;var g=a.style;var i;var d,f;if(FCK.EditMode==FCK_EDITMODE_WYSIWYG){d=new FCKDomRange(FCK.EditorWindow);d.MoveToSelection();f=FCKTools.GetScrollPosition(FCK.EditorWindow);}else{var c=FCK.EditingArea.Textarea;d=!FCKBrowserInfo.IsIE&&[c.selectionStart,c.selectionEnd];f=[c.scrollLeft,c.scrollTop];}if(!this.IsMaximized){if(FCKBrowserInfo.IsIE){j.attachEvent("onresize",FCKFitWindow_Resize);}else{j.addEventListener("resize",FCKFitWindow_Resize,true);}this._ScrollPos=FCKTools.GetScrollPosition(j);i=b;while((i=i.parentNode)){if(i.nodeType==1){i._fckSavedStyles=FCKTools.SaveStyles(i);i.style.zIndex=FCKConfig.FloatingPanelsZIndex-1;}}if(FCKBrowserInfo.IsIE){this.documentElementOverflow=l.style.overflow;l.style.overflow="hidden";g.overflow="hidden";}else{g.overflow="hidden";g.width="0px";g.height="0px";}this._EditorFrameStyles=FCKTools.SaveStyles(b);var e=FCKTools.GetViewPaneSize(j);h.position="absolute";b.offsetLeft;h.zIndex=FCKConfig.FloatingPanelsZIndex-1;h.left="0px";h.top="0px";h.width=e.Width+"px";h.height=e.Height+"px";if(!FCKBrowserInfo.IsIE){h.borderRight=h.borderBottom="9999px solid white";h.backgroundColor="white";}j.scrollTo(0,0);var k=FCKTools.GetWindowPosition(j,b);if(k.x!=0){h.left=(-1*k.x)+"px";}if(k.y!=0){h.top=(-1*k.y)+"px";}this.IsMaximized=true;}else{if(FCKBrowserInfo.IsIE){j.detachEvent("onresize",FCKFitWindow_Resize);}else{j.removeEventListener("resize",FCKFitWindow_Resize,true);}i=b;while((i=i.parentNode)){if(i._fckSavedStyles){FCKTools.RestoreStyles(i,i._fckSavedStyles);i._fckSavedStyles=null;}}if(FCKBrowserInfo.IsIE){l.style.overflow=this.documentElementOverflow;}FCKTools.RestoreStyles(b,this._EditorFrameStyles);j.scrollTo(this._ScrollPos.X,this._ScrollPos.Y);this.IsMaximized=false;}FCKToolbarItems.GetItem("FitWindow").RefreshState();if(FCK.EditMode==FCK_EDITMODE_WYSIWYG){FCK.EditingArea.MakeEditable();}FCK.Focus();if(FCK.EditMode==FCK_EDITMODE_WYSIWYG){d.Select();FCK.EditorWindow.scrollTo(f.X,f.Y);}else{if(!FCKBrowserInfo.IsIE){c.selectionStart=d[0];c.selectionEnd=d[1];}c.scrollLeft=f[0];c.scrollTop=f[1];}};FCKFitWindow.prototype.GetState=function(){if(FCKConfig.ToolbarLocation!="In"){return FCK_TRISTATE_DISABLED;}else{return(this.IsMaximized?FCK_TRISTATE_ON:FCK_TRISTATE_OFF);}};function FCKFitWindow_Resize(){var b=FCKTools.GetViewPaneSize(parent);var a=window.frameElement.style;a.width=b.Width+"px";a.height=b.Height+"px";}