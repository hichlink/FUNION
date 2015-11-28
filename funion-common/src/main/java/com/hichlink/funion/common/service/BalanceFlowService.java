package com.hichlink.funion.common.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.aspire.webbas.core.pagination.mybatis.pager.Page;
import java.util.List;
import com.hichlink.funion.common.entity.BalanceFlow;
import com.hichlink.funion.common.dao.BalanceFlowMapper;


/**
 * 
 * <b>Title：</b>BalanceFlowService.java<br/>
 * <b>Description：</b> <br/>
 * <b>@author： </b>oceanzhuang<br/>
 * <b>@date：</b>2015-11-28 14:10:24<br/>
 * <b>Copyright (c) 2015 ASPire Tech.</b>
 * 
 */
@Service("balanceFlowService")
public class BalanceFlowService{
    @Autowired
    private BalanceFlowMapper balanceFlowMapper;
    
    public Page<BalanceFlow> pageQuery(Page<BalanceFlow> page) {
    	List<BalanceFlow> list = balanceFlowMapper.pageQuery(page);
		page.setDatas(list);
        return page;
    }
    public void insert(BalanceFlow data) {
        balanceFlowMapper.insert(data);
    }
        public BalanceFlow get(Long flowId) {
        return balanceFlowMapper.selectByPrimaryKey(flowId);
    }
    public void saveAndUpdate(BalanceFlow data){
    			if (null != data.getFlowId()){//判断有没有传主键，如果传了为更新，否则为新增
					this.update(data);
		}else{
			this.insert(data);
		}
    }
    public void update(BalanceFlow data) {
        balanceFlowMapper.updateByPrimaryKey(data);
    }
    public int delete(Long flowId) {
        return balanceFlowMapper.deleteByPrimaryKey(flowId);
    }
    }