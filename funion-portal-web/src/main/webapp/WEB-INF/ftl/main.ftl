<!doctype html>
<html class="no-js">
<head>
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <title>我的控制台-流量向前冲</title>
<#include "root.ftl" encoding="utf-8">
<style>
.ds-avatar {
    background-color: #f8f8f8;
    border-radius: 3px;
    box-shadow: 0 1px 1px rgba(255, 255, 255, 0.75);
}
</style>
</head>
<body>
<header class="am-topbar admin-header">
  <div class="am-topbar-brand">
    <strong>流量向前冲</strong> <small>${agentInfo.agentName}的主页</small>
  </div>

  <div class="ds-avatar" style="float:right;margin-top:4px;margin-right:18px;">
	<img style="width: 42px; height: 42px;border-radius: 50%;background-color: #fff;"
			src="${agentInfo.headImg}" />
 </div>

</header>

<div class="am-cf admin-main">
  
  <!-- content start -->
  <div class="admin-content">

    <div class="am-cf am-padding">
    </div>

    <ul class="am-avg-sm-3 am-text-center">
      <li><a href="javascript:;" id="fetchCashBtn"  class="am-text-success"><span class="am-icon-btn am-success am-icon-jpy"></span><br/>账户余额<br/><span id="balance">${agentInfo.balance}</span>元</a></li>
      <li><a href="javascript:;" class="am-text-warning"><span class="am-icon-btn am-warning am-icon-university"></span><br/>累计收益<br/><span id="incomeTotal">${agentInfo.incomeTotal}</span>元</a></li>
      <li><a href="${ctx}/main/share.do" class="am-text-primary"><span class="am-icon-btn am-primary am-icon-credit-card"></span><br/>开始赚钱<br/>${agentInfo.presentAmount}</a></li>
    </ul>

    <div class="am-g">
      <div class="am-u-sm-12">
        <table class="am-table am-table-striped table-main">
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
	                     您目前可提现金额:<span id="amountTip">${agentInfo.balance}</span>元
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
		<tr>
			<td><%=rows[i].checkTime%></td>
			<td><span style="color:#CD853F">
			<% if(rows[i].commisionAmount > 0) {%> 
				+<%=rows[i].commisionAmount%> 
			<% }else{%>  
				<%=rows[i].commisionAmount%>
			<% }%> 
			元</span></td>
			<td><%=rows[i].remark%></td>
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
