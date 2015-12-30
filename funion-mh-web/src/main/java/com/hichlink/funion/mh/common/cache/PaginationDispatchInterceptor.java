package com.hichlink.funion.mh.common.cache;

import java.sql.Connection;

import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Signature;

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
	public void dispatch(Page<?> page, MappedStatement mappedStatement,
			BoundSql boundSql) {
		if (mappedStatement.getConfiguration().isCacheEnabled()){
		}
	}

}
