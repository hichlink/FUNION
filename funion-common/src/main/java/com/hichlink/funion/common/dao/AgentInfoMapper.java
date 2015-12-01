/*** Auto generator by codegenerator 2015-11-28 16:10:47*/
package com.hichlink.funion.common.dao;

import java.util.List;
import java.util.Map;

import com.aspire.webbas.core.pagination.mybatis.pager.Page;
import com.hichlink.funion.common.entity.AgentInfo;

public interface AgentInfoMapper {
    int deleteByPrimaryKey(Long agentId);

    int insert(AgentInfo record);

    int insertSelective(AgentInfo record);

    List<AgentInfo> pageQuery(Page<AgentInfo> page);

    AgentInfo selectByPrimaryKey(Long agentId);
    
    AgentInfo selectByOpenId(String openId);

    int updateByPrimaryKeySelective(AgentInfo record);

    int updateByPrimaryKey(AgentInfo record);
    
    int updateBalance(Map<String,Object> params);
}
