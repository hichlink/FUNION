package com.hichlink.funion.common.util;

import java.security.MessageDigest;

public class MD5Util {
	private static final char hexDigits[]={'0','1','2','3','4','5','6','7','8','9','a','b','c','d','e','f'}; 
    public final static String MD5(String s) {
        try {
            byte[] btInput = s.getBytes();
            // 获得MD5摘要算法的 MessageDigest 对象
            MessageDigest mdInst = MessageDigest.getInstance("MD5");
            // 使用指定的字节更新摘要
            mdInst.update(btInput);
            // 获得密文
            byte[] md = mdInst.digest();
            // 把密文转换成十六进制的字符串形式
            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(str);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void main(String[] args) {
    	String str1 = "amount=100&check_name=NO_CHECK&desc=测试提现&mch_appid=wxa7e557056c7d41bb&mchid=1247736101&nonce_str=b803f4be6e7b4df9a2b02542c6d30613&openid=oTilysxVwov46q8UGBnrpj-MFpFs&partner_trade_no=151202904626154602&spbill_create_ip=127.0.0.1&key=KSB3649gskd8729MNSY7dn7629HYsn3u";
    	String str2 = "amount=100&check_name=NO_CHECK&desc=测试提现&mch_appid=wxa7e557056c7d41bb&mchid=1247736101&nonce_str=b803f4be6e7b4df9a2b02542c6d30613&openid=oTilysxVwov46q8UGBnrpj-MFpFs&partner_trade_no=151202905250316601&spbill_create_ip=127.0.0.1&key=KSB3649gskd8729MNSY7dn7629HYsn3u";
    	System.out.println(str1.equals(str2));
    	System.out.println(MD5Util.MD5("amount=100&check_name=NO_CHECK&desc=测试提现&mch_appid=wxa7e557056c7d41bb&mchid=1247736101&nonce_str=b803f4be6e7b4df9a2b02542c6d30613&openid=oTilysxVwov46q8UGBnrpj-MFpFs&partner_trade_no=151202905250316601&spbill_create_ip=127.0.0.1&key=KSB3649gskd8729MNSY7dn7629HYsn3u").toUpperCase());
	}
}

