package com.chinalbs.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;
import com.chinalbs.entity.Spot;

public class SpotRowMapper implements RowMapper<Spot>{

  public Spot mapRow(ResultSet rs, int rowNum) throws SQLException {
    Spot spot = new Spot();
    spot.setfDeviceSn(rs.getString("f_device_sn"));
    spot.setfReceive(rs.getLong("f_receive"));
    return spot;
  }


}
