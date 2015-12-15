<!doctype html>
<html class="no-js">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>流量9.5折任性充-流量向前冲</title>
<#include "root.ftl" encoding="utf-8">
<style>
.box {
	border-radius: 0.5em;
	width: 90px;
	height: 48px;
	border: 1px solid #CCCCCC;
	float: left;
	margin: 8px 4px;
	text-align: center;
	line-height: 18px;
	font-size: 12px;
	padding: 4px;
}

.box-on {
	background: #FF6347;
	color: #FFFFFF;
	border: 0px;
}

.txt1 {
	font-size: 16px;
	font-weight: bold;
}

.txt2 {
	font-size: 10px;
	color: #CCCCCC;
}

.txt3 {
	font-size: 12px;
	color: #339900;
}

.line-through {
	text-decoration: line-through;
}
</style>
</head>
<body>
	<header class="am-topbar">
		<div class="am-topbar-brand">
			<strong>全网9.5折随时充</strong>
		</div>
	</header>

	<div class="am-cf admin-main">

		<!-- content start -->
		<div class="admin-content">
			<div class="am-u-sm-12" style="margin-top: 8px;">
				<form class="am-form am-form-horizontal">
					<input type="hidden" id="uuid" name="uuid" value="${uuid}" />
					<div class="am-form-group">
						<div class="am-u-sm-12">
							<input type="text" id="mobile" name="mobile" placeholder="输入11位手机号码 ">
							<small><span>移动号码</span> 全国通用，当月有效，支持2G/3G/4G</small>
						</div>
					</div>
					<div class="am-form-group" id="flowPackageDiv"></div>
					<div class="am-form-group">
						<div class="am-u-sm-12" style="text-align: right;">
							<button type="button" id="payBtn" class="am-btn am-btn-warning am-btn-block">立即充值</button>
						</div>
					</div>
					<div class="am-form-group">
						<div class="am-u-sm-12" style="font-size: 12px;">
							1、生效时间：充值成功即时生效，不支持冲正。<br /> 2、使用范围：支持全国漫游。<br /> 3、使用有效期：当月有效。<br />
							4、充值次数限制：移动单个号码每月充值30次。<br /> 5、温馨提醒：用户处于欠费停机等异常状态均不能充值。<br />
						</div>
					</div>


				</form>
			</div>
		</div>
	</div>
	<script id="flowPackages" type="text/html"> 
	   <div class="am-u-sm-12">
			<% for(var i=0; i<data.length; i++){%>  
			<div class="box" data-id="<%=data[i].productId%>">
				<span class="txt1"><%=data[i].flowAmount%>M</span><br /> <span
						class="txt2 line-through"><%=data[i].costPrice%>元</span><span class="txt2">
				 现价</span><span class="txt3"><%=data[i].settlementPrice%></span><span class="txt2">元</span>
			</div>
    		<%}%> 
		</div>
	</script>
	<!-- content end -->
	<#include "foot.ftl" encoding="utf-8">
	<!--[if (gte IE 9)|!(IE)]><!-->
	<script src="${ctx}/assets/js/jquery.min.js"></script>
	<!--<![endif]-->
	<script src="${ctx}/assets/js/amazeui.min.js"></script>
	<script src="${ctx}/js/template-native.js"></script>
	<script type="text/javascript" src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
	<script src="${ctx}/js/app/wxshare.js"></script>
	<script src="${ctx}/js/app/flow.js"></script>
</body>
</html>
