$(function() {
	var flag = false;
	$("#fetchCashBtn").click(function() {
		if (flag)
			return;
		flag = true;
		$.ajax({
			url : ctxPaths + '/fetchCash/fetch.do',
			type : 'post',
			data : {
				cash : 100
			},
			success : function(data) {
				flag = false;
				if (data.success) {
					alert('提取成功');
				} else {
					alert(data.message || '系统错误');
				}
			}
		});
	});
	initBalanceFlow()
	function initBalanceFlow() {
		$.ajax({
			url : ctxPaths + '/main/balanceFlow.do',
			type : 'get',
			success : function(data) {
				if (data.success) {
					var html = template('balanceFlowTmpl', data);
					$("#balanceFlow").html(html);
				} else {
					alert(data.message || '系统错误');
				}

			}
		});
	}
});