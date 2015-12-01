$(function() {
	$("#fetchCashBtn").click(function() {
		$.ajax({
			url : ctxPaths + '/fetchCash/fetch.do',
			type : 'post',
			data : {
				cash : 1
			},
			success : function(data) {
				if (data.success) {
					alert('提取成功');
				} else {
					alert(data.message || '系统错误');
				}
			}
		});
	});
});