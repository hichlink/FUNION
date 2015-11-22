var view_url = ctxPaths + '/orderInfo/get.ajax';
var pkId = getP('orderId');

var prodDetailTable = null;
var mpTableId = "#product-detail-table";
var orderRecordTableId = "#order-record-table";
var isWYAdmin = false;

$(function() {
    pOILInit();
});

function pOILInit() {
    $.ajax({
        url : ctxPaths + '/orderInfo/orderInfoViewInit.ajax',
        dataType : 'json',
        success : function(data, status) {
            isWYAdmin = data.isWYAdmin;
            initStatus();
        }
    });
}

function initStatus() {
    if (pkId) {
        $.ajaxSubmit(view_url, {
            'orderId' : pkId
        }, function(rtn) {
            if (rtn.success) {
                // 初始化页面的值
                initViewsData(rtn.data)
                // 初始化产品详细table
                initProductDetailTable(rtn.data);
                // 初始化客户订单历史记录
                initOrderDealRecord();
            } else {
                Q_Alert_Fail(data.message);
            }
        });
    } else {
        Q_Alert_Fail('参数错误');
    }
}

/**
 * 初始化页面的值
 */
function initViewsData(data) {
    $.dataInput($('.form-control-static'), data);
    if (data.orderType == 2) {
        $(".pOIVFlowPlus").css("display", "block");
        $("p[name='orderType']").text("流量+产品");
    } else {
        $(".pOIVFlowPlus").css("display", "none");
        $("p[name='orderType']").text("流量包");
    }
    $("#poiv-customerName").html($.htmlspecialchars(data.customerName));
}

/**
 * 初始化产品详细table
 */
function initProductDetailTable(data) {
    prodDetailTable = $(mpTableId).dataTable({
        "iDisplayLength": 99,
        "bDeferRender":true,
        "bLengthChange":false,
        "bInfo":false,
        "bPaginage" : false,
        "bSort" : false,
        "bFilter" : false,
        "bDestroy" : true,
        "sScrollY":"",
        "aaData" : data.orderDetailList,
        "oLanguage" : {
            "oPaginate" : {
                "sNext" : "",
                "sPrevious" : ""
            },
            "sEmptyTable" : "无产品记录",
            "sInfoEmpty" : "",
            "sLoadingRecords" : "请稍后，正在加载中...",
        },
        "aoColumns": [ {
            mData : "productName",
            mRender : function(value, type, rowData) {
                return "<label value='" + rowData.productId + "' >" + $.htmlspecialchars(value) + "</label>";
            }
        }, {
            mData : "amount",
            mRender : function(value, type, rowData) {
                return "<label value='"+ value +"' >" + commaDelimited(value) + "</label>";
            }
        }, {
            mData : "price",
            mRender : function(value, type, rowData) {
                if (value != null) {
                    return "<label>￥" + commaDelimited(value) + "</label>";
                } else {
                    return "";
                }
            }
        }, {
            mData : "settlementPrice",
            mRender : function(value, type, rowData) {
                if (value != null) {
                    return "<label>￥" + commaDelimited(value) + "</label>";
                } else {
                    return "";
                }
            }
        }],
    });
}

function initOrderDealRecord() {
    if (!pkId) {
        Q_Alert_Fail("没有获取到客户订单");
    }
    $.ajax({
        url : ctxPaths + '/orderDealRecord/getOrderDealRecord.ajax?sourceId=' + pkId + '&type=2',
        type : 'get',
        dataType : 'json',
        success : function(data) {
            if (data.success) {
                initOrderDealRecordTable(data.data);
            } else {
                Q_Alert_Fail(data.message);
            }

        }
    });
}

function initOrderDealRecordTable(data) {
    $(orderRecordTableId).dataTable({
        "iDisplayLength": 99,
        "bDeferRender":true,
        "bLengthChange":false,
        "bInfo":false,
        "bPaginage" : false,
        "bSort" : false,
        "bFilter" : false,
        "bDestroy" : true,
        "sScrollY":"",
        "aaData" : data,
        "oLanguage" : {
            "oPaginate" : {
                "sNext" : "",
                "sPrevious" : ""
            },
            "sEmptyTable" : "无订单历史修改记录",
            "sInfoEmpty" : "",
            "sLoadingRecords" : "请稍后，正在加载中...",
        },
        "aoColumns": [ {
            mData : "inputTime"
        }, {
            mData : "dealRecordId",
            mRender : function(value, type, rowData) {
                return "<label><a href='javascript:;' onclick='downloadEvent(" + rowData.dealRecordId + ")'>" 
                        + rowData.inputTime.substr(0, 10) + " 修改记录</a>" 
                        + "</label>";
            }
        }, {
            mData : "creator"
        }],
    });
}

// 下载订单处理记录
function downloadEvent(dealRecordId) {
    $('#add-form').exportData({
        url: ctxPaths + '/orderDealRecord/download.ajax',
        data: {
            dealRecordId: dealRecordId
        },
        callback: function(data) {},
        failure: function(form, action) {}
    });
}

/**
 * 逗号分隔
 * @param num
 * @returns
 */
function commaDelimited(num) {
    return String(num).replace(/(\d)(?=(\d\d\d)+(?!\d))/g, '$1,');
}