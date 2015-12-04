<!doctype html>
<html class="no-js">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>分享流量充值页面拿佣金-流量向前冲</title>
<#include "root.ftl" encoding="utf-8">
</head>
<body>
	<header class="am-topbar admin-header">
		<div class="am-topbar-brand">
			<strong>佣金说明</strong>
		</div>
	</header>

	<div class="am-cf admin-main">

		<!-- content start -->
	<div class="am-g">
      <div class="am-u-sm-12 am-u-sm-centered">
        <h2>流量向前冲平台佣金说明</h2>
        <p>个人用户通过一键注册成为[流量向前冲平台]代理.</p>
        <p>代理将流量充值页面分享出去，只要用户在分享出的页面上充值购买流量，代理即可拿到佣金.</p>
        <p>佣金实时到账，随时提现。</p>
        <p style="color:red">点微信右上角的分享即可哦！</p>
        <p><a href="${agentInfo.flowLink}">我的流量充值页面</a></p>
        <hr>
      </div>

    </div>
	</div>
	<!-- content end -->
	<script>
		var shareLink = '${agentInfo.flowLink}';
	</script>
	<#include "foot.ftl" encoding="utf-8">
	<!--[if (gte IE 9)|!(IE)]><!-->
	<script src="${ctx}/assets/js/jquery.min.js"></script>
	<!--<![endif]-->
	<script src="${ctx}/assets/js/amazeui.min.js"></script>
	<script type="text/javascript" src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
	<script src="${ctx}/js/app/wxshare.js"></script>
	<script src="${ctx}/js/app/share.js"></script>
</body>
</html>
