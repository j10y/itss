var FCKToolbarBreak=function(){};FCKToolbarBreak.prototype.Create=function(a){var b=FCKTools.GetElementDocument(a).createElement("div");b.className="TB_Break";b.style.clear=FCKLang.Dir=="rtl"?"left":"right";a.appendChild(b);};