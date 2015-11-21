package com.hichlink.funion.common.util;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.fluent.Request;
import org.apache.http.entity.ContentType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RequestUtil {
	private static Logger log = LoggerFactory.getLogger(RequestUtil.class);
	public static String httpGet(String url) {
		try {
			String response = Request.Get(url).execute().returnContent().toString();
			System.out.println(response);
			log.debug("http Get={},response={}", new Object[] { url, response });
			return response;
		} catch (ClientProtocolException e) {
			log.error(e.getMessage(), e);
		} catch (IOException e) {
			log.error(e.getMessage(), e);
		}
		return null;
	}
	public static String httpPost(String url, String content, ContentType contentType) {
		try {
			String response = Request.Post(url).bodyString(content, contentType).execute().returnContent().toString();
			log.debug("http Post={},response={}", new Object[] { url, response });
			return response;
		} catch (ClientProtocolException e) {
			log.error(e.getMessage(), e);
		} catch (IOException e) {
			log.error(e.getMessage(), e);
		}
		return null;
	}
	public static String httpPostByXml(String url, String content) {
		return httpPost(url,content,ContentType.APPLICATION_XML);
	}
}
