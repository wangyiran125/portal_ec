package com.chinalbs.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;




import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Entity - 设备统计信息
 * 
 * 该Entity 不用使用JPA ,因为会去连接客户数据库来获取数据
 * 
 * @author sujinxuan
 * 
 */
@JsonAutoDetect(fieldVisibility = Visibility.NONE, getterVisibility = Visibility.NONE,
setterVisibility = Visibility.NONE, isGetterVisibility = Visibility.NONE,
creatorVisibility = Visibility.NONE)
@Entity
@Table(name = "us_device_stat")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "us_device_stat_sequence")
@JsonIgnoreProperties(ignoreUnknown = true)
public class DeviceStatistics implements Serializable{

  private static final long serialVersionUID = 7072498891401083011L;
 
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO, generator = "sequenceGenerator")
  private Long fId;
  
  /** 用户ID */
  private Integer fServiceId;
  
  /** 统计时间 */
  private Date fStatDate;
  
  /** 总设备数 */
  private Integer fTotalDevice;
  
  /** 新增绑定设备 */
  private Integer fInstallDevice;
  
  /** 绑定设备总数 */
  private Integer fInstallTotalDevice;
  
  /** 绑定设备占总设备比率  */
  private BigDecimal fInstallRate;
  
  /** 新增设备占已绑定设备比率 */
  private BigDecimal fNewInstallRate;
  @JsonProperty
  public Integer getfServiceId() {
    return fServiceId;
  }

  public void setfServiceId(Integer fServiceId) {
    this.fServiceId = fServiceId;
  }

  
  @JsonProperty
  public Date getfStatDate() {
    return fStatDate;
  }

  public void setfStatDate(Date fStatDate) {
    this.fStatDate = fStatDate;
  }
  @JsonProperty
  public Integer getfTotalDevice() {
    return fTotalDevice;
  }
  @JsonProperty
  public void setfTotalDevice(Integer fTotalDevice) {
    this.fTotalDevice = fTotalDevice;
  }

  @JsonProperty
  public Integer getfInstallDevice() {
    return fInstallDevice;
  }

  public void setfInstallDevice(Integer fInstallDevice) {
    this.fInstallDevice = fInstallDevice;
  }

  @JsonProperty
  public Integer getfInstallTotalDevice() {
    return fInstallTotalDevice;
  }

  public void setfInstallTotalDevice(Integer fInstallTotalDevice) {
    this.fInstallTotalDevice = fInstallTotalDevice;
  }
  @JsonProperty
  public Long getfId() {
    return fId;
  }

  public void setfId(Long fId) {
    this.fId = fId;
  }
  @JsonProperty
  public BigDecimal getfInstallRate() {
    return fInstallRate;
  }

  public void setfInstallRate(BigDecimal fInstallRate) {
    this.fInstallRate = fInstallRate;
  }
  @JsonProperty
  public BigDecimal getfNewInstallRate() {
    return fNewInstallRate;
  }

  public void setfNewInstallRate(BigDecimal fNewInstallRate) {
    this.fNewInstallRate = fNewInstallRate;
  }
  
}
