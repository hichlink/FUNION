package com.hichlink.funion.common.interceptor;

import java.sql.SQLException;
import java.util.List;
import java.util.Properties;

import org.apache.ibatis.cache.CacheKey;
import org.apache.ibatis.executor.CachingExecutor;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.logging.Log;
import org.apache.ibatis.logging.LogFactory;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.reflection.DefaultReflectorFactory;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.ReflectorFactory;
import org.apache.ibatis.reflection.factory.DefaultObjectFactory;
import org.apache.ibatis.reflection.factory.ObjectFactory;
import org.apache.ibatis.reflection.wrapper.DefaultObjectWrapperFactory;
import org.apache.ibatis.reflection.wrapper.ObjectWrapperFactory;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.type.TypeHandlerRegistry;

import com.aspire.webbas.core.pagination.mybatis.interceptor.BasePagination;
import com.aspire.webbas.core.pagination.mybatis.pager.Page;
import com.hichlink.funion.common.cache.MemcachedCache;

/**
 * 采用分页拦截器执行物理分页查询时，原生的Executor创建cacheKey时未能包含分页参数page，为了解决这个问题，创建了本拦截器，
 * 本拦截器会拦截CachingExecutor的query方法，在创建cacheKey时将分页参数page包含其中。 老规矩，签名里要拦截的类型只能是接口。
 * 
 */
@Intercepts({ @Signature(type = Executor.class, method = "query", args = {
		MappedStatement.class, Object.class, RowBounds.class,
		ResultHandler.class }) })
public class CacheInterceptor extends BasePagination implements Interceptor {
	private static final Log logger = LogFactory.getLog(CacheInterceptor.class);
	private static final ObjectFactory DEFAULT_OBJECT_FACTORY = new DefaultObjectFactory();
	private static final ObjectWrapperFactory DEFAULT_OBJECT_WRAPPER_FACTORY = new DefaultObjectWrapperFactory();
	private static final ReflectorFactory DEFAULT_REFLECTOR_FACTORY = new DefaultReflectorFactory();
	@Override
	public Object intercept(Invocation invocation) throws Throwable {
		Executor executorProxy = (Executor) invocation.getTarget();
		MetaObject metaExecutor = MetaObject.forObject(executorProxy,
				DEFAULT_OBJECT_FACTORY, DEFAULT_OBJECT_WRAPPER_FACTORY, DEFAULT_REFLECTOR_FACTORY);
		// 分离代理对象链
		while (metaExecutor.hasGetter("h")) {
			Object object = metaExecutor.getValue("h");
			metaExecutor = MetaObject.forObject(object, DEFAULT_OBJECT_FACTORY,
					DEFAULT_OBJECT_WRAPPER_FACTORY,DEFAULT_REFLECTOR_FACTORY);
		}
		// 分离最后一个代理对象的目标类
		while (metaExecutor.hasGetter("target")) {
			Object object = metaExecutor.getValue("target");
			metaExecutor = MetaObject.forObject(object, DEFAULT_OBJECT_FACTORY,
					DEFAULT_OBJECT_WRAPPER_FACTORY,DEFAULT_REFLECTOR_FACTORY);
		}
		Object[] args = invocation.getArgs();
		if (null != args && args.length == 4 && args[1] instanceof Page<?>) {
			return this.query(metaExecutor, args);
		} else {
			return invocation.proceed();
		}

	}

	public <E> List<E> query(MetaObject metaExecutor, Object[] args)
			throws SQLException {
		MappedStatement ms = (MappedStatement) args[0];
		Object parameterObject = args[1];
		RowBounds rowBounds = (RowBounds) args[2];
		ResultHandler resultHandler = (ResultHandler) args[3];
		BoundSql boundSql = ms.getBoundSql(parameterObject);
		CacheKey cacheKey = createCacheKey(ms, parameterObject, rowBounds,
				boundSql);
		Executor executor = (Executor) metaExecutor.getOriginalObject();
		CacheKey cacheKey2 = createCacheKey(ms, parameterObject,boundSql);
		MemcachedCache cache = new MemcachedCache(cacheKey2.toString());
		Object total = cache.getObject(cacheKey2);
		 if (parameterObject instanceof Page<?> && null != total) {
			 Page<?> page = (Page<?>) parameterObject;
			 page.setTotal(Integer.valueOf(total.toString()));
		 }
		return executor.query(ms, parameterObject, rowBounds, resultHandler,
				cacheKey, boundSql);
	}

	private CacheKey createCacheKey(MappedStatement ms, Object parameterObject,
			RowBounds rowBounds, BoundSql boundSql) {
		CacheKey cacheKey = new CacheKey();
		cacheKey.update(ms.getId());
		cacheKey.update(rowBounds.getOffset());
		cacheKey.update(rowBounds.getLimit());
		List<ParameterMapping> parameterMappings = boundSql
				.getParameterMappings();
		// 解决自动生成SQL，SQL语句为空导致key生成错误的bug
		cacheKey.update(super.getPageSql((Page<?>) parameterObject, boundSql.getSql()));

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
		// 当需要分页查询时，将page参数里的当前页和每页数加到cachekey里
		 if (parameterObject instanceof Page<?>) {
			Page<?> page = (Page<?>) parameterObject;
			if (null != page) {
				cacheKey.update(page.getPage());
				cacheKey.update(page.getPageSize());
			}
		}
		return cacheKey;
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
	/**
	 * 只拦截CachingExecutor，其他的直接返回目标本身
	 */
	@Override
	public Object plugin(Object target) {
		if (target instanceof CachingExecutor) {
			return Plugin.wrap(target, this);
		} else {
			return target;
		}
	}

	@Override
	public void setProperties(Properties properties) {

	}

}
