package com.hichlink.funion.common.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.aspire.webbas.core.pagination.mybatis.pager.Page;
import java.util.List;
import com.hichlink.funion.common.entity.WxPayRecord;
import com.hichlink.funion.common.dao.WxPayRecordMapper;

/**
 * 
 * <b>Title：</b>WxPayRecordService.java<br/>
 * <b>Description：</b> <br/>
 * <b>@author： </b>oceanzhuang<br/>
 * <b>@date：</b>2015-11-28 14:10:24<br/>
 * <b>Copyright (c) 2015 ASPire Tech.</b>
 * 
 */
@Service("wxPayRecordService")
public class WxPayRecordService {
	@Autowired
	private WxPayRecordMapper wxPayRecordMapper;

	public Page<WxPayRecord> pageQuery(Page<WxPayRecord> page) {
		List<WxPayRecord> list = wxPayRecordMapper.pageQuery(page);
		page.setDatas(list);
		return page;
	}

	public void insert(WxPayRecord data) {
		wxPayRecordMapper.insert(data);
	}

	public WxPayRecord get(Long paySeqId) {
		return wxPayRecordMapper.selectByPrimaryKey(paySeqId);
	}

	public void saveAndUpdate(WxPayRecord data) {
		if (null != data.getPaySeqId()) {// 判断有没有传主键，如果传了为更新，否则为新增
			this.update(data);
		} else {
			this.insert(data);
		}
	}

	public void update(WxPayRecord data) {
		wxPayRecordMapper.updateByPrimaryKey(data);
	}

	public int delete(Long paySeqId) {
		return wxPayRecordMapper.deleteByPrimaryKey(paySeqId);
	}
	public WxPayRecord selectByOutTradeNo(String outTradeNo){
		return wxPayRecordMapper.selectByOutTradeNo(outTradeNo);
	}
}