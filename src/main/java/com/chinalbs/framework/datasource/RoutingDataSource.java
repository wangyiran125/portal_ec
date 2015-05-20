package com.chinalbs.framework.datasource;

import java.sql.Connection;
import java.sql.SQLException;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import com.chinalbs.common.log.LogUtil;

/**
 * 数据源路由
 * 
 * @author shijun
 * 
 */
public class RoutingDataSource extends AbstractRoutingDataSource {

  protected Object determineCurrentLookupKey() {
    if (LogUtil.isDebugEnabled(RoutingDataSource.class)) {
      LogUtil.debug(RoutingDataSource.class, "routing datasource", "Routing datasource to  : %s",
          DataSourceContextHolder.getDataSourceType().toString());
    }
    return DataSourceContextHolder.getDataSourceType();
  }

  public Connection getConnection() throws SQLException {
    return super.getConnection();
  }

}
