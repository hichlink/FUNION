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
				debug : false,
				appId : data.appid,
				timestamp : data.timestamp,
				nonceStr : data.nonceStr,
				signature : data.signature,
				jsApiList : [ "onMenuShareTimeline", "onMenuShareAppMessage",
						"onMenuShareQQ", "onMenuShareWeibo"]
			});
		}
	});
	wx.ready(function(){
		loadWxShare("全网流量最便9.5折,赶紧来抢吧",shareLink,'http://' + window.location.host + ctxPaths + '/images/share.jpg','你的朋友邀请你来充流量了');
	});
});