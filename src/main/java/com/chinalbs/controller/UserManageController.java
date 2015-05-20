package com.chinalbs.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.chinalbs.beans.CommonAttributes;
import com.chinalbs.entity.Device;
import com.chinalbs.entity.UsBindDevice;
import com.chinalbs.framework.controller.BaseController;
import com.chinalbs.framework.datasource.DataSourceContextHolder;
import com.chinalbs.framework.datasource.datatype.DataSourceType;
import com.chinalbs.framework.filter.Filter;
import com.chinalbs.framework.filter.Filter.Operator;
import com.chinalbs.framework.ordering.Ordering;
import com.chinalbs.framework.ordering.Ordering.Direction;
import com.chinalbs.framework.paging.Page;
import com.chinalbs.framework.paging.Pageable;
import com.chinalbs.service.DeviceService;
import com.chinalbs.service.UsBindDeviceService;
import com.chinalbs.service.UsUserService;

/**
 * Controller - user manage
 * 
 */
@Controller("userManageController")
@RequestMapping("/console/usermanage")
public class UserManageController extends BaseController {

	@Resource(name = "usUserServiceImpl")
	private UsUserService usUserService;
	@Resource(name = "usBindDeviceServiceImpl")
    private UsBindDeviceService usBindDeviceService;
	@Resource(name = "deviceServiceImpl")
    private DeviceService deviceService;
	

	/**
	 * 下属用户列表
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(Pageable pageable, ModelMap model,HttpSession session) {
	    // 判断在API数据库中是否登录成功，如果失败，userId为空，设为-1.查询无数据
	    String userId = (String) session.getAttribute(CommonAttributes.API_USERID_SESSION);
	    userId = userId != null ? userId : "-1";
	    if(pageable.getOrderProperty() == null && pageable.getOrderDirection() == null){
	        pageable.setOrderProperty("fTime");
	        pageable.setOrderDirection(Direction.desc);
	    }
	    DataSourceContextHolder.setDataSourceType(DataSourceType.CUST);
	    
	    List<Filter> filters = new ArrayList<Filter>();
	    Filter serviceIdFilter = new Filter("fServiceId", Operator.eq, userId);
	    filters.add(serviceIdFilter);
	    pageable.setFilters(filters);
		model.addAttribute("page", usUserService.findPage(pageable));
		
		DataSourceContextHolder.setDataSourceType(DataSourceType.OWN);
		return "/console/usermanage/list";
	}

	/**
     * 用户绑定列表
     */
    @RequestMapping(value = "/bindDevice", method = RequestMethod.GET)
    public String devicelist(Pageable pageable, ModelMap model,HttpSession session,Long id,String userName) {

        pageable.setOrderProperty("fTime");
        pageable.setOrderDirection(Direction.desc);
        DataSourceContextHolder.setDataSourceType(DataSourceType.CUST);
        
        List<Filter> filters = new ArrayList<Filter>();
        Filter userIdFilter = new Filter("fUserId", Operator.eq, id);
        filters.add(userIdFilter);
        pageable.setFilters(filters);
        Page<UsBindDevice> page = usBindDeviceService.findPage(pageable);
        
//        List<UsBindDevice> usBindDevices = new ArrayList<UsBindDevice>();
        
        List<Ordering> orders = new ArrayList<Ordering>();
        Ordering order = new Ordering("fSn", Direction.asc);
        orders.add(order);
       
        for(UsBindDevice usBindDevice:page.getContent()){
          List<Filter> snfilters = new ArrayList<Filter>();
          Filter snFilter = new Filter("fSn", Operator.eq, usBindDevice.getfDeviceSn());
          snfilters.add(snFilter);
          
          List<Device> deviceList = deviceService.findList(null, snfilters, orders);
          if(deviceList!=null){
            Device device =deviceList.get(0);
            usBindDevice.setfIsvirtual(device.getfIsvirtual());
            usBindDevice.setfPhone(device.getfPhone());
            usBindDevice.setfProtocol(device.getfProtocol());
            usBindDevice.setfType(device.getfType());
           // usBindDevices.add(usBindDevice);
          }
        }
        //Page<UsBindDevice> usBindDevicePage = new Page<UsBindDevice>(usBindDevices, page.getTotal(), page.getPageable());
        model.addAttribute("page", page);
        model.addAttribute("userName", userName);
        model.addAttribute("selectUserId", id);
        
        DataSourceContextHolder.setDataSourceType(DataSourceType.OWN);
        return "/console/usermanage/devicelist";
    }
	
}