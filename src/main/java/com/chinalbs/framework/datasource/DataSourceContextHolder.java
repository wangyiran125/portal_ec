package com.chinalbs.framework.datasource;

import org.springframework.util.Assert;
import com.chinalbs.framework.datasource.datatype.DataSourceType;

/**
 * 数据源 Router
 * 
 * @author shijun
 * 
 */
public class DataSourceContextHolder {

  private static final ThreadLocal<DataSourceType> contextHolder =
      new ThreadLocal<DataSourceType>();

  public static void setDataSourceType(DataSourceType dataSourceType) {
    Assert.notNull(dataSourceType, "dataSourceType cannot be null");
    contextHolder.set(dataSourceType);
  }

  public static DataSourceType getDataSourceType() {
    if (null == contextHolder.get()) {
      return DataSourceType.OWN;
    } else {
      return (DataSourceType) contextHolder.get();
    }
  }

  public static void clearDataSourceType() {
    contextHolder.remove();
  }
}
