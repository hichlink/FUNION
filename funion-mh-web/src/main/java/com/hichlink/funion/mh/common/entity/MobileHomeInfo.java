/*** Auto generator by codegenerator 2015-08-01 15:30:54*/
package com.hichlink.funion.mh.common.entity;

import java.util.Date;

public class MobileHomeInfo {
	/**
	 * 手机号码前7位
	 */
	private String mobileNo;

	/**
	 * 运营商名称
	 */
	private String operatorName;

	/**
	 * 省份
	 */
	private String province;

	/**
	 * 城市
	 */
	private String city;

	private String areaCode;
	/**
	 * 运营商编码
	 */
	private String operatorCode;

	/**
	 * 运营商编码
	 */
	private String operator;

	/**
	 * 创建日期
	 */
	private Date createDate;

	/**
	 * 修改日期
	 */
	private Date modifyDate;

	/**
	 * 手机号码前7位
	 * 
	 * @return the value of mobile_home_info.mobile_no
	 */
	public String getMobileNo() {
		return mobileNo;
	}

	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo == null ? null : mobileNo.trim();
	}

	/**
	 * 运营商名称
	 * 
	 * @return the value of mobile_home_info.operator_name
	 */
	public String getOperatorName() {
		return operatorName;
	}

	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName == null ? null : operatorName.trim();
	}

	/**
	 * 省份
	 * 
	 * @return the value of mobile_home_info.province
	 */
	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province == null ? null : province.trim();
	}

	/**
	 * 城市
	 * 
	 * @return the value of mobile_home_info.city
	 */
	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city == null ? null : city.trim();
	}

	/**
	 * 运营商编码
	 * 
	 * @return the value of mobile_home_info.operator_code
	 */
	public String getOperatorCode() {
		return operatorCode;
	}

	public void setOperatorCode(String operatorCode) {
		this.operatorCode = operatorCode == null ? null : operatorCode.trim();
	}

	/**
	 * 创建日期
	 * 
	 * @return the value of mobile_home_info.create_date
	 */
	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	/**
	 * 修改日期
	 * 
	 * @return the value of mobile_home_info.modify_date
	 */
	public Date getModifyDate() {
		return modifyDate;
	}

	public void setModifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
	}

	public String getAreaCode() {
		return areaCode;
	}

	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}
}
