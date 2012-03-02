/*
 * Unicode
 */
function unicode(s) {
	var len = s.length;
	var rs = "";
	for (var i = 0; i < len; i++) {
		var k = s.substring(i, i + 1);
		rs += "&#" + s.charCodeAt(i) + ";";
	}
	return rs;
}

/*
 * ·´unicode
 */
function runicode(s) {
	var k = s.split(";");
	var r = "";
	for (var x = 0; x < k.length; x++) {
		var m = k[x].replace(/&#/, "");
		r += String.fromCharCode(m);
	}
	return r;
}