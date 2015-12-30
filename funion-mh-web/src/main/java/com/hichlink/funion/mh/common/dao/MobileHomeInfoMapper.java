/*** Auto generator by codegenerator 2015-08-01 15:30:54*/
package com.hichlink.funion.mh.common.dao;

import java.util.List;

import com.aspire.webbas.core.pagination.mybatis.pager.Page;
import com.hichlink.funion.mh.common.entity.MobileHomeInfo;

public interface MobileHomeInfoMapper {
    int deleteByPrimaryKey(String mobileNo);

    int insert(MobileHomeInfo record);

    int insertSelective(MobileHomeInfo record);

    List<MobileHomeInfo> pageQuery(Page<MobileHomeInfo> page);

    MobileHomeInfo selectByPrimaryKey(String mobileNo);

    int updateByPrimaryKeySelective(MobileHomeInfo record);

    int updateByPrimaryKey(MobileHomeInfo record);
}
