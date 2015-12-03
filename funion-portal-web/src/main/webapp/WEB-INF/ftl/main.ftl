<!doctype html>
<html class="no-js">
<head>
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <title>我的控制台-流量向前冲</title>
<#include "root.ftl" encoding="utf-8">
</head>
<body>
<header class="am-topbar admin-header">
  <div class="am-topbar-brand">
    <strong>流量联盟</strong> <small>${agentInfo.realName}的主页</small>
  </div>

  <button class="am-topbar-btn am-topbar-toggle am-btn am-btn-sm am-btn-success am-show-sm-only" data-am-collapse="{target: '#topbar-collapse'}"><span class="am-sr-only">导航切换</span> <span class="am-icon-bars"></span></button>

</header>

<div class="am-cf admin-main">
  
  <!-- content start -->
  <div class="admin-content">

    <div class="am-cf am-padding">
    </div>

    <ul class="am-avg-sm-3 am-text-center">
      <li><a href="javascript:;" id="fetchCashBtn"  class="am-text-success"><span class="am-icon-btn am-success am-icon-university"></span><br/>账户余额<br/>${agentInfo.balance}</a></li>
      <li><a href="javascript:;" class="am-text-warning"><span class="am-icon-btn am-warning am-icon-jpy"></span><br/>累计收益<br/>${agentInfo.incomeTotal}</a></li>
      <li><a href="#" class="am-text-primary"><span class="am-icon-btn am-primary am-icon-credit-card"></span><br/>开始赚钱<br/>${agentInfo.presentAmount}</a></li>
    </ul>

    <div class="am-g">
      <div class="am-u-sm-12">
        <table class="am-table am-table-bd am-table-striped admin-content-table">
          <thead>
          <tr>
            <th>日期</th><th>操作金额</th><th>备注</th>
          </tr>
          </thead>
          <tbody id="balanceFlow">
          </tbody>
        </table>
      </div>
    </div>

  </div>
  <!-- content end -->
	 <div class="am-modal am-modal-prompt" tabindex="-1" id="cashModel">
	  <div class="am-modal-dialog">
	    <div class="am-modal-hd">佣金提现</div>
	    <div class="am-modal-bd">
	                     您目前可提现金额:<span id="amountTip"></span>元
	      <input type="text" id="cash" class="am-modal-prompt-input">
	    </div>
	    <div class="am-modal-footer">
	      <span class="am-modal-btn" data-am-modal-cancel>取消</span>
	      <span class="am-modal-btn" data-am-modal-confirm>提交</span>
	    </div>
	  </div>
	</div>
	<script id="balanceFlowTmpl" type="text/html"> 
	<% for(var i=0; i<rows.length; i++){%>  
		<tr><td><%=rows[i].inputTime%></td><td><%=rows[i].commisionAmount%>元</td><td><%=rows[i].remark%></td>
          </tr>
    <%}%> 
	</script>
	<#include "foot.ftl" encoding="utf-8">
	<!--[if (gte IE 9)|!(IE)]><!-->
	<script src="${ctx}/assets/js/jquery.min.js"></script>
	<!--<![endif]-->
	<script src="${ctx}/assets/js/amazeui.min.js"></script>
	<script src="${ctx}/js/template-native.js"></script>
	<script src="${ctx}/js/app/main.js"></script>
</body>
</html>
