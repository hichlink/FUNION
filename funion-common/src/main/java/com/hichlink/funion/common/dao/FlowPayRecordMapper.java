/*** Auto generator by codegenerator 2015-12-08 21:31:38*/
package com.hichlink.funion.common.dao;

import com.aspire.webbas.core.pagination.mybatis.pager.Page;
import com.hichlink.funion.common.entity.FlowPayRecord;
import java.util.List;

public interface FlowPayRecordMapper {
    int deleteByPrimaryKey(Long recordId);

    int insert(FlowPayRecord record);

    int insertSelective(FlowPayRecord record);

    List<FlowPayRecord> pageQuery(Page<FlowPayRecord> page);
    
    List<FlowPayRecord> find(FlowPayRecord record);

    FlowPayRecord selectByPrimaryKey(Long recordId);

    int updateByPrimaryKeySelective(FlowPayRecord record);

    int updateByPrimaryKey(FlowPayRecord record);
}
