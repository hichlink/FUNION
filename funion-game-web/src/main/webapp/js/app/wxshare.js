function loadWxShare(shareTitle, lineLink, imgUrl, descContent) {
	wx.onMenuShareTimeline({
		title : shareTitle, // 分享标题
		link : lineLink, // 分享链接
		imgUrl : imgUrl, // 分享图标
		desc : descContent, // 分享描述
		success : function() {
		},
		cancel : function() {
		}
	});
	// 分享到朋友
	wx.onMenuShareAppMessage({
		title : shareTitle, // 分享标题
		desc : descContent, // 分享描述
		link : lineLink, // 分享链接
		imgUrl : imgUrl, // 分享图标
		success : function() {
		},
		cancel : function() {
		}
	});
	// 分享到qq
	wx.onMenuShareQQ({
		title : shareTitle, // 分享标题
		desc : descContent, // 分享描述
		link : lineLink, // 分享链接
		imgUrl : imgUrl, // 分享图标
		success : function() {
		},
		cancel : function() {
		}
	});
	// 分享到微博
	wx.onMenuShareWeibo({
		title : shareTitle, // 分享标题
		desc : descContent, // 分享描述
		link : lineLink, // 分享链接
		imgUrl : imgUrl, // 分享图标
		success : function() {
		},
		cancel : function() {
		}
	});
}