package com.hichlink.funion.mh.common.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aspire.webbas.core.pagination.mybatis.pager.Page;
import com.aspire.webbas.core.util.StringTools;
import com.hichlink.funion.mh.common.dao.MobileHomeInfoMapper;
import com.hichlink.funion.mh.common.entity.MobileHomeInfo;


/**
 * 
 * <b>Title：</b>MobileHomeInfoService.java<br/>
 * <b>Description：</b> <br/>
 * <b>@author： </b>v5480<br/>
 * <b>@date：</b>2015-08-01 15:18:01<br/>
 * <b>Copyright (c) 2015 szwisdom Tech.</b>
 * 
 */
@Service("mobileHomeInfoService")
public class MobileHomeInfoService{
    @Autowired
    private MobileHomeInfoMapper mobileHomeInfoMapper;
    
    public Page<MobileHomeInfo> pageQuery(Page<MobileHomeInfo> page) {
    	List<MobileHomeInfo> list = mobileHomeInfoMapper.pageQuery(page);
		page.setDatas(list);
        return page;
    }
    public void insert(MobileHomeInfo data) {
        mobileHomeInfoMapper.insert(data);
    }
        public MobileHomeInfo get(String mobileNo) {
        return mobileHomeInfoMapper.selectByPrimaryKey(mobileNo);
    }
    public void saveAndUpdate(MobileHomeInfo data){
    			if (StringTools.isNotEmptyString(data.getMobileNo())){//判断有没有传主键，如果传了为更新，否则为新增
					this.update(data);
		}else{
			this.insert(data);
		}
    }
    public void update(MobileHomeInfo data) {
        mobileHomeInfoMapper.updateByPrimaryKey(data);
    }
    public int delete(String mobileNo) {
        return mobileHomeInfoMapper.deleteByPrimaryKey(mobileNo);
    }
    }