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
 * Entity - 用户统计信息
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
@Table(name = "us_user_stat")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "us_user_stat_sequence")
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserStatistics implements Serializable{

  private static final long serialVersionUID = 1801675337112572751L;

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO, generator = "sequenceGenerator")
  private Long fId;
  
  /** 用户ID */
  private Integer fServiceId;
  
  /** 统计时间 */
  private Date fStatDate;
  
  /** 新增用户 */
  private Integer fUserNew;
  
  /** 累计用户 */
  private Integer fUserTotal;
  
  /** 活跃用户*/
  private Integer fUserActive;
  
  /** 同比  */
  private BigDecimal fMonthRate;
  
  /** 环比 */
  private BigDecimal fDayRate;
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
  public Long getfId() {
    return fId;
  }

  public void setfId(Long fId) {
    this.fId = fId;
  }

  @JsonProperty
  public Integer getfUserNew() {
    return fUserNew;
  }

  public void setfUserNew(Integer fUserNew) {
    this.fUserNew = fUserNew;
  }

  @JsonProperty
  public Integer getfUserTotal() {
    return fUserTotal;
  }

  public void setfUserTotal(Integer fUserTotal) {
    this.fUserTotal = fUserTotal;
  }

  @JsonProperty
  public Integer getfUserActive() {
    return fUserActive;
  }

  public void setfUserActive(Integer fUserActive) {
    this.fUserActive = fUserActive;
  }

  @JsonProperty
  public BigDecimal getfMonthRate() {
    return fMonthRate;
  }

  public void setfMonthRate(BigDecimal fMonthRate) {
    this.fMonthRate = fMonthRate;
  }

  @JsonProperty
  public BigDecimal getfDayRate() {
    return fDayRate;
  }

  public void setfDayRate(BigDecimal fDayRate) {
    this.fDayRate = fDayRate;
  }

  
}
