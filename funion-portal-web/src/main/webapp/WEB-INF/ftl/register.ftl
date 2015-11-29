<!doctype html>
<html class="no-js">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>一键注册分享充流量拿佣金</title>
<#include "root.ftl" encoding="utf-8">
</head>
<body>
	<header class="am-topbar admin-header">
		<div class="am-topbar-brand">
			<strong>成为代理</strong> <small>个人信息填写</small>
		</div>
		<img style="width: 42px; height: 42px;margin-top:4px;"
			src="${userInfo.headimgurl}" />
	</header>

	<div class="am-cf admin-main">

		<!-- content start -->
		<div class="admin-content">
			<div class="am-u-sm-12" style="margin-top: 10px;">
				<form class="am-form am-form-horizontal" id="registerForm">
					<div class="am-form-group">
						<label for="realName" class="am-u-sm-3 am-form-label">姓名</label>
						<div class="am-u-sm-9">
							<input type="text" id="realName" name="realName" minlength="2" 
								placeholder="姓名 "> <small>请仔细填写，用于提现。</small>
						</div>
					</div>

					<div class="am-form-group">
						<label for="email" class="am-u-sm-3 am-form-label">电子邮件</label>
						<div class="am-u-sm-9">
							<input type="email" id="email" name="email" 
								placeholder="输入你的电子邮件"> <small>邮箱你懂得...</small>
						</div>
					</div>

					<div class="am-form-group">
						<label for="mobile" class="am-u-sm-3 am-form-label">电话</label>
						<div class="am-u-sm-9">
							<input type="email" id="mobile" name="mobile" data-validation-message="请输入有效手机号码" pattern=" /^(13[0-9]|14[0-9]|15[0-9]|17[0-9]|18[0-9])\d{8}$/"
								placeholder="输入你的电话号码">
						</div>
					</div>

					<div class="am-form-group">
						<label for="cardNo" class="am-u-sm-3 am-form-label">身份证</label>
						<div class="am-u-sm-9">
							<input type="text" name="cardNo" id="cardNo" data-validation-message="请输入有效身份证" pattern="/^([1-9]\d{7}((0\d)|(1[0-2]))(([0|1|2]\d))|3[0-1])\d{3}|([1-9]\d{5}[1-9]\d{3}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])\d{4})$/" placeholder="输入身份证">
						</div>
					</div>

					<div class="am-form-group">
						<div class="am-u-sm-9 am-u-sm-push-3">
							<button type="button" id="registerBtn" class="am-btn am-btn-primary">提交注册</button>
						</div>
					</div>
				</form>
			</div>
		</div>
	</div>
	<!-- content end -->

	<#include "root.ftl" encoding="utf-8">
	<!--[if (gte IE 9)|!(IE)]><!-->
	<script src="${ctx}/assets/js/jquery.min.js"></script>
	<!--<![endif]-->
	<script src="${ctx}/assets/js/amazeui.min.js"></script>

	<script src="${ctx}/js/app/register.js"></script>
</body>
</html>
