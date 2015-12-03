package com.hichlink.funion.common.service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aspire.webbas.core.pagination.mybatis.pager.Page;
import com.hichlink.funion.common.dao.AgentInfoMapper;
import com.hichlink.funion.common.entity.AgentInfo;

/**
 * 
 * <b>Title：</b>AgentInfoService.java<br/>
 * <b>Description：</b> <br/>
 * <b>@author： </b>oceanzhuang<br/>
 * <b>@date：</b>2015-11-28 14:10:24<br/>
 * <b>Copyright (c) 2015 ASPire Tech.</b>
 * 
 */
@Service("agentInfoService")
public class AgentInfoService {
	@Autowired
	private AgentInfoMapper agentInfoMapper;

	public Page<AgentInfo> pageQuery(Page<AgentInfo> page) {
		List<AgentInfo> list = agentInfoMapper.pageQuery(page);
		page.setDatas(list);
		return page;
	}

	public void insert(AgentInfo data) {
		agentInfoMapper.insert(data);
	}

	public AgentInfo get(Long agentId) {
		return agentInfoMapper.selectByPrimaryKey(agentId);
	}

	public AgentInfo selectByOpenId(String openId) {
		return agentInfoMapper.selectByOpenId(openId);
	}

	public AgentInfo selectByUUId(String uuid) {
		return agentInfoMapper.selectByUUID(uuid);
	}

	public void saveAndUpdate(AgentInfo data) {
		if (null != data.getAgentId()) {// 判断有没有传主键，如果传了为更新，否则为新增
			this.update(data);
		} else {
			this.insert(data);
		}
	}

	public void update(AgentInfo data) {
		agentInfoMapper.updateByPrimaryKey(data);
	}

	public void updateBalance(Long agentId, BigDecimal balance) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("agentId", agentId);
		params.put("balance", balance);
		agentInfoMapper.updateBalance(params);
	}

	public int delete(Long agentId) {
		return agentInfoMapper.deleteByPrimaryKey(agentId);
	}
}