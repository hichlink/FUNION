package com.hichlink.funion.mh.web;

import java.text.MessageFormat;
import java.util.Date;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aspire.webbas.core.web.BaseController;
import com.hichlink.funion.mh.common.config.SystemConfig;
import com.hichlink.funion.mh.common.entity.MobileHomeInfo;
import com.hichlink.funion.mh.common.service.MobileHomeInfoService;
import com.hichlink.funion.mh.common.util.HttpClientUtil;
import com.hichlink.funion.mh.common.util.IMSIUtil;

import net.sf.json.JSONObject;

/**
 * 
 * <b>Title：</b>MobileHomeInfoController.java<br/>
 * <b>Description：</b> <br/>
 * <b>@author： </b>v5480<br/>
 * <b>@date：</b>2015-08-01 15:18:01<br/>
 * <b>Copyright (c) 2015 szwisdom Tech.</b>
 * 
 */
@Controller
@RequestMapping("/mh")
public class MobileHomeInfoController extends BaseController {
	private static final Logger LOG = LoggerFactory.getLogger(MobileHomeInfoController.class);
	@Autowired
	@Qualifier("mobileHomeInfoService")
	private MobileHomeInfoService mobileHomeInfoService;

	@RequestMapping(value = "/mobile.ws")
	@ResponseBody
	public Map<String, Object> getByMobile(String mobile) {
		if (StringUtils.isBlank(mobile) || mobile.length() != 11) {
			return fail("手机号码有误");
		}
		String mobileNo = mobile.substring(0, 7);
		MobileHomeInfo mobileHomeInfo = getMobileInfo(mobileNo);
		if (null == mobileHomeInfo) {
			return fail("查询不到记录");
		}
		mobileHomeInfo.setMobileNo(mobile);
		return super.success(mobileHomeInfo);
	}

	private MobileHomeInfo getMobileInfo(String mobileNo) {
		MobileHomeInfo mobileHomeInfo = mobileHomeInfoService.get(mobileNo);
		if (null == mobileHomeInfo) {
			String resp = HttpClientUtil.sendDataHttpViaGet(
					MessageFormat.format(SystemConfig.getInstance().getMobileInfoUrl(), mobileNo + "0000"));
			JSONObject json = JSONObject.fromObject(resp);
			if ("00".equals(json.get("code"))) {
				MobileHomeInfo mobileInfo = (MobileHomeInfo) JSONObject.toBean(json.getJSONObject("data"),
						MobileHomeInfo.class);
				if ("未知".equals(mobileInfo.getCity())) {
					LOG.error("mobileNo={}取到结果{}", new Object[] { mobileNo, resp });
					return null;
				}
				mobileInfo.setMobileNo(mobileInfo.getMobileNo().substring(0, 7));
				mobileInfo.setCreateDate(new Date());
				mobileInfo.setModifyDate(new Date());
				mobileHomeInfoService.insert(mobileInfo);
				mobileInfo.setMobileNo(mobileNo);
				return mobileInfo;
			} else {
				return null;
			}

		}
		return mobileHomeInfo;
	}

	@RequestMapping(value = "/imsi.ws")
	@ResponseBody
	public Map<String, Object> getByIMSI(String imsi) {
		if (StringUtils.isBlank(imsi)) {
			return fail("IMSI不能为空");
		}
		String mobileNo = IMSIUtil.getAreaInfo(imsi);
		if (StringUtils.isBlank(mobileNo) || mobileNo.length() != 7) {
			LOG.info("imsi={},mobileNo={}", new Object[] { imsi, mobileNo });
		}
		if (StringUtils.isBlank(mobileNo)) {
			return fail("IMSI无效");
		}
		MobileHomeInfo mobileHomeInfo = getMobileInfo(mobileNo);
		if (null == mobileHomeInfo) {
			return fail("查询不到记录");
		}
		mobileHomeInfo.setMobileNo(imsi);
		return super.success(mobileHomeInfo);
	}
}