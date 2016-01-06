/**
 * 获取url参数值。
 * 
 * @param n
 *            为参数名
 */
var getP = function(n) {
	var hrefstr, pos, parastr, para, tempstr;
	hrefstr = window.location.href;
	pos = hrefstr.indexOf("?");
	parastr = hrefstr.substring(pos + 1);
	para = parastr.split("&");
	tempstr = "";
	for (var i = 0; i < para.length; i++) {
		tempstr = para[i];
		pos = tempstr.indexOf("=");
		if (tempstr.substring(0, pos).toLowerCase() == n.toLowerCase()) {
			return tempstr.substring(pos + 1);
		}
	}
	return '';
};