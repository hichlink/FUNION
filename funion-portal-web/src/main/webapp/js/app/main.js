$(function() {
	$('#fetchCashBtn').on('click', function() {
		$('#cashModel').modal({
			relatedTarget : this,
			onConfirm : function(e) {
				fetchCash(e.data);
			},
			onCancel : function(e) {

			}
		});
	});
	function getMyBalance() {
		$.ajax({
			url : ctxPaths + '/main/getMyBalance.do?' + new Date().getTime(),
			type : 'get',
			success : function(data) {
				if (data.success) {
					$("#cash").val('');
					$("#amountTip,#balance").html(data.data.balance);
					$("#incomeTotal").html(data.data.incomeTotal);
				} else {
					alert(data.message || '系统错误');
				}

			}
		});
	}
	var flag = false;
	function fetchCash(cash) {
		var a = /^[0-9]*(\.[0-9]{1,2})?$/;
		if (!a.test(cash)) {
			alert('输入的金额无效');
			return;
		}
		if (flag)
			return;
		flag = true;
		$.ajax({
			url : ctxPaths + '/fetchCash/fetch.do',
			type : 'post',
			data : {
				cash : cash
			},
			success : function(data) {
				flag = false;
				if (data.success) {
					getMyBalance();
					initBalanceFlow();
					alert('提取成功');
				} else {
					alert(data.message || '系统错误');
				}
			}
		});
	}
	initBalanceFlow();
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