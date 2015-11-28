package com.hichlink.funion.common.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.aspire.webbas.core.pagination.mybatis.pager.Page;
import java.util.List;
import com.hichlink.funion.common.entity.FlowExchangeLog;
import com.hichlink.funion.common.dao.FlowExchangeLogMapper;


/**
 * 
 * <b>Title：</b>FlowExchangeLogService.java<br/>
 * <b>Description：</b> <br/>
 * <b>@author： </b>oceanzhuang<br/>
 * <b>@date：</b>2015-11-28 14:10:24<br/>
 * <b>Copyright (c) 2015 ASPire Tech.</b>
 * 
 */
@Service("flowExchangeLogService")
public class FlowExchangeLogService{
    @Autowired
    private FlowExchangeLogMapper flowExchangeLogMapper;
    
    public Page<FlowExchangeLog> pageQuery(Page<FlowExchangeLog> page) {
    	List<FlowExchangeLog> list = flowExchangeLogMapper.pageQuery(page);
		page.setDatas(list);
        return page;
    }
    public void insert(FlowExchangeLog data) {
        flowExchangeLogMapper.insert(data);
    }
        public FlowExchangeLog get(Long logId) {
        return flowExchangeLogMapper.selectByPrimaryKey(logId);
    }
    public void saveAndUpdate(FlowExchangeLog data){
    			if (null != data.getLogId()){//判断有没有传主键，如果传了为更新，否则为新增
					this.update(data);
		}else{
			this.insert(data);
		}
    }
    public void update(FlowExchangeLog data) {
        flowExchangeLogMapper.updateByPrimaryKey(data);
    }
    public int delete(Long logId) {
        return flowExchangeLogMapper.deleteByPrimaryKey(logId);
    }
    }