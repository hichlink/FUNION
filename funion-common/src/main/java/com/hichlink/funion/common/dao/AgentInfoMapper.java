/*** Auto generator by codegenerator 2015-11-28 14:10:24*/
package com.hichlink.funion.common.dao;

import com.aspire.webbas.core.pagination.mybatis.pager.Page;
import com.hichlink.funion.common.entity.AgentInfo;
import java.util.List;

public interface AgentInfoMapper {
    int deleteByPrimaryKey(Long agentId);

    int insert(AgentInfo record);

    int insertSelective(AgentInfo record);

    List<AgentInfo> pageQuery(Page<AgentInfo> page);

    AgentInfo selectByPrimaryKey(Long agentId);

    int updateByPrimaryKeySelective(AgentInfo record);

    int updateByPrimaryKey(AgentInfo record);
}
