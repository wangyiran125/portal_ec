package com.chinalbs.entity;

import java.io.Serializable;


import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.chinalbs.entity.Device.Isvirtual;




/**
 * Entity - 用户绑定设备信息
 * 
 * 该Entity 不用使用JPA ,因为会去连接客户数据库来获取数据
 * 
 * @author sujinxuan
 * 
 */
@Entity
@Table(name = "us_user_device")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "us_user_device_sequence")
public class UsBindDevice implements Serializable{

  private static final long serialVersionUID = -5862597556430919511L;

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO, generator = "sequenceGenerator")
  /** 主键ID */
  private Long fId;
  
  /** 设备IMEI号 */
  private String fDeviceSn;
  
  /** 绑定设备用户ID */
  private String fUserId;

  /** 绑定时间 */
  private Long fTime;
  
  /**
   * 是否虚拟设备（0：真实设备，1:虚拟设备）
   */
  @Transient
  private Isvirtual fIsvirtual;
  
  @Transient
  /** 设备协议 */
  private Integer fProtocol;
  
  @Transient
  /** 设备sim卡号 */
  private String fPhone;

  @Transient
  /** 设备类型(车，宠物，人) */
  private Integer fType;

  

  public Long getfId() {
    return fId;
  }

  public void setfId(Long fId) {
    this.fId = fId;
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

  public String getfDeviceSn() {
    return fDeviceSn;
  }

  public void setfDeviceSn(String fDeviceSn) {
    this.fDeviceSn = fDeviceSn;
  }

  public String getfUserId() {
    return fUserId;
  }

  public void setfUserId(String fUserId) {
    this.fUserId = fUserId;
  }

  public Isvirtual getfIsvirtual() {
    return fIsvirtual;
  }

  public void setfIsvirtual(Isvirtual fIsvirtual) {
    this.fIsvirtual = fIsvirtual;
  }

  public Integer getfProtocol() {
    return fProtocol;
  }

  public void setfProtocol(Integer fProtocol) {
    this.fProtocol = fProtocol;
  }

  public String getfPhone() {
    return fPhone;
  }

  public void setfPhone(String fPhone) {
    this.fPhone = fPhone;
  }

  public Integer getfType() {
    return fType;
  }

  public void setfType(Integer fType) {
    this.fType = fType;
  }
  
  

} 
