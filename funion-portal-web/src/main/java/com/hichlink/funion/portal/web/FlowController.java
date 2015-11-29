package com.hichlink.funion.portal.web;

import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.aspire.webbas.core.web.BaseController;
import com.hichlink.funion.common.entity.FlowPayRecord;
import com.hichlink.funion.common.entity.WxAccessConf;
import com.hichlink.funion.common.entity.WxPayRecord;
import com.hichlink.funion.common.service.FlowPayRecordService;
import com.hichlink.funion.common.service.FlowProductInfoService;
import com.hichlink.funion.common.service.WxAccessConfService;
import com.hichlink.funion.common.service.WxPayRecordService;
import com.hichlink.funion.common.weixin.WeixinApiBiz;
import com.hichlink.funion.common.weixin.WeixinPayBiz;
import com.hichlink.funion.common.weixin.entity.AccessToken;
import com.hichlink.funion.common.weixin.entity.WxPayNotify;
import com.hichlink.funion.portal.common.config.SystemConfig;
import com.hichlink.funion.portal.common.dto.FlowProductDTO;
import com.hichlink.funion.portal.common.service.FlowService;
import com.hichlink.funion.portal.common.util.CheckPhone;
import com.hichlink.funion.portal.common.util.SessionUtil;

@Controller
@RequestMapping("/flow")
public class FlowController extends BaseController {
	private static final Logger LOG = LoggerFactory.getLogger(FlowController.class);
	@Autowired
	private FlowService flowService;
	@Autowired
	private FlowProductInfoService flowProductInfoService;
	@Autowired
	private WeixinPayBiz weixinPayBiz;
	@Autowired
	private WeixinApiBiz weixinApiBiz;
	@Autowired
	private WxAccessConfService wxAccessConfService;
	@Autowired
	private WxPayRecordService wxPayRecordService;
	@Autowired
	private FlowPayRecordService flowPayRecordService;

	@RequestMapping(value = "/{uuid}/enter.do")
	public ModelAndView enter(HttpServletRequest request, HttpServletResponse response, @PathVariable String uuid)
			throws Exception {
		if (StringUtils.isBlank(uuid)) {
			return null;
		}
		SessionUtil.setUUID(uuid);
		return new ModelAndView("flow", "uuid", uuid);
	}

	@RequestMapping(value = "/getProduct.do")
	@ResponseBody
	public Map<String, Object> getProduct(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(defaultValue = "") String mobile) throws Exception {
		if (StringUtils.isBlank(mobile) || !CheckPhone.isMobileNO(mobile)) {
			LOG.error("mobile={}无效", mobile);
			return super.fail("手机号码无效!");
		}
		return super.success(flowService.getProductByMobile(mobile));
	}

	@RequestMapping(value = "/{mobile}/{productId}/enterPay.do")
	public ModelAndView enterPay(HttpServletRequest request, HttpServletResponse response, @PathVariable String mobile,
			@PathVariable Long productId, @RequestParam(defaultValue = "") String code) throws Exception {
		if (StringUtils.isBlank(mobile) || !CheckPhone.isMobileNO(mobile)) {
			LOG.error("mobile={}无效", mobile);
			return null;
		}

		String appId = SystemConfig.getInstance().getAppId();
		String openId = SessionUtil.getPayOpenId();
		if (StringUtils.isBlank(openId)) {
			if (StringUtils.isBlank(code)) {
				String projectName = request.getContextPath();
				String redirectUri = URLEncoder.encode(SystemConfig.getInstance().getDomain() + projectName + "/"
						+ mobile + "/" + productId + "/enterPay.do", "utf-8");
				response.sendRedirect(weixinApiBiz.getAuthUrlBySnsapiBase(appId, redirectUri));
			} else {
				AccessToken accessToken = weixinApiBiz.getAccessToken(appId, code);
				if (null != accessToken) {
					openId = accessToken.getOpenId();
					SessionUtil.setPayOpenId(openId);
				} else {
					LOG.error("获取不到用户Token.");
					return null;
				}
			}
		}
		try {
			FlowProductDTO flowProductDTO = flowService.initPayRecord(request, response, mobile, productId);
			return new ModelAndView("pay", "flowProductDTO", flowProductDTO);
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
			return new ModelAndView("error", "errorMsg", e.getMessage());
		}

	}

	@RequestMapping(value = "/{prepayId}/sendPayRequest.do")
	@ResponseBody
	public Map<String, Object> sendPayRequest(HttpServletRequest request, HttpServletResponse response,
			@PathVariable String prepayId) throws Exception {
		if (StringUtils.isBlank(prepayId)) {
			LOG.error("prepayId={}无效", prepayId);
			return super.fail("参数无效!");
		}
		String appId = SystemConfig.getInstance().getAppId();
		return super.success(weixinPayBiz.getPayConfig(appId, prepayId));
	}

	@RequestMapping(value = "/notify.do", produces = { "text/xml;charset=UTF-8" })
	public String notify(@RequestBody String body) {
		try {
			LOG.debug("微信支付回调body={}", body);
			if (StringUtils.isBlank(body)) {
				LOG.error("回调参数为空");
				return null;
			}
			WxPayNotify wxPayNotify = new WxPayNotify(body);

			String appId = SystemConfig.getInstance().getAppId();
			WxAccessConf wxAccessConf = wxAccessConfService.getWxAccessConf(appId);
			if (!wxPayNotify.validateSign(wxAccessConf.getApiKey())) {
				LOG.error("支付回调签名认证失败");
				return null;
			}
			if (!wxPayNotify.isSuccess()) {
				LOG.error("支付回调失败,reason={}", wxPayNotify.getReturnMsg());
				return null;
			}
			String outTradeNo = wxPayNotify.getOutTradeNo();
			if (StringUtils.isBlank(outTradeNo)) {
				LOG.error("支付回调支付订单号为空");
				return null;
			}
			WxPayRecord wxPayRecord = wxPayRecordService.selectByOutTradeNo(outTradeNo);
			if (null == wxPayRecord) {
				LOG.error("支付回调根据outTradeNo={}找不到对应记录", outTradeNo);
				return null;
			}
			Integer total = wxPayRecord.getTotalFee().multiply(new BigDecimal(100)).intValue();
			if (total != wxPayNotify.getTotalFee()) {
				LOG.error("支付回调支付金额比较不一致{}！={}", new Object[] { total, wxPayNotify.getTotalFee() });
				return null;
			}
			if (!WxPayRecord.PAY_STATUS_SUCC.equals(wxPayRecord.getPayStatus())) {
				wxPayRecord.setPayStatus(WxPayRecord.PAY_STATUS_SUCC);
				wxPayRecord.setPayTime(new Date());
				wxPayRecordService.saveAndUpdate(wxPayRecord);
				FlowPayRecord flowPayRecord = flowPayRecordService.get(wxPayRecord.getRecordId());
				if (null != flowPayRecord) {
					flowPayRecord.setPayStatus(WxPayRecord.PAY_STATUS_SUCC);
					flowPayRecordService.saveAndUpdate(flowPayRecord);
				} else {
					LOG.error("支付回调根据recordId={}找不到对应流量包支付记录", wxPayRecord.getRecordId());
				}
			}

		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
			return null;
		}
		return "<xml><return_code><![CDATA[SUCCESS]]></return_code><return_msg><![CDATA[OK]]></return_msg></xml>";
	}
}