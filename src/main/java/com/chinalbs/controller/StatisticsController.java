package com.chinalbs.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.ServletContextAware;

import com.chinalbs.beans.CommonAttributes;
import com.chinalbs.beans.MonitorInfoBean;
import com.chinalbs.beans.Setting;
import com.chinalbs.entity.DeviceStatistics;
import com.chinalbs.entity.UserStatistics;
import com.chinalbs.framework.controller.BaseController;
import com.chinalbs.framework.datasource.DataSourceContextHolder;
import com.chinalbs.framework.datasource.datatype.DataSourceType;
import com.chinalbs.framework.filter.Filter;
import com.chinalbs.framework.filter.Filter.Operator;
import com.chinalbs.framework.ordering.Ordering;
import com.chinalbs.framework.ordering.Ordering.Direction;
import com.chinalbs.framework.paging.Page;
import com.chinalbs.framework.paging.Pageable;
import com.chinalbs.json.ApiDeviceStatJson;
import com.chinalbs.json.ApiStatJson;
import com.chinalbs.json.ApiUserStatJson;
import com.chinalbs.service.DeviceStatisticsService;
import com.chinalbs.service.UserStatisticsService;
import com.chinalbs.utils.ApiUtils;
import com.chinalbs.utils.MonitorUtils;
import com.chinalbs.utils.SettingUtils;
import com.chinalbs.utils.TimeUtils;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Controller - 统计
 * 
 * @author sujinxuan
 * 
 */
@Controller("statisticsController")
@RequestMapping("/console/statistics")
public class StatisticsController extends BaseController implements ServletContextAware {

  @Resource(name = "deviceStatisticsServiceImpl")
  private DeviceStatisticsService deviceStatisticsService;
  @Resource(name = "userStatisticsServiceImpl")
  private UserStatisticsService userStatisticsService;

  /** servletContext */
  private ServletContext servletContext;

  public void setServletContext(ServletContext servletContext) {
    this.servletContext = servletContext;
  }


  @RequestMapping(value = "/system", method = RequestMethod.GET)
  public String system(ModelMap model) {
    MonitorInfoBean monitor = MonitorUtils.getMonitorInfoBean();
    model.addAttribute("javaVersion", System.getProperty("java.version"));
    model.addAttribute("javaHome", System.getProperty("java.home"));
    model.addAttribute("osName", System.getProperty("os.name"));
    model.addAttribute("osArch", System.getProperty("os.arch"));
    model.addAttribute("serverInfo", servletContext.getServerInfo());
    model.addAttribute("servletVersion",
        servletContext.getMajorVersion() + "." + servletContext.getMinorVersion());
    model.addAttribute("cpuUsage", monitor.getCpuRatio());
    model.addAttribute("memoryUsage", monitor.getMemoryUsage() * 100);
    model.addAttribute("phymemoryUsage", monitor.getPhymemoryUsage() * 100);
    model.addAttribute("thread", monitor.getTotalThread() * 1024);
    return "/console/statistics/system";
  }

  /**
   * 概要统计（用户数和设备数）
   * 
   * @param model
   * @return
   */
  @RequestMapping(value = "/view", method = RequestMethod.GET)
  public String view(ModelMap model, HttpSession session) {
    List<Integer> list = new ArrayList<Integer>();
    String userId = (String) session.getAttribute(CommonAttributes.API_USERID_SESSION);
    String resultStr = ApiUtils.apiViewStatistics(userId,session);
    ApiStatJson apiStatJson = new ApiStatJson();
    try {
          apiStatJson = new ObjectMapper().readValue(resultStr, ApiStatJson.class);
    } catch (IOException e) {
      e.printStackTrace();
    }
    list.add(apiStatJson.getUser());
    list.add(apiStatJson.getDevice());
    model.addAttribute("overview", list);
    return "/console/statistics/view";
  }

  /**
   * 设备统计数据（用于生成chart）
   * 
   * @return
   */
  @RequestMapping(value = "/equipment/chart", method = RequestMethod.POST)
  public @ResponseBody
  List<DeviceStatistics> getDeviceStatistics(HttpSession session) {
    // 判断在API数据库中是否登录成功，如果失败，userId为空，设为-1.查询无数据
    String userId = (String) session.getAttribute(CommonAttributes.API_USERID_SESSION);
    userId = userId != null ? userId : "-1";
    String resultStr = ApiUtils.apiDeviceStatistics(userId, session);
    ApiDeviceStatJson apiDeviceStatJson = new ApiDeviceStatJson();
    try {
      apiDeviceStatJson = new ObjectMapper().readValue(resultStr, ApiDeviceStatJson.class);
    } catch (IOException e) {
      e.printStackTrace();
    }
    DataSourceContextHolder.setDataSourceType(DataSourceType.CUST);

     Setting setting = SettingUtils.get();
     List<Filter> filters = new ArrayList<Filter>();
     Filter startDateFilter = new Filter("fStatDate",Operator.le, new Date());
     Filter endDateFilter = new Filter("fStatDate",Operator.ge, TimeUtils.getMonthTime(new Date(),setting.getMonthCounts()));
     Filter serviceIdFilter = new Filter("fServiceId",Operator.eq,userId);
     filters.add(serviceIdFilter);
     filters.add(startDateFilter);
     filters.add(endDateFilter);

    List<Ordering> orders = new ArrayList<Ordering>();
    Ordering order = new Ordering("fStatDate", Direction.asc);
    orders.add(order);

    List<DeviceStatistics> deviceStatistics =
        deviceStatisticsService.findList(null, filters, orders);
    boolean flagToday=false;
    if (deviceStatistics == null) {
      deviceStatistics=new ArrayList<DeviceStatistics>();
    }
      for (DeviceStatistics deviceStat : deviceStatistics) {
        if (deviceStat.getfStatDate() != null
            && deviceStat.getfStatDate().getTime() == apiDeviceStatJson.getDevice().getfStatDate()
                .getTime()) {
          deviceStat.setfInstallDevice(apiDeviceStatJson.getDevice().getfInstallDevice());
          deviceStat.setfInstallRate(apiDeviceStatJson.getDevice().getfInstallRate());
          deviceStat.setfNewInstallRate(apiDeviceStatJson.getDevice().getfNewInstallRate());
          deviceStat.setfInstallTotalDevice(apiDeviceStatJson.getDevice().getfInstallTotalDevice());
          deviceStat.setfServiceId(apiDeviceStatJson.getDevice().getfServiceId());
          deviceStat.setfTotalDevice(apiDeviceStatJson.getDevice().getfTotalDevice());
          flagToday=true;
        }
    }
    
    if(deviceStatistics.size()==0 || !flagToday ){
      
      deviceStatistics.add(apiDeviceStatJson.getDevice());
    }
    
    DataSourceContextHolder.setDataSourceType(DataSourceType.OWN);
    return deviceStatistics;
  }

  /**
   * 设备统计列表
   * 
   * @param pageable
   * @param model
   * @return
   */
  @RequestMapping(value = "/equipment", method = RequestMethod.GET)
  public String deviceList(Pageable pageable, ModelMap model, HttpSession session) {
    // 判断在API数据库中是否登录成功，如果失败，userId为空，设为-1.查询无数据
    String userId = (String) session.getAttribute(CommonAttributes.API_USERID_SESSION);
    userId = userId != null ? userId : "-1";
    String resultStr = ApiUtils.apiDeviceStatistics(userId, session);
    ApiDeviceStatJson apiDeviceStatJson = new ApiDeviceStatJson();
    try {
      apiDeviceStatJson = new ObjectMapper().readValue(resultStr, ApiDeviceStatJson.class);
    } catch (IOException e) {
      e.printStackTrace();
    }
    if (pageable.getOrderProperty() == null && pageable.getOrderDirection() == null) {
      pageable.setOrderProperty("fStatDate");
      pageable.setOrderDirection(Direction.desc);
    }

    List<Filter> filters = new ArrayList<Filter>();
    Filter serviceIdFilter = new Filter("fServiceId", Operator.eq, userId);
    filters.add(serviceIdFilter);
    pageable.setFilters(filters);
    DataSourceContextHolder.setDataSourceType(DataSourceType.CUST);
    Page<DeviceStatistics> pageDeviceStatistics = deviceStatisticsService.findPage(pageable);
    List<DeviceStatistics> deviceStatistics=pageDeviceStatistics.getContent();
    boolean flagToday=false;
    if (deviceStatistics == null) {
      deviceStatistics=new ArrayList<DeviceStatistics>();
    }
      for (DeviceStatistics deviceStat : deviceStatistics) {
        if (deviceStat.getfStatDate() != null
            && deviceStat.getfStatDate().getTime() == apiDeviceStatJson.getDevice().getfStatDate()
                .getTime()) {
          deviceStat.setfInstallDevice(apiDeviceStatJson.getDevice().getfInstallDevice());
          deviceStat.setfInstallRate(apiDeviceStatJson.getDevice().getfInstallRate());
          deviceStat.setfNewInstallRate(apiDeviceStatJson.getDevice().getfNewInstallRate());
          deviceStat.setfInstallTotalDevice(apiDeviceStatJson.getDevice().getfInstallTotalDevice());
          deviceStat.setfServiceId(apiDeviceStatJson.getDevice().getfServiceId());
          deviceStat.setfTotalDevice(apiDeviceStatJson.getDevice().getfTotalDevice());
          flagToday=true;
        }
    }
   
    if (deviceStatistics.size() == 0 || !flagToday) {
      if (Direction.desc.equals(pageDeviceStatistics.getOrderDirection())
          && pageDeviceStatistics.getPageNumber() == 1){
        deviceStatistics.add(0, apiDeviceStatJson.getDevice());
      }
      else if (Direction.asc.equals(pageDeviceStatistics.getOrderDirection())
          && (pageDeviceStatistics.getPageNumber() == deviceStatistics.size() || deviceStatistics.size()==0)) {
        deviceStatistics.add(apiDeviceStatJson.getDevice());
      }
    }

    model.addAttribute("page", pageDeviceStatistics);
    DataSourceContextHolder.setDataSourceType(DataSourceType.OWN);
    return "/console/statistics/equipment";
  }

  /**
   * 用户统计列表
   * 
   * @param pageable
   * @param model
   * @return
   */
  @RequestMapping(value = "/user", method = RequestMethod.GET)
  public String userList(Pageable pageable, ModelMap model, HttpSession session) {
    // 判断在API数据库中是否登录成功，如果失败，userId为空，设为-1.查询无数据
    String userId = (String) session.getAttribute(CommonAttributes.API_USERID_SESSION);
    userId = userId != null ? userId : "-1";

    String resultStr = ApiUtils.apiUserStatistics(userId, session);
    ApiUserStatJson apiUserStatJson = new ApiUserStatJson();
    try {
      apiUserStatJson = new ObjectMapper().readValue(resultStr, ApiUserStatJson.class);
    } catch (IOException e) {
      e.printStackTrace();
    }

    if (pageable.getOrderProperty() == null && pageable.getOrderDirection() == null) {
      pageable.setOrderProperty("fStatDate");
      pageable.setOrderDirection(Direction.desc);
    }

    List<Filter> filters = new ArrayList<Filter>();
    Filter serviceIdFilter = new Filter("fServiceId", Operator.eq, userId);
    filters.add(serviceIdFilter);
    pageable.setFilters(filters);
    DataSourceContextHolder.setDataSourceType(DataSourceType.CUST);


    Page<UserStatistics> pageUserStatistics = userStatisticsService.findPage(pageable);
    boolean flagToday=false;
    List<UserStatistics> userStatistics=pageUserStatistics.getContent();
    if (userStatistics == null) {
      userStatistics=new ArrayList<UserStatistics>();
    }
    for (UserStatistics userStat : userStatistics) {
        if (userStat.getfStatDate() != null
            && userStat.getfStatDate().getTime() == apiUserStatJson.getUser().getfStatDate()
                .getTime()) {
          userStat.setfDayRate(apiUserStatJson.getUser().getfDayRate());
          userStat.setfMonthRate(apiUserStatJson.getUser().getfMonthRate());
          userStat.setfServiceId(apiUserStatJson.getUser().getfServiceId());
          userStat.setfUserActive(apiUserStatJson.getUser().getfUserActive());
          userStat.setfUserNew(apiUserStatJson.getUser().getfUserNew());
          userStat.setfUserTotal(apiUserStatJson.getUser().getfUserTotal());
          flagToday=true;
        }
    }
    
    if (userStatistics.size() == 0 || !flagToday) {
      if (Direction.desc.equals(pageUserStatistics.getOrderDirection())
          && pageUserStatistics.getPageNumber() == 1){
          userStatistics.add(0, apiUserStatJson.getUser());
      }
      else if (Direction.asc.equals(pageUserStatistics.getOrderDirection())
          && (pageUserStatistics.getPageNumber() == userStatistics.size() || (userStatistics.size() == 0))) {
        userStatistics.add(apiUserStatJson.getUser());
      }
    }
    model.addAttribute("page", pageUserStatistics);
    DataSourceContextHolder.setDataSourceType(DataSourceType.OWN);
    return "/console/statistics/user";
  }

  /**
   * 用户统计数据（用于生成chart）
   * 
   * @return
   */
  @RequestMapping(value = "/user/chart", method = RequestMethod.POST)
  public @ResponseBody
  List<UserStatistics> getUserStatistics(HttpSession session) {

    // 判断在API数据库中是否登录成功，如果失败，userId为空，设为-1.查询无数据
    String userId = (String) session.getAttribute(CommonAttributes.API_USERID_SESSION);
    userId = userId != null ? userId : "-1";
    String resultStr = ApiUtils.apiUserStatistics(userId, session);
    ApiUserStatJson apiUserStatJson = new ApiUserStatJson();
    try {
      apiUserStatJson = new ObjectMapper().readValue(resultStr, ApiUserStatJson.class);
    } catch (IOException e) {
      e.printStackTrace();
    }
    
    DataSourceContextHolder.setDataSourceType(DataSourceType.CUST);

     Setting setting = SettingUtils.get();
     List<Filter> filters = new ArrayList<Filter>();
     Filter startDateFilter = new Filter("fStatDate",Operator.le, new Date());
     Filter endDateFilter = new Filter("fStatDate",Operator.ge, TimeUtils.getMonthTime(new Date(),setting.getMonthCounts()));
     Filter serviceIdFilter = new Filter("fServiceId",Operator.eq,userId);
     filters.add(serviceIdFilter);
     filters.add(startDateFilter);
     filters.add(endDateFilter);

    List<Ordering> orders = new ArrayList<Ordering>();
    Ordering order = new Ordering("fStatDate", Direction.asc);
    orders.add(order);

    List<UserStatistics> userStatistics = userStatisticsService.findList(null, filters, orders);
    boolean flagToday=false;
    if (userStatistics != null) {
      for (UserStatistics userStat : userStatistics) {
        if (userStat.getfStatDate() != null
            && userStat.getfStatDate().getTime() == apiUserStatJson.getUser().getfStatDate()
                .getTime()) {
          userStat.setfDayRate(apiUserStatJson.getUser().getfDayRate());
          userStat.setfMonthRate(apiUserStatJson.getUser().getfMonthRate());
          userStat.setfServiceId(apiUserStatJson.getUser().getfServiceId());
          userStat.setfUserActive(apiUserStatJson.getUser().getfUserActive());
          userStat.setfUserNew(apiUserStatJson.getUser().getfUserNew());
          userStat.setfUserTotal(apiUserStatJson.getUser().getfUserTotal());
          flagToday=true;
        }
      }
    }
    
    if(userStatistics.size()==0 || !flagToday){
      userStatistics.add(apiUserStatJson.getUser());
    }
    
    DataSourceContextHolder.setDataSourceType(DataSourceType.OWN);
    return userStatistics;
  }
  
}
