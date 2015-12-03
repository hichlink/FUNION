package com.hichlink.funion.portal.web;

import java.math.BigDecimal;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aspire.webbas.core.web.BaseController;
import com.hichlink.funion.common.util.DigitUtil;
import com.hichlink.funion.portal.common.service.FetchCashService;

@Controller
@RequestMapping("/fetchCash")
public class FetchCashController extends BaseController {
	private static final Logger LOG = LoggerFactory.getLogger(FetchCashController.class);
	@Autowired
	private FetchCashService fetchCashService;

	@RequestMapping(value = "/fetch.do", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> fetch(HttpServletRequest request, HttpServletResponse response, BigDecimal cash) {
		if (null == cash || BigDecimal.ZERO.compareTo(cash) > 0 || !DigitUtil.isNumber(cash.toString())) {
			return super.fail("无效的操作金额");
		}
		try {
			fetchCashService.withdrawCash(cash);
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
			return super.fail(e.getMessage());

		}
		return super.success("提取成功,请留意微信零钱");
	}
}
