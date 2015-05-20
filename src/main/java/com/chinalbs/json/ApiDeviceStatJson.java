package com.chinalbs.json;

import com.chinalbs.entity.DeviceStatistics;

public class ApiDeviceStatJson extends ApiCommonJson{
  
    /** 设备数 */
    private DeviceStatistics device;

    public DeviceStatistics getDevice() {
      return device;
    }

    public void setDevice(DeviceStatistics device) {
      this.device = device;
    }

}
