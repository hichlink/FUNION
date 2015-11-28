/*** Auto generator by codegenerator 2015-11-28 14:10:46*/
package com.hichlink.funion.common.dao;

import com.aspire.webbas.core.pagination.mybatis.pager.Page;
import com.hichlink.funion.common.entity.FlowProductInfo;
import java.util.List;

public interface FlowProductInfoMapper {
    int deleteByPrimaryKey(Long productId);

    int insert(FlowProductInfo record);

    int insertSelective(FlowProductInfo record);

    List<FlowProductInfo> pageQuery(Page<FlowProductInfo> page);

    FlowProductInfo selectByPrimaryKey(Long productId);

    int updateByPrimaryKeySelective(FlowProductInfo record);

    int updateByPrimaryKey(FlowProductInfo record);
}
