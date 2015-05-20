package com.chinalbs.entity;

/***/
public class Simulator {
  
  /**设备编号*/
  private String sn;
  
  /**设备类型*/
  private Integer deviceType;
  
  /**经度*/
  private Double longitude;
  
  /**纬度*/
  private Double latitude;

  public String getSn() {
    return sn;
  }

  public enum DeviceType{
    M2616(1),OBD(2),MT90(3),T808(4);
    private int value = 0;
    private DeviceType(int value) {   
        this.value = value;
    }
    public static DeviceType valueOf(int value) {  
        switch (value) {
        case 1:
            return M2616;
        case 2:
            return OBD;
        case 3:
          return MT90;
        case 4:
          return T808;
        default:
            return null;
        }
    }
    public int value() {
        return this.value;
    }
  }
  
  public void setSn(String sn) {
    this.sn = sn;
  }
  
  public Integer getDeviceType() {
    return deviceType;
  }

  public void setDeviceType(Integer deviceType) {
    this.deviceType = deviceType;
  }



  public Double getLongitude() {
    return longitude;
  }

  public void setLongitude(Double longitude) {
    this.longitude = longitude;
  }

  public Double getLatitude() {
    return latitude;
  }

  public void setLatitude(Double latitude) {
    this.latitude = latitude;
  }

  @Override
  public String toString() {
    return "Simulator [sn=" + sn + ", deviceType=" + deviceType + ", longitude=" + longitude
        + ", latitude=" + latitude + "]";
  }
  
  
  
  
}
