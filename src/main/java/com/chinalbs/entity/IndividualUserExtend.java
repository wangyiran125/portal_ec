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
 * Entity - 个人用户信息扩展
 * 
 * @author shijun
 * 
 */
@Entity
@Table(name = "ec_individual_user_extend")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "ec_individual_user_extend_sequence")
public class IndividualUserExtend extends BaseEntity {

  /**
   * 
   */
  private static final long serialVersionUID = 7150635919484277535L;

  /** 姓名 */
  private String name;

  /** 地址 */
  private String address;
  /**身份证号*/
  private String identityCard;

  /** 电子邮箱 */
  private String email;

  /** 手机号码 */
  private String cellPhoneNum;

  /** 座机号码 */
  private String phoneNum;

  /** 用户 */
  private User user;

  @Column(length = 50)
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  @Column(length = 100)
  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  @Column(length = 20)
  public String getIdentityCard() {
    return identityCard;
  }

  public void setIdentityCard(String identityCard) {
    this.identityCard = identityCard;
  }

  @Column(length = 50)
  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  @Column(length = 20)
  public String getCellPhoneNum() {
    return cellPhoneNum;
  }

  public void setCellPhoneNum(String cellPhoneNum) {
    this.cellPhoneNum = cellPhoneNum;
  }

  @Column(length = 20)
  public String getPhoneNum() {
    return phoneNum;
  }

  public void setPhoneNum(String phoneNum) {
    this.phoneNum = phoneNum;
  }

  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user")
  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  @Override
  public String toString() {
    return "IndividualUserExtend [name=" + name + ", address=" + address + ", identityCard="
        + identityCard + ", email=" + email + ", cellPhoneNum=" + cellPhoneNum + ", phoneNum="
        + phoneNum + ", user=" + user + "]";
  }

  
}
