$(function() {
	var MOBILE_PATTERN = /^((13[0-9]{1})|(14[0-9]{1})|(15[0-9]{1})|(17[0-9]{1})|(18[0-9]{1}))+\d{8}$/;

	$("#mobile").on('change', function() {
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
		var tel = $.trim($("#mobile").val());
		if (!MOBILE_PATTERN.test(tel)) {
			alert('请输入有效的手机号码');
			return;
		}
		var box = $('#flowPackageDiv').find('div.box-on');
		if (box.length == 0) {
			alert('请选择流量包');
			return;
		}
		location.href = ctxPaths + '/flow/' + tel + '/'
				+ $(box[0]).attr('data-id') + '/enterPay.do';
	}
});