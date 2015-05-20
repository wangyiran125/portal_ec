package com.chinalbs.entity;

import java.io.Serializable;


import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;




/**
 * Entity - 用户信息
 * 
 * 该Entity 不用使用JPA ,因为会去连接客户数据库来获取数据
 * 
 * @author sujinxuan
 * 
 */
@Entity
@Table(name = "us_user")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "us_user_sequence")
public class UsUser implements Serializable{

  private static final long serialVersionUID = -6432728480051304252L;
  
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO, generator = "sequenceGenerator")
  /** 主键ID */
  private Long fId;
  
  /** Email */
  private String fEmail;
  
  /** 用户名 */
  private String fName;

  /** 密码 */
  private String fPassword;

  /** 电话 */
  private String fPhone;

  /** QQ账号 */
  private String fQqId;

  /** 人人网账号 */
  private String fRrId;

  /** 新浪账号 */
  private String fSinaId;

  /** 父节点 */
  private Long fParentId;

  /** 用户类型 */
  private Integer fType;

  /** 用户角色 */
  private Integer fRole;

  /** 昵称 */
  private String fNick;

  /** app name */
  private String fAppName;

  /** 真实姓名 */
  private String fReal;

  /** 地址 */
  private String fAddress;

  /** 创建时间 */
  private Long fTime;
  
  /** 是否显示 */
//  private Integer isshow;

  /** (服务后台)用户模式：(1开发者模式or2后台模式) */
  private Integer fUserMode;

  /** (服务后台)用户种类：(1企业or2个人) */
  private Integer fUserType;

  /** (服务后台)开发者的apiKey */
  private String fApiKey;

  /** (服务后台)所隶属的服务后台用户id */
  private Long fServiceId;

  /** 用户所属平台(1后台用户or2前台用户) */
  private Integer fPlatform;

  public Long getfId() {
    return fId;
  }

  public void setfId(Long fId) {
    this.fId = fId;
  }

  public String getfEmail() {
    return fEmail;
  }

  public void setfEmail(String fEmail) {
    this.fEmail = fEmail;
  }

  public String getfName() {
    return fName;
  }

  public void setfName(String fName) {
    this.fName = fName;
  }

  public String getfPassword() {
    return fPassword;
  }

  public void setfPassword(String fPassword) {
    this.fPassword = fPassword;
  }

  public String getfPhone() {
    return fPhone;
  }

  public void setfPhone(String fPhone) {
    this.fPhone = fPhone;
  }

  public String getfQqId() {
    return fQqId;
  }

  public void setfQqId(String fQqId) {
    this.fQqId = fQqId;
  }

  public String getfRrId() {
    return fRrId;
  }

  public void setfRrId(String fRrId) {
    this.fRrId = fRrId;
  }

  public String getfSinaId() {
    return fSinaId;
  }

  public void setfSinaId(String fSinaId) {
    this.fSinaId = fSinaId;
  }

  public Long getfParentId() {
    return fParentId;
  }

  public void setfParentId(Long fParentId) {
    this.fParentId = fParentId;
  }

  public Integer getfType() {
    return fType;
  }

  public void setfType(Integer fType) {
    this.fType = fType;
  }

  public Integer getfRole() {
    return fRole;
  }

  public void setfRole(Integer fRole) {
    this.fRole = fRole;
  }

  public String getfNick() {
    return fNick;
  }

  public void setfNick(String fNick) {
    this.fNick = fNick;
  }

  public String getfAppName() {
    return fAppName;
  }

  public void setfAppName(String fAppName) {
    this.fAppName = fAppName;
  }

  public String getfReal() {
    return fReal;
  }

  public void setfReal(String fReal) {
    this.fReal = fReal;
  }

  public String getfAddress() {
    return fAddress;
  }

  public void setfAddress(String fAddress) {
    this.fAddress = fAddress;
  }

  public Date getfTime() {
    if(fTime!=null){
      return new Date(fTime);
    }
    return null;
  }

  public void setfTime(Long fTime) {
    this.fTime = fTime;
  }


//  public Integer getIsshow() {
//    return isshow;
//  }
//
//  public void setIsshow(Integer isshow) {
//    this.isshow = isshow;
//  }

  public Integer getfUserMode() {
    return fUserMode;
  }

  public void setfUserMode(Integer fUserMode) {
    this.fUserMode = fUserMode;
  }

  public Integer getfUserType() {
    return fUserType;
  }

  public void setfUserType(Integer fUserType) {
    this.fUserType = fUserType;
  }

  public String getfApiKey() {
    return fApiKey;
  }

  public void setfApiKey(String fApiKey) {
    this.fApiKey = fApiKey;
  }

  public Long getfServiceId() {
    return fServiceId;
  }

  public void setfServiceId(Long fServiceId) {
    this.fServiceId = fServiceId;
  }

  public Integer getfPlatform() {
    return fPlatform;
  }

  public void setfPlatform(Integer fPlatform) {
    this.fPlatform = fPlatform;
  }

 
  
  
}
