package com.chinalbs.unionprimary;

import java.io.Serializable;

public class SpotPK implements Serializable {
  /**
   * 
   */
  private static final long serialVersionUID = -8302215571771386110L;

  /** 设备SN */
  private String fDeviceSn;

  /** 接收时间 */
  private Long fReceive;

  public String getfDeviceSn() {
    return fDeviceSn;
  }

  public void setfDeviceSn(String fDeviceSn) {
    this.fDeviceSn = fDeviceSn;
  }

  public Long getfReceive() {
    return fReceive;
  }

  public void setfReceive(Long fReceive) {
    this.fReceive = fReceive;
  }
}
