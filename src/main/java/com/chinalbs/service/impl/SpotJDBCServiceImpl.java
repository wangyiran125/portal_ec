package com.chinalbs.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.chinalbs.entity.Spot;
import com.chinalbs.rowmapper.SpotRowMapper;
import com.chinalbs.service.SpotJDBCService;
import com.chinalbs.utils.SettingUtils;

/**
 * 当用户采用分表存储轨迹数据的时候通过JDBC 查询数据
 * 
 * @author shijun
 * 
 */
@Service("spotJDBCServiceImpl")
public class SpotJDBCServiceImpl implements SpotJDBCService {

  @Resource(name = "jdbcTemplate")
  private JdbcTemplate jdbcTemplate;

  /**
   * 根据设备sn 返回spot列表
   * 
   * @param sn
   * @return
   */
  public List<Spot> findSpotByDeviceSn(String sn) {
    Assert.notNull(sn);
    String tableNameSuffix = StringUtils.substring(sn, sn.length() - 2, sn.length());
    String tableName = SettingUtils.get().getTableNamePrefix() + tableNameSuffix;
    List<Spot> spots =
        jdbcTemplate.query("select f_device_sn,f_receive from " + tableName + " where f_device_sn="+sn, new SpotRowMapper());
   // jdbcTemplate.queryForList("select f_device_sn,f_receive from " + tableName + " where f_device_sn=?", Spot.class, new Object[]{sn});
    return spots;
  }

}
