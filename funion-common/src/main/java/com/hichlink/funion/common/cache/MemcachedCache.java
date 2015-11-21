package com.hichlink.funion.common.cache;

import java.util.LinkedList;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import net.rubyeye.xmemcached.MemcachedClient;
import net.rubyeye.xmemcached.exception.MemcachedException;

import org.apache.ibatis.cache.Cache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 * 
 * <pre>
 * <b>Title：</b>MemcachedCache.java<br/>
 * <b>Description：</b>mybatis采用memcached缓存<br/>
 * <b>@author： </b>zhuangruhai<br/>
 * <b>@date：</b>2014年8月5日 下午6:30:56<br/>  
 * <b>Copyright (c) 2014 ASPire Tech.</b>   
 *  </pre>
 */
public class MemcachedCache implements Cache {

	// Sf4j logger reference
	private static Logger logger = LoggerFactory
			.getLogger(MemcachedCache.class);

	/** The cache service reference. */
	protected  static MemcachedClient CACHE_SERVICE = null;

	/** The ReadWriteLock. */
	private final ReadWriteLock readWriteLock = new ReentrantReadWriteLock();

	private String id;
	private LinkedList<String> cacheKeys = new LinkedList<String>();

	public MemcachedCache(String id) {
		this.id = id;
	}
	
	// 创建缓存服务类，基于java-memcached-client
	synchronized protected static MemcachedClient getCacheService() {
		try {
			if (null  == CACHE_SERVICE){
				CACHE_SERVICE = MemcachedClientAdapter.getInstance().getMemCachedClient();
			}
			return CACHE_SERVICE;
		} catch (Exception e) {
			String msg = "Initial the JMmemcachedClientAdapter Error.";
			logger.error(msg, e);
			throw new RuntimeException(msg);
		}
	}

	@Override
	public String getId() {
		return this.id;
	}

	// 根据 key 从缓存中获取数据
	@Override
	public Object getObject(Object key) {
		String cacheKey = String.valueOf(key.hashCode());
		Object value = null;
		try {
			value = getCacheService().get(cacheKey);
		} catch (TimeoutException e) {
			logger.error("getObject",e);
		} catch (InterruptedException e) {
			logger.error("getObject",e);
		} catch (MemcachedException e) {
			logger.error("getObject",e);
		}
		if (!cacheKeys.contains(cacheKey)) {
			cacheKeys.add(cacheKey);
		}
		return value;
	}

	@Override
	public ReadWriteLock getReadWriteLock() {
		return this.readWriteLock;
	}

	// 设置数据至缓存中
	@Override
	public void putObject(Object key, Object value) {
		String cacheKey = String.valueOf(key.hashCode());

		if (!cacheKeys.contains(cacheKey)) {
			cacheKeys.add(cacheKey);
		}
		try {
			getCacheService().set(cacheKey, 3600, value);
		} catch (TimeoutException e) {
			logger.error("putObject",e);
		} catch (InterruptedException e) {
			logger.error("putObject",e);
		} catch (MemcachedException e) {
			logger.error("putObject",e);
		}
	}

	// 从缓存中删除指定 key 数据
	@Override
	public Object removeObject(Object key) {
		String cacheKey = String.valueOf(key.hashCode());

		cacheKeys.remove(cacheKey);
		try {
			return getCacheService().delete(cacheKey);
		} catch (TimeoutException e) {
			logger.error("removeObject",e);
		} catch (InterruptedException e) {
			logger.error("removeObject",e);
		} catch (MemcachedException e) {
			logger.error("removeObject",e);
		}
		return false;
	}

	// 清空当前 Cache 实例中的所有缓存数据
	@Override
	public void clear() {
		try {
			cacheKeys.clear();
			getCacheService().flushAll();
		} catch (TimeoutException e) {
			logger.error("clear",e);
		} catch (InterruptedException e) {
			logger.error("clear",e);
		} catch (MemcachedException e) {
			logger.error("clear",e);
		}
	}

	@Override
	public int getSize() {
		return cacheKeys.size();
	}
}
