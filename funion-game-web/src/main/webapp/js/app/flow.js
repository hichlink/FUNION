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
					"onMenuShareQQ", "onMenuShareWeibo" ]
		});
	}
});
wx.ready(function() {
	loadWxShare("全网流量最便9.5折,赶紧来抢吧", window.location.href, 'http://' + window.location.host
			+ ctxPaths + '/images/share.jpg', '你的朋友邀请你来充流量了');
});
$(function() {

	var MOBILE_PATTERN = /^((13[0-9]{1})|(14[0-9]{1})|(15[0-9]{1})|(17[0-9]{1})|(18[0-9]{1}))+\d{8}$/;

	$("#mobile").on('keyup', function() {
		getFlowPackageByMobile();
	});
	$("#payBtn").on('click', function() {
		pay();
	});
	function initBoxEvent() {
		var $boxs = $('div.box');
		$boxs.on('click', function() {
			$boxs.removeClass('box-on');
			$(this).addClass('box-on');
		});
	}
	function getFlowPackageByMobile() {
		var tel = $.trim($("#mobile").val());
		if (tel.length == 11 && MOBILE_PATTERN.test(tel)) {
			$("#flowPackageDiv").html('');
			$.ajax({
				url : ctxPaths + '/flow/getProduct.do',
				type : 'get',
				data : {
					mobile : tel
				},
				success : function(data) {
					if (data.success) {
						var html = template('flowPackages', data);
						$("#flowPackageDiv").html(html);
						initBoxEvent();
					} else {
						$("#flowPackageDiv").html('');
					}
				}
			});
		} else {
			$("#flowPackageDiv").html('');
		}
	}
	function pay() {
		var tel = $.trim($("#mobile").val()), uuid = $('#uuid').val();
		if (!MOBILE_PATTERN.test(tel)) {
			alert('请输入有效的手机号码');
			return;
		}
		var box = $('#flowPackageDiv').find('div.box-on');
		if (box.length == 0) {
			alert('请选择流量包');
			return;
		}
		location.href = ctxPaths + '/flow/' + uuid + '/' + tel + '/'
				+ $(box[0]).attr('data-id') + '/enterPay.do';
	}
});