/*** Auto generator by codegenerator 2015-11-28 14:10:55*/
package com.hichlink.funion.common.dao;

import com.aspire.webbas.core.pagination.mybatis.pager.Page;
import com.hichlink.funion.common.entity.WxPayRecord;
import java.util.List;

public interface WxPayRecordMapper {
    int deleteByPrimaryKey(Long paySeqId);

    int insert(WxPayRecord record);

    int insertSelective(WxPayRecord record);

    List<WxPayRecord> pageQuery(Page<WxPayRecord> page);

    WxPayRecord selectByPrimaryKey(Long paySeqId);

    int updateByPrimaryKeySelective(WxPayRecord record);

    int updateByPrimaryKey(WxPayRecord record);
}
