package com.hichlink.funion.portal.common.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

public class CheckPhone {
	public static final String DX = "DX";
	public static final String YD = "YD";
	public static final String LT = "LT";

	public static boolean isMobileNO(String mobiles) {
		if (StringUtils.isBlank(mobiles)) {
			return false;
		}
		String all = "^((13[0-9])|(14[7])|(15[^4,\\D])|(17[6,7])|(18[0,1,2,3,4,5-9]))\\d{8}$";
		// ^((13[0-9])|(14[7])|(15[^4,\\D])|(17[6,7])|(18[0,1,2,3,4,5-9]))\\d{8}$
		Pattern p = Pattern.compile(all);
		Matcher m = p.matcher(mobiles);
		return m.matches();
	}

	public final static boolean isNotNull(String str) {
		if (str != null && !"".equals(str)) {
			return true;
		} else {
			return false;
		}

	}

	public static String getMobileOpr(String mobiles) {
		if (mobiles == null || mobiles.length() < 11) {
			return "NA";
		}
		int pos = mobiles.length() - 11;

		mobiles = mobiles.substring(pos);
		// ^((13[3])|(15[3])|(17[7])|(18[1,9,0]))\\d{8}$
		String dx = "^((13[3])|(15[3])|(17[7])|(18[1,9,0]))\\d{8}$";
		Pattern p = Pattern.compile(dx);
		Matcher m = p.matcher(mobiles);

		if (m.matches()) {
			return "DX";
		}
		// ^((13[4,5,6,7,8,9])|(14[7])|(15[0,1,2,7,8,9])|(18[2,3,4,7,8]))\\d{8}$
		String yd = "^((13[4,5,6,7,8,9])|(14[7])|(15[0,1,2,7,8,9])|(18[2,3,4,7,8]))\\d{8}$";
		p = Pattern.compile(yd);
		m = p.matcher(mobiles);
		if (m.matches()) {
			return "YD";
		}
		// ^((13[0,1,2])|(15[5,6])|(18[5,6]))\\d{8}$
		String lt = "^((13[0,1,2])|(15[5,6])|(18[5,6]))\\d{8}$";
		p = Pattern.compile(lt);
		m = p.matcher(mobiles);
		if (m.matches()) {
			return "LT";
		}
		return "NA";
	}
}
