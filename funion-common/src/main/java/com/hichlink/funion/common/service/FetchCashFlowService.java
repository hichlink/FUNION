package com.hichlink.funion.common.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.aspire.webbas.core.pagination.mybatis.pager.Page;

import java.math.BigDecimal;
import java.util.List;
import com.hichlink.funion.common.entity.FetchCashFlow;
import com.hichlink.funion.common.dao.FetchCashFlowMapper;

/**
 * 
 * <b>Title：</b>FetchCashFlowService.java<br/>
 * <b>Description：</b> <br/>
 * <b>@author： </b>oceanzhuang<br/>
 * <b>@date：</b>2015-11-28 14:10:24<br/>
 * <b>Copyright (c) 2015 ASPire Tech.</b>
 * 
 */
@Service("fetchCashFlowService")
public class FetchCashFlowService {
	@Autowired
	private FetchCashFlowMapper fetchCashFlowMapper;

	public Page<FetchCashFlow> pageQuery(Page<FetchCashFlow> page) {
		List<FetchCashFlow> list = fetchCashFlowMapper.pageQuery(page);
		page.setDatas(list);
		return page;
	}

	public void insert(FetchCashFlow data) {
		fetchCashFlowMapper.insert(data);
	}

	public FetchCashFlow get(Long flowId) {
		return fetchCashFlowMapper.selectByPrimaryKey(flowId);
	}

	public void saveAndUpdate(FetchCashFlow data) {
		if (null != data.getFlowId()) {// 判断有没有传主键，如果传了为更新，否则为新增
			this.update(data);
		} else {
			this.insert(data);
		}
	}
	public void updateBalance(BigDecimal balance){
		
	}
	public void update(FetchCashFlow data) {
		fetchCashFlowMapper.updateByPrimaryKey(data);
	}

	public int delete(Long flowId) {
		return fetchCashFlowMapper.deleteByPrimaryKey(flowId);
	}
}