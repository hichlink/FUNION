var update_url = ctxPaths + '/orderInfo/add.ajax';
var view_url = ctxPaths + '/orderInfo/get.ajax';
var check_url = ctxPaths + '/orderInfo/checkAllowBillIssued.ajax';
var pkId = getP('orderId');
var pOIAOrderType = getP('orderType');

var prodOrderList = null;
var prodOrderTable = null;
var mpTableId = "#product-order-table";
var potTableTd = "#prod-directcharge-table";
var potOrderTable = null;
var prodData = null;
var isMarketing = false;
var isWYAdmin = false;
// 是否允许下发计费
var isBillIssued = false;
var leftSel = $("#dltSource"); 
var rightSel = $("#dltTarget");

$(function() {
    initStatus();
    setListener();
});

/**
 * 状态初始化
 */
function initStatus() {
    // 初始化订单类型是流量包或流量+
    initFlowPackageOrPlus();
    // 初始化订单编号
    initOrderNumber();
    // 初始化【客戶名称】下拉列表
    initCustomerNameDDL();
    // 初始化流量包产品订单table
    initProductOrderTable();
    setAllTotalPrice();
}

/**
 * 初始化订单类型是流量包或流量+
 */
function initFlowPackageOrPlus() {
    if (pOIAOrderType == 1) {
        $(".flowPackageDiaplay").css("display", "block");
        $(".flowPlusDiaplay").css("display", "none");
        $(".flowplus-tbl-container").css("display", "none");
        $("#poia-price-total").css("display", "none");
    } else if (pOIAOrderType == 2) {
        $(".flowPackageDiaplay").css("display", "none");
        $(".flowPlusDiaplay").css("display", "block");
        $(".flowplus-tbl-container").css("display", "block");
        $("input[type='radio']:last").prop("checked", true);
        $("#poia-price-total").css("display", "block");
    }
}

/**
 * 初始化订单编号
 */
function initOrderNumber() {
    if (pkId) {
        $("#orderNumberLbl").text(pkId);
    } else {
        $.ajax({
            url : ctxPaths + '/orderInfo/getOrderNumber.ajax',
            dataType : 'json',
            success : function(data, status) {
                $("#orderNumberLbl").text(data);
            }
        });
    }
}

/**
 * 初始化【客戶名称】下拉列表
 */
function initCustomerNameDDL() {
    $.ajax({
        url : ctxPaths + '/orderInfo/selectByPartnerId.ajax',
        dataType : 'json',
        success : function(data, status) {
            if (data.errMsg) {
                Q_Alert_Fail(data.errMsg);
            } else {
                isWYAdmin = data.isWYAdmin;
                isMarketing = data.isMarketing;
                isBillIssued = data.isBillIssued;
                // 初始化select2组件
                initSelect2(data);
                initDisplayStatus();
                // 更新订单的情况下的初始化
                initUpdateViews();
            }
        }
    });
}

/**
 * 初始化select2组件
 * @param data
 */
function initSelect2(data) {
    $("#customer-select").removeClass().css("width","240px").select2({
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
    /**
     * 动态判断客户所属合作伙伴是否允许下发计费
     * (管理员并且是流量加订单)
     */
    if (isWYAdmin && $("input[type=radio]:checked").val() == 3) {
        $("#customer-select").on("change", function() {
            var $this = $(this);
            $.ajaxSubmit(check_url, {
                'customerId': $this.val()
            }, function(rtn) {
                isBillIssued = rtn.isBillIssued;
                if (isBillIssued) {
                    $(".flowPlusAndBillTypeIssued").show();
                    if (pkId) {
                        $(".not-update").attr("disabled", "disabled");
                    }
                } else {
                    $(".flowPlusAndBillTypeIssued").hide();
                }
            });
        });
    }
}

function initDisplayStatus() {
    if (isMarketing || isWYAdmin) {
        $(".radioGroupDisplay").css("display", "block");
    } else {
        $(".radioGroupDisplay").css("display", "none");
        $(".marketingDisplay").css("display", "none");
        $(".directChargeDisplay").css("display", "block");
    }
    // 是流量加订单并且允许下发计费
    if ($("input[type=radio]:checked").val() == 3 && isBillIssued) {
        $(".flowPlusAndBillTypeIssued").show();
        if (pkId) {
            $(".not-update").attr("disabled", "disabled");
        }
    }
}

/**
 * 初始化产品订单table
 */
function initProductOrderTable() {
    prodOrderTable = $(mpTableId).dataTable({
        "iDisplayLength": 99,
        "bDeferRender":true,
        "bLengthChange":false,
        "bInfo":false,
        "bPaginage" : false,
        "bSort" : false,
        "bFilter" : false,
        "bDestroy" : true,
        "sScrollY":"",
        "aaData" : prodOrderList,
        "oLanguage" : {
            "oPaginate" : {
                "sNext" : "",
                "sPrevious" : ""
            },
            "sEmptyTable" : "请选择要订购的产品",
            "sInfoEmpty" : "",
            "sLoadingRecords" : "请稍后，正在加载中...",
        },
        "aoColumnDefs": [ {//隐藏列
            "bVisible": $("input[type=radio]:checked").val() == 3,
            "aTargets": [ 1 ]
        } ],
        "aoColumns": [ {
            mData : "productName",
            mRender : function(value, type, rowData) {
                return "<a href='javascript:void(0);' value='" + rowData.productId + "' class='product-name-detail'>" + $.htmlspecialchars(value) + "</a>";
            }
        }, {
            mData : "productId",
            mRender : function(value, type, rowData) {
                return "<input attrid='" + value + "' name='productCount' class='numberFormat prodCountInput' value='"+ rowData.productCount +"' />";
            }
        }, {
            mData : "productId",
            mRender : function(value, type, rowData) {
                return "<input attrid='" + value + "' name='productPrice' class='numberFormat settlementAmountInput' value='" + rowData.settlementAmount + "' />";
            }
        }, {
            mData : "productId",
            mRender : function(value, type, rowData) {
                var deleteHtml = "<button class='btn btn-danger btn-sm deleteBtn' attrid='" + value + "'><i class='ace-icon fa fa-trash-o fa icon-only'></i></button>"
                return deleteHtml;
            }
        }],
        "fnRowCallback" : rowCallback,
    });
    
    potOrderTable = $(potTableTd).dataTable({
        "iDisplayLength": 99,
        "bDeferRender":true,
        "bLengthChange":false,
        "bInfo":false,
        "bPaginage" : false,
        "bSort" : false,
        "bFilter" : false,
        "bDestroy" : true,
        "sScrollY":"",
        "aaData" : prodOrderList,
        "oLanguage" : {
            "oPaginate" : {
                "sNext" : "",
                "sPrevious" : ""
            },
            "sEmptyTable" : "请选择要订购的产品",
            "sInfoEmpty" : "",
            "sLoadingRecords" : "请稍后，正在加载中...",
        },
        "aoColumns": [ {
            mData : "productName",
            mRender : function(value, type, rowData) {
                return "<a href='javascript:void(0);' value='" + rowData.productId + "' class='product-name-detail'>" + $.htmlspecialchars(value) + "</a>";
            }
        }, {
            mData : "productId",
            mRender : function(value, type, rowData) {
                return "<input attrid='" + value + "' name='podSettlementAmount' class='numberFormat podSettlementAmountInput' value='" + rowData.settlementAmount + "'/>";
            }
        }, {
            mData : "productId",
            mRender : function(value, type, rowData) {
                return "<input attrid='" + value + "' name='podProductPrice' class='numberFormat podProductPriceInput' value='" + rowData.productPrice + "'/>";
            }
        }, {
            mData : "productId",
            mRender : function(value, type, rowData) {
                var deleteHtml = "<button class='btn btn-danger btn-sm deleteBtn' attrid='" + value + "'><i class='ace-icon fa fa-trash-o fa icon-only'></i></button>"
                return deleteHtml;
            }
        }],
        "fnRowCallback" : rechargeRowCallback,
    });
}

function rowCallback(nRow, aData, iDisplayIndex) {
    setTimeout(function() {
        dtProdCountChangeHandler(nRow, aData, iDisplayIndex);
        dtProdPriceChangeHandler(nRow, aData, iDisplayIndex);
    });
}

/**
 * 【订购数量】改变
 * @param nRow
 * @param aData
 * @param iDisplayIndex
 */
function dtProdCountChangeHandler(nRow, aData, iDisplayIndex) {
    var txt = $(nRow).find(".prodCountInput");
    updateDateSource(txt, "count");
}

/**
 * 【订购价格】改变
 * @param nRow
 * @param aData
 * @param iDisplayIndex
 */
function dtProdPriceChangeHandler(nRow, aData, iDisplayIndex) {
    var txt = $(nRow).find(".settlementAmountInput");
    updateDateSource(txt, "price");
}

/**
 * 更新价格数据
 * @param $changeInput
 * @param changeFlag
 */
function updateDateSource($changeInput, changeInputFlag) {
    $changeInput.off("change").on("change", function(e) {
        var val = $(this).val();
        var $this = $(this);
        $.each(prodOrderList, function(n, value) {
            if ($this.attr("attrid") == value.productId) {
                if (changeInputFlag == "count") {
                    if (!isNum(val)) {
                        value.productCount = 0;
                    } else {
                        if (val.length > 38) {
                            Q_Alert_Fail("请输入适当的数量");
                            return;
                        } else {
                            value.productCount = parseInt(val, 10);
                        }
                    }
                } else if (changeInputFlag == "price") {
                    if (!isNum(val)) {
                        value.settlementAmount = 0;
                    } else {
                        value.settlementAmount = parseFloat(val);
                    }
                }
                value.productTotalPrice = floatMul(value.productCount, value.settlementAmount);
                return false;
            }
        });
        prodOrderTable.fnClearTable();
        refreshProdOrderTable();
    });
}

/* 直充客户订单 START*/
function rechargeRowCallback(nRow, aData, iDisplayIndex) {
    setTimeout(function() {
        dtPODSettlementAmountChangeHandler(nRow, aData, iDisplayIndex);
        dtPODProdPriceChangeHandler(nRow, aData, iDisplayIndex);
    });
}

/**
 * 【结算价格】改变
 * @param nRow
 * @param aData
 * @param iDisplayIndex
 */
function dtPODSettlementAmountChangeHandler(nRow, aData, iDisplayIndex) {
    var txt = $(nRow).find(".podSettlementAmountInput");
    podUpdateDateSource(txt, "settlementAmount");
}

/**
 * 【销售价格】改变
 * @param nRow
 * @param aData
 * @param iDisplayIndex
 */
function dtPODProdPriceChangeHandler(nRow, aData, iDisplayIndex) {
    var txt = $(nRow).find(".podProductPriceInput");
    podUpdateDateSource(txt, "productPrice");
}

/**
 * 更新数据
 * @param $changeInput
 * @param changeFlag
 */
function podUpdateDateSource($changeInput, changeInputFlag) {
    $changeInput.off("change").on("change", function(e) {
        var val = $(this).val();
        var $this = $(this);
        $.each(prodOrderList, function(n, value) {
            if ($this.attr("attrid") == value.productId) {
                if (changeInputFlag == "productPrice") {
                    if (!isNum(val)) {
                        value.productPrice = 0;
                    } else {
                        value.productPrice = parseFloat(val);
                    }
                } else if (changeInputFlag == "settlementAmount") {
                    if (!isNum(val)) {
                        value.settlementAmount = 0;
                    } else {
                        value.settlementAmount = parseFloat(val);
                    }
                }
                return false;
            }
        });
        prodOrderTable.fnClearTable();
        refreshProdOrderTable();
    });
}
/* 直充客户订单 END*/

/**
 * 设置事件监听
 */
function setListener() {
    // 【添加产品】按钮点击事件
    chooseProductBtnClickHandler();
    // 订单提交
    orderSubmitBtnClickHandler();
    // radioButton点击事件
    radioBtnClickHandler();
    // 双向选择按钮组点击事件
    doubleSelectBtnClick();
    // 返回按钮点击事件
    poiaBackBtnClickHandler();
}

/**
 * 【添加产品】按钮点击事件
 */
function chooseProductBtnClickHandler() {
    $("#choose-product").on('click', function(e) {
        e.preventDefault();
        // 取得合作伙伴对应的产品
        // 构造参数
        var radioProdType = $("input[type=radio]:checked").val();
        var customerId = $("#customer-select").val();
        if (customerId == null || customerId == "") {
            Q_Alert_Fail("请选择客户名称");
            return;
        }
        var url = "";
        var prodIds = [];
        if (prodOrderList != null) {
            $.each(prodOrderList, function(n, value) {
                prodIds.push(value.productId);
            });
        }
        
        if (isMarketing || isWYAdmin) {
            url = ctxPaths + '/flowProductInfo/flowProductInfo.ajax?productType='+ radioProdType + '&customerId=' + customerId;
        } else {
            url = ctxPaths + '/flowProductInfo/flowProductInfo.ajax?customerId=' + customerId;
        }
        $.ajax({
            url : url,
            dataType : 'json',
            success : function(data, status) {
                if (data.success == true) {
                    var prodDataDB = data.data.concat();
                    if (prodOrderList != null) {
                        for (var i = prodDataDB.length-1 ; i >= 0; i--) {
                            if ($.inArray(prodDataDB[i].productId, prodIds) >= 0) {
                                prodDataDB.splice(i, 1);
                            }
                        }
                    }
                    prodData = prodDataDB;
                    if (isMarketing || isWYAdmin) {
                        showPartnerProdDialog();
                    } else {
                        if (data.data.length == 0) {
                            Q_Alert_Fail("未找到任何产品，原因可能如下：<br>1. 当前合作伙伴未关联产品。<br>2. 同一客户下流量包产品只能生成一次订单。");
                            return;
                        }
                        if (prodOrderList != null) {
                            prodOrderList = prodOrderList.concat(prodData);
                        } else {
                            prodOrderList = prodData; 
                        }
                        $.each(prodOrderList, function(n, value) {
                            value.productTotalPrice = floatMul(value.productCount, value.settlementAmount != null ? value.settlementAmount : 0);
                        });
                        refreshProdOrderTable();
                    }
                }
            }
        });
    });
}

function showPartnerProdDialog() {
    var dialog = $("#partner-prod-dialog").removeClass('hide').dialog({
        modal: true,
        position: ["20%",50],
        width: "60%",
        resizable: false,
        buttons: [ 
            {
                text: "取消",
                "class": "btn btn-xs",
                click: function() {
                    $(this).dialog("close");
                }
            },
            {
                text: "添加",
                "class" : "btn btn-primary btn-xs",
                click: function() {
                    prodAddBtnClickHandler();
                } 
            }
        ]
    });
    // 初始化双向选择列表
    initDoubleSelect();
    
}

/**
 * 添加按钮点击事件
 */
function prodAddBtnClickHandler() {
    var selVal = []; 
    rightSel.find("option").each(function() { 
        selVal.push(this.value); 
    });
    if (selVal.length == 0) {
        Q_Alert_Fail("请选择产品");
        return;
    }
    var prodDataTemp = prodData;
    // 初始化产品订单列表
    prodOrderSelectedList = $.getSelectInfo(selVal, prodDataTemp);
    if (prodOrderList != null) {
        prodOrderList = prodOrderList.concat(prodOrderSelectedList);
    } else {
        prodOrderList = prodOrderSelectedList; 
    }
    $("#partner-prod-dialog").dialog("close"); 
    $.each(prodOrderList, function(n, value) {
        value.productTotalPrice = floatMul(value.productCount, value.settlementAmount != null ? value.settlementAmount : 0);
    });
    refreshProdOrderTable();
    
}

$.getSelectInfo = function(selVal, prodDataTemp) {
    var arr = new Array();
    $.each(selVal, function(index, id) {
        $.each(prodDataTemp, function(n, value) {
            if (id == value.productId) {
                arr.push(value);
                return false;
            }
        });
        return true;
    });
    return arr;
};

/**
 * 刷新产品订单表
 */
function refreshProdOrderTable() {
    // 刷新产品订单Table
    initProductOrderTable();
    // 产品名称详情点击事件
    productNameLinkClickHandler();
    // 文本框输入限制
    inputNumberFormatHandler();
    // 删除按钮点击事件
    deleteBtnClickHandler();
    // 设置合计金额
    setAllTotalPrice();
}

/**
 * 初始化双向选择列表
 */
function initDoubleSelect() {
    var radioProdType = $("input[type=radio]:checked").val();
    if (radioProdType == 1) {
        $("#poia-flow-type").html("基础流量包");
    } else if (radioProdType == 2) {
        $("#poia-flow-type").html("组合流量包");
    } else if (radioProdType == 3) {
        $("#poia-flow-type").html("流量+产品");
    }
    $("#dltSource").empty();
    $("#dltTarget").empty();
    $.each(prodData, function(n, value) {
        $("#dltSource").append("<option value='" + value.productId + "'>" + value.productName + "</option>")
    });
}

/**
 * 订单提交
 */
function orderSubmitBtnClickHandler() {
    $("#submitButton").click(function() {
        var radioProdType = $("input[type=radio]:checked").val();
        $.validator.addMethod("deliveryTimeRequired", function(value, element, params) {
            if (radioProdType == 3 && (value == "" || value == null)) {
                return false;
            } else {
                return true;
            }
        }, "请输入交货日期");
        var add_validator = $('#add-form').validate({
            rules: {
                'deliveryTime': {
                    maxlength: 20,
                }
            },
            submitHandler: function(form) {
                if (isBillIssued && $("#billing-type-select").val() == "") {
                    Q_Alert_Fail("请选择计费方式");
                    return;
                }
                var finalProdList = prodOrderList;
                if (finalProdList == null || finalProdList.length == 0) {
                    Q_Alert_Fail("请选择要订购的产品");
                    return;
                } else {
                    var successFlag = true;
                    $.each(finalProdList, function(n, value) {
                        if (isMarketing || isWYAdmin) {
                            var isInputEmpty = value.settlementAmount == null || value.productCount == null;
                            if (isInputEmpty) {
                                Q_Alert_Fail("请输入订购数量或结算价格");
                                successFlag = false;
                                return false;
                            }
                            if ($("input[type=radio]:checked").val() == 3 && value.productCount <= 0) {
                                Q_Alert_Fail("订购数量必须为大于0的整数，请重新输入。");
                                successFlag = false;
                                return false;
                            }
                            var doubleReg = /^\d{1,8}\.?\d{0,2}$/;
                            if (!doubleReg.test(value.settlementAmount) || value.settlementAmount.length > 8) {
                                Q_Alert_Fail("最多只能有八位整数和两位小数，请重新输入。");
                                successFlag = false;
                                return false;
                            }
                        } else {
                            var isInputEmpty = value.settlementAmount == null || value.productPrice == null ||
                                            value.settlementAmount == 0 || value.productPrice == 0;
                            if (isInputEmpty) {
                                Q_Alert_Fail("请输入订购数量或结算价格(不能为0)");
                                successFlag = false;
                                return false;
                            }
                            var doubleReg = /^\d{1,8}\.?\d{0,2}$/;
                            if (!doubleReg.test(value.settlementAmount) || !doubleReg.test(value.productPrice) ||
                                    value.settlementAmount.length > 8 || value.productPrice.length > 8) {
                                Q_Alert_Fail("最多只能有八位整数和两位小数，请重新输入。");
                                successFlag = false;
                                return false;
                            }
                            if ((value.productPrice + "").indexOf(".") != -1) {
                                var reg = /^\d+\.+\d{1,2}$/;
                                if (!reg.test(value.productPrice)) {
                                    Q_Alert_Fail("最多只能有两位小数，请重新输入。");
                                    successFlag = false;
                                    return false;
                                }
                            }
                        }
                        // 两位小数验证
                        if ((value.settlementAmount + "").indexOf(".") != -1) {
                            var reg = /^\d+\.+\d{1,2}$/;
                            if (!reg.test(value.settlementAmount)) {
                                Q_Alert_Fail("最多只能有两位小数，请重新输入。");
                                successFlag = false;
                                return false;
                            }
                        }
                    });
                    if (successFlag) {
                        // 提交订单
                        var orderInfo = {};
                        createOrderInfo(orderInfo, finalProdList);
                        $.ajax({
                            url : update_url,
                            type : "post",
                            contentType : "application/json; charset=UTF-8",
                            cache : false,
                            dataType : 'json',
                            data: JSON.stringify(orderInfo),
                            success : function(data, status) {
                                if (data.success == true) {
                                    Q_Alert('操作成功', function() {
                                        location.href = ctxPaths + '/pages/orderInfo_list.shtml';
                                    });
                                } else {
                                    Q_Alert_Fail(data.message);
                                }
                            }
                        });
                        return false;
                    }
                }
            },
        });
    });
}

/**
 * 构造订单信息对象
 */
function createOrderInfo(orderInfo, finalProdList) {
    if (pkId) {
        orderInfo.isUpdate = true;
        orderInfo.status = 1;
        orderInfo.orderId = pkId;
    } else {
        orderInfo.status = 2;
        orderInfo.orderId = parseInt($("#orderNumberLbl").text(), 10);
    }
    orderInfo.customerId = parseInt($("#customer-select").val(), 10);
    var radioVal = $("input[type=radio]:checked").val();
    orderInfo.flowProductInfoList = finalProdList;
    orderInfo.orderType = radioVal == 3 ? 2 : 1;
    orderInfo.priceTotal = parseFloat($("#allTotalPriceHidden").val());
    var deliveryTime = $("#delivery-date-picker").val();
    if (deliveryTime != "") {
        deliveryTime = deliveryTime.replace(/-/g, "/");
        orderInfo.deliveryTime = new Date(deliveryTime);
    }
    // 流量包订单
    if ($("input[type=radio]:checked").val() != 3) {
        // 默认下发计费
        orderInfo.billingType = 2;
    } else {
        // 允许下发计费
        if (isBillIssued) {
            orderInfo.billingType = parseInt($("#billing-type-select").val(), 10);
        } else {
            // 激活计费
            orderInfo.billingType = 1;
        }
    }
}

/**
 * 文本框输入限制
 */
function inputNumberFormatHandler() {
    $(".numberFormat").keypress(function(event) {
        var keyCode = event.which;
        if ($(this).attr("name") == "productCount") {
            if (keyCode == 8 || keyCode == 0 || keyCode >= 48 && keyCode <= 57)
                return true;
            else
                return false;
        } else {
            if (keyCode == 8 || keyCode == 0 || keyCode == 46 || (keyCode >= 48 && keyCode <= 57))
                return true;
            else
                return false;
        }
    });
}

/**
 * 删除按钮点击事件
 */
function deleteBtnClickHandler() {
    $(".deleteBtn").click(function() {
        var $btn = $(this);
        $.each(prodOrderList, function(n, value) {
            if ($btn.attr("attrid") == value.productId) {
                prodOrderList.splice(n, 1);
                return false;
            }
        });
        refreshProdOrderTable();
    });
}

/**
 * radioButton点击事件
 */
function radioBtnClickHandler() {
    $("input[type=radio]").change(function() {
        setAllTotalPrice();
        var radioVal = $("input[type=radio]:checked").val();
        if (radioVal == 3) {
            $(".flowplus-tbl-container").css("display", "block");
            $("#poia-price-total").css("display", "block");
        } else {
            $(".flowplus-tbl-container").css("display", "none");
            $("#poia-price-total").css("display", "none");
        }
    })
}

/**
 * 双向选择按钮组点击事件
 */
function doubleSelectBtnClick() {
    $("#toRight").click(function() {
        leftSel.find("option:selected").each(function() {
            $(this).remove().appendTo(rightSel);
        });
    });
    $("#toLeft").click(function() {
        rightSel.find("option:selected").each(function() {
            $(this).remove().appendTo(leftSel);
        });
    });
    leftSel.dblclick(function() {
        $(this).find("option:selected").each(function() {
            $(this).remove().appendTo(rightSel);
        });
    });
    rightSel.dblclick(function() {
        $(this).find("option:selected").each(function() {
            $(this).remove().appendTo(leftSel);
        });
    });
}

/**
 * 返回按钮点击事件
 */
function poiaBackBtnClickHandler() {
    $(".poia-back-btn").click(function(e) {
        location.href = ctxPaths + '/pages/orderInfo_list.shtml';
    });
}

/**
 * 产品名称详情点击事件
 */
function productNameLinkClickHandler() {
    $(".product-name-detail").click(function(e) {
        var flowProductId = $(this).attr("value");
        $.ajax({
            url : ctxPaths + '/flowProductInfo/get.ajax?productId=' + flowProductId,
            cache : false,
            dataType : 'json',
            success : function(data, status) {
                if (data.success == true) {
                    showProdDetailDialog(e);
                    initProdDetail(data.data);
                }
            }
        });
    });
}

/**
 * 显示产品详细对话框
 * @param e
 */
function showProdDetailDialog(e) {
    e.preventDefault();
    var dialog = $("#prod-detail-dialog").removeClass('hide').dialog({
        modal: true,
        position: [ "25.3%", 50 ],
        width: "50%",
        resizable: false,
        buttons: [ {
            text: "关闭",
            "class": "btn btn-primary btn-xs",
            click: function() {
                $(this).dialog("close");
            }
        } ]
    });
}

/**
 * 初始化产品详情
 */
function initProdDetail(prodDetail) {
    $("#pdProdName").text(prodDetail.productName);
    $("#pdProdCode").text(prodDetail.productCode);
    var prodType = prodDetail.productType;
    if (prodType == 1) {
        $("#pdProdType").text("基础流量包");
    } else if (prodType == 2) {
        $("#pdProdType").text("组合流量包");
    } else if (prodType == 3) {
        $("#pdProdType").text("流量+产品");
    }
    if (prodDetail.productPrice == null) {
        $("#pdProdPrice").text("￥0");
    } else {
        $("#pdProdPrice").text("￥" + prodDetail.productPrice);
    }
    $("#pdProdTotalPrice").text("￥" + floatMul(prodDetail.productPrice != null ? prodDetail.productPrice : 0, prodDetail.productCount));
    $("#pdProdDescription").text(prodDetail.productDesc);
    $("#pdFlowAmount").text(prodDetail.flowPackageInfo.flowAmount);
    var operatorCode = prodDetail.flowPackageInfo.operatorCode;
    if (operatorCode == "YD") {
        $("#pdOperatorCode").text("移动");
    } else if (operatorCode == "LT") {
        $("#pdOperatorCode").text("联通");
    } else if (operatorCode == "DX") {
        $("#pdOperatorCode").text("电信");
    }
    $("#pdZone").text(prodDetail.flowPackageInfo.zone);
}

/**
 * 设定合计金额
 */
function setAllTotalPrice() {
    var oTable = $(mpTableId).dataTable();
    var tableData = oTable.fnGetData();
    var allTotalPrice = 0;
    $.each(tableData, function(n, value) {
        allTotalPrice = floatAdd(allTotalPrice, value.productTotalPrice != null ? value.productTotalPrice : 0);
    });
    allTotalPrice = parseFloat(allTotalPrice);
    $("#allTotalPriceHidden").val(allTotalPrice);
    $("#allTotalPrice").html("￥" + commaDelimited(allTotalPrice));
}

/**
 * 添加全部
 */
function AddAll(ObjSource, ObjTarget) {
    // 目标列表的HTML加上原列表的所有HTML
    ObjTarget.append(ObjSource.html());
    // 原列表清空
    ObjSource.empty();
}

/**
 * 浮点数相乘
 */
function floatMul(arg1, arg2) {
    if (arg1 == null || arg2 == null) {
        return 0;
    }
    var m = 0, s1 = arg1.toString(), s2 = arg2.toString(), arr;
    arr = s1.split(".");
    if (arr.length > 1) {
        m += arr[1].length;
    }
    arr = s2.split(".");
    if (arr.length > 1) {
        m += arr[1].length;
    }
    return Number(s1.replace(".", "")) * Number(s2.replace(".", "")) / Math.pow(10, m);
}

/**
 * 浮点数相加
 */
function floatAdd(arg1, arg2) {
    var r1 = 0, r2 = 0, m, arr;
    arr = arg1.toString().split(".");
    if (arr.length > 1) {
        r1 = arr[1].length;
    }
    arr = arg2.toString().split(".");
    if (arr.length > 1) {
        r2 = arr[1].length;
    }
    m = Math.pow(10, Math.max(r1, r2));
    return (floatMul(arg1, m) + floatMul(arg2, m)) / m;
}

/**
 * 逗号分隔
 * @param num
 * @returns
 */
function commaDelimited(num) {
    return String(num).replace(/(\d)(?=(\d\d\d)+(?!\d))/g, '$1,');
}

function isNum(s) {
    var r = /^\d+(\.\d+)?$/;
    return r.test(s);
}

/**
 * 更新订单的情况下的初始化
 */
function initUpdateViews() {
    if (pkId) {
        $('#updId').val(pkId);
        $.ajaxSubmit(view_url, {
            'orderId': pkId
        }, function(rtn) {
            if (rtn.success) {
                $.dataInput($('.form-rollback'), rtn.data);
                initModifyViewStatus(rtn.data);
            } else {
                Q_Alert_Fail(data.message);
            }
        });
    }
}

/**
 * 初始化修改页面的值状态
 */
function initModifyViewStatus(data) {
    var status = data.status;
    if (status == 1) {
        $("label[name='status']").text("待生效");
    } else if (status == 3) {
        $("label[name='status']").text("暂停");
    }

    var obj = {};
    obj["id"] = data.customerId;
    obj["text"] = data.customerName;
    $("#customer-select").select2("data", obj);
    $("#customer-select").select2("disable");
    
    var prodType = ""
    if (data.orderDetailList[0] != null) {
        prodType = data.orderDetailList[0].productType;
    }
    if (prodType == 1) { 
        $("input[type=radio][value=1]").attr("checked",'checked');
    } else if (prodType == 2) {
        $("input[type=radio][value=2]").attr("checked",'checked');
    } else if (prodType == 3) {
        $("input[type=radio][value=3]").attr("checked",'checked');
        
        // 计费方式
        $.ajaxSubmit(check_url, {
            'customerId': data.customerId
        }, function(rtn) {
            isBillIssued = rtn.isBillIssued;
            // 允许下发计费
            if (isBillIssued) {
                $(".flowPlusAndBillTypeIssued").show();
                $(".not-update").attr("disabled", "disabled");
                $("#billing-type-select").val(data.billingType);
            }
        });
    }
    $("input[type=radio]").trigger("change");
    if ($("input[type=radio]:checked").val() == 3) {
        $(".flowPackageDiaplay").css("display", "none");
        $(".flowPlusDiaplay").css("display", "block");
    } else {
        $(".flowPackageDiaplay").css("display", "block");
        $(".flowPlusDiaplay").css("display", "none");
    }
    
    var prodIds = [];
    var odList = data.orderDetailList;
    $.each(odList, function(n, value) {
        value.productTotalPrice = floatMul(value.productCount, value.settlementAmount != null ? value.settlementAmount : 0);
        prodIds.push(value.productId);
    });
    if (prodIds.length > 0) {
        $.ajax({
            url : ctxPaths + '/flowProductInfo/flowProductInfo.ajax?productIds=' + prodIds,
            dataType : 'json',
            success : function(data, status) {
                if (data.success == true) {
                    prodOrderList = data.data;
                    $.each(prodOrderList, function(n, value) {
                        $.each(odList, function(index, od) {
                            if (value.productId == od.productId) {
                                value.orderDetailId = od.orderDetailId;
                                value.productCount = od.amount;
                                value.settlementAmount = od.price;
                                value.productTotalPrice = od.priceTotal;
                                value.productPrice = od.settlementPrice;
                            }
                        });
                    });
                    refreshProdOrderTable();
                }
            }
        });
    }
}