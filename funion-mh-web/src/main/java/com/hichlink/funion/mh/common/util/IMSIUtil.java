package com.hichlink.funion.mh.common.util;

import org.apache.commons.lang3.StringUtils;

public class IMSIUtil {

	public final static String getAreaInfo(String imsi) {
		if (!StringUtils.isNotBlank(imsi) || imsi.length() != 15) {
			return null;
		}
		String mcc = imsi.substring(0, 3);// 国家代码 460中国
		String mnc = imsi.substring(3, 5);// 网络代码。 00／02/07 移动 01／06联通 03/05电信
											// 20铁通
		if ("01".equals(mnc) || "06".equals(mnc)) { // 联通。
			return getMSISDNCU(imsi);
		} else if ("00".equals(mnc) || "02".equals(mnc) || "07".equals(mnc)) {
			return getMSISDNCMCC(imsi);
		} else if ("03".equals(mnc) || "05".equals(mnc)||"11".equals(mnc)) {
			return getMSISDNCT(imsi);
		}
		return null;
	}
	private static String getMSISDNCMCC(String imsi) {
		String mcc = imsi.substring(0, 3);// 国家代码 460中国
		String mnc = imsi.substring(3, 5);// 网络代码。 00／02/07 移动 01／06联通 03/05电信
		
		//String area = imsi.substring(5, 9);
		//String ndc = imsi.substring(9, 10);// A=0、1（13号段S=0时）//A=9（13号段 S=1时）
		String msisdnNDC = "";
		if (!"460".equals(mcc)) {
			return null;
		}
		//13S0H1H2H3ABCD 
		//46000 H1 H2 H3 S XXXXXX S=5、6、7、8、9；XXXXXX为MSISDN号码中ABCD扰码得到. 
		//46000 7  1  1  3 8  11210
		//13SH0H1H2H3ABCD 
		//46000 H1 H2 H3 T H0 XXXXX S=5、6、7、8、9；T=S-5;XXXXX为MSISDN号码中ABCD扰码得到. 
		//46000 7  1  1  3 8  11210
		
		//134H0H1H2H3ABCD 460020H0H1H2H3XXXXX H0=0—8; XXXXX为MSISDN号码中ABCD扰码得到. 
		if("00".equals(mnc)){
			String indc = imsi.substring(8, 9);// A=0、1（13号段S=0时）//A=9（13号段 S=1时）
			if(Integer.parseInt(indc)>=5){
				msisdnNDC="13"+indc;
				String area = imsi.substring(5, 8);
				return msisdnNDC+"0"+area;
			}else{
				msisdnNDC="13"+(Integer.parseInt(indc)+5);
				String area = imsi.substring(5, 8);
				String area1 = imsi.substring(9, 10);// A=0、1（13号段S=0时）//A=9（13号段 S=1时）
				return msisdnNDC+area1+area;
			}
		}else if("02".equals(mnc)){
			
			
			String subMnc = imsi.substring(5, 6);
			if("0".equals(subMnc)){
				//134H0H1H2H3ABCD 460020H0H1H2H3XXXXX H0=0—8; XXXXX为MSISDN号码中ABCD扰码得到. 
				msisdnNDC = "134" ;
				String area = imsi.substring(6, 10);
				return msisdnNDC+area;
			}else if("1".equals(subMnc)){
				//151H0H1H2H3ABCD 460021H0H1H2H3XXXXX XXXXX为MSISDN号码中ABCD扰码得到.
				msisdnNDC = "151" ;
				String area = imsi.substring(6, 10);
				return msisdnNDC+area;
			}else if("2".equals(subMnc)){
				//152H0H1H2H3ABCD 460022H0H1H2H3XXXXX XXXXX为MSISDN号码中ABCD扰码得到. 
				msisdnNDC = "152" ;
				String area = imsi.substring(6, 10);
				return msisdnNDC+area;
			}else if("3".equals(subMnc)){
				//150H0H1H2H3ABCD 460023H0H1H2H3XXXXX XXXXX为MSISDN号码中ABCD扰码得到. 
				msisdnNDC = "150" ;
				String area = imsi.substring(6, 10);
				return msisdnNDC+area;
			}else if("6".equals(subMnc)){
				//187H0H1H2H3ABCD 460027H0H1H2H3XXXXX XXXXX为MSISDN号码中ABCD扰码得到. 
				msisdnNDC = "182" ;
				String area = imsi.substring(6, 10);
				return msisdnNDC+area;
			}else if("5".equals(subMnc)){
				//187H0H1H2H3ABCD 460027H0H1H2H3XXXXX XXXXX为MSISDN号码中ABCD扰码得到. 
				msisdnNDC = "183" ;
				String area = imsi.substring(6, 10);
				return msisdnNDC+area;
			}else if("4".equals(subMnc)){
				//187H0H1H2H3ABCD 460027H0H1H2H3XXXXX XXXXX为MSISDN号码中ABCD扰码得到. 
				msisdnNDC = "184" ;
				String area = imsi.substring(6, 10);
				return msisdnNDC+area;
			}else if("7".equals(subMnc)){
				//187H0H1H2H3ABCD 460027H0H1H2H3XXXXX XXXXX为MSISDN号码中ABCD扰码得到. 
				msisdnNDC = "187" ;
				String area = imsi.substring(6, 10);
				return msisdnNDC+area;
			}else if("8".equals(subMnc)){
				//158H0H1H2H3ABCD 460028H0H1H2H3XXXXX XXXXX为MSISDN号码中ABCD扰码得到.  
				msisdnNDC = "158" ;
				String area = imsi.substring(6, 10);
				return msisdnNDC+area;
			}else if("9".equals(subMnc)){
				//159H0H1H2H3ABCD 460029H0H1H2H3XXXXX XXXXX为MSISDN号码中ABCD扰码得到. 
				msisdnNDC = "159" ;
				String area = imsi.substring(6, 10);
				return msisdnNDC+area;
			}
		}else if("07".equals(mnc)){
			String subMnc = imsi.substring(5, 6);
			if("7".equals(subMnc)){
				//157H0H1H2H3ABCD 460077H0H1H2H3XXXXX XXXXX为MSISDN号码中ABCD扰码得到. 
				msisdnNDC = "157" ;
				String area = imsi.substring(6, 10);
				return msisdnNDC+area;
			}else if("8".equals(subMnc)){
				//188H0H1H2H3ABCD 460078H0H1H2H3XXXXX XXXXX为MSISDN号码中ABCD扰码得到. 
				msisdnNDC = "188" ;
				String area = imsi.substring(6, 10);
				return msisdnNDC+area;
			}else if("9".equals(subMnc)){
				//147H0H1H2H3ABCD 460079H0H1H2H3XXXXX XXXXX为MSISDN号码中ABCD扰码得到. 
				msisdnNDC = "147" ;
				String area = imsi.substring(6, 10);
				return msisdnNDC+area;
			}
		}

		//String hlrArea = getmsisdnAreaCode(area);
		return msisdnNDC ;

	}
	
	
	private static String getMSISDNCT(String imsi) {
		String mcc = imsi.substring(0, 3);// 国家代码 460中国
		String ndc = imsi.substring(5, 6);
		//String area = imsi.substring(5, 9);
		//String ndc = imsi.substring(9, 10);//A=0、1（13号段S=0时）//A=9（13号段 S=1时）
		String msisdnNDC = "";
		if (!"460".equals(mcc)) {
			return null;
		}

		if ("6".equals(ndc)) {
			String subNdc = imsi.substring(8, 9);
			if("1".equals(subNdc)){
				msisdnNDC="189";
			}else if("0".equals(subNdc)){
				msisdnNDC="153";
			}else if("2".equals(subNdc)){
				msisdnNDC="180";
			}else if("3".equals(subNdc)){
				msisdnNDC="181";
			}
			String area = imsi.substring(6, 8)+imsi.substring(9, 11);
			return msisdnNDC+area;
		} else if ("0".equals(ndc)) {
			String subNdc = imsi.substring(6, 7);
			if("3".equals(subNdc)){
				//msisdnNDC="181";
			}else if("9".equals(subNdc)){
				msisdnNDC="133";
			}
			String area = imsi.substring(7, 11);
			return msisdnNDC+area;
		} 

		//String hlrArea = getmsisdnAreaCode(area);
		return null;

	}
	private static String getMSISDNCU(String imsi) {
		String mcc = imsi.substring(0, 3);// 国家代码 460中国
		
		String area = imsi.substring(5, 9);
		String ndc = imsi.substring(9, 10);// A=0、1（13号段S=0时）//A=9（13号段 S=1时）
		String msisdnNDC = "";
		if (!"460".equals(mcc)) {
			return null;
		}

		if ("0".equals(ndc) || "1".equals(ndc)) {
			msisdnNDC = "130";
		} else if ("3".equals(ndc)) {
			msisdnNDC = "156";
		} else if ("2".equals(ndc)) {
			msisdnNDC = "132";
		} else if ("4".equals(ndc)) {
			msisdnNDC = "155";
		} else if ("5".equals(ndc)) {
			msisdnNDC = "185";
		}else if ("6".equals(ndc)) {
			msisdnNDC = "186";
		}else if ("7".equals(ndc)) {
			msisdnNDC = "145";
		}else if ("9".equals(ndc)) {
			msisdnNDC = "131";
		}

		String hlrArea = getmsisdnAreaCode(area);
		return msisdnNDC + hlrArea;

	}

	private static String getmsisdnAreaCode(String area) {
		String postfix = area.substring(0, 3);
		String prefix = area.substring(3);
		return prefix + postfix;
	}

	public static void main(String[] args) {
		//System.out.println(getAreaInfo("460026350822565"))
		//15566490755;
		String imsi = "460010430721746";
		System.out.println(getAreaInfo(imsi));
		
		String responseStr = HttpClientUtil
				.sendDataHttpViaGet("http://www.soimsi.com/jorchen.php?token="
						+ "fb2da2e4337cbb9a0f22d5b0f800aee5" + "&imsi=" + imsi);
		System.out.println(responseStr);

		
		
	}
}
