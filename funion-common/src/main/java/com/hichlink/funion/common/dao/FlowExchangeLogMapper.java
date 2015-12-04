/*** Auto generator by codegenerator 2015-11-28 14:10:38*/
package com.hichlink.funion.common.dao;

import com.aspire.webbas.core.pagination.mybatis.pager.Page;
import com.hichlink.funion.common.entity.FlowExchangeLog;
import java.util.List;

public interface FlowExchangeLogMapper {
    int deleteByPrimaryKey(Long logId);

    int insert(FlowExchangeLog record);

    int insertSelective(FlowExchangeLog record);

    List<FlowExchangeLog> pageQuery(Page<FlowExchangeLog> page);
    
    List<FlowExchangeLog> find(FlowExchangeLog record);

    FlowExchangeLog selectByPrimaryKey(Long logId);

    int updateByPrimaryKeySelective(FlowExchangeLog record);

    int updateByPrimaryKey(FlowExchangeLog record);
}
