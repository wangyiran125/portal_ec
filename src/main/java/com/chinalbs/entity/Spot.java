package com.chinalbs.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.chinalbs.unionprimary.SpotPK;

@Entity
@Table(name = "us_spot")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "us_spot_sequence")
@IdClass(SpotPK.class)
public class Spot implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = -7017141124456324754L;

  public Spot() {
    super();
  }

  /** 设备SN */
  @Id
  private String fDeviceSn;

  /** 接收时间 */
  @Id
  private Long fReceive;

  /** 设备上传时间 */
  private Long fSystime;

  public Long getfReceive() {
    return fReceive;
  }

  public void setfReceive(Long fReceive) {
    this.fReceive = fReceive;
  }

  public String getfDeviceSn() {
    return fDeviceSn;
  }

  public void setfDeviceSn(String fDeviceSn) {
    this.fDeviceSn = fDeviceSn;
  }

  public Long getfSystime() {
    return fSystime;
  }

  public void setfSystime(Long fSystime) {
    this.fSystime = fSystime;
  }

}
