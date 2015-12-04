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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.aspire.webbas.core.exception.MyException;
import com.aspire.webbas.core.web.BaseController;
import com.hichlink.funion.common.entity.FlowPayRecord;
import com.hichlink.funion.common.entity.FlowProductInfo;
import com.hichlink.funion.common.entity.WxAccessConf;
import com.hichlink.funion.common.entity.WxPayRecord;
import com.hichlink.funion.common.flow.exchange.FlowRespMesg;
import com.hichlink.funion.common.service.FlowPayRecordService;
import com.hichlink.funion.common.service.FlowProductInfoService;
import com.hichlink.funion.common.service.WxAccessConfService;
import com.hichlink.funion.common.service.WxPayRecordService;
import com.hichlink.funion.common.util.XStreamHandle;
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
	private WeixinPayBiz weixinPayBiz;
	@Autowired
	private WeixinApiBiz weixinApiBiz;
	@Autowired
	private WxAccessConfService wxAccessConfService;
	@Autowired
	private WxPayRecordService wxPayRecordService;
	@Autowired
	private FlowPayRecordService flowPayRecordService;
	@Autowired
	private FlowProductInfoService flowProductInfoService;

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

	@RequestMapping(value = "/{uuid}/{mobile}/{productId}/enterPay.do")
	public void enterPay(HttpServletRequest request, HttpServletResponse response, @PathVariable String uuid,
			@PathVariable String mobile, @PathVariable Long productId, @RequestParam(defaultValue = "") String code,
			RedirectAttributes attr) throws Exception {

		String appId = SystemConfig.getInstance().getAppId();
		String openId = SessionUtil.getPayOpenId();
		String projectName = request.getContextPath();
		if (StringUtils.isBlank(openId)) {
			if (StringUtils.isBlank(code)) {
				String redirectUri = URLEncoder.encode(SystemConfig.getInstance().getDomain() + projectName + "/flow/"
						+ uuid + "/" + mobile + "/" + productId + "/enterPay.do", "utf-8");
				response.sendRedirect(weixinApiBiz.getAuthUrlBySnsapiBase(appId, redirectUri));
				return;
			}
		}
		/*
		 * attr.addAttribute("uuid", uuid); attr.addAttribute("mobile", mobile);
		 * attr.addAttribute("productId", productId); attr.addAttribute("code",
		 * code); return new ModelAndView("redirect:/flow/pay.do");
		 */

		response.sendRedirect(SystemConfig.getInstance().getDomain() + projectName + "/flow/pay.do?uuid=" + uuid
				+ "&mobile=" + mobile + "&productId=" + productId + "&code=" + code);

	}

	@RequestMapping(value = "/pay.do")
	public ModelAndView pay(HttpServletRequest request, HttpServletResponse response, String uuid, String mobile,
			Long productId, String code) throws Exception {
		if (StringUtils.isBlank(mobile) || !CheckPhone.isMobileNO(mobile)) {
			LOG.error("mobile={}无效", mobile);
			return null;
		}
		if (StringUtils.isBlank(SessionUtil.getPayOpenId()) && StringUtils.isNotBlank(code)) {
			String appId = SystemConfig.getInstance().getAppId();
			AccessToken accessToken = weixinApiBiz.getAccessToken(appId, code);
			if (null != accessToken) {
				String openId = accessToken.getOpenId();
				SessionUtil.setPayOpenId(openId);
			} else {
				LOG.error("获取不到用户Token.");
				return null;
			}
		}
		try {
			// FlowProductDTO flowProductDTO = (FlowProductDTO)
			// request.getAttribute("flowProductDTO");
			// FlowProductDTO flowProductDTO =
			// flowService.initPayRecord(request, response, uuid, mobile,
			// productId);
			FlowProductInfo flowProductInfo = flowProductInfoService.get(productId);
			if (null == flowProductInfo) {
				LOG.error("根据productId={}查找不到对应的流量包信息", productId);
				throw new MyException("查找不到对应的流量包信息");
			}
			if (StringUtils.isBlank(mobile) || !CheckPhone.isMobileNO(mobile)) {
				LOG.error("mobile={}无效", mobile);
				throw new MyException("手机号码无效!");
			}
			FlowProductDTO flowProductDTO = new FlowProductDTO();
			flowProductDTO.setProductId(flowProductInfo.getProductId());
			flowProductDTO.setProductName(flowProductInfo.getProductName());
			flowProductDTO.setNum(1);
			flowProductDTO.setSettlementPrice(flowProductInfo.getSettlementPrice());
			flowProductDTO.setTotalPrice(
					flowProductDTO.getSettlementPrice().multiply(new BigDecimal(flowProductDTO.getNum())));
			return new ModelAndView("pay", "flowProductDTO", flowProductDTO);
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
			return new ModelAndView("error", "errorMsg", e.getMessage());
		}

	}

	@RequestMapping(value = "/sendPayRequest.do")
	@ResponseBody
	public Map<String, Object> sendPayRequest(HttpServletRequest request, HttpServletResponse response, String uuid,
			String mobile, Long productId) throws Exception {
		String prepayId = flowService.initPayRecord(request, response, uuid, mobile, productId);
		if (StringUtils.isBlank(prepayId)) {
			LOG.error("prepayId={}无效", prepayId);
			return super.fail("生成支付订单失败!");
		}
		String appId = SystemConfig.getInstance().getAppId();
		return super.success(weixinPayBiz.getPayConfig(appId, prepayId));
	}

	@RequestMapping(value = "/notify.do", produces = { "text/xml;charset=UTF-8" })
	@ResponseBody
	public String notify(@RequestBody String body) {
		try {
			LOG.debug("微信支付回调body={}", body);
			if (StringUtils.isBlank(body)) {
				LOG.error("回调参数为空");
				return null;
			}
			WxPayNotify wxPayNotify = XStreamHandle.toBean(body, WxPayNotify.class);

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
			if (!total.equals(wxPayNotify.getTotalFee())) {
				LOG.error("支付回调支付金额比较不一致{}！={}", new Object[] { total, wxPayNotify.getTotalFee() });
				return null;
			}
			if (!WxPayRecord.PAY_STATUS_SUCC.equals(wxPayRecord.getPayStatus())) {
				wxPayRecord.setPayStatus(WxPayRecord.PAY_STATUS_SUCC);
				wxPayRecord.setPayCheckTime(new Date());
				wxPayRecord.setTransactionId(wxPayNotify.getTransactionId());
				wxPayRecordService.update(wxPayRecord);
				FlowPayRecord flowPayRecord = flowPayRecordService.get(wxPayRecord.getRecordId());
				if (null != flowPayRecord) {
					flowPayRecord.setPayStatus(WxPayRecord.PAY_STATUS_SUCC);
					flowPayRecordService.update(flowPayRecord);
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

	@RequestMapping(value = "/flowCallback.do", produces = { "text/xml;charset=UTF-8" })
	@ResponseBody
	public FlowRespMesg flowCallback(@RequestBody String body) {
		return  flowService.exchangeCallback(body);
	}
}
