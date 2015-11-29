package com.hichlink.funion.common.util;

import java.io.UnsupportedEncodingException;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class BaseUtil {
	private static final Logger log = LoggerFactory.getLogger(BaseUtil.class);

	// 加密
	public static String getBase64(String str) {
		if (StringUtils.isBlank(str)){
			return "";
		}
		byte[] b = null;
		String s = null;
		log.debug("加密值str:=" + str);
		try {
			b = str.getBytes("utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		if (b != null) {
			s = new BASE64Encoder().encode(b);
			log.debug("进来加密:" + s);
		}
		return s;
	}

	// 解密
	public static String getFromBase64(String s) {
		if (StringUtils.isBlank(s)){
			return "";
		}
		byte[] b = null;
		String result = null;
		log.debug("解密值s:=" + s);
		if (s != null) {
			BASE64Decoder decoder = new BASE64Decoder();
			try {
				b = decoder.decodeBuffer(s);
				result = new String(b, "utf-8");
				log.debug("进来解密:" + result);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return result;
	}

}
