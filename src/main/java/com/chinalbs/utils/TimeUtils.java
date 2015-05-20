package com.chinalbs.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 时间工具类
 * @author sujinxuan
 *
 */
public class TimeUtils {
  
      private static final SimpleDateFormat simpleFormat = new SimpleDateFormat("yyyy-MM-dd");
    
      /**
       * 获取输入日期前或后N个月时间
       * @param date
       * @param month
       * @return
       */
      public static Date getMonthTime(Date date,Integer month){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, month);    
        return calendar.getTime();
        
      }
      
      /**
       * 格式化时间 YYYY-MM-DD
       * @param date
       * @return
       */
      public static String getSimpleDateString(Date date){
        return simpleFormat.format(date);
      }
}
