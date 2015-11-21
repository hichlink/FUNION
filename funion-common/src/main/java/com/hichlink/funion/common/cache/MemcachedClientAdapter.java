package com.hichlink.funion.common.cache;

import java.io.File;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;

import net.rubyeye.xmemcached.MemcachedClient;
import net.rubyeye.xmemcached.MemcachedClientBuilder;
import net.rubyeye.xmemcached.XMemcachedClientBuilder;

import org.apache.commons.configuration.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aspire.webbas.configuration.config.ConfigurationHelper;
/**
 * 
 * <pre>
 * <b>Title：</b>MemcachedClientAdapter.java<br/>
 * <b>Description：</b>spymemcached适配方法，单实例<br/>
 * <b>@author： </b>zhuangruhai<br/>
 * <b>@date：</b>2014年8月5日 下午6:31:03<br/>  
 * <b>Copyright (c) 2014 ASPire Tech.</b>   
 *  </pre>
 */
public class MemcachedClientAdapter {
	// 创建一个 memcached 客户端对象
	private static final Logger logger = LoggerFactory.getLogger(MemcachedClientAdapter.class);
	protected static MemcachedClient mcc = null;
	private static MemcachedClientAdapter instance = null;
	
	private MemcachedClientAdapter() {
		try {
			List<InetSocketAddress> servers = new ArrayList<InetSocketAddress>();
			Configuration configuration = ConfigurationHelper.getConfiguration(File.separator + "memcached" + File.separator + "servers.xml");
			if (null != configuration){
				@SuppressWarnings("unchecked")
				List<String> list = (List<String>)configuration.getList("servers.server");
				
				for (String str : list){
					String[] strs = str.split(":");
					if (strs.length == 2){
						servers.add(new InetSocketAddress(strs[0],Integer.valueOf(strs[1])));
					}
				}
				MemcachedClientBuilder builder = new XMemcachedClientBuilder(servers);
				mcc= builder.build();
			}else{
				logger.error("初始化memcached配置文件," + ConfigurationHelper.getFullFileName(File.separator + "memcached" + File.separator + "servers.xml" + "出错"));
			}
		} catch (IOException e) {
			logger.error("初始化memcached出错", e);
		}
	};

	public static MemcachedClientAdapter getInstance() {
		if (instance == null) {
			instance = new MemcachedClientAdapter();
		}
		return instance;
	}

	public MemcachedClient getMemCachedClient() {
		return mcc;
	}
}
