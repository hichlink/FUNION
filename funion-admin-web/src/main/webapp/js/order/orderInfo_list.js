var list_url = ctxPaths + '/orderInfo/query.ajax';
var grid_selector = "#grid-table";
var pager_selector = "#grid-pager";
var isWYAdmin = false;
var isMarketing = false;

$(function() {
    pOILInitStatus();
    pOILSetListener();
});

/**
 * 状态初始化
 */
function pOILInitStatus() {
    // 初始化【客戶名称】下拉列表
    pOILInitCustomerNameDDL();
    // 输入框限制
    inputNumberFormatHandler();
}

/**
 * 初始化【客戶名称】下拉列表
 */
function pOILInitCustomerNameDDL() {
    $.ajax({
        url : ctxPaths + '/orderInfo/selectByPartnerId.ajax',
        dataType : 'json',
        success : function(data, status) {
            if (data.errMsg) {
                Q_Alert_Fail(data.errMsg);
            } else {
                isWYAdmin = data.isWYAdmin;
                isMarketing = data.isMarketing;

                // 初始化产品订单列表
                pOILInitProdOrderList();
                // 初始化select2组件
                initSelect2(data);
            }
        }
    });
}

/**
 * 初始化产品订单列表
 */
function pOILInitProdOrderList() {
    jqGrid_init($(grid_selector), pager_selector, {
        url : list_url,
        sortable : true,
        sortname : 'create_time',
        sortorder : 'desc',
        colNames : [ '订单编号', '客户名称', '合作伙伴', '合作类型', '订单类型', '创建时间', '订单状态', '操作'],
        colModel : [ {
            name : 'orderId',
            index : 'orderId',
            sortable : true,
            sortname : 'order_id',
            formatter : formatOrderId
        }, {
            name : 'customerName',
            index : 'customerName',
            sortable : true,
            sortname : 'customer_name',
            width : 100,
            formatter: formatCustomerName
        }, {
            name : 'partnerName',
            index : 'partnerName',
            sortable : true,
            sortname : 'partner_name',
            width : 100,
        }, {
            name : 'partnerTypeDesc',
            index : 'partnerTypeDesc',
            sortable : true,
            sortname : 'partner_type',
            width : 80,
        }, {
            name : 'orderType',
            index : 'orderType',
            sortable : true,
            sortname : 'order_type',
            width : 100,
            formatter : formatOrderType
        }, {
            name : 'createTime',
            index : 'createTime',
            sortable : true,
            sortname : 'create_time',
            width : 100
        }, {
            name : 'status',
            index : 'status',
            sortable : true,
            sortname : 'status',
            width : 60,
            formatter : formatOrderStatus
        }, {
            name : 'myac',
            index : '',
            width : 160,
            fixed : true,
            sortable : false,
            resize : false,
            formatter : formatActionButtons,
        }]
    });
    function formatCustomerName(cellvalue, options, rowObject) {
        return $.htmlspecialchars(cellvalue);
    }
    function formatOrderId(cellvalue, options, rowObject) {
        return '<a href="javascript:;" onclick="viewEvent(\''
                + cellvalue + '\')">' + cellvalue + '</a>';
    }
    function formatOrderType(cellvalue, options, rowObject) {
        if (cellvalue == 1) {
            return "流量包";
        } else if (cellvalue == 2) {
            return "流量+产品";
        }
    }
    function formatOrderStatus(cellvalue, options, rowObject) {
        if (cellvalue == 1) {
            return "待生效";
        } else if (cellvalue == 2) {
            return "生效";
        } else if (cellvalue == 3) {
            return "暂停";
        }
    }
    function formatActionButtons(cellvalue, options, rowObject) {
        var btnHtml = "<div>";
        // 需求变更：管理员和合作伙伴都可以改变订单状态 start
        var orderStatusTemp = rowObject['status'];
        var btnTitle = "";
        var changeStatus = 0;
        if (orderStatusTemp == 1) {
            btnTitle = "生效";
            changeStatus = 2;
        } else if (orderStatusTemp == 2) {
            btnTitle = "暂停";
            changeStatus = 3;
        } else if (orderStatusTemp == 3) {
            btnTitle = "生效";
            changeStatus = 2;
        }
        btnHtml += '<button onclick=\"pOILOrderTypeBtnClickHandler('+ changeStatus + ',' + rowObject['orderId'] + ')\" class=\"btn btn-xs btn-warning\" permCheck=\"auth_order_manager_list,changestatus,hidden\" data-rel=\"tooltip\" title=\"' + btnTitle + '\" ><i class=\"ace-icon fa fa-flag bigger-120\"></i></button>';
        // 需求变更：管理员和合作伙伴都可以改变订单状态 end
        
        btnHtml += '<button onclick=\"viewEvent(' + rowObject['orderId'] + ')\" class=\"btn btn-xs btn-success\" permCheck=\"auth_order_manager_list,detail,hidden\" data-rel=\"tooltip\" title=\"详情\" ><i class=\"ace-icon fa  fa-eye bigger-120\"></i></button>';
        if (rowObject.status != 2) {
            btnHtml += '<button onclick=\"pOILModifyBtnClickHandler(' + rowObject['orderId'] + ')\" class=\"btn btn-xs btn-info\" permCheck=\"auth_order_manager_list,modify,hidden\" data-rel=\"tooltip\" title=\"修改\" ><i class=\"ace-icon fa fa-pencil bigger-120\"></i></button>';
        }
        btnHtml += '<button onclick=\"deleteEvent(' + rowObject['orderId'] + ')\"'
        +    ' class=\"btn btn-xs btn-danger\" permCheck=\"auth_order_manager_list,delete,hidden\"'
        +    ' data-rel=\"tooltip\" title=\"删除\" >'
        +      '<i class=\"ace-icon fa fa-trash-o bigger-120\"></i>'
        + '</button>'
        btnHtml += "</div>";
        return btnHtml;
    }

    $('#seachBtn').on('click', function() {
        $(grid_selector).jqGrid('setGridParam', {
            postData : $('#queryForm').serializeJson(),
            page : 1
        }).trigger("reloadGrid");
    });
    $('#resetBtn').on('click', function() {
        $("#pOIL-customer-select").select2("val", "");
        resetForm($('#queryForm'));
    });
}

/**
 * 初始化select2组件
 * @param data
 */
function initSelect2(data) {
    $("#pOIL-customer-select").removeClass().css("width","240px").select2({
        minimumInputLength: 1,
        ajax: {
            url: ctxPaths + '/orderInfo/selectCustomerInfoByName.ajax',
            dataType: 'json',
            data: function (term) {
                return {
                    "customerName": term
                };
            },
            results: function (data) {
                return {
                    results: $.map(data.customerList, function (item) {
                        return {
                            id: item.customerId,
                            text: item.customerName
                        }
                    })
                };
            }
        }
    });
}

/**
 * 事件监听器
 */
function pOILSetListener() {
    // 新增订单 按钮点击事件
    pOILAddOrderBtnClickHandler();
    // 导出 按钮点击事件
    pOILExportOrderBtnClickHandler();
    // 价格设置 按钮点击事件
    pOILPriceSetBtnClickHandler();
}

/**
 * 新增订单 按钮点击事件
 */
function pOILAddOrderBtnClickHandler() {
    $('#pOILAddOrderBtn').click(function() {
        if (isWYAdmin || isMarketing) {
            $("#pOILOrderModal").modal({
                backdrop: "static",
                keyboard: false
            });
            $("#pOILOrderModal").on("shown.bs.modal", function (e) {
                pOILFlowPackageBtnClickHandler();
                pOILFlowPlusBtnClickHandler();
            });
        } else {
            window.location.href = ctxPaths + '/pages/orderInfo_add.shtml?orderType=1';
        }
    });
}

function pOILFlowPackageBtnClickHandler() {
    $('#pOILFlowPackageBtn').click(function() {
      window.location.href = ctxPaths + '/pages/orderInfo_add.shtml?orderType=1';
    });
}
function pOILFlowPlusBtnClickHandler() {
    $('#pOILFlowPlusBtn').click(function() {
        window.location.href = ctxPaths + '/pages/orderInfo_add.shtml?orderType=2';
    });
}

/**
 * 修改 按钮点击事件
 */
function pOILModifyBtnClickHandler(id) {
    window.location.href = ctxPaths + '/pages/orderInfo_add.shtml?orderId=' + id;
};

/**
 * 暂停/生效 按钮点击事件
 */
function pOILOrderTypeBtnClickHandler(status, orderId) {
    $.ajax({
        url: ctxPaths + '/orderInfo/changeStatus.ajax?status=' + status + '&orderId=' + orderId,
        dataType: "json",
        success: function(data) {
            if (data.success == true) {
                $(grid_selector).trigger("reloadGrid");
            } else {
                Q_Alert_Fail(data.message);
            }
        }
    });
}

function viewEvent(id) {
    window.location.href = ctxPaths + '/pages/orderInfo_view.shtml?orderId=' + id;
};

function deleteEvent(id) {
    Q_Confirm("是否要删除？", function(result) {
        if (result) {
            $.ajax({
                url: ctxPaths + '/orderInfo/changeStatus.ajax?status=4&orderId=' + id,
                dataType: "json",
                success: function(data) {
                    if (data.success == true) {
                        $(grid_selector).trigger("reloadGrid");
                    } else {
                        Q_Alert_Fail(data.message);
                    }
                }
            });
        }
    });
};

/**
 * 导出 按钮点击事件
 */
function pOILExportOrderBtnClickHandler() {
    $('#pOILExportOrderBtn').click(function() {
        $('#queryForm').exportData({
            url: ctxPaths + '/orderInfo/downLoadOrder.ajax',
            callback: function(data) {
//                if (!data.success) {
//                    Q_Alert(data.data.msg, undefined, true, function() {
//                    });
//                }
            },
            failure: function(form, action) {
//                Q_Alert_Fail(rtn.data.msg, undefined, false);
            }
        });
    });
}

/**
 * 价格设置 按钮点击事件
 */
function pOILPriceSetBtnClickHandler() {
    $('#pOILPriceSetBtn').click(function() {
        window.location.href = ctxPaths + '/pages/productPriceSetting.shtml';
    });
}

/**
 * 文本框输入限制
 */
function inputNumberFormatHandler() {
    $(".numberFormat").keypress(function(event) {
        var keyCode = event.which;
        if (keyCode == 8 || keyCode == 0 || keyCode >= 48 && keyCode <= 57)
            return true;
        else
            return false;
    });
}