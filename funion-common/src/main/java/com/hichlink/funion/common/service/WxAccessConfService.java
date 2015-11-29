package com.hichlink.funion.common.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aspire.webbas.core.exception.MyException;
import com.aspire.webbas.core.pagination.mybatis.pager.Page;
import java.util.List;
import com.hichlink.funion.common.entity.WxAccessConf;
import com.hichlink.funion.common.dao.WxAccessConfMapper;

/**
 * 
 * <b>Title：</b>WxAccessConfService.java<br/>
 * <b>Description：</b> <br/>
 * <b>@author： </b>oceanzhuang<br/>
 * <b>@date：</b>2015-11-28 14:10:24<br/>
 * <b>Copyright (c) 2015 ASPire Tech.</b>
 * 
 */
@Service("wxAccessConfService")
public class WxAccessConfService {
	@Autowired
	private WxAccessConfMapper wxAccessConfMapper;

	public Page<WxAccessConf> pageQuery(Page<WxAccessConf> page) {
		List<WxAccessConf> list = wxAccessConfMapper.pageQuery(page);
		page.setDatas(list);
		return page;
	}

	public void insert(WxAccessConf data) {
		wxAccessConfMapper.insert(data);
	}

	public WxAccessConf get(Long seqId) {
		return wxAccessConfMapper.selectByPrimaryKey(seqId);
	}

	public WxAccessConf getWxAccessConf(String appId) {
		WxAccessConf bean = new WxAccessConf();
		bean.setAppId(appId);
		WxAccessConf wc = wxAccessConfMapper.getByWxConf(bean);
		if (null == wc) {
			throw new MyException("根据AppId[" + appId + "]取不到对应的微信公众号配置");
		}
		return wc;
	}

	public void saveAndUpdate(WxAccessConf data) {
		if (null != data.getSeqId()) {// 判断有没有传主键，如果传了为更新，否则为新增
			this.update(data);
		} else {
			this.insert(data);
		}
	}

	public void update(WxAccessConf data) {
		wxAccessConfMapper.updateByPrimaryKey(data);
	}

	public int delete(Long seqId) {
		return wxAccessConfMapper.deleteByPrimaryKey(seqId);
	}
}