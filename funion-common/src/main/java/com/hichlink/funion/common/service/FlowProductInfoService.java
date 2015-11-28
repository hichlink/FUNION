package com.hichlink.funion.common.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.aspire.webbas.core.pagination.mybatis.pager.Page;
import java.util.List;
import com.hichlink.funion.common.entity.FlowProductInfo;
import com.hichlink.funion.common.dao.FlowProductInfoMapper;


/**
 * 
 * <b>Title：</b>FlowProductInfoService.java<br/>
 * <b>Description：</b> <br/>
 * <b>@author： </b>oceanzhuang<br/>
 * <b>@date：</b>2015-11-28 14:10:24<br/>
 * <b>Copyright (c) 2015 ASPire Tech.</b>
 * 
 */
@Service("flowProductInfoService")
public class FlowProductInfoService{
    @Autowired
    private FlowProductInfoMapper flowProductInfoMapper;
    
    public Page<FlowProductInfo> pageQuery(Page<FlowProductInfo> page) {
    	List<FlowProductInfo> list = flowProductInfoMapper.pageQuery(page);
		page.setDatas(list);
        return page;
    }
    public void insert(FlowProductInfo data) {
        flowProductInfoMapper.insert(data);
    }
        public FlowProductInfo get(Long productId) {
        return flowProductInfoMapper.selectByPrimaryKey(productId);
    }
    public void saveAndUpdate(FlowProductInfo data){
    			if (null != data.getProductId()){//判断有没有传主键，如果传了为更新，否则为新增
					this.update(data);
		}else{
			this.insert(data);
		}
    }
    public void update(FlowProductInfo data) {
        flowProductInfoMapper.updateByPrimaryKey(data);
    }
    public int delete(Long productId) {
        return flowProductInfoMapper.deleteByPrimaryKey(productId);
    }
    }