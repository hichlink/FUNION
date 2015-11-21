/*** Auto generator by codegenerator 2015-09-09 10:22:21*/
package com.hichlink.funion.common.dao;

import java.util.List;

import com.aspire.webbas.core.pagination.mybatis.pager.Page;
import com.hichlink.funion.common.entity.WxAccessConf;

public interface WxAccessConfMapper {
    int deleteByPrimaryKey(Long seqId);

    int insert(WxAccessConf record);

    int insertSelective(WxAccessConf record);

    List<WxAccessConf> pageQuery(Page<WxAccessConf> page);

    WxAccessConf selectByPrimaryKey(Long seqId);

    int updateByPrimaryKeySelective(WxAccessConf record);

    int updateByPrimaryKey(WxAccessConf record);

	WxAccessConf getByActId(String actId);

	WxAccessConf getByCustomerId(Long customerId);
	
	WxAccessConf getByWxConf(WxAccessConf wxAccessConf);
}
