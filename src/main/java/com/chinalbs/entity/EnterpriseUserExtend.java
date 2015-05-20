package com.chinalbs.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.chinalbs.framework.entity.BaseEntity;

/**
 * Entity - 企业用户基础信息扩展
 * 
 * @author shijun
 * 
 */
@Entity
@Table(name = "ec_enterprise_user_extend")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "ec_enterprise_user_extend_sequence")
public class EnterpriseUserExtend extends BaseEntity {

  /**
   * 
   */
  private static final long serialVersionUID = 5866555484163115259L;

  /** 公司名称 */
  private String companyName;

  /** 公司地址 */
  private String companyAddress;

  /** 公司联系人 */
  private String contactPerson;

  /** 联系邮箱 */
  private String contactEmail;

  /** 联系手机 */
  private String contactCellPhoneNum;

  /** 联系座机 */
  private String contactPhoneNum;

  /** 营业执照号码 */
  private String licenseNum;

  /** 组织结构代码 */
  private String organizationCode;
  
  /** 上传的营业执照的存放路径*/
  private String licenseImagePath;


  /** 用户 */
  private User user;

  @Column(length = 100)
  public String getCompanyName() {
    return companyName;
  }

  public void setCompanyName(String companyName) {
    this.companyName = companyName;
  }

  @Column(length = 100)
  public String getCompanyAddress() {
    return companyAddress;
  }

  public void setCompanyAddress(String companyAddress) {
    this.companyAddress = companyAddress;
  }

  @Column(length = 50)
  public String getContactPerson() {
    return contactPerson;
  }

  public void setContactPerson(String contactPerson) {
    this.contactPerson = contactPerson;
  }

  @Column(length = 50)
  public String getContactEmail() {
    return contactEmail;
  }

  public void setContactEmail(String contactEmail) {
    this.contactEmail = contactEmail;
  }

  @Column(length = 20)
  public String getContactCellPhoneNum() {
    return contactCellPhoneNum;
  }

  public void setContactCellPhoneNum(String contactCellPhoneNum) {
    this.contactCellPhoneNum = contactCellPhoneNum;
  }

  @Column(length = 20)
  public String getContactPhoneNum() {
    return contactPhoneNum;
  }

  public void setContactPhoneNum(String contactPhoneNum) {
    this.contactPhoneNum = contactPhoneNum;
  }

  @Column(length = 80)
  public String getLicenseNum() {
    return licenseNum;
  }

  public void setLicenseNum(String licenseNum) {
    this.licenseNum = licenseNum;
  }
  
  public String getLicenseImagePath() {
    return licenseImagePath;
  }

  public void setLicenseImagePath(String licenseImagePath) {
    this.licenseImagePath = licenseImagePath;
  }


  @Column(length = 80)
  public String getOrganizationCode() {
    return organizationCode;
  }

  public void setOrganizationCode(String organizationCode) {
    this.organizationCode = organizationCode;
  }

  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user")
  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

}
