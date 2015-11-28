/*** Auto generator by codegenerator 2015-11-28 14:10:35*/
package com.hichlink.funion.common.dao;

import com.aspire.webbas.core.pagination.mybatis.pager.Page;
import com.hichlink.funion.common.entity.FetchCashFlow;
import java.util.List;

public interface FetchCashFlowMapper {
    int deleteByPrimaryKey(Long flowId);

    int insert(FetchCashFlow record);

    int insertSelective(FetchCashFlow record);

    List<FetchCashFlow> pageQuery(Page<FetchCashFlow> page);

    FetchCashFlow selectByPrimaryKey(Long flowId);

    int updateByPrimaryKeySelective(FetchCashFlow record);

    int updateByPrimaryKey(FetchCashFlow record);
}
