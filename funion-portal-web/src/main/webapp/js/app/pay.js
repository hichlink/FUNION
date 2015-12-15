$(function() {
	// pay();
	$.ajax({
		url : ctxPaths + '/weixin/getJsConfig.do',
		async : false,
		data : {
			"url" : window.location.href
		},
		dataType : 'json',
		type : 'post',
		success : function(dataResult) {
			var data = dataResult.data;
			if (data.appid == null || data.appid == "") {
				return;
			}
			wx.config({
				debug : true,
				appId : data.appid,
				timestamp : data.timestamp,
				nonceStr : data.nonceStr,
				signature : data.signature,
				jsApiList : [ "onMenuShareTimeline", "onMenuShareAppMessage",
						"onMenuShareQQ", "onMenuShareWeibo", "chooseWXPay" ]
			});
		}
	});
	$('#payMoney').click(pay)
	var flag = false;
	function pay() {
		if (flag)
			return;
		flag = true;
		$.ajax({
			url : ctxPaths + '/flow/sendPayRequest.do',
			type : 'get',
			data : {
				uuid : getP('uuid'),
				mobile : getP('mobile'),
				productId : getP('productId')
			},
			dataType : 'json',
			aysnc : false,
			success : function(data) {
				flag = false;
				if (data.success) {
					var obj = data.data;
					alert(JSON.stringify(obj));
					wx.chooseWXPay({
						debug : true,
						timestamp : obj.timeStamp, // 支付签名时间戳，注意微信jssdk中的所有使用timestamp字段均为小写。但最新版的支付后台生成签名使用的timeStamp字段名需大写其中的S字符
						nonceStr : obj.nonceStr, // 支付签名随机串，不长于 32 位
						package : obj.package, // 统一支付接口返回的prepay_id参数值，提交格式如：prepay_id=***）
						signType : obj.signType, // 签名方式，默认为'SHA1'，使用新版支付需传入'MD5'
						paySign : obj.paySign, // 支付签名
						success : function(res) {
							location.href = ctxPaths + "/flow/" + getP('uuid') + "/enter.do";
						}
					});
					wx.error(function(res) {
						flag = false;
						alert(JSON.stringify(res));
					});
				}
			}
		});
	}
});