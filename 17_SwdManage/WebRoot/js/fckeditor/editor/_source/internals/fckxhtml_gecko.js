FCKXHtml._GetMainXmlString=function(){return(new XMLSerializer()).serializeToString(this.MainNode);};FCKXHtml._AppendAttributes=function(c,g,d){var a=g.attributes;for(var h=0;h<a.length;h++){var f=a[h];if(f.specified){var e=f.nodeName.toLowerCase();var b;if(e.StartsWith("_fck")){continue;}else{if(e.indexOf("_moz")==0){continue;}else{if(e=="class"){b=f.nodeValue.replace(FCKRegexLib.FCK_Class,"");if(b.length==0){continue;}}else{if(f.nodeValue===true){b=e;}else{b=g.getAttribute(e,2);}}}}this._AppendAttribute(d,e,b);}}};if(FCKBrowserInfo.IsOpera){FCKXHtml.TagProcessors["head"]=function(a,b){FCKXHtml.XML._HeadElement=a;a=FCKXHtml._AppendChildNodes(a,b,true);return a;};FCKXHtml.TagProcessors["meta"]=function(b,d,a){if(d.parentNode.nodeName.toLowerCase()!="head"){var c=FCKXHtml.XML._HeadElement;if(c&&a!=c){delete d._fckxhtmljob;FCKXHtml._AppendNode(c,d);return null;}}return b;};}if(FCKBrowserInfo.IsGecko){FCKXHtml.TagProcessors["link"]=function(a,b){if(b.href.substr(0,9).toLowerCase()=="chrome://"){return false;}return a;};}