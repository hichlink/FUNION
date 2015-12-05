<!doctype html>
<html class="no-js">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>微信支付-流量向前冲</title>
<#include "root.ftl" encoding="utf-8">
</head>
<body>
	<header class="am-topbar admin-header">
		<div class="am-topbar-brand">
			<small>结算信息</small>
		</div>
	</header>

	<div class="am-cf admin-main">

		<!-- content start -->
		<div class="admin-content">
			<div class="am-u-sm-12" style="margin-top: 10px;">
				<form class="am-form am-form-horizontal" id="payForm">
					<div class="am-form-group">
						<div class="am-u-sm-4 am-text-right">商品名：</div>
						<div class="am-u-sm-8">
							${flowProductDTO.productName}
						</div>
					</div>

					<div class="am-form-group">
						<div class="am-u-sm-4 am-text-right">售价：</div>
						<div class="am-u-sm-8">
							${flowProductDTO.settlementPrice}元
						</div>
					</div>

					<div class="am-form-group">
						<div for="mobile" class="am-u-sm-4 am-text-right">数量：</div>
						<div class="am-u-sm-8">
							1
						</div>
					</div>

					<div class="am-form-group">
						<div class="am-u-sm-4 am-text-right">合计支付：</div>
						<div class="am-u-sm-8">
							${flowProductDTO.totalPrice}元
						</div>
					</div>
					<div class="am-form-group">
						<div class="am-u-sm-4 am-text-right">支付方式：</div>
						<div class="am-u-sm-8">
							<span class="nav_icon"><img style="width:24px;height:24px;"
										src="${ctx}/images/icon_wechat.png" /></span> 微信支付
						</div>
					</div>
					<div class="am-form-group">
						<div class="am-u-sm-12">
							<button type="button" id="payMoney" class="am-btn am-btn-warning am-btn-block">安全支付</button>
						</div>
					</div>
				</form>
			</div>
		</div>
	</div>
	<!-- content end -->

	<#include "foot.ftl" encoding="utf-8">
	<!--[if (gte IE 9)|!(IE)]><!-->
	<script src="${ctx}/assets/js/jquery.min.js"></script>
	<!--<![endif]-->
	<script src="${ctx}/assets/js/amazeui.min.js"></script>
	<script type="text/javascript"
		src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
			<script type="text/javascript" src="${ctx}/js/app/common.js"></script>
	<script type="text/javascript" src="${ctx}/js/app/pay.js?20151201"></script>
</body>
</html>
