$(function() {
	$("#fetchCashBtn").on('click', function() {
		$.ajax({
			url : ctxPaths + '/fetchCash/fetch.do',
			type : 'get',
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