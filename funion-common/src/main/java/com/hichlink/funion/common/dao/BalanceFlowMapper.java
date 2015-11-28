/*** Auto generator by codegenerator 2015-11-28 14:10:29*/
package com.hichlink.funion.common.dao;

import com.aspire.webbas.core.pagination.mybatis.pager.Page;
import com.hichlink.funion.common.entity.BalanceFlow;
import java.util.List;

public interface BalanceFlowMapper {
    int deleteByPrimaryKey(Long flowId);

    int insert(BalanceFlow record);

    int insertSelective(BalanceFlow record);

    List<BalanceFlow> pageQuery(Page<BalanceFlow> page);

    BalanceFlow selectByPrimaryKey(Long flowId);

    int updateByPrimaryKeySelective(BalanceFlow record);

    int updateByPrimaryKey(BalanceFlow record);
}
