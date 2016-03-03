package com.hichlink.funion.admin.modules.mgmt.web;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aspire.webbas.core.pagination.mybatis.pager.Page;
import com.aspire.webbas.core.web.BaseController;
import com.hichlink.funion.common.entity.WxPayRecord;
import com.hichlink.funion.common.service.WxPayRecordService;

/**
 * 
 * <b>Title：</b>WxPayRecordController.java<br/>
 * <b>Description：</b> <br/>
 * <b>@author： </b>oceanzhuang<br/>
 * <b>@date：</b>2015-11-28 14:10:24<br/>
 * <b>Copyright (c) 2015 ASPire Tech.</b>
 * 
 */
@Controller
@RequestMapping("/wxPayRecord")
public class WxPayRecordController extends BaseController {
	private static final Logger LOG = LoggerFactory.getLogger(WxPayRecordController.class);
	@Autowired
	@Qualifier("wxPayRecordService")
	private WxPayRecordService wxPayRecordService;

	@RequestMapping(value = "/query.ajax")
	@ResponseBody
	public Map<String, Object> pageQuery(Page<WxPayRecord> page) {
		Page<WxPayRecord> list = wxPayRecordService.pageQuery(page);
		return super.page(list);
	}

	@RequestMapping(value = "/add.ajax", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> add(@ModelAttribute("wxPayRecord") WxPayRecord data) {
		wxPayRecordService.saveAndUpdate(data);
		return super.success("新增成功");
	}

	@RequestMapping(value = "/delete.ajax")
	@ResponseBody
	public Map<String, Object> delete(Long paySeqId) {
		wxPayRecordService.delete(paySeqId);
		return super.success("删除成功");
	}

	@RequestMapping(value = "/get.ajax")
	@ResponseBody
	public Map<String, Object> get(Long paySeqId) {
		WxPayRecord data = wxPayRecordService.get(paySeqId);
		return super.success(data);
	}

	/**
	 * 更新的时候需额外传递updId,值跟主键值一样,被@ModelAttribute注释的方法会在此controller每个方法执行前被执行，
	 * 要谨慎使用
	 */
	@ModelAttribute("wxPayRecord")
	public void getForUpdate(@RequestParam(value = "updId", required = false) Long updId, Model model) {
		if (null != updId) {
			model.addAttribute("wxPayRecord", wxPayRecordService.get(updId));
		} else {
			model.addAttribute("wxPayRecord", new WxPayRecord());
		}
	}
}