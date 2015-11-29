$(function() {
	var $form = $("#registerForm");
	$("#registerBtn").on('click', function() {
		var result = $form.validator('isFormValid');
		if (result) {
			post();
		}
	});
	var submitFlag = false;

	function post() {
		if (submitFlag)
			return;
		submitFlag = true;
		$.ajax({
			url : ctxPaths + '/agentInfo/register.do',
			type : 'post',
			data : $form.serialize(),
			success : function(data) {
				submitFlag = false;
				if (data.success) {
					alert('注册成功');
					location.href = ctxPaths + '/main/enter.do';
				} else {
					alert(data.message);
				}
			}
		});
	}

});