package com.hichlink.funion.common.cache;

import java.sql.Connection;
import java.util.List;

import org.apache.ibatis.cache.Cache;
import org.apache.ibatis.cache.CacheKey;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.reflection.DefaultReflectorFactory;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.ReflectorFactory;
import org.apache.ibatis.reflection.factory.DefaultObjectFactory;
import org.apache.ibatis.reflection.factory.ObjectFactory;
import org.apache.ibatis.reflection.wrapper.DefaultObjectWrapperFactory;
import org.apache.ibatis.reflection.wrapper.ObjectWrapperFactory;
import org.apache.ibatis.type.TypeHandlerRegistry;

import com.aspire.webbas.core.pagination.mybatis.interceptor.PaginationInterceptor;
import com.aspire.webbas.core.pagination.mybatis.pager.Page;
@Intercepts({ @Signature(method = "prepare", type = StatementHandler.class, args = { Connection.class }) })
/**
 * 
 * <pre>
 * <b>Title：</b>PaginationDispatchInterceptor.java<br/>
 * <b>Description：</b>修复了列表总数自己组装SQL不能缓存的问题手动把总数放入到memcached中<br/>
 * <b>@author： </b>zhuangruhai<br/>
 * <b>@date：</b>2014年8月5日 下午6:31:16<br/>  
 * <b>Copyright (c) 2014 ASPire Tech.</b>   
 *  </pre>
 */
public class PaginationDispatchInterceptor extends PaginationInterceptor {
	private static final ObjectFactory DEFAULT_OBJECT_FACTORY = new DefaultObjectFactory();
	private static final ObjectWrapperFactory DEFAULT_OBJECT_WRAPPER_FACTORY = new DefaultObjectWrapperFactory();
	private static final ReflectorFactory DEFAULT_REFLECTOR_FACTORY = new DefaultReflectorFactory();
	public void dispatch(Page<?> page, MappedStatement mappedStatement,
			BoundSql boundSql) {
		if (mappedStatement.getConfiguration().isCacheEnabled()){
			CacheKey cacheKey = createCacheKey(mappedStatement, page, boundSql);
			Cache cache = new MemcachedCache(cacheKey.toString());
			cache.putObject(cacheKey, page.getTotal());
		}
	}

	private CacheKey createCacheKey(MappedStatement ms, Object parameterObject,
			BoundSql boundSql) {
		CacheKey cacheKey = new CacheKey();
		cacheKey.update(ms.getId());
		// cacheKey.update(rowBounds.getOffset());
		// cacheKey.update(rowBounds.getLimit());
		List<ParameterMapping> parameterMappings = boundSql
				.getParameterMappings();
		// 解决自动生成SQL，SQL语句为空导致key生成错误的bug
		cacheKey.update(super.getCountSql(boundSql.getSql()));
		MetaObject metaObject = MetaObject.forObject(parameterObject,
				DEFAULT_OBJECT_FACTORY, DEFAULT_OBJECT_WRAPPER_FACTORY,DEFAULT_REFLECTOR_FACTORY);

		if (parameterMappings.size() > 0 && parameterObject != null) {
			TypeHandlerRegistry typeHandlerRegistry = ms.getConfiguration()
					.getTypeHandlerRegistry();
			if (typeHandlerRegistry.hasTypeHandler(parameterObject.getClass())) {
				cacheKey.update(parameterObject);
			} else {
				for (ParameterMapping parameterMapping : parameterMappings) {
					String propertyName = parameterMapping.getProperty();
					if (metaObject.hasGetter(propertyName)) {
						cacheKey.update(metaObject.getValue(propertyName));
					} else if (boundSql.hasAdditionalParameter(propertyName)) {
						cacheKey.update(boundSql
								.getAdditionalParameter(propertyName));
					}
				}
			}
		}
		return cacheKey;
	}
}
