package com.hichlink.funion.common.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.aspire.webbas.core.pagination.mybatis.pager.Page;
import java.util.List;
import com.hichlink.funion.common.entity.FlowPayRecord;
import com.hichlink.funion.common.dao.FlowPayRecordMapper;

/**
 * 
 * <b>Title：</b>FlowPayRecordService.java<br/>
 * <b>Description：</b> <br/>
 * <b>@author： </b>oceanzhuang<br/>
 * <b>@date：</b>2015-11-28 14:10:24<br/>
 * <b>Copyright (c) 2015 ASPire Tech.</b>
 * 
 */
@Service("flowPayRecordService")
public class FlowPayRecordService {
	@Autowired
	private FlowPayRecordMapper flowPayRecordMapper;

	public Page<FlowPayRecord> pageQuery(Page<FlowPayRecord> page) {
		List<FlowPayRecord> list = flowPayRecordMapper.pageQuery(page);
		page.setDatas(list);
		return page;
	}

	public void insert(FlowPayRecord data) {
		flowPayRecordMapper.insert(data);
	}

	public FlowPayRecord get(Long recordId) {
		return flowPayRecordMapper.selectByPrimaryKey(recordId);
	}

	public void saveAndUpdate(FlowPayRecord data) {
		if (null != data.getRecordId()) {// 判断有没有传主键，如果传了为更新，否则为新增
			this.update(data);
		} else {
			this.insert(data);
		}
	}

	public void update(FlowPayRecord data) {
		flowPayRecordMapper.updateByPrimaryKey(data);
	}

	public int delete(Long recordId) {
		return flowPayRecordMapper.deleteByPrimaryKey(recordId);
	}
}