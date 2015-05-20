package com.chinalbs.dao;

import java.util.List;

import com.chinalbs.entity.Device;
import com.chinalbs.entity.Spot;
import com.chinalbs.framework.dao.BaseDao;
import com.chinalbs.framework.paging.Page;
import com.chinalbs.framework.paging.Pageable;

public interface DeviceDao extends BaseDao<Device,String>{
  
  Page<Device> findDeviceWithServiceId (Long userId,Pageable pageable);
  
  /**
   * 检查设备是否绑定
   * @param sn
   * @return true绑定 false 没有绑定
   */
  boolean checkDeviceIsBinding(String sn);
  
  /**
   * 根据设备编号获取该设备的上传位置点的信息
   * @param sn
   * @return
   */
  List<Spot> selectUploadSpotsWithSn (String sn);
  
  /**根据设备号查询设备信息 */
  Device findBySn (String fSn);
  
  /**根据设备号模糊查询设备信息 */
  List<Device> findBySnVague (String fSn);
}
